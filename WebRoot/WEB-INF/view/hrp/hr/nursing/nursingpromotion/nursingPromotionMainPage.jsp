<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,datepicker,pageOffice" name="plugins" />
    </jsp:include>
    <script>
        var year, dept_code, emp_id, state;
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
                onChange: function (value) {
                    emp_id.reload({
                        url: '../queryEmpSelect.do?isCheck=false',
                        para: {
                            dept_code: value,
                        },
                    })
                }
            });
            emp_id = $("#emp_id").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            state = $("#state").etSelect({
                options: [
                    { id: 0, text: '新建' },
                    { id: 1, text: '提交' }
                ],
                defaultValue: "none",
                onChange: query,
            });
        };
        var query = function () {
            params = [
                { name: 'year', value: year.getValue() },
                { name: 'dept_id', value: dept_code.getValue().split("@")[1] },
                { name: 'emp_id', value: emp_id.getValue() },
                { name: 'state', value: state.getValue() }
            ];
            grid.loadData(params, 'queryNursingPromotion.do');
        };
        
        //添加
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/nursing/addNursingPromotionPage.do?isCheck=false&year='+year.getValue(),
                isMax: true,
                title: '添加',
                frameName: window.name,
            });
        };
        
        //删除
        var remove = function () {
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    param.push(rowdata);
                });

                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: 'deleteNursingPromotion.do',
                        data: {
                            paramVo: JSON.stringify(param)
                        },
                        success: function () {
                            grid.deleteRows(data);
                        }
                    })
                });
            }
        };
        //打印
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
                title: " 护理晋级申请表打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.nursing.HrNursingPromotionService",
                method_name: "queryNursingPromotionByPrint",
                bean_name: "hrNursingPromotionService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        };
        
        //提交
        var submit = function () {
        	var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.state!=0){
                    	  msg+="请选择新建状态";
                    	  return false;
                      }else{
                    	  ParamVo.push(rowdata);
                      }
                      
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmNursingPromotion.do',
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
        
        //撤回
        var cancel = function () {
         	var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.state!=1){
                    	  msg+="请选择提交状态";
                    	  return false;
                      }else{
                    	  ParamVo.push(rowdata);
                      }
                      
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定撤回?', function () {
                    ajaxPostData({
                        url: 'reConfirmNursingPromotion.do',
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
        
        //修改页面
        var openUpdate = function (rowData) {
            parent.$.etDialog.open({
                url: 'hrp/hr/nursing/updateNursingPromotionPage.do?isCheck=false&emp_id=' + rowData.emp_id + '&year=' + rowData.year,
                title: '修改',
                isMax: true,
                frameName: window.name,
            })
        };
        
        var initGrid = function () {
            var columns = [
        /*         {
                    display: '年度', name: 'year', width: 120,align:'left',
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'
                        return updateHtml;
                    }
                }, */
                { display: '科室', name: 'dept_name', width: 120,align:'left' ,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'
                        return updateHtml;
                    }},
                { display: '职工工号', name: 'emp_code', width: 120,align:'left' },
                { display: '职工姓名', name: 'emp_name', width: 120,align:'left' },
                { display: '现有阶别', name: 'cur_level_name', width: 120,align:'left' },
                { display: '现有职称', name: 'cur_title_name', width: 120,align:'left' },
                { display: '升报阶别', name: 'apply_level_name', width: 120,align:'left' },
                { display: '状态', name: 'state_name', width: 120,align:'left' }
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
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                        { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
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
    <div>
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>

                <td class="label">科室：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>

                <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
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