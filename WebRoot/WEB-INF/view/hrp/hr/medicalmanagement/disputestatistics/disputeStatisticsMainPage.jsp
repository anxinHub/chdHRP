<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,select,grid,datepicker,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var occ_date, plaint_date, plainter, finder;
        var grid;
        var initFrom = function () {
            occ_date = $("#occ_date").etDatepicker({
            	onChange: query,
            });
            plaint_date = $("#plaint_date").etDatepicker({
            	onChange: query,
            });
        };
        var query = function () {
            var params = [
                         // { name: 'occ_date', value: occ_date.getValue('yyyy-mm-dd') },
                          { name: 'plaint_date', value: plaint_date.getValue('yyyy-mm-dd')},
                          { name: 'plainter', value: $("#plainter").val() },
                          { name: 'patient', value: $("#patient").val() },
                          { name: 'in_hos_no', value: $("#in_hos_no").val() },
                         // { name: 'plaint_tel', value: $("#plaint_tel").val() },
                          { name: 'content', value: $("#content").val() },
            ];
            grid.loadData(params,'queryDisputeStatistics.do');
        };
      
        var add = function () {
        	 grid.addRow();
        };
        // 提交
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
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmPlaint.do',
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
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'reConfirmPlaint.do',
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
                title: " 赔款处理统计表打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrDisputeStatisticsService",
                method_name: "queryDisputeStatisticsByPrint",
                bean_name: "hrDisputeStatisticsService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
        var putout = function () {
        	exportGrid(grid);
        };
       
        var initGrid = function () {
            var columns = [
                { display: '住院号或门诊号', name: 'in_hos_no', width: 120 },
                { display: '病人姓名', name: 'patient', width: 120 },
                { display: '性别', name: 'sex_name', width: 120 },
                { display: '年龄', name: 'age', width: 120 },
                { display: '投诉人', name: 'plainter', width: 120 },
                { display: '投诉日期', name: 'plaint_date', width: 100},
                { display: '投诉内容', name: 'content', width: 120 },
                { display: '处理时间', name: 'deal_date', width: 120 },
                { display: '责任科室', name: 'dept_name', width: 120 },
                 { display: '责任人', name: 'emp_name', width: 120 },
                 { display: '责任人性质', name: 'emp_nature', width: 120 },
                 { display: '处理方式', name: 'deal_type', width: 120 },
                 { display: '赔款金额', name: 'damage', width: 120 },
                 { display: '处理意见', name: 'note', width: 120 },
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
                        { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
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
            <tr>
              <td class="label">投诉日期：</td>
                <td class="ipt">
                  <input id="plaint_date" type="text" />
                </td>
                <td class="label">病人姓名：</td>
                <td class="ipt">
                    <input id="patient" type="text" />
                </td>
                <td class="label" style="width:120px;">住院号/门诊号：</td>
                <td class="ipt">
                    <input id="in_hos_no" type="text" />
                </td>
            </tr>
            <tr>
                
                <td class="label">投诉人：</td>
                <td class="ipt">
                  <input id="plainter" type="text" />
                </td>
             
            <td class="label">投拆内容：</td>
                <td class="ipt">
                      <input id="content" type="text" />
                </td></tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>