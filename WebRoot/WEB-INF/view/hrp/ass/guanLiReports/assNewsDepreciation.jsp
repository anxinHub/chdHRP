<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    });
    
    //查询
    function query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
    }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '卡片编码', name: 'ass_card_no', align: 'left'
                     },
                     { display: '原累计折旧', name: 'old_depre', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                    			return formatNumber(
                    			rowdata.old_depre == null ? 0: rowdata.old_depre,'${ass_05005 }',1);
                    			},formatter:'###,##0.00'
	 
                     },
                     { display: '变动累计折旧', name: 'charge_depre', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                    	 return formatNumber(
                     			rowdata.charge_depre == null ? 0: rowdata.charge_depre,'${ass_05005 }',1);
                     			},formatter:'###,##0.00'
                    	 },
                     { display: '新累计折旧', name: 'new_depre', align: 'left',
                    		 render : function(rowdata, rowindex,value) {
                    		 return formatNumber(
                         			rowdata.new_depre == null ? 0: rowdata.new_depre,'${ass_05005 }',1);
                         			},formatter:'###,##0.00'
                    		 }
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../guanLiReports/queryAssNewsDepreciation.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:true,
                     toolbar: { items: [
                             { text: '查   询', id:'search', click: query,icon:'search' },
 						     { line:true },
 						     { text: '打   印', id:'print', click: printDate,icon:'print' },
 						     { line:true },
//                              { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
                     ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
        //字典下拉框
    	$("#year_month").ligerTextBox({width:150});
    } 
    
    //键盘事件
	function loadHotkeys() {

	}
    
    //导出
	function exportExcel(){
		
	}
	
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date=$("#year_month").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"折旧年月："},
    	          {"cell":1,"value":$("#year_month").val()},
    	          {"cell":2,"value":"报表日期:"},
  				  {"cell":3,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":2,"value":"制表人:"},
    				{"cell":3,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产新增折旧清单",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssNewsDepreciationService",
 				method_name: "queryAssNewsDepreciationPrint",
 				bean_name: "assNewsDepreciationService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">折旧年月：</td>
			<td align="left" class="l-table-edit-td"><input
				name="year_month" type="text" id="year_month" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
		</tr>

	</table>

	<div id="maingrid"></div>
</body>
</html>
