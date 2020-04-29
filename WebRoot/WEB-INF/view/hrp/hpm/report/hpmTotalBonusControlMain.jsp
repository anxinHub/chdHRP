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
   	 $("#year_month").ligerTextBox({ width:160 });
		loadHead(null);
		loadDict() ;
	});
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'year_month',
			value : $("#year_month").val()
		}); 
		grid.options.parms.push({
			name : 'dept_code',
			value :  liger.get("dept_code").getValue() 
		});
		$("#resultPrint > table > tbody").html("");
		grid.loadData(grid.where);
		//grid.loadServerData(grid.options.parms);
	}

	function loadHead() {
		grid = $("#grid").ligerGrid({
			columns : [ {
				display : '核算年度',
				name : 'acct_year',
				align : 'left',
				editor: { type: 'text' }
			}, {
				display : '科室编码',
				name : 'dept_code',
				align : 'left',
				editor: { type: 'text' }
			}, {
				display : '科室名称',
				name : 'dept_name',
				align : 'left'
			}, {
				display : '奖金额',
				name : 'bonus_money',
				align : 'left',
				editor: { type: 'text' }
			} , {
				display : '医疗收入',
				columns:[{
					display : '金额',
					name : 'income_money',
					align : 'left',
					editor: { type: 'text' }
				},{
					display : '占比',
					name : 'income_pro',
					align : 'left',
					
				render: function (rowdata, rowindex, value){
                    return rowdata.income_pro+'%' ;
				 }
				}]
			} , {
				display : '医疗支出',
				
				columns:[{
					display : '金额',
					name : 'cost_money',
					align : 'left',
					editor: { type: 'text' }
				},{
					display : '占比',
					name : 'cost_pro',
					align : 'left',
					render: function (rowdata, rowindex, value){
	                    return rowdata.cost_pro+'%' ;
					 }
				}]
				
			} , {
				display : '人力成本',
				
				columns:[{
					display : '金额',
					name : 'human_cost_money',
					align : 'left',
					editor: { type: 'text' }
				},{
					display : '占比',
					name : 'human_cost_pro',
					align : 'left',
					render: function (rowdata, rowindex, value){
	                    return rowdata.human_cost_pro+'%' ;
					 }
				}]
			}  ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHpmTotalBonusControl.do',
			width : '100%', 
			height: '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				},
                { text: '打印', id:'print', click: printDate,icon:'print' }
				]
			}
		});
		gridManager = $("#grid").ligerGetGridManager();
	}
	/*  function openUpdate(obj){
	    	//实际代码中&temp替换主键
	    	$.ligerDialog.open({ url: 'SchemeApplyUpdatePage.do?isCheck=false&scheme_seq_no='+obj,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.updateItem(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

	    } */
	 function loadDict() {
			//字典下拉框
		//autocomplete("#scheme_seq_no","../program/querySchemeCode.do?isCheck=false", "id", "text", true,true);
		 autocomplete("#dept_code","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
		}
	    
	    
	  //打印数据
		 function printDate(){
			//有数据直接打印
			if($("#resultPrint > table > tbody").html()!=""){
				lodopPrinterTable("resultPrint","打印.开始打印","列表",true);
				return;
			}
			
			//重新查询数据，避免分页导致打印数据不全
			var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
			
			ajaxJsonObjectByUrl("queryTotalBonusControl.do",{usePager:false,year_month:$("#year_month").val(),dept_code:liger.get("dept_code").getValue()},function (responseData){
				$.each(responseData.Rows,function(idx,item){ 
					 var trHtml="<tr>";
					 trHtml+="<td>"+item.acct_year+"</td>";
					 trHtml+="<td>"+item.dept_code+"</td>";
					 trHtml+="<td>"+item.dept_name+"</td>";
					 trHtml+="<td><table><tr><td>"+item.income_money+"</td><td></td>"+item.income_pro+"</tr></table></td>";
					 trHtml+="<td><table><tr><td>"+item.cost_money+"</td><td></td>"+item.cost_pro+"</tr></table></td>";
					 trHtml+="<td><table><tr><td>"+item.human_cost_money+"</td><td></td>"+item.human_cost_pro+"</tr></table></td>";
					 trHtml+="</tr>";
					 $("#resultPrint > table > tbody").append(trHtml);
				});
				manager.close();
				//alert($("#resultPrint").html())
				lodopPrinterTable("resultPrint","打印.开始打印","列表",true);
		    },true,manager);
			return;
		 }
		
		 
		 //导出数据
		 function exportExcel(){
			//有数据直接导出
			if($("#resultPrint > table > tbody").html()!=""){
				lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
				return;
			}
			
			//重新查询数据，避免分页导致打印数据不全
			var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
			
			
			ajaxJsonObjectByUrl("queryTotalBonusControl.do",{usePager:false,year_month:$("#year_month").val(),dept_code:liger.get("dept_code").getValue()},function (responseData){
				$.each(responseData.Rows,function(idx,item){ 
					 var trHtml="<tr>";
					 trHtml+="<td>"+item.acct_year+"</td>";
					 trHtml+="<td>"+item.dept_code+"</td>";
					 trHtml+="<td>"+item.dept_name+"</td>";
					 trHtml+="<td><table><tr><td>"+item.income_money+"</td><td></td>"+item.income_pro+"</tr></table></td>";
					 trHtml+="<td><table><tr><td>"+item.cost_money+"</td><td></td>"+item.cost_pro+"</tr></table></td>";
					 trHtml+="<td><table><tr><td>"+item.human_cost_money+"</td><td></td>"+item.human_cost_pro+"</tr></table></td>";
					 trHtml+="</tr>";
					 $("#resultPrint > table > tbody").append(trHtml);
				});
				manager.close();
				lodopExportExcel("resultPrint","导出Excel","列表.xls",true);
		    },true,manager);
			return;
		 }
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month" type="text" id="year_month" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy年MM月'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="grid"></div>

	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>
				<tr>
					<th width="200">核算年度</th>
					<th width="200">科室编码</th>
					<th width="200">科室名称</th>
					<th width="200">奖金额</th>
					<th width="200">医疗收入
						<table width="100%">
							<tr>
								<th width="200">金额</th>
								<th width="200">占比</th>
							</tr>
						</table>
					</th>
					<th width="200">医疗支出
						<table width="100%">
							<tr>
								<th width="200">金额</th>
								<th width="200">占比</th>
							</tr>
						</table>
					</th>
					<th width="200">人力成本
						<table width="100%">
							<tr>
								<th width="200">金额</th>
								<th width="200">占比</th>
							</tr>
						</table>
					</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>