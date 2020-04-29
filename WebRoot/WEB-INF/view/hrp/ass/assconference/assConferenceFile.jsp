<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
/* 	amount_money : function(value) {//金额
		return formatNumber(value,
				'${ass_05005}', 1);
	}, */
	$(function() {
		
		//加载数据
		loadHead(null); 
	
	});
	//查询
	function query(obj) {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件 
		grid.options.parms.push({ name : 'con_id', value : '${con_id}' });
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
                    
					{
						display : '文件名称',
						name : 'file_name',
						
						align : 'left',width: '350',
						render : function(rowdata, rowindex,value) {
							if(rowdata){ 
								if(!value){
									return "";
								}else{
								return "<a href="+rowdata.file_path+" download="+rowdata.file_name+"><font><b>"+value+"</b></font></a>";
							}
							}
						}
					}, {
						display : '文件路径',
						name : 'file_path',
						align : 'left',
						width: '250',
						render : function(rowdata, rowindex,value) {
							if(rowdata){ 
								if(!value){
									return "";
								}else{
									return "<a href="+rowdata.file_path+" download="+rowdata.file_name+"><font><b>"+value+"</b></font></a>";
							}
							}
						}
					}, {
						display : '文件类型',
						name : 'file_type',
						align : 'center',width: '100'
					}, {
						display : '删除',
						name : 'delete',
						render : function(rowdata, rowindex,value) {
							if(rowdata){ 							
								return "<a href=javascript:DeleteFile('"+rowdata.bid_id+"|"+rowdata.file_path+"|"+rowdata.file_name+"|"+
								rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"')><font><b>删除</b></font></a>";					
							}
						},
						align : 'center',width: '120'
					}, {
						display : '下载',
						name : 'uplode',
						align : 'center',
						width: '100',
						render : function(rowdata, rowindex,value) {
							if(rowdata){ 
								
							   return '<a href='+rowdata.file_path+' download='+rowdata.file_name+'>下载</a>';
							
							}
						}
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssTendFile.do?isCheck=false&con_id=${con_id}',
					width : '100%',
					height : '100%',
					alternatingRow : true,
					isScroll : true,
					checkbox : false,
					rownumbers : true,
					delayLoad :false,
					enabledEdit : false,
					isAddRow:false,
					selectRowButtonOnly : true,
					
				});
		
		gridManager = $("#maingrid").ligerGetGridManager();

	}

	function DeleteFile(obj){
	if(obj){

	 var vo=obj.split("|");		

	 var param="con_id="+vo[0]+
	 "&file_path="+vo[1]+
	 "&file_name="+vo[2]+
	 "&group_id="+vo[3]+
	 "&hos_id="+vo[4]+
	 "&copy_code="+vo[5];
			
		}
	$.ligerDialog.confirm('确定删除?', function(yes) {
		
		if (yes) {
			ajaxJsonObjectByUrl("deleteTendFile.do?isCheck=false&"+param, "", function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	
	<div id="maingrid"></div>
</body>
</html>
