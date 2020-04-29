<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=GBK" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title></title>
<script language="JavaScript">
		window.prefix = document.URL.replace(/\?[^\?]*$/g,"").replace(/[\w.]+$/g, "");
		window.rootPath=window.prefix.substr(0,window.prefix.length-3);
</script>
<link href="../dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="../dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="../dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="../dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>

<link rel="stylesheet" type="text/css" href="../dwz/erp/menu/menu1/css/superfish.css" />	
	
<style type="text/css">
	#header{height:85px}
	#leftside, #container, #splitBar, #splitBarProxy{top:90px}
</style>

<!--[if lte IE 9]>
<script src="../dwz/js/speedup.js" type="text/javascript"></script>
<![endif]-->
<script src="../dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<!--[if IE 6]> 
<script src="../dwz/erp/menu/menu1/js/DD_belatedPNG_0.0.8a.js" type="text/javascript" ></script> 
<script type="text/javascript"> 
DD_belatedPNG.fix('.headerNav a'); 
</script>  
<![endif]--> 
<script type="text/javascript" src="../dwz/erp/menu/menu1/js/superfish.js"></script>
		
<script src="../dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="../dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="../dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="../dwz/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="../dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="../dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>

<script src="../dwz/bin/dwz.min.js" type="text/javascript"></script>
<script src="../dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script language="JavaScript" src="base/scripts/sub.js" ></script>
<script language="JavaScript" src="../base/scripts/comm.js" ></script>
<script language="JavaScript" src="base/scripts/CreateObjects.js" ></script>
<script Language="JavaScript" src="base/scripts/main.js" ></script>
<script language="JavaScript" src="../dwz/erp/index_menu_cbcs.js" ></script>

<script type="text/javascript">
$(document).ready(function(){
//	$("#mainFrame").css("height",screen.height-328);
	$("#mainFrame").css("height",document.body.scrollHeight);
	
	DWZ.init("../dwz/dwz.frag.xml", {
//		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
		loginUrl:"../login.jsp",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
//		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			init();
			$("#themeList").theme({themeBase:"../dwz/themes"});
		//	setTimeout(function() {$("#sidebar .toggleCollapse div").trigger("click");}, 10);
		/*	if(modeMenuStr!=undefined && modeMenuStr!=""){
				changeModeFun(window.module,3);
			}else{
				setTimeout(function(){
					changeModeFun(window.module,3);
				}, 100);
			}*/
		}
	});
	
		
	$("ul.sf-menu,ul.sf-navbar").superfish();
	$("ul.sf-vertical").superfish({
	        animation:{opacity:'show',height:'show'},
			speed:'fast'
	});
});



window.onbeforeunload=function(){

	if(is_close_alert==1){
		return;
	}

	if(document.body.clientWidth-event.clientX<=51||event.altKey||event.ctrlKey){
		window.xmlhttp.post("logout", "","?ignore=true");
	}
};

</script>
</head>

<body scroll="no" oncontextmenu="return false" >
     
			<div id="navMenu"  style="DISPLAY: none; ">
				<ul class="sf-menu">
					<li style="display:none"><a href='#' onClick="changeModeFun('uinfo',0,this);" rel="uinfo">单位信息</a></li>
                    
					<li style="display:none"><a href='#' onClick="changeModeFun('acct',0,this);" rel="acct">财务管理</a></li>
					
					<li style="display:none"><a href='#' onClick="changeModeFun('budg',0,this);" rel="budg">预算管理</a></li>
					
					<li style="display:none"><a href='#'>成本管理</a>
						<ul>
							<li style="display:none"><a href='#' onClick="changeModeFun('dept',0,this);"  rel="dept" >科室成本核算</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('proj',0,this);"  rel="proj">项目成本核算</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('disease',0,this);"  rel="disease">病种成本核算</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('direct',0,this);"  rel="direct">科主任经营分析</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('hos',0,this);"  rel="hos">医院运营综合分析</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('rate',0,this);"  rel="rate">作业成本核算</a></li>
						</ul>
					</li>
					<li style="display:none"><a href='#'>绩效考核</a>
						<ul>
							<li style="display:none"><a href='#' onClick="changeModeFun('perf',0,this);"  rel="perf">绩效考核</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('bonus',0,this);"  rel="bonus">奖金分配</a></li>
						</ul>
					</li>
					<li style="display:none"><a href='#' onClick="changeModeFun('mate',0,this);" rel="mate">物流管理</a></li>
					<li style="display:none"><a href='#'>固定资产管理</a>
						<ul>
							<li style="display:none"><a href='#' onClick="changeModeFun('equi',0,this);"   rel="equi">固定资产</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('imma',0,this);"  rel="imma">无形资产</a></li>
							<li style="display:none"><a href='#' onClick="changeModeFun('dev',0,this);"  rel="dev">单机核算</a></li>
						</ul>
					</li>
					<li style="display:none"><a href='#' onClick="changeModeFun('hr',0,this);" rel="hr">人力资源管理</a></li>
					
					</ul>
			</div>

