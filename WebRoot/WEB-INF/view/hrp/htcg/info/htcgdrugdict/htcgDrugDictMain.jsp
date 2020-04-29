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
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'drug_code',
			value : $("#drug_code").val()
		});
		grid.options.parms.push({
			name : 'drug_type_code',
			value : liger.get("drug_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'fac_code',
			value : liger.get("fac_code").getValue()
		});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '药品编号',
										name : 'drug_code',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											return "<a href='#' onclick=\"openUpdate('"
													+ rowdata.group_id + "|"
						                            + rowdata.hos_id + "|"
						                            + rowdata.copy_code + "|"
													+ rowdata.drug_code
													+ "');\" >"
													+ rowdata.drug_code
													+ "</a>";
										}
									},
									{
										display : '药品名称',
										name : 'drug_name',
										align : 'left'
									},
									{
										display : '药品类别',
										name : 'drug_type_name',
										align : 'left'
									},
									{
										display : '规格',
										name : 'mode_code',
										align : 'left'
									},
									{
										display : '计量单位',
										name : 'unit_code',
										align : 'left'
									},
									{
										display : '单价',
										name : 'price',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.price, 2, 1);
										}
									}, {
										display : '生产厂商',
										name : 'fac_name',
										align : 'left'
									}, {
										display : '是否停用',
										name : 'is_stop',
										align : 'left',
										render : function(rowdata, rowindex, value) {
											return rowdata.is_stop == 0 ? "否" : "是"
										}
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryHtcgDrugDict.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							delayLoad : true,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,  
							toolbar : {
								items : [ {
									text : '查询',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}, {
									text : '添加',
									id : 'add',
									click : add_open,
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
								}, {
									text : '导入',
									id : 'import',
									click : imp,
									icon : 'up'
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open(){
		$.ligerDialog.open({
			url : 'htcgDrugDictAddPage.do?isCheck=false',
			height : 400,
			width : 400,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHtcDrugDict();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
						this.group_id + "@"
						+this.hos_id + "@"
						+this.copy_code + "@"
						+this.drug_code
						);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgDrugDict.do", {
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
	
	function imp() {
		var para = {
			"column" : [ {
				"name" : "drug_code",
				"display" : "药品编码",
				"width" : "150",
				"require" : true
			}, {
				"name" : "drug_name",
				"display" : "药品名称",
				"width" : "200",
				"require" : true
			}, {
				"name" : "drug_type_code",
				"display" : "药品类别编码",
				"width" : "150",
				"require" : true
			}, {
				"name" : "drug_type_name",
				"display" : "药品类别名称",
				"width" : "200",
				"require" : true
			}, {
				"name" : "mode_code",
				"display" : "规格",
				"width" : "100",
				"require" : true
			}, {
				"name" : "unit_code",
				"display" : "计量单位",
				"width" : "100",
				"require" : true
			}, {
				"name" : "price",
				"display" : "单价",
				"width" : "100",
				"require" : true
			}, {
				"name" : "fac_code",
				"display" : "生产厂商编码",
				"width" : "200",
				"require" : true
			}, {
				"name" : "fac_name",
				"display" : "生产厂商名称",
				"width" : "200",
				"require" : true
			} ]
		};
		importSpreadView("hrp/htcg/info/htcgdrugdict/importHtcgDrugDict.do?isCheck=false",para);

	}
	function openUpdate(obj) {
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1]
		+ "&" + "copy_code=" + vo[2] + "&" +  "drug_code=" + vo[3]
		
		$.ligerDialog.open({
			url : 'htcgDrugDictUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height : 400,
			width : 400,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHtcgDrugDict();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
	function loadDict() {
		$("#drug_code").ligerTextBox({
			width : 160
		});
		$("#drug_type_code").ligerTextBox({
			width : 160
		});
		$("#fac_code").ligerTextBox({
			width : 160
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">药品：</td>
			<td align="left" class="l-table-edit-td"><input name="drug_code"
				type="text" id="drug_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">药品类别：</td>
			<td align="left" class="l-table-edit-td"><input
				name="drug_type_code" type="text" id="drug_type_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">生产厂家：</td>
			<td align="left" class="l-table-edit-td"><input
				name="factory_code" type="text" id="fac_code" ltype="text"/></td>
			<td align="left"></td>
		</tr>
	</table>
<div id="maingrid"></div>
</body>
</html>
