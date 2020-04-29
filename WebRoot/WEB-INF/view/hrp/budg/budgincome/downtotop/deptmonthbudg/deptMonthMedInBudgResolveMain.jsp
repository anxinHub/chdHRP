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
	<jsp:param value="select,datepicker,ligerUI,grid" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var budg_year ;
    var flag = 0
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
	});
	
	var year_input,subj_name_select,subj_level_select,dept_name_select;

	function init(){
		
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	//query();
                	reloadSubjName();
                }, 10);
            },
            defaultDate: true
        });

		
		month_input = $("#month_input").etDatepicker({
			view:'months',
			minView:'months',
			dateFormat:"mm",
			todayButton:false,
			showNav:false,
			onChange:query
		});
		

		subj_name_select = $("#subj_name_select").etSelect({
			url:"../../../queryBudgSubj.do?isCheck=false&subj_type=04&budg_year="+year_input.getValue(),
			defaultValue: "${subj_code}",
			onChange: query
		});
		function reloadSubjName(value){
			subj_name_select.reload({
				url:"../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:'04',
					budg_year:year_input.getValue() 
				}
			});
		}
		subj_level_select = $("#subj_level_select").etSelect({
			url: "../../../queryBudgSubjLevel.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		});

		dept_name_select = $("#dept_name_select").etSelect({
			url: "../../../queryBudgDeptDict.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		})
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
	function query() {
		var search = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_name_select.getValue() },
			{ name: 'subj_level', value: subj_level_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() },
			{ name: 'month', value: month_input.getValue() }
		]
		//加载查询条件
		grid.loadData(search, '');
	}

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                     { display: '预算年度', name: 'year', align: 'left',width:80},
                     { display: '月份', name: 'month', align: 'left',width:40},
                     { display: '科室编码', name: 'dept_code', align: 'left',width:150},
					 { display: '科室名称', name: 'dept_name', align: 'left',width:150},
				 	 { display: '科目编码', name: 'subj_code', align: 'left',width:150},
				 	 { display: '科目名称', name: 'subj_name', align: 'left',width:150},
				 	 { display: '科室年度收入预算(元)', name: 'yearValue', align: 'right',width:150,
				 		render:function(ui) {
							if (ui.rowData.yearValue) {
								return formatNumber(ui.rowData.yearValue, 2, 1);
							}
						}
				 	 },
				 	 { display: '上年收入(元)', name: 'last_year_income', align: 'right',width:150,
				 		render:function(ui) {
							if (ui.rowData.last_year_income) {
								return formatNumber(ui.rowData.last_year_income, 2, 1);
							}
						}
				 	 },
				 	{ display: '增减比例(E)', name: 'grow_rate', align: 'right',width:80,dataType: "float",
				 		render:function(ui) {
							if (ui.rowData.grow_rate) {
								return formatNumber(ui.rowData.grow_rate, 2, 1) +"%";
							}
						}
				 	 },
				 	{ display: '分解比例(E)', name: 'resolve_rate', align: 'right',width:80,dataType: "float",
				 		render:function(ui) {
							if (ui.rowData.resolve_rate) {
								return formatNumber(ui.rowData.resolve_rate, 2, 1) +"%";
							}
						}
				 	 },
				 	{ display: '计算值(元)', name: 'count_value', align: 'right',width:150,
				 		render:function(ui) {
							if (ui.rowData.count_value) {
								return formatNumber(ui.rowData.count_value, 2, 1);
							}
						}
				 	 },
				 	 { display: '预算值(元)(E)', name: 'budg_value', align: 'right',width:150,dataType: "float",
				 		render:function(ui) {
							if (ui.rowData.budg_value) {
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}
						}
				 	 },
                     { display: '说明(E)', name: 'remark', align: 'left',width:200,dataType: "string"}
                    ],
                    dataModel: {
      					method: 'POST',
      					location: 'remote',
      					url: 'queryDeptMonthMedInBudgResolveUp.do?isCheck=false&is_last=1&resolve_or_sum=01',
      					recIndx: 'year'
      				},
      				usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
     				addRowByKey: true,
     				/* load : function(){
     					grid.refreshSummary();
     				},
     				summary : {
     					totalColumns:['resolve_rate','count_value','budg_value'],
     					keyWordCol : 'year'
     				}, */
     				toolbar: {
     					items: [
     						{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
     						{ type: "button", label: '计算', icon: 'calculator', listeners: [{ click: count }] },
     						{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
     						
     					]
     				}
           });
    }
  
    //计算
    function count(){
    	var year = year_input.getValue();
		var subj_code = subj_name_select.getValue();
		if( !year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		if( !subj_code){
			$.etDialog.error('预算科目不能为空');
			return false ;
		}
		ajaxPostData({
			url: "collectMedInDMBudgUp.do?isCheck=false&year="+year+"&subj_code="+subj_code,
			data: {},
			success: function (res){
				if (res.state == "true") {
					parent.query();
					query();
				}
			}
		})
    }   
    
  //保存
    function save (){
	   
		var data = grid.getChanges();
    	
		var ParamVo =[];
		if(data.updateList.length > 0 ){
        	
			if( data.updateList.length > 0){
	        	
	            var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
	                	this.year   +"@"+ 
						this.month   +"@"+ 
						this.dept_id  +"@"+ 
						this.subj_code  +"@"+ 
						(this.budg_value? this.budg_value:"")  +"@"+ 
						(this.grow_rate? this.grow_rate:"-1") + "@" + 
						(this.resolve_rate? this.resolve_rate:"-1") + "@" + 
						(this.count_value? this.count_value:"-1") + "@" +  
						(this.remark? this.remark:"-1") + "@" + 
						(this.last_year_income? this.last_year_income:"-1")
					);
	   			});
        	}
			ajaxPostData({
				url: "updateDeptMonthMedInBudgResolveUp.do?isCheck=false",
				data: { ParamVo: ParamVo.toString() },
				success: function (res){
					if (res.state == "true") {
						query();
					}
				}
			})
		}else{
    		$.etDialog.warn('没有需要保存的数据!');
    	}
    }
    /* function loadDict(){
        //字典下拉框
            
    	//预算月份下拉框
        autocomplete("#year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true,'',80);
            
        budg_year = liger.get("year").getValue();
            
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
    	//科目级次下拉框
        autocomplete("#subj_level","../../../queryBudgSubjLevel.do?isCheck=false","id","text",true,true,'',false,'',80);
    	
        //预算科室下拉框（）
        autocomplete("#dept_id","../../../queryBudgDeptDict.do?isCheck=false","id","text",true,true,'',false,'',200);
 		autoCompleteByData("#month", monthData.Rows, "id", "text", true, true,'',false,'',80);
        
   	 	$("#year").ligerTextBox({width:100});
        $("#month").ligerTextBox({width:100});
        $("#subj_code").ligerTextBox({width:200});
        $("#subj_level").ligerTextBox({width:100});
        $("#dept_id").ligerTextBox({width:200});
     }   */
    //键盘事件
	 function loadHotkeys() {
		hotkeys('Q', query);

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
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_name_select" style="width:180px;"></select>
				</td>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name=" " id="subj_level_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td class="label">月份： </td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>
	
</body>
</html>
