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
              emp_id = $("#emp_id").etSelect({
                  url: "../../queryEmpSelect.do?isCheck=false",
                  defaultValue: "none",
                  onChange: query
              });
           
        };
        var query = function () {
            var params = [
                          { name: 'dept_id', value: dept_code.getValue().split('@')[1] },
                          { name: 'emp_id', value: emp_id.getValue() }
            ];
            grid.loadData(params,'queryClinc.do');
        };
        var save = function () {
        	var ParamVo = [];
        	   var isPass = grid.validateTest({
                   required: {
                	   dept_name: true,
                	   emp_name: true,
                	   out_days: true,
                	   out_ratio: true
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
                        if(rowdata.dept_code!=null){
                        	 rowdata.dept_id = rowdata.dept_code.split('@')[1]
                        }
                        ParamVo.push(rowdata);
                    }) 
                    }
              //验证重复数据
          	if (!grid.checkRepeat(
          			grid.selectGet(),
          			['dept_name','emp_name']
          	)		
          	) {
                 return;
             }
            ajaxPostData({
                url: 'addClinc.do',
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
        // 提交
        var submit = function () {
           var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.is_commit!=0){
                  		msg+='请选择新建状态单据';
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
                        url: 'confirmClinc.do',
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
        // 撤回
        var cancel = function () {
             var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.is_commit!=1){
                    		msg+='请选择提交状态单据';
                      	  return false;
                        }
                        ParamVo.push(rowdata);
                    });
                    if(msg!=""){
                  	  $.etDialog.error(msg);
                  	  return;
                    }
                $.etDialog.confirm('确定撤回?', function () {
                    ajaxPostData({
                        url: 'reConfirmClinc.do',
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
        	var msg=""
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
            	if(item.rowData.is_commit!=0){
            		/*   $.etDialog.error( */msg+='请选择新建状态删除';
            		
            		
            	}else{
                param.push({
                	dept_id: item.rowData.dept_id,
                	emp_id: item.rowData.emp_id,
                	is_commit: item.rowData.is_commit,
                });
            	}
            })
        	
			  if(msg!=""){
      	$.etDialog.error('请选择新建状态删除' );
      	return;
      }
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteClinc.do',
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
                title: " 门诊能力打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrClincService",
                method_name: "queryClincByPrint",
                bean_name: "hrClincService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
     /*    var putout = function () {
        	exportGrid(grid);
        }; */
        var putin = function () {

    		//$("form[name=fileForm]").submit();
    		var para = {
    			"column" : [ {
    				"name" : "emp_id",
    				"display" : "姓名",
    				"width" : "200",
    				"require" : true
    			},
    			 {
    				"name" : "dept_id",
    				"display" : "科室",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "out_days",
    				"display" : "出诊天数",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "out_ratio",
    				"display" : "出诊率",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "order_ratio",
    				"display" : "预约率",
    				"width" : "200"
    			},{
    				"name" : "good_eval",
    				"display" : "微信好评",
    				"width" : "200"
    			},{
    				"name" : "bad_eval",
    				"display" : "微信差评",
    				"width" : "200"
    			},{
    				"name" : "per_charge",
    				"display" : "人均费用",
    				"width" : "200"
    			},{
    				"name" : "med_ratio",
    				"display" : "药占比",
    				"width" : "200"
    			},{"name" : "is_commit",
    				"display" : "是否提交",
    				"width" : "200"
    			}]

    		};
    		importSpreadView("/hrp/hr/healthadministration/clinc/importClinc.do", para);
        };
       
        var initGrid = function () {
            var columns = [
				{ display: '科室', name: 'dept_name', width: 120,
					editor: {
				    type: 'select',
				    keyField: 'dept_code',
				    url:'../../queryHosDeptSelect.do?isCheck=false',
				    change: function (value) {
				    	grid.getColumns()[2].editor.url ='../../queryEmpSelect.do?isCheck=false&dept_code='+value.dept_code;
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
               }
				},
				         { display: '姓名', name: 'emp_name', width: 120,	
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
	                               } },
                { display: '出诊天数', name: 'out_days', width: 120,dataType: "integer",render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 1, 1); }},
                { display: '出诊率', name: 'out_ratio', width: 120, dataType: "integer",render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 1, 1); }},
                { display: '预约率', name: 'order_ratio', width: 120,dataType: "integer",render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 1, 1); }},
                { display: '微信好评', name: 'good_eval', width: 120,dataType: "integer",render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 1, 1);  }},
                { display: '微信差评', name: 'bad_eval', width: 120,dataType: "integer",render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 1, 1); }},
                { display: '人均费用', name: 'per_charge', width: 120,dataType: "integer",render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 1, 1);  }},
                { display: '药占比', name: 'med_ratio', width: 120,dataType: "integer",render: function(ui) {
                    var value = ui.cellData;
                    return formatNumber(value, 1, 1); } },
                 { display: '是否提交', name: 'commit_name', width: 120 },
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
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                        { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                        { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                      /*   { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' }, */
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
					<td class="label">科室名称：</td>
				<td class="ipt"><select id="dept_code" style="width: 180px;"></select>
				</td>
				<td class="label">职工名称：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>