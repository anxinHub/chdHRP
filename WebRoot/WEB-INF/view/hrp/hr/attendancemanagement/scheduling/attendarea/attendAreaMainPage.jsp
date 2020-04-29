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
        	  dept_code = $("#dept_code").etSelect({
                  url: "../../queryHosDeptSelect.do?isCheck=false",
                  defaultValue: "none",
                  onChange: query
              });
             
        /*   	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            }); */
        };
        var query = function () {
            var params = [
                          { name: 'dept_id', value: dept_code.getValue().split('@')[1] },
                          { name: 'attend_areacode', value: $("#attend_areacode").val()}
            ];
            grid.loadData(params,'queryAttendArea.do');
        };
    
        var add = function () {
            parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/attendAreaAddPage.do?isCheck=false',
               	width: 700,
                height: $(window).height(),
                frameName :window.name,
                resize: true,
                title: '区域设置添加'
            });
        };
    var openUpdate = function (rowData) {
            
        	parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/updateAreaAddPage.do?isCheck=false&attend_areacode='
                		+rowData.attend_areacode,
                title: '区域设置修改',
                frameName :window.name,
                width: 700,
                height: $(window).height(),
            })
        };
 var openUpdate2 = function (rowData) {
            
        	parent.$.etDialog.open({
                url: 'hrp/hr/attendancemanagement/attend/viewDept.do?isCheck=false&attend_areacode='+rowData.attend_areacode+'&attend_area_name='+rowData.attend_area_name,
                title: '区域科室查看',
                width: 700,
                height: $(window).height(),
            })
        };
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
            	   url: 'deleteAttendArea.do',
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
                title: " 区域设置打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendAreaService",
                method_name: "queryAttendAreaByPrint",
                bean_name: "hrAttendAreaService",
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
				{ display: '区域编码', name: 'attend_areacode', width: 120,editable: false,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'
                        return updateHtml;
                    }
				},
				 { display: '区域名称', name: 'attend_area_name', width: 120},
                { display: '区域科室', name: 'dept_id', width: 120, editable : false,
					 render: function (ui) {
                        var updateHtml =
            		      '<a class="openUpdate2" row-index="' + ui.rowIndx + '">查看  </a>'
            		   return updateHtml;
		                
		             } 
		             },
		        	 { display: '班次分类', name: 'attend_class_typename', width: 120},
               
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
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
         /*        var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                	parent.$.etDialog.open({
                		url:"hrp/hr/attendancemanagement/attend/attendAreaAddPage.do?isCheck=false&attend_area_name=" + currentRowData.attend_area_name,
                		isMax: true,
                	}) */

                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                openUpdate(currentRowData);
            });

            $("#mainGrid").on('click', '.openUpdate2', function () {
             var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                openUpdate2(currentRowData);
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
			<!-- <tr>  <td class="label">单位信息：</td>
            <td class="ipt">
                <select id="hos_name" style="width:180px;" disabled></select>
            </td> -->
					<td class="label">区域名称：</td>
				<td class="ipt"><input id="attend_areacode" type="text">
				</td>
				<td class="label">区域科室：</td>
				<td class="ipt"><select id="dept_code" style="width: 180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>