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
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css"/>
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script type="text/javascript">
var dialog = frameElement.dialog;
var source,saveButton,sourcegridManager;
	$(function() {
		loadDict();
		$("#navtab").ligerTab({ onAfterSelectTabItem: function(tabid){
			if(tabid == '1'){
				 loadHead();
			}
			if(tabid == '2'){
				loadHead2();
			}
    	}});
		loadHead3();
	});
	
	
	function loadHead(){
    	deptgrid = $("#dept_list").ligerGrid({
			columns: [
				{display: '科室编码', name: 'dept_code', align: 'left'},
				{display: '科室名称', name: 'dept_name', align: 'left',
					editor : {
						type : 'select',
						valueField : 'text',
						textField : 'text',
						url : '../../../sys/queryDeptDict.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
						onChanged:function(data){
							var code = data.selected.text.split(" ")[0];
							data.record.dept_code = code;
							var name = data.selected.text.split(" ")[1];
							data.record.dept_name = name;
							var id = data.selected.id.split(".")[0];
							data.record.dept_id = id;
						}	
					},	
				},
				{display: '分摊系数', name: 'amortize_coefficient', align: 'right' ,editor: { type: 'float' }}
			],
			dataAction: 'server',usePager:true,url:'queryAmortizeDeptList.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,enabledEdit: true,checkbox:true,
			toolbar: { 
				items: [
	   				{ text: '查询', id:'query', click: queryDeptList, icon:'search' },
	   				{ line:true },
	   				{ text: '添加', id:'add', click: addDeptList, icon:'add' },
	   				{ line:true },
	   				{ text: '保存', id:'save', click: saveDeptList, icon:'save' },
	   				{ line:true },
	   				{ text: '删除', id:'delete', click: removeDeptList,  icon:'delete' },
	   			]}
		});

    	deptgridManager = $("#dept_list").ligerGetGridManager();
    }

	function queryDeptList(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			return;
		} 
		deptgrid.options.parms = [];
		deptgrid.options.parms.push({name : 'apply_code',value : apply_code});
		//加载查询条件
		deptgridManager.loadData(deptgrid.where);
	}
	function addDeptList(){
		deptgrid.addRow();
	}
	//保存科室列表
	function saveDeptList(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			$.ligerDialog.error("请先保存单据信息！");
			return;
		} 
		var data = deptgridManager.getSelectedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选择要保存的数据！");
			return;
		}
		
		 var param =[];
         $(data).each(function () {
             var rowdata={};
             if(this.dept_code){
            	 rowdata.apply_code = apply_code;
            	 rowdata.dept_id = this.dept_id;
            	 rowdata.dept_name = this.dept_name;
                 rowdata.amortize_coefficient = this.amortize_coefficient;
                 param.push(rowdata);
             }
         });
         
         ajaxJsonObjectByUrl("saveAmortizeDeptList.do",{saveList : JSON.stringify(param)},function(responseData){
             if(responseData.state=="true"){
            	 queryDeptList();
             }
         });
	}
	
	function removeDeptList(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			$.ligerDialog.error("请先保存单据信息！");
			return;
		} 
		var data = deptgridManager.getSelectedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选择要删除的数据！");
			return;
		}
	   
		var param =[];
        $(data).each(function () {
            var rowdata={};
            if(this.dept_code){
           	 	rowdata.apply_code = apply_code;
           	 	rowdata.dept_id = this.dept_id;
                param.push(rowdata);
            }
        });
		
        ajaxJsonObjectByUrl("deleteAmortizeDeptList.do",{deleteList : JSON.stringify(param)},function(responseData){
            if(responseData.state=="true"){
            	queryDeptList();
            }
        });
	}

	
	function loadHead2(){
		amortizedgrid = $("#amortized_histroy").ligerGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left'},
				{display: '月份', name: 'month', align: 'left'},
				{display: '科室编码', name: 'dept_code', align: 'left'},
				{display: '科室名称', name: 'dept_name', align: 'left'},
				{display: '资金来源', name: 'source_id', align: 'left'},
				{display: '本月摊销', name: 'this_amortized', align: 'right',
					render: function(rowData){
						return formatNumber(rowData.this_amortized,2,1);
					}	
				},
				{display: '累计摊销值', name: 'all_amortized', align: 'right' ,
					render: function(rowData){
						return formatNumber(rowData.all_amortized,2,1);
					}	
				}
			],
			dataAction: 'server',usePager:true,url:'queryAmortizeHistoryList.do?isCheck=false',
			width: '100%', height: '100%', rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			toolbar: { 
				items: [
	   				{ text: '查询', id:'query', click: queryHistroyList, icon:'search' },
	   			]}
		});

		amortizedgridManager = $("#amortized_histroy").ligerGetGridManager();
    }
	
	function queryHistroyList(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			return;
		} 
		amortizedgrid.options.parms = [];
		amortizedgrid.options.parms.push({name : 'apply_code',value : apply_code});
		//加载查询条件
		amortizedgridManager.loadData(amortizedgrid.where);
	}
	
	
	function loadHead3(){
		sourcegrid = $("#source").ligerGrid({
			columns: [
				{display: '资金来源', name: 'source_name', align: 'left',
					editor: { type: 'select' ,textField : 'text',valueField :'text',data:source}
				},
				{display: '金额', name: 'money', align: 'right',editor: { type: 'float' },totalSummary:{
                    type: 'sum'
                }}
			],
			dataAction: 'server',usePager:true,url:'queryAmortizeSourceList.do?isCheck=false',
			width: '100%', height: '100%', rownumbers:true,enabledEdit: true,checkbox:true,
			delayLoad: true,//初始化加载，默认false
			toolbar: { 
				items: [
	   				{ text: '查询', id:'query', click: querySourceList, icon:'search' },
	   				{ line:true },
	   				{ text: '添加', id:'add', click: addSourceList, icon:'add' },
	   				{ line:true },
	   				{ text: '保存', id:'save', click: saveSourceList, icon:'save' },
	   				{ line:true },
	   				{ text: '删除', id:'delete', click: removeSourceList,  icon:'delete' },
	   			]}
		});

		sourcegridManager = $("#source").ligerGetGridManager();
    }
	function querySourceList(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			return;
		} 
		sourcegrid.options.parms = [];
		sourcegrid.options.parms.push({name : 'apply_code',value : apply_code});
		//加载查询条件
		sourcegridManager.loadData(sourcegrid.where);
	}
	function addSourceList(){
		sourcegrid.addRow();
	}
	//保存资金来源列表
	function saveSourceList(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			$.ligerDialog.error("请先保存单据信息！");
			return;
		} 
		var data = sourcegridManager.getSelectedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选择要保存的数据！");
			return;
		}
		
		 var param =[];
         $(data).each(function () {
             var rowdata={};
             if(this.source_name){
            	 rowdata.source_id = this.source_name.split(" ")[0];
                 rowdata.money = this.money;
                 rowdata.apply_code = apply_code;
                 param.push(rowdata);
             }
         });
         
         ajaxJsonObjectByUrl("saveAmortizeSourceList.do",
        		 {saveList : JSON.stringify(param),amortize_year :liger.get("amortize_year").getValue() },function(responseData){
             if(responseData.state=="true"){
            	 querySourceList();
             }
         });
	}
	
	function removeSourceList(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			$.ligerDialog.error("请先保存单据信息！");
			return;
		} 
		var data = sourcegridManager.getSelectedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选择要删除的数据！");
			return;
		}
		
		 var param =[];
         $(data).each(function () {
             var rowdata={};
             if(this.source_name){
            	 rowdata.source_id = this.source_name.split(" ")[0];
                 rowdata.apply_code = apply_code;
                 param.push(rowdata);
             }
         });
		
		ajaxJsonObjectByUrl("deleteAmortizeSourceList.do",{deleteList : JSON.stringify(param)},function(responseData){
            if(responseData.state=="true"){
            	querySourceList();
            }
        });
	}
	
	function loadDict() {
		$("#apply_code").ligerTextBox({width : 180,disabled:true});
		liger.get("apply_code").setValue("编码由系统生成");
		$("#original_apply_code").ligerTextBox({width : 180});
		$("#apply_name").ligerTextBox({width : 180});
		$("#pact_code").ligerTextBox({width : 180});
		$("#amortize_year").ligerTextBox({width : 180});
		$("#amortized").ligerTextBox({width : 180});
		$("#origin_value").ligerTextBox({width : 180});
		$("#amortized_value").ligerTextBox({width : 180});
		$("#net_value").ligerTextBox({width : 180});
		$("#note").ligerTextBox({width : 180});
		saveButton = $("#save").ligerButton();
		$("#cancle").ligerButton();

		$.ajax({
			cache: false,
			async: false,
			type: 'post',
			dataType: 'json',
			url: "../../../sys/querySourceDict.do?isCheck=false",
			success: function (data) {
				source = data;
			}
		});
		
		autocomplete("#sup_id","../../../sys/querySupDictDict.do?isCheck=false", "id", "text",true, true, null, null, null, "180");
		autocomplete("#type_code", "../amortizetype/amortizeTypeSelect.do?isCheck=false", "id", "text", true, true, null, null, null, "180");

	}
	
	function cancle(){
		 dialog.close();
	}
	//保存主表格
	function save(){
		var apply_code = liger.get("apply_code").getValue();
		if(apply_code == "编码由系统生成"){
			apply_code = null;
		}
		if(!liger.get("apply_name").getValue()){
			$.ligerDialog.error("单据名称不能为空！");
			return;
		}
		if(!liger.get("amortize_year").getValue()){
			$.ligerDialog.error("摊销年限不能为空！");
			return;
		}
		if(!liger.get("origin_value").getValue()){
			$.ligerDialog.error("原值不能为空！");
			return;
		}
		if(!liger.get("type_code").getValue()){
			$.ligerDialog.error("待摊类别不能为空！");
			return;
		}
		saveButton.setDisabled();
		var para={
				apply_code : apply_code,
				original_apply_code :liger.get("original_apply_code").getValue(),
				apply_name :liger.get("apply_name").getValue(),
				pact_code :liger.get("pact_code").getValue(),
				amortize_year :liger.get("amortize_year").getValue(),
				amortized :liger.get("amortized").getValue(),
				origin_value :liger.get("origin_value").getValue(),
				amortized_value :liger.get("amortized_value").getValue(),
				net_value :liger.get("net_value").getValue(),
				note :liger.get("note").getValue(),
				sup_id :liger.get("sup_id").getValue().split(".")[0],
				type_code :liger.get("type_code").getValue()
		};
		
		ajaxJsonObjectByUrl("saveAmortizeInfo.do",para,function(responseData){
            saveButton.setEnabled();
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	if(responseData.apply_code){
	                liger.get("apply_code").setValue(responseData.apply_code);
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
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>流水号：</td>
			<td align="left" class="l-table-edit-td"><input id="apply_code" name="apply_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">原始单据号：</td>
			<td align="left" class="l-table-edit-td"><input id="original_apply_code" name="original_apply_code" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>单据名称：</td>
			<td align="left" class="l-table-edit-td"><input id="apply_name" name="apply_name" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>待摊类别：</td>
			<td align="left" class="l-table-edit-td"><input id="type_code" name="type_code" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>摊销年限：</td>
			<td align="left" class="l-table-edit-td"><input id="amortize_year" name="amortize_year" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">已摊销期间：</td>
			<td align="left" class="l-table-edit-td"><input id="amortized" name="amortized" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>原值：</td>
			<td align="left" class="l-table-edit-td"><input id="origin_value" name="origin_value" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">累计摊销值：</td>
			<td align="left" class="l-table-edit-td"><input id="amortized_value" name="amortized_value" /></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">净值：</td>
			<td align="left" class="l-table-edit-td"><input id="net_value" name="net_value" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">合同号：</td>
			<td align="left" class="l-table-edit-td"><input id="pact_code" name="pact_code" /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">供应商：</td>
			<td align="left" class="l-table-edit-td"><input id="sup_id" name="sup_id" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">备注：</td>
			<td align="left" class="l-table-edit-td"><input id="note" name="note" /></td>	
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td align="center"><input type="button" id="save" value="保存" onclick="save()"/></td>
			<td><input type="button" id="cancle" value="取消" onclick="cancle()"/></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<div id="maingrid" style="margin-top: 5px">
		 <div id="navtab">
		 		<div title="资金来源" tabid="0">
                  <div id="source"></div>
              </div>
              <div title="使用科室" tabid="1">
                  <div id="dept_list"></div>
              </div>
              <div title="摊销记录" tabid="2">
                  <div id="amortized_histroy"></div>
              </div>
          </div>
	</div>
</body>
</html>
