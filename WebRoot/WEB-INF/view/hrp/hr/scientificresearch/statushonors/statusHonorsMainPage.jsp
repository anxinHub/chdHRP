<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="dialog,datepicker,select,grid" name="plugins" />
    </jsp:include>
    <script>
        var apply_date, status_code, state;
        var grid;
        var initFrom = function () {
            apply_date = $("#apply_date").etDatepicker({
                range: true,
                onChange: query
            });
            status_code = $("#status_code").etSelect({
               url: "queryStatus.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            state = $("#state").etSelect({
                options: [
                    { id: 0, text: '新建' },
                    { id: 1, text: '已提交' },
                    { id: 2, text: '已审核' },
                ],
                defaultValue: "none",
                onChange: query
            });
        };
        var query = function () {
            params = [
                { name: 'apply_date', value: apply_date.getValue()[0] || '' },
                { name: 'end_date', value: apply_date.getValue()[1] || '' },
                { name: 'emp_name', value: $("#emp_name").val() },
                { name: 'status_code', value: status_code.getValue() },
                { name: 'note', value: $("#note").val() },
                { name: 'state', value: state.getValue() },
            ];
            grid.loadData(params, "queryStatusHonors.do");
        };
        
        var add = function () {
        	parent.$.etDialog.open({
                url: 'hrp/hr/scientificresearch/addStatusHonorsPage.do?isCheck=false',
                width: $(window).width(),
                height: $(window).height(),
                title: '添加'
            });
        };
        
        var remove = function () {
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
                param.push({
                	emp_id: item.rowData.emp_id,
                    apply_no: item.rowData.apply_no,
                    state: item.rowData.state,
                    status_code: item.rowData.status_code
                });
            })
$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
               url: 'deleteStatusHonors.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
});
        };
        var submit = function () {

            var msg="";
        	var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                    
                      var rowdata = this.rowData;
                      if(rowdata.state==0){
                      ParamVo.push(rowdata);
                      }else{
                    	  msg+="请检查状态!";
                      }
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmStatusHonors.do',
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
        var unSubmit = function () {
            var msg="";
        	var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                    
                      var rowdata = this.rowData;
                      if(rowdata.state==1){
                      ParamVo.push(rowdata);
                      }else{
                    	  msg+="请检查状态!";
                      }
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定取消提交?', function () {
                    ajaxPostData({
                        url: 'reConfirmStatusHonors.do',
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
        var audit = function () {


            var msg="";
        	var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                    
                      var rowdata = this.rowData;
                      if(rowdata.state==1){
                      ParamVo.push(rowdata);
                      }else{
                    	  msg+="请检查状态!";
                      }
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定审核?', function () {
                    ajaxPostData({
                        url: 'auditStatusHonors.do',
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
        var unAudit = function () {


            var msg="";
        	var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                    
                      var rowdata = this.rowData;
                      if(rowdata.state==2){
                      ParamVo.push(rowdata);
                      }else{
                    	  msg+="请检查状态!";
                      }
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定销审?', function () {
                    ajaxPostData({
                        url: 'unAuditStatusHonors.do',
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
        var openUpdate = function (rowData) {
        	parent.$.etDialog.open({
                url: 'hrp/hr/scientificresearch/updateStatusHonorsPage.do?isCheck=false&emp_id=' + rowData.emp_id + '&apply_no=' + rowData.apply_no + '&status_code=' + rowData.status_code,
                title: '修改',
                width: $(window).width(),
                height: $(window).height(),
            })
        };
        var initGrid = function () {
            var columns = [
                {
                    display: '申请单号', name: 'apply_no', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                { display: '摘要', name: 'note', width: 120 },
                { display: '职工工号', name: 'emp_code', width: 120 },
                { display: '职工名称', name: 'emp_name', width: 120 },
                { display: '学术地位', name: 'status_name', width: 120 },
                { display: '任职开始日期', name: 'beg_date', width: 120 },
                { display: '任职结束日期', name: 'end_date', width: 120 },
                { display: '状态', name: 'state_name', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
                dataModel: {
                     url: 'queryStatusHonors.do'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                        { type: 'button', label: '取消提交', listeners: [{ click: unSubmit }], icon: 'cancel' },
                        { type: 'button', label: '审核', listeners: [{ click: audit }], icon: 'audit' },
                        { type: 'button', label: '销审', listeners: [{ click: unAudit }], icon: 'cancel' }
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
        })
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="apply_date" type="text" />
                </td>
                <td class="label">职工名称：</td>
                <td class="ipt">
                    <input id="emp_name" type="text" />
                </td>
                <td class="label">学术地位：</td>
                <td class="ipt">
                    <select id="status_code" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">摘要：</td>
                <td class="ipt">
                    <input id="note" type="text" />
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="state" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>