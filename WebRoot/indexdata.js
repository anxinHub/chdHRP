var tab = null;
var accordion = null;
var tree = null;
var tabItems = [];
var sessionJson = {
	group_id:"",
	hos_id:"",
	comp_code : "",
	comp_name : "",
	hospital : "",
	copy_code : "",
	copy_name : "",
    mod_code : "",
    mod_name : "",
    user_id : "",
    user_code : "",
    user_name : "",
    is_dba : "",
    type_code:"",
    emp_code : "",
    acct_year : "",
    min_date : "",
    max_date : "",
    mod_start: "",
    copy_nature: ""
};

var isEnable=true;
// 创建左边菜单
function createLeftMenu() {
    /*var mod_code = getQueryString("mod_code");
    var copy_code = getQueryString("hos_copy");
    if (mod_code == null || mod_code == "") {
        alert("系统模块加载失败！");
        window.close();
        return;
    }*/
	
	//防止url注入访问
	/*var url=location.search;
	if(url.indexOf("?")!=-1){
		return;
	}*/
    ajaxJsonObjectByUrl("loadSystemModTree.do?isCheck=false", {
        /*copy_code : copy_code,
        mod_code : mod_code*/
    }, function(responseData) {
    	
        // alert(JSON.stringify(responseData.tree))
        // 普通用户显示系统首页
        if (responseData.user_code == "admin") {
            $(".liger-button").css('display', 'none');
        }

        setSeesionJson(responseData)
        createAccordion(responseData.tree);
        $("#accordion1").attr('title', responseData.mod_name);// 加载当前系统模块名称

        if (responseData.Mod == "") {
            $("#navMenu").css('display', 'none');
            $("#layout1").css('margin-top', '4px');
        } else {
            // createTopMenu(responseData.Mod);//顶部系统模块
        }
        initLayout();
        
        //查询用户列表打印格式
        ajaxJsonObjectByUrl("queryGridByUserID.do?isCheck=false", {mod_code : responseData.mod_code}, function(responseData) {
        	Local.setAll(responseData)
        	//console.log(Local.getAll())
        });
        
        
	    //系统有效期提示
        ajaxJsonObjectByUrl("about.do?isCheck=false", {}, function(result) {
        	if(result.tip=="true"){
           		parent.$.ligerDialog.warn("系统将在 "+result.time+" 到期！");
        	}
           	if(result.fwtimeStr){
           		$(".bottom-msg-left").text(result.fwtimeStr);
            }
        });
    });
   
}

// 创建左边手风琴菜单
function createAccordion(responseData) {

    $("#accordion1").html("");
    var menuData = [];
    var accordionCount = 0;
    $.each(responseData, function(idx, item) {
    	
        if (idx == 0 && item.accordion != 'true') {// 如果第一组菜单没有手风琴风格
            accordionCount = accordionCount + 1;
            $("#accordion1").append(
                    "<div title=\"业务功能\" class=\"l-scroll\"><ul id=\"ultree"
                            + accordionCount
                            + "\" style=\"margin-top:3px; \"/></div>");
            menuData = [];
        }
    
    	if(!isEnable  && item.url && item.url!="null" && item.text=="系统启用"){
    		//系统没有启用
    		$.etDialog.open({
    			closeBtn: 0, 
    			title: item.text,
                url: item.url,
                width : $(window).width() * 0.8,
		        height : $(window).height() * 0.8,
                btn: ['下一步','退出系统'],
                btn1: function(index, el) {
                	ajaxJsonObjectByUrl("querySysModStart.do?isCheck=false",{},function(resData) {
                		setModStart(resData);
                		if(!isEnable){
                	    	layer.alert("请先启用系统！");
                    	}else{
                    		$.etDialog.close(index);
                    	}
                    });
                },
                btn2: function(index, el) {
                	logout();
                }
            });

    	}
        
        if (item.accordion == 'true') {// 手风琴风格
            $("#ultree" + accordionCount).ligerTree({
                data : menuData,
                checkbox : false,
                idFieldName : 'id',
                parentIDFieldName : 'pid',
                needCancel : false,
                slide : true,
                btnClickToToggleOnly:false,
                attribute : [ 'nodename', 'url' ],
                isExpand : false,
                nodeWidth : 200,
                onSelect : function(node) {

                    if (node.data==null || !node.data.url || node.data.url == 'null') {
                        return;
                    }
                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid)
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });
            accordionCount = accordionCount + 1;
            // leftMenu+="<div title=\""+item.text+"\" class=\"l-scroll\"><ul
            // id=\"ultree"+accordionCount+"\"
            // style=\"margin-top:3px;\"/></div>";
            $("#accordion1").append(
                    "<div title=\"" + item.text
                            + "\" class=\"l-scroll\"><ul id=\"ultree"
                            + accordionCount
                            + "\" style=\"margin-top:3px;\"/></div>");

            menuData = [];
        } else {
            menuData.push({
                id : item.id,
                pid : item.pid,
                text : item.text,
                url : item.url
            });
            // leftMenu+="<div title=\""+item.text+"\" class=\"l-scroll\"><ul
            // style=\"margin-top:3px;\"/></div>";
        }

    });

    // 最后一组菜单
    $("#ultree" + accordionCount).ligerTree({
        data : menuData,
        checkbox : false,
        idFieldName : 'id',
        parentIDFieldName : 'pid',
        needCancel : false,
        slide : true,
        btnClickToToggleOnly:false,
        // attribute : [ 'nodename', 'url' ],
        isExpand : false,
        nodeWidth : 200,
        onSelect : function(node) {
            if (node.data==null || node.data.url == 'null' || !node.data.url)
                return;

            var tabid = $(node.target).attr("tabid");
            if (!tabid) {
                tabid = new Date().getTime();
                $(node.target).attr("tabid", tabid)
            }
            f_addTab(tabid, node.data.text, node.data.url);
        }
    });
    for (var i = 0 ;i<accordionCount+1; i++) {              //新加的宽度(陈林)
        // 增加了树的宽度，已解决部分系统树分支的名字命名过长 2017/11/13 王思杰
        $("#ultree" + i).css('width', '600px');
    };

}

