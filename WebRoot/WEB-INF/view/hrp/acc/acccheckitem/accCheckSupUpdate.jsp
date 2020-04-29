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
        
    	if("${is_mat}" == 1){
        	liger.get("is_mat").setValue(true)
		} 
        if("${is_med}" == 1){
        	liger.get("is_med").setValue(true)
		}
        if("${is_ass}" == 1){
        	liger.get("is_ass").setValue(true)
		} 
        if("${is_sup}" == 1){
        	liger.get("is_sup").setValue(true)
		}
        
        $("#sup_code").ligerTextBox({ disabled: true });
        
        $("#type_code").ligerComboBox({ });
        
		liger.get("type_code").setValue('${type_code}');
    	
		liger.get("type_code").setText('${type_name}');
        
     });  
     
     function  save(){
    	 
    	 mat=$("#is_mat").is(":checked") ? 1 : 0;
    	 
     	 med=$("#is_med").is(":checked") ? 1 : 0;
     	 
     	 ass=$("#is_ass").is(":checked") ? 1 : 0;
     	 
     	 sup=$("#is_sup").is(":checked") ? 1 : 0 ;
     	 
     	 if(mat=='0' && med=='0' && ass=='0' && sup=='0'){
     		 
     		 $.ligerDialog.error("系统模块不能为空");
     		 
     		 return ; 
     	 }
    	 
        var formPara={
        		
        		sup_id:$("#sup_id").val(),
        		
  	           sup_code:$("#sup_code").val(),
  	            
  	           type_code:liger.get("type_code").getValue(),
  	           
  	         	range_id:liger.get("range_id").getValue(),
  	            
  	           sup_name:$("#sup_name").val(),
  	            
  	           sort_code:$("#sort_code").val(),
  	              
  	           is_disable:liger.get("is_disable").getValue(),
  	            
 				is_mat:mat,
 				
 				is_med:med,
 				
 				is_ass:ass,
 				
 				is_sup:sup,
        		
 				is_auto:true,

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
        
        ajaxJsonObjectByUrl("updateAccSupAttr.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	parentFrameUse().query();
            }
        });
    }
     
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
   
    function saveAccSupAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_code","../../sys/querySupTypeDict.do?isCheck=false", "id", "text",true, true);
    
    	$("#note").val('${note}');
    	
    	$("#sort_code").ligerTextBox({disabled:true});
    	
    	$('#is_disable').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
		});
		liger.get("is_disable").setValue("${is_disable}");
		autocomplete("#range_id","../queryAccBudgRange.do?isCheck=false", "id", "text",true, true,'',false,'${range_id}',200,20,200,false);
    	
    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">

		<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b>供应商编码<font color="red">*</font>:
				</b></td>
				<td align="left" class="l-table-edit-td"><input name="sup_code"
					type="text" id="sup_code" ltype="text" validate="{maxlength:20}" value="${sup_code }" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b>供应商名称<font color="red">*</font>:
				</b></td>
				<td align="left" class="l-table-edit-td"><input name="sup_name"
					type="text" id="sup_name" ltype="text" validate="{required:true}" value="${sup_name }" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b>供应商类别:</b></td>
				<td align="left" class="l-table-edit-td"><input
					name="type_code" type="text" id="type_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b>排序号<font color="red">*</font>:
				</b></td>
				<td align="left" class="l-table-edit-td"><input
					name="sort_code" type="text" id="sort_code" value="系统生成"
					disabled="disabled" ltype="text"
					validate="{required:true,digits:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b>是否停用<font color="red">*</font>:
				</b></td>
				<td align="left" class="l-table-edit-td">
				<input name="is_disable" type="text" id="is_disable"  ltype="text" validate="{required:true}"/>
				</td>

					<td align="left" class="l-table-edit-td" colspan="3" >
             		<input name="is_mat"  id ="is_mat"  type="checkbox" />物流管理&nbsp;&nbsp;
             		<input name="is_med"  id ="is_med" type="checkbox" />药品管理&nbsp;&nbsp;
             		<input name="is_ass"  id ="is_ass" type="checkbox" />固定资产&nbsp;&nbsp;
             		<input name="is_sup"  id ="is_sup" type="checkbox" />供应商平台
             	</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b>备注:</b></td>
				<td align="left" class="l-table-edit-td" colspan="4"><textarea
						rows="3" cols="75" id="note" name="note" ltype="text"
						validate="{maxlength:20}"></textarea></td>
				<td align="left"></td>
			</tr>
		</table>
		<div style="width: 100%; float: left; margin-top: 10px">
			<hr />
		</div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td"><input name="sup_id"
					type="hidden" id="sup_id" ltype="text" value="${sup_id }" /></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">开户银行：</td>
				<td align="left" class="l-table-edit-td"><input
					name="bank_name" type="text" id="bank_name" ltype="text"
					value="${bank_name }" vavalidate="{required:false,maxlength:20}" />
				</td>
				<td align="left" width="150"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">银行账户：</td>
				<td align="left" class="l-table-edit-td"><input
					name="bank_number" type="text" id="bank_number"
					value="${bank_number}" ltype="text"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">法人：</td>
				<td align="left" class="l-table-edit-td"><input name="legal"
					type="text" id="legal" ltype="text" value="${bank_number}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left" width="150"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">纳税人登记号：</td>
				<td align="left" class="l-table-edit-td"><input name="regis_no"
					type="text" id="regis_no" ltype="text" value="${regis_no}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">手机：</td>
				<td align="left" class="l-table-edit-td"><input name="phone"
					type="text" id="phone" ltype="text" value="${phone}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left" width="150"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">电话：</td>
				<td align="left" class="l-table-edit-td"><input name="mobile"
					type="text" id="mobile" ltype="text" value="${mobile}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">联系人：</td>
				<td align="left" class="l-table-edit-td"><input name="contact"
					type="text" id="contact" ltype="text" value="${contact}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left" width="150"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">传真：</td>
				<td align="left" class="l-table-edit-td"><input name="fax"
					type="text" id="fax" ltype="text" value="${fax}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">地区：</td>
				<td align="left" class="l-table-edit-td"><input name="region"
					type="text" id="region" ltype="text" value="${region}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left" width="150"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">EMAIL：</td>
				<td align="left" class="l-table-edit-td"><input name="email"
					type="text" id="email" ltype="text" value="${email}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">邮政编码：</td>
				<td align="left" class="l-table-edit-td"><input name="zip_code"
					type="text" id="zip_code" ltype="text" value="${zip_code}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left" width="150"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">地址：</td>
				<td align="left" class="l-table-edit-td"><input name="address"
					type="text" id="address" ltype="text" value="${address}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">账期：</td>
				<td align="left" class="l-table-edit-td"><input name="range_id"
					type="text" id="range_id" ltype="text" value="${range_id}"
					validate="{required:false,maxlength:20}" /></td>
				<td align="left" width="150"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
