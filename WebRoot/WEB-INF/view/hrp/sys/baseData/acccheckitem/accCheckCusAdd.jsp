<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        $("#cus_code").ligerTextBox({ disabled: true });
        $("#cus_name").ligerTextBox({ disabled: true });
        $("#is_stop").ligerTextBox({ disabled: true });
        
        liger.get("type_code").setValue('${type_code}');
		 liger.get("type_code").setText('${type_name}');
        
     });  
     
     function  save(){


    	 //手机
      	  var reg=/^[1][3578][0-9]{9}$/;
            if($("#phone").val()!=null && ($("#phone").val()!="")){
      	 
          	if(reg.test($("#phone").val())==false){
      		  $.ligerDialog.error("手机输入不合法!");
   				
      		  return;
      	  }
       }
            
            //电话
            var re1 = /^[1][3578][0-9]{9}$/; 
            if($("#mobile").val()!=null && ($("#mobile").val()!="")){
              	 
            	if(re1.test($("#mobile").val())==false){
        		  $.ligerDialog.error("电话输入不合法!");
     				
        		  return;
        	  }
         }
          //EMAIL
          var re = /^\d{5,12}@[qQ][qQ]\.com$/;
          if($("#email").val()!=null && ($("#email").val()!="")){
         	 
          	if(re.test($("#email").val())==false){
      		  $.ligerDialog.error("邮箱输入不合法!");
   				
      		  return;
      	  }
       }
    	 
		var formPara = {
			cus_id : $("#cus_id").val(),

			bank_name : $("#bank_name").val(),

			bank_number : $("#bank_number").val(),

			legal : $("#legal").val(),

			regis_no : $("#regis_no").val(),

			phone : $("#phone").val(),

			mobile : $("#mobile").val(),

			contact : $("#contact").val(),

			fax : $("#fax").val(),

			email : $("#email").val(),

			region : $("#region").val(),

			zip_code : $("#zip_code").val(),

			address : $("#address").val(),

			note : $("#note").val()

		};

	
		ajaxJsonObjectByUrl("addAccCusAttr.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				parentFrameUse().query();
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

	function saveAccCusAttr() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		autocomplete("#type_code","../../sys/queryCusTypeDict.do?isCheck=false", "id", "text",true, true);
		
		$("#type_code").ligerTextBox({disabled:true});
	}
</script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
                <td align="left" class="l-table-edit-td"><input name="cus_id" type="hidden"  id="cus_id" ltype="text"  value ="${cus_id }" /></td>
            </tr> 
        <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">客户编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cus_code" type="text" id="cus_code" ltype="text"  value="${cus_code }" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">客户分类：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" disabled="disabled" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">客户名称：</td>
                <td align="left" class="l-table-edit-td"><input name="cus_name" type="text"  id="cus_name" ltype="text"  value="${cus_name }"  /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                 <select id="is_stop" name="is_stop" style="width: 135px;" disabled="disabled">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开户银行：</td>
                <td align="left" class="l-table-edit-td">
              		   <input name="bank_name" type="text"  id="bank_name" ltype="text"  vavalidate="{required:false,maxlength:20}" />
                </td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行账户：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_number" type="text"  id="bank_number" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">法人：</td>
                <td align="left" class="l-table-edit-td"><input name="legal" type="text"  id="legal" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">纳税人登记号：</td>
                <td align="left" class="l-table-edit-td"><input name="regis_no" type="text"  id="regis_no" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手机：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text" id="phone" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">电话：</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text" id="mobile" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系人：</td>
                <td align="left" class="l-table-edit-td"><input name="contact" type="text" id="contact" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">传真：</td>
                <td align="left" class="l-table-edit-td"><input name="fax" type="text" id="fax" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地区：</td>
                <td align="left" class="l-table-edit-td"><input name="region" type="text" id="region" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">EMAIL：</td>
                <td align="left" class="l-table-edit-td"><input name="email" type="text" id="email" ltype="text"    validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">邮政编码：</td>
                <td align="left" class="l-table-edit-td"><input name="zip_code" type="text" id="zip_code" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地址：</td>
                <td align="left" class="l-table-edit-td"><input name="address" type="text" id="address" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><textarea rows="3" cols="60" name="note" id="note"   validate="{required:false,maxlength:50}" style="resize: none;" ></textarea></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
