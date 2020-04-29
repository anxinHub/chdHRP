<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<style>
    .input-tip {
        color: red;
        padding-left: 40px;
    }
</style>
<script>
    var dataFormat;
    var proj_id = "${proj_id}"
    $(function() {
        loadDict();
        loadForm();
        var $wdate = document.getElementsByClassName("Wdate");
        // 给每个wdatede表单添加聚焦事件，加载日期框
        for (var i = 0, len = $wdate.length; i < len; i++) {
            $wdate[i].onfocus = function() {
                WdatePicker({
                    isShowClear: true,
                    readOnly: false,
                    dateFmt: 'yyyy-MM-dd'
                });
            }
        }
    });

    function save() {
        var formPara = {
            proj_id: proj_id,

            proj_code: $("#proj_code").val(),

            proj_name: $("#proj_name").val(),

            type_code: liger.get("type_code").getValue(),

            proj_simple: $("#proj_simple").val(),

            level_code: liger.get("level_code").getValue(),

            use_code: liger.get("use_code").getValue(),

            con_emp_id: liger.get("con_emp_id").getValue(),

            con_phone: $("#con_phone").val(),

            acc_emp_id: liger.get("acc_emp_id").getValue(), //财务负责人

            acc_phone: $("#acc_phone").val(), //财务负责人电话

            dept_id: liger.get("dept_id").getValue().split(".")[0], //填报部门

            app_emp_id: liger.get("app_emp_id").getValue(), //填报人

            app_date: $("#app_date").val(), //填报日期

            app_phone: $("#app_phone").val(), //联系电话

            email: $("#email").val(), //EMAIL

            note: $("#note").val(), //备注

            set_up_date: $("#set_up_date").val(), //立项日期

            complete_date: $("#complete_date").val(), //结题日期

            pay_end_date: $("#pay_end_date").val(), //报销终止日期

            sespend_date: $("#sespend_date").val(), //中止日期

            proj_state: liger.get("proj_state").getValue(), //项目状态

            is_stop: liger.get("is_stop").getValue(), //是否停用
            
            is_carry: liger.get("is_carry").getValue() //是否停用
        };
        ajaxJsonObjectByUrl("updateHosProj.do", formPara, function(responseData) {

            if (responseData.state == "true") {
                parent.query();
            }
        });
    }

    function loadForm() {

        $.metadata.setType("attr", "validate");
        var v = $("form").validate({
            errorPlacement: function(lable, element) {
                if (element.hasClass("l-textarea")) {
                    element.ligerTip({
                        content: lable.html(),
                        target: element[0]
                    });
                } else if (element.hasClass("l-text-field")) {
                    element.parent().ligerTip({
                        content: lable.html(),
                        target: element[0]
                    });
                } else {
                    lable.appendTo(element.parents("td:first").next("td"));
                }
            },
            success: function(lable) {
                lable.ligerHideTip();
                lable.remove();
            },
            submitHandler: function() {
                $("form .l-text,.l-textarea").ligerHideTip();
            }
        });
        $("form").ligerForm();
    }

    function saveHosProj() {
        if ($("form").valid()) {
            save();
        }
    }

    function loadDict() {
        //字典下拉框
        autocomplete("#type_code", "../../../sys/queryProjTypeDict.do?isCheck=false", "id", "text", true, true, null); //项目类别
        liger.get("type_code").setValue("${type_code}");
        liger.get("type_code").setText("${type_name}");
        autocomplete("#level_code", "../../../sys/queryProjLevelDict.do?isCheck=false", "id", "text", true, true, null); //项目级别
        liger.get("level_code").setValue("${level_code}");
        liger.get("level_code").setText("${level_name}");
        autocomplete("#use_code", "../../../sys/queryProjUseDict.do?isCheck=false", "id", "text", true, true, null); //项目用途
        liger.get("use_code").setValue("${use_code}");
        liger.get("use_code").setText("${use_name}");
        autocomplete("#con_emp_id", "../../../sys/queryEmpDict.do?isCheck=false", "id", "text", true, true, null); //项目负责人
        liger.get("con_emp_id").setValue("${con_emp_id}");
        liger.get("con_emp_id").setText("${con_emp_name}");
        autocomplete("#acc_emp_id", "../../../sys/queryEmpDict.do?isCheck=false", "id", "text", true, true, null); //财务负责人
        liger.get("acc_emp_id").setValue("${acc_emp_id}")
        liger.get("acc_emp_id").setText("${acc_emp_name}")
        autocomplete("#dept_id", "../../../sys/queryDeptDictLast.do?isCheck=false", "id", "text", true, true, null); //填报部门
        liger.get("dept_id").setValue("${dept_id}")
        liger.get("dept_id").setText("${dept_name}")
        autocomplete("#app_emp_id", "../../../sys/queryEmpDict.do?isCheck=false", "id", "text", true, true, null); //填报人
        liger.get("app_emp_id").setValue("${app_emp_id}")
        liger.get("app_emp_id").setText("${app_emp_name}")
        autocomplete("#proj_state", "../../qureyProjStateSelect.do?isCheck=false", "id", "text", true, true, null);
        liger.get("proj_state").setValue("${value_code}")
        liger.get("proj_state").setText("${value_name}")
        $("#is_stop").ligerComboBox({
            width: 200,
            data: [
                { text: '否', id: '0' },
                { text: '是', id: '1' }   
            ]
        });

        liger.get("is_stop").setValue("${is_stop}");
        
        $("#is_carry").ligerComboBox({
            width: 200,
            data: [
                { text: '否', id: '0' },
                { text: '是', id: '1' }   
            ]
        });

        liger.get("is_carry").setValue("${is_carry}");
    }
