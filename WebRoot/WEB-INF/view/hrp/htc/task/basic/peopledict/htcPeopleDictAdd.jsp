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
           people_code:$("#people_code").val(),
           people_name:$("#people_name").val(),
           title_code:liger.get("title_code").getValue(),
           people_type_code:liger.get("people_type_code").getValue(),
           dept_no:liger.get("dept_id").getValue().split(".")[1],
           dept_id:liger.get("dept_id").getValue().split(".")[0],
           is_stop:liger.get("is_stop").getValue(),
           people_note:liger.get("people_note").getValue()
         };
            ajaxJsonObjectByUrl("addHtcPeopleDict.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='people_code']").val('');
				 $("input[name='people_name']").val('');
				 $("input[name='title_code']").val('');
				 $("input[name='people_type_code']").val('');
				 $("input[name='people_note']").val('');
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
   
    function savePeopleDict(){
        if($("form").valid()){
            save();
        }
   }

      function loadDict(){
         autocomplete("#title_code","../../../info/base/queryHtcPeopleTitleDict.do?isCheck=false","id","text",true,true);
         autocomplete("#people_type_code","../../../info/base/queryHtcPeopleTypeDict.do?isCheck=false","id","text",true,true);
         autocomplete("#is_stop", "../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,'0');
         autocomplete("#dept_id","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true);
      } 
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">人员编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="people_code" type="text" id="people_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">人员姓名：</td>
				<td align="left" class="l-table-edit-td"><input
					name="people_name" type="text" id="people_name" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">职称编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="title_code" type="text" id="title_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">人员类别编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="people_type_code" type="text" id="people_type_code"
					ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">备注：</td>
				<td align="left" class="l-table-edit-td"><input name="people_note" type="text" id="people_note" ltype="text"/></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
