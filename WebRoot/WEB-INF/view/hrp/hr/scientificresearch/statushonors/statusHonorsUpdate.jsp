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
        var apply_date, status_code, beg_date,end_date;
        var formValidate;
        var state = '${state}';
        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#apply_date"), required: true },
                    { el: $("#status_code"), required: true },
                    { el: $("#beg_date"), required: true },
                    { el: $("#end_date"), required: true },
                ]
            });
        };
        
        var initForm = function () {
        	apply_date = $("#apply_date").etDatepicker({
                defaultDate: '${apply_date}'
            });

            beg_date = $("#beg_date").etDatepicker({
                defaultDate: '${beg_date}'
            });
            end_date = $("#end_date").etDatepicker({
                defaultDate: '${end_date}'
            });
            status_code = $("#status_code").etSelect({
                url: "queryHonor.do?isCheck=false",
                defaultValue: "${status_code}"
            });
        };
        
        var query = function () {
            var params = [
                { name: 'apply_no', value: '${apply_no}' },
                { name: 'apply_date', value: apply_date.getValue() },
                { name: 'emp_id', value: '${emp_id}' },
                { name: 'status_code', value: status_code.getValue() },
                { name: 'beg_date', value: beg_date.getValue() },
                { name: 'end_date', value: end_date.getValue() },
                { name: 'note', value: $("#note") },
            ];
            // TODO: 路径
            grid.loadData(params, 'queryHrEmpAcadeStatusDetail.do?isCheck=false');
        };
        
        var remove = function () {
        	var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
            	if(item.rowData.seq_no){
            		param.push({
                    	emp_id: '${emp_id}',
                        apply_no : '${apply_no}',
                        status_code : item.rowData.status_code,
                        seq_no : item.rowData.seq_no,
                        filepath : item.rowData.filepath,
                        accessory : item.rowData.accessory
                    });
            	}
            });
            
            ajaxPostData({
                url: 'deleteStatusHonorsDetail.do?isCheck=false',
                data: {
                    paramVo: JSON.stringify(param)
                },
                success: function () {
                	initGrid();
                	query();
                	parent.query();
                	/* var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.query(); */
                     //close();
                },
            })
        };
        
        var save = function () {
            var isPass = grid.validateTest({}) && formValidate.test();
            if (!isPass) {
                return;
            }
            var allData = grid.getAllData();
            ajaxPostData({
                url: 'updateStatusHonors.do',
                data: {
                    gridData: JSON.stringify(allData),
                    apply_no: '${apply_no}',
                    apply_date: apply_date.getValue(),
                    emp_id: '${emp_id}',
                    status_code: status_code.getValue(),
                    beg_date: beg_date.getValue(),
                    end_date: end_date.getValue(),
                    note: $("#note").val(),
                },
                success: function () {
                	initGrid();
                	query();
                	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    //parent.$.etDialog.close(curIndex);
                    parent.query();
                },
                delayCallback: true
            })
        };
        
        var add = function () {
            grid.addRow();
        };
        
        var submit = function () {
        	var ParamVo = [];
        	var data={
        			 apply_no: '${apply_no}',
                     apply_date: apply_date.getValue(),
                     emp_id: '${emp_id}',
                     status_code: status_code.getValue(),
                     state : state,
        	};
        	
        	ParamVo.push(data)
            ajaxPostData({
                url: 'confirmStatusHonors.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	
                	state = 1;
                	initGrid();
                	query();
                	parent.query();
                	/* var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.query(); */
                    
                },
            })
        	
        };
        
        var unsubmit = function () {
        	var ParamVo = [];
        	var data={
        			 apply_no: '${apply_no}',
                     apply_date: apply_date.getValue(),
                     emp_id: '${emp_id}',
                     status_code: status_code.getValue(),
                     state : state,
        	};
        	 ParamVo.push(data)
            ajaxPostData({
                url: 'reConfirmStatusHonors.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	state = 0;
                	initGrid();
                	query();
                	parent.query();
                	/* var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.query(); */
                },
            })
        	
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
                }/* ,
                { display: '附件1', name: 'filepath', hidden:true } */
            ];
            
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                columns: columns,
                editable : (state == 0 ) ? true : false,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete'  ,disabled: state == 0 ? false : true },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' ,disabled: state == 0 ? false : true  },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' ,disabled: state == 0 ? false : true  },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'add' ,disabled: state == 0 ? false : true  },
                        { type: 'button', label: '取消提交', listeners: [{ click: unsubmit }], icon: 'cancel'  , disabled: state == 1 ? false : true }
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
            query();
        })
    </script>
</head>

<body>
    <div id="main">
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label">申请单号：</td>
                <td class="ipt">
                    <input id="apply_no" type="text" value="${apply_no}" disabled="disabled"/>
                </td>
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="apply_date" type="text" />
                </td>
                <td class="label">职工姓名：</td>
                <td class="ipt">
                    <input id="emp_id" type="text" value="${emp_name}" disabled="disabled"/>
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
                    <input id="note" type="text" value="${note}"/>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>