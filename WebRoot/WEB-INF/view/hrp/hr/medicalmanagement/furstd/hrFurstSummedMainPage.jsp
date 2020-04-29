<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,select,validate,grid,upload,datepicker,dialog,pageOffice"
		name="plugins" />
</jsp:include><title>Insert title here</title>
    <script>
    var app_date, dept_code, emp_id, oper_name, is_commit;
    var tree, grid;
    var initFrom = function () {
        app_date = $("#sum_date").etDatepicker({
            range: true
           /*  onChange: query, */
        });
        emp_id = $("#emp_id").etSelect({
			url : "../../queryEmpSelect.do?isCheck=false",
			defaultValue : "none",
		     onChange: query,

        });
        is_commit = $("#is_commit").etSelect({
            options: [
                { id: 0, text: '新建' },
                { id: 1, text: '提交' },
                { id: 2, text: '已审核' },
                { id: 3, text: '未通过' }
            ],
            defaultValue: "none",
            onChange: query,
        });
    };
    //查询
    var query = function () {
        var params = [
          { name: 'sum_date', value: app_date.getValue()[0] || '' },
          { name: 'end_date', value: app_date.getValue()[1] || '' },
          {
   			name : 'app_no',
   			value : $("#app_no").val()
   		}, {
   			name : 'emp_id',
   			value : emp_id.getValue()
   		}, 
   		{
   			name : 'furstd_hos',
   			value: $("#furstd_hos").val()
   		},
   		{
   			name : 'is_commit',
   			value: is_commit.getValue()
   		},
        ];
        grid.loadData(params,'queryFurstSummed.do');
    };
    var openUpdate = function (rowData) {
  
    		parent.$.etDialog.open({
            url: 'hrp/hr/healthadministration/furstd/updateFurstSummedPage.do?isCheck=false&app_no='+rowData.app_no+'&sum_date='+rowData.sum_date+'&emp_id='+rowData.emp_id,
            title: '修改',
           /*  width: $(window).width(),
            height: $(window).height(), */
            isMax: true,
            frameName: window.name,
        })
    };
    var add = function () {
    	parent.$.etDialog.open({
            url: 'hrp/hr/healthadministration/furstd/addHrFurstSummedPage.do?isCheck=false',
            title: '添加',
            isMax: true,
            frameName: window.name,
        });

    };
    //删除
    var remove = function () {
    	var msg="";
        var selectData = grid.selectGet();
        if (selectData.length === 0) {
            $.etDialog.error('请选择行');
            return;
        }
        var param = [];
        selectData.forEach(function (item) {
          	if(item.rowData.is_commit!=0){
        		msg+='请选择新建状态删除';
        	}else{
            param.push({
            	app_no: item.rowData.app_no,
            	emp_id: item.rowData.emp_id,
            	sum_date: item.rowData.sum_date,
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
            url: 'deleteFurstSummed.do',
            data: { paramVo: JSON.stringify(param) },
            success: function () {
                grid.deleteRows(selectData);
                // tree.reAsyncChildNodes(null, 'refresh');
            }
        })
        })
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
                    url: 'confirmHrFurstSummed.do',
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
                    url: 'reConfirmHrFurstSummed.do',
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
    // 审批
    var audit = function () {
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
            $.etDialog.confirm('确定审批?', function () {
                ajaxPostData({
                    url: 'auditHrFurstSummed.do',
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
    // 销审
    var unaudit = function () {
    	var msg="";
    	   var ParamVo = [];
           var data = grid.selectGet();
           if (data.length == 0) {
               $.etDialog.error('请选择行');
           } else {
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     if(rowdata.is_commit!=2){
                   		msg+='请选择审核状态单据';
                     	  return false;
                       }
                       ParamVo.push(rowdata);
                   });
                   if(msg!=""){
                 	  $.etDialog.error(msg);
                 	  return;
                   }
               $.etDialog.confirm('确定销审?', function () {
                   ajaxPostData({
                       url: 'unauditHrFurstSummed.do',
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
    // 未通过
    var dispass = function () {
    	var msg="";
    	   var ParamVo = [];
           var data = grid.selectGet();
           if (data.length == 0) {
               $.etDialog.error('请选择行');
           } else {
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     if(rowdata.is_commit!=2){
                   		msg+='请选择审核状态单据';
                     	  return false;
                       }
                       ParamVo.push(rowdata);
                   });
                   if(msg!=""){
                 	  $.etDialog.error(msg);
                 	  return;
                   }
               $.etDialog.confirm('确定未通过?', function () {
                   ajaxPostData({
                       url: 'dispassHrFurstSummed.do',
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
                title: " 进修总结打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrFurstSummedService",
                method_name: "queryFurstSummedByPrint",
                bean_name: "hrFurstSummedService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
   //初始化表头和功能按钮
    var initGrid = function () {
        var columns = [
            { display: '申请单号', name: 'app_no', width: 120,
                render: function (ui) {
                    var updateHtml =
                        '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                        ui.cellData +
                        '</a>'
                    return updateHtml;
                }
            },
            { display: '总结日期', name: 'sum_date', width: 120 },
            { display: '姓名', name: 'emp_name', width: 120 },
            { display: '进修医院', name: 'furstd_hos', width: 120 },
            { display: '进修导师', name: 'teacher', width: 120 },
            { display: '导师电话', name: 'tel', width: 120 },
            { display: '状态', name: 'commit_name', width: 120 },
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
                //url: ''
            },
            columns: columns,
            toolbar: {
                items: [
                    { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                    { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                    { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                    { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                    { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                    { type: 'button', label: '审批', listeners: [{ click: audit }], icon: 'audit' },
                    { type: 'button', label: '销审', listeners: [{ click: unaudit }], icon: 'unaudit' },
                    { type: 'button', label: '未通过', listeners: [{ click: dispass }], icon: 'cacnel' },
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
                <td class="label">总结日期：</td>
                <td class="ipt">
                    <input id="sum_date"  type="text" />
                </td>
                <td class="label">申请单号：</td>
                <td class="ipt">
                    <input id="app_no" type="text" />
                </td>
                <td class="label">姓名：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">进修医院：</td>
                <td class="ipt">
                    <input id="furstd_hos" type="text"/>
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                    <select id="is_commit" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>
</html>