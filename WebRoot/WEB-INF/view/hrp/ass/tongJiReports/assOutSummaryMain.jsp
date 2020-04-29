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
<!-- 资产出库汇总 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
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
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split("@")[1]});
    	
    	grid.options.parms.push({name:'out_date_beg',value:$('#out_date_beg').val()});
    	grid.options.parms.push({name:'out_date_end',value:$('#out_date_end').val()});
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_id").getValue().split("@")[0]});
    	grid.options.parms.push({name : 'store_no',value : liger.get("store_id").getValue().split("@")[1]});
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
	}
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '科室编码', name: 'dept_code', align: 'left'
	                 },
	        	     { display: '领用科室', name: 'dept_name', align: 'left'
	                 },
					 { display: '金额', name: 'sum_price', align: 'left',
						 /* totalSummary:{render: function (suminf, column, cell)
		                    {
	                            return  formatNumber(suminf.sum,'${ass_05005 }',1) ;
	                        },
	                     align: 'left'}, */
						 render : function(rowdata, rowindex,value) {
							return formatNumber(value,'${ass_05005 }',1);
						 }
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssOutSummary.do',
                     width: '100%', height: '100%', rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     /* groupColumnName:'dept_code',groupColumnDisplay:'科室编码', */
                     toolbar: { 
                    	 items: [
                            { text: '查询', id:'sum', click: query,icon:'search' },
                            { line:true },
						    { text: '打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' }
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //字典下拉框
    function loadDict(){
    	var param = {query_key:''};
    	autocomplete("#dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	
    	autodate("#out_date_beg","YYYY-mm-dd","month_first");

		autodate("#out_date_end","YYYY-mm-dd","month_last");
    	$("#out_date_beg,#out_date_end").ligerTextBox({width : 80});
    	autocomplete("#store_id", "../queryHosStoreDict.do?isCheck=false","id", "text",true,true,param,true,null,"180");
    } 
    //键盘事件
	function loadHotkeys() {
	}
    
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date = $('#out_date_beg').val()+"至"+$('#out_date_end').val();
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
        		          {"cell":0,"value":"报表日期:"},
         				  {"cell":1,"value":date} ,
		    	          
		    	        
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制表人:"},
    				{"cell":1,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产出库汇总月报表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.tongJiReports.AssOutSummaryService",
 				method_name: "queryAssOutMainSummaryPrint",
 				bean_name: "assOutSummaryService" ,
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

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">出库日期：</td>
            <td align="left" class="l-table-edit-td" >
	            <div style="float:left;">
	            	<input name="out_date_beg" class="Wdate" type="text" id="out_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
	            </div>
	            <span style="float:left;margin: 0 3px;">至</span>
	            <div style="float:left;">
	            	<input name="out_date_end" class="Wdate" type="text" id="out_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
				</div>
			</td>
			<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">领用科室：</td>
		    <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  /></td>
        </tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
