<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
   <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
    		  awards_item_code:liger.get("awards_item_code").getValue(),
              
              id:$("#id").val(),
               
              grant_month:liger.get("grant_month").getValue(),
               
              start_year:liger.get("start_year").getValue(),
               
              start_month:liger.get("start_month").getValue(),
               
              end_year:liger.get("end_year").getValue(),
               
              end_month:liger.get("end_month").getValue()
        };
        ajaxJsonObjectByUrl("updateBudgAwardsGrantMonth.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveBudgAwardsGrantMonth(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
        //奖金项目  下拉框
        autocomplete("#awards_item_code","../../../queryBudgAwardsItem.do?isCheck=false","id","text",true,true,'',false,'${awards_item_code}');
        
    	//发放月份下拉框
    	autoCompleteByData("#grant_month", monthData.Rows, "id", "text", true, true,'',false,'${grant_month}');
    	 //开始年度下拉框
    	autoCompleteByData("#start_year", yearData.Rows, "id", "text", true, true,'',false,'${start_year}');
    	 //开始月份下拉框
    	autoCompleteByData("#start_month", monthData.Rows, "id", "text", true, true,'',false,'${start_month}');
    	 //结束年度下拉框
    	autoCompleteByData("#end_year", yearData.Rows, "id", "text", true, true,'',false,'${end_year}');
    	 //结束月份下拉框
    	autoCompleteByData("#end_month", monthData.Rows, "id", "text", true, true,'',false,'${end_month}');
    	 
    	 $("#awards_item_code").ligerTextBox({disabled:true});
    	 $("#id").ligerTextBox({disabled:true});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>奖金项目编码<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="awards_item_code" type="text" id="awards_item_code" ltype="text" value="${awards_item_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>序号<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="id" type="text" id="id" ltype="text" value="${id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发放月<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="grant_month" type="text" id="grant_month" ltype="text" value="${grant_month}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开始年度<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="start_year" type="text" id="start_year" ltype="text" value="${start_year}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开始月份<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="start_month" type="text" id="start_month" ltype="text" value="${start_month}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>结束年度<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="end_year" type="text" id="end_year" ltype="text" value="${end_year}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>结束月份<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="end_month" type="text" id="end_month" ltype="text" value="${end_month}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