function initLayout() {
    // 布局
    $("#layout1").ligerLayout({
        leftWidth : 190,
        height : '100%',
        heightDiff : -34,
        space : 4,
        onHeightChanged : f_heightChanged
    });

    var height = $(".l-layout-center").height();

    // Tab
    $("#framecenter").ligerTab({
        height : height,
        showSwitchInTab : true,
        showSwitch : true,
        onAfterAddTabItem : function(tabdata) {
            tabItems.push(tabdata);
            saveTabStatus();
        },
        onAfterRemoveTabItem : function(tabid) {
            for (var i = 0; i < tabItems.length; i++) {
                var o = tabItems[i];
                if (o.tabid == tabid) {
                    tabItems.splice(i, 1);
                    saveTabStatus();
                    break;
                }
            }
        },
        onReload : function(tabdata) {
            var tabid = tabdata.tabid;
            addFrameSkinLink(tabid);
        }
    });

    // 面板
    $("#accordion1").ligerAccordion({
        height : height - 24,
        speed : null
    });

    $(".l-link").hover(function() {
        $(this).addClass("l-link-over");
    }, function() {
        $(this).removeClass("l-link-over");
    });

    tab = liger.get("framecenter");
    accordion = liger.get("accordion1");
    tree = liger.get("tree1");
    $("#pageloading").hide();
    //flowMenu();//医院协同
    css_init();

    // pages_init();//加载页面

}
function f_heightChanged(options) {
    if (tab) {
        tab.addHeight(options.diff);
    }

    if (accordion && options.middleHeight - 24 > 0) {
        accordion.setHeight(options.middleHeight - 24);
    }
}

