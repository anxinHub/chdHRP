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
var grid;
var fileName;
var dialog = frameElement.dialog;
$(function(){
	$.post("getRules.do",null,function(responseData){
		 $("#rules").val(responseData);
    });
	loadHead(null);
	function plupload(){  
		$("#uploader").pluploadQueue({           
	       	runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
	       	url : "readAccSubjFiles.do?isCheck=false&rules="+$("#rules").val(),    
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
			   		if(json==null){
			   			
			   			$.ligerDialog.success('导入成功！');
			   			$("#uploader").css("display","block");
			   		   	$("#maingrid").css("display","none");
			   	        plupload();
			   	     	parent.query();
			   			
			   		}else if(json.Rows!= null&&json.Rows!= ""){
			   			$("#uploader").css("display","none");
					   	$("#maingrid").css("display","block");
					    grid.loadData(json);
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
							 { display: '科目编码', name: 'subj_code', align: 'left'},
							 { display: '科目名称', name: 'subj_name', align: 'left'},
							 { display: '英文名称', name: 'subj_name_en', align: 'left'},
							 { display: '币种名称', name: 'cur_name', align: 'left'},
							 { display: '科目类别名称', name: 'subj_type_name', align: 'left'},
							 { display: '科目性质名称', name: 'subj_nature_name', align: 'left'},
							 { display: '凭证类型名称', name: 'vouch_type_name', align: 'left'},
							 { display: '支出性质名称', name: 'out_name', align: 'left'},
							 { display: '余额方向(借/贷)', name: 'subj_dire_name', align: 'left',
								 render:function(rowdata, rowindex, value){
									 if(rowdata.subj_dire=="0"){
										 return "借";
									 }else if(rowdata.subj_dire=="1"){
										 return "贷";
									 }
										 return rowdata.subj_dire_name;

								 }},
							 { display: '期末调汇(是/否)', name: 'is_remit_name', align: 'left',
									 render:function(rowdata, rowindex, value){
										 if(rowdata.is_remit=="0"){
											 return "是";
										 }else if(rowdata.is_remit=="1"){
											 return "否";
										 }
											 return rowdata.is_remit_name;

								 }},
							 { display: '核算现金流(是/否)', name: 'is_cash_name', align: 'left',
									 render:function(rowdata, rowindex, value){
										 if(rowdata.is_cash=="0"){
											 return "否";
										 }else if(rowdata.is_cash=="1"){
											 return "是";
										 }
											 return rowdata.is_cash_name;

								}},
							 { display: '辅助核算1', name: 'check1_name', align: 'left'},
							 { display: '辅助核算2', name: 'check2_name', align: 'left'},
							 { display: '辅助核算3', name: 'check3_name', align: 'left'},
							 { display: '辅助核算4', name: 'check4_name', align: 'left'},
					         { display: '错误说明', name: 'error_type', align: 'left',width: '20%',
			               	 	render:function(rowdata, rowindex, value){
			              		return "<span style='color:red'>"+rowdata.error_type+"</span>";
	              			}
              		}],
				width : '99%',
				height: '370',
				//enabledEdit: true,
				usePager:false,
				rownumbers:true,
				selectRowButtonOnly:true
				
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
           	 	ajaxJsonObjectByUrl("addBatchAccSubjs.do?isCheck=false",{ParamVo : JSON.stringify(data),rules:$("#rules").val()},function (responseData){
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
    <div style="width:1100px;margin:0px auto;">  
        <div id="uploader"></div>  
	</div>
	<input type="hidden" id="rules" name="rules" />
	<div id="maingrid" style="display: none ; padding-top: 20px" ></div>  
</body>
</html>
