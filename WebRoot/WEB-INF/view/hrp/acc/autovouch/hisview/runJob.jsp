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


	$(function() {
		
		loadHotkeys();
		
		
	});
	
	

	//键盘事件
	function loadHotkeys() {
		
		autocomplete("#his_log_code", "../../queryAccHisLog.do?isCheck=false","id", "text", true, true,"",false,false,"200","180");
    	
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
		
		$("#etl_path").ligerTextBox({width : 350});
		$("#job_path").ligerTextBox({width : 350});
	}
	


	function save() {
		
		if($("#etl_path").val()==""){
			$.ligerDialog.error("ETL目录不能为空");
			return;
		}
		
		if($("#job_path").val()==""){
			$.ligerDialog.error("JOB目录不能为空");
			return;
		}
		
		$.ligerDialog.confirm('确定执行?', function(yes) {
			if (yes) {
				
				var para={
	        		etl_path:$("#etl_path").val(),
	        		job_path:$("#job_path").val(),
	        		view_code:liger.get("his_log_code").getValue()
		        };
		        	
				var loadIndex = layer.load(1);
				ajaxJsonObjectBylayer("runJob.do?isCheck=false",para,function (responseData){
					
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
		<div style="font-size:14px">&nbsp;&nbsp;说明：1)、此页面的操作需要服务器部署ETL，请参考ETL使用手册。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2)、路径：指ETL在服务器的路径。<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3)、执行时间比较长，请耐心等待。<br/>
		</div>
		<br/>
	
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="font-size:15px">
		 	<tr>
	        <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b><font color="red"></font></b>视图：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="his_log_code" name="his_log_code" />
            </td>
            </tr>
			<tr>
			<td align="right" class="l-table-edit-td" style="margin-left: 22;">ETL根目录：</td>
			<td align="left" class="l-table-edit-td" >
				<input type="text" id="etl_path" ltype="text" value="${etlPath}"/>
	        </td>
	        </tr>
	        <tr>
			<td align="right" class="l-table-edit-td"  style="margin-left: 22;">JOB文件路径：</td>
			<td align="left" class="l-table-edit-td" >
				<input type="text" id="job_path" ltype="text" value="${jobPath}"/>
	        </td>
	        </tr>
	       
		</table>
		
		<br/><br/><br/><br/>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
			<tr>
				<td align="center" class="l-table-edit-td">
					
					<button id="save" accessKey="B">
						<b>执行（<u>B</u>）
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
