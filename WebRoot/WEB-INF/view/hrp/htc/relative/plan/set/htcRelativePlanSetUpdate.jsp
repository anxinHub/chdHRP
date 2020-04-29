<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var dataFormat;
    var is_check = '${is_check}'
    $(function (){
        loadDict();
        loadForm();
      
    });  
     
    function save(){
    	
     	if ($("#end_month").val().length >0 && $("#start_month").val().length >0 
   	    	   	&& $("#end_month").val() < $("#start_month").val()){;
   	    	$.ligerDialog.error("起始月不能大于终止月");
   	     	return false;
        }

        var formPara={
        	group_id : '${group_id}',
        	hos_id :    '${hos_id}',
        	copy_code : '${copy_code}',
        	acc_year :  '${acc_year}',
	        plan_code:$("#plan_code").val(),
	        plan_name:$("#plan_name").val(),
	        start_month:$("#start_month").val(),
	        end_month:$("#end_month").val(),
	        method:liger.get("method").getValue(),
        };
        ajaxJsonObjectByUrl("updateHtcRelativePlanSet.do",formPara,function(responseData){
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
   
    function savePlanSet(){
        if($("form").valid()){
            save();
        }
    }
	function loadDict(){
        //字典下拉框
    	autocomplete("#method", "../../../info/base/queryHtcCheckMethod.do?isCheck=false", "id", "text",true,true,'',false,"${menthod}");

		$("#plan_code").ligerTextBox({ disabled: true });

		if(is_check == 1){
			 $("#plan_name").ligerTextBox({disabled: true});
			 $("#start_month").ligerTextBox({disabled: true});
			 $("#end_month").ligerTextBox({disabled: true});
			 $("#method").ligerTextBox({disabled: true});
		}
	}   
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案编码：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text" value="${plan_code}" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案名称：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_name" type="text" id="plan_name" ltype="text" value="${plan_name}" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">起始月：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate" name="start_month" type="text" id="start_month" ltype="text" value="${start_month}"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">终止月：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate" name="end_month" type="text" id="end_month" ltype="text" value="${end_month}"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方法：</td>
				<td align="left" class="l-table-edit-td"><input name="method" type="text" id="method" ltype="text"/ validate="{required:true}" ></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
