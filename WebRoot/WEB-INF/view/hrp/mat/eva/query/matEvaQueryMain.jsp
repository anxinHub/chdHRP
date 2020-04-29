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
	var para = "";
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
	    		{ display: '供应商', name: 'sup_name', align: 'left', width: '20%', frozen: true }, 
	    		{ display: '评价次数', name: 'sup_num', align: 'center', width: '10%', frozen: true,
	    			render : function(rowdata, rowindex, value) {
						return '<a href=javascript:openUpdate("' 
										+ rowdata.sup_id + ','
										+ rowdata.sup_no + ','
										+ rowdata.sup_name.split(" ")[0] + ','
										+ rowdata.sup_name.split(" ")[1] + ','
										+ '")>'+value+'</a>';
					}		
	    		},
	    		{ display: '平均得分', name: 'avg_score', align: 'right', formatter: '###,##0.00', width: '10%', frozen: true,
	    			render: function(rowdata, rowindex, value){return formatNumber(value,2,1)}	
	    		},
	    		{ display: '统计排名', name: 'sup_rank', align: 'center', width: '10%', frozen: true }
	    	];
		
		var target_columns = "";
			
		ajaxJsonObjectByUrl("queryTargetCodeThead.do?isCheck=false", {target_code:liger.get("target_code").getValue()}, function (responseData){
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
			url: 'queryMatEvaReportMain.do?isCheck=false&' + para,
			width: '100%',
			height: '100%',
			delayLoad :true,
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

		autocomplete("#sup_id", "../../queryHosSup.do?isCheck=false", "id",
				"text", true, true);
		
		autocomplete("#target_code", "queryMatEvaTarget.do?isCheck=false", "id",
				"text", true, true);
		
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
			
			loadHead();
		});
	}
	
	function openUpdate(obj){
		var splitStr = obj.split(",");
		
		var prms = "sup_id=" + splitStr[0] + 
		     "&" + "sup_no=" + splitStr[1] + 
		     "&" + "begin_date=" + $("#begin_date").val() + 
		     "&" + "end_date=" + $("#end_date").val() + 
		  	 "&" + "target_code=" + liger.get("target_code").getValue() +
		  	 "&" + "target_name=" + liger.get("target_code").getText() +
		  	 "&" + "sup_name=" + splitStr[2] + " " + splitStr[3];

		parent.$.ligerDialog.open({
			title : '评价明细数据',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/eva/query/matEvaDetailQueryPage.do?isCheck=false&' + prms.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function changeColumn(){
		grid.reload();
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
				<input name="target_code" type="text" id="target_code"/>
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>