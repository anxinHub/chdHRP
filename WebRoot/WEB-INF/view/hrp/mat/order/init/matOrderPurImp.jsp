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
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
	var grid;
	var detailGrid;
	var gridManager = null;
	var userUpdateStr;
	var pur_hos_id ;
	var take_hos_id;
	var pay_hos_id;
	$(function (){
    	
		loadDict();//加载下拉框
		loadHotkeys();
		loadHead(null);
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
	
	
	function loadDict(){
    	//字典下拉框
    	autocomplete("#pur_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true);//采购单位
		autocomplete("#req_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true);//请购单位
		
		//供应商
		autocomplete("#sup_code","../../queryHosSupDict.do?isCheck=false","id", "text", true, true, "", false, false, 200, false, 238);
		
		$("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        $("#brif").ligerTextBox({width : 160});
        $("#sup_code").ligerTextBox({width : 160});
        
        autocomplete("#take_hos_id", "../../queryMatHosInfoDict.do?isCheck=false", "id", "text", true, true,"", true);//收货单位
        autocomplete("#pay_hos_id", "../../queryMatHosInfoDict.do?isCheck=false", "id", "text", true, true,"", false);//付款单位
		//autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",true);//订单类型
		

		if(this.is_com == 1){
			autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",true,'2');//订单类型
			
		}else{
			autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",true,'1');//订单类型
		}
		autoCompleteByData("#is_com", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
		//liger.get("order_type").setValue(1);
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true,"", false);//采购类型
    	autocomplete("#pay_code", "../../queryMatPayTerm.do?isCheck=false", "id", "text", true, true,"", false);//付款方式
		autocomplete("#dept_code", "../../queryPurDept.do?isCheck=false", "id", "text", true, true,"", false);//采购部门
		autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true,
				"{dept_id : "+liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]+"}", false);//采购人
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
	
		autocomplete("#dir_dept_id","../../queryMatDeptDict.do?isCheck=false","id","text",true,true,"",false,false,'160');//定向科室	
				
		autocomplete("#store_code","../../queryMatStoreDictPro.do?isCheck=false", "id", "text", true, true,{is_write:'1',is_purchase:'1'});
		var store_id = liger.get("store_code").getValue().split(",")[0];
		autodate("#order_date");
		//autodate("#arrive_date");
        $("#order_code").ligerTextBox({width:160, disabled:true});        
        $("#order_date").ligerTextBox({width:160});
        //$("#arrive_date").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:160});       
        $("#order_type").ligerTextBox({width:160});
        $("#take_hos_id").ligerTextBox({width:160});
        $("#pay_hos_id").ligerTextBox({width:160});
        
        $("#stock_type_code").ligerTextBox({width:160});
        $("#pay_code").ligerTextBox({width:160});
        
        $("#dept_code").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        $("#store_code").ligerTextBox({width:160});
        $("#note").ligerTextBox({width:160});
        
        //change();
	} 
	//获取仓库采购员
	function charge_store(){ 
		
    	var store_id = liger.get("store_code").getValue().split(",")[0];  
		autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
		
	}
	//选择定向与非定向是否显示定向科室
    function changeDir(){
		 if(liger.get("is_dir").getValue()=='1'){
			 $(".dept").attr("style","visibility:true");
		 }else{
			 $(".dept").attr("style","visibility:hidden");
		 }
	 }
    /* function singleSel(){
   	 	if($("input[type='radio']:checked").val() == '1'){
   		 	$(".demo").attr("style","visibility:hidden");
			//changeDir();
   	 	}else{
   		 $(".demo").attr("style","visibility:true");
		 	//changeDir();
   	 	}
    } */
    
    
     
	function query(){
		grid.options.parms = [];
		grid.options.newPage = 1;
 		
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		}); 
		grid.options.parms.push({
			name : 'pur_hos_id',
			value : liger.get("pur_hos_id").getValue() == null ? "" : liger.get("pur_hos_id").getValue()
		}); 
		grid.options.parms.push({
			name : 'req_hos_id',
			value : liger.get("req_hos_id").getValue() == null ? "" : liger.get("req_hos_id").getValue()
		});
		grid.options.parms.push({
			name : 'pay_hos_id',
			value: liger.get("pay_hos_id").getValue() == null ? "" : liger.get("pay_hos_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'brif',
			value : $("#brif").val()
		}); 
		grid.options.parms.push({
			name : 'is_dir',
			value : liger.get("is_dir").getValue()
		}); 
		grid.options.parms.push({
			name : 'is_com',
			value : liger.get("is_com").getValue()
		}); 
		
		grid.options.parms.push({
			name : 'dir_dept_id',
			value : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		
		//加载查询条件
		grid.loadData(grid.where);
	}
    
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns : [
			           {display : '采购计划编号',name : 'pur_code',width : 120,align : 'left'/* ,
						render : function(rowdata, rowindex, value) {
							return '<a href=javascript:update_open("' 
								+ rowdata.group_id 
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code 
								+ ',' + rowdata.pur_id
								+ '")>'+rowdata.pur_code+'</a>';
							} */
					},{display : '供应商', name : 'sup_name', textField : 'sup_name', width : 200, align : 'left'
					}, {display : '材料编码', name : 'inv_code', width : 120, align : 'left',
						totalSummary: {
		                    align : 'right',
		                    render: function (suminf, column, cell) {
		                    	return '<div>合计：</div>';
		                    }
		                }
					},{display : '需求仓库', name : 'store_name',  width : 200, align : 'left'
					},{
						display: '是否代销', name: 'is_com', align: 'left', width:50, reg:"1=是,else=否",
						render : function(rowdata, rowindex, value) {
							return rowdata.is_com == 1 ? "是" : "否";
						}
					},{
						display : '材料名称', name : 'inv_name', width : 300, align : 'left'
					}, {
						display : '规格型号', name : 'inv_model', width : 100, align : 'left'
					}, {
						display : '计量单位', name : 'unit_name', width : 80, align : 'left'
					}, {
						display : '采购数量', name : 'pur_amount', width : 80, align : 'left',
						render : function(rowdata, rowindex, value) {
							return formatNumber(value == null ? 0 : value, 2, 1);
						},
						totalSummary: {
							align : 'left',
		                    render: function (suminf, column, cell) {
		                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1)+ '</div>';
		                    }
		                }
					 },{
						display : '已入订单量', name : 'already_amount', width : 80, align : 'left',
						totalSummary : {
							align : 'right',
							render : function(suminf, column,cell) {
									return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
							}
						} 
					}, {
						display : '订单数量', name : 'amount', width : 80, align : 'left',
						editor : {type : 'float',},
						totalSummary : {
							align : 'right',
							render : function(suminf, column,cell) {
									return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
							}
						}
					}, {
						display : '单价', name : 'price', width : 120, align : 'right',editor : {type : 'float'},
						render : function(rowdata, rowindex, value) {
							return formatNumber(value == null ? 0 : value, '${p04006 }', 1);
						}
					}, {
						display : '金额', name : 'amount_money', width : 120, align : 'right',
						render : function(rowdata, rowindex, value) {
							return formatNumber(value == null ? 0 : value, '${p04005 }', 1);
						},
						totalSummary: {
							align: 'right',
							render: function (suminf, column, cell) {
								return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
						   }
						}
					}, {
						display : '申请科室', name : 'app_dept_id', textField : 'app_dept_name', width : 150, align : 'left',
					}, {
						display : '申请日期', name : 'app_date', width : 110, align : 'left',
					}, {
						display : '备注', name : 'memo', width : 120, align : 'left',
					},{
						display : '业务员', name : 'ven_person', width : 120, align : 'left',
					},{
						display : '联系方式', name : 'phone', width : 120, align : 'left',
					}/* ,{
						display : '经营许可证号', name : 'cert_code', width : 80, align : 'left',
					},{
						display : '起始日期', name : 'start_date', width : 80, align : 'left',
					} ,{
						display : '到期日期', name : 'end_date', width : 80, align : 'left',
					} */ ],
				dataAction: 'server',dataType: 'server',usePager:true,
				url:'queryMatPurDetailGenOrder.do?isCheck=false',
				width: '100%', height: '100%', checkbox: true, rownumbers:true,
				delayLoad : true,enabledEdit : true,//初始化不加载，默认false
				selectRowButtonOnly:true,isAddRow: false,//heightDiff: -10,
				onBeforeEdit: f_onBeforeEdit, onBeforeSubmitEdit: f_onBeforeSubmitEdit, onAfterEdit: f_onAfterEdit,
				toolbar: { items: [
							{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				   			{ line:true },
				   			{text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete'}, 
							{ line:true },
							{ text: '生成订单（<u>A</u>）', id:'createOrder', click: createOrder, icon:'add' },
							{ line:true },
							{ text: '关闭材料（<u>B</u>）', id:'closeInv', click: closeInv, icon:'close' },
							{ line:true },
							{ text: '已关闭材料列表（<u>E</u>）', id:'closeInvInfo', click: closeInvInfo, icon:'search' },
							{ line:true },
							{ text: '关闭（<u>L</u>）', id:'close', click: this_close, icon:'close' }
				]},  
				onDblClickRow : function (rowdata, rowindex, value){
					if(rowdata.sup_id == null){
						$.ligerDialog.warn('请选择数据 ');
						return ; 
					}
					changeSupCode(rowdata);
				}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
    }
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
		
		if(column_name=='cert_id'){
			var certId = grid.getColumnByName("cert_id");
			certId.editor.url='../../queryMatInvCertIdWithDate.do?isCheck=false&inv_id='+e.record.inv_id;
		}
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		//查看入库数量是否和订单数量相符
		/* if(e.column.name == "amount"){
			//alert(e.record.order_amount);
			if(checkValue(e.record.inv_id,e.record.pur_amount) == '1'){
				$.ligerDialog.warn("订单数量大于采购数量！",'','',{closeWhenEnter:false});
				return;
			}
		}  */
		
		if (e.value != "") {
			if (e.column.name == "inv_id") {
				//判断是否为自动有效日期
				if ('${p04009 }' != 0) {
					grid.updateCell('inva_date', getDateAddDay(new Date(), '${p04009 }'), e.rowindex);
				}
			} else if (e.column.name == "amount") {
				
				//自动计算金额
				if (e.record.price != undefined && e.record.price != "") {
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算零售金额
				if (e.record.sell_price != undefined && e.record.sell_price != "") {
					grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
				}
				//自动计算批发金额
				if (e.record.sale_price != undefined && e.record.sale_price != "") {
					grid.updateCell('sale_money', e.value * e.record.sale_price, e.rowindex);
				}
				
				 
			} else if (e.column.name == "price") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('amount_money', e.record.amount ? e.value * e.record.amount : 0, e.rowindex);
				}
				
			}else if (e.column.name == "sell_price") {
				//自动计算零售金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			} else if (e.column.name == "sale_price") {
				//自动计算批发金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sale_money', e.value * e.record.amount, e.rowindex);
				}
			} else if (e.column.name == "sale_money") {
				//自动计算批发单价
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sale_price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
			} else if (e.column.name == "amount_money") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
				
			}
		}
		grid.updateTotalSummary();
		return true;
	}
	
	//验证数量
	function checkValue(inv_id,pur_amount){
		var sumAmt = 0.00;
		var data = gridManager.getData();
		$.each(data, function (i, v) {
			if(v.inv_id == inv_id){
				sumAmt = parseInt(sumAmt)+parseInt(v.amount);
			}
		});

		if((pur_amount != sumAmt) && (pur_amount < sumAmt)){
			return "1";
		}else{
			return "0";
		}
	}
	
	//关闭材料列表
	function closeInvInfo(){
		var sup_id = liger.get("sup_code").getValue();
    	var sup_text = liger.get("sup_code").getText();
		if(!sup_id){
    		$.ligerDialog.warn('请选择供应商！');
    		return false;
    	}
		var paras = sup_id+"@"+sup_text;
		$.ligerDialog.open({
			url: "matPurMainCloseInvInfosPage.do?isCheck=false&paras="+paras, 
			height: 500,width: 900, title:'关闭材料列表',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
		});
		
    }
	//关闭材料
	function closeInv(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
	 		var ParamVo =[];
	 		
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.pur_id +"@"+ 
					this.pur_detail_id
				) 
			});
			$.ligerDialog.confirm('确定要关闭选中的材料？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMatPurCloseInvs.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
							query();
			            }
					});
				}
			});
		}
	}
	
	//改变供应商
	function changeSupCode(rowdata){		

		liger.get("sup_code").setValue(rowdata.sup_id+","+rowdata.sup_no);
		liger.get("sup_code").setText(rowdata.sup_code+" "+rowdata.sup_name);
		
		liger.get("store_code").setValue(rowdata.store_id+","+rowdata.store_no);
		liger.get("store_code").setText(rowdata.store_code+" "+rowdata.store_name);
		
		liger.get("stocker").setValue(rowdata.emp_id);
		liger.get("stocker").setText(rowdata.emp_code+" "+rowdata.emp_name);
		
		pur_hos_id = rowdata.pur_hos_id;
		take_hos_id = rowdata.take_hos_id;
		pay_hos_id = rowdata.pay_hos_id;
		query();

		if(rowdata.is_com == 1){
			autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",false,'2');//订单类型
			autoCompleteByData("#is_com", yes_or_no.Rows, "id", "text", true, true,'',false,'1');//是否代销
			
		}else{
			autoCompleteByData("#order_type", matOrderMain_orderType.Rows, "id", "text", true, true,"",false,'1');//订单类型
			autoCompleteByData("#is_com", yes_or_no.Rows, "id", "text", true, true,'',false,'0');//是否代销
		}
	
		
    }
	
	//删除
	function remove() {
		grid.deleteSelectedRow();
	}
	
	//关闭
	function this_close() {
		frameElement.dialog.close();
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('L', this_close);
	}
	
	
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
  		/* if(!liger.get("arrive_date").getValue()){
  			$.ligerDialog.warn("计划到货日期不能为空");  
  			return false;  
  		} */
  		
     	//明细
   		var msg="";
   		var rowm = "";
   		var rows = 0;
   		var data = gridManager.getCheckedRows();
   		//判断grid 中的数据是否重复或者为空
   		
   		if(data.length == 0){
 			$.ligerDialog.warn("请选择材料！");  
			return false;  
 		}
   		
   		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		} 	
   		return true;	
 	 }
  	//订单生成
  	function createOrder(){
  		
  		if(validateGrid()){
  			var formPara = {
  				order_code : $("#order_code").val(),
        		order_date : $("#order_date").val(),
        			
        		order_type : liger.get("order_type").getValue() == null ? "" : liger.get("order_type").getValue(),
        				
        		//take_hos_id : liger.get("take_hos_id").getValue() == null ? "" : liger.get("take_hos_id").getValue().split(",")[0],
        		take_hos_id : take_hos_id,
        		pay_hos_id : pay_hos_id,
        		pur_hos_id : pur_hos_id,	
        		pur_type : $('input[name="pur_type"]:checked').val(),
        		stock_type_code : liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue() ,
        		pay_code : liger.get("pay_code").getValue() == null ? "" : liger.get("pay_code").getValue() ,
        				
        		dept_id : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0],
        		dept_no : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[1],
        		stocker : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
        		is_dir : liger.get("is_dir").getValue(),
          		is_com : liger.get("is_com").getValue(),
        		dir_dept_id : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[0],
        		dir_dept_no : liger.get("dir_dept_id").getValue() == null ? "" : liger.get("dir_dept_id").getValue().split(",")[1],		
        							
        		//arr_address : $("#arr_address").val(),	
        		note : $("#note").val(),
  				come_from : 2,		
  				sup_id: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
  				sup_no: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
  				store_id: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
  				store_no: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
  				detailData: JSON.stringify(gridManager.getCheckedRows())
  			};
  			$.ligerDialog.confirm('确定生成订单?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("addMatOrderByPur.do?isCheck=false", formPara, function (responseData) {
						if (responseData.state == "true") {
							parentFrameUse().query();
							
							liger.get("sup_code").setValue("");
							liger.get("sup_code").setText("");
							liger.get("stocker").setValue("");
							liger.get("stocker").setText("");
							take_hos_id='';
							pay_hos_id='';
							pur_hos_id='';
							query();
							//parentFrameUse().update_open(responseData.update_para);
							//this_close();
						}
					});
				}
  			});
  		}
  	}
  	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
			<tr>
				<td align="right" class="l-table-edit-td" width="10%">日期范围：</td>
			    <td align="left" class="l-table-edit-td" width="20%">
					<table>
						<tr>
							<td align="left" >
								<input class="Wdate" name="begin_date" id="begin_date" type="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<td align="right" class="l-table-edit-td" >至：</td>
							<td align="left" class="l-table-edit-td">
								<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
							</td>
			            </tr>
					</table>
				</td>
				
			    <td align="right" class="l-table-edit-td" width="10%">采购单位：</td>
			    <td align="left" class="l-table-edit-td" width="20%">
			    	<input name="pur_hos_id" type="text" id="pur_hos_id" required="false" ltype="text" validate="{required:true}" />
			    </td>
			    
			    <td align="right" class="l-table-edit-td" width="10%">请购单位：</td>
			    <td align="left" class="l-table-edit-td" width="20%">
			    	<input name="req_hos_id" type="text" id="req_hos_id" required="false" ltype="text" validate="{required:true}" />
			    </td>
			 </tr> 
			 <tr>
			 	<td align="right" class="l-table-edit-td" width="10%">摘要：</td>
			 	<td align="left" class="l-table-edit-td" width="20%">
					<input name="brif" type="text" requried="false" id="brif" />
				</td>
				<!-- <td align="left" class="l-table-edit-td" >
					<input name="is_dir" type="checkbox" id="is_dir" ltype="text" />&nbsp;&nbsp;是否定向
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td> -->
				
			    <td align="right" class="l-table-edit-td" width="10%">付款单位：</td>
			    <td align="left" class="l-table-edit-td" width="20%">
			    	<input name="pay_hos_id" type="text" id="pay_hos_id" required="false" ltype="text" validate="{required:true}" />
			    </td>
				
				<td align="right" class="l-table-edit-td" width="10%">供应商：</td>
				<td align="left" class="l-table-edit-td" width="20%">
					<input name="sup_code" type="text" requried="false" id="sup_code" />
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
			</tr>
		</table>
		<hr/>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
			<tr>
	            <td align="right" class="l-table-edit-td" width="10%">订单编号：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="order_code" type="text" id="order_code" value="自动生成" disabled="disabled" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>编制日期：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input class="Wdate" name="order_date" id="order_date" required="true" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>订单类型：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="order_type" type="text" id="order_type"  ltype="text" required="true"   validate="{required:true,maxlength:20}"/>
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" width="10%"><font color="red">*</font>采购方式：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input id="typeA" name="pur_type" type="radio" value="1" checked="checked" />自购订单
	            	<input id="typeB" name="pur_type" type="radio" value="2" />统购订单
           		</td>
           		<td align="right" class="l-table-edit-td demo"  width="10%"><font color="red">*</font>收货单位：</td>
				<td align="left" class="l-table-edit-td demo" width="20%">
					<input name="take_hos_id" type="text" id="take_hos_id" required="false" ltype="text" />
				</td>
				
				<td align="right" class="l-table-edit-td" width="10%">付款条件：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="pay_code" type="text" id="pay_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	        </tr> 
	        <tr>
	        	
	            <td align="right" class="l-table-edit-td" width="10%">采购类型：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stock_type_code" type="text" id="stock_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" width="10%">采购部门：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="dept_code" type="text" id="dept_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" width="10%">采购员：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="stocker" type="text" id="stocker"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	        </tr>
	        <tr>
	           
	            <td align="right" class="l-table-edit-td" width="10%">备注：</td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="note" type="text" id="note" ltype="text"  validate="{required:true,maxlength:50}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否代销：</td>
            	<td align="left" class="l-table-edit-td">
            		<input name="is_com" type="text" id="is_com" ltype="text" />
            	</td>
            	
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">需求仓库：</td>
            	<td align="left" class="l-table-edit-td">
            		<input name="store_code" type="text" id="store_code" ltype="text" />
            	</td>
            	
			</tr>
			
		</table>
	</form>
	
	<div id="maingrid"></div>
</body>
</html>
