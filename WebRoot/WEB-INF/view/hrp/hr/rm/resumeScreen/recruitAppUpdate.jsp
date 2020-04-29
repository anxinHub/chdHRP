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
	});
	
	function isIdCard (idcard) {
	    // 判断如果传入的不是一个字符串，则转换成字符串
	    idcard = typeof idcard === 'string' ? idcard : String(idcard);
	    //正则表达式验证号码的结构
	    let regx = /^[\d]{17}[0-9|X|x]{1}$/;
	    if (regx.test(idcard)) {
	      // 验证前面17位数字，首先定义前面17位系数
	      let sevenTeenIndex = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
	      // 截取参数前17位
	      let front_seventeen = idcard.slice(0, 17);
	      // 截取第18位
	      let eighteen = idcard.slice(17, 18);
	      // 这里如果是X要转换成小写，如果是数字在这里是字符串类型,则转换成数字类型，好做判断
	      eighteen = isNaN(parseInt(eighteen)) ? eighteen.toLowerCase() : parseInt(eighteen);
	      // 定义一个变量计算系数乘积之和余数
	      let remainder = 0;
	      //利用循环计算前17位数与系数乘积并添加到一个数组中
	      // charAt()类似数组的访问下标一样，访问单个字符串的元素,返回的是一个字符串因此要转换成数字
	      for (let i = 0; i < 17; i++) {
	        remainder = (remainder += parseInt(front_seventeen.charAt(i)) * sevenTeenIndex[i]) % 11;
	      }
	      //余数对应数字数组
	      let remainderKeyArr = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
	      // 取得余数对应的值
	      let remainderKey = remainderKeyArr[remainder] === 'X' ? remainderKeyArr[remainder].toLowerCase() : remainderKeyArr[remainder];
	      console.log(remainderKey);
	      console.log(eighteen)
	      // 如果最后一位数字对应上了余数所对应的值，则验证合格，否则不合格,
	      // 由于不确定最后一个数字是否是大小写的X，所以还是都转换成小写进行判断
	      if (eighteen === remainderKey) {
	        return true;
	      } else {
	        return false;
	      }
	    } else {
	    	return false;
	    }
	  }
	
	function save() {
		
		if(isnull($("#app_name").val())){
			parent.$.ligerDialog.warn('应聘人员名称不能为空');
			return;
		}
		
		if(!isnull($("#app_cardid").val())){
			if(!isIdCard($("#app_cardid").val())){					
				parent.$.ligerDialog.warn('身份证号格式不正确');		
				return;
			}
		}
		
		if(!isnull($("#app_phone").val())){
			var reg =/^((13|15|18|14|17)+\d{9})$/;
			if(!reg.test($("#app_phone").val())){					
				parent.$.ligerDialog.warn('联系电话格式不正确');		
				return;
			}
		}

			
		if(!isnull($("#app_email").val())){
			var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;		
			if(!reg.test($("#app_email").val())){					
				parent.$.ligerDialog.warn('邮箱格式不正确');		
				return;
			}
		}
		
		
		
// 		var data =  frameElement.dialog.get("data");
// 		console.log(data[0].app_id);
		var formPara = {
			app_id :  ${app_id},
			app_name :   $("#app_name").val(),
			app_sex : liger.get("app_sex").getValue(),
			app_birth :  $("#app_birth").val(),
			app_ethnic :liger.get("app_ethnic").getValue(),
			app_edu :  liger.get("app_edu").getValue(),
			app_dege :  liger.get("app_dege").getValue(),
			app_cardid : $("#app_cardid").val(),
			app_state :  $("#app_state").val(),
			app_major :  liger.get("app_major").getValue(),
			app_cet :liger.get("app_cet").getValue(),
			app_email : $("#app_email").val(),
			app_phone : $("#app_phone").val()
		};
		

				
			$.post("updateRecruitApp.do?isCheck=false&tab_code=HR_RECRUIT_APP", formPara, function(responseData1) {
				
				if (responseData1.state == "true") {
					
					parent.$.ligerDialog.success("修改成功！");
					$("input[name='app_name']").val('');
					$("input[name='app_sex']").val('');
					$("input[name='app_birth']").val('');
					$("input[name='app_ethnic']").val('');
					$("input[name='app_edu']").val('');
					$("input[name='app_dege']").val('');
					$("input[name='app_cardid']").val('');
					$("input[name='app_state']").val('');
					$("input[name='app_major']").val('');
					$("input[name='app_cet']").val('');
					$("input[name='app_email']").val('');
					$("input[name='app_phone']").val('');
					
					parent.queryCard(${demand_id}); 
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn("简历修改失败！");
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
		//专业要求
// 		autocomplete("#app_major",
// 				"../../baseSelect.do?isCheck=false&&field_tab_code=DIC_PROFESSION", "id", "text",
// 				true, true, "", false, null, "180");
		$("#app_major").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_PROFESSION',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 180,
	      	onSuccess: function (data) {
					this.setValue("${app_major}");
	      	}
		 });
// 		autocomplete("#app_edu",
// 				"../../baseSelect.do?isCheck=false&&field_tab_code=DIC_EDUCATION", "id", "text",
// 				true, true, "", false, null, "180");
		$("#app_edu").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_EDUCATION',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 180,
	      	autocomplete: true,
	      	width: 180,
	      	onSuccess: function (data) {
					this.setValue("${app_edu}");
	      	}
		 });
		
