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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		query();
		loadHotkeys();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({
			name : 'begin_check_date',
			value : $("#begin_check_date").val()
		});
		grid.options.parms.push({
			name : 'end_check_date',
			value : $("#end_check_date").val()
		});
		grid.options.parms.push({
			name : 'begin_in_date',
			value : $("#begin_in_date").val()
		});
		grid.options.parms.push({
			name : 'end_in_date',
			value : $("#end_in_date").val()
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue() == null ? "" : liger.get("store_id").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'deliver_no',
			value : $("#deliver_no").val()
		});
		grid.options.parms.push({
			name : 'origin_no',
			value : $("#origin_no").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : $("#state").val()
		});
		grid.options.parms.push({
			name : 'bill_no',
			value : $("#bill_no").val()
		});
		grid.options.parms.push({
			name : 'bill_state',
			value : liger.get("bill_state").getValue()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    {display: '单据号', name: 'deliver_no', align: 'left', width: 130,
						render : function(rowdata, rowindex, value) {
							return '<a href=javascript:update_open("'+ rowdata.group_id	+ ',' + rowdata.hos_id + ',' + rowdata.copy_code + ',' + rowdata.deliver_id	+ ',' + rowdata.deliver_no+'")>'+rowdata.deliver_no+'</a>';
						}
					},
				{display: '原始单号', name: 'origin_no', align: 'left',width:100},
				{display: '仓库', name: 'store_name', align: 'left',width:160},
		 		{display: '供应商', name: 'sup_name', align: 'left',width:240},
		 		{display: '采购员', name: 'stocker_name', align: 'left',width:80},
		 		{display: '入库日期', name: 'confirm_date', align: 'left',width: 80},
		 		{display: '库管员', name: 'confirmer_name', align: 'left',width: 80},
		 		{display: '金额', name: 'amount_money', align: 'right',width: 100 ,
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005}', 1);
						}
		 			}, 
				{display: '编制日期', name: 'in_date', align: 'left',width: 80}, 
		 		{display: '制单人', name: 'maker_name', align: 'left'	,width: 80},
		 		{display: '审核日期', name: 'check_date', align: 'left',width: 80	},
		 		{display: '审核人', name: 'checker_name', align: 'left',width: 80},
		 		{display: '状态', name: 'state', align: 'left',width: 80,
		 				render: function(rowdata,index,value){
		 					if(rowdata.state == 1){
		 						return "未审核";
		 					}else{
		 						return "审核";
		 					}
		 				}
		 			},
		 		{display: '发票号', name: 'bill_no', align: 'left',width: 120},
			 	{display: '发票状态', name: 'bill_state', align: 'left',width: 80},
		 		{display: '备注', name: 'brief', align: 'left',width:160}
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatNopayDeliver.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true, forzen:false ,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true }, 
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unaudit', click: unAudit, icon:'unaudit' },
				{ line:true },
				{ text: '复制（<u>F</u>）', id:'copy', click: copy, icon:'copy' },
	            { line:true },
                { text : '生成发票(<u>G</u>)', id : 'create_bill', click : create_bill, icon : 'edit' },
                { line: true },
                { text: '导入', id:'import', click: importDate,icon:'up' },
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function importDate (){
    	var para = {
			 "column" : [{
				"name" : "origin_no",
				"display" : "原始单号",
				"width" : "120"
			}, {
				"name" : 'in_date',
				"display" : '编制日期',
				"width" : "100",
				"require" : true
			},{
				"name" : 'sup_name',
				"display" : '供应商',
				"width" : "180",
				"require" : true
			},{
				"name" : 'store_name',
				"display" : '仓库',
				"width" : "160",
				"require" : true
			}, {
				"name" : 'stocker_name',
				"display" : '采购员',
				"width" : "90"
			}, {
				"name" : 'stock_type_name',
				"display" : '采购类型',
				"width" : "160"
			}, {
				"name" : 'bill_no',
				"display" : '发票号',
				"width" : "160"
			}, {
				"name" : 'bill_date',
				"display" : '发票日期',
				"width" : "100"
			}, {
				"name" : 'brief',
				"display" : '摘要',
				"width" : "200"
			}, {
				"name" : 'protocol_code',
				"display" : '协议编号',
				"width" : "180"
			}, {
				"name" : 'proj_name',
				"display" : '项目',
				"width" : "200"
			}, {
				"name" : 'inv_code',
				"display" : '材料编码',
				"width" : "140",
				"require" : true
			}, {
				"name" : 'inv_name',
				"display" : '材料名称',
				"width" : "180",
				"require" : true
			}, {
				"name" : 'batch_no',
				"display" : '批号',
				"width" : "120",
				"require" : true
			}, {
				"name" : 'price',
				"display" : '单价',
				"width" : "100",
				"require" : true
			}, {
				"name" : 'amount',
				"display" : '数量',
				"width" : "90",
				"require" : true
			},{
				"name" : "amount_money",
				"display" : "金额",
				"width" : "100",
				"require" : true
			},{
				"name" : "bar_code",
				"display" : "条形码",
				"width" : "140"
			},{
				"name" : "inva_date",
				"display" : "有效日期",
				"width" : "100"
			},{
				"name" : "disinfect_date",
				"display" : "灭菌日期",
				"width" : "100"
			},{
				"name" : "confirmer",
				"display" : "库管员",
				"width" : "90"
			},{
				"name" : "note",
				"display" : "备注",
				"width" : "200"
			}] 
		};
		importSpreadView("hrp/mat/payment/deliver/impData.do?isCheck=false", para)
    }
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
	}
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '添加期初未付款送货单',
			url: 'hrp/mat/payment/deliver/matNopayDeliverAddPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data:{}, 
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: true, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
    
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"deliver_id="+vo[3];
		parent.$.ligerDialog.open({
			title: '期初未付款送货单修改',
			url: 'hrp/mat/payment/deliver/matNopayDeliverUpdatePage.do?isCheck=false&' + paras.toString(),
			height: $(window).height(),
			width: $(window).width(),
			data:{}, 
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: true, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    //删除
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var deliver_nos = "";
			$(data).each(function (){		
				if(this.state == 1){
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.deliver_id 
						) 
				}else{
					deliver_nos = deliver_nos + this.deliver_no + ",";
				}
				
			});
			if(deliver_nos != ""){
				$.ligerDialog.error("删除失败！"+deliver_nos+"单据不是未审核状态,不允许删除");
				return;
			}
			if(ParamVo != null && ParamVo != ''){
				$.ligerDialog.confirm('确定删除?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("deleteMatNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
			}else{
				$.ligerDialog.error("无删除数据");
			}
			
		}
	}
	
	// 审核
	function audit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var deliver_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					deliver_nos = deliver_nos + this.deliver_no + ",";
				}else{
					ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.deliver_id  +"@"+  
							this.deliver_no  +"@"+  
							this.state  +"@"+ 3
						) 
				}
			});
			if(deliver_nos != ""){
				$.ligerDialog.error("审核失败！"+deliver_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateStateMatNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	//消审
	function unAudit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var deliver_nos = "";
			$(data).each(function (){		
				if(this.state != 3){
					deliver_nos = deliver_nos + this.deliver_no + ",";
				}else{
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.deliver_id  +"@"+  
						this.deliver_no  +"@"+  
						this.state  +"@"+ 1
					) 
				}
				
			});
			if(deliver_nos != ""){
				$.ligerDialog.error("消审失败！"+deliver_nos+"单据不是已审核确认状态");
				return;
			}
			$.ligerDialog.confirm('确定消审确认吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateStateMatNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//复制
	 function copy(){
		 var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				var ParamVo =[];
				$(data).each(function (){		
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.deliver_id + "@" +
						this.deliver_no
					) 
				});
				$.ligerDialog.confirm('确定复制?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("copyMatNopayDeliver.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
			}
	 }
	
	//生成发票
	function create_bill(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return;
		} else {
			var ParamVo = [];
			var deliver_nos = "";
			var billNos = "";
			var billDate = "";
			var stateNos = "";
			$(data).each(function() {
				if(this.state != 3){
					stateNos = stateNos + this.deliver_no + "<br>";
				}
				
				if (this.bill_no == "" || this.bill_no == null || this.bill_no == 'undefined') {
					billNos = billNos + this.deliver_no + "<br>";
				}
				
				if(this.bill_date == "" || this.bill_date == null || this.bill_date == 'undefined') {
					billDate = billDate + this.deliver_no + "<br>";
				}
				
				ParamVo.push(
					this.group_id + "@" + 
					this.hos_id + "@"+ 
					this.copy_code + "@" + 
					this.deliver_id + "@" +
					this.sup_id + "@" +
					this.bill_date + "@" +
					this.bill_no
				)
			});
			
			if(stateNos!=""){
				$.ligerDialog.error("以下单据没有审核，生成发票功能不能使用！：<br>"+ stateNos);
				return;
			}
			
			if(billNos!=""){
				$.ligerDialog.error("请先维护以下单据的发票号：<br>"+ billNos);
				return;
			}
			
			if(billDate!=""){
				$.ligerDialog.error("请先维护以下单据的发票日期：<br>"+ billDate);
				return;
			}
			
			$.ligerDialog.confirm('确定要生成发票吗?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("addMatBillByBillBatch.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
    function loadDict(){
		//字典下拉框
/* 		autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true); */
		autocomplete("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1});
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',220);
		
	    autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
	    autodate("#end_in_date", "yyyy-mm-dd", "month_last");

	    autoCompleteByData("#bill_state", [{id: "0", text: "货到票未到"}, {id: "1", text: "货票同到"}], "id", "text", true, true);

        $("#state").ligerTextBox({width:160});
        $("#begin_in_date").ligerTextBox({width:100});
        $("#end_in_date").ligerTextBox({width:100});
        $("#begin_check_date").ligerTextBox({width:100});
        $("#end_check_date").ligerTextBox({width:100});
        $("#deliver_no").ligerTextBox({width:160});
        $("#origin_no").ligerTextBox({width:160});
        $("#bill_no").ligerTextBox({width:160});
	} 
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
       <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">编制日期：</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            		</tr>
				</table>
	        </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
             <td align="left" class="l-table-edit-td">
            	<select name="state" id="state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="1">未审核</option>
                		<option value="2">审核</option>
            	</select>
            </td>
            
       </tr>
       <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_check_date" id="begin_check_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_check_date" id="end_check_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
           				</td>
            		</tr>
				</table>
	        </td> 
            <td align="left"></td>
           	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">原始单号：</td>
            <td align="left" class="l-table-edit-td"><input name="origin_no" type="text" id="origin_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
           	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据号：</td>
            <td align="left" class="l-table-edit-td" ><input name="deliver_no" type="text" id="deliver_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,}" /></td>
            <td align="left"></td>

            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input type="text" id="bill_no" /></td>
            <td align="left"></td>

            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票状态：</td>
            <td align="left" class="l-table-edit-td"><input type="text" id="bill_state" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
