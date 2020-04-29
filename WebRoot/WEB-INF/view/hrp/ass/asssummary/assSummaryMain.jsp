<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;

	$(function() {
		
		loadDict();//加载下拉框
		
		/* loadHotkeys(); */
		
		//加载数据
		loadHead(null); 
		
	});
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件 
		/* grid.options.parms.push({ name : '', value : $("#").val() }); */
		
	grid.options.parms.push({name:'year_month',value:$("#plan_year").val()}); 

		//加载查询条件
		grid.loadData(grid.where);
		loadHead();
		 $("#resultPrint > table > tbody").empty();
	}
	
	/* function addNewRow() {
		var manager = $("#maingrid").ligerGetGridManager();
		manager.addEditRow();
	} */
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ 
					/* {
						display : '业务类型',
						 
						align : 'center',
						columns:[
                                 {name:'bus_type',align:'left',width:'120',},
                                 {name:'bus_type1',align:'left',width:'120',}
                                 ]
					}, */
					{
						display : '业务类型',
						name : 'bus_type1',
						align : 'left',width: '150',
					},
					{
						display : '资金来源',
						name : 'source_name',
						align : 'left',width: '150',
					}, {
						display : '专用设备',
						name : 's_price',
						align : 'left',width: '100',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.s_price,'${ass_05005 }',1);
							}
					}, {
						display : '一般设备',
						name : 'g_price',
						align : 'left',width: '100',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.g_price,'${ass_05005 }',1);
							}
					}, {
						display : '其他固定资产',
						name : 'h_price',
						align : 'left',width: '100',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.h_price,'${ass_05005 }',1);
							}
					}, {
						display : '其他无形资产',
						name : 'o_price',
						align : 'left',width: '100',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.o_price,'${ass_05005 }',1);
							}
					}, {
						display : '房屋及建筑物',
						name : 'i_price',
						align : 'left',width: '100',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.i_price,'${ass_05005 }',1);
							}
					}, {
						display : '土地使用权',
						name : 'l_price',
						align : 'right',width: '120',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.l_price,'${ass_05005 }',1);
							}
					}, {
						display : '合计',
						
						align : 'left',width: '100',
							 render : function(rowdata, rowindex,
										value) {
									 return formatNumber(rowdata.g_price=='' ? 0:rowdata.g_price +rowdata.h_price == '' ? 0 : rowdata.h_price+rowdata.o_price =='' ? 0:rowdata.o_price +rowdata.i_price == '' ? 0 :rowdata.i_price +rowdata.l_price=='' ? 0:rowdata.l_price+rowdata.s_price=='' ? 0:rowdata.s_price,'${ass_05005 }',1);
								},formatter:'###,##0.00'
					} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssSummary.do?isCheck=false',
					width : '100%',
					height : '100%',
					alternatingRow : true,
					isScroll : true,
					checkbox : true,
					rownumbers : true,
					delayLoad :true,
					selectRowButtonOnly : true,
					checkBoxDisplay : isCheckDisplay,
					 toolbar: { items : [ { text: '查   询', id:'search', click: query,icon:'search' },
	              						     { line:true },
	             						      { text: '打   印', id:'print', click: printDate,icon:'print' }
					    				]},
				/* 	onDblClickRow : function(rowdata, rowindex, value) {
						if(rowdata.summary == '合计'){
							return;
						}else{
							openUpdate(
									rowdata.group_id + "|" + 
									rowdata.hos_id+ "|" + 
									rowdata.copy_code + "|"+ 
									rowdata.apply_id +"|"+
									rowdata.apply_no );
						}
						
					} */
				});
		
		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
	function isCheckDisplay(rowdata) {
		if (rowdata.summary == "合计")
			return false;
		return true;
	}
	
	
	//打印数据
 	function printDate(){
 		 if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		} 
 		
 		var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"会计期间："},
    	          {"cell":1,"value":$("#plan_year").val()},
    	          {"cell":7,"value":"制表日期:"},
  				  {"cell":8,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":7,"value":"制表人:"},
    				{"cell":8,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "全院资产变动汇总表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.summary.AssSummaryService",
 				method_name: "queryAssSummaryMainPrint",
 				bean_name: "assSummaryService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
	
	
	 function loadDict(){
	    	
			var param = {
	            	query_key:''
	        };
			
	            //字典下拉框
			
			/* autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false","id", "text",true,true,param,true);
			
	       
		      
	        $("#ass_year").ligerTextBox({width:70});
	        
	        $("#ass_month").ligerTextBox({width:70});
	        
	        $("#ass_month1").ligerTextBox({width:50});
	        
			autodate("#ass_year","YYYY");
			
			autodate("#ass_month","MM");
			
			autodate("#ass_month1","MM");
			$('#plan_year').ligerTextBox({width:100})
			;
			$("#plan_year").val('${ass_year_month-1 }')
			 */
			$("#plan_year").ligerTextBox({width:120});
			if(${ass_year_month}){
				$("#plan_year").val('${ass_year_month }')
	        }else{
	        	autodate("#plan_year","YYYYMM"); 
	        }   
	 
	 }  
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
        <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_year" type="text" id="plan_year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="left"></td>
			<!-- <td align="left" ></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产归属：</td>
			<td align="left" class="l-table-edit-td"><input
				name="dept_id" type="text" id="dept_id" 
				 /></td>
				 
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">级别：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="" type="text" id=""  /></td>  -->
        </tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
