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
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"	type="text/javascript"></script>
    <script type="text/javascript">
	var dataFormat;
	var clicked = 0;
	var selectData = "";
	var grid;
	var gridManager = null;
    var medPurMap = new HashMap(); 
    var useAudit = '${p08086 }'; 
    var state = "${state}";
    var checkEditor = '${come_from}' != 1 ? true : false;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);//加载数据
		
		if('${pur_type}'=='1'){
			$('input:radio:first').attr('checked', 'checked');
			$('#hos_name').hide();
		}else{
			$('input:radio:last').attr('checked', 'checked');
			$('#hos_name').show();
		}
		if(liger.get("is_dir").getValue()=='1'){
			$(".dept").attr("style","visibility:true");
			grid.columns[13].editor = null;
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
					rowdata.app_dept_id = liger.get("dir_dept_id").getValue();
					rowdata.app_dept_name = liger.get("dir_dept_id").getText();
					return rowdata.app_dept_name;
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

	function singleSel(){
   	 if($("input[type='radio']:checked").val() == '1'){
   		 $('#hos_name').hide();
   		 //统购计划中，请购单位为当前单位，并且不可编辑
   		 
   		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"${is_dir}",false,"0",'160');//是否定向
			 changeDir();
   	 }else{
   		 $('#hos_name').show();
   		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"${is_dir}",false,"1",'160');//是否定向
   		 $("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
   		 liger.get("req_hos_id").setValue("${req_hos_id}");
  		 liger.get("req_hos_id").setText("${req_hos_code} ${req_hos_name}");
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

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
                       { display: '交易编码', name: 'bid_code', align: 'left',width : 150,
                    	   totalSummary: {align: 'right',render: 
                    		    function (suminf, column, cell) {
       								return '<div>合计：</div>';
       							}
       					   }
       			       }, 
       			       { display: '药品编码', name: 'inv_code', align: 'left',width : 150, },
					   {display : '药品名称(E)',name : 'inv_name',align : 'left',width : 150,
						textField : 'inv_name',valueField : 'inv_name',editor : {
							type : 'select',textField : 'inv_name',valueField : 'inv_name',
							selectBoxWidth : "80%",
							selectBoxHeight : 240,
							grid : {columns : [	
												{display : '交易编码',name : 'inv_code',align : 'left'}, 
												{display : '药品编码',name : 'inv_code',align : 'left'}, 
												{display : '药品名称',name : 'inv_name',align : 'left'}, 
												{display : '规格型号',name : 'inv_model',align : 'left'}, 
												{display : '计量单位',name : 'unit_name',align : 'left'}, 
												{display : '当前库存',name : 'cur_amount',align : 'left'}, 
												{display : '供应商',name : 'sup_name',align : 'left'}, 
												{display : '生产厂商',name : 'fac_name',align : 'left'}, 
												{display : '参考单价',name : 'price',align : 'left'} 
											  ],
									switchPageSizeApplyComboBox : false,onAfterEdit : f_onAfterEdit,
									onSelectRow:function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : '../../queryMedInvListByPur.do?isCheck=false',
									usePager : true,pageSize : 200,
									onSuccess: 
										function (data, g) { //加载完成时默认选中
											if (grid.editor.editParm) {
												var editor = grid.editor.editParm.record;
												var item = data.Rows.map(function (v, i) {
													return v.inv_name;
												});
												var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
															//加载完执行
												setTimeout(function () {
													g.select(data.Rows[index]);
												}, 80);
											}
										}
									},
									keySupport : true,autocomplete : true,
									onSuccess : function() {
										this.parent("tr").next(".l-grid-row").find("td:first").focus();
									},
									ontextBoxKeyEnter: function (data) {
										f_onSelectRow_detail(data.rowdata);
									}
								}
							},
							{display : '规格型号',name : 'inv_model',align : 'left',width : 150},
							{display : '计量单位',name : 'unit_name',align : 'left',width : 100},
							{display : '当前库存',name : 'cur_amount',align : 'left',minWidth : 100,
								render : function(rowdata, rowindex,value) {
									return '<a href=javascript:openUpdate("'+ rowdata.inv_id+ '")>'+ formatNumber(value, 2, 0) + '</a>';
								}
							},
							{display : '采购数量(E)',name : 'amount',align : 'right',minWidth : 100, type : 'int',
								editor : checkEditor ? null : {type : 'int'},
								render : checkEditor ? function(rowdata, rowindex,value) {
									return '<a href=javascript:openAmountRela("'+ 
											rowdata.pur_id+ ','+
											rowdata.pur_detail_id+ ','+
											rowindex+ ','+
											rowdata.inv_code+ ','+
											rowdata.inv_name+ ','+
											state+ ','+
											'")>'+ formatNumber(value, 2, 0) + '</a>';
								} : null,
								totalSummary: {
									align: 'right',
									render: function (suminf, column, cell) {
										return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
									}
								}
							},
							{display : '单价(E)',name : 'price',align : 'right',minWidth : 100,type : 'number',
								editor : {type : 'numberbox',precision : '${p08006 }'},
								render : function(rowdata, rowindex,value) {
									rowdata.price = value == null ? "": formatNumber(value,'${p08006 }',0);
									return value == null ? "": formatNumber(value,'${p08006 }',1);
								}
							},
							{display : '金额',name : 'amount_money',align : 'right',minWidth : 100,
								render : function(rowdata) {
									return formatNumber(rowdata.amount_money == null ? 0: rowdata.amount_money,2, 1);
								},
								totalSummary: {
									align: 'right',
									render: function (suminf, column, cell) {
										return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
									}
								}
							},
							{ display: '申请科室', name: 'app_dept_id', align: 'left',
								   textField  :  'app_dept_name',minWidth : 150,
								   valueField : 'app_dept_name',
								   editor : {type : 'select',valueField : 'id',textField : 'text',selectBoxWidth : 250,
										url : '../../queryHosDeptDict.do?isCheck=false&is_last=1',
										selectBoxHeight : 240, keySupport : true, autocomplete : true,
											onSelected : function (data){	
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
							{ display: '申请日期', name: 'app_date', align: 'left',
								type: 'date', align: 'left', format: 'yyyy-MM-dd', minWidth: 100,
								editor: {
									type: 'date',showSelectBox:false
								},
							},
							{display : '供应商(E)',name : 'sup_id',align : 'left',width : 250,textField : 'sup_name',
								editor : {
									url : '../../queryHosSupDict.do?isCheck=false',
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
											ajaxJsonObjectByUrl("../../protocol/medprotocolmain/queryMedProtocolMainPur.do?isCheck=false",formPara,
													function (responseData){				        		
														if(responseData.state=="true"){
											        		var a_price = responseData.price;
											        		medPurMap.put(rowindex_id,a_price);
											        	}else {									        			
											        		medPurMap.put(rowindex_id,0)									        			
											        	}
											        }
											);
										}
							        }
													
								}, render : function(rowdata, rowindex, value) {
			  								return rowdata.sup_name;
			  							}
										
							}, 
							{display : '生产产商',name : 'fac_name',align : 'left',width : 250}, 
							{display : '备注(E)',name : 'memo',align : 'left',width : 200,type : 'string',editor : {type : 'string'}}, 
							{display : '包装单位(E)',name : 'pack_code',align : 'left',minWidth : 100, textField : 'pack_name',align : 'left',
								editor : {
									type : 'select',valueField : 'id',textField : 'text',
									url : '../../queryMedHosPackage.do?isCheck=false',
									keySupport : true,autocomplete : true,}
								},
							{display : '转换量(E)',name : 'num_exchange',align : 'left',minWidth : 100,type : 'int',
								editor : {type : 'int'}
							},
							{display : '包装数量(E)',name : 'num',align : 'left',minWidth : 100,type : 'number',
								editor : {type : 'number'}
							},
							{ display: '明细ID', name: 'pur_detail_id', align: 'left',minWidth : 80,hide:true}
						],
						dataAction : 'server',dataType : 'server',usePager : true,width : '100%',height : '90%',
						checkbox : true,rownumbers : true,
						enabledEdit : ((useAudit == 0 && state == 2 ) || (useAudit == 1 && state == 1)) ? true : false,
						isAddRow : ((useAudit == 0 && state == 2 ) || (useAudit == 1 && state == 1)) ? true : false,
						alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
						onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,onAfterShowData:initValue,
						/* onLoaded:function(){
		                    this.addRow();
		                }, */
						url : 'queryMedPurDetail.do?isCheck=false&pur_id='+ '${pur_id}',
						isScroll : true,selectRowButtonOnly : true,//heightDiff: -10,
						onsuccess:function(){
							//is_addRow();
						},
						toolbar : {
							items : [ {text : '保存',id : 'save',click : saveMedPurMain,icon : 'save',disabled: ((useAudit == 0 && state == 2 ) || (useAudit == 1 && state == 1)) ? false : true },
								      {line : true },
								      {text : '删除',id : 'delete',click : deleteRow,icon : 'delete' ,disabled: ((useAudit == 0 && state == 2 ) || (useAudit == 1 && state == 1)) ? false : true } ,
								      { line:true  ,hide : useAudit == 0 ? true : false},
								      { text: '审核', id:'audit', click: audit, icon:'bluebook' ,disabled: state == 1 ? false : true  ,hide:useAudit == 0 ? true : false},
					        		  { line:true  ,hide :  useAudit == 0 ? true : false},
					        		  { text: '取消审核', id:'unaudit', click: unaudit,icon:'bookpen',disabled: state == 2 ? false : true  ,hide: useAudit == 0 ? true : false},
					        		  { line:true } 
		        				    ]
						}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
		grid.toggleCol("sup_no", false);
	}
	 
	 //审核
    function audit(){
		if(state != '1'){
			$.ligerDialog.error('只能审核状态为未审核的数据');
			return;
		}
		var Param = [];
    	Param.push(
				//表的主键
				'${group_id}'+"@"+
				'${hos_id}'+"@"+
				'${copy_code}'+"@"+
				'${pur_id}'+"@"+
				'${state}'
		);
    	
    	$.ligerDialog.confirm('确定审核?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("checkMedPurMain.do?isCheck=true&paramVo="+Param,{},
                function (responseData){
                	if(responseData.state=="true"){
                		parentFrameUse().query();
                		state = 2;
                		loadHead();
            	    	grid.reRender();
                	}
                });
            }
        });  
    }
    //取消审核
    function unaudit(){
		if(state != '2'){
			$.ligerDialog.error('只能取消已审核的数据');
			return;
		}
		var Param = [];
    	Param.push(
				//表的主键
				'${group_id}'+"@"+
				'${hos_id}'+"@"+
				'${copy_code}'+"@"+
				'${pur_id}'+"@"+
				'${state}'
		);
		
    	$.ligerDialog.confirm('确定取消审核?', function (yes){
            if(yes){
               ajaxJsonObjectByUrl("cancelCheckMedPurMain.do?isCheck=true&paramVo="+Param,{},function (responseData){
                	if(responseData.state=="true"){
                		parentFrameUse().query();
                		state = 1;
                		loadHead();
            	    	grid.reRender();
                	}
               });
            }
        });  
   } 
	
	function f_onAfterEdit(e) {
		if (e.value != "" && e.value != 0) {
			if (e.column.name == "amount") {
				//自动计算金额
				if (e.record.price != undefined && e.record.price != "" && e.record.price != 0) {
					grid.updateCell('amount_money', e.value * e.record.price,e.rowindex);
				}
				//自动计算包装件数
 				if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
 					grid.updateCell('num', e.value / e.record.num_exchange, e.rowindex);
 				}
 				if(e.record.num != undefined && e.record.num != "" && e.record.num != 0){
 					grid.updateCell('num_exchange', e.value / e.record.num, e.rowindex);
 				}
			} else if (e.column.name == "price") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != ""
						&& e.record.amount != 0) {
					grid.updateCell('amount_money', e.value * e.record.amount,
							e.rowindex);
				}
			} else if (e.column.name == "num_exchange") {
				//自动计算包装件数
				if (e.record.amount != undefined && e.record.amount != ""
						&& e.record.amount != 0) {
					grid.updateCell('num', e.record.amount / e.value,
							e.rowindex);
				}
			} else if (e.column.name == "num") {
				//自动计算数量与金额
				if (e.record.num_exchange != undefined
						&& e.record.num_exchange != ""
						&& e.record.num_exchange != 0) {
					grid.updateCell('amount', e.value * e.record.num_exchange,
							e.rowindex);
					if (e.record.price != undefined && e.record.price != ""
							&& e.record.price != 0) {
						grid.updateCell('amount_money', e.record.amount
								* e.record.price, e.rowindex);
					}
				}
			}
		}
		return true;
	}

	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
		if(column_name=='sup_id'){
			var sup = grid.getColumnByName("sup_id");
			sup.editor.url='../../queryMedSupByInvId.do?isCheck=false&inv_id='+e.record.inv_id;
		}
	}

	function f_onSelectRow_Sup(data, rowindex, rowobj){
		selectData = "";
		selectData = data;
		if (selectData != "" || selectData != null) {
			if (column_name == "sup_id") {
				//alert(data.id)
			}
		}
	}
	
	function f_onSelectRow_app(data, rowindex, rowobj){
		selectData = "";
		selectData = data;
		if (selectData != "" || selectData != null) {
			if (column_name == "app_dept_id") {
				//alert(data.id)
			}
		}
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
			ajaxJsonObjectByUrl("../../protocol/medprotocolmain/queryMedProtocolMainPur.do?isCheck=false",formPara,function (responseData){        		
				if(responseData.state=="true"){        			
        			var a_price = responseData.price;       			
        			medPurMap.put(rowindex_id,a_price); 						  
        		}else {       			
        			medPurMap.put(rowindex_id,0);
        		}
        	});
			}else if (column_name == 'pack_code') {
				grid.updateRow(rowindex_id, {
					packcode : data.text
				});
			} else if (column_name == 'stocker') {
				grid.updateRow(rowindex_id, {
					stockId : data.code
				});
			}
			
		}
		return true;
	}

