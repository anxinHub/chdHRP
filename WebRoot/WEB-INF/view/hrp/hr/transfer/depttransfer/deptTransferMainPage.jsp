<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select, datepicker, grid, hr, dialog,pageOffice" name="plugins" />
    </jsp:include>
    <script>
        var adjust_date, dept_select, emp_select,
            kind_code, adjust_state, maker, checker,
            main_grid;
        $(function () {
            init();
            query();
        });

        function init() {
            adjust_date = $("#adjust_date").etDatepicker({
                range: true
            });

            dept_select = $("#dept_select").etSelect({
                url: '../../queryHosAftDeptSelect.do?isCheck=false',
                defaultValue: 'none'
            });

            emp_select = $("#emp_select").etSelect({
                url: '../../queryEmpSelect.do?isCheck=false',
                defaultValue: 'none'
            });

            kind_code = $("#kind_code").etSelect({
                url: '../../queryHrEmpKindSelect.do?isCheck=false',
                defaultValue: 'none'
            });

            adjust_state = $("#adjust_state").etSelect({
                options: [
                          { id: 0, text: '新建' },
                          { id: 1, text: '审核' },
                      ],
                      defaultValue: "none",
                  });

            maker = $("#maker").etSelect({
            	url : "../../queryUserSelect.do?isCheck=false",
    			defaultValue : "none",
            });

            checker = $("#checker").etSelect({
                url: '../../queryUserSelect.do?isCheck=false',
                defaultValue: 'none'
            });

            initGrid();
        }
    
        function initGrid() {
            // 基础表格参数
            var main_toolbar = {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                    { type: "button", label: '打印', icon: 'print', listeners: [{ click: print }] },
                    { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                    { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
                    { type: "button", label: '审核', icon: 'audit', listeners: [{ click: audit }] },
                    { type: "button", label: '销审', icon: 'cancel', listeners: [{ click: unAudit }] }
                ]
            };
            var main_column = [
                { display: '调动号', name: 'adjust_code', width: 130,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'
                        return updateHtml;
                    }
                },
                { display: '调动日期', name: 'adjust_date', width: 100 },
                { display: '职工编码', name: 'emp_code', width: 100 },
                { display: '职工编码', name: 'emp_id', width: 100,hidden:true },
                { display: '职工名称', name: 'emp_name', width: 100 },
                { display: '原部门', name: 'bef_dept_name', width: 100 },
                { display: '部门', name: 'aft_dept_name', width: 100 },
                { display: '状态', name: 'adjust_state_name', width: 100 },
                { display: '操作人', name: 'maker_name', width: 100 },
                { display: '操作时间', name: 'make_date', width: 100 },
                { display: '审核人', name: 'checker_name', width: 100 },
                { display: '审核时间', name: 'check_date', width: 100 },
            ];
            var main_obj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                toolbar: main_toolbar,
                columns: main_column,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
             /*    dataModel: {
                    url: 'queryHrDeptTransfer.do'
                } */
            };

            // 初始化表格
            main_grid = $("#main_grid").etGrid(main_obj);

            $("#main_grid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = main_grid.getAllData()[rowIndex];
                openUpdate(currentRowData);
            })
        }


        function query() {
            var url = 'queryHrDeptTransfer.do';
            var data = [
                { name: 'adjust_date', value: adjust_date.getValue()[0] },
                { name: 'adjust_enddate', value: adjust_date.getValue()[1] },
                { name: 'aft_dept_id', value: dept_select.getValue().split('@')[1]  },
                { name: 'emp_id', value: emp_select.getValue() },
                { name: 'kind_code', value: kind_code.getValue() },
                { name: 'adjust_state', value: adjust_state.getValue() },
                { name: 'maker', value: maker.getValue() },
                { name: 'checker', value: checker.getValue() },
                { name: 'adjust_code', value: $("#adjust_code").val() }
            ];
            main_grid.loadData(data, url);
        }
        function print() {

        	if(main_grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
            		]  */}; 
        	var printPara={
                title: " 部门调动打印",//标题
                columns: JSON.stringify(main_grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.transfer.HrDeptTransferService",
                method_name: "queryDeptTransByPrint",
                bean_name: "hrDeptTransferService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(main_grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        }
        function add() {
        	parent.$.etDialog.open({
                title: '部门调动添加',
                url: "hrp/hr/transfer/depttransfer/addHrDeptTransferPage.do?isCheck=false",
                width: $(window).width(),
                height: $(window).height(),
                frameName: window.name
            });

            //main_grid.addRow({});
        }
        var openUpdate = function (rowData) {
        	parent.$.etDialog.open({
                title: '部门调动修改',
                url: "hrp/hr/transfer/depttransfer/updateHrDeptTransferPage.do?isCheck=false&adjust_code="+rowData.adjust_code,
                width: $(window).width(),
                height: $(window).height(),
                frameName: window.name
            });
        };
        function del() {
           
        	var ParamVo = [];
            var codes = "";
            var adjust_code="";
            var data = main_grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                  $(data).each(function () {
                	  var rowdata = this.rowData;
                      if(rowdata.adjust_state != 0){
                    	  codes=codes+rowdata.adjust_code;
                      }else{
                    	  adjust_code=adjust_code+'\''+rowdata.adjust_code+"\',";
                      }
                  });
            }
            
            if(codes!=""){
            	$.etDialog.error('请选择新建的单据！');
            	return;
            }else{
                $.etDialog.confirm('确定要删除?', function () {
                    ajaxPostData({
                        url: 'deleteHrDeptTransfer.do',
                        data: {
                        	'adjust_code' : adjust_code
                        },
                        success: function () {
                        	query();
                        }
                    })
                });
            }
        }
        function audit() {
          //  var selectRows = main_grid.selectGet();
            var ParamVo = [];
          	var msg="";
            var data = main_grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
            	$(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.adjust_state!=0){
                  	  msg+="请选择新建状态";
                  	  return false;
                    }else{
                  	  ParamVo.push(rowdata);
                    }
                    
                });

            }
            
            if(msg!=""){
            	$.etDialog.error('请选择新建状态的单据！');
            	return;
            }else{
                $.etDialog.confirm('确定审核?', function () {
                    ajaxPostData({
                        url: 'auditdepttransfer.do',
                        data: {  
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        }
        function unAudit() {
        	   var ParamVo = [];
               var msg = "";
               var adjust_code="";
               var data = main_grid.selectGet();
               if (data.length == 0) {
                   $.etDialog.error('请选择行');
                   return;
               } else {
            	 	$(data).each(function () {
                        var rowdata = this.rowData;
                        if(rowdata.adjust_state!=1){
                      	  msg+="请选择审核状态";
                      	  return false;
                        }else{
                      	  ParamVo.push(rowdata);
                        }
                        
                    });
               }
               
               if(msg!=""){
               	$.etDialog.error('请选择审核状态的单据！');
               	return;
               }else{
                   $.etDialog.confirm('确定审核?', function () {
                       ajaxPostData({
                           url: 'reAuditdepttransfer.do',
                           data: {
                        		paramVo: JSON.stringify(ParamVo)
                           },
                           success: function () {
                           	 query();
                           }
                       })
                   });
               }
        }
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">调动日期：</td>
                <td class="ipt">
                    <input type="text" id="adjust_date">
                </td>
                <td class="label">调动号：</td>
                <td class="ipt">
                    <input type="text" id="adjust_code">
                </td>
                <td class="label">部门：</td>
                <td class="ipt">
                    <select name="" id="dept_select" style="width:180px;"></select>
                </td>
                <td class="label">职工：</td>
                <td class="ipt">
                    <select name="" id="emp_select" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">职工分类：</td>
                <td class="ipt">
                    <select name="" id="kind_code" style="width:180px;"></select>
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                    <select name="" id="adjust_state" style="width:180px;"></select>
                </td>
                <td class="label">操作人：</td>
                <td class="ipt">
                    <select name="" id="maker" style="width:180px;"></select>
                </td>
                <td class="label">审核人：</td>
                <td class="ipt">
                    <select name="" id="checker" style="width:180px;"></select>
                </td>
            </tr>
        </table>
        <div id="main_grid"></div>
    </div>
</body>

</html>