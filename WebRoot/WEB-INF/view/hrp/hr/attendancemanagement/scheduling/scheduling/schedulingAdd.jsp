<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,datepicker,select,validate,grid,dateSlider"
		name="plugins" />
</jsp:include>
<style>
body {
	margin: 0;
	font-size: 12px;
	/* background:#666; */
}

#box {
	/* width:400px; */
	height: 300px;
	margin: 20px 20px 0;
}

#tab_nav {
	margin: 0;
	padding: 0;
	height: 25px;
	line-height: 24px;
}

#tab_nav li {
	float: left;
	margin: 0 3px;
	list-style: none;
	border: 1px solid #999;
	border-bottom: none;
	height: 24px;
	width: 160px;
	text-align: center;
	background: #d6e4f4;
}

a {
	font: bold 14px/24px "微软雅黑", Verdana, Arial, Helvetica, sans-serif;
	color: #333;
	text-decoration: none;
}

a:hover {
	color: red;
}

#tab_content {
	/* 	width:398px; */
	height: 283px;
	border: 1px solid #999;
	font: bold 4em/273px "微软雅黑", Verdana, Arial, Helvetica, sans-serif;
	text-align: center;
	background: #FFF;
	overflow: hidden;
}

#t_1, #t_2, #t_3 {
	width: 100%;
	height: 283px;
}

p {
	background: red;
	float: left;
}

#tab_add {
	margin-left: 40px;
}

#add,#clear,#copy,#clearPb,#save,#close,#addAll{
	margin: 12px;
	box-sizing: border-box;
	height: 26px;
	padding-left: 10px;
	padding-right: 10px;
	border: 1px solid #aecaf0;
	background: #a1cef4;
	outline: none;
	border-radius: 2px;
	cursor: pointer;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
}