function f_addTab(tabid, text, url) {
	
    tab.addTabItem({
        tabid : tabid,
        text : text,
        url : url,
        callback : function() {
            // addShowCodeBtn(tabid);
            addFrameSkinLink(tabid);
        }
    });
}
function addShowCodeBtn(tabid) {
    var viewSourceBtn = $('<a class="viewsourcelink" href="javascript:void(0)">查看源码</a>');
    var jiframe = $("#" + tabid);
    viewSourceBtn.insertBefore(jiframe);
    viewSourceBtn.click(function() {
        showCodeView(jiframe.attr("src"));
    }).hover(function() {
        viewSourceBtn.addClass("viewsourcelink-over");
    }, function() {
        viewSourceBtn.removeClass("viewsourcelink-over");
    });
}
function showCodeView(src) {
    $.ligerDialog.open({
        title : '源码预览',
        url : 'dotnetdemos/codeView.aspx?src=' + src,
        width : $(window).width() * 0.9,
        height : $(window).height() * 0.9
    });

}
function addFrameSkinLink(tabid) {
    var prevHref = getLinkPrevHref(tabid) || "";
    var skin = getQueryString("skin");
    if (!skin)
        return;
    skin = skin.toLowerCase();
    attachLinkToFrame(tabid, prevHref + skin_links[skin]);
}
var skin_links = {
    "aqua" : "lib/ligerUI/skins/Aqua/css/ligerui-all.css",// 默认风格
    "gray" : "lib/ligerUI/skins/Gray/css/all.css",// 灰色风格
    "silvery" : "lib/ligerUI/skins/Silvery/css/style.css",// 银色风格
    "gray2014" : "lib/ligerUI/skins/gray2014/css/all.css"// 白色风格
};
function pages_init() {
    var tabJson = $.cookie('liger-home-tab');
    if (tabJson) {
        var tabitems = JSON2.parse(tabJson);
        for (var i = 0; tabitems && tabitems[i]; i++) {
            f_addTab(tabitems[i].tabid, tabitems[i].text, tabitems[i].url);
        }
    }
}
function saveTabStatus() {
    $.cookie('liger-home-tab', JSON2.stringify(tabItems));
}
function css_init() {
    var css = $("#mylink").get(0), skin = getQueryString("skin");
    if (skin != false) {
        $("#skinSelect").val(skin);
    } else {
        $("#skinSelect").val("aqua");
    }

    $("#skinSelect").change(
            function() {
                if (this.value) {
                    $.cookie("drp_htc_skin", this.value, {
                        expires : 7
                    });
                    location.href = "main.html?mod_code="
                            + sessionJson["mod_code"] +"&hos_copy="+sessionJson["copy_code"]+"&skin=" + this.value;
                } else {
                    $.cookie("drp_htc_skin", "aqua", {
                        expires : 7
                    });
                    location.href = "main.html?mod_code="
                            + sessionJson["mod_code"] +"&hos_copy="+sessionJson["copy_code"];
                }
            });

    if (!css || !skin)
        return;
    skin = skin.toLowerCase();
    $('body').addClass("body-" + skin);
    $(css).attr("href", skin_links[skin]);
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = location.search.substr(1).match(reg);
    if (r != null)
        return unescape(decodeURI(r[2]));
    return null;

    /*
     * var now_url = document.location.search.slice(1), q_array = now_url
     * .split('&'); for (var i = 0; i < q_array.length; i++) { var v_array =
     * q_array[i].split('='); if (v_array[0] == name) { return v_array[1]; } }
     * return false;
     */
}
function attachLinkToFrame(iframeId, filename) {
    if (!window.frames[iframeId+"tab"])
        return;
    var head = window.frames[iframeId+'tab'].document.getElementsByTagName('head')
            .item(0);
    var fileref = window.frames[iframeId+"tab"].document.createElement("link");
    if (!fileref)
        return;
    fileref.setAttribute("rel", "stylesheet");
    fileref.setAttribute("type", "text/css");
    fileref.setAttribute("href", filename);
    head.appendChild(fileref);
}
function getLinkPrevHref(iframeId) {
    if (!window.frames[iframeId+"tab"])
        return;
    var head = window.frames[iframeId+"tab"].document.getElementsByTagName('head')
            .item(0);
    var links = $("link:first", head);
    for (var i = 0; links[i]; i++) {
        var href = $(links[i]).attr("href");
        if (href && href.toLowerCase().indexOf("ligerui") > 0) {
            return href.substring(0, href.toLowerCase().indexOf("lib"));
        }
    }
}

function setSeesionJson(responseData) {
	
	sessionJson["group_id"] = responseData.group_id;
	sessionJson["hos_id"] = responseData.hos_id;
	sessionJson["mod_code"] = responseData.mod_code;
    sessionJson["mod_name"] = responseData.mod_name;
    sessionJson["user_id"] = responseData.user_id;
    sessionJson["user_code"] = responseData.user_code;
    sessionJson["user_name"] = responseData.user_name;
    sessionJson["is_dba"] = responseData.is_dba;
    sessionJson["type_code"] = responseData.type_code;
    sessionJson["emp_code"] = responseData.emp_code;
    sessionJson["emp_name"] = responseData.emp_name;
    sessionJson["acct_year"] = responseData.acct_year;
    sessionJson["comp_code"] = responseData.comp_code;
    sessionJson["comp_name"] = responseData.comp_name;
    sessionJson["copy_code"] = responseData.copy_code;
    sessionJson["copy_name"] = responseData.copy_name;
    sessionJson["group_name"] = responseData.group_name;
    sessionJson["hospital"] = responseData.hospital;
    sessionJson["min_date"] = responseData.min_date === undefined ? "" : responseData.min_date;
    sessionJson["max_date"] = responseData.max_date === undefined ? "" : responseData.max_date;
    sessionJson["copy_nature"] = responseData.copy_nature;
    
    setModStart(responseData);
    setSystemInfo();
    
    if(responseData.mod_code=="00"){
    	$("#homeFrame").attr("src","sysHomePage.do?isCheck=false");
    }else if(responseData.mod_code=="01"){
    	$("#homeFrame").attr("src","accHomePage.do?isCheck=false");
    }else if(responseData.mod_code=="03"){
    	$("#homeFrame").attr("src","costHomePage.do?isCheck=false");
    }else{
    	$("#homeFrame").attr("src","homePage.do?isCheck=false");
    }
  
}

