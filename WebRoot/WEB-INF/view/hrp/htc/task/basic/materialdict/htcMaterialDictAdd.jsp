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
     
     function  save(){
        var formPara={
        		mate_code:$("#mate_code").val(),
        		mate_name:$("#mate_name").val(),
        		mate_type_code:liger.get("mate_type_code").getValue(),
        		mate_mode:$("#mate_mode").val(),
        		meas_code:liger.get("meas_code").getValue(),
        		price:$("#price").val(),
        		fac_no:liger.get("fac_code").getValue().split(".")[1]==null?"":liger.get("fac_code").getValue().split(".")[1],
        		fac_id:liger.get("fac_code").getValue().split(".")[0]==null?"":liger.get("fac_code").getValue().split(".")[0],
        		is_stop:liger.get("is_stop").getValue()
         };
        ajaxJsonObjectByUrl("addHtcMaterialDict.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='mate_code']").val('');
				 $("input[name='mate_name']").val('');
				 $("input[name='mate_mode']").val('');
				 $("input[name='meas_code']").val('');
				 $("input[name='price']").val('');
				 $("input[name='fac_code']").val('');
				 $("input[name='is_stop']").val('');
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
   
    function saveMaterialDict(){
    	
        if($("form").valid()){
        	
            save();
            
        }
   }

     function loadDict(){
    	 autocomplete("#is_stop","../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text",true, true);
    	 autocomplete("#mate_type_code","../../../info/base/queryHtcMaterialTypeDict.do?isCheck=false","id","text",true,true);
    	 autocomplete("#meas_code","../../../info/base/queryHtcHosUnitDict.do?isCheck=false","id","text",true,true);
    	 autocomplete("#fac_code","../../../info/base/queryHtcHosFacDict.do?isCheck=false","id","text",true,true);
     }
</script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">物资编码：</td>
				<td align="left" class="l-table-edit-td"><input name="mate_code" type="text" id="mate_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">物资名称：</td>
				<td align="left" class="l-table-edit-td"><input name="mate_name" type="text" id="mate_name" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">物资分类：</td>
				<td align="left" class="l-table-edit-td"><input name="mate_type_code" type="text" id="mate_type_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">型号：</td>
				<td align="left" class="l-table-edit-td"><input name="mate_mode" type="text" id="mate_mode" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">单位：</td>
				<td align="left" class="l-table-edit-td"><input name="meas_code" type="text" id="meas_code" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">单价：</td>
				<td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" /></td>
				<td align="left"></td>
			</tr>
            <tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">生产厂商:</td>
				<td align="left" class="l-table-edit-td"><input name="fac_code" type="text" id="fac_code" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用:</td>
				<td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}"/></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
