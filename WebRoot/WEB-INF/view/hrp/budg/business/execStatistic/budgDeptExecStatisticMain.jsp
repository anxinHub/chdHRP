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
	<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
  //设置单元格打印格式
    var printData={
   		sum_year : function(value){ //
			return formatNumber(value, 2, 1);
		},  
   		month1 : function(value){ //一月份的预算值
			return formatNumber(value, 2, 1);
		},
		month2 : function(value){ //二月份的预算值
			return formatNumber(value, 2, 1);
		},
		month3 : function(value){ //三月份的预算值
			return formatNumber(value, 2, 1);
		},
		month4 : function(value){ //四月份的预算值
			return formatNumber(value, 2, 1);
		},
		month5 : function(value){ //五月份的预算值
			return formatNumber(value, 2, 1);
		},
		month6 : function(value){ //六月份的预算值
			return formatNumber(value, 2, 1);
		},
		month7 : function(value){ //七月份的预算值
			return formatNumber(value, 2, 1);
		},
		month8 : function(value){ //八月份的预算值
			return formatNumber(value, 2, 1);
		},
		month9 : function(value){ //九月份的预算值
			return formatNumber(value, 2, 1);
		},
		month10 : function(value){ //十月份的预算值
			return formatNumber(value, 2, 1);
		},
		month11 : function(value){ //十一月份的预算值
			return formatNumber(value, 2, 1);
		},
		month12 : function(value){ //十二月份的预算值
			return formatNumber(value, 2, 1);
		}
	};
    $(function (){
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
    var year_input,index_code_select,dept_id_select;
    
    function init() {
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: true
        });

		
		index_code_select = $("#index_code_select").etSelect({
			defaultValue:"none",
			url:"../../queryBudgIndexAccumulationDict.do?isCheck=false",
			onChange:query
		});
		
		dept_id_select = $("#dept_id_select").etSelect({
			defaultValue:"none",
			url:"../../queryBudgDeptDict.do?isCheck=false",
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
    function  query(){
    	if( !year_input.getValue()){
       		$.etDialog.error('预算年度不能为空');
       		return false ;
       	}
    	var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
		];
		//加载查询条件
		grid.loadData(parms, 'queryDeptExecStatisticData.do?isCheck=false');
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
    		columns: [ 
              	{ display: '年度', name: 'year', align: 'left',width:90,frozen :true,
				 		},
				{ display: '月份', name: 'month', align: 'left',width:100,frozen :true,
						},
                { display: '指标编码', name: 'index_code', align: 'left',width:100,frozen :true,
				 		},
			    { display: '指标名称', name: 'index_name', align: 'left',width:120,frozen :true,
				 		},
				{ display: '科室编码', name: 'dept_code', align: 'left',width:100,frozen :true,
				 		},
			    { display: '科室名称', name: 'dept_name', align: 'left',width:120,frozen :true,
				 		},
				{ display: '本月预算', name: 'budg_value', align: 'right',width:100,frozen :true,
				 			render:function(ui){
			    				 var value = ui.cellData;
			    				 return (value==null?'': formatNumber(value,2,1));
				 			}
						},
				{ display: '本月执行', name: 'execute_value', align: 'right',width:100,frozen :true,
							render:function(ui){
			    				 var value = ui.cellData;
			    				 return (value==null?'': formatNumber(value,2,1));
				 			}
						},
						
				{ display: '本月执行率', name: 'month_execute_rate', align: 'right',width:100,frozen :true,
							render:function(ui){
			    				 var value = ui.cellData;
			    				 return (value==null?'': formatNumber(value*100,2,1)+'%');
				 			}
						},
				{ display: '本年预算', name: 'year_budg_value', align: 'right',width:100,frozen :true,
							render:function(ui){
			    				 var value = ui.cellData;
			    				 return (value==null?'': formatNumber(value,2,1));
				 			}
						},
				{ display: '累计执行', name: 'year_execute_value', align: 'right',width:100,frozen :true,
							render:function(ui){
			    				 var value = ui.cellData;
			    				 return (value==null?'': formatNumber(value,2,1));
				 			}
						},
						
				{ display: '累计执行率', name: 'year_execute_rate', align: 'right',width:100,frozen :true,
							render:function(ui){
			    				 var value = ui.cellData;
			    				 return (value==null?'': formatNumber(value*100,2,1)+'%');
				 			}
						}
    		],
				
             dataMedol: {
  	         	method: 'POST',
  	         	location: 'remote',
  	         	url: '',
  	         	recIndx: 'year'
             },
             usePager: false,
             width: '100%',
             height: '100%',
             checkbox: true,
             freezeCols: 5,
             toolbar: { 
               	  items: [
               		{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
       		      ]
             },
        });
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
	//打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=printData;
 		grid.options.lodop.title="医院业务执行监控";
    }
 	//本合计年
 	function openUpdateSum(obj){
 		var vo = obj.split("!");
 		var parm = 	
 			"year="+vo[0]   +"&"+ 
 			"index_code="+vo[1]   +"&"+ 
 			"sum_year="+vo[2] 
 		$.etDialog.open({
            url: 'budgWorkHosExecuteLinkPageSum.do?isCheck=false&'+parm,
            title: '预算值链接',
            isMax: true
        });
 	}
    //点击月份的超链接
    function  openUpdate(obj){
		var vo = obj.split("!");
		var parm = 	
			"year="+vo[0]   +"&"+ 
			"index_code="+vo[1]   +"&"+ 
			"month="+vo[2]
		$.etDialog.open({
	          url: 'budgWorkHosExecuteLinkPage.do?isCheck=false&'+parm,
	          title: '预算值链接',
	          isMax: true
        });
    }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
				<td class="label">预算科室：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
