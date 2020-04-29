<%@ page language = "java" contentType = "text/html; charset = UTF-8" pageEncoding = "UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns = "http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8" />
<link href = "<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel = "stylesheet" type = "text/css" />
<link href = "<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel = "stylesheet" type = "text/css" />
<script src = "<%=path%>/lib/jquery/jquery-1.9.0.min.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/json2.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/hrp.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/ligerUI/js/core/base.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/ligerUI/js/ligerui.all.js" type = "text/javascript"></script>
<script src = "<%=path%>/lib/layer-v2.3/layer/layer.js" type = "text/javascript"></script>

<script type = "text/javascript">
//spread = document.getElementById('spreadFrame').contentWindow.GcSpread.Sheets.designer.wrapper.spread;

var spreadNS;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var grid=dialog.get("data").grid;
var columns = "";
    $(function (){
    	loadCenterTool();
    	$("#spreadFrame").css("height", $(window).height()-30);
    });

    var $tool;
	function loadCenterTool(){
		$tool = $("#centertoolbar").ligerToolBar({ items: [ 
			{ text: '选择文件',id:'openFileDialog.',icon:'uploadzip', click: openFileDialog},
			{ line:true },
			{ text: '导出Excel',id:'down.',icon:'down', click: exportExcel},
			{ line:true },
   	        {text: '确定', id:'save', icon:'save', click: mySave},   	   	    	
   	        { line:true },
	        { text: '关闭',id:'candle.',icon:'candle', click: myClose}
		]});
	} 
    
	function initSpreadTable() {
		var $spreadFrame = $(window.frames["spreadFrame"].document);
		var $content = $spreadFrame.find(".content .fill-spread-content");    
		$spreadFrame.find(".header").css("height",0);
		$content.css({ top: 2 });
		$content.parent().css({ height: $content.height() });
		var spread = spreadNS.designer.wrapper.spread;
		spread.options.newTabVisible = false;
		initSpreadData();
		spread.refresh();
    }
    
    //初始化值
    function initSpreadData(){
    	var spread = spreadNS.designer.wrapper.spread;
    	var sheet = spread.getSheet(0);
    	
    	sheet.suspendPaint();
    	//设置列
    	sheet.setColumnCount(44);  //默认最大20列如超过20列则需设置具体的最大列数
    	sheet.setColumnWidth(0, "100"); sheet.setText(0, 0, "药品编码");
    	sheet.setColumnWidth(1, "120"); sheet.setText(0, 1, "药品名称");
    	sheet.setColumnWidth(2, "120"); sheet.setText(0, 2, "别名");
    	sheet.setColumnWidth(3, "120"); sheet.setText(0, 3, "药品类别");
    	sheet.setColumnWidth(4, "100"); sheet.setText(0, 4, "计价方法");
    	sheet.setColumnWidth(5, "80"); sheet.setText(0, 5, "药品属性");
    	sheet.setColumnWidth(6, "80"); sheet.setText(0, 6, "药品剂型");
    	sheet.setColumnWidth(7, "100"); sheet.setText(0, 7, "药品规格");
    	sheet.setColumnWidth(8, "80"); sheet.setText(0, 8, "入库单位");
    	sheet.setColumnWidth(9, "100"); sheet.setText(0, 9, "病区拆零系数");
    	sheet.setColumnWidth(10, "80"); sheet.setText(0, 10, "病区单位");
    	sheet.setColumnWidth(11, "100"); sheet.setText(0, 11, "门诊拆零系数");
    	sheet.setColumnWidth(12, "80"); sheet.setText(0, 12, "门诊单位");
    	sheet.setColumnWidth(13, "120"); sheet.setText(0, 13, "生产厂商");
    	sheet.setColumnWidth(14, "120"); sheet.setText(0, 14, "供应商");
    	sheet.setColumnWidth(15, "90"); sheet.setText(0, 15, "计划价");
    	sheet.setColumnWidth(16, "90"); sheet.setText(0, 16, "加价率");
    	sheet.setColumnWidth(17, "90"); sheet.setText(0, 17, "零售价");
    	sheet.setColumnWidth(18, "90"); sheet.setText(0, 18, "启用日期");
    	sheet.setColumnWidth(19, "90"); sheet.setText(0, 19, "停用日期");
    	sheet.setColumnWidth(20, "100"); sheet.setText(0, 20, "代理商");
		sheet.setColumnWidth(21, "100"); sheet.setText(0, 21, "品牌名称");
		sheet.setColumnWidth(22, "100"); sheet.setText(0, 22, "药品用途");
		sheet.setColumnWidth(23, "100"); sheet.setText(0, 23, "包装规格");
		sheet.setColumnWidth(24, "100"); sheet.setText(0, 24, "单位重量");
		sheet.setColumnWidth(25, "100"); sheet.setText(0, 25, "单位体积");
		sheet.setColumnWidth(26, "100"); sheet.setText(0, 26, "ABC分类");
		sheet.setColumnWidth(27, "100"); sheet.setText(0, 27, "管理类别");
		sheet.setColumnWidth(28, "100"); sheet.setText(0, 28, "是否在用");
		sheet.setColumnWidth(29, "100"); sheet.setText(0, 29, "储存条件");
		sheet.setColumnWidth(30, "100"); sheet.setText(0, 30, "是否中标");
		sheet.setColumnWidth(31, "100"); sheet.setText(0, 31, "中标日期");
		sheet.setColumnWidth(32, "100"); sheet.setText(0, 32, "中标编码");
		sheet.setColumnWidth(33, "100"); sheet.setText(0, 33, "是否唯一供应商");
		sheet.setColumnWidth(34, "100"); sheet.setText(0, 34, "存储编码");
		sheet.setColumnWidth(35, "100"); sheet.setText(0, 35, "计划来源");
		sheet.setColumnWidth(36, "100"); sheet.setText(0, 36, "是否零库存管理");
		sheet.setColumnWidth(37, "100"); sheet.setText(0, 37, "是否条码管理");
		sheet.setColumnWidth(38, "100"); sheet.setText(0, 38, "是否个体码管理");
		sheet.setColumnWidth(39, "100"); sheet.setText(0, 39, "是否品种码管理");
		sheet.setColumnWidth(40, "100"); sheet.setText(0, 40, "是否保质期管理");
		sheet.setColumnWidth(41, "100"); sheet.setText(0, 41, "是否证件管理");
		sheet.setColumnWidth(42, "100"); sheet.setText(0, 42, "是否科室库管理");
		sheet.setColumnWidth(43, "100"); sheet.setText(0, 43, "是否自制品");
    	
    	sheet.getRange(0, 0, 1, 20).hAlign(spreadNS.HorizontalAlign.center);
    	sheet.frozenRowCount(1);
    	sheet.clearSelection();
    	sheet.resumePaint();
    	spread.removeSheet(1);
    }
       
    function myClose(){
    	dialog.close();
    }
   
    function openFileDialog(){
    	var spread = spreadNS.designer.wrapper.spread;
    	document.getElementById('spreadFrame').contentWindow.openFileDialog(spread,{isSavedWarning:false});
    }

    function exportExcel(){
    	document.getElementById('spreadFrame').contentWindow.exportExcel(spreadNS.designer.wrapper.spread,null);
    }
    
    //确定
    var row = null;
    var cell = null;
    function mySave(){
    	
     	var spread = spreadNS.designer.wrapper.spread;
    	var sheet = spread.getSheet(0);
    	
    	if(sheet.getText(0,0) != "药品编码"){
    		$.ligerDialog.error("导入模板不正确，请使用系统模板导入！");
			return false;
    	}
    	
     	var para = {
    			data: JSON.stringify(spread.toJSON()),
    	};
    	var loadIndex = layer.load(1);
    	ajaxJsonObjectBylayer("import.do?isCheck=false", para, function(responseData){	
    		layer.close(loadIndex);
    		
    		if(row != null){
    			var whiteStyle = new spreadNS.Style();
    			whiteStyle.backColor = "white";
    			whiteStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderTop = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderRight = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
        		sheet.setStyle(row, cell, whiteStyle, spreadNS.SheetArea.viewport);
    		}

    	 	if(responseData.state == "false" && responseData.row_cell != undefined){
    			row = parseInt(responseData.row_cell.split("：")[0]) - 1;
    			cell = parseInt(responseData.row_cell.split("：")[1]) - 1;
    			var redStyle = new spreadNS.Style();
    			redStyle.backColor = "red";
    	        /* cellStyle.name = 'cellStyle';
    	        sheet.addNamedStyle(cellStyle); */
    	        redStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        redStyle.borderTop = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        redStyle.borderRight = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        redStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        sheet.setStyle(row, cell, redStyle, spreadNS.SheetArea.viewport);
    			sheet.setActiveCell(row, cell);
    		}else if(responseData.state == "true"){
    			
    			grid.reload(responseData.Rows);
    			
    			//$.ligerDialog.success("导入成功，需要保存页面！");
    			//frameElement.dialog.close();
    		} 
    		
		},layer,loadIndex); 
    }
    
    </script>

</head>

<body style = "padding: 0px; overflow: hidden;">
	<div id = "pageloading" class = "l-loading" style = "display: none"></div>
	
	<div id = "centertoolbar" ></div>
	<div>药品类别、生产厂商、供应商之类的字典填写编码或名称都可导入</div>
	<iframe src = "<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width = "100%" id = "spreadFrame" name = "spreadFrame"></iframe>
			
</body>
</html>
