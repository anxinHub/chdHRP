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
                group_id:'${group_id}', 
                hos_id:'${hos_id}',
                copy_code:'${copy_code}',
                acc_year:'${acc_year}',
        		asset_type_code:liger.get("asset_type_code").getValue(),
                cost_item_no:liger.get("cost_item_code").getValue().split(".")[1],
        		cost_item_id:liger.get("cost_item_code").getValue().split(".")[0]
        };
        ajaxJsonObjectByUrl("updateHtcIassetCostRela.do",formPara,function(responseData){
            
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
   
    function saveIassetCostRela(){
    	
        if($("form").valid()){
        	
            save();
            
        }
    }

    function loadDict(){

    	//资产分类字典
 		autocomplete("#asset_type_code","../../../info/base/queryHtcIassetTypeDict.do?isCheck=false", "id","text", true, true);
 		 liger.get("asset_type_code").setValue('${asset_type_code}');
    	 liger.get("asset_type_code").setText('${asset_type_name}');
 		//成本项目字典
 		autocomplete("#cost_item_code","../../../info/base/queryHtcCostItemDict.do?isCheck=false", "id", "text", true,true);
 		 liger.get("cost_item_code").setValue('${cost_item_no}.${cost_item_id}');
    	 liger.get("cost_item_code").setText('${cost_item_name}');
        $("#asset_type_code").ligerTextBox({ disabled: true });
       
      }
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin:  0px  0px  0px  30px">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产分类编号：</td>
				<td align="left" class="l-table-edit-td"><input 	name="aeest_type_code" type="text" id="asset_type_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目编码：</td>
				<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
