<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,grid,tab,from,datepicker" name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">
	var grid;
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	var data = parentWindow.detailData;
	var emp_id=parentWindow.paramEmp;
	
    function query() {
        var url = 'queryAttendEmp.do?isCheck=false';
        var data = [
                    {name: 'emp_id',value: JSON.stringify(emp_id) },
        ];
        grid.loadData(data, url);
    }
	var initGrid = function () {
		var columns = [ {
			display: '职工', name : 'emp_code',
			width : 160,
          /* 	editor: {
                type: 'select',
                keyField: 'id',
                url:'queryEmpSelectResult.do?isCheck=false',
            } */ 
			},{
				display: '职工id', name: 'emp_id', width: 120, hidden: true, editable: false
			},  {
			display: '考勤项目编码', name: 'attend_code', width: 120, hidden: true, editable: false
		}, {
			display: '考勤项目', name: 'attend_name', width: 120,
			editor: {
				type: 'select',
				 keyField : 'attend_code',
				url : '../../queryAttendCodeCla.do?isCheck=false'/*, 
			    source: parentWindow.attend_codes ,
				change: function(event, ui) {
					var newRow = {};
					newRow["attend_code"] = ui.selected.id;
					grid.updateRow(ui.rowIndx, newRow);
				}, */
			}
		}, {
			display: '考勤开始时间', name: 'beg_time', width: 120, align: "right",
   		 editor: {
  		     type: 'date', 
  		   defaultParam : {
                   dateFormat: 'yy-mm-fd',
                   }
  		 
  		 }	 }, {
 			display: '考勤结束时间', name: 'end_time', width: 120, align: "right",
 	   		 editor: {
 	  		     type: 'date'
 	  		     
 	  		 }	 } /* , {
 				display: '考勤天数', name: 'attend_val', width: 120, align: "right", 
 				editor: {
 					type: 'select',
 				    autoFocus : true,   //  为true时 下拉框默认选择第一个
 				    disabled : false,
 					valueField : 'id',
 					textField : 'text',
 					defaultValue: '1',
 					//url : '../../queryAttendCodeCla.do?isCheck=false', 
 				    source: [
 						{"label": "1", "id": 1}, 
 						{"label": "0.5", "id": 0.5}, 
 						{"label": "1.5", "id": 1.5}
 					],
 				}
 			} */ ];
		
		var paramObj = {
			height: '90%',
			inWindowHeight: true,
			checkbox: true,
			usePager: false,
			columns: columns,
	        editable: true,
	        pageModel: {
                type: 'remote',//local前台分页
            },
	        //url : 'queryAttendEmp.do?isCheck=false',
			//flexHeight: true, 
			toolbar: {
				items: [ {
					type: 'button', label: '添加', icon: 'add', listeners: [{ click: addRow }] 
				}, {
					type: 'button', label: '删除', icon: 'remove', listeners: [{ click: remove }] 
				} ]
			}
		};
		
		grid = $("#mainGrid").etGrid(paramObj);
	};
	
	var addRow = function () {
		grid.addRow();
	}
	
	var remove = function () {
		grid.deleteSelectedRows();
	}

	var initForm = function () {
		$("#year_month").text(data.year_month);
		//$("#attend_date").text(data.attend_date);
		$("#dept_name_c").text(data.dept_name_c);
		$("#emp_name").text(data.emp_name);

		$("#save").click( function () {
			this_save();
			setTimeout(function () {
				grid.refresh();
            }, 10);
			
		});
		$("#close").click( function () {
			this_close();
		});
		attend_date = $("#attend_date").etDatepicker({
			view: "months",
			minView: "months",
			dateFormat: "yyyy-mm-dd",
			defaultDate: true,
			
		});
	};
	
	var this_close = function () {
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parentWindow.paramEmp=[];
		parent.$.etDialog.close(curIndex);
	}
	
	var this_save = function () {
		var col_val = "";
		var allData = grid.selectGet();
		var msg = "";
		var vals = 0;
		var param = [];

		/* $.each(allData,function(index, v){
	
		}) */ 
		if (allData.length == 0) {
            $.etDialog.error('请选择需要保存的行');
            return;
        } else {
		   $(allData).each(function () {
                    var rowdata = this.rowData;
				if(!rowdata.attend_name || !rowdata.beg_time || !rowdata.end_time){
					msg = "第" + (index+1) + "行，数据不合法<br/>";
				}
				vals += parseFloat(1);
				col_val += rowdata.attend_name + ";";
				 var dt1 = rowdata.beg_time;
				    var dt2 = rowdata.end_time;
				    dt1 = new Date(dt1.replace(/-/g,"-"));
				    dt2 = new Date(dt2.replace(/-/g,"-"));
				    if(dt1.getMonth()!=dt2.getMonth()){
				    	msg = "第" + (index+1) + "行，不是同一个月份<br/>";
				    }
				    param.push({
				    	attend_name:rowdata.attend_name,
				    attend_code:rowdata.attend_code,
				    beg_time:rowdata.beg_time,
				    end_time:rowdata.end_time,
				    attend_val : 1,
				    emp_id:rowdata.emp_id
				    })
            })
        }
		if(msg){
            $.etDialog.warn(msg);
            return;
		}
	/* 	if(emp_id.length==0){
			 $.etDialog.warn("请选择人员");
	            return;
		} */
		/* if(vals > 2){
            $.etDialog.warn("考勤天数合计不能超过2天");
            return;
		} */
		col_val = col_val.substr(0, col_val.length - 1);
		
		ajaxPostData({
            url: 'saveAttendResultShortPL.do?isCheck=false',
            data: {
            	'year_month': data.year_month, 
            	'dept_id_c': data.dept_id_c, 
            	//'emp_count':JSON.stringify(emp_id) , 
            	//'attend_date': data.attend_date, 
            	'col_name': data.col_name, 
            	'col_val': col_val, 
                'allData': JSON.stringify(param)
            },
            success: function (state) {
            	var paramEmp1 = [];
            	//保存后修改父页面对应单元格
            	var newRow = {};
            	newRow= col_val;
            	/* parentWindow.initDict(); */
          
            	emp_id=[];
            	parentWindow.paramEmp=[];
            	parentWindow.query();
            	parentWindow.leftGrid.refresh();
            	
            }
        });
		//grid.refresh();
		setTimeout(function () {
			grid.refresh();
			   grid.deleteRows(allData);
        }, 10);
	}
	/* var deleteRows = function () {
		   grid.deleteRows();
	} */
	$(function () {
		initForm();
		initGrid();
		query();
	})
</script>

<body>
	<div class="center">
		<table class="table-layout">
			<tr>
				<td class="label">考勤周期：</td>
				<td class="ipt" >
					<span id="year_month"></span>
				</td>
				
				<td class="label">日期：</td>
				<td class="ipt">
					 <input name="attend_date" type="text" id="attend_date" style="width:90px" disabled/>
				</td>
			</tr>
			<!-- <tr>	
				<td class="label">出勤科室：</td>
				<td class="ipt">
					<span id="dept_name_c"></span>
				</td>
				
				<td class="label">职工：</td>
				<td class="ipt">
					<span id="emp_name"></span>
				</td> 
			</tr>-->
		</table>
		<div id="mainGrid"></div>
		<div class="button-group btn">
			<button id="save">保存</button>
			<button id="close">关闭</button>
		</div>
	</div>
</body>

</html>