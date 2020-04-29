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
					{display: '流水号', name: 'apply_code', align: 'left' ,width:'10%',
						render:function(rowData){
							return "<a href=javascript:openUpdate('"+rowData.apply_code+"')>"+rowData.apply_code+"</a>";
							}	
					},
					{display: '单据名称', name: 'apply_name', align: 'left' ,width:'18%'},
					{display: '待摊类别', name: 'type_name', align: 'left' ,width:'10%'},
					{display: '合同号', name: 'pact_code', align: 'left' ,width:'10%'},
					{display: '摊销年限(年)', name: 'amortize_year', align: 'left' ,width:'8%'},
					{display: '已摊销期间(月)', name: 'amortized', align: 'left' ,width:'8%'},
					{display: '原值', name: 'origin_value', align: 'right' ,width:'10%',
						render: function(rowData){
							return formatNumber(rowData.origin_value,2,1);
						}	
					},
					{display: '累计摊销值', name: 'amortized_value', align: 'right' ,width:'10%',
						render: function(rowData){
							return formatNumber(rowData.amortized_value,2,1);
						}	
					},
					{display: '供应商', name: 'sup_name', align: 'left' ,width:'16%'},
					{display: '状态', name: 'state', align: 'right' ,width:'8%',
						render: function(rowData){
							if(rowData.state == 0){
								return "新建";
							}else {
								return "审核";
							}
						}	
					},
					{display: '摊销状态', name: 'amortize_state', align: 'right' ,width:'8%',
						render: function(rowData){
							if(rowData.amortize_state == 0){
								return "未摊销";
							}else if(rowData.amortize_state == 1){
								return "正在摊销";
							}else{
								return "摊销完成";
							}
						}	
					},
					{display: '备注', name: 'note', align: 'left' ,width:'18%'},
				],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryAmortizeInfo.do?isCheck=false',
				width: '100%', height: '100%', checkbox: true,rownumbers:true,
				delayLoad: true,//初始化加载，默认false
				selectRowButtonOnly:true,//heightDiff: -10,
				toolbar: { items: [
				   				{ text: '查询', id:'search', click: query, icon:'search' },
				   				{ line:true },
				   				{ text: '添加', id:'add', click: add, icon:'add' },
				   				{ line:true },
				   				{ text: '删除', id:'delete',  click: remove, icon:'delete' },
				   				{ line:true },
				   				{ text: '审核', id:'check',  click: check, icon:'cashier' },
				   				{ line:true },
				   				{ text: '取消审核', id:'uncheck',  click: uncheck, icon:'uncashier' },
				   				{ line:true },
				   				{ text: '期末摊销', id:'config',  click: amortize, icon:'config' },
				   				{ line:true },
								{ text: '打印', id:'print', click: printDate,icon:'print'}
				   			]}

			});

	        gridManager = $("#maingrid").ligerGetGridManager();
	    }
	 
	 function add(){
		parent.$.ligerDialog.open({
			title: '单据维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/acc/autovouch/amortization/amortizeAddPage.do?isCheck=false',
			modal: true, showToggle: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	 
	 function check(){
		 var data = gridManager.getSelectedRows();
			if(data.length == 0){
				$.ligerDialog.error("请选择要保存的数据！");
				return;
			}
			
			 var param =[];
	         $(data).each(function () {
	        	 if(this.state == 0){
		             param.push(this.apply_code);
	        	 }
	         });
	         if(param.length == 0){
	        	 $.ligerDialog.error("无可审核数据！");
				return;
	         }
	         
	         ajaxJsonObjectByUrl("checkAmortizeList.do?isCheck=false",{apply_codes : JSON.stringify(param),state : 1},function(responseData){
	             if(responseData.state=="true"){
	            	 query();
	             }
	         });
	 }
	 
	 function uncheck(){
		 var data = gridManager.getSelectedRows();
			if(data.length == 0){
				$.ligerDialog.error("请选择要保存的数据！");
				return;
			}
			
			 var param =[];
	         $(data).each(function () {
	        	 if(this.state == 1){
	             	param.push(this.apply_code);
	        	 }
	         });
	         if(param.length == 0){
	        	 $.ligerDialog.error("无可消审数据！");
				return;
	         }
	         ajaxJsonObjectByUrl("queryAmortizeHistoryCount.do?isCheck=false",{apply_codes : JSON.stringify(param)},function(responseData){
	             if(responseData.state=="true"){
	             	if(responseData.have_data == "true"){
	 					$.ligerDialog.confirm('已存在摊销记录，是否要清空历史数据?', function (yes){
	 						if(yes){
	 							 ajaxJsonObjectByUrl("checkAmortizeList.do?isCheck=false",{apply_codes : JSON.stringify(param),state : 0},function(responseData){
	 					             if(responseData.state=="true"){
	 					            	 query();
	 					             }
	 					         });
	 						}
	 					})
	             	}else{
	             		 ajaxJsonObjectByUrl("checkAmortizeList.do?isCheck=false",{apply_codes : JSON.stringify(param),state : 0},function(responseData){
	        	             if(responseData.state=="true"){
	        	            	 query();
	        	             }
	        	         });
	             	}
	             }
	         });
	 		
	 }
	 
	 function amortize(){
		 ajaxJsonObjectByUrl("setAmortize.do",{},function(responseData){
             if(responseData.state=="true"){
            	 query();
             }
         });
	 }

	 function openUpdate(apply_code){
		 parent.$.ligerDialog.open({
				title: '单据维护',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/acc/autovouch/amortization/amortizeUpdatePage.do?isCheck=false&apply_code='+apply_code,
				modal: true, showToggle: false, isResize: true,slide:false,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	 }
	 
	 function remove(){
		 var data = gridManager.getSelectedRows();
			if(data.length == 0){
				$.ligerDialog.error("请选择要保存的数据！");
				return;
			}
			
			 var param =[];
	         $(data).each(function () {
	        	 if(this.state == 0){
		             param.push(this.apply_code);
	        	 }
	         });
	         
	         if(param.length == 0){
	        	 $.ligerDialog.error("暂无可删除的数据！");
				 return;
	         }
	         
	         ajaxJsonObjectByUrl("deleteAmortizeList.do",{deleteList : JSON.stringify(param)},function(responseData){
	             if(responseData.state=="true"){
	            	 query();
	             }
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
			title : '待摊费用',
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeInfoService",
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
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">待摊类别：</td>
			<td align="left" class="l-table-edit-td"><input id="type_code" name="type_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">供应商：</td>
			<td align="left" class="l-table-edit-td"><input id="sup_id" name="sup_id" /></td>	
				
		</tr>
	</table>
	<div id="maingrid" style="margin-top: 10px"></div>
</body>
</html>
