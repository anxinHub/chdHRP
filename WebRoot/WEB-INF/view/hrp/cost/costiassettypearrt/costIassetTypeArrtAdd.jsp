<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	 
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
           asset_type_code:$("#asset_type_code").val(),
            
           asset_type_name:$("#asset_type_name").val(),
            
           supper_code:liger.get("supper_code").getValue(),//2016/10/25 lxj 将$("#supper_code").val() 改成了 liger.get("supper_code").getValue()
            
           is_last:$("#is_last").val(),
            
            
            
         };
        
        ajaxJsonObjectByUrl("addCostIassetTypeArrt.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
				
				
				 $("input[name='asset_type_code']").val('');
				 $("input[name='asset_type_name']").val('');
				 $("input[name='supper_code']").val('');
				 $("input[name='is_last']").val('');
				
				
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
   
    function saveCostIassetTypeArrt(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        /*2016/10/25 lxj
        	无形资产分类 上级编码 下拉列表
        */
   	 var params = {is_last:'0'};
	 autocomplete("#supper_code","../queryIassetTypeSupperCode.do?isCheck=false","id","text",true,true,params);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   		<input name="asset_type_id" type="hidden" id="asset_type_id"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_type_code" type="text" id="asset_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类名称：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_type_name" type="text" id="asset_type_name" ltype="text" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上级编码码：</td>
                <td align="left" class="l-table-edit-td"><input name="supper_code" type="text" id="supper_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否末级：</td>
                <td align="left" class="l-table-edit-td">
                		<select name="is_last" id="is_last" validate="{required:true}">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
                </td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
