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
	<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<%-- 	<link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/> --%>
<%-- 	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script> --%>
<%-- 	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script> --%>
    <script type="text/javascript">
     var grid;
     var gridManager;
     var state = '${matInMain.state}';
     
     $(function (){
		loadDict();//加载下拉框
		initBus();
        //loadForm();
		loadHead();
		queryDetail();
		$("#bus_type_code").bind("change", function(){changeBus();});
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
  		if(!liger.get("bus_type_code").getValue()){
  			$.ligerDialog.warn("业务类型不能为空");  
  			return false;  
  		}
  		if(!liger.get("store_code").getValue()){
  			$.ligerDialog.warn("仓库不能为空");  
  			return false;  
  		}
  		if(!$("#in_date").val()){
  			$.ligerDialog.warn("制单日期不能为空");  
  			return false;  
  		}
 		if(liger.get("bus_type_code").getValue() && liger.get("bus_type_code").getValue() == 2){
	 		if(!liger.get("stocker").getValue()){
	 			$.ligerDialog.warn("采购员不能为空");  
	 			return false;  
	 		} 
	 		if(!liger.get("stock_type_code").getValue()){
	 			$.ligerDialog.warn("采购类型不能为空");  
	 			return false;  
	 		} 
	 		if(!liger.get("sup_code").getValue()){
	 			$.ligerDialog.warn("供应商不能为空");  
	 			return false;  
	 		} 
 		}
  		//明细
 		var rowm = "";
  		var msg="";
 		var rows = 0;
 		var store_inv = "";  //用于判断是否属于该仓库
 		var sup_inv = ""; //用于判断唯一供应商
  		var data = gridManager.getData();
 		//alert(JSON.stringify(data));
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
	 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空并且数量不能为0" + "\n\r";
	 			}
	 			msg += rowm;
				if(v.is_bar == 1 && !v.sn){
					msg += "第"+(i+1)+"行按条码管理的材料必须输入条形码。<br>";
				}
				if(v.is_quality == 1){
					if(!v.inva_date){
						msg += "第"+(i+1)+"行按保质期管理的材料必须输入灭菌日期。<br>";
					}else{
						//如果材料按保质期管理，则判断有效日期是否与上一批号一致
						var para = {
							inv_id: v.inv_id, 
							batch_no: !v.batch_no ? "-" : v.batch_no,  
							inva_date: v.inva_date 
						}
						ajaxJsonObjectByUrl("queryMatStorageInBatchInva.do?isCheck=false", para, function (responseData){
							if(responseData.state=="false"){
								msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的有效日期应为"+responseData.inva_date+"<br>";
							}
						}, false);
					}
				}
				if(v.is_disinfect == 1){
					if(!v.disinfect_date){
						msg += "第"+(i+1)+"行灭菌材料必须输入灭菌日期。<br>";
					}else{
						//如果材料是灭菌材料，则判断灭菌日期是否与上一批号一致
						var para = {
							inv_id: v.inv_id, 
							batch_no: !v.batch_no ? "-" : v.batch_no,  
									disinfect_date: v.disinfect_date 
						}
						ajaxJsonObjectByUrl("queryMatStorageInBatchDisinfect.do?isCheck=false", para, function (responseData){
							if(responseData.state=="false"){
								msg += "第"+(i+1)+"行批号"+v.batch_no+"对应的灭菌日期应为"+responseData.disinfect_date+"<br>";
							}
						}, false);
					}
				}
				var key=v.inv_id +"|"+v.batch_no+"|"+v.sn+"|"+v.location_id;
	 			var value="第"+(i+1)+"行";
	 			if(!targetMap.get(key)){
	 				targetMap.put(key ,value);
	 			}else{
					msg += targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、货位不能重复" + "<br>";
	 			}
				rows = rows + 1;
				store_inv += v.inv_id + ",";
				if(v.is_single_ven == 1){
					sup_inv += v.inv_id + ","; 
				}
 			}
  		});
 		if(rows == 0){
 			$.ligerDialog.warn("请先添加材料！");  
			return false;  
 		}
 		if(store_inv.length > 0){
	 		//判断仓库材料关系
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_code").getValue().split(",")[0]
			}
			var is_flag = ajaxJsonObjectByUrl("../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不在该仓库中！");  
					//return false;
					msg += "材料"+responseData.inv_ids+"不在该仓库中！<br>";
				}
			}, false);
			//防止上面的ajax方法报错
			if(is_flag == false){
				return false;
			}
			//供应商材料对应关系
			para = {
				inv_ids: store_inv.substring(0, sup_inv.length-1), 
				sup_id: liger.get("sup_code").getValue().split(",")[0]
			}
			is_flag = ajaxJsonObjectByUrl("../../existsMatSupIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不属于该供应商！");  
					//return false;
					msg += "材料"+responseData.inv_ids+"不属于该供应商！<br>";
				}
			}, false);
			//防止上面的ajax方法报错
			if(!is_flag){
				return false;
			}
 		}
		//如果存在唯一供应商的材料则判断是否是唯一供应商
		if(sup_inv.length > 0){
			para = {
				inv_ids: sup_inv.substring(0, sup_inv.length-1), 
				sup_id: liger.get("sup_code").getValue().split(",")[0]
			}
			is_flag = ajaxJsonObjectByUrl("../../existsMatInvOnlySup.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不符合唯一供应商要求！");  
					//return false;
					msg += "材料"+responseData.inv_ids+"不符合唯一供应商要求！<br>";
				}
			}, false);
			//防止上面的ajax方法报错
			if(!is_flag){
				return false;
			}
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
     			in_id : $("#in_id").val(),
 				in_no : $("#in_no").val(),
 				bus_type_code : liger.get("bus_type_code").getValue(),
 				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
 				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
 				store_code : liger.get("store_code").getText() == null ? "" : liger.get("store_code").getText().split(" ")[0],
 				in_date : $("#in_date").val(),
 				stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
 				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
 				sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
 				stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
 				app_dept : liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue(),
 				protocol_code : liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue(),
				proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
 				brief : $("#brief").val(),
				old_money_sum : $("#money_sum").val(),
 				detailData : JSON.stringify(gridManager.getData())
 			};
 	        ajaxJsonObjectByUrl("updateMatStorageIn.do", formPara, function(responseData){
 	            if(responseData.state=="true"){
 	            	queryDetail();
 	            	parentFrameUse().query();
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
			value : '${matInMain.in_id}'
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
   
	function loadDict(){
    	//字典下拉框
    	autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, false, "${matInMain.bus_type_code}");
		autocompleteAsync("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true);//, "", false, "${matInMain.store_id},${matInMain.store_no}");
		liger.get("store_code").setValue("${matInMain.store_id},${matInMain.store_no}");
		liger.get("store_code").setText("${matInMain.store_code} ${matInMain.store_name}");
		
		autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, "", false, "${matInMain.stocker}");
		liger.get("stocker").setValue("${matInMain.stocker}}");
		liger.get("stocker").setText("${matInMain.stocker_code} ${matInMain.stocker_name}");
		
		autocompleteAsync("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		liger.get("sup_code").setValue("${matInMain.sup_id},${matInMain.sup_no}");
		liger.get("sup_code").setText("${matInMain.sup_code} ${matInMain.sup_name}");
		
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true, "", false, "${matInMain.stock_type_code}");
		
		autocomplete("#app_dept", "../../queryHosDeptByPerm.do?isCheck=false", "id", "text", true, true, {is_last : 1}, false, "${matInMain.app_dept}");
		liger.get("app_dept").setValue("${matInMain.app_dept}");
		liger.get("app_dept").setText("${matInMain.app_dept_code} ${matInMain.app_dept_name}");
		
		autocomplete("#protocol_code", "../../queryMatProtocolMain.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		liger.get("protocol_code").setValue("${matInMain.protocol_id},${matInMain.protocol_code}");
		liger.get("protocol_code").setText("${matInMain.protocol_code} ${matInMain.protocol_name}");
		
		autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		liger.get("proj_code").setValue("${matInMain.proj_id}");
		liger.get("proj_code").setText("${matInMain.proj_code} ${matInMain.proj_name}");
		//格式化文本框
        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#in_date").ligerTextBox({width:160});
        $("#brief").ligerTextBox({width:320});
        //格式化按钮
        if(state > 1){
    		$("#save").ligerButton({click: save, width:90, disabled:true});
        }else{
    		$("#save").ligerButton({click: save, width:90});
        }
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
    } 
    
    function initBus(){
    	if("${matInMain.bus_type_code}" != 2){
    		$("#stocker_span").css("display", "none");
			$("#stocker").ligerComboBox({width:160,disabled:true,cancelable: false});

    		$("#stock_type_code_span").css("display", "none");
			$("#stock_type_code").ligerComboBox({width:160,disabled:true,cancelable: false});

    		$("#sup_code_span").css("display", "none");
			$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
		}else{
    		$("#stocker_span").css("display", "inline");
			$("#stocker").ligerComboBox({width:160,disabled:false});

    		$("#stock_type_code_span").css("display", "inline");
			$("#stock_type_code").ligerComboBox({width:160,disabled:false});

    		$("#sup_code_span").css("display", "inline");
			$("#sup_code").ligerComboBox({width:160,disabled:false});
		}
    }
    
    function changeBus(){
    	if(liger.get("bus_type_code").getValue() != 2){
    		$("#stocker_span").css("display", "none");
			$("#stocker").ligerComboBox({width:160,disabled:true,cancelable: false});
	        liger.get("stocker").setValue(""); 
			liger.get("stocker").setText("");

    		$("#stock_type_code_span").css("display", "none");
			$("#stock_type_code").ligerComboBox({width:160,disabled:true,cancelable: false});
	        liger.get("stock_type_code").setValue("");
			liger.get("stock_type_code").setText("");

    		$("#sup_code_span").css("display", "none");
			$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
	        liger.get("sup_code").setValue("");
			liger.get("sup_code").setText("");
		}else{
    		$("#stocker_span").css("display", "inline");
			$("#stocker").ligerComboBox({width:160,disabled:false});
			liger.get("stocker").selectValue(liger.get("stocker").data[0].id);

    		$("#stock_type_code_span").css("display", "inline");
			$("#stock_type_code").ligerComboBox({width:160,disabled:false});
			liger.get("stock_type_code").selectValue(liger.get("stock_type_code").data[0].id);

    		$("#sup_code_span").css("display", "inline");
			$("#sup_code").ligerComboBox({width:160,disabled:false});
			liger.get("sup_code").selectValue(liger.get("sup_code").data[0].id);
		}
    }
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '明细ID', name : 'in_detail_id', width : 100, align : 'left'
			}, {
				display : '材料编码', name : 'inv_code', width : 100, align : 'left',
				/* totalSummary: {
                    align : 'right',
                    render: function (suminf, column, cell) {
                    	return '<div>合计：</div>';
                    }
                } */
			}, {
				display : '材料变更号', name : 'inv_no', align : 'left'
			}, {
				display : '材料名称(E)', name : 'inv_id', textField : 'inv_name', width : 180, align : 'left',
				editor : {
					type : 'select',
					valueField : 'inv_id',
					textField : 'inv_name',
					selectBoxWidth : 500,
					selectBoxHeight : 240,
					grid : {
						columns : [ {
							display : '材料编码', name : 'inv_code', width : 100, align : 'left'
						}, {
							display : '材料名称', name : 'inv_name', width : 120, align : 'left'
						}, {
							display : '物资类别', name : 'mat_type_name', width : 150, align : 'left'
						}, {
							display : '规格型号', name : 'inv_model', width : 120, align : 'left'
						}, {
							display : '计量单位', name : 'unit_name', width : 80, align : 'left'
						}, {
							display : '包装单位', name : 'pack_name', width : 80, align : 'left'
						}, {
							display : '转换量', name : 'num_exchange', width : 80, align : 'left'
						}, {
							display : '生产厂商', name : 'fac_name', width : 150, align : 'left'
						}, {
							display : '计划单价', name : 'plan_price', width : 80, align : 'left',
							render : function(rowdata, rowindex, value) {
								return formatNumber(rowdata.value, '${p04006 }', 1);
							}
						}, {
							display : '是否条码', name : 'is_bar', width : 60, align : 'left',
							render : function(rowdata, rowindex, value) {
								return rowdata.is_bar == 1 ? '是' : '否';
							}
						}, {
							display : '货位', name : 'location_name', width : 100, align : 'left'
						}, {
							display : '零售价', name : 'sell_price', width : 80, align : 'left',
							render : function(rowdata, rowindex, value) {
								return formatNumber(rowdata.value, '${p04006 }', 1);
							}
						} ],
						switchPageSizeApplyComboBox : false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						url : '../../queryMatInvList.do?isCheck=false&store_id=' 
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
			}, {
				display : '规格型号', name : 'inv_model', width : 120, align : 'left'
			}, {
				display : '计量单位', name : 'unit_name', width : 60, align : 'left'
			}, {
				display : '数量(E)', name : 'amount', width : 80, type : 'float', align : 'right',
				editor : {
					type : 'float',
				}
			}, {
				display : '单价(E)', name : 'price', width : 80, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p04006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '金额', name : 'amount_money', width : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				/* totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } */
			}, {
				display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width : 80, align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryMatHosPackage.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				},
			}, {
				display : '转换量(E)', name : 'num_exchange', width : 80, type : 'float', align : 'left',
				editor : {
					type : 'float',
				}
			}, {
				display : '包装件数(E)', name : 'num', width : 80, type : 'float', align : 'right',
				editor : {
					type : 'float',
				},
			}, {
				display : '包装单价', name : 'pack_price', width : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.pack_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '生产批号(E)', name : 'batch_no', width : 100, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '批次', name : 'batch_sn', width : 80, align : 'left',
			}, {
				display : '有效日期(E)', name : 'inva_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', width : 90,
				editor : {
					type : 'date',
				},
			}, {
				display : '灭菌日期(E)', name : 'disinfect_date', type: 'date', align : 'left', format: 'yyyy-MM-dd', width : 90,
				editor : {
					type : 'date',
				},
			}, {
				display : '条形码(E)', name : 'sn', width : 100, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '是否个体码', name : 'is_per_bar', width : 80, align : 'left',
				render : function(rowdata, rowindex, value) {
					if(value == 0){
						return "否";
					}else if(value == 1){
						return "是";
					}else{
						rowdata.is_per_bar = 0;
						return "否";
					}
				}
			}, {
				display : '院内码', name : 'bar_code', width : 80, align : 'left',
			}, {
				display : '批发单价(E)', name : 'sale_price', width : 80, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p04006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '批发金额', name : 'sale_money', width : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				/* totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }'', 1)+ '</div>';
                    }
                } */
			}, {
				display : '零售单价(E)', name : 'sell_price', width : 80, align : 'right', 
				editor : {
					type : 'numberbox',
					precision : '${p04006 }'
				},
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, {
				display : '零售金额', name : 'sell_money', width : 80, align : 'right',
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				/* totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                } */
			}, {
				display : '货位名称(E)', name : 'location_id', textField : 'location_name', width : 80, align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../queryMatLocationDict.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				}
			}, {
				display : '备注(E)', name : 'note', width : 160, align : 'left',
				editor : {
					type : 'text',
				}
			}, {
				display : '订单关系', name : 'order_rela', align : 'left',
			} ],
			dataAction : 'server', dataType : 'server', usePager : false, url : 'queryMatStorageInDetail.do?isCheck=false', 
			width : '100%', height : '93%', checkbox : true, enabledEdit : state == 1 ? true : false, alternatingRow : true, 
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true/* ,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '删除（<u>D</u>）', id : 'delete', click : remove, icon : 'delete', disabled: state == 1 ? false : true
				}, {
					line : true
				}, {
					text : '审核（<u>S</u>）', id : 'audit', click : audit, icon : 'audit', disabled: state == 1 ? false : true
				}, {
					line : true
				}, {
					text : '消审（<u>X</u>）', id : 'unAudit', click : unAudit, icon : 'unaudit', disabled: state == 2 ? false : true
				}, {
					line : true
				}, {
					text : '入库确认（<u>C</u>）', id : 'confirm', click : confirm, icon : 'account', disabled: state == 2 ? false : true
				}, {
					line : true
				}, {
					text : '维护发票（<u>W</u>）', id : 'invoice', click : invoice_open, icon : 'edit'
				}, {
					line : true
				}, {
					text : '上一张（<u>B</u>）', id : 'before_no', click : before_no, icon : 'before'
				}, {
					line : true
				}, {
					text : '下一张（<u>N</u>）', id : 'next_no', click : next_no, icon : 'next'
				}, {
					line : true
				}, {
					text : '条形码：<input type="text"/>', id : 'sn_imp', click : sn_imp, icon : 'up'
				}, {
					line : true
				} ]
			} */
		});
		gridManager = $("#maingrid").ligerGetGridManager();
		
		grid.toggleCol("in_detail_id", false);
		grid.toggleCol("inv_no", false);
		grid.toggleCol("order_rela", false);

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
					inv_code : data.inv_code,
					inv_no : data.inv_no,
					inv_model : data.inv_model == null ? "" : data.inv_model,
					unit_name : data.unit_name == null ? "" : data.unit_name,
					pack_code : data.pack_code == null ? "" : data.pack_code,
					pack_name : data.pack_name == null ? "" : data.pack_name,
					num_exchange : data.num_exchange == null ? "" : data.num_exchange,
					is_batch : data.is_batch == null ? 0 : data.is_batch,
					is_bar : data.is_bar == null ? 0 : data.is_bar,
					is_per_bar : data.is_per_bar == null ? 0 : data.is_per_bar,
					is_quality : data.is_quality == null ? 0 : data.is_quality,
					is_disinfect : data.is_disinfect == null ? 0 : data.is_disinfect,
					is_highvalue : data.is_highvalue == null ? 0 : data.is_highvalue,
					price : data.plan_price == null ? "" : data.plan_price,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					sell_price : data.sell_price == null ? "" : data.sell_price
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
		if (e.column.name == "inv_id" && !e.value){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && !e.value){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		if (e.column.name == "price" && !e.value){
			//$.ligerDialog.warn('单价不能为0！');
			return false;
		}
		if (e.column.name == "sn"){
			if(e.record.is_bar == 1 && !e.value){
				//$.ligerDialog.warn('按条码管理的材料必须输入条形码！');
				return false;
			}
		}
		if (e.column.name == "inva_date"){
			if(e.record.is_quality == 1 && !e.value){
				//$.ligerDialog.warn(按保质期管理的材料必须输入有效日期！');
				return false;
			}
		}
		if (e.column.name == "disinfect_date"){
			if(e.record.is_disinfect == 1 && !e.value){
				//$.ligerDialog.warn('行灭菌材料必须输入灭菌日期！');
				return false;
			}
		}
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if(e.value != "" && e.value != 0){
			if(e.column.name == "inv_id"){
				//判断是否为自动有效日期
				if('${p04009 }' != 0){
					grid.updateCell('inva_date', getDateAddDay(new Date(), '${p04009 }'), e.rowindex);
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
				//自动计算批发金额
				if(e.record.sale_price != undefined && e.record.sale_price != "" && e.record.sale_price != 0){
					grid.updateCell('sale_money', e.value * e.record.sale_price, e.rowindex);
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
				if(e.record.num_exchange != undefined && e.record.num_exchange != "" && e.record.num_exchange != 0){
					grid.updateCell('pack_price', e.value * e.record.num_exchange, e.rowindex);
				}
			}else if (e.column.name == "num_exchange"){
				//自动计算包装件数
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('num', e.record.amount / e.value, e.rowindex);
				}
				//自动包装单价
				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
					grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
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
					if(e.record.sale_money != undefined && e.record.sale_money != "" && e.record.sale_money != 0){
						grid.updateCell('sale_price', e.record.sale_money / e.record.amount, e.rowindex);
					}else if(e.record.sale_price != undefined && e.record.sale_price != "" && e.record.sale_price != 0){
						grid.updateCell('sale_money', e.record.amount * e.record.sale_price, e.rowindex);
					}
				}
			}else if (e.column.name == "sell_price"){
				//自动计算零售金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sale_price"){
				//自动计算批发金额
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sale_money', e.value * e.record.amount, e.rowindex);
				}
			}else if (e.column.name == "sale_money"){
				//自动计算批发单价
				if(e.record.amount != undefined && e.record.amount != "" && e.record.amount != 0){
					grid.updateCell('sale_price', e.value / e.record.amount, e.rowindex);
				}
			}
		}
		return true;
	}
	
	//获取明细数据
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 
    
	//根据data添加明细数量
    function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
    
    //移除明细行数据
    function remove(){
    	
    	if(state > 1){
    		$.ligerDialog.error('修改失败，单据不是未审核状态！');
    		return;
    	}
    	grid.deleteSelectedRow();
    }

	//当单据未审核 默认新增一行
    function is_addRow(){
    	if(state > 1){
	    	return;
    	}
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }
    
	//更新发票号
    function update_invoice(){
		
    	if(state != 3){
			$.ligerDialog.error("单据未入库确认，该功能不可用");
			return;
		}
    	var para = "group_id=" + $("#group_id").val() + "hos_id=" + $("#hos_id").val() + 
    		"copy_code=" + $("#copy_code").val() + "in_id=" + $("#in_id").val();
    	$.ligerDialog.open({
			title: '更新发票号',
			height: 300,
			width: 500,
			url: 'invoicePage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: "20%",
		});
    }
    
    //审核
	function audit(){

		if(state != 1){
			$.ligerDialog.error("审核失败！单据不是未审核状态");
			return;
		}
		var formPara={
 			group_id : $("#group_id").val(),
     		hos_id : $("#hos_id").val(),
     		copy_code : $("#copy_code").val(),
     		in_id : $("#in_id").val()
 		};
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMatStorageIn.do", formPara, function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 2;
						change_button();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		}); 
	}
	
    //消审
	function unAudit(){
		
		if(state != 2){
			$.ligerDialog.error("消审失败！单据不是审核状态");
			return;
		}
		var formPara={
 			group_id : $("#group_id").val(),
     		hos_id : $("#hos_id").val(),
     		copy_code : $("#copy_code").val(),
     		in_id : $("#in_id").val()
 		};
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMatStorageIn.do", formPara, function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 1;
						change_button();
				    	loadHead();
				    	grid.reRender();
				    	is_addRow();
					}
				});
			}
		}); 
	}
    
    //确认
    function confirm(){
    	
    	if(state != 2){
			$.ligerDialog.error("入库确认失败！单据不是审核状态");
			return;
		}
		var formPara={
 			group_id : $("#group_id").val(),
     		hos_id : $("#hos_id").val(),
     		copy_code : $("#copy_code").val(),
     		in_id : $("#in_id").val()
 		};
		$.ligerDialog.confirm('确定入库确认?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("confirmMatStorageIn.do", formPara, function (responseData){
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
    
    //添加材料
    function add_inv(){
    	
		if(state > 1){
			$.ligerDialog.error('单据不是未审核状态！');
			return;
		}
    	var para = "store_id=" + liger.get("store_code").getValue() +
			"&store_text=" + liger.get("store_code").getText();
		$.ligerDialog.open({
			title: '添加材料',
			height: 550,
			width: 1000,
			url: 'matchImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
    }
	
	//维护发票
    function invoice_open(){
		
    	var para = "group_id=" + $("#group_id").val() + 
			"&hos_id=" + $("#hos_id").val() + 
			"&copy_code=" + $("#copy_code").val() + 
			"&in_id=" + $("#in_id").val() + 
    		"&sup_id=" + liger.get("sup_code").getValue() +
    		"&sup_text=" + liger.get("sup_code").getText() + 
    		"&money_sum=" + $("#money_sum").val();
    	
    	$.ligerDialog.confirm('确定生成发票?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("existsMatInDetailByInvoice.do?isCheck=false&"+para, "", function (responseData){
					if(responseData.state=="true"){
						$.ligerDialog.open({
							title: '维护发票',
							height: 550,
							width: 1000,
							url: 'invoicePage.do?isCheck=false&'+para,
							modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
						});
					}else{
						$.ligerDialog.warn("该入库单已全部生成发票！");
					}
				});
			}
		}); 
    }
    
	//上一张
    function before_no(){
    	
		var formPara={
			group_id : $("#group_id").val(),
			hos_id : $("#hos_id").val(),
			copy_code : $("#copy_code").val(),
			in_id : $("#in_id").val(),
			store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
			bus_type_code : liger.get("bus_type_code").getValue(),
		};
		ajaxJsonObjectByUrl("../../queryMatInBeforeNo.do?isCheck=false",formPara,function(responseData){
			if(responseData.state=="true"){
				parentFrameUse().update_open(responseData.update_para);
				this_close();
			}
		}); 	
    }
	//下一张
	function next_no(){
		var formPara={
			group_id : $("#group_id").val(),
			hos_id : $("#hos_id").val(),
			copy_code : $("#copy_code").val(),
			in_id : $("#in_id").val(),
			store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
			bus_type_code : liger.get("bus_type_code").getValue(),
		};
		ajaxJsonObjectByUrl("../../queryMatInNextNo.do?isCheck=false",formPara,function(responseData){
			if(responseData.state=="true"){
				parentFrameUse().update_open(responseData.update_para);
				this_close();
			}else{
				$.ligerDialog.confirm('已经是最后一张单据了,是否打开新单据?', function (yes){
					if(yes){
						parentFrameUse().add_open();
				    	this_close();
					}
				}); 
			}
		}); 	
    }
    
    //条码导入
    function sn_imp(){
    	
		if(state > 1){
			$.ligerDialog.error('单据不是未审核状态！');
			return;
		}
    }

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('P', printDate);
		hotkeys('Z', printSet);
		hotkeys('I', imp);
		hotkeys('C', this_close);
	}
	
	//打印
	function printDate(){
		var para={
			in_id:$("#in_id").val()
		};
		
		//取打印模板

		//查询打印数据，主从表
		ajaxJsonObjectByUrl("queryMatInByPrintTemlate.do",para,function(responseData){
			var spread = new GcSpread.Sheets.Spread($('#spread')[0], { sheetCount: 1 });
	        var spreadNS = GcSpread.Sheets;
	        
	        var sheet = spread.getSheet(0);
	        //var sheet = spread.getActiveSheet();
	        
	        //开始绑定数据
	        spread.isPaintSuspended(true);
	      
	        var table = sheet.addTable('tableRecords', 1, 1, 4, 4);
 			table.autoGenerateColumns(false);
	        var tableColumns = [];
	        var tableColumn = new GcSpread.Sheets.TableColumnInfo();
			tableColumn.name("材料编码");
			tableColumn.dataField("inv_code");
			tableColumns.push(tableColumn);
	     
		/* 	names.forEach(function (name, index) {
			   var tableColumn = new GcSpread.Sheets.TableColumnInfo();
			   tableColumn.name(labels[index]);
			   tableColumn.dataField(name);
			   tableColumns.push(tableColumn);
			}); */
			
			table.bindColumns(tableColumns);
			table.bindingPath('Rows');
			source = new GcSpread.Sheets.CellBindingSource(responseData);
			sheet.setDataSource(source);
	        spread.isPaintSuspended(false);
	        
	        //开始设置打印格式
	        var printInfo = sheet.printInfo();
	        printInfo.showGridLine(false);//是否打印网格线 (默认是打印)
	        printInfo.showBorder(false);//是否打印控件的外边框线
	       /*  Inherit: 继承自表单的设置 ((默认) 行头/列头可见)。
	    	Hide: 不打印。
	    	Show: 在每页中都显示。
	    	ShowOnce: 显示一次 (在第一页). */
	        printInfo.showRowHeader(spreadNS.PrintVisibilityType.Hide);
	        printInfo.showColumnHeader(spreadNS.PrintVisibilityType.Hide);
	        spread.print(0);
			console.log(responseData);
		});
	}
	
	//打印设置
	function printSet(){
		parent.parent.$.ligerDialog.open({url : 'hrp/acc/accvouch/superPrint/printSetPage.do?mod_code=04&template_code=04010',
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			});
	}
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	function change_button(){
		//delete, update_invoice, create_invoice, audit, unAudit, confirm, add_inv, open_add
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
	            	<input name="group_id" type="text" id="group_id" value="${matInMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${matInMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${matInMain.copy_code}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${matInMain.in_id}" ltype="text" />
	            	<input name="money_sum" type="text" id="money_sum" value="${matInMain.money_sum}" ltype="text" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>入库单号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="in_no" type="text" id="in_no" value="${matInMain.in_no}" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
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
	            	<span style="color:red">*</span>制单日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="in_date" id="in_date" value="${matInMain.in_date}" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stocker_span" style="color:red">*</span>采购员：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="stock_type_code_span" style="color:red">*</span>采购类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:false}" />
	            </td>
	        </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span id="sup_code_span" style="color:red">*</span>供应商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	申请科室：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					协议编号：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					项&nbsp;&nbsp;&nbsp;&nbsp;目：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					摘&nbsp;&nbsp;&nbsp;&nbsp;要：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${matInMain.brief}" validate="{required:false,maxlength:50}" />
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" width="32%" >
					制单人：${matInMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="32%" >
					审核人：${matInMain.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="32%" >
					确认人：${matInMain.confirmer_name}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<!-- <button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="printSet" accessKey="Z"><b>打印模板（<u>Z</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
