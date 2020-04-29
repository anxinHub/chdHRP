<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
		<script type="text/javascript">
			var grid;
			var ass_nature ="${ass_nature}";
			var dept_id="${dept_id}";
			var dept_no="${dept_no}";
			var str = "select * from ASS_MEASURE_REC_DETAIL p " +
					" left join ASS_MEASURE_REC pp " +
					"  on pp.group_id = p.group_id " +
					" and pp.hos_id = p.hos_id " +
					" and pp.copy_code = p.copy_code " +
					" and pp.rec_id = p.rec_id " +
					" where p.group_id = a.group_id " +
					" and p.hos_id = a.hos_id " +
					" and p.ass_card_no = a.ass_card_no " +
					" and pp.state = 0 ";
			$(function() {
				loadHead();
			});

			function  query(){
				grid.options.parms=[];
				grid.options.newPage=1;
				grid.options.parms.push({name : 'ass_name',value:$("#ass_name").val()});
			  		grid.loadData(grid.where);
		   }
			
			function loadHead() {
//				onSelect: function (e) {
//	                    grid.addRows(e.data);
//	                    grid.moveRange(e.data,0);
//	                },
				if(dept_id == null || dept_no == null || dept_id == "" ||
					dept_no == "") {
					dept_no = "";
					dept_id = "";
				}
				grid = $("#maingrid").ligerGrid({
					columns: [{
							display: '资产卡片号',
							name: 'ass_card_no',
							align: 'left',
						},
						{
							display: '资产编码',
							name: 'ass_code',
							align: 'left',
						},
						{
							display: '资产名称',
							name: 'ass_name',
							align: 'left',
						},
						{
							display: '型号',
							name: 'ass_mondl',
							align: 'left',
						},
						{
							display: '规格',
							name: 'ass_spec',
							align: 'left',
						},
						{
							display: '品牌',
							name: 'ass_brand',
							align: 'left',
						},
						{
							display: '生产厂家',
							name: 'fac_name',
							align: 'left',
						},
						{
							display: '管理科室',
							name: 'dept_name',
							align: 'left',
						},
						{
							display: '领用日期',
							name: 'in_date',
							align: 'left',
						},
						{
							display: '是否计量',
							name: 'is_measure_name',
							align: 'left',
						}
					],
					dataAction: 'server',
					dataType: 'server',
					usePager: true,
					url: '../assmeasureplan/choseAssCardNo.do?isCheck=false&ass_nature=' + ass_nature + '&dept_id=' + dept_id + '&dept_no=' + dept_no+'&sql='+str,
					width: '99%',
					height: '99%',
					checkbox: true,
					rownumbers: true,
					//autoFilter: true,
					toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
				    				]},
				});
				gridManager = $("#maingrid").ligerGetGridManager();
			}
			
			function  addRows(){
				var data=gridManager.getCheckedRows();
				var peData=parent.gridManager.getData();
				var i=0;
				$(peData).each(
					function() {
						if(isnull(this.ass_card_no)){
							parent.gridManager.deleteRow(i);
						}
						i++;
					}
					);
				parent.grid.addRows(data);
				parent.grid.addRow();
		   }

		</script>
	</head>

	<body>
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">资产名称：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name" /></td>
				<td align="left"></td>
			</tr>
		</table>
		<div id="maingrid"></div>
	</body>

</html>