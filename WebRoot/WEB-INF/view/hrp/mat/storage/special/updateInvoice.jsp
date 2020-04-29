<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/map.js"></script>
    <script type="text/javascript">
     
     $(function (){
    	 
         loadDict();
     });  

	//字典下拉框
    function loadDict(){
        $("#bill_no").ligerTextBox({width:200});
        $("#bill_date").ligerTextBox({width:200});
        //格式化按钮
    	$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
	} 
	
	function save(){
    	var para = "group_id=" + $("#group_id").val() + 
			"&hos_id=" + $("#hos_id").val() + 
			"&copy_code=" + $("#copy_code").val() + 
			"&special_id=" + $("#special_id").val() + 
			"&in_id=" + $("#in_id").val() + 
			"&bill_no=" + $("#bill_no").val() + 
			"&bill_date=" + $("#bill_date").val();
		ajaxJsonObjectByUrl("updateMatSpecialInvoice.do?isCheck=false&"+para, "", function (responseData){
			if(responseData.state=="true"){
				parent.updateBill($("#bill_no").val(), $("#bill_date").val());
				frameElement.dialog.close();
			}
		});
	}
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
    </script>
  
  </head>
  
   <body>
	   <div id="pageloading" class="l-loading" style="display: none"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
			
				<td align="right" class="l-table-edit-td">发票号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="bill_no" type="text" id="bill_no"  ltype="text" required="true" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">发票日期：</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="bill_date" id="bill_date" type="text" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd'})"/>
				</td>
			</tr>
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${copy_code}" ltype="text" />
	            	<input name="special_id" type="text" id="special_id" value="${special_id}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${in_id}" ltype="text" />
	            </td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="2">
					<button id ="save" accessKey="B"><b>更新(<u>S</u>)</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭(<u>C</u>)</b></button>
				</td>
			</tr>
		</table>
    </body>
</html>
        