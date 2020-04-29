<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
         loadForm();
     });  
     function  save(){
        var formPara={
           drug_code:$("#drug_code").val(),
           drug_name:$("#drug_name").val(),
           drug_type_code:liger.get("drug_type_code").getValue(),
           mode_code:$("#mode_code").val(),
           unit_code:$("#unit_code").val(),
           price:$("#price").val(),
           fac_id:liger.get("fac_code").getValue().split(".")[0],
           fac_no:liger.get("fac_code").getValue().split(".")[1],
           is_stop:liger.get("is_stop").getValue(),
         };
        ajaxJsonObjectByUrl("addHtcgDrugDict.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='drug_code']").val('');
				 $("input[name='drug_name']").val('');
				 $("input[name='drug_type_code']").val('');
				 $("input[name='mode_code']").val('');
				 $("input[name='unit_code']").val('');
				 $("input[name='price']").val('');
				 $("input[name='fac_code']").val('');
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
   
    function saveHtcDrugDict(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#drug_type_code","../../base/queryHtcgDrugTypeDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#fac_code","../../base/queryHosFacDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#is_stop", "../../base/queryHtcgYearOrNo.do?isCheck=false", "id", "text", true, true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品编码：</td>
                <td align="left" class="l-table-edit-td"><input name="drug_code" type="text" id="drug_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品名称：</td>
                <td align="left" class="l-table-edit-td"><input name="drug_name" type="text" id="drug_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品类别：</td>
                <td align="left" class="l-table-edit-td"><input name="drug_type_code" type="text" id="drug_type_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格：</td>
                <td align="left" class="l-table-edit-td"><input name="mode_code" type="text" id="mode_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量单位：</td>
                <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
                <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂家：</td>
                <td align="left" class="l-table-edit-td"><input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}"/></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>