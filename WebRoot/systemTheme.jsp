<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/static_resource.jsp">
		 <jsp:param value="select,datepicker" name="plugins"/>
	</jsp:include>
	<style>
		div.work-space {
			width: 100%;
			height: 100%;
			padding-top: 50px;
		}

		div.work-space table {
			font-size: 12px;
		}

		div.work-space table tr {
			height: 30px;
		}

		div.work-space table .label {
			width: 20%;
			text-align: right;
			padding-right: 5px;
		}

		div.work-space table .ipt {
			text-align: left;
			padding-left: 5px;
			font-size: 12px;
		}

		.date_input_txt {
			border: 1px solid #aecaf0;
			width: 180px;
			height: 26px;
			outline: none;
			box-sizing: border-box;
			padding-left: 5px;
			-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.1);
			box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.1);
			border-radius: 1px;
			cursor: pointer;
		}
	</style>
	<script>
		var hos_select, copy_select, mod_select, acct_year_text,cur_year;
		var sysDuration; //系统期间
		 var user_type_code='${type_code}';
		
		$(function () {
			var myDate = new Date();
			cur_year=myDate.getFullYear().toString();
			//构造系统期间
			initSysDuration();
			//加载页面空间
			init();
		});

		//加载数据
		function init() {
			//================== 医院 ==================
			hos_select = $("#hos_code_select").etSelect({
				url: "hrp/sys/queryHosInfoDictPerm.do?isCheck=false",
				para: { hos_id: user_type_code==1?"":'${hos_id}' },
				defaultValue: '${hos_id}',
				showClear: false,
				onInit: function (value) { 
					reloadCopySelect(value=='none'?'0':value)
				},
				onChange: function (value) {
					reloadCopySelect(value)
				}
			});
			//================== 医院 ==================

			//================== 账套 ==================
			copy_select = $("#copy_code_select").etSelect({
				//defaultValue: "${copy_code}",
				showClear: false,
				onChange: function (value) {
					reloadModSelect(value);
				}
			});
			//联动加载账套
			function reloadCopySelect(value) {
				copy_select.reload({
					url: "hrp/sys/queryCopyCodeDictPerm.do?isCheck=false",
					para: { hos_id: value }
				});
			}
			//================== 账套 ==================

			//================== 系统 ==================
			mod_select = $("#mod_code_select").etSelect({
				defaultValue: "${mod_code}",
				showClear: false,
				onChange: function (value) {
					//联动系统年度期间
					var time = sysDuration[value] ? sysDuration[value] : cur_year;
					//设置年度
					acct_year_text.setValue(time);
					
				}
			});
			//联动加载系统
			function reloadModSelect(value) {
				mod_select.reload({
					url: "hrp/sys/queryModDictPerm.do?isCheck=false",
					type: "POST",
					para: {
						hos_id: hos_select.val()?hos_select.val():'0',
						copy_code: value
					},
				});
				
			}
			//================== 系统 ==================

			//================== 日期 ==================
			acct_year_text = $("#acc_year_text").etDatepicker({
				defaultDate: sysDuration['${mod_code}'] ? sysDuration['${mod_code}'] : cur_year,
				width: 210,
				view: "years",
				minView: "years",
				dateFormat: "yyyy"
			});
			//================== 日期 ==================
		}

		//确定按钮
		function save() {
			var hos_id=hos_select.val();
			var copy_code = copy_select.val();
			var copy_name = copy_select.text();
			var acct_year = acct_year_text.getValue();
			var mod_code = mod_select.val();
		
			var formPara = {
				hos_id:hos_id,	
				copy_code: copy_code,
				copy_name: copy_name,
				acct_year: acct_year,
				mod_code: mod_code
			};

			$.ajax({
				url: "systemThemeJump.do?isCheck=false",
				data: formPara,
				type: "POST",
				dataType: "JSON",
				success: function (res) {
					if (res.state == "true") {
						ChangeSystem(mod_code, copy_code, '${type_code}', res.user_name, res.group_name, res.hospital, res.copy_name, acct_year);
					} else {
						$.ligerDialog.error("License没有授权系统权限.");
						$("#loginMsg").text("");
					}
				}
			});

		}

		//切换系统
		function ChangeSystem(mod_code, copy_code, user_type, user_name, group_name, hospital, copy_name, acc_year,hos_id) {
			parent.window.onbeforeunload = null; //清除事件
			var skin = $.cookie("drp_htc_skin");
			if (skin != null && skin != "") {
				skin = "&skin=" + skin;
			} else {
				skin = "";
			}
			top.window.location.href = "main.html";
		}

		//生成系统期间
		function initSysDuration() {
			sysDuration = {
				"01": getYear('${myAccYearMonth.curYearMonthAcc}'),
				"03": getYear('${myAccYearMonth.curYearMonthCost}'),
				"04": getYear('${myAccYearMonth.curYearMonthMat}'),
				"05": getYear('${myAccYearMonth.curYearMonthAss}'),
				"08": getYear('${myAccYearMonth.curYearMonthMed}')
			}
			
			function getYear(value) {
				/* var nowYear = new Date().getFullYear().toString();
				var result = parseInt(value) ? value.substring(0, 4) : nowYear; */
				return value.substring(0, 4);
			}
		}
	</script>
</head>

<body>
	
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="work-space">
		<table cellpadding="0" border="0" cellspacing="0" align="center">
			<tr>
				<td class="label">医院：</td>
				<td class="ipt">
					<select id="hos_code_select" style="width:210px"></select>
				</td>
			</tr>
			<tr>
				<td class="label">账套：</td>
				<td class="ipt">
					<select id="copy_code_select" style="width:210px"></select>
				</td>
			</tr>
			<tr>
				<td class="label">系统：</td>
				<td class="ipt">
					<select id="mod_code_select" style="width:210px"></select>
				</td>
			</tr>
			<tr>
				<td class="label">年度：</td>
				<td class="ipt">
					<input type="text" id="acc_year_text" class="date_input_txt" readonly style="width:210px">
				</td>
			</tr>
		</table>
	</div>

	
</body>

</html>