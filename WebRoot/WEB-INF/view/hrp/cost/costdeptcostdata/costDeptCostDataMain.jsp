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
	    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()});  
            
    		grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 
        
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
    		
    	grid.loadData(grid.where);//加载查询条件
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
 			 				return rowdata.acc_year+rowdata.acc_month;
 			 			}
					 },
                     { display: '科室编码', name: 'dept_code', align: 'left'},
                     
                     { display: '科室名称', name: 'dept_name', align: 'left' },
                     
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left' },
                     
                     { display: '成本项目名称', name: 'cost_item_name', align: 'left'},
                     
                     { display: '资金来源编码', name: 'source_code', align: 'left' },
                     
					 { display: '资金来源名称', name: 'source_name', align: 'left'},
					 
                     { display: '直接成本', name: 'dir_amount', align: 'right',
						 
						 render : function(rowdata, rowindex,value) {
							    return '<div>' + formatNumber(rowdata.dir_amount ==null ? 0 : rowdata.dir_amount,2,1)+ '</div>';
			                     
	 			 			}
                   },
                     
                     { display: '管理分摊成本', name: 'man_amount', align: 'right' ,
						 
						 render : function(rowdata, rowindex,value) {
							    return '<div>' + formatNumber(rowdata.man_amount ==null ? 0 : rowdata.man_amount,2,1)+ '</div>';
			                     
	 			 			}
                   },
                     
                     { display: '医辅分摊成本', name: 'ass_amount', align: 'right',
	 							 
	 							 render : function(rowdata, rowindex,value) {
	 								    return '<div>' + formatNumber(rowdata.ass_amount ==null ? 0 : rowdata.ass_amount,2,1)+ '</div>';
	 				                     
	 		 			 			}
                   },
                     
                     { display: '医技分摊成本', name: 'med_amount', align: 'right',
	 		 							 
	 		 				 render : function(rowdata, rowindex,value) {
	 		 						return '<div>' + formatNumber(rowdata.med_amount ==null ? 0 : rowdata.med_amount,2,1)+ '</div>';
	 		 				                     
	 		 		 			 			}
                   }
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDeptCostData.do',width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '分摊', id:'share', click: itemclick,icon:'back' },
						{ line:true }, 
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' }
    				]}
                   });
			gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id){
                case "share":
                	var year_month = $("#year_month").val();
                	if(year_month != ""){
                		ajaxJsonObjectByUrl("deptCostShareData.do",{'year_month' : year_month},function (responseData){
                    		if(responseData.state=="true"){}
                    	});
                	}else{
                		 $.ligerDialog.error('请先选择统计年月');
                	}
                	return;
            }   
        }
        
    }

    function loadDict(){//字典下拉框
    	
    	autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    
    	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
    	
    	autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true); 
    	
    	$("#year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
    	
	}  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();//有数据直接打印
		
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
				
			usePager:false,
            
			year_month:$("#year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:$("#dept_id").val(),
            
           dept_no:$("#dept_no").val(),
            
           cost_item_id:$("#cost_item_id").val(),
            
           cost_item_no:$("#cost_item_no").val(),
            
           source_id:$("#source_id").val()
            
         };
		ajaxJsonObjectByUrl("queryCostDeptCostData.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.year_month+item.year_month+"</td>"; 
                     trHtml+="<td>"+item.dept_id+"</td>"; 
                     trHtml+="<td>"+item.dept_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_id+"</td>"; 
                     trHtml+="<td>"+item.cost_item_no+"</td>"; 
                     trHtml+="<td>"+item.source_id+"</td>"; 
                     trHtml+="<td>"+item.dir_amount+"</td>";
                     trHtml+="<td>"+item.man_amount+"</td>"; 
                     trHtml+="<td>"+item.ass_amount+"</td>"; 
                     trHtml+="<td>"+item.med_amount+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopPrinterTable("resultPrint","开始打印","列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 
		 $("#resultPrint > table > tbody").empty();

		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
			year_month:$("#year_month").val(),
			e_year_month:$("#e_year_month").val(),
           dept_id:$("#dept_id").val(),
           dept_no:$("#dept_no").val(),
           cost_item_id:$("#cost_item_id").val(),
           cost_item_no:$("#cost_item_no").val(),
           source_id:$("#source_id").val()
         };
		ajaxJsonObjectByUrl("queryCostDeptCostData.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.year_month+item.year_month+"</td>"; 
                     trHtml+="<td>"+item.dept_id+"</td>"; 
                     trHtml+="<td>"+item.dept_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_id+"</td>"; 
                     trHtml+="<td>"+item.cost_item_no+"</td>"; 
                     trHtml+="<td>"+item.source_id+"</td>"; 
                     trHtml+="<td>"+item.dir_amount+"</td>"; 
                     trHtml+="<td>"+item.man_amount+"</td>"; 
                     trHtml+="<td>"+item.ass_amount+"</td>"; 
                     trHtml+="<td>"+item.med_amount+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
	    },true,manager);
		return;
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
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
				
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室编码：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
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
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">科室</th>
					<th width="200">科室变更ID</th>
					<th width="200">成本项目ID</th>
					<th width="200">成本项目变更ID</th>
					<th width="200">资金来源</th>
					<th width="200">直接成本</th>
					<th width="200">公用分摊成本</th>
					<th width="200">管理分摊成本</th>
					<th width="200">医辅分摊成本</th>
					<th width="200">医技分摊成本</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
