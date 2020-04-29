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

	$(function ()
	{
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);	
		loadHotkeys();
	});

	//查询
	function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'begin_date', value: $("#begin_date").val()});
		grid.options.parms.push({name: 'end_date', value: $("#end_date").val()});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue()
		}); 
		grid.options.parms.push({name: 'change_no', value: $("#change_no").val()});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: "调整单号", align: "left", name: "change_no", width: 120, 
				render:function(rowdata, index, value){
					return "<a href=javascript:openUpdate('"+rowdata.change_id+"','"+index+"')>"+value+"</a>";
				}
			}, {
				display: "仓库", align: "left", name: "store_name", width: 120
			}, {
				display: "状态", align: "left", name: "state_name", width: 90
			}, {
				display: "制单人", align: "left", name: "maker_name", width: 90
			}, {
				display: "制单日期", align: "left", name: "make_date", width: 90
			}, {
				display: "审核人", align: "left", name: "checker_name", width: 90
			}, {
				display: "审核日期", align: "left", name: "check_date", width: 90
			}, {
				display: "确认人", align: "left", name: "confirmer_name", width: 90
			}, {
				display: "确认日期", align: "left", name: "confirm_date", width: 90
			}, {
				display: '备注', align: 'left', name: 'brief', width: 300 
			} ],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryMatSafeChangeList.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers: true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true,//heightDiff: -10,
			toolbar: { items: [ { 
				text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' 
			}, { 
				line:true 
			}, { 
				text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' 
			}, { 
				line:true 
			}, { 
				text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' 
			}, { 
				line:true 
			}, {
				text: '审核（<u>Z</u>）', id: 'audit', click: audit, icon: 'bluebook' 
			}, { 
				line:true 
			}, { 
				text: '消审（<u>U</u>）', id: 'unAudit', click: unAudit, icon: 'bookpen' 
			}, { 
				line:true 
			}, { 
				text: '确认（<u>C</u>）', id: 'confirm', click: confirm, icon: 'account' /* 
			}, { 
				line:true 
			}, { 
				text: '模板设置', id: 'printSet', click: printSet, icon: 'settings' 
			}, { 
				line:true 
			}, { 
				text: '批量打印（<u>P</u>）', id: 'print', click: print, icon: 'print'  */
			} ] },
			onDblClickRow : function (rowdata, rowindex, value){
				openUpdate(rowdata.change_id, rowindex);
			} 
		} );
		
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	var gridRowIdByOpen; //子页面所用
	
	//添加
	function add_open(){
		gridRowIdByOpen = null;
		parent.$.ligerDialog.open({ 
			title: '安全库存调整单->添加',
			url : 'hrp/mat/info/basic/safechange/matSafeChangeAddPage.do?isCheck=false',
			height: $(window).height(),
			width: $(window).width(),
			data:{}, 
			modal:true,showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: true, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	//修改
	function openUpdate(change_id, row_id){
		gridRowIdByOpen = row_id;
		parent.$.ligerDialog.open({ 
			title:'安全库存调整单->修改',
			url : 'hrp/mat/info/basic/safechange/matSafeChangeUpdatePage.do?isCheck=false&change_id='+change_id,
			height: $(window).height(),
			width: $(window).width(),
			data:{}, 
			modal:true, showToggle:false,showMax:false,
			isResize:true, showMin: true,showMax: true, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		var ids = "";
		var str = "";
		$.each(data, function (){
			if(this.state > 1){
				str += this.change_no + ",";
			}
			ids += this.change_id+",";
		})
		
		if(str.length > 0){
			$.ligerDialog.error("删除失败，单据【"+str.substr(0, str.length - 1)+"】不是未审核状态！");
			return false;
		}
		
		if(ids.length > 0){
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMatSafeChange.do",{ids: ids.substr(0, ids.length - 1)},function (res){
						if(res.state=="true"){
					    	gridManager.deleteSelectedRow();
						}
					});
				}
			}); 
		}
	}	
	
	//审核
	function audit(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 1){
				str += this.change_no +",";
			}
			ids += this.change_id + ",";
		})
		
		if(str != ''){
			$.ligerDialog.error("审核失败，单据【"+str.substr(0, str.length - 1)+"】不是未审核状态！");
			return false;
		}
		$.ligerDialog.confirm('确定审核所选单据吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMatSafeChange.do",{ids: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							grid.updateRow(this, {state: 2, state_name: '已审核', checker_name: res.checker_name, check_date: res.check_date});
						})
					}
				});
			}
		}); 
	}
	
	//消审
	function unAudit(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 2){
				str += this.change_no +",";
			}
			ids += this.change_id + ",";
		})
		
		if(str != ''){
			$.ligerDialog.error("消审失败，单据【"+str.substr(0, str.length - 1)+"】不是已审核状态！");
			return false;
		}
		$.ligerDialog.confirm('确定消审所选单据吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMatSafeChange.do",{ids: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							grid.updateRow(this, {state: 1, state_name: '未审核', checker_name: res.checker_name, check_date: res.check_date});
						})
					}
				});
			}
		}); 
	}
	
	//确认
	function confirm(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 2){
				str += this.change_no +",";
			}
			ids += this.change_id + ",";
		})
		
		if(str != ''){
			$.ligerDialog.error("确认失败，单据【"+str.substr(0, str.length - 1)+"】不是已审核状态！");
			return false;
		}
		$.ligerDialog.confirm('确定确认所选单据吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("confirmMatSafeChange.do",{ids: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							grid.updateRow(this, {state: 3, state_name: '已确认', confirmer_name: res.confirmer_name, confirm_date: res.confirm_date});
						})
					}
				});
			}
		}); 
	}
	
	//字典下拉框
	function loadDict(){ 
		//供应商下拉框
		autocomplete("#store_code", "../../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1});
		autoCompleteByData("#state", [{id: "1", text: "未审核"}, {id: "2", text: "已审核"}, {id: "3", text: "已确认"}], "id", "text", true, true);
		
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		$("#begin_date").ligerTextBox({width: 90});
		$("#end_date").ligerTextBox({width: 90});
		
		$("#change_no").ligerTextBox({width: 202});
	}  
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('Z', audit);
		hotkeys('U', unAudit);
	}
	
	//打印设置
	function printSet(){
		var useId=0;//统一打印
		if('${p04027 }'==1){
			//按用户打印
			useId='${user_id}';
		}
		
		officeFormTemplate({template_code:"04026",use_id:useId});
	}
	
	//打印
	function print(){
		var useId=0;//统一打印
		if('${p04027 }'==1){
			//按用户打印
			useId='${user_id}';
		}
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var pay_id ="" ;
			var in_nos = "";
			$.each(data, function (){
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				pay_id  += this.pay_id+","
			});
			
			var para={
					template_code: '04026',
					class_name: "com.chd.hrp.mat.service.payment.MatPayService",
					bean_name: "matPayService",
					method_name: "queryMatPayByPrintTemlate",
					//isSetPrint: flag,//是否套打，默认非套打
					isPreview: true,//是否预览，默认直接打印
					paraId: pay_id.substring(0, pay_id.length-1) ,
					use_id: useId,
					p_num: 1
			};
			
			officeFormPrint(para);
		}
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">
				制单日期：
			</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td store_td" width="10%">
				仓库：
			</td>
			<td align="left" class="l-table-edit-td store_td" width="20%">
				<input type="text" id="store_code" />
			</td>
			
			<td align="right" class="l-table-edit-td">
				状态：
			</td>
			<td align="left" class="l-table-edit-td">
				<input id="state" type="text"/>
			</td> 
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td" >
				调整单号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="change_no" />
			</td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
</body>
</html>