//加载模块启用年月
function setModStart(responseData){
	sessionJson["mod_start"] = responseData.mod_start === undefined ? "" : responseData.mod_start;
	var mod_code=sessionJson["mod_code"];
    if(sessionJson.mod_start[mod_code]){
    	isEnable=true;
    }else{
    	isEnable=false;
    }
}


// 加载用户信息
function setSystemInfo(){
	if(sessionJson["type_code"]==2 || sessionJson["type_code"]==3 || sessionJson["type_code"]==4){
		$("#mod_box").hide();
	}
    $("#userName").text(sessionJson["user_name"]);
    
    if(sessionJson["group_name"]!=""){
    	$(".groupName").text(sessionJson["group_name"]);
    }else{
    	$(".groupName").text("无");
    }
    
    if(sessionJson["hospital"]!=""){
    	$(".hospital").text(sessionJson["hospital"]);
    }else{
    	$(".hospital").text("无");
    }
    
    if(sessionJson["copy_name"]!=""){
    	$(".copyName").text(sessionJson["copy_name"]);
    }else{
   		$(".copyName").text("无");
    }
    
    if(sessionJson["acct_year"]!=""){
    	$(".acc_year").text(sessionJson["acct_year"]);
    }else{
   		$(".acc_year").text("无");
    }
   
    div_name.innerHTML = sessionJson["mod_name"];
}



// 系统首页
var openSystemProtalDialog;
function openSystemProtal() {

    openSystemProtalDialog = $.ligerDialog.open({
        title : '系统首页',
        url : 'portalPage.do?isCheck=false',
        width : $(window).width() * 0.9,
        height : $(window).height() * 0.9
    });
}

// 系统公告
function openSystemGongGao() {

    $.ligerDialog.open({
        title : '系统公告',
        url : 'systemGongGao.do?isCheck=false',
        width : $(window).width() * 0.9,
        height : $(window).height() * 0.9
    });
}

// 工作环境
function openSystemTheme() {

    /*
     * $.ligerDialog.open({ title : '工作环境', url: 'systemTheme.do?isCheck=false',
     * width: 420, height: 250 });
     */
    $.ligerDialog.open({
        url : 'systemThemePage.do?isCheck=false',
        data : {},
        height : 400,
        width : 420,
        title : '工作环境',
        modal : true,
        showToggle : false,
        showMax : false,
        showMin : false,
        isResize : false,
        buttons : [ {
            text : '确定',
            onclick : function(item, dialog) {
                dialog.frame.save();
            },
            cls : 'l-dialog-btn-highlight'
        }, {
            text : '取消',
            onclick : function(item, dialog) {
                dialog.close();
            }
        } ]
    });
}
// 重新登录
function systemExit() {
   /* ajaxJsonObjectByUrl("logOut.do?isCheck=false", "", function(responseData) {
        if (responseData.state == "true") {

        }
    });*/
    // var win = window
    //      .open(
    //              "win8/base/win8/win8.htm",
    //              "",
    //              'width='
    //                      + (window.screen.availWidth - 10)
    //                      + ',height='
    //                      + (window.screen.availHeight - 30)
    //                      + ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');
    // top.window.opener = null;
    // top.window.open('', '_self', '');
    // top.window.close();
    // win.focus();

    top.window.location.href = "win8/base/win8/home.html";
}

// 锁定
function lock() {
    var htmlStr = "<div>解锁密码：<br/>"
    htmlStr += "<input type='password' id='unLockPassword'/>&nbsp;&nbsp;&nbsp;&nbsp;<input onClick='unLock(this);' type='button' value='解  锁'/><br/>";
    htmlStr += "<input id='loginMsg' style='border:none;color:#F00;' readOnly='true'></input></div>"
    $.ligerDialog.waitting(htmlStr);
}

