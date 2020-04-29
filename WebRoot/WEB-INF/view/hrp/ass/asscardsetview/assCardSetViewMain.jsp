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
		$("#col_name").ligerTextBox({width:160});

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
		grid.options.parms.push({
			name : 'is_view',
			value : liger.get("is_view").getValue()
		});
		grid.options.parms.push({
			name : 'col_name',
			value : liger.get("col_name").getValue()
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
										display : '资产性质',
										name : 'naturs_name',
										align : 'left',
										width:100,
										frozen: true
									},
									{
										display : '显示名称',
										name : 'col_name',
										align : 'left',
										width:160,
										frozen: true,
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.col_name == "" || rowdata.col_name == null){
												return "<input type='text' size='20' name='col_name"+rowindex+"' id='col_name"+rowindex+"'  style='margin-top:5px;'/>";
											}
											return "<input type='text' size='20' name='col_name"+rowindex+"' id='col_name"+rowindex+"' value='"+rowdata.col_name+"' style='margin-top:5px;'/>";
										}
									},
									{
										display : '控件类型',
										name : 'type_code',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.type_code == 1) {
												return "下拉框";
											} else if (rowdata.type_code == 0) {
												return "文本框";
											} else if (rowdata.type_code == 2) {
												return "日期框";
											}else if(rowdata.type_code == 3){
												return "数值框";
											}
										},
										width:80
									},
									{
										display : '显示顺序',
										name : 'seq_no',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											return "<input type='text' size='5' name='seq_no"+rowindex+"' id='seq_no"+rowindex+"' value='"+rowdata.seq_no+"' style='margin-top:5px;'/>";
										},
										width:100
									},
									{
										display : '数据列宽',
										name : 'column_width',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.column_width == "" || rowdata.column_width == null){
												return "<input type='text' size='5' name='column_width"+rowindex+"' id='column_width"+rowindex+"'  style='margin-top:5px;'/>";
											}
											
											return "<input type='text' size='5' name='column_width"+rowindex+"' id='column_width"+rowindex+"' value='"+rowdata.column_width+"' style='margin-top:5px;'/>";
										},
										width:100
									},
									{
										display : '是否显示(卡片)',
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
										width:100
									},
									{
										display : '对齐方式',
										name : 'text_align',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='text_align"+rowindex+"'  name = 'text_align"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.text_align == "left") {
												str = str
														+ "<option value='"+rowdata.text_align+"' selected='selected'>左</option>";
												str = str
														+ "<option value='center'>中</option>";
												str = str
													+ "<option value='right'>右</option>";
											} else if (rowdata.text_align == "中") {
												str = str
														+ "<option value='"+rowdata.text_align+"' selected='selected'>中</option>";
												str = str
														+ "<option value='left' >左</option>";
												str = str
														+ "<option value='right' >右</option>";
											}else if(rowdata.text_align == "右"){
												str = str
												+ "<option value='"+rowdata.text_align+"' selected='selected'>右</option>";
												str = str
														+ "<option value='center' >中</option>";
												str = str
														+ "<option value='left' >左</option>";
											}else{
												str = str
												+ "<option value='left' >左</option>";
												str = str
												+ "<option value='center' >中</option>";
												str = str
												+ "<option value='right' >右</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '区域位置',
										name : 'field_area',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='field_area"+rowindex+"'  name = 'field_area"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.field_area == 1) {
												str = str
														+ "<option value='"+rowdata.field_area+"' selected='selected'>第一区域</option>";
												str = str
														+ "<option value='2'>第二区域</option>";
												str = str
													+ "<option value='3'>第三区域</option>";
											} else if (rowdata.field_area == 2) {
												str = str
														+ "<option value='"+rowdata.field_area+"' selected='selected'>第二区域</option>";
												str = str
														+ "<option value='1' >第一区域</option>";
												str = str
														+ "<option value='3' >第三区域</option>";
											}else if(rowdata.field_area == 3){
												str = str
												+ "<option value='"+rowdata.field_area+"' selected='selected'>第三区域</option>";
												str = str
														+ "<option value='2' >第二区域</option>";
												str = str
														+ "<option value='1' >第一区域</option>";
											}else{
												str = str
												+ "<option value='1' >第一区域</option>";
												str = str
												+ "<option value='2' >第二区域</option>";
												str = str
												+ "<option value='3' >第三区域</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '合并列数',
										name : 'field_width',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='field_width"+rowindex+"'  name = 'field_width"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.field_width == 1) {
												str = str
														+ "<option value='"+rowdata.field_width+"' selected='selected'>一个列宽</option>";
												str = str
														+ "<option value='2'>二个列宽</option>";
												str = str
													+ "<option value='3'>三个列宽</option>";
												str = str
													+ "<option value='4' >四个列宽</option>";
											} else if (rowdata.field_width == 2) {
												str = str
														+ "<option value='"+rowdata.field_width+"' selected='selected'>二个列宽</option>";
												str = str
														+ "<option value='1' >一个列宽</option>";
												str = str
														+ "<option value='3' >三个列宽</option>";
												str = str
														+ "<option value='4' >四个列宽</option>";	
											}else if(rowdata.field_width == 3){
												str = str
												+ "<option value='"+rowdata.field_width+"' selected='selected'>三个列宽</option>";
												str = str
														+ "<option value='2' >二个列宽</option>";
												str = str
														+ "<option value='1' >一个列宽</option>";
												str = str
														+ "<option value='4' >四个列宽</option>";		
											}else if(rowdata.field_width == 4){
												str = str
												+ "<option value='"+rowdata.field_width+"' selected='selected'>四个列宽</option>";
												str = str
														+ "<option value='2' >一个列宽</option>";
												str = str
														+ "<option value='1' >二个列宽</option>";
												str = str
														+ "<option value='4' >三个列宽</option>";		
											}else{
												str = str
												+ "<option value='1' >一个列宽</option>";
												str = str
												+ "<option value='2' >二个列宽</option>";
												str = str
												+ "<option value='3' >三个列宽</option>";
												str = str
												+ "<option value='4' >四个列宽</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '是否显示（列表）',
										name : 'is_tab_view',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_tab_view"+rowindex+"'  name = 'is_tab_view"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_tab_view == 1) {
												str = str
														+ "<option value='"+rowdata.is_tab_view+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_tab_view == 0) {
												str = str
														+ "<option value='"+rowdata.is_tab_view+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '是否必填',
										name : 'is_verify',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_verify"+rowindex+"'  name = 'is_verify"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_verify == 1) {
												str = str
														+ "<option value='"+rowdata.is_verify+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_verify == 0) {
												str = str
														+ "<option value='"+rowdata.is_verify+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '是否主键列',
										name : 'is_pk',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_pk"+rowindex+"'  name = 'is_pk"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_pk == 1) {
												str = str
														+ "<option value='"+rowdata.is_pk+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_pk == 0) {
												str = str
														+ "<option value='"+rowdata.is_pk+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '期初卡片显示',
										name : 'is_init_view',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_init_view"+rowindex+"'  name = 'is_init_view"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_init_view == 1) {
												str = str
														+ "<option value='"+rowdata.is_init_view+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_init_view == 0) {
												str = str
														+ "<option value='"+rowdata.is_init_view+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '期初卡片(列表)',
										name : 'is_init_tab_view',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_init_tab_view"+rowindex+"'  name = 'is_init_tab_view"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_init_tab_view == 1) {
												str = str
														+ "<option value='"+rowdata.is_init_tab_view+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_init_tab_view == 0) {
												str = str
														+ "<option value='"+rowdata.is_init_tab_view+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '卡片增加显示',
										name : 'is_insert_view',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_insert_view"+rowindex+"'  name = 'is_insert_view"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_insert_view == 1) {
												str = str
														+ "<option value='"+rowdata.is_insert_view+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_insert_view == 0) {
												str = str
														+ "<option value='"+rowdata.is_insert_view+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '是否只读',
										name : 'is_read',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_read"+rowindex+"'  name = 'is_read"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_read == 1) {
												str = str
														+ "<option value='"+rowdata.is_read+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_read == 0) {
												str = str
														+ "<option value='"+rowdata.is_read+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '卡片增加是否只读',
										name : 'is_insert_read',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_insert_read"+rowindex+"'  name = 'is_insert_read"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_insert_read == 1) {
												str = str
														+ "<option value='"+rowdata.is_insert_read+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_insert_read == 0) {
												str = str
														+ "<option value='"+rowdata.is_insert_read+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '期初卡片是否只读',
										name : 'is_init_read',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_init_read"+rowindex+"'  name = 'is_init_read"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_init_read == 1) {
												str = str
														+ "<option value='"+rowdata.is_init_read+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_init_read == 0) {
												str = str
														+ "<option value='"+rowdata.is_init_read+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '是否默认值',
										name : 'is_default',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											var str = "<select id='is_default"+rowindex+"'  name = 'is_default"+rowindex+"' style='margin-top:5px;width:128;'>";
											if (rowdata.is_default == 1) {
												str = str
														+ "<option value='"+rowdata.is_default+"' selected='selected'>是</option>";
												str = str
														+ "<option value='0' >否</option>";
											} else if (rowdata.is_default == 0) {
												str = str
														+ "<option value='"+rowdata.is_default+"' selected='selected'>否</option>";
												str = str
														+ "<option value='1' >是</option>";
											}
											str = str + "</select>";
											return str;
										},
										width:100
									},
									{
										display : '默认值',
										name : 'default_value',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.default_value == "" || rowdata.default_value == null){
												return "<input type='text' size='5' name='default_value"+rowindex+"' id='default_value"+rowindex+"'  style='margin-top:5px;'/>";
											}
											
											return "<input type='text' size='5' name='default_value"+rowindex+"' id='default_value"+rowindex+"' value='"+rowdata.default_value+"' style='margin-top:5px;'/>";
										},
										width:100
									},
									{
										display : '默认值名称',
										name : 'default_text',
										align : 'center',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.default_text == "" || rowdata.default_text == null){
												return "<input type='text' size='5' name='default_text"+rowindex+"' id='default_text"+rowindex+"'  style='margin-top:5px;'/>";
											}
											
											return "<input type='text' size='5' name='default_text"+rowindex+"' id='default_text"+rowindex+"' value='"+rowdata.default_text+"' style='margin-top:5px;'/>";
										},
										width:100
									},{ display: '操作', name: 'edit', align: 'center',
										render : function(rowdata, rowindex,
												value) {
												return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 30px;' ligeruiid='Button1004'" 
												+"onclick=save('"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.ass_naturs+"','"+rowdata.table_name+"','"+rowdata.col_code+"','"+rowdata.col_name+"','"+rowindex+"')>"
							       					+"<span>保存</span></div>";
										},
										width:100
								 	} 
									],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryAssCardSetView.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							delayLoad :true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [{
									text : '查询（<u>E</u>）',
									id : 'search',
									click : query,
									icon : 'search'
								} , {
									line : true
								},   {
									text : '批量保存',
									id : 'saveAll',
									click : saveAll,
									icon : 'save'
								}, {
									line : true
								},  {
									text : '打印（<u>P</u>）',
									id : 'print',
									click : printDate,
									icon : 'print'
								}, {
									line : true
								}]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function saveAll(){
		var data = grid.getData();
		var ParamVo = [];
		var flag = true;
		$.each(data,function(i,v){
			var seq_no_value = $("#seq_no" + i + "").val();
			var column_width_value = $("#column_width" + i + "").val();
			var reg = new RegExp("^[0-9][0-9]*$");
			if (!reg.test(seq_no_value)) {
				$.ligerDialog.error("输入数据类型错误，请输入整数类型数据");
				flag = false;
			}
			if (!reg.test(column_width_value)) {
				$.ligerDialog.error("输入数据类型错误，请输入整数类型数据");
				flag = false;
			}
			var is_view_value = $("#is_view" + i + "").val();
			var text_align_value = $("#text_align" + i + "").val();
			var col_name_value =  $("#col_name" + i + "").val();
			var field_area_value = $("#field_area" + i + "").val();
			var field_width_value =  $("#field_width" + i + "").val();
			
			var is_tab_view_value = $("#is_tab_view" + i + "").val();    
			var is_verify_value = $("#is_verify" + i + "").val();     
			var is_pk_value = $("#is_pk" + i + "").val();      
			var is_init_view_value = $("#is_init_view" + i + "").val();  
			var is_init_tab_view_value = $("#is_init_tab_view" + i + "").val();
			var is_insert_view_value = $("#is_insert_view" + i + "").val();
			var is_default_value = $("#is_default" + i + "").val();   
			var default_value_value = $("#default_value" + i + "").val() == ""?" ":$("#default_value" + i + "").val();
			var default_text_value =$("#default_text" + i + "").val() == ""?" ":$("#default_text" + i + "").val(); 
			
			var is_read_value =$("#is_read" + i + "").val() == ""?" ":$("#is_read" + i + "").val(); 
			var is_insert_read_value =$("#is_insert_read" + i + "").val() == ""?" ":$("#is_insert_read" + i + "").val(); 
			var is_init_read_value =$("#is_init_read" + i + "").val() == ""?" ":$("#is_init_read" + i + "").val(); 
			
			ParamVo.push(v.group_id + "@"  
					+ v.hos_id + "@"
					+ v.copy_code + "@" 
					+ v.ass_naturs + "@"
					+ v.table_name + "@" 
					+ v.col_code + "@"
					+ v.col_name + "@" 
					+ seq_no_value + "@"
					+ is_view_value+"@"
					+ column_width_value+"@"
					+ text_align_value+"@"
					+ col_name_value +"@" 
					+ field_area_value + "@"  
					+ field_width_value + "@"
					+ is_tab_view_value + "@"     
					+ is_verify_value + "@"       
					+ is_pk_value + "@"           
					+ is_init_view_value + "@"    
					+ is_init_tab_view_value + "@"
					+ is_insert_view_value + "@"  
					+ is_default_value + "@"      
					+ default_value_value+"@"
					+ default_text_value + "@"
					+ is_read_value + "@"
					+ is_insert_read_value + "@"
					+ is_init_read_value + "@"
			);
		});
		
		if(flag){
			ajaxJsonObjectByUrl("updateAssCardSetViewAll.do?isCheck=false", {
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
			table_name , col_code,
			col_name,rowindex) {
		var ParamVo = [];
		var flag = true;
		var seq_no_value = $("#seq_no" + rowindex + "").val();
		var column_width_value = $("#column_width" + rowindex + "").val();
		var reg = new RegExp("^[0-9][0-9]*$");
		if (!reg.test(seq_no_value)) {
			$.ligerDialog.error("输入数据类型错误，请输入整数类型数据");
			flag = false;
		}
		if (!reg.test(column_width_value)) {
			$.ligerDialog.error("输入数据类型错误，请输入整数类型数据");
			flag = false;
		}
		var is_view_value = $("#is_view" + rowindex + "").val();
		var text_align_value = $("#text_align" + rowindex + "").val();
		var col_name_value =  $("#col_name" + rowindex + "").val();
		var field_area_value = $("#field_area" + rowindex + "").val();
		var field_width_value =  $("#field_width" + rowindex + "").val();
		
		var is_tab_view_value = $("#is_tab_view" + rowindex + "").val();    
		var is_verify_value = $("#is_verify" + rowindex + "").val();     
		var is_pk_value = $("#is_pk" + rowindex + "").val();      
		var is_init_view_value = $("#is_init_view" + rowindex + "").val();  
		var is_init_tab_view_value = $("#is_init_tab_view" + rowindex + "").val();
		var is_insert_view_value = $("#is_insert_view" + rowindex + "").val();
		var is_default_value = $("#is_default" + rowindex + "").val();   
		var default_value_value = $("#default_value" + rowindex + "").val() == ""?" ":$("#default_value" + rowindex + "").val(); 
		var default_text_value = $("#default_text" + rowindex + "").val() == ""?" ":$("#default_text" + rowindex + "").val(); 
		var is_read_value =$("#is_read" + rowindex + "").val() == ""?" ":$("#is_read" + rowindex + "").val(); 
		var is_insert_read_value =$("#is_insert_read" + rowindex + "").val() == ""?" ":$("#is_insert_read" + rowindex + "").val(); 
		var is_init_read_value =$("#is_init_read" + rowindex + "").val() == ""?" ":$("#is_init_read" + rowindex + "").val(); 
		ParamVo.push(group_id + "@"  
						+hos_id + "@"
						+ copy_code + "@" 
						+ ass_naturs + "@"
						+ table_name + "@" 
						+ col_code + "@"
						+ col_name + "@" 
						+ seq_no_value + "@"
						+ is_view_value+"@"
						+ column_width_value+"@"
						+ text_align_value+"@"
						+ col_name_value +"@" 
						+ field_area_value + "@"  
						+ field_width_value + "@"
						+ is_tab_view_value + "@"     
						+ is_verify_value + "@"       
						+ is_pk_value + "@"           
						+ is_init_view_value + "@"    
						+ is_init_tab_view_value + "@"
						+ is_insert_view_value + "@"  
						+ is_default_value + "@"      
						+ default_value_value+"@"
						+ default_text_value +"@"
						+ is_read_value + "@"
						+ is_insert_read_value + "@"
						+ is_init_read_value + "@"
				);
		if(flag){
			ajaxJsonObjectByUrl("updateAssCardSetView.do", {
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
			width : 160,
			onSelected : function(value, text) {

			}
		});
		
		$('#is_view').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		})

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
   		ajaxJsonObjectByUrl("queryAssCardSetView.do?isCheck=false", selPara, function (responseData) {
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产性质：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_naturs" type="text" id="ass_naturs" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否显示：</td>
			<td align="left" class="l-table-edit-td">
				<!-- <select id="is_view">
					<option value="0">否</option>
					<option value="1">是</option>
				</select> -->
				<input name="is_view" type="text" id="is_view"  />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">显示名称：</td>
            <td align="left" class="l-table-edit-td"><input name="col_name" type="text" id="col_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
