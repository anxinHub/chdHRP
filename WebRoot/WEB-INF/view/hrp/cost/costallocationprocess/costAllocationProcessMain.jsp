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
    
    $(function (){
    	
		loadDict();
		
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){//根据表字段进行添加查询条件
    	
			grid.options.parms=[];grid.options.newPage=1;
        
			grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
            
    		grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 
        
    		var dept_dict = liger.get("dept_id").getValue();
    		
    		var server_dept_dict = liger.get("server_dept_id").getValue();
            
            var cost_item = liger.get("cost_item_id").getValue();
            
    		if(dept_dict !=null && dept_dict !=''){
        		
        		grid.options.parms.push({name:'dept_id',value:dept_dict.split(".")[0]}); 
            	
        	}
    		
			if(server_dept_dict !=null && server_dept_dict !=''){
        		
        		grid.options.parms.push({name:'server_dept_id',value:server_dept_dict.split(".")[0]}); 
        		
        	}
    		
    		if(cost_item !=null && cost_item !=''){
        		
        		grid.options.parms.push({name:'cost_item_id',value:cost_item.split(".")[0]}); 
            	
        	}
    		
    	grid.loadData(grid.where);//加载查询条件
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
 			 				return rowdata.acc_year+rowdata.acc_month;
 			 			},
					    width : 80,frozen: true
					 },
                     { display: '服务科室编码', name: 'dept_code', align: 'left',
						    width : 80,frozen: true},
                     
                     { display: '服务科室名称', name: 'dept_name', align: 'left',
							    width : 80,frozen: true },
                     
					 { display: '受益科室编码', name: 'server_dept_code', align: 'left',
								    width : 80,frozen: true},
                     
                     { display: '受益科室名称', name: 'server_dept_name', align: 'left',
									    width : 80,frozen: true },
                     
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left' ,
										    width : 80,frozen: true},
                     
                     { display: '成本项目名称', name: 'cost_item_name', align: 'left',
											    width : 80,frozen: true},
                     
                     { display: '资金来源编码', name: 'source_code', align: 'left' ,
												    width : 80,frozen: true},
                     
					 { display: '资金来源名称', name: 'source_name', align: 'left',
													    width : 80,frozen: true},
					 
                     { display: '服务科室直接成本', name: 'dir_amount', align: 'right',
						 
						 render : function(rowdata, rowindex,value) {
							    return '<div>' + formatNumber(rowdata.dir_amount ==null ? 0 : rowdata.dir_amount,2,1)+ '</div>';
			                     
	 			 	 	 },width : 100
                     },
                     {
							display : '间接管理成本',
							name : "",
							aling : "center",
							columns : [
									{
										display : '间接分摊管理直接成本',
										name : 'dir_man_amount',
										align : 'right',
										render : function(rowdata,
												rowindex, value) {
											return formatNumber(
													rowdata.dir_man_amount,
													2, 1)
										},
										width : 90
									},
									{
										display : '间接分摊医辅管理成本',
										name : 'indir_ass_man_amount',
										align : 'right',
										render : function(rowdata,
												rowindex, value) {
											return formatNumber(
													rowdata.indir_ass_man_amount,
													2, 1)
										},
										width : 90
									},
									{
										display : '间接分摊医技管理成本',
										name : 'indir_med_man_amount',
										align : 'right',
										render : function(rowdata,
												rowindex, value) {
											return formatNumber(
													rowdata.indir_med_man_amount,
													2, 1)
										},
										width : 90
									},
									{
										display : '间接分摊医技医辅管理成本',
										name : 'indir_ass_med_man_amount',
										align : 'right',
										render : function(rowdata,
												rowindex, value) {
											return formatNumber(
													rowdata.indir_ass_med_man_amount,
													2, 1)
										},
										width : 90
									} ]

						},
						{
							display : "间接医辅成本",
							name : "",
							aling : 'center',
							columns : [

									{
										display : '分摊医辅直接成本',
										name : 'dir_ass_amount',
										align : 'right',
										render : function(rowdata,
												rowindex, value) {
											return formatNumber(
													rowdata.dir_ass_amount,
													2, 1)
										},
										width : 90
									},
									{
										display : '间接分摊医技医辅成本',
										name : 'indir_med_ass_amount',
										align : 'right',
										render : function(rowdata,
												rowindex, value) {
											return formatNumber(
													rowdata.indir_med_ass_amount,
													2, 1)
										},
										width : 90
									} ]

						},
						{
							display : '分摊医技成本',
							name : 'dir_med_amount',
							align : 'right',
							render : function(rowdata, rowindex,
									value) {
								return formatNumber(
										rowdata.dir_med_amount, 2,
										1)
							},width : 90
						}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostAllocationProcess.do',width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
		                { text: '打印', id:'print', click: print,icon:'print' }
    				]}
                   });
			gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){//字典下拉框
    	
    	autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    	autocomplete("#server_dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    
    	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
    	
    	autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true); 
    	
    	$("#year_month").ligerTextBox({ width:160 });
	    $("#e_year_month").ligerTextBox({ width:160});
	 		 
		 autodate("#year_month","yyyyMM");
    	
	}  

	 function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#year_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "科室分摊过程明细",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostAllocationProcessService",
	 	   			method_name: "queryCostAllocationProcessPrint",
	 	   			bean_name: "costAllocationProcessService",
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

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
				
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">服务科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">受益科室：</td>
			<td align="left" class="l-table-edit-td"><input name="server_dept_id"
				type="text" id="server_dept_id" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="cost_item_id" type="text" id="cost_item_id" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资金来源：</td>
			<td align="left" class="l-table-edit-td"><input name="source_id"
				type="text" id="source_id" /></td>
		</tr>
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
