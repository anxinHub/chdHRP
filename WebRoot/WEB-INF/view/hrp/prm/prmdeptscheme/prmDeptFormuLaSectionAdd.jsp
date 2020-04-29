<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
	<script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	var dataFormat;
	var formula_stack_value = [];
	var num = 0;
	 var data = [];
     
     data.push({ id: 12, pid: 0, text: '目标值' });
   data.push({ id: 22, pid: 0, text: '实际完成值' });
   data.push({ id: 32, pid: 0, text: '满分标准' });
   data.push({ id: 42, pid: 0, text: '权重值' });
  /*   data.push({ id: 5, pid: 2, text: '1.1.2' });      

   data.push({ id: 10, pid: 8, text: 'wefwfwfe' });
    data.push({ id: 11, pid: 8, text: 'wgegwgwg' });
   data.push({ id: 12, pid: 8, text: 'gwegwg' });

    data.push({ id: 6, pid: 2, text: '1.1.3',});
   data.push({ id: 7, pid: 2, text: '1.1.4' });
   data.push({ id: 8, pid: 7, text: '1.1.5' });
   data.push({ id: 9, pid: 7, text: '1.1.6' }); */
	$(function() {
	 ue = UE.getEditor('formula_method_chs',{autoHeightEnabled: false, autoFloatEnabled: false, toolbars: []});

		loadDict();

		loadForm();

		loadTree();
		

	});

	function save() {
		
		if(formula_method_chs == ''){
			$.ligerDialog.warn('计算公式不能为空 ');
			return ;
		}
		
		var formPara = {

		/* 	formula_code : $("#formula_code").val(), */

		/* 	formula_name : $("#formula_name").val(), */

			formula_method_chs : UE.getEditor('formula_method_chs').getContentTxt(),

			formula_stack : $("#formula_stack").val(),

			is_stop : $("#is_stop").val(),
			
			formula_stack_value :JSON.stringify(formula_stack_value),
			
			acc_year : $("#acc_year").val(),
			
			acc_month : $("#acc_month").val(),
			
			goal_code :"${goal_code}",
			
			dept_no : liger.get("dept_no").getValue().split(".")[1],
			
			dept_id : liger.get("dept_no").getValue().split(".")[0],
			
			kpi_code : "${kpi_code}"

		};
		ajaxJsonObjectByUrl("savePrmDeptFormula.do",  formPara,function(responseData) {
			if (responseData.state == "true") {
				//query();
				parent.query();
			}
				});
	}
	/* function saveSchemeSection(){
		var dataSection = gridManager.rows;
		var index = 0;
		var msg = "";
		if(dataSection.length > 0){
			$.each(dataSection, function(d_index, d_content){ 
				if(d_content.section != 1){
					if((d_content.kpi_end_value == "undefined" || d_content.kpi_end_value == "")
			      			&&
			      			(d_content.kpi_beg_value == "undefined" || d_content.kpi_beg_value == "")
			      			&&
			      			(d_content.kpi_beg_score == "undefined" || d_content.kpi_beg_score == "")
			      			&&
				      		(d_content.kpi_end_score == "undefined" || d_content.kpi_end_score == "")
			      		  ){
			      			grid.deleteRow(d_content);//删除选择的行
			         		return true; 
			      		  }
			      		index++;
				}
			})
			
			for(var i = 0 ; i < index; i ++){
			    if (i < index -1) {
			    	if(dataSection[i].kpi_end_value != dataSection[i+1].kpi_beg_value){
						msg = '期间必须连续！';
					}
			    } 
			}
		}
		if(msg != ""){
			$.ligerDialog.error(msg);
			return;
		}
		
		
		var formPara = {
				
				dataSection : JSON.stringify(dataSection),
				acc_year : $("#acc_year").val(),
				acc_month : $("#acc_month").val(),
				goal_code :"${goal_code}",
				dept_no : liger.get("dept_no").getValue().split(".")[1],
				dept_id : liger.get("dept_no").getValue().split(".")[0],
				kpi_code : "${kpi_code}",
		};
		
		ajaxJsonObjectByUrl("saveDeptSchemeSection.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				query();
				parent.query();
			}
		});

	} */
	function savePrmDeptFormula() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		//$("form").ligerForm();
	}

	function savePrmFormula() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {

		$("#layout1").ligerLayout({
			leftWidth : 200,
			topHeight : 30,
			
		});

		$("#formula_code").ligerTextBox({
			width : 130
		});

		$("#formula_name").ligerTextBox({
			width : 200
		});

		$("#is_stop").ligerComboBox({
			width : 80
		});
		 //字典下拉框

    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false);
        
		liger.get("hos_id").setValue("${hos_id}");
        
        liger.get("hos_id").setText("${hos_code} ${hos_name}");
    	
    	autocomplete("#dept_no","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false);
    	
		liger.get("dept_no").setValue("${dept_id}.${dept_no}");
        
        liger.get("dept_no").setText("${dept_code} ${dept_name}");
        
		$("#hos_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#dept_no").ligerTextBox({width : 160,disabled: true});
    	
    	$("#acc_year").ligerTextBox({width : 70,disabled: true});
    	
    	$("#acc_month").ligerTextBox({width : 70,disabled: true});
    	
    	$("#kpi_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#nature_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#goal_value").ligerTextBox({width : 70,disabled: true});
    	
    	$("#ratio").ligerTextBox({width : 70,disabled: true});
	}

	function onSelect(note) {
		
	/* 	if (note.data.pid != 0) { */
			num = num + 1;
			var name_chs = UE.getEditor('formula_method_chs');

			var name_eng = $("#formula_method_eng");

			var formula_stack = $("#formula_stack");
			
			ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + "{" +note.data.text + "}");
			
			name_eng.val(name_eng.val() + "{"+note.data.id+"}");
			
			formula_stack_value.push({target_code:note.data.id,stack_seq_no:num});
			
			if (formula_stack.val() != '') {
				formula_stack.val(formula_stack.val() + ";" + note.data.id);
			} else {
				formula_stack.val(note.data.id);
			}
		/* } */
	}

	function button0() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 0);

		name_eng.val(name_eng.val() + 0);

	}

	function buttonCE() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		var formula_stack = $("#formula_stack");

		var name_chs_val = UE.getEditor('formula_method_chs').getContentTxt();

		var name_eng_val = $("#formula_method_eng").val();

		var formula_stack_val = $("#formula_stack").val();

		var indexof_var = "0|1|2|3|4|5|6|7|8|9|+|-|*|/|(|)|%|.";

		if (name_chs_val.length > 0) {

			var last_chs_var = name_chs_val.charAt(name_chs_val.length - 1);

			if (indexof_var.indexOf(last_chs_var) > 0) {

				ue.setContent(name_chs_val.substring(0, name_chs_val.length - 1));

				name_eng
						.val(name_eng_val.substring(0, name_eng_val.length - 1));

			} else {//计算最后一个位置 如果不是计算字符

				var count = -1;

				var indexof_var_split = indexof_var.split("|");

				for (var i = 0; i < indexof_var_split.length; i++) {

					var count1 = name_chs_val.lastIndexOf(indexof_var_split[i]);
					//alert("name_chs_val="+name_chs_val+" count1="+count1+" split[i]="+indexof_var_split[i]);
					if (count1 > 0 && count1 > count) {

						count = count1;

					}

				}

				ue.setContent(name_chs_val.substring(0, count));

				name_eng.val(name_eng_val.substring(0, count));

				var formula_stack_lastIndexOf = formula_stack_val
						.lastIndexOf(";");

				if (formula_stack_lastIndexOf > 0) {

					formula_stack.val(formula_stack_val.substring(0,
							formula_stack_lastIndexOf));

				} else {

					formula_stack.val('');

				}

			}

		}

	}
	function buttonC() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		var formula_stack = $("#formula_stack");

		ue.setContent("");

		name_eng.val('');

		formula_stack.val('');

	}
	function buttonJia() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '+');

		name_eng.val(name_eng.val() + '+');

	}
	function buttonZKH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '(');

		name_eng.val(name_eng.val() + '(');

	}
	function button7() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 7);

		name_eng.val(name_eng.val() + 7);

	}
	function button8() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 8);

		name_eng.val(name_eng.val() + 8);

	}
	function button9() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 9);

		name_eng.val(name_eng.val() + 9);

	}
	function buttonCH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '/');

		name_eng.val(name_eng.val() + '/');

	}
	function buttonYKH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + ')');

		name_eng.val(name_eng.val() + ')');

	}
	function button4() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 4);

		name_eng.val(name_eng.val() + 4);

	}
	function button5() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 5);

		name_eng.val(name_eng.val() + 5);

	}
	function button6() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 6);

		name_eng.val(name_eng.val() + 6);

	}
	function buttonCheng() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '*');

		name_eng.val(name_eng.val() + '*');

	}
	function buttonBFH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '%');

		name_eng.val(name_eng.val() + '%');

	}
	function button1() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 1);

		name_eng.val(name_eng.val() + 1);

	}
	function button2() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 2);

		name_eng.val(name_eng.val() + 2);

	}
	function button3() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 3);

		name_eng.val(name_eng.val() + 3);

	}
	function buttonJH() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '-');

		name_eng.val(name_eng.val() + '-');

	}
	function buttonDian() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '.');

		name_eng.val(name_eng.val() + '.');

	}
	
	function buttonDDY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '==');

		name_eng.val(name_eng.val() + '==');

	}
	
	function buttonBDY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '!=');

		name_eng.val(name_eng.val() + '!=');

	}
	
	function buttonDY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '>');

		name_eng.val(name_eng.val() + '>');

	}
	
	function buttonXY() {

		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '<');

		name_eng.val(name_eng.val() + '<');

	}
	
