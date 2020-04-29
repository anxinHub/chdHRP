<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />   
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	$(function() {
		loadDict()//加载下拉框
		loadForm(); 
		
		$("#dept_select").ligerTextBox({width:180,disabled:true});
		$("#station_select").ligerTextBox({width:180,disabled:true});
	});
	
	
	function save() {
		var formPara = {
			
			demand_id : ${demand_id},
			demand_cet : liger.get("demand_cet").getValue(),
			demand_sex : liger.get("demand_sex").getValue(),
			demand_note : '${demand_note}',
			
			dept_id :   liger.get("dept_select").getValue().split("@")[0],
			dept_no :  liger.get("dept_select").getValue().split("@")[1],
			
			station_code :  liger.get("station_select").getValue(),
			station_name :  $("#station_select").val(),
			demand_num : liger.get("demand_num").getValue(),
			form_code :  $("#form_code").val(),
			demand_major :  liger.get("demand_major").getValue(),
			demand_age :  $("#demand_age").val(),
			demand_edu :  liger.get("demand_edu").getValue(),
			demand_deg :  liger.get("demand_deg").getValue(),
			demand_qualify : $("#demand_qualify").val(),
			demand_require : $("#demand_require").val(),
			
		};
		
		
		$.post("updateRecruitDemand01.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND", formPara, function(responseData) {
			
			if (responseData.state == "true") {
				parent.$.ligerDialog.success("修改成功");
				$("input[name='dept_select']").val('');
				$("input[name='station_select']").val('');
				$("input[name='demand_num']").val('');
				$("input[name='form_code']").val('');
				$("input[name='demand_major").val('');
				$("input[name='demand_age']").val('');
				$("input[name='demand_qualify']").val('');
				$("input[name='demand_require']").val('');
				$("input[name='demand_edu']").val('');
				$("input[name='demand_deg']").val('');
				
				parent.query();
				frameElement.dialog.close();
				
			}else{
				parent.$.ligerDialog.warn(responseData.msg);
			}
		},"json");
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

	function saveDemand() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
	
		$("#dept_select").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSuccess: function (data) {
						this.setValue("${dept_no}"+'@'+"${dept_id}");
		      	}
			 });
		$("#station_select").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_STATION_MANAGE',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSuccess: function (data) {
						this.setValue("${station_code}");
		      	}
			 });
		 $("#demand_major").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_PROFESSION',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSuccess: function (data) {
						this.setValue("${demand_major}");
		      	}
			 });
		 
		 $("#demand_edu").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_EDUCATION',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSuccess: function (data) {
						this.setValue("${demand_edu}");
		      	}
			 });
		 $("#demand_deg").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_DEGREE',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSuccess: function (data) {
						this.setValue("${demand_deg}");
		      	}
			 });
		 
		 $("#demand_cet").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_ENGLISH_LEVEL',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSuccess: function (data) {
						this.setValue("${demand_cet}");
		      	}
			 });
		 $("#demand_sex").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_SEX',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSuccess: function (data) {
						this.setValue("${demand_sex}");
		      	}
			 });
		
		$("#attend_time").ligerTextBox({width:175});
		
	}
	function this_close() {
		frameElement.dialog.close();
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1" style="display:blok">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘科室<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="dept_select" type="text" id="dept_select"  ltype="text"  value="${dept_id}"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘岗位<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="station_select" type="text" id="station_select"  ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
		   </tr>
<!-- 		   <tr> -->
<!-- 				<td align="right" class="l-table-edit-td" -->
<!-- 					style="padding-left: 20px;">岗位编制人数： -->
<!-- 				</td> -->
<!-- 				<td align="left" class="l-table-edit-td"><input -->
<%-- 					name="complie_count" type="text" id="complie_count" value="${complie_count}" ltype="text"  --%>
<!-- 					 disabled="disabled" /></td> -->
<!-- 				<td align="left"></td> -->
<!-- 				<td align="right" class="l-table-edit-td"  style="padding-left:1px;" width="60">期望到岗日期：</td> -->
<%--             	<td align="left" class="l-table-edit-td" ><input name="attend_time" disabled="disabled" class="Wdate" value="${attend_time}"  type="text" id="attend_time" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td> --%>
<!-- 				<td align="left"></td> -->
<!-- 		   </tr> -->
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘人数：
				</td>
				<td align="left" class="l-table-edit-td"><input name="demand_num" value="${demand_num}"
					type="text" id="demand_num" ltype="text"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
					<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">用工形式：
				</td>
				 <td align="left" class="l-table-edit-td">
                	<select id="form_code" name="form_code" value="${form_code}">
                		<option value="001">正式工</option>
                		<option value="002">合同工</option>
                		<option value="003">临时工</option>
                		<option value="004">聘用</option>
                		<option value="005">劳务派遣</option>
                		<option value="006">借调</option>
                		<option value="007">调出</option>
                	</select>
                </td>
				<td align="left"></td>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">专业要求：</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_major" type="text" id="demand_major"  ltype="text" /></td>
				<td align="left"></td>
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">年龄范围：
				</td>
				<td align="left" class="l-table-edit-td"><input value="${demand_age}"
					name="demand_age" type="text" id="demand_age" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">学历：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="demand_edu" type="text" id="demand_edu" ltype="text" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">学位：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="demand_deg" type="text" id="demand_deg" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">英语级别：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_cet" type="text" id="demand_cet" ltype="text" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">性别：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_sex" type="text" id="demand_sex" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
		   <tr>	
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">任职资格：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="demand_qualify" name="demand_qualify" value="${demand_qualify}"  placeholder="请输入不超过100个字" maxlength="100"></textarea>       
                </td>
                <td align="left"></td>
			</tr>
			 <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">任职要求：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="demand_require" name="demand_require" value="${demand_require}" placeholder="请输入不超过100个字" maxlength="100"></textarea>
                 
                </td>
                <td align="left"></td>
              </tr>  
		</table>
		<table align="center" cellpadding="0" cellspacing="0"
			class="l-table-edit">
			<tr align="center">
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="保存" onclick="saveDemand();" /></td>
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="关闭" onclick="this_close();" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
