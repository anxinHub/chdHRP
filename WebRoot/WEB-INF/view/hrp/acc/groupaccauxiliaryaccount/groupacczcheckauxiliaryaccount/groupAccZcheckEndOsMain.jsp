<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/static_resource.jsp">
		<jsp:param value="grid,select,datepicker,ligerUI,checkbox" name="plugins" />
	</jsp:include>
	
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

	

	<style>
		.show-content label {
			margin-right: 10px;
		}

		.date_input_txt {
			cursor: pointer;
		}

		td.ipt.show-content {
			width: 540px;
		}

		.table-layout td.ipt {
			width:auto;
		}
	</style>
	<script>
		var grid;

		var gridManager = null;

		var userUpdateStr;

		var query_subj_code;

		var subj_box_data = "";

		$(function () {

		});

		function loadHead() {

			grid = $("#maingrid").ligerGrid({
				columns: [
					/*  { display: '期间', align: 'left',columns:[
											{ display: '年', isSort:false, name: 'acc_year', align: 'left', width: '4%'},
										{ display: '月', isSort:false, name: 'acc_month', align: 'left', width: '4%'}
									]
						},
						{ display: '凭证号', isSort:false, name: 'vouch_no', align: 'left',width: '8%'
						},
						{ display: '科目', isSort:false, name: 'subj_name', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								return rowdata.subj_name;
							}
						},
						{ display: '摘要', isSort:false, name: 'summary', align: 'left'
						},
						{ display: '借方', isSort:false, name: 'debit',align: 'right',width: '10%',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {return formatNumber(rowdata.debit, 2, 1);}
						},
						{ display: '贷方', isSort:false, name: 'credit', align: 'right',width: '10%',formatter:'###,##0.00',render : function(rowdata, rowindex, value) {return formatNumber(rowdata.credit, 2, 1);}
						},
						{ display: '方向', isSort:false, name: 'subj_dire', align: 'left', width: '2%'
						},
						{ display: '余额', isSort:false, name: 'end_os', align: 'right',width: '10%',formatter:'###,##0.00',
				render : function(rowdata, rowindex, value) 
						{  if(rowdata.end_os==0)
							return "Q";
						else
								return formatNumber(rowdata.end_os, 2, 1);
						}
						} */
					{ display: '核算项编码', isSort: false, name: 'obj_code',width:200, align: 'left' },
					{
						display: '核算项名称', isSort: false, name: 'obj_name',width:200, align: 'left'
						/* ,render : function(rowdata, rowindex, value) {
								return formatSpace(rowdata.obj_name,rowdata.subj_level - 1);
							} */
					},

					{
						display: '年初余额', align: 'center', id: '01',
						columns: [
							{ display: '借方', isSort: false, name: 'nc_d', align: 'right', id: 'nc_d', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.nc_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'nc_c', align: 'right', id: 'nc_c', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.nc_c, 2, 1); } } }
						]
					},

					{
						display: '期初余额', align: 'center', id: '02',
						columns: [
							{ display: '借方', isSort: false, name: 'qc_d', align: 'right', id: 'qc_d', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.qc_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'qc_c', align: 'right', id: 'qc_c', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.qc_c, 2, 1); } } }
						]
					},

					{
						display: '本期发生', align: 'center', id: '03',
						columns: [
							{ display: '借方', isSort: false, name: 'bq_d', align: 'right', id: 'bq_d', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.bq_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'bq_c', align: 'right', id: 'bq_c', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.bq_c, 2, 1); } } }
						]
					},

					{
						display: '累计发生', align: 'center', id: '04',
						columns: [
							{ display: '借方', isSort: false, name: 'sum_od', align: 'right', id: 'sum_od', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.sum_od, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'sum_oc', align: 'right', id: 'sum_oc', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.sum_oc, 2, 1); } } }
						]
					},

					{
						display: '期末余额', align: 'center', id: '05',
						columns: [
							{ display: '借方', isSort: false, name: 'end_d', align: 'right', id: 'end_d', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.end_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'end_c', align: 'right', id: 'end_c', formatter: '###,##0.00', render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.end_c, 2, 1); } } }
						]
					}
				],
				dataAction: 'server', dataType: 'server', usePager: false, url: 'collectGroupAccSubjZcheckEndOs.do',
				width: '100%', height: '100%', checkbox: false, rownumbers: true,
				delayLoad: true,
				/* groupColumnName:'subj_name',groupColumnDisplay:'科目', */
				selectRowButtonOnly: true,
				toolbar: {
					items: [
						{ text: '查询', id: 'search', click: queryNew, icon: 'search' },
						{ line: true },
						{ text: '打印', id: 'print', click: printDate, icon: 'print' },
						{ line: true }
					]
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}

		function showWindow() {

			var check_type_id = liger.get("check_item_type").getValue();

			if (check_type_id == "") {

				$.ligerDialog.error('核算类为必填项,不能为空');

				return;

			}

			$.ligerDialog.open({
				url: 'groupAccZCheckItemCheckPage.do?isCheck=false&check_type_id=' + check_type_id,
				height: 450,
				width: 720,
				title: '',
				modal: true,
				showToggle: false,
				showMax: false,
				showMin: false,
				isResize: true,
				buttons: [
					{
						text: '确定', onclick: function (item, dialog) {
							dialog.frame.saveSelectData();
							setTimeout(function () { dialog.close(); }, 300);
						}, cls: 'l-dialog-btn-highlight'
					},
					{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }]
			});
		}

		function subjSelector() {

			$.ligerDialog.open({
				url: '../../bookselector/accBookSubjSelectorPage.do?isCheck=false&flag=' + $("#subj_flag").val() + '&sign=11',
				data: { listBoxData: subj_box_data }, height: 470, width: 480, title: '账簿科目选择器', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				buttons: [
					{
						text: '确定', onclick:
						function (item, dialog) {
							var boxData = dialog.frame.getListBox();
							var param = "";
							var subj_param = "";
							if ($("#subj_flag").val() == "true") {
								subj_box_data = "";
								$.each(boxData, function (i, v) {
									if (boxData.length == (i + 1)) {
										subj_box_data = subj_box_data + "{'id':'" + v.id + "','text':'" + v.text + "'}";
										param = param + v.text;
										subj_param = subj_param + v.id.split(".")[1];
									} else {
										subj_box_data = subj_box_data + "{'id':'" + v.id + "','text':'" + v.text + "'},";
										param = param + v.text + ",";
										subj_param = subj_param + v.id.split(".")[1] + ",";
									}
								});
							} else {
								subj_box_data = "";
								$.each(boxData, function (i, v) {
									if (boxData.length == (i + 1)) {
										subj_box_data = subj_box_data + "{'id':'" + v.id + "','text':'" + v.text + "'}";
										param = param + v.text;
										subj_param = subj_param + v.id.split(".")[1];
									} else {
										subj_box_data = subj_box_data + "{'id':'" + v.id + "','text':'" + v.text + "'},";
										param = param + v.text + ",";
										subj_param = subj_param + v.id.split(".")[1] + ",";
									}
								});
							}
							query_subj_code = param;
							liger.get("subj_code").setValue(subj_param);

							liger.get("subj_code").setText(param);
							dialog.close();

						}, cls: 'l-dialog-btn-highlight'
					},
					{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
				]
			});
		}

		/* function onCheckTypeChange(){
			para="";//清空参数
			para = {
				check_type_id : liger.get("check_item_type").getValue()
			};
			
			//$("#check_item_code").val('');
			//方案
			autocomplete("#check_item_code",
					"../queryCheckItem.do?isCheck=false", "id", "text",
					true, true, para);
			$("#subj_code").val('');
			$("#subj_code").ligerComboBox({
				parms:para,
					url: '../querySubj.do?isCheck=false',
					valueField: 'id',
						textField: 'text', 
						selectBoxWidth: 160,
					autocomplete: true,
					width: 160,
				
				});
		} */

		//打印数据
		function printDate() { 

			if (grid.getData().length == 0) {
				$.ligerDialog.error("请先查询数据！");
				return;
			}

			var heads={
		    		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		    		  "rows": [
			          {"cell":0,"value":"会计日期："+acc_year_month.getValue(),"colSpan":"2"},
			          {"cell":0,"value":"科目："+subj_select.val()+subj_select.getText(),"br":"true","colSpan":"2" }
		    		  ]
		    	};
		     
		   		var printPara={
		   			title: "科目核算项余额表",//标题
		   			columns: JSON.stringify(grid.getPrintColumns()),//表头
		   			class_name: "com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccZcheckAuxiliaryAccountService",
					method_name: "collectGroupAccSubjZcheckEndOsPrint",
					bean_name: "groupAccZcheckAuxiliaryAccountService",
					heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
					/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		   		};
		    	
		   		//执行方法的查询条件
		   		$.each(grid.options.parms,function(i,obj){
		   			printPara[obj.name]=obj.value;
		    	});
		   		
		    	officeGridPrint(printPara);

		}

	</script>
	<script>
		var acc_year_month, subj_select,rela_select,cur_select, item_type, item_code, is_state_check, is_show_check,is_end_os;
		var if_is_state = true;
		var if_is_end = false;
		$(function () {
			init();
		});
 
		//构造页面控件
		function init() {
			loadHead();	//加载数据

			initDate();
			//科目
			/* subj_select = $("#subj_code_select").etSelect({
				url: '../../querySubjCode.do?isCheck=false',
				para: { is_last: 1, is_check: 1 },
				searchField:['text','search'],
				onInit: function (value) {
					loadItemType(value);
				},
				onChange: function (value) {
					loadItemType(value);
				},
				load:function(value,callback){
					$.ajax({
					    url:"../../querySubjCode.do?isCheck=false",    //请求的url地址
					    dataType:"json",   //返回格式为json 
					    data:{key:value},    //参数值
					    type:"POST",  
					    success:function(req){
					        callback(req); 
					    }

					});
				}
			}); */
			//医院账套关系查询 
			rela_select = $("#rela_code_select").etSelect({
				url: '../../../sys/queryHosCopyDict.do?isCheck=false',
				onInit: function (value) {
					reloadSubjSelect(value)
				},
				onChange: function (value) {
					reloadSubjSelect(value)
				}
						
			});
			//科目    根据账套级联科目
		     subj_select = $("#subj_code_select").etSelect({
				//defaultValue: "${copy_code}",
				
				onChange: function (value) {
					loadItemType(value);
				}
			}); 
			//联动加载账套
			function reloadSubjSelect(value) {
				console.log(value)
				subj_select.reload({
					url: "../../querySubjCodeByRela.do?isCheck=false",
					para: { rela_code: value },
					searchField:['text','search'],
					load:function(value,callback){
						$.ajax({
						    url:"../../querySubjByHosCopyRela.do?isCheck=false",    //请求的url地址
						    dataType:"json",   //返回格式为json 
						    data:{key:value},    //参数值
						    type:"POST",  
						    success:function(req){
						        callback(req); 
						    }

						});
					}
				});
			}
			  
			
			//核算类
			item_type = $("#item_type_select").etSelect({
				defaultValue: 1,
				onChange: function (value) {
					loadItemCode(value);
				}
			});
			//加载核算类
			function loadItemType(subj_code) {
				item_type.reload({
					url: "../../queryCheckTypeBySubjCode.do?isCheck=false",
					para: { subj_code: subj_code }
				});
			}


			//核算项
			item_code = $("#item_code_select").etSelect({
				defaultValue: "none",
				searchField:['text','search'],
				load:function(value,callback){  
					$.ajax({
					    url:"../../queryCheckItemByType.do?isCheck=false",    //请求的url地址
					    dataType:"json",   //返回格式为json 
					    data:{"key":value,"check_type_id":item_type[0].value},    //参数值
					    type:"POST",  
					    success:function(req){  
					        callback(req); 
					    }
 
					});
				}
			 
			});
			//加载核算项
			function loadItemCode(item_type) { 
				item_code.reload({
					url: "../../queryCheckItemByType.do?isCheck=false",
					para: {
						check_type_id: item_type
					},
				});
			}
			
			//币种
			cur_select = $("#cur_code_select").etSelect({
				url: '../../queryCur.do?isCheck=false'
			});
			

			//包含未记账
			is_state_check = $("#is_state_check").etCheck({
				ifChanged: function (status, checked, disabled) {
					CheckState("is_state_check", checked);
				}
			});
			is_end_os = $("#is_end_os").etCheck({
				ifChanged: function (status, checked, disabled) {
					CheckState("is_end_os", checked);
				}
			});
			is_show_check1 = $("#show_check1").etCheck({
				checked: true,
				ifChanged: function (status, checked, disabled) {
					CheckState("show_check1", checked);
				}
			});
			is_show_check2 = $("#show_check2").etCheck({
				checked: true,
				ifChanged: function (status, checked, disabled) {
					CheckState("show_check2", checked);
				}
			});
			is_show_check3 = $("#show_check3").etCheck({
				checked: true,
				ifChanged: function (status, checked, disabled) {
					CheckState("show_check3", checked);
				}
			});
			is_show_check4 = $("#show_check4").etCheck({
				checked: true,
				ifChanged: function (status, checked, disabled) {
					CheckState("show_check4", checked);
				}
			});
			is_show_check5 = $("#show_check5").etCheck({
				checked: true,
				ifChanged: function (status, checked, disabled) {
					CheckState("show_check5", checked);
				}
			});

			
		}

		//选中状态
		function CheckState(check_id, state) {
			switch (check_id) {
				case "is_state_check":
					if_is_state = state;
					break;
				case "is_end_os":
					if_is_end = state;
					break;
				case "show_check1":
					if (state) {
						grid.toggleCol('01', true);
						grid.toggleCol('nc_d', true);
						grid.toggleCol('nc_c', true);
					} else {
						grid.toggleCol('01', false);
						grid.toggleCol('nc_d', false);
						grid.toggleCol('nc_c', false);
					}
					break;
				case "show_check2":
					if (state) {
						grid.toggleCol('02', true);
						grid.toggleCol('qc_d', true);
						grid.toggleCol('qc_c', true);
					} else {
						grid.toggleCol('02', false);
						grid.toggleCol('qc_d', false);
						grid.toggleCol('qc_c', false);
					}
					break;
				case "show_check3":
					if (state) {
						grid.toggleCol('03', true);
						grid.toggleCol('bq_d', true);
						grid.toggleCol('bq_c', true);
					} else {
						grid.toggleCol('03', false);
						grid.toggleCol('bq_d', false);
						grid.toggleCol('bq_c', false);
					}
					break;
				case "show_check4":
					if (state) {
						grid.toggleCol('04', true);
						grid.toggleCol('sum_od', true);
						grid.toggleCol('sum_oc', true);
					} else {
						grid.toggleCol('04', false);
						grid.toggleCol('sum_od', false);
						grid.toggleCol('sum_oc', false);
					}
					break;
				case "show_check5":
					if (state) {
						grid.toggleCol('05', true);
						grid.toggleCol('end_d', true);
						grid.toggleCol('end_c', true);
					} else {
						grid.toggleCol('05', false);
						grid.toggleCol('end_d', false);
						grid.toggleCol('end_c', false);
					}
					break;
				default:
					break;
			}

		}

		//初始化期间默认值
		function initDate() {
			var url = "../../queryYearMonth.do?isCheck=false";
			$.ajax({
				url: url,
				type: "POST",
				dataType: "JSON",
				success: function (res) {
					acc_year_month = $("#acc_year_month").etDatepicker({
						defaultDate: [res[0].text, res[0].text],
						view: "months",
						minView: "months",
						range: true,
						dateFormat: "yyyy.mm",
						multipleDatesSeparator: "-",
						minDate: res[0].text,
						maxDate: res[res.length - 1].text,
						todayButton: false
					})
				},
				error: function (res) {
					console.log(res);
				}
			})
		}

		function queryNew() {
			grid.options.parms = [];

			grid.options.newPage = 1;
			//根据表字段进行添加查询条件

			var year_month = acc_year_month.getValue();
			var start_date, end_date;
			if (typeof year_month !== "object") {
				$.ligerDialog.error('会计期间格式不正确');
				return;
			} else {
				start_date = year_month[0];
				end_date = year_month[1];
			}

			var check_type = item_type.val();
			var check_item = item_code.val();

			if (check_type == "") {
				$.ligerDialog.error('核算类为必填项,不能为空');
				return;
			}

			var subj_code = subj_select.val();
			if (subj_code == "") {
				$.ligerDialog.error('科目为必填项,不能为空');
				return;
			}
			var rela_code = rela_select.val();
			if (rela_code == "") {
				$.ligerDialog.error('账套为必填项,不能为空');
				return;
			}
			grid.options.parms.push({ name: 'begin_year', value: new Date(start_date).getFullYear() });

			grid.options.parms.push({ name: 'begin_month', value: (new Date(start_date).getMonth() + 1) });

			grid.options.parms.push({ name: 'end_year', value: new Date(end_date).getFullYear() });

			grid.options.parms.push({ name: 'end_month', value: (new Date(end_date).getMonth() + 1) })

			grid.options.parms.push({ name: 'subj_code', value: subj_code });
			
			grid.options.parms.push({ name: 'rela_code', value: rela_code });

			grid.options.parms.push({ name: 'check_type_id', value: check_type });

			grid.options.parms.push({ name: 'check_item_code', value: check_item });

			var is_state = 99;
			var is_end=0;
			if (if_is_state) {
				is_state = 1;
			}
			if (if_is_end) {
				is_end = 1;
			}
			grid.options.parms.push({ name: 'state', value: is_state });
			
			grid.options.parms.push({ name: 'is_end', value: is_end });

			//加载查询条件
			grid.loadData(grid.where);
		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">

	<input type="hidden" id="subj_flag" name="subj_flag" />
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<div class="main" style="display:block;">
		<table class="table-layout" cellpadding="0" border=0 cellspacing="0">
			<tr>
				<td class="no-empty label">会计期间：</td>
				<td class="ipt">
					<input class="date_input_txt" id="acc_year_month" style="width:250px" readonly type="text">
				</td>
				<td class="no-empty label">账　套：</td>
				<td class="ipt"  colspan="1">
					<select name="" id="rela_code_select" style="width:300px"></select>
				</td>
				<td class="label">币种：</td>
				<td class="ipt" style="width:100px">
					<select name="" id="cur_code_select" style="width:90px;"></select>
				</td>
			</tr>
			<tr>
				<td class="no-empty label">科　　目：</td>
				<td class="ipt"  colspan="1">
					<select name="" id="subj_code_select" style="width:250px"></select>
				</td>
				<td class="no-empty label">核算类：</td>
				<td class="ipt">
					<select name="" id="item_type_select" style="width:300px;"></select>
				</td>
				
				<td colspan=2>
					<input type="checkbox" name="" id="is_state_check" checked="checked">
					<label for="is_state_check">包含未记账</label>
				
					<input type="checkbox" name="" id="is_end_os">
					<label for="is_end_os">显示零余额</label>
				</td>
			</tr>
			<tr>
				<td class="label">核算项：</td>
				<td class="ipt">
					<select name="" id="item_code_select" style="width:250px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">显示栏目：</td>
				<td class="ipt show-content" colspan="6">
					<input type="checkbox" id="show_check1" name="isShow" checked>
					<label for="show_check1">年初余额</label>
					<input type="checkbox" id="show_check2" name="isShow" checked>
					<label for="show_check2">期初余额</label>
					<input type="checkbox" id="show_check3" name="isShow" checked>
					<label for="show_check3">本期发生</label>
					<input type="checkbox" id="show_check4" name="isShow" checked>
					<label for="show_check4">累计发生</label>
					<input type="checkbox" id="show_check5" name="isShow" checked>
					<label for="show_check5">期末余额</label>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid" style="display:block"></div>

</body>

</html>