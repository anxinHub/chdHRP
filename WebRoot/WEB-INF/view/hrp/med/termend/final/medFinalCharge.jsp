<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var year;
	var month;
	var year_month;
	var last_year;
	var last_month;
	var last_year_month;
	
	$(function() {
		loadBody();//加载下拉框
		loadHead();
		loadButton();
	});

	function loadBody(){//加载body
	   //返回当前结账年月，以及反结账期间
        year = '${med_year}';
        month = '${med_month}';
        last_year = '${last_year}';
        last_month = '${last_month}';
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		if(year==""){
			$("#year_month").val("");
		}
		else{
			$("#year_month").val(year+'.'+month);
		}
		
		if(last_year==""){
			$("#last_year_month").val("");	
		}else{
			$("#last_year_month").val(last_year+'.'+last_month);
		}
        
		$("#year_month").ligerTextBox({width:100});
		$("#last_year_month").ligerTextBox({width:100});
		
		$("#year_month").ligerTextBox({ disabled:true });
		$("#last_year_month").ligerTextBox({ disabled:true });
	}
	//模板Grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '仓库编码',
				name : 'store_code',
				align : 'left',
				width : '80'
			},{
				display : '仓库名称',
				name : 'store_name',
				align : 'left',
				width : '150'
			},{
				display : '药品编码',
				name : 'inv_code',
				align : 'left',
				width : '120'
			},{
				display : '药品名称',
				name : 'inv_name',
				align : 'left',
				width : '240'
			},{
				display : '期初',
				columns : [{
					display : '数量',
					columns:[{
								display : '账表',
								name : 'begin_amount_rpt',
								align : 'right',
								width : '80'
								}, {
								display : '计算',
								name : 'begin_amount_calc',
								align : 'right',
								width : '80'
							}]
				},{display : '金额',
					columns:[{
								display : '账表',
								name : 'begin_money_rpt',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.begin_money_rpt ==null ? 0 : rowdata.begin_money_rpt, '${p08005}', 1);
								}
								}, {
								display : '计算',
								name : 'begin_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.begin_money_calc ==null ? 0 : rowdata.begin_money_calc, '${p08005}', 1);
								}
							}]
					
				}],
			},{
				display : '本期增加',
				columns : [{
					display : '数量',
					columns:[{
								display : '账表',
								name : 'in_amount_rpt',
								align : 'right',
								width : '80'
								}, {
								display : '计算',
								name : 'in_amount_calc',
								align : 'right',
								width : '80'
							}]
				},{display : '金额',
					columns:[{
								display : '账表',
								name : 'in_money_rpt',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.in_money_rpt ==null ? 0 : rowdata.in_money_rpt, '${p08005}', 1);
								}
								}, {
								display : '计算',
								name : 'in_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.in_money_calc ==null ? 0 : rowdata.in_money_calc, '${p08005}', 1);
								}
							}]
					
			}],
			},{
				display : '本期减少',
				columns : [{
					display : '数量',
					columns:[{
								display : '账表',
								name : 'out_amount_rpt',
								align : 'right',
								width : '80'
								}, {
								display : '计算',
								name : 'out_amount_calc',
								align : 'right',
								width : '80'
							}]
				},{display : '金额',
					columns:[{
								display : '账表',
								name : 'out_money_rpt',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.out_money_rpt ==null ? 0 : rowdata.out_money_rpt, '${p08005}', 1);
								}
								}, {
								display : '计算',
								name : 'out_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
										return formatNumber(rowdata.out_money_calc ==null ? 0 : rowdata.out_money_calc, '${p08005}', 1);
									}
							}]
					
				}],
			},{
				display : '期末',
				columns : [{
					display : '数量',
					columns:[{
								display : '账表',
								name : 'end_amount_rpt',
								align : 'right',
								width : '80'
								}, {
								display : '计算',
								name : 'end_amount_calc',
								align : 'right',
								width : '80'
							}]
				},{display : '金额',
					columns:[{
								display : '账表',
								name : 'end_money_rpt',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.out_money_rpt ==null ? 0 : rowdata.out_money_rpt, '${p08005}', 1);
								}
								}, {
								display : '计算',
								name : 'end_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
										return formatNumber(rowdata.out_money_calc ==null ? 0 : rowdata.out_money_calc, '${p08005}', 1);
									}
							}]
					
				}],
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryMedFinalCheck.do',
			width : '100%',
			height : '90%',
			checkbox : false,
			rownumbers : false,
			delayLoad: true,
			selectRowButtonOnly : true,//heightDiff: -10,
			
		});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function loadButton(){ 
		$("#check").ligerButton({click: check,  width:120});	
		$("#charge").ligerButton({click: charge,  width:120});	
		$("#inverse").ligerButton({click: inverse,  width:120});	
	}
	function check(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'year',value : year});
		grid.options.parms.push({name : 'month',value :month}); // month}); 
		grid.options.parms.push({name : 'para_08005',value : '${p08005}'});
		
		grid.loadData(grid.where);
	}
	
	function charge(){
		
		var paras={
			year : year,
			month : month,  //month,
			para_08005:'${p08005}'
		};
		
		
		if(year == '' || month == ''){
			$.ligerDialog.warn('当前结账年月为空,不能进行结账操作');
			return ; 
		}
		
		
		var flag = true;
		ajaxJsonObjectByUrl("queryExistsMedFinalUnconfirm.do?isCheck=false",paras,function (responseData){//查询是否包含未确认单据
			if(responseData.state == "true"){//不包含
				ajaxJsonObjectByUrl("updateMedFinalCharge.do",paras,function (responseData){
					if(responseData.state=="true"){
						
						year = responseData.year;
				        month = responseData.month;
				        last_year = responseData.last_year;
				        last_month =  responseData.last_month;
						//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
						if(year==""){
							$("#year_month").val("");
						}
						else{
							$("#year_month").val(year+'.'+month);
						}
						
						if(last_year==""){
							$("#last_year_month").val("");	
						}else{
							$("#last_year_month").val(last_year+'.'+last_month);
						}
						check();
		              }	
				});	
			}else{//包含
				$.ligerDialog.confirm('含有未确认单据,是否继续结账?',function(yes){//提示
					if(yes){
						ajaxJsonObjectByUrl("updateMedFinalCharge.do",paras,function (responseData){
							if(responseData.state=="true"){
								
								year = responseData.year;
						        month = responseData.month;
						        last_year = responseData.last_year;
						        last_month =  responseData.last_month;
								//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
								if(year==""){
									$("#year_month").val("");
								}
								else{
									$("#year_month").val(year+'.'+month);
								}
								
								if(last_year==""){
									$("#last_year_month").val("");	
								}else{
									$("#last_year_month").val(last_year+'.'+last_month);
								}
								check();
				              }	
						});	
					}
				});
			}
		});
	}
	
	function inverse(){
		if($("#last_year_month").val()==''){
			$.ligerDialog.warn('不存在上个期间，不能反结账！');
			return;
		}
		var paras={
				year : year,
				month : month,  
				last_year : last_year,
				last_month : last_month
			};
			ajaxJsonObjectByUrl("updateMedFinalInverse.do",paras,function (responseData){
				if(responseData.state=="true"){
					
					year = responseData.year;
			        month = responseData.month;
			        last_year = responseData.last_year;
			        last_month =  responseData.last_month;
					//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
					if(year==""){
						$("#year_month").val("");
					}
					else{
						$("#year_month").val(year+'.'+month);
					}
					
					if(last_year==""){
						$("#last_year_month").val("");	
					}else{
						$("#last_year_month").val(last_year+'.'+last_month);
					}
					check();
	              }	
			});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">当前年月：</td>
			<td align="left" class="l-table-edit-td">
				<input name="year_month" type="text" id="year_month" requried="true" disabled="disabled" />
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<button id ="check" accessKey="H"><b>账表核对（<u>H</u>）</b></button></td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<button id ="charge" accessKey="J"><b>结账（<u>J</u>）</b></button></td>
		</tr>
		<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">反结年月：</td>
			<td align="left" class="l-table-edit-td">
				<input name="last_year_month" type="text" id="last_year_month" requried="true"  disabled="disabled" />
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<button id ="inverse" accessKey="R"><b>反结账（<u>R</u>）</b></button></td>
		</tr>
		<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>