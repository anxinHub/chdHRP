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
        var traning_date, state;
        var grid;
        var initFrom = function () {
            traning_date = $("#traning_date").etDatepicker({
            	range : true,
                onChange: query,
                defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
            });
            $("#class_name").on('blur', query);
            state = $("#state").etSelect({
                options: [
                    { id: 0, text: '新建' },
                    { id: 1, text: '提交' }
                ],
                defaultValue: "none",
                onChange: query,
            });
        };
        //查询
        var query = function () {
            params = [
                { name: 'traning_begin_date', value: traning_date.getValue()[0] },
                { name: 'traning_end_date', value: traning_date.getValue()[1] },
                { name: 'state', value: state.getValue() },
                { name: 'classs_name', value: $('#classs_name').val() }
            ];
            grid.loadData(params,'queryInserviceEducation.do');
        };
        //添加
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/nursing/addInserviceEducationPage.do?isCheck=false',
                height: 600,
                width: 1000,
                frameNameObj: { 'index': window.name },
                title: '添加培训'
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
                         url: 'deleteInserviceEducation.do',
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
              		title: " 年度在职教育培训打印",//标题
              		columns: JSON.stringify(grid.getPrintColumns()),//表头
              		class_name:"com.chd.hrp.hr.service.nursing.HrInserviceEducationService",
           			method_name: "queryInserviceEducationByPrint",
           			bean_name: "hrInserviceEducationService",
           			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
           			foots: '',//表尾需要打印的查询条件,可以为空 
               	};
        	  $.each(grid.getUrlParms(),function(i,obj){
                  printPara[obj.name]=obj.value;
              }); 
             	console.log(printPara);
            	officeGridPrint(printPara);
        };
        
        //提交
        var submit = function () {
            var ParamVo = [];
            var data = grid.selectGet();
            var nos = "";
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      
                      if(rowdata.state != 0 ){
                    	  nos=nos+rowdata.edu_date+",";
                      }else{
                    	  ParamVo.push(rowdata);
                      }
                  });
            }
            
            if(nos!=""){
            	$.etDialog.error('请选择新建状态的单据！');
            }else{
            	$.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmInserviceEducation.do',
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
            var ParamVo = [];
            var nos = "";
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.state != 1 ){
                    	  nos=nos+rowdata.edu_date+",";
                      }else{
                    	  ParamVo.push(rowdata);
                      }
                  });
            }
            if(nos!=""){
            	$.etDialog.error('请选择提交状态的单据！');
            }else{
                $.etDialog.confirm('确定撤回?', function () {
                    ajaxPostData({
                        url: 'reConfirmInserviceEducation.do',
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
                url: 'hrp/hr/nursing/updateInserviceEducationPage.do?isCheck=false&edu_date='+rowData.edu_date+ '&class_name=' + rowData.classs_name,
                height: 600,
                width: 1000,
                frameNameObj: { 'index': window.name },
                title: '修改'
            });
        };
        
        var initGrid = function () {
            var columns = [
                { display: '培训日期', name: 'edu_date', width: 120,align:'left',
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' + ui.cellData +'</a>'
                        return updateHtml;
                    }
                },
                { display: '培训课程', name: 'classs_name', width: 120,align:'left' },
                { display: '培训地点', name: 'place', width: 120,align:'left' },
                { display: '学时数', name: 'hours', width: 120,align:'right' },
                { display: '授课人', name: 'teacher_name', width: 120,align:'left' },
                { display: '学员人数', name: 'student_num', width: 120,align:'right' },
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
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'add' },
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
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">培训日期：</td>
                <td class="ipt">
                    <input id="traning_date" type="text" />
                </td>
                <td class="label">课程名称：</td>
                <td class="ipt">
                    <input id="classs_name" type="text" />
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