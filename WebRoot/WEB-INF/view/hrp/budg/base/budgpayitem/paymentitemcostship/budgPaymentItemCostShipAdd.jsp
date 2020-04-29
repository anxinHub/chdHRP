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
    var budg_year ;
    $(function (){
    	loadForm();
        loadDict();
        
        $("#budg_year").change(function(){
        	budg_year = liger.get("budg_year").getValue();
        	liger.get("subj_code").setValue("");
 			liger.get("subj_code").setText("");
        	//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
            autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
        })
    });  
     
    function save(){
        var formPara={
	        budg_year:liger.get("budg_year").getValue(),
	        
	        payment_item_id:liger.get("payment_item_id").getValue(),
	        
	        fund_nature:liger.get("fund_nature").getValue(),
	        
	        subj_code:liger.get("subj_code").getValue()
        };
        ajaxJsonObjectByUrl("addBudgPaymentItemCostShip.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
    function loadForm(){
	    $.metadata.setType("attr", "validate");
	    var v = $("form").validate({
	        errorPlacement: function (lable, element){
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
	        success: function (lable){
	            lable.ligerHideTip();
	            lable.remove();
	        },
	        submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	        }
	    });
    }       
   
    function saveBudgPaymentItemCostShip(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
		autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true,'',200);
        
        budg_year = liger.get("budg_year").getValue();
        
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
       	
        autocomplete("#fund_nature","../../../../sys/querySysSourceNature.do?isCheck=false","id","text",true,true,'',false,'',200); 
        
        autocomplete("#payment_item_id","../../../queryBudgPaymentItem.do?isCheck=false&is_last=1","id","text",true,true,'',false,'',200); 
        //字典下拉框
        $("#budg_year").ligerTextBox({width:200});
        $("#payment_item_id").ligerTextBox({width:200});
        $("#fund_nature").ligerTextBox({width:200});
        $("#subj_code").ligerTextBox({width:200});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>支出项目<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_id" type="text" id="payment_item_id" ltype="text"  validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>资金性质<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="fund_nature" type="text" id="fund_nature" ltype="text"   validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>支出预算科目<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text"  validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
