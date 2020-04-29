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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var detailGrid;
    var gridRowData;
    var delivery_id, delivery_no;
    
    $(function ()
    {
    	$("#divAll").ligerLayout({
        	topHeight: 100, 
		});
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
        loadDetailHead(null);
		loadHotkeys();
    });
    //初始化全局变量
    function initPara(){
    	sup_id = "";
        delivery_id = "";
        delivery_no = "";
        if(detailGrid.getData().length > 0){
			detailGrid.deleteAllRows();
        }
    }
    
    //页面渲染
    function loadDict(){
		//字典下拉框
		autocomplete("#sup_id", "../../../mat/queryHosSupDict.do?isCheck=false", "id", "text", true, true, {is_sup: 1}, false, false, 238);
		autoCompleteByData("#come_from", [{"id": "1", "text": "订单生成"}, {"id": "2", "text": "手工录入"}], "id", "text", true, true);
		autoCompleteByData("#state", [{"id": "0", "text": "待签收"}, {"id": "1", "text": "已签收"}, {"id": "2", "text": "已作废"}], "id", "text", true, true);
		autoCompleteByData("#bill_type", [{"id": "1", "text": "普通订单" }, {"id": "2", "text": "代销订单"}], "id", "text", true, true, "", 1);
		autoCompleteByData("#in_state", [{"id": "1", "text": "未入库" }, {"id": "2", "text": "部分入库"}, {"id": "3", "text": "已入库"}], "id", "text", true, true, "", 1, false, 238);
		
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "before_month");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date",'yyyy-MM-dd', "new");
        $("#delivery_no").ligerTextBox({width:160});
	}
    
    //查询
    function  query(){
    	/* if(!liger.get("sup_id").getValue()){
    		$.ligerDialog.error("请选择供应商！");
    		return false;
    	} */
    	
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'delivery_no',
			value : $("#delivery_no").val()
		}); 
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'come_from',
			value : liger.get("come_from").getValue() == null ? "" : liger.get("come_from").getValue()
		}); 
		grid.options.parms.push({
			name : 'bill_type',
			value : liger.get("bill_type").getValue() == null ? "" : liger.get("bill_type").getValue()
		}); 
		grid.options.parms.push({
			name : 'in_state',
			value : liger.get("in_state").getValue() == null ? "" : liger.get("in_state").getValue()
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	initPara();
	}
    
    function changeSup(id, no, code, name){

		liger.get("sup_id").setValue(id+","+no);
		liger.get("sup_id").setText(code+" "+name);
		
		query();
    }

	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '供应商编码', name: 'sup_code', align: 'left', width: 90,
				}, { 
					display: '供应商名称', name: 'sup_name', align: 'left', width: 180,
				}, { 
					display: '送货单号', name: 'delivery_no', align: 'left', width: 110,
				}, { 
		 			display: '单据类型', name: 'come_from', align: 'left', width: 90,
		 		}, { 
		 			display: '业务类型', name: 'bill_type', align: 'left', width: 90,
		 		}, { 
		 			display: '配送日期', name: 'create_date', align: 'left', width: 90,
		 		}, { 
		 			display: '发票号', name: 'bill_no', align: 'left', width: 100,
		 		} , { 
		 			display: '发票日期', name: 'bill_date', align: 'left', width: 90,
		 		} , { 
		 			display: '状态', name: 'state', align: 'left', width: 80
		 		},  { 
		 			display: '送货地址', name: 'delivery_address', align: 'left', width: 150,
		 		}, { 
		 			display: '备注', name: 'note', align: 'left', width: 200,
		 		}
		 	],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDeliveryList.do',
			inWindow: false, width: '100%', height: '60%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '签收（<u>S</u>）', id: 'audit', click: audit, icon:'audit' },
				{ line:true }, 
				{ text: '作废（<u>Z</u>）', id: 'cancel', click: cancel, icon:'bcancle' }
			]},  
			onClickRow : function (rowdata, rowindex, value){
				//点击不同的行需重新加载明细数据
				if(delivery_id != rowdata.delivery_id){
					gridRowData = rowdata;
					changeDetailGrid(rowdata.delivery_id, rowdata.delivery_no);
				}
			},
			onDblClickRow : function (rowdata, rowindex, value){
				//双击行根据本行供应商重新查询数据
				changeSup(rowdata.sup_id, rowdata.sup_no, rowdata.sup_code, rowdata.sup_name);
			},
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //查询
    function  queryDetail(){
    	detailGrid.options.parms=[];
    	detailGrid.options.newPage=1;
        //根据表字段进行添加查询条件
 		detailGrid.options.parms.push({
 			name : 'delivery_id',
 			value : delivery_id
 		});
 		detailGrid.options.parms.push({
 			name : 'delivery_no',
 			value : delivery_no
 		});
 		
    	//加载查询条件
    	detailGrid.loadData(detailGrid.where);
     }
    
    function changeDetailGrid(d_id, d_no){
    	
    	delivery_id = d_id;
    	delivery_no = d_no;
    	
    	queryDetail();
    }

    function loadDetailHead(){
    	detailGrid = $("#detailgrid").ligerGrid({
			columns : [ 
				{
					display : '交易编码', name : 'bid_code', width : 100, align : 'left', frozen: true,
				},{
					display : '材料编码', name : 'inv_code', width : 120, align : 'left', frozen: true,
				}, {
					display : '材料名称', name : 'inv_id', textField : 'inv_name', width : 200, align : 'left', frozen: true,
				}, {
					display : '规格型号', name : 'inv_model', width : 180, align : 'left'
				}, {
					display : '生成厂商', name : 'fac_name', width : 200, align : 'left'
				}, {
					display : '计量单位', name : 'unit_name', width : 80, align : 'left'
				}, {
					display : '单价', name : 'price', width : 80, align : 'right', 
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${p04006}', 1);
					}
				}, {
					display : '入库数量(E)', name : 'amount', width : 80, align : 'right', editor : {type : 'float'},
				}, {
					display : '送货单数量', name : 'dlv_amount', width : 80, align : 'right',
				}, {
					display : '已入库数量', name : 'in_amount', width : 80, align : 'right',
				}, {
					display : '金额', name : 'amount_money', width : 80, align : 'right',
					render : function(rowdata, rowindex, value) {
						return value == null ? "" : formatNumber(value, '${p04005}', 1);
					}
				}, {
					display : '条形码', name : 'bar_code', width : 160, align : 'right',
				}, {
					display : '生产批号', name : 'batch_no', width : 80, align : 'right',
				}, {
					display : '生产日期', name : 'fac_date', width : 80, align : 'right',
				}, {
					display : '有效期', name : 'inva_date', width : 80, align : 'right',
				}, {
					display : '灭菌批号', name : 'disinfect_no', width : 80, align : 'right',
				}, {
					display : '灭菌日期', name : 'disinfect_date', width : 80, align : 'right',
				}, {
					display : '序列号', name : 'serial_no', width : 80, align : 'right',
				}, {
					display : '是否代销材料', name : 'is_com', width : 100, align : 'left',
				}, {
					display : '备注(E)', name : 'note', width : 200, align : 'left',
				} 
			],
			dataAction : 'server', dataType : 'server', usePager : false, 
			width : '100%', height : '40%', inWindow: false, heightDiff: 28, 
			url : 'queryMatDeliveryDetailByCode.do?isCheck=false', 
			checkbox : true, enabledEdit : true, isAddRow: false, alternatingRow : true, 
			isScroll : true, rownumbers : true, delayLoad : true,//初始化明细数据 
			toolbar: { items: [
   				{ text: '入库（<u>R</u>）', id: 'addIn', click: addIn, icon:'add' },
   				{ line:true }, 
   			]},  
		});
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
	
    //审核
	function audit(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var group_id, hos_id, copy_code; 
			var delivery_id = "";
			var is_frist = true;
			$(data).each(function (){
				if(is_frist){
					group_id = this.group_id;
					hos_id = this.hos_id;
					copy_code = this.copy_code;
					delivery_id = this.delivery_id;
				}else{
					delivery_id += "," + this.delivery_id;
				}
			});
			var parms = {
					group_id: group_id, 
					hos_id: hos_id, 
					copy_code: copy_code,
					delivery_id: delivery_id,
					state: 1
			} 
			$.ligerDialog.confirm('您是否要验收?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMatDeliveryState.do", parms, function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
    //作废
    function cancel(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var group_id, hos_id, copy_code; 
			var delivery_id = "";
			var is_frist = true;
			$(data).each(function (){
				if(is_frist){
					group_id = this.group_id;
					hos_id = this.hos_id;
					copy_code = this.copy_code;
					delivery_id = this.delivery_id;
				}else{
					delivery_id += "," + this.delivery_id;
				}
			});
			var parms = {
					group_id: group_id, 
					hos_id: hos_id, 
					copy_code: copy_code,
					delivery_id: delivery_id,
					state: 2
			} 
			$.ligerDialog.confirm('确定作废单据?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMatDeliveryState.do", parms, function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}

    //入库
	function addIn(){
    	//校验状态
    	var parms = {
    			group_id: gridRowData.group_id, 
    			hos_id: gridRowData.hos_id, 
    			copy_code: gridRowData.copy_code, 
    			delivery_id: gridRowData.delivery_id 
    	} 
		ajaxJsonObjectByUrl("queryMatDeliveryMainByCode.do?isCheck=false", parms, function (responseData){
			if(responseData.state == "1"){
				var data = detailGrid.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择要生成入库单的材料');
					return false;
				} 
				
				if(validateDetail(data)){
					//打开主表页面
					parent.$.ligerDialog.open({
						title : '入库单信息',
						width : $(window).width(), height : '300',
						url : 'hrp/sys/ecs/supDeli/addInPage.do?isCheck=false', 
						modal : true, showToggle : false, showMax : false, showMin : true, isResize : true,
						parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
						data: {gridRowData: gridRowData, data: data}
					});
				}
			}else{
				$.ligerDialog.error("已签收的送货单才可以入库！");
			}
		});
	}

	function validateDetail(data) {
		
		var msg="";
 		//判断grid 中的数量是否合法
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
			if (!v.amount || v.amount < 0 || v.amount + v.in_amount > v.dlv_amount) {
				msg += "第"+(i+1)+"行<br/>";
			}  
 		});
 		
		if(msg != ""){
			$.ligerDialog.warn(msg + "数量必须大于0且与入库数量之和不能大于送货单数量！");  
			return false;  
		}
		
		return true;
	}
    
    function ligerMsg(){
    	$.ligerDialog.success('操作成功');
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div id="divAll" style="width: 100%; height 100%; margin: 0px; padding: 0px;">
	    <div position="top">
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		        <tr>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td" >
		            	送货日期：
		            </td>
		            <td align="left" class="l-table-edit-td" >
						<table>
							<tr>
								<td align="left" >
									<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
								</td>
								<td align="right" class="l-table-edit-td"  >
									至：
								</td>
								<td align="left" class="l-table-edit-td">
									<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
								</td>
		            		</tr>
						</table>
			        </td>
					<td align="right" class="l-table-edit-td" >
						送货单号：
					</td>
		            <td align="left" class="l-table-edit-td" >
		            	<input name="delivery_no" type="text" id="delivery_no" ltype="text" validate="{required:false}" />
		            </td>
		        	<td align="right" class="l-table-edit-td" >
		            	单据类型：
		            </td>
		            <td align="left" class="l-table-edit-td" >
						<input name="bill_type" type="text" id="bill_type" ltype="text" validate="{required:false}" />
			        </td>
		        </tr> 
		        <tr>
					<td align="right" class="l-table-edit-td"  >
						<!-- <span style="color: red">*</span> -->
						供应商：
					</td>
		            <td align="left" class="l-table-edit-td" >
		            	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false}" />
		            </td>
					<td align="right" class="l-table-edit-td" >
						单据来源：
					</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="come_from" type="text" id="come_from" ltype="text" validate="{required:false}" />
		            </td>
					<td align="right" class="l-table-edit-td" >
						单据状态：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="state" type="text" id="state" ltype="text" validate="{required:false}" />
					</td>
		        </tr> 
		        <tr>
					<td align="right" class="l-table-edit-td"  >
						入库状态：
					</td>
		            <td align="left" class="l-table-edit-td" >
		            	<input name="in_state" type="text" id="in_state" ltype="text" validate="{required:false}" />
		            </td>
				</tr>
		    </table>
		</div>
    	<div position="center">
			<div id="maingrid"></div>
			<div id="detailgrid"></div>
		</div>
	</div>
</body>
</html>
