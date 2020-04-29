<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>  
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<script type="text/javascript">
	var grid, certFiles; 
	var gridManager = null;
	var sup_id = "${sup_id}";
	var old_cert_files = "";
	
	$(function() {
		
		loadDict();//加载下拉框
		loadForm();
		
		ajaxJsonObjectByUrl("queryCertType.do?isCheck=false", {
			cert_type_code : "${supCert.cert_type_code}"
		}, function(responseData) {
			if(responseData.is_cert_busi == 1){
				$("#busiTr").show();
			}else{
				$("#busiTr").hide();
			}
			
			if(responseData.is_cert_name == 1){
				$("#nameTr").show();
			}else{
				$("#nameTr").hide();
			}
		});
	});
	
	function loadForm(){
	    
		$.metadata.setType("attr", "validate");
	    var v = $("form").validate({
	    	errorPlacement: function (lable, element)
	       	{
	        	if (element.hasClass("l-textarea"))
	       		{
	          		element.ligerTip({ content: lable.html(), target: element[0] }); 
	          	}
	          	else if (element.hasClass("l-text-field"))
	       		{
	        		element.parent().ligerTip({ content: lable.html(), target: element[0] });
	      		}
	       		else
	          	{
	       			lable.appendTo(element.parents("td:first").next("td"));
	         	}
	    	},
	     	success: function (lable)
	      	{
	     		lable.ligerHideTip();
	      		lable.remove();
	     	},
	      	submitHandler: function ()
	      	{
	     		$("form .l-text,.l-textarea").ligerHideTip();
	      	}
		});
		$("form").ligerForm();
	}       

	function loadDict() {
		
		autocomplete("#cert_type_code","../../queryMatCertType.do?isCheck=false","id","text",true,true, {cert_kind_code: 3},false, false, 260);
		liger.get("cert_type_code").setValue("${supCert.cert_type_code}");
		liger.get("cert_type_code").setText("${supCert.cert_type_code}" + " " + "${supCert.cert_type_name}");
		
		$("#cert_code").ligerTextBox({ width : 260 });
		$("#cert_name").ligerTextBox({ width : 260 });
		$("#cert_busi").ligerTextBox({ width : 260 });
		
		$("#start_date").ligerTextBox({ width : 90 });
		$("#end_date").ligerTextBox({ width : 90 });

		autodate("#start_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		if("${supCert.is_long == 1}"){
			$("#start_date").val("${supCert.start_date}");
			$("#is_long").prop("checked", true);
			$("#end_date").val("");
			$("#end_date").ligerTextBox({ width : 90, disabled: true });
		}else{
			$("#start_date").val("${supCert.start_date}");
			$("#end_date").val("${supCert.end_date}");
		}

		certFiles = $("#files").etUpload({multiple: true});
		if("${supCert.file_path}".length > 0){
			old_cert_files = "${supCert.file_path}".split(",");
			certFiles.setValues(old_cert_files);
		}
		
		$("#save").ligerButton({ click: save, width: 50, disabled: ${supCert.check_state == 1} ? false : true});
		$("#printImg").ligerButton({ click: hrpPrintImgs, width: 70 });
		$("#close").ligerButton({ click: thisClose, width: 50 });
		
		$("#is_long").change(function() { 
			if($("#is_long").prop('checked')){
				$("#end_date").val("");
				$("#end_date").ligerTextBox({ width : 90, disabled: true });
			}else{
				$("#end_date").ligerTextBox({ width : 90, disabled: false });
				autodate("#end_date", "yyyy-mm-dd", "month_last");
			}
		});
		
		$("#cert_type_code").change(function(){
			ajaxJsonObjectByUrl("queryCertType.do?isCheck=false", {
				cert_type_code : liger.get("cert_type_code").getValue()
			}, function(responseData) {
				if(responseData.is_cert_busi == 1){
					$("#busiTr").show();
				}else{
					$("#busiTr").hide();
				}
				
				if(responseData.is_cert_name == 1){
					$("#nameTr").show();
				}else{
					$("#nameTr").hide();
				}
			});
		});
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
		
		if(!$("#start_date").val()) {
			$.ligerDialog.warn("证件开始日期不能为空！");
			return false;
		}
		if(!$("#end_date").val() && !$("#is_long").prop("checked")) {
			$.ligerDialog.warn("证件结束日期不能为空！");
			return false;
		}

		var formData = new FormData();
		formData.append("sup_id", sup_id);
		formData.append("cert_id", "${supCert.cert_id}")
		formData.append("cert_type_code", liger.get("cert_type_code").getValue());
		formData.append("cert_code", $("#cert_code").val());
		formData.append("cert_name", $("#cert_name").val());
		formData.append("start_date", $("#start_date").val());
		formData.append("end_date", $("#end_date").val());
		formData.append("is_long", $("#is_long").prop("checked") ? 1 : 0);
		formData.append("cert_busi", $("#cert_busi").val());
		
		//注册证图片
		var certFileList = certFiles.getValues();
		var new_cert_files = "";
		for (i = 0; i < certFileList.length; i++) {
			if (typeof certFileList[i] == "string") {
				new_cert_files += certFileList[i] + ",";
			}
			formData.append("cert_files", certFileList[i]);
		}
		formData.append("new_cert_files", new_cert_files.substring(0, new_cert_files.length - 1));
		formData.append("old_cert_files", old_cert_files);
		
		ajaxLigerPostFormData({
			url: "updateMatSupCert.do?isCheck=false", 
			para: formData, 
			success: function (res) {
				if (res.state == "true") {
					// 刷新父页面的表格
					parentFrameUse().query();
				}
			}
		});
	}
	
	function thisClose(){
 		frameElement.dialog.close();
	} 
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<form name="form" method="post" id="form" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
	        <tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>证件类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="cert_type_code" type="text" id="cert_type_code" type="text"  validate="{required:true}" />
	            </td>
	            <td align="left"></td>
	        </tr>    
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>证件编号：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="cert_code" type="text" id="cert_code" type="text" validate="{required:true,maxlength:50}" value="${supCert.cert_code}"/>
	            </td>
	            <td align="left"></td>
	        </tr> 
	        <tr id="nameTr">
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件名称：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="cert_name" type="text" id="cert_name" type="text" validate="{required:true,maxlength:200}" value="${supCert.cert_name}"/>
	            </td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>证件效期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<table>
						<tr>
							<td>
								<input class="Wdate" name="start_date" id="start_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<td align="right" class="l-table-edit-td"  >
								至
							</td>
							<td>
								<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<td>
								&nbsp;&nbsp;<input type="checkbox" id="is_long" />长期
							</td>
	            		</tr>
					</table>
	            </td>
	            <td align="left"></td>
	        </tr>   
	        <tr id="busiTr">
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">经营范围：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="cert_busi" type="text" id="cert_busi" type="text"  validate="{required:true,maxlength:500}" value="${supCert.cert_busi}"/>
	            </td>
	            <td align="left"></td>
	        </tr>
	      	<tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">附件：</td>
	            <td align="left" class="l-table-edit-td" colspan="3">
					<div id="files" style="border:0px"></div>
				</td>
	            <td align="left"></td>
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
	            <td align="left" class="l-table-edit-td">
					<font style="color:gray">请上传加盖公章的证件图片。</font>
	            </td>
	            <td align="left"></td>
	        </tr>
	        <tr style="height:20px"></tr>
	    </table>
    </form>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
		<tr>
			<td align="center" class="l-table-edit-td">
				<button id="save" ><b>保存</b></button> &nbsp;&nbsp;
				<button id="printImg" ><b>打印附件</b></button> &nbsp;&nbsp;
				<button id="close" ><b>关闭</b></button>
			</td>
		</tr>
	</table>
	
</body>
</html>