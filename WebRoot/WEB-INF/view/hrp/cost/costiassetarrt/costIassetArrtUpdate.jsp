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
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        		
        		asset_id:$("#asset_id").val(),
                
                asset_type_id:liger.get("asset_type_id").getValue(),
                 
                asset_code:$("#asset_code").val(),
                 
                asset_name:$("#asset_name").val(),
                 
                prim_value:$("#prim_value").val(),
                 
                start_date:$("#start_date").val(),
                 
                end_date:$("#end_date").val(),
                 
                dep_year:$("#dep_year").val(),
				
				
        };
        ajaxJsonObjectByUrl("updateCostIassetArrt.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
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
   
    function saveCostIassetArrt(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#asset_type_id","../queryIassetTypeArrt.do?isCheck=false","id","text",true,true);
        
    	liger.get("asset_type_id").setValue("${asset_type_id}");	
    	
		liger.get("asset_type_id").setText("${asset_type_name}");
		
		//$("#asset_code").ligerTextBox({disabled: true});
		
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   <input name="asset_id" type="hidden" id="asset_id" value="${asset_id}"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_type_id" type="text" id="asset_type_id" ltype="text"  validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产编码：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_code" type="text" id="asset_code" ltype="text"  value="${asset_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_name" type="text" id="asset_name" ltype="text"  value="${asset_name}" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产原值：</td>
                <td align="left" class="l-table-edit-td"><input name="prim_value" type="text" id="prim_value" ltype="text"  value="${prim_value}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">启用年月：</td>
                <td align="left" class="l-table-edit-td"><input name="start_date" type="text" id="start_date" ltype="text"  value="${start_date}" validate="{required:true}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束年月：</td>
                <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" ltype="text"  value="${end_date}" validate="{required:true}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年限：</td>
                <td align="left" class="l-table-edit-td"><input name="dep_year" type="text" id="dep_year" ltype="text"  value="${dep_year}" validate="{required:true,maxlength:4}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
