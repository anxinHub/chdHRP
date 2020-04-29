<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
    	
    	loadHead(null);	//加载数据
    	
    	
    });

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		  columns: [ 
                     { display: '对账日期', name: '', align: 'left',
		                    totalSummary:
		                    {
		                        render: function (suminf, column, cell)
		                        {
		                            return '<div>合计</div>';
		                        },
		                        align: 'left'
		                    }
                     },
                     { display: '凭证号', name: '', align: 'left'
   					 },
   					 { display: '摘要', name: '', align: 'left'
   					 },
   					 { display: '对方科目', name: '', align: 'right'
   					 },
   					 { display: '收支方向', name: '', align: 'right'
   					 },
   					 { display: '金额', name: '', align: 'right',
   						render:function(rowdata){
  	   						 return formatNumber(1, 2, 1);
  						 },
		                    totalSummary:
		                    {
		                        type: 'sum',
		                    }
   					 }
                        ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:false,
                     selectRowButtonOnly:true
                   });

    }

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
	<div id="maingrid"></div>

</body>
</html>