</script>
  
</head>
  
<body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td table-star"   style="padding-left:20px;">项目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_code" type="text" id="proj_code"value="${proj_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>

            <td align="right" class="l-table-edit-td table-star"  style="padding-left:20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_name" type="text" id="proj_name" value="${proj_name}"ltype="text" validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td table-star"  style="padding-left:20px;">项目类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td table-star"  style="padding-left:20px;">项目简称：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_simple" type="text" id="proj_simple" value="${proj_simple}" ltype="text" validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	 <td align="right" class="l-table-edit-td "  style="padding-left:20px;">项目级别：</td>
            <td align="left" class="l-table-edit-td"><input name="level_code" type="text" id="level_code" value="${level_code}" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目用途：</td>
            <td align="left" class="l-table-edit-td"><input name="use_code" type="text" id="use_code"   ltype="text"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" value="${con_emp_id}" ltype="text"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人电话：</td>
            <td align="left" class="l-table-edit-td"><input name="con_phone" type="text" id="con_phone" value="${con_phone}" ltype="text"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_emp_id" type="text" id="acc_emp_id"   ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务负责人电话：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_phone" type="text" id="acc_phone" value="${acc_phone}" ltype="text" /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">填报部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"   ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">填报人：</td>
            <td align="left" class="l-table-edit-td"><input name="app_emp_id" type="text" id="app_emp_id"   ltype="text"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">填报日期：</td>
            <td align="left" class="l-table-edit-td"><input name="app_date" type="text" id="app_date" class="Wdate" value="${app_date}" ltype="text"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系电话：</td>
            <td align="left" class="l-table-edit-td"><input name="app_phone" type="text" id="app_phone" value="${app_phone}" ltype="text"   /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">EMAIL：</td>
            <td align="left" class="l-table-edit-td"><input name="email" type="text" id="email" value="${email}" ltype="text"   /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" value="${note}" ltype="text"   /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">立项日期：</td>
            <td align="left" class="l-table-edit-td"><input name="set_up_date" type="text" id="set_up_date" class="Wdate" value="${set_up_date}" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结题日期：</td>
            <td align="left" class="l-table-edit-td"><input name="complete_date" type="text" id="complete_date" class="Wdate" value="${complete_date}" ltype="text"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销终止日期：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_end_date" type="text" id="pay_end_date"   class="Wdate" value="${pay_end_date}" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">中止日期：</td>
            <td align="left" class="l-table-edit-td"><input name="sespend_date" type="text" id="sespend_date" class="Wdate" value="${sespend_date}" ltype="text"   /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td table-star"  style="padding-left:20px;">项目状态：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_state" type="text" id="proj_state" value="${proj_state}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td table-star"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop"   ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td table-star"  style="padding-left:20px;">是否结转：</td>
            <td align="left" class="l-table-edit-td"><input name="is_carry" type="text" id="is_carry"   ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        
    </table>
    </form>
   
</body>
</html>
