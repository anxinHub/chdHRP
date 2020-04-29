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
var grid=null;
var gridManager=null;
var navtab=null;
var tree;
var setting;

	$(function (){
    	$("#layout1").ligerLayout({ leftWidth: 210,allowLeftResize: true,
    		onLeftToggle: function (isColl){
            	//alert(isColl ? "收缩" : "显示");
				grid._onResize();
        	}
	    	,onEndResize: function(isColl) {
				grid._onResize();
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
    			
	    		if(tabid=="item1" && $("#maingrid").width()==100){
	    			setTimeout(function(){
	    				grid._onResize();
	    			},50);
	    			
	    		}
	    	}
    	});
        navtab = $("#tab1").ligerGetTabManager();
    	$("#treeDiv").css("height", $(window).height()-28);
    	$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
   	 	$("#sort_code").attr( "disabled", true );
   	 	$("#is_sys").text("自定义元素");
 		$("#is_sys").css("color","blue");
   	 	loadDict();
		$("form").ligerForm();
		$("[name='textAreaWidth']").css("width", $(window).width()-230);
		$("[name='textAreaWidth']").css("height", $(window).height()-180);
		$("#note").ligerTextBox({ width: 555 });
		$("#ele_type").ligerComboBox({ value:4,          
            onSelected: function (newvalue)
            {
            	 if(operation!="insert"){
            		 return;
            	 }
            	 if(newvalue==1){
    				 $("#ele_code").val("RF_");
    			 }else if(newvalue==2){
    				 $("#ele_code").val("RP_");
    			 }else if(newvalue==3){
    				 $("#ele_code").val("RV_");
    			 }else if(newvalue==4){
    				 $("#ele_code").val("RZ_");
    			 }
            }
        });

    	$(':button').ligerButton({width:120});
    	loadTree();
       	loadHead();
       	
    });

	function loadDict(){
		
		autocompleteAsync("#mod_code","../../../sys/queryModDictAdminPerm.do?isCheck=false&mod_code_where='0102','0601'","id","text",false,true,'',false,$("#mod_code_init").val());
		
		if($("#mod_code_init").val()=="01"){
			$("#mod_code").ligerComboBox({ cancelable:false });
		}else{
			$("#mod_code").ligerComboBox({ disabled: true,cancelable:false });
		}
	}
	
    function loadTree(selNode){
    	var para={
				//mod_code:$("#mod_code").val()
    	};
		ajaxJsonObjectByUrl("querySuperReportEleManager.do",para,function(responseData){	
			  tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
			  if(selNode==undefined || selNode.pId==null || selNode.id==null){
	    	  		return;
	    	  }
			  tree.selectNode(selNode,true);
		},false);
    }
    
    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '参数编码', name: 'para_code', align: 'left',width:80,
						 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:myView('"+rowindex+"')>"
				       					+""+rowdata.para_code+"</a>";
							}},
                     { display: '参数名称', name: 'para_name', align: 'left',width:100},
                     { display: '参数类型标识', name: 'para_type', align: 'left',minWidth:0,hide:true},
                     { display: '参数类型', name: 'para_type_name', align: 'left',width:100},
                     { display: '参数性质', name: 'para_tm', align: 'left',width:80},
                     { display: '字典编码', name: 'dict_code', align: 'left',width:80},
                     { display: '字典名称', name: 'dict_name', align: 'left',width:100},
                     { display: '是否停用标识', name: 'is_stop', align: 'left',minWidth:0,hide:true},
                     { display: '是否停用', name: 'is_stop_name', align: 'left',width:60},
                     { display: '参数信息', name: 'para_json', align: 'left',width:'40%'}
    				],dataAction : 'local',dataType : 'local',usePager : true,enabledSort:false,
    				url : '',parms:{},
    				width : '100%',height : $(window).height()-205,checkbox : true,usePager : false,
    				rownumbers : true,selectRowButtonOnly : true ,heightDiff: 0,
    				colDraggable:true,rowDraggable:true,title: '选中行，在行号处按住鼠标左键可以上下拖动改变行的顺序',
    				toolbar: { items: [
    				         { text: '添加参数', id:'add', click: paraAdd, icon:'add' },
    				         { line:true },
    				         { text: '删除参数', id:'del', click: paraDel,icon:'delete'}
    				  ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function myClick(){
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null || node.ele_type==null){
    		return;
    	}
    	operation="update";
    	$("#ele_code").ligerTextBox({ disabled: true });
        $("#ele_code").attr( "disabled", true );
   		$("#sort_code").ligerTextBox({ disabled: false });
        $("#sort_code").attr( "disabled", false );
        $("#ele_type").ligerComboBox({ disabled: true,cancelable: false });
    	$("#ele_code").val(node.id);
    	$("#ele_name").val(node.name);
    	$("#ele_group").val(node.ele_group);
    	$("#sort_code").val(node.sort_code);
    	$("#note").val(node.note);
    	liger.get("mod_code").setValue(node.mod_code);
    	if($("#mod_code_init").val()!=node.mod_code.substring(0,2)){
        	//不是当前模块不能保存
        	$("input[name='saveButton']").attr("disabled",true);
        	$("input[name='saveButton']").ligerButton({disabled: true});
        }else{
        	$("input[name='saveButton']").attr("disabled",false);
        	$("input[name='saveButton']").ligerButton({disabled: false});
        }
    	
    	liger.get("ele_type").setValue(node.ele_type);
    	if(node.is_stop==1){
        	liger.get("is_stop").setValue(true);	
        }else{
        	liger.get("is_stop").setValue(false);
        }
    	
    	if(node.is_sys==1){
    		$("#is_sys").text("系统元素");
    		$("#is_sys").css("color","red");
        }else{
        	$("#is_sys").text("自定义元素");
        	$("#is_sys").css("color","blue");
        }
    	
    	//系统函数
    	if(node.ele_type==5){
    		liger.get("ele_type").setValue(node.ele_type);
    		liger.get("ele_type").setText("系统函数");
    	}
    	
    	
    	var para={
    			ele_code:node.id
    	};
    	//查询报表元素参数
    	ajaxJsonObjectByUrl("../querySuperReportParaByEle.do?isCheck=false",para,function(responseData){
    		grid.set({ data: responseData});
		});
    	
    	//查询报表元素SQL
    	ajaxTextByUrl("querySuperReportSourceAndViews.do?isCheck=false",{ele_code:node.id,ele_type:node.ele_type},function(responseData){	
    		$("#ele_sql").val(responseData);
    	});
    	
    	
    }
    
    //添加报表元素
    var operation="insert";
    function myAdd(){
    	operation="insert";
    	var node = tree.getSelectedNodes()[0];
    	if (node!=null) { 
    		tree.cancelSelectedNode(node);
    	}
		$("#ele_code").ligerTextBox({ disabled: false });
        $("#ele_code").attr( "disabled", false );
        $("#ele_type").ligerComboBox({ disabled: false,cancelable: false });
		$("#ele_name").val("");
		$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
        $("#sort_code").attr( "disabled", true );
        $("#ele_sql").val("");
        $("#note").val("");
		liger.get("is_stop").setValue(0);
		liger.get("ele_type").setValue(4);
		$("#is_sys").text("自定义元素");
    	$("#is_sys").css("color","blue");
		grid.set({ data: {}});
		$("input[name='saveButton']").attr("disabled",false);
    	$("input[name='saveButton']").ligerButton({disabled: false});
    	liger.get("mod_code").setValue($("#mod_code_init").val());
    }
   
    
    //保存报表元素
    function mySave(){
    	if($("#is_sys").text()=="系统元素"){
    		$.ligerDialog.error("系统元素不允许修改！");
    		return false;
    	}
    	if($("#ele_code").val()==""){
    		$.ligerDialog.error("元素编码不能为空！");
    		return false;
    	}
    	if($("#ele_name").val()==""){
    		$.ligerDialog.error("元素名称不能为空！");
    		return false;
    	}
    	if(liger.get("mod_code").getValue()==""){
    		$.ligerDialog.error("系统模块不能为空！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()==""){
    		$.ligerDialog.error("元素类型不能为空！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="1" && $("#ele_code").val().substring(0,3)!="RF_"){
    		$.ligerDialog.error("函数，元素编码必须以【RF_】开头命名！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="2" && $("#ele_code").val().substring(0,3)!="RP_"){
    		$.ligerDialog.error("存储过程，元素编码必须以【RP_】开头命名！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="3" && $("#ele_code").val().substring(0,3)!="RV_"){
    		$.ligerDialog.error("视图，元素编码必须以【RV_】开头命名！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="4" && $("#ele_code").val().substring(0,3)!="RZ_"){
    		$.ligerDialog.error("自定义SQL，元素编码必须以【RZ_】开头命名！");
    		return false;
    	}
    	if($("#sort_code").val()==""){
    		$.ligerDialog.error("排序号不能为空！");
			return false;
    	}
    	if($("#ele_sql").val()==""){
    		$.ligerDialog.error("元素取值不能为空！");
    		return false;
    	}
    	if(liger.get("mod_code").getValue()=="0102" && liger.get("ele_type").getValue()!="1"){
    		$.ligerDialog.error("工资系统只能保存函数！");
    		return false;
    	}
    	
    	if($("#mod_code_init").val()!=liger.get("mod_code").getValue().substring(0,2)){
        	//不是当前模块不能保存
    		$.ligerDialog.error("只能保存当前系统模块的元素！");
			return false;
        }
    	
    	var r1 =/^[0-9]*[1-9][0-9]*$/;//正整数
    	var r2 =/^-[0-9]*[1-9][0-9]*$/;//负整数
    	if($("#sort_code").val()!="系统生成" && !r1.test($("#sort_code").val()) && !r2.test($("#sort_code").val())){
    		$.ligerDialog.error("排序号只能输入整数！");
			return false;
    	}
    	
    	
    	var isStop=0;
    	if(liger.get("is_stop").getValue()){
    		isStop=1;
      	}
    	if($("#ele_group").val()==""){
    		$("#ele_group").val("系统默认");
    	}
    	
    	var para={
    		operation:operation,
    		ele_code:$("#ele_code").val(),
    		ele_name:$("#ele_name").val(),
    		mod_code:liger.get("mod_code").getValue(),
    		ele_type:liger.get("ele_type").getValue(),
    		ele_group:$("#ele_group").val(),
    		sort_code:$("#sort_code").val(),
			is_stop:isStop,
			ele_sql:$("#ele_sql").val(),
			note:$("#note").val(),
			grid:JSON.stringify(grid.getData())
			
    	};
    	
       	ajaxJsonObjectByUrl("saveSuperReportEle.do",para,function(responseData){
       		if(responseData.state=="true"){
       			//sheet.setName($("#report_name").val());
       			var node = tree.getSelectedNodes()[0];
       			loadTree(node);
       		}
   		});
    	
    }
    
    //删除报表元素
    function myDelete(){
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null || node.ele_type==null){
    		$.ligerDialog.error("请选择报表元素！");
    		return false;
    	}
    	
    	if($("#is_sys").text()=="系统元素"){
    		$.ligerDialog.error("系统元素不允许删除！");
    		return false;
    	}
    	
    	
    	$.ligerDialog.confirm('确定删除【'+node.name+'】吗?', function(yes) {
			if (yes) {
				var para={
		        		ele_code:node.id,
		        		ele_type:node.ele_type
		        };
				ajaxJsonObjectByUrl("deleteSuperReportEle.do",para,function(responseData){
	        		if(responseData.state=="true"){
	        			//sheet.setName($("#report_name").val());
	        			loadTree();
	        			myAdd();
	        		}
	    		});
			}
		});
        	
    	
    }
    
    //添加参数
    function paraAdd(){
    	
    	if($("#is_sys").text()=="系统元素"){
    		$.ligerDialog.error("系统元素不允许添加！");
    		return false;
    	}
    	parent.$.ligerDialog.open({url : 'hrp/acc/superReport/ele/paraSetPage.do?isCheck=false',
    		data:{}, height:400,width: 500, title:'元素参数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.mySave(grid); },cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
    }
    
    //修改参数
    function myView(rownumber){

    	parent.$.ligerDialog.open({url : 'hrp/acc/superReport/ele/paraSetPage.do?isCheck=false',
    		data:{gridData:grid.getData(),rownumber:rownumber}, height:400,width: 500, title:'元素参数',modal:true,showToggle:false,initShowMax:false,showMax:true,showMin: false,isResize:false,
    		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.myUpdate(grid,$("#is_sys").text(),rownumber);},cls:'l-dialog-btn-highlight' },
    		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]});
    }
    
    //删除参数
    function paraDel(){
    	if($("#is_sys").text()=="系统元素"){
    		$.ligerDialog.error("系统元素不允许删除！");
    		return false;
    	}
    	var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return;
		}
    	$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				grid.deleteSelectedRow();
			}
		});

    }
    //载报表元素
    function initSql(){
    	if(liger.get("ele_type").getValue()=="4"){
    		$.ligerDialog.error("自定义SQL不需要加载！");
    		return false;
    	}
    	
    	if($("#ele_code").val()==""){
    		$.ligerDialog.error("元素编码不能为空！");
    		return false;
    	}
    	
    	/* if($("#ele_sql").val()==""){
    		$.ligerDialog.error("SQL不能为空！");
    		return false;
    	} */
    	
    	ajaxJsonObjectByUrl("initSuperReportProc.do",{ele_code: $("#ele_code").val()},function(responseData){
    		if(responseData.state=="true"){
    		
    		}
		});
    }
    
    //生成取数SQL
    function createSql(){
    	if($("#is_sys").text()=="系统元素"){
    		$.ligerDialog.error("系统元素不允许修改！");
    		return false;
    	}
    	if($("#ele_code").val()==""){
    		$.ligerDialog.error("元素编码不能为空！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()==""){
    		$.ligerDialog.error("元素类型不能为空！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="1" && $("#ele_code").val().substring(0,3)!="RF_"){
    		$.ligerDialog.error("函数，元素编码必须以【RF_】开头命名！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="2" && $("#ele_code").val().substring(0,3)!="RP_"){
    		$.ligerDialog.error("存储过程，元素编码必须以【RP_】开头命名！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="3" && $("#ele_code").val().substring(0,3)!="RV_"){
    		$.ligerDialog.error("视图，元素编码必须以【RV_】开头命名！");
    		return false;
    	}
    	if(liger.get("ele_type").getValue()=="4"){
    		$.ligerDialog.error("自定义SQL，不能生成取值SQL！");
    		return false;
    	}
    	if(navtab.getSelectedTabItemID()!="item2"){
    		$.ligerDialog.error("请选择【元素取值】页签后再生成取值SQL！");
    		return false;
    	}
    	
    	var isReplace=false;
    	if($("#ele_sql").val()!=""){
    		isReplace=confirm("确定要替换吗?");
		}else{
			isReplace=true;
		}
    	
    	if(!isReplace){
			return false;
		}
    	
    	//拼装参数
    	var paraStr="";
    	var gridData=grid.getData();
    	$.each(gridData,function(i,obj){
    		if(liger.get("ele_type").getValue()==3){
				//视图
    			paraStr=paraStr+"  a."+obj.para_code+"=@para"+(i+1)+" and";
			}else{
				
				//函数、存储过程
	    		if(obj.para_json!=null && obj.para_json!=""){
	    			var json=JSON.parse(obj.para_json);
	    			if(json.data_type=="1"){
	        			//字符型
	        			paraStr=paraStr+"\n  p_"+obj.para_code+" in varchar2,";
	        		}else if(json.data_type=="2"){
	        			//数字型
	        			paraStr=paraStr+"\n  p_"+obj.para_code+" in number,";
	        		}
	    			
	    		}
			}
    	});
    	
    	var eleSqlTexArea="";
    	if(liger.get("ele_type").getValue()==1){
			//函数
			eleSqlTexArea="create or replace function "+$("#ele_code").val()+"(";
			if(paraStr!=""){
				paraStr=paraStr.substring(0,paraStr.length-1);
				eleSqlTexArea=eleSqlTexArea+paraStr+"\n";
			}else{
				eleSqlTexArea=eleSqlTexArea+"\n";
			}
			eleSqlTexArea=eleSqlTexArea+")\n";
			eleSqlTexArea=eleSqlTexArea+"   return number --返回类型\n";
			eleSqlTexArea=eleSqlTexArea+"is \n";
			eleSqlTexArea=eleSqlTexArea+"   res_number number; --定义变量\n";
			eleSqlTexArea=eleSqlTexArea+"begin \n";
			eleSqlTexArea=eleSqlTexArea+"   \n\r";
			eleSqlTexArea=eleSqlTexArea+"   \n\r";
			eleSqlTexArea=eleSqlTexArea+"   \n\r";
			eleSqlTexArea=eleSqlTexArea+"   return res_number;\n";
			eleSqlTexArea=eleSqlTexArea+"end;";
			
		}else if(liger.get("ele_type").getValue()==2){
			//存储过程
			eleSqlTexArea="create or replace procedure "+$("#ele_code").val()+"(";
			if(paraStr!=""){
				eleSqlTexArea=eleSqlTexArea+paraStr+"\n";
			}else{
				eleSqlTexArea=eleSqlTexArea+"\n";
			}
			eleSqlTexArea=eleSqlTexArea+"   res_number out number --返回变量和类型\n";
			eleSqlTexArea=eleSqlTexArea+")\n";
			eleSqlTexArea=eleSqlTexArea+"is \n";
			eleSqlTexArea=eleSqlTexArea+"   v_number number; --定义变量\n";
			eleSqlTexArea=eleSqlTexArea+"begin \n";
			eleSqlTexArea=eleSqlTexArea+"   \n\r";
			eleSqlTexArea=eleSqlTexArea+"   \n\r";
			eleSqlTexArea=eleSqlTexArea+"   \n\r";
			eleSqlTexArea=eleSqlTexArea+"   res_number:=100.12;--返回值\n";
			eleSqlTexArea=eleSqlTexArea+"end "+$("#ele_code").val()+";";
			
		}else if(liger.get("ele_type").getValue()==3){
			//视图
			eleSqlTexArea="create or replace view "+$("#ele_code").val()+" as \n";
			eleSqlTexArea=eleSqlTexArea+"   select \n";
			eleSqlTexArea=eleSqlTexArea+"       * \n";
			eleSqlTexArea=eleSqlTexArea+"   from table a \n";
			/* if(paraStr!=""){
				paraStr=paraStr.substring(0,paraStr.length-3);
				eleSqlTexArea=eleSqlTexArea+"   where "+paraStr+" \n";
			} */
		} 
    	
    	if(eleSqlTexArea!=""){
    		$("#ele_sql").val(eleSqlTexArea);	
    	}
    	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input id="mod_code_init" type="text"  value="${mod_code}" style="display:none"/>
	<div class="l-layout" id="layout1"  >
		<div position="left" title="报表元素">
		    <div  style="overflow:auto;" id="treeDiv">
		       	<ul class="ztree"  id="tree"></ul>
		    </div>
	    </div>
		
		<div position="center" id="centerReport" style="overflow: auto;">
			<div style="top:5px;left:5px;position:absolute;">
					<input type="button" value=" 添加（I）" accessKey="I" onclick="myAdd();"/>
					<input name="saveButton" type="button" value=" 删除（D）" accessKey="D" onclick="myDelete();"/>
					<input name="saveButton" type="button" value=" 保存（S）" accessKey="S" onclick="mySave();"/>
					<input type="button" value=" 生成SQL（Q）" accessKey="Q" onclick="createSql();"/>
					<input type="button" value=" 载入内置SQL（Y）" accessKey="Y" onclick="initSql();"/>
			</div>
			<br/><br/><br/>
			
			<div id="tab1" style="width: 100%;border:0px solid #A3C0E8; "> 
	            <div tabid="item1"  title="元素定义">
	                <form name="form1" method="post"  id="form1" >
						<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
						 	<tr>
						 	  <td align="right" class="l-table-edit-td"  style="padding-left:5px;"><b><font color="red">*</font></b>元素类型：</td>
					           <td align="left" class="l-table-edit-td">
					         	   <select id="ele_type" name="ele_type">
				                		<option value="1">函数</option>
				                		<option value="2">存储过程</option>
				                		<option value="3">视图</option>
				                		<option value="4">自定义SQL</option>
			                		</select>
					           </td>
					            <td align="right" class="l-table-edit-td"  style="padding-left:100px;"><b><font color="red">*</font></b>元素编码：</td>
					            <td align="left" class="l-table-edit-td">
					          	  <input name="ele_code" type="text" id="ele_code" ltype="text" validate="{required:true,maxlength:20}" title="以ZREP_开头命名"/>
					            </td>
					        </tr>
					        <tr>
					          <td align="right" class="l-table-edit-td"><b><font color="red">*</font></b>系统模块：</td>
					           <td align="left" class="l-table-edit-td">
					         	  <input name="mod_code" type="text" id="mod_code" ltype="text" />
					           </td>
					         	<td align="right" class="l-table-edit-td"><b><font color="red">*</font></b>元素名称：</td>
					            <td align="left" class="l-table-edit-td">
					            	<input name="ele_name" type="text" id="ele_name" ltype="text" />
					            </td>
					        </tr>
					         <tr>
					            <td align="right" class="l-table-edit-td" >元素分组：</td>
					            <td align="left" class="l-table-edit-td">
					            	<input name="ele_group" type="text" id="ele_group" ltype="text" />
					            </td>
					            <td align="right" class="l-table-edit-td"><b><font color="red">*</font></b>排序号：</td>
					            <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text"  /></td>
					        </tr>
					        <tr>
					            <td align="right" class="l-table-edit-td">说明：</td>
					            <td align="left" class="l-table-edit-td" colspan="3">
					            	<input name="note" type="text" id="note" ltype="text" />
					            </td>
					        </tr>
					        <tr>
					            <td align="right" class="l-table-edit-td"></td>
					            <td align="left" class="l-table-edit-td">
					                <input type="checkbox" id="is_stop"/>停用&nbsp;&nbsp;&nbsp;&nbsp;
					                <span id="is_sys" ></span>
					            </td>
					        </tr>
						</table>
					</form>
					<div id="maingrid"></div>
	            </div>
	            
	            
	            <div tabid="item2" title="元素取值">
	              	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:17px">
							<tr>
								<td align="left" class="l-table-edit-td">
									<textarea id="ele_sql" class="l-textarea" ltype="text" name="textAreaWidth"></textarea>
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
