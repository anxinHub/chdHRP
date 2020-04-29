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
        		acc_year:$("#acc_year").val(),
            	plan_code:liger.get("plan_code").getValue(),
            	proj_dept_no:liger.get("proj_dept_code").getValue().split(".")[1],
            	proj_dept_id:liger.get("proj_dept_code").getValue().split(".")[0],
            	people_code:liger.get("people_code").getValue(),
            	time_sum:$("#time_sum").val(),
         };
        ajaxJsonObjectByUrl("addHtcPeopleTime.do",formPara,function(responseData){
            if(responseData.state=="true"){
            	$("input[name='plan_code']").val('');
				$("input[name='proj_dept_code']").val('');
				$("input[name='people_code']").val('');
				$("input[name='time_sum']").val('');
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
   
    function savePeopleTime(){
        if($("form").valid()){
            save();
        }
   	}
	function loadDict(){

         autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true)
		
		 autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
		 
		 autocomplete("#people_code","../../../info/base/queryHtcPeopleDict.do?isCheck=false", "id", "text",true, true)

		 autodate("#acc_year","YYYY");
		 $("#acc_year").ligerTextBox({width:160});
		 $("#time_sum").ligerTextBox({width:160});
		 $("#acc_year").ligerTextBox({disabled:true});
    }   
</script>
</head>
<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
     	<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
          <td align="left" class="l-table-edit-td"><input class="Wdate" type="text" name="acc_year" id="acc_year" validate="{required:true}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
          <td align="left"></td>
       </tr>
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案：</td>
            <td align="left" class="l-table-edit-td"><input type="text" name="plan_code" id="plan_code" validate="{required:true}"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算科室：</td>
            <td align="left" class="l-table-edit-td"><input type="text" name="proj_dept_code" id="proj_dept_code" validate="{required:true}"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人员：</td>
            <td align="left" class="l-table-edit-td"><input type="text" name="people_code" id="people_code" validate="{required:true}"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工时(分钟)：</td>
            <td align="left" class="l-table-edit-td"><input type="text" name="time_sum" id="time_sum" validate="{required:true}"/></td>
            <td align="left"></td>
        </tr> 

    </table>
</form>
</body>
</html>
