<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	
	$(function() {
		
		loadDict()//加载下拉框
		$(".dept").hide();
		//加载数据
		loadHead(null);
	});
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val() }); 
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
    	var show_app_dept = $("#show_app_dept").is(":checked") ? 1 : 0;
		grid.options.parms.push({name : 'show_app_dept',value : show_app_dept});
    	if(show_app_dept){
    		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(',')[0]});
    	}
    	
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid( { 
			columns: [ { 
				display: '科室编码', name: 'app_dept_code', align: 'left'
			}, { 
				display: '科室名称', name: 'app_dept_name', align: 'left' 
			}, { 
				display: '材料编码', name: 'inv_code', align: 'left'
			}, { 
				display: '材料名称', name: 'inv_name', align: 'left'
			}, { 
				display: '规格型号', name: 'inv_model', align: 'left' 
			}, { 
				display: '计量单位', name: 'unit_name', align: 'left' 
			}, { 
				display: '物资类别', name: 'mat_type_name', align: 'left'
			}, { 
				display: '供应商', name: 'sup_name', align: 'left' 
			}, { 
				display: '生产厂商', name: 'fac_name', align: 'left' 
			}, { 
				display: '库存数量', name: 'cur_amount', align: 'right' 
			}, { 
				display: '需求数量', name: 'req_amount', align: 'right' 
			}, { 
				display: '采购数量', name: 'amount', align: 'right' 
			}, { 
				display: '单价', name: 'price', align: 'right' 
			}, { 
				display: '金额', name: 'amount_money', align: 'right' 
			} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatPurInvReport.do',
			width: '100%', height: '100%', checkbox: false,rownumbers: true, delayLoad: true,
			selectRowButtonOnly:true, inWindow: true, heightDiff: -30,
			toolbar: { 
				items: [ { 
					text: '查询', id:'query', click: query ,icon:'search' 
				}, { 
					line:true 
				}, {
					text : '打印',id : 'print',click : print ,icon : 'print'
				} ]
			}
		});

		grid.toggleCol("app_dept_code", false);
		grid.toggleCol("app_dept_name", false);
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function loadDict() {
		autocompleteObj({
			id: "#dept_code",
			urlStr: '../../queryMatDeptDict.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            initWidth: 160,
           	autocomplete: true,
           	highLight: true, 
           	//defaultSelect: true, 
           	boxwidth: 240
		});
		
		autocompleteObj({
			id: '#store_code', 
			urlStr: '../../queryMatStore.do?isCheck=false',
			valueField: 'id',
			textField: 'text', 
			initWidth: 160,
			autocomplete: true,
			highLight: true, 
			defaultSelect: true, 
			boxwidth: 240
		});
		
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		
		//$("#show_app_dept").ligerCheckBox({click: showChange});
		
		$("#begin_date").ligerTextBox({width:110});
		$("#end_date").ligerTextBox({width:110});
        $("#inv_code").ligerTextBox({width:247});
	}
	
	function showChange(){
		if($("#show_app_dept").is(":checked")){
			$(".dept").show();
			grid.toggleCol("app_dept_code", true);
			grid.toggleCol("app_dept_name", true);
		}else{
			$(".dept").hide();
			grid.toggleCol("app_dept_code", false);
			grid.toggleCol("app_dept_name", false);
		}
	}
	
	function print(){
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		//跨所有列:计算列数
		var colspan_num = grid.getColumns(1).length-1;

		var heads={
			//"isAuto": true/false 默认true，页眉右上角默认显示页码
			"rows": [
				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +"至"+ $("#end_date").val(),"colspan":colspan_num,"br":true}
			]
		};

		var printPara={
			rowCount:1,
			title:'采购计划单',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.mat.service.purchase.query.MatPurchaseQueryService",
			method_name: "printMatPurInvReport",
			bean_name: "matPurchaseQueryService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
		};
		
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">
				日期范围：
			</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" type="text" requried="true"
								onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
								id="begin_date" />
						</td>
						<td>&nbsp;至&nbsp;</td>
						<td>
							<input class="Wdate" name="end_date" type="text" requried="true"
								onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
								id="end_date" />
						</td>
					</tr>
				</table>
			</td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">
				　　库房：<!-- 两个全角空格 -->
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">
				　　　　
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="checkbox" id="show_app_dept" onclick="showChange()"/>&nbsp;显示需求科室
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" requried="false" id="inv_code" />
			</td>

			<td align="right" class="l-table-edit-td dept" style="padding-left: 10px;">
				　　科室：
			</td>
			<td align="left" class="l-table-edit-td dept">
				<input name="dept_code" type="text" requried="true" id="dept_code" />
			</td>
		</tr>
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
