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
	<jsp:param value="select,datepicker,ligerUI,dialog,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid;
	var charge_stan_code;
	//打印 单元格格式化 用
	var renderFunc = {
		budg_value: function (value) {
			return formatNumber(value, 2, 1)
		}
	};
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	var year_input, charge_stan_select;

	function init() {
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	initColumns();
                }, 10);
            },
            defaultDate: true
        });

		charge_stan_select = $("#charge_stan_select").etSelect({
			url: "../../../queryBudgChargeStan.do?isCheck=false&charge_stan_nature=01",
			defaultValue: "none",
			onChange: query
		})
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
	
	//查询
	function query() {
		var year = year_input.getValue();

		if (!year) {
			$.ligerDialog.error('预算年度不能为空');

			return false;
		}
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'charge_stan_code', value: charge_stan_select.getValue() },
			{ name: 'charge_stan_nature', value: "01" }
		];
		
		//加载查询条件
		grid.loadData(parms,"queryBudgHosChargeStan.do?isCheck=false");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left', width:"10%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'year',
						url: '../../../queryBudgYearTen.do?isCheck=false',
					}
				},
				{display: '指标编码', name: 'charge_stan_code', align: 'left', width:"15%",editable:false},
				{display: '指标名称', name: 'charge_stan_name', align: 'left', width: "20%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'charge_stan_code',
						url: '../../../queryBudgChargeStan.do?isCheck=false&charge_stan_nature=01',
						change: function (rowdata, celldata) {
							var value = celldata.selected.value;
							grid.updateRow(celldata.rowIndx,{charge_stan_code:value.split(" ")[0]})
						}
					}
				},
				{display: '预算值(E)', name: 'budg_value', align: 'right', width:"15%", dataType: "float",
					render:function(ui){
						if (ui.rowData.fun_id && ui.rowData.budg_value) {//取值函数链接
							return "<a href=javascript:openFunProcess('" + ui.rowData.group_id + "|" +
								ui.rowData.hos_id + "|" + ui.rowData.copy_code + "|" + ui.rowData.charge_stan_code + "|" + ui.rowData.year + "|" +
								ui.rowData.fun_id + "')>" + formatNumber(ui.rowData.budg_value, 2, 1) + "</a>";
						} else if (ui.rowData.formula_id && ui.rowData.budg_value) {//计算公式链接
							return "<a href=javascript:openFormulaProcess('" + ui.rowData.group_id + "|" +
								ui.rowData.hos_id + "|" + ui.rowData.copy_code + "|" + ui.rowData.charge_stan_code + "|" + ui.rowData.year + "|" +
								ui.rowData.formula_id + "')>" + formatNumber(ui.rowData.budg_value, 2, 1) + "</a>";
						} else if (ui.rowData.budg_value) {
							return formatNumber(ui.rowData.budg_value, 2, 1);
						}
					}
				},
				{display: '说明(E)', name: 'remark', align: 'left', dataType:"string",width:"37%",}
			],
			dataModel:{
	           	method:'POST',
	           	location:'remote',
	           	url:'',
	           	recIndx: 'year'
            },
	        usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
	        addRowByKey:true,
			toolbar: {
	            items: [
	              	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
	              	{ type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] },
					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					/* { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]}, */
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: impNew}] },
					{ type: "button", label: '计算',icon:'calculator',listeners: [{ click: collect}] },

				]
			},
		});
	}

	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
    
    function add_Row(){
    	grid.addRow() ;
    }

	//导入数据 
	function impNew() {
		parent.$.etDialog.open({
			url: 'hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanImportNewPage.do?isCheck=false',
			height: 300, width: 450, title: '医院费用标准维护', 
			isMax: true, frameName: window.name//用于parent弹出层调用本页面的方法或变量
		});
	}

	function add_open() {
		$.etDialog.open({
			url: 'budgHosChargeStanAddPage.do?isCheck=false',
			height: 300, width: 450, title: '医院费用标准维护', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgHosChargeStan()
            }
		});
	}

	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var delData = [];//接受页面端删除数据
			$(data).each(function () {
				if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.year + "@" +
						this.rowData.charge_stan_code
					)
				} else {
					delData.push(this)
				}
			});
			$.etDialog.confirm('删除操作会刷新页面,页面未保存数据可能丢失！确定删除?', function (yes) {
				if (yes) {
					if (ParamVo.length > 0) {
						ajaxPostData({
		                    url: "deleteBudgHosChargeStan.do?isCheck=false",
		                    data: { ParamVo: ParamVo.toString() },
		                    success: function(responseData) {
		                    	query();
		                    }
		                });
					} else {
						grid.deleteRows(delData);
						$.etDialog.success("删除成功.");
					}
				}
			});
		}
	}

	function imp() {
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgHosChargeStanImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	
	function downTemplate() {
		location.href = "downTemplate.do?isCheck=false";
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm =
			"group_id=" + vo[0] + "&" +
			"hos_id=" + vo[1] + "&" +
			"copy_code=" + vo[2] + "&" +
			"year=" + vo[3] + "&" +
			"charge_stan_code=" + vo[4]
		$.etDialog.open({
			url: 'budgHosChargeStanUpdatePage.do?isCheck=false&parm=' + parm,
			height: 300, width: 450, title: '医院费用标准维护', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgHosChargeStan()
            }
		});
	}

	function save() {
		debugger
		var data = grid.getChanges();
		var ParamVo =[];
		if( data.addList.length > 0 || data.updateList.length > 0 ){
    		if(data.addList.length > 0){
        		var addData = data.addList ;
        		if(!validateGrid(addData)){
        			return  false ;
        		}
        		addData.forEach(function(item) {	
                 	ParamVo.push(
	          			item.year	+"@"+
	          			item.charge_stan_code   +"@"+ 
	          			item.budg_value   +"@"+ 
	 					(item.remark?item.remark:"-1")   	+"@"+ 
	 					//行号 提示错误信息用
	 					item._rowIndx +"@"+
	 					"1" //添加标识
     				) 
     			});
         	}
 			if( data.updateList.length > 0){
 	        	
 	        	var updateData = data.updateList ;
 	        	if(!validateGrid(updateData)){
        			return  false ;
        		}
 	        	updateData.forEach(function(item) {	
 	               	ParamVo.push(
 	               		item.year	+"@"+
 	               		item.charge_stan_code   +"@"+ 
       					item.budg_value   +"@"+ 
       					(item.remark?item.remark:"-1")   	+"@"+ 
       					//行号 提示错误信息用
       					item._rowIndx +"@"+
       					"2" //修改标识
 	   				) 
 	   			});
         	}
 			ajaxPostData({
                url: "saveBudgHosChargeStan.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		}else{
			$.etDialog.warn('没有需要保存的数据!');
		}
	}

	function validateGrid(data) {  
		debugger;
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.year) {
				rowm+="[年度]、";
			}
			if (!v.charge_stan_name) {
				rowm+="[指标名称]、";
			}
			if (!v.budg_value) {
				rowm+="[预算值]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.charge_stan_code 
			var value="第"+(Number(v._rowIndx)+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
			}
 		});
 		if(msg != ""){
 			$.etDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
	//计算
	function collect() {
		var year = year_input.getValue();

		if (!year) {
			$.ligerDialog.error('预算年度不能为空');
			return false;
		}
		
		ajaxPostData({
            url: "collectBudgHosChargeStan.do?isCheck=false&year=" + year,
            success: function(responseData) {
            	query();
            }
        });
	}
	//计算过程查看  链接
	function openFormulaProcess(obj) {

		var vo = obj.split("|");

		var parm =
			"group_id=" + vo[0] + "&" +
			"hos_id=" + vo[1] + "&" +
			"copy_code=" + vo[2] + "&" +
			"index_code=" + vo[3] + "&" +
			"year=" + vo[4] + "&" +
			"formula_id=" + vo[5] + "&" +
			"element_type_code=" + '02'
			"element_level=" + '01'
		parent.$.etDialog.open({
			url: 'hrp/budg/common/budgformula/budgFormulaProcessPage.do?isCheck=false&' + parm,
			height: 600, width: 800, title: '计算过程查看', 
			isMax: true, frameName: window.name//用于parent弹出层调用本页面的方法或变量
		});
	}

	//取值函数查看  链接 
	function openFunProcess(obj) {

		var vo = obj.split("|");

		var parm =
			"group_id=" + vo[0] + "&" +
			"hos_id=" + vo[1] + "&" +
			"copy_code=" + vo[2] + "&" +
			"index_code=" + vo[3] + "&" +
			"year=" + vo[4] + "&" +
			"fun_code=" + vo[5] + "&" +
			"index_type_code=" + "02"

		parent.$.etDialog.open({
			url: 'hrp/budg/common/budgfun/budgFunProcessPage.do?isCheck=false&' + parm,
			height: 600, width: 800, title: '函数取值过程查看', 
			isMax: true, frameName: window.name//用于parent弹出层调用本页面的方法或变量
		});
	}
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "医院费用标准维护";
	}

	//增量生成
	function generate() {
		var year = year_input.getValue();
		if (year) {
			ajaxPostData({
	            url: "generate.do?isCheck=false&year=" + year,
	            success: function(responseData) {
	            	query();
	            }
	        });
		} else {
			$.etDialog.error("预算年度不能为空");
		}
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('G', generate);
		hotkeys('B', downTemplate);
		hotkeys('I', imp);
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">费用标准：</td>
				<td class="ipt">
					<select name="" id="charge_stan_select" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="ipt"></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>

</html>