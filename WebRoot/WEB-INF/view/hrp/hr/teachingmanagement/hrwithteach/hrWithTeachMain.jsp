<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,dialog,grid,select,datepicker,validate,pageOffice" name="plugins" />
        </jsp:include>
        <script>
            var grid, record_select, profession_select, dept_select,year;
            var initGrid = function () {
                var columns = [
                    { display: '年度', name: 'year', width: 100 ,editor:getRecentYearForSelect(),
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
                        relyOn:[{key:'year',field:'year'}],
                    	 editor: {
                             type: 'grid',
                             columns: [
                                 { display: '工号', name: 'emp_id', width: 120 ,hidden:true },
                                 { display: '工号', name: 'emp_code', width: 120 },
                                 { display: '带教老师', name: 'emp_name', width: 120 },
                                 /* { display: '学历', name: 'field_col_name', width: 120 },
                                 { display: '专业', name: 'professional_name', width: 120 }, */
                             ],
                             width: '700px',
                             height: '205px',
                             dataModel: {
                                 url: 'queryComboBoxHrWithTeach.do?isCheck=false',
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
                    { display: '带教老师', name: 'emp_name', width: 100 ,editable: false},
                    /* { display: '学历', name: 'field_col_name', width: 100,editable: false },
                    { display: '专业', name: 'professional_name', width: 100 ,editable: false}, */
                    { display: '轮转科室id', name: 'dept_id', width: 100 ,hidden:true},
                    { display: '轮转科室', name: 'dept_name', width: 100 ,
                    	 editor: {
                             type: 'select',
                             keyField: 'dept_id',
                             url: 'queryDeptComboBoxHrWithTeach.do?isCheck=false',
                             change: function (rowData, cellData) {
                                 grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                             }
                         }
                    	},
                    { display: '学员姓名', name: 'student', width: 100 },
                    { display: '轮转成绩', name: 'rotate', width: 100 },
                    { display: '学员对老师测评', name: 'stud_eval', width: 100 },
                    { display: '网络审核', name: 'net_aduit', width: 100 },
                    { display: '参会情况', name: 'attend_meet', width: 100 },
                    { display: '带教成绩', name: 'with_teach_grade', width: 100, },
                    { display: '带教补贴', name: 'with_teach_money', width: 100, },
                    { display: '超声培训', name: 'ultrasound', width: 100 },
                    { display: '病例质控', name: 'case_qc', width: 100 },
                    { display: '上课补贴', name: 'teach', width: 100 },
                    { display: '备注', name: 'note', width: 100 },
                ];
                var paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    editable: function (ui) {
                        // 第一条不可编辑
                        return ui.rowIndx !== -1; 
                    },
                  /*   dataModel: {
                        url: ''
                    }, */
                    columns: columns,
                    toolbar: {
                        items: [
                            { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                            { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                            { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                            { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                            <%--{ type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' },--%>
                            { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(paramObj);
            };

            var initSelect = function () {
                /* record_select = $("#record_select").etSelect({
                    url: 'queryEducationComboBoxIcu.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });

                profession_select = $("#profession_select").etSelect({
                    url: 'queryProfessionalComboBoxIcu.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                }); */
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
                month= $("#month").etDatepicker({
                	view: "months",
            	    minView: "months",
            	    showNav: false,
            	    dateFormat: "mm",
                    onChange: query
                });
                
            };
            
              var initValidate = function () {
                formValidate = $.etValidate({
                    items: [
                        { el: $("#year"), required: true },
                    ]
                });
            }; 
            var initFrom = function () {
                year = $("#year").etDatepicker({
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    defaultDate: true, 
                    onChange:query
                    })
                    };
            var query = function () {
                var params = [
                     { name: 'emp_id', value: emp_id.getValue() },
                    { name: 'emp_name', value: $("#emp_name").val() },
                   /*  { name: 'field_col_code', value: record_select.getValue() },
                    { name: 'profession_code', value: profession_select.getValue() }, */
                    { name: 'dept_id', value: dept_select.getValue() },
                    { name: 'year', value: $("#year").val() },
                    { name: 'month', value: $("#month").val() },
                ];
                grid.loadData(params,'queryHrWithTeach.do');
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
	                title: " 住院医带教补贴打印",//标题
	                columns: JSON.stringify(grid.getPrintColumns()),//表头
	                class_name: "com.chd.hrp.hr.service.teachingmanagement.HrWithTeachService",
	                method_name: "queryWithTeachByPrint",
	                bean_name: "hrWithTeachService",
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
		    				"name" : "student",
		    				"display" : "学生姓名",
		    				"width" : "200"
		    			} ,{
		    				"name" : "rotate",
		    				"display" : "轮转成绩",
		    				"width" : "200"
		    			} ,{
		    				"name" : "stud_eval",
		    				"display" : "学员对老师评价",
		    				"width" : "200"
		    			} ,{
		    				"name" : "net_aduit",
		    				"display" : "网络审核",
		    				"width" : "200"
		    			} ,{
		    				"name" : "attend_meet",
		    				"display" : "参会情况",
		    				"width" : "200"
		    			} ,{
		    				"name" : "with_teach_grade",
		    				"display" : "带教成绩",
		    				"width" : "200"
		    			} ,{
		    				"name" : "with_teach_money",
		    				"display" : "带教补贴",
		    				"width" : "200"
		    			} ,{
		    				"name" : "ultrasound",
		    				"display" : "超声培训",
		    				"width" : "200"
		    			} ,{
		    				"name" : "case_qc",
		    				"display" : "病例质控",
		    				"width" : "200"
		    			},{
		    				"name" : "teach",
		    				"display" : "上课补贴",
		    				"width" : "200"
		    			},{
		    				"name" : "note",
		    				"display" : "备注",
		    				"width" : "200"
		    			}]

		    		};
		    		importSpreadView("/hrp/hr/teachingmanagement/importWithTeach.do?isCheck=false", para, query);

		            	
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
                              	errorMsg += '第' + (index+1) + '行年份不能为空<br/>';
                              	return false;
                              }
                              if(rowdata.month == '' || rowdata.month == undefined){
                                	errorMsg += '第' + (index+1) + '行月不能为空<br/>';
                                	return false;
                                }
                              if(rowdata.emp_id == '' || rowdata.emp_id == undefined){
                                	errorMsg += '第' + (index+1) + '行工号不能为空<br/>';
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
                    	if (!grid.checkRepeat(
                    			grid.selectGet(),
                    			['month','year','emp_id']
                    	)		
                    	) {
                           return;
                       }
               
                ajaxPostData({
                    url: 'addOrUpdateHrWithTeach.do',
                    data: {
                        paramVo: JSON.stringify(ParamVo)
                    },
                    success: function () {
                        query();
                    }
                })
            }
            var add = function () {
                grid.addRow({year:year.getValue()});
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
                            url: 'deleteHrWithTeach.do',
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

            <%--var math = function(){
                 ajaxPostData({
                    url: 'http://118.178.184.131:9090/delete?id=1',
                    data: { paramVo: JSON.stringify(selectData) },
                    success: function () {
                        query();
                    }
                })
            }--%>

            $(function () {
                initSelect();
                initGrid();
                initFrom();
                initValidate();
                query();
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
                     <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>

                </tr>
                <tr>
                    <td class="label">月份：</td>
                    <td class="ipt">
                        <input id="month" type="text" />
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