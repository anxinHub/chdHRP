(function ($) { 

    /**
     * Initialize data grid component
     * @param data
     * @param columns
     * @param options
     * @param name
     * @returns {*}
     */
    $.fn.grid = function (data, columns, options, name) {

        var plugin   = this,
            defaults = {
                emptyRow: false,
                sortable: false,
                tableClass: "table table-bordered table-condensed",
                disableKeys: [],
                moveOnRowRemove: true,
                removable: true,
                readonly: false,
                emptyGridMessage: null,
                skipOnDuplicate: null,
                initialSort: null,
                selectable: false,
                toolbar: false,
                // container used for scrolling viewport
                getContainer: null
            };

        plugin.name = null;
        plugin.isEditing = false;
        plugin.$prevRow = null;
        plugin.editorProps = {};
        plugin.preventEnter = false;
        plugin.$lastActiveCell = null;

        /**
         * Helper method to traverse elements between two nodes
         * @param node1
         * @param node2
         * @returns {*}
         */
        $.fn.between = function (node1, node2) {
            var index0 = $(this).index(node1);
            var index1 = $(this).index(node2);

            if (index0 <= index1) {
                return this.slice(index0, index1 + 1);
            }
            return this.slice(index1, index0 + 1);
        };

        /**
         * Check if current browser is Firefox
         * @returns {boolean}
         */
        var isFirefox = function () {
            return navigator.userAgent.search("Firefox") > -1;
        };

        /**
         * Force redraw on element
         * NB! Causes scroll glitch after moving viewport with scrollLeft/scrollTop
         * Redraw is needed because of an old bug in firefox
         * https://bugzilla.mozilla.org/show_bug.cgi?id=688556
         * Use only for firefox because all other browser can get their shit together
         *
         * @param $el
         */
        var redraw = function ($el) {
            if (isFirefox()) {
                var el = $el.get(0);
                var d = el.style.display;

                // actual code that will force redraw of element
                el.style.display = "none";
                el.offsetHeight; // jshint ignore:line
                el.style.display = d;
            }
        };

        /**
         * Normalize line endings
         * @param text
         * @returns string
         */
        var normalizeLineEndings = function (text) {
        	if(text==null)return "";
            return text.replace(/\r\n/g, "\n");
        };

        /**
         * Clear text selection
         */
        var clearSelection = function () {
            if (document.selection && document.selection.empty) {
                document.selection.empty();
            } else if (window.getSelection) {
                var sel = window.getSelection();
                sel.removeAllRanges();
            }
        };

        /**
         * Set active cell in grid
         * @param $el jQuery element of cell that will be set as active
         */
        plugin.setActiveCell = function ($el) {

            // disable setting active cell when in read only mode
           

            plugin.$prevRow = $(".sensei-grid-tbody>tr>.activeCell", plugin.$el).parent("tr");
            plugin.$prevRow.removeClass("activeRow");

            $(".sensei-grid-tbody>tr>.activeCell", plugin.$el).removeClass("activeCell");
            $el.addClass("activeCell");
            $el.parent("tr").addClass("activeRow");

            // redraw element to fix border style in firefox
            // this should be called only for firefox, can cause performance issues on large grids
            redraw($el.parent("tr"));

            // trigger cell:select event
            plugin.events.trigger("cell:select", $el);

            if (plugin.config.readonly) {
                return;
            }
            
            if (plugin.$prevRow.index() !== $el.parent("tr").index()) {
                plugin.events.trigger("row:select", $el.parent("tr"));
                if (plugin.$prevRow.hasClass("sensei-grid-dirty-row") && plugin.isEditing) {
                    // save editor while keeping it open before trigger row:save event
                    // otherwise the value is not present in row data
                    plugin.saveEditor(true);
                    plugin.events.trigger("row:save", plugin.getRowData(plugin.$prevRow), plugin.$prevRow, "row:select");
                }
            }
            
            //console.log("$e1:",$el);
            // set last active cell
            plugin.$lastActiveCell = $el;

            // @todo remove
            // focus first row action, if current cell is row action cell
            // if ($el.data("action") === true) {
            // }
        };

        /**
         * Get cell position and fix inconsistent position in firefox/chrome
         * @returns {object}
         */
        $.fn.cellPosition = function () {

            var pos = $(this).position();

            // if browser is firefox or similar, fix table cell position
            // firefox calculates cell positions differently from webkit browsers
            if (isFirefox()) {
                pos.left -= 1;
                pos.top -= 1;
            }

            return pos;
        };

        plugin.events = {_events: {}};
        plugin.events.on = function (event, callback, context) {
            if (!_.has(this._events, event)) {
                this._events[event] = [];
            }
            this._events[event].push({callback: callback, context: context});
        };
        plugin.events.trigger = function (event) {
            var args = Array.prototype.slice.call(arguments, 1);
            if (_.has(this._events, event)) {
                var events = this._events[event];
                _.each(events, function (e) {
                    var cbk = _.bind(e["callback"], e["context"]);
                    cbk.apply(this, args);
                });
            }
        };
        plugin.events.off = function (event) {
            if (_.has(this._events, event)) {
                delete this._events[event];
            }
        };

        // @deprecated use isFirefox function
        plugin.isSillyFirefox = function () {
            if (!plugin.$el.find("td:first").position()) {
                return false;
            }
            var tableLeft = plugin.$el.position().left;
            var cellLeft = plugin.$el.find("td:first").position().left;
            return cellLeft !== tableLeft;
        };

        /**
         * Register cell editor
         * @param Editor
         */
        plugin.registerEditor = function (Editor) {
            var instance = new Editor(plugin);
            plugin.editors[instance.name] = instance;
        };

        /**
         * Register row action
         * @param RowAction
         */
        plugin.registerRowAction = function (RowAction) {
            var instance = new RowAction(plugin);
            plugin.rowActions[instance.name] = instance;
        };

        /**
         * Render grid and all related components
         */
        plugin.render = function () {

            // render row actions
            plugin.rowElements = {};
            _.each(plugin.rowActions, function (rowAction) {
                rowAction.initialize();
                var rowEl = "<div style='width:71.5px'>" + rowAction.rowElement() + "</div>";
                plugin.rowElements[rowAction.name] = rowEl;
            });

            plugin.renderBaseTable();
            plugin.renderColumns();
            plugin.renderData();
       // console.log("vouchFootDiv",$("#vouchFootDiv",parent.document).html())
            
            // check if we need to show initial sorting
            if (plugin.config.sortable && _.isObject(plugin.config.initialSort)) {
                var col = plugin.config.initialSort.col;
                var $col = plugin.$el.find("th").filter(function () {
                    return $(this).data("name") === col;
                });
                if ($col) {
                    plugin.showSortingIndicator($col, plugin.config.initialSort.order);
                }
            }


            // render each editor
            _.each(plugin.editors, function (editor) {
                editor.initialize();
                editor.render();
                editor.getElement().hide();
            });

            plugin.bindEvents();
        };

        plugin.updateData = function (data) {
            plugin.renderData(data);
            plugin.bindEvents();
        };

        plugin.destroy = function () {
            plugin.unbindEvents();
            plugin.$el.remove();
        };

        plugin.addEdit = function (edit) {
            // the pointer is at the last element in the edits array; push and exit
            if (plugin.editPointer === plugin.edits.length - 1) {
                plugin.editPointer += 1;
                plugin.edits.push(edit);

            } else {
                // the pointer is not at the end; an undo occured, so changes after this must be erased
                plugin.editPointer += 1;

                // remove the nth element; parameter takes a position, not an index
                plugin.edits.splice(plugin.editPointer);
                plugin.edits.push(edit);
            }
        };

        plugin.redo = function () {
            if (plugin.editPointer + 1 >= plugin.edits.length) {
                return [];

            } else {
                plugin.editPointer += 1;
                return plugin.edits[plugin.editPointer];
            }
        };

        plugin.undo = function () {
            if (plugin.editPointer < 0) {
                return [];

            } else {
                var edit = plugin.edits[plugin.editPointer];
                plugin.editPointer -= 1;
                return edit;
            }
        };

        plugin.bindEvents = function () {
            // unbind previous events
            plugin.unbindEvents();

            plugin.$el.on("click.grid", ".sensei-grid-tbody>tr>td", plugin.clickCell);
            plugin.$el.on("dblclick.grid", ".sensei-grid-tbody>tr>td", plugin.dblClickCell);
            plugin.$el.on("blur.grid", plugin.blur);
            plugin.$el.on("keydown.grid", plugin.keydown);
            plugin.$el.on("click.grid", ".sensei-grid-thead .sensei-grid-sortable", plugin.sort);
            plugin.$el.on("change.grid", ".sensei-grid-tbody td.selectable :checkbox", plugin.selectCell);
            plugin.$el.on("change.grid", ".sensei-grid-thead th.selectable :checkbox", plugin.selectAll);
            $(document).on("click.grid", plugin.editorBlur);
        };

        plugin.unbindEvents = function () {
            plugin.$el.off("click.grid", ".sensei-grid-tbody>tr>td");
            plugin.$el.off("dblclick.grid", ".sensei-grid-tbody>tr>td");
            plugin.$el.off("blur.grid");
            plugin.$el.off("keydown.grid");
            plugin.$el.off("click.grid", ".sensei-grid-thead .sensei-grid-sortable");
            plugin.$el.off("change.grid", ".sensei-grid-tbody td.selectable :checkbox");
            plugin.$el.off("change.grid", ".sensei-grid-thead th.selectable :checkbox");
            $(document).off("click.grid");
        };

        /**
         * Show sorting indicator on column header
         * @param $el Column header element
         * @param forceOrder Sorting order: asc|desc
         */
        plugin.showSortingIndicator = function ($el, forceOrder) {
            var order;

            // remove row selections
            plugin.$el.find("thead th.selectable :checkbox").prop("checked", false);
            plugin.$el.find("tbody td.selectable :checkbox").prop("checked", false);
            plugin.$el.find("tbody tr.selectedRow").removeClass("selectedRow");

            // remove previous sorting icon
            plugin.$el.find("th.sensei-grid-sortable .glyphicon").remove();

            if (forceOrder === "desc" || ($el.data("order") && $el.data("order") === "asc")) {
                order = "desc";
                // add sorting icon
                $el.append($("<span>").addClass("glyphicon glyphicon-chevron-up"));
            } else {
                order = "asc";
                // add sorting icon
                $el.append($("<span>").addClass("glyphicon glyphicon-chevron-down"));
            }

            if (forceOrder) {
                order = forceOrder;
            }

            // save sort order
            $el.data("order", order);

            return order;
        };

        plugin.sort = function () {
            // get column value
            var col = $(this).data("name");
            //var order = "asc";

            // show sorting indicator on column header
            var order = plugin.showSortingIndicator($(this), order);

            // trigger callback
            plugin.events.trigger("column:sort", col, order, $(this));

            //plugin.renderData();
        };

        plugin.editorBlur = function (e) {
            if (plugin.getActiveCell().length > 0 && plugin.$el.has($(e.target)).length === 0) {
                plugin.exitEditor();
                plugin.deactivateCell();
            }
            
        };

        plugin.hideEditors = function () {
            $(".sensei-grid-editor", plugin.$el).hide();
        };

        plugin.blur = function () {
        	
        	// check if focus has moved to editor
            // e.relatedTarget && plugin.$el.has($(e.relatedTarget))
            // not firefox compatible
            if (plugin.getActiveCell().length > 0 && !plugin.isEditing) {
                var $td = plugin.getActiveCell();
                plugin.exitEditor();
                plugin.isEditing = false;
              //  plugin.deactivateCell();
                // force redraw on last active row
                redraw($td.parent("tr"));
            }
        };

        // core parsers for cell values
        plugin.parsers = {};
        plugin.parsers["string"] = function (val) {
            return val.toString();
        };
        plugin.parsers["int"] = function (val) {
            return parseInt(val);
        };
        plugin.parsers["float"] = function (val) {
            return parseFloat(val);
        };

        plugin.getCellData = function ($cell) {

            // if cell doesn't exist, return null
            if (!$cell || $cell.length === 0) {
                return null;
            }

            var value = $cell.text();
            var type = plugin.getCellType($cell);

            // parse value according to defined cell type
            if (_.has(plugin.parsers, type)) {
                value = plugin.parsers[type](value);
            }

            return value;
        };

        plugin.getCellColumn = function ($cell) {
            return $cell.data("column");
        };

        plugin.getCellType = function ($cell) {
            return $cell.data("type");
        };

        plugin.getCellStatus = function ($cell) {
            return !!$cell.data("saved");
        };

        plugin.getCellDataByIndex = function (row, cell) {
            var $row = plugin.getRowByIndex(row);
            var $cell = plugin.getCellFromRowByIndex($row, cell);
            return plugin.getCellData($cell);
        };

        plugin.setCellDataByKey = function (row, key,val) {
            var $row = plugin.getRowByIndex(row);
            $row.find("td").filter(function () {
                if($(this).data("column") === key){
                	$(this).find("div").text(val);
                }
            });
        };
        
        plugin.getCellDataByKey = function (row, key) {
            var $row = plugin.getRowByIndex(row);
            var $cell = plugin.getCellFromRowByKey($row, key);
            return plugin.getCellData($cell);
        };

        plugin.getCellFromRowByIndex = function ($row, index) {
            var $cell = $row.find("td").eq(index);
            return $cell;
        };

        plugin.getCellFromRowByKey = function ($row, key) {
            var $cell = $row.find("td").filter(function () {
                return $(this).data("column") === key;
            });
            return $cell;
        };

        plugin.getCellRow = function ($cell) {
            return $cell.parent("tr");
        };

        plugin.getRowCellsByIndex = function (index) {
            return plugin.getRowByIndex(index).find("td");
        };

        plugin.getRowCells = function ($row) {
            return $row.find("td:not(.selectable)");
        };

        plugin.getRowByIndex = function (index) {
            var $row = plugin.$el.find(".sensei-grid-tbody>tr").eq(index);
            return $row;
        };

        plugin.getRowDataByIndex = function (index) {
            var $row = plugin.getRowByIndex(index);
            return plugin.getRowData($row);
        };

        plugin.getRowData = function ($row) {

            // return null when row is not found
            if (!$row || $row.length === 0) {
                return null;
            }

            // get all cells from row
            var $cells = plugin.getRowCells($row);

            // get data from each cell
            var data = {};
            $cells.each(function () {
                data[plugin.getCellColumn($(this))] = plugin.getCellData($(this));
            });
            return data;
        };

        plugin.getRows = function () {
            return plugin.$el.find(".sensei-grid-tbody>tr");
        };

        plugin.getSelectedRows = function () {
            return plugin.$el.find(".sensei-grid-tbody>tr.selectedRow");
        };

        plugin.getGridData = function () {
            var $rows = plugin.getRows();
            return $rows.map(function () {
                return plugin.getRowData($(this));
            }).get();
        };

        plugin.getActiveCell = function () {
            // if editor is active, get active cell from it
            if (plugin.isEditing && plugin.activeEditor && plugin.activeEditor.activeCell) {
                return plugin.activeEditor.activeCell;
            }
            return $("td.activeCell", plugin.$el);
        };

        plugin.setRowSaved = function ($row) {
            $row.removeClass("sensei-grid-dirty-row").removeClass("sensei-grid-empty-row");
            $row.find(">td").data("saved", true);
            $row.find(">td.selectable").html($("<input type=checkbox>"));
        };

        plugin.setRowDirty = function ($row) {
            $row.addClass("sensei-grid-dirty-row").removeClass("sensei-grid-empty-row");
            $row.find(">td").data("saved", false);
        };

        plugin.deactivateCell = function () {
            var $td = plugin.getActiveCell();
           /* 
            * 焦点离开始终保持选中行和单元格
            $td.removeClass("activeCell");
            $td.parent("tr").removeClass("activeRow");*/
            
            plugin.events.trigger("cell:deactivate", $td);// trigger cell:deactivate event
        };

        plugin.clearActiveCell = function () {
            var $td = plugin.getActiveCell();
            var oldValue = plugin.getCellData($td);
            $(">div", $td).empty();

            // trigger cell:clear event
            plugin.events.trigger("cell:clear", oldValue, $td);
        };

        plugin.removeRow = function ($row, userArg) {

            // check if rows can be removed
            if (!plugin.config.removable) {
                return false;
            }

            // get row index
            var row = $row.index();

            // avoid removing empty row
         /*   if ($row.hasClass("sensei-grid-empty-row")) {
                return false;
            }*/

            // select another row
            if (plugin.config["moveOnRowRemove"]) {
                // @todo move up, if there are no rows below
                plugin.moveDown();
            }

            // get row data for event
            var data = plugin.getRowData($row);

            // trigger row:remove event before actual removal
            // could be used to persist changes in db
            plugin.events.trigger("row:remove", data, row, $row, userArg);

            // remove row
            $row.remove();
        };

        /**
         * Remove currently active row and trigger event
         */
        plugin.removeActiveRow = function () {

            // check if any rows are selected
            var $selectedRows = plugin.getSelectedRows();
            if ($selectedRows.length > 0) {
                $selectedRows.each(function () {
                    plugin.removeRow($(this));
                });
                return;
            }

            // get active cell
            var $cell = plugin.getActiveCell();

            // can't remove a row if there is no active cell
            if ($cell.length === 0) {
                return false;
            }

            // get row element
            var $row = plugin.getCellRow($cell);

            // remove actual row
            plugin.removeRow($row);

            // return status
            return true;
        };

        //ctrl+d复制数据
        plugin.duplicateActiveRow = function () {
        	
        	if(plugin.config.readonly || !plugin.config.emptyRow){
        		return;
        	}
            // get active cell
            var $cell = plugin.getActiveCell();

            // can't remove a row if there is no active cell
            if (!$cell) {
                return false;
            }

            // get row element
            var $row = plugin.getCellRow($cell);

            // avoid removing empty row
            if ($row.hasClass("sensei-grid-empty-row")) {
                return false;
            }
            // get current row data
            var data = plugin.getRowData($row);

            // check if we need to skip some values
            if (!_.isEmpty(plugin.config.skipOnDuplicate)) {
                data = _.omit(data, plugin.config.skipOnDuplicate);
            }

            // duplicate current row
            if(page=="vouchKind"){
            	if(kindCode=="01"){
            		data.id=getRowIndex();//id
            	}else{
            		data.budg_id=getRowIndex();//budg_id
            	}
            }else{
            	data.id=getRowIndex();//id
            	if(page=="vouch" && is_budg==1){
            		data.budg_id=getRowIndex();//budg_id
                }
            }
            
            var $newRow = $(plugin.renderRow(data, false, true));
          
            // insert row below current one
            $newRow.insertAfter($row);

            // move focus down
            plugin.moveDown();

            // trigger row:duplicate event
            plugin.events.trigger("row:duplicate", $newRow);
            
            if(page=="vouch" || page=="vouchKind"){
            	//分录页面
            	$($newRow[0].cells[2]).find("div").text("");//分录ID
            	$($newRow[0].cells[5]).find("div").text("");//借方金额
    			$($newRow[0].cells[13]).find("div").text("0.00");//借方金额
    			$($newRow[0].cells[6]).find("div").text("");//贷方金额
    			$($newRow[0].cells[14]).find("div").text("0.00");//贷方金额
    			$($newRow[0].cells[17]).find("div").text("");//预算会计摘要
    			$($newRow[0].cells[19]).find("div").text("");//借方金额
    			$($newRow[0].cells[20]).find("div").text("");//贷方金额
    			$($newRow[0].cells[21]).find("div").text("0.00");//借方金额
    			$($newRow[0].cells[22]).find("div").text("0.00");//贷方金额
    			$($newRow[0].cells[24]).find("div").text("");//预算会计ID
    			if(page=="vouch" && is_budg==2){
    				//分屏式，处理科目标记
    				var subjName=data.subj_name;
    				if(subjName!=""){
    					$($newRow[0].cells[4]).find("div").text(subjName.substring(1,subjName.length));//科目
    				}
    			}
            }else if(page=="vouchCash" || page=="vouchCheck"){
            	//辅助核算、现金流量页面
            	plugin.setCellDataByKey($newRow.index(), "money","");
            }
            
            $($newRow[0].cells[0]).find("div").append("<input type=checkbox>");
            plugin.setRowSaved($newRow);//保存，改变编辑表格状态
            // return status
            return true;
        };
        
        //插入空行
        plugin.insertActiveRow = function () {
        	if(plugin.config.readonly || !plugin.config.emptyRow){
        		return;
        	}
        	//debugger;
        	//获取格
            var $cell = plugin.getActiveCell();
            //获取行
            var $row = plugin.getCellRow($cell);

            //判断锁定行
            if ($row.hasClass("sensei-grid-empty-row")) {
                return false;
            }
            // $row.remove();
            
            //获取行数据
            var data = plugin.getRowData($row);
            
            if(page=="vouchKind"){
            	if(kindCode=="01"){
            		data.id=getRowIndex();//id
            		data.budg_id="";//budg_id
            	}else{
            		data.id="";//id
            		data.budg_id=getRowIndex();//budg_id
            	}
            	
            }else{
            	data.id=getRowIndex();//id
            	if(page=="vouch" && is_budg==1){
                	data.budg_id=getRowIndex();//budg_id
                }
            }
            
            var $newRow = $(plugin.renderRow(data, false, true));
          
            $newRow.insertBefore($row);
            //$newRow.insertAfter($row);
            //plugin.moveDown();
            
            if(page=="vouch" || page=="vouchKind"){
            	//分录页面
            	setSubjFlag($newRow[0].cells[4]);//清除标记
            	
            	$($newRow[0].cells[2]).find("div").text("");//分录ID
            	//$($newRow[0].cells[3]).find("div").text("");//摘要
            	$($newRow[0].cells[4]).find("div").text("");//财务会计科目名称
            	$($newRow[0].cells[5]).find("div").text("");//借方金额
            	$($newRow[0].cells[6]).find("div").text("");//贷方金额
            	$($newRow[0].cells[7]).find("div").text("");//财务会计科目编码
    			$($newRow[0].cells[13]).find("div").text("0.00");//借方金额
    			$($newRow[0].cells[14]).find("div").text("0.00");//贷方金额
    			$($newRow[0].cells[17]).find("div").text("");//预算会计摘要
    			$($newRow[0].cells[18]).find("div").text("");//预算会计科目名称
    			$($newRow[0].cells[19]).find("div").text("");//借方金额
    			$($newRow[0].cells[20]).find("div").text("");//贷方金额
    			$($newRow[0].cells[21]).find("div").text("0.00");//借方金额
    			$($newRow[0].cells[22]).find("div").text("0.00");//贷方金额
    			$($newRow[0].cells[23]).find("div").text("");//预算会计科目编码
    			$($newRow[0].cells[24]).find("div").text("");//预算会计ID
            }else if(page=="vouchCash" || page=="vouchCheck"){
            	//辅助核算、现金流量页面
            	$.each(grid.columns, function (i,column) {
            		
            		var columnName=column.name;
            		if(columnName=="id" || columnName=="summary"){
            			return true;
            		}
            		plugin.setCellDataByKey($newRow.index(), columnName,"");
                	
            	});
            }
            $($newRow[0].cells[0]).find("div").append("<input type=checkbox>");
            plugin.setRowSaved($newRow);//保存，改变编辑表格状态
            return true;
        };

        plugin.moveRight = function () {

            var $td = plugin.getActiveCell();

            if ($td.next().length > 0) {
                plugin.setActiveCell($td.next());
            } else {
                // try next row
                var $nextRow = $td.parent("tr").next();
                if ($nextRow.length > 0) {
                    plugin.setActiveCell($("td:first", $nextRow));
                }
            }
        };

        plugin.moveUp = function () {
            var $td = plugin.getActiveCell();

            var $prevRow = $td.parent("tr").prev();
            if ($prevRow.length > 0) {
                var index = $td.index();
                var $upCell = $("td", $prevRow).eq(index);
                if ($upCell.length > 0) {
                    plugin.setActiveCell($upCell);
                } else {
                    plugin.setActiveCell($("td:last", $prevRow));
                }
            }else{
            	//向上移到头的时候置顶
            	//var top=document.documentElement.scrollTop;
            	//window.scroll(0,top); IE11不支持
            	window.scrollTo(0,0);
            }
        };

        plugin.moveLeft = function () {

            var $td = plugin.getActiveCell();

            if ($td.prev().length > 0) {
                plugin.setActiveCell($td.prev());
            } else {
                // try next row
                var $prevRow = $td.parent("tr").prev();
                if ($prevRow.length > 0) {
                    plugin.setActiveCell($("td:last", $prevRow));
                }
            }
        };

        plugin.moveDown = function () {

            var $td = plugin.getActiveCell();

            var $nextRow = $td.parent("tr").next();
            if ($nextRow.length > 0) {
                var index = $td.index();
                var $downCell = $("td", $nextRow).eq(index);
                if ($downCell.length > 0) {
                    plugin.setActiveCell($downCell);
                } else {
                    plugin.setActiveCell($("td:first", $nextRow));
                }
            }else{
            	window.scrollTo(0,$(window).height()); 
            }
        };

        plugin.scrollIntoView = function ($el, $container) {
            var padding = 50;
            var top = 0;
            var left = 0;
            if ($container.offset()) {
                top = $container.offset().top + $container.scrollTop()+100;
                left = $container.offset().left + $container.scrollLeft();
            }
          
            $container.scrollTop(
                $el.offset().top - top - padding
            );
            $container.scrollLeft(
                $el.offset().left - left - padding
            );
        };

        plugin.move = function (direction) {
            
            var directionMethod = "move" + direction.charAt(0).toUpperCase() + direction.substr(1);
            if (_.has(plugin, directionMethod)) {

                // move active cell
                plugin[directionMethod]();

                if (plugin.isEditing) {
                    // save & hide editor
                    plugin.saveEditor();
                }

                var $cell = plugin.getActiveCell();
                if($cell.data("hide")){
                	return;
                }
                var $container = $(window);
                var viewportSettings = {};
                if (plugin.config.getContainer) {
                    $container = plugin.config.getContainer();
                    viewportSettings = {viewport: $container};
                }

                // check if isInViewport method exists and active cell is in the viewport
                if ($.fn.isInViewport && $cell.isInViewport(viewportSettings).length === 0) {
                    // cell is not in containers viewport, let's scroll
                    plugin.scrollIntoView($cell, $container);
                }

                if (plugin.isEditing) {
                    // show editor for currently active cell
                    plugin.editCell();
                }


            } else {
                console.warn("move method not found", directionMethod);
            }
        };

        plugin.editCell = function () {
            // disable editor when in read only mode
            if (!plugin.config.readonly) {
            	plugin.isEditing=true;
            	plugin.copyPreRow();//复制上一行数据
                plugin.showEditor();
            }
            
        };

        plugin.getEditor = function () {
            return plugin.activeEditor;
        };

        plugin.getEditorInstance = function () {
            var $td = plugin.getActiveCell();

            var editorName = $td.data("editor");

            if (_.isFunction(editorName)) {
                editorName = editorName(plugin);
            }

            if (editorName && _.has(plugin.editors, editorName)) {
                // check if there is props for this editor
                var col = plugin.getCellColumn($td);
                if (_.has(plugin.editorProps, col)) {
                    plugin.editors[editorName].props = plugin.editorProps[col];
                }
                return plugin.editors[editorName];
            } else {
                // throw Error("Editor not found: " + editorName);
                // editor not found, skip cell
                //console.info("Editor not found, skipping cell: " + editorName);
                return false;
            }
        };

        plugin.saveEditor = function (keepEditor) {

            // save editor if is active
            if (plugin.isEditing && plugin.activeEditor) {

                var $td = plugin.getActiveCell();
                var val = plugin.activeEditor.getValue();

                if (normalizeLineEndings(val) !== normalizeLineEndings($td.text())) {

                    // stores the original content and records the cell row and column
                    var edit = {
                        "previousState": plugin.getCellData($td),
                        "currentState": val,
                        "row": plugin.getRowData(plugin.getCellRow($td))["id"],
                        "column": $td.index()
                    };

                    var allowHTML = $td.data("allowHTML");

                    // save the state prior to edit
                    plugin.addEdit(edit);

                    // set value from editor to the active cell
                    if (allowHTML) {
                        $td.html($("<div>").html(val));
                    } else {
                       // $td.html($("<div class='l-acc_test' style='font-color:red'>").text(val));
                    	 $td.find("div").text(val);
                    }

                    // trigger editor:save event
                    var data = {};
                    data[$td.data("column")] = val;
                    plugin.events.trigger("editor:save", data, $td);
                 
                    // remove empty row status from current row and assure that
                    // there is at least one empty row at the end of table
                    var $tr = $td.parent("tr");
                    if ($tr.hasClass("sensei-grid-empty-row")) {
                        $tr.removeClass("sensei-grid-empty-row").addClass("sensei-grid-dirty-row");
                        plugin.assureEmptyRow();
                    }
                }
            }

            // hide editor if needed
            if (!keepEditor && plugin.activeEditor) {
                plugin.getEditor().hide();
            }
          
        };

        //添加空行
        plugin.assureEmptyRow = function () {
        	if(plugin.config.readonly){
        		return;
        	}
           // if (plugin.config["emptyRow"] && plugin.$el.find(".sensei-grid-tbody>tr.sensei-grid-empty-row").length <20) {
        	 if (plugin.config["emptyRow"]) {
                var $tbody = plugin.$el.find(".sensei-grid-tbody");
                var $row = plugin.renderRow(null, false);
                
                if(page=="vouchKind"){
                	if(kindCode=="01"){
                		$row.cells[1].innerText=getRowIndex();//id
                	}else{
                		$row.cells[16].innerText=getRowIndex();//budg_id
                	}
                }else{
                	$row.cells[1].innerText=getRowIndex();//id
                	if(page=="vouch" && is_budg==1){
                		$row.cells[16].innerText=getRowIndex();//budg_id
                    }
                }
                               
                if (page=="vouch" || page=="vouchKind") {
                	customStyle($($row.cells[5]));//借方金额样式
                	customStyle($($row.cells[6]));//贷方金额样式
                	customStyle($($row.cells[19]));//借方金额样式
                	customStyle($($row.cells[20]));//贷方金额样式
                }
                
                $tbody.append($row);
            }
        };

        plugin.exitEditor = function (skipSave) {
        	
            var $td = plugin.getActiveCell();
            if (plugin.isEditing && plugin.activeEditor) {
                if (!skipSave) {
                    plugin.saveEditor();

                    var $row = $td.parent("tr");
                    // if the row was dirty, save it as a whole
                    if ($row.hasClass("sensei-grid-dirty-row") && plugin.isEditing) {
                        plugin.events.trigger("row:save", plugin.getRowData($row), $row, "editor:close");
                    }

                } else {
                    plugin.getEditor().hide();
                }
            }
          
            // need to regain focus
            if (plugin.isEditing) {
            	plugin.setActiveCell($td);
            	//plugin.$el.find("input").focus();
            	if(window.navigator.userAgent.indexOf("Chrome") >= 0){
            		plugin.$el.focus();
            	}else{
            		$($td).focus();
            	}
            	
            }
           
            plugin.isEditing = false;
        };

        plugin.moveEditor = function () {
            if (plugin.isEditing) {
                plugin.showEditor();
                plugin.editCell();
            }
        };

        plugin.selectRow = function ($row, forceSelect, forceUnselect) {
            // check if row can be selected
            if (!plugin.config.selectable) {
                return;
            }

            var $cell = $row.find(".selectable");
            plugin.selectCell($cell, forceSelect, forceUnselect);
        };

        plugin.selectCell = function ($cell, forceSelect, forceUnselect) {
            // check if "this" is a selectable cell
            // "this" will be a dom element if selectCell is called as a callback to dom event
            if ($(this) && $(this).is("input")) {
                $cell = $(this).parents("td.selectable");
            } else {
                // toggle checkbox state because if "this" is not a dom element, selectCell is not called as callback to
                // dom event and checkbox state is unchanged
                var $checkbox = $cell.find(":checkbox");

                if (forceSelect) {
                    $checkbox.prop("checked", true);
                } else if (forceUnselect) {
                    $checkbox.prop("checked", false);
                } else {
                    $checkbox.prop("checked", !$checkbox.prop("checked"));
                }
            }

            // don't select empty row
            if ($cell.parent().hasClass("sensei-grid-empty-row")) {
                return;
            }

            // toggle row select state
            if (forceSelect) {
                $cell.parent().addClass("selectedRow");
            } else if (forceUnselect) {
                $cell.parent().removeClass("selectedRow");
            } else {
                $cell.parent().toggleClass("selectedRow");
            }


            if ($cell.parent().hasClass("selectedRow")) {
                plugin.events.trigger("row:mark", $cell.parent());
            } else {
                plugin.events.trigger("row:unmark", $cell.parent());
            }
        };

        plugin.selectAll = function () {
            // forced states
            var forceSelect = true;
            var forceUnselect = false;

            var $checkbox = plugin.$el.find("thead th.selectable :checkbox");

            // check if current checkbox is unchecked
            if ($checkbox && !$checkbox.is(":checked")) {
                forceSelect = false;
                forceUnselect = true;
            }

            var $rows = plugin.getRows();
            $rows.each(function () {
                plugin.selectRow($(this), forceSelect, forceUnselect);
            });
        };

        plugin.showEditor = function () {
            // get editor instance
        	
            var editor = plugin.getEditorInstance();

            if (!editor) {
                plugin.exitEditor();
                plugin.isEditing = true;
                return;
            }

            // set active editor instance
            plugin.activeEditor = editor;
            // assign element instances
            var $editor = plugin.activeEditor.getElement();
            var $td = plugin.getActiveCell();
            
            plugin.activeEditor.activeCell = $td;

            // set editing mode after we have gotten active cell
            plugin.isEditing = true;
            // show editor and set correct position
            plugin.activeEditor.show();
            $editor.css($td.cellPosition());
            plugin.activeEditor.setDimensions($td);
            // set value in editor
            var column = $td.data("column");
            var allowHTML = $td.data("allowHTML");
           // var value = allowHTML ? $td.find(">div").html() : $td.text();
            var value = allowHTML ? $td.find(">div").html() : $td.find(">div").text();
            plugin.activeEditor.setValue(value);
            // set editor position again, because setting value and focusing editor
            // can cause different position
            $editor.css($td.cellPosition());
            // trigger editor:load event
            var data = {};
            data[column] = value;
            plugin.events.trigger("editor:load", data, $td);
            return $editor;
        };
        
        plugin.keydown = function (e) {
        	
            var preventDefault = true;

            // all keyCodes that will be used
            var codes = [8, 9, 13, 17, 32, 37, 38, 39, 40, 65, 68, 89, 90, 81];

            // specific keyCodes that won't be hijacked from the editor
            var editorCodes = [8, 32, 37, 38, 39, 40, 65, 68, 89, 90, 81];

            // loose keyCodes that don't need an active cell to work
            var looseCodes = [8];

            // get active cell
            var $activeCell = plugin.getActiveCell();
            var colName=$activeCell.data("column");
            
            if ((plugin.getActiveCell().length === 0 && !plugin.isEditing && !_.contains(looseCodes, e.which)) || !_.contains(codes, e.which)) {
                return;
            }
            
            if (!plugin.isEditing && _.contains(plugin.config.disableKeys, e.which)) {
                e.preventDefault();
                return;
            }
          
            //科目，空格触发
            if(plugin.isEditing && colName && colName.indexOf("subj_name")!=-1 && e.which=="32"){
            	var actVal=plugin.activeEditor.getValue();
            	plugin.getEditor().hide();
            	openSubjTree($activeCell,actVal);
            	return;
            }
            
            // if editor is editing and active editor can be found, prevent
            // keystrokes that could intervene with editor
            if(plugin.activeEditor && (e.which=="37" || e.which=="39")){
            	//left right
            }else if(plugin.activeEditor && (e.which=="38" || e.which=="40") && !$(plugin.activeEditor.editor).hasClass("sensei-grid-editor sensei-grid-ac-editor")){
            	//top down 非下拉框
            }else{
            	if (plugin.isEditing && plugin.getEditor() && _.contains(editorCodes, e.which)) {
                    return;
                } else{
                    e.preventDefault();
                }
            }
           
            var $nextCell, $checkbox;
            switch (e.which) {
                case 37: // left
                
                	if(plugin.activeEditor && plugin.isEditing){
                		//sensei-grid-basic-editor
                		//var $editorInput=plugin.$el.find(".sensei-grid-ac-editor input")[0];
                		var $editorInput=$(plugin.activeEditor.editor).find("input")[0];
                		var editorIdex=$editorInput.selectionStart;
                		var textLength=plugin.activeEditor.getValue().length;
                		//console.log($(plugin.getActiveCell).find("div").className);
                		//(editorIdex==0 && $editorInput.selectionEnd==$($editorInput).val().length)
                		if(textLength>0 && editorIdex>=1){
                			return;
                		}
                	}
                	
                    plugin.move("left");
                    plugin.hideMove("left");
                    
                    if(plugin.getActiveCell() && (page=="vouch" || page=="vouchKind")){
                    	//clickVouchDetailFun("left",plugin.getActiveCell());
                	}
                    break;
                case 38: // up

                    // check if current cell is selectable and shift key is pressed
                    if (e.shiftKey && plugin.config.selectable) {
                        // select cell/row
                        plugin.selectRow($activeCell.parent(), true);
                    }

                    plugin.move("up");

                    $nextCell = plugin.getActiveCell();
                    // check if next cell is selectable and shift key is pressed
                    if (e.shiftKey && plugin.config.selectable) {
                        // select cell/row
                        plugin.selectRow($nextCell.parent(), true);
                    }
                   
                    if($nextCell && (page=="vouch" || page=="vouchKind")){
                    	//点击触发，键盘不触发，会影响性能
                    	//clickVouchDetailFun("up",$nextCell);
                	}
                    
                    break;
                case 39: // right
                	
                	if(plugin.activeEditor && plugin.isEditing){
                		//var $editorInput=plugin.$el.find(".sensei-grid-ac-editor input")[0];
                		var $editorInput=$(plugin.activeEditor.editor).find("input")[0];
                		var editorIdex=$editorInput.selectionEnd;
                		var textLength=plugin.activeEditor.getValue().length;
                		//console.log(editorIdex,plugin.activeEditor.getValue());
                		if(textLength>0 && (editorIdex !==  textLength || ($editorInput.selectionStart==0 && editorIdex==textLength))){
                			return;
                		}
                	}
                	
                    plugin.move("right");
                    plugin.hideMove("right");
                    
                    if(plugin.getActiveCell() && (page=="vouch" || page=="vouchKind")){
                    	//点击触发，键盘不触发，会影响性能
                    	//clickVouchDetailFun("right",plugin.getActiveCell());
                	}
                    break;
                case 40: // down
                	
                    // check if current cell is selectable and shift key is pressed
                    if (e.shiftKey && plugin.config.selectable) {
                        // select cell/row
                        plugin.selectRow($activeCell.parent(), true);
                    }
                    plugin.move("down");

                    $nextCell = plugin.getActiveCell();
                    // check if next cell is selectable and shift key is pressed
                    if (e.shiftKey && plugin.config.selectable) {
                        // select cell/row
                        plugin.selectRow($nextCell.parent(), true);
                    }
                    
                    if($nextCell && (page=="vouch" || page=="vouchKind")){
                    	//clickVouchDetailFun("down",$nextCell);
                	}
                    break;
                case 13: // enter
                    var isRowAction = false;
                    var isSelectable = false;
                   
                	
                    if ($activeCell && $activeCell.data("action")) {
                        var rowActionName = $activeCell.data("action-name");
                        if (plugin.rowActions[rowActionName]) {
                            plugin.rowActions[rowActionName].trigger({
                                data: {$activeCell: $activeCell}
                            });
                        }
                       
                        isRowAction = true;
                    }
                    
                    // check if cell is selectable checkbox wrapper
                    if ($activeCell && $activeCell.hasClass("selectable")) {

                        // select cell/row
                        plugin.selectCell($activeCell);

                        // set isSelectable state
                        isSelectable = true;
                       
                    }
                    
                    // @todo the code below must be refactored
                    if (plugin.isEditing) {
                    	
                    	var $row=plugin.getCellRow($activeCell);
                        if (e.ctrlKey && e.shiftKey) {
                            plugin.move("up");
                        } else if (e.ctrlKey && !e.shiftKey) {
                            plugin.move("down");
                        } else {
                            // enter on row action and selectable cell should not change editor state
                            if (!plugin.preventEnter && !isRowAction && !isSelectable) {                            	
                            	
                            	var isRight=true;
                            	if($(".l-dialog-btn-inner",parent.document).length>0 && $(".l-dialog-btn-inner",parent.document).text()=="确定"){
                            		isRight=false;
//                            		if(page=="vouch" && $(".l-dialog-content",parent.document).text()=="保存成功。" && parent.paraList["008"]==2){
//                            			parent.myNewVouch();
//                            		}
                            		$(".l-dialog-btn-inner",parent.document).click();
                            	}
                            	
                            	//以下列值为空不跳到下一个单元格
                            	if((colName=="summary" || colName=="budg_summary") && plugin.activeEditor.getValue()==""){
                            		if($('.tt-menu').css("display") == 'none'){
                            			plugin.activeEditor.hide()
                        				plugin.showEditor();
                        			}
                            		isRight=false;
                            		
                            	}
                            	
                            	if(colName=="check_no"){
                            		if($($row[0].cells[$activeCell.index()-1]).find("div").text().split(" ")[0]==""){
                            			//票据类型为空
                            			isRight=true;
                            		}else{
                            			
                            			if(plugin.activeEditor.getValue()==""){
                            				//根据票据类型加载票据号
                            				if($('.tt-menu').css("display") == 'none'){
                            					plugin.activeEditor.hide()
                                				plugin.showEditor();
                                			}
                                			isRight=false;
                                         	/*var para={
                                         		column_id:"check_no",
                                         		paper_type_code:$($row[0].cells[$activeCell.index()-1]).find("div").text().split(" ")[0]
                                         	};
                                         	ajaxJsonObjectByUrl("accCheckDictList.do?isCheck=false",para,function (responseData){
                                         		if(responseData.length==0){
                                         			console.log("========")
                                         			plugin.editorProps["check_no"] = [];
                                         		}else{
                                         			plugin.editorProps["check_no"] = responseData;
                                         		}
                                         		
                                         		plugin.showEditor();
                                         	},false);*/
                                			
                                		}
                            		}
                            		
                            	}else if(colName=="subj_name" || colName=="budg_subj_name" || (colName=="cash_name" && parent.paraList["026"]==1) || colName=="diff_item_name" || colName=="money" || colName.indexOf("check")!=-1 ){
                            		
                            		if(plugin.activeEditor.getValue()==""){
                            			
                            			if($('.tt-menu').css("display") == 'none'){
                            				plugin.activeEditor.hide()
                            				plugin.showEditor();
                            			}
                            			isRight=false;
                            		}else if(colName!="money"){
                            			if($(".tt-suggestion.tt-selectable:first").text()==""){
                            				isRight=false;
                            			}else if($('.tt-menu').css("display") != 'none'){
                               	 			//回车自动匹配第一条数据
                               	 			plugin.activeEditor.setValue($(".tt-suggestion.tt-selectable:first").text());
                               	 			isRight=true;
                               	 		}
                           	 			/*$.each(grid.editorProps[colName], function (i, str) {
                            				//科目、辅助核算等下拉字典：如果输入编码不匹配的话，不跳过
                                			if(plugin.activeEditor.getValue()==str.name.split(" ")[0] || plugin.activeEditor.getValue()==str.name){
                                				isRight=true;
                                				return false;
                                   	 		}
                           	 			});*/
                            		
                            			if(!isRight){
                            				plugin.activeEditor.setValue("");
                            			}else{
                            				
                            				if((page=="vouch" || page=="vouchKind") && plugin.activeEditor.getValue()!="" && parent.paraList["047"]==1){
                            					
                            					if(colName=="subj_name" && $($row[0].cells[$activeCell.index()+1]).find("div").text()==""){
                                					var subjCode=plugin.activeEditor.getValue();
                                					subjCode=subjCode.substring(0,1);
                                					if(subjCode=="4"){
                                						plugin.move("right");
                                                    	plugin.hideMove("right");
                                                    	plugin.editCell();
                                					}
                                				}else if(colName=="budg_subj_name" && $($row[0].cells[$activeCell.index()+1]).find("div").text()==""){
                                					var subjCode=plugin.activeEditor.getValue();
                                					subjCode=subjCode.substring(0,1);
                                					if(subjCode=="6"){
                                						plugin.move("right");
                                                    	plugin.hideMove("right");
                                                    	plugin.editCell();
                                					}
                                				}
                            					
                            				}
                            				
                            			}

                            		}
                            		
                            	} 
                            	
                            	if(isRight){
                            		var resOpen=false;
                            		var openCheck=true;
                            		//借方、贷方输入空格不弹出辅助核算窗口
                            		if(colName=="debit_name" && $($row[0].cells[6]).find("div").text()!=""){
                            			openCheck=false;
                            		}else if(colName=="credit_name" && $($row[0].cells[5]).find("div").text()!=""){
                            			openCheck=false;
                            		}else if(colName=="budg_debit_name" && $($row[0].cells[20]).find("div").text()!=""){
                            			openCheck=false;
                            		}else if(colName=="budg_credit_name" && $($row[0].cells[19]).find("div").text()!=""){
                            			openCheck=false;
                            		}
                            		
                            		plugin.exitEditor();
                            		
                            		if(openCheck && $activeCell && (page=="vouch" || page=="vouchKind")){
                            			resOpen=openVouchCheck($activeCell,1);
                                	}
                            		
                            		if(page=="vouch" || page=="vouchKind"){
                            			
                            			//分栏式
                            			if(page=="vouch" && is_budg==1){
                            				
                            				var $nextRow = $activeCell.parent("tr").next();//下一行
                                			var isNext=false;
                                			if(colName=="debit_name"){
                                        		if(parent.paraList["047"]==1 && $($row[0].cells[$activeCell.index()-1]).find("div").text()!="" && $($row[0].cells[$activeCell.index()+1]).find("div").text()==""){
                                        			var subjCode=$($row[0].cells[$activeCell.index()-1]).find("div").text();
                                					subjCode=subjCode.substring(0,1);
                                					if(subjCode=="5"){
                                						isNext=true;
                                					}
                                        		}
                                        	}
                                			
                                			
                                			if(colName=="credit_name" || isNext){
                                				//贷方金额列，移到下一行
                                    	        if ($nextRow.length > 0) {
                                    	        	if($($nextRow[0].cells[3]).find("div").text()==""){
                                    	        		var cellsText3=$($row[0].cells[3]).find("div").text();
                                    	        		if(cellsText3!="" && cellsText3.substring(0,1)=="[" && cellsText3.indexOf("]")!=-1){
                                    	        			cellsText3=cellsText3.substring(cellsText3.indexOf("]")+1,cellsText3.length);
                                    	        		}
                                    	        		$($nextRow[0].cells[3]).find("div").text(cellsText3);//将摘要复制到下一行
                                    	        	}
                                    	        	grid.setActiveCell($($nextRow[0].cells[3]));
                                    	        	resOpen=true;
                                    	        	plugin.editCell();
                                    	        } 
                                			}
                                			
                                			if(colName=="budg_debit_name"){
                                        		if(parent.paraList["047"]==1 && $($row[0].cells[$activeCell.index()-1]).find("div").text()!="" && $($row[0].cells[$activeCell.index()+1]).find("div").text()==""){
                                        			var subjCode=$($row[0].cells[$activeCell.index()-1]).find("div").text();
                                					subjCode=subjCode.substring(0,1);
                                					if(subjCode=="7"){
                                						isNext=true;
                                					}
                                        		}
                                        	}
                                			
                                			if(colName=="budg_credit_name" || isNext){
                                				//预算会计，贷方金额列，如果下一行财务会计有数据，预算会计没有数据，直接跳到预算会计
                                    	        if ($nextRow.length > 0 && $($nextRow[0].cells[4]).find("div").text()!=""  && $($nextRow[0].cells[18]).find("div").text()=="") {
                                    	        	grid.setActiveCell($($nextRow[0].cells[18]));
                                    	        	resOpen=true;
                                    	        	plugin.editCell();
                                    	        }else{
                                    	        	grid.setActiveCell($($nextRow[0].cells[3]));
                                    	        	resOpen=true;
                                    	        	plugin.editCell();
                                    	        } 
                                			}
                            				
                            			}else{
                            				if(colName=="debit_name"){
                            					
                                        		if(parent.paraList["047"]==1 && $($row[0].cells[$activeCell.index()-1]).find("div").text()!="" && $($row[0].cells[$activeCell.index()+1]).find("div").text()==""){
                                        			var subjCode=$($row[0].cells[$activeCell.index()-1]).find("div").text();
                                					subjCode=subjCode.substring(0,1);
                                					if(subjCode=="5"){
                                						var $nextRow = $activeCell.parent("tr").next();//下一行
                                						grid.setActiveCell($($nextRow[0].cells[3]));
                                        	        	resOpen=true;
                                        	        	plugin.editCell();
                                					}
                                        		}
                                        	}
                            				
                            				if(colName=="budg_debit_name"){
                            					
                                        		if(parent.paraList["047"]==1 && $($row[0].cells[$activeCell.index()-1]).find("div").text()!="" && $($row[0].cells[$activeCell.index()+1]).find("div").text()==""){
                                        			var subjCode=$($row[0].cells[$activeCell.index()-1]).find("div").text();
                                					subjCode=subjCode.substring(0,1);
                                					if(subjCode=="7"){
                                						var $nextRow = $activeCell.parent("tr").next();//下一行
                                						grid.setActiveCell($($nextRow[0].cells[17]));
                                        	        	resOpen=true;
                                        	        	plugin.editCell();
                                					}
                                        		}
                                        	}
                            				
                            			}
                            			
                                	}
                            		
                            		if(!resOpen){
                            			//不弹出窗口，光标向右移动
                            			plugin.move("right");
                                    	plugin.hideMove("right");
                                    	plugin.editCell();
                            		}
                            		
                            	}
                               	
                            }
                        }
                    } else {
                        // enter on row action and selectable cell should not change editor state
                        if (!isRowAction && !isSelectable) {
                            plugin.editCell();
                        }
                    }
                    
                    if($activeCell && (page=="vouch" || page=="vouchKind")){
                     	clickVouchDetailFun("enter",$activeCell);
                 	}
                     
                    if($activeCell && page=="vouchCheck"){
                     	clickCheckDetailFun("enter",$activeCell);
                 	}
                   
                    break;
                case 17: // Ctrl
                    /*if (plugin.isEditing) {
                        plugin.exitEditor(true);
                    } else {
                        // get selected
                        var $selectedRows = plugin.getSelectedRows();
                        if ($selectedRows && $selectedRows.length > 0) {
                            // unselect all
                            $checkbox = plugin.$el.find("thead th.selectable :checkbox");
                            $checkbox.prop("checked", false);
                            plugin.selectAll();
                        } else {
                            // remove focus from grid if no rows are selected
                            plugin.$el.blur();
                        }
                    	
                    }*/
                    break;
                case 9: // tab
                    if (e.shiftKey) {
                        plugin.move("left");
                        plugin.hideMove("left");
                    } else {
                        plugin.move("right");
                        plugin.hideMove("right");
                    }
                    break;
                case 32: // space空格
                    // check if row is selectable
                    if ($activeCell && plugin.config.selectable) {
                        // select row
                        plugin.selectRow($activeCell.parent());
                    }
                    break;
                case 8: // backspace回删
                    if (e.ctrlKey || e.metaKey) {
                        plugin.removeActiveRow();
                    }
                    break;
                case 65: // "a" key
                    if (plugin.config.selectable && (e.ctrlKey || e.metaKey || e.shiftKey)) {

                        // toggle main selectable checkbox
                        $checkbox = plugin.$el.find("thead th.selectable :checkbox");
                        $checkbox.prop("checked", !$checkbox.prop("checked"));

                        // toggle select all rows
                        plugin.selectAll();
                    }
                    break;
                case 90: // undo
                    if (e.ctrlKey || e.metaKey) {
                        var edit = plugin.undo();

                        if (('row' in edit) && ('column' in edit)) {

                            var row = plugin.getRowByIndex(edit.row - 1);
                            var element = plugin.getCellFromRowByIndex(row, edit.column);

                            // set value from editor to the active cell
                            element.find("div").text(edit.previousState);

                            // trigger editor:save event
                            var data = {};
                            data[element.data("column")] = edit.previousState;
                            plugin.events.trigger("editor:save", data, element);
                        }
                    }
                    break;
                case 89: // redo
                    if (e.ctrlKey || e.metaKey) {
                        var redo = plugin.redo();

                        if (('row' in redo) && ('column' in redo)) {

                            var currentRow = plugin.getRowByIndex(redo.row - 1);
                            var currentEl = plugin.getCellFromRowByIndex(currentRow, redo.column);

                            // set value from editor to the active cell
                            currentEl.find("div").text(redo.currentState);

                            // trigger editor:save event
                            var localData = {};
                            localData[currentEl.data("column")] = redo.currentState;
                            plugin.events.trigger("editor:save", localData, currentEl);

                        }
                    }
                    break;
                case 68: // keypress "d" duplicate row
                    if (e.ctrlKey || e.shiftKey || e.metaKey) {
                        plugin.duplicateActiveRow();
                    }
                    break;
                case 81: // keypress "q" 
                    if (e.ctrlKey || e.shiftKey || e.metaKey) {
                        //plugin.duplicateActiveRow();//复制
                    	//plugin.editCell();//选中编辑
                    	//plugin.renderColumns();//加载表头
                    	plugin.insertActiveRow();
                    }
                    break;
            }

            if (preventDefault) {
                e.preventDefault();
            }
        };

        plugin.clickCell = function (e) {
        	
            // dont prevent default event if this is selectable cell with checkbox
            if (!$(this).hasClass("selectable")) {
                // is not selectable cell, prevent default event
                e.preventDefault();
            }

            var $prev;
            if (plugin.getActiveCell()) {
                $prev = plugin.getActiveCell().parent();
            }

            if (plugin.isEditing) {
                plugin.exitEditor();
            }
            
            plugin.setActiveCell($(this));

            // if shift key was pressed, extend selection between last active and current row
            if (plugin.config.selectable && e.shiftKey) {

                // disable text selection
                clearSelection();

                var $currentRow = $(this).parent();
                if ($prev && $currentRow) {
                    var $between = plugin.$el.find("tbody>tr").between($prev, $currentRow);
                    $between.each(function () {
                        // if current cell is selectable, skip its row, because the select event will be called anyway from
                        // checkbox change event
                        if (!$(e.target).is(":checkbox") || !$(this).is($currentRow)) {
                            plugin.selectCell($(this).find("td.selectable"), true);
                        }
                    });
                }
            }
            
            if(plugin.getActiveCell() && (page=="vouch" || page=="vouchKind")){
            	clickVouchDetailFun("clickCell",plugin.getActiveCell());
        	}
            
            if(plugin.getActiveCell() && page=="vouchCheck"){
            	clickCheckDetailFun("clickCell",plugin.getActiveCell());
        	}
        };

        plugin.dblClickCell = function (e) {
        	
        	plugin.copyPreRow();//复制上一行数据
            e.preventDefault();
            plugin.setActiveCell($(this));
            plugin.editCell();//选中编辑
            
        };

        plugin.renderColumns = function () {
        	//加载表头
            var $thead = $("thead", plugin.$el);
            var tr = document.createElement("tr");
            var trEmpty = document.createElement("tr");
            //固定表头
            $(tr).attr("style","left:0;position:fixed;z-index:3;border-top:1px solid #DDDDDD;");
            //border-right-style:none;border-left-style:none;border-top-style:none;border-bottom-style:none
            if (plugin.config.selectable) {
                var th = $("<th class='selectable'><input type='checkbox' /></th>")[0];
                tr.appendChild(th);
            }
            
            _.each(plugin.columns, function (column) {
                var th = document.createElement("th");
                
                var div = document.createElement("div");

                var styleStr="";
                if(column.hide){
                	styleStr="display:none;";
                	$(th).data("hide", true);
                }
                
                if(column.width && !column.hide){
                	styleStr+="width:"+(parseFloat(column.width.replace("px","")))+"px;";
                }
                
                if (plugin.config["sortable"]) {
                    th.className = "sensei-grid-sortable";
                }

                if((page=="vouch" || page=="vouchKind") && column.name.indexOf("debit_name")!=-1){
                	$(th).addClass("debit-head-bg-19");
                	//th.appendChild(div);
                	 $(th).attr("style","text-align:center;vertical-align:middle;border-right:2px solid #333333;"+styleStr);
                }else if((page=="vouch" || page=="vouchKind") && column.name.indexOf("credit_name")!=-1){
                	$(th).addClass("credit-head-bg-19");
                	//th.appendChild(div);
                	 $(th).attr("style","text-align:center;vertical-align:middle;"+styleStr);
                }else{
                	 $(div).html(column.display ? column.display : column.name);
                     th.appendChild(div);
                     $(th).attr("style","background:#FFFFDF;text-align:center;vertical-align:middle;"+styleStr);
                }
              
              
                $(th).data("name", column.name);
                $(th).data("type", column.type || "string");
                $(th).data("editor", column.editor || "BasicEditor");
                
                if (column.editorProps) {
                    plugin.editorProps[column.name] = column.editorProps;
                }

                trEmpty.appendChild(th);
                tr.appendChild(th);
                
            });
           
            // if (!_.isEmpty(plugin.rowElements)) {
            //   var th = document.createElement("th");
            //   th.innerHTML = "";
            //   tr.appendChild(th);
            // }
            if(page=="vouch" || page=="vouchKind"){
            	//因为凭证页面有双表头，所以高度要高
   		     	$(trEmpty).attr("style","position:relative;z-index:2;border:1px solid #DDDDDD;height:55px;");
    		}else{
    			//现金流量、辅助核算
    			$(trEmpty).attr("style","position:relative;z-index:2;border:1px solid #DDDDDD;height:22px");
    		}
       
            
            $thead.append(tr);
            $thead.append(trEmpty);
          // $(trEmpty).css("height",$(tr).css("height")); 
          //  console.log("$thead",$thead.html());
        };
        
        /**
         * Render grid data
         * @param data Optional array of grid data, it will override existing data array
         */
        plugin.renderData = function (data) {
        	
            var $tbody = $(".sensei-grid-tbody", plugin.$el);
            
            // override existing data array
            if (data) {
                plugin.data = data;
            }

            // remove existing content from tbody
            $tbody.html(null);
        	_.each(plugin.data, function (item) {
        		if(page=="vouchKind" && kindCode=="01" && (!item["subj_name"] || item["subj_name"]=="")){
        			//只渲染财务会计,有科目的分录
            		return true;
            	}else if(page=="vouchKind" && kindCode=="02" && (!item["budg_subj_name"] || item["budg_subj_name"]=="")){
        			//只渲染预算会计,有科目的分录
            		return true;
            	}
        		
        		if(item["id"] || item["budg_id"]){
	                 var tr = plugin.renderRow(item, true);
	                 $(tr).attr("style","position:relative;z-index:1;");
	                 $tbody.append(tr);
        		}
        		
            });   
          
            

            if (plugin.config.emptyRow) {
                // render empty row at the end of table
            	
            	for(var i=0;i<20;i++){
            		
		            var tr = plugin.renderRow(null, false);
		         /* if (page=="vouch" || page=="vouchKind") {
		                customStyle($(tr.cells[5]),0);//借方金额样式
		                customStyle($(tr.cells[6]),0);//贷方金额样式
		                customStyle($(tr.cells[19]),0);//借方金额样式
		                customStyle($(tr.cells[20]),0);//贷方金额样式
		                
		            }*/
		            //不是凭证页面
		            if(page!="vouch" && page!="vouchKind"){
		            	tr.cells[1].innerText=getRowIndex();//id
		            	 $tbody.append(tr);
            			break;
            		}
		            $tbody.append(tr);
            	}
	             
            }

            // check if grid is empty and if empty grid message is set
            if (_.isEmpty(plugin.data) && plugin.config.emptyGridMessage) {
                // render row with empty grid message
                var emptyCell = $("<td colspan=999>").text(plugin.config.emptyGridMessage);
                var emptyRow = $("<tr>").append(emptyCell);
                $tbody.html(emptyRow);
            }
           // console.log("$tbody.html();",$tbody.html());
        };

        plugin.renderRow = function (item, saved, dirty) {
            var tr = document.createElement("tr");
           
            if (!saved) {
                tr.className = "sensei-grid-empty-row";
            }

            if (dirty) {
                tr.className = "sensei-grid-dirty-row";
            }

            if (plugin.config.selectable) {
                var $td = $("<td></td>");
                //var td = document.createElement("td");
                if (saved) {
                    var $checkbox = $("<input type='checkbox' />");
                    $td.append($checkbox);
                }
                $td.prop("class", "selectable");
                tr.appendChild($td[0]);
            }
           
            _.each(plugin.columns, function (column) {
            		
                var td = document.createElement("td");
                var div = document.createElement("div");
                var cname=column.name;
                if (_.has(item, cname)) {
                	//数据是否包含列
            		if(cname=="money"){
                		//格式化两位小数
                		//toDecimal(item[cname]);
                		$(div).text(formatNumber(item[cname],2,1));
                	}else if(cname.indexOf("debit_name")!=-1){
                		//处理借方
 	                   customStyle($(td),item[cname.replace("_name","")],$(div));//借方金额样式addClass
                	}else if(cname.indexOf("credit_name")!=-1){
                		//处理贷方
                		customStyle($(td),item[cname.replace("_name","")],$(div));//贷方金额样式addClass
                	}else if(cname=="id" || cname=="budg_id"){
                		if(page=="vouchKind"){
                			//预算&会计窗口取主页面的id
                			$(div).text(item[cname]);
                			if(item[cname]){
                				rowIndex=parseInt(item[cname]);
                			}
                			
                		}else{
                			//主页面自动生成id
                    		$(div).text(getRowIndex());
                		}
                		
                	}else{
                		
                		if (column.allowHTML && column.allowHTML === true) {
                            $(div).html(item[cname]);
                        }else {
                            $(div).text(item[cname]);
                        }
                	}
                  
                }else if(page=="vouch"){
                	//没有找到数据
                	if(cname=="debit_name" || cname=="credit_name"){
                		customStyle($(td),0);//金额样式addClass
                	}
                	
                	if(is_budg==1 && (cname=="budg_debit_name" || cname=="budg_credit_name")){
                		customStyle($(td),0);//金额样式addClass
                	}
                	
                	if(cname=="id"){
                		$(div).text(getRowIndex());
                	}
                	
                	if(is_budg==1 && cname=="budg_id"){
                		$(div).text(getRowIndex());
                	}
                	
                }else if(page=="vouchKind"){
                	//没有找到数据
                	if(kindCode=="01" && (cname=="debit_name" || cname=="credit_name")){
                		customStyle($(td),0);//金额样式addClass
                	}
                	
                	if(kindCode=="02" && (cname=="budg_debit_name" || cname=="budg_credit_name")){
                		customStyle($(td),0);//金额样式addClass
                	}
                	
                	if(cname=="id" || cname=="budg_id"){
                		//主页面自动生成id
                		$(div).text(getRowIndex());
                	}
                }
                	
                
                if(page=="vouch" && is_budg==1 && item){
            		//两边摘要同步
                	if(cname=="budg_summary"){
                		
                		if(!item["summary"] || item["summary"]==""){
                			//没有财务会计摘要，说明没有财务会计分录，预算摘要列要清空
                			$(div).text("");
                		}else{
                			//预算会计的摘要与财务会计的摘要一致，预算会计的摘要为空
                    		$(div).text((item["budg_summary"]==item["summary"])?"":item["budg_summary"]);
                		}
                		
                	}else if(cname=="summary"){
                		//只有预算会计，没有财务会计，财务摘要为空取预算会计的摘要
                		if(!item["summary"] || item["summary"]==""){
                			$(div).text(item["budg_summary"]);
                		}
                		
                	}
                	
          		}
                
                if(page=="vouchKind" && kindCode=="02" && cname=="budg_summary" && item){
            		//预算会计没有摘要默认取财务会计摘要
                	$(div).text((item["budg_summary"] && item["budg_summary"]!="")?item["budg_summary"]:item["summary"]);
          		}
                
                //单元格加样式
                tdDivStyle(column,$(td),$(div));
               
                $(td).data("allowHTML", column.allowHTML);
                $(td).data("column", cname);
                $(td).data("type", column.type || "string");
                $(td).data("editor", column.editor || "BasicEditor");
                $(td).data("saved", saved);
                if(column.hide==true){
                	$(td).data("hide", true);
                }
                
                td.appendChild(div);
                
                if(item && page=="vouch" && cname=="subj_name" && is_budg==2){
            		//分屏式，显示科目标记
        			setSubjFlag(td,item["subj_code"],"add");
            	}else if(item && page=="vouchKind" && cname=="subj_name"){
            		//财务会计，显示科目标记
        			setSubjFlag(td,item["subj_code"],"add");
            	}else if(item && page=="vouchKind" && cname=="budg_subj_name"){
            		//预算会计，显示科目标记
        			setSubjFlag(td,item["budg_subj_code"],"add");
            	}
                
                tr.appendChild(td);
            
                 
            });

            if (!_.isEmpty(plugin.rowElements)) {
                // append row actions to tr element
                _.each(plugin.rowElements, function (rowEl, name) {
                    var td = document.createElement("td");
                    td.innerHTML = rowEl;
                    $(td).data("action", true);
                    $(td).data("action-name", name);
                    $(td).addClass("row-action");
                    
                    // if row action has defined trigger event, bind it to $(td) el
                    var rowAction = plugin.rowActions[name];
                    if (rowAction.triggerEvent && rowAction.triggerEvent.event && rowAction.triggerEvent.selector) {
                        $(td).on(rowAction.triggerEvent.event, rowAction.triggerEvent.selector, {$activeCell: $(td)}, rowAction.trigger);
                    }

                    tr.appendChild(td);
                });
            }
           // console.log("$tr",$(tr).html());
            return tr;
        };

        plugin.renderBaseTable = function () {
            var table = document.createElement("table");
            var thead = document.createElement("thead");
            var tbody = document.createElement("tbody");
           // var tfoot = document.createElement("tfoot");

            tbody.className = "sensei-grid-tbody";
            thead.className = "sensei-grid-thead";
         //   tfoot.className = "sensei-grid-thead";
            table.className = plugin.config.tableClass;
            
            table.appendChild(thead);
            table.appendChild(tbody);
         //   table.appendChild(tfoot);

            plugin.$el.html($("<div class='sensei-grid-table-wrapper'>").html(table));
           // console.log("plugin.$el.html",plugin.$el.html());
            plugin.$el.attr("tabindex", -1);

            if (plugin.config.toolbar) {
                plugin.$el.append($("<div class='sensei-grid-toolbar'>").text("Empty toolbar."));
            }
        };
        
        //按键时，隐藏列跳过
        plugin.hideMove =function (direction){
        	for(var i=0;i<100;i++){
        		var $nextCell = plugin.getActiveCell();
                if($nextCell.data("hide")){
                	plugin.move(direction);
                }else if($nextCell.index()==0){
                	plugin.move(direction);
                }else{
                	break;
                }
        	}
        	
        };
        
        //进入编辑状态，复制上一行数据
        plugin.copyPreRow =function (){
        	
        	if(plugin.config.readonly){
        		return;
        	}
        	
        	var $activeCell = plugin.getActiveCell();
        	var $preRow = $activeCell.parent("tr").prev();//上一行
        	if($preRow.length==0){
        		return;
        	}
        	
        	var columnName=$activeCell.data("column");
        	//摘要、发生日期、到期日期、合同编号、编辑状态复制上一行数据
        	if(columnName!="summary" && columnName!="budg_summary" && columnName!="occur_date" && columnName!="due_date" && columnName!="con_no"){
        		return;
        	}
        	
        	var cellIndex=$activeCell[0].cellIndex;//当前cell索引
        	var preText=$($preRow[0].cells[cellIndex]).find("div").text();
        	//console.log(cellIndex,$($preRow[0].cells[cellIndex]).find("div").text());
        	if($activeCell.find("div").text()=="" && preText!=""){
        		if((page=="vouch" || page=="vouchKind") && parent.paraList["036"]==1 && (cellIndex==3 || cellIndex==17)){
            		//摘要列，判断是否包含[]，如果包含去掉[]里面的内容
            		if(preText.substring(0,1)=="[" && preText.indexOf("]")!=1){
            			preText=preText.substring(preText.indexOf("]")+1,preText.length);
            		}
            	}
        		
    			$activeCell.find("div").text(preText);
    			var $tr=$activeCell.parent("tr");
    			if ($tr.hasClass("sensei-grid-empty-row")) {
                     $tr.removeClass("sensei-grid-empty-row").addClass("sensei-grid-dirty-row");
                     plugin.assureEmptyRow();
                 }
            }
        	
        };
        
        plugin.sumMoney = function () {
        	sumMoney();//合计金额
        };
        
        plugin.init = function (data, columns, options, name) {

            plugin.config = $.extend({}, defaults, options);
            plugin.data = data;
            plugin.columns = columns;
            plugin.name = name;
            plugin.$el = $(this);
            plugin.editors = {};
            plugin.rowActions = {};
            plugin.edits = [];
            plugin.editPointer = -1;

            return plugin;
        };
        
        return plugin.init(data, columns, options, name);
    };
})(jQuery);


