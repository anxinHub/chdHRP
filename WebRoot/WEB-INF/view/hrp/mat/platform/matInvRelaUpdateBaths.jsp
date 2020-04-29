<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
     var dataFormat;
     var gridcharge;
 	var gridtitle;
 	var gridTotal;
 	var gridChargeManager = null;
	var gridtitleManager = null;
	var gridTatalManager = null;
     $(function (){
    	
         loadDict()//加载下拉框
         loadHead(null);
         f_gridcharge_loadData();
 		 f_gridtitle_loadData();
         $("#layout1").ligerLayout({
             leftWidth: 200,
             centerBottomHeight:200
         });

     });  
     
     function loadHead() {

    	 var colunmName=[];
    	  colunmName.push({display : 'HRP材料编码',name : 'inv_code',align : 'left',width : '30%'});
    	  
    	  colunmName.push({display : 'HRP材料名称',name : 'inv_name',align : 'left',width : '80%'});
    	  
    	gridcharge = $('#maingrid1').ligerGrid({
    	  columns : colunmName ,
    	  dataAction : 'server',dataType : 'server',usePager : true,
   	 	  url : 'queryInvCode.do?isCheck=false',
   	 	width : '70%',height : '48%',
   	      enabledEdit: true,
   	      title : 'HRP材料',
   	      checkbox : true,
		 // isSingleCheck:true
  	});
   	 gridChargeManager = $('#maingrid1').ligerGetGridManager();
    	
    	 
 		gridtitle = $("#maingrid2").ligerGrid({

 			columns : [{
 				display : '省平台材料编码',
 				name : 'goodsid',
 				align : 'left',
 				width : '30%',
 				editor: { type: 'text'}
 			},{
 				display : '省平台材料名称',
 				name : 'goodsname',
 				width : '80%',
 				align : 'left'
 			}],
 			dataAction : 'server',
 			dataType : 'server',
 			usePager : true,
 			url : 'queryGoodsid.do?isCheck=false',
 			width : '70%',
 			height : '48%',
 			checkbox : true,
 			title : '省平台材料',
 			isSingleCheck:true
 		});
 		gridtitleManager = $("#maingrid2").ligerGetGridManager();
 		
 		
 		gridTotal = $("#maingrid3").ligerGrid({

 			columns : [
			{ display: 'HRP材料ID', name: 'inv_id', align: 'left'
					},           
 			{ display: 'HRP材料编码', name: 'inv_code', align: 'left'
	 				},
            { display: 'HRP材料名称', name: 'inv_name', align: 'left'
			 		},
            { display: '省平台材料编码', name: 'goodsid', align: 'left'
			 		},
            { display: '省平台材料名称', name: 'goodsname', align: 'left'
			 		}],
 			dataAction : 'server',
 			dataType : 'server',
 			usePager : true,
 			//url : 'queryMatInvRela.do',
 			width : '98%',
 			height : '44%',
 			enabledEdit : true,
 			alternatingRow : true,
 			rownumbers:true,
 			checkbox : true,//heightDiff: -10,
            toolbar: { items: [
            	{ text: '删除', id:'delete', click: deleteInvIdMaping,icon:'delete' },
		    	{ line:true },
		    	{ text: '保存', id:'add', click: addInvMaping, icon:'add' },
		    	{ line:true },
		    	{ text: '关闭', id:'close', click: this_close,icon:'delete' }
		    	               
		    				]}
 		});
 		gridTotalManager = $("#maingrid3").ligerGetGridManager();
 		
 	}
     
     function deleteInvIdMaping(){
    	 var data = gridTotalManager.getCheckedRows();

	     if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
            	 if(isnull(this.group_id)){
            		 gridTotalManager.deleteSelectedRow();
					}									
					});
             if(ParamVo == ""){
					return;
				}
         }
     }
     
     function addInvMaping(){
    	 var totalData =  gridTotalManager.getData();
    	 
    	 //过滤空行
    	 var totalDataArr = new Array();
    	 $.each(totalData,function(){
    		 if(this.inv_code != null){
    			 totalDataArr.push(this);
    		 }
    	 });
    	 
    	 if(totalData.length == 0){
    		 $.ligerDialog.warn('对应关系表不能为空');
			 return;
    	 }
    	 
    	 ajaxJsonObjectByUrl("addMatInvMaping.do?isCheck=false",{maping_data : JSON.stringify(totalDataArr)},function (responseData){
     		if(responseData.state=="true"){
     			query();
     		}
     	});
     }
  
   function query() {
	    gridTotal.options.parms = [];
	    gridTotal.options.newPage = 1;
		gridTotal.loadData(gridTotal.where);
		f_gridcharge_loadData();
		f_gridtitle_loadData();
 	}  
   
   function f_gridcharge_loadData() {
		gridcharge.options.parms = [];
		gridcharge.options.newPage = 1;
		//根据表字段进行添加查询条件
		gridcharge.options.parms.push({ 
			name : 'inv_id',
			value : liger.get("inv_id").getValue().split(",")[0]
		});
		//加载查询条件
		gridcharge.loadData(gridcharge.where);

	}
   
   function f_gridtitle_loadData() {
		gridtitle.options.parms = [];
		gridtitle.options.newPage = 1;
		//根据表字段进行添加查询条件
		gridtitle.options.parms.push({
			name : 'goodsid',
			value : liger.get("goodsid").getValue()
		});
		
		//加载查询条件
		gridtitle.loadData(gridtitle.where);
	}
   
    function loadDict(){
    	
    	autocomplete("#inv_id","queryMatInvHrpBySelect.do?isCheck=false", "id", "text", true, true,'',false,'',150);
    	autocomplete("#goodsid", "queryMatInvSptBySelect.do?isCheck=false", "id", "text", true, true,'',false,'',150);
    	
    	$("#addData").ligerButton({click: addData, width:90});
		$("#close").ligerButton({click: this_close, width:90});
    	
     } 
    function this_close(){
		frameElement.dialog.close();
	}    
    function addData(){
    	 var data3 = [];
    	 
    	 var data = gridChargeManager.getCheckedRows();
   	 	 var data2 = gridtitleManager.getCheckedRows();
   	 	 var totalData =  gridTotalManager.getData();
	   	for(var i = 0; i < data.length;i++){
	   		for(var k = 0; k < data2.length;k++){
	   			for(var j = 0;j < totalData.length;j++){
					 if(data[i].inv_code == totalData[j].inv_code && data2[k].goodsid == totalData[j].goodsid ){
						 $.ligerDialog.warn('对应关系已存在');
						 return;
					 }
				 }
	   			data3.push({
	   				inv_id : data[i].inv_id,
					inv_code:data[i].inv_code,
					inv_name:data[i].inv_name,
					goodsid:data2[k].goodsid,
					goodsname:data2[k].goodsname
				 });
	   		}
		 }
	   	 
	   
		 //var str = "[{\"Rows\":"+JSON.stringify(data3)+",\"Total\":"+total+"}]";
   	 	 gridTotal.addRows(data3);
   	 	
    }
    </script>
  
  </head>
      
