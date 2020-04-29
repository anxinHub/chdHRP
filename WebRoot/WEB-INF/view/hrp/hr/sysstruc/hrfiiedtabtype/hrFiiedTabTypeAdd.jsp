<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
            var type_filed_code, type_filed_name, note, validate;
            $(function () {
                loadDict();
                loadForm();
            })

            function loadDict() {
                type_filed_code = $('#type_filed_code');
                type_filed_name = $('#type_filed_name');
                note = $('#note');
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#type_filed_code"),
                            required: true
                        },
                        {
                            el: $("#type_filed_name"),
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
            	var formData = [];
            	formData.push({name:'type_filed_code' , value : type_filed_code.val()})
            	formData.push({name:'type_filed_name' , value : type_filed_name.val()})
            	formData.push({name:'note' , value : note.val()})
            	//console.log(formData);
            	ajaxPostData({
                    url: 'addHrFiiedTabType.do',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
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
                    <td class="label no-empty">类别代码：</td>
                    <td class="ipt">
                        <input name="type_filed_code" id="type_filed_code" style="width:180px;" type="text"/>

                    </td>
                    <td class="label no-empty">类别名称：</td>
                    <td class="ipt">
                        <input name="type_filed_name" id="type_filed_name" style="width:180px;" type="text"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">备注：</td>
                    <td class="ipt" colspan="3">
                        <textarea name="note" id="note" style='width:488px' class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>