<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="validate,dialog,select" name="plugins" />
	</jsp:include>
    <script>
	    var is_stop,duty_code,station_level_code,dept_code,dept_name,duty_name,dept_id;
	    var formValidate;
	    var initValidate = function () {
	        formValidate = $.etValidate({
	            items: [
	                { el: $("#station_code"), required: true },
	                { el: $("#dept_code"), required: true },
	                { el: $("#duty_code"), required: true },
	                { el: $("#station_level_code"), required: true },
	                { el: $("#is_stop"), required: true }
	            ]
	        });
	    };
	    
	    var initForm = function () {
	    	dept_code= $("#dept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                onChange: function (id) {
                	if(id!=null){
                		dept_id = id.split("@")[1];
                    	dept_name = dept_code.getText();
                    	if(duty_code.getText()!=null && duty_code.getText()!="" && duty_code.getText()!="undefined"){
                        	if(dept_name!=""){
                        		$("#station_name").val(dept_code.getText()+duty_code.getText());
                        	}
                        }
                	}
                },
            });
	    	
	    	dept_name = dept_code.getText();
	    	dept_id = dept_code.getValue().split("@")[1];
	    	duty_code = $("#duty_code").etSelect({
                url: "../../queryDutyCode.do?isCheck=false",
                defaultValue: "none",
                onChange: function (id) {
                    if(duty_code.getText()!=null && duty_code.getText()!="" && duty_code.getText()!="undefined"){
                    	if(dept_name!=""){
                    		$("#station_name").val(dept_name+duty_code.getText());
                    	}
                    }
                },
            });
	    	
	    	station_level_code = $("#station_level_code").etSelect({
                url: "../../queryStationLevel.do?isCheck=false",
            });
	    	
	    	is_stop = $("#is_stop").etSelect({
	            options: [
	                { id: 0, text: '否' },
	                { id: 1, text: '是' }
	            ]
	        });
            
	    };
	
	    var save = function () {
	        if (!formValidate.test()) {
	            return;
	        }
	        
	        ajaxPostData({
	            url: 'addStationInfo.do',
	            data: {
	                station_code : $("#station_code").val(),
	                station_name : $("#station_name").val(),
	                dept_id : dept_id,
	                duty_code : duty_code.getValue(),
	                station_level_code : station_level_code.getValue(),
	                is_stop: is_stop.getValue(),
	                note:$("#note").val()
	            },
	            delayCallback:true,
	            success: function () {
	                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
	                parent.query();
	            }
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
    <div >
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label no-empty">岗位编码：</td>
                <td class="ipt">
                    <input id="station_code" type="text" style="width: 180px;"/>
                </td>
			</tr>
			<tr>
                <td class="label no-empty">部门名称：</td>
                <td class="ipt">
                    <select id="dept_code" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label no-empty">职务名称：</td>
                <td class="ipt">
                    <select id="duty_code" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">岗位名称：</td>
                <td class="ipt"> 
                    <input id="station_name" type="text" style="width: 180px;" />
                </td>
			</tr>
            <tr>
                <td class="label no-empty">岗位等级：</td>
                <td class="ipt">
                    <select id="station_level_code" style="width: 180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*</font>是否停用：</td>
                <td class="ipt">
                    <select id="is_stop" style="width: 180px;"></select>
                </td>
			</tr>
            <tr>
                <td class="label">备注：</td>
	            <td class="ipt">
	                    <textarea id="note" rows="20" cols="25" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>
	            </td>
            </tr>
            
        </table>
    </div>
</body>

</html>