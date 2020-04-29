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
		f_setColumns();
    	loadHead(null);	//加载数据
    	

    });
    //查询
    function  query(){
    
    		grid.options.parms=[];
    		grid.options.newPage=1;        		 
  
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 
    	grid.options.parms.push({name:'appl_dept_code',value:liger.get("appl_dept").getValue().split(".")[2]}); 
    	grid.options.parms.push({name:'exec_dept_code',value:liger.get("exec_dept").getValue().split(".")[2]}); 
    	grid.options.parms.push({name:'charge_kind_id',value:liger.get("charge_kind_id").getValue()}); 
    	grid.options.parms.push({name:'charge_item_id',value:liger.get("charge_item_id").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
  //动态设置收入数据表头
	function f_setColumns(){ 
	  
		var columns=[
						{display: '统计年度',name : 'ACC_YEAR',width: 100,align : 'left', frozen: true},
						{display: '统计月份',name : 'ACC_MONTH',width: 100,align : 'left', frozen: true},
						{display: '开单科室编码',name : 'APPL_DEPT_ID',width: 100,align : 'left', frozen: true},
						{display: '开单科室名称',name : 'APPL_DEPT_NAME',width: 100,align : 'left', frozen: true},
						{display: '执行科室编码',name : 'EXEC_DEPT_ID',width: 100,align : 'left', frozen: true},
						{display: '执行科室名称',name : 'EXEC_DEPT_NAME',width: 100,align : 'left', frozen: true},
				             ];
		
				//公用import.jsp页面使用
					 ajaxJsonObjectByUrl("../costchargeitemarrt/queryCostChargeItemArrt.do?isCheck=false",{},function(responseData){
							//console.log(JSON.stringify(responseData))
							$.each(responseData.Rows,function(i,obj){
								//,render : function(rowdata, rowindex, value) { return formatNumber(value, 2, 1); }
								columns.push({display:obj.charge_item_name,name:'C_'+obj.charge_item_code.toUpperCase(),width: 100,align : 'right'});
								
							},false);
							 grid.set('columns', columns); 
					 });
					// console.log(JSON.stringify(columns));
			   
			    
			   
     } 

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.acc_year !="总"){
								return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code   + "|" + rowdata.acc_year   + "|" + rowdata.acc_month   + "|"+ rowdata.appl_dept_no   + "|" + rowdata.exec_dept_no   + "|" + rowdata.charge_item_id+"')>"+rowdata.acc_year+rowdata.acc_month+"</a>";
							}else {
								return rowdata.acc_year+rowdata.acc_month
							}
								
						}
                 
//                      ,totalSummary: {
//     	                    type: 'sum',
//     	                    render: function (suminf, column, cell) {
//     	                        return '<div>合计</div>';
//     	                    }
//     	                }
					 },
                     { display: '开单科室', name: 'appl_dept_name', align: 'left'
					 },
                     { display: '执行科室', name: 'exec_dept_name', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostIncomeDetailPrm.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
//     	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 		                { line:true },
		                /* { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true }, */
		                /* { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true }, */
		                { text: '导入', id:'import', click: itemclick,icon:'up' },
		                { line:true }/* ,
		                { text: '采集HIS数据', id:'impHis', click: itemclick,icon:'up' } */
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costIncomeDetailAddPage.do?isCheck=false', height: 350,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.GROUP_ID  +"@"+ 
							this.HOS_ID   +"@"+ 
							this.COPY_CODE   +"@"+ 
							this.ACC_YEAR   +"@"+ 
							this.ACC_MONTH   +"@"+ 
							this.APPL_DEPT_NO   +"@"+ 
							this.EXEC_DEPT_NO
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                        		
                            	ajaxJsonObjectByUrl("deleteCostIncomeDetailPrm.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "impHis":
                	$.ligerDialog.open({url: 'costIncomeDetailCollectPage.do?isCheck=false', height: 300,width: 300, title:'采集',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                	return;
				case "import":
					var columns=[
							{display: '统计年度',name : 'acc_year',width: 100,align : 'left'},
							{display: '统计月份',name : 'acc_month',width: 100,align : 'left'},
							{display: '开单科室编码',name : 'appl_dept_id',width: 100,align : 'left'},
							{display: '开单科室名称',name : 'appl_dept_name',width: 100,align : 'left'},
							{display: '执行科室编码',name : 'exec_dept_id',width: 100,align : 'left'},
							{display: '执行科室名称',name : 'exec_dept_name',width: 100,align : 'left'}
					             ];
					//公用import.jsp页面使用
						 ajaxJsonObjectByUrl("../costchargeitemarrt/queryCostChargeItemArrt.do?isCheck=false",{},function(responseData){
								//console.log(JSON.stringify(responseData))
								$.each(responseData.Rows,function(i,obj){
									//,render : function(rowdata, rowindex, value) { return formatNumber(value, 2, 1); }
									columns.push({display:obj.charge_item_name,name:obj.charge_item_name,width: 100,align : 'right'});
								});	
						 });
						// console.log(JSON.stringify(columns));
								var para={
										 "column":	columns  
											};
								
					importSpreadView("hrp/cost/costincomedetail/readCostIncomeDetailFilesX.do?isCheck=false",para); 
				return;
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
			"acc_year="+vo[3]   +"&"+ 
			"acc_month="+vo[4]   +"&"+ 
			"appl_dept_no="+vo[5]   +"&"+ 
			"exec_dept_no="+vo[6]   +"&"+ 
			"charge_item_id="+vo[7] 
    	$.ligerDialog.open({ url : 'costIncomeDetailUpdatePage.do?isCheck=false&' + parm,data:{}, height: 350,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeDetail(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#charge_item_id","../queryChargeItemArrt.do?isCheck=false","id","text",true,true);
    	 autocomplete("#charge_kind_id","../queryChargeKindArrt.do?isCheck=false","id","text",true,true);
    	 var appl_param = {
         		type_code:"('01')"
         };
         var exec_param = {
         		type_code:"('01','02')"
         };
 		 autocomplete("#appl_dept","../queryDeptDictCode.do?isCheck=false","id","text",true,true,appl_param);
 		 autocomplete("#exec_dept","../queryDeptDictCode.do?isCheck=false","id","text",true,true,exec_param);  
		 
    	$("#b_year_month").ligerTextBox({ width:160 });
    	$("#e_year_month").ligerTextBox({ width:160});
 		 
		 autodate("#b_year_month","yyyyMM");
		 
		 autodate("#e_year_month","yyyyMM");
		 
         }  
    
  //打印数据
	 function printDate(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","科室收入数据明细采集开始打印","科室收入数据明细采集列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
	    	appl_dept_id:liger.get("appl_dept").getValue().split(".")[0],
	    	appl_dept_no:liger.get("appl_dept").getValue().split(".")[1],
	    	exec_dept_id:liger.get("exec_dept").getValue().split(".")[0], 
	    	exec_dept_no:liger.get("exec_dept").getValue().split(".")[1],
	    	charge_kind_id:liger.get("charge_kind_id").getValue(),
	    	charge_item_id:liger.get("charge_item_id").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostIncomeDetail.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.appl_dept_name+"</td>"; 
                     trHtml+="<td>"+item.exec_dept_name+"</td>"; 
                     trHtml+="<td>"+item.charge_kind_name+"</td>"; 
                     trHtml+="<td>"+item.charge_item_name+"</td>"; 
                     trHtml+="<td>"+item.price+"</td>"; 
                     trHtml+="<td>"+item.num+"</td>"; 
                     trHtml+="<td>"+item.money+"</td>"; 
                     //trHtml+="<td>"+item.create_user+"</td>"; 
                     trHtml+="<td>"+item.create_date+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","科室收入数据明细采集开始打印","科室收入数据明细采集列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","科室收入数据明细采集导出Excel","科室收入数据明细采集列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
	    	appl_dept_id:liger.get("appl_dept").getValue().split(".")[0],
	    	appl_dept_no:liger.get("appl_dept").getValue().split(".")[1],
	    	exec_dept_id:liger.get("exec_dept").getValue().split(".")[0], 
	    	exec_dept_no:liger.get("exec_dept").getValue().split(".")[1],
	    	charge_kind_id:liger.get("charge_kind_id").getValue(),
	    	charge_item_id:liger.get("charge_item_id").getValue()
         };
		ajaxJsonObjectByUrl("queryCostIncomeDetail.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                 trHtml+="<td>"+item.appl_dept_name+"</td>"; 
                 trHtml+="<td>"+item.exec_dept_name+"</td>"; 
                 trHtml+="<td>"+item.charge_kind_name+"</td>"; 
                 trHtml+="<td>"+item.charge_item_name+"</td>"; 
                 trHtml+="<td>"+item.price+"</td>"; 
                 trHtml+="<td>"+item.num+"</td>"; 
                 trHtml+="<td>"+item.money+"</td>"; 
                 //trHtml+="<td>"+item.create_user+"</td>"; 
                 trHtml+="<td>"+item.create_date+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","科室收入数据明细采集导出Excel","科室收入数据明细采集列表.xls",true);
	    },true,manager);
		return;
	 }	
	 function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'收入采集(项目)',
	       			head:[
	    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
	    				{"cell":0,"value":
	    					"期间: " + $("#b_year_month").val()+
	    					" 至  "+ $("#e_year_month").val(),
	    				"colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryCostIncomeDetail.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
            <td align="left" class="l-table-edit-td"><input name="appl_dept" type="text" id="appl_dept" /></td>
           
        </tr> 
        <tr>
         
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室：</td>
            <td align="left" class="l-table-edit-td"><input name="exec_dept" type="text" id="exec_dept" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_kind_id" type="text" id="charge_kind_id" /></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_id" type="text" id="charge_item_id" /></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">开单科室</th>
					<th width="200">执行科室</th>
					<th width="200">收费类别</th>
					<th width="200">收费项目</th>
					<th width="200">单价</th>
					<th width="200">数量</th>
					<th width="200">金额</th>
					<!-- <th width="200">操作员ID</th> -->
					<th width="200">操作时间</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
