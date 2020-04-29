<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
		var fileName;
		var dialog = frameElement.dialog;
		var grid;
		var gridManager;
		var bid_id;
		var is_flag;
		$(function(){
			bid_id = '${bid_id}';
			is_flag = '${is_flag}';
		function plupload(){

			$("#uploader").pluploadQueue({

					runtimes : 'flash,html5,gears,browserplus,silverlight,html4',

					url : "upLodeFile.do?isCheck=false&bid_id="+bid_id+"&is_flag="+is_flag,

					chunk_size : '20mb',

					filters : {

					mime_types: [

					{title : "doc files", extensions : "bmp,gif,jpg,pic,png,tif,pdf,doc,docx,zip,txt,xls,xlsx,ppt,pptx,rar"}

					]
					},
					flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',

					silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',

					init: {

						FileUploaded: function(uploader,file,responseObject) {

							var res=responseObject.response;

							var json = eval('(' + res + ')') ;

							if(json.state == "true"){
								$.ligerDialog.success('上传成功！');
								grid.loadData(); 
								parentFrameUse().query();
								plupload();
							}else{
								$.ligerDialog.error('上传失败！');
								plupload();
							}

							plupload();

						},
						FilesAdded : function(uploader, file){
							/* if(uploader.files.length>1) { 
								$.ligerDialog.error("只能上传一个文件，请删除多余文件！");
								return;
							} */
						}
					}
				});

			}
			loadHead();
			plupload();
		});

		function loadHead() {
			grid = $("#maingrid").ligerGrid(
					{
						columns : [ 
						{
							display : '文件类型',
							name : 'file_type',
							align : 'left',
							width: '220'
						}, {
							display : '文件名称',
							name : 'file_name',
							align : 'left', 
							width: '300'
						},{
							display : '删除',
							name : 'delete',
							align : 'left',
							width: '150',
							render : function(rowdata,rowindex,value) {
								var bid_id = rowdata.bid_id;
								var file_path = rowdata.file_path;
								var file_name = rowdata.file_name;
								var file_type = rowdata.file_type;
								if(rowdata){
									return "<a href=javascript:delTendFile('"+bid_id+"@"+file_path+"@"+file_name+"@"+file_type+"')><b>删除</b></a>";
								}
							}
						}, {
							display : '文件路径',
							name : 'file_path',
							align : 'left',
							width: '150',
							render: function(rowdata,index,value){
			            		  if(!rowdata.file_path){
			            			  return '<a><b>下载</b></a>';
			            		  }else{
			            			  var array = rowdata.file_path.split('/');
				            		  var name = array[array.length-1];
				                      return '<a href='+value+' download='+name+'><b>下载</b></a>';
			            		  }
		                 	}
						}],
						dataAction: 'server', 
						dataType: 'server', 
						url: 'queryAssTendFile.do?isCheck=false&is_flag='+is_flag+'&bid_id='+bid_id,
						width: '100%', 
						height: '100%', 
						checkbox: true, 
						rownumbers: true,
						enabledEdit : true,
						isAddRow:false,
						usePager: false,
						delayLoad: false,//初始化不加载
						selectRowButtonOnly: true //heightDiff: -10,
					});
			gridManager = $("#maingrid").ligerGetGridManager();
		}
		
		function delTendFile(Param) {
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteTendFile.do?isCheck=false", {
						Param : Param.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							grid.loadData(); 
						}
					});
				}
			});
		}
</script>
</head>

<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
<div style="width:100%;margin:0px auto;">
<div id="uploader"></div>
</div>
<div id="maingrid">
	
</div>
</body>
</html>
