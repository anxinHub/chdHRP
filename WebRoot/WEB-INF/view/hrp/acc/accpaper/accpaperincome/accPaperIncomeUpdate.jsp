<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script>
var grid;
var ligers;
var gridManager;
		

		$(function (){
			loadDict();
	    	
 	    	loadHead();	//加载数据

	    });

		function loadDict(){

			//字典下拉框
 			$("#type_code").ligerComboBox({
 	    		url: "queryAccPaperIncomeType_code.do?isCheck=false",
 	    		valueField: "id",
 	    		textField: "text",
 	    		selectBoxWidth: '400',
 	    		selectBoxHeight:'260',
 	    		setTextBySource: true,
 	    		width: '156',
 	    		autocomplete: true,
 	    		highLight: true,
 	    		keySupport: true,
 	    		async: false,
 	    		alwayShowInDown: true, 
 	    		parms: {pageSize: 100}
 	    	});

			//币种下拉框
 			$("#cur_code").ligerComboBox({
 	    		url: "queryAccPaperIncomeMoney.do?isCheck=false",
 	    		valueField: "id",
 	    		textField: "text",
 	    		selectBoxWidth: '400',
 	    		selectBoxHeight:'260',
 	    		setTextBySource: true,
 	    		width: '156',
 	    		autocomplete: true,
 	    		highLight: true,
 	    		keySupport: true,
 	    		async: false,
 	    		alwayShowInDown: true, 
 	    		parms: {pageSize: 100}
 	    	});
				
			//汇率下拉框
 			$("#rate_code").ligerComboBox({
 	    		url: "queryAccPaperIncomeRatename.do?isCheck=false",
 	    		valueField: "id",
 	    		textField: "text",
 	    		selectBoxWidth: '400',
 	    		selectBoxHeight:'260',
 	    		setTextBySource: true,
 	    		width: '156',
 	    		autocomplete: true,
 	    		highLight: true,
 	    		keySupport: true,
 	    		async: false,
 	    		alwayShowInDown: true, 
 	    		parms: {pageSize: 100},
 	    		onChangeValue: function (value) {
 	 	    		var rarr = this.getValue().split("|");
 	 	    		$("#money").val(rarr[1])
 	    		}/* ,
 	 	    	onSuccess:function(data){
 	 	    		this.setValue("${vo.RATE_CODE}");
 	 	 	    } */
 	    	});

	 	    //核算类下拉加载
 			$("#check_type_id").ligerComboBox({
 	    		url: "queryAccPaperIncomeCheckTypeId.do?isCheck=false",
 	    		valueField: "id",
 	    		textField: "text",
 	    		selectBoxWidth: '400',
 	    		selectBoxHeight:'260',
 	    		setTextBySource: true,
 	    		width: '156',
 	    		autocomplete: true,
 	    		highLight: true,
 	    		keySupport: true,
 	    		async: false,
 	    		alwayShowInDown: true, 
 	    		parms: {pageSize: 100}
 	    	});

		 	//核算项下拉加载
 			$("#check_item_no").ligerComboBox({
 	    		url: "queryAccPaperIncomeCheckItemNo.do?isCheck=false",
 	    		valueField: "id",
 	    		textField: "text",
 	    		selectBoxWidth: '400',
 	    		selectBoxHeight:'130',
 	    		setTextBySource: true,
 	    		width: '156',
 	    		selectBoxHeight:130,
 	    		autocomplete: true,
 	    		highLight: true,
 	    		keySupport: true,
 	    		async: false,
 	    		alwayShowInDown: true, 
 	    		parms: {pageSize: 100},
 	    		onChangeValue: function (value) {
 	 	    		var a = this.getValue().split("|");
 	 	    		$("#bank_name").val(a[2]);
 	 	    		$("#bank_number").val(a[3]);
 	 	    		$("#sf_add").val(a[4]);
 	 	    		$("#sf_men").val(a[5]);
 	 	    	}
 	    	});

 			$(':button').ligerButton({
 				width : 80
 			});
 			
 			$("#dqll").ligerTextBox({width:156,height:25, number:true, precision:6});
 			$("#pmll").ligerTextBox({width:156,height:25, number:true, precision:6});
 			
 			$("#pm_money").ligerTextBox({width:156,height:25, number:true, precision:2});
 			$("#pm_money_b").ligerTextBox({width:156,height:25, number:true, precision:2});
 			
 			$("#dqz").ligerTextBox({width:156,height:25, number:true, precision:2});
 			$("#dqz_b").ligerTextBox({width:156,height:25, number:true, precision:2});

 			$("#fkqx").ligerTextBox({width:156,height:25, number:true, precision:0});

 			
 			liger.get("type_code").setValue("${vo.TYPE_CODE}");
 			liger.get("check_type_id").setValue("${vo.CHECK_TYPE_ID}");
 			
 			if(liger.get("check_item_no").findTextByValue('${vo.CHECK_ITEM}')){
 				liger.get("check_item_no").setValue("${vo.CHECK_ITEM}");
 			}else{
 	 			liger.get("check_item_no").addItem({id:'${vo.CHECK_ITEM}',text:'${vo.CHECK_ITEM_NAME}'});
 	 			liger.get("check_item_no").setValue('${vo.CHECK_ITEM}','${vo.CHECK_ITEM_NAME}');
 			}
 			//liger.get("check_item_no").setValue("${vo.CHECK_ITEM}");
 			
/*  			var data = {id:'2850|2664||11111111111111111||',text:'东阳市吴宁安茨数码商行'};
 			liger.get("check_item_no").insertItem(data); */
 			
 			liger.get("rate_code").setValue("${vo.RATE_CODE}");
			liger.get("cur_code").setValue("${vo.CUR_CODE}");
 			
 			$("#bank_name").val("${vo.BANK_NAME}");
 			$("#bank_number").val("${vo.BANK_NUMBER}");

 			$("#sf_men").val("${vo.SF_MEN}");
 			$("#sf_add").val("${vo.SF_ADD}");
 			
		}

		function huifu(obj){
			if('${vo.PAPER_NUM}' != null && '${vo.PAPER_NUM}' != ""){
				$("#paper_num").prop("disabled",obj);
			}
		}

		function updatePaperIncome(forms){

			if($("#paper_num").val() == null || $("#paper_num").val() == "" ){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#type_code").val() == null || $("#type_code").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#cur_code").val() == null || $("#cur_code").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#qf_date").val() == null || $("#qf_date").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#cw_date").val() == null || $("#cw_date").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#dq_date").val() == null || $("#dq_date").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#rate_code").val() == null || $("#rate_code").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#money").val() == null || $("#money").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#fkqx").val() == null || $("#fkqx").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#pm_money").val() == null || $("#pm_money").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#check_type_id").val() == null || $("#check_type_id").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
			if($("#check_item_no").val() == null || $("#check_item_no").val() == ""){
				$.ligerDialog.error("必填项不可为空!");
				return false;
			}
	
			grid.endEdit();
			var arr = grid.getData();
			var gridarr = [];
			var keyMap = new HashMap();
			var recode = false;
			if(arr.length > 0){
				$.each(arr,function(){
					if(this.bs_date || this.bsr || this.bszr_date || this.tx_date || this.txl || this.txje){
						if(this.bs_date && this.bsr && this.bszr_date && this.tx_date && this.txl && this.txje){
							if(keyMap.get(new Date(this.bs_date).toLocaleDateString() + this.bsr)){
								$.ligerDialog.error("背书日期和背书人不能重复!");
								recode = true;
								return false;
							}else{
								keyMap.put(new Date(this.bs_date).toLocaleDateString() + this.bsr,true);
								gridarr.push(this);
							}
						}else{
							$.ligerDialog.error("背书信息不完整!");
							recode = true;
							return false;
						}
					}
				})
				if(recode){
					return false;
				}
				forms += "&gridarr="+JSON.stringify(gridarr);
			}
			ajaxJsonObjectByUrl("updatePaperIncome.do",forms,function(responseData){
				if(responseData.state == "true"){
					parentFrameUse().query();
					//frameElement.dialog.close();
	            }
	        });
	    }
		
		
		/*  */
		function loadHead() {

			
			grid = $("#maingrid").ligerGrid({
				columns: [{
					display: '背书日期', name: 'bs_date',align: 'left',type: 'date',format:'yyyy-MM-dd',width:100,
					editor: {
						type: 'date',showSelectBox:false
					}
				},{
					display: '背书人', name: 'bsr', align: 'left', isAllowHide: false,width:100,
					editor: {
						type: 'text',
						precision: '${p04006}'
					}
				},{
					display: '背书转让日', name: 'bszr_date', align: 'left',type: 'date',format:'yyyy-MM-dd', isAllowHide: false,width:100,
					editor: {
						type: 'date',showSelectBox:false
					}
				},{
					display: '贴现日期', name: 'tx_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width:100,
					editor: {
						type: 'date',showSelectBox:false
					},
				},{
					display: '贴现率',name: 'txl', align: 'right', isAllowHide: false,width:150,placeholder:"0.000000",
					editor: {
						type: 'numberbox',
						precision: 6,
					},
					render: function(item)
		            {
		                    return formatNumber(item.txl,6);
		            }
				},{
					display: '贴现净额', name: 'txje', align: 'right', isAllowHide: false,width:150,placeholder:"0.00",
					editor: {
						type: 'float',
					},
					render: function(item)
		            {
		                    return formatNumber(item.txje,2,1);
		            }
				}
				],
				usePager: false, width: '99%', height:200,url:'queryAccPaperIncomeBook.do?isCheck=false&paper_num='+'${vo.PAPER_NUM}'+"&type_code="+'${vo.TYPE_CODE}',
				checkbox: true, enabledEdit: true, alternatingRow: false,//取消各行变色,否则和材料有效期必填数据的提示色有冲突
				isScroll: true, rownumbers: true, delayLoad: false,//初始化明细数据
				selectRowButtonOnly: true,//heightDiff: -10,
				toolbar: { items: [ { text: '删除', id:'dele',icon:'delete',click:is_deleteRow,disabled:${vo.STATE == 1 ? false:true}},
									{ line:true },
					                { text: '添加', id:'save',icon:'add',click:is_addRow,disabled:${vo.STATE == 1 ? false:true}},
					                { line:true }
						       ]},
			});

			gridManager = $("#maingrid").ligerGetGridManager();

		
		}
		function is_addRow() {
			//setTimeout(function () { //当数据为空时 默认新增一行
				grid.addRow();
			//}, 100);
		}

		function is_deleteRow(){
			var arr = grid.getCheckedRows();
			if(arr.length > 0){
					grid.deleteRange(arr)
			}else{
				$.ligerDialog.error('请选择行!')
			}
		}
</script>

</head>
<body>
<form id="acc_paper_sf">
<table cellpadding="0" align="center" cellspacing="0" class="l-table-edit" >
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*票据编号：</td>
            <td align="left" class="l-table-edit-td"><input name="paper_num" class="l-table-edit-td" placeholder="必填" value="${vo.PAPER_NUM}" ${vo.PAPER_NUM == null ? '':'disabled'} type="text" id="paper_num" ltype="text"  /></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">*票据类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" class="l-table-edit-td" placeholder="必填" value="" id="type_code" ltype="text"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*币种：</td>
            <td align="left" class="l-table-edit-td"><input name="cur_code" class="l-table-edit-td" placeholder="必填" type="text" id="cur_code" ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">*签发日期：</td>
            <td align="left" class="l-table-edit-td"><input name="qf_date" style="width: 90;height: 27" placeholder=" 必填" value="${vo.QF_DATE}" class="Wdate" id="qf_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',maxDate:dq_date.value})"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*到期日期：</td>
            <td align="left" class="l-table-edit-td"><input name="dq_date"  style="width: 90;height: 27" placeholder=" 必填" value="${vo.DQ_DATE}" class="Wdate" id="dq_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:qf_date.value})"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*财务日期：</td>
            <td align="left" class="l-table-edit-td"><input name="cw_date"  style="width: 90;height: 27" placeholder=" 必填" value="${vo.CW_DATE}" class="Wdate" id="cw_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',minDate:qf_date.value,maxDate:dq_date.value})"   /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">*汇率类型：</td>
            <td align="left" class="l-table-edit-td"><input name="rate_code" class="l-table-edit-td" placeholder="必填" id="rate_code" ltype="text" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*汇率：</td>
            <td align="left" class="l-table-edit-td"><input name="money" class="l-table-edit-td" type="text" placeholder="必填" value="${vo.MONEY }" id="money" disabled ltype="text"  style="text-align:right" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*付款期限(天)：</td>
            <td align="left" class="l-table-edit-td"><input name="fkqx" class="l-table-edit-td" style="text-align:right" maxlength="4" placeholder="必填" value="${vo.FKQX }" type="text" id="fkqx" ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">*票面金额：</td>
            <td align="left" class="l-table-edit-td"><input name="pm_money" class="l-table-edit-td" id="pm_money" maxlength="12" placeholder="必填   0.000.00" value="${vo.PM_MONEY == null ? null:vo.PM_MONEY}" style="text-align:right"  ltype="text"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本位币票面金额：</td>
            <td align="left" class="l-table-edit-td"><input name="pm_money_b"  class="l-table-edit-td" type="text" maxlength="12" id="pm_money_b" value="${vo.PM_MONEY_B == null ? null:vo.PM_MONEY_B}" placeholder="0.000.00" style="text-align:right"  ltype="text"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票面利率(%)：</td>
            <td align="left" class="l-table-edit-td"><input name="pmll"  class="l-table-edit-td" type="text" id="pmll" maxlength="10" value="${vo.PMLL == null ? null:vo.PMLL}" placeholder="0.000000" style="text-align:right"  ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">到期值：</td>
            <td align="left" class="l-table-edit-td"><input name="dqz" class="l-table-edit-td" maxlength="12" value="${vo.DQZ == null ? null:vo.DQZ}" placeholder="0.000.00"  style="text-align:right" id="dqz" ltype="text"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本位币到期值：</td>
            <td align="left" class="l-table-edit-td"><input name="dqz_b"  class="l-table-edit-td" maxlength="12" value="${vo.DQZ_B == null ? null:vo.DQZ_B}" placeholder="0.000.00" style="text-align:right" type="text" id="dqz_b" ltype="text"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">到期利率(%)：</td>
            <td align="left" class="l-table-edit-td"><input name="dqll"  class="l-table-edit-td"  maxlength="10" value="${vo.DQLL  == null ? null:vo.DQLL}" placeholder="0.000000" style="text-align:right" type="text" id="dqll" ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">承兑人：</td>
            <td align="left" class="l-table-edit-td"><input name="cdr" class="l-table-edit-td" value="${vo.CDR}" id="cdr" ltype="text"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*核算类：</td>
            <td align="left" class="l-table-edit-td"><input name="check_type_id"  class="l-table-edit-td" placeholder="必填" type="text" id="check_type_id" ltype="text"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">*核算项：</td>
            <td align="left" class="l-table-edit-td"><input name="check_item_no"  class="l-table-edit-td" placeholder="必填" type="text" id="check_item_no" ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">开户银行：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_name" class="l-table-edit-td" value="" id="bank_name" ltype="text"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行账号：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_number"  class="l-table-edit-td" value="" type="text" id="bank_number" ltype="text" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同号：</td>
            <td align="left" class="l-table-edit-td"><input name="con_no"  class="l-table-edit-td" value="${vo.CON_NO}" type="text" id="con_no" ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款人：</td>
            <td align="left" class="l-table-edit-td"><input name="sf_men" class="l-table-edit-td" value="${vo.SF_MEN}" id="sf_men" ltype="text"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款地：</td>
            <td align="left" class="l-table-edit-td"><input name="sf_add"  class="l-table-edit-td" value="${vo.SF_ADD}" type="text" id="sf_add" ltype="text"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出票人：</td>
            <td align="left" class="l-table-edit-td"><input name="cp_men"  class="l-table-edit-td" value="${vo.CP_MEN}" type="text" id="cp_men" ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right"  class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
            <td align="left" colspan="3" class="l-table-edit-td"><input name="summary" value="${vo.SUMMARY}" class="l-table-edit-td" id="summary" ltype="text" style="width:444"   /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出票地：</td>
            <td align="left" class="l-table-edit-td"><input name="cp_add" value="${vo.CP_ADD}" class="l-table-edit-td" type="text" id="cp_add" ltype="text" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td">${vo.CREATENAME}</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td">${vo.AUDITNAME == null ? '未审核':vo.AUDITNAME }</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">打印次数：</td>
            <td align="left" class="l-table-edit-td">${vo.PRINTCOUNT}</td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">${vo.STATE == 3 ? '退票说明：':''} ${vo.STATE == 4 ? '收款说明：':''}</td>
            <td align="left" rowspan="5" class="l-table-edit-td">${vo.STATEVAL}</td>
        </tr>
    </table>
</form>
	<div style="border:1px" id="btn">
<%-- 	    <input  type="button" style="display:${vo.STATE == 1 ? '':'none'}"  value="删除" onclick="is_deleteRow()" />
	    <input  type="button" style="display:${vo.STATE == 1 ? '':'none'}"  value="添加 " onclick="is_addRow()"/> --%>
	</div>
		<div id="maingrid"></div>
</body>
</html>