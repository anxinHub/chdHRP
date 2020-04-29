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
var dialog = frameElement.dialog;
var call=dialog.get("data")!=null?dialog.get("data").call:null;
var deptgrid,deptgridManager,deptgrid2;
var leftSum = 0;
var rightSum = 0;
	$(function() {
		loadDict();
		loadTopBar();
		loadHead();
		deptgrid.addRow();
	});
	
	
	//查询
	/*
	function query() {
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		deptgrid.options.parms = [];
		deptgrid.options.newPage = 1;
		deptgrid.options.parms.push({name : 'proj_code',value : liger.get("proj_code").getValue()});
		deptgridManager.loadData(deptgrid.where);
		
		var coop_type = liger.get("coop_type").getValue();
		if(coop_type == 2){
			deptgrid2.options.parms = [];
			deptgrid2.options.newPage = 1;
			deptgrid2.options.parms.push({name : 'proj_code',value : liger.get("proj_code").getValue()});
			deptgridManager2.loadData(deptgrid2.where);
		}
	}*/
	
	function loadHead(){
    	deptgrid = $("#leftgrid").ligerGrid({
			columns: [
				{display: '院内科室', name: 'dept_name', align: 'left',width:'40%',textField : 'text',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../../../sys/queryDeptDict.do?isCheck=false&is_last=1',
						keySupport : true,
						autocomplete : true,
						cancelable:false,
						onChanged:function(data){
							if(data.selected){
								data.record.dept_name = data.selected.text;
								data.record.dept_id = data.selected.id.split(".")[0];
							}else{
								data.record.dept_name = '';
								data.record.dept_id = '';
							}
						}	
					}
				},
				{display: '分摊比例(%)', name: 'ft_bl', align: 'right' ,width:'20%',editor:{type : 'float'},
					totalSummary:{
						//type: 'sum'
						render: function(suminf, column){
							leftSum = suminf.sum
							return "<span style='color:red'>分摊比例："+suminf.sum+"%</span>";
						}
					}
				},
				{display: '备注', name: 'note', align: 'left' ,width:'39%', editor:{type : 'text'}}
			],
			dataAction: 'server',usePager:false, url:'queryAccCooProjDept.do?isCheck=false',
			width: '50%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,enabledEdit: true

		});

    	deptgridManager = $("#leftgrid").ligerGetGridManager();
    }
	function loadHead2(){
    	deptgrid2 = $("#rightgrid").ligerGrid({
			columns: [
				{display: '合作对象', name: 'coop_obj_name', align: 'left',width:'15%',textField : 'coop_obj_name',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'coop_obj_name',
						cancelable:false,
						data:[
							{ id: 1, coop_obj_name: '客户' },
							{ id: 2, coop_obj_name: '供应商'}
						],
						onChanged :function(data){
							data.record.obj_name = "";
							deptgrid2.updateCell("obj_name","",data.rowindex);
							if(data.selected){
								data.record.coop_obj = data.selected.id;
								data.record.coop_obj_name = data.selected.coop_obj_name;
							}else{
								data.record.coop_obj = '';
								data.record.coop_obj_name = '';
							}	
						}
					}
				},
				{display: '外单位', name: 'obj_name', align: 'left',width:'35%',textField : 'text',
					editor :  {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						keySupport : true,
						autocomplete : true,
						cancelable:false,
						onChanged:function(this_data){
							if(this_data.selected){
								if(this_data.record.coop_obj == 1){
									this_data.record.cus_id = this_data.selected.id.split(".")[0];
								}
								if(this_data.record.coop_obj == 2){
									this_data.record.sup_id = this_data.selected.id.split(".")[0];
								}
								this_data.record.obj_name = this_data.selected.text;
							}else{
								this_data.record.cus_id = '';
								this_data.record.sup_id = '';
							}
						}
					}	
				},
				{display: '分摊比例(%)', name: 'ft_bl', align: 'right' ,width:'20%',editor:{type : 'float'},
					totalSummary:{
						//type: 'sum'
						render: function(suminf, column){
							rightSum = suminf.sum
							return "<span style='color:red'>分摊比例："+suminf.sum+"%</span>";
						}
					}
				},
				{display: '备注', name: 'note', align: 'left' ,width:'29%', editor:{type : 'text'}}
			],
			dataAction: 'server',usePager:false, url:'queryAccCooProjObj.do?isCheck=false', 
			width: '49.6%', height: '100%', rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,enabledEdit: true,checkbox:true,
			onBeforeEdit :function(rowData){
				if(rowData.column.name == 'obj_name'){
					if(rowData.record.coop_obj == 1){
						rowData.column.editor.url = '../../../../sys/queryCusDict.do?isCheck=false';
					}
					if(rowData.record.coop_obj == 2){
						rowData.column.editor.url = '../../../../sys/querySupDictDict.do?isCheck=false';
					}
				}
			}
		});

    	deptgridManager2 = $("#rightgrid").ligerGetGridManager();
    }
	
	function loadTopBar(){
		$("#topbar").ligerToolBar({ 
			items: [
                 { text: '删除',  click: del  , icon:'delete'},
                 { line:true },
                 { text: '保存',  click: save  , icon:'save'},
                 { line:true },
                 //{ text: '打印',  click: printDate  , icon:'print'},
                 //{ line:true },
                 //{ text: '打印模版',  click: printMode  , icon:'print'},
                 //{ line:true },
                 { text: '关闭',  click: cancle  , icon:'close'},
			]
		});
	}
	
	function printDate() {
		if (deptgrid.getData().length == 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}

		var para={
				template_code:'COOP02',
				class_name:"com.chd.hrp.acc.serviceImpl.autovouch.acccoodeptcost.AccCooProjServiceImpl",
				method_name:"queryAccCooProjDetailPrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:true,//是否预览，默认直接打印
				print_title: "合作项目单据",
				//ser_num : liger.get("ser_num").getValue()
				proj_code:liger.get("proj_code").getValue()
		}; 
		officeFormPrint(para);
	}
	
	function printMode(){
		officeFormTemplate({template_code : 'COOP02'});
	}
	
	function del(){
		var deptData = deptgridManager.getSelectedRows();
		var coopType = liger.get("coop_type").getValue();
		if(coopType == 2){
			var clientData = deptgridManager2.getSelectedRows();
		}
		if(coopType == 1 && deptData.length == 0){
			$.ligerDialog.error("请选择要删除的数据！");
			return;
		}
		if(coopType == 2 && deptData.length == 0 && clientData.length == 0){
			$.ligerDialog.error("请选择要删除的数据！");
			return;
		}
		$.ligerDialog.confirm('确定删除?', function (yes){
         	if(yes){
         		if(deptData.length != 0){
         			deptgrid.deleteRange(deptData);
            		deptgrid.addRow();
         		}
        		if(coopType == 2 && clientData.length != 0){
        			deptgrid2.deleteRange(clientData);
        			deptgrid2.addRow();
        		}
         	}
         }); 
	}
	
	function loadDict() {
		$("#proj_code").ligerTextBox({width : 180});
		$("#proj_name").ligerTextBox({width : 180});
		$("#note").ligerTextBox({width : 445});

		$("#coop_type").ligerComboBox({ 
			data: [
				{ id: 1, text: '院内'},
				{ id: 2, text: '院外'}
			], 
			isMultiSelect: false,
			value : 1,
			width : '180',
			onSelected:function(value){
				if(value == 2){
					if($("#rightgrid")[0].style.display == 'none'){
						$("#rightgrid").show();
						if(!deptgrid2){
						 	loadHead2();
						 	deptgrid2.addRow();
						}
					}
				}else{
					if($("#rightgrid")[0].style.display != 'none'){
						 $("#rightgrid").toggle();
					}
				}
			}
		});
		$("#state").ligerComboBox({  
            data: [
                { text: '启用', id: '0' },
                { text: '停用', id: '1' },
            ],
            width:180,
            value: 0
        });
		
	}
	
	function cancle(){
		 dialog.close();
	}
	
	function save(){
		if(!$("#proj_code").val()){
			$.ligerDialog.error("项目编码不能为空！");
			return;
		}
		if(!$("#proj_name").val()){
			$.ligerDialog.error("项目名称不能为空！");
			return;
		}
		if(!liger.get("coop_type").getValue()){
			$.ligerDialog.error("合作类型不能为空！");
			return;
		}
		if(!liger.get("state").getValue()){
			$.ligerDialog.error("状态不能为空！");
			return;
		}
		if(leftSum + rightSum > 100){
			$.ligerDialog.error("分摊比例合计不能大于100！");
			return;
		}

		var leftRow = liger.get("leftgrid").rows;
		var rightRow ;
		if(liger.get("coop_type").getValue() == 2){
			rightRow = liger.get("rightgrid").rows;
		}
		
		var para={
				note :liger.get("note").getValue(),
				coop_type :liger.get("coop_type").getValue(),
				is_stop :liger.get("state").getValue(),
				proj_code :liger.get("proj_code").getValue(),
				proj_name :liger.get("proj_name").getValue(),
				leftRow : JSON.stringify(leftRow),
				rightRow : JSON.stringify(rightRow),
		};
		
		ajaxJsonObjectByUrl("saveAccCooProj.do?isCheck=false",para,function(responseData){
			if(responseData.state=="true"){
            	parentFrameUse().query();
            	$("input[name='proj_code']").val('');
            	$("input[name='proj_name']").val('');
            	$("input[name='note']").val('');
            	deptgridManager.deleteRange(leftRow);
            	deptgrid.addRow();
            	if(liger.get("coop_type").getValue() == 2){
            		deptgridManager2.deleteRange(rightRow);
            		deptgrid2.addRow();
            	}
            }
        });
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="left" class="l-table-edit-td"></td>
			<td align="right" class="l-table-edit-td"><strong style="color: red;">*</strong>项目编码：</td>
			<td align="left" class="l-table-edit-td"><input id="proj_code" name="proj_code" /></td>	

			<td align="right" class="l-table-edit-td"><strong style="color: red;">*</strong>项目名称：</td>
			<td align="left" class="l-table-edit-td"><input id="proj_name" name="proj_name" /></td>	

		</tr>
		<tr>
			<td align="left" class="l-table-edit-td"></td>
			<td align="right" class="l-table-edit-td"><strong style="color: red;">*</strong>合作类型：</td>
			<td align="left" class="l-table-edit-td"><input id="coop_type" name="coop_type" /></td>	

			<td align="right" class="l-table-edit-td"><strong style="color: red;">*</strong>状态：</td>
			<td align="left" class="l-table-edit-td"><input id="state" name="state" /></td>	
		</tr>
		<tr>
			<td align="left" class="l-table-edit-td"></td>
			<td align="right" class="l-table-edit-td">备注：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input id="note" name="note" /></td>	
		</tr>

	</table>
	<div id="topbar"></div>
	<div>
		<div id="leftgrid" style="float: left; display: inline;" ></div>
		<div id="rightgrid" style="float: right; display: none;" ></div>
	</div>
</body>
</html>
