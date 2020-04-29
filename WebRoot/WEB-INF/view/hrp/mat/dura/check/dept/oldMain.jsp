<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    
    <style>
        body {
            overflow: hidden;
        }
        .Wdate {
            width: 90px;
        }
        input {
            border: 1px solid #aecaf0;
            height: 20px;
        }
        .table-star:before{
            content: "*";
            color: red;
        }
        .button-group {
            text-align: right;
            margin-bottom: 10px;
        }
        .button-group > button {
            padding: 2px 15px;
            margin-right: 40px;
            cursor: pointer;
            font-weight: bold;
            background: #e0edff;
            border: 1px solid #a3c0e8;
        }
        .button-group > button:hover {
            background: #ffbe76;
        }
    </style>
</head>

<body>
    <!-- 耐用品科室盘点 -->
    <div id="pageloading" class="l-loading"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td">盘点日期：</td>
            <td align="left" class="l-table-edit-td">
                <table>
                    <tr>
                        <td align="left">
                            <input id="begin_confirm_date" class="Wdate" name="begin_confirm_date" type="text" ltype="text"/>
                        </td>
                        <td align="left" class="l-table-edit-td">至：</td>
                        <td align="left" class="l-table-edit-td">
                            <input id="end_confirm_date" class="Wdate" name="end_confirm_date" type="text" ltype="text"/>
                        </td>
                    </tr>
                </table>
            </td>

            <td align="right" class="l-table-edit-td table-star">盘点科室：</td>
            <td align="left" class="l-table-edit-td">
                <input name="store_id" type="text" id="store_id" ltype="text" />
            </td>

            <td align="right" class="l-table-edit-td">状态：</td>
            <td align="left" class="l-table-edit-td">
                <input name="state" type="text" id="state" ltype="text"/>
            </td>
        </tr>
    </table>

    <div class="button-group">
        <button id="query" type="button" accessKey="Q">查询（<u>Q</u>）</button>
    </div>

    <div id="mainGrid"></div>
    
    <script src="<%=path%>/lib/hrp/mat/mat.js"></script>
    <script>
        var grid;

        // 加载下拉框
        var loadDict = function () {
            autocomplete("#store_id", "../../../queryMatDept.do?isCheck=false", "id", "text", true, true);

            autoCompleteByData("#state", matDuraCheck_state.Rows, "id", "text", true, true);
        }

        // 加载表头
        var loadHead = function () {
            grid = $("#mainGrid").ligerGrid({
                columns: [
                    { display: "盘点单据", name: "", width : 140 },
                    { display: "摘要", name: "", width: 120 },
                    { display: "盘点科室", name: "", width: 120 },
                    { display: "盘点日期", name: "", width: 120 },
                    { display: "盘点人", name: "", width: 100 },
                    { display: "审核人", name: "", width: 100 },
                    { display: "审核日期", name: "", width: 100 },
                    { display: "状态", name: "", width: 100 }
                ],
                toolbar: {
                    items: [
                        { text: "添加（<u>A</u>）", id: "add", click: add_open, icon: "add" },
                        { line: true },
                        { text: "审核（<u>S</u>）", id: "audit", click: audit, icon: "audit" },
                        { line: true },
                        { text: "取消审核（<u>U</u>）", id: "unaudit", click: unAudit, icon: "unaudit" },
                        { line: true },
                        { text: "删除（<u>D</u>）", id: "delete", click: remove, icon: "delete" }
                    ]
                },
                width: '100%',
                height: '100%',
                checkbox: true
            })
        }

        // 查询事件
        var query = function () {
            console.log("click query");
        }

        // 确认事件
        var audit = function () {
            console.log("click audit");
        }

        // 取消确认事件
        var unAudit = function () {
            console.log("click unAudit");
        }

        // 添加事件
        var add_open = function () {
            console.log("click add");
        }

        // 移除事件
        var remove = function () {
            console.log("click remove");
        }

        // 查询点击事件
        $("#query").click(function () {
            query();
        })

         // 绑定快捷键
        BindKeyBoard([
            { ctrl: true, keyCode: "Q", fn: function () { query() } },
            { ctrl: true, keyCode: "S", fn: function () { audit() } },
            { ctrl: true, keyCode: "U", fn: function () { unAudit() } },
            { ctrl: true, keyCode: "A", fn: function () { add_open() } },
            { ctrl: true, keyCode: "D", fn: function () { remove() } }
        ])
        
        $(function () {
            var $wdate = document.getElementsByClassName("Wdate");
            // 给每个wdatede表单添加聚焦事件，加载日期框
            for(var i = 0, len = $wdate.length; i < len; i++) {
                $wdate[i].onfocus = function(){
                    WdatePicker({ isShowClear:true, readOnly:false,dateFmt:'yyyy-MM-dd' });
                }
            }

            loadDict();
            loadHead();
        })
    </script>
</body>
</html>
