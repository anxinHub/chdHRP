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
    <link href='<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css' rel='stylesheet' type='text/css'/>
	<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
	<script src='<%=path%>/lib/SpreadJS/scripts/pluggable/gcspread.sheets.print.9.40.20161.0.min.js' type='text/javascript'></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     var state = '${medOrderMain.state}';
     var flag = '${medOrderMain.come_from}';
     
     $(function (){
		loadDict()//加载下拉框  
		loadHead();
		queryDetail();
		if('${medOrderMain.pur_type}'=='1'){
			$('input:radio:first').attr('checked', 'checked');
			$('#hos_name').hide();
		}
		if('${medOrderMain.pur_type}'=='2'){
			$('input:radio:last').attr('checked', 'checked');
			$('#hos_name').show();
		}
		singleSel();
		
     });  
     
     function singleSel(){
    	 if($("input[type='radio']:checked").val() == '1'){
    		 $(".demo").attr("style","visibility:hidden");
    		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"0",'160');//是否定向
			 changeDir();
    	 } 
    	 if($("input[type='radio']:checked").val() == '2'){
    		 $(".demo").attr("style","visibility:true");
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
   
     function queryDetail(){
    	grid.options.parms = [];
 		grid.options.newPage = 1;
 		//根据表字段进行添加查询条件
 		
     	grid.options.parms.push({name : 'order_id',value : $("#order_id").val()}); 
 		//加载查询条件
 		grid.loadData(grid.where);
     }
     
     //验证
     function validateGrid() { 
    	//主表
   		if(!liger.get("order_date").getValue()){
   			$.ligerDialog.warn("编制日期不能为空");  
   			return false;  
   		}
   		if(!liger.get("sup_code").getValue()){
   			$.ligerDialog.warn("供应商不能为空");  
   			return false;  
   		}
   		if($("input[type='radio']:checked").val() == '2'){
   			if(!liger.get("take_hos_id").getValue()){
   	  			$.ligerDialog.warn("收货单位不能为空");  
   	  			return false;  
   	  		}
   	  		if(!liger.get("pay_hos_id").getValue()){
   	  			$.ligerDialog.warn("付款单位不能为空");  
   	  			return false;  
   	  		} 
   		}
   		if(!liger.get("order_type").getValue()){
   			$.ligerDialog.warn("订单类型不能为空");  
   			return false;  
   		}
   		if(!liger.get("arrive_date").getValue()){
   			$.ligerDialog.warn("计划到货日期不能为空");  
   			return false;  
   		}
     	//明细
   		var msg="";
   		var rowm = "";
   		var rows = 0;
   		var data = gridManager.getData();
   		//判断grid 中的数据是否重复或者为空
   		var targetMap = new HashMap();
   		$.each(data,function(i, v){
   			rowm = "";
   			if(v.inv_id){
   				if (v.inv_id == "" || v.inv_id == null || v.inv_id == 'undefined') {
   	  				rowm+="[药品]、";
   	  			}  
   	  			if (v.amount == "" || v.amount == null || v.amount == 'undefined') {
   	  				rowm+="[数量]、";
   	  			}  
   	  			if (v.price == "" || v.price == null  || v.price == 'undefined') {  
   	  				rowm+="[单价]、"; 
   	  			}
   	  			//if (v.rdate == "" || v.rdate == null  || v.rdate == 'undefined') {  
   	  				//rowm+="[需求日期]、"; 
   	  			//}
   	  			if(rowm != ""){
   	 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空或0" + "\n\r";
   	 			}
   	  			msg += rowm;
   	  			rows = rows +1;
   	  			var key=v.inv_id ;
   	  			var value="第"+(i+1)+"行";
   	  			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
   	  				targetMap.put(key ,value);
   	  			}else{
   	  				msg += targetMap.get(key)+"与"+value+"药品编码不能重复" + "\n\r";
   	  			}
   			}
  			
   		});
   		
   		if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}
   		
   		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		} 	
   		return true;	
 	 }
     //保存
     function  save(){
    	 if(validateGrid()){
    		 var allData = gridManager.getData();
    		 if(allData.length == 0){
  		    	$.ligerDialog.error('请添加明细！');
  		    	return ; 
  		     }
    		 
    		 var formPara={
    	        	order_id : $("#order_id").val(),
    	        	order_code : $("#order_code").val(),
        			order_date : $("#order_date").val(),
        			arrive_date : $("#arrive_date").val(),
        			sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
        			sup_no : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
        			
        			order_type : liger.get("order_type").getValue() == null ? "" : liger.get("order_type").getValue(),
        			take_hos_id : liger.get("take_hos_id").getValue() == null ? "" : liger.get("take_hos_id").getValue().split(",")[0],
        			pay_hos_id : liger.get("pay_hos_id").getValue() == null ? "" : liger.get("pay_hos_id").getValue().split(",")[0],
        			
        			pur_type : $('input[name="pur_type"]:checked').val(),
        			stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue() ,
        			pay_code : liger.get("pay_code").getValue() == null ? "" : liger.get("pay_code").getValue() ,
        				
        			dept_id : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0],
        			dept_no : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[1],		
        			stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
        			
        			arr_address : $("#arr_address").val(),	
        			note : $("#note").val(),
        			is_dir : liger.get("is_dir").getValue(),
        			dir_dept_id : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[0],
                	dir_dept_no : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[1],	
        			detailData : JSON.stringify(allData)
    			};
    		 
    	        ajaxJsonObjectByUrl("updateMedOrderInit.do?isCheck=true",formPara,function(responseData){
    		         if(responseData.state=="true"){
    		        	 parentFrameUse().query();
    		              queryDetail();
    		         }
    		    });	 
    		 
    	 }
    }
     
  
    function loadDict(){
    	//字典下拉框
    	autocomplete("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true, "", false, false);//供应商
    	liger.get("sup_code").setValue('${medOrderMain.sup_id},${medOrderMain.sup_no}');
		liger.get("sup_code").setText('${medOrderMain.sup_code} ${medOrderMain.sup_name}');
		
    	autocomplete("#take_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.take_hos_id}');//收货单位
		autocomplete("#pay_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.pay_hos_id}');//付款单位
		autoCompleteByData("#order_type", medOrderMain_orderType.Rows, "id", "text", true, true, "", false, '${medOrderMain.order_type}');//订单类型
    	autocomplete("#stock_type_code", "../../queryMedStockType.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.stock_type_code}');//采购类型
    	autocomplete("#pay_code", "../../queryMedPayTerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.pay_code}');//付款方式
		autocomplete("#dept_code", "../../queryPurDept.do?isCheck=false", "id", "text", true, true, "", false, false);//采购部门
		liger.get("dept_code").setValue('${medOrderMain.dept_id},${medOrderMain.dept_no}');
		liger.get("dept_code").setText('${medOrderMain.dept_code} ${medOrderMain.dept_name}');
		
		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.stocker}');//采购人
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,'${medOrderMain.is_dir}','160');//是否定向
		autocomplete("#dir_dept_id","../../queryMedDeptDict.do?isCheck=false","id","text",true,true);//定向科室	
		liger.get("dir_dept_id").setValue('${medOrderMain.dir_dept_id},${medOrderMain.dir_dept_no}');
		liger.get("dir_dept_id").setText('${medOrderMain.dir_dept_code} ${medOrderMain.dir_dept_name}');
		
        $("#order_code").ligerTextBox({width:160, disabled:true});
        $("#order_date").ligerTextBox({width:160});
        $("#arrive_date").ligerTextBox({width:160});////计划到货日期
        $("#sup_code").ligerTextBox({width:160});
        
        $("#order_type").ligerTextBox({width:160});
        $("#take_hos_id").ligerTextBox({width:160});
        $("#pay_hos_id").ligerTextBox({width:160});
        $("#dir_dept_id").ligerTextBox({width:160});
        $("#stock_type_code").ligerTextBox({width:160});
        $("#pay_code").ligerTextBox({width:160});
        
        $("#dept_code").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        
        $("#arr_address").ligerTextBox({width:500});
        $("#note").ligerTextBox({width:160});
        
        
        $("#printSet").ligerButton({click: printSet, width:100});
		$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
		change();
     } 
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{ display : '交易编码',name : 'bid_code',width : 150,align : 'left',
				},{ display : '药品编码',name : 'inv_code',width : 150,align : 'left',
				},{display : '药品名称(E)',name : 'inv_id',textField : 'inv_name',width : 150,align : 'left',
					editor : {
						type : 'select',
						valueField : 'inv_id',
						textField : 'inv_name',
						selectBoxWidth : "80%",
						selectBoxHeight : 240,
						grid : {
							columns : [{display : '交易编码',name : 'bid_code',minWidth : 100,align : 'left'}, 
							           {display : '药品编码',name : 'inv_code',minWidth : 100,align : 'left'}, 
									{display : '药品变更号',name : 'inv_no',align : 'left'}, 
									{display : '药品名称',name : 'inv_name',minWidth : 120,align : 'left'}, 
									{display : '规格型号',	name : 'inv_model',	minWidth : 80,align : 'left'}, 
									{display : '计量单位',	name : 'unit_name',	minWidth : 80,align : 'left'}, 
									{display : '包装单位',	name : 'pack_unit_name',minWidth : 80,align : 'left'}, 
									{display : '转换量',	name : 'num_exchange',	minWidth : 80,align : 'right'}, 
									{display : '供应商',	name : 'sup_name',		minWidth : 100,	align : 'left'}, 
									{display : '生产厂商',	name : 'fac_name',		minWidth : 100,	align : 'left'},
									{display : '计划价',	name : 'plan_price',	minWidth : 80,	align : 'right',
										render : function(rowdata, rowindex, value) {
											 return formatNumber(rowdata.plan_price ==null ? 0 : rowdata.plan_price,'${p08006}',1);
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
								url : '../../queryMedInvListDept.do?isCheck=false',//&store_id='+ liger.get("take_hos_id").getValue() == null ? "" : liger.get("take_hos_id").getValue().split(",")[0],
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
						delayLoad : true,
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
				}, {display : '规格型号', name : 'inv_model', width : 150, align : 'left'
				}, {display : '计量单位', name : 'unit_name', width : 100, align : 'left'
				},{ display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width : 100, align : 'left',
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
			 }, {display : '转换量(E)', name : 'num_exchange', width : 100, type : 'int',
					editor : { type : 'int'}, align : 'left',
					render : function(rowdata, rowindex, value) {
						return rowdata.num_exchange == null ? "" : formatNumber(rowdata.num_exchange, 2, 1);
					}
				}, {display : '包装数量(E)', name : 'num', width : 100, type : 'number',
					editor : {
						type : 'number',
					},
					align : 'right'
				},{display : '已入库数量',name : 'already_amount',align : 'right',width : 100}, 
				{display : '采购数量(E)', name : 'amount',    width : 100, type : 'number',
					align : 'right',
					editor :   flag != 1 ? {} :{
						type : 'float'
					},
					//editor : { type : 'number', },
					render : function(rowdata, rowindex, value) {
						return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
					},
					totalSummary: {
						align : 'right',
	                    render: function (suminf, column, cell) {
	                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p08005 }', 1)+ '</div>';
	                    }
	                } 
				}, {display : '单价(E)', name : 'price', width : 100, align : 'right',
					editor : {
						type : 'numberbox',
						precision : '${p08006 }'
					},
					render : function(rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
						return value == null ? "" : formatNumber(value, '${p08006 }', 1);
					}
				}, {display : '金额', name : 'amount_money', width : 100, type : 'number', align : 'right',
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
				},{ display : '生产厂商', name : 'fac_name', align : 'left',width : 200
				}, { display : '备注', name : 'memo', align : 'left',width : 200,
					 editor : {
							type : 'text'
					 }
				},{ display : '明细ID', name : 'order_detail_id', align : 'left',width : 200,hide : true
				},{display : '订单关系', name : 'order_rela', align : 'left', width : 200,hide: true
				},{display : 'inv_no', name : 'inv_no', align : 'left', width : 200,hide: true
				}  
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryMedOrderInitDetail.do?isCheck=false',
			width : '100%',
			height : '90%',
			checkbox : true,
			enabledEdit : true,
			alternatingRow : true,
			onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,
			onAfterEdit : f_onAfterEdit,
			isAddRow : (flag == 1 ) ? false : true,
			isScroll : true,
			rownumbers : true,
			onsuccess:function(){
			
				//is_addRow();
			},
			delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {text : '删除（<u>D</u>）',id : 'delete', click : remove, icon : 'delete',disabled: state == 1 ? false : true },
				          { line:true },
			       		  { text: '保存（<u>S</u>）', id:'save', click: save,icon:'save' ,disabled: state == 1 ? false : true },
			       		  { line:true },
			       		  { text: '审核（<u>A</u>）', id:'audit', click: audit,icon:'audit' ,disabled: state == 1 ? false : true },
			       		  { line:true },
			       		  { text: '销审（<u>U</u>）', id:'unAudit', click: unAudit,icon:'unaudit' ,disabled: state == 2 ? false : true }
				        ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		grid.toggleCol("inv_no", false);
		grid.toggleCol("inv_no", false);
		grid.toggleCol("order_detail_id", false);
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
    //删除
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
		//alert(JSON.stringify(data)); 
		//回充数据 
		if (selectData != "" || selectData != null) {
			if (column_name == "inv_id") {
				grid.updateRow(rowindex_id, {
					bid_code : data.bid_code,
					inv_code : data.inv_code,
					inv_no : data.inv_no,
					inv_id : data.inv_id,
					inv_model : data.inv_model,
					unit_name : data.unit_name,
					pack_code : data.pack_code,
					pack_name : data.pack_name,
					fac_name : data.fac_name,
					price : data.plan_price,
					num_exchange : data.num_exchange,
					amount : '',
					amount_money :''
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
				//计算自动到货日期
				if('${p08007}' != 0){
			/* 		grid.updateCell('arrive_date', getDateAddDay(new Date(), '${sessionScope.med_para_map["08008"]}'), e.rowindex); */
				}
			}else if (e.column.name == "amount"){
				//自动计算金额
				if(e.record.price != undefined && e.record.price != "" && e.record.price != 0){
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
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
	
	//获得明细数据	
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 

	//键盘事件
	function loadHotkeys() {
		hotkeys('S', save);//保存
		hotkeys('D', remove);//删除
		//hotkeys('P', printDate);//打印	
		hotkeys('C', this_close);//关闭
	}
	
	//增加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
			//grid.beginEdit(0);
		}, 1000);
	}
	
	//审核
	function audit(){
		if(state != 1){
			$.ligerDialog.error("审核失败！单据不是新建状态");
			return;
		}
		var ParamVo =[];
		ParamVo.push(
				'${medOrderMain.group_id}'   +"@"+ 
				'${medOrderMain.hos_id}'   +"@"+ 
				'${medOrderMain.copy_code}'   +"@"+ 
				'${medOrderMain.order_id}' 
		);
		
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("../audit/auditMedOrderAudit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 2;
				    	loadHead();
				    	grid.reRender();
				    	is_addRow();
					}
				});
			}
		});
		
	}
	
	//销审
	function unAudit(){
		
		if(state != 2){
			$.ligerDialog.error("消审失败！单据不是审核状态");
			return;
		}
		var ParamVo =[];
		ParamVo.push(
				'${medOrderMain.group_id}'   +"@"+ 
				'${medOrderMain.hos_id}'   +"@"+ 
				'${medOrderMain.copy_code}'   +"@"+ 
				'${medOrderMain.order_id}' 
		);
		
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("../audit/unAuditMedOrderAudit.do?isCheck=true",{ParamVo : ParamVo.toString()},function (responseData){
					if(responseData.state=="true"){
						parentFrameUse().query();
						state = 1;
				    	loadHead();
				    	grid.reRender();
				    	is_addRow();
					}
				});
			}
		}); 
		
	}
	
	
	//打印
	/* function printDate(){
	
	} */
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//隐藏或显示  采购单位、请购单位、付款单位
    function change(){
    	singleSel();
    }
	
	
  //打印
	function printDate(){
		
	var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p08023 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
// 		var para={
// 				order_id:$("#order_id").val(),
// 				template_code:'08006',
// 				p_num:0,
// 				use_id:useId
// 			};
			//printTemplate("queryMedOrderInitByPrintTemlate.do",para);
		//alert(11111111111);
		var para={
				
				template_code:'08006',
				class_name:"com.chd.hrp.med.serviceImpl.order.init.MedOrderInitServiceImpl",
				method_name:"queryMedOrderInitByPrintTemlatePage",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:false,//是否预览，默认直接打印
				order_id:$("#order_id").val(),
				p_num:0,
 				use_id:useId
		};
		
		officeFormPrint(para);
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		
		var useId=0;//统一打印
		if('${p08023 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}
			
		
// 		parent.parent.$.ligerDialog.open({url : 'hrp/med/order/init/medOrderInitPrintSetPage.do?template_code=08006&use_id='+useId,
// 			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
// 		});

		officeFormTemplate({template_code:"08004"});
	}
	
	
	
    </script>
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
	        <tr>
	            <td align="right" class="l-table-edit-td" >订单编号：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="order_id" type="hidden" id="order_id" value="${medOrderMain.order_id}" ltype="text" />
	            	<input name="order_code" type="text" id="order_code" readOnly value="${medOrderMain.order_code}" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>编制日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="order_date" id="order_date" required="true" value="${medOrderMain.order_date}" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"><font color="red">*</font>供应商：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>采购方式：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input id="typeA" name="pur_type" type="radio" value="1" onChange="change();"/>自购订单
	            	<input id="typeB" name="pur_type" type="radio" value="2" onChange="change();"/>统购订单
           		</td>
           		
           		<td align="right" class="l-table-edit-td demo"><font color="red">*</font>收货单位：</td>
				<td align="left" class="l-table-edit-td demo">
					<input name="take_hos_id" type="text" id="take_hos_id" required="false" ltype="text" />
				</td>	
								            
				<td align="right" class="l-table-edit-td demo" ><font color="red">*</font>付款单位：</td>
				<td align="left" class="l-table-edit-td demo">
					<input name="pay_hos_id" type="text" id="pay_hos_id" required="false" ltype="text"  />
				</td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td" ><font color="red">*</font>订单类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="order_type" type="text" id="order_type"  ltype="text" required="true" validate="{required:true,maxlength:20}"/>
	            </td>
	        	
	            <td align="right" class="l-table-edit-td">采购类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" >付款条件：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="pay_code" type="text" id="pay_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	           
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" >采购部门：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_code" type="text" id="dept_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" >采购员：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	             <td align="right" class="l-table-edit-td" ><font color="red">*</font>计划到货日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="arrive_date" id="arrive_date" required="true" value="${medOrderMain.arrive_date}" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	     
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" >到货地址：</td>
	            <td align="left" class="l-table-edit-td" colspan="3">
	            	<input name="arr_address" type="text" id="arr_address" ltype="text"  value="${medOrderMain.arr_address}" validate="{required:true,maxlength:50}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >备注：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="note" type="text" id="note" ltype="text" value="${medOrderMain.note}" validate="{required:true,maxlength:50}" />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
            	<td align="left" class="l-table-edit-td">
            		<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
            	</td>
            	
				<td align="right" class="l-table-edit-td dept"  style="padding-left:20px;">定向科室：</td>
	            <td align="left" class="l-table-edit-td dept" >
	            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text" />
	            </td>
	            <td align="left"></td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<%-- <tr>	
				<td align="center" class="l-table-edit-td" >
					制单人：${medOrderMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					审核人：${medOrderMain.checker_name}
				</td>
			</tr> --%>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>模板打印（<u>M</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="close" accessKey="M"><b>关闭（<u>L</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
