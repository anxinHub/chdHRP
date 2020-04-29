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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#audit_date_beg").ligerTextBox({
			width : 90
		});
		$("#audit_date_end").ligerTextBox({
			width : 90
		});
		$("#create_date_beg").ligerTextBox({
			width : 90
		});
		$("#create_date_end").ligerTextBox({
			width : 90
		});
		$("#state").ligerComboBox({
			width : 160
		});
		$("#out_store_id").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'out_store_id',
			value : '${out_store_id}'
		});
		grid.options.parms.push({
			name : 'out_store_no',
			value : '${out_store_no}'
		});
		
		grid.options.parms.push({
			name : 'out_group_id',
			value : '${out_group_id}'
		});
		grid.options.parms.push({
			name : 'out_hos_id',
			value : '${out_hos_id}'
		});
		
		if('${bus_type_code}' == '13'){
			grid.options.parms.push({
				name : 'bus_type_code',
				value : '12'
			});
		}else if('${bus_type_code}' == '15'){
			grid.options.parms.push({
				name : 'bus_type_code',
				value : '14'
			});
		}
		
		grid.options.parms.push({
			name : 'create_emp',
			value : liger.get("create_emp").getValue()
		});
		
		grid.options.parms.push({
			name : 'create_date_beg',
			value : $("#create_date_beg").val()
		});
		grid.options.parms.push({
			name : 'create_date_end',
			value : $("#create_date_end").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : $("#state").val()
		});
		grid.options.parms.push({
			name : 'audit_date_beg',
			value : $("#audit_date_beg").val()
		});
		grid.options.parms.push({
			name : 'audit_date_end',
			value : $("#audit_date_end").val()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [  {
						display : '调拨单号',
						name : 'sell_out_no',
						align : 'left',
						width : 120,
						render : function(rowdata, rowindex,
								value) {
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.sell_out_no  +"')>"+rowdata.sell_out_no+"</a>";
						}, frozen: true
					}, {
						display : '摘要',
						name : 'note',
						align : 'left',
						width : 150,
						frozen: true
					} , {
						display : '调出仓库',
						name : 'out_store_name',
						align : 'left',
						width : 200, frozen: true
					}, {
						display : '调入集团',
						name : 'in_group_name',
						align : 'left',
						width : 140
					}, {
						display : '调入单位',
						name : 'in_hos_name',
						align : 'left',
						width : 140
					} , {
						display : '调入仓库',
						name : 'in_store_name',
						align : 'left',
						width : 200
					} , {
						display : '业务类型',
						name : 'bus_type_name',
						align : 'left',
						width : 200
					}, {
						display : '资产原值',
						name : 'price',
						align : 'right',
						editor : {
							type : 'numberbox'
						},
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.price,'${ass_05006}',1);
			            }
					}, {
						display : '累计折旧',
						name : 'add_depre',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.add_depre,'${ass_05005}',1);
			            }
					}, {
						display : '资产净值',
						name : 'cur_money',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.cur_money,'${ass_05006}',1);
			            }
					}, {
						display : '预留残值',
						name : 'fore_money',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.fore_money,'${ass_05006}',1);
			            }
					}, {
						display : '调拨价格',
						name : 'sell_price',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.sell_price,'${ass_05006}',1);
			            }
					}, {
						display : '应缴税费',
						name : 'dispose_tax',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.dispose_tax,'${ass_05005}',1);
			            }
					}, {
						display : '计税收入',
						name : 'dispose_income',
						align : 'right',
						width : 80,
						render: function(item)
			            {
			                    return formatNumber(item.dispose_income,'${ass_05005}',1);
			            }
					}, {
						display : '制单人',
						name : 'create_emp_name',
						align : 'left',
						width : 100
					}, {
						display : '制单日期',
						name : 'create_date',
						align : 'left',
						width : 100
					}, {
						display : '确认人',
						name : 'audit_emp_name',
						align : 'left',
						width : 100
					}, {
						display : '入库确认日期',
						name : 'audit_date',
						align : 'left',
						width : 100
					}, {
						display : '状态',
						name : 'state_name',
						align : 'left',
						width : 100
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryBySellInImport.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad :true,
					alternatingRow : true,
					 scroll: true,
                     frozen:true,
                     alternatingRow: false,
                     scrollToPage: true,
                     scrollToAppend: true,
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.sell_out_no);
					},
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '生成入库单',
							id : 'save',
							click : save,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
						} ]
					}
				});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function save(){
		gridManager.endEdit();
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var sell_out_nos = [];
			var out_copy_codes = [];
			$.each(data,function(){
				sell_out_nos.push("'"+this.sell_out_no+"'");
				out_copy_codes.push("'"+this.copy_code+"'");
			});
			$.post("initAssSellInGeneral.do?isCheck=false",
					{
					 'sell_out_nos':sell_out_nos.toString(),
					 'out_copy_codes':out_copy_codes.toString(),
					 'create_date':'${create_date}',
					 'in_store_id':'${in_store_id}',
					 'in_store_no':'${in_store_no}',
					 'out_group_id':'${out_group_id}',
					 'out_hos_id':'${out_hos_id}',
					 'out_store_id':'${out_store_id}',
					 'out_store_no':'${out_store_no}',
					 'note':'${note}',
					 'bus_type_code':'${bus_type_code}'},
					 function(data){
						parent.parentFrameUse().query();
						parent.parentFrameUse().openUpdate(data.update_para);
						parent.this_close();
						this_close();
			},"json");
			
		}
	}
	function this_close() {
		frameElement.dialog.close();
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "sell_out_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '资产调拨出库查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'assSellOutGeneralViewPage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	function loadDict() {
		
		autocomplete("#out_store_id", "../../queryHosStoreDict.do?naturs_code=03&isCheck=false","id", "text",true,true,null,false,null,"300");
		
		liger.get("out_store_id").setValue("${out_store_id}@${out_store_no}");
		liger.get("out_store_id").setText("${out_store_code} ${out_store_name}");
    	
		autocomplete("#create_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", false, false, false, false);
	
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" id="table1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" width="7%"><input
				name="create_date_beg" type="text" id="create_date_beg"
				  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left">至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input name="create_emp"
				type="text" id="create_emp" 
				 /></td>	 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">确认日期：</td>
			<td align="left" ><input name="audit_date_beg"
				type="text" id="audit_date_beg" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left">至：</td>
			<td align="left"><input name="audit_date_end" type="text"
				id="audit_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">调出仓库：</td>
			<td align="left"  class="l-table-edit-td"><input name="out_store_id"
				type="text" id="out_store_id" 
				 /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
