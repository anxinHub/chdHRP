<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
 <jsp:include page="${path}/resource.jsp">
    <jsp:param value="select,dialog,validate,grid" name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">

	function saveData() {
		var formData = [];
    	formData.push({name:'rep_code' , value : '${rep_code}'})
    	formData.push({name:'back_note' , value : $('#back_note').val()})
        
        ajaxPostData({
            url: "backAssRepair.do?isCheck=false",
            data: formData,
            success: function (res) {
                if (res.state) {
                	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.search();
                }
            }
        });
	
	}
</script>


<body>
 <div class="container"> 
        <div class="center">
            <table class="table-layout">
	                <tr>
	                    <td class="label">退回原因：</td>
	                    <td class="ipt" colspan="4" >
	                         <textarea rows="5" cols="50" name="back_note" id="back_note"></textarea>
	                    </td>
	                </tr>
	                
            </table>
        </div>
    </div>
</body>
</html>