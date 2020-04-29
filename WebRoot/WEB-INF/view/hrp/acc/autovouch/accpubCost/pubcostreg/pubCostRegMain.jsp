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
	$(function() {
		loadDict();//加载下拉框
		loadHead();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;

		grid.options.parms.push({name : 'acc_year',value : liger.get("acc_year").getValue()});
		grid.options.parms.push({name : 'proj_code',value : liger.get("proj_code").getValue()});
		grid.options.parms.push({name : 'ft_para',value : liger.get("ft_para").getValue()});
		grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
		
		//加载查询条件
		grid.loadData(grid.where);
	}


	function loadDict() {
		autocomplete("#acc_year", "../../../../acc/queryAcctYearDict.do?isCheck=false", "id", "text", true, true, null, null, '${acct_year}', "160");
		autocomplete("#proj_code", "../../../../acc/queryHosDictType.do?isCheck=false&table_code=01005", "id", "text", true, true, null, null, null, "220");
		autocomplete("#ft_para", "../../../../acc/queryInitAccDict.do?isCheck=false&table_code=ACC_FT_PAPER", "id", "text", true, true, null, null, null, "160");
		$("#state").ligerComboBox({ 
			data: [
				{ id: 1, text: '新建' },
				{ id: 2, text: '审核'}], 
			isMultiSelect: false});
	}
	
	 function loadHead(){
	    	grid = $("#maingrid").ligerGrid({
				columns: [
					{display: '统计年月', name: 'year_month', align: 'left' ,
						render:function(rowData){
							return "<a href=javascript:openUpdate('"+rowData.year_month+"','"+rowData.proj_code+"','"
									+rowData.ft_para+"','"+rowData.ft_my+"','"+rowData.note+"','"+rowData.state+"')>"+rowData.year_month+"</a>";
						}	
					},
					{display: '项目', name: 'proj_name', align: 'left' },
					{display: '分摊参数', name: 'ft_para_name', align: 'left' },
					{display: '公用费用', name: 'ft_my', align: 'right' ,
						render: function(rowData){
							return formatNumber(rowData.ft_my,2,1);
						}
					},
					{display: '状态', name: 'state_name', align: 'left' },
					{display: '备注', name: 'note', align: 'left' }
				],
				dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccPubCostReg.do?isCheck=false',
				width: '100%', height: '100%', checkbox: true,rownumbers:true,
				delayLoad: true,//初始化加载，默认false
				selectRowButtonOnly:true,//heightDiff: -10,
				toolbar: { items: [
				   				{ text: '查询', id:'search', click: query, icon:'search' },
				   				{ line:true },
				   				{ text: '添加', id:'add', click: add, icon:'add' },
				   				{ line:true },
				   				{ text: '删除', id:'delete',  click: remove, icon:'delete' },
				   				{ line:true },
								{ text: '打印', id:'print', click: printDate,icon:'print'}
				   			]}

			});

	        gridManager = $("#maingrid").ligerGetGridManager();
	    }
	 
	 function add(){
		parent.$.ligerDialog.open({
			title: '公用费用分摊',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/acc/autovouch/accpubcost/pubcostreg/pubCostRegAddPage.do?isCheck=false',
			data:{call:'add'},
			modal: true, showToggle: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	 

	 function openUpdate(year_month,proj_code,ft_para,ft_my,note,state){
		 if(note == "null"){
			 note = "";
		 }
		 parent.$.ligerDialog.open({
				title: '公用费用分摊',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/acc/autovouch/accpubcost/pubcostreg/pubCostRegUpdatePage.do?isCheck=false&year_month='+year_month+'&proj_code='+proj_code,
				data:{call:'update',year_month:year_month,proj_code:proj_code},
				modal: true, showToggle: false, isResize: true,slide:false,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	 }
	 
	 function remove(){
		 var data = gridManager.getSelectedRows();
			if(data.length == 0){
				$.ligerDialog.error("请选择要保存的数据！");
				return;
			}
			
			 var param =[];
	         $(data).each(function () {
	        	 if(this.state == '1'){
		             param.push(this);
	        	 }
	         });
	         if(param.length == 0){
	        	 $.ligerDialog.error("只能删除状态为新建的数据！");
	        	 return ;
	         }
	         ajaxJsonObjectByUrl("deleteAccPubCostReg.do",{deleteList : JSON.stringify(param)},function(responseData){
	             if(responseData.state=="true"){
	            	 query();
	             }
	         });
	 }
	 
	function printDate() {
		if (grid.getData().length == 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		var heads = {
			"isAuto": true//false 默认true，页眉右上角默认显示页码
		};
		var printPara = {
			rowCount : 1,
			title : '公用费用',
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.acc.service.autovouch.accpubCost.AccPubCostRegService",
			method_name : "queryAccPubCostRegPrint",
			bean_name : "accPubCostRegService",
			heads : JSON.stringify(heads)
		//表头需要打印的查询条件,可以为空
		};
		//执行方法的查询条件
		$.each(grid.options.parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});
		officeGridPrint(printPara);
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input id="acc_year" name="acc_year" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">项目：</td>
			<td align="left" class="l-table-edit-td"><input id="proj_code" name="proj_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">分摊参数：</td>
			<td align="left" class="l-table-edit-td"><input id="ft_para" name="ft_para" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td"><input id="state" name="state" /></td>	
				
		</tr>
	</table>
	<div id="maingrid" style="margin-top: 10px"></div>
</body>
</html>
