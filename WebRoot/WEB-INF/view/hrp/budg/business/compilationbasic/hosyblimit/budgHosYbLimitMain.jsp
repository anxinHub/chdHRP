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
		pay_limit: function (value) {
			return formatNumber(value, 2, 1);
		},
		rate: function (value) {
			return formatNumber(value, 2, 1) + "%";
		},
		control_limit: function (value) {
			return formatNumber(value, 2, 1);
		}
	};
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	var year_input,insurance_code_select;

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
			{ name: 'insurance_code', value: insurance_code_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,"queryBudgHosYbLimit.do?isCheck=false");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left', width:"10%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'year',
						url: '../../../queryBudgYearTen.do?isCheck=false',
					},
				},
				{display: '医保类型', name: 'insurance_name', align: 'left', width:"20%",editable:setEdit,
					editor: {
						type: 'select',
						keyField: 'insurance_code',
						url : '../../../queryBudgYBType.do?isCheck=false',
					}
				},
				{display: '本年支付额度(元)(E)', name: 'pay_limit', align: 'right', width: "15%", dataType:"float",
					editor: {change: setControlLimit},
					render:function(ui){
						if (ui.rowData.pay_limit) {
							return formatNumber(ui.rowData.pay_limit, 2, 1);
						}
					}
				},
				{display: '控线增长比率(E)', name: 'rate', align: 'center', width:"10%", dataType:"float",
					editor: { change: setControlLimit },
					render:function(ui){
						if (ui.rowData.rate) {
							return formatNumber(ui.rowData.rate, 2, 1) + "%";
						}
					}
				},
				{display: '本年控制额度(元)', name: 'control_limit', align: 'right', width: "15%",editable:false,
					render:function(ui){
						if (ui.rowData.control_limit) {
							return formatNumber(ui.rowData.control_limit, 2, 1);
						}
					}
				},
				{display: '备注(E)', name: 'remark', align: 'left', dataType:"string",width:"27%" }
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
				/* { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: impNew}] }, */
				]
			},
		});
	}

	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "全院医保额度控制";
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
    
	//填写、修改 本年支付额度、控线长量比率 后  更新 本年控制额度单元格
	function setControlLimit(rowdata,celldata) {
		// 如果 
		if (rowdata.rate && rowdata.pay_limit) {
			if (rowdata.rate >= 0 && rowdata.rate <= 100) {
				var control_limit = Number(rowdata.pay_limit) * (1 + Number(rowdata.rate) / 100);
				grid.updateRow(celldata.rowIndx,{control_limit:control_limit})
			} else {
				$.etDialog.error('控线长量比率必须在0-100之间');
			}
		}
	}

	//计算
	function collectControlLimit() {
		var data = grid.selectGet();

		if (data.length == 0) {
			$.etDialog.error('请选择行数据');
		} else {
			var ParamVo = [];
			$(data).each(function () {
				if (!this.pay_limit) {
					$.etDialog.error('本年支付额度不能为空,且必须位数字');
					return false;
				}
				if (!this.rate) {
					$.etDialog.error('控线增长比率不能为空,且必须位数字');
					return false;
				}
				ParamVo.push(
					this.group_id + "@" +
					this.hos_id + "@" +
					this.copy_code + "@" +
					this.year + "@" +
					this.insurance_code + "@" +
					this.pay_limit + "@" +
					this.rate + "@" +
					Number(this.pay_limit) * (1 + Number(this.rate) / 100) + "@" +
					(this.remark ? this.remark : "-1")
			    )
			})
			ajaxPostData({
                url: "updateBudgHosYbLimit.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		}
	}

	function add_open() {
		$.etDialog.open({
			url: 'budgHosYbLimitAddPage.do?isCheck=false',
			height: 300, width: 450, title: '全院医保额度控制', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.savebudgHosYbLimit()
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
		                    url: "deleteBudgHosYbLimit.do?isCheck=false",
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
			content: 'budgHosYbLimitImportPage.do?isCheck=false'
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
			url: 'budgHosYbLimitUpdatePage.do?isCheck=falseparm=' + parm,
			height: 300, width: 450, title: '全院医保额度控制', 
			btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.savebudgHosYbLimit()
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
               			item.pay_limit + "@" +
               			item.rate + "@" +
               			(item.control_limit?item.control_limit:"-1")   	+"@"+  
               			(item.remark ? item.remark : "-1") 	+"@"+ 
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
             			item.insurance_code   +"@"+ 
             			item.pay_limit + "@" +
             			item.rate + "@" +
             			(item.control_limit?item.control_limit:"-1")   	+"@"+  
             			(item.remark ? item.remark : "-1") 	+"@"+ 
       					//行号 提示错误信息用
       					item._rowIndx +"@"+
       					"2" //修改标识
 	   				) 
 	   			});
         	}
 			ajaxPostData({
                url: "saveBudgHosYbLimit.do?isCheck=false",
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
			if (!v.insurance_name) {
				rowm+="[医保类型]、";
			}
			if (!v.control_limit) {
				rowm+="[本年控制额度]、";
			}
			if (!v.pay_limit) {
				rowm+="[本年支付额度]、";
			}
			if (!v.rate) {
				rowm+="[控线增长比例]、";
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
				<td class="label">类型编码：</td>
				<td class="ipt">
					<select name="" id="insurance_code_select" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="label"></td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>
</body>
</html>