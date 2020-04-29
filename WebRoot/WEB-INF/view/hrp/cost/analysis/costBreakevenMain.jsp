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
		
		
      $(function(){
    	  loadHead(null);//加载数据
    	  loadDict();
      });

      //查询
      function query(){

    	  queryGrid();
      };

         function queryGrid(){
             
        	 grid.options.parms=[];
       	     grid.options.newPage=1;
       	   //根据表字段进行添加查询条件，获取年份和月份
        	 grid.options.parms.push({name:'year_month_begin',value:$("#year_month_begin").val()}); 
        	 grid.options.parms.push({name:'year_month_end',value:$("#year_month_end").val()}); 
  	       	//加载查询条件
  	         grid.loadData(grid.where);
          }

           
      function loadDict(){
    	  //年月的初始化
    	  autodate("#year_month_begin","yyyyMM");
    	  autodate("#year_month_end","yyyyMM");
    	  $("#year_month_begin").ligerTextBox({width:120});
          $("#year_month_end").ligerTextBox({width:120});
          $(':button').ligerButton({ width: 80 });
          
       };
      
		function loadHead(){
			initGrid();
		}
		function initGrid(){
				grid =  $("#maingrid").ligerGrid({
					  columns: [
					     { display: '序列', name: 't_id', align: 'left',width:'4%'},
				         { display: '科室', name: 'dept_name', align: 'left',width:'12%'},
				         { display: '收益',columns:[
							{ display: '收入', name: 't_1', align: 'right',
         				         render : function(rowdata, rowindex,value) {
         				        	return "<a href=javascript:openUpdate('"
	         				    		+rowdata.dept_id+ "|" 
	         				    		+rowdata.dept_no+ "|"
	         				    		+$("#year_month_begin").val()+ "|" 
	         				    		+$("#year_month_end").val()+ "|" 
         				    		    +0+"')>"+formatNumber(rowdata.t_1,2,1);+"</a>";
									}
			               },
							{ display: '成本', name: 't_2', align: 'right',width:'12%',
        				         render : function(rowdata, rowindex,value) {
        				        	 return "<a href=javascript:openUpdate('"
        				        	    +rowdata.dept_id+ "|" 
	         				    		+rowdata.dept_no+ "|"
	         				    		+$("#year_month_begin").val()+ "|" 
	         				    		+$("#year_month_end").val()+ "|" 
          				    		    +1+"')>"+formatNumber(rowdata.t_2,2,1);+"</a>";
        				         }
			                  },
							{ display: '收益', name: 't_3', align: 'right',width:'12%',
       				         render : function(rowdata, rowindex,value) {
								   return formatNumber(rowdata.t_3,2,1);
								}},
							{ display: '成本收益率', name: 't_4', align: 'right',width:'12%',
       				         render : function(rowdata, rowindex,value) {
								   return formatNumber(rowdata.t_4,2,1);
								}},
				         				       ]},
				         { display: '工作量',columns:[
   							{ display: '门诊人次', name: 't_5', align: 'right',width:'12%',
       				         render : function(rowdata, rowindex,value) {
								   return formatNumber(rowdata.t_5,2,1);
								}},
   							{ display: '住院床日', name: 't_6', align: 'right',width:'12%',
       				         render : function(rowdata, rowindex,value) {
								   return formatNumber(rowdata.t_6,2,1);
								}},
   							{ display: '医技工作量', name: 't_7', align: 'right',
        				         render : function(rowdata, rowindex,value) {
									   return formatNumber(rowdata.t_7,2,1);
									}},
   				         ]}
				         
				       ],
			           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostBreakeven.do',
			            height: '100%', checkbox:false,rownumbers:true,delayLoad :true,
			           selectRowButtonOnly:true,
			           });
		           gridManager = $("#maingrid").ligerGetGridManager();
			}

		 function openUpdate(obj){
	
		    	var vo = obj.split("|");
		    	var parm ='&'+ "dept_id="+vo[0] + '&'+ "dept_no="+vo[1] +'&'+ "year_month_begin="+vo[2] +'&'+ "year_month_end="+vo[3] +'&'+ "state="+vo[4] 

		    	$.ligerDialog.open({
		    		url: 'costBreakevenDetailMainPage.do?isCheck=false'+parm,
		    		title:'',
		    		height: 500,
		    		width: 450,
		    		modal: true,
		    		showToggle: false,
		    		showMax: false,
		    		showMin: true,
		    		isResize: true,
		    	});
		  
		
		    }

			
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="left" class="l-table-edit-td"><input type="button" value=" 查询" onclick="query();" /></td>
	 	</tr>
	 </table>
	 <div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>