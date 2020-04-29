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
    var veri_check_id = '${veri_check_id}';
    var direct = '${direct}';
    var objDirect = '${objDirect}';
    var check_method = "${a018}";//判断是对账方式
    $(function (){
    	loadHead(null);	//加载数据
    });
    
  	//查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'veri_check_id',value : veri_check_id});
    	grid.options.parms.push({name:'direct',value : direct});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if(direct=="L"){
    		grid = $("#maingrid").ligerGrid({
      		  	columns: [  
					{ display: '日期', name: 'vouch_date', align: 'left',width:80},
					{ display: '结算方式', name: 'pay_name', align: 'left',width:100},
                    { display: '凭证号', name: 'vouch_no', align: 'left',width:80},
                    { display: '票据号', name: 'check_no', align: 'left',width:100},
     				{ display: '摘要', name: 'summary', align: 'left',width:120},
     				{ display: '借方金额', name: 'debit', align: 'right',width:80,
     					render:function(rowdata){
     						return formatNumber(rowdata.debit,2,1);
    					}
     				},{ display: '贷方金额', name: 'credit', align: 'right',width:80,
     					render:function(rowdata){
     						return formatNumber(rowdata.credit,2,1);
    					}
     				},{ display: '已对账金额', name: 'yCheck_money', align: 'right',width:100,
     					render:function(rowdata){
     						return formatNumber(rowdata.yCheck_money,2,1);
    					}	
     				},
     				,{ display: '对账时间', name: 'veri_date', align: 'left',width:100},
     				,{ display: '对账人', name: 'create_name', align: 'left',width:100}
              	],
              	dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBankVeriDetail.do?isCheck=false&veri_check_id='+veri_check_id+'&direct='+direct+'&check_method='+check_method,
              	width: '100%', height: '100%', checkbox: false,rownumbers:false,
              	selectRowButtonOnly:true
           });
    	}else{
    		grid = $("#maingrid").ligerGrid({
      		  	columns: [  
					{ display: '日期', name: 'veri_date', align: 'left',width:80},
                    { display: '结算方式', name: 'pay_name', align: 'left',width:100},
                    { display: '票据号', name: 'check_no', align: 'left',width:100},
     				{ display: '摘要', name: 'summary', align: 'left',width:120},
     				{ display: '借方金额', name: 'debit', align: 'right',width:80,
     					render:function(rowdata){
     						return formatNumber(rowdata.debit,2,1);
    					},
  		                
     				},{ display: '贷方金额', name: 'credit', align: 'right',width:80,
     					render:function(rowdata){
     						return formatNumber(rowdata.credit,2,1);
    					},
  		                
     				},{ display: '已对账金额', name: 'yCheck_money', align: 'right',width:100,
     					render:function(rowdata){
     						return formatNumber(rowdata.yCheck_money,2,1);
    					}	
     				},
     				,{ display: '对账时间', name: 'veri_date', align: 'left',width:100},
     				,{ display: '对账人', name: 'create_name', align: 'left',width:100}
              	],
              	dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccBankVeriDetail.do?isCheck=false&veri_check_id='+veri_check_id+'&direct='+direct,
              	width: '100%', height: '100%', checkbox: false,rownumbers:false,
              	selectRowButtonOnly:true
    		});
    	}
    	gridManager1 = $("#maingrid").ligerGetGridManager();
    }

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>	
	<div id="maingrid"></div>
</body>
</html>
