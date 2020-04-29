<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/Lodop/ssf.js"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<script src="<%=path%>/lib/Lodop/printThis.js" language="javascript"></script>
<script src="<%=path%>/lib/Lodop/webPrint.js" language="javascript"></script>
<script type="text/javascript">
var grid;
var gridManager = null;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var spreadNS;//spreadNS=Gc.Spread.Sheets
//var spread;//spread=spreadNS.designer.wrapper.spread，重新导入数据，需要重新初始化，所以用spreadNS.designer.wrapper.spread获取对象
//var $spreadFrame=window.frames["spreadFrame"];
//$spreadFrame.GcSpread.Sheets.designer.wrapper.spread;
var printInfo;

$(function() {
	$("#layout1").ligerLayout({ leftWidth: 460});
	loadCenterTool();
	$("#spreadFrame").css("height", $(window).height()-60);
	//$(':button').ligerButton({width:110});
	loadHead();	//加载数据
	
});

function loadHead(){
	if($("#template_code").val()==""){
		$.ligerDialog.error("没有配置打印参数！");
		return;
	}
	var para={
			template_code:$("#template_code").val(),
			use_id:$("#use_id").val()
	}
	
	grid = $("#maingrid").ligerGrid({
       columns: [ 
				/* { display: '参数编码', name: 'para_code', align: 'left',width:100,
					 render : function(rowdata, rowindex,value) {
							var str="";
							if(rowdata.flag<7){
								str="<a href=javascript:void(0); ondragstart='return false' onclick=myDrag("+rowdata.flag+",'"+rowdata.para_code+"','"+rowdata.para_name+"'); title='点击填充到指定位置' style='cursor:pointer;'>"+rowdata.para_code+"</a>";
								
							}else{
								str=rowdata.para_code;
							}
							
							return str;
					}
				}, */
				 { display: '参数名称', name: 'para_name', align: 'left',width:130,
					 render : function(rowdata, rowindex,value) {
							
							return "<span title='"+rowdata.para_code+"'>"+rowdata.para_name+"</span>";
					}},
				 { display: '参数类型', name: 'flag_value', align: 'left',width:70},
                 { display: '参数值', name: 'para_value', align: 'left',width:100,
						render : function(rowdata, rowindex,value) {
							var str = "";
								
							if(rowdata.flag == 7){
								
								str = "<select id = 'p_"+rowdata.para_code+"' class='inputMar'>";
									if(rowdata.para_value == "是"){
										str = str + "<option value='否'>否</option>";
										str = str + "<option value='是' selected='selected'>是</option>";
									}else{
										str = str + "<option value='否'selected='selected'>否</option>";
										str = str + "<option value='是' >是</option>";
									}
								str = str + "</option></select>";
								
							}else if(rowdata.flag == 1 || rowdata.flag == 2 || rowdata.flag == 5 || rowdata.flag == 8 || rowdata.flag == 10){
								//1主表(横纵坐标)、2从表(纵坐标)、系统参数：3是否、5金额大写(横纵坐标)、8输入文本框(纵坐标)、10输入文本框(横纵坐标)
								str = "<input id='p_"+rowdata.para_code+"' value='"+(rowdata.para_value==null?"":rowdata.para_value)+"' class='inputMar' style='margin-top:5px;width:80px;'>";
								
							}else if(rowdata.flag == 9){
								
								var obj = eval('[' + rowdata.para_json + ']'); 
								str = "<select id='p_"+rowdata.para_code+"' style='margin-top:5px;width:150;'>";
								for(var json in obj){
									if(rowdata.para_value == obj[json].code){
										str = str + "<option value='"+obj[json].code+"' selected='selected'>"+obj[json].value+"</option>";
									}else{
										str = str + "<option value='"+obj[json].code+"'>"+obj[json].value+"</option>";
									}
								}
								str = str + "</option>";

							}else{
								//str="<button style='margin-top:5px;' onclick=myDrag('"+rowdata.para_code+"','"+rowdata.para_name+"')>填充</button>";
							}
							return str;
						}
				 },
				 { display: '操作', name: 'operation', align: 'center',width:80,
					 render : function(rowdata, rowindex,value) {
							var str="";
							if(rowdata.flag==1 || rowdata.flag==2 || rowdata.flag == 5 || rowdata.flag==10){
								str="<a href=javascript:void(0); ondragstart='return false' onclick=myDrag("+rowdata.flag+",'"+rowdata.para_code+"','"+rowdata.para_name+"',"+rowindex+"); style='cursor:pointer;'>取值</a>";
								
							}else if(rowdata.para_code=="003"){
								str="<span title='某内容套打的时候需要显示，在单元格右键插入批注即可'>说明</span>";
								
							}
							
							return str;
					}
				 }
                 ],
                 dataAction: 'server',dataType: 'server',usePager:false,url:'querySuperPrintParaByListJosn.do?isCheck=false',
                 width: '99%', height:'100%', checkbox: false,rownumbers:true,urlParms:para,
                 selectRowButtonOnly:true
               });

    gridManager = $("#maingrid").ligerGetGridManager();
}

