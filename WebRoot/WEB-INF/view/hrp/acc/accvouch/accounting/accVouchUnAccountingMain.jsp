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
	$(function() {
		loadDict();
		loadHead(null); //加载数据
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '会计期间',
								name : 'year_month',
								align : 'left',render:function(rowdata){
									return rowdata.acc_year+"."+rowdata.acc_month;	
								}
							},
							{
								display : '凭证类型',
								name : 'vouch_type_name',
								align : 'left'
							},
							{
								display : '凭证编号',
								name : 'vouch_no',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.vouch_type_short + "-"
											+ rowdata.vouch_no;
								}
							}, {
								display : '借方金额',
								name : 'debit',
								align : 'right',
								render : function(rowdata, rowindex, value) {
		          					return formatNumber(rowdata.debit, 2, 1);
		          				}
							}, {
								display : '贷方金额',
								name : 'credit',
								align : 'right',
								render : function(rowdata, rowindex, value) {
		          					return formatNumber(rowdata.credit, 2, 1);
		          				}
							}, {
								display : '制单人',
								name : 'create_name',
								align : 'left'
							}, {
								display : '出纳签字人',
								name : 'cash_name',
								align : 'left'
							}, {
								display : '审核人',
								name : 'audit_name',
								align : 'left'
							}, {
								display : '备注',
								name : 'note',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAccVouchUnAccounting.do?isCheck=false&acc_year='+'${acc_year}'+'&group_id='+'${group_id}'
					+'&hos_id='+'${hos_id}'+'&copy_code='+'${copy_code}'+'&vouch_type_code='+'${vouch_type_code}',
					width : '100%',
					height : '100%',
					checkbox : false,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
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
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.vouch_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccVouch.do", {
								ParamVo : ParamVo
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
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
	}
	function print() {
	}
	function acc_export() {
	}
	function acc_jz() {
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
				this.vouch_id+"@"+this.group_id+"@"+this.hos_id+"@"+this.copy_code+"@"+this.acc_year+"@"+this.acc_month)
			});
			$.ligerDialog.confirm('确定记账?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("accountingAccVouch.do?isCheck=false&vouchid="+ParamVo, 
							{}, function(responseData) {
						if (responseData.state == "true") {
							query();
							parent.query();
						}
					});
				}
			});
		}
		
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
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<div id="maingrid"></div>
</body>
</html>
