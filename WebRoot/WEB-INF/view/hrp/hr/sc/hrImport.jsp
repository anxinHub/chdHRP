<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
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
   <script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<style>
.l-dialog-body {
    position: relative;
    overflow: hidden;
        width:300px !important;
}
.l-dialog-content-noimage {
    padding-left: 6px;
    padding-top: 10px;
    padding-bottom: 10px;
    color:red;
    width:300px !important;	
}



</style>
<script type = "text/javascript">

// 获取打开层的windows对象
if("${ui}"=="liger"){
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
}else{
	var parentFrameName = parent.$.etDialog.parentFrameName;

  var parentWindow = parent.window[parentFrameName];
}

// 数据对象
//var dataForImport = parentWindow.dataForImport;
var columns;
var column =[];
//var url = ${url};
// 参数
/*var para = dataForImport.para;
 var isUpdate = para.isUpdate;
 */
    $(function (){
    
    	
    	 
    		  initColumn();
    		  loadCenterTool();
    	    	$("#spreadFrame").css("height", $(window).height()-30);
    	  
    	
    });
    function initColumn(){
    	
    	  $.post('queryHrExcelColumn.do?isCheck=false',{
				'tab_code' : '${tab_code}',
				'rjt':"column"
			}, function (data) {
  		  $(data.Rows).each(function ( i,v) {
  		   var name = v.col_name;
  		 /*   if(v.required && v.required=='true'){
  			   
  			    name= name+"(必填)"
  		   } */
 			   a = {
     				   "display": name,
     				   "name":v.col_code,
     				   "width":"200",
     				   "required": v.required
     				  /*  "tab_code": v.tab_code, */
     				  
     		   };
  		  
  		   column.push(a);
       }); 
      }); 
  	  columns=column;
    	
    }
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

          var index = 0;
          sheet.suspendPaint();

          sheet.setColumnCount(columns.length, spreadNS.SheetArea.viewport);
          $.each(columns, function (i, obj) {
              if (obj.width) {
                  sheet.setColumnWidth(index, obj.width);
              } else {
                  sheet.setColumnWidth(index, 150);
              }
              if (obj.require) {
                  sheet.setText(0, index, obj.display);
                  requireJson.push(index);
              } else {
                  sheet.setText(0, index, obj.display)
              }
              index++;
          });
          sheet.getRange(0, 0, 1, 10).hAlign(spreadNS.HorizontalAlign.center);
          sheet.frozenRowCount(1);
          sheet.clearSelection();
          sheet.resumePaint();
          spread.removeSheet(1);
    }
       
    function myClose(){
          if("${ui}"=="liger"){
        	 	dialog.close();  
          }else{
        	  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
              parent.$.etDialog.close(curIndex);
          }
     
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
        //删除所有其他的sheet，值导入第1个sheet
        if (spread.getSheetCount() > 1) {
            for (var i = 1; i <= spread.getSheetCount(); i++) {
                spread.removeSheet(1);
            }
        }
        var sheet = spread.getSheet(0);
        for (var i = 1; i < sheet.getRowCount(); i++) {
            var isEmpty = true;//判断是否空行
            for (var c = 0; c < sheet.getColumnCount(); c++) {
                if (sheet.getText(i, c) != "") {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                continue;
            }
          /*   for (var j = 0; j < requireJson.length; j++) {
                if (sheet.getText(i, requireJson[j]) == "") {
                    $.etDialog.warn("第" + (parseInt(requireJson[j]) + 1) + "列，不能为空！");
                    return false;
                }
            } */
        }

     /*    var isUpdateCk = false;
        if (isUpdate && $("#isUpdate").is(':checked')) {
            isUpdateCk = true;
        }
        para["isUpdate"] = isUpdateCk; */
        var para = {"column": columns};
        var importPara = {
            content: JSON.stringify(spread.toJSON()),
            para: JSON.stringify(para),
            tab_code:"${tab_code}"
        };
 /*    	for (var i = 0; i < para.column.length; i++) {
			var thisColumn = para.column[i].display;
			//Sheet1的情况下现金出纳帐和银行出纳帐中会报错。待定
    		//var excelColumn = spread.toJSON().sheets.Sheet0.data.dataTable[0][i].value;
    		var excelColumn = spread.toJSON().sheets.Sheet1.data.dataTable[0][i].value;
    		if(thisColumn !== excelColumn){ 
    			confirm("模板错误，请重新下载模板！");
				return ;
    		}
		} */
    	 //拼接动态子集查询
  		var  arr="${tab_code}".toLowerCase().split("_");
  		 var url='importData';
  	  for(var i=1;i< arr.length;i++){//遍历字符串
  		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
  	      }
  	 var url;
  	  if("${tab_code}"!='HOS_EMP'){
  		  
  		var  arr="${tab_code}".toLowerCase().split("_");
 		  url='importData';
 		  
 	  for(var i=1;i< arr.length;i++){//遍历字符串
 		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
 	      }
         }else{
        	 
         	url='importDataHosEmp.do';
         }
  	  
        	var loadIndex = layer.load(1);
      /*   ajaxJsonObjectBylayer({
            url: ,
            data: importPara, */
            ajaxJsonObjectBylayerHr("../"+url+".do?isCheck=false",importPara,function(responseData){	
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

        	 	if(responseData.state == "false" ){
					// $.ligerDialog.open({ 	width: 400,height: 300,  showMax: true, /*showToggle: true,*/ showMin: true, isResize: true, slide: false });
					 $.ligerDialog.open({
							height : 450,
							width : 500,
							content: responseData.warn,
							modal : true,
							showToggle : false,
							showMax : false,
							showMin : true,
							isResize : true,
						
						});
					 
					 if(responseData.row_cell != undefined){
        	 			var strArr=responseData.row_cell.split(",");
        	 			for(j = 0,len=strArr.length; j < len; j++) {
        	 			   
        	 			
        	 			/* alert(responseData.row_cell);
        	 		
        	 			var json=JSON.parse(responseData.row_cell); */
        	 			//JSONArray jsonArray1 = JSONArray.fromObject(json); 
        	 			
        	 			row = parseInt(strArr[j].split("：")[0]) - 1;
            			cell = parseInt(strArr[j].split("：")[1]) - 1;
            			var redStyle = new spreadNS.Style();
            			redStyle.backColor = "red";
            	      /*   cellStyle.name = 'cellStyle';
            	        sheet.addNamedStyle(cellStyle);  */
            	        redStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
            	        redStyle.borderTop = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
            	        redStyle.borderRight = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
            	        redStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4",spreadNS.LineStyle.medium);
            	        sheet.setStyle(row, cell, redStyle, spreadNS.SheetArea.viewport);
            			sheet.setActiveCell(row, cell);
        	 			};
        	 		}
        		}else if(responseData.state == "true"){
        			grid.reload();
        		} 
        	},layer,loadIndex); 
        }
    
    </script>

</head>
<body style = "padding: 0px; overflow: hidden;">
	<div id = "pageloading" class = "l-loading" style = "display: none"></div>
	
	<div id = "centertoolbar" ></div>
	<iframe src = "<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width = "100%" id = "spreadFrame" name = "spreadFrame"></iframe>
			
</body>
</html>
