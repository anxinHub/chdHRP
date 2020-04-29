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
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var para;
    
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

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
  	//查询
    function  rightQuery(){
    	rightgrid.options.parms=[];
    	rightgrid.options.newPage=1;
        //根据表字段进行添加查询条件
        rightgrid.options.parms.push({name:'wage_code',value:para}); 
        rightgrid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
        rightgrid.options.parms.push({name:'para_name',value:$("#para_name").val()}); 

    	//加载查询条件
    	rightgrid.loadData(rightgrid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '工资套编码', name: 'wage_code', align: 'left',
                    	 render:function(rowdata){
                    		 return "<a href=javascript:openUpdate('"+rowdata.wage_code+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"')>"+rowdata.wage_code+"</a>";
                    	 }
					 },
                     { display: '工资套名称', name: 'wage_name', align: 'left'
					 },
                     { display: '操作', name: 'edit', align: 'center',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:showData('"+rowdata.wage_code+"')>职工<a/>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../accwage/queryAccWage.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
                     	{ line:true },
                     	{ text: '删除', id:'delete', click: itemclick,icon:'delete' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        rightgrid = $("#rightgrid").ligerGrid({
            columns: [ 
                      { display: '职工编码', name: 'emp_code', align: 'left'
 					 },
                      { display: '职工名称', name: 'emp_name', align: 'left'
 					 },
 					 { display: '职工分类', name: 'kind_name', align: 'left'
 					 },
                     { display: '部门名称', name: 'dept_name', align: 'left'
					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,
                      width: '100%', height: '96%', checkbox: false,rownumbers:true,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
                      	{ text: '查询', id:'search', click: rightQuery,icon:'search' },
                      	{ line:true },
                      	{ text: '删除', id:'del', click: toobarclick,icon:'del' },
                      	{ line:true },
                      	{ text: '全部删除', id:'delete', click: toobarclick,icon:'delete' },
                      	{ line:true },
                      	{ text: '选择职工', id:'collect', click: toobarclick,icon:'search' },
                      	{ line:true }
                      	
     				]}
                    });

        rightgridManager = $("#rightgrid").ligerGetGridManager();
    }
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accWageEmpAddPage.do?isCheck=false', height: 275,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.leger_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccWageEmp.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
            }   
        }
        
    }
    
    function toobarclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "delete":
                	var ParamVo =[];
                    $(data).each(function (){					
						ParamVo.push(
						//表的主键
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code 
						)
                    });
                	ajaxJsonObjectByUrl("deleteAccWageEmp.do",{ParamVo : ParamVo},function (responseData){
                		if(responseData.state=="true"){
                			rightQuery();
                		}
                	});
                	return;
                case "del":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.wage_code   +"@"+ 
							this.emp_id   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBatchAccWageEmp.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			rightQuery();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "collect":
              		$.ligerDialog.open({url: 'accWageEmpCollectPage.do?isCheck=false', height: 500,width: 600, title:'选择职工',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '勾选导入', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' },{ text: '部分导入', onclick: function (item, dialog) { dialog.frame.saveAll(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
            }   
        }
        
    }
    
    function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "wage_code="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3];

    	$.ligerDialog.open({ url : '../accwage/updateAccWage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccTell(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function showData(obj){
    	
    	para=obj;
    	
    	var formPara={
        		
    	           wage_code:obj
    	            
    	         };
    	        
    	        ajaxJsonObjectByUrl("queryAccWageEmp.do",formPara,function(responseData){
    	            
    	        rightgridManager.set({ data: responseData });
    	        
    	        });
    	
    }
    
    function loadDict(){
        //字典下拉框
		$("#para_code").ligerTextBox({width:160});
    	
    	$("#para_name").ligerTextBox({width:160});
    	
    	$("#para_name").ligerTextBox({width:160});
     
	} 
     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div style="width: 1140px;">
	<div  style="float: left; width: 570px;">
	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>
	</div>
	<div  style="float: left; width: 570px;">
	<div id="righttoolbar" ></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">条件：</td>
            <td align="left" class="l-table-edit-td">
            <select name="select_where" id="select_where">
            <option value="0">职工</option>
            <option value="1">部门</option>
            <option value="2">职工分类</option>
            </select>
            </td>
            <td align="left"></td>
            <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	 <div id="rightgrid" style="margin-left: 3px"></div>
	</div>
	</div>

</body>
</html>
