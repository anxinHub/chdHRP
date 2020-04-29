<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate" name="plugins" />
	</jsp:include>
    <script>
        
        var initChangeMoneyGrid = function () {
        	var columns = [
                { display: '变更编号', name: 'change_code', width: '14%'},
                { display: '变更日期', name: 'change_date',align: 'center',  width: '8%'},
           	 { display: '协议编号', name: 'pact_code', width: '13%',
               	render : function (data){
               		return '<a class="toPact" rowIndex = "'+data.rowIndx+'">'+data.rowData.pact_code+'</a>'
               	}	 
           	 },
                { display: '协议名称', name: 'pact_name', width: '16%'},
                { display: '供应商', name: 'sup_name',  width: '16%'},
                { display: '变更原因', name: 'change_reason', width: '20%'},
                { display: '操作员', name: 'user_name',  width: '8%'},
           ];
           var paramObj = {
           	editable: false,
           	height: '100%',
           	width:'98.5%',
           	pageModel: false,
            dataModel: {
                url: '../../change/queryPactMainChangeFKXY.do?isCheck=false',
                postData: {
                	pact_code : '${pact_code}'
                }
            },
            columns: columns,
           };
            changeMoneyGrid = $("#changeMoneyGrid").etGrid(paramObj);
        };
        
        
        $(function () {
            initChangeMoneyGrid();
        })
        
    </script>
</head>

<body style="overflow: auto;">
    <hr><h1 style="padding-left: 20px">付款协议变更表</h1> 
    <div id="changeMoneyGrid"></div>
</body>

</html>

