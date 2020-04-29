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
    	
    	$("#location_type_code").ligerTextBox({width:160});
    	$("#location_type_name").ligerTextBox({width:160});
    	loadHotkeys();
    });
  	//键盘事件
	  function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('E', exportExcel);
		hotkeys('P', printDate);
		hotkeys('I', imp);	
	 }
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'location_type_code',value:$("#location_type_code").val()}); 
    	grid.options.parms.push({name:'location_type_name',value:$("#location_type_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '货位分类编码ID', name: 'location_type_id', hide:true },
                     { display: '货位分类编码', name: 'location_type_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.location_type_code   + "|" + 
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code   + "|" + 
									rowdata.acc_year  +"')>"+rowdata.location_type_code+"</a>";
							}
					 },
                     { display: '货位分类名称', name: 'location_type_name', align: 'left'
					 },
                     { display: '是否停用', name: 'is_stop', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(value == 0){
									return "否";
								}else{
									return "是";
								}
							}
					 },
                     { display: '备注', name: 'note', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatLocationType.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad: true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                /* { text: '导出', id:'export', click: itemclick, icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: itemclick, icon:'print' },
		                { line:true }, */
		                {text : '下载导入模板',id : 'downTemplate',click : itemclick,icon : 'down'}, 
						{line : true}, 
		                { text: '导入', id:'import', click: itemclick, icon:'up' },
		                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.location_type_code   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year 
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
              		$.ligerDialog.open({url: 'matLocationTypeAddPage.do?isCheck=false', height: 300,width: 500, 
              				title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
              				buttons: [ 
              				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatLocationType(); },cls:'l-dialog-btn-highlight' }, 
              				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
              				         ] 
              		});
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
							this.location_type_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMatLocationType.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "downTemplate":
                    location.href = "downTemplate.do?isCheck=false";
                    return;
                case "import":
                   	$.ligerDialog.open({url: 'matLocationTypeImportPage.do?isCheck=false', height: 430,width: 760, isResize:true, title:'导入'});
                   	return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "print":
                	//打印
                case "export":
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
		var parm = "location_type_code="+vo[0]; 
		
    	$.ligerDialog.open({ url : 'matLocationTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, 
    			title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [ 
    			           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatLocationType(); },cls:'l-dialog-btn-highlight' }, 
    			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    			         ] });

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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="location_type_code" type="text" id="location_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">货位分类名称：</td>
            <td align="left" class="l-table-edit-td"><input name="location_type_name" type="text" id="location_type_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
