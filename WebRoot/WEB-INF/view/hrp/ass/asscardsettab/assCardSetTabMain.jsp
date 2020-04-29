<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#ass_naturs").ligerTextBox({
			width : 160
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'ass_naturs',
			value : liger.get("ass_naturs").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资产性质编码',
										name : 'ass_naturs',
										align : 'left',
										width:120
									},
									{
										display : '资产性质名称',
										name : 'naturs_name',
										align : 'left',
										width:100
									},
									{
										display : '页签编码',
										name : 'tab_id',
										align : 'left',
										width:200
									},
									{
										display : '页签名称',
										name : 'tab_name',
										align : 'left',
										width:200,
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.tab_name == "" || rowdata.tab_name == null){
												return "<input type='text' size='25' name='tab_name"+rowindex+"' id='tab_name"+rowindex+"'  style='margin-top:5px;'/>";
											}
											return "<input type='text' size='25' name='tab_name"+rowindex+"' id='tab_name"+rowindex+"' value='"+rowdata.tab_name+"' style='margin-top:5px;'/>";
										}
									},
									/*
									{
										display : '页签地址',
										name : 'tab_url',
										align : 'left',
										width:300,
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.tab_url == "" || rowdata.tab_url == null){
												return "<input type='text' size='90' name='tab_url"+rowindex+"' id='tab_url"+rowindex+"'  style='margin-top:5px;'/>";
											}
											return "<input type='text' size='90' name='tab_url"+rowindex+"' id='tab_url"+rowindex+"' value='"+rowdata.tab_url+"' style='margin-top:5px;'/>";
										}
									},*/
									{
										display : '是否显示',
										name : 'is_view',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_view"+rowindex+"'  name = 'is_view"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_view == 1) {
												str = str
														+ "<option value='"+rowdata.is_view+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_view == 0) {
												str = str
														+ "<option value='"+rowdata.is_view+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:50
									},
									{
										display : '显示顺序',
										name : 'seq_no',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											return "<input type='text' size='5' name='seq_no"+rowindex+"' id='seq_no"+rowindex+"' value='"+rowdata.seq_no+"' style='margin-top:5px;'/>";
										},
										width:80
									},
				                     { display: '操作', name: 'edit', align: 'center',
										render : function(rowdata, rowindex,
												value) {
												return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 30px;' ligeruiid='Button1004'" 
												+"onclick=save('"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.ass_naturs+"','"+rowdata.tab_id+"','"+rowindex+"')>"
							       					+"<span>保存</span></div>";
										},
										width:120
								 	} 
									],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryAssCardSetTab.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							delayLoad :true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '查询（<u>E</u>）',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}, {
									text : '批量保存',
									id : 'saveAll',
									click : saveAll,
									icon : 'save'
								},{
									line : true
								},
								{
									text : '打印（<u>P</u>）',
									id : 'print',
									click : printDate,
									icon : 'print'
								}, {
									line : true
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//批量保存
	function saveAll(){
		var data = grid.getData();
		var ParamVo = [];
		var flag = true;
		$.each(data,function(i,v){
			var seq_no_value = $("#seq_no" + i + "").val();
			var reg = new RegExp("^[0-9][0-9]*$");
			if (!reg.test(seq_no_value)) {
			$.ligerDialog.error("输入数据类型错误,请输入整数类型数据");
			flag = false;
		}
		/* if(!reg.test(column_width_value)){
			$.ligerDialog.error("输入数据类型错误，请输入整数类型数据");
			flag = false;
		} */
		
		var is_view_value = $("#is_view" + i + "").val();
		var tab_name_value = $("#tab_name" + i + "").val();
		ParamVo.push(v.group_id + "@" + v.hos_id + "@"
				+ v.copy_code + "@" + v.ass_naturs + "@"
				+ v.tab_id + "@" + tab_name_value   + "@" + seq_no_value+"@"+is_view_value);
		});
		if(flag){
			ajaxJsonObjectByUrl("updateAssCardSetTabAll.do?isCheck=false", {
				ParamVo : ParamVo.toString()
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	
	}
	function save(group_id ,hos_id,
			copy_code, ass_naturs,
			tab_id,rowindex) {
		var ParamVo = [];
		var flag = true;
		var seq_no_value = $("#seq_no" + rowindex + "").val();
		var reg = new RegExp("^[0-9][0-9]*$");
		if (!reg.test(seq_no_value)) {
			$.ligerDialog.error("输入数据类型错误，请输入整数类型数据");
			flag = false;
		}
		var is_view_value = $("#is_view" + rowindex + "").val();
		var tab_name_value = $("#tab_name" + rowindex + "").val();
		//var tab_url_value = $("#tab_url" + rowindex + "").val();
		ParamVo.push(group_id + "@" + hos_id + "@"
						+ copy_code + "@" + ass_naturs + "@"
						+ tab_id + "@" + tab_name_value   + "@" + seq_no_value+"@"+is_view_value);
		if(flag){
			ajaxJsonObjectByUrl("updateAssCardSetTab.do", {
				ParamVo : ParamVo.toString()
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}

	function loadDict() {
		$("#ass_naturs").ligerComboBox({
			url : '../queryAssNaturs.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			width : 160
		});

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('S', save);

		hotkeys('P', printDate);
	}
	
	
	  function printDate(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'资产卡片显示表',
       			head:[
    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
       			],
       			foot:[
    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
					{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
					{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
					{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:2,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryAssCardSetTab.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }

	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产性质：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_naturs" type="text" id="ass_naturs" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
