<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	$(function() {

		loadDict();//加载下拉框
		loadHead(null);
	});

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'scheme_code',
			value : liger.get("scheme_code").getValue()
		});
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '病种方案',
				name : 'scheme_code',
				align : 'left',
				textField : 'scheme_name',
				minWidth : 80,
				align : 'left',
			}, {
				display : '病种核算期间',
				name : 'period_code',
				align : 'left',
				minWidth : 80,
				textField : 'period_name',
				minWidth : 80,
				align : 'left',
			}, {
				display : '项目成本方案',
				name : 'plan_code',
				align : 'left',
				minWidth : 80,
				textField : 'plan_name',
				minWidth : 80,
				align : 'left',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../../htc/info/base/queryHtcPlan.do.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
					onSelected : function (data){}
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgChargeCostSchemeRela.do?isCheck=false',
			width : '100%',
			height : '95%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: 30,
			enabledEdit : true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '保存',
					id : 'save',
					click : save,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}, {
					line : true
				}, ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function save() {
		var detailData = {
			detailData : JSON.stringify(gridManager.getData())
		};
		ajaxJsonObjectByUrl("addHtcgChargeCostSchemeRela.do?isCheck=false", detailData,
				function(responseData) {
					if (responseData.state == "true") {
						query();
						is_addRow();
					}
				});
	}

	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(
								  this.group_id + '@' 
							    + this.hos_id + '@'
							    + this.copy_code + '@'
							    + this.acc_year + '@'
							    + this.scheme_code + '@'
							    + this.period_type_code + '@'
							    + this.period_code + '@'
							    + this.plan_code);
					});
			$.ligerDialog.confirm('确定删除?',
							function(yes) {
								if (yes) {ajaxJsonObjectByUrl("deleteHtcgChargeCostSchemeRela.do?isCheck=false",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
													is_addRow();
												}
											});
								}
							});

		}
	}

	function loadDict() {
		//字典下拉框
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true);
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
           <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
            <td align="left" class="l-table-edit-td"><input name="scheme_code" style="width:160px;" type="text" id="scheme_code" ltype="text"  /></td>
            <td align="left"></td>
        </tr> 
	</table>
	<div id="maingrid"></div>
</body>
</html>
