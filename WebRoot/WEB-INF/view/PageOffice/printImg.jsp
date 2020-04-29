<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=path%>/lib/magnify/magnifycss.css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
<script src="<%=path%>/lib/jquery/jquery.print.js"></script>
<script src="<%=path%>/lib/magnify/magnify.js"></script>
<style>
	.container {
		height: 100%;
		padding: 10px;
	}
	.flex-column {
		display: flex;
		flex-direction: column;
	}
	.images-view {
		flex: 1;
	}
</style>
<script>
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var imgs = dialog.get("data").imgs;
	var imgsNode = dialog.get("data").imgsNode;
	$(function (){
		//加载图片
		loadImg(imgsNode);
        $('#images-view').height($(window).height() - $('.image-set').height() - 70);
		$('body').show();
		
		//图片展示器配置
		$('.magnify').magnify({
			footToolbar: [
				'print',
				'zoomIn',
				'zoomOut',
				'prev',
				'next',
				'actualSize',
			],
			print: function (self) {
				var $printImg = self.$stage.find('.magnify-image');
				$printImg.print();
			}
		});
	});

	function loadImg(imgsNode) {
		var imageW = $('<div class="image-set"></div>');
		$(imgsNode).each(function (index, item) {
			var imgSrc = $(item).attr('src');
			var img = $('<a class="magnify" href="' +imgSrc + '"><img class="images" src="' + imgSrc + '"/></a>');
			imageW.append(img);
		});

		$('.container').append(imageW);
	}
</script>

<title>图片打印</title>
</head>

<body style="display:none">
	<div class="container flex flex-column">
		<div id="images-view" class="images-view">
		</div>
	</div>
</body>

</html>