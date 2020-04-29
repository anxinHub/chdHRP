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
var source,saveButton,deptgrid,deptgridManager,proj,collectButton;
var state = '${state}';
var page_type = '${page_type}';
var queryshow=false;
var addshow=false;
var deleteshow=false;
var importshow=false;
var auditshow=false;
var printshow=false;
	$(function() {
		if(call == 'vouch'){
			 queryshow=true;
			 addshow=true;
			 deleteshow=true;
			 importshow=true;
			 auditshow=true;
			 printshow=true;
		}
		loadDict();
		loadHead();
		loadToolBar();
		
		if("update" == page_type){
			liger.get("ft_my").setValue('${ft_my}');
			liger.get("note").setValue('${note}');
			$("#year_month").val(dialog.get("data").year_month);
			query("1");
		}
	});
	
	//查询
	function query(times) {
		deptgrid.options.parms = [];
		deptgrid.options.newPage = 1;

		if(page_type == 'add'){
			if(!$("#year_month").val()){
				$.ligerDialog.error("统计年月不能为空！");
				return;
			}
			if(!liger.get("proj_code").getValue()){
				$.ligerDialog.error("项目不能为空！");
				return;
			}
			if(!liger.get("ft_para").getValue()){
				$.ligerDialog.error("分摊参数不能为空！");
				return;
			}
			
			deptgrid.options.parms.push({name : 'proj_code',value : liger.get("proj_code").getValue()});
			deptgrid.options.parms.push({name : 'year_month',value : $("#year_month").val()});
			deptgrid.options.parms.push({name : 'ft_para',value : liger.get("ft_para").getValue()});
			deptgrid.options.parms.push({name : 'times',value : times});
		}
		if(page_type == 'update'){
			deptgrid.options.parms.push({name : 'proj_code',value : dialog.get("data").proj_code});
			deptgrid.options.parms.push({name : 'year_month',value : dialog.get("data").year_month});
			deptgrid.options.parms.push({name : 'ft_para',value : '${ft_para}'});
			deptgrid.options.parms.push({name : 'times',value : times});
		}
		deptgrid.loadData(deptgrid.where);
	}
	
	function loadHead(){
    	deptgrid = $("#dept_div").ligerGrid({
			columns: [
				{display: '科室编码', name: 'dept_code', align: 'left',width:'12%',
					render :function(rowData){
						if(rowData.data_type == 2 && state == 1){
							return "<a href=javascript:openUpdate('"+rowData.dept_id+"',"+rowData.ft_my+","+rowData.__index+",'"+rowData.dept_name+"')>"+rowData.dept_code+"</a>";
						}else{
							return rowData.dept_code;
						}
					}	
				},
				{display: '科室名称', name: 'dept_name', align: 'left',width:'20%'},
				{display: '科室分类', name: 'kind_name', align: 'left',width:'10%'},
				{display: '科室类型', name: 'type_name', align: 'left',width:'10%'},
				{display: '科室性质', name: 'natur_name', align: 'left',width:'9%'},
				{display: '支出性质', name: 'out_name', align: 'left',width:'9%'},
				{display: '数据来源', name: 'data_type_name', align: 'left',width:'9%'},
				{display: '分摊比例(%)', name: 'ft_bl', align: 'right' ,width:'9%'},
				{display: '分摊金额', name: 'ft_my', align: 'right' ,width:'9%',
					render: function(rowData){
						return formatNumber(rowData.ft_my,2,1);
					}
				}
			],
			dataAction: 'server',usePager:true,url:'queryAccPubCostRegDept.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad: true,
			selectRowButtonOnly:true,enabledEdit: true,checkbox:true,
			toolbar: { 
				items: [
	   				{ text: '查询', id:'query', click: query, icon:'search' /* ,hide:queryshow */},
	   				{ line:true /* ,hide:queryshow */},
	   				{ text: '添加', id:'add', click: addPubCostRegDept, icon:'add' ,hide:addshow},
	   				{ line:true ,hide:addshow},
	   				{ text: '删除', id:'delete', click: removePubCostRegDept,  icon:'delete' ,hide:deleteshow},
	   				{ line:true ,hide:deleteshow},
	   				{ text: '导入', id:'import',  click: importDeptList,  icon:'up' ,hide:importshow},
	   				{ line:true ,hide:importshow},
	   				{ text: '打印', id:'print',  click: printDeptList, icon:'print' /* ,hide:printshow */},
	   				{ line:true ,hide:auditshow},
	   				{ text: '审核', id:'auditAccPubCostReg',  click: auditAccPubCostReg, icon:'ok' ,hide:auditshow},
	   				{ text: '取消审核', id:'unauditAccPubCostReg', click: unauditAccPubCostReg,  icon:'candle' ,hide:auditshow},
	   				{ line:true ,hide:auditshow},
	   				{ text: '关闭', id:'cancle', click: cancle,  icon:'close'},
	   				
	   			]}
		});

    	deptgridManager = $("#dept_div").ligerGetGridManager();
    }
	
	function openUpdate(dept_id,ft_my,index,dept_name){
		if(state == 2){
			$.ligerDialog.error("当前单据已审核，不能进行修改操作！");
			return;
		}
		if(!$("#year_month").val()){
			$.ligerDialog.error("统计年月不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		$.ligerDialog.open({
			title: '部门维护',
			height: $(window).height()*0.7,
			width: $(window).width()*0.4,
			url: 'pubCostRegDeptAddPage.do?isCheck=false',
			data:{
				all_ft_my : liger.get("ft_my").getValue(),
				year_month : $("#year_month").val(),
				proj_code : liger.get("proj_code").getValue(),
				ft_para : liger.get("ft_para").getValue(),
				note : liger.get("note").getValue(),
				ft_my : ft_my,
				dept_id : dept_id,
				dept_name : dept_name,
				type : 'update'
			},
			modal: true, showToggle: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}

	function addPubCostRegDept(){
		if(state == 2){
			$.ligerDialog.error("当前单据已审核，不能进行修改操作！");
			return;
		}
		if(!$("#year_month").val()){
			$.ligerDialog.error("统计年月不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		
		$.ligerDialog.open({
			title: '部门维护',
			height: $(window).height()*0.7,
			width: $(window).width()*0.4,
			url: 'pubCostRegDeptAddPage.do?isCheck=false',
			modal: true, showToggle: false, isResize: true,slide:false,
			data:{
				all_ft_my : liger.get("ft_my").getValue(),
				year_month : $("#year_month").val(),
				proj_code : liger.get("proj_code").getValue(),
				ft_para : liger.get("ft_para").getValue(),
				note : liger.get("note").getValue(),
				type : 'add'
			},
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
		
	}
	
	function removePubCostRegDept(){
		if(state == 2){
			$.ligerDialog.error("当前单据已审核，不能进行删除操作！");
			return;
		}
		var data = deptgridManager.getSelectedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选择要删除的数据！");
			return;
		}
	   
        ajaxJsonObjectByUrl("deleteAccPubCostRegDept.do",{deleteList : JSON.stringify(data),year_month : $("#year_month").val(),proj_code : liger.get("proj_code").getValue()},function(responseData){
            if(responseData.state=="true"){
            	deptgridManager.deleteSelectedRow();
            	liger.get("ft_my").setValue(responseData.ft_my);
            	parentFrameUse().query();
            }
        });
	}
	
	
	function auditAccPubCostReg(){
		if(!$("#year_month").val()){
			$.ligerDialog.error("统计年月不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		if(!liger.get("ft_para").getValue()){
			$.ligerDialog.error("分摊参数不能为空！");
			return;
		}
		var para={
				year_month : $("#year_month").val(),
				proj_code :liger.get("proj_code").getValue(),
				ft_my :liger.get("ft_my").getValue(),
				note :liger.get("note").getValue(),
				ft_para :liger.get("ft_para").getValue()
		};
		ajaxJsonObjectByUrl("auditAccPubCostReg.do?type=audit",para,function(responseData){
            if(responseData.state=="true"){
            	state = 2;
            	loadToolBar();
            	query();
            	parentFrameUse().query();
            	liger.get("ft_my").setValue(responseData.ft_my);
            }
        });
	}
	
	function unauditAccPubCostReg(){
		if(!$("#year_month").val()){
			$.ligerDialog.error("统计年月不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		if(!liger.get("ft_para").getValue()){
			$.ligerDialog.error("分摊参数不能为空！");
			return;
		}
		var para={
				year_month : $("#year_month").val(),
				proj_code :liger.get("proj_code").getValue(),
				ft_para :liger.get("ft_para").getValue()
		};
		
		ajaxJsonObjectByUrl("auditAccPubCostReg.do?type=unaudit",para,function(responseData){
            if(responseData.state=="true"){
            	state = 1;
            	loadToolBar();
            	query();
            	parentFrameUse().query();
            }
        });
	}
	
	function loadToolBar(){
		if("update"== page_type){
			$("#proj_code").ligerComboBox({disabled:true,cancelable : false});
            $("#year_month").ligerComboBox({disabled:true,cancelable : false});
            $("#ft_para").ligerComboBox({disabled:true,cancelable : false});
		}
		var button= $('div.l-toolbar-item'); 
        if(state == 1){
            $('div.l-toolbar-item').eq(5).show();
            $('div.l-toolbar-item').eq(6).hide();
            $("#ft_my").ligerComboBox({disabled:false,cancelable : true});
            $("#note").ligerComboBox({disabled:false,cancelable : true});
        }
        if(state == 2){
            $('div.l-toolbar-item').eq(5).hide();
            $('div.l-toolbar-item').eq(6).show();
            $("#proj_code").ligerComboBox({disabled:true,cancelable : false});
            $("#year_month").ligerComboBox({disabled:true,cancelable : false});
            $("#ft_para").ligerComboBox({disabled:true,cancelable : false});
            $("#ft_my").ligerComboBox({disabled:true,cancelable : false});
            $("#note").ligerComboBox({disabled:true,cancelable : false});
        }
        if("vouch" == call){
			$("#ft_my").ligerComboBox({disabled:true,cancelable : false});
            $("#note").ligerComboBox({disabled:true,cancelable : false});
		}
	}
	
	function printDeptList() {
		if (deptgrid.getData().length == 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		var heads = {
			"isAuto": true,//false 默认true，页眉右上角默认显示页码
			"rows": [
	  	          {"cell":0,"value":"统计年月："+$("#year_month").val() + "   项目："+$("#proj_code").val(),"colSpan":"5"}
	      	]
		};
		var printPara = {
			rowCount : 1,
			title : '费用登记部门表',
			columns : JSON.stringify(deptgrid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.acc.service.autovouch.accpubCost.AccPubCostRegService",
			method_name : "queryAccPubCostRegDeptPrint",
			bean_name : "accPubCostRegService",
			heads : JSON.stringify(heads)
		//表头需要打印的查询条件,可以为空
		};
		//执行方法的查询条件
		$.each(deptgrid.options.parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});
		officeGridPrint(printPara);
	}
	
	function importDeptList(){
		var year_month = $("#year_month").val()
		if(!year_month){
			$.ligerDialog.error("统计年月不能为空！");
			return;
		}
		var proj_code = liger.get("proj_code").getValue();
		if(!proj_code){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		var ft_para = liger.get("ft_para").getValue();
		if(!ft_para){
			$.ligerDialog.error("分摊参数不能为空！");
			return;
		}
		if(state == 2){
			$.ligerDialog.error("当前单据已审核，不能进行此操作！");
			return;
		}
		
		parent.$.ligerDialog.open({ 
       		url : 'hrp/acc/autovouch/accpubcost/pubcostreg/pubCostDeptImportPage.do?isCheck=false'
       				+'&year_month=' + year_month
       				+'&proj_code=' + proj_code + '&ft_my='+liger.get("ft_my").getValue() + '&ft_para='+ft_para
       				,
			data:{
				columns : deptgrid.columns, 
				grid : deptgrid,
				my_box : my_box
			}, height: $(window).height(),
			width: $(window).width(),title:'公用费用部门导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name  //用于parent弹出层调用本页面的方法或变量
		}); 
	    	
	}

	function loadDict() {
		autocompleteObj({
			  id: '#proj_code',
			  urlStr: "../../../../acc/queryHosDictType.do?isCheck=false&table_code=01005",
			  valueField: 'id',
			  textField: 'text',
			  autocomplete: true,//可输入
			  initWidth: '180',
			 // defaultSelect: true,//默认第一个值
			  autocompletelocal: true,//本地检索
			  initvalue: dialog.get("data").proj_code,
			  pageSize: 99999//下拉框默认显示条数
		});
		autocompleteObj({
			  id: '#ft_para',
			  urlStr: "../../../../acc/queryInitAccDict.do?isCheck=false&table_code=ACC_FT_PAPER",
			  valueField: 'id',
			  textField: 'text',
			  autocomplete: true,//可输入
			  initWidth: '180',
			 // defaultSelect: true,//默认第一个值
			  autocompletelocal: true,//本地检索
			  initvalue: '${ft_para}',
			  pageSize: 99999//下拉框默认显示条数
		});
		
		my_box = $("#ft_my").ligerTextBox({width : 180});
		year_box = $("#year_month").ligerTextBox({width : 180});
		note_box = $("#note").ligerTextBox({width :450});
		
		
		var today=new Date();
	    var h=today.getFullYear();
	    var m=today.getMonth()+1;
	    if(m<10){
	    	m = "0" +m
	    }
		$("#year_month").val(h + m);
		
		collectButton = $("#collect").ligerButton();
		saveButton = $("#save").ligerButton();

	}
	
	function cancle(){
		 dialog.close();
	}
	//保存主表格
	function save(){
		if(state == 2){
			$.ligerDialog.error("当前单据已审核，不能再次保存！");
			return;
		}
		if(!$("#year_month").val()){
			$.ligerDialog.error("统计年月不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		if(!liger.get("ft_para").getValue()){
			$.ligerDialog.error("分摊参数不能为空！");
			return;
		}
		if(!liger.get("ft_my").getValue()){
			$.ligerDialog.error("公用费用不能为空！");
			return;
		}
		var para={
				year_month : $("#year_month").val(),
				proj_code :liger.get("proj_code").getValue(),
				ft_para :liger.get("ft_para").getValue(),
				ft_my :liger.get("ft_my").getValue(),
				note :liger.get("note").getValue()
		};
		
		ajaxJsonObjectByUrl("saveAccPubCostReg.do",para,function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	query();
            	page_type = "update";
            	loadToolBar();
            }
        });
	}
	
	//计算费用和比例
	function collect(){
		if(state == 2){
			$.ligerDialog.error("当前单据已审核，不能计算！");
			return;
		}
		var ft_para = liger.get("ft_para").getValue();
		if(!ft_para){
			$.ligerDialog.error("分摊参数不能为空！");
			return;
		}
		var ft_my = liger.get("ft_my").getValue();
		if(!ft_my){
			$.ligerDialog.error("公用费用不能为空！");
			return;
		}
		if(!$("#year_month").val()){
			$.ligerDialog.error("统计年月不能为空！");
			return;
		}
		if(!liger.get("proj_code").getValue()){
			$.ligerDialog.error("项目不能为空！");
			return;
		}
		
		var para={
				year_month : $("#year_month").val(),
				proj_code :liger.get("proj_code").getValue(),
				ft_para :liger.get("ft_para").getValue(),
				ft_my :liger.get("ft_my").getValue(),
				note :liger.get("note").getValue()
		};
		
		ajaxJsonObjectByUrl("queryAccPubCostRegDeptCount.do?isCheck=false",para,function(responseData){
            if(responseData.state=="true"){
            	if(responseData.have_data == "true"){
					$.ligerDialog.confirm('已存在数据是否需要重新计算?', function (yes){
						if(yes){
							ajaxJsonObjectByUrl("collectAccPubCostReg.do",para,function(responseData){
					            if(responseData.state=="true"){
					            	query();
					            	page_type = "update";
					            	loadToolBar();
					            }
					        });
						}
					})
            	}else{
            		ajaxJsonObjectByUrl("collectAccPubCostReg.do",para,function(responseData){
			            if(responseData.state=="true"){
			            	query();
			            	page_type = "update";
			            	loadToolBar();
			            }
			        });
            	}
            }
        });
		
	}
	
	function num(obj){
	    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>统计年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="year_month" type="text" id="year_month" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width: 100px;" />
			</td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>项目：</td>
			<td align="left" class="l-table-edit-td"><input id="proj_code" name="proj_code" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>公用费用：</td>
			<td align="left" class="l-table-edit-td"><input id="ft_my" name="ft_my" oninput="num(this)"/></td>
			<td align="center"><input type="button" id="collect" value="计算" onclick="collect()"/></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><strong style="color: red;">*</strong>分摊参数：</td>
			<td align="left" class="l-table-edit-td"><input id="ft_para" name="ft_para" /></td>	
			<td align="right" class="l-table-edit-td">备注：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input id="note" name="note" /></td>	
			<td align="center"><input type="button" id="save" value="保存" onclick="save()"/></td>
		</tr>
	</table>
	<div id="maingrid" style="margin-top: 5px">
		 <div>
              <div id="dept_div"></div>
        </div>
	</div>
</body>
</html>
