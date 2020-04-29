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
        loadDict();
        loadForm();
    });  
     
    function save(){
        var formPara={
        		work_cause_code:$("#work_cause_code").val(),
        		work_cause_name:$("#work_cause_name").val(),
        		work_remark:$("#work_remark").val()
        };
        ajaxJsonObjectByUrl("updateHtcWorkCauseDict.do",formPara,function(responseData){
            
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
   
    function saveWorkCauseDict(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	$("#work_cause_code").ligerTextBox({
			disabled : true
		});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">作业动因编码：</td>
                <td align="left" class="l-table-edit-td"><input name="work_cause_code" type="text" disabled="disabled" id="work_cause_code" ltype="text"  value="${work_cause_code}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">作业动因名称：</td>
                <td align="left" class="l-table-edit-td"><input name="work_cause_name" type="text" id="work_cause_name" ltype="text"  value="${work_cause_name}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明：</td>
                <td align="left" class="l-table-edit-td"><input name="work_remark" type="text" id="work_remark" ltype="text"  value="${work_remark}"/></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    </body>
</html>
