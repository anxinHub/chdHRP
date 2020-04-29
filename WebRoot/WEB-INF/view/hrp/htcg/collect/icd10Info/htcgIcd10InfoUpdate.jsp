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
        		mr_no : $("#mr_no").val(),
				in_hos_no : $("#in_hos_no").val(),
				icd10_type_code : liger.get("icd10_type_code").getValue(),
				icd10_code : liger.get("icd10_code").getValue(),
				icd10_seq : liger.get("icd10_seq").getValue(),
				outcome_code : liger.get("outcome_code").getValue(),
        };
        ajaxJsonObjectByUrl("updateHtcgIcd10Info.do",formPara,function(responseData){
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
   
    function updateIcd10Info(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        autocomplete("#icd10_type_code","../../base/queryHtcgIcd10TypeDict.do?isCheck=false","id","text",true,true);
    	liger.get("icd10_type_code").setValue('${icd10_type_code}');
		liger.get("icd10_type_code").setText('${icd10_type_name}'); 
		autocomplete("#icd10_code","../../base/queryHtcgIcd10Dict.do?isCheck=false","id","text",true,true);
		liger.get("icd10_code").setValue('${icd10_code}');
		liger.get("icd10_code").setText('${icd10_name}'); 
		autocomplete("#outcome_code","../../base/queryHtcgOutcomeDict.do?isCheck=false","id","text",true,true);
		liger.get("outcome_code").setValue('${outcome_code}');
		liger.get("outcome_code").setText('${outcome_name}'); 
        $("#mr_no").ligerTextBox({disabled:true});
        $("#in_hos_no").ligerTextBox({disabled:true});
	    $("#icd10_seq").ligerComboBox({  
              data: [
                  { text: '1', id: '1' },
                  { text: '2', id: '2' },
                  { text: '3', id: '3' }
              ], width : 178,value: '${icd10_seq}',
          }); 
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
      	<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td"style="padding-left: 20px;">病案号：</td>
				<td align="left" class="l-table-edit-td"><input name="mr_no" type="text" id="mr_no" ltype="text" validate="{required:true}" value='${mr_no}'/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">住院号：</td>
				<td align="left" class="l-table-edit-td"><input name="in_hos_no" type="text" id="in_hos_no" ltype="text" validate="{required:true}"  value='${in_hos_no}' /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">诊断类别：</td>
				<td align="left" class="l-table-edit-td"><input name="icd10_type_code" type="text" id="icd10_type_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">诊断编码：</td>
				<td align="left" class="l-table-edit-td"><input name="icd10_code" type="text" id="icd10_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">诊断序列：</td>
				<td align="left" class="l-table-edit-td"><input name="icd10_seq" type="text" id="icd10_seq" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>	
			<tr>
					<td align="left" class="l-table-edit-td"
					style="padding-left: 20px;">转归：</td>
				<td align="left" class="l-table-edit-td"><input
					name="outcome_code" type="text" id="outcome_code" ltype="text"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
		</table>
    </form>
    </body>
</html>
