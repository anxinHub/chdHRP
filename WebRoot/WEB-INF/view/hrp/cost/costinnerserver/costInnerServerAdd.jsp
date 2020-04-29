<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 var server_dept = liger.get("server_dept_id").getValue();
         
         var server_by_dept = liger.get("server_by_dept_id").getValue();
    	 
         var formPara={

 				acc_year:$("#year_month").val().substring(0,4),
                 
                 acc_month:$("#year_month").val().substring(4,6),
        	        
        	  server_dept_id:server_dept.split(".")[0],
        	        
        	  server_dept_no:server_dept.split(".")[1],
        	        
        	  server_by_dept_id:server_by_dept.split(".")[0],
        	        
        	  server_by_dept_no:server_by_dept.split(".")[1],
            
        	  server_item_code: liger.get("server_item_code").getValue(),
            
           	  server_num:$("#server_num").val()
            
         };
        
        ajaxJsonObjectByUrl("addCostInnerServer.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("input[name='year_month']").val('');
				 $("input[name='server_dept_id']").val('');
				 $("input[name='server_dept_no']").val('');
				 $("input[name='server_by_dept_id']").val('');
				 $("input[name='server_by_dept_no']").val('');
				 $("input[name='server_item_code']").val('');
				 $("input[name='server_num']").val('');
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
   
    function saveCostInnerServer(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	var param = {
     			  type_code:"('03')"
              };
    	var se_param = {
     			  type_code:"('03','02','01')"
              };
            //字典下拉框
         /*
        	2016/11/3 lxj
        	服务科室和被服务科室科室过滤写反
        	修改参数
        */
    	autocomplete("#server_by_dept_id","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true,se_param);
    	autocomplete("#server_dept_id","../queryDeptDictNoLast.do?isCheck=false","id","text",true,true,param);
    	autocomplete("#server_item_code","../queryServerItemDict.do?isCheck=false","id","text",true,true); 
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">服务科室：</td>
                <td align="left" class="l-table-edit-td"><input name="server_dept_id" type="text" id="server_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">受益科室：</td>
                <td align="left" class="l-table-edit-td"><input name="server_by_dept_id" type="text" id="server_by_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">服务项目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="server_item_code" type="text" id="server_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">服务量：</td>
                <td align="left" class="l-table-edit-td"><input name="server_num" type="text" id="server_num" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
