<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
            var new_loc_code, loc_name, super_code, is_last, is_stop, validate;
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

              /*   super_code = $("#super_code").etSelect({
                    url: '../../querySuperLocationSelect.do?isCheck=false',
                    defaultValue: "none"
                }); */

                 dept_id = $("#dept_id").etSelect({
                	  url: '../../../mat/queryHosDeptDict.do?isCheck=false',
                      defaultValue: "${dept_id}",
              });
                new_loc_code = $("#new_loc_code").etSelect({
                	  url: '../../querySuperLocationSelect.do?isCheck=false&is_last=1',
                      defaultValue: "${loc_code}"
                });
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#new_loc_code"),
                            required: true
                        },
                        {
                            el: $("#dept_id"),
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
            	formData.push({name:'dept_id' , value : $('#dept_id').val().split(',')[0]})
            	formData.push({name:'new_loc_code' , value : $('#new_loc_code').val()})
            	formData.push({name:'old_loc_code' , value : $('#old_loc_code').val()})
            	console.log(formData)
            	ajaxPostData({
                    url: 'updateAssLocationDept.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                        parent.tree.reAsyncChildNodes(null, 'refresh',false,function (){
                        	var selectId = $('#new_loc_code').val();
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
                    <td class="label no-empty">部门：</td>
                    <td class="ipt">
                <select name="dept_id" id="dept_id" type="text" style="width:180px;"  disabled="disabled" readonly="readonly"></select>
                    </td>
               	</tr>
                <tr>
                    <td class="label no-empty">位置：</td>
                    <td class="ipt">
                        <select name="new_loc_code" id="new_loc_code" type="text" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="ipt">
                        <input name="old_loc_code" id="old_loc_code"  value="${loc_code}" type="hidden" >
                    </td>
                </tr>
               
            </table>
        </div>
    </body>

    </html>