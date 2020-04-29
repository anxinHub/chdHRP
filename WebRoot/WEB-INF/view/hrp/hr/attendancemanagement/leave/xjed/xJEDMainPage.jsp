<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,grid,select,tree,pageOffice,datepicker" name="plugins" />
    </jsp:include>
    
    <script>
        var grid, tree,editData,attend_code;
        var saveObj= {};
        var items = {};
        
        $(function() {
			initDict();
			initGrid();
			initTree();
			genegrateMonthsColumns();
			query();
		      // 给输入框绑定搜索树事件
            $(".text-input").on('keyup', function () {
                var $self = $(this)
                searchTree({
                    tree: tree,
                    value: $self.val(),
                    callback: function () {
                        $self.focus();
                    }
                })
            })
		});
        
        var initTree = function() {
			tree = $("#mainTree").etTree({
				async : {
					enable : true,
					url : '../../queryDeptTree.do?isCheck=false'
				},
				callback : {
					onClick : function() {
						query();
					}
				}
			});
		};
		
        function initDict() {
        /*     hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}',
                	onInit : function(value) {
                		genegrateMonthsColumns()
        			}, 
            }); */
            /* year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: true,
            }); */
        	attend_code = $("#attend_code").etSelect({
        		url: 'queryAttendCode.do?isCheck=false&control_type=0', 
        		defaultValue: 'none'
        	})
        }
        
        function initGrid() {
			var gridObj = {
					height: '100%',
                    inWindowHeight: true,
                    checkbox: false,
                    editable: true,
					columns : leftcolumns,
					editorEnd: editorEnd, 
			        cellDblClick: cellDblClick, 
			};
			$("#mainGrid").on('click', '.SaveUpdate', function() {
				var rowIndex = $(this).attr('row-index');
				var currentRowData = grid.getAllData()[rowIndex];
				SaveUpdate(currentRowData);
			})

			gridObj.dataModel = { // 数据加载的有关属性
			//url: ''
			};
			gridObj.toolbar = {
				items : [ 
						{ type : 'button', label : '初始化', icon : 'create',id : 'create', listeners: [{ click: importEmp }]},
				        { type : "button", label : '查询',icon : 'search',id : 'query', listeners : [ {click : query} ] },
						{ type : "button", label : '批量录入', icon : 'add', id : 'add', listeners : [ { click : add } ] },
						{ type : "button", label : '保存', icon : 'save', id : 'save', listeners : [ { click : save } ] },
						{ type : "button", label : '修改全院控制', icon : 'importQykz', id : 'import', listeners : [ { click : importQykz } ] },
				 		{ type : 'button', label : '打印', icon : 'print',listeners : [ { click : print } ]}, 
						{ type : 'button', label : '导入', icon : 'import',listeners : [ { click : importdata } ] }
				]
			};
			grid = $("#mainGrid").etGrid(gridObj);
		}
		
        var data=[];
    	/*  左表 基础列头 */
    	var leftcolumns = [ 
    			{ display: "职工编码",align: "left",name: "emp_code",width: 120,editable: false },
        		{ display: "职工姓名",align: "left",name: "emp_name",width: 120,editable: false }
		        /* ,  {
		            display: "工作开始时间",
		            align: "left",
		            width: 120,
		            name: "WORKTIME",
					editable : false  }, */
		];
     	
        var map = [];
     	/* 生成动态的列 */
       	var genegrateMonthsColumns = function() {
    		var columns = [];
    	  	ajaxPostData({
				url : 'queryItem.do?isCheck=false',
				success : function(responseData) { 
			  		for (var i = 0; i < responseData.Rows.length; i++) {
						items[responseData.Rows[i].attend_code] = {control_type: responseData.Rows[i].control_type};
		       			columns.push({
		    				display : responseData.Rows[i].attend_name + (responseData.Rows[i].control_type ? "(E)" : ""),
		    				name : responseData.Rows[i].attend_code,
		    				control_type: responseData.Rows[i].control_type,
		       				width : 80,
		       				align: "right", 
		       				editor: {
    						    autoFocus : true,   //  为true时 下拉框默认选择第一个
    						    disabled : false,
    							valueField : 'id',
    							textField : 'text',
    						    change: function(event, ui) {
    						    	editData = {
    						    		column_val: ui.currentValue, //考勤项目值
    						    		attend_code: ui.dataIndx, //考勤项目
    						    	}; 
    						    }
    						}
		    			})
		    			map.push(responseData.Rows[i].attend_code);
		       		}; 
		       		
		       		var newColumns = leftcolumns.concat(columns);
		       		grid.option('columns', newColumns);
		       		grid.refreshView();
		    		//return columns;
				}
			});
       	};

    	//单元格双击事件
    	function cellDblClick(event, ui){
    		//是否为全院控制列
    		var col_name = ui.column.dataIndx;
    		
    	   	if(items[col_name] && items[col_name].control_type == 0){
    	   		return false;
    	   	}
    	   	
    	   	return true;
    	}
       	
        //初始化
       	var importEmp=function (){
                $.etDialog.confirm('确定初始化?', function () {
                    ajaxPostData({
                        url: 'importEmp.do?isCheck=false',
                        data: {
                        	attend_year : $('#year').val(),
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
       	}
      
        //结束编辑
    	function editorEnd(event, rowdata){
    		//确定唯一值“职工+项目”
    	   	console.log(rowdata)
    	   	var rowIndex = rowdata.rowData.emp_id + rowdata.dataIndx;
    	   	var column_val = "", attend_date = "", attend_code = "";
    	   	column_val = rowdata.rowData[rowdata.dataIndx];
    		
    		var rowDetail = {
    			emp_id : rowdata.rowData.emp_id,
    			attend_code : rowdata.dataIndx,
    			attend_ed : column_val
    		}
    		
    		saveObj[rowIndex] = rowDetail; 
    		return true;
    	} 
       	
       	//保存
        function save () {
        	if (JSON.stringify(saveObj)=="{}") {
                $.etDialog.warn('未获得修改数据');
                return;
            }
        	
        	var saveObjs= [];
    		for (var rowIndex in saveObj){
    			var detail = saveObj[rowIndex];
    			saveObjs.push({
    				emp_id : detail.emp_id,
    				attend_code: detail.attend_code,
    				attend_ed: detail.attend_ed
    			});
    		}
    		
			ajaxPostData({
				url : 'saveXJED.do?isCheck=false',
				data : {
					'allData': JSON.stringify(saveObjs)
				},
				success : function() {
					query();
				}
			})
		};
		
		//查询
		function query() {
			var selectedNode = tree.getSelectedNodes()[0];
			var super_id = "";
			var dept_id = "";
			if (selectedNode) {
				if (selectedNode.IS_LAST ==1) {
					dept_id = selectedNode.DEPT_ID;
				} else if(selectedNode.IS_LAST== 0){
					super_id = selectedNode.id;
					
				}else if(selectedNode.IS_LAST== undefined){
					super_id ="";
					dept_id ="";
				}
			}
			
			var param = [
				<%--{ name : 'hos_id',value : hos_name.getValue() },
				{ name : 'attend_year', value : $('#year').val() },--%>
				{ name : 'super_id', value : super_id },
				{ name : 'dept_id', value : dept_id }
			];
			
			saveObj = {};//初始化后自动清空
			grid.loadData(param, 'queryXJED.do');
		}
		
		//批量添加
		function add() {
			
			var dept_id = "";
			var selectedNode = tree.getSelectedNodes()[0];
			if (!selectedNode) {
				$.etDialog.warn('请选择部门！');
                return;
			}else{
				if (selectedNode.IS_LAST ==1) {
					dept_id = selectedNode.DEPT_ID;
				} else{
					return;
				}
			}
			parent.$.etDialog.open({
				url : 'hrp/hr/attendancemanagement/attend/addXJEDPage.do?isCheck=false&dept_id='+dept_id,
				frameName : window.name,
				width : 800,
				height : 450,
				title : '批量增加',
			});
		}

					
		//导入
		var importdata = function () {
			var column =[/*  {
		    				"name" : "attend_year",
		    				"display" : "年份",
		    				"width" : "200",
		    				"require" : true
		    			},  */{
		    	            display: "职工编码",
		    	            align: "center",
		    	            width: 120,
		    	            name: "emp_code" ,
		    	            editable: false 
		    	        }, {
		    	            display: '职工名称',
		    	            align: 'center',
		    	            name: 'emp_name',
		    	            width: 120,
		    	            editable: false 
		    	        }/* ,  {
		    	            display: "工作开始时间",
		    	            align: "center",
		    	            width: 120,
		    	            name: "WORKTIME",
		    				editable : false  } */];
		            	var a = null;
		            	var col=[];
		            	 $.post('queryItemGeRen.do?isCheck=false', function (responseData) {
			 			  		for (var i = 0; i < responseData.Rows.length; i++) {
			 			  		 a = {
						             "display" : responseData.Rows[i].attend_name,
									 "name" : responseData.Rows[i].attend_code,
									 "width" : "90",
								 }
								 column.push(a);
							 };
						}, 'json');
		            	 
						var para = {
							"column" : column
						}
						importSpreadView( "hrp/hr/attendancemanagement/attend/importXJED.do?isCheck=false",
								para, query);
					};

					var print = function() {
						if (grid.getAllData() == null) {
							$.etDialog.error("请先查询数据！");
							return;
						}
						var heads = {
						/* "isAuto":true,//系统默认，页眉显示页码
						"rows": [
						 {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
						]  */};
						var printPara = {
							title : " 休假额度设置打印",//标题
							columns : JSON.stringify(grid.getPrintColumns()),//表头
							class_name : "com.chd.hrp.hr.service.attendancemanagement.leave.HrXJEDService",
							method_name : "queryXJEDByPrint",
							bean_name : "hrXJEDService",
							heads : JSON.stringify(heads),//表头需要打印的查询条件,可以为空
							foots : '',//表尾需要打印的查询条件,可以为空 
						};
						$.each(grid.getUrlParms(), function(i, obj) {
							printPara[obj.name] = obj.value;
						});
						//console.log(printPara);
						officeGridPrint(printPara);

					};
		var importQykz = function(){
   			$.etDialog.open({
   				title: '修改全院控制',
   				type: 1, 
   				content: $("#itemDiv"), 
   				width: 300, 
   				height: 300,
   				btn: ['确定', '取消'],
   				btn1: function (index, el) {
   					if(!attend_code.getValue()){
   						$.etDialog.warn('请选择需要修改额度考勤项目');
   						return false;
   					}
   		            $.etDialog.confirm('修改全院控制的额度数据会覆盖原有数据是否继续?', function (index1) {
    					ajaxPostData({
    						url : 'updateXjedByItem.do',
    						data : {
    							attend_year: $('#year').val(), 
    							attend_code: attend_code.getValue 
    						},
    					    success: function (res) {
    							$.etDialog.close(index); // 关闭弹窗
    							query();
    						}
    					})
    					$.etDialog.close(index1); // 关闭弹窗
   	    			});
   				}
            });
		}
		
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        <div class="center">
             <table class="table-layout">
                <tr>
                   <!-- <td class="label">单位信息：</td>
	                <td class="ipt">
	                    <select id="hos_name"  style="width:180px" disabled></select>
	                </td> -->
	                <!-- <td class="label">核算年度：</td>
	                <td class="ipt">
	                    <input id="year" type="text" />
	                </td> -->
                </tr>
            </table>

            <div id="mainGrid"></div>
        </div>
    </div>
    
	<div id="itemDiv" style="display: none">
		<table class="table-layout">
			<tr>
				<td class="label">考勤项目：</td>
				<td class="ipt">
					<select id="attend_code" type="text" style="width:160px"/>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>