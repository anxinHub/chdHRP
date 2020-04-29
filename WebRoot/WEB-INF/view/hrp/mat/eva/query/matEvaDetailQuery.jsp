<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null; 
	var para = "begin_date=${begin_date}&end_date=${end_date}&sup_id=${sup_id}&target_code=${target_code}";
	var target_code = "${target_code}"; 
	
	$(function () {
	
		loadDict();
		loadHead(); //加载数据
	});
	
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;
		
		grid.options.parms.push({
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue()
		});
		grid.options.parms.push({
			name : 'target_code',
			value : liger.get("target_code").getValue()
		});

		grid.loadData(grid.where);
	}

	function loadHead() {
		var columns = [
	    		{ display: '评价单号', name: 'eva_code', align: 'left', width: '15%', frozen: true }, 
	    		{ display: '评价日期', name: 'eva_date', align: 'center', width: '10%', frozen: true	},
	    		{ display: '得分', name: 'get_score', align: 'right', formatter: '###,##0.00', width: '10%', frozen: true,
	    			render: function(rowdata, rowindex, value){return formatNumber(value,2,1)}	
	    		}
	    	];
		
		var target_columns = "";
		
		ajaxJsonObjectByUrl("queryTargetCodeThead.do?isCheck=false", {target_code : target_code}, function (responseData){
	    	if(responseData.Rows.length > 0){
	    		$.each(responseData.Rows, function(v_index, v_data){ 
	    			target_columns = "{ display: '"+v_data.target_name+"', name:'t_"+v_data.target_code+"',"
	        			 + "align: 'right', formatter: '###,##0.00', width: '10%' ,"
	        			 + "render: function(rowdata, rowindex, value){return formatNumber(value,2,1)}}"; 
	    			
	        		columns.push(eval("("+target_columns+")"));
	    		}); 
			}
	    }, false);
		
		grid = $("#maingrid").ligerGrid({
			columns:columns,
			dataAction: 'server',
			dataType: 'server',
			usePager: true, 
			url: 'queryMatEvaReportDetail.do?isCheck=false&' + para,
			width: '100%',
			height: '100%',
			delayLoad :false,
			checkbox: false,
			rownumbers: true,
			selectRowButtonOnly: true,//heightDiff: -10,
			toolbar: {
					items: [{
						text: '查询',
						id: 'search',
						click: query,
						icon: 'search'
					}]
				}
			});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		$("#begin_date").ligerTextBox({ width : 110 });
		$("#end_date").ligerTextBox({ width : 110 });
		if(${begin_date != null} || ${end_date != null}){
			$("#begin_date").val("${begin_date}");
			$("#end_date").val("${end_date}");
			$("#begin_date").ligerTextBox({width:110, disabled:true});
			$("#end_date").ligerTextBox({width:110, disabled:true});
		}	

		autocomplete("#sup_id", "../../queryHosSup.do?isCheck=false", "id",
				"text", true, true);
		if(${sup_id != null}){
			liger.get("sup_id").setValue("${sup_id}");
			liger.get("sup_id").setText("${sup_name}");
			$("#sup_id").ligerComboBox({width:160, disabled:true, cancelable: false});
		}
		
		autocomplete("#target_code", "queryMatEvaTarget.do?isCheck=false", "id",
			"text", true, true);
		if(${target_code != null}){
			liger.get("target_code").setValue("${target_code}");
			liger.get("target_code").setText("${target_name}");
			$("#target_code").ligerComboBox({width:160, disabled:true, cancelable: false});
		}
		$("#begin_date").focus(function () {
			para = "begin_date=" + $("#begin_date").val() +
			"&end_date=" + $("#end_date").val() + 
			"&sup_id=" + liger.get("sup_id").getValue() + 
			"&target_code=" + liger.get("target_code").getValue();
			
			loadHead();
		});	
		
		$("#end_date").bind("focus", function () {
			para = "begin_date=" + $("#begin_date").val() +
			"&end_date=" + $("#end_date").val() + 
			"&sup_id=" + liger.get("sup_id").getValue() + 
			"&target_code=" + liger.get("target_code").getValue();
			
			loadHead();
		});		
		
		$("#sup_id").bind("change", function () {
			para = "begin_date=" + $("#begin_date").val() +
			"&end_date=" + $("#end_date").val() + 
			"&sup_id=" + liger.get("sup_id").getValue() + 
			"&target_code=" + liger.get("target_code").getValue();
			
			loadHead();
		});	
		$("#target_code").bind("change", function () {
			para = "begin_date=" + $("#begin_date").val() +
			"&end_date=" + $("#end_date").val() + 
			"&sup_id=" + liger.get("sup_id").getValue() + 
			"&target_code=" + liger.get("target_code").getValue();
			
			target_code = liger.get("target_code").getValue();
			
			loadHead();
		});
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商信息：</td>
			<td align="left" class="l-table-edit-td">
				<input name="sup_id" type="text" id="sup_id" />
			</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">评价指标：</td>
			<td align="left" class="l-table-edit-td">
				<input name="target_code" type="text" id="target_code" />
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>