<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<% 
String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%-- <jsp:include page="${path}/inc.jsp"/> --%>
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <style> 
  .white_content {  display: none;  position: absolute;  top: 50%;  left: 60%;  width: 180px;  height: 180px; z-index:9999;  padding: 16px;  border: 2px solid #AECAF0;  background-color: white;   overflow: auto;  }  
  </style> 
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var clicked = 0;
     var selectData = "";
     var grid;
     var gridManager = null;
     var matPurMap = new HashMap(); 
     $(function (){
         loadDict();//加载下拉框
        //loadForm();
        loadHead(null);//加载数据
        
        //默认选中自购计划,不显示采购单位、请购单位、付款单位
        var planType = $("input[type='radio']:checked").val();
        singleSel();
        
        if(liger.get("is_dir").getValue()=='1'){
			$(".dept").attr("style","visibility:true");
		}else{
			$(".dept").attr("style","visibility:hidden");
		}
		
		$('#is_dir').bind("change",function(){
			if(liger.get("is_dir").getValue()=='1'){
				$(".dept").attr("style","visibility:true");
				grid.columns[13].editor = null;
			}else{
				$(".dept").attr("style","visibility:hidden");
				liger.get("dir_dept_id").setValue("");
				liger.get("dir_dept_id").setText("");
			}
		});
		$('#dir_dept_id').bind("change",function(){
			if(liger.get("dir_dept_id").getValue() != ""){
				grid.columns[13].editor = null;
				grid.columns[13].render = function(rowdata, rowindex, value) {
					rowdata.app_dept_id = liger.get("dir_dept_id").getValue().split(",")[0];
					rowdata.app_dept_no = liger.get("dir_dept_id").getValue().split(",")[1];
					rowdata.app_dept_name = liger.get("dir_dept_id").getText();
					return rowdata.text;
				};
			}else{
				grid.columns[13].editor = {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryHosDept.do?isCheck=false&is_last=1',
					keySupport : true,
					autocomplete : true
				};
				grid.columns[13].render = null;
			}
			//loadHead();
	    	grid.reRender();
		 });
	
     });  
	  function add_rows(data){
	    	//先清除数据然后再添加
	    	delete_allRows();
	    	grid.addRows(data);
	  }
	  
     //隐藏或显示  采购单位、请购单位、付款单位
     function singleSel(){
    	 if($("input[type='radio']:checked").val() == '1'){
    		 $('#hos_name').hide();
    		 //统购计划中，请购单位为当前单位，并且不可编辑
    		 liger.get("req_hos_id").setValue("${req_hos_id}");
    		 liger.get("req_hos_id").setText("${req_hos_code} ${req_hos_name}");
    		 $("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
    		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"0",'160');//是否定向
			 changeDir();
    	 }else{
    		 $('#hos_name').show();
    		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"1",'160');//是否定向
			 changeDir();
    	 }
     }
     //选择定向与非定向是否显示定向科室
     function changeDir(){
		 if(liger.get("is_dir").getValue()=='1'){
			 $(".dept").attr("style","visibility:true");
		 }else{
			 $(".dept").attr("style","visibility:hidden");
		 }
	 }
     function loadHead(){
    	 var loading_onoff = true;
    	 grid = $("#maingrid").ligerGrid({
             columns: [ 
                       { display: '交易编码', name: 'bid_code', align: 'left',width : 150,
                    	   totalSummary: {align: 'right',render: 
                    		    function (suminf, column, cell) {
       								return '<div>合计：</div>';
       							}
       					   }
       			       }, 
       			       { display: '材料编码', name: 'inv_code', align: 'left',width : 150, },
                       { display: '材料名称(E)',name: 'inv_name',align: 'left',width : 150,
  					     textField : 'inv_name',valueField : 'inv_name',
  					     editor :{
								type : 'select',textField : 'inv_name',valueField : 'inv_name',
								selectBoxWidth : "80%",
								selectBoxHeight : 240,
								grid : {
									columns : [ 
												{display : '交易编码',name : 'bid_code',align : 'left'}, 
									            {display : '材料编码',name : 'inv_code',align : 'left'}, 
									            {display : '材料名称',name : 'inv_name',align : 'left'}, 
									            {display : '规格型号',name : 'inv_model',align : 'left'}, 
									            {display : '计量单位',name : 'unit_name',align : 'left'}, 
									            {display : '当前库存',name : 'cur_amount',align : 'left'}, 
									            {display : '供应商',name : 'sup_name',align : 'left'}, 
									            {display : '生产厂商',name : 'fac_name',align : 'left'}, 
									            {display : '参考单价',name : 'price',align : 'left'}  
									          ],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									onAfterEdit : f_onAfterEdit,
									gid: 'invGrid', 
									url : '../../queryMatInvListByPur.do?isCheck=false',
									//delayLoad:true,
									usePager:true,pageSize : 200,
									onGridClick:function (){           // 为了 点击时不走success的条件(特意加的事件)
										loading_onoff = true;
									},
									onSuccess: function (data, g) { //加载完成时默认选中
										if (loading_onoff && grid.editor.editParm) {
											var editor = grid.editor.editParm.record;
											var item = data.Rows.map(function (v, i) {
												return v.inv_name;
											});
											var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
											loading_onoff = false;
											//加载完执行
											setTimeout(function () {
												g.select(data.Rows[index]);
											}, 80);
										}else if(!loading_onoff){
											return false;
										}
									}
			  					},
								keySupport : true,autocomplete : true,
								onSuccess : function() {
									this.parent("tr").next(".l-grid-row").find("td:first").focus();
								},
								onChangeValueImmediately:function (str){
									loading_onoff = true;
								},
								ontextBoxKeyEnter: function (data) {
									f_onSelectRow_detail(data.rowdata);
								}
  					     	}
  					   },
                       { display: '规格型号', name: 'inv_model', align: 'left',width : 150},
  					   { display: '计量单位(E)', name: 'unit_name', align: 'left',minWidth : 80},
				  	   { display: '当前库存', name: 'cur_amount', align: 'right',minWidth : 80, 
				  			render : function(rowdata, rowindex, value) {
				  				return formatNumber(value, 2, 0);
							}
					  	},
					  // { display: '计划数量', name: 'req_amount', align: 'right',minWidth : 80},
						{display: '建议采购量', name: 'advise_num', align: 'right',width : 90, 
							render : function(rowdata, rowindex, value) {
				  				return formatNumber(value, 2, 0);
							}
						},
					   { display: '采购数量(E)', name: 'amount', align: 'right',minWidth : 80,type: 'float', editor: { type: 'float'},
							totalSummary: {align: 'right',render: 
								function (suminf, column, cell) {
									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
								}
							}
					   },
				       { display: '单价(E)', name: 'price', align: 'right',minWidth : 80,type: 'number',editor : {
						 type : 'numberbox',precision : '${p04006 }'},render : 
							 function(rowdata, rowindex, value) {
								rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
								return value == null ? "" : formatNumber(value, '${p04006 }', 1);
						 	}
					   },
					   { display: '金额', name: 'amount_money', align: 'right',minWidth : 80,render:
						   function(rowdata){
						   		return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money,2,1);
					       },
						   totalSummary: {align: 'right',render: 
							   function (suminf, column, cell) {
									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
							   }}
					   },
					   { display: '申请科室', name: 'app_dept_id', align: 'left',
						   textField : 'app_dept_name' , minWidth : 150,
						   valueField : 'app_dept_name',
						   editor : {type : 'select',valueField : 'id',textField : 'text',selectBoxWidth : 250,
								url : '../../queryHosDeptDict.do?isCheck=false&is_last=1',
								selectBoxHeight : 240,keySupport : true,autocomplete : true,onSelected :
									function (data){	
										if(typeof (data) === "string"){
											var formPara="";
											formPara = {												 			
												app_dept_id : data.split(",")[0],
												app_dept_no : data.split(",")[1]
											}			 	
										}
									}
							},
	  						keySupport : true,autocomplete : true,
							onSuccess : function() {
								this.parent("tr").next(".l-grid-row").find("td:first").focus();
							}
					   },
					   { display: '申请日期', name: 'app_date', align: 'left',type: 'date', align: 'left', 
						 format: 'yyyy-MM-dd', minWidth: 100,editor: {type: 'date',showSelectBox:false},
					   },
					   /* /CHD-HRP/src/com/chd/hrp/sys/controller/SupController.java
					   /CHD-HRP/WebRoot/WEB-INF/view/hrp/mat/purchase/make/matPurMainAdd.jsp
					   /hrp/mat/queryHosSupDict */
						{display : '供应商',name : 'sup_id',align : 'left',width : 250,textField : 'sup_name',
							editor : {
								/* url : '../../../sys/queryHosSupDict.do?isCheck=false', */
								url : '../../queryHosSupDictDisable.do?isCheck=false',
								type : 'select',textField : 'text',valueField : 'id',selectBoxWidth : 250,selectBoxHeight : 240,
								onSelectRow : f_onSelectRow_Sup,
								autocomplete: true,
								onSelected : function (data){	
									if(typeof (data) === "string"){
										var formPara="";
										formPara = {												 			
											sup_id : data.split(",")[0],
											sup_no : data.split(",")[1]
										}			 	
										ajaxJsonObjectByUrl("../../protocol/matprotocolmain/queryMatProtocolMainPur.do?isCheck=false",formPara,
												function (responseData){				        		
													if(responseData.state=="true"){
										        		var a_price = responseData.price;
										        		matPurMap.put(rowindex_id,a_price);
										        	}else {									        			
										        		matPurMap.put(rowindex_id,0)									        			
										        	}
										        }
										);
									}
						        }
												
							}, render : function(rowdata, rowindex, value) {
		  								return rowdata.sup_name;
		  							}
									
						}, 
					   { display: '生产产商', name: 'fac_name', align: 'left',width : 200},
					   { display: '备注(E)', name: 'memo', align: 'left',width : 150,type: 'string', editor: { type: 'string'}},
					   { display: '包装数量(E)', name: 'num', align: 'right',minWidth : 80,type: 'number', editor: { type: 'number'}},
		  			   { display: '包装单位(E)', name: 'pack_code', align: 'right',minWidth : 80,textField : 'pack_name',align : 'left',
					   		editor : {
								type : 'select',valueField : 'id',textField : 'text',
								url : '../../queryMatHosPackage.do?isCheck=false',
								keySupport : true,autocomplete : true,
							},
							render : function(rowdata, rowindex, value) {
								return rowdata.pack_name;
							}
					   },
					   { display: '转换量(E)', name: 'num_exchange', align: 'right',minWidth : 80,type: 'int', editor: { type: 'int'}}
                       ],
                       dataAction: 'server',dataType: 'server',usePager:false,
                       width: '100%', height: '100%', checkbox: true,rownumbers:true,
                       enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                       onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
                       isScroll : true,
                       selectRowButtonOnly:true,//heightDiff: -10,
                       toolbar: { 
                    	   items: [
                       			{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
                       			{ line:true },
                       			{ text: '保存', id:'save', click: save,icon:'save' },
                       			{ line:true },
                       		 	{ text: '安全库存导入', id:'security', click: security, icon:'up' },
                       		 	{ line:true },
                       			{ text: '关闭', id:'close', click: this_close,icon:'close' }
  				       		]}
                   });
		  
          gridManager = $("#maingrid").ligerGetGridManager();
     }
     function btn_saveColumn(){
 		
		   var path = window.location.pathname+"/maingrid";
		   var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		   saveColHeader({
			   grid:grid,
			   path:path,
			   url:url,
			   callback:function(data){
				   $.ligerDialog.success("保存成功");
			   }
		   });
	  
	   return false;
}
     //安全库存导入
     function security(){ 
         $.ligerDialog.open({
        	url: 'matDeptRequriedSafePage.do?isCheck=false',
        	height: 500,top:1,width: 950, title:'安全库存导入',
        	modal:true,showToggle:false,showMax:true,showMin: false,isResize:false 
         }); 
     }
     
  	 //删除行集合
 	function deleteRange(data){
 		grid.deleteRange(data);
 	}
   
 	//增加行数据
	function add_rows(data){
		//清空grid
 		grid.deleteAllRows();
		//新增行
		grid.addRows(data);
	}
 	
 	//增加行
 	function is_addRow() {
 		setTimeout(function() { //当数据为空时 默认新增一行
 			grid.addRow();
 		}, 1000);
 	}
     
     function f_onAfterEdit(e) {
    	if(e.value != "" && e.value != 0){
 			if (e.column.name == "amount"){
 				//自动计算金额
 				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
 					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
 				}
 				//自动计算包装件数
 				if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
 					grid.updateCell('num', e.value / e.record.num_exchange, e.rowindex);
 				}
 				if(e.record.num != undefined && e.record.num != "" && e.record.num != 0){
 					grid.updateCell('num_exchange', e.value / e.record.num, e.rowindex);
 				}
 			}else if (e.column.name == "price"){
 				//自动计算金额
 				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
 					grid.updateCell('amount_money', e.value * e.record.amount, e.rowindex);
 				}
 			}else if (e.column.name == "num_exchange"){
 				//自动计算包装件数
 				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
 					grid.updateCell('num', e.record.amount / e.value, e.rowindex);
 				}
 			}else if (e.column.name == "num"){
 				//自动计算数量与金额
 				if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
 					grid.updateCell('amount', e.value * e.record.num_exchange, e.rowindex);
 					if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
 						grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
 					}
 				}
 			}
 			
 		}
 		return true;
 	}
     
     //供应商
     function f_onSelectRow_Sup(data, rowindex, rowobj){
 		selectData = "";
 		selectData = data;
 		if (selectData != "" || selectData != null) {
 			if (column_name == "sup_id") {
 				//alert(data.id)
 			}
 		}
 	}
     
     var rowindex_id = "";
 	 var column_name="";
 	 function f_onBeforeEdit(e) {
 		rowindex_id = e.rowindex;
		clicked = 0;
		column_name=e.column.name;
		/* if(column_name=='sup_id'){
			var sup = grid.getColumnByName("sup_id");
			sup.editor.url='../../queryMatSupByInvId.do?isCheck=false&inv_id='+e.record.inv_id;
		} */
 	 }
 	
 	 //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_name") {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id,{
						bid_code : data.bid_code,
						inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,
						fac_name : data.fac_name,
						cur_amount : data.cur_amount,
						price : data.price,
						inv_id : data.inv_id,
						inv_no : data.inv_no,
						sup_id : data.is_default == undefined?'' : data.sup_id + ',' + data.sup_no,
						sup_name : data.is_default == undefined?'' : data.sup_name,
						pack_code : data.pack_code==undefined?'' : data.pack_code,
						pack_name : data.pack_name==undefined?'' : data.pack_name,
						num_exchange : data.num_exchange==undefined?'' : data.num_exchange
				});
				
				var formPara="";
				 	formPara = {
				 		sup_id:data.sup_id,
				 		sup_no:data.sup_no
			 		}	
				 	
				ajaxJsonObjectByUrl("../../protocol/matprotocolmain/queryMatProtocolMainPur.do?isCheck=false",formPara,function (responseData){
					if(responseData.state=="true"){
	        			var a_price = responseData.price;
	        			matPurMap.put(rowindex_id,a_price);
	        		}else {
	        			matPurMap.put(rowindex_id,0);
	        		}
	        	});
			}else if(column_name == 'pack_code'){
				grid.updateRow(rowindex_id,{
						pack_code:data.id,
						
				});
			}
		}
			return true;
	}
 	 
 	 function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	 }
		
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	
	function this_close(){
		frameElement.dialog.close();
	}
	
	function save() {		
		
		if(liger.get("dept_id").getValue().split(",")[0] == ''){
			$.ligerDialog.error('编制科室不能为空');
	    	return;
		} 
		
		if(liger.get("store_id").getValue().split(",")[0] == ''){
			$.ligerDialog.error('需求库房不能为空');
	    	return;
		} 
			
		if($('#make_date').val() == ''){
			$.ligerDialog.error('编制日期不能为空');
	    	return;
		}
				
		if($("input[type='radio']:checked").val() != '1'){
			if(liger.get("pur_hos_id").getValue().split(",")[0] == '' ){
				$.ligerDialog.error('请填写采购单位');
				return;
			}
				
			if(liger.get("req_hos_id").getValue().split(",")[0] == ''){
				$.ligerDialog.error('请填写请购单位');
				return;
			}
				
			if(liger.get("pay_hos_id").getValue().split(",")[0] == ''){
				$.ligerDialog.error('请填写付款单位');
				return;
			}
		}
		
		if(liger.get("is_dir").getValue()=='1'){
			if(liger.get("dir_dept_id").getValue() == ""){
				$.ligerDialog.error('请填写定向科室 ');
				return;
			}
		}
			
	    var allData = gridManager.getData();
	   
	    var flag = true;
	    var rows = 0 ;
	    var msg = '';
	    if(allData.length != 0){
		    $(allData).each(function(d_index, d_content){
		    var row_index = d_index + 1;
		    if(this.inv_id != undefined && this.inv_id != '' && this.inv_id != null){
		    	rows += 1;
		    	if(!this.inv_name){	
			    	msg += '第' + row_index +'行请选择材料名称<br>';		    			
			    	return flag = false;
			    }
			    		
				if(!this.price){		    			
					msg += '第' + row_index +'行请填写单价<br>';		    			
				    return flag = false;
				}
						 
				if (!this.amount) {						
					msg += '第' + row_index +'行请填写采购数量<br>';						
					return flag = false;
				} 
						
				if (parseFloat(this.amount) <= 0 ) {						
					msg += '第' + row_index +'行数量必须大于零<br>';						
					return flag = false;
				}  
					
						
				if (this.sup_id == "" || this.sup_id == null  || this.sup_id == 'undefined') {  					    
					msg += '第' + row_index +'行请选择供应商<br>';						
					return flag = false;
				}
				var mat_pare = '${p040013 }';
					if(mat_pare == 1){
						if(matPurMap.get(d_index)!= 0){ 
							var price = matPurMap.get(d_index);
							if(this.price > price){
								msg += '第' + row_index +'行采购材料单价大于协议中供应商设定的单价<br>';	
							}
						}  
		    		}
		    }
			}); 
		    
		    
		    if(rows == 0){
		    	$.ligerDialog.warn('请选择材料 ');
		    	return ;
		    }
		    	
		    if(msg != ''){
		    	$.ligerDialog.warn(msg);
		    	return;
		    }
	    	}
	    	
			var formPara = {
				pur_code : $('#pur_code').val(),//计划单号
				dept_id : liger.get("dept_id").getValue().split(",")[0],//编制科室
				dept_no : liger.get("dept_id").getValue().split(",")[1],
				store_id : liger.get("store_id").getValue().split(",")[0],
				store_no : liger.get("store_id").getValue().split(",")[1],
				make_date : $("#make_date").val(),//编制日期
				arrive_date : $("#arrive_date").val(),//计划到货日期
				pur_type : $("input[type='radio']:checked").val(),//计划类型
				brif : $("#brif").val(),//摘要
				pur_hos_id : liger.get("pur_hos_id").getValue().split(",")[0],//采购单位
				req_hos_id : liger.get("req_hos_id").getValue().split(",")[0],//请购单位
				pay_hos_id : liger.get("pay_hos_id").getValue().split(",")[0],//付款单位
				is_dir : liger.get("is_dir").getValue(),
				dir_dept_id : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[0],
				dir_dept_no : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[1],		
				come_from : 1,
				allData : JSON.stringify(allData) //获取所有数据
				
			};
			
			ajaxJsonObjectByUrl("addMatPurMain.do?isCheck=true", formPara,function(responseData) {
				if (responseData.state == "true") {
					$.ligerDialog.confirm('是否继续添加采购计划单？', function (yes){
						if(yes){
							$("input[name='dept_id']").val('');
							$("input[name='make_date']").val('');
							$("input[name='arrive_date']").val('');
							$("input[name='brif']").val('');
							$("input[name='pur_hos_id']").val('');
							$("input[name='req_hos_id']").val('');
							$("input[name='pay_hos_id']").val('');
							//parentFrameUse().query();
							var pur_id = responseData.pur_id;
							location.reload();
						}else{
							//parentFrameUse().openUpdate(responseData.update_para);
							this_close();
						}
					});
				}
			});
		}

		function loadForm() {
			$.metadata.setType("attr", "validate");
			var v = $("form").validate({errorPlacement : 
				function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},success : function(lable) {
					lable.ligerHideTip();
					lable.remove();
				},submitHandler : function() {
					$("form .l-text,.l-textarea").ligerHideTip();
				}
			});
			$("form").ligerForm();
		}
		
		//保存采购计划
		function saveMatPurMain() {		
			save();
		}
					
		function loadDict() {
			//字典下拉框
			autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true,"",false);//编制科室下拉框
			autocomplete("#store_id", "../../queryMatStoreDictPro.do?isCheck=false", "id", "text", true, true,{is_write:'1',is_purchase:'1'});
			autocomplete("#pur_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false);//采购单位下拉框 
			autocomplete("#req_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false);//请购单位下拉框 
			autocomplete("#pay_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false);//付款单位下拉框 
			
			autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
			autocomplete("#dir_dept_id","../../queryMatDeptDictDate.do?isCheck=false","id","text",true,true,{is_last:'1',is_write:'1'},false,false,'160');//定向科室	
			
			autodate("#make_date");
		
			$("#pur_code").ligerTextBox({width:160,disabled:true});
			$("#dept_id").ligerTextBox({width:160});
			$("#make_date").ligerTextBox({width:160});
			$("#brif").ligerTextBox({width:160});
			
			$("#arrive_date").ligerTextBox({width:160});
			$("#pur_hos_id").ligerTextBox({width:160});
			$("#req_hos_id").ligerTextBox({width:160});
			$("#pay_hos_id").ligerTextBox({width:160});
		}
		
		//自动添加行
		function is_addRow() {
			setTimeout(function() { //当数据为空时 默认新增一行
				grid.addRow();
			}, 100);

	    }
		
		//删除选中行
		function deleteRow(){
			gridManager.deleteSelectedRow();
        }
	</script>
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划单号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:true,maxlength:20}" value="自动生成" disabled="disabled"/>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制科室：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="dept_id" type="text" id="dept_id" ltype="text"  validate="{required:true}"/>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>需求库房：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id" ltype="text"  validate="{required:true}"/>
            </td>
            <td align="left"></td>
        </tr>
        
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>计划类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="planA" name="planType" type="radio" value="1" checked="checked" onclick="singleSel()" />自购计划
            	<input id="planB" name="planType" type="radio" value="2" onclick="singleSel()" />统购计划
            </td>
            <td align="left"></td> 
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>编制日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="make_date" type="text" id="make_date" ltype="text"  
            	onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/>
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划到货日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="arrive_date" type="text" id="arrive_date" ltype="text"  
           		 onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/>
             </td>
            <td align="left"></td>
        </tr> 
     	<tr id="hos_name">
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>采购单位：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>请购单位：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="req_hos_id" type="text" id="req_hos_id" ltype="text"  />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>付款单位：</td>
	        <td align="left" class="l-table-edit-td">
	        	<input name="pay_hos_id" type="text" id="pay_hos_id" ltype="text"  />
	        </td>  
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
            <td align="left" class="l-table-edit-td" colspan="2">
            	<textarea class="l-textarea" name="brif" id="brif" rows="3" style="width: 300px;"></textarea> 
            </td>
            
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_dir" type="text" id="is_dir" ltype="text" />
            </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td dept"  style="padding-left:20px;">定向科室：</td>
            <td align="left" class="l-table-edit-td dept" >
            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text" />
            </td>
            <td align="left"></td>
        </tr>
    </table>
    
    <div id="maingrid"></div>
    </form>
   <div id="light" class="white_content"> 
    	<table id="imgt" class="l-table-edit" cellspacing="0" cellpadding="0">
        </table>
    </div> 
    </body>
</html>
