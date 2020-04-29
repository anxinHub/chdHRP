<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>hrFiiedTabStrucAdd</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
            $(function () {
                loadDict();
                loadForm();
            })

            function loadDict() {
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#field_tab_code"),
                            required: true
                        },
                        {
                            el: $("#cite_sql"),
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
            	if($('#cite_sql').val().toLowerCase().indexOf('code'.toLowerCase()) !=-1 ||$('#cite_sql').val().toLowerCase().indexOf('field_col_code'.toLowerCase()) !=-1 && $('#cite_sql').val().toLowerCase().indexOf('field_col_name '.toLowerCase())!=-1 ){
            	var formData = [];
            	formData.push({name:'field_tab_code' , value : $('#field_tab_code').val()})
            	formData.push({name:'cite_sql' , value : $('#cite_sql').val()})
            	formData.push({name:'query_sql' , value : $('#query_sql').val()})
            	formData.push({name:'note' , value : $('#note').val()})
            	ajaxPostData({
                    url: 'saveHrFiiedView.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        //parent.search();
                        //parent.tree.reAsyncChildNodes(null, 'refresh');
                    }
                })
            	}else{
            		$.etDialog.error('请填写正确的sql！');
   	        	 	return;
            	}
            }
        </script>
    </head>

    <body>
        <div class="main"  style=" overflow:scroll;height:100%;">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty">代码表编码：</td>
                    <td class="ipt">
                        <input name="field_tab_code" id="field_tab_code" disabled="disabled" type="text" style="width:180px;" value="${field_tab_code }">
                    </td>
                    <td class="label">代码表名称：</td>
                    <td class="ipt">
                        <input name="field_tab_name" id="field_tab_name" disabled="disabled" type="text" style="width:180px;" value="${field_tab_name }">
                    </td>
                </tr>
               
                <tr>
                	<td class="label">外部引用SQL：</td>
                    <td class="ipt" colspan="10">
                        <textarea name="cite_sql" id="cite_sql" style="width:800px;height:300px;">${cite_sql }</textarea>
                    </td>
                </tr>
                  <tr>
                	<td></td>
                	<td colspan="10">
                		<span>参数说明：列别名 field_col_code(编码)、field_col_name(名称)<span/><br/>
                		<span style="margin-left:20px">条件参数可用 @group_id、@hos_id、@copy_code、@user_id<span/><br/>
                		<!-- <span style="margin-left:20px">检索参数 @key<span/><br/> -->
                		<span>参考格式：select t.type_code field_col_code, t.type_name field_col_name <span/></br>
                		<span style="margin-left:20px">from hr_emp_type t <span/></br>
                		<span style="margin-left:20px">where t.group_id = @group_id and t.hos_id = @hos_id <span/>
                		
                	</td>
                </tr>
                <tr>
                	<td class="label">查询SQL：</td>
                    <td class="ipt" colspan="10">
                        <textarea name="query_sql" id="query_sql" style="width:800px;height:300px;">${query_sql }</textarea>
                    </td>
                </tr>
               
                <tr>
                    <td class="label">备注：</td>
                    <td class="ipt" colspan="3">
                        <textarea name="note" id="note" style="width:800px">${note }</textarea>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>