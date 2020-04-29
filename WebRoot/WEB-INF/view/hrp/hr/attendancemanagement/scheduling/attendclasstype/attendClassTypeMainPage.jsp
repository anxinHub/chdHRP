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
           
       /*    	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            }); */
        };
        function query () {
            var params = [
                          { name: 'attend_class_typecode', value: $("#attend_class_typecode").val() }
                    
            ];
            grid.loadData(params,'queryAttendClassType.do');
        };
       
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/attendClassTypeAddPage.do?isCheck=false',
                width: 400,
                height: 200,
               frameName :window.name,
                title: '班次分类添加'
            });
        };
    var openUpdate = function (rowData) {
            
        	parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/updateAttendClassTypePage.do?isCheck=false&attend_class_typecode='
                		+rowData.attend_class_typecode,
                title: '班次分类修改',
                frameName :window.name,
                width: 400,
                height: 200,
            })
        };
        var remove = function () {
        	 var selectData = grid.selectGet();
             if (selectData.length === 0) {
                 $.etDialog.error('请选择行');
                 return;
             }
         	var ParamVo = [];
         	selectData.forEach(function (item) {
                   ParamVo.push({
                	   attend_class_typecode: item.rowData.attend_class_typecode
                		   
                   });
               });
             $.etDialog.confirm('确定删除?', function () {
             ajaxPostData({
             	   url: 'deleteAttendClassType.do',
                 data: { paramVo: JSON.stringify(ParamVo) },
                 success: function () {
                     grid.deleteRows(selectData);
                 }
             }) });
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
                title: " 班次分类打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendClassTypeService",
                method_name: "queryAttendClassTypeByPrint",
                bean_name: "hrAttendClassTypeService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
      /*   var putout = function () {
        	exportGrid(grid);
        }; */
        
        var initGrid = function () {
            var columns = [
				{ display: '班次分类编码', name: 'attend_class_typecode', width: 120,editable: false,
					render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                ui.cellData +
                            '</a>'

                        return updateHtml;
                    } 

				},
				 { display: '班次分类名称', name: 'attend_class_typename', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: false,
                wrap: true,
                hwrap: true,
                dataModel: {
                    // url: ''
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
            	 var rowIndex = $(this).attr('row-index');
                 var currentRowData = grid.getDataInPage()[rowIndex];
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
			<tr>  <!-- <td class="label">单位信息：</td>
            <td class="ipt">
                <select id="hos_name" style="width:180px;" disabled></select>
            </td> -->
					<td class="label">班次分类：</td>
				<td> <input id="attend_class_typecode" type="text">
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>