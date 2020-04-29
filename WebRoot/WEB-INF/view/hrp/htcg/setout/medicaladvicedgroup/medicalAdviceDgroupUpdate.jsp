<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/htc.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/json2.js"></script>
    <link href="<%=path%>/lib/htc/style/select/chosen.css" rel="stylesheet">
    <script src="<%=path%>/lib/htc/style/select/chosen.jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        period_type_code:$("#period_type_code").val(),
        acct_month:$("#acct_month").val(),
        scheme_code:$("#scheme_code").val(),
        drgs_code:$("#drgs_code").val(),
        charge_item_code:$("#charge_item_code").val(),
        amount:$("#amount").val(),
        price:$("#price").val(),
        income_money:$("#income_money").val(),
        recipe_type_code:$("#recipe_type_code").val(),
        clp_d_step:$("#clp_d_step").val(),
        item_mr_count:$("#item_mr_count").val(),
        drgs_mr_count:$("#drgs_mr_count").val(),
        prim_percent:$("#prim_percent").val(),
        grand_percent:$("#grand_percent").val(),
        recipe_d_group:$("#recipe_d_group").val()
        };
        ajaxJsonObjectByUrl("updateMedicalAdviceDgroup.do",formPara,function(responseData){
            
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
   
    function saveMedicalAdviceDgroup(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">period_type_code：</td>
                <td align="left" class="l-table-edit-td"><input name="period_type_code" type="text" id="period_type_code" ltype="text"  value="${period_type_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">acct_month：</td>
                <td align="left" class="l-table-edit-td"><input name="acct_month" type="text" id="acct_month" ltype="text"  value="${acct_month}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">scheme_code：</td>
                <td align="left" class="l-table-edit-td"><input name="scheme_code" type="text" id="scheme_code" ltype="text"  value="${scheme_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">drgs_code：</td>
                <td align="left" class="l-table-edit-td"><input name="drgs_code" type="text" id="drgs_code" ltype="text"  value="${drgs_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">charge_item_code：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_item_code" type="text" id="charge_item_code" ltype="text"  value="${charge_item_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">amount：</td>
                <td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text"  value="${amount}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">price：</td>
                <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text"  value="${price}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">income_money：</td>
                <td align="left" class="l-table-edit-td"><input name="income_money" type="text" id="income_money" ltype="text"  value="${income_money}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">recipe_type_code：</td>
                <td align="left" class="l-table-edit-td"><input name="recipe_type_code" type="text" id="recipe_type_code" ltype="text"  value="${recipe_type_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">clp_d_step：</td>
                <td align="left" class="l-table-edit-td"><input name="clp_d_step" type="text" id="clp_d_step" ltype="text"  value="${clp_d_step}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">item_mr_count：</td>
                <td align="left" class="l-table-edit-td"><input name="item_mr_count" type="text" id="item_mr_count" ltype="text"  value="${item_mr_count}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">drgs_mr_count：</td>
                <td align="left" class="l-table-edit-td"><input name="drgs_mr_count" type="text" id="drgs_mr_count" ltype="text"  value="${drgs_mr_count}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">prim_percent：</td>
                <td align="left" class="l-table-edit-td"><input name="prim_percent" type="text" id="prim_percent" ltype="text"  value="${prim_percent}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">grand_percent：</td>
                <td align="left" class="l-table-edit-td"><input name="grand_percent" type="text" id="grand_percent" ltype="text"  value="${grand_percent}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">recipe_d_group：</td>
                <td align="left" class="l-table-edit-td"><input name="recipe_d_group" type="text" id="recipe_d_group" ltype="text"  value="${recipe_d_group}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
