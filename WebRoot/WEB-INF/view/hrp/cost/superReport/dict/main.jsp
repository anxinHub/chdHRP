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


<style type="text/css"> 
   .l-page-top{ height:40px; background:#f8f8f8; margin-bottom:3px;}
</style>

    
<script type="text/javascript">
var grid;
var gridManager;

    $(function (){
		
		$("#layout1").ligerLayout({ leftWidth: 560,allowLeftResize: true,
    		onLeftToggle: function (isColl){
            	//alert(isColl ? "收缩" : "显示");
				grid._onResize();
        	}	
		});
		loadDict();
		$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
        $("#sort_code").attr( "disabled", true );
		$("[name='textAreaWidth']").css("width", $(window).width()-800);
		$("form").ligerForm();
    	$(':button').ligerButton({width:100});
    	loadHead();
    });
    
    function loadDict(){
    	autocompleteAsync("#mod_code_query","../../../sys/queryModDictAdminPerm.do?isCheck=false","id","text",false,true,'',false,$("#mod_code_init").val());
    	autocomplete("#mod_code","../../../sys/queryModDictAdminPerm.do?isCheck=false","id","text",false,true,'',false,$("#mod_code_init").val());
    	$("#mod_code").ligerComboBox({ disabled: true,cancelable:false });
    }
      
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '字典编码', name: 'dict_code', align: 'left',width:80,
						 render : function(rowdata, rowindex,value) {
									return "<a href=javascript:myView('"+rowdata.dict_code+"','"+rowdata.dict_name+"','"+rowdata.mod_code+"','"+rowdata.is_sys+"','"+rowdata.is_stop+"')>"
				       					+""+rowdata.dict_code+"</a>";
							}},
                     { display: '字典名称', name: 'dict_name', align: 'left',width:150},
					 { display: '系统模块', name: 'mod_name', align: 'left',width:100},
					 { display: '系统模块', name: 'mod_code', align: 'left',hide:true,width:0},
					 { display: '是否系统字典', name: 'is_sys_name', align: 'left',width:100},
					 { display: '是否系统字典', name: 'is_sys', align: 'left',hide:true,width:0},
					 { display: '是否停用', name: 'is_stop_name', align: 'left',width:80},
					 { display: '是否停用', name: 'is_stop', align: 'left',hide:true,width:0}
    				],dataAction : 'server',dataType : 'server',usePager : true,
    				url : 'querySuperReportDictList.do',parms:{mod_code:liger.get("mod_code_query").getValue()},
    				width : '600px',height : '100%',checkbox : true,
    				rownumbers : true,delayLoad:false,selectRowButtonOnly : true ,heightDiff: 0
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //查询
    function  myQuery(){
    	grid.options.parms=[];
    	grid.options.newPage = 1;
    	grid.options.parms.push({name:'mod_code',value:liger.get("mod_code_query").getValue()});
		grid.loadData(grid.where);
		
 	}
    
    //点击
    var is_sys=0;
    function myView(dictCode,dictName,modCode,isSys,isStop){
    	operation="update";
    	is_sys=isSys;
    	$("#dict_code").val(dictCode);
    	$("#dict_code").ligerTextBox({ disabled: true });
        $("#dict_code").attr( "disabled", true );
    	$("#dict_name").val(dictName);
   		$("#sort_code").ligerTextBox({ disabled: false });
        $("#sort_code").attr( "disabled", false );
        liger.get("mod_code").setValue(modCode);
        if($("#mod_code_init").val()!=modCode){
        	//不是当前模块不能保存
        	$("input[name='saveButton']").attr("disabled",true);
        	$("input[name='saveButton']").ligerButton({disabled: true});
        }else{
        	$("input[name='saveButton']").attr("disabled",false);
        	$("input[name='saveButton']").ligerButton({disabled: false});
        }
        
        if(isStop==1){
        	liger.get("is_stop").setValue(true);	
        }else{
        	liger.get("is_stop").setValue(false);
        }
        
        ajaxJsonObjectByUrl("querySuperReportDictByCode.do?isCheck=false", {dict_code:dictCode}, function(responseData) {
        	$("#sort_code").val(responseData.sort_code);
        	$("#dict_sql").val(responseData.dict_sql);
        	$("#dict_check_sql").val(responseData.dict_check_sql=="null"?"":responseData.dict_check_sql);
        	$("#note").val(responseData.note=="null"?"":responseData.note);
		});
        
    }

    //删除
    function myDelete(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			var isSysSel=0;
			$(data).each(function(i,obj) {
				paramVo.push(this.dict_code);
				if(isSysSel==0)isSysSel=this.is_sys;
			});
			
			if(isSysSel==1){
	    		$.ligerDialog.error("系统字典不能删除！");
	    		return;
	    	}
			
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteBatchSuperReportDict.do", {paramVo :paramVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							myAdd();
							myQuery();
						}
					});
				}
			});
		}
    }
    
    //保存
    var operation="insert";
	function mySave(){
		if(is_sys==1){
    		$.ligerDialog.error("系统字典不能修改！");
    		return;
    	}
		
		if($("#dict_code").val()==""){
    		$.ligerDialog.error("字典编码不能为空！");
			return false;
    	}
    	if($("#dict_name").val()==""){
    		$.ligerDialog.error("字典名称不能为空！");
			return false;
    	}
    	if(liger.get("mod_code").getValue()==""){
    		$.ligerDialog.error("系统模块不能为空！");
			return false;
    	}
    	if($("#sort_code").val()==""){
    		$.ligerDialog.error("排序号不能为空！");
			return false;
    	}
    	if($("#dict_sql").val()==""){
    		$.ligerDialog.error("字典SQL不能为空！");
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
    
    	var para={
    			operation:operation,
    			dict_code:$("#dict_code").val(),
    			dict_name:$("#dict_name").val(),
    			sort_code:$("#sort_code").val(),
    			mod_code:liger.get("mod_code").getValue(),
    			dict_sql:$("#dict_sql").val(),
    			dict_check_sql:$("#dict_check_sql").val(),
    			note:$("#note").val(),
    			is_stop:isStop
    			};
    	ajaxJsonObjectByUrl("saveSuperReportDict.do",para,function(responseData){	
    		if(responseData.state=="true"){
    			myQuery();
    		}
		});
    }
	
	//添加
	function myAdd(){
		operation="insert";
		is_sys=0;
		$("#dict_code").val("");
		$("#dict_code").ligerTextBox({ disabled: false });
        $("#dict_code").attr( "disabled", false );
		$("#dict_name").val("");
		$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
        $("#sort_code").attr( "disabled", true );
        $("#dict_sql").val("");
        $("#dict_check_sql").val("");
        $("#note").val("");
		liger.get("is_stop").setValue(0);
		liger.get("mod_code").setValue($("#mod_code_init").val());
		$("input[name='saveButton']").attr("disabled",false);
    	$("input[name='saveButton']").ligerButton({disabled: false});
	}

	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
<input id="mod_code_init" type="text"  value="${mod_code}" style="display:none"/>
	<div class="l-page-top">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" id="acc_report_tab">
			<tr>
			     <td align="right" class="l-table-edit-td"  style="padding-left:20px;" id="acc_year_td">系统模块：</td>
			     <td align="left" class="l-table-edit-td">
			     <input type="text" id="mod_code_query" ltype="text" />
				</td>
			</tr>
		</table>
		<div style="top:10px;right:5px;position:absolute;">
				<input type="button" value=" 查询（Q）" accessKey="Q" onclick="myQuery();"/>
				<input type="button" value=" 添加（I）" accessKey="I" onclick="myAdd();"/>
				<input name="saveButton" type="button" value=" 删除（D）" accessKey="D" onclick="myDelete();"/>
				<input name="saveButton" type="button" value=" 保存（S）" accessKey="S" onclick="mySave();"/>
		</div>
	</div>

	<div class="l-layout" id="layout1"  >
		<div position="left" title="报表字典">
		     <div id="maingrid"></div>
	    </div>
		
		<div position="center" id="centerReport">
		    <form name="form1" method="post"  id="form1" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
			 	<tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:5px;"><b><font color="red">*</font></b>字典编码：</td>
		            <td align="left" class="l-table-edit-td">
		          	  <input name="dict_code" type="text" id="dict_code" ltype="text" validate="{required:true,maxlength:20}" />
		            </td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>字典名称：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="dict_name" type="text" id="dict_name" ltype="text" />
		            </td>
		        </tr> 
		       
		        <tr>
		           <td align="right" class="l-table-edit-td"  style="padding-left:5px;"><b><font color="red">*</font></b>系统模块：</td>
		           <td align="left" class="l-table-edit-td">
		         	  <input name="mod_code" type="text" id="mod_code" ltype="text" />
		           </td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>排序号：</td>
		            <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text"  /></td>
		        </tr>
		        </table>
		        
		        <table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:5px;"><b><font color="red">*</font></b>查询SQL：</td>
					<td align="left" class="l-table-edit-td">
						<textarea id="dict_sql" rows="14" class="l-textarea" ltype="text" name="textAreaWidth" ></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:5px;">检索条件：</td>
					<td align="left" class="l-table-edit-td">
						<textarea id="dict_check_sql" rows="2" class="l-textarea" ltype="text" name="textAreaWidth"></textarea>
					</td>
				</tr>
				<tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">说明：</td>
		            <td align="left" class="l-table-edit-td">
		                <textarea id="note" rows="2" class="l-textarea" name="textAreaWidth"></textarea>
		            </td>
		        </tr>
		        <tr>
		          	<td align="right" class="l-table-edit-td"  style="padding-left:5px;"></td>
		            <td align="left" class="l-table-edit-td">
		                <input type="checkbox" id="is_stop"/>停用
		            </td>
		        </tr>
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
		            <td align="left" class="l-table-edit-td">
		            	<div style="color:#00F;font-size:15px">
		            		系统变量说明：<br/>
		                	本集团：\#{group_id}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本医院：\#{hos_id}<br/>
		                	本账套：\#{copy_code}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本年度：\#{acc_year}<br/>
		                	当前用户ID：\#{user_id}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前用户编码：\#{user_code}<br/>
		                	检索下拉框，需要在检索条件里面加SQL，条件变量：\#{key}<br/>
		                	<span style="color:red;">确保输入的SQL能执行通过。</span>
		            	</div>
		            </td>
		        </tr> 
			</table>
		    </form>
		</div>
		
	</div>
	
</body>
</html>
