<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>hrTabStrucUpdate</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script src="${path}/lib/hrp.js" type="text/javascript"></script>
        <script>
            var type_tab_code, is_innr, validate;
            $(function () {
                loadDict();
                loadForm();
            })

            function loadDict() {
            	type_tab_code = $("#type_tab_code").etSelect({
                    url: '../../queryHrTableType.do?isCheck=false',
                    defaultValue: "${type_tab_code}"
                });

            	is_innr = $("#is_innr").etSelect({
                    options: [{id:0,text:'否'},{id:1,text:'是'}],
                    defaultValue: "${is_innr}"
                });

            }

            function loadForm() {
                validate = $.etValidate({
                    items: [
                        {
                            el: $("#tab_name"),
                            required: true
                        },
                        {
                            el: $("#type_tab_code"),
                            required: true
                        },
                        {
                            el: $("#is_innr"),
                            required: true,
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
            	var formData = {
            		'tab_code':$('#tab_code').val(),
            		'tab_name':$('#tab_name').val(),
            		'note':$('#note').val(),
            		'type_tab_code':type_tab_code.getValue(),
            		'is_innr':is_innr.getValue()
            	};
            	ajaxPostData({
                    url: 'updateHrTableStruc.do',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	parentFrameUse().tree.reload();
                    }
                })
            }
        </script>
    </head>

    <body>
        <div class="main">
        	<form>
	            <table class="table-layout">
	                <tr>
	                    <td class="label no-empty">数据表编码：</td>
	                    <td class="ipt">
	                        <input type="text" name="tab_code" id="tab_code" disabled="disabled" value="${tab_code }" style="width:180px;"/>
	                    </td>
	                    <td class="label no-empty">数据表名称：</td>
	                    <td class="ipt">
	                        <input type="text" name="tab_name" id="tab_name" value="${tab_name }" style="width:180px;"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label no-empty">数据表类型：</td>
	                    <td class="ipt">
	                        <select name="type_tab_code" id="type_tab_code" style="width:180px;"></select>
	                    </td>
	                    <td class="label no-empty">是否内置：</td>
	                    <td class="ipt">
	                        <select name="is_innr" id="is_innr" disabled="disabled" style="width:180px;"></select>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label">数据表说明：</td>
	                    <td class="ipt" colspan="3">
	                        <textarea name="note" id="note" style='width:488px'>${note }</textarea>
	                    </td>
	                </tr>
	            </table>
            </form>
        </div>
    </body>

    </html>