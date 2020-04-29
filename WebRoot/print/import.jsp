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
var para=dialog.get("data").para;
var columns=para.column;
var url=dialog.get("data").url;
var query = dialog.get("data").fun;
var isUpdate = para.isUpdate === undefined ? false : para.isUpdate;
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
    
    if(isUpdate){
    	item.push({ line:true });
		item.push({ text: '是否覆盖：<input type="checkbox" id="isUpdate"/>',id:'isUpdate',icon:'', click: isUpdateData});
    }
    
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

    	var index=0;
    	sheet.suspendPaint();
    	
    	sheet.setColumnCount(columns.length, spreadNS.SheetArea.viewport);
    	$.each(columns,function(i,obj){
    		
    		if(obj.width){
    			sheet.setColumnWidth(index, obj.width);
    		}else{
    			sheet.setColumnWidth(index, 150);
    		}
    		
    		if(obj.require){
    			sheet.setText(0,index, obj.display);
    			requireJson.push(index);
    		}else{
    			sheet.setText(0,index, obj.display)
    		}
    	    
    		index++;
    		
    	});
    	sheet.getRange(0, 0, 1, 10).hAlign(spreadNS.HorizontalAlign.center);
    	sheet.frozenRowCount(1);
    	sheet.clearSelection();
    	sheet.resumePaint();
    	spread.removeSheet(1);
    	/* var table = sheet.addTable('tableRecords', 4, 1,1, 3);
		//table.showHeader(false);
		table.bandRows(false);
		table.autoGenerateColumns(false);
			
		names.forEach(function (name, index) {
			 var tableColumn = new GcSpread.Sheets.TableColumnInfo();
			 tableColumn.name(displays[index]);
			 tableColumn.dataField(name);
			 tableColumns.push(tableColumn);
		});
			
			
		table.bindColumns(tableColumns);
	//	table.bindingPath('sales');
		source = new GcSpread.Sheets.CellBindingSource(data);
		sheet.setDataSource(source); */
			
    
    }
       
    function myClose(){
    	dialog.close();
    	if(query){
    		query();
    	}
    }
   
    function openFileDialog(){
    	var spread=spreadNS.designer.wrapper.spread;
    	document.getElementById('spreadFrame').contentWindow.openFileDialog(spread,{isSavedWarning:false});
    }

    function exportExcel(){
    	document.getElementById('spreadFrame').contentWindow.exportExcel(spreadNS.designer.wrapper.spread,null);
    }
    
    //确定
    var row=null;
    var cell=null;
    function mySave(){
    	
     	var spread=spreadNS.designer.wrapper.spread;
     	//删除所有其他的sheet，值导入第1个sheet
     	if(spread.getSheetCount()>1){
     		for(var i=1;i<=spread.getSheetCount();i++){
     			spread.removeSheet(1);
     		}
     	}
     	
    	var sheet = spread.getSheet(0);
    	
    	for(var i=1;i<sheet.getRowCount();i++){
    		var isEmpty=true;//判断是否空行
    		for(var c=0;c<sheet.getColumnCount();c++){
    			if(sheet.getText(i,c)!=""){
    				isEmpty=false;
    				break;
    			}
    		}
    		
    		if(isEmpty){
    			continue;
    		}
    		
  			for(var j=0;j<requireJson.length;j++){
  				if(sheet.getText(i,requireJson[j])==""){
  		    		$.ligerDialog.warn("第"+(parseInt(requireJson[j])+1)+"列，不能为空！");
  					return false;
  		    	}
  			}
  		}
    	
    	var isUpdateCk=false;
   		if(isUpdate && $("#isUpdate").is(':checked')){
   			isUpdateCk=true;
       	}
   		para["isUpdate"]=isUpdateCk;
    	
   		
    	var importPara={
    		content:JSON.stringify(spread.toJSON()),
    		para:JSON.stringify(para)
    	};
    	
    	for (var i = 0; i < para.column.length; i++) {
			var thisColumn = para.column[i].display;
			//Sheet1的情况下现金出纳帐和银行出纳帐中会报错。待定
    		//var excelColumn = spread.toJSON().sheets.Sheet0.data.dataTable[0][i].value;
    		var excelColumn = spread.toJSON().sheets.Sheet1.data.dataTable[0][i].value;
    		if(thisColumn !== excelColumn){ 
    			confirm("模板错误，请重新下载模板！");
				return ;
    		}
		}
    	var loadIndex = layer.load(1);
    	ajaxJsonObjectBylayer("..//"+url,importPara,function(responseData){	
    		layer.close(loadIndex);
    		
    		if(row!=null){
    			var whiteStyle = new spreadNS.Style();
    			whiteStyle.backColor = "white";
    			whiteStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderTop = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderRight = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    			whiteStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
        		sheet.setStyle(row, cell, whiteStyle, spreadNS.SheetArea.viewport);
    		}

    	 	if(responseData.state=="false" && responseData.row_cell!=undefined){
    			row=parseInt(responseData.row_cell.split("：")[0])-1;
    			cell=parseInt(responseData.row_cell.split("：")[1])-1;
    			var redStyle = new spreadNS.Style();
    			redStyle.backColor = "red";
    	        /* cellStyle.name = 'cellStyle';
    	        sheet.addNamedStyle(cellStyle); */
    	        redStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        redStyle.borderTop = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        redStyle.borderRight = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        redStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
    	        sheet.setStyle(row, cell, redStyle, spreadNS.SheetArea.viewport);
    			sheet.setActiveCell(row,cell);
    		}
    		
		},layer,loadIndex); 
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
	<div id="centertoolbar" ></div>
	<iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width="100%" id="spreadFrame" name="spreadFrame"></iframe>
		
</body>
</html>
