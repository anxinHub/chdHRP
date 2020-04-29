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
    	grid.options.parms.push({name:'bonus_item_code',value:liger.get("bonus_item_code").getValue()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
  
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '奖金项编码', name: 'bonus_item_code', align: 'left',render : function(rowdata, rowindex,value) {
							return "<a href=javascript:openUpdate('"+rowdata.bonus_item_code+"')>"+rowdata.bonus_item_code+"</a>";
  					}
					 },
                     { display: '奖金项名称', name: 'bonus_item_name', align: 'left'
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
                     { display: '备注', name: 'remark', align: 'left'
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostBonusItemArrt.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
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
								rowdata.bonus_item_code 
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
              		$.ligerDialog.open({url: 'costBonusItemArrtAddPage.do?isCheck=false', height: 240,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostBonusItemArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.bonus_item_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostBonusItemArrt.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costBonusItemArrtImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"bonus_item_code="+vo[0] ;
    	$.ligerDialog.open({ url : 'costBonusItemArrtUpdatePage.do?isCheck=false&' + parm,data:{}, height: 240,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostBonusItemArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
    	$("#is_stop").ligerComboBox({width:80 });
            //字典下拉框
    	autocomplete("#bonus_item_code","../queryBonusItemArrt.do?isCheck=false","id","text",true,true);
         }  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		 
		//有数据直接打印
	 	if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","奖金项维护列表",true);
			return;
		} 
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
            
           bonus_item_code:liger.get("bonus_item_code").getValue(),
            
           is_stop:$("#is_stop").val()
            
         };
		ajaxJsonObjectByUrl("queryCostBonusItemArrt.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.bonus_item_code+"</td>"; 
                     trHtml+="<td>"+item.bonus_item_name+"</td>"; 
                     trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>"; 
                     trHtml+="<td>"+(item.remark==null?'':item.remark)+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","奖金项维护",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		 
		 $("#resultPrint > table > tbody").empty();
		 
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","奖金项维护.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
		      
	           bonus_item_code:liger.get("bonus_item_code").getValue(),
	            
	           is_stop:$("#is_stop").val()
         };
		ajaxJsonObjectByUrl("queryCostBonusItemArrt.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.bonus_item_code+"</td>"; 
                     trHtml+="<td>"+item.bonus_item_name+"</td>"; 
                     trHtml+="<td>"+(item.is_stop==0?'否':'是')+"</td>"; 
                     trHtml+="<td>"+(item.remark==null?'':item.remark)+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","奖金项维护.xls",true);
	    },true,manager);
		return;
	 }	
// 	 function print(){
// 		//设置表头
// 	   		var columnInfos = [ {
	   		 
// 	   			name : "bonus_item_code",
// 	   			displayName : "奖金项编码",
// 	   			size: 100 
// 	   		}, {
// 	   			name : "bonus_item_name",
// 	   			displayName : "奖金项名称",
// 	   			formatter : "#,##0.00"
// 	   		}, {
// 	   			name : "is_stop",
// 	   			displayName : "是否停用",
// 	   			formatter : "#,##0.00"
// 	   		}, {
	   		 
// 	   			name : "remark",
// 	   			displayName : "备注",
// 	   			size: 100 
// 	   		}];
// 	   		var exportPara = {
// 	   				usePager:false,
// 	   				bonus_item_code:$("#bonus_item_code").val(),
// 	   				is_stop:$("#is_stop").val()
	   			    
	   			  
// 	   	   		};
	   		
// 	   	   		//公用部分
// 	   	   		viewPrintOneHead("queryCostBonusItemArrt.do", exportPara, columnInfos,
// 	   	   				"奖金项维护", 2000); 	 
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
	 	      		title: "奖金项维护",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostBonusItemArrtService",
	 	   			method_name: "queryCostBonusItemArrtPrint",
	 	   			bean_name: "costBonusItemArrtService"
	 	   			
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">奖金项编码：</td>
            <td align="left" class="l-table-edit-td"><input name="bonus_item_code" type="text" id="bonus_item_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            <select name="is_stop" id="is_stop"  style="width:140px;">
					<option value="">请选择</option>
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
            </td>
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">奖金项编码</th>
					<th width="200">奖金项名称</th>
					<th width="200">是否停用</th>
					<th width="200">备注</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
