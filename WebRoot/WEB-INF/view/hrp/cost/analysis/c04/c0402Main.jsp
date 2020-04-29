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
                  { display: '科室', name: 'CompanyName', minWidth: 60, width: '10%',align:'left' },
                  { display: '合计', columns:
   	               [
   	                   { display: '本期', name: 't1', align: 'right', width: '9%' }, 
   	                   { display: '累计', name: 't2', width: '9%', align: 'right' } 
   	               ]
                  },
                  { display: '固定成本', columns:
                      [
                          { display: '成本', columns:[
                                                  { display: '本期', name: 't3', align: 'right', width: '9%' }, 
                             	                   { display: '累计', name: 't4', width: '9%', align: 'right' }  
                                                    ] 
                          }, 
                          { display: '比重',  columns:[
                                                  { display: '本期', name: 't5', align: 'right', width: '9%' }, 
                                	               { display: '累计', name: 't6', width: '9%', align: 'right' }  
                                                       ] 
                          } 
                      ]
                   },
                   { display: '变动成本', columns:
                       [
                           { display: '成本',  columns:[
                                                      { display: '本期', name: 't7', align: 'right', width: '9%' }, 
                                 	                   { display: '累计', name: 't8', width: '9%', align: 'right' }  
                                                        ]
                           }, 
                           { display: '比重',   columns:[
                                                       { display: '本期', name: 't9', align: 'right', width: '9%' }, 
                                  	                    { display: '累计', name: 't10', width: '9%', align: 'right' }  
                                                         ]
                           } 
                       ]
                    }
		                     ], pageSize: 10, 
		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0402.do',
		           width: '100%', height: '100%'/* , checkbox: true */,rownumbers:true,delayLoad :true,
		           selectRowButtonOnly:true,
		           //allowHideColumn: false,
		             		           toolbar: { items: [{ text: '查询', click: query,icon:'search'}, 
		                              { line:true },
		                             /*  { text: '生成', click: creat, icon: 'add'},
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
       	   grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
       	   grid.options.parms.push({name:'acc_month_begin',value:$("#acc_month_begin").val()}); 
       	   grid.options.parms.push({name:'acc_month_end',value:$("#acc_month_end").val()}); 
       	   
       	   
	       	//加载查询条件
	       	grid.loadData(grid.where);
       };
       
       function loadDict(){
      	  
    	   $("#acc_year").ligerTextBox({ width:120 });
      	  $("#acc_month").ligerTextBox({ width:90 });

      	  autodate("#acc_year","yyyy");
      	  
      	  autodate("#acc_month","MM");
      
        };
       
       //生成
       function creat(){
    	   
    	   $.ligerDialog.open({url: 'costAnalysisC0402AddPage.do?isCheck=false', height: 300,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	   
       };
       
       //打印
       function print(){};
</script>
</head>
<body>
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年</td>
           <td align="left" class="l-table-edit-td"><input name="acc_month_begin" type="text" id="acc_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月 </td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>