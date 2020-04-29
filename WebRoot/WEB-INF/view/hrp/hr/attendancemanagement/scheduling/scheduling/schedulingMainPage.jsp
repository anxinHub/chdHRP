<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,select,grid,datepicker,pageOffice" name="plugins" />
</jsp:include>
<script>
        var pb_date;
        var grid;
        var initFrom = function () {
        	
        	pb_date = $("#pb_date").etDatepicker({range: true,defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']});

        	state=$("#state").etSelect();
        	
        	attend_areacode=$("#attend_areacode").etSelect({
                url: "queryAttendAreacode.do?isCheck=false",
                defaultValue: "none"/* ,
                onChange: query */
            });
           
          /* 	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            }); */
        	emp_id = $("#emp_id").etSelect({
                url: '../../queryEmpSelect.do?isCheck=false',
                defaultValue: 'none'/* ,
                onChange: query */
            });
        };
        function query()  {
        	
        	if($('#pb_date').val()=="" || $('#pb_date').val().split('至').length!=2){
        		$.etDialog.error('请按排班日期范围查询！');
				return;
        	}
            var params = [
						{name: 'pb_date_begin',value: $('#pb_date').val().split('至')[0]},
						{name: 'pb_date_end',value: $('#pb_date').val().split('至')[1]},
                          { name: 'attend_pbname', value: $("#attend_pbname").val() },
                          { name: 'attend_areacode', value: attend_areacode.getValue() },
                          { name: 'state', value: state.getValue() },
                          {name:'emp_id',    value:emp_id.getValue()}
            ];
            grid.loadData(params,'queryScheduling.do');
        };
       
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/SchedulingAddPage.do?isCheck=false&attend_areacode='+attend_areacode.getValue(),
                width: $(window).width(),
                height: $(window).height(),
            	frameName : window.name,
                isMax: true,
                title: '排班添加'
            });
        };
    var openUpdate = function (rowData) {
            
        	parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/updateSchedulingPage.do?isCheck=false&attend_pbcode='
                		+rowData.attend_pbcode+'&attend_pbrule='+rowData.attend_pbrule,
                title: '排班修改',
                isMax: true,
            	frameName : window.name,
                width: $(window).width(),
                height: $(window).height(),
            })
        };
    /*     // 提交
        var submit = function () {
			
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      ParamVo.push(rowdata);
                  });
                $.etDialog.confirm('确定归档?', function () {
                    ajaxPostData({
                        url: 'confirmScheduling.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
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
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      ParamVo.push(rowdata);
                  });
                $.etDialog.confirm('确定取消归档?', function () {
                    ajaxPostData({
                        url: 'reConfirmScheduling.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        }; */
        var remove = function () {
       	 var selectData = grid.selectGet();
         if (selectData.length === 0) {
             $.etDialog.error('请选择行');
             return;
         }
     	var ParamVo = [];
     	  $(selectData).each(function () {
               var rowdata = this.rowData;
               ParamVo.push(rowdata);
           });
         $.etDialog.confirm('确定删除?', function () {
         ajaxPostData({
         	   url: 'deleteScheduling.do',
             data: { paramVo: JSON.stringify(ParamVo) },
             success: function () {
                 grid.deleteRows(selectData);
             }
         }) });
        };
        var check= function () {

            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      ParamVo.push(rowdata);
                  });
                $.etDialog.confirm('确定审签?', function () {
                    ajaxPostData({
                        url: 'auditCountersign.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
        var uncheck= function () {

            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      ParamVo.push(rowdata);
                  });
                $.etDialog.confirm('确定取消?', function () {
                    ajaxPostData({
                        url: 'unauditCountersign.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
      /*   var putout = function () {
        	exportGrid(grid);
        }; */
        
        var initGrid = function () {
            var columns = [
				{ display: '排班编码', name: 'attend_pbcode', width: 120,editable: false,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'
                        return updateHtml;
                    }
				},
				{ display: '排班名称', name: 'attend_pbname', width: 120 },
				{ display: '区域名称', name: 'attend_areacode', width: 120 },
                { display: '排班规则', name: 'pbrule_name', width: 120 },
             /*    { display: '状态', name: 'attend_over_name', width: 120 }, */
                { display: '审签状态', name: 'pbcheck_state_name', width: 120 },
                { display: '审签人', name: 'pbchecker_name', width: 120 },
                { display: '审签日期', name: 'pbcheck_date', width: 120 },
                { display: '备注', name: 'attend_pbnote', width: 120 },

            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                wrap: true,
                hwrap: true,
                dataModel: {
                    // url: ''
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete'},
                        { type: 'button', label: '审签', listeners: [{ click: check }], icon: 'check' },
                        { type: 'button', label: '消审', listeners: [{ click: uncheck }], icon: 'cancel' },
                       /*  { type: 'button', label: '归档排班', listeners: [{ click: submit }], icon: 'submit' },
                        { type: 'button', label: '取消归档', listeners: [{ click: cancel }], icon: 'cancel' }, */
                      
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];
                openUpdate(currentRowData);
            })
        };

        $(function () {
            initFrom();
            initGrid();
           // query();
        })
    </script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>  
				<!-- td class="label">单位信息：</td>
	            <td class="ipt">
	                <select id="hos_name" style="width:180px;" disabled></select>
	            </td-->
	            <td class="label">排班日期：</td>
                <td class="ipt">
                    <input id="pb_date" type="text" />
                </td>
				<td class="label">区域名称：</td>
				<td> <select id="attend_areacode" style="width: 180px;"></select>
				</td>
				<td class="label">状态：</td>
				<td>
				<select id="state" style="width: 180px;">
					<option value="0">0 未审签</option>
					<option value="1">1 已审签</option>
				</select>
				</td>
					
			</tr>
			<tr>
				<td class="label">排班名称：</td>
				<td class="ipt"><input id="attend_pbname" type="text"> 
				</td>
				<td class="label">职工名称：</td>
				<td> <select id="emp_id" style="width: 180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>