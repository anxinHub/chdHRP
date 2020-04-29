<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>

<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var spreadNS=dialog.get("data").spreadNS;
var printInfo=dialog.get("data").printInfo;

$(function() {
	
	 $("form").ligerForm();
	 loadPrintInfo();
});

function loadPrintInfo(){
	var spread=spreadNS.designer.wrapper.spread;
	if(!spread){
		return;
	}
	
	var sheet = spread.getActiveSheet();
	var sheetJson=sheet.toJSON();
	if(!sheetJson){
		return;
	}
	
	var printInfoLoad=sheetJson.printInfo;
	if(!printInfoLoad){
		resetPrintInfo();
		return;
	}
	//设置纸张大小
	var paperSize=printInfoLoad.paperSize;
	if(paperSize){
		if(paperSize.width && printInfoLoad.paperSize.width!="100%"){			
			$("#page_width").val(toDecimal(printInfoLoad.paperSize.width));
		}else{
			$("#page_width").val("100%");
		}
		
		if(paperSize.height && printInfoLoad.paperSize.height!="100%"){
			$("#page_height").val(toDecimal(printInfoLoad.paperSize.height));
		}else{
			$("#page_height").val("100%");
		}
	}
	
	//设置边距
	if(printInfoLoad.margin){
		$("#margin_top").val(printInfoLoad.margin.top);
	}else{
		$("#margin_top").val(0);
	}
	
	if(printInfoLoad.margin){
		$("#margin_bottom").val(printInfoLoad.margin.bottom);
	}else{
		$("#margin_bottom").val(0);
	}
	
	
	if(printInfoLoad.margin){
		$("#margin_left").val(printInfoLoad.margin.left);
	}else{
		$("#margin_left").val(20);
	}
	
	if(printInfoLoad.margin){
		$("#margin_right").val(printInfoLoad.margin.right);
	}else{
		$("#margin_right").val(0);
	}
	
	if(printInfoLoad.margin){
		$("#margin_header").val(printInfoLoad.margin.header);
	}else{
		$("#margin_header").val(0);
	}
	
	if(printInfoLoad.margin){
		$("#margin_footer").val(printInfoLoad.margin.footer);
	}else{
		$("#margin_footer").val(0);
	}
}

function mySave(){
	var spread=spreadNS.designer.wrapper.spread;
	if (!spread) {
        return;
    }
	
	var reg = /^\d+(?=\.{0,1}\d+$|$)/;
	/* if(!reg.test($("#page_width").val())){
		$.ligerDialog.error("宽度必须为正数！");
		return;
	} 
	if(!reg.test($("#page_height").val())){
		$.ligerDialog.error("高度必须为正数！");
		return;
	}  */
	
	var sheet = spread.getActiveSheet();
   // var printInfo = sheet.Print.PrintInfo();
    //var printInfo = new spreadNS.Print.PrintInfo();
    //设置纸张大小
    printInfo.paperSize(new spreadNS.Print.PaperSize($("#page_width").val(), $("#page_height").val()));
    
    //设置边距
    if(!reg.test($("#margin_top").val())){
		$.ligerDialog.error("上边距必须为正数！");
		return;
	} 
	if(!reg.test($("#margin_left").val())){
		$.ligerDialog.error("左边距必须为正数！");
		return;
	} 
	if(!reg.test($("#margin_right").val())){
		$.ligerDialog.error("右边距必须为正数！");
		return;
	} 
	if(!reg.test($("#margin_bottom").val())){
		$.ligerDialog.error("下边距必须为正数！");
		return;
	}
	
	if(!reg.test($("#margin_header").val())){
		$.ligerDialog.error("页眉边距必须为正数！");
		return;
	} 
	
	if(!reg.test($("#margin_footer").val())){
		$.ligerDialog.error("页脚边距必须为正数！");
		return;
	} 
	
	printInfo.margin({
    	top:$("#margin_top").val(), 
    	bottom:$("#margin_bottom").val(), 
    	left:$("#margin_left").val(), 
    	right:$("#margin_right").val(), 
    	header:$("#margin_header").val(), 
    	footer:$("#margin_footer").val()
    });
    
	sheet.printInfo(printInfo);
   // $.ligerDialog.success("设置成功！");
    frameElement.dialog.close();
}

function resetPrintInfo(){
	$("#page_width").val("850");
	$("#page_height").val("1100");
	$("#margin_top").val(20);
	$("#margin_bottom").val(0);
	$("#margin_left").val(20);
	$("#margin_right").val(0);
	$("#margin_header").val(0);
	$("#margin_footer").val(0);
}


//强制保留两位小数
function toDecimal(x) {  
	if(x.toString().indexOf(".")==-1){
		return;
	}
    var f = parseFloat(x);    
    if (isNaN(f)) {
        return false;    
    }
    
    var f = Math.round(x*100)/100;
    var s = f.toString();    
    var rs = s.indexOf('.');    
    if (rs < 0) {    
        rs = s.length;    
        s += '.';    
    }    
    while (s.length <= rs + 2) {    
        s += '0';    
    }    
    return s;    
};    

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
<form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px" align="center">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:0px;">纸张大小(px)：</td>
			<td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="page_width" ltype="text"/>
			</td>
			<td>宽</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"></td>
			<td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="page_height" ltype="text"/>
			</td>
			<td>高</td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td">上边距（px）：</td>
		    <td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="margin_top" ltype="text"/>
			</td>
			<td></td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td">下边距（px）：</td>
		    <td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="margin_bottom" ltype="text"/>
			</td>
			<td></td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td">左边距（px）：</td>
		    <td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="margin_left" ltype="text"/>
			</td>
			<td></td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td">右边距（px）：</td>
		    <td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="margin_right" ltype="text"/>
			</td>
			<td></td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td">页眉边距（px）：</td>
		    <td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="margin_header" ltype="text"/>
			</td>
			<td></td>
		</tr>
		<tr>
		    <td align="right" class="l-table-edit-td">页脚边距（px）：</td>
		    <td align="left" class="l-table-edit-td" style="padding-left:5px;">
				<input type="text" id="margin_footer" ltype="text"/>
			</td>
			<td></td>
		</tr>
	</table>
</form>	
</body>
</html>
