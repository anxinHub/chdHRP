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
<!-- Sensei Grid CSS -->
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/hrp/acc/superVouch/grid/sensei-grid.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/jquery/smartMenu.css"/>
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery/jquery-smartMenu-min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script src="<%=path%>/lib/hrp/acc/superVouch/js/main.js?v=2020413" type="text/javascript"></script>
<link rel="stylesheet" href="<%=path%>/lib/et_components/etCheck/css/icheck.css">
<script src="<%=path%>/lib/et_components/etCheck/js/icheck.js"></script>
<script src="<%=path%>/lib/et_components/etCheck/etCheck.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etSelect.min.js"></script>
	
<!-- 避免浏览器缓存缓存加载 -->
<!-- script id="spread_print"></script>
<script>
	var scPrint = document.getElementById("spread_print");
	scPrint.src = "<%=path%>/lib/SpreadJS10/Spread.Sheets.All.10.1.0/scripts/pluggable/gc.spread.sheets.print.10.1.0.min.js?"+Math.random();
</script-->

<style>
        .info {
            padding:0px;
            position:relative;
            /*height: 500px;*/
            display: none;/*避免闪动初始规定不显示*/
            border-top:solid 5px #D0E9F8;
        }
       
       .checkpos{
       		position:absolute;display:none;width:400px;z-index:99;border:1px solid #B8CBCB;background-color:#fff;font-size:14px;
       		padding: 5px;margin:5px;/*line-height:10px;white-space:nowrap;*/
       }
</style>
<script type="text/javascript">

var frameObj;
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var paraList=${paraList};//凭证相关参数列表
var vouchJson={
	    "vouch_id": "",//凭证ID
	    "vouch_date": "",//凭证日期2016-08-20
	    "vouch_type_code": "",//凭证类型编码
	    "att_num": "0",//附件数量
	    "vouch_no": "",//凭证号
	    "state": "1",//凭证状态
	    "create_user": "",//制单人ID
	    "create_name": "",//制单人名称
	    "cash_user": "",//出纳签字人ID
	    "cash_name": "",//出纳签字人名称
	    "audit_user": "",//审核人ID
	    "audit_name": "",//审核人名称
	    "acc_user": "",//记账人ID
	    "acc_name": "",//记账人名称
	    "debit_sum": "",//借方合计，前台计算
	    "credit_sum": "",//贷方合计，前台计算
	    "budg_debit_sum": "",//借方合计，前台计算
	    "budg_credit_sum": "",//贷方合计，前台计算
	    "row_size": "",//表格行数量，前台计算
	    "busi_log_table": "",//生成凭证传参，日志表
	    "busi_type_code": "",//生成凭证传参，业务类型编码
	    "busi_no":"",//生成凭证传参，单据编码
	    "template_code":"",//生成凭证传参，模板编码
	    "auto_id":"",//自动凭证ID
	    "Rows": [{}]//凭证分录数组
	    };  
var vouchCheckJson={};//凭证辅助核算数组，{ "3": [ { "id": "1", "cid": "", "summary": "门诊收费", "subj_code": "63", "money": "9736465.43", "check1_name": "10370101 肿瘤内科1", "check1": "75@75" }
var vouchCashJson={};//凭证现金流量标注数组
var kindCheckJson=null;//财务&预算会计页面数据
var kindCashJson=null;//财务&预算会计页面数据
var diffJson=[];//差异标注
var openType='${openType}';
var vouchLoadIndex;
var isReadOnly=true;//保存是否允许编辑凭证
var ktLayerObj;
var vouchTypeData;
var subjDict=[];//会计科目下拉框json
var summaryDict=[];//摘要下拉框json
var checkDict={};//辅助核算下拉框json
var isParentQuery='${isParentQuery}';
var isOtherSys='${is_other_sys}';//因为账簿可以跨账套查询，如果该凭证的的医院或者账套不等于当前系统的，凭证不可以编辑
var is_budg_check;
var is_diff_check;
var copyNature='${copy_nature}';
var isOpenktLayer=true;

