<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	
	 
     $(function (){
    	
    	$("#acct_yearm").ligerTextBox({width : 160});
    	
    	$("#bonus_money").ligerTextBox({width:160});

    	loadDict();
     	
     });  
     
     function  save(){
    	 
    	 if ($("#acct_yearm").val() == "") {
 			$.ligerDialog.error('核算日期不能为空');
 			return;
 		}
    	 
    	 if ($("#bonus_money").val() == "") {
  			$.ligerDialog.error('奖金额不能为空');
  			return;
  		}
    	 
    	 if ($("#item_code").val() == "") {
  			$.ligerDialog.error('项目不能为空');
  			return;
  		}
     	var formPara={
     			
     			item_code : liger.get("item_code").getValue(),
     			
     			acct_yearm : $("#acct_yearm").val(),
     	
     			dept_id : liger.get("dept_id").getValue().split(",")[0],
     			
     			dept_no : liger.get("dept_id").getValue().split(",")[1],
     	
     			bonus_money : $("#bonus_money").val(),
     			
     			note : $("#note").val()
     			
     	};
     	
     	ajaxJsonObjectByUrl("saveDeptBonusauditAdd.jsp.do?isCheck=false",formPara,function(responseData){
            
     		if(responseData.state=="true"){
     			

                parent.query();
                
            }
     		
     	});
    		
	}
     
     //下拉框  
	 function loadDict(){
		 var para = {
		   			acct_yearm:$("#acct_yearm").val()
				}
	 	
		 autocomplete("#dept_id","../queryDeptDictByPerm.do?&isCheck=false","id","text",true,true,para);
		 
		 liger.get("dept_id").setValue("${dept_id},${dept_no}");	
		 liger.get("dept_id").setText("${dept_code} ${dept_name}");	
			 
		 autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true);  
		 
		 autocomplete("#item_code","../queryItemAllDict.do?&isCheck=false","id","text",true,true); 
		 
		 liger.get("item_code").setValue("${item_code}");	
		 liger.get("item_code").setText("${item_name}");
	
		$("#dept_id").ligerTextBox({disabled : true});
		
		$("#item_code").ligerTextBox({disabled : true});
	
		 $("#note").ligerTextBox({width:160 });
     }       
   

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>核算年月：</td>
	            <td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text"   id="acct_yearm" value="${acct_yearm}"  ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
	            <td align="left"></td>

			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
	            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" value="${dept_id}"  ltype="text"  validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>项目名称：</td>
           		<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code"   ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>奖金额：</td>
				<td align="left" class="l-table-edit-td"><input name="bonus_money" type="text" id="bonus_money"   ltype="text"  value="${bonus_money}"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">备注：</td>
				<td align="left" class="l-table-edit-td"><input name="note" type="text" id="note"  ltype="text"  value="${note}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
