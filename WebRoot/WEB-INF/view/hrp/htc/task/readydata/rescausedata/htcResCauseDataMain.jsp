<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		//加载数据
		loadDict()//加载下拉框
		loadHead(null);
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '资源动因编码',
								name : 'res_cause_code',
								align : 'left'
							}, {
								display : '资源动因',
								name : 'res_cause_name',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"updateResCause('"
									        + rowdata.group_id + "|"
									        + rowdata.hos_id + "|"
									        + rowdata.copy_code + "|"
											+ rowdata.res_cause_code
											+ "');\" >"
											+ rowdata.res_cause_name + "</a>";
								}
							}, {
								display : '说明',
								name : 'res_remark',
								align : 'left'
							}, {
								display : '完成标志',
								name : 'copn_cia',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcResCauseDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad :true,
					selectRowButtonOnly : true,
					toolbar : {
						items : [{
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}]
					}
					
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
   function updateResCause(obj){
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm =  "group_id=" + vo[0] + 
		      "&" + "hos_id=" + vo[1] + 
			  "&" + "copy_code=" + vo[2]+ 
		      "&" + "res_cause_code=" + vo[3];
	      
	   $.ligerDialog.open({ 
		   url: 'htcResCauseDataSaveMainPage.do?isCheck=false&'+parm,
		   data:{},
		   top:0,
		   title:"资源动因配置",
		   height: 500,
		   width: 1000,
		   modal:true,
		   showToggle:false,
		   showMax:false,
		   showMin:false,
		   isResize:true,
		   buttons: [{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
   }

	function loadDict(){
		 autodate("#acc_year","YYYY");
		 $("#acc_year").ligerTextBox({width:160});
      }
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="acc_year" type="text" id="acc_year" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" style="width:160px;"/></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>	
</body>
</html>
