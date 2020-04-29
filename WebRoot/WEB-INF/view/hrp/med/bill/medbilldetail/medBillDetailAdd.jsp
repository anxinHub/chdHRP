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
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
           bill_id:$("#bill_id").val(),
            
           bill_detail_id:$("#bill_detail_id").val(),
            
           in_id:$("#in_id").val(),
            
           in_detail_id:$("#in_detail_id").val(),
            
           bill_money:$("#bill_money").val(),
            
           fav_money:$("#fav_money").val(),
            
           pay_money:$("#pay_money").val(),
            
           is_pay_all:$("#is_pay_all").val(),
            
           fav_money_temp:$("#fav_money_temp").val(),
            
           pay_money_temp:$("#pay_money_temp").val()
            
         };
        
        ajaxJsonObjectByUrl("addMedBillDetail.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='bill_id']").val('');
				 $("input[name='bill_detail_id']").val('');
				 $("input[name='in_id']").val('');
				 $("input[name='in_detail_id']").val('');
				 $("input[name='bill_money']").val('');
				 $("input[name='fav_money']").val('');
				 $("input[name='pay_money']").val('');
				 $("input[name='is_pay_all']").val('');
				 $("input[name='fav_money_temp']").val('');
				 $("input[name='pay_money_temp']").val('');
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
   
    function saveMedBillDetail(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票ID：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_id" type="text" id="bill_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票明细ID：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_detail_id" type="text" id="bill_detail_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库单ID：</td>
            <td align="left" class="l-table-edit-td"><input name="in_id" type="text" id="in_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入库明细ID：</td>
            <td align="left" class="l-table-edit-td"><input name="in_detail_id" type="text" id="in_detail_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票金额：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_money" type="text" id="bill_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">优惠金额：</td>
            <td align="left" class="l-table-edit-td"><input name="fav_money" type="text" id="fav_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计付款：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_money" type="text" id="pay_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">全部开票标志：</td>
            <td align="left" class="l-table-edit-td"><input name="is_pay_all" type="text" id="is_pay_all" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">优惠金额临时：</td>
            <td align="left" class="l-table-edit-td"><input name="fav_money_temp" type="text" id="fav_money_temp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款金额临时：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_money_temp" type="text" id="pay_money_temp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
