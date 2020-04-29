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
    var gridRight;
    var year_input;
    var index_code_select;
    var dept_id_select;
    var budg_year ;
    var year;
    var index_code;
    var dept_id;
	var rowidex ;
    
    //打印 单元格格式化 用
    var renderFunc = {
		count_value : function(value){ //预算值
	  		return formatNumber(value, 2, 1);
	  	}, 
     	budg_value : function(value){ //预算值
  			return formatNumber(value, 2, 1);
  		},
  		dept_suggest_sum : function(value){ //科室意见汇总
  			return formatNumber(value, 2, 1);
  		},
  	};
    
    $(function ()
    {
    	loadHead(null);
    	loadHeadDetail(null);
    	//加载数据
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
			onChange: function(value){
				index_code = value ;
				reloadDeptName(value);
				query();
			}
		});
		function reloadSubjName(value){
			index_code_select.reload({
				url:"../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=03&edit_method=04",
				para:{
					budg_year:value
				}
			})
		};
		
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
    	var params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
			{ name: 'budg_level', value: "03" },
			{ name: 'edit_method', value: "04" },
		]
		//加载查询条件
		grid.loadData(params,"queryProbDYBudgDown.do?isCheck=false&budg_level=03&edit_method=04");
     }

    function  queryRight(){
    	parms=[];
		//根据表字段进行添加查询条件
		parms.push({name:'year',value:year }); 
		parms.push({name:'index_code',value:index_code}); 
		parms.push({name:'dept_id',value:dept_id}); 
		//加载查询条件
		gridRight.loadData(parms,"queryBudgWorkDeptRate.do?isCheck=false");
	}
    
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
	       	   { display: '预算年度', name:'year', align: 'center',width:80,editable:setEdit ,
		        	   editor : {
							type : 'select',
							keyField : 'year',
							url : '../../../../../queryBudgYear.do?isCheck=false',
							change:function(rowdata,celldata){
		               		 	grid.updateRow(celldata.rowIndx,{index_code:"",index_name:""})
		               	 	}
		       	 		}
			 		},
		        { display: '指标编码', name: 'index_code', align: 'left',editable:false ,width:100,
			 		},
			 	{ display: '指标名称', name: 'index_name', align: 'left',editable:setEdit ,width:120,
			 		   editor : {
						   type : 'select',
						   keyField : 'index_code',
						   url : 'queryBudgIndex.do?isCheck=false&budg_level=01&edit_method=04',
						   change :function(rowdata,celldata){
		               		 	grid.updateRow(celldata.rowIndx,{dept_code:"",dept_name:""})
		               	   } ,
						   //与年度联动查询
		           	       create:function(rowdata,celldata,setting){
		           	    	   if(rowdata.year){
		           	    		   setting.url = 'queryBudgIndex.do?isCheck=false&budg_level=03&edit_method=04&budg_year='+rowdata.year;
		           	    	   }else{
		           	    		   $.etDialog.warn('请先填写年度');
		           	    	       return false ;
		           	    	   }
		           	       }
						} 
		 			},
				 { display: '科室编码', name: 'dept_code', align: 'left',width:80,editable:false,
				 		},
				 { display: '科室名称', name: 'dept_name', align: 'left',width:120,editable:setEdit,
				 			editor:{
				 				type:'select' ,
				 				keyField:'dept_id',
				 				url:'../../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code=',
				 				change:reloadDeptCode,
				 				//与指标联动查询
			               	    create:function(rowdata,celldata,setting){
			               	    	 if(rowdata.index_code){
			               	    		 setting.url = '../../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code='+rowdata.index_code;
			               	    	 }else{
			               	    		 $.etDialog.warn('请先填写指标名称');
			               	    		 return false ;
			               	    	 }
			               	    }
				 			}
				 		},
				 { display: '计算值', name: 'count_value', align: 'right',width: 120,editable:false,
				 			render:function(ui) {
								if (ui.rowData.count_value) {
									return formatNumber(ui.rowData.count_value, 2, 1);
								}
							}
				 		},
                 { display: '预算值(E)', name: 'budg_value', align: 'right',dataType: "float", width: 120,
				 			render:function(ui) {
								if (ui.rowData.budg_value) {
									return formatNumber(ui.rowData.budg_value, 2, 1);
								}
							}
				 		},
				 { display: '说明(E)', name: 'remark', align: 'left',dataType: "string", minWidth: 150,
				 		}
            ],
            dataModel: {
				method: 'POST',
				location: 'remote',
				url: '',
				recIndx: 'year'
			},
			usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
			addRowByKey: true, inWindowHeight: true,
			rowSelect: queryRightDate,
			toolbar: {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					{ type: "button", label: '增量生成', icon: 'plus', listeners: [{ click: generate }] },
					{ type: "button", label: '添加行', icon: 'plus', listeners: [{ click: add_Row }] },
					{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
					{ type: "button", label: '下载导入模板', icon: 'arrowthickstop-1-s', listeners: [{ click: downTemplate }] },
					{ type: "button", label: '导入', icon: 'arrowthick-1-n', listeners: [{ click: imp }] },
				]
			}
	    });
	}
    
	function loadHeadDetail() {
		gridRight = $("#maingridRight").etGrid({
			columns: [
				{
					display: '运营尺度(E)', name: 'measure_name', align: 'center', dataType: "string", width: "25%"
				},
				{
					display: '运营预期(E)', name: 'measure_value', align: 'right', dataType: "float", width: 100,
					render:function(ui) {
						if (ui.rowData.measure_value) {
							return formatNumber(ui.rowData.measure_value, 2, 1);
						}
					},
				},
				{
					display: '概率(E)', name: 'rate', align: 'right', dataType: "float", width: 80,
					render:function(ui) {
						if (ui.rowData.rate) {
							return formatNumber(ui.rowData.rate, 2, 0) + "%";
						}
					}
				},
				{
					display: '计算值', name: 'count_value', align: 'right', minWidth: "100",
					render:function(ui) {
						return formatNumber(ui.rowData.count_value == null ? 0 : ui.rowData.count_value, 2, 1);
					}
				}
			],
			dataModel: {
				method: 'POST',
				location: 'remote',
				url: '',
				recIndx: 'measure_name'
			},
			usePager: false, width: '100%', height: '100%', checkbox: false, editable: true,
			addRowByKey: true, inWindowHeight: true,
			toolbar: {
				items: [
					{ type: "button", label: '生成', icon: 'plus', listeners: [{ click: generateRight }] },
					{ type: "button", label: '添加行', icon: 'plus', listeners: [{ click: addRow }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: removeRows }] },
					{ type: "button", label: '计算', icon: 'calculator', listeners: [{ click: count }] },
				]
			},
			summary: { //  前台渲染摘要行    摘要行集合    
				totalColumns: ['rate', 'count_value'], //合计冻结行 
				keyWordCol: 'measure_name', //关键字所在列的列名
			},
			load: function () {
				gridRight.refreshSummary();
			}
		});
	}

	//选择科室后 更新科室编码 
    function reloadDeptCode(rowdata,celldata){
    	setTimeout(function () {
    		grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]})
    	}, 10);
    }
	
  //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室年度业务预算-確定预算";
    }
  	//添加行
	function add_Row() {
		grid.addRow();
	}
    //添加可编辑行
    function addRow(){
    	gridRight.addRow();
    }
    
 // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
	function setEdit(ui) {
		if (ui.rowData && ui.rowData.group_id) {
			return false;
		} else {
			return true;
		}
	}
    
	// 选中 左侧行数据 加载右侧概率数据
	function queryRightDate(event, ui) {
		year = ui.rowData.year;
		index_code = ui.rowData.index_code;
		dept_id = ui.rowData.dept_id;
		queryRight();	
	}
    
    //保存数据
	function save() {
		var data = grid.getChanges();
		var ParamVo = [];
		if (data.addList.length > 0 || data.updateList.length > 0) {
			if (data.addList.length > 0) {
				var addData = data.addList;
				if (!validateGrid(addData)) {
					return false;
				}
				$(addData).each(function () {
					ParamVo.push(
						this.year   +"@"+ 
						this.index_code  +"@"+ 
						this.dept_id  +"@"+ 
						this.dept_code  +"@"+ 
						(this.count_value? this.count_value:"")  +"@"+ 
						(this.budg_value? this.budg_value:"")  +"@"+ 
	   					(this.remark?this.remark:"")   	+"@"+ 
						this._rowIndx + "@" +
						//添加数据标识
						'1' + "@" +
						(this.detail?JSON.stringify(this.detail.Rows):"-1") + "@"
					)
				});
			}
			if (data.updateList.length > 0) {
				var updateData = data.updateList;
				$(updateData).each(function () {
					ParamVo.push(
						this.year   +"@"+ 
						this.index_code  +"@"+ 
						this.dept_id  +"@"+ 
						this.dept_code  +"@"+
						(this.count_value? this.count_value:"")  +"@"+ 
						(this.budg_value? this.budg_value:"")  +"@"+ 
	   					(this.remark?this.remark:"")   	+"@"+ 
						this._rowIndx + "@" +
						//修改数据标识
						'2' + "@" +
						(this.detail?JSON.stringify(this.detail.Rows):"-1") + "@"
					)
				});
			}
			ajaxPostData({
 			    url: 'saveProbDYBudgDown.do?isCheck=false',
 			    data: {ParamVo : ParamVo.toString()},
 			    success: function (responseData) {
 			    	query();
 			    	year = "";
					index_code = "";
					queryRight()
 			    }
 			})
		} else {
			$.etDialog.warn('没有需要保存的数据!');
		}
	}
 
    function remove(){
    	
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.warn('请选择行');
        }else{
             var ParamVo =[];
             var deleteDate = [];
             $(data).each(function (){
             	if(!this.rowData.group_id){
             		deleteDate.push(this);
             	}else{
             		ParamVo.push(
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.year   +"@"+ 
						this.rowData.dept_id   +"@"+ 
						this.rowData.index_code 
					) 
             	}
			});
            $.etDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		if(ParamVo.length > 0){
            			ajaxPostData({
    	                    url: "deleteProbDYBudgDown.do?isCheck=false",
    	                    data: { ParamVo: ParamVo.toString() },
    	                    success: function(responseData) {
    	                        query();
    	                        year = "";
								index_code = "";
								queryRight();
    	                    }
    	                });
            		}else if (deleteDate.length > 0) {
						grid.deleteRows(deleteDate);
						year = "";
						index_code = "";
						queryRight();
						$.etDialog.success("删除成功!");
					}
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
			content : 'probDYBudgImportPage.do?isCheck=false'
		});
		layer.full(index);
    }	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
    //增量生成
    function generate(){
 	   	var year = year_input.getValue();
 	   	if(year){
 	   		ajaxPostData({
			    url: "generate.do?isCheck=false&year="+year,
			    data: {},
			    success: function (responseData) {
			    	query();
			    }
			})
 	   	}else{
 	   		$.etDialog.warn("预算年度不能为空");
 	   	}
    }
    
    //右侧grid 生成
    function generateRight(){
		var data = grid.selectGet();
		var rightData = gridRight.getAllData();
    	if(rightData != null ){
    		$.etDialog.warn('所选数据已生成概率数据,不能再生成');
    	}else{
    		if(data.length == 1){
    			ajaxPostData({
    			    url: "setProbBudgRate.do?isCheck=false",
    			    data: {},
    			    success: function (responseData) {
    			    	gridRight.deleteRows(gridRight.getAllData());
    					gridRight.addRows(responseData.Rows);
    					gridRight.refreshSummary();
    			    }
    			})
        	}else{
        		$.etDialog.warn('请选择一行数据进行生成');
        	}
    	}
    }
    
    //右侧grid 删除
	function removeRows(){
		var data = gridRight.selectGet();
        if (data.length == 0){
        	$.etDialog.warn('请选择行');
        }else{
        	gridRight.deleteRows(data);
        }
        gridRight.refreshSummary();
    }
    
	//右侧grid 计算
	function count(){
		var data = gridRight.getAllData();
		var dataL = grid.selectGet();
		if(data == null){
			$.etDialog.warn('没有需要计算的数据');
		}else{
			if(dataL.length != 1){
				$.etDialog.warn('请在左侧选择一行数据再操作');
			}else{
				var count_value = 0; //存储 总计算值
				//var countValue = 0;// 存储 右侧表格每行 计算值
				var falg= 0 ; // 记录 总概率
				$(data).each(function (){
					falg = falg + Number(this.rate) ;
				})
				if(falg == 100){
					$(data).each(function (){
						this.count_value = Number(this.measure_value) * Number(this.rate) / 100 ;//计算右侧每行 计算值
						
						count_value = count_value + this.count_value ;
						//gridRight.updateCell('count_value',countValue,this);
					})
					
					grid.updateRow(dataL[0].rowData._rowIndx, { "count_value": count_value, "budg_value": count_value, detail: { "Rows": data, "Total": data.length } });

					gridRight.refreshView();

					gridRight.refreshSummary();

				}else{
					$.etDialog.warn('总概率不等于100%,不能计算');
				}
			}
		}
    }
	
	function updateTotal(){
		gridRight.updateTotalSummary();
	}
	function validateGrid(data) {
		var msg = "";
		var rowm = "";
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();
		$.each(data, function (i, v) {
			rowm = "";
			if (!v.year) {
				rowm += "[年度]、";
			}
			if (!v.index_name) {
				rowm += "[指标名称]、";
			}
			if (!v.dept_name) {
				rowm += "[科室名称]、";
			}
			if (!v.budg_value) {
				rowm += "[预算值]、";
			}

			if (rowm != "") {
				rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key = v.year + v.month + v.index_code + v.dept_id
			var value = "第" + (Number(v._rowIndx) + 1) + "行";
			if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
				targetMap.put(key, value);
			} else {
				msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
			}
		});
		if (msg != "") {
			$.etDialog.warn(msg);
			return false;
		} else {
			return true;
		}
	}
    //键盘事件
	  function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('I', imp);
		hotkeys('G', generate);
		hotkeys('T', generateRight);
		hotkeys('R', removeRows);
		hotkeys('C', count);
	 }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
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
			</tr>
		</table>
	</div>
    <div id="toptoolbar" ></div>
	<div >
	 	<div  style="float: left; width: 65%;">
	 	
			<div id="maingrid"></div>
		</div>
		<div  style="float: left; width: 35%;">
		
			 <div id="maingridRight"></div>
		</div>
    </div> 
</body>
</html>
