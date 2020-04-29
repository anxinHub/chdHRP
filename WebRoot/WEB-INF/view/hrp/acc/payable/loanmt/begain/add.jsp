<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();
		
	});

	function save() {
		gridManager.endEdit();
		if(liger.get("dept_id").getValue() == ""){
			$.ligerDialog.error('科室不能为空');
			return;
		}
		if(liger.get("emp_id").getValue() == ""){
			$.ligerDialog.error('借款人不能为空');
			return;
		}
		var data = gridManager.getData();
		if(JSON.stringify(data) == '[{}]'){
  			$.ligerDialog.error('明细数据不能为空');
  			return;
  		}
		var formPara = {
				begin_code : $("#begin_code").val() == ""?0:$("#begin_code").val(),
				dept_id : liger.get("dept_id").getValue().split(".")[0],
				dept_no : liger.get("dept_id").getValue().split(".")[1],
				emp_id : liger.get("emp_id").getValue().split(".")[0],
				emp_no : liger.get("emp_id").getValue().split(".")[1],
				proj_id : liger.get("proj_id").getValue().split(".")[0],
				proj_no : liger.get("proj_id").getValue().split(".")[1],
				remark : $("#remark").val(),
				remain_amount : $("#remain_amount").val(),
		        ParamVo : JSON.stringify(data)
		};
		
		if (validateGrid()) {  
				$.post("addBudgBorrBegain.do", formPara,
					function(responseData) {
							if (responseData.state == "true") {
							$("#begin_code").val(responseData.begin_code);
							$("#remain_amount").val(responseData.remain_amount);
							parentFrameUse().query();
							$.ligerDialog.confirm('添加成功。是否继续添加单据?', function (yes){
		                		if(yes){
		                			$("#begin_code").val("");
		                			$("#remark").val("");
		                			liger.get("dept_id").setValue(0);
		                			liger.get("emp_id").setValue(0);
		                			liger.get("proj_id").setValue(0);
		                			$("#remain_amount").val("")
		                			loadDict();
		                            grid.loadData();
		                            is_addRow();
		                		}else{
		                			//this_close();
		                		}
		                	}); 
					}else{
						$.ligerDialog.error(responseData.error)
					}
				},"json");
		}
	}
	

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'begin_code',
			value : $("#begin_code").val() == ""?"0":$("#begin_code").val()
		});
 		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资金来源',
										name : 'source_id_no',
										textField : 'source_name',
										align : 'left',
										width: '200',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../../../sys/querySourceDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true,
											/* onSuccess : function(data) {
											} */
										},
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>合计</div>';
					                        },
					                        align: 'center'
					                    }
									},
									{
										display : '支出项目',
										name : 'payment_item_id_no',
										textField : 'payment_item_name',
										align : 'left',
										width: '200',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../../queryBudgPaymentItemDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true,
											/* onSuccess : function(data) {
											} */
										}
									},
									{
										display : '借款余额',
										name : 'remain_amount',
										width: '150',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render: function(item)
							            {
							                    return formatNumber(item.remain_amount,2,1);
							            },
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					                        }
					                    } 
									},
									{
										display : '说明',
										name : 'remark',
										align : 'left',
										width: '300',
										editor : {
											type : 'text'
										}
									}],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							//url : 'queryBudgBorrBegainDet.do?isCheck=false',
							width : '100%',
							height : '90%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							rownumbers : true,
							delayLoad : true,//初始化明细数据
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '保存',
									id : 'save',
									click : save,
									icon : 'save'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'delete',
									click : remove,
									icon : 'delete'
								},
				                { line:true },
				                /* { text: '导入', id:'importData', click: importData,icon:'up' }, {
									line : true
								}, */ {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'delete'
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		var total_num = this.totalRow2.text().replace("合计","").replace(",","");
		$("#remain_amount").val(total_num);
		return true;
	}
	function validateGrid() {
		//明细
 		var msg="";
 		var rowm = "";
 		var rows = 0;
 		var data = gridManager.getData();
 		//alert(JSON.stringify(data));
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (v.source_id_no) {
				if (v.source_id_no == "" || v.source_id_no == null || v.source_id_no == 'undefined') {
					rowm+="[资金来源]、";
				}  
				if (v.payment_item_id_no == "" || v.payment_item_id_no == null || v.payment_item_id_no == 'undefined') {
					rowm+="[支出项目]、";
				}  
				if (v.remain_amount == "" || v.remain_amount == null || v.remain_amount == 'undefined' || parseFloat(v.remain_amount) < 0) {
					rowm+="[借款余额]、";
				}
				if(rowm != ""){
					rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空或不能为负数" + "\n\r";
				}
				msg += rowm;
				var key=v.source_id_no+"|"+v.payment_item_id_no;
				var value="第"+(i+1)+"行";
				if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
					targetMap.put(key ,value);
				}else{
					msg += targetMap.get(key)+"与"+value+"不能重复" + "\n\r";
				}
				rows += 1;
			}  
 		});
 		if(rows == 0){
 			$.ligerDialog.warn("请添加明细数据！");  
			return false;  
 		}
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		} 	 	
 		return true;	
	}
	function remove(){
    	grid.deleteSelectedRow();
    }
	function this_close(){
		frameElement.dialog.close();
	}
	
	function importData(){
		
	}
	
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('A', save);
		hotkeys('D', remove);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		$("#dept_id").ligerComboBox({
          	url: "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1",
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected:function(value){
          		$("#emp_id").val("");
          		var dept_id = value.split(".")[0];
				var dept_no = value.split(".")[1];
          		autocomplete("#emp_id","../../../queryEmpDict.do?isCheck=false&dept_id="+dept_id+"&dept_no="+dept_no, "id","text", true, true,null,false);
          	}
 		 });  
    	
    	autocomplete("#proj_id", "../../../../sys/queryProjDictDict.do?isCheck=false", "id","text", true, true,null,false); 
    	
    	$("#remain_amount").val(0);
		$("#remain_amount").ligerTextBox({disabled:true,cancelable: false,width : 160});
		
		$("#begin_code").ligerTextBox({disabled:true,cancelable: false,width : 160});
    	//状态
    	$("#emp_id").ligerComboBox({
			width : 160
		});
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>单号：</td>
			<td align="left" class="l-table-edit-td"><input name="begin_code" disabled="disabled"
				type="text" id="begin_code"  
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" 
				type="text" id="dept_id"  
				 /></td>
			<td align="left"></td>
		</tr>	
		<tr>	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>借款人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="emp_id" type="text" id="emp_id" 
				 /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" 
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">借款余额：</td>
			<td align="left" class="l-table-edit-td"><input name="remain_amount" disabled="disabled"
				type="text" id="remain_amount" 
				 /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">备注：</td>
			<td align="left" class="l-table-edit-td" colspan="9">
				<textarea rows="3" cols="70" id="remark" name="remark"></textarea>
			</td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
	
</body>
</html>
