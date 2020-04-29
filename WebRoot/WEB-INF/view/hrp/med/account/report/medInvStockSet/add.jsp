<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
     
     $(function (){
    	 
        loadDict();
        loadForm();
     });  
     
     function  save(){
 		if($("form").valid()){
 			var formPara={
 					show_id : $("#show_id").val(),
 	 				show_name : $("#show_name").val(),
 	 				show_flag : liger.get("show_flag").getValue(),
 	 				direction_flag : liger.get("direction_flag").getValue()
 			};
 			ajaxJsonObjectByUrl("addMedShowSet.do", formPara, function(responseData){
 				if(responseData.state=="true"){
 					$("#show_id").val("");
 					$("#show_name").val("");
 					parent.query();
 	            }
 			});
 		}
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
	    //$("form").ligerForm();
	 }       
   
    function loadDict(){
		//格式化文本框
        $("#show_id").ligerTextBox({width: 160, number: true, precision: 0});
        $("#show_name").ligerTextBox({width:160});
    	//字典下拉框
    	autoCompleteByData("#show_flag", yes_or_no.Rows, "id", "text", true, true, "", false, "1");
    	autoCompleteByData("#direction_flag", medShowSet_directionFlag.Rows, "id", "text", true, true, "", true);
        //格式化按钮
		$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
	function this_close(){
		frameElement.dialog.close();
	}
	
    </script>

  </head>
  
	<body>
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<div>
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
					<tr>
			            <td align="right" class="l-table-edit-td" width="30%">
			            	<span style="color:red">*</span>编码：
			            </td>
			            <td align="left" class="l-table-edit-td" width="50%">
			            	<input name="show_id" type="text" id="show_id" ltype="text" required="true" validate="{required:true}" />
			            </td>
					</tr>
					<tr>
			            <td align="right" class="l-table-edit-td" width="30%">
			            	
			            </td>
			            <td align="left" class="l-table-edit-td" width="50%">
			            	<span style="color:red">注：编码跟显示顺序有关</span>
			            </td>
					</tr>
			        <tr>
			            <td align="right" class="l-table-edit-td" width="30%">
			            	<span style="color:red">*</span>显示名称：
			            </td>
			            <td align="left" class="l-table-edit-td" width="50%">
			            	<input name="show_name" type="text" id="show_name" ltype="text" required="true" validate="{required:true}" />
			            </td>
					</tr>
					<tr>
			            <td align="right" class="l-table-edit-td" >
			            	<span style="color:red">*</span>是否显示：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="show_flag" type="text" id="show_flag" ltype="text" required="true" validate="{required:true}" />
			            </td>
					</tr>
					<tr>
			            <td align="right" class="l-table-edit-td"  >
			            	<span style="color:red">*</span>方向：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="direction_flag" type="text" id="direction_flag" ltype="text" required="true" validate="{required:true}" />
			            </td>
			        </tr> 
			        <tr>	<td colspan="2">&nbsp;&nbsp;</td></tr>
			        <tr>	<td colspan="2">&nbsp;&nbsp;</td></tr>
			        <tr>	<td colspan="2">&nbsp;&nbsp;</td></tr>
			        <tr>	
						<td align="center" class="l-table-edit-td" colspan="2">
							<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
							&nbsp;&nbsp;
							<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
