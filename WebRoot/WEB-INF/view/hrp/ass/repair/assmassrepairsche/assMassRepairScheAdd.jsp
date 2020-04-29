<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>assLocationAdd</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,validate,grid" name="plugins" />
        </jsp:include>
        <script>
            var rep_user, rep_team_code, super_code, phone1, phone2, validate;
            $(function () {
                loadDict();
                loadForm();
            })

            function loadDict() {
                 rep_user = $("#rep_user").etSelect({
                     url: '../../../ass/queryUserDict.do?isCheck=false&rep_team_code='+'${rep_team_code}',
                     defaultValue: "none"
                 });

                 rep_team_code = $("#rep_team_code").etSelect({
                     url: '../../../ass/queryRepTeam.do?isCheck=false',
                     defaultValue: "${rep_team_code}"
                 });

              /*   super_code = $("#super_code").etSelect({
                    url: '../../querySuperLocationSelect.do?isCheck=false',
                    defaultValue: "none"
                }); */

              /*   phone1 = $("#phone1").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "none"
                }); */
              /*   phone2 = $("#phone2").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "none"
                }); */
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#rep_user"),
                            required: true
                        },
                        {
                            el: $("#rep_team_code"),
                            required: true
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
            	formData.push({name:'rep_user' , value : $('#rep_user').val()})
            	formData.push({name:'rep_team_code' , value : $('#rep_team_code').val()})
            	//formData.push({name:'super_code' , value : $('#super_code').val()})
            	formData.push({name:'acc_year' , value : '${acc_year}'})
            	formData.push({name:'acc_month' , value : '${acc_month}'})
            	ajaxPostData({
                    url: 'addAssMassRepairSche.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                      /*   parent.tree.reAsyncChildNodes(null, 'refresh',false,function (){
                       	var selectId = $('#rep_user').val();
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
                    <td class="label no-empty">维修班组：</td>
                    <td class="ipt">
                        <select name="rep_team_code" id="rep_team_code" type="text" style="width:180px;" disabled="disabled"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label no-empty">维修工程师：</td>
                    <td class="ipt">
                        <select name="rep_user" id="rep_user" type="text" style="width:180px;"></select>
                    </td>
               	</tr>
               <!--  <tr>
                    <td class="label no-empty">上级位置：</td>
                    <td class="ipt">
                        <select name="super_code" id="super_code" style="width:180px;"></select>
                    </td>
				</tr>  -->
               <!--  <tr>
                    <td class="label no-empty">院内电话：</td>
                    <td class="ipt">
                        <input name="phone1" id="phone1" style="width:180px;" type="text"/>
                    </td>
                </tr> 
                <tr>
                    <td class="label">联系电话：</td>
                    <td class="ipt" >
                        <input name="phone2" id="phone2" style="width:180px;" type="text"/>
                    </td>
                </tr> -->
            </table>
        </div>
    </body>

    </html>