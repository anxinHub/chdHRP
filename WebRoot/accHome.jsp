<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    //out.print(path);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
<script src="<%=path%>/indexdata.js"></script>

<style type="text/css">
*{
	margin:0;
    padding:0;
}
html,body{
	width: 100%;
    height: 100%;
    font-size: 100%;
    min-width: 1000px;
}
.visibility{
	visibility: hidden;
}
#mainContent {
    padding: 40px 30px 0;
}

#mainContent .addBackground {
	padding: 4px;
    margin-bottom: 30px;
    /*width: 60%;
    background: #789cce;
    border: 2px solid #b8d1f3; */
}

#mainContent .addWidth {
  	height: 34px;
    display: inline-flex;
    justify-content: center;
    align-items: Center;
}

#mainContent .arrowRightWidth {
  	width: 6.667%;
}

#mainContent div{
	width: 100%;
	padding-bottom: 40px;
}

.mainText{
    width: 11%;
    padding: 5px;
    text-align: center;
    vertical-align: middle;
    border: 2px solid #b8d1f3;
    border-radius: 10px;
    display: inline-block;
    font-size: 13px;
    cursor: pointer;
    background: #fff;
}

.mainline {
	/* width: 16.66%; */
    border-radius: 0;
    /* background: #78abf1; */
}

.arrowRight,.arrowLeft,.vlineUp,.vlineUp1,.vlineDown,.vlineDown1{                        
    width: 6%;
    height: 10px;
    display: inline-block;
    position: relative;             
}

.arrowLongRight{
   	width: 18%;
    height: 10px;
    display: inline-block;
    position: relative;
}
.arrowUp,.arrowDown,.vline{
	position: relative;
	z-index: -1;
}

.arrowRight:before, .arrowRight:after, 
.arrowLeft:before, .arrowLeft:after, 
.arrowLongRight:before, .arrowLongRight:after, 
.arrowUp:before, .arrowUp:after, 
.arrowDown:before, .arrowDown:after,
.vline:before, .vline:after,
.vlineUp:before, .vlineUp:after,
.vlineUp1:before, .vlineUp1:after,
.vlineDown:before, .vlineDown:after,
.vlineDown1:before, .vlineDown1:after{
    content: '';
	border-color:transparent;    
	border-style: solid;
	position: absolute;               
}

.arrowRight:after,.arrowLeft:after,.arrowLongRight:after{                  
    width: 106%;
    border-width: 1px;
    height: 0;
    background-color: #555;
    top: 5px;
    left: -5px;
    z-index: -1;
}

.arrowRight:before,.arrowLongRight:before{                
    border-left-color: #555;
    border-width: 6px;
    left: 96%;
    top: 0px;
    z-index: -1;
}

.arrowLeft:after{                  
    width: 119%;
}

.arrowLeft:before{  
 	border-right-color: #555;   
 	border-width: 6px;           
    left: -11px;
    top: 0px;
    z-index: -1;
}

.arrowLongRight:after{                  
    width: 97%;
    top: 5px;
    left: 0;
    z-index: -1;
}

.arrowLongRight:before{                
    border-left-color: #555;
    border-width: 6px;
    right: -2px;
    top: 0px;
    z-index: -1;
}

.arrowUp:after{
    left: -9px;
    top: -46px;
    border-width: 6px;
    border-bottom-color: #555;
    z-index: -1;
}

.arrowDown:before{
    border: none;
    background: #555;
    height: 280%;
    width: 2px;
    top: -40px;
    left: -4px;
    z-index: -1;
}

.arrowDown:after{
    left: -9px;
    top: 4px;
    border-width: 6px;
    border-top-color: #555;
    z-index: -1;
}

.vline:before{
    border: none;
    background: #555;
    height: 81px;
    width: 2px;
    top: -55px;
    left: -40px;
    z-index: -1;
}

.vlineUp:after{
    width: 48%;
    border-width: 1px;
    height: 0;
    background-color: #555;
    top: -54px;
    left: -15%;
    z-index: -1;
}

.vlineUp1:after {
	  width: 14%;
    border-width: 1px;
    height: 0;
    background-color: #555;
    top: -2px;
    left: -15px;
    z-index: -1;
}

.vlineDown:after{
   	width: 80px;
    border-width: 1px;
    height: 0;
    background-color: #555;
    top: 19px;
    left: -120px;
    z-index: -1;
}

