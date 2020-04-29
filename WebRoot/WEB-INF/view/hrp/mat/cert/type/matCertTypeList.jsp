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
		loadTable();
		loadGrid();
	});
	
	//加载表单
	function loadTable(){

		$("#cert_type_text").ligerTextBox({width:180});
		autocompleteAsync("#cert_kind_code", "../../queryMatSysList.do?isCheck=false", "id", "text", true, true, {field_code : "cert_kind_code"});
		autoCompleteByData("#is_stop", [{id: 0, text: "否"}, {id: 1, text: "是"}], "id", "text", true, true);
	}
	
	//加载表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [{
				display: '证件类型编号', name: 'cert_type_code', align: 'left', width: 100, 
				render: function(rowdata, index, value){
					return "<a href=javascript:edit('"+value+"','"+index+"')>"+value+"</a>"
				}
			},{
				display: '证件类型名称', name: 'cert_type_name', align: 'left', width: 180
			},{
				display: '证件类别', name: 'cert_kind_code',  textField: "cert_kind_name", align: 'left', width: 180
			},{
				display: '是否需经营范围', name: 'is_cert_busi', textField: "is_cert_busi_name", align: 'left', width: 100
			},{
				display: '是否需证件名称', name: 'is_cert_name', textField: "is_cert_name_name", align: 'left', width: 100
			},{
				display: '是否停用', name: 'is_stop', textField: "is_stop_name", align: 'left', width: 70
			/* },{
				display: '备注', name: 'note', align: 'left', width: 220 */
			}],
			dataAction: 'server', dataType: 'server', usePager: true, width: '100%', height: '100%', 
			url:'queryMatCertTypeList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, //heightDiff: 30, 
			toolbar: { items: [{
				text: '查询', id: 'query', icon: 'search', click: query
			},{ line:true },{
				text: '新增', id: 'add', icon: 'add', click: add
			},{ line:true },{
				text: '启用', id:'start', icon: 'flowstart', click: start
			},{ line:true },{
				text: '停用', id:'stop', icon: 'flowstop', click: stop
			},{ line:true },{
				text: '导入', id:'imp', icon: 'down', click: imp
			},{ line:true },{
				text: '删除', id:'remove', icon: 'delete', click: remove
			}] }
		});
	}

	//查询
	function  query(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'cert_type_text', value: $("#cert_type_text").val()});
		grid.options.parms.push({name: 'cert_kind_code', value: liger.get("cert_kind_code").getValue()});
		grid.options.parms.push({name: 'is_stop', value: liger.get("is_stop").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}

	//添加
	function add(){
		$.ligerDialog.open({ 
			title: '证件类型维护',
			url : 'matCertTypePage.do?isCheck=false',
			height: 350,
			width: 450,
			data: {is_update: 0}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
		}); 
	}

	//修改
	function edit(cert_type_code, rowindex){
		var data = grid.getRow(rowindex);
		$.ligerDialog.open({ 
			title: '证件类型维护',
			url : 'matCertTypePage.do?isCheck=false',
			height: 350,
			width: 450,
			data: {matCertType: data, rowindex: rowindex, is_update: 1}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
		}); 
	}

	//启用
	function start(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择证件类型');
			return false;
		}
		
		var codes = "";
		var error_msg = "";
		$.each(data, function (){
			codes += this.cert_type_code+",";
			if(this.is_stop == "0"){
				error_msg += this.cert_type_code+" "+this.cert_type_name+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下证件类型已启用：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定启用?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatCertTypeToStart.do?isCheck=false",{codes: codes.substr(0, codes.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {is_stop: 0, is_stop_name: '否'});
				    	})
					}
				});
			}
		}); 
	}

	//停用
	function stop(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择证件类型');
			return false;
		}
		
		var codes = "";
		var error_msg = "";
		$.each(data, function (){
			codes += this.cert_type_code+",";
			if(this.is_stop == "1"){
				error_msg += this.cert_type_code+" "+this.cert_type_name+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下证件类型已停用：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定停用?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatCertTypeToStop.do?isCheck=false",{codes: codes.substr(0, codes.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {is_stop: 1, is_stop_name: '是'});
				    	})
					}
				});
			}
		}); 
	}
	
	//导入
	function imp(){
		var para = {
			url : 'hrp/mat/cert/type/matCertTypeImportPage.do?isCheck=false',
			title : '证件类型导入',
			width : 0,
			height : 0,
			isShowMin : false,
			isModal : true,
			data : {
				grid : grid
			}
		};
		parent.openDialog(para);
	}

	//删除
	function remove(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择证件类型');
			return false;
		}
		
		var codes = "";
		$.each(data, function (){
			codes += this.cert_type_code+",";
		})
		
		$.ligerDialog.confirm('确定删除?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("deleteMatCertType.do?isCheck=false",{codes: codes.substr(0, codes.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	grid.deleteSelectedRow();
					}
				});
			}
		}); 
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table class="table-layout"  width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				证件类型信息：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_type_text" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				证件类别：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_kind_code" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				是否停用：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="is_stop" />
			</td>
		</tr>
	</table>
	<div id="grid"></div>
</body>
</html>
