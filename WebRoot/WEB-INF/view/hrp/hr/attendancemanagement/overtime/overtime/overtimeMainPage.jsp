<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title></title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="dialog,grid,select,datepicker,pageOffice" name="plugins" />
    </jsp:include>
    <script>
        var oper_date,overtime_date,emp_id,bdept_code,cdept_code,state,attend_code,grid,create_date;
		var is_check = 0;
        //页面初始话
        $(function () {
            initDict();//初始化查询条件
            initGrid();//初始化grid
            query();
        })
        
        //加载查询条件
        function initDict() {
            create_date = $("#create_date").etDatepicker({
            	range: true,
            	//defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
            });// 编制日期
            
            overtime_date= $("#overtime_date").etDatepicker({
            	range: true,
            	defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
            });//加班日期
            
            //编制部门
            bdept_code = $("#bdept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
            
            //出勤部门
            cdept_code = $("#cdept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
          
            //职工
        	emp_id = $("#emp_id").etSelect({
    			url : "../../queryEmpSelect.do?isCheck=false",
    			defaultValue : "none",
    			onChange : query
    		});
            
            //加班类型
        	attend_code=$("#attend_code").etSelect({
    			url : "../../queryOvertimeKind.do?isCheck=false",
    			defaultValue : "none",
    			onChange : query
    		});
        	
            //状态
            state = $("#state").etSelect({
                   options: [
                       { id: 0, text: '新建' },
                       { id: 1, text: '已提交' },
                       { id: 2, text: '已审核' }
                   ],
                   defaultValue: "none",
                   onChange: query, 
			});
        }
        
        
        //查询
        function query() {
            var param = [
	            { name: 'overtime_beg_date', value: overtime_date.getValue[0] },
	            { name: 'overtime_end_date', value: overtime_date.getValue[1] },
	            { name: 'create_beg_date',  value: create_date.getValue()[0] },
	            { name: 'create_end_date',  value: create_date.getValue()[1] },
	            { name: 'bdept_id', value: bdept_code.getValue().split('@')[1] },
	            { name: 'cdept_id', value: cdept_code.getValue().split('@')[1] },
	            { name: 'overtime_code',  value: $('#overtime_code').val()},
	            { name: 'emp_id',  value: emp_id.getValue() },
	            { name: 'attend_code', value: attend_code.getValue()},
	            { name: 'state',value:state.getValue()  }
            ];
            grid.loadData(param, "queryOvertime.do");
        }
        
        
        //加载表格
        function initGrid() {
            // 按钮
            var main_toolbar = {
                items: [
                    { type: "button", label: '查询', icon: 'print', listeners: [{ click: query }] },
                    { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                    { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
                    { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                    { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                    { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                ]
            };
            
            //grid列
            var main_columns = [
                { display: "加班编码",align: "left",width: 100,name: "overtime_code",editable: false,
                	render: function (ui) { // 修改页打开
                		return '<a data-item=' + ui.rowIndx + ' class="openUpdate">' + ui.cellData + '</a>'
                    }
                },
                {display: '出勤部门',align: 'left',name: 'cdept_name',width: 100},
                {display: "职工编码",align: "left",width: 90,name: "emp_code"},
                {display: "职工名称",align: "left",width: 90,name: "emp_name"},
                {display: '编制部门',align: 'left',name: 'bdept_name',width: 100},
                {display: "加班类型",align: "left",width: 80,name: "kind_name"},
                {display: "加班日期",align: "left",width: 90,name: "overtime_date"},
                {display: "加班天数",align: "right",width: 70,name: "days"},
                {display: "加班开始时间",align: "left",width: 85,name: "begin_date"},
                {display: "加班结束时间",align: "left",width: 85,name: "end_date"},  
                {display: "加班小时数",align: "right",width: 72,name: "hours"},
                {display: "状态",align: "left",width: 80,name: "state_name"}, 
                {display: '制单日期',align: 'left',name: 'create_date',width: 80},
                {display: "提交人",align: "left",width: 80,name: "loginer_name"}, 
                {display: "提交日期",align: "left",width: 80,name: "oper_date"},
                {display: "审核人",align: "left",width: 80,name: "checker_name"},
                {display: "审核日期",align: "left",width: 80,name: "check_date"},
                {display: "退回原因",align: "left",width: 80,name: "back_reason"}
            ];
            
            
            //grid属性
            var main_obj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                toolbar: main_toolbar,
                columns: main_columns,
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

		//添加页面跳转
        function add() {
        	parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/addOvertimePage.do?isCheck=false',
                frameName : window.name,
                width: $(parent.window).width(),
                height: $(parent.window).height()-100,
                title: '加班添加页',
            });
        }
		
		//修改页面跳转
        function openUpdate(rowData) {
            var parm = '';
            parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/updateOvertimePage.do?isCheck=false&overtime_code='+rowData.overtime_code,
                frameName : window.name,
                width: $(parent.window).width(),
                height: $(parent.window).height()-100,
                title: '加班修改页',
            });
        }
		
        
        //删除
        function del() {
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var ParamVo = [];//存储选择的数据
        	var msg = '';//记录非新建状态的加班编码
        	var overtime_codes = "";
       	  	$(selectData).each(function () {
				var rowdata = this.rowData;
				if(rowdata.state != 0){//判断是否为新建状态
                	msg += '加班编码:【' + rowdata.overtime_code + '】不是新建状态<br/>'
				}else{
					overtime_codes=overtime_codes+'\''+rowdata.overtime_code+"\',";
				}
			});
        	
			if(msg != ''){//提示信息
				$.etDialog.error(msg);
				return ; 
			}else{
				$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: 'deleteOvertime.do',
						data: {
							'overtime_codes' : overtime_codes.substr(0, overtime_codes.length - 1)
						},
	                	success: function () {
	                    	query();
	                	}
					})
				});
			}
		}

        
        // 提交
        var submit = function () {
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return ; 
            } 
            
            var msg = '';//记录不是新建状态的加班编码
            var overtime_codes = "";
            $(data).each(function () {
                var rowdata = this.rowData;
                if(rowdata.state != 0){//判断是否为新建状态
                	msg += '加班编码:【' + rowdata.overtime_code + '】不是新建状态<br/>'
                }else{
					overtime_codes=overtime_codes+'\''+rowdata.overtime_code+"\',";
				}
            });
            
            if(msg != ''){
            	$.etDialog.error(msg);
                return ; 
            }else{
            	$.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({ 
                    	url: 'confirmOvertime.do',
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
        
        
        // 撤回
        var cancel = function () {
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            }
            
            var msg = '';//记录非提交状态的加班编码
            var overtime_codes="";
            $(data).each(function () {
                var rowdata = this.rowData;
                if(rowdata.state != 1){//判断是否为提交状态
                	msg += '加班编码:【' + rowdata.overtime_code + '】不是提交状态<br/>'
                }else{
					overtime_codes=overtime_codes+'\''+rowdata.overtime_code+"\',";
				}
            });
            
            if(msg != ''){
            	$.etDialog.error(msg);
                return ; 
            }else{
            	$.etDialog.confirm('确定撤回?', 
                  		function () {ajaxPostData({
                  			url: 'reConfirmOvertime.do',
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
        
        //打印
        function print(){
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads = {};
        	
        	var printPara={
          		title: "加班申请",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.hr.service.attendancemanagement.overtime.HrOvertimeService",
       			bean_name: "hrOvertimeService",
       			method_name: "queryOvertimeByPrint",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: ''//表尾需要打印的查询条件,可以为空 
           	};
        	
           	$.each(grid.getUrlParms(),function(i,obj){
         		printPara[obj.name]=obj.value;
           	}); 
           	officeGridPrint(printPara);
        }
        
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">加班日期：</td>
                <td class="ipt">
                    <input id="overtime_date" type="text"  style="width:200px"/>
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
                    <input id="create_date" type="text" style="width:200px"/>
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
                    <select id="attend_code" type="text" style="width:200px"></select>
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