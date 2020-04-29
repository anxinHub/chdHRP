<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    <script type="text/javascript">
    var grid;
    var invGrid;
    var gridManager;
    var state = '${medInMain.state}';
    var group_id = '${medInMain.group_id}';
    var hos_id = '${medInMain.hos_id}';
    var copy_code = '${medInMain.copy_code}';
    var deliver_id = '${medInMain.deliver_id}';
    var deliver_no = '${medInMain.deliver_no}';
    var year = '${medInMain.year}';
    var month = '${medInMain.month}';
    var store_id = '${medInMain.store_id}';
     $(function (){
		loadDict()//加载下拉框
        loadForm();
		loadHead();
		
		$("#store_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
		$("#sup_id").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
		
		$("body").keydown(function() {
	          if (event.keyCode == "9") {//keyCode=13是TAB 键
	          	grid.addRow();
	          }
	     });
     });  
 	
 	function validateGrid() {  
 		//主表
 		if($("#origin_no").val() == null || $("#origin_no").val()  == ""){
 			$.ligerDialog.warn("原单据号不能为空");  
 			return false;  
 		}
 		if(liger.get("store_id").getValue() == null || liger.get("store_id").getValue() == ""){
 			$.ligerDialog.warn("仓库不能为空");  
 			return false;  
 		}
 		if($("#confirm_date").val() == null || $("#confirm_date").val() == ""){
 			$.ligerDialog.warn("入库日期不能为空");  
 			return false;  
 		}
 		if(liger.get("sup_id").getValue() == null || liger.get("sup_id").getValue() == ""){
 			$.ligerDialog.warn("供应商不能为空");  
 			return false;  
 		} 
 		if(liger.get("confirmer").getValue() == null || liger.get("confirmer").getValue() == ""){
 			$.ligerDialog.warn("库管员不能为空");  
 			return false;  
 		}
 		if(liger.get("stocker").getValue() == null || liger.get("stocker").getValue() == ""){
 			$.ligerDialog.warn("采购员不能为空");  
 			return false;  
 		} 
 		//明细
 		var rowm = "";
 		var msg="";
 		var rows = 0;
 		var store_inv = "";  //用于判断是否属于该仓库
 		var sup_inv = ""; //用于判断唯一供应商
 		var data = gridManager.getData();
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.inv_id) {
				if (!v.amount) {
					rowm+="[数量]、";
				}  
				if (v.price == "" || v.price == null  || v.price == 'undefined') {  
					rowm+="[单价]、"; 
				} 
				if(rowm != ""){
					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且数量不能为0。" + "<br>";
				}
				msg += rowm;
				if(v.is_bar == 1 && !v.sn){
					msg += "第"+(i+1)+"行按条码管理的药品必须输入条形码。<br>";
				}
				if(v.is_quality == 1){
					if(!v.inva_date){
						msg += "第"+(i+1)+"行按保质期管理的药品必须输入有效日期。<br>";
					}else{
						//如果药品按保质期管理，则判断有效日期是否与上一批号一致
						var para = {
							inv_id: v.inv_id, 
							batch_no: !v.batch_no ? "-" : v.batch_no,   
							inva_date: v.inva_date 
						}
						ajaxJsonObjectByUrl("../../storage/in/queryMedStorageInBatchInva.do?isCheck=false", para, function (responseData){
							if(responseData.state=="false"){
								msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的有效日期应为"+responseData.inva_date+"<br>";
							}
						}, false);
					}
				}
				if(v.is_disinfect == 1){
					if(!v.disinfect_date){
						msg += "第"+(i+1)+"行灭菌药品必须输入灭菌日期。<br>";
					}else{
						//如果药品是灭菌药品，则判断灭菌日期是否与上一批号一致
						var para = {
							inv_id: v.inv_id, 
							batch_no: !v.batch_no ? "-" : v.batch_no,   
							disinfect_date: v.disinfect_date 
						}
						ajaxJsonObjectByUrl("../../storage/in/queryMedStorageInBatchDisinfect.do?isCheck=false", para, function (responseData){
							if(responseData.state=="false"){
								msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的灭菌日期应为"+responseData.disinfect_date+"<br>";
							}
						}, false);
					}
				}
				var key= v.batch_no == '-' ? v.inv_id+"|"+v.price+"|"+v.batch_no+"|"+v.sn+"|"+v.location_id : v.inv_id+"|"+v.batch_no+"|"+v.sn+"|"+v.location_id;
				var value="第"+(i+1)+"行";
				if(!targetMap.get(key)){
					targetMap.put(key ,value);
				}else{
					msg += targetMap.get(key)+"与"+value + v.batch_no == '-' ? "药品编码、单价、生产批号、条形码、货位不能重复" : "药品编码、生产批号、条形码、货位不能重复" + "<br>";
				}
				rows = rows + 1;
				store_inv += v.inv_id + ",";
				if(v.is_single_ven == 1){
					sup_inv += v.inv_id + ",";
				}
			}
 		});
 		if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}
 		//判断仓库药品关系
 		if(store_inv.length > 0){
 			//仓库药品对应关系
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_id").getValue().split(",")[0]
			}
			var is_flag = ajaxJsonObjectByUrl("../../existsMedStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					msg += "药品"+responseData.inv_ids+"不在该仓库中！<br>";
				}
			}, false);
			//供应商药品对应关系
			para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				sup_id: liger.get("sup_id").getValue().split(",")[0]
			}
			is_flag = ajaxJsonObjectByUrl("../../existsMedSupIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("药品"+responseData.inv_ids+"不属于该供应商！");  
					//return false;
					msg += "药品"+responseData.inv_ids+"不属于该供应商！<br>";
				}
			}, false);
		}
		//如果存在唯一供应商的药品则判断是否是唯一供应商
		if(sup_inv.length > 0){
			var para = {
				inv_ids: sup_inv.substring(0, sup_inv.length-1), 
				sup_id: liger.get("sup_id").getValue().split(",")[0]
			}
			var is_flag = ajaxJsonObjectByUrl("../../existsMedInvOnlySup.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("药品"+responseData.inv_ids+"不符合唯一供应商要求！");  
					//return false;
					msg += "药品"+responseData.inv_ids+"不符合唯一供应商要求！<br>";
				}
			}, false);
		}
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		} 	 	
 		return true;	
 	}
     
     function  save(){
    	if(validateGrid()){
	        var formPara={
	        		deliver_id : '${medInMain.deliver_id}',
	        		deliver_no : '${medInMain.deliver_no}',
					store_id : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0],
					store_no : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[1],
					confirm_date : $("#confirm_date").val(),
					stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
					sup_id : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0],
					sup_no : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[1],
					stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
					protocol_code :liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue(),
					brief : $("#brief").val(),
					old_money_sum : $("#money_sum").val(),
					detailData : JSON.stringify(gridManager.getData())
				};
		        ajaxJsonObjectByUrl("updateMedNopayDeliver.do?isCheck=false",formPara,function(responseData){
		            if(responseData.state=="true"){
		                parent.query();
		            }
		        });
	    	}
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
   
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
			        {display : '药品编码', name : 'inv_code', minWidth : 100, align : 'left',
			        	totalSummary:{ 
			        		render: function (suminf, column, cell){
		                            return '<div>合计</div>';
		                    },align: 'left'},
						}, 
					{display : '药品名称(E)', name : 'inv_id', textField : 'inv_name', minWidth : 120, align : 'left',
						editor : {
							type : 'select',
							valueField : 'inv_id',
							textField : 'inv_name',
							selectBoxWidth : 500,
							selectBoxHeight : 240,
							grid : {
								columns : [ 
								        {display : '药品编码', name : 'inv_code', minWidth : 100, align : 'left'
											}, 
										{display : '药品名称', name : 'inv_name', minWidth : 120, align : 'left'
											}, 
										{display : '药品类别', name : 'med_type_code', minWidth : 80, align : 'left'
											},
										{display : '规格型号', name : 'inv_model', minWidth : 80, align : 'left'
											}, 
										{display : '计量单位', name : 'unit_name', minWidth : 80, align : 'left'
											}, 
										{display : '包装单位', name : 'pack_name', minWidth : 80, align : 'left'
											}, 
										{display : '供应商', name : 'sup_name', minWidth : 200, align : 'left'
											}, 
										{display : '转换量', name : 'num_exchange', minWidth : 80, align : 'left'
											}, 
										{display : '生产厂商', name : 'fac_name', minWidth : 100, align : 'left'
											}, 
										{display : '计划单价', name : 'plan_price', minWidth : 80, align : 'left',
												render : function(rowdata, rowindex, value) {
													return formatNumber(rowdata.plan_price, '${p08006}', 1);
												}
											}, 
										{display : '是否条码', name : 'is_bar', minWidth : 80, align : 'left',
												render : function(rowdata, rowindex, value) {
													return rowdata.is_bar == 1 ? '是' : '否';
												}
											}, 
										{display : '证件号', name : 'cert_code', minWidth : 100, align : 'left'
											}, 
										{display : '货位名称', name : 'location_name', minWidth : 100, align : 'left'
											}, 
										{display : '零售价', name : 'sell_price', minWidth : 100, align : 'left',
												render : function(rowdata, rowindex, value) {
													return formatNumber(rowdata.sell_price, '${p08006}', 1);
												}
											} 
										],
									switchPageSizeApplyComboBox : false,
									onSelectRow: function (data) {
										var e = window.event;
										if (e && e.which == 1) {
											f_onSelectRow_detail(data);
										}
									},
									url : '../../queryMedInvList.do?isCheck=false&store_id='+ 
											liger.get("store_id").getValue().split(",")[0] +
											'&sup_id=' + liger.get("sup_id").getValue().split(",")[0],
									pageSize : 10,
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
								delayLoad : false, keySupport : true, autocomplete : true,
								onSuccess : function() {
									this.parent("tr").next(".l-grid-row").find("td:first").focus();
								},
								ontextBoxKeyEnter: function (data) {
									f_onSelectRow_detail(data.rowdata);
								}
							},
							render : function(rowdata, rowindex, value) {
								return rowdata.inv_name;
							}
						}, 
					{display : '规格型号', name : 'inv_model', minWidth : 80, align : 'left'
						}, 
					{display : '计量单位', name : 'unit_name', minWidth : 80, align : 'left'
						}, 
					//{display : '供应商', name : 'sup_name', minWidth : 200, align : 'left'
						//}, 
					{display : '数量(E)', name : 'amount', minWidth : 80, type : 'number', align : 'left',editor : {type : 'number'},
							render : function(rowdata, rowindex, value) {
								return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
							}
						}, 
					{display : '单价(E)', name : 'price', minWidth : 80, align : 'right', type : 'number',editor : {type : 'number'},
							render : function(rowdata, rowindex, value) {
								return rowdata.price == null ? "" : formatNumber(rowdata.price, '${p08006}', 1);
							}
						}, 
					{display : '金额', name : 'amount_money', minWidth : 80, type : 'number', align : 'right',
							totalSummary:{render: function (suminf, column, cell){
		                            return '<div>'+suminf.sum+'</div>';
		                    },align: 'left'},
							render : function(rowdata, rowindex, value) {
								return rowdata.amount_money == null ? "" : formatNumber(rowdata.amount_money, '${p08005}', 1);
							}
						}, 
					{display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', minWidth : 80, align : 'left',
							editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../../queryMedHosPackage.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
							},
							render : function(rowdata, rowindex, value) {
								return rowdata.pack_name;
							}
						}, 
					{display : '转换量(E)', name : 'num_exchange', minWidth : 80, type : 'int', align : 'left',editor : {type : 'int',},
							render : function(rowdata, rowindex, value) {
								return rowdata.num_exchange == null ? "" : formatNumber(rowdata.num_exchange, 2, 1);
							}
						},
					{display : '包装件数(E)', name : 'num', minWidth : 80, type : 'number', align : 'left',editor : {type : 'number'},
						}, 
					{display : '包装单价', name : 'pack_price', type : 'number', minWidth : 80, align : 'right',
							render : function(rowdata, rowindex, value) {
								return rowdata.pack_price == null ? "" : formatNumber(rowdata.pack_price, '${p08006}', 1);
							}
						},
					{display : '生产批号(E)', name : 'batch_no', minWidth : 80, align : 'left',editor : {type : 'text'}
						}, 
					{display : '有效日期(E)', name : 'inva_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', minWidth : 100,editor : {type : 'date',showSelectBox:false},
						}, 
					{display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', minWidth : 100,editor : {type : 'date',showSelectBox:false},
						},
					{display : '条形码(E)', name : 'sn', minWidth : 80, align : 'left',editor : {type : 'text'}
						}, 
					{display : '院内码', name : 'bar_code', minWidth : 80, align : 'left',editor : {type : 'text'}
						}, 
					{display : '是否个体码(E)', name : 'is_per_bar', textField : 'text', minWidth : 80, align : 'left',
							editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								data : yes_or_no.Rows,
								keySupport : true,
								autocomplete : true,
							},
							render : function(rowdata, rowindex, value) {
								return (rowdata.is_per_bar == null ? 0 : rowdata.is_per_bar) == 0 ? '否' : '是';
							}
						}, 
					{display : '零售单价(E)', name : 'sell_price', minWidth : 80, align : 'right', type : 'number',editor : {type : 'number'},
							render : function(rowdata, rowindex, value) {
								return rowdata.sell_price == null ? "" : formatNumber(rowdata.sell_price, '${p08006}', 1);
							}
						}, 
					{display : '零售金额', name : 'sell_money', minWidth : 80, type : 'number', align : 'right',
							render : function(rowdata, rowindex, value) {
								return rowdata.sell_money == null ? "" : formatNumber(rowdata.sell_money, '${p08005}', 1);
							},
						}, 
					{display : '货位名称(E)', name : 'location_id', textField : 'location_name', minWidth : 80, align : 'left',
							editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../../queryMedLocationDict.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
							},
							render : function(rowdata, rowindex, value) {
								return rowdata.location_name;
							}
						}, 
					{display : '备注(E)', name : 'note', minWidth : 80, align : 'left',editor : {type : 'text'}
						}
					],
					dataAction : 'server', dataType : 'server', usePager : false, url : 'queryMedNopayDeliverDetail.do?isCheck=false&deliver_id=${medInMain.deliver_id}', width : '100%', height : '93%',
					checkbox : true, enabledEdit : state == 1 ? true : false, alternatingRow : true, 
					onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
					onsuccess:function(){
					
						//is_addRow();
					},
					isScroll : true, rownumbers : true, delayLoad : false,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ 
				    {text : '删除（<u>D</u>）', id : 'delete', click : remove, icon : 'delete',disabled:state ==1?false:true}, 
					{line : true},
					{text : '审核（<u>S</u>）', id : 'audit', click : audit, icon : 'audit',disabled:state ==1?false:true},
					{line : true}, 
					{text : '消审（<u>X</u>）', id : 'unAudit', click : unAudit, icon : 'unaudit'}
					/* {text : '确认（<u>C</u>）', id : 'confirm', click : confirm, icon : 'account'},
					{text : '上一张（<u>B</u>）', id : 'add_inv', click : before_no, icon : 'before'}, 
					{line : true}, 
					{text : '下一张（<u>N</u>）', id : 'open_add', click : next_no, icon : 'next'}, 
					{line : true}  */
					]
				}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		grid.toggleCol("inv_no", false);
		grid.toggleCol("order_rela", false);
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
    function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
    
    function remove(){
    	grid.deleteSelectedRow();
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
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					inv_code : data.inv_code,
					inv_no : data.inv_no,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					price : data.plan_price == null ? 0.0 : data.plan_price,
					pack_code : data.pack_code == null ? "" : data.pack_code,
					pack_name : data.pack_name == null ? "" : data.pack_name,
					num_exchange : data.num_exchange == null ? "" : data.num_exchange,
					is_batch : data.is_batch == null ? 0 : data.is_batch,
					is_bar : data.is_bar == null ? 0 : data.is_bar,
					is_per_bar : data.is_per_bar == null ? 0 : data.is_per_bar,
					is_quality : data.is_quality == null ? 0 : data.is_quality,
					is_disinfect : data.is_disinfect == null ? 0 : data.is_disinfect,
					is_highvalue : data.is_highvalue == null ? 0 : data.is_highvalue,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					sell_price : data.sell_price == null ? "" : data.sell_price ,
					sup_name : data.sup_name == null ? "" : data.sup_name
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
		if (e.column.name == "inv_id" && e.value == ""){
			//e.column.name.focus();
			return false;
		}else if (e.column.name == "amount" && e.value == 0){
			return false;
		}else if (e.column.name == "price" && e.value == 0){
			return false;
		}
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != "" && e.value != 0){
			if(e.column.name == "inv_id"){
				//判断是否为自动有效日期
				if("${p08009}" != 0){
					grid.updateCell('inva_date', getDateAddDay(new Date(), "${p08009}"), e.rowindex);
				}
			}else if (e.column.name == "amount"){
				//自动计算金额
				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算零售金额
				if(e.record.sell_price != undefined && e.record.sell_price != "" && e.record.sell_price != 0){
					grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
				}
				//自动计算包装件数
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
					grid.updateCell('num', e.value / e.record.num_exchange, e.rowindex);
				}
			}else if (e.column.name == "price"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('amount_money', e.value * e.record.amount, e.rowindex);
				}
				//计算包装单价
				if(e.record.num != undefined && e.record.num != "" && e.record.num != 0){
					grid.updateCell('pack_price', e.value * e.record.num, e.rowindex);
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
					if(e.record.sell_price != undefined && e.record.sell_price != "" && e.record.sell_price != 0){
						grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
					}
				}
				//自动包装单价
				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
					grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
				}
			}else if (e.column.name == "sell_price"){
				//自动计算零售金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			}
		}
		return true;
	}
	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
			//grid.beginEdit(0);
		}, 1000);
	}
	// 审核
	function audit(){
		if (state != 1){
			$.ligerDialog.error('审核失败！'+deliver_no+'单据不是未审核状态,不能审核');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id   +"@"+ 
					copy_code    +"@"+ 
					deliver_id  +"@"+ 
					deliver_no   +"@"+ 
					state   +"@"+ 3
				) 
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateStateMedNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							self.location.reload();  //刷新本页
							parent.query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		if (state != 3){
			$.ligerDialog.error('消审失败！'+deliver_no+'单据不是已审核状态,不能消审');
			return;
		}else{
			var ParamVo =[];
			ParamVo.push(
					group_id   +"@"+ 
					hos_id  +"@"+ 
					copy_code    +"@"+ 
					deliver_id   +"@"+ 
					deliver_no   +"@"+ 
					state  +"@"+ 1
				) 
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateStateMedNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							self.location.reload();  //刷新本页
							parent.query();
						}
					});
				}
			}); 
		}
	}

    //上一张
	function before_no(){
		var paras = 
			"group_id="+group_id +"&"+ 
			"hos_id="+hos_id +"&"+ 
			"copy_code="+ copy_code +"&"+ 
			"deliver_id="+deliver_id +"&"+ 
			"deliver_no="+deliver_no  +"&"+ 
			"bus_type_code="+bus_type_code;
		ajaxJsonObjectByUrl("queryMedNopayDeliverBeforeNo.do?isCheck=false",paras.toString(),function(responseData){
			if(responseData.state=="true"){
				parent.update_open(responseData.update_para);
				this_close();
			}else{
				$.ligerDialog.warn('该单据是第一单据，没有上一张单据'); 
			}
		});   
	}
    //下一张
	function next_no(){
		var paras = 
			"group_id="+group_id +"&"+ 
			"hos_id="+hos_id +"&"+ 
			"copy_code="+ copy_code +"&"+ 
			"deliver_id="+deliver_id +"&"+ 
			"deliver_no="+deliver_no  +"&"+ 
			"bus_type_code="+bus_type_code;
		ajaxJsonObjectByUrl("queryMedNopayDeliverNextNo.do?isCheck=false",paras.toString(),function(responseData){
			if(responseData.state=="true"){
				parent.update_open(responseData.update_para);
				this_close();
			}else{
				$.ligerDialog.confirm('已经是最后一张单据了,是否添加新单据?', function (yes){
					if(yes){
				    	parent.add_open();
				    	this_close();
					}
				}); 
			}
		});   
		
	}
	

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('I', imp);
		hotkeys('C', this_close);
	}
	
	function loadDict(){
    	//字典下拉框
    	//仓库下拉框
		autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true);
    	liger.get("store_id").setValue('${medInMain.store_id},${medInMain.store_no}');
    	liger.get("store_id").setText('${medInMain.store_code} ${medInMain.store_name}');
    	
		//供应商
    	autocomplete("#sup_id", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true,'',false,'',240);
    	liger.get("sup_id").setValue('${medInMain.sup_id},${medInMain.sup_no}');
    	liger.get("sup_id").setText('${medInMain.sup_code} ${medInMain.sup_name}');
    	
    	//库管员 
		autocomplete("#confirmer", "../../queryMedManagerEmp.do?isCheck=false", "id", "text", true, true);
		liger.get("confirmer").setValue('${medInMain.confirmer}');
    	liger.get("confirmer").setText('${medInMain.confirmer_name}');
    	//采购员 
		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true);
		liger.get("stocker").setValue('${medInMain.stocker}');
    	liger.get("stocker").setText('${medInMain.stocker_name}');
    
		//采购类型
		autocomplete("#stock_type_code", "../../queryMedStockType.do?isCheck=false", "id", "text", true, true);
		liger.get("stock_type_code").setValue('${medInMain.stock_type_code}');
    	liger.get("stock_type_code").setText('${medInMain.stock_type_name}');
		
		//协议编号
		autocomplete("#protocol_code", "../../queryMedProtocolMain.do?isCheck=false", "id", "text", true, true);
		liger.get("protocol_code").setValue('${medInMain.protocol_id}');
    	liger.get("protocol_code").setText('${medInMain.protocol_code} ${medInMain.protocol_name}');
    	
    	//项目下拉框
		autocomplete("#proj_id", "../../queryMedProj.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		liger.get("proj_id").setValue('${medInMain.proj_id}');
    	liger.get("proj_id").setText('${medInMain.proj_code} ${medInMain.proj_name}');
    	
		//格式化文本框
        $("#deliver_no").ligerTextBox({width:240, disabled:true});
        $("#origin_no").ligerTextBox({width:160});
        $("#confirmer").ligerTextBox({width:160});
        $("#sup_id").ligerTextBox({width:240});
        $("#proj_id").ligerTextBox({width:240});
        $("#confirm_date").ligerTextBox({width:240});
        $("#brief").ligerTextBox({width:480});
        //格式化按钮
        if(state > 1){
    		$("#save").ligerButton({click: save, width:90, disabled:true});
        }else{
    		$("#save").ligerButton({click: save, width:90});
        }

    	$("#print").ligerButton({click: print, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
     } 

	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		officeFormTemplate({template_code:"08023",use_id:useId});
	}
	 
    //打印
    function print(){
    	
		var useId=0;//统一打印
		var para={
			paraId : '${medInMain.deliver_id}',
			template_code:'08023',
			class_name:"com.chd.hrp.med.serviceImpl.affi.check.MedAffiCheckMainServiceImpl",
			method_name:"queryMedNopaydeliverByPrintTemlate1",
			isPrintCount:false,//更新打印次数
			isPreview:true,//预览窗口，传绝对路径
			use_id:useId,
			p_num: 0
		}; 
		 	
		officeFormPrint(para);
	}
	
	function this_close(){
		frameElement.dialog.close();
	}
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	        <tr>
	        	<td align="right" class="l-table-edit-td" ><b>单据号<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="deliver_no" type="text" id="deliver_no" value="${medInMain.deliver_no}" ltype="text" disabled="disabled"/>
	            </td>
	            <td align="right" class="l-table-edit-td" ><b>原单据号<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="origin_no" type="text" id="origin_no" value="${medInMain.origin_no}" ltype="text" />
	            </td>
	            <td align="right" class="l-table-edit-td"  ><b>仓库<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_id" type="text" id="store_id" ltype="text"  validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td" ><b>编制日期<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="confirm_date" id="confirm_date" value="${medInMain.confirm_date}" type="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  ><b>库管员<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="confirmer" type="text" id="confirmer" ltype="text"  validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  ><b>采购员<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker" ltype="text"  validate="{required:true}" />
	            </td>
	        </tr> 
			<tr>
				<td align="right" class="l-table-edit-td"  ><b>供应商<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_id" type="text" id="sup_id" ltype="text"  validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" ><b>协议编号:</b></td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  ><b>采购类型:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" ><b>项&nbsp;&nbsp;目:</b></td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td" ><b>备&nbsp;注:</b></td>
	            <td align="left" class="l-table-edit-td" colspan="3">
	            	<input name="brief" type="text" id="brief" vlaue='${medInMain.brief}' ltype="text" validate="{maxlength:50}" />
	            </td>
	             <td style="display: none;">
	            	<input name="money_sum" type="text" id="money_sum" value="${medInMain.amount_money}" ltype="text" />
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					制单人：${medInMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					审核人：${medInMain.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					库管员：${medInMain.confirmer_name}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>模板设置（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
