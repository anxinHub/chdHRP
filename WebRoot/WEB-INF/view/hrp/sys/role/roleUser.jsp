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
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	
    var grid;
    var data;
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
    /* 	var param = {
 				'role_id':'${role_id}',
 				'group_id':'${group_id}',
 				'hos_id':'${hos_id}'
 			};
 		$.post("queryUserByRole.do",param,function (responseData){
 			data = responseData;
 			loadHead(null);	
 	    },"json"); */
 		loadDict();
 		loadHead();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'role_id',value: '${role_id}'}); 
    	grid.options.parms.push({name:'user_id_sel',value:liger.get("user_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
        	if($("#user_code").val()!=""){
        		return rowdata.user_code.indexOf($("#user_code").val()) > -1;	
        	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '用户编码', name: 'user_code', align: 'left'
					 },
                     { display: '用户名称', name: 'user_name', align: 'left'
					 },
					 { display: '职工', name: 'emp_name', align: 'left'
					 },
					 { display: '上级用户', name: 'sj_user', align: 'left'
					 },
					 { display: '状态', name: 'is_stop_name', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryRoleUserByAdd.do?isCheck=false&role_id=${role_id}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:false,isChecked: f_isChecked,
                     selectRowButtonOnly:true//heightDiff: -10,
                     
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function f_isChecked(rowdata){
 		var flag=false;
 		/* $.each(data.Rows,function(i,v){
 			if(rowdata.user_id == v.user_id){
 				flag = true;
 			}
 		}); */
 		return flag;
    }
    
    
    function loadDict(){
        //字典下拉框
	autocomplete("#user_code","../queryUserDictBySys.do?isCheck=false","id","text",true,true);    
     }   
    
    function saveRole(){
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.error('请选择用户');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.user_id+"@"+"${group_id}"+"@"+"${hos_id}"+"@"+"${role_id}" 
					)
             });
             ajaxJsonObjectByUrl("../roleuser/addRoleUser.do",{ParamVo : ParamVo},function (responseData){
                 if(responseData.state=="true"){
                 	parent.query();
                 	query();
                 }
             });
         }
    }
    </script>
   
	
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
           
          
	           <div title="" class="l-layout-content" style="height: 100%;" position="center">
	           		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				        <tr>
				            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户编码：</td>
				            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
				            <td align="left"><input class="l-button l-button-test"  type="button" value="查询" onclick="query()"/></td>
				        </tr> 
			    	</table>
	              	<div id="maingrid"></div>
	           </div>
</body>
</html>
