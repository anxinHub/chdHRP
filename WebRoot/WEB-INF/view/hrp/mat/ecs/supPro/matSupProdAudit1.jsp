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
	var inv_id;
	$(function() {
		loadDict();
		loadForm();
	})
	  function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#inv_id"),
                            required: true
                        } 
                    ]
                })
            }
	function saveData(data) {
		if($('#inv_id').val()==""){
			return;
		}

		
		var formData = [];
    	formData.push({name:'check_state' , value :'2'})
    	formData.push({name:'prod_id' , value : data.prod_id})
    	formData.push({name:'spec_id' , value : data.spec_id})
    	formData.push({name:'chos_id' , value : data.chos_id})
    	formData.push({name:'csup_id' , value : data.csup_id})
    	formData.push({name:'sid' , value : data.sid})
    	formData.push({name:'inv_id' , value : $('#inv_id').val().split(',')[0]})
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
	function loadDict() {
	 	var invId='${inv_id}';
			
		inv_id = $("#inv_id").etSelect({
			url : '../../../mat/queryMatInv.do?isCheck=false',
			width: 200,
			defaultValue : invId
		});
		
		if(invId!="none"){
			inv_id.addOptions({ text: parent.inv_code+" "+parent.inv_name, id: invId });
			inv_id.setValue(invId);
		}
	
	}
</script>
</head>
<body>
	<div style="margin-top: 50px;">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">材料名称：</td>
				<td><input type="text" id="inv_id" name="inv_id" style="width:380px;"></td>
			</tr>
		</table>
	</div>
</body>
</html>