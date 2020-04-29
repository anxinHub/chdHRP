<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

    var dataFormat;
    
    $(function (){
		
     	loadDict()//加载下拉框
        loadForm();
    });  
     
    function save(){
        var formPara={
                group_id:'${group_id}',
                hos_id:'${hos_id}',
                copy_code:'${copy_code}',
        		year_month:$("#year_month").val(),
        		dept_no:liger.get("dept_code").getValue().split(".")[1],
        		dept_id:liger.get("dept_code").getValue().split(".")[0],
           		mate_code:liger.get("mate_code").getValue(),
           		is_charge:liger.get("is_charge").getValue(),
           		num:$("#num").val(),
           		amount:$("#amount").val(),
           		source_id:liger.get("source_code").getValue()
        };
        ajaxJsonObjectByUrl("updateHtcDeptMaterialDetail.do",formPara,function(responseData){
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
   
    function saveDeptMaterialDetail(){
    	
        if($("form").valid()){
        	
            save();
            
        }
    }
    function loadDict(){
    	autocomplete("#dept_code","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true);
    	liger.get("dept_code").setValue('${dept_no}.${dept_id}');
     	liger.get("dept_code").setText('${dept_name}');
      	autocomplete("#mate_code","../../../info/base/queryHtcMaterialDict.do?isCheck=false","id","text",true,true);
      	liger.get("mate_code").setValue('${mate_code}');
     	liger.get("mate_code").setText('${mate_name}');
      	autocomplete("#is_charge", "../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,"${is_charge}");
      	autocomplete("#source_code","../../../info/base/queryHtcSourceDict.do?isCheck=false","id","text",true,true);
      	liger.get("source_code").setValue('${source_id}');
     	liger.get("source_code").setText('${source_name}');
     	$("#year_month").ligerTextBox({disabled:true});
     	$("#dept_code").ligerTextBox({disabled:true});
     	$("#mate_code").ligerTextBox({disabled:true});
    }
    </script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
            <tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
				<td align="left" class="l-table-edit-td">
					<input class="Wdate" name="year_month" type="text" id="year_month" ltype="text" validate="{required:true}" value="${acc_year}${acc_month}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">物资材料：</td>
				<td align="left" class="l-table-edit-td">
					<input name="mate_code" type="text" id="mate_code" ltype="text" validate="{required:true}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">收费标志：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true}"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">数量：</td>
				<td align="left" class="l-table-edit-td">
					<input name="num" type="text" id="num" ltype="text" validate="{required:true}" value="${num}"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">金额：</td>
				<td align="left" class="l-table-edit-td">
					<input name="amount" type="text" id="amount" ltype="text" validate="{required:true}" value="${amount}"/>
				</td>
				<td align="left"></td>
			</tr>
		    <tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资金来源：</td>
				<td align="left" class="l-table-edit-td">
					<input name="source_code" type="text" id="source_code" ltype="text" validate="{required:true}" />
				</td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