//行号累计
var rowIndex=1000;//从1000开始，自动凭证明细id用的是分录行号，避免id重复
function getRowIndex(){
	rowIndex=rowIndex+1;
	return rowIndex;
}

//单元格样式
function tdDivStyle(column,$td,$div){
	
	 var divStyleStr="";
     var tdStyleStr="";
     // check if nowrap needs to be disabled
     if (column.wrap === true) {
         //$(td).css("white-space", "normal");
     	//styleStr="white-space:normal;";
     	divStyleStr="white-space:normal;word-break: break-all;word-wrap: break-word;";
     	
     }
     
     if ((page=="vouch" || page=="vouchKind") && column.name.indexOf("debit_name")!=-1) {
     	//divStyleStr+="font-weight:bold;";//border:1px solid #DDDDDD;padding-right:0px;
     	tdStyleStr+="border-right:2px solid #333333;";
     }
    
    
     if(column.align){
     	tdStyleStr+="position:relative;text-align:"+column.align+";vertical-align:middle;";
     }
     
     if(column.width && !column.hide){
	     tdStyleStr+="width:"+(parseFloat(column.width))+"px;";
     }
     if(column.hide){
     	tdStyleStr+="display:none;";
     }
     
     if($div && divStyleStr!="")$div.attr("style",divStyleStr);
     if($td && tdStyleStr!="")$td.attr("style",tdStyleStr);
}

