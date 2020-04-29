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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	
	$(function ()
	{
		//加载下拉框
		loadDict()
		//加载数据
		loadHead(null);	
		//加载快捷键
		loadHotkeys();
		//默认隐藏
		$(".set_td").hide();
		
		//绑定监听事件
		$("input[name='store_type']").bind("click", function(){
			if(this.value == 2){
				$(".store_td").hide();
				$(".set_td").show();
			}else{
				$(".store_td").show();
				$(".set_td").hide();
			}
		});
	});
	
	//查询
	function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name: 'begin_in_date', 
			value: $('#begin_in_date').val()
		}); 
		grid.options.parms.push({
			name: 'end_in_date',
			value: $('#end_in_date').val()
		}); 
		
		var store_type = $("input[name='store_type']:checked").val();
		if(store_type == 2){
			grid.options.parms.push({
				name: 'set_id',
				value: liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()}
			);
		}else{
			grid.options.parms.push({
				name : 'store_id',
				value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
			}); 
		}
		grid.options.parms.push({
			name: 'begin_bill_date', 
			value: $('#begin_bill_date').val()
		}); 
		grid.options.parms.push({
			name: 'end_bill_date',
			value: $('#end_bill_date').val()
		}); 
		grid.options.parms.push({
			name : 'bill_state',
			value : liger.get("bill_state").getValue() == null ? "" : liger.get("bill_state").getValue()
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	} 
	
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [{
				display: '入库单号', name: 'in_no', align: 'left', width: '160',
				render : function(rowdata, rowindex, value) {
					if(value == '合计'){
						return value;
					}
					return '<a href=javascript:update_open("'
							+ rowdata.group_id + ','
							+ rowdata.hos_id + ',' 
							+ rowdata.copy_code + ',' 
							+ rowdata.in_id + '")>' 
							+ value + '</a>';
				}
			}, { 
				display: '仓库', name: 'store_name', align: 'left', width: '180'
			}, { 
				display: '制单日期', name: 'in_date', align: 'left', width: '90', 
			}, { 
				display: '入库发票状态', name: 'in_state', align: 'left', width: '90'
			}, { 
				display: '发票号', name: 'bill_no', align: 'left', width: '120'
			}, { 
				display: '发票日期', name: 'bill_date', align: 'left', width: '90', 
			}, { 
				display: '发票状态', name: 'bill_state', align: 'left', width: '90'
			}, { 
				display: '供货单位', name: 'sup_name', align: 'left', width: '200'
			}, { 
				display: '应付金额', name: 'amount_money', align: 'right', width: '100', 
				render : function(rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
				} 
			}, { 
				display: '开票金额', name: 'bill_money', align: 'right', width: '100', 
				render : function(rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
				} 
			}, { 
				display: '已付金额', name: 'payable_money', align: 'right', width: '100', 
				render : function(rowdata, rowindex, value) {
					return formatNumber(value ==null ? 0 : value, '${p04005 }', 1);
				} 
			} ], 
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInBillReport.do?isCheck=true',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [ { 
				text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' 
			}, { 
				line: true 
			}, { 
				text: '打印（<u>P</u>）', id: 'print', click: print, icon: 'print' 
			} ] }, 
		});
		
		gridManager = $("#maingrid").ligerGetGridManager();
	} 
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', print);
	}
	
	//打开修改页面
	function update_open(obj) {
		var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&" 
			+ "copy_code=" + vo[2] + "&" + "in_id=" + vo[3];
		parent.$.ligerDialog.open({
			title : '入库单查询',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/in/updatePage.do?isCheck=false&'+ paras.toString(),
			modal : true, showToggle : false, showMax : true, showMin : true, isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//打印
	function print(){
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		var heads={
			"isAuto":true,//系统默认，页眉显示页码
			"rows": [ {
				"cell":0,"value":"入库日期："
			}, {
				"cell":1,"value":""+liger.get("begin_in_date").getValue()+"至"+liger.get("end_in_date").getValue()
			} ]
		};
		
		//表尾
		var time=new Date();
		var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
		var foots = {
			rows: [ {
				"cell":0,"value":"制表日期:"
			}, {
				"cell":1,"value":date
			} ]
		}; 
		
		var printPara = {
			title: "入库发票信息表",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.mat.service.matpayquery.MatPayQueryService",
			method_name: "printMatInBillReport",
			bean_name: "matPayQueryService",
			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
			foots:JSON.stringify(foots),//表尾打印数据,可以为空
		};
		
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
	
	//字典下拉框
	function loadDict(){
		//仓库
		autocompleteObj({
			id:  '#store_code',                   
			urlStr: 	"../queryMatStoreDictDate.do?isCheck=false", 
			valueField:  'id',            
			textField:    'text' ,            
			autocomplete: true,			
			highLight: true,
			parmsStr: {read_or_write: 1},
			defaultSelect:  false,
			boxwidth: 240,
		});
		//虚仓
		autocompleteObj({
			id:  '#set_code',                   
			urlStr: 	"../queryMatVirStore.do?isCheck=false", 
			valueField:  'id',            
			textField:    'text' ,            
			autocomplete: true,			
			highLight: true,
			defaultSelect:  false,
			boxwidth: 240,
		});
		//供应商
		autocompleteObj({
			id:  '#sup_code',                   
			urlStr: 	"../queryHosSupDict.do?isCheck=false", 
			valueField:  'id',            
			textField:    'text' ,            
			autocomplete: true,			
			highLight: true,
			defaultSelect:  false,
			boxwidth: 240,
		});
		autoCompleteByData("#bill_state", [{id: "0", text: "货到票未到"}, {id: "1", text: "货票同到"}], "id", "text", true, true);
		//日期 默认值
		autodate("#begin_in_date","yyyy-MM-dd","month_first");
		autodate("#end_in_date","yyyy-MM-dd","month_last");
		
		$("#begin_in_date").ligerTextBox({width:100});
		$("#end_in_date").ligerTextBox({width:100});
		$("#begin_bill_date").ligerTextBox({width:100});
		$("#end_bill_date").ligerTextBox({width:100});
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr></tr> 
		<tr>
			<td align="right" class="l-table-edit-td">
				制单日期：
			</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td">至</td>
						<td>
							<input class="Wdate" name="end_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td">
				查询方式：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_type" type="radio" value="1" checked/>&nbsp;&nbsp;按仓库
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="store_type" type="radio" value="2"/>&nbsp;&nbsp;按虚仓
			</td>
			
			<td align="right" class="l-table-edit-td store_td">
				仓库：
			</td>
			<td align="left" class="l-table-edit-td store_td">
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
			</td>
			
			<td align="right" class="l-table-edit-td set_td">
				虚仓：
			</td>
			<td align="left" class="l-table-edit-td set_td">
				<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false}" />
			</td>
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td">
				发票日期：
			</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_bill_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td">至</td>
						<td>
							<input class="Wdate" name="end_date" id="end_bill_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td">
				发票状态：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="bill_state" type="text" id="bill_state" ltype="text" validate="{required:false}" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				供应商：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>