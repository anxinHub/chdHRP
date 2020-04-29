<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var typeId=dialog!=null?dialog.get("data").typeId:"";

	$(function() {
		
		loadHotkeys();
		
    	autodate("#date_begin","YYYY-mm-dd","month_first");
		autodate("#date_end","YYYY-mm-dd","month_last");
	});
	
	

	//键盘事件
	function loadHotkeys() {
		
    	
		//格式化按钮
		$("#save").ligerButton({
			click : save,
			width : 90
		});
		$("#close").ligerButton({
			click : this_close,
			width : 90
		}); 
		
		hotkeys('C', this_close);
		
	}
	


	function save() {
	  	
    	if($("#date_begin").val() == null || $("#date_begin").val() == ""){
			$.ligerDialog.error('请选择起始日期!');
			return;
		}
		
		if($("#date_end").val() == null || $("#date_end").val() == ""){
			$.ligerDialog.error('请选择结束日期!');
			return;
		}
		
		$.ligerDialog.confirm('确定同步?', function(yes) {
			if (yes) {
				var para={
					type_id: typeId.toString(),
	        		begin_date: $("#date_begin").val(),
	        		end_date: $("#date_end").val()
		        };
		        	
				var loadIndex = layer.load(1);
				ajaxJsonObjectBylayer("syncData.do?isCheck=false",para,function (responseData){
					
				},layer,loadIndex);
			}
		});
		
	}

	
	function this_close() {
		frameElement.dialog.close();
	}

	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 100%; height: 100%;">
			
		<table align="center" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
		 	<tr>
	        <td align="right" class="l-table-edit-td" style="padding-left: 20px;">日期：</td>
			<td align="left" ><input name="date_begin" type="text" id="date_begin" class="Wdate" style="width: 100px;"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" >&nbsp;至：</td>
			<td align="left"><input name="date_end" type="text" id="date_end"  class="Wdate" style="width: 100px;" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
	        </tr>
	       
		</table>
		
		<br/><br/><br/><br/>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
			<tr>
				<td align="center" class="l-table-edit-td">
					
					<button id="save" accessKey="B">
						<b>确定（<u>B</u>）
						</b>
					</button> &nbsp;&nbsp;
					<button id="close" accessKey="C">
						<b>取消（<u>C</u>）
						</b>
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
