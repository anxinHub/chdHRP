<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科室盈亏分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });
    
       function query(){
       	 grid.options.parms=[];
      	 grid.options.newPage=1;
      	   //根据表字段进行添加查询条件，获取年份和月份
       	    grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
       	    grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
       	    grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
       	    grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]}); 
       	    grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue().split(".")[2]}); 
 	       	//加载查询条件
 	       grid.loadData(grid.where);
        }
       
     //打印 
       function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#year_month_begin").val()+"至"+$("#year_month_end").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "分摊汇总表",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.director.CostProfitandlossService",
	 	   			method_name: "queryCostSharePrint",
	 	   			bean_name: "costProfitandlossService",
	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	 	   			
	 	       	};
	 	      //执行方法的查询条件
	 		  $.each(grid.options.parms,function(i,obj){
	 			printPara[obj.name]=obj.value;
	  	      });
	 		
	  	     officeGridPrint(printPara);	
	    }

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
					display: '科室',
					name: 'dept_name',
					align: 'left',
				    render: function(rowdata, rowindex, value) {
					   if(rowdata.dept_code !=null && rowdata.dept_code !='01'&& rowdata.dept_code !='02'){
					   return "<a href=javascript:openCost('" +
						$("#year_month_begin").val() + "|" +
						$("#year_month_end").val() + "|" +
						rowdata.dept_code+
					  "')>" + rowdata.dept_name + "</a>";
					   }else {
						   return rowdata.dept_name;
						  }
				  }
				}, {
					display: '总成本',
					name: 't_1',
					align: 'left',
					render: function(rowdata, rowindex, value) {
					  return formatNumber(rowdata.t_1, 2, 1);
					}
				}, {
					display: '直接成本',
					columns: [{
						display: '直接计入',
						columns: [{
							display: '金额',
							name: 't_2',
							align: 'left',
						    render: function(rowdata, rowindex, value) {
								   return "<a href=javascript:openCostDir('" +
									$("#year_month_begin").val() + "|" +
									$("#year_month_end").val() + "|" +
									rowdata.dept_code+ "|" +
									rowdata.natur_code+
								  "')>" + formatNumber(rowdata.t_2, 2, 1) + "</a>";
							  }
						}, {
							display: '比例',
							name: 't_3',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_3, 2, 1)+ '%';
							}
						}]
					}]
				}, {
					display: '间接成本',
					columns: [{
						display: '分摊管理成本',
						columns: [{
							display: '金额',
							name: 't_4',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_4, 2, 1);
							}
						}, {
							display: '比例',
							name: 't_5',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_5, 2, 1)+ '%';
							}
						}]
					}, {
						display: '分摊医辅成本',
						columns: [{
							display: '金额',
							name: 't_6',
							align: 'left',
						    render: function(rowdata, rowindex, value) {
								   return "<a href=javascript:openCostAss('" +
									$("#year_month_begin").val() + "|" +
									$("#year_month_end").val() + "|" +
									rowdata.dept_code+ "|" +
									rowdata.natur_code+
								  "')>" + formatNumber(rowdata.t_6, 2, 1) + "</a>";
							  } 
						}, {
							display: '比例',
							name: 't_7',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_7, 2, 1)+ '%' ;
							}
						}]
					}, {
						display: '分摊医技成本',
						columns: [{
							display: '金额',
							name: 't_8',
							align: 'left',
							 render: function(rowdata, rowindex, value) {
								   return "<a href=javascript:openCostMed('" +
									$("#year_month_begin").val() + "|" +
									$("#year_month_end").val() + "|" +
									rowdata.dept_code+ "|" +
									rowdata.natur_code+
								  "')>" + formatNumber(rowdata.t_8, 2, 1) + "</a>";
							  } 
						}, {
							display: '比例',
							name: 't_9',
							align: 'left',
							render: function(rowdata, rowindex, value) {
								 return formatNumber(rowdata.t_9, 2, 1) + '%';
							}
						}]
					}]
				}],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostShare.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}

		function openCost(obj){
			var vo = obj.split("|");
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_code="+vo[2];
			parent.$.ligerDialog.open({
					title : '科室成本',
					height : '500',
					width : '500',
					url : 'hrp/cost/director/profitandloss/costShareCostMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
			}

		function openCostDir(obj){ 
			var vo = obj.split("|");
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_code="+vo[2] +"&natur_code="+vo[3];
			parent.$.ligerDialog.open({
					title : '科室成本',
					height : '500',
					width : '500',
					url : 'hrp/cost/director/profitandloss/costShareCostDirMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
			}
		

		function openCostAss(obj){
			var vo = obj.split("|");
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_code="+vo[2] +"&natur_code="+vo[3];
			parent.$.ligerDialog.open({
					title : '科室成本',
					height : '500',
					width : '500',
					url : 'hrp/cost/director/profitandloss/costShareCostAssMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
			}

		function openCostMed(obj){
			var vo = obj.split("|");
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_code="+vo[2] +"&natur_code="+vo[3];
			parent.$.ligerDialog.open({
					title : '科室成本',
					height : '500',
					width : '500',
					url : 'hrp/cost/director/profitandloss/costShareCostMedMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
			}
		
		
	    function loadDict(){

	    	  autocomplete("#dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true);
	    	  
	    	  //年月的初始化
	    	  autodate("#year_month_begin","yyyyMM");
	    	  autodate("#year_month_end","yyyyMM");
	    	  $("#year_month_begin").ligerTextBox({width:120});
	          $("#year_month_end").ligerTextBox({width:120});
	          $(':button').ligerButton({ width: 80 });
	       };
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
           <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="查询" onclick="query();" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="打印" onclick="print();" /></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0; border: none;"></div>
</body>
</html>