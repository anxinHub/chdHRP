<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

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
    	grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资金来源编码', name: 'source_code', align: 'left',
                    	 render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.source_id  + "|" + 
									rowdata.source_code +"')>"+rowdata.source_code+"</a>";
							   }
					 },
                     { display: '资金来源名称', name: 'source_name', align: 'left'
					 },
					 { display: '资金性质', name: 'nature_name', align: 'left'
					 },
					 { display: '是否停用', name: 'is_stop', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "否";
								}else{
									return "是"
								}
							}
					 },
                 	 { display: '备注', name: 'note', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssSource.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                    	{ text: '查询', id:'search', click: query,icon:'search' },
                      	{ line:true },
 				    	{ text: '添加', id:'add', click: itemclick, icon:'add' },
 				    	{ line:true },
 				    	{ text: '删除', id:'delete', click: itemclick,icon:'delete' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.source_id  + "|" + 
								rowdata.source_code
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
              		$.ligerDialog.open({url: 'sourceAddPage.do?isCheck=false', height: 350,width: 450, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSource(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
																ParamVo.push(
																this.group_id   +"@"+ 
																this.hos_id   +"@"+ 
																this.source_id +"@"+ 
																this.source_code
																); });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssSource.do",{ParamVo : ParamVo.toString()},function (responseData){
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
    
 function openUpdate(obj){
    	
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm ="&group_id="+ 
		   vo[0]   +"&hos_id="+ 
		   vo[1]   +"&source_id="+ 
		   vo[2]   +"&source_code="+
		   vo[3]; 
	 
		
    	$.ligerDialog.open({ url : 'sourceUpdatePage.do?isCheck=false&' + parm,data:{}, height: 350,width: 450, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSource(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    function loadDict(){
    	
		var param = {
            	query_key:''
        };
		
            //字典下拉框
    	autocomplete("#source_id","../querySourceDict.do?isCheck=false","id","text",true,true,param,true);
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源编码：</td>
            <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
