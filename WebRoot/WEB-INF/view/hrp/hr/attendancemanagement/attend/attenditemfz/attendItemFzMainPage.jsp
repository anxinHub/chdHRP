<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select,grid,dialog" name="plugins" />
    </jsp:include>
    <script>
        var unit_info;
        var grid;
        var initFrom = function () {
        	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            });

        };
        var query = function () {
            params = [
            	 { name: 'hos_id', value: hos_name.getValue() },
                 { name: 'attend_code_fz', value: $("#attend_code_fz").val() },
                 { name: 'attend_name_fz', value: $("#attend_name_fz").val() }
             
            ];
            grid.loadData(params);
        };
        var add = function () {
  
                parent.$.etDialog.open({
                      url: 'hrp/hr/attendancemanagement/attend/hraddAttendItemFzPage.do?isCheck=false',
                      width: 400,
                      height: 400,
                      frameName :window.name,
                      title: '考勤项目分组添加'
                  });
        };
        var openUpdate = function (rowData) {
            
            parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/updateAttendItemFzPage.do?isCheck=false&attend_code_fz='
                		+rowData.attend_code_fz,
                title: '考勤项目分组修改',

                width: 400,
                height: 400,

                frameName :window.name,
            })
        };
        /* var save = function () {
        	var ParamVo = [];
        	   var isPass = grid.validateTest({
                   required: {
                	   attend_name: true
                   }
               });
               if (!isPass) {
                   return;
               }
         var data = grid.getAllData();
             if (data.length == 0) {
                  $.etDialog.error('请选择行');
                  return;
              } else {
                    $(data).each(function () {
                        var rowdata = this.rowData;
                      
                        ParamVo.push(rowdata);
                    }) 
                    }
              //验证重复数据
          	if (!grid.checkRepeat(
          			data,
          			['attend_code','attend_name']
          	)		
          	) {
                 return;
             } 
            ajaxPostData({
                url: 'addAttendItemFz.do',
                data: {
                    paramVo:  JSON.stringify(data)
                },
                success: function () {
                }
            })
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
                 url: 'deleteAttendItemFz.do',
                data: { paramVo: JSON.stringify(ParamVo) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            }) });
        };
 
        var initGrid = function () {
            var columns = [
                { display: '项目分组编码', name: 'attend_code_fz', width: 120,editable: false,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'
                        return updateHtml;
                    }},
                { display: '项目分组名称', name: 'attend_name_fz', width: 120 },
                { display: '是否在考勤结果是否显示', name: 'attend_result_is_fzname', width: 160 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                //editable: true,
                showBottom:false,
                dataModel: {
                     url: 'queryAttendItemFz.do'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        /* { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' }, */
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' }
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
    <table class="table-layout">
        <tr>
            <td class="label">单位信息：</td>
            <td class="ipt">
                <select id="hos_name" style="width:180px;" disabled></select>
            </td>
            <td class="label">项目分组编码：</td>
            <td class="ipt">
                <input id="attend_code_fz" type="text" />
            </td>
            <td class="label">项目分组名称：</td>
            <td class="ipt">
                <input id="attend_name_fz" type="text" />
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>