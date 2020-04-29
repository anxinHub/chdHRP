<%@ page language="java" pageEncoding="utf-8" import="com.zhuozhengsoft.pageoffice.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script type="text/javascript">

$(function (){
});

//加载完JS回调函数
function OnProgressComplete() {
	ShowPrint();
//window.parent.myFunc(); //调用父页面的js函数

}

//保存
function ShowSave() {
   document.getElementById("PageOfficeCtrl1").WebSave();
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
	savePrintCount();
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
	ajaxJsonObjectByUrl("deleteFile.do?isCheck=false",{temp_file_name:tempFileName},function(){
		ShowClose();
	});
}

//更新打印次数
function savePrintCount(){
	if($("#business_no").val()==""){
		delteFile();
		return;
	}
	
	var para={
			group_id:$("#group_id").val(),
			hos_id:$("#hos_id").val(),
			copy_code:$("#copy_code").val(),
			template_code:$("#template_code").val(),
			business_no:$("#business_no").val()
	};
	ajaxJsonObjectByUrl("savePrintCount.do?isCheck=false",para,function(){
		delteFile();
	});
}


</script>
</head>

<body>

	<div id="ProgressBarSide" style="color: Silver; width: 200px; visibility: visible;position: absolute;  left: 70px; top: 60px; margin-top: -32px">
        <span style="color: black; font-size: 14px; text-align: center;">正在处理数据，请耐心等待...</span>
	</div>

	<input id="temp_file_name" type="text"  value="${temp_file_name}" style="display:none"/>
	<input id="business_no" type="text"  value="${business_no}" style="display:none"/>
	<input id="group_id" type="text"  value="${group_id}" style="display:none"/>
	<input id="hos_id" type="text"  value="${hos_id}" style="display:none"/>
	<input id="copy_code" type="text"  value="${hos_copy}" style="display:none"/>
	<input id="template_code" type="text"  value="${template_code}" style="display:none"/>
	
	<div id="pageOfficeDiv" style="display:none">
	<!-- *********************pageoffice组件的使用 **************************-->
	<po:FileMakerCtrl id="PageOfficeCtrl1" />
	<!-- *********************pageoffice组件的使用 **************************-->
	</div>
	<script>
	$("#pageOfficeDiv").show();
	</script>
</body>
</html>
