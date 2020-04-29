<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <% String path = request.getContextPath(); %>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>接口调试页面</title>
            <!-- <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/2.0.4/css/bootstrap.css"> -->
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="hr, grid, checkbox" name="plugins" />
            </jsp:include>
            <script src="<%=path%>/lib/et_components/et_plugins/etJsonLayout.min.js" type="text/javascript"></script>
            <script>
                var start_time, end_time, over_time,
                    check_post, check_get;
                var resultDom;
                $(function () {
                    resultDom = document.getElementById('code');
                    $("#btn_submit").click(function () {
                        var url = $("#ipt_url").val();
                        var type = check_post.checked ? 'post' : (check_get.checked ? 'get' : '');

                        if (validate(url, type)) {
                            request(url, type);
                        };

                    });

                    initCheckBox();
                });

                // 请求
                function request(url, type) {
                    resultDom.innerHTML = '';
                    showMessage('请求中...');
                    start_time = new Date().getTime();
                    $.ajax({
                        url: url,
                        dataType: 'json',
                        type: type,
                        success: function (data) {
                            var data_str = JSON.stringify(data);
                            initFormat(resultDom, data_str);
                        },
                        error: function (res) {
                            resultDom.innerHTML = JSON.stringify(res);
                        },
                        complete: function () {
                            if (start_time) {
                                end_time = new Date().getTime();
                                over_time = end_time - start_time;
                                document.getElementById('request_times').innerHTML = over_time + 'ms'
                            }
                        }
                    });
                }

                // 初始化复选框
                function initCheckBox() {
                    check_post = $("#check_post").etCheck({
                        checked: true,
                        onCheck: function () {
                            check_get.setUncheck();
                        }
                    });
                    check_get = $("#check_get").etCheck({
                        onCheck: function () {
                            check_post.setUncheck();
                        }
                    });
                }

                // 验证
                function validate(url, type) {
                    resultDom.innerHTML = '';
                    if (!url) {
                        showMessage('"请求地址" 不可为空!', 'red');
                    }
                    if (!type) {
                        showMessage('"请求方式" 不可为空！', 'red')
                    }
                    if (url && type) {
                        return true;
                    }
                    return false;
                }

                // 显示信息
                function showMessage(msg, color) {
                    var message = document.createElement('div');
                    message.innerHTML = msg;
                    message.style.color = color;
                    resultDom.appendChild(message);
                }
            </script>
        </head>

        <body>
            <table class='table-layout' style='width:800px;'>
                <tr>
                    <td class='label'>
                        请求地址：
                    </td>
                    <td class='ipt'>
                        <input type="text" id='ipt_url' style='width:400px;'>
                        <button id='btn_submit'>请求</button>
                        <label id='request_times' for=""></label>
                    </td>
                </tr>
                <tr>
                    <td class='label'>
                        请求方式：
                    </td>
                    <td class='ipt'>
                        <input type="checkbox" name="" id="check_post">
                        <label for="check_post">POST</label>
                        <input type="checkbox" name="" id="check_get">
                        <label for="check_get">GET</label>
                    </td>
                </tr>
                <tr>
                    <td class='label'>
                        查看格式：
                    </td>
                    <td>
                        <div>
                            <label for="QuoteKeys" class="checkbox">
                                <input type="checkbox" id="QuoteKeys" onclick="QuoteKeysClicked(this.checked)" checked="true">引号
                            </label>
                            <span id="CollapsibleViewDetail" style="visibility: visible;margin-right:10px; ">
                                <a href="javascript:void(0);" onclick="ExpandAllClicked()">展开</a>
                                <a href="javascript:void(0);" onclick="CollapseAllClicked()">叠起</a>
                                <a href="javascript:void(0);" onclick="CollapseLevel(3)">2级</a>
                                <a href="javascript:void(0);" onclick="CollapseLevel(4)">3级</a>
                                <a href="javascript:void(0);" onclick="CollapseLevel(5)">4级</a>
                                <a href="javascript:void(0);" onclick="CollapseLevel(6)">5级</a>
                                <a href="javascript:void(0);" onclick="CollapseLevel(7)">6级</a>
                                <a href="javascript:void(0);" onclick="CollapseLevel(8)">7级</a>
                                <a href="javascript:void(0);" onclick="CollapseLevel(9)">8级</a>
                            </span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td Valign='top' class='label' style='padding-top:12px'>请求结果：</td>
                    <td style='padding-top:12px'>
                        <div id="code" class='Canvas div-code' style='height:400px;overflow:auto;margin-bottom:0px;'></div>
                    </td>
                </tr>
            </table>
        </body>

        </html>