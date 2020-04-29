<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>

<script type="text/javascript">
//spread=document.getElementById('spreadFrame').contentWindow.GcSpread.Sheets.designer.wrapper.spread;

var spreadNS;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var PSpread=dialog.get("data").spread;

    $(function ()
    {
    	loadCenterTool();
    	$("#spreadFrame").css("height", $(window).height()-30);
         
    });

    var $tool;
    function loadCenterTool(){
    	
    	
    var item= [ 
			{ text: '选择文件',id:'openFileDialog.',icon:'uploadzip', click: openFileDialog},
			{ line:true },
   		 	{ text: '导出Excel',id:'down',icon:'down', click: exportExcel}
   	];	
    
    
    item.push({ line:true });
	item.push({ text: '确定',id:'save',icon:'save', click: mySave});
	item.push({ line:true });
	item.push({ text: '关闭',id:'candle',icon:'candle', click: myClose});
    
    
   	 $tool=$("#centertoolbar").ligerToolBar({ items: item});
   } 
   
    function isUpdateData(obj){
    }
    
    function initSpreadTable() {
    	var $spreadFrame=$(window.frames["spreadFrame"].document);
    	var $content=$spreadFrame.find(".content .fill-spread-content");    
    	$spreadFrame.find(".header").css("height",0);
	   	$content.css({ top: 2 });
	   	$content.parent().css({ height: $content.height() });
	   	var spread = spreadNS.designer.wrapper.spread;
	 	spread.options.newTabVisible=false;
	 	initSpreadData();
	   	spread.refresh();
    }
    
    //初始化值
    var requireJson=[];
    function initSpreadData(){
    	var spread = spreadNS.designer.wrapper.spread;
    	var sheet = spread.getSheet(0);
    	spread.removeSheet(1);
    
    }
       
    function myClose(){
    	dialog.close();
    }
   
    function openFileDialog(){
    	var spread=spreadNS.designer.wrapper.spread;
    	document.getElementById('spreadFrame').contentWindow.openFileDialog(spread,{isSavedWarning:false});
    }

    function exportExcel(){
    	document.getElementById('spreadFrame').contentWindow.exportExcel(spreadNS.designer.wrapper.spread,null);
    }
    
    //确定
    function mySave(){
    	
     	var spread=spreadNS.designer.wrapper.spread;
     	PSpread.isPaintSuspended(true);
     	PSpread.fromJSON(spread.toJSON());
     	PSpread.isPaintSuspended(false);
     	frameElement.dialog.close();
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
	<div id="centertoolbar" ></div>
	<iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width="100%" id="spreadFrame" name="spreadFrame"></iframe>
			
</body>
</html>
