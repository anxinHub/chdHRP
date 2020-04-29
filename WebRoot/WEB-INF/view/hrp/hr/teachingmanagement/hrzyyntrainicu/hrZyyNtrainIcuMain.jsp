<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,dialog,grid,select,datepicker,pageOffice" name="plugins" />
        </jsp:include>
        <script>
            var grid, record_select, profession_select, dept_select,year,month;
            var initColumns = function () {
            	 ajaxPostData({
                     url: 'queryHrZyyNtrainIcuSet.do?isCheck=false',
                     data: { year: $("#year").val()},
                     success: function (res) {
                    	 
                    	var fullScore = res.fullScore;
                     	
                    	if(fullScore.meet_sign == null)fullScore.meet_sign='';
                     	if(fullScore.skill_train == null)fullScore.skill_train='';
                     	if(fullScore.skill_eval == null)fullScore.skill_eval='';
                     	if(fullScore.drg_eval == null)fullScore.drg_eval='';
                     	if(fullScore.dept_work == null)fullScore.dept_work='';
                     	if(fullScore.on_duty == null)fullScore.on_duty='';
                     	if(fullScore.mmeet_case == null)fullScore.mmeet_case='';
                     	if(fullScore.mini_cex == null)fullScore.mini_cex='';
                     	if(fullScore.physique == null)fullScore.physique='';
                     	if(fullScore.ade == null)fullScore.ade='';
                     	if(fullScore.online_reg == null)fullScore.online_reg='';
                     	if(fullScore.core_class_test == null)fullScore.core_class_test='';
                     	if(fullScore.core_class_eval == null)fullScore.core_class_eval='';
                     	if(fullScore.dept_eval == null)fullScore.dept_eval='';
                     	if(fullScore.accessory == null)fullScore.accessory='';
                     	if(fullScore.tot_score == null)fullScore.tot_score='';
                     	
                    	 var gridColumns = [
                    	 { display: '年度', name: 'year', width: 100, editor:getRecentYearForSelect(),
                    		 editable : function (col){
                    			    if(col.rowData){
                    				    if(col.rowData.hos_id != null && col.rowData.group_id != null){
                    					    return false;
                    				    }
                    				   return true;
                    			    }else{
                    				    return true;
                    			    }
                    		    }
                    	 },
                         { display: '月份', name: 'month', width: 100 , editor: getMonthForSelect(),
                    		 editable : function (col){
                   			    if(col.rowData){
                   				    if(col.rowData.hos_id != null && col.rowData.group_id != null){
                   					    return false;
                   				    }
                  				    return true;
                   			    }else{
                   				    return true;
                   			    }
                   		    }
                         },    
                         { display: '工号', name: 'emp_code', width: 100 ,
                         	 editor: {
                                  type: 'grid',
                                  columns: [
                                      { display: '工号', name: 'emp_id', width: 120 ,hidden:true },
                                      { display: '工号', name: 'emp_code', width: 120 },
                                      { display: '姓名', name: 'emp_name', width: 120 },
                                      { display: '学历', name: 'field_col_name', width: 120 },
                                      { display: '专业', name: 'professional_name', width: 120 },
                                  ],
                                  width: '700px',
                                  height: '205px',
                                  dataModel: {
                                      url: 'queryComboBoxIcu.do?isCheck=false',
                                  },
                              },editable : function (col){
                  			    if(col.rowData){
                				    if(col.rowData.hos_id != null && col.rowData.group_id != null){
                					    return false;
                				    }
                				   return true;
                			    }else{
                				    return true;
                			    }
                		    }
                            },
                         { display: '姓名', name: 'emp_name', width: 100 ,editable: false},
                         { display: '学历', name: 'field_col_name', width: 100,editable: false },
                         { display: '专业', name: 'professional_name', width: 100 ,editable: false},
                         { display: '轮转科室id', name: 'dept_id', width: 100 ,hidden:true},
                         { display: '轮转科室', name: 'dept_name', width: 100 ,
                         	 editor: {
                                  type: 'select',
                                  keyField: 'dept_id',
                                  url: 'queryDeptComboBox.do?isCheck=false',
                                  change: function (rowData, cellData) {
                                      grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                                  }
                              }
                         	},
                         { display: '带教老师', name: 'teacher', width: 100 },
                         { display: '会计签到(满分:'+res.fullScore.meet_sign+'分)', name: 'meet_sign', width: 100 },
                         { display: '技能培训(满分:'+res.fullScore.skill_train+'分)', name: 'skill_train', width: 100 },
                         { display: '技能考核(满分:'+res.fullScore.skill_eval+'分)', name: 'skill_eval', width: 100 },
                         { display: '病种(满分:'+res.fullScore.drg_eval+'分)', name: 'drg_eval', width: 100 },
                         { display: '科室基本(满分:'+res.fullScore.dept_work+'分)', name: 'dept_work', width: 100 },
                         { display: '值班(满分:'+res.fullScore.on_duty+'分)', name: 'on_duty', width: 100 },
                         { display: '晨会病例(满分:'+res.fullScore.mmeet_case+'分)', name: 'mmeet_case', width: 100 },
                         { display: 'mini-CEX(满分:'+res.fullScore.mini_cex+'分)', name: 'mini_cex', width: 100 },
                         { display: '体格检查(满分:'+res.fullScore.physique+'分)', name: 'physique', width: 100 },
                         { display: '出科考试(满分:'+res.fullScore.ade+'分)', name: 'ade', width: 100 },
                         { display: '网络登记(满分:'+res.fullScore.online_reg+'分)', name: 'online_reg', width: 100 },
                         { display: '核心课程前后测(满分:'+res.fullScore.core_class_test+'分)', name: 'core_class_test', width: 125 },
                         { display: '核心课程考核(满分:'+res.fullScore.core_class_eval+'分)', name: 'core_class_eval', width: 125 },
                         { display: '科室评价(满分:'+res.fullScore.dept_eval+'分)', name: 'dept_eval', width: 100 },
                         { display: '加分(满分:'+res.fullScore.accessory+'分)', name: 'accessory', width: 100 },
                         { display: '最后成绩(满分:'+res.fullScore.tot_score+'分)', name: 'tot_score', width: 100 },
                         { display: '备注', name: 'note', width: 100 },
                     ];
                     grid.option('columns', gridColumns);
                     grid.refreshView();
                     query();
                     }
                     })
            }
            
            
            var initGrid = function () {
            	
                var columns = [
                    { display: '年度', name: 'year', width: 100 ,
                    	editor:getRecentYearForSelect(),
                    	editable : function (col){
               			    if(col.rowData){
               				    if(col.rowData.hos_id != null && col.rowData.group_id != null){
               					    return false;
               				    }
               				        return true;
               			    }else{
               				    return true;
               			    }
               		    }
                    },
                    { display: '月份', name: 'month', width: 100 , 
                    	editor: getMonthForSelect(),
                    	editable : function (col){
               			    if(col.rowData){
               				    if(col.rowData.hos_id != null && col.rowData.group_id != null){
               					    return false;
               				    }
               				        return true;
               			    }else{
               				    return true;
               			    }
               		    }
                    },    
                    { display: '工号', name: 'emp_code', width: 100 ,
                    	 editor: {
                             type: 'grid',
                             columns: [
                                 { display: '工号', name: 'emp_id', width: 120 ,hidden:true },
                                 { display: '工号', name: 'emp_code', width: 120 },
                                 { display: '姓名', name: 'emp_name', width: 120 },
                                 { display: '学历', name: 'field_col_name', width: 120 },
                                 { display: '专业', name: 'professional_name', width: 120 },
                             ],
                             width: '700px',
                             height: '205px',
                             dataModel: {
                                 url: 'queryComboBoxIcu.do?isCheck=false',
                             },
                         },
                       },
                    { display: '姓名', name: 'emp_name', width: 100 ,editable: false},
                    { display: '学历', name: 'field_col_name', width: 100,editable: false },
                    { display: '专业', name: 'professional_name', width: 100 ,editable: false},
                    { display: '轮转科室id', name: 'dept_id', width: 100 ,hidden:true},
                    { display: '轮转科室', name: 'dept_name', width: 100 ,
                    	 editor: {
                             type: 'select',
                             keyField: 'dept_id',
                             url: 'queryDeptComboBox.do?isCheck=false',
                             change: function (rowData, cellData) {
                                 grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                             }
                         }
                    	},
                    { display: '带教老师', name: 'teacher', width: 100 },
                    { display: '会计签到(满分:)', name: 'meet_sign', width: 100 },
                    { display: '技能培训(满分:', name: 'skill_train', width: 100 },
                    { display: '技能考核(满分:', name: 'skill_eval', width: 100 },
                    { display: '病种(满分:', name: 'drg_eval', width: 100 },
                    { display: '科室基本(满分:', name: 'dept_work', width: 100 },
                    { display: '值班(满分:', name: 'on_duty', width: 100 },
                    { display: '晨会病例(满分:', name: 'mmeet_case', width: 100 },
                    { display: 'mini-CEX(满分:', name: 'mini_cex', width: 100 },
                    { display: '体格检查(满分:', name: 'physique', width: 100 },
                    { display: '出科考试(满分:', name: 'ade', width: 100 },
                    { display: '网络登记(满分:', name: 'online_reg', width: 100 },
                    { display: '核心课程前后测(满分:', name: 'core_class_test', width: 100 },
                    { display: '核心课程考核(满分:', name: 'core_class_eval', width: 100 },
                    { display: '科室评价(满分:', name: 'dept_eval', width: 100 },
                    { display: '加分(满分:', name: 'accessory', width: 100 },
                    { display: '最后成绩(满分:', name: 'tot_score', width: 100 },
                    { display: '备注', name: 'note', width: 100 },
                ];
                    
                /* var aaa = function (col, data) {
                	col.forEach(function (item) {
						if (item.columns) {
							aaa(item.columns, data);
							return;
            			}
                		if (item.name) {
                			var coin = data[item.name];
                			
                			if (coin) {
	            				item.display += coin + '分)'
	            			}
                		}	
            		})
                 }
                ajaxPostData({
                	url: 'queryHrZyyNtrainIcuSet.do?isCheck=false',
                	data: { year: $("#year").val() },
                	success: function (res) {
                		aaa(columns, res)
                		
                		console.log(columns)
                	}
                })
                
                return; */
                 
                var paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    editable: function (ui) {
                        // 第一条不可编辑
                        return ui.rowIndx !== -1; 
                    },
                    /* dataModel: {
                        url: ''
                    }, */
                    columns: columns,
                    toolbar: {
                        items: [
                            { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                            { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                            { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                            { type: 'button', label: '计算', listeners: [{ click: math }], icon: 'calculate' },
                            { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                            <%--{ type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' },--%>
                            { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                        ]
                    }
                
                };
                grid = $("#mainGrid").etGrid(paramObj);
                     };
            var initFrom = function () {
            	year = $("#year").etDatepicker({
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
            	month=$("#month").etDatepicker({
            		view: "months",
            	    minView: "months",
            	    showNav: false,
            	    dateFormat: "mm",
            	    onChange:query
                });

                    }
            var initSelect = function () {
                record_select = $("#record_select").etSelect({
                    url: 'queryEducationComboBoxIcu.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });

                profession_select = $("#profession_select").etSelect({
                    url: 'queryProfessionalComboBoxIcu.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });
                emp_id = $("#emp_id").etSelect({
                    url: "../queryEmpSelect.do?isCheck=false",
                    defaultValue: "none",
                    onChange: query
                });
                dept_select = $("#dept_select").etSelect({
                    url: 'queryDeptComboBoxIcu.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });
            };

            var query = function () {
                var params = [
                    { name: 'year', value: $("#year").val() },
                    { name: 'month', value: $("#month").val() },
                    { name: 'emp_id', value: emp_id.getValue() },
                    { name: 'emp_name', value: $("#name").val() },
                    { name: 'field_col_code', value: record_select.getValue() },
                    { name: 'profession_code', value: profession_select.getValue() },
                    { name: 'dept_id', value: dept_select.getValue() },
                ];
                grid.loadData(params,'queryHrZyyNtrainIcu.do');
            };
            var print = function () { 

	        	if(grid.getAllData()==null){
	        		$.etDialog.error("请先查询数据！");
	    			return;
	    		}
	        	var heads={
	            		 /* "isAuto":true,//系统默认，页眉显示页码
	            		"rows": [
	        	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
	            		]  */}; 
	        	var printPara={
	                title: " 住院医规培轮转成绩表（ICU）打印",//标题
	                columns: JSON.stringify(grid.getPrintColumns()),//表头
	                class_name: "com.chd.hrp.hr.service.teachingmanagement.HrZyyNtrainIcuService",
	                method_name: "queryZyyNtrainIcuByPrint",
	                bean_name: "hrZyyNtrainIcuService",
	                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	                foots: '',//表尾需要打印的查询条件,可以为空 
	            };
	            $.each(grid.getUrlParms(),function(i,obj){
	                printPara[obj.name]=obj.value;
	            }); 
	            //console.log(printPara);
	            officeGridPrint(printPara);   		
		            	
		    }
            <%--var putout = function () { 
            	exportGrid(grid);
            }--%>
		  var putin = function () { 
			  var para = {
		    			"column" : [ {
		    				"name" : "year",
		    				"display" : "年度",
		    				"width" : "200",
		    				"require" : true
		    			},{
		    				"name" : "month",
		    				"display" : "月份",
		    				"width" : "200",
		    				"require" : true
		    			},{
		    				"name" : "emp_id",
		    				"display" : "职工编码",
		    				"width" : "200",
		    				"require" : true
		    			},{
		    				"name" : "dept_id",
		    				"display" : "轮转科室",
		    				"width" : "200"
		    			} ,{
		    				"name" : "teacher",
		    				"display" : "带教老师",
		    				"width" : "200"
		    			} ,{
		    				"name" : "meet_sign",
		    				"display" : "会议签到",
		    				"width" : "200"
		    			} ,{
		    				"name" : "skill_train",
		    				"display" : "技能培训",
		    				"width" : "200"
		    			} ,{
		    				"name" : "skill_eval",
		    				"display" : "技能考核",
		    				"width" : "200"
		    			} ,{
		    				"name" : "drg_eval",
		    				"display" : "病种考核",
		    				"width" : "200"
		    			} ,{
		    				"name" : "dept_work",
		    				"display" : "科室基本能力",
		    				"width" : "200"
		    			} ,{
		    				"name" : "on_duty",
		    				"display" : "值班",
		    				"width" : "200"
		    			} ,{
		    				"name" : "mmeet_case",
		    				"display" : "晨会病例",
		    				"width" : "200"
		    			} ,{
		    				"name" : "mini_cex",
		    				"display" : "MINI_CEX",
		    				"width" : "200"
		    			},{
		    				"name" : "physique",
		    				"display" : "体格",
		    				"width" : "200"
		    			},{
		    				"name" : "ade",
		    				"display" : "出科考试",
		    				"width" : "200"
		    			},{
		    				"name" : "online_reg",
		    				"display" : "网络登记",
		    				"width" : "200"
		    			},{
		    				"name" : "core_class_eval",
		    				"display" : "核心课程考核",
		    				"width" : "200"
		    			},{
		    				"name" : "core_class_test",
		    				"display" : "核心课程前后测评",
		    				"width" : "200"
		    			},{
		    				"name" : "dept_eval",
		    				"display" : "科室评价",
		    				"width" : "200"
		    			},{
		    				"name" : "accessory",
		    				"display" : "加分",
		    				"width" : "200"
		    			},{
		    				"name" : "tot_score",
		    				"display" : "总得分",
		    				"width" : "200"
		    			},{
		    				"name" : "note",
		    				"display" : "备注",
		    				"width" : "200"
		    			}]

		    		};
		    		importSpreadView("/hrp/hr/teachingmanagement/importZyyNtrainIcu.do?isCheck=false", para, query);

		            	
		   }
            var save = function () {
                var ParamVo = [];
            	var errorMsg = '';//错误提示信息
                var data = grid.selectGet();
                if (data.length == 0) {
                    $.etDialog.error('请选择行');
                    return;
                } else {
                      $(data).each(function (index,obj){
                    	  var rowdata = obj.rowData;
                              if(rowdata.year == '' || rowdata.year == undefined){
                              	errorMsg += '勾选的第' + (index+1) + '行年份不能为空<br/>';
                              	return false;
                              }
                              if(rowdata.month == '' || rowdata.month == undefined){
                                	errorMsg += '勾选的第' + (index+1) + '行月不能为空<br/>';
                                	return false;
                                }
                              if(rowdata.emp_id == '' || rowdata.emp_id == undefined){
                                	errorMsg += '勾选的第' + (index+1) + '行工号不能为空<br/>';
                                	return false;
                                }
                              ParamVo.push(rowdata);
                          });
                          
                          
                          if(errorMsg != ''){
                         	 $.etDialog.error(errorMsg);
                              return;
                              }
                      
                      };
                      //验证重复数据
                      if (!grid.checkRepeat(grid.selectGet(), ['month','year','emp_id'])){
                          return;
                      }
               
                ajaxPostData({
                    url: 'addOrUpdateHrZyyNtrainIcu.do',
                    data: {
                        paramVo: JSON.stringify(ParamVo)
                    },
                    success: function () {
                        query();
                    }
                })
            }

            var add = function () {
                grid.addRow();
            };

            var remove = function () {
                var data = grid.selectGet();
                if (data.length == 0) {
                    $.etDialog.error('请选择行');
                } else {
                    var param = [];
                    $(data).each(function () {
                        var rowdata = this.rowData;
                        param.push(rowdata);
                    });

                    $.etDialog.confirm('确定删除?', function () {
                        ajaxPostData({
                            url: 'deleteHrZyyNtrainIcu.do',
                            data: {
                                paramVo: JSON.stringify(param)
                            },
                            success: function () {
                                grid.deleteRows(data);
                                tree.reAsyncChildNodes(null, 'refresh');
                            }
                        })
                    });
                }
            };

            
            var math = function () {
            	initColumns();
                var gridAllData = grid.getAllData();
                if (!gridAllData || gridAllData.length === 0) {
                    return;
                }

                ajaxPostData({
                    url: 'mathHrZyyNtrainIcu.do',
                    data: {
                        paramVo: JSON.stringify(gridAllData)
                    },
                    success: function () {
                        query();
                    }
                })
            };
           

            $(function () {
                initSelect();
                initGrid();
                initFrom();
                initColumns();
              /*   query(); */
            });



        </script>
    </head>

    <body>
        <!-- 住院医师规培轮转成绩表（病区）主页 添加页面 URL hrZyyNtrainInareaAddPage 修改页面跳转 URL hrZyyNtrainInareaUpdatePage -->
        <div class="main">
            <table class="table-layout">
                <tr>
                 <td class="label">年度：</td>
                 <td class="ipt">
                    <input id="year" type="text" />
                 </td>
                 	<td class="label">月份：</td>
                    <td class="ipt">
                        <input id="month" type="text" />
                    </td>
                      <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                </tr>
                <tr>
                    
                    <td class="label">学历：</td>
                    <td class="ipt">
                        <select name="" id="record_select" style="width:180px;"></select>
                    </td>
                                    <td class="label">专业：</td>
                    <td class="ipt">
                        <select name="" id="profession_select" style="width:180px;"></select>
                    </td>
                    <td class="label">轮转科室：</td>
                    <td class="ipt">
                        <select name="" id="dept_select" style="width:180px;"></select>
                    </td>
                </tr>
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>