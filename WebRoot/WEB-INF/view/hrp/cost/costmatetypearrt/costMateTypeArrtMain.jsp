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
    	grid.options.parms.push({name:'mate_type_code',value:$("#mate_type_code").val()}); 
    	grid.options.parms.push({name:'mate_type_name',value:$("#mate_type_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '材料分类编码', name: 'mate_type_code', align: 'left'
					 },
                     { display: '材料分类名称', name: 'mate_type_name', align: 'left'
					 },
                     { display: '上级编码', name: 'supper_code', align: 'left',
						 render:function(rowdata){//2016/10/25 lxj 增加render
                    	 	if(rowdata.supper_code == '0'){
                    	 		return "TOP";
                    	 	}
                    	 	return rowdata.supper_code;
                     	}
					 },
                     { display: '末级标志', name: 'is_last', align: 'left',reg:'0=否,1=是',type:'formatYesNo'
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostMateTypeArrt.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                /*
    	                	2016/10/26 lxj
    	                	增加同步按钮
    	                */
    	                { text: '同步', id:'sync', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
//     	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
// 		                { line:true },
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.mate_type_code
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        formatYesNo();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costMateTypeArrtAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostMateTypeArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.mate_type_code
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostMateTypeArrt.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                    /*
                	2016/10/31 lxj
                	同步数据：发送请求
                */
                case "sync":
                	$.ligerDialog.confirm('同步操作将会删除成本系统中所维护的材料和材料分类信息,确定同步吗?', function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("syncCostMateTypeArrtNew.do?isCheck=false",{},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                    }); 
                	return;
				case "import":
                	$.ligerDialog.open({url: 'costMateTypeArrtImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"mate_type_code="+vo[3]
    	$.ligerDialog.open({ url : 'costMateTypeArrtUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostMateTypeArrt(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
			$("#mate_type_code").ligerTextBox({width:160});
			$("#mate_type_name").ligerTextBox({width:160});
         }  
    
	 
	 //导出数据
	 function exportExcel(){
		 
		 $("#resultPrint > table > tbody").empty();
		 
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","材料分类.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
				
			usePager:false,
			
           	mate_type_code:$("#mate_type_code").val(),
           	
           	mate_type_name:$("#mate_type_name").val()
           	
         };
		ajaxJsonObjectByUrl("queryCostMateTypeArrt.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.mate_type_code+"</td>"; 
                     trHtml+="<td>"+item.mate_type_name+"</td>"; 
                     trHtml+="<td>"+item.supper_code+"</td>"; 
                     trHtml+="<td>"+(item.is_last==0?'否':'是')+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","材料分类.xls",true);
	    },true,manager);
		return;
	 }		 
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
 	      		title: "材料分类",//标题
 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
 	      		class_name: "com.chd.hrp.cost.service.CostMateTypeArrtService",
 	   			method_name: "queryCostMateTypeArrtPrint",
 	   			bean_name: "costMateTypeArrtService"
 	   			
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="mate_type_code" type="text" id="mate_type_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料分类名称：</td>
            <td align="left" class="l-table-edit-td"><input name="mate_type_name" type="text" id="mate_type_name" /></td>
        </tr> 
 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">材料分类编码</th>
					<th width="200">材料分类名称</th>
					<th width="200">上级编码</th>
					<th width="200">末级标志</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
