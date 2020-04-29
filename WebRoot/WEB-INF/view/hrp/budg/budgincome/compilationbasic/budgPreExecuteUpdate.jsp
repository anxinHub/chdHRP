<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/static_resource.jsp">
        <jsp:param value="select,datepicker,etValidate,dialog,grid" name="plugins" />
    </jsp:include>
   <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat,
        validate,
        subj_code,
        dept_id;
    $(function (){
        loadDict();
        loadForm();
    });  
     
    function save(){
        var formPara={
    		year:$('#year').val(),
            
            dept_id:dept_id.getValue(),
             
            month:$('#month').val(),
             
            subj_code:subj_code.getValue(),
             
            amount:$("#amount").val(),
             
            remark:$("#remark").val()?$("#remark").val():""
        };

        ajaxPostData({
            url: "updateBudgPreExecute.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
                if(responseData.state=="true"){
                    parent.queryNew();
                    // 关闭当前弹框
                    /* var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex); */
                }
            }
        })
    }
     
    function loadForm(){
        validate = $.etValidate({
            config: {
                // tipTime: 1000
            },
            items: [
                {
                    el: $("#year"),
                    required: true
                },
                {
                    el: $("#dept_id"),
                    required: true
                },
                {
                    el: $("#month"),
                    required: true
                },
                {
                    el: $("#subj_code"),
                    required: true,
                },
                {
                    el: $("#amount"),
                    required: true,
                    test: /\d/
                }
            ]
        })
    }       
   
    function saveBudgPreExecute(){
        if(validate.test()) {
            save();
        }
    }
    function loadDict(){
        //科目编码
        subj_code = $("#subj_code").etSelect({
            url: "../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+"${year}" + "&is_last=1&key=${subj_code}",
            defaultValue: '${subj_code}',
        });

        // 部门
        dept_id = $("#dept_id").etSelect({
                url: "../../queryDept.do?isCheck=false&is_last=1",
                defaultValue: '${dept_id}'
        });
    }   
    </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
    <table class="table-layout">
        <tr>
            <td class="label no-empty">年度：</td>
            <td class="ipt">
               <input type="text" id="year" value='${year}' disabled style="cursor:pointer" />
            </td>
            <td class="label no-empty">部门：</td>
            <td class="ipt">
               <select name="dept_id" id="dept_id" style="width:180px;" disabled ></select>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">月份：</td>
            <td class="ipt">
               <input name="month" type="text" id="month" value='${month}' disabled  />
            </td>
            <td class="label no-empty" >科目编码：</td>
            <td class="ipt">
               <select name="subj_code" id="subj_code" style="width:180px;" disabled ></select>    
            </td>
        </tr>
        <tr>
            <td class="label no-empty">金额：</td>
            <td class="ipt">
               <input type="text" id="amount"  value="${amount}" />
            </td>
            <td class="label">说明：</td>
            <td class="ipt">
               <input type="text" id="remark" value="${remark}" />
            </td>
        </tr>
    </table>

    </body>
</html>
