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
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
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
    
    	grid.options.parms.push({name:'user_code',value:$("#user_code").val()}); 
    	grid.options.parms.push({name:'sj_user',value:liger.get("sj_user").getValue()}); 

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
                     { display: '用户编码', name: 'user_code', align: 'left',width:100,
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate("+rowdata.user_id+")>"+rowdata.user_code+"</a>";
							}
					 },
                     { display: '用户名称', name: 'user_name', align: 'left'
					 },
					 { display: '职工', name: 'emp_name', align: 'left'
					 },
                     { display: '类型', name: 'type_name', align: 'left',width:80
					 },
					 { display: '联系电话', name: 'phone', align: 'left'
					 },
					 { display: '上级用户', name: 'sj_user', align: 'left'
					 },
					 { display: '最后登录模块', name: 'mod_code', align: 'left'
					 },
					 { display: '最后登录账套', name: 'copy_code', align: 'left'
					 },
                     { display: '状态', name: 'is_stop_name', align: 'left',width:80
					 },
                     { display: '备注', name: 'note', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryUser.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: -20,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                { text: '重置密码', id:'back', click: reset,icon:'back' },
    	                { line:true },
    	                { text: '打印', id:'print', click: print,icon:'print' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'userAddPage.do?isCheck=false', height: 350,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveUser(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.user_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteUser.do",{ParamVo : ParamVo.toString()},function (responseData){
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
    function openUpdate(user_id){
    	
    	$.ligerDialog.open({ url : 'userUpdatePage.do?isCheck=false&user_id='+user_id,data:{}, height: 350,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '重置密码', onclick: function (item, dialog) { dialog.frame.rePassword(); },cls:'l-dialog-btn-highlight'  },{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveUser(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); },cls:'l-dialog-btn-highlight'  } ] });

    }
    function loadDict(){
    	 $("#user_code").ligerTextBox({width:160});
         //字典下拉框
         autocomplete("#sj_user","../queryUserDict.do?isCheck=false","id","text",true,true); 
         if(parent.sessionJson.type_code==0 || parent.sessionJson.type_code==1){
        	 $("td[name=sjTd]").css("display","none");
         }
    	
    }
    
	function reset(){
	    	
	    	var data = gridManager.getCheckedRows();
	                    if (data.length == 0){
	                    	$.ligerDialog.error('请选择行');
	                    }else{
	                        var ParamVo =[];
	                        $(data).each(function (){					
								ParamVo.push(this.user_id 
								) });
	                        $.ligerDialog.confirm('确定重置密码：123456.?', function (yes){
	                        	if(yes){
	                            	ajaxJsonObjectByUrl("updateBatchUserPwd.do",{ParamVo : ParamVo.toString()},function (responseData){
	                            		if(responseData.state=="true"){
	                            			query();
	                            		}
	                            	});
	                        	}
	                        }); 
	                    }
	   }
	
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	/* var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	}); */
    	
   		
   		var printPara={
   			rowCount:1,
   			title:'用户列表',
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.sys.service.UserService",
			method_name: "queryUserPrint",
			bean_name: "userService"
   			};
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
   		
    }

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户：</td>
            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" name="sjTd" class="l-table-edit-td"  style="padding-left:20px;">上级用户：</td>
            <td align="left" name="sjTd" class="l-table-edit-td"><input name="sj_user" type="text" id="sj_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left" name="sjTd" ></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
