<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="hr,dialog,datepicker,select,validate,grid,tab,dateSlider" name="plugins" />
</jsp:include>
<style>
  body {
	margin: 0;
	font-size: 12px;
}

#box {
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

 #pbsq,#pbfc{
    margin: 12px;
    font-size: 14px;
    font-weight: 700;
    color: red;
 }
.container {
	width:100%;
  	height:100%;
}

.ettab-container {
    width: 100%;
    height: 100%;
    border: 0;
    display: -webkit-flex;
    display: -ms-flex;
    display: flex;
    box-sizing:border-box;
}

.ettab-nav {
    padding: 0;
    margin: 0;
    width: 20%;
}

.ettab-nav .ettab-tab {
    padding: 0;
    margin: 0;
    width: 50%;
    box-sizing: border-box;
    height: 100%;
    text-align: center
}
.ettab-content {
    border-top: 1px solid #bed5f3;
}

.ettab-content .ettab-panel.ettab-active {
	display: flex;
    flex-direction: column;
} 

.togglebtn {
	position: absolute;
	background: url(/CHD-HRP/lib/ligerUI/skins/icons/toggle2.png) no-repeat 4px 4px;
	height: 24px;
	width: 24px;
	cursor: pointer;
	left: auto;
	top: -7px;
	right: 0px;
	border-radius: 50%;
	background-color: #ffffff;
	border: 1px solid #ddd;
}
.bgi {
	background-image: url(/CHD-HRP/lib/ligerUI/skins/icons/toggle1.png);
}

