<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"></script>
	<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager;
	var apply_id = "${medAffiOut.apply_id}";
	var is_add = "${medAffiOut.is_add}";
  
	$(function (){
		
		loadDict()//加载下拉框
		loadHead();
		loadHotkeys();
      	//仓库绑定change事件
		$("#store_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
		//科室绑定change事件
		$("#dept_id").bind("change",function(){
			if(liger.get("dept_id").getValue()){
		    	var para = {dept_id : liger.get("dept_id").getValue() == "" ? "0" : liger.get("dept_id").getValue().split(",")[0]};
		    	liger.get("dept_emp").clear();
		    	liger.get("dept_emp").set("prams", para);
		    	liger.get("dept_emp").reload();
			}
		});
	});  
	//键盘事件
 	function loadHotkeys() {
 		hotkeys('S', save);//保存
 		hotkeys('D', remove);//删除
 		hotkeys('L', this_close);//关闭
 	}
 	
 	//验证
    function validateGrid() {  
    	//主表
  		if(liger.get("bus_type_code").getValue() == null || liger.get("bus_type_code").getValue() == ""){
  			$.ligerDialog.warn("业务类型不能为空!");  
  			return false;  
  		}
  		if(liger.get("store_id").getValue() == null || liger.get("store_id").getValue() == ""){
  			$.ligerDialog.warn("仓库不能为空!");  
  			return false;  
  		}
  		if($("#out_date").val() == null || $("#out_date").val() == ""){
  			$.ligerDialog.warn("出库日期不能为空!");  
  			return false;  
  		}
  		if($("#dept_id").val() == null || $("#dept_id").val() == ""){
  			$.ligerDialog.warn("领料科室不能为空！");  
  			return false;  
  		}
  		if($("#dept_emp").val() == null || $("#dept_emp").val() == ""){
  			$.ligerDialog.warn("领料人不能为空！");  
  			return false;  
  		}
  		
  		var validate_detail_buf = new StringBuffer();
		var out_detail_data = gridManager.rows;	
  	    var targetMap = new HashMap();
  	 	if(out_detail_data.length > 0){
  	 		$.each(out_detail_data, function(d_index, d_content){ 
  	 	    	if(typeof(d_content.inv_code) == "undefined" || d_content.inv_code == ""){
  	 	    		grid.deleteRow(d_content);//删除选择的行
  	 	      		return true;
  	 	      	}
  	 	      	var value="第"+(d_index+1)+"行";
				if(typeof(d_content.amount) == "undefined" || d_content.amount == ""){	       				
  	 	      		validate_detail_buf.append(value+"对应数量为空 请输入\n");  	       				
  	       		}
  	 	      		
  	 	      	if(d_content.imme_amount - d_content.amount <0){	 	      			
  		      		validate_detail_buf.append(value+"对应数量大于即时库存数据数量 请重新输入\n"); 	   				
  	   		  	}
  	 	      		
  	 	      	var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.batch_sn+"|"+d_content.bar_code;
  	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){  	 					
  	 				targetMap.put(key ,value);  	 					
  	 			}else{ 	 					
  	 				validate_detail_buf.append(targetMap.get(key)+"与"+value+"药品编码、生产批号、条形码不能重复" + "\n\r"); 	 					
  	 			}
  			}); 
  	 	}
  	 	if(validate_detail_buf.toString()  != ""){  	 			
  	 		$.ligerDialog.warn(validate_detail_buf.toString());  	 			
  	 		return false;
  	 	}

  	 	if(gridManager.rows.length == 0){ 	 			
  	 		$.ligerDialog.warn('请选择药品'); 	 			
  	 		return false;
  	 	}
  	 	return true;
 	}
 	
 	
 	//保存
    function  save(){
		grid.endEdit();
    	
        if(validateGrid()){
        	var out_detail_data = gridManager.rows;
        	//alert(JSON.stringify(out_detail_data));	
        	var formPara={
            		out_no : $("#out_no").val(),
            		bus_type_code : liger.get("bus_type_code").getValue(),
    				store_id : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0],
    				store_no : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[1],
    				brief : $("#brief").val(),
    				out_date : $("#out_date").val(),
    				dept_id : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[0],
    				dept_no : liger.get("dept_id").getValue() == null ? "" : liger.get("dept_id").getValue().split(",")[1],
    				dept_emp : liger.get("dept_emp").getValue() == null ? "" : liger.get("dept_emp").getValue().split(",")[0],
    				use_code : liger.get("use_code").getValue() == null ? "" : liger.get("use_code").getValue(),
    				proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),	
    				is_dir : $("#is_dir").val(),
            		apply_id: apply_id,
    				detailData : JSON.stringify(gridManager.getData())
    		};
            //ajaxJsonObjectByUrl("../../../affi/out/addMedAffiOutCommon.do?isCheck=true",formPara,function(responseData){
            ajaxJsonObjectByUrl("addMedAffiOutByApp.do?isCheck=true",formPara,function(responseData){
            	if(responseData.state=="true"){
	        		parent.queryDetail();
	        		parent.refreshParent();
	            	var voStr = responseData.update_para.split(",");
	        		var paras = "group_id=" + voStr[0].toString() + "&" 
	        			+ "hos_id=" + voStr[1].toString() + "&" 
	        			+ "copy_code=" + voStr[2].toString() + "&" 
	        			+ "out_id=" + voStr[3].toString() + "&" 
	        			+ "store_id=" + voStr[4].toString();
	        		parent.parent.$.ligerDialog.open({
						title: '代销出库单修改',
						height:parent.$(window).height()+100,
						width: parent.$(window).width()+10,
						url: 'hrp/med/affi/out/medAffiOutCommonUpdatePage.do?isCheck=false&'+paras.toString(),
						modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
					});
	        		
	            	this_close();
                }
            });	
        }
    	
    }
	
	function loadDict(){
    	//字典下拉框
    	$("#out_no").ligerTextBox({width:180,disabled: true });           
    	if("${medAffiOut.out_no}"){
    		$("#out_no").val("${medAffiOut.out_no}");
    	}  
    	$("#out_date").ligerTextBox({width:180});     
    	if("${medAffiOut.out_date}"){
    		$("#out_date").val("${medAffiOut.out_date}");
    	}else{
    		autodate("#out_date");
    	}	
    	$("#brief").ligerTextBox({width:465});   

    	var bus_type_code_paras={sel_flag : "aout"};
    	autocomplete("#bus_type_code", "../../../queryMedBusType.do?isCheck=false", "id", "text", true, true,bus_type_code_paras,false,'${medAffiOut.bus_type_code}','180');
            
    	autocompleteAsync("#store_id", "../../../queryMedStore.do?isCheck=false", "id", "text", true, true, "", false, false, '180');
    	if("${medAffiOut.store_id}"){
	    	liger.get("store_id").setValue("${medAffiOut.store_id},${medAffiOut.store_no}");
	    	liger.get("store_id").setText("${medAffiOut.store_code} ${medAffiOut.store_name}");
    	}
    	autocompleteAsync("#dept_id", "../../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,{is_last: 1},false,false,'180');
    	if("${medAffiOut.dept_id}"){
	    	liger.get("dept_id").setValue("${medAffiOut.dept_id},${medAffiOut.dept_no}");
	    	liger.get("dept_id").setText("${medAffiOut.dept_code} ${medAffiOut.dept_name}");
    	}
    	
    	/* 
    	autocompleteAsync("#dept_emp", "../../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true, {dept_id: '${medAffiOut.dept_id}'},false,false,'180'); 
    	if("${medAffiOut.dept_emp}"){
	    	liger.get("dept_emp").setValue("${medAffiOut.dept_emp},${medAffiOut.emp_no}");
	    	liger.get("dept_emp").setText("${medAffiOut.emp_code} ${medAffiOut.emp_name}");
    	} */
    	
    	autocompleteAsync("#dept_emp", "../../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true, "",false,false,'180'); 

    	autocomplete("#use_code", "../../../queryMedOutUse.do?isCheck=false", "id", "text", true, true, "", false, false, '180');
    	if("${medAffiOut.use_code}"){
    		liger.get("use_code").setValue("${medAffiOut.use_code}");
        	liger.get("use_code").setText("${medAffiOut.use_name}");
    	}
    	autocomplete("#proj_code", "../../../queryMedProj.do?isCheck=false", "id", "text", true, true, "", false, false, '180');
    	if("${medAffiOut.proj_id}"){
    		liger.get("proj_code").setValue("${medAffiOut.proj_id}");
        	liger.get("proj_code").setText("${medAffiOut.proj_code} ${medAffiOut.proj_name}");
    	} 
	} 
	
	function loadHead(){
    	var medAffiOutDetail  = ${medAffiOutDetail};
    	grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '药品编码', name: 'inv_code', align: 'left',width:100,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
			}, { 
				display: '药品名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left',width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : '80%',
	         		selectBoxHeight : 370,
	         		grid : {
	         			columns : [ {
	         				display : '药品编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '药品名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	         				display : '药品类别', name : 'med_type_name', width : 140, align : 'left'
	         			}, {
	         				display : '规格型号', name : 'inv_model', width : 60, align : 'left'
	         			}, {
	         				display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 120, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:100,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 90, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 90, align : 'left'
	         			}, {
	         				display : '是否收费', name : 'is_charge', width : 60, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
	         			}, {
	         				display : '货位', name : 'location_name', width : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow : f_onSelectRow_detail,
	         			url : '../../../queryMedAffiOutInvList.do?isCheck=false&store_id=' 
         					+ liger.get("store_id").getValue().split(",")[0]+'&out_id=${medAffiOut.out_id}',
	         			
	         			pageSize : 200
	         		},
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		}
	         	}
			}, {
				display: '规格型号', name: 'inv_model', align: 'left',width:140
			}, {
				display: '计量单位', name: 'unit_name', align: 'left',width:60
			}, { 
				display: '批号', name: 'batch_no', align: 'left',width:120
			}, {
				display: '当前库存', name: 'cur_amount', align: 'left',width:90
			}, {
				display: '即时库存', name: 'imme_amount', align: 'left',width:90
			}, {
				display: '数量(E)', name: 'amount', align: 'left', width:90, editor : {type : 'number'},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
			}, { 
				display: '单价',name:'price',align:'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '有效日期', name: 'inva_date', align: 'left',width:90
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:90
			}, { 
				display: '条形码', name: 'bar_code', align: 'left',width:120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right',width:100,
				/*editor : {
					type : 'numberbox',
					precision : '${p08006 }'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '零售价格', name: 'sell_price', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '货位名称', name: 'location_name', align: 'left',width:180
			}, { 
				display: '备注(E)', name: 'note', align: 'left',width:160,
				editor : {
					type : 'text'
				}
			}, { 
				display: '批次数量合计', name: 'sum_amount',width:100, hide: true 
			}, { 
				display: '批次明细', name: 'inv_detail_data',width:100, hide:true 
			} ],
			usePager : false,width : '100%',height : '100%',
			enabledEdit : is_add == 1 ? true : false,fixedCellHeight:true,
			data:medAffiOutDetail,heightDiff:-20,
			onBeforeEdit : f_onBeforeEdit, 
			onBeforeSubmitEdit : f_onBeforeSubmitEdit, 
			onAfterEdit : f_onAfterEdit,
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//药品批次明细
			toolbar: { items: [
				{ text: '保存（<u>S</u>）', id:'save', click: save,icon:'save', hide: is_add == 1 ? false : true},
				{ line:true, hide: is_add == 1 ? false : true},
				{text : '删除（<u>D</u>）',id : 'delete',click : deleteRow,icon : 'delete', hide: is_add == 1 ? false : true}, 
		        {line : true, hide: is_add == 1 ? false : true}, 
		        {text : '关闭（<u>L</u>）',id : 'close',click : this_close,icon : 'close'}
			]}
    	});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
  	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;		
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					batch_no : data.batch_no == null ? "" : data.batch_no,
					batch_sn : data.batch_sn == null ? "" : data.batch_sn,
					bar_code : data.bar_code == null ? "" : data.bar_code,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					inva_date : data.inva_date == null ? "" : data.inva_date,
					disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name
				});
			}
		}
		return true;
	}
    function f_onAfterEdit(e){
    	if("amount" == column_name){
    		grid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		grid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		grid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record); 
    	}
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择药品！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		*/
		return true;
	} 
    //删除
    function deleteRow(){ 
    	gridManager.deleteSelectedRow();
    }
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){   	
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '药品编码', name: 'inv_code',width:100, align : 'left',
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
    		}, { 
    			display: '药品名称(E)', name: 'inv_id', textField: 'inv_name', width:240, align : 'left',
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : 500,
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '药品编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '药品名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 80, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 50, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 50, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow : f_detail_onSelectRow_detail,
	         			url : '../../../queryMedAffiOutInvList.do?isCheck=false&store_id=' 
	         					+ liger.get("store_id").getValue().split(",")[0] + '&out_id=${medAffiOut.out_id}&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code,
	         			pageSize : 10
	         		},
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', minWidth : 50, align : 'left'
     		}, { 
     			display : '即时库存', name : 'imme_amount', minWidth : 50, align : 'left', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 60, align : 'left', editor : {type : 'float'},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '单价', name: 'price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : is_add == 1 ? true : false, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',data : f_getInvDetailData(row),columnWidth:80, 
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete', hide: is_add == 1 ? false : true},
    			{ line:true, hide: is_add == 1 ? false : true},
    			{ text: '添加行', id:'add', click: detailGridAddRow,icon:'add', hide: is_add == 1 ? false : true},
    			{ line:true, hide: is_add == 1 ? false : true}
    		]} 
		});

		$(batchSn).bind('keydown.grid', function(event) {
    		if (event.keyCode == 13) {// enter,也可以改成9:tab
    			detailGrid.endEditToMedNext();
    		}
    	});
    }
	
	//删除明细
    function deleteDetailRow(){ 
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
  	//添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 1000);
    }
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
		if(validateStr(rowdata.inv_id) && validateStr(rowdata.amount) && rowdata.amount != 0){
			//明细中有批次信息并且主数量和明细数量相等
			if(validateStr(rowdata.inv_detail_data) && validateStr(rowdata.sum_amount) && rowdata.amount == rowdata.sum_amount){
				var rows = jsonRowsToObject(rowdata.inv_detail_data);
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("store_id").getValue().split(",")[0], 
    				out_id : '${medAffiOut.out_id}', 
            		inv_id : rowdata.inv_id, 
            		inv_batch : rowdata.inv_batch, 
            		bar_code : rowdata.bar_code, 
            		amount : rowdata.amount 
            	}
        		ajaxJsonObjectByUrl("../../../queryMedAffiInvByFifo.do?isCheck=false",invPara,function(responseData){
        			data = responseData;
                }, false);
				//变更主数据中药品批次信息
        		grid.updateCell('sum_amount', gridRowData.amount, gridRowData); 
        		grid.updateCell('inv_detail_data', JSON.stringify(data.Rows), gridRowData); 
        	}
    	}
        return data;
    }
    
    //改变明细数量更新主数据数量
    function changeDetailAmount(){
    	//获取明细总数量，并更新主数据数量
		var sumAmount =0;
		var dataDetail =  detailGrid.getData();
		if(dataDetail.length > 0){
			$(dataDetail).each(function(){
				if(validateStr(this.amount)){
					sumAmount += this.amount;
				}
			})
    		grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', JSON.stringify(dataDetail), gridRowData); 
		}else{
			grid.updateCell('amount', sumAmount, gridRowData); 
    		grid.updateCell('amount_money', sumAmount*gridRowData.price, gridRowData); 
    		grid.updateCell('sale_money', sumAmount*gridRowData.sale_price, gridRowData); 
    		grid.updateCell('sell_money', sumAmount*gridRowData.sell_price, gridRowData); 
    		grid.updateCell('sum_amount', sumAmount, gridRowData); 
    		grid.updateCell('inv_detail_data', "", gridRowData); 
		}
    }

	var detail_id = "";
	var detail_name = "";
	function f_detail_onBeforeEdit(e) {
		detail_id = e.rowindex;
		detailClicked = 0;
		detail_name = e.column.name;		
	}
	//选中回充数据
	function f_detail_onSelectRow_detail(data, rowindex, rowobj) {
		detailSelectData = "";
		detailSelectData = data;
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (detailSelectData != "" || detailSelectData != null) {
			if (detail_name == "inv_id") {
				detailGrid.updateRow(detail_id, {
					inv_no : data.inv_no,
					inv_code : data.inv_code,
					inv_name : data.inv_name,
					batch_sn : data.batch_sn == null ? "" : data.batch_sn,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price
				});
			}
		}
		return true;
	}
    function f_detail_onAfterEdit(e){ 
    	if("amount" == detail_name){ 
    		detailGrid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		detailGrid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		detailGrid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record); 
    		changeDetailAmount();
    	} 
    } 
    // 编辑单元格提交编辑状态之前作判断限制
	function f_detail_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择药品！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//detailGrid.setCellEditing(e.record, e.column, true);
			return false;
		}
		return true;
	}
   
	//增加行数据
	function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
	//增加行
	function addNew(){
		is_addRow();
	}
	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 


	//删除
 	function remove(){
     	grid.deleteSelectedRow();
     }
 	
 	//打印
 	function printDate(){
 	
 	}
 	
 	//关闭
 	function this_close(){
 		frameElement.dialog.close();
 	}
 	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	
	function is_addRow(){//当数据为空时 默认新增一行
		setTimeout(function () {
			var data = grid.getData();
			if (data.length==0)
				grid.addRow();
			}, 1000);
	}
</script>
  
</head>
  
<body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
  	<input name="is_dir"  type="hidden" id="is_dir" value="0" />
	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_no" type="text" id="out_no" ltype="text" value="自动生成" disabled="disabled" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			        </tr> 
			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date" type="text" id="out_date" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>领料人：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_emp" type="text" id="dept_emp" ltype="text" validate="{required:false}" /></td>
			            <td align="left"></td>
			        </tr> 			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	药品用途：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="use_code" type="text" id="use_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	项目：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="4"><input name="brief" type="text" id="brief" ltype="text"  value="${medAffiOut.brief}" validate="{maxlength:50}" /></td>
			            <td align="left"></td>
			        </tr> 			
			    </table>			    
			</form>		
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		
	</div>
</body>
</html>
