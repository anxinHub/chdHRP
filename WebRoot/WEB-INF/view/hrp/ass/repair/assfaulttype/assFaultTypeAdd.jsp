<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>assFaultTypeAdd</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
            var fau_code, fau_name, super_code, is_last, is_stop, validate;
            $(function () {
                loadDict();
                loadForm();
            })

            function loadDict() {
                // fau_code = $("#fau_code").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });

                // fau_name = $("#fau_name").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });

                /*  super_code = $("#super_code").etSelect({
                    url: '../../querySuperFaultTypeSelect.do?isCheck=false',
                    defaultValue: "0"
                });  */

              /*   is_last = $("#is_last").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "none"
                }); */
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
                            el: $("#fau_code"),
                            required: true
                        },
                        {
                            el: $("#fau_name"),
                            required: true
                        }/* ,
                        {
                            el: $("#super_code"),
                            required: true
                        },
                        {
                            el: $("#is_last"),
                            required: true,
                        } */,
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
            	var formData = [];
            	formData.push({name:'fau_code' , value : $('#fau_code').val()})
            	formData.push({name:'fau_name' , value : $('#fau_name').val()})
            	//formData.push({name:'super_code' , value : $('#super_code').val()})
            	//formData.push({name:'is_last' , value : $('#is_last').val()})
            	formData.push({name:'is_stop' , value : $('#is_stop').val()})
            	ajaxPostData({
                    url: 'addAssFaultType.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                        parent.tree.reAsyncChildNodes(null, 'refresh',false,function (){
                        	var selectId = $('#fau_code').val();
                        	var selectNode = parent.tree.getNodesByParam('id',selectId,null)[0];
                        	parent.tree.selectNode(selectNode, false, true)                        
                        });
                    }
                })
            }
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty">故障编码：</td>
                    <td class="ipt">
                        <input name="fau_code" id="fau_code" type="text" style="width:180px;">
                    </td>
               	</tr>
                <tr>
                    
                    <td class="label no-empty">故障名称：</td>
                    <td class="ipt">
                        <input name="fau_name" id="fau_name" type="text" style="width:180px;">
                    </td>
                </tr>
               <!--   <tr>
                    <td class="label no-empty">上级位置：</td>
                    <td class="ipt">
                        <select name="super_code" id="super_code" style="width:180px;"></select>
                    </td>
				</tr>  -->
               <!--  <tr>
                    <td class="label no-empty">是否末级：</td>
                    <td class="ipt">
                        <select name="is_last" id="is_last" style="width:180px;"></select>
                    </td>
                </tr> -->
                <tr>
                    <td class="label">是否停用：</td>
                    <td class="ipt" >
                        <select name="is_stop" id="is_stop" style="width:180px;"></select>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>