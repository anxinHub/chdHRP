<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="hr,dialog,datepicker,select,validate,grid,dateSlider,characterList,tree,tab,from,pageOffice" name="plugins" />
</jsp:include>
</head>

<script type="text/javascript">
	var hos_name,year_month,tree,emp_code;
	var attend_codes = [];
	var tree_code,leftGrid;
	var dataAll;
	var saveObj= {};
	var editData;
	var jsonHead;
	var headData = {};
	var detailData = {};
	var paramEmp = [];
	$(function () {
		localStorage.clear();
		leftGrid();
		initDict();
		
		// 给输入框绑定搜索树事件
	    $(".text-input").on('keyup', function () {
	        var $self = $(this);
	        searchTree({
	            tree: tree,
	            value: $self.val(),
	            callback: function () {
	                $self.focus();
	            }
	        })
	    })
	});

	function initDict() {
		tree = $("#tree").etTree({
	        async: {
	            enable: true,
	            url: '../../queryHosDeptDictTree.do?isCheck=false&is_perm=1',
	        },
	        callback: {
                onClick: function () {
                    query('tree');
                }
            }
	    });
		
		hos_name = $("#hos_name").etSelect({
		    url: '../../queryHosInfoSelect.do?isCheck=false',
		    defaultValue: '${sessionScope.hos_id}',
		    onInit : function() {
		    	genegrateMonthsColumns();
		    	//query('tree');
			},
		});
		
		emp_code = $("#emp_code").etSelect({
		    url: '../../queryEmpSelectAttend.do?isCheck=false',
		    defaultValue: "none",
		    onChange: function(value) {
		    	query('tree');
		    }
		});
		dept_id_c = $("#dept_id_c").etSelect({
			url: '../../queryHosDeptSelect.do?isCheck=false',
			defaultValue: "none",
			onChange:  function(value) {
		    	query('tree');
		    }
		});
		
		dept_id_b = $("#dept_id_b").etSelect({
			url: '../../queryHosDeptSelect.do?isCheck=false',
			defaultValue: "none",
			onChange:  function(value) {
		    	query('tree');
		    }
		});
	/* 	attend_item = $("#attend_item").etSelect({
			url: '../../queryAttendCodeCla.do?isCheck=false',
			defaultValue: "none",
			onChange:  function(value) {
		    	query('tree');
		    }
		}); */
		kind_code= $("#kind_code").etSelect({
			url: '../../queryHrEmpKindSelect.do?isCheck=false',
			defaultValue: "none",
			onChange:  function(value) {
		    	query('tree');
		    }
		});
		year_month = $("#year_month").etDatepicker({
			view: "months",
			minView: "months",
			dateFormat: "yyyy-mm",
			defaultDate: true,
			onChange: function(value) {
				 setTimeout(function(){
					genegrateMonthsColumns();
					query('tree');
				},0) 
			}
		});
		
		ajaxPostData({
			url: '../../queryAttendCodeCla.do?isCheck=false',
			async: true, 
			success: function (data) {
				$.each(data, function(){
					attend_codes.push(
						{"label": this.text, "id": this.id}
					)
				})
			}
		})
	};
	
	var query = function (queryFor) {
        if (queryFor == 'tree') {
            var selectedNode = tree.getSelectedNodes()[0];
            tree_code = selectedNode ? selectedNode.id : '';
            params = [
                {name: 'dept_code',value: tree_code },
                {name: 'hos_id',value: hos_name.getValue() },
                {name: 'year_month',value: year_month.getValue().replace("-", "") },
                {name: 'emp_id',value: emp_code.getValue() },
                {name: 'dept_id_c',value: dept_id_c.getValue().split("@")[1]},
                {name: 'dept_id_b',value: dept_id_b.getValue().split("@")[1] },
                {name: 'kind_code',value: kind_code.getValue() },
            ]
        } 
    	//重新查询后对象应为空
    	saveObj= {};
    	//查询
        leftGrid.loadData(params, "queryAttendResult.do");
    }; 
    
	/* 左表 基础列头 */
	var leftcolumns = [
		{display : '考勤周期',name : 'year_month',width : 60,editable: false},
		{display : '出勤科室',name : 'dept_name_c',width : 100,editable: false},
		{display : '职工编码',name : 'emp_code',width : 60,editable: false},
		{display : '职工名称',name : 'emp_name',width : 60,editable: false},
		{display : '编制科室',name : 'dept_name_b',width : 100,editable: false}
	];
	
	/* 根据日期获取周 */
	var getWeekByDate = function(date){
		var sWeek = new Date(date).getDay();
		switch (sWeek){ 
			case  0: 
				sWeek='周日';  
				break;  
			case  1: 
				sWeek='周一';  
				break;  
			case  2: 
				sWeek='周二';  
				break;  
			case  3: 
				sWeek='周三';  
				break;  
			case  4: 
				sWeek='周四';  
				break;  
			case  5: 
				sWeek='周五';  
				break;  
			case  6: 
				sWeek='周六';  
				break;  
			default:  
				break;  
		};
		return sWeek;
	}
	
	/* 根据日期获取是否为工作日 */
	var getWeekByXiuBan = function(date){
		
		var sWeeks = "";
		ajaxPostData({
            url: '../attend/queryOfficialHoliday.do?isCheck=false&year_month='+date,async: false,	
            success: function (data) {
            	if(data.Rows.length > 0 ){
            		switch (data.Rows[0].attend_date_state){ 
        			case  "0": 
        				sWeeks="<span style='color:green'>休</span>";  
        				break;  
        			case  "1": 
        				sWeeks="<span style='color:red'>班</span>";  
        				break;  
        			default:  
        				break;  
        		}
            	} 
            }
        });
		
		return sWeeks;
	}
    
	/* 生成日期的列 */
	var genegrateMonthsColumns = function() {
		var columns = [];
		var date = year_month.getValue().replace("-", "");
		var year = year_month.getValue().substr(0, 4);
		ajaxPostData({
			  url: 'queryAttendResultHead.do?isCheck=false&year_month='+date,
			  async: false,	  
			  success: function (data) {
				  if(data.state == "true"){
					  jsonHead = eval(data.jsonHead);
					  $.each(jsonHead,function(index, v){
		    				if(v.name){
		    					headData[v.name] = v.date;
		    					columns.push({
		    						display : v.display + "<br/>" + getWeekByDate(v.date) + "<br/>" + getWeekByXiuBan(v.date),
		    						name : v.name,
		    						width : 30,
		    						editor: {
		    							type: 'select',
		    						    autoFocus : true,   //  为true时 下拉框默认选择第一个
		    						    disabled : false,
		    						    attr: 'multiple="multiple"', 
		    							valueField : 'id',
		    							textField : 'text',
		    							//url : '../../queryAttendCodeCla.do?isCheck=false', 
		    						    source: attend_codes,
		    						    open: function() {
		    						    	
		    						    },
		    						    select: function() {
		    		
		    						    },
		    						    change: function(event, ui) {
		    						    	editData = {
		    						    		column_val: ui.selected.label, //主表字段内容
		    						    		attend_code: ui.selected.id, //明细表考勤项目值
		    						    	};
		    						    },
		    						    close: function() {
		    		
		    						    }
		    						}
		    					})
		    			}
	    			});
				}
				  
				columns.push({
					display : "备注", name : "note", width : 120, 
					editor: {
						type: 'text'
					}
				});
				  
				var newColumns = leftcolumns.concat(columns);
				leftGrid.option('columns', newColumns);
				leftGrid.refreshView();
			},
		});
	}; 
	
	
	

	
	//单元格双击事件
	function cellDblClick(event, ui){
		//是否为考勤项列
		var col_name = ui.column.dataIndx;
	   	if(col_name.length < 4 && col_name.indexOf("d") == 0){
	   		var column_val = ui.rowData[col_name];
	   		//if(column_val && column_val.indexOf(";") > 0){
	   		if(column_val){//测试时使用
	   			//弹出页面
	   			detailData = {
	   				rowIndx: ui.rowIndx, 
   					col_name: col_name, 
   					year_month:  ui.rowData.year_month, 
   					dept_id_c: ui.rowData.dept_id_c, 
   					dept_name_c: ui.rowData.dept_name_c, 
   					emp_id: ui.rowData.emp_id, 
   					emp_name: ui.rowData.emp_code + " " + ui.rowData.emp_name, 
   					attend_date: headData[col_name], 
	   			};

				parent.$.etDialog.open({
					url: 'hrp/hr/attendancemanagement/attendresult/hrAttendResultDetailPage.do?isCheck=false',
					width: 500,
					height: 400,
					frameName :window.name,
					title: '考勤职工添加'
				});
	   			return false;
	   		}
	   	}
	   	
	   	return true;
	}
	//单元格结束编辑事件
	function editorEnd(event, ui){
		//确定唯一值“出勤科室+职工+天”
	   	var rowIndex = ui.rowData.dept_id_c + ui.rowData.emp_id + ui.dataIndx;
	   	var column_val = "", attend_date = "", attend_code = "";
	   	if(ui.dataIndx.indexOf("d") == 0){
	   		column_val = editData.column_val;
			attend_date = headData[ui.dataIndx];
			attend_code = editData.attend_code;
	   	}else{
	   		column_val = ui.rowData[ui.dataIndx];
	   	}
		
		var rowDetail = {
			dept_id_c: ui.rowData.dept_id_c,
			emp_id: ui.rowData.emp_id,
			year_month: ui.rowData.year_month,
			column: ui.dataIndx, 
			column_val: column_val, 
			attend_date: attend_date,
			attend_code: attend_code, 
		}
		saveObj[rowIndex] = rowDetail;
		return true;
	} 
	
	//生成
	function createData(){
		var selectedNode = tree.getSelectedNodes()[0];
        var tree_code = selectedNode ? selectedNode.id : '';
        if(tree_code){
        	ajaxPostData({
                url: 'addBatchAttendResult.do',
                data: {
                	'year_month': year_month.getValue().replace("-", ""),
                	'dept_code':tree_code
                },
                success: function () {
                    query();
                }
            });
        }else{
        	ajaxPostData({
                url: 'addBatchAttendResult.do',
                data: {
                	'year_month': year_month.getValue().replace("-", "")
                },
                success: function () {
                    query();
                }
            });
        }
	}
	
	//保存
	function saveD() {
		if (JSON.stringify(saveObj)=="{}") {
            $.etDialog.warn('未获得修改数据');
            return;
        }
		var saveObjs= [];
		for (var rowIndex in saveObj){
			var detail = saveObj[rowIndex];
			saveObjs.push({
				dept_id_c : detail.dept_id_c,
				emp_id : detail.emp_id,
				year_month: detail.year_month,
				column: detail.column, 
				column_val: detail.column_val, 
				attend_date: detail.attend_date,
				attend_code : detail.attend_code, 
			});
		}
		
		ajaxPostData({
            url: 'addAttendResult.do',
            data: {
            	'year_month': year_month.getValue().replace("-", ""),
                'allData': JSON.stringify(saveObjs)
            },
            success: function () {
            	//保存后对象应为空
            	saveObj= {};
                query();
            }
        });
	}
	
	//增加职工
	function addData(){
		parent.$.etDialog.open({
			url: 'hrp/hr/attendancemanagement/attendresult/addEmpPage.do?isCheck=false&year_month=' + year_month.getValue().replace("-", ""),
			width: 400,
			height: 300,
			frameName :window.name,
			title: '考勤职工添加'
		});
	}
	
	//删除职工
	function deleteData(){
		var data = leftGrid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
        	var param = [];
            $(data).each(function () {
                var rowdata = this.rowData;
                param.push({
                	year_month: rowdata.year_month, 
					dept_id_c: rowdata.dept_id_c, 
                	emp_id : rowdata.emp_id,
                })
            });
            
            $.etDialog.confirm('确定删除?', function () {
                ajaxPostData({
                    url: 'deleteAttendResult.do',
                    data: { 
                    	year_month: year_month.getValue().replace("-", ""),
                    	ids: JSON.stringify(param)
                    },
                    success: function () {
                        query();
                    }
                 })
            });
        }
	}
	
	//打印
	function printData(){	
		if(leftGrid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads = {};
    	var printPara={
      		title: "考勤数据维护",//标题
      		columns: JSON.stringify(leftGrid.getPrintColumns()).replace(/<br\/>/g, " "),//表头
      		class_name: "com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultService",
   			bean_name: "hrAttendResultService",
   			method_name: "queryAttendResultPrint",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			foots: ''//表尾需要打印的查询条件,可以为空 
       	};
    	
       	$.each(leftGrid.getUrlParms(),function(i,obj){
     		printPara[obj.name]=obj.value;
       	}); 
       	officeGridPrint(printPara);
	}
	
	//导入页面跳转
	function importData(){
		var columns =[];
		var para;
		columns.push({
			"name" : "dept_code",
			"display" : "出勤科室",
			"width" : "100",
			"require" : true
		},{
			"name" : "emp_code",
			"display" : "职工信息",
			"width" : "100",
			"require" : true
		});
		
		var date = year_month.getValue().replace("-", "");
    	var a = null;
		if(jsonHead.length>0){
			$.each(jsonHead,function(index, v){
				columns.push({
		       		"display" : v.display+"("+getWeekByDate(v.date)+")",
		       		"name" : v.name,
		       		"width" : "90"
		       	});
			});

			para = {
				"year_month": date, 
				"column": columns
			};
			 
			importSpreadView("/hrp/hr/attendancemanagement/attendresult/importAttendResult.do", para);
		}
	}
	
	//引入排版
	function importPb(){
		$.etDialog.confirm('确定引入排班?', function () {
			ajaxPostData({
				url: 'importPbForResult.do',
				data: { 
					year_month: year_month.getValue().replace("-", ""),
					dept_code: tree_code
				},
				success: function () {
					query();
				}
			})
		});
	}
	
	//引入加班
	function importJb(){
		$.etDialog.confirm('确定引入加班?', function () {
			ajaxPostData({
				url: 'importJbForResult.do',
				data: { 
					year_month: year_month.getValue().replace("-", ""),
					dept_code: tree_code
				},
				success: function () {
					query();
				}
			})
		});
	}
	//引入休假
	function importXj(){
		$.etDialog.confirm('确定引入休假?', function () {
			ajaxPostData({
				url: 'importXjForResult.do',
				data: { 
					year_month: year_month.getValue().replace("-", ""),
					dept_code: tree_code
				},
				success: function () {
					query();
				}
			})
		});
	}
	//快捷设置
	function importKj(ui){
		detailData={};
		var data = leftGrid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else if (data.length > 1) {
            $.etDialog.error('请选择单个人员进行设置');
        } else{//是否为考勤项列
	
	
	   	
	   		//if(column_val && column_val.indexOf(";") > 0){
	   		if(data){//测试时使用
	   			//弹出页面
	   			/* detailData = {
	   				rowIndx: ui.rowIndx, 
   				
   					year_month: data.rowIndx.year_month, 
   					dept_id_c: data.rowIndx.dept_id_c, 
   					dept_name_c:data.rowIndx.dept_name_c, 
   					emp_id: data.rowIndx.emp_id, 
   					emp_name: data.rowIndx.emp_code + " " + data.rowIndx.emp_name, 
   					attend_date: headData, 
	   			}; */
	   		  $(data).each(function () {
	                var rowdata = this.rowData;
	            	detailData = {
	                	rowIndx: ui.rowIndx, 
	   					year_month: rowdata.year_month, 
	   					dept_id_c: rowdata.dept_id_c, 
	   					dept_name_c:rowdata.dept_name_c, 
	   					emp_id: rowdata.emp_id, 
	   					emp_name: rowdata.emp_code + " " + rowdata.emp_name, 
	   					attend_date: headData, 
	                }
	            });
				parent.$.etDialog.open({
					url: 'hrp/hr/attendancemanagement/attendresult/hrAttendResultShortCutPage.do?isCheck=false',
					width: 600,
					height: 500,
					frameName :window.name,
					title: '快捷设置'
				});
	   			return false;
	   		}
	   	
	   	
	   
	}
        }
	//快捷设置
	function importPL(ui){
		paramEmp=[];
		var data = leftGrid.selectGet();
		
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else{//是否为考勤项列
	
	
	   	
	   		//if(column_val && column_val.indexOf(";") > 0){
	   		if(data){//测试时使用
	   			//弹出页面
	   			/* detailData = {
	   				rowIndx: ui.rowIndx, 
   				
   					year_month: data.rowIndx.year_month, 
   					dept_id_c: data.rowIndx.dept_id_c, 
   					dept_name_c:data.rowIndx.dept_name_c, 
   					emp_id: data.rowIndx.emp_id, 
   					emp_name: data.rowIndx.emp_code + " " + data.rowIndx.emp_name, 
   					attend_date: headData, 
	   			}; */
	   		  $(data).each(function () {
	                var rowdata = this.rowData;
	            	detailData = {
	                	rowIndx: ui.rowIndx, 
	   					year_month: rowdata.year_month, 
	   					dept_id_c: rowdata.dept_id_c, 
	   					dept_name_c:rowdata.dept_name_c, 
	   					emp_id: rowdata.emp_id, 
	   					emp_name: rowdata.emp_code + " " + rowdata.emp_name, 
	   					attend_date: headData, 
	                };
	            	paramEmp.push({
	            		emp_id: rowdata.emp_id
	            	})
	            	})
/* 	            }; */
				parent.$.etDialog.open({
					url: 'hrp/hr/attendancemanagement/attendresult/hrAttendResultShortPLPage.do?isCheck=false',
					width: 600,
					height: 500,
					frameName :window.name,
					title: '批量修改'
				});
				
	   			return false;
	   		}
	   	
	   
	}
        }
function leftGrid () {
		 // 基础表格参数
	    var toolbar = {
	        items: [
				{ type: "button", label: '生成', icon: 'create', listeners: [{ click: createData }] },
	            { type: "button", label: '保存', icon: 'save', listeners: [{ click: saveD }] },
	            { type: "button", label: '增加职工', icon: 'add', listeners: [{ click: addData }] },
	            { type: "button", label: '删除职工', icon: 'delete', listeners: [{ click: deleteData }] },
	            { type: "button", label: '导入', icon: 'import', listeners: [{ click: importData }] },
	            { type: "button", label: '引入排班', icon: 'importPb', listeners: [{ click: importPb }] },
	            { type: "button", label: '引入加班', icon: 'importJb', listeners: [{ click: importJb }] },
	            { type: "button", label: '引入休假', icon: 'importXj', listeners: [{ click: importXj }] },
	            { type: "button", label: '快捷设置', icon: 'importKj', listeners: [{ click: importKj }] }, 
	            { type: "button", label: '批量修改', icon: 'importKj', listeners: [{ click: importPL }] }, 
	            { type: "button", label: '打印', icon: 'print', listeners: [{ click: printData }] }
	        ]
	    };
		var paramObj = {	
			width : 'auto',
			height: '100%',
	        editable: true,
	        inWindowHeight: '100%',
	        freezeCols : 5, //冻结两列
	        toolbar: toolbar,
	        checkbox: true,
			columns : leftcolumns, 
			cellDblClick: cellDblClick, 
			editorEnd: editorEnd, 
			pageModel: {
               type: 'remote',//local前台分页
           },
			//editorBegin :cellC
		};
		leftGrid = $("#maGrid").etGrid(paramObj);
	};
</script>    
<body>
	<div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div>
            <div id="tree"></div>
        </div>
        <div class="center" style="padding:0">
            <table class="table-layout">
				<tr>
	                <td class="label">单位信息：</td>
	                <td class="ipt">
	                    <select id="hos_name"  style="width:160px" disabled></select>
	                </td>
	               
	               <td class="label">考勤周期：</td>
	               <td class="ipt">
	                    <input name="year_month" type="text" id="year_month" style="width:90px"/>
	               </td>
	               
	               <td class="label">职工信息：</td>
	               <td class="ipt">
	                    <select id="emp_code" type="text" style="width:160px"/>
	               </td>
	            </tr>
	            <tr>
	             <td class="label">出勤科室：</td>
	                <td class="ipt">
	                    <select id="dept_id_c"  style="width:160px" ></select>
	                </td>
	                <td class="label">编制科室：</td>
	                <td class="ipt">
	                    <select id="dept_id_b"  style="width:160px" ></select>
	                </td>
	                <td class="label">职工类别：</td>
	                <td class="ipt">
	                    <select id="kind_code"  style="width:160px" ></select>
	                </td>
	            
	            </tr>
	           <!--  <tr>
	            <td class="label">考勤项目：</td>
	                <td class="ipt">
	                    <select id="attend_item"  style="width:160px" ></select>
	                </td></tr> -->
            </table>
            
            <div id="maGrid"></div>
        </div>
    </div>
</body>
</html>