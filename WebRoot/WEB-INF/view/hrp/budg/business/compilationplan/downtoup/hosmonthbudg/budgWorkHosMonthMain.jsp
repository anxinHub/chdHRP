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
<script type="text/javascript">
    var grid;
    var userUpdateStr;
    var parms =[];
    $(function () {
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
    var year_input, index_code_select;
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

		index_code_select = $("#index_code_select").etSelect({
			url: "../../../../queryBudgIndexDict.do?isCheck=false",
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
	};
    //查询
	function query() {
		parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms, 'queryBudgWorkHosMonthDown.do?isCheck=false');
		
		
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'center', width: 60, editable: false,
					render:function(ui) {
					if (ui.rowData.year != "合计") {
						return "<a href=javascript:openUpdate('" + ui.rowData.group_id + "|" + ui.rowData.hos_id + "|"
							+ ui.rowData.copy_code + "|" + ui.rowData.year + "|" + ui.rowData.index_code + "')>" + ui.rowData.year + "</a>";
					}
				}
				},
				{
					display: '指标编码', name: 'index_code', align: 'left', width: 100, editable: false,
				},
				{
					display: '指标名称', name: 'index_name', align: 'left', width: 120, editable: false,
				},
				{
					display: '01月(元/E)', name: 'm01', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m01, 2, 1);
					}
				},
				{
					display: '02月(元/E)', name: 'm02', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m02, 2, 1);
					}
				},
				{
					display: '03月(元/E)', name: 'm03', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m03, 2, 1);
					}
				},
				{
					display: '04月(元/E)', name: 'm04', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m04, 2, 1);
					}
				},
				{
					display: '05月(元/E)', name: 'm05', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m05, 2, 1);
					}
				},
				{
					display: '06月(元/E)', name: 'm06', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m06, 2, 1);
					}
				},
				{
					display: '07月(元/E)', name: 'm07', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m07, 2, 1);
					}
				},
				{
					display: '08月(元/E)', name: 'm08', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m08, 2, 1);
					}
				},
				{
					display: '09月(元/E)', name: 'm09', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m09, 2, 1);
					}
				},
				{
					display: '10月(元/E)', name: 'm10', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m10, 2, 1);
					}
				},
				{
					display: '11月(元/E)', name: 'm11', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m11, 2, 1);
					}
				},
				{
					display: '12月(元/E)', name: 'm12', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m12, 2, 1);
					}
				}
			],
			usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
			addRowByKey: false, inWindowHeight: true, freezeCols: 3,
			toolbar: {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					{ type: "button", label: '增量生成', icon: 'plus', listeners: [{ click: generate }] },
					{ type: "button", label: '添加', icon: 'plus', listeners: [{ click: add_open }] },
					{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
					{ type: "button", label: '下载导入模板', icon: 'arrowthickstop-1-s', listeners: [{ click: downTemplate }] },
					{ type: "button", label: '导入', icon: 'arrowthick-1-n', listeners: [{ click: imp }] },
					{ type: "button", label: '汇总', icon: 'calculator', listeners: [{ click: collect }] },
					{ type: "button", label: '分解计算', icon: 'arrow-4', listeners: [{ click: resolveDataDown}] },
					{ type: "button", label: '预算分解维护', icon: 'folder-open', listeners: [{ click: openRatePage }] },
					{ type: "button", label: '打印', icon: 'script', listeners: [{ click: printData }] },
				]
			}
  		});
    }
   
    function add_open(){
		$.etDialog.open({ url : 'budgWorkHosMonthAddPage.do?isCheck=false',height: 300,width: 600, 
			title:'医院月份业务预算添加',
			btn : ['确定','取消'],
			btn1 : function(index,el){
				var iframeWindow = window[el.find('iframe').get(0).name];
				iframeWindow.saveBudgWorkHosMonth();
			}
    	}); 
    }
    
    function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var ParamVo = [];//后台删除数据
			var deletePageRow = [];// 页面删除数据
			$(data).each(function () {
				if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.year + "@" +
						this.rowData.index_code
					)
				} else {
					deletePageRow.push(this);
				}

			});
			$.etDialog.confirm('确定删除?', function (yes) {
				if (yes) {
					if (ParamVo.length > 0) {
						ajaxPostData({
	         			    url: 'deleteBudgWorkHosMonthDown.do?isCheck=false',
	         			    data: {ParamVo : ParamVo.toString()},
	         			    success: function (responseData) {
	         			    	query();
	         			    }
	         			})
					} else if (deletePageRow.length > 0) {
						grid.deleteRows(deletePageRow);
						$.etDialog.success("删除成功!");
					}
				}
			});
		}
	}
    
    function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgWorkHosMonthImportPage.do?isCheck=false'
		});
		layer.full(index);
    }	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }
    
    //医院月份业务预算分解维护
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code=" + vo[2] + "&year=" + vo[3] + "&index_code=" + vo[4] 

		parent.$.ligerDialog.open({
			url: 'hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthUpdatePageDown.do?isCheck=false&' + parm, data: {}, height: 500, width: 700,
			title: '医院月份业务预算分解维护', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,
		});
	}
	
	//医院月份业务预算分解维护
	function openRatePage() {
		var year = year_input.getValue();
		index_code = index_code_select.getValue();
		if(!year) {
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		if(!index_code) {
			$.etDialog.error('预算指标不能为空');
			return false ;
		}
		var parm = "year=" + year + "&index_code=" + index_code
		parent.$.ligerDialog.open({
			url: 'hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthUpdatePageDown.do?isCheck=false&' + parm, data: {}, height: 500, width: 700,
			title: '医院月份业务预算分解维护', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,
		});
	}
    
    //修改保存
	function save() {
		var data = grid.getChanges();
		if (data.updateList.length == 0) {
			$.etDialog.error('没有需要保存的数据');
		} else {
			var ParamVo = [];
			$(data.updateList).each(function () {
				ParamVo.push(this.year + "@" + this.index_code + "@" + "01" + "@" + (this.m01 ? this.m01 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "02" + "@" + (this.m02 ? this.m02 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "03" + "@" + (this.m03 ? this.m03 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "04" + "@" + (this.m04 ? this.m04 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "05" + "@" + (this.m05 ? this.m05 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "06" + "@" + (this.m06 ? this.m06 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "07" + "@" + (this.m07 ? this.m07 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "08" + "@" + (this.m08 ? this.m08 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "09" + "@" + (this.m09 ? this.m09 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "10" + "@" + (this.m10 ? this.m10 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "11" + "@" + (this.m11 ? this.m11 : "-1"));
				ParamVo.push(this.year + "@" + this.index_code + "@" + "12" + "@" + (this.m12 ? this.m12 : "-1"));
			})
			ajaxPostData({
			    url: 'updateBudgWorkHosMonthDown.do?isCheck=false',
			    data: {ParamVo : ParamVo.toString()},
			    success: function (responseData) {
			    	query();
			    }
			})
		}
	}
    
	// 汇总
    function collect(){
		var year = year_input.getValue() ;
		if( !year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		ajaxPostData({
			url:"collectBudgWorkHosMonthDown.do?isCheck=false&year="+year,
		    data:'',
		    success:function(responseData){
		    	query();
		    }
		})
   }
   // 分解计算
   function resolveDataDown() {
		var year = year_input.getValue();
		var index_code = index_code_select.getValue() ;
		if (!year) {
			$.etDialog.error('预算年度不能为空');
			return false;
		}
		if(!index_code){
			$.etDialog.error('预算指标不能为空');
			return false;
		}
		ajaxPostData({
			url:"resolveDataDown.do?isCheck=false&year=" + year+"&index_code="+index_code,
		    data:{},
		    success:function(responseData){
		    	query();
		    }
		})
	}
    //打印
	//budgWorkHosMonthService.query
	function printData(){
   	if(grid.getAllData()==null){
   		$.etDialog.error("请先查询数据！");
			return;
		}
   	var heads = {};
   	
   	var printPara={
     		title: "医院月份业务预算",//标题
     		columns: JSON.stringify(grid.getPrintColumns()),//表头
     		class_name: "com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosMonthService",
  			method_name: "getPrintData",
  			bean_name: "budgWorkHosMonthService",
  			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
  			foots: ''//表尾需要打印的查询条件,可以为空 
      	};
		$.each(parms, function (i, obj) {
			printPara[obj.name] = obj.value;
		});
      	officeGridPrint(printPara);
	}
    
	//增量生成
    function generate(){
	   	var year = year_input.getValue();
	   	if(year){
	   		ajaxPostData({
			    url: "generate.do?isCheck=false&year="+year,
			    data: "",
			    success: function (responseData) {
			    	query();
			    }
			})
	   	}else{
	   		$.etDialog.error("预算年度不能为空");
	   	}
    }
	
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('G', generate);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('S', save);
		hotkeys('P', printData);
		hotkeys('I', imp);
		hotkeys('C', collect);
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="ipt"></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
