<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,pageOffice,tab,datepicker,time,upload,validate" name="plugins" />
    </jsp:include>
<style type="text/css">
 	#file-upload .view-item {
		width: 220px;
		height: 220px;
		line-height : 218px;
	}
	#file-upload .view-item .default{
		font-size: 72px;
	}
     
	.ztree {
		height: 100%;
	}
	
    /* tab切换铺满 */
    .ettab-container {
        height: 100%;
        display: -webkit-flex;
        display: -ms-flex;
        display: flex;
    }

    .ettab-content {
        height: 95%;
        height: -moz-calc(100% - 28px);
        height: -webkit-calc(100% - 28px);
        height: calc(100% - 28px);
    }
    
    .ettab-content .ettab-panel.ettab-active {
        display: flex;
        flex-direction: column;
    }
</style>
<script type="text/javascript">
	var tree, file, fileMultiple;// 培训计划树、文件上传组件对象
	var examWay1, trainSite1, trainDate1, timeBegin1, timeEnd1;// 第一个页签tab_1(考试安排)
	var grid2;// 第二个页签tab_2(考试人员)
	var grid3;// 第三个页签tab_3（考试成绩）
	var grid4, examWay4, examSite4, examDate4, timeBegin4, timeEnd4;// 第四个页签tab_4（补考记录）
	var grid5, certDate5;// 第五个页签tab_5（培训证书）
	var deptId, empId, kindCode, isPass, editIsPass, isGive, editIsGive, trainType, isGiveTip, tabIdIndex;
	var tab2GridLoadFlag = tab3GridLoadFlag = tab4GridLoadFlag = tab5GridLoadFlag = true;// grid加载标记
	var globalLoadIndex;// 全局加载层索引
	
    window.onload = function(){
    	// 考试方式下拉
    	examWay1 = $("#exam_way_code1").etSelect({
    		url: "../../queryHrExamWaySelect.do?isCheck=false",
    		defaultValue: "none"
    	});
    	examWay4 = $("#exam_way_code4").etSelect({
    		url: "../../queryHrExamWaySelect.do?isCheck=false",
    		defaultValue: "none"
    	});
    	
    	// 考试地点下拉
    	var examSiteSelectObj = {
   			url: "../../queryHrTrainSiteSelect.do?isCheck=false",
       		defaultValue: "none",
       		create: true,
       		createOnBlur: true
    	};
    	trainSite1 = $("#train_site").etSelect(examSiteSelectObj);
    	examSite4 = $("#exam_site").etSelect(examSiteSelectObj);
    	
    	// 职工下拉 
    	empId = $("#emp_id").etSelect({
    		url: "../../queryEmpSelect.do?isCheck=false",
    		defaultValue: "none"
    	});
    	
    	// 部门下拉
    	deptId = $("#dept_id").etSelect({
    		url: "../../queryHosDeptSelect.do?isCheck=false",
    		defaultValue: "none",
    		onChange: function (value) {
                empId.reload({
                    url: '../../queryEmpSelect.do?isCheck=false',
                    para: {
                        dept_code: value,
                    }
                });
            }
    	});
    	
    	// 职工分类下拉
    	kindCode = $("#kind_code").etSelect({
    		url: "../../queryHrEmpKindSelect.do?isCheck=false",
    		defaultValue: "none"
        });
    	
    	$.etDialog.close(globalLoadIndex);
    }
	
	$(function(){
		globalLoadIndex = $.etDialog.load();
		
		initTab();
		initTree();
		initGrid();
		
		initDict();
		
		$("#search-tree").on('keyup', function(){
			var $self = $(this);
			searchTree({
				tree: tree,
				value: $self.val(),
				callback: function(){
					$self.focus();
				}
			});
		});
		
		$("#save-exam").click(saveExam);// 保存考试安排
		$("#save-bukao").click(saveBukao);// 保存补考安排
		$("#save-train-cert").click(saveTrainCert);// 保存培训证书
		
		$("input").attr("autocomplete", "off");
	});
	
	// 初始化树
	function initTree(){
		tree = $("#mainTree").etTree({
			async: {
				enable: true,
				url: 'getTrainPlanTreeJson.do?isCheck=false&isExam=1'
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
	    		},
				keep : {
					leaf : true
				},
				key : {
					children : "nodes"
				}
			},
			treeNode : {
				open : true
			},
			callback: {
				onNodeCreated : function(event, treeId, node) {
					tree.expandNode(node, true, false, false);
					if (node.nodes && node.level === 0 && node.nodes.length === 0) {
						treeObj.hideNode(node);
					}
				},
				onClick : function(event, treeId, node) {
					if(node.level == 1){
						trainType = node.pId;// 培训类别
						isGiveTip = node.isCert;// 是否发证
						
						// 切换培训计划时执行
						if($("#plan_id").val() != node.id){
							// grid加载标记，true:切换需要加载，false:切换不用再加载。
							tab2GridLoadFlag = tab3GridLoadFlag = tab4GridLoadFlag = tab5GridLoadFlag = true;
							
							grid3.getColumns()[2].editor.dataModel.url = 'queryTrainObjEmpData.do?isCheck=false&not_in_exam_res=1&plan_id=' + node.id;
							grid4.getColumns()[2].editor.dataModel.url = 'queryExamResultEmpData.do?isCheck=false&is_pass=2&not_in_bukao=1&plan_id=' + node.id;
						
							if(node.isCert == "1"){
								grid5.option("columns", eval(grid5Columns1Str).concat(grid5Columns2));
								grid5.option("toolbar.items", grid5ToolbarItems1.concat(grid5ToolbarItems2, grid5ToolbarItems2_1));
							}else{
								grid5.option("columns", eval(grid5Columns1Str));
								grid5.option("toolbar.items", grid5ToolbarItems1.concat(grid5ToolbarItems2_1));
							}
							grid5.getColumns()[2].editor.dataModel.url = 'queryExamBukaoResultEmpData.do?isCheck=false&is_pass=1&is_give_cert=1&plan_id=' + node.id;
							
							$("#plan_id").val(node.id);
							
							// 加载当前激活页签
							if(tabIdIndex == 2){
								queryExamEmp();
								tab2GridLoadFlag = false;
							}else if(tabIdIndex == 3){
								queryExamResult();
								tab3GridLoadFlag = false;
							}else if(tabIdIndex == 4){
								queryBukaoEmpExecute();
								tab4GridLoadFlag = false;
							}else if(tabIdIndex == 5){
								queryTrainEmpCertExecute();
								tab5GridLoadFlag = false;
							}
						}
						
						// 取培训计划有关的考试安排、补考安排、培训证书
						ajaxPostData({
			                url: 'queryTrainPlanRelationExam.do?isCheck=false',
			                data: {
			                    plan_id: node.id
			                },
			                delayCallback: true,
			                success: function (resData) {
		                		afreshRelationInput(resData);
			                }
			            });
					}
				}
			}
		});
	}
	
	// 初始化页面页签
	function initTab(){
		etTab = $("#etTab").etTab({
			onChange: function(item){
				tabIdIndex = item.index + 1;
				var index = item.index + 1;
				clearItemQueryPara();
				
				// 排列页签要用的查询条件
				if(index == 2){
					$("#tab_2_query_para").append($(".dept_id")).append($(".emp_id"))
										  .append($(".kind_code")).append($(".null_col"));
				}else if(index == 3){
					$("#tab_3_query_para").append($(".dept_id")).append($(".emp_id"))
										  .append($(".kind_code")).append($(".is_pass"));
				}else if(index == 4){
					$("#tab_4_query_para_row1").append($(".dept_id")).append($(".emp_id"));
					$("#tab_4_query_para_row2").append($(".kind_code")).append($(".is_pass"));
				}else if(index == 5){
					$("#tab_5_query_para_row1").append($(".dept_id")).append($(".emp_id"));
					$("#tab_5_query_para_row2").append($(".kind_code")).append($(".is_give"));
				}
				
				// 切换tab刷新grid(刷新视图)
				if(index != 1){
					window['grid' + index].refreshView();
					if($("#plan_id").val() != ""){
						// 加载当前激活页签，加载标记为true时，需要重新查数据
						if(index == 2 && tab2GridLoadFlag){
							queryExamEmp();
							tab2GridLoadFlag = false;
						}else if(index == 3 && tab3GridLoadFlag){
							queryExamResult();
							tab3GridLoadFlag = false;
						}else if(index == 4 && tab4GridLoadFlag){
							queryBukaoEmpExecute();
							tab4GridLoadFlag = false;
						}else if(index == 5 && tab5GridLoadFlag){
							queryTrainEmpCertExecute();
							tab5GridLoadFlag = false;
						}
					}
				}
			}
		});
	}
	
	// 初始化字典	
	function initDict(){
		var etDatepickerPara = {
			defaultDate: true,
			view: "days",
			minView: "days",
			dateFormat: "yyyy-mm-dd"
		};
		// 考试日期
		trainDate1 = $("#train_date1").etDatepicker(etDatepickerPara);
		// 补考日期
		examDate4 = $("#exam_date4").etDatepicker(etDatepickerPara);
		
		// 考试开始时间
		timeBegin1 = laydate.render({
			elem: '#time_begin1',
			type: 'time',
			format: 'HH:mm'
		});
		// 考试结束时间
		timeEnd1 = laydate.render({
			elem: '#time_end1',
			type: 'time',
			format: 'HH:mm'
		});
		// 补考开始时间
		timeBegin4 = laydate.render({
			elem: '#time_begin4',
			type: 'time',
			format: 'HH:mm'
		});
		// 补考结束时间
		timeEnd4 = laydate.render({
			elem: '#time_end4',
			type: 'time',
			format: 'HH:mm'
		});
		
		// 是否合格下拉
		var isPassSelecetObj = {
			backEndSearch: false,
			options: [
				{id: "1", text: "是"},
				{id: "2", text: "否"}
			],
			defaultValue: "none"
		};
		isPass = $("#is_pass").etSelect(isPassSelecetObj);
		editIsPass = $("#edit_is_pass").etSelect(isPassSelecetObj);
		
		// 是否发放下拉
		var isGiveSelectObj = {
			backEndSearch: false,
			options: [
				{id: "1", text: "是"},
				{id: "0", text: "否"}
			],
			defaultValue: "none"
		};
		isGive = $("#is_give").etSelect(isGiveSelectObj);
		editIsGive = $("#edit_is_give").etSelect(isGiveSelectObj);
		
		// 发证日期
		certDate5 = $("#cert_date5").etDatepicker({
			defaultDate: true,
			view: "days",
			minView: "days",
			dateFormat: "yyyy-mm-dd"
		});
	}
	var grid5Columns1Str = "[" +
		"{ display: '部门', name: 'dept_name', width: 120, align: 'left', editable: false }," +
		"{ display: '职工工号', name: 'emp_code', width: 120, align: 'left'," +
			"editor: {" +
				"type: 'grid'," +
				"columns: [" +
					"{ display: '职工id', name: 'emp_id', hidden: true }," +
					"{ display: '部门', name: 'dept_name', width: 120, align : 'left' }," +
					"{ display: '职工工号', name: 'emp_code', width: 120, align: 'left' }," +
					"{ display: '职工姓名', name: 'emp_name', width: 120, align: 'left' }," +
					"{ display: '人员类别', name: 'kind_name', width: 120, align: 'left' }" +
				"]," +
				"width: '600px'," +
				"height: '216px'," +
				"dataModel: {" +
					"url: ''" +
				"}" +
			"}" +
		"}," +
		"{ display: '职工姓名', name: 'emp_name', width: 120, align: 'left', editable: false }," +
		"{ display: '人员类别', name: 'kind_name', width: 120, align: 'left', editable: false }," +
		"{ display: '证书上传与下载', name: '', width: 100, align: 'center', editable: false," +
			"render: function(ui){" +
				"if(ui.rowData.group_id){" +
					"var path = ui.rowData.cert_path;" +
					"if(path){" +
						"var arr = path.split('/');" +
						"var name = arr[arr.length-1];" +
						"return '<a class=\"certUpload\" row-index=\"' + ui.rowData._rowIndx + '\">上传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"' + ui.rowData.cert_path + '\" download=\"' + name + '\">下载</a>';" +
					"}else{" +
						"return '<a class=\"certUpload\" row-index=\"' + ui.rowData._rowIndx + '\">上传</a>';" +
					"}" +
				"}" +
			"}" +
		"}" +
    "]"; 
	var grid5Columns2 = [
		{ display: '电子证书', name: '', width: 80, align: 'center', editable: false,
			render: function(ui){
				if(ui.rowData.group_id){
					return '<a onclick="dzzsPrint(this);" row-index="' + ui.rowData._rowIndx + '">打印</a>';
				}
			}
		},
		{ display: '证书编号', name: 'cert_code', width: 120, align: 'left', editable: false },
		{ display: '是否发放', name: 'is_give', width: 80, align: 'center', editable: false,
			render: function(ui){
				if(ui.rowData.group_id){
					if(ui.rowData.is_give == 1){
						return '<input onclick="clickGiveChecked(this);" row_index="' + ui.rowData._rowIndx + '" type="checkbox" value="1" name="givechecked" checked="checked"/>';
					}else{
						return '<input onclick="clickGiveChecked(this);" row_index="' + ui.rowData._rowIndx + '" type="checkbox" value="1" name="givechecked"/>';
					}
				}
			}
		}
	];
	var grid5ToolbarItems1 = [
		{ type: 'button', label: '查询', listeners: [{ click: queryTrainEmpCert }], icon: 'search' },
		{ type: 'button', label: '生成', listeners: [{ click: generateTrainEmpCert}], icon: 'document' },
		{ type: 'button', label: '添加', listeners: [{ click: addTrainEmpCert }], icon: 'add' },
		{ type: 'button', label: '保存', listeners: [{ click: saveTrainEmpCert }], icon: 'save' },
		{ type: 'button', label: '删除', listeners: [{ click: deleteTrainEmpCert}], icon: 'delete' } 
	];
	var grid5ToolbarItems2 = [
		{ type: 'button', label: '证书模板设置', listeners: [{ click: setTemplateCert }], icon: 'document' },
		{ type: 'button', label: '证书批量打印', listeners: [{ click: certPrintBatch }], icon: 'print' },
		{ type: 'button', label: '批量设置', listeners: [{ click: setEmpCertBatch }], icon: 'gear' }
	];
	var grid5ToolbarItems2_1 = [
		{ type: 'button', label: '批量下载', listeners: [{ click: downloadCertBatch}], icon: 'import2' },
		{ type: 'button', label: '批量上传', listeners: [{ click: uploadCertBatch}], icon: 'add2' }
	];
	
	var initGrid = function () {
		// 考试人员表格
		grid2 = $("#examEmpGrid").etGrid({
			height: '100%',
			inWindowHeight: true,
			columns: [
      			{ display: '部门', name: 'dept_name', width: 120, align: 'left' },
      			{ display: '职工工号', name: 'emp_code', width: 120, align: 'left' },
      			{ display: '职工姓名', name: 'emp_name', width: 120, align: 'left' },
      			{ display: '人员类别', name: 'kind_name', width: 120, align: 'left' }
      		],
			toolbar: {
				items: [
					{ type: 'button', label: '查询', listeners: [{ click: queryExamEmp }], icon: 'search' },
					{ type: 'button', label: '打印', listeners: [{ click: printExamEmp }], icon: 'print' }
				]
			}
		});
		
		// 考试成绩
		grid3 = $("#examResultGrid").etGrid({
			height: '100%',
			inWindowHeight: true,
			editable: true,
			checkbox: true,
			columns: [
     			{ display: '部门', name: 'dept_name', width: 120, align: 'left', editable: false },
     			{ display: '职工工号', name: 'emp_code', width: 120, align: 'left',
     				editor: {
     					type: 'grid',
     					columns: [
							{ display: '职工id', name: 'emp_id', hidden: true },
							{ display: '部门', name: 'dept_name', width: 120, align: 'left' },
							{ display: '职工工号', name: 'emp_code', width: 120, align: 'left' },
							{ display: '职工姓名', name: 'emp_name', width: 120, align: 'left' },
							{ display: '人员类别', name: 'kind_name', width: 120, align: 'left' }
     					],
     					width: '600px',
     					height: '216px',
     					dataModel: {
     						url: ''
     					}
     				}
     			},
     			{ display: '职工姓名', name: 'emp_name', width: 120, align: 'left', editable: false },
     			{ display: '人员类别', name: 'kind_name', width: 120, align: 'left', editable: false },
     			{ display: '考试成绩', name: 'score', width: 120, align: 'left',
     				editor: {
     					type: 'number'
     				}	
     			},
     			{ display: '是否合格', name: 'is_pass_name', width: 120, align: 'left',
     				editor: {
     					type: 'select',
     					valueField : 'is_pass',
     					source: [
  							{"label": "是", "id": '1'}, 
  							{"label": "否", "id": '2'}
  						]
     				}	
     			},
     			{ display: '原始卷', name: 'paper_path', width: 120, align: 'center', editable: false,
     				render: function(ui){
     					if(ui.rowData.paper_path){
     						var path = ui.rowData.paper_path;
        					var arr = path.split('/');
                   		  	var name = arr[arr.length-1];
                   		 	return '<a class="openPaper" row-index="' + ui.rowData._rowIndx + '">' + name + '</a>';
     					}
     				}
     			},
     			{ display: '操作', name: '', width: 100, align: 'center', editable: false,
     				render: function(ui){
     					if(ui.rowData.group_id){
	     					var path = ui.rowData.paper_path;
	     					if(path){
	    						var arr = path.split('/');
	        	       		  	var name = arr[arr.length-1];
	     						return '<a class="paperUpload" row-index="' + ui.rowData._rowIndx + '">上传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="' + ui.rowData.paper_path + '" download="' + name + '">下载</a>';
	     					}else{
	     						return '<a class="paperUpload" row-index="' + ui.rowData._rowIndx + '">上传</a>';
	     					}
     					}
     				}
     			},
     			{ display: '', name: 'emp_id', hidden: true, editable: false }
     		],
			toolbar: {
				items: [
					{ type: 'button', label: '查询', listeners: [{ click: queryExamResult }], icon: 'search' },
					{ type: 'button', label: '生成', listeners: [{ click: generateExamResult }], icon: 'document' },
					{ type: 'button', label: '添加', listeners: [{ click: addExamResult }], icon: 'add' },
					{ type: 'button', label: '删除', listeners: [{ click: deleteExamResult }], icon: 'delete' },
					{ type: 'button', label: '保存', listeners: [{ click: saveExamResult }], icon: 'save' },
					{ type: 'button', label: '导入', listeners: [{ click: impExamResult }], icon: 'import' },
					{ type: 'button', label: '批量修改', listeners: [{ click: editExamResultBatch }], icon: 'edit' },
					{ type: 'button', label: '批量上传原始卷', listeners: [{ click: impPaperBatch }], icon: 'add2' }
				]
			}
		});
		$("#examResultGrid").on("click", ".paperUpload", function(){
			var examResrowI = $(this).attr("row-index");
			var examResRowD = grid3.getAllData()[examResrowI];
			paperUpload(examResRowD);
		});
		$("#examResultGrid").on("click", ".openPaper", function(){
			var examResrowI = $(this).attr("row-index");
			var examResRowD = grid3.getAllData()[examResrowI];
			openPaper(examResRowD);
		});
		
		// 补考记录
		grid4 = $("#bukaoGrid").etGrid({
			height: '100%',
			inWindowHeight: true,
			editable: true,
			checkbox: true,
			columns: [
     			{ display: '部门', name: 'dept_name', width: 120, align: 'left', editable: false },
     			{ display: '职工工号', name: 'emp_code', width: 120, align: 'left',
     				editor: {
     					type: 'grid',
     					columns: [
							{ display: '职工id', name: 'emp_id', hidden: true },
							{ display: '部门', name: 'dept_name', width: 120, align: 'left' },
							{ display: '职工工号', name: 'emp_code', width: 120, align: 'left' },
							{ display: '职工姓名', name: 'emp_name', width: 120, align: 'left' },
							{ display: '人员类别', name: 'kind_name', width: 120, align: 'left' }
     					],
     					width: '600px',
     					height: '216px',
     					dataModel: {
     						url: ''
     					}
     				}
     			},
     			{ display: '职工姓名', name: 'emp_name', width: 120, align: 'left', editable: false },
     			{ display: '人员类别', name: 'kind_name', width: 120, align: 'left', editable: false },
     			{ display: '考试成绩', name: 'score', width: 120, align: 'left',
     				editor: {
     					type: 'number'
     				}	
     			},
     			{ display: '是否合格', name: 'is_pass_name', width: 120, align: 'left', 
     				editor: {
     					type: 'select',
     					valueField : 'is_pass',
     					source: [
  							{"label": "是", "id": '1'}, 
  							{"label": "否", "id": '2'}
  						]
     				}	
     			},
     			{ display: '原始卷', name: 'paper_path', width: 120, align: 'center', editable: false,
     				render: function(ui){
     					if(ui.rowData.group_id){
     						var path = ui.rowData.paper_path;
     						if(path){
	        					var arr = path.split('/');
	                   		  	var name = arr[arr.length-1];
	                   		 	return '<a class="openPaper" row-index="' + ui.rowData._rowIndx + '">' + name + '</a>';
     						}
     					}
     				}
     			},
     			{ display: '操作', name: '', width: 100, align: 'center', editable: false,
     				render: function(ui){
     					if(ui.rowData.group_id){
     						var path = ui.rowData.paper_path;
	     					if(path){
	    						var arr = path.split('/');
	        	       		  	var name = arr[arr.length-1];
	     						return '<a class="bukaoPaperUpload" row-index="' + ui.rowData._rowIndx + '">上传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="' + ui.rowData.paper_path + '" download="' + name + '">下载</a>';
	     					}else{
	     						return '<a class="bukaoPaperUpload" row-index="' + ui.rowData._rowIndx + '">上传</a>';
	     					}
     					}
     				}
     			},
     			{ display: '', name: 'emp_id', hidden: true }
     		],
			toolbar: {
				items: [
					{ type: 'button', label: '查询', listeners: [{ click: queryBukaoEmp }], icon: 'search' },
					{ type: 'button', label: '生成', listeners: [{ click: generateBukaoEmpBatch }], icon: 'document' },
					{ type: 'button', label: '添加', listeners: [{ click: addBukaoEmp }], icon: 'add' },
					{ type: 'button', label: '删除', listeners: [{ click: deleteBukaoEmp }], icon: 'delete' },
					{ type: 'button', label: '保存', listeners: [{ click: saveBukaoEmp }], icon: 'save' },
					{ type: 'button', label: '批量修改', listeners: [{ click: editBukaoEmpBatch }], icon: 'edit' },
					{ type: 'button', label: '导入', listeners: [{ click: impBukaoEmp }], icon: 'import' },
					{ type: 'button', label: '批量上传原始卷', listeners: [{ click: uploadBukaoPaperBatch }], icon: 'add2' }
				]
			}
		});
		$("#bukaoGrid").on("click", ".bukaoPaperUpload", function(){
			var bukaoResrowI = $(this).attr("row-index");
			var bukaoResRowD = grid4.getAllData()[bukaoResrowI];
			bukaoPaperUpload(bukaoResRowD);
		});
		$("#bukaoGrid").on("click", ".openPaper", function(){
			var bukaoResrowI = $(this).attr("row-index");
			var bukaoResRowD = grid4.getAllData()[bukaoResrowI];
			openPaper(bukaoResRowD);
		});
		
		// 培训证书
		grid5 = $("#trainCertGrid").etGrid({
			height: '100%',
			inWindowHeight: true,
			editable: true,
			checkbox: true,
			columns: eval(grid5Columns1Str),
			toolbar: {
				items: grid5ToolbarItems1.concat(grid5ToolbarItems2_1)
			}
		});
		$("#trainCertGrid").on("click", ".certUpload", function(){
			var certRowI = $(this).attr("row-index");
			var certRowD = grid5.getAllData()[certRowI];
			certUpload(certRowD);
		});
	}
	
	// 考试人员start******************************
	// 查询考试人员
	function queryExamEmp(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		var params = [
 			{name: "plan_id", value: $("#plan_id").val()},
			{name: "emp_id", value: empId.getValue()},
			{name: "dept_code", value: deptId.getValue()},
			{name: "kind_code", value: kindCode.getValue()}
		];
		grid2.loadData(params, "../record/queryTrainRecordTarget.do?isCheck=false");
	}
	
	// 打印考试人员
	function printExamEmp(){
		if(grid2.getAllData() == null){
			$.etDialog.warn("请先查询数据！");
			return;
		}
		var heads = {
		};
		var printPara = {
			title: " 考试人员",// 标题
			columns: JSON.stringify(grid2.getPrintColumns()),
			class_name: "com.chd.hrp.hr.service.training.HrTrainRecordService",
			method_name: "queryTrainRecordTargetPrint",
			bean_name: "hrTrainRecordService",
			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
			foots: '',//表尾需要打印的查询条件,可以为空
		};
		
		$.each(grid2.getUrlParms(), function(i, obj){
			printPara[obj.name] = obj.value;
		});
		officeGridPrint(printPara);
	}
	// 考试人员end******************************
	
	// 考试成绩start*********************************
	// 考试成绩查询
	function queryExamResult(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		var params = [
  			{name: "plan_id", value: $("#plan_id").val()},
  			{name: "emp_id", value: empId.getValue()},
  			{name: "dept_id", value: deptId.getValue()},
  			{name: "kind_code", value: kindCode.getValue()},
  			{name: "is_pass", value: isPass.getValue()}
  		];
		grid3.loadData(params, "queryExamResult.do?isCheck=false");
	}
	
	// 生成考试结果记录
	function generateExamResult(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请先选择培训计划.');
			return;
		}
		
		ajaxPostData({
            url: 'generateExamResult.do?isCheck=false',
            data: {
            	plan_id: $("#plan_id").val()
            },
            success: function (res) {
            	queryExamResult();
            }
        });
	}
	
	// 考试成绩添加
	function addExamResult(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		grid3.addRow();
	}
	
	// 考试成绩删除
	function deleteExamResult(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		
		var selectData = grid3.selectGet();
		if(selectData.length == 0){
			$.etDialog.warn('请选择行');
            return;
		}
		
		var param = [];
		selectData.forEach(function(item){
			param.push(item.rowData);
		});
		$.etDialog.confirm('确定删除？', function(){
			ajaxPostData({
				url: 'deleteExamResult.do?isCheck=false',
				data: {
					paramVo: JSON.stringify(param)
				},
				success: function(){
					grid3.deleteRows(selectData);
				}
			});
		});
	}
	
	// 考试成绩保存
	function saveExamResult(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		
		var gridAllData = grid3.getAllData();
		
		var isPass = grid3.validateTest({
			required: {
				emp_code: true
			}
		});
		if(!isPass){
			return;
		}
		if(checkGridDataRepeat(grid3, gridAllData, ['emp_code'])){
			return;
		}
		
		var param = [];
		gridAllData.forEach(function(item){
			param.push(item);
		});
		ajaxPostData({
			url: 'saveExamResult.do?isCheck=false',
			data: {
				plan_id: $("#plan_id").val(),
				paramVo: JSON.stringify(param)
			},
			success: function(){
				queryExamResult();
			}
		});
	}
	
	// 考试成绩导入
	function impExamResult(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		var para = {
			"plan_id": $("#plan_id").val(),
  			"column" : [ 
  				{ "name": "emp_code", "display": "职工工号", "width": "200", "require": true },
  				{ "name": "emp_name", "display": "职工姓名", "width": "200" },
  				{ "name": "score", "display" : "考试成绩", "width": "200" },
  				{ "name": "is_pass", "display": "是否合格", "width": "200", "require": true }
  			]
  		};
		importSpreadView("/hrp/hr/training/examine/importExamResult.do?isCheck=false", para, queryExamResult);
	}
	
	// 上传考试原始卷
	function paperUpload(rowData){
		if(!rowData.plan_id){
			$.etDialog.warn("请先保存考试成绩.");
			return;
		}
		initFileWindows();
		$.etDialog.open({
			title: '上传',
			type: 1,
			content: $("#file-upload-div"),
			width: 340,
			height: 340,
			btn: ['上传', '取消'],
			btn1: function(index, el){
				paperUploadExe(rowData, index);
			},
			btn2: function(index, el){
				file.setValue("");
			}
		});
	}
	
	// 上传考试原始卷执行
	function paperUploadExe(rowData, index){
		var fileForm = new FormData();
		
		if(file.getValue() == undefined){
			$.etDialog.warn("请选择文件.");
			return;
		}
		
		fileForm.append("plan_id", rowData.plan_id);
		fileForm.append("emp_id", rowData.emp_id);
		fileForm.append("emp_code", rowData.emp_code);
		fileForm.append("paper_path", rowData.paper_path);
		fileForm.append("PAPER_FILE", file.getValue());
		ajaxPostFormData({
			url: 'uploadExamPaper.do?isCheck=false',
			data: fileForm,
			dataType: 'json',
			success: function(){
				queryExamResult();
				$.etDialog.close(index);
				file.setValue("");
			}
		});
	}
	
	// 查看原始卷
	function openPaper(rowData){
		ajaxPostData({
			url: 'checkFileIsExists.do?isCheck=false',
			data: {
				file_path: rowData.paper_path
			},
			success: function(res){
				if(res.exists == "true"){
					initFileWindows();
					
					file.setValue(rowData.paper_path);
					$("#file-upload .view-image img").attr("src", rowData.paper_path + "?t=" + new Date().getTime());
					$("#file-upload .delete").hide();
					
					$.etDialog.open({
						title: '查看',
						type: 1,
						content: $("#file-upload-div"),
						width: 340,
						height: 340,
						btn: ['关闭'],
						btn1: function(index, el){
							$.etDialog.close(index);
							file.setValue("");
						}
					});
				}else{
					$.etDialog.warn(res.warn);
				}
			}
		});
	}
	
	// 批量修改考试成绩
	function editExamResultBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		
		var allData = grid3.getAllData();
		if(allData == null || allData.length == 0){
			$.etDialog.warn("请先查出数据。");
			return;
		}
		// 取得行数据
		var paraData = getBatchEditPara(grid3);
		
		var selectData = grid3.selectGet();
		
		if(selectData.length != 0){
			editExamResultBatchExe(paraData);
		}else{
			$.etDialog.confirm('没有选择行，将修改全部数据，确定执行吗？', function(index, el){
				editExamResultBatchExe(paraData);
				$.etDialog.close(index);
			});
		}
	}
	// 批量修改考试成绩执行
	function editExamResultBatchExe(para){
		$("#edit_score").val("");
		editIsPass.clearItem();
		$(".flag-checkbox").prop("checked", false);
		
		$.etDialog.open({
			title: '批量修改',
			type: 1,
			content: $("#exam-score-div"),
			width: 400,
			height: 400,
			btn: ['确定', '取消'],
			btn1: function(index, el){
				var flag = false;
				// 考试成绩分数
				if($("#edit_score_checkbox").prop("checked")){
					if($("#edit_score").val() == "" || /^[0-9]*$/.test($("#edit_score").val()) == false){
						$.etDialog.warn("考试成绩只能是数字！");
						return;
					}else{
						para["score"] = $("#edit_score").val();
						flag = true;
					}
				}
				// 考试成绩是否合格
				if($("#edit_is_pass_checkbox").prop("checked")){
					if(editIsPass.getValue() == ""){
						$.etDialog.warn("是否合格请选择值.");
						return;
					}else{
						para["is_pass"] = editIsPass.getValue();
						flag = true;
					}
				}
				
				// 执行更新
				if(flag){
					ajaxPostData({
						url: 'updateExamScoreBatch.do?isCheck=false',
						data: para,
						success: function(){
							$.etDialog.close(index);
							queryExamResult();
						}
					});
				}else{
					$.etDialog.warn("没有选择要修改的项.");
				}
			}
		});
	}
	
	// 考试成绩批量上传原始卷
	function impPaperBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		
		initMultipleFileWindows();
		$.etDialog.open({
			title: '批量上传',
			type: 1,
			content: $("#file-upload-div2"),
			width: 500,
			height: 400,
			btn: ['上传', '取消'],
			btn1: function(index, el){
				impPaperBatchExe(index);
			},
			btn2: function(index, el){
				fileMultiple.setValues(['']);
			}
		});
	}
	
	// 考试成绩批量上传原始卷执行
	function impPaperBatchExe(index){
		if(grid3.getAllData() == null){
			$.etDialog.warn("请先查出数据.");
			return;
		}
		
		var fileForm = new FormData();
		fileForm.append("plan_id", $("#plan_id").val());
		var files = fileMultiple.getValues();
		
		$(files).each(function(i){
			fileForm.append("file" + i, this);
		});
		fileForm.append("fileNum", files.length);
		
		ajaxPostFormData({
			url: 'uploadExamPaperBatch.do?isCheck=false',
			data: fileForm,
			dataType: 'json',
			success: function(){
				queryExamResult();
				$.etDialog.close(index);
				fileMultiple.setValues(['']);
			}
		});
	}
	// 考试成绩end******************************
	
	// 补考成绩start*********************************
	// 保存补考安排
	function saveBukao(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请先选择培训计划.');
			return;
		}
		
		var formValidate = $.etValidate({
			items: [
				{el: $("#exam_way_code4"), required: true},
				{el: $("#exam_date4"), required: true},
				{el: $("#time_begin4"), required: true},
				{el: $("#time_end4"), required: true}
			]
		});
		if(!formValidate.test()){
			return;
		}
		
		ajaxPostData({
            url: 'saveTrainBukao.do?isCheck=false',
            data: {
            	plan_id: $("#plan_id").val(),
            	exam_way_code: examWay4.getValue(),
            	exam_date: examDate4.getValue(),
            	time_begin: $("#time_begin4").val(),
            	time_end: $("#time_end4").val(),
            	exam_site: examSite4.getText(),
            	emp_num: $("#emp_num").val()
            },
            success: function (res) {
            	if(res.empNum){
            		$("#emp_num").val(res.empNum);
            	}
            }
        });
	}
	
	// 补考成绩查询
	function queryBukaoEmp(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		$.etDialog.open({
			title: '补考人员查询条件',
			type: 1,
			content: $("#bukao-query-para-div"),
			width: 600,
			height: 300,
			btn: ['查询', '取消'],
			btn1: function(index, el){
				queryBukaoEmpExecute();
				$.etDialog.close(index);
			}
		});
	}
	// 补考成绩查询执行
	function queryBukaoEmpExecute(){
		var params = [
   			{name: "plan_id", value: $("#plan_id").val()},
   			{name: "emp_id", value: empId.getValue()},
   			{name: "dept_id", value: deptId.getValue()},
   			{name: "kind_code", value: kindCode.getValue()},
   			{name: "is_pass", value: isPass.getValue()}
   		];
   		grid4.loadData(params, "queryBukaoEmp.do?isCheck=false");
	}
	
	// 补考成绩添加
	function addBukaoEmp(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		grid4.addRow();
	}
	
	// 补考成绩删除
	function deleteBukaoEmp(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		
		var selectData = grid4.selectGet();
		if(selectData.length == 0){
			$.etDialog.warn('请选择行');
            return;
		}
		
		var param = [];
		selectData.forEach(function(item){
			param.push(item.rowData);
		});
		$.etDialog.confirm('确定删除？', function(){
			ajaxPostData({
				url: 'deleteBukaoEmp.do?isCheck=false',
				data: {
					plan_id: $("#plan_id").val(),
					paramVo: JSON.stringify(param)
				},
				success: function(res){
					grid4.deleteRows(selectData);
					if(res.empNum){
						$("#emp_num").val(res.empNum);
					}
				}
			});
		});
	}
	
	// 补考成绩保存
	function saveBukaoEmp(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		
		var gridAllData = grid4.getAllData();
		
		var isPass = grid4.validateTest({
			required: {
				emp_code: true
			}
		});
		if(!isPass){
			return;
		}
		
		if(checkGridDataRepeat(grid4, gridAllData, ['emp_code'])){
			return;
		}
		
		var param = [];
		gridAllData.forEach(function(item){
			param.push(item);
		});
		ajaxPostData({
			url: 'saveBukaoEmp.do?isCheck=false',
			data: {
				plan_id: $("#plan_id").val(),
				paramVo: JSON.stringify(param)
			},
			success: function(res){
				queryBukaoEmpExecute();
				if(res.empNum){
					$("#emp_num").val(res.empNum);
				}
			}
		});
	}
	
	// 批量修改补考记录
	function editBukaoEmpBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		
		var allData = grid4.getAllData();
		if(allData == null || allData.length == 0){
			$.etDialog.warn("请先查出数据。");
			return;
		}
		
		// 取得行数据
		var paraData = getBatchEditPara(grid4);
		
		var selectData = grid4.selectGet();
		
		if(selectData.length != 0){
			editBukaoEmpBatchExe(paraData);
		}else{
			$.etDialog.confirm('没有选择行，将修改全部数据，确定执行吗？', function(index, el){
				editBukaoEmpBatchExe(paraData);
				$.etDialog.close(index);
			});
		}
	}
	// 批量修改补考记录执行
	function editBukaoEmpBatchExe(para){
		$("#edit_score").val("");
		editIsPass.clearItem();
		$(".flag-checkbox").prop("checked", false);
		
		$.etDialog.open({
			title: '批量修改',
			type: 1,
			content: $("#exam-score-div"),
			width: 400,
			height: 400,
			btn: ['确定', '取消'],
			btn1: function(index, el){
				var flag = false;
				// 补考成绩分数
				if($("#edit_score_checkbox").prop("checked")){
					if($("#edit_score").val() == "" || /^[0-9]*$/.test($("#edit_score").val()) == false){
						$.etDialog.warn("考试成绩只能是数字！");
						return;
					}else{
						para["score"] = $("#edit_score").val();
						flag = true;
					}
				}
				// 补考成绩是否合格
				if($("#edit_is_pass_checkbox").prop("checked")){
					if(editIsPass.getValue() == ""){
						$.etDialog.warn("是否合格请选择值.");
						return;
					}else{
						para["is_pass"] = editIsPass.getValue();
						flag = true;
					}
				}
				
				// 执行更新
				if(flag){
					ajaxPostData({
						url: 'updateBukaoScoreBatch.do?isCheck=false',
						data: para,
						success: function(){
							$.etDialog.close(index);
							queryBukaoEmpExecute();
						}
					});
				}else{
					$.etDialog.warn("没有选择要修改的项.");
				}
			}
		});
	}
	
	// 补考成绩导入
	function impBukaoEmp(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		var para = {
			"plan_id": $("#plan_id").val(),
  			"column" : [ 
  				{ "name": "emp_code", "display": "职工工号", "width": "200", "require": true },
  				{ "name": "emp_name", "display" : "职工姓名", "width": "200" },
  				{ "name": "score", "display" : "考试成绩", "width": "200" },
  				{ "name": "is_pass", "display": "是否合格", "width": "200", "require": true }
  			]
  		};
		importSpreadView("/hrp/hr/training/examine/importBukaoEmp.do?isCheck=false", para, queryBukaoEmpExecute);
	}
	
	// 补考记录原始卷上传
	function bukaoPaperUpload(rowData){
		if(!rowData.plan_id){
			$.etDialog.warn("请先保存补考成绩.");
			return;
		}
		
		initFileWindows();
		$.etDialog.open({
			title: '上传',
			type: 1,
			content: $("#file-upload-div"),
			width: 340,
			height: 340,
			btn: ['上传', '取消'],
			btn1: function(index, el){
				bukaoPaperUploadExe(rowData, index);
			},
			btn2: function(index, el){
				file.setValue("");
			}
		});
	}
	// 补考记录原始卷上传执行
	function bukaoPaperUploadExe(rowData, index){
		var fileForm = new FormData();
		fileForm.append("plan_id", rowData.plan_id);
		fileForm.append("emp_id", rowData.emp_id);
		fileForm.append("emp_code", rowData.emp_code);
		fileForm.append("paper_path", rowData.paper_path);
		fileForm.append("PAPER_FILE", file.getValue());
		ajaxPostFormData({
			url: 'uploadBukaoPaper.do?isCheck=false',
			data: fileForm,
			dataType: 'json',
			success: function(){
				queryBukaoEmpExecute();
				$.etDialog.close(index);
				file.setValue("");
			}
		});
	}
	
	// 补考成绩批量上传原始卷
	function uploadBukaoPaperBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		
		initMultipleFileWindows();
		$.etDialog.open({
			title: '批量上传',
			type: 1,
			content: $("#file-upload-div2"),
			width: 500,
			height: 400,
			btn: ['上传', '取消'],
			btn1: function(index, el){
				uploadBukaoPaperBatchExe(index);
			},
			btn2: function(index, el){
				fileMultiple.setValues(['']);
			}
		});
	}
	// 补考成绩批量上传原始卷执行
	function uploadBukaoPaperBatchExe(index){
		if(grid4.getAllData() == null){
			$.etDialog.warn("请先查出数据.");
			return;
		}
		
		var fileForm = new FormData();
		fileForm.append("plan_id", $("#plan_id").val());
		var files = fileMultiple.getValues();
		
		$(files).each(function(i){
			fileForm.append("file" + i, this);
		});
		fileForm.append("fileNum", files.length);
		
		ajaxPostFormData({
			url: 'uploadBukaoPaperBatch.do?isCheck=false',
			data: fileForm,
			dataType: 'json',
			success: function(){
				queryBukaoEmpExecute();
				$.etDialog.close(index);
				fileMultiple.setValues(['']);
			}
		});
	}
	
	// 批量生成补考记录
	function generateBukaoEmpBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请先选择培训计划.');
			return;
		}
		
		ajaxPostData({
            url: 'generateBukaoEmpBatch.do?isCheck=false',
            data: {
            	plan_id: $("#plan_id").val()
            },
            success: function (res) {
            	queryBukaoEmpExecute();
            	if(res.empNum){
            		$("#emp_num").val(res.empNum);
            	}
            }
        });
	}
	// 补考成绩end******************************
	
	// 培训证书start***************************************
	// 保存培训证书
	function saveTrainCert(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请先选择培训计划.');
			return;
		}
		
		var formValidate = $.etValidate({
			items: [
				{el: $("#cert_name"), required: true},
				{el: $("#cert_date5"), required: true},
				{el: $("#cert_unit"), required: true}
			]
		});
		if(!formValidate.test()){
			return;
		}
		
		ajaxPostData({
            url: 'saveTrainCert.do?isCheck=false',
            data: {
            	plan_id: $("#plan_id").val(),
            	cert_name: $("#cert_name").val(),
            	cert_date: certDate5.getValue(),
            	cert_unit: $("#cert_unit").val()
            },
            success: function (res) {
            	
            }
        });
	}
	
	// 查询职工证书
	function queryTrainEmpCert(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请先选择培训计划.');
			return;
		}
		
   		$.etDialog.open({
			title: '培训人员证书查询条件',
			type: 1,
			content: $("#train-emp-cert-query-para-div"),
			width: 600,
			height: 300,
			btn: ['查询', '取消'],
			btn1: function(index, el){
				queryTrainEmpCertExecute();
				$.etDialog.close(index);
			}
		});
	}
	// 查询职工证书执行
	function queryTrainEmpCertExecute(){
		var params = [
   			{name: "plan_id", value: $("#plan_id").val()},
   			{name: "emp_id", value: empId.getValue()},
   			{name: "kind_code", value: kindCode.getValue()},
   			{name: "dept_id", value: deptId.getValue()},
   			{name: "is_give", value: isGive.getValue()}
   		];
   		grid5.loadData(params, "queryTrainEmpCert.do?isCheck=false");
	}
	
	// 生成职工证书
	function generateTrainEmpCert(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		ajaxPostData({
            url: 'generateTrainExamCert.do?isCheck=false',
            data: {
            	plan_id: $("#plan_id").val()
            },
            success: function (res) {
            	queryTrainEmpCertExecute();
            }
        });
	}
	
	// 添加职工证书
	function addTrainEmpCert(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		grid5.addRow();
	}
	
	// 保存职工证书
	function saveTrainEmpCert(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		
		var selectData = grid5.selectGet();
		if(selectData.length == 0){
			$.etDialog.warn('请选择行');
            return;
		}
		
		var isPass = grid5.validateTest({
			required: {
				emp_code: true
			}
		});
		if(!isPass){
			return;
		}
		
		var param = [];
		selectData.forEach(function(item){
			param.push(item.rowData);
		});
		
		if (checkGridDataRepeat(grid5, param, ['emp_code'])) {
            return;
        }
		
		ajaxPostData({
			url: 'saveTrainEmpCert.do?isCheck=false',
			data: {
				plan_id: $("#plan_id").val(),
				paramVo: JSON.stringify(param)
			},
			success: function(){
				queryTrainEmpCertExecute();
			}
		});
	}
	
	// 删除职工证书
	function deleteTrainEmpCert(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请选择培训计划.');
			return;
		}
		
		var selectData = grid5.selectGet();
		if(selectData.length == 0){
			$.etDialog.warn('请选择行');
            return;
		}
		
		var param = [];
		selectData.forEach(function(item){
			param.push(item.rowData);
		});
		$.etDialog.confirm('确定删除？', function(){
			ajaxPostData({
				url: 'deleteTrainEmpCert.do?isCheck=false',
				data: {
					paramVo: JSON.stringify(param)
				},
				success: function(){
					grid5.deleteRows(selectData);
				}
			});
		});
	}
	
	// 证书模板设置
	function setTemplateCert(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		
		var useId = 0;
		var p06007 = "${p06007}";// 系统参数
		if(p06007 == "0"){
			useId = 0;// 统一模板
		}else if(p06007 == "1"){
			useId = trainType;// 按培训类别设置模板
		}else if(p06007 == "2"){
			useId = $("#plan_id").val();// 按培训主题(计划)设置模板
		}
		
		officeFormTemplate({template_code: "06001", use_id: useId});
	}
	
	// 证书批量打印
	function certPrintBatch(){
		if(grid5.getAllData() == null){
			$.etDialog.warn("请先查出数据.");
			return;
		}
		
		var selectData = grid5.selectGet();
		if(selectData.length != 0){
			certPrintBatchExe(grid5);
		}else{
			$.etDialog.confirm('没有选择行，将批量打印全部数据，确定执行吗？', function(index, el){
				$.etDialog.close(index);
				certPrintBatchExe(grid5);
			});
		}
	}
	// 证书批量打印执行
	function certPrintBatchExe(grid5){
		var gridData = grid5.selectGet();
		
		var rows = [];
		var total = 0;
		if(gridData.length != 0){
			gridData.forEach(function(item){
				rows.push({print_id: item.rowData.emp_id, print_code: item.rowData.emp_code, print_name: item.rowData.emp_name});
				total++;
			});
		}else{
			gridData = grid5.getAllData();
			
			gridData.forEach(function(item){
				rows.push({print_id: item.emp_id, print_code: item.emp_code, print_name: item.emp_name});
				total++;
			});
		}
		
		var useId = 0;
		var p06007 = "${p06007}";
		if(p06007 == "0"){
			useId = 0;// 统一模板
		}else if(p06007 == "1"){
			useId = trainType;// 按培训类别设置模板
		}else if(p06007 == "2"){
			useId = $("#plan_id").val();// 按培训主题(计划)设置模板
		}
		
	   	var para = {
			plan_id: $("#plan_id").val(),
			class_name: "com.chd.hrp.hr.service.training.HrTrainExamineService",
			method_name: "generateDZBTrainEmpCert",
			bean_name: "hrTrainExamineService",
			template_code: '06001',// 打印模板参数
			isPrintCount: false,//更新打印次数
			isPreview: true,//预览窗口，传绝对路径
			use_id: useId
		};
	   	
	   	officeFormPrintBatch(para, {Total: total, Rows: rows}); 
	}
	// 证书批量打印执行
	function certPrintBatchExe1(grid5){
		var gridData = grid5.selectGet();
		var empIds = "";
		if(gridData.length != 0){
			gridData.forEach(function(item){
				empIds += "," + item.rowData.emp_id;
			});
		}else{
			gridData = grid5.getAllData();
			gridData.forEach(function(item){
				empIds += "," + item.emp_id;
			});
		}
		
		var useId = 0;
		var p06007 = "${p06007}";
		if(p06007 == "0"){
			useId = 0;// 统一模板
		}else if(p06007 == "1"){
			useId = trainType;// 按培训类别设置模板
		}else if(p06007 == "2"){
			useId = $("#plan_id").val();// 按培训主题(计划)设置模板
		}
		
	   	var para = {
			plan_id: $("#plan_id").val(),
			emp_ids: empIds.substring(1),
			class_name: "com.chd.hrp.hr.service.training.HrTrainExamineService",
			method_name: "generateDZBTrainEmpCertBatch",
			bean_name: "hrTrainExamineService",
			template_code: '06001',// 打印模板参数
			isPrintCount: false,//更新打印次数
			isPreview: true,//预览窗口，传绝对路径
			use_id: useId
		};
	   	
	   	officeFormPrint(para); 
	}
	
	// 职工证书记录批量设置
	function setEmpCertBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		
		var allData = grid5.getAllData();
		if(allData == null || allData.length == 0){
			$.etDialog.warn("请先查出数据。");
			return;
		}
		// 取得行数据
		var paraData = getBatchEditPara(grid5);
		
		var selectData = grid5.selectGet();
		
		if(selectData.length != 0){
			setEmpCertBatchExe(paraData);
		}else{
			$.etDialog.confirm('没有选择行，将修改全部数据，确定执行吗？', function(index, el){
				setEmpCertBatchExe(paraData);
				$.etDialog.close(index);
			});
		}
	}
	// 职工证书记录批量设置执行
	function setEmpCertBatchExe(para){
		$(".flag-checkbox").prop("checked", false);
		editIsGive.clearItem();
		
		$.etDialog.open({
			title: '批量设置',
			type: 1,
			content: $("#emp-cert-set-batch-div"),
			width: 400,
			height: 400,
			btn: ['确定', '取消'],
			btn1: function(index, el){
				var flag = false;
				// 职工证书是否发放
				if($("#edit_is_give_checkbox").prop("checked")){
					if(editIsGive.getValue() == ""){
						$.etDialog.warn("是否发放请选择值.");
						return;
					}else{
						para["is_give"] = editIsGive.getValue();
						flag = true;
					}
				}
				
				// 执行更新
				if(flag){
					ajaxPostData({
						url: 'updateTrainEmpCertBatch.do?isCheck=false',
						data: para,
						success: function(){
							$.etDialog.close(index);
							queryTrainEmpCertExecute();
						}
					});
				}else{
					$.etDialog.warn("没有选择要修改的项.");
				}
			}
		});
	}
	
	// 电子证书打印
	function dzzsPrint(ele){
		var certRowI = ele.getAttribute("row-index");
		var certRowD = grid5.getAllData()[certRowI];
		
		var useId = 0;
		var p06007 = "${p06007}";// 系统参数
		if(p06007 == "0"){
			useId = 0;// 统一模板
		}else if(p06007 == "1"){
			useId = trainType;// 按培训类别设置模板
		}else if(p06007 == "2"){
			useId = $("#plan_id").val();// 按培训主题(计划)设置模板
		}
	   	var para = {
			class_name: "com.chd.hrp.hr.service.training.HrTrainExamineService",
			method_name: "generateDZBTrainEmpCert",
			bean_name: "hrTrainExamineService",
			emp_ids: certRowD.emp_id,
			plan_id: certRowD.plan_id,
			template_code: '06001',// 打印模板参数
			isPrintCount: false,//更新打印次数
			isPreview: true,//预览窗口，传绝对路径
			use_id: useId
		};
	   	
	   	officeFormPrint(para); 
	}
	
	// 批量下载
	function downloadCertBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		
		$.ajax({
			type: "post",
			url: 'checkFileIsExists.do?isCheck=false',
			data: {
				plan_id: $("#plan_id").val(),
			},
			dataType: "json",
			async: true,
			success: function (res) {
				if(res.state == "true"){
					if(res.warn){
						$.etDialog.warn(res.warn);
					}
					location.href = 'downloadCertBatch.do?isCheck=false&plan_id=' + $("#plan_id").val();
				}else{
					$.etDialog.error(res.error);
				}
			},
			error: function (res){
				$.etDialog.error("操作失败.");
			}
		});
	}
	
	// 上传职工培训证书
	function certUpload(rowData){
		if(!rowData.plan_id){
			$.etDialog.warn("请先职工证书.");
			return;
		}
		initFileWindows();
		$.etDialog.open({
			title: '上传',
			type: 1,
			content: $("#file-upload-div"),
			width: 340,
			height: 340,
			btn: ['上传', '取消'],
			btn1: function(index, el){
				certUploadExe(rowData, index);
			},
			btn2: function(index, el){
				file.setValue("");
			}
		});
	}
	// 上传职工培训证书执行
	function certUploadExe(rowData, index){
		var fileForm = new FormData();
		fileForm.append("plan_id", rowData.plan_id);
		fileForm.append("emp_id", rowData.emp_id);
		fileForm.append("emp_code", rowData.emp_code);
		fileForm.append("cert_path", rowData.cert_path);
		fileForm.append("CERT_FILE", file.getValue());
		ajaxPostFormData({
			url: 'uploadTrainEmpCert.do?isCheck=false',
			data: fileForm,
			dataType: 'json',
			success: function(){
				queryTrainEmpCertExecute();
				$.etDialog.close(index);
				file.setValue("");
			}
		});
	}
	
	// 批量上传职工培训证书
	function uploadCertBatch(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn("请先选择培训计划.");
			return;
		}
		initMultipleFileWindows();
		$.etDialog.open({
			title: '批量上传',
			type: 1,
			content: $("#file-upload-div2"),
			width: 500,
			height: 400,
			btn: ['上传', '取消'],
			btn1: function(index, el){
				uploadCertBatchExe(index);
			},
			btn2: function(index, el){
				fileMultiple.setValues(['']);
			}
		});
	}
	// 批量上传职工培训证书执行
	function uploadCertBatchExe(index){
		if(grid5.getAllData() == null){
			$.etDialog.warn("请先查出数据.");
			return;
		}
		
		var fileForm = new FormData();
		fileForm.append("plan_id", $("#plan_id").val());
		var files = fileMultiple.getValues();
		
		$(files).each(function(i){
			fileForm.append("file" + i, this);
		});
		fileForm.append("fileNum", files.length);
		
		ajaxPostFormData({
			url: 'uploadEmpCertBatch.do?isCheck=false',
			data: fileForm,
			dataType: 'json',
			success: function(){
				queryTrainEmpCertExecute();
				$.etDialog.close(index);
				fileMultiple.setValues(['']);
			}
		});
	}
	
	// 是否发放单击事件
	function clickGiveChecked(checkedEle){
		var certRowI = checkedEle.getAttribute("row_index");
		var certRowD = grid5.getAllData()[certRowI];
		if(checkedEle.checked == true){
			certRowD.is_give = 1;
		}else{
			certRowD.is_give = 0;
		}
	}
	// 培训证书end***************************************
	
	// 考试安排start****************************************
	// 保存考试安排
	function saveExam(){
		if($("#plan_id").val() == ""){
			$.etDialog.warn('请先选择培训计划.');
			return;
		}
		var formValidate = $.etValidate({
			items: [
				{el: $("#exam_way_code1"), required: true},
				{el: $("#train_date1"), required: true},
				{el: $("#time_begin1"), required: true},
				{el: $("#time_end1"), required: true}
			]
		});
		if(!formValidate.test()){
			return;
		}
		ajaxPostData({
            url: 'saveTrainExam.do?isCheck=false',
            data: {
            	plan_id: $("#plan_id").val(),
            	exam_way_code: examWay1.getValue(),
            	train_date: trainDate1.getValue(),
            	time_begin: $("#time_begin1").val(),
            	time_end: $("#time_end1").val(),
            	train_site: trainSite1.getText(),
            	note: $("#note").val()
            },
            success: function (res) {
            	
            }
        });
	}
	// 考试安排end****************************************
	
	// 清空查询条件
	function clearItemQueryPara(){
		if(empId){empId.clearItem();}
		if(deptId){deptId.clearItem();}
		if(kindCode){kindCode.clearItem();}
		if(isPass){isPass.clearItem();}
		if(isGive){isGive.clearItem();}
	}
	
	<%--
	// 清空grid
	function clearGridData(){
		var grid2Data = grid2.getAllData();
		var len2 = 0;
		if(grid2Data != null){
			len2 = grid2Data.length;
		}
		var grid2IndxArr = [];
		for(var i = 0; i < len2; i++){
			grid2IndxArr.push({rowIndx: i});
		}
		grid2.deleteRows(grid2IndxArr);
		grid2.refreshView();
		
		var grid3Data = grid3.getAllData();
		var len3 = 0;
		if(grid3Data != null){
			len3 = grid3Data.length;
		}
		var grid3IndxArr = [];
		for(var i = 0; i < len3; i++){
			grid3IndxArr.push({rowIndx: i});
		}
		grid3.deleteRows(grid3IndxArr);
		grid3.refreshView();
		
		var grid4Data = grid4.getAllData();
		var len4 = 0;
		if(grid4Data != null){
			len4 = grid4Data.length;
		}
		var grid4IndxArr = [];
		for(var i = 0; i < len4; i++){
			grid4IndxArr.push({rowIndx: i});
		}
		grid4.deleteRows(grid4IndxArr);
		grid4.refreshView();
		
		var grid5Data = grid5.getAllData();
		var len5 = 0;
		if(grid5Data != null){
			len5 = grid5Data.length;
		}
		var grid5IndxArr = [];
		for(var i = 0; i < len5; i++){
			grid5IndxArr.push({rowIndx: i});
		}
		grid5.deleteRows(grid5IndxArr);
		grid5.refreshView();
	}
	--%>
	
	// 点击培训计划树生，填入所有关联的值
	function afreshRelationInput(resData){
		// 清空表单、清空表单组件
		$(".text-inp-re").val("");
		trainDate1.clearValue();
		examDate4.clearValue();
		if(examWay1){examWay1.clearItem();}
		if(examWay4){examWay4.clearItem();}
		if(trainSite1){trainSite1.clearItem();}
		if(examSite4){examSite4.clearItem();}
		certDate5.clearValue();
		
		clearItemQueryPara();
		
		// 填写考试安排tab
		if(resData.exam){
			examWay1.setValue(resData.exam.exam_way_code);
			trainDate1.setValue(resData.exam.train_date_str);
			$("#time_begin1").val(resData.exam.time_begin);
			$("#time_end1").val(resData.exam.time_end);
			trainSite1.removeOption("0");
			if(resData.exam.train_site){
				trainSite1.addOptions({id: "0", text: resData.exam.train_site});
			}else{
				trainSite1.addOptions({id: "0", text: ""});
			}
			trainSite1.setValue("0");
			$("#note").val(resData.exam.note);
		}
		
		// 填写补考安排tab
		if(resData.bukao){
			examDate4.setValue(resData.bukao.exam_date_str);
			$("#time_begin4").val(resData.bukao.time_begin);
			$("#time_end4").val(resData.bukao.time_end);
			examSite4.removeOption("0");
			if(resData.bukao.exam_site){
				examSite4.addOptions({id: "0", text: resData.bukao.exam_site});
			}else{
				examSite4.addOptions({id: "0", text: ""});
			}
			examSite4.setValue("0");
			examWay4.setValue(resData.bukao.exam_way_code);
			$("#emp_num").val(resData.bukao.emp_num);
		}
		
		// 填写培训证书tab
		if(resData.cert){
			$("#cert_name").val(resData.cert.cert_name);
			certDate5.setValue(resData.cert.cert_date_str);
			$("#cert_unit").val(resData.cert.cert_unit);
		}
	}
	
	// 初始化单文件上传组件
	function initFileWindows(){
		file = null;
		$("#file-upload").html('');
		file = $("#file-upload").etUpload({
			type: "file"
		});
	}
	
	// 初始化多文件上传组件
	function initMultipleFileWindows(){
		fileMultiple = null;
		$("#file-upload2").html("");
		fileMultiple = $("#file-upload2").etUpload({
			type: "file",
			multiple: true
		});
	}
	
	<%--
	// 验证表格列必填
	function validateRequired(grid, colNameArr, data){
		var dataArr = [];// 行数据数组
		if(data == undefined){
			dataArr = grid.getAllData();
		}else{
			data.forEach(function(item){
				dataArr.push(item.rowData ? item.rowData : item);
			});
		}
		
		var tipCol = {};
		var isPass = true;
		for(var i = 0; i < dataArr.length; i++){
			for(var j = 0; j < colNameArr.length; j++){
				if(dataArr[i][colNameArr[j]] == null 
						|| dataArr[i][colNameArr[j]] == "" 
						|| dataArr[i][colNameArr[j]] == undefined){
					tipCol[colNameArr[j]] = grid.getColumn(colNameArr[j]).title;
					isPass = false;
				}
			}
		}
		
		var msg = "";
		Object.keys(tipCol).forEach(function(attr){
			msg += "【" + tipCol[attr] + "】列不能为空<br/>";
		});
		
		if(!isPass){
			$.etDialog.warn(msg);
		}
		
		return isPass;
	}
	--%>
	
	// 取得行数据
	function getBatchEditPara(grid){
		var paraData = {
			plan_id: $("#plan_id").val()
		};
		
		var selectData = grid.selectGet();
		if(selectData.length != 0){
			var param = [];
			selectData.forEach(function(item){
				param.push(item.rowData);
			});
			paraData["paramVo"] = JSON.stringify(param);
		}else{
			var param = [];
			var allData = grid.getAllData();
			allData.forEach(function(item){
				param.push(item);
			});
			paraData["paramVo"] = JSON.stringify(param);
		}
		
		return paraData;
	}
	
	// 检查grid数据有没有指定列的重复
	function checkGridDataRepeat(grid, gridData, columns){
		var len = gridData.length;
		var count = 0;
		var flag = false;
		for(var i = 0; i < (len - 1); i++){
			for(var j = i + 1; j < len; j++){
				for(var k = 0; k < columns.length; k++){
					if(gridData[i][columns[k]] == gridData[j][columns[k]]){
						count++;
						if(count == columns.length){
							flag = true;
						}
					}
				}
				if(flag) break;
			}
			if(flag) break;
		}
		
		if(flag){
			var tipMsg = "";
			for(var n = 0; n < columns.length; n++){
				tipMsg += grid.getColumn(columns[n]).title + "列，";
			}
			$.etDialog.error(tipMsg + "不能完全重复！");
			
			return flag;
		}else{
			return false;
		}
	}