//强制保留两位小数
function toDecimal(x) {    
    var f = parseFloat(x);    
    if (isNaN(f)) {
        return false;
    }
    
    var f = Math.round(x*100)/100;
    var s = f.toString();    
    var rs = s.indexOf('.');    
    if (rs < 0) {    
        rs = s.length;    
        s += '.';    
    }    
    while (s.length <= rs + 2) {    
        s += '0';    
    }    
    return s;    
};    

//全选div的内容
function selectText(text) {
    if (document.body.createTextRange) {
        var range = document.body.createTextRange();
        range.moveToElementText(text);
        range.select();
    } else if (window.getSelection) {
        var selection = window.getSelection();
        var range = document.createRange();
        range.selectNodeContents(text);
        selection.removeAllRanges();
        selection.addRange(range);
        /*if(selection.setBaseAndExtent){
            selection.setBaseAndExtent(text, 0, text, 1);
        }*/
    } 
}

//分屏式，显示科目标记
function setSubjFlag(td,subjCode){
	
	if(parent.copyNature=="02"){
		return;
	}
	
	var span=$(td).find("span");
	if(span.length>0){
		
		$(span[0]).removeClass("accsubjflag19");
		$(span[0]).removeClass("budgsubjflag19");
		$(span[1]).text("");
		
	}else{
		var spanFlag = document.createElement("span");
		var spanText = document.createElement("span");
		$(spanText).addClass("subjflagText19");
		td.appendChild(spanFlag);
		td.appendChild(spanText);
	}
	
	span=$(td).find("span");
	if(subjCode){
		var subjAttr=getSubjAttr(subjCode);
		if(subjAttr.kind_code=="02"){
			//预算会计科目
			$(span[0]).addClass("budgsubjflag19")
			$(span[1]).text("预");
		}else{
			$(span[0]).addClass("accsubjflag19")
			$(span[1]).text("财");
		}
	}
	
}


