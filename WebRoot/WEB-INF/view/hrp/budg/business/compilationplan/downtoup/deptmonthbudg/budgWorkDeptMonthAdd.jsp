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
    <jsp:param value="select,datepicker,dialog,etValidate" name="plugins" />
</jsp:include>
<script type="text/javascript">
     var dataFormat;
     var budg_year ;
     var index_code ;
     var year_input;
     var month_input;
     var index_code_select;
     var dept_id_select;
     
     $(function (){
         init();
         formValidate = $.etValidate({
             config: {},
             items: [
                 { el: $("#year_input"), required: true },
                 { el: $("#month_input"), required: true },
                 { el: $("#index_code_select"), required: true },
                 { el: $("#dept_id_select"), required: true },
                 { el: $("#budg_value"), required: true }
             ]
         });
     });  
     
     function init(){
 		getData("../../../../queryBudgYear.do?isCheck=false", function (data) {
 			year_input = $("#year_input").etDatepicker({
 				defaultDate: data[0].text,
 				view: "years",
 				minView: "years",
 				dateFormat: "yyyy",
 				minDate: data[data.length - 1].text,
 				maxDate: data[0].text,
 				todayButton: false,
 				onChanged:function(value){
 					budg_year = value ;
 					reloadSubjName(value);
 				}
 			});
 		});
 		month_input = $("#month_input").etDatepicker({
			view:'months',
			minView:'months',
			dateFormat:"mm",
			todayButton:false,
			showNav:false,
		});
 		index_code_select = $("#index_code_select").etSelect({
 			defaultValue: "none",
 			onChange: function(value){
 				index_code = value ;
 				reloadDeptName(value);
 			}
 		});
 		function reloadSubjName(value){
 			index_code_select.reload({
 				url:"../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=04",
 				para:{
 					budg_year:value
 				}
 			})
 		};
 		
 		dept_id_select = $("#dept_id_select").etSelect({
 			defaultValue: "none",
 		});
 		function reloadDeptName(value){
 			dept_id_select.reload({
 				url:"../../../../queryBudgIndexDeptSet.do?isCheck=false",
 				para:{
 					index_code : value
 				}
 			})
 		};
 	}

 	//ajax获取数据
 	function getData(url, callback) {
 		$.ajax({
 			url: url,
 			dataType: "JSON",
 			type: "post",
 			success: function (res) {
 				if (typeof callback === "function") {
 					callback(res);
 				}
 			}
 		})
 	};
     
 	 function save () {
         var formPara = {
    		 year : year_input.getValue(),
             index_code : index_code_select.getValue(),
             dept_id : dept_id_select.getValue(),
             month : month_input.getValue(),
             budg_value : $("#budg_value").val(),
             remark : $("#remark").val(),
             grow_rate : $("#grow_rate").val(),
             count_value:"",
             resolve_rate:"",
             last_month_carried:"",
             carried_next_month:""
         };

         formPara.grow_rate = formPara.grow_rate || 0;

         ajaxPostData({
             url: "addBudgWorkDeptMonthDown.do",
             data: formPara,
             success: function(responseData) {
            	 year_input.clear();
             	 month_input.clear();
             	 index_code_select.clearItem();
             	 dept_id_select.clearItem();
                 $("#budg_value").val('');
                 $("#remark").val('');
                 $("#grow_rate").val('');

                 parent.query();
             }
         });
     }

     function saveBudgWorkDeptMonth() {
         if (formValidate.test()) {
             save();
         }
     }
 	
    //格式化金额
    function formatMoney() {
        $("#budg_value").blur(function() {
            var value = isNaN(this.value) || this.value == "" ? this.value : fm(this.value);
            this.value = value;
        });

        function fm(s, n) {
            n = n > 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = s.split(".")[0].split("").reverse(),
                r = s.split(".")[1];
            t = "";
            for (i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
            }
            var result = t.split("").reverse().join("") + "." + r;
            return result.replace(/,/g, "");
        }
    }
  </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label no-empty">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;" />
				</td>
			</tr> 
        	<tr>
				<td class="label no-empty">预算科室：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;" />
				</td>
				<td class="label no-empty">月份： </td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
			</tr> 
        	<tr>
				<td class="label no-empty">预算值：</td>
				<td class="ipt">
					<input type="text" id="budg_value" style="width:180px;" />
				</td>
				<td class="label">增长比例：</td>
				<td class="ipt">
					<input type="text" id="grow_rate" style="width:180px;" />
				</td>
			</tr> 
        	<tr>
				<td class="label">说明：</td>
				<td class="ipt">
					<input type="text" id="remark" style="width:180px;" />
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
