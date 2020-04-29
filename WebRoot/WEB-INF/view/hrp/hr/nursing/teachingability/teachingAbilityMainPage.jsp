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
        var teaching_type, emp_id;
        var tree, grid;
        var initFrom = function () {
        	teach_date = $("#teach_date").etDatepicker({
              	range : true,
                  onChange: query,
                  defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
              });
            teaching_type = $("#teaching_type").etSelect({
                url: "queryTeachType.do?isCheck=false",
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
                { name: 'teach_type', value: teaching_type.getValue() },
                { name: 'teach_begin_date', value: teach_date.getValue()[0] },
                { name: 'teach_end_date', value: teach_date.getValue()[1] },
            ];
            grid.loadData(params,"queryTeachingAbility.do");
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
 					year:true
 				}
 			})
 			if (!isPass) {
 				return;
 			}
            		
            $.each(data,function (index,obj) {
            	
                var rowdata = obj.rowData;
                if(rowdata.teach_date == ''){
                	errorMsg += '第' + (index+1) + '行教学日期不能为空<br/>';
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
        			['teach_date','emp_code']
        	)		
        	) {
               return;
           }
              ajaxPostData({
                  url: 'addTeachingAbility.do',
                  data: {
                      paramVo: JSON.stringify(ParamVo)
                  },
                  success: function () {
                  	 query();
                  }
              });
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
                    teach_date: item.rowData.teach_date,
                    emp_id: item.rowData.emp_id,
                });
            })
$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deleteTeachingAbility.do',
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
        				"name" : "teach_date",
        				"display" : "教学日期",
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
        				"name" : "teach_type",
        				"display" : "教学种类",
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
        		importSpreadView("/hrp/hr/nursing/importTeach.do?isCheck=false", para, query);
    			
        };
        var putout = function () { };
        
        var initGrid = function () {
            var columns = [
                {
                    display: '教学日期', name: 'teach_date', width: 120,
                    editor: {
                        type: 'date',
                        dateFormat: 'yymmdd',
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
                    },editable : function(col){
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
                { display: '职工名称', name: 'emp_name', width: 120,editable : function(col){
                	if(col.rowData){
                		if(col.rowData.state!=null){
                			return false;
                		}
                		return true;
                	}else{
                		return true;
                	}
                } },
                { display: '', name: 'emp_id', hidden: true, },
                {
                    display: '教学种类', name: 'teach_type_name', width: 120,
                    editor: {
                        type: 'grid',
                        columns: [
                            { display: '编码', name: 'teach_type', width: 120 },
                            { display: '名称', name: 'teach_type_name', width: 120 },

                        ],
                        width: '700px',
                        height: '205px',
                        dataModel: {
                            url: 'queryHrTeachType.do?isCheck=false',
                        },                    
                }
                },
                { display: '备注', name: 'note', width: 120, }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                /* dataModel: {
                    url: 'queryTeachingAbility.do'
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                      //  { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' }
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
                <td class="label">教学日期：</td>
                <td class="ipt">
                    <input id="teach_date" type="text" />
                </td>
                <td class="label">教学种类：</td>
                <td class="ipt">
                    <select id="teaching_type" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>