</script>
</head>
<body>
<div class="container">
	<div class="left border-right">
		<div class="search-form">
			<label>快速定位</label>
			<input type="text" class="text-input" id="search-tree">
		</div>
		<div id="mainTree"></div>
	</div>
	<div class="center">
		<div style="height: 100%;">
			<div id="etTab">
				<div title="考试安排" tabid='tab_1'>
					<div id="exam_div" >
						<input type="hidden" id="plan_id" value=""/>
						<table class="flex-item-1 table-layout" border="0">
							<tr>
								<td class="label no-empty" style="width: 80px;">考试方式：</td>
            					<td class="ipt">
									<input class="text-inp-re" name="exam_way_code1" id="exam_way_code1" type="text" style="width: 160px"/>
								</td>
							</tr>
							<tr>
								<td class="label no-empty" style="width: 80px;">考试日期：</td>
            					<td class="ipt" style="width: 360px;">
									<input class="text-inp-re" name="train_date1" id="train_date1" type="text" style="width: 160px"/>
									<span style="margin: 0 8px;">自</span>
									<input class="text-inp-re" name="time_begin1" id="time_begin1" type="text" placeholder="HH:mm" style="width: 60px"/>
									<span style="margin: 0 8px;">至</span>
									<input class="text-inp-re" name="time_end1" id="time_end1" type="text" placeholder="HH:mm" style="width: 60px"/>
								</td>
							</tr>
							<tr>
								<td class="label" style="width: 80px;">考试地点：</td>
            					<td class="ipt" >
									<input class="text-inp-re" name="train_site" id="train_site" type="text" style="width: 240px;"/>
								</td>
							</tr>
							<tr>
								<td class="label" style="width: 80px;">备注：</td>
            					<td class="ipt" >
									<input class="text-inp-re" name="note" id="note" type="text" style="width: 350px;"/>
								</td>
							</tr>
							<tr>
								<td class="label" style="width: 80px;"></td>
            					<td class="ipt">
									<button id="save-exam" style="float:left">保存</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div title="考试人员" tabid='tab_2'>
					<table class="table-layout">
                		<tr id="tab_2_query_para"></tr>
            		</table>
					<div id="examEmpGrid"></div>
				</div>
				<div title="考试成绩" tabid='tab_3'>
					<table class="table-layout">
                		<tr id="tab_3_query_para">
	        			</tr>
            		</table>
					<div id="examResultGrid"></div>
				</div>
				<div title="补考记录" tabid='tab_4'>
					<div id="bukao_div">
						<table class="flex-item-1 table-layout" border="0">
							<tr>
								<td class="label no-empty" style="width: 80px;">补考时间：</td>
            					<td class="ipt" colspan="5">
									<input class="text-inp-re" name="exam_date4" id="exam_date4" type="text" style="width: 160px"/>
									<span style="margin: 0px 8px 0px 66px;">自</span>
									<input class="text-inp-re" name="time_begin4" id="time_begin4" type="text" placeholder="HH:mm" style="width: 60px"/>
									<span style="margin: 0px 8px;">至</span>
									<input class="text-inp-re" name="time_end4" id="time_end4" type="text" placeholder="HH:mm" style="width: 60px"/>
								</td>
							</tr>
							<tr>
								<td class="label" style="width: 80px;">考试地点：</td>
            					<td class="ipt" colspan="5">
									<input class="text-inp-re" name="exam_site" id="exam_site" type="text" style="width: 240px;"/>
								</td>
							</tr>
							<tr>
								<td class="label no-empty" style="width:80px">考核方式：</td>
            					<td class="ipt" style="width: 160px;">
									<input class="text-inp-re" name="exam_way_code4" id="exam_way_code4" type="text" style="width: 160px;"/>
								</td>
								<td class="label" style="width: 80px;">参加人数：</td>
            					<td class="ipt" style="width: 160px;">
									<input class="text-inp-re" name="emp_num" id="emp_num" type="text" value="0" style="width: 160px;" disabled="disabled"/>
								</td>
								<td class="label" style="width: 0;"></td>
            					<td class="ipt">
									<button id="save-bukao" style="width:60px ;float:left">保存</button>
								</td>
							</tr>
						</table>
					</div>
					<hr style="width:100%;border:1px solid #d6e4f4;"/>
					<p style="text-decoration: underline;">补考记录</p>
					<div id="bukaoGrid"></div>
					<div id="bukao-query-para-div" style="display: none;">
						<table class="table-layout" style="margin: 0 auto">
							<tr id="tab_4_query_para_row1"></tr>
							<tr id="tab_4_query_para_row2"></tr>
							<tr></tr>
						</table>
					</div>
				</div>
				<div title="培训证书" tabid='tab_5'>
					<div id="cert_div">
						<table class="flex-item-1 table-layout" border="0">
							<tr>
								<td class="label no-empty" style="width:80px;">证书名称：</td>
            					<td class="ipt" style="width: 180px">
									<input class="text-inp-re" name="cert_name" id="cert_name" type="text" style="width: 180px"/>
								</td>
								<td class="label no-empty" style="width: 80px;">发证日期：</td>
            					<td class="ipt" style="width: 160px" colspan="3">
									<input class="text-inp-re" name="cert_date5" id="cert_date5" type="text" style="width: 160px"/>
								</td>
							</tr>
							<tr>
								<td class="label no-empty" style="width:80px;">发证机构：</td>
            					<td class="ipt" colspan="3">
									<input class="text-inp-re" name="cert_unit" id="cert_unit" type="text" style="width: 428px;"/>
								</td>
								<td class="label" style="width: 0"></td>
            					<td class="ipt">
									<button id="save-train-cert" style="width:60px ;float:left">保存</button>
								</td>
							</tr>
						</table>
					</div>
					<hr style="width:100%;border:1px solid #d6e4f4;"/>
					<div id="trainCertGrid"></div>
					<div id="train-emp-cert-query-para-div" style="display: none;">
						<table class="table-layout" style="margin: 0 auto">
							<tr id="tab_5_query_para_row1"></tr>
							<tr id="tab_5_query_para_row2"></tr>
							<tr></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="query-para-div" style="display: none;">
		<table class="table-layout" style="margin: 0 auto">
			<tr>
				<td class="label dept_id">部门：</td>
				<td class="ipt dept_id">
					<select class="text-inp-re" id="dept_id" style="width:180px;"></select>
				</td>
				<td class="label emp_id">职工：</td>
				<td class="ipt emp_id">
					<select class="text-inp-re" id="emp_id" style="width:180px;"></select>
				</td>
				<td class="label kind_code">人员类别：</td>
				<td class="ipt kind_code">
					<select class="text-inp-re" id="kind_code" style="width:180px;"></select>
				</td>
				<td class="label is_pass">是否合格：</td>
				<td class="ipt is_pass">
					<input class="text-inp-re" id="is_pass" type="text" style="width:180px;"/>
				</td>
				<td class="label is_give">是否发放：</td>
				<td class="ipt is_give">
					<input class="text-inp-re" id="is_give" type="text" style="width:180px;"/>
				</td>
				<td class="label null_col"></td>
				<td class="ipt null_col"></td>
			</tr>
		</table>
	</div>
	
	<div id="file-upload-div" style="display:none;">
		<div id="file-upload" style="width: 250px;margin: 0 auto;"></div>
	</div>
	<div id="file-upload-div2" style="display:none;">
		<p style="color:red;"><b>注：请确保文件名是职工工号！否则无法与职工关联！</b></p>
		<div id="file-upload2" style="max-width:484px;margin: 0 auto;"></div>
	</div>
	
	<div id="exam-score-div" style="display:none;">
		<table class="table-layout" style="margin: 0 auto">
			<tr id="exam-score-div-table-tr1">
				<td class="label edit-score">考试成绩：</td>
				<td class="ipt edit-score">
					<input id="edit_score" type="text" style="width:180px;"/>
					<input type="checkbox" id="edit_score_checkbox" name="edit_score_checkbox" class="flag-checkbox"/>
				</td>
			</tr>
			<tr>
				<td class="label edit_is_pass">是否合格：</td>
				<td class="ipt edit_is_pass">
					<input id="edit_is_pass" type="text" style="width:180px;"/>
					<input type="checkbox" id="edit_is_pass_checkbox" name="edit_is_pass_checkbox" class="flag-checkbox"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="emp-cert-set-batch-div" style="display:none;">
		<table class="table-layout" style="margin: 0 auto">
			<td class="label edit_is_give">是否发放：</td>
			<td class="ipt edit_is_give">
				<input id="edit_is_give" type="text" style="width:180px;"/>
				<input type="checkbox" id="edit_is_give_checkbox" name="edit_is_give_checkbox" class="flag-checkbox"/>
			</td>
		</table>
	</div>
</div>
</body>
</html>