<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="select,validate,dialog" name="plugins" />
	</jsp:include>
    <script>
        var is_stop,duty_level_code,duty_kind_code,is_flag;
        var duty_level_codes = '${duty_level_code}' == "" ? "none" : '${duty_level_code}' ;
        var duty_kind_codes = '${kind_code}' == "" ? "none" : '${kind_code}' ;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
					{ el: $("#duty_kind_code"), required: true },
					{ el: $("#duty_level_code"), required: true },
                    { el: $("#duty_code"), required: true },
                    { el: $("#duty_name"), required: true },
                    { el: $("#is_stop"), required: true }
                ]
            });
        };
        var initForm = function () {
            
        	is_stop = $("#is_stop").etSelect({
                options: [
                    { id: 0, text: '否' },
                    { id: 1, text: '是' }
                ],
                defaultValue: '${is_stop}'
            })
            
            duty_level_code = $("#duty_level_code").etSelect({
                url: "../queryDutyLevel.do?isCheck=false",
                defaultValue: duty_level_codes,
            });
        	
            duty_kind_code = $("#duty_kind_code").etSelect({
                url: "../queryDutyKind.do?isCheck=false",
                defaultValue: duty_kind_codes,
            });
        };

        var save = function () {
            if (!formValidate.test()) {
                return;
            }
            
            if('${duty_name}' != $("#duty_name").val()){
            	is_flag = 1;
            }else{
            	is_flag = 0;
            }
            
            ajaxPostData({
                url: 'updateHosDuty.do',
                data: {
                	duty_level_code: duty_level_code.getValue(),
                	kind_code: duty_kind_code.getValue(),
                    duty_code: $("#duty_code").val(),
                    duty_name: $("#duty_name").val(),
                    is_stop: is_stop.getValue(),
                    note: $("#note").val(),
                    is_flag : is_flag
                },
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.query();
                    parent.tree.reAsyncChildNodes(null, 'refresh');
                },
                delayCallback: true
            })
        };

        $(function () {
            initValidate();
            initForm();
        })
//封装一个限制字数方法
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
    });
        
    </script>
</head>

<body>
    <table class="table-layout" style="width: 100%;">
        <tr>
            <td class="label no-empty">职务编码：</td>
            <td class="ipt">
                <input id="duty_code" type="text" value="${duty_code}" disabled="disabled" />
            </td>
        </tr>
        <tr>
            <td class="label no-empty">职务名称：</td>
            <td class="ipt">
                <input id="duty_name" type="text" value="${duty_name}" />
            </td>
        </tr>
        <tr>
            <td class="label no-empty">职务类别：</td>
            <td class="ipt">
                <input id="duty_kind_code" type="text" style="width: 180px;"/>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">职务等级：</td>
            <td class="ipt">
                <input id="duty_level_code" type="text" style="width: 180px;"/>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">是否停用：</td>
            <td class="ipt">
                <select id="is_stop" style="width: 180px;"></select>
            </td>
        </tr>
        <tr>
            <td class="label">备注：</td>
            <td class="ipt">
            <textarea id="note" rows="20" cols="25" class="wishContent" placeholder="请输入不超过100个字" maxlength="100">${note}</textarea>
            </td>
        </tr>
    </table>
</body>

</html>