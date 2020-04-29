<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var userUpdateStr;

	$(function() {
		$(':button').ligerButton({width:90});
		//004:现金流量与辅助核算一起保存,隐藏批量标注、批量取消按钮
		//if($("#para004").val()=='1'){
			//$("input[name='batchButton']").hide();	
		//}
		
		loadDict();

		loadHead(null); //加载数据

		/* $("#topmenu").ligerMenuBar({
			items : [  {
				text : '高级查询',
				id : 'search',
				click : itemclick
			},  {
				text : '批量标注',
				menu : menu_batch
			} , {
				text : '模版标注',
				menu : menu_template
			}, {
				text : '打印',
				menu : menu_print
			}, {
				text : '导出',
				id : 'export',
				click : itemclick
			}  ]
		}); */

	});
	//查询
	function query() {
		if($("#year_month").val()==""){
			$.ligerDialog.error('会计期间不能为空！');
			return;
		}
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'acc_year',value : $("#year_month").val().substring(0,4)});
		grid.options.parms.push({name : 'acc_month',value : $("#year_month").val().substring(5,7)});
		grid.options.parms.push({name : 'cash_flow_state',value : $("#cash_flow_state").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证编号',
				name : 'vouch_no',width:120,
				align : 'left',render:function(rowdata){
					return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
				}
			}, {
				display : '凭证日期',
				name : 'vouch_date',
				align : 'left',width:120,formatter: "yyyy-MM-dd",
			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',width:200
			}, {
				display : '科目名称',
				name : 'subj_name',
				align : 'left'
			}, {
				display : '借方金额',
				name : 'debit',
				align : 'right',formatter: "###,##0.00",
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.debit, 2, 1);
  				}
			}, {
				display : '贷方金额',
				name : 'credit',
				align : 'right',formatter: "###,##0.00",
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.credit, 2, 1);
  				}
			},{
				display : '现金项目',
				name : 'cash_item_name',
				align : 'left',render:function(rowdata){
					if(rowdata.cash_item_name==null || rowdata.cash_item_name==""){
						return "<a href=javascript:openCash('"+rowdata.vouch_id+"','"+rowdata.vouch_detail_id+"','"+3+"')>标注</a>";	;
					}else{
						return "<a href=javascript:openCash('"+rowdata.vouch_id+"','"+rowdata.vouch_detail_id+"','"+rowdata.cash_item_name.split("@")[1]+"','"+rowdata.cash_item_id+"')>"+rowdata.cash_item_name.split("@")[0]+"</a>";	
					}
					
				}
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAccVouchCashFlow.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true //heightDiff: -10,
			
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item) {
		
		if (item.id) {
			
			switch (item.id) {
			
			case "add":
				
				return;
				
			case "modify":
				
				return;
				
			case "delete":
				
				return;
				
			case "batch":
				
				return;
			case "unbatch":
				
				return;
			case"templateSet":
				$.ligerDialog.open({url: 'queryAccVouchCashFlowTemplate.do?isCheck=false', height: 420,width: 650, title:'模板设置',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.searchAccVouch(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
				return;
			case "Excel":
			case "Word":
			case "PDF":
			case "TXT":
			case "XML":
				$.ligerDialog.waitting('导出中，请稍候...');
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
					if (item.id == "Excel")
						$.ligerDialog.success('导出成功');
					else
						$.ligerDialog.error('导出失败');
				}, 1000);
				return;
			}
		}

	}
	function loadDict() {
		$("#cash_flow_state").ligerComboBox({width:80 });
		//字典下拉框
		$("#year_month").ligerComboBox({
          	url: '../../queryYearMonth.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 80,
          	autocomplete: true,
          	width: 80,
          	onSuccess:function(data){
          		for(var i in data){
          			if(data[i].text =='${yearMonth}'){
          				liger.get("year_month").setValue(data[i].id.split(".")[0]+"."+data[i].id.split(".")[1]);
          				liger.get("year_month").setText(data[i].text);
          			}
          		}
          	}
 		  });
	}

	/**
	 * 打印 打印 预览 设置
	 */
	var menu_print = {
		width : 120,
		items : [ {
			text : '打印',
			id : 'print',
			click : itemclick
		}, {
			text : '预览',
			id : 'view',
			click : itemclick
		}, {
			text : '设置',
			id : 'set',
			click : itemclick
		} ]
	};
	/**
	 * 现金流量标注 批量操作
	 */
	var menu_batch = {
		width : 120,
		items : [ {
			text : '批量标注',
			id : 'batch',
			click : itemclick
		}, {
			text : '批量取消',
			id : 'unbatch',
			click : itemclick
		} ]
	};
	/**
	 * 现金流量标注 模版操作
	 */
	var menu_template = {
		width : 120,
		items : [ {
			text : '模版标注',
			id : 'template',
			click : itemclick
		}, {
			text : '模版设置',
			id : 'templateSet',
			click : itemclick
		} ]
	};
	
	//批量标注
	function batchBz(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return false;
		}
		var checkData = [];
		var vouchData="";
		var cash_dire;
		var old_cash_dire = "";
		var flag = false;
		$(data).each(function() {
			if(this.state == "99"){
				vouchData=vouchData+""+this.vouch_no+"、";
			}else{
				cash_dire = (this.debit != 0 ? "借" : "贷");
				if(old_cash_dire && old_cash_dire != cash_dire){
					flag = true;
					return false;
				}
				old_cash_dire = cash_dire;
				checkData.push({
					vouch_id: this.vouch_id, 
					vouch_detail_id: this.vouch_detail_id
				})
			}
		});
		if(flag){
			$.ligerDialog.warn("批量标注只能勾选发生额方向一直的分录！");
			return false;
		}
		if(vouchData.length>0){
			$.ligerDialog.warn(vouchData.substring(0, vouchData.length-1)+"记账凭证,不能标注！");
			return false;
		}
		$.ligerDialog.open({
			title: '批量标注',
			url: 'AccVouchCashFlowBatch.do?', 
			height: 300, 
			width: 500, 
			data: {checkData: JSON.stringify(checkData), cash_dire: cash_dire == "借" ? 0 : 1},
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, 
			buttons: [ { 
				text: '确定', onclick: function (item, dialog) { dialog.frame.mySave(); },cls:'l-dialog-btn-highlight' 
			}, { 
				text: '取消', onclick: function (item, dialog) { dialog.close(); } 
			} ] 
		});
	}
	acc_cash_item
	//批量取消
	function batchQx(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var vouchData="";
			
			$(data).each(function() {
				if(this.state=="99"){
					vouchData=vouchData+""+this.vouch_no+"、";
				}
				
				ParamVo.push(this.vouch_id+"@"+this.vouch_detail_id);
			});
			
			if(vouchData.length>0){
				
				$.ligerDialog.error(vouchData.substring(0, vouchData.length-1)+"记账凭证,不能取消标注！");
				
				return;
			}
			
			$.ligerDialog.confirm('确定取消标注?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("AccVouchCashFlowUnbatch.do", {
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
	
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	
	//单条标注
	function openCash(vouch_id,vouch_detail_id,cash_dire,cash_item_id){
		
		$.ligerDialog.open({url: 'accVouchCashFlowUpdatePage.do?vouch_id='+vouch_id+'&vouch_detail_id='+vouch_detail_id+'&cash_dire='+cash_dire+'&cash_item_id='+cash_item_id, height: 480,width: 1110, title:'现金流量标注',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.mySave(); },cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

		//004:现金流量与辅助核算一起保存,隐藏取消标注按钮
	  // if($("#para004").val()=='1'){

		//	$.ligerDialog.open({url: 'accVouchCashFlowUpdatePage.do?para004=${para004}&vouch_id='+vouch_id+'&vouch_detail_id='+vouch_detail_id+'&debit='+debit+'&credit='+credit, height: 480,width: 1110, title:'现金流量标注',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.mySave(); },cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });	
		
		//}else{
			
			//$.ligerDialog.open({url: 'accVouchCashFlowUpdatePage.do?para004=${para004}&vouch_id='+vouch_id+'&vouch_detail_id='+vouch_detail_id+'&debit='+debit+'&credit='+credit, height: 480,width: 1110, title:'现金流量标注',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.mySave(); },cls:'l-dialog-btn-highlight' },/* { text: '取消标注', onclick: function (item, dialog) { dialog.frame.myUnbz(); },cls:'l-dialog-btn-highlight' },  */{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
		
//		} 

	}
	
	function printDate(){
		if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
		var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	  	          {"cell":0,"value":"会计期间："+$("#year_month").val(),"colSpan":"5"}
	      		  ]
	      	};
	   		
	   		var printPara={
	   			rowCount:1,
	   			title:'现金流量标注',
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.AccVouchCashFlowService",
				method_name: "queryAccVouchCashFlowPrint",
				bean_name: "accVouchCashFlowService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	   			};
	    	
	   		//执行方法的查询条件
	   		$.each(grid.options.parms,function(i,obj){
	   			printPara[obj.name]=obj.value;
	    	});
	   		
	    	officeGridPrint(printPara);
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
			<td align="left" class="l-table-edit-td"><input  name="year_month" type="text" id="year_month" ltype="text"
				style="width: 160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">标注状态：</td>
			<td align="left" class="l-table-edit-td">
				<select name="cash_flow_state" id="cash_flow_state" >
					<option value=""></option>
					<option value="0">未标注</option>
					<option value="1">已标注</option>
					<option value="2">金额不等</option>
					<option value="3">流入流出不等</option>
				</select>
				</td>
				<td align="left" class="l-table-edit-td">
				</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="hidden" id="para004" value="${para004}"/>
				<input class="liger-button" type="button" accessKey="Q" value="查询（Q）" onclick="query();"/>
				<input class="liger-button" type="button"  value="打 印" onclick="printDate();"/>
				<input class="liger-button" name="batchButton" type="button" accessKey="B" value="批量标注（B）" onclick="batchBz();"/>
				<input class="liger-button" name="batchButton" type="button" accessKey="X" value="批量取消（X）" onclick="batchQx();"/>
			</td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
