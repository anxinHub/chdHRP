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
        		acc_year:$("#year_month").val().substring(0,4),
                
                acc_month:$("#year_month").val().substring(4,6),
                
                dept_id:liger.get("dept").getValue().split(".")[0],
                 
                dept_no:liger.get("dept").getValue().split(".")[1],
                 
                para_code:liger.get("para_code").getValue(),
                 
                para_value:$("#para_value").val()
        };
        ajaxJsonObjectByUrl("updateCostUserDefinedPara.do",formPara,function(responseData){
            
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
   
    function saveCostUserDefinedPara(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#dept","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true);
        var param = {
        		is_sys:0
        };
        liger.get("dept").setValue("${dept_id}.${dept_no}");
        liger.get("dept").setText("${dept_name}");
	    autocomplete("#para_code","../queryDeptParaDict.do?isCheck=false","id","text",true,true,param); 
	    liger.get("para_code").setValue("${para_code}");
        liger.get("para_code").setText("${para_name}");
        
	    $("#year_month").ligerTextBox({ disabled: true});
        
 		$("#dept").ligerTextBox({ disabled: true});
        
        $("#para_code").ligerTextBox({ disabled: true});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="year_month" type="text" id="year_month"  disabled="disabled"  ltype="text"  value="${year_month}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept" type="text" id="dept" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">自定义分摊参数：</td>
                <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计值：</td>
                <td align="left" class="l-table-edit-td"><input name="para_value" type="text" id="para_value" ltype="text"  value="${para_value}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
