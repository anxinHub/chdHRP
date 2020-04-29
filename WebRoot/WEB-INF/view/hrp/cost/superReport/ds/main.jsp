<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript" ></script>


<script type="text/javascript">
var cGrid = null;
var pGrid = null;
//var gridManager=null;
var navtab=null;
var tree;
var setting;

	$(function (){
    	$("#layout1").ligerLayout({ leftWidth: 210,allowLeftResize: true,
    		onLeftToggle: function (isColl){
            	//alert(isColl ? "收缩" : "显示");
				cGrid._onResize();
				pGrid._onResize();
        	}
	    	,onEndResize: function(isColl) {
	    		cGrid._onResize();
				pGrid._onResize();
	        }
    	});
    	setting = {      
 		   		data: {simpleData: {enable: true},key: {title: "title"}},
 		   		treeNode:{open:true},
 		   		callback:{onClick:myClick}
 	 	};
    	$("#tab1").ligerTab(
    	{
    		onBeforeSelectTabItem: function (tabid){
    			
	    		if(tabid=="item1"){
	    			setTimeout(function(){
	    				cGrid._onResize();
	    				pGrid._onResize();
	    			},50);
	    			
	    		}
	    	}
    	});
        navtab = $("#tab1").ligerGetTabManager();
    	$("#treeDiv").css("height", $(window).height()-28);
    	
    	$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
   	 	$("#sort_code").attr( "disabled", true );
   	 	$("#is_sys_text").text("自定义数据集");
 		$("#is_sys_text").css("color","blue");

   	 	loadDict();
		$("form").ligerForm();
		$("[name='textAreaWidth']").css("width", $(window).width()-230);
		$("[name='textAreaWidth']").css("height", $(window).height()-180);
		$("#note").ligerTextBox({ width: 555 });
		$("#ds_type").ligerComboBox({});

    	$(':button').ligerButton({width:120});
    	loadTree();
       	loadHead();
    });

	function loadDict(){
		
		autocompleteAsync("#mod_code","../../../sys/queryModDictAdminPerm.do?isCheck=false&mod_code_where='0102'","id","text",false,true,'',false,$("#mod_code_init").val());
		
		if($("#mod_code_init").val()=="01"){
			$("#mod_code").ligerComboBox({ cancelable:false });
		}else{
			$("#mod_code").ligerComboBox({ disabled: true,cancelable:false });
		}
	}
	
    function loadTree(selNode){
		ajaxJsonObjectByUrl("querySuperReportDsManager.do?isCheck=false",{},function(responseData){	
			  tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
			  if(selNode==undefined || selNode.pId==null || selNode.id==null){
	    	  		return;
	    	  }
			  tree.selectNode(selNode,true);
		},false);
    }
    
    function loadHead(){
    	
    	cGrid = $("#cGrid").ligerGrid({
           columns: [ 
					 { display: '数据列编码', name: 'col_code', align: 'left',width:80,
						 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:columnView('"+rowindex+"')>"
				       					+""+rowdata.col_code+"</a>";
							}},
                     { display: '数据列名称', name: 'col_name', align: 'left',width:120},
                     { display: '数据列类型', name: 'col_type', align: 'left',width:90,
                    	 render : function(rowdata, rowindex,value) {
								var type = rowdata.col_type
								if(type == 1){return "字符型";}
								if(type == 2){return "数字型";}
						}},
                     { display: '数据列长度', name: 'col_length', align: 'left',width:90} 
    				],dataAction : 'local',dataType : 'local',usePager : true,enabledSort:false,
    				url : '',parms:{},
    				width : '98%',height : $(window).height()-180,checkbox : true,usePager : false,
    				rownumbers : true,selectRowButtonOnly : true ,heightDiff: 0,
    				colDraggable:true,rowDraggable:true,
    				toolbar: { items: [
    				         { text: '添加数据列', id:'add', click: columnAdd, icon:'add' },
    				         { line:true },
    				         { text: '删除数据列', id:'del', click: columnDel,icon:'delete'}
    				  ]}
                   });

        //gridManager = $("#cGrid").ligerGetGridManager();
        
        pGrid = $("#pGrid").ligerGrid({
            columns: [ 
				{ display: '参数编码', name: 'para_code', align: 'left', width: 100,
					render : function(rowdata, rowindex,value) {
						return "<a href=javascript:myView('"+rowindex+"')>"
							+""+rowdata.para_code+"</a>";
					}
				},
				{ display: '参数名称', name: 'para_name', align: 'left', width:80}, 
				{ display: '参数类型标识', name: 'para_type', align: 'left', width: 100, hide: true},
				{ display: '参数类型', name: 'para_type_name', align: 'left', width: 100},
				{ display: '字典编码', name: 'dict_code', align: 'left', width: 80}, 
				{ display: '字典名称', name: 'dict_name', align: 'left', width: 100},
				{ display: '是否停用标识', name: 'is_stop', align: 'left', width: 100, hide: true},
				{ display: '是否停用', name: 'is_stop_name', align: 'left', width: 70}, 
				{ display: '参数信息', name: 'para_json', align: 'left', width: 180} 
			],dataAction : 'local',dataType : 'local',usePager : true,enabledSort:false,
			url : '',parms:{},
			width : '99%',height : $(window).height()-180,checkbox : true,usePager : false,
			rownumbers : true,selectRowButtonOnly : true ,heightDiff: 0,
			colDraggable:true,rowDraggable:true,
			toolbar: { items: [
				{ text: '添加参数', id:'add', click: paraAdd, icon:'add' },
				{ line:true },
				{ text: '删除参数', id:'del', click: paraDel,icon:'delete'}
			]}
		});
    }
    
   
    function myClick(){
    	operation="update";
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null || node.isParent == true){
    		return;
    	}
    	$("#ds_code").ligerTextBox({ disabled: true });
        $("#ds_code").attr( "disabled", true );
        $("#ds_type").ligerComboBox({ disabled: true,cancelable: false });
    	liger.get("mod_code").setValue(node.mod_code);
    	liger.get("ds_type").setValue(node.ds_type);
    	liger.get("ds_class").setValue(node.ds_class);
    	$("#ds_group").val(node.ds_group);
   		$("#sort_code").ligerTextBox({ disabled: false });
        $("#sort_code").attr( "disabled", false );
    	$("#sort_code").val(node.sort_code);
    	liger.get("state").setValue(parseInt(node.state) == 1);
    	$("#ds_code").val(node.id);
    	$("#ds_name").val(node.name);
    	if($("#mod_code_init").val() != node.mod_code.substring(0,2)){
        	//不是当前模块不能保存
        	$("input[name='saveButton']").attr("disabled",true);
        	$("input[name='saveButton']").ligerButton({disabled: true});
        }else{
        	$("input[name='saveButton']").attr("disabled",false);
        	$("input[name='saveButton']").ligerButton({disabled: false});
        }
    	
    	$("#is_sys").val(node.is_sys);
    	if(node.is_sys==1){
    		$("#is_sys_text").text("系统数据集");
    		$("#is_sys_text").css("color","red");
        }else{
        	$("#is_sys_text").text("自定义数据集");
        	$("#is_sys_text").css("color","blue");
        }



    	var para={
    			ds_code:node.id
    	};
    	//查询报表数据集列
    	ajaxJsonObjectByUrl("querySuperReportDSColoums.do?isCheck=false",para,function(responseData){
    		cGrid.set({ data: responseData});
		});
    	//查询报表数据集参数
    	ajaxJsonObjectByUrl("querySuperReportDSParas.do?isCheck=false",para,function(responseData){  
    		pGrid.set({ data: responseData});
		}); 
    	//查询报表数据集SQL
    	ajaxTextByUrl("querySuperReportDsSql.do?isCheck=false",para,function(responseData){	
    		$("#sqlcontent").val(responseData);
    	});
    }
    
    //添加报表数据集
    var operation="insert";
    function myAdd(){
    	operation="insert";
    	var node = tree.getSelectedNodes()[0];
    	if (node!=null) { 
    		tree.cancelSelectedNode(node);
    	}
		$("#ds_code").ligerTextBox({ disabled: false });
        $("#ds_code").attr( "disabled", false );
        $("#ds_type").ligerComboBox({ disabled: false,cancelable: false });
		$("#ds_name").val("");
        $("#note").val("");
        $("#sqlcontent").val("");
		liger.get("state").setValue(0);
		liger.get("ds_type").setValue(4);
		liger.get("ds_class").setValue(0);
		$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
        $("#sort_code").attr( "disabled", true );
		$("#is_sys_text").text("自定义数据集");
    	$("#is_sys_text").css("color","blue");

		cGrid.set({ data: {}});
		pGrid.set({ data: {}});
		$("input[name='saveButton']").attr("disabled",false);
    	$("input[name='saveButton']").ligerButton({disabled: false});
    	liger.get("mod_code").setValue($("#mod_code_init").val());
    }
   
    
    //保存数据集数据集
    function mySave(){
    	if($("#ds_code").val()==""){
    		$.ligerDialog.error("数据集编码不能为空！");
    		return false;
    	}
    	if($("#ds_name").val()==""){
    		$.ligerDialog.error("数据集名称不能为空！");
    		return false;
    	}
    	if(liger.get("mod_code").getValue()==""){
    		$.ligerDialog.error("系统模块不能为空！");
    		return false;
    	}
    	if(liger.get("ds_type").getValue()==""){
    		$.ligerDialog.error("数据集类型不能为空！");
    		return false;
    	}
    	if(liger.get("mod_code").getValue()=="0102" && liger.get("ds_type").getValue()!="1"){
    		$.ligerDialog.error("工资系统只能保存函数！");
    		return false;
    	}
    	if($("#sort_code").val()==""){
    		$.ligerDialog.error("排序号不能为空！");
			return false;
    	}
    	
    	var r1 =/^[0-9]*[1-9][0-9]*$/;//正整数
    	var r2 =/^-[0-9]*[1-9][0-9]*$/;//负整数
    	if($("#sort_code").val()!="系统生成" && !r1.test($("#sort_code").val()) && !r2.test($("#sort_code").val())){
    		$.ligerDialog.error("排序号只能输入整数！");
			return false;
    	}

    	if($("#mod_code_init").val()!=liger.get("mod_code").getValue().substring(0,2)){
        	//不是当前模块不能保存
    		$.ligerDialog.error("只能保存当前系统模块的数据集！");
			return false;
        }
    	
    	if($("#ds_group").val()==""){
    		$("#ds_group").val("系统默认");
    	}
    	
    	var state=0;
    	if(liger.get("state").getValue()){
    		state=1;
      	}
    	var para={
    		ds_code:$("#ds_code").val(),
    		ds_name:$("#ds_name").val(),
    		mod_code:liger.get("mod_code").getValue(),
    		ds_type:liger.get("ds_type").getValue(),
    		ds_class:liger.get("ds_class").getValue(),
    		state:state,
    		sort_code:$("#sort_code").val(),
			ds_group:$("#ds_group").val(),
			note:$("#note").val(),
			is_sys: $("#is_sys").val(), 
			sqlcontent:$("#sqlcontent").val(),
			operation:operation,
			cGrid:JSON.stringify(cGrid.getData()),
			pGrid:JSON.stringify(pGrid.getData())
			
    	};
    	
       	ajaxJsonObjectByUrl("saveSuperReportDs.do",para,function(responseData){
       		if(responseData.state=="true"){
       			//sheet.setName($("#report_name").val());
       			var node = tree.getSelectedNodes()[0];
       			loadTree(node);
       		}
   		});
    	
    }
    
    //删除报表数据集
    function myDelete(){
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null || node.isParent == true){
    		$.ligerDialog.error("请选择数据集！");
    		return false;
    	}
    	
    	if(node.is_sys == 1){
    		$.ligerDialog.error("系统数据集不能删除！");
    		return false;
    	}
    	
    	$.ligerDialog.confirm('确定删除【'+node.name+'】吗?', function(yes) {
			if (yes) {
				var para={
		        		ds_code:node.id,
		        		ds_type:node.ds_type
		        };
				ajaxJsonObjectByUrl("deleteSuperReportDs.do",para,function(responseData){
	        		if(responseData.state=="true"){
	        			//sheet.setName($("#report_name").val());
	        			loadTree();
	        			myAdd();
	        		}
	    		});
			}
		});
        	
    	
    }
    
    //添加数据列
    function columnAdd(){
    	
    	parent.$.ligerDialog.open({url : 'hrp/cost/superReport/ds/paraSetPage.do?isCheck=false',
    		data:{}, height:300,width: 360, title:'数据集参数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.mySave(cGrid); },cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
    }
    
    //修改数据列
    function columnView(rownumber){

    	parent.$.ligerDialog.open({url : 'hrp/cost/superReport/ds/paraSetPage.do?isCheck=false',
    		data:{gridData: cGrid.getData(), rownumber: rownumber}, height:300,width: 360, title:'数据集参数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.myUpdate(cGrid,rownumber);},cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
    }
    
    //删除数据列
    function columnDel(){
    	var data = cGrid.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return;
		}
    	$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				cGrid.deleteSelectedRow();
			}
		});

    }
    
    //添加参数
    function paraAdd(){
    	
    	parent.$.ligerDialog.open({url : 'hrp/cost/superReport/ele/paraSetPage.do?isCheck=false',
    		data:{}, height:400,width: 500, title:'数据集参数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.mySave(pGrid); },cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
    }
    
    //修改参数
    function myView(rownumber){

    	parent.$.ligerDialog.open({url : 'hrp/cost/superReport/ele/paraSetPage.do?isCheck=false',
    		data:{gridData: pGrid.getData(),rownumber: rownumber}, height:400,width: 500, title:'数据集参数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.myUpdate(pGrid, 0,rownumber);},cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
    }
    
    //删除参数
    function paraDel(){
    	var data = pGrid.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return;
		}
    	$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				pGrid.deleteSelectedRow();
			}
		});

    }
	
    //载报表元素
	function initSql(){
		if($("#ds_code").val()==""){
			$.ligerDialog.error("数据集编码不能为空！");
			return false;
		}
		
		var paras = { 
			is_ele: 0, 
			ele_code: $("#ds_code").val(), 
			ele_type: liger.get("ds_type").getValue(), 
			path: 'WEB-INF\\classes\\oracle\\05other\\sql\\rep'
		}

		ajaxJsonObjectByUrl("../ele/initSuperReportProc.do", paras, function(responseData){
			if(responseData.state=="true"){
				    		
			}
		});
	}

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input id="mod_code_init" type="text"  value="${mod_code}" style="display:none"/>
	<input id="is_sys" type="text" style="display:none"/>
	<div class="l-layout" id="layout1"  >
		<div position="left" title="数据集">
		    <div  style="overflow:auto;" id="treeDiv">
		       	<ul class="ztree"  id="tree"></ul>
		    </div>
	    </div>
		
		<div position="center" id="centerReport" style="overflow: auto;">
			<div style="top:5px;left:5px;position:absolute;">
					<input type="button" value=" 添加（I）" accessKey="I" onclick="myAdd();"/>
					<input name="saveButton" type="button" value=" 删除（D）" accessKey="D" onclick="myDelete();"/>
					<input name="saveButton" type="button" value=" 保存（S）" accessKey="S" onclick="mySave();"/>
					<input type="button" value=" 载入内置SQL（Y）" accessKey="Y" onclick="initSql();"/>
			</div>
			<br/><br/><br/>
			
			<div id="tab1" style="width: 100%;border:0px solid #A3C0E8; "> 
	            <div tabid="item1"  title="数据集定义">
	                <form name="form1" method="post"  id="form1" >
						<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
						 	<tr>
						 	  <td align="right" class="l-table-edit-td"  style="padding-left:5px;"><b><font color="red">*</font></b>数据集类型：</td>
					           <td align="left" class="l-table-edit-td">
					         	   <select id="ds_type" name="ds_type">
				                		<option value="1">函数</option>
				                		<option value="2">存储过程</option>
				                		<option value="3">视图</option>
				                		<option value="4">自定义SQL</option>
			                		</select>
					           </td>
					            <td align="right" class="l-table-edit-td"  style="padding-left:100px;"><b><font color="red">*</font></b>数据集编码：</td>
					            <td align="left" class="l-table-edit-td">
					          	  <input name="ds_code" type="text" id="ds_code" ltype="text" validate="{required:true,maxlength:20}" title="以ZREP_开头命名"/>
					            </td>
					        </tr>
					        <tr>
					          <td align="right" class="l-table-edit-td"><b><font color="red">*</font></b>系统模块：</td>
					           <td align="left" class="l-table-edit-td">
					         	  <input name="mod_code" type="text" id="mod_code" ltype="text" />
					           </td>
					         	<td align="right" class="l-table-edit-td"><b><font color="red">*</font></b>数据集名称：</td>
					            <td align="left" class="l-table-edit-td">
					            	<input name="ds_name" type="text" id="ds_name" ltype="text" />
					            </td>
					        </tr>
					        <tr>
					        	<td align="right" class="l-table-edit-td"  style="padding-left:5px;"><b><font color="red">*</font></b>数据集类别：</td>
					           <td align="left" class="l-table-edit-td">
					         	   <select id="ds_class" name="ds_class">
				                		<option value="1">行数据集</option>
				                		<option value="2">列数据集</option>
			                		</select>
								</td>
								<td align="right" class="l-table-edit-td"><b><font color="red">*</font></b>排序号：</td>
								<td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text"  /></td>
							</tr>
							<tr>
					            <td align="right" class="l-table-edit-td" >分组：</td>
					            <td align="left" class="l-table-edit-td">
					            	<input name="ds_group" type="text" id="ds_group" ltype="text" />
					            </td>
							
					            <td align="right" class="l-table-edit-td"></td>
					            <td align="left" class="l-table-edit-td">
					                <input type="checkbox" id="state"/>停用&nbsp;&nbsp;&nbsp;&nbsp;
					                <span id="is_sys_text" ></span>
					            </td>
					        </tr>
						</table>
					</form>
					<div style="width: 40%; float: left;">
						<div id="cGrid"></div>
					</div>
					<div style="width: 60%; float: right;">
						<div id="pGrid"></div>
					</div>
	            </div>
        		<div id="item2" title="SQL语句">
        			<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:17px">
							<tr>
								<td align="left" class="l-table-edit-td">
									<textarea id="sqlcontent" class="l-textarea" ltype="text" name="textAreaWidth"></textarea>
								</td>
							</tr>
							<tr>
					            <td align="left" class="l-table-edit-td">
					            	<div style="color:#00F;">
					            		1、自定义SQL，where 条件后面的参数以@para1，@para2...命名。<br/>
		                				2、列表里面的参数必须与SQL里面参数的顺序保持一致。<br/>
		                				3、勾选参数列表，选择行号可以移动参数的顺序。<br/>
					            	</div>
					            </td>
					        </tr> 
					</table>
        		</div>
        	</div>
		    
		</div>
	</div>

</body>
</html>
