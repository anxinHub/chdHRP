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
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/jquery/smartMenu.css"/>
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery/jquery-smartMenu-min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/js/vouchKind.js" type="text/javascript"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etSelect.min.js"></script>

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
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-editors.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-row-actions.js" type="text/javascript"></script>
    
<style type="">
	.checkpos{
    		position:absolute;display:none;width:400px;z-index:99;border:1px solid #B8CBCB;background-color:#fff;font-size:14px;
    		padding: 5px;margin:5px;/*line-height:10px;white-space:nowrap;*/
     }
</style>

<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var initRowSize=0;
$(function() {
	if(existsChromeVer42(44)){
		//谷歌43以下版本滚动条的宽度多1px
		$("#vouchDiv").css("padding-right","1px");
	}
	//复制一份辅助核算和现金流量数据，点击确定的时候更新过去
	parent.kindCheckJson=JSON.parse(JSON.stringify(parent.vouchCheckJson));
	parent.kindCashJson=JSON.parse(JSON.stringify(parent.vouchCashJson));
	
	$("#vouchDiv").css("height",$(window).height()-35-31);
	$(':button').ligerButton({width:70});
	loadDict();
	kindCode=liger.get("kind_code").getValue();
	loadVouchTable();
	$("#vouchFootDiv").css("top",$("#vouchDiv").height()+35);
	$("#vouchFootDiv").css("width",$(window).width());//div宽度 
	$("#debit_nameSum").css("width",moenyWith);
	$("#credit_nameSum").css("width",moenyWith);
	
 	//快捷键处理
	BindKeyBoard([
		{keyCode:113,fn:function(){
			//删除分录F2
			myDelDetail();
		}},
		{keyCode:114,fn:function(){changeDire()}},//切换F3
        {keyCode:115,fn:function(){
 	    	//现金流量F4
 	    	myCash();
   	    }},
   	    {keyCode:116,fn:function(){
   	    	//辅助核算F5
   	    	myCheck();
   	    }},
	    {keyCode:123,fn:function(){
	    	mySave();
	    }}, 
	    {keyCode:27,fn:function(){
	    	myClose();
	    }}
	]);
	
	//金额运算
 	evelMoney();
	//右键菜单
 	initMenu();
	
 	$("#vouchDiv").click(function(){
		$("#checkDiv").hide();
	});
	$("#topDiv").click(function(){
		$("#checkDiv").hide();
	});
});


function myClose() {
	if(loadIndex!=0){
		return;
	} 
	
	var frameGrid=parent.frameObj.grid;
	var debit_nameSum=$("#debit_nameSum",parent.document).find("div").text();
	var credit_nameSum=$("#credit_nameSum",parent.document).find("div").text();
	if(kindCode=="02"){
		debit_nameSum=$("#budg_debit_nameSum",parent.document).find("div").text();
		credit_nameSum=$("#budg_credit_nameSum",parent.document).find("div").text();
	}
	
 	if(initRowSize!=grid.getRows().length || debit_nameSum!=$("#debit_nameSum").find("div").text() 
 			|| credit_nameSum!=$("#credit_nameSum").find("div").text()){
		if(!confirm("是否关闭？")){
			return;
		}
	} 
	
	parent.kindCheckJson=null;
	parent.kindCashJson=null;
 	setTimeout(function() {
		dialog.close();
	}, 50); 
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

<body style="padding: 0px;overflow-x:hidden; overflow-y:scroll;background:#FFFFDF;">
	
	<div id="topDiv" style="margin:0;position:fixed;z-index:3;top:0px;width:100%;background:white;padding:0px 5px;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="height:35px;">
			<tr>
				<td align="left" class="l-table-edit-td" width="80px"><input type="text"  id="kind_code"  ltype="text" title="F3快速切换"/></td>
				<td align="left">
					<input type="button" name="controllerButton" value="删除分录 F2" onclick="myDelDetail();"/>
					<input type="button" value="现金流量 F4" onclick="myCash();"/>
					<input type="button" value="辅助核算 F5" onclick="myCheck();"/>
				</td>
				<td align="right">
					<input type="button" value=" 关闭 ESC" onclick="myClose();"/>
					<input type="button" name="controllerButton" value=" 确定 F12" onclick="mySave();"/>
				</td>
			</tr>
		</table>
	</div>

	<div id="vouchDiv" class="sensei-grid sensei-grid-default tablebody" style="width:100%;position:absolute;top:35px;"></div>
	<div id="vouchFootDiv" style="position:fixed;z-index:3;left:0;padding-right:0px;vertical-align: middle;">
		<table width="100%">
			<tr style="background:#FFFFDF;font-size:14px;">
				<td id="subj_nameSum" style="height:31px;border:1px solid #DDDDDD;">
					<div style="float:left;vertical-align: middle;">合计：</div>
					<div style="float:left;text-align:left; vertical-align: top;border:solid 1px red;color:red">
						<span id="jdbpSpan" style="cursor:pointer"></span>
					</div>
					<div style="float:right;vertical-align: middle;" id="capital"></div>
				</td>
				<td id="debit_nameSum" style="border:1px solid #DDDDDD;border-right:2px solid #333333;"><div></div></td>
				<td id="credit_nameSum" style="border:1px solid #DDDDDD;"><div></div></td>
			</tr>
		</table>
	</div>
	
	<div id="checkDiv" class="checkpos">
		<table id="checkTable" style="border-collapse:separate; border-spacing:0px 10px;">
		</table>
	</div>
	
</body>
</html>
