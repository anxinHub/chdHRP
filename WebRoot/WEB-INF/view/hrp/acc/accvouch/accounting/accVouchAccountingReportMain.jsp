<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	$(function() {
		loadDict();
		loadHead(null); //加载数据

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值
	function f_getWhere() {
		if (!grid)
			return null;
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '科目编码',
				name : 'subj_code',
				align : 'left',width:'15%'
			}, {
				display : '科目全称',
				name : 'subj_name',
				align : 'left',width:'40%'
			}, {
				display : '借方金额',
				name : 'debit',
				align : 'right',formatter: "###,##0.00",
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.debit, 2, 1);
  				}
			}, {
				display : '贷方金额',
				name : 'credit',
				align : 'right',formatter: "###,##0.00",
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.credit, 2, 1);
  				}
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAccVouchAccountingReport.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				return;
			case "modify":
				return;
			case "delete":
				return;
			case "Excel":
			case "Word":
			case "PDF":
			case "TXT":
			case "XML":
				$.ligerDialog.waitting('导出中，请稍候...');
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
					if (item.id == "Excel")
						$.ligerDialog.success('导出成功');
					else
						$.ligerDialog.error('导出失败');
				}, 1000);
				return;
			}
		}

	}
	function loadDict() {
	}
	
	function printDate(){
		var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	  	          {"cell":0,"value":"当前期间："+${yearMonth},"colSpan":"5"}
	      		  ]
	      	};
	   		
	   		var printPara={
	   			rowCount:1,
	   			title:'记账报告',
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.AccVouchAccountingService",
				method_name: "queryAccVouchAccountingReportPrint",
				bean_name: "accVouchAccountingService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	   			};
	    	
	   		//执行方法的查询条件
	   		$.each(grid.options.parms,function(i,obj){
	   			printPara[obj.name]=obj.value;
	    	});
	   		
	    	officeGridPrint(printPara);
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
        <tr>
            <td align="left"  id="title" style="padding-left: 50px;padding-top: 20px" ><h3>当前期间:${yearMonth}</h3></td>
         	<td align="left" style="padding-left: 10px;padding-top: 20px"><input type="button"  class="liger-button" value="打印" onclick="printDate();"/></td> 

        </tr> 
    </table>
	
	
	<div id="maingrid"></div>
</body>
</html>
