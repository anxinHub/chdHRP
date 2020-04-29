<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
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
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="dialog,magnify,jquery_print" name="plugins" />
        </jsp:include>
        <script>
            var checkboxItem;
            $(function () {
                // 从父页面拿到图片节点
                var imgsNode = top.$.etDialog.getFrameName('imgsNode');
                loadImg(imgsNode);

                //  图片展示器配置
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


            })

            function loadImg(imgsNode) {
                var imageW = $('<div class="image-set"></div>');
                $(imgsNode).each(function (index, item) {
                    var imgSrc = $(item).attr('src');
                    var img = $('<a class="magnify" href="' +
                        imgSrc + '"><img class="images" src="' + imgSrc + '"/></a>');
                    imageW.append(img);
                });
                $('.container').append(imageW);
            }
        </script>

        <title>Document</title>
    </head>

    <body>
        <div class="container flex-column">
            <div id="images-view" class="images-view">

            </div>
        </div>
    </body>

    </html>