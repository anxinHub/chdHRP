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
	var charge_stan_code ;
	//打印 单元格格式化 用
	var renderFunc = {
		yb_income: function (value) {
			return formatNumber(value, 2, 1)
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
			defaultValue:"none",
			onChange:query
		});

		dept_name_select = $("#dept_name_select").etSelect({
			url:"../../../queryBudgDeptDict.do?isCheck=false",
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
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'dept_id', value:dept_name_select.getValue() },
			{ name: 'insurance_code', value: insurance_code_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,"queryBudgZeyfIncomeHis.do?isCheck=false");
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
				{display: '科室编码', name: 'dept_code', align: 'left',width:"19%",editable:false,},
				{display : '科室名称', name : 'dept_name', align : 'left', width:"25%",editable:setEdit,
					editor : {
						type : 'select',
						keyField : 'dept_id',
						url : '../../../queryBudgDeptDict.do?isCheck=false',
						change: function (rowdata, celldata) {
							var value = celldata.selected.value;
							grid.updateRow(rowdata._rowIndx,{dept_code:value.split(" ")[0]})
						}
					} 
				},
				{display: '医保类型', name: 'insurance_name', align: 'left',width:"25%",editable:setEdit,
					editor : {
						type : 'select',
						keyField : 'insurance_code',
						url : '../../../queryBudgYBType.do?isCheck=false',
					} 
				},
				{display: '医保收入(E)', name: 'yb_income', align: 'right', dataType:"float",width:"19%",
					render:function(ui){
						if (ui.rowData.yb_income) {
							return formatNumber(ui.rowData.yb_income, 2, 1);
						}
					}
				}
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
	
    //不用 
	function add_open() {
		$.etDialog.open({
			url: 'budgZeyfIncomeHisAddPage.do?isCheck=false',
			height: 300, width: 450, title: '总额预付历史收入数据', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.savebudgZeyfIncomeHis()
            }
		});
	}
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "医院费用标准维护";
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
						this.rowData.dept_id + "@" +
						this.rowData.insurance_code
					)
				} else {
					delData.push(this)
				}
			})
			$.ligerDialog.confirm('确定删除?', function (yes) {
				if (yes) {
					if (ParamVo.length > 0) {
						ajaxPostData({
		                    url: "deleteBudgZeyfIncomeHis.do?isCheck=false",
		                    data: { ParamVo: ParamVo.toString() },
		                    success: function(responseData) {
		                    	query();
		                    }
		                });
					}else {
						grid.deleteRows(delData);
						$.etDialog.success("删除成功.");
					}
				}
			});
		}
	}
	//导入数据 
	function impNew() {
		parent.$.etDialog.open({
			url: 'hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisImportNewPage.do?isCheck=false',
			height: 300, width: 450, title: '总额预付历史数据采集',
			isMax: true, frameName: window.name//用于parent弹出层调用本页面的方法或变量
		});
	}

	function imp() {
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgZeyfIncomeHisImportPage.do?isCheck=false'
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
			url: 'budgZeyfIncomeHisUpdatePage.do?isCheck=false',
			height: 300, width: 450, title: '总额预付历史收入数据', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.savebudgZeyfIncomeHis()
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
               			item.insurance_code   +"@"+ 
               			item.dept_id + "@" +
               			(item.yb_income?item.yb_income:"-1")   	+"@"+  
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
 	               		item.insurance_code   +"@"+ 
 	               		item.dept_id + "@" +
       					(item.yb_income?item.yb_income:"-1")   	+"@"+ 
       					//行号 提示错误信息用
       					item._rowIndx +"@"+
       					"2" //修改标识
 	   				) 
 	   			});
         	}
 			ajaxPostData({
                url: "saveBudgZeyfIncomeHis.do?isCheck=false",
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
			if (!v.year) {
				rowm+="[年度]、";
			}
			if (!v.dept_name) {
				rowm+="[科室名称]、";
			}
			if (!v.insurance_name) {
				rowm+="[医保类型]、";
			}
			if (!v.yb_income) {
				rowm+="[医保收入]、";
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
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
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