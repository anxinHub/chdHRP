<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="select, dialog" name="plugins" />
</jsp:include>


<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        
    });  
     
    function save(){
        var formPara={
        rep_user:'${rep_user}',
        user_code:$("#user_code").val(),
        user_name:$("#user_name").val(),
        phone1:$("#phone1").val(),
        phone2:$("#phone2").val(),
        sort_code:$("#sort_code").val()
        };
        
        ajaxPostData({
            url: 'updateAssRepairUser.do?isCheck=false',
            data: formPara,
            success: function (data) {
            	parent.queryRepUser();
            }
        })
    }
     
     
   
    function loadDict(){
      /*   //字典下拉框
        autocomplete("#phone1","../../sys/querySysSourceNature.do?isCheck=false","id","text",true,true); 
    	liger.get("phone1").setValue("${phone1}");
        liger.get("phone1").setText('${phone1} ${nature_name}');
        
        $('#phone2').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
	});
		liger.get("phone2").setValue("${phone2}");
    	
    	$("#sort_code").val('${sort_code}'); */
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   	  <div class="mian">
        <table class="table-layout" >
            <tr>
                <td class="label">用户编码：</td>
                <td class="ipt">
                  <input name="user_code" type="text" id="user_code" disabled="disabled"   value="${usercode}" style="width: 180px;" readonly="readonly" /></td>
            </tr> 
            <tr>
                <td class="label">用户名称：</td>
                <td class="ipt">
                  <input name="user_name" type="text" id="user_name"  value="${user_name}" disabled="disabled" style="width: 180px;" />
                </td>
            </tr> 
            <tr>
                <td class="label" >院内电话：</td>
                <td class="ipt" ><input name="phone1" type="text"  id="phone1" style="width: 180px;" value="${phone1}" style="width: 180px;" /></td>
                <td  ></td>
                </tr>
           <tr>
                <td class="label">联系电话 ：</td>
                <td class="ipt">
                	<input name="phone2" type="text" id="phone2" style="width: 180px;"value="${phone2}" style="width: 180px;" />
                </td>
                <td ></td>
            </tr> 
            <tr>
                <td class="label">备注：</td>
                <td class="ipt">
                	<input  id="sort_code" type="text" name="sort_code" style="width: 180px;" value="${sort_code}" disabled="disabled"></input>
                </td>
            </tr>
        </table>
    </div>
    </body>
</html>
