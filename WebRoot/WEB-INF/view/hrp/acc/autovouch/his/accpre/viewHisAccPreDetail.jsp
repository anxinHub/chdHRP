<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var state=dialog.get("data").state;
	var charge_date_start=dialog.get("data").charge_date_start;
	var charge_date_end=dialog.get("data").charge_date_end;
	var pay_type_code=dialog.get("data").pay_type_code;
    var grid;
    
    var gridManager = null;
    
    var rep_no = '${rep_no}';

    var charge_code_equal =  '${charge_code_equal}';
    
    var charge_date = '${charge_date}';
    var io_type='${io_type}';
    
    var parms ={
    		rep_no:rep_no,charge_code_equal:charge_code_equal,charge_date:charge_date,io_type:io_type,state:state,
    		charge_date_start:charge_date_start,charge_date_end:charge_date_end,pay_type_code:pay_type_code
    		};
    
    $(function ()
    {
    	
    	loadHead(null);	
        
    });

    
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
					columns : [
									{display : '日报序号',name : 'rep_no',width: 120,align : 'left'}, 
									{display : '收费日期',name : 'charge_date',align : 'left',width: 120,align : 'left'},
									{display : '收款员编码',name : 'charge_code',width: 120,align : 'left'},
									{display : '收款员名称',name : 'charge_name',align : 'left',width: 120,align : 'left'},
									{display : '收费金额',name : 'charge_money',align : 'left',width: 120,align : 'right',
										render: function (item) {
											return formatNumber(item.charge_money,2,1);
			                    		}	
									},
									{display : '状态',name : 'state',align : 'left',width: 120,align : 'left'},
									{display : '支付方式',name : 'pay_type_code',align : 'left',width: 120,align : 'left'},
									{display : '卡号',name : 'card_no',align : 'left',width: 120,align : 'left'},
									{display : '患者姓名',name : 'patient_name',align : 'left',width: 120,align : 'left'},
									{display : '诊疗编号',name : 'hospital_no',align : 'left',width: 120,align : 'left'},
									{display : '票据开始号',name : 'begin_no',align : 'left',width: 120,align : 'left'},
									{display : '票据结束号',name : 'end_no',align : 'left',width: 120,align : 'left'}
									
							],
					dataAction: 'server',dataType: 'server',usePager:true,url:'queryHisAccPreDetail.do',width: '100%', height: '100%', checkbox: false,parms:parms,
					rownumbers:true,selectRowButtonOnly:true
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}
</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>

    <div id="maingrid"></div>

</body>
</html>