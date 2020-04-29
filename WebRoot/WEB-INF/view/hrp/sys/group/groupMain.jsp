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
    $(function ()
    {
    	$("#group_code").ligerTextBox({});
		$("#group_name").ligerTextBox({});
		$("#group_simple").ligerTextBox({});
		$("#is_stop").ligerTextBox({});
		$("#group_legal").ligerTextBox({});
		$("#group_contact").ligerTextBox({});
		$("#group_email").ligerTextBox({});
		$("#group_phone").ligerTextBox({});
		$("#group_address").ligerTextBox({});
		$("#user_code").ligerTextBox({});
    	loadTree();
    	loadHead(null);	//加载数据
    	
    });

   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '集团编码', name: 'group_code', align: 'left'
					 },
                     { display: '集团名称', name: 'group_name', align: 'left'
					 },
                     { display: '简称', name: 'group_simple', align: 'left'
					 },
                     { display: '操作人', name: 'user_code', align: 'left'
					 },
                     { display: '操作时间', name: 'create_date', align: 'left'
					 },
                     { display: '变更原因', name: 'note', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'../groupdict/queryGroupDict.do?group_id='+$("#group_id").val(),
                     width: '100%', height: '100%', checkbox: false,rownumbers: true, delayLoad: true,  
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(){
		var group_id = $("#group_id").val();
		if(group_id != null && group_id != ""){
			$.ligerDialog.open({ url : 'groupUpdatePage.do?isCheck=false&group_id=' + group_id,data:{}, height: 300,width: 400, title:'集团变更',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveGroup(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		}

    }
    </script>
    
    <script>
    var tree;
    var setting = {      
		data: {
			simpleData: {
				enable: true
			}
		},
		treeNode:{
			open:true
		},
		callback:{
			onClick:groupDetail
		}
   }; 
    function loadTree(){
    	ajaxJsonObjectByUrl("queryGroupByMenu.do",null,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    
    var userId;
	function groupDetail(){
		var node = tree.getSelectedNodes();
		$.each(node,function(index,value){
			clearFrom();
			ajaxJsonObjectByUrl("queryGroupByCode.do?isCheck=false&group_id="+value.id+"&hos_id=0&type_code=3",null,function (responseData){
				$("#group_code").attr("disabled", "disabled");
				$("#group_name").attr("disabled", "disabled");
				$("#group_simple").attr("disabled", "disabled");
				$("#group_code").val(responseData.group.group_code);
				$("#group_name").val(responseData.group.group_name);
				$("#group_simple").val(responseData.group.group_simple);
				$("#is_stop").val(responseData.group.is_stop);
				$("#group_legal").val(responseData.group.group_legal);
				$("#group_contact").val(responseData.group.group_contact);
				$("#group_email").val(responseData.group.group_email);
				$("#group_phone").val(responseData.group.group_phone);
				$("#group_address").val(responseData.group.group_address);
				$("#note").val(responseData.group.note);
				$("#group_id").val(responseData.group.group_id);
				$("#user_code").val(responseData.group.user_code);
				userId=responseData.group.user_id;
				loadHead(null);
			});
		});
		
	}
    	
    </script>
	<script>
	function clearFrom(){
		$('#group_code').removeAttr("disabled");
		$('#group_name').removeAttr("disabled");
		$('#group_simple').removeAttr("disabled");
		$("#group_code").val("");
		$("#group_name").val("");
		$("#group_simple").val("");
		$("#is_stop").val("");
		$("#group_legal").val("");
		$("#group_contact").val("");
		$("#group_email").val("");
		$("#group_phone").val("");
		$("#group_address").val("");
		$("#note").val("");
		$("#group_id").val("");
		$("#user_code").val("");
	}
	function resetPass(){
		var node = tree.getSelectedNodes();
		if(node == ""){
			$.ligerDialog.error('请先选择集团');
			return;
		}
		 $.ligerDialog.confirm('是否重置：123456?', function (yes){
			 if(yes){
		            ajaxJsonObjectByUrl("../user/updateBatchUserPwd.do",{ParamVo : userId},function (responseData){
		                if(responseData.state=="true"){
		                	$.ligerDialog.success('重置成功');
		                }
		            });
				
			 }
		 });
	}
	
	function deleteGroup(){
		var node = tree.getSelectedNodes();
		if(node == ""){
			$.ligerDialog.error('请先选择集团');
			return;
		}
		$.each(node,function(index,value){
			var ParamVo =[];
			ParamVo.push(value.id);
            $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteGroup.do",{ParamVo : ParamVo},function (responseData){
                 		if(responseData.state=="true"){
                 			loadTree();
                 			query();
                 			clearFrom();
                 		}
                 	});
             	}
             }); 
		});
	}
	
	function save(){
		if($("#group_code").val()==""){
			$.ligerDialog.error('集团编码不能为空');
			return;
		}
	
		if($("#group_name").val()==""){
			$.ligerDialog.error('集团名称不能为空');
			return;
		}
		
		if($("#group_simple").val()==""){
			$.ligerDialog.error('集团简称不能为空');
			return;
		}
		
		if($("#user_code").val()==""){
			$.ligerDialog.error('集团管理员不能为空');
			return;
		}
		
		var group_id = $("#group_id").val();
		if(group_id == ""){//添加
			var param = {
					group_id:0,
					group_code:$("#group_code").val(),
					group_name:$("#group_name").val(),
					group_simple:$("#group_simple").val(),
					is_stop:$("#is_stop").val(),
					group_legal:$("#group_legal").val(),
					group_contact:$("#group_contact").val(),
					group_email:$("#group_email").val(),
					group_phone:$("#group_phone").val(),
					group_address:$("#group_address").val(),
					note:$("#note").val(),
					hos_id:0,
					user_code:$("#user_code").val(),
					user_name:'集团管理员',
					user_pwd:'123456',
					type_code:3,
					emp_code:'',
					mod_code:''
					
			};	
		
			ajaxJsonObjectByUrl("addGroup.do",param,function (responseData){
         		if(responseData.state=="true"){
         			loadTree();
         			query();
         			clearFrom();
         			//$.ligerDialog.success('默认密码为123456');
         		}
         	});
		}else{//修改
			var param = {
					group_id:group_id,
					group_code:$("#group_code").val(),
					group_name:$("#group_name").val(),
					group_simple:$("#group_simple").val(),
					is_stop:$("#is_stop").val(),
					group_legal:$("#group_legal").val(),
					group_contact:$("#group_contact").val(),
					group_email:$("#group_email").val(),
					group_phone:$("#group_phone").val(),
					group_address:$("#group_address").val(),
					note:$("#note").val(),
					user_code:$("#user_code").val(),
					user_id: userId
			};
			ajaxJsonObjectByUrl("updateGroup.do",param,function (responseData){
         		if(responseData.state=="true"){
         		}
         	});
		}
	}
	</script>
	 <script type="text/javascript">
       function itemclick(item)
       {
           alert(item.text);
       }
       $(function ()
       {
           $("#toptoolbar").ligerToolBar({ items: [
               {text: '添加', icon:'add', click: clearFrom},
               { line:true },
               { text: '删除',icon:'delete', click: deleteGroup }
           ]
           });
           
       });
   </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

     <div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
           
          <div class="l-layout-left" style="left: 0px; top: 0px; width: 200px; height: 100%;">
          <div class="l-layout-header" id="toptoolbar"></div>
	       <div class="l-layout-content" position="left">
		       	<div class="ztree"  style="float: left " >
		       		<ul id="tree"></ul>
		       	</div>
	       </div>
          </div>
          <div class="l-layout-center" style="left: 205px; top: 0px; width: 975px; height: 100%;">
	           <div title="" class="l-layout-content" style="height: 500px;" position="center">
	              <form name="form1" method="post"  id="form1" >
	              	<input type="hidden" id="group_id" name="group_id"/>
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" align="center" >
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>集团编码：</td> 
			                <td align="left" class="l-table-edit-td"><input  name="group_code" type="text" id="group_code" size="28" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>集团名称：</td>
			                <td align="left" class="l-table-edit-td"><input name="group_name" type="text" id="group_name" size="28" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>集团管理员：</td>
			                <td align="left"  class="l-table-edit-td">
			                	<input name="user_code" type="text" size="28"  style="float: left;" id="user_code" ltype="text" validate="{required:true,maxlength:20}" />
			                </td>
			                <td align="left"> <div class="l-button" style="width: 60px; " ligeruiid="Button1002" onclick="resetPass();">
		       				<span>密码重置</span></div></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>集团简称：</td>
			                <td align="left" class="l-table-edit-td"><input name="group_simple" type="text" id="group_simple" size="28" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
			                <td align="left" class="l-table-edit-td">
			                	<select id="is_stop" name="is_stop" style="width: 195px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
			                </td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">法人：</td>
			                <td align="left" class="l-table-edit-td"><input name="group_legal" type="text" id="group_legal" size="28" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系人：</td>
			                <td align="left" class="l-table-edit-td"><input name="group_contact" type="text" id="group_contact" size="28" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">邮箱：</td>
			                <td align="left" class="l-table-edit-td"><input name="group_email" type="text" id="group_email" size="28" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系电话：</td>
			                <td align="left" class="l-table-edit-td"><input name="group_phone" type="text" id="group_phone" size="28" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
			                <td align="left" class="l-table-edit-td"></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地址：</td>
			                <td align="left" class="l-table-edit-td" colspan="4"><input name="group_address" type="text" size="60" id="group_address" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left" ></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
			                <td align="left" class="l-table-edit-td" colspan="4">
			                	<textarea rows="3" cols="60" id="note" name="note"></textarea>
			                </td>
			                <td align="left"></td>
			            </tr> 
						<tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
			                <td align="left" class="l-table-edit-td" >
			                	<div class="l-button" style="width: 60px; float: left; margin-right: 40px;margin-left: 18px;" ligeruiid="Button1004" onclick="save();">
		       					<span>保存</span></div>
			                </td>
			                <td align="left"></td>
			                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
			                <td align="left" class="l-table-edit-td" >
			                	<div class="l-button" style="width: 60px; float: left; margin-right: 40px;margin-left: 18px;" ligeruiid="Button1005" onclick="openUpdate();">
		       					<span>集团变更</span></div>
			                </td>
			                <td align="left"></td>
			            </tr> 
			        </table>
    			</form>
	           </div>
         </div>  
         <div class="l-layout-centerbottom" style="left: 205px; top: 270px; width: 975px; "><div class="l-layout-header">变更记录</div>
	         <div title="" class="l-layout-content" style="" position="centerbottom">
	             	<div id="maingrid"></div>
	          </div>
          </div>  
	</div>
</body>
</html>
