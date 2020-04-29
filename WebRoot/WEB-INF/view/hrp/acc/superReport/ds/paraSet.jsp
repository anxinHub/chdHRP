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
    	$("#sort_code").val("系统生成");
   		$("#sort_code").ligerTextBox({ disabled: true });
        $("#sort_code").attr( "disabled", true );
        $("#col_length").ligerSpinner({ height: 28, type: 'int',value:170 });
		$("form").ligerForm();
		loadData();
    });

	//修改页面加载数据
	function loadData(){
		
		var rownumber=dialog.get('data').rownumber;
		if(rownumber==undefined){
			return;
		}
		var gridData=dialog.get('data').gridData;
		$.each(gridData,function(i,obj){
    		if(i==rownumber){
    			$("#col_code").val(obj.col_code);
    			$("#col_name").val(obj.col_name);
    			liger.get("para_type").setValue(obj.para_type);
    			liger.get("col_type").setValue(obj.col_type);
    			liger.get("col_length").setValue(obj.col_length);
    			if(obj.para_json!=null && obj.para_json!=""){
    				var json=JSON.parse(obj.para_json);
    				$("#col_length").val(json.width==null?"":json.width);
    				liger.get("col_name").setValue(json.col_name);
    				
    			}
    			return false;
    		}
    	});
	}
    
    //添加
    function mySave(grid){
    	if($("#col_code").val()==""){
    		$.ligerDialog.error("数据列编码不能为空！");
			return false;
    	}
    	if($("#col_name").val()==""){
    		$.ligerDialog.error("数据列名称不能为空！");
			return false;
    	}
    	if(liger.get("col_name").getValue()==""){
    		$.ligerDialog.error("数据类型不能为空！");
			return false;
    	}
    	var isValidate=true;
    	$.each(grid.getData(),function(i,obj){
    		if(obj.col_code==$("#col_code").val()){
    			isValidate=false;
    			return false;
    		}
    	});
    	if(!isValidate){
    		$.ligerDialog.error("数据列编码在列表里面已经存在了！");
    		return false;
    	}
    	
    	grid.addRows(getPataJson());
    	$("#col_code").val("");
    	$("#col_name").val("");
    	$.ligerDialog.success("已添加到列表！");
    }
   
  //修改
    function myUpdate(grid,rownumber){
    	if($("#col_code").val()==""){
    		$.ligerDialog.error("数据列编码不能为空！");
			return false;
    	}
    	if($("#col_name").val()==""){
    		$.ligerDialog.error("数据列名称不能为空！");
			return false;
    	}
    	if(liger.get("col_name").getValue()==""){
    		$.ligerDialog.error("数据类型不能为空！");
			return false;
    	}
    	
    	grid.updateRow(rownumber,getPataJson());
    	dialog.close();
    }
  
    //拼装数据列字符串，返回json对象
    function getPataJson(){
    	//拼装列表数据其他字段
    	var dictText="";
    	var paraStr="\"col_code\":\""+$("#col_code").val()+"\",\"col_name\":\""+$("#col_name").val()+"\",\"is_stop\":0,\"sort_code\":0";
    	var paraJson=JSON.parse("{"+paraStr+"}");
    	
    	return paraJson;
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
	 	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>数据列编码：</td>
            <td align="left" class="l-table-edit-td">
          	  <input name="col_code" type="text" id="col_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>数据列名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="col_name" type="text" id="col_name" ltype="text" />
            </td>
        </tr>
       
       
        
	</table>
    </form>
</body>
</html>
