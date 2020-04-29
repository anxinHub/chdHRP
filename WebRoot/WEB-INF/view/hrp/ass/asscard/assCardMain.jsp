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
    <jsp:param value="select,datepicker,dialog,ligerUI,grid,pageOffice,lodop" name="plugins" />
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
	//$.ajaxSetup({
		//async : false
	//});
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
				//console.log(12312312);
				f_setColumns();
				loadLigerForm();
				query();
			}
		});
		f_setColumns();
		loadLigerForm();
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
		grid.loadData(parms,'queryAssCard.do');
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
	                	type:'remote',
	                	rPP:200,
	                	rPPOptions:[10,50,100,200,500,1000,5000]
	                },
					toolbar : {
						items : [ {
							label : '查询',
							type: "button",
							id : 'search',
							icon :'search',
							listeners:[{
								click:query
							}],
						},{
							label : '添加',
							type: "button",
							id : 'add',
							icon : 'plus',
							listeners:[{
								click:open_add
							}],
						}, {
							label : '删除',
							type: "button",
							id : 'delete',
							icon : 'closethick',
							listeners:[{
								click:remove
							}],
						},  {
							label : '打印',
							type: "button",
							id : 'print',
							icon : 'print',
							listeners:[{
								click:printDate
							}],
						},
						{ 
							label: '模板设置', 
							type: "button",
							id:'printSet', 
							icon:'print', 
							listeners:[{
								click:printSet
							}],
						},  {
							label : '批量打印',
							type: "button",
							id : 'printList',
							icon : 'print',
							listeners:[{
								click:printList
							}],
						}, {
							label : '条码打印',
							type: "button",
							id : 'printCode',
							icon : 'print',
							listeners:[{
								click:printCode
							}],
						}, {
							label : '重置条码',
							type: "button",
							id : 'resetBarCode',
							icon : 'reset',
							listeners:[{
								click:resetBarCode
							}],
						}, {
							label : '批量修改',
							type: "button",
							id : 'batchUpdate',
							icon : 'reset',
							listeners:[{
								click:batchUpdate
							}],
						}
						
						]
					},
					 rowDblClick:function (ui){
						 var rowdata = ui.rowData;
						 openUpdate(rowdata.group_id , rowdata.hos_id,rowdata.copy_code ,rowdata.ass_card_no);
					 }
				});
	} 
	
	function batchUpdate(){
		if (liger.get("ass_nature").getValue() == "") {
			$.ligerDialog.error('请选择资产性质');
			return;
		}
		
		if(liger.get("ass_nature").getValue() == "01"){
			return;
		}
		
		if(liger.get("ass_nature").getValue() == "05"){
			return;
		}
		
		if(liger.get("ass_nature").getValue() == "06"){
			return;
		}
		var data = grid.selectGetChecked();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						var rowdata = this.rowData;
						ParamVo.push(rowdata.group_id + "@" + rowdata.hos_id + "@"
								+ rowdata.copy_code + "@" + rowdata.ass_card_no+ "@" + rowdata.ass_in_no);
					});
			
			parent.$.ligerDialog.open({
      			title: '卡片批量修改',
      			height : 400,
  				width : 1000,
      			url: 'hrp/ass/asscard/assCardUpdateBatchPage.do?isCheck=false&paramVo='+ParamVo.toString()+'&ass_nature='+liger.get("ass_nature").getValue(),
      			modal: false, showToggle: false, showMax: true, showMin: false, isResize: true,
      			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
      		}); 
		}
	}
	
	function resetBarCode(){
		ajaxJsonObjectByUrl("resetBarCode.do?isCheck=false",null,function (responseData){
    		if(responseData.state=="true"){
    			
    		}
    	});
	}
	
	function f_setColumns() {
		var ass_nature = $("#ass_nature").val() == null || $("#ass_nature").val() == "" ? "01":liger.get("ass_nature").getValue();
		$.post("getAssCardGridTitle.do?isCheck=false", {
			"ass_nature" : ass_nature
		}, function(data) {
			var strCol = [];
			$.each(data.Rows, function(i, v) {
				if(v.is_tab_view == 1){
					if (v.type_code == "1") {
						var name = v.col_code.toLowerCase()+"_name";
						strCol.push({ display: v.col_name.replaceAll("　",""), name: name, align:v.text_align,minWidth:v.column_width});
					} else if(v.type_code == "0"){
						if(v.col_code == "ASS_CARD_NO"){
							strCol.push({ 
									display: v.col_name.replaceAll("　",""), 
									name:v.col_code.toLowerCase(),
									align:v.text_align,
									minWidth:v.column_width,
									render : function(ui){
										var rowdata = ui.rowData;
										if(rowdata.ass_card_no == '合计'){
											return "合计"
										}else{
											return '<a href=javascript:openUpdate('+rowdata.group_id + ',' + rowdata.hos_id+ ',\"' + rowdata.copy_code + '\",\"'+ rowdata.ass_card_no +'\");>'+rowdata.ass_card_no+'</a>';
										}
									}
							});
						}else{
							strCol.push({ display: v.col_name.replaceAll("　",""), name: v.col_code.toLowerCase() , align:v.text_align,minWidth:v.column_width});
						}
					} else if(v.type_code == "2"){
						strCol.push({ display: v.col_name.replaceAll("　",""), name: v.col_code.toLowerCase() , align:v.text_align,minWidth:v.column_width});
					} else if(v.type_code == "3"){
						strCol.push({ display: v.col_name.replaceAll("　",""), name: v.col_code.toLowerCase() , align:'right',minWidth:v.column_width,
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
		var ass_nature = liger.get("ass_nature").getValue()||'01'; // $("#ass_nature").val() == null || $("#ass_nature").val() == "" ? "01":
		$.post("getAssCardQue.do?isCheck=false", {
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
	                        valueField: 'id',
	                		textField: 'text',
	                        triggerToLoad:true,
	                        keySupport : true,
							autocomplete : true,
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
			dataFrom = $("#divTables").ligerForm({width:'100%',space:10,labelWidth:100,labelAlign:'right',align:'left'});
			dataFrom.set('fields',fields);
			//console.log(dataFrom.editor[0]);
			
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
							//$('[ligeruiid="'+key+'"]').val(v.default_text);
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

		//var data = gridManager.getCheckedRows();
		 var data = grid.selectGetChecked(); 
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						var rowdata = this.rowData;
						ParamVo.push(rowdata.group_id + "@" + rowdata.hos_id + "@"
								+ rowdata.copy_code + "@" + rowdata.ass_card_no+ "@" + rowdata.ass_in_no);
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssCard.do", {
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
	
	function open_add(){
		var para = "ass_nature="+liger.get("ass_nature").getValue();
		parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assCardAddPage.do?isCheck=false&'+para,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}

	function openUpdate(group_id,hos_id,copy_code,ass_card_no) {

		var parm = "group_id=" + group_id + "&" + "hos_id=" + hos_id + "&"
				+ " copy_code=" + copy_code + "&" + "ass_card_no=" + ass_card_no + "&"
				+ "ass_nature=" + liger.get("ass_nature").getValue();
		parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}

	
	function dateText() {
		WdatePicker({
			isShowClear : true,
			readOnly : false,
			dateFmt : 'yyyy-MM-dd'
		});
	}
	
	function printSet(){
		
		var template_code = "";
		var ass_para_map = "";
		var ass_nature = liger.get("ass_nature").getValue();
		if(ass_nature == "01"){
			template_code = "05192";
			ass_para_map = '${ass_05078}';
		}else if(ass_nature == "02"){
			template_code = "05190";
			ass_para_map = '${ass_05074}';
		}else if(ass_nature == "03"){
			template_code = "05191";
			ass_para_map = '${ass_05075}';
		}else if(ass_nature == "04"){
			template_code = "05193";
			ass_para_map = '${ass_05076}';
		}else if(ass_nature == "05"){
			template_code = "05194";
			ass_para_map = '${ass_05077}';
		}else if(ass_nature == "06"){
			template_code = "05195";
			ass_para_map = '${ass_05079}';
		}
		
		
		var useId=0;//统一打印
		if(ass_para_map==1){
			//按用户打印
			useId='${user_id }';
		}
		
		officeFormTemplate({template_code:template_code,use_id:useId});
	}
	
	function printList(){
		
		var template_code = "";
		var ass_para_map = "";
		var ass_nature = liger.get("ass_nature").getValue();
		if(ass_nature == "01"){
			template_code = "05192";
			ass_para_map = '${ass_05078}';
		}else if(ass_nature == "02"){
			template_code = "05190";
			ass_para_map = '${ass_05074}';
		}else if(ass_nature == "03"){
			template_code = "05191";
			ass_para_map = '${ass_05075}';
		}else if(ass_nature == "04"){
			template_code = "05193";
			ass_para_map = '${ass_05076}';
		}else if(ass_nature == "05"){
			template_code = "05194";
			ass_para_map = '${ass_05077}';
		}else if(ass_nature == "06"){
			template_code = "05195";
			ass_para_map = '${ass_05079}';
		}
		
		
		 var useId=0;//统一打印
	 		if(ass_para_map == 1) {
			//按用户打印
			useId = '${user_id }';
		}
		//var data = gridManager.getCheckedRows();
		 var data = grid.selectGetChecked();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ass_card_nos = "";
			$(data).each(function() {
				var rowdata = this.rowData;
				ass_card_nos += "'"+rowdata.ass_card_no + "',"
			});
			var para = {
				ass_card_nos : ass_card_nos.substring(0, ass_card_nos.length - 1),
				template_code : template_code,
				class_name : "com.chd.hrp.ass.serviceImpl.card.AssCardBasicServiceImpl",
				method_name : "queryCardPrint",
				isSetPrint : false,//是否套打，默认非套打
				isPreview : true,//是否预览，默认直接打印
				use_id : useId,
				p_num : 1,
				ass_naturs : ass_nature
			};

			officeFormPrint(para);
		}
	}

	function printDate() {
		
		var ass_nature = liger.get("ass_nature").getValue();
		var class_name = "";
		var method_name = "queryPrint";
		var bean_name = "";
		if(ass_nature == "01"){
			class_name = "com.chd.hrp.ass.service.card.AssCardHouseService";
			bean_name = "assCardHouseService";
		}else if(ass_nature == "02"){
			class_name = "com.chd.hrp.ass.service.card.AssCardSpecialService";
			bean_name = "assCardSpecialService";
		}else if(ass_nature == "03"){
			class_name = "com.chd.hrp.ass.service.card.AssCardGeneralService";
			bean_name = "assCardGeneralService";
		}else if(ass_nature == "04"){
			class_name = "com.chd.hrp.ass.service.card.AssCardOtherService";
			bean_name = "assCardOtherService";
		}else if(ass_nature == "05"){
			class_name = "com.chd.hrp.ass.service.card.AssCardInassetsService";
			bean_name = "assCardInassetsService";
		}else if(ass_nature == "06"){
			class_name = "com.chd.hrp.ass.service.card.AssCardLandService";
			bean_name = "assCardLandService";
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
	function showAndHide() {
		if (show_id == 1) {
			$("#divTables").css("display", 'none');
			$('.togglebtn').css('backgroundPositionY', '-10px');
			show_id = 2;
		} else if (show_id == 2) {
			$("#divTables").css("display", 'block');
			$('.togglebtn').css('backgroundPositionY', '0');
			show_id = 1;
			for ( var i in dataFrom.editors) {
				if (dataFrom.editors[i].editor.control == "ComboBox") {
					dataFrom.editors[i].control.valueField.hide()
				}
			}
		}
		/* grid._onResize(); */
		grid.refreshView(); 
	}
	
	
	//条码打印模板设置
	function printCodeSet(){
		
		var template_code = "";
		var ass_para_map = '${ass_05100}';
		var ass_nature =  liger.get("ass_nature").getValue();
		
		/**
		*无形资产 土地 房屋不提供条码打印
		*/
		if(ass_nature == '06' || ass_nature == '05' || ass_nature == '01'){
			return;
		}
		
		var useId=0;//统一打印
		if(ass_para_map==1){
			//按用户打印
			useId = '${user_id }';
		}
		
		officeFormTemplate({template_code:'05200',use_id:useId});
	}
	//模板条码打印
	function printCode(){
		
		var ass_para_map = '${ass_05100}';
		var ass_nature =  liger.get("ass_nature").getValue();
		
		/**
		*无形资产 土地 房屋不提供条码打印
		*/
		if(ass_nature == '06' || ass_nature == '05' || ass_nature == '01'){
			return;
		}
		

		
		var arr = [];
		var ass_cards = [];
		var str = "";
		 var data = grid.selectGetChecked();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
				return;
			} else {
				$(data).each(function(i,v){
					var rowdata = this.rowData;
					var ass_spec = rowdata.ass_spec == null ? "" : rowdata.ass_spec;
					var ass_mondl = rowdata.ass_mondl == null ? "" : rowdata.ass_mondl;
					var location = rowdata.location == null ? "" : rowdata.location;
					var price = rowdata.price == null ? "" : rowdata.price;
					var fac_name = rowdata.fac_id_name == null || rowdata.fac_id_name == "undefined" ? "" : rowdata.fac_id_name;
					var store_name = rowdata.store_id_name == null || rowdata.store_id_name == "undefined" ? "" : rowdata.store_id_name;
					var note = rowdata.note == null ? "" : rowdata.note;
					var ass_card_no = "";
					if(ass_para_map == "0"){
						ass_card_no = rowdata.ass_ori_card_no == null || rowdata.ass_ori_card_no =="" ? rowdata.ass_card_no :rowdata.ass_ori_card_no;
					}else if(ass_para_map == "1"){
						ass_card_no = rowdata.ass_card_no;
					}
					
					if(rowdata.use_state == 6){
						str = str + "卡片["+rowdata.ass_card_no+"]是待处置状态不能打印！</br>"
					}
					
					if(rowdata.use_state == 7){
						str = str + "卡片["+rowdata.ass_card_no+"]是已处置状态不能打印！</br>"
					}
					
					$.post("generalCardCode.do?isCheck=false",{ass_nature:ass_nature,ass_card_no:ass_card_no},function(data){
						
					},"json"); 
					
					var obj = {
							title:'${hos_simple}' + '固定资产卡片',
							bar_code:rowdata.bar_code,
							ass_card_no:ass_card_no,
							ass_code:rowdata.ass_id_code,
							ass_name:rowdata.ass_id_name,
							ass_spec:ass_spec+" "+ass_mondl,
							location:location,
							price:price,
							fac_name:fac_name,
							share_dept:rowdata.dept_id_name,
							in_date:rowdata.in_date == null?"":rowdata.in_date,
							ass_seq_no:rowdata.ass_seq_no == null?"":rowdata.ass_seq_no,
							store_name:store_name,
							note:note,
							img_path:'<%=path%>/'+rowdata.bar_url
					};
					ass_cards.push(rowdata.ass_card_no);
					arr.push(obj);  
				});
			}
			
		if(str != ""){
			$.ligerDialog.warn(str);
			return;
		}	
		cardLodopBarCode(arr);
		//$.post("updateIsBarPrint.do?isCheck=false",{ass_nature:ass_nature,paramVo:ass_cards.toString()},function(data){
			
		//},"json");
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
</body>
</html>