$(function() {
	
	//清除客户端快捷键缓存
	Local._remove("hrp[repeat[commit");
	vouchLoadIndex = layer.load(1);
	$(':button').ligerButton({width:70});
	//$("input[name='button60']").ligerButton({width:60});
    $("#vouchDateDiv").css("left",$(window).width()/2-95);
	$("#vouchFrame").css("height", $(window).height()-128-74-37);
 	$("#vouchFootDiv").css("top",$("#vouchFrame").height()+108);
	$("#mainHead").css("top",$("#vouchFrame").height()+110);
	$("#vouchCheckCashFrame").css("width",$(window).width());
	frameObj=document.getElementById("vouchFrame").contentWindow;
	loadDict();
	queryVouchFlow();//查询凭证审核流程
	
	//loadVouchTable();
	/*alert($(window).height()); //浏览器当前窗口可视区域高度 
	alert($(document).height()); //浏览器当前窗口文档的高度 
	alert($(document.body).height());//浏览器当前窗口文档body的高度 
	alert($(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin*/
	
	ktLayerObj=$("#mainHead").ktLayer({
        // 参数配置
        direction:"up",
        BtnbgImg:{close:'<%=path%>/lib/hrp/acc/superReport/open.png',open:'<%=path%>/lib/hrp/acc/superReport/close.png'},
        speed:"100",
        bgColor:"#FFFFFF",//背景颜色
        closeHeight:0,//关闭状态高度
        Descript:["辅助核算","辅助核算"],//展开收起描述
        bwidth:85,
   	 	open:function(){
       	 
        }
    });
	
	//快捷键处理
	BindKeyBoard([
		{keyCode:112,fn:function(){
			//新建凭证F1
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
			myNewVouch('main');
		}},
	    {keyCode:113,fn:function(){
	    	//删除分录F2
	    	myDelDetail();
		}},
	    {keyCode:114,fn:function(){
	    	//复制凭证F3
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	copyVouch();
	    }},
	    {keyCode:115,fn:function(){
	    	//现金流量F4
	    	myCash();
	    }},
	    {keyCode:116,fn:function(){
	    	//辅助核算F5
	    	myCheck();
	    }},
	    {keyCode:117,fn:function(){
	    	//F6预算会计
	    	if(copyNature=="02"){
	    		return;
	    	}
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	myOpenBudg('02');
	    }},
	    {keyCode:118,fn:function(){
	    	//F7差异标注
	    	if(copyNature=="02"){
	    		return;
	    	}
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	myOpenDiff();
	    }},
	    {keyCode:81,alt:18,fn:function(){
	    	//Alt+Q上一步
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	updateVouchStateByFlow('pre');
	    }},
	    {keyCode:83,alt:18,fn:function(){
	    	//Alt+S下一步
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	updateVouchStateByFlow('next');
	    }},
	    {keyCode:119,fn:function(){
	    	//删除凭证F8
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	myDelVouch();
	    }},
	    {keyCode:120,fn:function(){
	    	//存草稿F9
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	mySave(-1,'false');
	    }},
	    {keyCode:121,fn:function(){
	    	//表格打印F10
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	mySave(1,'true',2);
	    }},
	    {keyCode:122,fn:function(){
	    	//打印F11
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	mySave(1,'true',1);
	    }},
	    {keyCode:123,fn:function(){
	    	//保存F12
	    	if(Local.get("hrp[repeat[commit")){
	    		return;
	    	}
	    	mySave(1,'false');;
	    }},
	    {keyCode:27,fn:function(){
	    	closeDialog();
	    }}
	]);
	
	window.onload=function(){
		document.getElementById("vouchFrame").src="vouchPage.do?isCheck=false"; 
	};
	
	
});



