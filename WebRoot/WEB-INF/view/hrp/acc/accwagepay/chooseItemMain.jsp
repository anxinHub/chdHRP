<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
	var grid;
	
	var gridManager = null;
	
	var userUpdateStr;
     
     $(function (){
     	
    	 $("#layout1").ligerLayout({ topHeight:50,minLeftWidth:230,allowLeftResize: false}); 
    	 
    	 loadHead();
        
     });  
     
   	//查询
    function  query(){
     	
   		grid.options.parms=[];
   		
   		grid.options.newPage=1;
   		
		grid.options.parms.push({name:'item_code',value:$("#item_code").val()});
    	
    	grid.options.parms.push({name:'item_name',value:$("#item_name").val()});
    	
    	grid.options.parms.push({name:'acc_year',value:'${acc_year}'});
        
     	//加载查询条件
     	grid.loadData(grid.where);
      
     }
     
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '项目编码', name: 'item_code', align: 'left'
 					 },
                      { display: '项目名称', name: 'item_name', align: 'left'
 					 },
                      { display: '说明', name: 'note', align: 'left'
 					 },{
 						 name: 'column_item', hide:'column_item'
 					 }],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'../accwageitems/queryAccWageItems.do?isCheck=false&wage_code='+'${wage_code}'+'&scheme_id='+'${scheme_id}'+"&acc_year="+'${acc_year}',
                      width: '100%', height: '100%', checkbox: true,rownumbers:true,
                      selectRowButtonOnly:true,enabledEdit: true ,//heightDiff: -10,
                      toolbar: { items: [
                      	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                      	{ line:true },
                      	{ text: '确认（<u>A</u>）', id:'add', click: btn_add,icon:'add' },
                      	{ line:true }
     				]} 
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
         
     }

     function btn_add()
     { 
         var data = grid.getCheckedRows(); 
         
         if(data.length == 0){
        	 
        	 $.ligerDialog.error('请选择导入数据！');
        	 
             return; 
         }
         
        var ParamVo=[];
     	
     	$(data).each(function (){
     		
     		ParamVo.push(
 			//表的主键
 			this.column_item+"@"+'${scheme_id}'
 			
 			)
 			
         });
     	
     	ajaxJsonObjectByUrl("addBatchAccWageSchemeItem.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
    		 if(responseData.state=="true"){
    			parent.loadHead();
    		} 
    	});
        
     }
     
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   
	   <div class="l-layout" id="layout1" style="height: 100%;" >
	           
		       <div position="top" >
		         <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 10px">
				   	 	<tr>
				            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项编码：</td>
				            <td align="left" class="l-table-edit-td" colspan="2"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:false,maxlength:18}" /></td>
				            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项名称：</td>
				            <td align="left" class="l-table-edit-td" colspan="2"><input name="item_name" type="text" id="item_name" ltype="text" validate="{required:false,maxlength:18}" /></td>
				        </tr>
		   			</table>
		       </div>
		      
	          <div position="center"  title="  ">
			           <div id="maingrid" ></div>
	         </div>  
	    </div>  
    </body>
</html>
