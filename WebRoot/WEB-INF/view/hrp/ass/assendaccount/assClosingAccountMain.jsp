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
		//加载数据
		loadHead(null);
		
		 	var year = '${year}';
	        var month = '${month}';
	        var last_year = '${last_year}';
	        var last_month = '${last_month}';
	         
	    	if(year==""){
				$("#year_month").val("");
			}
			else{
				$("#year_month").val(year+'.'+month);
			}
	    	if(year==""){
				$("#generate_year_month").val("");
			}
			else{
				$("#generate_year_month").val( last_year+'.'+ last_month);
			}
	         
	    	if(last_year==""){
	    		
				$("#last_year_month").val("");
			}
	    	
			else{
				
				$("#last_year_month").val(last_year+'.'+last_month);
			}
	    	
	     	 $("#year_month").ligerTextBox({width:140});
	     	 
	    	 $("#last_year_month").ligerTextBox({width:140});
	    	 
	         $("#year_month").ligerTextBox({disabled:true});
	         
	         $("#last_year_month").ligerTextBox({disabled:true});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '年度',
				name : 'acc_year',
				align : 'left',
				width : '100'
			}, {
				display : '月份',
				name : 'acc_month',
				align : 'left',
				width : '100'
			}, {
				display : '开始时间',
				name : 'begin_date',
				align : 'left',
				width : '100'
			}, {
				display : '结束时间',
				name : 'end_date',
				align : 'left',
				width : '100'
			}, {
				display : '是否结转',
				name : 'ass_flag',
				align : 'left',
				width : '100',
				render : function(rowdata, rowindex, value) {
					if (rowdata.ass_flag == 1) {
						return "是";
					} else {
						return "否";
					}
				}

			}, {
				display : '结转人',
				name : 'ass_user',
				align : 'left',
				width : '100'

			}, {
				display : '结转日期',
				name : 'ass_date',
				align : 'left',
				width : '100'
			} ],
			dataAction : 'server',
			dataType : 'server',
			url : 'queryAssYearMonth.do',
			width : '100%',
			height : '100%',
			usePager : false,
			rownumbers : true,
			selectRowButtonOnly : true,
			heightDiff : -20
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	//结转
	function check() {

		acc_year_month = $("#year_month").val()

		last_year_month = $("#last_year_month").val()
		
		if(acc_year_month == '0000.00' && last_year_month == '0000.00'){
			$.ligerDialog.warn('无法进行结账,请启用系统');
			return;
		}

		var paramVo;

		paramVo = {

			acc_year_month : acc_year_month,
			last_year_month : last_year_month

		};

		ajaxJsonObjectByUrl(
				"assColsingAccount.do",
				paramVo,
				function(responseData) {
					if (responseData.state == "true") {
						var year = responseData.year;
				        var month = responseData.month;
				        var last_year = responseData.last_year;
				        var last_month = responseData.last_month;
						if(year==""){
							$("#year_month").val("");
						}
						else{
							$("#year_month").val(year+'.'+month);
						}
				    	if(year==""){
							$("#generate_year_month").val("");
						}
						else{
							$("#generate_year_month").val( last_year+'.'+ last_month);
						}
				         
				    	if(last_year==""){
				    		
							$("#last_year_month").val("");
						}
				    	
						else{
							
							$("#last_year_month").val(last_year+'.'+last_month);
						}
				    	query();
					}
				});
		query();
	}
	//反结
	function uncheck() {

		acc_year_month = $("#year_month").val()

		last_year_month = $("#last_year_month").val()
		
		if(acc_year_month == '0000.00' && last_year_month == '0000.00'){
			$.ligerDialog.warn('无法进行反结,请启用系统')
			return;
		}

		var paramVo;

		paramVo = {
			acc_year_month : acc_year_month,
			last_year_month : last_year_month
		};

		ajaxJsonObjectByUrl("assDelColsingAccount.do", paramVo,
				function(responseData) {
					if (responseData.state == "true") {
						var year = responseData.year;
				        var month = responseData.month;
				        var last_year = responseData.last_year;
				        var last_month = responseData.last_month;
						if(year==""){
							$("#year_month").val("");
						}
						else{
							$("#year_month").val(year+'.'+month);
						}
				    	if(year==""){
							$("#generate_year_month").val("");
						}
						else{
							$("#generate_year_month").val( last_year+'.'+ last_month);
						}
				         
				    	if(last_year==""){
				    		
							$("#last_year_month").val("");
						}
				    	
						else{
							
							$("#last_year_month").val(last_year+'.'+last_month);
						}
				    	query();
					}
				});

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div class="l-layout-top"
		style="left: 0px; width: 100%; top: 0px; height: 300px;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td">本月：</td>
				<td align="left" class="l-table-edit-td"><input
					name="year_month" type="text" id="year_month" ltype="text"
					value="" /></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left"><input class="l-button l-button-test"
					id="check" type="button" value="结&nbsp;&nbsp;&nbsp;&nbsp;转"
					onclick="check()" /></td>

			</tr>
			<tr>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td">上月：</td>
				<td align="left" class="l-table-edit-td"><input
					name="last_year_month" type="text" id="last_year_month" ltype="text"
					value="" /></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left"><input class="l-button l-button-test"
					type="button" id="uncheck" value="反&nbsp;&nbsp;&nbsp;&nbsp;结"
					onclick="uncheck()" /></td>
			</tr>
		</table>
	</div>
	<div class="l-layout-bottom"
		style="left: 0px; width: 100%; top: 100px; height: 100%;">
		<div id="maingrid"></div>
	</div>
</body>
</html>
