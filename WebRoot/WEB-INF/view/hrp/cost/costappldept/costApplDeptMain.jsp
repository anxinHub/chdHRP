<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'begin_year_month',value:$("#begin_year_month").val()}); 
    	grid.options.parms.push({name:'end_year_month',value:$("#end_year_month").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left'},
                     { display: '开单科室编码', name: 'appl_dept_code', align: 'left'},
                     { display: '开单科室名称', name: 'appl_dept_name', align: 'left'}, 
                     { display: '收费类别名称', name: 'charge_kind_code', align: 'left'},
                     { display: '收费类别名称', name: 'charge_kind_name', align: 'left'},
                     { display: '金额', name: 'money', align: 'right',
						 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.money,2,1);
							}
                     }],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostApplDept.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
		                { text: '打印', id:'print', click: print,icon:'print' }
    				]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
		
		 $("#begin_year_month").ligerTextBox({ width:160 });
	     $("#end_year_month").ligerTextBox({ width:160});
		 autodate("#begin_year_month","yyyyMM");
	 autodate("#end_year_month","yyyyMM");
         }  
    

  	
	 function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#begin_year_month").val()+"至"+$("#end_year_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "开单收入统计",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostApplDeptService",
	 	   			method_name: "queryCostApplDeptPrint",
	 	   			bean_name: "costApplDeptService",
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

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_year_month" type="text" id="begin_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="end_year_month" type="text" id="end_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
