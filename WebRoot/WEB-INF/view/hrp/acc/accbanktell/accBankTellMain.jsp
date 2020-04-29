<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<%-- <jsp:include page="${path}/inc.jsp" /> --%>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;

	$(function() {

		loadDict();

		loadHead(null); //加载数据

		//获取当前时间，根据年度、月份设置凭证起止日期
		var mydate = new Date();

		var vYear = mydate.getFullYear();

		var vMon = mydate.getMonth() + 1;

		var acc_month;

		if (vMon < 10) {

			acc_month = getMonthDate(vYear, "0" + vMon);

		} else {

			acc_month = getMonthDate(vYear, vMon);

		}

		$("#begin_date").val(acc_month.split(";")[0]);

		$("#end_date").val(acc_month.split(";")[1]);

	});

	//查询
	function query() {
		if ($("#subj_code").val() == '') {
			$.ligerDialog.error('科目编码为必填项');
			return;
		}
		if ($("#begin_date").val() == '' || $("#end_date").val() == '') {
			$.ligerDialog.error('起始时间为必填项');
			return;
		}

		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'subj_code',
			value : liger.get("subj_code").getValue()
		});
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		});
		grid.options.parms.push({
			name : 'is_check',
			value : $('#is_check').val()
		});
		//加载查询条件
		grid.loadData(grid.where);
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '业务日期',
				type : 'date',
				name : 'occur_date',
				align : 'left',
				format : 'yyyy-MM-dd',
				minWidth : 100,
				editor : {
					type : 'date',
				}
			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',
				editor : {
					type : 'text',
				},
			}, {
				display : '结算方式',
				name : 'pay_code',
				textField : 'pay_name',
				align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../queryPayType.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				}
			}, {
				display : '票据号',
				name : 'check_no', 
				align : 'left',    
				editor : {
					type : 'text',
				},
			}, {
				display : '借方金额',
				name : 'debit',
				align : 'right',
				editor : {
					type : 'float',
				},
				render : function(rowdata) {
					return formatNumber(rowdata.debit, 2, 1);
				}
			}, {
				display : '贷方金额',
				name : 'credit',
				align : 'right',
				editor : {
					type : 'float',
				},
				render : function(rowdata) {
					return formatNumber(rowdata.credit, 2, 1);
				}
			}, {
				display : '余额',
				name : 'bal',
				align : 'right',
				render : function(rowdata) {
					return formatNumber(rowdata.bal, 2, 1);
				}
			}, {
				display : '是否对账',
				name : 'is_check',
				align : 'center'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryBankTell.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			checkBoxDisplay : isCheckDisplay,
			enabledEdit : true,
			clickToEdit :true,
			selectRowButtonOnly : true,
			onAfterEdit : f_onAfterEdit,
			onBeforeEdit: f_onBeforeEdit,
			onAfterAddRow: f_onAfterAddRow, 
			onDblClickRow : function(rowdata, rowindex, value) {
				if (rowdata.bank_id == '0') {
					$.ligerDialog.error('只能修改每日发生数据');
					return;
				}
			}
,    lodop:{
		title:"银行对账单",
			fn:{
 			debit:function(value){//借方
 				if(value == 0){return "";}
        			else{return formatNumber(value, 2, 1);}
 			},
 			credit:function(value){//贷方
 				if(value == 0){return "";}
       			else{return formatNumber(value, 2, 1);}
				},
				end_os:function(value){//余额
	   				 if(value==0){return "Q";}
					 else{return formatNumber(value, 2, 1);}
				}
 		}
		}
		
		
		
		});
	}
	 //打印回调方法
    function lodopPrint(){
    /* 	var accStr="不包含未记账"
       	if($("#is_state").prop("checked")){
       		accStr="包含未记账"
       	} */
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td>";
 		grid.options.lodop.head=head;
    }
	function isCheckDisplay(rowdata) {
		//admin用户没有复选框
		// console.log(rowdata.checkboxDisplay ==false)
		if (rowdata.occur_date == null)
			return false;

		return true;

	}
	
	//新增行事件
	function f_onAfterAddRow(rowdata){
   		if(grid.getRow(rowdata.__index - 1)){
			var b_rowdata = grid.getRow(rowdata.__index - 1);
			grid.updateCell('occur_date', b_rowdata.occur_date, rowdata.__id);
		}
	}
	
    //编辑前事件
    function f_onBeforeEdit(e)
    { 
    	if(e.record.summary == "期初余额" || e.record.summary == "合计" && e.record.occur_date ==null)return false;
    	
    	return true;
    }
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		 <%--console.log(e)--%>
		if (e.column.columnname == "debit") {
			if(grid.getRow(e.rowindex - 1)){
				var b_rowdata = grid.getRow(e.rowindex - 1);
				var b_bal = b_rowdata.bal ? parseFloat(b_rowdata.bal) : 0;
				var debit = e.value ? parseFloat(e.value) : 0;
				var credit = e.record.credit ? parseFloat(e.record.credit) : 0;
				grid.updateCell('bal', b_bal - debit + credit, e.rowindex);
			}
		}
		if (e.column.columnname == "credit") {
			var validate_detail_buf = new StringBuffer();
			var flag = true;
			var d = new Date(e.record.occur_date)
			var year = d.getFullYear();
			var month = d.getMonth() + 1;
			var day = d.getDate();
			<%--console.log(e.record.occur_date==='')--%>
			if(e.record.occur_date===''){
				validate_detail_buf.append('业务日期不能为空! ');
			}
			if(liger.get("subj_code").getValue() == ''){
				
				validate_detail_buf.append('科目编码不能为空! ');
	
			}
			/*if(!e.record.summary){
				
				validate_detail_buf.append('摘要不能为空! ');
			}*/
			
			if(e.record.debit == 0 && e.record.credit == 0 ){
				
				validate_detail_buf.append('借方金额贷方金额填写一个! ');
			}
			
			if(e.record.debit && e.record.credit && e.record.debit != 0 && e.record.credit != 0 ){
				
				validate_detail_buf.append('借方金额贷方金额填写一个! ');
			}
			
			var formPara = {

				subj_code : liger.get("subj_code").getValue(),

				debit : e.record.debit,

				credit : e.record.credit,

				check_no : e.record.check_no,

				occur_date : year + '-' + (month < 10 ? "0" + month : month)
						+ '-' + (day < 10 ? "0" + day : day),
						
				summary : e.record.summary,

				pay_type_code : e.record.pay_code,

				bank_id : e.record.bank_id

			};
			 <%--console.log(validate_detail_buf.toString() != "")--%>
			 if(validate_detail_buf.toString() != ""){
				 
				 $.ligerDialog.error("第"+(e.rowindex + 1)+"行"+validate_detail_buf.toString(), '提示','',{closeWhenEnter:false});
				 
				return false;
			 }
			 
			if(grid.getRow(e.rowindex - 1)){
				var b_rowdata = grid.getRow(e.rowindex - 1);
				var b_bal = b_rowdata.bal ? parseFloat(b_rowdata.bal) : 0;
				var debit = e.record.debit ? parseFloat(e.record.debit) : 0;
				var credit = e.value ? parseFloat(e.value) : 0;
				grid.updateCell('bal', b_bal - debit + credit, e.rowindex);
			}
			 
			if (e.record.bank_id == 0 ||e.record.bank_id == null) {
				ajaxJsonObjectByUrl("addAccBankTell.do", formPara, function(
						responseData) {
					if (responseData.state == "true") {
						e.record.bank_id=responseData.id
					}
				});
			} else if (e.record.bank_id != 0) {
				ajaxJsonObjectByUrl("updateAccBankTell.do", formPara, function(
						responseData) {

					if (responseData.state == "true") {
					}
				});
			}
		}

		 return false;
	}

	function add_open() {

		$.ligerDialog.open({
			url : 'accBankTellAddPage.do?isCheck=false',
			height : 300,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAccBankCheck();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	function delete_btn() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var flag = true;
			$(data).each(function() {

				if (this.is_check == '是') {
					$.ligerDialog.error('已对账的数据不能删除');
					flag = false;
					return;
				}

				if (this.bank_id != 0) {
					ParamVo.push(
					//表的主键
					this.bank_id)
				}
			});
			if (ParamVo.length == 0) {
				$.ligerDialog.error('请选择业务数据');
				return;
			}
			if (flag) {
				$.ligerDialog.confirm('确定删除?', function(yes) {
					if (yes) {
						ajaxJsonObjectByUrl("deleteAccBankTell.do", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				});
			}
		}
	}
	/* function openUpdate(obj) {

		$.ligerDialog.open({
			url : 'accBankTellUpdatePage.do?isCheck=false&bank_id=' + obj,
			data : {},
			height : 373,
			width : 488,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAccBankCheck();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	} */
	
	 function print_btn(){

		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		 }
           	
    	/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "银行对账单",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.tell.AccBankCheckTellService",
				method_name: "collectqueryAccBankCheckTellPrint",
				bean_name: "accBankCheckTellService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
	
		//执行方法的查询条件
   		printPara['subj_code']= liger.get("subj_code").getValue();
   		printPara['begin_date']=$("#begin_date").val();
   		printPara['end_date']=$("#end_date").val();
   		printPara['is_check']=$("#is_check").val();
		officeGridPrint(printPara);
		}
	 function downTemplate(){
	    	
	    	location.href = "downTemplate.do";
	    	
	    }    
	function loadDict() {
		//字典下拉框
		autocomplete("#subj_code",
				"../querySubj.do?isCheck=false&subj_nature_code=03&is_last=1",
				"id", "text", true, true, '', true);

		$("#begin_date").ligerTextBox({
			width : 120
		});

		$("#end_date").ligerTextBox({
			width : 120
		});

		$("#is_check").ligerTextBox({
			width : 80
		});

		$(':button').ligerButton({
			width : 80
		});
	}
	
	<%-- function imports(){
		
		var index = layer.open({
			type : 2,
			title : '银行对账单导入',
			shadeClose : false,
			shade : false,
			maxmin : false, //开启最大化最小化按钮
			area : [ '800px', '400px' ],
			content : 'accBankTellImportPage.do?isCheck=false'
		});
		layer.full(index);
		
		
		$.ligerDialog.open({
			url: 'accBankTellImportPage.do?isCheck=false',
			height: 500,
			width: 1135,
			title: '导入',
			showToggle: false,
			showMax: false,
			showMin: true,
			isResize: true
		}); --%>
		
		/*   var putin = */ function imports() {

	    		//$("form[name=fileForm]").submit();
	    		var para = {
	    			"column" : [ {
	    				"name" : "subj_code",
	    				"display" : "科目编码",
	    				"width" : "120",
	    				"require" : true
	    			},{
	    				"name" : "subj_name",
	    				"display" : "科目名称",
	    				"width" : "200"
	    			},{
	    				"name" : "occur_date",
	    				"display" : "发生日期",
	    				"width" : "100",
	    				"require" : true
	    			},{
	    				"name" : "pay_type_code",
	    				"display" : "结算方式编码",
	    				"width" : "120",
	    				"require" : true
	    			},{
	    				"name" : "pay_type_name",
	    				"display" : "结算方式名称",
	    				"width" : "120"
	    			},{
	    				"name" : "summary",
	    				"display" : "摘要",
	    				"width" : "160"
	    			},{
	    				"name" : "debit",
	    				"display" : "借方金额",
	    				"width" : "120"
	    			},{
	    				"name" : "credit",
	    				"display" : "贷方金额",
	    				"width" : "120"
	    			},{
	    				"name" : "bal",
	    				"display" : "余额",
	    				"width" : "120"
	    			},{
	    				"name" : "check_no",
	    				"display" : "票据号",
	    				"width" : "120"
	    			},{
	    				"name" : "business_no",
	    				"display" : "单据号",
	    				"width" : "120"
	    			}]

	    		};
	    		importSpreadView("../hrp/acc/accbanktell/readAccBankTellFiles.do?isCheck=false", para);
	        };
	/* } */

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<div id="topmenu"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				size="3px" color="red">*</font>科目编码：</td>
			<td align="left" class="l-table-edit-td"><input name="subj_code"
				type="text" id="subj_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">日期：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="begin_date" type="text" id="begin_date" ltype="text"
				validate="{required:true,maxlength:20}"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="center" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="end_date" type="text" id="end_date" ltype="text"
				validate="{required:true,maxlength:20}"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				size="3px" color="red">*</font>是否对账：</td>
			<td align="left" class="l-table-edit-td">
				<select id="is_check">
					<option value="1">全部</option>
					<option value="2">是</option>
					<option value="3">否</option>
				</select>
			</td>
			<td align="left"></td>
		</tr>

	</table>
	<div style="border: 1px">
		<input type="button" value=" 查询" onclick="query();" />
		<input type="button" value=" 添加" onclick="is_addRow()" />
		<input type="button" value=" 删除" onclick="delete_btn();" />
		<input  type="button" value="打印" onclick="print_btn();"/>
		<input  type="button" value="导入" onclick="imports();"/>
	<!-- 	<input  type="button" value="下载导入模板" onclick="downTemplate();"/> -->
	</div>
	<div id="maingrid"></div>

</body>
</html>
