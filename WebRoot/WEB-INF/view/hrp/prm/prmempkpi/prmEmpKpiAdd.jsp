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
     var dialog = frameElement.dialog;
     var dialogData; //获取data参数
     
     $(function (){
         loadDict()//加载下拉框
         loadForm();
         
         $("#kpi_code").ligerTextBox({width:160});
         $("#kpi_name").ligerTextBox({width:458});
         $("#dim_code").ligerTextBox({width:180});
         $("#nature_code").ligerTextBox({width:160});
         $("#is_last").ligerTextBox({width:160});
         $("#acc_year").ligerTextBox({width:180});
         $("#goal_code").ligerTextBox({width:180});
         $("#super_kpi_code").ligerTextBox({width:180});
         $("#dept_code").ligerTextBox({width:160});
         
         var dates = getCurrentDate();
         var acc_year = dates.split(";")[0]
         $('#acc_year').val(acc_year);
     });  
     
     //保存
     function  save(){
       var acc_year = $('#acc_year').val();
       if(acc_year == ''){
    	   $.ligerDialog.warn('指标年度不能为空 ');
    	   return ; 
       }
       
       var goal_code = liger.get('goal_code').getValue();
       if(goal_code == ''){
    	   $.ligerDialog.warn('目标名称不能为空 ');
    	   return ; 
       }
       
       var dept_code = liger.get('dept_code').getValue();
       if(dept_code == ''){
    	   $.ligerDialog.warn('科室名称不能为空 ');
    	   return ; 
       }
       
       var dept_id = dept_code.split(".")[0];
       var dept_no =  dept_code.split(".")[1];
       
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
    	   $.ligerDialog.warn('维度名称不能为空 ');
    	   return ; 
       }
       
       var nature_code = $("#nature_code").val();
       if(nature_code == ''){
    	   $.ligerDialog.warn('指标性质不能为空 ');
    	   return ; 
       }
       
       var super_kpi_code = liger.get('super_kpi_code').getValue();
       if(super_kpi_code == ''){
    	   $.ligerDialog.warn('上级编码不能为空 ');
    	   return ; 
       }
       
       var is_last = $("#is_last").val();
       if(is_last == ''){
    	   $.ligerDialog.warn('是否末級不能为空 ');
    	   return ; 
       }
       
       dialogData = dialog.get('data');//获取data参数
       
		var formPara={
    	   hos_id : dialogData.hos_id,
           acc_year :acc_year,
           goal_code :goal_code,
           dept_id:dept_id,
           dept_no:dept_no,
           kpi_code:kpi_code,
           kpi_name:kpi_name,
           nature_code:nature_code,
           dim_code:dim_code,
           super_kpi_code:super_kpi_code,
           is_last:$('#is_last').val() == null ? '0' :$('#is_last').val(),
           
         };
        ajaxJsonObjectByUrl("addPrmEmpKpi.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='kpi_code']").val('');
				 $("input[name='kpi_name']").val('');
				 $("input[name='nature_code']").val('');
				 $("input[name='dim_code']").val('');
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
   	
	//确定
    function addPrmEmpKpi(){
        if($("form").valid()){
            save();
        }
   }
	
    //字典下拉框
    function loadDict(){
        autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",true);//目标名称
        autocomplete("#dept_code","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",true);//科室名称
    	autocomplete("#dim_code","../queryPrmKpiDim.do?isCheck=false","id","text",true,true);//维度名称
        autocomplete("#super_kpi_code","../queryPrmEmpKpiSuperKpiCode.do?isCheck=false","id","text",true,true,"",true);//上级指标
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" id = "table2" width="100%">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
                	<span style="size: 2;color: red">*</span>目标名称：
                </td>
                
                <td align="left" class="l-table-edit-td" >
                	<input name="goal_code" type="text" id="goal_code" ltype="text" validate="{maxlength:20}"/>
               	</td>
                
                <td align="right" class="l-table-edit-td">
            		<span style="size: 2;color: red">*</span>科室名称：
            	</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dept_code" type="text" id="dept_code" ltype="text" />
                </td>
            </tr> 
            
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
                	<span style="size: 2;color: red">*</span>指标年度：
                </td>
                <td align="left" class="l-table-edit-td">
                	<input name="acc_year" type="text" id="acc_year" ltype="text" class="Wdate" 
                	onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2016-01-01'})" value="${acc_year}"/>
               	</td>
               	
            	
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
                	<span style="size: 2;color: red">*</span>指标编码：
                </td>
                <td align="left" class="l-table-edit-td">
                	<input name="kpi_code" type="text" id="kpi_code" ltype="text" validate="{maxlength:20}" value="${kpi_code}"/>
               	</td>
            </tr> 
            
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
            	 	<span style="size: 2;color: red">*</span>指标名称：
            	</td>
            	
                <td align="left" class="l-table-edit-td" colspan="3">
                	<input name="kpi_name" type="text" id="kpi_name" ltype="text" validate="{maxlength:40}" value="${kpi_name}"/>
                </td>
            </tr>
            
             <tr>
             	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
             		<span style="size: 2;color: red">*</span>上级编码：
             	</td>
                <td align="left" class="l-table-edit-td">
                	<input name="super_kpi_code" type="text" id="super_kpi_code" ltype="text" validate="{maxlength:20}"/>
                </td>
             
             	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
             		<span style="size: 2;color: red">*</span>指标性质：
             	</td>
                <td align="left" class="l-table-edit-td">
                	<select id="nature_code" name="nature_code" style="width: 200px;">
			        	<option value="01">01 正向</option>
			           	<option value="02">02 反向</option>
                	</select>
                </td>
            </tr>
            
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
            		<span style="size: 2;color: red">*</span>维度名称：
            	</td>
                <td align="left" class="l-table-edit-td">
                	<input name="dim_code" type="text" id="dim_code" ltype="text" validate="{maxlength:20}"/>
                </td>
            	
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
            	<span style="size: 2;color: red">*</span>是否末级：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_last" name="is_last" style="width: 200px;">
                		<option value=""></option>
			            <option value="1">是</option>
			            <option value="0">否</option>
                	</select>
                </td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
