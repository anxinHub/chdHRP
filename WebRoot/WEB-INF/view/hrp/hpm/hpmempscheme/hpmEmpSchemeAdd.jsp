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
    	 
        loadDict();
        
        loadForm();
        
     });  
     
     function  save(){
    	 
        var formPara={
           fun_code : '',
		
           dept_id:liger.get("dept_code").getValue().split(",")[0],
           dept_no:liger.get("dept_code").getValue().split(",")[1],
            
           duty_code:liger.get("duty_code").getValue(),
           item_code:liger.get("item_code").getValue(),
           
           
           method_code:liger.get("method_code").getValue(),
            
           formula_code:liger.get("formula_code").getValue()
            
         };
		ajaxJsonObjectByUrl("addHpmEmpScheme.do",formPara,function(responseData){
            
            if(responseData.state=="true"){

            	parentFrameUse().query();
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
    // $("form").ligerForm();
 }       
   
    function saveEmpScheme(){
        if($("form").valid()){
            save();
        }
	}
    function loadDict(){
    	
		$("#formula_method_chs").ligerTextBox({ disabled: true,width:515});
    	
    	$("#formula_name").ligerTextBox({ disabled: true,width:160 });
    	
    	$("#formula_code").ligerTextBox({disabled: true,width:160 });
    	
    	//-------------------------------------------------------------------------------------------
    	autocomplete("#method_code","../queryTargetMethodPara.do?isCheck=false","id","text",true,true);
    	
    	liger.get("method_code").setValue("02");
		
		liger.get("method_code").setText("计算公式");

    	$("#method_code").ligerTextBox({disabled:true });
    	//-------------------------------------------------------------------------------------------
    	
		autocomplete("#dept_code","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
            
		autocomplete("#duty_code","../queryEmpDutyDict.do?isCheck=false","id","text",true,true);
		
		autocomplete("#item_code","../queryAphiEmpItem.do?isCheck=false","id","text",true,true);
    	
	} 
	function openFormula(){
	    $.ligerDialog.open({
			url: '../hpmformula/hpmFormulaPage.do?isCheck=false', 
			/* height: 430,
			width: 930, */
			showMax:true,
			title:'计算公式'
		});
    }
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">科室名称：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="dept_code" type="text" id="dept_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">职务名称：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="duty_code" type="text" id="duty_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">项目名称：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="item_code" type="text" id="item_code" ltype="text"/></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">取值方法：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="method_code" type="text" id="method_code" ltype="text"
					validate="{required:true}" /></td>
				<td align="left"></td>

				<td align="left" style="padding-left: 100px; padding-top: 20px;"><font style="color: red"><-选择取值方法</font></td>

				<td align="left" style="padding-left: 10px; padding-top: 20px;"><input class="liger-button" id="formulaButton" name="formulaButton"
					onClick="openFormula()" value="选择公式" /></td>
				<td align="left"></td>


			</tr>

			<tr>

				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">公式代码：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="formula_code" type="text" id="formula_code" ltype="text" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">公式名称：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;"><input name="formula_name" type="text" id="formula_name" ltype="text" /></td>
				<td align="left"></td>

			</tr>

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">取值公式：</td>
				<td align="left" class="l-table-edit-td" colspan="4" style="padding-top: 20px;"><input name="formula_method_chs" type="text" ltype="text"
					id="formula_method_chs" /></td>
				<td align="left"></td>
			</tr>


		</table>
	</form>

</body>
</html>
