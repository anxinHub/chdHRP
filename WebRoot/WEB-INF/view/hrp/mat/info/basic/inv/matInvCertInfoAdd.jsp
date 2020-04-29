<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
   	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridSup;
     var gridManager = null;
     var gridManagerSup = null;
     var cert_id ;
     
     var supData;
     
     var supMap = new HashMap(null);
     
     $(function (){
		
		 $("#layout1").ligerLayout({
             centerBottomHeight:300
         });
		 
		 loadHead();
		 
		 //loadHeadSup();
     });
     
     //查询
     function  query(){
     		grid.options.parms=[];
     		grid.options.newPage=1;
         //根据表字段进行添加查询条件
     	  grid.options.parms.push({name:'inv_id',value : '${inv_id}'}); 

     	//加载查询条件
     	grid.loadData(grid.where);
      }
     
    function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ { 
            	display: '证件号', name: 'cert_code', align: 'left', width:260,editor : {type : 'string'}
			}, { 
				display: '证件产品名称', name: 'cert_inv_name', align: 'left', width:260,editor : {type : 'string'}
			}, { 
				display: '证件类型ID', name: 'type_id', align: 'left',hide:true
			}, { 
				display: '证件类型', name: 'type_name', align: 'left', width:260, valueField : 'id',textField : 'text',
				editor : {	
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../../qryMatInvCertType.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
					onSelected:function(value,text){
						if(value && text)
						grid.updateCell("type_id", value.split(",")[0], rowindex_id);
					}
				} 
			}, { 
				display: '起始日期', name: 'start_date', align: 'left',width:105,type: 'date', 
				format: 'yyyy-MM-dd',editor: {type: 'date'}
			}, { 
				display: '截止日期', name: 'end_date', align: 'left',width:105,type: 'date', 
				format: 'yyyy-MM-dd',editor: {type: 'date'}
			},  { 
				display: '生产厂商', name: 'fac_id', align: 'left', width:250,textField : 'fac_name',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../../queryHosFac.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				} 
			},{ 
				display: '供应商', name: 'sup_id', align: 'left', width:250,textField : 'sup_name',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../../queryHosSup.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				} 
			},{ 
				display: '状态', name: 'cert_state', align: 'left',width:100,
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					data: [{
						"id" : "1",
						"text" : "在用"
					}, {
						"id" : "0",
						"text" : "停用"
					}],
 	    							
					keySupport : true,
					autocomplete : true
				} ,
				render:function(rowdate,rowindex,value){
					if(value == 0){
						rowdate.cert_state = "0";
						return "停用";
					}else if(value == 1){
						return "在用";
					}
				}
			} ],
			dataAction: 'server',dataType: 'server',usePager:true,
			width: '100%', height: '100%', checkbox:true,rownumbers:true,enabledEdit : true,isAddRow:false ,
			selectRowButtonOnly:true,heightDiff: 0 ,isSingleCheck:true,
			onBeforeEdit : f_onBeforeEdit, 
			//onSelectRow : addSupData ,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
				{ line:true },
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
				{ line:true },
				{ text: '添加行', id:'new', click: addRow,icon:'add' },
			]} 
     	});

         gridManager = $("#maingrid").ligerGetGridManager();
     }	
