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
    	
    	
        
        
		autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true,'',false,'${dept_id},${dept_no}');
     	
     	autocomplete("#work_item_code","../queryWorkItem.do?isCheck=false","id","text",true,true,'',false,'${work_item_code}');	

     	$("#dept_id").ligerTextBox({ disabled: true });
     	$("#work_item_code").ligerTextBox({ disabled: true });
     	$("#is_acc").ligerComboBox();
        if('${is_acc}'=='1'){
     		liger.get("is_acc").setValue(1);
     		liger.get("is_acc").setText("是");	
     		
     	}else{
     		liger.get("is_acc").setValue(0);
     		liger.get("is_acc").setText("否");	
     	}
     	
     	loadForm();
        
    });  
    
    function save(){
        var formPara={
        		
        		dept_id:liger.get("dept_id").getValue().split(",")[0],
        		dept_no:liger.get("dept_id").getValue().split(",")[1],
        
        work_item_code:liger.get("work_item_code").getValue(),
        
        is_acc:$("#is_acc").val(),
        
        work_standard:$("#work_standard").val()
        
        };
        ajaxJsonObjectByUrl("updateHpmWorkitemConf.do",formPara,function(responseData){
            
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
   
    function saveWorkitemConf(){
    	
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
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室编码：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标编码：</td>
				<td align="left" class="l-table-edit-td"><input name="work_item_code" type="text" id="work_item_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
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
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计件标准：</td>
				<td align="left" class="l-table-edit-td"><input name="work_standard" type="text" id="work_standard" ltype="text" value="${work_standard}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
