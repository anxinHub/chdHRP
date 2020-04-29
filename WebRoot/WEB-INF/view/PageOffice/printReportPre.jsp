<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=path%>/pageoffice.js" id="po_js_main"></script>
<script type="text/javascript">

$(function() {
	$("#pageOfficeDiv").css("height", $(window).height()-10);
	
});


//加载完JS回调函数
function AfterDocumentOpened() {
	delteFile();
	//document.getElementById("PageOfficeCtrl1").FullScreen=true;
	//document.getElementById("PageOfficeCtrl1").Document.Application.ActiveWindow.ActivePane.View.Type = 3;
}

//保存
function ShowSave() {
	//保存模板，直接保存文件
	document.getElementById("PageOfficeCtrl1").WebSave();
    var res=document.getElementById("PageOfficeCtrl1").CustomSaveResult;
	
    if(res=="ok"){
    	alert("保存成功！");	
    }else{
    	alert("保存失败！");
    }	
    
	/* if($("#open_flag").val()=="template"){
		
	   
	}else if(res=="ok"){
		
		//根据数据保存模板格式
		var para={
			'temp_file_name':$("#temp_file_name").val(),
			'tempFile':$("#tempFile").val()
		};
		var loadIndex = layer.load(1);
		$.ajax({
			url: "saveReportTempFile.do?isCheck=false",
				type: "POST",
				data:para,
				dataType: "json",
				success: function (res) {
					if(!res.state){
						alert(res.msg);
						return;
					}
				
					if(res.state){
						alert("保存成功！");	
					}
				},
				error: function (res) {
					alert(res.msg);	
				},
				complete: function () {
					layer.close(loadIndex);
				}
			});
	} */
}

//保存为
function ShowAsSave() {
	document.getElementById("PageOfficeCtrl1").ShowDialog(2);
}

//还原默认
function DelTempFile(){
	if(confirm("确定要还原吗？")){
		var tempFile=$("#tempFile").val();
		ajaxJsonObjectByUrl("deleteFile.do?isCheck=false",{temp_file_name:tempFile},function(){
			alert("还原成功，请重新打开页面！");	
			//location.reload();
		});
	}
	
}

//打印
function ShowPrint() {
	try{
		document.getElementById("PageOfficeCtrl1").PrintOut();//直接打印
	}catch(err){
		document.getElementById("PageOfficeCtrl1").ShowDialog(4);//对话框打印
	}
}

//打印预览
function ShowPrintPre(){
	try{
		 document.getElementById("PageOfficeCtrl1").PrintPreview();
	}catch(err){
		document.getElementById("PageOfficeCtrl1").ShowDialog(4);//对话框打印
	}
}

//页面设置
function ShowPrintSet() {
	document.getElementById("PageOfficeCtrl1").ShowDialog(5);
}

//全屏
function ShowFullScreen() {
	document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
}

//关闭
function ShowClose() {
	if(frameElement!=null){
		//低于谷歌42版本
		frameElement.dialog.close();
	}else{
		//高于谷歌42版本
		//window.external.close();//3.0
		POBrowser.closeWindow();
	}
}

//删除生成的文件
function delteFile(){
	if($("#open_flag").val()=="query"){
		var tempFileName=$("#temp_file_name").val();
		ajaxJsonObjectByUrl("deleteFile.do?isCheck=false",{temp_file_name:tempFileName});
	}
}

//说明
function ShowNote(){
	alert("打印报表说明：\r1、只会套用打印模板的行高、列宽\r2、取页面设置里面的信息，如纸张、边距、页眉页脚等");
}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">

	<div id="ProgressBarSide" style="color: Silver; width: 200px; visibility: visible;position: absolute;  left: 70px; top: 60px; margin-top: -32px">
        <span style="color: black; font-size: 14px; text-align: center;">正在处理数据，请耐心等待...</span>
	</div>

	<input id="temp_file_name" type="text"  value="${temp_file_name}" style="display:none"/>
	<input id="tempFile" type="text"  value="${tempFile}" style="display:none"/>
	<input id="group_id" type="text"  value="${group_id}" style="display:none"/>
	<input id="hos_id" type="text"  value="${hos_id}" style="display:none"/>
	<input id="copy_code" type="text"  value="${hos_copy}" style="display:none"/>
	<input id="open_flag" type="text"  value="${open_flag}" style="display:none"/>
	
	<div id="pageOfficeDiv" style="width:auto;display:none">
		<!-- *********************pageoffice组件的使用 **************************-->
		<po:PageOfficeCtrl id="PageOfficeCtrl1" />
		<!-- *********************pageoffice组件的使用 **************************-->
	</div>
	<script>
	$("#pageOfficeDiv").show();
	</script>

	</div>
</body>
</html>
