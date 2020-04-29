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
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
    	var dept_code = liger.get("dept_code").getValue();
  
        var formPara={

           dept_code:dept_code.split(".")[2],
            
                acc_year:$("#year_month").val().substring(0,4),
       	
            	acc_month:$("#year_month").val().substring(4,6),
                 
                asset_type_id:liger.get("asset_type_code").getValue(),
                 
                depre_amount:$("#depre_amount").val(),
                 
                source_id:liger.get("source_id").getValue()
        };
        ajaxJsonObjectByUrl("updateCostIassetDetail.do",formPara,function(responseData){
            
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
   
    function saveCostIassetDetail(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
        autocomplete("#dept_code","../queryDeptDictCodeLast.do?isCheck=false","id","text",true,true);
        
		autocomplete("#asset_type_code","../queryIassetTypeArrt.do?isCheck=false","id","text",true,true);
        
        autocomplete("#source_id","../querySourceArrt.do?isCheck=false","id","text",true,true);
        
        liger.get("dept_code").setValue("${dept_code}");
        
		liger.get("dept_code").setText("${dept_name}");
        
		liger.get("asset_type_code").setValue("${asset_type_code}");
        
		liger.get("asset_type_code").setText("${asset_type_name}");
		
		liger.get("source_id").setValue("${source_id}");
        
		liger.get("source_id").setText("${source_name}");
		
		$("#dept_code").ligerTextBox({disabled: true});
		
		$("#year_month").ligerTextBox({disabled: true});
		
		$("#asset_type_code").ligerTextBox({disabled: true});
		
		$("#source_id").ligerTextBox({disabled: true});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" disabled = "disabled"  value="${year_month}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室编码：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text"  disabled = "disabled"  validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="asset_type_code" type="text" id="asset_type_code" ltype="text"  disabled = "disabled"   validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
             
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
                <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" ltype="text"  disabled = "disabled"  validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧额：</td>
                <td align="left" class="l-table-edit-td"><input name="depre_amount" type="text" id="depre_amount" ltype="text"   value="${depre_amount}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    </body>
</html>
