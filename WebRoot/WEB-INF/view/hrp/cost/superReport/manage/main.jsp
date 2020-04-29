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
<link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript" ></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/gc.spread.sheets.all.10.1.0.min.js' type='text/javascript'></script>
<script src='<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/interop/gc.spread.excelio.10.1.0.min.js' type='text/javascript'></script>
<script src="<%=path%>/lib/SpreadJS10/license.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<script type="text/javascript">
var tree;
var setting;
var grid;
var gridManager;

    $(function (){
		
		$("#layout1").ligerLayout({ leftWidth: 210,allowLeftResize: true
			,onLeftToggle: function (isColl){
            	//alert(isColl ? "收缩" : "显示");
				grid._onResize();
        	}
			,onEndResize: function(isColl) {
				grid._onResize();
	        }	
		});
    	setting = {      
 		   		data: {simpleData: {enable: true},key: {title: "title"}},
 		   		treeNode:{open:true},
 		   		callback:{onClick:myClick}
 	 	};
    	$("#treeDiv").css("height", $(window).height()-28);
    	loadTree();
    	loadHead();
    	//$(':button').ligerButton({width:90});
    });
    
    function loadTree(){
    	var para={
				mod_code:$("#mod_code").val()    			
    	};
		ajaxJsonObjectByUrl("../queryAccSuperReportByPerm.do?isCheck=false",para,function(responseData){	
			  tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
		});
		
    }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '年度', name: 'acc_year', align: 'left',width:100},
					 { display: '期间', name: 'acc_month_name', align: 'left',width:100},
					 { display: '报表编码', name: 'report_code', align: 'left',width:100},
                     { display: '报表名称', name: 'report_name', align: 'left'},
					 { display: '报表类型', name: 'report_type_name', align: 'left'},
					 { display: '报表类型', name: 'report_type', align: 'left',hide:true},
					 { display: '操作用户',name: 'user_name', align: 'left'},
					 { display: '操作时间',name: 'create_date', align: 'left'},
					 { display: '操作',name: 'edit', align: 'left',width:100,
							render : function(rowdata, rowindex,value) {
									return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 20px;' ligeruiid='Button1004'" 
									+"onclick=myView('"+rowdata.acc_year+"','"+rowdata.acc_month+"','"+rowdata.report_code+"','"+rowdata.mod_code+"','"+rowdata.report_name+"','"+rowdata.report_type+"')>"
				       					+"<span>查看</span></div>";
							}}
    				],dataAction : 'server',dataType : 'server',usePager : false,
    				url : 'querySuperReportInstanceList.do',width : '100%',height : '100%',checkbox : true,
    				rownumbers : true,delayLoad:true,selectRowButtonOnly : true ,heightDiff: 0,
    				toolbar: { items: [
    			                    	{ text: '查询', id:'search', click: myQuery, icon:'search' },
    			                    	{ line:true },
    			    	                { text: '删除', id:'del', click: myDelete,icon:'delete' },
    									{ line:true }
    			       ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function  myQuery(){
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null){
    		//$.ligerDialog.error("请选择报表！");
    		return false;
    	}
    	var accYear=$("#acc_year").val();
    	if(accYear!=""){
    		accYear=accYear.substring(0,4);
    	}
    	grid.options.parms=[];
    	grid.options.newPage = 1;
    	grid.options.parms.push({name:'report_code',value:node.id});
    	grid.options.parms.push({name:'mod_code',value:$("#mod_code").val()});
		grid.options.parms.push({name:'acc_year',value:accYear}); 
		grid.loadData(grid.where);
		
 	}

    //删除
    function myDelete(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			var reportCode="";
			var modCode="";
			$(data).each(function(i,obj) {
				if(i==0){
					reportCode=this.report_code;
					modCode=this.mod_code;
				}
				paramVo.push(this.acc_year+"@"+this.acc_month);
				
			});
			
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteBatchSuperReportInstance.do", {report_code:reportCode,mod_code:modCode,paramVo :paramVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							myQuery();
						}
					});
				}
			});
		}
    }
    
	function myClick(){
    	myQuery();
    }
	
	//查看报表内容
	function myView(accYear,accMonth,reportCode,modCode,reportName,reportType){
		var title=reportName;
		if(reportType=="2"){
			title=title+"（"+accYear+"年"+accMonth+"月）";
		}else if(reportType=="3" && accMonth=="14"){
			title=title+"（"+accYear+"年第一季度）";
		}else if(reportType=="3" && accMonth=="15"){
			title=title+"（"+accYear+"年第二季度）";
		}else if(reportType=="3" && accMonth=="16"){
			title=title+"（"+accYear+"年第三季度）";
		}else if(reportType=="3" && accMonth=="17"){
			title=title+"（"+accYear+"年第四季度）";
		}else if(reportType=="4" && accMonth=="18"){
			title=title+"（"+accYear+"年上半年）";
		}else if(reportType=="4" && accMonth=="19"){
			title=title+"（"+accYear+"年下半年）";
		}else if(reportType=="5"){
			title=title+"（"+accYear+"年）";
		}
		
		var para={
    			//accYear,accMonth,reportCode,modCode,reportName,reportType
    			operation:"instance",
      		   	report_code:reportCode,
      			mod_code:modCode,
      			acc_year:accYear,
      			acc_month:accMonth
      	};
		ajaxJsonObjectByUrl("../querySuperReportInstance.do?isCheck=false",para,function(responseData){	
			/* var printPara={type:10};
            printGridView(responseData,printPara); */
			//printSpreadView(responseData,{openType:'manger',report_code:reportCode,mod_code:modCode});
            
			var spread = new GC.Spread.Sheets.Workbook($('#spread')[0], { sheetCount: 1 });
			var excelIo = new GC.Spread.Excel.IO();
			try {
				if (!spread) {
					return;
				}
			} catch (ex) { }

			spread.isPaintSuspended(true);
			spread.fromJSON(responseData);
			spread.isPaintSuspended(false);
			
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
	    				'report_code':reportCode,
	    				'sheet_name':sheetName,
	    				'openFlag':'query'
	    		};
				officePrintReport(para,blob,function(){
					layer.close(loadIndex);
				});
			});
			
		});
		//parent.openFullDialog('hrp/acc/superReport/manage/reportPage.do?isCheck=false&acc_year='+accYear+'&acc_month='+accMonth+'&mod_code='+modCode+'&report_code='+reportCode,title,0,0,true,true);
		/*parent.$.ligerDialog.open({url : 'hrp/acc/superReport/manage/reportPage.do?isCheck=false&acc_year='+accYear+'&acc_month='+accMonth+'&mod_code='+modCode+'&report_code='+reportCode,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:title,modal:true,showToggle:false,showMax:false,showMin: false,isResize:false,
    		 buttons: [ { text: '导出Excel', onclick: function (item, dialog) { dialog.frame.exportExcel(); },cls:'l-dialog-btn-highlight' },
    		           { text: '打印', onclick: function (item, dialog) { dialog.frame.myPrint(); },cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
    		});*/
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
			         
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" id="acc_report_tab">
					<tr>
					     <td align="right" class="l-table-edit-td"  style="padding-left:20px;" id="acc_year_td">年度：</td>
					     <td align="left" class="l-table-edit-td">
								<input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" value="${accYearMonthMy97.curDate}"
								onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy年',minDate:'${accYearMonthMy97.minDate}',maxDate:'${accYearMonthMy97.maxDate}'})"/>
						</td>
					</tr>
			</table>
			
		    <div id="maingrid"></div>
		</div>
		
	</div>
	
</body>
</html>
