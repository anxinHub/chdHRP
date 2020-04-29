<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
     
     var data;
     
     var grid;
     
	 var is_balance=0;
     
     var is_neg=0;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        $("#close").ligerButton({disabled: false,width:92});
        
        $("#save").ligerButton({disabled: false,width:92});
        
     });  
     
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
  
    function loadDict(){
            //字典下拉框
            
            $("#acc_month").ligerComboBox({width:180});
            
            $("#item_code").ligerComboBox({width:180});
            
            $("#emp_code").ligerComboBox({width:180});
            
            autocomplete("#group_id","../queryGroupDict.do?isCheck=false","id","text",true,true,true,true);
            
            autocomplete("#hos_id","../queryHosInfo.do?isCheck=false","id","text",true,true,true,true);
            
            autocomplete("#copy_code","../queryHosCopy.do?isCheck=false","id","text",true,true,true,true);
            
            autocompleteObj({
	    		id:"#acc_year",
	    		urlStr:"../queryAccYear.do?isCheck=false&wage_flag=1",
	    		valueField:"id",
	    		textField:"text",
	    		autocomplete:true,
	    		highLight:true,
	    		parmsStr:null,
	    		defaultSelect:true,
	    		initvalue:null,
	    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,
	    		selectEvent:function (value){
				
	    				var fromData={
		                		
		                		acc_year:liger.get("acc_year").getValue(), 
		                		wage_flag: 1
		            	}
		    		
		    	   autocomplete("#acc_month","../queryAccYearMonth.do?isCheck=false","id","text",true,true,fromData,true);
		            
				}
	    	});
            
            autocompleteObj({
	    		id:"#wage_code",
	    		urlStr:"../queryAccWage.do?isCheck=false",
	    		valueField:"id",
	    		textField:"text",
	    		autocomplete:true,
	    		highLight:true,
	    		parmsStr:null,
	    		defaultSelect:true,
	    		initvalue:null,
	    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,
	    		selectEvent:function (value){
				
	    				var fromData={
		                		
		                		wage_code:liger.get("wage_code").getValue(),
		                		
		                		acc_year:liger.get("acc_year").getValue()
		                
		            	}
		    		
		    			autocomplete("#item_code","../queryAccWageItem.do?isCheck=false","id","text",true,true,fromData,true);
		            
				}
	    	});
            
     } 
    
  
	function showWindow(){
    	
		var fromData={
        		
				group_id : liger.get("group_id").getValue(),
				
				group_name : $("#group_id").val(),
				
				hos_id : liger.get("hos_id").getValue(),
				
				hos_name : $("#hos_id").val(),
				
				copy_code : liger.get("copy_code").getValue(),
				
				copy_name : $("#copy_code").val(),
				
        		wage_code : liger.get("wage_code").getValue(),
        		
        		wage_name : $("#wage_code").val(),
        		
        		acc_year  : liger.get("acc_year").getValue(),
        		
        		acc_year_name : $("#acc_year").val(),
				
        		acc_month : liger.get("acc_month").getValue(),
        		
        		acc_month_name : $("#acc_month").val(),
        		
        		item_code : liger.get("item_code").getValue(),
        		
        		item_name : $("#item_code").val(),
        		
        		emp_code : $("#emp_code").val()
        
    	}
		
		return fromData;
		
    }
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table_id" style="width: 100%">
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">集团：</td>
                <td align="left" class="l-table-edit-td"><input name="group_id" type="text" id="group_id" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医院：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本账套：</td>
                <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
                <td align="left" class="l-table-edit-td">
				<input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" />
				</td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份：</td>
                <td align="left" class="l-table-edit-td">
                <input name="acc_month" type="text" id="acc_month" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目：</td>
                <td align="left" class="l-table-edit-td">
                <input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
                <td align="left" class="l-table-edit-td">
                <select id="emp_code">
                	<option value="本职工">本职工</option>
                </select>
				</td>
                <td align="left"></td>
            </tr>  
        </table>
    </form>
    </body>
</html>
