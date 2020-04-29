<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title></title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="dialog,grid,select,datepicker" name="plugins" />
    </jsp:include>
    <script>
        var create_date,overtime_date,emp_id,bdept_code,cdept_code,state,attend_code,grid,create_date;
		var is_check = 1;
        function initDict() {
            // 登记日期
            create_date = $("#create_date").etDatepicker({
            	range: true,
            	defaultValue: "none"
            });
            overtime_date= $("#overtime_date").etDatepicker({
            	//timepicker : true,
            	range: true,
            	defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
            });
          
            bdept_code = $("#bdept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
            
            cdept_code = $("#cdept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
            
        	emp_id = $("#emp_id").etSelect({
    			url : "../../queryEmpSelect.do?isCheck=false",
    			defaultValue : "none",
    			onChange : query
    		});
        	attend_code=$("#attend_code").etSelect({
    			url : "../../queryOvertimeKind.do?isCheck=false",
    			defaultValue : "none",
    			onChange : query
    		});
        	   state = $("#state").etSelect({
                   options: [
                       { id: 1, text: '已提交' },
                       { id: 2, text: '已审核' },
                   ],
                   defaultValue: "none",
                    onChange: query, 
               });
        }
        function query() {
            var param = [
				{ name: 'overtime_beg_date', value: overtime_date.getValue[0] },
				{ name: 'overtime_end_date', value: overtime_date.getValue[1] },
				{ name: 'create_beg_date',  value: create_date.getValue()[0] },
				{ name: 'create_end_date',  value: create_date.getValue()[1] },
	            { name: 'bdept_id', value: bdept_code.getValue().split('@')[1] },
	            { name: 'cdept_id', value: cdept_code.getValue().split('@')[1] },
	            { name: 'emp_id',  value: emp_id.getValue() },
	            { name: 'overtime_code' ,value: $('#overtime_code').val() },
	            { name: 'attend_code', value: attend_code.getValue()},
	            { name: 'state',value:state.getValue() },
            ];
            grid.loadData(param);
        }
        
        function initGrid() {
            // 基础表格参数
            var main_toolbar = {
                items: [
                    { type: "button", label: '查询', icon: 'print', listeners: [{ click: query }] },
                    //{ type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                    { type: 'button', label: '审核', listeners: [{ click: submit }], icon: 'submit' },
                    { type: 'button', label: '退回', listeners: [{ click: unSubmit }], icon: 'submit' },
                    { type: 'button', label: '销审', listeners: [{ click: cancel }], icon: 'cancel' },
                ]
            };
            var main_columns = [
                { display: "加班编码",
                    align: "left",
                    width: 100,
                    name: "overtime_code",
                    editable: false,
                    render: function (ui) { // 修改页打开
                		return '<a data-item=' + ui.rowIndx + ' class="openUpdate">' + ui.cellData + '</a>'
                    }
                },
                {
                    display: '编制部门',
                    align: 'left',
                    name: 'bdept_name',
                    width: 120
                },
                {
                    display: '出勤部门',
                    align: 'left',
                    name: 'cdept_name',
                    width: 120
                },
                {
                    display: "职工编码",
                    align: "left",
                    width: 120,
                    name: "emp_code"
                },
                {
                    display: "职工名称",
                    align: "left",
                    width: 120,
                    name: "emp_name"
                },
                {
                    display: "加班类型",
                    align: "left",
                    width: 90,
                    name: "kind_name"
                },
                {
                    display: "加班日期",
                    align: "left",
                    width: 90,
                    name: "overtime_date" 
                },
                {
                    display: "加班天数",
                    align: "right",
                    width: 80,
                    name: "days"
                },
                {
                    display: "加班小时数",
                    align: "right",
                    width: 80,
                    name: "hours"
                },
                {
                    display: "加班开始点数",
                    align: "left",
                    width: 90,
                    name: "begin_date"
                },
                {
                    display: "加班结束点数",
                    align: "left",
                    width: 90,
                    name: "end_date"
                },  {
                    display: "状态",
                    align: "left",
                    width: 80,
                    name: "state_name"
                },
                {
                    display: '编制日期',
                    align: 'left',
                    name: 'create_date',
                    width: 80
                },
                {
                    display: "提交人",
                    align: "left",
                    width: 80,
                    name: "oper_name"
                },
                {
                    display: "提交日期",
                    align: "left",
                    width: 80,
                    name: "oper_date"
                },
                {
                    display: "审核人",
                    align: "left",
                    width: 80,
                    name: "checker_name"
                },
                {
                    display: "审核日期",
                    align: "left",
                    width: 80,
                    name: "check_date"
                }
            ];
            var main_obj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                toolbar: main_toolbar,
                columns: main_columns,
                 dataModel: {
                    url: 'queryOvertimeCheck.do'
                },  
    			pageModel: {
                    type: 'remote',//local前台分页
                }, 
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
            };
            grid = $("#mainGrid").etGrid(main_obj);
            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('data-item');
                var currentRowData = grid.getDataInPage()[rowIndex];
                openUpdate(currentRowData);
            })
        }

        function openUpdate(rowData) {
            parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/updateOvertimePage.do?isCheck=false&overtime_code='+rowData.overtime_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '加班修改页',
                frameNameObj: { 'index': window.name },
            });
        }

        // 审核
        var submit = function () {
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return ; 
            }
            
            var msg = '';//记录非提交状态的加班编码
            var ParamVo = [];
            var overtime_codes = "";
            $(data).each(function () {
                var rowdata = this.rowData;
                if(rowdata.state != 1){//判断是否为提交状态
                	msg += '加班编码:【' + rowdata.overtime_code + '】不是提交状态!<br/>'
                }else{
                	overtime_codes=overtime_codes+'\''+rowdata.overtime_code+"\',";
                }
            });
            
            if(msg != ''){
            	$.etDialog.error(msg);
                return ; 
            }else{
            	$.etDialog.confirm('确定审核?', function () {
                    ajaxPostData({
                        url: 'auditOvertimeCheck.do',
                        data: {
                        	'overtime_codes' : overtime_codes.substr(0, overtime_codes.length - 1)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
        
        // 退回
        var unSubmit = function () {
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return ; 
            }
            var msg = '';//记录非提交状态的加班编码
            var ParamVo = [];
            var overtime_codes = "";
            $(data).each(function () {
                var rowdata = this.rowData;
                if(rowdata.state != 1){//判断是否为提交状态
                	msg += '加班编码:【' + rowdata.overtime_code + '】不是提交状态<br/>';
            	}else{
            		overtime_codes=overtime_codes+'\''+rowdata.overtime_code+"\',";
            	}
            });
                  
			if(msg != ''){
				$.etDialog.error(msg);
				return ; 
 			}else{
 				$.etDialog.confirm('确定退回?', function () {
 					ajaxPostData({
 						url: 'reAuditOvertimeCheck.do',
 						data: {
 							'overtime_codes' : overtime_codes.substr(0, overtime_codes.length - 1)
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
        	var msg = '';//记录非提交状态的加班编码
            var overtime_codes = "";
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                  $(data).each(function () {
                    	var rowdata = this.rowData;
                    	if(rowdata.state != 2){//判断是否为提交状态
                      		msg += '加班编码:【' + rowdata.overtime_code + '】不是审核状态<br/>'
                  		}else{
                  			overtime_codes=overtime_codes+'\''+rowdata.overtime_code+"\',";
                  		}
                  });
            }
            if(msg != ''){
				$.etDialog.error(msg);
				return ; 
 			}else{
 				$.etDialog.confirm('确定销审?', function () {
 					ajaxPostData({
 						url: 'unAuditOvertimeCheck.do',
 						data: {
 							'overtime_codes' : overtime_codes.substr(0, overtime_codes.length - 1)
 						},
 	                	success: function () {
 	                    	query();
 	                	}
 					})
 				});
 			}
        };

        $(function () {
            initDict();
            initGrid();
           // query();
        })
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">加班日期：</td>
                <td class="ipt">
                    <input id="overtime_date" type="text"  style="width:180px"/>
                </td>
                
                <td class="label">加班编码：</td>
                <td class="ipt">
                    <input id="overtime_code" type="text" />
                </td>
                
                <td class="label">职工名称：</td>
                <td class="ipt" >
                    <select id="emp_id" type="text" style="width:180px"></select>
                </td>
                
            </tr>
            <tr>
                <td class="label">制单日期：</td>
                <td class="ipt">
                    <input id="create_date" type="text" style="width:180px"/>
                </td>
                
                <td class="label">编制部门：</td>
                <td class="ipt">
                    <select id="bdept_code" type="text" style="width:180px"></select>
                </td>
                
                <td class="label">出勤部门：</td>
                <td class="ipt">
                    <select id="cdept_code" type="text" style="width:180px"></select>
                </td>
                
            </tr>
            <tr>
                <td class="label">加班类型：</td>
                <td class="ipt">
                    <select id="attend_code" type="text" style="width:180px"></select>
                </td>
                
                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="state" type="text" style="width:180px"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>