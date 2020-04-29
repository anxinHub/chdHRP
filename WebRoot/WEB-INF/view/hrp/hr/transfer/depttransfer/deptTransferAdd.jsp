<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%String path = request.getContextPath();%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Insert title here</title>
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="select, datepicker, hr, dialog, upload,validate" name="plugins" />
            </jsp:include>
            <style>
                .title {
                    padding: 5px;
                }
            </style>
            <script>
                var adjust_date, emp_select, emp_type_select, photo_file, aft_dept_id   ;
                var formValidate;
                var user_hide_data = {};

                $(function () {
                    init()
                    initValidate()
                });
                var initValidate = function () {
                    formValidate = $.etValidate({
                    	config: {},
                    	items: [
            				{ el: $("#emp_select"), required: true },
            				{ el: $("#adjust_date"), required: true },
            			/* 	{ el: $("#adjust_reason"), required: true }, */
                        ]
                    });
                };
                function init() {
                    adjust_date = $("#adjust_date").etDatepicker();

                    emp_select = $("#emp_select").etSelect({
                        url: "../../queryPerson.do?isCheck=false",
                        defaultValue: 'none',
                        onChange: function (value) {
                            if (value) {
                                ajaxPostData({
                                    url: '../depttransfer/queryHosEmpDept.do?isCheck=false&emp_id=' + value,
                                    data: {},
                                    success: function (res) {
                                    	 user_hide_data = {
                                                 emp_name: res.emp_name || '',
                                                 emp_id: res.emp_id || '',
                                                 dept_id: res.dept_id || '',
                                                 dept_no: res.dept_no || '',
                                                 dept_name: res.dept_name || '',
                                                 kind_code: res.kind_code || '',
                                                 kind_name: res.kind_name || '',
                                             };
                                    	  $("#kind_code").val(res.kind_name || '');
                                          $("#bef_dept_id").val(res.dept_name || '');
                                        

                                        if (photo_file) {
                                            photo_file.setValue(res.photo)
                                        }
                                    }
                                })
                            } else {
                                photo_file.setValue('')
                            }
                        }
                    });

                    aft_dept_id = $("#aft_dept_id").etSelect({
                        url: "../../queryHosAftDeptSelect.do?isCheck=false",
                        defaultValue: 'none'
                    })

                    photo_file = $("#photo_file").etUpload();

                    initEvent();
                }

                function initEvent() {
                    $("#btn_close").click(function () {
                    	  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                            
                            var parentFrameName = parent.$.etDialog.parentFrameName;
                            var parentWindow = parent.window[parentFrameName];
                            parentWindow.query(); 
                            parent.$.etDialog.close(curIndex);
					    
                    });

                    $("#btn_save").click(function () {
                    	if (!formValidate.test()) {
                			return;
                		};
                        var formData = new FormData();
                        formData.append("adjust_date", adjust_date.getValue());
                        formData.append("emp_id", user_hide_data.emp_id);
                        formData.append("bef_dept_id", user_hide_data.dept_id);
                        formData.append("bef_dept_no", user_hide_data.dept_no);
                        formData.append("adjust_reason", $("#adjust_reason").val());
                        formData.append("aft_dept_id", aft_dept_id.getValue());
                        ajaxPostFormData({
                            url: 'addHrDeptTransfer.do',
                            data: formData,
                            success: function (data) {
                            	  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		                            
		                            var parentFrameName = parent.$.etDialog.parentFrameName;
		                            var parentWindow = parent.window[parentFrameName];
		                            parentWindow.query(); 
		                            parent.$.etDialog.close(curIndex);
							    
                              /*   console.log(data);
                                $.etDialog.success('添加成功') */
                            }
                        });
                    });
                }
            </script>
        </head>

        <body>
            <div class="flex-wrap">
                <table class="table-layout flex-item-1">
                    <tr>
                        <td class="label">调动编号：</td>
                        <td class="ipt">
                            <input type="text" id="transfer_code"  value="系統自动生成" disabled>
                        </td>
                        <td class="label">调动日期：</td>
                        <td class="ipt">
                            <input type="text" id="adjust_date">
                        </td>
                        <td class="label"><font size="2" color="red">*</font>职工名称：</td>
                        <td class="ipt">
                            <select name="" id="emp_select" style="width:180px;"></select>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">职工类别：</td>
                        <td class="ipt">
                            <input  id="kind_code" type="text" disabled/>
                        </td>
                        <td class="label"></td>
                        <td class="ipt"></td>
                        <td class="label"></td>
                        <td class="ipt"></td>
                    </tr>
                    <tr>
                        <td class="label"><!-- <font size="2" color="red">*</font> -->调动原因：</td>
                        <td class="ipt" colspan="5" style="padding-top:8px;">
                            <textarea name="" id="adjust_reason" style="width:790px;"></textarea>
                        </td>

                    </tr>
                </table>
                <div class="upload-photo-form">
                    <div id="photo_file"></div>
                </div>
            </div>
            <div class="flex-wrap" style="height:130px;">
                <div class="flex-item-1">
                    <div class="title">
                        <<调整前>>
                    </div>
                    <div class="single-block">
                        <table class="table-layout ">
                            <tr>
                                <td class="label">
                                    部门：
                                </td>
                                <td class="ipt">
                                    <input type="text" id="bef_dept_id" disabled>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="flex-item-1">
                    <div class="title">
                        <<调整后>>
                    </div>
                    <div class="single-block">
                        <table class="table-layout ">
                            <tr>
                                <td class="label">
                                    <font size="2" color="red">*</font>部门：
                                </td>
                                <td class="ipt">
                                    <select name="" id="aft_dept_id" style="width:180px;"></select>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="button-group">
                <button id="btn_save">保存</button>
                <button id="btn_close">关闭</button>
            </div>
        </body>

        </html>