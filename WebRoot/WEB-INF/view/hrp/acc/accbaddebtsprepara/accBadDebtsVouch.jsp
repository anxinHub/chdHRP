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
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;

	var gridManager = null;

	var subj_type_code;
	$(function() {
		acc_year = '${acc_year}';
		acc_month = '${acc_month}';
		template_type_code = '${template_type_code}';
		loadHead(null); //加载数据
		loadDict();
		loadButton();
		query();
	});
	
	//科目Grid
	function loadHead() {
		grid = $("#subjgrid").ligerGrid({
			columns : [ {
				display : '凭证编号',
				name : 'vouch_no',
				align : 'left',
				width : 80,
				render : function(rowdata, rowindex, value) {
					return '<a href=javascript:openSuperVouch("'+ rowdata.vouch_id + '")>'+rowdata.vouch_type_short+'-'+rowdata.vouch_no+'</a>';
				}
			}, {
				display : '制单日期',
				name : 'vouch_date',
				align : 'left',
				width : 80
			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',
				width : 180
			}, {
				display : '借方金额',
				name : 'debit',
				align : 'right',
				width : 80,
				render : function(rowdata){
					 return formatNumber(rowdata.debit,2,1);
				}
			}, {
				display : '贷方金额',
				name : 'credit',
				align : 'right',
				width : 80,
				render : function(rowdata){
					 return formatNumber(rowdata.credit,2,1);
				}
			}, {
				display : '制单人',
				name : 'create_name',
				align : 'left',
				width : 80
			}, {
				display : '出纳签字人',
				name : 'cash_name',
				align : 'left',
				width : 80
			}, {
				display : '审核人',
				name : 'audit_name',
				align : 'left',
				width : 80
			}, {
				display : '记账人',
				name : 'acc_name',
				align : 'left',
				width : 80
			}, {
				display : '备注',
				name : 'note',
				align : 'left',
				width : 100
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryBadDebtsVouch.do?isCheck=false',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : false,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: itemclick, icon:'delete' },
				{ line:true },
				{ text: '打印（<u>P</u>）', id:'print', click: itemclick, icon:'print' },
				{ line:true },
				{ text: '导出（<u>E</u>）', id:'export', click: itemclick, icon:'extend' },
				{ line:true }
			]}
		});

		gridManager = $("#subjgrid").ligerGetGridManager();
	}
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
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
							ajaxJsonObjectByUrl("../accvouch/query/deleteAccVouch.do?isCheck=false", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			case "print":
				alert("print^_^");
				if(grid.getData().length==0){
					$.ligerDialog.warn("请先查询数据！");
					return;
				}
				var heads={
					"rows": []
				};
			   		
				var printPara={
					rowCount:1,
					title:'凭证',
					columns: JSON.stringify(grid.getPrintColumns()),//表头
					class_name: "com.chd.hrp.acc.service.termend.AccTermendTemplateService",
					method_name: "queryAccTermendTemplateVouchPrint",
					bean_name: "accTermendTemplateService",
					heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
				};
			
				//执行方法的查询条件
				$.each(grid.options.parms, function(i, obj){
					printPara[obj.name]=obj.value;
				});
				
				officeGridPrint(printPara);
				return;
			case "export":
				var selPara = {};
				$.each(grid.options.parms, function (i, obj) {
					selPara[obj.name] = obj.value;
				});
				var printPara = {
					headCount: 2,
					type: 3,
					columns: grid.getColumns(1)
				};
				ajaxJsonObjectByUrl("queryBadDebtsVouch.do?isCheck=false", selPara, function (resData) {
					printGridView(resData, printPara);
				});
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
	
	function loadButton() {
		$("#but_query").ligerButton({
			click : query,
			width : 90
		});
	}

	function loadDict() {
		autocomplete("#vouch_type_code", "../queryVouchType.do?isCheck=false","id", "text", true, true, "", true);
        $("#create_date_b").ligerTextBox({width:120});
		autodate("#create_date_b", "yyyy-mm-dd", "month_first");
        $("#create_date_e").ligerTextBox({width:120});
		autodate("#create_date_e", "yyyy-mm-dd", "month_last");
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'create_date_b',
			value : $("#create_date_b").val()
		});
		grid.options.parms.push({
			name : 'create_date_e',
			value : $("#create_date_e").val()
		});
		grid.options.parms.push({
			name : 'vouch_type_code',
			value : liger.get("vouch_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'template_type_code',
			value : template_type_code
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function openSuperVouch(vouch_id){
		parent.parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div class="l-clear"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="create_date_b" type="text" id="create_date_b" ltype="text" required="true" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" style="width: 160px;" />
						</td>
						<td>
							至：
						</td>
						<td>
							<input class="Wdate" name="create_date_e" type="text" id="create_date_e" ltype="text" required="true" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" style="width: 160px;" />
						</td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">凭证类型：</td>
			<td align="left" class="l-table-edit-td">
				<input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" required="true" validate="{required:true}" />
			</td>
			<td align="left"></td>
			<td align="left" style="display: none">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="but_query" accessKey="Q" value="查询(Q)" />
			</td>
		</tr>
	</table>
	<div id="subjgrid" style="margin-top: 0px; margin-left: 10px;"></div>
</body>
</html>
