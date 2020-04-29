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
		if(!$("#year_month_beg").val()){
			$.ligerDialog.error("制单日期不能为空！");
			return;
		}
		if(!$("#year_month_end").val()){
			$.ligerDialog.error("制单日期不能为空！");
			return;
		}
		grid.options.parms.push({name : 'year_month_beg',value : $("#year_month_beg").val()});
		grid.options.parms.push({name : 'year_month_end',value : $("#year_month_end").val()});
		grid.options.parms.push({name : 'maker',value : liger.get("maker").getValue()});
		grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
		grid.options.parms.push({name : 'coop_type',value : liger.get("coop_type").getValue()});
		grid.options.parms.push({name : 'proj_code',value : liger.get("proj_code").getValue()});
		
		//加载查询条件
		grid.loadData(grid.where);
	}


	function loadDict() {
		autocompleteObj({
			  id: '#proj_code',
			  urlStr: "queryAccCoopProjDict.do?isCheck=false",
			  valueField: 'id',
			  textField: 'text',
			  autocomplete: true,//可输入
			  initWidth: '280',
			 // defaultSelect: true,//默认第一个值
			  autocompletelocal: true,//本地检索
			  pageSize: 99999//下拉框默认显示条数
		});
		autocompleteObj({
			  id: '#maker',
			  urlStr: "queryCoopCostMaker.do?isCheck=false",
			  valueField: 'id',
			  textField: 'text',
			  autocomplete: true,//可输入
			  initWidth: '160',
			 // defaultSelect: true,//默认第一个值
			  autocompletelocal: true,//本地检索
			  pageSize: 99999//下拉框默认显示条数
		});
		$("#coop_type").ligerComboBox({ 
			data: [
				{ id: 1, text: '院内' },
				{ id: 2, text: '院外'}], 
			isMultiSelect: false,
			width : 160
			}
		);
		$("#state").ligerComboBox({ 
			data: [
				{ id: 1, text: '新建' },
				{ id: 2, text: '审核'}], 
			isMultiSelect: false,
			width : 160
			}
		);
		
		$("#year_month_beg").ligerTextBox({width : 125});
		$("#year_month_end").ligerTextBox({width : 125});
		
		var today=new Date();
	    var h=today.getFullYear();
	    var m=today.getMonth()+1;
	    if(m < 10){
	    	m = "0" + m;
	    }
	    var acc_month=getMonthDate(h,m);
		$("#year_month_beg").val(acc_month.split(";")[0]);
		$("#year_month_end").val(acc_month.split(";")[1]);
	}
	
	 function loadHead(){
	    	grid = $("#maingrid").ligerGrid({
				columns: [
					{display: '流水号', name: 'ser_num', align: 'left' ,
						render:function(rowData){
							return "<a href=javascript:openUpdate('"+rowData.ser_num+"')>"+rowData.ser_num+"</a>";
						}	
					},
					{display: '制单日期', name: 'create_date', align: 'left' },
					{display: '合作项目', name: 'proj_name', align: 'left' },
					{display: '合作类型', name: 'coop_type', align: 'left' ,
						render: function(rowData){
							if(rowData.coop_type == '1'){
								return '院内';
							}else{
								return '院外';
							}
						}
					},
					{display: '总费用', name: 'sm_my', align: 'right' ,
						render: function(rowData){
							return formatNumber(rowData.sm_my,2,1);
						}
					},
					{display: '状态', name: 'state', align: 'left' ,
						render: function(rowData){
							if(rowData.state == '1'){
								return '新建';
							}else{
								return '审核';
							}
						}	
					},
					{display: '制单人', name: 'user_name', align: 'left' },
					{display: '备注', name: 'note', align: 'left' }
				],
				dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccCoopCost.do?isCheck=false',
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
			title: '分摊登记',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/acc/autovouch/acccoodeptcost/acccoopcost/accCoopCostAddPage.do?isCheck=false',
			data:{call:'add',state:1},
			modal: true, showToggle: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	 

	 function openUpdate(ser_num){
		 parent.$.ligerDialog.open({
				title: '费用登记',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/acc/autovouch/acccoodeptcost/acccoopcost/accCoopCostUpdatePage.do?isCheck=false&ser_num='+ser_num,
				data:{call:'update'},
				modal: true, showToggle: false, isResize: true,slide:false,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	 }
	 
	 function remove(){
		 var data = gridManager.getSelectedRows();
			if(data.length == 0){
				$.ligerDialog.error("请选择要删除的数据！");
				return;
			}
		 var param =[];
         $(data).each(function () {
        	 if(this.state == '1'){
	             param.push(this.ser_num);
        	 }
         });
         if(param.length == 0){
        	 $.ligerDialog.error("只能删除状态为新建的数据！");
        	 return ;
         }
         $.ligerDialog.confirm('是否删除数据?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteAccCoopCost.do",{deleteList : JSON.stringify(param)},function(responseData){
			             if(responseData.state=="true"){
			            	 query();
			             }
			         });
				}
			})
				
	         
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
			title : '合作科室费用',
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.acc.service.autovouch.acccoodeptcost.AccCoopCostService",
			method_name : "queryAccCoopCostPrint",
			bean_name : "accCoopCostService",
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
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="year_month_beg" type="text" id="year_month_beg" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
				style="width: 100px;" />
			</td>
			<td align="left" class="l-table-edit-td">至</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month_end"
				type="text" id="year_month_end" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
				style="width: 90px;" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input id="maker" name="maker" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td"><input id="state" name="state" /></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">合作项目：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input id="proj_code" name="proj_code" /></td>	
			<td align="left" class="l-table-edit-td" style="padding-left: 10px;">合作类型：</td>
			<td align="left" class="l-table-edit-td"><input id="coop_type" name="coop_type" /></td>	
		</tr>
	</table>
	<div id="maingrid" style="margin-top: 10px"></div>
</body>
</html>
