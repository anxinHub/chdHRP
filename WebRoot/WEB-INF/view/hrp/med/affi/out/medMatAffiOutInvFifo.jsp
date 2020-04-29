<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE>
<html style="overflow:hidden;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"></script>
	<script type="text/javascript">
	var grid;   
    var gridManager = null;
    var clicked = 0;   
    var gridFifo = null;  
    var dialog = frameElement.dialog;    
    var store_id = '${store_id}'   
     
    $(function (){	 
    	
       loadDict();
       loadHead(null);	 
    });  
   
    function checkMedOutFifo(){
    	//根据选择回冲数据
    	//清空父页面表格
    	//获取明细数据
    	if(gridFifo ==null){//没有点击加号的情况下
    		
    		var row = gridManager.getSelectedRow();
    		if(typeof(row.amount) == "undefined" || row.amount == "" || row.amount == 0){
   				$.ligerDialog.warn("药品名称对应：["+row.inv_name+"]对应数量为空 请输入\n");		
   				return false;
		  	}
    		
    		var para={
    				flag : 'fifo',//先进先出标志
    				amount : row.amount,
    				inv_id : row.inv_id,
    				inv_no : row.inv_no,
    				store_id : store_id.split(",")[0],
    				store_no : store_id.split(",")[1]
             };
    		ajaxJsonObjectByUrl("queryMedAffiOutByFifoBalance.do?isCheck=false",para,function(responseData){
				var detail_rows = new StringBuffer();
            	detail_rows.append("[");
            	$.each(responseData.Rows, function(d_index, d_content){ 
            		if(d_index != 0){detail_rows.append(",");}
            		
            		detail_rows.append('{"inv_id":"').append(d_content.inv_id).append('",');
            		detail_rows.append('"group_id":"').append(d_content.group_id).append('",');
            		detail_rows.append('"hos_id":"').append(d_content.hos_id).append('",');
            		detail_rows.append('"copy_code":"').append(d_content.copy_code).append('",');
            		detail_rows.append('"inv_no":"').append(d_content.inv_no).append('",');
            		detail_rows.append('"inv_code":"').append(d_content.inv_code).append('",');
            		detail_rows.append('"inv_name":"').append(d_content.inv_name).append('",');
            		detail_rows.append('"inv_model":"').append(d_content.inv_model).append('",');
            		detail_rows.append('"unit_code":"').append(d_content.unit_code).append('",');
            		detail_rows.append('"unit_name":"').append(d_content.unit_name).append('",');
            		detail_rows.append('"price":"').append(d_content.price).append('",');
            		detail_rows.append('"amount":"').append(d_content.amount).append('",');
            		detail_rows.append('"amount_money":').append(d_content.price*d_content.amount).append(',');
            		detail_rows.append('"sell_price":"').append(d_content.sell_price).append('",');
            		detail_rows.append('"batch_sn":"').append(d_content.batch_sn).append('",');         		
            		detail_rows.append('"batch_no":"').append(d_content.batch_no).append('",');
            		detail_rows.append('"bar_code":"').append(d_content.bar_code).append('",');
            		detail_rows.append('"location_id":"').append(d_content.location_id).append('",');
            		detail_rows.append('"location_code":"').append(d_content.location_code).append('",');
            		detail_rows.append('"location_name":"').append(d_content.location_name).append('",');
            		detail_rows.append('"inva_date":"').append(d_content.inva_date).append('",');
            		detail_rows.append('"left_amount":"').append(d_content.left_amount).append('",');
            		detail_rows.append('"left_money":"').append(d_content.left_money).append('",');
            		detail_rows.append('"instant_amount":"').append(d_content.instant_amount).append('",');
            		detail_rows.append('"instant_amount_money":"').append(d_content.instant_amount_money).append('",');
            		
            		detail_rows.append('"sell_money":').append(d_content.sell_price*d_content.amount).append('}');
            		
            	});
            	detail_rows.append("]");
            	//var p_data = parent.gridManager.rows;//获取父页面数据
            	//parent.grid.deleteRange(p_data);//删除父页面数据
            	parent.grid.addRows(eval(detail_rows.toString()));//添加上行数据
            	if(parent.$("#is_dir").val() == '1'){
            		parent.$("#is_dir").val('1');
            	}else{
            		parent.$("#is_dir").val('0');
            	}
            	
            	dialog.close();
    		});
    		
    	}else{//点击加号的情况下 情况2
    		
    		var detail_fifo_data = gridFifo.getCheckedRows();
    	
    		if(detail_fifo_data != null && detail_fifo_data.length > 0){
    	    	var validate_detail_buf = new StringBuffer();    	 		
    	    	$.each(detail_fifo_data, function(d_index, d_content){ 
	 	      		if(typeof(d_content.amount) == "undefined" || d_content.amount == ""){	       				
	 	      				validate_detail_buf.append("药品名称对应：["+d_content.inv_name+"]对应数量为空 请输入\n");	       				
	       		  	}
	 	      		
	 	      		if(d_content.amount >d_content.left_amount){ 	      			
	 	      			validate_detail_buf.append("药品名称对应：["+d_content.inv_name+"]对应出库数量大于库存数量 请重新输入\n"); 	      			
	 	      		}
				}); 

    	 		if(validate_detail_buf.toString()  != ""){  	 			
    	 			$.ligerDialog.warn(validate_detail_buf.toString());   	 			
    	 			return false;
    	 		}
    	 		
				var detail_rows = new StringBuffer();           	
            	detail_rows.append("[");           	
            	$.each(detail_fifo_data, function(d_index, d_content){ 

            		if(d_index != 0){detail_rows.append(",");}
            		
            		detail_rows.append('{"inv_id":"').append(d_content.inv_id).append('",');
            		detail_rows.append('"group_id":"').append(d_content.group_id).append('",');
            		detail_rows.append('"hos_id":"').append(d_content.hos_id).append('",');
            		detail_rows.append('"copy_code":"').append(d_content.copy_code).append('",');
            		detail_rows.append('"inv_no":"').append(d_content.inv_no).append('",');
            		detail_rows.append('"inv_code":"').append(d_content.inv_code).append('",');
            		detail_rows.append('"inv_name":"').append(d_content.inv_name).append('",');
            		detail_rows.append('"inv_model":"').append(d_content.inv_model).append('",');
            		detail_rows.append('"unit_code":"').append(d_content.unit_code).append('",');
            		detail_rows.append('"unit_name":"').append(d_content.unit_name).append('",');
            		detail_rows.append('"price":"').append(d_content.price).append('",');
            		detail_rows.append('"amount":"').append(d_content.amount).append('",');
            		detail_rows.append('"amount_money":').append(d_content.price*d_content.amount).append(',');
            		detail_rows.append('"sell_price":"').append(d_content.sell_price).append('",');
            		detail_rows.append('"batch_sn":"').append(d_content.batch_sn).append('",');
            		detail_rows.append('"batch_no":"').append(d_content.batch_no).append('",');
            		detail_rows.append('"bar_code":"').append(d_content.bar_code).append('",');
            		detail_rows.append('"location_id":"').append(d_content.location_id).append('",');
            		detail_rows.append('"location_code":"').append(d_content.location_code).append('",');
            		detail_rows.append('"location_name":"').append(d_content.location_name).append('",');
            		detail_rows.append('"inva_date":"').append(d_content.inva_date).append('",');
            		detail_rows.append('"left_amount":"').append(d_content.left_amount).append('",');
            		detail_rows.append('"left_money":"').append(d_content.left_money).append('",');
            		detail_rows.append('"instant_amount":"').append(d_content.instant_amount).append('",');
            		detail_rows.append('"instant_amount_money":"').append(d_content.instant_amount_money).append('",');
            		
            		detail_rows.append('"sell_money":').append(d_content.sell_price*d_content.amount).append('}');

				}); 
            	detail_rows.append("]");
    	 		//var p_data = parent.gridManager.rows;//获取父页面数据
            	//parent.grid.deleteRange(p_data);//删除父页面数据
            	parent.grid.addRows(eval(detail_rows.toString()));//添加上行数据
            	if(parent.$("#is_dir").val() == '1'){
            		parent.$("#is_dir").val('1');
            	}else{
            		parent.$("#is_dir").val('0');
            	}
            	dialog.close();
    			
    		}else{ //情况 3  点击加号 没有选择数据 则提取主记录 和情况1相同 如有修改 请同时修改情况1
    			var row = gridManager.getSelectedRow();
        		if(typeof(row.amount) == "undefined" || row.amount == "" || row.amount == 0){
       				$.ligerDialog.warn("药品名称对应：["+row.inv_name+"]对应数量为空 请输入\n");		
       				return false;
    		  	}
        		
        		var para={
        				flag : 'fifo',//先进先出标志
        				amount : row.amount,
        				inv_id : row.inv_id,
        				inv_no : row.inv_no,
        				store_id : store_id.split(",")[0],
        				store_no : store_id.split(",")[1]
                 };
        		ajaxJsonObjectByUrl("queryMedAffiOutByFifoBalance.do?isCheck=false",para,function(responseData){
    				var detail_rows = new StringBuffer();
                	detail_rows.append("[");
                	$.each(responseData.Rows, function(d_index, d_content){ 
                		if(d_index != 0){detail_rows.append(",");}
                		
                		detail_rows.append('{"inv_id":"').append(d_content.inv_id).append('",');
                		detail_rows.append('"group_id":"').append(d_content.group_id).append('",');
                		detail_rows.append('"hos_id":"').append(d_content.hos_id).append('",');
                		detail_rows.append('"copy_code":"').append(d_content.copy_code).append('",');
                		detail_rows.append('"inv_no":"').append(d_content.inv_no).append('",');
                		detail_rows.append('"inv_code":"').append(d_content.inv_code).append('",');
                		detail_rows.append('"inv_name":"').append(d_content.inv_name).append('",');
                		detail_rows.append('"inv_model":"').append(d_content.inv_model).append('",');
                		detail_rows.append('"unit_code":"').append(d_content.unit_code).append('",');
                		detail_rows.append('"unit_name":"').append(d_content.unit_name).append('",');
                		detail_rows.append('"price":"').append(d_content.price).append('",');
                		detail_rows.append('"amount":"').append(d_content.amount).append('",');
                		detail_rows.append('"amount_money":').append(d_content.price*d_content.amount).append(',');
                		detail_rows.append('"sell_price":"').append(d_content.sell_price).append('",');
                		detail_rows.append('"batch_sn":"').append(d_content.batch_sn).append('",');         		
                		detail_rows.append('"batch_no":"').append(d_content.batch_no).append('",');
                		detail_rows.append('"bar_code":"').append(d_content.bar_code).append('",');
                		detail_rows.append('"location_id":"').append(d_content.location_id).append('",');
                		detail_rows.append('"location_code":"').append(d_content.location_code).append('",');
                		detail_rows.append('"location_name":"').append(d_content.location_name).append('",');
                		detail_rows.append('"inva_date":"').append(d_content.inva_date).append('",');
                		detail_rows.append('"left_amount":"').append(d_content.left_amount).append('",');
                		detail_rows.append('"left_money":"').append(d_content.left_money).append('",');
                		detail_rows.append('"instant_amount":"').append(d_content.instant_amount).append('",');
                		detail_rows.append('"instant_amount_money":"').append(d_content.instant_amount_money).append('",');
                		
                		detail_rows.append('"sell_money":').append(d_content.sell_price*d_content.amount).append('}');
                		
                	});
                	detail_rows.append("]");
                	//var p_data = parent.gridManager.rows;//获取父页面数据
                	//parent.grid.deleteRange(p_data);//删除父页面数据
                	parent.grid.addRows(eval(detail_rows.toString()));//添加上行数据
                	if(parent.$("#is_dir").val() == '1'){
                		parent.$("#is_dir").val('1');
                	}else{
                		parent.$("#is_dir").val('0');
                	}
                	dialog.close();
        		});	
    		}	
    	}
   }
    function loadDict(){
        //字典下拉框
    	autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",false,'${store_id}','180');   	
    	autocomplete("#inv_id", "../../queryMedInvDict.do?isCheck=false", "id", "text", true, true,{is_com : '1'},false,false,'280');   	
    	$("#store_id").ligerComboBox({disabled: true});
    
     } 
  
    function loadHead(){
    	var store_id = '${store_id}';
    	var para = {
				store_id : store_id.split(",")[0],
				store_no : store_id.split(",")[1]
		};
    	grid = $("#maingrid").ligerGrid({
           columns: [
				{ display: '药品编码', name: 'inv_code', width: 100},
                { display: '药品名称', name: 'inv_name', width: 140},
                { display: '规格型号', name: 'inv_model', width: 160},
                { display: '计量单位', name: 'unit_name', width: 50},
                { display: '是否收费', name: 'is_charge', width: 50,
               	 render: function (rowdata, rowindex, value){
            		 if(rowdata.is_charge == '1'){
              			 return "是";
              		 }else{
              			 return "否";
              		 }
		           } },
                { display: '生产厂商', name: 'fac_name', width: 160},
                { display: '库存', name: 'left_amount', width: 50},
                { display: '即时库存', name: 'instant_amount', width: 50,
                  	 render: function (rowdata, rowindex, value){
                		 if(typeof(rowdata.instant_amount) == "undefined"){
                  			 return "未出库";
                  		 }else{
                  			 return rowdata.instant_amount;
                  		 }
    		           } },
                { display: '出库数量', name: 'amount', width: 80,editor : {type : 'int'}}
				], 
				 
				checkbox: false,dataAction : 'server',dataType : 'server',usePager : true,
				url:'queryMedAffiOutByInvHold.do?isCheck=false',enabledEdit : true,fixedCellHeight:true,
				isScroll : true,selectRowButtonOnly:true,isSingleCheck:true,heightDiff: 10,
                width: '95%', height: '100%', 
                detail: { onShowDetail: f_showFifoBalance},
                parms :para,
                toolbar: { 
                	items: [
				            { text: '查询', id:'query', click: query,icon:'search' }
					]}
                });

        gridManager = $("#maingrid").ligerGetGridManager();
    	
    }

	 function f_showFifoBalance(row, detailPanel,callback){
		 
		 var para = {
				inv_id : row.inv_id,
				inv_no : row.inv_no,
 				store_id : store_id.split(",")[0],
				store_no : store_id.split(",")[1]
		};
		 
         var eleFifo = document.createElement('div'); 
         $(detailPanel).append(eleFifo);
         gridFifo = $(eleFifo).css('margin',1).ligerGrid({
             columns:
                         [
                         { display: '药品编码', name: 'inv_code',width: 100},
                         { display: '药品名称', name: 'inv_name',width: 180},
                         { display: '批号', name: 'batch_no',width: 100 },
                         { display: '批次', name: 'batch_sn' ,width: 100},
                         { display: '条形码', name: 'bar_code' ,width: 100},
                         { display: '单价', name: 'price' ,width: 50},
                         { display: '库存', name: 'left_amount' ,width: 50},
                         { display: '即时库存', name: 'instant_amount' ,width: 50,
                          	 render: function (rowdata, rowindex, value){
                        		 if(typeof(rowdata.instant_amount) == "undefined"){
                          			 return "未出库";
                          		 }else{
                          			 return rowdata.instant_amount;
                          		 }
            		           } },
                         { display: '出库数量', name: 'amount' ,width: 50,editor : {type : 'int'}}
                         ], 
						dataAction : 'server',dataType : 'server',usePager : false,checkbox: true,
			 			parms :para,
			 			url : 'queryMedAffiOutByFifoBalance.do?isCheck=false',enabledEdit : true,fixedCellHeight:true,
			 			width: '100%',height : '90%',
			 			frozen:false
         });  
     }
	//查询
	 function  query(){
	    	grid.options.parms=[];
			grid.options.newPage=1;
	        //根据表字段进行添加查询条件
			var store_id = liger.get("store_id").getValue();

	        if(store_id){
	        	grid.options.parms.push({name:'store_id',value : store_id.split(",")[0]}); 
	        	grid.options.parms.push({name:'store_no',value : store_id.split(",")[1]}); 
	        }
	        var inv_id = liger.get("inv_id").getValue();
	        if(inv_id){
	        	grid.options.parms.push({name:'inv_id',value : inv_id.split(",")[0]}); 
	        	grid.options.parms.push({name:'inv_no',value : inv_id.split(",")[1]}); 
	        }
	        
	    	//加载查询条件
	    	grid.loadData(grid.where);
			$("#resultPrint > table > tbody").empty();
	  }

    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div position="top">
		<form name="form1" method="post"  id="form1" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
			    <tr>
			        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			        <td align="left" class="l-table-edit-td">
			        	<input name="store_id" type="text" id="store_id" ltype="text" />
			        </td>
			        <td align="left"></td>
			        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品信息：</td>
			        <td align="left" class="l-table-edit-td">
			        	<input name="inv_id" type="text" id="inv_id" ltype="text"/>
			        </td>
			        <td align="left"></td>
			    </tr> 
			</table>
		</form>
	</div>
	<div position="center" >
		<div id="maingrid"></div>
	</div>
</body>
</html>
