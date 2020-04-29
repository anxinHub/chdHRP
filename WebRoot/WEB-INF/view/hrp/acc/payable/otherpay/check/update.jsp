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
	
	
	//页面初始化
	$(function() {

		loadDict();//加载下拉框
		loadHead(null);//加载grid
		loadHotkeys();//加载快捷键
	});

	
	
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
				{display : '收款单位/个人',name : 'unit_id',textField : 'unit_name',align : 'left',width: '200',editor : {
					type : 'select',valueField : 'unit_id',textField : 'unit_name',
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
				{display : '开户行',name : 'bank_name',align : 'left',width: '200'
				},
				{display : '开户行所在地',name : 'bank_location',align : 'left',width: '200'
				},
				{display : '银行账号',name : 'card_no',align : 'left',width: '200'
				},
				{display : '付款金额',name : 'payment_amount',align : 'left',width: '150',
					editor : {
						type : 'numberbox',
						precision : 2
					},align : 'right',
					render: function(item){
						return formatNumber(item.payment_amount,2,1);
					} ,totalSummary:{
						render: function (suminf, column, cell){
					   		return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					    	}
						}  
				},

				{display : '说明',name : 'remark',align : 'left',width: '300',
					editor : {
						type : 'text'
					}
				}
			],
			dataAction : 'server',dataType : 'server',usePager : false,
			url : 'queryBudgChargeApplyDet.do?isCheck=false&apply_code=${apply_code}',
			width : '100%',height : '85%',checkbox : false,enabledEdit : '${state}'!='02'?false:true,
			alternatingRow : true,onBeforeEdit : f_onBeforeEdit,onBeforeSubmitEdit : f_onBeforeSubmitEdit,
			onAfterEdit : f_onAfterEdit,isScroll : true,rownumbers : true,delayLoad : false,//初始化明细数据
			selectRowButtonOnly : true
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
		return  true; 
	}
	
	
	//关闭窗口
	function this_close(){
		frameElement.dialog.close();
	}
	
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', audit);
		hotkeys('D', reAudit);
	}
	
	
	//添加行
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	
	
	//加载字典
	function loadDict() {

    	//use_apply_code_up 用户申请单 下拉框 hrp/budg/base/budgMoneyApply/Apply/queryUserApplyCode
    	$("#use_apply_code_up").ligerComboBox({	
	      	url: '../../../../budg/base/budgMoneyApply/Apply/queryUserApplyCode.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160,
	      	disabled:true
		 });
		liger.get("use_apply_code_up").setValue("${use_apply_code}");
	 	liger.get("use_apply_code_up").setText("${use_apply_code}");
		
		autocomplete("#dept_id","../../../../sys/queryDeptDict.do?isCheck=false&is_last=1", "id","text", true, true,null,false);
		
 		liger.get("dept_id").setValue("${dept_id}.${dept_no}");
 		liger.get("dept_id").setText("${dept_code} ${dept_name}");
 		 
 		
 		$("#emp_id").ligerComboBox({
          	url: "../../../queryEmpDict.do?isCheck=false",
          	valueField: 'id',textField: 'text', selectBoxWidth: 160,
          	autocomplete: true,width: 160
 		 });
 		
  		liger.get("emp_id").setValue("${emp_id}.${emp_no}");
		liger.get("emp_id").setText("${emp_code} ${emp_name}");
		
    	autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	liger.get("proj_id").setValue("${proj_id}.${proj_no}");
		liger.get("proj_id").setText("${proj_code} ${proj_name}");
		
		autocomplete("#source_code", "../../../../sys/querySourceDict.do?isCheck=false", "id","text", true, true,null,false); 
		
		liger.get("source_code").setValue("${source_code}.${source_id}");
		
		liger.get("source_code").setText("${source_code} ${source_name}");
		
		autocomplete("#payment_code", "../../../queryBudgPaymentItemDict.do?isCheck=false&is_write=1", "id","text", true, true,null,false); 
		
		liger.get("payment_code").setValue("${payment_item_id}@${payment_item_no}");
		
		liger.get("payment_code").setText("${payment_item_code} ${payment_item_name}");
    	
		$("#dept_id").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#dept_id").attr("disabled","disabled");
		$("#proj_id").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#proj_id").attr("disabled","disabled");
		$("#emp_id").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#emp_id").attr("disabled","disabled");
		
		$("#source_code").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#source_code").attr("disabled","disabled");
		
		$("#payment_code").ligerComboBox({
			disabled:true,cancelable: false,width : 230
		});
		$("#payment_code").attr("disabled","disabled");
		
		$("#apply_date").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#apply_date").attr("disabled","disabled");
		
		
		$("#remark").attr("disabled","disabled");
		
		$("#payment_amount").val(parseFloat(${payment_amount }).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,'$&,'));
		$("#payment_amount").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#offset_amount").val("${offset_amount }");
		$("#offset_amount").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#pay_amount").val("${pay_amount }");
		$("#pay_amount").ligerTextBox({disabled:true,cancelable: false,width : 230});
		
		$("#apply_code").val("${apply_code}");
		$("#apply_code").ligerTextBox({disabled:true,cancelable: false,width : 230});
    	
		$("#audit").ligerButton({click: audit, width:90});
		$("#unAudit").ligerButton({click: reAudit, width:90});
		$("#close").ligerButton({click: this_close, width:90});
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
		
		/*var ParamVo = {
			apply_code :  '${use_apply_code}'   ,
			apply_date :  '${apply_date}'   ,
			proj_id :  '${proj_id}'   ,
			proj_no :  '${proj_no}'   ,
			dept_id :  '${dept_id}'   ,
			source_id :  '${source_id}'   ,
			source_code :  '${source_code}'   ,
			payment_item_id :  '${payment_item_id}'   ,
			payment_item_no :  '${payment_item_no}'
			
 		};
		
		
		 ajaxJsonObjectByUrl("../../../../budg/base/budgMoneyApply/Apply/queryBudgetQuota.do?isCheck=false", {ParamVo : JSON.stringify(ParamVo)}, function(responseData) {
			
			$('#already_apply').val(''+responseData.already_apply+'');
			$('#budg_money').val(''+responseData.budg_money+'');
			$('#already_execute').val(''+responseData.already_execute+'');
		});
		
		
    	if(!'${isDisplay}'){
    		$("#this_td1").hide();
    		$("#this_td2").hide();
    		$("#this_td3").hide();
        	$('#already_apply').css( "display" , "none" );
			$('#budg_money').val(''+responseData.budg_money+'');
			$('#already_execute').val(''+responseData.already_execute+'');
    	} */
    	
	}
	
	
	//审核
	function audit(){
		var ParamVo = [];
		ParamVo.push('${group_id}' + "@" + '${hos_id}' + "@" + '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
						
		$.ligerDialog.confirm('确定审核?', function (yes){
            if(yes){
				ajaxJsonObjectByUrl("auditBudgChargeApply.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						parentFrameUse().query();
						query();
						location.reload();
					}
				});
            }
		});
	}
	
	//消审
	function reAudit(){
		var ParamVo = [];
		ParamVo.push('${group_id}' + "@" + '${hos_id}' + "@"
									+ '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
		$.ligerDialog.confirm('确定消审?', function (yes){
           	if(yes){
				ajaxJsonObjectByUrl("reAuditBudgChargeApply.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						parentFrameUse().query();
						query();
						location.reload();
					}
				});
           	}
		});
	}
	
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${a02004 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${a02004 }'==2){
			//按科室打印
			 if(liger.get("dept_id").getValue()==""){
				$.ligerDialog.warn('当前打印模式是按科室打印，请选择科室！');
				return;
			}
			useId=liger.get("dept_id").getValue().split(".")[0];
		}
		var para={
				apply_code:$("#apply_code").val(),
				template_code:'02002',
				use_id:useId
			};
			printTemplate("queryPaymentCheckPrintTemlate.do",para);
	}
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${a02004 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${a02004 }'==2){
			//按科室打印
			 if(liger.get("dept_id").getValue()==""){
				$.ligerDialog.warn('当前打印模式是按科室打印，请选择科室！');
				return;
			}
			
			useId=liger.get("dept_id").getValue().split(".")[0];
		}
		
		parent.parent.$.ligerDialog.open({url : 'hrp/acc/payable/reimbursemt/apply/paymentApplyPrintSetPage.do?isCheck=false&template_code=02002&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		});
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>费用申请单号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="apply_code" disabled="disabled" type="text" id="apply_code" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>申请日期：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_date" value="${apply_date}"
				type="text" id="apply_date" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" 
				type="text" id="dept_id"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>申请人：</td>
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
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资金来源：</td>
			<td align="left" class="l-table-edit-td"><input name="source_code"  type="text" id="source_code"  /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_code" type="text" id="payment_code"  /></td>
			<td align="left"></td> 
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款金额：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_amount" type="text" id="payment_amount" value="${payment_amount}"/></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">申请事由：</td>
			<td align="left" class="l-table-edit-td" colspan="4">
				<textarea rows="3" cols="90" id="remark" name="remark">${remark}</textarea>
			</td>
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" id="this_td2" >用款申请单：</td>
            <td align="left" class="l-table-edit-td"  id="this_td3" >
            	<input name="use_apply_code_up" type="text" id="use_apply_code_up"  disabled="disabled" />
            </td>
		</tr>
		<!--<tr id="this_td1">
			 <td align="right" class="l-table-edit-td" style="padding-left: 20px;"  >用款申请额度：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="use_aplly_money" type="text" id="already_apply"  disabled="disabled"  />
			</td> 
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算额度：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="to_money" type="text" id="budg_money"  disabled="disabled" />
            </td>
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">已执行额度：</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="alerly_money" type="text" id="already_execute"  disabled="disabled" />
            </td>
		</tr>-->
	</table>
	
	<hr size="1" width="1400" color="#A3COE8" align="left" style="position:absolute;">
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 10px;">
		
		
		<tr style="display: none">
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">项目总预算：</td>
			<td align="left" class="l-table-edit-td"></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">预算余额：</td>
			<td align="left" class="l-table-edit-td"></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">可用余额：</td>
			<td align="left" class="l-table-edit-td"></td>
			<td align="left"></td>
		</tr>
		
		<!-- 	
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">是否有借款：</td>
			<td align="left" class="l-table-edit-td"><font id="is_apply"></font></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">借款余额：</td>
			<td align="left" class="l-table-edit-td"><font id="not_price"></font></td>
			<td align="left"></td>
		</tr> -->
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
					<button id ="audit" accessKey="A"><b>审核（<u>A</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="unAudit" accessKey="U"><b>消审（<u>U</u>）</b></button>
					<!-- &nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button> -->
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
</body>
</html>
