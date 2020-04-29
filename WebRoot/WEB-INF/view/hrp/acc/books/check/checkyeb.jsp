<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript" src="<%=path%>/lib/hrp/acc/accBookZcheck.js"></script>
<script type="text/javascript" src="<%=path%>/lib/hrp/acc/superReport/ktLayer.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>	

<style>
	.info {
		padding: 5px 0px;
		position:relative;
		/*height: 500px;*/
		display: none;/*避免闪动初始规定不显示*/
	}
</style>

	<script>
    var grid,acc_year_month1,acc_year_month2;
    var gridManager = null;
    var year_month1="";
    var is_check = 1;
    var is_zcheck = "${is_zcheck}";
    var check_type_id = "${check_type_id}";
    var check_type_name = "${check_type_name}";
    var page_name = "余额表";
    var template_code = "${template_code}"; 
    var method_name = "collectAccBooksCheckPrintYEB";
    var method_name_grid = "collectAccBooksCheckPrintGridYEB";
	var childObj = {};  //用于子页面调用
	var checkType = "${type}";
	
    $(function ()
    {
    	//loadForm();
		loadDict();
    	loadHead(null);	//加载数据
    	loadLayer();//加载浮动层
    	
    	not_group = ${accPara043} ? false : true; //结果集是否分组显示
    	
    	if(${copy_nature} == "02"){
    		$("#sub").hide();
    		$("#sub_inp").hide();
    	}
    	if($("#check_item_type1").val() == "" || $("#check_item_type1").val() == null){
			$("#item1").hide();
			$("#item1_inp").hide();
		}
    	
    	var curTemCode = template_code.substring(5,6)
    	if (curTemCode!=5){
    		$("#xianzhong_td").hide();
    		$("#xianzhong_tdItem").hide();
    		$("#jigou_td").hide();
    		$("#jigou_tdItem").hide();
    		$("#jiesuan_td").hide();
    		$("#jiesuan_tdItem").hide();
    	}
    });
  
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '科目', isSort:false, hide: true, name: 'subj_name', align: 'left',width: '200', frozen: false
			}, { 
				display: '核算项1', isSort: false, hide: true, name:  'check1_name', align:  'left', width: '250'
			}, { 
				display: '核算项2', isSort: false, hide: true, name:  'check2_name', align:  'left', width: '250'
			}, { 
				display: '核算项3', isSort: false, hide: true, name:  'check3_name', align:  'left', width: '250'
			}, { 
				display: '核算项4', isSort: false, hide: true, name:  'check4_name', align:  'left', width: '250', frozen: false 
			}, {
				display: '年初余额', align: 'center', id: '01',
				columns: [ { 
					display: '借方', isSort: false, name: 'nc_d', id: 'nc_d', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				}, { 
					display: '贷方', isSort: false, name: 'nc_c', id: 'nc_c', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				} ]
			}, {
				display: '期初余额', align: 'center', id: '02',
				columns: [ { 
					display: '借方', isSort: false, name: 'qc_d', id: 'qc_d', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				}, { 
					display: '贷方', isSort: false, name: 'qc_c', id: 'qc_c', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				} ]
			}, {
				display: '本期发生', align: 'center', id: '03',
				columns: [ { 
					display: '借方', isSort: false, name: 'bq_d', id: 'bq_d', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				}, { 
					display: '贷方', isSort: false, name: 'bq_c', id: 'bq_c', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				} ]
			}, {
				display: '累计发生', align: 'center', id: '04',  
				columns: [ { 
					display: '借方', isSort: false, name: 'sum_d', id: 'sum_d', align: 'right', width: '120',  
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				}, { 
					display: '贷方', isSort: false, name: 'sum_c', id: 'sum_c', align: 'right', width: '120',  
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				} ]
			}, {
				display: '期末余额', align: 'center', id: '05',
				columns: [ { 
					display: '借方', isSort: false, name: 'end_d', id: 'end_d', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				}, { 
					display: '贷方', isSort: false, name: 'end_c', id: 'end_c', align: 'right', width: '120', 
					render: function (rowdata, rowindex, value) { 
						if (typeof (value) == 'undefined') { 
							return ""; 
						} else { 
							return formatNumber(value, 2, 1); 
						} 
					} 
				} ]
			} ],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryAccBooksCheckYEB.do?isCheck=false',
			width: '100%', height: '98%', checkbox: false, rownumbers: true,
			delayLoad: true, pageSize: 100, pageSizeOptions: [100, 200, 500],  
			selectRowButtonOnly: true,
			toolbar: { items: [
				{ text: '查询', id:'search', click: query,icon:'search' },
				{ line:true } ,
				{ text: '模板设置', id:'printSet', click: printSet,icon:'settings' },
				{ line:true }, 
				{ text: '模板打印', id:'printData', click: printData,icon:'print' },
				{ line:true }, 
				{ text: '列表打印', id:'printGrid', click: printGrid,icon:'print' },
				{ line:true }  
			] } , 
			contextmenuEidtor: { items: [
				{id: 'showMx', text: "明细", icon: "search", line: true, click: showMx}
			] }, 
		});
		
		//默认隐藏累计发生
		//grid.toggleCol('04', false);
		//grid.toggleCol('sum_d', false);
		//grid.toggleCol('sum_c', false);
		
		gridManager = $("#maingrid").ligerGetGridManager(); 
	};
    
    //主页面条件
    function loadDict(){
		autocomplete("#guanlianfang_code","../../queryAccYewuDict.do?isCheck=false&table_code=01001", "id", "text",true, true);
		autocomplete("#jigou_code","../../queryAccYewuDict.do?isCheck=false&table_code=01002", "id", "text",true, true);
		autocomplete("#xianzhong_code","../../queryAccYewuDict.do?isCheck=false&table_code=01003", "id", "text",true, true);
		autocomplete("#jiesuan_code","../../queryAccYewuDict.do?isCheck=false&table_code=01004", "id", "text",true, true);
    	//会计期间
		acc_year_month1 = $("#acc_year_month1").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});
		acc_year_month2 = $("#acc_year_month2").etDatepicker({
			range: false,
			view: "months",
			minView: "months",
			dateFormat: "yyyy.mm",
			defaultDate: ['${yearMonth}']
		});
		
    	autocompleteObj({
			id:  '#sch_id', 
			urlStr: 	"../../queryAccBookSch.do?isCheck=false&is_check="+is_check+"&page="+'${template_code}', 
			valueField:  'id', 
			textField:    'text' , 
			autocomplete: true, 
			highLight: true,
			parmsStr: "",
			defaultSelect:  true, 
			boxwidth: subjWidth, 
			selectEvent: function(value){
				if(liger.get("begin_subj_code")){
					liger.get("begin_subj_code").clear();
					liger.get("begin_subj_code").set("parms", {sch_id: value});
					liger.get("begin_subj_code").reload();
				}
				if(liger.get("end_subj_code")){
					liger.get("end_subj_code").clear();
					liger.get("end_subj_code").set("parms", {sch_id: value});
					liger.get("end_subj_code").reload();
				}
			}
		});
    	
    	$("#only_last").ligerCheckBox();
    	$("#show_zero").ligerCheckBox();
    	$("input[name='is_show']").ligerCheckBox();
    	//绑定事件
    	$("input[name='is_show']").change( function() {
    		CheckState($(this).attr("id"));
    	})
    }
    
    function loadForm(){
		$("form").ligerForm();
   }; 
   
   function onCheckTypeChange(){
    	para="";//清空参数
		para = {
			check_type_id :  liger.get("check_item_type1").getValue()
		};
    	
		$("#check_item_code1_b").val('');
		$("#check_item_code1_e").val('');
		//方案
		autocomplete("#check_item_code1_b",
				"../../queryCheckItem.do?isCheck=false", "id", "text",
				true, true, para);
		$("#subj_code").val('');
		$("#subj_code").ligerComboBox({
			parms: para,
	      	url:  '../../querySubj.do?isCheck=false',
	      	valueField:  'id',
	       	textField:  'text', 
	       	selectBoxWidth:  160,
	      	autocomplete:  true,
	      	width:  160,
	      	
		 });
    };

	//选中状态
	function CheckState(check_id) {
		var state = $("#"+check_id).prop("checked");
		switch (check_id) {
			case "show_nc":
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
			case "show_qc":
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
			case "show_bq":
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
			case "show_sum":
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
			case "show_end":
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
	
	//显示明细
	function showMx(ui, event){
		childObj = {
			year_month1: year_month1, 
			year_month2: year_month2, 
			subj_code: ui.subj_code, 
			check_type1: yebObj.check_type1, 
			check_type_name1: yebObj.check_type_name1, 
			check_item1: ui.check1_name.split(" ")[0], 
			check_type2: yebObj.check_type2, 
			check_type_name2: yebObj.check_type_name2, 
			check_item2: ui.check2_name ? ui.check2_name.split(" ")[0] : '', 
			check_type3: yebObj.check_type3, 
			check_type_name3: yebObj.check_type_name3, 
			check_item3: ui.check3_name ? ui.check3_name.split(" ")[0] : '', 
			check_type4: yebObj.check_type4, 
			check_type_name4: yebObj.check_type_name4, 
			check_item4: ui.check4_name ? ui.check4_name.split(" ")[0] : '' 
		}; 

		parent.$.ligerDialog.open({
			title: '余额表明细',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/acc/books/check/checkyebmxzPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
  	
   //打印数据
	function printDate(){ 
		
	   if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		};
		var heads={
			//"isAuto": true/false 默认true，页眉右上角默认显示页码
			"rows": [
          		{"cell":0,"value": "会计期间："+year_month1[0]+"至"+year_month1[1],"colSpan": "4"} ,
	          	{"cell": 4,"value": "科目："+liger.get("subj_code").getText(),"from": "right","align": "right","colSpan": "5"}
			]
    	}; 
		 
   		var printPara={
   			title: "核算项科目总账",//标题
   			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
   			class_name: "com.chd.hrp.acc.service.books.auxiliaryaccount.AccZcheckAuxiliaryAccountService",
			method_name: "collectAccZcheckGeneralLedgerPrint",
			bean_name: "accZcheckAuxiliaryAccountService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空

			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
   		};
	   		
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
	};
 	
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">

	<div id="pageloading" class="l-loading" style="display:none"></div>

	<div id="toptoolbar"></div>
	<div id="queryTab" >
	    <table cellpadding="0" cellspacing="0" class="l-table-edit"  width="auto">
	    	<tr> 
	            <td align="right" class="l-table-edit-td" width="70px;"><font size="2" color="red">*</font>会计期间：</td>
	            <td><table><tr>
					<td align="left" class="l-table-edit-td">
						<input class="Wdate" name="acc_year_month1" type="text" 
							id="acc_year_month1" ltype="text" style="width: 160px;" />
					</td>
					<td align="left" class="l-table-edit-td">至</td>
					<td align="left" class="l-table-edit-td">
						<input class="Wdate" name="acc_year_month2" type="text" 
							id="acc_year_month2" ltype="text" style="width: 160px;" />
					</td>
					<td align="left"></td>
				</tr></table></td>
	            <td align="right" class="l-table-edit-td" width="70px;">方案：</td> 
	            <td align="left" class="l-table-edit-td">
	            	<table>
						<tr>
	            			<td><input name="sch_id" type="text" id="sch_id" ltype="text" /></td>
	            			<td align="left" class="l-table-edit-td"><input class="l-button l-button-test"  type="button" value="设置" onclick="subjIntercalate();"/></td>
						</tr>
					</table>
				</td>
				
	            <td align="right" class="l-table-edit-td" width="70px;"></td> 
	        	<td align="left" class="l-table-edit-td" >
	        		<input type="checkbox" id="only_last"/>只显示末级科目
	        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            	<input type="checkbox" id="show_zero"/>显示零余额
	            </td>
			</tr>
			<tr>
 	            <td align="right" class="l-table-edit-td" width="70px;">
 	            	<font size="2" color="red">*</font>显示项目：
 	            </td>
	            <td align="left" class="l-table-edit-td" colspan="3">
	            	<input name="is_show" type="checkbox" id="show_nc" checked/>年初余额
	            	&nbsp;&nbsp;
	            	<input name="is_show" type="checkbox" id="show_qc" checked/>期初余额
	            	&nbsp;&nbsp;
	            	<input name="is_show" type="checkbox" id="show_bq" checked/>本期发生
	            	&nbsp;&nbsp;
	            	<input name="is_show" type="checkbox" id="show_sum"/>累计发生
	            	&nbsp;&nbsp;
	            	<input name="is_show" type="checkbox" id="show_end" checked/> 期末余额
	            </td>
			</tr>
		</table>
	</div>
	<div id="floatDiv" style="height:0px;">
		<!-- <div id="floatQuery" class="info"> -->
		<div id="floatQuery" >
    		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				<tr>
							            
		        	<td align="right" class="l-table-edit-td" width="70px;">科目范围：</td>
		        	<td align="left" class="l-table-edit-td">
		            	<table>
							<tr>
								<td><input id="begin_subj_code" name="begin_subj_code"  /></td>
								<td align="left" class="l-table-edit-td" >至</td>
								<td align="left" class="l-table-edit-td"><input id="end_subj_code" name="end_subj_code" /></td>
		            		</tr>
						</table>
		            </td>
		            
		            <td id="sub" align="right" class="l-table-edit-td" width="70px;">科目属性：</td>
		            <td id="sub_inp" align="left" class="l-table-edit-td">
		            	<input id="subj_kind" name="subj_kind" />
		            </td>
		            
		            <td align="right" class="l-table-edit-td" width="70px;">统计方式：</td>
		            <td align="left" class="l-table-edit-td" id="show_by">
						<input type="radio" value="1" name="show_by" />按科目
						&nbsp;&nbsp;
						<input type="radio" value="2" name="show_by" checked/>按核算项
						&nbsp;&nbsp;
						<input type="radio" value="3" name="show_by"/>全部
					</td>
					
		        </tr>
		         
		        <tr>
		        	<td id="item1" align="right" class="l-table-edit-td check_type1">
		        		<font size="2" color="red">*</font>核算类1：
		        	</td>
		            <td id="item1_inp" align="left" class="l-table-edit-td check_type1">
		            	<input id="check_item_type1" name="check_item_type1" />
		            </td>
		            
		        	<td align="right" class="l-table-edit-td"><span id="check_name1" >核算项1：</span></td>
		        	<td align="left" class="l-table-edit-td">
		            	<table>
							<tr>
								<td><input id="check_item_code1_b" name="check_item_code1_b"  /></td>
								<td align="left" class="l-table-edit-td" >至</td>
								<td align="left" class="l-table-edit-td" ><input id="check_item_code1_e" name="check_item_code1_e" /></td>
								
								<td align="right" class="l-table-edit-td proj_type" style="display:none; width:70px;">项目分类：</td>
					            <td align="left" class="l-table-edit-td proj_type" style="display:none;" >
					            	<input name="proj_type" id="proj_type" />
					            </td>
		            		</tr>
						</table>
		            </td>
					            
					<td align="left" class="l-table-edit-td" id="add1" colspan="2">
						<input class="l-button l-button-test"  type="button" value="增加" onclick="addLine1()"/>
					</td>
					
					<td id="xianzhong_td" align="right" class="l-table-edit-td" >险种</td>
					<td id="xianzhong_tdItem" align="left" class="l-table-edit-td"> <input type="text" id="xianzhong_code"/> </td>

		        </tr>

				<tr>
					<td id="jiesuan_td" align="right" class="l-table-edit-td" width="70px;">结算方式</td>
		        	<td id="jiesuan_tdItem" align="left" class="l-table-edit-td">
		            	<table>
							<tr>
								<td><input type="text" id="jiesuan_code"/></td>
								<td align="left" class="l-table-edit-td">关联方</td>
								<td align="left" class="l-table-edit-td"><input  id="guanlianfang_code"/></td>
		            		</tr>
						</table>
		            </td>
		        
					<td id="jigou_td" align="right" class="l-table-edit-td" >机构</td>
					<td id="jigou_tdItem" align="left" class="l-table-edit-td" > <input  id="jigou_code"/> </td>
		        </tr>
		        
		        <tr id="attend2" style="display: none">
		        	<td align="right" class="l-table-edit-td" >
		        		<font size="2" color="red">*</font>核算类2：
		        	</td>
		            <td align="left" class="l-table-edit-td"  >
		            	<input id="check_item_type2" name="check_item_type2" />
		            </td>
		            
		        	<td align="right" class="l-table-edit-td">核算项2：</td>
		        	<td align="left" class="l-table-edit-td">
		        		<table>
							<tr>
								<td><input id="check_item_code2_b" name="check_item_code2_b"  /></td>
								<td align="left" class="l-table-edit-td">至</td>
								<td align="left" class="l-table-edit-td"><input id="check_item_code2_e" name="check_item_code2_e" /></td>
		            		</tr>
						</table>
					</td>
					
					<td align="left" class="l-table-edit-td" id="add2" colspan="2">
						<input class="l-button l-button-test"  type="button" value="增加" onclick="addLine2()"/>
						<input class="l-button l-button-test"  type="button" value="移除" onclick="deleteLine1()"/>
					</td>
		        </tr>
		        
				<tr id="attend3" style="display: none">
		        	<td align="right" class="l-table-edit-td" >
		        		<font size="2" color="red">*</font>核算类3：
		        	</td>
		            <td align="left" class="l-table-edit-td"><input id="check_item_type3" name="check_item_type3" /></td>
		            
		        	<td align="right" class="l-table-edit-td">核算项3：</td>
		        	<td align="left" class="l-table-edit-td">
			        	<table>
							<tr>
			        			<td><input id="check_item_code3_b" name="check_item_code3_b"  /></td>
					        	<td align="left" class="l-table-edit-td">至</td>
					        	<td align="left" class="l-table-edit-td"><input id="check_item_code3_e" name="check_item_code3_e" /></td>
			        		</tr>
						</table>
					</td>
					
					<td align="left" class="l-table-edit-td"  id="add3" colspan="2">
						<input class="l-button l-button-test"  type="button" value="增加" onclick="addLine3()"/>
						<input class="l-button l-button-test"  type="button" value="移除" onclick="deleteLine2()"/>
					</td>
		        </tr>
		        
		        <tr id="attend4" style="display: none">
		        	<td align="right" class="l-table-edit-td" >
		        		<font size="2" color="red">*</font>核算类4：
		        	</td>
		            <td align="left" class="l-table-edit-td">
		            	<input id="check_item_type4" name="check_item_type4" />
		            </td>
		            
		        	<td align="right" class="l-table-edit-td">核算项4：</td>
		        	<td align="left" class="l-table-edit-td">
		        		<table>
							<tr> 
		        				<td><input id="check_item_code4_b" name="check_item_code4_b"  /></td>
					        	<td align="left" class="l-table-edit-td">至</td>
					        	<td align="left" class="l-table-edit-td"><input id="check_item_code4_e" name="check_item_code4_e" /></td>
		        			</tr>
						</table>
					</td>
					
					<td align="left" class="l-table-edit-td" id="add4" colspan="2">
						<input class="l-button l-button-test"  type="button" value="移除" onclick="deleteLine3()"/>
					</td>
		        </tr>
			</table>
		</div>
		<!-- <div id="queryTabAX" >
		    <table cellpadding="0" cellspacing="0" class="l-table-edit"  >
		    	<tr>
					
					
				</tr>
			</table>
		</div> -->
	</div>
	<div id="maingrid"></div>
</body>
</html>
