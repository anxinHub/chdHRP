<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本量利分析</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

		var grid;
		var gridManager = null;
		var dept_name = '${item_name}' +'科室'
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
    	
      });

	    
           
      function loadDict(){
    	  $("#year_month_begin").ligerTextBox({width:120,disabled:true});
          $("#year_month_end").ligerTextBox({width:120,disabled:true});
          
       };
      
		function loadHead(){
			initGrid();
			initColumns();
		}

		function initGrid(){
				grid =  $("#maingrid").ligerGrid({
			           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostVolumeProfitDetail.do?isCheck=false&year_month_begin='+'${year_month_begin}'+'&year_month_end='+'${year_month_end}'+'&natur_code='+'${item_code}',
			           width: '100%', height: '100%', checkbox:false,rownumbers:true,delayLoad :false,
			           selectRowButtonOnly:true,width: '100%', height: '100%',
			           onDblClickRow : function (data, rowindex, rowobj)
			           {
			        	     if(!data.t_4 || data.t_6 <= 0){
                                 return false;
				        	  }
			        	   openUpdate(data.dept_code,data.natur_code);

			        	 } 
			           });
		           gridManager = $("#maingrid").ligerGetGridManager();
			}

		  function initColumns(){
			    if('${item_code}' =='01')initColumnsM()
			    if('${item_code}' =='02')initColumnsZ()
			    if('${item_code}' =='03')initColumnsY()
			  }

		  //门诊
		  function initColumnsM(){
			  var columns = [{
					display: dept_name,
					name: 'dept_name',
					align: 'left'
				},
				{
					display: '实际',
					columns: [{
							display: '医疗收入',
							name: 't_1',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_1, 2, 1);
							}
						},
						{
							display: '固定成本',
							name: 't_2',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_2, 2, 1);
							}
						},
						{
							display: '变动成本',
							name: 't_3',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_3, 2, 1);
							}
						},{
							display: '收支结余',
							name: 't_4',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_4, 2, 1);
							}
						},
						{
							display: '门诊人次',
							name: 't_5',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_5, 2, 1);
							}
						},
						{
							display: '单位收入',
							name: 't_6',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_6, 2, 1);
							}
						},
						{
							display: '单位变动成本',
							name: 't_7',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_7, 2, 1);
							}
						},
						{
							display: '单位结余',
							name: 't_8',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_8, 2, 1);
							}
						},
					]
				},
				{
					display: '保本',
					columns: [{
							display: '保本急人次',
							name: 't_9',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								 if(rowdata.t_9>0){
								  return formatNumber(rowdata.t_9, 2, 1);
								}else{
									return '-'
								}
							}
						},
						{
							display: '保本总收入',
							name: 't_10',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_10, 2, 1);
							}
						},
					]
				}
			]
		       
		       grid.set('columns', columns);
			}

		  //住院
		  function initColumnsZ(){
			  var columns = [{
					display: dept_name,
					name: 'dept_name',
					align: 'left'
				},
				{
					display: '实际',
					columns: [{
							display: '医疗收入',
							name: 't_1',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_1, 2, 1);
							}
						},{
							display: '固定成本',
							name: 't_2',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_2, 2, 1);
							}
						},
						{
							display: '变动成本',
							name: 't_3',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_3, 2, 1);
							}
						},{
							display: '收支结余',
							name: 't_4',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_4, 2, 1);
							}
						},
						{
							display: '床日数',
							name: 't_5',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_5, 2, 1);
							}
						},{
							display: '床位使用率',
							name: 'work',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.work, 2, 1) + '%';
							}
						},
						{
							display: '单位收入',
							name: 't_6',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_6, 2, 1);
							}
						},
						{
							display: '单位变动成本',
							name: 't_7',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_7, 2, 1);
							}
						},
						{
							display: '单位结余',
							name: 't_8',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_8, 2, 1);
							}
						},
					]
				},
				{
					display: '保本',
					columns: [{
							display: '保本床日',
							name: 't_9',
							align: 'right',
							render: function(rowdata, rowindex, value) {
							 if(rowdata.t_9>0){
								  return formatNumber(rowdata.t_9, 2, 1);
								}else{
									return '-'
								}
							}
						},
						{
							display: '保本总收入',
							name: 't_10',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_10, 2, 1);
							}
						},
					]
				}
			]
		       
		       grid.set('columns', columns);
			}

		//医技
		  function initColumnsY(){
			  var columns = [{
					display: dept_name,
					name: 'dept_name',
					align: 'left'
				},
				{
					display: '实际',
					columns: [{
							display: '医疗收入',
							name: 't_1',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_1, 2, 1);
							}
						},
						{
							display: '固定成本',
							name: 't_2',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_2, 2, 1);
							}
						},
						{
							display: '变动成本',
							name: 't_3',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_3, 2, 1);
							}
						},{
							display: '收支结余',
							name: 't_4',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_4, 2, 1);
							}
						},
						{
							display: '工作量',
							name: 't_5',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_5, 2, 1);
							}
						},
						{
							display: '单位收入',
							name: 't_6',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_6, 2, 1);
							}
						},
						{
							display: '单位变动成本',
							name: 't_7',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_7, 2, 1);
							}
						},
						{
							display: '单位结余',
							name: 't_8',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_8, 2, 1);
							}
						},
					]
				},
				{
					display: '保本',
					columns: [{
							display: '保本床日',
							name: 't_9',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_9, 2, 1);
							}
						},
						{
							display: '保本总收入',
							name: 't_10',
							align: 'right',
							render: function(rowdata, rowindex, value) {
								return formatNumber(rowdata.t_10, 2, 1);
							}
						},
					]
				}
			]
		       
		       grid.set('columns', columns);
			}


			function openUpdate(dept_code,natur_code){

				var parm ='&'+ "year_month_begin="+'${year_month_begin}' + "&year_month_end="+'${year_month_end}' +"&dept_code="+dept_code +"&natur_code="+natur_code
				parent.$.ligerDialog.open({
					title : '科室本量利分析',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/cost/analysis/costVolumeProfitDetailChartMainPage.do?isCheck=false'+parm,
					modal : false,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : false,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

			}
</script>
</head>
<body>
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" value = '${year_month_begin}' /></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" value = '${year_month_end}' /></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>