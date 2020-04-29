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
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
            
           rowid:$("#rowid").val(),
            
           ur_fromtype:$("#ur_fromtype").val(),
            
           ur_eq_group:$("#ur_eq_group").val(),
            
           use_date:$("#use_date").val(),
            
           start_time:$("#start_time").val(),
            
           end_date:$("#end_date").val(),
            
           end_time:$("#end_time").val(),
            
           work_load_num:$("#work_load_num").val(),
            
           unit_code:$("#unit_code").val(),
            
           dept_code:$("#dept_code").val(),
            
           patient_id:$("#patient_id").val(),
            
           patient_sex:$("#patient_sex").val(),
            
           patient_age:$("#patient_age").val(),
            
           patient_name:$("#patient_name").val(),
            
           price:$("#price").val(),
            
           total_fee:$("#total_fee").val(),
            
           alone_pay_num:$("#alone_pay_num").val(),
            
           year:$("#year").val(),
            
           month:$("#month").val(),
            
           charge_kind_id:$("#charge_kind_id").val(),
            
           charge_item_id:$("#charge_item_id").val(),
            
           busi_data_source_code:$("#busi_data_source_code").val(),
            
           ex_id:$("#ex_id").val(),
            
           is_input_flag:$("#is_input_flag").val(),
            
           status:$("#status").val(),
            
           invalid_flag:$("#invalid_flag").val(),
            
           remark:$("#remark").val(),
            
           doctor_order_id:$("#doctor_order_id").val(),
            
           operator:$("#operator").val(),
            
           positive_flag:$("#positive_flag").val(),
            
           sample_no:$("#sample_no").val(),
            
           exposure_num:$("#exposure_num").val(),
            
           start_date:$("#start_date").val(),
            
           other_info:$("#other_info").val(),
            
           add_user:$("#add_user").val(),
            
           add_date:$("#add_date").val(),
            
           add_time:$("#add_time").val(),
            
           update_user:$("#update_user").val(),
            
           update_date:$("#update_date").val(),
            
           update_time:$("#update_time").val(),
            
           cancel_date:$("#cancel_date").val(),
            
           cancel_time:$("#cancel_time").val(),
            
           cancel_user:$("#cancel_user").val(),
            
           submit_date:$("#submit_date").val(),
            
           submit_time:$("#submit_time").val(),
            
           submit_user:$("#submit_user").val(),
            
           audit_date:$("#audit_date").val(),
            
           audit_time:$("#audit_time").val(),
            
           audit_user:$("#audit_user").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssEquserecord.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='rowid']").val('');
				 $("input[name='ur_fromtype']").val('');
				 $("input[name='ur_eq_group']").val('');
				 $("input[name='use_date']").val('');
				 $("input[name='start_time']").val('');
				 $("input[name='end_date']").val('');
				 $("input[name='end_time']").val('');
				 $("input[name='work_load_num']").val('');
				 $("input[name='unit_code']").val('');
				 $("input[name='dept_code']").val('');
				 $("input[name='patient_id']").val('');
				 $("input[name='patient_sex']").val('');
				 $("input[name='patient_age']").val('');
				 $("input[name='patient_name']").val('');
				 $("input[name='price']").val('');
				 $("input[name='total_fee']").val('');
				 $("input[name='alone_pay_num']").val('');
				 $("input[name='year']").val('');
				 $("input[name='month']").val('');
				 $("input[name='charge_kind_id']").val('');
				 $("input[name='charge_item_id']").val('');
				 $("input[name='busi_data_source_code']").val('');
				 $("input[name='ex_id']").val('');
				 $("input[name='is_input_flag']").val('');
				 $("input[name='status']").val('');
				 $("input[name='invalid_flag']").val('');
				 $("input[name='remark']").val('');
				 $("input[name='doctor_order_id']").val('');
				 $("input[name='operator']").val('');
				 $("input[name='positive_flag']").val('');
				 $("input[name='sample_no']").val('');
				 $("input[name='exposure_num']").val('');
				 $("input[name='start_date']").val('');
				 $("input[name='other_info']").val('');
				 $("input[name='add_user']").val('');
				 $("input[name='add_date']").val('');
				 $("input[name='add_time']").val('');
				 $("input[name='update_user']").val('');
				 $("input[name='update_date']").val('');
				 $("input[name='update_time']").val('');
				 $("input[name='cancel_date']").val('');
				 $("input[name='cancel_time']").val('');
				 $("input[name='cancel_user']").val('');
				 $("input[name='submit_date']").val('');
				 $("input[name='submit_time']").val('');
				 $("input[name='submit_user']").val('');
				 $("input[name='audit_date']").val('');
				 $("input[name='audit_time']").val('');
				 $("input[name='audit_user']").val('');
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
   
    function saveAssEquserecord(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
           
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">ID：</td>
            <td align="left" class="l-table-edit-td"><input name="rowid" type="text" id="rowid" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">01：机组:02：设备：</td>
            <td align="left" class="l-table-edit-td"><input name="ur_fromtype" type="text" id="ur_fromtype" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">urEqGroup：</td>
            <td align="left" class="l-table-edit-td"><input name="ur_eq_group" type="text" id="ur_eq_group" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="use_date" type="text" id="use_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="start_time" type="text" id="start_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="end_time" type="text" id="end_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工作量：</td>
            <td align="left" class="l-table-edit-td"><input name="work_load_num" type="text" id="work_load_num" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">unitCode：</td>
            <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">deptCode：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用患者：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_id" type="text" id="patient_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">性别：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_sex" type="text" id="patient_sex" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年龄：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_age" type="text" id="patient_age" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">姓名：</td>
            <td align="left" class="l-table-edit-td"><input name="patient_name" type="text" id="patient_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收取费用：</td>
            <td align="left" class="l-table-edit-td"><input name="total_fee" type="text" id="total_fee" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">为空表示按标准配备数量或者没有单独收费， 不为空表示特殊的单独收费项数量：</td>
            <td align="left" class="l-table-edit-td"><input name="alone_pay_num" type="text" id="alone_pay_num" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月：</td>
            <td align="left" class="l-table-edit-td"><input name="month" type="text" id="month" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用自增ID：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_kind_id" type="text" id="charge_kind_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">chargeItemId：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_id" type="text" id="charge_item_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仪器来源编码（仪器来源类型：3.设备效益分析）COST_BUSI_SOUREC_DICT：</td>
            <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">扩展ID：</td>
            <td align="left" class="l-table-edit-td"><input name="ex_id" type="text" id="ex_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手工录入标志：</td>
            <td align="left" class="l-table-edit-td"><input name="is_input_flag" type="text" id="is_input_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态（0:新增 1:提交 2:审核 3:作废）：</td>
            <td align="left" class="l-table-edit-td"><input name="status" type="text" id="status" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">有效标记：</td>
            <td align="left" class="l-table-edit-td"><input name="invalid_flag" type="text" id="invalid_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录医嘱OrderID：</td>
            <td align="left" class="l-table-edit-td"><input name="doctor_order_id" type="text" id="doctor_order_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录操作员（编码_名字）：</td>
            <td align="left" class="l-table-edit-td"><input name="operator" type="text" id="operator" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">阳性标志：</td>
            <td align="left" class="l-table-edit-td"><input name="positive_flag" type="text" id="positive_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用于标本号/医技号：</td>
            <td align="left" class="l-table-edit-td"><input name="sample_no" type="text" id="sample_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用于曝光次数：</td>
            <td align="left" class="l-table-edit-td"><input name="exposure_num" type="text" id="exposure_num" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始日期：</td>
            <td align="left" class="l-table-edit-td"><input name="start_date" type="text" id="start_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">其他信息：</td>
            <td align="left" class="l-table-edit-td"><input name="other_info" type="text" id="other_info" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增人（SYS_USER）：</td>
            <td align="left" class="l-table-edit-td"><input name="add_user" type="text" id="add_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增日期：</td>
            <td align="left" class="l-table-edit-td"><input name="add_date" type="text" id="add_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增时间：</td>
            <td align="left" class="l-table-edit-td"><input name="add_time" type="text" id="add_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新人（SYS_USER）：</td>
            <td align="left" class="l-table-edit-td"><input name="update_user" type="text" id="update_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新日期：</td>
            <td align="left" class="l-table-edit-td"><input name="update_date" type="text" id="update_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新时间：</td>
            <td align="left" class="l-table-edit-td"><input name="update_time" type="text" id="update_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消日期：</td>
            <td align="left" class="l-table-edit-td"><input name="cancel_date" type="text" id="cancel_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消时间：</td>
            <td align="left" class="l-table-edit-td"><input name="cancel_time" type="text" id="cancel_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消人（SYS_USER）：</td>
            <td align="left" class="l-table-edit-td"><input name="cancel_user" type="text" id="cancel_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">提交日期：</td>
            <td align="left" class="l-table-edit-td"><input name="submit_date" type="text" id="submit_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">提交时间：</td>
            <td align="left" class="l-table-edit-td"><input name="submit_time" type="text" id="submit_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">提交人（SYS_USER）：</td>
            <td align="left" class="l-table-edit-td"><input name="submit_user" type="text" id="submit_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date" type="text" id="audit_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核时间：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_time" type="text" id="audit_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人（SYS_USER）：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_user" type="text" id="audit_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
