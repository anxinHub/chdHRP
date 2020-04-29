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
<!-- 配置界面上的css -->  
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/plupload-2.1.3/js/jquery.plupload.queue/css/jquery.plupload.queue.css">  
<script type="text/javascript" src="<%=path%>/lib/plupload-2.1.3/js/plupload.full.min.js"></script>  
<script type="text/javascript" src="<%=path%>/lib/plupload-2.1.3/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>  
   
<!-- 国际化中文支持 -->  
<script type="text/javascript" src="<%=path%>/lib/plupload-2.1.3/js/i18n/zh_CN.js"></script> 

<script type="text/javascript">


$(function(){
	uploader.init();
	
});

function selectFiles(){
	$("#selectZipFile").click();
}

function importFlow(){
	$("#pageloading").show();
	$("#message").html("");
	uploader.start();
}

function removeFiled(obj){
		$("#message").html("");
		//替换<div id="'+obj.id+'">这个标签里面所有的内容，包含自己 style="display:none">
		var fileId=obj.id.substring(0,obj.id.length-1);
		uploader.removeFile(uploader.getFile(fileId));	
		document.getElementById('filelist').innerHTML=document.getElementById('filelist').innerHTML.replace('<div id="'+fileId+'">'+document.getElementById(fileId).innerHTML+"</div>","");
}

var errorCode={
		"-100":"上传异常",
		"-200":"http网络错误",
		"-300":"磁盘读写错误，例如本地上某个文件不可读",
		"-400":"因为安全问题而产生的错误",
		"-500":"初始化时发生错误",
		"-600":"选择的文件太大",
		"-601":"选择的文件类型不符合要求",
		"-602":"选择了重复的文件",
		"-700":"图片格式错误",
		"-702":"文件大小超过所能处理的最大值"
};

function getErrorMessageCH(errCode){
	if(errorCode[errCode]=="undefined"){
		return "内存错误";
	}else{
		return errorCode[errCode];
	} 
}

</script>
</head>

<body style="padding: 0px;overflow:auto;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="liger-button" id="selectZipFile" style="display:none">选择文件</div>  
    <div style="width:485px;height:325px;overflow:auto;" >
    	<span style="font-size: 16px;color: blue;">支持文件格式：zip、bpmn</span>
        <div id="filelist" style="line-height:20px"></div>
        <div id ="message"  align="left" style="font-size: 16px;color: red;">您的浏览器没有安装Flash或者Silverlight插件不支持HTML5。<br/>Your browser doesn't have Flash, Silverlight or HTML5 support。</div>  
	</div>
   <script>
   var cur_file=0;//当前文件下标
   var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : 'selectZipFile', // 这个是点击上传的html标签的id,可以a,button等等
		//container: document.getElementById('container'), //用来指定Plupload所创建的html结构的父容器，默认为前面指定的browse_button的父元素。该参数的值可以是一个元素的id,也可以是DOM元素本身。
		url : 'releaseFlowDesignerByZip.do?isCheck=false',
		flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',
		silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',
		multi_selection:true,//是否可以在文件浏览对话框中选择多个文件
		unique_names:true,//是否生成唯一的文件名，避免与服务器文件重名
		filters : {
			max_file_size : '1mb',
			prevent_duplicates:true,//是否允许选取重复的文件，为true时表示不允许，为false时表示允许，默认为false。	
			mime_types: [
				//{title : "Excel files", extensions : "xlsx,xls"},
				{title : "Zip files", extensions : "zip"},
				{title : "bpmn files", extensions : "bpmn"}
			]
		},
		init: {
			PostInit: function() {
				$("#filelist").html("");
				$("#message").html("");
			},
			//当文件添加到上传队列后触发
			FilesAdded: function(up, files) {
				//控制上传数量
				/*if(uploader.files.length>1){ 
		            //uploader.splice(1,999);
					$("#message").html("只能上传一个文件");					
		        }*/
		        
				plupload.each(files, function(file) {
					cur_file++;
					document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' +cur_file+'.'+file.name + ' (' + plupload.formatSize(file.size) + ') <b></b><span><a id="'+file.id+'B" style="color:blue;cursor:pointer;"  herf="javascript:void(0);" onclick="removeFiled(this);" >删除</a></span></div>';
				});
			},
			//会在文件上传过程中不断触发，可以用此事件来显示上传进度
			UploadProgress: function(up, file) {
				document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span style="font-family:华文中宋; color:blue; ">' + file.percent + "%</span>";
				if(file.percent=="100"){
					document.getElementById(file.id).getElementsByTagName('span')[1].innerHTML="";//上传成功，去掉删除按钮
				}
			},
			
			Error: function(up, err) {
				uploader.stop();
				$("#pageloading").hide();
				document.getElementById('message').innerHTML = "Error #" + err.code + ": " + getErrorMessageCH(err.code);//err.message;
			},
			//当队列中的某一个文件正要开始上传前触发
			BeforeUpload:function(up,file){
				uploader.setOption("multipart_params",{"fileId":file.id,"fileName": file.name});//"fileLength":uploader.files.length,
			},
			//当上传队列中某一个文件开始上传后触发
			FileUploaded:function(up,file,result){
				var resJson=JSON2.parse(result.response);
				plupload.each(uploader.files, function(f) {
					if(f.id==resJson.fileId){
						if(resJson.showType=="error"){
							//uploader.stop();
							document.getElementById(f.id).getElementsByTagName('b')[0].innerHTML = '<span style="font-family:华文中宋; color:red; ">导入失败</span>';
							//	document.getElementById(f.id).getElementsByTagName('span')[1].innerHTML='<button id="'+f.id+'B" onclick="removeFiled(this);">删除</button>';
							//	f.status=1;
							var xuhao=$("#"+file.id).text().substring(0,$("#"+file.id).text().indexOf(".")+1);
							$("#message").html($("#message").html()+xuhao+resJson.msg+"<br/>");
						}else{
							document.getElementById(f.id).getElementsByTagName('b')[0].innerHTML = '<span style="font-family:华文中宋; color:blue; ">导入成功</span>';
						}
						
					}
				});
				$("#pageloading").hide();
			},
			
			UploadComplete : function(up,files){
				parent.loadFlowTree();
				if(parent.nodeSel==null){
					parent.query(1);
     			}
				$("#pageloading").hide();
			}
		}
	});
   </script>
</body>
</html>
