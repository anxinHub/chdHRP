<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html;">
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,select,grid,datepicker"
		name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=path%>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>

<script>
	var accGrid, budgGrid, formValidate;
	var dire_source = [{"label": "借方", "id": 0}, {"label": "贷方", "id": 1}];
	var treeSetting;
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	
	
	//验证初始化
	function initValidate() {
		formValidate = $.etValidate({
			items : [ {
				el : $("#tp_code"),
				required : true
			}, {
				el : $("#tp_name"),
				required : true
			}, {
				el : $("#sort_code"),
				required : true
			}, ]
		});
	}
	
	//字符自增 
	function incChar(char0){
		if(!char0){
			return "A";
		}
		
		char0 = char0.toUpperCase(); //转大写
		var char1 = 0, char2 = 0, newChar = "";
		
		char1 = char0.substr(0, 2).charCodeAt();
		
		if(char0.length > 1){
			char2 = char0.substr(1, 2).charCodeAt();
			if(char2 == 90){
				char2 = 65;
				char1 += 1;
			}else{
				char2 += 1;
			}
			newChar = String.fromCharCode(char1) + String.fromCharCode(char2);
		}else{
			if(char1 == 90){
				newChar = "AA";
			}else{
				newChar = String.fromCharCode(char1 + 1);
			}
		}
		
		return newChar;
	}
	
	//财务会计查询
	function queryAcc(tpCode){
		params = [ {
			name: 'tp_code', value: tpCode 
		}, {
			name: 'kind_code', value: '01' 
		}, {
			name: 'acc_year', value: $("#vouch_date",parent.document).val().substring(0,4) 
		}]
		
		accGrid.loadData(params, "../../autovouch/accbudgtp/queryDetailByCodeVouch.do?isCheck=false");
	}
	//财务会计Grid
	function initAccGrid() {
		/* 列头 */
		var columns = [ {
			display: '财务会计科目', name: 'subj_name', width: 240, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'subj_code',
				url : '../../autovouch/accbudgtp/querySubjSelect.do?isCheck=false&kind_code=01&acc_year='+$("#vouch_date",parent.document).val().substring(0,4), 
			}
		}, {
			display: '摘要', name: 'summary', width: 160, isSort: false, 
			editor: {
				type: 'text'
			}
		}, {
			display: '方向', name: 'dire_name', width: 50, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'dire',
			    source: dire_source,
			}
		}, {
			display: '编号', name: 'sort_code', width: 55, isSort: false, hidden: false, editable: false, 
			editor: {
				type: 'number'
			}
		}, {
			display: '科目类型', name: 'kind_code', width: 60, hidden: true, 
		} ]; 
		
		
		/* 表格基础参数 */
		var paramObj = {	
			width: 'auto',
			height: '100%',
			usePager: false, 
			editable: true,
			inWindowHeight: true,
			checkbox: true,
			columns: columns, 
			showButton: false
		};
		
		accGrid = $("#accGrid").etGrid(paramObj);
	}
	
	var accIndex = 1;
	function addAccRow(){
		if(acc_first && accGrid.getAllData() && accGrid.getAllData().length > 0){
			accSortCode = accGrid.getAllData()[accGrid.getAllData().length - 1].sort_code;
			acc_first = false;
		}
		accSortCode = incChar(accSortCode);
		accGrid.addRow({kind_code: '01', sort_code: accSortCode, dire: 0, dire_name: '借方'});
	}
	function removeAccRow(){
		accGrid.deleteSelectedRows();
		acc_first = true;
	}
	
	//预算会计查询
	function queryBudg(tpCode){
		params = [ {
			name: 'tp_code', value: tpCode 
		}, {
			name: 'kind_code', value: '02' 
		}, {
			name: 'acc_year', value: $("#vouch_date",parent.document).val().substring(0,4) 
		} ]
		
		budgGrid.loadData(params, "../../autovouch/accbudgtp/queryDetailByCodeVouch.do?isCheck=false");
	}
	//预算会计Grid
	function initBudgGrid() {
		/* 列头 */
		var columns = [ {
			display: '预算会计科目', name: 'subj_name', width: 240, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'subj_code',
				url : '../../autovouch/accbudgtp/querySubjSelect.do?isCheck=false&kind_code=02&acc_year='+$("#vouch_date",parent.document).val().substring(0,4),
			}
		}, {
			display: '摘要', name: 'summary', width: 160, isSort: false, 
			editor: {
				type: 'text'
			}
		}, {
			display: '方向', name: 'dire_name', width: 50, isSort: false, 
			editor: {
				type: 'select',
			    autoFocus : true,   //  为true时 下拉框默认选择第一个
			    disabled : false,
				keyField : 'dire',
			    source: dire_source,
			}
		}, {
			display: '取值公式', name: 'cal', width: 100, isSort: false, 
			editor: {
				type: 'text'
			}
		}, {
			display: '编号', name: 'sort_code', width: 55, hidden: true, isSort: false, editable: false, 
		}, {
			display: '科目类型', name: 'kind_code', width: 60, hidden: true, 
		} ]; 
	
		
		/* 表格基础参数 */
		var paramObj = {	
			width: 'auto',
			height: '100%', 
			usePager: false, 
			editable: true,
			inWindowHeight: true,
			checkbox: true,
			columns: columns,
		};
		
		budgGrid = $("#budgGrid").etGrid(paramObj);
	}
	
	var accSortCodes = {};
	var errorMsg = "";
	//校验公式是否合法
	function checkCal(row, cal) {
		var flag = true;
	    try{
			var ret=cal.replace(/[a-zA-Z]+/g, function () {
				//调用方法时内部会产生 this 和 arguments
				//console.log(arguments[0]);
				var v_char = arguments[0].toUpperCase();
				if(!accSortCodes[v_char]){
					errorMsg += "预算会计第" + row + "行取值公式中编号" + v_char + "不存在<br/>";
					flag = false;
				}
				return 1;
			});
	    	eval("cal="+ret);
	    	return flag;
	    }catch(err){
	    	errorMsg += "预算会计第" + row + "行取值公式不合法<br/>";
	    	return false;
	    }
	}
	
	
	//保存
	function save() {
		
		var node = treeMod.getSelectedNodes()[0];
		if (node == null || node.pId == null) {
			return;
		}
				
		//组装明细
		var allData = [];
		var accData = accGrid.getAllData();
		var budgData = budgGrid.getAllData();
		
		if(accData.length == 0 || budgData.length == 0){
            $.etDialog.warn("请添加分录信息");
            return false;
		}
		
		var msg = "";
		$.each(accData,function(index, v){
			if(!v.subj_name || !v.sort_code){
				msg += "财务会计第"+(index+1)+"行科目、排序号不能为空<br/>";
			}else{
				allData.push(v);
				accSortCodes[v.sort_code] = 1;
			}
		});
		$.each(budgData,function(index, v){
			if(!v.subj_name || !v.sort_code){
				msg += "预算会计第"+(index+1)+"行科目、排序号不能为空<br/>";
			}else{
				//校验公式
				errorMsg = "";
				if(!checkCal(index+1, v.cal)){
					msg += errorMsg;
				}
				allData.push(v);
			}
		});
		
		if(msg){
			$.etDialog.warn(msg);
            return false;
		}
		
		ajaxPostData({
			url : '../../autovouch/accbudgtp/saveAccBudgTp.do',
			data : {
				tp_code: node.id, 
				tp_name: node.name,
				is_update: 1, 
				allData: JSON.stringify(allData)
			},
		    success: function (responseData) {
	           
			},
		})
	}
	
	//凭证存模板
	function vouchSave(){
		//alert('实现中...')
		//var cal="(a+aa+b)*(-1)";
		/* var ret = cal.replace(/[0-9]*(\.[0-9]*)?/g,function(e){
			if(e!=""){
				console.log(e)
				return 1
			}
			
		});  */
		/* var ret=cal.replace(/[a-zA-Z]+/g, function () {
	        //调用方法时内部会产生 this 和 arguments
	        console.log(arguments[0]);
	        return arguments[0];
		});
		console.log(ret) */
	}
	
	
	//删除
	function del() {
		var node = treeMod.getSelectedNodes()[0];
		if (node == null || node.pId == null) {
			return;
		}
		
		$.etDialog.confirm('确定删除?', function () {
			ajaxPostData({
				url: '../../autovouch/accbudgtp/deleteAccBudgTp.do',
				data: {
					'tp_codes' : node.id
				},
				success: function () {
					myFresh();
					parent.vouchJson["px_tp_code"]="";
					parent.vouchJson["px_tp_name"]="";
					parent.vouchJson["px_tp_note"]="";
				}
			})
		});
	}

	
	function loadData(){
		var node = treeMod.getSelectedNodes()[0];
		if (node == null || node.pId == null) {
			return;
		}
		queryBudg(node.id);
		queryAcc(node.id);
	}
	
	function initLayout(){
		
		$("#treeModDiv").css("height", $(window).height()-30);
		$("#layout1").ligerLayout({ leftWidth: 150, allowLeftResize: true });
		
		var leftWidth = $(window).width()-160;
		$("#layout").ligerLayout({
			topHeight: 30, 
			leftWidth: leftWidth, 
			InWindow: true, 
			height: '100%', 
			//centerWidth: '5', 
			rightWidth: leftWidth, 
			//bottomHeight: 50, 
			onLeftToggle: function (isColl){
				if(isColl == false) {
					budgGrid.refreshView();
				}
	        },
	        onRightToggle: function (isColl){
				if(isColl == false) {
					accGrid.refreshView();
				}
	        }
		});
	}
	
	//加载平行记账模板
    function loadBudgTpTree(){
    	
    	var subjCode=[];
    	var isMoney=0;
    	$.each(parent.frameObj.grid.getGridData(),function(i,obj){
    		if(obj.subj_code!=""){
    			if(parent.frameObj.getSubjAttr(obj.subj_code).kind_code=="02"){
    				return true;
    			}
    			
    			if(parseFloat(obj.debit)!=0){
    				isMoney=1;
    				subjCode.push({subj_code: obj.subj_code, dire:0});
    			}else if(parseFloat(obj.credit)!=0){
    				isMoney=1;
    				subjCode.push({subj_code: obj.subj_code, dire:1});
    			}else{
    				subjCode.push({subj_code: obj.subj_code, dire:2});
    			}
    		}
    	});
    
    	if(subjCode.length==0){
    		$.etDialog.error("请录入财务会计科目.");
    		return;
    	}
    	
    	var is_dire=0;
    	if($("#is_dire").is(':checked') && isMoney==1){
    		is_dire=1;
    	}
    	var is_all=0;
    	if($("#is_all").is(':checked')){
    		is_all=1;
    	}
    	
		var param={
			param:JSON.stringify(subjCode),
			is_dire: is_dire,
			is_all:  is_all
		};
		
   	 	ajaxJsonObjectByUrl("queryAccBudgTpTree.do?isCheck=false",param,function (responseData){
     		treeMod=$.fn.zTree.init($("#treeMod"), treeSetting, responseData.Rows);
			if(responseData.Rows.length>0){
				var node = treeMod.getNodeByParam("id",responseData.Rows[0].id);
				treeMod.selectNode(node, true);
				loadData();
			}else{
				if(budgGrid && budgGrid.getAllData()){
					$.each(budgGrid.getAllData(),function(i,o){
						budgGrid.deleteRows([{rowIndx:i}]);
					});
				}
				
				if(accGrid && accGrid.getAllData()){
					$.each(accGrid.getAllData(),function(i,o){
						accGrid.deleteRows([{rowIndx:i}]);
					});
				}
			}
        });
    	
    }
	
	function myClose(){
		parent.layer.close(index); //再执行关闭 
	}
	
	function saveBudgData(){
		
		if(parent.isReadOnly){
			$.etDialog.error("当前凭证不可编辑.");
			return;
		}
		
		var data=budgGrid.selectGetChecked();
		if(data.length==0){
			data=budgGrid.getAllData();
		}
		
		if(data && data.length==0){
			return;
		}
		
		if($("#is_budg_val",parent.document).val()==1 && $("#budg_subj_nameSum",parent.document).text().replace(/\s+/g,"")!="零元整"){
			//分栏式
			if(!confirm("是否重新生成预算会计？")){
				return;
			}
		}else if($("#is_budg_val",parent.document).val()==2){
			var isConfirm=false
			$.each(parent.frameObj.grid.getGridData(),function(i,obj){
	    		if(obj.subj_code!=""){
	    			if(parent.frameObj.getSubjAttr(obj.subj_code).kind_code=="02"){
	    				isConfirm=true;
	    				return false;
	    			}
	    		}
	    	});
			if(isConfirm && !confirm("是否重新生成预算会计？")){
				return;
			}
		}
		
		var is_dire=0;
    	if($("#is_dire").is(':checked')){
    		is_dire=1;
    	}
    	var node = treeMod.getSelectedNodes()[0];
		if (node != null && node.pId != null) {
			parent.vouchJson["px_tp_code"]=node.id;
			parent.vouchJson["px_tp_name"]=node.name;
			parent.vouchJson["px_tp_note"]=node.tp_note;
		}
		
		parent.saveBudgByAccTp(data,accGrid.getAllData(),is_dire);
	}
	
	function myFresh(){
		loadBudgTpTree();
	}
	
	
	function loadToolbar(){
	   	 //工具条
		var isAllCheck=Local.get("acc[vouch[is_all_check");
		if(isAllCheck==null || isAllCheck=="false"){
			isAllCheck="unchecked";
		}else{
			isAllCheck="checked";
		}
	   	 
		var isDireCheck=Local.get("acc[vouch[is_dire_check");
		if(isDireCheck==null || isDireCheck=="true"){
			isDireCheck="checked";
		}else{
			isDireCheck="unchecked";
		} 
		
	    $("#toptoolbar").ligerToolBar({ items: [
			   {text: '全匹配 <input type="checkbox" id="is_all" '+isAllCheck+'/>', id: 'is_all', icon: ''},
			   {text: '按方向 <input type="checkbox" id="is_dire" '+isDireCheck+'/>', id: 'is_dire', icon: ''},
			   { text: '刷新', click: myFresh, icon:'refresh' },
	           { text: '生成预算会计', click: saveBudgData, icon:'cashier' },
	           { line:true },
	           { text: '删除模板', click: del, icon:'delete' },
	           { text: '保存模板', click: save, icon:'save' },
	           { text: '关闭', click: myClose, icon:'close' },
	           { line:true }
	      ]
	    });
	
	    $("#is_all").change(function() { 
		    Local.set("acc[vouch[is_all_check",$("#is_all").is(':checked'));
		});
	     
	    $("#is_dire").change(function() { 
	    	Local.set("acc[vouch[is_dire_check",$("#is_dire").is(':checked'));
	 	});
	 	
	     
	}
	
	$(function() {
		treeSetting = {   
   			data: {
   				simpleData: {
   					enable: true,
   					idKey: "id",
   					pIdKey: "pId",
   					rootPId: 0
   				},
   				key: {title:"title"}
   			},callback: {
   				onClick: function () {
   					loadData()
   				}
   			}
    	}; 
		
		loadToolbar();
		loadBudgTpTree();
		initBudgGrid(); 
		initAccGrid();
		initLayout();
		
		$('.l-layout-bottom, .l-layout-center, .l-layout-centerbottom, .l-layout-left, .l-layout-right, .l-layout-top ').css('z-index','2')
	
	})
</script>
</head>

<body style="overflow-x: hidden">

<div  id="layout1" class="l-layout">

	<div position="left" title="模板列表">
		<div id="treeModDiv" class="ztree" style="overflow:auto;">
			<ul id="treeMod"></ul>
		</div>
	</div>
	<div position="center" id="centerReport">
	
		<div position="centerbottom" id="navtab1" style="border:1px solid #A3C0E8; ">
			<div position="top"  id="toptoolbar"></div>
			<div id="layout" style="z-index: 2">
				<div position="left" title="预算会计（取值公式：财务会计的编号运算公式，如：A+B+C-D）"> 
					<div id="budgGrid"></div>
				</div>
				
				<div position="right" title="财务会计">
					<div id="accGrid"></div>
				</div>
			</div>
		
			<!-- div tabid="subjDiv" title="对照" lselected="true" >
				<div id="subjgrid" style="margin-top: 5px"></div>
			</div>
			<div tabid="template" title="模板" >
			
			</div-->
		</div>
	</div>
</div>



</body>

</html>