<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
    
    var tree, tree2,tree3;
    
    var setting = { 
			
		   		data: {simpleData: {enable: true}},
		   		
		   		treeNode:{open:true},
		   		
		        edit: {
		        	
					enable: true,
					
					showRemoveBtn: showRemoveBtn,
					
					showRenameBtn:false
					
				},callback: {
					
					beforeRemove: beforeRemove,
					
					beforeClick: showRemoveBtn,
					
					onClick: zTreeOnClick,
					
					onDblClick: zTreeOnDblClick
					
				}
				
	 	};
    var setting1 = {
    	
    	data: {simpleData: {enable: true}},
   		
   		treeNode:{open:true},
   		
   		check: {
   			enable: true,
   			chkStyle: "checkbox",
   			chkboxType: { "Y": "", "N": "" }
   		},

   		callback: {
   			onCheck: checkThisOnly
   		}
    }
    
    $(function ()
    {
		loadDict();
		
		loadTree();
		
    	$("#layout1").ligerLayout({ leftWidth: 220,rightWidth: '100%'});
    	
    	$("#subj_tab").ligerTab();
    	
    	$("#treeDiv").css("height", $(window).height()-28);

    	$("#column_name").ligerTextBox({width:200});
    	
    	
    	$("#note").ligerTextBox({width:805});
    	
    });
    
    function zTreeOnDblClick(event, treeId, treeNode) {
        alert(treeNode ? treeNode.tId + ", " + treeNode.id : "isRoot");
    };
    
    function beforeRemove(treeId, treeNode) {

		 var state= confirm("确认删除方案 " + treeNode.name + " 吗？");
			
			if (state) {
				 
				ajaxJsonObjectByUrl("deleteAccMultiPlan.do?plan_code="+treeNode.id,{}, function (responseData) {
					
					if (responseData.state == "true") {
						
					}
					
				}); 
				return true;
			}
			return false;
				
	}
	
	function showRemoveBtn(treeId, treeNode) {
		
		if(null==treeNode.pId){
			
			return false;
		}
		return true;
	}
	
	function zTreeOnClick(event, treeId, treeNode) {
		//加载数据
		setPage(treeNode.id);
		return;


		if (treeNode.id == 0) {
			plan_code="00";
		}else{
			plan_code = treeNode.id;
		} 
		
		$(".l-grid1 .l-grid-header-inner").width("100%");
		
		$("#listbox2").ligerListBox({
			parms : {"subj_dire":"0","plan_code": plan_code},
			url : 'queryAccMultiPlanSubjList.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 320,
			width:420
   		});

   		$("#listbox4").ligerListBox({
   			parms : {"subj_dire":"1","plan_code": plan_code},
   			url : 'queryAccMultiPlanSubjList.do?isCheck=false',
   			valueField : 'id',
   			textField : 'text',
   			isShowCheckBox : true,
   			isMultiSelect : true,
   			height : 320,
   			width:420
   		});

   }
    
    function loadTree(){
		ajaxJsonObjectByUrl("queryAccMultiPlanTree.do?isCheck=false",{},function(responseData){	
			  tree = $.fn.zTree.init($("#tree"), setting, responseData.Rows);
		}); 
    }
    

    function loadDict(){
		//字典下拉框
		$("#listbox2").ligerListBox({height : 320,width:420,isShowCheckBox : true,isMultiSelect : true}); 
          
		$("#listbox4").ligerListBox({height : 320,width:420,isShowCheckBox : true,isMultiSelect : true}); 
        
		autocompleteObj({
    		id:"#subj_name",
    		urlStr:'../querySubjAll.do?isCheck=false',
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		highLight:true,
    		parmsStr:null,
    		defaultSelect:true,
    		initvalue:null,
    		initWidth:"160",
    		initHeight:null,boxwidth:null,alwayShowInDown:null,
    		selectEvent:function(value) {
    			var subj_code=value.split(".")[1];
				$("#listbox1").ligerListBox({
					/* parms : {'subj_code':subj_code},
					url : 'queryAccSubjList.do?isCheck=false',
					valueField : 'id',
					textField : 'text',
					isShowCheckBox : true,
					isMultiSelect : true, */
					height : 320,
					width:420
					
				});
				
				$("#listbox3").ligerListBox({
					parms : {'subj_code':subj_code},
					url : 'queryAccSubjList.do?isCheck=false',
					valueField : 'id',
					textField : 'text',
					isShowCheckBox : true,
					isMultiSelect : true,
					height : 320,
					width:420
				});
				queryAccSubjTree(subj_code);
    			
    		}
    	});
  
		var $choose = $("input[name='choose']");
		$choose.change(function(){
			var subj_code = liger.get("subj_name").getValue();
			loadModeTreeData(subj_code);
		});
    }
    function queryAccSubjTree(subj_code){
		loadModeTreeData(subj_code)
    }

	/**
	 * 查找父节点，递归
	 * @param  {obj} node     ztree节点
	 * @param  {boolean} disabled 是否禁用
	 */
	function findParendDisabled (node, disabled, treeName) {
		var parentNode = node.getParentNode();

		if (parentNode) {
			// 如果是有勾选状态，且要启用，就不执行
			if (!((parentNode.check_Child_State === 1 || parentNode.check_Child_State === 2) && !disabled)) {
				treeName.setChkDisabled(parentNode, disabled, false, false);
			}

			findParendDisabled(parentNode, disabled, treeName);
		}
	};
	/**
	 * 查找子节点，递归
	 * @param  {obj} node     ztree节点
	 * @param  {boolean} disabled 是否禁用
	 */
	function findChildDisabled (node, disabled, treeName) {
		if (node.isParent) {
			for (var i = 0; i < node.children.length; i++) {
				var childNode = node.children[i];

				treeName.setChkDisabled(childNode, disabled, false, false);

    			findChildDisabled(childNode, disabled, treeName);
			}
		}
	};
	/**
	 * 点击checkbox方法。父子节点互斥
	 */
    function checkThisOnly (event, treeId, treeNode) {
    	var checkedStatus;
    	var treeName;
    	
    	if (treeId === "tree2") {
    		treeName = tree2;
    	} else if (treeId === "tree3") {
    		treeName = tree3;
    	}

    	if (treeNode.getParentNode()) {
	    	checkedStatus = treeNode.getParentNode().check_Child_State;
    	}

		if (treeNode.checked) {
			if (checkedStatus === 1 || checkedStatus === 2) {
				findParendDisabled(treeNode, true, treeName);
			}
    		findChildDisabled(treeNode, true, treeName);
    	} else {
    		if (checkedStatus !== 1 && checkedStatus !== 2) {
    			findParendDisabled(treeNode, false, treeName);
    		}
    		findChildDisabled(treeNode, false, treeName);
    	}
    }
    
	//保存
    function save(){
		var analy_type = $('input[name="choose"]:checked ').val();
		if($("#column_name").val()===''){
			$.ligerDialog.warn('多栏帐名称为必填项，不能为空！');
			return;
		}
		if(liger.get("subj_name").getValue()===''){
			$.ligerDialog.warn('会计科目为必填项，不能为空！');
			return;
		}
		var formData = {
			column_name:$("#column_name").val(), //名称
			note:$("#note").val(),
			analy_type:analy_type,
			subj_code : liger.get("subj_name").getValue().split(".")[1],
			debitList:"",
			creditList:""
		}
		// var debitCheck = tree2 ? tree2.getCheckedNodes() : [];
		// var creditCheck = tree3 ? tree3.getCheckedNodes() : [];
	
		if(analy_type==="1"||analy_type==="3"){
			var debitList = tree2.getCheckedNodes();

			debitList = debitList.map(function (item) {
				return item.id;
			})
			
			
			formData.debitList = debitList.join(",");
			if(formData.debitList==''){
				$.ligerDialog.warn('请选择借方分析方式');
				return;
			}
		}
		if(analy_type==="2"||analy_type==="3"){
			var creditList = tree3.getCheckedNodes();
			
			
			creditList = creditList.map(function (item) {
				return item.id;
			})
			
			formData.creditList = creditList.join(",");
			if(formData.creditList===''){
				$.ligerDialog.warn('请选择贷方分析方式');
				return;
			}
		}
		console.log(formData)
    	 ajaxJsonObjectByUrl("addAccMultiPlan.do",formData,function (responseData){
    		parentFrameUse().loadDict()
    	 });
    	
    }
    
    function saveAccColumn(){
        if($("form").valid()){
        	save();
        }
   	}
    
	//切换分析方式**
	function showMode(value){
		$("fieldset").hide();
		if(value==="1"){
			$("#debit").show();
		}else if(value==="2"){
			$("#credit").show();
		}else if(value==="3"){
			$("#debit").show();
			$("#credit").show();
		}
	}

	//加载分析方式的树形数据**
	function  loadModeTree(value,data){
		if(value === "1"){
			tree2 = $.fn.zTree.init($("#tree2"), setting1, data);
		}else if(value === "2"){
			tree3 = $.fn.zTree.init($("#tree3"), setting1, data);
		}else if(value === "3"){
			tree2 = $.fn.zTree.init($("#tree2"), setting1, data);
			tree3 = $.fn.zTree.init($("#tree3"), setting1, data);
		}
	}
	
	function setPage(column_code){
		
		ajaxJsonObjectByUrl("getAccMultiPlanDate.do?isCheck=false&plan_code="+column_code,'',function (res){
			console.log(res);
			var radio = res.analy_type;
			$("input[name='choose'][value='"+radio+"']").prop("checked","checked");
			var checkData = {
				debit:res.debitList||[],
				credit:res.creditList||[]
			}
			loadModeTreeData(res.SUBJ_CODE,checkData);
    	});
		
	}

	//获取选中的code 
	//规则：父节点选中
	function getCheckCodes(nodes){
		var codes = [];
		for (var i = 0; i < nodes.length; i++) {
			var node = nodes[i];
			if(codes.indexOf(node.pId)!==-1){//判断避免重复添加
				break;
			}
			if(node.check_Child_State === -1){//没有子节点
				codes.push(node.id);
			}else if(node.check_Child_State === 2){ //全选
				codes.push(node.id);
			}else if(node.check_Child_State === 1){
				for (var j = 0; j < node.children.length; j++) {
					var child = node.children[j];
					codes.push(child.id);
				}
			}
		}
		return codes.toString();
	}

	//加载分析方式树
	function loadModeTreeData(subj_code,checkData){
		var value = $("input[name='choose']:checked").val();
		ajaxJsonObjectByUrl("queryAccSubjTree.do?isCheck=false&subj_code="+subj_code,'',function(responseData){	
    		var value = $("input[name='choose']:checked").val();
			showMode(value);
			loadModeTree(value,responseData.Rows);
			//如果有值就让树中内容选中
			if(checkData){
				var debitCheck = checkData.debit;
				var creditCheck = checkData.credit;
				if(debitCheck&&debitCheck.length>0){
					setCheckNodes(tree2,debitCheck);
				}
				if(creditCheck&&creditCheck.length>0){
					setCheckNodes(tree3,creditCheck);
				}
			}
		});
	}

	//设置树选中
	function setCheckNodes(tree,arrId){
		var treeNodes = tree.transformToArray(tree.getNodes());
		for (var i = 0; i < treeNodes.length; i++) {//循环所有节点
			var node = treeNodes[i];
			if(arrId.indexOf(node.id)!==-1){//判断节点是否在被check的数组中
				tree.checkNode(node,true,true);
			}
		}
	}
	</script>
