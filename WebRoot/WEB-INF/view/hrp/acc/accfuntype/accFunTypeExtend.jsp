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
    	
    	var hos_code = liger.get("hos_code").getValue();
    	
    	var copy_code = liger.get("copy_code").getValue();
    	
    	var acct_year = liger.get("year_month").getValue();

    	if("" == hos_code ){
    		
    		$.ligerDialog.error('请选择医院');
    		
    		return;
    		
    	}else if(""==copy_code){
    		
    		$.ligerDialog.error('请选择账套');
    		
    		return;
    		
    	}else if(""==acct_year){
    		$.ligerDialog.error('请选择会计年度');
    		
    		return;
    	}
        var formPara={
        hos_code:liger.get("hos_code").getValue(),	
        copy_id:liger.get("copy_code").getValue(),
        acct_yearE : liger.get("year_month").getValue()
        };
        ajaxJsonObjectByUrl("extendAccFunType.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	
                parent.query();
                
            }
            
        });
        
    }
   
    function extendAccFunType(){
            save();
    }
    function loadDict(){
        //字典下拉框
        autocomplete("#hos_code","../queryHosInfoDict.do?isCheck=false","id","text",true,true);
        autocomplete("#copy_code","../queryCopyDict.do?isCheck=false","id","text",true,true);
        autocomplete("#year_month","../queryAcctYearDict.do?isCheck=false","id","text",true,true);
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin-left: 120px">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医院：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_code" type="text" id="hos_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账套：</td>
                <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
