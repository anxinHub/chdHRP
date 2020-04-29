<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;"> 
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">

    var grid;

    var rightgrid;

    var gridManager = null;

    var rightgridManager = null;

    var userUpdateStr;

    var para = "";

    var sumPara = "";

    var wherePara = "";

    var tree_wage_code = "";

    var tree_wage_name = "";

    var scheme_code = "";

    var scheme_name = "";
    
    var acc_time;

    var tree;

    var is_DataCanSave = false;    // 数据是否能保存   

    var column = "";

    var setting = {

        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0

            },
            key: {
                children: "nodes"
            }
        },
        check: {
            enable: false
        },
        treeNode: {
            open: true
        },
        callback: {
            onClick: zTreeOnClick
        }

    };
    
    $(function() {
    	
    	$("#layout1").ligerLayout({
            leftWidth: 350,
            centerWidth: 750,
            onLeftToggle: function(isColl) {
                grid._onResize();
            },
            onEndResize: function(isColl) {
                grid._onResize()
            }
        });
    	
        loadDict(null);

        loadHead(null); //加载数据

        loadTree(null);

        $("#money1").ligerTextBox({
            width: 80
        });

        $("#money2").ligerTextBox({
            width: 80
        });
        
        $("#money3").ligerTextBox({
            width: 80
        });

        $("#money4").ligerTextBox({
            width: 80
        });
        
        $("#money5").ligerTextBox({
            width: 80
        });
    
        $("#emp_name").ligerTextBox({
            width: 183
        });

        $("#dept_code").ligerTextBox({
            width: 110
        });
        
        $("#emp_pay").ligerTextBox({
            width: 110
        });
        
        $("#note").ligerTextBox({
            width: 160
        });
        $("#id_number").ligerTextBox({
            width: 160
        });

        /* $("#acc_time").ligerTextBox({
            cancelable: false
        }); */
        
        $("#wage_item").ligerTextBox({
            width: 70
        });
        
        $("#wage_item1").ligerTextBox({
            width: 70
        });
        
        $("#wage_item2").ligerTextBox({
            width: 70
        });
        
        $("#wage_item3").ligerTextBox({
            width: 70
        });
        
        $("#wage_item4").ligerTextBox({
            width: 70
        });
        
        $("#wage_code").bind("change", function() {

            var fromData = {

                wage_code: liger.get("wage_code").getValue(),
                scheme_type_code: '01'

            }

            autocomplete("#scheme_name", "../queryAccWageScheme.do?isCheck=false", "id", "text", true, true, fromData);
        })

    });

    // 左侧树单击节点
	function zTreeOnClick(event, treeId, treeNode) {
		setTimeout(function() {
			if (treeNode.pId == 0) {
				tree_wage_code = treeNode.id;
				scheme_code = "";
				scheme_name = "";
				tree_wage_name = treeNode.name;
			} else {
				tree_wage_code = treeNode.pId;
				scheme_code = treeNode.id;
				scheme_name = treeNode.name
				tree_wage_name = treeNode.getParentNode().name.split(" ")[1];
			}

			if (grid.get("url")){ //检测它的URL值，若它为空则直接渲染(不含数据)，若有值则清空表格数据再重新渲染
				grid.set("url", "");
				$("#maingrid").find("tbody").children().remove();
				loadHead();
				query();
			} else {
				grid.unbind("AfterEdit", f_onAfterEdit);
				grid.unbind('BeforeEdit', f_onBeforeEdit);
				loadHead();
				query();
			};
 
			var fromData = {
				'wage_code': tree_wage_code,
				'acc_year': acc_time.getValue().split(".")[0],
				'scheme_id': scheme_code
			}
			$(".l-grid1 .l-grid-header-inner").width("100%");
			autocomplete("#item_code", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
			autocomplete("#item_code1", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
			autocomplete("#item_code2", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
			autocomplete("#item_code3", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
			autocomplete("#item_code4", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
		}, 0)
	}

    // 右侧查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		var wage_id;
		var acc_times = acc_time.getValue();
		var acc_money = $("#money1").val();
		var acc_money1 = $("#money2").val();
		var acc_money2 = $("#money3").val();
		var acc_money3 = $("#money4").val();
		var acc_money4 = $("#money5").val();
		var item_code = liger.get("item_code").getValue();
		var item_code1 = liger.get("item_code1").getValue();
		var item_code2 = liger.get("item_code2").getValue();
		var item_code3 = liger.get("item_code3").getValue();
		var item_code4 = liger.get("item_code4").getValue();
		var attr_code = liger.get("attr_code").getValue();
		if (tree_wage_code != "") {
			wage_id = tree_wage_code;
		} else {
			$.ligerDialog.error('请选择工资套或者方案进行查询！');
			return;
		}
		if (acc_times == "") {
			$.ligerDialog.error('会计期间为必填项！');
			return;
		}
		grid.options.parms.push({ name: 'item_code', value: para });
		grid.options.parms.push({ name: 'attr_code', value: attr_code });
		grid.options.parms.push({ name: 'sum_item', value: sumPara });
		grid.options.parms.push({ name: 'column_item', value: sumPara });
		grid.options.parms.push({ name: 'wage_code', value: wage_id });
		grid.options.parms.push({ name: 'acc_year', value: acc_times.split(".")[0] });
		grid.options.parms.push({ name: 'acc_month', value: acc_times.split(".")[1] });
		grid.options.parms.push({ name: 'scheme_id', value: scheme_code });
		
		var kind_code = "";
		if(liger.get("emp_kind").getValue().split(";").length > 0 && liger.get("emp_kind").getValue() != ''){
			for ( var i = 0; i < liger.get("emp_kind").getValue().split(";").length; i++){
				kind_code += "'" + liger.get("emp_kind").getValue().split(";")[i] + "',";
			}
			grid.options.parms.push({ name: 'kind_code', value: kind_code.slice(0, kind_code.length-1) });
		}else{
			grid.options.parms.push({ name: 'kind_code', value: liger.get("emp_kind").getValue() });
		}
		
		grid.options.parms.push({ name: 'dept_code', value: liger.get("dept_code").getValue().split(".")[0] });
		grid.options.parms.push({ name: 'emp_code', value: $("#emp_code").val() });
		grid.options.parms.push({ name: 'pay_type_code', value: liger.get("emp_pay").getValue() }); 
		if(item_code!=""&&$("#wage_item").val()!=""&&acc_money!=""){
			grid.options.parms.push({
				name: 'item_name', 
				value: "awp." + item_code+" "+$("#wage_item").val()+" "+acc_money });
		}
		if(item_code1!=""&&$("#wage_item1").val()!=""&&acc_money1!=""){
			grid.options.parms.push({ 
				name: 'item_name1',
				value: "awp." + item_code1+" "+$("#wage_item1").val()+" "+acc_money1
			});
		}
		if(item_code2!=""&&$("#wage_item2").val()!=""&&acc_money2!=""){
			grid.options.parms.push({
				name: 'item_name2',
				value: "awp." + item_code2+" "+$("#wage_item2").val()+" "+acc_money2
			});
		}
		if(item_code3!=""&&$("#wage_item3").val()!=""&&acc_money3!=""){
			grid.options.parms.push({
				name: 'item_name3',
				value: "awp." + item_code3+" "+$("#wage_item3").val()+" "+acc_money3
			});
		}
		if(item_code4!=""&&$("#wage_item4").val()!=""&&acc_money4!=""){
			grid.options.parms.push({
				name: 'item_name4',
				value: "awp." + item_code4+" "+$("#wage_item4").val()+" "+acc_money4
			});
		}
		grid.options.parms.push({ name: 'note', value: $("#note").val() });
		grid.options.parms.push({ name: 'id_number', value: $("#id_number").val() });
		
		//加载查询条件
		grid.set("url", "queryAccWagePay.do");
		<%--grid.loadData(grid.where);--%>
		grid._onResize();
    }
    
	var result=0;
	
	function loadHead() {
		var columns = "";
		columns = columns + "{ display: '职工编码', name: 'EMP_CODE', align: 'left',frozen: true,width:'10%',"
        		+ "render:function(rowData){if(rowData.EMP_ID != 0){return '<a href=javascript:openWagePayUpdate('+ JSON.stringify(rowData)+')>' + rowData.EMP_CODE + '</a>';}else{return rowData.EMP_CODE}}},"
		        + "{ display: '职工名称', name: 'EMP_NAME', align: 'left',frozen: true,width:'10%'},"
		        + "{ display: '职工分类', name: 'KIND_NAME', align: 'left',frozen: true,width:'10%'},"
		        + "{ display: '部门编码', name: 'DEPT_CODE', align: 'left',frozen: true,width:'10%'},"
		        + "{ display: '部门名称', name: 'DEPT_NAME', align: 'left',frozen: true,width:'10%'},"
		        + "{ display: '身份证号', name: 'ID_NUMBER', align: 'left',frozen: true,width:'10%'},"
		        + "{ display: '发放方式', name: 'PAY_TYPE_NAME', align: 'left',frozen: true,width:'10%'},"
		        + "{ display: '备注', name: 'NOTE', align: 'left',frozen: false,editor: { type: 'text' }}";

        para = "";
        sumPara = "";
        $.ajax({
            type: "POST",
            url: "queryAccWagePayGrid.do?isCheck=false",
            data: {
                'scheme_id': scheme_code,
                "wage_code": tree_wage_code,
                "acc_year": acc_time.getValue().split(".")[0]
            },
            dataType: "json",
            async: false,
            success: function(data) {
                if (data.Rows.length > 0) {
                    $.each(data.Rows, function(i, v) {
                        columns = columns + ",{ display: '" + v.ITEM_NAME + "', name: '" + v.COLUMN_ITEM + "', align: 'right',editor: { type: 'text' },formatter: '###,##0.00'," +
                            "render:function(rowdata){ return formatNumber(rowdata." + v.COLUMN_ITEM + ",2,1)}},";
                        para += ",to_char(awp." + v.COLUMN_ITEM + ") as " + v.COLUMN_ITEM;
                        if (v.IS_SUM == 1) {
                            sumPara += ",to_char(sum(awp." + v.COLUMN_ITEM + ")) as " + v.COLUMN_ITEM;
                        } else {
                            sumPara += ",'0' as " + v.COLUMN_ITEM
                        }
                        if(result==0){
                        	column += "," + v.COLUMN_ITEM;
							if(data.Rows.length==i+1){
                        		result=1;
                        	}
                        }
                    });
                    columns = columns.substr(0, columns.length - 1);
                }
                grid = $("#maingrid").ligerGrid({
                    columns: eval("[" + columns + "]"),
                    dataAction: 'server',
                    dataType: 'server',
                    usePager: true,
                    url: '',
                    onAfterEdit: f_onAfterEdit,
                    onBeforeEdit: f_onBeforeEdit,
                    width: '100%',
                    height: '100%',
                    checkbox: true,
                    rownumbers: true,
                    minColumnWidth: 100,
                    clickToEdit: true,
                    selectRowButtonOnly: true,
                    delayLoad: true,
                    columnWidth: '15%',
                    enabledEdit: true,
                    checkBoxDisplay: isCheckDisplay,
                    toolbar: {
                        items: [
                            { text: '查询', id: 'search', click: query, icon: 'search' },
                            { line: true },
                            { text: '添加', id: 'add', click: itemclick, icon: 'add' },
                            { line: true },
                            { text: '批量修改', id: 'updateBatch', click: itemclick, icon: 'edit' },
                            { line: true },
                            { text: '删除', id: 'delete', click: itemclick, icon: 'delete' },
                            { line: true },
                            { text: '继承', id: 'extend', click: itemclick, icon: 'extend' },
                            { line: true },
                            { text: '计算', id: 'collect', click: itemclick, icon: 'collect' },
                            { line: true },
                            { text: '审核', id: 'audit', click: itemclick, icon: 'audit' },
                            { line: true },
                            { text: '取消审核', id: 'unaudit', click: itemclick, icon: 'unaudit' },
                            { line: true },
                            { text: '下载导入模板', id: 'downTemplate', click: itemclick, icon: 'down' },
                            { line: true },
                            { text: '导入', id: 'import', click: itemclick, icon: 'up' },
                            { line: true },
                            { text: '生成', id: 'init', click: itemclick, icon: 'initwage' },
                            { line: true },
                            { text: '打印', id:'print', click: printDate,icon:'print' },
                            { line:true } 
                        ]
                    }
                });

                gridManager = $("#maingrid").ligerGetGridManager();
            }
        });
    }
    
	// 工资录入修改
    function openWagePayUpdate(rowData){
		if(rowData.EMP_ID != 0){
			parent.$.ligerDialog.open({
	            url : 'hrp/acc/accwagepay/accWagePayUpdatePage.do?isCheck=false&wage_code='
	            		+rowData.WAGE_CODE+'&acc_year='+rowData.ACC_YEAR+'&acc_month='+rowData.ACC_MONTH
	            		+'&emp_id='+rowData.EMP_ID+'&emp_name='+rowData.EMP_NAME+'&dept_name='+rowData.DEPT_NAME,
	            width :$(parent.window).width(),
	            height : $(parent.window).height(),
	            title : '工资录入修改',
	            modal : true,
	            top:0,
	            showClose: true,
	            showToggle : false,
	            showMax : false,
	            showMin : true,//是否最小化
	            isResize : false,
	            parentframename:window.name
	        });
		}
    }

    function isCheckDisplay(rowdata) {
        //admin用户没有复选框
        // console.log(rowdata.checkboxDisplay ==false)

        if (rowdata.PAY_ID == null || rowdata.PAY_ID == 0)
            return false;

        return true;

    }

    // var rid = 0;

    //编辑前事件
    function f_onBeforeEdit(e) {
		if(e.record.PAY_STATE == '1' || (e.record.EMP_CODE == "合计" && e.record.PAY_ID == 0)){
			return false;
		}
        return true;

    }
    // 跳转到下一个单元格之前事件
    function f_onAfterEdit(e) {
    	
      var event1 = window.event;

         if(e.value != e.oldvalue) is_DataCanSave = true;     // 比较行数据是否更改过 若是 则保存

        //当鼠标点击到外部时(除了表格跟弹窗外)，数据保存
        if (is_DataCanSave) {

        var len = column.substring(1, column.length).split(",");

        var item = "";

        is_DataCanSave = false;

        var accTime = acc_time.getValue();

        if (accTime == "") {

            $.ligerDialog.error('会计期间为必填项！');

            return;

        }
        
		if(e.record.PAY_STATE == 1){
        	
 			$.ligerDialog.error('数据已经审核,不允许修改！');
 			
        	return;
        }

        $(len).each(function(index, value) {

            var v = e.record[len[index]];

            if (v == "UNDEFINED" || v == null|| v == "") {

                v = 0;
            }

            item += v + ";";
        });

        if(e.record.EMP_ID == null){
        	
        	return;
        }
        
        var formPara = {

            wage_code: tree_wage_code,

            emp_id: e.record.EMP_ID,

            acc_year: acc_time.getValue().split(".")[0],

            acc_month: acc_time.getValue().split(".")[1],
            
            note: e.record.note==null?"":enterSaveData.NOTE,

            pay_id: e.record.PAY_ID,

            item: item,

            item_column: column,

            add_type: '1'

        };

        ajaxJsonObjectByUrl("addAccWagePay.do", formPara, function(responseData) {

            /* if (responseData.state == "true") {

            	query();
            } */
        });

    }

    	//return false;
	}

    function itemclick(item) {
        if (item.id) {
            switch (item.id) {
                case "add": // 添加
                    if (tree_wage_code == "") {
                        $.ligerDialog.warn('请选择方案或者工资套进行添加操作！');
                        return;
                    }
                
                    parent.$.ligerDialog.open({
        	            url : 'hrp/acc/accwagepay/accWagePayAddPage.do?isCheck=false&wage_code=' + tree_wage_code + "&wage_name=" + tree_wage_name + "&year_month=" + $("#acc_time").val() + "&scheme_name=" + scheme_name + "&scheme_code=" + scheme_code,
        	            width :$(parent.window).width(),
        	            height : $(parent.window).height(),
        	            title : '工资录入',
        	            modal : true,
        	            top:0,
        	            showClose: true,
        	            showToggle : false,
        	            showMax : false,
        	            showMin : true,//是否最小化
        	            isResize : false,
        	            parentframename:window.name
        	        });

//                     parent.openFullDialog('hrp/acc/accwagepay/accWagePayAddPage.do?isCheck=false&wage_code=' + tree_wage_code + "&wage_name=" + tree_wage_name + "&year_month=" + $("#acc_time").val() + "&scheme_name=" + scheme_name + "&scheme_code=" + scheme_code, '工资录入', 0, 0, true, false);
                    return;
                case "init": // 生成
                    var year_month = acc_time.getValue();
                    if (year_month == "" || year_month == "0000.00" || year_month == null) {
                        $.ligerDialog.warn('期间为必填项！');
                        return;
                    }
                    if (tree_wage_code == "") {
                        $.ligerDialog.warn('请选择方案进行生成操作！');
                        return;
                    }
                    var acc_year = year_month.split(".")[0];
                    var acc_month = year_month.split(".")[1];
                    var ParamVo = [];
                    ParamVo.push(
                        //表的主键
                        tree_wage_code + "@" +
                        acc_year + "@" +
                        acc_month
                    );
                    $.ligerDialog.confirm('是否生成本月工资表?', function(yes) {
                        if (yes) {
                            ajaxJsonObjectByUrl("initAccWagePay.do", 
                            	{ ParamVo: ParamVo.toString()}, 
                            	function(responseData) {
                                if (responseData.state == "true") {
                                    query();
                                }
                            });
                        }
                    });
                    return;
                case "addbatch":

                    var wage_code = liger.get("wage_code").getValue();

                    var acc_year = acc_time.getValue().split(".")[0];

                    var acc_month = acc_time.getValue().split(".")[1];

                    if (wage_code == "") {

                        $.ligerDialog.warn('请选择工资套！');

                        return;

                    }

                    $.ligerDialog.open({
                        url: 'accWagePayBatchAddPage.do?wage_code=' + wage_code + "&acc_year=" + acc_year + "&acc_month=" + acc_month,
                        height: 502,
                        width: 1145,
                        title: '添加',
                        modal: true,
                        showToggle: false,
                        showMax: false,
                        showMin: true,
                        isResize: true,
                        buttons: [{
                            text: '确定',
                            onclick: function(item, dialog) {
                                dialog.frame.save();
                            },
                            cls: 'l-dialog-btn-highlight'
                        }, {
                            text: '取消',
                            onclick: function(item, dialog) {
                                dialog.close();
                            }
                        }]
                    });
                    return;
                case "delete": // 删除

                    var data = gridManager.getCheckedRows();

                    if (data.length == 0) {

                        $.ligerDialog.warn('请选择行');

                        return;

                    } else {

                        var acc_year = acc_time.getValue().split(".")[0];

                        var acc_month = acc_time.getValue().split(".")[1];

                        var ParamVo = [];

                        $(data).each(function() {

                            if (this.PAY_ID != "") {
                            	
                                ParamVo.push(
                                    //表的主键
                                    this.PAY_ID + "@" +
                                    this.GROUP_ID + "@" +
                                    this.HOS_ID + "@" +
                                    this.COPY_CODE + "@" +
                                    tree_wage_code + "@" +
                                    acc_year + "@" +
                                    acc_month+ "@" +
                                    this.EMP_ID
                                )
                            }
                        });
                        if (ParamVo.length > 0) {
                            $.ligerDialog.confirm('确定删除?', function(yes) {
                                if (yes) {
                                    ajaxJsonObjectByUrl("deleteAccWagePay.do", {
                                        ParamVo: ParamVo.toString()
                                    }, function(responseData) {
                                        if (responseData.state == "true") {
                                            query();
                                        }
                                    });
                                }
                            });
                        } else {

                            $.ligerDialog.warn('请选择非合计数据进行删除');

                            return;
                        }
                    }
                    return;
                case "deleteAll":

                    var wage_code = liger.get("wage_code").getValue();

                    var acc_year = acc_time.getValue().split(".")[0];

                    var acc_month = acc_time.getValue().split(".")[1];

                    var ParamVo = [];
                    ParamVo.push(
                        //表的主键
                        this.GROUP_ID + "@" +
                        this.HOS_ID + "@" +
                        this.COPY_CODE + "@" +
                        wage_code + "@" +
                        acc_year + "@" +
                        acc_month
                    )
                    $.ligerDialog.confirm('确定要全部删除?', function(yes) {
                        if (yes) {
                            ajaxJsonObjectByUrl("deleteAllAccWagePay.do", {
                                ParamVo: ParamVo
                            }, function(responseData) {
                                if (responseData.state == "true") {
                                    query();
                                }
                            });
                        }
                    });

                    return;

                case "extend": // 继承

                    if (tree_wage_code == "") {

                        $.ligerDialog.warn('请选择工资套进行继承');

                        return;
                    }
                
                    $.ligerDialog.confirm('确定继承?', function(yes) {
                        if (yes) {
                        	
                        	var acc_year = acc_time.getValue().split(".")[0];

                            var acc_month = acc_time.getValue().split(".")[1];

                            var formData = {

                                wage_code: tree_wage_code,

                                scheme_code: scheme_code,

                                acc_year: acc_year,
 
                                acc_month: acc_month,
                                
                                item_column: column
                            }

                            ajaxJsonObjectByUrl("extendAccWagePay.do?isCheck=false", formData, function(responseData) {

                                if (responseData.state == "true") {

                                    query();

                                }

                            });
                        }
                    });

                    return;

                case "collect": // 计算
                    if (tree_wage_code == "") {
                        $.ligerDialog.warn('请选择工资套进行计算');
                        return;
                    }
                
                    var formPara = {
                        wage_code: tree_wage_code,
                        acc_year: acc_time.getValue().split(".")[0],
                        acc_month: acc_time.getValue().split(".")[1],
                    };
                    $.ligerDialog.confirm('是否计算本月的工资数据?', function(yes) {
                        if (yes) {
                            ajaxJsonObjectByUrl("updateBatchAccWagePay.do", formPara, function(responseData) {
                                if (responseData.is_ok == "0") {
                                    $.ligerDialog.success(responseData.msg_text);
                                    //query();
                                } else if (responseData.is_ok == "-1") {
                                    $.ligerDialog.error(responseData.msg_text);
                                    return;
                                } else if (responseData.is_ok == "100") {
                                    $.ligerDialog.warn(responseData.msg_text);
                                    return;
                                }
                            });
                        }
                    });
                    return;
                case "import":

                    if (tree_wage_code == "") {

                        $.ligerDialog.warn('请选择工资套或方案进行导入');

                        return;
                    }

                    var acc_year = acc_time.getValue().split(".")[0];

                    var acc_month = acc_time.getValue().split(".")[1];

                    $.ligerDialog.open({
                        url: 'accWagePayImportPage.do?scheme_id=' + scheme_code + '&wage_code=' + tree_wage_code + '&group_id=' + '${group_id}' + '&hos_id=' + '${hos_id}' + '&copy_code=' + '${copy_code}' + '&acc_year=' + acc_year + '&acc_month=' + acc_month,
                        height: 500,
                        width: 1135,
                        title: '导入',
                        modal: true,
                        showToggle: false,
                        showMax: false,
                        showMin: true,
                        isResize: true
                    });

                    return;
                case "addOrUpdate":

                    var wage_code = liger.get("wage_code").getValue();

                    var acc_year = acc_time.getValue().split(".")[0];

                    var acc_month = acc_time.getValue().split(".")[1];

                    $.ligerDialog.open({
                        url: 'accWagePayDescAddPage.do?wage_code=' + wage_code + "&acc_year=" + acc_year + "&acc_month=" + acc_month,
                        height: 400,
                        width: 590,
                        title: '工资说明',
                        modal: true,
                        showToggle: false,
                        showMax: false,
                        showMin: true,
                        isResize: true,
                        buttons: [{
                            text: '确定',
                            onclick: function(item, dialog) {
                                dialog.frame.save();
                            },
                            cls: 'l-dialog-btn-highlight'
                        }, {
                            text: '取消',
                            onclick: function(item, dialog) {
                                dialog.close();
                            }
                        }]
                    });

                    return;

                case "downTemplate":

                    var acc_year = acc_time.getValue().split(".")[0];

                    if (tree_wage_code == "") {

                        $.ligerDialog.warn('请选择工资套或方案进行下载模板');

                        return;
                    }

                    location.href = "downTemplate.do?scheme_id=" + scheme_code + "&wage_code=" + tree_wage_code + "&acc_year=" + acc_year;

                    return;
                case "audit": // 审核
                    if (tree_wage_code == "") {
                        $.ligerDialog.warn('请选择工资套进行审核');
                        return;
                    }

                    var formPara = {
                        wage_code: tree_wage_code,
                        acc_year: acc_time.getValue().split(".")[0],
                        acc_month: acc_time.getValue().split(".")[1],
                        pay_state:'1'
                    };
                    $.ligerDialog.confirm('是否审核本月的工资数据?', function(yes) {
                        if (yes) {
                            ajaxJsonObjectByUrl("auditAccWagePay.do", formPara, function(responseData) {
                            	if (responseData.state == "true") {
                                    query();
                                }
                            });
                        }
                    });
                    return;
                case "unaudit":

                    if (tree_wage_code == "") {

                        $.ligerDialog.warn('请选择工资套进行审核');

                        return;
                    }

                    var formPara = {

                        wage_code: tree_wage_code,

                        acc_year: acc_time.getValue().split(".")[0],

                        acc_month: acc_time.getValue().split(".")[1],
                        
                        pay_state:'0'

                    };

                    $.ligerDialog.confirm('是否取消审核本月的工资数据?', function(yes) {

                        if (yes) {

                            ajaxJsonObjectByUrl("auditAccWagePay.do", formPara, function(responseData) {

                            	if (responseData.state == "true") {

                                    query();

                                }

                            });
                        }
                    });
                    return;
                case "updateBatch": // 批量修改
                	 if (tree_wage_code == "") {
                         $.ligerDialog.warn('请选择方案或者工资套进行修改操作！');
                         return;
                     }
                     parent.openFullDialog('hrp/acc/accwagepay/accWagePayBatchUpdatePage.do?isCheck=false&wage_code=' + tree_wage_code + "&wage_name=" + tree_wage_name + "&year_month=" + $("#acc_time").val() + "&scheme_name=" + scheme_name + "&scheme_code=" + scheme_code, '批量修改', 0, 0, true, false);
                    return;
            }
        }

    }

    function openUpdate(obj, group_id, hos_id, copy_code) {

        var vo = obj.split("|");
        var parm = "type_id=" +
            vo[0] + "&group_id=" +
            vo[1] + "&hos_id=" +
            vo[2] + "&copy_code=" +
            vo[3];

        $.ligerDialog.open({
            url: 'accWageTypeUpdatePage.do?isCheck=false&' + parm,
            data: {},
            height: 500,
            width: 500,
            title: '修改',
            modal: true,
            showToggle: false,
            showMax: false,
            showMin: false,
            isResize: true,
            buttons: [{
                text: '确定',
                onclick: function(item, dialog) {
                    dialog.frame.saveAccBank();
                },
                cls: 'l-dialog-btn-highlight'
            }, {
                text: '取消',
                onclick: function(item, dialog) {
                    dialog.close();
                }
            }]
        });

    }
    
    // 加载左侧方案树
    function loadTree(obj) {
         ajaxJsonObjectByUrl("queryAccWagePayTree.do?isCheck=false&scheme_type_code=01", obj, function(responseData) {
            tree = $.fn.zTree.init($("#tree"), setting, responseData.Rows);
        });
    }

    function btn_query() {

        var wage = liger.get("wage_code").getValue();

        var scheme = liger.get("scheme_name").getValue();

        var forData = {

            wage_code: wage,

            scheme_code: scheme
        }

        loadTree(forData);

    }

    function loadDict() {

        //加载工资套
        autocomplete("#wage_code", "../queryAccWage.do?isCheck=false", "id", "text", true, true);
        //加载方案列表
        autocompleteAsyncMulti("#emp_kind", "../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0", "id", "text", true, true);
        
        autocomplete("#dept_code", "../queryDeptDictNo.do?isCheck=false&is_stop=0", "id", "text", true, true, '', false, '', 110);

         $("#emp_code").ligerTextBox({width:160});
         
        autocomplete("#emp_kind", "../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0", "id", "text", true, true);

        autocomplete("#emp_pay", "../queryEmpPay.do?isCheck=false&is_stop=0", "id", "text", true, true);
        
        autocomplete("#attr_code","../queryEmpSet.do?isCheck=false","id","text",true,true,'',false);
       
        $("#dept_code").ligerComboBox({
            width: 110
        });
        
        $("#item_code").ligerComboBox({
            selectBoxWidth: 160,
            autocomplete: true,
            width: 160
        });
        
        $("#item_code1").ligerComboBox({
            selectBoxWidth: 160,
            autocomplete: true,
            width: 160
        });
        
        $("#item_code2").ligerComboBox({
            selectBoxWidth: 160,
            autocomplete: true,
            width: 160
        });
        
        $("#item_code3").ligerComboBox({
            selectBoxWidth: 160,
            autocomplete: true,
            width: 160
        });

        $("#item_code4").ligerComboBox({
            selectBoxWidth: 160,
            autocomplete: true,
            width: 160
        });

        $("#attr_code").ligerComboBox({
            selectBoxWidth: 100,
            autocomplete: true,
            width: 100
        });
/*         $("#acc_time").ligerComboBox({
            url: '../queryYearMonth.do?isCheck=false',
            valueField: 'id',
            textField: 'text',
            selectBoxWidth: 160,
            autocomplete: true,
            width: 160
        }); */
        
       //会计期间
     	acc_time = $("#acc_time").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: '${wage_year_month}'
   		});

      	
        
        var year_Month = '${wage_year_month}';

        if (year_Month.toString() == "000000") {

            var date = new Date;

            var year = date.getFullYear();

            var month = date.getMonth() + 1;

            month = (month < 10 ? "0" + month : month);

            year_Month = (year.toString() + month.toString());

        }

        //acc_time.setValue(year_Month.substring(0, 4) + "." + year_Month.substring(4, 6).toString());

        //liger.get("acc_time").setText(year_Month.substring(0, 4) + "." + year_Month.substring(4, 6).toString());

        var fromData = {

            wage_code: '0000'

        }

        autocomplete("#scheme_name", "../queryAccWageScheme.do?isCheck=false", "id", "text", true, true, fromData);


    }

    // 维护方案
    function btn_add() {
        var nodes = tree.getSelectedNodes();
        var node_name;
        var scheme_code;
        var scheme_name;
        var acc_year = acc_time.getValue().split(".")[0];

        var acc_year = acc_time.getValue().split(".")[0];

        if (nodes == "" || nodes == null) {
            $.ligerDialog.error('请选择工资套或方案进行维护！');
            return;
        }

        if (nodes[0].pId == "0") {
            node_name = nodes[0].name;
        } else {
            node_name = nodes[0].getParentNode().name;
            scheme_code = nodes[0].id;
            scheme_name = nodes[0].name
        }
		
        parent.$.ligerDialog.open({
            url : 'hrp/acc/accwagepay/accWageSchemeMainPage.do?isCheck=false&node=' + node_name + '&scheme_code=' + scheme_code + '&scheme_name=' + scheme_name + '&acc_year=' + acc_year + '&scheme_type_code=' + '01',
            data : {},
            width :$(parent.window).width(),
            height : $(parent.window).height(),
            title : '工资方案维护',
            modal : true,
            top:0,
            showClose: true,
            showToggle : false,
            showMax : false,
            showMin : true,//是否最小化
            isResize : false,
            parentframename:window.name
        });
    }

    function btn_delete() {
        var formData = {
            wage_code: tree_wage_code,
            scheme_id: scheme_code
        }

        if (scheme_code == "") {
            $.ligerDialog.error('请选择要删除的方案！');
            return;
        } else {
            $.ligerDialog.confirm("该方案存在业务数据,是否确认删除?", function(yes) {
                if (yes) {
                    ajaxJsonObjectByUrl("deleteAccWageScheme.do?isCheck=false", formData, function(responseData) {
                        if (responseData.state == "true") {
                            btn_query();
                        }
                    });
                }
            });
            return;

        }
    }
    
    function show(){
		if($("#vouch_table").is(":hidden")){
			$("#vouch_table").show();
		}else{
			$("#vouch_table").hide();
			
		}
		grid._onResize();
		//$(".l-bar-btnload").click();
	}

    function printDate(){
    	   
    	if(grid.getData().length==0){
    		
    		$.ligerDialog.error("请先查询数据！");
    		
    		return;
    	}
    	//console.log(grid.getColumns(1))
    	/* var heads={
    		"isAuto":true,//系统默认，页眉显示页码
    		"rows": [
	          {"cell":0,"value":"会计期间："},
	          {"cell":1,"value":""+acc_time.getValue()+""}
    	]}; */
    	
    	/*var dates = getCurrentDate();
    	var cur_date = dates.split(";")[2];
    	var foots={
       		//"isAuto":true/false 默认true，页脚默认左下角显示打印人、右下角显示打印时间 
       		"rows":[
   			{"cell":0,"value":"制表人： ${sessionScope.user_name}","colSpan":3},//"cell":0,"value" 单元格的下标
   			{"cell":2,"value":"打印日期: " + cur_date,"colSpan":3,"from":"right","align":"right"}//"from":"right"：cell下标从右边开始数
           	]
        };*/ 
    	 
    	var printPara={
      		title: acc_time.getValue().split(".")[0]+"年"+acc_time.getValue().split(".")[1]+"月"+tree_wage_name+"工资明细表",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.acc.service.wagedata.AccWagePayService",
   			method_name: "queryAccWagePayPrint",
   			bean_name: "accWagePayService",
   			//heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			
       	};
    	
    	//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
    	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
            <div position="left" title="  ">

		        <table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套名称：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="scheme_name" type="text" id="scheme_name" ltype="text" validate="{required:true,maxlength:18}" /></td>
			        </tr>
			        <tr>
				        <td align="left"><input class="l-button" type="button" style="width: 80px;margin-left: 20px" id="query" value="查询(Q)" onclick="btn_query();" /></td>
				        <td align="left"><input class="l-button" type="button" style="width: 80px;margin-left: 20px" id="query" value="维护方案(A)" onclick="btn_add();" /></td>
				        <td align="left"><input class="l-button" type="button" style="width: 80px;margin-left: 20px" id="query" value="删除方案(D)" onclick="btn_delete();" /></td>
			        </tr>
	   			</table>
	   			  <hr>
   			   <div style="width:97%; height:77%;overflow:auto;border: 1px solid #AECAF0;margin-left: 5px;margin-top: 5px" >
		      		<ul class="ztree" id="tree" ></ul>
			   </div>
            </div>
            <div position="center"  title="  " class="bb">
            	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			   	 	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:7px;">会&nbsp;&nbsp;计&nbsp;&nbsp;期&nbsp;间：</td>
			            <td align="left" class="l-table-edit-td" ><input style="width: 160"  name="acc_time" type="text" id="acc_time"/></td>
			            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:5px;">部 门 分 类：</td>
			            <td align="left" class="l-table-edit-td" ><input name="dept_kind" type="text" id="dept_kind" /></td> -->
			            <td align="right" class="l-table-edit-td"  style="padding-left:15px;">部门名称：</td>
			            <td align="left" class="l-table-edit-td" ><input name="dept_code" type="text" id="dept_code"  ltype="text" validate="{required:true,maxlength:18}" />
			            </td> 
			            <td align="right" class="l-table-edit-td"  style="padding-left:35px;">职工分类：</td>
			            <td align="left" class="l-table-edit-td" ><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:18}" /></td>
			        </tr>
					 <tr>
			            <td align="right" class="l-table-edit-td"  style="width:80px;">职&nbsp;&nbsp;工&nbsp;&nbsp;名&nbsp;称：</td>
			            <td align="left" class="l-table-edit-td" style="width: 160"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
			            <td align="right" class="l-table-edit-td" style="width:80px;">发放方式：</td>
			            <td align="left" class="l-table-edit-td" ><input name="emp_pay" type="hidden" id="emp_pay" ltype="text" validate="{required:true,maxlength:18}" /></td>
			            <td align="right" class="l-table-edit-td"  style="width:80px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
			         	<td align="left" class="l-table-edit-td" colspan="3"><input name="note" type="text" id="note" /></td>
			             <td align="right" class="l-table-edit-td" style="padding-left: 20px;"><a href="javascript:show()">高级查询</a></td>
			        </tr> 
	   			</table>
	   			<table id="vouch_table" cellpadding="0" cellspacing="0" class="l-table-edit" style="display: none">
			        <tr>
					   <td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code" type="hidden" id="item_code" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
						<td align="left"><input name="money1" type="text" id="money1"  ltype="text" validate="{required:true,maxlength:18}" />
					   <td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code1" type="hidden" id="item_code1" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item1">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
						<td align="left"><input name="money2" type="text" id="money2"  ltype="text" validate="{required:true,maxlength:18}" />
			        </tr>
			        <tr>
					   <td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code2" type="hidden" id="item_code2" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item2">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
						<td align="left"><input name="money3" type="text" id="money3"  ltype="text" validate="{required:true,maxlength:18}" />
					   <td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code3" type="hidden" id="item_code3" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item3">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
						<td align="left"><input name="money4" type="text" id="money4"  ltype="text" validate="{required:true,maxlength:18}" />
			        </tr>
			        <tr>
					   <td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code4" type="hidden" id="item_code4" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item4">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
						<td align="left"><input name="money5" type="text" id="money5"  ltype="text" validate="{required:true,maxlength:18}" />
			      
			          <td align="right" class="l-table-edit-td"  style="width:80px;">身份证号：</td>
			         	<td align="left" class="l-table-edit-td" ><input name="id_number" type="text" id="id_number" /></td>
			        <td align="right" class="l-table-edit-td"  style="width:80px;">自定义属性：</td>
			         	<td align="left" class="l-table-edit-td" ><input name="attr_code" type="hidden" id="attr_code" /></td>
			        
			        </tr>   
			     
			        
			</table>
	   		<div id="maingrid"></div>
            </div>
        </div>


</body>
</html>
