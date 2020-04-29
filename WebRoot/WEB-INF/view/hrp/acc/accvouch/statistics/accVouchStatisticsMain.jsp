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

	$(function() {
		loadDict();

		loadHead(null); //加载数据

		var yearmonth = '${yearMonth}'
		$("#acc_month_b").val(yearmonth.split(".")[1]);
		
		$("#acc_month_e").val(yearmonth.split(".")[1]);

	});
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_month_b',
			value : $("#acc_month_b").val()
		});
		grid.options.parms.push({
			name : 'acc_month_e',
			value : $("#acc_month_e").val()
		});
		grid.options.parms.push({
			name : 'vouch_type_code',
			value : liger.get("vouch_type_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'create_user',
			value : liger.get("create_user").getValue()
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '凭证总数',
				name : 'vouch_tote',
				align : 'left'
			}, {
				display : '正常凭证',
				name : 'vouch_normal',
				align : 'left'
			}, {
				display : '草稿凭证',
				name : 'draft',
				align : 'left'
			},
			{
				display : '作废凭证',
				name : 'vouch_cancel',
				align : 'left'
			}, {
				display : '出纳签字',
				align : 'center',
				columns:[{
					display : '未签字',
					name : 'no_cashier',
					align : 'left'
				}, {
					display : '已签字',
					name : 'cashier',
					align : 'left'
				}]
				
			 }, {
				display : '凭证审核',
				align : 'center',
				columns:[{
					display : '未审核',
					name : 'no_examine',
					align : 'left'
				}, {
					display : '已审核',
					name : 'examine',
					align : 'left'
				}]
			}, {
				display : '凭证记账',
				align : 'left',
				columns:[{
					display : '未记账',
					name : 'no_telly',
					align : 'left'
				}, {
					display : '记账',
					name : 'telly',
					align : 'left'
				}]
			}
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAccVouchStatistics.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad:true,
			selectRowButtonOnly : true/* ,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				} ]
			},
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.vouch_id);
			} */
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						ParamVo.push(
						//表的主键
						this.vouch_id)
					});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAccVouch.do", {
								ParamVo : ParamVo
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			case "Excel":
			case "Word":
			case "PDF":
			case "TXT":
			case "XML":
				$.ligerDialog.waitting('导出中，请稍候...');
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
					if (item.id == "Excel")
						$.ligerDialog.success('导出成功');
					else
						$.ligerDialog.error('导出失败');
				}, 1000);
				return;
			}
		}

	}
	function loadDict() {
		$("#acc_month_b").ligerTextBox({width:80});
		$("#acc_month_e").ligerTextBox({width:80});

		//字典下拉框
		autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false",
				"id", "text", true, true,'',false,'',100);
		autocomplete("#state", "../queryAccVouchState.do?isCheck=false",
				"id", "text", true, true);
		autocomplete("#create_user", "../../queryCreateUser.do?isCheck=false",
				"id", "text", true, true);
	}
	
	function printDate(){
		if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
		var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      	  	          {"cell":0,"value":"会计期间："+$("#acc_month_b").val()+"至"+$("#acc_month_e").val(),"colSpan":"5"}
	      		  ]
	      	};
	   		
	   		var printPara={
	   			rowCount:1,
	   			title:'凭证统计',
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.AccVouchStatisticsService",
				method_name: "queryAccVouchStatisticsPrint",
				bean_name: "accVouchStatisticsService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	   			};
	    	
	   		//执行方法的查询条件
	   		$.each(grid.options.parms,function(i,obj){
	   			printPara[obj.name]=obj.value;
	    	});
	   		
	    	officeGridPrint(printPara);
	}

	/**
	 * 打印 打印 预览 设置
	 */
	var menu_print = {
		width : 120,
		items : [ {
			text : '打印',
			id : 'print',
			click : itemclick
		}, {
			text : '预览',
			id : 'view',
			click : itemclick
		}, {
			text : '设置',
			id : 'set',
			click : itemclick
		} ]
	};
	/**
	 * 新增 新增 红字冲销 蓝字冲销 模版导入 模版下载
	 */
	var menu_label = {
		width : 120,
		items : [ {
			text : '标注',
			id : 'label',
			click : itemclick
		}, {
			text : '取消标注',
			id : 'unlabel',
			click : itemclick
		} ]
	};

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="acc_month_b" type="text" id="acc_month_b" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"
				style="width: 100px;" /></td>
			<td align="right" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_month_e"
				type="text" id="acc_month_e" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"
				style="width: 100px;" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_user" type="text" id="create_user" ltype="text" /></td>
			<td align="left"></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">凭证类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"> <input class="l-button" style="width: 90px" type="button" value="查询（Q）" onclick="query();"/></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><input class="liger-button" type="button"  value="打 印" onclick="printDate();"/></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
