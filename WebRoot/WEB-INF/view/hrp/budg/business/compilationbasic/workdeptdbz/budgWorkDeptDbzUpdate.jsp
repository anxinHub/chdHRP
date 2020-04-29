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
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'dept_id',value:$("#dept_id").val()}); 
    	  grid.options.parms.push({name:'year',value:$("#year").val()}); 
    	  grid.options.parms.push({name:'disease_code',value:$("#disease_code").val()}); 
    	  grid.options.parms.push({name:'insurance_code',value:$("#insurance_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    function save(){
        var formPara={
      		 year:liger.get("year").getValue(),
     	     dept_id:liger.get("dept_id").getValue(),
       	     disease_code:liger.get("disease_code").getValue(),
        	 insurance_code:liger.get("insurance_code").getValue(),
             budg_workload:$("#budg_workload").val()
        };
        ajaxJsonObjectByUrl("updateBudgWorkDeptDbz.do?isCheck=false",formPara,function(responseData){
            
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
   
    function savebudgWorkDeptDbz(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	  //医保单病种编码
        autocomplete("#disease_code","../../../queryBudgSingleDisease.do?isCheck=false","id","text",true,true);
       //加载医保类型
        autocomplete("#year","../../../queryBudgYear.do?isCheck=false","id","text",true,true);
      //加载医保类型
        autocomplete("#insurance_code","../../../queryBudgYBType.do?isCheck=false","id","text",true,true);
      //加载科室名称
        autocomplete("#dept_id","../../../queryBudgDeptDict.do?isCheck=false","id","text",true,true);
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门ID：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" value="${dept_id}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" value="${year}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种编码：</td>
            <td align="left" class="l-table-edit-td"><input name="disease_code" type="text" id="disease_code" ltype="text" value="${disease_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医保类型编码：</td>
            <td align="left" class="l-table-edit-td"><input name="insurance_code" type="text" id="insurance_code" ltype="text" value="${insurance_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务预算：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_workload" type="text" id="budg_workload" ltype="text" value="${budg_workload}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
