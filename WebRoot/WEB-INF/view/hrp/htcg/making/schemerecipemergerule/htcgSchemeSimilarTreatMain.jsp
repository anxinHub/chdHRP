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
	var scheme_code = '${scheme_code}'
	var drgs_code = '${drgs_code}'
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
			name : 'scheme_code',
			value : liger.get("scheme_code").getValue()
		});
		grid.options.parms.push({
			name : 'drgs_code',
			value : liger.get("drgs_code").getValue()
		});
		grid.options.parms.push({
			name : 'charge_nature_code',
			value : liger.get("charge_nature_code").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
							columns : [ {
								display : '方案名称',
								name : 'scheme_name',
								align : 'left',
								render : function(rowdata, rowindex,value) {
									return "<a href='#' onclick=\"openUpdate('"
											+ rowdata.group_id + "|"
				                            + rowdata.hos_id + "|"
				                            + rowdata.copy_code + "|"
											+ rowdata.scheme_code + "|"
											+ rowdata.drgs_code+ "|"
											+ rowdata.charge_nature_code + "|"
											+ rowdata.charge_code + "');\" >"
											+ rowdata.scheme_name
											+ "</a>";
								  }
							    }, {
										display : '病种编码',
										name : 'drgs_code',
										align : 'left',
										
									}, {
										display : '病种名称',
										name : 'drgs_name',
										align : 'left'
									}, {
										display : '项目性质',
										name : 'charge_nature_name',
										align : 'left'
									}, {
										display : '标准项目编码',
										name : 'charge_code',
										align : 'left'
									}, {
										display : '标准项目名称',
										name : 'charge_name',
										align : 'left'
									}, {
										display : '相似项目编码',
										name : 'similar_code',
										align : 'left'
									}, {
										display : '相似项目名称',
										name : 'similar_name',
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryHtcgSchemeSimilarTreat.do?isCheck=false',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							delayLoad : true,
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
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open(){
		$.ligerDialog.open({
			url : 'htcgSchemeSimilarTreatAddPage.do?isCheck=false&scheme_code='+scheme_code+'&drgs_code='+drgs_code,
			height : 350,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveSchemeSimilarTreat();
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
			$(data).each(
					function() {
						ParamVo.push(
								+  this.group_id + "@"
								+ this.hos_id + "@"
								+ this.copy_code + "@"
								+ this.scheme_code + "@"
								+ this.drgs_code + "@"
								+ this.charge_nature_code + "@"
								+ this.charge_code);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?',
							function(yes) {
								if (yes) {ajaxJsonObjectByUrl("deleteHtcgSchemeSimilarTreat.do?isCheck=false",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
												}
											});
								}
							});
		}
	}

	
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1]
		+ "&" + "copy_code=" + vo[2] + "&" + "scheme_code=" + vo[3]+ "&" + "drgs_code=" + vo[4]
		+ "&" +  "charge_nature_code=" + vo[5]+ "&" + "charge_code=" + vo[6]
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'htcgSchemeSimilarTreatUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height : 350,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveSchemeSimilarTreat();
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
		//字典下拉框
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true,{scheme_code:scheme_code},true);
		autocomplete("#drgs_code","../../base/queryHtcgDrgsDict.do?isCheck=false", "id", "text", true,true,{drgs_code:drgs_code},true);
		autocomplete("#charge_nature_code","../../base/queryHtcgChargeNatureDict.do?isCheck=false", "id", "text",true, true);
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案：</td>
			<td align="left" class="l-table-edit-td"><input
				name="scheme_code" type="text" id="scheme_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病种名称：</td>
			<td align="left" class="l-table-edit-td"><input name="drgs_code"
				type="text" id="drgs_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目性质：</td>
			<td align="left" class="l-table-edit-td"><input 
			name="charge_nature_code" type="text" id="charge_nature_code"ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
