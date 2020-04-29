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
		$("#attend_time").ligerTextBox({
			width : 180
		});
	});
	
	
	function save() {
		if(isnull($("#dept_select").val())){
			parent.$.ligerDialog.warn('招聘科室不能为空');
			return;
		}
		if(isnull($("#station_select").val())){
			parent.$.ligerDialog.warn('招聘岗位不能为空');
			return;
		}

		dept_id = liger.get("dept_select").getValue().split("@")[0];
		dept_no = liger.get("dept_select").getValue().split("@")[1];
		var formPara = {

			dept_id :  dept_id,
			dept_no :  dept_no,
			station_code :  liger.get("station_select").getValue(),
			station_name :  $("#station_select").val(),
			demand_num : $("#demand_num").val(),
			form_code : $("#form_code").val(),
			demand_major :  liger.get("demand_major").getValue(),
			
			demand_age :  $("#demand_age").val(),
			demand_edu :  liger.get("demand_edu").getValue(),
			demand_deg :  liger.get("demand_deg").getValue(),
			
			demand_cet :liger.get("demand_cet").getValue(),
			demand_sex : liger.get("demand_sex").getValue(),
			demand_qualify : $("#demand_qualify").val(),
			demand_require : $("#demand_require").val(),
			demand_state : '01',

		};
		
		console.log(formPara),
		
		$.post("addRecruitDemand01.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND", formPara, function(responseData) {
			
			if (responseData.state == "true") {
				parent.$.ligerDialog.success("添加成功");
				$("input[name='dept_select']").val('');
				$("input[name='station_select']").val('');
				$("input[name='demand_num']").val('');
				$("input[name='form_code']").val('');
				$("input[name='demand_major']").val('');
				$("input[name='demand_age']").val('');
				$("input[name='demand_edu']").val('');
				$("input[name='demand_deg']").val('');
				$("input[name='demand_cet']").val('');
				$("input[name='demand_sex']").val('');
				$("input[name='demand_qualify']").val('');
				$("input[name='demand_require']").val('');
				
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
	      	width: 180,
// 	      	onSuccess: function (data) {
// 				this.setValue("${demand_sex}");
//       		}

		 });
		 
		 $("#station_select").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_STATION_MANAGE',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 180,
		      	onBeforeSelect: function (newvalue)
		      	 {
		      		if(liger.get("dept_select").getValue() =='' ||  liger.get("dept_select").getValue() == null){
		      			return $.ligerDialog.warn('请先选择招聘科室！');
		      		}
		      	 },
		      	onSelected: function (newvalue)
		      	{
		      		var formPara = {
						dept_id : liger.get("dept_select").getValue().split("@")[0],
						station_code : newvalue
					};
		      		ajaxJsonObjectByUrl("queryStationDescription.do?isCheck=false&tab_code=HR_STATION_DESCRIPTION",formPara,function (responseData){
                		if(responseData.Total  > 0){
                			liger.get("demand_major").setValue(responseData.Rows[0].discipline);
                			liger.get("demand_edu").setValue(responseData.Rows[0].education);
                			liger.get("demand_deg").setValue(responseData.Rows[0].academic);
                			 $("#form_code").val(responseData.Rows[0].kind_code);
                			 $("#station_num").val(responseData.Rows[0].station_num);
                			 $("#demand_age").val(responseData.Rows[0].age);
                			 $("#demand_qualify").val(responseData.Rows[0].technique);
                			 $("#demand_require").val(responseData.Rows[0].interpersonal);
                			 $("#act_num").val(responseData.Rows[0].act_num);
                			 
                		}           
		      		});
		      	}

			 });
		 
		 autocomplete("#demand_major","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_PROFESSION", "id", "text",
	    			true, true, "", false, null, "180");
 	
		 autocomplete("#demand_edu","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_EDUCATION", "id", "text",
	    			true, true, "", false, null, "180");
		 
    
		 autocomplete("#demand_deg","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_DEGREE", "id", "text",
	    			true, true, "", false, null, "180");
		 
		 autocomplete("#demand_cet","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_ENGLISH_LEVEL", "id", "text",
	    			true, true, "", false, null, "180");
		
		 autocomplete("#demand_sex","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_SEX", "id", "text",
	    			true, true, "", false, null, "180");
     
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
					name="dept_select" type="text" id="dept_select" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘岗位<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="station_select" type="text" id="station_select" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">定岗人数：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="station_num" type="text" id="station_num" ltype="text" 
					disabled="true"/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">实际人数：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="act_num" type="text" id="act_num" ltype="text" 
					validate="{required:true,maxlength:100}" disabled/></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">岗位编制人数：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="complie_count" type="text" id="complie_count" ltype="text" 
				 disabled/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:1px;" width="60">预计到岗日期：</td>
            	<td align="left" class="l-table-edit-td" ><input name="attend_time" disabled="disabled" class="Wdate" value="${attend_time}"  type="text" id="attend_time" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘人数：
				</td>
				<td align="left" class="l-table-edit-td"><input name="demand_num"
					type="text" id="demand_num" ltype="text"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">用工形式：
				</td>
				 <td align="left" class="l-table-edit-td">
                	<select id="form_code" name="form_code" >
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
					name="demand_major" type="text" id="demand_major" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">年龄范围：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_age" type="text" id="demand_age" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">学历：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_edu" type="text" id="demand_edu" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">学位：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_deg" type="text" id="demand_deg" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">英语级别：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_cet" type="text" id="demand_cet" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">性别：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="demand_sex" type="text" id="demand_sex" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
		   <tr>	
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">任职资格：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="demand_qualify" name="demand_qualify" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>       
                </td>
                <td align="left"></td>
			</tr>
			 <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">任职要求：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="demand_require" name="demand_require" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>
                 
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
					value="保存" onclick="save();" /></td>
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="关闭" onclick="this_close();" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