function loadDict(vouchLoadIndex) {
	
	//是否按对照生成预算会计
	var isBudgCheck=Local.get("acc[vouch[is_budg_check");
	if(isBudgCheck==null || isBudgCheck=="true"){
		isBudgCheck=true;
	}else{
		isBudgCheck=false;
	}
	
	is_budg_check = $("#is_budg_check").etCheck({
		checked :isBudgCheck,
		ifChanged: function (status, checked, disabled) {
			Local.set("acc[vouch[is_budg_check",checked);
        }
	});
	
	//是否自动差异标注
	is_diff_check = $("#is_diff_check").etCheck({
		ifChanged: function (status, checked, disabled) {
        }
	});
	setDiffCheck(diffJson);
	
	if(openType=="autoVouch" && dialog.get("data")!=undefined){
		
		if(dialog.get("data").auto_id && dialog.get("data").auto_id!=""){
			
			//新的自动凭证处理方式
			vouchJson["auto_id"]=dialog.get("data").auto_id;
			vouchJson["busi_log_table"]=dialog.get("data").busi_log_table;
			//期末处理、工资转账自动凭证，在生成凭证时不需要提前插入自动凭证日志表acc_busi_log_temp，所以需要传busi_no
			if(dialog.get("data").busi_no){
				vouchJson["busi_no"]=dialog.get("data").busi_no;
			}
			
			/* ajaxJsonObjectBylayer("queryAutoVouch.do?isCheck=false",{auto_id:dialog.get("data").auto_id}, function(responseData) {
				
				myGetTemplateVouchJson(vouchLoadIndex,responseData,"","");
				
				vouchJson["busi_log_table"]=dialog.get("data").busi_log_table;
				alert(vouchJson["busi_log_table"])
				//期末处理、工资转账自动凭证，在生成凭证时不需要提前插入自动凭证日志表acc_busi_log_temp
				if(dialog.get("data").busi_no){
					vouchJson["busi_no"]=dialog.get("data").busi_no;
				}
			},layer,vouchLoadIndex); */
			
		}else{
			
			//老的自动凭证处理方式
			vouchJson=dialog.get("data").vouch;
			$("#createUserId").val(vouchJson.create_user);
			$("#vouchId").val(vouchJson.vouch_id);
			$("#state").val(vouchJson.state);
			$("#file_num").val(vouchJson.att_num);
			$("#create_name").text("制单："+vouchJson.create_name);
			//辅助核算
			if(vouchJson.check){
				vouchCheckJson=vouchJson.check;	
			}
			//现金流量
			if(vouchJson.cash){
				vouchCashJson= vouchJson.cash;
			}
			vouchJson["busi_log_table"]=dialog.get("data").busi_log_table;
			vouchJson["busi_type_code"]=dialog.get("data").busi_type_code;
			vouchJson["busi_no"]=dialog.get("data").busi_no;
			vouchJson["template_code"]=dialog.get("data").template_code;
		}
		
		 
	}else{
		
		//新建凭证,修改凭证
		vouchJson["vouch_type_code"]="${superVouchMain.vouch_type_code}";//记录凭证类型初始值，值改变需要变更凭证号
		vouchJson["vouch_date"]="${superVouchMain.vouch_date}";//记录凭证日期初始值，值改变需要变更凭证号
		vouchJson["vouch_no"]="${superVouchMain.vouch_no}";//记录初始凭证号
		vouchJson["busi_type_code"]='${superVouchMain.busi_type_code}';//业务类型编码
		$("#createUserId").val("${superVouchMain.create_user}");
		$("#vouchId").val("${superVouchMain.vouch_id}");
		$("#state").val("${superVouchMain.state}");
		$("#file_num").val("${superVouchMain.vouch_att_num}");
		$("#create_name").text("制单：${superVouchMain.create_name}");
		$("#cash_name").text("出纳：${superVouchMain.cash_name}");
		$("#audit_name").text("审核：${superVouchMain.audit_name}");
		$("#acc_name").text("记账：${superVouchMain.acc_name}");
		
	}
	
	$("#vouch_date").val(vouchJson["vouch_date"]);
	$("#vouch_no").val(vouchJson["vouch_no"]);
	
	var initIsBudg=Local.get("acc[vouch[is_budg");
	if(!initIsBudg){
		//取客户端的缓存
		initIsBudg=1;
	}
	if(copyNature=="02"){
		initIsBudg=2;
	}

	$("#is_budg").ligerComboBox({
		data:[{ id: 1, text: '分栏式'},{ id: 2, text: '分屏式' }],
		valueField: 'id',
     	textField: 'text', 
	    cancelable: false,
	    autocomplete: false,
	    width: 85,
	    value: initIsBudg,
	    onBeforeSelect: function (newvalue){
	    	if(newvalue==liger.get("is_budg").getValue()){
        		return false;
        	}
	    	if(vouchJson["row_size"]!=frameObj.grid.getRows().length || vouchJson["debit_sum"]!=$("#debit_nameSum").find("div").text() || vouchJson["credit_sum"]!=$("#credit_nameSum").find("div").text()
	    			|| vouchJson["budg_debit_sum"]!=$("#budg_debit_nameSum").find("div").text() || vouchJson["budg_credit_sum"]!=$("#budg_credit_nameSum").find("div").text()){
        		if(!confirm("数据改变，是否切换？")){
        			return false;
        		}
        	}
	    },onSelected: function (selectValue){
        	layer.closeAll();
        	
        	//清空客户端缓存
        	vouchJson.Rows=[];
        	vouchCheckJson={};
        	vouchCashJson={};
        	vouchJson["busi_type_code"]="";
        	vouchJson["template_code"]="";
        	vouchJson["template_name"]="";
        	vouchJson["template_note"]="";
        	vouchJson["auto_id"]="";
        	vouchJson["busi_no"]="";
        	vouchJson["busi_log_table"]="";
        	
        	ktLayerObj.fn.close();
        	$("#vouchFootDiv",document).hide();
        	frameObj.location.reload(true);
        	Local.set("acc[vouch[is_budg",selectValue);
    	}
        
	});
	
	//根据系统参数010取凭证类型
	$("#vouch_type_code").ligerComboBox({
	 	parms : {p010:paraList["010"]},
    	url: 'querySuperVouchTypeByPerm.do?isCheck=false',
    	valueField: 'id',
     	textField: 'text', 
     	selectBoxWidth: 120,
    	autocomplete: false,
    	width: 120,
    	cancelable: false,
    	async:false,
    	onSuccess  :function (data){
    		vouchTypeData=data;
    		var isPerm=false;
    		if(data!=null && data.length>0){
    			if(vouchJson["vouch_type_code"]==""){
    				isPerm=true;
    				liger.get("vouch_type_code").setValue(data[0].id);
        			liger.get("vouch_type_code").setText(data[0].text);
    			}else{
    				for(var i=0;i<data.length;i++){
        				if(data[i].id==vouchJson["vouch_type_code"]){
        					isPerm=true;//修改检查是否有凭证类型的权限
        					liger.get("vouch_type_code").setValue(data[i].id);
                			liger.get("vouch_type_code").setText(data[i].text);
                			break;
        				}
        			}	
    			}
    			
    		}
    		
    		if(!isPerm){
    			alert("没有凭证类型权限")
    			$("#vouchId").val("");
    			return;
    		}
    		
    		/*同步加载，不需要在这里处理了
    		var frameObj=document.getElementById("vouchFrame").contentWindow;
    		if(frameObj.grid!=undefined && !frameObj.grid.config["readonly"]){
    	    	//第一行摘要获取焦点
    	        var $row = frameObj.grid.getRowByIndex(0);
    	        //$($row[0].cells[3]).focus();
    	        frameObj.grid.setActiveCell($($row[0].cells[3]));
    	        frameObj.grid.$el.focus();
    	        frameObj.grid.exitEditor(true);
    	    }*/
    		
	    },onSelected: function (selectValue){
	    	
	 		if(openType!="autoVouch" && $("#vouchId").val()=="" && selectValue!="" && $("#vouch_date").val()!=""){
				getMaxVouchNo({"p001":paraList["001"],"vouch_type_code":selectValue,"vouch_date":$("#vouch_date").val()});
			}
		}
	});
	/*$("#vouch_type_code").change(function(){
		 if(this.value!="" && $("#vouch_date").val()!=""){
		 	getMaxVouchNo({"p001":paraList["001"],"vouch_type_code":liger.get("vouch_type_code").getValue(),"vouch_date":$("#vouch_date").val()});
		 }
	});*/
	
	
	//医院企业会计制度
	if(copyNature=="02"){
		$("td[name=accBudgTd]").css('visibility','hidden');
		$("input[name=accBudgButton]").css('display','none');	
		is_diff_check.setUncheck(false);
		is_budg_check.setUncheck(false);
	}
}

