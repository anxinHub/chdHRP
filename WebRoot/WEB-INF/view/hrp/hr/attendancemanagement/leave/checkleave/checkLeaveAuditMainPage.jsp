<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
    	<jsp:param value="grid, select, datepicker, dialog, hr" name="plugins" />
    </jsp:include>
    <script>
    var grid, attend_xjreg_date, dept_code, emp_id, xj_type, state, main_obj;
    var xj_state = 0;
    $(function () {
    	initSelect();
    	initGrid();
    	query();
    });
    
    function query() {
        var param = [ 
					 { name: 'xjreg_begin_date', value: attend_xjreg_date.getValue()[0] },
                     { name: 'xjreg_end_date', value: attend_xjreg_date.getValue()[1] },
                     { name: 'dept_id', value: dept_code.getValue().split('@')[1] },
                     { name: 'emp_id', value: emp_id.getValue() },
                     { name: 'xj_type', value: xj_type.getValue() },
                     { name: 'state', value: state.getValue() },
                     { name: 'audit', value: 1 },
                     { name:'attend_xjapply_code', value:$("#attend_xjapply_code").val()},
		];
        grid.loadData(param,'queryApplyingLeavesAudit.do');
    }
    
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

        xj_type = $("#xj_type").etSelect({
        	options: [
            	{ id: 0, text: '休假' },
            	{ id: 1, text: '销假' },
            ],
            defaultValue: "0",
            onChange: function (value) {
            	xj_state =  typeof(value);
            	
            	if(xj_state == 0){
                	main_obj.toolbar.items[6].disabled = false;
                }else{
                	main_obj.toolbar.items[6].disabled = true;
                } 
            	//需要前端配合写一个toolbar的刷新事件
            	
            	//console.log(main_obj.toolbar.items[6].disabled); 
            	query();
            }
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
                            { type: 'button', label: '审核', listeners: [{ click: submit }], icon: 'submit' },
                            { type: 'button', label: '退回', listeners: [{ click: unsubmit }], icon: 'submit' },
                            { type: 'button', label: '销审', listeners: [{ click: cancel }], icon: 'cancel' },
                            { type: 'button', label: '核定', listeners: [{ click: check }], icon: 'unsubmit' },
                            { type: 'button', label: '取消核定', listeners: [{ click: uncheck }], icon: 'cancel' },  
                            { type: "button", label: '作废', icon: 'cancel', listeners: [{ click: cancelF }],disabled: xj_state == 0 ? false : true },
                        ]
                    };
                    var main_columns = [
                        {<%-- 休假申请编号与销假申请 编号在此页面用的参数名都是attend_xjapply_code --%>
                            display: '申请编号', name: 'attend_xjapply_code', width: 120,
                            render: function (ui) {
                                var updateHtml =
                                    '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                    ui.cellData +
                                    '</a>'
                                return updateHtml;
                            }
                        },
                        {
                            display: '', name: 'attend_xxjapply_code', width: 80,hidden:true
                        },
                        {
                            display: '职工编码', name: 'emp_code', width: 80
                        },
                        {
                            display: '职工名称', name: 'emp_name', width: 100
                        },
                        {
                            display: '部门', name: 'dept_name', width: 100
                        },
                        {
                            display: '休假类型', name: 'attend_name', width: 80
                        },
                        {
                            display: '休假开始日期', name: 'attend_xjbegdate', width: 80
                        },
                        {
                            display: '休假结束日期', name: 'attend_xjenddate', width: 80
                        },
                        {
                            display: '休假天数', name: 'attend_xjdays', width: 80
                        },
                        {
                            display: '申请休假结束时间', name: 'attend_xxj_backtime', width: 100
                        },
                        {
                            display: '实际休假天数', name: 'attend_xxjdays', width: 80
                        },
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
                        }/* ,
                        {
                            display: '状态', name: 'state_name', width: 80
                        } */
                    ];
                    main_obj = {
                  		width :  'auto',
                        height: '100%',
                        inWindowHeight: true,
                        checkbox: true,
                        editable: false,
                        toolbar: main_toolbar,
                        columns: main_columns,
                    };
                    
                    
                    grid = $("#mainGrid").etGrid(main_obj);
                    $("#mainGrid").on('click', '.openUpdate', function () {
                        var rowIndex = $(this).attr('row-index');
                        var currentRowData = grid.getDataInPage()[rowIndex];
                        openUpdate(currentRowData);
                    });
                    main_obj.toolbar.items[6].disabled
                }

                // 审核
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
                              if(rowdata.state!=1){
                            	  codes=codes+rowdata.attend_xjapply_code;
                              }else{
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',";
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('请选择提交状态的单据！');
                    	return;
                    }else{
                        $.etDialog.confirm('确定审核?', function () {
                            ajaxPostData({
                                url: 'auditHrApplyingLeaves.do',
                                data: {
                                	'xj_type': xj_type.getValue(),
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                    }
                };
                
                // 审核不通过
                var unsubmit = function () {
                    var ParamVo = [];
                    var data = grid.selectGet();
                    var codes = "";
                    var attend_xjapply_codes="";
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
                    	$.etDialog.error('请选择提交状态的单据！');
                    	return;
                    }else{
                        $.etDialog.confirm('确定退回?', function () {
                            ajaxPostData({
                                url: 'auditUnHrApplyingLeaves.do',
                                data: {
                                	'xj_type': xj_type.getValue(),
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	query();
                                }
                            })
                        });
                    }
                };
                
                // 销审
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
                              if(rowdata.state!=2){
                            	  codes=codes+rowdata.attend_xjapply_code;
                              }else{
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',";
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('请选择审核状态的单据！');
                    	return;
                    }else{
                        $.etDialog.confirm('确定销审?', function () {
                            ajaxPostData({
                                url: 'unauditHrHrApplyingLeaves.do',
                                data: {
                                	'xj_type': xj_type.getValue(),
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                    }
                };
                
                //核定
                var check= function () {
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
                              if(rowdata.state!=2){
                            	  codes += rowdata.attend_xjapply_code;
                              }else{
                            	  attend_xjapply_codes += '\''+rowdata.attend_xjapply_code+"\',";
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('请选择审核状态的单据！');
                    	return;
                    }else{
                        $.etDialog.confirm('确定核定?', function () {
                            ajaxPostData({
                                url: 'checkHrApplyingLeaves.do',
                                data: {
                                	'xj_type': xj_type.getValue(),
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                    }
                };
                
                //取消核定
                var uncheck= function () {
                    var ParamVo = [];
                    var data = grid.selectGet();
                    var codes = "";
                    var attend_xjapply_codes="";
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                        return;
                    } else {
                          $(data).each(function () {
                              var rowdata = this.rowData;
                              if(rowdata.state!=3){
                            	  codes=codes+rowdata.attend_xjapply_code;
                              }else{
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',"; 
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('请选择核定状态的单据！');
                    	return;
                    }else{
                        $.etDialog.confirm('确定取消核定?', function () {
                            ajaxPostData({
                                url: 'uncheckHrHrApplyingLeaves.do',
                                data: {
                                	'xj_type': xj_type.getValue(),
                                	'attend_xjapply_codes' : attend_xjapply_codes
                                },
                                success: function () {
                                	 query();
                                }
                            })
                        });
                    }
                };
                
				//作废
                function cancelF(){
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
                              }else{
                            	  if(rowdata.attend_xxjapply_code!=""){
                            		  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xxjapply_code+"\',";
                            	  }else{
                            	  console.log(rowdata)
                            	  attend_xjapply_codes=attend_xjapply_codes+'\''+rowdata.attend_xjapply_code+"\',";}
                              }
                          });
                    }
                    
                    if(codes!=""){
                    	$.etDialog.error('已经做废的单据不能再次作废！');
                    	return;
                    }else{
                    	$.etDialog.confirm('确定要作废?', function () {
                        	ajaxPostData({
                                    url: 'cancelFXJApplyingLeaves.do',
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
                
                
           		//修改页面
                var openUpdate = function (rowData) {
	                if(xj_type.getValue()==0){
	                	parent.$.etDialog.open({
	                    	url: 'hrp/hr/attendancemanagement/attend/updateApplyingLeavesPage.do?isCheck=false&attend_xjapply_code='+rowData.attend_xjapply_code+'&xj_type='+xj_type.getValue(),
	                        title: '查看休假申请',
	                        isMax: true,
	                    })	
	                }else{
	                	parent.$.etDialog.open({
	                		<%-- 此url仅在此页面传参：休假申请编号与销假申请编号的参数名颠倒使用，以对应HrTerminateleaveMapper接口的query2()方法---yangyunfei --%>
	                    	url: 'hrp/hr/attendancemanagement/attend/updateTerminateleavePage.do?isCheck=false&attend_xxjapply_code='
	                           		+rowData.attend_xjapply_code+'&xj_type='+xj_type.getValue()+'&attend_xjapply_code='+rowData.attend_xxjapply_code,
	                        title: '查看销假申请',
	                        isMax: true,
	                    })
	                }
                };
            </script>
        </head>

        <body style="overflow:hidden;">
            <div class="main">
                <table class="table-layout">
                    <tr>
	                    <td class="label"> 分类： </td>
                        <td class="ipt">
                            <select name="" id="xj_type" style="width:180px;"></select>
                        </td>
                        
                        <td class="label">登记日期：</td>
                        <td class="ipt">
                            <input type="text" id="attend_xjreg_date">
                        </td>
                        
                        <td class="label">申请编号：</td>
                        <td class="ipt">
                            <input type="text" id="attend_xjapply_code">
                        </td>
                        
                        <td class="label">部门：</td>
                        <td class="ipt">
                            <select name="" id="dept_code" style="width:180px;"></select>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="label">  职工名称：</td>
                        <td class="ipt">
                            <select name="" id="emp_id" style="width:180px;"></select>
                        </td>
        
                        <td class="label">状态：</td>
                        <td class="ipt">
                            <select name="" id="state" style="width:180px;"></select>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="mainGrid"></div>
        </body>
        </html>