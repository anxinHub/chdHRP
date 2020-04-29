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
    	grid.options.parms.push({name:'cost_item_id',value:$("#cost_item_id").val()}); 
    	grid.options.parms.push({name:'cost_type_id',value:$("#cost_type_id").val()}); 
    	grid.options.parms.push({name:'cost_type_no',value:$("#cost_type_no").val()}); 
    	grid.options.parms.push({name:'cost_item_no',value:$("#cost_item_no").val()}); 
    	grid.options.parms.push({name:'cost_item_code',value:$("#cost_item_code").val()}); 
    	grid.options.parms.push({name:'cost_item_name',value:$("#cost_item_name").val()}); 
    	grid.options.parms.push({name:'user_code',value:$("#user_code").val()}); 
    	grid.options.parms.push({name:'create_date',value:$("#create_date").val()}); 
    	grid.options.parms.push({name:'note',value:$("#note").val()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#cost_item_id").val()!=""){
                		return rowdata.cost_item_id.indexOf($("#cost_item_id").val()) > -1;	
                	}
                	if($("#cost_type_id").val()!=""){
                		return rowdata.cost_type_id.indexOf($("#cost_type_id").val()) > -1;	
                	}
                	if($("#cost_type_no").val()!=""){
                		return rowdata.cost_type_no.indexOf($("#cost_type_no").val()) > -1;	
                	}
                	if($("#cost_item_no").val()!=""){
                		return rowdata.cost_item_no.indexOf($("#cost_item_no").val()) > -1;	
                	}
                	if($("#cost_item_code").val()!=""){
                		return rowdata.cost_item_code.indexOf($("#cost_item_code").val()) > -1;	
                	}
                	if($("#cost_item_name").val()!=""){
                		return rowdata.cost_item_name.indexOf($("#cost_item_name").val()) > -1;	
                	}
                	if($("#user_code").val()!=""){
                		return rowdata.user_code.indexOf($("#user_code").val()) > -1;	
                	}
                	if($("#create_date").val()!=""){
                		return rowdata.create_date.indexOf($("#create_date").val()) > -1;	
                	}
                	if($("#note").val()!=""){
                		return rowdata.note.indexOf($("#note").val()) > -1;	
                	}
                	if($("#is_stop").val()!=""){
                		return rowdata.is_stop.indexOf($("#is_stop").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '成本项目ID', name: 'cost_item_id', align: 'left'
					 },
                     { display: '成本分类ID', name: 'cost_type_id', align: 'left'
					 },
                     { display: '成本分类变更ID', name: 'cost_type_no', align: 'left'
					 },
                     { display: '成本项目变更id', name: 'cost_item_no', align: 'left'
					 },
                     { display: '成本项目编码', name: 'cost_item_code', align: 'left'
					 },
                     { display: '成本项目名称', name: 'cost_item_name', align: 'left'
					 },
                     { display: '变更人', name: 'user_code', align: 'left'
					 },
                     { display: '变更时间', name: 'create_date', align: 'left'
					 },
                     { display: '变更原因', name: 'note', align: 'left'
					 },
                     { display: '0启用，1停用', name: 'is_stop', align: 'left'
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostItemDictNo.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
    	                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
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
								rowdata.cost_item_id   + "|" + 
								rowdata.cost_item_no 
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
              		$.ligerDialog.open({url: 'costItemDictNoAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostItemDictNo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.cost_item_id   +"@"+ 
							this.cost_item_no 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostItemDictNo.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costItemDictNoImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"cost_item_id="+vo[3]   +"&"+ 
			"cost_item_no="+vo[4] 
    	$.ligerDialog.open({ url : 'costItemDictNoUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostItemDictNo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
			$("#cost_item_id").ligerTextBox({});
			$("#cost_type_id").ligerTextBox({});
			$("#cost_type_no").ligerTextBox({});
			$("#cost_item_no").ligerTextBox({});
			$("#cost_item_code").ligerTextBox({});
			$("#cost_item_name").ligerTextBox({});
			$("#user_code").ligerTextBox({});
			$("#create_date").ligerTextBox({});
			$("#note").ligerTextBox({});
			$("#is_stop").ligerTextBox({});
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		
		var printPara={
			usePager:false,
            
            
            
           cost_item_id:$("#cost_item_id").val(),
            
           cost_type_id:$("#cost_type_id").val(),
            
           cost_type_no:$("#cost_type_no").val(),
            
           cost_item_no:$("#cost_item_no").val(),
            
           cost_item_code:$("#cost_item_code").val(),
            
           cost_item_name:$("#cost_item_name").val(),
            
           user_code:$("#user_code").val(),
            
           create_date:$("#create_date").val(),
            
           note:$("#note").val(),
            
           is_stop:$("#is_stop").val(),
            
            
            
         };
		ajaxJsonObjectByUrl("queryCostItemDictNo.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.cost_item_id+"</td>"; 
                     trHtml+="<td>"+item.cost_type_id+"</td>"; 
                     trHtml+="<td>"+item.cost_type_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
                     trHtml+="<td>"+item.user_code+"</td>"; 
                     trHtml+="<td>"+item.create_date+"</td>"; 
                     trHtml+="<td>"+item.note+"</td>"; 
                     trHtml+="<td>"+item.is_stop+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","列表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false,
           cost_item_id:$("#cost_item_id").val(),
           cost_type_id:$("#cost_type_id").val(),
           cost_type_no:$("#cost_type_no").val(),
           cost_item_no:$("#cost_item_no").val(),
           cost_item_code:$("#cost_item_code").val(),
           cost_item_name:$("#cost_item_name").val(),
           user_code:$("#user_code").val(),
           create_date:$("#create_date").val(),
           note:$("#note").val(),
           is_stop:$("#is_stop").val(),
         };
		ajaxJsonObjectByUrl("queryCostItemDictNo.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.cost_item_id+"</td>"; 
                     trHtml+="<td>"+item.cost_type_id+"</td>"; 
                     trHtml+="<td>"+item.cost_type_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_no+"</td>"; 
                     trHtml+="<td>"+item.cost_item_code+"</td>"; 
                     trHtml+="<td>"+item.cost_item_name+"</td>"; 
                     trHtml+="<td>"+item.user_code+"</td>"; 
                     trHtml+="<td>"+item.create_date+"</td>"; 
                     trHtml+="<td>"+item.note+"</td>"; 
                     trHtml+="<td>"+item.is_stop+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
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
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本分类ID：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_type_id" type="text" id="cost_type_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本分类变更ID：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_type_no" type="text" id="cost_type_no" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目变更id：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_no" type="text" id="cost_item_no" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_item_name" type="text" id="cost_item_name" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更人：</td>
            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更时间：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更原因：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">0启用，1停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">成本项目ID</th>
					<th width="200">成本分类ID</th>
					<th width="200">成本分类变更ID</th>
					<th width="200">成本项目变更id</th>
					<th width="200">成本项目编码</th>
					<th width="200">成本项目名称</th>
					<th width="200">变更人</th>
					<th width="200">变更时间</th>
					<th width="200">变更原因</th>
					<th width="200">0启用，1停用</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
