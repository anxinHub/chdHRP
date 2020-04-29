<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
var grid;
var fileName;
var dialog = frameElement.dialog;
$(function(){
	
	loadHead(null);
	function plupload(){  
		$("#uploader").pluploadQueue({           
	       	runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
	       	url : "readAccWageItemFiles.do?isCheck=false",    
	       	chunk_size : '20mb',           
	       	filters : {                
	           mime_types: [  
	               {title : "excel files", extensions : "xls,xlsx"}
	           ]  
	       	},  
	       	flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',                 
		    silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',     
	   	  	init: {
		   		FileUploaded: function(uploader,file,responseObject) {
			   		var res=responseObject.response;
			   		var json = eval('(' + res + ')');
			   		
			   		if(json.Rows!= null&&json.Rows!= ""){
			   			$("#uploader").css("display","none");
					   	$("#maingrid").css("display","block");
					    grid.loadData(json);
			   		} else{
			   			$.ligerDialog.success('导入成功！');
			   			$("#uploader").css("display","block");
			   		   	$("#maingrid").css("display","none");
			   	        plupload();
			   	     	parent.query();
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
							 { display: '工资套编码', name: 'wage_code', align: 'left'},
							 { display: '工资套名称', name: 'wage_name', align: 'left'},
							 { display: '工资项编码', name: 'item_code', align: 'left'},
							 { display: '工资项名称', name: 'item_name', align: 'left'},
							 { display: '工资类型编码', name: 'item_type', align: 'left'},
							 { display: '工资类型名称', name: 'type_name', align: 'left'},
							 { display: '工资性质编码', name: 'item_nature', align: 'left'},
							 { display: '工资性质名称', name: 'nature_name', align: 'left'},
							 { display: '计算方式', name: 'is_cal', align: 'left'},
							 { display: '是否继承上月(是/否)', name: 'is_jc', align: 'left',
								 render:function(rowdata, rowindex, value){
									 if(rowdata.is_jc=="0"){
										 return "是";
									 }else if(rowdata.is_jc=="1"){
										 return "否";
									 }
										 return rowdata.is_jc;
								 }},
							 { display: '是否参与合计(是/否)', name: 'is_sum', align: 'left',
									 render:function(rowdata, rowindex, value){
										 if(rowdata.is_sum=="0"){
											 return "是";
										 }else if(rowdata.is_sum=="1"){
											 return "否";
										 }
											 return rowdata.is_sum;
								 }
							 },
							 { display: '错误说明', name: 'error_type', align: 'left',width: '35%',
							render:function(rowdata, rowindex, value){
								return "<span style='color:red'>"+rowdata.error_type+"</span>";
							}
				}],
				width : '98%',
				height: '100%',
				//enabledEdit: true,
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
    <div style="width:100%;margin:0px auto;">  
        <div id="uploader"></div>  
	</div>
	<input type="hidden" id="rules" name="rules" />
	<div id="maingrid" style="display: none ; padding-top: 20px" ></div>  
</body>
</html>
