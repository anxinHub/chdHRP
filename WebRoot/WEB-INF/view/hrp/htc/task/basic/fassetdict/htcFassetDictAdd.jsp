<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
    
     function  save(){
      
        var formPara={
           asset_code:$("#asset_code").val(),
           asset_name:$("#asset_name").val(),
           asset_model:$("#asset_model").val(),
           asset_type_code:liger.get("asset_type_code").getValue(),
           prim_value:$("#prim_value").val(),
           start_date:$("#start_date").val(),
           end_date:$("#end_date").val(),
           dep_year:$("#dep_year").val(),
           dept_no:liger.get("dept_code").getValue().split(".")[1]==null?"":liger.get("dept_code").getValue().split(".")[1],
           dept_id:liger.get("dept_code").getValue().split(".")[0]==null?"":liger.get("dept_code").getValue().split(".")[0],
         };
        ajaxJsonObjectByUrl("addHtcFassetDict.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='asset_code']").val('');
				 $("input[name='asset_name']").val('');
				 $("input[name='fasset_model']").val('');
				 $("input[name='asset_type_code']").val('');
				 $("input[name='prim_value']").val('');
				 $("input[name='start_date']").val('');
				 $("input[name='end_date']").val('');
				 $("input[name='dep_year']").val('');
				 $("input[name='dept_code']").val('');
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
   
    function saveFassetDict(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	//资产分类字典
		autocomplete("#asset_type_code","../../../info/base/queryHtcFassetTypeDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#dept_code","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true);

    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin: 0 0 0 30px" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片号：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_code" type="text" id="asset_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_name" type="text" id="asset_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格型号:</td>
                <td align="left" class="l-table-edit-td"><input name="asset_model" type="text" id="asset_model" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_type_code" type="text" id="asset_type_code" ltype="text" validate="{required:true,}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">原值：</td>
                <td align="left" class="l-table-edit-td"><input name="prim_value" type="text" id="prim_value" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">启用年月：</td>
                 <td align="left" class="l-table-edit-td"><input  class="Wdate" name="start_date" type="text" id="start_date" ltype="text"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
                 <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">停用年月：</td>
                <td align="left" class="l-table-edit-td"><input  class="Wdate" name="end_date" type="text" id="end_date" ltype="text"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年限：</td>
                <td align="left" class="l-table-edit-td"><input name="dep_year" type="text" id="dep_year" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">管理科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text"/></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
