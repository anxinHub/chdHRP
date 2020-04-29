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
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>

<style type="text/css">
	#navtab .l-tab-content{
		height: 100%;
	}
	#navtab .l-tab-content .l-tab-content-item{
		overflow: auto;
	}
</style>

<script type="text/javascript">
	var dialog = frameElement.dialog;
	var data = JSON.parse(dialog.options.data);
	var searchFields = [], gridFields = [], formFields = [];
	/*
      "is_default_value": "0",
      "default_value": "value",
	*/
	var editorType = {
    		'01': 'combobox',//下拉框 
  			'02': 'text',//文本框
  			'03': 'date',//日期框
  			'04': 'text',//单文件上传
  			'05': 'text',//多文件上传
  			'06': 'text',//多行文本框
  			'07': 'digits',//整数框
  			'08': 'number'//浮点框
      }
	
	$(function() {
		$.each(data.gridSetData, function(index, item){
			if(item.is_view == '1'){
				gridFields.push(getFiled(item))
			}
		});
		
		$.each(data.searchSetData, function(index, item){
			if(item.is_view == '1'){
				searchFields.push(getFiled(item))
			}
		});
		
		$.each(data.formSetData, function(index, item){
			if(item.is_view == '1'){
				formFields.push(getFiled(item))
			}
		});
		
		loadSearchForm();
		loadGrid();
		loadForm();
		
		$("#navtab").ligerTab();
		
	});
	
	function getFiled(item){
		var display = item.view_name;
		var name = item.col_code;
		var newline = item.newline == '1' ? true : false;
		var type = editorType[item.com_type_code];
		var align = item.text_align;
		var list_width = item.list_width;
		var form_width = item.form_width;
		var form_heigth = item.form_heigth ? item.form_heigth : '';
		
	    var editor = {};
		
		//下拉框
		if(item.com_type_code == '01' && item.value_mode_code == '02'){
			editor = {
					url: '../../queryTypeFiledTypeSelect.do?isCheck=false',
					parms: {type_filed_code: item.field_tab_code},
					valueField : 'id',
					textField: 'text',
					selectBoxWidth: 300,
					autocomplete: true,
					width: 300
			}
		}
			
		return { display: display, name: name, width: form_width, newline: newline, type: type, editor: editor };
	}
	
	function loadSearchForm(){
		//创建表单结构 
        $("#search_view").ligerForm({
            inputWidth: 170, labelWidth: 90, space: 40,
            fields: searchFields
        }); 
	}
	
	function loadGrid() {
		grid = $("#maingrid").ligerGrid({
			columns : gridFields,
			data: null, width: '100%', height: '100%', checkbox: true, rownumbers: true, delayLoad: true, 
			toolbar : {
				items : [ {
					text : '查询', id : 'search', click : itemclick , icon : 'search'
				}, {
					line : true
				}, {
					text : '添加', id : 'add', click : itemclick , icon : 'add'
				}]
			}
		});

	}

	function loadForm(){
        $("#form_view").ligerForm({
            inputWidth: 170, labelWidth: 90, space: 40,
            fields: formFields,
            buttons: [{ text: "保存", width: 60, click: submitform }]
        }); 
	}
	
	function itemclick(item){}
	
	function submitform(){}
	
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div position="center" title="">
		<div id="navtab" style="height: 600px; border: 1px solid #A3C0E8;">
			<div tabid="item1" title="列表页面" lselected="true">
				<div id="toptoolbar"></div>
				<div id="search_view"></div> 
				<div id="maingrid"></div>
			</div>
			<div tabid="item2" title="表单页面">
				<div id="form_view"></div> 
			</div>
		</div>
	</div>
</body>
</html>
