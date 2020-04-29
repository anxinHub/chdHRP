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
<script src="<%=path %>/lib/ligerUI/js/plugins/ligerPanel.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>

<script type="text/javascript">
  
	$(function (){
		
		loadToolbar();
		
		 $("#panelTitle").ligerPanel({
             title: '流程标题',
             width: '100%',
             height : '100%'
             //url : ''
         });
		 $("#panelFile").ligerPanel({
             title: '流程附件',
             width: '100%',
             height : '100%'
             //url : ''
         });
		 
		 loadForm(); 
	}); 
  
	function loadToolbar(){
	  	 //工具条
	     $("#toptoolbar").ligerToolBar({ items: [
	      	{ text: '发送', id:'true', click: send, icon:'outbox' },
	      	{ line:true },
	        { text: '选择模板', id:'save', click: selectFlow,icon:'customers' },
	        { line:true },
	        { text: '上传附件', id:'uploadFile', click: uploadFile,icon:'uploadzip' }
	      ]
	      });
  	}
	
	var form;
	function loadForm(){
		
		form=$("#formTitle").ligerForm({
            inputWidth: 170, labelWidth: 90, space: 20,labelAlign:'right',
            fields: [
			{ display: "标题",name: "flowTilte", newline: true, type: "text", width: '500', validate: { required: true, maxlength: 200 } },
            { display: "重要程度", name: "important", newline: false, type: "select",width:100, validate: { required: true},editor: {data:[{ id: '1', value: '1', text: '普通' },
                                                                                                { id: '2', value: '2', text: '重要' },
                                                                                                { id: '3', value: '3', text: '非常重要' }],value:'1'} }
           
            ]
        });
		
		/* form.setData({ 
			 important: '1'
         });*/
	}
	
	function send(){
		
	}
	
	function selectFlow(){
		$.ligerDialog.open({
            title : '选择流程模板',
            url: 'flowSelectSendPage.do',
            width: $(window).width()+10,
            height: $(window).height(),
            buttons: [ { text: '确定',onclick: function (item, dialog) { dialog.frame.selectFiles(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
		});
	}
	
	function uploadFile(){
		$("#FlowFileList").html($("#FlowFileList").html()+'&nbsp;&nbsp;<a href="#">aaaaaaaa.text</a>');
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="panelTitle" style=" margin-top:0px;overflow: auto;">
		<form id="formTitle"></form>
	</div>
	<div id="panelFile" style=" margin-top:10px; overflow: auto;">
		<div id="FlowFileList"></div>
	</div>
	<div id="toptoolbar" style=" margin-top:10px;"></div>
</body>
</html>
