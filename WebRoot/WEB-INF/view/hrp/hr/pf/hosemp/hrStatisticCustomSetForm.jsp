<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>简单统计表设置</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,tree,grid,sortable,dialog" name="plugins" />
        </jsp:include>
        <style>
            .grid-main {
                box-sizing: border-box;
                position: relative;
              
            }

            .grid-main>div {
                box-sizing: border-box;
                width: 47.5%;
                /* position: absolute; */
                float: left;
            }

            .grid-main .grid-left,
            .grid-main .grid-right {
                /* width: 380px; */
                  border: 1px solid #e0ecff;
            }

            .grid-main .grid-middle {
                width: 50px;
                text-align: center;
                padding-top: 50px;
            }
         .button1{
             box-sizing: border-box;
   			 height: 26px;
    		 padding-left: 10px;
    		 padding-right: 10px;
   			 border: 1px solid #aecaf0;
        	 background: #e5edf4;
         }
             .button2{
             box-sizing: border-box;
   			 height: 26px;
    		 padding-left: 10px;
    		 padding-right: 10px;
   			 border: 1px solid #aecaf0;
        	 background: #e5edf4;
         }
             .button3 {
            box-sizing: border-box;
   			 height: 26px;
    		 padding-left: 10px;
    		 padding-right: 10px;
   			 border: 1px solid #aecaf0;
        	 background: #e5edf4;
         }
             .button4{
            box-sizing: border-box;
   			 height: 26px;
    		 padding-left: 10px;
    		 padding-right: 10px;
   			 border: 1px solid #aecaf0;
        	 background: #e5edf4;
         }
            .button5{
            box-sizing: border-box;
   			 height: 26px;
    		 padding-left: 10px;
    		 padding-right: 10px;
   			 border: 1px solid #aecaf0;
        	 background: #e5edf4;
         }
            .button6{
            box-sizing: border-box;
   			 height: 26px;
    		 padding-left: 10px;
    		 padding-right: 10px;
   			 border: 1px solid #aecaf0;
        	 background: #e5edf4;
         }
            .grid-middle a {
                text-decoration: none;
                user-select: none;
                display: block;
                margin-bottom: 20px;
            }
        </style>
        <script>
            var tree,
                meta_grid,
                target_grid,
                query_grid,
                tab_code,
                tab_name;
    		var store_type_code = '${store_type_code}';
            $(function () {
                initTree();
                initGrid();
                initEvent();
                reloadTargetGrid();
                reloadQueryGrid();
            });
            function fixWidth(percent)
            {
            return document.body.clientWidth * percent ; //这里你可以自己做调整
            }
            function initTree() {
                tree = $("#tree").etTree({
                    async: {
                        enable: true,
                        url: '../queryHrStoreTabStruc.do?isCheck=false',
                    },
                    callback: {
                        onClick: function (e, id, node) {
                            query_grid.getColumn('col_name').editor.url = '../queryHrColStruc.do?isCheck=false&store_type_code=${store_type_code}&tab_code=' + node.id;
                            query_grid.getColumn('field_col_name').editor.dataModel.url = '../queryHrFiiedDataDicByTabCol.do?isCheck=false&tab_code=' + node.id,
                            tab_code = node.id;
                            tab_name = node.name;
                            reloadMetaGrid(node.id);
                            // reloadTargetGrid();
                        }
                    }
                });
            }

            function initGrid() {
                var meta_obj = {
                    columns: [
                        { display: '数据列名称', name: 'col_name', width:fixWidth(0.4) },
                        { display: '数据列编码', name: 'col_code', width:fixWidth(0.2) },
                        { display: '备注', name: 'note', width:fixWidth(0.4) },
                    ],
                    height: 320,
                   
                }

                var target_obj = {
                    columns: [
                        { display: '数据表名称', name: 'tab_name', width:fixWidth(0.4) },
                        { display: '数据列名称', name: 'col_name', width:fixWidth(0.2)},
                        { display: '备注', name: 'note', width:fixWidth(0.4)},
                    ],
                    height: 320,
                
                }

                meta_grid = $("#meta_grid").etSortable(meta_obj);
                target_grid = $("#target_grid").etSortable(target_obj);

                // 
                var query_columns = [
                    { display: '左括号', align: 'center', width: 80, name: 'l_bracket' },
                    {
                        display: '数据项编码',
                        align: 'center',
                        name: 'col_name',
                        width: 200,
                        relyOn: [{
                            field: 'tab_code',
                            key: 'tab_code'
                        }],
                        editor: {
                            type: 'select',
                            keyField: 'col_code',
                            url: '../queryHrColStruc.do?isCheck=false&store_type_code=${store_type_code}' ,
                            change: function (rowwData, cellData) {
                                query_grid.updateRow(cellData.rowIndx, {
                                    field_col_code: '',
                                    field_col_name: '',
                                    field_col_name_type: cellData.selected.type
                                })
                            }
                        },
                        render: function (ui) {
                            if (ui.cellData) {
                                return ui.rowData.tab_name + '.' + ui.cellData;
                            }
                        }
                    },
                    {
                        display: '条件',
                        align: "center",
                        width: 120,
                        name: "con_sign_note",
                        editor: {
                            type: 'select',
                            keyField: 'con_sign_code',
                            url: '../queryHrConSignSelect.do?isCheck=false'
                        }
                    },
                    {
                        display: '数据项值',
                        align: "center",
                        width: 120,
                        name: "field_col_name",
                        editable: function (ui) {
                            if (ui.rowData && ui.rowData.col_name) {
                                return true;
                            }
                            return false;
                        },
                        relyOn: [{
                            field: 'tab_code',
                            key: 'tab_code'
                        }, {
                            field: 'col_code',
                            key: 'col_code'
                        }],
                        editor: {
                        	//console.log('tab_code');
                            // 用于动态切换改编辑框类型的属性
                            dynamic: true,
                            keyField: 'field_col_code',
                            columns: [{
                                display: '代码项编码',
                                align: 'center',
                                width: 120,
                                name: 'field_col_code'
                            }, {
                                display: '代码项名称',
                                align: 'center',
                                width: 120,
                                name: 'field_col_name'
                            }],
                            dataModel: {
                                url: '../queryHrFiiedDataDicByTabCol.do?isCheck=false',
                            },
                            width: 500,
                            height: 200
                        },
                        render : function(rowdata, rowIndx,value) {
							if(rowdata.data[rowdata.rowIndx].col_value != null &&rowdata.data[rowdata.rowIndx].field_col_name ==null){
								//rowdata.data[rowdata.rowIndx].field_col_name=rowdata.data[rowdata.rowIndx].col_value
								return rowdata.data[rowdata.rowIndx].field_col_name;
							}else{
								return rowdata.data[rowdata.rowIndx].field_col_name;
							}
						}
                    },
                    { display: '右括号', align: 'center', width: 80, name: 'r_bracket' },
                    {
                        display: "连接符",
                        align: "center",
                        width: 120,
                        name: "join_sign_note",
                        editor: {
                            type: 'select',
                            keyField: 'join_sign_code',
                            url: '../queryHrJoinSignSelect.do?isCheck=false'
                        }
                    }
                ]

                var query_obj = {
                    editable: true,
                    height: '100%-50',
                    width: '100%-15',
                    inWindowHeight: true,
                    checkbox: true,
                    columns: query_columns,
                    virtualY: false,
                    pageModel: false,
                    showBottom: false,
                    cellSelect: function (event, ui) {
                        if (ui.dataIndx === 'field_col_name') {
                            var type = ui.rowData.field_col_name_type || 'textbox';
                            if (type === 'select') {
                                type = 'grid'
                            }
                            console.log(type)
                            ui.column.editor.dynamic = type;
                        }
                    },
                }
                query_grid = $("#query_grid").etGrid(query_obj);
                
                //queryGridAddRow();
                
            };
            
            function queryGridAddRow () {
            	var selectedNode = tree.getSelectedNodes();
            	if (selectedNode.length === 0) {
            		$.etDialog.error('请选择左边树')
            		return;
            	}
            	query_grid.addRow({
            		tab_code: selectedNode[0].id,
            		tab_name: selectedNode[0].name
            	});
            }

            function initEvent() {
                $("#save").click(function () {
                    var gridData = target_grid.getData();
                    var conditionData = query_grid.getAllData();
                    var statistic_code = $("#statistic_code").val();
                    var statistic_name = $("#statistic_name").val();
                    //console.log(gridData);
                    if(gridData.length==0){
                    	$.etDialog.error("请选择数据列!");
						return;
                    }
                    
                    var newConditionData = [];
                    var errorMsg = '';
                    
              /*       if(conditionData){
                    	
	                    $.each(conditionData,function(index,obj){
	                    	
	                    	if(obj.col_name == ''){//数据项编码为空视为空行
	                    		return ;
	                    	}
	                    	if(index+1>1){
	                    	if(obj.l_bracket != '(' && obj.r_bracket == ')'){
	                    		
	                    		errorMsg += '第' + (index+1) + '行左括号必须为:<span style="color:red">(</span><br/>'
	                    	}
	                    	
	                    	if(obj.l_bracket == '(' &&obj.r_bracket != ')'){
	                    		errorMsg += '第' + (index+1) + '行右括号必须为:<span style="color:red">)</span><br/>'
	                    	}
	                    	}
	                    	newConditionData.push(obj);//过滤掉空行后的数据
	                    });
	                    		
	                    if(errorMsg != ''){
	                    	$.etDialog.error(errorMsg);
							return;
	                    }	
                    } */		
                   var  l_bracket=0;
                    var  r_bracket=0;
                    $(conditionData).each(function( index,item){
                    	if(item.col_name == ''){//数据项编码为空视为空行
                    		return ;
                    	}
                    	/*var ind=	index+1;
                    	if(index<conditionData.length-1){
                    		
                    		if(item.join_sign_code ==null || item.join_sign_code == ""){
                       			errorMsg += '第' + (ind ) + '连接符不能为空<br/>';
                       		}	
                    	} 
                    	if(index ==conditionData.length-1){
                    		if(item.join_sign_code !=undefined){
                       			errorMsg += '第' + (ind) + '行不能存在连接符<br/>';
                       		}	
                    	}
                     
                    		if(item.l_bracket !=null && item.l_bracket != ""){
                    			l_bracket=l_bracket+1;
                       			
                       		} 	
                    		if(item.r_bracket !=null && item.r_bracket != ""){
                    			r_bracket=r_bracket+1;
                       		}
                    		if(l_bracket>r_bracket){
                    			errorMsg += '缺失右括号<br/>';
                    		}
                    		if(l_bracket<r_bracket){
                    			errorMsg += '缺失左括号<br/>';
                    		}*/
                    		newConditionData.push(item);
                    });
                    
                    if(errorMsg != ''){
                    	 $.etDialog.error(errorMsg);
                         return;
                    }
                    
                    var url = 'saveHrStatisticCustomSet.do?isCheck=false';
                    var data = {
                        statistic_code: statistic_code,
                        statistic_name: statistic_name,
                        data: JSON.stringify(gridData),
                        conditionData: JSON.stringify(newConditionData)
                    };
                    // 获取数据
                    ajaxPostData({
                        url: url,
                        data: data,
                        success: function (res) {
                            $("#statistic_code").val(res.statistic_code);
                        },
                        delayCallback: true
                    });
                });

                $("#close").on('click', function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                });

                $("#btn_add").click(function () {
                	queryGridAddRow();
                });

                $("#btn_del").click(function () {
                    query_grid.deleteSelectedRows();
                })


            }

            function reloadMetaGrid(id) {
                var url = "../queryHrColStrucGrid.do?isCheck=false";
                var data = { 'tab_code': id };
                // 获取数据
                $.post(url, data, function (res) {
                    meta_grid.reload(res.Rows);
                }, 'json');
            }

            function reloadTargetGrid() {
                var url = "queryHrStatisticSetTab.do?isCheck=false";
                var data = { statistic_code: '${statistic_code}' };
                // 获取数据
                $.post(url, data, function (res) {
                    target_grid.reload(res.Rows)
                }, 'json');
            }
            
            function reloadQueryGrid(){
            	var url = "queryHrStatisticSetCondition.do?isCheck=false";
                var data = { statistic_code: '${statistic_code}' ,store_type_code :'${store_type_code}'};
                // 获取数据
                $.post(url, data, function (res) {
                    //target_grid.reload(res.Rows)
                    console.log(res.Rows)
                    query_grid.option('dataModel.data', res.Rows);
                    query_grid.refreshView();
                }, 'json');
            }

            // 右移选中
            function moveToRight() {
                var metaData = meta_grid.getChecked();
                var targetData = [];
                var treeNode = tree.getSelectedNodes()[0];
                $(metaData.checkedData).each(function () {
                    if (uniqueRow(treeNode.id, this.col_code)) {
                        targetData.push({
                            tab_name: treeNode.name,
                            tab_code: treeNode.id,
                            col_name: this.col_name,
                            col_code: this.col_code,
                            note: this.note
                        });
                    } else {
                        console.warn(treeNode.name + this.col_code + '重复');
                    }
                });
                target_grid.addRows(targetData);
            }

            // 右移所有
            function moveToRightAll() {
                var metaData = meta_grid.getData();
                var targetData = [];
                var treeNode = tree.getSelectedNodes()[0];
                $(metaData).each(function () {
                    if (uniqueRow(treeNode.id, this.col_code)) {
                        targetData.push({
                            tab_name: treeNode.name,
                            tab_code: treeNode.id,
                            col_name: this.col_name,
                            col_code: this.col_code,
                            note: this.note
                        });
                    } else {
                        console.warn(treeNode.name + this.col_code + '重复');
                    }
                });
                target_grid.addRows(targetData);
            }

            // 左移动(删除右侧列表选中数据)
            function moveToLeft() {
                target_grid.deleteCheckedRows();
            }

            // 左移动(删除右侧列表所有数据)
            function moveToLeftAll() {
                target_grid.deleteAllRows();
            }

            // 向上移动行
            function prevRow() {
                target_grid.prevMove();
            }

            // 向下移动行
            function nextRow() {
                target_grid.nextMove();
            }

            // 验证重复数据
            function uniqueRow(tab_code, col_code) {
                var rowData = target_grid.getData();
                for (let i = 0; i < rowData.length; i++) {
                    const data = rowData[i];
                    if (data.tab_code === tab_code && data.col_code === col_code) {
                        return false;
                    }
                }
                return true;
            }

        </script>
    </head>

    <body>
        <div class="container">
            <div class="left">
                <div class="search-form">
                    <label for="">快速定位：</label>
                    <input type="text" class="text-input" id="search_input">
                </div>
                <div id="tree"></div>
            </div>
            <div class="center">
                <table class="table-layout">
                    <tr>
                        <td >统计表编号：</td>
                        <td >
                            <input type="text" id="statistic_code" value="${statistic_code == null ? '自动生成' :statistic_code}" disabled="disabled" name="statistic_code">
                        </td>
                        <td class="label">统计表名称：</td>
                        <td class="ipt">
                            <input type="text" id="statistic_name" name="statistic_name" value="${statistic_name }">
                        </td>
                        <td class="ipt">
                            <button id="save">保存</button>
                            <button id="close">关闭</button>
                        </td>
                    </tr>
                </table>
                <div class="grid-main clearfix">
                    <div class="grid-left">
                        <div id="meta_grid" ></div>
                    </div>
                    <div class="grid-middle">
                        <a href="javascript:;" onclick="moveToRight()"><button class="button1">></button></a>
                        <a href="javascript:;" onclick="moveToRightAll()"><button class="button2">>></button></a>
                        <a href="javascript:;" onclick="moveToLeft()">
                            <button class="button3"><</button></a>
                                <a href="javascript:;" onclick="moveToLeftAll()">
                                     <button class="button4"><<</button></a>
                                        <a href="javascript:;" onclick="prevRow()">
                                           <button class="button5">↑</button>
                                        </a>
                                        <a href="javascript:;" onclick="nextRow()"> <button class="button6">↓</button></a>
                    </div>
                    <div class="grid-right">
                        <div id="target_grid"></div>
                    </div>
                </div>

                <table class="table-layout">
                    <tr>
                        <td>
                            <button id="btn_add">添加条件</button>
                            <button id="btn_del">删除条件</button>
                        </td>
                    </tr>
                </table>
                <div id="query_grid">

                </div>
            </div>
        </div>
    </body>

    </html>