<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,select,grid,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var dept_code, diagnose, is_same;
        var grid;
        var initFrom = function () {

            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                onChange: query
            });
            dept_code = $("#dept_code").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
                });
      
            is_same = $("#is_same").etSelect({
                options: [
                    { id: '1', text: '符合' },
                    { id: '0', text: '不符合' },
                ],
                defaultValue: "none",
                onChange: query
            });
        };
        var query = function () {
            params = [
            	{ name: 'diagnose', value: $('#diagnose').val() },
            	{ name: 'dept_code', value: dept_code.getValue() },
                { name: 'is_same', value: is_same.getValue()  },
                { name: 'year', value: year.getValue() },
            ];
            grid.loadData(params,'queryIntensiveCare.do?isCheck=false');
        };
        var save = function () {
        	var errorMsg = '';//错误提示信息
        	  var ParamVo = [];
              var data = grid.selectGet();
              if (data.length == 0) {
                  $.etDialog.error('请选择行');
                  return;
              }  
              $.each(data,function (index,obj) {
                  var rowdata = obj.rowData;
                  
                
                  if(rowdata.dept_id == '' || rowdata.dept_id == undefined){
                    	errorMsg += '勾选的第' + (index+1) + '行科室不能为空<br/>';
                    	return false;
                    }
                  ParamVo.push(rowdata);
              });
              
              
              if(errorMsg != ''){
             	 $.etDialog.error(errorMsg);
                  return;
             }
            //验证重复数据
        	if (!grid.checkRepeat(
        			data,
        			['dept_name','year']
        	)		
        	) {
               return;
           }
            ajaxPostData({
                url: 'addIntensiveCare.do?isCheck=false',
                data: {
                    paramVo: JSON.stringify(ParamVo),
                    year:year.getValue()  
                },
                success: function () {
                    query();
                }
            })
        };
        var add = function () {
            grid.addRow();
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
                	year: item.rowData.year,
                	dept_id: item.rowData.dept_id,
                	state: item.rowData.state,
                });
            })
		$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
               url: 'deleteIntensiveCare.do?isCheck=false',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                    // tree.reAsyncChildNodes(null, 'refresh');
                }
            })
		});
        };
        var putin = function () {
        	var para = {
        			"column" : [ {
        				"name" : "year",
        				"display" : "年份",
        				"width" : "200",
        				"require" : true
        			},
        			{
        				"name" : "dept_code",
        				"display" : "科室编号",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "in_hos_no",
        				"display" : "住院号",
        				"width" : "200"
        			},{
        				"name" : "patient",
        				"display" : "姓名	",
        				"width" : "200"
        			},{
        				"name" : "diagnose",
        				"display" : "诊断",
        				"width" : "200"
        			},{
        				"name" : "is_same",
        				"display" : "符合状态",
        				"width" : "200"
        			},{
        				"name" : "note",
        				"display" : "备注",
        				"width" : "200"
        			}]

        		};
        		importSpreadView("/hrp/hr/nursing/importAdmin2.do?isCheck=false", para, query);
        		
        };
        /* var putout = function () {
        	exportGrid(grid);
        }; */
        
        var initGrid = function () {
            var yearEditor = getRecentYearForSelect();
            var columns = [
           /*      { display: '年份', name: 'year', width: 120,
                    editor: yearEditor,
                    editable : function(col){
                    	if(col.rowData){
                    		if(col.rowData.group_id != null && col.rowData.hos_id != null){
                                return false;
                            }
                            return true;
                        }else{
                            return true;
                        }
                    }
                }, */
                { display: '科室', name: 'dept_name', width: 120,
                    editor: {
                        type: 'select',
                        keyField: 'dept_code',
                        url: '../queryHosDeptSelect.do?isCheck=false',
                        defaultValue: "dept_code",		
                    },
                    render: function (ui) {
                        var cellData = ui.rowData.dept_code;
                        if (cellData) {
                        	ui.rowData.dept_id = cellData.split('@')[1];
                            ui.rowData.dept_no = cellData.split('@')[0];
                        }
                    },
                    editable : function(col){
                    	if(col.rowData){
                    		if(col.rowData.group_id != null && col.rowData.hos_id != null){
                                return false;
                            }
                            return true;
                        }else{
                            return true;
                        }
                    }

                },
                { display: '住院号', name: 'in_hos_no', width: 120 },
                { display: '姓名', name: 'patient', width: 120 },
                { display: '诊断', name: 'diagnose', width: 120 },
                { display: '符合/不符合', name: 'same_name', width: 120,
                    editor: {
                        type: 'select',
                        keyField: 'is_same',
                        source: [
                            { label: '符合', id: '1' },
                            { label: '不符合', id: '0' },
                        ],
                        defaultValue: "is_same",
                    }
                },
                { display: '备注', name: 'note', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
               /*  dataModel: {
                   url: ''
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' }
                        /* { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' } */
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
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
               <td class="label">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>
                <td class="label">诊断名称：</td>
                <td class="ipt">
                    <input id="diagnose" type="text">
                </td>
              
            </tr>
            <tr>
              <td class="label">符合状态：</td>
                <td class="ipt">
                    <select id="is_same" style="width:180px;"></select>
                </td></tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>