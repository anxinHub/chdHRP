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
    
    $(function ()
    {
    	
    	loadHead(null);	
    	loadDict() ;
    	
    });

  //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	grid.options.parms.push({name:'mate_code',value:$("#mate_code").val()}); 
    	grid.options.parms.push({name:'mate_type_code',value:liger.get("mate_type_code").getValue()}); 
    	$("#resultPrint > table > tbody").html("");

	//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '材料编码',
								name : 'mate_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
									        + rowdata.group_id + "|"
									        + rowdata.hos_id + "|"
									        + rowdata.copy_code + "|"
											+ rowdata.mate_code + "');\" >"
											+ rowdata.mate_code + "</a>";
								}
							}, {
								display : '材料名称',
								name : 'mate_name',
								align : 'left'
							}, {
								display : '材料类别名称',
								name : 'mate_type_name',
								align : 'left'
							}, {
								display : '型号',
								name : 'mate_mode',
								align : 'left'
							}, {
								display : '计量单位',
								name : 'meas_name',
								align : 'left'
							}, {
								display : '单价',
								name : 'price',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.price, 2, 1);
								}
							},{
								display : '生产厂商',
								name : 'fac_name',
								align : 'left'
							},{
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.is_stop == 1 ? "是" : "否";
								}
							}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcMaterialDict.do',
					width : '100%',
					height : '100%',
					delayLoad:true,
					checkbox : true,
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
						},{
							line : true
						},{
							text : '同步',
							id : 'synchro',
							click : synchro,
							icon : 'bluebook'
						}]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|"
						        + rowdata.hos_id + "|"
						        + rowdata.copy_code + "|"
								+ rowdata.mate_code);//实际代码中temp替换主键
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//添加
	function add_open() {
		$.ligerDialog.open({
			url : 'htcMaterialDictAddPage.do?isCheck=false',
			height : 450,
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
					dialog.frame.saveMaterialDict();
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

	//删除
	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
						+this.group_id +"@"
						+this.hos_id +"@"
						+this.copy_code +"@"
						+this.mate_code);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcMaterialDict.do", {
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

	function synchro(){
		ajaxJsonObjectByUrl("synchroHtcMaterialDict.do",null,function(responseData) {
			if (responseData.state == "true") {
				query()
			}
		});
	}
	function openUpdate(obj) {
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm =  "group_id=" + vo[0] + 
		      "&" + "hos_id=" + vo[1] + 
			  "&" + "copy_code=" + vo[2]+ 
			  "&" + "mate_code=" + vo[3]
		  
		$.ligerDialog
				.open({
					url : 'htcMaterialDictUpdatePage.do?isCheck=false&'+parm,
					data : {},
					height : 450,
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
							dialog.frame.saveMaterialDict();
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
		$("#mate_code").ligerTextBox({
			width : 160
		});
		autocomplete("#mate_type_code","../../../info/base/queryHtcMaterialTypeDict.do?isCheck=false","id", "text", true, true);
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">物资名称：</td>
			<td align="left" class="l-table-edit-td"><input name="mate_code" type="text" id="mate_code" ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">材料分类编码：</td>
			<td align="left" class="l-table-edit-td"><input name="mate_type_code" type="text" id="mate_type_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
