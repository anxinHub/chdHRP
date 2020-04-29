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
var columns=dialog.get("data").columns;
var rowIndex=dialog.get("data").rowIndex;
var grid=dialog.get("data").grid;
var out_code=dialog.get("data").out_code;
    $(function ()
    {
    	loadCenterTool();
    	$("#spreadFrame").css("height", $(window).height()-30);
         
    });

    var $tool;
    function loadCenterTool(){
   	 $tool=$("#centertoolbar").ligerToolBar({ items: [ 
		 	{ text: '选择文件',id:'openFileDialog.',icon:'uploadzip', click: openFileDialog},
			{ line:true },
			{ text: '导出Excel',id:'down.',icon:'down', click: exportExcel},
			{ line:true },
			{
				text: '是否追加导入：<input type="checkbox" id="isAppand"/>', id: 'isAppandC', click: isAppandData, icon: ''
			},
			{ line:true },
   	        {text: '确定', id:'save', icon:'save', click: mySave},   	   	    	
   	        { line:true },
	        { text: '关闭',id:'candle.',icon:'candle', click: myClose}
   	        ]
   	    });
   } 
   
    function isAppandData(obj){
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
    function initSpreadData(){
    	var spread = spreadNS.designer.wrapper.spread;
    	var sheet = spread.getSheet(0);
    	if(!columns){
    		return;
    	}
    	
    	var index=0;
    	sheet.suspendPaint();
    	
    	//先计算列数，否则会影响数据
    	var coumnCount=0;
    	$.each(columns,function(i,obj){
    		if(!obj.hide){
    			coumnCount++;
    		}
    	});
    	sheet.setColumnCount(coumnCount, spreadNS.SheetArea.viewport);
    	
    	$.each(columns,function(i,obj){
    		if(!obj.hide){
    			if(obj.display=="摘要"){
    				 sheet.setColumnWidth(index, 300);
    	    		 sheet.setText(0,index, obj.display);
    			}else if(obj.display=="金额"){
	   				 sheet.setColumnWidth(index, 150);
		    		 sheet.setText(0,index, obj.display);
				}else{
    				 sheet.setColumnWidth(index, 200);
    	    		 sheet.setText(0,index, obj.display);
    			}
    			index++;
    		}
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
    	var sheet = spread.getSheet(0);
    	if(sheet.getText(1,0)==""){
    		$.ligerDialog.error("摘要不能为空！");
			return false;
    	}
    
     	var para={
    			content:JSON.stringify(spread.toJSON()),
    			columns:JSON.stringify(columns),
    			rowIndex:rowIndex,
    			out_code:out_code
    	};
    	var loadIndex = layer.load(1);
    	ajaxJsonObjectBylayer("importCheck.do?isCheck=false",para,function(responseData){	
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
    		}else if(responseData.state=="true"){
    			//重置行号
    			rowIndex=parseInt(responseData.rowIndex);
    			//表格渲染
    			if($("#isAppand").is(':checked')){
    				//追加导入
    				var $tbody = $(".sensei-grid-tbody", grid.$el);
    				$.each(responseData.Rows, function (i,item) {
        				if(item["id"]){
			                 var tr = grid.renderRow(item, true);
			                 $(tr).attr("style","position:relative;z-index:1;");
			                 $tbody.append(tr);
			              // grid.setRowSaved($(tr));
	        			}
            		}); 
    			    
    				$.each(grid.getGridData(),function (i,obj) {
    					var $row=grid.getRowByIndex(i);
    					if($row.find(">td.selectable").html()==undefined || $row.find(">td.selectable").html().indexOf("checkbox")==-1){
    						grid.removeRow($row);//删除空数据
    					}
    				});
    				//在最后一行插入一条空行
    				rowIndex=rowIndex+1;
    				var tr = grid.renderRow(null, false);
  		            tr.cells[1].innerText=rowIndex;//行号
  		            $tbody.append(tr);
    				grid.bindEvents();
    			}else{
    				//清空导入
    				grid.renderData(responseData.Rows);
    			}
    			
    			//$.ligerDialog.success("导入成功，需要保存页面！");
    			grid.sumMoney();
    			frameElement.dialog.close();
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
