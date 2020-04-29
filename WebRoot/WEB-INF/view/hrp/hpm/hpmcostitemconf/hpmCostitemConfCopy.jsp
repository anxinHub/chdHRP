<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
     var dataFormat;
     var app_mod_code = '${app_mod_code}';
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
        		dept_id_s:liger.get("dept_id_s").getValue().split(",")[0],
            	dept_no_s:liger.get("dept_id_s").getValue().split(",")[1],
        		dept_id_t:liger.get("dept_id_t").getValue().split(",")[0],
        		dept_no_t:liger.get("dept_id_t").getValue().split(",")[1],
            
         };
        
        ajaxJsonObjectByUrl("hpmCopyHpmCostitemConf"+app_mod_code+".do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveIncomeitemConf(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){

    	autocomplete("#dept_id_s","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
            
     	autocomplete("#dept_id_t","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
     	
     } 
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">选择科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id_s" type="text" id="dept_id_s" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id_t" type="text" id="dept_id_t" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
