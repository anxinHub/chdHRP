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
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
         
        var formPara={
            hrp_dept_code:liger.get("hrp_dept_code").getValue(),
            his_dept_code:liger.get("his_dept_code").getValue(),
            dept_type:liger.get("dept_type").getValue(),
            natur_code:liger.get("natur_code").getValue(),
         };
        ajaxJsonObjectByUrl("addSysHisDeptRef.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='hrp_dept_code']").val('');
				 $("input[name='his_dept_code']").val('');
				 $("input[name='dept_type']").val('');
				 $("input[name='natur_code']").val(''); 
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
   
    function saveHisDeptRef(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
        autocomplete("#hrp_dept_code","../queryDept.do?isCheck=false","id","text",true,true,{is_last:1});
            
        autocomplete("#his_dept_code","../querySysHisDeptDict.do?isCheck=false","id","text",true,true);

	    $("#dept_type").ligerComboBox({
            width : 200,
            data: [
                { text: '开单科室', id: '1' },
                { text: '执行科室', id: '2' },
                { text: '共用', id: '3' },
            ],  
        });

	    $("#natur_code").ligerComboBox({
            width : 200,
            data: [
                { text: '门诊', id: '01' },
                { text: '住院', id: '02' },
            ],  
        });
           
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HRP科室：</td>
                <td align="left" class="l-table-edit-td"><input name="hrp_dept_code" type="text" id="hrp_dept_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HIS科室：</td>
                <td align="left" class="l-table-edit-td"><input name="his_dept_code" type="text" id="his_dept_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类型：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_type" type="text" id="dept_type" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室性质：</td>
                <td align="left" class="l-table-edit-td"><input name="natur_code" type="text" id="natur_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
