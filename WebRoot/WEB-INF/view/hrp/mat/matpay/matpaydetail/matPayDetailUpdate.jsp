<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        pay_id:$("#pay_id").val(),
        pay_detail_id:$("#pay_detail_id").val(),
        bill_id:$("#bill_id").val(),
        bill_detail_id:$("#bill_detail_id").val(),
        fav_money:$("#fav_money").val(),
        pay_money:$("#pay_money").val(),
        is_pay_all:$("#is_pay_all").val()
        };
        ajaxJsonObjectByUrl("updateMatPayDetail.do",formPara,function(responseData){
            
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
   
    function saveMatPayDetail(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款单ID：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_id" type="text" id="pay_id" ltype="text" value="${pay_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款单明细ID：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_detail_id" type="text" id="pay_detail_id" ltype="text" value="${pay_detail_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票ID：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_id" type="text" id="bill_id" ltype="text" value="${bill_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票明细ID：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_detail_id" type="text" id="bill_detail_id" ltype="text" value="${bill_detail_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">优惠金额：</td>
            <td align="left" class="l-table-edit-td"><input name="fav_money" type="text" id="fav_money" ltype="text" value="${fav_money}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款金额：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_money" type="text" id="pay_money" ltype="text" value="${pay_money}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否付完：</td>
            <td align="left" class="l-table-edit-td"><input name="is_pay_all" type="text" id="is_pay_all" ltype="text" value="${is_pay_all}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
