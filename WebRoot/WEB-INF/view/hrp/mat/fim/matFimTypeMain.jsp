<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
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
    	grid.options.parms.push({name:'fim_type_code',value:$("#fim_type_code").val()}); 
    	grid.options.parms.push({name:'fim_type_name',value:$("#fim_type_name").val()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '物资财务编码', name: 'fim_type_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.fim_type_code   + "|" + 
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code   +"')>"+rowdata.fim_type_code+"</a>";
							}
					 },
                     { display: '名称', name: 'fim_type_name', align: 'left'
					 }, 
					 { display: '是否停用', name: 'is_stop', align: 'left',render : function(rowdata, rowindex, value) {
						       if(rowdata.is_stop == 0)
						    	   return "启用";
						       else 
						    	   return "停用";
						   }
                     }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatFimType.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: addPage, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: deletePage,icon:'delete' },
    	                { line:true }
    	               
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.fim_type_code   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    
    function loadDict(){
        //字典下拉框
    	$("#fim_type_code").ligerTextBox({width:160});
    	$("#fim_type_name").ligerTextBox({width:160}); 
     }   
    function addPage(){
    	$.ligerDialog.open({url: 'addMatFimTypePage.do?isCheck=false', height: 300,width: 500, 
				title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
				buttons: [ 
				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatLocationType(); },cls:'l-dialog-btn-highlight' }, 
				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				         ] 
		});
    	
    }
    function deletePage(){
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
				this.fim_type_code 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteMatFimType.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    function openUpdate(data){
    	var vo = data.split("|");
		var parm = "fim_type_code="+vo[0]; 
    	$.ligerDialog.open({url: 'updateMatFimTypePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, 
			title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
			buttons: [ 
			           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatFimType(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			         ] 
	});
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资财务分类编码：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="fim_type_code" type="text" id="fim_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" on>物资财务分类名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="fim_type_name" type="text" id="fim_type_name" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_stop" name="is_stop" >
            		<option></option>
	            	<option value="0">否</option>
	            	<option value="1">是</option>
	            </select>
            </td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
