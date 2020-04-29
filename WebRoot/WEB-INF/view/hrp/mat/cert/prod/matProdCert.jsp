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
	var cert_info; //换证使用
	
	$(function(){
		loadToolBar();
		loadTable();
		loadData();
	})
	
	//加载头部按钮
	function loadToolBar(){
		$("#toolbarDiv").ligerToolBar({
			items: [{
				text: '保存', id: 'save', icon: 'save', click: save
			},{ line: true },{
				text: '过期换证', id: 'addNew', icon: 'communication', click: addNew
			},{ line: true },{
				text: '认证', id:'authent', icon: 'cashier', click: authent
			},{ line: true },{
				text: '取消认证', id:'unAuthent', icon: 'uncashier', click: unAuthent
			},{ line: true },{
				text: '审核', id:'check', icon: 'blabel', click: check
			},{ line: true },{
				text: '消审', id:'unCheck', icon: 'bcancle', click: unCheck
			},{ line: true },{
				text: '图片打印', id:'printImg', icon: 'print', click: printImg
			},{ line: true },{
				text: '关闭', id:'close', icon: 'close', click: thisClose
			}]
		})
	}

	//图片打印
	function printImg(){
		hrpPrintImgs();
	}
	
	//控制按钮只读
	function changeToolBar(){
		//判断认证状态
		if($("#authent_state").val() == "2"){
			liger.get("toolbarDiv").setHide("save");
			liger.get("toolbarDiv").setHide("authent");
    		//判断审核状态
    		if($("#check_state").val() == "2"){
    			liger.get("toolbarDiv").setShow("addNew");
    			liger.get("toolbarDiv").setHide("unAuthent");
    			liger.get("toolbarDiv").setHide("check");
    			liger.get("toolbarDiv").setShow("unCheck");
    			liger.get("toolbarDiv").setShow("printImg");
    		}else{
    			liger.get("toolbarDiv").setHide("addNew");
    			liger.get("toolbarDiv").setShow("unAuthent");
    			liger.get("toolbarDiv").setShow("check");
    			liger.get("toolbarDiv").setHide("unCheck");
    			liger.get("toolbarDiv").setHide("printImg");
    		}
		}else{
			liger.get("toolbarDiv").setShow("save");
			liger.get("toolbarDiv").setHide("addNew");
			liger.get("toolbarDiv").setShow("authent");
			liger.get("toolbarDiv").setHide("unAuthent");
			liger.get("toolbarDiv").setHide("check");
			liger.get("toolbarDiv").setHide("unCheck");
			liger.get("toolbarDiv").setHide("printImg");
		}
	}
	
	//加载Table表单样式
	function loadTable(){
		autocompleteAsync("#cert_type_code", "../../queryMatCertType.do?isCheck=false", "id", "text", true, true, {cert_kind_code: 1}, false, false, "250");
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
					$('#cert_id').val(res.cert_id);
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
					//换证使用
					cert_info = res.cert_code + " " + res.cert_date;
					//改变按钮
					changeToolBar();
					//显示新老证查看超链接
					$("#newCertBtn").show();
					$("#oldCertBtn").show();
					//显示关联材料
					$("#grid").show();
					loadGrid();
					queryCertInv();
				}
			})
		}else{
			liger.get("toolbarDiv").setHide("addNew");
			liger.get("toolbarDiv").setHide("authent");
			liger.get("toolbarDiv").setHide("unAuthent");
			liger.get("toolbarDiv").setHide("check");
			liger.get("toolbarDiv").setHide("unCheck");
			liger.get("toolbarDiv").setHide("printImg");
			if(parentData.old_cert_id){
				liger.get("cert_type_code").setValue(parentData.cert_type_code);
				liger.get("cert_type_code").setText(parentData.cert_type_text);
				$("#prod_name").val(parentData.prod_name);
				liger.get("fac_id").setValue(parentData.fac_id);
				liger.get("fac_id").setText(parentData.fac_text);
				$("#prod_alias").val(parentData.prod_alias);
				$("#old_cert_id").val(parentData.old_cert_id);
				$("#old_cert_id").val(parentData.old_cert_id);
				$("#old_cert_info").val(parentData.old_cert_info);
			}
		}
	}
	
	//保存
	function save(){
		var cert_type_code = liger.get("cert_type_code").getValue();
		if(!cert_type_code) {
			$.ligerDialog.warn("证件类型不能为空！");
			return false;
		}
		
		if(!$("#cert_code").val()) {
			$.ligerDialog.warn("证件编号不能为空！");
			return false;
		}
		if(!$("#prod_name").val()) {
			$.ligerDialog.warn("产品名称不能为空！");
			return false;
		}
		
		var fac_id = liger.get("fac_id").getValue();
		if(!fac_id) {
			$.ligerDialog.warn("生产厂商不能为空！");
			return false;
		}

		if(!$("#start_date").val()) {
			$.ligerDialog.warn("证件开始日期不能为空！");
			return false;
		}
		if(!$("#end_date").val() && !$("#is_long").prop("checked")) {
			$.ligerDialog.warn("证件结束日期不能为空！");
			return false;
		}
		
		var is_stop = liger.get("is_stop").getValue();
		if(!is_stop) {
			$.ligerDialog.warn("是否停用不能为空！");
			return false;
		}

		var formData = new FormData();
		formData.append("cert_id", $("#cert_id").val());
		formData.append("cert_type_code", liger.get("cert_type_code").getValue());
		formData.append("cert_code", $("#cert_code").val());
		formData.append("prod_name", $("#prod_name").val());
		formData.append("fac_id", liger.get("fac_id").getValue());
		formData.append("start_date", $("#start_date").val());
		formData.append("end_date", $("#end_date").val());
		formData.append("is_long", $("#is_long").prop("checked") ? 1 : 0);
		formData.append("prod_alias", $("#prod_alias").val());
		formData.append("is_stop", liger.get("is_stop").getValue());
		formData.append("new_cert_id", $("#new_cert_id").val());
		formData.append("new_cert_info", $("#new_cert_info").val());
		formData.append("old_cert_id", $("#old_cert_id").val());
		formData.append("old_cert_info", $("#old_cert_info").val());
		
		//注册证图片
		var certFileList = certFiles.getValues();
		var new_cert_files = "";
		for (i = 0; i < certFileList.length; i++) {
			if (typeof certFileList[i] == "string") {
				new_cert_files += certFileList[i] + ",";
			}
			formData.append("cert_files", certFileList[i]);
		}
		formData.append("old_cert_files", $("#old_cert_files").val());
		formData.append("new_cert_files", new_cert_files.substring(0, new_cert_files.length - 1));
		
		ajaxLigerPostFormData({
			url: "saveMatProdCert.do?isCheck=false", 
			para: formData, 
			success: function (res) {
				if (res.state == "true") {
					$('#cert_id').val(res.cert_id);
					liger.get("cert_code").setDisabled();
					if (res.cert_files) {
						certFiles.setValues(res.cert_files.split(","));
						$('#old_cert_files').val(res.cert_files);
					}
					//改变按钮
					changeToolBar();
					//显示新老证查看超链接
					$("#newCertBtn").show();
					$("#oldCertBtn").show();
					
					// 刷新父页面的表格
					if(typeof(parentFrameUse()) != "undefine" && typeof(parentFrameUse().query) != "undefine"){
						parentFrameUse().query();
					}
				}
			}
		});
	}
	
	//过期换证
	function addNew(){
		//判断是否存在新证
		if($("#new_cert_id").val()){
			$.ligerDialog.warn('已存在新证！');
			return false;
		}
		var paras = {
			old_cert_id: $("#cert_id").val(), 
			old_cert_info: cert_info, 
			cert_type_code: liger.get("cert_type_code").getValue(),
			cert_type_text: liger.get("cert_type_code").getText(),
			prod_name: $("#prod_name").val(),
			fac_id: liger.get("fac_id").getValue(),
			fac_text: liger.get("fac_id").getText(),
			prod_alias: $("#prod_alias").val()
		};
		parentFrameUse().addNewByOld(paras);
		/* 通过该页面打开保存的时候刷新不了主页面
			parent.$.ligerDialog.open({ 
			title: '过期换证',
			url : 'hrp/mat/cert/prod/matProdCertPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data: paras, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  */
		thisClose();
	}

	//认证
	function authent(){
		if($("#authent_state").val() == "2"){
			$.ligerDialog.error('产品注册证已认证！');
			return false;
		}
		
		$.ligerDialog.confirm('确定认证?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdCertToAuthent.do?isCheck=false",{ids: $("#cert_id").val()},function (responseData){
					if(responseData.state=="true"){
						//grid.updateRow(parentData.rowindex, {authent_state: 2, authent_state_name: '已认证'});
			    		$("#authent_state").val("2");
			    		$("#authent_state_name").text("已认证");
			    		changeToolBar();
					}
				});
			}
		}); 
	}

	//取消认证
	function unAuthent(){
		if($("#authent_state").val() == "1"){
			$.ligerDialog.error('产品注册证未认证！');
			return false;
		}
		
		$.ligerDialog.confirm('确定取消认证?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdCertToUnAuthent.do?isCheck=false",{ids: $("#cert_id").val()},function (responseData){
					if(responseData.state=="true"){
						//grid.updateRow(parentData.rowindex, {authent_state: 1, authent_state_name: '未认证'});
			    		$("#authent_state").val("1");
			    		$("#authent_state_name").text("未认证");
			    		changeToolBar();
					}
				});
			}
		}); 
	}

	//审核
	function check(){
		if($("#check_state").val() == "2"){
			$.ligerDialog.error('产品注册证已审核！');
			return false;
		}
		
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdCertToCheck.do?isCheck=false",{ids: $("#cert_id").val()},function (responseData){
					if(responseData.state=="true"){
						//grid.updateRow(parentData.rowindex, {check_state: 2, check_state_name: '已审核'});
			    		$("#check_state").val("2");
			    		$("#check_state_name").text("已审核");
			    		changeToolBar();
					}
				});
			}
		}); 
	}

	//消审
	function unCheck(){

		if($("#check_state").val() == "1"){
			$.ligerDialog.error('产品注册证未审核！');
			return false;
		}
		
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateMatProdCertToUnCheck.do?isCheck=false",{ids: $("#cert_id").val()},function (responseData){
					if(responseData.state=="true"){
						//grid.updateRow(parentData.rowindex, {check_state: 1, check_state_name: '未审核'});
			    		$("#check_state").val("1");
			    		$("#check_state_name").text("未审核");
			    		changeToolBar();
					}
				});
			}
		}); 
	}
	
	//加载表格
	function loadGrid(){
		grid = $("#grid").ligerGrid({
			columns: [{
				display: '材料编码', name: 'inv_id', textField: "inv_code", align: 'left', width: 140
			},{
				display: '材料名称(E)', name: 'inv_name', align: 'left', width: 180, 
				editor: {
					type: 'select',
					valueField: 'inv_name',
					textField: 'inv_name',
					selectBoxWidth: '80%',
					selectBoxHeight: 180,
					grid: {
						columns: [{
							display: '材料编码', name: 'inv_code', align: 'left', width: 140
						},{
							display: '材料名称', name: 'inv_name', align: 'left', width: 180, 
						},{
							display: '规格型号', name: 'inv_model', align: 'left', width: 160
						},{
							display: '单位', name: 'unit_name', align: 'left', width: 90
						},{
							display: '单价', name: 'plan_price', align: 'left', width: 100
						},{
							display: '在用状态', name: 'use_state', align: 'left', width: 70
						}],
						switchPageSizeApplyComboBox: false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						gid: "invGrid", 
						url: 'queryMatInvList.do?isCheck=false',
						pageSize: 100,
						onSuccess: function (data, g) { //加载完成时默认选中
							if (grid.editor.editParm) {
								var editor = grid.editor.editParm.record;
								var item = data.Rows.map(function (v, i) {
									return v.inv_name;
								});
								var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
								//加载完执行
								setTimeout(function () {
									g.select(data.Rows[index]);
								}, 80);
							}
						}
					},
					delayLoad: true, keySupport: true, autocomplete: true,// rownumbers : true,
					onSuccess: function (data, grid) {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
				},
				render: function (rowdata, rowindex, value) {
					return rowdata.inv_name;
				}
			},{
				display: '规格型号', name: 'inv_model', align: 'left', width: 160
			},{
				display: '单位', name: 'unit_name', align: 'left', width: 90
			},{
				display: '单价', name: 'plan_price', align: 'left', width: 100
			},{
				display: '在用状态', name: 'use_state', align: 'left', width: 70
			}],
			dataAction: 'server', dataType: 'server', usePager: false, width: '100%', height: '100%', 
			url:'queryMatProdCertInvList.do?isCheck=false', delayLoad: true, 
			checkbox: true, rownumbers: true, selectRowButtonOnly: false, heightDiff: 30, 
			isAddRow: false, enabledEdit: true, onBeforeEdit: f_onBeforeEdit, 
			toolbar: { items: [{
				text: '选择材料', id: 'chooseInv', icon: 'extend', click: chooseInv
			},{ line:true },{
				text: '删除', id:'removeInv', icon: 'delete', click: removeInv
			},{ line:true },{
				text: '添加行', id:'addInvRow', icon: 'add', click: addInvRow
			},{ line:true },{
				text: '保存', id:'saveInv', icon: 'save', click: saveInv/* 
			},{ line:true },{
				text: '取消关联', id:'remove', icon: 'delete', click: remove */
			}] }
		});
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
	}
	
	//选中回充数据
	function f_onSelectRow_detail(data) {
		//回充数据 
		if (column_name == "inv_name") {
			grid.updateRow(rowindex_id, {
				inv_id: data.inv_id,
				inv_code: data.inv_code,
				inv_name: data.inv_name,
				inv_model: data.inv_model == null ? "" : data.inv_model,
				unit_name: data.unit_name == null ? "" : data.unit_name,
				plan_price: data.plan_price == null ? "" : data.plan_price,
				use_state: data.use_state == null ? 0 : data.use_state
			});
			/* setTimeout(function (){
				grid.endEditToNext();
			},300) */
		}
		//手动移除grid IE下只能移除......   隐藏不生效
		// $('.l-box-select-lookup').remove();
		return true;
	}
	
	//查询关联材料
	function queryCertInv(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'cert_id', value: $("#cert_id").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	//选择材料
	function chooseInv(){
		parent.$.ligerDialog.open({ 
			title: '选择材料',
			url : 'hrp/mat/cert/prod/matInvListPage.do?isCheck=false',
			height: $(window).height()-50,
			width: $(window).width()-100,
			data: {cert_id: $("#cert_id").val()}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//删除材料
	function removeInv(){
    	grid.deleteSelectedRow();
	}
	
	//保存
	function saveInv(){
		var data = grid.getData();
		if(data.length == 0){
			$.ligerDialog.error('请选择材料！');
			return false;
		}
		
		var inv_count = 0;
		$.each(data, function(index, item){
			if(item.inv_id){
				inv_count ++;
			}
		});
		
		if(inv_count == 0){
			$.ligerDialog.error('请选择材料！');
			return false;
		}
		
		var paras = {
			cert_id: $("#cert_id").val(), 
			cert_code: $("#cert_code").val(), 
			allData: JSON.stringify(data)
		};

		ajaxJsonObjectByUrl("saveMatProdCertInv.do?isCheck=false", paras, function(res){
			if(res.state == "true"){
				queryCertInv();
			}
		});
	}
	
	//添加行
	function addInvRow(){
		grid.addRow();
	}
	
	//选择新证
	function chooseNewCert(){
		var paras = {
			cert_id: $("#cert_id").val(), 
			cert_info: cert_info, 
			old_cert_id: $("#old_cert_id").val(),
			fac_id: liger.get("fac_id").getValue()
		};
		parent.$.ligerDialog.open({ 
			title: '选择新证',
			url : 'hrp/mat/cert/prod/matProdCertChoosePage.do?isCheck=false',
			height: $(window).height()-50,
			width: $(window).width()-100,
			data: paras, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//更新选择后的新证信息
	function changeNewCert(new_cert_id, new_cert_info){
		$("#new_cert_id").val(new_cert_id);
		$("#new_cert_info").val(new_cert_info);
	}
	
	//取消新老证关联
	function removeNewCert(){
		$.ligerDialog.confirm('确定取消新老证关联?', function (yes){
			if(yes){
				var paras = {
					cert_id: $("#cert_id").val(), 
					new_cert_id: $("#new_cert_id").val()
				}
				ajaxJsonObjectByUrl("updateMatProdCertToUnNewCert.do?isCheck=false", paras, function (responseData){
					if(responseData.state=="true"){
				    	changeNewCert("", "");
					}
				});
			}
		}); 
	}
	
	//查看新证
	function openNewCert(){
		openCert($("#new_cert_id").val())
	}
	
	//查看老证 
	function openOldCert(){
		openCert($("#old_cert_id").val())
	}
	
	//打开证件
	function openCert(cert_id){
		if(!cert_id){
			$.ligerDialog.warn('不存在新老证！');
			return false;
		}
		parent.$.ligerDialog.open({ 
			title: '新老证查看',
			url : 'hrp/mat/cert/prod/matProdCertOtherPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data: {cert_id: cert_id}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
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
						<td align="left" class="l-table-edit-td" id="newCertBtn" style="display: none;">
							<a href="#" onClick="chooseNewCert()">选择新证</a>
							<br />
							<a href="#" onClick="openNewCert()">查看</a>
							&nbsp;
							<a href="#" onClick="removeNewCert()">取消</a>
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
						<td align="left" class="l-table-edit-td" id="oldCertBtn" style="display: none;">
							<a href="#" onClick="openOldCert()">查看</a>
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
	<div id="grid" style="display: none"></div>
</body>
</html>