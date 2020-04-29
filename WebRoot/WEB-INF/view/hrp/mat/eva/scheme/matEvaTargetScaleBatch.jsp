<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid; 
	var gridManager = null;
	
	$(function(){
		loadGrid();
	})
	
	//加载指标标度表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [ {
				display: '标度代号', name: 'scale_code', align: 'left', width: 100,
				render: function(rowdata, index, value){
					return "<a href=javascript:edit_scale('"+value+"','"+index+"')>"+value+"</a>";
				}
			}, {
				display: '标度名称', name: 'scale_name', align: 'left', width: 120
			}, {
				display: '标度内容', name: 'scale_content', align: 'left', width: 200
			}, {
				display: '得分比例', name: 'scale_point', align: 'left', width: 80
			}, {
				display: '上限值', name: 'high_point', align: 'left', width: 80
			}, {
				display: '下限值', name: 'low_point', align: 'left', width: 80
			}, {
				display: '排序号', name: 'sort_code', align: 'left', width: 80
			}, {
				display: '是否停用', name: 'is_stop', textField: 'is_stop_name', align: 'left', width: 70
			} ],
			dataAction: 'server', dataType: 'server', usePager: false, 
			width: '100%', height: '100%', delayLoad: false, 
			url: '../target/queryMatEvaTargetScaleList.do?isCheck=false&target_code=${target_code}', 
			checkbox: false, rownumbers: true, selectRowButtonOnly: false, heightDiff: 30
		});
	}
	
	//指标标度修改
	function edit_scale(rowdata){
		var data = grid.getSelectedRow();
		
		$.ligerDialog.open({ 
			title: '指标标度维护',
			url : 'matEvaTargetScalePage.do?isCheck=false&matEvaScale=' + JSON.stringify(data),
			height: 370,
			width: 450,
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
		}); 
	}

</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="grid"></div>
</body>
</html>