<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeSys").click(function(a){
		$("#navMenu").slideDown(300);
		a.stopPropagation();
		$(this).blur();
	});
	$("#navMenu").click(function(a){
		a.stopPropagation()
	});
	$(document).click(function(a){
		a.button!=2 && $("#navMenu").slideUp(300)
	});
	$("#foldin").click(function(){
		$("#navMenu").slideUp(300)
	});
});

</SCRIPT>
	<div id="layout">
        <div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a>
				<ul class="nav">
					<!--<li><a href="javascript:void(0);" onclick="showHelp();return false;">帮助</a></li>-->
                    
                    <li id="changeSys"><a href="javascript:">切换系统</a></li>
                    
					<!--<li><a href="#" id="changeSys"  onclick="changeSys();">切换系统</a></li>-->
                    	<li><a href="javascript:void(0);" onClick="hrp_version();return false;">版本信息</a></li>
                        <li><a href="javascript:void(0);" onClick="modifyPswd();return false;">修改密码</a></li>
                        <li><a href="javascript:void(0);" onClick="systemExit();">退出</a></li>
                    </ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<!--<li theme="red"><div>红色</div></li>-->
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
				<div style="text-align:center;line-height:80px;color:white;font-size:24px;font-weight:bold;">医院资源管理与大数据应用系统DRP-<font style="font-size:16px">HRP</font></div>
			</div>
			 

			
			<!--div id="navMenu">
				<ul>
					<li id="mode_acct" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('acct',0);"><span>会计核算</span></a></li>
					<li id="mode_mate" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('mate',0);"><span>物流管理</span></a></li>
					<li id="mode_equi" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('equi',0);"><span>固定资产</span></a></li>
					<li id="mode_imma" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('imma',0);"><span>无形资产</span></a></li>
					<li id="mode_pact" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('pact',0);"><span>合同管理</span></a></li>
					<li id="mode_hr" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('hr',0);"><span>人力资源</span></a></li>
					<li id="mode_budg" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('budg',0);"><span>预算管理</span></a></li>
					<li id="mode_payctl" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('payctl',0);"><span>资金支出控制</span></a></li>
					<li id="mode_perf" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('perf',0);"><span>绩效考核</span></a></li>
					<li id="mode_bonus" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('bonus',0);"><span>奖金分配</span></a></li>
					<li id="mode_dept" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('dept',0);"><span>科室核算系统</span></a></li>
					<li id="mode_sys" style="display:none"><a href="javascript:void(0);" onclick="changeModeFun('sys',0);"><span>单位信息</span></a></li>
				</ul>
			</div-->
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2 id="topMenuName"></h2><div>收缩</div></div>
				<div class="accordion" fillSpace="sidebar" id="accordionMenuId">
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<!--ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul-->
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<div class="alertInfo">
								<p><span id="stratagemName"></span></p>
								<p><span id="com" ></span></p>
								<!--h2><a href="#" target="_blank" onclick="return showHelp();">系统使用手册(word)</a></h2-->
							</div>
							<div class="right">
							</div>
							<p>您好，<span id="curUser" style="color:blue"></span>&nbsp;&nbsp;登录日期：<span id="curDate"></span>&nbsp;&nbsp;</p>
							<p><span id="curComp"></span></p>
						</div>
						<div class="pageFormContent" >
							<iframe id="mainFrame" width="100%" height="" class="share_self"  frameborder="0" scrolling="no" ></iframe>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>

	<div id="footer"><p style="height:8px;">&nbsp;</p>
    杭州亦童科技有限公司</div>

<script Language="JavaScript" >
	if (typeof activex_lib == undefined || typeof activex_lib=="unknown"){
		CreateObjectControl('activex_lib','VHTable',1,1,null,'none');
	}
</script>
<vh:statCmm id="cmm" style="behavior: url(./base/htc/statCommon.htc)" init="0"/>

</body>
</html>
