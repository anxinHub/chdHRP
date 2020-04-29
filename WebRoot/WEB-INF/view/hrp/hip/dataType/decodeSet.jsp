<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,grid,select" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<style type="text/css">
.spanBorder{
    /* width: 11%; */
    padding: 5px;
    text-align: center;
    vertical-align: middle;
    border: 2px solid #b8d1f3;
    border-radius: 10px;
    display: inline-block;
    font-size: 13px;
    cursor: pointer;
    background: #fff;
}
.arrowRight,.arrowLeft{
    width: 30px;
    height: 10px;
    display: inline-block;
    position: relative;             
}
.arrowRight:before, .arrowRight:after,
.arrowLeft:after{
    content: '';
	border-color:transparent;    
	border-style: solid;
	position: absolute;               
}
.arrowRight:after,.arrowLeft:after{
    width: 106%;
    border-width: 1px;
    height: 0;
    background-color: #555;
    top: 5px;
    left: -5px;
    z-index: -1;
}
.arrowRight:before{
    border-left-color: #555;
    border-width: 6px;
    left: 96%;
    top: 0px;
    z-index: -1;
}  
</style>
<script type="text/javascript">
	var $grid;
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	var hipDataType = parentWindow.hipDataType;

	var sourceColSelect = [], decodeTableSelect = [], decodeTypeSelect = [], createTypeSelect = [];
	var sourceColOptions = [], decodeTableOptions = [], decodeTypeOptions = [], createTypeOptions = [];
	var sourceCols = {};
	
	$(function ()
	{
		initLayout();
		initSeleteOptions();
		//加载数据
		loadHead(null);	
		query();
		
		//existsSourceCol();//时序性问题该方法无用
		
		//绑定按钮
		$("#this_save").click(function() {
			this_save();
		})
		$("#this_close").click(function() {
			this_close();
		})
	});
	
	function initLayout(){
		$("#layout").ligerLayout({ 
			//topHeight: 100, 
			InWindow: true, 
			height: '100%', 
			bottomHeight: 50, 
		});
	}
	
	function initSeleteOptions(){
		//来源字段
		ajaxPostData({
        	url: "querySourceColListByType.do?isCheck=false&type_id="+hipDataType.type_id, 
        	async: false,
   			success: function (res) {
   				sourceColOptions.push(res);
    			$.each(res,function(index, item){
    				sourceCols[item.text] = item.id;
    				sourceColSelect.push({label: item.text, id: item.id})
    			});
    		}
    	});
		//转换方式
		decodeTypeOptions = [ {
			id: "1", text: "对应转换"
		}, {
			id: "2", text: "系统生成"
		} ];
		decodeTypeSelect = [ {
			id: "1", label: "对应转换"
		}, {
			id: "2", label: "系统生成"
		} ];
		//转换表
		ajaxPostData({
        	url: "queryHrpDictTable.do?isCheck=false", 
        	async: false,
   			success: function (res) {
    			decodeTableOptions.push(res);
    			$.each(res,function(index, item){
    				decodeTableSelect.push({label: item.text, id: item.id})
    			});
    		}
    	});
		//生成方式
		createTypeOptions = [ {
			id: "UUID", text: "UUID"
		}, {
			id: hipDataType.to_table+"_SEQ", text: hipDataType.to_table+"_SEQ"
		} ];
		createTypeSelect = [ {
			id: "UUID", label: "UUID"
		}, {
			id: hipDataType.to_table+"_SEQ", label: hipDataType.to_table+"_SEQ"
		} ];
	}
	
	//校验来源字段合法性
	function existsSourceCol(){
 		var data = $grid.getAllData();
 		if(!data || data.length == 0){
 			return;
 		}
 		var msg;
 		$.each(data, function(index, item){
 			if(item.source_col && !sourceCols[item.source_col]){
 				msg += "第"+(index+1)+"行，来源字段【"+item.source_col+"】不合法<br/>";
 			}
 		});
 		
 		if(msg){
			$.etDialog.warn(msg);
 		}
	}
	
	//查询
	function  query(){
		params = [ {
			name: "type_id", value: hipDataType.type_id
		}, {
			name: "table_name", value: hipDataType.to_table
		} ]
		
		$grid.loadData(params, "queryHipDataDecode.do?isCheck=false");
	}
	
	function loadHead(){
		/* 列头 */
		var columns = [ {
			display: '目标表字段', name: 'table_col', align: 'left', width: 120, editable: false, 
			/* render: function (ui) { // 修改页打开
				return '<a data-item=' + ui.rowIndx + ' class="read_decode">' + ui.cellData + '</a>'
			}  */
		}, {
			display: '字段注释', name: 'comments', align: 'left', width: 90, editable: false, 
		}, {
			display: '是否必填', name: 'nullable', align: 'left', width: 70, editable: false, 
		}, {
			display: 'ID', name: 'decode_id', align: 'left', hidden: true, editable: false, 
		}, {
			display: '转换方式', name: 'decode_type_name', align: 'left', width: 70, editable: true, 
			editor: {
				type: 'select', 
				keyField : 'decode_type', 
				source: decodeTypeSelect, 
			}
		}, {
		    display: '来源字段', name: 'source_col', align: 'left', width: 120, editable: true, 
			editor: {
				type: 'select', 
				keyField : 'source_col', 
				//url : "querySourceColListByType.do?isCheck=false&type_id="+hipDataType.type_id, 
				source: sourceColSelect,
			}
		}, {
			display: '转换表', name: 'decode_table_name', align: 'left', width: 120, editable: true, 
			editor: {
				type: 'select', 
				keyField : 'decode_table', 
				//url : "queryHrpDictTable.do?isCheck=false",
				source: decodeTableSelect, 
			}
		}, {
			display: '对应字段', name: 'rela_col', align: 'left', width: 120, editable: true, 
			editor: {
				type: 'select', 
				keyField : 'rela_col', 
				url : "queryHrpDictTableCol.do?isCheck=false",
			}
		}, {
			display: '取值字段', name: 'decode_col', align: 'left', width: 120, editable: true, 
			editor: {
				type: 'select', 
				keyField : 'decode_col', 
				url : "queryHrpDictTableCol.do?isCheck=false",
				//source: [],
			}
		}, {
			display: '生成方式', name: 'create_type', align: 'left', width: 150, editable: true, 
			editor: {
				type: 'select', 
				keyField : 'create_type', 
				source: createTypeSelect, 
			}
		}, {
			display: '中间表', name: 'join_table_name', align: 'left', width: 120, editable: true, 
			editor: {
				type: 'select', 
				keyField : 'join_table', 
				source: decodeTableSelect, 
				//url : "queryHrpDictTable.do?isCheck=false",
			}
		}, {
			display: '表达式', name: 'el', align: 'left', width: 240, editable: true, 
			editor: {
				type: 'text'
			}
		}, {
			display: '备注', name: 'note', align: 'left', width: 240, editable: true, 
			editor: {
				type: 'text'
			}
		} ]; 
		
		/* 头部按钮 */
	    var toolbar = {
			items: [ { 
				type: "button", label: '清除行', icon: 'clean', listeners: [{ click: cleanRow }] 
			},{ 
				type: "button", label: '帮助', icon: 'help', listeners: [{ click: help }] 
			} ]
		};
		
		/* 表格基础参数 */
		var paramObj = {	
			width: 'auto',
			height: '90%', 
			usePager: false, 
			editable: true,
			inWindowHeight: true,
			toolbar: toolbar,
			checkbox: true, 
			sortable: false, 
			columns: columns, 
			cellDblClick: cellDblClick, 
			editorEnd: editorEnd, 
		};
		
		$grid = $("#maingrid").etGrid(paramObj);

		/* $("#maingrid").on('click', '.read_decode', function () {
			var rowIndex = $(this).attr('data-item');
			var currentRowData = $grid.getDataInPage()[rowIndex];
			read_decode(rowIndex, currentRowData);
		}) */
	}
	
	//双击事件
	function cellDblClick(event, ui){
		var col_name = ui.column.dataIndx;
		if(col_name == "decode_col"){
			ui.column.editor.url = "queryHrpDictTableCol.do?isCheck=false&table_name="+ui.rowData.decode_table;
			return true;
		}
		if(col_name == "rela_col"){
			ui.column.editor.url = "queryHrpDictTableCol.do?isCheck=false&table_name="+(ui.rowData.join_table ? ui.rowData.join_table : ui.rowData.decode_table);
			return true;
		}
	}
	
	//编辑结束事件
	function editorEnd(event, ui){
		var col_name = ui.column.dataIndx;
		if(col_name == "decode_type" && ui.rowData[col_name] == 1){
			$grid.updateRow(ui.rowIndx, {
				create_type: "", 
			});
			return true;
		}
		if(col_name == "decode_type" && ui.rowData[col_name] == 2){
			$grid.updateRow(ui.rowIndx, {
				source_col: "", 
				decode_table: "", 
				decode_col: "", 
				rela_col: "",
			});
			return true;
		}
		if((col_name == "decode_table_name" || col_name == "join_table_name") && ui.rowData[col_name] != ""){
			$grid.updateRow(ui.rowIndx, {rela_col: ""});
		}
	}
	
	//清空行
	function cleanRow(){
    	var data = $grid.selectGet();
    	$.each(data, function(index, value){
    		$grid.updateRow(value.rowIndx, {
    			table_col: value.rowData.table_col, 
    			decode_id: "", 
    			source_col: "", 
    			decode_table: "", 
    			decode_table_name: "", 
    			decode_col: "", 
    			decode_type: "", 
    			decode_type_name: "", 
    			rela_col : "", 
    			create_type: "", 
    			join_table: "", 
    			join_table_name: "", 
    			el: "", 
    			note: ""
    		});
    	});
	}
	
	function help(){
		$.etDialog.open({
			content: $('#helpDiv'), 
			width: "600",
			height: "200",
			title: '帮助',
            btn: ['关闭'],
			showMax: false,
			maxmin: false,
            //zIndex: 8,
            shade: 0, //无遮罩
            btn1: function (index, el) {
            	$.etDialog.close(index);
            },
		});
	}
	
	function this_save(){
 		var data = $grid.getAllData();
		
 		var saveData = [];
 		var msg = "";
		$.each(data,function(index, v){
			if(!v.decode_type){
				/* 先不限制必填
				if(v.nullable == "必填"){,,,,
					msg += "第"+(index+1)+"行字段为必填<br/>";
				} 
				*/
				return true;  //跳出当前循环
			}
			
			if(v.decode_type == 1 && !v.source_col){
				msg += "第"+(index+1)+"行【转换方式】为\"对应转换\"时【来源字段】为必填<br/>";
			}

			if(v.decode_type == 2 && !v.create_type){
				msg += "第"+(index+1)+"行【转换方式】为\"系统生成\"时【生成方式】为必填<br/>";
			}
			
 			if(v.source_col && !sourceCols[v.source_col]){
 				msg += "第"+(index+1)+"行，来源字段【"+v.source_col+"】不合法<br/>";
 			}
 			
 			//校验对应关系数据
 			if(v.decode_table && (!v.decode_col || !v.rela_col)){
 				msg += "第"+(index+1)+"行，【转换表】存在时【对应字段】和【取值字段】不能为空<br/>";
 			}
 			if(v.join_table && !v.el){
 				msg += "第"+(index+1)+"行，【中间表】存在时【表达式】不能为空<br/>";
 			}
 			
			saveData.push(v);
		});
		
		if(msg){
			$.etDialog.warn(msg);
            return false;
		}
 		
 		/* 明细为空表示清除转换条件
 		if(!saveData || saveData.length == 0){
            $.etDialog.warn("请添加转换信息");
            return false;
		} 
 		*/
		
		ajaxPostData({
			url : 'addHipDataDecode.do?isCheck=false',
			data : {
				type_id: hipDataType.type_id, 
				allData: JSON.stringify(saveData)
			},
		    success: function (responseData) {
				
			},
		})
	}
	
	function this_close(){
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex);
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout" style="z-index: 2">
		<div position="center" style="left:width:100%; height: 100%;">
			<div id="maingrid"></div>
		</div>
		<div position="bottom" class="button-group" >
			<button id="this_save">保存</button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="this_close">关闭</button>
		</div>
	</div>
	<div id="helpDiv" style="display: none;">
		<div style="margin-left: 20px;margin-top: 20px;">
			<span class="spanBorder" title="取数sql的select字段">来源字段</span>
			<span class="arrowLeft"></span>
			<span style="color: red;"title="通过该字段与转换表关联">对应字段&nbsp;</span>
			<span class="arrowRight"></span>
			<span class="spanBorder" title="用来转换数据">转换表</span>
			<span class="arrowLeft"></span>
			<span style="color: red;" title="转换后的值">取值字段&nbsp;</span>
			<span class="arrowRight"></span>
			<span class="spanBorder" title="存储的数据表字段">目标表字段</span>
		</div>
	</div>
</body>
</html>