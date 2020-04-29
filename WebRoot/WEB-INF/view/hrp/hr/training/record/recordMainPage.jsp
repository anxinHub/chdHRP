<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,tree,grid,hr,upload,pageOffice,tab,datepicker,time,validate,select,checkbox" name="plugins" />
	</jsp:include>
	<style type="text/css">
		.etUpload-multiple {
            max-width: 1000px;
        }
        .view-item {
            width: 200px;
            height: 200px; 
            line-height : 182px
        }
        .upload-photo-form {
	     	text-align: left; 
	     	padding: 0px; 
	     	box-sizing: border-box; 
        }
        .datepicker{
         	z-index: 19892022; 
        }
		.ettab-nav .ettab-tab {
		    padding: 0 19px 0 10px;
		}
		.ztree {
		    height: 100%;
		}
		.download{
			display : none !important
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
    <script>
        var tree, target,notice,signin,courseware,bk,photo_file,addbatch,etDialog,train_site,bk_site,site_source;
        var deptSoues = [];
        var empSoues = [];
        var empkind = [];
        var signEmpSoues = [];
        var noticeSouce = [];
        
        var dept_code,emp_id,kind_code,sign_case;
        var tab1GridLoadFlag,tab2GridLoadFlag,tab3GridLoadFlag,tab4GridLoadFlag,tab5GridLoadFlag,tab6GridLoadFlag;
        
        var query = function () {
            params = [];
            grid.loadData(params);
        };

        window.onload = function(){
        	train_way_code = $("#train_way_code").etSelect({url: "../../queryHosTrainWaySelect.do?isCheck=false",defaultValue: "none"});
        	ajaxPostData({url: '../../queryHosDeptSelect.do?isCheck=false&is_last=1',success: function (result) {
        		deptSoues = result;
        	}});
	    		
        	ajaxPostData({url: 'queryNoticeModeSelect.do?isCheck=false',success: function (result) {
        		noticeSouce = result;
        		notice_way = $("#notice_way").etSelect({backEndSearch: false,options: result ,defaultValue: 'none'});
        	}});
       		bk_site = $("#bk_site").etSelect({
       			url: '../../queryHrTrainSiteSelect.do?isCheck=false',
       			defaultValue: 'none',
       			create : true,
       			createOnBlur :true
       		});
        	train_site = $("#train_site").etSelect({
        		url: '../../queryHrTrainSiteSelect.do?isCheck=false',
        		defaultValue: 'none',
        		create : true,
       			createOnBlur :true
       		});
        	
        	
        	// 职工下拉
        	emp_id = $("#emp_id").etSelect({
        		url : 'queryEmpSelectForRecord.do?isCheck=false&notin=notin',
   				defaultValue: 'none'
			});
        	
        	// 部门下拉
        	dept_code = $("#dept_code").etSelect({
        		url: '../../queryHosDeptSelect.do?isCheck=false&is_last=1',
   				defaultValue: 'none',
				onChange:function(value){
					emp_id.reload({
						url: 'queryEmpSelectForRecord.do?isCheck=false&dept_code='+value
					});
				}
			});
        	
        	// 人员类列
        	kind_code = $("#kind_code").etSelect({
        		url: '../../queryHrEmpKindSelect.do?isCheck=false',
   				defaultValue: 'none'
			});
        	
        	// 签到情况
        	sign_case = $("#sign_case").etSelect({
        		defaultValue: 'none',
        		options:[
        			{id: '1', text: '正常' },
        			{id: '2', text: '迟到' },
        			{id: '3', text: '早退' },
        			{id: '4', text: '迟到+早退' }
        		]
        	});
        	sign_case2 = $("#sign_case2").etSelect({
        		defaultValue: 'none',
        		options:[
        			{id: '1', text: '正常' },
        			{id: '2', text: '迟到' },
        			{id: '3', text: '早退' },
        			{id: '4', text: '迟到+早退' }
        		]
        	}); 
        	
        } 
        
        //培训对象
       	 var initTargetGrid = function () {
                var columns = [
                     { display: '部门', name: 'dept_name',width: '19%',
                    	 editor: {
                  		     type: 'select', 
                  		     keyField: 'dept_code',
                  		     source: deptSoues,
                  			 change: function (id) {
                  				dept_code = id.dept_code;
                  				target.getColumns()[3].editor.url ='queryEmpSelectForRecord.do?isCheck=false&dept_code='+dept_code+"&plan_id="+tree.getSelectedNodes()[0].id
                  		 	 }
                  		 }	 
                     },
                     { display: '工号', name: 'emp_code', width: '15%'},
                     { display: '姓名', name: 'emp_name', width: '15%',
                    	 editor: {
                  		     type: 'select', 
                  		     keyField: 'emp_id',
                  			 change: function (id,rowdata) {
                  				ajaxPostData({
                        		 	url: 'queryEmpDetailInfo.do?isCheck=false',
                        		 	data:{
                        		 		emp_id : id.emp_id
                        		 	},
                        			success: function (result) {
                        				id.emp_code = result.emp_code;
                        				target.refreshCell(rowdata.rowIndx,'emp_code',true);
                        				id.kind_name = result.kind_name;
                        				target.refreshCell(rowdata.rowIndx,'kind_name',true);
                        				id.dept_code = result.dept_code;
                        				id.dept_name = result.dept_name;
                        				target.refreshCell(rowdata.rowIndx,'dept_name',true);
                        			},
                        		});
                  		 	 }
                  		 }	 	 
                     },
                     { display: '人员类别', name: 'kind_name', width: '15%',editable:false}
                ];
                var paramObj = {
                	editable: true,
                	height: '100%',
                	inWindowHeight: true,
                	checkbox: true,
                    columns: columns,
                    isSort :false,
                    load : function(){
                    	if (!(tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0)) {
                    		target.getColumns()[3].editor.url ='queryEmpSelectForRecord.do?isCheck=false&plan_id='+tree.getSelectedNodes()[0].id
            			}
                    },
                    toolbar: {
                        items: [
                        	{ type: 'button', label: '查询', listeners: [{ click: queryTarget }], icon: 'search' },
                        	{ type: 'button', label: '添加', listeners: [{ click: addTarget }], icon: 'add' },
                        	{ type: 'button', label: '删除', listeners: [{ click: deleteTarget }], icon: 'delete' },
                        	{ type: 'button', label: '保存', listeners: [{ click: saveTarget }], icon: 'save' },
                        	{ type: 'button', label: '导入', listeners: [{ click: importTarget }],  icon: 'import' },
                        	{ type: 'button', label: '打印', listeners: [{ click: printTarget }], icon: 'print' },
                        	{ type: 'button', label: '批量添加', listeners: [{ click: addbatchs }], icon: 'add' }
                        ]
                    }
                };
                target = $("#target").etGrid(paramObj);
        };
        //查询培训对象
        var queryTarget = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
        		$.etDialog.warn("请先选择培训计划."); return ;
			}
            params = [
            	 { name: 'dept_code', value: dept_code.getValue() },
            	 { name: 'emp_id', value: emp_id.getValue() },
            	 { name: 'kind_code', value: kind_code.getValue() },
            	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
            ];
            target.loadData(params , 'queryTrainRecordTarget.do?isCheck=false');
        }
        //查询批量添加中的职工信息
        var queryAddTarget = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
        		$.etDialog.warn("请先选择培训计划."); return ;
			}
            params = [
            	 { name: 'dept_code', value: $("#dept_code4").val() },
            	 { name: 'emp_id', value: $("#emp_id4").val() },
            	 { name: 'kind_code', value: $("#kind_code4").val() },
            	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
            ];
            addbatch.loadData(params, 'queryTrainRecordTargetForAdd.do?isCheck=false');
        }
        // 打印培训对象
        var printTarget = function(){
        	if(target.getAllData() == null){
    			$.etDialog.warn("请先查询数据！");
    			return;
    		}
    		var heads = {
    		};
    		var printPara = {
    			title: " 培训对象",// 标题
    			columns: JSON.stringify(target.getPrintColumns()),
    			class_name: "com.chd.hrp.hr.service.training.HrTrainRecordService",
    			method_name: "queryTrainRecordTargetPrint",
    			bean_name: "hrTrainRecordService",
    			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
    			foots: '',//表尾需要打印的查询条件,可以为空
    		};
    		
    		$.each(target.getUrlParms(), function(i, obj){
    			printPara[obj.name] = obj.value;
    		});
    		officeGridPrint(printPara);
        }
        
        //添加培训对象
        var addTarget = function(){
        	target.addRow();
        	if (!(tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0)) {
        		target.getColumns()[3].editor.url ='queryEmpSelectForRecord.do?isCheck=false&plan_id='+tree.getSelectedNodes()[0].id
			}
        }
        // 删除培训对象
		var deleteTarget = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			}
			 var data = target.selectGet();
	         if (data.length == 0) {
	             $.etDialog.error('请选择行');
	         } else {
	             var param = [];
	             $(data).each(function () {
	            	 var rowdata = this.rowData;
	            	 if (rowdata.row_id) {
		                 rowdata.group_id = ${group_id};
		                 rowdata.hos_id = ${hos_id};
		                 rowdata.plan_id =tree.getSelectedNodes()[0].id;
		                 param.push(rowdata);
					}
	             });
	             $.etDialog.confirm('确定删除?', function () {
	                 ajaxPostData({
	                     url: 'deleteTrainRecordTarget.do?isCheck=false',
	                     data: {mapVo: JSON.stringify(param)},
	                     success: function () {target.deleteRows(data);}
	                 })
	             });
	         }
		}
        
        var AddTargetBatch = function(){
        	var param = [];
        	var data = addbatch.selectGet();
        	if (data.length == 0) {
        		return $.etDialog.error('请选择行');
			}
        	$(data).each(function () {
	             var rowdata = this.rowData;
	             var row = rowdata._rowIndx +1 ;
		             rowdata.group_id = ${group_id};
		             rowdata.hos_id = ${hos_id};
		             rowdata.plan_id =tree.getSelectedNodes()[0].id;
		             param.push(rowdata);
	         });
			 
			 ajaxPostData({
			 	 url: 'saveTrainRecordTarget.do?isCheck=false',
			 	 data: {
	            	 mapVo: JSON.stringify(param)
	             },
				 success: function (result) {
					 queryTarget();
				 },
			});
			$.etDialog.close(etDialog);
			addbatch.destroy();
        }
        
        var addbatchs = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			};
			
        	 var columns = [
                 { display: '部门', name: 'dept_name',width: 120},
                 { display: '工号', name: 'emp_code', width: 120},
                 { display: '姓名', name: 'emp_name', width: 120},
                 { display: '人员类别', name: 'kind_name', width: 120,editable:false}
            ];
            var paramObj = {
            	editable: false,
            	height: '400px',
            	inWindowHeight: true,
            	checkbox: true,
                columns: columns,
                isSort :false,
                beforeTableView: function(){
                	var all = target.getAllData();
                	var select = addbatch.getAllData();
                	$(all).each(function(){
                		var a = this;
                		$(select).each(function(){
                			if(this.emp_id == a.emp_id){
                				addbatch.deleteRow(this._rowIndx);
                			}
                		})
                	})
                },
                toolbar: {
                    items: [
                    	{ type: 'button', label: '查询', listeners: [{ click: queryAddTarget }], icon: 'search' },
                    	{ type: 'button', label: '添加', listeners: [{ click: AddTargetBatch }], icon: 'search' },
                    ]
                }
            };
            addbatch = $("#addbatch").etGrid(paramObj);
            
            dept_code4 = $("#dept_code4").etSelect({url: '../../queryHosDeptSelect.do?isCheck=false&is_last=1',defaultValue: 'none',
    			onChange:function(value){emp_id4.clearItem();emp_id4.reload({url: 'queryEmpSelectForRecord.do?isCheck=false&dept_code='+value});},
    		});
            emp_id4 = $("#emp_id4").etSelect({url : 'queryEmpSelectForRecord.do?isCheck=false&notin=notin',defaultValue: 'none'});
    	    kind_code4 = $("#kind_code4").etSelect({url: '../../queryHrEmpKindSelect.do?isCheck=false',defaultValue: 'none'});
        	
        	etDialog = $.etDialog.open({
        		title: '培训对象批量添加',
    			content: $("#targetAddbatch"), 
    			width: 800, 
    			height: '100%',
    			offset: '50px',
    			cancel: function(){
    				addbatch.destroy();
    			}
            });
        }
        //保存培训对象
		var saveTarget = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			}
			var param = [];
			var error = "";
			var data = target.getAllData();
			if(data != null && data.length != 0){
				if (!target.checkRepeat(data, ['emp_code'])) {
		            return;
		     	}
				 $(data).each(function () {
		             var rowdata = this;
		             var row = rowdata._rowIndx +1 ;
			             if(!rowdata.dept_code){error = "第"+row + "行部门不能为空";return;}
			             if(!rowdata.emp_code){error = "第"+row + "行工号不能为空";return;}
			             if(!rowdata.emp_name){error = "第"+row + "行姓名不能为空";return;}
			             if(!rowdata.kind_name){error = "第"+row + "行人员类别不能为空";return;}
			             rowdata.group_id = ${group_id};
			             rowdata.hos_id = ${hos_id};
			             rowdata.plan_id =tree.getSelectedNodes()[0].id;
			             param.push(rowdata);
		         });
				 if(error.length != 0){$.etDialog.error(error) ;return ;}
				 
				 ajaxPostData({
				 	 url: 'saveTrainRecordTarget.do?isCheck=false',
				 	 data: {
		            	 mapVo: JSON.stringify(param)
		             },
					 success: function (result) {
						 queryTarget();
					 },
				});
			}
        }
        var importTarget = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
        		$.etDialog.warn("请先选择培训计划.");return ;
			}
        	var para = {"column" : 
        		[{"name" : "dept_code","display" : "部门","width" : "200"},
        		{"name" : "emp_code","display" : "工号","width" : "200","require" : true},
        		{"name" : "emp_name","display" : "姓名","width" : "200"},
        		{"name" : "kind_code","display" : "人员类别","width" : "200"} ]
        		};
        		importSpreadView("/hrp/hr/training/record/importDate.do?isCheck=false&type=target&plan_id="+tree.getSelectedNodes()[0].id, para);

        }
        
        //培训通知
        var noticeEmpSource = [];
		var initNoticeGrid = function () {
            var columns = [
            	 { display: '日期', name: 'train_date',width: '12%',
            		 editor: {
              		     type: 'date', 
              		 }	 
            	 },
                 { display: '起止时间', name: 'time',width: '12%'},
                 { display: '地点', name: 'train_site', width: '15%'},
                 { display: '培训对象', name: 'emp_name', width: '10%',
                	 editor: {
              		     type: 'select', 
              		     keyField: 'emp_id',
              		     source: noticeEmpSource,
              		     change : function(){
              		    	refrshNoticEmp();
              		     }
              		 }	 	 
                 },
                 { display: '培训主题', name: 'train_title', width: '20%'},
                 { display: '授课人', name: 'teacher', width: '12%'},
                 { display: '座位号', name: 'seat_number', width: '10%'},
                 { display: '通知方式', name: 'way_name', width: '10%',
                	 editor: {
              		     type: 'select', 
              		     keyField: 'inform_way_code',
              		     source: noticeSouce,
              		 }	  
                 },
                 { display: '备注', name: 'note', width: '12%'}
            ];
            var paramObj = {
            	editable: true,
            	height: '100%',
            	inWindowHeight: true,
            	checkbox: true,
                columns: columns,
                isSort :false,
                load : function(){
                	 ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=signin&plan_id='+tree.getSelectedNodes()[0].id ,
    						success: function (result) {
    							signEmpSoues = result;
		                    	refrshNoticEmp();
    						}
    				});
                },
                toolbar: {
                    items: [
                    	{ type: 'button', label: '查询', listeners: [{ click: queryNotice }], icon: 'search' },
                    	{ type: 'button', label: '生成', listeners: [{ click: createNotice }], icon: 'add' },
                    	{ type: 'button', label: '删除', listeners: [{ click: deleteNotice }], icon: 'delete' },
                    	{ type: 'button', label: '保存', listeners: [{ click: saveNotice }], icon: 'save' },
                    	{ type: 'button', label: '导入', listeners: [{ click: importNotice }], icon: 'import' },
                    	{ type: 'button', label: '打印', listeners: [{ click: printNotice }], icon: 'print' },
                    	{ type: 'button', label: '批量通知设置', listeners: [{ click: setNoticeBatch }], icon: 'settings' }
                    	/* { type: 'button', label: '发送通知', listeners: [{ click: addSub }], icon: 'print' }  */
                    ]
                }
            };
            notice = $("#notice").etGrid(paramObj);
       };
       //查询培训通知
       var queryNotice = function(){
    	   if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
    		   $.etDialog.warn("请先选择培训计划.");return ;
			}
    	   params = [
          	 { name: 'dept_code', value: dept_code.getValue() },
          	 { name: 'emp_id', value: emp_id.getValue() },
          	 { name: 'kind_code', value: kind_code.getValue() },
          	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
           ];
    	   notice.loadData(params , 'queryTrainRecordNotice.do?isCheck=false');
       }
       // 打印培训通知
       var printNotice = function(){
	       	if(notice.getAllData() == null){
	   			$.etDialog.warn("请先查询数据！");
	   			return;
	   		}
	   		var heads = {
	   		};
	   		var printPara = {
	   			title: " 培训通知",// 标题
	   			columns: JSON.stringify(notice.getPrintColumns()),
	   			class_name: "com.chd.hrp.hr.service.training.HrTrainRecordService",
	   			method_name: "queryTrainRecordNoticePrint",
	   			bean_name: "hrTrainRecordService",
	   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	   			foots: '',//表尾需要打印的查询条件,可以为空
	   		};
	   		
	   		$.each(target.getUrlParms(), function(i, obj){
	   			printPara[obj.name] = obj.value;
	   		});
	   		officeGridPrint(printPara);
       }
       //生成培训通知
       var createNotice = function(){
    	   if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
    		   $.etDialog.warn("请先选择培训计划.");return ;
			} 
    	   params = [
          	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
           ];
    	   notice.loadData(params , 'queryTrainRecordCreateNotice.do?isCheck=false');
       }
       // 删除培训通知
		var deleteNotice = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			}
			 var data = notice.selectGet();
	         if (data.length == 0) {
	             $.etDialog.error('请选择行');
	         } else {
	             var param = [];
	             $(data).each(function () {
	            	 var rowdata = this.rowData;
	            	 if (rowdata.row_id) {
		                 rowdata.group_id = ${group_id};
		                 rowdata.hos_id = ${hos_id};
		                 rowdata.copy_code = '${copy_code}';
		                 rowdata.plan_id = tree.getSelectedNodes()[0].id;
		                 param.push(rowdata);
					}
	             });
	             $.etDialog.confirm('确定删除?', function () {
	                 ajaxPostData({
	                     url: 'deleteTrainRecordNotice.do?isCheck=false',
	                     data: {mapVo: JSON.stringify(param)},
	                     success: function () {
	                    	 notice.deleteRows(data);
	                    	 ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=signin&plan_id='+tree.getSelectedNodes()[0].id ,
	           						success: function (result) {
	           							signEmpSoues = result;
				                    	refrshNoticEmp();
	           						}
	           					});
	                     }
	                 })
	             });
	         }
		}
       //保存培训通知
		var saveNotice = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			}
			var param = [];
			var error = "";
			var data = notice.getAllData();
			if(data != null && data.length != 0){
				 $(data).each(function () {
		             var rowdata = this;
		             var row = rowdata._rowIndx +1 ;
			             if(!rowdata.train_date){error = "第"+row + "行日期不能为空";return;}
			             if(!rowdata.time){error = "第"+row + "行起止时间不能为空";return;}
			             if(!rowdata.emp_id){error = "第"+row + "行培训对象不能为空";return;}
			             if(!rowdata.train_site){error = "第"+row + "行地点不能为空";return;}
			             if(!rowdata.train_title){error = "第"+row + "行培训主题不能为空";return;}
			             if(!rowdata.teacher){error = "第"+row + "行授课人不能为空";return;}
			             if(!rowdata.seat_number){error = "第"+row + "行座位号不能为空";return;}
			             rowdata.group_id = ${group_id};
			             rowdata.hos_id = ${hos_id};
			             rowdata.plan_id = tree.getSelectedNodes()[0].id;
			             param.push(rowdata);
		         });
				 if(error.length != 0){$.etDialog.error(error) ;return ;}
				 
				 ajaxPostData({
				 	 url: 'saveTrainRecordNotice.do?isCheck=false',
				 	 data: {
		            	 mapVo: JSON.stringify(param)
		             },
					 success: function (result) {
						 queryNotice();
					 },
				});
			}
       }
		var importNotice = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
        		$.etDialog.warn("请先选择培训计划.");return ;
			}
        	var para = {
        			"column" : [ 
        				{"name" : "train_date","display" : "日期","width" : "200","require" : true},
        				{"name" : "time_begin","display" : "开始时间","width" : "200","require" : true},
        				{"name" : "time_end","display" : "结束时间","width" : "200","require" : true},
        				{"name" : "train_site","display" : "地点","width" : "200","require" : true},
        				{"name" : "emp_name","display" : "培训对象","width" : "200","require" : true},
        				{"name" : "train_title","display" : "培训主题","width" : "200","require" : true},
        				{"name" : "teacher","display" : "授课人","width" : "200","require" : true},
        				{"name" : "seat_number","display" : "座位号","width" : "200"},
        				{"name" : "inform_way_code","display" : "通知方式","width" : "200","require" : true},
        				{"name" : "note","display" : "备注","width" : "200"},
        			]

        		};
        		importSpreadView("/hrp/hr/training/record/importDate.do?isCheck=false&type=notice&plan_id="+tree.getSelectedNodes()[0].id, para);
        }
		//刷新表格中的下拉框人员信息呢
       var refrshNoticEmp = function(){
    	   var noticeEmpSource = Array.from(signEmpSoues);
    	   
       		var data = notice.getAllData();
			if(data != null && data.length != 0){
				 $(data).each(function () {
		             var rowdata = this;
		             $(signEmpSoues).each(function(){
		            	 if (rowdata.emp_id == this.id) {
		            		 noticeEmpSource.splice(noticeEmpSource.indexOf(this),1);
						}
		             })
		         });
			}
       	  notice.getColumns()[4].editor.source = noticeEmpSource;
       }
		//批量设置通知方式
		var setNoticeBatch = function(){
			var data = notice.selectGet();
        	if (data.length == 0) {
	             $.etDialog.error('请选择数据');return;
        	}
        	$.etDialog.open({
        		title: '批量设置通知方式',
    			content: $("#noticebatch"), 
    			width: 300, 
    			height:260,
    			offset: '50px',
    			btn: ['确定', '取消'],
    			btn1: function (index, el) {
    				var inform_way_code = notice_way.getValue();
    				var inform_way_name = notice_way.getText();
    				 var param = [];
   					if(data != null && data.length != 0){
   						 $(data).each(function () {
   				             var rowdata = this.rowData;
					         rowdata.group_id = ${group_id};
					         rowdata.hos_id = ${hos_id};
					         rowdata.plan_id = tree.getSelectedNodes()[0].id;
					         rowdata.inform_way_code = inform_way_code;
		    		         rowdata.way_name = inform_way_name;
					         param.push(rowdata);
   				         });
   						 
   						 ajaxPostData({
   						 	 url: 'saveTrainRecordNotice.do?isCheck=false',
   						 	 data: {mapVo: JSON.stringify(param) },
   							 success: function (result) { queryNotice(); },
   						});
   					}
    				$.etDialog.close(index);
    			}
            });
		}
       
        //签到记录
        var signinEmpSource = [];
		var initSignInGrid = function () {
            var columns = [
          		{ display: '部门', name: 'dept_name',width: '15%',
                	 editor: {
              		     type: 'select', 
              		     keyField: 'dept_code',
              		     source: deptSoues,
              			 change: function (id) {
              				dept_code = id.dept_code;
              				ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=signin&plan_id='+tree.getSelectedNodes()[0].id +'&dept_code='+dept_code,
              						success: function (result) {
              							signEmpSoues = result;
			              				resfrshSignInEmp();
              						}
              				});
              		 	 }
              		 }	 
                 },
                 { display: '工号', name: 'emp_code', width: '12%'},
                 { display: '姓名', name: 'emp_name', width: '12%',
                	 editor: {
              		     type: 'select', 
              		     keyField: 'emp_id',
              		    source: signinEmpSource,
              			 change: function (id,rowdata) {
              				ajaxPostData({
                    		 	url: 'queryEmpDetailInfo.do?isCheck=false',
                    		 	data:{
                    		 		emp_id : id.emp_id
                    		 	},
                    			success: function (result) {
                    				id.emp_code = result.emp_code;
                    				signin.refreshCell(rowdata.rowIndx,'emp_code',true);
                    				id.kind_name = result.kind_name;
                    				signin.refreshCell(rowdata.rowIndx,'kind_name',true);
                    				id.dept_code = result.dept_code;
                    				id.dept_name = result.dept_name;
                    				signin.refreshCell(rowdata.rowIndx,'dept_name',true);
                    				
                    				resfrshSignInEmp();
                    			},
                    		});
              		 	 }
              		 }	 	 
                 },
                 { display: '人员类别', name: 'kind_name', width: '12%',editable:false},
                 { display: '签到时间', name: 'in_time', width: '12%'},
                 { display: '签退时间', name: 'out_time', width: '12%'},
                 { display: '签到情况', name: 'sign', width: '10%',
                	 editor: {
              		     type: 'select', 
              		     keyField: 'sign_case',
              		     source: [
                 			{id: '1', label: '正常' },
                			{id: '2', label: '迟到' },
                			{id: '3', label: '早退' },
                			{id: '4', label: '迟到+早退' }
                		]
              		 }
                 },
            ];
            var paramObj = {
            	editable: true,
            	height: '100%',
            	inWindowHeight: true,
            	checkbox: true,
                columns: columns,
                isSort :false,
                load : function(){
                	ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=signin&plan_id='+tree.getSelectedNodes()[0].id ,
  						success: function (result) {
  							signEmpSoues = result;
              				resfrshSignInEmp();
  						}
  					});
                },
                cellClick : function(event, ui){
                	if (ui.dataIndx == 'in_time' | ui.dataIndx == 'out_time') {
               			var intime = ui.rowData.in_time;
               			if (intime) {
                			train_date.setValue(intime.split(" ")[0]);
                			$("#sign_time").val(intime.split(" ")[1])
						}
						var outtime = ui.rowData.out_time;
						if (outtime) {
	                		train_date.setValue(outtime.split(" ")[0]);
    	            		$("#sign_time2").val(outtime.split(" ")[1]);
						}
							
                		if (ui.dataIndx == 'in_time') {
	                		$("#sign_time").removeAttr("hidden");
	                		$("#sign_time2").attr("hidden","hidden");
						}
						if (ui.dataIndx == 'out_time') {
	                		$("#sign_time2").removeAttr("hidden");
	                		$("#sign_time").attr("hidden","hidden");
						}
	                	$.etDialog.open({
	                		title: '时间选择',
	            			content: $("#itemDiv"), 
	            			width: 300, 
	            			height: 180,
	            			offset: '140px',
	            			btn: ['确定', '取消'],
	            			btn1: function (index, el) {
								var data = $("#sign_date").val();
								if (ui.dataIndx == 'in_time') {
									if (!checkTime()) {
										return;
									}
		            				var time = $("#sign_time").val();
									ui.rowData.in_time = data + " " + time;
		            				signin.refreshCell(ui.rowIndx,'in_time',false);
								}
								if (ui.dataIndx == 'out_time') {
									if (!checkTime()) {
										return;
									}
		            				var time = $("#sign_time2").val();
									ui.rowData.out_time = data + " " + time;
		            				signin.refreshCell(ui.rowIndx,'out_time',false);
								}
	            				$.etDialog.close(index);
	            			}
	                    });
					}
                },
                toolbar: {
                    items: [
                    	{ type: 'button', label: '查询', listeners: [{ click: querySignIn }], icon: 'search' },
                    	{ type: 'button', label: '生成', listeners: [{ click: createSignIn }], icon: 'disk' },
                    	{ type: 'button', label: '添加', listeners: [{ click: addSignIn }], icon: 'add' },
                    	{ type: 'button', label: '删除', listeners: [{ click: deleteSignIn }], icon: 'delete' },
                    	{ type: 'button', label: '保存', listeners: [{ click: saveSignIn }], icon: 'save' },
                    	{ type: 'button', label: '导入', listeners: [{ click: importSignIn }], icon: 'import' },
                    	{ type: 'button', label: '打印', listeners: [{ click: printSignIn }], icon: 'print' },
                    	{ type: 'button', label: '批量设置', listeners: [{ click: timeSet }], icon: 'settings' },
                    ]
                }
            };
            signin = $("#signin").etGrid(paramObj);
       };
        var checkTime = function(){
        	var hour = sign_time2.config.dateTime.hours - sign_time.config.dateTime.hours;
			var min = sign_time2.config.dateTime.minutes - sign_time.config.dateTime.minutes;
			if (hour < 0 && sign_time2.config.dateTime.hours != 0) {
				$.etDialog.warn('签退时间不能小于签到时间!');
				return false;
			} else if(hour == 0 && min <= 0){
				$.etDialog.warn('签退时间不能小于签到时间!');
				return false;
			}
			return true;
        }
        //批量时间设置
        var timeSet = function(){
        	$("#sign_time3").val("");
        	$("#sign_time4").val("");
        	sign_case2.clearItem();
        	ck1.setUncheck();
        	ck2.setUncheck();
        	ck3.setUncheck();
        	var data = signin.selectGet();
        	if (data.length == 0) {
        		 $.etDialog.error('请选择数据');return;
        	}
        	$.etDialog.open({
        		title: '批量时间设置',
    			content: $("#itemDiv2"), 
    			width: 340, 
    			height: 280,
    			offset: '140px',
    			btn: ['确定', '取消'],
    			btn1: function (index, el) {
    				var in_time,out_time;
    				 if (ck2.status == "checked" || ck3.status == "checked") {
   						 if (ck2.status == "checked") {
	   							var time1 = $("#sign_time3").val();
	   							if (time1) {
	   								var sign_date = $("#sign_date2").val();
	   								if (sign_date) {
		   								 in_time = sign_date + " " + time1;
									}else{
										$.etDialog.error('请选择签到日期');return;
									}
	   							}else{
	   								in_time = "";
	   							}
   						 }
						if (ck3.status == "checked") {
   							var time2 = $("#sign_time4").val();
   							if (time2) {
   								var sign_date = $("#sign_date2").val();
   								if (sign_date) {
   									out_time = sign_date + " " + time2;
								}else{
									$.etDialog.error('请选择签退日期');return;
								}
   							}else{
   								out_time = "";
   							}
   						}
    				 }
    				 var sign_case = sign_case2.getValue();
 					 var sign = sign_case2.getText();
    				 if (ck1.status == "checked") {
	   		            if (!sign_case) {
	   		            	$.etDialog.error('请选择签到情况');return;
						}
		             }
    				 
    				var param = [];
	   				 $(data).each(function () {
	   		             var rowdata = this.rowData;
	   		          	 rowdata.group_id = ${group_id};
			             rowdata.hos_id = ${hos_id};
			             rowdata.plan_id =tree.getSelectedNodes()[0].id;
   		            	 if (ck2.status == "checked") {
		   		             rowdata.in_time = in_time;
						}
   		            	 if (ck3.status == "checked") {
	   		             	rowdata.out_time = out_time;
   		            	 }
	   		             if (sign_case) {
	   		            	if (ck1.status == "checked") {
			   		             rowdata.sign_case = sign_case;
			   		             rowdata.sign = sign;
	   		            	}
						}
			             param.push(rowdata);
		   		     });
	   				 if (ck1.status == "checked" || ck2.status == "checked" || ck3.status == "checked") { 
	   					 ajaxPostData({
						 	 url: 'saveTrainRecordSignIn.do?isCheck=false',
						 	 data: {
				            	 mapVo: JSON.stringify(param)
				             },
							 success: function (result) {
								 querySignIn();
							 },
						});
					 }
	   				$.etDialog.close(index);
    			}
            });
        }
        
        var createSignIn = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
     		   $.etDialog.warn("请先选择培训计划.");return ;
 			} 
     	   params = [
           	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
            ];
     	  signin.loadData(params , 'queryTrainRecordCreateSignIn.do?isCheck=false');
        }
       //查询签到记录
       var querySignIn = function(){
    	   if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
    		   $.etDialog.warn("请先选择培训计划.");return ;
			}
    	   params = [
          	 { name: 'dept_code', value:dept_code.getValue() },
          	 { name: 'emp_id', value: emp_id.getValue() },
          	 { name: 'kind_code', value: kind_code.getValue() },
          	 { name: 'sign_case', value: sign_case.getValue() },
          	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
           ];
    	   signin.loadData(params , 'queryTrainRecordSignIn.do?isCheck=false');
       }
    // 打印签到记录
       var printSignIn = function(){
	       	if(signin.getAllData() == null){
	   			$.etDialog.warn("请先查询数据！");
	   			return;
	   		}
	   		var heads = {
	   		};
	   		var printPara = {
	   			title: " 培训通知",// 标题
	   			columns: JSON.stringify(signin.getPrintColumns()),
	   			class_name: "com.chd.hrp.hr.service.training.HrTrainRecordService",
	   			method_name: "queryTrainRecordSignInPrint",
	   			bean_name: "hrTrainRecordService",
	   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	   			foots: '',//表尾需要打印的查询条件,可以为空
	   		};
	   		
	   		$.each(target.getUrlParms(), function(i, obj){
	   			printPara[obj.name] = obj.value;
	   		});
	   		officeGridPrint(printPara);
       }
       //添加签到记录
       var addSignIn = function(){signin.addRow();}
       // 删除签到记录
		var deleteSignIn = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			}
			 var data = signin.selectGet();
	         if (data.length == 0) {
	             $.etDialog.error('请选择行');
	         } else {
	             var param = [];
	             $(data).each(function () {
	            	 var rowdata = this.rowData;
	            	 if (rowdata.row_id) {
		                 rowdata.group_id = ${group_id};
		                 rowdata.hos_id = ${hos_id};
		                 rowdata.copy_code = '${copy_code}';
		                 rowdata.plan_id =tree.getSelectedNodes()[0].id;
		                 param.push(rowdata);
					}
	             });
	             $.etDialog.confirm('确定删除?', function () {
	                 ajaxPostData({
	                     url: 'deleteTrainRecordSignIn.do?isCheck=false',
	                     data: {mapVo: JSON.stringify(param)},
	                     success: function () {
	                    	 signin.deleteRows(data);
	                    	 ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=signin&plan_id='+tree.getSelectedNodes()[0].id ,
           						success: function (result) {
           							signEmpSoues = result;
			              			resfrshSignInEmp();
           						}
           					});
	                    }
	                 })
	             });
	         }
		}
       //保存签到记录
		var saveSignIn = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			}
			var param = [];
			var error = "";
			var data = signin.getAllData();
			if(data != null && data.length != 0){
				 $(data).each(function () {
					 var rowdata = this;
		             var row = rowdata._rowIndx +1 ;
			             if(!rowdata.dept_code){error = "第"+row + "行部门不能为空";return;}
			             if(!rowdata.emp_code){error = "第"+row + "行工号不能为空";return;}
			             if(!rowdata.emp_name){error = "第"+row + "行姓名不能为空";return;}
			             if(!rowdata.kind_name){error = "第"+row + "行人员类别不能为空";return;}
			             if(!rowdata.sign_case){error = "第"+row + "行签到情况不能为空";return;}
			             rowdata.group_id = ${group_id};
			             rowdata.hos_id = ${hos_id};
			             rowdata.plan_id =tree.getSelectedNodes()[0].id;
			             param.push(rowdata);
		         });
				 if(error.length != 0){$.etDialog.error(error) ;return ;}
				 
				 ajaxPostData({
				 	 url: 'saveTrainRecordSignIn.do?isCheck=false',
				 	 data: {
		            	 mapVo: JSON.stringify(param)
		             },
					 success: function (result) {
						 querySignIn();
					 },
				});
			}
       }
		var importSignIn = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
        		$.etDialog.warn("请先选择培训计划.");return ;
			}
        	var para = {
        			"column" : [ 
        				{"name" : "dept_name","display" : "部门","width" : "200"},
        				{"name" : "emp_code","display" : "工号","width" : "200","require" : true},
        				{"name" : "emp_name","display" : "姓名","width" : "200"},
        				{"name" : "kind_name","display" : "人员类别","width" : "200"},
        				{"name" : "in_time","display" : "签到时间","width" : "200"},
        				{"name" : "out_time","display" : "签退时间","width" : "200"},
        				{"name" : "sign_case","display" : "签到情况","width" : "200"},
        			]

        		};
        		importSpreadView("/hrp/hr/training/record/importDate.do?isCheck=false&type=signin&plan_id="+tree.getSelectedNodes()[0].id, para);
        }
		//刷新表格下拉框中的人员信息
		var resfrshSignInEmp = function(){
	    	   var signinEmpSource = Array.from(signEmpSoues);
	    	   
	       		var data = signin.getAllData();
				if(data != null && data.length != 0){
					 $(data).each(function () {
			             var rowdata = this;
			             $(signEmpSoues).each(function(){
			            	 if (rowdata.emp_id == this.id) {
			            		 signinEmpSource.splice(signinEmpSource.indexOf(this),1);
							}
			             })
			         });
				}
				signin.getColumns()[3].editor.source = signinEmpSource;
	       }
       
        //课件
		var initCoursewareGrid = function () {
            var columns = [
                 { display: '授课人', name: 'teacher',width: '12%',editable:true},
                 { display: '单位', name: 'unit', width: '15%',editable:true},
                 { display: '部门', name: 'dept', width: '12%',editable:true},
                 { display: '职称', name: 'job_title', width: '12%',editable:true},
                 { display: '专业方向', name: 'direction', width: '12%',editable:true},
                 { display: '课件', name: 'file_path', width: '12%',editable:false,
                	render: function(rowdata){
                		var path = rowdata.cellData;
                		if (path) {
                			return path.substr(path.lastIndexOf('/') + 1,path.length);
						}
                	}	 
                 },
                 { display: '备注', name: 'note', width: '12%',editable:true},
                 { display: '操作', name: 'done', width: '12%',align: 'center',
                	 render:function(data) {
	               		  var path = data.rowData.file_path;
	               		  if(path=="" || !path){
	               			  return '<a class="uploadFile" rowIndex = "'+data.rowIndx+'">上传</a>';
	               		  }
	               		  var array = path.split('/');
	               		  var name = array[array.length-1];
                         return '<a class="uploadFile" rowIndex = "'+data.rowIndx+'">上传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='+path+' download='+name+'>下载</a>';
                     }	 
                 },
                 { display: '预览', name: 'view', width: '7%',editable:false,align:'center',
               	  render:function(data) {
                         return '<a class="toView" rowIndex = "'+data.rowIndx+'">预览</a>'
                     }
                 },
            ];
            var paramObj = {
            	editable: true,
            	height: '100%',
            	inWindowHeight: true,
            	checkbox: true,
                columns: columns,
                isSort :false,
                toolbar: {
                    items: [
                    	{ type: 'button', label: '查询', listeners: [{ click: queryCourseware }], icon: 'search' },
                    	{ type: 'button', label: '添加', listeners: [{ click: addCourseware }], icon: 'add' },
                    	{ type: 'button', label: '删除', listeners: [{ click: deleteCourseware }], icon: 'delete' },
                    	{ type: 'button', label: '保存', listeners: [{ click: saveCourseware }], icon: 'save' },
                    ]
                }
            };
            courseware = $("#courseware").etGrid(paramObj);
            
            $("#courseware").on("click", ".uploadFile", function(){
            	 var rowIndex = $(this).attr('rowIndex');
                 var currentRowData = courseware.getAllData()[rowIndex];
    			 uploadFile(currentRowData);
    		});
            $("#courseware").on('click','.toView',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                var currentRowData = courseware.getAllData()[rowIndex];
                toView(currentRowData);
           })

       };
       //文件预览
       function toView(data){
	       	if(!data.file_path){
	       		return;
	       	}
	       	var file_path = data.file_path;
	       //	file_path = file_path.substr(1,file_path.length);
	       	showFile(file_path);
       }

       //上传文件
       function uploadFile(data){
    	   if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
    		   $.etDialog.warn("请先选择培训计划.");return ;
			}
    	   file = null;
	   		$("#uploadFile").html('');
	   		file = $("#uploadFile").etUpload({
	   			type: "file"
	   		});
	   		$.etDialog.open({
				title: '上传',
				type: 1,
				content: $("#uploadFile"),
				width: 240,
				height: 310,
				btn: ['上传', '取消'],
				btn1: function(index, el){
					var fileForm = new FormData();
					fileForm.append("file",file.getValue());
					ajaxPostFormData({
						url: 'addFile.do?isCheck=false&plan_id='+tree.getSelectedNodes()[0].id,
						data: fileForm,
						dataType: 'json',
						success: function(result){
							data.file_path = result.url;
							courseware.refreshCell(data._rowIndx,'file_path');
							courseware.refreshCell(data._rowIndx,'done');
							courseware.refreshCell(data._rowIndx,'view');
							$.etDialog.close(index);
						}
					});
				},
				btn2: function(index, el){
					file.setValue("");
					$.etDialog.close(index);
				}
			});
       }
       
       //查询课件
       var queryCourseware = function(){
    	   if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
    		   $.etDialog.warn("请先选择培训计划.");return ;
			}
    	   params = [
          	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
           ];
    	   courseware.loadData(params , 'queryTrainRecordCourseware.do?isCheck=false');
       }
       //添加课件
       var addCourseware = function(){courseware.addRow();}
       // 删除课件
		var deleteCourseware = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			} 
			 var data = courseware.selectGet();
	         if (data.length == 0) {
	             $.etDialog.error('请选择行');
	         } else {
	             var param = [];
	             $(data).each(function () {
	            	 var rowdata = this.rowData;
	            	 if (rowdata.kj_id) {
		                 rowdata.group_id = ${group_id};
		                 rowdata.hos_id = ${hos_id};
		                 rowdata.copy_code = '${copy_code}';
		                 rowdata.plan_id =tree.getSelectedNodes()[0].id;
		                 param.push(rowdata);
	            	 }
	             });
	             $.etDialog.confirm('确定删除?', function () {
	                 ajaxPostData({
	                     url: 'deleteTrainRecordCourseware.do?isCheck=false',
	                     data: {mapVo: JSON.stringify(param)},
	                     success: function () {courseware.deleteRows(data);}
	                 })
	             });
	         }
		}
       //保存课件
		var saveCourseware = function(){
			var param = [];
			var error = "";
			var data = courseware.getAllData();
			if(data != null && data.length != 0){
				 $(data).each(function () {
		             var rowdata = this;
		             var row = rowdata._rowIndx +1 ;
		             if(!rowdata.teacher){error = "第"+row + "行授课人不能为空";return;}
		             if(!rowdata.file_path){error = "第"+row + "行文件不能为空";return;}
		             rowdata.group_id = ${group_id};
		             rowdata.hos_id = ${hos_id};
		             rowdata.plan_id =tree.getSelectedNodes()[0].id;
		             param.push(rowdata);
		         });
				 if(param.length == 0){$.etDialog.error(error) ;return ;}
				 
				 ajaxPostData({
				 	 url: 'saveTrainRecordCourseware.do?isCheck=false',
				 	 data: {
		            	 mapVo: JSON.stringify(param)
		             },
					 success: function (result) {
						 queryCourseware();
					 },
				});
			}
       }
        //课件
        var bkEmpSource = [];
		var initBKGrid = function () {
            var columns = [
            	{ display: '部门', name: 'dept_name',width: '19%',
               	 editor: {
             		     type: 'select', 
             		     keyField: 'dept_code',
             		     source: deptSoues,
             			 change: function (id) {
             				dept_code = id.dept_code;
              				ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=bk&plan_id='+tree.getSelectedNodes()[0].id +'&dept_code='+dept_code,
              						success: function (result) {
              							signEmpSoues = result;
			              				refrshbkEmp();
              						}
              				});
             		 	 }
             		 }	 
                },
                { display: '工号', name: 'emp_code', width: '15%'},
                { display: '姓名', name: 'emp_name', width: '15%',
               	 editor: {
             		     type: 'select', 
             		     keyField: 'emp_id',
             		     source: bkEmpSource,
             			 change: function (id,rowdata) {
             				ajaxPostData({
                   		 	url: 'queryEmpDetailInfo.do?isCheck=false',
                   		 	data:{
                   		 		emp_id : id.emp_id
                   		 	},
                   			success: function (result) {
                   				id.emp_code = result.emp_code;
                   				bk.refreshCell(rowdata.rowIndx,'emp_code',true);
                   				id.kind_name = result.kind_name;
                   				bk.refreshCell(rowdata.rowIndx,'kind_name',true);
                   				id.dept_name = result.dept_name;
                   				bk.refreshCell(rowdata.rowIndx,'dept_name',true);
                   			},
                   		});
             				refrshbkEmp();
             		 	 }
             		 }	 	 
                },
                { display: '人员类别', name: 'kind_name', width: '15%',editable:false}
            ];
            var paramObj = {
            	editable: true,
            	height: '100%',
            	inWindowHeight: true,
            	checkbox: true,
            	pageModel: false,
                columns: columns,
                isSort :false,
                load: function(){
                	ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=bk&plan_id='+tree.getSelectedNodes()[0].id,
  						success: function (result) {
  							signEmpSoues = result;
              				refrshbkEmp();
  						}
  					});
                	setEmpNum();
                },
                change: setEmpNum,
                toolbar: {
                    items: [
                    	{ type: 'button', label: '查询', listeners: [{ click: queryBKEmp }], icon: 'search' },
                    	{ type: 'button', label: '生成', listeners: [{ click: createBKEmp }], icon: 'disk' },
                    	{ type: 'button', label: '添加', listeners: [{ click: addBKEmp }], icon: 'add' },
                    	{ type: 'button', label: '删除', listeners: [{ click: deleteBKEmp }], icon: 'delete' },
                    	{ type: 'button', label: '保存', listeners: [{ click: saveBKEmp }], icon: 'save' },
                    	{ type: 'button', label: '打印', listeners: [{ click: printBKEmp }], icon: 'print' },
                    ]
                }
            };
            bk = $("#bk").etGrid(paramObj);
       };
       var createBKEmp = function(){
    	   if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
    		   $.etDialog.warn("请先选择培训计划.");return ;
			} 
    	   params = [
          	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
           ];
    	   bk.loadData(params , 'queryTrainRecordCreateBKEmp.do?isCheck=false');
       }
       var refrshbkEmp = function(){
    	   var bkEmpSource = Array.from(signEmpSoues);
       		var data = bk.getAllData();
			if(data != null && data.length != 0){
				 $(data).each(function () {
		             var rowdata = this;
		             $(signEmpSoues).each(function(){
		            	 if (rowdata.emp_id == this.id) {
		            		 bkEmpSource.splice(bkEmpSource.indexOf(this),1);
						}
		             })
		         });
			}
			bk.getColumns()[3].editor.source = bkEmpSource;
       }
     
       var setEmpNum = function(){
    	   var data = bk.getAllData();
    	   if (data) {
				$("#emp_num").val(data.length);
		   }else{
			   $("#emp_num").val(0);
		   }
       }
       //查询课件
       var queryBKEmp = function(){
    	   if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
    		   $.etDialog.warn("请先选择培训计划.");return ;
			} 
    	   params = [
          	 { name: 'dept_code', value: dept_code.getValue() },
          	 { name: 'emp_id', value: emp_id.getValue() },
          	 { name: 'kind_code', value: kind_code.getValue() },
          	 { name: 'plan_id', value:tree.getSelectedNodes()[0].id },
           ];
    	   bk.loadData(params , 'queryTrainRecordBKEmp.do?isCheck=false');
       }
       // 打印签到记录
       var printBKEmp = function(){
	       	if(bk.getAllData() == null){
	   			$.etDialog.warn("请先查询数据！");
	   			return;
	   		}
	   		var heads = {
	   		};
	   		var printPara = {
	   			title: " 培训通知",// 标题
	   			columns: JSON.stringify(bk.getPrintColumns()),
	   			class_name: "com.chd.hrp.hr.service.training.HrTrainRecordService",
	   			method_name: "queryTrainRecordBKEmpPrint",
	   			bean_name: "hrTrainRecordService",
	   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	   			foots: '',//表尾需要打印的查询条件,可以为空
	   		};
	   		
	   		$.each(target.getUrlParms(), function(i, obj){
	   			printPara[obj.name] = obj.value;
	   		});
	   		officeGridPrint(printPara);
       }
       //添加课件
       var addBKEmp = function(){bk.addRow();}
       // 删除课件
		var deleteBKEmp = function(){
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			} 
			 var data = bk.selectGet();
	         if (data.length == 0) {
	             $.etDialog.error('请选择行');
	         } else {
	             var param = [];
	             $(data).each(function () {
	            	 var rowdata = this.rowData;
	            	 if (rowdata.emp_id) {
		                 rowdata.group_id = ${group_id};
		                 rowdata.hos_id = ${hos_id};
		                 rowdata.plan_id =tree.getSelectedNodes()[0].id;
		                 param.push(rowdata);
					}
	             });
	             $.etDialog.confirm('确定删除?', function () {
	                 ajaxPostData({
	                     url: 'deleteTrainRecordBK.do?isCheck=false',
	                     data: {mapVo: JSON.stringify(param)},
	                     success: function () {
	                    	 bk.deleteRows(data);
	                    	 refrshbkEmp();
	                     }
	                 })
	             });
	         }
		}
       //保存课件
		var saveBKEmp = function(){
			var param = [];
			var error = "";
			var data = bk.getAllData();
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			} 
			if(data != null && data.length != 0){
				 $(data).each(function () {
					 var rowdata = this;
		             var row = rowdata._rowIndx +1 ;
		             if(!rowdata.emp_code){error = "第"+row + "行工号不能为空";return;}
		             if(!rowdata.emp_name){error = "第"+row + "行姓名不能为空";return;}
		             if(!rowdata.kind_name){error = "第"+row + "行人员类别不能为空";return;}
		             rowdata.group_id = ${group_id};
		             rowdata.hos_id = ${hos_id};
		             rowdata.plan_id =tree.getSelectedNodes()[0].id;
		             param.push(rowdata);
		         });
				 if(param.length == 0){$.etDialog.error(error) ;return ;}
				 
				 ajaxPostData({
				 	 url: 'saveTrainRecordBKEmp.do?isCheck=false',
				 	 data: {
		            	 mapVo: JSON.stringify(param)
		             },
					 success: function (result) {
						 queryBKEmp();
					 },
				});
			}
       }
        
    	function initTree(){
    		tree = $("#mainTree").etTree({
    			async: {
    				enable: true,
    				url: '../examine/getTrainPlanTreeJson.do?isCheck=false'
    			},
    			data : {
    				simpleData : {enable : true,idKey : "id",pIdKey : "pId"},
    				keep : {leaf : true},
    				key : {children : "nodes"}
    			},
    			treeNode : {open : true},
    			callback: {
    				onNodeCreated : function(event, treeId, node) {
    					tree.expandNode(node, true, false, false);
    					if (node.nodes && node.level === 0 && node.nodes.length === 0) {
    						treeObj.hideNode(node);
    					}
    				},
    				onClick : function(event, treeId, node) {
    					
    					$("#plan_id").val( node.id);
    					// grid加载标记，true:切换需要加载，false:切换不用再加载。
						tab1GridLoadFlag = true;
						tab2GridLoadFlag = true;
						tab3GridLoadFlag = true;
						tab4GridLoadFlag = true;
						tab5GridLoadFlag = true;
						tab6GridLoadFlag = true;
    					
    					if(node.level == 1){
    						cleanValue();
    						var tabid = etTab.status.active.tabid;
    						if (tabid == '0') {
    							queryClass();
							}
    						if (tabid == '1') {
    							queryTarget();
    							tab1GridLoadFlag = false;
							}
    						if (tabid == '2') {
    							queryNotice();
    							ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=inform&plan_id='+tree.getSelectedNodes()[0].id,success: function (result) {signEmpSoues = result;}});
    							tab2GridLoadFlag = false;
    						}
    						if (tabid == '3') {
    							querySignIn();
    							ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=signin&plan_id='+tree.getSelectedNodes()[0].id,success: function (result) {signEmpSoues = result;}});
    							tab3GridLoadFlag = false;
    						}
    						if (tabid == '4') {
    							queryCourseware();
    							tab4GridLoadFlag = false;
							}
    						if (tabid == '5') {
    							train_way_code.setValue(node.train_way_code);
    							querybkClass();
    							queryBKEmp();
    							ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=bk&plan_id='+tree.getSelectedNodes()[0].id,success: function (result) {signEmpSoues = result;}});
    							tab5GridLoadFlag = false;
    						}
    						if (tabid == '6') {
    							queryPhoto();
    							tab6GridLoadFlag = false;
							}
    					}
    				}
    			},
    		});
    	}
    	
        // 保存课程安排
        var saveCourse = function(){
        	formValidate = $.etValidate({
				items : [ 
					{el : $("#train_date"), required : true}, 
					{el : $("#time_begin"), required : true}, 
					{el : $("#time_end"), required : true}, 
					{el : $("#teacher"),required : true},
					{el : $("#time_begin"),required : true},
					{el : $("#time_end"),required : true}
					]
			});
			if(!formValidate.test()){return;};
			
			if($("#time_begin").val() && $("#time_end").val() 
					&& parseFloat($("#time_begin").val().replace(":", "")) > parseFloat($("#time_end").val().replace(":", ""))){
				$.etDialog.warn('开始时间不能大于结束时间!');
				return;
			}
			if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				$.etDialog.warn("请先选择培训计划.");return ;
			} 
			
			ajaxPostData({
				url : 'saveTrainRecordCourse.do?isCheck=false',
				data : {
					train_date : $("#train_date").val(),
					time_begin : $("#time_begin").val(),
					time_end : $("#time_end").val(),
					train_site : train_site.getText(),
					teacher : $("#teacher").val(),
					hours : $("#hours").val(),
					train_content : $("#train_content").val(),
					plan_id :tree.getSelectedNodes()[0].id,
					note : $("#note").val()
				},
				success : function(data) {},
				delayCallback : true
			})
        }
        // 保存课程安排
        var saveBK = function(){
        	formValidate = $.etValidate({
				items : [ 
					{el : $("#bk_time"), required : true}, 
					{el : $("#bk_begin"),required : true},
					{el : $("#bk_end"),required : true},
					{el : $("#train_way_code"),required : true}
				]
			});
			if(!formValidate.test()){return;};
			
			if($("#bk_begin").val() && $("#bk_end").val() 
					&& parseFloat($("#bk_begin").val().replace(":", "")) > parseFloat($("#bk_end").val().replace(":", ""))){
				$.etDialog.warn('开始时间不能大于结束时间!');
				return;
			}
			
			ajaxPostData({
				url : 'saveTrainRecordBK.do?isCheck=false',
				data : {
					train_date : $("#bk_time").val(),
					time_begin : $("#bk_begin").val(),
					time_end : $("#bk_end").val(),
					train_site : bk_site.getText(),
					train_way_code : $("#train_way_code").val(),
					emp_num : $("#emp_num").val(),
					plan_id : tree.getSelectedNodes()[0].id
				},
				success : function(data) {},
				delayCallback : true
			})
        }
        // 保存
        var savePhoto = function(){
        	if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
        		 $.etDialog.warn('请选择培训计划!'); return ;
  			} 
        	var files = photo_file.getValues();
        	var file = new FormData();
        	var i = 0;
        	var file_path = "";
        	$(files).each(function () {
	        	 if (this.type) {
		        	 file.append("file" + ++i, this);
				 }else{
					 file_path += this + ";";
				 }
     		})
		  ajaxPostFormData({  
		    	 url: 'saveTrainRecordPhoto.do?isCheck=false&plan_id='+tree.getSelectedNodes()[0].id+'&file_num=' +i+"&file_path="+file_path,
		         type: 'post',  
		         data: file,  
		         cache: false,
		         processData: false,
		         contentType: false,
		         async: false,
		    })
        }
        
        //回显查询课程
        var queryClass = function(){
        	 if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
 				return ;
 			} 
        	ajaxPostData({
				url : 'queryTrainRecordClass.do?isCheck=false',
				data : {
					plan_id :  tree.getSelectedNodes()[0].id
				},
				success : function(data) {
					train_date.setValue(data.train_date);
					$("#time_begin").val(data.time_begin);
					$("#time_end").val(data.time_end);
					train_site.clearItem();
					var D = train_site.getOptions();
					for(var d in D){
						if (D[d].text == data.train_site) {
							train_site.setValue(D[d].id);
						}
					}
					if (train_site.getItem() == "") {
						train_site.addOptions({id: "0", text: data.train_site});
						train_site.setValue(0);
					}
					$("#teacher").val(data.teacher);
					$("#hours").val(data.hours);
					$("#train_content").val(data.train_content);
					$("#note").val(data.note);
				},
				error: function(){
					train_site.clearItem();
				},
				delayCallback : true
			})
        }
      //回显查询补课
        var querybkClass = function(){
        	 if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
 				return ;
 			} 
        	ajaxPostData({
				url : 'queryTrainRecordBK.do?isCheck=false',
				data : {
					plan_id :  tree.getSelectedNodes()[0].id
				},
				success : function(data) {
					bk_time.setValue(data.train_date);
					$("#bk_begin").val(data.time_begin);
					$("#bk_end").val(data.time_end);
					bk_site.clearItem();
					var D = bk_site.getOptions();
					for(var d in D){
						if (D[d].text == data.train_site) {
							bk_site.setValue(D[d].id);
						}
					}
					if (bk_site.getItem() == "") {
						bk_site.addOptions({id: "0", text: data.train_site});
						bk_site.setValue(0);
					}
					train_way_code.setValue(data.train_way_code);
					$("#emp_num").val(data.emp_num);
				},error: function(){
					bk_site.clearItem();
				},
				delayCallback : true
			})
        }
      //回显查询图片
      var queryPhoto = function(){
    	  if (tree.getSelectedNodes().length == 0 || tree.getSelectedNodes()[0].level == 0) {
				return ;
			} 
    	  ajaxPostData({
				url : 'queryTrainRecordPhoto.do?isCheck=false',
				data : {
					plan_id :  tree.getSelectedNodes()[0].id
				},
				success : function(data) {
					var files = []
					$(data.Rows).each(function(){
						files.push(this.file_path);
					})
					photo_file.setValues(files);
				},
				delayCallback : true
			})
      }
      
      var cleanValue = function(){
    	    train_date = $("#train_date").etDatepicker({defaultDate: true});
    		$("#time_begin").val('');
			$("#time_end").val('');
			$("#train_site").val('');
			$("#teacher").val('');
			$("#hours").val('');
			$("#train_content").val('');
			$("#note").val('');
			bk_time = $("#bk_time").etDatepicker({defaultDate: true});
			$("#bk_begin").val('');
			$("#bk_end").val('');
			$("#bk_site").val('');
			$("#train_way_code").val('');
			$("#emp_num").val('');
			photo_file = null;
	    	$("#photo").html("");
			photo_file = $("#photo").etUpload({multiple:true});
      }
        
        //计算课时
        var countHours = function(){
			var hour = time_end.config.dateTime.hours - time_begin.config.dateTime.hours;
			var min = time_end.config.dateTime.minutes - time_begin.config.dateTime.minutes;
			if (hour < 0 && time_end.config.dateTime.hours != 0) {
				$.etDialog.warn('开始时间不能大于结束时间!');
				time_begin.config.dateTime.hours = time_end.config.dateTime.hours;
				return;
			} else if(hour == 0 && min < 0){
				time_begin.config.dateTime.minutes = time_end.config.dateTime.minutes - 1;
			}
        }
        var countHours2 = function(){
			var hour = bk_begin.config.dateTime.hours - bk_begin.config.dateTime.hours;
			var min = bk_end.config.dateTime.minutes - bk_end.config.dateTime.minutes;
			if (hour < 0 && time_end.config.dateTime.hours != 0) {
				$.etDialog.warn('开始时间不能大于结束时间!');
				bk_begin.config.dateTime.hours = bk_end.config.dateTime.hours;
				return;
			} else if(hour == 0 && min < 0){
				bk_begin.config.dateTime.minutes = bk_end.config.dateTime.minutes - 1;
			}
        }
        
        $(function () {
            initTree();
            initTargetGrid();
            initNoticeGrid();
            initSignInGrid();
            initCoursewareGrid();
            initBKGrid();
             
            train_date = $("#train_date").etDatepicker({defaultDate: true});
            time_begin = laydate.render({ 
            	elem: '#time_begin', 
            	type: 'time', 
            	format: 'HH:mm',
            	done: function(){
            		countHours();
            	}
            });
            time_end = laydate.render({ elem: '#time_end', type: 'time', format: 'HH:mm',
            	done: function(){
        			countHours();
        		} 
            });
            
            sign_date = $("#sign_date").etDatepicker({defaultDate: true});
            sign_date2 = $("#sign_date2").etDatepicker({defaultDate: true});
            sign_time = laydate.render({ elem: '#sign_time', type: 'time', format: 'HH:mm'});
            sign_time2 = laydate.render({ elem: '#sign_time2', type: 'time', format: 'HH:mm'});
            sign_time3 = laydate.render({ elem: '#sign_time3', type: 'time', format: 'HH:mm'});
            sign_time4 = laydate.render({ elem: '#sign_time4', type: 'time', format: 'HH:mm'});
            bk_time = $("#bk_time").etDatepicker({defaultDate: true});
            bk_begin = laydate.render({ elem: '#bk_begin', type: 'time', format: 'HH:mm' ,
            	done: function(){
            		countHours2();
            	}	
            });
            bk_end = laydate.render({ elem: '#bk_end', type: 'time', format: 'HH:mm',
            	done: function(){
            		countHours2();
            	}	
            });
            ck1 = $('#ck1').etCheck();
            ck2 = $('#ck2').etCheck();
            ck3 = $('#ck3').etCheck();
            
            photo_file = $("#photo").etUpload({multiple:true});
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
            
            etTab = $("#etTab").etTab({
       			onChange: function(item){
       				clearItemQueryPara();
					
					if (item.tabid == '1') {
       					$("#tab_1_query_para").append($(".dept_id")).append($(".emp_id")).append($(".kind_code"));
					} else if (item.tabid == '2') {
						$("#tab_2_query_para").append($(".dept_id")).append($(".emp_id")).append($(".kind_code"));
					} else if (item.tabid == '3') {
						$("#tab_3_query_para").append($(".dept_id")).append($(".emp_id")).append($(".kind_code")).append($(".sign_case"));
					}
					
					if(item.tabid != "0"){
						target.refreshView();
						notice.refreshView();
						signin.refreshView();
						courseware.refreshView();
						bk.refreshView();
						
						if ($("#plan_id").val() != "") {
							if (item.tabid == '1' && tab1GridLoadFlag){
		       					
								queryTarget();
		       					tab1GridLoadFlag = false;
		       					
		       				} else if (item.tabid == '2' && tab2GridLoadFlag){
		       					
		       					ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=inform&plan_id='+tree.getSelectedNodes()[0].id,success: function (result) {signEmpSoues = result;}});
	       						queryNotice();
	       						tab2GridLoadFlag = false;
	       						
		       				} else if(item.tabid == '3'  && tab3GridLoadFlag){
		       					
		       					ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=signin&plan_id='+tree.getSelectedNodes()[0].id,success: function (result) {signEmpSoues = result;}});
	       						querySignIn();
	       						tab3GridLoadFlag = false;
	       						
		       				} else if(item.tabid == '4' && tab4GridLoadFlag){
		       					
		       					queryCourseware();
		       					tab4GridLoadFlag
		       					tab4GridLoadFlag = false;
		       					
		       				} else if(item.tabid == '5' && tab5GridLoadFlag){
	       			            
		       					ajaxPostData({url: 'querySignInEmpSelect.do?isCheck=false&type=bk&plan_id='+tree.getSelectedNodes()[0].id,success: function (result) {signEmpSoues = result;}});
	       						querybkClass();
	    						queryBKEmp();
	    						tab5GridLoadFlag = false;
	    						
		       				} else if(item.tabid == '6' && tab6GridLoadFlag){
	       						queryPhoto();
	       						tab6GridLoadFlag = false;
		       				}
							
						}
					}
       			}
       		});
            
         // 清空查询条件
        	function clearItemQueryPara(){
        		if(emp_id){emp_id.clearItem();}
        		if(dept_code){dept_code.clearItem();}
        		if(kind_code){kind_code.clearItem();}
        		if(sign_case){sign_case.clearItem();}
        	}
            
            //保存培训课程
            $("#saveCourse").on("click", function () {
            	saveCourse();
    		})
            $("#saveBK").on("click", function () {
            	saveBK();
    		})
            $("#savePhoto").on("click", function () {
            	savePhoto();
    		})
    		
    		//$("#etTab").css("width", $(".container").width() - 220 );
    		$(".upload-photo-form").css("width", $(".container").width() - 230 );
        })
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text">
            </div>
            <div id="mainTree"></div>
        </div>
        
        <div class="center" style="padding: 0">
			<div style="height: 100%;">
				<div id="etTab">
					<input type="hidden" id="plan_id" value=""/>
			  		<div title="课程安排" tabid="0">
					 	<table class="table-layout">
							<tr>
								<td class="label no-empty" style="width: 100px;">培训时间：</td>
								<td class="ipt" colspan="8">
									<input id="train_date" type="text" style="margin-right: 15px"/>
									自<input id="time_begin" type="text" placeholder="HH:mm"  style="width: 100px;margin-right: 5px;margin-left: 5px"/>
									至<input id="time_end" type="text" placeholder="HH:mm"  style="width: 100px;margin-left: 5px">
								</td>
							</tr>
							<tr>
								<td class="label" style="width: 100px;">培训地点：</td>
								<td class="ipt"><input id="train_site" type="text" style="width: 180px"/></td>
							</tr>
							<tr>
								<td class="label no-empty" style="width: 100px;">授课人：</td>
								<td class="ipt"><input id="teacher" type="text"/></td>
								<td class="label no-empty" style="width: 100px;">课时：</td>
								<td class="ipt"><input id="hours" type="text" /></td>
								<td></td><td></td><td></td><td></td>
							</tr>
							<tr>
								<td class="label" style="width: 100px;">培训内容：</td>
								<td class="ipt" colspan="5"><textarea id="train_content" type="text" style="width: 92.5%; height: 200px; resize: none;"></textarea></td>
							</tr>
							<tr>
								<td class="label" style="width: 100px;">备注：</td>
								<td class="ipt" colspan="5"><input id="note" type="text" style="width: 92.5%;" /></td>
							</tr>
							<tr>
								<td class="label" style="width: 100px;"></td>
								<td class="ipt" ><button id="saveCourse">保存</button></td>
							</tr>
						</table>
			  		</div>
			  		<div title="培训对象" tabid='1'>
					  	 <table class="table-layout">
							<tr id="tab_1_query_para"></tr>
						</table>
				 		<div id="target"></div>
			  		</div>
			  		<div title="培训通知" tabid='2'>
					  	<table class="table-layout">
							<tr id="tab_2_query_para"></tr>
					 	</table>
			    		<div id="notice"></div>
			  		</div>
			  		<div title="签到记录" tabid='3'>
					  	<table class="table-layout">
							<tr id="tab_3_query_para"></tr>
						 </table>
			    		<div id="signin"></div>
			  		</div>
			  		<div title="课件" tabid='4'>
			    		<div id="courseware"></div>
			  		</div>
			  		<div title="补课记录" tabid='5'>
					  	<table class="table-layout">
							<tr>
								<td class="label no-empty" style="width: 100px;">补课时间：</td>
								<td class="ipt" colspan="8">
									<input id="bk_time" type="text" style="margin-right: 15px"/>
									自<input id="bk_begin" type="text" placeholder="HH:mm"  style="width: 100px;margin-right: 5px;margin-left: 5px"/>
									至<input id="bk_end" type="text" placeholder="HH:mm"  style="width: 100px;margin-left: 5px">
								</td>
							</tr>
							<tr>
								<td class="label" style="width: 100px;">补课地点：</td>
								<td class="ipt"><input id="bk_site" type="text" style="width: 180px"/></td>
							</tr>
							<tr>
								<td class="label" style="width: 100px;">培训方式：</td>
								<td class="ipt"><input id="train_way_code" type="text" style="width: 180px"/></td>
								<td class="label" style="width: 100px;">参加人数：</td>
								<td class="ipt"><input id="emp_num" type="text" disabled="disabled" style="background-color: #EAEAEA"/></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="label" style="width: 100px;"></td>
								<td class="ipt" ><button id="saveBK">保存</button></td>
							</tr>
					 	</table>
			    		<div id="bk"></div>
			  		</div>
			  		<div title="上课照片" tabid='6' >
				    	<table class="table-layout">
							<tr>
								<td class="label" style="width: 100px;"></td>
								<td class="ipt" ><button id="savePhoto">保存</button></td>
							</tr>
						</table>
				 		<div class="upload-photo-form">
							<div id="photo"></div>
						</div>
			  		</div>
			  		
			  		<div id="query-para-div" style="display: none;">
						<table class="table-layout" style="margin: 0 auto">
							<tr>
								<td class="label dept_id" style="width: 100px;">部门：</td>
								<td class="ipt dept_id">
									<select class="text-inp-re" id="dept_code" style="width: 180px"></select>
								</td>
								<td class="label emp_id" style="width: 100px;">职工：</td>
								<td class="ipt emp_id">
									<select class="text-inp-re" id="emp_id" style="width: 180px"></select>
								</td>
								<td class="label kind_code" style="width: 100px;">人员类别：</td>
								<td class="ipt kind_code">
									<select id="kind_code" style="width: 180px"></select>
								</td>
								<td class="label sign_case" style="width: 120px;">签到情况：</td>
								<td class="ipt sign_case">
									<select id="sign_case"  style="width: 160px"></select>
								</td>
							</tr>
						</table>
					</div>
				</div>
    		</div>
		</div>
	</div>
		
    
    <div id="itemDiv" style="display: none">
		<table class="table-layout">
			<tr>
				<td class="label" style="width: 100px;">日期：</td>
				<td class="ipt">
					<input id="sign_date" type="text" />
				</td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">时间：</td>
				<td class="ipt">
					<input id="sign_time" type="text" placeholder="HH:mm"  style="width: 100px;"/>
					<input id="sign_time2" type="text" placeholder="HH:mm"  style="width: 100px;"/>
				</td>
			</tr>
		</table>
	</div>
	
	 <div id="itemDiv2" style="display: none">
		<table class="table-layout">
			<tr>
				<td class="label" style="width: 120px;">签到情况：</td>
				<td class="ipt"><input id="sign_case2" type="text" style="width: 180px"/>&nbsp;&nbsp;<input type="checkbox" id="ck1" ></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">签到\退日期：</td>
				<td class="ipt"><input id="sign_date2" type="text" /></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">签到时间：</td>
				<td class="ipt"><input id="sign_time3" type="text" placeholder="HH:mm"  style="width: 100px;"/>&nbsp;&nbsp;<input type="checkbox" id="ck2" ></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">签退时间：</td>
				<td class="ipt"><input id="sign_time4" type="text" placeholder="HH:mm"  style="width: 100px;"/>&nbsp;&nbsp;<input type="checkbox" id="ck3"></td>
			</tr>
		</table>
	</div>
	
	<div id="targetAddbatch">
		<table class="table-layout">
			<tr>
				<td class="label" style="width: 100px;">部门：</td>
				<td class="ipt"><input id="dept_code4" type="text" style="width: 180px"/></td>
				<td class="label" style="width: 100px;">职工：</td>
				<td class="ipt"><input id="emp_id4" type="text" style="width: 180px"/></td>
				<td class="label" style="width: 100px;">人员类别：</td>
				<td class="ipt"><input id="kind_code4" type="text" style="width: 180px"/></td>
			</tr>
	 	</table>
   		<div id="addbatch"></div>
	</div>
	
	<div id="noticebatch">
		<table class="table-layout">
			<tr>
				<td class="label" style="width: 100px;">通知方式：</td>
				<td class="ipt"><input id="notice_way" type="text" style="width: 180px"/></td>
			</tr>
	 	</table>
	</div>
	<div id="uploadFile"></div>
</body>

</html>