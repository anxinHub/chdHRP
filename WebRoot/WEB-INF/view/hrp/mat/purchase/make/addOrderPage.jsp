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
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     var is_com = "${is_com}" ;
     var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
 	 var detailData = dialog != null ? dialog.get("data") : "";
 	 
     $(function (){
		loadDict()//加载下拉框
		loadHead();		
		singleSel();
		var path = window.location.pathname+"/maingrid";
		var url = '../../../sys/querySysTableStyle.do?isCheck=false';
		loadColHeader({
			grid:grid,
			path:path,
			url:url
		});
		$("#store_code").bind("change", function () {
			charge_store();
			//loadHead();
			grid.reRender();
			
		});
		$("#dept_code").bind("change", function () {		
			liger.get("stocker").clear();
			liger.get("stocker").set("parms", { dept_id: liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0] });
			liger.get("stocker").reload();
		});
     });
     
     function singleSel(){
    	 if($("input[type='radio']:checked").val() == '1'){
    		 $(".demo").attr("style","visibility:hidden");
    		 //autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"0",'160');//是否定向
			 changeDir();
    	 }else{
    		 $(".demo").attr("style","visibility:true");
    		 //autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,"1",'160');//是否定向
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
     
     function query(){
    	 grid.options.parms = [];
 		 grid.options.newPage = 1;
 		
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
   		
   		if(data.length == 0){
 			$.ligerDialog.warn("请先添加材料！");  
			return false;  
 		}
   		
   		return true;	
 	 }
     
     //保存
     function  save(){
    	 
    	 if(validateGrid()){
    		 var allData = gridManager.getData();
             var formPara={
            		order_code : $("#order_code").val(),
        			order_date : $("#order_date").val(),
        			arrive_date : $("#arrive_date").val(),
        			sup_id : '${sup_id}',
        			sup_no : '${sup_no}',
        			store_id : '${store_id}',
        			store_no : '${store_no}',
        			order_type : liger.get("order_type").getValue() == null ? "" : liger.get("order_type").getValue(),
        			take_hos_id : liger.get("take_hos_id").getValue() == null ? "" : liger.get("take_hos_id").getValue().split(",")[0],
        			pay_hos_id : liger.get("pay_hos_id").getValue() == null ? "" : liger.get("pay_hos_id").getValue().split(",")[0],
        			pur_hos_id : liger.get("pur_hos_id").getValue() == null ? "" : liger.get("pur_hos_id").getValue().split(",")[0],
        			pur_type : $('input[name="pur_type"]:checked').val(),
        			stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue() ,
        			pay_code : liger.get("pay_code").getValue() == null ? "" : liger.get("pay_code").getValue() ,
        				
        			dept_id : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0],
        			dept_no : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[1],		
        			stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
        			is_dir : liger.get("is_dir").getValue(),
        			dir_dept_id : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[0],
        			dir_dept_no : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[1],		
        			come_from : 2,			
        			arr_address : $("#arr_address").val(),	
        			note : $("#note").val(),
        			is_dir : liger.get("is_dir").getValue(),
        			detailData : JSON.stringify(allData)
    		};
             
             var pageTitle="订单修改";
             var saveUrl = "addMatOrderInitByPur.do?isCheck=false";;
             var pageUrl = "hrp/mat/order/init/matOrderInitUpdatePage.do?isCheck=false";
             
            ajaxJsonObjectByUrl(saveUrl,formPara,function(responseData){
                if(responseData.state=="true"){
                	parentFrameUse().query();
                	var voStr = responseData.update_para.split(",");
            		var paras = "group_id=" + voStr[0].toString() + "&" 
            			+ "hos_id=" + voStr[1].toString() + "&" 
            			+ "copy_code=" + voStr[2].toString() + "&" 
            			+ "order_id=" + voStr[3].toString();
                	parent.$.ligerDialog.open({
    					title: pageTitle,
    					height: $(window).height(),
    					width: $(window).width(),
    					url: pageUrl + "&" + paras.toString(),
    					modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, 
    				});
                }
                this_close();
            });
    	 }
     }
     
  
    function loadDict(){
    	
    	//字典下拉框
    	//id, data, valueField, textField, autocomplete,highLight, parmsStr,defaultSelect,initvalue,initWidth
    	autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"", false);//供应商
    	liger.get("sup_code").setValue('${sup_id},${sup_no}');
		liger.get("sup_code").setText('${sup_text}'); 
		$("#sup_code").ligerComboBox({width:160,disabled:true,cancelable: false});
		
		autocomplete("#store_code", "../../queryMatStoreDictPro.do?isCheck=false", "id", "text", true, true,{is_write:'1',is_purchase:'1'});
    	liger.get("store_code").setValue('${store_id},${store_no}');
		liger.get("store_code").setText('${store_text}');
		var store_id = liger.get("store_code").getValue().split(",")[0];
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
		
    	autocomplete("#take_hos_id", "../../queryMatHosInfoDict.do?isCheck=false", "id", "text", true, true,"", true);//收货单位
		autocomplete("#pay_hos_id", "../../queryMatHosInfoDict.do?isCheck=false", "id", "text", true, true,"", true);//付款单位
		autocomplete("#pur_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true,"",true);//采购单位
		
		if(is_com == 1){
			autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",false,'2');//订单类型
			
		}else{
			autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",false,'1');//订单类型
		}
		
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true,"", false);//采购类型
    	autocomplete("#pay_code", "../../queryMatPayTerm.do?isCheck=false", "id", "text", true, true,"", false);//付款方式
		autocomplete("#dept_code", "../../queryPurDept.do?isCheck=false", "id", "text", true, true,"", false);//采购部门 
		/* autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,
				"{dept_id : "+liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]+"}", true);//采购人 */
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
		autocomplete("#dir_dept_id","../../queryMatDeptDict.do?isCheck=false","id","text",true,true,"",false,false,'160');//定向科室	
		autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
		$("#stocker").ligerTextBox({ width: 160 });	
		autodate("#order_date");
		autodate("#arrive_date");
        $("#order_code").ligerTextBox({width:160, disabled:true});        
        $("#order_date").ligerTextBox({width:160});
        $("#arrive_date").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:160});       
        $("#order_type").ligerTextBox({width:160});
        $("#take_hos_id").ligerTextBox({width:160});
        $("#pay_hos_id").ligerTextBox({width:160});
        
        $("#stock_type_code").ligerTextBox({width:160});
        $("#pay_code").ligerTextBox({width:160});
        
        $("#dept_code").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        
        $("#arr_address").ligerTextBox({width:500});
        $("#note").ligerTextBox({width:160});
        
        ///$("#genByPur").ligerButton({click: genByPur, width:120});
		change();
		
     } 
	
    function loadHead() {
    	//alert(JSON.stringify(detailData));
		grid = $("#maingrid").ligerGrid({
			columns : [
			    {display : '交易编码',name : 'bid_code',width : 150,align : 'left',
         	   			totalSummary: {align: 'right',render: 
		       		    function (suminf, column, cell) {
									return '<div>合计：</div>';
								}
						   }
				},{display : '材料编码',name : 'inv_code',width : 150,align : 'left',
				},{display : '材料名称',name : 'inv_name',width : 150,align : 'left',
					/*display : '材料名称(E)',name : 'inv_id',textField : 'inv_name',width : 150,align : 'left',
					 editor : {
						type : 'select',
						valueField : 'inv_id',
						textField : 'inv_name',
						selectBoxWidth : '80%',
						selectBoxHeight : 240,
						grid : {
							columns : [ {display : '交易编码',name : 'bid_code',minWidth : 100,align : 'left'},
							            {display : '材料编码',name : 'inv_code',minWidth : 100,align : 'left'}, 
									{display : '材料变更号',name : 'inv_no',align : 'left'}, 
									{display : '材料名称',name : 'inv_name',minWidth : 120,align : 'left'}, 
									{display : '规格型号',	name : 'inv_model',	minWidth : 80,align : 'left'}, 
									{display : '计量单位',	name : 'unit_name',	minWidth : 80,align : 'left'}, 
									{display : '包装单位',	name : 'pack_unit_name',minWidth : 80,align : 'left'}, 
									{display : '转换量',	name : 'num_exchange',	minWidth : 80,align : 'right'}, 
									{display : '供应商',	name : 'sup_name',		minWidth : 100,	align : 'left'}, 
									{display : '生产厂商',	name : 'fac_name',		minWidth : 100,	align : 'left'},
									{display : '计划价',	name : 'plan_price',	minWidth : 80,	align : 'right',
										render : function(rowdata, rowindex, value) {
											 return formatNumber(rowdata.plan_price ==null ? 0 : rowdata.plan_price,'${p04006}',1);
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
								url : '../../queryMatInvListDept.do?isCheck=false',//&store_id='+ liger.get("take_hos_id").getValue() == null ? "" : liger.get("take_hos_id").getValue().split(",")[0],
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
					} */
				}, {display : '规格型号', name : 'inv_model', width : 150, align : 'left'
				}, {display : '计量单位', name : 'unit_name', width : 100, align : 'left'
				},{ display : '包装单位(E)', name : 'pack_code', textField : 'pack_name', width : 100, align : 'left',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../queryMatHosPackage.do?isCheck=false',
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
				}, {display : '采购数量', name : 'amount',    width : 100, type : 'number',
					align : 'right',
					render : function(rowdata, rowindex, value) {
						return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
					},
					totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
						}
					}
				}, {display : '单价(E)', name : 'price', width : 100, align : 'right',
					editor : {
						type : 'numberbox',
						precision : '${p04006 }'
					},
					render : function(rowdata, rowindex, value) {
						rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
						return value == null ? "" : formatNumber(value, '${p04006 }', 1);
					}
				}, {display : '金额', name : 'amount_money', width : 100, type : 'number', align : 'right',
					render : function(rowdata, rowindex, value) {
						rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
						return value == null ? "" : formatNumber(value, '${p04005 }', 1);
					},totalSummary: {
						align: 'right',
						render: function (suminf, column, cell) {
							return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
						}
					}
				},{ display : '生产厂商', name : 'fac_name', align : 'left',width : 200
					
				}, { display : '备注', name : 'memo', align : 'left',width : 200,
					 editor : {
							type : 'text'
					 }
				},{display : '采购计划关系', name : 'pur_rela', align : 'left',hide:true,width:80}
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			width : '100%',
			height : '98%',
			checkbox : true,
			data : detailData,
			enabledEdit : true,
			alternatingRow : true,
			isAddRow: false,
			onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,
			onAfterEdit : f_onAfterEdit,
			isScroll : true,isAddRow: false,
			rownumbers : true,
			delayLoad : false,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ 
				            {text : '删除（<u>D</u>）',id : 'delete',click : remove,icon : 'delete'},
							{ line:true },
			       			{ text: '保存（<u>S</u>）', id:'save', click: save,icon:'save' },
			       			{ line:true },
			       			{ text: '关闭（<u>L</u>）', id:'close', click: this_close,icon:'close' }
			    ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		grid.toggleCol("inv_no", false);
		grid.toggleCol("pur_rela", false);
	}
	//获取仓库采购员
	function charge_store(){
		
    	var store_id = liger.get("store_code").getValue().split(",")[0];  
		autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
		
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
					inv_model : data.inv_model,
					unit_name : data.unit_name,
					pack_code : data.pack_code,
					fac_name : data.fac_name,
					pack_name : data.pack_name,
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
				if('${p04007}' != 0){
					grid.updateCell('arrive_date', getDateAddDay(new Date(), '${p04008}'), e.rowindex);
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

	
	//增加行数据
	function add_rows(data){
    	//grid.removeRange(gridManager.getData());
    	grid.addRows(data);
    }
	//删除行集合
	function deleteRange(data){
		grid.deleteRange(data);
	}

	//增加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
			//grid.beginEdit(0);
		}, 1000);
	}
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	//隐藏或显示  采购单位、请购单位、付款单位
    function change(){
    	singleSel();
    }
	
    </script>
  	
