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
        loadDict();//加载下拉框
    	loadHead(null);	//加载数据
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	//加载查询条件
    	grid.loadData(grid.where);
	}

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		columns: [ 
				{ display: '材料编码', name: 'inv_code', align: 'left',width: '15%',frozen: true },
				{ display: '材料名称', name: 'inv_name', align: 'left',width: '15%',frozen: true },
                { display: '进价', name: 'price', align: 'left',width: '15%',frozen: true },
                { display: '条码', name: 'bar_code', align: 'left',width: '15%',frozen: true },
				{ display: '结余数量', name: 'left_amount', align: 'right',width: '15%',frozen: true },
				{ display: '结余金额', name: 'left_money', align: 'right',width: '15%',frozen: true },
				{ display: '二维码查看', name: 'left_money', align: 'center',width: '10%',frozen: true,
					render: function (rowdata, rowindex, value) {
						return "<a href='../../../../"+rowdata.group_id+"/"+rowdata.hos_id+"/"+rowdata.copy_code+"/mat/dura/"+rowdata.bar_code+".png' target='_blank'>查看</a>";
					}	
				},
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDuraQueryQrCode.do',
            width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
            selectRowButtonOnly:true,//heightDiff: -10,
            toolbar: { items: [
            		{ text: '查询 ', id:'search', click: query, icon:'search' },
            		{ line: true},
            		{ text: '生成二维码 ', id:'init', click: initQrCode, icon:'add' },
            	]},
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
  	//入库确认
	function initQrCode(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			
			var ParamVo = [];
			
			$.each(data, function (index, element) {
				ParamVo.push(this.inv_code + "@" 
						   + this.inv_name + "@"
						   + this.price + "@"
						   + this.bar_code);
	        });
			
			ajaxJsonObjectByUrl("createMatDuraQrCode.do", {
				ParamVo : ParamVo.toString()
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
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
	<div id="maingrid"></div>
</body>
</html>
