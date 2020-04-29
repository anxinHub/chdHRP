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
        
        $("#proj_name").bind("blur",function(){
        	$("#proj_simple").val($("#proj_name").val());
        })
     });  
     
     function  save(){
        var formPara={
       		proj_id:'',
       		proj_code:$("#proj_code").val(),
            proj_name:$("#proj_name").val(),
            type_code:liger.get("proj_type").getValue(),
            proj_simple:$("#proj_simple").val(),
            sort_code:$("#sort_code").val(),
            is_stop:$("#is_stop").val(),
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
            note:$("#note").val()
        };
        
        // 下拉框手动输入验证
        if(formPara.type_code == '' && $("#proj_type").val() != ''){
        	return $.ligerDialog.warn("请选择正确的【项目类型】！");
        }
        if(formPara.level_code == '' && $("#level_code").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【项目级别】！");
        }
        if(formPara.con_emp_id == '0' && $("#con_emp_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【项目负责人】！");
        }
        if(formPara.acc_emp_id == '0' && $("#acc_emp_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【财务负责人】！");
        }
        if(formPara.app_emp_id == '0' && $("#app_emp_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【填报人】！");
        }
        if(formPara.dept_id == '0' && $("#dept_id").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【填报部门】！");
        }
        if(formPara.use_code == '' && $("#use_code").val() != ''){
	        return $.ligerDialog.warn("请选择正确的【项目用途】！");
        }
        
        ajaxJsonObjectByUrl("addProj.do?isCheck=false",formPara,function(responseData){
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
   
    function saveProj(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#proj_type","../queryProjTypeDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#con_emp_id","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#acc_emp_id","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#app_emp_id","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#level_code","../../sys/queryProjLevelDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#use_code","../../sys/queryProjUseDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_id","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
    	$("#sort_code").ligerTextBox({width:180,disabled:true});
    	$("#is_stop").ligerTextBox({});
    	$("#proj_code").ligerTextBox({});
    	$("#type_code").ligerTextBox({});
    	$("#proj_name").ligerTextBox({});
    	$("#proj_simple").ligerTextBox({});
    	if('${is_stop}' == 0){
    		$("#is_stop").val("否");
    	}else{
    		$("#is_stop").val("是");
    	}
    	
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
    	liger.get("dept_id").setText("${dept_code} ${dept_name}");
    	
    	$("#note").val('${note}');
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目类型<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_type" type="text" id="proj_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_name" type="text" id="proj_name" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left" width="150"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                <select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                				</select>
                </td>
                
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目简称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_simple" type="text" id="proj_simple" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left" width="150"></td>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
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
                <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:false,maxlength:20}" /></td>
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目级别：</td>
                <td align="left" class="l-table-edit-td"><input name="level_code" type="text"   id="level_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
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
