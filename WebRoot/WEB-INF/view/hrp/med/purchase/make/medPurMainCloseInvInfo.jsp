<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<!-- jsp:include page="${path}/inc.jsp"/-->
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/med/med.js" type="text/javascript"></script>
	<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		
	});
	//查询
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'pur_code',value : $("#pur_code").val()});
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()}); 
		grid.options.parms.push({name : 'brief',value : $("#brief").val()}); 
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
					    {display : '采购ID',name : 'pur_id',width:80,hide : true},
					    {display : '采购明细ID',name : 'pur_detail_id',width:80,hide : true},
					    {display : '单据号',name : 'pur_code',align : 'left',width : '120'},
					    {display : '摘要', name: 'brif', align: 'left',width:'80'},
					    {display : '编制日期', name: 'make_date', align: 'left',width:'80'}, 
					    {display : '制单人', name: 'maker_name', align: 'left',width:'80'}, 
					    {display : '供应商', name: 'sup_name', align: 'left',width:'150'}, 
						{display : '药品编码',name : 'inv_code',align : 'left',width : '120'},
						{display : '药品名称',name : 'inv_name',align : 'left',width : '120'},
						{display : '规格型号',name : 'inv_model',width : 120,align : 'left'},
						{display : '计量单位',name : 'unit_name',width : 60,align : 'left'},
						{display : '单价', name: 'price', align: 'right',width:'80',
							render: function (rowdata, rowindex, value) {
								rowdata.price = value == null ? "" : formatNumber(value, '${p08006 }', 0);
								return value == null ? "" : formatNumber(value, '${p08006 }', 1);
							}
				 		}, 
				 		{display: '数量', name: 'amount', align: 'right',width:'80',
				 			totalSummary : {
								align : 'right',
								render : function(suminf, column,cell) {
									return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
								}
							}	
						},{display : '金额',name : 'amount_money',width : 100,align : 'right',
							render : function(rowdata, rowindex,value) {
								rowdata.amount_money = value == null ? "": formatNumber(value,'${p08005 }',0);
								return value == null ? "": formatNumber(value,'${p08005 }',1);
							},
							totalSummary : {
								align : 'right',
								render : function(suminf, column,cell) {
									return '<div>'+ formatNumber(suminf.sum == null ? 0	: suminf.sum,'${p08005 }',1) + '</div>';
								}
							}
						},{display : '生产厂商',name : 'fac_name',width : 200,align : 'left'
						}
					],
					dataAction : 'server',dataType : 'server',usePager : true,width : '98%',height : '85%',
					url : 'queryMedPurCloseInvInfo.do?isCheck=false',
					checkbox : true,rownumbers : true,isAddRow: false,
					enabledEdit : false,delayLoad : true,//初始化不加载，默认false
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar: {
						items: [{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
								{ line:true },
								{ text: '取消关闭药品（<u>B</u>）', id:'cancleCloseInv', click: cancleCloseInv, icon:'close' },
								{ line:true },
								{ text: '关闭（<u>L</u>）', id:'close', click: this_close, icon:'close' }
						]}
				});
			gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//关闭药品
	function cancleCloseInv(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
	 		var ParamVo =[];
	 		var msgInv="";
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.pur_id +"@"+ 
					this.pur_detail_id
				) 
			});
			$.ligerDialog.confirm('确定要取消关闭的药品？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMedPurCancleCloseInv.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
							query();
							parent.query();
			            }
					});
				}
			});
		}
	}
	
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('N', cancleCloseInv);
		hotkeys('L', this_close);
	}

	function this_close() {
		frameElement.dialog.close();
	}

	function loadDict() {
		//字典下拉框
		$("#begin_date").ligerTextBox({width : 100});
		$("#end_date").ligerTextBox({width : 100});
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, 180);
		autocomplete("#dept_code", "../../queryPurDept.do?isCheck=false", "id", "text", true, true, "", false, false, 180);
		$("#pur_code").ligerTextBox({width:180});
        $("#inv_code").ligerTextBox({width:180});
        $("#brief").ligerTextBox({width:180});
        
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border='0'>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">编制日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td align="left"><input class="Wdate" name="begin_date"
							id="begin_date" type="text" 
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td align="right" class="l-table-edit-td">至：</td>
						<td align="left" class="l-table-edit-td"><input class="Wdate"
							name="end_date" id="end_date" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td" width="10%">
				<span  style="color:red">*</span>编制部门：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="dept_code" type="text" id="dept_code" ltype="text" required="true" validate="{required:false}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">
				<span  style="color:red">*</span>供货单位：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:false}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">单据号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="pur_code" type="text" id="pur_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">摘要：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
	</table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
