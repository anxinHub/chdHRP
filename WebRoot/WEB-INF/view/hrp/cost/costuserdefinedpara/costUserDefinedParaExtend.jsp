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
                
                target_year:$("#target_year_month").val().substring(0,4), 
                
                target_month:$("#target_year_month").val().substring(4,6),
                 
                defined_para_code:liger.get("defined_para_code").getValue(),
        };

        ajaxJsonObjectByUrl("costUserDefinedParaExtendInheritance.do?isCheck=false",formPara,function(responseData){
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
   
    function saveCostUserDefinedParaExtend(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
	    $("#year_month").ligerTextBox({width:160});
        
 		$("#target_year_month").ligerTextBox({width:160});

 		autocomplete("#defined_para_code","../queryDeptParaDict.do?isCheck=false","id","text",true,true);
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">源年月：</td>
                <td align="left" class="l-table-edit-td"><input  name="year_month" type="text" id="year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标年月：</td>
                <td align="left" class="l-table-edit-td"><input name="target_year_month" type="text" id="target_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊参数：</td>
                <td align="left" class="l-table-edit-td"><input name="defined_para_code" type="text" id="defined_para_code" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
