<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
    	 var formPara={
  				//inv_code : $("#inv_code").val(),
  				inv_name : $("#inv_name").val(),
  				alias : $("#alias").val(),
  				mat_type_id : liger.get("mat_type_id").getValue() == null ? "" : liger.get("mat_type_id").getValue().split(",")[0],
  				mat_type_no : liger.get("mat_type_id").getValue() == null ? "" : liger.get("mat_type_id").getValue().split(",")[1],
  				inv_model : $("#inv_model").val(),
  				unit_code : liger.get("unit_code").getValue() == null ? "" : liger.get("unit_code").getValue(),
  				fac_id : liger.get("fac_id").getValue() == null ? "" : liger.get("fac_id").getValue(),
  				plan_price : $("#plan_price").val(),
  				price_rate : $("#price_rate").val(),  	
  				sell_price : $("#sell_price").val(),
  				agent_name : $("#agent_name").val(),
  				brand_name : $("#brand_name").val(),
  				note:$("#note").val()
  				//supData : JSON.stringify(gridManager.getData())
  			};
        ajaxJsonObjectByUrl("updateSupMatInv.do",formPara,function(responseData){
            
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
   
    function saveSupMatInv(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	//字典下拉框
		autocomplete("#mat_type_id", "../../sup/queryMatTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : '1'});
		
		autocomplete("#unit_code", "../queryUnitDict.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#fac_id", "../queryFacDict.do?isCheck=false", "id", "text", true, true,"","","",400,"",400);
		
		
		 liger.get("mat_type_id").setValue("${mat_type_id}");
	        liger.get("mat_type_id").setText("${mat_type_code} ${mat_type_name}");
	        liger.get("unit_code").setValue("${unit_code}");
	        liger.get("unit_code").setText("${unit_name}");
	        liger.get("fac_id").setValue("${fac_id}");
	        liger.get("fac_id").setText("${fac_name}");
		
		//渲染效果
		//$("#inv_code").ligerTextBox({width:160});
		$("#inv_name").ligerTextBox({width:160});
		$("#alias").ligerTextBox({width:160});
		$("#inv_model").ligerTextBox({width:160});
		$("#plan_price").ligerTextBox({width:160, number:true, precision:'${para_04001}'});
		$("#plan_price").focus(function(){this.select();});//加获取焦点选择文本事件
		$("#price_rate").ligerTextBox({width:160, number:true});
		$("#sell_price").ligerTextBox({width:160, number:true, precision:'${para_04001}'});
		$("#sell_price").focus(function(){this.select();});//加获取焦点选择文本事件
		$("#agent_name").ligerTextBox({width:160});
		$("#brand_name").ligerTextBox({width:160});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
           <%--  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资材料编码：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_code" type="text" id="inv_code" ltype="text" value="${inv_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> --%>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资材料名称：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_name" type="text" id="inv_name" ltype="text" value="${inv_name}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">别名：</td>
            <td align="left" class="l-table-edit-td"><input name="alias" type="text" id="alias" ltype="text" value="${alias}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
            <td align="left" class="l-table-edit-td"><input name="mat_type_id" type="text" id="mat_type_id" ltype="text" value="${mat_type_id}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格型号：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" ltype="text" value="${inv_model}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量单位：</td>
            <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" value="${unit_code}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" value="${fac_id}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划价：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_price" type="text" id="plan_price" ltype="text" value="${plan_price}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">加价率：</td>
            <td align="left" class="l-table-edit-td"><input name="price_rate" type="text" id="price_rate" ltype="text" value="${price_rate}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">零售价：</td>
            <td align="left" class="l-table-edit-td"><input name="sell_price" type="text" id="sell_price" ltype="text" value="${sell_price}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">品牌：</td>
            <td align="left" class="l-table-edit-td"><input name="brand_name" type="text" id="brand_name" ltype="text" value="${brand_name}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">代理商：</td>
            <td align="left" class="l-table-edit-td"><input name="agent_name" type="text" id="agent_name" ltype="text" value="${agent_name}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" value="${note}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        </table>
    </form>
    </body>
</html>
