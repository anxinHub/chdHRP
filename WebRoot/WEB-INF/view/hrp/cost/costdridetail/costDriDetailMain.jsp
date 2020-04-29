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
    	grid.options.parms.push({name:'dept_id',value:$("#dept_id").val()}); 
    	grid.options.parms.push({name:'dept_no',value:$("#dept_no").val()}); 
    	grid.options.parms.push({name:'server_dept_id',value:$("#server_dept_id").val()}); 
    	grid.options.parms.push({name:'server_dept_no',value:$("#server_dept_no").val()}); 
    	grid.options.parms.push({name:'cost_item_id',value:$("#cost_item_id").val()}); 
    	grid.options.parms.push({name:'cost_item_no',value:$("#cost_item_no").val()}); 
    	grid.options.parms.push({name:'source',value:$("#source").val()}); 
    	grid.options.parms.push({name:'dri_amount',value:$("#dri_amount").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#year_month").val()!=""){
                		return rowdata.year_month.indexOf($("#year_month").val()) > -1;	
                	}
                	if($("#dept_id").val()!=""){
                		return rowdata.dept_id.indexOf($("#dept_id").val()) > -1;	
                	}
                	if($("#dept_no").val()!=""){
                		return rowdata.dept_no.indexOf($("#dept_no").val()) > -1;	
                	}
                	if($("#server_dept_id").val()!=""){
                		return rowdata.server_dept_id.indexOf($("#server_dept_id").val()) > -1;	
                	}
                	if($("#server_dept_no").val()!=""){
                		return rowdata.server_dept_no.indexOf($("#server_dept_no").val()) > -1;	
                	}
                	if($("#cost_item_id").val()!=""){
                		return rowdata.cost_item_id.indexOf($("#cost_item_id").val()) > -1;	
                	}
                	if($("#cost_item_no").val()!=""){
                		return rowdata.cost_item_no.indexOf($("#cost_item_no").val()) > -1;	
                	}
                	if($("#source").val()!=""){
                		return rowdata.source.indexOf($("#source").val()) > -1;	
                	}
                	if($("#dri_amount").val()!=""){
                		return rowdata.dri_amount.indexOf($("#dri_amount").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left'
					 },
                     { display: '服务科室', name: 'dept_id', align: 'left'
					 },
                     { display: '服务科室变更ID', name: 'dept_no', align: 'left'
					 },
                     { display: '受益科室', name: 'server_dept_id', align: 'left'
					 },
                     { display: '受益科室变更ID', name: 'server_dept_no', align: 'left'
					 },
                     { display: '成本项目ID', name: 'cost_item_id', align: 'left'
					 },
                     { display: '成本项目变更ID', name: 'cost_item_no', align: 'left'
					 },
                     { display: '资金来源', name: 'source', align: 'left'
					 },
                     { display: '平级分摊成本', name: 'dri_amount', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDriDetail.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.year_month   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.dept_no   + "|" + 
								rowdata.server_dept_id   + "|" + 
								rowdata.server_dept_no   + "|" + 
								rowdata.cost_item_id   + "|" + 
								rowdata.cost_item_no   + "|" + 
								rowdata.source 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costDriDetailAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDriDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.server_dept_id   +"@"+ 
							this.server_dept_no   +"@"+ 
							this.cost_item_id   +"@"+ 
							this.cost_item_no   +"@"+ 
							this.source 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostDriDetail.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costDriDetailImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"year_month="+vo[3]   +"&"+ 
			"dept_id="+vo[4]   +"&"+ 
			"dept_no="+vo[5]   +"&"+ 
			"server_dept_id="+vo[6]   +"&"+ 
			"server_dept_no="+vo[7]   +"&"+ 
			"cost_item_id="+vo[8]   +"&"+ 
			"cost_item_no="+vo[9]   +"&"+ 
			"source="+vo[10] 
    	$.ligerDialog.open({ url : 'costDriDetailUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDriDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
			$("#year_month").ligerTextBox({});
			$("#dept_id").ligerTextBox({});
			$("#dept_no").ligerTextBox({});
			$("#server_dept_id").ligerTextBox({});
			$("#server_dept_no").ligerTextBox({});
			$("#cost_item_id").ligerTextBox({});
			$("#cost_item_no").ligerTextBox({});
			$("#source").ligerTextBox({});
			$("#dri_amount").ligerTextBox({});
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
		
		var printPara={
			usePager:false,
            
            
            
           year_month:$("#year_month").val(),
            
           dept_id:$("#dept_id").val(),
            
           dept_no:$("#dept_no").val(),
            
           server_dept_id:$("#server_dept_id").val(),
            
           server_dept_no:$("#server_dept_no").val(),
            
           cost_item_id:$("#cost_item_id").val(),
            
           cost_item_no:$("#cost_item_no").val(),
            
           source:$("#source").val(),
            
           dri_amount:$("#dri_amount").val()
            
         };
		ajaxJsonObjectByUrl("queryCostDriDetail.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.year_month+"</td>"; 
                     trHtml+="<td>"+item.dept_id+"</td>"; 
                     trHtml+="<td>"+item.dept_no+"</td>"; 
                     trHtml+="<td>"+item.server_dept_id+"</td>"; 
                     trHtml+="<td>"+item.server_dept_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_id+"</td>"; 
                     trHtml+="<td>"+item.cost_item_no+"</td>"; 
                     trHtml+="<td>"+item.source+"</td>"; 
                     trHtml+="<td>"+item.dri_amount+"</td>";
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
		
		var exportPara={
			usePager:false,
           year_month:$("#year_month").val(),
           dept_id:$("#dept_id").val(),
           dept_no:$("#dept_no").val(),
           server_dept_id:$("#server_dept_id").val(),
           server_dept_no:$("#server_dept_no").val(),
           cost_item_id:$("#cost_item_id").val(),
           cost_item_no:$("#cost_item_no").val(),
           source:$("#source").val(),
           dri_amount:$("#dri_amount").val()
         };
		ajaxJsonObjectByUrl("queryCostDriDetail.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.year_month+"</td>"; 
                     trHtml+="<td>"+item.dept_id+"</td>"; 
                     trHtml+="<td>"+item.dept_no+"</td>"; 
                     trHtml+="<td>"+item.server_dept_id+"</td>"; 
                     trHtml+="<td>"+item.server_dept_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_id+"</td>"; 
                     trHtml+="<td>"+item.cost_item_no+"</td>"; 
                     trHtml+="<td>"+item.source+"</td>"; 
                     trHtml+="<td>"+item.dri_amount+"</td>";
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
            <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">服务科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">服务科室变更ID：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">受益科室：</td>
            <td align="left" class="l-table-edit-td"><input name="server_dept_id" type="text" id="server_dept_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">受益科室变更ID：</td>
            <td align="left" class="l-table-edit-td"><input name="server_dept_no" type="text" id="server_dept_no" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目变更ID：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_no" type="text" id="cost_item_no" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
            <td align="left" class="l-table-edit-td"><input name="source" type="text" id="source" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">平级分摊成本：</td>
            <td align="left" class="l-table-edit-td"><input name="dri_amount" type="text" id="dri_amount" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">服务科室</th>
					<th width="200">服务科室变更ID</th>
					<th width="200">受益科室</th>
					<th width="200">受益科室变更ID</th>
					<th width="200">成本项目ID</th>
					<th width="200">成本项目变更ID</th>
					<th width="200">资金来源</th>
					<th width="200">平级分摊成本</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