</style>
<script>
	var status,year, begin_date, end_date, attend_areacode,attend_name, attend_pbrule, dateSilder,dateSearchSilder,leftGrid,dateSearchGrid,paramLeftObj;
    var currentDateList = []; // 当前列头所展示的所有日期列表
    var classSource = [];  //班次回调数据
    var deptSource = [];  //科室回调数据
    
    $(function () {
        initForm();
        loadLeftGrid();
        loadClassDept();
        
     	// 折叠按钮
		$('.togglebtn').click(function () {
			$('.togglebtn').toggleClass('bgi');
			$('.table-layout').toggle();
			//grid.refreshView();
			leftGrid.refresh();
		})

        
        /* 判断是否已封存、已审核  */
        if("${state}"!="" || "${attend_pbcheck_state}"==1){
        	
        	attend_areacode.disabled();
        	attend_pbrule.disabled();
        	$('#attend_pbname').prop('disabled','disabled');
        	$('#attend_pbnote').prop('disabled','disabled');
       	   	btn=document.getElementById('save');
       	   	btn.disabled=true;
       	    btn1=document.getElementById('add');
       	    btn1.disabled=true;
       	    btn2=document.getElementById('addAll');
       	    btn2.disabled=true;
       	    btn3=document.getElementById('copy');
       	    btn3.disabled=true;
       	    btn4=document.getElementById('clearPb');
       	    btn4.disabled=true;
       	    btn5=document.getElementById('clear');
       	    btn5.disabled=true;
        	   
       	   if("${attend_pbcheck_state}"==1){
       		   $('#pbfc').hide();
       		   $('#pbsq').show()
       	   }else{
       		   $('#pbfc').show();
       		   $('#pbsq').hide()
       	   }
        	  
		}else{
       	   $('#pbfc').hide();
       	   $('#pbsq').hide()
		};

        tab = $("#tab").etTab({
			onChange: function (item) {
				// 判断初始化页面是否加载过grid，加载过的grid不执行二次加载
				if (!dateSearchGrid) {
					loadDateSearchGrid();
				}
	           	changeSearchLeftGridColumns();
				//queryGrid()
			}
        });
    });
    
    /* 加载表格中班次和科室数据 */
    var loadClassDept = function(){
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
    };

   /* 左表 基础列头  */
    var leftcolumns = [{
            display: '科室名称',
            name: 'dept_name',
            editable: false,
            width: 120
        },{
            display: '职工姓名',
            name: 'emp_name',
            editable: false,
            width: 60
        },
    ];
   
    /* 排班查询左表基础列头  */
    var searchLeftcolumns = [{
            display: '科室名称',
            name: 'dept_name',
            editable: false,
            width: 120
        },{
    		display : '护理等级',
    		name : 'level_code',
    		editable : false,
    		width : 60
    	},{
            display: '职工姓名',
            name: 'emp_name',
            editable: false,
            width: 70
        },
    ];
   
   /* 改变排班规则 -》 改变日期滚动数据 */
    var changeDateSlider = function (value) {
    	if (value == 0) {
    		dateSilder.option({
        		view: 'weeks',
        		dateFormat: 'YYYY-MM-DD',
        	});
    		dateSearchSilder.option({
        		view: 'weeks',
        		dateFormat: 'YYYY-MM-DD',
        	});
    	} else {
    		dateSilder.option({
        		view: 'months',
        		dateFormat: 'YYYY-MM',
        	});
    		dateSearchSilder.option({
        		view: 'months',
        		dateFormat: 'YYYY-MM',
        	});
    	}
    };
    
   /* 生成 星期的列 */
    var genegrateWeeksColumns = function (date) {
    	var columns = []
    	var capitalMap = ['一', '二', '三', '四', '五', '六', '日']
    	currentDateList = []
    	for (var i = 0; i < 7; i++) {
   			var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD')
    		currentDateList.push(theDate)
    		columns.push({
    			display: theDate + '(周' + capitalMap[i] + ')',
    			//name: theDate,
    			columns: [
   			      	{
       					display: '班次',
       					name: 'attend_classname--' + theDate,
       					width: '60',
       					editor: {
       						type: 'select',
       						keyField: 'attend_classcode--' + theDate,
       						source:classSource,		
       						
       					}
       				},
       				{
       					display: '科室',
       					name: 'each_dept_name--' + theDate,
       					width: '60',
       					editor: {
       						type: 'select',
       						keyField: 'dept--' + theDate,
       						source:deptSource,	
       					}
       				},
    			]
    		})
    	}
    	return columns
    };
   /* 生成月份的列 */
	var genegrateMonthsColumns = function (date) {
		var columns = []
		var daysInMonth = moment(date, 'YYYY-MM').daysInMonth()
		currentDateList = []
    	for (var i = 0; i < daysInMonth; i++) {
    		var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD')
    		currentDateList.push(theDate)
    		columns.push({
    			display: theDate,
    			//name: theDate,
    			columns: [
    				{
    					display: '班次',
    					name: 'attend_classname--' + theDate,
    					width: '60',
    					editor: {
    						type: 'select',
    						keyField: 'attend_classcode--' + theDate,
							source:classSource,
    						
    					}
    				},
    				{
    					display: '科室',
    					name: 'each_dept_name--' + theDate,
    					width: '60',
    					editor: {
    						type: 'select',
    						keyField: 'dept--' + theDate,
    						source:deptSource,
    					}
    				},
    			]
    		})
    	}
    	return columns
    };
   /* 改变左表的列 */
	var changeLeftGridColumns = function () {
		var options = dateSilder.getOptions();
   		var backColumns = []
   		if (options.view === 'weeks') {
   			backColumns = genegrateWeeksColumns(options.activeDate)
   		} else {
   			backColumns = genegrateMonthsColumns(options.activeDate)
   		}

        var newColumns = leftcolumns.concat(backColumns)
        leftGrid.option('columns', newColumns);
        leftGrid.refreshView();
	};
	
	/* 排班查询改变左表的列 */
	var changeSearchLeftGridColumns = function () {
		var searchOptions = dateSearchSilder.getOptions();
   		var searchBackColumns = [];
   		
   		if (searchOptions.view === 'weeks') {
   			searchBackColumns = genegrateWeeksColumns(searchOptions.activeDate);
   		} else {
   			searchBackColumns = genegrateMonthsColumns(searchOptions.activeDate);
   		}
   		
        var newSearchColumns = searchLeftcolumns.concat(searchBackColumns);
        dateSearchGrid.option('columns', newSearchColumns);
        dateSearchGrid.refreshView();
	};
	
	/* 查询排班 */
    var queryGrid = function(){
    	
		var params = [];
   		params = [
            { name: 'emp_id', value: attend_name.getValue()},
            { name:'attend_classcode',value: attend_class.getValue()},
        ];
    	
    	
    	dateSearchGrid.loadData(params,'queryPBClass.do?isCheck=false');
    };
    var queryScheEmp = function (treeId) {
        var params = [];

            params = [
                { name: 'attend_areacode', value: attend_areacode.getValue() },
            ];
            leftGrid.loadData(params,'queryScheEmp.do?isCheck=false&attend_areacode='+ attend_areacode.getValue());
    };
    var initForm = function () {
		
        attend_areacode = $("#attend_areacode").etSelect({
            url: "queryAttendAreacode.do?isCheck=false",
            defaultValue: "${attend_areacode}",
        	onChange : function(value) {
        		/* classSource = [];
				deptSource = [];
				changeLeftGridColumns();
				queryScheEmp();
				loadClassDept(); */
			}
        });
        
        /* 排班修改日期滚动条 */
        dateSilder = $("#dateSlider").etDateSlider({
        	view: 'weeks',
        	defaultDate: "${fristDate}",
        	dateFormat: 'YYYY-MM-DD',
        	onChanged: function () {
        		changeLeftGridColumns()
        	}
        });
        /* 排班查询日期滚动条 */
        dateSearchSilder = $("#dateSearchSilder").etDateSlider({
        	view: 'weeks',
        	defaultDate: "${fristDate}",
        	dateFormat: 'YYYY-MM-DD',
        	onChanged: function () {
        		changeSearchLeftGridColumns()
        	}
        });
        /* 排班规则 */
        attend_pbrule = $("#attend_pbrule").etSelect({
            options: [{
                    id: 0,
                    text: '周'
                },
                {
                    id: 1,
                    text: '月'
                },
            ],
            defaultValue: "${attend_pbrule}",
            onInit: function (value) {
            	changeDateSlider(value);
            	changeLeftGridColumns();
			},
			onChange: function (value) {
				changeDateSlider(value);
				changeLeftGridColumns();
			}
        });
        
        /* 倒班规则 */
        db_gz = $("#db_gz").etSelect({
            options: [{
                    id: 1,
                    text: '一班倒'
                },
                {
                    id: 2,
                    text: '二班倒'
                },
                {
                    id: 3,
                    text: '三班倒'
                }
            ],
            defaultValue: "${db_gz}",
            onInit: function (value) {
			},
			onChange: function (value) {
			}
        });
        
        
        
        /* 姓名 */
        attend_name = $("#attend_name").etSelect({
            url: "../../queryEmpSelect.do?isCheck=false",
            defaultValue: "none",
            onChange: function(value){
            }
        });
        
        /* 班次 */
		attend_class =$("#attend_class").etSelect({
			url : "../../queryCalssCode.do?isCheck=false&attend_areacode="+ attend_areacode.getValue(),
			defaultValue : "none",
			checkboxMode : true,
			onChange: function(value){
			}
		});
        
        
        $("#save").click(function () {
        	
        	if("${attend_pbcheck_state}"!=0){
        		$.etDialog.error('已审签不能保存');
				return;
        	}
        	
        	if ( $('#attend_pbcode').val()== null || $('#attend_pbcode').val()== "") {
				$.etDialog.error('排班编码不能为空');
				return;
			} 
        	if ( $('#attend_pbname').val()== null || $('#attend_pbname').val()== "") {
				$.etDialog.error('排班名称不能为空');
				return;
			} 
        	
        	if ( attend_areacode.getValue()== "") {
				$.etDialog.error('排班区域不能为空');
				return;
			} 
        	
        	if ( db_gz.getValue()== "") {
				$.etDialog.error('倒班规则不能为空');
				return;
			} 
        	
        	var data = leftGrid.getAllData()
       		if (data ==null  || data.length ===0) {
				$.etDialog.error('请选择需要排班的人员');
				return;
			}
							
        	data = schedulingCompileParams(data);
        	//console.log(JSON.stringify(data))
            ajaxPostData({
                url: 'updateScheduling.do',
                data: {
                	old_attend_pbcode:"${attend_pbcode}",
                	attend_pbcheck_state:"${attend_pbcheck_state}",
                    attend_areacode: attend_areacode.getValue(),
                    attend_pbname :$('#attend_pbname').val(),
                    attend_pbrule: attend_pbrule.getValue(),
                    db_gz: db_gz.getValue(),
                    attend_pbnote:$('#attend_pbnote').val(),
                    attend_pbcode :$('#attend_pbcode').val(),
                 	paramVo : JSON.stringify(data)
                },
                
                success: function (responseData) {
             	/*    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                   parent.$.etDialog.close(curIndex);
                   var parentFrameName = parent.$.etDialog.parentFrameName;
                   var parentWindow = parent.window[parentFrameName];
                   parentWindow.query();  */

                },
            }) 
        });
        $("#close").click(function () {
            var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            parent.$.etDialog.close(curIndex);
        })
        $("#add").click(function () {
            if (attend_areacode.getValue() == null || attend_areacode.getValue() =="") {
                $.etDialog.error('请选区域');
                return;
            } else {
                parent.$.etDialog.open({
                    url: 'hrp/hr/attendancemanagement/attend/schedulingEmpPage.do?isCheck=false&attend_areacode=' +
                        attend_areacode.getValue(),
            			width : $(window).width()-200,
    					height : $(window).height()-100,
                    frameNameObj: {
                        'add': window.name
                    },
                    title: '添加人员'
                });
            }
        });
        
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
		});
    	$("#clear").click(function() {

			var leftGridData = leftGrid.selectGet();
			if (leftGridData.length === 0) {
				$.etDialog.error('请选择行');
				return;
			}
			var ParamVo = [];
	     	  $(leftGridData).each(function () {
	               var rowdata = this.rowData;
	               ParamVo.push(rowdata);
	             
	           });
	     	 var data = {
		                attend_pbcode:"${attend_pbcode}" ,
		                ParamVo: JSON.stringify(ParamVo),
		            };
	         $.etDialog.confirm('确定删除?', function () {
	         ajaxPostData({
	         	 url: 'deleteSchedulingEmp.do?isCheck=false',
	             data: data,
	             success: function () {
	            	 leftGrid.deleteRows(leftGridData);
	            	 location.reload();
	             }
	         }) });
			
		});
		$("#clearPb").click(function(){
			var allData = leftGrid.getAllData()
			allData.forEach(function (item) {
				var keys = Object.keys(item)
				keys.forEach(function (key) {
					if (key.indexOf('--') > 0) {
						item[key] = ''
					}
				})
				leftGrid.updateRow(item._rowIndx, item)
			})
		});
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
			}
		});
		/* 查询 */
		$("#query").click(function(){
			queryGrid();
		});
		
		/* 是否显示护理等级 */
		$(".leve").click(function(){
			if(this.value == '0'){
				dateSearchGrid.getColumns()[1].hidden=false;
				dateSearchGrid.refresh();
        	} else{
        		dateSearchGrid.getColumns()[1].hidden=true;
				dateSearchGrid.refresh();
        	}
		});
		
		/* 是否显示出勤科室 */
		$(".cq_dept").click(function(){
			if(this.value == '0'){
				leftGrid.getColumns()[1].hidden=true;
				leftGrid.refresh();
        	} else{
        		leftGrid.getColumns()[1].hidden=false;
        		leftGrid.refresh();
        	}
		});
		
    	
	};
	var dataModel = {
		url: 'queryPB.do?isCheck=false&attend_pbcode='+'${attend_pbcode}',
        getData: function (res) {
        	if(res.error){
        		$.etDialog.error(res.error);
        		return;
        	}
          	var data = res.Rows;
          	res.Rows = schedulingDecompileParams(data);
          	return res;
        },
	};
	
    var loadLeftGrid = function () {
        paramLeftObj = {
   			height: '100%',
   		    inWindowHeight: true,
   			checkbox : true,
   			freezeCols : 2, //冻结两列
   			//editable : true,
   			showBottom:false,
   			columns : leftcolumns,
            dataModel: dataModel
        };
        
        if("${state}"!="" || "${attend_pbcheck_state}"==1){
        	 paramLeftObj.editable = false;
        }else{
        	paramLeftObj.editable = true;
        };
        
        leftGrid = $("#leGrid").etGrid(paramLeftObj);
    };
    
    
    /* 排班查询表 */
    var loadDateSearchGrid = function () {
        var paramSearchLeftObj = {
   			height: '100%',
   		    inWindowHeight: true,
   			freezeCols : 3, //冻结两列
   			editable : false,
   			columns : searchLeftcolumns,
   			showBottom:false,
            dataModel: dataModel
        };
        dateSearchGrid = $("#dateSearchGrid").etGrid(paramSearchLeftObj);
    };
