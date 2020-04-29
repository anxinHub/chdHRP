<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,select,grid,datepicker" name="plugins" />
	</jsp:include>
    <script>
    
    var inits = function (){
        $("#save").click(function () {
            ajaxPostData({
	               url: 'saveProvidentfundratio.do',
	                data: $("#fundform").serialize(),
	                success: function () {
	                }
	        })
        });
    }

    $(function(){
    	inits();
    })
    
    </script>
</head>

<body>
    <div class="main">
    	<form id="fundform">
	        <table class="table-layout">
	            <tr>
	               <td class="label">单位缴费比例：</td>
	                <td class="ipt">
	                    <input id="unit_rate" name="unit_rate" value="${vo.UNIT_RATE }" type="number"  style="text-align: right;" /> %
	                </td>
	            </tr>
	            <tr>
	               <td class="label">个人缴费比例：</td>
	                <td class="ipt">
	                    <input id="individual_rate" name="individual_rate" type="number" value="${vo.INDIVIDUAL_RATE }" style="text-align: right;" /> %
	                </td>
	            </tr>
	            <tr>
	               <td class="label">备注：</td>
	                <td class="ipt">
	                    <input id="remark" name="remark" value="${vo.REMARK }" type="text" />
	                </td>
	            </tr>
	        </table>
		</form>
    </div>
<div class="button-group btn" style="text-align:left;margin-left:160px">
	        <button id="save">保存</button>
</div>
</body>

</html>