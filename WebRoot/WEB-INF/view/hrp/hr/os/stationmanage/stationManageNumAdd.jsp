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
 <script src="<%=path%>/lib/et_components/et_plugins/etTree.min.js"></script>
     <script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<script type="text/javascript">
	var dataFormat;
	var grid; 
	var gridManager = null; 
	var cardgrid;
	var cardgridManager = null;
	 var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	 var dialogData = dialog.get('data');//获取data参数
	var editor;
	var emp_id;
	$(function() {
		$("#layout1").ligerLayout({
			rightWidth : '400',
			//heightDiff: -8,
			//每展开左边树即刷新一次表格，以免结构混乱
			onRightToggle : function() {
				grid._onResize();
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize();
			}
		});
		
	
		loadHead(null);
		query();


	});
	function query() {
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		//根据表字段进行添加查询条件

		grid.options.parms.push({
			name : 'dept_no',
			value : dialogData.param[0].dept_no
		}); 
		grid.options.parms.push({
			name : 'dept_id',
			value : dialogData.param[0].dept_id
		}); 
		grid.options.parms.push({
			name : 'station_kind',
			value : dialogData.param[0].station_code
		}); 
		grid.options.parms.push({
			name : 'tab_code',
			value : 'HOS_EMP'
		}); 

		//加载查询条件
		grid.loadData(grid.where);
		
	}
	function saveData() {
		gridManager.endEdit();
		
		
	//	var data = gridManager.getData();
		var addData = grid.getAdded();//添加数据
		var modData = grid.getUpdated(); //修改数据'
		 var addDataVo = [];
        var modDataVo = [];
		var num = 0;
		if (addData.length==0) {
			$.ligerDialog.error('未获取到新增数据');
			return false;
		}
		if(modData.length==0&&addData.length==0){
			$.ligerDialog.error('未获取到修改数据');
			return false;
		}
		 $(addData).each(function () {
			 var rowdata = this;
        	if(rowdata.emp_code!=undefined){
           addDataVo.push(rowdata)};
        });
        $(modData).each(function () {
        	 var rowdata = this;
        	if(rowdata.dept_id!=""){
            modDataVo.push(rowdata)};
        });
	
		if(addDataVo.length!=0){
			var formPara = {
					dept_no : dialogData.param[0].dept_no,
					dept_id: dialogData.param[0].dept_id,
					station_kind : dialogData.param[0].station_code,
					tab_code : 'HR_STATION_MANAGE', 
				    
			
				ParamVo : JSON.stringify(addDataVo)
			};
			ajaxJsonObjectByUrl("updateEmpKind.do?isCheck=false", formPara, function(
					responseData) {
				if (responseData.state == "true") {
				
				}
			});	
		}
		if(modDataVo.length!=0){
			var modPara = {
					dept_no : dialogData.param[0].dept_no,
					dept_id: dialogData.param[0].dept_id,
					station_kind : dialogData.param[0].station_code,
					tab_code : 'HR_STATION_MANAGE', 
				
			
				ParamVo : JSON.stringify(modDataVo)
			};
			ajaxJsonObjectByUrl("updateEmpKind.do?isCheck=false", modPara, function(
					responseData) {
				if (responseData.state == "true") {
				
				}
			});	
		}
		
			
		
	}
	function this_close() {
		frameElement.dialog.close();
	}

	

	function loadHead() {

  	  var columns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HOS_EMP'],design:'queryManNumData.do'});

		
		editor = {
				type : 'select',
				valueField : 'emp_id',
				textField : 'emp_name',
				selectBoxWidth : 260,
				selectBoxHeight : 240,
				grid : {
					columns :columns ,
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : 'queryManNumData.do?isCheck=false&dept_id='+dialogData.param[0].dept_id,
					pageSize : 30
				},
				keySupport : true,
				alwayShowInDown : false,
				keySupport : true,
				autocomplete : true,
				selectRowButtonOnly:true
				
				
			};
		
		
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{ display: '职工ID',name: 'emp_id',width: 100,algin:'left',editor:editor,
										render : function(rowdata, rowindex,
												value) {
											return rowdata.emp_id;
										},
										width : '130',
					                   

									},{display: '工号',name: 'emp_code',width: 100,algin:'left',hide :true},
									{display: '姓名',name: 'emp_name',width: 100,algin:'left',
										render : function(rowdata, rowindex,
											value) {
										return rowdata.emp_name;
									},}
									 ],
							dataAction : 'server',
							dataType : 'server',
						url:'queryStationManageNum.do',
							usePager : false,
							width : '100%',
							height : '97%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							isScroll : true,
							rownumbers : true,
							delayLoad : true,//初始化明细数据
							selectRowButtonOnly : true,//heightDiff: -10,
							
							
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		is_addRow();
		
	
	}
	


	
	function printBarcode(){
		
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "emp_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					emp_code : data.emp_code,
					emp_id : data.emp_id,
					emp_name : data.emp_name
				});
				
			}
		}
	
		var data = gridManager.getData();
		 $(data).each(function () {
			 var rowdata = this;
        	if(rowdata.emp_id!=undefined&&rowdata.emp_id!=''){
        		emp_id+=rowdata.emp_id+",";
        };
        })
		 emp_id=emp_id.substring(9,emp_id.length-1);
		 editor.grid.url='queryManNumData.do?isCheck=false&emp_id='+emp_id+"&dept_id="+dialogData.param[0].dept_id;
		 var emp_id;
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	
	
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}

</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
		<div position="center" title="人员列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		
	</div>

</body>
</html>
