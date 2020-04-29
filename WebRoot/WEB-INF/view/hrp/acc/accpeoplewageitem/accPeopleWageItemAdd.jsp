<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     var i=0;
     
     var data;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
		ajaxJsonObjectByUrl("queryTrendColumn.do",{},function(responseData){
			
			var tHtml="";
			
			if(responseData.Rows.length>0){
				
				tHtml=tHtml+"<tr><td colspan='6'><hr/></td></tr>";
				
			}
            
			$.each(responseData.Rows,function(index,value){
				
				i=++i;
	     		
				if(index%2==0){
					
					tHtml=tHtml+"<tr>";
					
				}
				
				tHtml=tHtml+"<td align='right' class='l-table-edit-td'  style='padding-left:20px;'>"+value.item_name+"：</td>"
				+"<td align='left' class='l-table-edit-td'><input name='item"+(index+1)+"' type='text' id='item"+(index+1)+"' ltype='text' validate='{required:true,maxlength:20}'  /></td>"
				+"<td align='left'></td>";
				
				if(index%2!=0){
					
					tHtml=tHtml+"</tr>";
					
				}
				
				
				if(value.item_cal == 0){
					
					
					
				}
				
	     	});
			
			if(i == responseData.Rows.length && i%2==1){
				
				tHtml=tHtml+"<td align='right' class='l-table-edit-td'  style='padding-left:20px;'>应发合计：</td>"
				+"<td align='left' class='l-table-edit-td'><input name='item' type='text' id='item' ltype='text' validate='{required:true,maxlength:20}' /></td>"
				+"<td align='left'></td></tr>";
				
			}else{
				
				tHtml=tHtml+"<tr><td align='right' class='l-table-edit-td'  style='padding-left:20px;'>应发合计：</td>"
				+"<td align='left' class='l-table-edit-td'><input name='item' type='text' id='item' ltype='text' validate='{required:true,maxlength:20}' /></td>"
				+"<td align='left'></td></tr>";
				
			}
			
			$("#table_id").append(tHtml);
			
        });
        
     });  
     
     function  save(){
    	 
        var formPara={
        		
           emp_id:liger.get("emp_code").getValue(),
        		
           type_code:$("#type_code").val(),
            
           type_name:$("#type_name").val(),
           
           bank_number:$("#bank_number").val(),
           
           is_stop:$("#is_stop").val(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("addAccWageType.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				/*  $("input[name='para_code']").val('');
				 $("input[name='mod_code']").val('');
				 $("input[name='para_name']").val('');
				 $("input[name='para_value']").val('');
				 $("input[name='note']").val('');
				 $("input[name='is_stop']").val(''); */
                parent.query();
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
   
    function saveAccPara(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
		           
     } 
    
    function detail(){
    	
    	var formPara={
        		
    	           emp_id:liger.get("emp_code").getValue()
    	            
    	         };
    	        
    	        ajaxJsonObjectByUrl("queryEmpByWageCode.do",formPara,function(responseData){
    	            
    	        	data = responseData;
    	        	
    	        	$.each(responseData,function(i,v){
    	        		
    	        		$("#wage_code").val(v.wage_name);
    	        		
    	        		$("#dept_code").val(v.dept_name);
    	        		
    	        		$("#emp_kind").val(v.kind_name);
    	        		
    	        		$("#sex").val(v.sex);
    	        		
    	        		$("#pay_type_name").val(v.pay_type_name);
    	        		
    	        		$("#station_name").val(v.station_name);
    	        		
    	        		$("#duty_name").val(v.duty_name);
    	        		
    	        		$("#id_number").val(v.id_number);
    	        		
    	        	});
    	        	
    	        });
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table_id">

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" onchange="detail()"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">性别：</td>
                <td align="left" class="l-table-edit-td"><input name="sex" type="text" id="sex" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发放方式：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_type_name" type="text" id="pay_type_name" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">岗位：</td>
                <td align="left" class="l-table-edit-td"><input name="station_name" type="text" id="station_name" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职务：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_name" type="text" id="duty_name" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">身份证号：</td>
                <td align="left" class="l-table-edit-td"><input name="id_number" type="text" id="id_number" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
