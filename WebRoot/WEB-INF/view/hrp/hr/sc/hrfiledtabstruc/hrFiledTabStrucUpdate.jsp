<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,grid,validate" name="plugins" />
        </jsp:include>
        <script>
            var field_tab_code, field_tab_name, type_tab_code, is_innr,is_cite,is_stop, note, validate;
            $(function () {
                loadDict();
                loadForm();
            });

            function loadDict() {
              /*   field_tab_code = $("#field_tab_code").etSelect({
                    url: '',
                    defaultValue: "none"
                });

                field_tab_name = $("#field_tab_name").etSelect({
                    url: '',
                    defaultValue: "none"
                }); */

                type_tab_code = $("#type_tab_code").etSelect({
                    url: '../../queryHrTableType.do?is_copy_codeheck=false',
                    defaultValue: "${type_tab_code}"
                });

                is_innr = $("#is_innr").etSelect({
                	options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "${is_innr}"
                });
                is_stop = $("#is_stop").etSelect({
                	options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "${is_stop}"
                });
                
                is_cite = $("#is_cite").etSelect({
                	options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "${is_cite}"
                });
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#field_tab_code"),
                            required: true
                        },
                        {
                            el: $("#field_tab_name"),
                            required: true
                        },
                        {
                            el: $("#type_tab_code"),
                            required: true
                        },
                        {
                            el: $("#is_innr"),
                            required: true,
                        },{
                            el: $("#is_stop"),
                            required: true,
                        },
                        {
                            el: $("#is_cite"),
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
            	var formData = [];
            	formData.push({name:'field_tab_code' , value : $('#field_tab_code').val()})
            	formData.push({name:'field_tab_name' , value : $('#field_tab_name').val()})
            	formData.push({name:'type_tab_code' , value : $('#type_tab_code').val()})
            	formData.push({name:'is_innr' , value : $('#is_innr').val()})
            	formData.push({name:'is_stop' , value : $('#is_stop').val()})
            	formData.push({name:'is_cite' , value : $('#is_cite').val()})
            	formData.push({name:'note' , value : $('#note').val()})
            	console.log(formData);
            	ajaxPostData({
                    url: 'hrFiledTabStrucUpdate.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                        parent.tree.reAsyncChildNodes(null, 'refresh');
                    }
                })
            }
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                 <tr>
                    <td class="label no-empty" style="width:100px;">代码表编码：</td>
                    <td class="ipt">
                        <input name="field_tab_code" id="field_tab_code" type="text" value="${field_tab_code}" disabled="disabled" readonly="readonly" style="width:180px;">
                    </td>
                    <td class="label no-empty" tyle="width:100px;">代码表名称：</td>
                    <td class="ipt">
                        <input name="field_tab_name" id="field_tab_name" type="text" value = "${field_tab_name}" style="width:180px;">
                    </td>
                </tr>
                <tr>
                    <td class="label no-empty">代码表分类：</td>
                    <td class="ipt">
                        <select name="type_tab_code" id="type_tab_code" style="width:180px;"></select>
                    </td>
                    <td class="label no-empty">是否内置：</td>
                    <td class="ipt">
                        <select name="is_innr" id="is_innr" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label no-empty">是否外部引用：</td>
                    <td class="ipt">
                        <select name="is_cite" id="is_cite" style="width:180px;"></select>
                    </td>
                     <td class="label no-empty">是否停用：</td>
                    <td class="ipt">
                        <select name="is_stop" id="is_stop" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">备注：</td>
                    <td class="ipt" colspan="3">
                        <textarea name="note" id="note" style='width:488px'>${note}</textarea>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>