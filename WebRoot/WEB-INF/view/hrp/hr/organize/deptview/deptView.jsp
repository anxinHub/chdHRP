<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<title>jsMind</title>
    <head>
    <%-- <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, grid, tab, tree, hr, dialog,checkbox,upload" name="plugins" />
        </jsp:include> --%>
        <script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
        <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path %>/lib/hrp/jsmind/js/jsmind.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp/jsmind/js/jsmind.screenshot.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp/jsmind/js/jsmind.draggable.js" type="text/javascript"></script>

<link href="<%=path%>/lib/hrp/jsmind/jsmind.css" rel="stylesheet">
        <!--
            enable drag-and-drop feature
            <script type="text/javascript" src="js/jsmind.draggable.js"></script>
        -->
        <style type="text/css">
        #jsmind_container{
            border:solid 1px #ccc;
            /*background:#f4f4f4;*/
            background:#f4f4f4;
        }
         .bim-forms {
            border-bottom: solid 1px #f5f1f1;
        }
 
        .bim-forms .btn {
            padding: 6px 12px;
        }
 
        .menu {
          width: 150px;
    border: 2px solid #979797;
    position: absolute;
    background: #f5f5f5;
    overflow: hidden;
    z-index: 13;
    font-size: 12px;
    margin: 0;
    padding: 1px;
     box-shadow: 2px 2px 20px #333333;
     border-radius: 5px;
        }
  #divmenu ul li:hover {
        font-size: 17px;
        background-color: #E1B700;
    }
   #divmenu ul li {
        border-radius: 5px;
        list-style: none;
        margin: 5px;
        font-size: 16px;
    }
        .menu ul {
            margin: 0px;
            padding: 0px;
            text-align: center;
            list-style-type: none;
        }
 
        .menu ul li {
            padding: 3px 0px;
            font-size: 12px;
        }
 
        .menu ul li:hover {
            background: #e1dddd;
        }
 
        .menu ul li a:link {
            color: #000;
            text-decoration: none;
        }
        .button {
    box-sizing: border-box;
    height: 26px;
    padding-left: 10px;
    padding-right: 10px;
    border: 1px solid #aecaf0;
    background: #e5edf4;
    outline: none;
    border-radius: 2px;
    cursor: pointer;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
}
/* 图片放大查看 */
	      	.preView {
	      		position: fixed;
			    width: 100%;
			    height: 100%;
			    top: 0;
			    left: 0;
			    opacity: .5;
			    background-color: #000;
			    z-index: 99;
	      	}
	      	
	      	.preView-image {
      		    position: fixed;
			    width: 400px;
			    height: 400px;
			    top: 100px;
			    left: 50%;
			    margin-left: -200px;
			    z-index: 100;
	      	}
	      	
	      	.preView-image img {
      		    width: 100%;
	      	}
    </style>
    </head>
