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
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        uci_userecorddr:$("#uci_userecorddr").val(),
        analysis_code:$("#analysis_code").val(),
        charge_kind_id:$("#charge_kind_id").val(),
        consum_code:$("#consum_code").val(),
        dept_code:$("#dept_code").val(),
        unit_code:$("#unit_code").val(),
        quantity_type:$("#quantity_type").val(),
        price:$("#price").val(),
        amount:$("#amount").val(),
        quantity_type:$("#quantity_type").val(),
        relative_qty:$("#relative_qty").val(),
        add_date:$("#add_date").val(),
        use_date:$("#use_date").val(),
        cancel_date:$("#cancel_date").val(),
        month_str:$("#month_str").val(),
        sum_by_month_flag:$("#sum_by_month_flag").val(),
        pay_price:$("#pay_price").val(),
        pay_amount:$("#pay_amount").val()
        };
        ajaxJsonObjectByUrl("updateAssEquseconsumableitem.do",formPara,function(responseData){
            
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
   
    function saveAssEquseconsumableitem(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用记录（ASS_EQUseRecord）：</td>
            <td align="left" class="l-table-edit-td"><input name="uci_userecorddr" type="text" id="uci_userecorddr" ltype="text" value="${uci_userecorddr}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分析项</td>
            <td align="left" class="l-table-edit-td"><input name="analysis_code" type="text" id="analysis_code" ltype="text" value="${analysis_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>         
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用自增ID：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_kind_id" type="text" id="charge_kind_id" ltype="text" value="${charge_kind_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">chargeItemId：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_id" type="text" id="charge_item_id" ltype="text" value="${charge_item_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">代码(服务消耗资源项定义表 ASS_EQCConsumableItem)：</td>
            <td align="left" class="l-table-edit-td"><input name="consum_code" type="text" id="consum_code" ltype="text" value="${consum_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">deptCode：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" value="${dept_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">unitCode：</td>
            <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" value="${unit_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数量：</td>
            <td align="left" class="l-table-edit-td"><input name="quantity_type" type="text" id="quantity_type" ltype="text" value="${quantity_type}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" value="${price}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
            <td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text" value="${amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数量类型（1:绝对量 2:相对量 3:相对量已计算绝对量）：</td>
            <td align="left" class="l-table-edit-td"><input name="quantity_type" type="text" id="quantity_type" ltype="text" value="${quantity_type}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">相对量：</td>
            <td align="left" class="l-table-edit-td"><input name="relative_qty" type="text" id="relative_qty" ltype="text" value="${relative_qty}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           <!--   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">细项数量：</td>
            <td align="left" class="l-table-edit-td"><input name="uci_servdetitemqty" type="text" id="uci_servdetitemqty" ltype="text" value="${uci_servdetitemqty}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>-->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">增加日期：</td>
            <td align="left" class="l-table-edit-td"><input name="add_date" type="text" id="add_date" ltype="text" value="${add_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="use_date" type="text" id="use_date" ltype="text" value="${use_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消日期：</td>
            <td align="left" class="l-table-edit-td"><input name="cancel_date" type="text" id="cancel_date" ltype="text" value="${cancel_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计月份：</td>
            <td align="left" class="l-table-edit-td"><input name="month_str" type="text" id="month_str" ltype="text" value="${month_str}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否是按月统计：</td>
            <td align="left" class="l-table-edit-td"><input name="sum_by_month_flag" type="text" id="sum_by_month_flag" ltype="text" value="${sum_by_month_flag}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单独收费消耗项单价：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_price" type="text" id="pay_price" ltype="text" value="${pay_price}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单独收费消耗项总金额：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_amount" type="text" id="pay_amount" ltype="text" value="${pay_amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
