<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        
    	grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()});  
        
		var dept_dict = liger.get("dept_id").getValue();
        
        var cost_item = liger.get("cost_item_id").getValue();
        
		if(dept_dict !=null && dept_dict !=''){
    		
    		grid.options.parms.push({name:'dept_id',value:dept_dict.split(".")[0]}); 
    		
        	grid.options.parms.push({name:'dept_no',value:dept_dict.split(".")[1]}); 
        	
    	}
		
		if(cost_item !=null && cost_item !=''){
    		
    		grid.options.parms.push({name:'cost_item_id',value:cost_item.split(".")[0]}); 
    		
        	grid.options.parms.push({name:'cost_item_no',value:cost_item.split(".")[1]}); 
        	
    	}
		    grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',render : function(rowdata, rowindex,value) {
                    	 
                    		return rowdata.acc_year+rowdata.acc_month;
                    		
            			}
//                      ,totalSummary:{
// 							type:'sum',
// 							render:function(suminf,column,cell){
// 								return '<div>总计</div>';
// 							}
// 						}
					 },
                     { display: '科室编码', name: 'dept_code', align: 'left'
					 },
                     { display: '科室名称', name: 'dept_name', align: 'left',
						 render:function(rowdata,rowindex,value){
							 return formatSpace(rowdata.dept_name, rowdata.dept_level-1, null);
						 }
					 },
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left'
					 },
                     { display: '成本项目名称', name: 'cost_item_name', align: 'left',
						 render:function(rowdata,rowindex,value){
							 return formatSpace(rowdata.cost_item_name, rowdata.item_grade-1, null);
						 }
					 },
					 { display: '资金来源编码', name: 'source_code', align: 'left'
					 },
                     { display: '资金来源名称', name: 'source_name', align: 'left'
					 }, 
                     { display: '金额', name: 'dir_amount', align: 'right',
							render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.dir_amount,2,1);
							}
// 					 ,totalSummary: {
// 		   	                      type: 'sum',
// 		   	                      render: function (suminf, column, cell) {
// 		   	                         return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
// 		   	                      }
// 		   	                 	}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostCollection.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                    	{ text: '归集', id:'CostCollection', click: addCostCollection,icon:'back' },
                     	{ line:true },
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
    				]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

   
    function loadDict(){
    	
    	$("#year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
    	autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true); 
         }  
    
    //归集 
    function addCostCollection(){
    	
    	if($("#year_month").val()==''){
    		
    		$.ligerDialog.error("统计年月不能为空");
    		
    		return false
    	}
    	
    	 var formPara={
    	            
    	          year_month : $("#year_month").val()

    	         };
        $.ligerDialog.confirm('是否归集?', function (yes){
        	           if(yes){
        	               ajaxJsonObjectByUrl("addCostCollection.do",formPara,function(responseData){      	               
        	                   if(responseData.state=="true"){
        	                   }
        	               });
        	               }
            	});
    }
    
  //打印数据
	 function printDate(){
		 $("#resultPrint > table > tbody").empty();

		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","科室支出总账采集",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		
		var printPara={
				
			usePager:false,
           
			year_month:$("#year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
                   
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
                   
           cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:'',
        		   
           source_id: liger.get("source_id").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostCollection.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.dept_code+"</td>"; 
                     trHtml+="<td>"+item.dept_name+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
                     trHtml+="<td>"+item.source_code+"</td>"; 
                     trHtml+="<td>"+item.source_name+"</td>"; 
                     trHtml+="<td>"+item.dir_amount+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","科室支出总账采集",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();

		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","科室支出总账采集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var exportPara={
				
			usePager:false,

			year_month:$("#year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
                   
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
                   
           cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:'',
        		   
        	   source_id: liger.get("source_id").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostCollection.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				  trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                  trHtml+="<td>"+item.dept_code+"</td>"; 
                  trHtml+="<td>"+item.dept_name+"</td>"; 
                  trHtml+="<td>"+item.cost_item_code+"</td>"; 
                  trHtml+="<td>"+item.cost_item_name+"</td>"; 
                  trHtml+="<td>"+item.source_code+"</td>"; 
                  trHtml+="<td>"+item.source_name+"</td>"; 
                  trHtml+="<td>"+item.dir_amount+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","科室支出总账采集.xls",true);
	    },true,manager);
		return;
	 }		
	 function print(){
		//设置表头
	   		var columnInfos = [ {
	   		 
	   			name : "year_month",
	   			displayName : "统计年月",
	   			size: 100 
	   		}, {
	   			name : "dept_code",
	   			displayName : "科室编码",
	   			formatter : "#,##0.00"
	   		}, {
	   			name : "dept_name",
	   			displayName : "科室名称",
	   			formatter : "#,##0.00"
	   		}, {
	   			name : "cost_item_code",
	   			displayName : "成本项目编码",
	   			formatter : "#,##0.00"
	   		} , {
	   		 
	   			name : "cost_item_name",
	   			displayName : "成本项目名称",
	   			size: 100 
	   		}, {
	   			name : "source_code",
	   			displayName : "资金来源编码",
	   			formatter : "#,##0.00"
	   		}, {
	   			name : "source_name",
	   			displayName : "资金来源名称",
	   			formatter : "#,##0.00"
	   		}, {
	   			name : "dir_amount",
	   			displayName : "金额",
	   			formatter : "#,##0.00"
	   		} ];
	   		var exportPara = {
	   				usePager:false,
	   				year_month:$("#year_month").val(),
	   				e_year_month:$("#e_year_month").val(),
                    dept_no:$("#dept_no").val(),
                   cost_item_no:$("#cost_item_no").val(),
                   source_id:$("#source_id").val()
	   	   		};
	   	  
	   	   		//公用部分
	   	   		viewPrintOneHead("queryCostCollection.do", exportPara, columnInfos,
	   	   				"成本归集", 2000); 	 
	 }
	 
			
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" /></td>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
        <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" /></td>
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">科室编码</th>
					<th width="200">科室名称</th>
					<th width="200">成本项目编码</th>
					<th width="200">成本项目名称</th>
					<th width="200">资金来源编码</th>
					<th width="200">资金来源名称</th>
					<th width="200">金额</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
