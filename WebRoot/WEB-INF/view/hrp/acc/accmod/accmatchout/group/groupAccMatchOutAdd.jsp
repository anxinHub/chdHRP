<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath(); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
        		out_id:0,
        		proj_id:liger.get("proj_id").getValue().split(".")[0],
        		proj_no:liger.get("proj_id").getValue().split(".")[1],
        		occur_date:$("#occur_date").val(),
        		business_no:$("#business_no").val(),
        		vouch_no:$("#vouch_no").val(),
        		summary:$("#summary").val(),
        		subj_id:liger.get("subj_id").getValue().split(".")[0],
        		debit:$("#debit").val()
         };
        
        ajaxJsonObjectByUrl("addGroupAccMatchOut.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='proj_id']").val('');
				 $("input[name='occur_date']").val('');
				 $("input[name='business_no']").val('');
				 $("input[name='vouch_no']").val('');
				 $("input[name='summary']").val('');
				 $("input[name='subj_id']").val('');
				 $("input[name='debit']").val('');
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
   
    function saveAccPayType(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	autocomplete("#proj_id","../../../../sys/queryProjDictDict.do?isCheck=false","id","text",true,true,'',false,'','300');  
   
    	autocomplete("#subj_id","../../../querySubj.do?isCheck=false","id","text",true,true,{is_last:1},false,'','300');  
		$("#con_emp_id").ligerTextBox({disabled:true });
    	$("#dept_id").ligerTextBox({disabled:true });
     } 
    function projChange(){
    	$.post("../../../accmatchinit/queryAccProjAttrByProj.do?isCheck=false",{"proj_id":liger.get("proj_id").getValue().split(".")[0]},function(data){
    		$("#con_emp_id").val(data.con_emp_name);
    		$("#dept_id").val(data.dept_name);
    	},"json");
    }
    function year(){
    	
    	WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'});
    	//var date = $("#reply_date").val();
    	//var str = date.split("-")[0] + date.split("-")[1] + date.split("-")[2];
    	//var hehe = Math.floor(Math.random()*9)+1;
    	//$("#business_no").val(str+"00"+hehe);
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" style="margin-left:100px;margin-top:30px" class="l-table-edit" >
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发生日期：</td>
                <td align="left" class="l-table-edit-td">
                	<input class="Wdate" name="occur_date" type="text" id="occur_date" ltype="text" onFocus="year()"  validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:100}" onchange="projChange()"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
                <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请科室：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:50}" disabled="disabled"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭&nbsp;证&nbsp;号：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="vouch_no" type="text" id="vouch_no"  ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销事由：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:100}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销单号：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="business_no" type="text" id="business_no" ltype="text" validate="{required:true,maxlength:50}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_id" type="text" id="subj_id" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出金额：</td>
                <td align="left" class="l-table-edit-td"><input name="debit" type="text" id="debit" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
