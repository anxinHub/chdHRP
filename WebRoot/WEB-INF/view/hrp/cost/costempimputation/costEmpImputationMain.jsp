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
    	var dept_id = liger.get("dept_id").getValue();
        
    	var cost_item = liger.get("cost_item_id").getValue();
      	  
        if(dept_id !=null && dept_id!=''){
   	         grid.options.parms.push({name:'dept_id',value:dept_id.split(".")[0]}); 
   	         grid.options.parms.push({name:'dept_no',value:dept_id.split(".")[1]}); 
        }
    	grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
		if(cost_item !=null && cost_item !=''){
    		
    		grid.options.parms.push({name:'cost_item_id',value:cost_item.split(".")[0]}); 
    		
        	grid.options.parms.push({name:'cost_item_no',value:cost_item.split(".")[1]});
		}

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left'
					 },
					 { display: '科室编码', name: 'dept_code', align: 'left'
					 },
					 { display: '科室名称', name: 'dept_name', align: 'left'
					 },
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left'
					 },
                     { display: '成本项目名称', name: 'cost_item_name', align: 'left'
					 },
                     { display: '资金来源编码', name: 'source_code', align: 'left'
					 },
                     { display: '资金来源名称', name: 'source_name', align: 'left'
					 },
                     { display: '金额', name: 'amount', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostEmpImputation.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }, 
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true }
    				]} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
               
				case "export":
                	return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
   
    function loadDict(){
            //字典下拉框
            
            autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
            
            autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
            
			$("#year_month").ligerTextBox({width:160});
         }  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","活动成本归集",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var dept = liger.get("dept_id").getValue();
		
		var cost_item = liger.get("cost_item_id").getValue();
		
		var printPara={
				
				usePager:false,

			 	dept_id:(dept !=null && dept !='')?dept.split(".")[0]:'',
             
	            dept_no:(dept !=null && dept !='')?dept.split(".")[1]:'',
	            
	           	year_month:$("#year_month").val(),
	            
	           	cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
	                    
	            cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:''
            
         };

		ajaxJsonObjectByUrl("queryCostEmpImputation.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.year_month+"</td>";
                     trHtml+="<td>"+item.dept_code+"</td>"; 
                     trHtml+="<td>"+item.dept_name+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
                     trHtml+="<td>"+item.source_code+"</td>";
                     trHtml+="<td>"+item.source_name+"</td>";
                     trHtml+="<td>"+item.amount+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});

			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","活动成本归集",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","活动成本归集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var dept = liger.get("dept_id").getValue();
		
		var cost_item = liger.get("cost_item_id").getValue();
		
		var exportPara={
				
				usePager:false,

			 	dept_id:(dept !=null && dept !='')?dept.split(".")[0]:'',
             
	            dept_no:(dept !=null && dept !='')?dept.split(".")[1]:'',
	            
	           	year_month:$("#year_month").val(),
	            
	           	cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
	                    
	            cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:''
            
         };
		ajaxJsonObjectByUrl("queryCostEmpImputation.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.year_month+"</td>";
	                 trHtml+="<td>"+item.dept_code+"</td>"; 
	                 trHtml+="<td>"+item.dept_name+"</td>"; 
	                 trHtml+="<td>"+item.cost_item_code+"</td>"; 
	                 trHtml+="<td>"+item.cost_item_name+"</td>"; 
	                 trHtml+="<td>"+item.source_code+"</td>";
	                 trHtml+="<td>"+item.source_name+"</td>";
	                 trHtml+="<td>"+item.amount+"</td>";  
					 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","活动成本归集.xls",true);
	    },true,manager);
		return;
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" />
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"/></td>
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
