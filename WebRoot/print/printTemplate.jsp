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
<script src="<%=path%>/lib/Lodop/ssf.js"></script>
<script src="<%=path%>/lib/Lodop/LodopFuncs.js" language="javascript"></script>
<link href='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/css/gc.spread.sheets.excel2013white.10.1.0.css' rel='stylesheet' type='text/css'/>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/gc.spread.sheets.all.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/interop/gc.spread.excelio.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/pluggable/gc.spread.sheets.print.10.1.0.min.js' type='text/javascript'></script>
<script src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/lib/FileSaver.js"></script>
<script src="<%=path%>/lib/SpreadJS10/license.js" type="text/javascript"></script>   
<script type="text/javascript">
var spread;
var spreadNS;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var url=dialog.get("data").url;
var para=dialog.get("data").para;
var windowMask;               // 遮罩
    $(function (){
    	loadCenterTool();
		initSpread();
    	//$("#spreadFrame").css("height", $(window).height()-30);
    	
    });
    
    function loadCenterTool(){
     	 $("#centertoolbar").ligerToolBar({ items: [ 
			{ text: '刷新',id:'refresh',icon:'refresh', click: myLoad},
			{ line:true },
  		 	{ text: '打印',id:'print',icon:'print', click: myPrint},
  			{ line:true },
  			{ text: '导出Excel',id:'down.',icon:'down', click: exportExcel},
  			{ line:true },
  			{ text: '打印模板设置',id:'archives.',icon:'archives', click: myPrintSet},
  			{ line:true },
  	        { text: '关闭',id:'candle.',icon:'candle', click: myClose}
     	        ]
     	    });
     } 
    
     function initSpread() {
    	$("#spread").css("height", $(window).height()-30);
    	spread = new GC.Spread.Sheets.Workbook($('#spread')[0], { sheetCount: 1 });
        spreadNS = GC.Spread.Sheets;
        spread.options.newTabVisible=false;
        spread.options.grayAreaBackColor='#fff';
        excelIo = new GC.Spread.Excel.IO();
        myLoad();
     
    }
    
     
  
  /*  function initSpreadTable() {
    	var $spreadFrame=$(window.frames["spreadFrame"].document);
    	var $content=$spreadFrame.find(".content .fill-spread-content");    
    	$spreadFrame.find(".header").css("height",0);
	   	$content.css({ top: 2 });
	   	$content.parent().css({ height: $content.height() });
	   	spread = spreadNS.designer.wrapper.spread;
	 	spread.options.newTabVisible=false;
	 	myLoad();
	    spread.refresh();
	  
    }*/
    
    function myClose(){
    	dialog.close();
    }
    
    //打印
    function myPrint(){
     	if(url=="hrp/acc/accvouch/superVouch/querySuperVouchPrintBatch.do"){
    		$(".loading-placeholder.loading-pic").css("display","block");
			 	setTimeout(function(){
			 	 lodopSpread({
			 		spread:spread.toJSON(),
			 		taskName:"模板打印",
			 		percent:"Width:100%",
			 		head:false,
			 		pageCount:pageCount,//模板每页打印次数 
			 		complete:function(){
			 			$(".loading-placeholder.loading-pic").css("display","none");
			 		}
			 	});
			 },10)
			 return;
    	}
		
		windowMask =$(".loading-placeholder.loading-pic");

		$(".loading-placeholder.loading-pic").css("display","block");
    /* 	$("#spread").focus(function(e){      //因为在spread.print函数里有focus方法，所以当触发focus时关闭外面的遮罩
			 if($(".loading-placeholder.loading-pic").css("display") == "none"){
				 return ;
			 }
			 $(".loading-placeholder.loading-pic").css("display","none");
         }); */
    	 
		setTimeout(function(){
			spread.print(); 
			$(".loading-placeholder.loading-pic").css("display","none");
		},0) 
    
    }

    //设置表格样式
    function setPreadJSTable(){
    	
    	spread.options.newTabVisible=false;
    	 for(var i=0;i<spread.getSheetCount();i++){
        	//var sheet = spread.getActiveSheet();
        	var sheet = spread.getSheet(i);
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
        	sheet.options.gridline = {showVerticalGridline: false, showHorizontalGridline: false};//是否显示外边框、是否显示网格线
        }
    }
    
	//打印模板设置
	function myPrintSet(){
	   var template_code=para["template_code"];
	   var use_id="";
	   if(para["use_id"]!=undefined){
		   use_id=para["use_id"];
	   }
	   
       parent.$.ligerDialog.open({url : 'hrp/acc/accvouch/superPrint/printSetPage.do?isCheck=false&template_code='+template_code+'&use_id='+use_id,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
		});
	}
  
  //重新加载
 var pageCount=1;
 function myLoad(){
	var loadIndex = layer.load(1);
	ajaxJsonObjectBylayer("..//"+url,para,function (responseData){
		//console.log(JSON.stringify(responseData))
		spread.isPaintSuspended(true);
	    spread.fromJSON(responseData);
	    setPreadJSTable();
	    spread.isPaintSuspended(false);
	    pageCount=responseData.pageCount;
	    layer.close(loadIndex);
	},layer,loadIndex);
 } 
  
 //导出excel
 var excelIo;
 function exportExcel(){
	
	 excelIo.save(spread.toJSON(), function (blob) {
          saveAs(blob,"新建文件.xlsx");
       }, function (e) {
             // process error
          //console.log(e);
       });
	 
	/*  $.ligerDialog.prompt('导出文件名称', function (yes,value) {
 		if(yes && value!="") {
 	           // here is excel IO API
 	         excelIo.save(spread.toJSON(), function (blob) {
 	            saveAs(blob, value+".xlsx");
 	         }, function (e) {
 	               // process error
 	            //console.log(e);
 	         });
 		}
 		
 	}); */
 	//document.getElementById('spreadFrame').contentWindow.exportExcel(spread,null);
 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div class='loading-placeholder loading-pic ' style="display: none"></div>
	<div id="centertoolbar" ></div>
	<!-- iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width="100%" id="spreadFrame" name="spreadFrame"></iframe-->
	<div id='spread' style='width:100%;' tabindex = 1></div>
</body>
</html>
