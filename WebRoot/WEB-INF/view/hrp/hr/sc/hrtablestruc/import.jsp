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
	       	chunk_size : '10mb',           
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
			   		if(json.errorRows.Rows!= null&&json.errorRows.Rows!= ""){
			   			$.ligerDialog.success('成功导入:' +json.succesCount+ '条, 失败:' +json.errorCount+ '条');
			   			$("#uploader").css("display","none");
					   	$("#maingrid").css("display","block");
					    grid.loadData(json.errorRows);
					    parent.search();
                        parent.tree.reload();
			   		}else{
			   			$.ligerDialog.success('成功导入:' +json.succesCount+ '条, 失败:' +json.errorCount+ '条');
			   			$("#uploader").css("display","block");
			   		   	$("#maingrid").css("display","none");
			   	        plupload();
			   	     	parent.search();
			   	        parent.tree.reload();
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
					{display : '结构代码',name : 'tab_code',align : 'left',editor : {type : 'text'},width : 80},
					{display : '结构名称',name : 'tab_name',align : 'left',editor : {type : 'text'},width : 80},
					{display : '类型代码',name : 'type_tab_code',align : 'left',editor : {type : 'text'},width : 80},
					{display : 'JSON存储数据表字段',name : 'tab_col',align : 'left',editor : {type : 'text'},width : 150},
					{display : 'JSON存储数据表SQL',name : 'tab_sql',align : 'left',editor : {type : 'text'},width : 150},
					{display : 'JSON数据列设置',	name : 'tab_view_col',align : 'left',editor : {type : 'text'},width : 150},
					{display : 'JSON数据列设置',name : 'tab_view_page',align : 'left',	editor : {type : 'text'},width : 150},
					{display : 'JSON查询设计器',name : 'tab_query_col',align : 'left',editor : {	type : 'text'},width : 150},
					{display : 'JSON动态页面构建',name : 'tab_query_page',align : 'left',editor : {	type : 'text'},width : 150},
					{display : '是否内置',name : 'is_innr',align : 'left',editor : {type : 'text'},	width : 50},
					{display : '排序号',name : 'sort',align : 'left',editor : {type : 'text'},	width : 50},
					{display : '是否停用',name : 'is_stop',align : 'left',editor : {type : 'text'},	width : 50},
					{display : '备注',name : 'note',align : 'left',editor : {type : 'text'},	width : 100},
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
				selectRowButtonOnly:true,
				/* toolbar: { items: [
			    	                {text : '保存',
										id : 'save',
										click : itemclick,
										icon : 'add',
			    	                }, 
									{line : true},
									{text : '删除',
										id : 'delete',
										click : itemclick,
										icon : 'delete'},
									{line : true}
									
			    				]} */
					});
	gridManager = $("#maingrid").ligerGetGridManager();
}
function itemclick(item){ 
    if(item.id)
    {
        switch (item.id)
        {
            case "save":
            	var data = gridManager.getData();
           	 	ajaxJsonObjectByUrl("saveImportData.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
                	if(responseData.state=="true"){
           					$("#uploader").css("display","block");
							$("#maingrid").css("display","none");
							parent.query();
							$.ligerDialog.confirm('导入成功！是否关闭当前页面', function (yes) {
								if(yes==true){
									dialog.close();
								}else{
									document.location.reload();
								}
							});
           			}else if(responseData.state=="err"){
           				$("#uploader").css("display","block");
						$("#maingrid").css("display","none");
						plupload();
						document.location.reload();
           			}else{
           				grid.loadData(responseData);
           			}
           		});
				return;
			case "delete":  
                	var data = gridManager.getCheckedRows();
                	 if (data.length == 0){
                     	$.ligerDialog.error('请选择行');
                     }else{
               	       var data = gridManager.deleteSelectedRow();
                     }
				return;
        }   
    }
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
