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
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/ssf.js"></script>
<script src="<%=path%>/lib/Lodop/printThis.js" language="javascript"></script>
<script src="<%=path%>/lib/Lodop/webPrint.js" language="javascript"></script>

<script type="text/javascript">
var spread;
var spreadNS;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var responseData=dialog.get("data").responseData;
var printPara=dialog.get("data").printPara;
var printInfo;

    $(function (){
    	loadCenterTool()
    	$("#spreadFrame").css("height", $(window).height()-30);
    });
    
    var $tool;
    function loadCenterTool(){
    	var item= [ 
			{ text: '导出Excel',id:'down.',icon:'down', click: exportExcel},
			{ line:true },
   		 	{ text: '打印',id:'print',icon:'print', click: myPrint}
 	        
    	];
    	//查询页面
		if(printPara.openType=="query"){
			item.push({ line:true });
			item.push({ text: '打印维护',id:'settings',icon:'settings', click: myPrintSet});
			item.push({ line:true });
			item.push({ text: '显示工具栏',id:'up',icon:'up', click: hidenSpreadMenu});
			item.push({ line:true });
			item.push({ text: '保存',id:'save.',icon:'save', click: mySave});
		}
		item.push({ line:true });
		item.push({ text: '关闭',id:'candle.',icon:'candle', click: myClose});
		
    	$tool= $("#centertoolbar").ligerToolBar({ items:item});
		
      } 
    
    
    function initSpreadTable() {
      	var $spreadFrame=$(window.frames["spreadFrame"].document);
    	var $content=$spreadFrame.find(".content .fill-spread-content");    
    	$spreadFrame.find(".header").css("height",0);
	   	$content.css({ top: 2 });
	   	$content.parent().css({ height: $content.height() }); 
	   	
	   	spread = spreadNS.designer.wrapper.spread;
	   	spread.suspendPaint();
	   	
	 	spread.removeSheet(1);
	 	
	 	// 直接用spread打印
		initSpreadJSData();
		
	 	spread.options.newTabVisible=false;
	 	setPreadJSTableStyle();
	    //spread.refresh();
	    spread.resumePaint();
	    
	  //  console.log(spread.toJSON());
    }
    
    var spreadJSTop=195;
    var headerHeight=175;
    function hidenSpreadMenu(item){
    	
    	var $spreadFrame=$(window.frames["spreadFrame"].document);
    	var $content=$spreadFrame.find(".content .fill-spread-content");
    	if(item.text=="隐藏工具栏"){
    		
    		headerHeight=$spreadFrame.find(".header").css("height");
    		spreadJSTop=$content.css("top");
    		$spreadFrame.find(".header").css("height",0);
	   	    $content.css({ top: 2 });
	   	 	//$tool.set("items",[{text: "显示工具栏", id: "up", icon: "down", click: hidenSpreadMenu}]);
	   	 	item.text="显示工具栏";
	   	 	item.icon="down";
	   	 	
	    }else{
	    	
	    	headerHeight=$spreadFrame.find(".header").css("height",headerHeight);
	   	    $content.css({ top: spreadJSTop });
		   	item.text="隐藏工具栏";
	   	 	item.icon="up";
	   	 	
	    }  
    	$content.parent().css({ height: $content.height() });
   	    spreadNS.designer.wrapper.spread.refresh();
    	$tool._render();
    	
    }
      
    /*
    	printPara.type=10
    	直接用spreadJS打印
    */
    function initSpreadJSData(){
    	 spread.isPaintSuspended(true);
         spread.fromJSON(responseData);
         spread.isPaintSuspended(false);
    }
    
 
    
    function myClose(){
    	dialog.close();
    }
    
    //打印
    function myPrint(){
        
    	var pathName=window.location.pathname;
   		pathName=pathName.replace(".jsp","").replace(/\//g,'{').replace("{CHD-HRP{hrp{","");
   		pathName=pathName+"{"+printPara.mod_code+"{"+printPara.report_code;
   		
		lodopSpread({
			spread:spread.toJSON(),
			title:"报表打印",
			taskName:pathName
			//foot:{user:parent.sessionJson.user_name,date:true}
		}); 
     
    }
   		
    //打印维护
	function myPrintSet(){
    	
  /*   	spreadPrint({
			spread:spread.toJSON(),
			head:false
		});
		return;  */
		
		var pathName=window.location.pathname;
   		pathName=pathName.replace(".jsp","").replace(/\//g,'{').replace("{CHD-HRP{hrp{","");
   		pathName=pathName+"{"+printPara.mod_code+"{"+printPara.report_code;
   	
		lodopSpread({
			spread:spread.toJSON(),
			title:"报表打印",
			taskName:pathName,
			foot:{user:parent.sessionJson.user_name,date:true},
			type:2
		}); 
	}
    
    function mySave(){
    	//保存报表内容
    	var loadIndex = layer.load(1);
        para={
  			content:JSON.stringify(spread.toJSON()),
  			report_code:printPara.report_code,
 			mod_code:printPara.mod_code,
 			acc_year:printPara.acc_year,
 			acc_month:printPara.acc_month
   	 	};
        ajaxJsonObjectBylayer("../hrp/acc/superReport/saveSuperReportContent.do?isCheck=false",para,function(){
        	$.ligerDialog.success("保存成功！");
        },layer,loadIndex);
    }
    
    //设置表格样式
    function setPreadJSTableStyle(){
    	for(var i=0;i<spread.getSheetCount();i++){
        	//var sheet = spread.getActiveSheet();
        	var sheet = spread.getSheet(i);
        	sheet.clearSelection();
            sheet.options.protectionOptions.allowSelectLockedCells=true;//是否允许用户选择被锁定的单元格。
            sheet.options.protectionOptions.allowSelectUnlockedCells=true;//是否允许用户选择未被锁定的单元格。
            sheet.options.protectionOptions.allowFilter=true;//是否允许用户筛选。
        	sheet.options.protectionOptions.allowSort = true;//是否允许用户排序。
        	sheet.options.protectionOptions.allowResizeRows=true;//是否允许用户改变行高
        	sheet.options.protectionOptions.allowResizeColumns=true;//是否允许用户改变列宽
        	sheet.options.protectionOptions.allowEditObjects=false;//是否允许用户编辑浮动对象。
        	
        	sheet.options.isProtected=true;
        	sheet.options.rowHeaderVisible = true;//是否显示行标尺
        	sheet.options.colHeaderVisible =true;//是否显示列标尺
        	sheet.options.sheetTabColor="#ADD8E6";
        	sheet.options.gridline = {showVerticalGridline: true, showHorizontalGridline: true};//是否显示外边框、是否显示网格线
        }
    }
    
    function exportExcel(){
    	var spread=spreadNS.designer.wrapper.spread;
	 	if (!spread) {
            return;
        }
	 	
    	if(printPara.type && printPara.type==1){
		 	// 表单级别绑定，复制表头导出
           var tempSpread = new spreadNS.Workbook();
           tempSpread.fromJSON(spread.toJSON( { includeBindingSource: true } ));
          /*  var hearderStyle = new spreadNS.Style();
            hearderStyle.borderLeft =new spreadNS.LineBorder("black",spreadNS.LineStyle.medium);
            hearderStyle.borderTop = new spreadNS.LineBorder("black",spreadNS.LineStyle.medium);
            hearderStyle.borderRight = new spreadNS.LineBorder("black",spreadNS.LineStyle.medium);
            hearderStyle.borderBottom = new spreadNS.LineBorder("black",spreadNS.LineStyle.medium);  */
           
    		for(var sheetPro in tempSpread.sheets){
                var tempSheet = tempSpread.sheets[sheetPro];
                if(!(tempSheet instanceof spreadNS.Worksheet)){
                    continue;
                }

                var sheet = spread.getSheet(0);
                tempSheet.addRows(0, 1);
              //  tempSheet.setStyle(0, -1, hearderStyle);
                for(var col = 0; col < sheet.getColumnCount(); col++){
                	tempSheet.setValue(0, col, sheet.getText(0, col, spreadNS.SheetArea.colHeader));
                }
                tempSheet.getRange(0, 0,1, sheet.getColumnCount()).hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
            }
    		
            document.getElementById('spreadFrame').contentWindow.exportExcel(tempSpread,null);
		}else{
			document.getElementById('spreadFrame').contentWindow.exportExcel(spread,null);
		}
    	
    }
   
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="centertoolbar" ></div>
	<iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width="100%" id="spreadFrame" name="spreadFrame"></iframe>
</body>
</html>
