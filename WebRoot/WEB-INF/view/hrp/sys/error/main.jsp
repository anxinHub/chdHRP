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
        grid.options.parms.push({name:'mtype',value:liger.get("mtype").getValue()}); 
    	grid.options.parms.push({name:'modCode',value:liger.get("mod_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#level_code").val()!=""){
                		return rowdata.level_code.indexOf($("#mode_code").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'SQL_ID', name: 'sqlId', align: 'left',width:200},
                     { display: '功能描述', name: 'note', align: 'left',width:200},
                     
                     { display: '类型', name: 'type', align: 'left',width:100},
                     { display: '文件分类', name: 'modCode', align: 'left',width:100},
                     
                     { display: '文件路径', name: 'filePath', align: 'left'},
                     { display: '异常信息', name: 'error', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryErrors.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '执行', id:'execute', click: itemclick,icon:'database' },
                     	{ line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					$.ligerDialog.error(rowdata.error);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

  
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "execute":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.sqlId 
							)
                        });
                        $.ligerDialog.confirm('确定执行?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("executeSql.do",{ParamVo : ParamVo},function (responseData){
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
    function loadDict(){
    	$("#mtype").ligerComboBox({
            width : 200,
            data: [
                { text: 'sql', id: 'sql' },
                { text: 'type', id: 'type' },
                { text: 'view', id: 'view' },
                { text: 'func', id: 'func' },
                { text: 'proc', id: 'proc' }
            ],  
            value: '',
            initIsTriggerEvent: false 
        });
    	autocomplete("#mod_code","queryMods.do?isCheck=false","level_code","level_name",true,true); 
	}   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">类型：</td>
            <td align="left" class="l-table-edit-td"><input name="mtype" type="text" id="mtype" type="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文件分类：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code" type="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