//加载打印模板内容
function initSpreadTable() {
	
	var para={
			template_code:$("#template_code").val(),
			use_id:$("#use_id").val()
		};
	ajaxJsonObjectByUrl("querySuperPrintContentByCode.do?isCheck=false",para,function(responseData){
		
		try{
			var spread=spreadNS.designer.wrapper.spread;
			if (!spread) {
		        return;
		    }
			printInfo = new spreadNS.Print.PrintInfo();
			printInfo.qualityFactor(3);//设置纸张质量因子
	        spread.isPaintSuspended(true);
	        spread.fromJSON(responseData);
	        spread.isPaintSuspended(false);
	        
	        //初始化spread打印参数
	        var sheet = spread.getActiveSheet();
	    	var sheetJson=sheet.toJSON();
	    	if(sheetJson){
	    		var printInfoLoad=sheetJson.printInfo;
		    	if(printInfoLoad){
		    		//纸张大小
		    		if(printInfoLoad.paperSize){
		    			printInfo.paperSize(new spreadNS.Print.PaperSize(printInfoLoad.paperSize.width==undefined?"850":printInfoLoad.paperSize.width,printInfoLoad.paperSize.height==undefined?"1100":printInfoLoad.paperSize.height));
		    		}
		    		//设置边距
		    		if(printInfoLoad.margin){
		    			printInfo.margin({
		    		    	top:printInfoLoad.margin.top==undefined?0:printInfoLoad.margin.top, 
		    		    	bottom:printInfoLoad.margin.bottom==undefined?0:printInfoLoad.margin.bottom, 
		    		    	left:printInfoLoad.margin.left==undefined?20:printInfoLoad.margin.left, 
		    		    	right:printInfoLoad.margin.right==undefined?0:printInfoLoad.margin.right, 
		    		    	header:printInfoLoad.margin.header==undefined?0:printInfoLoad.margin.header, 
		    		    	footer:printInfoLoad.margin.footer==undefined?0:printInfoLoad.margin.footer
		    		    });
		    		}
		    	}
		        
	    	} 
	        
	        
            spread.newTabVisible(false);
            spread.grayAreaBackColor('#fff');
		}catch(ex){};
		
           
           
    /*     var frameObj=document.getElementById("spreadFrame").contentWindow;
           var GcSpread=frameObj.GcSpread;
           var sheet = spread.getActiveSheet();
           var data = { name: 'Jones',  region: 'East',
     			  sales: [
     			    { inv_code: '000001', inv_name: '材料1', amount_money: 1.99 },
     			    { inv_code: '000002', inv_name: '材料2', amount_money: 4.99 },
     			    { inv_code: '000003', inv_name: '材料3', amount_money: 15.99 },
     			    { inv_code: '000004', inv_name: '材料4', amount_money: 25.99 },
     			    { inv_code: '000005', inv_name: '材料5', amount_money: 35.99 },
     			    { inv_code: '000006', inv_name: '材料6', amount_money: 45.99 }
     			   ]};
     			var tableColumns = [], 
     			    names = ['inv_code', 'inv_name', 'amount_money'],
     			    labels = ['材料编码', '材料名称', '金额'];
     			var table = sheet.addTable('tableRecords', 4, 1,6, 3);
     			//table.showHeader(false);
     			table.bandRows(false);
     			table.autoGenerateColumns(false);
     			names.forEach(function (name, index) {
     			   var tableColumn = new GcSpread.Sheets.TableColumnInfo();
     			   tableColumn.name(labels[index]);
     			   tableColumn.dataField(name);
     			   tableColumns.push(tableColumn);
     			});
     			
     			table.bindColumns(tableColumns);
     			table.bindingPath('sales');
     			source = new GcSpread.Sheets.CellBindingSource(data);
     			sheet.setDataSource(source);   */
           
		
	}); 
}

