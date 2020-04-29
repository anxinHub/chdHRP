<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,hr.select,grid,datepicker " name="plugins" />
</jsp:include>
<script>
	var dept_code, title_code, duty_code, level_code;
	var grid;
	
	var initFrom = function() {
		apply_date = $("#apply_date").etDatepicker({
			range : true,
			onChange : query
		});

		perm_type = $("#perm_type").etSelect({
			url : "../queryPermTypeSelect.do?isCheck=false",
			defaultValue : "none",
		});
		emp_id = $("#emp_id").etSelect({
			url : "../queryEmpSelect.do?isCheck=false",
			defaultValue : "none",
			onChange : query
		});
	};
	var query = function() {
		var params = [ 
			 { name: 'apply_date', value: apply_date.getValue()[0] || '' },
             { name: 'end_date', value: apply_date.getValue()[1] || '' },

		 {
			name : 'emp_id',
			value : emp_id.getValue()
		}, {
			name : 'perm_type',
			value : perm_type.getValue()
		}, ];
		grid.loadData(params);
	};
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "emp_code") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					emp_code : data.emp_code,
					emp_name : data.emp_name,
					emp_id :data.emp_id
				});

			}
		}
		return true;
	}
	var save = function() {
		   var ParamVo = [];
       	var errorMsg = '';//错误提示信息
		var isPass = grid.validateTest({
			required : {
				emp_code : true,
				emp_name : true,
				emp_id : true,
				perm_name : true,
			}
		});
		if (!isPass) {
			return;
		}
	     //验证重复数据
      	if (!grid.checkRepeat(
      			grid.selectGet(),
      			['emp_id','emp_name','perm_name']
      	)		
      	) {
             return;
         }
		var gridAllData = grid.getAllData();
		if (!gridAllData || gridAllData.length === 0) {
			return;
		}
		

		ajaxPostData({
			url : 'addHrDrugPerm.do?isCheck=false',
			data : {
				paramVo : JSON.stringify(gridAllData)
			},
			success : function() {
				query();
			}
		})
	};
	var add = function() {
		grid.addRow();
	};
	var submit = function() {
		var msg="";
		var ParamVo = [];
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			$(data).each(function() {
				var rowdata = this.rowData;
				/* if(rowdata.emp_id && rowdata.perm_type && rowdata.state_name){
					ParamVo.push(rowdata);
				} */
		           if(rowdata.state!=0){
               		msg+='请选择新建状态单据';
                 	  return false;
                   }
                   ParamVo.push(rowdata);
               });
               if(msg!=""){
             	  $.etDialog.error(msg);
             	  return;
               }
			if(ParamVo.length>0){
				$.etDialog.confirm('确定提交?', function() {
					ajaxPostData({
						url : 'confirmHrDrugPerm.do?isCheck=false',
						data : {
							paramVo : JSON.stringify(ParamVo)
						},
						success : function() {
							query();
						}
					})
				});
			}
			
		}

	};
	var cancel = function() {
		var msg="";
		var ParamVo = [];
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			$(data).each(function() {
				var rowdata = this.rowData;
			/* 	if(rowdata.emp_id && rowdata.perm_type && rowdata.state_name){
					ParamVo.push(rowdata);
				} */
				
		          if(rowdata.state!=1){
	               		msg+='请选择提交状态单据';
	                 	  return false;
	                   }
	                   ParamVo.push(rowdata);
	               });
	               if(msg!=""){
	             	  $.etDialog.error(msg);
	             	  return;
	               }
			if(ParamVo.length>0){
				$.etDialog.confirm('确定撤回?', function() {
					ajaxPostData({
						url : 'reConfirmHrDrugPerm.do?isCheck=false',
						data : {
							paramVo : JSON.stringify(ParamVo)
						},
						success : function() {
							query();
						}
					})
				});
			}
			
		}
	};
	var remove = function() {
		var selectData = grid.selectGet();
		if (selectData.length === 0) {
			$.etDialog.error('请选择行');
			return;
		}

		var param = [];
		var nullRow=[];
		selectData.forEach(function(item) {
			if(item.rowData.emp_id && item.rowData.perm_type){
				param.push({
					perm_type : item.rowData.perm_type,
					emp_id : item.rowData.emp_id
				});
			}else{
				nullRow.push({rowIndx: item.rowIndx});
			}
		});
			
		$.etDialog.confirm('确定删除?', function (index, el) {
			 if(param.length>0){
				 ajaxPostData({
						url : 'deleteHrDrugPerm.do',
						data : {
							paramVo : JSON.stringify(param)
						},
						success : function() {
							grid.deleteRows(selectData);
						}
				});
			 }else{
				 grid.deleteRows(nullRow);
			 }
			 $.etDialog.close(index);
	    });
		
	};
	var putin = function() {
		var para = {
    			"column" : [ {
    				"name" : "emp_id",
    				"display" : "职工编码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "perm_type",
    				"display" : "权限类别",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "get_date",
    				"display" : "获得日期",
    				"width" : "200",
    				"require" : true
    				
    			},{
    				"name" : "stop_date",
    				"display" : "停止日期",
    				"width" : "200",
    				"require" : true
    			}, {
    				"name" : "apply_date",
    				"display" : "提交日期",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "state",
    				"display" : "状态",
    				"width" : "200"
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			},]

    		};
    		importSpreadView("/hrp/hr/privilegemanagement/importDrugPerm.do?isCheck=false", para);
			
			
			
	};
	var putout = function() {
	};
	var initGrid = function() {
		var yearEditor = getRecentYearForSelect();
		var columns = [ {
			display : '职工工号',
			name : 'emp_code',
			width : 120,
			editable:false,
		}, {
			display : '职工id',
			name : 'emp_id',
			width : 120,
			editable:false,
			hidden:true,
		}, {
			display : '职工名称',
			name : 'emp_name',
			width : 120,
			
			editor : {
   		     type: 'select', 
    		     keyField: 'emp_id',
    		     url:'../queryEmpSelect.do?isCheck=false',
        		 change: function(ui){
        			
        			 ajaxPostData({
     		   			url: '../queryEmpSelectInfo.do?isCheck=false&emp_id='+ui.emp_id,
     		   			success: function (data) {
         		   			ui.emp_code = data.Rows[0].emp_code;
      		   				ui.emp_id = data.Rows[0].emp_id;
      		   				ui.emp_name =data.Rows[0].emp_name;
      		   				
      		   			grid.refreshCell(ui._rowIndx, 'emp_code', false);
      			   		grid.refreshCell(ui._rowIndx, 'emp_id', false);
      					grid.refreshCell(ui._rowIndx, 'emp_name', false);

     		   			}
     		   		});
		   				
		   	
   		   	/* 	ajaxPostData({
   		   			url: '../queryEmpSelectInfo.do?isCheck=false&emp_id='+ui.emp_code,
   		   			success: function (data) {
       		   			
	   			

   		   			}
   		   		}); */
   		   		//without_id += ui.subject_id + ","
       		  },
			},
			 editable : function(col){
	               	if(col.rowData){
	            		if(col.rowData.hos_id!=null){
	            			return false;
	            		}
	            		return true;
	            	}else{
	            		return true;
	            	}
	            }
		}, {
			display : '',
			name : 'emp_id',
			hidden : true,
		}, {
			display : '权限类别',
			name : 'perm_name',
			width : 120,
			editor : {
				type : 'select',
				keyField : 'perm_type',
				url : '../queryPermTypeSelect.do?isCheck=false',
			},
            editable : function(col){
               	if(col.rowData){
            		if(col.rowData.hos_id!=null){
            			return false;
            		}
            		return true;
            	}else{
            		return true;
            	}
            }
	
		},{
			display : '',
			name : 'level_code',
			hidden : true,
		}, {
			display : '限制药品明细',
			name : '',
			width : 120,
			editable : false,
			 render: function (ui) {
                 var updateHtml =
                     '<a class="openUpdate" row-index="' + ui.rowIndx + '">编辑  </a>'
                 return updateHtml;
             }
		}, {
			display : '获得日期',
			name : 'get_date',
			editable : true,
			width : 120,
			editor : {
				type : 'date',
				dateFormat : 'yy-mm-dd',
			},
		}, {
			display : '停止日期',
			name : 'stop_date',
			editable : true,
			width : 120,
			editor : {
				type : 'date',
				dateFormat : 'yy-mm-dd',
			},
		}, {
			display : '备注',
			name : 'note',
			editable : true,
			width : 120
		},{
			"name" : "state_name",
			"display" : "状态",
			"width" : 80,
			editable : false
		} ,{
			display:"",
			name : "state",
			hidden:true
		} ];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			editable : true,
			dataModel : {
				url : 'queryHrDrugPerm.do'
			},
			columns : columns,
			toolbar : {
				items : [ {
					type : 'button',
					label : '查询',
					listeners : [ {
						click : query
					} ],
					icon : 'search'
				}, {
					type : 'button',
					label : '保存',
					listeners : [ {
						click : save
					} ],
					icon : 'save'
				}, {
					type : 'button',
					label : '添加',
					listeners : [ {
						click : add
					} ],
					icon : 'add'
				}, {
					type : 'button',
					label : '删除',
					listeners : [ {
						click : remove
					} ],
					icon : 'delete'
				}, {
					type : 'button',
					label : '提交',
					listeners : [ {
						click : submit
					} ],
					icon : 'add'
				}, {
					type : 'button',
					label : '撤回',
					listeners : [ {
						click : cancel
					} ],
					icon : 'cancel'
				}, {
					type : 'button',
					label : '导入',
					listeners : [ {
						click : putin
					} ],
					icon : 'import'
				}/* , {
					type : 'button',
					label : '导出',
					listeners : [ {
						click : putout
					} ],
					icon : 'export'
				}  */]
			}
		};
		grid = $("#mainGrid").etGrid(paramObj);

        $("#mainGrid").on('click', '.openUpdate', function () {
            var rowIndex = $(this).attr('row-index');
            var currentRowData = grid.getAllData()[rowIndex];
            if(!currentRowData.emp_id){
            	$.etDialog.error('请选择员工');
            }else{
            	   parent.$.etDialog.open({
               		url:"hrp/hr/privilegemanagement/hrDrugPermDetailMainPage.do?isCheck=false&emp_id=" + currentRowData.emp_id+'&perm_type=' + currentRowData.perm_type+'&state='+currentRowData.state_name,
                       width: 800,
                       height: 600,
                       frameName :window.name,
                       title: '权限明细添加'
                   });
            	
            }
        })
	};

	$(function() {
		initFrom();
		initGrid();
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">获得日期：</td>
				<td class="ipt"><input id="apply_date" type="text" /></td>
				<td class="label">职工姓名：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"></select>
				</td>
				<td class="label">权限类别：</td>
				<td class="ipt"><select id="perm_type" style="width: 180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>