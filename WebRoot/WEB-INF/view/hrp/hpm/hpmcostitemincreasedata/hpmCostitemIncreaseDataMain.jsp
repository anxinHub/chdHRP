<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>

<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	 $("#acct_yearm").ligerTextBox({ width:160 });
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'cost_item_code',value:liger.get("cost_item_code").getValue()}); 
    	$("#resultPrint > table > tbody").html("");
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '支出项目编码', name: 'cost_item_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
							return "<a href='#' onclick=\"openUpdate('"+rowdata.cost_item_code+"','"+rowdata.acct_year+"','"+rowdata.acct_month+"');\" >"+rowdata.cost_item_code+"</a>";
			           }
					 },
					 { display: '支出项目名称', name: 'cost_item_name', align: 'left'
					 },
                     { display: '支出金额', name: 'prim_cost', align: 'left'
					 },
                     { display: '计提支出', name: 'prim_cost_ret', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmCostitemIncreaseData.do',
                     width: '100%',height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                { text: '生成', id:'init', click: itemclick,icon:'bookpen' },
    	                { line:true },
    	                { text: '计算', id:'calculate', click: itemclick,icon:'add' },
    	                { line:true },
    	                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
    	                { line:true },
    	                {text : '导入',id : 'import',click : itemclick,icon : 'up'	}, 
		                {line : true},
    	                { text: '打印', id:'print', click: printDate,icon:'print' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					openUpdate(rowdata.cost_item_code,rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
  

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'hpmCostitemIncreaseDataAddPage.do?isCheck=false', height: 250,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostitemIncreaseData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var checkIds =[];
                        $(data).each(function (){
                        	checkIds.push(this.cost_item_code+";"+this.acct_year+";"+this.acct_month);//实际代码中temp替换主键
                        });
                    	 $.ligerDialog.confirm('确定删除?', function (yes){
                 			ajaxJsonObjectByUrl("deleteHpmCostitemIncreaseData.do",{checkIds:checkIds.toString()},function (responseData){
                 				if(responseData.state=="true"){
                          			query();
                     		} 
                 			})
    				  });
                    }
                    return;
                case "calculate":
                	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
	                	var checkIds =[];
	                    $(data).each(function (){
	                    	checkIds.push(this.cost_item_code+";"+this.acct_year+";"+this.acct_month+";"+this.prim_cost);//实际代码中temp替换主键
	                    });
	                    ajaxJsonObjectByUrl("increaseCalculate.do?isCheck=false",{checkIds:checkIds.toString()},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
                    }    
                	return;
                case "downTemplate":location.href = "downTemplateCostitemIncreaseData.do?isCheck=false";return;

                case "import":
                	$.ligerDialog.open({url: 'costitemIncreaseDataImportPage.do?isCheck=false', height: 430,width: 760, isResize:true, title:'导入'});
                	return;
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
                case "init":
                	var acct_yearm=$("#acct_yearm").val();
                	var cost_item_code = liger.get("cost_item_code").getValue();
                	if(acct_yearm==''){
                		$.ligerDialog.error('请选择核算年月');
                		return ;
                	}
                	
                	var formPara={
                			acct_yearm:acct_yearm,
                			cost_item_code:cost_item_code
                      };
                	 var checkIds =[];
                     $(data).each(function (){
                     	checkIds.push($("#acct_yearm").val()+","+cost_item_code);
                     });
                     ajaxJsonObjectByUrl("getCostItemIncreaseValue.do?isCheck=false", formPara, function(responseData) {
                 		if (responseData.state == "false") {
                 			$.ligerDialog.open({url: 'costItemDataIncreaseChoosePage.do?isCheck=false&checkIds='+checkIds, height: 200,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostItemTargetConf(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
      
                 		}else{
                 			$.ligerDialog.error('该月指标已审核，不能进行生成');
                 		}
                 	})
                	return;
				
            }   
        }
        
    }
    function openUpdate(cost_item_code,acct_year,acct_month){
    	var formPara={
    			acct_yearm:acct_year+""+acct_month,
    			cost_item_code:cost_item_code
          };
    	 ajaxJsonObjectByUrl("getCostItemIncreaseValue.do?isCheck=false", formPara, function(responseData) {
      		if (responseData.state == "false") {
      			$.ligerDialog.open({ url: 'costitemIncreaseDataUpdatePage.do?isCheck=false&cost_item_code='+cost_item_code+'&acct_year='+acct_year+'&acct_month='+acct_month,
        			data:{}, height: 250,width:500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostitemIncreaseData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

      		}else{
      			$.ligerDialog.error('该月指标已审核，不能进行生成');
      		}
      	})
    
    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#cost_item_code","../queryCostItemSeq.do?isCheck=false","id","text",true,true);
         }   
    
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","全院支出数据准备打印.开始打印","全院支出数据准备列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		ajaxJsonObjectByUrl("queryCostitemIncreaseData.do",{usePager:false,acct_yearm:$("#acct_yearm").val(),cost_item_code:liger.get("cost_item_code").getValue()},function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.cost_item_code+"</td>";
				 trHtml+="<td>"+item.cost_item_name+"</td>";
				 trHtml+="<td>"+item.prim_cost+"</td>";
				 trHtml+="<td>"+item.prim_cost_ret+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","全院支出数据准备打印.开始打印","全院支出数据准备列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","全院支出数据准备导出Excel","全院支出数据准备列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		
		ajaxJsonObjectByUrl("queryCostitemIncreaseData.do",{usePager:false,acct_yearm:$("#acct_yearm").val(),cost_item_code:liger.get("cost_item_code").getValue()},function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.cost_item_code+"</td>";
				 trHtml+="<td>"+item.cost_item_name+"</td>";
				 trHtml+="<td>"+item.prim_cost+"</td>";
				 trHtml+="<td>"+item.prim_cost_ret+"</td>";
				 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","全院支出数据准备导出Excel","全院支出数据准备列表.xls",true);
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>
				<tr>
					<th width="200">支出项目编码</th>
					<th width="200">支出项目名称</th>
					<th width="200">支出金额</th>
					<th width="200">计提支出</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
