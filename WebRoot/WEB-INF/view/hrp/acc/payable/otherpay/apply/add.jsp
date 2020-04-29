<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">


	var dataFormat;
	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();
		
	});
	
	//保存并提交数据
	function commit(){
		$.ligerDialog.confirm('是否保存并提交数据?', function (yes){
			var flag = 1;//保存并提交数据 标识   flag=1,保存并提交
			save(flag);
		})
	}
	
	//保存
	function save(flag) {
		gridManager.endEdit();
		if(liger.get("dept_id").getValue() == ""){
			$.ligerDialog.warn('科室不能为空');
			return;
		}
		if(liger.get("emp_id").getValue() == ""){
			$.ligerDialog.warn('申请人不能为空');
			return;
		}
		if($("#remark").val() == ""){
			$.ligerDialog.warn('申请事由不能为空');
			return;
		}
		if($("#apply_date").val() == ""){
			$.ligerDialog.warn('申请日期不能为空');
			return;
		}
		
		if(liger.get("payment_code").getValue()== ""){
			$.ligerDialog.warn('支出项目不能为空');
			return;
		}
		
		if(liger.get("source_code").getValue()== ""){
			$.ligerDialog.warn('资金来源不能为空');
			return;
		}
		
		var data = gridManager.getData();
		if(JSON.stringify(data) == '[{}]'){
  			$.ligerDialog.warn('明细数据不能为空');
  			return;
  		}
		//20180321  东阳需求 如果不是自筹资金则必须填写项目   gaopei
		/* if(data[0].source_id_no.split(".")[0]!="001"){
			if(liger.get("proj_id").getValue()==""){
				$.ligerDialog.error('项目不能为空');
				return;
			} 
		} */
		var formPara = {
			flag : flag == 1 ? 1 : 0,
			apply_code : $("#apply_code").val() == ""?0:$("#apply_code").val(),
			apply_date : $("#apply_date").val(),
			dept_id : liger.get("dept_id").getValue().split(".")[0],
			dept_no : liger.get("dept_id").getValue().split(".")[1],
			emp_id : liger.get("emp_id").getValue().split(".")[0],
			emp_no : liger.get("emp_id").getValue().split(".")[1],
			proj_id : liger.get("proj_id").getValue().split(".")[0],
			proj_no : liger.get("proj_id").getValue().split(".")[1],
			remark : $("#remark").val(),
			source_id : liger.get("source_code").getValue().split(".")[1],
			payment_amount : $("#payment_amount").val().replace(/,/gi,''),
			payment_item_id : liger.get("payment_code").getValue().split("@")[0],
			payment_item_no : liger.get("payment_code").getValue().split("@")[0],
			use_apply_code : liger.get("use_apply_code").getValue(),
	        ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {  
			ajaxJsonObjectByUrl("addBudgChargeApply.do", formPara,function(responseData) {
				if (responseData.state == "true") {
					$("#apply_code").val(responseData.apply_code);
					$("#payment_amount").val(parseFloat(responseData.payment_amount).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,'$&,'));
					parentFrameUse().query();
					$.ligerDialog.confirm('添加成功。是否继续添加单据?', function (yes){
                		if(yes){
                			$("#apply_code").val("");
                			$("#phone").val("");
                			$("#card_no").val("");
                			$("#proj_id").val("");
                			$("#unit_name").val(""); 
                			$("#bank_location").val("");
                			$("#bank_name").val("");
                			$("#remark").val("");
                			liger.get("emp_id").setValue(""); 
                			liger.get("emp_id").setText("");
                			autodate("#apply_date");
                			liger.get("dept_id").setValue("");
                			liger.get("dept_id").setText("");
                			liger.get("proj_id").setValue("");
                			liger.get("proj_id").setText("");
                			liger.get("use_apply_code").setValue("");
                			liger.get("use_apply_code").setText("");
                			loadDict();
                            grid.loadData();
                            //is_addRow();
                		}else{
                			//this_close();
                		}
                	});
				}
			});
		}
	}
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'apply_code',value : $("#apply_code").val() == ""?"0":$("#apply_code").val()});
 		grid.loadData(grid.where);
	}
	
	
	//加载grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{display : '收款单位/个人',name : 'unit_id',textField : 'unit_name',align : 'left',width: '200',
					editor : {type : 'select',valueField : 'unit_id',textField : 'unit_name',
					selectBoxWidth: '80%',selectBoxHeight: 240,
					grid: {
						columns: [
						{
							display: '收款单位/个人', name: 'unit_name', width: 240, align: 'left'
						},
						  {display : '开户行',name : 'bank_name',align : 'left',width: '200'
						},
						  {display : '开户行所在地',name : 'bank_location',align : 'left',width: '200'
						},
						  {display : '银行账号',name : 'card_no',align : 'left',width: '200'
						}],
						switchPageSizeApplyComboBox: false,
						onSelectRow: function (data) {
							var e = window.event;
							if (e && e.which == 1) {
								f_onSelectRow_detail(data);
							}
						},
						url: 'queryBudgUnit.do?isCheck=false',pageSize: 500
						
					},
					delayLoad: true, keySupport: true, autocomplete: true ,
					onSuccess: function (data, grid) {
						this.parent("tr").next(".l-grid-row").find("td:first").focus();
					},
					ontextBoxKeyEnter: function (data) {
						f_onSelectRow_detail(data.rowdata);
					}
				  }
				},
				
				{display : '开户行',name : 'bank_name',align : 'left',width: '200',/* editor : {type : 'text'} */
					editor :{
 	 					type : 'select',
 						valueField : 'text',
 						textField : 'text',
 						selectBoxWidth : 500,
 						selectBoxHeight : 300,
 						url: '../../../../acc/internetbank/queryAccICBCIBPSCode.do?isCheck=false',
 						width: '98%', height: '98%', 
 						keySupport : true,
 						autocomplete : true,
 						onSuccess: function (data, grid) {
 							this.parent("tr").next(".l-grid-row").find("td:first").focus();
 						},
 						ontextBoxKeyEnter: function (data) {
 							f_onSelectRow_detail(data.rowdata);
 						}
 						
 	 				}
				},
				{display : '开户行所在地',name : 'bank_location',align : 'left',width: '200',/* editor : {type : 'text'} */
					editor :{
	 	 					type : 'select',
	 						valueField : 'text',
	 						textField : 'text',
	 						selectBoxWidth : 300,
	 						selectBoxHeight : 200,
	 						url: '../../../../acc/internetbank/queryAccBankNetICBCCode.do?isCheck=false',
	 						width: '98%', height: '98%', 
	 						keySupport : true,
	 						autocomplete : true,
	 					}
				},
				{display : '银行账号',name : 'card_no',align : 'left',width: '200',editor : {type : 'text'}
				},
				{display : '付款金额',name : 'payment_amount',width: '150',editor : {type : 'number',precision : 2},align : 'right',
					render: function(item){
						return formatNumber(item.payment_amount,2,1);
					},totalSummary:{render: 
						function (suminf, column, cell){
					   		return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					    }
					} 
				},
				{display : '说明',name : 'remark',align : 'left',width: '300',editor : {type : 'text'}},
				//{display : '支付状态',name : 'pay_state',align : 'left',width: '50'}
			],
			dataAction : 'server',dataType : 'server',usePager : false,width : '100%',height : '90%',checkbox : true,
			enabledEdit : true,alternatingRow : true,onBeforeEdit : f_onBeforeEdit,onBeforeSubmitEdit : f_onBeforeSubmitEdit,
			onAfterEdit : f_onAfterEdit,isScroll : true,rownumbers : true,delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,toolbar : {
				items : [ 
					{text : '保存',id : 'save',click : save,icon : 'save'}, 
					{line : true}, 
					{text : '提交',id : 'commit',click : commit,icon : 'ok'},
					{line : true}, 
					{text : '删除',id : 'delete',click : remove,icon : 'delete'}, 
					{line : true}, 
					{text : '关闭',id : 'close',click : this_close,icon : 'delete'} 
				]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		var total_num = this.totalRow2.text().replace("合计","").replace(/,/g,"");
		$("#payment_amount").val(parseFloat(total_num).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,'$&,'));
		return true;
	}
	
	
	function validateGrid() {
		//明细
 		var msg="";
 		var rowm = "";
 		var rows = 0;
 		var data = gridManager.getData();
 		
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.unit_id) {
				if (v.unit_id == "" || v.unit_id == null || v.unit_id == 'undefined') {
					rowm+="[收款单位/个人]、";
				}  
				if (v.bank_name == "" || v.bank_name == null || v.bank_name == 'undefined') {
					rowm+="[开户行]、";
				}
				if (v.bank_location == "" || v.bank_location == null || v.bank_location == 'undefined') {
					rowm+="[开户行所在地]、";
				} 
				if (v.card_no == "" || v.card_no == null || v.card_no == 'undefined') {
					rowm+="[开户行所在地]、";
				} 
				if (v.payment_amount == "" || v.payment_amount == null || v.payment_amount == 'undefined' || parseFloat(v.payment_amount) < 0) {
					rowm+="[付款金额]、";
				}
				if(rowm != ""){
					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空或不能为负数" + "\n\r";
				}
				msg += rowm;
				var key=v.unit_id+"|"+v.card_no;
				var value="第"+(i+1)+"行";
				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
					targetMap.put(key ,value);
				}else{
					msg += targetMap.get(key)+"与"+value+"不能重复" + "\n\r";
				}
				rows += 1;
			}  
 		});
 		/* if(rows == 0){
 			$.ligerDialog.warn("请添加明细数据！");  
			return false;  
 		} */
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		} 	 	
 		return true;	
	}
	
	//删除行
	function remove() {

		grid.deleteSelectedRow();
	}
	
	//关闭窗口
	function this_close(){
		frameElement.dialog.close();
	}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', save);
		hotkeys('D', remove);

	}
	
	//添加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	
	//加载字典
	function loadDict() {
		
		$("#dept_id").ligerComboBox({
          	url: "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1",
          	valueField: 'id',textField: 'text', 
           	selectBoxWidth: 400,
           	selectBoxHeight: 260,
          	autocomplete: true,width: 160,
          	onSelected:function(value){

          		//var dept_id = value.split(".")[0];
				//var dept_no = value.split(".")[1];
          		//$("#emp_id").ligerComboBox({
                //  	url: "../../../queryEmpDict.do?isCheck=false&dept_id="+dept_id,
                //  	valueField: 'id',textField: 'text',selectBoxWidth: 160,
                //  	autocomplete: true,width: 160,
         		//});
          		//liger.get("emp_id").setValue("");
          		//liger.get("emp_id").setText(""); 
          		
        		$("#payment_code").ligerComboBox({url: "../../../queryBudgPaymentItemDictDept.do?isCheck=false&dept_id="+liger.get("dept_id").getValue().split(".")[0]});
          	}
 		});
		
		$("#emp_id").ligerComboBox({
          	url: "../../../../sys/queryEmpDictDept.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 300,
           	selectBoxHeight: 260,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(v){
          		var emp_id = v.split(".")[0];
				var emp_no = v.split(".")[1];
				if(emp_id!=""&&emp_no!=""){
			    	autocomplete("#dept_id","../../../../sys/queryDeptDict.do?isCheck=false&is_last=1&emp_id="+emp_id, "id", "text", true, true, '',true);
				}
          	}
 		 });
		autocomplete("#payee_person", "../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false); 
		autocomplete("#payee_unit", "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1", "id","text", true, true,null,false); 
    	//autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	
    	$("#payment_code").ligerComboBox({
                  	url: "",
                  	valueField: 'id',
                   	textField: 'text', 
                   	selectBoxWidth: 500,
                   	selectBoxHeight: 260,
                  	autocomplete: true,
                  	width: 160,
                  	onSelected:function(v){ //hrp/budg/base/budgMoneyApply/Apply/MoneyApplyUpdatePage
                  		var use_apply_code = liger.get("use_apply_code").getValue();
                  		var proj_id = liger.get("proj_id").getValue().split(".")[0];
                  		var source_id = liger.get("source_code").getValue().split(".")[1];
                  		var payment_item_id = liger.get("payment_code").getValue().split("@")[0];
                  		if(use_apply_code!='' && proj_id != '' && source_id != '' && payment_item_id != ''){
                  			var para = {
            	      				use_apply_code : liger.get("use_apply_code").getValue(),
            	      				proj_id : liger.get("proj_id").getValue().split(".")[0],
            	      				source_id : liger.get("source_code").getValue().split(".")[1],
            	      				payment_item_id : liger.get("payment_code").getValue().split("@")[0]
            	          	};
                      		
                      		//用款申请单号、项目、资金来源、支出项目带出,用款申请额度(use_apply_money)
                      		$.post("queryMoneyApplyDet.do?isCheck=false",para,function(data){
                      			//console.info(data);
            					$('#use_apply_money').val(data.apply_amount==''?0:data.apply_amount);
            				},"json");
                  		}

                  	}
         		 });
    	
    	$("#payment_amount").val(0);
		$("#payment_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#apply_code").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#apply_date").ligerTextBox({width : 160});
		
		autodate("#apply_date");
    	
		//autocomplete("#source_code", "../../../../sys/querySourceDict.do?isCheck=false", "id","text", true, true,null,false); 
		//autocomplete("#payment_code", "../../../queryBudgPaymentItemDict.do?isCheck=false&is_write=1", "id","text", true, true,null,false); 
    	

		$("#source_code").ligerComboBox({
          	url: "../../../../sys/querySourceDict.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(v){ //hrp/budg/base/budgMoneyApply/Apply/MoneyApplyUpdatePage
          		var use_apply_code = liger.get("use_apply_code").getValue();
          		var proj_id = liger.get("proj_id").getValue().split(".")[0];
          		var source_id = liger.get("source_code").getValue().split(".")[1];
          		var payment_item_id = liger.get("payment_code").getValue().split("@")[0];
          		if(use_apply_code!='' && proj_id != '' && source_id != '' && payment_item_id != ''){
          			var para = {
    	      				use_apply_code : liger.get("use_apply_code").getValue(),
    	      				proj_id : liger.get("proj_id").getValue().split(".")[0],
    	      				source_id : liger.get("source_code").getValue().split(".")[1],
    	      				payment_item_id : liger.get("payment_code").getValue().split("@")[0]
    	          	};
              		
              		//用款申请单号、项目、资金来源、支出项目带出,用款申请额度(use_apply_money)
              		$.post("queryMoneyApplyDet.do?isCheck=false",para,function(data){
              			//console.info(data);
    					$('#use_apply_money').val(data.apply_amount==''?0:data.apply_amount);
    				},"json");
          		}
          		
	          	

          	}
 		 });
		
		
		
		
		$("#proj_id").ligerComboBox({
          	url: "../../../../sys/queryProjDictDictDet.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 500,
           	selectBoxHeight: 260,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(v){ //hrp/budg/base/budgMoneyApply/Apply/MoneyApplyUpdatePage
          		var use_apply_code = liger.get("use_apply_code").getValue();
          		var proj_id = liger.get("proj_id").getValue().split(".")[0];
          		var source_id = liger.get("source_code").getValue().split(".")[1];
          		var payment_item_id = liger.get("payment_code").getValue().split("@")[0];
          		if(use_apply_code!='' && proj_id != '' && source_id != '' && payment_item_id != ''){
          			var para = {
    	      				use_apply_code : liger.get("use_apply_code").getValue(),
    	      				proj_id : liger.get("proj_id").getValue().split(".")[0],
    	      				source_id : liger.get("source_code").getValue().split(".")[1],
    	      				payment_item_id : liger.get("payment_code").getValue().split("@")[0]
    	          	};
              		
              		//用款申请单号、项目、资金来源、支出项目带出,用款申请额度(use_apply_money)
              		$.post("queryMoneyApplyDet.do?isCheck=false",para,function(data){
              			//console.info(data);
    					$('#use_apply_money').val(data.apply_amount==''?0:data.apply_amount);
    				},"json");
          		}
          		
	          	

          	}
 		 });
		
		
		
		$("#use_apply_code").ligerComboBox({
          	url: "../../reimbursemt/apply/queryUseApplyCode.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(v){ //hrp/budg/base/budgMoneyApply/Apply/MoneyApplyUpdatePage
          		var use_apply_code = liger.get("use_apply_code").getValue();
          		var proj_id = liger.get("proj_id").getValue().split(".")[0];
          		var source_id = liger.get("source_code").getValue().split(".")[1];
          		var payment_item_id = liger.get("payment_code").getValue().split("@")[0];
          		if(use_apply_code!='' && proj_id != '' && source_id != '' && payment_item_id != ''){
          			var para = {
    	      				use_apply_code : liger.get("use_apply_code").getValue(),
    	      				proj_id : liger.get("proj_id").getValue().split(".")[0],
    	      				source_id : liger.get("source_code").getValue().split(".")[1],
    	      				payment_item_id : liger.get("payment_code").getValue().split("@")[0]
    	          	};
              		
              		//用款申请单号、项目、资金来源、支出项目带出,用款申请额度(use_apply_money)
              		$.post("queryMoneyApplyDet.do?isCheck=false",para,function(data){
              			//console.info(data);
    					$('#use_apply_money').val(data.apply_amount==''?0:data.apply_amount);
    				},"json");
          		}
          		
	          	

          	}
 		 });
	}
	
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//回充数据 
		if (selectData != "" || selectData != null) {
				grid.updateRow(rowindex_id, {
					unit_name: data.unit_id,
					bank_name: data.bank_name,
					bank_location: data.bank_location,
					card_no: data.card_no == null ? "" : data.card_no
				});
				setTimeout(function (){
					grid.endEditToNext();
				},300)
		}
		//手动移除grid IE下只能移除......   隐藏不生效
		// $('.l-box-select-lookup').remove();
		return true;
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>费用申请单号：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_code" disabled="disabled"
				type="text" id="apply_code"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>申请日期：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_date" 
				type="text" id="apply_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" 
				type="text" id="dept_id"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>经办人：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" /></td>
			<td align="left"></td>
		</tr>	
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" 
				 /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red">*</font>资金来源：</td>
			<td align="left" class="l-table-edit-td"><input name="source_code"  type="text" id="source_code"  /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>支出项目：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_code" type="text" id="payment_code"  /></td>
			<td align="left"></td> 
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款金额：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_amount" type="text" id="payment_amount" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>申请事由：</td>
			<td align="left" class="l-table-edit-td" colspan="4">
				<textarea rows="3" cols="70" id="remark" name="remark"></textarea>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">用款申请：</td>
			<td align="left" class="l-table-edit-td"><input name="use_apply_code" 
				type="text" id="use_apply_code"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">用款申请额度：</td>
			<td align="left" class="l-table-edit-td"><input name="use_apply_money" disabled="disabled" value="0"
				type="text" id="use_apply_money"  
				 /></td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
	
</body>
</html>
