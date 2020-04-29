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

	function save() {
		gridManager.endEdit();
		if(liger.get("dept_id").getValue() == ""){
			$.ligerDialog.error('科室不能为空');
			return;
		}
		if(liger.get("emp_id").getValue() == ""){
			$.ligerDialog.error('借款人不能为空');
			return;
		}
		if($("#return_date").val() == ""){
			$.ligerDialog.error('退还日期不能为空');
			return;
		}
		var pay_way = liger.get("pay_way").getValue();
		if(pay_way == "" || pay_way == null){
			$.ligerDialog.error("请选择支付方式！");
			return;
		}
		var data = gridManager.getData();
		var formPara = {
				return_code : $("#return_code").val() == ""?0:$("#return_code").val(),
				return_date : $("#return_date").val(),
				dept_id : liger.get("dept_id").getValue().split(".")[0],
				dept_no : liger.get("dept_id").getValue().split(".")[1],
				emp_id : liger.get("emp_id").getValue().split(".")[0],
				emp_no : liger.get("emp_id").getValue().split(".")[1],
				proj_id : liger.get("proj_id").getValue().split(".")[0],
				proj_no : liger.get("proj_id").getValue().split(".")[1],
				return_amount:'0',
				pay_way:pay_way,
		        ParamVo : JSON.stringify(data)
		};
		
		if (validateGrid()) {  
				$.post("addBudgBorrReturn.do", formPara,
					function(responseData) {
							if (responseData.state == "true") {
							$("#return_code").val(responseData.return_code);
							$("#return_amount").val(responseData.return_amount);
							parentFrameUse().query();
							$.ligerDialog.confirm('添加成功。是否继续添加单据?', function (yes){
		                		if(yes){
		                			$("#return_code").val("");
		                			loadDict();
		                            grid.loadData();
		                		}else{
		                			//this_close();
		                		}
		                	}); 
					}
				},"json");
		}
	}
	

	function query(dept_id,emp_id,proj_id) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'return_code',
			value : $("#return_code").val() == ""?"":$("#return_code").val()
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : dept_id
		});
		grid.options.parms.push({
			name : 'emp_id',
			value : emp_id
		});
		grid.options.parms.push({
			name : 'proj_id',
			value : proj_id
		});
 		grid.loadData(grid.where);
	}

	function onAfterShowData(){
 		$("#return_amount").val($("#summary").text());
		$("#return_amount_heid").val($("#summary").text());
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
			{columns : [
				{display : '资金来源',name : 'source_id_no',textField : 'source_name',align : 'left',width: '230',
					totalSummary:{
					   render: function (suminf, column, cell){return '<div>合计</div>';}, align: 'center'}
				},
				{display : '支出项目',name : 'payment_item_id_no',textField : 'payment_item_name',align : 'left',width: '230'},
				{display : '退还金额',name : 'return_amount',align : 'left',width: '230',
					editor : {type : 'numberbox',precision : 2},align : 'right',
					totalSummary:{
					    render: function (suminf, column, cell){
					       return '<div id="summary">' + formatNumber(suminf.sum,2,1) + '</div>';
					    }
					} ,
					render: function(item){
						return formatNumber(item.return_amount,2,1);
					}
				}],
				dataAction : 'server',
				dataType : 'server',
				usePager : false,
				url : 'queryReturnDetForUpdate.do?isCheck=false',
				width : '100%',
				height : '90%',
				checkbox : false,
				enabledEdit : true,
				alternatingRow : true,
				onBeforeEdit : f_onBeforeEdit,
				onBeforeSubmitEdit : f_onBeforeSubmitEdit,
				onAfterEdit : f_onAfterEdit,
				onAfterShowData :onAfterShowData,
				isScroll : true,
				rownumbers : true,
				delayLoad : true,//初始化明细数据
				selectRowButtonOnly : true,//heightDiff: -10,
				toolbar : {
					items : [ 
						{text : '保存',id : 'save',click : save,icon : 'save'},
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
		var total_num = this.totalRow2.text().replace("合计","").replace(",","");
		$("#return_amount").val(total_num);
		return true;
	}
	function validateGrid() {
		//明细
 		var msg="";
 		var rowm = "";
 		var rows = 0;
 		var data = gridManager.getData();
 		//alert(JSON.stringify(data));
 		//判断grid 中的数据是否重复或者为空
 		var total_return_amount = 0;
 		//判断还款金额是否为空
 		var money = 0;
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.source_id_no) {
				/* if (v.return_amount == '' || v.return_amount == null || v.return_amount == 'undefined') {
					rowm+="[退还金额不能为空]、";
				} */
				money += v.return_amount;
				//alert(v.this_return_amount);
				if (parseFloat(v.return_amount) > parseFloat(v.remain_amount)) {
					rowm+="[退还金额不能大于借款余额]\n\r";
				} 
				if (parseFloat(v.remain_amount) == 0) {
					rowm+= "[没有可以退还的借款余额]\n\r";
				}
				
				if (parseFloat(v.return_amount) == 0) {
					rowm+= "[没有可以退还的借款余额]\n\r";
				}
				total_return_amount = parseFloat(total_return_amount) + parseFloat(v.return_amount);
				if(rowm != ""){
					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "" + "\n\r";
				}
				msg += rowm;
				var key=v.source_id_no+"|"+v.payment_item_id_no;
				var value="第"+(i+1)+"行";
				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
					targetMap.put(key ,value);
				}else{
					msg += targetMap.get(key)+"与"+value+"不能重复" + "\n\r";
				}
				rows += 1;
				
			}  
 		});
 		if(money <= 0){
 			$.ligerDialog.warn("退还金额不能为空！");  
			return false;
 		}
 		if(parseFloat($("#return_amount_heid").val()) == 0){
 			$.ligerDialog.warn("没有可退还的金额！");  
			return false;
 		}
 		if(total_return_amount == 0){
 			$.ligerDialog.warn("没有可退还的金额！");  
			return false;  
 		}
 		if(rows == 0){
 			$.ligerDialog.warn("请添加明细数据！");  
			return false;  
 		}
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		} 	 	
 		return true;	
	}
	
	
	function this_close(){
		frameElement.dialog.close();
	}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', save);

	}

	function loadDict() {
		$("#dept_id").ligerComboBox({
          	url: "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(value){
          		$("#return_amount").val("");
        		$("#return_amount_heid").val("");
          		$("#emp_id").val("")
          		var dept_id = value.split(".")[0];
				var dept_no = value.split(".")[1];
				$("#emp_id").ligerComboBox({
		          	url: "../../../queryEmpDict.do?isCheck=false&dept_id="+dept_id+"&dept_no="+dept_no,
		          	valueField: 'id',
		           	textField: 'text', 
		           	selectBoxWidth: 160,
		          	autocomplete: true,
		          	width: 160,
		          	onSelected:function(value){
		          		$("#return_amount").val("");
		        		$("#return_amount_heid").val("");
		          		var proj_id = liger.get("proj_id").getValue().split(".")[0];
		          		var emp_id = value.split(".")[0];
		          		var param = {
		                		'proj_id':proj_id,
		                		'emp_id':emp_id,
		                		'dept_id':dept_id
		                	};
		                	/* $.post("getBorrowAmount.do?isCheck=false",param,function(data){
		                		if(data.result != null){
		                    		$("#return_amount").val(data.result.remain_amount);
		                    		$("#return_amount_heid").val(data.result.remain_amount);
		                		}else{
		                		}
		                	},"json"); */
		          		query(dept_id,emp_id,proj_id);
		          	}
		 		 });
          	}
 		 });
		
		$("#proj_id").ligerComboBox({
          	url: "../../../../sys/queryProjDictDict.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected :function(value){
          		$("#return_amount").val("");
        		$("#return_amount_heid").val("");
          		var dept_id = liger.get("dept_id").getValue().split(".")[0];
          		var emp_id = liger.get("emp_id").getValue().split(".")[0];
          		if(dept_id == ""){
        			$.ligerDialog.error('科室不能为空');
        			return;
        		}
        		if(emp_id == ""){
        			$.ligerDialog.error('借款人不能为空');
        			return;
        		}
          		var proj_id = value.split(".")[0];
          		var param = {
                		'proj_id':proj_id,
                		'emp_id':emp_id,
                		'dept_id':dept_id
                	};
                	/* $.post("getBorrowAmount.do?isCheck=false",param,function(data){
                		if(data.result != null){
                    		$("#return_amount").val(data.result.remain_amount);
                    		$("#return_amount_heid").val(data.result.remain_amount);
                		}else{
                		}
                	},"json"); */
          		query(dept_id,emp_id,proj_id);
          	}
 		 });
    	
    	$("#return_amount").val(0);
		$("#return_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#return_code").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#return_date").ligerTextBox({width : 160});
		autodate("#return_date");
		autocomplete("#pay_way","../../../queryPayType.do?isCheck=false", "id","text", true, true,null,false);
		$("#emp_id").ligerComboBox({
			width : 160
		});
    	
	}
</script>
 
</head>

<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="return_amount_heid" name="return_amount_heid"/>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>单号：</td>
			<td align="left" class="l-table-edit-td"><input name="return_code" disabled="disabled"
				type="text" id="return_code"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" 
				type="text" id="dept_id" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>退还日期：</td>
			<td align="left" class="l-table-edit-td"><input name="return_date" 
				type="text" id="return_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"  
				 /></td>
			<td align="left"></td>
		</tr>	
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>借款人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="emp_id" type="text" id="emp_id" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">退还金额：</td>
			<td align="left" class="l-table-edit-td"><input name="return_amount" disabled="disabled"
				type="text" id="return_amount" 
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>支付方式：</td>
			<td align="left" class="l-table-edit-td"><input name="pay_way" 
				type="text" id="pay_way" 
				 /></td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
	
</body>
</html>
