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
    	$("#year_month").ligerTextBox({ width:160 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    	grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 
        
		var dept_dict = liger.get("dept_id").getValue();
        
        var cost_item = liger.get("cost_item_id").getValue();
        
		if(dept_dict !=null && dept_dict !=''){
    		
    		/* grid.options.parms.push({name:'dept_id',value:dept_dict.split(".")[0]}); 
    		
        	grid.options.parms.push({name:'dept_no',value:dept_dict.split(".")[1]});  */
        	
			grid.options.parms.push({name:'dept_code',value:dept_dict.split(".")[2]});
        	
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
                    	 if(rowdata.acc_year!="总"){
                    		return "<a href=javascript:openUpdate('"+rowdata.acc_year +"|"+rowdata.acc_month+"|"+ rowdata.dept_id+"|"+ rowdata.dept_no+"|"+ rowdata.cost_item_id  +"|" + rowdata.cost_item_no  +"|" + rowdata.source_id  +"')>"+rowdata.acc_year+rowdata.acc_month+"</a>";
                    	 }else{
                    		 return rowdata.acc_year+rowdata.acc_month;
                    		 
                    		 
                    	 }
            			}
                    	
// 						,totalSummary:{
// 							type:'sum',
// 							render:function(suminf,column,cell){
// 								return '<div>总计</div>';
// 							}
// 						}
					 },
                     { display: '科室编码', name: 'dept_code', align: 'left',render : function(rowdata,rowindex,value) {
						 if(rowdata.is_last=1){
							 return rowdata.dept_code;
						 }
                     }
					 },
                     { display: '科室名称', name: 'dept_name', align: 'left',
						 render : function(rowdata, rowindex, value) {
								return formatSpace(rowdata.dept_name,
										rowdata.dept_level - 1);
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostOutAcctVouchPrm.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:false,
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
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                /* { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true }, */
		                { text: '导入', id:'import', click: itemclick,icon:'up' }/* ,
		                { line:true },
		                { text: '采集会计数据', id:'impAcc', click: itemclick,icon:'up' } */
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
  //动态设置收入数据表头
	function f_setColumns(){ 
	  
		var columns=[
						{display: '统计年度',name : 'ACC_YEAR',width: 100,align : 'left', frozen: true},
						{display: '统计月份',name : 'ACC_MONTH',width: 100,align : 'left', frozen: true},
						{display: '科室编码',name : 'DEPT_ID',width: 100,align : 'left', frozen: true},
						{display: '科室名称',name : 'DEPT_NAME',width: 100,align : 'left', frozen: true}
				             ];
		
				//公用import.jsp页面使用
					 ajaxJsonObjectByUrl("../costitemdictno/queryCostItemDictNo.do?isCheck=false",{},function(responseData){
							//console.log(JSON.stringify(responseData))
							$.each(responseData.Rows,function(i,obj){
								//,render : function(rowdata, rowindex, value) { return formatNumber(value, 2, 1); }
								columns.push({display:obj.cost_item_name,name:'C_'+obj.cost_item_code.toUpperCase(),width: 100,align : 'right'});
								
							},false);
							 grid.set('columns', columns); 
					 });
					// console.log(JSON.stringify(columns));
			   
			    
			   
     } 
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costOutAcctVouchAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostOutAcctVouch(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.DEPT_NO   +"@"+ 
							this.DEPT_NO  +"@"+ 
							this.COST_ITEM_ID +"@"+ 
							this.COST_ITEM_NO +"@"+ 
							this.SOURCE_ID
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostOutAcctVouch.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "impAcc":
                	$.ligerDialog.open({url: 'costOutAcctVouchCollectPage.do?isCheck=false', height: 300,width: 300, title:'采集',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                	return;
				case "import":
					var columns=[
									{display: '统计年度',name : 'acc_year',width: 100,align : 'left'},
									{display: '统计月份',name : 'acc_month',width: 100,align : 'left'},
									{display: '科室编码',name : 'dept_id',width: 100,align : 'left'},
									{display: '科室名称',name : 'dept_name',width: 100,align : 'left'}
							             ];
							//公用import.jsp页面使用
								 ajaxJsonObjectByUrl("../costitemdictno/queryCostItemDictNo.do?isCheck=false",{},function(responseData){
										//console.log(JSON.stringify(responseData))
										$.each(responseData.Rows,function(i,obj){
											//,render : function(rowdata, rowindex, value) { return formatNumber(value, 2, 1); }
											columns.push({display:obj.cost_item_name,name:obj.cost_item_name,width: 100,align : 'right'});
										});	
								 });
								// console.log(JSON.stringify(columns));
										var para={
												 "column":	columns  
													};
										
					importSpreadView("hrp/cost/costoutacctvouch/readCostItemDictFilesX.do?isCheck=false",para); 

                	//$.ligerDialog.open({url: 'costOutAcctVouchImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"acc_year="+vo[0]   +"&"+ 
			"acc_month="+vo[1]   +"&"+ 
			"dept_id="+vo[2]   +"&"+ 
			"dept_no="+vo[3]   +"&"+ 
			"cost_item_id="+vo[4]   +"&"+ 
			"cost_item_no="+vo[5] +"&"+
			"source_id="+vo[6];
    	$.ligerDialog.open({ url : 'costOutAcctVouchUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostOutAcctVouch(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	 $("#b_year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	   //字典下拉框
			
			 autodate("#b_year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
            //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictCode.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
    	autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true); 
         }  
    
  //打印数据
	 function printDate(){
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

			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
                   
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
                   
           cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:''
            
         };
		ajaxJsonObjectByUrl("queryCostOutAcctVouch.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.dept_code+"</td>"; 
                     trHtml+="<td>"+item.dept_name+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
                     trHtml+="<td>"+item.amount+"</td>";
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

			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           dept_id:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[0]:'',
                   
           dept_no:(dept_dict !=null && dept_dict !='')?dept_dict.split(".")[1]:'',
            
           cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
                   
           cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:''
            
         };
		ajaxJsonObjectByUrl("queryCostOutAcctVouch.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                  trHtml+="<td>"+item.dept_code+"</td>"; 
                  trHtml+="<td>"+item.dept_name+"</td>"; 
                  trHtml+="<td>"+item.cost_item_code+"</td>"; 
                  trHtml+="<td>"+item.cost_item_name+"</td>"; 
                  trHtml+="<td>"+item.amount+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","科室支出总账采集.xls",true);
	    },true,manager);
		return;
	 }	
// 	 function print(){
		
// 	   		var exportPara = {
// 	   				usePager:false,
// 	   				b_year_month:$("#b_year_month").val(),
// 	   				e_year_month:$("#e_year_month").val(),
// 	   				dept_id:$("#dept_id").val(),
// 	   				cost_item_id:$("#cost_item_id").val(),
// 	   				source_id:$("#source_id").val()
	   			
// 	   	   		};
	   		
// 	   		$.ajax({
// 	 		   url:"queryCostOutAcctVouch.do",
// 	 		   type:"post",
// 	 		   data:exportPara,
// 	 		   dataType:"JSON",
// 	 		   success:function(res){
	 			  
// 	 			   var data={
// 	 					   headers:[
// 	 								{ x: 0, y: 0, rowSpan: 1, colSpan: 1, displayName: "统计年月", name: "year_month",size:100},
// 	 								{ x: 1, y: 0, rowSpan: 1, colSpan: 1, displayName: "科室编码", name: "dept_code",size:100},
// 	 								{ x: 2, y: 0, rowSpan: 1, colSpan: 1, displayName: "科室名称", name: "dept_name" ,size:100 },
// 	 								{ x: 3, y: 0, rowSpan: 1, colSpan: 1, displayName: "成本项目编码", name: "cost_item_code" ,size:100},
// 	 								{ x: 4, y: 0, rowSpan: 1, colSpan: 1, displayName: "成本项目名称", name: "cost_item_name",size:100},
// 	 								{ x: 5, y: 0, rowSpan: 1, colSpan: 1, displayName: "资金来源编码", name: "source_code",size:100},
// 	 								{ x: 6, y: 0, rowSpan: 1, colSpan: 1, displayName: "资金来源名称", name: "source_name" ,size:100 },
// 	 								{ x: 7, y: 0, rowSpan: 1, colSpan: 1, displayName: "金额", name: "amount" ,size:100 },
	 								 
// 	 									],
// 	 					    rows: res.Rows
// 	 			   }
// 	 			   viewPrint(data, "科室支出总账采集");
// 	 		   },
// 	 		   error: function (res) {
// 	 					console.error(res);
// 	 				}
// 	 	   });
	 	
// 	 }
          function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#b_year_month").val()+"至"+$("#e_year_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "绩效成本支出采集",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostOutAcctVouchService",
	 	   			method_name: "queryCostOutAcctVouchPrmPrint",
	 	   			bean_name: "costOutAcctVouchService",
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

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
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
					<th width="200">金额</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
