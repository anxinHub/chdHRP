<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>	
<script type="text/javascript">	
	$(function(){
		loadDict();
	});
	
	function loadDict(){
		//初始参数
		var para = {acc_year: "${acc_year}"};
		//会计期间
		autocompleteObj({
			id:  '#acc_year', 
			urlStr: 	"../../queryAccYear.do?isCheck=false", 
			valueField:  'id', 
			textField:    'text' , 
			autocomplete: true, 
			highLight: true, 
			defaultSelect:  false,  
			initvalue: "${acc_year}", 
			initWidth: 70, 
			selectEvent: function(value){
				var para1 = {
					acc_year: value
				}
				liger.get("begin_month").clear();
				liger.get("begin_month").set("parms", para1);
				liger.get("begin_month").reload();
				liger.get("end_month").clear();
				liger.get("end_month").set("parms", para1);
				liger.get("end_month").reload();
				liger.get("begin_subj_code").clear();
				liger.get("begin_subj_code").set("parms", para1);
				liger.get("begin_subj_code").reload();
				liger.get("end_subj_code").clear();
				liger.get("end_subj_code").set("parms", para1);
				liger.get("end_subj_code").reload();
		    }, 
		});
		autocompleteObj({
			id:  '#begin_month', 
			urlStr: 	"../../queryAccYearMonth.do?isCheck=false", 
			valueField:  'id', 
			textField:    'text' , 
			parmsStr: para, 
			autocomplete: true, 
			highLight: true, 
			defaultSelect:  false, 
			initvalue: "${acc_month}", 
			initWidth: 65, 
		});
		autocompleteObj({
			id:  '#end_month', 
			urlStr: 	"../../queryAccYearMonth.do?isCheck=false", 
			valueField:  'id', 
			textField:    'text' , 
			parmsStr: para, 
			autocomplete: true, 
			highLight: true, 
			defaultSelect:  false, 
			initvalue: "${acc_month}", 
			initWidth: 65, 
		});
		
		//科目
		autocompleteObj({
			id:  '#begin_subj_code', 
			urlStr: 	"../../querySubjCode.do?isCheck=false", 
			valueField:  'id', 
			textField:    'text' , 
			autocomplete: true, 
			highLight: true, 
			parmsStr: para, 
			defaultSelect:  false, 
			boxwidth: subjWidth, 
			selectEvent: function(value){

				liger.get("end_subj_code").clear();
				liger.get("end_subj_code").setValue(value);
				liger.get("end_subj_code").setText(liger.get("begin_subj_code").getText());
		    }, 
		    textBoxKeySpace: function(value){
		    	showSubjTree({
					ligerId: "begin_subj_code", 
					acc_year: acc_year_month1.getValue().split(".")[0], 
					windowName: window.name
				});
		    }
		});

		autocompleteObj({
			id:  '#end_subj_code', 
			urlStr: 	"../../querySubjCode.do?isCheck=false", 
			valueField:  'id', 
			textField:    'text', 
			autocomplete: true, 
			highLight: true, 
			parmsStr: para, 
			defaultSelect:  false, 
			boxwidth: subjWidth, 
		    textBoxKeySpace: function(value){
		    	showSubjTree({
					ligerId: "end_subj_code", 
					acc_year: acc_year_month1.getValue().split(".")[0], 
					windowName: window.name
				});
		    }
		});
		
		//科目级次
		autocompleteAsync("#subj_level","../../querySubjLevel.do?isCheck=false","id","text",false,true,'',true,'',70);
		var subjCom=$("#subj_level").ligerComboBox({ cancelable: false });
		//subjCom.addItem({id:100,text:'辅助核算'});
		subjCom.setValue(99,'末级');

		//统一按钮样式
		$("input[name='controllerButton']").ligerButton({width: 90});
	}


	function myPrintSet(){
		officeFormTemplate({template_code:"01003"});
	}
	
	function openPrint(){
		//必填条件验证
		if(!liger.get("acc_year").getValue()){
			$.ligerDialog.warn("会计年度不能为空！");
			return false;
		}
		if(!liger.get("begin_month").getValue() || !liger.get("end_month").getValue()){
			$.ligerDialog.warn("会计期间不能为空！");
			return false;
		}
		if(!liger.get("subj_level").getValue()){
			$.ligerDialog.warn("科目级次不能为空！");
			return false;
		}
		if(!liger.get("begin_subj_code").getValue() || !liger.get("end_subj_code").getValue()){
			$.ligerDialog.warn("会计科目不能为空！");
			return false;
		}
		
		var gridParms = {
			acc_year: liger.get("acc_year").getValue(),
			begin_month: liger.get("begin_month").getValue(), 
			end_month: liger.get("end_month").getValue(), 
			subj_level: liger.get("subj_level").getValue(), 
			begin_subj_code: liger.get("begin_subj_code").getValue(), 
			end_subj_code: liger.get("end_subj_code").getValue()
		}
		
		ajaxJsonObjectByUrl("queryAccBooksPrintSubj.do?isCheck=false", gridParms, function (responseData) {
			var param = {
				template_code: '01003',
				class_name: "com.chd.hrp.acc.service.books.AccBookPrintService",
				method_name: "collectAccBooksPrint",
				bean_name: "accBookPrintService",
				isSetPrint: false,//是否套打，默认非套打
				isPreview: false,//是否预览，默认直接打印
				main_query: true, //主表数据由java返回(为false时取para中的参数)
				acc_year: liger.get("acc_year").getValue(),
				begin_month: liger.get("begin_month").getValue(), 
				end_month: liger.get("end_month").getValue(), 
				//增加主表数据对应字段
				p_year_month_b: liger.get("begin_month").getValue(), 
				p_year_month_e: liger.get("end_month").getValue(), 
			}
			
			officeTablePrintBatch(param, responseData);
		});
	}
</script>
</head>
<body style="padding: 50px; overflow: hidden;"  onload="">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<b><font color="red">*</font></b>会计年度：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input type="text" name="acc_year" id="acc_year"/>
			</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 41px;">
				<b><font color="red">*</font></b>会计期间：
			</td>
			<td>
				<table>
					<tr>
						<td align="left">
							<input name="begin_month" type="text" id="begin_month"/>
						</td>
						<td align="left" class="l-table-edit-td" style="padding-left: 8px;">至</td>
						<td align="left" class="l-table-edit-td">
							<input name="end_month" type="text" id="end_month"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
        <tr>
			<td align="right" class="l-table-edit-td">
				<b><font color="red">*</font></b>科目级次：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="subj_level" type="text" id="subj_level" ltype="text" />
            </td>
        </tr>
		<tr>
        	<td align="right" class="l-table-edit-td">
        		<b><font color="red">*</font></b>科目范围：
        	</td>
        	<td align="left" class="l-table-edit-td" colspan="3" >
            	<table>
					<tr>
						<td align="left">
							<input name="begin_subj_code" type="text" id="begin_subj_code"/>
						</td>
						<td align="left" class="l-table-edit-td">至</td>
						<td align="left" class="l-table-edit-td">
							<input name="end_subj_code" type="text" id="end_subj_code"/>
						</td>
            		</tr>
				</table>
            </td>
        </tr>
		<tr>
			<td colspan="4" align="center" style="padding-top: 40px;">
				<input type="button" accessKey="G" name="controllerButton" value=" 模板设置（G）" onclick="myPrintSet();"/>
				&nbsp;&nbsp;&nbsp;
         		<input type="button" accessKey="P" name="controllerButton" value=" 账簿打印（P）" onclick="openPrint();"/>
			</td>
		</tr>
	</table>
</body>
</html>