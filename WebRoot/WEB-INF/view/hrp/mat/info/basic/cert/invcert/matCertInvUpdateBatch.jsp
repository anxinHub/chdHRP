<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html style="overflow:auto;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat; 
     var grid;
     var gridManager = null; 
     $(function (){
		loadDict()//加载下拉框
        loadForm();
		loadHotkeys();
     });
     
     //表单提交的全部弹出数据
	function  save(){
        if($("form").valid()){
			var formPara={
		        group_id : $("#group_id").val(),
		        hos_id : $("#hos_id").val(),
		        copy_code : $("#copy_code").val(),
				cert_ids : $("#cert_ids").val(),
				fac_id : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0],
				fac_no : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[1],
				sup_id : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue(),
				cert_state : $("#cert_state").val()
			};
			
			ajaxJsonObjectByUrl("updateMatCertInvBatch.do?isCheck=false",formPara,function(responseData){
				if(responseData.state=="true"){
					parentFrameUse().query();
					this_close();
				}
			});
        }
    }
     
     //加载表单
	function loadForm(){
	    
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement: function (lable, element){
				if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     //$("form").ligerForm();
	}       
	
	function loadDict(){
		autocomplete("#fac_code", "../../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true,"","","",400,"",400);
		autocomplete("#sup_code", "../../../../queryHosSup.do?isCheck=false", "id", "text", true, true,"","","",400,"",400);
		$("#cert_state").ligerTextBox({width:160});
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Esc', this_close);
	 }
	
	//关闭当前页面
	function this_close(){
		frameElement.dialog.close();
	}
</script> 
</head>
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="padding: 10px"  >
	        <tr>
	        </tr>
	        <tr>
	        </tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	生产厂商：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="fac_code" type="text" id="fac_code" ltype="text"  validate="{required:false}" />
	            </td>
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td"  >
	            	供应商：
	            </td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="sup_code" type="text" id="sup_code" ltype="text"  validate="{required:false}" />
	            </td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td"  >
	            	状态：
	            </td>
	            <td align="left" class="l-table-edit-td" >
					<select id="cert_state" name="cert_state">
				    	<option value="0">停用</option>
				    	<option value="1">在用</option>
			        </select>
	            </td>
	        </tr> 
	        <tr>
	        	<td colspan="2" align="left" class="l-table-edit-td" >
	        		<input type="hidden" name="group_id" id="group_id" value="${group_id}">
	        		<input type="hidden" name="hos_id" id="hos_id" value="${hos_id}">
	        		<input type="hidden" name="copy_code" id="copy_code" value="${copy_code}">
	        		<input type="hidden" name="cert_ids" id="cert_ids" value="${cert_ids}">
	        	</td>
	        </tr>
	    </table>
    </form>
    </body>
</html>
