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
    	
    	$('#user_code').bind('change', function(){
    		query(2);
        })
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    
    	grid.options.parms.push({name:'user_code',value:liger.get("user_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '用户ID', name: 'user_id', align: 'left'
					 },
                     { display: '用户编码', name: 'user_code', align: 'left'
					 },
                     { display: '用户名称', name: 'user_name', align: 'left'
					 },
                     { display: '状态', name: 'is_stop', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "启用";
								}else{
									return "停用";
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../user/queryAllUser.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     //toolbar: { items: [
                                     	//{ text: '查询', id:'search', click: query,icon:'search' }
                    				//]},
                                    onSelectRow: function (data, rowindex, rowobj)
                                    {
                                        parent.user_id = data.user_id;
                                        parent.query();
                                    }

                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(user_code){
    	
    	$.ligerDialog.open({ url : 'userUpdatePage.do?isCheck=false&user_code='+user_code,data:{}, height: 350,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveUser(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            var param = {
            		'type_codes':'0,1'
            };
    	autocomplete("#user_code","../queryUserDict.do?isCheck=false","id","text",true,true,param);    
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户：</td>
            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" /></td>
            <!-- <td align="left"><input class="l-button l-button-test"  type="button" value="查询" onclick="query(2)"/></td> -->
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
