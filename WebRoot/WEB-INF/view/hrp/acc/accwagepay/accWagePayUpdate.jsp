<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<style type="text/css">
.inp{
	border-left-width:0px;
	border-top-width:0px;
	border-right-width:0px;
	border-bottom-color:black;
	margin-top: 10px;
	padding-top: 5px
}
</style>
<script type="text/javascript">
	var dataFormat;
	var wageCode = "${wage_code}";
	var accYear = "${acc_year}";
	var accMonth = "${acc_month}";
	var empId = "${emp_id}";
	var item_column = "";
	var sum_column = "";
	var wageItem;
	$(function() {
		initColumnItem();
		
		// keydown
		$("#table_id").keydown(function(e) {
            var event = e || window.event;
            var _index = $(".inp").index($(event.target));    //获取当前编辑的单元格index值
            var trLength = $(this).find('tr:first').find('td[align="right"]').length;      //获取表格中一行的单元格数以便键盘上下时控制
            if (event.keyCode == 13 || event.keyCode == 39) {   //当回车到最后一格时再回车即转到第一格

                if (_index == $(".inp").index($(".inp:enabled").eq($(".inp:enabled").length -1))) {   //当回车到最后一个编辑格时再回车回到最开始的编辑格
                    _index = -1;
                };

                for (var i = _index + 1; i < $(".inp").length; i++) {
                    if (!$(".inp").eq(i).attr('disabled')) {
                        $(".inp").eq(i).focus();
                        break;
                    }
                };
            } else if (event.keyCode == 37) { //左
                if (_index == $(".inp").index($(".inp:enabled").eq(0)))    //当回车到最开始的编辑格时再回车回到最后的编辑格
                    _index = $(".inp").length;

                for (var a = _index - 1; a >= 0; a--) {
                    if (!$(".inp").eq(a).attr('disabled')) {
                        $(".inp").eq(a).focus();
                        break;
                    }
                }
            } else if (event.keyCode == 38) { //上      根据index值控制，键盘向上时即index - “一行单元格数”

                for (var n = _index - trLength; n >= 0; n -= trLength) {
                    if (!$(".inp").eq(n).attr('disabled')) {
                        $(".inp").eq(n).focus();
                        break;
                    }
                }
            } else if (event.keyCode == 40) { //下     同理

                for (var n = _index + trLength; n < $(".inp").length; n += trLength) {
                    if (!$(".inp").eq(n).attr('disabled')) {
                        $(".inp").eq(n).focus();
                        break;
                    }
                }
            }
        });
	});
	
	function initColumnItem(){
		// 列出工资项
		var param = {
			wage_code : wageCode,
			acc_year : accYear,
			is_stop : 0
		};
		ajaxJsonObjectByUrl("queryTrendColumn.do?isCheck=false", param, function(responseData){
			$("#table_id").html("");
			var tHtml = "";
			wageItem = responseData.Rows;
			$.each(responseData.Rows, function(index, value){
				sum_column += "," + value.column_item;
				if(value.item_cal == 1){
					item_column += "," + value.column_item
				}
				if(index%7 == 0){
					tHtml += "<tr>";
				}else if(index%7 == 0 && index != 0){
					tHtml += "</tr>";
				}
				
				if(value.item_cal == 1){
					tHtml += "<td align='right' class='l-table-edit-td' style='padding-left:25px;padding-top:10px;'>"+value.item_name+"：</td>"
						   + "<td align='left' class='l-table-edit-td'>"
						   +     "<input class='inp' name='"+value.column_item+"' type='text' id='"+value.column_item+"' ltype='text' validate='{required:true,maxlength:20}' style='width: 80px'/>"
						   + "</td>"
						   + "<td align='left'></td>";
				}else{
					tHtml += "<td align='right' class='l-table-edit-td' style='padding-left:25px;padding-top:10px;'>"+value.item_name+"：</td>"
						   + "<td align='left' class='l-table-edit-td'>"
						   +     "<input class='inp' name='"+value.column_item+"' type='text' id='"+value.column_item+"' ltype='text' validate='{required:true,maxlength:20}' disabled='disabled' style='width: 80px'/>"
						   + "</td>"
						   + "<td align='left'></td>";
				}
			});
			$("#table_id").append(tHtml);
			
			// 列出金额
			showWage();
			
			detail();
		});
	}
	
	function showWage(){
		var formPara={
			wage_code: wageCode,
			acc_year: accYear + "." + accMonth,
			emp_id : empId,
			column_item : sum_column
		};
		ajaxJsonObjectByUrl("queryAccEmpDetail.do?isCheck=false",formPara,function(responseData){
			data = responseData.Rows[0];
			var item=sum_column.substring(1,sum_column.length).split(",");
			if(data != null ){
				$.each(item,function(i,v){
					$("#"+v).val(data[v]);
				});
			}else{
				$.each(item,function(i,v){
					$("#"+v).val("0");
				});
			}
		});
	}
	
	function detail(){
		var formPara={
			emp_id : empId
		};
		ajaxJsonObjectByUrl("queryEmpByWageCode.do?isCheck=false", formPara, function(responseData){
			data = responseData;
			$.each(responseData.Rows,function(i,v){
				$("#kind_name").val(v.kind_name);
				if(v.sex==0){
					$("#sex").val("男");
				}else{
					$("#sex").val("女");
				}
				$("#pay_type_name").val(v.pay_type_name);
				$("#pay_code").val(v.pay_type_code);
				$("#station_name").val(v.station_name);
				$("#duty_name").val(v.duty_name);
				$("#id_number").val(v.id_number);
				$("#emp_id").val(v.emp_id);
				$("#emp_no").val(v.emp_no);
				$("#dept_id").val(v.dept_id);
				$("#dept_no").val(v.dept_no);
				$("#kind_code").val(v.kind_code);
				$("#station_code").val(v.station_code);
				$("#duty_code").val(v.duty_code);
			});
		});
	}
	
	//清空工资项文本框中的内容
    function clear_input(){
    	$('input').filter('.inp').val("");
    }
	
	// 保存
	function save(){
		var item="";
		var wage_item = sum_column.substring(1,sum_column.length).split(",");
		var exp = /^([-1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		var flag = false;
		$(wage_item).each(function(index,value){
			var v = $("#"+wage_item[index]).val();
			if(v == ""){
				item+="0;";
			}else if(exp.test(v)){
				item+=v+";";
			}else{
				var text = $("#" + value).parent().prev().text().replace("：", "");
				$.ligerDialog.warn("工资项【" + text + "】数字不合法.");
				flag = true;
				return;
			}
		});
		if(flag){
			return;
		}

		var formPara={
			emp_id : $("#emp_id").val(),
			emp_no : $("#emp_no").val(),
			acc_year : accYear,
			acc_month : accMonth,
			wage_code : wageCode,
			dept_id : $("#dept_id").val(),
			dept_no : $("#dept_no").val(),
			kind_code : $("#kind_code").val(),
			kind_name : $("#kind_name").val(),
			pay_code : $("#pay_code").val(),
			pay_name : $("#pay_type_name").val(),
			station_code : $("#station_code").val(),
			station_name : $("#station_name").val(),
			duty_code : $("#duty_code").val(),
			duty_name : $("#duty_name").val(),
			sex : $("#sex").val(),
			id_number : $("#id_number").val(),
			note : $("#note").val(),
			item : item,
			item_column : sum_column
		};
		ajaxJsonObjectByUrl("addAccWagePay.do", formPara, function(responseData){
			if(responseData.state=="true"){
				$.ligerDialog.success('更新成功.')
				parentFrameUse().query();
			}
		});
	}

	// 继承 
	function wage_item_extend(){
		var formPara={
			wage_code : wageCode,
			acc_year : accYear,
			acc_month : accMonth,
			emp_id : $("#emp_id").val(),
			emp_no : $("#emp_no").val(),
			column_item : sum_column
		}
		ajaxJsonObjectByUrl("extendAccWagePay.do?isCheck=false",formPara,function(responseData){
			data = responseData.Rows[0];
			var item=sum_column.substring(1,sum_column.length).split(",");
			$.each(item,function(i,v){
				$("#"+v).val(data[v]);
			});
		});
	}
	
	// 计算
	function btn_collect(){
		var emp_id= $("#emp_id").val();
		var formPara={
			wage_code : wageCode,
			acc_year : accYear,
			acc_month : accMonth,
			emp_id : $("#emp_id").val()
		};
		$.ligerDialog.confirm('是否计算此员工本月的工资数据?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("collectAccWagePayByPerson.do",formPara,function (responseData){
					if(responseData.is_ok=="0"){
						$.ligerDialog.success(responseData.msg_text);
						parentFrameUse().query();
					}else if(responseData.is_ok=="-1"){
						$.ligerDialog.error(responseData.msg_text);
						return;
					}else if(responseData.is_ok=="100"){
						$.ligerDialog.warn(responseData.msg_text);
						return;
					}
				});
			}
		});
	}

	// 关闭
	function btn_colse(){
		frameElement.dialog.close();
	}
