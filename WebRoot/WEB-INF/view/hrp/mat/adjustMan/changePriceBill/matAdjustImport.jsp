<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<!-- 
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="s-chd hrp htc hpm">
<meta name="robots" content="all">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1"> -->
<script src="/CHD-HRP/lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<link href="/CHD-HRP/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="/CHD-HRP/lib/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="/CHD-HRP/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script src="/CHD-HRP/lib/jquery-validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="/CHD-HRP/lib/jquery-validation/jquery.metadata.js"
	type="text/javascript"></script>
<script src="/CHD-HRP/lib/jquery-validation/messages_cn.js"
	type="text/javascript"></script>
<script src="/CHD-HRP/lib/hrp.js" type="text/javascript"></script>
<script src="/CHD-HRP/lib/map.js"></script>
<script src="/CHD-HRP/lib/hrp/cost/cost.js"	type="text/javascript"></script>
<script src="/CHD-HRP/lib/json2.js"></script>
<script src="/CHD-HRP/lib/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="/CHD-HRP/lib/layer-v2.3/layer/layer.js"
	type="text/javascript"></script>
<script src="/CHD-HRP/lib/hotkeys/jquery.hotkeys.js"
	type="text/javascript"></script>
<script src="/CHD-HRP/lib/hrp/ass/ligerNumberbox.js"
	type="text/javascript"></script>

<!-- 打印配置 -->

<script
	src="/CHD-HRP/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js"
	type="text/javascript"></script>
<script
	src="/CHD-HRP/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js"
	type="text/javascript"></script>

<!-- 配置界面上的css -->
<link rel="stylesheet" type="text/css"
	href="/CHD-HRP/lib/plupload-2.1.3/js/jquery.plupload.queue/css/jquery.plupload.queue.css">
<script type="text/javascript"
	src="/CHD-HRP/lib/plupload-2.1.3/js/plupload.full.min.js"></script>
<script type="text/javascript"
	src="/CHD-HRP/lib/plupload-2.1.3/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>

<!-- 国际化中文支持 -->
<script type="text/javascript"
	src="/CHD-HRP/lib/plupload-2.1.3/js/i18n/zh_CN.js"></script>
<script language="javascript" src="/CHD-HRP/lib/Lodop/LodopFuncs.js"></script>
<object id="LODOP_OB"
	classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>

<script type="text/javascript">
var grid;
var fileName;
var dialog = frameElement.dialog;
$(function(){
	loadHead(null);
	function plupload(){  
		$("#uploader").pluploadQueue({           
	       	runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
	       	url : "readMatAdjustFiles.do?isCheck=false",    
	       	chunk_size : '2mb',           
	       	filters : {                
	           mime_types: [  
	               {title : "excel files", extensions : "xls,xlsx"}
	           ]  
	       	},  
	       	flash_swf_url : '/CHD-HRP/lib/plupload-2.1.3/js/Moxie.swf',                 
		    silverlight_xap_url : '/CHD-HRP/lib/plupload-2.1.3/js/Moxie.xap',     
	   	  	init: {
		   		FileUploaded: function(uploader,file,responseObject) {
			   		var res=responseObject.response;
			   		var json = eval('(' + res + ')');
					   		 if(json.Rows!= null&&json.Rows!= ""){
					   			$("#uploader").css("display","none");
							   	$("#maingrid").css("display","block");
							    grid.loadData(json);
					   		}else{
					   			$.ligerDialog.success('导入成功！');
					   			$("#uploader").css("display","block");
					   		   	$("#maingrid").css("display","none");
					   	        plupload();
					   	       	parent.loadTree();
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
							 { display: '材料编码', name: 'inv_code', align: 'left',editor:{type:'text'}},
							 /* { display: '材料名称', name: 'inv_name', align: 'left',editor:{type:'text'}},
							 { display: '规格型号', name: 'inv_model', align: 'left',editor:{type:'text'}},
							 { display: '计量单位', name: 'unit_name', align: 'left',editor:{type:'text'}},
							 { display: '生产厂商', name: 'fac_name', align: 'left',editor:{type:'text'}}, */
							 { display: '原计划价', name: 'old_price', align: 'left',editor:{type:'text'}},
							 { display: '新计划价', name: 'new_price', align: 'left',editor:{type:'text'}},
							 { display: '调价原因', name: 'adjust_reason', align: 'left',editor:{type:'text'}},
					         { display: '错误说明', name: 'error_type', align: 'left',width: '35%',
	               	 	render:function(rowdata, rowindex, value){
	              		return "<span style='color:red'>"+rowdata.error_type+"</span>";
	              		}
              		}],
				width : '99%',
				height: '370',
				enabledEdit: true,
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
									
			    				]}*/
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
           	 	ajaxJsonObjectByUrl("addBatchMatType.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
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
