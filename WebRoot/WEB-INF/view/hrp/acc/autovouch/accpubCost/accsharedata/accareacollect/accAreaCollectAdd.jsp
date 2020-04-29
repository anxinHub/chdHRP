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
     var totalNum;
     var dialog = frameElement.dialog;//调用页面的dialog对象
     var yearMonth = (dialog.get("data").year_month).split('.').join('');
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        loadTotal();
        
        $("#year_month").val(yearMonth);  
      	//设置总人数和占比只读
      	$("#year_month").ligerGetTextBoxManager().setDisabled();
   	 	$("#total").ligerGetTextBoxManager().setDisabled();
   	 	$("#percent").ligerGetTextBoxManager().setDisabled();
     }); 
     
     //获取总人数
     function loadTotal(){
    	 var formPara = {	
         		year_month: yearMonth,
    	 }
    	 
    	 ajaxJsonObjectByUrl("queryTotalArea.do?isCheck=false", formPara ,function(responseData){
             
             if(responseData.state=="true"){
 				 totalNum = responseData.data;
             }else{
            	 $.ligerDialog.error("获取总面积失败!");
             }
         });
    	 
     }
     
   	 //科室人数格式判断及总人数和占比计算
     function num(obj){
   		 var reg = /^[1-9]\d*$|^0$/;
   		 if (reg.test(obj.value) == false){//判断输入字符是否为数字
   	   		 obj.value = obj.value.replace(/\D|^0/g,"");//将非数字或者以0为开头的替换为空
   		 }else {
   			 var total = (totalNum*1) + (obj.value*1);
   			 var percent = (obj.value*1)/total*100;
   			 percent = percent.toFixed(2);//保留两位小数
   			 $("#total").val(total);
   			 $("#percent").val(percent);
   		 }
   		 
   		 if($("#dept_count").val()==""){
   			$("#total").val(totalNum);
   			$("#percent").val('');
   		 }
   	 }
     
     
     function  save(){
   
    	 if($("#year_month").val()==""){
    		 
             $.ligerDialog.error("统计年月不能为空!");
    		 
    		 return;
    	 }
    	 
    	 
    	 if(liger.get("dept_item").getValue()==""){
    		 
    		 $.ligerDialog.error("科室不能为空!");
    		 
    		 return;
    	 }
    	 
		 if($("#dept_count").val()==""){
    		 
             $.ligerDialog.error("科室面积不能为空!");
    		 
    		 return;
    	 }
		 
		var res = $("#year_month").val();
		var str = res.split('.');
		var resultStr = str.join('');

        var formPara={
        		
        		year_month:resultStr,
        		
        		dept_id:liger.get("dept_item").getValue(),
        		
        		emp_count:$("#dept_count").val(),
        		
        		ft_bl: 0,
        		
        		state: 1
            
         };
        
        ajaxJsonObjectByUrl("addAccAreaCollect.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='dept_item']").val('');
				 $("input[name='dept_count']").val('');
				 $("input[name='total']").val('');
				 $("input[name='percent']").val('');
				 loadTotal();
                 parent.query();
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
   
    function saveAccSubjType(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_item","../../accpubcost/pubcostreg/queryDeptAllInfoDict.do?isCheck=false&is_last=1","dept_id","dept_name",true,true);

     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1">
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:50px;">统计年月:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:50px;">科室:</td>
                <td align="left" class="l-table-edit-td">
                	<input  name="dept_item" type="text" id="dept_item" ltype="text" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:50px;">科室面积(㎡):</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dept_count" type="text" id="dept_count" ltype="text" onkeyup="num(this)" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:50px;">总面积(㎡):</td>
                <td align="left" class="l-table-edit-td">
                	<input name="total" type="text" id="total" ltype="text" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:50px;">占比(%):</td>
                <td align="left" class="l-table-edit-td">
                	<input name="percent" type="text" id="percent" ltype="text" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
