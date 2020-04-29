<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
%>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/Lodop/barcode.js" type="text/javascript"></script>   
<script src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<style>
.togglebtn {
    
    top: 6px;
    right: 15px;
    background: url(<%=path%>/lib/ligerUI/skins/icons/toggle.gif) no-repeat 0px 0px;
    height: 10px;
    width: 9px; 
    cursor: pointer;
    left: auto;
    top: 12px;
    right: 6px;
}
</style>
<script type="text/javascript">
	var dataFormat;
	var navtab = null;
	var rowData;
	var tab_ids = [];
	var is_init = 0;
	//$.ajaxSetup({
		//async : false
	//});
	var card_data = '${card_data}';
	$(function() {
		initTable();
		initTab();
	});

	function initTab() {
		$("#navtab1").ligerTab({
			changeHeightOnResize : true,
			onAfterSelectTabItem:selectTab,
			is_select:false
		});
		navtab = $("#navtab1").ligerGetTabManager();
		$.post("getAssCardTab.do?isCheck=false", {
			"ass_nature" : "${ass_nature}"
		}, function(resData) {
			tabData = resData;
			var tabid = "";
			$.each(resData.Rows, function(i, v) {
				if (i == 0) {
					tabid = v.tab_id;
				}
				navtab.addTabItem({
					tabid : v.tab_id,
					text : v.tab_name,
					showClose : false
				});
			});
			
			navtab.selectTabItem(tabid);
		}, "json");
	}
	function selectTab(tabid){
		var flag = true;
		for(var i = 0; i < tab_ids.length; i++){
			if(tabid == tab_ids[i]){
				flag = false;
			}
		}
		if(!flag){
			return;
		}
		$.each(tabData.Rows,function(i,v){
			if(v.tab_id == tabid){
				navtab.setTabItemSrc(tabid,v.tab_url+"&ass_card_no=${ass_card_no}");
			}
		});
		tab_ids.push(tabid);
	}
	
	function initTable() {
		$
				.post(
						"getAssCardTitle.do?isCheck=false",
						{
							"ass_nature" : "${ass_nature}"
						},
						function(data) {
							rowData = data;
							var max_field_area = 0;

							var tableStr = $("#divTables");

							for (var i = 0; i < data.Rows.length; i++) {
								if (data.Rows[i].field_area > max_field_area) {
									max_field_area = data.Rows[i].field_area;
								}
							}
							for (var j = 1; j <= max_field_area; j++) {
								var field_width = 0;
								var colspan = "";
								var width = "";
								var html = "";
								var $tr = $("<tr>");
								var $td = $("<td>");
								var $table = $("<table class='l-table-edit' cellpadding='0' cellspacing='0'  width='100%' border='0' >");
								$tr.append($td);
								$td.append($table);
								tableStr.append($tr);
								for (var k = 0; k < data.Rows.length; k++) {
									if (data.Rows[k].field_area == j) {
										if (data.Rows[k].is_insert_view == 1) {
											field_width = field_width
													+ data.Rows[k].field_width;
											if (data.Rows[k].field_width == 1) {
												colspan = "0";
												width = "14%";
											} else if (data.Rows[k].field_width == 2) {
												colspan = "3";
												width = "14%";
											} else if (data.Rows[k].field_width == 3) {
												colspan = "5";
												width = "14%";
											} else if (data.Rows[k].field_width == 4) {
												colspan = "7";
												width = "14%";
											}
											if (field_width == data.Rows[k].field_width) {
												html += "<tr>";
											}
											if (field_width > 4) {
												field_width = data.Rows[k].field_width;
												html += "</tr><tr>";
											}
											html += "<td align='right' width='10%' class='l-table-edit-td'  style='padding-left:20px;'>"
													+ data.Rows[k].col_name
													+ "：</td>";
											if (data.Rows[k].type_code == 2) {
												html += "<td align='left' width='"+width+"' class='l-table-edit-td' colspan='"+colspan +"'><input name='"
														+ data.Rows[k].col_code
														+ "' type='text' id='"
														+ data.Rows[k].col_code
														+ "' class='Wdate' onFocus='dateText()' /></td>";
											} else {
												html += "<td align='left' width='"+width+"' class='l-table-edit-td' colspan='"+colspan +"'><input name='"+data.Rows[k].col_code+"' type='text' id='"+data.Rows[k].col_code+"'  /></td>";
											}

											if (field_width == 4
													|| k + 1 == data.Rows.length) {
												if (field_width < 4
														&& k + 1 == data.Rows.length) {
													if (field_width == 3) {
														html += "<td align='right' width='10%' class='l-table-edit-td' style='padding-left:20px;'></td><td align='left'  colspan='"
																+ colspan
																+ "' width='14%' class='l-table-edit-td'></td>";
													}
													if (field_width == 2) {
														html += "<td><td/><td><td/><td><td/><td><td/>";
													}
													if (field_width == 1) {
														html += "<td><td/><td><td/><td><td/><td><td/><td><td/><td><td/>";
													}
												}
												field_width = 0;
												html += "</tr>";
											}

										}
									}
								}
								if (j != max_field_area) {
									$tr.append($td);
									$td.append("<hr/>");
								}
								$table.append(html);
							}
							loadDict();
						}, "json");
	}

	function save() {
		var formPara = {
			'ass_nature' : '${ass_nature}',
			  'is_update':'true',
			  'in_type':'${in_type}'
		};
		var flag = true;
		var msg = "";
		$.each(rowData.Rows,
				function(i, v) {

					if (v.is_verify == 1) {
						if ($("#" + v.col_code + "").val() == "") {
							msg = msg + v.col_name + "[不能为空]</br>";
							flag = false;
						}

					}
					if (v.is_insert_view == 1) {
						if (v.type_code == 0) {//文本
							formPara[v.col_code.toLowerCase()] = $(
									"#" + v.col_code + "").val();
						} else if (v.type_code == 1) {//下拉
							if (v.is_change == 1) {
								if(v.col_code.toLowerCase().split("_")[0] == "proc"){//采购仓库单独处理
									formPara[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1] + "_id"] = liger.get(v.col_code).getValue().split("@")[0];
									
									formPara[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1] + "_no"] = liger.get(v.col_code).getValue().split("@")[1];
									
								}else{
									formPara[v.col_code.toLowerCase().split("_")[0]
									+ "_id"] = liger.get(v.col_code)
									.getValue().split("@")[0];
									formPara[v.col_code.toLowerCase().split("_")[0]
									+ "_no"] = liger.get(v.col_code)
									.getValue().split("@")[1];
								}
							} else {
								formPara[v.col_code.toLowerCase()] = liger.get(
										v.col_code).getValue();
							}
						} else if (v.type_code == 2) {//日期
							formPara[v.col_code.toLowerCase()] = $(
									"#" + v.col_code + "").val();
						} else if (v.type_code == 3) {//数值
							formPara[v.col_code.toLowerCase()] = $(
									"#" + v.col_code + "").val();
						}
					} else {
						if(v.default_value == "TODAY"){
							formPara[v.col_code.toLowerCase()] = cardautodate();
						}else{
							if(v.default_value == "ASS_CARD_NO"){
								formPara[v.col_code.toLowerCase()] = '${ass_card_no}';
							}else{
								formPara[v.col_code.toLowerCase()] = v.default_value;
							}
						}
					}
				});

		if (!flag) {
			$.ligerDialog.warn(msg);
			return;
		}
		
		if(formPara.acc_depre_amount.length >= 5){
			$.ligerDialog.warn("折旧年限输入不合法！");
			return;
		}
		
		if(formPara.manage_depre_amount.length >= 5){
			$.ligerDialog.warn("分摊年限输入不合法！");
			return;
		}
		
		if(parseFloat(formPara.cur_money) > parseFloat(formPara.price)){
			$.ligerDialog.warn("净值不能大于原值！");
			return;
		}
		
		//if((parseFloat(formPara.price) - parseFloat(formPara.depre_money) - parseFloat(formPara.fore_money)).toFixed(3) != parseFloat(formPara.cur_money)){
			//$.ligerDialog.warn("净值输入不合法！");
			//return;
		//}
		formPara.is_init = '${is_init}';
		ajaxJsonObjectByUrl("updateAssCard.do?isCheck=false", formPara,
				function(responseData) {

					if (responseData.state == "true") {
						parentFrameUse().query();
					}
		});
	}
	
	
	function cardautodate(dateFmt, flag) {
		var d;
		if (dateFmt == undefined || dateFmt == "") {
			dateFmt = "yyyy-mm-dd";
		}
		if (flag == undefined || flag == "") {
			d = new Date();
		} else {
			if (flag == "new") {
				d = new Date();
			} else {
				var mydate = new Date();
				var vYear = mydate.getFullYear();
				var vMon = mydate.getMonth() + 1;
				var vDay = mydate.getDate();
				var this_date = getMonthDate(vYear, vMon);
				//每个月的最后一天日期（为了使用月份便于查找，数组第一位设为0）
				var daysInMonth = new Array(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
				//若是闰年，二月最后一天是29号
				if (vYear % 4 == 0 && vYear % 100 != 0) {
					daysInMonth[2] = 29;
				}

				if (flag == "month_first") {
					d = new Date(this_date.split(";")[0].replace(/-/g, '/'));
				} else if (flag == "month_last") {
					d = new Date(this_date.split(";")[1].replace(/-/g, '/'));
				} else if (flag == "year_first") {
					d = new Date((vYear + '-01' + '-01').replace(/-/g, '/'));
				} else if (flag == "year_last") {
					d = new Date((vYear + '-12' + '-31').replace(/-/g, '/'));
				} else if (flag == "before_month") {
					if (vMon == 1) {
						vYear = mydate.getFullYear() - 1;
						vMon = 12;
					} else {
						vMon = vMon - 1;
					}
					if (daysInMonth[vMon] < vDay) {
						vDay = daysInMonth[vMon];
					}
					if (vDay < 10) {
						vDay = "0" + vDay;
					}
					if (vMon < 10) {
						vMon = "0" + vMon;
					}
					d = new Date((vYear + "-" + vMon + "-" + vDay).replace(/-/g, '/'));
				} else if (flag == "next_month") {
					if (vMon == 12) {
						vYear = mydate.getFullYear() + 1;
						vMon = 1;
					} else {
						vMon = vMon + 1;
					}
					if (daysInMonth[vMon] < vDay) {
						vDay = daysInMonth[vMon];
					}
					if (vDay < 10) {
						vDay = "0" + vDay;
					}
					if (vMon < 10) {
						vMon = "0" + vMon;
					}
					d = new Date((vYear + "-" + vMon + "-" + vDay).replace(/-/g, '/'));
				} else {
					d = new Date();
				}
			}
		}

		if (dateFmt.toLowerCase() == 'yyyy') {
			return d.getFullYear().toString();
		}
		if (dateFmt.toLowerCase() == 'mm') {
			return addzero(d.getMonth() + 1);
		}
		if (dateFmt.toLowerCase() == 'dd') {
			return addzero(d.getDate());
		}
		if (dateFmt.toLowerCase() == 'yyyy-mm') {
			return d.getFullYear().toString() + "-" + addzero(d.getMonth() + 1);
		}
		if (dateFmt.toLowerCase() == 'yyyy mm') {
			return d.getFullYear().toString() + " " + addzero(d.getMonth() + 1);
		}
		if (dateFmt.toLowerCase() == 'yyyymm') {
			return d.getFullYear().toString() + addzero(d.getMonth() + 1);
		}
		if (dateFmt.toLowerCase() == 'yyyy/mm') {
			return d.getFullYear().toString() + "/" + addzero(d.getMonth() + 1);
		}
		if (dateFmt.toLowerCase() == 'yyyy-mm-dd') {
			return d.getFullYear().toString() + "-" + addzero(d.getMonth() + 1) + "-" + addzero(d.getDate());
		}
		if (dateFmt.toLowerCase() == 'yyyy mm dd') {
			return d.getFullYear().toString() + " " + addzero(d.getMonth() + 1) + " " + addzero(d.getDate());
		}
		if (dateFmt.toLowerCase() == 'yyyy/mm/dd') {
			return d.getFullYear().toString() + "/" + addzero(d.getMonth() + 1) + "/" + addzero(d.getDate());
		}
		if (dateFmt.toLowerCase() == 'yyyymmdd') {
			return d.getFullYear().toString() + addzero(d.getMonth() + 1) + addzero(d.getDate());
		}
		if (dateFmt.toLowerCase() == 'yyyy-mm-dd hh:mm:ss') {
			return d.getFullYear().toString() + "-" + addzero(d.getMonth() + 1) + "-" + addzero(d.getDate()) + " " + addzero(d.getHours()) + ":" + addzero(d.getMinutes()) + ":" + addzero(d.getSeconds());
		}

		function addzero(v) {
			if (v < 10) return '0' + v;
			return v.toString();
		}
	} 

	function loadDict() {
		card_data = card_data.replace(/\""/g,'"');
		card_data = card_data.replace('\\',' ');
		card_data = card_data.replace('\n',' ');
		var obj = eval('(' + card_data + ')'); 
		var one_td = $("table:eq(1) tr:first td:eq(0)").width();
		var two_td = $("table:eq(1) tr:first td:eq(1)").width();
		$.each(rowData.Rows,
				function(i, v) {
					var str = v.col_code;
					var field_width = v.field_width;
					if (v.is_insert_view == 1) {
						var width = 0;
						if (field_width == 1) {
							width = two_td;
						} else if (field_width == 2) {
							//16的计算规则：跨两个字段款，一个标签宽:每个对象左右上下均存在4px,首末各4px，中间双8px 16 = 4px + 4px*2*1 + 4px
							//20的计算规则：每个标签距离左边20px,由于只有一个标签因此为20px
							width = one_td + (two_td * 2) + 16 + 20;
						} else if (field_width == 3) {
							//30的计算规则：跨三个字段款，两个标签宽:每个对象左右上下均存在4px,首末各4px，中间双8px 30 = 4px + 4px*2*3 + 4px
							//40的计算规则：每个标签距离左边20px,由于只有两个标签因此：40 = 20px *2
							width = (one_td * 2) + (two_td * 3) + 30 + 40;
						} else if (field_width == 4){
							//42的计算规则：跨四个字段款，三个标签宽:每个对象左右上下均存在4px,首末各4px，中间双8px 48 = 4px + 4px*2*5 + 4px
							//40的计算规则：每个标签距离左边20px,由于只有三个标签因此：60 = 20px *3
							width = (one_td * 3) + (two_td * 4) + 48 + 60;
						}
						
						if (v.type_code == 0) {
							if(v.is_pk == 1){
								$("#" + str + "").ligerTextBox({
									width : width,
									disabled : true,
									cancelable : false
								});
							}else{
								$("#" + str + "").ligerTextBox({
									width : width
								});
							}
							$("#" + str + "")
									.val(obj[v.col_code.toLowerCase()]);
						} else if (v.type_code == 1) {
							
							autocompleteAsync("#" + str + "", v.re_url, "id",
									"text", true, true, "", false, null,
									width,true);
							if(v.is_pk == 1){
								$("#" + str + "").ligerTextBox({
									width : width,
									disabled : true,
									cancelable : false
								});
							}else{
								$("#" + str + "").ligerTextBox({
									width : width
								});
							}
							if (v.is_change == 1) {
								if(v.col_code.toLowerCase().split("_")[0] == "proc"){
									if (obj[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1] + "_id"] != null
											&& obj[v.col_code.toLowerCase().split("_")[0]+ "_" +v.col_code.toLowerCase().split("_")[1] + "_id"]  != ""
													){
										
										liger.get("" + str + "").setValue(
												obj[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1]+ "_id"]+ "@"+ obj[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1]+ "_no"]);
										
										var code = obj[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1]+ "_id_code"] == null ? "":obj[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1]+ "_id_code"];
										var name = obj[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1]+ "_id_name"] == null ? "":obj[v.col_code.toLowerCase().split("_")[0]+"_"+v.col_code.toLowerCase().split("_")[1]+ "_id_name"];
										liger.get("" + str + "").setText(code+" "+name);
										
									}
								}else{
									if (obj[v.col_code.toLowerCase().split("_")[0]
									+ "_id"] != null
									&& obj[v.col_code.toLowerCase().split(
											"_")[0]
											+ "_id"] != "") {
										
										
										liger.get("" + str + "").setValue(
												obj[v.col_code.toLowerCase().split("_")[0]+ "_id"]+ "@"+ obj[v.col_code.toLowerCase().split("_")[0]+ "_no"]);
										var code = obj[v.col_code.toLowerCase().split("_")[0]+ "_id_code"] == null ? "":obj[v.col_code.toLowerCase().split("_")[0]+ "_id_code"];
										var name = obj[v.col_code.toLowerCase().split("_")[0]+ "_id_name"] == null ? "":obj[v.col_code.toLowerCase().split("_")[0]+ "_id_name"];
										liger.get("" + str + "").setText(code+" "+name);		
										
									}
								}
							} else {
								liger.get("" + str + "").setValue(obj[v.col_code.toLowerCase()] == null?"":obj[v.col_code.toLowerCase()]);
										
								liger.get("" + str + "").setText(obj[v.col_code.toLowerCase()+ "_name"] == null?"":obj[v.col_code.toLowerCase()+ "_name"]);
							}

						} else if (v.type_code == 2) {
							if(v.is_pk == 1){
								$("#" + str + "").ligerTextBox({
									width : width,
									disabled : true,
									cancelable : false
								});
							}else{
								$("#" + str + "").ligerTextBox({
									width : width
								});
							}
							$("#" + str + "")
									.val(obj[v.col_code.toLowerCase()]);
						} else if (v.type_code == 3) {
							if(v.is_pk == 1){
								$("#" + str + "").ligerTextBox({
									width : width,
									disabled : true,
									cancelable : false
								});
							}else{
								$("#" + str + "").ligerTextBox({
									width : width
								});
							}
							$("#" + str + "")
									.val(obj[v.col_code.toLowerCase()]);
						}
						
						if(v.is_insert_read == 1){
							$("#" + str + "").ligerTextBox({
								width : width,
								disabled : true,
								cancelable : false
							});
						}
					}
				});
		
			$("#ASS_ID").change(function(){
			
			$.post("queryAssDictByID.do?isCheck=false",{ass_id:liger.get("ASS_ID").getValue().split("@")[0],ass_no:liger.get("ASS_ID").getValue().split("@")[1]},function(resdata){
				if('${ass_nature}' == '01'){
					liger.get("UNIT_CODE").setValue(resdata.ass_unit);
					liger.get("UNIT_CODE").setText(resdata.ass_unit_name);
					liger.get("VEN_ID").setValue(resdata.ven_id+"@"+resdata.ven_no);
					liger.get("VEN_ID").setText(resdata.ven_code);
					if(resdata.is_depr == 1){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("是");
					}else if(resdata.is_depr == 0){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("否");
					}
					liger.get("DEPR_METHOD").setValue(resdata.ass_depre_code);
					liger.get("DEPR_METHOD").setText(resdata.ass_depre_name);
					$("#ACC_DEPRE_AMOUNT").val(resdata.depre_years);
					$("#GB_CODE").val(resdata.gb_code);
					
					if(resdata.is_manage_depre == 1){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("是");
					}else if(resdata.is_manage_depre == 0){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("否");
					}
					$("#MANAGE_DEPRE_AMOUNT").val(resdata.manage_depre_amount);
					liger.get("MANAGE_DEPR_METHOD").setValue(resdata.manage_depr_method);
					liger.get("MANAGE_DEPR_METHOD").setText(resdata.manage_depr_method_name);
					$("#REG_NO").val(resdata.reg_no);
				} 
				
				
				if('${ass_nature}' == '02'){
					$("#ASS_SPEC").val(resdata.ass_spec);
					$("#ASS_MONDL").val(resdata.ass_model);
					$("#ASS_BRAND").val(resdata.ass_brand);
					liger.get("UNIT_CODE").setValue(resdata.ass_unit);
					liger.get("UNIT_CODE").setText(resdata.ass_unit_name);
					liger.get("FAC_ID").setValue(resdata.fac_id+"@"+resdata.fac_no);
					liger.get("FAC_ID").setText(resdata.fac_code);
					liger.get("VEN_ID").setValue(resdata.ven_id+"@"+resdata.ven_no);
					liger.get("VEN_ID").setText(resdata.ven_code);
					if(resdata.is_depr == 1){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("是");
					}else if(resdata.is_depr == 0){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("否");
					}
					if(resdata.is_measure == 1){
						liger.get("IS_MEASURE").setValue(resdata.is_measure);
						liger.get("IS_MEASURE").setText("是");
					}else if(resdata.is_measure == 0){
						liger.get("IS_MEASURE").setValue(resdata.is_measure);
						liger.get("IS_MEASURE").setText("否");
					}
					liger.get("DEPR_METHOD").setValue(resdata.ass_depre_code);
					liger.get("DEPR_METHOD").setText(resdata.ass_depre_name);
					$("#ACC_DEPRE_AMOUNT").val(resdata.depre_years);
					$("#GB_CODE").val(resdata.gb_code);
					
					if(resdata.is_bar == 1){
						liger.get("IS_BAR").setValue(resdata.is_bar);
						liger.get("IS_BAR").setText("是");
					}else if(resdata.is_bar == 0){
						liger.get("IS_BAR").setValue(resdata.is_bar);
						liger.get("IS_BAR").setText("否");
					}
					
					if(resdata.bar_type == 1){
						liger.get("BAR_TYPE").setValue(resdata.bar_type);
						liger.get("BAR_TYPE").setText("一维条码");
					}else if(resdata.bar_type == 2){
						liger.get("BAR_TYPE").setValue(resdata.bar_type);
						liger.get("BAR_TYPE").setText("二维条码");
					}
					
					
					if(resdata.is_manage_depre == 1){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("是");
					}else if(resdata.is_manage_depre == 0){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("否");
					}
					$("#MANAGE_DEPRE_AMOUNT").val(resdata.manage_depre_amount);
					liger.get("MANAGE_DEPR_METHOD").setValue(resdata.manage_depr_method);
					liger.get("MANAGE_DEPR_METHOD").setText(resdata.manage_depr_method_name);
					$("#REG_NO").val(resdata.reg_no);
					
					liger.get("MEASURE_TYPE").setValue(resdata.measure_type);
					if(resdata.measure_type == 0){
						liger.get("MEASURE_TYPE").setText("A类");
					}else if(resdata.measure_type == 1){
						liger.get("MEASURE_TYPE").setText("B类");
					}else if(resdata.measure_type == 2){
						liger.get("MEASURE_TYPE").setText("C类");
					}
					
					liger.get("MEASURE_KING_CODE").setValue(resdata.measure_king_code);
					liger.get("MEASURE_KING_CODE").setText(resdata.measure_king_name);
				}
				
				if('${ass_nature}' == '03'){
					$("#ASS_SPEC").val(resdata.ass_spec);
					$("#ASS_MONDL").val(resdata.ass_model);
					$("#ASS_BRAND").val(resdata.ass_brand);
					liger.get("UNIT_CODE").setValue(resdata.ass_unit);
					liger.get("UNIT_CODE").setText(resdata.ass_unit_name);
					liger.get("FAC_ID").setValue(resdata.fac_id+"@"+resdata.fac_no);
					liger.get("FAC_ID").setText(resdata.fac_code);
					liger.get("VEN_ID").setValue(resdata.ven_id+"@"+resdata.ven_no);
					liger.get("VEN_ID").setText(resdata.ven_code);
					if(resdata.is_depr == 1){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("是");
					}else if(resdata.is_depr == 0){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("否");
					}
					if(resdata.is_measure == 1){
						liger.get("IS_MEASURE").setValue(resdata.is_measure);
						liger.get("IS_MEASURE").setText("是");
					}else if(resdata.is_measure == 0){
						liger.get("IS_MEASURE").setValue(resdata.is_measure);
						liger.get("IS_MEASURE").setText("否");
					}
					liger.get("DEPR_METHOD").setValue(resdata.ass_depre_code);
					liger.get("DEPR_METHOD").setText(resdata.ass_depre_name);
					$("#ACC_DEPRE_AMOUNT").val(resdata.depre_years);
					$("#GB_CODE").val(resdata.gb_code);
					
					if(resdata.is_bar == 1){
						liger.get("IS_BAR").setValue(resdata.is_bar);
						liger.get("IS_BAR").setText("是");
					}else if(resdata.is_bar == 0){
						liger.get("IS_BAR").setValue(resdata.is_bar);
						liger.get("IS_BAR").setText("否");
					}
					
					if(resdata.bar_type == 1){
						liger.get("BAR_TYPE").setValue(resdata.bar_type);
						liger.get("BAR_TYPE").setText("一维条码");
					}else if(resdata.bar_type == 2){
						liger.get("BAR_TYPE").setValue(resdata.bar_type);
						liger.get("BAR_TYPE").setText("二维条码");
					}
					
					
					if(resdata.is_manage_depre == 1){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("是");
					}else if(resdata.is_manage_depre == 0){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("否");
					}
					$("#MANAGE_DEPRE_AMOUNT").val(resdata.manage_depre_amount);
					liger.get("MANAGE_DEPR_METHOD").setValue(resdata.manage_depr_method);
					liger.get("MANAGE_DEPR_METHOD").setText(resdata.manage_depr_method_name);
					$("#REG_NO").val(resdata.reg_no);
					
					liger.get("MEASURE_TYPE").setValue(resdata.measure_type);
					if(resdata.measure_type == 0){
						liger.get("MEASURE_TYPE").setText("A类");
					}else if(resdata.measure_type == 1){
						liger.get("MEASURE_TYPE").setText("B类");
					}else if(resdata.measure_type == 2){
						liger.get("MEASURE_TYPE").setText("C类");
					}
					
					liger.get("MEASURE_KING_CODE").setValue(resdata.measure_king_code);
					liger.get("MEASURE_KING_CODE").setText(resdata.measure_king_name);
				}
				
				if('${ass_nature}' == '04'){
					$("#ASS_SPEC").val(resdata.ass_spec);
					$("#ASS_MONDL").val(resdata.ass_model);
					$("#ASS_BRAND").val(resdata.ass_brand);
					liger.get("UNIT_CODE").setValue(resdata.ass_unit);
					liger.get("UNIT_CODE").setText(resdata.ass_unit_name);
					liger.get("FAC_ID").setValue(resdata.fac_id+"@"+resdata.fac_no);
					liger.get("FAC_ID").setText(resdata.fac_code);
					liger.get("VEN_ID").setValue(resdata.ven_id+"@"+resdata.ven_no);
					liger.get("VEN_ID").setText(resdata.ven_code);
					if(resdata.is_depr == 1){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("是");
					}else if(resdata.is_depr == 0){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("否");
					}
					if(resdata.is_measure == 1){
						liger.get("IS_MEASURE").setValue(resdata.is_measure);
						liger.get("IS_MEASURE").setText("是");
					}else if(resdata.is_measure == 0){
						liger.get("IS_MEASURE").setValue(resdata.is_measure);
						liger.get("IS_MEASURE").setText("否");
					}
					liger.get("DEPR_METHOD").setValue(resdata.ass_depre_code);
					liger.get("DEPR_METHOD").setText(resdata.ass_depre_name);
					$("#ACC_DEPRE_AMOUNT").val(resdata.depre_years);
					$("#GB_CODE").val(resdata.gb_code);
					
					if(resdata.is_bar == 1){
						liger.get("IS_BAR").setValue(resdata.is_bar);
						liger.get("IS_BAR").setText("是");
					}else if(resdata.is_bar == 0){
						liger.get("IS_BAR").setValue(resdata.is_bar);
						liger.get("IS_BAR").setText("否");
					}
					
					if(resdata.bar_type == 1){
						liger.get("BAR_TYPE").setValue(resdata.bar_type);
						liger.get("BAR_TYPE").setText("一维条码");
					}else if(resdata.bar_type == 2){
						liger.get("BAR_TYPE").setValue(resdata.bar_type);
						liger.get("BAR_TYPE").setText("二维条码");
					}
					
					
					if(resdata.is_manage_depre == 1){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("是");
					}else if(resdata.is_manage_depre == 0){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("否");
					}
					$("#MANAGE_DEPRE_AMOUNT").val(resdata.manage_depre_amount);
					liger.get("MANAGE_DEPR_METHOD").setValue(resdata.manage_depr_method);
					liger.get("MANAGE_DEPR_METHOD").setText(resdata.manage_depr_method_name);
					$("#REG_NO").val(resdata.reg_no);
					
					liger.get("MEASURE_TYPE").setValue(resdata.measure_type);
					if(resdata.measure_type == 0){
						liger.get("MEASURE_TYPE").setText("A类");
					}else if(resdata.measure_type == 1){
						liger.get("MEASURE_TYPE").setText("B类");
					}else if(resdata.measure_type == 2){
						liger.get("MEASURE_TYPE").setText("C类");
					}
					
					liger.get("MEASURE_KING_CODE").setValue(resdata.measure_king_code);
					liger.get("MEASURE_KING_CODE").setText(resdata.measure_king_name);
				}
				
				if('${ass_nature}' == '05'){
					$("#ASS_BRAND").val(resdata.ass_brand);
					liger.get("UNIT_CODE").setValue(resdata.ass_unit);
					liger.get("UNIT_CODE").setText(resdata.ass_unit_name);
					liger.get("FAC_ID").setValue(resdata.fac_id+"@"+resdata.fac_no);
					liger.get("FAC_ID").setText(resdata.fac_code);
					liger.get("VEN_ID").setValue(resdata.ven_id+"@"+resdata.ven_no);
					liger.get("VEN_ID").setText(resdata.ven_code);
					if(resdata.is_depr == 1){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("是");
					}else if(resdata.is_depr == 0){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("否");
					}
					liger.get("DEPR_METHOD").setValue(resdata.ass_depre_code);
					liger.get("DEPR_METHOD").setText(resdata.ass_depre_name);
					$("#ACC_DEPRE_AMOUNT").val(resdata.depre_years);
					$("#GB_CODE").val(resdata.gb_code);
					
					if(resdata.is_manage_depre == 1){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("是");
					}else if(resdata.is_manage_depre == 0){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("否");
					}
					$("#MANAGE_DEPRE_AMOUNT").val(resdata.manage_depre_amount);
					liger.get("MANAGE_DEPR_METHOD").setValue(resdata.manage_depr_method);
					liger.get("MANAGE_DEPR_METHOD").setText(resdata.manage_depr_method_name);
					$("#REG_NO").val(resdata.reg_no);
				}
				
				if('${ass_nature}' == '06'){
					liger.get("UNIT_CODE").setValue(resdata.ass_unit);
					liger.get("UNIT_CODE").setText(resdata.ass_unit_name);
					liger.get("VEN_ID").setValue(resdata.ven_id+"@"+resdata.ven_no);
					liger.get("VEN_ID").setText(resdata.ven_code);
					if(resdata.is_depr == 1){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("是");
					}else if(resdata.is_depr == 0){
						liger.get("IS_DEPR").setValue(resdata.is_depr);
						liger.get("IS_DEPR").setText("否");
					}
					liger.get("DEPR_METHOD").setValue(resdata.ass_depre_code);
					liger.get("DEPR_METHOD").setText(resdata.ass_depre_name);
					$("#ACC_DEPRE_AMOUNT").val(resdata.depre_years);
					$("#GB_CODE").val(resdata.gb_code);
					
					if(resdata.is_manage_depre == 1){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("是");
					}else if(resdata.is_manage_depre == 0){
						liger.get("IS_MANAGE_DEPRE").setValue(resdata.is_manage_depre);
						liger.get("IS_MANAGE_DEPRE").setText("否");
					}
					$("#MANAGE_DEPRE_AMOUNT").val(resdata.manage_depre_amount);
					liger.get("MANAGE_DEPR_METHOD").setValue(resdata.manage_depr_method);
					liger.get("MANAGE_DEPR_METHOD").setText(resdata.manage_depr_method_name);
					$("#REG_NO").val(resdata.reg_no);
				}

			},"json");
		});
	}

	function dateText() {
		WdatePicker({
			isShowClear : true,
			readOnly : false,
			dateFmt : 'yyyy-MM-dd'
		});
	}

	function this_close() {
		frameElement.dialog.close();
	}
	
	var show_id = 1;
	function showAndHide(){
		if(show_id == 1){
			$("#divTables").css("display",'none');
			$('.togglebtn').css('backgroundPositionY','-10px');
			show_id = 2;
		}
		else if(show_id == 2){
			$("#divTables").css("display",'table');
			$('.togglebtn').css('backgroundPositionY','0');
			show_id = 1;
		}
		navtab.onResize();
	}
	
	function printSet(){
		var template_code = "";
		var ass_para_map = "";
		var ass_nature = '${ass_nature}';
		if (ass_nature == "01") {
			template_code = "05192";
			ass_para_map = '${ass_05076}';
		} else if (ass_nature == "02") {
			template_code = "05190";
			ass_para_map = '${ass_05074}';
		} else if (ass_nature == "03") {
			template_code = "05191";
			ass_para_map = '${ass_05075}';
		} else if (ass_nature == "04") {
			template_code = "05053";
			ass_para_map = '${ass_05077}';
		} else if (ass_nature == "05") {
			template_code = "05194";
			ass_para_map = '${ass_05078}';
		} else if (ass_nature == "06") {
			template_code = "05195";
			ass_para_map = '${ass_05079}';
		}

		var useId = 0;//统一打印
		if (ass_para_map == 1) {
			//按用户打印
			useId = '${user_id }';
		}

		officeFormTemplate({
			template_code : template_code,
			user_id : useId
		});
	}
	
	function print(){
		var template_code = "";
		var ass_para_map = "";
		var ass_nature = '${ass_nature}';
		if (ass_nature == "01") {
			template_code = "05192";
			ass_para_map = '${ass_05076}';
		} else if (ass_nature == "02") {
			template_code = "05190";
			ass_para_map = '${ass_05074}';
		} else if (ass_nature == "03") {
			template_code = "05191";
			ass_para_map = '${ass_05075}';
		} else if (ass_nature == "04") {
			template_code = "05053";
			ass_para_map = '${ass_05077}';
		} else if (ass_nature == "05") {
			template_code = "05194";
			ass_para_map = '${ass_05078}';
		} else if (ass_nature == "06") {
			template_code = "05195";
			ass_para_map = '${ass_05079}';
		}

		var useId = 0;//统一打印
		if (ass_para_map == 1) {
			//按用户打印
			useId = '${user_id }';
		}
		var para = {
			ass_card_no : '${ass_card_no}',
			template_code : template_code,
			class_name : "com.chd.hrp.ass.serviceImpl.card.AssCardBasicServiceImpl",
			method_name : "queryCardPrint",
			isSetPrint : false,//是否套打，默认非套打
			isPreview : true,//是否预览，默认直接打印
			use_id : useId,
			p_num : 0,
			ass_naturs : ass_nature
		};

		officeFormPrint(para);
	}
	
	//模板条码打印
	function printCode(){
		
		var ass_para_map = '${ass_05100}';
		var ass_nature = '${ass_nature}';
		
		/**
		*无形资产 土地 房屋不提供条码打印
		*/
		if(ass_nature == '06' || ass_nature == '05' || ass_nature == '01'){
			return;
		}
		
		if($("#USE_STATE").val() == 6){
			$.ligerDialog.warn("待处置状态不能打印！");
			return;
		}
		
		if($("#USE_STATE").val() == 7){
			$.ligerDialog.warn("已处置状态不能打印！");
			return;
		}
		
		//var useId=0;//统一打印
		//if(ass_para_map==1){
			//按用户打印
			//useId = '${user_id }';
		//}
		var arr = [];
		var ass_cards = [];
		var ass_spec = $("#ASS_SPEC").val() == null ? "" : $("#ASS_SPEC").val();
		var ass_mondl = $("#ASS_MONDL").val() == null ? "" : $("#ASS_MONDL").val();
		var location = $("#LOCATION").val() == null ? "" : $("#LOCATION").val();
		var price = $("#PRICE").val() == null ? "" : $("#PRICE").val();
		var fac_name = liger.get("FAC_ID").getText().split(" ")[1] == null ? "": liger.get("FAC_ID").getText().split(" ")[1];
		var store_name = liger.get("STORE_ID").getText().split(" ")[1] == null || liger.get("STORE_ID").getText().split(" ")[1] == "undefined"  ? "" :liger.get("STORE_ID").getText().split(" ")[1];
		var note = $("#NOTE").val() == null ? "" : $("#NOTE").val();
		var ass_card_no = "";
		if(ass_para_map == "0"){
			ass_card_no = $("#ASS_ORI_CARD_NO").val() == null || $("#ASS_ORI_CARD_NO").val()=="" ? $("#ASS_CARD_NO").val() :$("#ASS_ORI_CARD_NO").val();
		}else if(ass_para_map == "1"){
			ass_card_no = $("#ASS_CARD_NO").val();
		}
		
		var obj = {
				title:'${hos_simple}'+'固定资产卡片',
			    bar_code:$("#BAR_CODE").val(),
				ass_card_no:ass_card_no,
				ass_code:liger.get("ASS_ID").getText().split(" ")[0],
				ass_name:liger.get("ASS_ID").getText().split(" ")[1],
				ass_spec:ass_spec+" "+ass_mondl,
				share_dept:liger.get("DEPT_ID").getText().split(" ")[1],
				in_date:$("#IN_DATE").val(),
				ass_seq_no:$("#MAN_CODE").val(),
				location:location,
				price:price, 
				fac_name:fac_name,
				store_name:store_name,
				note:note,
				img_path:'<%=path%>/'+'${bar_url}'
		};
		ass_cards.push($("#ASS_CARD_NO").val());
		arr.push(obj);
		cardLodopBarCode(arr);
		$.post("updateIsBarPrint.do?isCheck=false",{ass_nature:ass_nature,paramVo:ass_cards.toString()},function(data){
			
		},"json");
	}
</script>

</head>

<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<!-- <input name="ass_nature" type="text" id="ass_nature" /> -->
		<table id="divTables" cellpadding="0" cellspacing="0"
			class="l-table-edit" width="100%"></table>

		<table align="center" cellpadding="0" cellspacing="0"
			class="l-table-edit"  border="0" width="100%">
			<tr align="center">
				<td align="center" class="l-table-edit-td">
					<input class="l-button l-button-test"  type="button" value="保存" onclick="save();" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="l-button l-button-test"  type="button" value="模板设置" onclick="printSet();" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="l-button l-button-test"  type="button" value="打印" onclick="print();" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="l-button l-button-test"  type="button" value="条码打印" onclick="printCode();" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="l-button l-button-test"  type="button" value="关闭" onclick="this_close();" />
				</td>
				<td align="right" >
						<div class="togglebtn" onclick="showAndHide()"></div>
				</td>
			</tr>
		</table>
		<div id="navtab1"
			style="width: 100%; overflow: hidden; border: 1px solid #D3D3d3;"
			class="liger-tab"></div>
	</form>
</body>
</html>
