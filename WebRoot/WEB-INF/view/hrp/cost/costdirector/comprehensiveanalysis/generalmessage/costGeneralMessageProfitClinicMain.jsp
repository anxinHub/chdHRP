<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合信息分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;//收益状况分析-全成本
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
       	 grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
       	 grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()});
      	 grid.options.parms.push({name:'dept_code',value:"${dept_code}"=="undefined"?"":"${dept_code}"});
      	 grid.options.parms.push({name:'natur_code',value:"${natur_code}"=="null"?"":"${natur_code}"}); 
 	       	//加载查询条件
 	     grid.loadData(grid.where);
        }

      function loadDict(){

      	  autocomplete("#dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true,"",false,"${dept_id}.${dept_no}.${dept_code}");
    	  $("#year_month_begin").ligerTextBox({width:120, disabled:true});
          $("#year_month_end").ligerTextBox({width:120, disabled:true});
          $("#dept_code").ligerComboBox({disabled:true});
          
       };
      
		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
        			display: '门诊科室',
        			name: 'dept_name',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						if(rowdata.t_7>0){
							return "<a href=javascript:open('" +
							$("#year_month_begin").val() + "|" +
							$("#year_month_end").val() + "|" +
							rowdata.dept_code +
						  "')>" + rowdata.dept_name + "</a>";
						}else {
							return rowdata.dept_name
						}
					}
	        	},{
        			display: '门诊人次',
        			name: 't_1',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return formatNumber(rowdata.t_1, 2, 1);
					}
	        	},{
        			display: '单位收入',
        			name: 't_2',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return formatNumber(rowdata.t_2, 2, 1);
					}
	        	},{
        			display: '单位变动成本',
        			name: 't_3',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return formatNumber(rowdata.t_3, 2, 1);
					}
	        	},{
        			display: '单位收益',
        			name: 't_4',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return formatNumber(rowdata.t_4, 2, 1);
					}
	        	},{
        			display: '固定成本',
        			name: 't_5',
        			align: 'left',
        			render: function(rowdata, rowindex, value) {
						return "<a href=javascript:openCost('" +
							$("#year_month_begin").val() + "|" +
							$("#year_month_end").val() + "|" +
							rowdata.dept_code +  "|" +
							1 +
						    "')>" + formatNumber(rowdata.t_5, 2, 1) + "</a>";
					}
	        	},{
        			display: '变动成本',
        			name: 't_6',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return "<a href=javascript:openCost('" +
						 $("#year_month_begin").val() + "|" +
						 $("#year_month_end").val() + "|" +
						 rowdata.dept_code +  "|" +
						 2 +
					    "')>" +  formatNumber(rowdata.t_6, 2, 1) + "</a>";
					}
	        	},{
        			display: '保本诊次',
        			name: 't_7',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return formatNumber(rowdata.t_7, 2, 1);
					}
	        	},{
        			display: '保本收入',
        			name: 't_8',
        			align: 'left',
					render: function(rowdata, rowindex, value) {
						return formatNumber(rowdata.t_8, 2, 1);
					}
	        	}
        	],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostGeneralMessageProfitClinic.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:false,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}

		function open(obj){
			var vo = obj.split("|");
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_code="+vo[2];
			parent.$.ligerDialog.open({
					title : '本量利分析',
					height : '500',
					width : '500',
					url : 'hrp/cost/director/comprehensiveanalysis/costGeneralMessageProfitClinicChartMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
			}

		function openCost(obj){
			var vo = obj.split("|"); 
			var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_code="+vo[2]+"&nature_id="+vo[3];
			parent.$.ligerDialog.open({
				title : '本量利分析',
				height : '500',
				width : '500',
				url : 'hrp/cost/director/comprehensiveanalysis/costGeneralMessageProfitClinicCostMainPage.do?isCheck=false' + parm,
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
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" value="${year_month_begin}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" value="${year_month_end}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
           <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" /></td>
	 	</tr>
	 </table>
	   <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>