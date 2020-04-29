<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%-- <jsp:include page="${path}/inc.jsp"/> --%>
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <style> 
  .white_content {  display: none;  position: absolute;  top: 50%;  left: 60%;  width: 180px;  height: 180px; z-index:9999;  padding: 16px;  border: 2px solid #AECAF0;  background-color: white;   overflow: auto;  }  
  </style> 
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script src="<%=path%>/lib/stringbuffer.js"	type="text/javascript"></script>
    <script type="text/javascript">
	var dataFormat;
	var clicked = 0;
	var selectData = "";
	var grid;
	var gridManager = null;
	$(function() {
		
		loadHead(null);//加载数据

	});

	function query(){
		  grid.options.parms=[];
    	  grid.options.newPage=1;
          //根据表字段进行添加查询条件
    	  
          grid.options.parms.push({name:'sup_id',value:'${sup_id}'});
    	  grid.options.parms.push({name:'sup_no',value:'${sup_no}'});
    	
    	  //加载查询条件
    	 grid.loadData(grid.where);
		 $("#resultPrint > table > tbody").empty();
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
                       		{ display: '供应商编码', name: 'sup_code', align: 'left',width : 100, },
							{display : '供应商名称',name : 'sup_name',align : 'left',width : 200},
							{display : '业务员',name : 'ven_person',align : 'left',width : 150},
							{display : '联系方式',name : 'phone',align : 'left',width : 150},
							{display : '经营许可证号',name : 'cert_code',align : 'left',width : 250}, 
							{display : '起始日期',name : 'start_date',align : 'left',width : 150}, 
							{ display: '到期日期', name: 'end_date', align: 'left',width : 150}
						],
						dataAction : 'server',dataType : 'server',usePager : true,width : '100%',height : '90%',
						checkbox : true,rownumbers : true,enabledEdit : false,
						url : 'queryMedPurSupDetail.do?isCheck=false&sup_id='+ '${sup_id}'+'&sup_no='+'${sup_no}',
						isScroll : true,selectRowButtonOnly : true,//heightDiff: -10,
						toolbar : {
							items : [ 
					        		  {text : '关闭（<u>L</u>）',id : 'close',click : this_close,icon : 'close'}
		        				    ]
						}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
</script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	 <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
	</div>
</body>
</html>
