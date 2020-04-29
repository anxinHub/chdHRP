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
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var user_id;
    
    var role_id;
    
    var tip=1;
    
    var user_type_code='${type_code}';
    
    $(function ()
    {
    	
    	loadDict();
    	
    	loadHead(null);
    	
    	$("#hos_code").bind("change", function () { change_copy_code(); });
		
    	
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
    		var is_copy = mod_code.split(".")[7];
    		var is_acc_year = mod_code.split(".")[8];
    		if(is_copy == 1 && liger.get("copy_code").getValue() == ""){
    			$.ligerDialog.error('请先选择账套');
        		return;
    		}
    		if(is_acc_year == 1 && $("#acc_year").val() == ""){
    			$.ligerDialog.error('请先选择年度');
        		return;
    		}
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({name:'code',value:$("#code").val()}); 
    		grid.options.parms.push({name:'name',value:$("#name").val()}); 
    		grid.options.parms.push({name:'user_id',value:user_id}); 
    		grid.options.parms.push({name:'role_id',value:role_id});
    		grid.options.parms.push({name:'hos_id',value:liger.get("hos_code").getValue()});
    		grid.options.parms.push({name:'obj_code',value:liger.get("mod_code").getValue()}); 
    		grid.options.parms.push({name:'copy_code',value:liger.get("copy_code").getValue()}); 
    		grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
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
								var prm_obj_id=rowdata.obj_id.replace(/\./g,'-');
								if(value == 1){
									return "<input name='is_read"+prm_obj_id+"' checked='checked' type='checkbox' id='is_read"+prm_obj_id+"' ltype='text'"
										+" style='margin-top:5px;' />";
								}
								return "<input name='is_read"+prm_obj_id+"' type='checkbox' id='is_read"+prm_obj_id+"' ltype='text'"
									+" style='margin-top:5px;' />";
							}
					 },
                     { display: '写入权限', name: 'is_write', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								var prm_obj_id=rowdata.obj_id.replace(/\./g,'-');
								if(value == 1){
									return "<input name='is_write"+prm_obj_id+"' checked='checked' type='checkbox' id='is_write"+prm_obj_id+"' ltype='text'"
										+" style='margin-top:5px;' />";
								}
								return "<input name='is_write"+prm_obj_id+"' type='checkbox' id='is_write"+prm_obj_id+"' ltype='text'"
									+" style='margin-top:5px;' />";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPermData.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                    					{ text: '保存', id:'add', click: addPerm, icon:'add' },
                    	                { line:true },
                    	                { text: '批量保存', id:'batchadd', click: batchadd,icon:'add' }
                    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function batchadd(){
    	
    	var obj = liger.get("mod_code").getValue();
    	var table_code = obj.split(".")[0];
    	if(!table_code){
    		$.ligerDialog.error('请先选择权限');
    		return;
    	}
		var copy_code;
		var is_copy = obj.split(".")[7];
		
		
    	if(is_copy == 0){
    		copy_code = '0';
    	}else{
    		copy_code =liger.get("copy_code").getValue();
    		if(!copy_code){
    			$.ligerDialog.error('请先选择账套');
        		return;
    		}
    	}

    	var acc_year;
    	var is_acc_year = obj.split(".")[8];
    	
    	if(is_acc_year == 0){
    		acc_year = '0';
    	}else{
    		acc_year = $("#acc_year").val();
    		if(!acc_year){
    			$.ligerDialog.error('请先选择年度');
        		return;
    		}
    	}
    	
    	if(tip==1){
    		if(!user_id){
    			$.ligerDialog.error('请先选择用户');
        		return;
    		}
			
		}else{
			if(!role_id){
    			$.ligerDialog.error('请先选择角色');
        		return;
    		}
			
		}
    	$.ligerDialog.open({ url : 'batchAddPermPage.do?isCheck=false',data:{}, height: 300,width: 350, title:'批量保存',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    function alertRole(role_id){
    	$.ligerDialog.open({ url : 'roleUserMainPage.do?isCheck=false&role_id='+role_id,data:{}, height: 600,width: 700, title:'查看',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [  { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    function addPerm(){
    	
    	
    	var obj = liger.get("mod_code").getValue();
    	
    	var mod_code = obj.split(".")[1];
    	
    	var table_code = obj.split(".")[0];
    	if(!table_code){
    		$.ligerDialog.error('请先选择权限');
    		return;
    	}
    	var data = gridManager.getData();

		var is_acc_year = obj.split(".")[8];
    	
		var copy_code;
		var is_copy = obj.split(".")[7];
		
    	if(is_copy == 0){
    		copy_code = '0';
    	}else{
    		copy_code =liger.get("copy_code").getValue();
    		if(!copy_code){
    			$.ligerDialog.error('请先选择账套');
        		return;
    		}
    	}

    	var acc_year;
    	var is_acc_year = obj.split(".")[8];
    	
    	if(is_acc_year == 0){
    		acc_year = '0';
    	}else{
    		acc_year = $("#acc_year").val();
    		if(!acc_year){
    			$.ligerDialog.error('请先选择年度');
        		return;
    		}
    	}
        var hos_id='0';
        hos_id=liger.get("hos_code").getValue();
        
        if(hos_id==''){
        	hos_id='0';
        }
    	
    	var param = [];
    	$.each(data,function(i,v){
    		var prm_obj_id=v.obj_id.replace(/\./g,'-');
    		if(tip==1){
				param.push(user_id+"@"+table_code+"@"+v.obj_id+"@"+mod_code+"@"+$("#is_read"+prm_obj_id+"").attr("checked")+"@"+$("#is_write"+prm_obj_id+"").attr("checked")+"@"+copy_code+"@"+acc_year+"@"+hos_id);
			}else{
				param.push(role_id+"@"+table_code+"@"+v.obj_id+"@"+mod_code+"@"+$("#is_read"+prm_obj_id+"").attr("checked")+"@"+$("#is_write"+prm_obj_id+"").attr("checked")+"@"+copy_code+"@"+acc_year+"@"+hos_id);
			}
    	});
    	if(tip==1){
    		if(!user_id){
    			$.ligerDialog.error('请先选择用户');
        		return;
    		}
			$.post("addUserPermData.do",{ParamVo : param},function(data){
				if(data != null){
					$.ligerDialog.success('保存成功');
					query();
				}
			},"json");
		}else{
			if(!role_id){
    			$.ligerDialog.error('请先选择角色');
        		return;
    		}
			$.post("addRolePermData.do",{ParamVo : param},function(data){
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
			role_id=null;
			$("#iframe").attr("src","userMainPage.do?isCheck=false");
		}else if(code == 2){
			tip=2;
			user_id=null;
			$("#iframe").attr("src","roleMainPage.do?isCheck=false");
		}
	}
   
	function loadDict(){
       
    	$("#acc_year").ligerTextBox({width:160});
    	
    	$("#code").ligerTextBox({width:160});
    	
    	$("#name").ligerTextBox({width:160});
    	
		 $("#mod_code").ligerComboBox({
	      	url: '../queryPermData.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 180,
	      	autocomplete: true,
	      	width: 180,
	      	onSelected :function(newvalue){
	      		if(!newvalue){
	      			return;
	      		}
	      		var is_acc_year = newvalue.split(".")[8];
	      		
				if(is_acc_year == '1'){
    	        	
    	        	$("#acc_year_1").show();$("#acc_year_2").show();
    	        	
    	        }else{
    	        	
    	        	$("#acc_year_1").hide();$("#acc_year_2").hide();
    	        	
    	        }
				
	      		query();
	      	},onSuccess  :function (data){
	      		liger.get("mod_code").setValue(data[0].id);
    			liger.get("mod_code").setText(data[0].text);
	      	}
		 });
		 
		$("#acc_year_1").hide();$("#acc_year_2").hide();
		autocomplete("#hos_code","../queryHosInfoDict.do?isCheck=false","id","text",true,true);  
    	
		autocomplete("#copy_code","../queryCopyCodeDict.do?isCheck=false","id","text",true,true,{},true); 
		change();
     }  
	function change_copy_code(){
    	var hos_id=liger.get("hos_code").getValue();
    	autocompleteObj({
    		id: "#copy_code",
    		urlStr: "../queryCopyCodeDict.do?isCheck=false&hos_id="+hos_id,
    		valueField: "id",
    		textField: "text",
    		autocomplete: true,
    		highLight: true,
    		parmsStr: {},
    		defaultSelect: true,
    		selectEvent: selectPage(1)
    	}); 
    } 
	function change(){	//控制医院是否显示
 	   if(user_type_code!='3'){ 
 		   $(".demo").css("display","none");
		 }else{
			 $(".demo").css("display","");
		 }  
	 }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left:20px;">权限：</td>
			<td align="right" class="l-table-edit-td">
				<input name="mod_code" type="text" id="mod_code" ltype="text"  validate="{required:true,maxlength:20}" />
			</td>
			<td align="right" class="l-table-edit-td demo"  style="padding-left:20px;">医院：</td>
            <td align="left" class="l-table-edit-td demo"><input name="hos_code" type="text" id="hos_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left" class="l-table-edit-td demo"></td>
			<td align="right" class="l-table-edit-td" style="padding-left:20px;">账套：</td>
			<td align="right" class="l-table-edit-td">
				<input name="copy_code" type="text" id="copy_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			
			<td id="acc_year_1" align="right" class="l-table-edit-td" style="padding-left:20px;">年度：</td>
			<td id="acc_year_2" align="right" class="l-table-edit-td">
				<input class="Wdate" name="acc_year" type="text" id="acc_year" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" ltype="text"/>
			</td>
		</tr>
	</table>
     <div class="l-layout" id="layout1" style="height: 100%;width:1000px;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 400px; height: 100%;">
		       	<iframe src="userMainPage.do?isCheck=false" id="iframe" frameborder="0"width="100%;" height="94%;"></iframe>
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