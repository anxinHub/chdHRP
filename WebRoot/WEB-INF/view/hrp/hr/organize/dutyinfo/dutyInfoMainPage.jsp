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
        var grid,duty_code;
        var query = function () {
            params = [
                { name: 'key', value: $("#duty_code").val() }
            ];
            grid.loadData(params,'queryHosDuty.do?isCheck=false');
        };
        var add = function () {
            $.etDialog.open({
                url: 'dutyInfoAddPage.do?isCheck=false',
                width: 450,
                height: 450,
                title: '添加',
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.save()
                }
            });
        };
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
                        url: 'deleteHosDuty.do',
                        data: { paramVo: JSON.stringify(param) },
                        success: function () {
                            grid.deleteRows(data);
                        }
                        })
                });
                }
        };
        
        function print() {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	console.log(grid.getUrlParms());
        	var heads = {};
        	
        	var printPara={
          		title: "职务信息",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.hr.service.organize.HosDutyInfoService",
       			bean_name: "hosDutyInfoService",
       			method_name: "queryHrDutyPrint",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: ''//表尾需要打印的查询条件,可以为空 
           	};
        	
           	$.each(grid.getUrlParms(),function(i,obj){
         		printPara[obj.name]=obj.value;
           	}); 
           	console.log(printPara);
           	officeGridPrint(printPara);
        }

        
        
        var putout = function () {};
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'dutyInfoUpdatePage.do?isCheck=false&duty_code='+openParam.duty_code,
                title: '修改',
                width: 450,
                height: 450,
                btn: [ '保存', '取消' ],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];

                    iframeWindow.save();
                }
            })
        };
        var initGrid = function () {
            var columns = [
                { display: '职务编码', name: 'duty_code', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                ui.cellData +
                            '</a>'
                        return updateHtml;
                    }
                },
                { display: '职务名称', name: 'duty_name', width: 120 },
                { display: '职务类别', name: 'kind_name', width: 120 },
                { display: '职务等级', name: 'duty_level_name', width: 120 },
                {
                    display: '是否停用',
                    name: 'is_stop_name',
                    width: 120,
                    render: function (ui) {
                        var cellData = ui.cellData;
                        if (cellData === "是") {
                            return  '<span style="color:red;"> 是</span>';
                        } else {
                            return '否';

                        }
                    }    
                },
                
                { display: '备注', name: 'note', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        duty_code: rowData.duty_code
                    };

                    openUpdate(openParam);
                },
              
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                        { type: 'button', label: '导入', listeners: [{ click: importDate }], icon: 'import' }/* ,
                        { type: 'button', label: '导出', listeners: [{ click: exportMainGrid }], icon: 'export' } */
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];

                var openParam = {
                    duty_code: currentRowData.duty_code
                };
                openUpdate(openParam);
            })
        };

        $(function () {
            initGrid();
            query();
        })
        
 		function exportMainGrid(){
            	exportGrid(grid);
            }
        //导入数据
        function importDate(){
    		//$("form[name=fileForm]").submit();
    		var para = {
    			"column" : [ {
    				"name" : "duty_code",
    				"display" : "职务编码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "duty_name",
    				"display" : "职务名称",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "kind_code",
    				"display" : "职务类别信息",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "duty_level_code",
    				"display" : "职务等级信息",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "is_stop",
    				"display" : "是否停用",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			} ]

    		};
    		importSpreadView("/hrp/hr/duty/importDateHHD.do?isCheck=false", para, query); 
    	}
    </script>
</head>

<body>
    <div class="mian">
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">职务信息：</td>
                    <td class="ipt">
                        <input id="duty_code" type="text" />
                    </td>
                </tr>
            </table>
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>

