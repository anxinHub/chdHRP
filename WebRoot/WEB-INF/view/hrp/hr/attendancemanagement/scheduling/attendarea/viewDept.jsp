<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%String path = request.getContextPath();%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title></title>
            <style>
                .btn {
                    text-align: center;
                }

                .center .btn button {
                    margin: 4px;
                    width: 100px;
                }
            </style>
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="hr,dialog,select,tree" name="plugins" />
            </jsp:include>
            <script>
                var leftTree, rightTree;

                function initTree() {
                  
                    rightTree = $("#rightTree").etTree({
                        async: {
                        
                            enable: true,
                         url: 'queryAreaDept.do?isCheck=false',
                         otherParam: [ "attend_areacode", "${attend_areacode}"],
                         
                        },
                        callback: {
                            onClick: function (event, treeId, treeNode) {
                                var is_innr = treeNode.is_innr;
                            }
                        },
                       
                    }); 
               

                    $("#searchrightTree").keyup(function (e) { // 树快速定位
                        var _this = this;
                        searchTree({
                            tree: rightTree,
                            value: this.value,
                            callback: function (node) {
                                $(_this).focus(); //回去焦点
                            }
                        });
                    });

                   
                       $("#close").click(function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                })
                }

                $(function () {
                    initTree();
                    $('#Trees').css({'width':'266px','height': $(window).height()-10});
                })
            </script>
        </head>

        <body>
        	<div style="overflow:hidden">
                 <div class="main" style="float:left;">
                <table class="table-layout">
                    <tr>
                          <td class="label">区域名称：</td>
                        <td class="ipt">
                            <input id="attend_area_name"   value="${attend_area_name}"  type="text"  disabled />
                        </td>
                    </tr>
                    <tr>
                    <td colspan="2" align="center">
                    	<button id="close">关闭</button>
                    </td>
                    </tr>
                </table>
            </div>
            <div id="Trees" class="flex-wrap single-block" style="float:left;">
                <div class="flex-item-1">
                    <div class="search-form">
                        <label>快速定位</label>
                        <input type="text" id="searchrightTree" class="text-input">
                    </div>
                    <div id="rightTree" style="height:91%"></div>
                </div>
            </div>
    </div>
        </body>

        </html>