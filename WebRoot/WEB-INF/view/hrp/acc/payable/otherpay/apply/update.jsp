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
		loadHead(null);//加载数据
		loadHotkeys();//加载快捷键
		
		if('${state}'!= '01'){//置灰不能点击
        	toobarmanage=gridManager.toolbarManager;
        	toobarmanage.setDisabled('save');
        	toobarmanage.setDisabled('delete');
        }
	});
	
	
	//保存
	function save() {
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
		
		if($("#unit_name").val() == ""){
			$.ligerDialog.warn('收款单位不能为空');
			return;
		}
		
		if($("#card_no").val() == ""){
			$.ligerDialog.warn('银行账号不能为空');
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
			source_id : liger.get("source_code").getValue().split(".")[1],
			proj_id : liger.get("proj_id").getValue().split(".")[0],
			proj_no : liger.get("proj_id").getValue().split(".")[1],
			payment_item_id_no : liger.get("payment_code").getValue(),
			payment_item_id : liger.get("payment_code").getValue().split("@")[0],
			payment_item_no : liger.get("payment_code").getValue().split("@")[0],
			remark : $("#remark").val(),
			payment_amount : $("#payment_amount").val(),
			card_no : $("#card_no").val(),
			phone : $("#phone").val(),
			bank_name : $("#bank_name").val(),
			bank_location : $("bank_location").val(),
			unit_name:$("#unit_name").val(),
			use_apply_code:liger.get("use_apply_code").getValue(),
	        ParamVo : JSON.stringify(data)
		};
		
		if (validateGrid()) {  
			ajaxJsonObjectByUrl("updateBudgChargeApply.do", formPara,function(responseData) {
				if (responseData.state == "true") {
					query();
					parentFrameUse().query();
					is_addRow();
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
				{display : '开户行',name : 'bank_name',align : 'left',width: '200',/* editor : {type : 'text'} */
					editor :{
 	 					type : 'select',
 						valueField : 'text',
 						textField : 'text',
 						selectBoxWidth : 300,
 						selectBoxHeight : 200,
 						url: '../../../../acc/internetbank/queryAccICBCIBPSCode.do?isCheck=false',
 						width: '98%', height: '98%', 
 						keySupport : true,
 						autocomplete : true
 						
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
	 						autocomplete : true
	 					}
				},
				{display : '银行账号',name : 'card_no',align : 'left',width: '200',editor : {type : 'text'}
				},
				{display : '付款金额',name : 'payment_amount',align : 'left',width: '150',
					editor : {
						type : 'numberbox',
						precision : 2
					},align : 'right',
					render: function(item){
						return formatNumber(item.payment_amount,2,1);
					},totalSummary:{
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
			width : '100%',height : '85%',checkbox : true,enabledEdit : '${state}'!='01'?false:true,
			alternatingRow : true,onBeforeEdit : f_onBeforeEdit,onBeforeSubmitEdit : f_onBeforeSubmitEdit,
			onAfterEdit : f_onAfterEdit,isScroll : true,rownumbers : true,delayLoad : false,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ 
					{text : '保存',id : 'save',click : save,icon : 'save'}, 
					{line : true}, 
					
					{text : '删除',id : 'delete',click : remove,icon : 'delete'} 
				]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	var rowindex_id = "";
	var column_name = "";
	
	//编辑单元格之前
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
		
		var sum_payment_amount = 0;
		
		if(e.column.columnname == "payment_amount"){
			var data = grid.getData();
			$.each(data,function(i,v){
				if(v.payment_amount == undefined){
					return ; 
				}
				sum_payment_amount += v.payment_amount;
			});
			$("#payment_amount").val(parseFloat(sum_payment_amount).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,'$&,'));
		}
		return true;
	}
	
	//验证grid表格录入的数据
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
					rowm+="[付款金额]、";
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
	
	//删除
	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return ; 
		} 
		var ParamVo = [];
		var payment_amount = '${payment_amount}';
		var i = 0;
		$(data).each(function() {
			if(isnull(this.group_id)){
				gridManager.deleteSelectedRow();
			}else{
				ParamVo.push(this.group_id + "@" + this.hos_id + "@"
					+ this.copy_code + "@" + this.apply_code + "@"
					+ this.source_id + "@" + this.payment_item_id);
				
				payment_amount -= this.payment_amount;
			}
			i++;
		});
		
		if(ParamVo == ""){
			is_addRow();
			return;
		}

		var formpara = {
			group_id : '${group_id}',
			hos_id : '${hos_id}',
			copy_code : '${copy_code}',
			apply_code : '${apply_code}',
			payment_amount : payment_amount,
			paramVo : ParamVo.toString()
		}
		
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteBudgChargeApplyDet.do?isCheck=false", formpara, function(responseData) {
					if (responseData.state == "true") {
						query();
						parentFrameUse().query();
						$("#payment_amount").val(parseFloat(payment_amount).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,'$&,'));
						is_addRow();
					}
				});
			}
		});
	}

	//提交
	function submit(){
		var ParamVo = [];
		
		if('${state}' != '01'){
			$.ligerDialog.warn('只能提交新建状态的数据');
			return ;
		}
		
		ParamVo.push('${group_id}'+ "@" + '${hos_id}' + "@" + '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
		$.ligerDialog.confirm('确定提交?', function (yes){
           	if(yes){
				ajaxJsonObjectByUrl("submitBudgChargeApply.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						parentFrameUse().query();
						location.reload();
					}
				});
           	}
		});
	}
	
	//撤回
	function unSubmit(){
		
		if('${state}' != '02'){
			$.ligerDialog.warn('只能撤销已提交状态的数据');
			return ;
		}
		
		var ParamVo = [];
		ParamVo.push('${group_id}'+ "@" + '${hos_id}' + "@" + '${copy_code}' + "@" + '${apply_code}' + "@" + '${state}');
		$.ligerDialog.confirm('确定撤回?', function (yes){
           	if(yes){
				ajaxJsonObjectByUrl("unSubmitBudgChargeApply.do", {ParamVo : ParamVo.toString()}, function(responseData) {
					if (responseData.state == "true") {
						parentFrameUse().query();
						location.reload();
					}
				});
           	}
		});
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

	//收款单位选择器
	function unitSelector(){
		
		$.ligerDialog.open({ 
    		url : 'accUnitSelectorPage.do?isCheck=false',
    		data:{}, 
    		title:'选择器',height: 500,width: 1000,modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
		   		{ text: '确定', onclick: 
		   			function (item, dialog) { 
	    		    	dialog.frame.saveSelectData();
	    		    	dialog.close();
		    		},cls:'l-dialog-btn-highlight' 
		    	}, 
    		    { text: '取消', onclick: 
    		    	function (item, dialog) { 
    		    		dialog.close(); 
    		    	} 
		    	} 
		    ] 
    	});
	}
	
	//加载字典
	function loadDict() {

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
		
    	var isDisplay = '${isDisplay}';
    	if(!isDisplay){
    		$("#this_td1").hide();
        	$("#this_td2").hide();
    	}
		
		$("#dept_id").ligerComboBox({
          	url: "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1",
          	valueField: 'id',textField: 'text', selectBoxWidth: 160,
          	autocomplete: true,width: 160,
          	onSelected:function(value){
          		
          		$("#payment_code").ligerComboBox({url: "../../../queryBudgPaymentItemDictDept.do?isCheck=false&dept_id="+liger.get("dept_id").getValue().split(".")[0]});
          		//var dept_id = value.split(".")[0];
				//var dept_no = value.split(".")[1];
          		//$("#emp_id").ligerComboBox({
                //  	url: "../../../queryEmpDict.do?isCheck=false&dept_id="+dept_id,
                //  	valueField: 'id',textField: 'text',selectBoxWidth: 160,
                // 	autocomplete: true,width: 160,
         		//});
          		//liger.get("emp_id").setValue("");
          		//liger.get("emp_id").setText(""); 
          	}
 		});
		//autocomplete("#dept_id","../../../../sys/queryDeptDict.do?isCheck=false&is_last=1", "id","text", true, true,null,false);
		
 		liger.get("dept_id").setValue("${dept_id}.${dept_no}");
 		liger.get("dept_id").setText("${dept_code} ${dept_name}");

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
				if(v !=  '${emp_id}.${emp_no}'){
			    	autocomplete("#dept_id","../../../../sys/queryDeptDict.do?isCheck=false&is_last=1&emp_id="+emp_id, "id", "text", true, true, '',true);
				}
          	}
 		 });
 		
  		liger.get("emp_id").setValue("${emp_id}.${emp_no}");
		liger.get("emp_id").setText("${emp_code} ${emp_name}");
		
    	//autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	liger.get("proj_id").setValue("${proj_id}.${proj_no}");
		liger.get("proj_id").setText("${proj_code} ${proj_name}");
		
		//autocomplete("#source_code", "../../../../sys/querySourceDict.do?isCheck=false", "id","text", true, true,null,false); 
		
		liger.get("source_code").setValue("${source_code}.${source_id}");
		
		liger.get("source_code").setText("${source_code} ${source_name}");
		
		//autocomplete("#payment_code", "../../../queryBudgPaymentItemDict.do?isCheck=false&is_write=1", "id","text", true, true,null,false); 
		
		liger.get("payment_code").setValue("${payment_item_id}@${payment_item_no}");
		
		liger.get("payment_code").setText("${payment_item_code} ${payment_item_name}");
		
		liger.get("use_apply_code").setValue("${use_apply_code}");
		
		liger.get("use_apply_code").setText("${use_apply_code}");
    	
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
			
			$("#source_code").ligerComboBox({
				disabled:true,cancelable: false,width : 160
			});
			$("#source_code").attr("disabled","disabled");
			
			$("#payment_code").ligerComboBox({
				disabled:true,cancelable: false,width : 160
			});
			$("#payment_code").attr("disabled","disabled");
			
			$("#apply_date").ligerTextBox({disabled:true,cancelable: false,width : 160});
			
			$("#apply_date").attr("disabled","disabled");
			
			
			$("#remark").attr("disabled","disabled");
			$("#use_apply_code").ligerTextBox({disabled : true,cancelable: false});
		}
		
		
		
    	$("#payment_amount").val(parseFloat(${payment_amount }).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,'$&,'));
		$("#payment_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#offset_amount").val("${offset_amount }");
		$("#offset_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#pay_amount").val("${pay_amount }");
		$("#pay_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#apply_code").val("${apply_code}");
		$("#apply_code").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#apply_date").ligerTextBox({width : 160});
		$("#audit").ligerButton({click: submit, width:90});
		$("#unAudit").ligerButton({click: unSubmit, width:90});
		$("#close").ligerButton({click: this_close, width:90});
		$("#print").ligerButton({click: printDate, width:90});
		$("#printSet").ligerButton({click: printSet, width:100});
	}
	
	//打印
	function printDate(){
		
		var useId=0;//统一打印
		if('${a02004}'==1){
			//按用户打印
			useId='${sessionScope.user_id}';
		}else if('${a02004}'==2){
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
			printTemplate("queryPaymentApplyPrintTemlate.do",para);
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
	
	
	
	//打印设置
	function printSet(){
		
		var useId=0;//统一打印
		if('${a02004}'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${a02004}'==2){
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

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>单号：</td>
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>经办人：</td>
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
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red">*</font>支出项目：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_code" type="text" id="payment_code"  /></td>
			<td align="left"></td> 
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款金额：</td>
			<td align="left" class="l-table-edit-td"><input name="payment_amount" type="text" id="payment_amount" value="${payment_amount}"/></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font color="red">*</font>申请事由：</td>
			<td align="left" class="l-table-edit-td" colspan="4">
				<textarea rows="3" cols="70" id="remark" name="remark">${remark}</textarea>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;" id="this_td1">用款申请：</td>
			<td align="left" class="l-table-edit-td" id="this_td2"><input name="use_apply_code" 
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
					<!-- &nbsp;&nbsp;
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; 
					<button id ="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
</body>
</html>
