<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
        group_id:"${group_id}",
        hos_id:"${hos_id}",
        copy_code:"${copy_code}",
        acc_year:"${acc_year}",
        acc_month:"${acc_month}",
        dept_no:liger.get("dept_code").getValue().split(".")[1],
        dept_id:liger.get("dept_code").getValue().split(".")[0],
        people_type_code:liger.get("people_type_code").getValue(),
        people_code:liger.get("people_code").getValue(),
        cost_item_no:liger.get("cost_item_code").getValue().split(".")[1],
        cost_item_id:liger.get("cost_item_code").getValue().split(".")[0],
        amount:$("#amount").val()
        };
        ajaxJsonObjectByUrl("updateHtcPeopleCostDetail.do",formPara,function(responseData){
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
   
    function savePeopleCostDetail(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	
    	autocomplete("#dept_code",
				"../../../info/base/queryHtcDeptDict.do?isCheck=false", "id",
				"text", true, true);
        liger.get("dept_code").setValue('${dept_no}.${dept_id}');
     	liger.get("dept_code").setText('${dept_name}');
		autocomplete("#people_type_code",
				"../../../info/base/queryHtcPeopleTypeDict.do?isCheck=false",
				"id", "text", true, true);
		liger.get("people_type_code").setValue('${people_type_code}');
	    liger.get("people_type_code").setText('${people_type_name}');
		autocomplete("#people_code",
				"../../../info/base/queryHtcPeopleDict.do?isCheck=false", "id",
				"text", true, true);
		liger.get("people_code").setValue('${people_code}');
	    liger.get("people_code").setText('${people_name}');
		autocomplete("#cost_item_code",
				"../../../info/base/queryHtcCostItemDict.do?isCheck=false",
				"id", "text", true, true);
		 liger.get("cost_item_code").setValue('${cost_item_no}.${cost_item_id}');
	     liger.get("cost_item_code").setText('${cost_item_name}');

	    $("#year_month").ligerTextBox({ disabled: true });
        $("#dept_code").ligerTextBox({ disabled: true });
        $("#people_code").ligerTextBox({ disabled: true });
        $("#people_type_code").ligerTextBox({ disabled: true });
        $("#cost_item_code").ligerTextBox({ disabled: true });
        $("#orig_amount").ligerTextBox({ disabled: true });
     }   
    </script>
  </head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年月：</td>
                 <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text"  value="${acc_year}${acc_month}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人员：</td>
                <td align="left" class="l-table-edit-td"><input name="people_code" type="text" id="people_code" ltype="text"   validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人员类别：</td>
                <td align="left" class="l-table-edit-td"><input name="people_type_code" type="text" id="people_type_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text"   validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">调整金额：</td>
                <td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text"  value="${amount}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">原始金额：</td>
                <td align="left" class="l-table-edit-td"><input name="orig_amount" type="text" id="orig_amount" ltype="text"  value="${orig_amount}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
