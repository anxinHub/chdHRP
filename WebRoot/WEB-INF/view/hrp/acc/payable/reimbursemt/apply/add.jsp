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
		
		$("#dept_id").bind("change", function() {
			grid.columns[3].editor.url = '../../../queryBudgPaymentItemDictDept.do?isCheck=false&dept_id='+liger.get("dept_id").getValue().split(".")[0];
		});

	});
	
	//保存并提交数据
	function commit(){
		$.ligerDialog.confirm('是否保存并提交数据?', function (yes){
			var flag = 1;//保存并提交数据 标识   flag=1,保存并提交
			save(flag);
		})
	}

	//撤销 revoke
	function revoke(){
		$.ligerDialog.confirm('是否确认撤销?', function (yes){
			//撤销
		})
	}
	//print
	function print(){

		//print

	}
	
	
	function save(flag) {
		gridManager.endEdit();
		if(liger.get("dept_id").getValue() == ""){
			$.ligerDialog.error('科室不能为空');
			return;
		}
		if(liger.get("emp_id").getValue() == ""){
			$.ligerDialog.error('报销人不能为空');
			return;
		}
		if($("#remark").val() == ""){
			$.ligerDialog.error('申请事由不能为空');
			return;
		}
		if($("#apply_date").val() == ""){
			$.ligerDialog.error('申请日期不能为空');
			return;
		}
		if($("#card_no").val() == ""){
			$.ligerDialog.error('银行账号不能为空');
			return;
		}
		var data = gridManager.getData();
		if(JSON.stringify(data) == '[{}]'){
  			$.ligerDialog.error('明细数据不能为空');
  			return;
  		}
		//20180321  东阳需求 如果不是自筹资金则必须填写项目   gaopei
		if(data[0].source_id_no.split(".")[0]!="001"){
			if(liger.get("proj_id").getValue()==""){
				$.ligerDialog.error('项目不能为空');
				return;
			}
		}
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
				//payment_amount : $("#payment_amount").val(),
				card_no : $("#card_no").val(),
				phone : $("#phone").val(),
				start_date : $("#start_date").val(),
				end_date : $("#end_date").val(),
				address : $("#address").val(),
				use_apply_code : $("#use_apply_code").val(),
		        ParamVo : JSON.stringify(data)
		};
		
		if (validateGrid()) {  
			ajaxJsonObjectByUrl("addBudgPaymentApply.do", formPara,
					function(responseData) {
							if (responseData.state == "true") {
							$('#make_date').html('制单日期：'+$('#apply_date').val());
							$("#apply_code").val(responseData.apply_code);
							//$("#payment_amount").val(responseData.payment_amount);
							$("#pay_amount").val(responseData.pay_amount);
							parentFrameUse().query();
							$.ligerDialog.confirm('添加成功。是否继续添加单据?', function (yes){
		                		if(yes){
		                			$("#apply_code").val("");
		                			$("#phone").val("");
		                			$("#card_no").val("");
		                			$("#remark").val("");
		                			$("#start_date").val("");
		                			$("#end_date").val("");
		                			$("#address").val("");
		                			liger.get("emp_id").setValue("");
		                			liger.get("emp_id").setText("");
		                			autodate("#apply_date");
		                			liger.get("dept_id").setValue("");
		                			liger.get("dept_id").setText("");
		                			liger.get("proj_id").setValue(""); 
		                			liger.get("proj_id").setText("");
		                			loadDict();
		                            //grid.loadData();
		                            //is_addRow();
		                		}
		                	});
					}
				});
		}
	}
	

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'apply_code',
			value : $("#use_apply_code").val() == ""?"0":$("#use_apply_code").val()
		});
 		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资金来源',
										name : 'source_id_no',
										textField : 'source_name',
										align : 'left',
										width: '200',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../../../sys/querySourceDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
											}
										},
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>合计</div>';
					                        },
					                        align: 'center'
					                    }
									},
									{
										display : '支出项目',
										name : 'payment_item_id_no',
										textField : 'payment_item_name',
										align : 'left',
										width: '200',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
								           	selectBoxWidth: 400,
								           	selectBoxHeight: 260,
											url : '../../../queryBudgPaymentItemDictDept.do?isCheck=false',
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
											}
										}
									},
									/* {
										display : '用款申请金额',
										name : 'use_payment',
										align : 'left',
										width: '150',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render: function(item)
							            {
							                    return formatNumber(item.use_payment,2,1);
							            }
									}, */
									{
										display : '报销金额',
										name : 'payment_amount',
										width: '150',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render: function(item)
							            {
							                    return formatNumber(item.payment_amount,2,1);
							            }
										,
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					                        }
					                    }
									},
									{
										display : '实际支付金额',
										name : 'pay_amount',
										width: '150',
										align : 'right',
										editor : {
											type : 'text',
											precision : 2
										},
										render: function(item)
							            {
							                    return formatNumber(item.pay_amount,2,1);
							            },
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					                        }
					                    } 
									},
									{display : '单据张数', name : 'amount',align : 'left',width: '100',editor : {type : 'int'}},
									{
										display : '说明',
										name : 'remark',
										align : 'left',
										width: '300',
										editor : {
											type : 'text'
										}
									}],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryMoneyApplyDet.do?isCheck=false',//queryMoneyApplyDet.do?isCheck=false
							width : '100%',
							height : '90%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							//onAfterEdit : f_onAfterEdit,
							onAfterEdit: updatePayAmount,
							isScroll : true,
							rownumbers : true,
							delayLoad : true,//初始化明细数据
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '删除',
									id : 'delete',
									click : remove,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '保存',
									id : 'save',
									click : save,
									icon : 'save'
								}, {
									line : true
								}, {
									text : '提交',
									id : 'commit',
									click : commit,
									icon : 'ok'
								}, {
									line : true
								}, {
									text : '撤销',
									id : 'revoke',
									click : revoke,
									icon : 'ok'
								}, {
									line : true
								}, {
									text : '打印（P）',
									id : 'print',
									click : print,
									icon : 'ok'
								}, {
									line : true
								}, {
									text : '关闭（E）',
									id : 'close',
									click : this_close,
									icon : 'delete'
								} ]
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
	//自动更新实际支付金额
	function updatePayAmount(e) {
		if(e.column.columnname == 'payment_amount'){
			gridManager.updateCell('pay_amount', e.record.payment_amount, e.record); 
		}
	}
	
	// 跳转到下一个单元格之前事件
	//function f_onAfterEdit(e) {
	//	var total_num = this.totalRow2.text().replace("合计","").replace(",","");
	//	$("#payment_amount").val(total_num);
	//	return true;
	//}
	function validateGrid() {
		//明细
 		var msg="";
 		var rowm = "";
 		var rows = 0;
 		var data = gridManager.getData();
 		//alert(JSON.stringify(data));
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.source_id_no) {
				if (v.source_id_no == "" || v.source_id_no == null || v.source_id_no == 'undefined') {
					rowm+="[资金来源]、";
				}  
				if (v.payment_item_id_no == "" || v.payment_item_id_no == null || v.payment_item_id_no == 'undefined') {
					rowm+="[支出项目]、";
				}  
				if (v.payment_amount == "" || v.payment_amount == null || v.payment_amount == 'undefined' || parseFloat(v.payment_amount) < 0) {
					rowm+="[报销金额]、";
				}
				//if (v.pay_amount == "" || v.pay_amount == null || v.pay_amount == 'undefined' || parseFloat(v.pay_amount) < 0) {
				//	rowm+="[实际支付金额]、";
				//}
				if(rowm != ""){
					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空或不能为负数" + "\n\r";
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
	function remove() {

		grid.deleteSelectedRow();
	}
	
	function this_close(){
		frameElement.dialog.close();
	}
	
	function changeEmpKind(){
		
    	var dept_id = liger.get("dept_id").getValue().split(".")[0];
    	
    	var dept_no = liger.get("dept_id").getValue().split(".")[1];
    	
    	if(liger.get("dept_id").getValue() ==""){
    		
    		$("#dept_id").val("");
    		
    		return;
    		
    	}
    	var para = {dept_id:dept_id,dept_no:dept_no};
    	
    	$("#emp_id").val("");
    	
    	autocomplete("#emp_id","../../../queryEmpDict.do?isCheck=false", "id", "text", true, true, para);

    }
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', save);
		hotkeys('D', remove);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow({
				source_name: '001 自筹资金',
				source_id_no:'001.1'
			});
		}, 1000); 
	}
	function loadDict() {
		
		$("#dept_id").ligerComboBox({
          	url: "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 400,
           	selectBoxHeight: 260,
          	autocomplete: true,
          	width: 160 ,
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
          		//grid.columns[4].editor.grid.url = '../../../queryBudgPaymentItemDictDept.do?isCheck=false&dept_id=';
          		
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
          		$("#card_no").val("");
          		var emp_id = v.split(".")[0];
				var emp_no = v.split(".")[1];
				if(emp_id!=""&&emp_no!=""){
					//自动带出银行账号 下拉框					
			    	var para = {emp_id:emp_id,emp_no:emp_no};
			    	autocomplete("#card_no","queryGetEmpCardNoSelect.do?isCheck=false", "id", "text", true, true, para,true);
			    	autocomplete("#use_apply_code","queryUseApplyCode.do?isCheck=false", "id", "text", true, true, para);
					/* $.post("queryGetEmpCardNo.do?isCheck=false",{emp_id:emp_id,emp_no:emp_no},function(data){
						$("#card_no").val(data.accEmpAccount.account_code);
					},"json"); */
			    	autocomplete("#dept_id","../../../../sys/queryDeptDict.do?isCheck=false&is_last=1&emp_id="+emp_id, "id", "text", true, true, '',true);
				}
          	}
 		 });
		

		$("#card_no").ligerComboBox({
          	url: "queryGetEmpCardNoSelect.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 200,
           	autocomplete: false,
          	width: 160
 		 });

		
		$("#use_apply_code").ligerComboBox({
          	url: "queryUseApplyCode.do?isCheck=false",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(v){
          		query();
          		/* //自动带出申请科室与项目名称	
          		$.post("queryAutoDeptPro.do?isCheck=false",{emp_id:emp_id,emp_no:emp_no},function(data){
					$("#card_no").val(data.accEmpAccount.account_code);
				},"json");*/
          	}
 		 });
		
    	autocomplete("#proj_id", "../../../../sys/queryProjDictDictDet.do?isCheck=false", "id","text", true, true,null,false,'','','','500'); 
    	
    	
    	//$("#payment_amount").val(0);
		//$("#payment_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#apply_code").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#phone").ligerTextBox({width : 160});
		
		$("#card_no").ligerTextBox({/* disabled:true, */cancelable: false,width : 160});
		
		$("#apply_date").ligerTextBox({width : 160});
		autodate("#apply_date");
		
		$("#start_date").ligerTextBox({width : 160});
		
		$("#end_date").ligerTextBox({width : 160});
		
		$("#address").ligerTextBox({width : 420});
    	
		
		var emp_id = '${emp_id}';
		var emp_text = '${emp_text}';
		var dept_id = '${dept_id}';
		var dept_text = '${dept_text}';
		liger.get("dept_id").setValue(dept_id);
        liger.get("dept_id").setText(dept_text);
		liger.get("emp_id").setValue(emp_id);
        liger.get("emp_id").setText(emp_text);
        //liger.get("dept_id").setDisabled(true);
		
     	$('#maker').html('制单人：'+ emp_text);
     	$('#make_date').html('制单日期：${make_date}');
    	$('#YS_leader').html('预算归口负责人：  ');
    	$('#ToExamine').html('审核日期：${check_date}');
    	/*var state = ${state_t};
   		if(state == '01'){
   			state = '新建';
   		}else if(state == '02'){
   			state = '已提交';
   		}else if(state == '03'){
   			state = '已审核';
   		}*/
    	$('#state').html('状态：新建'); 
		
		
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>报销申请单号：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_code" disabled="disabled"
				type="text" id="apply_code"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" 
				type="text" id="dept_id"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>申请日期：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_date" 
				type="text" id="apply_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>报销人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="emp_id" type="text" id="emp_id" 
				 /></td>
			<td align="left"></td>
		</tr>	
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">联系电话：</td>
			<td align="left" class="l-table-edit-td"><input name="phone" 
				type="text" id="phone"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red">*</font>银行帐号：</td>
			<td align="left" class="l-table-edit-td"><input name="card_no" 
				type="text" id="card_no"  
				 /></td>
			<td align="left"></td>
			<!-- td align="right" class="l-table-edit-td" style="padding-left: 20px;">报销金额：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_amount" disabled="disabled" type="text" id="payment_amount" /></td>
			<td align="left"></td-->
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">用款申请：</td>
			<td align="left" class="l-table-edit-td"><input name="use_apply_code" type="text" id="use_apply_code"/></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">出差开始日期：</td>
			<td align="left" class="l-table-edit-td"><input name="start_date" 
				type="text" id="start_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">出差结束日期：</td>
			<td align="left" class="l-table-edit-td"><input name="end_date" 
				type="text" id="end_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"  
				 /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">出差起讫地址：</td>
			<td align="left" class="l-table-edit-td" colspan="4"><input name="address" type="text" id="address" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red">*</font>报销事由：</td>
			<td align="left" class="l-table-edit-td" colspan="4">
				<textarea rows="3" cols="70" id="remark" name="remark"></textarea>
			</td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
    <table class="" width="100%"  >
    	<tr>
    		<td width="20%" id = 'maker'></td>
    		<td width="20%" id = 'make_date' ></td>
    		<td width="20%" id = 'YS_leader'></td>
    		<td width="20%" id = 'ToExamine'></td>
    		<td width="20%" id = 'state'></td>
    	</tr>
    </table>
</body>
</html>
