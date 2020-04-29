<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog,etValidate" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    function save() {
        var formPara = {
            paramVo:"${paramVo}",
            op: $("#op").val(),
            state: "${state}",
            type:"${type}"
        };

        ajaxPostData({
            url: "auditOrUnAudit.do?isCheck=false",
            data: formPara,
            success: function(responseData) {
            	var curIndex = parent.$.etDialog.getFrameIndex(window.name);              
                parent.query(); 
                parent.$.etDialog.close(curIndex);
            }
        });
    }


    function audit() {
    	if($('#op').val()==''){
    		$.etDialog.error("请填写审核意见");
    		return;
    	}
    	save();
    }
    	
</script>
</head>

<body>
    <div class="mian">
        <table class="table-layout">
          <tr>
                <td class="ipt">
                <%if(request.getParameter("type").equals("1")){ %>
                	审核意见：
                <%}else{ %>
                	确认意见:
                <%} %>	
                	</td>
          </tr>
          <tr>
                <td>
                    <textarea id="op"  rows=50 cols=80></textarea>
                </td>               
            </tr>
        </table>
    </div>
</body>

</html>