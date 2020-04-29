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
        $("#budg_year").change(function(){
			 query() ;
		})
		$("#proj_name").change(function(){
			 query() ;
		})
		$("#state").change(function(){
			 query() ;
		})
		$("#source_id").change(function(){
			 query() ;
		})
		$("#payment_item_id").change(function(){
			 query() ;
		})
		$("#payment_item_no").change(function(){
			 query() ;
		})
		
    });  
     
    function save(){
        var formPara={
        budg_year:$("#budg_year").val(),
        proj_id:$("#proj_id").val(),
        source_id:$("#source_id").val(),
        budg_amount:$("#budg_amount").val(),
        in_amount:$("#in_amount").val(),
        cost_amount:$("#cost_amount").val(),
        remain_amount:$("#remain_amount").val(),
        payment_item_id:$("#payment_item_id").val(),
        payment_item_no:$("#payment_item_no").val(),
        b_budg_amount:$("#b_budg_amount").val(),
        b_in_amount:$("#b_in_amount").val(),
        b_cost_amount:$("#b_cost_amount").val(),
        b_remain_amoun:$("#b_remain_amoun").val(),
        in_amount:$("#in_amount").val(),
        t_budg_amount:$("#t_budg_amount").val(),
        t_in_amount:$("#t_in_amount").val(),
        t_cost_amount:$("#t_cost_amount").val(),
        usable_amount:$("#usable_amount").val(),
        remain_amount:$("#remain_amount").val()

        
        };
        ajaxJsonObjectByUrl("updateBudgProjBegain.do?isCheck=false",formPara,function(responseData){
            
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
   
    function savebudgProjBegain(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
	      //加载年度   
        autocomplete("#budg_year","../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);        
        //加载项目名称
        autocomplete("#proj_id","../../queryProjName.do?isCheck=false","id","text",true,true,'',true); 
        //加载项目名称
        autocomplete("#source_id","../../queryBudgSource.do?isCheck=false","id","text",true,true,'',true); 
        
      //加载状态
        autocomplete("#state","../../queryBudgState.do?isCheck=false","id","text",true,true,'',true); 
      //支出项目ID
        autocomplete("#payment_item_id","queryBudgPaymentItemId.do?isCheck=false","itemid","itemtext",true,true,'',true); 
      //支出项目变更号
        autocomplete("#payment_item_no","queryBudgPaymentItemNo.do?isCheck=false","idno","textno",true,true,'',true); 
      
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" value="${budg_year}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" value="${proj_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
            <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" ltype="text" value="${source_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" value="${state}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算金额：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_amount" type="text" id="budg_amount" ltype="text" value="${budg_amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">到账金额：</td>
            <td align="left" class="l-table-edit-td"><input name="in_amount" type="text" id="in_amount" ltype="text" value="${in_amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出金额：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_amount" type="text" id="cost_amount" ltype="text" value="${cost_amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目ID：</td>
           <td align="left" class="l-table-edit-td"><input name="payment_item_id" type="text" id="payment_item_id" ltype="text" value="${payment_item_id}" validate="{required:true,maxlength:20}" /></td>
           <td align="left"></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目变更ID：</td>
           <td align="left" class="l-table-edit-td"><input name="payment_item_no" type="text" id="payment_item_no" ltype="text" value="${payment_item_no}" validate="{required:true,maxlength:20}" /></td>
           <td align="left"></td>
        </tr>
  
		<tr>
		</tr>
        </table>
    </form>
    </body>
</html>
