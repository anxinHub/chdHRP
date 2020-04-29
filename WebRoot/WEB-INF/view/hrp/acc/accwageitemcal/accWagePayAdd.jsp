<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
     
     function  save(){
    	 
    	 var wage_name =$("#wage_code").val();
    	 
    	 $.ligerDialog.confirm('确认要将'+ '${wage_name}'+'合并到【'+wage_name+'】吗？', function (yes){
         	
    		 if(yes){
    			 
         		 var formPara={

         		           wage_code:liger.get("wage_code").getValue(),
         		           
         		           wage_where:"${wage_code}",
         		           
         		           acc_year:'${acc_year}',
         		           
         		           acc_month:'${acc_month}',
         		           
         		           wage_name:'${wage_name}'
         		            
         		         };
         		        
         		        ajaxJsonObjectByUrl("../accwagepay/combineAccWagePayDesc.do?isCheck=false",formPara,function(responseData){
         		            
         		            if(responseData.state=="true"){
         		                parent.query();
         		            }
         		        });
         	}
         }); 
    	 
    }       
   
    function saveWageItemCal(){

            save();

   }
    function loadDict(){
    	
    	var para={
    			
    			wage_code_where:"${wage_code}"
    			
    	};
    		
    	autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true,para);
  
     } 
    
    </script>

  </head>
  
   <body style="padding:10px;margin:0;">
   <div id="pageloading" class="l-loading" style="display:none"></div>
   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
		   <td align="right" class="l-table-edit-td" >工资套：</td>
		   <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
		    <td align="left"></td>
		</tr> 
	</table>
   
    </body>
</html>
