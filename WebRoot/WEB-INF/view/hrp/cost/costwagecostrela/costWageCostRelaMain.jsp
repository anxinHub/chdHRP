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
        
        var cost_item = liger.get("cost_item_id").getValue();
        
        grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()});   
    	grid.options.parms.push({name:'emp_kind_code',value:liger.get("emp_kind_code").getValue()}); 
    	grid.options.parms.push({name:'wage_item_code',value:liger.get("wage_item_code").getValue()}); 
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
                     { display: '统计年月', name: 'year_month', align: 'left',
                    	 render : function(rowdata, rowindex,
									value) {
				 				return rowdata.acc_year+rowdata.acc_month;
				 			}
					 },
                     { display: '职工分类编码', name: 'emp_kind_code', align: 'left'
					 },
					 { display: '职工分类名称', name: 'emp_kind_name', align: 'left'
					 },
                     { display: '工资项编码', name: 'wage_item_code', align: 'left'
					 },
					 { display: '工资项名称', name: 'wage_item_name', align: 'left'
					 },
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left'
					 },
					 { display: '成本项目名称', name: 'cost_item_name', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostWageCostRela.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                /*2016/10/25 lxj 
    	                	新增继承按钮
    	                */
    	                { text: '继承', id:'extend', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
//     	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]} ,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.id   
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
              		$.ligerDialog.open({url: 'costWageCostRelaAddPage.do?isCheck=false', height: 500,width: 800, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostWageCostRela(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.id
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostWageCostRela.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                /*
                2016/10/25 lxj
                	增加继承页面
                */
                case "extend":
                	$.ligerDialog.open({url: 'costWageCostRelaExtendPage.do?isCheck=false', height: 270,width: 500, title:'继承数据',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostWageCostRela(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		
                	return;
				case "import":
                	$.ligerDialog.open({url: 'costWageCostRelaImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
    	
		var parm = "id="+obj;
		
    	$.ligerDialog.open({ url : 'costWageCostRelaUpdatePage.do?isCheck=false&' + parm,data:{}, height: 270,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostWageCostRela(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	 $("#b_year_month").ligerTextBox({ width:160 });
	     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#b_year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");
            //字典下拉框
    	autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#wage_item_code","../queryWageItemArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
         }  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		 
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","工资项与成本项目的对应关系",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var cost_item = liger.get("cost_item_id").getValue();
		
		var printPara={
			usePager:false,

			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
            
           emp_kind_code:liger.get("emp_kind_code").getValue(),
            
           wage_item_code:liger.get("wage_item_code").getValue(),
            
         cost_item_id:(cost_item !=null && cost_item !='')?cost_item.split(".")[0]:'',
	            
	     cost_item_no:(cost_item !=null && cost_item !='')?cost_item.split(".")[1]:''
            
         };
		ajaxJsonObjectByUrl("queryCostWageCostRela.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                     trHtml+="<td>"+item.emp_kind_code+"</td>"; 
                     trHtml+="<td>"+item.emp_kind_name+"</td>"; 
                     trHtml+="<td>"+item.wage_item_code+"</td>"; 
                     trHtml+="<td>"+item.wage_item_name+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","工资项与成本项目的对应关系",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 
		 $("#resultPrint > table > tbody").empty();
		 
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","工资项与成本项目的对应关系.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		var cost_item = liger.get("cost_item_id").getValue();
		var exportPara={
			usePager:false,

			b_year_month:$("#b_year_month").val(),
			e_year_month:$("#e_year_month").val(),
	            
	           emp_kind_code:liger.get("emp_kind_code").getValue(),
	            
	           wage_item_code:liger.get("wage_item_code").getValue()
         };
		ajaxJsonObjectByUrl("queryCostWageCostRela.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.acc_year+item.acc_month+"</td>"; 
                  trHtml+="<td>"+item.emp_kind_code+"</td>"; 
                  trHtml+="<td>"+item.emp_kind_name+"</td>"; 
                  trHtml+="<td>"+item.wage_item_code+"</td>"; 
                  trHtml+="<td>"+item.wage_item_name+"</td>"; 
                  trHtml+="<td>"+item.cost_item_code+"</td>"; 
                  trHtml+="<td>"+item.cost_item_name+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","工资项与成本项目的对应关系.xls",true);
	    },true,manager);
		return;
	 }	
// 	 function print(){
// 		 var exportPara = {
// 					  b_year_month:$("#b_year_month").val(),
// 					  e_year_month:$("#e_year_month").val(),
// 			          emp_kind_code:liger.get("emp_kind_code").getValue(),			            
// 			          wage_item_code:liger.get("wage_item_code").getValue(),
// 			          cost_item_id:$("cost_item_id").val() 
			
	   			
// 	   	   		};
	   		
// 	   		$.ajax({
// 	 		   url:"queryCostWageCostRela.do",
// 	 		   type:"post",
// 	 		   data:exportPara,
// 	 		   dataType:"JSON",
// 	 		   success:function(res){
	 			  
// 	 			   var data={
// 	 					   headers:[
// 	 								{ x: 0, y: 0, rowSpan: 1, colSpan: 1, displayName: "统计年月", name: "year_month",size:130},
// 	 								{ x: 1, y: 0, rowSpan: 1, colSpan: 1, displayName: "职工分类编码", name: "emp_kind_code",size:130},
// 	 								{ x: 2, y: 0, rowSpan: 1, colSpan: 1, displayName: "职工分类名称", name: "wage_item_code" ,size:130 },
// 	 								{ x: 3, y: 0, rowSpan: 1, colSpan: 1, displayName: "工资项编码", name: "wage_item_name'" ,size:130 },
// 	 								{ x: 4, y: 0, rowSpan: 1, colSpan: 1, displayName: "工资项名称", name: "cost_item_code" ,size:130 },
// 	 								{ x: 5, y: 0, rowSpan: 1, colSpan: 1, displayName: "成本项目编码", name: "remark" ,size:130 },
// 	 								{ x: 6, y: 0, rowSpan: 1, colSpan: 1, displayName: "成本项目名称", name: "cost_item_name" ,size:130 },
// 	 								],
// 	 					    rows: res.Rows
// 	 			   }
// 	 			   viewPrint(data, "工资项与成本项目对应关系");
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
 	      		title: "工资项与成本项目的对应关系",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.CostWageCostRelaService",
 	   			method_name: "queryCostWageCostRelaPrint",
 	   			bean_name: "costWageCostRelaService"
 	   			
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_kind_code" type="text" id="emp_kind_code" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项编码：</td>
            <td align="left" class="l-table-edit-td"><input name="wage_item_code" type="text" id="wage_item_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" /></td>
            <td align="left"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">职工分类编码</th>
					<th width="200">职工分类名称</th>
					<th width="200">工资项编码</th>
					<th width="200">工资项名称</th>
					<th width="200">成本项目编码</th>
					<th width="200">成本项目名称</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
