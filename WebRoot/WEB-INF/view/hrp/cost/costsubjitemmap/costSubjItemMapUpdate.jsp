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
    var group_id = '${group_id}';
    var hos_id = '${hos_id}';
    var copy_code = '${copy_code}';
    var acc_year = '${acc_year}';
	var old_subj_code = '${subj_code}';
	var old_item_code = '${item_code}';
    $(function (){
    	
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        
        var formPara={
        		group_id:group_id,
        		
        		hos_id:hos_id,
        		
        		copy_code:copy_code,

        		acc_year:acc_year,

        		old_subj_code:old_subj_code,
        		 
        		old_item_code:old_item_code,

        		subj_code:liger.get("subj_code").getValue(),
        		
        		item_code:liger.get("item_code").getValue(),
                
        };
        ajaxJsonObjectByUrl("updateCostSubjItemMap.do",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
                old_subj_code = liger.get("subj_code").getValue();
                old_item_code = liger.get("item_code").getValue();
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
   
    function saveCosItemSubjRef(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){

    	autocomplete("#item_code","../queryCostSubjItemMapByItem.do?isCheck=false","id","text",true,true,"",false,false,"178");
        liger.get("item_code").setValue('${item_code}');
        liger.get("item_code").setText('${item_code}${item_name}');
    	autocomplete("#subj_code","../queryCostSubjItemMapBySubj.do?isCheck=false","id","text",true,true,"",false,false,"178");
        liger.get("subj_code").setValue('${subj_code}');
        liger.get("subj_code").setText('${subj_code} ${subj_name}');
    	
    }   
    </script>
  </head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
