<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var toolBra;
	var parent_node_id;
	var state_name;
	
	$(function() {
		
		loadDict();
		loadHead();
		 
		query();

	});

	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
 
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'create_date_b',
			value : $("#create_date_b").val()
		});
		grid.options.parms.push({
			name : 'create_date_e',
			value : $("#create_date_e").val()
		});
		grid.options.parms.push({
			name : 'invo_num',
			value : liger.get("invo_num").getValue()
		});
		grid.options.parms.push({
			name : 'vouch_no_b',
			value : $("#vouch_no_b").val()
		});
		grid.options.parms.push({
			name : 'vouch_no_e',
			value : $("#vouch_no_e").val()
		});
		grid.options.parms.push({
			name : 'invo_money_b',
			value : $("#invo_money_b").val()
		});
		grid.options.parms.push({
			name : 'invo_money_e',
			value : $("#invo_money_e").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	 function loadHead(){
	    	grid = $("#maingrid").ligerGrid({
	    		columns:[ {
					display : '凭证编号',name : 'vouch_no',align : 'left',width:90,
					render:function(rowdata){
						if(rowdata.state==0){
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 废</span></div></a>";
						}else if(rowdata.state==-1){
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_no+"<span style='color:red;font-weight: bold;'> 稿</span></div></a>";
						}else{
							return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')>"+rowdata.vouch_no+"</a>";
						}
					}
				},{
					display : '凭证日期',name : 'vouch_date',align : 'left',width:100
				},{
					display: '文件名称', name: 'att_name_o', align: 'left',width:100,
					render : function(rowdata, rowindex,value) {
						return "<a href=javascript:down('"+ rowdata.att_path + "')>"
								+ rowdata.att_name_o+ "</a>";
					}
				}, {
					display: '附件类型', name: 'att_type', align: 'left',width:90,
					render : function(rowdata, rowindex,value) {
						if(rowdata.att_type == 1){
							return '文档';
						}else{
							return '发票';
						}
					}
				}, {
					display: '上传人', name: 'create_name', align: 'left',width:110
				}, {
					display: '上传时间', name: 'create_date', align: 'left',width:150
				},{
					display: '文件大小(KB)', name: 'att_size', align: 'left',width:90
				}, {
					display: '发票号码', name: 'invo_num', align: 'left',width:200
				}, {
					display: '发票金额', name: 'invo_money', align: 'right',width:150,
					render : function(rowdata, rowindex, value) {
	  					return formatNumber(rowdata.invo_money, 2, 1);
	  				}
				}, {
					display: '发票日期', name: 'invo_date', align: 'left',width:110
				}, {
					display: '名称', name: 'att_name_n', align: 'left',width:1,hide:true
				}
	   		     ],
		        dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccVouchAdjunct.do?isCheck=false',
				width: '100%', height: '100%', checkbox: true,rownumbers:true,
				delayLoad: true,
				selectRowButtonOnly:true,//heightDiff: -10,
				toolbar: { items: [
					{ text: '查询(<u>Q</u>)', id:'search', click: query,icon:'search' },
					{ line:true } 
				]}
	    	});

	        gridManager = $("#maingrid").ligerGetGridManager();
		 }
	
	//字典下拉框
	function loadDict() {
		
		$("#create_date_b").ligerTextBox({width:90});autodate("#create_date_b", "yyyy-mm-dd", "month_first");
    	$("#create_date_e").ligerTextBox({width:90});autodate("#create_date_e", "yyyy-mm-dd", "month_last");
		$("#vouch_no_b").ligerTextBox({width:100 });
		$("#vouch_no_e").ligerTextBox({width:100 });
		$("#invo_num").ligerTextBox({width:224 });
		$("#invo_money_b").ligerTextBox({width:100 });
		$("#invo_money_e").ligerTextBox({width:100 });
	}
	
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
	
	function down(att_path) {
		location.href = "downAccFile.do?isCheck=false&att_path="+att_path;
	}

	

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">凭证日期：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="create_date_b" type="text" id="create_date_b" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" style="width: 90px;" /></td>
			<td align="left" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date_e"
				type="text" id="create_date_e" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" style="width: 90px;" /></td>
			<td align="left"></td>
			 
			<td align="right" class="l-table-edit-td" style="padding-left: 25px;">发 票 号：</td>
			<td align="left" class="l-table-edit-td">
				<input name="invo_num" type="text" id="invo_num" ltype="text" validate="{required:true,maxlength:100}" /></td>
			<td align="left"></td>
			
		</tr>
		<tr>
			 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭 证 号 ：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_b" type="text" id="vouch_no_b" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" >&nbsp;至&nbsp;</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no_e" type="text" id="vouch_no_e" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
			 <td align="right" class="l-table-edit-td" style="padding-left:10px;">发票金额：</td>
	            <td align="left" class="l-table-edit-td">
	            	<table>
	            		<tr>
	            			<td>
	            				<input name="invo_money_b" type="text" id="invo_money_b" ltype="text" validate="{required:true,maxlength:20}" />
	            			</td>
				            <td align="left" >&nbsp;至&nbsp;</td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="invo_money_e" type="text" id="invo_money_e" ltype="text" validate="{required:true,maxlength:20}" />
				            </td>
	            		</tr>
	            	</table>
	           </td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
