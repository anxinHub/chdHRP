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
	var set_id;
	
	$(function() {
		loadBody();//加载下拉框
		loadHead();
		loadButton();
		changeStoreSet();

		//绑定change事件
		$("#set_code").bind("change",function(){
			changeStoreSet();
		})
	});

	function loadBody(){//加载body
		
		autocompleteAsync("#set_code", "../../queryMatVirStoreByWrite.do?isCheck=false", "id", "text", true, true, "", true);
		
		/* 
		//返回当前结账年月，以及反结账期间
        year = '${mat_year}';
        month = '${mat_month}';
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
         */
		$("#year_month").ligerTextBox({width:100});
		$("#last_year_month").ligerTextBox({width:100});
		
		$("#year_month").ligerTextBox({ disabled:true });
		$("#last_year_month").ligerTextBox({ disabled:true });
	}
	
	function changeStoreSet(){
		
		set_id = liger.get("set_code").getValue();
	
		if(set_id){
			var para = {
					set_id: set_id
			}
			ajaxJsonObjectByUrl("queryYearMonthByStoreSet.do?isCheck=false", para, function (responseData) {
				if (responseData.state == "true") {
					year = responseData.year_month.split(".")[0];
					if(year){
						month = responseData.year_month.split(".")[1];
					}else{
						month = "";
					}
					last_year = responseData.before_year_month.split(".")[0];
					if(last_year){
						last_month = responseData.before_year_month.split(".")[1];
					}else{
						last_month = "";
					}
					
					$("#year_month").val(responseData.year_month);
					$("#last_year_month").val(responseData.before_year_month);
				}
			}, true);
		}
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
				display : '物资编码',
				name : 'inv_code',
				align : 'left',
				width : '120'
			},{
				display : '物资名称',
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
									return formatNumber(rowdata.begin_money_rpt ==null ? 0 : rowdata.begin_money_rpt, '${p04005}', 1);
								}
								}, {
								display : '计算',
								name : 'begin_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.begin_money_calc ==null ? 0 : rowdata.begin_money_calc, '${p04005}', 1);
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
									return formatNumber(rowdata.in_money_rpt ==null ? 0 : rowdata.in_money_rpt, '${p04005}', 1);
								}
								}, {
								display : '计算',
								name : 'in_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.in_money_calc ==null ? 0 : rowdata.in_money_calc, '${p04005}', 1);
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
									return formatNumber(rowdata.out_money_rpt ==null ? 0 : rowdata.out_money_rpt, '${p04005}', 1);
								}
								}, {
								display : '计算',
								name : 'out_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
										return formatNumber(rowdata.out_money_calc ==null ? 0 : rowdata.out_money_calc, '${p04005}', 1);
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
									return formatNumber(rowdata.out_money_rpt ==null ? 0 : rowdata.out_money_rpt, '${p04005}', 1);
								}
								}, {
								display : '计算',
								name : 'end_money_calc',
								align : 'right',
								width : '100',
								render : function(rowdata, rowindex, value) {
										return formatNumber(rowdata.out_money_calc ==null ? 0 : rowdata.out_money_calc, '${p04005}', 1);
									}
							}]
					
				}],
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryMatFinalCheck.do',
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
		grid.options.parms.push({name : 'year', value : year});
		grid.options.parms.push({name : 'month', value : month}); // month}); 
		grid.options.parms.push({name : 'set_id', value : set_id}); // month}); 
		grid.options.parms.push({name : 'para_04005', value : '${p04005}'});
		
		grid.loadData(grid.where);
	}
	
	function charge(){
		
		var paras={
				year : year,
				month : month,  //month,
				set_id : set_id, 
				para_04005:'${p04005}'
		};
		
		var flag = true;
		
		ajaxJsonObjectByUrl("queryExistsMatFinalUnconfirm.do?isCheck=false",paras,function (responseData){//查询是否包含未确认单据
			if(responseData.state == "true"){//不包含
				ajaxJsonObjectByUrl("updateMatFinalCharge.do",paras,function (responseData){
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
						ajaxJsonObjectByUrl("updateMatFinalCharge.do",paras,function (responseData){
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
				last_month : last_month, 
				set_id : set_id
			};
			ajaxJsonObjectByUrl("updateMatFinalInverse.do",paras,function (responseData){
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
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;" rowspan="3">
				虚仓：
			</td>
			<td align="left" class="l-table-edit-td" rowspan="3">
				<input name="set_code" type="text" id="set_code" requried="true" ltype="text" validate="{required:true}" />
			</td>
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