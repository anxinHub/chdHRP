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
			name : 'scheme_code',
			value : liger.get("scheme_code").getValue()
		});
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid.options.parms.push({
			name : 'period_type_code',
			value : liger.get("period_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'period_code',
			value : liger.get("period_code").getValue()
		});
		grid.options.parms.push({
			name : 'drgs_code',
			value : $("#drgs_code").val()
		});
		grid.options.parms.push({
			name : 'mr_no',
			value : $("#mr_no").val()
		});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
							columns : [{
										display : '核算类型',
										name : 'period_type_name',
										align : 'left'
									  },{
										display : '核算年度',
										name : 'acc_year',
										align : 'left'
									  },{
										display : '核算期间',
										name : 'period_name',
										align : 'left'
									},{
										display : '方案名称',
										name : 'scheme_name',
										align : 'left'
									},{
										display : '病种编码',
										name : 'drgs_code',
										align : 'left'
									},{
										display : '病种名称',
										name : 'drgs_name',
										align : 'left'
									},{
										display : '入组规则',
										name : 'icd_rule_name',
										align : 'left'
									},{
										display : '病案号',
										name : 'mr_no',
										align : 'left',
									}, {
										display : '入院号',
										name : 'in_hos_no',
										align : 'left'
									}, {
										display : '病案数',
										name : 'amount',
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryHtcgSchemeMrGroupData.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							delayLoad : true,
							rownumbers : true,
							selectRowButtonOnly : true,
							toolbar : {
								items : [ {
									text : '查询',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}, {
									text : '入组',
									id : 'init',
									click : init,
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
								}]
							},
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function init() {
		var acc_year = $("#acc_year").val();
		var scheme_code = liger.get("scheme_code").getValue();
		if (scheme_code == '') {
			$.ligerDialog.error('请选择方案!');
			return;
		}
		if (acc_year == '') {
			$.ligerDialog.error('请选择核算年度!');
			return;
		}
		var formPara = {
			acc_year : acc_year,
			scheme_code : scheme_code
		};
		ajaxJsonObjectByUrl("initHtcgSchemeMrGroupData.do?isCheck=false", formPara,
				function(WorkponseData) {
					if (WorkponseData.state == "true") {
						query();
					}
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
								  this.group_id + "@"
								+ this.hos_id + "@"
								+ this.copy_code + "@"
								+ this.period_type_code + "@"
								+ this.acc_year + "@"
								+ this.acc_month + "@"
								+ this.scheme_code + "@"
								+ this.drgs_code + "@"
								+ this.mr_no + "@"
								+ this.in_hos_no);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgSchemeMrGroupData.do", {
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
	


	function chargePeriodType() {
		var formPara={
                period_type_code:liger.get("period_type_code").getValue()
              };
    	autocomplete("#period_code","../../base/queryHtcgPeriodDict.do?isCheck=false","id","text",true,true,formPara);
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#period_type_code","../../base/queryHtcgPeriodTypeDict.do?isCheck=false","id","text",true,true);

		$("#acc_year").ligerTextBox({
			width : 160
		});
		$("#mr_no").ligerTextBox({
			width : 160
		});
		$("#drgs_code").ligerTextBox({
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
            <td align="left" class="l-table-edit-td"><input name="scheme_code" style="width:160px;" type="text" id="scheme_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年度：</td>
            <td align="left" class="l-table-edit-td"><input  class="Wdate" style="width:160px;" name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间类型：</td>
            <td align="left" class="l-table-edit-td"><input name="period_type_code" type="text" id="period_type_code" ltype="text" validate="{required:true,maxlength:20}" onchange="chargePeriodType()" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算期间：</td>
            <td align="left" class="l-table-edit-td"><input name="period_code" type="text" id="period_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病案号码：</td>
            <td align="left" class="l-table-edit-td"><input name="mr_no"  style="width:160px;" type="text" id="mr_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种名称：</td>
            <td align="left" class="l-table-edit-td"><input name="drgs_code" style="width:160px;" type="text" id="drgs_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
	</table>
<div id="maingrid"></div>
</body>
</html>