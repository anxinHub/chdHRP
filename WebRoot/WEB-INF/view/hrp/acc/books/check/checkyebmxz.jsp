<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript" src="<%=path%>/lib/hrp/acc/accBookZcheck.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/acc/superReport/ktLayer.js"></script>
<style>
	.info {
		padding: 5px 0px;
		position:relative;
		/*height: 500px;*/
		display: none;/*避免闪动初始规定不显示*/
	}
</style>

<script>
	var grid, gridManager = null;
	var childObj = parentFrameUse().childObj;
	
	$(function () {
    	loadHead(null);	//加载数据
    	query();
    });
    
    function query(){

		grid.options.parms=[];
		grid.options.newPage=1;
		
		//根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acc_year_b',value : childObj.year_month1.split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_b',value : childObj.year_month1.split(".")[1]}); 
    	grid.options.parms.push({name:'acc_year_e',value : childObj.year_month2.split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_e',value : childObj.year_month2.split(".")[1]}); 
    	grid.options.parms.push({name: 'begin_subj_code', value: childObj.subj_code}); 
    	grid.options.parms.push({name: 'end_subj_code', value:  childObj.subj_code}); 
    	grid.options.parms.push({name: 'check_item_type1', value: childObj.check_type1}); 
    	grid.options.parms.push({name: 'check_item_type2', value: childObj.check_type2}); 
    	grid.options.parms.push({name: 'check_item_type3', value: childObj.check_type3}); 
    	grid.options.parms.push({name: 'check_item_type4', value: childObj.check_type4}); 
    	
    	grid.options.parms.push({name: 'check_item_code1_b', value: childObj.check_item1}); 
    	grid.options.parms.push({name: 'check_item_code1_e', value: childObj.check_item1}); 
    	grid.options.parms.push({name: 'check_item_code2_b', value: childObj.check_item2}); 
    	grid.options.parms.push({name: 'check_item_code2_e', value: childObj.check_item2});
    	grid.options.parms.push({name: 'check_item_code3_b', value: childObj.check_item3}); 
    	grid.options.parms.push({name: 'check_item_code3_e', value: childObj.check_item3}); 
    	grid.options.parms.push({name: 'check_item_code4_b', value: childObj.check_item4}); 
    	grid.options.parms.push({name: 'check_item_code4_e', value: childObj.check_item4});
    	
    	grid.options.parms.push({name: 'order_by', value: 1});
    	grid.options.parms.push({name: 'show_zero', value: 1});

    	//改变列
    	changeCol();

    	//加载查询条件
    	grid.loadData(grid.where);
    }
  
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '期间', align: 'left',
				columns:[ { 
					display: '年', isSort:false, name: 'acc_year', align: 'left', width: '40'
				}, { 
					display: '月', isSort:false, name: 'acc_month', align: 'left', width: '40', frozen: false
				} ]
			}, { 
				display: '凭证号', isSort:false, name: 'vouch_no', align: 'left',width: '100', frozen: false, 
				render : function(rowdata, rowindex, value) {
					if (value) {
						return "<a href=javascript:openSuperVouch('"+ rowdata.vouch_id+ "')>"+ value + "</a>";
					}
				}
			}, { 
				display: '科目', isSort:false, name: 'subj_name', align: 'left',width: '200', frozen: false
			}, { 
				display: '核算项1', isSort: false, name:  'check1_name', align:  'left', width: '180'
			}, { 
				display: '核算项2', isSort: false, hide: true, name:  'check2_name', align:  'left', width: '180'
			}, { 
				display: '核算项3', isSort: false, hide: true, name:  'check3_name', align:  'left', width: '180'
			}, { 
				display: '核算项4', isSort: false, hide: true, name:  'check4_name', align:  'left', width: '180', frozen: false 
			}, { 
				display: '摘要', isSort:false, name: 'summary', align: 'left',width: '200',
			}, { 
				display: '借方', isSort:false, name: 'debit',align: 'right',width: '120',formatter:'###,##0.00',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.debit, 2, 1);
				}
			}, { 
				display: '贷方', isSort:false, name: 'credit', align: 'right',width: '120',formatter:'###,##0.00',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.credit, 2, 1);
				}
			}, { 
				display: '方向', isSort:false, name: 'subj_dire', align: 'left', width: '50'
			}, { 
				display: '余额', isSort:false, name: 'end_os', align: 'right',width: '120',formatter:'###,##0.00',reg:'0=Q,0.00=Q',
				render : function(rowdata, rowindex, value) {  
					if(rowdata.end_os==0)
						return "Q";
					else
						return formatNumber(rowdata.end_os, 2, 1);
				}
			} ],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryAccBooksCheckMXZ.do?isCheck=false',
			width: '100%', height: '98%', checkbox: false, rownumbers: true,
			delayLoad: true, pageSize: 100, pageSizeOptions: [100, 200, 500],
			selectRowButtonOnly: true,
		});

		gridManager = $("#maingrid").ligerGetGridManager(); 
	};

	//改变列
	function changeCol(){
		//改变核算项1显示名称
		grid.changeHeaderText("check1_name", childObj.check_type_name1);
		//判断核算项2、3、4是否显示且更改显示名称
		if(childObj.check_type2){
			grid.toggleCol("check2_name", true);
			grid.changeHeaderText("check2_name", childObj.check_type_name2);
		}
		if(childObj.check_type3){
			grid.toggleCol("check3_name", true);
			grid.changeHeaderText("check3_name", childObj.check_type_name3);
		}
		if(childObj.check_type4){
			grid.toggleCol("check4_name", true);
			grid.changeHeaderText("check4_name", childObj.check_type_name4);
		}
	}
 	
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="maingrid"></div>
</body>
</html>
