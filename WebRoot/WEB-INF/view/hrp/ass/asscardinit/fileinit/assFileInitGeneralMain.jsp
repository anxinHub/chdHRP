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
	var ass_card_no = '${ass_card_no}';
	
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'ass_nature',
			value : '${ass_nature}'
		});
		grid.options.parms.push({
			name : 'ass_card_no',
			value : ass_card_no
		});
		grid.loadData(grid.where);
	}
	function setAssCardNo(no) {
		ass_card_no = no;
		query();
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '文档编号',
										name : 'file_code',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											return "<a href=javascript:openUpdate('"
													+ rowdata.group_id
													+ "|"
													+ rowdata.hos_id
													+ "|"
													+ rowdata.copy_code
													+ "|"
													+ rowdata.ass_card_no
													+ "|"
													+ rowdata.file_code
													+ "')>"
													+ rowdata.file_code
													+ "</a>";
										}
									},
									{
										display : '文档名称',
										name : 'file_name',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											return "<a href=javascript:down('"
													+ rowdata.file_url + "')>"
													+ rowdata.file_name
													+ "</a>";
										}
									}, {
										display : '文档类别',
										name : 'equi_usage_code',
										align : 'left'
									}, {
										display : '存档位置',
										name : 'location',
										align : 'left'
									}, {
										display : '是否停用',
										name : 'is_stop',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.is_stop == 0){
												return '否';
											}else{
												return '是'
											}
										}
									}  ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryAssFileInit.do',
							width : '100%',
							height : '90%',
							checkbox : true,
							rownumbers : true,
							delayLoad : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							scroll : true,
							frozen : true,
							alternatingRow : false,
							scrollToPage : true,
							scrollToAppend : true,
							toolbar : {
								items : [ {
									text : '添加',
									id : 'add',
									click : open_add,
									icon : 'add'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'delete',
									click : remove,
									icon : 'delete'
								} ]
							},
							onDblClickRow : function(rowdata, rowindex, value) {
								openUpdate(rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.ass_card_no + "|"
										+ rowdata.file_code);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function openUpdate(obj) {
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "file_code=" + vo[4] + "&ass_nature=${ass_nature}";
		parent.$.ligerDialog.open({
			url : 'assFileInitUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height : 350,
			width : 600,
			title : '资产文档修改',
			modal : false,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			parentframename : window.name,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssFile();
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

	function open_add() {
		if(ass_card_no == '0'){
			parent.$.ligerDialog.warn("请先保存卡片");
    		return;
    	}
		parent.$.ligerDialog
				.open({
					title : '资产文档添加',
					height : 350,
					width : 600,
					url : 'assFileInitAddPage.do?isCheck=false&ass_nature=${ass_nature}&ass_card_no=${ass_card_no}',
					modal : false,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					parentframename : window.name,
					top : 90,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveAssFile();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '关闭',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});

	}

	function remove() {
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			parent.$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.ass_card_no + "@"
								+ this.file_code + "@" + this.file_url)
					});
			parent.$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssFileInit.do", {
						ParamVo : ParamVo.toString(),
						ass_nature : '${ass_nature}'
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function down(file_url) {
		
		location.href = "downAssFileInit.do?isCheck=false&file_url="+file_url;
	}

	function loadDict() {
		//字典下拉框

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<div id="maingrid"></div>

</body>
</html>
