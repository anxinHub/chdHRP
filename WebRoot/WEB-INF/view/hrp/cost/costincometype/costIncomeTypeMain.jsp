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
    	$("#income_type_code").ligerTextBox({ width:160 });
    	$("#income_type_name").ligerTextBox({ width:160 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'income_type_code',value:$("#income_type_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '收入类型代码', name: 'income_type_code', align: 'left',
						render : function(rowdata, rowindex,
								value) {
								return "<a href=javascript:openUpdate('"+rowdata.income_type_id+"')>"+rowdata.income_type_code+"</a>";
						} 
					 },
                     { display: '收入类型名称', name: 'income_type_name', align: 'left'
					 },
                     { display: '停用标志', name: 'is_stop', align: 'left',
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostIncomeType.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					/* { text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true },  
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true } ,
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' } */
    				]} ,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.income_type_id 
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
              		$.ligerDialog.open({url: 'costIncomeTypeAddPage.do?isCheck=false', height: 240,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.income_type_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	$.post("deleteCostIncomeType.do",{ParamVo : ParamVo},function (responseData){
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
                	$.ligerDialog.open({url: 'costIncomeTypeImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"income_type_id="+vo[0] 
    	$.ligerDialog.open({ url : 'costIncomeTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 240,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncomeType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
			$("#income_type_code").ligerTextBox({});
			$("#income_type_name").ligerTextBox({});
			$("#is_stop").ligerTextBox({});
         }  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		//有数据直接打印
		//if($("#resultPrint > table > tbody").html()!=""){
			//lodopPrinterTable("resultPrint","收入类型开始打印","收入类型列表",true);
			//return;
		//}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
           income_type_code:$("#income_type_code").val(),
            
            
         };
		ajaxJsonObjectByUrl("queryCostIncomeType.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.income_type_code+"</td>"; 
                     trHtml+="<td>"+item.income_type_name+"</td>"; 
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
			lodopPrinterTable("resultPrint","收入类型开始打印","收入类型列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","收入类型导出Excel","收入类型列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
           income_type_code:$("#income_type_code").val(),
         };
		ajaxJsonObjectByUrl("queryCostIncomeType.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.income_type_code+"</td>"; 
                     trHtml+="<td>"+item.income_type_name+"</td>"; 
                     if(item.is_stop == 0){
    					 trHtml+="<td>否</td>";
    				 }else{
    					 trHtml+="<td>是</td>";
    				 }
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","收入类型导出Excel","收入类型列表.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入类型：</td>
            <td align="left" class="l-table-edit-td"><input name="income_type_code" type="text" id="income_type_code" /></td>
        </tr> 
	
        <tr>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">收入类型代码</th>
					<th width="200">收入类型名称</th>
					<th width="200">停用标志</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
