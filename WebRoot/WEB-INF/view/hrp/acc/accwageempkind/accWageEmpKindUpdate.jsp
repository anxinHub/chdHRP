<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
    });  
     
    function save(){
    	var formPara={
        		
    			new_wage_code: liger.get("wage_code").getValue(),
                
    	        new_kind_code: liger.get("kind_code").getValue(),
    	        
    			old_wage_code: $("#old_wage_code").val(),
                
    	        old_kind_code: $("#old_kind_code").val()
    	            
    	         };
        ajaxJsonObjectByUrl("updateAccWageEmpKind.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
                parent.$(".l-dialog,.l-window-mask").remove(); //关闭弹出层
            }
        });
    }
     
    function loadDict(){
        //字典下拉框
    	autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true,);
  		autocomplete("#kind_code", "../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0", "id", "text", true, true);
  		liger.get("wage_code").setValue('${wage_code}');
        liger.get("wage_code").setText('${wage_name}');
  		liger.get("kind_code").setValue('${kind_code}');
        liger.get("kind_code").setText('${kind_name}');
        
		$("#kind_code").ligerTextBox({width:160});
   	 	$("#wage_code").ligerTextBox({width:160});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

				<input name="old_wage_code" type="hidden" id="old_wage_code" value="${wage_code}" ltype="text"  />
				<input name="old_kind_code" type="hidden" id="old_kind_code" ltype="text" value="${kind_code}" validate="{required:true,maxlength:20}" />
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text"   validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
    </body>
</html>
