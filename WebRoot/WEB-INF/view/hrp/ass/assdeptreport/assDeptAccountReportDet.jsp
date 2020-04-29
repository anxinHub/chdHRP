<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    var userUpdateStr;
    
    $(function (){
    	loadHead();
        query();
    });
    
    //查询
    function query(){
    	
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ass_naturs',value:'${ass_nature}'}); 
    	grid.options.parms.push({name:'dept_id',value:'${dept_id}'}); 
    	grid.options.parms.push({name:'year_month',value:'${year_month}'}); 
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '卡片编号', name: 'ass_card_no', align: 'left'},
                     { display: '资产名称', name: 'ass_name', align: 'left'},
                     { display: '规格', name: 'ass_spec', align: 'left'
             		 },
             		 { display: '型号', name: 'ass_mondl', align: 'left'
            		 },
                     { display: '原值', name: 'price', align: 'left'
                     },
                     { display: '折旧', name: 'depre_money', align: 'left'
                     },
                     { display: '使用状态', name: 'state_name', align: 'left'
                     },
                     { display: '入库时间', name: 'in_date', align: 'left'
                     },
                     { display: '条形码', name: 'bar_code', align: 'left'
                     }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAssDeptAccountReportDet.do?isCheck=false&dept_id=${dept_id}&year_month=${year_month}&ass_naturs=${ass_nature}',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:false,
                     toolbar: { items: [
                         { text: '关闭',
								id : 'close',
								click : this_close,
								icon : 'candle' }
					     
                 	 ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function this_close() {
		frameElement.dialog.close();
	}
	    	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>
</body>
</html>