var $tool;
function loadCenterTool(){
	 $tool=$("#centertoolbar").ligerToolBar({ items: [   	      
	       {text: '保存', id:'save', icon:'save', click: mySave},
	       { line:true },
	       { text: '页面设置',id:'account',icon:'account', click: myOpenInfo},
       /*{ text: '设置查询条件',id:'search2',icon:'search2', click: mywhereSet},*/
       	   { line:true },
	       { text: '隐藏工具栏',id:'up',icon:'up', click: hidenSpreadMenu},
	       { line:true },
	       { text: '打印预览', id:'print', icon:'print', click: myPrint},
	       { line:true },
           { text: '关闭',id:'candle.',icon:'candle', click: myClose}
	       ]
	    });
} 


var spreadJSTop=195;
var headerHeight=175;
function hidenSpreadMenu(item){
	
	/* var butonText=$(item).children("b").html();
	var $spreadFrame=$(window.frames["spreadFrame"].document);
	var $content=$spreadFrame.find(".content .fill-spread-content");
	if(butonText=="隐藏工具栏（<u>Y</u>）"){
		
		headerHeight=$spreadFrame.find(".header").css("height");
		spreadJSTop=$content.css("top");
		$spreadFrame.find(".header").css("height",0);
   	    $content.css({ top: 2 });
   	 	$(item).children("b").html("显示工具栏（<u>Y</u>）");
   	 	
    }else{
    	
    	headerHeight=$spreadFrame.find(".header").css("height",headerHeight);
   	    $content.css({ top: spreadJSTop });
   	 	$(item).children("b").html("隐藏工具栏（<u>Y</u>）");
   	 	
    }  
	$content.parent().css({ height: $content.height() });
	spreadNS.designer.wrapper.spread.refresh(); */
	
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
	if (!spread) {
        return;
    }
	
	if($("#template_code").val()=="01001"){
		//window打印
		spreadPrint({
			spread:spread.toJSON(),
			head:false
		});
	   /*  lodopSpread({
			spread:spread.toJSON(),
			taskName:"模板打印",
			percent:"Width:100%",
			head:false 
		}) */
		return;
	}
	
	var sheet = spread.getActiveSheet();
    if($("#p_009").val()!=undefined && $("#p_009").val()=="是"){
    	printInfo.showBorder(true);//是否打印控件的外边框线
    }else{
    	printInfo.showBorder(false);//是否打印控件的外边框线
    }
    
    if($("#p_010").val()!=undefined && $("#p_010").val()=="是"){
    	printInfo.showGridLine(true);//是否打印网格线 (默认是打印)
    }else{
    	printInfo.showGridLine(false);//是否打印网格线 (默认是打印)
    }
  
    setPrintSysPage(printInfo);
    
   /*  Inherit: 继承自表单的设置 ((默认) 行头/列头可见)。
	Hide: 不打印。
	Show: 在每页中都显示。
	ShowOnce: 显示一次 (在第一页). */
   printInfo.showRowHeader(spreadNS.Print.PrintVisibilityType.hide);
   printInfo.showColumnHeader(spreadNS.Print.PrintVisibilityType.hide);
  // printInfo.footerCenter("&P/&N");
   // printInfo.headerRight("&P/&N");
    /*  printInfo.repeatRowStart(0);
    printInfo.repeatRowEnd(11);
    printInfo.repeatColumnStart(0);
    printInfo.repeatColumnEnd(50); */
   // printInfo.rowStart(0)
   // printInfo.rowEnd(11);
   // sheet.setRowPageBreak(8, true);
  //  sheet.setColumnPageBreak(3, true);
 	sheet.printInfo(printInfo);
	spread.print();
}

