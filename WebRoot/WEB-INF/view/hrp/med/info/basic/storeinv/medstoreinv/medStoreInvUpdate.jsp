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
    var isHide = '${p08035 }' == 1 ? false : true;
    $(function (){
        loadDict();
        loadForm(); 
    });  
     
    function save(){  
    	 
        var formPara={
       
        store_id:parent.liger.get("store_id").getValue().split(",")[0] ,
        inv_id:$("#inv_id").val(),
        old_location_id : liger.get("old_location_id").getValue(),
        location_id:liger.get("location_id").getValue()
        };   
        ajaxJsonObjectByUrl("addOrUpdateMedStoreInv.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
                parent.$.ligerDialog.close();
                parent.$(".l-dialog,.l-window-mask").remove();
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
   
    function addStoreInv(){
         save();
    }
    function loadDict(){
    	
        //字典下拉框
        var store_id=$("#store_id").val(); 
       /*  autocomplete("#store_id","../../../../queryMedStore.do?isCheck=false&","id","text",true,true );
		liger.get("store_id").setValue(parent.liger.get("store_id").getValue().split(",")[0]);
    	
    	liger.get("store_id").setText("${store_code} ${store_name}");
         */
        autocomplete("#location_id","../../../../queryMedLocation.do?isCheck=false&store_id="+store_id,"id","text",true,true,'',false,'${location_id}','160');
        liger.get("location_id").setText('${location_code} ${location_name}');
        autocomplete("#old_location_id","../../../../queryMedLocation.do?isCheck=false&store_id="+store_id,"id","text",true,true,'',false,'${location_id}','160');
        liger.get("old_location_id").setText('${location_code} ${location_name}');
        //由于页面当前字段是置灰的，则没有进行走后台 直接通过父页面的仓库取值
        $("#store_id").ligerComboBox({width:160,disabled:true});
 		var store_code = parent.liger.get("store_id").getText()  
        $("#store_id").val(store_code);
 		
        $("#inv_id").ligerTextBox({width:160,disabled:true});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr style="display: none">
        <input name="old_location_id" type="hidden" id="old_location_id" value="${location_code}" />
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>仓库:</b></td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" disabled="disabled" ltype="text" value="${store_id }"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         </tr> 
        <tr>    
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品:</b></td>
            <td align="left" class="l-table-edit-td"><input name="inv_id" type="text" id="inv_id" disabled="disabled" ltype="text" value="${inv_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>货位:</b></td>
            <td align="left" class="l-table-edit-td"><input name="location_id" type="text" hide:isHide id="location_id" ltype="text" value="${location_code}" /></td>
            <td align="left"></td>
         </tr> 
			
        </table>
    </form>
    </body>
</html>
