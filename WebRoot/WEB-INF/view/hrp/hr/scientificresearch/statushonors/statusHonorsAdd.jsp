<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,select,grid,upload,validate" name="plugins" />
	</jsp:include>
    <script>
        var apply_date, status_code, beg_date, end_date;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#apply_date"), required: true },
                    { el: $("#emp_id"), required: true },
                    { el: $("#status_code"), required: true },
                    { el: $("#beg_date"), required: true },
                    { el: $("#end_date"), required: true },
                ]
            });
        };
        var initForm = function () {
            apply_date = $("#apply_date").etDatepicker();
            beg_date = $("#beg_date").etDatepicker();
            end_date = $("#end_date").etDatepicker();
            status_code = $("#status_code").etSelect({
                url: "queryStatus.do?isCheck=false",
                defaultValue: "none"
            });
            emp_id = $("#emp_id").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none"
            });
        };

        var remove = function () {};
        var save = function () {
        	var b = moment(beg_date.getValue());
        	var e = moment(end_date.getValue());
        	if((b - e) > 0){
        		$.etDialog.error("任职结束日期不能早于任职开始日期");
        		return;
        	}
        	
            var isPass = grid.validateTest({}) && formValidate.test();
            if (!isPass) {
                return;
            }
            var allData = grid.getAllData();

            ajaxPostData({
                 url: 'addStatusHonors.do',
                data: {
                    gridData: JSON.stringify(allData),

                    apply_no: '${apply_no}',
                    apply_date: apply_date.getValue(),
                    emp_id: emp_id.getValue(),
                    status_code: status_code.getValue(),
                    beg_date: beg_date.getValue(),
                    end_date: end_date.getValue(),
                    note: $("#note").val(),
                },
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    <%--parent.query();--%>
                },
                delayCallback: true
            })
        };
        var add = function () {
            grid.addRow();
        };
        
        var initGrid = function () {
            var columns = [
                { display: '序号', name: 'seq_no', width: 120 },
                { display: '详细内容', name: 'content', width: 120 },
                { display: '附件', name: 'accessory', width: 200,
                    fileModel: {
                        keyField: 'file',
                        url: 'addPicture.do?isCheck=false'
                    }
                }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                columns: columns,
                editable: true,
                toolbar: {
                    items: [
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            add();

            // 上传图片
            $("#mainGrid").on('click', '.grid-file-link', function () {
                var rowIndex = $(this).attr('rowindex');
                var img_upload;
                var dialogIndex = $.etDialog.open({
                    content: '<div id="img_upload"></div>',
                    title: '文件上传',
                    width: 300,
                    height: 220,
                    btn: ['上传'],
                    btn1: function () {
                        var formData = new FormData();
                        formData.append('file', img_upload.getValue());
                        ajaxPostFormData({
                            url: 'addStatusPicture.do?isCheck=false',
                            data: formData,
                            success: function (res) {
                                grid.updateRow(rowIndex, {
                                    accessory: res.url
                                })
                                $.etDialog.close(dialogIndex);
                            }
                        })
                    },
                    success: function (el) {
                        img_upload = $("#img_upload").etUpload();
                    }
                })
            })
        }

        $(function () {
            initValidate();
            initForm();
            initGrid();
        })
    </script>
</head>

<body>
    <div id="main">
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label">申请单号：</td>
                <td class="ipt">
                    <input id="apply_no" type="text" value="系统自动生成" disabled="disabled"/>
                </td>
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="apply_date" type="text" />
                </td>
                <td class="label">职工姓名：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"> </select>
                </td>
            </tr>
            <tr>
                <td class="label">学术地位：</td>
                <td class="ipt">
                    <select id="status_code" style="width:180px;"></select>
                </td>
                <td class="label" style="width:100px;">任职开始日期：</td>
                <td class="ipt">
                    <input id="beg_date" type="text" />
                </td>
                <td class="label" style="width:100px;">任职结束日期：</td>
                <td class="ipt">
                    <input id="end_date" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">摘要：</td>
                <td class="ipt">
                    <input id="note" type="text" />
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>