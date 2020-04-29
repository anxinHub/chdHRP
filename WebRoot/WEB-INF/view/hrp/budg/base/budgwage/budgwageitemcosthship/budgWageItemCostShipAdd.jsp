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
     var budg_year;
     $(function (){
         loadDict()//加载下拉框
         loadForm();
         
         $("#budg_year").change(function() {
 			budg_year = liger.get("budg_year").getValue();
 			//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
 			
 			liger.get("subj_code").setValue('');
 			autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'',180);
         })
         
     });  
     
     function  save(){
         var formPara={
        		
           budg_year:liger.get("budg_year").getValue(),
            
           wage_item_code:liger.get("wage_item_code").getValue(),
           wage_item_name:liger.get("wage_item_code").selectedText.split(" ")[1],
            
           subj_code:liger.get("subj_code").getValue(),
     	   
         };
        
        ajaxJsonObjectByUrl("addBudgWageItemCostShip.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='budg_year']").val('');
				 $("input[name='wage_item_code']").val('');
				 $("input[name='subj_code']").val('');
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
   
    function saveBudgWageItemCostShip(){
        if($("form").valid()){
            save();
        }
    }
   
    function loadDict(){
     	//字典下拉框
    	//预算年度下拉框
		//autocomplete("#budg_year","../../../queryBudgYearTen.do?isCheck=false","id","text",true,true,'',true,"",180);
         autocompleteAsync("#budg_year","../../../queryBudgYearTen.do?isCheck=false","id","text",true,true,'',true,budg_year,180);
		
     	liger.get("budg_year").setValue(${sessionScope.acct_year});
     	budg_year = liger.get("budg_year").getValue();
		//工资项目下拉框
		autocomplete("#wage_item_code","../../../queryBudgWageItem.do?isCheck=false","id","text",true,true,'',false,"",180);
		//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
		autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'',180);
    }
</script>
  
</head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度<font color="red" >*</font>：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目编码<font color="red" >*</font>：</td>
            <td align="left" class="l-table-edit-td"><input name="wage_item_code" type="text" id="wage_item_code" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码<font color="red" >*</font>：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
        </tr> 
    	</table>
   </form>
   
   </body>
</html>
