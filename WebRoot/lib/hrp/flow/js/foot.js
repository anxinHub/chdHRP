
function initBottomMenu(){
	
	//Adjust panel height
	$.fn.adjustPanel = function(){ 
		$(this).find("ul, .subpanel").css({ 'height' : 'auto'}); //Reset subpanel and ul height
		
		var windowHeight = $(window).height(); //Get the height of the browser viewport
		var panelsub = $(this).find(".subpanel").height(); //Get the height of subpanel	
		var panelAdjust = windowHeight - 120; //Viewport height - 100px (Sets max height of subpanel)
		var ulAdjust =  panelAdjust - 25; //Calculate ul size after adjusting sub-panel (27px is the height of the base panel)
		
		if ( panelsub >= panelAdjust ) {	 //If subpanel is taller than max height...
			$(this).find(".subpanel").css({ 'height' : panelAdjust }); //Adjust subpanel to max height
			$(this).find("#alertpanel ul").css({ 'height' : ulAdjust}); //Set subpanel ul to auto (default size)
			$(this).find("#chatpanel ul").css({ 'height' :  ulAdjust});
		//	$(this).find("ul").css({ 'height' : ulAdjust}); //Adjust subpanel ul to new size
			$(this).find("#flowMenu ul").css({ 'height' :  ulAdjust});
		}
		else if ( panelsub < panelAdjust ) { //If subpanel is smaller than max height...
			$(this).find("#alertpanel ul").css({ 'height' :  'auto'}); //Set subpanel ul to auto (default size)
			$(this).find("#chatpanel ul").css({ 'height' :  'auto'}); //Set subpanel ul to auto (default size)
			$(this).find("#flowMenu ul").css({ 'height' :  windowHeight - 180}); //Set subpanel ul to auto (default size)
		}
	};
	
	//Execute function on load
	$("#chatpanel").adjustPanel(); //在线用户
	$("#alertpanel").adjustPanel(); //消息
	$("#flowpanel").adjustPanel(); //医院协同
	
	//Each time the viewport is adjusted/resized, execute the function
	$(window).resize(function () { 
		$("#chatpanel").adjustPanel();//在线用户
		$("#alertpanel").adjustPanel();//消息
		//$("#flowpanel").adjustPanel();//医院协同
	});
	
	$("#flowpanel a:first").click(function() { //If clicked on the first link of #chatpanel and #alertpanel...
		if(getQueryString("mod_code")=="00" || $("#flowMenuUl li").length==0){
			//系统平台没有医院协同模块。
			return;
		}
		if($(this).next("#flowMenu").is(':visible')){ //If subpanel is already active...
			$(this).next("#flowMenu").hide(); //Hide active subpanel
			$("#flowpanel a").removeClass('active'); //Remove active class on the subpanel trigger
		}
		else { //if subpanel is not active...
			$("#flowMenu").hide(); //Hide all subpanels
			$(this).next("#flowMenu").toggle(); //Toggle the subpanel to make active
			$("#flowpanel a").removeClass('active'); //Remove active class on all subpanel trigger
			$(this).toggleClass('active'); //Toggle the active class on the subpanel trigger
		}
	
		
		return false; //Prevent browser jump to link anchor
	});

	
	//Click event on Chat Panel + Alert Panel	
	$("#chatpanel a:first, #alertpanel a:first").click(function() { //If clicked on the first link of #chatpanel and #alertpanel...
		if($(this).next("#onlineUser").is(':visible') || $(this).next("#message").is(':visible')){ //If subpanel is already active...
			$(this).next("#message").hide();
			$(this).next("#onlineUser").hide(); //Hide active subpanel
			$("#chatpanel a").removeClass('active'); //Remove active class on the subpanel trigger
			$("#alertpanel a").removeClass('active'); //Remove active class on the subpanel trigger
		}
		else { //if subpanel is not active...
			$("#message").hide(); //Hide all subpanels
			$(this).next("#message").toggle();
			$("#onlineUser").hide();
			$(this).next("#onlineUser").toggle(); //Toggle the subpanel to make active
			$("#chatpanel a").removeClass('active'); //Remove active class on all subpanel trigger
			$("#alertpanel a").removeClass('active'); //Remove active class on the subpanel trigger
			$(this).toggleClass('active'); //Toggle the active class on the subpanel trigger
		}
		
		return false; //Prevent browser jump to link anchor
	});
	
	//Click event outside of subpanel
	/*$(document).click(function() { //Click anywhere and...
		$(".subpanel").hide(); //hide subpanel
		$("#footpanel li a").removeClass('active'); //remove active class on subpanel trigger
	});*/
	
	$("#footpanel h3").click(function() {
		$(this).parent().hide(); 
		$(this).parent().prev().removeClass('active');
	})
	
	$('.subpanel ul').click(function(e) { 
		e.stopPropagation(); //Prevents the subpanel ul from closing on click
	});
	
	//Delete icons on Alert Panel
	$("#alertpanel li").hover(function() {
		$(this).find("a.delete").css({'visibility': 'visible'}); //Show delete icon on hover
	},function() {
		$(this).find("a.delete").css({'visibility': 'hidden'}); //Hide delete icon on hover out
	});
	
	//在线用户数
	$("#onlineUserCount").text("18");
	
}

