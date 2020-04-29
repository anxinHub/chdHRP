<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
     var grid;
     var detailGrid;
     var gridManager = null;
	 var isEmpByDept = '${p08043 }' == 1 ? true : false;
		
     $(function (){
		$("#layout1").ligerLayout({
			topHeight:145,
        	centerBottomHeight:80
		});
        loadDict();
        //loadForm();
        loadHead(null);	
      	//仓库绑定change事件
		$("#bus_type_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
		$("#store_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
		//科室绑定change事件
		$("#dept_id").bind("change",function(){
			if(isEmpByDept && liger.get("dept_id").getValue()){
		    	var para = {dept_id : liger.get("dept_id").getValue() == "" ? "0" : liger.get("dept_id").getValue().split(",")[0]};
		    	liger.get("dept_emp").set("parms", para);
		    	liger.get("dept_emp").reload();
		    	liger.get("dept_emp").setValue("");
		    	liger.get("dept_emp").setText("");
		    	//autocomplete("#dept_emp", "../../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true, para, true);
			}
		});
     });  
     
     function  save(){
		grid.endEdit();
    	 
    	var validate_detail_buf = new StringBuffer();
    	
		//弹出框验证 
    	var bus_type_code = liger.get("bus_type_code").getValue();if(!bus_type_code){validate_detail_buf.append("请选择业务类型<br>");}
     	
    	var store_id = liger.get("store_id").getValue();if(!store_id){validate_detail_buf.append("请选择业务仓库<br>");}
    	
    	var out_date = $("#out_date").val();if(!out_date){validate_detail_buf.append("请选择出库日期<br>");}
    	
    	var dept_id = liger.get("dept_id").getValue();if(!dept_id){validate_detail_buf.append("请选择领用科室<br>");}
    	
    	var dept_emp = liger.get("dept_emp").getValue();//if(!dept_emp){validate_detail_buf.append("请选择领料人<br>");}
		
    	var out_detail_data = gridManager.rows;
    	var targetMap = new HashMap();
 		var store_inv = "";  //用于判断是否属于该仓库
 		var rows = 0;

 		if(out_detail_data.length > 0){

 			$.each(out_detail_data, function(d_index, d_content){ 
				if(typeof(d_content.inv_id) != "undefined" && d_content.inv_id != null && d_content.inv_id != ""){  		  
	 	      		var value="第"+(d_index+1)+"行";

	 	      		if(typeof(d_content.amount) == "undefined" || d_content.amount == "" || d_content.amount == 0){
						validate_detail_buf.append(value+"数量不能为空或零 请输入<br>");
	       		  	}
	 	      		
	 	      		if(bus_type_code == "21"){
		 	      		if(d_content.amount > 0){
		 	      			validate_detail_buf.append(value+"退库数量不能大于0 请重新输入<br>");
		   		  		}
	 	      		}else{
		 	      		if(d_content.amount < 0){
		 	      			validate_detail_buf.append(value+"出库数量不能小于0 请重新输入<br>");
		   		  		}
		 	      		if(d_content.imme_amount - d_content.amount <0){
		 	      			validate_detail_buf.append(value+"数量不能大于即时库存数据数量 请重新输入<br>");
		   		  		}
	 	      		}
	 	      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
	
	 				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
	 					
	 					targetMap.put(key ,value);
	 				}else{
	 					
	 					validate_detail_buf.append(targetMap.get(key)+"与"+value+"药品编码、生产批号、条形码、单价不能重复<br>");
	 				}
	 				store_inv += d_content.inv_id + ",";
	 				rows += 1;
				}
			}); 
 		}

 		if(rows == 0){
 			$.ligerDialog.warn('请选择药品');
 			return false;
 		}
 		
 		//判断仓库药品关系
 		if(store_inv.length > 0){
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_id").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../../existsMedStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("以下药品不在该仓库中：<br>"+responseData.inv_ids);  
					//return false;
					validate_detail_buf.append("以下药品不在该仓库中：<br>"+responseData.inv_ids);
				}
			}, false);
 		}

 		if(validate_detail_buf.toString()  != ""){
 			$.ligerDialog.warn(validate_detail_buf.toString());
 			return false;
 		}
    	 
        var formPara={
        		bus_type_code: liger.get("bus_type_code").getValue(),
        		store_id: liger.get("store_id").getValue().split(",")[0],
        		store_no: liger.get("store_id").getValue().split(",")[1],
        		brief: $("#brief").val(),
        		out_date: $("#out_date").val(),
        		dept_id: liger.get("dept_id").getValue().split(",")[0],
        		dept_no: liger.get("dept_id").getValue().split(",")[1],
        		dept_emp: liger.get("dept_emp").getValue() == null ? "" : liger.get("dept_emp").getValue().split(",")[0],
        		use_code : liger.get("use_code").getValue() == null ? "" : liger.get("use_code").getValue(),
        		proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
        		is_dir: $("#is_dir").val(),
        		detailData: JSON.stringify(out_detail_data)
		};
        var url = "${medOutMain.url}" ? "${medOutMain.url}" : "addMedOutMain";
        ajaxJsonObjectByUrl(url+".do",formPara,function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	parentFrameUse().openUpdate(responseData.update_para);
            	this_close();
            }
        });
    }
     
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
    //$("form").ligerForm();
 }       
   
    function saveMedOutMain(){
        if($("form").valid()){
            save();
        }
	}
    function loadDict(){
    	//字典下拉框
    	$("#out_no").ligerTextBox({width:160,disabled: true }); 
            
    	$("#out_date").ligerTextBox({width:160}); 
    	autodate("#out_date");
    	
    	$("#brief").ligerTextBox({width:465}); 
            
    	autocompleteAsync("#store_id", "../../../queryMedStore.do?isCheck=false", "id", "text", true, true, {is_com : 0});
    	if("${medOutMain.store_id}"){
	    	liger.get("store_id").setValue("${medOutMain.store_id},${medOutMain.store_no}");
	    	liger.get("store_id").setText("${medOutMain.store_code} ${medOutMain.store_name}");
    	}
    	var bus_type_code_paras={sel_flag : "out"};
    	autocomplete("#bus_type_code", "../../../queryMedBusType.do?isCheck=false", "id", "text", true, true,bus_type_code_paras, false, "${medOutMain.bus_type_code}");
		
    	autocompleteAsync("#dept_id", "../../../queryMedDeptDict.do?isCheck=false", "id", "text", true, true,{is_last: 1});
    	if("${medOutMain.dept_id}"){
	    	liger.get("dept_id").setValue("${medOutMain.dept_id},${medOutMain.dept_no}");
	    	liger.get("dept_id").setText("${medOutMain.dept_code} ${medOutMain.dept_name}");
    	}
    	autocomplete("#dept_emp", "../../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true, isEmpByDept ? {dept_id: '${medOutMain.dept_id}'} : "");
    	if("${medOutMain.dept_emp}"){
	    	liger.get("dept_emp").setValue("${medOutMain.dept_emp},${medOutMain.emp_no}");
	    	liger.get("dept_emp").setText("${medOutMain.emp_code} ${medOutMain.emp_name}");
    	}
    	autocomplete("#use_code", "../../../queryMedOutUse.do?isCheck=false", "id", "text", true, true);
    	if("${medOutMain.use_code}"){
    		liger.get("use_code").setValue("${medOutMain.use_code}");
        	liger.get("use_code").setText("${medOutMain.use_name}");
    	}
    	autocomplete("#proj_code", "../../../queryMedProj.do?isCheck=false", "id", "text", true, true);
    	if("${medOutMain.proj_id}"){
    		liger.get("proj_code").setValue("${medOutMain.proj_id}");
        	liger.get("proj_code").setText("${medOutMain.proj_code} ${medOutMain.proj_name}");
    	} 
		$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
    
	function loadHead(){
    	var medOutDetail  = ${medOutDetail};
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
							display : '别名', name : 'alias', width : 120, align : 'left'
						}, {
	         				display : '药品类别', name : 'med_type_name', width : 80, align : 'left'
	         			}, {
	         				display : '规格型号', name : 'inv_model', width : 180, align : 'left'
	         			}, {
							display : '计量单位', name : 'inv_model', width : 70, align : 'left'
						}, {
							display : '包装规格', name : 'inv_structure', width : 70, align : 'left'
						}, {
	         				display : '生产厂商', name : 'fac_name', width : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 80, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 50, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 50, align : 'left'
	         			}, { 
	         				display : '是否收费', name : 'is_charge', width : 80, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
	         			}, {
	         				display : '货位', name : 'location_name', width : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
	         			url : '../../../queryMedStockInvListOut.do?isCheck=false&is_com=0&store_id=' 
	         					+ liger.get("store_id").getValue().split(",")[0] + '&bus_type_code=' 
	         					+ liger.get("bus_type_code").getValue(),
	         			pageSize : 200,
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
	         		delayLoad : false, keySupport : true, autocomplete : true,// rownumbers : true,
	         		onSuccess : function() {
	         			this.parent("tr").next(".l-grid-row").find("td:first").focus();
	         		},
	         		ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
	         	}
	         }, { 
	         	display: '规格型号', name: 'inv_model', align: 'left',width:180
	         }, { 
	         	display: '计量单位', name: 'unit_name', align: 'left',width:80
	         }, { 
	         	display: '批号', name: 'batch_no', align: 'left',width:80
	         }, { 
	         	display: '当前库存', name: 'cur_amount', align: 'left',width:80
	         }, { 
	         	display: '即时库存', name: 'imme_amount', align: 'left',width:80
	         }, { 
	         	display: '数量(E)', name: 'amount', align: 'left', width:80, editor : {type : 'number'},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
	         }, { 
				display: '单价', name: 'price', align: 'right',width:100,
				render : function(rowdata, rowindex, value) {
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
				display: '有效日期', name: 'inva_date', align: 'left',width:120
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:120
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
				display: '备注(E)', name: 'note', align: 'left',width:180,
				editor : {
					type : 'text'
				}
			}, { 
				display: '批次数量合计', name: 'sum_amount',width:80, hide: true 
			}, { 
				display: '批次明细', name: 'inv_detail_data',width:80, hide: true 
			} ],
			usePager : false, inWindow: false, width : '100%',height : '100%',
			enabledEdit : true,fixedCellHeight:true, heightDiff: 28, 
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			data: medOutDetail, checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//药品批次明细
			onLoaded: function(){this.addRowEdited()}, 
			toolbar: { items: [
				/* { text: '保存', id:'save', click: saveMedOutMain,icon:'save' },
				{ line:true }, */
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' },
				{ line:true },
				{ text: '定向出库', id:'dirImp', click: medOutDirImport,icon:'up' },
				{ line:true },
				{ text: '配套导入', id:'matchImp', click: medOutMatchedImport,icon:'up' },
				{ line:true },
				{ text: '历史使用导入', id:'hisImp', click: medOutHistoryImport,icon:'up' },
				{ line:true }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }    
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
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
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择药品！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		*/
		return true;
	} 
    
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
    			display: '药品编码', name: 'inv_code',width:100,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
    		}, { 
    			display: '药品名称(E)', name: 'inv_id', textField: 'inv_name',width:240,
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
	         			url : '../../../queryMedStockInvDetailList.do?isCheck=false&is_com=0&store_id=' 
	         					+ liger.get("store_id").getValue().split(",")[0]  + '&bus_type_code=' 
	         					+ liger.get("bus_type_code").getValue() + '&inv_id=' + row.inv_id
	         					+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price,
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
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
                    }
                }
    		}, { 
    			display: '单价', name: 'price', width: 100, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:100,
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
    			display: '批发单价', name: 'sale_price', width: 100, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:100,
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
    			display: '零售单价', name: 'sell_price', width: 100, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p08006 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:100,
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
    		dataAction : 'server',dataType : 'server',usePager : true, checkbox: true,
    		rownumbers: true, enabledEdit : true, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',columnWidth:80, data : f_getInvDetailData(row),
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    		]} 
		});

		//默认添加行
		detailGridAddRow();
    }
    
    //添加空行
    function detailGridAddRow(){
    	setTimeout(function () {detailGrid.addRow();}, 500);
    }
    
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
        		ajaxJsonObjectByUrl("../../../queryMedInvByFifo.do?isCheck=false",invPara,function(responseData){
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
		alert(dataDetail.length);
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
		/* if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择药品！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		} */
		return true;
	}
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
    
    
 	
	function medOutFifoImport(){//选择药品

		var store_id = liger.get("store_id").getValue();
		var store_text = liger.get("store_id").getText();
    	
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		
    	//var dept_id = liger.get("dept_id").getValue()?liger.get("dept_id").getValue():'null';
    	
    	//var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
		var out_id = "null";
		var paras = store_id+"@"+out_id+"@"+store_text;
		
		$.ligerDialog.open({url: "medOutMainFifoPage.do?isCheck=false&paras="+paras, height: 500,width: 900, title:'选择药品',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function medOutMatchedImport(){//配套导入
    	
    	var dept_id = liger.get("dept_id").getValue();
    	var dept_text = liger.get("dept_id").getText();
    	
    	if(!dept_id){
    		$.ligerDialog.warn('请选择领用科室');
    		return false;
    	}
    	
		var store_id = liger.get("store_id").getValue();
		var store_text = liger.get("store_id").getText();
		
    	if(!store_id){
    		
    		$.ligerDialog.warn('请选择仓库');
    		
    		return false;
    	}
		
    	//var dept_id = liger.get("dept_id").getValue()?liger.get("dept_id").getValue():'null';
    	
    	//var store_id = liger.get("store_id").getValue()?liger.get("store_id").getValue():'null';
		
		var paras = dept_id+"@"+ dept_text+"@"+ store_id +"@"+ store_text ;
		
		$.ligerDialog.open({url: "medOutMainMatchedPage.do?isCheck=false&paras="+paras, height: 500,width: 900, title:'配套导入',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function medOutDirImport(){//定向导入
		
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
			title:'定向导入',
			url: "medOutMainDirPage.do?isCheck=false&paras="+paras, 
			height: $(window).height()-100,
			width: $(window).width()-200,
			//height: 500,
			//width: 900, 
			modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,
			buttons: [ 
			           { text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}

    function medOutHistoryImport(){//历史导入
		
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

    	var para = "store_id=" + liger.get("store_id").getValue() +
    		"&store_text=" + liger.get("store_id").getText() +
    		"&dept_id=" + liger.get("dept_id").getValue() +
    		"&dept_text=" + liger.get("dept_id").getText();;
		
		$.ligerDialog.open({
			title:'历史导入',
			height: $(window).height()-100,
			width: $(window).width()-200,
			url: "medOutMainHistoryPage.do?isCheck=false&"+para, 
			modal:true,showToggle:false,showMax: true,showMin: false,isResize:true,
			buttons: [ 
				{ text: '选择', onclick: function (item, dialog) { dialog.frame.checkMedOutFifo(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}
 
	function is_addRow(){//默认新增一行
		setTimeout(function () {
			grid.addRow();
		}, 100);
	}
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}

	//关闭当前弹出框
	function this_close(){
		frameElement.dialog.close();
	}
    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
  	<input name="is_dir" type="hidden" id="is_dir" />
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_no" type="text" id="out_no" ltype="text" value="自动生成"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>制单日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date" type="text" id="out_date" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><!-- <span style="color:red">*</span> -->领料人：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_emp" type="text" id="dept_emp" ltype="text" validate="{maxlength:20}" /></td>
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
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;">${medOutMain.brief}</textarea>
			            </td> 
			            <td align="left"></td>
			        </tr> 
			    </table>
			</form>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		<div position="bottom" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
				<tr>	
					<td align="center" class="l-table-edit-td" >
						<button id ="save" accessKey="S"><b>保存（<u>S</u>）</b></button>
						&nbsp;&nbsp;
						<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
	</div>

    </body>
</html>