function buttonDYDY(){
		
		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '>=');

		name_eng.val(name_eng.val() + '>=');
	}
	
	function buttonXYDY(){
		
		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '<=');

		name_eng.val(name_eng.val() + '<=');
	}
	
	function buttonYFH(){
		
		var name_chs = UE.getEditor('formula_method_chs');

		var name_eng = $("#formula_method_eng");

		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '&&');

		name_eng.val(name_eng.val() + '&&');
	}
	
	function buttonHFH(){
	
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '||');
	
		name_eng.val(name_eng.val() + '||');
	}
	
	function buttonIF(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 'if(<span style="color: red">条件</span>){<br/><span style="color: red">&nbsp;&nbsp;内容体</span><br/>}');
	
		name_eng.val(name_eng.val() + 'if(){}');
	}
	
	function buttonIF(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + 'if(<span style="color: red">条件</span>){<span style="color: red">&nbsp;&nbsp;内容体</span>}');
	
		name_eng.val(name_eng.val() + 'if(){}');
	}
	
	function buttonELSEIF(){
	
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + ' else if(<span style="color: red">条件</span>){<span style="color: red">&nbsp;&nbsp;内容体</span>}');
	
		name_eng.val(name_eng.val() + 'else if(){}');
	}
	
	function buttonELSE(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + ' else{<span style="color: red">&nbsp;&nbsp;内容体</span>}');
	
		name_eng.val(name_eng.val() + 'else {}');
	}
	
	function buttonSYF(){
		
		var name_chs = UE.getEditor('formula_method_chs');
	
		var name_eng = $("#formula_method_eng");
	
		ue.setContent(UE.getEditor('formula_method_chs').getContentTxt() + '条件 ？ "真值" : "假值" ;');
	
		name_eng.val(name_eng.val() + '条件 ？ "真值" : "假值"');
	}

	function loadTree() {
		 var tree = $("#tree1").ligerTree({  
	           data:data, 
	            idFieldName :'id',
	            slide : false,
	            checkbox : false,
	            onSelect : onSelect,
	            parentIDFieldName :'pid'
	          
	            });
	            treeManager = $("#tree1").ligerGetTreeManager();
		/* ajaxJsonObjectByUrl("../prmtarget/queryTargetTree.do?isCheck=false",
				{},

				function(responseData) {

					if (responseData != null) {

						tree = $("#tree1").ligerTree({

							data : data,

							checkbox : false,

							idFieldName : 'id',

							parentIDFieldName : 'pid',

							onSelect : onSelect,

							isExpand : 3,
							nodeWidth : 400

						});

						treeManager = $("#tree1").ligerGetTreeManager();

						treeManager.collapseAll();
					}

				}); */
	}
