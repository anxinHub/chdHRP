<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>hrTabStrucAdd</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid,checkbox" name="plugins" />
        </jsp:include>
        <script src="${path}/lib/hrp.js" type="text/javascript"></script>
        <script>
            var type_tab_code, is_innr, validate;
            var is_group_id, is_hos_id, is_copy_code;
            $(function () {
                loadDict();
                loadForm();
            })

            function loadDict() {
            	type_tab_code = $("#type_tab_code").etSelect({
                    url: '../../queryHrTableType.do?is_copy_codeheck=false',
                    defaultValue: "none"
                });

            	is_innr = $("#is_innr").etSelect({
                    options: [{id:0,text:'否'},{id:1,text:'是'}],
                    defaultValue: "0"
                });

                /* is_group_id = $("#is_group_id").etCheck({
                    checked: true
                });
                is_hos_id = $("#is_hos_id").etCheck({
                    checked: true
                }); */
                /* is_copy_code = $("#is_copy_code").etCheck({
                    checked: true
                }); */
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
            	var tab_code = $('#tab_code').val();
                var reg = /(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/;
            	if(!reg.test(tab_code)){
            		$.etDialog.error("数据表编码非法，请重新命名。");
            		return;
            	}
            	var formData = {
                		'tab_code':$('#tab_code').val(),
                		'tab_name':$('#tab_name').val(),
                		'note':$('#note').val(),
                		'type_tab_code':type_tab_code.getValue(),
                        'is_innr':is_innr.getValue(),
                        /* 'is_group_id': is_group_id.checked ? '1' : '0',
                        'is_hos_id': is_hos_id.checked ? '1' : '0' */
                	};
            	ajaxPostData({
                    url: 'addHrTableStruc.do',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                        //父级查询
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
	                        <input type="text" name="tab_code" id="tab_code" value="${tab_code}" style="width:180px;"/>
	                    </td>
	                    <td class="label no-empty">数据表名称：</td>
	                    <td class="ipt">
	                        <input type="text" name="tab_name" id="tab_name" style="width:180px;"/>
	                    </td>
	                </tr>
	                <tr>
	                	<td class="label no-empty">数据表类型：</td>
	                    <td class="ipt">
	                        <select name="type_tab_code" id="type_tab_code" style="width:180px;"></select>
	                    </td>
	                    <td class="label no-empty">是否内置：</td>
	                    <td class="ipt">
	                        <select name="is_innr" id="is_innr" style="width:180px;" disabled="disabled" ></select>
	                    </td>
	                </tr>
                    <!-- <tr>
                        <td class="label">是否包含：</td>
                        <td class="ipt">
                            <input id="is_group_id" type="checkbox">
                            <label for="is_group_id"> 集团 </label>
                            <input id="is_hos_id" type="checkbox">
                            <label for="is_hos_id"> 医院 </label>
                            <input id="is_copy_code" type="checkbox">
                            <label for="is_copy_code"> 帐套 </label>
                        </td>
                    </tr> -->
	                <tr>
	                    <td class="label">数据表说明：</td>
	                    <td class="ipt" colspan="3">
	                        <textarea name="note" id="note" style='width:488px'></textarea>
	                    </td>
                    </tr>
	            </table>
            </form>
        </div>
    </body>

    </html>