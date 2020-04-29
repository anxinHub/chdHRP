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

              /*   super_code = $("#super_code").etSelect({
                    url: '../../querySuperLocationSelect.do?isCheck=false',
                    defaultValue: "none"
                }); */

                 dept_id = $("#dept_id").etSelect({
                	  url: '../../../mat/queryHosDeptDict.do?isCheck=false&pageSize=500',
                      defaultValue: "${dept_id}",
              });
                loc_code = $("#loc_code").etSelect({
                	  url: '../../queryNotExistsLocationSelect.do?isCheck=false&is_last=1&dept_id='+$("#dept_id").val().split(',')[0],
                      defaultValue: "none",
                      checkboxMode:true
                });
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#loc_code"),
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
            	formData.push({name:'loc_code' , value : $('#loc_code').val()})
            	ajaxPostData({
                    url: 'addAssLocationDept.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                       /*  parent.tree.reAsyncChildNodes(null, 'refresh',false,function (){
                        	var selectId = $('#loc_code').val();
                        	var selectNode = parent.tree.getNodesByParam('id',selectId,null)[0];
                        	parent.tree.selectNode(selectNode, false, true)                        
                        }); */
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
                        <input name="dept_id" id="dept_id" type="text" style="width:180px;" disabled="disabled" readonly="readonly" value="1">
                    </td>
               	</tr>
                <tr>
                    
                    <td class="label no-empty">位置：</td>
                    <td class="ipt">
                        <input name="loc_code" id="loc_code" type="text" style="width:180px;">
                    </td>
                </tr>
                <!-- <tr>
                    <td class="label no-empty">上级位置：</td>
                    <td class="ipt">
                        <select name="super_code" id="super_code" style="width:180px;"></select>
                    </td>
				</tr> -->
               <!--  <tr>
                    <td class="label no-empty">是否末级：</td>
                    <td class="ipt">
                        <select name="is_last" id="is_last" style="width:180px;"></select>
                    </td>
                </tr> -->
                <!-- <tr>
                    <td class="label">是否停用：</td>
                    <td class="ipt" >
                        <select name="is_stop" id="is_stop" style="width:180px;"></select>
                    </td>
                </tr> -->
            </table>
        </div>
    </body>

    </html>