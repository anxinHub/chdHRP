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
    	
    	 var strgetSelectValue=false;
    	 
   		 var formPara={

   		           emp_id:'${emp_ids}',
   		           
   		           sex:$("#sex").val(),
   		           
   		           is_pay_box:$("input[id='is_pay_box']").is(':checked')==true?"1":"0",
   	    		            
   		           is_pay:$("#is_pay").val(),
   		            
   		           pay_type_code_box:$("input[id='pay_type_code_box']").is(':checked')==true?"1":"0",
   	    	    		     
   		           pay_type_code:liger.get("pay_type_code").getValue(),
   		            
   		           station_code_box:$("input[id='station_code_box']").is(':checked')==true?"1":"0",
 	    	    		     
   		           station_code:liger.get("station_code").getValue(),
   		            
   		           duty_code_box:$("input[id='duty_code_box']").is(':checked')==true?"1":"0",
   	    	    		     
   		           duty_code:liger.get("duty_code").getValue(),
	    		       
    		       id_number_box:$("input[id='id_number_box']").is(':checked')==true?"1":"0",
    	    	    		     
    		       id_number:$("#id_number").val(),
    		       
    		       phone_box:$("input[id='phone_box']").is(':checked')==true?"1":"0",
	    	    		     
   	    		   phone:$("#phone").val(),
   	    		   
   	    		   mobile_box:$("input[id='mobile_box']").is(':checked')==true?"1":"0",
   	    		     
   	    		   mobile:$("#mobile").val(),
   	    		   
   	    		   emial_box:$("input[id='emial_box']").is(':checked')==true?"1":"0",
   	    		     
 	   	    	   emial:$("#emial").val(),
 	   	    	   
 	   	    	   note_box:$("input[id='note_box']").is(':checked')==true?"1":"0",
   	    		     
  	 	   	       note:$("#note").val(),
  	 	   	       
  	 	   		   is_buyer_box:$("input[id='is_buyer_box']").is(':checked')==true?"1":"0",
  		            
  	 	   		   is_buyer:$("#is_buyer").val(),
  	 	   	       
  	 	   		   countries_code_box:$("input[id='countries_code_box']").is(':checked')==true?"1":"0",
  	    		     
  	 	   	 	   countries_code:liger.get("countries_code").getValue(),
  	 	   	 	   
  	 	   	       attr_code_box:$("input[id='attr_code_box']").is(':checked')==true?"1":"0",
  	    		     
  	 	   	 	   attr_code:liger.get("attr_code").getValue()
	  	 	   	 	   
   		         };
   		        ajaxJsonObjectByUrl("../accempattr/initAccEmpAttr.do?isCheck=false",formPara,function(responseData){
   		            
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
   
    function saveAccDeptAttr(){
        if($("form").valid()){
            save();
        }
   }
    
    function set(){
    	$.ligerDialog.open({ url : '../acccheckitem/accCheckEmpSetPage.do?isCheck=false',data:{}, height: 400,width: 650, 
			title:'自定义属性',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			//parentframename:window.name
			 });
    }
    function attr(){
    	autocomplete("#attr_code","../queryEmpSet.do?isCheck=false","id","text",true,true,'',true,'',180);
    }
    
    function loadDict(){
    		
    	autocomplete("#station_code","../../sys/queryStationDict.do?isCheck=false","id","text",true,true,'',true,'',160);
    	
    	autocomplete("#duty_code","../../sys/queryDutyDict.do?isCheck=false","id","text",true,true,'',true,'',160);
    	
    	autocomplete("#pay_type_code","../queryEmpPay.do?isCheck=false","id","text",true,true,'',true,'',160);
    	
    	autocomplete("#countries_code","../../sys/queryCountriesDict.do?isCheck=false","id","text",true,true,'',false,'',160);
    	
    	autocomplete("#attr_code","../queryEmpSet.do?isCheck=false","id","text",true,true,'',true,'',160);
    	
    	
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
       		 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">性别：</td>
                <td align="left" class="l-table-edit-td"><select id="sex" name="sex" >
               			<option value="0">男</option>
               			<option value="1">女</option>
                	</select></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">是否发放工资：</td>
                <td align="left" class="l-table-edit-td"><select id="is_pay" name="is_pay" >
               			<option value="0">否</option>
               			<option value="1">是</option>
                	</select></td>
                <td align="left"><input name="chBox" type="checkbox"  id="is_pay_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">发放方式：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_type_code" type="text"  id="pay_type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="pay_type_code_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">岗位：</td>
                <td align="left" class="l-table-edit-td"><input name="station_code" type="text"  id="station_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="station_code_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">职务：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_code" type="text"  id="duty_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="duty_code_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">身份证号：</td>
                <td align="left" class="l-table-edit-td"><input name="id_number" type="text"  id="id_number" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="id_number_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">手机号：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text"  id="phone" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="phone_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">电话号：</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text"  id="mobile" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="mobile_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">邮箱：</td>
                <td align="left" class="l-table-edit-td"><input name="emial" type="text"  id="emial" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="emial_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text"  id="note" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="note_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">是否采购员：</td>
                <td align="left" class="l-table-edit-td"><select id="is_buyer" name="is_buyer" >
               			<option value="0">否</option>
               			<option value="1">是</option>
                	</select></td>
                <td align="left"><input name="chBox" type="checkbox"  id="is_buyer_box"  /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:120px;">国籍：</td>
                <td align="left" class="l-table-edit-td"><input name="countries_code" type="text"  id="countries_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="countries_code_box"  /></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:120px;">自定义属性：</td>
                <td align="left" class="l-table-edit-td"><input name="attr_code" type="text" onclick="attr();" id="attr_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"><input name="chBox" type="checkbox"  id="attr_code_box"  /></td>
                <td align="left"><a onclick="set()">设置</a></td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
