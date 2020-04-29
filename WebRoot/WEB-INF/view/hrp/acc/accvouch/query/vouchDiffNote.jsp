<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css"/>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>

<style type="text/css">
     
</style> 

<script type="text/javascript">
var dialog=frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var diffNoteJson=dialog.get("data").diffNoteJson;

	$(function() {
		loadDict();
		loadNote(99);
	});
	
	function loadDict(){
		$("#sign_flag").ligerComboBox({
			data:[{ id: 99, text: '全部'},{ id: 0, text: '未标注'},{ id: 2, text: '不需要标注' }],
			valueField: 'id',
	     	textField: 'text', 
		    cancelable: false,
		    autocomplete: false,
		    width: 120,
		    value: 99,
		    onBeforeSelect: function (newvalue){
		    	
		    },onSelected: function (selectValue){
		    	loadNote(selectValue);
		    }
		});
	}
	
	
	function loadNote(flag){
		
		$("#note").html("");
		var rows=0;
		if(diffNoteJson.Rows.length>0){
			
			$("#note").append("<table>");
			$.each(diffNoteJson.Rows,function(i,o){
				if(flag==0){
					if(o.note.indexOf("没有维护")!=-1){
						rows++;
						$("#note").append("<tr><td>"+(rows)+".</td><td><a href=javascript:openSuperVouch('"+o.vouch_id+"')>"+o.note+"</a></td></tr>");	
					}
				}else if(flag==2){
					if(o.note.indexOf("不需要标注")!=-1){
						rows++;
						$("#note").append("<tr><td>"+(rows)+".</td><td><a href=javascript:openSuperVouch('"+o.vouch_id+"')>"+o.note+"</a></td></tr>");	
					}
				}else{
					rows++;
					$("#note").append("<tr><td>"+(rows)+".</td><td><a href=javascript:openSuperVouch('"+o.vouch_id+"')>"+o.note+"</a></td></tr>");
				}
				
			});
			$("#note").append("</table>");
			$("#note").append("<br/>");
		}
			
	}
	
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
   
	

</script>
</head>

<body style="padding: 10px;"  >
 	<div style="font-size: 14px;">
 		<input name="sign_flag" type="hidden" id="sign_flag"  validate="{required:true,maxlength:20}" />以下凭证不能自动生成差异标注：
 		<div id="note">
 		</div>
 	</div> 
	
</body>
</html>
