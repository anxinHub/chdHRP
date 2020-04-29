<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>storeTypeAdd</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
        var validate;
            $(function () {
                loadForm();
            })

        

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#store_type_code"),
                            required: true
                        },
                        {
                            el: $("#store_type_name"),
                            required: true
                        }
                    ]
                })
               
             
            }

            function saveData() {
                if (validate.test()) {
                    save();
                }
            }


            function save() {
                var saveParam = {
                		store_type_code: $('#store_type_code').val(),
                		store_type_name: $('#store_type_name').val(),
                       
                        note: $('#note').val(),
                    };

                    ajaxPostData({
                        url: 'addHrStoreType.do',
                        data: saveParam,
                        delayCallback:true,
                        success: function () {
                            var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                            parent.$.etDialog.close(curIndex);
                            parent.search();
                        }
                    })

            }
            //封装一个限制字数方法
            var checkStrLengths = function (str, maxLength) {
                var maxLength = maxLength;
                var result = 0;
                if (str && str.length > maxLength) {
                    result = maxLength;
                } else {
                    result = str.length;
                }
                return result;
            }

            //监听输入
            $(".wishContent").on('input propertychange', function () {

                //获取输入内容
                var userDesc = $(this).val();

                //判断字数
                var len;
                if (userDesc) {
                    len = checkStrLengths(userDesc, 100);
                } else {
                    len = 0
                }

                //显示字数
                $(".wordsNum").html(len + '/100');
            });
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty">类型代码：</td>
                    <td class="ipt">
                        <input id="store_type_code" type="text"/>
                    </td>
                    <td class="label no-empty">类型名称：</td>
                    <td class="ipt">
                        <input id="store_type_name" type="text"/>
                    </td>
                </tr>
              
                <tr>
                    <td class="label">备注：</td>
                    <td class="ipt" colspan="3">
                        <textarea  id="note" style='width:488px' class="wishContent" placeholder="请输入不超过100个字" maxlength="100"> </textarea>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>