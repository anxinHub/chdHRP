<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

    var dataFormat;
    
    $(function (){
    	
		autocomplete("#dept_kind_code","../../queryDeptKindDict.do?isCheck=false","id","text",true,true);
     	
 		autocomplete("#item_code","../../queryItemAllDict.do?isCheck=false","id","text",true,true);
     	
     	autocomplete("#formula_code","../../queryFormula.do?isCheck=false","id","text",true,true);

     	autocomplete("#method_code","../../queryTargetMethodPara.do?isCheck=false","id","text",true,true);
     	
      	autocomplete("#fun_code","../../queryFun.do?isCheck=false","id","text",true,true);

        loadForm();
        
		liger.get("dept_kind_code").setValue('${dept_kind_code}');
    	
    	liger.get("dept_kind_code").setText('${dept_kind_name}');
        
		liger.get("item_code").setValue('${item_code}');
    	
    	liger.get("item_code").setText('${item_name}');
    	
		liger.get("formula_code").setValue('${formula_code}');
    	
    	liger.get("formula_code").setText('${formula_name}');
    	
		liger.get("method_code").setValue('${method_code}');
    	
    	liger.get("method_code").setText('${method_name}');
    	
		liger.get("fun_code").setValue('${fun_code}');
    	
    	liger.get("fun_code").setText('${fun_name}');
    	
    	$("#item_code").ligerTextBox({ disabled: true });
    	
    	$("#dept_kind_code").ligerTextBox({ disabled: true });
    });  
     
    function save(){
        var formPara={
        		
        		dept_kind_code:liger.get("dept_kind_code").getValue(),
                
                item_code:liger.get("item_code").getValue(),
                 
                method_code:liger.get("method_code").getValue(),
                 
                formula_code:liger.get("formula_code").getValue(),
                 
                fun_code:liger.get("fun_code").getValue()
        
        };
        ajaxJsonObjectByUrl("updateHpmDeptKindScheme.do",formPara,function(responseData){
            
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
   
    function saveDeptKindScheme(){
        if($("form").valid()){
            save();
        }
    }  
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" value="${dept_kind_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目编码：</td>
				<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" value="${item_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">取值方法：</td>
				<td align="left" class="l-table-edit-td"><input name="method_code" type="text" id="method_code" ltype="text" value="${method_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">取值公式：</td>
				<td align="left" class="l-table-edit-td"><input name="formula_code" type="text" id="formula_code" ltype="text" value="${formula_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">取值函数：</td>
				<td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text" value="${fun_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
