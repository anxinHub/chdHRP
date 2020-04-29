<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     $(function (){
		loadDict()//加载下拉框
		loadForm();
       
    			
         });
         
  /*        $("#subj_code").blur(function(){
         	var map ={
         			budg_year:$("#budg_year").val(),
         			subj_code:$("#subj_code").val(),
         			rules:$("#rules").val()
         		};
         	ajaxJsonObjectByUrl("getSuperCode.do?isCheck=false",map,function(responseData){
         		if(responseData.super_code == 0){
         			$("#super_code").val(responseData.super_code);
         		}else{
         			$("#super_code").val(responseData.super_code+' '+ responseData.super_name);
         		}
    	    		
    	    	$("#subj_level").val(responseData.subj_level);
         	});
         }) */
      
       
     
     function  save(){
    	
        var formPara={
        
            type_code:liger.get("type_code").getValue(),
            subj_code:'${subj_code}'
            
         };
        
        ajaxJsonObjectByUrl("budgBathUpdate.do?isCheck=false",formPara,function(responseData){
            
           
                parent.query();
            
        });
    }
     
 function loadForm(){
    
 }       
   
    function saveIncomeBudgSubj(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        //字典下拉框
    	autocomplete("#type_code","../../../queryBudgTypeCode.do?isCheck=false","id","text",true,true,"",false,"",160,20);
    
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" style="padding-top:20px">
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目类别<font color="red" >*</font>:</b></td>
              <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            </tr> 
			
        </table>
    </form>
   
    </body>
</html>
