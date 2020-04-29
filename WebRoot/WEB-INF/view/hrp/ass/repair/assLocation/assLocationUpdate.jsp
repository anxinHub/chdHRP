<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
            var loc_code, loc_name, super_code, is_last, is_stop, validate;
            $(function () {
                loadDict();
                loadForm();
            })

            function loadDict() {
                // loc_code = $("#loc_code").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });

                // loc_name = $("#loc_name").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });
				console.log(123123);
                 super_code = $("#super_code").etSelect({
                    url: '../../querySuperLocationSelect.do?isCheck=false',
                    //defaultValue: "${super_code}",
                    onInit:function(){
                    	super_code.addOptions({id:0,text:'无'});
                    	super_code.setValue('${super_code}');
                    }
                }); 

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
                    defaultValue: "${is_stop}"
                });
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#loc_code"),
                            required: true
                        },
                        {
                            el: $("#loc_name"),
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
            	formData.push({name:'loc_code' , value : $('#loc_code').val()})
            	formData.push({name:'loc_name' , value : $('#loc_name').val()})
            	formData.push({name:'super_code' , value : $('#super_code').val()})
            	//formData.push({name:'is_last' , value : $('#is_last').val()})
            	formData.push({name:'is_stop' , value : $('#is_stop').val()})
            	formData.push({name:'spell_code' , value : $('#spell_code').val()})
            	formData.push({name:'wbx_code' , value : $('#wbx_code').val()})
            	ajaxPostData({
                    url: 'updateAssLocation.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                        parent.tree.reAsyncChildNodes(null, 'refresh',false,function (){
                        	var selectId = $('#loc_code').val();
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
                    <td class="label no-empty">位置编码：</td>
                    <td class="ipt">
                        <input name="loc_code" id="loc_code" type="text" style="width:180px;" value="${loc_code}" disabled="disabled" readonly="readonly">
                    </td>
               	</tr>
                <tr>
                    
                    <td class="label no-empty">位置名称：</td>
                    <td class="ipt">
                        <input name="loc_name" id="loc_name" type="text" style="width:180px;" value="${loc_name}" >
                    </td>
                </tr>
                 <tr>
                    <td class="label no-empty">上级位置：</td>
                    <td class="ipt">
                        <select name="super_code" id="super_code" style="width:180px;" disabled="disabled"></select>
                    </td>
				</tr> 
                 <!-- <tr>
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
                <tr>
                    
                    <td class="label no-empty">拼音码：</td>
                    <td class="ipt">
                        <input name="spell_code" id="spell_code" type="text" style="width:180px;" value="${spell_code}" >
                    </td>
                </tr>
                <tr>
                    
                    <td class="label no-empty">五笔码：</td>
                    <td class="ipt">
                        <input name="wbx_code" id="wbx_code" type="text" style="width:180px;" value="${wbx_code}" >
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>