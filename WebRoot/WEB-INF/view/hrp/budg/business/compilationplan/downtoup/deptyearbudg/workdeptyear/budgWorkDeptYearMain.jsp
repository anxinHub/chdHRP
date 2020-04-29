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
    var budg_year ;
    var index_code ;
    var year_input;
    var index_code_select;
    var dept_id_select;
    var state;
    var params=[];
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
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
			defaultValue: "none",
			url: '../../../../../queryBudgIndexDict.do?isCheck=false',
			onChange: function(value){
				index_code = value ;
				reloadDeptName(value);
				query();
			}
		});
		
		dept_id_select = $("#dept_id_select").etSelect({
			defaultValue: "none",
			onChange: query
		});
		function reloadDeptName(value){
			dept_id_select.reload({
				url:"../../../../../queryBudgIndexDeptSet.do?isCheck=false",
				para:{
					index_code : value
				}
			})
		};
		
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
		})
	};
    
	 //查询
    function  query(){
    	params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
			{ name: 'state', value: state.getValue() },
			{ name: 'budg_level', value: "03" },
		]
		//加载查询条件
		grid.loadData(params,"queryBudgWorkDeptYearDown.do?isCheck=false");
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '年度', name: 'year', align: 'left',width:90
			   		},
               { display: '科室编码', name: 'dept_code', align: 'left',width:120
					},
			   { display: '科室名称', name: 'dept_name', align: 'left',width:150
					},
			   { display: '指标编码', name: 'index_code', align: 'left',width:120
				 	},
			   { display: '指标名称', name: 'index_name', align: 'left',width:150
					},
               { display: '预算值', name: 'budg_value', align: 'right',width:180,editable:setEditByState,
						render:function(ui){
							if (ui.rowData.budg_value) {
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}
						}
					},
               { display: '说明', name: 'remark', align: 'left',width:200,editable:setEditByState
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
            usePager:false,width: '100%', height: '100%',checkbox: true,editable: true,
            addRowByKey:true,
         	toolbar: {
	            items: [
		          	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
				    { type: "button",label:'打印', icon:'arrowthickstop-1-s',listeners: [{ click: printDate}]},
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
   		 if(ui.rowData && (ui.rowData.state == null || ui.rowData.state == '' || ui.rowData.state =='03' )){
   			 return true ;
   		 }else{
   			 return false ;
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
				this.rowData.index_code   +"@"+ 
				this.rowData.index_name   +"@"+ 
				this.rowData.dept_id   +"@"+
				this.rowData.dept_name +"@"+
				"01"  +"@"+
				"1"
			) 
		});
		$.etDialog.confirm('确定提交?', function (yes){
			if(yes){
				ajaxPostData({
     			    url: 'reviewOrUnreview.do?isCheck=false',
     			    data: {ParamVo : ParamVo.toString()},
     			    success: function (responseData) {
     			    	query();
     			    }
     			})
			}
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
				this.rowData.index_code   +"@"+ 
				this.rowData.index_name   +"@"+ 
				this.rowData.dept_id   +"@"+
				this.rowData.dept_name +"@"+
				"-1"   +"@"+
				"2"
			) 
		});
		$.etDialog.confirm('确定撤回?', function (yes){
			if(yes){
				ajaxPostData({
     			    url: 'reviewOrUnreview.do?isCheck=false',
     			    data: {ParamVo : ParamVo.toString()},
     			    success: function (responseData) {
     			    	query();
     			    }
     			})
			}
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
 				this.rowData.index_code   +"@"+ 
 				this.rowData.index_name   +"@"+ 
 				this.rowData.dept_id   +"@"+
 				this.rowData.dept_name +"@"+
 				"" +"@"+
 	        	"" +"@"+
 				"02"  
			) 
		});
		$.etDialog.confirm('确定审核通过?', function (yes){
			if(yes){
				ajaxPostData({
     			    url: 'passOrDisPass.do?isCheck=false',
     			    data: {ParamVo : ParamVo.toString()},
     			    success: function (responseData) {
     			    	query();
     			    }
     			})
			}
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
				this.rowData.index_code   +"@"+ 
				this.rowData.index_name   +"@"+ 
				this.rowData.dept_id   +"@"+
				this.rowData.dept_name +"@"+
				"01"   +"@"+
				"3"
			) 
		});
		$.etDialog.confirm('确定取消审核?', function (yes){
			if(yes){
				ajaxPostData({
     			    url: 'reviewOrUnreview.do?isCheck=false',
     			    data: {ParamVo : ParamVo.toString()},
     			    success: function (responseData) {
     			    	query();
     			    }
     			})
			}
		}); 
	}
 	
	function printDate(){
    	if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads = {};
    	
    	var printPara={
    			
      		title: "科室年度业务预算表",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptYearService",
   			method_name: "getPrintData",
   			bean_name: "budgWorkDeptYearService",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
    	printPara['year']=year_input.getValue();
    	printPara['index_code']=index_code_select.getValue();
    	printPara['dept_id']=dept_id_select.getValue();
    	printPara['state']=state.getValue();
    	printPara['budg_level']="03";
    	
       	officeGridPrint(printPara);
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
            			this.year	+"@"+
       					this.index_code   +"@"+ 
       					this.dept_id   +"@"+ 
       					(this.last_year_workload? this.last_year_workload:"")  +"@"+ 
       					(this.grow_rate? this.grow_rate:"")  +"@"+ 
       					(this.grow_value? this.grow_value:"")  +"@"+ 
       					(this.resolve_rate? this.resolve_rate:"")  +"@"+ 
       					(this.count_value? this.count_value:"")  +"@"+ 
       					(this.budg_value? this.budg_value:"")  +"@"+ 
       					(this.remark?this.remark:"")   	+"@"+ 
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
	           			this.year	+"@"+
      					this.index_code   +"@"+ 
      					this.dept_id   +"@"+ 
      					(this.last_year_workload? this.last_year_workload:"")  +"@"+ 
      					(this.grow_rate? this.grow_rate:"")  +"@"+ 
      					(this.grow_value? this.grow_value:"")  +"@"+ 
      					(this.resolve_rate? this.resolve_rate:"")  +"@"+ 
      					(this.count_value? this.count_value:"")  +"@"+ 
      					(this.budg_value? this.budg_value:"")  +"@"+ 
      					(this.remark?this.remark:"")   	+"@"+ 
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
                url: "saveBudgWorkDeptYearUp.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		}else{
    		$.etDialog.warn('没有需要保存的数据!');
    	}
    }
    function resolveHosSuggest(){
    	
    }
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', resolveHosSuggest);
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
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;"></select>
				</td>
				<td class="label">状态：</td>
				<td class="ipt">
					<select name="" id="state" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
