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

    $(function (){//加载数据
    	
        loadDict();//加载下拉框
    	
    	loadHead(null);
    
        query();
    });
    //查询
    function query(){
		grid.options.parms=[];grid.options.newPage=1;
    	grid.loadData(grid.where);//加载查询条件
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
			 		 { display: '编码', name: 'ibps_code', align: 'left',width:150},	
					 { display: '名称', name: 'ibps_name', align: 'left',width:580}
			 		 ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccICBCIBPSMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{text : '导入',id : 'carry',click : importData,icon : 'up'},
						{line : true}
						                
				    ]}
                   });
        gridManager = $("#maingrid").ligerGetGridManager();
    }

    //import
    function  importData(){	
    	$.ligerDialog.open({
            url: 'importAccICBCIBPSPage.do?isCheck=false',
            height: 500,
            width: 820,
            title: '导入',
            modal: true,
            showToggle: false,
            showMax: false,
            showMin: true,
            isResize: true
        });
     }
    
    function loadDict(){//字典下拉框 
    	$("#ibps_code").ligerTextBox({width : 120});
    	$("#ibps_name").ligerTextBox({width : 180});
	} 
	</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
