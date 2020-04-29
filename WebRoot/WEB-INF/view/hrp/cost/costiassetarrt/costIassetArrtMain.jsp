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
    	grid.options.parms.push({name:'asset_type_id',value:liger.get("asset_type_id").getValue()}); 
    	grid.options.parms.push({name:'asset_code',value:$("#asset_code").val()}); 
    	grid.options.parms.push({name:'asset_name',value:$("#asset_name").val()}); 
    	grid.options.parms.push({name:'dep_year',value:$("#dep_year").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }


    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     
                     { display: '资产编码', name: 'asset_code', align: 'left'
					 },
                     { display: '资产名称', name: 'asset_name', align: 'left'
					 },
					 { display: '资产分类编码', name: 'asset_type_code', align: 'left'
					 },
					 { display: '资产分类名称', name: 'asset_type_name', align: 'left'
					 },
                     { display: '资产原值', name: 'prim_value', align: 'left'
					 },
                     { display: '启用年月', name: 'start_date', align: 'left'
					 },
                     { display: '结束年月', name: 'end_date', align: 'left'
					 },
                     { display: '折旧年限', name: 'dep_year', align: 'left'
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostIassetArrt.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
                        { line:true },
   	                    { text: '同步', id:'sync', click: itemclick, icon:'add' },
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
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" +  
								rowdata.asset_id 
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
              		$.ligerDialog.open({url: 'costIassetArrtAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIassetArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.asset_type_id   +"@"+ 
							this.asset_id
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostIassetArrt.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "sync":
                	$.ligerDialog.confirm('同步操作将会删除成本系统中所维护的无形资产信息,确定同步吗?', function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("syncCostIassetArrtNew.do?isCheck=false",{},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                    }); 
                	return;
				case "import":
                	$.ligerDialog.open({url: 'costIassetArrtImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"asset_id="+vo[3] 
    	$.ligerDialog.open({ url : 'costIassetArrtUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIassetArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
            autocomplete("#asset_type_id","../queryIassetTypeArrt.do?isCheck=false","id","text",true,true);
            
			$("#asset_code").ligerTextBox({width:160});
			
			$("#asset_name").ligerTextBox({width:160});
			
			$("#dep_year").ligerTextBox({width:160});
			
         }  
    
  /* //打印数据
	 function printDate(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","无形资产",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,

			asset_type_id:$("#asset_type_id").val(),
            
           	asset_code:$("#asset_code").val(),
            
           	asset_name:$("#asset_name").val(),
            
           	dep_year:$("#dep_year").val()

         };
		ajaxJsonObjectByUrl("queryCostIassetArrt.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.asset_type_code+"</td>"; 
                     trHtml+="<td>"+item.asset_code+"</td>"; 
                     trHtml+="<td>"+item.asset_name+"</td>"; 
                     trHtml+="<td>"+item.prim_value+"</td>"; 
                     trHtml+="<td>"+item.start_date+"</td>"; 
                     trHtml+="<td>"+item.end_date+"</td>"; 
                     trHtml+="<td>"+item.dep_year+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","无形资产",true);
	    },true,manager);
		return;
	 }
	 */
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","无形资产.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
			asset_type_id:$("#asset_type_id").val(),
            
           	asset_code:$("#asset_code").val(),
            
           	asset_name:$("#asset_name").val(),
            
           	dep_year:$("#dep_year").val()
         };
		ajaxJsonObjectByUrl("queryCostIassetArrt.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.asset_type_code+"</td>"; 
                     trHtml+="<td>"+item.asset_code+"</td>"; 
                     trHtml+="<td>"+item.asset_name+"</td>"; 
                     trHtml+="<td>"+item.prim_value+"</td>"; 
                     trHtml+="<td>"+item.start_date+"</td>"; 
                     trHtml+="<td>"+item.end_date+"</td>"; 
                     trHtml+="<td>"+item.dep_year+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","无形资产.xls",true);
	    },true,manager);
		return;
	 }	
// 	 function print(){
// 		 var exportPara = {
// 				 usePager:false,

// 					asset_type_id:$("#asset_type_id").val(),
		            
// 		           	asset_code:$("#asset_code").val(),
		            
// 		           	asset_name:$("#asset_name").val(),
		            
// 		           	dep_year:$("#dep_year").val()
// 						      };
					
// 					$.ajax({
// 					   url:"queryCostIassetArrt.do",
// 					   type:"post",
// 					   data:exportPara,
// 					   dataType:"JSON",
// 					   success:function(res){
						  
// 						   var data={
// 								   headers:[
// 											{ x: 0, y: 0, rowSpan: 1, colSpan: 1, displayName: "资产编码", name: "asset_code",size:80},
// 											{ x: 1, y: 0, rowSpan: 1, colSpan: 1, displayName: "资产名称", name: "asset_name",size:80},
// 											{ x: 2, y: 0, rowSpan: 1, colSpan: 1, displayName: "资产分类编码", name: "asset_type_code",size:80},
// 											{ x: 3, y: 0, rowSpan: 1, colSpan: 1, displayName: "资产分类名称", name: "asset_type_name" ,size:80},
// 											{ x: 4, y: 0, rowSpan: 1, colSpan: 1, displayName: "资产原值", name: "prim_value",size:80},
// 											{ x: 5, y: 0, rowSpan: 1, colSpan: 1, displayName: "启用年月", name: "start_date" ,size:80},
// 											{ x: 6, y: 0, rowSpan: 1, colSpan: 1, displayName: "结束年月", name: "end_date",size:80},
// 											{ x: 7, y: 0, rowSpan: 1, colSpan: 1, displayName: "折旧年限", name: "dep_year" ,size:80},
// 											],
// 								    rows: res.Rows
								   
// 						   }
// 						   viewPrint(data, "无形资产");
// 					   },
// 					   error: function (res) {
// 								console.error(res);
// 							}
// 				});	 	  		 		 
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
	 	      		title: "无形资产",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostIassetArrtService",
	 	   			method_name: "queryCostIassetArrtPrint",
	 	   			bean_name: "costIassetArrtService"
	 	   			
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="asset_type_id" type="text" id="asset_type_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产编码：</td>
            <td align="left" class="l-table-edit-td"><input name="asset_code" type="text" id="asset_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="asset_name" type="text" id="asset_name" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年限：</td>
            <td align="left" class="l-table-edit-td"><input name="dep_year" type="text" id="dep_year" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">资产分类编码</th>
					<th width="200">资产编码</th>
					<th width="200">资产名称</th>
					<th width="200">资产原值</th>
					<th width="200">启用年月</th>
					<th width="200">结束年月</th>
					<th width="200">折旧年限</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
