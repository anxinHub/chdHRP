<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
     var acc_year = '${acc_year}'; 
     
     //页面初始化
     $(function (){
        loadDict();//加载查询条件
        loadForm();
     });  
     
     
     //添加
     function  save(){
    	 
        var formPara={
       		acc_year:acc_year ,
       		subj_code:liger.get("subj_code").getValue(),
			cash_item_id:liger.get("cash_item_id").getValue() ,
			cash_money:$("#cash_money").val(),
			summary:$("#summary").val()
        };
        
        ajaxJsonObjectByUrl("addAccCashFlowInit.do",formPara,function(responseData){
            if(responseData.state=="true"){
				$("input[name='subj_code']").val('');	
				$("input[name='cash_item_id']").val('');
				$("input[name='cash_money']").val('');	
				$("input[name='summary']").val('');
				parent.query();
            }
        });
    }
     
     
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
         	success:function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
		});
	}       
   	
	//保存
    function saveAccCashFlowInit(){
        if($("form").valid()){
            save();
        }
   }
	
    //字典下拉框
    function loadDict(){
       
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,'',false,'',256) ;
		autocomplete("#cash_item_id","../queryCashItemSelect.do?isCheck=false","id","text",true,true,'',false,'',256) ;
		
		$("#subj_code").ligerTextBox({width:256});
		$("#cash_item_id").ligerTextBox({width:256});
		$("#cash_money").ligerTextBox({width:256});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">现金流量项目<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td"><input name="cash_item_id" type="text" id="cash_item_id" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额<font color="red">*</font>：</td>
                <td align="left" class="l-table-edit-td"><input name="cash_money" type="text" id="cash_money" ltype="text" validate="{required:true,number:true}" /></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注<font color="red">*</font>：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="5" cols="40" name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:200}" ></textarea></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
