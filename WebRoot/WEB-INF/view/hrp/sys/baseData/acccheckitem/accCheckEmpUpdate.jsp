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
    	 //身份证验证
    	  var reg=/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
          if($("#id_number").val()!=null && ($("#id_number").val()!="")){
    	 
        	if(reg.test($("#id_number").val())==false){
    		  $.ligerDialog.error("身份证输入不合法!");
				
    		  return;
    	  }
     }
          /* //邮箱验证
        var re = /^\d{5,12}@[qQ][qQ]\.com$/;
        if($("#email").val()!=null && ($("#email").val()!="")){
       	 
        	if(re.test($("#email").val())==false){
    		  $.ligerDialog.error("邮箱输入不合法!");
				
    		  return;
    	  }
     }
        //工作电话验证
        var re1 = /^[1][358][0-9]{9}$/; 
        if($("#phone").val()!=null && ($("#phone").val()!="")){
          	 
        	if(re1.test($("#phone").val())==false){
    		  $.ligerDialog.error("工作电话输入不合法!");
				
    		  return;
    	  }
     }
        
        //手机电话验证
        var re1 = /^[1][358][0-9]{9}$/; 
        if($("#mobile").val()!=null && ($("#mobile").val()!="")){
          	 
        	if(re1.test($("#mobile").val())==false){
    		  $.ligerDialog.error("手机输入不合法!");
				
    		  return;
    	  }
     }
   */
  
        var formPara={
        		emp_id:'${emp_id}',
        		emp_no:'${emp_no}',
        		group_id:'${group_id}', 
        		hos_id:'${hos_id}',     
        		sex:$("#sex").val(),     
        		is_pay:$("#is_pay").val(),   
        		pay_type_code:liger.get("pay_type_code").getValue(),
        		station_code:liger.get("station_code").getValue(),
        		duty_code:liger.get("duty_code").getValue(),
        		countries_code:liger.get("countries_code").getValue(),
        		id_number:$("#id_number").val(), 
        		phone:$("#phone").val(),   
        		mobile:$("#mobile").val(), 
        		email:$("#email").val(), 
//         		is_buyer:$("#is_buyer").val(),  
        		note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("../accempattr/updateAccEmpAttr.do",formPara,function(responseData){
            
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
     //$("form").ligerForm();
 }       
   
    function saveAccEmpAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#station_code","../../sys/queryStationDict.do?isCheck=false","id","text",true,true,'',false,'${station_code}');
    	autocomplete("#duty_code","../../sys/queryDutyDict.do?isCheck=false","id","text",true,true,'',false,'${duty_code}');
    	autocomplete("#pay_type_code","../queryEmpPay.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#countries_code","../../sys/queryCountriesDict.do?isCheck=false","id","text",true,true,'',false,'${countries_code }',300);
    	
    	liger.get("countries_code").setValue("${countries_code}");
    	liger.get("countries_code").setText("${countries_name}");
    	
    	
    	$("#countries_code").ligerTextBox({width:300});
    	
    	if('${is_stop}' == 0){
    		$("#is_stop").val("否");
    	}else{
    		$("#is_stop").val("是");
    	}
    	$("#is_stop").ligerTextBox({width:180,disabled: true });
    	$("#emp_code").ligerTextBox({width:180,disabled: true });
    	$("#dept_code").ligerTextBox({ width:180,disabled: true });
    	$("#emp_name").ligerTextBox({ width:180,disabled: true });
    	$("#kind_code").ligerTextBox({width:180,disabled: true });
    	
    	$("#is_pay").ligerTextBox({ width:180});
    	$("#sex").ligerTextBox({ width:180});
    	$("#station_code").ligerTextBox({width:180});
    	$("#duty_code").ligerTextBox({ width:180});
    	$("#pay_type_code").ligerTextBox({width:180});
    	
    	$("#id_number").ligerTextBox({width:180});
    	$("#phone").ligerTextBox({width:180});
    	

    	$("#mobile").ligerTextBox({width:180});
    	$("#email").ligerTextBox({width:180});
    	
    	$("#sex").val("${sex}");
    	$("#is_pay").val("${is_pay}");
//     	$("#is_buyer").val("${is_buyer}");
    	
    	liger.get("station_code").setValue("${station_code}");
    	liger.get("station_code").setText("${station_name}");
    	
    	liger.get("duty_code").setValue("${duty_code}");
    	liger.get("duty_code").setText("${duty_name}");
    	
    	liger.get("pay_type_code").setValue("${pay_type_code}");
    	liger.get("pay_type_code").setText("${pay_type_name}");
    	
    	$("#note").val('${note}');
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        	<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工编码：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" value="${emp_code }" disabled="disabled" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" value="${dept_name }" disabled="disabled" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工名称：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_name" type="text" value="${emp_name }" disabled="disabled" id="emp_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" value="${kind_name }" disabled="disabled" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text"  id="is_stop" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">性别：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="sex" name="sex">
                		<option value="0">男</option>
                		<option value="1">女</option>
                	</select>
                </td>
            	<td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否发工资：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_pay" name="is_pay">
                		<option value="1">是</option>
                		<option value="0">否</option>
                	</select>
                </td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发放方式：</td>
                <td align="left" class="l-table-edit-td">
               	 	<input name="pay_type_code" type="text"  id="pay_type_code" ltype="text" validate="{maxlength:20}" />
                </td>
            	<td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">岗位：</td>
                <td align="left" class="l-table-edit-td"><input name="station_code" type="text"  id="station_code" ltype="text"  validate="{maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职务：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_code" type="text"  id="duty_code" ltype="text" validate="{maxlength:20}" /> </td>
            	<td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">身份证号：</td>
                <td align="left" class="l-table-edit-td"><input name="id_number" type="text" value="${id_number }"  id="id_number"  ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工作电话：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text" value="${phone }"  id="phone" ltype="text" validate="{required:false,maxlength:20}" /> </td>
            	<td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手机：</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text" value="${mobile }"   id="mobile" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">EMAIL：</td>
                <td align="left" class="l-table-edit-td"><input name="email" type="text" value="${email }"  id="email" ltype="text" validate="{required:false,maxlength:20}" /> </td>
            	<td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">国籍：</td>
                <td align="left" class="l-table-edit-td"><input name="countries_code" type="text"  id="countries_code" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="left"></td>
                <td align="left"></td>
                <td align="left"></td>
            </tr>  
<!--             <tr> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否采购员：</td> -->
<!--                 <td align="left" class="l-table-edit-td"> -->
<!--                 <select id="is_buyer" name="is_buyer"> -->
<!--                 		<option value="1">是</option> -->
<!--                 		<option value="0">否</option> -->
<!--                 	</select> -->
<!--                 </td> -->
<!--                 <td align="left" width="150"></td> -->
<!--             </tr>  -->
            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
			                <td align="left" class="l-table-edit-td" colspan="4">
			                	<textarea rows="3" cols="75" id="note" name="note" ltype="text" validate="{required:false,maxlength:50}" style="resize: none;"></textarea>
			                </td>
			                <td align="left"></td>
			</tr> 
        </table>
    </form>
   
    </body>
</html>