</style>
<script>
    var year, begin_date, end_date, attend_areacode, attend_pbrule, dateSilder;
    var currentDateList = []; // 当前列头所展示的所有日期列表
    var classSource = [];  //班次回调数据
    var deptSource = [];  //科室回调数据
    
    $(function() {
		initForm();
		leftGrid();
	});
    
	/*  左表 基础列头 */
	var leftcolumns = [ {
		display : '科室名称',
		name : 'dept_name',
		editable : false,
		width : 80
	},/*  {
		display : '护理等级',
		name : 'level_name',
		editable : false,
		hidden: true,
		width : 80
	},  */{
		display : '职工姓名',
		name : 'emp_name',
		editable : false,
		width : 80
	}, ];
	/* 改变排班规则 -》 改变日期滚动数据 */
	var changeDateSlider = function(value) {
		if (value == 0) {
			dateSilder.option({
				view : 'weeks',
				dateFormat : 'YYYY-MM-DD',
			})
		} else {
			dateSilder.option({
				view : 'months',
				dateFormat : 'YYYY-MM',
			})
		}
	}
	/* 生成 星期的列 */
	var genegrateWeeksColumns = function(date) {
		var columns = []
		var capitalMap = [ '一', '二', '三', '四', '五', '六', '日' ]
		currentDateList = []
		for (var i = 0; i < 7; i++) {
			var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD')
			currentDateList.push(theDate)
			columns.push({
				display : theDate + '(周' + capitalMap[i] + ')',
				//name: theDate,
				columns : [
				{
					display : '班次',
					name : 'attend_calssname--' + theDate,
					width : '60',
					editor : {
						type : 'select',
						keyField : 'attend_classcode--'+ theDate,
						source:classSource
					}
				},
				{
					display : '科室',
					name : 'each_dept_name--' + theDate,
					width : '60',
					editor : {
						type : 'select',
						keyField : 'dept--' + theDate,
						source:deptSource

					}
				}, ]
			})
		}
		return columns
	}
	/* 生成月份的列 */
	var genegrateMonthsColumns = function(date) {
		var columns = []
		var daysInMonth = moment(date, 'YYYY-MM').daysInMonth()
		currentDateList = []
		for (var i = 0; i < daysInMonth; i++) {
			var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD')
			currentDateList.push(theDate)
			columns.push({
				display : theDate,
				//name: theDate,
				columns : [
					{
						display : '班次',
						name : 'attend_calssname--' + theDate,
						width : '60',
						editor : {
							type : 'select',
							keyField : 'attend_classcode--'+ theDate,
							source:classSource

						}
					},
					{
						display : '科室',
						name : 'each_dept_name--' + theDate,
						width : '60',
						editor : {
							type : 'select',
							keyField : 'dept--' + theDate,
							source:deptSource
						}
					}, ]
			})
		}
		return columns
	}
	/* 改变左表的列 */
	var changeLeftGridColumns = function() {
		var options = dateSilder.getOptions()
		var backColumns = []
		if (options.view === 'weeks') {
			backColumns = genegrateWeeksColumns(options.activeDate)
		} else {
			backColumns = genegrateMonthsColumns(options.activeDate)
		}

		var newColumns = leftcolumns.concat(backColumns)
		leftGrid.option('columns', newColumns);
		leftGrid.refreshView();
	}
    var query = function (treeId) {
        var params = [];

            params = [
                { name: 'attend_areacode', value: attend_areacode.getValue() },
            ];
            leftGrid.loadData(params,'queryScheEmp.do?isCheck=false&attend_areacode='+ attend_areacode.getValue());
        }
	var initForm = function() {
		attend_areacode = $("#attend_areacode").etSelect({
			url : "queryAttendAreacode.do?isCheck=false",
			defaultValue : "none",
			onChange : function(value) {
				classSource = [];
				deptSource = [];
				changeLeftGridColumns();
				query();
					
				/* 班次 */
			    ajaxPostData({
					url: "../../queryCalssCode.do?isCheck=false&attend_areacode="+attend_areacode.getValue(),
					success: function (res) {
						$.each(res,function(index,value){
							classSource.push({label:value.text,id:value.id})
						})
					}
				});
			    
			    /* 科室 */
			    ajaxPostData({
			    	url: "../../queryCalssDept.do?isCheck=false&attend_areacode="+attend_areacode.getValue(),
					success: function (res) {
						$.each(res,function(index,value){
							deptSource.push({label:value.text,id:value.id})
						})
					}
				});
			}
		});
		attend_pbrule = $("#attend_pbrule").etSelect({
			options : [ {
				id : 0,
				text : '周'
			}, {
				id : 1,
				text : '月'
			}, ],
			onInit : function(value) {
				changeDateSlider(value)
				changeLeftGridColumns()
			},
			onChange : function(value) {
				changeDateSlider(value)
				changeLeftGridColumns()
			}
		});

		/* 日期滚动条 */
		dateSilder = $("#dateSlider").etDateSlider({
			view : 'weeks',
			dateFormat : 'YYYY-MM-DD',
			onChanged : function() {
				changeLeftGridColumns()
			}
		})
		
		$("#save").click(function() {
			if ( $('#attend_pbcode').val()== null || $('#attend_pbcode').val()== "") {
				$.etDialog.error('排班编码不能为空');
				return;
			} 
			if ( $('#attend_pbname').val()== null || $('#attend_pbname').val()== "") {
				$.etDialog.error('排班名称不能为空');
				return;
			} 
			if ( attend_areacode.getValue()== null || attend_areacode.getValue()== "") {
				$.etDialog.error('区域名称不能为空');
				return;
			}
			var data = leftGrid.getAllData()
			if (data ==null  || data.length ===0) {
				$.etDialog.error('请选择需要排班的人员');
				return;
			}
			
			data = schedulingCompileParams(data)

			ajaxPostData({
				url : 'addScheduling.do',
				data : {
					attend_areacode : attend_areacode.getValue(),
					attend_pbname : $('#attend_pbname').val(),
					attend_pbrule : attend_pbrule.getValue(),
					attend_pbnote : $('#attend_pbnote').val(),
					attend_pbcode : $('#attend_pbcode').val(),
					paramVo : JSON.stringify(data)
				},
		
				success : function(responseData) {
					$.etDialog.success(
							'保存成功',
							 function (index, el) {
								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
				                   parent.$.etDialog.close(curIndex); 
				                   parent.$.etDialog.open({
				                       url: 'hrp/hr/attendancemanagement/attend/updateSchedulingPage.do?isCheck=false&attend_pbcode='
				                   		+$('#attend_pbcode').val()+'&attend_pbrule='+attend_pbrule.getValue(),
				                   title: '排班修改',
				                   isMax: true,
				               	   frameName : window.name,
				                   width: $(window).width(),
				                   height: $(window).height(),
				               })
							    }
							)
				 
                   /*  var parentFrameName = parent.$.etDialog.parentFrameName;
                   var parentWindow = parent.window[parentFrameName];
                   parentWindow.query();  */
				},
			})
		});
		

		$("#close").click(function() {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		})
		$("#add").click(function() {
			if(attend_areacode.getValue() !=""){
				parent.$.etDialog.open({
					url : 'hrp/hr/attendancemanagement/attend/schedulingEmpPage.do?isCheck=false',
					width : $(window).width()-500,
					height : $(window).height()-200,
					frameNameObj : {
						'add' : window.name
					},
					title : '添加人员'
				});
			}else{
				 $.etDialog.error('请选区域');
				 return;
			}
				
		});
		//删除人员
		$("#clear").click(function() {

			var leftGridData = leftGrid.selectGet();
			if (leftGridData.length === 0) {
				$.etDialog.error('请选择行!');
				return;
			}
			
			leftGrid.deleteRows(leftGridData);
		
		})
		//清除排班
		$("#clearPb").click(function(){
			var allData = leftGrid.getAllData();
			allData.forEach(function (item) {
				var keys = Object.keys(item)
				keys.forEach(function (key) {
					if (key.indexOf('--') > 0) {
						item[key] = ''
					}
				})
				leftGrid.updateRow(item._rowIndx, item);
			})
		})
		//批量设置
		$("#addAll").click(function(){
			/* 获取勾选行数据 */
			 var data =leftGrid.selectGet();
			 var valarray = [];
			 for(var i =0; i<data.length; i++){
				 valarray.push(data[i].rowData)
			 };
			var selectedData = schedulingCompileParams(valarray);
			
			 data1 = selectedData;

            if (selectedData.length == 0) {
                $.etDialog.error('请选择行');
                return;
            }
			
            if (attend_areacode.getValue() == null|| attend_areacode.getValue() == "") {
				$.etDialog.error('请选区域');
				return;
			} else {
				parent.$.etDialog.open({
					url : 'hrp/hr/attendancemanagement/attend/addAllPage.do?isCheck=false&attend_areacode='+ attend_areacode.getValue()+'&selectedData='+ data1,
					width : 400,
					height : 400,
					frameName :window.name,
					title : '批量设置'
				});
			}
		})
		$("#copy").click(function() {
			var date=dateSilder.getValue();
			var theDateType = attend_pbrule.getValue() == '0' ? 'weeks' : 'months'
			var startDate = moment(date).subtract(1, theDateType).format('YYYY-MM-DD')
			var endDate = moment(date).subtract(1, 'days').format('YYYY-MM-DD')

			var data = leftGrid.selectGet();
			if (data.length === 0) {
				$.etDialog.error('请选择人员');
				return;
			}else{
				var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    param.push(rowdata);
                });
				
                //排班规则周
               	ajaxPostData({
   					url : 'copyWeek.do?isCheck=false',
   							data:{
   								date : date,
   								startDate : startDate,
   								endDate : endDate,
   								paramVo : JSON.stringify(param)
   							},
   					success : function(responseData) { 
   						// 根据起始日期，日期类型，生成对应的行数据
   						/// 根据原选择的行数据，遍历更新行。（可能问题，选择行和获得数据条数不一样导致报错）
   						var rows = schedulingDecompileParams(responseData.Rows, date, theDateType)
   						data.forEach(function (item, index) {
							leftGrid.updateRow(item.rowIndx, rows[index])
   						})
   					}
   				})
                	
                /* else{
				ajaxPostData({
					url : 'copyMonth.do?isCheck=false',
					success : function(responseData) {
						$("#attend_classcode").val(
								responseData.attend_classcode);

					}
				})
                } */
			}
				
		})
		
	
        var show_child_count = 1;
        $('#show_child').click(function () {
            show_child_count++;
            if (show_child_count % 2 === 0) {
                $(this).html("︾展开");
                $("#children_block").hide();
               /*  leftGrid.option('height', '100%+180'); */
                leftGrid.refresh();
            } else {
                $(this).html("︽ 收起");
                $("#children_block").show();
                /* leftGrid.option('height', '100%'); */
                leftGrid.refresh();
            }
        })
	};
	 



	var leftGrid = function() {
		var paramLeftObj = {
		    height: '100%',
		    inWindowHeight: true,
			checkbox : true,
			freezeCols : 2, //冻结两列
			showBottom:false,
			editable : true,
			columns : leftcolumns,
		};
		leftGrid = $("#leGrid").etGrid(paramLeftObj);
	};
