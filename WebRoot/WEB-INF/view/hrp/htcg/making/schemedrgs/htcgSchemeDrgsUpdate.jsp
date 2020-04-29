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
        old_scheme_code:'${scheme_code}',
        old_drgs_code:'${drgs_code}',
        };
        ajaxJsonObjectByUrl("updateHtcgSchemeDrgs.do",formPara,function(responseData){
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
   
    function saveSchemeDrgs(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false","id","text",true,true);
    	liger.get("scheme_code").setValue('${scheme_code}');
		liger.get("scheme_code").setText('${scheme_name}');
     	autocomplete("#drgs_code","../../base/queryHtcgDrgsDict.do?isCheck=false","id","text",true,true);
     	liger.get("drgs_code").setValue('${drgs_code}');
		liger.get("drgs_code").setText('${drgs_name}');
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
           <tr>
				 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算方案名称：</td>
                <td align="left" class="l-table-edit-td"><input name="scheme_code" type="text" id="scheme_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
           </tr>
           <tr>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种名称：</td>
                <td align="left" class="l-table-edit-td"><input name="drgs_code" type="text" id="drgs_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
           </tr>
        </table>
    </form>
    </body>
</html>
