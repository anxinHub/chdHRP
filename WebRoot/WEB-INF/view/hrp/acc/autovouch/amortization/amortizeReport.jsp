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
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css"/>
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	$(function() {
		loadDict();//加载下拉框
		loadHead();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;

		grid.options.parms.push({name : 'type_code',value : liger.get("type_code").getValue()});
		grid.options.parms.push({name : 'pact_code',value : liger.get("pact_code").getValue()});
		grid.options.parms.push({name : 'apply_code',value : liger.get("apply_code").getValue()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_id").getValue().split(".")[0]});
		
		//加载查询条件
		grid.loadData(grid.where);
	}


	function loadDict() {

		$("#apply_code").ligerTextBox({width : 160});
		$("#pact_code").ligerTextBox({width : 160});
		autocomplete("#sup_id", "../../../sys/querySupDictDict.do?isCheck=false", "id", "text", true, true, null, null, null, "220");
		autocomplete("#type_code", "../amortizetype/amortizeTypeSelect.do?isCheck=false", "id", "text", true, true, null, null, null, "220");
	}
	
	 function loadHead(){
	    	grid = $("#maingrid").ligerGrid({
				columns: [
					{display: '流水号', name: 'apply_code', align: 'left' ,
						render:function(rowData){
							return "<a href=javascript:openUpdate('"+rowData.apply_code+"')>"+rowData.apply_code+"</a>";
							}	
					},
					{display: '单据名称', name: 'apply_name', align: 'left' },
					{display: '代摊类别', name: 'type_name', align: 'left' },
					{display: '合同号', name: 'pact_code', align: 'left' },
					{display: '摊销年限', name: 'amortize_year', align: 'left' },
					{display: '原值', name: 'origin_value', align: 'left' },
					{display: '累计摊销值', name: 'amortized_value', align: 'left' },
					{display: '供应商', name: 'sup_id', align: 'left' },
					{display: '备注', name: 'note', align: 'left' },
				],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryAmortizeReport.do?isCheck=false',
				width: '100%', height: '100%', checkbox: true,rownumbers:true,
				delayLoad: true,//初始化加载，默认false
				selectRowButtonOnly:true,//heightDiff: -10,
				toolbar: { items: [
				   				{ text: '查询', id:'search', click: query, icon:'search' },
				   				{ line:true },
								{ text: '打印', id:'print', click: printDate,icon:'print'}
				   			]}

			});

	        gridManager = $("#maingrid").ligerGetGridManager();
	    }
	 

	 function openUpdate(apply_code){
		 parent.$.ligerDialog.open({
				title: '单据维护',
				height: $(window).height(),
				width: $(window).width()*0.9,
				url: 'hrp/acc/autovouch/amortization/amortizeUpdatePage.do?isCheck=false&apply_code='+apply_code,
				modal: true, showToggle: false, isResize: true,slide:false,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	 }
	 
	 
	function printDate() {
		if (grid.getData().length == 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		var heads = {
			"isAuto": true//false 默认true，页眉右上角默认显示页码
		};
		var printPara = {
			rowCount : 1,
			title : '代摊费用',
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.acc.service.autovouch.AccAmortizeInfoService",
			method_name : "queryAmortizeInfoPrint",
			bean_name : "accAmortizeInfoService",
			heads : JSON.stringify(heads)
		//表头需要打印的查询条件,可以为空
		};
		//执行方法的查询条件
		$.each(grid.options.parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});
		officeGridPrint(printPara);
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">流水号：</td>
			<td align="left" class="l-table-edit-td"><input id="apply_code" name="apply_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">合同号：</td>
			<td align="left" class="l-table-edit-td"><input id="pact_code" name="pact_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">代摊类别：</td>
			<td align="left" class="l-table-edit-td"><input id="type_code" name="type_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">供应商：</td>
			<td align="left" class="l-table-edit-td"><input id="sup_id" name="sup_id" /></td>	
				
		</tr>
	</table>
	<div id="maingrid" style="margin-top: 10px"></div>
</body>
</html>
