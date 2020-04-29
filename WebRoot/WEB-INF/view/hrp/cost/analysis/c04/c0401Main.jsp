<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科室成本习性分析表</title>
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

      		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
		           columns: [
                  { display: '科室编码', name: 'dept_code', minWidth: 60, width: '10%', align: 'left' },
                  { display: '科室名称', name: 'dept_name', minWidth: 60, width: '10%', align: 'left' },
                  { display: '合计', columns:
   	               [
   	                   { display: '本期', name: 't_1', align: 'right', width: '9%',formatter:'###,##0.00',
   	                	render : function(rowdata, rowindex,value) {
							 return formatNumber(rowdata.t_1,2,1);
						}    
   	                   }, 
   	                   { display: '累计', name: 't_2', width: '9%', align: 'right',formatter:'###,##0.00',
   	                	render : function(rowdata, rowindex,value) {
							 return formatNumber(rowdata.t_2,2,1);
						}    
   	                   } 
   	               ]
                  },
                  { display: '固定成本', columns:
                      [
                          { display: '成本', columns:[
                                                  { display: '本期', name: 't_3', align: 'right',formatter:'###,##0.00', width: '9%' ,
                                                	  render : function(rowdata, rowindex,value) {
                              							 return formatNumber(rowdata.t_3,2,1);
                              						}   
                                                  }, 
                             	                   { display: '累计', name: 't_4', width: '9%', align: 'right',formatter:'###,##0.00',
                                                	  render : function(rowdata, rowindex,value) {
                              							 return formatNumber(rowdata.t_4,2,1);
                              						}   
                             	                   }  
                                                    ] 
                          }, 
                          { display: '比重',  columns:[
                                                  { display: '本期', name: 't_5', align: 'right', width: '9%',formatter:'###,##0.00',
                                						render : function(rowdata, rowindex,value) {
                                 							 return formatNumber(rowdata.t_5,2,1);
                                 						}  
                                                  }, 
                                	               { display: '累计', name: 't_6', width: '9%', align: 'right',formatter:'###,##0.00',
                              						render : function(rowdata, rowindex,value) {
                              							 return formatNumber(rowdata.t_6,2,1);
                              						}

                                	                 }  
                                                       ] 
                          } 
                      ]
                   },
                   { display: '变动成本', columns:
                       [
                           { display: '成本',  columns:[
                                                      { display: '本期', name: 't_7', align: 'right', width: '9%',formatter:'###,##0.00',
                                                    	  render : function(rowdata, rowindex,value) {
                                  							 return formatNumber(rowdata.t_7,2,1);
                                  						}   
                                                      }, 
                                 	                   { display: '累计', name: 't_8', width: '9%', align: 'right',formatter:'###,##0.00',
                                                    	  render : function(rowdata, rowindex,value) {
                                  							 return formatNumber(rowdata.t_8,2,1);
                                  						} 
                                                      }  
                                                        ]
                           }, 
                           { display: '比重',   columns:[
                                                       { display: '本期', name: 't_9', align: 'right', width: '9%',formatter:'###,##0.00',
                                     						render : function(rowdata, rowindex,value) {
                                     							 return formatNumber(rowdata.t_9,2,1);
                                     						}   
                                                       }, 
                                  	                    { display: '累计', name: 't_10', width: '9%', align: 'right',formatter:'###,##0.00',
                                  	              		render : function(rowdata, rowindex,value) {
                                							 return formatNumber(rowdata.t_10,2,1);
                                						}
                                                    	   }  
                                                         ]
                           } 
                       ]
                    }
		                     ], pageSize: 10, 
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0401.do',
		           width: '100%', height: '100%', /* checkbox: true, */rownumbers:true,delayLoad :true,
		          selectRowButtonOnly:true,
		           toolbar: { items: [{ text: '查询', click: query,icon:'search'}, 
		                              { line:true }
		           ,
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
	       	//加载查询条件
	       	grid.loadData(grid.where);
       };
       
       function loadDict(){
     	  
    	   $("#year_month_begin").ligerTextBox({ width:120 });
       	
      	    autodate("#year_month_begin","yyyyMM");
   
       };
       //生成
       function creat(){
    	   
    		$.ligerDialog.open({url: 'costAnalysisC0401AddPage.do?isCheck=false', height: 300,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	   
       };
       
       //打印
//        function print(){
    	   
//     	   var exportPara = { 				
//    				usePager:false,	   		
//    				year_month_begin: $("#year_month_begin").val(),
// 				year_month_end: $("#year_month_end").val()
//    			};
   		  
//    	   $.ajax({
//    		   url:"queryAnalysisC0401.do",
//    		   type:"post",
//    		   data:exportPara,
//    		   dataType:"JSON",
//    		   success:function(res){
   			  
//    			   var data={
//    					   headers:[
//    								{ x: 0, y: 0, rowSpan: 3, colSpan: 1, displayName: "科室编码", name: "dept_code",size:90 },
//    								{ x: 1, y: 0, rowSpan: 3, colSpan: 1, displayName: "科室名称", name: "dept_name",size:120 },
//    								{ x:2,y:0,rowSpan:1,colSpan:2,displayName:"合计"},
//    								{ x:2,y :1,rowSpan:2,colSpan:1,displayName:"本期",name:"t_1",size:80,formatter: "#,##0.00",size:80},
//    								{ x:3,y :1,rowSpan:2,colSpan:1,displayName:"累计",name:"t_2",size:50,formatter: "#,##0.00",size:90},
//    								{ x:4,y :0,rowSpan:1,colSpan:4,displayName:"固定成本"},
//    								{ x:4,y :1,rowSpan:1,colSpan:2,displayName:"成本"},
//    								{ x:6,y :1,rowSpan:1,colSpan:2,displayName:"比重"},
//    								{ x:4,y :2,rowSpan:1,colSpan:1,displayName:"本期",name:"t_3",size:50,formatter: "#,##0.00",size:70},
//    								{ x:5,y :2,rowSpan:1,colSpan:1,displayName:"累计",name:"t_4",size:50,formatter: "#,##0.00",size:80},
//    								{ x:6,y :2,rowSpan:1,colSpan:1,displayName:"本期",name:"t_5",size:50,formatter: "#,##0.00",size:70},
//    								{ x:7,y :2,rowSpan:1,colSpan:1,displayName:"累计",name:"t_6",size:50,formatter: "#,##0.00",size:80},
//    								{ x:8,y :0,rowSpan:1,colSpan:4,displayName:"变动成本"},
//    								{ x:8,y :1,rowSpan:1,colSpan:2,displayName:"成本"},
//    								{ x:10,y :1,rowSpan:1,colSpan:2,displayName:"比重"},
//    								{ x:8,y :2,rowSpan:1,colSpan:1,displayName:"本期",name:"t_7",size:50,formatter: "#,##0.00",size:70},
//    								{ x:9,y :2,rowSpan:1,colSpan:1,displayName:"累计",name:"t_8",size:50,formatter: "#,##0.00",size:80},
//    								{ x:10,y :2,rowSpan:1,colSpan:1,displayName:"本期",name:"t_9",size:50,formatter: "#,##0.00",size:70},
//    								{ x:11,y :2,rowSpan:1,colSpan:1,displayName:"累计",name:"t_10",size:50,formatter: "#,##0.00",size:80}
//    								],
//    					    rows: res.Rows
//    			   }
//    			   viewPrint(data, "科室成本习性分析表(变动性)");
//    		   },
//    		   error: function (res) {
//    					console.error(res);
//    				}
//    	   });

    	   
    	   
    	   
//        };
         function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "科室成本习性分析表表(变动性)",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c04.C04Service",
 	   			method_name: "queryC0401Print",
 	   			bean_name: "c04Service",
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
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
	 <div class="sample-turtorial" style="display: none"></div>
</body>
</html>