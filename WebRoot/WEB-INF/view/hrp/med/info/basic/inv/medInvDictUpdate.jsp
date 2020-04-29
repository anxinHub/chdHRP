<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
		if("${medInvDict.is_single_ven}" == 1){
			$("#is_single_ven").prop("checked", true);
		}
		if("${medInvDict.is_charge}" == 1){
			$("#is_charge").prop("checked", true);
		}
		if("${medInvDict.is_highvalue}" == 1){
			$("#is_highvalue").prop("checked", true);
		}
		if("${medInvDict.is_dura}" == 1){
			$("#is_dura").prop("checked", true);
		}
		if("${medInvDict.is_com}" == 1){
			$("#is_com").prop("checked", true);
		}
		if("${medInvDict.is_bar}" == 1){
			$("#is_bar").prop("checked", true);
		}
		if("${medInvDict.is_per_bar}" == 1){
			$("#is_per_bar").prop("checked", true);
		}
		if("${medInvDict.is_quality}" == 1){
			$("#is_quality").prop("checked", true);
		}
		if("${medInvDict.is_disinfect}" == 1){
			$("#is_disinfect").prop("checked", true);
		}
		if("${medInvDict.is_sec_whg}" == 1){
			$("#is_sec_whg").prop("checked", true);
		}
		if("${medInvDict.is_shel_make}" == 1){
			$("#is_shel_make").prop("checked", true);
		}
    });  
     
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
     //$("form").ligerForm();
    }      
    
    function loadDict(){
    	//字典下拉框
		autocomplete("#med_type_code", "../../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : '1'}, false, "${medInvDict.med_type_id},${medInvDict.med_type_no}");
		var paras = {
			field_code : "price_type"
		}
		autocomplete("#price_type", "../../../queryMedSysList.do?isCheck=false", "id", "text", true, true, paras, false, "${medInvDict.price_type}");
		paras = {
			field_code : "amortize_type"
		}
		autocomplete("#amortize_type", "../../../queryMedSysList.do?isCheck=false", "id", "text", true, true, paras, false, "${medInvDict.amortize_type}");
		autocomplete("#unit_code", "../../../queryHosUnit.do?isCheck=false", "id", "text", true, true, "", false, "${medInvDict.unit_code}");
		autocomplete("#fac_code", "../../../queryHosFac.do?isCheck=false", "id", "text", true, true, "", false, "${medInvDict.fac_id}");

        $("#use_state").ligerComboBox({width:160, disabled:true, cancelable: false});
        liger.get("use_state").setValue("${medInvDict.use_state}");
		liger.get("use_state").setText("${medInvDict.use_state}" == 1 ? "是" : "否");
		//autocomplete("#use_state", "../../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true, "", false, "${medInvDict.use_state}");
		autoCompleteByData("#abc_type", medInv_ABCType.Rows, "id", "text", true, true, "", false, "${medInvDict.abc_type}");
		
		autocomplete("#med_sx_id", "../../../queryMedSx.do?isCheck=false", "id", "text", true, true, "", false);
		liger.get("med_sx_id").setValue("${medInvDict.med_sx_id}");
		liger.get("med_sx_id").setText("${medInvDict.med_sx_name}");
    	
		//药品剂型
		autocomplete("#med_jx_id", "../../../queryMedJx.do?isCheck=false", "id", "text", true, true, "", false);
		liger.get("med_jx_id").setValue("${medInvDict.med_jx_id}");
		liger.get("med_jx_id").setText("${medInvDict.med_jx_name}");
		
		$("#inv_code").ligerTextBox({width:160});
		$("#inv_name").ligerTextBox({width:160});
		$("#alias").ligerTextBox({width:160});
		$("#inv_model").ligerTextBox({width:160});
		$("#plan_price").ligerTextBox({width:160, number:true, precision:'${p08006 }'});
		$("#price_rate").ligerTextBox({width:160, number:true});
		$("#sell_price").ligerTextBox({width:160, number:true, precision:'${p08006 }'});
		$("#agent_name").ligerTextBox({width:160});
		$("#brand_name").ligerTextBox({width:160});
		$("#inv_usage").ligerTextBox({width:160});
		$("#inv_structure").ligerTextBox({width:160});
		$("#sdate").ligerTextBox({width:160});
		$("#edate").ligerTextBox({width:160});
		$("#per_weight").ligerTextBox({width:160});
		$("#per_volum").ligerTextBox({width:160});
		$("#change_note").ligerTextBox({width:320});
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Esc', this_close);
	 }
	
	function this_close(){
		frameElement.dialog.close();
	} 
	</script>
	</head>
  
	<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="padding: 10px">
	        <tr>
	            <td align="right" class="l-table-edit-td"   width="10%">
	            	<span style="color:red">*</span>药品编码：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="inv_code" type="text" id="inv_code" ltype="text" required="true" value="${medInvDict.inv_code}" validate="{required:true,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  width="10%">
	            	<span style="color:red">*</span>药品名称：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="inv_name" type="text" id="inv_name" ltype="text" required="true" value="${medInvDict.inv_name}" validate="{required:true,maxlength:50}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  width="10%">
	            	别名：
	            </td>
	            <td align="left" class="l-table-edit-td" width="20%">
	            	<input name="alias" type="text" id="alias" ltype="text" required="false" value="${medInvDict.alias}"  validate="{required:false}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>药品类别：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>计价方法：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_type" type="text" id="price_type" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>药品属性：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="med_sx_id" type="text" id="med_sx_id" ltype="text"  validate="{required:true}" />
	            </td>
			</tr>
			 <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>药品剂型：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="med_jx_id" type="text" id="med_jx_id" ltype="text"   validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>病区拆零系数：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bqplxs" type="text" id="bqplxs" ltype="text"  validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>门诊拆零系数：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="mzplxs" type="text" id="mzplxs" ltype="text"  validate="{required:true}" />
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td"  >
	            	规格型号：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_model" type="text" id="inv_model" ltype="text" value="${medInvDict.inv_model}" validate="{required:false,maxlength:200}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	<span style="color:red">*</span>计量单位：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="unit_code" type="text" id="unit_code" ltype="text" validate="{required:true}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	<span style="color:red">*</span>生产厂商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="fac_code" type="text" id="fac_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	计划价：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="plan_price" type="text" id="plan_price" ltype="text" value="${medInvDict.plan_price}"  validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	加价率：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="price_rate" type="text" id="price_rate" ltype="text" value="${medInvDict.price_rate}" validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	零售价：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sell_price" type="text" id="sell_price" ltype="text" value="${medInvDict.sell_price}" validate="{required:false,maxlength:20}" />
	            </td>
	        </tr> 
	        <tr>
	        	 <td align="right" class="l-table-edit-td"  >
	        		启用日期：
	        	</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="sdate" id="sdate" type="text" value="${medInvDict.sdate}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	停用日期：
	            </td>
	            <td align="left" class="l-table-edit-td">
		            <input class="Wdate" name="edate" id="edate" type="text" value="${medInvDict.edate}" onchange="changeDate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	代理商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="agent_name" type="text" id="agent_name" ltype="text" value="${medInvDict.agent_name}" validate="{required:false,maxlength:60}" />
	            </td>
	        </tr>
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	品牌名称：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="brand_name" type="text" id="brand_name" ltype="text" value="${medInvDict.brand_name}" validate="{required:false,maxlength:60}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
					药品用途：
				</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_usage" type="text" id="inv_usage" ltype="text" value="${medInvDict.inv_usage}" validate="{required:false,maxlength:60}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >
	            	药品结构：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_structure" type="text" id="inv_structure" ltype="text" value="${medInvDict.inv_structure}" validate="{required:false,maxlength:60}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" >
	            	单位重量：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="per_weight" type="text" id="per_weight" ltype="text" value="${medInvDict.per_weight}" validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	单位体积：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="per_volum" type="text" id="per_volum" ltype="text" value="${medInvDict.per_volum}" validate="{required:false,maxlength:20}" />
	            </td>
	            <td align="right" class="l-table-edit-td"  >
	            	ABC分类：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="abc_type" type="text" id="abc_type" ltype="text" validate="{required:false}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  >
	            	在用状态：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="use_state" type="text" id="use_state" ltype="text" validate="{required:false}" />
	            </td> 
	            <td align="right" class="l-table-edit-td"  >
	            	<input name="is_single_ven" type="checkbox" id="is_single_ven" ltype="text" validate="{required:false}" />
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	是否唯一供应商
	            </td>
	        </tr>
	        <tr style="display:none">
        		 <td align="right" class="l-table-edit-td"  >
	            	摊销方式：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="amortize_type" type="text" id="amortize_type" ltype="text" validate="{required:false}"  />
	            </td>
	           
        		
        	</tr>
	        <tr>
	        	<td class="l-table-edit-td" colspan="6">
	        		<table cellpadding="0" cellspacing="0" style="margin-top: 20px;" class="l-table-edit" border="1px;" width="100%">
	        			<tr height="30px" style="background: url(../../../../../lib/ligerUI/skins/Aqua/images/grid/header-bg.gif) repeat;);">
	        				<td align="center" style="display:none"  width="6%">是否收费</td>
	        				<td align="center" style="display:none"  width="6%">是否高值</td>
	        				<td align="center" style="display:none"  width="8%">是否耐用品</td>
	        				<td align="center" style="display:none"  width="6%">是否代销</td>
	        				<td align="center" width="10%">是否条码管理</td>
	        				<td align="center" width="8%">是否个体码</td>
	        				<td align="center" width="10%">是否保质期管理</td>
	        				<td align="center" style="display:none"  width="10%">是否灭菌药品</td>
	        				<td align="center" width="10%">是否证件管理</td>
	        				<td align="center" width="10%">是否科室库管理</td>
	        				<td align="center" width="8%">是否自制品</td>
	        			</tr>
	        			<tr>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_charge" type="checkbox" id="is_charge" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_highvalue" type="checkbox" id="is_highvalue" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_dura" type="checkbox" id="is_dura" ltype="text" />
	      					</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_com" type="checkbox" id="is_com" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_bar" type="checkbox" id="is_bar" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_per_bar" type="checkbox" id="is_per_bar" ltype="text"/>
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_quality" type="checkbox" id="is_quality" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td" style="display:none">
	        					<input name="is_disinfect" type="checkbox" id="is_disinfect" ltype="text"/>
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_cert" type="checkbox" id="is_cert" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_sec_whg" type="checkbox" id="is_sec_whg" ltype="text" />
	        				</td>
	        				<td align="center" class="l-table-edit-td">
	        					<input name="is_shel_make" type="checkbox" id="is_shel_make" ltype="text" />
	        				</td>
	        			</tr>
	        		</table>
	        	</td>
	        </tr>
	        <tr><td colspan="6">&nbsp;</td></tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" >
	        		修改说明：
	        	</td>
	        	<td align="left" class="l-table-edit-td" colspan="3">
	        		<input name="change_note" type="text" id="change_note" ltype="text" value="${medInvDict.change_note}" validate="{required:false,maxlength:100}" />
	        	</td>
	        </tr>
	    </table>
    </form>
    </body>
</html>
