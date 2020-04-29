<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!-- link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" /-->
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/js/vouch.js?v=2020415" type="text/javascript"></script>

    <!-- CSS dependencies -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/css/bootstrap.css"/>
    <!--link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/themes/classic.date.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/fontawesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.css"/-->
	
    <!-- Sensei Grid CSS -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.css"/>

	<script src="<%=path%>/lib/hrp/acc/superVouch/grid/lodash/lodash.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/pickadate/lib/picker.date.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/bootstrap/dist/js/bootstrap.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/summernote/dist/summernote.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/isInViewport/lib/isInViewport.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/typeahead.js/dist/typeahead.jquery.js" type="text/javascript"></script>
	
	<!-- Sensei Grid JS -->
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.js?v=20200221" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-editors.js" type="text/javascript"></script>
    <script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-row-actions.js" type="text/javascript"></script>


<script type="text/javascript">

$(function() {
	if(existsChromeVer42(44)){
		//谷歌43以下版本滚动条的宽度多1px
		$("#vouchDiv").css("padding-right","1px");
	}
	
	is_budg=$("#is_budg_val",parent.document).val();
//	$(':button').ligerButton({width:90});
	loadVouchTable();
	/*alert($(window).height()); //浏览器当前窗口可视区域高度 
	alert($(document).height()); //浏览器当前窗口文档的高度 
	alert($(document.body).height());//浏览器当前窗口文档body的高度 
	alert($(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin*/
//	$("#vouchDiv").css("height", $(window).height()-128-74);
//	$("#vouchDiv").css("width", $(window).width());
//	$("#vouchFootDiv").css("top",$("#vouchDiv").height());
//	$("#vouchFootDiv").css("width",$(window).width()-17);
	$("#vouchFootDiv",parent.document).show();
	if(is_budg==1){
		//分栏式
		$("#budg_subj_nameSum",parent.document).show();
		$("#budg_debit_nameSum",parent.document).show();
		$("#budg_credit_nameSum",parent.document).show();
	}else{
		//分屏式
		$("#budg_subj_nameSum",parent.document).hide();
		$("#budg_debit_nameSum",parent.document).hide();
		$("#budg_credit_nameSum",parent.document).hide();
	}
	if(parent.vouchFlow.length==0){
		alert("请检查凭证制单流程。");
		return;
	}
/*	if(parent.liger.get("vouch_type_code").getValue()==""){
		alert("没有凭证类型，请检查系统参数010。");
		return;
	}*/
	
	//$.fn.zTree.init($("#subjTree"), setting, zNodes);
	
 	//快捷键处理
	BindKeyBoard([
		{keyCode:112,fn:function(){
			//新建凭证F1
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.myNewVouch();
		}},
	    {keyCode:113,fn:function(){
	    	//删除分录F2
	    	parent.myDelDetail();
		}},
	    {keyCode:114,fn:function(){
	    	//复制凭证F3
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.copyVouch();
	    }},
	    {keyCode:115,fn:function(){
	    	//现金流量F4
	    	parent.myCash();
	    }},
	    {keyCode:116,fn:function(){
	    	//辅助核算F5
	    	parent.myCheck();
	    }},
	    {keyCode:117,fn:function(){
	    	//F6预算会计
	    	if(parent.copyNature=="02"){
	    		return;
	    	}
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.myOpenBudg('02');
	    }},
	    {keyCode:118,fn:function(){
	    	//F7差异标注
	    	if(parent.copyNature=="02"){
	    		return;
	    	}
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.myOpenDiff();
	    }},
	    {keyCode:81,alt:18,fn:function(){
	    	//Alt+Q上一步
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.updateVouchStateByFlow('pre');
	    }},
	    {keyCode:83,alt:18,fn:function(){
	    	//Alt+S下一步
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.updateVouchStateByFlow('next');
	    }},
	    {keyCode:119,fn:function(){
	    	//删除凭证F8
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.myDelVouch();
	    }},
	    {keyCode:120,fn:function(){
	    	//存草稿F9
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.mySave(-1,'false');
	    }},
	    {keyCode:121,fn:function(){
	    	//表格打印F10
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.mySave(1,'true',2);
	    }},
	  /*   {keyCode:80,alt:18,fn:function(){
	    //Alt+P
	    }}, */
	    {keyCode:122,fn:function(){
	    	//打印F11
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.mySave(1,'true',1);
	    }},
	    {keyCode:123,fn:function(){
	    	//保存F12
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	parent.mySave(1,'false');;
	    }},
	    {keyCode:27,fn:function(){
	    	parent.closeDialog();
	    }}
	]);
	
	
	//金额运算
 	evelMoney();
	//右键菜单
 	parent.initMenu();
	
	parent.layer.close(parent.vouchLoadIndex); 
	
	$("#vouchDiv").click(function(){
		$("#checkDiv",parent.document).hide();
		if(parent.columnCheckSel){
			for(var a in parent.columnCheckSel){
				if(parent.columnCheckSel[a])parent.columnCheckSel[a].selectBox.hide();
			}
		}
	});
	$("#topDiv",parent.document).click(function(){
		$("#checkDiv",parent.document).hide();
	});
	
});


function myDelDetail(){
	if(grid.getSelectedRows().length==0){
		return;
	}
	layer.confirm('您确定要删除吗？', {
		icon: 3, title:'提示',
		btn: ['确定','取消'] //按钮
		}, function(index){
			grid.removeActiveRow();
			layer.close(index);
		},function(){
			//20s后自动关闭
		}
	);
	
}

function gridEditorBlur(){
	//grid.editorBlur(grid.getActiveCell());//光标离开保存数据
	grid.saveEditor(true);//光标离开保存数据
	if (grid.isEditing) {
		grid.exitEditor(true);
	}
}


/*
function getScrollbarWidth() {
	 var noScroll, scroll, oDiv = document.createElement("DIV");
	  oDiv.style.cssText = "position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
	  noScroll = document.body.appendChild(oDiv).clientWidth;
	  oDiv.style.overflowY = "scroll";
	  scroll = oDiv.clientWidth;
	  document.body.removeChild(oDiv);
	  return noScroll-scroll;
}
*/

</script>

</head>

<body style="padding: 0px;overflow-x:hidden; overflow-y:scroll;background:#FFFFDF;" onBlur="gridEditorBlur();">
	<!-- border-style:solid; border-width:1px; -->
	<div id="vouchDiv" class="sensei-grid sensei-grid-default tablebody" style="width:100%;position:absolute;"></div>
	
	<!-- div id="subjContent" class="subjContent" style="display:none; position: absolute;background-color:#D0E9F8;z-index:100">
		<ul id="subjTree" class="ztree" style="margin-top:0;"></ul>
	</div-->
	
</body>
</html>
