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
					{ 
						display:  '核算项1', isSort: false, hide: true, name:  'check1_name', align:  'left', width: '180' 
					}, { 
						display:  '核算项2', isSort: false, hide: true, name:  'check2_name', align:  'left', width: '180' 
					}, {  
						display:  '核算项3', isSort: false, hide: true, name:  'check3_name', align:  'left', width: '180' 
					}, {  
						display:  '核算项4', isSort: false, hide: true, name:  'check4_name', align:  'left', width: '180' 
					}, 
					{
						display: '年初余额', align: 'center', id: '01',
						columns: [
							{ display: '借方', isSort: false, name: 'nc_d', align: 'right', id: 'nc_d', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.nc_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'nc_c', align: 'right', id: 'nc_c', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.nc_c, 2, 1); } } }
						]
					},

					{
						display: '期初余额', align: 'center', id: '02',
						columns: [
							{ display: '借方', isSort: false, name: 'qc_d', align: 'right', id: 'qc_d', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.qc_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'qc_c', align: 'right', id: 'qc_c', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.qc_c, 2, 1); } } }
						]
					},

					{
						display: '本期发生', align: 'center', id: '03',
						columns: [
							{ display: '借方', isSort: false, name: 'bq_d', align: 'right', id: 'bq_d', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.bq_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'bq_c', align: 'right', id: 'bq_c', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.bq_c, 2, 1); } } }
						]
					},

					{
						display: '累计发生', align: 'center', id: '04',
						columns: [
							{ display: '借方', isSort: false, name: 'sum_d', align: 'right', id: 'sum_d', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.sum_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'sum_c', align: 'right', id: 'sum_c', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.sum_c, 2, 1); } } }
						]
					},

					{
						display: '期末余额', align: 'center', id: '05',
						columns: [
							{ display: '借方', isSort: false, name: 'end_d', align: 'right', id: 'end_d', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.end_d, 2, 1); } } },
							{ display: '贷方', isSort: false, name: 'end_c', align: 'right', id: 'end_c', formatter: '###,##0.00', width: 90, render: function (rowdata, rowindex, value) { if (typeof (value) == 'undefined') { return ""; } else { return formatNumber(rowdata.end_c, 2, 1); } } }
						]
					}
				],
				dataAction: 'server', dataType: 'server', usePager: true, url: 'collectAccSubjZcheckEndOs.do',
				width: '100%', height: '98%', checkbox: false, rownumbers: true,
				delayLoad: true, pageSize: 100, pageSizeOptions:[100, 200, 500],
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
	    
	    //改变表格列头显示
	    function changeColumnName(){
	    	if(check_type1){
	    		grid.toggleCol("check1_name", true);
	    		grid.changeHeaderText("check1_name", check_item_type1.getText());
		    	if(check_type2){
		    		grid.toggleCol("check2_name", true);
		    		grid.changeHeaderText("check2_name", check_item_type2.getText());
		    	}else{
		    		grid.toggleCol("check2_name", false);
		    	}
		    	if(check_type3){
		    		grid.toggleCol("check3_name", true);
		    		grid.changeHeaderText("check3_name", check_item_type3.getText());
		    	}else{
		    		grid.toggleCol("check3_name", false);
		    	}
		    	if(check_type4){
		    		grid.toggleCol("check4_name", true);
		    		grid.changeHeaderText("check4_name", check_item_type4.getText());
		    	}else{
		    		grid.toggleCol("check4_name", false);
		    	}
	    	}else{
	    		if(sch_select.getValue()){
	    			ajaxJsonObjectByUrl("../accbooksch/queryAccBookSchCheckNameBySchId.do?isCheck=false", {sch_id: sch_select.getValue()}, function (res) {
	    				if(res.Rows.length>0){
	    					if(res.Rows.length==1){
	    			    		grid.toggleCol("check1_name", true);
	    			    		grid.changeHeaderText("check1_name", res.Rows[0].CHECK_TYPE_NAME);
	    			    	}else{
	    			    		grid.toggleCol("check1_name", false);
	    			    	}
	    			    	if(res.Rows.length==2){
	    			    		grid.toggleCol("check2_name", true);
	    			    		grid.changeHeaderText("check2_name", res.Rows[1].CHECK_TYPE_NAME);
	    			    	}else{
	    			    		grid.toggleCol("check2_name", false);
	    			    	}
	    			    	if(res.Rows.length==3){
	    			    		grid.toggleCol("check3_name", true);
	    			    		grid.changeHeaderText("check3_name", res.Rows[2].CHECK_TYPE_NAME);
	    			    	}else{
	    			    		grid.toggleCol("check3_name", false);
	    			    	}
	    			    	if(res.Rows.length==4){
	    			    		grid.toggleCol("check4_name", true);
	    			    		grid.changeHeaderText("check4_name", res.Rows[3].CHECK_TYPE_NAME);
	    			    	}else{
	    			    		grid.toggleCol("check4_name", false);
	    			    	}
	    				}
	    			}, false); 
	    		}else{
	    			grid.toggleCol("check1_name", false);
	    		}
	    	}
	    }

		//打印回调方法
		function lodopPrint() {
			var accStr;
			if ($("#is_state").get(0).checked) {
				accStr = "包含未记账"

			} else {
				accStr = "不包含未记账"
			}
			var head = "<table class='head' width='100%'><tr><td>会计期间：" + liger.get("acc_year_month1").getText() + "至" + liger.get("acc_year_month2").getText() + "</td>";
			head = head + "<td align='right'>" + liger.get("cur_code").getText() + "</td></tr>";
			head = head + "<tr><td>科目：" + liger.get("subj_code").getText() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;核算类：" + liger.get("check_item_type").getText() + "</td>";
			head = head + "<td align='right'>" + accStr + "</td></tr></table>";
			grid.options.lodop.head = head;
		}

		function showWindow() {

			var check_type_id = liger.get("check_item_type").getValue();

			if (check_type_id == "") {

				$.ligerDialog.error('核算类为必填项,不能为空');

				return;

			}

			$.ligerDialog.open({
				url: 'accZCheckItemCheckPage.do?isCheck=false&check_type_id=' + check_type_id,
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
				url: '../bookselector/accBookSubjSelectorPage.do?isCheck=false&flag=' + $("#subj_flag").val() + '&sign=11',
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
		   			class_name: "com.chd.hrp.acc.service.books.auxiliaryaccount.AccZcheckAuxiliaryAccountService",
					method_name: "collectAccSubjZcheckEndOsPrint",
					bean_name: "accZcheckAuxiliaryAccountService",
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
		var acc_year_month, subj_select, cur_select, is_state_check, is_show_check,is_end_os, sch_select;
		var check_item_type1, check_item_code1_b, check_item_code1_e;
		var check_item_type2, check_item_code2_b, check_item_code2_e;
		var check_item_type3, check_item_code3_b, check_item_code3_e;
		var check_item_type4, check_item_code4_b, check_item_code4_e;
	    var check_type1, check_type2, check_type3, check_type4, type_count; 
		var if_is_state = true;
		var if_is_end = false;
		$(function () {
			init();
		});
 
		//构造页面控件
		function init() {
			
			loadHead();	//加载数据
			
			//会计期间
	     	acc_year_month = $("#acc_year_month").etDatepicker({
	     		 range: true,
	             view: "months",
	             minView: "months",
	             dateFormat: "yyyy.mm",
	             defaultDate: ['${yearMonth}', '${yearMonth}'],
	             onChange:function(fd, d, picker){
	            	 var minDate,maxDate;
	            	 if(!d){
	            		 minDate = null;
	            		 maxDate = null;
	            	 }else{
	            		 var selectYear = d[0].getFullYear();
	            		 minDate = new Date(selectYear + '-1-1');
	                     maxDate = new Date(selectYear + '-12-31');
	            	 }
	                 picker.update({
	                	 minDate,
	                	 maxDate
	                 })
	             },
	   		});
			
			//科目
			subj_select = $("#subj_code_select").etSelect({
				url: '../querySubjCode.do?isCheck=false',
				para: { is_last: 1, is_check: 1 },
				searchField:['text', 'search'],
				defaultValue: "none" 
			});

			//币种
			/* cur_select = $("#cur_code_select").etSelect({
				url: '../queryCur.do?isCheck=false'
			}); */
			
			//方案
			sch_select = $("#sch_select").etSelect({
				url: '../queryAccBookSch.do?isCheck=false',
				searchField:['text', 'search'],
			});

			//包含未记账
			/* is_state_check = $("#is_state_check").etCheck({
				ifChanged: function (status, checked, disabled) {
					CheckState("is_state_check", checked);
				}
			}); */
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

			//核算类1
			check_item_type1 = $("#check_item_type1").etSelect({
				url: "../queryCheckTypeBySubjCode.do?isCheck=false",
				defaultValue: "none",
				onChange: function (value) {
					loadItemCode("check_item_type1", value);
				}
			});

			//核算项1
			check_item_code1_b = $("#check_item_code1_b").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
			check_item_code1_e = $("#check_item_code1_e").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
		}
		
		//加载核算项
		function loadItemCode(check_item_type, item_type) { 
			switch(check_item_type){
				case "check_item_type1" : 
					check_item_code1_b.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					check_item_code1_e.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					break;
				case "check_item_type2" : 
					check_item_code2_b.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					check_item_code2_e.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					break;
				case "check_item_type3" : 
					check_item_code3_b.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					check_item_code3_e.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					break;
				case "check_item_type4" : 
					check_item_code4_b.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					check_item_code4_e.reload({
						url: "../queryCheckItemByTypeFy.do?isCheck=false",
						para: {
							check_type_id: item_type
						},
					});
					break;
				default:
					break;
			}
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
						grid.toggleCol('sum_d', true);
						grid.toggleCol('sum_c', true);
					} else {
						grid.toggleCol('04', false);
						grid.toggleCol('sum_d', false);
						grid.toggleCol('sum_c', false);
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


		function queryNew() {
			grid.options.parms = [];

			grid.options.newPage = 1;
			//根据表字段进行添加查询条件
	   		year_month1 = acc_year_month.getValue();
	       	if(year_month1==""){
	       		$.ligerDialog.error('起始年月为必填项');
	       		return ;
	       	};
	       	
	       	var sch_id = sch_select.getValue();
	       	check_type1 = check_item_type1.getValue();
	       	if(!sch_id && check_type1 =="" ){
	           	$.ligerDialog.error('核算类1为必填项');
	           	return ;
			}
	       	check_type2 = check_item_type2 ? check_item_type2.getValue() : "";
			if(type_count >= 2 && check_type2 =="" ){
				$.ligerDialog.error('核算类2为必填项');
				return ;
			}
	       	check_type3 = check_item_type3 ? check_item_type3.getValue() : "";
			if(type_count >= 3 && check_type3 =="" ){
				$.ligerDialog.error('核算类3为必填项');
				return ;
			}
	       	check_type4 = check_item_type4 ? check_item_type4.getValue() : "";
			if(type_count >= 4 && check_type4 =="" ){
				$.ligerDialog.error('核算类4为必填项');
				return ;
			}
			
			var check_item1_b = check_item_code1_b.getValue();
	   	    var check_item1_e = check_item_code1_e.getValue();

	   	 	var check_item2_b = check_item_code2_b ? check_item_code2_b.getValue() : "";
	   	 	var check_item2_e = check_item_code2_e ? check_item_code2_e.getValue() : "";
	   	 	
	   	 	var check_item3_b = check_item_code3_b ? check_item_code3_b.getValue() : "";
	   	 	var check_item3_e = check_item_code3_e ? check_item_code3_e.getValue() : "";
	   	 	
	   	 	var check_item4_b = check_item_code4_b ? check_item_code4_b.getValue() : "";
	   	 	var check_item4_e = check_item_code4_e ? check_item_code4_e.getValue() : "";
	   	    
	   	    var subj_code = subj_select.getValue();
	   	    
			grid.options.parms.push({name:'acc_year_b',value : year_month1[0].split(".")[0]}); 
	  		grid.options.parms.push({name:'acc_month_b',value : year_month1[0].split(".")[1]}); 
	  		grid.options.parms.push({name:'acc_year_e',value : year_month1[1].split(".")[0]}); 
	  		grid.options.parms.push({name:'acc_month_e',value : year_month1[1].split(".")[1]}); 
	  		grid.options.parms.push({name: 'sch_id', value:  sch_id}); 
	  		grid.options.parms.push({name: 'subj_code', value:  subj_code}); 
	  		grid.options.parms.push({name: 'check_item_type', value: check_type1}); 
	     	grid.options.parms.push({name: 'check_item_type2', value: check_type2}); 
	     	grid.options.parms.push({name: 'check_item_type3', value: check_type3}); 
	     	grid.options.parms.push({name: 'check_item_type4', value: check_type4}); 
	    	
	     	grid.options.parms.push({name: 'check_item_code1_b', value: check_item1_b}); 
	    	grid.options.parms.push({name: 'check_item_code1_e', value: check_item1_e}); 
	 		grid.options.parms.push({name: 'check_item_code2_b', value: check_item2_b}); 
	    	grid.options.parms.push({name: 'check_item_code2_e', value: check_item2_e});
	    	grid.options.parms.push({name: 'check_item_code3_b', value: check_item3_b}); 
	    	grid.options.parms.push({name: 'check_item_code3_e', value: check_item3_e}); 
	    	grid.options.parms.push({name: 'check_item_code4_b', value: check_item4_b}); 
	    	grid.options.parms.push({name: 'check_item_code4_e', value: check_item4_e});
	    	grid.options.parms.push({name: 'show_zero', value: if_is_end ? 1 : 0});

	       	//改变列头
	       	changeColumnName();

	    	//加载查询条件
	    	grid.loadData(grid.where);
		}
		
		//方案设置
		function subjIntercalate(){
			parent.$.ligerDialog.open({
				title :  '方案设置',
				width :  $(window).width()-500,
				height :  $(window).height()-100,
				url :  'hrp/acc/accbooksch/accBookSchMainPage.do?isCheck=false&is_check=1',
				modal :  true,
				showToggle :  false,
				showMax :  false,
				showMin :  false,
				isResize :  true,
				parentframename :  window.name,
				buttons :  [ {
					text :  '保存',
					onclick :  function(item, dialog) {
						dialog.frame.saveSch(0);
					},
					cls :  'l-dialog-btn-highlight'
				},{
					text :  '查询',
					onclick :  function(item, dialog) {
						dialog.frame.saveSch(1);
					},
					cls :  'l-dialog-btn-highlight'
				},{
					text :  '取消',
					onclick :  function(item, dialog) {
						dialog.close();
					}
				} ]
			});
	 	};
	 	
	 	//增加第二行
	 	function addSecond(){

			//核算类2
			check_item_type2 = $("#check_item_type2").etSelect({
				url: "../queryCheckTypeBySubjCode.do?isCheck=false",
				defaultValue: "none",
				onChange: function (value) {
					loadItemCode("check_item_type2", value);
				}
			});

			//核算项2
			check_item_code2_b = $("#check_item_code2_b").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
			check_item_code2_e = $("#check_item_code2_e").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
	 	};
	 	
	 	//增加第三行
	 	function addThird(){

			//核算类2
			check_item_type3 = $("#check_item_type3").etSelect({
				url: "../queryCheckTypeBySubjCode.do?isCheck=false",
				defaultValue: "none",
				onChange: function (value) {
					loadItemCode("check_item_type3", value);
				}
			});

			//核算项2
			check_item_code3_b = $("#check_item_code3_b").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
			check_item_code3_e = $("#check_item_code3_e").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
	 	};
	 	
	 	//增加第四行
	 	function addFourth(){

			//核算类2
			check_item_type4 = $("#check_item_type4").etSelect({
				url: "../queryCheckTypeBySubjCode.do?isCheck=false",
				defaultValue: "none",
				onChange: function (value) {
					loadItemCode("check_item_type4", value);
				}
			});

			//核算项2
			check_item_code4_b = $("#check_item_code4_b").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
			check_item_code4_e = $("#check_item_code4_e").etSelect({
				url: "../queryCheckItemByTypeFy.do?isCheck=false",
				defaultValue: "none",
				searchField:['text','search'],
			});
	 	};
	 	
	 	function addLine1(){
	 		$('#attend2').show();
	 		$('#add1').hide();
	 		addSecond();
	 		type_count = 2;
	 	};
	 	function addLine2(){
	 		$('#attend3').show();
	 		$('#add2').hide();
	 		$('#delete1').hide();
	 		addThird();
	 		type_count = 3;
	 	};
	 	function addLine3(){
	 		$('#attend4').show();
	 		$('#add3').hide();
	 		$('#delete2').hide();
	 		addFourth();
	 		type_count = 4;
	 	};
	 	function deleteLine1(){
	 		check_item_type2.clearItem();
	 		check_item_code2_b.clearItem();
	 		check_item_code2_e.clearItem(); 
	 		
	 		$('#attend2').hide();
	 		$('#add1').show();
	 		type_count = 1;
	 	};
	 	function deleteLine2(){
	 		check_item_type3.clearItem();
	 		check_item_code3_b.clearItem();
	 		check_item_code3_e.clearItem(); 
	 		
	 		$('#attend3').hide();
	 		$('#add2').show();
	 		$('#delete1').show();
	 		type_count = 2;
	 	};
	 	function deleteLine3(){
	 		check_item_type4.clearItem();
	 		check_item_code4_b.clearItem();
	 		check_item_code4_e.clearItem(); 
	 		
	 		$('#attend4').hide();
	 		$('#add3').show();
	 		$('#delete2').show();
	 		type_count = 3;
	 	};
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">

	<input type="hidden" id="subj_flag" name="subj_flag" />
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<div class="main" style="display:block;">
		<table class="table-layout" cellpadding="0" border=0 cellspacing="0" width="100%">
			<tr>
				<td class="no-empty label">会计期间：</td>
				<td class="ipt">
					<input id="acc_year_month" name="acc_year_month" style="width:180px" type="text">
				</td>
				<td class="label" style="padding-left: 35px">方案：</td>
				<td class="ipt" style="width: 230px;">
					<select name="" id="sch_select" style="width:180px"></select>
					<button onclick="subjIntercalate();">设置 </button>
				</td>
				<td class="label" style="width: 80px">科目：</td>
				<td class="ipt" style="width: 300px;">
					<select name="" id="subj_code_select" style="width:180px"></select>
					<input type="checkbox" name="" id="is_end_os">
					<label for="is_end_os">显示零余额</label>
					<!-- <input type="checkbox" name="" id="is_state_check" checked="checked">
					<label for="is_state_check">包含未记账</label> -->
				</td>
				<!-- <td class="label">币种：</td>
				<td class="ipt" style="width:100px">
					<select name="" id="cur_code_select" style="width:90px;"></select>
				</td> -->
			</tr>
			<tr>
	        	<td class="no-empty label">核算类1：</td>
	            <td class="ipt">
					<select name="" id="check_item_type1" style="width:180px"></select>
	            </td>
	            
	        	<td class="label" style="margin-left: 5px">核算项1：</td>
	        	<td class="ipt" colspan="3">
	            	<table>
						<tr>
							<td>
								<select name="" id="check_item_code1_b" style="width:180px"></select>
							</td >
							<td>&nbsp;至：</td>
							<td>
								<select name="" id="check_item_code1_e" style="width:180px"></select>
							</td>
							<td width="100px">
								<button id="add1" onclick="addLine1()">增加</button>
							</td>
	            		</tr>
					</table>
	            </td>
	        </tr>
	        
	        <tr id="attend2" style="display: none">
	        	<td class="no-empty label">核算类2：</td>
	            <td class="ipt">
	            	<select name="" id="check_item_type2" style="width:180px"></select>
	            </td>
	            
	        	<td class="label">核算项2：</td>
	        	<td class="ipt" colspan="3">
	        		<table>
						<tr>
							<td>
								<select name="" id="check_item_code2_b" style="width:180px"></select>
							</td>
							<td>&nbsp;至：</td>
							<td>
								<select name="" id="check_item_code2_e" style="width:180px"></select>
							</td>
							<td width="100px">
								<button id="add2" onclick="addLine2()">增加</button>
								<button id="delete1" onclick="deleteLine1()">移除</button>
							</td>
	            		</tr>
					</table>
				</td>
	        </tr>
	        
			<tr id="attend3" style="display: none">
	        	<td class="no-empty label">核算类3：</td>
	            <td class="ipt">
	            	<select name="" id="check_item_type3" style="width:180px"></select>
	            </td>
	            
	        	<td class="label">核算项3：</td>
	        	<td class="ipt" colspan="3">
		        	<table>
						<tr>
		        			<td>
		        				<select name="" id="check_item_code3_b" style="width:180px"></select>
		        			</td>
				        	<td>&nbsp;至：</td>
							<td>
				        		<select name="" id="check_item_code3_e" style="width:180px"></select>
				        	</td>
							<td>
								<button id="add3" onclick="addLine3()">增加</button>
								<button id="delete2" onclick="deleteLine2()">移除</button>
							</td>
		        		</tr>
					</table>
				</td>
	        </tr>
	        
	        <tr id="attend4" style="display: none">
	        	<td class="no-empty label">核算类4：</td>
	            <td class="ipt">
					<select name="" id="check_item_type4" style="width:180px"></select>
	            </td>
	            
	        	<td class="label">核算项4：</td>
	        	<td class="ipt" colspan="3">
	        		<table>
						<tr>
	        				<td>
	        					<select name="" id="check_item_code4_b" style="width:180px"></select>
	        				</td>
				        	<td>&nbsp;至：</td>
							<td>
				        		<select name="" id="check_item_code4_e" style="width:180px"></select>
				        	</td>
							<td>
								<button id="delete3" onclick="deleteLine3()">移除</button>
							</td>
	        			</tr>
					</table>
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