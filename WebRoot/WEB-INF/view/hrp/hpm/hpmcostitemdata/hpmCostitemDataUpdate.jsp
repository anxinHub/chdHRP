<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
     var dataFormat;
     var app_mod_code = parent.app_mod_code;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
           acct_year:$("#acct_year").val(),
            
           acct_month:$("#acct_month").val(),
            
           dept_id:liger.get("dept_id").getValue().split(",")[0],
           dept_no:'${dept_no}',
            
           cost_item_code:liger.get("cost_item_code").getValue(),
            
           prim_cost:$("#prim_cost").val(),
            
           prim_cost_ret:$("#prim_cost_ret").val(),
            
           calc_cost:$("#calc_cost").val(),
            
           calc_cost_ret:$("#calc_cost_ret").val(),
            
           cost_tot_ret:$("#cost_tot_ret").val()
            
         };
        
        ajaxJsonObjectByUrl("updateHpmCostitemData"+ app_mod_code +".do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveCostitemData(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_code","../../hpm/queryCostItemSeq.do?isCheck=false","id","text",true,true);
    	liger.get("dept_id").setValue("${dept_id}");
    	liger.get("dept_id").setText("${dept_name}");
    	liger.get("cost_item_code").setValue("${cost_item_code}");
    	liger.get("cost_item_code").setText("${cost_item_name}");
     } 
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年度：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_year" class="Wdate" type="text" id="acct_year" ltype="text" disabled="disabled"
					value="${acct_year }" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算月份：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_month" class="Wdate" type="text" id="acct_month" ltype="text" disabled="disabled"
					value="${acct_month }" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" disabled="disabled"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目：</td>
				<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" disabled="disabled"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">直接支出：</td>
				<td align="left" class="l-table-edit-td"><input name="prim_cost" type="text" id="prim_cost" ltype="text" value="${prim_cost }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计提直接支出：</td>
				<td align="left" class="l-table-edit-td"><input name="prim_cost_ret" type="text" id="prim_cost_ret" ltype="text" value="${prim_cost_ret}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">间接支出：</td>
				<td align="left" class="l-table-edit-td"><input name="calc_cost" type="text" id="calc_cost" ltype="text" value="${calc_cost }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计提间接支出：</td>
				<td align="left" class="l-table-edit-td"><input name="calc_cost_ret" type="text" id="calc_cost_ret" ltype="text" value="${calc_cost_ret }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计提合计支出：</td>
				<td align="left" class="l-table-edit-td"><input name="cost_tot_ret" type="text" id="cost_tot_ret" ltype="text" value="${cost_tot_ret }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