// 解锁
function unLock(obj) {
    var passwordStr = obj.document.getElementById("unLockPassword").value;
    if (passwordStr == "") {
        return;
    }
    ajaxJsonObjectByUrl(
            "userUnLock.do?isCheck=false&password=" + passwordStr,
            sessionJson,
            function(responseData) {
                if (responseData.isLogin == "ok") {
                    $.ligerDialog.closeWaitting();
                }

                if (responseData.loginMsg != null
                        && responseData.loginMsg != "") {
                    obj.document.getElementById("loginMsg").value = responseData.loginMsg;
                }
            });

}


/*
 * 凭证窗口专用，勿动
 * src:访问路径,必填
 * title:窗口标题,必填
 * width:系统宽度差额,必填
 * height:系统高度差额,必填
 */
function openFullDialog(url,title,width,height,isModal,isShowMin,data){
    var showMin=false;
    var modal=true;
    if(isShowMin!=undefined)showMin=isShowMin;
    if(isModal!=undefined)modal=isModal;

    $.ligerDialog.open({
        url : url,
        data : data,
        width :$(window).width()-width+10,
        height : $(window).height()-height,
        title : title,
        modal : modal,
        top:0,
        showClose: true,
        showToggle : false,
        showMax : false,
        showMin : true,//是否最小化
        isResize : false
    });
}

//弹出系统全屏窗口
/*
 * url:访问路径,必填
 * title:窗口标题,必填
 * width:系统宽度差额,必填
 * height:系统高度差额,必填
 */
function openDialog(obj){
    var showMin=false;
    var modal=true;
    if(obj.isShowMin!=undefined)showMin=obj.isShowMin;
    if(obj.isModal!=undefined)modal=obj.isModal;
    if(obj.showClose == undefined)  obj.showClose = false;         //  默认不显示关闭按钮
    var isResize=obj.isResize===undefined?false:obj.isResize;
    var showToggle=obj.showToggle===undefined?false:obj.showToggle;
    var showMax=obj.showMax===undefined?false:obj.showMax;
    var isDrag=obj.isDrag===undefined?false:obj.isDrag;
    
    if(obj.buttons){
    	$.ligerDialog.open({
            url : obj.url,
            data : obj.data,
            width :$(window).width()-obj.width+10,
            height : $(window).height()-obj.height,
            title : obj.title,
            modal : modal,//是否模式对话框
            showToggle : showToggle,
            showMax : showMax,
            showMin : showMin,//是否最小化
            isResize : isResize,
            isDrag : isDrag,
            showClose: obj.isShowClose,
            buttons:obj.buttons
        });
    }else{
    	$.ligerDialog.open({
            url : obj.url,
            data : obj.data,
            width :$(window).width()-obj.width+10,
            height : $(window).height()-obj.height,
            title : obj.title,
            modal : modal,//是否模式对话框
            showToggle : showToggle,
            showMax : showMax,
            showMin : showMin,//是否最小化
            isResize : isResize,
            isDrag : isDrag,
            showClose: obj.isShowClose,
            parentframename:obj.parentframename
        });
    }
    
}

/*医院协同begin*/
function flowMenu(){
    if(getQueryString("mod_code")=="00"){
        //系统平台没有医院协同模块。
        return;
    }
    var copy_code = getQueryString("hos_copy");
    ajaxJsonObjectByUrl("loadSystemModTree.do?isCheck=false", {
        copy_code : copy_code,
        mod_code : '99'
    }, function(responseData) {

        var flwMenuData = [];
        $.each(responseData.tree, function(idx, item) {

            flwMenuData.push({
                id : item.id,
                pid : item.pid,
                text : item.text,
                url : item.url
            });
        });

        if(flwMenuData!=""){
            $("#yiyuanXTUrl").addClass("chat");

            $("#flowMenuUl").ligerTree({
                data : flwMenuData,
                checkbox : false,
                idFieldName : 'id',
                parentIDFieldName : 'pid',
                needCancel : false,
                slide : true,
                // btnClickToToggleOnly:false,
                // attribute : [ 'nodename', 'url' ],
                isExpand : false,
                nodeWidth : 132,
                onSelect : function(node) {

                    if (node.data==null || node.data.url == 'null' || !node.data.url)
                        return;

                    var tabid = $(node.target).attr("tabid");
                    if (!tabid) {
                        tabid = new Date().getTime();
                        $(node.target).attr("tabid", tabid);
                    }
                    f_addTab(tabid, node.data.text, node.data.url);
                }
            });
        }
    });

}
/*医院协同end*/
