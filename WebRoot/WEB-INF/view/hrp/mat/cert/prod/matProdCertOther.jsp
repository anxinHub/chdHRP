<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	var certFiles, grid;
	
	$(function(){
		loadToolBar();
		loadTable();
		loadGrid();
		loadData();
	})
	
	//加载头部按钮
	function loadToolBar(){
		$("#toolbarDiv").ligerToolBar({
			items: [{
				text: '关闭', id:'close', icon: 'close', click: thisClose
			}]
		})
	}
	
	//加载Table表单样式
	function loadTable(){
		autocompleteAsync("#cert_type_code", "../../queryMatCertType.do?isCheck=false", "id", "text", true, true, "", false, false, "250");
		$("#cert_code").ligerTextBox({width: 250});
		$("#prod_name").ligerTextBox({width: 250});
		autocompleteAsync("#fac_id", "../../queryHosFac.do?isCheck=false", "id", "text", true, true, "", false, false, "250");
		$("#start_date").ligerTextBox({width: 90});
		$("#end_date").ligerTextBox({
			width: 90, 
			onChangeValue: function(value){
				var d1 = new Date(value);
				var d2 = new Date();
				if(d1.getTime() >= d2.getTime()){
					$("#date_state").text("未过期");
				}else{
					$("#date_state").text("已过期");
				}
			}
		});
		$("#is_long").ligerCheckBox({
			onAfterClick: function(checked){
				if(checked){
					$("#end_date").val("");
					liger.get("end_date").setDisabled();
					$("#date_state").text("未过期");
				}else{
					liger.get("end_date").setEnabled();
				}
			}
		});
		$("#prod_alias").ligerTextBox({width: 250});
		autoCompleteByData("#is_stop", [{id: 0, text: "否"}, {id: 1, text: "是"}], "id", "text", true, true, "", true, false, "90");
		//autoCompleteByData("#authent_state", [{id: 0, text: "未认证"}, {id: 1, text: "已认证"}], "id", "text", true, true, "", true, false, "90");
		$("#new_cert_info").ligerTextBox({width: 250, disabled: true});
		$("#old_cert_info").ligerTextBox({width: 250, disabled: true});
		certFiles = $("#files").etUpload({multiple: true});
	}
	
	//修改页面回充表单值
	function loadData(){
		if(parentData.cert_id){
			ajaxJsonObjectByUrl("queryMatProdCertById.do?isCheck=false", {cert_id: parentData.cert_id}, function (res) {
				if(res.cert_code){
					liger.get("cert_type_code").setValue(res.cert_type_code);
					$('#cert_code').val(res.cert_code);
					liger.get("cert_code").setDisabled();
					$('#prod_name').val(res.prod_name);
					liger.get("fac_id").setValue(res.fac_id);
					liger.get("fac_id").setText(res.fac_code + " " + res.fac_name);
					$("#start_date").val(res.start_date);
					if(res.is_long == "1"){
						liger.get("is_long").setValue(true);
						liger.get("end_date").setDisabled();
					}else{
						$("#end_date").val(res.end_date);
					}
					$("#prod_alias").val(res.prod_alias);
					liger.get("is_stop").setValue(res.is_stop);
					$("#check_state").val(res.check_state);
					$("#check_state_name").text(res.check_state_name);
					$("#authent_state").val(res.authent_state);
					$("#authent_state_name").text(res.authent_state_name);
					$("#date_state").text(res.date_state);
					$("#new_cert_id").val(res.new_cert_id);
					$("#new_cert_info").val(res.new_cert_info);
					$("#old_cert_id").val(res.old_cert_id);
					$("#old_cert_info").val(res.old_cert_info);
					if(res.cert_files){
						certFiles.setValue(res.cert_files);
					}
					$("#old_cert_files").val(res.cert_files);
					queryCertInv();
				}
			})
		}
	}
	
	//加载表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [{
				display: '材料编码', name: 'inv_code', align: 'left', width: 140
			},{
				display: '材料名称(E)', name: 'inv_name', align: 'left', width: 180, 
				
			},{
				display: '规格型号', name: 'inv_model', align: 'left', width: 160
			},{
				display: '单位', name: 'unit_name', align: 'left', width: 90
			},{
				display: '单价', name: 'plan_price', align: 'left', width: 100
			},{
				display: '在用状态', name: 'use_state', align: 'left', width: 70
			}],
			dataAction: 'server', dataType: 'server', usePager: true, width: '100%', height: '100%', 
			url:'queryMatProdCertInvList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, //heightDiff: 30, 
			isAddRow: false, enabledEdit: true
		});
	}
	
	//查询关联材料
	function queryCertInv(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'cert_id', value: parentData.cert_id});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//关闭页面
	function thisClose(){
 		frameElement.dialog.close();
	} 
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toolbarDiv"></div>
	<table class="table-layout" style="margin-top:10px;" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>证件类型：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_type_code" validate="{required:true}" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>证件编号：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="hidden" id="cert_id" />
				<input type="text" id="cert_code" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>产品名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="prod_name" validate="{required:true}" />
			</td>

			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>生产厂商：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="fac_id" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>证件效期：
			</td> 
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input type="text" id="start_date" validate="{required:true}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td> 
						<td>&nbsp;至&nbsp;</td>
						<td>
							<input type="text" id="end_date" validate="{required:true}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td> 
						<td class="l-table-edit-td">
							<input type="checkbox" id="is_long" />长期
						</td>
					</tr>
				</table>
			</td>

			<td align="right" class="l-table-edit-td">
				产品别名：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="prod_alias" />
			</td>
		</tr>
		<tr> 
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>是否停用：
			</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input type="text" id="is_stop" />
						</td>
						<td align="right" class="l-table-edit-td" width="150px;">
							<input type="hidden" id="check_state" />
							审核状态：<span id="check_state_name">未审核</span>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td">
				认证状态：
			</td> 
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td width="90px;">
							<input type="hidden" id="authent_state" />
							<span id="authent_state_name">未认证</span>
						</td>
						<td align="right" class="l-table-edit-td" width="150px;">
							效期状态：<span id="date_state">　　　</span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				新证编号：
			</td> 
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input type="hidden" id="new_cert_id" />
							<input type="text" id="new_cert_info"/>
						</td>
					</tr>
				</table>
			</td>

			<td align="right" class="l-table-edit-td">
				老证编号：
			</td> 
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input type="hidden" id="old_cert_id" />
							<input type="text" id="old_cert_info" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				注册证图片：
			</td> 
			<td align="left" class="l-table-edit-td" colspan="3">
				<div id="files"></div>
				<input id="old_cert_files" type="hidden">
			</td>
		</tr>
	</table>
	<div id="grid"></div>
</body>
</html>