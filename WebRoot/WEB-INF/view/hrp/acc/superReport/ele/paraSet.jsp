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


<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

	$(function (){
    	
    	loadDict();
    	$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
        $("#sort_code").attr( "disabled", true );
        $("#para_width").ligerSpinner({ height: 28, type: 'int',value:170 });
		$("form").ligerForm();
		loadData();
    });

	function loadDict(){
		autocompleteAsync("#dict_code","../../querySuperReportDict.do?isCheck=false","id","text",true,true,'',false,'');
	}
	
	//修改页面加载数据
	function loadData(){
		
		var rownumber=dialog.get('data').rownumber;
		if(rownumber==undefined){
			return;
		}
		var gridData=dialog.get('data').gridData;
		$.each(gridData,function(i,obj){
    		if(i==rownumber){
    			$("#para_code").val(obj.para_code);
    			$("#para_name").val(obj.para_name);
    			liger.get("para_type").setValue(obj.para_type);
    			liger.get("dict_code").setValue(obj.dict_code);
    			if(obj.is_stop!=null && obj.is_stop=="1"){
					liger.get("is_stop").setValue(true);
				}else{
					liger.get("is_stop").setValue(false);
				}
    			if(obj.para_json!=null && obj.para_json!=""){
    				var json=JSON.parse(obj.para_json);
    				$("#para_width").val(json.width==null?"":json.width);
    				$("#para_where").val(json.para==null?"":json.para);
    				liger.get("data_type").setValue(json.data_type);
    				
    				if(json.required!=null && json.required=="true"){
    					liger.get("is_required").setValue(true);
    				}else{
    					liger.get("is_required").setValue(false);
    				}
    				
    			}
    			return false;
    		}
    	});
	}
    
    //添加
    function mySave(grid){
    	
    	if($("#para_code").val()==""){
    		$.ligerDialog.error("参数编码不能为空！");
			return false;
    	}
    	if($("#para_name").val()==""){
    		$.ligerDialog.error("参数名称不能为空！");
			return false;
    	}
    	if(liger.get("para_type").getValue()==""){
    		$.ligerDialog.error("参数类型不能为空！");
			return false;
    	}
    	if(liger.get("data_type").getValue()==""){
    		$.ligerDialog.error("数据类型不能为空！");
			return false;
    	}
    	if((liger.get("para_type").getValue()=="3" || liger.get("para_type").getValue()=="4") && $("#dict_code").val()==""){
    		$.ligerDialog.error("下拉框字典不能为空！");
			return false;
    	}
    	
    	var isValidate=true;
    	$.each(grid.getData(),function(i,obj){
    		if(obj.para_code==$("#para_code").val()){
    			isValidate=false;
    			return false;
    		}
    	});
    	if(!isValidate){
    		$.ligerDialog.error("参数编码在列表里面已经存在了！");
    		return false;
    	}
    	
    	grid.addRows(getPataJson());
    	$("#para_code").val("");
    	$("#para_name").val("");
    	liger.get("dict_code").setValue("");
    	$("#para_where").val("");
    	$.ligerDialog.success("已添加到列表！");
    }
   
    //修改
    function myUpdate(grid,isSys,rownumber){
    	if(isSys=="系统元素"){
    		$.ligerDialog.error("系统元素不允许修改！");
    		return false;
    	}
    	
    	if($("#para_code").val()==""){
    		$.ligerDialog.error("参数编码不能为空！");
			return false;
    	}
    	if($("#para_name").val()==""){
    		$.ligerDialog.error("参数名称不能为空！");
			return false;
    	}
    	if(liger.get("para_type").getValue()==""){
    		$.ligerDialog.error("参数类型不能为空！");
			return false;
    	}
    	if(liger.get("data_type").getValue()==""){
    		$.ligerDialog.error("数据类型不能为空！");
			return false;
    	}
    	if((liger.get("para_type").getValue()=="3" || liger.get("para_type").getValue()=="4") && $("#dict_code").val()==""){
    		$.ligerDialog.error("下拉框字典不能为空！");
			return false;
    	}
    	
    	var isValidate=true;
    	$.each(grid.getData(),function(i,obj){
    		
    		if(i!=rownumber && obj.para_code==$("#para_code").val()){
    			isValidate=false;
    			return false;
    		}
    	});
    	
    	if(!isValidate){
    		$.ligerDialog.error("参数编码在列表里面已经存在了！");
    		return false;
    	}
    	
    	
    	grid.updateRow(rownumber,getPataJson());
    	$.ligerDialog.success("已更新到列表！");
    	//tipDlg("修改成功！");

    }
    
    //拼装参数字符串，返回json对象
    function getPataJson(){
    	//拼装列表数据其他字段
    	var dictText="";
    	if(liger.get("dict_code").getText()!=""){
    		dictText=liger.get("dict_code").getText().split(" ")[1];
    	}
    	var paraStr="\"para_code\":\""+$("#para_code").val()+"\",\"para_name\":\""+$("#para_name").val()+"\"";
    	paraStr=paraStr+",\"para_type\":\""+liger.get("para_type").getValue()+"\",\"para_type_name\":\""+liger.get("para_type").getText()+"\"";
    	paraStr=paraStr+",\"dict_code\":\""+liger.get("dict_code").getValue()+"\",\"dict_name\":\""+dictText+"\"";
    	var isStop=0;
    	var isStopName="否";
    	if(liger.get("is_stop").getValue()){
    		isStop=1;
    		isStopName="是";
    	}
    	paraStr=paraStr+",\"is_stop\":\""+isStop+"\",\"is_stop_name\":\""+isStopName+"\"";
    	var paraJson=JSON.parse("{"+paraStr+"}");
    	
    	//拼装para_json字段
    	var info="\"required\":\""+liger.get("is_required").getValue()+"\"";
    	info=info+",\"data_type\":\""+liger.get("data_type").getValue()+"\"";
    	if($("#para_width").val()!=""){
    		info=info+",\"width\":\""+$("#para_width").val()+"\"";
    	}
    	if($("#para_where").val()!=""){
    		info=info+",\"para\":\""+$("#para_where").val()+"\"";
    	}
    	
    	paraJson["para_json"]="{"+info+"}";
    	return paraJson;
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
	 	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>参数编码：</td>
            <td align="left" class="l-table-edit-td">
          	  <input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>参数名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="para_name" type="text" id="para_name" ltype="text" />
            </td>
        </tr>
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>参数类型：</td>
           <td align="left" class="l-table-edit-td">
         	   <select id="para_type" name="para_type">
        		<option value="1">文本框</option>
        		<option value="2">日期框</option>
        		<option value="3">下拉框</option>
        		<option value="4">检索下拉框</option>
        		<option value="5">复选框</option>
        		<option value="6">其他</option>
			</select>
           </td>
        </tr>
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>数据类型：</td>
           <td align="left" class="l-table-edit-td">
         	   <select id="data_type" name="data_type">
        		<option value="1">字符型</option>
        		<option value="2">数字型</option>
			</select>
           </td>
        </tr>
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>参数性质：</td>
           <td align="left" class="l-table-edit-td">
         	   <select id="para_tm" name="data_type">
        		<option value="1">模版参数</option>
        		<option value="2">报表参数</option>
			</select>
           </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">显示宽度：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="para_width" type="text" id="para_width" ltype="text" title="单位px"/>
            </td>
        </tr>
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">下拉框字典：</td>
           <td align="left" class="l-table-edit-td">
         	  <input name="dict_code" type="text" id="dict_code" ltype="text" />
           </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">下拉框条件：</td>
            <td align="left" class="l-table-edit-td">
            	<textarea id="para_where" rows="3" class="l-textarea" name="para_where" title="以参数编码为下拉框级联条件，多个以逗号隔开" style="width:300px"></textarea>
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
        	<td align="left" class="l-table-edit-td">
                <input type="checkbox" id="is_required"/>必填
                &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="is_stop"/>停用
            </td>
        </tr>
	</table>
    </form>
</body>
</html>
