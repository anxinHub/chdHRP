<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,select,grid,pageOffice,datepicker" name="plugins" />
    </jsp:include>
    <script>
        var <%--dept_code, title_code, --%>duty_code, prize, emp_id;
        var tree, grid;
        var initFrom = function () {
            <%--dept_code = $("#dept_code").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
            });
            title_code = $("#title_code").etSelect({
                url: "../queryHrTitle.do?isCheck=false",
                defaultValue: "none",
            });--%>
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                // minDate: data[data.length - 1].text,
                // maxDate: data[0].text,
                onChange: query,
            });
            duty_code = $("#duty_code").etSelect({
                url: "../queryDuty.do?isCheck=false",
                defaultValue: "none",
            });
            prize = $("#prize").etSelect({
                url: "queryPrize.do?isCheck=false",
                defaultValue: "none",
                onChange: query
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
                <%--{ name: 'dept_code', value: dept_code.getValue() },
                { name: 'title_code', value: title_code.getValue() },--%>
                { name: 'duty_code', value: duty_code.getValue() },
                { name: 'prize', value: prize.getValue() },
                { name: 'year', value: year.getValue()  }
            ];
            grid.loadData(params,"queryAdministrationAbility.do");
        };

        var save = function () {
        	
        	var errorMsg = '';//错误提示信息
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            }
            
            var isPass = grid.validateTest({
 				required: {
 					emp_code :true,
 					
 				}
 			})
 			if (!isPass) {
 				return;
 			}
            
            $.each(data,function (index,obj) {
                var rowdata = obj.rowData;
                
               
                ParamVo.push(rowdata);
            });
            
            
            if(errorMsg != ''){
           	 $.etDialog.error(errorMsg);
                return;
           }
            
            //验证重复数据
          	if (!grid.checkRepeat(
          			grid.selectGet(),
          			['emp_code','year']
          	)){
                 return;
             }
            ajaxPostData({
                  url: 'addAdministrationAbility.do',
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
                    emp_id: item.rowData.emp_id,
                    state: item.rowData.state,
                });
            })
$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deleteAdministrationAbility.do',
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
        				"name" : "imtheme",
        				"display" : "改善主题",
        				"width" : "200"
        			},{
        				"name" : "prize",
        				"display" : "获奖情况",
        				"width" : "200"
        			},{
        				"name" : "note",
        				"display" : "备注",
        				"width" : "200"
        			} ]

        		};
        		importSpreadView("/hrp/hr/nursing/importAdmin.do?isCheck=false", para, query);
        };
        var putout = function () { };
        var initGrid = function () {
            var yearEditor = getRecentYearForSelect();
            var columns = [
       /*          {
                    display: '年份', name: 'year', width: 120,
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
                {
                    display: '职工工号', name: 'emp_code', width: 120,
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
                { display: '职工名称', name: 'emp_name', width: 120,
                	editable : function(col){
                    	if(col.rowData){
                    		if(col.rowData.state!=null){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return true;
                    	}
                    }},
                { display: '', name: 'emp_id', hidden: true, },
                { display: '改善主题', name: 'imtheme', width: 120 },
                { display: '职务', name: 'duty_name', width: 120, editable: false, },
                { display: '获奖情况', name: 'field_col_name', width: 120,
                    editor: {
                        type: 'select',
                        keyField : 'prize',
                        url: 'queryPrize.do?isCheck=false',
                    }
                },
                { display: '备注', name: 'note', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                /* dataModel: {
                    url: 'queryAdministrationAbility.do'
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                       // { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' }
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
    <div class="mian">
        <table class="table-layout">
            <tr>
                <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                <td class="label">职务：</td>
                <td class="ipt">
                    <select id="duty_code" style="width:180px;"></select>
                </td>
                <td class="label">年份：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">获奖情况：</td>
                <td class="ipt">
                    <select id="prize" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>