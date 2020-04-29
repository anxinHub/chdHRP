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
	<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     var state = '${medInMain.state}';
	 var by_sup_inv = '${p08021 }';
	 var by_batch_price = '${p08030 }';
     
     $(function (){
		loadDict()//加载下拉框
        //loadForm();
		loadHead();
		queryDetail();

		$("#store_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
		$("#sup_code").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		})
     });  
     
     function validateGrid() {  
  		//主表
  		if(liger.get("bus_type_code").getValue() == null || liger.get("bus_type_code").getValue() == ""){
  			$.ligerDialog.warn("业务类型不能为空");  
  			return false;  
  		}
  		if(liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == ""){
  			$.ligerDialog.warn("仓库不能为空");  
  			return false;  
  		}
  		if($("#in_date").val() == null || $("#in_date").val() == ""){
  			$.ligerDialog.warn("入库日期不能为空");  
  			return false;  
  		}
  		//明细
  		var msg="";
		var priceMsg = "";
  		var rowm = "";
 		var store_inv = "";
 		var rows = 0;
  		var data = gridManager.getData();
  		//alert(JSON.stringify(data));
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
 			if (v.inv_id) {
 				//给默认批次用于判断
				if(!v.batch_no){
					v.batch_no = '-';
				}
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
						if(v.batch_no != "-"){
							//如果药品按保质期管理，则判断有效日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id, 
								batch_no: v.batch_no,  
								inva_date: v.inva_date 
							}
							ajaxJsonObjectByUrl("../../queryMedInvBatchInva.do?isCheck=false", para, function (responseData){
								if(responseData.state=="false"){
									msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的有效日期应为"+responseData.inva_date+"<br>";
								}
							}, false);
						}
					}
				}
				if(v.is_disinfect == 1){
					if(!v.disinfect_date){
						msg += "第"+(i+1)+"行灭菌药品必须输入灭菌日期。<br>";
					}else{
						if(v.batch_no != "-"){
							//如果药品是灭菌药品，则判断灭菌日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id, 
								batch_no: v.batch_no,  
								disinfect_date: v.disinfect_date 
							}
							ajaxJsonObjectByUrl("../../queryMedInvBatchDisinfect.do?isCheck=false", para, function (responseData){
								if(responseData.state=="false"){
									msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的灭菌日期应为"+responseData.disinfect_date+"<br>";
								}
							}, false);
						}
					}
				}
				if(v.batch_no != "-"){
					//同一批号的单价必须一致
					var para = {
						inv_id: v.inv_id, 
						batch_no: v.batch_no,   
						price: v.price 
					}
					ajaxJsonObjectByUrl("../../queryMedInvBatchPrice.do?isCheck=false", para, function (responseData){
						if(responseData.state=="false"){
							priceMsg += "第"+(i+1)+"行批号"+v.batch_no+"对应的单价应为"+responseData.price + ", ";// + ", <br>";
						}
					}, false);
				}
				
				//如果条码为空给默认条码用于判断
				if(!v.sn){
					v.sn = '-';
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
	  		}
  		});
 		if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}
 		//判断仓库药品关系
 		if(store_inv.length > 0){
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMedStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					msg += "药品"+responseData.inv_ids+"不在该仓库中！<br>";
				}
			}, false);

			if(by_sup_inv == 1){
	 			//如果供应商不为空则验证药品供应商关系
	 			if(liger.get("sup_code").getValue()){
		 			para = {
		 	 			inv_ids: store_inv.substring(0, store_inv.length-1), 
		 	 			sup_id: liger.get("sup_code").getValue().split(",")[0]
		 	 		}
		 	 		ajaxJsonObjectByUrl("../../existsMedSupIncludeInv.do?isCheck=false", para, function (responseData){
		 	 			if(responseData.state=="false"){
		 	 				msg += "药品"+responseData.inv_ids+"不属于该供应商！<br>";
		 	 			}
		 	 		}, false);
	 			}
			}
 		}
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		} 	 	
  		if(priceMsg){
			//提示单价不同是否继续保存
			if(by_batch_price  == 0){
				/* $.ligerDialog.confirm(priceMsg+'确定继续保存单据?', function (yes){
					if(!yes){
						return false;
					}
				}); */
				if(!confirm(priceMsg+'确定继续保存单据?')){
					return false;
				}
			}else{//提示单价不同并中断保存操作
				$.ligerDialog.warn(priceMsg);
				return false;
			}
		}
  		return true;	
  	}
      
	function  save(){
     	if(validateGrid()){
	        var formPara={
	        	in_id : $("#in_id").val(),
				in_no : $("#in_no").val(),
				bus_type_code : liger.get("bus_type_code").getValue(),
				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
				sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
				in_date : $("#in_date").val(),
				brief : $("#brief").val(),
				old_money_sum : $("#money_sum").val(),
				state : state,
				detailData : JSON.stringify(gridManager.getData())
			};
	        ajaxJsonObjectByUrl("updateMedInitIn.do",formPara,function(responseData){
	            if(responseData.state=="true"){
	            	parentFrameUse().query();
	    			queryDetail();
	            }
	        });
     	}
    }
	
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'in_id',
			value : '${medInMain.in_id}'
		});

    	//加载查询条件
    	grid.loadData(grid.where);
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
   
    function saveMedInMain(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	//字典下拉框
    	autocomplete("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'init'}, false, '${medInMain.bus_type_code}');
		autocompleteAsync("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true);//, "", false, '${medInMain.store_id},${medInMain.store_no}');
		liger.get("store_code").setValue("${medInMain.store_id},${medInMain.store_no}");
		liger.get("store_code").setText("${medInMain.store_code} ${medInMain.store_name}");
		autocomplete("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true);//, "", false, '${medInMain.sup_id},${medInMain.sup_no}');
		if("${medInMain.sup_id}"){
			liger.get("sup_code").setValue("${medInMain.sup_id},${medInMain.sup_no}");
			liger.get("sup_code").setText("${medInMain.sup_code} ${medInMain.sup_name}");
		}

        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#in_date").ligerTextBox({width:160});
        $("#brief").ligerTextBox({width:320});
        if(state > 1){
    		$("#save").ligerButton({click: save, width:90, disabled:true});
        }else{
    		$("#save").ligerButton({click: save, width:90});
        }
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '明细ID',
				name : 'in_detail_id',
				minWidth : 100,
				align : 'left', 
				hide: true
			}, {
				display : '药品变更号',
				name : 'inv_no',
				align : 'left', 
				width : 80,
				hide: true
			}, {
				display : '药品编码',
				name : 'inv_code',
				minWidth : 100,
				align : 'left',
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
			}, {
				display : '药品名称(E)',
				name : 'inv_id',
				textField : 'inv_name',
				minWidth : 180,
				align : 'left',
				editor : {
					type : 'select',
					valueField : 'inv_id',
					textField : 'inv_name',
					selectBoxWidth : '80%',
					selectBoxHeight : 240,
					grid : {
						columns : [ {
							display : '药品编码',
							name : 'inv_code',
							minWidth : 100,
							align : 'left'
						}, {
							display : '药品名称',
							name : 'inv_name',
							minWidth : 120,
							align : 'left'
						}, {
							display : '物资类别',
							name : 'med_type_name',
							width : 100,
							align : 'left'
						}, {
							display : '规格型号',
							name : 'inv_model',
							minWidth : 80,
							align : 'left'
						}, {
							display : '计量单位',
							name : 'unit_name',
							minWidth : 80,
							align : 'left'
						}, {
							display : '生产厂商',
							name : 'fac_name',
							minWidth : 100,
							align : 'left'
						}, {
							display : '计划单价',
							name : 'plan_price',
							minWidth : 80,
							align : 'left',
							render : function(rowdata, rowindex, value) {
								return rowdata.plan_price ==null ? "" : formatNumber(rowdata.plan_price, '${p08006}', 1);
							}
						}, {
							display : '是否条码',
							name : 'is_bar',
							minWidth : 80,
							align : 'left',
							render : function(rowdata, rowindex, value) {
								return rowdata.is_bar == 1 ? '是' : '否';
							}
						}, {
							display : '货位名称',
							name : 'location_name',
							minWidth : 100,
							align : 'left'
						} ],
						switchPageSizeApplyComboBox : false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						url : '../../queryMedInvList.do?isCheck=false&is_com=0&store_id=' 
							+ liger.get("store_code").getValue().split(",")[0]
							+'&sup_id=' + liger.get("sup_code").getValue().split(",")[0],
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
					keySupport : true,
					autocomplete : true,
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
			}, {
				display : '规格型号',
				name : 'inv_model',
				minWidth : 80,
				align : 'left'
			}, {
				display : '计量单位',
				name : 'unit_name',
				minWidth : 80,
				align : 'left'
			}, {
				display : '数量(E)',
				name : 'amount',
				minWidth : 80,
				type : 'float',
				align : 'left',
				editor : {
					type : 'float',
				},
				totalSummary: {
					align : 'left',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
                    }
                }
			}, {
				display : '单价(E)',
				name : 'price',
				minWidth : 80,
				align : 'right',
				editor : {
					type : 'numberbox',
					precision : '${p08006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, {
				display : '金额',
				name : 'amount_money',
				minWidth : 80,
				width : 100,
				editor : {
					type : 'numberbox',
					precision : '${p08006 }'
				},
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
				display : '生产批号(E)',
				name : 'batch_no',
				minWidth : 80,
				editor : {
					type : 'text',
				},
				align : 'left'
			}, {
				display : '批次',
				name : 'batch_sn',
				minWidth : 80,
				align : 'left'
			}, {
				display : '有效日期(E)',
				name : 'inva_date',
				type: 'date',
				format: 'yyyy-MM-dd',
				width : 100,
				align : 'left',
				editor : {
					type : 'date',showSelectBox:false
				},
			}, {
				display : '灭菌日期(E)',
				name : 'disinfect_date',
				type: 'date',
				format: 'yyyy-MM-dd',
				width : 100,
				align : 'left',
				editor : {
					type : 'date',showSelectBox:false
				},
			}, {
				display : '条形码(E)',
				name : 'sn',
				minWidth : 80,
				align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '货位名称',
				name : 'location_name',
				minWidth : 80,
				align : 'left'
				/* textField : 'location_name',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryMedLocationDict.do?isCheck=false',
					keySupport : true,
					autocomplete : true
				},
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return rowdata.location_name;
				} */
			}, {
				display : '零售单价(E)',
				name : 'sell_price',
				width : 90,
				align : 'right',
				editor : {
					type : 'numberbox',
					precision : '${p08006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
					return value == null ? "" : formatNumber(value, '${p08006 }', 1);
				}
			}, {
				display : '零售金额',
				name : 'sell_money',
				width : 110,
				align : 'right',
				editor : {
					type : 'numberbox',
					precision : '${p08006 }'
				},
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
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryMedInitInDetail.do?isCheck=false',
			width : '100%',
			height : '93%',
			checkbox : true,
			enabledEdit : state == 1 ? true : false,
			alternatingRow : true,
			onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,
			onAfterEdit : f_onAfterEdit,
			isScroll : true,
			rownumbers : true,
			onsuccess:function(){
				
				//is_addRow();
			},
			delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '删除（<u>D</u>）', id : 'delete', click : remove, icon : 'delete', disabled: state == 1 ? false : true
				}, {
					line : true/*
				}, {
					text : '添加行（<u>H</u>）', id : 'add_Row', click : is_addRow, icon : 'add'
				}, {
					line : true*/
				}, {
					text : '入库确认（<u>X</u>）', id : 'confirm', click : confirm_in, icon : 'account', disabled: state == 1 ? false : true
				}, {
					line : true
				}, {
					text : '导入（<u>I</u>）', id : 'imp', click : imp, icon : 'up', disabled: state == 1 ? false : true
				},{
					line : true
				}, { 
					text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' 
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
    
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
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
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "inv_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					inv_code : data.inv_code,
					inv_no : data.inv_no,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					price : data.plan_price == null ? "" : data.plan_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					is_bar : data.is_bar == null ? 0 : data.is_bar,
					is_quality : data.is_quality == null ? 0 : data.is_quality,
					is_disinfect : data.is_disinfect == null ? 0 : data.is_disinfect,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name
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
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//e.column.name.focus();
			return false;
		}else if (e.column.name == "amount" && e.value == 0){
			return false;
		}else if (e.column.name == "price" && e.value == 0){
			return false;
		}
		*/
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != "" && e.value != 0){
			if(e.column.name == "inv_id"){
				if(e.record.is_quality){
					//判断是否为自动有效日期
					if("${p08009}" != 0){
						grid.updateCell('inva_date', getDateAddDay(new Date(), "${p08009}"), e.rowindex);
					}
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
			}else if (e.column.name == "price"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('amount_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "amount_money"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('price', e.value / e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sell_price"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sell_money"){
				//自动计算金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sell_price', e.value / e.record.amount, e.rowindex);
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
    
    function remove(flag){
    	if(state > 1){
    		if(flag == "")
    		$.ligerDialog.error('修改失败，单据不是未审核状态！');
    		return;
    	}
    	grid.deleteSelectedRow();
    }
    
	//未审核单据，默认添加空行 
    function is_addRow(){
    	if(state > 1){
	    	return;
    	}
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }
    
  	
  //批量入库确认
	function confirm_in() {
		var is_store='${p08045 }';
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var today = todayYear + '-' + todayMonth + '-' + todayDate;
			var confirmDate;
			if('${p08047 }'==0){
				confirmData(today);
			}else{
				$.ligerDialog.open({
					content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
					width: 300,
					height: 150,
					buttons: [
						{ 
							text: '确定',
							onclick: function (item, dialog) {
								confirmDate = $("#confirmDate").val();
							
								if (confirmDate) {
									dialog.close();
									confirmData(confirmDate)
								}
							}
						},
		                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
					]
				})
			}
				
		}
	}
	
    //确认
    function confirmData(confirmDate){
    	
    	if(state == 3){
			$.ligerDialog.error("入库确认失败！单据已入库确认");
			return;
		}
		var formPara={
 			group_id : $("#group_id").val(),
     		hos_id : $("#hos_id").val(),
     		copy_code : $("#copy_code").val(),
     		in_id : $("#in_id").val(),
     		confirm_date : confirmDate
 		};
		$.ligerDialog.confirm('确定入库确认?', function (yes){
			if(yes){
				
				ajaxJsonObjectByUrl("confirmMedInitIn.do", formPara, function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 3;
						change_button();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		}); 
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('P', printDate);
		hotkeys('M', printSet);
		hotkeys('I', imp);
		hotkeys('C', this_close);
	}

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
		
		/* var para={
			in_id:$("#in_id").val(),
			template_code:'08008',
			use_id:useId
		};
		
		printTemplate("queryMedInByPrintTemlateQc.do",para); */
		
		var para={
				template_code:'08006',
				class_name:"com.chd.hrp.med.serviceImpl.storage.in.MedStorageInServiceImpl",
				method_name:"queryMedInByPrintTemlateNewPrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:false,//是否预览，默认直接打印
				in_id:$("#in_id").val(),
				use_id:useId
		}; 
		officeFormPrint(para);
    	
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
		
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/initial/in/storageInPrintSetPageQc.do?template_code=08008&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
		officeFormTemplate({template_code:"08006",use_id:useId});
	}
    
    function add_rows(data){
     	//先清除数据然后再添加
     	grid.deleteAllRows();
     	grid.addRows(data);
    }
    
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }

	function imp(){
		if(state > 1){
			$.ligerDialog.error('修改失败，单据不是未审核状态！');
			return;
		}
		$.ligerDialog.open({
			title:'导入',
			height: 400,width: 900, 
			url: 'medInitInImprotPage.do?isCheck=false', 
			modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
		});
	}
	function this_close(){
		frameElement.dialog.close();
	}
	function change_button(){
		if(state == 1){
			$("#save").ligerButton({click: save, width:90, disabled:false});
		}else{
			$("#save").ligerButton({click: save, width:90, disabled:true});
		}
	}
    </script>
  
</head>
  
<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${medInMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${medInMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${medInMain.copy_code}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${medInMain.in_id}" ltype="text" />
	            	<input name="money_sum" type="text" id="money_sum" value="${medInMain.money_sum}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>入库单号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="in_no" type="text" id="in_no" value="${medInMain.in_no}" ltype="text" disabled="disabled" required="true" validate="{required:true}"/>
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>业务类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>仓库：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	入库日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="in_date" id="in_date" type="text" value="${medInMain.in_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	供应商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					<span style="color:red">*</span>备&nbsp;&nbsp;&nbsp;&nbsp;注：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${medInMain.brief}" required="true" validate="{required:true,maxlength:50}" />
	            </td>
	        </tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>
				<td align="center" class="l-table-edit-td" width="48%" >
					制单人：${medInMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="48%" >
					确认人：${medInMain.confirmer_name}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="2" >
					<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
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
