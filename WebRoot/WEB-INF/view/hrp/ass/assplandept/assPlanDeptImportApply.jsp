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

		$("#apply_year").ligerTextBox({
			width : 160
		});

		$("#dept_id").ligerTextBox({
			width : 160
		});
	
		$("#dept_ids").ligerTextBox({
			width : 160
		});
		$("#plan_year").ligerTextBox({
			width : 160
		});
		$("#is_add").ligerTextBox({
			width : 160
		});
		query();
	});
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件

		grid.options.parms.push({ name : 'apply_no', value : $("#apply_no").val() });
		
		grid.options.parms.push({ name : 'apply_month1', value : $("#apply_month1").val() });
		
		grid.options.parms.push({ name : 'apply_month2', value : $("#apply_month2").val() });
		
		grid.options.parms.push({ name : 'dept_id', value : liger.get("dept_id").getValue() });
 
		grid.options.parms.push({ name : 'apply_emp', value : liger.get("apply_emp").getValue() });
		
		grid.options.parms.push({ name : 'apply_date1', value : $("#apply_date1").val() });
		
		grid.options.parms.push({ name : 'apply_date2', value : $("#apply_date2").val() });
		
		grid.options.parms.push({ name : 'check_emp', value : liger.get("check_emp").getValue() });
		
		grid.options.parms.push({ name : 'check_date1', value : $("#check_date1").val() });
		
		grid.options.parms.push({ name : 'check_date2', value : $("#check_date2").val() });
		
		grid.options.parms.push({ name : 'is_add', value : $("#is_add").val() });
		
		grid.options.parms.push({ name : 'state', value : $("#state").val() });

		//加载查询条件
		grid.loadData(grid.where);
	}

	function addNewRow() {
		var manager = $("#maingrid").ligerGetGridManager();
		manager.addEditRow();
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [

			{
				display : '申请单号',
				name : 'apply_no',
				align : 'left',
				width : '120',
				frozen : true,
				render : function(rowdata, rowindex,
						value) {
					if(rowdata.is_add == 1){
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.apply_id  + "|" + rowdata.apply_no +"')>"+rowdata.apply_no+"&nbsp;&nbsp;<font color='red'><b>追</b></font></a>";
					}else{
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.apply_id  + "|" + rowdata.apply_no +"')>"+rowdata.apply_no+"</a>";
					}
				   }

			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',
				width : '150',
				frozen : true
			}, {
				display : '购置年度',
				name : 'apply_year',
				align : 'left',
				width : '60',
				frozen : true
			}, {
				display : '购置月份',
				name : 'apply_month',
				align : 'left',
				width : '60',
				frozen : true
			}, {
				display : '申请科室',
				name : 'dept_name',
				align : 'left',
				width : '100'
			}, {
				display : '申请人',
				name : 'apply_emp_name',
				align : 'left',
				width : '90'
			}, {
				display : '申请日期',
				name : 'apply_date',
				align : 'left',
				width : '90'
			}, {
				display : '申请金额',
				name : 'apply_money',
				align : 'right',
				width : '120',
				render : function(rowdata, rowindex, value) {
					return formatNumber(
							rowdata.apply_money == null ? 0
									: rowdata.apply_money,
							'${ass_05005}',
							1);
				},
				totalis_add : {
					type : 'Sum'
				}
			}, {
				display : '制单人',
				name : 'create_emp_name',
				align : 'left',
				width : '90'
			}, {
				display : '制单日期',
				name : 'create_date',
				align : 'left',
				width : '90'
			}, {
				display : '审核人',
				name : 'check_emp_name',
				align : 'left',
				width : '90'
			}, {
				display : '审核日期',
				name : 'check_date',
				align : 'left',
				width : '90'
			}, {
				display : '是否追加申请',
				name : 'is_add',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_add == 0) {
						return "否";
					} else {
						return "是";
					}
				}
			}, {
				display : '状态',
				name : 'state',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					if (rowdata.state == 0) {
						return "新建";
					} else if (rowdata.state == 1) {
						return "审核";
					}
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : '../assapplydept/queryAssApplyDepts.do?isCheck=false',
			width : '100%',
			height : '100%',
			alternatingRow : true,
			isScroll : true,
			checkbox : true,
			rownumbers : true,
			delayLoad :true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '保 存',
					id : 'save',
					click : save,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '关闭',
					id : 'close',
					click : this_close,
					icon : 'candle'
				} ]
			}

		});

		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
	function openUpdate(obj) {


		var vo = obj.split("|");
		var parm ="&group_id="+ 
		  vo[0] + "&hos_id=" + 
		  vo[1] + "& copy_code=" + 
		  vo[2] + "&apply_id=" + 
		  vo[3] + "&apply_no="+
		  vo[4]+ "&state="+vo[5];
		
		parent.$.ligerDialog.open({
			title: '购置申请查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assplandept/assPlanDeptViewApplyPage.do?isCheck=false&' + parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

		});
		
	}

	function save() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			if ($("#dept_ids").val() == "") {
				$.ligerDialog.error('编制科室不能为空');
				return;
			}
			if ($("#plan_year").val() == "") {
				$.ligerDialog.error('购置年度不能为空');
				return;
			}
			if ($("#is_add_plan").val() == "") {
				$.ligerDialog.error('是否追加计划不能为空');
				return;
			}
			if ($("#buy_code").val() == "") {
				$.ligerDialog.error('采购方式不能为空');
				return;
			}

			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(this.apply_id)
			});
			$.post("addAssPlanDeptImport.do?isCheck=false", {
				apply_ids : ParamVo.toString(),
				dept_id : liger.get("dept_ids").getValue().split("@")[0],
				dept_no : liger.get("dept_ids").getValue().split("@")[1],
				is_add : $("#is_add_plan").val(),
				buy_code : $("#buy_code").val(),
				plan_year : $("#plan_year").val()
			}, function(responseData) {
				if (responseData.state == "true") {
					parentFrameUse().query();
					parentFrameUse().openUpdate(responseData.update_para);
        			this_close();
				}
			},"json");

		}

	}

	function loadDict() {
		//字典下拉框
		//formatNumber('#apply_money',2,0);
		//默认年
		autodate("#apply_year", "YYYY");
		autodate("#plan_year", "YYYY");
		
		
		//默认月
		autodate("#apply_month1", "YYYYmm");
		//默认月
		autodate("#apply_month2", "YYYYmm");

		
		var param = {
            	query_key:''
        }; 
		
		ajaxJsonObjectByUrl("../queryDeptDictInitValue.do?isCheck=false",{},function(data){
			autocomplete("#dept_ids", "../queryDeptDict.do?isCheck=false", "id",
					"text", true, true, null, null,data == null || data == "" ? null : data[0].id);
		},false);
		
		autocompleteAsync("#dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,null,false);
		
		autocompleteAsync("#apply_emp","../../../hrp/sys/queryUserDict.do?isCheck=false","id","text",true,true,param,true); 
		
		autocompleteAsync("#check_emp","../../../hrp/sys/queryUserDict.do?isCheck=false","id","text",true,true,param,true);
		
		$("#apply_no").ligerTextBox({
			width : 160
		});
		
		$("#apply_year").ligerTextBox({
			width : 160
		});
		
		$("#apply_month1").ligerTextBox({width : 90});
		
		$("#apply_month2").ligerTextBox({width : 90});
 
		$("#apply_date1").ligerTextBox({width : 90});
		
		$("#apply_date2").ligerTextBox({width : 90});
 
		$("#check_date1").ligerTextBox({width : 90});
		
		$("#check_date2").ligerTextBox({width : 90});
		 
		
    	$("#state").ligerComboBox({
			width : 160
		});
		
    	$("#is_add").ligerComboBox({
			width : 160
		});
    	
    	$("#buy_code").ligerComboBox({
			width : 160
		});
		
    	$("#is_add_plan").ligerComboBox({
			width : 160
		});
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">购置期限：</td>
			<td align="left" class="l-table-edit-td" width="5%;"><input name="apply_month1" type="text" id="apply_month1" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_month2" type="text" id="apply_month2" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;" width="60">申请单号：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_no"
				type="text" id="apply_no"   /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">申请科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" 
				 /></td>
			<td align="left" ></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">申请日期：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_date1" type="text" id="apply_date1" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_date2" type="text" id="apply_date2" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">申&nbsp;&nbsp;请&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_emp"
				type="text" id="apply_emp" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">审&nbsp;&nbsp;核&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input name="check_emp"
				type="text" id="check_emp" 
				 /></td>
			<td align="left"></td>
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input name="check_date1" type="text" id="check_date1" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input name="check_date2" type="text" id="check_date2" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td">
			<select id="state" name="state">
						<option value="">全部</option>
                		<option value="0">新建</option>
                		<option value="1">审核</option>
                	</select>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">追加申请：</td>
			<td align="left" class="l-table-edit-td">
				<select id="is_add" name="is_add">
				        <option value="">全部</option>
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select>
				</td>
			<td align="left"></td>
		</tr>
	</table>

	<hr size="1" width="1400" color="#A3COE8" align="left" style="" />
	</table>
	<table cellpadding="0" cellspacing="0" width="100%" class="l-table-edit" bgcolor="#DDDDDD">
		<tr>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>购置年度：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_year"
				type="text" id="plan_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				color="red">*</font>编制科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_ids"
				type="text" id="dept_ids" ltype="text" 
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>是否追加计划：</td>
			<td align="left" class="l-table-edit-td"><select id="is_add_plan"
				name="is_add_plan">
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>采购方式：</td>
			<td align="left" class="l-table-edit-td"><select id="buy_code"
				name="buy_code">
					<option value="0">自主采购</option>
					<option value="1">集中采购</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>


	<div id="maingrid"></div>
</body>
</html>
