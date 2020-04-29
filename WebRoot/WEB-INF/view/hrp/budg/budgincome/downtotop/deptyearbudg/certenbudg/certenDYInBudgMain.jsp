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
		<jsp:param value="select,datepicker,ligerUI,grid,dialog" name="plugins" />
	</jsp:include>
	<script src="/CHD-HRP/lib/et_assets/hr/common.js"></script>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;

		var budg_year;

		//打印 单元格格式化 用
		var renderFunc = {
			count_value: function (value) { //计算值
				return formatNumber(value, 2, 1);
			},
			budg_value: function (value) { //预算值
				return formatNumber(value, 2, 1);
			},
			dept_suggest_sum: function (value) { //科室意见汇总
				return formatNumber(value, 2, 1);
			},
		};

		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
		});
		
		var year_input,subj_name_select,subj_level_select,dept_name_select

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
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value: subj_name_select.getValue() },
				{ name: 'subj_level', value: subj_level_select.getValue() },
				{ name: 'dept_id', value: dept_name_select.getValue() }
			]
			//加载查询条件
			grid.loadData(search,"");
		}

		function loadHead() {
			var yearEditor = getRecentYearForSelect();
			grid = $("#maingrid").etGrid({
				columns: [
					{display: '预算年度', name: 'year', align: 'center',width:"6%",editable:setEdit,
						 editor: yearEditor,
					},
					{ display: '科室编码', name: 'dept_code', align: 'left', width:'10%',editable:false},
					{ display: '科室名称', name: 'dept_name', align: 'left', width: '12%',editable:setEdit,
						editor:{
			 				type:'select' ,
			 				keyField:'dept_id',
			 				url:'queryBudgDept.do?isCheck=false',
			 				change:reloadDeptCode,
						}
					},
					{display: '科目编码', name: 'subj_code', align: 'left',width:"10%",editable:false},
					{display: '科目名称', name: 'subj_name', align: 'left',width:"15%",editable:setEdit,
						editor: {
				 			keyField:'subj_code',
		               	    type: 'select',  //编辑框为下拉框时
		               	    url: '../../../../qureySubjIndexFromPlan.do?isCheck=false&edit_method=03&budg_year='+budg_year,      //  动态数据接口
			               	change:function(rowdata,celldata){
		             	    	grid.updateRow(celldata.rowIndx,{subj_code:rowdata.subj_code})
		             	    },
		               	    //与年度联动查询
		               	    create:function(rowdata,celldata,setting){
		               	    	if(rowdata.year){
		               	    		setting.url = '../../../../qureySubjIndexFromPlan.do?isCheck=false&edit_method=03&budg_year='+rowdata.year;
		               	    	}else{
		               	    		 
		               	    		$.etDialog.error('请先填写年度');
		               	    		 
		               	    		return false ;
		               	    	}
		               	    }
		               	}
					},
					{display: '计算值', name: 'count_value', align: 'right',width:'12%',editable:false,
						render:function(ui){
							if (ui.rowData.fun_id){//取值函数链接
								return "<a href=javascript:openFunProcess('" + ui.rowData.group_id + "|" +
									ui.rowData.hos_id + "|" + ui.rowData.copy_code + "|" + ui.rowData.subj_code + "|" + ui.rowData.dept_id + "|"+ ui.rowData.year + "|" +
									ui.rowData.fun_id + "')>" + formatNumber(ui.rowData.count_value, 2, 1) + "</a>";
							} else if (ui.rowData.formula_id) {//计算公式链接
								return "<a href=javascript:openFormulaProcess('" + ui.rowData.group_id + "|" +
								ui.rowData.hos_id + "|" + ui.rowData.copy_code + "|" + ui.rowData.subj_code + "|" + ui.rowData.dept_id + "|"+ ui.rowData.year + "|" +
								ui.rowData.formula_id + "')>" + formatNumber(ui.rowData.count_value, 2, 1) + "</a>";
							} else {
								return formatNumber(ui.rowData.count_value, 2, 1);
							}
						}
						
					},
					{display: '预算值(E)', name: 'budg_value', align: 'right', dataType:'float',width:'12%',
						render:function(ui) {
							if (ui.rowData.budg_value) {
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}
						}
					},
					{display: '说明(E)', name: 'remark', align: 'left', dataType:'string',minWidth:'20%'},
				],
				dataModel:{
		           	 method:'POST',
		           	 location:'remote',
		           	 url:'queryCertenDYInBudgUp.do?isCheck=false&edit_method=03',
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
						/* { type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
						{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }, */
						{ type: "button", label: '计算',icon:'calculator',listeners: [{ click: collect}] },
	            ]}
			});
		}
		//打印回调方法
		function lodopPrint() {
			var head = "";
			grid.options.lodop.head = head;
			grid.options.lodop.fn = renderFunc;
			grid.options.lodop.title = "科室年度医疗收入预算-确定预算信息";
		}

		//选择指标后 更新指标编码 
	    function reloadDeptCode(rowdata,celldata){
	    	setTimeout(function () {
	    		grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
	    	}, 10);
	    }	
	    // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
	    function setEdit(ui){
	   		 if(ui.rowData && ui.rowData.group_id){
	   			 return false ;
	   		 }else{
	   			 return true ;
	   		 }
	    }
		//添加行
	    function add_Row(){
	    	grid.addRow() ;
	    }
		
		function add_open() {
			$.ligerDialog.open({
				url: 'certenDYInBudgAddPage.do?isCheck=false', data: {}, height: 500, width: 800,
				title: '医院年度医疗收入预算确定预算添加', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true

			});
		}
		
		//增量生成
	    function generate(){
	    	var year = year_input.getValue();
	    	if(year){
	    		ajaxPostData({
		    		url: "generate.do?isCheck=false&year="+year,
					data: {},
					success: function (res){
						if (res.state == "true") {
							query();
						}
					}
	    		});
	    	}else{
	    		$.etDialog.error("预算年度不能为空");
	    	}
	    }
		
	    function save() {
			var data = grid.getChanges();
	    	
	    	var ParamVo =[];

			if(data.addList.length > 0 || data.updateList.length > 0){
	    		
	        	if(data.addList.length > 0){
	        		
	        		var addData = data.addList ;
	        		
	        		if(!validateGrid(addData)){
	        			return  false ;
	        		}
	                $(addData).each(function (){	
	                	ParamVo.push(
                			this.year + "@" +
    						this.subj_code + "@" +
    						this.dept_id + "@" +
    						(this.budg_value ? this.budg_value : "0") + "@" +
    						(this.count_value ? this.count_value : "0") + "@" +
    						(this.remark ? this.remark : "") + "@" +
	    					this._rowIndx +"@"+ 
	    					'1' //添加数据标识
	    				) 
	    			});
	        	}
	    		if(data.updateList.length > 0){
	        		var updateData = data.updateList ;
	                $(updateData).each(function (){	
	                	ParamVo.push(
                			this.year + "@" +
    						this.subj_code + "@" +
    						this.dept_id + "@" +
    						(this.budg_value ? this.budg_value : "0") + "@" +
    						(this.count_value ? this.count_value : "0") + "@" +
    						(this.remark ? this.remark : "") + "@" +
	    					this._rowIndx +"@"+ 
	    					'2' //修改数据标识
	    				) 
	    			});
	        	}
	    		ajaxPostData({
					url: "saveCertenDYInBudgUp.do?isCheck=false",
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
				if (!v.subj_name) {
					rowm+="[科目名称]、";
				}
				if (!v.dept_name) {
					rowm+="[科室名称]、";
				}
				if (!v.budg_value) {
					rowm+="[预算值]、";
				}
	 			
				if(rowm != ""){
					rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
				}
				msg += rowm;
				var key=v.year + v.month + v.subj_code + v.dept_id
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
	 
		function remove() {
			var data = grid.selectGet();
	        if (data.length == 0){
	        	$.etDialog.error('请选择行');
	        }else{
	            var ParamVo =[];//后台删除数据
	            var deletePageRow = [];// 页面删除数据
	            $(data).each(function (){	
	            	if(this.rowData.group_id){
	            		ParamVo.push(
            				this.rowData.group_id   +"@"+ 
            				this.rowData.hos_id   +"@"+ 
            				this.rowData.copy_code   +"@"+ 
            				this.rowData.year   +"@"+ 
            				this.rowData.subj_code + "@" +
    						this.rowData.dept_id
	       				) 
	            	}else{
	            		deletePageRow.push(this);
	            	}
				});
	            if(ParamVo.length > 0){
	            	$.etDialog.confirm('确定删除?',function (){
						ajaxPostData({
							url: "deleteCertenDYInBudgUp.do?isCheck=false",
							data: { ParamVo: ParamVo.toString() },
							success: function (res){
								if (res.state == "true") {
									query();
								}
							}
						})
					});
	            }else if(deletePageRow.length > 0 ){
	            	grid.deleteRows(deletePageRow);
	            	$.etDialog.success("删除成功!");
	            }
			}
		}
		
		function imp() {
			$.etDialog.open({
				url: 'certenDYInBudgImportPage.do?isCheck=false',
				title: '导入',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				isMax: true
			});
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
				"subj_code=" + vo[4] + "&" +
				"dept_id=" + vo[5]

			$.ligerDialog.open({
				url: 'certenDYInBudgUpdatePage.do?isCheck=false&' + parm, data: {}, height: 300, width: 450, title: '医院年度医疗收入独立核算科目预算', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgHosIndependentSubj(); }, cls: 'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});
		}

		//计算
		function collect() {
			var year = year_input.getValue();
			if (!year) {
				$.etDialog.error('预算年度不能为空');
				return false;
			}
			ajaxPostData({
				url: "collectCertenDYBudgData.do?isCheck=false&year=" + year + "&budg_level=" + "03" + "&edit_method=" + "03",
				data: {},
				success: function (res){
					if (res.state == "true") {
						query();
					}
				}
			})
		}
		//计算过程查看  链接
		function openFormulaProcess(obj) {

			var vo = obj.split("|");

			var parm =
				"group_id=" + vo[0] + "&" +
				"hos_id=" + vo[1] + "&" +
				"copy_code=" + vo[2] + "&" +
				"index_code=" + vo[3] + "&" +
				"dept_id=" + vo[4] + "&" +
				"year=" + vo[5] + "&" +
				"formula_id=" + vo[6] + "&" +
				"element_type_code=" + '04'
			"element_level=" + '03'
			parent.$.ligerDialog.open({
				url: 'hrp/budg/common/budgformula/budgFormulaProcessPage.do?isCheck=false&' + parm, data: {},
				height: 600, width: 800, title: '计算过程查看', modal: true, showToggle: false, showMax: true, showMin: false,
				isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});
		}

		//取值函数查看  链接 
		function openFunProcess(obj) {

			var vo = obj.split("|");

			var parm =
				"group_id=" + vo[0] + "&" +
				"hos_id=" + vo[1] + "&" +
				"copy_code=" + vo[2] + "&" +
				"index_code=" + vo[3] + "&" +
				"dept_id=" + vo[4] + "&" +
				"year=" + vo[5] + "&" +
				"fun_code=" + vo[6] + "&" +
				"budg_year=" + vo[5] + "&" +
				"budg_level=" + '03' + "&" +
				"index_type_code=" + "04"

			parent.$.ligerDialog.open({
				url: 'hrp/budg/common/budgfun/budgFunProcessPage.do?isCheck=false&' + parm, data: {},
				height: 600, width: 800, title: '函数取值过程查看', modal: true, showToggle: false, showMax: true, showMin: false,
				isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});
		}

		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('A', add_open);
			hotkeys('S', save);
			hotkeys('D', remove);
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
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>

</html>