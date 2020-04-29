<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        ass_id:$("#ass_id").val(),
        ass_no:$("#ass_no").val(),
        ass_code:$("#ass_code").val(),
        ass_name:$("#ass_name").val(),
        ass_type_code:$("#ass_type_code").val(),
        acc_type_code:$("#acc_type_code").val(),
        ass_unit:$("#ass_unit").val(),
        is_measure:$("#is_measure").val(),
        is_depre:$("#is_depre").val(),
        ass_depre_code:$("#ass_depre_code").val(),
        depre_years:$("#depre_years").val(),
        is_stop:$("#is_stop").val(),
        ass_spec:$("#ass_spec").val(),
        ass_model:$("#ass_model").val(),
        fac_id:$("#fac_id").val(),
        fac_no:$("#fac_no").val(),
        ven_id:$("#ven_id").val(),
        ven_no:$("#ven_no").val(),
        usage_code:$("#usage_code").val(),
        gb_code:$("#gb_code").val(),
        wbx_code:$("#wbx_code").val()
        };
        ajaxJsonObjectByUrl("updateAssNoDict.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
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
   
    function saveAssNoDict(){
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
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产ID：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_id" type="text" id="ass_id" ltype="text"  value="${ass_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产NO：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_no" type="text" id="ass_no" ltype="text"  value="${ass_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产编码：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_code" type="text" id="ass_code" ltype="text"  value="${ass_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name" ltype="text"  value="${ass_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">类别编码：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_type_code" type="text" id="ass_type_code" ltype="text"  value="${ass_type_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_type_code" type="text" id="acc_type_code" ltype="text"  value="${acc_type_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_unit" type="text" id="ass_unit" ltype="text"  value="${ass_unit}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否计量：</td>
                <td align="left" class="l-table-edit-td"><input name="is_measure" type="text" id="is_measure" ltype="text"  value="${is_measure}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否折旧：</td>
                <td align="left" class="l-table-edit-td"><input name="is_depre" type="text" id="is_depre" ltype="text"  value="${is_depre}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法编码：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_depre_code" type="text" id="ass_depre_code" ltype="text"  value="${ass_depre_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年限：</td>
                <td align="left" class="l-table-edit-td"><input name="depre_years" type="text" id="depre_years" ltype="text"  value="${depre_years}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"  value="${is_stop}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_spec" type="text" id="ass_spec" ltype="text"  value="${ass_spec}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">型号：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_model" type="text" id="ass_model" ltype="text"  value="${ass_model}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商ID：</td>
                <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text"  value="${fac_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商NO：</td>
                <td align="left" class="l-table-edit-td"><input name="fac_no" type="text" id="fac_no" ltype="text"  value="${fac_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主要供应商ID：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text"  value="${ven_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主要供应商NO：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_no" type="text" id="ven_no" ltype="text"  value="${ven_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产用途：</td>
                <td align="left" class="l-table-edit-td"><input name="usage_code" type="text" id="usage_code" ltype="text"  value="${usage_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">国标码：</td>
                <td align="left" class="l-table-edit-td"><input name="gb_code" type="text" id="gb_code" ltype="text"  value="${gb_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">五笔码：</td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text" id="wbx_code" ltype="text"  value="${wbx_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
