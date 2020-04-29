<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,hr.select,grid,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var dept_code, title_code, duty_code, level_code, emp_id;
        var grid;
        var initFrom = function () {

        	year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                onChange: query,
            });
            dept_code = $("#dept_code").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
            });
            title_code = $("#title_code").etSelect({
                url: "../queryHrTitle.do?isCheck=false",
                defaultValue: "none",
            });
            duty_code = $("#duty_code").etSelect({
                url: "../queryDuty.do?isCheck=false",
                defaultValue: "none",
            });
            level_code = $("#level_code").etSelect({
                url: "../queryDicLevel.do?isCheck=false",
                defaultValue: "none",
            });
            emp_id = $("#emp_id").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
        };
        var query = function () {
            var params = [
                    { name: 'emp_id', value: emp_id.getValue() },
                    { name: 'dept_id', value: dept_code.getValue().split("@")[1] },
                    { name: 'title_code', value: title_code.getValue() },
                    { name: 'duty_code', value: duty_code.getValue() },
                    { name: 'level_code', value: level_code.getValue() },
                    { name: 'year', value: year.getValue()}
                ];
            grid.loadData(params,"queryPeerEvaluation.do");
        };
        var save = function () {
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
            	var flag = true;
            	 $(data).each(function () {
                     var rowdata = this.rowData;
                     if(rowdata.state == 1){
                    	 flag = false;
                    	 return;
                     }
                     ParamVo.push(rowdata);
                 });
            	 if(!flag){
            		 $.etDialog.error('已提交的数据不能再次保存');
                     return;
            	 }
            	 
            	 var isPass = grid.validateTest({
     				required: {
     					emp_code :true,
     					year:true
     				}
     			})
     			if (!isPass) {
     				return;
     			}
            	 
            	//验证重复数据
              	if (!grid.checkRepeat(data, ['year','emp_code'])) {
                    return;
                }
                
            	ajaxPostData({
                     url: 'addPeerEvaluation.do',
                     data: {
                         paramVo: JSON.stringify(ParamVo),
                         year : year.getValue()
                     },
                     success: function () {
                         query();
                     }
                 })
            }
        };
        
        var add = function () {
            grid.addRow();
        };
        var submit = function () {
        	var msg = "";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.state == 1){
                    	  msg += "只能包含未提交的数据！"
                    	  return false;
                      }
                      ParamVo.push(rowdata);
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmPeerEvaluation.do',
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
        var cancel = function () {
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
            	var flag = true;
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.state == 0){
                    	  flag = false;
                    	  return false;
                      }
                      ParamVo.push(rowdata);
                  });
                  if(!flag){
                	  $.etDialog.error('只能包含新建状态的数据！');
                	  return;
                  }
                $.etDialog.confirm('确定撤回?', function () {
                    ajaxPostData({
                        url: 'reConfirmPeerEvaluation.do',
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
                    emp_id: item.rowData.emp_id,
                    state: item.rowData.state,
                });
            })
          $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deletePeerEvaluation.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
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
        			},{
        				"name" : "emp_id",
        				"display" : "职工工号",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "emp_name",
        				"display" : "职工姓名",
        				"width" : "200"
        			},{
        				"name" : "peer_score",
        				"display" : "同事评价成绩",
        				"width" : "200"
        			},{
        				"name" : "state",
        				"display" : "状态",
        				"width" : "200"
        			},{
        				"name" : "note",
        				"display" : "备注",
        				"width" : "200"
        			} ]

        		};
        		importSpreadView("/hrp/hr/nursing/importPEER.do?isCheck=false", para);
    			
        };
        <%--
        var putout = function () {
        	exportGrid(grid);
        };--%>
        var initGrid = function () {
            var yearEditor = getRecentYearForSelect();
            var columns = [
          /*       { display: '年份', name: 'year', width: 120,
                    editor: yearEditor, 
                    editable : function(col){
                    	if(col.rowData){
                    		if(col.rowData.state!=null){
                                return false;
                            }
                            return true;
                        }else{
                            return true;
                        }
                    }
                }, */
                { display: '职工工号', name: 'emp_code', width: 120,
                    editor: {
                        type: 'grid',
                        columns: [
                        	 { display: '职工工号', name: 'emp_code', width: 120 },
                             { display: '职工名称', name: 'emp_name', width: 120 },
                             { display: '职务', name: 'duty_name', width: 120 },
                             { display: '职称', name: 'title_name', width: 120 },
                             { display: '护理等级', name: 'level_name', width: 120 },
                             { display: '', name: 'duty_code', hidden: true },
                             { display: '', name: 'title_code', hidden: true },
                             { display: '', name: 'level_code', hidden: true },
                             { display: '', name: 'emp_id', hidden: true },
                        ],
                        width: '700px',
                        height: '205px',
                        dataModel: {
                            url: '../queryHrEmpData.do?isCheck=false',
                        },
                    },
                    render: function (ui) {
                        var cellData = ui.cellData;
                        var state_name = ui.rowData.state_name;
                        var tip = '';

                        if (state_name == '不合格') {
                            tip = '<span style="color:red;"> 不合格</span>'
                        }

                        return cellData + tip;
                    },
                    editable : function(col){
                        if(col.rowData){
                            if(col.rowData.state!=null){
                                return false;
                            }
                            return true;
                        }else{
                            return true;
                        }
                    }
                },
                { display: '职工名称', name: 'emp_name', width: 120, editable: false, },
                { display: '', name: 'emp_id', hidden: true, },
                { display: '护理等级', name: 'level_name', editable: false, width: 120},
                { display: '', name: 'level_code', hidden: true, },
                { display: '同事评价成绩', name: 'peer_score', width: 120 ,
                	editor: {
                		type: 'number'
                    }},
                { display: '合格/不合格', name: 'qualified_name', editable: false, width: 120 },
                { display: '状态', name: 'state_name', editable: false, width: 120 },
                { display: '备注', name: 'note',  width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                /* dataModel: {
                     url: 'queryPeerEvaluation.do?isCheck=false'
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'add' },
                        { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                        { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' }<%--,
                        { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' }--%>
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
                <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>
                <td class="label">年份：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">职务：</td>
                <td class="ipt">
                    <select id="duty_code" style="width:180px;"></select>
                </td>
                <td class="label">职称：</td>
                <td class="ipt">
                    <select id="title_code" style="width:180px;"></select>
                </td>
                <td class="label">护理等级：</td>
                <td class="ipt">
                    <select id="level_code" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>