</script>
</head>

<body>
	<div id="children_block">
		<table class="table-layout">
			<tr>
			    <td class="label "><font size="2" color="red">*</font>区域名称：</td>
				<td class="ipt"><select id="attend_areacode" style="width: 180px;"></select></td>
				<td class="label"><font size="2" color="red">*</font>排班编码：</td>
				<td class="ipt"><input id="attend_pbcode"  type="text" /></td>
				<td class="label"><font size="2" color="red">*</font>排班名称：</td>
				<td class="ipt"><input id="attend_pbname" type="text" /></td>
				
			</tr>
			<tr>
				<td class="label"><font size="2" color="red">*</font>排班规则：</td>
				<td class="ipt"><select id="attend_pbrule"
					style="width: 180px;"></select></td>
				<td class="label">备注：</td>
				<td class="ipt"><input id="attend_pbnote" type="text" /></td>

			</tr>
		</table>
	</div>
	<div id="tab_add">
		<button id="add">添加人员</button>
		<button id="clear">删除人员</button>
		<button id="addAll">批量设置</button>
		<button id="copy">复制上周/月</button>
		<button id="clearPb">清除排班</button>
		<button id="save">保存</button>
		<button id="close">关闭</button>
	<!-- 	是否显示护理等级： <select id="leve" style="width: 100px;"></select> 
		班次： <select id="attend_class" style="width: 100px;"></select>  -->
		<a href="javascript:;" id="show_child" style="text-decoration: none;padding-left:18%">︽收起</a>
	</div>
	<div>
		<div id="dateSlider"></div>
		<div id="leGrid"></div>
	</div>

</body>
</html>