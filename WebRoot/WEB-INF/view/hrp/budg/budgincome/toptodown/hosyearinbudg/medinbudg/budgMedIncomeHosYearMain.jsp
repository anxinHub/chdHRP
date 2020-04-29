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

		var budg_year;

		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
		});
		
		var year_input,subj_name_select,subj_level_select;
		
		function init(){
			getData("../../../../queryBudgYear.do?isCheck=false", function (data) {
				year_input = $("#year_input").etDatepicker({
					defaultDate: data[0].text,
					view: "years",
					minView: "years",
					dateFormat: "yyyy",
					/* minDate: data[data.length - 1].text,
					maxDate: data[0].text, */
					todayButton: false,
					onSelect: function (value) {
						reloadSubjName(value);
						setTimeout(function () {
							query();
						}, 10);
					}
				});
				reloadSubjName(data[0].text);
			});

			subj_name_select = $("#subj_name_select").etSelect({
				defaultValue: "none",
				onChange: query
			});
			function reloadSubjName(value){
				subj_name_select.reload({
					url:"../../../../queryBudgSubj.do?isCheck=false",
					para:{
						subj_type:'04',
						budg_year:value
					}
				})
			}
			subj_level_select = $("#subj_level_select").etSelect({
				url: "../../../../queryBudgSubjLevel.do?isCheck=false&insurance_natrue='01'",
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
			})
		};

		//查询
		function query() {
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value: subj_name_select.getValue() },
				{ name: 'subj_level', value: subj_level_select.getValue() }
			]
			//加载查询条件
			grid.loadData(search,"queryBudgMedIncomeHosYear.do?isCheck=false");
		}

		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{display: '预算年度', name: 'year', align: 'center',width:'8%',editable:false},
					{display: '科目编码', name: 'subj_code', align: 'left',width:'12%',editable:false},
					{display: '科目名称', name: 'subj_name', align: 'left',width:'15%',editable:false},
					{display: '上年收入(元)', name: 'last_year_income', align: 'right',width:'12%',editable:false,
						render:function(ui) {
							if (ui.rowData.last_year_income) {
								return formatNumber(ui.rowData.last_year_income, 2, 1);
							}else{
								return formatNumber(0, 2, 1);
							}
						}
					},
					{display: '预算值(E)', name: 'budg_value', align: 'right', dataType:'float',editable:setEdit,width:'12%',
						render:function(ui) {
							if (ui.rowData.budg_value) {
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}else{
								return formatNumber(0, 2, 1);
							}
						}
					},
					{display: '说明(E)', name: 'remark', align: 'left', dataType:'string',width:'38%',
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
			           	{ type: "button", label: '打印',icon:'script',listeners: [{ click: printDate}] },
						{ type: "button", label: '保存',icon:'plus',listeners: [{ click: save}] },
						{ type: "button", label: '科室汇总',icon:'plus',listeners: [{ click: sumDeptBudgValue}] },
						{ type: "button", label: '导出',icon:'import',listeners: [{ click: exportExcel}] },
	           	]}
			});
		}
		
		// 根据is_last 是否末级  返回 true 或 false  控制单元格可否编辑 用 
	    function setEdit(ui){
	   		 if(ui.rowData && ui.rowData.is_last == 0 ){
	   			 return false ;
	   		 }else{
	   			 return true ;
	   		 }
	    }
		
	    function sumDeptBudgValue(){
	    	var year = year_input.getValue();
	    	if(year){
	    		ajaxPostData({
					url: "sumDeptBudgValue.do?isCheck=false&year="+year,
					data: {},
					success: function (res) {
						if (res.state == "true") {
							query();
						}
					}
				})
	    	}else{
	    		$.etDialog.error("预算年度不能为空");
	    	}
	    }
		
		function save() {
			
			var data = grid.getChanges();

			if (data.updateList.length == 0) {
				$.etDialog.error('没有需要保存的数据');
			} else {
				var ParamVo = [];
				var updateData = data.updateList ;
				$(updateData).each(function () {
					ParamVo.push(
						this.year + "@" +
						this.subj_code + "@" +
						(this.budg_value ? this.budg_value : "0") + "@" +
						(this.count_value ? this.budg_value : "0") + "@" +
						(this.remark ? this.remark : "") + "@" +
						(this.last_year_income ? this.last_year_income : "0") + "@" +
      					//行号 提示错误信息用
      					this._rowIndx +"@"+
      					"2" //修改标识
					)
				})
				ajaxPostData({
                    url: "updateBudgMedIncomeHosYear.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        query();
                    }
                });
			}

		}

		var printDate = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={}; 
        	var printPara={
                title: "医院年度医疗收入预算增量预算信息",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgMedIncomeHosYearService",
                method_name: "getPrintData",
                bean_name: "budgMedIncomeHosYearService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
         	
            officeGridPrint(printPara);
        	
        	
        }
		
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('P', printDate);
			hotkeys('S', save);
		}
		
		//导出excel
		function exportExcel(){
			var year=year_input.getValue();
			var subj_code=subj_name_select.getValue();
			var subj_level=subj_level_select.getValue();
	
            location.href = "exportExcel.do?isCheck=false&year="+year+"&subj_code="+subj_code+"&subj_level="+subj_level;
			
		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

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
		</table>
	</div>

	<div id="maingrid"></div>

</body>

</html>