function myClose(){
	dialog.close();
}


function mySave(){
	var modCode=null;
	$.each(grid.getData(),function(i,obj){
		modCode=obj.mod_code;
		return false;
	});
	if(modCode==null || modCode==""){
		$.ligerDialog.error("没有配置打印参数！");
		return;
	}
	
	var para_value="";
	var isCheck=true;
	var beginNum=0;
	var endNum=0;
	var re = /^[0-9]*[1-9][0-9]*$/ ; 
	
	$.each($(".inputMar"),function(i){
		var thisVal=$(this).val();
		
		if($(this).attr("id").replace("p_","")=="004"){
			beginNum=thisVal;
			if(!re.test(beginNum)){
				$.ligerDialog.error("起始行只能为正整数！");
				isCheck=false;
				return;
			}
		}
		if($(this).attr("id").replace("p_","")=="005"){
			endNum=thisVal;
			if(!re.test(endNum)){
				$.ligerDialog.error("结束行只能为正整数！");
				isCheck=false;
				return;
			}
		}
		/* if($(this).attr("id").replace("p_","")=="006"){
			if(thisVal!="" && thisVal.split(",").length!=2){
				$.ligerDialog.error("纸张大小的格式不正确，宽，高！");
				isCheck=false;
				return;
			}
			if(thisVal.split("*")[0]=="" || thisVal.split("*")[1]==""){
				$.ligerDialog.error("纸张大小的格式不正确，宽，高！");
				isCheck=false;
				return;
			}
		} */
		
		if(thisVal=="")thisVal=" ";
		para_value=para_value+$(this).attr("id").replace("p_","")+"@"+thisVal+"#";
	});
	if(!isCheck){
		return;
	}
	if(parseInt(beginNum)>parseInt(endNum)){
		$.ligerDialog.error("起始行不能大于结束行！");
		return;
	}
	
	var spread=spreadNS.designer.wrapper.spread;
	if (!spread) {
        return;
    }
	
	var sheet = spread.getActiveSheet();
    printInfo.showRowHeader(spreadNS.Print.PrintVisibilityType.hide);
    printInfo.showColumnHeader(spreadNS.Print.PrintVisibilityType.hide);
  
    
    if($("#p_009").val()!=undefined && $("#p_009").val()=="是"){
    	printInfo.showBorder(true);//是否打印控件的外边框线
    }else{
    	printInfo.showBorder(false);//是否打印控件的外边框线
    }
    
    if($("#p_010").val()!=undefined && $("#p_010").val()=="是"){
    	printInfo.showGridLine(true);//是否打印网格线 (默认是打印)
    }else{
    	printInfo.showGridLine(false);//是否打印网格线 (默认是打印)
    }
    
    if($("#p_012").val()!=undefined){
    	para_value=para_value+"012@"+$("#p_012").val()+"#";
    }
   
    setPrintSysPage(printInfo);
    sheet.printInfo(printInfo); 
	var para={
			content:JSON.stringify(spread.toJSON()),
			para_value:para_value,
			mod_code:modCode,
			template_code:$("#template_code").val(),
			use_id:$("#use_id").val()
	};
	
	var loadIndex = layer.load(1);
	ajaxJsonObjectBylayer("saveSuperPrintTemplate.do?isCheck=false",para,function (responseData){
		layer.close(loadIndex);
	},layer,loadIndex);
	
}

function myDrag(flag,para_code,para_name,rowindex){
	var spread=spreadNS.designer.wrapper.spread;
	if (!spread) {
        return;
    }
	
	var sheet = spread.getActiveSheet();
	var activeRowIndex = sheet.getActiveRowIndex();
	activeRowIndex=parseInt(activeRowIndex)+1;//下标从0开始
	var activeColumnIndex = sheet.getActiveColumnIndex();
	activeColumnIndex=parseInt(activeColumnIndex)+1;//下标从0开始
	
	
	$.each($(".inputMar"),function(i){
		
		if(i==rowindex){
			
			if(flag==1 || flag==5 || flag==10){
				//横纵坐标
				$(this).val(activeRowIndex+","+activeColumnIndex);
				return true;
			}else if(flag==2){
				//纵坐标
				$(this).val(activeColumnIndex);
				return true;
			}		
		}
		
	});
	
	//从表
	/* sheet.setText(activeRowIndex, activeColumnIndex, "\${"+para_code+"\}");
	
	var comment = new spreadNS.Comment();
	comment.width(200);
    comment.height(200);
    comment.text(para_name);
    sheet.setComment(activeRowIndex, activeColumnIndex, comment); */
}

