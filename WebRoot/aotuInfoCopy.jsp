<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//out.print(path);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery.cookie.js"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	$(function (){	
		$("#exist").click(function(){
			parent.$.XYTipsWindow.removeBox();
		});
			loadCompDict();
			liger.get("hos_code").setValue('${hos_id}');
			liger.get("hos_code").setText('${hos_name}');
			liger.get("copy_code").setValue('${copy_code}');
			liger.get("copy_code").setText('${copy_name}');
			liger.get("mod_code").setValue('${mod_code}');
			liger.get("mod_code").setText('${mod_name}');
			
				var curDate=new Date();
	        	$("#acct_year").val(curDate.getFullYear());
	        	$("#save").ligerButton({
	        		click: function (){
	        			query();
	        		}
	        	});
	        	$("#exist").ligerButton({
	        		click: function (){
	        			query();
	        		}
	        	});
	  });
	
     function  save(){
    
     	
     	if(liger.get("copy_code").getValue()==""){
     		$.ligerDialog.error("请选择账套.");
     		return false;
     	}
     	if($("#acct_year").val()==""){
     		$.ligerDialog.error("请选择年度.");
     		return false;
     	}
     	if(liger.get("mod_code").getValue()==""){
     		$.ligerDialog.error("请选择系统.");
     		return false;
     	}
     	

     	$("#loginMsg").text("系统登录中,请稍候...");
     	var formPara={copy_code:liger.get("copy_code").getValue(),copy_name:liger.get("copy_code").getText()
     			,acct_year:$("#acct_year").val()
     			,mod_code:liger.get("mod_code").getValue()};
     	ajaxJsonObjectByUrl("systemThemeJump.do?isCheck=false",formPara,function(responseData){
    		if(responseData.state=="true"){
    			openMain(liger.get("mod_code").getValue(),liger.get("copy_code").getValue());
    		}else{
    			$.ligerDialog.error("License没有授权系统权限.");
    			$("#loginMsg").text("");
    		}
    	});
	}
     var para = "";//清空参数
     function loadCompDict(){
    	 
    	autocomplete("#hos_code","hrp/sys/queryHosInfoDictPerm.do?isCheck=false","id","text",true,true);    
    	autocomplete("#copy_code","hrp/sys/queryCopyCodeDictPerm.do?isCheck=false","id","text",true,true,para);    
     	autocomplete("#mod_code","hrp/sys/queryModDictPerm.do?isCheck=false","id","text",true,true,para);    
     	}
     function chargeInfoCode(){
    	 para = {
 				hos_id:liger.get("hos_code").getValue(),
 				copy_code:liger.get("copy_code").getValue()
 		};
    	autocomplete("#copy_code","hrp/sys/queryCopyCodeDictPerm.do?isCheck=false","id","text",true,true,para);    
      	autocomplete("#mod_code","hrp/sys/queryModDictPerm.do?isCheck=false","id","text",true,true,para);    

     }
     function chargeCopyCode(){
    	 para = {
  				hos_id:liger.get("hos_code").getValue(),
  				copy_code:liger.get("copy_code").getValue()
  		};
    	 autocomplete("#mod_code","hrp/sys/queryModDictPerm.do?isCheck=false","id","text",true,true,para);    

     }
 	function openMain(mod_code,copy_code){

		var skin=$.cookie("drp_htc_skin");
		if(skin!=null && skin!=""){
			skin="&skin="+skin;
		}else{
			skin="";
		}
	
		var win = window.open("main.html?copy_code=" +copy_code + "&mod_code="+mod_code+skin, "", "width="+(screen.availWidth)+", height="+(screen.availHeight-35)+", top=0, left=0,scrollbars=1, resizable=0, status=1");
		top.window.opener=null;
		top.window.open('','_self','');
		top.window.close();
		win.focus();
		
	} 

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center" style="margin-top:30px;">
<tr>
				<td align="right" class="l-table-edit-td">医院：</td>
				<td align="left" class="l-table-edit-td"><input type="text"
					id="hos_code" name="hos_code" onchange="chargeInfoCode()"/></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">账套：</td>
				<td align="left" class="l-table-edit-td"><input type="text"
					id="copy_code" name="copy_code" onchange="chargeCopyCode()"/></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">年度：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate"
					type="text" id="acct_year"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" style="width:160px;"/></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">系统：</td>
				<td align="left" class="l-table-edit-td"><input type="text"
					id="mod_code" name="mod_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
			</tr>
			<tr>
				<td height="20px" colspan="2">&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><div id="loginMsg"
						style="width: 200px; text-align: center; color: #F00;"></div></td>
			</tr>
			<tr>
            	<td align="right" ><div id="save" onclick="save()">确 定</div></td>
            	<td align="center" ><div id="exist">关 闭</div></td>
    		</tr>
		</table>
	</form>
</body>
</html>
