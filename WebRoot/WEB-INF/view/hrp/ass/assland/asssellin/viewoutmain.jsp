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
		loadHead(null);
		query();
	});
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
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
		grid.options.parms.push({
			name : 'sell_in_no',
			value : '${sell_in_no}'
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
			                    return formatNumber(item.sell_price,'${ass_05005}',1);
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
					url : 'queryBySellInImportView.do',
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
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
						} ]
					}
				});
		gridManager = $("#maingrid").ligerGetGridManager();
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
			url: 'hrp/ass/assland/asssellin/assSellOutLandViewPage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
