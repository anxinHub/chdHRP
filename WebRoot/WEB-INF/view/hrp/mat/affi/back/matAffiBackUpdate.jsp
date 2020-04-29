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
    <script src="<%=path%>/lib/stringbuffer.js"></script> 
	<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
	<%-- <link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script> --%>
    
	<script type="text/javascript">
    var grid;
    var detailGrid;
    var gridManager;
    var state = '${matAffiInMain.state}';
    var is_total_cont =0;
	var is_price_cont =0;
	$(function (){
		loadDict();//加载下拉框
        //loadForm();
		loadHead();
		queryDetail();
		
		$("#store_code").bind("change",function(){
	    	/* loadHead();
	    	grid.reRender(); */
	    	grid.columns[4].editor.grid.url = '${p04021}' == 0 ? '../../queryMatAffiInvList.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0]
					: '../../queryMatAffiInvList.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0]+'&sup_id='+liger.get("sup_code").getValue().split(",")[0];
 			grid.reRender();
		}) 
		$("#sup_code").bind("change",function(){
	    	/* loadHead();
	    	grid.reRender(); */
	    	grid.columns[4].editor.grid.url = '${p04021}' == 0 ? '../../queryMatAffiInvList.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0]
			: '../../queryMatAffiInvList.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0]+'&sup_id='+liger.get("sup_code").getValue().split(",")[0];
			grid.reRender();
		})
	});  
	
    //查询明细
 	function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name : 'group_id',
 			value : '${matAffiInMain.group_id}'
 		});
 		grid.options.parms.push({
 			name : 'hos_id',
 			value : '${matAffiInMain.hos_id}'
 		});
 		grid.options.parms.push({
 			name : 'copy_code',
 			value : '${matAffiInMain.copy_code}'
 		});
 		grid.options.parms.push({
 			name : 'in_id',
 			value : '${matAffiInMain.in_id}'
 		});
 		grid.options.parms.push({
 			name : 'store_id',
 			value : '${matAffiInMain.store_id}'
 		});

     	//加载查询条件
     	grid.loadData(grid.where);
 	}
    
	function  save(){
		grid.endEdit();
		/* if(!$("form").valid()){
			return false;
		} */
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
 			$.ligerDialog.warn("编制日期不能为空");  
 			return false;  
 		}
 		if(liger.get("stocker").getValue() == null || liger.get("stocker").getValue() == ""){
 			$.ligerDialog.warn("采购员不能为空");  
 			return false;  
 		} 
 		if(liger.get("stock_type_code").getValue() == null || liger.get("stock_type_code").getValue() == ""){
 			$.ligerDialog.warn("采购类型不能为空");  
 			return false;  
 		} 
 		/* if(liger.get("sup_code").getValue() == null || liger.get("sup_code").getValue() == ""){
 			$.ligerDialog.warn("供应商不能为空");  
 			return false;  
 		}  */
 		
 		//明细
		var validate_detail_buf = new StringBuffer();
		var detail_data = gridManager.rows;
		var targetMap = new HashMap();
		var store_inv = "";  //用于判断是否属于该仓库
 		var rows = 0;
 		var msg="";
 		
 		var inva_date="";
		var disinfect_date="";
		
		if(detail_data.length > 0){
			$.each(detail_data, function(d_index, d_content){ 
				if(d_content.inv_id){
					var value="第"+(d_index+1)+"行";
					//d_content.amount = d_content.amount*-1
					if(typeof(d_content.amount) == "undefined" || d_content.amount == "" || d_content.amount >= 0){
						
		      				validate_detail_buf.append(value+"对应数量不能为空且必须小于0 请输入<br>");
					}
		      		if(d_content.imme_amount + d_content.amount <0){
		      			
	      				validate_detail_buf.append(value+"即时库存不足 请修改退货数量<br>");
			  		}
		      		
		      		if(typeof(d_content.inva_date) != "undefined" && d_content.inva_date != null && d_content.inva_date != ""){
		      			inva_date = d_content.inva_date;
		      		}else{
		      			inva_date = "";
		      		}
		      		
		      		if(typeof(d_content.disinfect_date) != "undefined" && d_content.disinfect_date != null && d_content.disinfect_date != ""){
		      			disinfect_date = d_content.disinfect_date;
		      		}else{
		      			disinfect_date = "";
		      		}
		      		
	 	      		var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price+"|"+inva_date+"|"+disinfect_date;
					if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
		 				targetMap.put(key ,value);
		 			}else{
						validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码、单价、有效日期、灭菌日期不能重复" + "<br>");
					}
	  	      		/* var key=d_content.inv_id +"|"+d_content.batch_no+"|"+d_content.bar_code+"|"+d_content.price;
	  				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
	  					targetMap.put(key ,value);
	  				}else{
	  					validate_detail_buf.append(targetMap.get(key)+"与"+value+"材料编码、生产批号、条形码单价不能重复" + "\n\r");
	  				} */
	  				store_inv += d_content.inv_id + ",";
	  				rows += 1;
				}
 			}); 
  		}
		
		
		//判断仓库材料关系
 		if(store_inv.length > 0){
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				store_id: liger.get("store_code").getValue().split(",")[0]
			}
			
			ajaxJsonObjectByUrl("../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不在该仓库中！");  
					//return false;
					//msg += "材料"+responseData.inv_ids+"不在该仓库中！<br>";
					validate_detail_buf.append("材料"+responseData.inv_ids+"不在该仓库中！<br>");
				}
			}, false);
			
			
			//供应商材料对应关系
			 var para = {
				inv_ids: store_inv.substring(0, store_inv.length-1), 
				sup_id: liger.get("sup_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMatAffiSupIsInv.do?isCheck=false", para, function (responseData){
				if(responseData.state=="false"){
					validate_detail_buf.append("材料"+responseData.inv_ids+"不属于该供应商！<br>");
				}
			}, false); 
			
 		}
		
  		if(validate_detail_buf.toString()  != ""){
  			$.ligerDialog.warn(validate_detail_buf.toString());
  			return false;
  		}
  		if(gridManager.rows.length == 0){
  			$.ligerDialog.warn('请选择材料');
  			return false;
  		}
		var formPara={
     			in_id : $("#in_id").val(),
 				in_no : $("#in_no").val(),
 				bus_type_code : liger.get("bus_type_code").getValue(),
 				store_id : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
 				store_no : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
 				store_code : liger.get("store_code").getText() == null ? "" : liger.get("store_code").getText().split(" ")[0],
 				year : $("#in_date").val().substr(0, 4),
 		 		month : $("#in_date").val().substr(5, 2),
 				in_date : $("#in_date").val(),
 				stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
 				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
 				sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
 				stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
 				app_dept : liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue().split(",")[0],
 				protocol_code : liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue(),
				proj_id : liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
 				brief : $("#brief").val(),
				//old_money_sum : $("#money_sum").val(),
 				detailData : JSON.stringify(gridManager.getData())
		};
		
		//alert(JSON.stringify(gridManager.getData()));
		
		ajaxJsonObjectByUrl("updateMatAffiBack.do", formPara, function(responseData){
			if(responseData.state=="true"){
				parentFrameUse().query();
             	grid.reload();
            }
		});
	}
	
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'in_id',
			value : '${matAffiInMain.in_id}'
		});
		grid.options.parms.push({
			name : 'store_id',
			value : '${matAffiInMain.store_id}'
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
    	autocomplete("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '29'}, false, "${matAffiInMain.bus_type_code}",'180');
		//autocomplete("#store_code", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, {is_com : '1'}, false, false,'180',"",200);
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_com : '1',is_write:'1'}, false,"","","",200);
		liger.get("store_code").setValue("${matAffiInMain.store_id},${matAffiInMain.store_no}");
		liger.get("store_code").setText("${matAffiInMain.store_code} ${matAffiInMain.store_name}");
		
		autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, "", false, "",'180');
		liger.get("stocker").setValue("${matAffiInMain.stocker}");
		liger.get("stocker").setText("${matAffiInMain.stocker_code} ${matAffiInMain.stocker_name}");
		
		autocomplete("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true, "", false, false,'180',"",300);
		liger.get("sup_code").setValue("${matAffiInMain.sup_id},${matAffiInMain.sup_no}");
		liger.get("sup_code").setText("${matAffiInMain.sup_code} ${matAffiInMain.sup_name}");
		
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true, "", false, "${matAffiInMain.stock_type_code}",'180');
		
		//autocomplete("#app_dept", "../../queryMatDept.do?isCheck=false", "id", "text", true, true, {is_last : 1}, false, false,'180',"",300);
		autocomplete("#app_dept", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : '1',is_write:'1'},false,"","","",300);
		liger.get("app_dept").setValue("${matAffiInMain.app_dept}");
		liger.get("app_dept").setText("${matAffiInMain.app_dept_code} ${matAffiInMain.app_dept_name}");
		

		
		autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false,'',"",510);
		liger.get("proj_code").setValue("${matAffiInMain.proj_id}");
		liger.get("proj_code").setText("${matAffiInMain.proj_code} ${matAffiInMain.proj_name}");
		
		//格式化文本框
		$("#protocol_code").ligerTextBox({width:320,disabled:true,cancelable: false});
        $("#in_no").ligerTextBox({width:160, disabled:true});
        $("#in_date").ligerTextBox({width:180});
        $("#brief").ligerTextBox({width:510});
        $("#proj_code").ligerTextBox({width:510});
        $("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		$("#close").ligerButton({click: this_close, width:90});
       
     } 
    
    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '材料编码', name: 'inv_code', align: 'left',width:120,
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>合计：</div>';
					}
				}
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left',width:240,
				editor : {
					type : 'select',
	         		valueField : 'inv_id',
	         		textField : 'inv_name',
	         		selectBoxWidth : '80%',
	         		selectBoxHeight : 240,
	         		grid : {
	         			columns : [ {
	         				display : '材料编码', name : 'inv_code', minWidth : 100, align : 'left'
	         			}, {
	         				display : '材料名称', name : 'inv_name', minWidth : 120, align : 'left'
	         			}, {
	         				display : '物资类别', name : 'mat_type_name', minWidth : 140, align : 'left'
	         			}, {
	         				display : '规格型号', name : 'inv_model', minWidth : 140, align : 'left'
	         			}, {
	         				display : '生产厂商', name : 'fac_name', minWidth : 100, align : 'left'
	         			}, { 
	        	         	display: '批号', name: 'batch_no', align: 'left', width: 140, align : 'left'
	       	         	}, { 
	        	         	display: '条形码', name: 'bar_code', align: 'left', width: 100, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:140,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', minWidth : 100, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', minWidth : 100, align : 'left'
	         			}, {
	         				display : '是否收费', name : 'is_charge', minWidth : 140, align : 'left',
	         				render : function(rowdata, rowindex, value) {
	         					return value == 1 ? '是' : '否';
	         				}
	         			}, { 
         					display : '有效日期', name : 'inva_date', width : 100, align : 'left'
         				}, { 
         					display : '灭菌日期', name : 'disinfect_date', width : 100, align : 'left'
	         			}, {
	         				display : '货位', name : 'location_name', minWidth : 100, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
	         			//url : '${p04021}' == 0 ? '../../queryMatAffiInvList.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0]
						url : '../../queryMatAffiInvList.do?isCheck=false&store_id='+liger.get("store_code").getValue().split(",")[0]+'&sup_id='+liger.get("sup_code").getValue().split(",")[0],
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
	         }, { 
	         	display: '规格型号', name: 'inv_model', align: 'left',width:140
	         }, { 
	         	display: '计量单位', name: 'unit_name', align: 'left',width:140
	         }, { 
	         	display: '批号', name: 'batch_no', align: 'left',width:250
	         }, { 
	         	display: '当前库存', name: 'cur_amount', align: 'left',width:140
	         }, { 
	         	display: '即时库存', name: 'imme_amount', align: 'left',width:140
	         }, {     	display: '数量(E)', name: 'amount', align: 'left', width:140, editor : {type : 'number'},
		         	render : function(rowdata, rowindex, value) {
		         		//value=value<0? -1*value:value
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					},
	         	totalSummary: {
					align: 'left',
					render: function (suminf, column, cell) {
						//suminf.sum=suminf.sum<0? -1*suminf.sum:suminf.sum
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, 2, 1) + '</div>';
					}
				}
	         }, { 
				display: '单价', name: 'price', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
					}
				}
			}, { 
				display: '有效日期', name: 'inva_date', align: 'left',width:120
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:120
			}, { 
				display: '条形码', name: 'bar_code', align: 'left',width:120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right',width:140,
				/*editor : {
					type : 'numberbox',
					precision : '${p04006 }'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
					}
				}
			}, { 
				display: '零售价格', name: 'sell_price', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
					return value == null ? "" : formatNumber(value, '${p04072 }', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right',width:140,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
					return value == null ? "" : formatNumber(value, '${p04073 }', 1);
				},totalSummary: {
					align: 'right',
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04073 }', 1) + '</div>';
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
				display: '批次数量合计', name: 'sum_amount',width:140,hide : true 
			}, { 
				display: '批次明细', name: 'inv_detail_data',width:140 , hide : true 
			} ],
			usePager : false,width : '100%',height : '95%',enabledEdit : state == 1 ? true : false,fixedCellHeight:true,
			heightDiff:-20,url : 'queryMatAffiBackDetailByCode.do?isCheck=false&in_id=${matAffiInMain.in_id}&store_id=${matAffiInMain.store_id}',
			delayLoad : true,//初始化明细数据
			onBeforeEdit : f_onBeforeEdit, onBeforeSubmitEdit : f_onBeforeSubmitEdit, onAfterEdit : f_onAfterEdit,
			onsuccess:function(){
				//is_addRow();
			},
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
			toolbar: { items: [
				{ text: '保存', id:'save', click: save,icon:'save', disabled: state == 1 ? false : true},
				{ line:true },
				{ text: '删除', id:'delete', click: deleteRow,icon:'delete' , disabled: state == 1 ? false : true},
				{ line:true },
				{ text: '审核', id:'audit', click: audit,icon:'audit' , disabled: state == 1 ? false : true},
				{ line:true },
				{ text: '消审', id:'unaudit', click: unAudit,icon:'unaudit' , disabled: state == 2 ? false : true},
				{ line:true },
				{ text: '退货确认', id:'confirm', click: confirmData,icon:'account' , disabled: state == 2 ? false : true},
				{ line:true },
				{ text: '入库单导入', id:'in_imp', click: in_imp,icon:'up' , disabled: state == 1 ? false : true},
				{ line:true },
				{ text: '上一张', id:'before_no', click: before_no,icon:'before' },
				{ line:true },
				{ text: '下一张', id:'next_no', click: next_no,icon:'next' },
				{ line:true },
				{ text: '关闭', id:'close', click: this_close,icon:'close' }
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
					
					sale_price : data.sale_price == null ? "" : data.sale_price,
					sell_price : data.sell_price == null ? "" : data.sell_price,
					inva_date : data.inva_date == null ? "" : data.inva_date,
					disinfect_date : data.disinfect_date == null ? "" : data.disinfect_date,
					location_id : data.location_id == null ? "" : data.location_id,
					location_name : data.location_name == null ? "" : data.location_name,
					amount : data.is_per_bar==1 || data.is_highvalue == 1 ? "-1" : "",
					price : data.price == null ? "" : data.price,
					amount_money : data.is_per_bar==1 || data.is_highvalue == 1 ? data.price*(-1) : ""
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
    		grid.updateTotalSummary();
    	}
    }
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		return true;
	} 
    
    //删除
    function deleteRow(){ 
    	
    	gridManager.deleteSelectedRow();
    }
    
	//批量添加明细数据
    function add_Rows(data){
    	grid.deleteAllRows();
    	grid.addRows(data);
    }
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
		//alert(JSON.stringify(row));    	
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		//detailGrid =$(detailPanel).css('margin',10).ligerGrid({
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '材料编码', name: 'inv_code',width:100, align : 'left'
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width:240, align : 'left',
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
	         				display : '材料名称', name : 'inv_name', width : 240, align : 'left'
	         			}, {
	        	         	display: '批次', name: 'batch_sn', align: 'left', width: 80, align : 'left'
	       	         	}, { 
	       					display: '单价', name: 'price', align: 'right', width:80,
	       					render : function(rowdata, rowindex, value) {
	       						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
	       					}
	       				}, { 
	         				display : '库存', name : 'cur_amount', width : 50, align : 'left'
	         			}, { 
	         				display : '即时库存', name : 'imme_amount', width : 50, align : 'left'
	         			} ],
	         			switchPageSizeApplyComboBox : false,
	         			onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_detail_onSelectRow_detail(data);
							}
						},
	         			url : '../../queryMatAffiOutDetailInvList.do?isCheck=false&is_com=1&store_id=' 
         						+ liger.get("store_code").getValue().split(",")[0] + '&inv_id=' + row.inv_id
         						+ '&batch_no=' + row.batch_no + '&bar_code=' + row.bar_code + '&price=' + row.price
         						+ '&inva_date=' + row.inva_date + '&disinfect_date=' + row.disinfect_date,
	         			pageSize : 10,
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
	         		onSuccess: function (data, grid) {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_detail_onSelectRow_detail(data.rowdata);
					}
	         	}
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left' 
    		}, { 
     			display : '库存', name : 'cur_amount', minWidth : 50, align : 'left'
     		}, { 
     			display : '即时库存', name : 'imme_amount', minWidth : 50, align : 'left', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 60, align : 'left', editor : {type : 'float'}
    		}, { 
    			display: '单价', name: 'price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 80, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04072 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width:80,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04073 }', 1);
    			}
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : state == 1 ? true : false, fixedCellHeight: true, frozen: false,
    		onBeforeEdit : f_detail_onBeforeEdit, onBeforeSubmitEdit : f_detail_onBeforeSubmitEdit, onAfterEdit : f_detail_onAfterEdit,
    		width: '65%',height: '90%',data : f_getInvDetailData(row),columnWidth:80, 
    		toolbar: { items: [
    			{ text: '删除', id:'delete', click: deleteDetailRow,icon:'delete' },
    			{ line:true },
    			{ text: '添加行', id:'add', click: detailGridAddRow,icon:'add' },
    			{ line:true }
    		]} 
		});
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
				var rows = jsonRowsToObject(rowdata.inv_detail_data, "in_detail_id");
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("store_code").getValue().split(",")[0], 
    				//out_id : '${out_id}', 
            		inv_id : rowdata.inv_id, 
            		batch_no : rowdata.batch_no, 
            		bar_code : rowdata.bar_code, 
            		price : rowdata.price,
            		amount : rowdata.amount,
            		inva_date : rowdata.inva_date,
            		disinfect_date : rowdata.disinfect_date 
            	}
        		ajaxJsonObjectByUrl('../../queryMatAffiInvByFifo.do?isCheck=false',invPara,function(responseData){
        			data = responseData;
                }, false);
				//变更主数据中材料批次信息
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
			//$.ligerDialog.warn('请选择材料！');
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			return false;
		}
		return true;
	}
    //删除明细
    function deleteDetailRow(){ 
    	
    	detailGrid.deleteSelectedRow();
    	changeDetailAmount();
    }
    
    function in_imp(){
    	var store_id = liger.get("store_code").getValue();
    	if(store_id == null || store_id == "" || store_id == "defined"){
    		$.ligerDialog.error("请先选择库房！");
    		return;
    	}
    	var sup_id = liger.get("sup_code").getValue();
    	if(sup_id == null || sup_id == "" || sup_id == "defined"){
    		$.ligerDialog.error("请先选择供应商！");
    		return;
    	}
    	var para = "store_id=" + liger.get("store_code").getValue() +
    		"&store_text=" + liger.get("store_code").getText() + 
    		"&sup_id=" + liger.get("sup_code").getValue() +
    		"&sup_text=" + liger.get("sup_code").getText();
    	$.ligerDialog.open({
			title: '入库单导入',
			height: 500,
			width: 900,
			url: 'matAffiBackInImpPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top: 1
		});
    }
    
    function remove(){
    	if(state > 1){
    		$.ligerDialog.error('修改失败，单据不是未审核状态！');
    		return;
    	}
    	grid.deleteSelectedRow();
    }

	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;		
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
			if (e.column.name == "amount"){
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

	//添加空行--flag=init表示页面初始化时加载不应出提示
    function is_addRow(flag){
    	if(state > 1){
    		if(flag == "init"){
    			return;
    		}else{
	    		$.ligerDialog.error('修改失败，单据不是未审核状态！');
	    		return;
    		}
    	}
    	setTimeout(function() {
			grid.addRow();
		}, 1000);
    }

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('P', printDate);
		hotkeys('I', imp);
		hotkeys('L', this_close);
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
				ajaxJsonObjectByUrl("auditMatAffiBack.do", formPara, function (responseData){
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
				ajaxJsonObjectByUrl("unAuditMatAffiBack.do", formPara, function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 1;
						change_button();
				    	loadHead();
				    	grid.reRender();
					}
				});
			}
		}); 
	}
    
    //确认
    function confirm(){
    	var is_store='${p04045 }';
    	if(state != 2){
			$.ligerDialog.error("退货确认失败！单据不是审核状态");
			return;
		} 
    	var todayDate = new Date();
		var todayYear = todayDate.getFullYear();
		var todayMonth = todayDate.getMonth() + 1;
		var todayDate = todayDate.getDate();
		todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
		todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
		var today = todayYear + '-' + todayMonth + '-' + todayDate;
		if('${p04047 }'==0){
			confirmData(today);
		}else{
			$.ligerDialog.open({
				content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
				width: 300,
				height: 150,
				buttons: [
					{ text: '确定', onclick: function (item, dialog) {
						var aa = $("#confirmDate").val();
						if (aa) {
							
							dialog.close();
							confirmData(aa);
						}
					}},
	                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
			})
		}
	}
		
	function confirmData(){
		var is_store='${p04045 }';
		
		/* 1.开始   说明：后期可用于维护用户输入日期操作 */
		var confirmDate = $("#in_date").val(); 
		/* 1.结束 */
		
		var formPara={
	 			group_id : $("#group_id").val(),
	     		hos_id : $("#hos_id").val(),
	     		copy_code : $("#copy_code").val(),
	     		in_id : $("#in_id").val(),
	     		confirm_date :confirmDate
	 		};
			var ParamVo =[];
			ParamVo.push(
				$("#group_id").val()   +"@"+ 
				$("#hos_id").val()   +"@"+ 
				$("#copy_code").val()   +"@"+ 
				$("#in_id").val()  +"@"+ 
				confirmDate+"@"+
				is_store +"@"+
				liger.get("store_code").getValue().split(",")[0] +"@"+
				liger.get("in_no").getValue()
			) 
			
			$.ligerDialog.confirm('确定退货确认?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("/CHD-HRP/hrp/mat/storage/in/verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
						if (responseData.state == "true") {
							ajaxJsonObjectByUrl("confirmMatAffiBack.do", formPara, function (responseData){
								if(responseData.state=="true"){
									parentFrameUse().query();
									state = 3;
									change_button();
							    	loadHead();
							    	grid.reRender();
								}
							});
						}
					},false);
				}
			}); 
				
		}
    //添加新退货单
    function add_open(){
    	
    	parentFrameUse().add_open();
    	this_close();
	}
    //打印
	function printDate(){
		
	}
    
	//上一张
    function before_no(){
    	if('${up}' == $("#in_id").val()){
 			$.ligerDialog.confirm('本单据为第一张单据,是否跳转添加单据', function (yes){
				if(yes){
					parentFrameUse().add_open();
					this_close();
				}
 			});
 		}else{
 			var obj = $("#group_id").val()+","+$("#hos_id").val()+","+$("#copy_code").val()+"," + '${up}';
 			parentFrameUse().update_open(obj);
 			this_close();	
 		}	
    }
    //下一张
	function next_no(){
		if('${next}' == $("#in_id").val()){
			$.ligerDialog.confirm('本单据为最后一张单据,是否跳转添加单据', function (yes){
				if(yes){
					parentFrameUse().add_open();
					this_close();
				}
 			});
 		}else{
 			var obj = $("#group_id").val()+","+$("#hos_id").val()+","+$("#copy_code").val()+"," + '${next}';
 			parentFrameUse().update_open(obj);
 			this_close();
 		}
		
    }
	
	//关闭当前页面
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
	
	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${p04023 }'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${p04023 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		/* var para={
			in_id:$("#in_id").val(),
			template_code:'04019',
			p_num:0,
			use_id:useId
		};
		
		printTemplate("queryMatAffiBackByPrintTemlate.do",para); */
		
		var para={
				in_id:$("#in_id").val(),
				template_code:'04019',
				class_name:"com.chd.hrp.mat.serviceImpl.affi.in.MatAffiInCommonServiceImpl",
				method_name:"queryMatAffiInByPrintTemlate1",
				isPrintCount:false,//更新打印次数
				isPreview:false,//预览窗口，传绝对路径
				p_num : 0,
				use_id:useId
			};
			
			officeFormPrint(para);
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${p04023 }'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${p04023 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		officeFormTemplate({template_code:"04019",use_id:useId});
		/* parent.parent.$.ligerDialog.open({url : 'hrp/mat/affi/back/affiBackPrintSetPage.do?template_code=04019&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
	}
	function changeIndate(){
		var sup_id = liger.get("sup_code").getValue().split(",")[0];
		var in_date = liger.get("in_date").getValue();
		if(sup_id && in_date){
			$.ajax({
				url:"../../getMatPactFkxyInfo.do?isCheck=false",
				data:{
					sup_id:sup_id,
					in_date :in_date	
				},
				type:"post",
				dataType:"json",
				success:function(msg){
					var pactFkxyInfo=msg.pactFkxyInfo?msg.pactFkxyInfo:"";
					
					liger.get("protocol_code").setValue("");
					liger.get("protocol_code").setValue(pactFkxyInfo);
					is_total_cont =msg.is_total_cont?msg.is_total_cont:0;
					is_price_cont =msg.is_price_cont?msg.is_price_cont:0;
				},
				async:false
			})
		}
		
	}
    </script>
  
</head>
  
<body onload="is_addRow('init')">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			<tr>
	            <td style="display: none;">
	            	<input name="group_id" type="text" id="group_id" value="${matAffiInMain.group_id}" ltype="text" />
	            	<input name="hos_id" type="text" id="hos_id" value="${matAffiInMain.hos_id}" ltype="text" />
	            	<input name="copy_code" type="text" id="copy_code" value="${matAffiInMain.copy_code}" ltype="text" />
	            	<input name="in_id" type="text" id="in_id" value="${matAffiInMain.in_id}" ltype="text" />
	            	<%-- <input name="money_sum" type="text" id="money_sum" value="${matAffiInMain.money_sum}" ltype="text" /> --%>
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>入库单号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="in_no" type="text" id="in_no" value="${matAffiInMain.in_no}" ltype="text" disabled="disabled" required="true" validate="{required:true}" />
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
	            	<span style="color:red">*</span>入库日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="in_date" id="in_date" value="${matAffiInMain.in_date}" type="text" required="true" onchange="changeIndate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>采购员：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	采购类型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:false}" />
	            </td>
	        </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	供应商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >申请科室：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					协议编号：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="protocol_code" type="text" id="protocol_code" ltype="text" value="${matAffiInMain.protocol_id}" validate="{required:false,maxlength:20}" />
	            </td>
			</tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					项&nbsp;&nbsp;&nbsp;&nbsp;目：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
	            </td>
	        </tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
					备&nbsp;&nbsp;&nbsp;&nbsp;注：
	            </td>
	            <td align="left" class="l-table-edit-td" colspan="5">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${matAffiInMain.brief}" validate="{required:false,maxlength:50}" />
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					制单人：${matAffiInMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					审核人：${matAffiInMain.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					确认人：${matAffiInMain.confirmer_name}
				</td>
			</tr>
				<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<!--button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
					< <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="bar_print" accessKey="R"><b>条码打印（<u>R</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="merge_print" accessKey="M"><b>合并打印（<u>M</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="L"><b>关闭（<u>L</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
