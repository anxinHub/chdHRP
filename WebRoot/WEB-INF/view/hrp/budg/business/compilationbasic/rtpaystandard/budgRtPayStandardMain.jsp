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
		<jsp:param value="grid,select,datepicker,ligerUI,dialog,grid" name="plugins" />
	</jsp:include>
<script type="text/javascript">
	var grid;
	
	//打印 单元格格式化 用
	var renderFunc = {
		outpatient_charge: function (value) {
			return formatNumber(value, 2, 1)
		},
		day_bed_charge: function (value) {
			return formatNumber(value, 2, 1)
		},
		o_workload_budg: function (value) {
			return formatNumber(value, 2, 1)
		},
		i_workload_budg: function (value) {
			return formatNumber(value, 2, 1)
		}
	};
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	var year_input, insurance_code_select;

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

		insurance_code_select=$("#insurance_code_select").etSelect({
			url:"../../../queryBudgYBTypeByMode.do?isCheck=false&pay_mode_code=RT",
			defaultValue:"none",
			onChange:query
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
	};
	
	//查询
	function query() {
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'insurance_code', value: insurance_code_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,"queryBudgRtPayStandard.do?isCheck=false");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left',width:"10%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'year',
						url: '../../../queryBudgYearTen.do?isCheck=false',
					}
				},
				{display: '医保类型', name: 'insurance_name', align: 'left',width:"15%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'insurance_code',
						url: 'queryBudgYBTY.do?isCheck=false&pay_mode_code=RT',
						change:function(rowdata,celldata){
							var value = celldata.selected.value;
							grid.updateRow(rowdata._rowIndx, {insurance_code:value.split(" ")[0]})
						}
					}
				},
				{display: '门诊均次费用(元) (E)', name: 'outpatient_charge', align: 'right', dataType:"float",width:"15%",
					render:function(ui){
						if (ui.rowData.outpatient_charge) {
							return formatNumber(ui.rowData.outpatient_charge, 2, 1);
						}
					}
				},
				{
					display: '床日均次费用(人次) (E)', name: 'day_bed_charge', align: 'right', dataType:"float",width:"15%",
					render:function(ui){
						if (ui.rowData.day_bed_charge) {
							return formatNumber(ui.rowData.day_bed_charge, 2, 1);
						}
					}
				},
				{
					display: '门诊业务量预算(元) (E)', name: 'o_workload_budg', align: 'right', dataType:"float",width:"15%",
					render:function(ui){
						if (ui.rowData.o_workload_budg) {
							return formatNumber(ui.rowData.o_workload_budg, 2, 1);
						}
					}
				},
				{
					display: '床日业务量预算(床日) (E)', name: 'i_workload_budg', align: 'right', dataType:"float",width:"15%",
					render:function(ui){
						if (ui.rowData.i_workload_budg) {
							return formatNumber(ui.rowData.i_workload_budg, 2, 1);
						}
					}
				},
				{display: '备注 (E)', name: 'remark', align: 'left', dataType:'string',width:"20%",}
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
	
	function impNew() {
		var para = {
			"column": [
				{
					"name": "a",
					"display": "年度【必填】",
					"width": "200",
					"require": true
				},
				{
					"name": "b",
					"display": "医保类型名称【必填】",
					"width": "200",
					"require": true
				},
				{
					"name": "c",
					"display": "门诊均次费用",
					"width": "200"
				},
				{
					"name": "d",
					"display": "床日均次费用",
					"width": "200"
				},
				{
					"name": "e",
					"display": "门诊业务量预算",
					"width": "200"
				},
				{
					"name": "f",
					"display": "床日业务量预算",
					"width": "200"
				},
				{
					"name": "g",
					"display": "备注",
					"width": "200"
				}
			]
		};
		importSpreadView("hrp/budg/business/compilationbasic/rtpaystandard/budgRTPaystandardImportNewPage.do?isCheck=false", para);
	}

	function add_open() {
		$.etDialog.open({
			url: 'budgRtPayStandardAddPage.do?isCheck=false',
			height: 300, width: 450, title: '人头付费标准维护', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.savebudgWorkHosDbz()
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
						this.rowData.insurance_code
					)
				} else {
					delData.push(this)
				}
			});
			$.etDialog.confirm('删除操作会刷新页面,页面未保存数据可能丢失！确定删除?', function (yes) {
				if (yes) {
					if (ParamVo.length > 0) {
						ajaxPostData({
		                    url: "deleteBudgRtPayStandard.do?isCheck=false",
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
			content: 'budgRtPayStandardImportPage.do?isCheck=false'
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
			"insurance_code=" + vo[4]

		$.etDialog.open({
			url: 'budgRtPayStandardUpdatePage.do?isCheck=false',
			height: 300, width: 450, title: '人头付费标准维护', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.savebudgWorkHosDbz()
            }
		});
	}

	//修改保存
	function save() {
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
               			item.year + "@" +
               			item.insurance_code + "@" +
               			(item.outpatient_charge ? item.outpatient_charge : "") + "@" +
          				(item.day_bed_charge ? item.day_bed_charge : "") + "@" +
          				(item.o_workload_budg ? item.o_workload_budg : "") + "@" +
          				(item.i_workload_budg ? item.i_workload_budg : "") + "@" +
          				(item.remark ? item.remark : "-1") +"@"+
      					//行号 提示错误信息用
      					item._rowIndx +"@"+
      					"1" //添加标识
     				) 
     			});
         	}
 			if( data.updateList.length > 0){
 	        	var updateData = data.updateList ;
 	        	updateData.forEach(function(item) {	
 	               	ParamVo.push(
 	               		item.year + "@" +
             			item.insurance_code + "@" +
             			(item.outpatient_charge ? item.outpatient_charge : "") + "@" +
        				(item.day_bed_charge ? item.day_bed_charge : "") + "@" +
        				(item.o_workload_budg ? item.o_workload_budg : "") + "@" +
        				(item.i_workload_budg ? item.i_workload_budg : "") + "@" +
        				(item.remark ? item.remark : "-1") +"@"+
       					//行号 提示错误信息用
       					item._rowIndx +"@"+
       					"2" //修改标识
 	   				) 
 	   			});
         	}
 			ajaxPostData({
                url: "saveBudgRtPayStandard.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		}else{
			$.etDialog.warn('没有需要保存的数据!');
		};
	}
	
	function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
 			if (!v.year) {
				rowm += "[年度]、";
			}
			if (!v.insurance_name) {
				rowm += "[医保类型]、";
			}
			if (!v.outpatient_charge) {
				rowm+="[门诊均次费用]、";
			}
			if (!v.day_bed_charge) {
				rowm+="[床日均次费用]、";
			}
			if (!v.o_workload_budg) {
				rowm+="[门诊业务量预算]、";
			}
			if (!v.i_workload_budg) {
				rowm+="[床日业务量预算]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.insurance_code 
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
	
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "科室单病种业务预算";
	}

	function generate() {
		var year = year_input.getValue() ;
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
				<td class="label">医保类型：</td>
				<td class="ipt">
					<select name="" id="insurance_code_select" style="width:180px;"></select>
					</td>
				<td class="label"></td>
				<td class="ipt"></td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>

</html>