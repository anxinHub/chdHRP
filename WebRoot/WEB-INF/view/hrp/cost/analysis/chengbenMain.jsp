<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医疗构成分析总表</title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script> 
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });
	       
      function loadDict(){
    	  //年月的初始化
    	  autodate("#year_month_begin","yyyyMM"); 
    	  autodate("#year_month_end","yyyyMM");
    	  $("#year_month_begin").ligerTextBox({width:120});
          $("#year_month_end").ligerTextBox({width:120});
      };
      
		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
		           columns: [
                       { display: '分类', name: 'cost_type_name',align: 'left', minWidth: 60, width: '10%' ,
                    	   
                       },
// 		               { display: '医疗业务成本', columns:
// 			               [
// 			                   { display: '金融', name: 't_1', align:'right',
//                  	        	  render : function(rowdata, rowindex,
// 											value) {
// 								 	return formatNumber(rowdata.t_1,2,1);
// 								} }, 
// 			                   { display: '比重', name: 't_2',align:'right',
//                   	        	  render : function(rowdata, rowindex,
// 											value) {
// 								 	return formatNumber(rowdata.t_2,2,1);
// 								} } 
// 			               ]
// 		               },
		              
		                       { display: '合计', name: 'sum_amount',align:'right',minWidth: 60, width: '10%',
		                    	   render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.sum_amount,2,1);
								}
                 	        	   }, 
		                       
		                { display: '其中', columns:
		                    [
		                        { display: '直接成本', name: 'dir_amount',align:'right',formatter:'###,##0.00',
                  	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.dir_amount,2,1);
								} }, 
		                        { display: '间接成本', name: 'indir_amount',align:'right',formatter:'###,##0.00',
                  	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.indir_amount,2,1);
								} } 
		                    ]
		                 }
		                     ], pageSize: 10, 
		           dataAction: 'server',dataType: 'server',usePager:true,url:'querychengbenMain.do?isCheck=false',
		           width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,
		           //allowHideColumn: false,
		           toolbar: { items: [{ text: '查询', click: query, icon: 'query'},
		                              { line:true },
		                              /* { text: '生成', id:'add', click: itemclick,icon:'add' },
		                           	  { line:true }, */
		                              { text: '打印', id: 'print',click: print, icon: 'print'}
		           					]
		                },
		               width: '100%', height: '100%'
		           });


		           //$("#pageloading").hide();
           gridManager = $("#maingrid").ligerGetGridManager();
		}
		
	   
       //查询
       function query(){
    	   grid.options.parms=[];
      	   grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	   grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
       	   grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
       	   
	       	//加载查询条件
	       	grid.loadData(grid.where);
       };
       
       //生成
       function creat_data(){};
       
       function itemclick(item){ 
           if(item.id)
           {
               switch (item.id)
               {
                   case "add":
                   	
                 		$.ligerDialog.open({url: 'costAnalysisC0201AddPage.do?isCheck=false', height: 350,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                 		return;
               }   
           }
           
       }
      
         function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		           {"cell":0,"value":"期间"+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"} 
 	    	]};
 	       var printPara={
 	      		title: "医院各类科室直接成本明细表",//标题 
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.AnalysisService", 
 	   			method_name: "querychengbenMainprint",
 	   			bean_name: "analysisService", 
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);	
   		
    }
</script>
</head>
<body>
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" > 
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	  
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
	 
	 <div class="sample-turtorial" style="display: none"></div>
<!-- 	 <div id="maingrid"></div> -->
<!-- 	<div id="resultPrint" style="display: none"> -->
<!-- 	<table width="100%"> -->
<!-- 		<thead> -->
<!-- 			<tr> -->
<!-- 			   	     <th width="200" rowspan="2">项目</th> -->
<!-- 				     <th width="200" colspan="2" align="center">医疗业务成本</th> -->
<!-- 				     <th width="200" colspan="2" align="center">医疗成本</th> -->
<!-- 				     <th width="200" colspan="2" align="center">医疗全成本</th> -->
<!-- 				     <th width="200" colspan="2" align="center">医院全成本</th> -->
<!-- 			  </tr> -->
<!-- 			  <tr> -->
<!-- 			<th width="200" align="right">金融</th> -->
<!-- 			<th width="200" align="right">比重</th> -->
<!-- 			<th width="200" align="right">金融</th> -->
<!-- 			<th width="200" align="right">比重</th> -->
<!-- 			<th width="200" align="right">金融</th> -->
<!-- 			<th width="200" align="right">比重</th> -->
<!-- 			<th width="200" align="right">金融</th> -->
<!-- 			<th width="200" align="right">比重</th> -->
<!-- 		</tr> -->
		
<!-- 		</thead> -->
<!-- 	<tbody></tbody> -->
<!-- 	</table> -->
<!-- </div> -->
	 
</body>
</html>