<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path %>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 <%-- //项目负责人电话
     	 /*  var reg=/^[1][3578][0-9]{9}$/;
           if($("#con_phone").val()!=null && ($("#con_phone").val()!="")){
     	 
         	if(reg.test($("#con_phone").val())==false){
     		  $.ligerDialog.error("项目负责人电话输入不合法!");
  				
     		  return;
     	  }
      } 
           
           //财务负责人电话
           var re1 = /^[1][3578][0-9]{9}$/; 
           if($("#acc_phone").val()!=null && ($("#acc_phone").val()!="")){
             	 
           	if(re1.test($("#acc_phone").val())==false){
       		  $.ligerDialog.error("财务负责人电话输入不合法!");
    				
       		  return;
       	  }
        }
         //邮箱
         var re2 = /^\d{5,12}@[qQ][qQ]\.com$/;
         if($("#email").val()!=null && ($("#email").val()!="")){
        	 
         	if(re2.test($("#email").val())==false){
     		  $.ligerDialog.error("邮箱输入不合法!");
  				
     		  return;
     	  }
      }
        //联系电话
     	  var re3=/^[1][3578][0-9]{9}$/;
         if($("#app_phone").val()!=null && ($("#app_phone").val()!="")){
   	 
       	if(re3.test($("#app_phone").val())==false){
   		  $.ligerDialog.error("联系电话输入不合法!");
				
   		  return;
   	  }
    }
        */
    	 --%>
        var formPara={
       		proj_id:'${proj_id}',
       		group_id:'${group_id}',
       		hos_id:'${hos_id}',
            level_code:liger.get("level_code").getValue(),
            use_code:liger.get("use_code").getValue(),
            con_emp_id:liger.get("con_emp_id").getValue().split(".")[0],
            con_phone:$("#con_phone").val(),
            acc_emp_id:liger.get("acc_emp_id").getValue().split(".")[0],
            acc_phone:$("#acc_phone").val(),
            app_emp_id:liger.get("app_emp_id").getValue().split(".")[0],
            dept_id:liger.get("dept_id").getValue().split(".")[0],
            app_date:$("#app_date").val(),
            app_phone:$("#app_phone").val(),
            email:$("#email").val(),
            note:$("#note").val(),
            proj_name:$("#proj_name").val(),
            proj_simple:$("#proj_simple").val(),
            type_code:liger.get("type_code").getValue(),
            is_disable:$("#is_disable").val(),
            sort_code: "系统生成",
            proj_code:liger.get("proj_code").getValue()
        };
        
     	// 下拉框手动输入验证
        if((formPara.type_code == '' || formPara.type_code == '0') && $("#type_code").val() != ''){
        	return $.ligerDialog.warn("请选择正确的【项目类型】！");
        }
        if(formPara.level_code == '' && $("#level_code").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【项目级别】！");
        }
        if((formPara.con_emp_id == '0' || formPara.con_emp_id == '') && $("#con_emp_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【项目负责人】！");
        }
        if((formPara.acc_emp_id == '0' || formPara.acc_emp_id == '') && $("#acc_emp_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【财务负责人】！");
        }
        if((formPara.app_emp_id == '0' || formPara.app_emp_id == '') && $("#app_emp_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【填报人】！");
        }
        if((formPara.dept_id == '0' || formPara.dept_id == '') && $("#dept_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【填报部门】！");
        }
        if(formPara.use_code == '' && $("#use_code").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【项目用途】！");
        }
        
        ajaxJsonObjectByUrl("../accprojattr/updateAccProjAttr.do",formPara,function(responseData){
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
     $("form").ligerForm();
 }       
   
    function saveAccProjAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_code","<%=path %>/hrp/sys/queryProjTypeDict.do?isCheck=false","id","text",true,true,'',false,false,false);
    	autocomplete("#con_emp_id","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#acc_emp_id","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#app_emp_id","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#level_code","../../sys/queryProjLevelDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#use_code","../../sys/queryProjUseDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_id","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
    	//$("#is_disable").ligerTextBox({ disabled: true });
    	$("#proj_code").ligerTextBox({ disabled: true });
    	//$("#type_code").ligerTextBox({ disabled: true });
    	//$("#proj_name").ligerTextBox({ disabled: true });
    	//$("#proj_simple").ligerTextBox({ disabled: true });
    	
    	liger.get("con_emp_id").setValue("${con_emp_id}");
    	liger.get("con_emp_id").setText("${con_emp_name}");
    	
    	liger.get("acc_emp_id").setValue("${acc_emp_id}");
    	liger.get("acc_emp_id").setText("${acc_emp_name}");
    	
    	liger.get("app_emp_id").setValue("${app_emp_id}");
    	liger.get("app_emp_id").setText("${app_emp_name}");
    	
    	liger.get("level_code").setValue("${level_code}");
    	liger.get("level_code").setText("${level_name}");
    	
    	liger.get("use_code").setValue("${use_code}");
    	liger.get("use_code").setText("${use_name}");
    	
    	liger.get("dept_id").setValue("${dept_id}.${dept_no}");
    	liger.get("dept_id").setText("${dept_code} ${dept_name}".trim());
    	
    	liger.get("type_code").setValue('${type_code}')
    	liger.get("type_code").setText('${type_name}')
    	
    	$("#is_disable").val("${is_disable}");
    	$("#note").val('${note}');
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_code" type="text" value="${proj_code }" disabled="disabled" id="proj_code" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目类型：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_name" type="text" value="${proj_name }" id="proj_name" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                <select id="is_disable" name="is_disable">
                		<option value="1">是</option>
                		<option value="0">否</option>
                </select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目简称：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_simple" type="text" value="${proj_simple }"  id="proj_simple" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目级别：</td>
                <td align="left" class="l-table-edit-td"><input name="level_code" type="text"   id="level_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
                <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text"  id="con_emp_id" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人电话：</td>
                <td align="left" class="l-table-edit-td"><input name="con_phone" type="text" value="${con_phone }"  id="con_phone" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务负责人：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_emp_id" type="text" id="acc_emp_id" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务负责人电话：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_phone" type="text" value="${acc_phone }" id="acc_phone" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">填报人：</td>
                <td align="left" class="l-table-edit-td"><input name="app_emp_id" type="text" id="app_emp_id" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">填报部门：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:false}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">填报日期：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate" name="app_date" value="${app_date }" type="text" id="app_date" ltype="text" validate="{required:false,maxlength:20}" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" /></td>
                <td align="left" width="150"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系电话：</td>
                <td align="left" class="l-table-edit-td"><input name="app_phone" value="${app_phone }" type="text" id="app_phone" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目用途：</td>
                <td align="left" class="l-table-edit-td"><input name="use_code" type="text" id="use_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">EMAIL：</td>
                <td align="left" class="l-table-edit-td"><input name="email" value="${email }" type="text" id="email" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><textarea rows="3" cols="60" name="note" id="note" validate="{required:false,maxlength:50}" style="resize: none;" ></textarea></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
