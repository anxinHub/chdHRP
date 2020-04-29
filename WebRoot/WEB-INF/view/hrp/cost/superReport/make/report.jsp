<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
*{
	margin: 0;
	padding: 0;
}

#left {
	float: left;
	width: 220px;
	 
}

#content {
	 
	margin-left: 220px;/*==等于左边栏宽度==*/
}

div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #D9D9D9;text-align: left;padding: 2px;}
div#rMenu a{
    cursor: pointer;
    list-style: none outside none;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript" ></script>

<script type="text/javascript">

//spread=document.getElementById('spreadFrame').contentWindow.GcSpread.Sheets.designer.wrapper.spread;

var spreadNS;
var tree;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

    $(function ()
    {
    	$("#layout1").ligerLayout({ leftWidth: 210,allowLeftResize: true,height:$(window).height()-30
    		,onLeftToggle: function (isColl){
    			spreadToggle(this,isColl);
        	}
            ,onEndResize: function(isColl) {
            	spreadToggle(this,false);
            }

    	});
    	loadCenterTool();
    	loadTree();
    	$("#spreadFrame").css({width:$(window).width()-210,height:$(window).height()-30,top:0});
    	$("#treeDiv").css("height", $(window).height()-30);
    });

	function spreadToggle(obj,isColl){
		if(isColl){
			$("#spreadFrame").css({position:"absolute",left:20,width:$(window).width()-20});
		}else{
			$("#spreadFrame").css({position:"absolute",left:obj.leftWidth,width:$(window).width()-obj.leftWidth});
		}
   	    spreadNS.designer.wrapper.spread.refresh();
		    	
	}

    var $tool;
    function loadCenterTool(){
   	 $tool=$("#centertoolbar").ligerToolBar({ items: [   	      
   	       {text: '保存', id:'save', icon:'save', click: mySave},
   	       { line:true },
	       { text: '自定义函数',id:'account',icon:'account', click: myCellSet},
	       /*
	       { line:true },
	       { text: '设置查询条件',id:'search2',icon:'search2', click: mywhereSet},
	       */
	       { line:true },
   	       { text: '隐藏工具栏',id:'up',icon:'up', click: hidenSpreadMenu},
   	       { line:true },
   	       { text: '参数设置', id:'cashier', icon:'cashier', click: getDs},
   	       { line:true },
	       { text: '载入内置模板', id:'down', icon:'down', click: initReport},
   	       { line:true },
   	       { text: '打印', id:'print', icon:'print', click: myPrint},
	       { line:true },
	       { text: '关闭',id:'candle.',icon:'candle', click: myClose}
   	       ]
   	    });
   } 
    function setTop(){
    	
    }
    function setCell(value){
    	var spread=spreadNS.designer.wrapper.spread;
    	var sheet = spread.getActiveSheet();
    	var activeRowIndex = sheet.getActiveRowIndex();
    	var activeColumnIndex = sheet.getActiveColumnIndex();
    	var node = tree.getSelectedNodes()[0];
     	sheet.setText(activeRowIndex, activeColumnIndex, "{"+node.name+"\n["+node.pId+"."+node.id+"]}");
     	sheet.comments.add(activeRowIndex, activeColumnIndex, "{page.body}");
    	//sheet.comments.add(activeRowIndex, activeColumnIndex, $("#formula_name").val().replace(/\\/g,""));
    }
    //自定义函数
    function myCellSet(){
    	var spread=spreadNS.designer.wrapper.spread;
    	var sheet = spread.getActiveSheet();
    	$.ligerDialog.open({url : 'cellSetPage.do?isCheck=false&mod_code='+$("#mod_code").val(),
    		data:{sheet:sheet,spreadNS:spreadNS}, height: $(window).height()-10,width: $(window).width()-10, title:'自定义函数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false
    		});
    	 
    }
    
    //设置查询条件
    function mywhereSet(){
    	
    }
   
    //保存报表内容
    function mySave(){
    	
    	if($("#report_code").val()==""){
    		$.ligerDialog.error("报表编码不能为空！");
			return false;
    	}
    	
    	var spread=spreadNS.designer.wrapper.spread;
    	var para={
    			content:JSON.stringify(spread.toJSON()),
    			report_code:$("#report_code").val(),
    			mod_code:$("#mod_code").val()
    	};
    	
    	var loadIndex = layer.load(1);
    	
    	$.ajax({
             type: 'POST',
             url: '../updateSuperReportContent.do?isCheck=false',
             dataType: 'json',
             contentType:'application/json',
             data:JSON.stringify(para),
             success:function(responseData){
            	if (responseData.error) {
 					$.ligerDialog.error(responseData.error);
 				}else if (responseData.msg) {
            		$.ligerDialog.success(responseData.msg);
 				}
             },error:function(textStatus, errorThrown){
            	 $.ligerDialog.error('操作失败.');
             },complete:function(){
            	 layer.close(loadIndex);
             }
        }); 
    }
   
    var printInfo;
    var excelIo;
    function initSpreadTable() {
    	
    	//spread = new GcSpread.Sheets.Spread($('#spread')[0], { sheetCount: 1 });
       // spreadNS = GcSpread.Sheets;
      //sheet.setName('Sheet1');
       // sheet = spread.getSheet(0);
       //spread.removeSheet(0);
        
        //spread.clearSheets();
		//var sheet = spreadNS.Sheet('Sheet1');
		//spread.addSheet(0, sheet);
        /*   var source = [
            { Course: "Calculus", Term: 1, Credit: 5, Score: 80, Teacher: "Nancy.Feehafer" },
            { Course: "P.E.", Term: 1, Credit: 3.5, Score: 85, Teacher: "Andrew.Cencini" },
            { Course: "Political Economics", Term: 1, Credit: 3.5, Score: 95, Teacher: "Jan.Kotas" },
            { Course: "Basic of Computer", Term: 1, Credit: 2, Score: 85, Teacher: "Steven.Thorpe" },
            { Course: "Micro-Economics", Term: 1, Credit: 4, Score: 62, Teacher: "Jan.Kotas" },
            { Course: "Linear Algebra", Term: 2, Credit: 5, Score: 73, Teacher: "Nancy.Feehafer" },
            { Course: "Accounting", Term: 2, Credit: 3.5, Score: 86, Teacher: "Nancy.Feehafer" },
            { Course: "Statistics", Term: 2, Credit: 5, Score: 85, Teacher: "Robert.Zare" },
            { Course: "Marketing", Term: 2, Credit: 4, Score: 70, Teacher: "Laura.Giussani" }
        ];
        spread.isPaintSuspended(true);
        var table = sheet.addTableByDataSource("Table1", 2, 1, source, spreadNS.TableStyles.medium2());
        table.showFooter(true);
        table.showHeader(true);
        table.highlightFirstColumn(true);
        table.highlightLastColumn(false);
        table.setColumnFormula(2, "=SUM(D4:D12)");
        table.setColumnFormula(3, "=SUM(E4:E12)");
        table.setColumnValue(0, "Total");
        sheet.setColumnWidth(0, 20);
        sheet.setColumnWidth(1, 130);
        sheet.setColumnWidth(2, 70);
        sheet.setColumnWidth(3, 70);
        sheet.setColumnWidth(4, 70);
        sheet.setColumnWidth(5, 100);
     	
        //console.log(sheet.getArray(0, 0,13,6))
       // console.log(JSON.stringify(sheet.toJSON()));//取json数据
        
     */
     
    	var para={
    			mod_code:$("#mod_code").val(),
    			report_code:$("#report_code").val(),
    		};
    	ajaxJsonObjectByUrl("../querySuperReportContentByCode.do?isCheck=false",para,function(responseData){
    		try {
    			
    			var spread=spreadNS.designer.wrapper.spread;
    			if (!spread) {
                   return;
                }
    			
                spread.isPaintSuspended(true);
                spread.fromJSON(responseData);
                spread.isPaintSuspended(false);                
                
                /*************开始设置打印信息*******************
        	   	printInfo = new spreadNS.Print.PrintInfo();  
        	//	printInfo.paperSize(new spreadNS.Print.PaperSize("100%", "100%"));
        		printInfo.paperSize(new spreadNS.Print.PaperSize(spreadNS.Print.PaperKind.a4));
        		printInfo.showBorder(true);//是否打印控件的外边框线
                printInfo.showGridLine(false);//是否打印网格线 (默认是打印)

        		//页码
        	//	printInfo.headerLeftImage("");
        		printInfo.headerLeft("");
        		printInfo.headerCenter("");
        		printInfo.headerRight("&P/&N");
        		printInfo.footerLeft("制表人：${sessionScope.user_name }");
        		printInfo.footerCenter("");
        		printInfo.footerRight("打印日期：&D"); 
            
        		printInfo.showRowHeader(spreadNS.Print.PrintVisibilityType.hide);
        		printInfo.showColumnHeader(spreadNS.Print.PrintVisibilityType.hide);
        		
        		printInfo.margin({top:20,bottom:20,left:0,right:0,header:0,footer:0});
        		//printInfo.margin({top:75, bottom:75, left:20, right:20, header:10, footer:20});
        		  window.setTimeout(function() {
        			  //printInfo.zoomFactor(1);//缩放大小
        			  printInfo.qualityFactor(3);//设置纸张质量因子
        		      var sheet = spread.getSheet(0);
        		      sheet.printInfo(printInfo);
                  }, 300);
        		  ************结束设置打印信息********************/
        		  
            	/*
        		//spread.removeSheet(0);
        		spread.clearSheets();
        		sheet = new GcSpread.Sheets.Sheet(node.name);
        		spread.addSheet(0, sheet);

        		spread.isPaintSuspended(true);
              	sheet.fromJSON(responseData.sheets[node.name]);
              	//sheet.setName(node.name);
              	spread.isPaintSuspended(false);
              	*/
              	
            } catch (ex) {}
    		
    	},false); 
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
    
    function myPrint(){
    	var spread=spreadNS.designer.wrapper.spread;
    	//console.log(JSON.stringify(spread.toJSON()));
    	
    	var sheetName="";
    	for(var i=0;i<spread.getSheetCount();i++){
    		//var sheet = spread.getActiveSheet();
    		var sheet = spread.getSheet(i);
        	sheet.clearSelection();
			if(i==0){
				sheetName=sheet.name();
			}    		
    	}
    	
    	var loadIndex = layer.load(1);
    	excelIo.save(spread.toJSON({includeBindingSource: true}),function(blob){
    		var para={
    				'url':'PageOffice/createReportTempFile.do?isCheck=false',
    				'report_code':$("#report_code").val(),
    				'sheet_name':sheetName,
    				'openFlag':'template'
    		};
			officePrintReport(para,blob,function(){
				layer.close(loadIndex);
			});
		});
    	
    	
    	//spread.print();
    	
/*     	spreadPrint({
			spread:spread.toJSON(),
			head:false
		});
		return;  */
    	
    	/* var pathName=window.location.pathname;
   		pathName=pathName.replace(".do","").replace(/\//g,'{').replace("{CHD-HRP{hrp{","");
   		
		lodopSpread({
			spread:spread.toJSON(),
			title:"报表打印",
			taskName:pathName,
			foot:{user:parent.sessionJson.user_name,date:true}
		}); */
    }
	    function getDs(){   	
	    	var dsNames=new Set();
	    	var spread=spreadNS.designer.wrapper.spread;
	    	var sheetName="";	
	    	 sheet = spread.getActiveSheet();
	    	 var jsonString=JSON.stringify(sheet.toJSON())
	    	 var json = $.parseJSON(jsonString);
	    	 
	    	 var dsRow="";
	    	 $.each(json.comments, function (i, n){
	    		 if(n.text=='{page.body}'&&dsRow.indexOf(n.rowIndex)==-1)
	    			 dsRow+=n.rowIndex+",";
	    	 });
	    	 var tableString =JSON.stringify(json.data.dataTable);
	    	 var tableJson= eval('(' + tableString + ')');  

	    	// dsRow=dsRow.replace(",","");
	    	 var dsName="";
	    	 var a=dsRow.split(",");
	    	 for(var i=0;i<a.length-1;i++){ 
	    		 	var rr=a[i];
	    		 	var rowData=tableJson[rr];
	    		 	
	    		 	for (var col in rowData) {
	    		 		
	    		 		var colData=rowData[col].value;
	    				    if(typeof(colData)!='undefined'&&colData!=null){
	    				    	
	    				    	colData=colData.substring(colData.indexOf("[")+1,colData.indexOf("."));
	    				    	dsNames.add(colData);     //获取key值
	    				    }
	    			}  
	    	 }
	    	 for (var x of dsNames) { // 遍历Set
	    		 dsName+=x+",";
	    		}
	     	$.ligerDialog.open({url : 'dsParaSetPage.do?isCheck=false&mod_code='+$("#mod_code").val()+"&dsNames="+dsName,
	     		data:{dsName:dsNames}, height: $(window).height()-10,width: $(window).width()-10, title:'自定义函数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false
	     		});
	    	// return dsNames;
	    }
	    
    function myClose(){
    	dialog.close();
    }
    
    //加载树形结构
    function loadTree(){
    	var setting = {      
 		   		data: {simpleData: {enable: true},key: {title: "title"}},
 		   		treeNode:{open:true},
 		   		callback:{onClick: setCell,beforeClick: zTreeBeforeClick}
 	 	};

    	ajaxJsonObjectByUrl("queryMakeSuperReportDsManager.do?isCheck=false",{},function(responseData){	
			  tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
		},false);
    }
    
    //验证选中的树节点是否是父节点
    function zTreeBeforeClick(treeId, treeNode, clickFlag) {
    	if (treeNode.isParent) {
			return false;
		}
    };
    
    function setAnnotation(name){
    	var spread=spreadNS.designer.wrapper.spread;
    	var sheet = spread.getActiveSheet();
    	var index = sheet.getSelections()[0];
    	for (var i = 0; i < index.colCount; i++) {
			for (var j = 0; j < index.rowCount; j++) {
		    	sheet.comments.add(j+index.row, i+index.col, name);
			}
		}
    }
    
    function removeAnnotation(){
    	var spread=spreadNS.designer.wrapper.spread;
    	var sheet = spread.getActiveSheet();
    	var index = sheet.getSelections()[0];
    	for (var i = 0; i < index.colCount; i++) {
			for (var j = 0; j < index.rowCount; j++) {
		    	sheet.comments.remove(j+index.row, i+index.col);
			}
		}
    }
    
    function initReport(){
    	
    	$.ajax({
            url: '<%=path%>'+"/lib/SpreadJS10/Spread.Sheets.Designer.10/src/resources/templates/"+$("#mod_code").val()+"/" + $("#report_code").val() + ".ssjson",
            //async: false,
            type: 'GET',
            dataType: 'json',
            success: function(responseData) {
            	try{
	            	var spread=spreadNS.designer.wrapper.spread;
	    			if (!spread) {
	                   return;
	                }
	    			
	                spread.isPaintSuspended(true);
	                spread.fromJSON(responseData);
	                spread.isPaintSuspended(false);
            	} catch (ex) {}
            },
            error: function(XMLHttpRequest, textStatus) {
            	if(textStatus=="error"){
            		$.ligerDialog.error('系统没有内置模板.');
            	}
            }
        });
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input id="mod_code" type="text"  value="${mod_code}" style="display:none"/>
	<input id="report_code" type="text"  value="${report_code}" style="display:none"/>
	
	<div id="centertoolbar"></div>
	<div class="l-layout" id="layout1">
		<!-- div position="top">
			
		</div-->
	
		<div position="left" title="数据集列表">
		    <div  style="overflow:auto;" id="treeDiv">
		       	<ul class="ztree"  id="tree"></ul>
		    </div>
	    </div>
		<div id="content">
			<iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" style="position:absolute;left:210px;" id="spreadFrame" name="spreadFrame"></iframe>
		</div>
		 	
	 	<div id="rMenu">
		    <a href="#" class="list-group-item" id="setParam">参数设置</a>
		</div>
	</div>
</body>
</html>
