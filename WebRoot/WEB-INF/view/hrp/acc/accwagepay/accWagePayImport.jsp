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
	
    var para ="";
    
    var sumPara = "";
    
    var wherePara="";
	
	$(function(){
		
		loadHead(null);
		
		function plupload(){  
			
			var parm = "scheme_id="+
			'${scheme_id}'   +"&acc_year="+ 
			'${acc_year}'   +"&acc_month="+ 
			'${acc_month}'   +"&wage_code="+ 
			'${wage_code}'   +"&group_id="+ 
			'${group_id}'   +"&hos_id="+ 
			'${hos_id}'   +"&copy_code="+ 
			'${copy_code}';
			
			$("#uploader").pluploadQueue({   
				
		       	runtimes : 'flash,html5,gears,browserplus,silverlight,html4',    
		       	
		       	url : "readAccWagePayFiles.do?isCheck=false&"+parm,   
		       			
		       	chunk_size : '5mb',   
		       	
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
								    
						   		}else{
						   			
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
		
		var columns = "";
		
		//var wage_scheme= '${scheme_id}';
		
		var wage_code= '${wage_code}';
		
		columns = columns +"{ display: '年度', name: 'acc_year', align: 'left'},"
		
		+"{ display: '月份', name: 'acc_month', align: 'left'},"
		
		+"{ display: '职工编码', name: 'emp_code', align: 'left'},"
		
    	+"{ display: '职工名称', name: 'emp_name', align: 'left'},"
    	+"{ display: '失败原因', name: 'error_type', align: 'left'}";
			
		
		$.ajax({
			
			type: "POST", 
			
	        url: "queryAccWagePayGrid.do?isCheck=false",
	        
	        data: {'scheme_id':'${scheme_id}',"wage_code":wage_code,'acc_year':'${acc_year}'},
	        
	        dataType: "json",
	        
	        async: false,
	        
	        success: function(data){
	        	
	        	if(data.Rows.length>0){
	        		
	        		$.each(data.Rows,function(i,v){
	        			
	        			columns = columns + ",{ display: '"+v.ITEM_NAME+"', name: '"+v.COLUMN_ITEM+"', align: 'right',"+
	        				
	        			"render:function(rowdata){ return rowdata."+v.COLUMN_ITEM+">0?rowdata."+v.COLUMN_ITEM+":'0'}}";
	
	        			/* para+= ",awp."+v.COLUMN_ITEM;
	        			
	        			sumPara+= ",sum(awp."+v.COLUMN_ITEM+") as "+v.COLUMN_ITEM; */
	        			
	        		});
	        		
	        		columns = columns +",{ display: '错误说明', name: 'error_type', align: 'left'}"
	        		
	        		columns = columns.substr(0,columns.length);
	        		
	        	}
	        	
	        }
		});
		
		grid = $("#maingrid").ligerGrid({
			
					columns : eval("["+columns+"]"),
					
					width : '99%',
					
					height: '370',

					usePager:false,
					
					rownumbers:true,
					
					minColumnWidth:100,
					
					columnWidth:'15%',
					
					selectRowButtonOnly:true
					
				});
		
		gridManager = $("#maingrid").ligerGetGridManager();
		
	}
	/* function itemclick(item){ 
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
	} */
    </script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div style="width:1100px;margin:0px auto;">  
        <div id="uploader"></div>  
	</div>
	<div id="maingrid" style="display: none ; padding-top: 20px" ></div>  
</body>
</html>