<body>   
<div id="layout1">
       <div position="center" style="width:1010px">
	        <div style=" margin-left:30px;  padding: 0; float: left;width:470px;">
	        		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	           <tr>
	               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HRP材料：</td>
	               <td align="left" class="l-table-edit-td"><input name="inv_id" type="text" id="inv_id" ltype="text" validate="{required:true,maxlength:20}"  /></td>
	               <td align="left"></td>
	               <td align="left"><input class="l-button l-button-test" style="float: right;" type="button" value="查询" onclick="f_gridcharge_loadData()"/></td>
	           </tr> 
	     		</table>
			<div  id="maingrid1"  ></div>
		</div>
		
		<div style="margin-left:30px;     padding: 0; float: right;width:470px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">省平台材料：</td>
	                <td align="left" class="l-table-edit-td"><input name="goodsid" type="text" id="goodsid" ltype="text" validate="{required:true,maxlength:20}" /></td>
	                <td align="left"></td>
	                <td align="left"><input class="l-button l-button-test" style="float: right;" type="button" value="查询" onclick="f_gridtitle_loadData();"/></td>
	            </tr> 
        	</table>
			<div  id="maingrid2"></div>
		</div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="addData"><b>添加</b></button>
				</td>
			</tr>
		</table>
     </div>  
     <div position="centerbottom" >
        <div style="margin-left: 30px;padding: 0; ">
			<div id="maingrid3"></div>
		</div>
     </div>  
</div> 
    </body>
</html>
