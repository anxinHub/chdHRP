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
<script src="<%=path%>/lib/Lodop/printThis.js" language="javascript"></script>
<script src="<%=path%>/lib/Lodop/webPrint.js" language="javascript"></script>

<script type="text/javascript">
var spread;
var spreadNS;
var sheet;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var url=dialog.get("data").url;
var para=dialog.get("data").para;
var use_id=para.use_id === undefined ? 0 : para.use_id;//没有值，统一打印
var para014=1;//模板每页打印次数
var printArray = [];//存放打印次数单据号//102-CK17010154@1,102-CK17010155@2
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
    	sheet = spread.getActiveSheet();
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
    	
		windowMask =$(".loading-placeholder.loading-pic");
		$(".loading-placeholder.loading-pic").css("display","block");
		setTimeout(function(){
			//console.log(spread.toJSON())
			
			//window打印
	    	if(url=="hrp/acc/accvouch/superVouch/querySuperVouchPrintBatch.do"){
	    		spreadPrint({
	    	 		spread:spread.toJSON(),
	    	 		head:false
	    	 	});
	        	
	        	if(printArray.length>0){
	    			var para1={
	    				template_code:para.template_code,
	    				use_id:use_id,
	    				business_no:printArray.toString()
	    			};
	    			ajaxJsonObjectByUrl("../hrp/acc/accvouch/superPrint/savePrintCount.do?isCheck=false",para1,function(responseData){
	    				
	    			});
	    		}
	        	$(".loading-placeholder.loading-pic").css("display","none");
				return;
	    	}
			
			
			//更新打印次数
			if(printArray.length>0){
				var para1={
					template_code:para.template_code,
					use_id:use_id,
					business_no:printArray.toString()
				};
				ajaxJsonObjectByUrl("../hrp/acc/accvouch/superPrint/savePrintCount.do?isCheck=false",para1,function(responseData){
					if(responseData.state=="true"){
						spread.print();
					}else{
						$.ligerDialog.error("更新打印次数失败！");
					}
				});
				
			}else{
				spread.print();
			}
			
			$(".loading-placeholder.loading-pic").css("display","none");
		},0) 
    
    }

    //设置表格样式
    function setPreadJSTable(){
    	
    	spread.options.newTabVisible=false;
    	sheet = spread.getSheet(0);
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
    
	//打印模板设置
	function myPrintSet(){
	   var template_code=para["template_code"];
       parent.$.ligerDialog.open({url : 'hrp/acc/accvouch/superPrint/printSetPage.do?isCheck=false&template_code='+template_code+'&use_id='+use_id,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
		});
	}
  
  //重新加载
 function myLoad(){
	var loadIndex = layer.load(1);
	var para1={
			template_code:para.template_code,
			use_id:use_id
	};
	//取模板内容
	ajaxJsonObjectBylayer("../hrp/acc/accvouch/superPrint/querySuperPrintContentByCode.do?isCheck=false",para1,function (responseData){
		//console.log(responseData)
		//console.log(JSON.stringify(responseData))
		spread.isPaintSuspended(true);
	    spread.fromJSON(responseData);
	    setPreadJSTable();
	    spread.isPaintSuspended(false);
	    
	    createSpreadData();
	    
	},layer,loadIndex);
 } 
 
//加载数据 
function createSpreadData(){
	
	var loadIndex = layer.load(1);
	var pageCount=0;//系统总页数
	var pageIndex=0;//系统页码
	var tempRowCount=parseInt(sheet.getRowCount());//模板行数
	var tempColumnCount=parseInt(tempColumnCount=sheet.getColumnCount());//模板列数
	var printPara;//打印参数
	var para003="否";//是否套打
	var para004=0;//明细开始行
	var para005=0;//明细结束行
	var para013="否";//是否清除空行
	var pageSize=0;//单据明细页大小
		
	//取业务数据、打印参数
	ajaxJsonObjectBylayer("..//"+url,para,function(responseData){
		//console.log(responseData)
		printArray=[];
		printPara=responseData.para;
		pageCount=responseData.page;
		var rows=responseData.Rows;
		var businessKey=printPara["999999"];//取打印次数业务表字段
		para003=printPara["003"]=== undefined ? "否" : printPara["003"];//是否套打
		para004=parseInt(printPara["004"]);//明细开始行
		para005=parseInt(printPara["005"]);//明细结束行
		var para011=printPara["011"] === undefined ? "" : printPara["011"];//打印次数
		para013=printPara["013"] === undefined ? "否" : printPara["013"];
		para014=parseInt(printPara["014"] === undefined ? 1 : printPara["014"]);//模板每页打印次数
		
		if(para004!="" && para005!=""){
			pageSize=para005-para004+1;//明细数据页大小
		}
		
    	sheet.suspendPaint();
    	
    	//根据模板复制表格
		copyTable(pageCount);
		
		$.each(rows, function(i, obj) {
			
			//根据模板复制表格
			pageIndex=pageIndex+1;//系统页码
			var page001=1;//单据总页数
			var page002=1;//当前单据页码
			var detail=obj.detail;//明细数据
			var detailIndex=0;
			
			if(detail.length>pageSize){
				page001=Math.ceil(detail.length/pageSize);
			}
			
			//主表数据
			setRowColumnValue(obj);
			//处理系统打印参数
			setSysParaValue(page001,page002);
			//打印次数
			if(para011!="" && obj[businessKey] && obj["011"]){
				//102-CK17010154@1,102-CK17010155@2
				printArray.push(obj[businessKey]+"@"+obj["011"]);	
			}
			
			if(detail.length>0){
				
				$.each(detail, function(j, dobj) {
					
					if(detailIndex>pageSize-1){
						//超过明细表格，模板页数+1
						detailIndex=0;
						pageIndex=pageIndex+1;
						page002=page002+1;//单据页码
						
						//主表数据
						setRowColumnValue(obj);
						//处理系统打印参数
						setSysParaValue(page001,page002);
					}
					//明细表数据
					setColumnValue(dobj,detailIndex);
					detailIndex=detailIndex+1;
					
				});
				
				//单据最后一页处理空行
				if(page001==page002){
					setRowVisible(detailIndex);
				}
				
			}else{
				setRowVisible(0);
			}
	    	
		});
		
		sheet.resumePaint();
	},layer,loadIndex);
	
	//根据模板复制表格
	function copyTable(){
		
		if(tempRowCount==0 || tempColumnCount==0){
			return;
		}
		
		//处理套打
		if(para003=="是"){
			//清除模板边框
			var cell = sheet.getRange(0, 0, tempRowCount, tempColumnCount);
			var ns = GC.Spread.Sheets;
			cell.borderLeft(new ns.LineBorder("transparent", ns.LineStyle.thin));
			cell.borderTop(new ns.LineBorder("transparent", ns.LineStyle.thin));
			cell.borderRight(new ns.LineBorder("transparent", ns.LineStyle.thin));
			cell.borderBottom(new ns.LineBorder("transparent", ns.LineStyle.thin));
			
			//清除模板数据
			for(var i=0;i<tempRowCount;i++){
				for(var j=0;j<tempColumnCount;j++){
					var comment = sheet.comments.get(i, j);
					if(comment==null){
						sheet.setValue(i,j,"");
					}
				}
			}
		//	sheet.options.gridline = {showVerticalGridline: false, showHorizontalGridline: false};//是否显示外边框、是否显示网格线
		}
		
		//开始复制数据
		for(var i=1;i<pageCount;i++){
			var toRow=sheet.getRowCount();
	    	//addRows(row, count);
	    	sheet.addRows(toRow, tempRowCount);
	    	sheet.setRowPageBreak(toRow, true);//分页符
	    	for(var j=0;j<tempRowCount;j++){    		
	    		sheet.setRowHeight(j+toRow, sheet.getRowHeight(j));
	    	}
	    	//copyTo(fromRow, fromColumn, toRow, toColumn, rowCount, columnCount, option);
			sheet.copyTo(0, 0, toRow, 0, tempRowCount, tempColumnCount, GC.Spread.Sheets.CopyToOptions.all);
		}
		
		//var printInfo = sheet.printInfo();
		//printInfo.paperSize(new GC.Spread.Sheets.Print.PaperSize(GC.Spread.Sheets.Print.PaperKind.a4));
		//printInfo.paperSize(new GC.Spread.Sheets.Print.PaperSize(210, 127));
	}
	
	
	//填充主表数据
	function setRowColumnValue(obj){
		//console.log(obj)
		for(var key in obj){
			 var paraValue=printPara[key];
			 if(paraValue === undefined || paraValue=="null" || paraValue==""){
				 continue;
			 }
			 
			 
			 var paraVArray=paraValue.split(",");
			 if(paraVArray.length<2){
				 continue;
			 }
			 
			 data=obj[key];
			 var rowIndex=parseInt(paraVArray[0])-1;//横坐标
			 var columnIndex=columnIndex=parseInt(paraVArray[1])-1;//列坐标
			 var beginRowIndex=rowIndex+tempRowCount*(pageIndex-1);//目标行
			 var flag=1//主表
			 
			 //特殊标识
			 if(paraVArray.length==3){
				 flag=paraVArray[2];
				 
				 if(flag==5){
					 data=amountInWords(obj[key],2);//金额转大写
				 }
			 }
			 //console.log(beginRowIndex,obj[key])
			 sheet.setValue(beginRowIndex,columnIndex, data);
		}
	 }
	
	//填充明细表数据
	function setColumnValue(obj,index){

		var rowIndex=(para004+index-1)+(tempRowCount*(pageIndex-1));//横坐标
		for(var key in obj){
			var paraValue=printPara[key];
			if(paraValue === undefined || paraValue=="null" || paraValue==""){
				continue;
			}
			
		 /* var re = /^[1-9]+[0-9]*]*$/; //判断正整数  
			if (!re.test(paraValue)) {
			　　return;
			} */
			
			var columnIndex=parseInt(paraValue)-1;//列坐标
			sheet.setValue(rowIndex,columnIndex, obj[key]);
		}
		
	}
	
	//处理系统打印参数
	function setSysParaValue(page001,page002){
		//总页数
		setRowColumnValue({"001":page001});
		
		//当前页码
		setRowColumnValue({"002":page002});
	} 
	
	//清除空行
	function setRowVisible(detailIndex){
		if(para013=="否" || pageSize==0){
			return;
		}
	
		var rowB=para004+(tempRowCount*(pageIndex-1))+detailIndex;
		var rowE=para005+(tempRowCount*(pageIndex-1));
		for(var i=rowB;i<=rowE;i++){
			sheet.setRowVisible(i-1, false);
		}
		
	} 
	  
 }
 
 
 //导出excel
 var excelIo;
 function exportExcel(){
	
	 excelIo.save(spread.toJSON(), function (blob) {
          saveAs(blob,"新建文件.xlsx");
       }, function (e) {
             // process error
          console.log(e);
       });
	 
	/*  $.ligerDialog.prompt('导出文件名称', function (yes,value) {
 		if(yes && value!="") {
 	           // here is excel IO API
 	         excelIo.save(spread.toJSON(), function (blob) {
 	            saveAs(blob, value+".xlsx");
 	         }, function (e) {
 	               // process error
 	            console.log(e);
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
	<div id='spread' style='width:100%;'></div>
</body>
</html>
