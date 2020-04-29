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
<script src="<%=path%>/lib/hrp/script/dateformat.js"></script>
<script src="<%=path%>/lib/echarts/echarts.js"></script>
<script src='<%=path%>/lib/ckeditor/ckeditor.js'></script>
<script src="<%=path%>/indexdata.js"></script>
<script>
	
	(function(){
		$.ajax({    
			type:"POST",    
		    url:"./hrp/portal/querySysShowPortalInfo.do?isCheck=false",    
		    data:{},
		    dataType: "json",
		    success: function(result) { 
		    	
		    	var compareHeightArr = []; // 比较高度的数组
                var isAllowCompare = false;

                var $moduleList = $("#module-list");
                if (result.length === 0) {
                    $(".init-loading").hide(); 
                    $moduleList.html("<div class='all-nothing'></div>");
                    return;
                }
                
                var moduleHtml = "";
                moduleHtml +=
                	'<li class="module-item-wrap">' +
                    '<div class="module-item">' +
                        '<div class="module-head">' +
                            '<span>' + "常用报表 "+ '</span>'+
                         '</div>' +
                         '<div class="module-content"></div>' +
                         '<div class="l-loading module-loading"></div>' +
                     '</div>' +
                 '</li>';
                
                var $moduleHtml = $(moduleHtml);

                $moduleList.append($moduleHtml);

                var moduleGrid = "";
                var colData = result[0].column_name.split(",");
                var contentData = result[0].title_code;
                var in_$moduleHtml = $moduleHtml;
                var $moduleContent = in_$moduleHtml.find(".module-content");
                
                moduleGrid +=
                    '<table class="module-grid" >';
                        '<tr>';

                        for (var l = 0; l < colData.length; l++) {
                            moduleGrid += '<th>' + colData[l] + '</th>';
                        }
                   		    moduleGrid += '</tr>';
                   		 for (var j = 0; j < result[0].show_rows; j++) {
                   			 
                   			var content_url = j < result.length ? result[j].more_url : "";
                   			var title_names = j < result.length ? result[j].title_name : "";
                   			
                            moduleGrid += '<tr>';

                            moduleGrid += '<td>' +(j+1)+'</td>'+
                                 		 '<td>'+	
                                		 '<span class="mainText" id= "'+content_url+'">'  + title_names +'</span>'
                                 		 '</td>';
                            moduleGrid += '</tr>';
                   		 }
                    moduleGrid += '</table>'; 
                       		
                    $moduleContent.html(moduleGrid);
                
		    },
		    error: function() {
	            alert('请求失败~');
	        }
		});   
	/**
     * 给a标签绑定事件，点击打开dialog全屏
     */
    $(document).on("click", "span", function (event) {
        
    	var url = event.target.id;
		var tabid = parent.$("#accordion1").find("ul li[url$='"+url+"']").attr("tabid");
		
        if (!tabid) {
            tabid = new Date().getTime();
            parent.$("li[url$='"+url+"']").attr("tabid", tabid);
        }
        parent.f_addTab(tabid, event.target.innerText, url);
    	}) 
	})()
</script>
<style type="text/css">
	 html, body {
        height: 100%;
    }
    body {
        margin: 0;
        font-size: 14px;
        overflow:hidden;
        background: #f9f9f9;
    }
    /*模块列表*/
    #module-list {
        margin: 0;
        padding: 0;
        width: 100%;
        min-width: 700px;
        height: 100%;
        list-style: none;
        font-size: 0;
        overflow: auto;
    }
    /*列表项外层*/
    .module-item-wrap {
        display: inline-block;
        float: left;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        padding: 6px 10px;
        min-height: 200px;
        width: 50%;
        min-width: 300px;
    }
    .module-item-wrap.full-row {
        width: 100%;
    }
    .module-item {
        border-radius: 3px;
        border: 1px solid #bbb;
        position: relative;
        background: #fff;
    } 
    .module-head {
        font-size: 14px;
        height: 26px;
        line-height: 26px;
        background: url(lib/ligerUI/skins/Aqua/images/grid/header-bg.gif) repeat; 
        padding: 0 15px;  
        -webkit-box-sizing: border-box; 
                box-sizing: border-box; 
        color: #333;
        font-weight: bold;
        border-bottom: 1px solid #bbb;
    }
    .module-more {
        float: left;
        cursor: pointer;
        color: #333;
        text-decoration: none;
    }
   
    .module-more:hover {
        text-decoration: underline;
        color: #fb9a5f;
    }
    .module-content {
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
        min-height: 174px;
        padding: 10px;
        box-sizing: border-box;
        overflow: auto;
    }
    .module-grid {
        font-size: 14px;
        width: 100%;
        position: relative; 
    }
    .module-grid:after {
        content: "";
        width: 100%;
        height: 1px;
        border-bottom: 1px dashed #bbb;
        position: absolute;
        left: 0;
    }
    .module-grid tr {
        display: table-row;
        border-bottom: 1px dashed #bbb;
        height: 30px;
        line-height: 30px;
        text-align: left;
        position: relative;
        box-sizing: border-box;
    }
    .module-grid tr:hover {
        background: #f9f9f9;
        cursor: pointer;
    }
    .module-grid tr:after {
        content: "";
        width: 100%;
        height: 1px;
        border-bottom: 1px dashed #bbb;
        position: absolute;
        left: 0;
    }
    .module-grid tr:first-child:after {
        content: "";
        width: 100%;
        height: 1px;
        border-bottom: none;
    }
    .module-grid tr td {
        padding: 0;
        height: 30px;
        box-sizing: border-box;
    }
    .module-grid tr td:first-child,
    .module-grid tr th:first-child {
        padding-left: 10px;
        -webkit-box-sizing: border-box;
                box-sizing: border-box;
    }

</style>
</head>

<body>
	<div style="text-align: center;">
		<div class="l-loading init-loading" ></div>
	    <ul id="module-list"></ul>
	</div>
</body>
</html>