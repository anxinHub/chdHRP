<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多方位收益分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
    	  query();
      });
    
       function query(){
       	 grid.options.parms=[];
      	 grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	    grid.options.parms.push({name:'year_month_begin',value:'${year_month_begin}'}); 
       	    grid.options.parms.push({name:'year_month_end',value:'${year_month_end}'});
       	    grid.options.parms.push({name:'dept_id',value:'${dept_id}'=='null'?'':'${dept_id}'}); 
       	    grid.options.parms.push({name:'dept_no',value:'${dept_no}'=='null'?'':'${dept_no}'}); 
 	       	//加载查询条件
 	       grid.loadData(grid.where);
        }

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
					display: '收费类别',
					name: 'chargename',
					align: 'left',
					/* render: function(rowdata, rowindex, value) {
						if(rowdata.chargeid !=null ){
							return "<a href=javascript:openItemDir('" +
							$("#year_month_begin").val() + "|" +
							$("#year_month_end").val() + "|" +
							rowdata.dept_id+ "|" +
							rowdata.dept_no+ "|" +
							rowdata.chargeId+ "|" +
						  "')>" + rowdata.chargename + "</a>";	
						}else{
							return rowdata.chargename;
						}	   		  	
					  } */
				}, {
					display: '收入',
					name: 'money',
					align: 'left',
					render: function(rowdata, rowindex, value) {
						  return formatNumber(rowdata.money, 2, 1);
						}
				},{
					display: '比率',
					name: 'proportion',
					align: 'left',
					render: function(rowdata, rowindex, value) {
					  return formatNumber(rowdata.proportion, 2, 1)+"%";
					}
				}, {
					display: '自己开单自己执行',
					name: 'selfmoney',
					align: 'left',
					render: function(rowdata, rowindex, value) {
					  return formatNumber(rowdata.selfmoney, 2, 1);
					}
				}, {
					display: '比率',
					name: 'selfproportion',
					align: 'left',
					render: function(rowdata, rowindex, value) {
					  return formatNumber(rowdata.selfproportion, 2, 1)+"%";
					}
				}
				],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryMultiIncomeByDeptDir.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}
	    function loadDict(){};
	    
	    function openItemDir(obj){
	    	var vo = obj.split("|");
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_id="+vo[2]+"&dept_no="+vo[3]+"&chargeId="+vo[4];
			parent.$.ligerDialog.open({
					title : '医疗项目收入明细',
					height : '500',
					width : '500',
					url : 'hrp/cost/director/comprehensiveanalysis/multiIncomeByChargeTypePage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
	    }
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="toptoolbar"></div>
	<div id="maingrid" style="margin: 0; padding: 0; border: none;"></div>
</body>
</html>