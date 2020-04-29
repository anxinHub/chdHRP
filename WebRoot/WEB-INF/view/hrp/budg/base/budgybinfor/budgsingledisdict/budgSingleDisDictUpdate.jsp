<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">
    
    $(function (){
        loadDict();
    });  
     
    function save(){
    	if($("form").valid()){
    		var formPara={
   				disease_code : $("#disease_code").val(),
   				disease_name : $("#disease_name").val(),
   				is_stop : liger.get("is_stop").getValue() 
   			};
   	        ajaxJsonObjectByUrl("updateBudgSingleDisDict.do?isCheck=true",formPara,function(responseData){  
   	            if(responseData.state=="true"){
   	                parent.query();
   	            }
   	        });
    	}
        
    }
    
    function loadDict(){
    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true, "",false, '${budgSingleDisDict.is_stop}');
    	$("#disease_code").ligerTextBox({width:160});
    	$("#disease_name").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
        $("#disease_code").ligerComboBox({disabled:true,cancelable: false});
        
        $("#save").ligerButton({click: save, width:90});
        $("#close").ligerButton({click: this_close, width:90});
     }  
    
  	//关闭
    function this_close(){
    	frameElement.dialog.close();
    }
</script>	
</head>
  
<body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>病种编码<font color="red">*</font></b>
                </td>
                <td align="left" class="l-table-edit-td">
                	<input name="disease_code" type="text" id="disease_code" ltype="text"  value="${budgSingleDisDict.disease_code}" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>病种名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="disease_name" type="text" id="disease_name" ltype="text"  value="${budgSingleDisDict.disease_name}" validate="{required:true,maxlength:50}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}"/>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<button id ="save" accessKey="B"><b>保存（<u>B</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
    </body>
</html>
