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
	//打印 单元格格式化 用
	var renderFunc = {

		control_limit: function (value) {
			return formatNumber(value, 2, 1);
		},
		last_yb_income: function (value) {
			return formatNumber(value, 2, 1);
		},
		rate: function (value) {

			return formatNumber(value, 2, 1) + "%";
		},
		dept_control_limit: function (value) {
			return formatNumber(value, 2, 1);
		}
	};

	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	var year_input,insurance_code_select,dept_name_select;

	function init(){
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

		insurance_code_select = $("#insurance_code_select").etSelect({
			url:"../../../queryBudgYBType.do?isCheck=false",
			onChange:query
		});

		dept_name_select = $("#dept_name_select").etSelect({
			url:"../../../queryBudgDeptDict.do?isCheck=false&type_code=02",
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
	}
	
	//查询
	function query() {
		if (!year_input.getValue()) {
			$.etDialog.error('年度不能为空');
			return false;
		}
		/*
		if (!insurance_code_select.getValue()) {
			$.etDialog.error('医保类型不能为空');
			return false;
		}
		*/
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() },
			{ name: 'insurance_code', value: insurance_code_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,"queryBudgDeptYbLimit.do?isCheck=false");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left',  width:"8%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'year',
						url: '../../../queryBudgYearTen.do?isCheck=false',
						change: qureyLastData  //值改变 重新查询 全院医保额度、上年医保收入
					},
				},
				{display: '科室编码', name: 'dept_code', align: 'left', width:"10%",editable:false},
				{display: '科室名称', name: 'dept_name', align: 'left', width: "10%",
					editor: {
						type: 'select',
						keyField: 'dept_id',
						url: '../../../queryBudgDeptDict.do?isCheck=false&type_code=02',
						change: qureyLastData,	//值改变 重新查询 全院医保额度、上年医保收入
					},
				},
				{display: '医保类型', name: 'insurance_name', align: 'left', width:"15%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'insurance_code',
						url : '../../../queryBudgYBType.do?isCheck=false',
						change: qureyLastData //值改变 重新查询 全院医保额度、上年医保收入
					}
				},
				{display: '全院医保额度', name: 'control_limit', align: 'right', width:"10%",editable:false,
					render:function(ui){
						if (ui.rowData.control_limit) {
							return formatNumber(ui.rowData.control_limit, 2, 1);
						}
					}
				},
				{display: '上年医保收入', name: 'last_yb_income', align: 'right', width:"10%",editable:false,
					render:function(ui){
						if (ui.rowData.last_yb_income) {
							return formatNumber(ui.rowData.last_yb_income, 2, 1);
						}
					}
				},
				{display: '分解比例(E)', name: 'rate', align: 'center', width:"10%", dataType:"float",
					editor : {change:countLimit},
					render:function(ui){
						if (ui.rowData.rate) {
							return formatNumber(ui.rowData.rate, 2, 1) + "%";
						}
					},
				},
				{display: '科室医保额度(E)', name: 'dept_control_limit', align: 'right', width:"10%", editor: { type: "float" },
					render:function(ui){
						if (ui.rowData.dept_control_limit) {
							return formatNumber(ui.rowData.dept_control_limit, 2, 1);
						}
					},
				},
				{display: '备注(E)', name: 'remark', align: 'left', dataType: "string",width:"25%"}
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
					{ type: "button", label: '历史数据比例分解',icon:'arrow-4-diag',listeners: [{ click: updateResolveRate}] },
				]
			},
			summary: { //  前台渲染摘要行    摘要行集合    
                totalColumns: ['last_yb_income','rate','dept_control_limit'], //合计冻结行 
                keyWordCol: 'year', //关键字所在列的列名
                /* averageColumns: ['revenues', 'profits'], // 平均冻结行
                maxColumns: ['revenues', 'profits'], // 最大值冻结行
                minColumns: ['revenues', 'profits'] //  最小值冻结行 */
            }, 
            load:function(){
            	grid.refreshSummary();
            }
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

	function qureyLastData(rowdata,celldata) {
		setTimeout(function (){  // 增加延时定时器 让select框的change事件里的data同步
			if(rowdata.dept_name){
				grid.updateRow(celldata.rowIndx,{dept_code:rowdata.dept_name.split(" ")[0]})
			}
			// 年度 、 科室 、 医保类型 全部填写完毕后  查询 全院医保额度、上年医保收入 并回显数据
			if (rowdata.year && rowdata.dept_id && rowdata.insurance_code) {
				var formPara = {
					year: rowdata.year,
					dept_id: rowdata.dept_id,
					insurance_code: rowdata.insurance_code,
				};
				ajaxPostData({
					url: "qureyLastData.do?isCheck=false",
					data: formPara,
					success: function(responseData) {
						grid.updateRow(celldata.rowIndx,{control_limit:responseData.control_limit,last_yb_income: responseData.last_yb_income})
					}
				});
			}
		},300)
	}

	//分解比率 后  计算并更新 科室医保额度单元格  
	function countLimit(rowdata,celldata) {
		// 如果 
		if (rowdata.rate) {
			if(rowdata.control_limit){
				if (rowdata.rate >= 0 && rowdata.rate <= 100) {
					
					var dept_control_limit = Number(rowdata.control_limit) * Number(rowdata.rate) / 100;
					grid.updateRow(celldata.rowIndx,{'dept_control_limit':dept_control_limit})
					
				} else {
					$.ligerDialog.error('分解比率必须在0-100之间');
				}
			} else{
				setTimeout(function(){
	   				$.etDialog.error('指标:【'+rowdata.insurance_name +'】全院医保额度未维护,请至【全院医保额度控制】处维护数据后操作!');
	   			},10)
			} 
			
		}else{
	   		 $.ligerDialog.error('分解比例不能为空,且必须为数字');
		}
		
	}
	
	
	function add_open() {
		$.etDialog.open({
			url: 'budgDeptYbLimitAddPage.do?isCheck=false',
			height: 300, width: 450, title: '科室医保额度控制', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgDeptYbLimit()
            }
		});
	}

	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var delData = []
			$(data).each(function () {
				if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.year + "@" +
						this.rowData.dept_id + "@" +
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
		                    url: "deleteBudgDeptYbLimit.do?isCheck=false",
		                    data: { ParamVo: ParamVo.toString() },
		                    success: function(responseData) {
		                    	query();
		                    }
		                });
					} else {
						grid.deleteRows(delData);
						$.etDialog.success("删除成功.")
					}
				}
			});
		}
	}

	function impNew() {
		var paramVo = {
			"column": [
				{
					"name": "a",
					"display": "年度【必填】",
					"width": "200",
					"require": true
				},
				{
					"name": "b",
					"display": "科室名称【必填】",
					"require": true
				},
				{
					"name": "c",
					"display": "医保类型名称【必填】",
					"require": true
				},
				{
					"name": "d",
					"display": "科室医保额度【必填】",
					"require": true
				},
				{
					"name": "e",
					"display": "备注"
				}
			]
		};
		importSpreadView("hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitImportNewPage.do?isCheck=false", paramVo);
	}

	function imp() {
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgDeptYbLimitImportPage.do?isCheck=false'
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
			"dept_id=" + vo[4] + "&" +
			"insurance_code=" + vo[5]

		$.etDialog.open({
			url: 'budgDeptYbLimitUpdatePage.do?isCheck=false&parm=' + parm,
			height: 300, width: 450, title: '科室医保额度控制', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgDeptYbLimit()
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
               			item.year	+"@"+
               			item.dept_id	+"@"+
               			item.insurance_code   +"@"+ 
               			item.rate + "@" +
               			(item.dept_control_limit?item.dept_control_limit:"-1")   	+"@"+  
               			(item.remark ? item.remark : "-1") 	+"@"+ 
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
 	               		item.year	+"@"+
             			item.dept_id	+"@"+
             			item.insurance_code   +"@"+ 
             			item.rate + "@" +
             			(item.dept_control_limit?item.dept_control_limit:"-1")   	+"@"+  
             			(item.remark ? item.remark : "-1") 	+"@"+ 
       					//行号 提示错误信息用
       					item._rowIndx +"@"+
       					"2" //修改标识
 	   				) 
 	   			});
         	}
 			ajaxPostData({
                url: "saveBudgDeptYbLimit.do?isCheck=false",
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
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
 			if (v.year == "" || v.year == null || v.year == 'undefined') {
				rowm += "[年度]、";
			}
			if (v.dept_name == "" || v.dept_name == null || v.dept_name == 'undefined') {
				rowm += "[科室编码]、";
			}
			if (v.insurance_code == "" || v.insurance_code == null || v.insurance_code == 'undefined') {
				rowm += "[医保类型编码]、";
			}
			if (!v.dept_control_limit) {
				rowm+="[科室医保额度]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.dept_id + v.insurance_code 
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
		grid.options.lodop.title = "科室医保额度控制";
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
			$.ligerDialog.error("预算年度不能为空");
		}
	}

	//历史数据比例分解
	function updateResolveRate() {
		var year = year_input.getValue();
		var insurance_code = insurance_code_select.getValue();
		if (year && insurance_code) {
			ajaxPostData({
                url: "updateResolveRate.do?isCheck=false&year=" + year + "&insurance_code=" + insurance_code,
                success: function(responseData) {
                	query();
                }
            });
		} else {
			$.ligerDialog.error("预算年度、医保类型不能为空");
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
				<td class="label">预算科室：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>
</html>