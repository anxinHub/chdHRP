<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
    	  grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目', name: 'item_name', align: 'left',width:'20%'},
                     { display: '行次', name: '', align: 'center',width:'10%',
                    	render: function(rowdata,rowindex,value){
                    		if(rowdata.item_name=='总计'){
                    			return '';
                    		}else{
                    			return rowindex+1;
                    		}
                    	}	 
                     },
                     { display: '医疗业务成本', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'本期',name:'t_1',align:'right',formatter:'###,##0.00',
	                    	 			render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_1,2,1);
									}
                      				},
						 			{display:'累计',name:'t_2',align:'right',formatter:'###,##0.00',
                        			 render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.t_2,2,1);
									}
                      			}
                    	         ]
					 },
					 { display: '管理费用成本', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'本期',name:'t_3',align:'right',formatter:'###,##0.00',
	                    	 			render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_3,2,1);
									}
                      				},
						 			{display:'累计',name:'t_4',align:'right',formatter:'###,##0.00',
                        			 render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.t_4,2,1);
									}
                      			}
                    	         ]
					 },
// 					 { display: '财政支出', name: '', align: 'center',width:'20%',
//                     	 columns:[
//                     	          {display:'本期',name:'t_5',align:'right',
// 	                    	 			render : function(rowdata, rowindex,
// 												value) {
// 									 	return formatNumber(rowdata.t_5,2,1);
// 									}
//                       				},
// 						 			{display:'累计',name:'t_6',align:'right',
//                         			 render : function(rowdata, rowindex,
// 										value) {
// 									 return formatNumber(rowdata.t_6,2,1);
// 									}
//                       			}
//                     	         ]
// 					 },
// 					 { display: '科教支出', name: '', align: 'center',width:'20%',
//                     	 columns:[
//                     	          {display:'本期',name:'t_7',align:'right',
// 	                    	 			render : function(rowdata, rowindex,
// 												value) {
// 									 	return formatNumber(rowdata.t_7,2,1);
// 									}
//                       				},
// 						 			{display:'累计',name:'t_8',align:'right',
//                         			 render : function(rowdata, rowindex,
// 										value) {
// 									 return formatNumber(rowdata.t_8,2,1);
// 									}
//                       			}
//                     	         ]
// 					 }
// 					 ,
					 { display: '合计', name: '', align: 'center',width:'20%',
                    	 columns:[
                    	          {display:'本期',name:'t_9',align:'right',formatter:'###,##0.00',
	                    	 			render : function(rowdata, rowindex,
												value) {
									 	return formatNumber(rowdata.t_9,2,1);
									}
                      				},
						 			{display:'累计',name:'t_10',align:'right',formatter:'###,##0.00',
                        			 render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.t_10,2,1);
									}
                      			}
                    	         ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0303.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id: 'print',click: print, icon: 'print'}
                     	/* ,
    					{ text: '生成', id:'add', click: itemclick, icon:'add' },
    	                { line:true } */
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costAnalysisC0303AddPage.do?isCheck=false', height: 300,width: 500, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
            }   
        }
        
    }
   
   
    function loadDict(){
    	
    	$("#year_month_begin").ligerTextBox({ width:120 });
    	$("#year_month_end").ligerTextBox({ width:120 });
    	
   	    autodate("#year_month_begin","yyyyMM");
      	 
   	    autodate("#year_month_end","yyyyMM");
    	
	}   
    
     function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		           {"cell":0,"value":"统计年月："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"} 
 	    	]};
 	       var printPara={
 	      		title: "医院医疗成本分类构成表",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.AnalysisService", 
 	   			method_name: "queryAnalysisC0303print",
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

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<div class="sample-turtorial" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
            <td align="left" class="l-table-edit-td">至：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_end" type="text" id="year_month_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
         
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
