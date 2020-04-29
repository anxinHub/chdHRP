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
        ajaxJsonObjectByUrl("updateAssEqiuserecordqueue.do",formPara,function(responseData){
            
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
            <td align="left" class="l-table-edit-td"><input name="urq_rowid" type="text" id="urq_rowid" ltype="text" value="${urq_rowid}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仪器来源编码（仪器来源类型：3.设备效益分析）COST_BUSI_SOUREC_DICT：</td>
            <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code" ltype="text" value="${busi_data_source_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统设备ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exdevicecode" type="text" id="urq_exdevicecode" ltype="text" value="${urq_exdevicecode}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exitemcode" type="text" id="urq_exitemcode" ltype="text" value="${urq_exitemcode}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">urqExitemdcode：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exitemdcode" type="text" id="urq_exitemdcode" ltype="text" value="${urq_exitemdcode}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统业务ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exbusscode" type="text" id="urq_exbusscode" ltype="text" value="${urq_exbusscode}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">外部系统科室ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exuseloccode" type="text" id="urq_exuseloccode" ltype="text" value="${urq_exuseloccode}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_usedate" type="text" id="urq_usedate" ltype="text" value="${urq_usedate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工作量：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_workloadnum" type="text" id="urq_workloadnum" ltype="text" value="${urq_workloadnum}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_price" type="text" id="urq_price" ltype="text" value="${urq_price}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">总金额：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_totalfee" type="text" id="urq_totalfee" ltype="text" value="${urq_totalfee}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">为空表示按标准配备数量或者没有单独收费， 不为空表示特殊的单独收费项数量：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_alonepaynum" type="text" id="urq_alonepaynum" ltype="text" value="${urq_alonepaynum}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用患者ID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientid" type="text" id="urq_patientid" ltype="text" value="${urq_patientid}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者姓名：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientname" type="text" id="urq_patientname" ltype="text" value="${urq_patientname}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者性别：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientsex" type="text" id="urq_patientsex" ltype="text" value="${urq_patientsex}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录患者年龄：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_patientage" type="text" id="urq_patientage" ltype="text" value="${urq_patientage}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_startdate" type="text" id="urq_startdate" ltype="text" value="${urq_startdate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开始使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_starttime" type="text" id="urq_starttime" ltype="text" value="${urq_starttime}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_enddate" type="text" id="urq_enddate" ltype="text" value="${urq_enddate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束使用时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_endtime" type="text" id="urq_endtime" ltype="text" value="${urq_endtime}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">操作员：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_operator" type="text" id="urq_operator" ltype="text" value="${urq_operator}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">其他信息：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_otherinfo" type="text" id="urq_otherinfo" ltype="text" value="${urq_otherinfo}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消标志：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_cancelflag" type="text" id="urq_cancelflag" ltype="text" value="${urq_cancelflag}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_remark" type="text" id="urq_remark" ltype="text" value="${urq_remark}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记录医嘱OrderID：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_doctororderid" type="text" id="urq_doctororderid" ltype="text" value="${urq_doctororderid}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用做阳性标志：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_positiveflag" type="text" id="urq_positiveflag" ltype="text" value="${urq_positiveflag}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">标本号/检查号：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_sampleno" type="text" id="urq_sampleno" ltype="text" value="${urq_sampleno}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">曝光次数：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_exposurenum" type="text" id="urq_exposurenum" ltype="text" value="${urq_exposurenum}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_adddate" type="text" id="urq_adddate" ltype="text" value="${urq_adddate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_addtime" type="text" id="urq_addtime" ltype="text" value="${urq_addtime}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">完成日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_finishdate" type="text" id="urq_finishdate" ltype="text" value="${urq_finishdate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">完成时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_finishtime" type="text" id="urq_finishtime" ltype="text" value="${urq_finishtime}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最后执行日期：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_lastexecutedate" type="text" id="urq_lastexecutedate" ltype="text" value="${urq_lastexecutedate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最后执行时间：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_lastexecutetime" type="text" id="urq_lastexecutetime" ltype="text" value="${urq_lastexecutetime}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行次数：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_executetimes" type="text" id="urq_executetimes" ltype="text" value="${urq_executetimes}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"><input name="urq_status" type="text" id="urq_status" ltype="text" value="${urq_status}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
