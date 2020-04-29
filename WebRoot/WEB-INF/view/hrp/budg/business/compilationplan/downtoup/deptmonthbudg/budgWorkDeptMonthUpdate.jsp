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
    var gridManager = null;
    var userUpdateStr;
    var flag = 0;
    var index_code ="${index_code}"
    var parentFrameName = parent.$.etDialog.parentFrameName;
    var parentWindow = parent.window[parentFrameName];
    var year_input,month_input,index_code_select,dept_id_select;
	var parms=[];
    $(function (){
    	//加载数据
    	init();
    	loadHead(null);	
		loadHotkeys();
    });
    function init(){
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function (value) {
            	index_code_select.setValue('');
            	reloadSubjName(value);
            },
            defaultDate: true
        });
	    month_input = $("#month_input").etDatepicker({
			view:'months',
			minView:'months',
			dateFormat:"mm",
			todayButton:false,
			showNav:false
		});
		index_code_select = $("#index_code_select").etSelect({
			defaultValue: "none"
		});
		reloadSubjName(year_input.getValue());
		function reloadSubjName(value){
			index_code_select.reload({
				url:"../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=03",
				para:{
					budg_year:value
				}
			})
		};

		dept_id_select = $("#dept_id_select").etSelect({
			url:"../../../../queryBudgDeptDict.do?isCheck=false",
			defaultValue:"none"
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

    	parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
			{ name: 'month', value: month_input.getValue() }
		];
		//加载查询条件
		grid.loadData(parms, 'queryCollectData.do?isCheck=false');
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                     { display: '年度', name: 'year', align: 'left',width:60,
					 		},
					 { display: '月份', name: 'month', align: 'left',width:40,
					 		},
					 { display: '科室编码', name: 'dept_code', align: 'left',width:100,
					 		},
					 { display: '科室名称', name: 'dept_name', align: 'left',width:120,
					 		},
                     { display: '指标编码', name: 'index_code', align: 'left',width:100,
					 		},
				 	 { display: '指标名称', name: 'index_name', align: 'left',width:120,
					 		},
				 	 { display: '科室年度预算(元)', name: 'yearValue', align: 'right',width:120,
					 			render:function(ui) {
									if (ui.rowData.yearValue) {
										return formatNumber(ui.rowData.yearValue, 2, 1);
									}
								}
						 },
					 { display: '上年业务量', name: 'last_year_workload', align: 'right',width:120,
							 render:function(ui) {
								if (ui.rowData.last_year_workload) {
									return formatNumber(ui.rowData.last_year_workload, 2, 1);
								}
							 }
					 	},
					 { display: '增长比例(E)', name: 'grow_rate', align: 'center',width:80,dataType: "float",
							 	render:function(ui) {
									if (ui.rowData.grow_rate) {
										return formatNumber(ui.rowData.grow_rate, 2, 1);
									}
								}
					 		},
                  	 { display: '分解比例(E)', name: 'resolve_rate', align: 'center',width:80,dataType: "float",
					 			render:function(ui) {
									if (ui.rowData.resolve_rate) {
										return formatNumber(ui.rowData.resolve_rate, 2, 1);
									}
								}
					 		},
                     { display: '计算值', name: 'count_value', align: 'right',width:120,
					 			render:function(ui) {
									if (ui.rowData.count_value) {
										return formatNumber(ui.rowData.count_value, 2, 1);
									}
								}
					 		},
                     { display: '预算值(E)', name: 'budg_value', align: 'right',width:120,dataType: "float",
					 			render:function(ui) {
									if (ui.rowData.budg_value) {
										return formatNumber(ui.rowData.budg_value, 2, 1);
									}
								}
					 		},
                     { display: '说明', name: 'remark', align: 'left',width:200
					 		}
                     ],
     				url: 'queryCollectData.do?isCheck=false',
     				usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
    				addRowByKey: true,delayLoad:true,
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
    						{ type: "button", label: '添加', icon: 'plus', listeners: [{ click: add_open }] },
    						{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
    						{ type: "button", label: '计算', icon: 'calculator', listeners: [{ click: collect }] },
    						{ type: "button", label: '打印', icon: 'script', listeners: [{ click: printData }] },
    					]
    				}
         });

    }
    
	function add_open(){
		$.etDialog.open({ url : 'budgWorkDeptMonthAddPage.do?isCheck=false&',data:{},height: 300,width: 600,  
			title:'科室月份业务预算添加',btn : ['确定','取消'],
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
				this.dept_id  +"@"+ 
				(this.grow_rate? this.grow_rate:"-1") + "@" + 
				(this.resolve_rate? this.resolve_rate:"-1") + "@" + 
				(this.count_value? this.count_value:"-1") + "@" + 
				(this.budg_value? this.budg_value:"-1") + "@" + 
				(this.remark? this.remark:"-1") + "@" + 
				(this.last_year_workload? this.last_year_workload:"-1")
			  );
	    })
	    ajaxPostData({
		    url: 'updateBudgWorkDeptMonthRate.do?isCheck=false',
		    data: {ParamVo : ParamVo.toString()},
		    success: function (responseData) {
		    	query();
		    }
		})
	}
	//计算
    function collect(){
		var year = year_input.getValue() ;
		var index_code = index_code_select.getValue() ;
			   
		if( !year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		if( !index_code){
			$.etDialog.error('预算指标不能为空');
			return false ;
		}
		ajaxPostData({
		    url: "collectBudgWorkDeptMonthDown.do?isCheck=false&year="+year+"&index_code="+index_code,
		    data: "",
		    success: function (responseData) {
		    	query();
		        parentWindow.query();
		    }
		})
   }
	//打印
	function printData(){
    	if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads = {};
    	
    	var printPara={
    			
      		title: "科室月份业务预算分解维护信息",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptMonthService",
   			method_name: "getUpdatePrintData",
   			bean_name: "budgWorkDeptMonthService",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
    	printPara['year']=year_input.getValue();
    	printPara['index_code']=index_code_select.getValue();
    	printPara['dept_id']=dept_id_select.getValue();
    	printPara['month']=month_input.getValue();
       	officeGridPrint(printPara);
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', printData);
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
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;"></select>
				</td>
				<td class="label">月份： </td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
