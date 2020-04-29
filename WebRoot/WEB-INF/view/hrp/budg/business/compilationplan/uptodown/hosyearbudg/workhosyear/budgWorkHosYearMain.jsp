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
	<jsp:param value="select,datepicker,ligerUI,grid,pageOffice" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    //查询
    function  query(){
    	var params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() }
		]
		//加载查询条件
		grid.loadData(params,"");
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                     { display: '年度', name: 'year', align: 'left',width:"10%",
					 		},
                     { display: '指标编码', name: 'index_code', align: 'left',width:"15%",
					 		},
					 { display: '指标名称', name: 'index_name', align: 'left',width:"15%",
					 		},
					 { display: '上年业务量', name: 'last_year_workload', align: 'right',width:"15%",
					 			render:function(ui) {
									if (ui.rowData.last_year_workload) {
										return formatNumber(ui.rowData.last_year_workload, 2, 1);
									}else{
										return formatNumber(0, 2, 1);
									}
								}
					 		},
                     { display: '预算值', name: 'budg_value', align: 'right',width:"15%",
					 			render:function(ui) {
									if (ui.rowData.budg_value) {
										return formatNumber(ui.rowData.budg_value, 2, 1);
									}else{
										return formatNumber(0, 2, 1);
									}
								}
					 		},
                     { display: '说明', name: 'remark', align: 'left',width:"25%",
					 		}
                     ],
                     dataModel:{
 	       	           	method:'POST',
 	       	           	location:'remote',
 	       	           	url:'queryBudgWorkHosYearUp.do?isCheck=false',
 	       	           	recIndx: 'year'
 	       	         },
 	       	         usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
 	       	         addRowByKey:true,
 	       	     	 toolbar: {
		               items: [
				           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
				           	{ type: "button", label: '打印',icon:'script',listeners: [{ click: printDate}] },
							{ type: "button", label: '科室汇总',icon:'contact',listeners: [{ click: sumDeptSuggest}] },
		           	 ]}
        });
    }
   
    
    function printDate(){
    	if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads = {};
    	
    	var printPara={
      		title: "医院年度业务预算表",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosYearService",
   			method_name: "getPrintData",
   			bean_name: "budgWorkHosYearService",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
    	printPara['year']=year_input.getValue();
    	printPara['index_code']=index_code_select.getValue();
       	officeGridPrint(printPara);
    }
    
    
    
    function sumDeptSuggest(){
    	var year = year_input.getValue();
    	var data = grid.selectGet();
    	if(year){
    		if (data.length == 0) {
                $.etDialog.error('请选择行');
            }else{
            	var index_codes = '';
            	$(data).each(function () {
                    var rowdata = this.rowData;
                    index_codes += rowdata.index_code+',';
                });
            	ajaxJsonObjectByUrl("sumDeptSuggest.do?isCheck=false",{year:year,index_codes:index_codes},function (responseData){
        			if(responseData.state=="true"){
        				query();
        			}
        		});
            }
    	}else{
    		$.etDialog.error("预算年度不能为空");
    	}
    }
    
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


    	index_code_select = $("#index_code_select").etSelect({
			url: "../../../../../queryBudgIndexDict.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		});
		function reloadSubjName(value){
			index_code_select.reload({
				url:"../../../../../queryBudgIndexDict.do?isCheck=false",
				para:{
					budg_year:value
				}
			})
		}
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
    
    
    //键盘事件
  	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', sumDeptSuggest);
		hotkeys('P', printDate);
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
