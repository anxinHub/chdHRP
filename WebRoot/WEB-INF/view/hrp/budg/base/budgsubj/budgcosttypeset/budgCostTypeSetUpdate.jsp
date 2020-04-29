<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    
    var budg_year = "${budg_year}" ;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        		type_codeOld :'${type_code}',
        		
        		subj_codeOld :'${subj_code}',
        		
        		budg_year : '${budg_year}',
        		
        		type_code:liger.get("type_code").getValue(),
						
        		subj_code:liger.get("subj_code").getValue(),
        		
		        };
        ajaxJsonObjectByUrl("updateBudgCostTypeSet.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
    function loadForm(){
    
    $.metadata.setType("attr","validate");
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
   
    function updateBudgAccSubjNature(){
    	if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'05'+"&budg_year="+budg_year,"id","text",true,true);
        liger.get("subj_code").setValue('${subj_code}');
        liger.get("subj_code").setText('${subj_code} ${subj_name}');
        autocomplete("#type_code","../../../queryBudgCostSubjType.do?isCheck=false","id","text",true,true);
        liger.get("type_code").setValue('${type_code}');
        liger.get("type_code").setText('${type_code} ${type_name}');
        
        $("#type_code").ligerTextBox({width:160});
        $("#subj_code").ligerTextBox({width:160});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        	<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input class="Wdate" name="budg_year" type="text" id="budg_year" disabled="disabled" value="${budg_year}"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
           </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目类别名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
           </tr> 
		   <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>收入预算科目名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
