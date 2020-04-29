function init(){
	$(document).ready(function () {	
		if(window.location.host.indexOf("localhost")==-1 && window.location.host.indexOf("127.0.0.1")==-1){
			$("#sourchDiv").css("display","none");
		}
		
        if($.cookie("drp_userName")!=null){
        	$("#userName").val($.cookie("drp_userName"));
        }
        if($.cookie("drp_passWord")!=null){
       		passWord.value=$.cookie("drp_passWord");
        }
        if($.cookie("drp_savePwd")!=null){
        	if($.cookie("drp_savePwd")=="true"){
        		$("#savePwd").attr("checked",true);
        	}else{
        		$("#savePwd").attr("checked",false);
        	}
        }
		
	});
}
init();

function login(){
	var user=document.getElementById('userName').value;
	var pwd=document.getElementById('userPwd').value;
	$("#tipMsg").text("");
	if(user==""){
		$("#tipMsg").text("请输入用户名");
		//alert("请输入用户名");
		return false;
	}
	if(pwd==""){
		$("#tipMsg").text("请输入密码");
		//alert("请输入密码");
		return false;
	}
	
	IbtnEnter.src="lib/hrp/style/login/images/user_botton_bak.png";
	
	$.ajax({
        type:"post",
        url:"login_login",
        data:{user_code:user,password:pwd},
        dataType:"json",
       // contentType: 'application/xml', 
        success:function(responseData){
        	IbtnEnter.src="lib/hrp/style/login/images/user_botton.gif";
        	
            if(responseData!=null ){
            	if(responseData.error!=undefined && responseData.error!=""){
            		$("#tipMsg").text("");
            		alert(responseData.error);
            		return false;
            	}
            	
    	       	if(responseData.msg!=undefined && responseData.msg!="ok"){
    	       		//alert(dataFormat.msg);
    	       		$("#tipMsg").text(responseData.msg);
    	       		return false;
    	       	}else{
    	       		openIndex(user,pwd,responseData.mod_code);
    	       	}
            }
        	
        	if( typeof callBackFun === 'function' ){
        		callBackFun(responseData);
        	}else{
        		//$.ligerDialog.error('回调方法不存在！');
        	}
        },
        error:function(data){
            alert(data);  //弹出 error
        	IbtnEnter.src="lib/hrp/style/login/images/user_botton.gif";
        	$("#tipMsg").text("");
        	//alert('系统异常，请稍后重试')
        	//$.ligerDialog.error('系统异常，请稍后重试！');
        }
       
    });
	
	
	
}

function openIndex(user,pwd,mod_code){

	if ($("#savePwd").attr("checked") == true) {
		$.cookie("drp_userName", user, { expires: 7 });
		$.cookie("drp_passWord", pwd, { expires: 7 });
		$.cookie("drp_savePwd", "true", { expires: 7 });
	}else{
		$.cookie("drp_userName", "", { expires: 7 });
		$.cookie("drp_passWord", "", { expires: 7 });
		$.cookie("drp_savePwd", "false", { expires: 7 });
	}
	var skin=$.cookie("drp_skin");
	if(skin!=null && skin!=""){
		skin="&skin="+skin;
	}else{
		skin="";
	}
	var win = window.open("index.html?mod_code="+mod_code+skin, "", "width="+(screen.availWidth)+", height="+(screen.availHeight-35)+", top=0, left=0,scrollbars=1, resizable=0, status=1");
	top.window.opener=null;
	top.window.open('','_self','');
	top.window.close();
	win.focus();
	
}


function setSource(){
	/*$.layer({
		type: 2,
		border: [0],
		title: [
			'平台数据源设置',
			'border:none; background:expression(curDlgBackground); color:expression(curDlgColor);' //自定义标题风格
		],
		maxmin: true,
		shadeClose: false, //开启点击遮罩关闭层
		area : ['600px' , '500px'],
		iframe: {src:'his/sys/datasource/system.html.html'}
	});*/
	//$.ligerDialog.open({ url: 'his/sys/datasource/system.html', height: 300,width: 350, title:'平台数据源设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:false,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveUser(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
	window.showModalDialog("his/sys/datasource/system.html",self,"dialogHeight:310px;dialogWidth:500px;center: yes; status=no");
}


