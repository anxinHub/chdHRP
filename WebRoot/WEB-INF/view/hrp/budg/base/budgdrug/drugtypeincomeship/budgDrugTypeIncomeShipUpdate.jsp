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
        		 budg_year:liger.get("budg_year").getValue(),
        	     drug_type_id:liger.get("drug_type_id").getValue().split(",")[0],
        	     drug_type_no:liger.get("drug_type_id").getValue().split(",")[1],
        	     subj_code:liger.get("subj_code").getValue()
        };
        ajaxJsonObjectByUrl("updateBudgDrugTypeIncomeShip.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveBudgDrugTypeIncomeShip(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
		autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true);
        
		liger.get("budg_year").setValue('${budg_year}');
		liger.get("budg_year").setText('${budg_year}');
        
        budg_year = liger.get("budg_year").getValue();
       	
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true,'',false,'${subj_code}');
        
        autocomplete("#drug_type_id","../../../queryBudgDrugType.do?isCheck=false","id","text",true,true,'',false,'${drug_type_id},${drug_type_no}');
        
        $("#budg_year").ligerTextBox({disabled:true,cancelable: false});
        
        $("#subj_code").ligerTextBox({disabled:true,cancelable: false});
        
    }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入预算科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" value="${subj_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品分类：</td>
            <td align="left" class="l-table-edit-td"><input name="drug_type_id" type="text" id="drug_type_id" ltype="text" value="${drug_type_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
