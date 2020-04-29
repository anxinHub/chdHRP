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
    var dept_no = '${dept_no}';
    $(function (){
        loadDict();
        loadForm();
        if('${is_acc}'=='true'){
     		liger.get("is_acc").setValue(1);
     		liger.get("is_acc").setText("是");	
     		
     	}
     	
     	if('${is_acc}'=='false'){
     		liger.get("is_acc").setValue(0);
     		liger.get("is_acc").setText("否");	
     		
     	}
     	
     	$("#dept_id").ligerTextBox({ disabled: true });
     	$("#income_item_code").ligerTextBox({ disabled: true });

    });   
     
    function save(){
    	var dept_id = liger.get("dept_id").getValue().split(",")[0];
    	
    	if(dept_id == ''){
    		$.ligerDialog.warn('请填写科室 ');
    		return ;
    	}
    	
    	var income_item_code = liger.get("income_item_code").getValue();
    	if(income_item_code == ''){
    		$.ligerDialog.warn('请填写收费项目 ');
    		return ;
    	}
    	
        var formPara={
        	dept_id:dept_id,
        	dept_no:dept_no,
	        income_item_code:income_item_code,
	        is_acc:liger.get("is_acc").getValue(),
	        order_perc:$("#order_perc").val(),
	        perform_perc:$("#perform_perc").val()
        };
        ajaxJsonObjectByUrl("updateHpmIncomeitemConf"+app_mod_code+".do",formPara,function(responseData){
            
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
        //字典下拉框
    	autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
     	autocomplete("#income_item_code","../../hpm/queryAphiIncomeItem.do?isCheck=false","id","text",true,true);
     	
     	liger.get("dept_id").setValue("${dept_id}");
 		liger.get("dept_id").setText("${dept_name}");	
 		
 		liger.get("income_item_code").setValue("${income_item_code}");
 		liger.get("income_item_code").setText("${income_item_name}");	
     }   
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" disabled="disabled" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收费项目：</td>
				<td align="left" class="l-table-edit-td"><input name="income_item_code" type="text" disabled="disabled" id="income_item_code" ltype="text"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否参与核算：</td>
				<td align="left" class="l-table-edit-td"><select name="is_acc" id="is_acc">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">开单权重：</td>
				<td align="left" class="l-table-edit-td"><input name="order_perc" type="text" id="order_perc" ltype="text" value="${order_perc }"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">执行权重：</td>
				<td align="left" class="l-table-edit-td"><input name="perform_perc" type="text" value="${perform_perc }" id="perform_perc" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
