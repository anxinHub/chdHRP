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
            var main_tab_code, main_col_code, affi_tab_code, affi_col_code, cas_nature, up_auto, is_sql, validate;
            $(function () {
                loadDict();
                loadForm();
            })
            function loadDict() {
            	console.log("${hrCastrans}");
            	main_tab_code = $("#main_tab_code").etSelect({
                    url: '../../queryHrTabStruc.do?isCheck=false',
                    defaultValue: "${hrCastrans.main_tab_code}",
                    onChange: function (value) {
    					//reloadMainColSelect(value)
    				}
                });
            	
            	main_col_code = $("#main_col_code").etSelect({
                    url: '../../queryHrColStruc.do?isCheck=false',
                    defaultValue: "${hrCastrans.main_col_code}"
                });
            	
            	/* function reloadMainColSelect(value){
            		main_col_code.reload({
    					url: "../../queryHrColStruc.do?isCheck=false",
    					para: { tab_code: value }
    				});
            	} */
            	
            	affi_tab_code = $("#affi_tab_code").etSelect({
                    url: '../../queryHrTabStruc.do?isCheck=false',
                    defaultValue: "${hrCastrans.affi_tab_code}",
                    onInit: function (value) {
    					//reloadCopySelect(value)
    				},
                    onChange: function (value) {
    					reloadAffiColSelect(value)
    				}
                });
				
            	affi_col_code = $("#affi_col_code").etSelect({
                    url: '../../queryHrColStruc.do?isCheck=false&tab_code='+'${hrCastrans.affi_tab_code}',
                    defaultValue: "${hrCastrans.affi_col_code == null ? 'none' : hrCastrans.affi_col_code}"
                });
            	
            	function reloadAffiColSelect(value){
            		affi_col_code.reload({
    					url: "../../queryHrColStruc.do?isCheck=false",
    					para: { tab_code: value }
    				});
            	}
            	
            	cas_nature = $("#cas_nature").etSelect({
            		options: [{id:'mf',text:'主到附'},{id:'fm',text:'附到主'},{id:'rt',text:'双向实时'}],
                    defaultValue: "${hrCastrans.cas_nature  == null ? 'none' : hrCastrans.cas_nature}"
                });
            	
            	up_auto = $("#up_auto").etSelect({
            		options: [{id:0,text:'手动'},{id:1,text:'自动'}],
                    defaultValue: "${hrCastrans.up_auto == null ? 'none' : hrCastrans.up_auto}"
                });
            	
            	is_sql = $("#is_sql").etSelect({
            		options: [{id:0,text:'否'},{id:1,text:'是'}],
                    defaultValue: "${hrCastrans.is_sql == null ? 'none' : hrCastrans.is_sql}"
                });
            }

            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#main_tab_code"),
                            required: true
                        },
                        {
                            el: $("#main_col_code"),
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
            	var formData = {
            		main_tab_code:main_tab_code.getValue(), 
            		main_col_code:main_col_code.getValue(), 
            		affi_tab_code:affi_tab_code.getValue(), 
            		affi_col_code:affi_col_code.getValue(), 
            		cas_nature:cas_nature.getValue(), 
            		up_auto:up_auto.getValue(), 
            		cas_level:$('#cas_level').val(), 
            		is_sql:is_sql.getValue(),	
            		cas_sql:$('#cas_sql').val(),
            		note:$('#note').val()
            	};
            	ajaxPostData({
                    url: 'updateHrCastrans.do',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                        parent.loadTree();
                    }
                })
            }
           /*  //封装一个限制字数方法
            var checkStrLengths = function (str, maxLength) {
                var maxLength = maxLength;
                var result = 0;
                if (str && str.length > maxLength) {
                    result = maxLength;
                } else {
                    result = str.length;
                }
                return result;
            }

            //监听输入
            $(".wishContent").on('input propertychange', function () {

                //获取输入内容
                var userDesc = $(this).val();

                //判断字数
                var len;
                if (userDesc) {
                    len = checkStrLengths(userDesc, 100);
                } else {
                    len = 0
                }

                //显示字数
                $(".wordsNum").html(len + '/100');
            }); */
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty" style="width:100px;">主数据表编码：</td>
                    <td class="ipt">
                        <select name="main_tab_code" id="main_tab_code" style="width:180px;" disabled></select>
                    </td>
                    <td class="label no-empty" style="width:100px;">主数据列名称：</td>
                    <td class="ipt">
                    <input type="text" name="main_col_code" id="main_col_code" style="width:180px;" value="${hrCastrans.main_col_code}" disabled>
                    </td>
                </tr>
                <tr>
                    <td class="label">附数据表编码：</td>
                    <td class="ipt">
                        <select name="affi_tab_code" id="affi_tab_code" style="width:180px;"></select>
                    </td>
                    <td class="label">附数据列名称：</td>
                    <td class="ipt">
                        <select name="affi_col_code" id="affi_col_code" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">级联性质：</td>
                    <td class="ipt">
                        <select name="cas_nature" id="cas_nature" style="width:180px;"></select>
                    </td>
                    <td class="label">自动更新：</td>
                    <td class="ipt">
                        <select name="up_auto" id="up_auto" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">事务别级：</td>
                    <td class="ipt">
                       <input type="text" name="cas_level" id="cas_level" style="width:180px;" value="${hrCastrans.cas_level}" />
                    </td>
                    <td class="label">自定义级联：</td>
                    <td class="ipt">
                        <select name="is_sql" id="is_sql" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">更新SQL：</td>
                    <td class="ipt" colspan="3">
                        <textarea name="cas_sql" id="cas_sql" style='width:510px;height:80px'>${hrCastrans.cas_sql}</textarea>
                    </td>
                </tr>
                <tr>
                    <td class="label">备注：</td>
                    <td class="ipt" colspan="3">
                        <textarea name="note" id="note" style='width:510px;height:80px' class="wishContent" placeholder="请输入不超过100个字" maxlength="100">${hrCastrans.note}</textarea>
                    </td>
                </tr>
            </table>
        </div>
    </body>

    </html>