<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     
     $(function (){
		loadDict();//加载下拉框       
		loadHead();
	
     });  
     //验证
     function validateGrid() {  
  		//主表
  		if(liger.get("bus_type_code").getValue() == null || liger.get("bus_type_code").getValue() == ""){
  			$.ligerDialog.warn("业务类型不能为空!");  
  			return false;  
  		}
  		var bus_type_code = liger.get("bus_type_code").getValue();
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
  		/* if($("#dept_emp").val() == null || $("#dept_emp").val() == ""){
  			$.ligerDialog.warn("领料人不能为空！");  
  			return false;  
  		} */
  		//明细
  		var msg="";
  		var rowm = "";
  		var data = gridManager.getData();
  		var validate_detail_buf = new StringBuffer();
  		//alert(JSON.stringify(data));
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		var rows = 0;
  		$.each(data,function(i, v){
  			rowm = "";
  			if(v.inv_id){
  				var value="第"+(i+1)+"行";
  				if (v.inv_id == "" || v.inv_id == null || v.inv_id == 'undefined') {
  	 				rowm+="[药品]、"; 
  	 			}  
  	 			if (v.amount == "" || v.amount == null || v.amount == 'undefined') {
  	 				rowm+="[数量]、";
  	 			}  
  	 			if (v.price == "" || v.price == null  || v.price == 'undefined') {  
  	 				rowm+="[单价]、"; 
  	 			} 
  	 			if(rowm != ""){
  	 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
  	 			}

 	      		if(bus_type_code == "21"){
	 	      		if(v.amount > 0){
	 	      			validate_detail_buf.append(value+"退库数量不能大于0 请重新输入<br>");
	   		  		}
 	      		}else{
	 	      		if(v.amount < 0){
	 	      			validate_detail_buf.append(value+"出库数量不能小于0 请重新输入<br>");
	   		  		}
	 	      		if(v.imme_amount - v.amount <0){
	 	      			validate_detail_buf.append(value+"数量不能大于即时库存数据数量 请重新输入<br>");
	   		  		}
 	      		}
 	      		
  	 			msg += rowm;
  	 			var key=v.inv_id +"|"+v.batch_no+"|"+v.bar_code+"|"+v.price;
  	 			
  	 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
  	 				targetMap.put(key ,value);
  	 			}else{
  	 				msg += targetMap.get(key)+"与"+value+"药品编码、生成批号、条形码单价不能重复" + "\n\r";
  	 			}
  	 			rows = rows + 1;
  			}
 			
  		});
  		
		if(validate_detail_buf.toString()  != ""){
 			$.ligerDialog.warn(validate_detail_buf.toString());	
 			return false;
 		}
  		
		if(rows == 0){
  			$.ligerDialog.warn("请添加明细药品！");  
 			return false;  
  		} 
		
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		} 	 
  		
  		return true;	
  	}
     
     function  save(){
		grid.endEdit();
    	if(validateGrid()){
	    	//alert(JSON.stringify(gridManager.getData()));
	        var formPara={	
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
				detailData : JSON.stringify(gridManager.getData())
			};
	        ajaxJsonObjectByUrl("addMedAffiOutCommon.do?isCheck=true",formPara,function(responseData){
	            if(responseData.state=="true"){
	            	
	            	parentFrameUse().query();
	            	$.ligerDialog.confirm('是否继续添加代销出库单?', function (yes){
	            		if(yes){
	            			out_no : $("#out_no").val("自动生成");
	            			out_date : $("#out_date").val("");
	            			brief : $("#brief").val("");
	            			is_dir : $("#is_dir").val("");
	            			loadDict();
	                        grid.loadData();
	            		}else{
	            			parentFrameUse().openUpdate(responseData.update_para);
	            			this_close();
	            		}
	            	}); 
	            }
	        });
    	}
    }
     
   
    function loadDict(){
    	//字典下拉框
    	var count_store = 0;
    	$("#store_id").ligerComboBox({
    	 	parms : {is_com : 1},
          	url: '../../queryMedStoredisable.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	highLight : true,
    		keySupport:true,
          	//initIsTriggerEvent: true,
    		/* onSuccess : function (data){
    			if(data.length >0 ){
					if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
						defaultSelectValue = data[0].id;
						if( count_store==0){
							this.setValue(data[0].id);
						}
					}
				}
    			count_store++;
           }, */
    		onSelected: function (selectValue){    			
    			if(selectValue !=null ){
    				loadHead(null);	
    		    	grid.reRender();
    			}
			}
    	 });
    	
    	var count_dept = 0;
    	$("#dept_id").ligerComboBox({
    	 	parms : {is_last : 1},
          	url: '../../queryMedDeptDict.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	highLight : true,
    		keySupport:true,
          	//initIsTriggerEvent: true,
    		/* onSuccess : function (data){
    			if(data.length >0 ){
					if(data[0].id != undefined && data[0].id != "" && data[0].id != null){
						defaultSelectValue = data[0].id;
						if(count_dept==0){
							this.setValue(data[0].id);
						}
					}
				}
    			count_dept++;
           }, */
    		onSelected: function (selectValue){    			
    			if(selectValue !=null ){    				
    				var dept_emp_paras={
    	        			dept_id:selectValue.split(",")[0],
    	    				dept_no:selectValue.split(",")[1]
    	        	};	   				
    				liger.get("dept_emp").clear();        			
        			autocomplete("#dept_emp", "../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true,dept_emp_paras,false,false,'160');
    			}
			}
    	 });
    	
    	autocomplete("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes:'5,7,9,28,30,32,33,34'},false,'28');
		autocomplete("#dept_emp", "../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true); 
    	autocomplete("#use_code", "../../queryMedOutUse.do?isCheck=false", "id", "text", true, true, "", false, false, 160);
    	autocomplete("#proj_code", "../../queryMedProj.do?isCheck=false", "id", "text", true, true,"","","","","",510);
		
		autodate("#out_date", "yyyy-mm-dd", "new");
		//格式化文本框
        $("#out_no").ligerTextBox({width:160, disabled:true});
        $("#bus_type_code").ligerTextBox({width:160});
        $("#store_code").ligerTextBox({width:160});
        $("#out_date").ligerTextBox({width:160});
        $("#dept_code").ligerTextBox({width:160});
        $("#dept_emp").ligerTextBox({width:160});
        $("#proj_code").ligerTextBox({width:510});
        //$("#brief").ligerTextBox({width:400});
        //格式化按钮
		$("#save").ligerButton({click: save, width:90});
		$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
					display : '药品编码',name : 'inv_code',minWidth : 120,align : 'left',
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>合计：</div>';
						}
					}
				},{display: '药品名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left',width:240,
					editor : {
						type : 'select',
		         		valueField : 'inv_id',
		         		textField : 'inv_name',
		         		selectBoxWidth : '80%',
		         		selectBoxHeight : 240,
		         		grid : {
		         			columns : [ 
		         				{display : '药品编码', name : 'inv_code', width : 100, align : 'left'}, 
		         				{display : '药品名称', name : 'inv_name', width : 240, align : 'left'}, 
		         				{display : '药品类别', name : 'med_type_name', width : 140, align : 'left'}, 
		         				{display : '规格型号', name : 'inv_model', width : 180, align : 'left'}, 
		         				{display : '生产厂商', name : 'fac_name', width : 100, align : 'left'}, 
		         				{display: '批号', name: 'batch_no', align: 'left', width: 200, align : 'left'}, 
		         				{display: '单价', name: 'price', align: 'right', width:140,
			       					render : function(rowdata, rowindex, value) {
			       						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
			       					}
		       					}, { 
		         					display : '库存', name : 'cur_amount', width : 100, align : 'right'
		         				}, { 
		         					display : '即时库存', name : 'imme_amount', width : 100, align : 'right'
		         				}, {
			         				display : '是否收费', name : 'is_charge', width : 60, align : 'left',
			         				render : function(rowdata, rowindex, value) {
			         					return value == 1 ? '是' : '否';
			         				}
		         				},{display : '条形码', name : 'bar_code', width : 100, align : 'left'} ,
		         				{display : '货位', name : 'location_name', width : 100, align : 'left'} 
		         				
		         			],
		         			switchPageSizeApplyComboBox : false,
		         			onSelectRow: function (data) {
								var e = window.event;
								if (e && e.which == 1) {
									f_onSelectRow_detail(data);
								}
							},
		         			url : '../../queryMedAffiOutInvList.do?isCheck=false&store_id=' 
		         					+ liger.get("store_id").getValue().split(",")[0] + '&bus_type_code=' 
		         					+ liger.get("bus_type_code").getValue(),
		         			pageSize : 500,
							onSuccess: function (data, g) { //加载完成时默认选中
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
		         		delayLoad : true,keySupport : true,autocomplete : true,
						onSuccess: function (data, grid) {
							this.parent("tr").next(".l-grid-row").find("td:first").focus();
						},
						ontextBoxKeyEnter: function (data) {
							f_onSelectRow_detail(data.rowdata);
						}
		         	}
				}, {display : '规格型号', name : 'inv_model', minWidth : 180, align : 'left'
				}, {display : '计量单位', name : 'unit_name', minWidth : 140, align : 'left'
				}, {display : '批号', name : 'batch_no', minWidth : 140, align : 'left'	
				//}, {display : '批次', name : 'batch_sn', minWidth : 140, align : 'left'	
				}, {display : '库存', name : 'cur_amount', minWidth : 140, align : 'right'	
				}, {display : '即时库存', name : 'imme_amount', minWidth : 140, align : 'right'	
				}, {display : '数量(E)', name : 'amount',    minWidth : 140, type : 'number',
					align : 'right',
					editor : { type : 'number', },
					render : function(rowdata, rowindex, value) {
						return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
					},totalSummary: {
						align: 'left',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
						}
					}
				}, {
					display : '单价(E)',
					name : 'price',
					minWidth : 140,
					align : 'right',
					editor : {
						type : 'numberbox',
						precision : '${p08006 }'
					},
					render : function(rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p08006}', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
				},{
					display : '金额', name : 'amount_money', minWidth : 140, type : 'number', align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.amount_money == null ? "" : formatNumber(rowdata.amount_money, '${p08005}', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
						}
					}
				}, {
					display : '有效日期(E)', name : 'inva_date', type: 'date', format: 'yyyy-MM-dd', width : 100,
				}, {
					display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', format: 'yyyy-MM-dd', width : 100,
				},{
					display : '条形码(E)', name : 'bar_code', minWidth : 140,
					editor : {
						type : 'text',
					},
					align : 'left'
				},{ 
					display: '批发价格', name: 'sale_price', align: 'right',width:140,
					/*editor : {
						type : 'numberbox',
						precision : '${p08006 }'
	        		},*/
					render : function(rowdata, rowindex, value) {
						rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
				}, { 
					display: '批发金额', name: 'sale_money', align: 'right',width:140,
					render : function(rowdata, rowindex, value) {
						rowdata.sale_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
						return value == null ? "" : formatNumber(value, '${p08005 }', 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
						}
					}
				}, {
					display : '零售单价(E)', name : 'sell_price', type : 'number', minWidth : 140, align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.sell_price == null ? "" : formatNumber(rowdata.sell_price, '${p08006}', 1);
					}
				},{
					display : '零售金额', name : 'sell_money', type : 'number', minWidth : 140, align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.sell_money == null ? "" : formatNumber(rowdata.sell_money, '${p08005}', 1);
					},totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p08005 }', 1) + '</div>';
						}
					}
				},{ 
					display: '货位名称', name: 'location_name', align: 'left',width:180
				},{ 
					display: '供应商', name: 'sup_name', align: 'left',width:180
				}, { 
					display: '备注(E)', name: 'note', align: 'left',width:180,
					editor : {
						type : 'text'
					}
				},{ 
					display: '供应商ID', name: 'sup_id',width:140 ,hide : true 
				},{ 
					display: '供应商NO', name: 'sup_no',width:140 ,hide : true 
				},{ 
					display: '批次数量合计', name: 'sum_amount',width:140 ,hide : true 
				}, { 
					display: '批次明细', name: 'inv_detail_data',width:140 ,hide : true 
				}
			],
			usePager : false,width : '100%',height : '100%',enabledEdit : true,fixedCellHeight:true,heightDiff:-20,
			onBeforeEdit : f_onBeforeEdit, 
			onBeforeSubmitEdit : f_onBeforeSubmitEdit, 
			onAfterEdit : f_onAfterEdit,
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//药品批次明细
			toolbar : {
				items : [ 	{ text: '保存', id:'save', click: save,icon:'save' },
							{ line:true },
							{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
							{ line:true },
							/* { text: '定向出库', id:'dirImp', click: medOutDirImport,icon:'up' },
							{ line:true }, */
							{ text: '整单出库', id:'wholeOut', click: wholeOut,icon:'up' },
							{ line:true },
							{ text: '配套导入', id:'matchImp', click: matchImp,icon:'up' },/* ,
							{ line:true },
							{ text: '历史使用导入', id:'hisImp', click: medOutHistoryImport,icon:'up' },
							{ line:true } */
							{line : true},
					        { text: '保存列头', id:'btn_saveColumn', click: btn_saveColumn,icon:'btn_saveColumn' },
					        {line : true},
							{ text: '选择药品', id:'add', click: choice_inv,icon:'add' },
							{line : true},
					        { text: '复制药品（<u>M</u>）', id:'copy', click: copy, icon:'copy' },
					        {line : true},
					        {text : '关闭（<u>L</u>）',id : 'close',click : this_close,icon : 'close'}
					        
				        ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
        
	}
    
    //复制药品
    function copy () {
		var data_copys = grid.getCheckedRows();
		if (data_copys.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			for(var i = 0, length = data_copys.length; i<length; i++){         // 把数组的每个data都复制一遍，即深复制
				data_copys[i] = $.extend({},data_copys[i]) ;
				data_copys[i].amount = '1';
			}
			grid.addRows(data_copys);
		}
	}
  
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;		
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
	 
	//选择药品
	function choice_inv(){
		var store_id = liger.get("store_id").getValue();
    	if(!store_id){
    		$.ligerDialog.warn('请选择仓库!');
    		return false;
    	}
		
		var store_id = liger.get("store_id").getValue();
		var store_text = liger.get("store_id").getText();
		
		$.ligerDialog.open({url: "medAffiOutMainChoiceInvPage.do?isCheck=false&store_id="+store_id+"&store_text="+store_text,
				height: 500,width: 900, title:'选择药品',modal:true,showToggle:false,showMax:true,
				showMin: false,isResize:true
		});
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
					bar_code : data.bar_code == null ? "" : data.bar_code,
					cur_amount : data.cur_amount == null ? "" : data.cur_amount,
					imme_amount : data.imme_amount == null ? "" : data.imme_amount,
					price : data.price == null ? "" : data.price,
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					inva_date : data.inva_date == null ? "" : data.inva_date,
					disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					sup_name : data.sup_name == null ? "" : data.sup_name,
					sup_id : data.sup_id == null ? "" : data.sup_id,
					sup_no : data.sup_no == null ? "" : data.sup_no
				});
			}
		}
		return true;
	}
    function f_onAfterEdit(e){
    	if("inv_id" == column_name){
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    	}else if("amount" == column_name){
    		grid.updateCell('amount_money', e.record.amount*e.record.price, e.record); 
    		grid.updateCell('sale_money', e.record.amount*e.record.sale_price, e.record); 
    		grid.updateCell('sell_money', e.record.amount*e.record.sell_price, e.record);
    		grid.updateCell('sum_amount', 0, e.record); 
    		grid.updateCell('inv_detail_data', "", e.record); 
    	}
		grid.updateTotalSummary();
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
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
		return true;
	} 
 	// 编辑后事件 当编辑药品名称时候弹出药品选择框
    /* function f_onAfterEdit(e) {
 		
		if(e.column.name=='inv_name' && e.value != ''){
			
			var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
			
			var paras = e.value+"@"+store_id;
			
			$.ligerDialog.open({url: "medOutMainFifoPage.do?isCheck=false&paras="+paras, height: 500,width: 900, title:'选择药品',modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

		}
    } */
    
    //删除
    function deleteRow(){ 
    	
    	gridManager.deleteSelectedRow();
    }
    
	//批量添加明细数据
    function add_Rows(data){
    	delete_allRows();
    	grid.addRows(data);
    }
    //清空表格
    function delete_allRows(){
		for (var i = 0, l = gridManager.rows.length; i < l; i++) {  
			var o = gridManager.getRow(i);
			if (o['__id'] in gridManager.records)
				gridManager._deleteData.ligerDefer(gridManager, 10, [ o ]); 
		}  
		gridManager.reRender.ligerDefer(gridManager, 20);
	}
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '药品编码', name: 'inv_code',width:140
    		}, { 
    			display: '药品名称(E)', name: 'inv_id', textField: 'inv_name',width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : 500,
	         		selectBoxHeight : 160,
	         		grid : {
	         			columns : [ {
	         				display : '药品编码', name : 'inv_code', width : 100, align : 'left'
	         			}, {
	         				display : '药品名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 140, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:140,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 100, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_detail_onSelectRow_detail(data);
							}
						}, 
	         			//onSelectRow : f_detail_onSelectRow_detail,
	         			url : '../../queryMedAffiOutDetailInvList.do?isCheck=false&store_id=' 
	         					+ liger.get("store_id").getValue().split(",")[0] + '&bus_type_code=' 
	         					+ liger.get("bus_type_code").getValue() + '&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price,
	         			pageSize : 500,
	         			onSuccess: function (data, g) { //加载完成时默认选中
							if (detailGrid.editor.editParm) {
								var editor = detailGrid.editor.editParm.record;
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
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		},
	         		ontextBoxKeyEnter: function (data) {
	         			f_detail_onSelectRow_detail(data.rowdata);
					}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', minWidth : 100, align : 'left'
     		}, { 
     			display : '即时库存', name : 'imme_amount', minWidth : 100, align : 'left', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 100, align : 'left', editor : {type : 'float'}
    		}, { 
    			display: '单价', name: 'price', width: 140, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:140,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			}
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 140, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:140,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			}
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 140, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:140,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p08005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08005 }', 1);
    			}
    		} ], 
    	    usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : true, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, 
    		onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, 
    		onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',columnWidth:140, 
    		data : f_getInvDetailData(row),
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    			{ text: '添加行', id:'add', click: detailGridAddRow,icon:'add' },
    			{ line:true }
    		]} 
		});

		
		//默认添加行
		detailGridAddRow();
		
    }
    
    //添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 1000);
    }
    //获得选中药品行后
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
    	//alert("是否存在数据："+JSON.stringify(rowdata.inv_detail_data));
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
    				bus_type_code : liger.get("bus_type_code").getValue(), 
            		inv_id : rowdata.inv_id,
            		batch_no : rowdata.batch_no,
            		bar_code : rowdata.bar_code,
            		price : rowdata.price,
            		amount : rowdata.amount
            	}
        		ajaxJsonObjectByUrl("../../queryMedAffiInvByFifo.do?isCheck=false",invPara,function(responseData){
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
		//alert(dataDetail.length);
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
			//当编辑完数量后，自动计算金额、零售金额、批发金额
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
    
    //删除明细
    function deleteDetailRow(){ 
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
	
	//添加药品
	/* function addInv(){
		var store_id = liger.get("store_code").getValue();
    	if(!store_id){
    		$.ligerDialog.warn('请选择仓库');
    		return false;
    	}
		
		var paras = store_id;
		//alert(paras);
		$.ligerDialog.open({
			url: "medMedAffiOutInvFifoPage.do?isCheck=false&paras="+paras, height: 450,width: 950, 
			title:'添加药品',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
				{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	} */
	
	//配套导入
	function matchImp(){
		var para = "store_id=" + liger.get("store_id").getValue() +
		"&store_text=" + liger.get("store_id").getText();
		
		$.ligerDialog.open({
			title: '配套导入',
			height: 450,
			width: 950,
			url: 'medAffiOutCommonMatchImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});  
	}
	
	//整单出库页面
	function wholeOut(){
		var para = "store_id=" + liger.get("store_id").getValue() +
		"&dept_id=" + liger.get("dept_id").getValue();
		
		$.ligerDialog.open({
			title: '整单出库',
			height: 400,
			width: 950,
			url: 'medAffiOutCommonWholeOutPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1,
			buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
		});
		
	}
	
	
	
	//历史导入
	/* function medOutHistoryImport(){		
		var dept_id = liger.get("dept_id").getValue();   	
    	if(!dept_id){   		
    		$.ligerDialog.warn('请选择领用科室');   		
    		return false;
    	}    	
		var store_id = liger.get("store_id").getValue();   	
    	if(!store_id){   		
    		$.ligerDialog.warn('请选择仓库');  		
    		return false;
    	}		
		var paras = dept_id+"@"+store_id;		
		$.ligerDialog.open({
			url: "medOutMainHistoryPage.do?isCheck=false&paras="+paras, 
			height: 500,width: 900, title:'历史导入',modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,
			buttons: [ 
				{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
    } */
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);//保存
		hotkeys('D', remove);//删除
		//hotkeys('P', printDate);//打印
		hotkeys('W', wholeOut);//整单出库
		hotkeys('M', matchImp);//配套导入
		hotkeys('N', addNew);//增加行
		hotkeys('C', this_close);//关闭
	}
	
	//打印
	function printDate(){
	
	}
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	function is_addRow(){//当数据为空时 默认新增一行
		setTimeout(function () {
			var data = grid.getData();
			if (data.length==0)
				grid.addRow();
			}, 1000);
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	
	var begin_bar;
	//按条码自动填充药品信息
	function paddingInv_beginBar(){
		if(event.keyCode==13){  
			begin_bar = $("#begin_bar").val();
			var store_id = liger.get("store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar){
				var para={
						flag: 1, //出单独药品
						store_id: store_id, 
						bar_code: begin_bar 
		    	}
				ajaxJsonObjectByUrl("../../queryMedAffiOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
					}
				});
			}else{
				$.ligerDialog.warn('请选择仓库或者输入条码');  		
	    		return false;
			}
		}
	}
	
	//按条码自动填充药品信息
	function paddingInv_endBar(){
		if(event.keyCode==13){  
			var end_bar = $("#end_bar").val();
			var store_id = liger.get("store_id").getValue().split(",")[0]; 
			if(store_id && begin_bar && end_bar){
				var para={
						flag: 2, 
						store_id: store_id, 
						bar_code: begin_bar, 
						bar_code_end: end_bar 
		    	}
				ajaxJsonObjectByUrl("../../queryMedAffiOutInvListByBar.do?isCheck=false",para,function (responseData){
					if(responseData.state == "true"){
				    	grid.addRows(responseData.invData.Rows);//添加数据
				    	$("#begin_bar").val("");
				    	$("#end_bar").val("");
					}
				});
			}else{
				$.ligerDialog.warn('请选择仓库或者输入条码');  		
	    		return false;
			}
		}
	}
	
    </script>
  
</head>
  
<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	        <tr>	        	
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库单号：</td>
			    <td align="left" class="l-table-edit-td"><input name="out_no" type="text" id="out_no" ltype="text" value="自动生成"/></td>
			   
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			    <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			    
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			    <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			    
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库日期：</td>
			    <td align="left" class="l-table-edit-td"><input name="out_date" type="text" id="out_date" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			    
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>领用科室：</td>
			    <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			    
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领料人：</td>
			    <td align="left" class="l-table-edit-td"><input name="dept_emp" type="text" id="dept_emp" ltype="text" validate="{maxlength:20}" /></td>
			   
	        </tr>
	        <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品用途：</td>
			    <td align="left" class="l-table-edit-td">
			    	<input name="use_code" type="text" id="use_code" ltype="text" validate="{required:false}" />
			    </td>
			    
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
			   	<td align="left" class="l-table-edit-td" colspan="4">
			    	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false}" />
			    </td>
			</tr> 
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
				<td align="left" class="l-table-edit-td" colspan="3">
					<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;"></textarea>
				</td> 
				<td align="center" class="l-table-edit-td" colspan="4" ><!-- style="display: none;"> -->
					<fieldset style="border:1px solid #00CC00; width: 450px;">
						<legend style="color: #000">扫码出库(注：录入条码敲回车)</legend>
						<table cellpadding="0" cellspacing="0" class="l-table-edit">
							<tr>
								<td align="right" class="l-table-edit-td">
									条形码：
								</td>
								<td align="left" class="l-table-edit-td">
									<input name="begin_bar" type="text" id="begin_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_beginBar()"/>
								</td>
								<td align="right" class="l-table-edit-td">
									至：
								</td>
								<td align="left" class="l-table-edit-td">
									<input name="end_bar" type="text" id="end_bar" ltype="text" validate="{required:false}" onkeypress="paddingInv_endBar()"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr> 
			<tr>
				<td>
					<input name="is_dir" type="hidden" id="is_dir" />
					<input name="inId" type="hidden" id="inId" />
				</td>
			</tr>
	    </table>
    </form>
    <div position="center" >
		<div id="maingrid"></div>
	</div>
</body>
</html>
