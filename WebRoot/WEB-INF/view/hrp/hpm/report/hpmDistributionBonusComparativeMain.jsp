<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	//var userUpdateStr;
	$(function() {
   	 $("#year_month_start").ligerTextBox({ width:160 });
   	autodate("#year_month_start","yyyymm");
   	
    $("#year_month_end").ligerTextBox({ width:160 });
   	autodate("#year_month_end","yyyymm");
   	$("#nalytical").ligerComboBox({width:160 });
		loadHead(null);
		loadDict() ;
	});
	function query() {
		
		var nalytical = liger.get("nalytical").getValue();
		
		if(nalytical == '01'){
			
			grid.changeHeaderText('dept_kind_code', '科室分类编码');
			
			grid.changeHeaderText('dept_kind_name', '科室分类名称');
			
		}
		
		if(nalytical == '02'){
			
			grid.changeHeaderText('dept_kind_code', '科室性质编码');
					
			grid.changeHeaderText('dept_kind_name', '科室性质名称');
			
		}
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({name : 'year_month_start',value:$("#year_month_start").val()}); 
		grid.options.parms.push({name : 'year_month_end',value:$("#year_month_end").val()}); 
		grid.options.parms.push({name : 'nalytical',value:liger.get("nalytical").getValue()});
		$("#resultPrint > table > tbody").html("");
		grid.loadData(grid.where);
		//grid.loadServerData(grid.options.parms);
	}
	function loadHead() {
		grid = $("#grid").ligerGrid({
			columns : [ {
				display : '核算年月',
				name : 'acct_year',
				align : 'left',
				editor: { type: 'text' }
			}, {
				display : '科室分类代码',
				name : 'dept_kind_code',
				align : 'left',
				editor: { type: 'text' }
			}, {
				display : '科室分类名称',
				name : 'dept_kind_name',
				align : 'left'
			}, {
				display : '奖金额',
				name : 'bonus_money',
				align : 'left',
				editor: { type: 'text' },
				render: function (rowdata , rowindex , value){
					return formatNumber(rowdata.bonus_money ==null ? 0 : rowdata.bonus_money,2,1);
				}
			} , {
				display : '占比',
				name : 'pro',
				align : 'left',
				 render: function (rowdata, rowindex, value){
					 if(typeof rowdata.pro == 'undefined'){
						 return '0%' ;
					 }else{
						 return rowdata.pro+'%' ;
					 }
                     
				 }
			} , {
				display : '人数',
				name : 'emp_num',
				align : 'left',
				editor: { type: 'text' },
				render: function (rowdata , rowindex , value){
					return formatNumber(rowdata.emp_num ==null ? 0 : rowdata.emp_num,2,1);
				}
			}  , {
				display : '人均奖',
				name : 'av',
				align : 'left',
				editor: { type: 'text' },
				render: function (rowdata , rowindex , value){
					return formatNumber(rowdata.av ==null ? 0 : rowdata.av,2,1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHpmDistributionBonusComparative.do',
			width : '100%', 
			height: '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,delayLoad:true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				},
				{ text: '导出Excel', id:'exportExcel', click: exportExcel,icon:'outbox' }
				]
			}
		});
		gridManager = $("#grid").ligerGetGridManager();
	}
	
	

	 function loadDict() {

		}
	  


		 function exportExcel(){
				
				var year_month_start=$("#year_month_start").val();
				
				var year_month_end=$("#year_month_end").val();
				
				var nalytical = liger.get("nalytical").getValue()?liger.get("nalytical").getValue():'null';
		    	
		    	if(year_month_start==''){
		    		
		    		$.ligerDialog.error('请选择核算开始年月');
		    		
		    		return false;
		    	}
		    	
				if(year_month_end==''){
		    		
		    		$.ligerDialog.error('请选择核算结束年月');
		    		
		    		return false;
		    	}
		    	
		    	var paras = year_month_start+"@"+year_month_end+"@"+nalytical;
		    	
				 location.href = "exportDistributionBonusComparativeExcel.do?paras="+paras;
						 
			 }
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算开始年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" class="Wdate" name="year_month_start" type="text" id="year_month_start" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算结束年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" class="Wdate" name="year_month_end" type="text" id="year_month_end" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">分析类别：</td>
			<td align="left" class="l-table-edit-td"><select name="nalytical" id="nalytical">
					<option value="01">科室分类</option>
					<option value="02">科室性质</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="grid"></div>

	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>
				<tr>
					<th width="200">核算年月</th>
					<th width="200">科室分类代码</th>
					<th width="200">科室分类名称</th>
					<th width="200">奖金额</th>
					<th width="200">占比</th>
					<th width="200">人数</th>
					<th width="200">人均奖</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>

	<div id="resultPrint1" style="display: none">
		<table width="100%">
			<thead>
				<tr>
					<th width="200">核算年月</th>
					<th width="200">科室性质代码</th>
					<th width="200">科室性质名称</th>
					<th width="200">奖金额</th>
					<th width="200">占比</th>
					<th width="200">人数</th>
					<th width="200">人均奖</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>