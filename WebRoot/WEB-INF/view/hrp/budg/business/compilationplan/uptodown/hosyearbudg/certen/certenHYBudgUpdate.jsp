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
        
    });  
     
    function save(){
        var formPara={
        year:$("#year").val(),
        index_code:$("#index_code").val(),
        count_value:$("#count_value").val(),
        budg_value:$("#budg_value").val(),
        remark:$("#remark").val(),
        grow_rate:$("#grow_rate").val(),
        grow_value:$("#grow_value").val(),
        last_year_workload:$("#last_year_workload").val(),
        hos_suggest:$("#hos_suggest").val(),
        dept_suggest_sum:$("#dept_suggest_sum").val()
        };
        ajaxJsonObjectByUrl("updateZeroHYBudgUp.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveBudgHosIndependentSubj(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#year","../../../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",false,"${year}",200);
        
    	budg_year = liger.get("year").getValue();
		
    	 //预算指标下拉框budg_level （01医院年度 02医院月份 03科室年度 04科室月份 ）编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 
        autocomplete("#index_code","../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=01&edit_method=01&budg_year="+budg_year,"id","text",true,true,'',false,'${index_code}',200);
    
    }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>年度<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" disabled="disabled" ltype="text"  validate="{required:true,maxlength:4}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算指标<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" disabled="disabled" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计算值：</td>
            <td align="left" class="l-table-edit-td"><input name="count_value" type="text" id="count_value" ltype="text" value="${count_value}" validate="{number:true，maxlength:18}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算值：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_value" type="text" id="budg_value" ltype="text" value="${budg_value}" validate="{number:true,maxlength:18}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" value="${remark}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">增长比例：</td>
            <td align="left" class="l-table-edit-td"><input name="grow_rate" type="text" id="grow_rate" ltype="text" value="${grow_rate}" validate="{number:true,maxlength:18}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">增加额：</td>
            <td align="left" class="l-table-edit-td"><input name="grow_value" type="text" id="grow_value" ltype="text" value="${grow_value}" validate="{number:true,maxlength:18}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上年收入：</td>
            <td align="left" class="l-table-edit-td"><input name="last_year_income" type="text" id="last_year_workload" ltype="text" value="${last_year_workload}" validate="{last_year_workload,maxlength:18}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医院意见：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_suggest" type="text" id="hos_suggest" ltype="text" value="${hos_suggest}" validate="{last_year_workload,maxlength:18}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室意见汇总：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_suggest_sum" type="text" id="dept_suggest_sum" ltype="text" value="${dept_suggest_sum}" validate="{last_year_workload,maxlength:18}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
