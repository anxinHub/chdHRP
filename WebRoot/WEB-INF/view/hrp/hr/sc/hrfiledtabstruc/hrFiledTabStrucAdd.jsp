<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>hrFiledTabStrucAdd</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
            var field_tab_code, field_tab_name, type_tab_code, is_innr,is_cite,is_stop, note, validate;
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
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "0"
                });
                
                is_cite = $("#is_cite").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "0"
                });
                is_stop = $("#is_stop").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "0"
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
                        },
                        {
                            el: $("#is_cite"),
                            required: true,
                        },
                        {
                            el: $("#is_stop"),
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
            	var field_tab_code= $('#field_tab_code').val().substring(0,3);
            	if(field_tab_code.toLowerCase().indexOf("DIC".toLowerCase())!=-1){
            	var formData = [];
            	formData.push({name:'field_tab_code' , value : $('#field_tab_code').val()})
            	formData.push({name:'field_tab_name' , value : $('#field_tab_name').val()})
            	formData.push({name:'type_tab_code' , value : $('#type_tab_code').val()})
            	formData.push({name:'is_innr' , value : $('#is_innr').val()})
//             	formData.push({name:'is_cite' , value : $('#is_cite').val()})
            	formData.push({name:'is_stop' , value : $('#is_stop').val()})
            	if($('#is_cite').val()==0){
            		formData.push({name:'related_sql' , value :"select  field_col_code   as id , field_col_name as text from HR_FILED_DATA where group_id = [group_id] and hos_id = [hos_id]  and field_tab_code  = '"+$('#field_tab_code').val()+"'"})	
            		formData.push({name:'query_sql' , value :" select fd.group_id, fd.hos_id, fd.field_tab_code, fd.field_col_code, fd.field_col_name , fd.super_col_code , case    when fd.super_col_code is null then '"+'TOP'+"' else sfd.field_col_name end as super_col_name, fd.spell_code, fd.wbx_code, fd.is_innr ,fd.is_stop, fd.is_last ,fd.note"
            			+" from HR_FILED_DATA fd  left join HR_FILED_DATA sfd on fd.group_id = sfd.group_id and fd.hos_id = sfd.hos_id and fd.field_tab_code = sfd.field_tab_code and fd.super_col_code = sfd.field_col_code "
            			 
            			+"where fd.group_id = [group_id] and fd.hos_id = [hos_id]  and fd.field_tab_code = '"+$('#field_tab_code').val()
            			 +"' order by fd.field_col_code "})	

            	}
            	formData.push({name:'is_cite' , value : $('#is_cite').val()})
            	formData.push({name:'note' , value : $('#note').val()})
            	ajaxPostData({
                    url: 'hrFiledTabStrucAdd.do?isCheck=false',
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
            	}else{
            		$.etDialog.error('请填写正确的代码表编码！');
   	        	 	return;
            	}
            }
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty" tyle="width:100px;">代码表编码：</td>
                    <td class="ipt">
                        <input name="field_tab_code" id="field_tab_code" type="text" style="width:180px;" placeholder="代码表编码以DIC开头">
                    </td>
                    <td class="label no-empty" tyle="width:100px;">代码表名称：</td>
                    <td class="ipt">
                        <input name="field_tab_name" id="field_tab_name" type="text" style="width:180px;">
                    </td>
                </tr>
                <tr>
                    <td class="label no-empty">代码表分类：</td>
                    <td class="ipt">
                        <select name="type_tab_code" id="type_tab_code" style="width:180px;"></select>
                    </td>
                    <td class="label no-empty">是否内置：</td>
                    <td class="ipt">
                        <select name="is_innr" id="is_innr" style="width:180px;" disabled="disabled" ></select>
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
                        <textarea name="note" id="note" style='width:488px'></textarea>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>