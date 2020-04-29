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
            
            
            
           urq_rowid:$("#urq_rowid").val(),
            
           busi_data_source_code:$("#busi_data_source_code").val(),
            
           urq_exdevicecode:$("#urq_exdevicecode").val(),
            
           urq_exitemcode:$("#urq_exitemcode").val(),
            
           urq_exitemdcode:$("#urq_exitemdcode").val(),
            
           urq_exbusscode:$("#urq_exbusscode").val(),
            
           urq_exuseloccode:$("#urq_exuseloccode").val(),
            
           urq_usedate:$("#urq_usedate").val(),
            
           urq_workloadnum:$("#urq_workloadnum").val(),
            
           urq_price:$("#urq_price").val(),
            
           urq_totalfee:$("#urq_totalfee").val(),
            
           urq_alonepaynum:$("#urq_alonepaynum").val(),
            
           urq_patientid:$("#urq_patientid").val(),
            
           urq_patientname:$("#urq_patientname").val(),
            
           urq_patientsex:$("#urq_patientsex").val(),
            
           urq_patientage:$("#urq_patientage").val(),
            
           urq_startdate:$("#urq_startdate").val(),
            
           urq_starttime:$("#urq_starttime").val(),
            
           urq_enddate:$("#urq_enddate").val(),
            
           urq_endtime:$("#urq_endtime").val(),
            
           urq_operator:$("#urq_operator").val(),
            
           urq_otherinfo:$("#urq_otherinfo").val(),
            
           urq_cancelflag:$("#urq_cancelflag").val(),
            
           urq_remark:$("#urq_remark").val(),
            
           urq_doctororderid:$("#urq_doctororderid").val(),
            
           urq_positiveflag:$("#urq_positiveflag").val(),
            
           urq_sampleno:$("#urq_sampleno").val(),
            
           urq_exposurenum:$("#urq_exposurenum").val(),
            
           urq_adddate:$("#urq_adddate").val(),
            
           urq_addtime:$("#urq_addtime").val(),
            
           urq_finishdate:$("#urq_finishdate").val(),
            
           urq_finishtime:$("#urq_finishtime").val(),
            
           urq_lastexecutedate:$("#urq_lastexecutedate").val(),
            
           urq_lastexecutetime:$("#urq_lastexecutetime").val(),
            
           urq_executetimes:$("#urq_executetimes").val(),
            
           urq_status:$("#urq_status").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssEqiuserecordqueue.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='urq_rowid']").val('');
				 $("input[name='busi_data_source_code']").val('');
				 $("input[name='urq_exdevicecode']").val('');
				 $("input[name='urq_exitemcode']").val('');
				 $("input[name='urq_exitemdcode']").val('');
				 $("input[name='urq_exbusscode']").val('');
				 $("input[name='urq_exuseloccode']").val('');
				 $("input[name='urq_usedate']").val('');
				 $("input[name='urq_workloadnum']").val('');
				 $("input[name='urq_price']").val('');
				 $("input[name='urq_totalfee']").val('');
				 $("input[name='urq_alonepaynum']").val('');
				 $("input[name='urq_patientid']").val('');
				 $("input[name='urq_patientname']").val('');
				 $("input[name='urq_patientsex']").val('');
				 $("input[name='urq_patientage']").val('');
				 $("input[name='urq_startdate']").val('');
				 $("input[name='urq_starttime']").val('');
				 $("input[name='urq_enddate']").val('');
				 $("input[name='urq_endtime']").val('');
				 $("input[name='urq_operator']").val('');
				 $("input[name='urq_otherinfo']").val('');
				 $("input[name='urq_cancelflag']").val('');
				 $("input[name='urq_remark']").val('');
				 $("input[name='urq_doctororderid']").val('');
				 $("input[name='urq_positiveflag']").val('');
				 $("input[name='urq_sampleno']").val('');
				 $("input[name='urq_exposurenum']").val('');
				 $("input[name='urq_adddate']").val('');
				 $("input[name='urq_addtime']").val('');
				 $("input[name='urq_finishdate']").val('');
				 $("input[name='urq_finishtime']").val('');
				 $("input[name='urq_lastexecutedate']").val('');
				 $("input[name='urq_lastexecutetime']").val('');
				 $("input[name='urq_executetimes']").val('');
				 $("input[name='urq_status']").val('');
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
   
    function saveAssEqiuserecordqueue(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">URQ_RowID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_rowid" type="text" id="urq_rowid" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仪器来源编码（仪器来源类型：3.设备效益分析）COST_BUSI_SOUREC_DICT：</td>
            <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统设备ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exdevicecode" type="text" id="urq_exdevicecode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exitemcode" type="text" id="urq_exitemcode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">urqExitemdcode：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exitemdcode" type="text" id="urq_exitemdcode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统业务ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exbusscode" type="text" id="urq_exbusscode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统科室ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exuseloccode" type="text" id="urq_exuseloccode" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_usedate" type="text" id="urq_usedate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工作量：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_workloadnum" type="text" id="urq_workloadnum" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_price" type="text" id="urq_price" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">总金额：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_totalfee" type="text" id="urq_totalfee" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">为空表示按标准配备数量或者没有单独收费， 不为空表示特殊的单独收费项数量：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_alonepaynum" type="text" id="urq_alonepaynum" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用患者ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientid" type="text" id="urq_patientid" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者姓名：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientname" type="text" id="urq_patientname" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者性别：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientsex" type="text" id="urq_patientsex" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者年龄：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientage" type="text" id="urq_patientage" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_startdate" type="text" id="urq_startdate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_starttime" type="text" id="urq_starttime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_enddate" type="text" id="urq_enddate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_endtime" type="text" id="urq_endtime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">操作员：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_operator" type="text" id="urq_operator" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">其他信息：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_otherinfo" type="text" id="urq_otherinfo" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消标志：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_cancelflag" type="text" id="urq_cancelflag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_remark" type="text" id="urq_remark" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录医嘱OrderID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_doctororderid" type="text" id="urq_doctororderid" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用做阳性标志：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_positiveflag" type="text" id="urq_positiveflag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">标本号/检查号：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_sampleno" type="text" id="urq_sampleno" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">曝光次数：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exposurenum" type="text" id="urq_exposurenum" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_adddate" type="text" id="urq_adddate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_addtime" type="text" id="urq_addtime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">完成日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_finishdate" type="text" id="urq_finishdate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">完成时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_finishtime" type="text" id="urq_finishtime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最后执行日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_lastexecutedate" type="text" id="urq_lastexecutedate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最后执行时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_lastexecutetime" type="text" id="urq_lastexecutetime" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行次数：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_executetimes" type="text" id="urq_executetimes" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_status" type="text" id="urq_status" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