/* 	  function loadDict(){
	       
		}   */

</script>

</head>

<body style="padding: 10px; margin: 0;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_no" type="text" id="dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" disabled="disabled"  ltype="text"  value="${acc_year}"  validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" disabled="disabled"  value="${acc_month}"   ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text"  value="${kpi_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">指标性质：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text"  value="${nature_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">目标值：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_value" type="text" id="goal_value" value="${goal_value}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">满分：</td>
            <td align="left" class="l-table-edit-td"><input name="ratio" type="text" id="ratio"  value="${full_score}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<form name="form1" method="post" id="form1">
		<div id="layout1"
			style="width: 100%; margin: 40px; height: 400px; margin: 0; padding: 0;">
		<!-- 	<div position="top">
				<table cellpadding="0" cellspacing="0" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式编码：</td>
						<td align="left" class="l-table-edit-td">
							<input name="formula_code" type="text" id="formula_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
						<td align="left"></td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
						<td align="left" class="l-table-edit-td">
							<input name="formula_name" type="text" id="formula_name" ltype="text" validate="{required:true,maxlength:200}" /> 
							<input name="formula_method_eng" type="hidden" id="formula_method_eng" ltype="text" validate="{required:true,maxlength:2000}" /> 
							<input name="formula_stack" type="hidden" id="formula_stack" ltype="text" validate="{required:true,maxlength:2000}" /></td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
						<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
								<option value="0">否</option>
								<option value="1">是</option>
						</select></td>
						<td align="left"></td>
					</tr>
				</table>


			</div> -->
			<div position="left" title="指标" style="width: 200px; height: 100%; overflow: auto;">
				<ul id="tree1"></ul>
			</div>
			<div position="center" title="计算公式（中文）" >
				 <table cellpadding="0" cellspacing="0" class="l-table-edit" style="width:100%; bottom: 143px;">
					<tr>
						<td align="left" class="l-table-edit-td">
							<textarea class="liger-textarea" id="formula_method_chs" disabled name="formula_method_chs" style="width:100%;height:38%;" validate="{required:true}">${formula_method_chs}</textarea>
						 </td>
					<tr />
				</table>
				<div class="bottom" style="position: absolute;width: 100%;bottom: 0;">
	
					<table align="left">
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="0" onClick="button0();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="CE" onClick="buttonCE();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="C" onClick="buttonC();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="+" onClick="buttonJia();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="(" onClick="buttonZKH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="==" onClick="buttonDDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value=">=" onClick="buttonDYDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="if" onClick="buttonIF();"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="7" onClick="button7();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="8" onClick="button8();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="9" onClick="button9();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="/" onClick="buttonCH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value=")" onClick="buttonYKH();">
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="!=" onClick="buttonBDY();">
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="<=" onClick="buttonXYDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="else if" onClick="buttonELSEIF();"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="4" onClick="button4();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="5" onClick="button5();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="6" onClick="button6();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="*" onClick="buttonCheng();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="%" onClick="buttonBFH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value=">" onClick="buttonDY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="&&" onClick="buttonYFH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="else" onClick="buttonELSE();"></td>
						</tr>
						<tr>
							<td align="left" style="padding: 0px 0px 10px 10px;" class="l-table-edit-td"><input class="liger-button" type="button" value="1" onClick="button1();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="2" onClick="button2();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="3" onClick="button3();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="-" onClick="buttonJH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="." onClick="buttonDian();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="<" onClick="buttonXY();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="||" onClick="buttonHFH();"></td>
							<td align="left" style="padding: 0px 0px 10px 40px;" class="l-table-edit-td"><input class="liger-button" type="button" value="?:" onClick="buttonSYF();"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>

	</form>

</body>
</html>
