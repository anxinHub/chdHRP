<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        loadForm();
        
        $.post("queryAccYearMonthByMenu.do?isCheck=false",{copy_code:'${copy_code}'},function (responseData){
    	       
        	if(responseData.Rows.length>0){ 
        		
        		$("#acc_date").hide();
                
                $("#acc_year").val(responseData.Rows[responseData.Rows.length-1].name+1);
        		
        	}else{
        		
        		$("#acc_date").show();
        	}
        	
       	},"json");
        
     });  
     
     function  save(){
    	 
    	 d = new Date();
    	 
         nowYear = +d.getFullYear(); 
         
         if($("#acc_year").val() == ""){
        	 $.ligerDialog.error('请按顺序添加会计年度,不能为空');
			 return;
         }
         
    	 var acc_year = $("#acc_year").val(); 
    	 
    	 var end_day = $("#end_day").val();
    	 
    	 var chkObjs = document.getElementsByName("rdo");
    	 
    	 if(chkObjs[1].checked){
    
 		if(end_day==""){
    			 
    			 $.ligerDialog.error('结束日期不能为空');
    			 
    			 return;
    		 }
    		 
    	 }
     	
     	var begin_time= null;
     	
     	if( end_day !="" ){
     		
     		begin_time= Number(end_day)+1;
     		
     	}else{
     		
     		begin_time=0;
     		
     	}
     	
        var formPara={
           
           acc_year:acc_year,
           
           acc_begin:begin_time,
           
           acc_end:end_day,
           
           copy_code:'${copy_code}'
            
         };
        ajaxJsonObjectByUrl("addAccYearMonth.do",formPara,function(responseData){
            
        	if(responseData.state=="true"){
            	
                parent.loadTree();
            
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
   
    function saveAccYearMonth(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){

     }
    
    function chargeRadio(){
    	
    	var chkObjs = document.getElementsByName("rdo");
    	
    	for(var i=0;i<chkObjs.length;i++)
    	  {
    	     if(chkObjs[i].checked)
    	      {
    	    	 if(chkObjs[i].value=="0"){
    	    		 $("#acc_time").hide();
    	    	 }else{
    	    		 $("#acc_time").show();
    	    	 }
    	      }      
    	  }
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div id="acc_date" style="margin-left: 100px;padding-top: 10px;display: none;">
     	 <div>年度:<input class="Wdate"  name="acc_year" type="text" id="acc_year" ltype="text"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></div>
   </div>
   <div style="margin-left: 100px">
		   <div style="padding-top: 10px"><input type="radio" name ="rdo" onclick="chargeRadio()"  checked="checked" value="0">自然月</div>
		   <div style="padding-top: 10px"><input type="radio" name ="rdo" onclick="chargeRadio()" value="1">非自然月</div>
   </div>
   <div id="acc_time"  style="display: none;margin-left: 100px;padding-top: 10px" >
      	<div style="padding-top: 10px">结束日期:<input class="Wdate"  name="end_day" type="text" id="end_day" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'dd',disabledDates:['29','30','31']})"/></div>
   </div>
   
    </body>
</html>
