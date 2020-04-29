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
	var leftgrid;
	var leftgridManager = null;
	var rightgrid;
	var rightgridManager = null;
	var supId = ""
	var supNo = "";
	var supCode = "";
	var supName = "";
	
	$(function() {

		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
	});

	//查询
	function querySupInfo() {
		leftgrid.options.parms = [];
		leftgrid.options.newPage = 1;
		
		//根据表字段进行添加查询条件
		leftgrid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue()
		});
		leftgrid.options.parms.push({
			name : 'is_evaluate',
			value : $("#is_evaluate").prop("checked") ? 1 : 0
		});
		//加载查询条件
		leftgrid.loadData();
	}

	//查询
	function queryEvaSupMain() {
		rightgrid.options.parms = [];
		rightgrid.options.newPage = 1;
		
		//根据表字段进行添加查询条件
		rightgrid.options.parms.push({
			name : 'sup_id',
			value : supId
		});
		rightgrid.options.parms.push({
			name : 'sup_no',
			value : supNo
		});
		rightgrid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		rightgrid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		});
		
		//加载查询条件
		rightgrid.loadData();
	}
	
	function loadHead() {
		leftgrid = $("#maingridleft").ligerGrid({
			columns : [ 
				{ display : '供应商ID', name: 'sup_id', align: 'left', hide:true},
				{ display : '供应商NO', name: 'sup_no', align: 'left', hide:true},
				{ display : '供应商编码', name : 'sup_code', width : '30%', align : 'left' }, 
				{ display : '供应商名称', name : 'sup_name', width : '45%', align : 'left' }, 
				{ display : '是否停用', name : 'is_disable', width : '20%', align : 'center',
					render: function (rowdata, rowindex, value) {
						return value == 0 ? "<font color='green'>否</font>" : "<font color='red'>是</font>";
					},
				}
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryMatEvaSupInfoLeft.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : false,
			onSelectRow: f_onSelectRow, 
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : querySupInfo,
					icon : 'search'
				}]
			}
		});
		rightgrid = $("#maingridright").ligerGrid({
			columns : [ 
				{ display : '评价编号', name : 'eva_code', width : '20%', align : 'left', 
					render : function(rowdata, rowindex, value) {
						if(rowdata.user_name == "平均分"){
							return;
						}else{
							return '<a href=javascript:openUpdate(0,"' + rowdata.eva_code + '")>'+value+'</a>';
						}	
					}	
				}, 
				{ display : '评价人', name : 'user_name', width : '20%', align : 'left' }, 
				{ display : '评价时间', name : 'eva_date', width : '20%', align : 'left' }, 
				{ display : '状态', name : 'state', width : '10%', align : 'center',
					render: function (rowdata, rowindex, value) {
						if(rowdata.user_name == '平均分'){
							return;
						}
						if(value == 9){
							return "作废";
						}else if(value == 2){
							return "提交";
						}else{
							return "新建";
						}	
					},	
				},
				{ display : '得分', name : 'get_score', width : '10%', align : 'right' },
				{ display : '总体评价', name : 'eva_content', width : '20%', align : 'left',
					render: function (rowdata, rowindex, value) {
						if(value == null){
							return ;
						}else{
							return '<a href=javascript:openPrompt("' + value + '")>' + value + '</a>';
						}	
					},
				} 
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryMatEvaSupMainRight.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			checkBoxDisplay : isCheckDisplay,
			rownumbers : true,
			delayLoad : true,
			rowAttrRender:function(rowdata,rowid){
	 			if(rowdata.state==9){
	 				return "style='background:red'";
	 			}else if(rowdata.state==2){
	 				return "style='background:#66FF66'";
	 			}
 			}, alternatingRow:false,
			toolbar : {
				items : [ {
					text : '评价',
					id : 'add',
					click : openUpdateForAdd,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'delete',
					click : removeEva,
					icon : 'delete'
				}, {
					line : true
				}, {
					text : '提交',
					id : 'audit',
					click : submitEva,
					icon : 'audit'
				}, {
					line : true
				}, {
					text : '作废',
					id : 'bcancle',
					click : invalidEva,
					icon : 'bcancle'
				}]
			}
		});

		leftgridManager = $("#maingridleft").ligerGetGridManager();
		rightgridManager = $("#maingridright").ligerGetGridManager();
	}

	function openPrompt(strPrm){
		
		$.ligerDialog.prompt("提示", strPrm, true);
	}
	
	function removeEva(){
		
		var data = rightgridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			var eva_codes = "";
			var err_codes = "";
			$.each(data, function (index, element) {
				if(element.state != 1){
					err_codes += this.eva_code + ",";
				}else{
					eva_codes += this.eva_code + ",";
				}
	        })
			if (err_codes != "") {
				$.ligerDialog.warn("[" + err_codes.substring(0, err_codes.length-1) +"]单据已提交或者已作废<br>");
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatEvaSup.do", {
						eva_codes : eva_codes.substring(0, eva_codes.length-1)
					}, function(responseData) {
						if (responseData.state == "true") {
							queryEvaSupMain();
						}
					});
				}
			});
		}
	}
	
	function submitEva(){
		
		var data = rightgridManager.getCheckedRows();
			
		if (data.length == 0) {
			
			$.ligerDialog.warn('请选择行');
			return false;
		}else {
			var ParamVo = [];
			var err_codes = "";
			var eva_codes = "";
			
			$.each(data, function (index, element) {
				if(element.state != 1){
					err_codes += this.eva_code + ",";
				}else{
					eva_codes += this.eva_code + ",";
				}
	        })
			if (err_codes != "") {
				$.ligerDialog.warn("[" + err_codes.substring(0, err_codes.length-1) +"]单据已提交或者已作废<br>");
				return;
			}
			$.ligerDialog.confirm('确定提交?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("submitMatEvaSup.do", {
						eva_codes : eva_codes.substring(0, eva_codes.length-1)
					}, function(responseData) {
						if (responseData.state == "true") {
							queryEvaSupMain();
						}
					});
				}
			});
		}
	}
	
	function invalidEva(){
		
		var data = rightgridManager.getCheckedRows();
			
		if (data.length == 0) {
			
			$.ligerDialog.warn('请选择行');
			return false;
		}else {
			var ParamVo = [];
			var eva_codes = "";
			var err_codes = "";
			
			$.each(data, function (index, element) {
				if(element.state != 2){
					err_codes += this.eva_code + ",";
				}else{
					eva_codes += this.eva_code + ",";
				}
	        })
			if (err_codes != "") {
				$.ligerDialog.warn("[" + err_codes.substring(0, err_codes.length-1) +"]单据不是已提交状态！<br>");
				return;
			}
			
			$.ligerDialog.confirm('确定作废?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("invalidMatEvaSup.do", {
						eva_codes : eva_codes.substring(0, eva_codes.length-1)
					}, function(responseData) {
						if (responseData.state == "true") {
							queryEvaSupMain();
						}
					});
				}
			});
		}
	}
	
	function openUpdateForAdd(){
		openUpdate(1, "");
	}
	
	//打开评价
	function openUpdate(isAdd, obj) {
		
		if(supId == ""){
			$.ligerDialog.warn("请选择左侧供应商！");
			return;
		}
		
		var paras = "sup_id=" + supId + 
			  "&" + "sup_no=" + supNo + 
			  "&" + "sup_code=" + supCode + 
			  "&" + "sup_name=" + supName + 
			  "&" + "isAdd=" + isAdd;
		if(isAdd == 0){
			paras += "&eva_code=" + obj;
		}
		
		parent.$.ligerDialog.open({
			title : '评价维护页面',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/eva/sup/matEvaSupUpdatePage.do?isCheck=false&' + paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function loadDict() {
		
		//字典下拉框
		autocomplete("#sup_id", "../../queryHosSup.do?isCheck=false", "id",
				"text", true, true, {is_disable:0});
		
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		$("#begin_date").ligerTextBox({ width : 110 });
		$("#end_date").ligerTextBox({ width : 110 });
	}
	//键盘事件
	function loadHotkeys() {
	}

	function f_onSelectRow(rowdata, rowindex) {
		supId = rowdata.sup_id;
		supNo = rowdata.sup_no;
		supCode = rowdata.sup_code;
		supName = rowdata.sup_name;
		queryEvaSupMain();
	} 
	
	function isCheckDisplay(rowdata) {
		if (rowdata.user_name == "平均分")
			return false;
		return true;
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

		
		<div  style="float: left; width: 38%;">
				
			<table>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商信息：</td>
					<td align="left" class="l-table-edit-td">
						<input name="sup_id" type="text" id="sup_id" />
					</td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
					<td align="left" class="l-table-edit-td" colspan="5">
						<input name="is_evaluate" type="checkbox" id="is_evaluate" ltype="text"  onclick="querySupInfo()"/> 只显示已评价	
					</td>
				</tr>
			</table>
			<div id="maingridleft" style="margin: 4px; padding: 0; float: left;"></div>
		</div>
		<div  style="float: left; width: 60%;">
			<table>
				<td align="right" class="l-table-edit-td"  width="10%">
            	评价日期：
	            </td>
	            <td align="left" class="l-table-edit-td"  width="20%">
					<table>
						<tr>
							<td>
								<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
							</td>
							<td align="right" class="l-table-edit-td"  >
								至
							</td>
							<td>
								<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
							</td>
	            		</tr>
					</table>
		        </td>
			</table>
			<div id="maingridright" style="margin: 4px; padding: 0; margin-left: 10px; float: left;"></div>
		</div>
</body>
</html>
