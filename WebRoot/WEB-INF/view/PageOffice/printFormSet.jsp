<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
		<%
	String path = request.getContextPath();
%>
			<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
			<html>

			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
				<title></title>
				<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
				<!-- <link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />  -->
				<style type="text/css">
					#gridHeader {
						background: #e2f0ff url(../../../CHD-HRP/lib/ligerUI/skins/Aqua/images/grid/header-bg.gif?v=2017-02-22) repeat-x left bottom;
					}

					#gridHeader td {
						/* 表头样式 */
						border-right: 1px solid #a3c0e8;
						border-bottom: 1px solid #a3c0e8;
						padding: 4px 5px;
						text-align: center;
					}
					#gridCenter {
						height: auto;
					}
					
					#gridCenter td {
						/* 表格内容样式 */
						border-right: 1px solid #a3c0e8;
						border-bottom: 1px solid #a3c0e8;
						overflow: hidden;
						padding: 4px 5px;
						vertical-align: middle;
						word-wrap: break-word;
					}

					.optname {
						width: 130px;
					}

					.opttype {
						width: 70px;
					}

					.optvalue {
						width: 100px;
					}

					.operation {
						width: 70px;
						text-align: center;
					}
				</style>
				<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
				<script src="<%=path%>/lib/json2.js"></script>
				<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
				<script type="text/javascript" src="<%=path%>/pageoffice.js" id="po_js_main"></script>
				<!-- <script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
				<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script> -->
				<script type="text/javascript">
					var grid;
					var gridManager = null;
					
					$(function () {
						// $("#layout1").ligerLayout({
						// 	rightWidth: 460
						// });
						$("#pageOfficeDiv").css("height", $(window).height());
						$("#pageOfficeDiv").css("width", $(window).width() - 435);
						$('.adjoined-bottom').width($("#pageOfficeDiv").width());
						$('#maingrid').height($(window).height());
						$('#maingrid').width($(window).width() - $('.adjoined-bottom').width()); // 页面初始化时设置表格宽度
						$('#gridContent').height($(window).height() - $('#gridTop').height()); // 页面初始化时设置表格内容宽度
						//$(':button').ligerButton({width:110});
						// loadHead(); //加载数据
						loadHead2();

					});

					function loadHead2() {
						var para = {
							template_code: $("#template_code").val(),
							use_id: $("#use_id").val(),
							group_id: $("#group_id").val(),
							hos_id: $("#hos_id").val(),
							copy_code: $("#copy_code").val()
						}
						$.ajax({
								type: "POST",
								url: "queryFormPrintPara.do?isCheck=false",
								data: para,
								dataType: 'json',
								/* beforeSend: function () {
									$('#gridloading').show();
								}, */
								success: function (msg) {
									_renderTable(msg.Rows);
									if(msg.Rows.length==0){
										alert("没有配置打印参数！");
									}
								},
								error: function (XMLHttpRequest, textStatus, errorThrown) {
									var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
									if (sessionstatus == "MSG_TIME_OUT") {
										alert('会话超时，请重新登录.');
									} else if (sessionstatus == "NOT_PERMID") {
										alert('没有该操作权限.');
									} else if (sessionstatus == "REQUEST_MAPPING") {
										alert("没有找到对应的请求.");
									} else {
										alert('系统异常，请稍后重试.');
									}
								}
									/* complete: function () {
										$('#gridloading').hide();
									} */
								});
						}

						function _renderTable(data) {
							for (var i = 0; i<data.length; i++) {
								var $tr = $('<tr></tr>');
								var rowArr = [];
								$.each(data[i], function (index, value) {
									var rowdata = data[i];
									// 参数名字列 渲染  (rowArr的索引值代表顺序加载)
									if (index == 'para_name') {
										rowArr[0] = "<td class='optname'><span title='" + rowdata.para_code + "'>" +
											value + "</span></td>";
										//  参数类型列 渲染
									} else if (index == 'flag_value') {
										rowArr[1] = '<td class="opttype">' + value + '</td>';
										// 参数值列 渲染
									} else if (index == 'para_value') {
										var str = '';
										if (rowdata.flag == 7) {

											str = "<select id = 'p_" + rowdata.para_code + "' class='inputMar'>";
											if (rowdata.para_value == "是") {
												str = str + "<option value='否'>否</option>";
												str = str + "<option value='是' selected='selected'>是</option>";
											} else {
												str = str + "<option value='否'selected='selected'>否</option>";
												str = str + "<option value='是' >是</option>";
											}
											str = str + "</option></select>";

										} else if (rowdata.flag == 1 || rowdata.flag == 2 || rowdata.flag == 5 ||
											rowdata.flag == 8 || rowdata.flag ==
											10) {
											//1主表(横纵坐标)、2从表(纵坐标)、系统参数：3是否、5金额大写(横纵坐标)、8输入文本框(纵坐标)、10输入文本框(横纵坐标)
											str = "<input id='p_" + rowdata.para_code + "' value='" + (rowdata.para_value ==
													null ? "" : rowdata.para_value) +
												"' class='inputMar' style='margin-top:5px;width:80px;'>";

										} else if (rowdata.flag == 9) {

											var obj = eval('[' + rowdata.para_json + ']');
											str = "<select id='p_" + rowdata.para_code +
												"' style='margin-top:5px;width:150;'>";
											for (var json in obj) {
												if (rowdata.para_value == obj[json].code) {
													str = str + "<option value='" + obj[json].code +
														"' selected='selected'>" + obj[json].value +
														"</option>";
												} else {
													str = str + "<option value='" + obj[json].code + "'>" + obj[
														json].value + "</option>";
												}
											}
											str = str + "</option>";

										} else {
											//str="<button style='margin-top:5px;' onclick=myDrag('"+rowdata.para_code+"','"+rowdata.para_name+"')>填充</button>";
										}
										rowArr[2] = '<td class="optvalue">' + str + '</td>';
									}

								})
								//  操作列 渲染
								if (data[i].flag == 1 || data[i].flag == 2 || data[i].flag == 5 || data[i].flag == 10) {
									var str = "";
									str = "<a href=javascript:void(0); ondragstart='return false' onclick=myDrag(" + data[i]
										.flag + ",'" +
										data[i].para_code + "','" + data[i].para_name + "'," + i +
										"); style='cursor:pointer;'>取值</a>";
									rowArr.push('<td class="operation">' + str + '</td>');
								} else if (data[i].para_code == "003") {
									str = "<span title='某内容套打的时候需要显示，在单元格右键插入批注即可'>说明</span>";
									rowArr.push('<td class="operation">' + str + '</td>');
								} else if (data[i].para_code == "016") {
									str = "<span title='承前/过次页要显示在哪列，如：5；填写每页打印条数生效'>说明</span>";
									rowArr.push('<td class="operation">' + str + '</td>');
								} else if (data[i].para_code == "017") {
									str = "<span title='要统计合计的列号，如7,8；填写每页打印条数生效'>说明</span>";
									rowArr.push('<td class="operation">' + str + '</td>');
								}else {
									rowArr.push('<td class="operation"></td>');
								}

								$tr.html(rowArr.join(''));

								$('#gridCenter tbody').append($tr);
							}
						}

						function loadHead() {

							var para = {
								template_code: $("#template_code").val(),
								use_id: $("#use_id").val(),
								group_id: $("#group_id").val(),
								hos_id: $("#hos_id").val(),
								copy_code: $("#copy_code").val()
							}

							/* ajaxJsonObjectByUrl("queryFormPrintPara.do?isCheck=false",para,function (responseData){
								alert()
							}); */

							grid = $("#maingrid").ligerGrid({
								columns: [
									/* { display: '参数编码', name: 'para_code', align: 'left',width:100,
										 render : function(rowdata, rowindex,value) {
												var str="";
												if(rowdata.flag<7){
													str="<a href=javascript:void(0); ondragstart='return false' onclick=myDrag("+rowdata.flag+",'"+rowdata.para_code+"','"+rowdata.para_name+"'); title='点击填充到指定位置' style='cursor:pointer;'>"+rowdata.para_code+"</a>";
													
												}else{
													str=rowdata.para_code;
												}
												
												return str;
										}
									}, */
									{
										display: '参数名称',
										name: 'para_name',
										align: 'left',
										width: 130,
										render: function (rowdata, rowindex, value) {

											return "<span title='" + rowdata.para_code + "'>" + rowdata.para_name +
												"</span>";
										}
									},
									{
										display: '参数类型',
										name: 'flag_value',
										align: 'left',
										width: 70
									},
									{
										display: '参数值',
										name: 'para_value',
										align: 'left',
										width: 100,
										render: function (rowdata, rowindex, value) {
											var str = "";

											if (rowdata.flag == 7) {

												str = "<select id = 'p_" + rowdata.para_code +
													"' class='inputMar'>";
												if (rowdata.para_value == "是") {
													str = str + "<option value='否'>否</option>";
													str = str +
														"<option value='是' selected='selected'>是</option>";
												} else {
													str = str +
														"<option value='否'selected='selected'>否</option>";
													str = str + "<option value='是' >是</option>";
												}
												str = str + "</option></select>";

											} else if (rowdata.flag == 1 || rowdata.flag == 2 || rowdata.flag ==
												5 || rowdata.flag == 8 || rowdata.flag ==
												10) {
												//1主表(横纵坐标)、2从表(纵坐标)、系统参数：3是否、5金额大写(横纵坐标)、8输入文本框(纵坐标)、10输入文本框(横纵坐标)
												str = "<input id='p_" + rowdata.para_code + "' value='" + (
														rowdata.para_value == null ? "" : rowdata.para_value
													) +
													"' class='inputMar' style='margin-top:5px;width:80px;'>";

											} else if (rowdata.flag == 9) {

												var obj = eval('[' + rowdata.para_json + ']');
												str = "<select id='p_" + rowdata.para_code +
													"' style='margin-top:5px;width:150;'>";
												for (var json in obj) {
													if (rowdata.para_value == obj[json].code) {
														str = str + "<option value='" + obj[json].code +
															"' selected='selected'>" + obj[json].value +
															"</option>";
													} else {
														str = str + "<option value='" + obj[json].code +
															"'>" + obj[json].value + "</option>";
													}
												}
												str = str + "</option>";

											} else {
												//str="<button style='margin-top:5px;' onclick=myDrag('"+rowdata.para_code+"','"+rowdata.para_name+"')>填充</button>";
											}
											return str;
										}
									},
									{
										display: '操作',
										name: 'operation',
										align: 'center',
										width: 80,
										render: function (rowdata, rowindex, value) {
											var str = "";
											if (rowdata.flag == 1 || rowdata.flag == 2 || rowdata.flag == 5 ||
												rowdata.flag == 10) {
												str =
													"<a href=javascript:void(0); ondragstart='return false' onclick=myDrag(" +
													rowdata.flag + ",'" +
													rowdata.para_code + "','" + rowdata.para_name + "'," +
													rowindex + "); style='cursor:pointer;'>取值</a>";

											} else if (rowdata.para_code == "003") {
												str = "<span title='某内容套打的时候需要显示，在单元格右键插入批注即可'>说明</span>";
											} else if (rowdata.para_code == "016") {
												str = "<span title='某内容套打的时候需要显示，在单元格右键插入批注即可'>说明</span>";
											} else if (rowdata.para_code == "017") {
												str = "<span title='某内容套打的时候需要显示，在单元格右键插入批注即可'>说明</span>";
											}

											return str;
										}
									}
								],
								dataAction: 'server',
								dataType: 'server',
								usePager: false,
								url: 'queryFormPrintPara.do?isCheck=false',
								width: '99%',
								height: '100%',
								checkbox: false,
								rownumbers: true,
								urlParms: para,
								delayLoad: false,
								selectRowButtonOnly: true
							});

							gridManager = $("#maingrid").ligerGetGridManager();

						}


						function mySave() {

							var para_value = "";
							var isCheck = true;
							var beginNum = 0;
							var endNum = 0;
							var re = /^[0-9]*[1-9][0-9]*$/;

							$.each($(".inputMar"), function (i) {
								var thisVal = $(this).val();

								if (thisVal!="" && $(this).attr("id").replace("p_", "") == "004") {
									beginNum = thisVal;
									
									if (!re.test(beginNum)) {
										alert("起始行只能为正整数！");
										isCheck = false;
										return;
									}
								}
								if (thisVal!="" && $(this).attr("id").replace("p_", "") == "005") {
									endNum = thisVal;
									if (!re.test(endNum)) {
										alert("结束行只能为正整数！");
										isCheck = false;
										return;
									}
								}
								/* if($(this).attr("id").replace("p_","")=="006"){
									if(thisVal!="" && thisVal.split(",").length!=2){
										$.ligerDialog.error("纸张大小的格式不正确，宽，高！");
										isCheck=false;
										return;
									}
									if(thisVal.split("*")[0]=="" || thisVal.split("*")[1]==""){
										$.ligerDialog.error("纸张大小的格式不正确，宽，高！");
										isCheck=false;
										return;
									}
								} */

								if (thisVal == "") thisVal = " ";
								para_value = para_value + $(this).attr("id").replace("p_", "") + "@" + thisVal +
									"#";
							});
							
							if (para_value == "") {
								alert("没有配置打印参数！");
								return;
							}
							
							if (!isCheck) {
								return;
							}
							
							if (parseInt(endNum)!=0 && parseInt(beginNum) > parseInt(endNum)) {
								alert("起始行不能大于结束行！");
								return;
							}

							if ($("#p_012").val() != undefined) {
								para_value = para_value + "012@" + $("#p_012").val() + "#";
							}

							var para = {
								para_value: para_value,
								mod_code:$("#mod_code").val(),
								template_code: $("#template_code").val(),
								use_id: $("#use_id").val(),
								group_id: $("#group_id").val(),
								hos_id: $("#hos_id").val(),
								copy_code: $("#copy_code").val()
							};

							ajaxJsonObjectByUrl("saveFormPrintPara.do?isCheck=false", para, function (responseData) {
								document.getElementById("PageOfficeCtrl1").WebSave();
								var res = document.getElementById("PageOfficeCtrl1").CustomSaveResult;
								if (res == "ok") {
									alert("保存成功！");
								} else {
									alert("保存失败！");
								}
							});

						}

						function myDrag(flag, para_code, para_name, rowindex) {
							if (activeCell == "") {
								return;
							}

							var activeRowIndex = parseInt(activeCell.split("$")[2]);
							var activeColumnIndex = parseInt(getNum(activeCell.split("$")[1]));


							$.each($(".inputMar"), function (i, obj) {

								if (obj.id == "p_" + para_code) {

									if (flag == 1 || flag == 5 || flag == 10) {
										//横纵坐标
										$(this).val(activeRowIndex + "," + activeColumnIndex);
										return true;
									} else if (flag == 2) {
										//纵坐标
										$(this).val(activeColumnIndex);
										return true;
									}
								}

							});

						}

						//字母转数字
						function getNum(s) {

							var chars = ["A", "B", "C", "D", "E", "F", "G",
								"H", "I", "J", "K", "L", "M", "N",
								"O", "P", "Q", "R", "S", "T", "U",
								"V", "W", "X", "Y", "Z"
							];

							function getCharIndex(string) {
								var strings = $.trim(string).split("");
								var indexs = [];
								var temp = [];
								var result = 0;
								for (var i = 0; i < strings.length; i++) {
									indexs.push($.inArray(strings[i], chars) + 1);
								}
								for (var h = 0; h < indexs.length; h++) {
									if (h === indexs.length - 1) {
										temp.push(indexs[h])
									} else {
										temp.push(indexs[h] * chars.length + (h === 0 ? 0 : Math.pow(chars.length, h + 1) -
											26))
									}
								}
								for (var n = 0; n < temp.length; n++) {
									result += temp[n]
								}
								return result;
							}
							return getCharIndex(s)
						}

						//加载完JS回调函数
						function AfterDocumentOpened() {
							// document.getElementById("PageOfficeCtrl1").FullScreen=true;
							//document.getElementById("PageOfficeCtrl1").Document.Application.ActiveWindow.ActivePane.View.Type = 3;
							//点击Excel中的指定的单元格，调用js函数OnCellClick()弹出一个可以选择部门的对话框
							//document.getElementById("PageOfficeCtrl1").JsFunction_OnExcelCellClick = "cellClick()";
						}


						var activeCell = "";

						function cellClick(Celladdress, value, left, bottom) {
							activeCell = Celladdress;
							//alert(Celladdress+"----"+value+"----"+left+"-------"+bottom)
						}

						//保存
						function ShowSave() {
							mySave();
						}



						//保存为
						function ShowAsSave() {
							document.getElementById("PageOfficeCtrl1").ShowDialog(2);
						}

						//打印
						function ShowPrint() {
							try{
								document.getElementById("PageOfficeCtrl1").PrintOut();//直接打印
							}catch(err){
								document.getElementById("PageOfficeCtrl1").ShowDialog(4);//对话框打印
							}
						}

						//打印预览
						function ShowPrintPre() {
							try{
								document.getElementById("PageOfficeCtrl1").PrintPreview();
							}catch(err){
								document.getElementById("PageOfficeCtrl1").ShowDialog(4);//对话框打印
							}
							
						}

						//页面设置
						function ShowPrintSet() {
							document.getElementById("PageOfficeCtrl1").ShowDialog(5);
						}

						//全屏
						function ShowFullScreen() {
							document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById(
								"PageOfficeCtrl1").FullScreen;
						}

						//关闭
						function ShowClose() {
							POBrowser.closeWindow();
						}
						
						//说明
						function ShowNote(){
							var note="说明：\r";
							note=note+"1、设置千分符：单元格格式-》数值-》勾选使用千位分隔符\r";
							note=note+"2、设置日期格式：单元格格式-》自定义-》yyyy-MM-dd HH:mm:ss\r";
							note=note+"3、页眉页脚：页面设置-》页眉/页脚-》自定义页面/页脚-》插入页码、日期，系统变量包括：\r";
							note=note+"用户名称：{user_name}，职工名称：{emp_name}\r";
							note=note+"医院名称：{hos_name}，集团名称：{group_name}\r";
							alert(note);
						}
				</script>

			</head>

			<body style="padding: 0px; overflow: hidden;">

				<input id="use_id" type="text" value="${use_id}" style="display:none" />
				<input id="template_code" type="text" value="${template_code}" style="display:none" />
				<input id="group_id" type="text" value="${group_id}" style="display:none" />
				<input id="hos_id" type="text" value="${hos_id}" style="display:none" />
				<input id="copy_code" type="text" value="${copy_code}" style="display:none" />
				<input id="mod_code" type="text" value="${mod_code}" style="display:none" />

				<div id="layout1" style="width:100%;margin:0; padding:0;">

					<div class="adjoined-bottom" position="center" style='float:left;' title="">

						<div id="pageOfficeDiv" style="display:none">
							<!-- *********************pageoffice组件的使用 **************************-->
							<po:PageOfficeCtrl id="PageOfficeCtrl1" />
							<!-- *********************pageoffice组件的使用 **************************-->
						</div>
					</div>

					<div id="maingrid" position="right" style='float:left;position:relative'>
						<!-- <div id="gridloading" class="l-loading" style="display: none"></div> -->
						<div id='gridTop' style='width:100%;zoom:1'>
							<table cellpadding='0' cellspacing='0' id='gridHeader'>
								<tbody>
									<tr>
										<td class='optname'>参数名称</td>
										<td class='opttype'>参数类型</td>
										<td class='optvalue'>参数值</td>
										<td class='operation'>操作</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div id='gridContent' style='width:100%;overflow:auto;zoom:1'>
							<table cellpadding='0' cellspacing='0' id='gridCenter' style='table-layout:fixed;' height='100%'>
								<tbody>

								</tbody>
							</table>

						</div>
					</div>
				</div>

				<script>
					$("#pageOfficeDiv").show();
				</script>
			</body>

			</html>