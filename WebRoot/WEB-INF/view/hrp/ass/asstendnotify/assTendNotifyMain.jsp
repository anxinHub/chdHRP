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
		
		loadDict();//加载下拉框
		
		loadHotkeys();
		
		//加载数据
		loadHead(null); 
	});
	
	//查询
	function query(obj) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件 
		grid.options.parms.push({ name : 'bid_code', value : $("#bid_code").val() });
		grid.options.parms.push({ name : 'bid_makertime', value : $("#bid_makertime").val() });
		grid.options.parms.push({ name : 'bid_method', value : liger.get("bid_method").getValue() });
		
		grid.options.parms.push({ name : 'ven_id', value : liger.get("ven_id").getValue() });
		grid.options.parms.push({ name : 'bid_ylwcode', value : $("#bid_ylwcode").val() });
		grid.options.parms.push({ name : 'bid_state', value : liger.get("bid_state").getValue() });
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ 
					{
						display : '招标编号',
						name : 'bid_code',
						align : 'center',
						width: '120',
						render : function(rowdata,rowindex,value) {
							return "<a href=javascript:openAssTendDetail("+rowdata.bid_id+","+rowdata.bid_state+")><b>"+rowdata.bid_code+"</b></font></a>";
						}
					}, {
						display : '供应商',
						name : 'sup_name',
						align : 'left',
						width: '120',
						editor : {
							type : 'select',
							valueField : 'id',
							textField : 'text',
							url : '../queryHosSupDict.do?isCheck=false',
							keySupport : true,
							autocomplete : true
						}
					}, {
						display : '定标日期',
						name : 'bid_calibratedate',
						type : 'date',
						format : 'yyyy-MM-dd',
						align : 'left',
						width : '120'
					}, {
						display : '中标文件',
						name : 'bid_winfiledr',
						align : 'center',
						width: '120',
						render : function(rowdata, rowindex,value) {
							if(rowdata){
								return "<a href=javascript:assTendNotifyFileUp("+rowdata.bid_id+",'01')><b>中标文件</b></a>";
							}
						}
					}, {
						display : '招标地址',
						name : 'bid_addr',
						align : 'left',
						width: '120'
					}, {
						display : '招标人',
						name : 'bid_tenderee',
						align : 'left',
						width: '120'
					}, {
						display : '申请金额',
						name : 'bid_value',
						align : 'right',
						width: '120',
						render : function(rowdata, rowindex,value) {
							 return formatNumber(
										rowdata.bid_value == null ? 0
												: rowdata.bid_value,'${ass_05005}',1);
						}
					}, {
						display : '招标方式',
						name : 'bid_method',
						align : 'left',
						width: '120',
                    	render:function(rowdata,rowindex,value){
                       		if(value == '01'){
   			 					return "公开招标";
   			 				}else if(rowdata.state == '02'){
   			 					return "邀请招标";
   			 				}
			 			}
					}, {
						display : '公告媒介',
						name : 'bid_notice',
						align : 'left',
						width: '120'
					}, {
						display : '公告日期',
						name : 'bid_noticedate',
						type : 'date',
						format : 'yyyy-MM-dd',
						align : 'left',
						width : '120'
					}, {
						display : '招标联系电话',
						name : 'bid_phone',
						align : 'left',
						width: '120'
					}, {
						display : '一链网招标编号',
						name : 'bid_ylwcode',
						align : 'left',
						width: '120'
					}, {
						display : '招标文件',
						name : 'bid_filedr',
						align : 'center',
						width: '120',
						render : function(rowdata, rowindex,value) {
							if(rowdata){
								return "<a href=javascript:assTendNotifyFileUp("+rowdata.bid_id+",'02')><b>招标文件</b></a>";
							}
						}
					},{
						display : '状态',
						name : 'bid_state',
						align : 'left',
						width: '120',
						render : function(rowdata, rowindex,value) {
							if(rowdata.bid_state=="01"){
								return "新建";
							}
							if(rowdata.bid_state=="02"){
								return "提交";
							}
							if(rowdata.bid_state=="03"){
								return "审核";
							}
						}
					}],
					dataAction: 'server', 
					dataType: 'server', 
					url: 'queryAssTendNotifyMain.do?isCheck=false',
					width: '100%', 
					height: '100%', 
					checkbox: true, 
					rownumbers: true,
					enabledEdit : true,
					isAddRow:false,
					usePager: true,
					delayLoad: false,//初始化不加载
					selectRowButtonOnly: true,//heightDiff: -10,
					toolbar : {
						items : [ 
							{ text: '查询（<u>Q</u>）', id:'query', click: query ,icon:'search' },
                            { line:true },
							{ text: '中标文件上传（<u>U</u>）', id:'upload_file', click: assTendNotifyFileUpSelect, icon:'outbox' },
							{ line:true }
						]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						if(rowdata.summary == '合计'){
							return;
						}else{
							openUpdate(
									rowdata.group_id + "|" + 
									rowdata.hos_id+ "|" + 
									rowdata.copy_code + "|"+ 
									rowdata.apply_id +"|"+
									rowdata.apply_no );
						}
						
					}
				});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//打开招标单页面
	function openAssTendDetail(bid_id,bid_state) {
		parent.$.ligerDialog.open({
			title: '设备招标单',
			height: 400,
			width: 800,
			url: 'hrp/ass/asstend/assTendDetailPage.do?isCheck=false&bid_id='+bid_id+"&bid_state="+bid_state,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//文件上传
	function assTendNotifyFileUpSelect(){
		var data=grid.getSelectedRows();
		var bid_id="";
		if (data.length != 1) {
			if(data.length<1){
				$.ligerDialog.warn('请选择行！');
				return;
			}
			$.ligerDialog.warn('只能选择单行操作！');
		} else {
			$(data).each(
				function(){
	  	    		bid_id=this.bid_id;		 
					parent.$.ligerDialog.open({
						url:'hrp/ass/asstendnotify/upLodePage.do?isCheck=false&is_flag=01&bid_id='+bid_id,
						data:{},
						height: 600,
						width: 900,
						title:'上传',
						modal:true,
						showToggle:false,
						showMax:false,
						showMin: false,
						isResize:true,
						parentframename:window.name 
					});
				}
			);
		}
	}
	
	//文件上传
	function assTendNotifyFileUp(bid_id,is_flag) {
     	 parent.$.ligerDialog.open({
     		 url:'hrp/ass/asstendnotify/upLodePage.do?isCheck=false&bid_id='+bid_id+'&is_flag='+is_flag,
     		 height: 600,
     		 width: 900,
     		 title:'上传文件',
     		 modal:true,
     		 showToggle:false,
     		 showMax:false,
     		 showMin: false,
     		 isResize:true,
     		 parentframename:window.name 
     	});
	}
	
	//字典下拉框
	function loadDict() {
		$("#bid_code").ligerTextBox({width : 160});
		$("#bid_makertime").ligerTextBox({width : 160});
		$("#ven_id").ligerComboBox({width : 160});
		$("#bid_ylwcode").ligerTextBox({width : 160});
		//状态
		$('#bid_state').ligerComboBox({
				data:[{id:'01',text:'新建'},{id:'02',text:'提交'},{id:'03',text:'审核'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 160
		}); 
		//招标方式
		$('#bid_method').ligerComboBox({
				data:[{id:'01',text:'公开招标'},{id:'02',text:'邀请招标'}],
				valueField: 'text',
	            textField: 'text',
				cancelable:true,
				width : 160
		});
		//供应商
		autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true, true, null, null);
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">招标编号：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_code"
				type="text" id="bid_code"/></td>
			<td align="left" ></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_makertime"
				type="text" id="bid_makertime" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">招标方式：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_method"
				type="text" id="bid_method" 
				 /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id"/></td>
			<td align="left" ></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">一链网编码：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_ylwcode"
				type="text" id="bid_ylwcode"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_state"
				type="text" id="bid_state" /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
