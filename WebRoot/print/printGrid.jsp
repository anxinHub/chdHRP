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
    
    function loadCenterTool(){
    	
    	if(printPara.is_print == false){
    		$("#centertoolbar").ligerToolBar({ items: [ 
	     	        { text: '导出Excel',id:'down.',icon:'down', click: exportExcel},
	      			{ line:true },
	       	        { text: '关闭',id:'candle.',icon:'candle', click: myClose}
          	    ]
          	});
    	}else{
    		 $("#centertoolbar").ligerToolBar({ items: [ 
	       		 	{ text: '打印',id:'print',icon:'print', click: myPrint},
	       			{ line:true },
	     	        { text: '打印设置',id:'account',icon:'account', click: myOpenInfo},
	     	        { line:true },
	     	        { text: '导出Excel',id:'down.',icon:'down', click: exportExcel},
	      			{ line:true },
	       	        { text: '关闭',id:'candle.',icon:'candle', click: myClose}
          		]
        	});
    	}
    	
      	
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
	 	
	 	if(printPara.type && printPara.type==2){
	 		// 表格绑定，表头带筛选功能，需要指定数据总条数，适用于单表头
	 		initSpreadTableData();
		}else if(printPara.type && printPara.type==3){
		 	// 单元格级别绑定，位置随便固定，需要指定数据总条数，表头条数，适用于多表头
			initSpreadColumnData();
		}else if(printPara.type && printPara.type==10){
		 	// 直接用spread打印
			initSpreadJSData();
		}else{
			// 表单级别绑定数据，适用于单表头，渲染速度快，行标尺做为表头，适用于单表头
			initSpreadFormData();
		}
	 	
	 	spread.options.newTabVisible=false;
	 	setPreadJSTableStyle();
	    //spread.refresh();
	    spread.resumePaint();
	    
	    /*************开始设置打印信息********************/
	    printInfo = new spreadNS.Print.PrintInfo();
	    if(printPara.type && printPara.type!=10){
			//printInfo.paperSize(new spreadNS.Print.PaperSize("100%", "100%"));
			printInfo.paperSize(new spreadNS.Print.PaperSize(spreadNS.Print.PaperKind.a4));
			printInfo.showBorder(true);//是否打印控件的外边框线
	        printInfo.showGridLine(false);//是否打印网格线 (默认是打印)
	
			//页码
			printInfo.headerLeft("");
       		printInfo.headerCenter("");
       		printInfo.headerRight("&P/&N");
       		printInfo.footerLeft("制表人：${sessionScope.user_name }");
       		printInfo.footerCenter("");
       		printInfo.footerRight("打印日期：&D"); 
	    
			printInfo.showRowHeader(spreadNS.Print.PrintVisibilityType.hide);
			//表单级别绑定
			if(printPara.type && printPara.type==1){
				printInfo.showColumnHeader(spreadNS.Print.PrintVisibilityType.show);
			}else{
				printInfo.showColumnHeader(spreadNS.Print.PrintVisibilityType.hide);
			}
			printInfo.margin({
		    	top:20, 
		    	bottom:20, 
		    	left:0, 
		    	right:0, 
		    	header:0, 
		    	footer:0
		    });
			
			window.setTimeout(function() {
				 // printInfo.zoomFactor(1);//缩放大小
				  printInfo.qualityFactor(3);//设置纸张质量因子
			      var sheet = spread.getSheet(0);
			      sheet.printInfo(printInfo);
	        }, 300);
	    }
	    
        /*************结束设置打印信息********************/
	  //  console.log(spread.toJSON());
    }
    
    /*
    	printPara.type=1
   		 表单级别绑定数据，适用于单表头，渲染速度快，行标尺做为表头，适用于单表头
   		 参数：
   		printPara{autoFile：true/false(是否自动匹配行列高宽度)}
   		printPara.columns：grid的columns{width:列宽度，formatter:列格式化}
   		responseData.Rows
    */
    function initSpreadFormData(){
    	if(!printPara || !printPara.columns){
    		return;
    	}
    	
    	if(!responseData || !responseData.Rows){
    		return;
    	}
    	
    	var columns = [];
    	//渲染表头
     	$.each(printPara.columns, function(i, obj) {
    		if((obj.hide!=undefined && obj.hide==true) || obj.display==undefined){
    			return true;
    		}
    		
    		var newColumns='{"name":"'+obj.name+'","displayName":"'+obj.display+'"';
    		var size=120;
    		if(obj.width){
    			size=obj.width;
    		}
    		newColumns=newColumns+',"size":"'+size+'"';
    		if(obj.formatter){
    			newColumns=newColumns+',"formatter":"'+obj.formatter+'"';
    		}
    		newColumns=newColumns+'}';
    		columns.push(JSON.parse(newColumns));
    	}); 
    	
     	 
    	 var title="sheet1"
    	 if(printPara.title){
    		 title=printPara.title;
 		 }
    	 
    	 
    	 var sheet = spread.getSheet(0);
    	 //sheet.suspendPaint();
    	 sheet.name(title);//标题
    	 sheet.autoGenerateColumns = false;//是否自动生成绑定列
    	 sheet.setDataSource(responseData.Rows);
    	 sheet.bindColumns(columns);
    	 var cellCount=sheet.getColumnCount();
    	 if(printPara.autoFile){
    		 for(var i=0;i<cellCount;i++){
     			sheet.autoFitColumn(i);//自动匹配列宽度
     		}
    	    //sheet.autoFitRow(1);//自动匹配行的高度
    	 }
    	 
    	 sheet.getRange(0, 0, sheet.getRowCount(),cellCount, spreadNS.SheetArea.viewport).setBorder(new spreadNS.LineBorder("#000000", spreadNS.LineStyle.thin), {all:true},3);
    }
       
    /*
    	printPara.type=2
    	表格绑定，表头带筛选功能，需要指定数据总条数，适用于单表头
    	参数：
    	printPara{autoFile：true/false(是否自动匹配行列高宽度)}
   		printPara.columns：grid的columns{width:列宽度，formatter:列格式化}
   		responseData.Rows
   		responseData.Total
    */
    function initSpreadTableData(){
    	if(!printPara || !printPara.columns){
    		return;
    	}
    	
    	if(!responseData || !responseData.Rows){
    		return;
    	}
    	
    	if(!responseData.Total){
    		return;
    	}
    	
    	var columns = [];
    	//渲染表头
     	$.each(printPara.columns, function(i, obj) {
    		if((obj.hide!=undefined && obj.hide==true) || obj.display==undefined){
    			return true;
    		}
    		
    		var newColumns=new spreadNS.Tables.TableColumn();
    		newColumns.name(obj.display);
    		newColumns.dataField(obj.name);
    		
    		var size=120;
    		if(obj.width){
    			size=obj.width;
    		}
    		
    		if(obj.formatter){
    			
    		}
    		columns.push(newColumns);
    	});
    	
    	
    	 var title="sheet1"
    	 if(printPara.title){
    		 title=printPara.title;
 		 }
    	 var sheet = spread.getSheet(0);
    	 var cellCount=sheet.getColumnCount();
    	 sheet.setRowCount(responseData.Total, spreadNS.SheetArea.viewport);
    	 sheet.setColumnCount(cellCount, spreadNS.SheetArea.viewport);
    	 
    	 sheet.suspendPaint();
    	 sheet.name(title);//标题
    	 //冻结表头行
     	 sheet.frozenRowCount(1);
    	 
    	 var table = sheet.tables.add('tableRecords', 0, 0,responseData.Total, cellCount);
    	 table.autoGenerateColumns(false);//是否自动生成绑定列
    	 table.bindColumns(columns);
    	 table.bindingPath("Rows"); 
    	 sheet.setDataSource(new spreadNS.Bindings.CellBindingSource(responseData));
    	 if(printPara.autoFile){
    		for(var i=0;i<cellCount;i++){
    			sheet.autoFitColumn(i);//自动匹配列宽度
    		}
     	    //sheet.autoFitRow(1);//自动匹配行的高度
     	 }
    	 
    	 sheet.getRange(0, 0, 1, cellCount).font("bold 11pt 宋体");//表头字体
    	 sheet.getRange(0, 0, sheet.getRowCount(), cellCount, spreadNS.SheetArea.viewport).setBorder(new spreadNS.LineBorder("#000000", spreadNS.LineStyle.thin), {all:true},3);
    	 sheet.resumePaint();
    	 
    }
    
    
    /*
    	printPara.type=3
    	单元格级别绑定，位置随便固定，需要指定数据总条数，表头数量，适用于多表头
    	参数：
    	printPara{autoFile：true/false(是否自动匹配行列高宽度)，headCount：表头数量数}
   		printPara.columns：grid的columns{width:列宽度，formatter:列格式化}
   		responseData.Rows
   		responseData.Total
    */
    var rowIndex=0;
    var cellIndex=0;
    var columnsType3 = [];
    var formatterJson=[];
    function initSpreadColumnData(){
    	
    	if(!printPara || !printPara.columns){
    		return;
    	}
    	
    	if(!responseData || !responseData.Rows){
    		return;
    	}
    	
    	if(!printPara || !printPara.headCount){
    		return;
    	}
    	
    	var sheet = spread.getSheet(0); 
    	sheet.suspendPaint();
    	
    	//标题
    	var title="sheet1"
    	if(printPara.title){
    		title=printPara.title;
    		sheet.setValue(0,0, title);
    		sheet.getRange(0, 0, 1, 1).font("bold 18pt 宋体");
    		sheet.autoFitRow(0);//自动匹配行的高度
    		sheet.getRange(0, 0,1, 1).hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);//居中
    		rowIndex++;
    	}
    	sheet.name(title);//标题
    	
    	//渲染条件
    	if(printPara.head){
    		$.each(printPara.head, function(i, obj) {
    			
    			sheet.setValue(rowIndex,obj.cell,obj.value);
    			sheet.getRange(rowIndex, obj.cell,1, 1).font("bold 11pt 宋体");
    			sheet.autoFitRow(rowIndex);//自动匹配行的高度
    			if(obj.align){
    				if(obj.align=="center"){
    					sheet.getRange(rowIndex, obj.cell,1, 1).hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);	
    				}else if(obj.align=="right"){
    					sheet.getRange(rowIndex, obj.cell,1, 1).hAlign(spreadNS.HorizontalAlign.right).vAlign(spreadNS.VerticalAlign.right);
    				}
    				
    			}
    			if(obj.colspan){
    				//列合并
    				sheet.addSpan(rowIndex, obj.cell, 1, obj.colspan);
    			}
    			if(obj.br){
    				rowIndex++;
    			}
        	});	
    	
    	}
    	
    	//先计算列数，否则会影响数据,只计算3层
    	var coumnCount=printPara.columns.length;
    	$.each(printPara.columns, function(i, obj1) {
    		
    		if(obj1.columns){
        		coumnCount=coumnCount+obj1.columns.length;
        		$.each(obj1.columns, function(j, obj2) {
        			if(obj2.columns){
                		coumnCount=coumnCount+obj2.columns.length;
                	}
        		});
        		
        	}
    	});
        sheet.setColumnCount(coumnCount, spreadNS.SheetArea.viewport);
        
    	//渲染表头
    	$.each(printPara.columns, function(i, obj) {
    		
    		if((obj.hide!=undefined && obj.hide==true) || obj.display==undefined){
    			return true;
    		}
    		
    		sheet.setValue(rowIndex,cellIndex, obj.display);
    		
    		//列宽度
    		if(obj.width){
    			if(obj.width.toString().indexOf("%")!=-1){
    				//printPara.autoFile=true;
    				var w=obj.width.replace("%","");
    				w=$(window).width()*w/100;
    				sheet.setColumnWidth(cellIndex,w,spreadNS.SheetArea.viewport);	
    			}else{
    				sheet.setColumnWidth(cellIndex,obj.width,spreadNS.SheetArea.viewport);	
    			}
    			
    		}
    		
    		//第一层合并列
    		if(obj.columns){
    			sheet.addSpan(rowIndex, cellIndex, 1, obj.columns.length);
    			setColumnObj(sheet,obj.columns,rowIndex);
    		}else{
    			
    			//没有级次，合并行
    			if(obj.formatter){
        			formatterJson.push({name:obj.name,formatter:obj.formatter});
        		}
    			columnsType3.push(obj);
    			sheet.addSpan(rowIndex, cellIndex, printPara.headCount, 1);
    			cellIndex++;
    			
    		}
			
    		
    	});
    	
    	
    	
    	//标题合并
    	if(printPara.title){
    		sheet.addSpan(0, 0, 1, cellIndex);
    		title=printPara.title;
    	}
    	
    	
    	//列头居中
    	sheet.getRange(rowIndex, 0,printPara.headCount, cellIndex).hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    	//列头字体
		sheet.getRange(rowIndex, 0, printPara.headCount, cellIndex).font("bold 11pt 宋体");
    	
		//数据开始填充行
    	rowIndex=rowIndex+printPara.headCount;
    	//冻结表头行
    	sheet.frozenRowCount(rowIndex);
    	
    	//计算表尾行数
    	var footIndex=0;
    	if(printPara.foot){
    		$.each(printPara.foot, function(i, obj) {
    			if(obj.br){
    				footIndex++;
    			}
        	});	
    	}
    	
    	//设置行数
  	 	sheet.setRowCount(rowIndex+parseInt(responseData.Total)+footIndex, spreadNS.SheetArea.viewport);
  	 	sheet.setColumnCount(cellIndex, spreadNS.SheetArea.viewport);
  		
    	//渲染数据
    	$.each(responseData.Rows, function(i, itemObj) {
    		
    		$.each(columnsType3, function(cell, item) {
    			
    			if(itemObj[item.name]!=null && itemObj[item.name]!='null'){
    				sheet.setValue(rowIndex,cell,itemObj[item.name]);
    				//格式化金额
    				setColumnFormatter(item.name,sheet,rowIndex,cell);
    	    		
    			}else{
    				sheet.setValue(rowIndex,cell,"");
    			}
    			
    		});
    		rowIndex++;
    	});
    	
    	//渲染表尾
    	if(printPara.foot){
    		$.each(printPara.foot, function(i, obj) {
    			
    			sheet.setValue(rowIndex,obj.cell,obj.value);
    			sheet.getRange(rowIndex, obj.cell,1, 1).font("bold 11pt 宋体");
    			sheet.autoFitRow(rowIndex);//自动匹配行的高度
    			if(obj.align){
    				if(obj.align=="center"){
    					sheet.getRange(rowIndex, obj.cell,1, 1).hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);	
    				}else if(obj.align=="right"){
    					sheet.getRange(rowIndex, obj.cell,1, 1).hAlign(spreadNS.HorizontalAlign.right).vAlign(spreadNS.VerticalAlign.right);
    				}
    				
    			}
    			if(obj.colspan){
    				//列合并
    				sheet.addSpan(rowIndex, obj.cell, 1, obj.colspan);
    			}
    			if(obj.br){
    				rowIndex++;
    			}
        	});	
    	
    	}
    	
    	
    	if(printPara.autoFile){
   		 	for(var i=0;i<cellIndex;i++){
    			sheet.autoFitColumn(i);//自动匹配列宽度
    		}
   	    //sheet.autoFitRow(1);//自动匹配行的高度
   	 	}
    	
    	sheet.getRange(0, 0, rowIndex, cellIndex, spreadNS.SheetArea.viewport).setBorder(new spreadNS.LineBorder("#000000", spreadNS.LineStyle.thin), {all:true},3);
    	sheet.resumePaint();
    	
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
    
    //判断是否格式化金额
    function setColumnFormatter(name,sheet,rowIndex,cell){
    	
		$.each(formatterJson, function(i, obj) {
	    	if(obj.name==name && obj.formatter){
	    		sheet.setFormatter(rowIndex,cell,obj.formatter,spreadNS.SheetArea.viewport);
	    		return false;
	    	}
			
    	});
    }
    
    //设置合并行的表头
    function setColumnObj(sheet,columns,rowIndex){
		for(var i = 0,length = columns.length ; i < length ; i++){
			if((columns[i].hide!=undefined && columns[i].hide==true) || columns[i].display==undefined){
    			return true;
    		}
    		if(columns[i].formatter){
    			formatterJson.push({name:columns[i].name,formatter:columns[i].formatter});
    		}
    		
    		//第二层合并列
    		if(columns[i].columns){
    			sheet.addSpan(rowIndex+1, cellIndex, 1, columns[i].columns.length);
    			setColumnObj(sheet,columns[i].columns,rowIndex);
    		}else{
    			sheet.setValue(rowIndex+1,cellIndex,columns[i].display);
				
        		//列宽度
    			if(columns[i].width){
    				sheet.setColumnWidth(cellIndex,columns[i].width);
    			}
    			columnsType3.push(columns[i]);
        		cellIndex++;
    		}
		}
    	/*$.each(columns, function(i, obj) {
    		if((obj.hide!=undefined && obj.hide==true) || obj.display==undefined){
    			return true;
    		}
    		if(obj.formatter){
    			formatterJson.push({name:obj.name,formatter:obj.formatter});
    		}
    		
    		//第二层合并列
    		if(obj.columns){
    			sheet.addSpan(rowIndex+1, cellIndex, 1, obj.columns.length);
    			setColumnObj(sheet,obj.columns,rowIndex);
    		}else{
    			sheet.setValue(rowIndex+1,cellIndex,obj.display);
				debugger
        		//列宽度
    			if(obj.width){
    				sheet.setColumnWidth(cellIndex,obj.width);
    			}
    			columnsType3.push(obj);
        		cellIndex++;
    		}
    		
			
		});*/
    	
    }
    
    function myClose(){
    	dialog.close();
    }
    
    //打印
    function myPrint(){
      	var spread=spreadNS.designer.wrapper.spread;
    	if (!spread) {
            return;
        }
    	
        spread.print();
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
    
    function myOpenInfo(){
    	$.ligerDialog.open({url : 'printInfo.jsp',
    		data:{spreadNS:spreadNS,printInfo:printInfo}, height: 400,width: 500, title:'打印设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.mySave(); },cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
    }

  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="centertoolbar" ></div>
	<iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width="100%" id="spreadFrame" name="spreadFrame"></iframe>
</body>
</html>
