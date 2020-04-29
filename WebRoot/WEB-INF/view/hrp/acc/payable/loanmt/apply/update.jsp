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
		if('${state}'!= '01'){//置灰不能点击
        	toobarmanage=gridManager.toolbarManager;
        	toobarmanage.setDisabled('save');
        	toobarmanage.setDisabled('delete');
        }
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
		if($("#remark").val() == ""){
			$.ligerDialog.error('申请事由不能为空');
			return;
		}
		if($("#apply_date").val() == ""){
			$.ligerDialog.error('申请日期不能为空');
			return;
		}
		var data = gridManager.getData();
		var formPara = {
				apply_code : $("#apply_code").val() == ""?0:$("#apply_code").val(),
				apply_date : $("#apply_date").val(),
				dept_id : liger.get("dept_id").getValue().split(".")[0],
				dept_no : liger.get("dept_id").getValue().split(".")[1],
				emp_id : liger.get("emp_id").getValue().split(".")[0],
				emp_no : liger.get("emp_id").getValue().split(".")[1],
				proj_id : liger.get("proj_id").getValue().split(".")[0],
				proj_no : liger.get("proj_id").getValue().split(".")[1],
				remark : $("#remark").val(),
				borrow_amount : $("#borrow_amount").val(),
				card_no : $("#card_no").val(),
				phone : $("#phone").val(),
		        ParamVo : JSON.stringify(data)
		};
		
		if (validateGrid()) {  
				ajaxJsonObjectByUrl("addBudgBorrApply.do", formPara,
					function(responseData) {
							if (responseData.state == "true") {
							$("#apply_code").val(responseData.apply_code);
							$("#borrow_amount").val(responseData.borrow_amount);
							query();
							parentFrameUse().query();
							is_addRow();
					}
				});
		}
	}
	

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'apply_code',
			value : $("#apply_code").val() == ""?"0":$("#apply_code").val()
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
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
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
											url : '../../../queryBudgPaymentItemDict.do?isCheck=false&is_write=1',
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
											}
										}
									},
									{
										display : '借款金额',
										name : 'borrow_amount',
										width: '150',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render: function(item)
							            {
							                    return formatNumber(item.borrow_amount,2,1);
							            },
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					                        }
					                    } 
									},
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
							url : 'queryBudgBorrApplyDet.do?isCheck=false&apply_code=${apply_code}',
							width : '100%',
							height : '85%',
							checkbox : true,
							enabledEdit : '${state}'!='01'?false:true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							rownumbers : true,
							delayLoad : false,//初始化明细数据
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '保存',
									id : 'save',
									click : save,
									icon : 'save'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'delete',
									click : remove,
									icon : 'delete'
								} 
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
		$("#borrow_amount").val(total_num);
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
				if (v.borrow_amount == "" || v.borrow_amount == null || v.borrow_amount == 'undefined' || parseFloat(v.borrow_amount) < 0) {
					rowm+="[借款金额]、";
				}
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

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var i = 0;
			$(data).each(
					function() {
						if(isnull(this.group_id)){
							gridManager.deleteSelectedRow();
						}else{
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.apply_code + "@"
								+ this.source_id + "@" + this.payment_item_id);
						}
						i++;
					});
			if(ParamVo == ""){
				is_addRow();
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteBudgBorrApplyDet.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
							parentFrameUse().query();
							$("#borrow_amount").val(responseData.borrow_amount);
							is_addRow();
						}
					});
				}
			});
		}
	}
	
	 function updateSubmit(){
	    	var ParamVo = [];
								ParamVo.push('${group_id}'+ "@" + '${hos_id}' + "@"
										+ '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
				$.ligerDialog.confirm('确定提交?', function (yes){
	            	if(yes){
						ajaxJsonObjectByUrl("updateSubmit.do", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == 'true') {
								parentFrameUse().query();
								location.reload();
							}
						});
	            	}
				});
	    }
	    
		function updateWithdraw(){
			var ParamVo = [];
			ParamVo.push('${group_id}'+ "@" + '${hos_id}' + "@"
					+ '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
				$.ligerDialog.confirm('确定撤回?', function (yes){
	            	if(yes){
						ajaxJsonObjectByUrl("updateWithdraw.do", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								parentFrameUse().query();
								location.reload();
							}
						});
	            	}
				});
	    }
		
		function this_close(){
			frameElement.dialog.close();
		}
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', save);
		hotkeys('D', remove);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
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
          		$("#emp_id").val("");
          		var dept_id = value.split(".")[0];
				var dept_no = value.split(".")[1];
				$("#emp_id").ligerComboBox({
		          	url: "../../../queryEmpDict.do?isCheck=false&dept_id="+dept_id+"&dept_no="+dept_no,
		          	valueField: 'id',
		           	textField: 'text', 
		           	selectBoxWidth: 160,
		          	autocomplete: true,
		          	width: 160,
		          	onSelected:function(v){
		          		$("#card_no").val("");
		          		var emp_id = v.split(".")[0];
						var emp_no = v.split(".")[1];
						$.post("queryGetEmpCardNo.do?isCheck=false",{emp_id:emp_id,emp_no:emp_no},function(data){
							$("#card_no").val(data.accEmpAccount.account_code);
						},"json");
		          	}
		 		 });
          		liger.get("emp_id").setValue("${emp_id}.${emp_no}");
        		liger.get("emp_id").setText("${emp_code} ${emp_name}"); 
          	}
 		 });
		
		liger.get("dept_id").setValue("${dept_id}.${dept_no}");
		liger.get("dept_id").setText("${dept_code} ${dept_name}");
		
    	autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	liger.get("proj_id").setValue("${proj_id}.${proj_no}");
		liger.get("proj_id").setText("${proj_code} ${proj_name}");
		
		if('${state}'!= '01'){//置灰不能点击
			$("#dept_id").ligerComboBox({
				disabled:true,cancelable: false,width : 160
			});
			$("#dept_id").attr("disabled","disabled");
			$("#proj_id").ligerComboBox({
				disabled:true,cancelable: false,width : 160
			});
			$("#proj_id").attr("disabled","disabled");
			$("#emp_id").ligerComboBox({
				disabled:true,cancelable: false,width : 160
			});
			$("#emp_id").attr("disabled","disabled");
			$("#phone").ligerTextBox({disabled:true,cancelable: false,width : 160});
			$("#phone").attr("disabled","disabled");
			
			$("#card_no").ligerTextBox({disabled:true,cancelable: false,width : 160});
			$("#card_no").attr("disabled","disabled");
			
			$("#apply_date").ligerTextBox({disabled:true,cancelable: false,width : 160});
			$("#apply_date").attr("disabled","disabled");
			$("#remark").attr("disabled","disabled");
		}
		
		
    	$("#borrow_amount").val("${borrow_amount }");
		$("#borrow_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#apply_code").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#phone").ligerTextBox({width : 160});
		
		$("#card_no").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#apply_date").ligerTextBox({width : 160});
		
		$("#audit").ligerButton({click: updateSubmit, width:90});
		$("#unAudit").ligerButton({click: updateWithdraw, width:90});
		$("#close").ligerButton({click: this_close, width:90});
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
	}
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${a02002}'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${a02002}'==2){
			//按科室打印
			 if(liger.get("dept_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按科室打印，请选择科室！');
				return;
			}
			useId=liger.get("dept_id").getValue().split(".")[0];
		}
		/* var para={
				apply_code:$("#apply_code").val(),
				template_code:'02001',
				use_id:useId
			};
			printTemplate("queryBorrApplyPrintTemlate.do",para); */
		var para={
				template_code:'02001',
				class_name:"com.chd.hrp.acc.serviceImpl.payable.loanmt.BudgBorrApplyServiceImpl",
				method_name:"queryBorrApplyPrintTemlateNew",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:true,//是否预览，默认直接打印
				apply_code:$("#apply_code").val(),
				use_id:useId
		}; 
		officeFormPrint(para); 
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${a02002}'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${a02002}'==2){
			//按科室打印
			 if(liger.get("dept_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按科室打印，请选择科室！');
				return;
			}
			
			useId=liger.get("dept_id").getValue().split(".")[0];
		}
		
		officeFormTemplate({template_code:"02001",use_id:useId});
		/* parent.parent.$.ligerDialog.open({url : 'hrp/acc/payable/loanmt/apply/borrApplyPrintSetPage.do?isCheck=false&template_code=02001&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>单号：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_code" disabled="disabled" value="${apply_code }"
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
				type="text" id="apply_date" class="Wdate" value="${apply_date }" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">联系电话：</td>
			<td align="left" class="l-table-edit-td"><input name="phone" 
				type="text" id="phone"  value="${phone }"
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">银行帐号：</td>
			<td align="left" class="l-table-edit-td"><input name="card_no" disabled="disabled"
				type="text" id="card_no"  value="${card_no }"
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">借款金额：</td>
			<td align="left" class="l-table-edit-td"><input name="borrow_amount" disabled="disabled"
				type="text" id="borrow_amount" value=""
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">申请事由：</td>
			<td align="left" class="l-table-edit-td" colspan="9">
				<textarea rows="3" cols="70" id="remark" name="remark">${remark }</textarea>
			</td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">制单人：</td>
			<td align="left" class="l-table-edit-td">${maker_name }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">制单日期：</td>
			<td align="left" class="l-table-edit-td">${make_date }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">审核人：</td>
			<td align="left" class="l-table-edit-td">${checker_name }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">审核日期：</td>
			<td align="left" class="l-table-edit-td">${check_date }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">支付人：</td>
			<td align="left" class="l-table-edit-td">${payer_name }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">支付日期：</td>
			<td align="left" class="l-table-edit-td">${pay_date }</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">状态：</td>
			<td align="left" class="l-table-edit-td">
			${state_name }
			</td>
			<td align="left"></td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="audit" accessKey="A"><b>提交（<u>A</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="unAudit" accessKey="U"><b>撤回（<u>U</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
</body>
</html>