<style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
		
		.mode{
			border:1px solid #aecaf0;
			width:400px;
			height:350px;
			margin-left:10px;
			float:left;
		}

		.mode legend{
			padding:5px;
			margin-left:5px;
			font-size:15px;
		}

		.mode div{
			height:80%;
			overflow-y:auto;
		}
	</style>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <div id="layout1">
            <div position="left">
            
            <div  style="overflow:auto;" id="treeDiv">
		       	<ul class="ztree"  id="tree"></ul>
		    </div>
            
            </div>
            
            <div position="right">
            <form name="form1" method="post"  id="form1" >
            <table cellpadding="0" cellspacing="0" class="l-table-edit" style="height:10%;width: 100%">
		        <tr>
		        	<td align="right" class="l-table-edit-td" >会计科目：</td>
		        	<td align="left" class="l-table-edit-td" style="width: 10%"><input name="subj_name" type="text" id="subj_name" ltype="text" /></td>
		        	<td align="center" class="l-table-edit-td"></td>
		        	<td align="left" class="l-table-edit-td"  >多栏账名称：</td>
		        	<td align="left" class="l-table-edit-td" ><input name="column_name" type="text" id="column_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
		        	<td align="center" class="l-table-edit-td"></td>
		        	<td align="right" class="l-table-edit-td" >备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
		        	<td align="left" class="l-table-edit-td" colspan="7"><input name="note" type="text" id="note" ltype="text" validate="{required:false,maxlength:50}" /></td>
		        	<td align="center" class="l-table-edit-td"></td>
		        </tr> 
		    </table>
            </form>
            <div style="height: 90%;width: 100%">
            <div >
	            <div style="float: left;padding-left: 8px">分析方式：</div>
	            <div style="float: left;padding-left: 40px"><input name="choose" type="radio" id="choose" ltype="text" checked="checked" value="1"/></div>
	            <div style="float: left">借方分析</div>
	            <div style="float: left;padding-left: 40px" > <input name="choose" type="radio" id="choose" ltype="text" value="2"/></div>
	            <div style="float: left">贷方分析</div>
	            <div style="float: left;padding-left: 40px"><input name="choose" type="radio" id="choose" ltype="text" value="3"/></div>
	            <div style="float: left">余额分析</div>
            </div>
            <div style="width: 100%;float: left;"><hr/></div>
            <div>
				<fieldset id="debit" class="mode">
					<legend>借方分析方式</legend>
					<div>  
						<ul class="ztree" id="tree2"></ul> 
					</div>
				</fieldset>
				
				<fieldset id="credit" class="mode">
					<legend>贷方分析方式</legend>
					<div>  
						<ul class="ztree" id="tree3"></ul> 
					</div>
				</fieldset>
            </div>
            
           </div>
         
         </div>  
        
        </div> 
	
</body>
</html>
