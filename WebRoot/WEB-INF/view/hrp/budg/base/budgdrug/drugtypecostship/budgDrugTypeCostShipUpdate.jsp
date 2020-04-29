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
    var budg_year = "${budg_year}";
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
       		budg_year : liger.get("budg_year").getValue(),
   			med_type_id : liger.get("med_type_id").getValue().split(",")[0],
   			cost_subj_code : liger.get("cost_subj_code").getValue(),
   			income_subj_code : liger.get("income_subj_code").getValue(),
   			old_income_subj_code : '${income_subj_code}'
        };
        console.log(formPara)
        ajaxJsonObjectByUrl("updateBudgDrugTypeCostShip.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            	parent.$.ligerDialog.close();
            	parent.$(".l-dialog,.l-window-mask").remove();
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
   
    function saveBudgDrugTypeCostShip(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
         autocompleteAsync("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',false,'${budg_year}', 180);
        
         budg_year = liger.get("budg_year").getValue();
         
         autocomplete("#med_type_id","queryMedTypes.do?isCheck=false","id","text",true,true,'',false,'${med_type_id},${med_type_no}',180);
         
   	     autocomplete("#cost_subj_code","queryBudgSubj.do?isCheck=false&subj_code=${cost_subj_code}&subj_type=05&is_last=1&budg_year=${budg_year}","id","text",true,true,'',false,'${cost_subj_code}',180,150);
   	     autocomplete("#income_subj_code","queryBudgSubj.do?isCheck=false&subj_code=${income_subj_code}&subj_type=04&is_last=1&budg_year=${budg_year}","id","text",true,true,'',false,'${income_subj_code}',180,150);
   	  
   	  	$("#budg_year").ligerTextBox({
			width : 180,
			disabled : true,
			cancelable : false
		});
		$("#med_type_id").ligerTextBox({
			width : 180,
			disabled : true,
			cancelable : false
		});
     	 
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" disabled="disabled"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
           
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品分类：</td>
            <td align="left" class="l-table-edit-td"><input name="med_type_id" type="text" id="med_type_id" ltype="text"   validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
        </tr> 
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算支出科目：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_subj_code" type="text" id="cost_subj_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算收入科目：</td>
            <td align="left" class="l-table-edit-td"><input name="income_subj_code" type="text" id="income_subj_code" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
