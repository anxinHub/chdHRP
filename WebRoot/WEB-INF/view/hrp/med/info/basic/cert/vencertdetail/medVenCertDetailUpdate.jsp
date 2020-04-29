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
        if("${validity_type}" == 0){
        	$("#end_date").prop("disabled",true);
    		$("#end_date").ligerTextBox({disabled:true});
        }
        $("#validity_type").change(function (){
        	if($("#validity_type").val() == 0){
        		$("#end_date").val("9999-12-30");
        		$("#end_date").prop("disabled",true);
        		$("#end_date").ligerTextBox({disabled:true});
        	}else{
        		$("#end_date").val("");
        		$("#end_date").prop("disabled",false);
        		$("#end_date").ligerTextBox({disabled:false});
        	}
        })
    });  
     
    function save(){
    	 var formPara={
    	            
 	           cert_id:$("#cert_id").val(),
 	           cert_codeOld:'${ cert_code}',
 	           cert_code:$("#cert_code").val(),
 	            
 	           type_id:liger.get("type_id").getValue().split(",")[0],
 	            
 	           sup_id:liger.get("sup_id").getValue().split(",")[0],
 	            
 	           issuing_authority:$("#issuing_authority").val(),
 	            
 	           start_date:$("#start_date").val(),
 	            
 	           end_date:$("#end_date").val(),
 	            
 	           cert_memo:$("#cert_memo").val(),
 	            
 	           file_path:$("#file_path").val() ,
 	           
 	           cert_state:$("#cert_state").val(),
 	           
 	           validity_type:$("#validity_type").val()
        };
        ajaxJsonObjectByUrl("updateMedVenCertDetail.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
    
  //附件上传
    function upLodePage(data){
	  var parm = "sup_id="+data +"&"+ "cert_code="+$("#cert_code").val() ;
  	  			
  	 	 parent.$.ligerDialog.open({url:'upLodePage.do?isCheck=false&'+parm,data:{},height: 500,width: 900, 
  	     			 title:'上传',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true ,parentframename:window.name }); 
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
     //$("form").ligerForm();
    }       
   
    function saveMedVenCertDetail(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
   	    autocomplete("#sup_id", "../../../../queryHosSupDict.do?isCheck=false", "id","text", true, true,'',false,'',240);
        liger.get("sup_id").setValue('${sup_id}');
        liger.get("sup_id").setText('${sup_id} ${sup_name}');
        //证件编码
  	/* 	autocomplete("#cert_code", "../../../../queryMedInvCert.do?isCheck=false", "id", "text", true, true,'',false,'',400);
        liger.get("cert_code").setValue('${cert_id} ${cert_code}');
        liger.get("cert_code").setText('${cert_id} ${cert_code}'); */
   		//证件类型
        autocomplete("#type_id", "../../../../queryMedVenCertType.do?isCheck=false", "id","text", true, true,'',false,'${type_id}',240);
       /*  liger.get("type_id").setValue('${type_id},${type_code}');
        liger.get("type_id").setText('${type_code} ${type_name}'); */
        
        $("#cert_state").val("${cert_state}");
        
        $("#validity_type").val('${validity_type}');
        
        $("#cert_code").ligerTextBox({width:400});
        $("#sup_id").ligerTextBox({width:240,disabled:true});
        $("#cert_id").ligerTextBox({width:240});
        $("#type_id").ligerTextBox({width:240});
        $("#issuing_authority").ligerTextBox({width:240});
        $("#start_date").ligerTextBox({width:240});
        $("#end_date").ligerTextBox({width:240});
        $("#cert_state").ligerTextBox({width:240});
        $("#validity_type").ligerTextBox({width:240});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
         <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>证件编号<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="cert_code" type="text" id="cert_code" value="${cert_code}" ltype="text" validate="{required:true,maxlength:100}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>    
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>注册:</b></td>
            <td align="left" class="l-table-edit-td"><input name="cert_id" type="text" id="cert_id" value="${cert_id}" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>证件类型<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id"  ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>发证机关<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="issuing_authority" type="text" id="issuing_authority" value="${issuing_authority}" ltype="text" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>有效期类型<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td" >
            	<select name="validity_type" id="validity_type"style="width: 135px;" >
                	<option value="0">永久</option>
                	<option value="1">限定</option>
            	</select>
			</td>
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用:</b></td>
            <td align="left" class="l-table-edit-td">
            	<select id="cert_state" name="cert_state">
            		<option value="1">在用</option>
               		<option value="0">停用</option>
               		
               	</select>
            </td>
      	 </tr>
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>起始日期<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="start_date" type="text" id="start_date" value="${start_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>截止日期<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="end_date" type="text" id="end_date" value="${end_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>证件描述:</b></td>
            <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="4" class="liger-textarea" id="cert_memo" name="cert_memo" style="width:580px" validate="{maxlength:200}" >${cert_memo}</textarea>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>文档路径:</b></td>
            <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="4" class="liger-textarea" id="file_path" name="file_path" style="width:580px" validate="{maxlength:200}" >${file_path}</textarea>
            </td>
            <td align="left"></td>
        </tr> 
       	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>附件上传:</b></td>
            <td align="left" class="l-table-edit-td" >
            	<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 40px;' ligeruiid='Button1004' onclick="upLodePage('${sup_id}')"><span>上传</span></div>
            </td>
            <td align="left"></td>
        </tr>  
    </table>
    </form>
    </body>
</html>
