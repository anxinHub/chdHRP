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
        var apply_date, honor_code, get_date;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#apply_date"), required: true },
                    { el: $("#emp_id"), required: true },
                    { el: $("#honor_code"), required: true },
                    { el: $("#get_date"), required: true },
                ]
            });
        };
        var initForm = function () {
            apply_date = $("#apply_date").etDatepicker();
            get_date = $("#get_date").etDatepicker();
            honor_code = $("#honor_code").etSelect({
                url: "queryHonor.do?isCheck=false",
                defaultValue: "none"
            });
            emp_id = $("#emp_id").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none"
            });
        };

        var remove = function () {
        	var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            // 删除行
            grid.deleteRows(selectData);
        };
        var save = function () {
        	//申请日期
        	var beginDate = apply_date.getValue();
        	//获得日期
			var endDate = get_date.getValue();

			var d1 = new Date(beginDate.replace(/\-/g, "\/"));
			var d2=new Date(endDate.replace(/\-/g, "\/"));
			if(beginDate!=""&&endDate!=""&&d1 <d2){
			  $.etDialog.error("申请日期不能早于获得日期！");
			  return false;
			}

            var isPass =  formValidate.test();
            if (!isPass) {
                return;
            }
            //grid.validateTest({}) 
            var allData = grid.getAllData();
            var isPa =  grid.validateTest({allData}); 
             if (!isPa) {
                return;
            }
            ajaxPostData({
                url: 'addAcademicHonors.do',
                data: {
                    gridData: JSON.stringify(allData),
                    apply_no: '${apply_no}',
                    apply_date: apply_date.getValue(),
                    emp_id: emp_id.getValue(),
                    honor_code: honor_code.getValue(),
                    get_date: get_date.getValue(),
                    note: $("#note").val(),
                },
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.query();
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
                <td class="label">学术荣誉：</td>
                <td class="ipt">
                    <select id="honor_code" style="width:180px;"></select>
                </td>
                <td class="label">获得日期：</td>
                <td class="ipt">
                    <input id="get_date" type="text" />
                </td>
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