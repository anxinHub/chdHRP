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

        $("#start_date").ligerTextBox({width: 90});
        $("#end_date").ligerTextBox({width: 90});
        autodate("#start_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
		$("#auth_text").ligerTextBox({width: 160});
		$("#cert_type_text").ligerTextBox({width: 160});
		$("#fac_text").ligerTextBox({width: 194});
		$("#sup_text").ligerTextBox({width: 160});
		autoCompleteByData("#authent_state", [{id: 0, text: "未认证"}, {id: 1, text: "已认证"}], "id", "text", true, true);
		//autoCompleteByData("#is_stop", [{id: 0, text: "在用"}, {id: 1, text: "停用"}], "id", "text", true, true);
	}
	
	//加载表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [{
				display: '证件类型', name: 'cert_type_name', align: 'left', width: 100
			},{
				display: '授权书编号', name: 'auth_id', align: 'left', width: 140, 
				render: function(rowdata, index, value){
					return "<a href=javascript:edit('"+rowdata.auth_id+"','"+index+"')>"+value+"</a>"
				}
			},{
				display: '产品名称', name: 'prod_name', align: 'left', width: 180
			},{
				display: '生产厂商', name: 'fac_id', textField: "fac_name", align: 'left', width: 180
			},{
				display: '授权企业', name: 'sup_id', textField: "sup_name", align: 'left', width: 180
			},{
				display: '有效授权时段', name: 'auth_date', align: 'left', width: 160
			},{
				display: '关联材料', name: 'inv_count', align: 'center', width: 80,
				render: function(rowdata, index, value){
					return "<a href=javascript:openInv('"+index+"')>"+value+"</a>"
				}
			},{
				display: '是否新证', name: 'is_new', textField: "is_new_name", align: 'left', width: 70
			},{
				display: '审核状态', name: 'check_state', textField: "check_state_name", align: 'left', width: 70
			},{
				display: '在用状态', name: 'is_stop', textField: "is_stop_name", align: 'left', width: 70
			},{
				display: '认证状态', name: 'authent_state', textField: "authent_state_name", align: 'left', width: 70
			},{
				display: '效期状态', name: 'date_state', align: 'left', width: 70,
				render: function(rowdata, index, value){
					if(value == "已过期"){
						return "<span style='color: red'>已过期</span>"
					}
					return value;
				}
			/* },{
				display: '备注', name: 'note', align: 'left', width: 220 */
			}],
			dataAction: 'server', dataType: 'server', usePager: true, width: '100%', height: '100%', 
			url:'queryMatProdAuthorList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, //heightDiff: 30, 
			toolbar: { items: [{
				text: '查询', id: 'query', icon: 'search', click: query
			},{ line:true },{
				text: '新增', id: 'add', icon: 'add', click: add
			},{ line:true },{
				text: '认证', id:'authent', icon: 'cashier', click: authent
			},{ line:true },{
				text: '取消认证', id:'unAuthent', icon: 'uncashier', click: unAuthent
			},{ line:true },{
				text: '审核', id:'check', icon: 'blabel', click: check
			},{ line:true },{
				text: '消审', id:'unCheck', icon: 'bcancle', click: unCheck
			},{ line:true },{/* 
				text: '启用', id:'start', icon: 'flowstart', click: start
			},{ line:true },{
				text: '停用', id:'stop', icon: 'flowstop', click: stop
			},{ line:true },{
				text: '导入', id:'imp', icon: 'down', click: imp
			},{ line:true },{ */
				text: '删除', id:'remove', icon: 'delete', click: remove
			}] }
		});
	}

	//查询
	function  query(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'start_date', value: $("#start_date").val()});
		grid.options.parms.push({name: 'end_date', value: $("#end_date").val()});
		grid.options.parms.push({name: 'auth_text', value: $("#auth_text").val()});
		grid.options.parms.push({name: 'cert_type_text', value: $("#cert_type_text").val()});
		grid.options.parms.push({name: 'fac_text', value: $("#fac_text").val()});
		grid.options.parms.push({name: 'sup_text', value: $("#sup_text").val()});
		grid.options.parms.push({name: 'authent_state', value: liger.get("authent_state").getValue()});
		//grid.options.parms.push({name: 'is_stop', value: liger.get("is_stop").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}

	//添加
	function add(){
		parent.$.ligerDialog.open({ 
			title: '授权书维护',
			url : 'hrp/mat/cert/author/matProdAuthorPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data: {is_update: 0}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}

	//修改
	function edit(auth_id, rowindex){
		parent.$.ligerDialog.open({ 
			title: '授权书维护',
			url : 'hrp/mat/cert/author/matProdAuthorPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data: {auth_id: auth_id, rowindex: rowindex, is_update: 1}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//关联材料
	function openInv(index){
		var rowData = grid.getRow(index);
		var paras = {
			auth_id: rowData.auth_id, 
			fac_id: rowData.fac_id,
			fac_text: rowData.fac_code + " " + rowData.fac_name,
			sup_id: rowData.sup_id,
			sup_text: rowData.sup_code + " " + rowData.sup_name,
			prod_name: rowData.prod_name
		};
		parent.$.ligerDialog.open({ 
			title: '关联材料',
			url : 'hrp/mat/cert/author/matProdAuthorInvPage.do?isCheck=false',
			height: $(window).height()-50,
			width: $(window).width()-100,
			data: paras, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//过期换证--子页面使用该方法
	function addNewByOld(paras){
		parent.$.ligerDialog.open({ 
			title: '过期换证',
			url : 'hrp/mat/cert/author/matProdAuthorPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data: paras, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}

	//认证
	function authent(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择授权书');
			return false;
		}
		
		var ids = "";
		var error_msg = "";
		$.each(data, function (){
			ids += this.auth_id+",";
			if(this.authent_state == "2"){
				error_msg += this.auth_id+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下授权书已认证：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定认证?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdAuthorToAuthent.do?isCheck=false",{ids: ids.substr(0, ids.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {authent_state: 1, authent_state_name: '已认证'});
				    	})
					}
				});
			}
		}); 
	}

	//取消认证
	function unAuthent(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择授权书');
			return false;
		}
		
		var ids = "";
		var error_msg = "";
		$.each(data, function (){
			ids += this.auth_id+",";
			if(this.authent_state == "1"){
				error_msg += this.auth_id+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下授权书未认证：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定取消认证?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdAuthorToUnAuthent.do?isCheck=false",{ids: ids.substr(0, ids.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {authent_state: 0, authent_state_name: '未认证'});
				    	})
					}
				});
			}
		}); 
	}

	//审核
	function check(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择授权书');
			return false;
		}
		
		var ids = "";
		var error_msg = "";
		$.each(data, function (){
			ids += this.auth_id+",";
			if(this.check_state == "2"){
				error_msg += this.auth_id+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下授权书已审核：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdAuthorToCheck.do?isCheck=false",{ids: ids.substr(0, ids.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {check_state: 2, check_state_name: '已审核'});
				    	})
					}
				});
			}
		}); 
	}

	//消审
	function unCheck(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择授权书');
			return false;
		}
		
		var ids = "";
		var error_msg = "";
		$.each(data, function (){
			ids += this.auth_id+",";
			if(this.check_state == "1"){
				error_msg += this.auth_id+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下授权书未审核：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdAuthorToUnCheck.do?isCheck=false",{ids: ids.substr(0, ids.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {check_state: 1, check_state_name: '未审核'});
				    	})
					}
				});
			}
		}); 
	}

	//启用
	function start(){

		var data = grid.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择授权书');
			return false;
		}
		
		var ids = "";
		var error_msg = "";
		$.each(data, function (){
			ids += this.auth_id+",";
			if(this.is_stop == "0"){
				error_msg += this.auth_id+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下授权书已启用：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定启用?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdAuthorToStart.do?isCheck=false",{ids: ids.substr(0, ids.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {is_stop: 0, is_stop_name: '在用'});
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
			$.ligerDialog.error('请选择授权书');
			return false;
		}
		
		var ids = "";
		var error_msg = "";
		$.each(data, function (){
			ids += this.auth_id+",";
			if(this.is_stop == "1"){
				error_msg += this.auth_id+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下授权书已停用：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定停用?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdAuthorToStop.do?isCheck=false",{ids: ids.substr(0, ids.length - 1)},function (responseData){
					if(responseData.state=="true"){
				    	$.each(data, function(){
							grid.updateRow(this, {is_stop: 1, is_stop_name: '停用'});
				    	})
					}
				});
			}
		}); 
	}
	
	//导入
	function imp(){
		var para = {
			url : 'hrp/mat/cert/type/matProdAuthorImportPage.do?isCheck=false',
			title : '授权书导入',
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
			$.ligerDialog.error('请选择授权书');
			return false;
		}
		
		var ids = "";
		var error_msg = "";
		$.each(data, function (){
			ids += this.auth_id+",";
			if(this.authent_state == "2"){
				error_msg += this.auth_id+"<br/>";
			}
		})
		
		if(error_msg.length > 0){
			$.ligerDialog.error('以下授权书已认证：<br/>'+error_msg);
			return false;
		}
		
		$.ligerDialog.confirm('确定删除?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("deleteMatProdAuthor.do?isCheck=false",{ids: ids.substr(0, ids.length - 1)},function (responseData){
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
				授权日期：
			</td> 
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input type="text" id="start_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td>至</td>
						<td>
							<input type="text" id="end_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td">
				授权书：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="auth_text" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				证件类型：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_type_text" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				生产厂商：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="fac_text" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				授权企业：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="sup_text" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				认证状态：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="authent_state" />
			</td>
		</tr>
		<!-- <tr>
			<td align="right" class="l-table-edit-td">
				是否停用：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="is_stop" />
			</td>
		</tr> -->
	</table>
	<div id="grid"></div>
</body>
</html>
