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


    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '收费项目', name: 'charge_item_name', align: 'left',
                    	 totalSummary:{
 							type:'sum',
 							render:function(suminf,column,cell){
 								return '<div>合计</div>';
 							}
 						}
					 },
                     { display: '单价', name: 'price', align: 'left'
					 },
                     { display: '数量', name: 'num', align: 'left'
					 },
                     { display: '金额', name: 'money', align: 'right'
						 ,totalSummary: {
	                      type: 'sum',
	                      render: function (suminf, column, cell) {
	                         return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
	                      }
	                 	}
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../costincomedetail/queryCostIncomeDetail.do',
                     url:'../costincomedetail/queryCostIncomeDetail.do?isCheck=false&group_id='+"${group_id}"+'&hos_id='+"${hos_id}"+'&copy_code='+"${copy_code}"+'&acc_year='+"${acc_year}"+'&acc_month='+"${acc_month}"+'&appl_dept_no='+"${appl_dept_no}"+'&exec_dept_no='+"${exec_dept_no}"+'&charge_kind_id='+"${charge_kind_id}", 
                    		 width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
                      /* { text: '查询', id:'search', click: query,icon:'search' }, */
    					
    				]}, 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  
    function loadDict(){
            //字典下拉框
    	
		 
         }  
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    </table>

	<div id="maingrid"></div>

</body>
</html>
