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
var dialog = frameElement.dialog;
var data = dialog.get('data');

	$(function() {
		loadDict();
	});
	
	
	function loadDict() {
		$("#ft_my").ligerTextBox({width : 280});
		$("#save").ligerButton();
		$("#cancle").ligerButton();
		
		if('add' == data.type){
			autocomplete("#dept_id", "queryDeptAllInfoDict.do?isCheck=false&is_last=1", "dept_id", "dept_name", true, true, null, null, null, "280");
		}else{
			$("#dept_id").ligerTextBox({width : 280,disabled:true});
			liger.get("dept_id").setValue(data.dept_name);
		}
		
		liger.get("ft_my").setValue(data.ft_my);
	}
	
	function num(obj){
	    obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	    obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
	}
	
	function cancle(){
		 dialog.close();
	}
	//保存
	function save(){
		if(!liger.get("dept_id").getValue()){
			$.ligerDialog.error("请选择科室！");
			return;
		}
		var ft_my = liger.get("ft_my").getValue();
		if(!ft_my){
			$.ligerDialog.error("请输入分摊金额！");
			return;
		}
		ft_my = formatNumber(ft_my,2);
		if(ft_my == '0.00'){
			$.ligerDialog.error("请输入分摊金额！");
			return;
		}
		
		var select = liger.get("dept_id").selected;
		if('add' == data.type){
			var para={
					dept_id : select.dept_id,
					dept_no : select.dept_no,
					ft_my :liger.get("ft_my").getValue(),
					all_ft_my : data.all_ft_my,
					year_month : data.year_month,
					proj_code : data.proj_code,
					ft_para : data.ft_para,
					note : data.note,
					type : data.type
			};
		}else{
			var para={
					dept_id : data.dept_id,
					ft_my :liger.get("ft_my").getValue(),
					all_ft_my : data.all_ft_my,
					year_month : data.year_month,
					proj_code : data.proj_code,
					ft_para : data.ft_para,
					note : data.note,
					type : data.type
			};
		}
		
		ajaxJsonObjectByUrl("saveAccPubCostRegDept.do",para,function(responseData){
            if(responseData.state=="true"){
            	parent.query();
            	parent.page_type = "update";
            	parent.my_box.setValue(responseData.ft_my);
            	parent.loadToolBar();
            	cancle();
            }
        });
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="padding-left: 20px">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;padding-top: 20px"><strong style="color: red;">*</strong>科室：</td>
			<td align="left" class="l-table-edit-td"  style="padding-left: 10px;padding-top: 20px"><input id="dept_id" name="dept_id" /></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;padding-top: 20px"><strong style="color: red;">*</strong>分摊金额：</td>
			<td align="left" class="l-table-edit-td" style="padding-left: 10px;padding-top: 20px"><input id="ft_my" name="ft_my" oninput="num(this)"/></td>	
		</tr>
		<tr>
			<td></td>
			<td align="center" style="padding-left: 10px; padding-top: 20px"><input type="button" id="save" value="保存" onclick="save()"/>
			<input type="button" id="cancle" value="取消" onclick="cancle()"/></td>
		</tr>
	</table>
</body>
</html>
