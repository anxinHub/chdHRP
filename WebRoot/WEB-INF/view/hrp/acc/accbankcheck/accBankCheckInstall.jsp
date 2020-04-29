<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
     var dataFormat;
     
     var state=0;
     
     var bank_id=0;
     
     var flag = false;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        load();

     });  
     
     function load(){
    	 
    	 var acc_year = '${yearMonth}';
         
         var formPara={
                 
                 acc_year:acc_year.substring(0, 4),
                 
                 acc_month:acc_year.substring(5, 7),
                 
                 cash_flag:'1'
            };
         
 		ajaxJsonObjectByUrl("queryAccTellDayByCode.do?isCheck=false",formPara,function(responseData){
             
             if(responseData.name=="1"){
             	
             	$("#bal").ligerTextBox({ disabled:true });
             	
             	flag = true;
             }
         });
 		
 		$("#subj_code").bind("change",function(){
 	    	
 			 var formPara={
 		                
 		                subj_code:liger.get("subj_code").getValue()
 		                
 		           };
 		        
 				ajaxJsonObjectByUrl("queryInstallMoney.do?isCheck=false",formPara,function(responseData){
 					if(responseData.bank_id != "null"){
 						
 						state = 1;
 						
 						bank_id = responseData.bank_id

 						$("#bal").val(formatNumber(responseData.money,2,1));
 						
 					}else{
 						
 						$("#bal").val("0.00");
 					}
 		            
 		        });
 			
 	    });
 		 
 		liger.get("subj_code").setValue('${subj_code}');
 	    liger.get("subj_code").setText('${subj_name}');
 	    $("#bal").val(dialog.get("data").bal);
     }
     
     function cc(s){
    	 if(/[^0-9\.]/.test(s)) return "0.00";
    	 s=s.replace(/^(\d*)$/,"$1.");
    	 s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
    	 s=s.replace(".",",");
    	 var re=/(\d)(\d{3},)/;
    	 while(re.test(s))
    	 s=s.replace(re,"$1,$2");
    	 s=s.replace(/,(\d\d)$/,".$1");
    	 return  s.replace(/^\./,"0.")
    	 }
     
     function  save(){
    	if(flag){
    		$.ligerDialog.error('有日结数据不允许修改');
    		return;
    	}
    	
        var formPara={
          
           subj_code:liger.get("subj_code").getValue(),
            
           bal:$("#bal").val().replace(/,|\s/g,''),
           
           state:state,
           
           bank_id:bank_id
            
         };

        ajaxJsonObjectByUrl("addAccBankCheckInstall.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                //parent.query(); 
                $("#bal",parent.document).val(formatNumber($("#bal").val(),2,1));
            }
        });
    }
     
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
 }       
   
    function installAccTell(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
            var param={
            		subj_nature_code:'03',
					is_last:1
            }
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,param,'','','','',"300px");
     } 
    
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行对账单初始余额：</td>
                <td align="left" class="l-table-edit-td"><input name="bal" type="text" id="bal" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