// 		autocomplete("#app_dege",
// 				"../../baseSelect.do?isCheck=false&&field_tab_code=DIC_DEGREE", "id", "text",
// 				true, true, "", false, null, "180");
		$("#app_dege").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_DEGREE',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 180,
	      	autocomplete: true,
	      	width: 180,
	      	onSuccess: function (data) {
					this.setValue("${app_dege}");
	      	}
		 });
		
// 		autocomplete("#app_cet",
// 				"../../baseSelect.do?isCheck=false&&field_tab_code=DIC_ENGLISH_LEVEL", "id", "text",
// 				true, true, "", false, null, "180");
		
		$("#app_cet").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_ENGLISH_LEVEL',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 180,
	      	onSuccess: function (data) {
					this.setValue("${app_cet}");
	      	}
		 });
		
// 		autocomplete("#app_ethnic",
// 				"../../baseSelect.do?isCheck=false&&field_tab_code=DIC_NATION", "id", "text",
// 				true, true, "", false, null, "180");
		
		$("#app_ethnic").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_NATION',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 180,
	      	onSuccess: function (data) {
					this.setValue("${app_ethnic}");
	      	}
		 });
		
		
// 		autocomplete("#app_sex",
// 				"../../baseSelect.do?isCheck=false&&field_tab_code=DIC_SEX", "id", "text",
// 				true, true, "", false, null, "180");
		$("#app_sex").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_SEX',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160,
	      	onSuccess: function (data) {
					this.setValue("${app_sex}");
	      	}
		 });
		
		$("#app_birth").ligerTextBox({width:175});
		
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
					style="padding-left: 20px;">姓名<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_name" type="text" id="app_name" ltype="text"  value="${app_name}"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">性别：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_sex" type="text" id="app_sex" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">出生日期：
				</td>
				<td align="left" class="l-table-edit-td" ><input name="app_birth" class="Wdate"  type="text" id="app_birth" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  value="${app_birth}"/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">民族：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="app_ethnic" type="text" id="app_ethnic" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">学历：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_edu" type="text" id="app_edu" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">学位：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_dege" type="text" id="app_dege" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">身份证：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="app_cardid" type="text" id="app_cardid" ltype="text" 
					validate="{required:true,maxlength:100}" value="${app_cardid}"/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">状态：
            	 <td align="left" class="l-table-edit-td">
                	<select id="app_state" name="app_state" >
                		<option value="01">在职</option>
                		<option value="02">离职</option>
                	</select>
                </td>
				<td align="left"></td>
		   </tr>
		  
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">专业：</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_major" type="text" id="app_major" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">英语级别：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_cet" type="text" id="app_cet" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">电子邮件：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_email" type="text" id="app_email" ltype="text" 
					validate="{required:true,maxlength:100}" value="${app_email}"/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">联系电话：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="app_phone" type="text" id="app_phone" ltype="text" 
					validate="{required:true,maxlength:100}" value="${app_phone}"/></td>
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
