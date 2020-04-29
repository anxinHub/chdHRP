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
     var reason;
     var etValidate;
     $(function (){
         etValidate = $.etValidate({
             config: {

             },
             items: [
                 { el: $("#reason"), required: true },
                 { el: $("#refer_value"), required: true, type: 'number' }
             ]
         })
     });  
     
     function save() {
    	var data = parent.checkData;
 		var ParamVo =[];
 		$(data).each(function (){
 			ParamVo.push(
 				this.rowData.group_id   +"@"+ 
 				this.rowData.hos_id   +"@"+ 
 				this.rowData.copy_code   +"@"+ 
 				this.rowData.year   +"@"+ 
 				this.rowData.index_code   +"@"+ 
 				this.rowData.index_name   +"@"+ 
 				this.rowData.dept_id   +"@"+
 				this.rowData.dept_name +"@"+
 				$("#refer_value").val() +"@"+
 	        	$("#reason").val() +"@"+
 				"03"  
 			) 
 		});
        ajaxPostData({
             url: "passOrDisPass.do?isCheck=false",
             data: {ParamVo : ParamVo.toString()},
             success: function(responseData) {
                 if (responseData.state == "true") {
                	 parent.query();
                 }
             }
         });
     }
     
	 function saveBudgWorkDeptDisPassReason(){
	      if(etValidate.test()){
	          save();
	      }
	 }
    </script>
  
  </head>
  
   <body>
    <div class="main">
   		<table class="table-layout" >
		<tr>
            <td class="label no-empty">不通过原因：</td>
            <td class="ipt">
                <input id="reason" name="reason" type="text" style="width : 300px"/>
            </td>
        </tr>
        <tr>
            <td class="label no-empty">参考值：</td>
            <td class="ipt">
                <input id="refer_value" name="refer_value" type="text"/>
            </td>
        </tr>
    </table>
  </div>
     
</body>
</html>
