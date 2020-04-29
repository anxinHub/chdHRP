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
/* 	amount_money : function(value) {//金额
		return formatNumber(value,
				'${ass_05005}', 1);
	}, */
	$(function() {
		
		loadDict();//加载下拉框
		
		//加载数据
		loadHead(null); 
		
	});
	//查询
	function query(obj) {
		
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
		
		grid.options.parms.push({ name : 'is_add', value : liger.get("is_add").getValue() });
		
		grid.options.parms.push({ name : 'state', value : liger.get("state").getValue() });

		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : 'apply_id',
						name : 'apply_id',
						hide:true,
						align : 'left',width: '120',frozen: true
					},
					{
						display : '申请单号',
						name : 'apply_no',
						align : 'left',width: '120',frozen: true,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.summary == "合计"){
								return '';
							}
							
							if(rowdata.is_add == 1){
								return rowdata.apply_no+"&nbsp;&nbsp;<font color='red'><b>追</b></font>";
							}else{
								return rowdata.apply_no;
							}
								
						   }
					}, {
						display : '摘要',
						name : 'summary',
						align : 'left',width: '150',frozen: true
					}, {
						display : '购置年度',
						name : 'apply_year',
						align : 'left',width: '60',frozen: true
					}, {
						display : '购置月份',
						name : 'apply_month',
						align : 'left',width: '60',frozen: true
					}, {
						display : '申请科室',
						name : 'dept_name',
						align : 'left',width: '100'
					}, {
						display : '申请人',
						name : 'apply_emp_name',
						align : 'left',width: '90'
					}, {
						display : '申请日期',
						name : 'apply_date',
						align : 'left',width: '90'
					}, {
						display : '申请金额',
						name : 'apply_money',
						align : 'right',width: '120',
						render : function(rowdata, rowindex,
								value) {
							 return formatNumber(
										rowdata.apply_money == null ? 0
												: rowdata.apply_money,
										'${ass_05005}',
										1);
						}
					}, {
						display : '制单人',
						name : 'create_emp_name',
						align : 'left',width: '90'
					}, {
						display : '制单日期',
						name : 'create_date',
						align : 'left',width: '90'
					}, {
						display : '审核人',
						name : 'check_emp_name',
						align : 'left',width: '90'
					}, {
						display : '审核日期',
						name : 'check_date',
						align : 'left',width: '90'
					}, {
						display : '是否追加申请',
						name : 'is_add',
						align : 'left',width: '90',
						render : function(rowdata, rowindex,
								value) {
								if(rowdata.is_add==0){
									return "否";
								}
								else if(rowdata.is_add==1){
									return "是";
								}
						   }
					}, {
						display : '状态',
						name : 'state',
						align : 'left',width: '70',
						render : function(rowdata, rowindex,
								value) {
								if(rowdata.state==0){
									return "新建";
								}else if(rowdata.state==1){
									return "审核";
								}
						   }
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : '../assapplydept/queryAssApplyDept.do',
					width : '100%',
					height : '100%',
					alternatingRow : true,
					isScroll : true,
					checkbox : true,
					rownumbers : true,
					delayLoad :true,
					selectRowButtonOnly : true,
					checkBoxDisplay : isCheckDisplay,
					toolbar : {
						items : [ {
							text : '查询（<u>Q</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, 
						{
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
							},
						{
								text: '全选',
								id:'checkall', 
								click: checkall }	
						]
					}
				});
		
		gridManager = $("#maingrid").ligerGetGridManager();

	}
	
	function isCheckDisplay(rowdata) {
		if (rowdata.summary == "合计")
			return false;
		return true;
	}

	///向招标主表传递数据
	function save(){
		//alert("hhh");
		var data=gridManager.getCheckedRows();
		var detail_id="";
		var amount=0;
		$(data).each(function(){
		if(detail_id==""){
			detail_id=this.apply_id;
		}else{
			detail_id=detail_id+","+this.apply_id;
		}
		amount=amount+parseFloat(this.apply_money);
		});
		   frameElement.dialog.close();
		return detail_id+"@"+amount;
	};
	
	///全选功能
	 function checkall(){
		var param={ apply_no: $("#apply_no").val() ,
				apply_month1: $("#apply_month1").val() ,
				apply_month2: $("#apply_month2").val() ,
				dept_id: liger.get("dept_id").getValue() ,
				apply_emp: liger.get("apply_emp").getValue() ,
				apply_date1:$("#apply_date1").val(),
				apply_date2: $("#apply_date2").val()  ,
				check_emp: liger.get("check_emp").getValue() ,
				check_date1: $("#check_date1").val(),
				check_date2:  $("#check_date2").val(),
				is_add: liger.get("is_add").getValue(),
				state: liger.get("state").getValue() 
		}
		ajaxJsonObjectByUrl("queryassTendcheckAll.do?isCheck=false", param, function(responseData) {
			if (responseData.state == "true") {
				//alert(responseData.state);
				var apply_id=responseData.apply_id;
				var apply_money=responseData.apply_money;
				parentFrameUse().grid.addRow({bid_value:apply_money,
					apply_id:apply_id
				});
				this_close();
				//parentFrameUse().query();
				 //parentFrameUse().grid.reload() ;
			}
		});	
	 };
	 
		function this_close() {
			frameElement.dialog.close();
		}
		
	function loadDict() {
		//字典下拉框
 
		var param = {
            	query_key:''
        }; 
		//默认月
		autodate("#apply_month1","YYYYmm");
		//默认月
		autodate("#apply_month2","YYYYmm");
		
		autocomplete("#dept_id","../queryDeptDict.do?isCheck=false","id","text",true,true,param,true);
		
		autocomplete("#apply_emp","../../../hrp/sys/queryUserDict.do?isCheck=false","id","text",true,true,param,true); 
		
		autocomplete("#check_emp","../../../hrp/sys/queryUserDict.do?isCheck=false","id","text",true,true,param,true);
		
		$("#apply_no").ligerTextBox({
			width : 160
		});
		
		$("#apply_year").ligerTextBox({
			width : 160
		});
		
		$("#apply_month1").ligerTextBox({width : 120});
		
		$("#apply_month2").ligerTextBox({width : 120});
 
		$("#apply_date1").ligerTextBox({width : 120});
		
		$("#apply_date2").ligerTextBox({width : 120});
 
		$("#check_date1").ligerTextBox({width : 120});
		
		$("#check_date2").ligerTextBox({width : 120});
		 
		
    	/* $("#state").ligerComboBox({
			width : 160
		});
		
    	$("#is_add").ligerComboBox({
			width : 160
		}); */
		 $('#state').ligerComboBox({
				data:[{id:1,text:'审核'},{id:0,text:'新建'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 160
		})
		 $('#is_add').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 160
		})
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
			<td align="center" class="l-table-edit-td" width="2%">&nbsp至：</td>
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
			<td align="center" class="l-table-edit-td">&nbsp至：</td>
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
			<td align="center" class="l-table-edit-td">&nbsp至：</td>
			<td align="left" class="l-table-edit-td"><input name="check_date2" type="text" id="check_date2" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="state" name="state">
						<option value="">全部</option>
                		<option value="0">新建</option>
                		<option value="1">审核</option>
                	</select> -->
             <input  name="state" type="text" id="state" />   	
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">追加申请：</td>
			<td align="left" class="l-table-edit-td">
				<!-- <select id="is_add" name="is_add">
				        <option value="">全部</option>
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select> -->
                <input  name="is_add" type="text" id="is_add" />	
				</td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
