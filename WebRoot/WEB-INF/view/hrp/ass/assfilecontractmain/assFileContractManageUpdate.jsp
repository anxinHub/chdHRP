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
    $(function (){
        loadDict();
        loadForm();
		
    });  
     
    function save(){
    	
        var formPara={
        		contract_id:$("#contract_id").val(),
        		contract_no:$("#contract_no").val(),
        		payment_id:$("#payment_id").val(),
        		summary:$("#summary").val(),
        		cur_code:liger.get("cur_code").getValue().split(".")[0],
        		pay_money:$("#pay_money").val(),
        		start_date:$("#start_date").val(),
        		end_date:$("#end_date").val(),
        		fact_pay_date:$("#fact_pay_date").val(),
                
           state:liger.get("state").getValue(),
           is_state:liger.get("is_state").getValue()
        
        };
        ajaxJsonObjectByUrl("updateAssFileContractManage.do",formPara,function(responseData){
            
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
   
    function saveAssContractManage(){
        if($("form").valid()){
            //save();
        }
    }
    function loadDict(){
        //字典下拉框
       autocomplete("#cur_code","../../../hrp/acc/queryCur.do?isCheck=false","id","text",true,true,"",false,"${cur_code}");
//     	liger.get("ven_id").setValue("${ven_id}");
//         liger.get("ven_id").setText("${ven_name}");
//     	$("#cur_code").ligerComboBox({
// 		      	url: '../../../hrp/acc/queryCur.do?isCheck=false',
// 		      	valueField: 'id',
// 		       	textField: 'text', 
// 		       	selectBoxWidth: 180,
// 		      	autocomplete: true,
// 		      	width: 180
// 			 });
    	$("#state").ligerComboBox({
			width : 135
		});
    	liger.get("state").setValue("${state}");
    	$("#is_state").ligerComboBox({
			width : 135
		});
    	liger.get("is_state").setValue("${is_state}");
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同ID：</td>
                <td align="left" class="l-table-edit-td"><input name="contract_id" type="text" disabled="disabled" id="contract_id" value="${contract_id}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同号：</td>
                <td align="left" class="l-table-edit-td"><input name="contract_no" type="text" id="contract_no" disabled="disabled" value="${contract_no}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款期号：</td>
                <td align="left" class="l-table-edit-td"><input name="payment_id" type="text" id="payment_id" value="${payment_id}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
                <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" value="${summary}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cur_code" type="text" id="cur_code" value="${cur_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款金额：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_money" type="text" id="pay_money" value="${pay_money}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款期间开始：</td>
                <td align="left" class="l-table-edit-td"><input name="start_date" type="text" id="start_date" value="${start_date}" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款期间结束：</td>
                <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" value="${end_date}" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">实付日期：</td>
                <td align="left" class="l-table-edit-td"><input name="fact_pay_date" type="text" id="fact_pay_date" value="${fact_pay_date}" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left"></td>
            </tr> 
             <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
               <td align="left" class="l-table-edit-td">
                <select name ="state" id = "state" value="${state}">
                  <option value= "0">新建</option>
                  <option value = "1">审核</option>
                </select>
            <td align="left"></td>
            </tr> 
             <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">启用状态：</td>
               <td align="left" class="l-table-edit-td">
                <select name ="is_state" id = "is_state" value="${is_state}">
                  <option value= "0">停止</option>
                  <option value = "1">启用</option>
                </select>
            <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
