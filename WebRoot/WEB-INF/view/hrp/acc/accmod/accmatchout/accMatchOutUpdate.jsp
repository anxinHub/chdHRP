<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
        		out_id: $("#out_id").val(),
        		proj_id:liger.get("proj_id").getValue().split(".")[0],
        		proj_no:liger.get("proj_id").getValue().split(".")[1],
        		occur_date:$("#occur_date").val(),
        		business_no:$("#business_no").val(),
        		vouch_no:$("#vouch_no").val(),
        		summary:$("#summary").val(),
        		subj_code:liger.get("subj_code").getValue().split(".")[0],
        		debit:$("#debit").val()
         };
        ajaxJsonObjectByUrl("updateaccMatchOut.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveAccPayType(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	//autocomplete("#proj_id","../../../sys/queryProjDictDict.do?isCheck=false","id","text",true,true,'',false,'${proj_id}.${proj_no}',onChange: projChange()); 
    	$("#proj_id").ligerComboBox({
    	      	url: "../../../sys/queryProjDictDict.do?isCheck=false",
    	      	valueField: 'id',
    	       	textField: 'text', 
    	       	selectBoxWidth: 200,
    	      	autocomplete: true,
    	      	width: 200,
    	      	keySupport:true,
    	      	onSuccess: function(){
    	      		$.post("../../accmatchinit/queryAccProjAttrByProj.do?isCheck=false",{"proj_id":liger.get("proj_id").getValue().split(".")[0]},function(data){
    	        		$("#con_emp_id").val(data.con_emp_name);
    	        		$("#dept_id").val(data.dept_name);
    	        	},"json");
    	      	}
    	});
    	liger.get("proj_id").setValue("${proj_id}.${proj_no}");
        liger.get("proj_id").setText("${proj_code} ${proj_name}");
    	//autocomplete("#subj_code","../../querySubj.do?isCheck=false","id","text",true,true,'',false,'${subj_code}','','','300px');  
    	$("#subj_code").ligerComboBox({
    		parms: {is_last:1},
            url: '../../querySubj.do?isCheck=false',
            valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 300,
            selectBoxHeight: 240,
            autocomplete: true,
            initValue : 0,
            width: 300
      	});
    	
    	liger.get("subj_code").setValue("${subj_code}");
        liger.get("subj_code").setText("${subj_code} ${subj_name}");
    	
    	$("#proj_id").ligerTextBox({ disabled: true });
		$("#con_emp_id").ligerTextBox({disabled:true });
    	$("#dept_id").ligerTextBox({disabled:true }); 
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
        <table cellpadding="0" cellspacing="0" align="center" class="l-table-edit" >
                 <tr>
                <td style="display: none" align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td style="display: none" align="left" class="l-table-edit-td">
                	<input style="display: none" name="out_id" type="text" id="out_id"  ltype="text" value="${out_id}"  validate="{required:true,maxlength:20}" />
                </td>
                <td style="display: none" align="left"></td>
            </tr> 
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发生日期：</td>
                <td align="left" class="l-table-edit-td">
                	<input class="Wdate" name="occur_date" type="text" id="occur_date" ltype="text" onFocus="year()"  value="${occur_date }" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
                <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id"  value="${emp_name }" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请科室：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:true,maxlength:50}" disabled="disabled"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证号：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="vouch_no" type="text" id="vouch_no"  ltype="text" value="${vouch_no}"  validate="{required:false,maxlength:200}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销事由：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="summary" type="text" id="summary" value="${summary}" ltype="text" validate="{required:true,maxlength:50}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销单号：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="business_no" type="text" id="business_no" ltype="text" value="${business_no}"  validate="{required:false,maxlength:200}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" value="${subj_code}" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出金额：</td>
                <td align="left" class="l-table-edit-td"><input name="debit" type="text" id="debit" ltype="text"  value="${debit}"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
