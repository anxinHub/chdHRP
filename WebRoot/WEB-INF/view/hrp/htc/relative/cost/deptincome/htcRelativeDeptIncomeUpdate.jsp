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
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
    
        var formPara={
        		
	    		acc_year:$("#year_month").val().substring(0,4),
	            
	            acc_month:$("#year_month").val().substring(4,6),

	            income_detail_id:"${income_detail_id}",

                appl_dept_id:liger.get("appl_dept").getValue().split(".")[0],
                
                appl_dept_no:liger.get("appl_dept").getValue().split(".")[1],

                exec_dept_id:liger.get("exec_dept").getValue().split(".")[0],
                
                exec_dept_no:liger.get("exec_dept").getValue().split(".")[1],
                 
                charge_kind_id:liger.get("charge_kind_id").getValue().split(".")[0],
                 
                charge_item_id:liger.get("charge_item_id").getValue().split(".")[0],
                 
                num:$("#num").val(),
                 
                money:$("#money").val(),

                busi_data_source_code:liger.get("busi_data_source_code").getValue(),
        };
        ajaxJsonObjectByUrl("updateHtcRelativeDeptIncome.do",formPara,function(responseData){
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
   
    function saveDeptIncome(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	 /* 开单科室为临床科室  */
        var appl_param = {type_code:"('01')"};
        /* 执行科室为临床科室和医技科室  */
	    var exec_param = {type_code:"('01','02')"};
		 autocomplete("#appl_dept","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true,appl_param);
		 liger.get("appl_dept").setValue("${appl_dept_id}.${appl_dept_no}");
	     liger.get("appl_dept").setText("${appl_dept_name}");
		 autocomplete("#exec_dept","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true,exec_param); 
		 liger.get("exec_dept").setValue("${exec_dept_id}.${exec_dept_no}");
	     liger.get("exec_dept").setText("${exec_dept_name}");
		 autocomplete("#charge_kind_id","../../../info/base/queryHtcChargeKindArrt.do?isCheck=false","id","text",true,true);
		 liger.get("charge_kind_id").setValue("${charge_kind_id}");
	     liger.get("charge_kind_id").setText("${charge_kind_name}");
		 autocomplete("#busi_data_source_code","../../../info/base/queryHtcDataSource.do?isCheck=false", "id", "text", true, true,{busi_data_source_type:1});/* 1：收入数据来源 2.成本数据来源(必填) */
		 liger.get("busi_data_source_code").setValue("${busi_data_source_code}");
	     liger.get("busi_data_source_code").setText("${busi_data_source_name}");
	  	 var param = {charge_kind_id:"${charge_kind_id}"};
	     autocomplete("#charge_item_id","../../../info/base/queryHtcChargeItemArrt.do?isCheck=false","id","text",true,true,param,false,"","180"); 
		 liger.get("charge_item_id").setValue("${charge_item_id}");
		 liger.get("charge_item_id").setText("${charge_item_name}");
		 autodate("#year_month","YYYYmm");

		 
		 $("#year_month").ligerTextBox({disabled:true});
     }   
    function charge_kind(){
        
    	var param = {charge_kind_id:liger.get("charge_kind_id").getValue()};
     	 $("#charge_item_id").val(""); 
 		 autocomplete("#charge_item_id","../../../info/base/queryHtcChargeItemArrt.do?isCheck=false","id","text",true,true,param,false,"","180"); 
	}
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="year_month" type="text" id="year_month"  disabled="disabled"  ltype="text"  value="${acc_year}${acc_month}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
                <td align="left" class="l-table-edit-td"><input name="appl_dept" type="text" id="appl_dept"  ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室：</td>
                <td align="left" class="l-table-edit-td"><input name="exec_dept" type="text" id="exec_dept"  ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_kind_id" type="text" id="charge_kind_id"  ltype="text" validate="{required:true}" onchange="charge_kind();"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_item_id" type="text" id="charge_item_id"  ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数量：</td>
                <td align="left" class="l-table-edit-td"><input name="num" type="text" id="num" ltype="text" value="${num}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
                <td align="left" class="l-table-edit-td"><input name="money" type="text" id="money" ltype="text" value="${money}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据来源：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