<script type="text/javascript">

   
    var _jm = null;
    function open_empty() {
        var options = {
            container: 'jsmind_container',
            theme: 'greensea',
            editable: false
        }
        _jm = jsMind.show(options);
    }

    var jm = 0;
   

    //预览文件
    function showFile(filepath) {
        layer.photos({ photos: { "data": [{ "src": filepath }] }, anim: 5 });
    }

    $(function () {
        //初始化装载数据
        open_empty();
        InitJsMind();
      //  dragFunc("jsmind_container");
        //监听右侧菜单点击事件，发生点击则隐藏菜单层
        $("#divmenu").click(function (event) {
            var $this = $(event.target);
            $("#divmenu").hide();
        });
 
        //画布添加鼠标点击事件
        $('#jsmind_container').mousedown(function (e) {
            if (e.which == 1) {  // 1 = 鼠标左键 left; 2 = 鼠标中键; 3 = 鼠标右键
                $("#divmenu").hide();
            }
        });

        $("#contentbody").bind("contextmenu", function () {
            var div = $("#divmenu");
            if (showmenu()) div.css({ "left": document.body.scrollLeft + event.clientX - 125, "top": document.body.scrollTop + event.clientY - 60 }).show();

            return false;
        });
        
                document.getElementById('ul').onclick = function(e) {
                    var e = e || window.event;
                    var target = e.target || e.srcElement;
                   //展开所有节点
                    if(target.id=='expand_all'){
                    	 _jm.expand_all();
                         $("#jsmind_container").offset({ top: ((0 - jm)), left: 100 }); //将中心点调至屏幕中心
                     
                    } 
                  //合并所有节点
                    if(target.id=='collapse_all'){
                    	  _jm.collapse_all();
                          $("#jsmind_container").offset({ top: ((0 - jm)), left: 100 }); //将中心点调至屏幕中心
                    }
                    //画布缩小
                    if(target.id=='zoomIn'){
                    	_jm.view.zoomIn()
                    	}
                    //画布放大
                    if(target.id=='zoomOut'){

                        _jm.view.zoomOut()
                    }
                    $('ul li').remove();
                }
        $("#btn_add").click(function () {
         	parent.$.etDialog.open({
                url: 'hrp/hr/organize/deptOrgSetPage.do?isCheck=false',
                title: '组织架构图设置',
                width: $(window).width(),
                height: $(window).height(),
                btn: ['取消' ]
            })
        });
     
    });

    //页面初始化获取树数据
    function InitJsMind() {
        $.post("queryDeptViewOrg.do?isCheck=false", function (data) {

            var str = JSON.stringify(data);
          var jsonData = $.parseJSON(str); 
       
            //加载模型树
            var mind = {
                "meta": {
                    "name": "",
                    "author": "",
                    "version": "0"
                },
                "format": "node_tree",//node_array
                "data": jsonData         			
            }
            _jm.show(mind);
            
        })
    }

   
   /*  //画布缩小
    function zoomIn() {
        if (_jm.view.zoomIn()) {
            zoomOutButton.disabled = false;
        } else {
            zoomInButton.disabled = true;
        };
    };
    //画布放大
    function zoomOut() {
        if (_jm.view.zoomOut()) {
            zoomInButton.disabled = false;
        } else {
            zoomOutButton.disabled = true;
        };
    }; */

    function dragFunc(id) {
        var Drag = document.getElementById(id);
        Drag.onmousedown = function (event) {
            var ev = event || window.event;
            event.stopPropagation();
            var disX = ev.clientX - Drag.offsetLeft;
            var disY = ev.clientY - Drag.offsetTop;
            document.onmousemove = function (event) {
                var ev = event || window.event;
                Drag.style.left = ev.clientX - disX + "px";
                Drag.style.top = ev.clientY - disY + "px";
                Drag.style.cursor = "move";
            };
        };
        Drag.onmouseup = function () {
            document.onmousemove = null;
            this.style.cursor = "default";
        };
    };


    //通过JS屏蔽自带右键菜单
    document.oncontextmenu = function (e) {
        return false;
    }
    //动态化展示右键菜单
    function showmenu(selected_node) {
        var selected_node = _jm.get_selected_node(); // as parent of new node
    /*      //创建一个div
		var div = document.createElement('divmenu');
		div.innerHTML = '<li οnclick="add_node1(); class="add"" >'+'测试'+'</li>'; //设置显示的数据，可以是标签．
		//div.class = "add";//设置div的属性，如：class，title，id 等等
 
		var bo = document.body; //获取body对象.
		//动态插入到body中
		bo.insertBefore(div, bo.lastChild); */
		 $('ul li').remove();
		　var ul = document.getElementById('ul');

	　//设置 li 属性，如 id
 	　//添加 li
	if(selected_node.children.length==0){
		ajaxJsonObjectByUrl("../os/deptview/queryDeptViewOrgMsg.do",{
			'dept_code':selected_node.id,
			 'rjt':'json'
		
		}, function (data) {
			if(data.Rows.length>0){
				 $.each(data.Rows,function(i,v){
						//添加职称+
					 　var li = document.createElement("li");
					　li.innerHTML =v.TEC_TITLE_CODE_NAME;
					  ul.appendChild(li); 
					　//添加学历
					　var li = document.createElement("li");
					　li.innerHTML = v.EDUCATION_CODE_NAME;
					  ul.appendChild(li);
					　//添加入院时间
					　var li = document.createElement("li");
					　li.innerHTML =v.HOSTIME;
					  ul.appendChild(li);
					 　//添加照片
						　var li = document.createElement("li");
					 
					　　　　var img = document.createElement("img");
					　　　　//设置 img 属性，如 id
					　　　　img.setAttribute("id", "newImg");
					       img.setAttribute("width", "160");
					       img.setAttribute("height", "200");
					　　　　//设置 img 图片地址
					　　　　img.src = v.PHOTO;
					 
					　li.appendChild(img);
					ul.appendChild(li);
						 }) 
			}else{
				document.getElementById("divmenu").style.display="none";
			}
			
		 })
		 
	 　}
	　　　
	
	　//li.innerHTML = "查看照片";
/* 	<li οnclick="expand_all()" class="pub">展开所有</li>
    <li οnclick="collapse_all()" class="pub">合并所有</li>
    <li οnclick="zoomIn()" class="pub">画布放大</li>
    <li οnclick="zoomOut()" class="pub">画布缩小</li> */
	　　　　
        if (!selected_node) {
            $("#divmenu ul li:not(.pub)").hide();
            $("#divmenu ul li[class='pub']").show();
        }
        else {
            if ((selected_node.isroot || false)) {
                $("#divmenu ul li:not(.add)").hide();
                $("#divmenu ul li[class='add']").show();
            }
            else if (selected_node.data.leave == 1) {
                if (selected_node.children.length > 0) {
                    $(".pub,.update,.view,.history,.delete").hide();
                    $(".sel,.upload,.add,.move").show();
                } else {
                    $(".pub,.update,.view,.history").hide();
                    $(".sel,.upload,.add,.move,.delete").show();
                }
            }
            else {
                if (selected_node.children.length > 0) {
                    $(".pub,.view,.history,.update,.delete").hide();
                    $(".upload,.sel,.add,.move").show();
                }
                else {
                    if (selected_node.data.filepath == "null" || selected_node.data.filepath == undefined) {
                        $(".upload,.sel,.delete,.add,.move").show();
                        $(".pub,.view,.history,.update").hide();
                    } else {
                        $(".view,.delete,.update,.history,.move").show();
                        $(".pub,.sel,.upload,.add").hide();
                    }
                }
            }
        }
        return true;
    }


   
     //鼠标滚轮缩放
    window.onmousewheel = document.onmousewheel = function (e) {
        e = e || window.event;
        if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件               
            if (e.wheelDelta > 0) { //当滑轮向上滚动时  
                _jm.view.zoomIn()
            }
            if (e.wheelDelta < 0) { //当滑轮向下滚动时  
                _jm.view.zoomOut();
            }
        } else if (e.detail) {  //Firefox滑轮事件  
            if (e.detail > 0) { //当滑轮向下滚动时  
                _jm.view.zoomOut();
            }
            if (e.detail < 0) { //当滑轮向上滚动时  
                _jm.view.zoomIn()
            }
        }
    }
</script>
<body>
<div class="bim-cont">
    <div class="bim-forms bg-none">
<!--          <button id="btn_add" class="button">设置</button>
 -->    </div>
 
    <div class="box table-responsive border-top-none" id="contentbody" style="overflow-y:auto;">
        <div id="layout" >
            <div id="jsmind_container" ></div>
        </div>
    </div>
</div>
<!--右侧菜单-->
<div id="divmenu" class="menu" >
    <ul id='ul'>
    </ul>
</div>
 
</body>
</html>