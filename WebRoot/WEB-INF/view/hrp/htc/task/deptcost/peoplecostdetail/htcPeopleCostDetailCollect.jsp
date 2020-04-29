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
       		start_acc_month:$("#start_acc_month").val(),
       		end_acc_month:$("#end_acc_month").val(),
       		dept_no:liger.get("dept_code").getValue()==""?"":liger.get("dept_code").getValue().split(".")[1],
       		dept_id:liger.get("dept_code").getValue()==""?"":liger.get("dept_code").getValue().split(".")[0],
            people_type_code:liger.get("people_type_code").getValue(),
            people_code:liger.get("people_code").getValue()
         };
        ajaxJsonObjectByUrl("collectHtcPeopleCostDetail.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='dept_code']").val('');
				 $("input[name='people_code']").val('');
				 $("input[name='people_type_code']").val('');
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
		autocomplete("#people_type_code",
				"../../../info/base/queryHtcPeopleTypeDict.do?isCheck=false",
				"id", "text", true, true);
		autocomplete("#people_code",
				"../../../info/base/queryHtcPeopleDict.do?isCheck=false", "id",
				"text", true, true);
		autodate("#acc_year", "YYYY");
		autodate("#start_acc_month", "mm");
		autodate("#end_acc_month", "mm");
           
     } 
    </script>
  </head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年份：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate"
					name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true}"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始月：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate"
					name="start_acc_month" type="text" id="start_acc_month" ltype="text" validate="{required:true}"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM',maxDate:'#F{$dp.$D(\'acct_month_e\')}'})" /></td>
			<td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束月：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate"
					name="end_acc_month" type="text" id="end_acc_month" ltype="text" validate="{required:true}"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM',minDate:'#F{$dp.$D(\'acct_month_b\')}'})"/></td>
			<td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人员类别：</td>
                <td align="left" class="l-table-edit-td"><input name="people_type_code" type="text" id="people_type_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人员：</td>
                <td align="left" class="l-table-edit-td"><input name="people_code" type="text" id="people_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
