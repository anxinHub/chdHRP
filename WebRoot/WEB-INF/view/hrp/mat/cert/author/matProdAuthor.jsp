<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow-x:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	var certFiles = [];
	var $auth_level, facFromData = {};
	var auth_info; //换证使用
	
	$(function(){
		$auth_level = 1;
		loadToolBar();
		loadTable();
		addDetailTab();
		loadDetailTab();
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
				text: '关联材料', id:'openInv', icon: 'initwage', click: openInv
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
		liger.get("toolbarDiv").setShow("openInv");
		liger.get("toolbarDiv").setShow("printImg");
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
		$("#auth_id").ligerTextBox({width: 250, disabled: true});
		autocomplete("#cert_type_code", "../../queryMatCertType.do?isCheck=false", "id", "text", true, true, {cert_kind_code: 2}, false, false, "250");
		$("#fac_id").ligerComboBox({
			width: 250, 
			url: "../../queryHosFac.do?isCheck=false", 
			valueField: 'id',
			textField: 'text',
			autocomplete: true,
			highLight: true,
			defaultSelect: false,
			keySupport: true, 
			onSelected: function(value, text){
				liger.get("fac_id_from1").setValue("1,"+value);
				liger.get("fac_id_from1").setText(text);
			}
		});
		
		$("#prod_name").ligerTextBox({width: 250});
		$("#auth_date").ligerTextBox({width: 250, disabled: true});
		
		autoCompleteByData("#is_stop", [{id: 0, text: "否"}, {id: 1, text: "是"}], "id", "text", true, true, "", true, false, "90");
		$("#new_auth_info").ligerTextBox({width: 480, disabled: true});
		$("#old_auth_info").ligerTextBox({width: 480, disabled: true});
	}
	
	//修改页面回充表单值
	function loadData(){
		if(parentData.auth_id){
			ajaxJsonObjectByUrl("queryMatProdAuthorById.do?isCheck=false", {auth_id: parentData.auth_id}, function (res) {
				if(res.auth_id){
					$('#auth_id').val(res.auth_id)
					liger.get("cert_type_code").setValue(res.cert_type_code);
					liger.get("cert_type_code").setText(res.cert_type_code + " " + res.cert_type_name);
					liger.get("fac_id").setValue(res.fac_id);
					liger.get("fac_id").setText(res.fac_code + " " + res.fac_name);
					$("#sup_id").val(res.sup_id);
					$("#sup_text").val(res.sup_code + " " + res.sup_name);
					$("#prod_name").val(res.prod_name);
					$("#auth_date").val(res.auth_date);
					liger.get("is_stop").setValue(res.is_stop);
					$("#check_state").val(res.check_state);
					$("#check_state_name").text(res.check_state_name);
					$("#authent_state").val(res.authent_state);
					$("#authent_state_name").text(res.authent_state_name);
					$("#date_state").text(res.date_state);
					$("#new_auth_id").val(res.new_auth_id);
					$("#new_auth_info").val(res.new_auth_info);
					$("#old_auth_id").val(res.old_auth_id);
					$("#old_auth_info").val(res.old_auth_info);
					
					var detailJson = eval('(' +res.detailJson+ ')');
					$.each(detailJson, function(i, item){
						if(item.auth_level > 1){
							$("#addItem" + $auth_level).click();
							/* 
							$auth_level +=1;
							addDetailTab();
							loadDetailTab(); 
							*/
						}
						
						liger.get("fac_id_from"+item.auth_level).setValue(item.from_type+","+item.fac_id_from);
						liger.get("fac_id_from"+item.auth_level).setText(item.fac_text_from);
						liger.get("fac_id_to"+item.auth_level).setValue(item.to_type+","+item.fac_id_to);
						liger.get("fac_id_to"+item.auth_level).setText(item.fac_text_to);
						$("#start_date"+item.auth_level).val(item.start_date);
						if(item.is_long == "1"){
							liger.get("is_long"+item.auth_level).setValue(true);
							liger.get("end_date"+item.auth_level).setDisabled();
						}else{
							$("#end_date"+item.auth_level).val(item.end_date);
						}
						
						$("#auth_detail_id"+item.auth_level).val(item.auth_detail_id);
						if (item.cert_files) {
							certFiles[item.auth_level].setValues(item.cert_files);
							$("#old_cert_files"+item.auth_level).val(item.cert_files.join(","));
						}
					})
					//换证使用
					auth_info = res.auth_id + " " + res.auth_date;
					//改变按钮
					changeToolBar();
					//显示新老证查看超链接
					$("#newAuthorBtn").show();
					$("#oldAuthorBtn").show();
				}
			})
		}else{
			liger.get("toolbarDiv").setHide("openInv");
			liger.get("toolbarDiv").setHide("addNew");
			liger.get("toolbarDiv").setHide("authent");
			liger.get("toolbarDiv").setHide("unAuthent");
			liger.get("toolbarDiv").setHide("check");
			liger.get("toolbarDiv").setHide("unCheck");
			liger.get("toolbarDiv").setHide("printImg");
			//添加页面默认是否停用为否
			liger.get("is_stop").setValue(0);
			//判断是否为过期换证
			if(parentData.old_auth_id){
				liger.get("cert_type_code").setValue(parentData.cert_type_code);
				liger.get("cert_type_code").setText(parentData.cert_type_text);
				liger.get("fac_id").setValue(parentData.fac_id);
				liger.get("fac_id").setText(parentData.fac_text);
				$("#prod_name").val(parentData.prod_name);
				$("#old_auth_id").val(parentData.old_auth_id);
				$("#old_auth_info").val(parentData.old_auth_info);
				liger.get("fac_id_from1").setValue("1,"+parentData.fac_id);
				liger.get("fac_id_from1").setText(parentData.fac_text);
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
		
		if(!$("#prod_name").val()) {
			$.ligerDialog.warn("产品名称不能为空！");
			return false;
		}
		
		var fac_id = liger.get("fac_id").getValue();
		if(!fac_id) {
			$.ligerDialog.warn("生产厂商不能为空！");
			return false;
		}
		
		var is_stop = liger.get("is_stop").getValue();
		if(!is_stop) {
			$.ligerDialog.warn("是否停用不能为空！");
			return false;
		}

		var formData = new FormData();
		formData.append("auth_id", $("#auth_id").val());
		formData.append("cert_type_code", liger.get("cert_type_code").getValue());
		formData.append("fac_id", liger.get("fac_id").getValue());
		formData.append("prod_name", $("#prod_name").val());
		formData.append("is_stop", liger.get("is_stop").getValue());
		formData.append("new_auth_id", $("#new_auth_id").val());
		formData.append("new_auth_info", $("#new_auth_info").val());
		formData.append("old_auth_id", $("#old_auth_id").val());
		formData.append("old_auth_info", $("#old_auth_info").val());
		formData.append("auth_level", $auth_level);
		
		var flag = false;  //判断是否跳出循环
		for(var i = 1; i <= $auth_level; i++){
			//判断最后一次目标企业必须为供应商
			if(i == $auth_level){
				if(!liger.get("fac_id_to" + i).getValue()) {
					$.ligerDialog.warn(i+"级授权：目标企业不能为空！");
					flag = true;
					break;
				}
				if(liger.get("fac_id_to" + i).getValue().split(",")[0] != "2"){
					$.ligerDialog.warn(i+"级授权：末级授权目标企业必须为供应商！");
					flag = true;
					break;
				}
				if(!$("#start_date" + i).val()) {
					$.ligerDialog.warn(i+"级授权：开始日期不能为空！");
					flag = true;
					break;
				}
				if(!$("#end_date" + i).val() && !$("#is_long" + i).prop("checked")) {
					$.ligerDialog.warn(i+"级授权：结束日期不能为空！");
					flag = true;
					break;
				}
				if($("#end_date" + i).val() && new Date($("#end_date" + i).val()) < new Date($("#start_date" + i).val())){
					$.ligerDialog.warn(i+"级授权：结束日期不能大于开始日期！");
					flag = true;
					break;
				}
				
				//反写效期状态
				if($("#is_long" + i).prop("checked") || new Date($("#end_date" + i).val()) > new Date()){
					$('#date_state').text("未过期");
				}else{
					$('#date_state').text("已过期");
				}
				//供应商
				$('#sup_id').val(liger.get("fac_id_to" + i).getValue().split(",")[1]);
				$('#sup_text').val(liger.get("fac_id_to" + i).getText());
			}
			formData.append("auth_detail_id" + i, $("#auth_detail_id" + i).val());
			formData.append("from_type" + i, liger.get("fac_id_from" + i).getValue().split(",")[0]);
			formData.append("fac_id_from" + i, liger.get("fac_id_from" + i).getValue().split(",")[1]);
			formData.append("to_type" + i, liger.get("fac_id_to" + i).getValue().split(",")[0]);
			formData.append("fac_id_to" + i, liger.get("fac_id_to" + i).getValue().split(",")[1]);
			formData.append("start_date" + i, $("#start_date" + i).val());
			formData.append("end_date" + i, $("#end_date" + i).val());
			formData.append("is_long" + i, $("#is_long" + i).prop("checked") ? 1 : 0);
			
			var new_cert_files = "";
			var fileList = certFiles[i].getValues();
			for (var j = 0; j < fileList.length; j++) {
				if(typeof fileList[j] == "string"){
					new_cert_files += fileList[j] + ",";
				}
				formData.append("fileList" + i, fileList[j]);
			}
			formData.append("new_cert_files" + i, new_cert_files.substring(0, new_cert_files.length-1));
			formData.append("old_cert_files" + i, $("#old_cert_files" + i).val());
		};
		
		if(flag){
			return false;
		}
		
		ajaxLigerPostFormData({
			url: "saveMatProdAuthor.do?isCheck=false", 
			para: formData, 
			success: function (res) {
				if (res.state == "true") {
					//反写ID
					$('#auth_id').val(res.auth_id);
					//反写日期
					$('#auth_date').val(res.auth_date);
					//用于新老证替换
					auth_info = res.auth_id + " " + res.auth_date;
					//明细ID及图片信息
					for(var i =1; i <= $auth_level; i++){
						if (res['cert_files' + i]) {
							$("#auth_detail_id" + i).val(res['auth_detail_id' + i]);
							certFiles[i].setValues(res['cert_files' + i].split(","));
							$("#old_cert_files" + 1).val(res['cert_files' + i]);
						}
					}
					//改变按钮
					changeToolBar();
					//显示新老证查看超链接
					$("#newAuthorBtn").show();
					$("#oldAuthorBtn").show();
					
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
		//判断是否存在新授权
		if($("#new_auth_id").val()){
			$.ligerDialog.warn('已存在新授权！');
			return false;
		}
		var paras = {
			old_auth_id: $("#auth_id").val(), 
			old_auth_info: auth_info, 
			cert_type_code: liger.get("cert_type_code").getValue(),
			cert_type_text: liger.get("cert_type_code").getText(),
			prod_name: $("#prod_name").val(),
			fac_id: liger.get("fac_id").getValue(),
			fac_text: liger.get("fac_id").getText()
		};
		parentFrameUse().addNewByOld(paras);
		/* 通过该页面打开保存的时候刷新不了主页面
			parent.$.ligerDialog.open({ 
			title: '过期换证',
			url : 'hrp/mat/cert/author/matProdAuthorPage.do?isCheck=false',
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
				ajaxJsonObjectByUrl("updateMatProdAuthorToAuthent.do?isCheck=false",{ids: $("#auth_id").val()},function (responseData){
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
				ajaxJsonObjectByUrl("updateMatProdAuthorToUnAuthent.do?isCheck=false",{ids: $("#auth_id").val()},function (responseData){
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
				ajaxJsonObjectByUrl("updateMatProdAuthorToCheck.do?isCheck=false",{ids: $("#auth_id").val()},function (responseData){
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
				ajaxJsonObjectByUrl("updateMatProdAuthorToUnCheck.do?isCheck=false",{ids: $("#auth_id").val()},function (responseData){
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
	
	//关联材料
	function openInv(){
		var paras = {
			auth_id: $("#auth_id").val(), 
			fac_id: liger.get("fac_id").getValue(),
			fac_text: liger.get("fac_id").getText(),
			sup_id: $("#sup_id").val(),
			sup_text: $("#sup_text").val(),
			prod_name: $("#prod_name").val()
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
	
	//选择新证
	function chooseNewAuthor(){
		var paras = {
			auth_id: $("#auth_id").val(), 
			auth_info: auth_info, 
			old_auth_id: $("#old_auth_id").val(),
			fac_id: liger.get("fac_id").getValue(),
			sup_id: $("#sup_id").val()
		};
		parent.$.ligerDialog.open({ 
			title: '选择新授权',
			url : 'hrp/mat/cert/author/matProdAuthorChoosePage.do?isCheck=false',
			height: $(window).height()-50,
			width: $(window).width()-100,
			data: paras, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//更新选择后的新授权信息
	function changeNewAuthor(new_auth_id, new_auth_info){
		$("#new_auth_id").val(new_auth_id);
		$("#new_auth_info").val(new_auth_info);
	}
	
	//取消新老授权关联
	function removeNewAuthor(){
		$.ligerDialog.confirm('确定取消新老授权关联?', function (yes){
			if(yes){
				var paras = {
					auth_id: $("#auth_id").val(), 
					new_auth_id: $("#new_auth_id").val()
				}
				ajaxJsonObjectByUrl("updateMatProdAuthorToUnNewCert.do?isCheck=false", paras, function (responseData){
					if(responseData.state=="true"){
				    	changeNewAuthor("", "");
					}
				});
			}
		}); 
	}
	
	//查看新授权
	function openNewAuthor(){
		openAuthor($("#new_auth_id").val())
	}
	
	//查看老授权 
	function openOldAuthor(){
		openAuthor($("#old_auth_id").val())
	}
	
	//打开授权
	function openAuthor(auth_id){
		if(!auth_id){
			$.ligerDialog.warn('不存在新老授权！');
			return false;
		}
		parent.$.ligerDialog.open({ 
			title: '新老授权查看',
			url : 'hrp/mat/cert/author/matProdAuthorOtherPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data: {auth_id: auth_id}, 
			modal: true, showToggle: false, showMax: false,
			isResize: true, showMin: true, showMax: false, 
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	//增加授权级次
	function addDetailTab(){
		var str = '';
		str += '<div id="detailTab'+$auth_level+'" style="margin-top:10px; margin-left: 20px; ">';
		str += '<hr/>';
		str += '<table class="table-layout">';
		str += '<tr>';
		str += '<td align="right" class="l-table-edit-td">';
		str += '<span style="color: #666666; font-weight: bold; font-size: 16px;">'+$auth_level+'级授权</span>';
		str += '</td>';
		str += '<td align="center" class="l-table-edit-td">';
		str += '<button id="addItem'+$auth_level+'" style="width: 60px;">增加</button>';
		str += '<button id="removeItem'+$auth_level+'" style="margin-left: 10px; width: 60px;" >移除</button>';
		str += '</td>';
		str += '<td><input id="auth_detail_id'+$auth_level+'" type="hidden" /></td>';
		str += '<td></td>';
		str += '</tr>';
		str += '<tr>';
		str += '<td align="right" class="l-table-edit-td">';
		str += '<span style="color: red">*</span>发起企业：';
		str += '</td>';
		str += '<td align="left" class="l-table-edit-td">';
		str += '<input id="fac_id_from'+$auth_level+'" type="text" validate="{required:true}" />';
		str += '</td>';
		str += '<td align="right" class="l-table-edit-td" width="120px;">';
		str += '<span style="color: red">*</span>附件：';
		str += '</td>';
		str += '<td align="left" class="l-table-edit-td" rowspan="3">';
		str += '<div id="cert_files'+$auth_level+'"></div>';
		str += '<input id="old_cert_files'+$auth_level+'" type="hidden">';
		str += '</td>';
		str += '</tr>';
		str += '<tr>';
		str += '<td align="right" class="l-table-edit-td">';
		str += '<span style="color: red">*</span>目标企业：';
		str += '</td>';
		str += '<td align="left" class="l-table-edit-td">';
		str += '<input id="fac_id_to'+$auth_level+'" type="text" validate="{required:true}" />';
		str += '</td>';
		str += '<td align="left" class="l-table-edit-td">';
		str += '<a id="eidtFac'+$auth_level+'" href="#">编辑</a>';
		str += '&nbsp;&nbsp;';
		str += '<a id="addFac'+$auth_level+'" href="#" >新增</a>';
		str += '</td>';
		str += '</tr>';
		str += '<tr>';
		str += '<td align="right" class="l-table-edit-td">';
		str += '<span style="color: red">*</span>授权效期：';
		str += '</td>';
		str += '<td align="left" class="l-table-edit-td">';
		str += '<table>';
		str += '<tr>';
		str += '<td>';
		str += '<input type="text" id="start_date'+$auth_level+'" class="Wdate" validate="{required:true}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'+"'yyyy-MM-dd'"+'})" />';
		str += '</td>';
		str += '<td>&nbsp;至&nbsp;</td>';
		str += '<td>';
		str += '<input type="text" id="end_date'+$auth_level+'" class="Wdate" validate="{required:true}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'+"'yyyy-MM-dd'"+'})" />';
		str += '</td>';
		str += '<td class="l-table-edit-td">';
		str += '<input type="checkbox" id="is_long'+$auth_level+'" />长期';
		str += '</td>';
		str += '</tr>';
		str += '</table>';
		str += '</td>';
		str += '<td></td>';
		str += '</tr>';
		str += '</table>';
		str += '</div>';
		
		$('#detailDiv').append(str);
	}
	
	//渲染授权级次
	function loadDetailTab(){
		$("#fac_id_from" + $auth_level).ligerComboBox({
			width: 250, 
			disabled: true, 
			cancelable: false, 
		});
		if(facFromData.id){
			liger.get("fac_id_from" + $auth_level).setValue(facFromData.id);
			liger.get("fac_id_from" + $auth_level).setText(facFromData.text);
		}
		
		$("#fac_id_to" + $auth_level).ligerComboBox({
			width: 250, 
			url: "queryHosFacSup.do?isCheck=false", 
			valueField: 'id',
			textField: 'text',
			autocomplete: true,
			highLight: true,
			defaultSelect: false,
			keySupport: true
		});
		
		$("#start_date" + $auth_level).ligerTextBox({width: 90});
		$("#end_date" + $auth_level).ligerTextBox({width: 90});
		$("#is_long" + $auth_level).ligerCheckBox({
			onAfterClick: function(checked){
				if(checked){
					$("#end_date" + $auth_level).val("");
					liger.get("end_date" + $auth_level).setDisabled();
				}else{
					liger.get("end_date" + $auth_level).setEnabled();
				}
			}
		});
		certFiles[$auth_level] = $("#cert_files" + $auth_level).etUpload({multiple: true});
		
		$("#addItem" + $auth_level).ligerButton({
			width: 60, 
			click: function(){
				if(!liger.get("fac_id_to" + $auth_level).getValue()) {
					$.ligerDialog.warn($auth_level+"级授权：目标企业不能为空！");
					return false;
				}
				if(!$("#start_date" + $auth_level).val()) {
					$.ligerDialog.warn($auth_level+"级授权：开始日期不能为空！");
					return false;
				}
				if(!$("#end_date" + $auth_level).val() && !$("#is_long" + $auth_level).prop("checked")) {
					$.ligerDialog.warn($auth_level+"级授权：结束日期不能为空！");
					return false;
				}
				if($("#end_date" + $auth_level).val() && new Date($("#end_date" + $auth_level).val()) < new Date($("#start_date" + $auth_level).val())){
					$.ligerDialog.warn($auth_level+"级授权：结束日期不能大于开始日期！");
					return false;
				}
				
				facFromData = {
					id: liger.get("fac_id_to" + $auth_level).getValue(),
					text: liger.get("fac_id_to" + $auth_level).getText()
				};
				
				$("#addItem" + $auth_level).hide();
				$("#removeItem" + $auth_level).hide();
				liger.get("fac_id_to" + $auth_level).setDisabled();
				liger.get("start_date" + $auth_level).setDisabled();
				liger.get("end_date" + $auth_level).setDisabled();
				//liger.get("is_long" + $auth_level).setDisabled();
				liger.get("is_long" + $auth_level).setReadonly(true);
				certFiles[$auth_level].disabled();
				
				$auth_level += 1;
				addDetailTab();
				loadDetailTab();
			}
		});
		$("#removeItem" + $auth_level).ligerButton({
			width: 60, 
			click: function(){
				$("#detailTab" + $auth_level).remove();
				
				$auth_level = $auth_level - 1;
				$("#addItem" + $auth_level).show();
				if($auth_level > 1){
					$("#removeItem" + $auth_level).show();
				}
				liger.get("fac_id_to" + $auth_level).setEnabled();
				liger.get("start_date" + $auth_level).setEnabled();
				liger.get("end_date" + $auth_level).setEnabled();
				//liger.get("is_long" + $auth_level).setEnabled();
				liger.get("is_long" + $auth_level).setReadonly(false);
				certFiles[$auth_level].enabled();
			}
		});
		if($auth_level == 1){
			$("#removeItem" + $auth_level).hide();
		}
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
				<span style="color: red">*</span>授权编号：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="auth_id" value="自动生成" disabled="disabled"/>
			</td>
			
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>证件类型：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_type_code" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>生产厂商：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="fac_id" validate="{required:true}" />
				<input type="hidden" id="sup_id" />
				<input type="hidden" id="sup_text" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>产品名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="prod_name" validate="{required:true}" />
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
				授权效期：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="auth_date" disabled="disabled"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				新授权：
			</td> 
			<td align="left" class="l-table-edit-td" colspan="3">
				<table>
					<tr>
						<td>
							<input type="hidden" id="new_auth_id" />
							<input type="text" id="new_auth_info"/>
						</td>
						<td align="left" class="l-table-edit-td" id="newAuthorBtn" style="display: none;">
							<a href="#" onClick="chooseNewAuthor()">选择新授权</a>
							&nbsp;&nbsp;
							<a href="#" onClick="openNewAuthor()">查看</a>
							&nbsp;&nbsp;
							<a href="#" onClick="removeNewAuthor()">取消</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				老授权：
			</td> 
			<td align="left" class="l-table-edit-td" colspan="3">
				<table>
					<tr>
						<td>
							<input type="hidden" id="old_auth_id" />
							<input type="text" id="old_auth_info" />
						</td>
						<td align="left" class="l-table-edit-td" id="oldAuthorBtn" style="display: none;">
							<a href="#" onClick="openOldAuthor()">查看</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div id="detailDiv">
	</div>
</body>
</html>