/*      function loadHeadSup(){
       	 gridSup = $("#maingridSup").ligerGrid({
                columns: [
                    { display: '供应商',name: 'sup_id', align: 'left', textField : 'id',width: 200,
				    	editor : {
							type : 'select',
							valueField : 'id',
							//textField : 'text',
							url : '../../../queryHosSup.do?isCheck=false',
							keySupport : true,
							autocomplete : true,
							onSelected:function(value,text){
								gridSup.updateRow(rowindex_id,{
									sup_id : value,
									sup_code:text.split(" ")[0],
				   					sup_name:text.split(" ")[1],
				   				});
								gridSup.endEdit(rowindex_id);
							}
							
						} 
				   },
				   { display: '供应商编码', name: 'sup_code', align: 'left', width: 200},
				   { display: '供应商名称', name: 'sup_name', align: 'left'}
					],
                    dataAction: 'server',dataType: 'server',usePager:true,isAddRow:false ,
                    width: '100%', height: '100%', checkbox : true,
        			enabledEdit : true, rownumbers : true,
        			delayLoad : true,//初始化不加载数据
        			//url:'../cert/invcert/queryMatCerSup.do?isCheck=false',
        			//data: supData,
        			onBeforeEdit : f_onBeforeEdit,
        			onAfterEdit : getCertSupDate,
      			  	selectRowButtonOnly : true,//heightDiff: -10,
                    toolbar: { items: [
                   			{ text: '删除', id:'delete', click: removeRow,icon:'delete' },
                   			{ line:true },
                   			{ text: '添加行', id:'new', click: addRowSup,icon:'add' },
                   			{ line:true },
			       	]}
                  });

             gridManagerSup = $("#maingridSup").ligerGetGridManager();
      } */
     
     //选中行加载 证件对应的供应商
     function addSupData(rowdata){
    	 if(rowdata.supData){
    		 gridSup.loadData(rowdata.supData);
    	 }else{
    		 gridSup.deleteAllRows(); 
    	 }
    	
    	 
     }
     
     var rowindex_id = "";
	 var column_name="";
	 function f_onBeforeEdit(e) {
		 rowindex_id = e.rowindex;
		 column_name=e.column.name;
	 }
	 
	 
	 function getCertSupDate(){
		 //
		 var dataCert = grid.getSelectedRows();
		 
		 var data = gridManager.getSelected();
		 
		 var dataSup = gridManagerSup.getData();
		 
		 if(dataCert.length != 1){
			 
			 $.ligerDialog.error("请选择上面表格一行数据再操作");
			 
		 }else{
			 var msg = "";
			 var map = new HashMap();
			 $.each(dataSup,function(i, v){
				var rows = 0;
	   			if(v.sup_id){
	 	 			var key=v.sup_id ;
	 	 			var value=v.sup_code;
	 	 			if(map.get(key)== null || map.get(key) == 'undefined' || map.get(key) == ""){
	 	 				map.put(key ,value);
	 	 			}else{
	 	 				msg += "供应商:"+v.sup_code+"重复！" + "\n\r";
	 	 			}
	 	 			rows = rows + 1;
	   			}
	   			if(msg != ""){
		   			$.ligerDialog.warn(msg);
		  			return false;
	   			}else{
	   				return true;
	   			}
			 })
			 
			 if(dataSup.length>0){
				 
				 data.supData={"Rows":dataSup,"Total":dataSup.length};
			 } 
		 }
		 
	 }
	 
	 function deleteRow(){
		 
	     gridManager.deleteSelectedRow();
	 }
	 
     function removeRow(){
    	 
    	 var deleteRows = gridSup.getSelectedRows();
     	
     	gridSup.removeRange(deleteRows);
     	
     	var dataCert = grid.getSelectedRows();
		 
		 var data = gridManager.getSelected();
		 
		 
		 if(dataCert.length != 1){
			 
			 $.ligerDialog.error("请选择上面表格一行数据再操作");
			 
		 }else{
			 
			var dataSup = gridSup.getData(); 
			
 			if(dataSup.length>0){
				 
				 data.supData={"Rows":dataSup,"Total":dataSup.length};
			 }else{
				 
				 data.supData={"Rows":null,"Total":0};
			 } 
		 }
		 
     	
     	
     }
     
     function addRow(){
    	 	grid.addRowEdited({
				cert_code: '' ,
             	type_id: '' ,
             	type_name: '' ,
             	start_date: '' ,
             	end_date: '' ,
             	fac_id: '' ,
             	sup_id: '' ,
				cert_state:"1"});
     }
	
     function addRowSup(){
		 var dataCert = grid.getSelectedRows();
		 
		 if(dataCert.length != 1){
			 $.ligerDialog.error("请选择上面表格一行数据再操作");
		 }else{
			 gridSup.addRow();
		 }
 	 	
  	}
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	 function validateGrid(){
	    	 //明细
	   		var msg="";
	   		var rowm = "";
	   		var rows = 0;
	   		var data = gridManager.getData();
	   		//判断grid 中的数据是否重复或者为空
	   		var targetMap = new HashMap();
	   		$.each(data,function(i, v){
	   			if (!v.cert_code) {
					rowm+="[证件编码]、";
				}
	   			if (!v.type_name) {
					rowm+="[证件类型]、";
				}  
	   			if (!v.start_date) {
					rowm+="[起始日期]、";
				}  
	   			if (!v.end_date) {
					rowm+="[截止日期]、";
				} 
	   			if (v.cert_state == null || v.cert_state == "" || v.cert_state == "undefined") {
					rowm+="[状态]、";
				}  
				if(rowm != ""){
					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
				}
				msg += rowm;
	   			rowm = "";
	   			if(v.cert_code){
	 	 			msg += rowm;
	 	 			var key=v.cert_code ;
	 	 			var value="第"+(i+1)+"行";
	 	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
	 	 				targetMap.put(key ,value);
	 	 			}else{

	 	 				msg += v.inv_code +"证件编码重复！" + "\n\r";
	 	 			}
	 	 			rows = rows + 1;
	   			}
	   		});

	   		if(msg != ""){
	   			$.ligerDialog.warn(msg);
	  			return false;
	   		}
	   		return true;
	     }
	
	function save(){
		var data = gridManager.getData();
		//var updataPageSup = parentFrameUse().grid.getData();
		if(!validateGrid(data)){
   		 	return false;
   	 	}else{
   	 		/* //除去 重复供应商信息
   	 		$(data).each(function(){
   	 			var supInfo = this.supData.Rows ;
   	 			$(supInfo).each(function(){
   	 				supMap.put(this.sup_id,this);
   	 			})
   	 		})
   	 		
   	 		// 除去 材料添加页面 与 证件信息页面的 重复供应商信息 用Map
   	 		var updataPageSupMap = new HashMap();
   	 		if(updataPageSup.length > 0){
	   	 		$(updataPageSup).each(function(){
	   	 			updataPageSupMap.put(this.sup_id,this);
	 			})
   	 		}
   	 		var supData=[];
   	 		
   	 		for(var i in supMap.keySet()){
	   	 		if( !updataPageSupMap.get(supMap.keySet()[i])){
	 				supData.push(supMap.get(supMap.keySet()[i]));
	 			}
   	 		} */
   	 		
   	 		var formPara={
   	 			allData: data.length==0?"":JSON.stringify(data)
    	    };
			
   	        ajaxJsonObjectByUrl("addCertSup.do?isCheck=false",formPara,function(responseData){
   	        	if(responseData.state == 'true'){
   	        		/* parentFrameUse().certIds = responseData.certIds;
   	        		parentFrameUse().grid.addRows(supData); */
   	        	}
   	        })
   	 	}
		
	}
</script>
</head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="layout1" style="height: 100%">
		<div position="center" id="maingrid" ></div>
		<!-- <div  position="centerbottom" id="maingridSup"></div> -->
	</div>
    </body>
</html>
