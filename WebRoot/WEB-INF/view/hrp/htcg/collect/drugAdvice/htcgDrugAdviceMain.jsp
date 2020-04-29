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
			name : 'mr_no',
			value : $("#mr_no").val()
		});
		grid.options.parms.push({
			name : 'in_hos_no',
			value : $("#in_hos_no").val()
		});
		grid.options.parms.push({
			name : 'patient_name',
			value : $("#patient_name").val()
		});
		grid.options.parms.push({
			name : 'advice_date',
			value : $("#advice_date").val()
		});
		grid.options.parms.push({
			name : 'order_by_id',
			value : liger.get("order_by_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'order_by_no',
			value : liger.get("order_by_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'perform_by_id',
			value : liger.get("perform_by_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'perform_by_no',
			value : liger.get("perform_by_code").getValue().split(".")[1]
		});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '病案号',
								name : 'mr_no',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
												+ rowdata.group_id + "|"
												+ rowdata.hos_id + "|"
												+ rowdata.copy_code + "|"
												+ rowdata.mr_no + "|"
												+ rowdata.in_hos_no + "|"
												+ rowdata.advice_date + "|"
												+ rowdata.order_by_no + "|"
												+ rowdata.order_by_id + "|"
												+ rowdata.perform_by_no + "|"
												+ rowdata.perform_by_id + "|"
												+ rowdata.drug_code+ "');\" >" + rowdata.mr_no + "</a>";
								}
							},
							{
								display : '住院号',
								name : 'in_hos_no',
								align : 'left'
							},
							{
								display : '姓名',
								name : 'patient_name',
								align : 'left'
							},
							{
								display : '医嘱时间',
								name : 'advice_date',
								align : 'left'
							},
							{
								display : '开单科室',
								name : 'order_by_name',
								align : 'left'
							},
							{
								display : '执行科室',
								name : 'perform_by_name',
								align : 'left'
							},
							{
								display : '药品编码',
								name : 'drug_code',
								align : 'left'
							},
							{
								display : '药品名称',
								name : 'drug_name',
								align : 'left'
							},
							{
								display : '数量',
								name : 'amount',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.amount, 2, 1);
								}
							},
							{
								display : '单价',
								name : 'price',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.price, 2, 1);
								}
							},
							{
								display : '金额',
								name : 'income_money',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.income_money,
											2, 1);
								}
							}, {
								display : '医嘱时效',
								name : 'recipe_type_name',
								align : 'left'
							}, {
								display : '病人地点',
								name : 'place',
								align : 'left'
							}  ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcgDrugAdvice.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					selectRowButtonOnly : true,
					heightDiff : -10,
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
						},{
							text : '导入',
							id : 'import',
							click : imp,
							icon : 'up'
						} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//导入
	function imp() {
		var para = {
			"column" : [ {
				"name" : "mr_no",
				"display" : "病案号",
				"width" : "100",
				"require" : true
			}, {
				"name" : "in_hos_no",
				"display" : "住院号",
				"width" : "100",
				"require" : true
			},{
				"name" : "advice_date",
				"display" : "医嘱时间",
				"width" : "100",
				"require" : true
			}, {
				"name" : "order_by_code_name",
				"display" : "开单科室",
				"width" : "100",
				"require" : true
			}, {
				"name" : "perform_by_code_name",
				"display" : "执行科室",
				"width" : "100",
				"require" : true
			}, {
				"name" : "drug_code_name",
				"display" : "药品编码或者名称",
				"width" : "100",
				"require" : true
			}, {
				"name" : "amount",
				"display" : "数量",
				"width" : "100",
				"require" : true
			}, {
				"name" : "price",
				"display" : "单价",
				"width" : "100",
				"require" : true
			}, {
				"name" : "recipe_type_code_name",
				"display" : "医嘱时效",
				"width" : "100"
			}, {
				"name" : "place",
				"display" : "病人地点",
				"width" : "100"
			}]
		};
		importSpreadView("hrp/htcg/collect/drugAdvice/importHtcgDrugAdvice.do?isCheck=false", para);

	}
	
	function add_open(){
		$.ligerDialog.open({
			url : 'htcgDrugAdviceAddPage.do?isCheck=false',
			height : 440,
			width : 470,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDrugAdvice();
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
						ParamVo.push(this.group_id + "@" 
								+ this.hos_id + "@"
							    + this.copy_code + "@"
								+ this.mr_no + "@"
							    + this.in_hos_no + "@"
							    + this.advice_date + "@" 
							    + this.order_by_no + "@" 
							    + this.order_by_id + "@" 
							    + this.perform_by_no + "@" 
							    + this.perform_by_id + "@" 
							    + this.drug_code);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgDrugAdvice.do", {
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

	
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm =  "group_id=" + vo[0]
		    + "&" + "hos_id=" + vo[1]
		    + "&" + "copy_code=" + vo[2]
		    + "&" + "mr_no=" + vo[3]
			+ "&" + "in_hos_no=" + vo[4]
			+ "&" + "advice_date=" + vo[5]
			+ "&" + "order_by_no=" + vo[6]
			+ "&" + "order_by_id=" + vo[7]
			+ "&" + "perform_by_no=" + vo[8]
			+ "&" + "perform_by_id=" + vo[9]
			+ "&" + "drug_code=" + vo[10]
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'htcgDrugAdviceUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height : 440,
			width : 470,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.updateDrugAdvice();
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
		//autocomplete("#drug_code","../queryDrugDict.do?isCheck=false","id","text",true,true);
		autocomplete("#order_by_code", "../../base/queryHtcgDeptDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#perform_by_code","../../base/queryHtcgDeptDict.do?isCheck=false", "id", "text",true, true);
		$("#mr_no").ligerTextBox({
			width : 160
		});
		$("#in_hos_no").ligerTextBox({
			width : 160
		});
		$("#patient_name").ligerTextBox({
			width : 160
		});
		$("#advice_date").ligerTextBox({
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病案号码：</td>
            <td align="left" class="l-table-edit-td"><input name="mr_no"  style="width:160px;" type="text" id="mr_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 住院号码：</td>
            <td align="left" class="l-table-edit-td"><input name="in_hos_no"  style="width:160px;" type="text" id="in_hos_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 患者姓名：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_name"  style="width:160px;" type="text" id="patient_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医嘱时间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate"  style="width:160px;" name="advice_date" type="text" id="advice_date" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
            <td align="left" class="l-table-edit-td"><input name="order_by_code" type="text" id="order_by_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 执行科室：</td>
            <td align="left" class="l-table-edit-td"><input name="perform_by_code" type="text" id="perform_by_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	</table>
	<div id="maingrid"></div>
</body>
</html>
