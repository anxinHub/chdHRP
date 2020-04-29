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
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);

		$("#bus_type_code").ligerTextBox({
			width : 160
		});
		$("#in_out_type").ligerComboBox({
			width : 160
		});
		$("#is_stop").ligerComboBox({
			width : 160
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'bus_type_code',
			value : $("#bus_type_code").val()
		});
		grid.options.parms.push({
			name : 'in_out_type',
			value : liger.get("in_out_type").getValue()
		});
		grid.options.parms.push({
			name : 'is_stop',
			value : liger.get("is_stop").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '业务类型编码',
				name : 'bus_type_code',
				align : 'left'
			}, {
				display : '业务类型名称',
				name : 'bus_type_name',
				align : 'left'
			},

			{
				display : '类型属性',
				name : 'in_out_type',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					if (rowdata.in_out_type == 0) {
						return "增加";
					} else {
						return "减少"
					}
				}
			}, {
				display : '是否停用',
				name : 'is_stop',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_stop == 0) {
						return "否";
					} else {
						return "是"
					}
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryAssBusTypeDict.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : false,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {
		//字典下拉框
	$('#in_out_type').ligerComboBox({
			data:[{id:1,text:'减少'},{id:0,text:'增加'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});
	$('#is_stop').ligerComboBox({
		data:[{id:1,text:'是'},{id:0,text:'否'}],
		valueField: 'id',
        textField: 'text',
		cancelable:true
	})
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">业务类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="bus_type_code" type="text" id="bus_type_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">业务性质：
			</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select
				id="in_out_type" name="in_out_type" style="width: 135px;">
					<option value="">请选择</option>
					<option value="0">增加</option>
					<option value="1">减少</option>
			</select> -->
			<input name="in_out_type" type="text" id="in_out_type"  />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="is_stop" type="text" id="is_stop"  />
			</td>
			<td align="left"></td>

		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
