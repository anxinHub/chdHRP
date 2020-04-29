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
         loadDict()//加载下拉框
        loadForm();
         
		$.post("getRules.do?isCheck=false",null,function(responseData){
			if(responseData){
       		 	$("#rules").val(responseData);
       		 $("#font2").text(responseData);
	       	 }else{
	       		 $.ligerDialog.error('支出预算科目编码规则未设置！');
	       	 }
		});
		    
		$("#subj_code").blur(function(){
			var map ={
					budg_year:$("#budg_year").val(),
					subj_code:$("#subj_code").val(),
					rules:$("#rules").val()
				};
			ajaxJsonObjectByUrl("getSuperCode.do?isCheck=false",map,function(responseData){
		 		if(responseData.super_code == 0){
		 			$("#super_code").val(responseData.super_code);
		 		}else{
		 			$("#super_code").val(responseData.super_code +' ' +responseData.super_name);
		 		}
		 		
		 		$("#subj_level").val(responseData.subj_level);
		 	});
		})
		$("#subj_name").blur(function(){
			if($("#super_code").val().split(" ")[0] == 0){
				$("#subj_name_all").val($("#subj_name").val());
			}else{
			 	$("#subj_name_all").val($("#super_code").val().split(" ")[1]+"-"+ $("#subj_name").val());
			}
			
		})
        
     });  
     
     function  save(){
    	 if(!$("#rules").val()){
    		 $.ligerDialog.error('支出预算科目编码规则未设置');
    		 return false ;
    	 }
        var formPara={
            
           budg_year:$("#budg_year").val(),
            
           subj_code:$("#subj_code").val(),
            
           subj_name:$("#subj_name").val(),
            
           subj_name_all:$("#subj_name_all").val(),
            
           //subj_nature:liger.get("subj_nature").getValue(),
            
           super_code:$("#super_code").val().split(" ")[0],
            
           subj_level:$("#subj_level").val(),
            
           is_last:$("#is_last").val(),
           
           is_caarried:$("#is_caarried").val(),
           
           rules:$("#rules").val()
            
         };
        
        ajaxJsonObjectByUrl("addBudgCostSubj.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='budg_year']").val('');
				 $("input[name='subj_code']").val('');
				 $("input[name='subj_name']").val('');
				 $("input[name='subj_name_all']").val('');
				 //$("input[name='subj_nature']").val('');
				 $("input[name='super_code']").val('');
				 $("input[name='subj_level']").val('');
				 $("input[name='is_last']").val('');
				 $("input[name='is_caarried']").val('');
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
     //$("form").ligerForm();
 }       
   
    function saveCostBudgSubj(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	//autocomplete("#subj_nature","../../../queryBudgAccSubjNature.do?isCheck=false","id","text",true,true);
    	//autocomplete("#acc_subj_code2","../../../queryAccBudgSubj.do?isCheck=false&subj_type_code=04","id","text",true,true);
    	autodate("#budg_year","yyyy");
    	$("#budg_year").ligerTextBox({width:160});
    	$("#subj_code").ligerTextBox({width:160});
    	$("#subj_name").ligerTextBox({width:160});
    	//$("#subj_nature").ligerTextBox({width:160});
    	$("#is_last").ligerTextBox({width:160});
    	$("#is_caarried").ligerTextBox({width:160});
    	$("#subj_name_all").ligerTextBox({width:160,disabled:true});
    	$("#super_code").ligerTextBox({width:160,disabled:true});
    	$("#subj_level").ligerTextBox({width:160,disabled:true});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
	   <font id="font1">编码规则：<font id="font2" color="red"></font></font><hr/>
	   <input type="hidden" id="rules" name="rules" />
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input class="Wdate" name="budg_year" type="text" id="budg_year" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目编码<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目名称<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_name" type="text" id="subj_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <!-- <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目性质<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_nature" type="text" id="subj_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>  -->
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否末级<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_last" name="is_last" style="width: 135px;" >
	                		<option value="0">否</option>
	                		<option value="1">是</option>
              		</select>
                </td>
                <td align="left"></td>
            </tr> 
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否结转<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_caarried" name="is_caarried" style="width: 135px;" >
	                		<option value="0">否</option>
	                		<option value="1">是</option>
              		</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目全称<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_name_all" id="subj_name_all" type="text" ltype="text" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>上级编码<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="super_code" type="text"  id="super_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目级别<font color="red" >*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_level" type="text"  id="subj_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
