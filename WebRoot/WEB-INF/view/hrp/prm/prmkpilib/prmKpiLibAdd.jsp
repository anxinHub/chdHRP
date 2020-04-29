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
     
     var kpi_hos_type;
     
     var kpi_dept_type;
     
     var kpi_emp_type;
     
     $(function (){
         loadDict()//加载下拉框
         loadForm();
       	$("#kpi_code").ligerTextBox({width:120});
         $("#kpi_name").ligerTextBox({width:395});
         $("#dim_code").ligerTextBox({width:120});
         $("#kpi_note").ligerTextBox({width:630});
         $("#nature_code").ligerTextBox({width:120});
         $("#kpi_set_note").ligerTextBox({width:630});
         $("#kpi_act_note").ligerTextBox({width:630});
         $("#unit_code").ligerTextBox({width:120}); 
         $("#kpi_type").ligerTextBox({width:80}); 
         
         
     
     });  
     
     function  save(){
       
       var kpi_code = $("#kpi_code").val();
       if(kpi_code == ''){
    	   $.ligerDialog.warn('指标编码不能为空 ');
    	   return ; 
       }
       
       var kpi_name = $("#kpi_name").val();
       if(kpi_name == ''){
    	   $.ligerDialog.warn('指标名称不能为空 ');
    	   return ; 
       }
       
       var dim_code = liger.get("dim_code").getValue();
       if(dim_code == ''){
    	   $.ligerDialog.warn('维护名称不能为空 ');
    	   return ; 
       }
       
       var nature_code = $("#nature_code").val();
       if(nature_code == ''){
    	   $.ligerDialog.warn('指标性质不能为空 ');
    	   return ; 
       }
       
       kpi_hos_type =  $("#kpi_hos_type").ligerCheckBox({ disabled: false });
       
       kpi_dept_type =  $("#kpi_dept_type").ligerCheckBox({ disabled: false });
       
       kpi_emp_type =  $("#kpi_emp_type").ligerCheckBox({ disabled: false });
    	 
        var formPara={
        		
           kpi_code:kpi_code,
            
           kpi_name:kpi_name,
            
           nature_code:nature_code,
            
           dim_code:dim_code,
            
           kpi_note:$("#kpi_note").val(),
            
           is_stop:$("#is_stop").val(),
           
           unit_code:$("#unit_code").val(),
           
           
           kpi_set_note:$("#kpi_set_note").val(),
           
           kpi_act_note:$("#kpi_act_note").val(),
           
           kpi_hos_type :kpi_hos_type.getValue()==true?'1':'0',
           
           kpi_dept_type : kpi_dept_type.getValue()==true?'1':'0',
            
           kpi_emp_type: kpi_emp_type.getValue()==true?'1':'0',
         };
        
        ajaxJsonObjectByUrl("addPrmKpiLib.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='kpi_code']").val('');
				 $("input[name='kpi_name']").val('');
				 $("input[name='nature_code']").val('');
				 $("input[name='dim_code']").val('');
				 $("input[name='dim_note']").val('');
				 $("input[name='is_stop']").val('');
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
   
    function savePrmKpiLib(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dim_code","../queryPrmKpiDim.do?isCheck=false","id","text",true,true);
   
    	autocomplete("#unit_code","../quertHosUnitDict.do?isCheck=false","id","text",true,true);
    	 //$("#kpi_code").ligerTextBox({width:552});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" id = "table2" width="100%">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码：</td>
                <td align="left" class="l-table-edit-td" colspan="1">
                	<input name="kpi_code" type="text" id="kpi_code" ltype="text" validate="{maxlength:50}"/>
               	</td>
               	
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
                <td align="left" class="l-table-edit-td" colspan="3">
                	<input name="kpi_name" type="text" id="kpi_name" ltype="text" validate="{maxlength:100}"/>
                </td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维度名称：</td>
                <td align="left" class="l-table-edit-td"><input name="dim_code" type="text" id="dim_code" ltype="text" validate="{maxlength:70}" /></td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标性质：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="nature_code" name="nature_code" style="width: 200px;">
			                		<option value="01">01 正向</option>
			                		<option value="02">02 反向</option>
                	</select>
                </td>
                
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量单位：</td>
                <td align="left" class="l-table-edit-td">
                <input name="unit_code" type="text" id="unit_code" ltype="text" validate="{maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标描述：</td>
                <td align="left" colspan="7" class="l-table-edit-td"><input name="kpi_note" type="text" id="kpi_note" ltype="text" validate="{maxlength:200}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">设立目的：</td>
                <td align="left" colspan="7" class="l-table-edit-td"><input name="kpi_set_note" type="text" id="kpi_set_note" ltype="text" validate="{maxlength:200}" /></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">行动要点：</td>
                <td align="left"  colspan="7"  class="l-table-edit-td"><input name="kpi_act_note" type="text" id="kpi_act_note" ltype="text" validate="{maxlength:200}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
               <!--  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标类别：</td>
                <td align="left" class="l-table-edit-td">
                    	<select id="kpi_type" name="kpi_type" style="width: 135px;">
			                		<option value="01">01 医院</option>
			                		<option value="02">02 科室</option>
			                	    <option value="03">03 职工</option>
                	</select>
                </td> 
                <td align="left"></td>-->
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"> <input type="checkbox" id = "kpi_hos_type" name="kpi_hos_type"/>医院</td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"><input type="checkbox" id = "kpi_dept_type" name="kpi_dept_type"/>科室</td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"><input type="checkbox" id = "kpi_emp_type" name="kpi_emp_type"/>职工</td>
                <td align="left"></td> 
            </tr>
        </table>
    </form>
   
    </body>
</html>
