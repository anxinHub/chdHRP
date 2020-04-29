<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr; 
	var rowindex = '${rowindex}';//行号
	var proofData = parentFrameUse().grid.getRow(rowindex).detailData;//论证信息数据
	$(function() {
		//加载数据
		loadDict();
		loadForm();
		loadHead();
	});
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ 
					{
						display : '第N年',
						name : 'year_num',
						editor : {
							type : 'int'
						},
						align : 'center',width: '100',
					}, {
						display : '项目收入/元',
						name : 'project_income',
						editor : {
							type : 'numberbox'
						},
						align : 'right',width: '150',
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.project_income == null ? 0
												: rowdata.project_income,
										'${ass_05005}',
										1);
						}
					}, {
						display : '年工作量/人次',
						name : 'year_workload',
						editor : {
							type : 'text'
						},
						align : 'right',width: '150'
					}, {
						display : '收费标准/元',
						name : 'charge_stand',
						editor : {
							type : 'numberbox'
						},
						align : 'right',width: '150',
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.charge_stand == null ? 0
												: rowdata.charge_stand,
										'${ass_05005}',
										1);
						}
					}, {
						display : '耗材费用/元',
						name : 'consumable_cost',
						align : 'right',width: '150',
						editor : {
							type : 'numberbox'
						},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.consumable_cost == null ? 0
												: rowdata.consumable_cost,
										'${ass_05005}',
										1);
						}
					}, {
						display : '人员经费/元',
						name : 'employee_cost',
						align : 'right',width: '150',
						editor : {
							type : 'numberbox'
						},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.employee_cost == null ? 0
												: rowdata.employee_cost,
										'${ass_05005}',
										1);
						}
					}, {
						display : '维修费/元',
						name : 'maintenance_cost',
						align : 'right',width: '150',
						editor : {
							type : 'numberbox'
						},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.maintenance_cost == null ? 0
												: rowdata.maintenance_cost,
										'${ass_05005}',
										1);
						}
					}, {
						display : '水电费/元',
						name : 'waterele_cost',
						align : 'right',width: '150',
						editor : {
							type : 'numberbox'
						},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.waterele_cost == null ? 0
												: rowdata.waterele_cost,
										'${ass_05005}',
										1);
						}
					}, {
						display : '折旧费/元',
						name : 'depreciation_cost',
						align : 'right',width: '150',
						editor : {
							type : 'numberbox'
						},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.depreciation_cost == null ? 0
												: rowdata.depreciation_cost,
										'${ass_05005}',
										1);
						}
					}, {
						display : '其他成本/元',
						name : 'other_cost',
						align : 'right',width: '150',
						editor : {
							type : 'numberbox'
						},
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.other_cost == null ? 0
												: rowdata.other_cost,
										'${ass_05005}',
										1);
						}
					}],
					dataAction : 'server',dataType : 'server',usePager : false,
					data:proofData,	width : '100%',	height : '100%',
					enabledEdit : true,	isScroll : true,checkbox : true,rownumbers : true,
					selectRowButtonOnly : true,
					toolbar : {
						items : [  
						      	{text : '保存',id : 'save',click : save,icon : 'save'},
    	                		{text : '删除行',id : 'delete',click : deleteProofDetai,icon : 'delete'},
    	                		{line : true} ,
    	                		{text : '关闭',id : 'close',click : this_close,icon : 'candle'}
						]
					}
				});
		
		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
	function this_close() {
		frameElement.dialog.close();
	}
	
	//论证信息删除
	function deleteProofDetai() {
		var data = grid.getCheckedRows();
  		if(data.length == 0){
  			$.ligerDialog.error('请选择要删除的行!');
               return;
         }else{
         	 for (var i = 0; i < data.length; i++){
         		 grid.remove(data[i]);
              } 
         }
	}
	//保存方法
	function save(){
		
		var data = gridManager.getData();
		if($("form").valid()){
			if(validateGrid()){
				//将论证信息更新到父级页面明细数据中
				parentFrameUse().grid.updateRow(rowindex, {
					
					bcost_year:$("#bcost_year").val(),
					avg_cost:$("#avg_cost").val(),
					avg_profit:$("#avg_profit").val(),
					benefit_rate:$("#benefit_rate").val(),
					create_user:$("#create_user").val(),
					use_place:$("#use_place").val(),
					apply_analyze:$("#apply_analyze").val(),
					investigate_analyze:$("#investigate_analyze").val(),
					describ:$("#describ").val(),
					//detailData 记录 论证信息的明细数据
					detailData:{"Rows":data,"Total":data.length}
				});
				this_close();
			}
			
		}
	   
	}
	
	function validateGrid() {

		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data,
				function(i, v) {
			if (!isnull(v.year_num)) {
					var key = v.year_num;
					var value = "第" + (i + 1) + "行";
					
					if (isnull(v.year_num)) {
						msg += "[年度不能为空]、";
					}
					
					if (isnull(v.project_income)) {
						msg += "[项目收入]、";
					}
					if (isnull(v.year_workload)) {
						msg += "[年工作量单价]、";
					}
					 if (isnull(v.charge_stand)) {
						msg += "[收费标准]、";
					} 
					 
					 /*if (isnull(v.consumable_cost)) {
							msg += "[耗材费用]、";
						} 
					 if (isnull(v.employee_cost)) {
							msg += "[人员经费]、";
						} 
					 if (isnull(v.maintenance_cost)) {
							msg += "[维修费]、";
						} 
					 if (isnull(v.waterele_cost)) {
							msg += "[水电费]、";
						} 
					 if (isnull(v.depreciation_cost)) {
							msg += "[折旧费]、";
						} */
					if (msg != "") {
						msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
					}
					if (isnull(targetMap.get(key))) {
						targetMap.put(key, value);
					} else {
						msg = targetMap.get(key) + "与" + value + "重复！\n\r",
						value;
						msgMap.put(
								targetMap.get(key) + "与" + value + "重复！\n\r",
								value);
					}
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	}

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	
	function loadForm(){

	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	           //提示信息  显示2秒后消失
	             setTimeout(function(){
	            	  lable.ligerHideTip();
	                  lable.remove();
	             },2000)
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     //$("form").ligerForm();
	 }
	function loadDict(){
		// 获取 父级页面的表格数据
		var dataMap = parentFrameUse().grid.getRow(rowindex);
		
		$("#bcost_year").val(dataMap.bcost_year);
		$("#avg_cost").val(dataMap.avg_cost);
		$("#avg_profit").val(dataMap.avg_profit);
		$("#benefit_rate").val(dataMap.benefit_rate);
		$("#create_user").val(dataMap.create_user);
		$("#use_place").val(dataMap.use_place);
		$("#apply_analyze").val(dataMap.apply_analyze);
		$("#investigate_analyze").val(dataMap.investigate_analyze);
		$("#describ").val(dataMap.describ);
				
		$("#bcost_year").ligerTextBox({width : 160});
		$("#avg_cost").ligerTextBox({width : 160});
		$("#avg_profit").ligerTextBox({width : 160});
		$("#benefit_rate").ligerTextBox({width : 160});
		$("#create_user").ligerTextBox({width : 160});
		$("#use_place").ligerTextBox({width : 160});
		
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="is_addRow();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<form name="form1" method="post"  id="form1">
		<table  cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>回本年限<span style="color:red">*</span>:</font></td>
				<td align="left" class="l-table-edit-td" ><input name="bcost_year"  id="bcost_year" ltype="text"  validate="{required:true}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>年平均成本<span style="color:red">*</span>:</font></td>
				<td align="left" class="l-table-edit-td"><input name="avg_cost" id="avg_cost" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;" ><font>年平均利润<span style="color:red">*</span>:</font></td>
				<td align="left" class="l-table-edit-td"><input name="avg_profit"  id="avg_profit" ltype="text" validate="{required:true}"  /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>投资效益率<span style="color:red">*</span>:</font></td>
				<td align="left" class="l-table-edit-td"><input name="benefit_rate"  id="benefit_rate" ltype="text" validate="{required:true}" /></td>
				<td align="left" ></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>操作人员<span style="color:red">*</span>:</font></td>
				<td align="left" class="l-table-edit-td"><input name="create_user" type="text" id="create_user" ltype="text"  validate="{required:true}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>使用场地<span style="color:red">*</span>:</font></td>
				<td align="left" class="l-table-edit-td"><input name="use_place" type="text" id="use_place" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
				
			</tr>
			 <tr>
				<td  align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>临床应用分析:</font></td>
				<td align="left" class="l-table-edit-td"  colspan="5"><textarea rows="4" name="apply_analyze"  id="apply_analyze" class="liger-textarea" style="width: 600px;"  ></textarea></td>
	
				<td  align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>调研分析:</font></td>
				<td align="left" class="l-table-edit-td"  colspan="5"><textarea rows="4" name="investigate_analyze"  id="investigate_analyze" class="liger-textarea" style="width: 600px;" ></textarea></td>
				
			</tr> 
			
			<tr>
				<td  align="right" class="l-table-edit-td" style="padding-left: 20px;"><font>说明:</font></td>
				<td align="left" class="l-table-edit-td"  colspan="10"><textarea  rows="4" name="describ"  id="describ" class="liger-textarea" style="width: 1450px;" ></textarea></td>
				<td align="left"></td>
				
			</tr >
			
		</table>
	</form>
	<div id="maingrid" style="margin-top: 20px"></div>
</body>
</html>
