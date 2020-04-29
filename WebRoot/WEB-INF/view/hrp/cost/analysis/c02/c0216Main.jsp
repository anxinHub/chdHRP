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
						{ display: '分类', name: 'type_name',align: 'left', minWidth: 60, width: '10%' },
		               { display: '医疗业务成本', columns:
			               [
			                   { display: '金融', name: 't_1', align:'right',
                 	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_1,2,1);
								} }, 
			                   { display: '比重', name: 't_2',align:'right',
                  	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_2,2,1);
								} } 
			               ]
		               },
		               { display: '医疗成本', columns:
		                   [
		                       { display: '金融', name: 't_3',align:'right',
                 	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_3,2,1);
								} }, 
		                       { display: '比重', name: 't_4',align:'right',
                  	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_4,2,1);
								} } 
		                   ]
		                },
		                { display: '医疗全成本', columns:
		                    [
		                        { display: '金融', name: 't_5',align:'right',
                  	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_5,2,1);
								} }, 
		                        { display: '比重', name: 't_6',align:'right',
                  	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_6,2,1);
								} } 
		                    ]
		                 },
		                 { display: '医院全成本', columns:
		                     [
		                         { display: '金融', name: 't_7',align:'right',
                   	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_7,2,1);
								} }, 
		                         { display: '比重', name: 't_8',align:'right',
                  	        	  render : function(rowdata, rowindex,
											value) {
								 	return formatNumber(rowdata.t_8,2,1);
								} } 
		                     ]
		                  }
		                     ], pageSize: 10, 
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0216.do',
		           width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,
		           //allowHideColumn: false,
		           toolbar: { items: [{ text: '查询', click: query, icon: 'query'},
		                              { line:true },
		                             /*  { text: '生成', click: creat_data, icon: 'creat_data'},
		                              { line:true }, */
		                              { text: '打印', click: print, icon: 'print'}
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
       
       //打印
//        function print(){
//     	   var exportPara = {
//       				usePager: false,
//       				year_month_begin: $("#year_month_begin").val(),
//       				year_month_end: $("#year_month_end").val()
//       			};
       		  
//        	   $.ajax({
//        		   url:"queryAnalysisC0216.do",
//        		   type:"post",
//        		   data:exportPara,
//        		   dataType:"JSON",
//        		   success:function(res){
       			  
//        			   var data={
//        					   headers:[
//        								{ x: 0, y: 0, rowSpan: 2, colSpan: 1, displayName: "分类", name: "type_name",size:120  },
//        								{ x: 1, y: 0, rowSpan: 1, colSpan: 2, displayName: "医疗业务成本" },
//        								{ x: 1, y: 1, rowSpan: 1, colSpan: 1, displayName: "金融", name: "t_1", formatter: "#,##0.00",size:100 },
//        								{ x: 2, y: 1, rowSpan: 1, colSpan: 1, displayName: "比重", name: "t_2", formatter: "#,##0.00",size:60 },
//        								{ x: 3, y: 0, rowSpan: 1, colSpan: 2, displayName: "医疗成本" },
//        								{ x: 3, y: 1, rowSpan: 1, colSpan: 1, displayName: "金融", name: "t_3", formatter: "#,##0.00",size:100 },
//        								{ x: 4, y: 1, rowSpan: 1, colSpan: 1, displayName: "比重", name: "t_4", formatter: "#,##0.00",size:60 },
//        								{ x: 5, y: 0, rowSpan: 1, colSpan: 2, displayName: "医疗全成本"},
//        								{ x: 5, y: 1, rowSpan: 1, colSpan: 1, displayName: "金融", name: "t_5", formatter: "#,##0.00",size:100 },
//        								{ x: 6, y: 1, rowSpan: 1, colSpan: 1, displayName: "比重", name: "t_6", formatter: "#,##0.00",size:60 },
//        								{ x: 7, y: 0, rowSpan: 1, colSpan: 2, displayName: "医院全成本"},  
//        								{ x: 7, y: 1, rowSpan: 1, colSpan: 1, displayName: "金融", name: "t_7", formatter: "#,##0.00",size:100 },
//        								{ x: 8, y: 1, rowSpan: 1, colSpan: 1, displayName: "比重", name: "t_8", formatter: "#,##0.00",size:60 }
//        					    ],
//        					    rows: res.Rows
//        			   }
       			  
//        			   viewPrint(data, "医疗构成分析总表");
//        		   },
//        		   error: function (res) {
//    					console.error(res);
//    				}
//        	   });
//        };
         function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'管理医疗构成分析总表',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
    				{"cell":0,"value":
    					"期间: " + $("#year_month_begin").val()+
    					" 至  "+ $("#year_month_end").val(),
    				"colspan":colspan_num,"br":true}
       			],
       			foot:[
    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
					{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
					{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
					{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:2,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryAnalysisC0216	.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
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
</body>
</html>