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
	<script src="/CHD-HRP/lib/et_assets/hr/common.js"></script>
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
		
		var year_input,subj_name_select,subj_level_select,dept_name_select;
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
		}
		function init(){
			getData("../../../queryBudgYear.do?isCheck=false", function (data) {
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
					url:"../../../queryBudgSubj.do?isCheck=false",
					para:{
						subj_type:'04',
						budg_year:value
					}
				});
			}
			subj_level_select = $("#subj_level_select").etSelect({
				url: "../../../queryBudgSubjLevel.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});

			dept_name_select = $("#dept_name_select").etSelect({
				url: "../../../queryDept.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});
			
			state = $("#state").etSelect({
			    options:[
		            {id: '',text:'全部'},
		            {id: '04',text:'空'},
		            {id: '01',text:'下发'},
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
			})
		};

		//查询
		function query() {
			budg_year = year_input.getValue()
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value: subj_name_select.getValue() },
				{ name: 'subj_level', value: subj_level_select.getValue() },
				{ name: 'dept_id', value: dept_name_select.getValue() },
				{ name: 'state', value: state.getValue() }
			]
			//加载查询条件
			grid.loadData(search,"queryMedInDYBudgUp.do?isCheck=false");
		}

		function loadHead() {

			 var yearEditor = getRecentYearForSelect();	
			grid = $("#maingrid").etGrid({
				columns: [
					{ display: '预算年度', name: 'year', align: 'left', width: 80,frozen :true,editable:setEdit ,
						 
						  editor: yearEditor,
						},
					{ display: '科室编码', name: 'dept_code', align: 'left', width: 150,editable:false},
					{ display: '科室名称', name: 'dept_name', align: 'left', width: 150,editable:setEdit,
							editor:{
				 				type:'select' ,
				 				keyField:'dept_id',
				 				url:'queryBudgDept.do?isCheck=false',
				 				change:reloadDeptCode,
							}
						},
					{ display: '科目编码', name: 'subj_code', align: 'left', width: 150,editable:false },
					{ display: '科目名称', name: 'subj_name', align: 'left', width: 150,editable:setEdit,
							editor:{
				 				type:'select' ,
				 				keyField:'subj_code',
				 				url:'../../../queryBudgSubj.do?isCheck=false&subj_type='+'04'+'&is_last='+'1'+'&budg_year='+budg_year,
				 				change:queryLastYearWorkload ,
							
								//与年度联动查询
		                	    create:function(rowdata,celldata,setting){
		                	    	 if(rowdata.year){
		                	    		 setting.url = '../../../queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1&budg_year='+rowdata.year;
		                	    	 }else{
		                	    		 $.etDialog.error('请先填写年度');
		                	    		 return false ;
		                	    	 }
		                	    }
							},
						},
					{display: '上年收入(元)', name: 'last_year_income', align: 'right', width: 150,editable:false,
							render:function(ui) {
								if (ui.rowData.last_year_income) {
									return formatNumber(ui.rowData.last_year_income, 2, 1);
								}
		         			}
						},
					{display: '预算值(元)', name: 'budg_value', align: 'right', width: 150,editable:setEditByState,
							render:function(ui) {
								if (ui.rowData.budg_value) {
									return formatNumber(ui.rowData.budg_value, 2, 1);
								}
		         			}
						},
					{ display: '说明', name: 'remark', align: 'left', width: 200,editable:setEditByState,
						},
					{ display: '参考值', name: 'refer_value', align: 'right',width:120,editable:false,
				 			render:function(ui) {
								if (ui.rowData.refer_value) {
									return formatNumber(ui.rowData.refer_value, 2, 1);
								}
				 			}
						},
		            { display: '不通过原因', name: 'reason', width:200,dataType:'float',editable:false
						},
		            { display: '状态', name: 'state', minWidth:120,dataType:'float',editable:false,
				 			render:function(ui) {
				 				if (ui.rowData.state == '0') {
									return "新建";
								}else if (ui.rowData.state == '01') {
									return "下发";
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
                 pageModel:{
                	 type : 'remote'
                 } ,
                 usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
       	         addRowByKey:true,
                 toolbar: {
                     items: [
      		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
      		          	{ type: "button", label: '生成',icon:'plus',listeners: [{ click: generate}] },
      					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_row}] },
      					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
      					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
      					{ type: "button", label: '预算分解维护',icon:'minus',listeners: [{ click: resolve_open}] },
      				/* 	{ type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
      					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }, */
      					{ type: "button", label: '下发',icon:'arrowreturnthick-1-e',listeners: [{ click: issued}] },
      					{ type: "button", label: '撤回',icon:'arrowreturnthick-1-w',listeners: [{ click: retract}] },
      					{ type: "button", label: '确认通过',icon:'check',listeners: [{ click: pass}] }, 
      					{ type: "button", label: '确认不通过',icon:'closethick',listeners: [{ click: disPass}] },
      					{ type: "button", label: '取消确认',icon:'circle-arrow-w',listeners: [{ click: cancelConfirm}] }
                 ]}
			});
		}
		
		// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
	    function setEdit(ui){
	   		 if(ui.rowData && ui.rowData.group_id){
	   			 return false ;
	   		 }else{
	   			 return true ;
	   		 }
	    }
		
	    // 根据 state 是否存在 返回 true 或 false  控制单元格可否编辑 用 (合计行所有列不可编辑)
	    function setEditByState(ui){
	   		 if(ui.rowData && (ui.rowData.state == null || ui.rowData.state == '' || ui.rowData.state =='03' || ui.rowData.state =='0' )){
	   			 return true ;
	   		 }else{
	   			 return false ;
	   		 }
	    }
		 
		//添加行
		function add_row(){
			grid.addRow();
		}
		
		//选择 科室后 查询其上年业务量 并在行数据中添加上年业务量数据
	    function queryLastYearWorkload(rowdata,celldata){
		   	if(rowdata.year && rowdata.subj_code && rowdata.dept_id){
		   		$.post("queryDeptYearLastYearWork.do?isCheck=false&budg_year="+rowdata.year+"&subj_code="+rowdata.subj_code+"&dept_id="+rowdata.dept_id,null,function(responseData){
	              	var para = eval("("+responseData+")") ;
	              	if(para){
	              		grid.updateRow(celldata.rowIndx,{'last_year_income':para.last_year_income});
	              	}
		        });
		   	}
	    }
		
		//选择指标后 更新指标编码 
	    function reloadDeptCode(rowdata,celldata){
	    	setTimeout(function () {
	    		grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
	    	}, 10);
	    }	

		function resolve_open() {
			var year = year_input.getValue();
			var subj_code = subj_name_select.getValue()	   
			if( !year){
				$.etDialog.error('预算年度不能为空');
				
				return false ;
			}
			/* if( !subj_code){
				
				$.etDialog.error('科目名称不能为空');
				
				return false ;
			} */
		  	var parm = "year="+year +"&subj_code="+subj_code 
			$.ligerDialog.open({
				url: 'medInDYBudgResolveMainPage.do?isCheck=false&'+parm, data: {}, height: 500, width: 800,
				title: '科室年度医疗收入预算分解维护', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			});
		}
		
		//保存
	    function save (){
			var data = grid.getChanges();
			var ParamVo =[];
			if( data.addList.length > 0 || data.updateList.length > 0 ){
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
    						(this.budg_value ? this.budg_value : "") + "@" +
    						(this.last_year_income ? this.last_year_income : "") + "@" +
    						(this.remark ? this.remark : "") + "@" +
    						(this.refer_value? this.refer_value:"") +"@"+
           					(this.reason? this.reason:"") +"@"+
           					(this.state? this.state:"") +"@"+
	      					//行号 提示错误信息用
	      					this._rowIndx +"@"+
	      					"1" //添加标识
	    				) 
	    			});
	        	}
				if( data.updateList.length > 0){
		        	var updateData = data.updateList ;
		               $(updateData).each(function (){	
		               	ParamVo.push(
							this.year + "@" +
							this.subj_code + "@" +
							this.dept_id + "@" +
							(this.budg_value ? this.budg_value : "") + "@" +
							(this.last_year_income ? this.last_year_income : "") + "@" +
							(this.remark ? this.remark : "") + "@" +
							(this.refer_value? this.refer_value:"") +"@"+
	       					(this.reason? this.reason:"") +"@"+
	       					(this.state? this.state:"") +"@"+
	      					//行号 提示错误信息用
	      					this._rowIndx +"@"+
	      					"2" //修改标识
		   				) 
		   			});
	        	}
				ajaxPostData({
					url: "saveMedInDYBudgUp.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res) {
						if (res.state == "true") {
							query();
						}
					}
				})
			}else{
	    		$.etDialog.warn('没有需要保存的数据!');
	    	}
	    }
		
		function remove(){
			var data = grid.selectGet();
	        if (data.length == 0){
	        	$.etDialog.error('请选择行');
	        }else{
	            var ParamVo =[];
	            var deleteDate = [];
	            $(data).each(function (){	
	            	if(this.rowData.group_id&&this.rowData.state==0){
	            		ParamVo.push(
            				this.rowData.group_id + "@" +
    						this.rowData.hos_id + "@" +
    						this.rowData.copy_code + "@" +
    						this.rowData.year + "@" +
    						this.rowData.subj_code + "@" +
    						this.rowData.dept_id
						)
	            	}else{
	            		deleteDate.push(this);
	            	}
				});
	            if(ParamVo.length > 0){
	            	$.etDialog.confirm('确定删除?', function () {
						ajaxPostData({
							url: "deleteMedInDYBudgUp.do?isCheck=false",
							data: { ParamVo: ParamVo.toString() },
							success: function (res) {
								if (res.state == "true") {
									query();
								}
							}
						})
					});
	            }
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
				var key=v.year + v.subj_code + v.dept_id 
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
		
		   //下发的功能
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
					this.rowData.budg_value   +"@"+
					this.rowData.dept_name +"@"+
					"01"  +"@"+
					"1"
				) 
			});
			$.etDialog.confirm('确定下发?', function () {
				ajaxPostData({
					url: "issuedOrRetract.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res) {
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
			$.etDialog.confirm('确定撤回?', function () {
				ajaxPostData({
					url: "issuedOrRetract.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res) {
						if (res.state == "true") {
							query();
						}
					}
				})
			});
		}
	    
	    //确认通过的功能
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
			$.etDialog.confirm('确定确认通过?', function () {
				ajaxPostData({
					url: "passOrDisPass.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res) {
						if (res.state == "true") {
							query();
						}
					}
				})
			});
		}
	    
		//确认不通过
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
	            title: '预算确认不通过原因',
	            btn: ['确定', '取消'],
	            btn1: function(index, el) {
	                var iframeWindow = window[el.find('iframe').get(0).name];
	                iframeWindow.saveBudgIncomeDeptDisPassReason();
	            }
	        });
	    }
	    
	    //取消确认的功能
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
			$.etDialog.confirm('确定取消确认?', function () {
				ajaxPostData({
					url: "issuedOrRetract.do?isCheck=false",
					data: { ParamVo: ParamVo.toString() },
					success: function (res) {
						if (res.state == "true") {
							query();
						}
					}
				})
			});
		}
		
		//增量生成
		function generate(){
		 	var year = year_input.getValue();
		 	if(year){
		 		ajaxPostData({
					url: "generate.do?isCheck=false&year="+year,
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
	    
		function imp() {
			var index = layer.open({
				type: 2,
				title: '导入',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				area: ['893px', '500px'],
				content: 'medInDYBudgImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
		function downTemplate() {
			location.href = "downTemplate.do?isCheck=false";
		}

		function printDate() {
			if (grid.getAllData().length == 0 || grid.getAllData() == null) {
				$.etDialog.error("无打印数据！");
				return;
			}

			grid.options.parms = [];
			grid.options.newPage = 1;

			grid.options.parms.push({ name: 'year', value: year_input.getValue() });
			grid.options.parms.push({ name: 'subj_code', value: subj_name_select.getValue() });
			grid.options.parms.push({ name: 'subj_level', value: subj_level_select.getValue() });
			grid.options.parms.push({ name: 'dept_id', value: dept_name_select.getValue() });
			var selPara = {};
			$.each(grid.options.parms, function (i, obj) {
				selPara[obj.name] = obj.value;
			});
			var printPara = {
				headCount: 2,
				title: "科室年度医疗收入预算信息",
				type: 3,
				columns: grid.getColumns(1)
			};
			ajaxJsonObjectByUrl("queryMedInDYBudgUp.do?isCheck=false", selPara, function (responseData) {
				printGridView(responseData, printPara);
			});
		}
		
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('S', save);
			hotkeys('D', remove);
			hotkeys('B', downTemplate);
			hotkeys('P', printDate);
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