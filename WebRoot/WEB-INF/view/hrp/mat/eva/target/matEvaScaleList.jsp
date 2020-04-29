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
	
	$(function (){
		loadGrid();
		query_scale();
	});
	
	//加载表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [{
				display: '标度代号', name: 'scale_code', align: 'left', width: 100, 
				render: function(rowdata, index, value){
					return "<a href=javascript:edit_scale('"+value+"','"+index+"')>"+value+"</a>"
				}
			},{
				display: '标度名称', name: 'scale_name', align: 'left', width: 120
			},{
				display: '标度内容', name: 'scale_content', align: 'left', width: 200
			},{
				display: '得分比例', name: 'scale_point', align: 'left', width: 80
			},{
				display: '上限值', name: 'high_point', align: 'left', width: 80
			},{
				display: '下限值', name: 'low_point', align: 'left', width: 80
			},{
				display: '是否停用', name: 'is_stop_name', align: 'left', width: 70
			}],
			dataAction: 'server', dataType: 'server', usePager: false, width: '100%', height: '100%', 
			url:'queryMatEvaScaleList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, heightDiff: 30, 
			toolbar: { items: [{
				text: '新增', id: 'add_scale', icon: 'add', click: add_scale
			},{ line:true },{
				text: '删除', id:'remove_scale', icon: 'delete', click: remove_scale
			},{ line:true },{
				text: '关闭', id: 'close_scale', icon: 'close', click: close_scale
			}] }
		});
	}

	//查询
	function  query_scale(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		//grid.options.parms.push({name: 'scale_code', value: node.data.scale_code});
		//加载查询条件
		grid.loadData(grid.where);
	}

	//添加
	function add_scale(){
		$.ligerDialog.open({ 
			title: '标准标度维护',
			url : 'matEvaScalePage.do?isCheck=false',
			height: 450,
			width: 450,
			data: {is_update: 0}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: false, showMax: false, 
		}); 
	}

	//修改
	function edit_scale(scale_code, rowindex){
		var data = grid.getRow(rowindex);
		$.ligerDialog.open({ 
			title: '标准标度维护',
			url : 'matEvaScalePage.do?isCheck=false',
			height: 450,
			width: 450,
			data: {matEvaScale: data, rowindex: rowindex, is_update: 1}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: false, showMax: false, 
		}); 
	}

	//删除
	function remove_scale(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择标准标度');
			return false;
		}
		
		var codes = "";
		$.each(data, function (){
			codes += this.scale_code+",";
		})
		
		$.ligerDialog.confirm('确定删除?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("deleteMatEvaScale.do?isCheck=false",{codes: codes.substr(0, codes.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	grid.deleteSelectedRow();
					}
				});
			}
		}); 
	}
	
	function close_scale(){
 		frameElement.dialog.close();
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="grid"></div>
</body>
</html>
