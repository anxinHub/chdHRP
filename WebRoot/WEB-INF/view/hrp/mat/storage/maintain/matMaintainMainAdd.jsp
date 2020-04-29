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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
 	 var isSave=true;
 	var btnColor=parent.$('.l-dialog-btn.l-dialog-btn-highlight').css('background');
     $(function (){
        loadDict();//加载下拉框
     });  
     function changeBtnColor(){
    	 if(isSave){
    		 parent.$('.l-dialog-btn.l-dialog-btn-highlight').css({background:'#428DB7'});
    	 }else{
    		 parent.$('.l-dialog-btn.l-dialog-btn-highlight').css({background:'#FAFAFA'});
    	 }
     }
     
     function  save(){
    	 if(isSave){
    		 isSave=false;
    		 changeBtnColor();
    		//主表
   	  		if(!liger.get("store_id").getValue()){
   	  			$.ligerDialog.warn("仓库不能为空");  
   	  			return false;  
   	  		}
   	  		if(!$("#create_date").val()){
   	  			$.ligerDialog.warn("盘点日期不能为空");  
   	  			return false;  
   	  		}
   	  		//处理明细
   	       var formPara={
   	       		store_id:liger.get("store_id").getValue().split(",")[0],
   	       		store_no:liger.get("store_id").getValue().split(",")[1],
   	       		brief:liger.get("brief").getValue(),
   	       		create_date:$("#create_date").val()
   	       }
   	  		$.ajax({
   	  			url:"addMatMaintainMainAndDetail.do?isCheck=false",
   	  			data:formPara,
   	  			dataType:"json",
   	  			type:"post",
   	  	        async:false,
   	  			success:function(responseData){
   	  				if(responseData.state=="true"){
   	  					parentFrameUse().query();
   	  					$.ligerDialog.confirm('是否继续添加养护记录?', function (yes){
   	  						if(yes){
   	  	             			isSave=true;
   	  	             			changeBtnColor();
   	  	             		}else{
   	  	             			parentFrameUse().openUpdate(responseData.update_para);
   	  	             			this_close();
   	  	             		}
   	  					})
   	  				}
   	  			}
   	  		})
    	 }
    	 //setTimeout(function () {
    		// parent.$('.l-dialog-btn.l-dialog-btn-highlight').css({background: '#aaa'})
    	 //}, 300)
     }
    
    //关闭
 	function this_close(){
  		frameElement.dialog.close();
  	}

	function saveMatLocationType(){
	     if($("form").valid()){
	         save();
	     }
	}
    function loadDict(){
        //字典下拉框
    	$("#create_date").ligerTextBox({width:180}); 
    	autodate("#create_date");
    	$("#create_date").ligerGetDateEditorManager().setDisabled();
    	autocompleteAsync("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},true,false,'180');
    	//autocompleteAsync("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},true,false,'180');
    	$("#brief").ligerTextBox({width:360,height:100}); 

    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr></tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>养护日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" disable="true" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
       <!--  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>物资类别：</td>
            <td align="left" class="l-table-edit-td"><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>  -->
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td">
           <!--  <input name="brief" type="text" id="brief" ltype="text" ligerui="width:500"  /> -->
            <textarea name="brief" type="text" id="brief" ltype="text" cols="40" rows="2" class="l-textarea" style="width:400px"></textarea>
            </td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