</head>
  
<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%">订单编号：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="order_code" type="text" id="order_code" value="自动生成" disabled="disabled" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>编制日期：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input class="Wdate" name="order_date" id="order_date" required="true" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>供应商：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="sup_code" type="text" id="sup_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>采购方式：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input id="typeA" name="pur_type" type="radio" value="1" checked="checked" onChange="singleSel();"/>自购订单
	            	<input id="typeB" name="pur_type" type="radio" value="2" onChange="singleSel();"/>统购订单
           		</td>
           		
           		<td align="right" class="l-table-edit-td demo"  width="10%"><font color="red">*</font>收货单位：</td>
				<td align="left" class="l-table-edit-td demo" width="20%">
					<input name="take_hos_id" type="text" id="take_hos_id" required="false" ltype="text" />
				</td>
					            
				<td align="right" class="l-table-edit-td demo" width="10%"><font color="red">*</font>付款单位：</td>
				<td align="left" class="l-table-edit-td demo" width="20%">
					<input name="pay_hos_id" type="text" id="pay_hos_id" required="false" ltype="text"  />
				</td>	
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>订单类型：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="order_type" type="text" id="order_type"  ltype="text" required="true"   validate="{required:true,maxlength:20}"/>
	            </td>
	        	
	            <td align="right" class="l-table-edit-td" width="10%">采购类型：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stock_type_code" type="text" id="stock_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" width="10%">付款条件：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="pay_code" type="text" id="pay_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	           
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" width="10%">采购部门：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="dept_code" type="text" id="dept_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" width="10%">采购员：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stocker" type="text" id="stocker"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>计划到货日期：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input class="Wdate" name="arrive_date" id="arrive_date" required="true" value="${matOrderMain.arrive_date}" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" width="10%">到货地址：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="arr_address" type="text" id="arr_address" ltype="text"  validate="{required:true,maxlength:50}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%">备注：</td>
	            <td align="left" class="l-table-edit-td"  width="20%">
	            	<input name="note" type="text" id="note" ltype="text"  validate="{required:true,maxlength:50}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%">需求仓库：</td>
	            <td align="left" class="l-table-edit-td"  width="20%">
	            	<input name="store_code" type="text" id="store_code" ltype="text"   />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td" width="10%">采购单位：</td>
			    <td align="left" class="l-table-edit-td" width="20%">
			    	<input name="pur_hos_id" type="text" id="pur_hos_id" required="false" ltype="text" validate="{required:true}" />
			    </td>
	            
	            <td align="right" class="l-table-edit-td"  width="10%" >是否定向：</td>
            	<td align="left" class="l-table-edit-td" width="20%">
            		<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
            	</td>
            	
            	<td align="right" class="l-table-edit-td dept"  width="10%" >定向科室：</td>
	            <td align="left" class="l-table-edit-td dept" width="20%">
	            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text" />
	            </td>
			</tr>
	    </table>
	    <div id="maingrid"></div>
    </form>
	
</body>
</html>
