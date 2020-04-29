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
    	$('#role_id').bind('change', function(){
    		query();
        })
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    
    	grid.options.parms.push({name:'role_id',value:liger.get("role_id").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '角色ID', name: 'role_id', align: 'left'
					 },
                     { display: '角色名称', name: 'role_name', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.role_id+"')>"+rowdata.role_name+"</a>";
							}
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../hosrole/queryHosRole.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                                    onSelectRow: function (data, rowindex, rowobj)
                                    {
                                        parent.role_id = data.role_id;
                                        parent.query();
                                    }
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(role_id){
    	
    	parent.alertRole(role_id);

    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#role_id","../queryRoleDict.do?isCheck=false","id","text",true,true);    
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td">
				<input class="l-button l-button-test"  type="button" value="按用户" onclick="javascript:parent.selectPage(1)"/>
			</td>
			<td align="right" class="l-table-edit-td">
				<input class="l-button l-button-test"  type="button" value="按角色" onclick="javascript:parent.selectPage(2)"/>
			</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">角色：</td>
            <td align="left" class="l-table-edit-td"><input name="role_id" type="text" id="role_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <!-- td align="left"><input class="l-button l-button-test"  type="button" value="查询" onclick="query()"/></td-->
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
