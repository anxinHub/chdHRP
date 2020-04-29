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
        loadDict();
        loadForm();
    });  
     
    function save(){
        var formPara={
       		scheme_code:liger.get("scheme_code").getValue(),
            drgs_code:liger.get("drgs_code").getValue(),
            charge_nature_code:liger.get("charge_nature_code").getValue(),
            charge_code:liger.get("charge_code").getValue(),
            similar_code:liger.get("similar_code").getValue()
        };
        ajaxJsonObjectByUrl("updateHtcgSchemeSimilarTreat.do?isCheck=false",formPara,function(responseData){
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
   
    function saveSchemeSimilarTreat(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	
    	autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true,{scheme_code:"${scheme_code}"},true);
		autocomplete("#drgs_code","../../base/queryHtcgDrgsDict.do?isCheck=false", "id", "text", true,true,{drgs_code:"${drgs_code}"},true);
		autocomplete("#charge_nature_code","../../base/queryHtcgChargeNatureDict.do?isCheck=false", "id", "text",true, true,{charge_nature_code:"${charge_nature_code}"},true);
        //字典下拉框
        if('${charge_nature_code}' == '01'){
        	autocomplete("#charge_code","../../base/queryCostChargeItemArrtDict.do?isCheck=false","id","text",true,true,"",false,"",'178');
	    	autocomplete("#similar_code","../../base/queryCostChargeItemArrtDict.do?isCheck=false","id","text",true,true,"",false,"",'178');
        }else if('${charge_nature_code}' == '02'){
        	autocomplete("#charge_code","../../base/queryHtcgDrugDict.do?isCheck=false","id","text",true,true,"",false,"",'178');
	    	autocomplete("#similar_code","../../base/queryHtcgDrugDict.do?isCheck=false","id","text",true,true,"",false,"",'178');
        }else if('${charge_nature_code}' == '03'){
			autocomplete("#charge_code","../../base/queryMatInvDict.do?isCheck=false","id","text",true,true,"",false,"",'178');
	    	autocomplete("#similar_code","../../base/queryMatInvDict.do?isCheck=false","id","text",true,true,"",false,"",'178');
        }
    	
        liger.get("charge_code").setValue("${charge_code}");
        liger.get("charge_code").setText("${charge_name}");
        
    	liger.get("similar_code").setValue("${similar_code}");
        liger.get("similar_code").setText("${similar_name}");

        $("#scheme_code").ligerTextBox({disabled:true});
        $("#drgs_code").ligerTextBox({disabled:true});
        $("#charge_nature_code").ligerTextBox({disabled:true});
        $("#charge_code").ligerTextBox({disabled:true});
    	
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案：</td>
                <td align="left" class="l-table-edit-td"><input name="scheme_code"  type="text" id="scheme_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种：</td>
                <td align="left" class="l-table-edit-td"><input name="drgs_code"  type="text" id="drgs_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目性质：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_nature_code"  type="text" id="charge_nature_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">标准项目：</td>
                <td align="left" class="l-table-edit-td">
                <input name="charge_code"  type="text" id="charge_code" ltype="text" validate="{required:true}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">相似项目：</td>
                <td align="left" class="l-table-edit-td"><input name="similar_code" type="text" id="similar_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
