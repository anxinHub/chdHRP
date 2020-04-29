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
    	$("#charge_item_code").ligerTextBox({ width:160 });
    	//$("#charge_item_name").ligerTextBox({ width:160 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'charge_kind_id',value:liger.get("charge_kind_id").getValue()}); 
    	grid.options.parms.push({name:'charge_item_code',value:$("#charge_item_code").val()}); 
    	//grid.options.parms.push({name:'charge_item_name',value:$("#charge_item_name").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '收费项目编码', name: 'charge_item_code', align: 'left',
						render : function(rowdata, rowindex,
								value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code   + "|" + rowdata.charge_kind_id   + "|" + rowdata.charge_item_id+"')>"+rowdata.charge_item_code+"</a>";
						}
					 },
                     { display: '收费项目名称', name: 'charge_item_name', align: 'left'
					 },
                     { display: '收费类别名称', name: 'charge_kind_name', align: 'left'
					 },
                     { display: '单价', name: 'price', align: 'right',
							render : function(rowdata,rowindex, value) {
								return formatNumber(rowdata.price,2, 1)
							},
					 },
                     { display: '停用标志', name: 'is_stop', align: 'left',reg:'0=否,1=是',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "否";
								}else{
									return "是"
								}
							}
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostChargeItemArrt.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                { text: '导入', id:'import', click: importData,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.charge_kind_id   + "|" + 
								rowdata.charge_item_id
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

	function importData() {
				var para = {
					"column": [{
						"name": "charge_item_code",
						"display": "收费项目编码",
						"width": "300",
						"require": true
					}, {
						"name": "charge_item_name",
						"display": "收费项目名称",
						"width": "300",
						"require": true
					}, {
						"name": "charge_kind_code",
						"display": "收费类别编码",
						"width": "300",
						"require": true
					}, {
						"name": "charge_kind_name",
						"display": "收费类别名称",
						"width": "300",
						"require": true
					}, {
						"name": "price",
						"display": "单价",
						"width": "300",
						"require": true
					}, {
						"name": "is_stop",
						"display": "是否停用",
						"width": "100",
						"require": true
					}]
				};
				importSpreadView(
					"../hrp/cost/costchargeitemarrt/costChargeItemArrtImportPage.do?isCheck=false",
					para);

			}


    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costChargeItemArrtAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostChargeItemArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.charge_kind_id   +"@"+ 
							this.charge_item_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	$.post("deleteCostChargeItemArrt.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}else{
                            			$.ligerDialog.warn(responseData.error);
                            		}
                            	},"json");
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costChargeItemArrtImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"charge_kind_id="+vo[3]   +"&"+ 
			"charge_item_id="+vo[4] 
    	$.ligerDialog.open({ url : 'costChargeItemArrtUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostChargeItemArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
			$("#charge_item_code").ligerTextBox({});
			$("#charge_item_name").ligerTextBox({});
			
			 autocomplete("#charge_kind_id","../queryChargeKindArrt.do?isCheck=false","id","text",true,true);
         }  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		//if($("#resultPrint > table > tbody").html()!=""){
		//	lodopPrinterTable("resultPrint","收费项目开始打印","收费项目列表",true);
		//	return;
		//}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
            
            
		charge_kind_id:("#charge_kind_id").val(), 
    	charge_item_code:$("#charge_item_code").val()
    	//charge_item_name:$("#charge_item_name").val() 
            
            
            
         };
		ajaxJsonObjectByUrl("queryCostChargeItemArrt.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 	 trHtml+="<td>"+item.charge_kind_name+"</td>";
                     trHtml+="<td>"+item.charge_item_code+"</td>"; 
                     trHtml+="<td>"+item.charge_item_name+"</td>"; 
                     trHtml+="<td>"+item.price+"</td>"; 
                     if(item.is_stop == 0){
    					 trHtml+="<td>否</td>";
    				 }else{
    					 trHtml+="<td>是</td>";
    				 }
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","收费项目开始打印","收费项目列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","收费项目导出Excel","收费项目列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
			charge_kind_id:liger.get("charge_kind_id").getValue(), 
	    	charge_item_code:$("#charge_item_code").val(),
	    	//charge_item_name:$("#charge_item_name").val()
         };
		ajaxJsonObjectByUrl("queryCostChargeItemArrt.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				 trHtml+="<td>"+item.charge_kind_name+"</td>"; 
				 trHtml+="<td>"+item.charge_item_code+"</td>"; 
                 trHtml+="<td>"+item.charge_item_name+"</td>"; 
                 trHtml+="<td>"+item.price+"</td>"; 
                 if(item.is_stop == 0){
					 trHtml+="<td>否</td>";
				 }else{
					 trHtml+="<td>是</td>";
				 }
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","收费项目导出Excel","收费项目列表.xls",true);
	    },true,manager);
		return;
	 }	
	//通过Spread组件进行打印
// 	 function print(){
// 		//设置表头
// 			var columnInfos = [ {
// 				name : "charge_item_code",
// 				displayName : "收费项目编码",
// 				size: 80
// 			}, {
// 				name : "charge_item_name",
// 				displayName : "收费项目名称",
// 				size: 100 
// 			}, {
// 				name : "charge_kind_name",
// 				displayName : "收费类别名称",
// 				formatter : "#,##0.00"
// 			},{
// 				name : "price",
// 				displayName : "单价",
// 				formatter : "#,##0.00"
// 			},{
// 				name : "is_stop",
// 				displayName : "停用标志",
// 				formatter : "#,##0.00"
// 			}];
		 
		 
		 
// 		 var exportPara = {
// 					usePager : false,  
// 					charge_kind_id:$("#charge_kind_id").val(), 
// 			    	charge_item_code:$("#charge_item_code").val()
// 				};
// 				//公用部分
// 				viewPrintOneHead("queryCostChargeItemArrt.do", exportPara, columnInfos,
// 						"收费项目", 500);
			
// 	 }
	    function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"单位："+$("sessionScope.hos_name").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "收费项目",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostChargeItemArrtService",
	 	   			method_name: "queryCostChargeItemArrtPrint",
	 	   			bean_name: "costChargeItemArrtService",
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_kind_id" type="text" id="charge_kind_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_code" type="text" id="charge_item_code" /></td>
       <!--      <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_name" type="text" id="charge_item_name" /></td> -->
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">收费类别名称</th>
					<th width="200">收费项目编码</th>
					<th width="200">收费项目名称</th>
					<th width="200">单价</th>
					<th width="200">停用标志</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
