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
    	
    	var time = $("#end_date").val();
    	
    	var begin_time= Number(time)+1;
    	
        var formPara={
        		
       	acc_year:'${acc_year}',
       	
        end_time:$("#end_date").val(),
        
        begin_time:begin_time,
        copy_code:parent.liger.get("copy_code").getValue()
        
        };

        ajaxJsonObjectByUrl("updateAccYearMonth.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.loadHead();
            }
        });
    }
   
    function saveAccYearMonth(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div id="acc_time"  style="margin-left: 50px;padding-top: 50px" >
      	<div style="padding-top: 10px">结束日期:<input class="Wdate"  name="end_date" type="text" id="end_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'dd',disabledDates:['29','30','31']})"/></div>
   </div>
   </body>
</html>
