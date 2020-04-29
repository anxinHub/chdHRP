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
    	$("#year_month").ligerTextBox({ width:160 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
			grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
            
    		grid.options.parms.push({name:'source',value:liger.get("source").getValue()}); 
        
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
    		grid.options.parms.push({name:'source_id',value:liger.get("source").getValue()}); 
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
                     { display: '直接成本', name: 'amount', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDeptDriData.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true }
    				]}/* ,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.year_month   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.dept_no   + "|" + 
								rowdata.cost_item_id   + "|" + 
								rowdata.cost_item_no   + "|" + 
								rowdata.source 
							);
    				}  */
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costDeptDriDataAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDeptDriData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.year_month   +"@"+ 
							this.dept_id   +"@"+ 
							this.dept_no   +"@"+ 
							this.cost_item_id   +"@"+ 
							this.cost_item_no   +"@"+ 
							this.source 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostDeptDriData.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costDeptDriDataImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
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
   /*  function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"year_month="+vo[3]   +"&"+ 
			"dept_id="+vo[4]   +"&"+ 
			"dept_no="+vo[5]   +"&"+ 
			"cost_item_id="+vo[6]   +"&"+ 
			"cost_item_no="+vo[7]   +"&"+ 
			"source="+vo[8] 
    	$.ligerDialog.open({ url : 'costDeptDriDataUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDeptDriData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    } */
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
    	autocomplete("#source","../querySourceArrt.do?isCheck=false","id","text",true,true); 
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
			usePager:false,
            
           year_month:$("#year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
      	            
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
   	            
    	   cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:'',
            
           source_id:liger.get("source").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostDeptDriData.do",printPara,function (responseData){
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
			lodopPrinterTable("resultPrint","开始打印","列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
			usePager:false,
            
           year_month:$("#year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
      	            
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
   	            
    	   cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:'',
            
           source_id:liger.get("source").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostDeptDriData.do",exportPara,function (responseData){
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
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
            <td align="left" class="l-table-edit-td"><input name="source" type="text" id="source" /></td>
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
					<th width="200">直接成本</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
