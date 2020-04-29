<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    var dataFormat;
    var app_mod_code = '${app_mod_code}';
    var deptNo = '${dept_no}';
    $(function (){
        loadDict();
     	loadForm();
     	
     	 if('${is_acc}'=="1"){

       		liger.get("is_acc").setValue(1);
       		
       		liger.get("is_acc").setText("是");	
       		
       	}else{

       		liger.get("is_acc").setValue(0);
       		
       		liger.get("is_acc").setText("否");
       		
       	}
       	
       	if('${is_prim_cost}'=="1"){

       		liger.get("is_prim_cost").setValue(1);
       		
       		liger.get("is_prim_cost").setText("是");	
       		
       	}else{

       		liger.get("is_prim_cost").setValue(0);
       		
       		liger.get("is_prim_cost").setText("否");	
       		
       	}
       	
       	if('${is_calc_cost}'=="1"){

       		liger.get("is_calc_cost").setValue(1);
       		
       		liger.get("is_calc_cost").setText("是");	
       		
       	}else{

       		liger.get("is_calc_cost").setValue(0);
       		
       		liger.get("is_calc_cost").setText("否");	
       		
       	}
    });  
     
    function save(){
        var formPara={
        dept_id:liger.get("dept_id").getValue().split(",")[0],
        dept_no:deptNo,
        dept_name:'${dept_name}',
        cost_iitem_name:'${cost_iitem_name}',
        cost_item_code:liger.get("cost_item_code").getValue(),
        is_acc:liger.get("is_acc").getValue(),
        is_prim_cost:liger.get("is_prim_cost").getValue(),
        is_calc_cost:liger.get("is_calc_cost").getValue()
        };
        ajaxJsonObjectByUrl("updateHpmCostitemConf"+app_mod_code+".do",formPara,function(responseData){
            
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
   
    function saveCostitemConf(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
        
     	autocomplete("#cost_item_code","../../hpm/queryAphiCostItem.do?isCheck=false","id","text",true,true);
     	
     	liger.get("dept_id").setValue("${dept_id}");
     	
 		liger.get("dept_id").setText("${dept_name}");	
 		
 		liger.get("cost_item_code").setValue("${cost_item_code}");
 		
 		liger.get("cost_item_code").setText("${cost_iitem_name}");

 		

     }   
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" disabled="disabled" ltype="text" value="${dept_id}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目：</td>
				<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" disabled="disabled" ltype="text"
					value="${cost_item_code}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否参与核算：</td>
				<td align="left" class="l-table-edit-td"><select name="is_acc" id="is_acc" style="width: 160px;">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">直接成本：</td>
				<td align="left" class="l-table-edit-td"><select name="is_prim_cost" id="is_prim_cost" style="width: 160px;">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">间接成本：</td>
				<td align="left" class="l-table-edit-td"><select name="is_calc_cost" id="is_calc_cost" style="width: 160px;">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