function openGuanyu(){
	$(".dropdown-menu").hide();
	var rad = Math.ceil(Math.random()*10);
	$.ligerDialog.open({title : '东华医为科技有限公司', url: 'guanyu.html?v='+rad, height: 330,width: 400, 
		buttons: [ { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] });
}


function openGzt(){
	$(".dropdown-menu").hide();
	var htmlStr = "<div style='font-size:14px'>密码：<input type='password' id='gztPassword' /><br/><br/><input id='loginMsg' style='border:none;color:#F00;'></input></div><br/>"
	    htmlStr += "<div style='text-align:center;font-size:14px'><input onClick='gztLogin();' type='button' value='&nbsp;&nbsp;确&nbsp;定Enter&nbsp;&nbsp;'/>&nbsp;&nbsp;&nbsp;&nbsp;<input onClick='gztQx();' type='button' value='&nbsp;&nbsp;取&nbsp;消Esc&nbsp;&nbsp;'/></div><br/>"
	    $.ligerDialog.waitting(htmlStr);
	
	document.getElementById("gztPassword").focus();
	document.onkeydown = function(event) {
		var e = event || window.event|| arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 27) { // 按 Esc 
			$.ligerDialog.closeWaitting();
		}
		if (e && e.keyCode == 113) { // 按 F2 
		}
		if (e && e.keyCode == 13) { // enter 键
			gztLogin();
		}
	};
}

//登录
function gztLogin() {
	
    var passwordStr = document.getElementById("gztPassword").value;
    if (passwordStr == "") {
        return;
    }
    ajaxJsonObjectByUrl(
            "singleLogin.do?isCheck=false",{
            	user_code : sessionJson.user_code,
    			user_pwd : passwordStr
            },
            function(responseData) {
                
                if (responseData.loginMsg != null
                        && responseData.loginMsg != "") {
                    document.getElementById("loginMsg").value=responseData.loginMsg;
                    return;
                }
                $.ligerDialog.closeWaitting();
                var rad = Math.ceil(Math.random()*10);
                $.ligerDialog.open({
                    url : 'single/salaryQuery.html?v='+rad,
                    data : {},
                    height : $(window).height(),
                    width : $(window).width(),
                    title : '工资条查询',
                    modal : true,
                    showToggle : false,
                    showMax : false,
                    showMin : false,
                    isResize : false,
                    buttons: [ { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ]
                });
            });

}

function gztQx(){
	$.ligerDialog.closeWaitting();
}
