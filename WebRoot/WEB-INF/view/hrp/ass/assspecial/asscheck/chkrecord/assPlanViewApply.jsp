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
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var budg_year;
	var grid;
	var gridManager = null;
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);//加载数据
		loadHotkeys();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'rss_chk_no',
			value : $("#rss_chk_no").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}
	
	//加载表格
	function loadHead() {
		var flag;
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '申报单号',
						name : 'ass_chk_no',
						align : 'left',
						width : '10%',
						render : function(rowdata, rowindex, value) {
							return "<a href=javascript:openUpdate('"
									+ rowdata.group_id + "|"
									+ rowdata.hos_id + "|"
									+ rowdata.copy_code + "|"
									+ rowdata.ass_chk_no + "')>"
									+ rowdata.ass_chk_no + "</a>";
						}
					}, {
						display : '摘要',
						name : 'summary',
						align : 'left',
					}, {
						display : '制单人',
						name : 'create_emp',
						textField : 'create_emp_name',
						align : 'left',
					}, {
						display : '制单日期',
						name : 'create_date',
						align : 'left',
					}, {
						display : '确认人',
						name : 'audit_emp',
						textField : 'audit_emp_name',
						align : 'left',
					}, {
						display : '确认日期',
						name : 'apply_date',
						align : 'left',
					}, {
						display : '状态',
						name : 'state',
						textField : 'state_name',
						align : 'left',
					}],
					usePager : false,
					url : 'queryCheckApSpecialByImport.do?isCheck=false&state=2&rss_chk_no=${rss_chk_no}',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					onBeforeEdit : function() {
						flag = true;
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|" + rowdata.check_ap_no);
					},
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
						} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}





	//修改
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "ass_chk_no=" + vo[3];
		parent.$.ligerDialog.open({
			url : 'hrp/ass/assspecial/check/chkrecord/assCheckViewUpdatePage.do?isCheck=false&' + parm.toString(),
			title : '查看申报信息',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			slide:false,
			parentframename: window.name
		});
	}
	

	function save() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} /* else {
			
			} */

			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(this.ass_chk_no)
			});
			$.post("addAssPlanDeptImport.do?isCheck=false", {
				//ass_chk_no : ParamVo.toString(),
				'ass_chk_no' : ParamVo.toString(),
				'create_date':'${create_date}',
				 'note':'${note}',
			
			}, function(responseData) {
			
			/* 		if(	parentFrameUse().grid.rows.ass_card_no != 'undefined' && parentFrameUse().grid.rows.ass_card_no !=""){
						parentFrameUse().grid.addRows(responseData.Rows);
					}else{
						parentFrameUse().grid.deleteRows();
						parentFrameUse().grid.addRows(responseData.Rows);
					} */
				parent.parentFrameUse().openUpdate(responseData.update_para);
				parent.this_close();
			    this_close();
					
			},"json");

		}

	
	//字典下拉框
	function loadDict() {

		//状态
		/* $("#state").ligerTextBox({width : 150}); */
		$('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'},{id:2,text:'确认'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		
		$("#create_date_beg,#create_date_end,#apply_date_beg,#apply_date_end").ligerTextBox({width : 80});
    	
	}
	
	//键盘事件
	function loadHotkeys() {
/* 
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('I', imp);
		hotkeys('S', audit); */
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<input type="hidden" id="rss_chk_no" name="rss_chk_no" value="${rss_chk_no }" />
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">制单日期：</td>
            <td align="left" class="l-table-edit-td" >
            <div style="float:left;">
            	<input name="create_date_beg" class="Wdate" type="text" id="create_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </div>
            <span style="float:left;margin: 0 3px;">至</span>
            <div style="float:left;">
            	<input name="create_date_end" class="Wdate" type="text" id="create_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</div>
			</td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">确认日期：</td>
            <td align="left" class="l-table-edit-td" >
            <div style="float:left;">
            	<input name="apply_date_beg" class="Wdate" type="text" id="apply_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </div>
            <span style="float:left;margin: 0 3px;">至</span>
            <div style="float:left;">
            	<input name="apply_date_end" class="Wdate" type="text" id="apply_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</div>
			</td>
			<td align="left"></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td">
				<!-- <select id="state" name="state">
            		<option value="">全部</option>
            		<option value="0">新建</option>
            		<option value="1">审核</option>
            		<option value="2">确认</option>
            	</select> -->
            	<input  name="state" type="text" id="state"/>
            </td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
