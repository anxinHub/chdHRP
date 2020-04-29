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
    <script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat,
        budg_year,
        year,
        dept_id,
        month,
        subj_code,
        validate;
     $(function (){
        loadDict();  //加载下拉框
        loadForm();
     });  
     
        function  save(){
            var formPara={
                year:year.getValue(),
                
                dept_id:dept_id.getValue(),
                
                month:month.getValue(),
                
                subj_code:subj_code.getValue(),
                
                amount:$("#amount").val(),
                
                remark:$("#remark").val()?$("#remark").val():""
            };
            
            ajaxPostData({
                url: "addBudgPreExecute.do?isCheck=false",
                data: formPara,
                success: function(responseData) {
                    resetForm();
                    parent.queryNew();
                    // 关闭当前弹框
                    // var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    // parent.$.etDialog.close(curIndex);
                }
            })
        }
        //重置表单
        function resetForm() {
            dept_id.clearItem();
            month.clearValue();
            subj_code.clearItem();
            $("input[name='amount']").val('');
            $("input[name='remark']").val('');
            $("#amount_money").val('');
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
        function loadDict() {
            ajaxPostData({
                url: "../../queryBudgYear.do?isCheck=false",
                success: function (data) {
                    // 年度
                    var date=new Date();
                    var sy=date.getFullYear();
                    year = $("#year").etDatepicker({
                        defaultDate: '${year}',
                        view: "years",
                        minView: "years",
                        dateFormat: "yyyy",
                       // minDate: data[data.length - 1].text,
                       // maxDate: data[0].text,
                        todayButton: false,
                        onChanged: function (value) {
                        	reloadSubjCode(value)
                        }
                    });
                    reloadSubjCode(sy);
                }
            });
            subj_code = $("#subj_code").etSelect({
					defaultValue: "none"
			});
           
			//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
			function reloadSubjCode(year){
				subj_code.reload({
					url:"../../queryBudgSubj.do?isCheck=false",
					para:{
						subj_type:"04",
						budg_year:year ,
						is_last : 1
					}
				})
			}
            // 部门
            dept_id = $("#dept_id").etSelect({
                url: "../../queryDept.do?isCheck=false&is_last=1",
                defaultValue: "none"
            });
            // 月份
            month = $("#month").etDatepicker({
                view: "months",
                maxView: "months",
                minView: "months",
                dateFormat: "mm",
                showNav: false,
                todayButton: false,
            })
        } 
    </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
    <table class="table-layout">
        <tr>
            <td class="label no-empty">年度：</td>
            <td class="ipt">
                <input type="text" id="year" readonly style="cursor:pointer" />                    
            </td>
            <td class="label no-empty">部门：</td>
            <td class="ipt">
                <select name="dept_id" id="dept_id" style="width:180px;" ></select>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">月份：</td>
            <td class="ipt">
                <input type="text" id="month" readonly style="cursor:pointer" />
            </td>
            <td class="label no-empty">科目编码：</td>
            <td class="ipt">
                <select name="subj_code" id="subj_code" style="width:180px;" ></select>
            </td>
        </tr>          
        <tr>
            <td class="label no-empty">金额：</td>
            <td class="ipt">
                <input name="amount" type="text" id="amount" />
            </td>
            <td class="label">说明：</td>
            <td class="ipt">
                <input name="remark" type="text" id="remark" style="width:180px;"  />
            </td>
        </tr>
    </table>
   
    </body>
</html>
