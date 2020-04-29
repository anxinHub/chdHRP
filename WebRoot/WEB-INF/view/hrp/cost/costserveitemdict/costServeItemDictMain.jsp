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

    	grid.options.parms.push({name:'server_item_code',value:liger.get("server_item_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({ 
           columns: [ 
					 { display: '内部服务项目编码', name: 'server_item_code', align: 'left',render : function(rowdata, rowindex,value) {
							return "<a href=javascript:openUpdate('"+rowdata.server_item_code+ "')>"+rowdata.server_item_code+"</a>";
					}
					 },
                     { display: '内部服务项目名称', name: 'server_item_name', align: 'left'
					 },
                     { display: '是否停用', name: 'is_stop', align: 'left',reg:'0=否,1=是',
							render : function(rowdata, rowindex, value) {
								if (rowdata.is_stop == 0) {
									return "否";
								} else {
									return "是"
								}
							}

					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostServeItemDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
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
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.server_item_code
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
              		$.ligerDialog.open({url: 'costServeItemDictAddPage.do?isCheck=false',height: 324,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostEmpAttr(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.server_item_code  
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	$.post("deleteCostServeItemDict.do",{ParamVo : ParamVo.toString()},function (responseData){
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
                	$.ligerDialog.open({url: 'costServeItemDictImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"server_item_code="+vo
    	$.ligerDialog.open({ url : 'costServeItemDictUpdatePage.do?isCheck=false&' + parm,data:{}, height: 324,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostEmpAttr(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            
    	autocomplete("#server_item_code","../queryServerItemDict.do?isCheck=false","id","text",true,true); 
         }  
    
  //打印数据
	 function printDate(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","服务项目",true);
			return;
		}
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
				
			usePager:false,
    		
			server_item_code :liger.get("server_item_code").getValue()
            
         };
		ajaxJsonObjectByUrl("queryCostServeItemDict.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                 trHtml+="<td>"+item.server_item_code+"</td>"; 
                 trHtml+="<td>"+item.server_item_name+"</td>"; 
                 trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopPrinterTable("resultPrint","开始打印","服务项目",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 $("#resultPrint > table > tbody").empty();
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","服务项目.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
	
		var exportPara={
			usePager:false,
			server_item_code :liger.get("server_item_code").getValue()
         };
		ajaxJsonObjectByUrl("queryCostServeItemDict.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				     trHtml+="<td>"+item.server_item_code+"</td>"; 
	                 trHtml+="<td>"+item.server_item_name+"</td>"; 
	                 trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","服务项目.xls",true);
	    },true,manager);
		return;
	 }		
// 	 function print(){
// 		//设置表头
// 			var columnInfos = [ {
// 				name : "server_item_code",
// 				displayName : "内部服务项目编码",
// 				size: 80
// 			}, {
// 				name : "server_item_name",
// 				displayName : "内部服务项目名称",
// 				size: 100 
// 			}, {
// 				name : "is_stop",
// 				displayName : "是否停用",
// 				size: 100 
// 			}];
// 			var exportPara = {
// 					usePager : false,
// 					server_item_code : $("#server_item_code").val()
// 				};
// 				//公用部分
// 				viewPrintOneHead("queryCostServeItemDict.do", exportPara, columnInfos,
// 						"服务项目", 500);
		 
		 
		 
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
	 	      		title: "服务项目",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostServeItemDictService",
	 	   			method_name: "queryCostServeItemDictPrint",
	 	   			bean_name: "costServeItemDictService",
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">服务项目：</td>
            <td align="left" class="l-table-edit-td"><input name="server_item_code" type="text" id="server_item_code" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">内部服务项目编码</th>
					<th width="200">内部服务项目名称</th>
					<th width="200">是否停用</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
