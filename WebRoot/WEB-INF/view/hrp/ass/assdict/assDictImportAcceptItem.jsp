<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	loadHead(null);	
    }); 

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '验收项目编码', name: 'accept_item_code', align: 'left',width:'50%'
					 		},
                     { display: '验收项目名称', name: 'accept_item_name', align: 'left',width:'50%'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryDictAssAcceptItemDict.do?ass_id=${ass_id}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function this_close(){
    	frameElement.dialog.close();
    }
    
    function importAcceptItemAffi(obj){
    	var data = gridManager.getCheckedRows();
    	if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		}else{
			obj.gridData = data;
			this_close();
		}
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="maingrid"></div>
</body>
</html>
