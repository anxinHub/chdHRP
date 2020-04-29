<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        $("#year_month").ligerTextBox({ width:180 });
        
        loadForm();
        
     });  
     
     function  save(){
    	 
 		var dept = liger.get("listbox1").getValue();
    	 
    	 var server_dept = liger.get("listbox2").getValue();
    	 
    	 var cost_item = liger.get("listbox3").getValue();

        var formPara={

           dept:dept,
            
           server_dept:server_dept,
            
           cost_item:cost_item,
           
           acc_year:$("#year_month").val().substring(0,4),
           
           acc_month:$("#year_month").val().substring(4,7),
            
           para_code:liger.get("para_code").getValue()
            
         };
        
        ajaxJsonObjectByUrl("fastCostParaManSet.do",formPara,function(responseData){
            
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
   
    function saveFastCostParaManSet(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
        
    	$("#listbox1").ligerListBox({
			parms : {type_code : "('01')",pageSize:"ALL"},
			url : '../queryDeptDictNo.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 325,
			width : 240
		});
    	
    	$("#listbox2").ligerListBox({
    		parms : {pageSize:"ALL"},
			url : '../queryDeptDictNo.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 325,
			width : 240
		});
    	
    	$("#listbox3").ligerListBox({
    		parms : {pageSize:"ALL"},
			url : '../queryItemDictNo.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 325,
			width : 240
		});
    	
    	autocomplete("#para_code","../queryDeptParaDict.do?isCheck=false","id","text",true,true);

     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

             <tr>
                <td align="left" class="l-table-edit-td">服务科室(不选择为全部)：</td>
                <td align="left" class="l-table-edit-td">受益科室(不选择为全部)：</td>
                <td align="left" class="l-table-edit-td">成本项目(不选择为全部)：</td>
            </tr> 
            <tr>
                <td align="left" class="l-table-edit-td"><div id="listbox1"></div> </td>
                <td align="left" class="l-table-edit-td"><div id="listbox2"></div></td>
                <td align="left" class="l-table-edit-td"><div id="listbox3"></div> </td>
            </tr> 
			<tr>
                <td align="left" class="l-table-edit-td" >年月：</td>
                <td align="left" class="l-table-edit-td" >分摊参数(必选)：</td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" validate="{required:true}"/></td>
                <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
   
    </body>
</html>
