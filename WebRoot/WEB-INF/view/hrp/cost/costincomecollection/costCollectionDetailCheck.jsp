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
    	grid.options.parms.push({name:'year_month',value:$("#year_month").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left'},
                     { display: '开单科室编码', name: 'appl_dept_code', align: 'left'},
                     { display: '开单科室名称', name: 'appl_dept_name', align: 'left'},
                     { display: '执行科室编码', name: 'exec_dept_code', align: 'left'},
                     { display: '执行科室名称', name: 'exec_dept_name', align: 'left'},
                     { display: '收费类别编码', name: 'charge_kind_code', align: 'left'},
                     { display: '收费类别名称', name: 'charge_kind_name', align: 'left'},
                     { display: '收费项目编码', name: 'charge_item_code', align: 'left'},
                     { display: '收费类别名称', name: 'charge_item_name', align: 'left'},
                     { display: '金额', name: 'money', align: 'right',
							render : function(rowdata, rowindex,
									value) {
								 return formatNumber(rowdata.money,2,1);
							}
					 },
					 { display: '备注', name: 'note', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostCollectionDetailCheck.do?isCheck=false.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
    				]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

   
    function loadDict(){
    	
    	  $("#year_month").ligerTextBox({ width:160 });
    	  autodate("#year_month","yyyyMM");
         }  
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
