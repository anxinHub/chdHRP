<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
	
     $(function (){
    
     	
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	var data1 = parent.gridManager1.getData();
     	var data2 = parent.gridManager2.getData();
     	var ParamVo = [];
     	var is_pay = false;
     	var is_con_no = false;
     	if($("#pay").attr("checked") == true){
     		is_pay = true;
	    }
	    	 
	    if($("#con_no").attr("checked") == true){
	    	is_con_no = true;
	    }
	    if (data1.length == 0 || data2.length == 0){
        	$.ligerDialog.error('请先查询对账数据');
        	return;
        }
	    var num = 1;
     	for(var i = 0; i < data1.length; i++){
     		if(data1[i].is_checks == '已对账'){
				break;
			}
     		for(var j = 0; j < data2.length; j++){
     			if(data1[j].is_checks == '已对账'){
					break;
				}
     			if(is_pay == true && is_con_no == true){
     				if(data1[i].credit == data2[j].debit && data1[i].check_no == data2[j].check_no && data1[i].pay_type_code == data2[j].pay_type_code){
     					ParamVo.push(
         						 data1[i].bank_id+"@"
         						+data2[j].tell_id+"@"
         						+data2[j].vouch_check_id+"@"
                                +data2[j].vouch_id+"@"
                                +data1[i].credit);
         			}else{
         				num++;
         				break;
         			}
     			}else if(is_pay == true){
     				if(data1[i].credit == data2[j].debit && data1[i].pay_type_code == data2[j].pay_type_code){
     					ParamVo.push(
         						 data1[i].bank_id+"@"
         						+data2[j].tell_id+"@"
         						+data2[j].vouch_check_id+"@"
                                +data2[j].vouch_id+"@"
                                +data1[i].credit);
         			}else{
         				num++;
         				break;
         			}
     			}else if(is_con_no == true){
     				if(data1[i].credit == data2[j].debit && data1[i].check_no == data2[j].check_no){
     					ParamVo.push(
         						 data1[i].bank_id+"@"
         						+data2[j].tell_id+"@"
         						+data2[j].vouch_check_id+"@"
                                +data2[j].vouch_id+"@"
                                +data1[i].credit);
         			}else{
         				num++;
         				break;
         			}
     			}else{
     				if(data1[i].credit == data2[j].debit){
     					ParamVo.push(
         						 data1[i].bank_id+"@"
         						+data2[j].tell_id+"@"
         						+data2[j].vouch_check_id+"@"
                                +data2[j].vouch_id+"@"
                                +data1[i].credit);
         			}else{
         				num++;
         				break;
         			}
     			}
     		}
     		
     	}
     	if(data1.length == num || data2.length == num){
     		$.ligerDialog.error('没有可以对账的数据');
        	return;
     	}
     	if(ParamVo == ""){
     		$.ligerDialog.error('没有可以对账的数据');
        	return;
     	}
        ajaxJsonObjectByUrl("saveBatchAccBankVeri.do",{ParamVo : ParamVo.toString()},function(responseData){
            
            if(responseData.state=="true"){				
                parent.queryA();
                parent.queryB();
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
   
    function saveAccUnitBankCheck(){
        if($("form").valid()){
            save();
        }
   }
    
    </script>
  <script type="text/javascript">
  	
  </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   <center>
         <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">对账方式：</td>
                <td align="left" class="l-table-edit-td"  style="padding-top:10px;"><input  name="dire_money"  readonly="readonly" checked="checked" type="checkbox" id="dire_money"  /></td>
                <td align="left">方向+金额</td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input  name="pay" type="checkbox" id="pay"  /></td>
                <td align="left">结算方式</td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input  name="con_no" type="checkbox" id="con_no"  /></td>
                <td align="left">票据号</td>
            </tr>
           </table>
   </center>
    </form>
   
    </body>
</html>
