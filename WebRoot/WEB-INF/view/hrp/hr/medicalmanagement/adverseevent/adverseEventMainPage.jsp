<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,select,grid,datepicker,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var occ_date, occ_dept_id, emp_id, finder;
        var grid;
        var initFrom = function () {
            occ_date = $("#occ_date").etDatepicker({
            	onChange: query,
            });
            occ_dept_id = $("#occ_dept_id").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            emp_id = $("#emp_id").etSelect({
                url: "../../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            finder = $("#finder").etSelect({
                url: "../../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
        };
        var query = function () {
            var params = [
                          { name: 'occ_date', value: occ_date.getValue('yyyy-mm-dd') },
                          { name: 'occ_dept_id', value: occ_dept_id.getValue().split('@')[1] },
                          { name: 'emp_id', value: emp_id.getValue() },
                          { name: 'finder', value: finder.getValue() },
                          {name : 'patient', value: $("#patient").val()},
                          {name : 'in_hos_no', value: $("#in_hos_no").val()},

            ];
            grid.loadData(params,'queryAdverseEvent.do');
        };
        var save = function () {
        	var ParamVo = [];
     	   var isPass = grid.validateTest({
               required: {
            	   occ_date: true,
            	   occ_dept_name: true,
            	   emp_name: true,
            	   in_hos_no: true
               }
           });
           if (!isPass) {
               return;
           }
            var data = grid.selectGet();
                 if (data.length == 0) {
                     $.etDialog.error('请选择行');
                     return;
                 } else {
                       $(data).each(function () {
                           var rowdata = this.rowData;
                           if(rowdata.dept_code!=null && rowdata.occ_dept_id==null){
                          	 rowdata.occ_dept_id = rowdata.dept_code.split('@')[1]
                          }
                           ParamVo.push(rowdata);
                       }) 
                       }
                 //验证重复数据
             	if (!grid.checkRepeat(
             			grid.selectGet(),
             			['occ_date','occ_dept_name','emp_name','in_hos_no']
             	)		
             	) {
                    return;
                }
         
            ajaxPostData({
                url: 'addAdverseEvent.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	query()
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
                param.push(item.rowData);
            });
            
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteAdverseEvent.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
            })
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
                title: " 不良事件打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrAdverseEventService",
                method_name: "queryAdverseEventByPrint",
                bean_name: "hrAdverseEventService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        };
        var putout = function () {
        	exportGrid(grid);
        };
        var putin = function () {

    		//$("form[name=fileForm]").submit();
    		var para = {
    			"column" : [ {
    				"name" : "occ_date",
    				"display" : "发生日期",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "occ_dept_id",
    				"display" : "发生科室",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "emp_id",
    				"display" : "当事者",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "in_hos_no",
    				"display" : "住院号/门诊号",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "patient",
    				"display" : "病人姓名",
    				"width" : "200"
    			},{
    				"name" : "sex_code",
    				"display" : "性别",
    				"width" : "200"
    			},{
    				"name" : "age",
    				"display" : "年龄",
    				"width" : "200"
    			},{
    				"name" : "diagnose",
    				"display" : "诊断",
    				"width" : "200"
    			},{
    				"name" : "content",
    				"display" : "内容",
    				"width" : "200"
    			},{"name" : "finder",
    				"display" : "发现者",
    				"width" : "200"
    			},{"name" : "dept_opinion",
    				"display" : "科室定性意见",
    				"width" : "200"
    			},{"name" : "dept_aff_date",
    				"display" : "科室定性时间",
    				"width" : "200"
    			},{
    				"name" : "hos_aff_date",
    				"display" : "院部定性时间",
    				"width" : "200"
    			},{"name" : "mmd_aff_date",
    				"display" : "医务部定性时间",
    				"width" : "200"
    			},{"name" : "reper",
    				"display" : "上报人",
    				"width" : "200"
    			} ]

    		};
    		importSpreadView("/hrp/hr/privilegemanagement/medicalsafety/importAdverseEvent.do", para, query);
        };
       
        var initGrid = function () {
            var columns = [
                           { display: '发生科室', name: 'occ_dept_name', width: 120,
                           	editor: {
                                   type: 'select',
                                   keyField: 'dept_code',
                                   url:'../../queryHosDeptSelect.do?isCheck=false',
                                   change: function (value) {
                                   	grid.getColumns()[3].editor.url ='../../queryEmpSelect.do?isCheck=false&dept_code='+value.dept_code;
                                   	grid.getColumns()[10].editor.url ='../../queryEmpSelect.do?isCheck=false&dept_code='+value.dept_code;
                                   	grid.getColumns()[15].editor.url ='../../queryEmpSelect.do?isCheck=false&dept_code='+value.dept_code;
                                   }
                                   

                               },
                               editable : function(col){
                               	if(col.rowData){
                               		if(col.rowData.hos_id!=null){
                               			return false;
                               		}
                               		return true;
                               	}else{
                               		return true;
                               	}
                               } },
                           { display: '住院号或门诊号', name: 'in_hos_no', width: 120,
                                   editable : function(col){
                                   	if(col.rowData){
                                   		if(col.rowData.hos_id!=null){
                                   			return false;
                                   		}
                                   		return true;
                                   	}else{
                                   		return true;
                                   	}
                                   } },
                                   { display: '当事者', name: 'emp_name', width: 120,	
                                   	editor: {
                                           type: 'select',
                                           keyField: 'emp_id',
                                           url:'../../queryEmpSelect.do?isCheck=false',
                                       },
                                       editable : function(col){
                                       	if(col.rowData){
                                       		if(col.rowData.hos_id!=null){
                                       			return false;
                                       		}
                                       		return true;
                                       	}else{
                                       		return true;
                                       	}
                                       }  },
                                       { display: '发生日期', name: 'occ_date', width: 100,
                                     	   editor: {
                                               type: 'date',
                                               dateFormat: 'yy-mm-dd',
                                           },
                                           editable : function(col){
                                           	if(col.rowData){
                                           		if(col.rowData.hos_id!=null){
                                           			return false;
                                           		}
                                           		return true;
                                           	}else{
                                           		return true;
                                           	}
                                           } },
                { display: '病人姓名', name: 'patient', width: 80 },
                { display: '性别', name: 'sex_name', width: 80 ,	
                	editor: {
                        type: 'select',
                        keyField: 'sex_code',
                        url:'../../queryDicSexSelect.do?isCheck=false',
                    }  
                	},
                { display: '年龄', name: 'age', width: 80 },
            
                { display: '诊断', name: 'diagnose', width: 120 },
             
                { display: '差错及不良事件发生经过情况及后果', name: 'content', width: 120 },
           
                { display: '发现者', name: 'finder_name', width: 120,	
                    	editor: {
                            type: 'select',
                            keyField: 'finder',
                            url:'../../queryEmpSelect.do?isCheck=false',
                        }  },
                { display: '科室定性意见', name: 'dept_opinion', width: 120 },
                { display: '定性时间（科室）', name: 'dept_aff_date', width: 100,
             	   editor: {
                       type: 'date',
                       dateFormat: 'yy-mm-dd',
                   } },
                { display: '院部定性时间', name: 'hos_aff_date', width: 120,
                 	   editor: {
                           type: 'date',
                           dateFormat: 'yy-mm-dd',
                       }  },
                { display: '定性时间(医务部)', name: 'mmd_aff_date', width: 120,
             	   editor: {
                       type: 'date',
                       dateFormat: 'yy-mm-dd',
                   } },
                { display: '上报人', name: 'reper_name', width: 120,	
                	editor: {
                        type: 'select',
                        keyField: 'reper',
                        url:'../../queryEmpSelect.do?isCheck=false',
                    }  },
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
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                       /*  { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' }, */
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
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
                <td class="label">发生日期：</td>
                <td class="ipt">
                    <input id="occ_date" type="text" />
                </td>
                <td class="label">病人姓名：</td>
                <td class="ipt">
                    <input id="patient" type="text" />
                </td>
                <td class="label" style="width:120px;">住院号/门诊号：</td>
                <td class="ipt">
                    <input id="in_hos_no" type="text" />
                </td>
            </tr>
            <tr>
                <td class="label">发生科室：</td>
                <td class="ipt">
                    <select id="occ_dept_id" style="width:180px;"></select>
                </td>
                <td class="label">当事者：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                <td class="label">发现者：</td>
                <td class="ipt">
                    <select id="finder" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>