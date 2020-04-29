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
 	       echartsShow();
        }

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
        			display: '医技科室',
        			name: 'dept_name',
        			align: 'left',
	        	  },{
	        		display: '实际',
	        		columns:[{
        			 display: '总收入',
        			 name: 't_1',
        			 align: 'left',
        			 render: function(rowdata, rowindex, value) {
	 						return formatNumber(rowdata.t_1, 2, 1);
	 				}
		           },{
        			  display: '总成本',
        			  name: 't_2',
        			  align: 'left',
        			  render: function(rowdata, rowindex, value) {
 	 						return formatNumber(rowdata.t_2, 2, 1);
 	 				 }
			        },{
        			   display: '总收益',
        			   name: 't_3',
        			   align: 'left',
        			   render: function(rowdata, rowindex, value) {
	 						return formatNumber(rowdata.t_3, 2, 1);
	 				 }
				      },{
	        			 display: '工作量',
	        			 name: 't_4',
	        			 align: 'left',
	        			 render: function(rowdata, rowindex, value) {
		 						return formatNumber(rowdata.t_4, 2, 1);
		 				 }
					 }]
		         },{
	        		display: '保本',
	        		columns:[{
	        			 display: '保本工作量',
	        			 name: 't_5',
	        			 align: 'left',
	        			 render: function(rowdata, rowindex, value) {
	        					if(rowdata.t_5>0){
	    							return "<a href=javascript:open('" +
	    							$("#year_month_begin").val() + "|" +
	    							$("#year_month_end").val() + "|" +
	    							rowdata.dept_code +
	    						  "')>" + formatNumber(rowdata.t_5, 2, 1) + "</a>";
	    						}else {
	    							return formatNumber(rowdata.t_5, 2, 1);
	    						}
	 					 }
			           },{
	        			  display: '保本总收入',
	        			  name: 't_6',
	        			  align: 'left',
	        			  render: function(rowdata, rowindex, value) {
	  						return formatNumber(rowdata.t_6, 2, 1);
	  					 }
				      }]
		         },{
	        		display: '收益测算',
	        		render: function(rowdata, rowindex, value) {
	            		  return "<a href=javascript:openCalculation('" +
							  $("#year_month_begin").val() + "|" +
							  $("#year_month_end").val() + "|" +
								rowdata.dept_code +
						   "')>" + '测算' + "</a>";
	   					 }
		         }
        	   ],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostBreakevenPointMedical.do?isCheck=false',
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
					url : 'hrp/cost/director/breakevenpointanalysis/costBreakevenPointMedicalChartMainPage.do?isCheck=false' + parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});
			}

		 function openCalculation(obj){
				var vo = obj.split("|");
				var parm ='&'+ "year_month_begin="+vo[0] + "&year_month_end="+vo[1] +"&dept_code="+vo[2];
				   $.ligerDialog.open({ url : 'costBreakevenPointMedicalCalculationMainPage.do?isCheck=false&' + parm,
					   data:{}, 
					   height: 500,
					   width: 920,
					   title:'测算',
					   modal:true,
					   showToggle:false,
					   showMax:false,
					   showMin: false,
					   isResize:true,
					   buttons: [{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

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
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0; border: none;"></div>
</body>
</html>