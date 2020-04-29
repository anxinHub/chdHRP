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
    var index_code ='${index_code}' ;
    var parms=[];
    $(function (){
    	//加载数据
    	init();
    	loadHead(null);	
		loadHotkeys();
    });
    
    var year_input,index_code_select;
    function init(){
		getData("../../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: "${year}",
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onChanged:function (value) {
					query();
				}
			});
		});
	    
		index_code_select = $("#index_code_select").etSelect({
			defaultValue: index_code,
			url : "../../../../queryBudgIndexDict.do?isCheck=false",
			onChange: function (value) {
				query();
			}
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
		})
	};
    
	//查询
    function  query(){
    	if(!year_input.getValue()){
    		$.etDialog.error('预算年度不能为空');
    		return false ;
    	}
    	if(!index_code_select.getValue()){
    		$.etDialog.error('预算指标不能为空');
    		return false ;
    	}
    	parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
		];
		//加载查询条件
		grid.loadData(parms, 'queryResolveDataDown.do?isCheck=false');
     }
    function loadHead(){
    	grid = $("#maingrid").etGrid({
       	   columns: [ 
                 { display: '年度', name: 'year', align: 'left',width:'5%',
				 		},
				 { display: '月份', name: 'month', align: 'left',width:'3%',
				 		},
                    { display: '指标编码', name: 'index_code', align: 'left',width:'8%',
				 		},
			 	 { display: '指标名称', name: 'index_name', align: 'left',width:'10%',
				 		},
			 	 { display: '医院年度预算(元)', name: 'yearValue', align: 'right',width:'10%',
				 			render:function(ui) {
								if (ui.rowData.yearValue) {
									return formatNumber(ui.rowData.yearValue, 2, 1);
								}
							}
					 },
				 { display: '上年业务量', name: 'last_year_workload', align: 'right',width:'10%',
						 render:function(ui) {
							if (ui.rowData.last_year_workload) {
								return formatNumber(ui.rowData.last_year_workload, 2, 1);
							}
						 }
				 	},
				 { display: '增长比例(E)', name: 'grow_rate', align: 'center',width:'8%',dataType: "float",
						 	render:function(ui) {
								if (ui.rowData.grow_rate) {
									return formatNumber(ui.rowData.grow_rate, 2, 1);
								}
							}
				 		},
               	 { display: '分解比例(E)', name: 'resolve_rate', align: 'center',width:'8%',dataType: "float",
				 			render:function(ui) {
								if (ui.rowData.resolve_rate) {
									return formatNumber(ui.rowData.resolve_rate, 2, 1)+'%';
								}
							}
				 		},
                 { display: '计算值', name: 'count_value', align: 'right',width:'9%',
			 			render:function(ui) {
							if (ui.rowData.count_value) {
								return formatNumber(ui.rowData.count_value, 2, 1);
							}
						}
			 		},
                 { display: '预算值(E)', name: 'budg_value', align: 'right',width:'9%',dataType: "float",
			 			render:function(ui) {
							if (ui.rowData.budg_value) {
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}
						}
			 		},
                 { display: '说明', name: 'remark', align: 'left',width:'17%'
			 		}
            ],
            dataModel: {
				method: 'POST',
				location: 'remote',
				url: '',
				recIndx: 'year'
			},
			usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
			addRowByKey: true,
			load : function(){
				grid.refreshSummary();
			},
			summary : {
				totalColumns:['resolve_rate','count_value','budg_value'],
				keyWordCol : 'year'
			},
			toolbar: {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					/* { type: "button", label: '添加', icon: 'plus', listeners: [{ click: add_open }] },
					{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] }, */
					{ type: "button", label: '计算', icon: 'calculator', listeners: [{ click: resolveDataDown }] },
					{ type: "button", label: '打印', icon: 'script', listeners: [{ click: printData }] },
				]
			}
        });
    }
    
	function add_open(){
		$.etDialog.open({ url : 'budgWorkDeptMonthAddPage.do?isCheck=false&',data:{}, height: 300,width: 600, 
			title:'科室月份业务预算添加',
			btn1 : function(index,el){
				var iframeWindow = window[el.find('iframe').get(0).name];
				iframeWindow.saveBudgWorkDeptMonth();
			}
    	}); 
    }
  
	function save(){
		var data = grid.getChanges();
    	
		if( data.updateList.length == 0 ){
    		$.etDialog.error('没有需要保存的数据');
    		return;
    	}
		var ParamVo =[]; 
	 	$(data.updateList).each(function (){
			  ParamVo.push( 
				this.year +"@"+ 
				this.index_code +"@"+ 
				this.month +"@"+ 
				(this.grow_rate? this.grow_rate:"-1") + "@" + 
				(this.resolve_rate? this.resolve_rate:"-1") + "@" + 
				(this.count_value? this.count_value:"-1") + "@" + 
				(this.budg_value? this.budg_value:"-1") + "@" + 
				(this.remark? this.remark:"-1") + "@" + 
				(this.last_year_workload? this.last_year_workload:"-1")
			  );
	    })
	    ajaxPostData({
		    url: 'updateBudgWorkHosMonthRateDown.do?isCheck=false',
		    data: {ParamVo : ParamVo.toString()},
		    success: function (responseData) {
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
		    url: "resolveDataDown.do?isCheck=false&year="+year+"&index_code="+index_code,
		    data: "",
		    success: function (responseData) {
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
      		title: "医院月份业务预算分解维护信息",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosMonthService",
   			method_name: "getResolvePrintData",
   			bean_name: "budgWorkHosMonthService",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
		$.each(parms, function (i, obj) {
			printPara[obj.name] = obj.value;
		});
       	officeGridPrint(printPara);
    }
  
   //键盘事件
   function loadHotkeys() {
	 hotkeys('Q', query);
	 hotkeys('A', add_open)
	 hotkeys('P', printData);
	 hotkeys('C', resolveDataDown);
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
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
