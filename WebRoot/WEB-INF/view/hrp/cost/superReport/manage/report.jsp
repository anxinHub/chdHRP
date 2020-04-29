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
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<link href='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/css/gc.spread.sheets.10.1.0.css' rel='stylesheet' type='text/css'/>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/gc.spread.sheets.all.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/interop/gc.spread.excelio.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/pluggable/gc.spread.sheets.print.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/lib/FileSaver.min.js' type='text/javascript'></script>

<script type="text/javascript">
var spread;
var spreadNS;
    $(function (){
		
    	$("#spread").css("height", $(window).height());
    	initSpread();
    	
    });
  
	    
    function initSpread() {
    	spread = new GC.Spread.Sheets.Workbook($('#spread')[0], { sheetCount: 1 });
     	spreadNS = GC.Spread.Sheets;
     	excelIo = new GC.Spread.Excel.IO();
    	myQuery();
    }
      
   
    //查询操作
    function myQuery(){    	
    	var para={
    			operation:"instance",
      		   	report_code:$("#report_code").val(),
      			mod_code:$("#mod_code").val(),
      			acc_year:$("#acc_year").val(),
      			acc_month:$("#acc_month").val()
      			};
    	
		ajaxJsonObjectByUrl("../querySuperReportInstance.do?isCheck=false",para,function(responseData){	
    		
   		 	try {
   			 	if (!spread) {
                    return;
                }
   			 	//没有实例数据，spreadJS控件不加载数据。
                if(para.operation=="instance" && responseData.state!=undefined && responseData.state=="no instance"){
                	return;
                }   
                spread.isPaintSuspended(true);
                spread.fromJSON(responseData);
                spread.isPaintSuspended(false);
                setPreadJSTable();
            } catch (ex) {}
         	
 		});
    }
    
    
    
    //打印
    function myPrint(){
    	spread.print();
    }
    
    //导出excel
    var excelIo;
    function exportExcel(){
    	
    	$.ligerDialog.prompt('导出文件名称', function (yes,value) {
    		if(yes && value!="") {
    	         var json = spread.toJSON();
    	           // here is excel IO API
    	         excelIo.save(json, function (blob) {
    	            saveAs(blob, value+".xlsx");
    	         }, function (e) {
    	               // process error
    	            console.log(e);
    	         });
    		}
    		
    	});
    	   
    } 
    
    //设置表格样式
    function setPreadJSTable(){
    	spread.options.grayAreaBackColor='#fff';
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
        	spread.options.newTabVisible=false;
        	sheet.options.gridline = {showVerticalGridline: false, showHorizontalGridline: false};//是否显示外边框、是否显示网格线
        	
        }
    }
    
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display:none"></div>
<input id="mod_code" type="text"  value="${mod_code}" style="display:none"/>
<input id="report_code" type="text"  value="${report_code}" style="display:none"/>
<input id="acc_year" type="text"  value="${acc_year}" style="display:none"/>
<input id="acc_month" type="text"  value="${acc_month}" style="display:none"/>	

<div id='spread' style='width:100%;'></div>
		
</body>
</html>
