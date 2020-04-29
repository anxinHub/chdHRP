<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<title></title>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
	    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    
    	 $(':button').ligerButton({width:80});
    });
  
    
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
                      { display: '资金来源编码', name: 'source_code', align: 'left'
 					 },
                      { display: '资金来源名称', name: 'source_name', align: 'left'
 					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssPaySourceBySetting.do?isCheck=false&pay_code=${pay_code}',
                      width: '100%', height: '90%', checkbox: true,rownumbers:true,
                      selectRowButtonOnly:true,
                      toolbar: { items: [
       					{ text: '保存', id:'save', click: save, icon:'add' },
       				]}
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
    }

    function save(){
    	var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push("${pay_code}" +"@"+this.source_id);
						
					});
				ajaxJsonObjectByUrl("saveAssPaySourceMain.do?isCheck=false", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
						parentFrameUse().query2()();
					}
				});
		}
    }
    
    function loadDict(){
            //字典下拉框
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    		
    		<div id="maingrid"></div>

</body>
</html>
