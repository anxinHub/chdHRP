<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开单收入分析</title>
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
       	    grid.options.parms.push({name:'appl_dept_id',value:liger.get("appl_dept_code").getValue().split(".")[0]}); 
       	    grid.options.parms.push({name:'appl_dept_no',value:liger.get("appl_dept_code").getValue().split(".")[1]}); 
       	    grid.options.parms.push({name:'appl_dept_code',value:liger.get("appl_dept_code").getValue().split(".")[2]});
     	    grid.options.parms.push({name:'appl_dept_id',value:liger.get("appl_dept_code").getValue().split(".")[0]}); 
       	    grid.options.parms.push({name:'appl_dept_no',value:liger.get("appl_dept_code").getValue().split(".")[1]}); 
       	    grid.options.parms.push({name:'appl_dept_code',value:liger.get("appl_dept_code").getValue().split(".")[2]}); 
       	    grid.options.parms.push({name:'exec_dept_id',value:liger.get("exec_dept_code").getValue().split(".")[0]}); 
     	    grid.options.parms.push({name:'exec_dept_no',value:liger.get("exec_dept_code").getValue().split(".")[1]}); 
     	    grid.options.parms.push({name:'exec_dept_code',value:liger.get("exec_dept_code").getValue().split(".")[2]}); 
     	    grid.options.parms.push({name:'state',value:liger.get("state").getValue()});
 	       	//加载查询条件
 	       grid.loadData(grid.where);
        }

		function loadHead(){
			grid =  $("#maingrid").ligerGrid({
				columns: [{
					display: '开单科室',
					name: 'appl_dept_name',
					align: 'left',
				},{
					display: '执行科室',
					name: 'exec_dept_name',
					align: 'left',
				},{
					display: '收入',
					name: 'money',
					align: 'left',
					render: function(rowdata, rowindex, value) {
						 return formatNumber(rowdata.money, 2, 1);
					}
				},{
					display: '收入比',
					name: 't',
					align: 'left',
				}],
	           dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDeptRevenueOpeningOrder.do?isCheck=false',
	           width: '100%',height: '100%',checkbox:true,rownumbers:true,delayLoad :true,
	           selectRowButtonOnly:true,checkBoxDisplay : f_checkBoxDisplay
	           });
	           gridManager = $("#maingrid").ligerGetGridManager();
		}

	    function f_checkBoxDisplay(rowdata){
	     	 if (rowdata.dept_name == "合计")
	  			    return false;
	  		      return true;
	  	       }
	       
	    function loadDict(){

	    	  autocomplete("#appl_dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true);
	    	  autocomplete("#exec_dept_code","../../queryDeptDictCode.do?isCheck=false","id","text",true,true);
	    	  
	    	    $("#state").ligerComboBox({
	                width : 180,
	                data: [
	                    { text: '本科室开单本科室执行', id: 1 },
	                    { text: '本科室开单他科室执行', id: 2 }
	                ],  
	                initIsTriggerEvent: false,
	            });
		    	  
	    	  
	    	  //年月的初始化
	    	  autodate("#year_month_begin","yyyyMM");
	    	  autodate("#year_month_end","yyyyMM");
	    	  $("#year_month_begin").ligerTextBox({width:120});
	          $("#year_month_end").ligerTextBox({width:120});
	          $("#color").ligerTextBox({width:120});
	          $(':button').ligerButton({width:80});
	       };


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
		 	      		title: "科室收入分析-开单收入分析",//标题
		 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
		 	      		class_name: "com.chd.hrp.cost.service.director.CostRevenueAnalysisService",
		 	   			method_name: "queryCostDeptRevenueOpeningOrderPrint",
		 	   			bean_name: "costRevenueAnalysisService",
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
	 <div id="toptoolbar" ></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_begin" type="text" id="year_month_begin" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
	 	   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至</td>
           <td align="left" class="l-table-edit-td"><input name="year_month_end" type="text" id="year_month_end"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
           <td align="left" class="l-table-edit-td"><input name="appl_dept_code" type="text" id="appl_dept_code" /></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室：</td>
           <td align="left" class="l-table-edit-td"><input name="exec_dept_code" type="text" id="exec_dept_code" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="查询" onclick="query();" /></td>
           <td align="left" class="l-table-edit-td"><input type="button" value="打印" onclick="print();" /></td>
	 	</tr>
	 	<tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医疗收入类型：</td>
           <td align="left" class="l-table-edit-td" colspan="3"><input name="state" type="text" id="state" /></td>
	 	</tr>
	 </table>
	    <div id="maingrid" style="margin:0; padding:0; border: none;"></div>
</body>
</html>