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
	<jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var year_input, source_id_select, asset_type_select;

	$(function () {
		//加载数据
		init();
		loadHead(null);
		loadHotkeys();
	});
	//查询
	function query() {
		var parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'source_id', value: source_id_select.getValue() },
			{ name: 'ass_type_id', value: asset_type_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,'queryExecuteMain.do?isCheck=false');
	}
	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '预算年度', name: 'budg_year', align: 'center', width: '10%',editable:setEdit ,
					editor: {
						type: 'select',
						valueField: 'id',
						textField: 'text',
						url: '../../../queryBudgYear.do?isCheck=false',
						keySupport: true,
						autocomplete: true
					},
				},
				{display: '月份', name: 'month', align: 'center', width: '5%',editable:setEdit ,
					editor: {
						valueField: 'monthID',
						textField: 'label',
						type: 'select',  //编辑框为下拉框时
						source: [{ monthID: "01", label: "1月" }, {
							monthID: "02", label: "2月"
						}, { monthID: "03", label: "3月" }, {
							monthID: "04", label: "4月"
						}, { monthID: "05", label: "5月" }, {
							monthID: "06", label: "6月"
						}, { monthID: "07", label: "7月" }, {
							monthID: "08", label: "8月"
						}, { monthID: "09", label: "9月" }, {
							monthID: "10", label: "10月"
						}, { monthID: "11", label: "11月" }, {
							monthID: "12", label: "12月"
						}],   //  静态数据接口  也可以是回调函数返回值
					}
				},
				{display: '资金来源', name: 'source_name', align: 'left',width:'20%',editable:setEdit ,
					editor: {
						type: 'select',
						valueField: 'aid',
						textField: 'atext',
						url: 'querySourceName.do?isCheck=false',
						keySupport: true,
						autocomplete: true,
					}
				},
				{display: '资产类别', name: 'ass_type_name', align: 'left', width: '20%',editable:setEdit ,
					editor: {
						keyField: 'bid',
						type: 'select',  //编辑框为下拉框时
						url: '../../../queryBudgCostFassetType.do?isCheck=false',//  静态数据接口  也可以是回调函数返回值
					}
				},
				{display: '采购金额', name: 'pur_amount', dataType:'float', align: 'right',width: '15%',editable:setEditFalse,
					render:function(ui){
						return formatNumber(ui.rowData.pur_amount, 2, 1);
					}
				},
				{display: '说明', name: 'remark', dataType:"string", align: 'left',width: '27%',editable:setEditFalse
				}
			],
			dataModel:{
	           	method:'POST',
	           	location:'remote',
	           	url:'',
	           	recIndx: 'budg_year' //必填 且该列不能为空  
            },
            usePager:true,width: '100%', height: '100%',checkbox: true,editable:true,
				toolbar: {
	                items: [
	                { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
	                { type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
	                { type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					{ type: "button", label: '下载模板',icon:'arrowthick-1-s',listeners: [{ click: downTemplate}] },
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
	            ]},
          });
	}
	
	//添加行
	function add_Row() {
		grid.addRow();
	}
	
	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
	
	// 合计行 不允许编辑 返回 true 或 false  控制单元格可否编辑 用  
    function setEditFalse(ui){
		if(!ui.rowData){
			return false ;
		}else{
			if(ui.rowData.budg_year == '合计'){
	   			return false ;
	   		}else{
	   			return true ;
	   		}
		}
    }
	
	//保存
	function save() {

		var data = grid.getChanges();
		
		if (data.addList.length == 0 && data.updateList.length == 0) {

			$.etDialog.error('没有需要保存的数据');

		} else {
			var ParamVo = [];
			var addData = data.addList ;
			var updateData = data.updateList ;
			if (addData.length > 0) {
				if (!validateGrid(addData)) {
					return false;
				}
				$(addData).each(function () {

					ParamVo.push(
						this.budg_year + "@" + 
						this.month + "@" +
						this.aid + "@" +
						this.bid + "@" +
						this.pur_amount + "@" + 
						(this.remark ? this.remark : "") + "@" +
						this._rowIndx +"@"+ 
						"1"
					)
				});
			}

			if (updateData.length > 0) {

				$(updateData).each(function () {

					ParamVo.push(
						this.budg_year + "@" + 
						this.month + "@" +
						this.source_id + "@" + 
						this.ass_type_id + "@" +
						this.pur_amount + "@" + 
						(this.remark ? this.remark : "") + "@" + 
						this._rowIndx +"@"+ 
						"2"
					)
				});
			}
			ajaxPostData({
 			    url: 'saveBudgAssPurExecute.do?isCheck=false',
 			    data: {ParamVo : ParamVo.toString()},
 			    success: function (responseData) {
 			    	query();
 			    }
 			})
		}
	}

	function validateGrid(data) {

		var msg = "";
		var rowm = "";
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();

		$.each(data, function (i, v) {

			rowm = "";
			if (v.budg_year == "" || v.budg_year == null || v.budg_year == 'undefined') {
				rowm += "[预算年度]、";
			}
			if (v.month == "" || v.month == null || v.month == 'undefined') {
				rowm += "[月份]、";
			}
			if (v.source_name == "" || v.source_name == null || v.source_name == 'undefined') {
				rowm += "[资金来源]、";
			}
			if (v.ass_type_name == "" || v.ass_type_name == null || v.ass_type_name == 'undefined') {
				rowm += "[资产类别]、";
			}
			if (v.pur_amount == "" || v.pur_amount == null || v.pur_amount == 'undefined') {
				rowm += "[资产类别]、";
			}
			if (rowm != "") {
				rowm = rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key = v.budg_year + v.month + v.aid + v.bid
			var value="第"+(Number(v._rowIndx)+1)+"行";
			if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
				targetMap.put(key, value);
			} else {
				msg += targetMap.get(key) + "数据重复!!" + "\n\r";
			}
		});
		if (msg != "") {
			$.etDialog.warn(msg);
			return false;
		} else {
			return true;
		}
	}

	//删除;
	function remove() {
		var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.budg_year + "@" +
						this.rowData.month + "@" +
						this.rowData.source_id + "@" + this.rowData.ass_type_id
					)
				} else {
					deletePageRow.push(this);
				}
			});
            
            $.etDialog.confirm('确定删除?', function (yes){
         		if(ParamVo.length > 0){
         			ajaxPostData({
         			    url: 'deleteBudgAssPurExecute.do?isCheck=false',
         			    data: {ParamVo : ParamVo.toString()},
         			    success: function (responseData) {
         			    	query();
         			    }
         			})
         		}else if(deletePageRow.length > 0 ){
   	            	grid.deleteRows(deletePageRow);
   	            	$.etDialog.success("删除成功!");
   	            }
	        }); 
        }
	}
	
	//导入
	function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgAssPurExecuteImportPage.do?isCheck=false'
		});
		layer.full(index);
    }
	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
	
	function init() {
		getData("../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onChanged: query
			});
		});

		source_id_select = $("#source_id_select").etSelect({
			url: "../../../queryBudgSource.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		});

		asset_type_select = $("#asset_type_select").etSelect({
			url: "../../../queryBudgCostFassetType.do?isCheck=false?isCheck=false",
			defaultValue: "none",
			onChange: query
		});
	}

	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">资金来源：</td>
				<td class="ipt">
					<select name="" id="source_id_select" style="width:180px;"></select>
				</td>
				<td class="label">资产分类：</td>
				<td class="ipt">
					<select name="" id="asset_type_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>