<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
var grid;
var fileName;
var dialog = frameElement.dialog;
$(function(){
	loadHead(null);
	function plupload(){  
		$("#uploader").pluploadQueue({           
	       	runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
	       	url : "readFiles.do?isCheck=false",    
	       	chunk_size : '2mb',           
	       	filters : {                
	           mime_types: [  
	               {title : "json files", extensions : "xls,xlsx,json"}
	           ]  
	       	},  
	       	flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',                 
		    silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',     
	   	  	init: {
		   		FileUploaded: function(uploader,file,responseObject) {
			   		var res=responseObject.response;
			   		var json = eval('(' + res + ')');
			   		if(json.error != null && json.error != ""){
			   			$.ligerDialog.error(json.error);
			   			return;
			   		}
			   		if(json.Rows!= null&&json.Rows!= ""){
			   			$("#uploader").css("display","none");
					   	$("#maingrid").css("display","block");
					    grid.loadData(json);
			   		}else{
			   			
                       $.ligerDialog.confirm('导入成功！', function (yes){
                    	   if(yes){
                    		    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                                parent.$.etDialog.close(curIndex);
					   	     	parent.search();
					   	    	parent.tree.reAsyncChildNodes(null, 'refresh');
                    	   }
                        });
			   		}
			   	}
			}
	   	});
	}
	plupload();
});
function getJsonObjLength(jsonObj) {
    var Length = 0;
    for (var item in jsonObj) {
      Length++;
    }
    return Length;
  }
function loadHead() {
	grid = $("#maingrid").ligerGrid(
					{
				columns : [
					{display : '代码表编码',name : 'field_tab_code',align : 'left',editor : {type : 'text'},width : 150},
					{display : '代码表名称',name : 'field_tab_name',align : 'left',editor : {type : 'text'},width : 120},
					{display : '代码项编码',name : 'field_col_code',align : 'left',editor : {	type : 'text'},width : 80},
					{display : '代码项名称',name : 'field_col_name',align : 'left',editor : {type : 'text'},	width : 80},
					{display : '错误说明',name : 'error_type',align : 'left',width : '35%',
						render : function(rowdata, rowindex, value) {
							return "<span style='color:red'>"
									+ rowdata.error_type + "</span>";
						}
					}
              		],
				width : '99%',
				height: '370',
				enabledEdit: false,
				usePager:false,
				rownumbers:true,
				selectRowButtonOnly:true
					});
	gridManager = $("#maingrid").ligerGetGridManager();

}
    </script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div style="width:775px;margin:0px auto;">  
        <div id="uploader"></div>  
	</div>
	<div id="maingrid" style="display: none ; padding-top: 20px" ></div>  
</body>
</html>
