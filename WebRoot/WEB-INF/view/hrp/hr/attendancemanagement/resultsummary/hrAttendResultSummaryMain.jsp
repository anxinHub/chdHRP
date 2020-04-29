<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param
		value="hr,dialog,datepicker,select,validate,grid,dateSlider,characterList,tree,tab,from,pageOffice"
		name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">
	var year_month, dept_code, emp_code, state;
	var jsonHead, selectSql, detailData;
	var items = {};
	$(function () {
		initDict();
		leftGrid();
		genegrateMonthsColumns();
		query();
	});

	function initDict() {
		/* 考勤周期 */
		year_month = $("#year_month").etDatepicker({
			view: "months",
			minView: "months",
			dateFormat: "yyyy-mm",
			defaultDate: true,
			onChange: function(value) {
				genegrateMonthsColumns()
			}
		});
		kind_code= $("#kind_code").etSelect({
			url: '../../queryHrEmpKindSelect.do?isCheck=false',
			defaultValue: "none",
			/* checkBox:true, */
			checkboxMode: true,
			onChange:  function(value) {
		    	//query();
		    }
		});
		
	};

	/* 左表 基础列头 */
	var leftcolumns = [
		{display : '考勤周期',name : 'year_month',width : 70,editable: false},
		{display : '出勤科室',name : 'dept_name',width : 100,editable: false}/*,
		{display : '职工编码',name : 'emp_code',width : 60,editable: false},
		{display : '职工名称',name : 'emp_name',width : 60,editable: false},
		{display : '编制科室',name : 'dept_name_b',width : 100,editable: false},
		{display : '医护属性',name : 'yh_name',width : 100,editable: false},
		{display : '应出勤天数',name : 'attend_date',width : 60,editable: false} */
	];
	var rightcolumns = [
		//{display : '加班小时数',name : 'add_date',width : 60,editable: false},
		//{display : '积休天数',name : 'jixiu_date',width : 60,editable: false},
		//{display : '实际出勤天数',name : 'result_date',width : 80,editable: false},
		{display : '总人数',name : '',width : 60,editable: false,columns: [  { display: '医生', name: 'doct', align:'right',width: 100 },
		                 											   { display: '护士', name: 'nurse', align:'right',width: 100 },
		                 											    { display: '其他', name: 'other', align:'right',width: 100 },
		                 											  { display: '合计', name: 'countall', align:'right',width: 100},
		                 											    ]
		
		}
	];
    
	/* 生成日期的列 */
	var genegrateMonthsColumns = function() {
		var columns = [];
		var date = year_month.getValue().replace("-", "");
		ajaxPostData({
			url: '../resultmanage/queryAttendResultManageHead.do?isCheck=false',
			async: false,	  
			success: function (data) {
				if(data.state == "true"){
					jsonHead = eval(data.jsonHead);
					selectSql = data.selectSql;
					$.each(jsonHead,function(index, v){
						if(v.name){
							items[v.name] = {attend_code: v.attend_code, attend_types: v.attend_types};
							columns.push({
								display : v.display,
								name : v.name,
								width : 50,
								align: "right",  
								columns: [  { display: '医生', name: 'doct'+v.name.substr(4), align:'right',width: 100 },
											   { display: '护士', name: 'nurse'+v.name.substr(4), align:'right',width: 100 },
										    { display: '其他', name: 'other'+v.name.substr(4), align:'right',width: 100 },]	
							})
						}
					});
				}
				  
				var leftNewColumns = leftcolumns.concat(columns);
				var newColumns = leftNewColumns.concat(rightcolumns);
				leftGrid.option('columns', newColumns);
				leftGrid.refreshView();
			},
		});
	};
		
	var leftGrid = function() {
		 // 基础表格参数
	    var toolbar = {
	        items: [
	            { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
	            { type: "button", label: '打印', icon: 'print', listeners: [{ click: printData }] },
	        ]
	    };
		var paramObj = {	
				height: '100%',
                inWindowHeight: true,
                checkbox: true,
                pageModel: false,

                showBottom: false,
               columns : leftcolumns,
               toolbar: {
                   items: [

           	            { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
           	            { type: "button", label: '打印', icon: 'print', listeners: [{ click: printData }] },

                   ]
               }
	      //  freezeCols : 5, //冻结两列
			/* pageModel: {
                type: 'remote',//local前台分页
            }, */
		};
		leftGrid = $("#maGrid").etGrid(paramObj);
	};
	
	/* //单元格双击事件
	function cellDblClick(event, ui){
		//是否为考勤项列
		var col_name = ui.column.dataIndx;
	   	if(items[col_name] && (items[col_name].attend_types == "02" || items[col_name].attend_types == "03")){
	   		if(ui.rowData[col_name] > 0){
	   			//弹出页面
	   			detailData = {
   					year_month:  ui.rowData.year_month, 
   					dept_id_c: ui.rowData.dept_id_c, 
   					dept_name_c: ui.rowData.dept_name_c, 
   					emp_id: ui.rowData.emp_id, 
   					emp_name: ui.rowData.emp_code + " " + ui.rowData.emp_name, 
   					attend_code: items[col_name].attend_code, 
	   			};
	   			var url, title;
				if(items[col_name].attend_types == "02"){
					url = "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageJbPage.do?isCheck=false";
					title = "加班记录";
				}else{
					url = "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageXjPage.do?isCheck=false";
					title = "休假记录";
				}
				
				parent.$.etDialog.open({
					url: url,
					width: 700, 
					height: 400,
					frameName: window.name,
					title: title
				});
	   		}
	   	}
	   	
	   	return true;
	} */
	
	//查询
	var query = function () {
		
		var params = [
			{name: 'year_month', value: year_month.getValue().replace("-", "") },
			{name: 'kind_code', value: kind_code.getValue() },
			{name: 'selectSql', value: selectSql }
		]
        leftGrid.loadData(params, "queryAttendSummary.do");
    };
    

    //打印
	function printData(){	
		if(leftGrid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads = {};
    	
    	var printPara={
      		title: "考勤汇总",//标题
      		columns: JSON.stringify(leftGrid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultSummaryService",
   			bean_name: "hrAttendResultSummaryService",
   			method_name: "printAttendSummary",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
    	
       	$.each(leftGrid.getUrlParms(),function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	officeGridPrint(printPara);
	}

</script>
<body>
	<div class="container">
		<div class="center" style="padding: 0">
			<table class="table-layout">
				<tr>
					<td class="label">考勤周期：</td>
					<td><input name="year_month" type="text" id="year_month"
						style="width: 90px" /></td>

				 <td class="label">职工分类：</td>
					<td><select id="kind_code" style="width: 150px"></select></td>

					<!--	<td class="label">职工名称：</td>
					<td><select id="emp_code" style="width: 150px"></select></td>

					<td class="label">状态：</td>
					<td><select id="state" style="width: 90px"></select></td> -->
				</tr>
			</table>

			<div id="maGrid"></div>
		</div>
	</div>
</body>
</html>