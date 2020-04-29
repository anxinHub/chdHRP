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
<script type="text/javascript" src="<%=path%>/pageoffice.js" id="po_js_main"></script>
<script type="text/javascript">

$(function() {
	
});


//加载完JS回调函数
function AfterDocumentOpened() {
	console.log("开始打印");
	document.getElementById("PageOfficeCtrl1").PrintOut();//直接打印
	console.log("准备删除临时文件");
	delteFile();
	//document.getElementById("PageOfficeCtrl1").FullScreen=true;
	//document.getElementById("PageOfficeCtrl1").Document.Application.ActiveWindow.ActivePane.View.Type = 3;
}

//保存
function ShowSave() {
    document.getElementById("PageOfficeCtrl1").WebSave();
    var res=document.getElementById("PageOfficeCtrl1").CustomSaveResult;
    if(res=="ok"){
    	alert("保存成功！");	
    }else{
    	alert("保存失败！");
    }
	    
}

//保存为
function ShowAsSave() {
	document.getElementById("PageOfficeCtrl1").ShowDialog(2);
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
	var tempFileName=$("#temp_file_name").val();
	console.log("删除临时文件："+tempFileName);
	ajaxJsonObjectByUrl("deleteFile.do?isCheck=false",{temp_file_name:tempFileName}, function(){
		console.log(parent);
		if("${isFinish}" && "${isFinish}" == true){
			parent["${printStatePara}"] = true;
		}
		ShowClose();
	}, false);
}



</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	
	<div id="ProgressBarSide" style="color: Silver; width: 200px; visibility: visible;position: absolute;  left: 70px; top: 60px; margin-top: -32px">
        <span style="color: black; font-size: 14px; text-align: center;">正在处理数据，请耐心等待...</span>
	</div>

	<input id="temp_file_name" type="text"  value="${temp_file_name}" style="display:none"/>
	<input id="group_id" type="text"  value="${group_id}" style="display:none"/>
	<input id="hos_id" type="text"  value="${hos_id}" style="display:none"/>
	<input id="copy_code" type="text"  value="${hos_copy}" style="display:none"/>
	
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
