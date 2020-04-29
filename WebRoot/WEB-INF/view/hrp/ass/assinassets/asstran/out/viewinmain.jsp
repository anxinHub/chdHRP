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
		//加载数据
		loadHead(null);

		$("#topmenu").ligerToolBar({ items: [
										     
										     {
													text : '关闭',
													id : 'close',
													click : this_close,
													icon : 'candle'
											 }
		                                     
		                                 ]
		});
		 query();
	});
	
	function this_close() {
		frameElement.dialog.close();
	}
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({
			name : 'out_no',
			value : '${out_no}'
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	

	function loadHead() {
			grid = $("#maingrid").ligerGrid(
					{
						columns : [  {
							display : '入库单号',
							name : 'ass_in_no',
							align : 'left',
							width : 120,
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.note == "合计"){
									return '';
								}
								return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.ass_in_no  +"')>"+rowdata.ass_in_no+"</a>";
							}, frozen: true
						},{ display: '发票号',
							name: 'invoice_no', 
							align: 'left',
							width : 150,
							frozen: true
				 		},
	 					{
							display : '摘要',
							name : 'note',
							align : 'left',
							width : 150,
							frozen: true
						},{
							display : '仓库',
							name : 'store_name',
							align : 'left',
							width : 260, frozen: true
						},{
							display : '供应商',
							name : 'ven_name',
							align : 'left',
							width : 260
						}, {
							display : '领用科室',
							name : 'dept_name',
							align : 'left',
							width : 140
						}, {
							display : '入库金额',
							name : 'in_money',
							align : 'right',
							width : 80,
							render: function(item)
				            {
				                    return formatNumber(item.in_money,'${ass_05005}',1);
				            }
						}, {
							display : '采购员',
							name : 'purc_emp_name',
							align : 'left',
							width : 110
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
							name : 'confirm_emp_name',
							align : 'left',
							width : 100
						}, {
							display : '入库确认日期',
							name : 'in_date',
							align : 'left',
							width : 100
						}, {
							display : '状态',
							name : 'state_name',
							align : 'left',
							width : 100
						},{
							display : '用途',
							name : 'ass_purpose_name',
							align : 'left',
							width : 100
						}],
						dataAction : 'server',
						dataType : 'server',
						usePager : true,
						url : 'queryInMainByOutNo.do?isCheck=false',
						width : '100%',
						height : '100%',
						checkbox : false,
						rownumbers : true,
						delayLoad :true,
						checkBoxDisplay : isCheckDisplay,
						selectRowButtonOnly : true,//heightDiff: -10,
						onDblClickRow : function(rowdata, rowindex, value) {
							openUpdate(rowdata.group_id + "|" + rowdata.hos_id
									+ "|" + rowdata.copy_code + "|"
									+ rowdata.ass_in_no);
						}
					});
			
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
	



	function openUpdate(obj) {
		var vo = obj.split("|");
		if("null"==vo[3] || "undefined"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_in_no=" + vo[3];

		parent.$.ligerDialog.open({
			title: '采购入库查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assinassets/assin/assInMainInassetsUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu" style="background:#FFFFFF; color:#FFFFFF; border:1px solid #A4D3EE" ></div>
	<div id="maingrid"></div>
</body>
</html>
