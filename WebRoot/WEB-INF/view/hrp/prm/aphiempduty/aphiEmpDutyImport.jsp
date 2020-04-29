<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
var grid;
var fileName;
var is_stop = [{ is_stop: 1, text: '是' }, { is_stop: 0, text: '否'},];
$(function(){
	 loadHead(null);
    function plupload(){  
     $("#uploader").pluploadQueue({           
            runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
            url : "readAphiEmpDutyFiles.do?isCheck=false",    
            chunk_size : '1mb',           
            filters : {               
                max_file_size : '2048kb',  
                mime_types: [  
                    {title : "excel files", extensions : "xls,xlsx"}  
                ]  
            },  
            flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',                 
   	     	silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',     
            // 参数   可不加
            multipart_params: {'user': 'lyd', 'time': '2012-06-12'}  ,
	   	  init: {
	   		FileUploaded: function(uploader,file,responseObject) {
	   		var res=responseObject.response;
	   		var json = eval('(' + res + ')');
			   		 if(json.Rows!= null&&json.Rows!= ""){
			   			$("#uploader").css("display","none");
					   	$("#maingrid").css("display","block");
					  	$("#Reload").css("display","none");
					    grid.loadData(json);
			   		}else{
			   			$("#message").html("导入成功！");
			   		}
	   		  	}
	   		 }
        });
    }  
    
    plupload();  
      
    $('#Reload').click(function(){  
    	$("#uploader").css("display","block");
	   	$("#maingrid").css("display","none");
        plupload();  
    }); 
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
{ display: '职务编码', name: 'duty_code', align: 'left',editor: { type: 'text' } },
{ display: '职务名称', name: 'duty_name', align: 'left',editor: { type: 'text' } },
{ display: '备注', name: 'duty_note', align: 'left',editor: { type: 'text' } },
{ display: '是否停用', name: 'is_stop',align: 'left',
	editor: { type: 'select',data:is_stop,valueField: 'is_stop'},
	render: function (rowdata, rowindex, value){
		 if(rowdata.is_stop == 1){
 			 return "是";
 		 }
		 
		  if(rowdata.is_stop == 0){
 			 return "否";
 		 }
		
			return "";
    }
},
{ display : '错误信息' , name :'error_type',align:'left',
	  render :function(rowdata, rowindex, value){
		 return "<span style='color:red'>"+rowdata.error_type+"</span>";
      }
	}
                 ],
 				width : '100%',
				height : '100%',
				enabledEdit: true,
				selectRowButtonOnly:true,
				 toolbar: { items: [
			    	                {text : '保存',
										id : 'save',
										click : itemclick,
										icon : 'add'}, 
									{line : true},
									{
									  text : '删除',
									  id : 'delete',
									  click : itemclick,
									  icon : 'delete'
									}
			    				]}
					});
	gridManager = $("#maingrid").ligerGetGridManager();
	formatYesNo();
}
function itemclick(item){ 
    if(item.id)
    {
        switch (item.id)
        {
           case "save":
        	var data = gridManager.getData();
            ajaxJsonObjectByUrl("addBatchEmpDutyDict.do?isCheck=false",{ParamVo : JSON.stringify(data)},function (responseData){
                	if(responseData.state=="true"){
        				$("#uploader").css("display","block");
						$("#maingrid").css("display","none");
						$("#Reload").css("display","block");
						  plupload();  
        	    	}else if(responseData.state=="err"){

        			   $("#uploader").css("display","block");
				  	   $("#maingrid").css("display","none");
					   $("#Reload").css("display","block");
					    plupload();
        				
            		}else{
    
            			    grid.loadData(responseData);
            			
            		}

                });
                	
                	
                	
                	
                   /* var ParamVo =[];
                    $(data).each(function (){
                    	ParamVo.push(this.duty_code+"@"+this.duty_name);
                    });
                        	ajaxJsonObjectByUrl("addBatchEmpDutyDict.do?isCheck=false",{ParamVo : ParamVo},function (responseData){
                        		 if(responseData.state=="true"){
                        				$("#uploader").css("display","block");
	   									$("#maingrid").css("display","none");
	   									$("#Reload").css("display","block");
       									 plupload();  
                        		} 
                        	});*/
                    	
                return;
                
            case 'delete' :
              
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
	<div style="width: 750px; margin: 0px auto;">
		<div id="uploader"></div>
	</div>
	<div id="message" align="center"
		style="font-size: 18px; margin-top: -20px; color: red;"></div>
	<div id="maingrid" style="display: none; padding-top: 20px"></div>
</body>
</html>
