<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<script type="text/javascript">

$(function() {
	loadDict();
	 $('input:checkbox').ligerCheckBox();
});

function loadDict(){
	//凭证类型
	autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", false, true,'',true,'',100);
	$("#vouch_type_code").ligerComboBox({ cancelable: false });
	
	//科目级次
	autocompleteAsync("#subj_level","../../querySubjLevel.do?isCheck=false","id","text",false,true,'',true,'',100);
	var subjCom=$("#subj_level").ligerComboBox({ cancelable: false });
	//subjCom.addItem({id:100,text:'辅助核算'});
	subjCom.setValue(99,'末级');
	
	//设置凭证起止日期
	var acc_month=getMonthDate('${acc_year}','${acc_month}');
	$("#create_date_b").val(acc_month.split(";")[0]);
	$("#create_date_e").val(acc_month.split(";")[1]);
	
	//凭证号
	$("#vouch_no_b").ligerTextBox({width:90 });
	$("#vouch_no_e").ligerTextBox({width:90 });
	
	//科目
	autocomplete("#subj_code_b","querySubjNameAll.do?isCheck=false","id","text",true,true,"","","",subjWidth);
	$("#subj_code_b").ligerComboBox({ width:475 });
	
	//制单人
	autocomplete("#create_user", "../../../sys/queryUserDict.do?isCheck=false","id", "text", true, true,'',false,'',100);
	
	$("input[name='controllerButton']").ligerButton({width:120});
	
}

//打印模板设置
function myPrintSet(){
	parent.$.ligerDialog.open({url : 'hrp/acc/accvouch/superPrint/printSetPage.do?template_code=01001',
		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
	/* 	buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.mySave(); },cls:'l-dialog-btn-highlight' },
		           { text: '打印预览', onclick: function (item, dialog) { dialog.frame.myPrint(); },cls:'l-dialog-btn-highlight' },
		           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] */
		});
}


//打印
function myPrint(flag){
	
	 var start_date = $("#create_date_b").val() + " 00:00"; 
	 var end_date = $("#create_date_e").val() + " 00:00"; 
	 start_date = new Date(start_date.replace(/-/g, "/")); 
	 end_date = new Date(end_date.replace(/-/g, "/")); 
	 if(start_date.getMonth() != end_date.getMonth()) { 
		  $.ligerDialog.error("不支持跨月打印！"); 
	      return false;  
	 } 

	/* var urlPara={
			vouch_id:1,page:1,
			vouch_date_b:$("#create_date_b").val(),
			vouch_date_e:$("#create_date_e").val(),
			vouch_type_code:liger.get("vouch_type_code").getValue(),
			subj_level:liger.get("subj_level").getValue(),
			vouch_no_b:$("#vouch_no_b").val(),
			vouch_no_e:$("#vouch_no_e").val(),
			create_user:liger.get("create_user").getValue()
			}
	var parintPara=null;
	
	if(flag==1){
		
		parintPara={
				print_para_code:"",
				template_code:"01001",
				p_003:true//套打，不加此参数走打印参数表的设置
		}
	
	}else{
		parintPara={
				print_para_code:"",
				template_code:"01001",
				p_003:false//模板打印，不加此参数走打印参数表的设置
		}
	} */
	if($("#create_date_b").val())
	var para={
			vouch_date_b:$("#create_date_b").val(),
			vouch_date_e:$("#create_date_e").val(),
			vouch_type_code:liger.get("vouch_type_code").getValue(),
			subj_level:liger.get("subj_level").getValue(),
			vouch_no_b:$("#vouch_no_b").val(),
			vouch_no_e:$("#vouch_no_e").val(),
			create_user:liger.get("create_user").getValue(),
			subj_code:liger.get("subj_code_b").getValue(),
			template_code:'01001',
			isPrintCount:false,//更新打印次数
			isPreview:true,//预览窗口，传绝对路径
			isSetPrint:flag
	};
		
	//printTemplate("../superVouch/querySuperVouchPrintBatch.do",para);
	printTemplate("hrp/acc/accvouch/superVouch/querySuperVouchPrintBatch.do",para);
	
}