function setVouchTypeValue(code){
	liger.get("vouch_type_code").setValue(code);
}

</script>


<style type="text/css">
.l-page-top {height: 128px;text-align:center;}

</style>
</head>

<body style="padding: 0px; overflow: hidden;" >
	<input type="hidden" id="vouchId"></input>
	<input type="hidden" id="createUserId" ></input>
	<input type="hidden" id="state" ></input>
	<!--textarea id="test" rows="5" cols="100"></textarea-->
	<div id="topDiv">
	<div id="stateDiv" style="display:none;position:absolute;top:44px;left:4px;border:solid 1px red;color:red;font-size:15px;height:25px;vertical-align: middle;text-align:center;"></div>
	
	<div class="l-page-top" style="padding:0px 5px;">
		<!--div id="topmenu"></div-->
		<table  cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="height:40px;border-bottom:solid 1px #333333;">
				<tr>
					<td align="left">
						<input type="button" value="新建凭证 F1" onclick="myNewVouch('main');"/>
						<input type="button" name="controllerButton" value="删除分录 F2" onclick="myDelDetail();"/>
						<input type="button" id="copyButton" value="复制凭证 F3" onclick="copyVouch();"/>
						<input type="button" value="现金流量 F4" onclick="myCash();"/>
						<input type="button" value="辅助核算 F5" onclick="myCheck();"/>
						<input type="button" value="财务&预算F6" name="accBudgButton" onclick="myOpenBudg('02');" title="财务&预算会计分开显示"/>
						<input type="button" value="差异标注F7" name="accBudgButton" onclick="myOpenDiff();"/>
						<input id="preFlowButton" style="display:none" name="flowButton" type="button" onclick="updateVouchStateByFlow('pre');"/>
						<input id="nextFlowButton" style="display:none" name="flowButton" type="button"  onclick="updateVouchStateByFlow('next');"/>
					</td>
					<td align="right">
						<input type="button" name="controllerButton" value="删除凭证 F8" onclick="myDelVouch();"/>
						<input type="button" name="controllerButton" value="存草稿 F9" onclick="mySave(-1,'false');"/>
						<input type="button" id="saveAndPrintButton" value="表格打印F10" onclick="mySave(1,'true',2);"/>
						<input type="button" id="saveAndPrintButton" value=" 打印 F11" onclick="mySave(1,'true',1);"/>
						<input type="button" id="saveVouchButton" name="controllerButton" value=" 保存 F12" onclick="mySave(1,'false');"/>
						<input type="button" value=" 关闭 ESC" onclick="closeDialog();"/>
					</td>
				</tr>
		</table>
		
		<h1><u><b>记   账   凭   证</b></u></h1>
		<div style="position:absolute;top:50px;right:4px;font-size:15px">
			<a href="javascript:myVouchJump('P');">上一张</a>
			&nbsp;<a href="javascript:myVouchJump('N');">下一张</a>
			&nbsp;<input name="vouch_no_q" style="width:57px" type="text"  id="vouch_no_q"  ltype="text" title="输入凭证号按回车键查询" onkeypress="if(event.keyCode==13 && $('#vouch_no_q').val()!='') {myVouchJump();}"/>
			&nbsp;<a href="javascript:if($('#vouch_no_q').val()!=''){myVouchJump();}">跳转</a>&nbsp;
		</div>
		
		<div id="vouchDateDiv"  style="position:absolute;top:78px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0" style="font-size:15px">
			<td align="right" class="l-table-edit-td" width="60px">
				日期：
			</td>
			<td align="left" class="l-table-edit-td" width="105px">
				<input class="Wdate" name="vouch_date"  style="width:105px" type="text"  id="vouch_date"  ltype="text"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'${minDate}',onpicking:function(dp){vouchDtateFunc(dp);},Mchanging:cMonthFunc,ychanging:cYearFunc})"/>
			</td>
			</table>
		</div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0" style="font-size:15px">
			<tr>
				<td align="left" class="l-table-edit-td" width="120px"><input name="vouch_type_code"  type="text"  id="vouch_type_code"  ltype="text" /></td>
				<!-- td align="right" class="l-table-edit-td" width="65px">凭证号：</td-->
				<td align="left" class="l-table-edit-td" width="60px"><input name="vouch_no" style="width:60px" type="text"  id="vouch_no" ltype="text" onkeypress="if(event.keyCode==13) {summaryOnFocus();}"/></td>
				<td align="left" class="l-table-edit-td" width="80px" name="accBudgTd"><input type="text"  id="is_budg"  ltype="text" /></td>
				<td align="right" class="l-table-edit-td" name="accBudgTd">
					<input type="checkbox" id="is_diff_check"/>
					<label for="is_diff_check">自动差异标注</label>
					<input type="checkbox" id="is_budg_check"/>
					<label for="is_budg_check">按对照生成</label>
				</td>
				<td align="right" class="l-table-edit-td" width="60px">
					<input name="file_num"  type="text"  style="width:60px" id="file_num"  ltype="text" onkeypress="if(event.keyCode==13) {summaryOnFocus();}"/>
				</td>
				<td align="right" width="38px"><a href="javascript: myUpload();">附件</a>&nbsp;</td> 
				
			</tr>
		</table>
	</div>
	</div>
	
	<div id="menuDiv">			
		<iframe id="vouchFrame" name="vouchFrame" width="100%" frameborder="no" src="" style="position:absolute;top:115px"></iframe>
		<div id="vouchFootDiv" style="position:absolute;left:0;font-size:14px;display:none;padding-right:1px;vertical-align: middle;">
			<table width="100%">
				<tr style="background:#FFFFDF;">
					<td id="subj_nameSum" colspan="2" style="height:31px;border:1px solid #DDDDDD;">
					<div style="float:left;vertical-align: middle;">合计：</div>
					<div style="float:left;text-align:left; vertical-align: top;border:solid 1px red;color:red">
						<span id="jdbpSpan" style="cursor:pointer"></span>
					</div>
					<div style="float:right;vertical-align: middle;" id="capital"></div>
					</td>
					<td id="debit_nameSum" style="border:1px solid #DDDDDD;border-right:2px solid #333333;"><div></div></td>
					<td id="credit_nameSum" style="border:1px solid #DDDDDD;"><div></div></td>
					<td id="budg_subj_nameSum" style="border:1px solid #DDDDDD;text-align: left; vertical-align: middle;">
						<div style="float:left;text-align:left; vertical-align: top;border:solid 1px red;color:red;">
							<span id="budg_jdbpSpan" style="cursor:pointer"></span>
						</div>
						<div style="float:right;vertical-align: middle;" id="budg_capital"></div>
					</td>
					<td id="budg_debit_nameSum" style="border:1px solid #DDDDDD;border-right:2px solid #333333;"><div></div></td>
					<td id="budg_credit_nameSum" style="border:1px solid #DDDDDD;"><div></div></td>
				</tr>
			</table>
			<table width="100%">		
				<tr>
					<td id="create_name" style="width:25%;vertical-align: middle;border-right-style:none;border-left-style:none;border-bottom-style:none;">制单：</td>
					<td id="cash_name" style="width:25%;vertical-align: middle;border-right-style:none;border-left-style:none;border-bottom-style:none;">出纳：<div id="cashUser"></div></td>
					<td id="audit_name" style="width:25%;vertical-align: middle;height:37px;border-right-style:none;border-left-style:none;border-bottom-style:none;">审核：<div id="checkUser"></div></td>
					<td id="acc_name" style="width:25%;vertical-align: middle;border-right-style:none;border-left-style:none;border-bottom-style:none;">记账：<div id="accountUser"></div></td>
				</tr>
				<tr>
					<td style="text-align: left; vertical-align: middle;border-right-style:none;border-left-style:none;border-bottom-style:none;" colspan="4">
						<div  style="float:left;text-align:left; vertical-align: top;border-right-style:none;border-left-style:none;border-bottom-style:none;">
						<span id="subjActiveCell"></span>
						<span id="subjBalanceDIv"></span>
						<span id="subjEndOsDIv" style="font-weight:bold"></span>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="checkDiv" class="checkpos" tabindex="0">
		<table id="checkTable" style="border-collapse:separate; border-spacing:0px 10px;">
		</table>
	</div>
	
	<div id="mainHead" style="height:0px;position:absolute;z-index:4;">
		<div class="info">
			<iframe id="vouchCheckCashFrame" src="" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="yes"></iframe>
		</div>
	</div>
	
	
	<div id="target1" style="margin:3px; display:none;">
		<div style="font-size:15px">
		   	<b>功能快捷键：</b><br/>
			●space：非编辑状态，勾选复选框/取消复选框<br/>	
			●ctrl + d or shift + d：向下复制一行，带摘要科目。<br/>
			●ctrl + q or shift + q：向下插入空行，不带摘要科目。<br/>
			●tab：移入下一个单元格，进入编辑状态时不触发该单元格弹出窗口事件。<br/>
			●shift + tab：移入上一个单元格，进入编辑状态时不触发该单元格弹出窗口事件。<br/>
			●shift + up：勾选上一行复选框。<br/>
			●shift + down：勾选下一行复选框。<br/>
			<br/>
			<b>编辑快捷键：</b><br/>
			●space：编辑状态，科目列：弹出科目选择框，借贷金额列：切换到对方。<br/>
			●=：借贷金额列：取借贷平衡。<br/>	
			<!-- ●ctrl：退出编辑状态。<br/> -->
			●ctrl + enter：移入下一行单元格，进入编辑状态时不触发该单元格弹出窗口事件。<br/>
			●ctrl + shift + enter：移入上一行单元格，进入编辑状态时不触发该单元格弹出窗口事件。<br/>
			●left, right, up, down：退出编辑状态，移动单元格。<br/>
			●enter：进入编辑状态 or 进入下一单元格编辑状态,触发该单元格弹出窗口事件。
	    </div>
 	</div>
	
</body>
</html>
