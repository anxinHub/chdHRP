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
    	grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
    	grid.options.parms.push({name:'appl_dept_id',value:$("#appl_dept_id").val()}); 
    	grid.options.parms.push({name:'appl_dept_no',value:$("#appl_dept_no").val()}); 
    	grid.options.parms.push({name:'exec_dept_id',value:$("#exec_dept_id").val()}); 
    	grid.options.parms.push({name:'exec_dept_no',value:$("#exec_dept_no").val()}); 
    	grid.options.parms.push({name:'charge_kind_code',value:$("#charge_kind_code").val()}); 
    	grid.options.parms.push({name:'money',value:$("#money").val()}); 
    	grid.options.parms.push({name:'create_user',value:$("#create_user").val()}); 
    	grid.options.parms.push({name:'create_date',value:$("#create_date").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#year_month").val()!=""){
                		return rowdata.year_month.indexOf($("#year_month").val()) > -1;	
                	}
                	if($("#appl_dept_id").val()!=""){
                		return rowdata.appl_dept_id.indexOf($("#appl_dept_id").val()) > -1;	
                	}
                	if($("#appl_dept_no").val()!=""){
                		return rowdata.appl_dept_no.indexOf($("#appl_dept_no").val()) > -1;	
                	}
                	if($("#exec_dept_id").val()!=""){
                		return rowdata.exec_dept_id.indexOf($("#exec_dept_id").val()) > -1;	
                	}
                	if($("#exec_dept_no").val()!=""){
                		return rowdata.exec_dept_no.indexOf($("#exec_dept_no").val()) > -1;	
                	}
                	if($("#charge_kind_code").val()!=""){
                		return rowdata.charge_kind_code.indexOf($("#charge_kind_code").val()) > -1;	
                	}
                	if($("#money").val()!=""){
                		return rowdata.money.indexOf($("#money").val()) > -1;	
                	}
                	if($("#create_user").val()!=""){
                		return rowdata.create_user.indexOf($("#create_user").val()) > -1;	
                	}
                	if($("#create_date").val()!=""){
                		return rowdata.create_date.indexOf($("#create_date").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left'
					 },
                     { display: '开单科室', name: 'appl_dept_id', align: 'left'
					 },
                     { display: '开单科室变更ID', name: 'appl_dept_no', align: 'left'
					 },
                     { display: '执行科室', name: 'exec_dept_id', align: 'left'
					 },
                     { display: '执行科室变更ID', name: 'exec_dept_no', align: 'left'
					 },
                     { display: '收费类别', name: 'charge_kind_code', align: 'left'
					 },
                     { display: '金额', name: 'money', align: 'left'
					 },
                     { display: '操作员ID', name: 'create_user', align: 'left'
					 },
                     { display: '操作时间', name: 'create_date', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostIncome.do',
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
    					if(rowdata.year_month == null){
    						$.ligerDialog.warn('请选择行'); 
    						return false ;
    					}
    					
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.year_month   + "|" + 
								rowdata.appl_dept_no   + "|" + 
								rowdata.exec_dept_no 
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
              		$.ligerDialog.open({url: 'costIncomeAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncome(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.year_month   +"@"+ 
							this.appl_dept_no   +"@"+ 
							this.exec_dept_no 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostIncome.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
				case "import":
                	$.ligerDialog.open({url: 'costIncomeImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
			"year_month="+vo[3]   +"&"+ 
			"appl_dept_no="+vo[4]   +"&"+ 
			"exec_dept_no="+vo[5] 
    	$.ligerDialog.open({ url : 'costIncomeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostIncome(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
			$("#year_month").ligerTextBox({});
			$("#appl_dept_id").ligerTextBox({});
			$("#appl_dept_no").ligerTextBox({});
			$("#exec_dept_id").ligerTextBox({});
			$("#exec_dept_no").ligerTextBox({});
			$("#charge_kind_code").ligerTextBox({});
			$("#money").ligerTextBox({});
			$("#create_user").ligerTextBox({});
			$("#create_date").ligerTextBox({});
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
            
            
            
           year_month:$("#year_month").val(),
            
           appl_dept_id:$("#appl_dept_id").val(),
            
           appl_dept_no:$("#appl_dept_no").val(),
            
           exec_dept_id:$("#exec_dept_id").val(),
            
           exec_dept_no:$("#exec_dept_no").val(),
            
           charge_kind_code:$("#charge_kind_code").val(),
            
           money:$("#money").val(),
            
           create_user:$("#create_user").val(),
            
           create_date:$("#create_date").val()
            
         };
		ajaxJsonObjectByUrl("queryCostIncome.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.year_month+"</td>"; 
                     trHtml+="<td>"+item.appl_dept_id+"</td>"; 
                     trHtml+="<td>"+item.appl_dept_no+"</td>"; 
                     trHtml+="<td>"+item.exec_dept_id+"</td>"; 
                     trHtml+="<td>"+item.exec_dept_no+"</td>"; 
                     trHtml+="<td>"+item.charge_kind_code+"</td>"; 
                     trHtml+="<td>"+item.money+"</td>"; 
                     trHtml+="<td>"+item.create_user+"</td>"; 
                     trHtml+="<td>"+item.create_date+"</td>";
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
           year_month:$("#year_month").val(),
           appl_dept_id:$("#appl_dept_id").val(),
           appl_dept_no:$("#appl_dept_no").val(),
           exec_dept_id:$("#exec_dept_id").val(),
           exec_dept_no:$("#exec_dept_no").val(),
           charge_kind_code:$("#charge_kind_code").val(),
           money:$("#money").val(),
           create_user:$("#create_user").val(),
           create_date:$("#create_date").val()
         };
		ajaxJsonObjectByUrl("queryCostIncome.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
                     trHtml+="<td>"+item.year_month+"</td>"; 
                     trHtml+="<td>"+item.appl_dept_id+"</td>"; 
                     trHtml+="<td>"+item.appl_dept_no+"</td>"; 
                     trHtml+="<td>"+item.exec_dept_id+"</td>"; 
                     trHtml+="<td>"+item.exec_dept_no+"</td>"; 
                     trHtml+="<td>"+item.charge_kind_code+"</td>"; 
                     trHtml+="<td>"+item.money+"</td>"; 
                     trHtml+="<td>"+item.create_user+"</td>"; 
                     trHtml+="<td>"+item.create_date+"</td>";
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
            <td align="left" class="l-table-edit-td"><input name="appl_dept_id" type="text" id="appl_dept_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室变更ID：</td>
            <td align="left" class="l-table-edit-td"><input name="appl_dept_no" type="text" id="appl_dept_no" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室：</td>
            <td align="left" class="l-table-edit-td"><input name="exec_dept_id" type="text" id="exec_dept_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室变更ID：</td>
            <td align="left" class="l-table-edit-td"><input name="exec_dept_no" type="text" id="exec_dept_no" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_kind_code" type="text" id="charge_kind_code" /></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
            <td align="left" class="l-table-edit-td"><input name="money" type="text" id="money" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">操作员ID：</td>
            <td align="left" class="l-table-edit-td"><input name="create_user" type="text" id="create_user" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">操作时间：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">统计年月</th>
					<th width="200">开单科室</th>
					<th width="200">开单科室变更ID</th>
					<th width="200">执行科室</th>
					<th width="200">执行科室变更ID</th>
					<th width="200">收费类别</th>
					<th width="200">金额</th>
					<th width="200">操作员ID</th>
					<th width="200">操作时间</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