function setPrintSysPage(printInfo){
	
		if($("#p_012").val()=="1"){
			printInfo.headerLeft("&P/&N");
			printInfo.headerCenter("");
			printInfo.headerRight("");
			printInfo.footerLeft("");
			printInfo.footerCenter("");
			printInfo.footerRight("");
		}else if($("#p_012").val()=="2"){
			printInfo.headerCenter("&P/&N");
			printInfo.headerLeft("");
			printInfo.headerRight("");
			printInfo.footerLeft("");
			printInfo.footerCenter("");
			printInfo.footerRight("");
		}else if($("#p_012").val()=="3"){
			printInfo.headerRight("&P/&N");
			printInfo.headerLeft("");
			printInfo.headerCenter("");
			printInfo.footerLeft("");
			printInfo.footerCenter("");
			printInfo.footerRight("");
		}else if($("#p_012").val()=="4"){
			printInfo.footerLeft("&P/&N");
			printInfo.headerLeft("");
			printInfo.headerCenter("");
			printInfo.headerRight("");
			printInfo.footerCenter("");
			printInfo.footerRight("");
		}else if($("#p_012").val()=="5"){
			printInfo.footerCenter("&P/&N");
			printInfo.headerLeft("");
			printInfo.headerCenter("");
			printInfo.headerRight("");
			printInfo.footerLeft("");
			printInfo.footerRight("");
		}else if($("#p_012").val()=="6"){
			printInfo.footerRight("&P/&N");
			printInfo.headerLeft("");
			printInfo.headerCenter("");
			printInfo.headerRight("");
			printInfo.footerLeft("");
			printInfo.footerCenter("");
		}else{
			printInfo.headerLeft("");
			printInfo.headerCenter("");
			printInfo.headerRight("");
			printInfo.footerLeft("");
			printInfo.footerCenter("");
			printInfo.footerRight("");
		}
	
}

function myOpenInfo(){
	$.ligerDialog.open({url : 'printInfoPage.do?isCheck=false',
		data:{spreadNS:spreadNS,printInfo:printInfo}, height: 400,width: 500, title:'打印设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.mySave(); },cls:'l-dialog-btn-highlight' },
		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
}


</script>

</head>

<body style="padding: 0px; overflow: hidden;">
<input id="use_id" type="text"  value="${use_id}" style="display:none"/>
<input id="template_code" type="text"  value="${template_code}" style="display:none"/>
	<div id="layout1" style="width:100%;margin:0; padding:0;">
	
		<div id="maingrid" position="left" title="打印参数"></div>
		
		<div class="adjoined-bottom" position="center" title="打印模板">
			<!-- 
			<button accessKey="S" onclick="mySave();"><b>保存（<u>S</u>）</b></button>
			&nbsp;&nbsp;
			<button accessKey="F" onclick="myOpenInfo();"><b>页面设置（<u>F</u>）</b></button>
			&nbsp;&nbsp;
			<button accessKey="Y" onclick="hidenSpreadMenu(this);"><b>隐藏工具栏（<u>Y</u>）</b></button>
			&nbsp;&nbsp;
			<button accessKey="P" onclick="myPrint();"><b>打印预览（<u>P</u>）</b></button>
			&nbsp;&nbsp;
			<button accessKey="C" onclick="myClose();"><b>关闭（<u>C</u>）</b></button><br/>&nbsp;
			 -->
			<div id="centertoolbar" ></div>
			<iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width="100%" id="spreadFrame" name="spreadFrame"></iframe>
		</div>

	</div>
</body>
</html>