.vlineDown1:after{
    width: 86%;
    border-width: 1px;
    height: 0;
    background-color: #555;
    top: 19px;
    left: -40px;
    z-index: -1;
}

.menu{
    position: absolute;
    list-style: none;
    top: -1px;
    left: 100%;
    border: 1px solid #000;
    font-size: 13px;
    display: none;
    text-align: center;
    background: #fff;
    z-index: 10;
}

ul {
    list-style: none;
    float: left;
    display: none;
}
li {
    width: 100px;
    padding: 5px;
    border-bottom: 1px solid #d4cece;
    position: relative;
    cursor: pointer;
}
li span {
    position: absolute;
    top: 5px;
    right: 10px;
}
li:hover {
    background: #e2eefe;
    transition: background-color .5s;
}
.relative {
    position:relative;
    top: 0;
    left: 0;
}
.absolute {
    position: absolute;
    left: 100%;
    top: -1px;
    border: 1px solid #000;
    background: #fff;
    z-index: 10;
}
.show {
    display: block;
}
.hide {
    display: none;
}

</style>
<script>
	$(function(){
		$("ul li:last-child").css("border-bottom","0px");
		$(".mainText").hover(function(){
			if($(this).children.length > 0) {
				$(this).children('.menu').show()
			}
		},function(){
			$(this).children('.menu').hide()
		});
		
		/* 展示对应的子节点 */
		var uls = document.querySelectorAll("ul"); 
		var lis = document.querySelectorAll("li"); 
		var liWidth = document.querySelector(".mainText ul").offsetWidth-2;

		for (var i = uls.length - 1; i >= 0; i--) {
		    if(uls[i].parentNode.nodeName === "LI") {
		        uls[i].parentNode.classList.add("relative"); 
		        uls[i].classList.add("absolute");  
		    }
		};
		
		for (var i = 0; i < lis.length; i++) {
		    if( lis[i].children.length === 1) { 
		        lis[i].children[0].outerHTML = "";
		    };
		};
		
		$.each(lis,function(){
			$(this).hover(function(){
				if( $(this).children.length === 2) {
					$(this).children('ul').removeClass("hide");
					$(this).children('ul').addClass("show");
		        } 
			},function(){
				if($(this).children.length === 2) {
					$(this).children('ul').removeClass("show");
					$(this).children('ul').addClass("hide");
		        }
			});
		});

		$(".newOldJoin").click(newOldJoin);
	})
	
	/* 跳转页面的方法 */
	var clickSpan = function(event){
		var url = event.target.id;
		var tabid = parent.$("#accordion1").find("ul li[url$='"+url+"']").attr("tabid");
		
        if (!tabid) {
            tabid = new Date().getTime();
            parent.$("li[url$='"+url+"']").attr("tabid", tabid);
        }
        parent.f_addTab(tabid, event.target.innerText, url);
	};	
	
	/* 添加页面的方法 */
	var openSpan = function(event,title){
		var url = event.target.id;
		parent.openFullDialog(url,title,0,0,true,true);
	}
	
	// 打开新旧制度衔接
	function newOldJoin(){
		parent.openDialog({
			url : 'hrp/acc/join/accNewOldJoinMainPage.do',
			data : {},
			width : '0',
			height : '0',
			title : '新旧制度衔接',
			isModal : true,
			isShowMin : true,
			isResize : true,
			showClose : true,
			parentframename:window.name
		});
	}
</script>

</head>

