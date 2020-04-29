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
        var bad_eval, finder;
        var grid;
        var initFrom = function () {
        	attend_areacode=$("#attend_areacode").etSelect({
                url: "queryAttendAreacode.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
           
         /*  	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            }); */
        
        };
        var query = function () {
            var params = [
                          { name: 'attend_pbname', value: $("#attend_pbname").val() },
                          { name: 'attend_areacode', value: attend_areacode.getValue() }
                         
            ];
            grid.loadData(params,'queryCountersign.do');
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
                $.etDialog.confirm('确定审核?', function () {
                    ajaxPostData({
                        url: 'auditCountersign.do?isCheck=false',
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
                        url: 'unauditCountersign.do?isCheck=false',
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
                url: 'hrp/hr/attendancemanagement/attend/updateCountersignPage.do?isCheck=false&attend_pbcode='
                		+rowData.attend_pbcode,
                title: '排班查看',
                width: $(window).width(),
                height: $(window).height(),
            })
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
	                { display: '排班规则', name: 'pbrule_name', width: 120 },
	                { display: '审签状态', name: 'pbcheck_state_name', width: 120 },
	                { display: '审签人', name: 'pbchecker_name', width: 120 },
	                { display: '审签日期', name: 'pbcheck_date', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                wrap: true,
                hwrap: true,
                dataModel: {
                    // url: ''
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '核定', listeners: [{ click: check }], icon: 'unsubmit' },
                        { type: 'button', label: '取消核定', listeners: [{ click: uncheck }], icon: 'cancel' },
                        
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
            query();
        })
    </script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr> <!--  <td class="label">单位信息：</td>
            <td class="ipt">
                <select id="hos_name" style="width:180px;" disabled></select>
            </td> -->
					<td class="label">区域名称：</td>
				<td> <select id="attend_areacode" style="width: 180px;"></select>
				</td>
				<td class="label">排班名称：</td>
				<td class="ipt"><input id="attend_pbname" type="text"> 
				</td>
				
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>