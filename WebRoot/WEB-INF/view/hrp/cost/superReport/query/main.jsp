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
<link href='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/css/gc.spread.sheets.excel2013white.10.1.0.css' rel='stylesheet' type='text/css'/>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/gc.spread.sheets.all.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/interop/gc.spread.excelio.10.1.0.min.js' type='text/javascript'></script>
<script src="<%=path%>/lib/SpreadJS10/license.js" type="text/javascript"></script>
<link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript" ></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<style>
        .info {
            padding: 5px 10px;
            position:relative;
            /*height: 500px;*/
            display: none;/*避免闪动初始规定不显示*/
        }
</style>
    
<script type="text/javascript">
var spread;
var spreadNS;
var tree;
var setting;

    $(function (){
		
		$("#layout1").ligerLayout({ leftWidth: 210,allowLeftResize: true
			,onLeftToggle: function (isColl){
    			spreadToggle(this,isColl);
        	}
            ,onEndResize: function(isColl) {
            	spreadToggle(this,false);
            }	
		});
    	setting = {      
 		   		data: {simpleData: {enable: true},key: {title: "title"}},
 		   		treeNode:{open:true},
 		   		callback:{onClick:myClick}
 		   		
 	 	};
    	$("#treeDiv").css("height", $(window).height()-28);
    	loadTree();
    	initSpread();
    	$("#mainHead").ktLayer({
             // 参数配置
             direction:"down",
             BtnbgImg:{open:'<%=path%>/lib/hrp/acc/superReport/open.png',close:'<%=path%>/lib/hrp/acc/superReport/close.png'},
             speed:"100",
            // bgColor:"#ccc",//背景颜色
             closeHeight:0,//关闭状态高度
             Descript:["查询条件","收起条件"],//展开收起描述
	    	 open:function(){
	        	 
	         }
         });
    	 $(':button').ligerButton({width:90});
    });
    
    function spreadToggle(obj,isColl){
		if(isColl){
			$("#spread").css({position:"absolute",width:$(window).width()-20});
		}else{
			$("#spread").css({position:"absolute",width:$(window).width()-obj.leftWidth});
		}
		spread.refresh();
	}
    
    function loadTree(){
    	var para={
				mod_code:$("#mod_code").val()    			
    	};
		ajaxJsonObjectByUrl("../queryAccSuperReportByPerm.do?isCheck=false",para,function(responseData){	
			  tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
		});
    }
    
	function myClick(){
		var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null){
    		return false;
    	}
    	
    	//查询报表条件
		var para={
			report_type:node.report_type    			
    	};
		ajaxJsonObjectByUrl("../queryAccSuperReportByWhere.do?isCheck=false",para,function(responseData){
			var curMonth;
			if(responseData.minDate=="0"){
				$("#acc_year").attr("disabled","disabled");
				$.ligerDialog.error("请维护会计年度！");
				return false;
			}
			
			$("#acc_year").val(responseData.curDate.substring(0,5));
			$("#minDate").val(responseData.minDate);
			$("#maxDate").val(responseData.maxDate);
			curMonth=parseInt(responseData.curDate.substring(5,7));
			
			if(node.report_type==2 || node.report_type==3 || node.report_type==4 || node.report_type==5){
				//月报、季报、半年报、年报
				$("#acc_report_tab").css("display","block");
				if(node.report_type==2){
					//月报
					$("#period_td").css("display","block");
					accYearFunc($("#acc_year").val(),responseData.curDate.substring(5,7));
				}else if(node.report_type==3){
					//季报
					var selVal=14;
					if(curMonth>=4 && curMonth<=6)selVal=15;
					else if(curMonth>=7 && curMonth<=9)selVal=16;
					else if(curMonth>=10 && curMonth<=12)selVal=17;
					
					$("#period_td").css("display","block");
					$("#period").ligerComboBox({
		                width : 150,
		                data: [
		                    { text: '第一季度', id: '14' },
		                    { text: '第二季度', id: '15' },
		                    { text: '第三季度', id: '16' },
		                    { text: '第四季度', id: '17' }
		                ],
		                value: selVal,cancelable: false
		            });
					
				}else if(node.report_type==4){
					//半年报
					var selVal=18;
					if(curMonth>=7 && curMonth<=12)selVal=19;
					
					$("#period_td").css("display","block");
					$("#period").ligerComboBox({
		                width : 150,
		                data: [
		                    { text: '上半年', id: '18' },
		                    { text: '下半年', id: '19' }
		                ],  
		                value: selVal,cancelable: false
		            });
					
				}else if(node.report_type==5){
					//年报
					$("#period_td").css("display","none");
					
				}
				
				
			}else{
				//普通报表
				$("#acc_report_tab").css("display","none");
			}
			
		},false);
		
		var operation="define";//默认查模板
		var accYear="";
		var accMonth="";
		if(node.report_type==2 || node.report_type==3 || node.report_type==4 || node.report_type==5){
			//月报、季报、半年报、年报
			if($("#acc_year").val()!=""){
				accYear=$("#acc_year").val().substring(0,4);
        	}
    		
    		accMonth=liger.get("period").getValue();
    		if(node.report_type==5){
        		accMonth="20";//年报
        	}
    		
		}
		
		var operation="instance";//查报表实例
		if(accYear=="" || accMonth==""){
			operation="define";//默认查模板
		}
		//查询报表
		var para={
    			operation:operation,
      		   	report_code:node.id,
      			mod_code:$("#mod_code").val(),
      			acc_year:accYear,
      			acc_month:accMonth
      	};
		queryReport(para);
    }

	//年度发生改变，查询月份
	function accYearFunc(newDateStr,curDate){
		if(curDate==undefined){
			curDate=liger.get("period").getValue();
		}
		var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null){
    		return false;
    	}
    	
    	if(node.report_type!=2){
			return;
		}
    	
		if(newDateStr==""){
			return;
		}
		
		$("#period").ligerComboBox({
			parms : {acc_year:newDateStr.substring(0,4)},
		   	url: '../queryYearMonthByAccYearSelect.do?isCheck=false',
	        width : 150,
	        valueField: 'id',
	      	textField: 'text', 
	        cancelable: false,
	        async:false,
	        onSuccess: function (data) {
				if (data.length > 0) {
					if(curDate!=""){
						this.setValue(curDate);
					}else if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
						this.setValue(data[0].id);
					}
				}
			}
	       });
	}
	
    
    function initSpread() {
    	$("#spread").css("height", $(window).height());
    	spread = new GC.Spread.Sheets.Workbook($('#spread')[0], { sheetCount: 1 });
        spreadNS = GC.Spread.Sheets;
        excelIo = new GC.Spread.Excel.IO();
    }
    
    function loadCenterTool(){
   	 $("#centertoolbar").ligerToolBar({ items: [
   	       {text: '查询', id:'search', icon:'search', click: myQuery},
   	       { line:true },
   	       {text: '生成', id:'blabel', icon:'blabel', click: myCreate},
	       { line:true },
   	       { text: '打印',id:'print',icon:'print', click: myPrint}
   	       ]
   	    });
   } 
   
    //查询操作
    function myQuery(){
    	if($("#acc_year").val()==""){
    		$.ligerDialog.error("请维护会计年度！");
    		return false;
    	}
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null){
    		$.ligerDialog.error("请选择报表！");
    		return false;
    	}
    	
    	var accYear="";
    	var accMonth="";
    	if(node.report_type==2 || node.report_type==3 || node.report_type==4 || node.report_type==5){
    		//月报、季报、半年报、年报
    		if($("#acc_year").val()==""){
        		$.ligerDialog.error("请选择会计年度！");
        		return false;
        	}
    		accYear=$("#acc_year").val().substring(0,4);
    		if(node.report_type==5){
    			//年报
        		accMonth="20";
        	}else{
        		if($("#period").val()==""){
            		$.ligerDialog.error("请选择期间！");
            		return false;
            	}
	    		accMonth=liger.get("period").getValue();
        	}
    	}
    	
    	var para={
    			operation:"instance",
      		   	report_code:node.id,
      			mod_code:$("#mod_code").val(),
      			acc_year:accYear,
      			acc_month:accMonth
      			};
    	queryReport(para);
    }
    
    /*
    	查询报表。
    	如果点击报表，查询报表定义模板。
    	如果点击查询按钮，查询报表实例数据，没有实例数据，spreadJS控件不加载数据。
    */
    var paraSetParam;
    function queryReport(para){
    	ajaxJsonObjectByUrl("../querySuperReportInstance.do",para,function(responseData){	
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
                setPreadJSTable();
                spread.isPaintSuspended(false); 
                paraSetParam = responseData.sheets.Sheet1.data.dataTable;
            } catch (ex) {}
         	
            
 		});
    }
    
    //生成
    function myCreate(){
    	
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null){
    		$.ligerDialog.error("请选择报表！");
			return false;
    	}
    	
    	var accYear="";
    	var accMonth="";
    	if(node.report_type==2 || node.report_type==3 || node.report_type==4 || node.report_type==5){
    		//月报、季报、半年报、年报
    		if($("#acc_year").val()==""){
        		$.ligerDialog.error("请选择会计年度！");
        		return false;
        	}
    		accYear=$("#acc_year").val().substring(0,4);
    		if(node.report_type==5){
    			//年报
        		accMonth="20";
        	}else{
        		if($("#period").val()==""){
            		$.ligerDialog.error("请选择期间！");
            		return false;
            	}
        		accMonth=liger.get("period").getValue();
        	}
    	}
    	
    	$.ligerDialog.confirm('确定要生成报表吗？', function(yes) {
    		if (yes) {
    			
   				var para={
       	      		   	report_code:node.id,
       	      			mod_code:$("#mod_code").val(),
       	      			acc_year:accYear,
       	      			acc_month:accMonth
       	      	};
       			var loadIndex = layer.load(1);
       			ajaxJsonObjectBylayer("../saveSuperReportInstance.do",para,function(responseData){	
       	    		
       				try {
       	    			 if (!spread) {
       	    				 layer.close(loadIndex);
       	                     return;
       	                 }
       	                
       	                 spread.isPaintSuspended(true);
       	                 spread.fromJSON(responseData);
       	                 setPreadJSTable();
       	                 spread.isPaintSuspended(false);
       	                 layer.close(loadIndex);
       	                 //保存计算完后的报表内容，为了取报表单元格的数据
       	                 console.log(JSON.stringify(spread.toJSON()));
       	                 para={
       	             			
       	             			report_code:node.id,
       	    	      			mod_code:$("#mod_code").val(),
       	    	      			acc_year:accYear,
       	    	      			acc_month:accMonth,
       	    	      			content:JSON.stringify(spread.toJSON())
       	             		};
       	                 ajaxJsonObjectByUrl("../saveSuperReportContent.do?isCheck=false",para);
       	                 
       	             } catch (ex) {
       	            	 layer.close(loadIndex);
       	             }
       	          	
       	  		},layer,loadIndex);
   			}
    			
    		
    	});
    	
    }
    
    //保存
    function mySave(){
    	debugger;
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null){
    		$.ligerDialog.error("请选择报表！");
			return false;
    	}
    	
    	var accYear="";
    	var accMonth="";
    	if(node.report_type==2 || node.report_type==3 || node.report_type==4 || node.report_type==5){
    		//月报、季报、半年报、年报
    		if($("#acc_year").val()==""){
        		$.ligerDialog.error("请选择会计年度！");
        		return false;
        	}
    		accYear=$("#acc_year").val().substring(0,4);
    		if(node.report_type==5){
    			//年报
        		accMonth="20";
        	}else{
        		if($("#period").val()==""){
            		$.ligerDialog.error("请选择期间！");
            		return false;
            	}
        		accMonth=liger.get("period").getValue();
        	}
    	}
    		
		var para={
    		report_code:node.id,
  			mod_code:$("#mod_code").val(),
  			acc_year:accYear,
  			acc_month:accMonth,
  			content:JSON.stringify(spread.toJSON())
    		};
        ajaxJsonObjectByUrl("../saveSuperReportContent.do?isCheck=false",para,function(){
       	 $.ligerDialog.success("保存成功！");
        });
              
    			
    	
    	
    }
  
    
    //打印
    function myPrint(){
    	//spread.print();
 		var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null){
    		$.ligerDialog.error("请选择报表！");
			return false;
    	}
    	
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
    				'url':'PageOffice/createReportFile.do?isCheck=false',
    				'report_code':node.id,
    				'sheet_name':sheetName,
    				'openFlag':'query'
    		};
			officePrintReport(para,blob,function(){
				layer.close(loadIndex);
			});
		});
    	
    /* 	var accYear="";
    	var accMonth="";
    	if(node.report_type==2 || node.report_type==3 || node.report_type==4 || node.report_type==5){
    		//月报、季报、半年报、年报
    		if($("#acc_year").val()==""){
        		$.ligerDialog.error("请选择会计年度！");
        		return false;
        	}
    		if($("#period").val()==""){
        		$.ligerDialog.error("请选择期间！");
        		return false;
        	}
    		accYear=$("#acc_year").val().substring(0,4);
    		accMonth=liger.get("period").getValue();
    		if(node.report_type==5){
    			//年报
        		accMonth="20";
        	}
    	}
    	
   	 	var printPara={
   	 		openType:"query",
 			report_code:node.id,
  			mod_code:$("#mod_code").val(),
  			acc_year:accYear,
  			acc_month:accMonth
      	};
   	 	
   	 	//printGridView(spread.toJSON(),{type:10}); 
   	 	printSpreadView(spread.toJSON(),printPara);*/
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
    	         });
    		}
    		
    	});
    	   
    } 
    
    //设置表格样式
    function setPreadJSTable(){
    	//spread.addCustomFunction(new REP());
        //spread.addCustomFunction(new RES());
    	spread.options.grayAreaBackColor='#fff';
    	spread.options.newTabVisible=false;
    	var node = tree.getSelectedNodes()[0];
    	
    	for(var i=0;i<spread.getSheetCount();i++){
        	//var sheet = spread.getActiveSheet();
        	var sheet = spread.getSheet(i);
            sheet.options.protectionOptions.allowSelectLockedCells=true;//是否允许用户选择被锁定的单元格。
            sheet.options.protectionOptions.allowSelectUnlockedCells=true;//是否允许用户选择未被锁定的单元格。
            sheet.options.protectionOptions.allowFilter=true;//是否允许用户筛选。
        	sheet.options.protectionOptions.allowSort = true;//是否允许用户排序。
        	sheet.options.protectionOptions.allowResizeRows=true;//是否允许用户改变行高
        	sheet.options.protectionOptions.allowResizeColumns=true;//是否允许用户改变列宽
        	sheet.options.rowHeaderVisible = $("#is_scale").is(':checked');//是否显示行标尺
        	sheet.options.colHeaderVisible =$("#is_scale").is(':checked');//是否显示列标尺
        	sheet.options.sheetTabColor="#ADD8E6";
        	sheet.options.gridline = {showVerticalGridline: false, showHorizontalGridline: false};//是否显示外边框、是否显示网格线
        	
        	//是否允许编辑
        	if(node!=null && node.is_write==1){
        		sheet.options.protectionOptions.allowEditObjects=true;//是否允许用户编辑浮动对象。
            	sheet.options.isProtected=false;
        	}else{
        		sheet.options.protectionOptions.allowEditObjects=false;//是否允许用户编辑浮动对象。
            	sheet.options.isProtected=true;
        	}
        }
    }
    


