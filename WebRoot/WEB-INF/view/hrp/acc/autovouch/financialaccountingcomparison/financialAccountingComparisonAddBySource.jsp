<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="select,form" name="plugins" />
</jsp:include>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var data = dialog.options.data;
	var subj_code_c = data.subj_code_c;
	var jsonHead = data.jsonHead;
	var init_values = data.init_values;
	var subjs = [];
	var form;
	$(function (){
		loadDict();
	}); 
	
	function  save(){
		//获取Form表单数据
		var allData = [];
		var rowData = {};  //用于更新父页面表格数据
		var widget;
		$.each(jsonHead, function(i, v){
			//获取对应的select控件
			widget = getWidget(v.source_id);
			rowData["id_"+v.source_id] = widget.getValue();
			rowData["name_"+v.source_id] = widget.getText();
			allData.push({
				source_id: v.source_id, 
				subj_code_k: widget.getValue()
			});
		})
    	
		var param = {
			'by_source': 1,
			'subj_code_c': subj_code_c, 
			'allData': JSON.stringify(allData)
		};
		ajaxJsonObjectByUrl("addFinancialAccountingComparison.do", param, function(responseData){
			//parent.query();
			if(responseData.state == true){
				var selected = parent.grid.getSelected();
				if (!selected) { alert('请选择行'); return; }
				parent.grid.updateRow(selected, rowData);
			}
			dialog.close();
		});
	}
	
    function loadDict(){
    	//获取科目列表
		ajaxJsonObjectByUrl("queryFinancialAccountingComparisonSubjK.do?isCheck=false", "", function(data){
			subjs = data;
		}, false);
    	//根据jsonHead动态生成Form表单元素
		var fieldItems = [];
    	var defaultOption;
		$.each(jsonHead, function(i, v){
			//添加默认值
			defaultOption = {};
			if(init_values["id_"+v.source_id]){
				defaultOption.id = init_values["id_"+v.source_id];
				defaultOption.text = init_values["name_"+v.source_id];
			}
			//添加控件
			fieldItems.push({
				id: v.source_id, 
				name: v.source_name, 
				labelStyle: "width: 40px;", 
				type: "select", 
				width: "450px", 
				place: 1, 
				required: false, 
				OPTIONS: {
					options: subjs, 
					searchField: ['id', 'text', 'subj_name', 'spell_code', 'wbx_code'], 
					defaultOption: defaultOption,
					//url: "queryFinancialAccountingComparisonSubjK.do?isCheck=false", 
					defaultValue : "none",
					backEndSearch: false, 
				}, 
			});
		})
		//构建Form表单
		form = $("#from").etForm({
			colNum: 1, 
			fieldItems: fieldItems
		});
		//构建表单中的插件
		form.initWidget();
    } 

	//获取Form表单中对应的select控件
    function getWidget(widget_id){
		var widget;
		$.each(form.getWidgetArray(), function(i, v){
			if(v.id == widget_id){
				widget = v.widget;
				return false;
			}
		});
		
		return widget;
    }
	</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="from" style="margin-top: 20px;"></div>
</body>
</html>
