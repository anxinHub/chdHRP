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
    var index_code ;
    var year_input,index_code_select,dept_name_select;
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
	function init() {
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
			url:"../../../../queryBudgIndexDict.do?isCheck=false",
			onChange:query
		});

		dept_name_select = $("#dept_name_select").etSelect({
			url:"../../../../queryBudgDeptDict.do?isCheck=false",
			defaultValue:"none",
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
    	
    	if(!year_input.getValue()){
    		$.etDialog.error('预算年度不能为空');
    		return false ;
    	}
    	/*
    	if(!index_code_select.getValue()){
    		$.etDialog.error('预算指标不能为空');
    		return false ;
    	}
    	*/
    	var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms, 'queryBudgWorkDeptMonthDown.do?isCheck=false');
		
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [
        	   {display: '年度', name: 'year', align: 'center', width: 60, 
	        	   editable: false,
					render:function(ui) {
						if (ui.rowData.year != "合计") {
							return "<a href=javascript:openUpdate('" + ui.rowData.group_id + "|" + ui.rowData.hos_id + "|"
								+ ui.rowData.copy_code + "|" + ui.rowData.year + "|" + ui.rowData.index_code + "|"
								+ ui.rowData.dept_id + "')>" + ui.rowData.year + "</a>";
						}
					}
				},
				{display: '指标编码', name: 'index_code', align: 'left', width: 100, editable: false,
				},
				{display: '指标名称', name: 'index_name', align: 'left', width: 120, editable: false,
				},
				{display: '科室编码', name: 'dept_code', align: 'left', width: 100, editable: false,
				},
				{display: '科室名称', name: 'dept_name', align: 'left', width: 120, editable: false,
				},
				{display: '科室年度预算', name: 'yearValue', align: 'right', width: 120, editable: false,
					render:function(ui) {
						if (ui.rowData.yearValue) {
							return formatNumber(ui.rowData.yearValue, 2, 1);
						}
					}
				},
				{display: '01月(E)', name: 'm01', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m01, 2, 1);
					}
				},
				{display: '02月(E)', name: 'm02', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m02, 2, 1);
					}
				},
				{display: '03月(E)', name: 'm03', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m03, 2, 1);
					}
				},
				{display: '04月(E)', name: 'm04', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m04, 2, 1);
					}
				},
				{display: '05月(E)', name: 'm05', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m05, 2, 1);
					}
				},
				{display: '06月(E)', name: 'm06', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m06, 2, 1);
					}
				},
				{display: '07月(E)', name: 'm07', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m07, 2, 1);
					}
				},
				{display: '08月(E)', name: 'm08', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m08, 2, 1);
					}
				},
				{display: '09月(E)', name: 'm09', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m09, 2, 1);
					}
				},
				{display: '10月(E)', name: 'm10', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m10, 2, 1);
					}
				},
				{display: '11月(E)', name: 'm11', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m11, 2, 1);
					}
				},
				{display: '12月(E)', name: 'm12', align: 'right', width: 120, dataType: "float",
					render:function(ui) {
						return formatNumber(ui.rowData.m12, 2, 1);
					}
				}
			],
			dataModel: {
				method: 'POST',
				location: 'remote',
				url: '',
				recIndx: 'year'
			},
			usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
			addRowByKey: true, inWindowHeight: true, freezeCols: 6,
			toolbar: {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					{ type: "button", label: '增量生成', icon: 'plus', listeners: [{ click: generate }] },
					{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
					{ type: "button", label: '下载导入模板', icon: 'arrowthickstop-1-s', listeners: [{ click: downTemplate }] },
					{ type: "button", label: '导入', icon: 'arrowthick-1-n', listeners: [{ click: imp }] },
					{ type: "button", label: '计算', icon: 'calculator', listeners: [{ click: collect }] },
					{ type: "button", label: '打印', icon: 'script', listeners: [{ click: printData }] },
					{ type: "button", label: '预算分解维护', icon: 'folder-open', listeners: [{ click: openRatePage }] },
				]
			}
        });
    }
    
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.year + "@" +
						this.rowData.index_code + "@" +
						this.rowData.dept_id
					)
				} else {
					deletePageRow.push(this);
				}
			});
            $.etDialog.confirm('确定删除?', function (yes){
         		if(ParamVo.length > 0){
         			ajaxPostData({
         			    url: 'deleteBudgWorkDeptMonthDown.do?isCheck=false',
         			    data: {ParamVo : ParamVo.toString()},
         			    success: function (responseData) {
         			    	query();
         			    }
         			})
         		}else if(deletePageRow.length > 0 ){
   	            	grid.deleteRows(deletePageRow);
   	            	$.etDialog.success("删除成功!");
   	            }
	        }); 
        }
   	}
    function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgWorkDeptMonthImportPage.do?isCheck=false'
		});
		layer.full(index);
	}	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "group_id="+vo[0] +"&hos_id="+vo[1]+"&copy_code="+vo[2] +"&year="+vo[3] +"&index_code="+vo[4] + "&dept_id="+vo[5]  
		 
		parent.$.etDialog.open({ url : 'hrp/budg/business/compilationplan/downtoup/deptmonthbudg/budgWorkDeptMonthUpdatePage.do?isCheck=false&'+parm,
			height:500,width:700,
			title:'预算分解维护',isMax:true,
			frameName: window.name,
    	}); 
    }
    
    function openRatePage(){
    	 var year = year_input.getValue() ;
    	 index_code = index_code_select.getValue();
    	 var parm = "year="+year +"&index_code="+index_code 
    	 parent.$.etDialog.open({ url : 'hrp/budg/business/compilationplan/downtoup/deptmonthbudg/budgWorkDeptMonthUpdatePage.do?isCheck=false&'+parm,
    		height:500,width:700,
 			title:'预算分解维护',isMax:true,
 			frameName: window.name,
     	}); 
    }
	//修改保存
    function save(){
    	var data = grid.getChanges();
		if( data.updateList.length == 0 ){
    		$.etDialog.error('没有需要保存的数据');
    		return;
    	}
		var ParamVo =[]; 
	 	$(data.updateList).each(function (){
			ParamVo.push( this.year +"@"+ this.index_code +"@"+ "01" +"@"+ this.dept_id  +"@"+ (this.m01? this.m01:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "02" +"@"+ this.dept_id  +"@"+ (this.m02? this.m02:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "03" +"@"+ this.dept_id  +"@"+ (this.m03? this.m03:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "04" +"@"+ this.dept_id  +"@"+ (this.m04? this.m04:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "05" +"@"+ this.dept_id  +"@"+ (this.m05? this.m05:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "06" +"@"+ this.dept_id  +"@"+ (this.m06? this.m06:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "07" +"@"+ this.dept_id  +"@"+ (this.m07? this.m07:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "08" +"@"+ this.dept_id  +"@"+ (this.m08? this.m08:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "09" +"@"+ this.dept_id  +"@"+ (this.m09? this.m09:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "10" +"@"+ this.dept_id  +"@"+ (this.m10? this.m10:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "11" +"@"+ this.dept_id  +"@"+ (this.m11? this.m11:"-1") );
	       	ParamVo.push( this.year +"@"+ this.index_code +"@"+ "12" +"@"+ this.dept_id  +"@"+ (this.m12? this.m12:"-1") );
		 })
		 ajaxPostData({
		    url: 'updateBudgWorkDeptMonthDown.do?isCheck=false',
		    data: {ParamVo : ParamVo.toString()},
		    success: function (responseData) {
		    	query();
		    }
		 })
    }
	//计算
    function collect(){
		var year = year_input.getValue() ;
		index_code = index_code_select.getValue();	   
		if( !year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		if( !index_code){
			$.etDialog.error('预算指标不能为空');
			return false ;
		}
		ajaxPostData({
		    url: 'collectBudgWorkDeptMonthDown.do?isCheck=false&year='+year+'&index_code='+index_code,
		    data: '',
		    success: function (responseData) {
		    	query();
		    },
		    error: function(responseData){
		    	//$.etDialog.error(responseData.error,'','',{width:500,resize:true});
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
    			
      		title: "科室月份业务预算",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptMonthService",
   			method_name: "getPrintData",
   			bean_name: "budgWorkDeptMonthService",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
    	printPara['year']=year_input.getValue();
    	printPara['index_code']=index_code_select.getValue();
    	printPara['dept_id']=dept_name_select.getValue();
		
       	officeGridPrint(printPara);
    }

    //增量生成
    function generate(){
 	   	var year = year_input.getValue();
 	   	if(year){
	 	   	ajaxPostData({
			    url: "generate.do?isCheck=false&year="+year,
			    data: '',
			    success: function (responseData) {
			    	query();
			    },
			})
 	   	}else{
 	   		$.etDialog.error("预算年度不能为空");
 	   	}
    }
	
    //键盘事件
    function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('G', generate);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('P', printData);
		hotkeys('I', imp);
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
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
