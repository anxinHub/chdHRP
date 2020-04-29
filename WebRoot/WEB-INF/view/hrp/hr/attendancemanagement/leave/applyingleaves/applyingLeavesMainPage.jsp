<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%String path = request.getContextPath();%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Insert title here</title>
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="grid, select, datepicker, dialog, hr,pageOffice" name="plugins" />
            </jsp:include>
            <script>
                var grid, attend_xjreg_date, dept_code, emp_id, attend_code, state;
                $(function () {
                    initSelect();
                    initGrid();
                    query();
                });

                function initSelect() {
                	
                	attend_xjreg_date = $("#attend_xjreg_date").etDatepicker({
                    	range : true,
                    	defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
                    })
                    
                    dept_code = $("#dept_code").etSelect({
                        url: "../../queryHosDeptSelect.do?isCheck=false",
                        defaultValue: "none",
                    });
                	emp_id = $("#emp_id").etSelect({
            			url : "../../queryEmpSelect.do?isCheck=false",
            			defaultValue : "none",
            			onChange : query
            		});

                	attend_code = $("#attend_code").etSelect({
                        url: "../../queryAllAttendCode.do?isCheck=false",
                        defaultValue: "none",
                    });
                	   state = $("#state").etSelect({
                           options: [
                               { id: 0, text: '新建' },
                               { id: 1, text: '已提交' },
                               { id: 2, text: '已审核' },
                               { id: 3, text: '已核定' },
                               { id: 4, text: '已作废' },
                           ],
                           defaultValue: "none",
                            onChange: query, 
                       });
                }

                function initGrid() {
                    // 基础表格参数
                    var main_toolbar = {
                        items: [
                            { type: "button", label: '查询', icon: 'print', listeners: [{ click: query }] },
                            { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                            { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
                            { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                            { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                            { type: "button", label: '添加（补登）', icon: 'add', listeners: [{ click: add2 }] },
                            { type: "button", label: '作废', icon: 'cancel', listeners: [{ click: cancelF }] },
                            { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                           
                        ]
                    };
                    var main_columns = [
                        {
                            display: '休假申请编号', name: 'attend_xjapply_code', width: 120,
                            render: function (ui) {
                                var updateHtml =
                                    '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                    ui.cellData +
                                    '</a>'
                                return updateHtml;
                            }
                        },
                        {
                            display: '登记日期', name: 'attend_xjreg_date', width: 120
                        },
                        {
                            display: '职工编码', name: 'emp_code', width: 80
                        },
                        {
                            display: '职工名称', name: 'emp_name', width: 100
                        },
                        {
                            display: '部门', name: 'dept_name', width: 80
                        },
                        {
                            display: '休假类型', name: 'attend_name', width: 80
                        },
                        {
                            display: '休假开始日期', name: 'attend_xjbegdate', width: 120
                        },
                        {
                            display: '休假结束日期', name: 'attend_xjenddate', width: 120
                        },
                        {
                            display: '休假天数', name: 'attend_xjdays', width: 80
                        },
                        { display: "实际休假天数", name: "attend_xxjdays", width: 80},
                        {
                            display: '状态', name: 'state_name', width: 80
                        },
                        {
                            display: '提交人', name: 'attend_xjloginer_name', width: 80
                        },
                        {
                            display: '操作日期', name: 'attend_xjreg_operdate', width: 80
                        },
                        {
                            display: '审核人', name: 'checker_name', width: 80
                        },
                        {
                            display: '审核日期', name: 'check_date', width: 80
                        },
                        {
                            display: '核定人', name: 'confirmer_name', width: 80
                        },
                        {
                            display: '核定日期', name: 'confirm_date', width: 80
                        },
                        {
                            display: '是否补登记', name: 'attend_xj_add_name', width: 80
                        },{
                            display: '退回原因', name: 'back_reason', width: 80
                        },
                    ];
                    var main_obj = {
                        height: '100%',
                        inWindowHeight: true,
                        checkbox: true,
                        toolbar: main_toolbar,
                        columns: main_columns,
                       /*  dataModel: {
                            url: '',
                        }, */  
                        rowDblClick: function (event, ui) {
                            var rowData = ui.rowData;
                            openUpdate(rowData);
                        },
                    };
                    grid = $("#mainGrid").etGrid(main_obj);
                    $("#mainGrid").on('click', '.openUpdate', function () {
                        var rowIndex = $(this).attr('row-index');
                        var currentRowData = grid.getDataInPage()[rowIndex];
                        openUpdate(currentRowData);
                    })
                }

                function add() {
                	parent.$.etDialog.open({
                        url: 'hrp/hr/attendancemanagement/attend/addApplyingLeavesPage.do?isCheck=false',
                        width: $(window).width(),
                        height: $(window).height(),
                        frameName :window.name,
                        title: '休假申请添加'
                    });
                }
                function add2() {
                	parent.$.etDialog.open({
                        url: 'hrp/hr/attendancemanagement/attend/mendApp2lyingLeavesPage.do?isCheck=false',
                        width: $(window).width(),
                        height: $(window).height(),
                        frameName :window.name,
                        title: '补登记添加'
                    });
                }
                
                // 提交
                var submit = function () {
                    var ParamVo = [];
                    var codes = "";
                    var attend_xjapply_codes="";
                    var data = grid.selectGet();
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                        return;
                    } else {
                          $(data).each(function () {
                              var rowdata = this.rowData;
                              if(rowdata.state!=0){
                            	  codes=codes+rowdata.attend_xjapply_code;
                              }else{
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',";
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('请选择新建的单据！');
                    	return;
                    }else{
                    	$.etDialog.confirm('确定提交?', function () {
                            ajaxPostData({
                                url: 'confirmApplyingLeaves.do',
                                data: {
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                        
                    }
                };
                
                // 撤回
                var cancel = function () {
                    var ParamVo = [];
                    var codes = "";
                    var attend_xjapply_codes="";
                    var data = grid.selectGet();
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                        return;
                    } else {
                          $(data).each(function () {
                        	  var rowdata = this.rowData;
                              if(rowdata.state!=1){
                            	  codes=codes+rowdata.attend_xjapply_code;
                              }else{
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',";
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('请选择提交的单据！');
                    	return;
                    }else{
                        $.etDialog.confirm('确定撤回?', function () {
                            ajaxPostData({
                                url: 'reConfirmApplyingLeaves.do',
                                data: {
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                    }
                };
                
                //删除
                function del() {
                	var ParamVo = [];
                    var codes = "";
                    var attend_xjapply_codes="";
                    var data = grid.selectGet();
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                        return;
                    } else {
                          $(data).each(function () {
                        	  var rowdata = this.rowData;
                              if(rowdata.state != 0){
                            	  codes=codes+rowdata.attend_xjapply_code;
                              }else{
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',";
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('请选择新建的单据！');
                    	return;
                    }else{
                        $.etDialog.confirm('确定要删除?', function () {
                            ajaxPostData({
                                url: 'deleteApplyingLeaves.do',
                                data: {
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                    }
                }
                //作废
                function cancelF() {
                	var ParamVo = [];
                    var codes = "";
                    var attend_xjapply_codes="";
                    var data = grid.selectGet();
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                        return;
                    } else {
                          $(data).each(function () {
                        	  var rowdata = this.rowData;
                        	  if(rowdata.state == 4){
                            	  codes=codes+rowdata.attend_xjapply_code;
                            	  return false;
                              }else{
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',";
                              }
                        	  
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('已经做废的单据不能再次作废！');
                    	return;
                    }else{
                    	$.etDialog.confirm('确定要作废?', function () {
                            ajaxPostData({
                                url: 'cancelFApplyingLeaves.do',
                                data: {
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                    }
                     
                }
				//查询
                function query() {
                    var param = [ 
                                 { name: 'xjreg_begin_date', value: attend_xjreg_date.getValue()[0] },
                                 { name: 'xjreg_end_date', value: attend_xjreg_date.getValue()[1] },
                                 { name: 'dept_id', value: dept_code.getValue().split('@')[1] },
                                 { name: 'emp_id', value: emp_id.getValue() },
                                 { name: 'attend_code', value: attend_code.getValue() },
                                 { name: 'state', value: state.getValue() },
                                 {name:'attend_xjapply_code', value:$("#attend_xjapply_code").val()}
					];
                    grid.loadData(param,'queryApplyingLeaves.do');
                }
           
                var openUpdate = function (rowData) {
                	parent.$.etDialog.open({
                        url: 'hrp/hr/attendancemanagement/attend/updateApplyingLeavesPage.do?isCheck=false&attend_xjapply_code='
                        		+rowData.attend_xjapply_code+'&xj_type='+rowData.xj_type,
                        title: '休假申请修改',
                        width: $(window).width(),
                        height: $(window).height(),
                        frameName: window.name
                    })
                };
                
                //打印
                function print(){
                	if(grid.getAllData()==null){
                		$.etDialog.error("请先查询数据！");
            			return;
            		}
                	var heads = {};
                	
                	var printPara={
                  		title: "休假申请",//标题
                  		columns: JSON.stringify(grid.getPrintColumns()),//表头
                  		class_name: "com.chd.hrp.hr.service.attendancemanagement.leave.HrApplyingLeavesService",
               			bean_name: "hrApplyingLeavesService",
               			method_name: "queryApplyingLeavesByPrint",
               			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
               			foots: ''//表尾需要打印的查询条件,可以为空 
                   	};
                	
                	$.each(grid.getUrlParms(),function(i,obj){
                 		printPara[obj.name]=obj.value;
                   	}); 
                   	console.log(printPara);
                   	officeGridPrint(printPara);
                }
                
            </script>
        </head>

        <body>
            <div class="main">
                <table class="table-layout">
                    <tr>
                        <td class="label"> 登记日期：</td>
                        <td class="ipt">
                            <input type="text" id="attend_xjreg_date">
                        </td>
                        
                        <td class="label">申请编号：</td>
                        <td class="ipt">
                            <input type="text" id="attend_xjapply_code">
                        </td>
                        
                        <td class="label"> 部门：</td>
                        <td class="ipt">
                            <select name="" id="dept_code" style="width:180px;"></select>
                        </td>
                    </tr>
                    <tr>
                        <td class="label"> 职工名称：</td>
                        <td class="ipt">
                            <select name="" id="emp_id" style="width:180px;"></select>
                        </td>
                        
                        <td class="label">  休假类型：</td>
                        <td class="ipt">
                            <select name="" id="attend_code" style="width:180px;"></select>
                        </td>
                        
                        <td class="label"> 状态：</td>
                        <td class="ipt">
                            <select name="" id="state" style="width:180px;"></select>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="mainGrid"></div>
        </body>
     </html>