function myPrintSet1(){
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${sessionScope.user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"01001"});
}

function myPrint1(flag){

	var isPreview=true;
	if(liger.get("isPreview").getValue()){
		isPreview=false;
	}
	
	var p019="否";
	if(liger.get("p019").getValue()){
		p019="是";
	}
	
	var para={
			template_code:'01001',
			class_name:"com.chd.hrp.acc.service.vouch.SuperVouchService",
			method_name:"querySuperVouchPrintBatchPage",
			bean_name:"superVouchService",
			isSetPrint:flag,//是否套打，默认非套打
			isPreview:isPreview,//是否预览，默认直接打印
			p019:p019,//打印辅助核算 
			vouch_date_b:$("#create_date_b").val(),
			vouch_date_e:$("#create_date_e").val(),
			vouch_type_code:liger.get("vouch_type_code").getValue(),
			subj_level:liger.get("subj_level").getValue(),
			vouch_no_b:$("#vouch_no_b").val(),
			vouch_no_e:$("#vouch_no_e").val(),
			create_user:liger.get("create_user").getValue(),
			subj_code:liger.get("subj_code_b").getValue()
	};
	
	officeFormPrint(para);
	
}


function myPrintSet2(){
	officeFormTemplate({template_code:"01002"});
}

</script>
</head>

<body style="padding: 50px; overflow: hidden;"  onload="">
<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>凭证日期：</td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="create_date_b" type="text" id="create_date_b" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" style="width: 90px;" /> 
			</td>
			<td>至&nbsp;</td>
			<td width="150px"><input class="Wdate" name="create_date_e" type="text" id="create_date_e" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" style="width: 90px;" />
			</td>
			 <td align="right" class="l-table-edit-td"><b><font color="red">*</font></b>科目级次：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="subj_level" type="text" id="subj_level" ltype="text" />
            </td>
		</tr>
		<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证号：</td>
            <td align="left" class="l-table-edit-td" width="90px">
            	<input name="vouch_no_b" type="text" id="vouch_no_b" ltype="text" value="1"/>
            </td>
            <td>至&nbsp;</td>
            <td><input name="vouch_no_e" type="text" id="vouch_no_e" ltype="text" value="2"/></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>凭证类型：</td>
			<td align="left" class="l-table-edit-td">
				<input	name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"/>
			</td>
         </tr> 
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
            <td align="left" class="l-table-edit-td" colspan="5">
            	<input name="subj_code_b" type="text" id="subj_code_b" ltype="text" />
            </td>
         </tr>
         <tr>
	          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
	          <td align="left" class="l-table-edit-td">
	          	<input name="create_user" id="create_user" ltype="text" />
	          </td>
	          <td colspan="4"></td>
         </tr>
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td" colspan="5">
            	<input type="checkbox" id="isPreview"/>直接打印
            	&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="p019"/>打印辅助核算
            </td>
         </tr>
         <tr style="height:50px"><td>&nbsp;</td></tr>
         <tr>
         	<td colspan="6" style="padding-left:100px;">
         		<!--input type="button" accessKey="S" name="controllerButton" value=" 打印模板设置spread（S）" onclick="myPrintSet();"/>
         		
         		<input type="button" accessKey="T" name="controllerButton" value=" 凭证套打（T）" onclick="myPrint('true');"/>
         		
         		<input type="button" accessKey="P" name="controllerButton" value=" 凭证打印（P）" onclick="myPrint('false');"/-->
         		
         		<input type="button" accessKey="G" name="controllerButton" value=" 表格模板设置（G）" onclick="myPrintSet2();"/>
         		
         		<input type="button" accessKey="S" name="controllerButton" value=" 凭证模板设置（S）" onclick="myPrintSet1();"/>
         		
         		<input type="button" accessKey="T" name="controllerButton" value=" 凭证套打（T）" onclick="myPrint1('true');"/>
         		
         		<input type="button" accessKey="P" name="controllerButton" value=" 凭证打印（P）" onclick="myPrint1('false');"/>
         		
         	</td>
         </tr>
	</table>
</body>
</html>
