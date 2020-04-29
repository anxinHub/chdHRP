<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,ligerUI,grid,pageOffice" name="plugins" />   
</jsp:include>
<style>
.togglebtn {
    position: absolute;
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
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var columnsData;
	var queData;
	var dataFrom;
	
	$(function() {
		//加载数据
		dataFrom = $("#divTables").ligerForm({width:'100%',space:10,labelWidth:100,labelAlign:'right',align:'left'});
		loadHead(null);
		$("#ass_nature").ligerComboBox({
			url : '../queryAssNaturs.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 150,
			autocomplete : true,
			width : 150,
			async: false,
			onSuccess: function (data) {
				if (data.length > 0) {
					if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
							this.setValue(data[0].id);
					}
				}
			},
			onSelected : function(value, text) {
				f_setColumns();
				loadLigerForm();
				query();
			}
		});
	});
	//查询
	function query() {
		//根据表字段进行添加查询条件
		if (liger.get("ass_nature").getValue() == "") {
			$.ligerDialog.error('请选择资产性质');
			return;
		}
		var parms = [];
		var data = dataFrom.getData();
		$.each(queData.Rows,function(i,v){
			if(v.is_view == 1){
				if(v.is_change == 1){
					if(v.col_code.toLowerCase().split("_")[0] == "proc"){//采购仓库单独处理
						parms.push({name : (v.col_code.split("_")[0] + "_" + v.col_code.split("_")[1] + "_id").toLowerCase(),value : data[v.col_code].split("@")[0]});
						parms.push({name : (v.col_code.split("_")[0] + "_" + v.col_code.split("_")[1] + "_no").toLowerCase(),value : data[v.col_code].split("@")[1]});
					}else{
						parms.push({name : (v.col_code.split("_")[0]+ "_id").toLowerCase(),value : data[v.col_code].split("@")[0]});
						parms.push({name : (v.col_code.split("_")[0]+ "_no").toLowerCase(),value : data[v.col_code].split("@")[1]});
					}
				}else{
					parms.push({name : v.col_code.toLowerCase(),value : data[v.col_code]});
				}
				if(v.is_section == 1){
					if(v.type_code == 2){
						var key_beg = v.col_code+ "_BEG";
						var key_end = v.col_code+ "_END";
						parms.push({name : (v.col_code+ "_BEG").toLowerCase(),value : formatDate($("[name='"+key_beg+"']").val())});
						parms.push({name : (v.col_code+ "_END").toLowerCase(),value : formatDate($("[name='"+key_end+"']").val())});
					}else{
						parms.push({name : (v.col_code+ "_BEG").toLowerCase(),value : data[v.col_code+ "_BEG"]});
						parms.push({name : (v.col_code+ "_END").toLowerCase(),value : data[v.col_code+ "_END"]});
					}
				}else{
					parms.push({name : v.col_code.toLowerCase(),value : data[v.col_code]});
				}
				
			}
	 	});
		parms.push({
			name : 'ass_nature',
			value : liger.get("ass_nature").getValue()
		});
		grid.loadData(parms,'queryAssCardInit.do');
		
	}

	function loadHead() {

		grid = $("#maingrid").etGrid(
				{
					columns:[],
					usePager : true,
					width : '100%',
					height : '100%',
					checkbox : true,
					dataModel: {     // 加载数据模块
	                    location: "remote",  // 若是后台数据 传入URL值 同时location 为 remote
						url:'',
	                },
	                pageModel:{
	                	type:'remote'
	                },
					toolbar : {
						items : [ {
							label : '查询',
							type: "button",
							id : 'search',
							listeners:[{
								click : query
							}],
							icon : 'search'
						},{
							label : '添加',
							type: "button",
							id : 'add',
							icon : 'add',
							listeners:[{
								click : open_add
							}]
						},{
							label : '删除',
							type: "button",
							id : 'delete',
							icon : 'delete',
							listeners:[{
								click : remove
							}]
						},{
							label : '删除全部',
							type: "button",
							id : 'deleteAll',
							icon : 'delete',
							listeners:[{
								click : removeAll
							}]
						},{
							label : '打印',
							type: "button",
							id : 'print',
							icon : 'print',
							listeners:[{
								click : printDate
							}]
						},{
							label : '导入',
							type: "button",
							id : 'import',
							icon : 'up',
							listeners:[{
								click : importData
							}]
						},{
							label : '期初建账',
							type: "button",
							id : 'initAccount',
							icon : 'uploadzip',
							listeners:[{
								click : initAccount
							}]
						}
						/**, {
							line : true
						}, {
							text : '条码打印（<u>K</u>）',
							id : 'printBarcode',
							click : printBarcode,
							icon : 'print'
						}*/]
					},
					rowDblClick:function (ui){
						 var rowdata = ui.rowData;
						 openUpdate(rowdata.group_id , rowdata.hos_id,rowdata.copy_code ,rowdata.ass_card_no);
					}
				});

	} 
	function f_setColumns() {
		var ass_nature = $("#ass_nature").val() == null || $("#ass_nature").val() == "" ? "01":liger.get("ass_nature").getValue();
		$.post("getAssCardInitGridTitle.do?isCheck=false", {
			"ass_nature" : ass_nature
		}, function(data) {
			var strCol = [];
			$.each(data.Rows, function(i, v) {
				if(v.is_init_tab_view == 1){
					if (v.type_code == "1") {
						var name = v.col_code.toLowerCase()+"_name";
						strCol.push({ display: v.col_name.replaceAll("　",""), name: name, align: v.text_align,width:v.column_width});
					} else if (v.type_code == "0"){
						if(v.col_code == "ASS_CARD_NO"){
							strCol.push({ 
									display: v.col_name.replaceAll("　",""), 
									name:v.col_code.toLowerCase(),
									align:v.text_align,
									width:v.column_width,
									render : function(ui){
										var rowdata = ui.rowData;
										if(rowdata.ass_card_no == '合计'){
											return "合计"
										}else{
											return '<a href=javascript:openUpdate('+rowdata.group_id + ',' + rowdata.hos_id+ ',' + rowdata.copy_code + ',\"'+ rowdata.ass_card_no +'\");>'+rowdata.ass_card_no+'</a>';
										}
									}
							});
						}else{
							strCol.push({ display: v.col_name.replaceAll("　",""), name: v.col_code.toLowerCase() , align: v.text_align,width:v.column_width});
						}
					} else if(v.type_code == "2"){
						strCol.push({ display: v.col_name.replaceAll("　",""), name: v.col_code.toLowerCase() , align:v.text_align,width:v.column_width});
					} else if(v.type_code == "3"){
						strCol.push({ display: v.col_name.replaceAll("　",""), name: v.col_code.toLowerCase() , align:'right',width:v.column_width,
							render: function(ui)
				            {
				                    return formatNumber(ui.cellData,'${ass_05005}',1);
				            }});
					}
				}
			});
			columnsData = data;
			grid.option('columns', strCol);
            grid.refresh();
		}, "json");

	}

	var groupicon = "<%=path%>/lib/ligerUI/skins/icons/communication.gif";
	function loadLigerForm(){
		var WorkWidth = $(window).width();
		var linCount =  parseInt(WorkWidth/260);
		var ass_nature = $("#ass_nature").val() == null || $("#ass_nature").val() == "" ? "01":liger.get("ass_nature").getValue();
		$.post("getAssCardInitQue.do?isCheck=false", {
			"ass_nature" : ass_nature
		}, function(data) {
			queData = data;
			var fields = [];
			var field_width = 0;
			$.each(data.Rows,function(i,v){
				if(v.is_view == 1){
					var newline = false;
					field_width = field_width + v.field_width;
					
					if(v.is_section == 1){
						field_width = field_width + v.field_width;
					}
					
					var witdth = 150;
					
					if(v.field_width == 1){
						witdth = 150;
					}
					if(v.field_width == 2){
						witdth = 150 * 2 + 110;				
					}
					if(v.field_width == 3){
						witdth = 150 * 3 + 220;
					}
					if(v.field_width == 4){
						witdth = 150 * 4 + 330;
					}
					
					if (field_width > linCount) {
						field_width = v.field_width;
						if(v.is_section == 1){
							field_width = field_width + v.field_width;
						}
						newline = true;
					}
					
					if(v.type_code == 0){
						if(v.is_section == 1){
							fields.push({ display: v.col_name, name: v.col_code+"_BEG", newline: newline, type: "text" ,width:witdth});
							fields.push({ display: '至', name: v.col_code+"_END", newline: false, type: "text" ,width:witdth});
						}else{
							fields.push({ display: v.col_name, name: v.col_code, newline: newline, type: "text" ,width:witdth});
						}
					}
					
					if(v.type_code == 1){
						fields.push({ display: v.col_name, name: v.col_code, newline: newline, type: "select",width:witdth,editor: {
	                        url: v.re_url,
	                        type : 'select',
	                        triggerToLoad:true,
	                        keySupport : true,
							autocomplete : true
	                    }
  						});
					}
					
					if(v.type_code == 2){
						if(v.is_section == 1){
							fields.push({ display: v.col_name, name: v.col_code+"_BEG", newline: newline, type: "date" ,width:witdth});
							fields.push({ display: '至', name: v.col_code+"_END", newline: false, type: "date" ,width:witdth});
						}else{
							fields.push({ display: v.col_name, name: v.col_code, newline: newline, type: "date" ,width:witdth});
						}
					}
					
					if(v.type_code == 3){
						if(v.is_section == 1){
							fields.push({ display: v.col_name, name: v.col_code+"_BEG", newline: newline, type: "number" ,width:witdth});
							fields.push({ display: '至', name: v.col_code+"_END", newline: false, type: "number" ,width:witdth});
						}else{
							fields.push({ display: v.col_name, name: v.col_code, newline: newline, type: "number" ,width:witdth});
						}
					}
				}
			});
			dataFrom.set('fields',fields);
			
			$.each(data.Rows,function(i,v){
				if(v.is_view == 1){
					
					if(v.type_code == 0){
						if(v.is_section == 1){
							if(v.is_default == 1){
								var key_beg = v.col_code.toString()+"_BEG";
								var key_end = v.col_code.toString()+"_END";
								$("[name='"+key_beg+"']").val(v.default_text);
								$("[name='"+key_end+"']").val(v.default_text);
							}
							
						}else{
							if(v.is_default == 1){
								var key = v.col_code.toString();
								$("[name='"+key+"']").val(v.default_text);
							}
						}
					}
					
					if(v.type_code == 1){
						if(v.is_default == 1){
							var key = v.col_code.toString();
							liger.get(key).setValue(v.default_value);
							liger.get(key).setText(v.default_text);
						}
					}
					
					if(v.type_code == 2){
						if(v.is_section == 1){
							if(v.is_default == 1){
								if(v.default_value == 'TODAY'){
									var key_beg = v.col_code.toString()+"_BEG";
									var key_end = v.col_code.toString()+"_END";
									$("[name='"+key_beg+"']").val(cardDefaultDate("YYYY-mm-dd","month_first"));
									$("[name='"+key_end+"']").val(cardDefaultDate("YYYY-mm-dd","month_last"));
								}
							}
						}else{
							if(v.is_default == 1){
								var key = v.col_code.toString();
								$("[name='"+key+"']").val(v.default_text);
							}
						}
					}
					
					if(v.type_code == 3){
						if(v.is_section == 1){
							if(v.is_default == 1){
								var key_beg = v.col_code.toString()+"_BEG";
								var key_end = v.col_code.toString()+"_END";
								$("[name='"+key_beg+"']").val("");
								$("[name='"+key_end+"']").val("");
							}
						}else{
							if(v.is_default == 1){
								var key = v.col_code.toString();
								$("[name='"+key+"']").val(v.default_text);
							}
						}
					}
				}
			});
			
		},"json");
		
	}
	
	function cardDefaultDate(dateFmt, flag) {
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
			$(id).val(addzero(d.getDate()));
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
	
	function formatDate(str){
		if(str == ""){
			return "";
		}
		var d = new Date(str);
		var dateStr = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
		if(dateStr == "1970-1-1"){
			return "";
		}
		return dateStr;
	}
	
	String.prototype.replaceAll  = function(s1,s2){   
	    return this.replace(new RegExp(s1,"gm"),s2);   
	} 
	
	function remove() {

		var data = grid.selectGetChecked(); 
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						var rowdata = this.rowData;
						ParamVo.push(rowdata.group_id + "@" + rowdata.hos_id + "@"
								+ rowdata.copy_code + "@" + rowdata.ass_card_no);
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssCardInit.do", {
						ParamVo : ParamVo.toString(),
						ass_nature : liger.get("ass_nature").getValue()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
	function removeAll() {
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAllAssCardInit.do?isCheck=false", {
						ass_nature : liger.get("ass_nature").getValue()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
	}
	
	function open_add(){
		var para = "ass_nature="+liger.get("ass_nature").getValue();
		parent.$.ligerDialog.open({
			title: '期初资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscardinit/assCardInitAddPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}

	function openUpdate(group_id,hos_id,copy_code,ass_card_no) {

		var parm = "group_id=" + group_id + "&" + "hos_id=" + hos_id + "&"
				+ " copy_code=" + copy_code + "&" + "ass_card_no=" + ass_card_no + "&"
				+ "ass_nature=" + liger.get("ass_nature").getValue();
		parent.$.ligerDialog.open({
			title: '期初资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscardinit/assCardInitUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});

	}
	
	//导入功能
	function importData(){
		$("#card_btn").ligerButton({width:100,click: importCardData});
		$("#resource_btn").ligerButton({width:100,click:importResourceData});
		$("#depre_btn").ligerButton({width:100,click: importDepreData});
		$("#manage_btn").ligerButton({width:100,click: importManageDepreData});
		$("#pay_btn").ligerButton({width:100,click: importPayData});
		
		$.ligerDialog.open({width:500, target: $("#impBox") });
	}
	
	//导入卡片数据
	function importCardData(){
		var para = {
				"column" : [/* {
					"name" : "ass_nature",
					"display" : "资产性质",
					"width" : "150",
					"require" : true
				},  */{
					"name" : "ass_ori_card_no",
					"display" : "原始卡片号",
					"width" : "200",
					"require" : true
				}, {
					"name" : "ass_code",
					"display" : "资产编码",
					"width" : "200",
					"require" : true
				/* }, {
					"name" : "ass_name",
					"display" : "资产名称",
					"width" : "200",
					"require" : true */
				}, {
					"name" : "ven_name",
					"display" : "供应商",
					"width" : "200"/* , 
					"require" : true */
				}, {
					"name" : "bus_type",
					"display" : "业务类型",
					"width" : "150",
					"require" : true
				} , {
					"name" : "ass_spec",
					"display" : "规格",
					"width" : "120"
				}, {
					"name" : "ass_mondl",
					"display" : "型号",
					"width" : "120"
				}, {
					"name" : "ass_brand",
					"display" : "品牌",
					"width" : "120"
				}, {
					"name" : "unit_code",
					"display" : "计量单位",
					"width" : "100"
				}, {
					"name" : "fac_name",
					"display" : "生产厂商",
					"width" : "200"
				}, {
					"name" : "ins_date",
					"display" : "安装日期",
					"width" : "150"
				}, {
					"name" : "ins_money",
					"display" : "安装费用",
					"width" : "120"
				}, {
					"name" : "accept_emp",
					"display" : "验收人",
					"width" : "120"
				}, {
					"name" : "accept_date",
					"display" : "验收日期",
					"width" : "150"
				}, {
					"name" : "ass_in_no",
					"display" : "入库单号",
					"width" : "200"
				}, {
					"name" : "in_date",
					"display" : "入库日期",
					"width" : "150"
				}, {
					"name" : "run_date",
					"display" : "投入使用日期",
					"width" : "150"
				/* }, {
					"name" : "dept_name",
					"display" : "管理部门",
					"width" : "200" */
				}, {
					"name" : "is_dept",
					"display" : "在用状态",
					"width" : "100",
					"require" : true
				}, {
					"name" : "store_name",
					"display" : "仓库名称",
					"width" : "200"
				}, {
					"name" : "use_dept",
					"display" : "使用科室",
					"width" : "200"
				}, {
					"name" : "ass_seq_no",
					"display" : "序列号",
					"width" : "200"
				}, {
					"name" : "ass_purpose",
					"display" : "资产用途",
					"width" : "200"
				}, {
					"name" : "location",
					"display" : "存放位置",
					"width" : "200"
				}, {
					"name" : "is_measure",
					"display" : "是否计量",
					"width" : "100"
				}, {
					"name" : "is_throw",
					"display" : "是否投放",
					"width" : "100"
				}, {
					"name" : "is_depr",
					"display" : "是否折旧",
					"width" : "100"
				/* }, {
					"name" : "depr_method",
					"display" : "折旧方法",
					"width" : "200" */
				}, {
					"name" : "acc_depre_amount",
					"display" : "折旧年限",
					"width" : "200"
				}, {
					"name" : "is_manage_depre",
					"display" : "是否分摊",
					"width" : "100"
				/* }, {
					"name" : "manage_depr_method",
					"display" : "分摊方法",
					"width" : "200" */
				}, {
					"name" : "manage_depre_amount",
					"display" : "分摊年限",
					"width" : "200"
				}, {
					"name" : "ass_amount",
					"display" : "数量",
					"width" : "100"
				}, {
					"name" : "price",
					"display" : "资产原值",
					"width" : "120",
					"require" : true
				}, {
					"name" : "depre_money",
					"display" : "累计折旧",
					"width" : "120",
					"require" : true
				}, {
					"name" : "manage_depre_money",
					"display" : "累计分摊",
					"width" : "120"
				}, {
					"name" : "fore_money",
					"display" : "预留残值",
					"width" : "120"
				}, {
					"name" : "cur_money",
					"display" : "资产净值",
					"width" : "120",
					"require" : true
				}, {
					"name" : "add_depre_month",
					"display" : "累计折旧月份",
					"width" : "100"
				}, {
					"name" : "add_manage_month",
					"display" : "累计分摊月份",
					"width" : "100"
				}, {
					"name" : "service_date",
					"display" : "保修截止日期",
					"width" : "150"
				}, {
					"name" : "gb_code",
					"display" : "国标码",
					"width" : "100"
				}, {
					"name" : "use_state",
					"display" : "使用状态",
					"width" : "100"
				},{
					"name" : "source_name",
					"display" : "资金来源",
					"width" : "200"
				}, {
					"name" : "pact_code",
					"display" : "合同编号",
					"width" : "200"
				},{
					"name" : "bar_code",
					"display" : "条形码",
					"width" : "200"
				}, {
					"name" : "note",
					"display" : "备注",
					"width" : "200"
				}, {
					"name" : "cert_name",
					"display" : "权属证明",
					"width" : "200"
				} , {
					"name" : "cert_code",
					"display" : "权属证号",
					"width" : "200"
				} , {
					"name" : "cert_date",
					"display" : "发证日期",
					"width" : "200"
				} , {
					"name" : "prop_code",
					"display" : "产权形式",
					"width" : "200"
				} , {
					"name" : "struct_code",
					"display" : "建筑结构",
					"width" : "200"
				} , {
					"name" : "house_area",
					"display" : "建筑面积",
					"width" : "200"
				} , {
					"name" : "use_area",
					"display" : "使用面积",
					"width" : "200"
				} , {
					"name" : "base_area",
					"display" : "地下面积",
					"width" : "200"
				} , {
					"name" : "land_area",
					"display" : "占地面积",
					"width" : "200"
				} , {
					"name" : "land_no",
					"display" : "地号",
					"width" : "200"
				} , {
					"name" : "gain_date",
					"display" : "取得日期",
					"width" : "200"
				} , {
					"name" : "land_source_code",
					"display" : "土地来源",
					"width" : "200"
				} , {
					"name" : "book_amount",
					"display" : "图书数量",
					"width" : "200"
				} , {
					"name" : "relic_amount",
					"display" : "文物数量",
					"width" : "200"
				} , {
					"name" : "relic_grade_code",
					"display" : "文物等级",
					"width" : "200"
				} , {
					"name" : "car_place",
					"display" : "车辆产地",
					"width" : "200"
				} , {
					"name" : "car_sign",
					"display" : "车牌号",
					"width" : "200"
				} , {
					"name" : "car_frame",
					"display" : "车架号",
					"width" : "200"
				} , {
					"name" : "car_engine",
					"display" : "发动机号",
					"width" : "200"
				} , {
					"name" : "car_brand",
					"display" : "厂牌型号",
					"width" : "200"
				} , {
					"name" : "car_gas",
					"display" : "排气量",
					"width" : "200"
				} , {
					"name" : "man_code",
					"display" : "出厂编码",
					"width" : "200"
				}, {
					"name" : "proc_store_name",
					"display" : "采购仓库",
					"width" : "200",
					"require" : true
				}]
			};
			importSpreadView("hrp/ass/asscardinit/assAssCardInitImportPage.do?isCheck=false",para);
	}
	
	//导入资产来源
	
	function importResourceData(){
		var para = {
				"column" : [{
					"name" : "ass_ori_card_no",
					"display" : "原始卡片号",
					"width" : "200",
					"require" : true
				},{
					"name" : "source_id",
					"display" : "资金来源编码",
					"width" : "200",
					"require" : true
				},{
					"name" : "price",
					"display" : "卡片原值",
					"width" : "200",
					"require" : true
				},{
					"name" : "depre_money",
					"display" : "累计折旧",
					"width" : "200"
				},{
					"name" : "manage_depre_money",
					"display" : "累计分摊",
					"width" : "200"
				},{
					"name" : "cur_money",
					"display" : "卡片净值",
					"width" : "200"
				},{
					"name" : "fore_money",
					"display" : "卡片残值",
					"width" : "200"
				},{
					"name" : "pay_money",
					"display" : "已付金额",
					"width" : "200"
				},{
					"name" : "unpay_money",
					"display" : "未付金额",
					"width" : "200"
				}]
		};
		importSpreadView("hrp/ass/asscardinit/assAssResourceInitImportPage.do?isCheck=false",para);
	}
	
	//导入折旧记录
	function importDepreData(){
		var para = {
				"column" : [{
					"name" : "depre_no",
					"display" : "折旧序号",
					"width" : "200",
					"require" : true
				}, {
					"name" : "depre_year",
					"display" : "折旧年度",
					"width" : "200",
					"require" : true
				},{
					"name" : "depre_month",
					"display" : "折旧期间",
					"width" : "200",
					"require" : true
				},{
					"name" : "ass_card_no",
					"display" : "资产卡片号",
					"width" : "200",
					"require" : true
				},{
					"name" : "source_id",
					"display" : "资金来源",
					"width" : "200",
					"require" : true
				},{
					"name" : "use_dept",
					"display" : "使用科室",
					"width" : "200",
					"require" : true
				},{
					"name" : "use_percent",
					"display" : "分摊比例",
					"width" : "200",
					"require" : true
				},{
					"name" : "prim_money",
					"display" : "原值",
					"width" : "200",
					"require" : true
				},{
					"name" : "now_depre_amount",
					"display" : "本期折旧",
					"width" : "200",
					"require" : true
				},{
					"name" : "add_depre_amount",
					"display" : "累计折旧",
					"width" : "200",
					"require" : true
				},{
					"name" : "add_depre_month",
					"display" : "累计折旧月份",
					"width" : "200",
					"require" : true
				},{
					"name" : "cur_money",
					"display" : "资产净值",
					"width" : "200",
					"require" : true
				},{
					"name" : "fore_money",
					"display" : "预留残值",
					"width" : "200",
					"require" : true
				},{
					"name" : "operator",
					"display" : "操作员ID",
					"width" : "200",
					"require" : true
				},{
					"name" : "deal_date",
					"display" : "处理日期",
					"width" : "200",
					"require" : true
				},{
					"name" : "equi_depre_code",
					"display" : "使用折旧方法",
					"width" : "200",
					"require" : true
				},{
					"name" : "note",
					"display" : "摘要",
					"width" : "200"
				}]
		};
		importSpreadView("hrp/ass/asscardinit/assAssDepreInitImportPage.do?isCheck=false",para);
		
	}
	
	//导入分摊记录
	function importManageDepreData(){
		var para = {
				"column" : [{
					"name" : "depre_no",
					"display" : "分摊序号",
					"width" : "200",
					"require" : true
				}, {
					"name" : "depre_year",
					"display" : "分摊年度",
					"width" : "200",
					"require" : true
				},{
					"name" : "depre_month",
					"display" : "分摊期间",
					"width" : "200",
					"require" : true
				},{
					"name" : "ass_card_no",
					"display" : "资产卡片号",
					"width" : "200",
					"require" : true
				},{
					"name" : "source_id",
					"display" : "资金来源",
					"width" : "200",
					"require" : true
				},{
					"name" : "use_dept",
					"display" : "使用科室",
					"width" : "200",
					"require" : true
				},{
					"name" : "use_percent",
					"display" : "分摊比例",
					"width" : "200",
					"require" : true
				},{
					"name" : "prim_money",
					"display" : "原值",
					"width" : "200",
					"require" : true
				},{
					"name" : "now_depre_amount",
					"display" : "本期分摊",
					"width" : "200",
					"require" : true
				},{
					"name" : "add_depre_amount",
					"display" : "累计分摊",
					"width" : "200",
					"require" : true
				},{
					"name" : "add_depre_month",
					"display" : "累计分摊月份",
					"width" : "200",
					"require" : true
				},{
					"name" : "cur_money",
					"display" : "资产净值",
					"width" : "200",
					"require" : true
				},{
					"name" : "fore_money",
					"display" : "预留残值",
					"width" : "200",
					"require" : true
				},{
					"name" : "operator",
					"display" : "操作员ID",
					"width" : "200",
					"require" : true
				},{
					"name" : "deal_date",
					"display" : "处理日期",
					"width" : "200",
					"require" : true
				},{
					"name" : "equi_depre_code",
					"display" : "使用分摊方法",
					"width" : "200",
					"require" : true
				},{
					"name" : "note",
					"display" : "摘要",
					"width" : "200"
				}]
		};
		importSpreadView("hrp/ass/asscardinit/assAssManageDepreInitImportPage.do?isCheck=false",para);
		
	}
	
	//导入分期付款
	function importPayData(){
		var para = {
				"column" : [{
					"name" : "ass_card_no",
					"display" : "卡片编号",
					"width" : "150",
					"require" : true
				}, {
					"name" : "pay_plan_date",
					"display" : "付款时间",
					"width" : "200",
					"require" : true
				}, {
					"name" : "pay_plan_money",
					"display" : "付款金额",
					"width" : "200",
					"require" : true
				}, {
					"name" : "pay_money",
					"display" : "已付金额",
					"width" : "200",
					"require" : true
				}, {
					"name" : "unpay_money",
					"display" : "未付金额",
					"width" : "200",
					"require" : true
				}, {
					"name" : "ven_code",
					"display" : "供应商",
					"width" : "200",
					"require" : true
				}, {
					"name" : "note",
					"display" : "备注",
					"width" : "200"
				}]
		};
		importSpreadView("hrp/ass/asscardinit/assAssPayInitImportPage.do?isCheck=false",para);
		
	}

	
	function dateText() {
		WdatePicker({
			isShowClear : true,
			readOnly : false,
			dateFmt : 'yyyy-MM-dd'
		});
	}
function printDate() {
		
		var ass_nature = liger.get("ass_nature").getValue();
		var class_name = "";
		var method_name = "queryPrint";
		var bean_name = "";
		if(ass_nature == "01"){
			class_name = "com.chd.hrp.ass.service.card.AssCardInitHouseService";
			bean_name = "assCardInitHouseService";
		}else if(ass_nature == "02"){
			class_name = "com.chd.hrp.ass.service.card.AssCardInitSpecialService";
			bean_name = "assCardInitSpecialService";
		}else if(ass_nature == "03"){
			class_name = "com.chd.hrp.ass.service.card.AssCardInitGeneralService";
			bean_name = "assCardInitGeneralService";
		}else if(ass_nature == "04"){
			class_name = "com.chd.hrp.ass.service.card.AssCardInitOtherService";
			bean_name = "assCardInitOtherService";
		}else if(ass_nature == "05"){
			class_name = "com.chd.hrp.ass.service.card.AssCardInitInassetsService";
			bean_name = "assCardInitInassetsService";
		}else if(ass_nature == "06"){
			class_name = "com.chd.hrp.ass.service.card.AssCardInitLandService";
			bean_name = "assCardInitLandService";
		}
		
		if (grid.getAllData().length == 0) {
			$.ligerDialog.error("请先查询数据！");
			return;
		}
 		
 		var time=new Date();
    	var date=$("#IN_DATE_BEG").val()+"至"+$("#IN_DATE_END").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"资产性质："},
    	          {"cell":1,"value":liger.get("ass_nature").getText().split(" ")[1]},
    	          {"cell":25,"value":"报表日期:"},
  				  {"cell":26,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":25,"value":"制表人:"},
    				{"cell":26,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "卡片打印",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: class_name,
 				method_name: method_name,
 				bean_name: bean_name,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.getUrlParms(), function(i, obj) {
 			printPara[obj.name] = obj.value;
		});
 		
 		officeGridPrint(printPara);
	}
	var show_id = 1;
	function showAndHide(){
		if(show_id == 1){
			$("#divTables").css("display",'none');
			$('.togglebtn').css('backgroundPositionY','-10px');
			show_id = 2;
		}
		else if(show_id == 2){
			$("#divTables").css("display",'block');
			$('.togglebtn').css('backgroundPositionY','0');
			show_id = 1;
			for(var i in dataFrom.editors){
				if(dataFrom.editors[i].editor.control == "ComboBox"){
					dataFrom.editors[i].control.valueField.hide()  
				}
			}
		}
		grid._onResize();
	}
	
	function initAccount(){
		$.ligerDialog.confirm('是否覆盖建账?', function(yes) {
			if (yes) {
				isfg = "1";
			}else{
				isfg = "0";
			}
			ajaxJsonObjectByUrl("assAssCardInitAccount.do", {isfg : isfg 
			}, function(responseData) {
				if (responseData.state == "true") {
					
				}
			});
		});
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<div class="l-table-edit" style="margin-left: 48px;margin-top: 5px;overflow:hidden;">
			<div style="float:left">资产性质：</div>
			<div style="float:left"><input style="float:left" name="ass_nature" type="text" id="ass_nature" /> </div>
			<div style="float:right"><div class="togglebtn" onclick="showAndHide()"></div></div>
	</div>
	<hr/>
	<div id="divTables"></div>
	<div id="maingrid"></div>
	
	<style>#impBox .l-button {float: left;margin-left:10px;margin-top:10px;}</style>
	<div id="impBox" style="width:500px; margin:3px; display:none;">
    	<div id="card_btn">导入卡片数据</div>
    	<div id="resource_btn">导入资金来源</div>
    	<div id="depre_btn">导入折旧记录</div>
    	<div id="manage_btn">导入分摊记录</div>
    	<div id="pay_btn">导入分期付款</div>

 	</div>
	
</body>
</html>
