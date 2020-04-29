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
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script type="text/javascript">
	var dataFormat;
	$(function() {
		loadDict()//加载下拉框
		loadForm();
	});

	function save() {
		var formPara = {
			dept_id : liger.get("dept_id").getValue().split(",")[0],//编制科室
			dept_no : liger.get("dept_id").getValue().split(",")[1],
			make_date : $("#make_date").val(),//编制日期
			arrive_date : $("#arrive_date").val(),//计划到货日期
			store_id : liger.get("store_id").getValue().split(",")[0],
			store_no : liger.get("store_id").getValue().split(",")[1],
			mat_type_code : liger.get("mat_type_id").getText().split(" ")[0],
			brif : $("#brif").val()//摘要
		}; 
		//console.log(formPara)
		ajaxJsonObjectByUrl("matPurPlanGenBySecuLimit.do?isCheck=false", formPara,
			function(responseData) {
				if (responseData.state == "true") {
					parent.query();
				}
		});
	}

	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}

	function saveMatLocationType() {
		if ($("form").valid()) {
			save();
		}
	}
	
	function loadDict() {
		//字典下拉框
		autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true,"",true);//编制科室下拉框			
		//autocomplete("#pur_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false);//采购单位下拉框 
		//autocomplete("#req_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false);//请购单位下拉框 
		//autocomplete("#pay_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false);//付款单位下拉框 
		
		//autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
		//autocomplete("#dir_dept_id","../../queryMatDeptDictDate.do?isCheck=false","id","text",true,true,{is_last:'1',is_write:'1'},false,false,'160');//定向科室	
		
		autocomplete("#store_id","../../queryMatStore.do?isCheck=false","id","text",true,true,"",true);//仓库
        autocomplete("#mat_type_id","../../queryMatTypeDict.do?isCheck=false","id","text",true,true,"",false);//物资分类
        
		autodate("#make_date");
	
		$("#make_date").ligerTextBox({width:160});
		
		$("#arrive_date").ligerTextBox({width:160});
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr></tr>
			<tr>

				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><span style="color: red">*</span>编制科室：</td>
				<td align="left" class="l-table-edit-td"><input
					name="dept_id" type="text" id="dept_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><span style="color: red">*</span>编制日期：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate" name="make_date" type="text" id="make_date" ltype="text"  
           			onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/></td>
				<td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>仓库：</td>
		        <td align="left" class="l-table-edit-td" >
		            <input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}"  />
		        </td>
		        <td align="left"></td>
		    </tr>
		    
		    <tr>    
	           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资分类:</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="mat_type_id" type="text" id="mat_type_id" ltype="text" validate="{maxlength:20}" />
	            </td>
	            <td align="left"></td>
	        </tr>
	        
	        <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划到货日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="arrive_date" type="text" id="arrive_date" ltype="text"  
	           		 onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{maxlength:20}"/>
	             </td>
	            <td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
	            <td align="left" class="l-table-edit-td" colspan="2">
	            	<input name="brif" type="text" id="brif" ltype="text" />
	            </td>
			</tr>
		</table>
	</form>

</body>
</html>
