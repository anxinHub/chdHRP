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
		
		var year_input,subj_name_select,subj_level_select,dept_name_select,state

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

			dept_name_select=$("#dept_name_select").etSelect({
				url: "../../../../queryBudgDeptDict.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
			
			state = $("#state").etSelect({
			    options:[
		            {id: '',text:'全部'},
		            {id: '04',text:'空'},
		            {id: '01',text:'提交'},
		            {id: '02',text:'通过'},
		            {id: '03',text:'不通过'},
			    ],
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
			});
		};

		function query(){
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value: subj_name_select.getValue() },
				{ name: 'subj_level', value: subj_level_select.getValue() },
				{ name: 'dept_id', value: dept_name_select.getValue() },
				{ name: 'state', value: state.getValue() }
			]
			//加载查询条件
			grid.loadData(search,"queryBudgMedIncomeDeptYear.do?isCheck=false");
		}

		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{display: '预算年度', name: 'year', align: 'center', width:'8%',editable:false},
					{display: '科室编码', name: 'dept_code', align: 'left',width:'10%',editable:false},
					{display: '科室名称', name: 'dept_name', align: 'left',width: '12%',editable:false},
					{display: '科目编码', name: 'subj_code', align: 'left', width: '8%',editable:false},
					{display: '科目名称', name: 'subj_name', align: 'left', width: '12%',editable:false},
					{display: '上年收入(元)', name: 'last_year_income', align: 'right',width: '12%',editable:false,
						render:function(ui) {
							if (ui.rowData.last_year_income) {
								return formatNumber(ui.rowData.last_year_income, 2, 1);
							}
						}
					},
					{display: '预算值(E)', name: 'budg_value', align: 'right', width:'12%',dataType:'float',editable:setEditByState, 
						render:function(ui) {
							if (ui.rowData.budg_value) {
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}
						}
					},
					{display: '说明(E)', name: 'remark', align: 'left',dataType: 'string',width:'18%',editable:setEditByState},
					 { display: '参考值', name: 'refer_value', align: 'right',width:120,editable:false,
			 			render:function(ui) {
							if (ui.rowData.refer_value) {
								return formatNumber(ui.rowData.refer_value, 2, 1);
							}
			 			}
					},
		            { display: '不通过原因', name: 'reason', width:200,dataType:'float',editable:false},
		            { display: '状态', name: 'state', minWidth:80,dataType:'float',editable:false,
			 			render:function(ui) {
							if (ui.rowData.state == '01') {
								return "提交";
							}else if (ui.rowData.state == '02'){
								return "通过";
							}else if (ui.rowData.state == '03'){
								return "不通过";
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
	            addRowByKey:true,/* summaryRowIndx :[0], */
				toolbar: {
	               items: [
			           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
						{ type: "button", label: '打印',icon:'plus',listeners: [{ click: printDate}] },
						{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
						{ type: "button", label: '提交',icon:'arrowreturnthick-1-e',listeners: [{ click: issued}] },
      					{ type: "button", label: '撤回',icon:'arrowreturnthick-1-w',listeners: [{ click: retract}] },
      					{ type: "button", label: '审核通过',icon:'check',listeners: [{ click: pass}] }, 
      					{ type: "button", label: '审核不通过',icon:'closethick',listeners: [{ click: disPass}] },
      					{ type: "button", label: '取消审核',icon:'circle-arrow-w',listeners: [{ click: cancelConfirm}] }
				   ]
				}
			});
		}
		
		// 根据 state 是否存在 返回 true 或 false  控制单元格可否编辑 用 (合计行所有列不可编辑)
	    function setEditByState(ui){
	   		 if(ui.rowData && (ui.rowData.state == null || ui.rowData.state == '' || ui.rowData.state =='03' ||ui.rowData.is_last == 0)){
	   			 return true ;
	   		 }else{
	   			 return false ;
	   		 }
	    }
		
		function save() {
			var data = grid.getChanges();
	    	var ParamVo =[];
	    	
			if(data.updateList.length > 0){
				
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
               			this.year + "@" +
   						this.subj_code + "@" +
   						this.dept_id + "@" +
   						(this.budg_value ? this.budg_value : "0") + "@" +
   						(this.count_value ? this.budg_value : "0") + "@" +
   						(this.remark ? this.remark : "") + "@" +
   						(this.last_year_income ? this.last_year_income : "0") + "@" +
   						(this.refer_value ? this.refer_value : "") + "@" +
   						(this.state ? this.state : "") + "@" +
   						(this.reason ? this.reason : "-1")
    				) 
    			});
                ajaxPostData({
					url: "updateBudgMedIncomeDeptYear.do?isCheck=false",
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
		
		 //提交的功能
		function issued(){
			var data = grid.selectGetChecked();
			if (data.length == 0){
				$.etDialog.error('请选择行');
				return;
			}
			var ParamVo =[];
			$(data).each(function (){
				ParamVo.push(
					this.rowData.group_id   +"@"+ 
					this.rowData.hos_id   +"@"+ 
					this.rowData.copy_code   +"@"+ 
					this.rowData.year   +"@"+ 
					this.rowData.subj_code   +"@"+ 
					this.rowData.subj_name   +"@"+ 
					this.rowData.dept_id   +"@"+
					this.rowData.dept_name +"@"+
					"01"  +"@"+
					"1"
				) 
			});
			$.etDialog.confirm('确定提交?',function (){
				ajaxPostData({
					url: "reviewOrUnreview.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
				})
			}); 
		}
	    //撤回的功能
		function retract(){
			var data = grid.selectGetChecked();
			if (data.length == 0){
				$.etDialog.error('请选择行');
				return;
			}
			var ParamVo =[];
			$(data).each(function (){
				ParamVo.push(
					this.rowData.group_id   +"@"+ 
					this.rowData.hos_id   +"@"+ 
					this.rowData.copy_code   +"@"+ 
					this.rowData.year   +"@"+ 
					this.rowData.subj_code   +"@"+ 
					this.rowData.subj_name   +"@"+ 
					this.rowData.dept_id   +"@"+
					this.rowData.dept_name +"@"+
					"-1"   +"@"+
					"2"
				) 
			});
			$.etDialog.confirm('确定撤回?',function (){
				ajaxPostData({
					url: "reviewOrUnreview.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
				})
			}); 
		}
	    
	    //审核通过的功能
		function pass(){
			var data = grid.selectGetChecked();
			if (data.length == 0){
				$.etDialog.error('请选择行');
				return;
			}
			var ParamVo =[];
			$(data).each(function (){
				ParamVo.push(
					this.rowData.group_id   +"@"+ 
	 				this.rowData.hos_id   +"@"+ 
	 				this.rowData.copy_code   +"@"+ 
	 				this.rowData.year   +"@"+ 
	 				this.rowData.subj_code   +"@"+ 
	 				this.rowData.subj_name   +"@"+ 
	 				this.rowData.dept_id   +"@"+
	 				this.rowData.dept_name +"@"+
	 				"" +"@"+
	 	        	"" +"@"+
	 				"02"  
				) 
			});
			$.etDialog.confirm('确定审核通过?',function (){
				ajaxPostData({
					url: "passOrDisPass.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
				})
			}); 
		}
	    
		//审核不通过
	    function disPass() {
	    	checkData = grid.selectGetChecked();
	    	if (checkData.length == 0){
				$.etDialog.error('请选择行');
				return;
			}
	        $.etDialog.open({
	            url: 'disPassReasonPage.do?isCheck=false',
	            height: 350,
	            width: 700,
	            title: '预算审核不通过原因',
	            btn: ['确定', '取消'],
	            btn1: function(index, el) {
	                var iframeWindow = window[el.find('iframe').get(0).name];
	                iframeWindow.saveBudgWorkDeptDisPassReason();
	            }
	        });
	    }
	    
	    
	    //取消审核的功能
		function cancelConfirm(){
			var data = grid.selectGetChecked();
			if (data.length == 0){
				$.etDialog.error('请选择行');
				return;
			}
			var ParamVo =[];
			$(data).each(function (){
				ParamVo.push(
					this.rowData.group_id   +"@"+ 
					this.rowData.hos_id   +"@"+ 
					this.rowData.copy_code   +"@"+ 
					this.rowData.year   +"@"+ 
					this.rowData.subj_code   +"@"+ 
					this.rowData.subj_name   +"@"+ 
					this.rowData.dept_id   +"@"+
					this.rowData.dept_name +"@"+
					"01"   +"@"+
					"3"
				) 
			});
			$.etDialog.confirm('确定取消审核?',function (){
				ajaxPostData({
					url: "reviewOrUnreview.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
				})
			}); 
		}
		
		
		var printDate = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={}; 
        	var printPara={
                title: "科室年度医疗收入预算增量预算信息",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgMedIncomeDeptYearService",
                method_name: "queryBudgMedIncomeDeptYear",
                bean_name: "budgMedIncomeDeptYearService",
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
			<tr>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td class="label">状态：</td>
				<td class="ipt">
					<select name="" id="state" style="width:180px;"></select>
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>

</html>