</script>
</head>
<body>
	<div id="layout1">
		<div position="top">
			<input name="emp_id" type="hidden" id="emp_id" />
			<input name="emp_no" type="hidden" id="emp_no" />
			<input name="dept_id" type="hidden" id="dept_id" />
			<input name="dept_no" type="hidden" id="dept_no" />
			<input name="kind_code" type="hidden" id="kind_code" />
			<input name="kind_name" type="hidden" id="kind_name" />
			<input name="station_code" type="hidden" id="station_code" />
			<input name="station_name" type="hidden" id="station_name" />
			<input name="pay_code" type="hidden" id="pay_code" />
			<input name="pay_type_name" type="hidden" id="pay_type_name" />
			<input name="duty_code" type="hidden" id="duty_code" />
			<input name="duty_name" type="hidden" id="duty_name" />
			<input name="sex" type="hidden" id="sex" />
			<input name="id_number" type="hidden" id="id_number" />
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">部门名称：</td>
					<td align="left" class="l-table-edit-td">
						<input name="dept_name" type="text" id="dept_name" ltype="text" value="${dept_name}" disabled="disabled"/>
					</td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">职工名称：</td>
					<td align="left" class="l-table-edit-td">
						<input name="emp_name" type="text" id="emp_name" ltype="text" value="${emp_name}" disabled="disabled"/>
					</td>
					<td align="left"></td>
				</tr>
			</table>
		</div>
	
		<div position="center">
			<div style="width: 100%;">
				<div style="float: left; margin-top: 10px">
					<input class="l-button" type="button" style="width: 80px; margin-left: 20px" 
						   id="query" value="清空" onclick="clear_input();" />
				</div>
				<div style="float: left; margin-top: 10px">
					<input class="l-button" type="button" style="width: 80px; margin-left: 20px" 
						   id="reset" value="重置" onclick="showWage();" />
				</div>
				<div style="float: left; margin-top: 10px">
					<input class="l-button" type="button" style="width: 80px; margin-left: 20px" 
						   id="save" value="保存(S)" onclick="save();" />
				</div>
				<div style="float: left; margin-top: 10px">
					<input class="l-button" type="button" style="width: 80px; margin-left: 20px" 
						   id="extend" value="继承" onclick="wage_item_extend();" />
				</div>
				<div style="float: left; margin-top: 10px">
					<input class="l-button" type="button" style="width: 80px; margin-left: 20px" 
						   id="collect" value="计算" onclick="btn_collect();" />
				</div>
				<div style="float: left; margin-top: 10px">
					<input class="l-button" type="button" style="width: 80px; margin-left: 20px" 
						   id="colse" value="关闭" onclick="btn_colse();" />
				</div>
			</div>
			<div style="width: 100%; float: left; margin-top: 10px"><hr/></div>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" id="table_id"></table>
		</div>
	</div>
</body>
</html>
