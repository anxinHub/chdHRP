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
    var gridManager = null;
    var userUpdateStr;
    var user_id;
    var role_id;
    var tip=1;
    $(function ()
    {

    	loadDict();
    	loadHead(null);
    });
    
    function  query(){
    	if(tip==1 && (user_id == "" || user_id == null)){
    		//$.ligerDialog.error('请先选择用户');
    		return;
    	}
    	if(tip==2 && (role_id == "" || role_id == null)){
    		$.ligerDialog.error('请先选择角色');
    		return;
    	}
    	var mod_code = liger.get("mod_code").getValue();
    	if(mod_code != ""){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({name:'code',value:$("#code").val()}); 
    		grid.options.parms.push({name:'name',value:$("#name").val()}); 
    		grid.options.parms.push({name:'user_id',value:user_id}); 
    		grid.options.parms.push({name:'role_id',value:role_id}); 
    		grid.options.parms.push({name:'obj_code',value:liger.get("mod_code").getValue()}); 
    		grid.options.parms.push({name:'tip',value:tip}); 
    		//加载查询条件
    		grid.loadData(grid.where);
    	}else{
    		$.ligerDialog.error('请先选择权限');
    		return;
    	}
 }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '编码', name: 'obj_code', align: 'left'
					 },
                     { display: '名称', name: 'obj_name', align: 'left'
					 },
                     { display: '读取权限', name: 'is_read', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(value == 1){
									return "<input name='is_read"+rowdata.obj_id+"' checked='checked' type='checkbox' id='is_read"+rowdata.obj_id+"' ltype='text'"
										+" style='margin-top:5px;' />";
								}
								return "<input name='is_read"+rowdata.obj_id+"' type='checkbox' id='is_read"+rowdata.obj_id+"' ltype='text'"
									+" style='margin-top:5px;' />";
							}
					 },
                     { display: '写入权限', name: 'is_write', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(value == 1){
									return "<input name='is_write"+rowdata.obj_id+"' checked='checked' type='checkbox' id='is_write"+rowdata.obj_id+"' ltype='text'"
										+" style='margin-top:5px;' />";
								}
								return "<input name='is_write"+rowdata.obj_id+"' type='checkbox' id='is_write"+rowdata.obj_id+"' ltype='text'"
									+" style='margin-top:5px;' />";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPermData.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                    					{ text: '保存', id:'add', click: addPerm, icon:'add' },
                    	                { line:true },
                    	                { text: '批量保存', id:'batchadd', click: itemclick,icon:'add' }
                    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function itemclick(item){
    	if(item.id)
        {
            switch (item.id)
            {
            
                case "batchadd":
                	 $.ligerDialog.open({ url : 'accBatchAddPermPage.do',data:{}, height: 300,width: 300, title:'批量保存',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
                    return;
            }   
        }
    }
    
    function alertRole(role_id){
    	$.ligerDialog.open({ url : 'accRoleUserMainPage.do?isCheck=false&role_id='+role_id,data:{}, height: 400,width: 500, title:'查看',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [  { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    function addPerm(){
    	var obj = liger.get("mod_code").getValue();
    	var mod_code = obj.split(".")[1];
    	var table_code = obj.split(".")[0];
    	
    	var data = gridManager.getData();
    	var param = [];
    	
    	
    	$.each(data,function(i,v){
    		if($("#is_read"+v.obj_id+"").attr("checked")==false && $("#is_write"+v.obj_id+"").attr("checked")==false){
    			
    		}else{
    			if(tip==1){
    				param.push(user_id+"@"+table_code+"@"+v.obj_id+"@"+mod_code+"@"+$("#is_read"+v.obj_id+"").attr("checked")+"@"+$("#is_write"+v.obj_id+"").attr("checked"));
    			}else{
    				param.push(role_id+"@"+table_code+"@"+v.obj_id+"@"+mod_code+"@"+$("#is_read"+v.obj_id+"").attr("checked")+"@"+$("#is_write"+v.obj_id+"").attr("checked"));
    			}
    		}
    	});
    	if(tip==1){
			$.post("addAccUserPermData.do",{ParamVo : param},function(data){
				if(data != null){
					$.ligerDialog.success('保存成功');
					query();
				}
			},"json");
		}else{
			$.post("addAccRolePermData.do?isCheck=false",{ParamVo : param},function(data){
				if(data != null){
					$.ligerDialog.success('保存成功');
					query();
				}
			},"json");
		}
    }
    
	function selectPage(code){
		if(code == 1){
			tip=1;
			$("#iframe").attr("src","accUserMainPage.do?isCheck=false");
		}else if(code == 2){
			tip=2;
			$("#iframe").attr("src","accRoleMainPage.do?isCheck=false");
		}
	}
   
	function loadDict(){
        //字典下拉框
        $("#code").ligerTextBox({width:160});
    	$("#name").ligerTextBox({width:160});
        var param = {
        		'mod_code':'01'
        };
	
	$("#mod_code").ligerComboBox({
      	url: '../../sys/queryPermData.do?isCheck=false',
      	valueField: 'id',
       	textField: 'text', 
       	selectBoxWidth: 180,
      	autocomplete: true,
		keySupport: true,
      	width: 180,
      	parms : param,
		onSuccess : function (data){
			if(data.length >0 ){
				if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
       				liger.get("mod_code").setValue(data[0].id);
       				liger.get("mod_code").setText(data[0].text);
				}
			}
			
       },
      	onSelected :function(newvalue){
      		query();
      	}
	 });
     }  
   
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left:20px;">权限：</td>
			<td align="right" class="l-table-edit-td">
				<input name="mod_code" type="text" id="mod_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="right" class="l-table-edit-td"><input class="l-button l-button-test"  type="button" value="权限说明" onclick=""/></td>
			<td align="right" class="l-table-edit-td">
				<input class="l-button l-button-test"  type="button" value="按用户" onclick="selectPage(1)"/>
			</td>
			<td align="right" class="l-table-edit-td">
				<input class="l-button l-button-test"  type="button" value="按角色" onclick="selectPage(2)"/>
			</td>
		</tr>
	</table>
     <div class="l-layout" id="layout1" style="height: 100%;width:1000px;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 400px; height: 100%;">
		       	<iframe src="accUserMainPage.do?isCheck=false" id="iframe" frameborder="0"width="100%;" height="94%;"></iframe>
          </div>
          <div class="l-layout-center" style="left: 405px; top: 0px; width: 800px; height: 100%;">
	          <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编码：</td>
		            <td align="left" class="l-table-edit-td"><input name="code" type="text" id="code" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">名称：</td>
		            <td align="left" class="l-table-edit-td"><input name="name" type="text" id="name" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"><input class="l-button l-button-test"  type="button" value="查询" onclick="query()"/></td>
		        </tr> 
		    </table>

			<div id="maingrid"></div>
         </div>  
	</div>
</body>
</html>
