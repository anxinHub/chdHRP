<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	 $(function (){
		 loadDict();
    	 loadHead(null);	
	 });
	 
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'columns', name: 'year_month', align: 'left'},
                     { display: 'columns', name: 'dept_code', align: 'left'},
                     { display: 'columns', name: 'dept_name', align: 'left'},
                     { display: 'columns',name: 'patient_type_code', align: 'left'},
					 { display: 'columns',name: 'patient_name', align: 'left'},
                     { display: 'columns',name:'clinic_num', align: 'left'},
                     { display: 'columns', name:'operation_num', align: 'left'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostClinicWork.do',delayLoad:true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '按钮', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '按钮', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '按钮', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
    				 ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_open(){}
    function remove(){}
    function loadDict(){
    	$("#patient_type_code").ligerTextBox({width:180});
    	$("#patient_type_codes").ligerComboBox({width:180});
    }  
    
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
 <div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文本框：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_type_code" type="text" id="patient_type_code" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">下拉框：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_type_codes" type="text" id="patient_type_codes" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
