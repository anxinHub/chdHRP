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
                     { display: '排序', name: 'id', align: 'center',width:'10%'
                     },
                     { display: '同比', name: '', align: 'center',
                    	 columns:[
                    	          {display:'科室名称',name:'dept_name',align:'left'},
									{display:'本期',name:'t_1',align:'right',formatter:'###,##0.00',
	                    	        	  render : function(rowdata, rowindex,
													value) {
										 	return formatNumber(rowdata.t_1,2,1);
										}
                      				},
                      				{display:'上年同期',name:'t_2',align:'right',formatter:'###,##0.00',
                      	        	  render : function(rowdata, rowindex,
  												value) {
  									 	return formatNumber(rowdata.t_2,2,1);
  									}},
  									{display:'增长率',name:'t_3',align:'right',formatter:'###,##0.00',
                        	        	  render : function(rowdata, rowindex,
    												value) {
    									 	return formatNumber(rowdata.t_3,2,1);
    									}}
                    	         ]
					 },
					 { display: '环比', name: '', align: 'center',
                    	 columns:[
                    	          {display:'科室名称',name:'dept_name_hb',align:'left'},
									{display:'本期',name:'t_4',align:'right',formatter:'###,##0.00',
	                    	        	  render : function(rowdata, rowindex,
													value) {
										 	return formatNumber(rowdata.t_4,2,1);
										}
                      				},
                      				{display:'上期',name:'t_5',align:'right',formatter:'###,##0.00',
                      	        	  render : function(rowdata, rowindex,
  												value) {
  									 	return formatNumber(rowdata.t_5,2,1);
  									}},
  									{display:'增长率',name:'t_6',align:'right',formatter:'###,##0.00',
                        	        	  render : function(rowdata, rowindex,
    												value) {
    									 	return formatNumber(rowdata.t_6,2,1);
    									}}
                    	         ]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAnalysisC0613.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function createData(){
  	   
    }
function print(){
     	
     	if(grid.getData().length==0){
     		
 			$.ligerDialog.error("请先查询数据！");
 			
 			return;
 		}
     	
     	var heads={
 	    		//"isAuto":true,//系统默认，页眉显示页码
 	    		"rows": [
 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val(),"colSpan":"5"}
 	    	]};
 	       var printPara={
 	      		title: "管理医疗成本控制排序表",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.analysis.c06.C06Service",
 	   			method_name: "queryC0613Print",
 	   			bean_name: "c06Service",
 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 	   			
 	       	};
 	      //执行方法的查询条件
 		  $.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	      });
 		
  	     officeGridPrint(printPara);

    		
     }
    
     function loadDict(){
    	 $("#year_month_begin").ligerTextBox({ width:120 });
    	 $("#year_month_end").ligerTextBox({ width:120 });  	
   	    autodate("#year_month_begin","yyyyMM");
   	    autodate("#year_month_end","yyyyMM");
 	}    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_begin" type="text" id="year_month_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td>
<!--             <td align="left" class="l-table-edit-td">至：</td> -->
<!--             <td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_end" type="text" id="year_month_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" ltype="text"/></td> -->

        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
