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
    	formData.push({name:'rep_code' , value : $('#rep_code').val()})
    	formData.push({name:'urg_note' , value : $('#urg_note').val()})
    	formData.push({name:'is_urg' , value : 1})
    	ajaxPostData({
            url: 'cuiAssRepirByRepCode.do?isCheck=false',
            data: formData,
            delayCallback: true,
            success: function (data) {
            	//关闭
                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
            }
        })
	
	}
</script>


<body>
 <div class="container"> 
        <div class="center">
            <table class="table-layout">
					<tr>
						<td class="label">报修单号：</td>
						<td class="ipt">
							<input id=rep_code style="width:180px" type="text"  disabled="disabled" value="${rep_code}"/>
						</td>
					</tr>
	                <tr>
	                    <td class="label">问题描述：</td>
	                    <td class="ipt" colspan="4" >
	                         <textarea rows="5" cols="50" name="urg_note" id="urg_note">${urg_note}</textarea>
	                    </td>
	                </tr>
	                
            </table>
        </div>
    </div>
</body>
</html>