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
        
     });  
     
     function  save(){
    	 
		 mat=$("#is_mat").is(":checked") ? 1 : 0;
    	 
     	 med=$("#is_med").is(":checked") ? 1 : 0;
     	 
     	 ass=$("#is_ass").is(":checked") ? 1 : 0;
     	 
     	 sup=$("#is_sup").is(":checked") ? 1 : 0;
     	 
     	 if(mat=='0' && med=='0' && ass=='0' && sup=='0'){
     		 
     		 $.ligerDialog.error("系统模块不能为空");
     		 
     		 return ; 
     	 }

		var formPara = {
				
		   cus_id:'',
				
           cus_code:$("#cus_code").val(),
            
           type_code:liger.get("type_code").getValue(),
            
           cus_name:$("#cus_name").val(),
            
           sort_code:$("#sort_code").val(),
            
           is_stop:$("#is_stop").val(),

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

			note : $("#note").val(),
			
			is_mat:$("#is_mat").is(":checked") ? 1 : 0,
					
      	    is_med:$("#is_med").is(":checked") ? 1 : 0,
      	    		
      	    is_ass:$("#is_ass").is(":checked") ? 1 : 0,
      	    		
      	    is_sup:$("#is_sup").is(":checked") ? 1 : 0 ,
      	    aff_code:liger.get("guanlianfang_code").getValue(),
      	    inst_code:liger.get("jigou_code").getValue(),
      	    dang_code:liger.get("xianzhong_code").getValue(),
      	    tmop_code:liger.get("jiesuan_code").getValue(),
      	    range_id:liger.get("zhagnqi_code").getValue()

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
		
		$("#sort_code").ligerTextBox({ disabled: true });
		 
		autocomplete("#type_code","../../sys/queryCusTypeDict.do?isCheck=false", "id", "text",true, true);
		
		autocomplete("#guanlianfang_code","../queryAccYewuDict.do?isCheck=false&table_code=01001", "id", "text",true, true);
		autocomplete("#jigou_code","../queryAccYewuDict.do?isCheck=false&table_code=01002", "id", "text",true, true);
		autocomplete("#xianzhong_code","../queryAccYewuDict.do?isCheck=false&table_code=01003", "id", "text",true, true);
		autocomplete("#jiesuan_code","../queryAccYewuDict.do?isCheck=false&table_code=01004", "id", "text",true, true);
		
		autocomplete("#zhagnqi_code","../queryAccBudgRange.do?isCheck=false", "id", "text",true, true);
		
	}
</script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>客户编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="cus_code" type="text" id="cus_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>客户名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="cus_name" type="text" id="cus_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color ="red">*</font>:</b></td>
			    <td align="left" class="l-table-edit-td">
			                	<select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
			    </td>
			    <td align="left"></td>
			    
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>客户类别:</b></td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号:</b></td>
                <td align="left" class="l-table-edit-td" ><input name="sort_code" type="text" id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,digits:true , maxlength:20}" /></td>
                <td align="left"></td>
                
                <td align="right" class="l-table-edit-td" colspan="3">
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<input name="is_mat"  id ="is_mat" type="checkbox" />物流管理&nbsp;&nbsp;
                	<input name="is_med"  id ="is_med"  type="checkbox" />药品管理&nbsp;&nbsp;
                	<input name="is_ass"  id ="is_ass" type="checkbox" />固定资产&nbsp;&nbsp;
                	<input name="is_sup"  id ="is_sup" type="checkbox" />供应商平台&nbsp;&nbsp;
                </td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                	<textarea rows="3" cols="70" id="note" name="note" ltype="text" validate="{maxlength:20}"></textarea>
                </td>
                <td align="left"></td>
            </tr> 

        </table>
        <div style="width: 100%; float: left; margin-top: 10px">
			<hr />
		</div>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
                <td align="left" class="l-table-edit-td"><input name="cus_id" type="hidden"  id="cus_id" ltype="text"  value ="${cus_id }" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开户银行：</td>
                <td align="left" class="l-table-edit-td">
              		   <input name="bank_name" type="text"  id="bank_name" ltype="text"  vavalidate="{required:false,maxlength:20}" />
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行账户：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_number" type="text"  id="bank_number" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">法人：</td>
                <td align="left" class="l-table-edit-td"><input name="legal" type="text"  id="legal" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">纳税人登记号：</td>
                <td align="left" class="l-table-edit-td"><input name="regis_no" type="text"  id="regis_no" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">手机：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text" id="phone" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">电话：</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text" id="mobile" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系人：</td>
                <td align="left" class="l-table-edit-td"><input name="contact" type="text" id="contact" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left" ></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">传真：</td>
                <td align="left" class="l-table-edit-td"><input name="fax" type="text" id="fax" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">地区：</td>
                <td align="left" class="l-table-edit-td"><input name="region" type="text" id="region" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left" ></td>
            </tr> 
            <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">EMAIL：</td>
                <td align="left" class="l-table-edit-td"><input name="email" type="text" id="email" ltype="text"    validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">邮政编码：</td>
                <td align="left" class="l-table-edit-td"><input name="zip_code" type="text" id="zip_code" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left" ></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地址：</td>
                <td align="left" class="l-table-edit-td"><input name="address" type="text" id="address" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">关联方：</td>
                <td align="left" class="l-table-edit-td"><input name="zip_code" type="text" id="guanlianfang_code" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">机构：</td>
                <td align="left" class="l-table-edit-td"><input name="address" type="text" id="jigou_code" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">险种：</td>
                <td align="left" class="l-table-edit-td"><input name="zip_code" type="text" id="xianzhong_code" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">结算方式：</td>
                <td align="left" class="l-table-edit-td"><input name="address" type="text" id="jiesuan_code" ltype="text"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">账期：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="zip_code" type="text" id="zhagnqi_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left" ></td>
            </tr> 
            
        </table>
    </form>
   
    </body>
</html>
