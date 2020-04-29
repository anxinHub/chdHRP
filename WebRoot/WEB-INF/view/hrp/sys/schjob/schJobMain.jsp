<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
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
    	grid.options.parms.push({name:'job_id',value:$("#job_id").val()}); 
    	grid.options.parms.push({name:'group_id',value:$("#group_id").val()}); 
    	grid.options.parms.push({name:'hos_id',value:$("#hos_id").val()}); 
    	grid.options.parms.push({name:'mod_code',value:$("#mod_code").val()}); 
    	grid.options.parms.push({name:'table_code',value:$("#table_code").val()}); 
    	grid.options.parms.push({name:'job_name',value:$("#job_name").val()}); 
    	grid.options.parms.push({name:'job_expression',value:$("#job_expression").val()}); 
    	grid.options.parms.push({name:'job_run_time',value:$("#job_run_time").val()}); 
    	grid.options.parms.push({name:'job_proc',value:$("#job_proc").val()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#job_id").val()!=""){
                		return rowdata.job_id.indexOf($("#job_id").val()) > -1;	
                	}
                	if($("#group_id").val()!=""){
                		return rowdata.group_id.indexOf($("#group_id").val()) > -1;	
                	}
                	if($("#hos_id").val()!=""){
                		return rowdata.hos_id.indexOf($("#hos_id").val()) > -1;	
                	}
                	if($("#mod_code").val()!=""){
                		return rowdata.mod_code.indexOf($("#mod_code").val()) > -1;	
                	}
                	if($("#table_code").val()!=""){
                		return rowdata.table_code.indexOf($("#table_code").val()) > -1;	
                	}
                	if($("#job_name").val()!=""){
                		return rowdata.job_name.indexOf($("#job_name").val()) > -1;	
                	}
                	if($("#job_expression").val()!=""){
                		return rowdata.job_expression.indexOf($("#job_expression").val()) > -1;	
                	}
                	if($("#job_run_time").val()!=""){
                		return rowdata.job_run_time.indexOf($("#job_run_time").val()) > -1;	
                	}
                	if($("#job_proc").val()!=""){
                		return rowdata.job_proc.indexOf($("#job_proc").val()) > -1;	
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
                     { display: 'job_id', name: 'job_id', align: 'left'
					 },
                     { display: 'group_id', name: 'group_id', align: 'left'
					 },
                     { display: 'hos_id', name: 'hos_id', align: 'left'
					 },
                     { display: 'mod_code', name: 'mod_code', align: 'left'
					 },
                     { display: 'table_code', name: 'table_code', align: 'left'
					 },
                     { display: 'job_name', name: 'job_name', align: 'left'
					 },
                     { display: 'job_expression', name: 'job_expression', align: 'left'
					 },
                     { display: 'job_run_time', name: 'job_run_time', align: 'left'
					 },
                     { display: 'job_proc', name: 'job_proc', align: 'left'
					 },
                     { display: 'is_stop', name: 'is_stop', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'querySchJob.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.job_id   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id 
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
              		$.ligerDialog.open({url: 'schJobAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSchJob(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.job_id   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteSchJob.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
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
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index] 
		
    	$.ligerDialog.open({ url : 'deptIncomeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSchJob(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">job_id：</td>
            <td align="left" class="l-table-edit-td"><input name="job_id" type="text" id="job_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">group_id：</td>
            <td align="left" class="l-table-edit-td"><input name="group_id" type="text" id="group_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_id：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">mod_code：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">table_code：</td>
            <td align="left" class="l-table-edit-td"><input name="table_code" type="text" id="table_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">job_name：</td>
            <td align="left" class="l-table-edit-td"><input name="job_name" type="text" id="job_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">job_expression：</td>
            <td align="left" class="l-table-edit-td"><input name="job_expression" type="text" id="job_expression" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">job_run_time：</td>
            <td align="left" class="l-table-edit-td"><input name="job_run_time" type="text" id="job_run_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">job_proc：</td>
            <td align="left" class="l-table-edit-td"><input name="job_proc" type="text" id="job_proc" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">is_stop：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