/*  function REP(){}
 REP.prototype = new GC.Spread.CalcEngine.Functions.Function("REP", 0, 0, {name: "REP",description: "系统取值函数"});
 REP.prototype.evaluate = function (args) {
     return "";
 }
 
 function RES(){}
 RES.prototype = new GC.Spread.CalcEngine.Functions.Function("RES", 0, 0, {name: "RES",description: "系统取值元素"});
 RES.prototype.evaluate = function (args) {
     return "";
 } */
 
 var REPFunction = new GC.Spread.CalcEngine.Functions.Function("REP", 1, 255, {name: "REP",description: "系统取值函数"});
 REPFunction.evaluate = function()
 {
     var result = "";
   /*  for (var i = 0; i < arguments.length; i++) {
         result += arguments[i];
     }*/
     return result;
 }
 GC.Spread.CalcEngine.Functions.defineGlobalCustomFunction("REP", REPFunction);

 var REPFunction = new GC.Spread.CalcEngine.Functions.Function("RES", 1, 255, {name: "RES",description: "系统取值元素"});
 REPFunction.evaluate = function()
 {
     var result = "";
   /*  for (var i = 0; i < arguments.length; i++) {
         result += arguments[i];
     }*/
     return result;
 }
 GC.Spread.CalcEngine.Functions.defineGlobalCustomFunction("RES", REPFunction);
 
 
 function myImport() {
	var node = tree.getSelectedNodes()[0];
 	if(node==null || node.pId==null){
 		$.ligerDialog.error("请选择报表！");
		return false;
 	}
	 
	if(node.is_write==0){
		$.ligerDialog.error("该报表不允许编辑！");
		 return false;
	}

	var para = {
		url : 'hrp/acc/superReport/importPage.do?isCheck=false',
		title : '导入',
		width : 0,
		height : 0,
		isShowMin : false,
		isModal : true,
		data : {
			spread:spread
		}
	};
	parent.openDialog(para);
	//parent.$.ligerDialog.open({url : 'importCheckPage.do?isCheck=false',
	//data:{columns:grid.columns,rowIndex:rowIndex,grid:grid}, height:$(parent.window).height(),width: $(parent.window).width()+10, title:'导入辅助核算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false});
}


	function paraSet(){
		var data = paraSetParam;
		var param = [];
		for (var obj in data) {
			var elem = data[parseInt(obj)];
			for(var i in elem){
				var value = elem[parseInt(i)];
				if(value.value){
					if(value.value.indexOf(".") != -1){
						param.push(value.value);
					}
				}
			}
		}
		var node = tree.getSelectedNodes()[0];
		if(!node || node.level == 0){
			return false;
		}
		$.ligerDialog.open({
			url : '../paraSetPage.do?isCheck=false&report_code='+node.id + "&data="+param,
    		height: 450 ,width: 400, 
			title:'自定义函数',modal:true,showToggle:false,initShowMax:false,isResize:false,
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.save(); }},
    		    { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				] 
    		});
		
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
<input id="mod_code" type="text"  value="${mod_code}" style="display:none"/>
<input type="text" id="minDate" style="display:none"/>
<input type="text" id="maxDate" style="display:none"/>	
	<div class="l-layout" id="layout1"  >
		<div position="left" title="报表列表">
		    <div  style="overflow:auto;" id="treeDiv">
		       	<ul class="ztree"  id="tree"></ul>
		    </div>
	    </div>
		
		<div position="center" id="centerReport">
			<div id="mainHead" style="height:0px;">
		        <div class="info">
			         <table cellpadding="0" cellspacing="0" class="l-table-edit" id="acc_report_tab" style="display:none">
					       <tr>
					       <td>
					        	<table>
					        		<tr>
					        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
						             <td align="left" class="l-table-edit-td">
										<input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" 
										onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy年',minDate:'#F{$dp.$D(\'minDate\')}',maxDate:'#F{$dp.$D(\'maxDate\')}',ychanging:function(dp){accYearFunc(dp.cal.getNewDateStr());}})"/>
									 </td>
					        		 </tr>
					        	</table>
					        </td>
					        <td id="period_td">
					        	<table>
					        		<tr>
					        			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
										<td align="left" class="l-table-edit-td">
											<div id="period" style="display:none"></div> 
										</td>
					        		</tr>
					        	</table>
					        </td>   
					        </tr>
					  </table>
					 
					  <div style="bottom:5px;right:500px;position:absolute;" ><input type="checkbox" checked="checked" id="is_scale"/>显示标尺</div>
					  <input style="bottom:5px;right:400px;position:absolute;" type="button" accessKey="Q"  value=" 查询（Q）" onclick="myQuery();"/>
 					  <!-- <input style="bottom:5px;right:400px;position:absolute;" type="button" accessKey="PS"  value=" 参数设置" onclick="paraSet();"/> -->
					  <input style="bottom:5px;right:300px;position:absolute;" type="button" accessKey="I"  value=" 导入（I）" onclick="myImport();"/>
					  <!-- input style="bottom:5px;right:200px;position:absolute" type="button" accessKey="E"  value=" 导出Excel（E）" onclick="exportExcel();"/-->
					  <input style="bottom:5px;right:200px;position:absolute" type="button" accessKey="P"  value=" 打印（P）" onclick="myPrint();"/>
					  <input style="bottom:5px;right:100px;position:absolute" type="button" accessKey="N"  value=" 生成（N）" onclick="myCreate();"/>
					  <input style="bottom:5px;right:1px;position:absolute" type="button" accessKey="S"  value=" 保存（S）" onclick="mySave();"/>
					  &nbsp;<br/>&nbsp;<br/>
			    </div>
		    </div>
			<!--div id="centertoolbar" ></div-->	
	        <div id='spread' style='width:100%;'></div>
		</div>
		
	</div>
</body>
</html>
