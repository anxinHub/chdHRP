<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="datepicker,grid,select,dialog,hr,pageOffice" name="plugins" />
        </jsp:include>
        <script>
            var year_date, month_date, dept_select, duty_select, title_select, level_select, grid
            $(function () {
                init();
                query();
            });

            function init() {
                initSelect();
                initGrid();
                
            }

            function initSelect() {
            	year_date = $("#year_date").etDatepicker({
                    view: 'years',
                    minView: 'years',
                    dateFormat: 'yyyy'
                });
            	
                month_date = $("#month_date").etDatepicker({
            		view: "months",
            	    minView: "months",
            	    showNav: false,
            	    dateFormat: "mm",
                });

                dept_select = $("#dept_select").etSelect({
                    url: '../../queryHosDeptSelect.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });

                duty_select = $("#duty_select").etSelect({
                    url: '../../queryDuty.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });

                title_select = $("#title_select").etSelect({
                    url: '../../queryHrTitle.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });

                level_select = $("#level_select").etSelect({
                    url: '../../queryDicLevel.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });
                emp_id = $("#emp_id").etSelect({
                    url: "../../queryEmpSelect.do?isCheck=false",
                    defaultValue: "none",
                    onChange: query
                });

            }

            function initGrid() {
            	var yearEditor = getRecentYearForSelect();
            	var monthEditor = getMonthForSelect();
                var main_columns = [
                    { display: '年度', name: 'year', width: 120, editor: yearEditor,
                    	editable : function(col){
                        	if(col.rowData){
                        		if(col.rowData.state!=null){
                        			return false;
                        		}
                        		return true;
                        	}else{
                        		return true;
                        	}
                        }},
                    { display: '月份', name: 'month', width: 120, editor: monthEditor ,
                        	editable : function(col){
                            	if(col.rowData){
                            		if(col.rowData.state!=null){
                            			return false;
                            		}
                            		return true;
                            	}else{
                            		return true;
                            	}
                            }},
                    { display: '职工工号', name: 'emp_code', width: 120, editor: {
                        type: 'grid',
                        columns: [
                        	{ display: '职工工号', name: 'emp_code', width: 120 },
                            { display: '职工名称', name: 'emp_name', width: 120 },
                            { display: '职务', name: 'duty_name', width: 120 },
                            { display: '职称', name: 'title_name', width: 120 },
                            { display: '级别', name: 'level_name', width: 120 },
                            { display: '', name: 'emp_id', hidden: true }
                        ],
                        width: '700px',
                        height: '205px',
                        dataModel: {
                            url: '../../queryHosEmpGrid.do?isCheck=false',
                        }
                    },
                	editable : function(col){
                    	if(col.rowData){
                    		if(col.rowData.state!=null){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return true;
                    	}
                    }},
                    { display: '职工姓名', name: 'emp_name', width: 120,
                    	editable : function(col){
                        	if(col.rowData){
                        		if(col.rowData.state!=null){
                        			return false;
                        		}
                        		return true;
                        	}else{
                        		return true;
                        	}
                        } },
                    { display: '护理素养', name: 'morality', width: 120 },
                    { display: '护理质量', name: 'quality', width: 120 },
                    { display: '安全护理', name: 'safety', width: 120 },
                    { display: '加分项', name: 'accessory', width: 120 },
                    { display: '备注', name: 'note', width: 120 }
                ];

                var main_paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    editable: true,
                    rowDblClick: function (event, ui) {
                    },
                    dataModel: {
                     /*    url: '' */
                    },
                    columns: main_columns,
                    toolbar: {
                        items: [
                            { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                            { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                            { type: 'button', label: '删除', listeners: [{ click: del }], icon: 'delete' },
                            { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                            { type: 'button', label: '导入', listeners: [{ click: importData }], icon: 'import' }/* ,
                            { type: 'button', label: '导出', listeners: [{ click: exportData }], icon: 'export' } */
                        ]
                    }
                };

                grid = $("#grid").etGrid(main_paramObj);

            }
            
            var query = function () {
                var params = [
                        { name: 'emp_id', value: emp_id.getValue() },
                        { name: 'year', value: year_date.getValue() },
                        { name: 'month', value: month_date.getValue() },
                        { name: 'dept_id', value: dept_select.getValue().split("@")[1] },
                        { name: 'duty_code', value: duty_select.getValue() },
                        { name: 'title_code', value: title_select.getValue() },
                        { name: 'level_code', value: level_select.getValue() },
                    ];
                grid.loadData(params,"queryHrEmpMonthNurse.do");
            };

            function add() {
            	grid.addRow();
            }

            function save() {
           	   var ParamVo = [];
                  var data = grid.selectGet();
                  if (data.length == 0) {
                      $.etDialog.error('请选择行');
                  } else {
                        $(data).each(function () {
                            var rowdata = this.rowData;
                            ParamVo.push(rowdata);
                        });
                       //验证重复数据
                      	if (!grid.checkRepeat(grid.selectGet(), ['emp_code','year','month'])) {
                           return;
                       }
                       var isPass = grid.validateTest({
           				required: {
           					year :true,
           					month :true,
           					emp_name :true
           				}
           			})
           			if (!isPass) {
           				return;
           			}
	                ajaxPostData({
	                    url: 'saveHrEmpMonthNurse.do',
	                    data: { 
	                        paramVo: JSON.stringify(ParamVo)
	                    },
	                    success: function () {
	                        query();
	                    }
	                })
	            }
            }
            function del() {
            	var selectData = grid.selectGet();
                if (selectData.length === 0) {
                    $.etDialog.error('请选择行');
                    return;
                }

                var param = [];
                selectData.forEach(function (item) {
                    param.push({
                        year: item.rowData.year,
                        month: item.rowData.month,
                        emp_id: item.rowData.emp_id,
                    });
                })
                
                $.etDialog.confirm('确定删除?', function () {
		                ajaxPostData({
		                     url: 'deleteHrEmpMonthNurse.do',
		                    data: { paramVo: JSON.stringify(param) },
		                    success: function () {
		                    	grid.deleteRows(selectData);
		                    }
		                })
	                }
	            )
            }
            
            function print() {
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
                  		title: "护理绩效月考核记录打印",//标题
                  		columns: JSON.stringify(grid.getPrintColumns()),//表头
                  		class_name: "com.chd.hrp.hr.service.nursingtraining.HrEmpMonthNurseService",
               			method_name: "queryByPrint",
               			bean_name: "hrEmpMonthNurseService",
               			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
               			foots: '',//表尾需要打印的查询条件,可以为空 
                   	};
                 	$.each(grid.getUrlParms(),function(i,obj){
               			printPara[obj.name]=obj.value;
                	}); 
                 	console.log(printPara);
                	officeGridPrint(printPara); }

            function importData() { 
            	var para = {
            			"column" : [ {
            				"name" : "year",
            				"display" : "年份",
            				"width" : "200",
            				"require" : true
            			},{
            				"name" : "month",
            				"display" : "月份",
            				"width" : "200",
            				"require" : true
            			},{
            				"name" : "emp_id",
            				"display" : "员工编码",
            				"width" : "200",
            				"require" : true
            			},{
            				"name" : "morality",
            				"display" : "护士素养",
            				"width" : "200"
            			},{
            				"name" : "quality",
            				"display" : "护理质量	",
            				"width" : "200"
            			},{
            				"name" : "safety",
            				"display" : "安全护理",
            				"width" : "200"
            			},{
            				"name" : "accessory",
            				"display" : "加分项",
            				"width" : "200"
            			},{
            				"name" : "note",
            				"display" : "备注",
            				"width" : "200"
            			} ]

            		};
            		importSpreadView("/hrp/hr/nursingtraining/hrempmonthnurse/importMonthNurse.do?isCheck=false", para, query);
            }

         /*    function exportData() { 
            	exportGrid(grid);
            } */
        </script>
    </head>

    <body>
        <!-- 季度护士长考核主页 添加页面 URL addQuarterlyAssessmentPage 修改页面跳转 URL updateQuarterlyAssessmentPage -->
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">考核年度：</td>
                    <td class="ipt">
                        <input type="text" id="year_date">
                    </td>
                    <td class="label">考核月份：</td>
                    <td class="ipt">
                        <input id="month_date" type="text" />
                    </td>
                       <td class="label">科室名称：</td>
                    <td class="ipt">
                        <select name="" id="dept_select" style="width:180px;"></select>
                    </td>
                    <td class="label">职工：</td>
                    <td class="ipt">
                        <select id="emp_id" style="width:180px;"></select>
                    </td>
                  

                </tr>
                <tr>
                 
                    <td class="label">职务：</td>
                    <td class="ipt">
                        <select name="" id="duty_select" style="width:180px;"></select>
                    </td>
                    <td class="label">职称：</td>
                    <td class="ipt">
                        <select name="" id="title_select" style="width:180px;"></select>
                    </td>
                    <td class="label">级别：</td>
                    <td class="ipt">
                        <select name="" id="level_select" style="width:180px;"></select>
                    </td>
                    <td class="label"></td>
                    <td class="label"></td>
                </tr>
            </table>

        </div>
        <div id="grid"></div>
    </body>

    </html>