/* 	function f_onSelectRow_updateSup(data, rowindex, rowobj) {
		grid.updateRow(rowindex_id, {
			sup_id : data.value
		});
	} */

	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}

	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}

	function initValue(){
		//var data = gridManager.getData(); 
		var data = gridManager.getData();
        $(data).each(function (){					
        	var index  = this.ROW_ID - 1;
                  var formPara="";
				 	formPara = {
				 		sup_id:this.sup_id,
				 		sup_no:this.sup_no
			 		}		
            
		ajaxJsonObjectByUrl("../../protocol/medprotocolmain/queryMedProtocolMainPur.do?isCheck=false",formPara,
				function (responseData){
			        if(responseData.state=="true"){
			        	var a_price = responseData.price;
			        	medPurMap.put(index,a_price);
			  		}else {
			        	medPurMap.put(index,0);
			        }
			    }
		);
		});
	}
	
	function save() {
		
		var allData = gridManager.getData();
		var deletedData = gridManager.getDeleted();
		if (liger.get("dept_id").getValue().split(",")[0] == '') {
			$.ligerDialog.error('编制科室不能为空');
			return;
		}

		if ($('#make_date').val() == '') {
			$.ligerDialog.error('编制日期不能为空');
			return;
		}

		if ($("input[type='radio']:checked").val() != '1') {
			if (liger.get("pur_hos_id").getValue().split(",")[0] == '') {
				$.ligerDialog.error('请填写采购单位');
				return;
			}

			if (liger.get("req_hos_id").getValue().split(",")[0] == '') {
				$.ligerDialog.error('请填写请购单位');
				return;
			}

			if (liger.get("pay_hos_id").getValue().split(",")[0] == '') {
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
		//alert(JSON.stringify(allData)); 
		var flag = true;
		var rows = 0;
		var msg = '';
		if (allData.length != 0) {
			$(allData).each(
				function(d_index, d_content) {
					var row_index = d_index + 1;
					if(this.inv_id != undefined && this.inv_id != '' && this.inv_id != null){
						rows += 1;
						if (this.inv_name == undefined || this.inv_name == '') {
							msg += '请选择药品名称<br>';
							return flag = false;
						}
	
						if (!this.price) {
							msg += '请填写单价<br>';
							return flag = false;
						}
	
						if (!this.amount) {						
							msg += '第' + row_index +'行请填写采购数量<br>';						
							return flag = false;
						} 
	
						if (parseFloat(this.amount) <= 0) {
							msg += '数量必须大于零<br>';
							return flag = false;
						}
	
						
						var med_pare = '${p08013 }';
							if(med_pare == 1){
						    	if(medPurMap.get(d_index)!= 0){ 
									var price = medPurMap.get(d_index);
									if(this.price > price){
										msg += '第' + row_index +'行采购药品单价大于协议中供应商设定的单价<br>';	
										return flag = false;
									}
						    	}  
						 	}
						}
					});
		}
		
		if(rows == 0){
	    	$.ligerDialog.warn('请选择药品 ');
	    	return ;
	    }
	    	
	    if(msg != ''){
	    	$.ligerDialog.warn(msg);
	    	return;
	    }

		var formPara = {
			pur_code : $('#pur_code').val(),//计划单号
			pur_id : '${pur_id}',
			dept_id : liger.get("dept_id").getValue().split(",")[0],//编制科室
			dept_no : liger.get("dept_id").getValue().split(",")[1],
			make_date : $("#make_date").val(),//编制日期
			arrive_date : $("#arrive_date").val(),//计划到货日期
			pur_type : $("input[type='radio']:checked").val(),//计划类型
			brif : $("#brif").val(),//摘要
			is_dir : liger.get("is_dir").getValue(),//是否定向
			pur_hos_id : liger.get("pur_hos_id").getValue().split(",")[0],//采购单位
			req_hos_id : liger.get("req_hos_id").getValue().split(",")[0],//请购单位
			pay_hos_id : liger.get("pay_hos_id").getValue().split(",")[0],//付款单位
			dir_dept_id : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[0],
			dir_dept_no : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[1],		
			allData : JSON.stringify(allData)//获取所有数据
		};

		ajaxJsonObjectByUrl("updateMedPurMain.do?isCheck=true", formPara, function(responseData) {
			if (responseData.state == "true") {
				parentFrameUse().query();
			}
		});
	}

	//打开药品当前存明细页面
	function openUpdate(obj) {
		$.ligerDialog.open({
			url : 'medInvCurAmountDetailPage.do?isCheck=false&inv_id=' + obj,
			data : {},top : 1,height : 450,width : 1000,title : '药品当前库存明细',
			modal : true,showToggle : false,showMax : true,showMin : false,
			isResize : true,
			buttons : [ {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	//打开计划数量明细页面(由于对应关系太复杂该页面改为直接保存数据库不采取单据修改保存方式)
	function openAmountRela(obj) {
    	var vo = obj.split(",");
		$.ligerDialog.open({
			title : '计划数量来源', 
			height : 400,width : 600,
			url : 'medPurAmountRelaPage.do?isCheck=false&pur_id=' + vo[0] + '&pur_detail_id=' + 
					vo[1] + '&rowindex=' + vo[2] + '&inv_code=' + vo[3] + '&inv_name=' + vo[4] + '&state=' + vo[5], 
			modal : true, showToggle : false,showMax : false,showMin : false,isResize : true
		});
	}

	//修改 从子页面修改 传回的采购数量
	function updateAmount(rowindex, amount) {
		console.log(grid.getRow(rowindex));
		grid.updateCell('amount', amount, rowindex);
		grid.updateCell('amount_money', amount*parseFloat(grid.getRow(rowindex).price), rowindex);
		grid.updateTotalSummary();
	}

	//保存采购计划
	function saveMedPurMain() {
		save();
	}

	function loadDict() {
		//字典下拉框
		autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制科室下拉框	
		liger.get("dept_id").setValue("${dept_id},${dept_no}");
		liger.get("dept_id").setText("${dept_code} ${dept_name}");
		
		autocomplete("#pur_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true,"",false,false);//采购单位下拉框 
		liger.get("pur_hos_id").setValue("${pur_hos_id},${pur_hos_no}");
		liger.get("pur_hos_id").setText("${pur_hos_code} ${pur_hos_name}");
		
		autocomplete("#req_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true,"",false,false);//请购单位下拉框 
		liger.get("req_hos_id").setValue("${req_hos_id},${req_hos_no}");
		liger.get("req_hos_id").setText("${req_hos_code} ${req_hos_name}");
		
		autocomplete("#pay_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true,"",false,false);//付款单位下拉框 
		liger.get("pay_hos_id").setValue("${pay_hos_id},${pay_hos_no}");
		liger.get("pay_hos_id").setText("${pay_hos_code} ${pay_hos_name}");
		
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_dir}",'160');//是否定向
		autocomplete("#dir_dept_id","../../queryMedDeptDict.do?isCheck=false","id","text",true,true,{is_last:'1'},false,false);//定向科室	
		if('${dir_dept_id}' != ''){
			liger.get("dir_dept_id").setValue("${dir_dept_id},${dir_dept_no}");
			liger.get("dir_dept_id").setText("${dir_dept_code} ${dir_dept_name}");
		}
		
		$("#req_hos_id").ligerComboBox({disabled:true,cancelable: false});
		
		$("#pur_code").ligerTextBox({width:160,disabled:true});
		$("#dept_id").ligerTextBox({width:160});
		$("#make_date").ligerTextBox({width:160});
		$("#brif").ligerTextBox({width:160});
		
		$("#arrive_date").ligerTextBox({width:160});
		$("#pur_hos_id").ligerTextBox({width:160});
		$("#req_hos_id").ligerTextBox({width:160});
		$("#pay_hos_id").ligerTextBox({width:160}); 

		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('P', printDate);
		hotkeys('M', printSet);
		hotkeys('C', this_close);
	}
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		var para={
			pur_id:'${pur_id}',
			template_code:'08003',
			p_num:0,
			use_id:useId
			
		};
		
		printTemplate("queryMedMakeByPrintTemlate.do?isCheck=false",para);
	}
	
	//打印设置
	function printSet(){
		
		
		var useId=0;//统一打印
		if('${p08017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		parent.parent.$.ligerDialog.open({url : 'hrp/med/purchase/make/purMainMakePrintSetPage.do?template_code=08003&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		});
	}
	
	//删除选中行
	function deleteRow() {
		gridManager.deleteSelectedRow();
	}
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
</script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>计划单号：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:true,maxlength:20}" value="${pur_code}" disabled="disabled" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>编制科室：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>编制日期：</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="make_date" type="text" id="make_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
					validate="{required:true,maxlength:20}" value="${make_date}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>计划类型：</td>
				<td align="left" class="l-table-edit-td">
					<input id="planA" name="planType" type="radio" value="1" onclick="singleSel()" />自购计划
					<input id="planB" name="planType" type="radio" value="2" onclick="singleSel()" />统购计划</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘要：</td>
				<td align="left" class="l-table-edit-td" colspan="2">
					<input name="brif" type="text" id="brif" ltype="text" value="${brif}" />
				</td>
					
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划到货日期：</td>
            	<td align="left" class="l-table-edit-td">
            		<input class="Wdate" name="arrive_date" type="text" id="arrive_date" ltype="text"  value="${arrive_date}"
            		onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}"/>
            	</td>
            	<td align="left"></td>
			</tr>

			<tr id="hos_name">
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>采购单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" />
				</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red" size="2">*</font>请购单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="req_hos_id" type="text" id="req_hos_id" ltype="text" readonly="readonly" />
				</td>
				<td align="left"></td>

				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><font color="red" size="2">*</font>付款单位：</td>
				<td align="left" class="l-table-edit-td">
					<input name="pay_hos_id" type="text" id="pay_hos_id" ltype="text" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
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
	 <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
<!-- 			<tr>	 -->
<!-- 				<td align="center" class="l-table-edit-td" width="32%" > -->
<%-- 					制单人：${medPurMain.maker} --%>
<!-- 				</td> -->
<!-- 				<td align="center" class="l-table-edit-td" width="32%" > -->
<%-- 					审核人：${medPurMain.checker} --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
