<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="select,validate,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
	$(function() {
		loadForm();
	})
	  function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#check_note"),
                            required: true
                        } 
                    ]
                })
            }
	function saveData(data) {
		if (validate.test()) {
        } 
		if( $("#check_note").val().length<2){
			$.etDialog.error('最少输入两个字');
			return;
		}
		var formData = [];
    	formData.push({name:'check_state' , value :'3'})
    	formData.push({name:'prod_id' , value : data.prod_id})
    	formData.push({name:'spec_id' , value : data.spec_id})
    	formData.push({name:'chos_id' , value : data.chos_id})
    	formData.push({name:'csup_id' , value : data.csup_id})
    	formData.push({name:'sid' , value : data.sid})
    	formData.push({name:'inv_id' , value : data.inv_id})
    	formData.push({name:'check_note' , value : $('#check_note').val()})
		 ajaxPostData({
             url: "addMatSupProdSpecInv.do?isCheck=false",
             data: formData,
             success: function (res) {
                 if (res.state == "true") {
                	  parent.search();
                 }
             }
         })
	}
</script>
</head>
<body>
	<div style="margin-top: 50px; margin-left: 20px;">
		<table class="table-layout">
			<tr >
				<td  class="no-empty">审核反馈信息：</td>
				<td><textarea rows="15" cols="50" id="check_note" style="height:100px"></textarea></td>
			</tr>
		</table>
	</div>
</body>
</html>