<body>
	<div id="mainContent">

		<div>
			<span class="mainText" id="hrp/acc/accsubj/accSubjMainPage.do" onClick="clickSpan(event)">会计科目</span>
			<span class="arrowRight"></span>
			<span class="mainText" id="hrp/acc/accvouch/superVouch/superVouchMainPage.do" onClick="openSpan(event,'会计凭证')">凭证录入</span>
			<span class="arrowRight"></span>
			<span class="mainText" id="hrp/acc/accvouch/cashier/accVouchCashierMainPage.do" onClick="clickSpan(event)">出纳签字</span>
			<span class="arrowLeft" style="width:0"></span>
			<span class="arrowRight"></span>
			<span class="mainText relative">账簿查询
				<ul class="menu">
		            <li id="hrp/acc/accsubjledger/accSubjLedgerMainPage.do" onClick="clickSpan(event)">科目总账(按月)<span>></span></li>
		            <li id="hrp/acc/accsubjledger/accSubjLedgerDetailMainPage.do" onClick="clickSpan(event)">科目总账(按天)<span>></span></li>
		            <li id="hrp/acc/accsubjledger/accThreeColumnLedgerDetailMainPage.do" onClick="clickSpan(event)">三栏明细账<span>></span></li>
		            <li id="hrp/acc/accsubjledger/accSubjLedgerSumMainPage.do" onClick="clickSpan(event)">科目汇总表<span>></span></li>
		            <li id="hrp/acc/accsubjledger/accBalanceLedgerDetailMainPage.do" onClick="clickSpan(event)">科目余额表<span>></span></li>
		            <li id="hrp/acc/accsubjledger/accColumnsLedgerDetailMainPage.do" onClick="clickSpan(event)">多栏明细账<span>></span></li>
		        </ul>
			</span>
		</div>
		
		<div style="padding:0;height:10px">
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="vline"></span>
			<span class="vlineDown"></span>
			<span class="arrowUp"></span>
			<span class="arrowDown"></span>
			<span class="mainText visibility"></span>
			<span class="vline"></span>
			<span class="vlineDown1"></span>
		</div>
		
		<div>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText relative">自动凭证
				<ul class="menu">
					<li>HIS收费<span>></span>
		                <ul>
		                    <li id="hrp/acc/autovouch/his/accpre/hisAccPreMainPage.do" onClick="clickSpan(event)">预交金汇总表<span>></span></li>
		                    <li id="hrp/acc/autovouch/his/accpre/hisAccPreDetailMainPage.do" onClick="clickSpan(event)">预交金明细表<span>></span></li>
		                    <li id="hrp/acc/autovouch/his/charge/hisChargeOMainPage.do" onClick="clickSpan(event)">门诊收费<span>></span></li>
		                    <li id="hrp/acc/autovouch/his/charge/hisChargeIMainPage.do" onClick="clickSpan(event)">住院收费<span>></span></li>
		                    <li id="hrp/acc/autovouch/his/charge/hisBalIMainPage.do" onClick="clickSpan(event)">住院结算<span>></span></li>
		                    <li id="hrp/acc/autovouch/his/baldetail/accBalDetailIMainPage.do" onClick="clickSpan(event)">住院结算明细清单<span>></span></li>
		                </ul>
		            </li>
		            <li id="hrp/acc/autovouch/matautovouch/accMatAutoVouchMainPage.do" onClick="clickSpan(event)">物流管理凭证<span>></span></li>
		            <li id="hrp/acc/autovouch/medautovouch/accMedAutoVouchMainPage.do" onClick="clickSpan(event)">药品管理凭证<span>></span></li>
		            <li id="hrp/acc/autovouch/budgautovouch/accBudgAutoVouchMainPage.do" onClick="clickSpan(event)">借款报销凭证<span>></span></li>
		            <li id="hrp/acc/autovouch/assautovouch/accAssAutoVouchMainPage.do" onClick="clickSpan(event)">固定资产凭证<span>></span></li>
		            <li id="hrp/acc/autovouch/maintain/accAutoVouchMaintainMainPage.do" onClick="clickSpan(event)">自动凭证维护<span>></span></li>
		        </ul>
			</span>
			<span class="arrowRight visibility"></span>
			<span class="mainText"  id="hrp/acc/accvouch/audit/accVouchAuditMainPage.do" onClick="clickSpan(event)">凭证审核</span>
			<span class="arrowLeft visibility" style="width:0"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText relative" id="stateMents">财务报表
				<ul class="menu">
		            <li id="hrp/acc/superReport/make/makeMainPage.do" onClick="clickSpan(event)">报表制作<span>></span></li>
		            <li id="hrp/acc/superReport/query/queryMainPage.do" onClick="clickSpan(event)">报表查询<span>></span></li>
                   	<li id="hrp/acc/acctarget/accTargetMainPage.do" onClick="clickSpan(event)">基本指标<span>></span></li>
                   	<li id="hrp/acc/acctargetdata/accTargetDataMainPage.do" onClick="clickSpan(event)">基本数字<span>></span></li>
		               
		        </ul>
			</span>
		</div>
		
		<div style="padding:0;height:10px">
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText visibility"></span>
			<span class="vlineUp visibility"></span>
			<span class="vline visibility"></span>
			<span class="vlineDown visibility"></span>
			<span class="arrowUp"></span>
			<span class="arrowDown"></span>
			<span class="mainText visibility"></span>
			<span class="vline visibility"></span>
			<span class="vlineDown1 visibility"></span>
		</div>
		
		<div>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText relative">往来管理
				<ul class="menu">
					<li id="hrp/acc/accnostro/balanceMainPage.do" onClick="clickSpan(event)">余额查询<span>></span></li>
					<li id="hrp/acc/accnostro/detailAccountMainPage.do" onClick="clickSpan(event)">明细账查询<span>></span></li>
					<li id="hrp/acc/accnostro/inventoryVerificationMainPage.do" onClick="clickSpan(event)">核销清册<span>></span></li>
					<li id="hrp/acc/accnostro/pContactsReminderMainPage.do" onClick="clickSpan(event)">个人往来催款单<span>></span></li>
					<li id="hrp/acc/accverification/accVerificationMainPage.do" onClick="clickSpan(event)">往来核销<span>></span></li>
					<li id="hrp/acc/accmpayveri/accMpayVeriMainPage.do" onClick="clickSpan(event)">单据核销<span>></span></li>
				</ul>
			</span>
			<span class="arrowLeft"></span>
			<span class="mainText"  id="hrp/acc/accvouch/accounting/accVouchAccountingCountMainPage.do" onClick="clickSpan(event)">凭证记账</span>			
		</div>
		
		<div style="padding:0;height:10px">
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText visibility"></span>
			<span class="mainText visibility"></span>
			<span class="arrowDown" style="margin-left: 2px;"></span>
		</div>
		
		<div>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText relative">期末处理
				<ul class="menu">
					<li>月末处理<span>></span>
			            <ul>
							<li id="hrp/acc/termend/monthend/fundextract/accFundExtractPage.do" onClick="clickSpan(event)">医疗风险基金提取<span>></span></li>
							<li id="hrp/acc/termend/monthend/costextract/accCostExtractPage.do" onClick="clickSpan(event)">支出费用提取<span>></span></li>
							<li id="hrp/acc/termend/monthend/incomcost/accIncomCostPage.do" onClick="clickSpan(event)">收支结转<span>></span></li>
							<li id="hrp/acc/accbaddebtsprepara/accBadDebtsPreparaMainPage.do" onClick="clickSpan(event)">坏账准备提取<span>></span></li>
							<li id="hrp/acc/termend/monthend/closing/accClosingPage.do" onClick="clickSpan(event)">结账<span>></span></li>
						</ul>
					</li>
					<li>年末处理<span>></span>
			            <ul>
			            	<li id="hrp/acc/termend/yearend/fiscalexpenditure/accFiscalExpenditurePage.do" onClick="clickSpan(event)">财政基本补助支出结转<span>></span></li>	
							<li id="hrp/acc/termend/yearend/budgtarget/accBudgTargetMainPage.do" onClick="clickSpan(event)">年度预算下达数<span>></span></li>
							<li id="hrp/acc/termend/yearend/incomcost/accYearIncomCostPage.do" onClick="clickSpan(event)">年度收支结余<span>></span></li>
							<li id="hrp/acc/termend/yearend/balanceallocation/accBalanceAllocationPage.do" onClick="clickSpan(event)">结余分配<span>></span></li>
						</ul>
					</li>
				</ul>
			</span>
		</div>
		
		<div>
			<span class="mainText">出纳管理
			</span>
			<span class="arrowRight"></span>
			<span class="mainText relative">出纳账登记
				<ul class="menu">
					<li id="hrp/acc/acctell/accCashAccountMainPage.do" onClick="clickSpan(event)">现金出纳账<span>></span></li>
					<li id="hrp/acc/acctell/accBankByAccountMainPage.do" onClick="clickSpan(event)">银行出纳账<span>></span></li>
				</ul>
			</span>
			<span class="arrowRight"></span>
			<span class="mainText" id="hrp/acc/acctell/accCashiercheckMainPage.do" onClick="clickSpan(event)">出纳与会计对账</span>
			<span class="arrowRight"></span>
			<span class="mainText relative">结账
				<ul class="menu">
					<li id="hrp/acc/balanceadjustment/accBalanceAdjustmentByDayMainPage.do" onClick="clickSpan(event)">日结<span>></span></li>
					<li id="hrp/acc/balanceadjustment/accBalanceAdjustmentByMonthMainPage.do" onClick="clickSpan(event)">月结<span>></span></li>
				</ul>
			</span>
		</div>
		
		<div style="padding:0;height:10px">
			<span class="arrowRight visibility"></span>
			<span class="arrowDown"></span>
			<span class="mainText visibility"></span>
			<span class="vline"></span>
			<span class="vlineDown1"></span>
		</div>
		
		<div>
			<span class="mainText relative">日记账
				<ul class="menu">
					<li id="hrp/acc/accdiary/accCashJournalMainPage.do" onClick="clickSpan(event)">现金日记账<span>></span></li>
					<li id="hrp/acc/accdiary/accBankJournalMainPage.do" onClick="clickSpan(event)">银行日记账<span>></span></li>
				</ul>
			</span>
			<span class="arrowRight visibility"></span>
			<span class="mainText relative">单位与银行账
				<ul class="menu">
					<li id="hrp/acc/accbanktell/accBankTellMainPage.do" onClick="clickSpan(event)">银行对账单<span>></span></li>
					<li id="hrp/acc/accbanktell/accUnitBankTellMainPage.do" onClick="clickSpan(event)">单位银行账<span>></span></li>
				</ul>
			</span>
			<span class="arrowRight"></span>
			<span class="mainText" id="hrp/acc/accbanktell/accUnitBankCheckMainPage.do" onClick="clickSpan(event)">单位与银行对账</span>
			<span class="arrowRight"></span>
			<span class="mainText" id="hrp/acc/accbanktell/accBalanceAdjustmentMainPage.do" onClick="clickSpan(event)">余额调节表</span>
		</div>
		
		
		<!-- h3 style="padding-bottom: 10px;">新旧制度衔接转换流程</h3>

		<div>
			<span class="mainText addWidth newOldjoin">导入旧制度科目</span>
			<span class="arrowRight"></span>
			<span class="mainText addWidth newOldjoin">自动转换新制度下会计科目</span>
			<span class="arrowRight"></span>
			<span class="mainText addWidth newOldjoin">新制度科目核对与调整</span>
			<span class="arrowRight"></span>
			<span class="mainText addWidth newOldjoin">建立新制度科目标准</span>
		</div>
		
		<div style="padding:0;height:10px">
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="vlineDown visibility"></span>
			<span class="arrowDown"  style="margin-left: 1px;"></span>
		</div>
		
		<div>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="mainText addWidth newOldjoin">自动生成新旧科目映射关系</span>
			<span class="arrowRight"></span>
			<span class="mainText addWidth newOldjoin">新旧科目映射关系核对与调整</span>
			<span class="arrowRight"></span>
			<span class="mainText addWidth newOldjoin">建立新旧科目转换衔接标准</span>
		</div>

		
		<div style="padding:0;height:10px">
			<span class="mainText visibility"></span>
			<span class="mainText visibility"></span>
			<span class="arrowDown"  style="margin-left: 1px;"></span>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility"></span>
			<span class="arrowUp"></span>
			<span class="arrowDown"></span>
			<span class="mainText visibility"></span>
			<span class="arrowRight visibility" style="margin-left: 6px;"></span>
			<span class="arrowUp"></span>
			<span class="arrowDown"></span>
		</div>
		
		<div>
			<span class="mainText mainline">
				<span class="mainText addWidth newOldjoin" style="width:88%">导入旧制度科目余额</span>
				<span class="mainText addWidth newOldjoin" style="width:88%;margin-top:10px">导入旧制度辅助账余额</span>
			</span>
			<span class="arrowRight"></span>
			<span class="mainText mainline">
				<span class="mainText addWidth newOldjoin" style="width:88%">自动转换新旧制度科目余额</span>
				<span class="mainText addWidth newOldjoin" style="width:88%;margin-top:10px">自动转换新旧制度辅助账余额</span>
			</span>
			<span class="arrowRight"></span>
			<span class="mainText mainline">
				<span class="mainText addWidth newOldjoin" style="width:88%">新制度科目余额核对与调整</span>
				<span class="mainText addWidth newOldjoin" style="width:88%;margin-top:10px">新制度辅助账余额核对调整</span>
			</span>
			<span class="arrowRight"></span>
			<span class="mainText mainline">
				<span class="mainText addWidth newOldjoin" style="width:88%">完成新制度科目余额转换</span>
				<span class="mainText addWidth newOldjoin" style="width:88%;margin-top:10px">完成新制度辅助账余额转换</span>
			</span>
		</div -->
	</div>
</body>
</html>