</script>
</head>

<body>
	<div class="container">
		<div id="tab">
       		<div title="修改排班" tabid="0">
               	<div class="tab_content">
            		<table class="table-layout" id="children_block">
						<tr>
							<td class="label"><font size="2" color="red">*</font>排班编码：</td>
							<td class="ipt"><input id="attend_pbcode" value="${attend_pbcode}"  type="text" disabled="disabled" /></td>
							<td class="label"><font size="2" color="red">*</font>排班名称：</td>
							<td class="ipt"><input id="attend_pbname" value="${attend_pbname}" type="text" /></td>
							<td class="label "><font size="2" color="red">*</font>区域名称：</td>
							<td class="ipt"><select id="attend_areacode" style="width: 180px;"></select></td>
						</tr>
						<tr>
							<td class="label"><font size="2" color="red">*</font>排班规则：</td>
							<td class="ipt"><select id="attend_pbrule"style="width: 180px;"></select></td>
							<td class="label"><font size="3" color="red">*</font>倒班规则：</td>
							<td class="ipt"><select id="db_gz"style="width: 180px;"></select></td>
							<td class="label">备注：</td>
							<td class="ipt"><input id="attend_pbnote"  value="${attend_pbnote}"  type="text" /></td>
						</tr>
						
					</table>
				  
					<div id="tab_add">
				        <button id="add">添加人员</button>
						<button id="clear">删除人员</button>
						<button id="addAll">批量设置</button>
						<button id="copy">复制上周/月</button>
						<button id="clearPb">清除排班</button>
						<button id="save">保存</button>
						<button id="close">关闭</button>
						
						
						<span id="pbfc" >已封存</span>
						<span id="pbsq" >已审签</span>
						
						出勤科室：
						<input class="cq_dept" type="radio" name="cq_dept"   value='1' />
						<label>显示</label>
						<input class="cq_dept" type="radio" name="cq_dept"   value='0' checked/>
						<label>不显示</label>
					</div>
					<div class="togglebtn"></div>
					
			 		<div>
						<div id="dateSlider"></div>
						<div id="leGrid"></div>
					</div>
				</div>
           	</div>
			<div title="查询排班" tabid="1">
				<div class="tab_content">
					<table class="table-layout">
						<tr>
							<td class="label">姓名：</td>
							<td class="ipt" style="width:180px"><select id="attend_name" style="width: 180px;"></select></td>
							<td class="label">班次：</td>
							<td class="ipt"><select id="attend_class" style="width: 180px;"></select></td>
							<td><button id="query">查询</button></td>
							
							<td class="label">护理等级：</td>
							<td>
								<input class="leve" type="radio" name="leve"  checked value='0' />
								<label>显示</label>
							</td>
							<td>
								<input class="leve" type="radio" name="leve"  value='1' />
								<label>不显示</label>
							</td>
						</tr>
					</table>
			 		<div>
						<div id="dateSearchSilder"></div>
						<div id="dateSearchGrid"></div>
					</div>
				</div>
            </div>
		</div>
	</div>
</body>
</html>