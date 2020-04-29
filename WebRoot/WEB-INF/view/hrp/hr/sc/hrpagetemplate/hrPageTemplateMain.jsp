<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<style type="text/css">
.tempplate_wrapper{
	width:100%;
	height:100%;
	position:relative;
	right:20px;
	left:10px;
	top:10px;
    overflow: auto;
}
.tempplate_wrapper ul li{
	float:left;
	margin:5px;
}
.tempplate_wrapper ul li img{
    width: 260px;
    height: 120px;
	display:block;
	border:none;
	opacity:0.7;
	-moz-box-shadow:2px 2px 4px #8e8e8e;
	-webkit-box-shadow:2px 2px 4px #8e8e8e;
	box-shadow:2px 2px 4px #8e8e8e;
}
</style>
<script type="text/javascript">
    var path = "<%=path%>"
	var tree;
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	$(function() {
		//布局
		$("#layout").ligerLayout({leftWidth: 180, minLeftWidth: 230, allowLeftResize: false,isLeftCollapse:false,allowLeftCollapse:false});
		initTemplate();
		loadTree();
	});
	
      function initTemplate(){
    	  template_grid = function(options){
		    this.options = $.extend({
			 template_type:'01',//如果模板类型为空默认模板01
		    },options);
		    this.init();
	      };
	     
	      template_grid.prototype.init = function(){
			  let promise = this.query();
			  promise.then((resolve) =>{
                  this.bind(resolve);
		     });
		
		  };
		  template_grid.prototype.query = function (){
       	    return new Promise((resolve,reject) =>{
		       	     $.ajax({
			       	    	type: "post",
			       			url: this.options.url,
			       			data: {template_type:this.options.template_type},
			       			dataType: "json",
			       			async: true,
			       			success:function(responseData){
			       				resolve(responseData.Rows);
				       	    }
		        	    })
           	    })
		  };
		  template_grid.prototype.bind = function (resolve){
				var elem_tempplate_div = document.getElementById("tempplate_div");
				elem_tempplate_div.innerHTML = ""; 
				var elem_ul = document.createElement("ul");
				if(resolve.length > 0){
	    			$.each(resolve, function(v_index, v_data){
		    			var elem_li = document.createElement("li");
		    			var elem_img = document.createElement("img");
		    			elem_img.src = path + "/" + v_data.template_img
		    			elem_li.appendChild(elem_img);
		    			elem_ul.appendChild(elem_li);
		    			var elem_div= document.createElement("div");
		    			elem_div.style.textAlign = "center";
		    			elem_div.style.margin="5px";
		    			var elem_button= document.createElement("button")
		    			elem_button.innerHTML = "查看";
		    			elem_button.className = "l-button";
		    			elem_button.style.width = "120px";
		    			elem_button.style.margin="1px";
		    			elem_button.onclick= function(){
	    					queryTemplateDetails(v_data.template_img);
	                    };
		    			elem_div.appendChild(elem_button);
		    			elem_li.appendChild(elem_div);
		    			var elem_button= document.createElement("button")
		    			elem_button.innerHTML = "使用";
		    			elem_button.className = "l-button";
		    			elem_button.style.width = "120px";
		    			elem_button.style.margin="1px";
		    			elem_button.onclick= function(){
		    				useTemplateDetails(v_data);
	                    };
		    			elem_div.appendChild(elem_button);
	    			}); 
	    			elem_tempplate_div.appendChild(elem_ul);
				}
		  };
   }
	
	function loadTree() {
		 tree = $("#mainTree").ligerTree({
			     data:template_type_date,
            	 idFieldName:'id',
            	 textFieldName: 'text',
                 parentIDFieldName:'pid', 
            	 checkbox: false,
            	 treeLine:false,
            	 onSelect: function(node, target){
            		if(!node.data.id){
            			return;
            		}
            		 new template_grid({
           			   url:'queryHrPageTemplate.do?isCheck=false', //获取数据API地址
           			   template_type:node.data.id
           			});

            	}
            });
	}
   function queryTemplateDetails(template_path){
		parent.$.ligerDialog.open({
			title : '图片展示',
			width : 800,
			height : 500,
			url : 'hrp/hr/sc/hrpagetemplate/hrPageTemplateImgMainPage.do?isCheck=false&template_path='+template_path,
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
   }

   function useTemplateDetails(template_data){
	     parentFrameUse().useTemplateDetails(template_data);
	     dialog.close();//关闭dialog 
	   }
</script>
</head>
<body style="padding: 0px;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout" style="height: 100%;">
		<div position="left" title="模板类型">
			<div style="width:100%;height:calc(100% - 120px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
		</div>
		<div position="center" title="图片展示">
		     <div class="tempplate_wrapper" id="tempplate_div"></div>
		</div>
	</div>
</body>
</html>
