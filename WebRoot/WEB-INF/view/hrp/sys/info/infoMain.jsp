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
    	loadDict();
    	loadTree();
    	loadHead(null);	//加载数据
    	$("#hos_code").ligerTextBox({});
		$("#hos_name").ligerTextBox({});
		$("#hos_simple").ligerTextBox({});
		$("#is_stop").ligerTextBox({});
		$("#hos_legal").ligerTextBox({});
		$("#hos_contact").ligerTextBox({});
		$("#hos_email").ligerTextBox({});
		$("#hos_phone").ligerTextBox({});
		$("#hos_address").ligerTextBox({});
		$("#user_code").ligerTextBox({});
		$("#hos_zipcode").ligerTextBox({});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件

    	//加载查询条件
    	grid.loadData(grid.where);
     }
   

    function loadHead(){
    	var hos_id = $("#hos_id").val();
		var group_id = $("#group_id").val();
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '医院编码', name: 'hos_code', align: 'left'
					 },
                     { display: '医院名称', name: 'hos_name', align: 'left'
					 },
                     { display: '简称', name: 'hos_simple', align: 'left'
					 },
                     { display: '操作人', name: 'user_code', align: 'left'
					 },
                     { display: '操作时间', name: 'create_date', align: 'left'
					 },
                     { display: '变更原因', name: 'note', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'../infodict/queryInfoDict.do?hos_id='+hos_id+'&group_id='+group_id,
                     width: '100%', height: '100%', checkbox: false,rownumbers: true,delayLoad: true,
                     selectRowButtonOnly:false
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(){
		var hos_id = $("#hos_id").val();
		var group_id = $("#group_id").val();
		if(hos_id != null && hos_id != ""){
			$.ligerDialog.open({ url : 'infoUpdatePage.do?isCheck=false&hos_id=' + hos_id+'&group_id='+group_id,data:{}, height: 300,width: 400, title:'医院变更',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savehos(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		}

    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#hos_level","../queryHosLevelDict.do?isCheck=false","id","text",true,true);
        autocomplete("#hos_type","../queryHosTypeDict.do?isCheck=false","id","text",true,true);
        autocomplete("#super_code","../queryHosInfo.do?isCheck=false","id","text",true,true);
        autocomplete("#hos_city","../queryRegionDict.do?isCheck=false","id","text",true,true);
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
			onClick:hosDetail
		}
   }; 
    function loadTree(){
    	ajaxJsonObjectByUrl("queryInfoByMenu.do",null,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	});
    }
    
    var userId;
	function hosDetail(){
		var node = tree.getSelectedNodes();
		$.each(node,function(index,value){
			clearFrom();
			ajaxJsonObjectByUrl("queryInfoByCode.do?isCheck=false&hos_id="+value.id+"&group_id="+value.group_id+"&type_code=4",null,function (responseData){
				$("#hos_code").attr("disabled", "disabled");
				$("#hos_name").attr("disabled", "disabled");
				$("#hos_simple").attr("disabled", "disabled");
				$("#hos_code").val(responseData.info.hos_code);
				$("#hos_name").val(responseData.info.hos_name);
				$("#hos_simple").val(responseData.info.hos_simple);
				$("#is_stop").val(responseData.info.is_stop);
				$("#hos_contact").val(responseData.info.hos_contact);
				$("#hos_email").val(responseData.info.hos_email);
				$("#hos_phone").val(responseData.info.hos_phone);
				$("#hos_address").val(responseData.info.hos_address);
				$("#hos_zipcode").val(responseData.info.hos_zipcode);
				$("#note").val(responseData.info.note);
				$("#hos_id").val(responseData.info.hos_id);
				$("#group_id").val(responseData.info.group_id);
				$("#user_code").val(responseData.info.user_code);
				liger.get("hos_level").setValue(responseData.info.hos_level);
		        liger.get("hos_type").setValue(responseData.info.hos_type);
		        liger.get("super_code").setValue(responseData.info.super_code);
		        liger.get("hos_city").setValue(responseData.info.hos_city);
		        userId=responseData.info.user_id;
		        loadHead(null);
			});
		});
		
	}
    	
    </script>
	<script>
	function clearFrom(){
		$('#hos_code').removeAttr("disabled");
		$('#hos_name').removeAttr("disabled");
		$('#hos_simple').removeAttr("disabled");
		$("#hos_code").val("");
		$("#hos_name").val("");
		$("#hos_simple").val("");
		$("#is_stop").val("");
		$("#hos_contact").val("");
		$("#hos_email").val("");
		$("#hos_phone").val("");
		$("#hos_address").val("");
		$("#hos_zipcode").val("");
		$("#note").val("");
		$("#hos_id").val("");
		$("#group_id").val("");
		$("#hos_level").val("");
        $("#hos_type").val("");
        $("#super_code").val("");
        $("#hos_city").val("");
        $("#user_code").val("");
	}
	function resetPass(){
		var node = tree.getSelectedNodes();
		if(node == ""){
			$.ligerDialog.error('请先选择医院');
			return;
		}
		$.ligerDialog.confirm('是否重置：123456?', function (yes){
			 if(yes){
				
	            ajaxJsonObjectByUrl("../user/updateBatchUserPwd.do",{ParamVo: userId},function (responseData){
	                if(responseData.state=="true"){
	                	$.ligerDialog.success('重置成功');
	                }
	            });
			 }
		});
	}
	
	function deletehos(){
		var node = tree.getSelectedNodes();
		if(node == ""){
			$.ligerDialog.error('请先选择医院');
			return;
		}
		$.each(node,function(index,value){
			var ParamVo =[];
			ParamVo.push(value.id +"@"+ value.group_id);
            $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteInfo.do",{ParamVo : ParamVo},function (responseData){
                 		if(responseData.state=="true"){
                 			loadTree();
                 			query();
                 			clearFrom();
                 			loadDict();
                 		}
                 	});
             	}
             }); 
		});
	}
	
	function save(){
		if($("#hos_code").val()==""){
			$.ligerDialog.error('医院编码不能为空');
			return;
		}
	
		if($("#hos_name").val()==""){
			$.ligerDialog.error('医院名称不能为空');
			return;
		}
		
		if($("#hos_simple").val()==""){
			$.ligerDialog.error('医院简称不能为空');
			return;
		}
		
		if($("#user_code").val()==""){
			$.ligerDialog.error('医院管理员不能为空');
			return;
		}
		
		if(liger.get("super_code").getValue()==""){
			$.ligerDialog.error('上级医院不能为空');
			return;
		}
		
		if(liger.get("hos_level").getValue()==""){
			$.ligerDialog.error('医院等级不能为空');
			return;
		}
		
		if(liger.get("hos_city").getValue()==""){
			$.ligerDialog.error('地区不能为空');
			return;
		}
		
		var hos_id = $("#hos_id").val();
		var group_id = $("#group_id").val();
		if(hos_id == ""){//添加
			var param = {
					hos_id:'',
					hos_code:$("#hos_code").val(),
					hos_name:$("#hos_name").val(),
					hos_simple:$("#hos_simple").val(),
					is_stop:$("#is_stop").val(),
					hos_contact:$("#hos_contact").val(),
					hos_email:$("#hos_email").val(),
					hos_zipcode:$("#hos_zipcode").val(),
					hos_phone:$("#hos_phone").val(),
					hos_address:$("#hos_address").val(),
					note:$("#note").val(),
					hos_level:liger.get("hos_level").getValue(),
					hos_type:liger.get("hos_type").getValue(),
	        		super_code:liger.get("super_code").getValue(),
	        		hos_city:liger.get("hos_city").getValue(),
					user_code:$("#user_code").val(),
					user_name:'医院管理员',
					user_pwd:'123456',
					type_code:4,
					emp_code:'',
					mod_code:''
			};	
		
			ajaxJsonObjectByUrl("addInfo.do",param,function (responseData){
         		if(responseData.state=="true"){
         			loadTree();
         			query();
         			clearFrom();
         			loadDict();
         			//$.ligerDialog.success('默认密码为123456');
         		}
         	});
		}else{//修改
			var param = {
					hos_id:hos_id,
					group_id:group_id,
					hos_code:$("#hos_code").val(),
					hos_name:$("#hos_name").val(),
					hos_simple:$("#hos_simple").val(),
					is_stop:$("#is_stop").val(),
					hos_contact:$("#hos_contact").val(),
					hos_email:$("#hos_email").val(),
					hos_zipcode:$("#hos_zipcode").val(),
					hos_phone:$("#hos_phone").val(),
					hos_address:$("#hos_address").val(),
					note:$("#note").val(),
					hos_level:liger.get("hos_level").getValue(),
					hos_type:liger.get("hos_type").getValue(),
	        		super_code:liger.get("super_code").getValue(),
	        		hos_city:liger.get("hos_city").getValue(),
					user_code:$("#user_code").val(),
					user_id: userId
			};
			ajaxJsonObjectByUrl("updateInfo.do",param,function (responseData){
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
               { text: '删除',icon:'delete', click: deletehos }
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
	              	<input type="hidden" id="hos_id" name="hos_id"/>
	              	<input type="hidden" id="group_id" name="group_id"/>
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" align="center" >
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>医院编码：</td>
			                <td align="left" class="l-table-edit-td"><input  name="hos_code" style="width: 163px;" type="text" id="hos_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>医院名称：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_name" style="width: 163px;" type="text" id="hos_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>医院管理员：</td>
			                <td align="left" class="l-table-edit-td">
			                <input name="user_code" type="text" style="width: 163px;"  id="user_code" ltype="text" validate="{required:true,maxlength:20}" />
			                </td>
			                <td align="left"><div class="l-button" style="width: 60px;" ligeruiid="Button1002" onclick="resetPass();">
		       				<span>密码重置</span></div></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>医院简称：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_simple" style="width: 163px;" type="text" id="hos_simple" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>上级医院：</td>
			                <td align="left" class="l-table-edit-td"><input name="super_code" type="text" id="super_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>医院等级：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_level" type="text" id="hos_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>地区：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_city" type="text" id="hos_city" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
			                <td align="left" class="l-table-edit-td">
			                	<select id="is_stop" name="is_stop" style="width: 163px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
			                </td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系人：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_contact" style="width: 163px;" type="text" id="hos_contact" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医院分类：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_type" type="text" id="hos_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系电话：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_phone" style="width: 163px;" type="text" id="hos_phone" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">邮编：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_zipcode" style="width: 163px;" type="text" id="hos_zipcode" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			            </tr> 
			            <tr>
			            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">邮箱：</td>
			                <td align="left" class="l-table-edit-td"><input name="hos_email" style="width: 163px;" type="text" id="hos_email" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left"></td>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
			                <td align="left" class="l-table-edit-td"></td>
			                <td align="left"></td>
			            </tr>   
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地址：</td>
			                <td align="left" class="l-table-edit-td" colspan="4"><input name="hos_address" type="text" size="70" id="hos_address" ltype="text" validate="{required:true,maxlength:20}" /></td>
			                <td align="left" ></td>
			            </tr> 
			            <tr>
			                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
			                <td align="left" class="l-table-edit-td" colspan="4">
			                	<textarea rows="3" cols="70" id="note" name="note"></textarea>
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
		       					<span>医院变更</span></div>
			                </td>
			                <td align="left"></td>
			            </tr> 
			        </table>
    			</form>
	           </div>
         </div>  
         <div class="l-layout-centerbottom" style="left: 205px; top: 330px; width: 975px; "><div class="l-layout-header">变更记录</div>
	         <div title="" class="l-layout-content" style="" position="centerbottom">
	             	<div id="maingrid"></div>
	          </div>
          </div>  
	</div>
</body>
</html>
