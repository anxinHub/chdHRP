<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,select,validate,grid,upload,datepicker,dialog,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var app_date, dept_code, emp_id, patient, state;
        var tree, grid;
        var initFrom = function () {
            app_date = $("#app_date").etDatepicker({
                range: true
            });
            dept_code = $("#dept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            state = $("#state").etSelect({
                options: [
                    { id: 0, text: '新建' },
                    { id: 1, text: '提交' }
                ],
                defaultValue: "none",
                onChange: query,
            });

        };
        var query = function () {
            var params = [
              { name: 'apply_date', value: app_date.getValue()[0] || '' },
               { name: 'end_date', value: app_date.getValue()[1] || '' },
             
           	{
          			name : 'bill_no',
          			 value: $("#bill_no").val()
          		},{
       			name : 'dept_id',
       			value : dept_code.getValue().split('@')[1] 
       		}, 
       		{
       			name : 'patient',
       			 value: $("#patient").val()
       		},
       		{
       			name : 'case_no',
       			 value: $("#case_no").val()
       		},
       		{
       			name : 'state',
       			 value: state.getValue()
       		},
       		{
       			name : 'patient',
       			 value:  $("#patient").val()
       		},
            ];
            grid.loadData(params,'queryMeetDiagApplication.do');
        };
        var add = function () {
        	parent.$.etDialog.open({
                url: 'hrp/hr/healthadministration/consultation/addMeetDiagApplicationPage.do?isCheck=false',
                title: '添加',
                width: $(window).width(),
                height: $(window).height(),
                frameName :window.name
            });
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
            		msg+='请选择新建状态删除';
            		
            		
            	}else{
                param.push({
                	bill_no: item.rowData.bill_no,
                	dept_id: item.rowData.dept_id,
                	case_no: item.rowData.case_no,
                	app_date: item.rowData.app_date,
                });
            	}
            })
      	  if(msg!=""){
          	$.etDialog.error('请选择新建状态删除' );
          	return;
          }
				$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deleteMeetDiagApplication.do',
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
                        url: 'confirmMeetDiag.do?isCheck=false',
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
                        url: 'reConfirmHrMeetDiag.do?isCheck=false',
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


            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      ParamVo.push(rowdata);
                  });
                $.etDialog.confirm('确定审批?', function () {
                    ajaxPostData({
                        url: 'auditMeetDiag.do?isCheck=false',
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
        	   var ParamVo = [];
               var data = grid.selectGet();
               if (data.length == 0) {
                   $.etDialog.error('请选择行');
               } else {
                     $(data).each(function () {
                         var rowdata = this.rowData;
                         ParamVo.push(rowdata);
                     });
                   $.etDialog.confirm('确定销审?', function () {
                       ajaxPostData({
                           url: 'unauditHrMeetDiag.do?isCheck=false',
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
        	   var ParamVo = [];
               var data = grid.selectGet();
               if (data.length == 0) {
                   $.etDialog.error('请选择行');
               } else {
                     $(data).each(function () {
                         var rowdata = this.rowData;
                         ParamVo.push(rowdata);
                     });
                   $.etDialog.confirm('确定未通过?', function () {
                       ajaxPostData({
                           url: 'dispassHrMeetDiag.do?isCheck=false',
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
                title: " 全院大会诊申请打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrMeetDiagApplicationService",
                method_name: "queryMeetDiagAppByPrint",
                bean_name: "hrMeetDiagApplicationService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
        var openUpdate = function (rowData) {
        	if(rowData.is_commit!=0){
        		  $.etDialog.error('请选择新建状态修改');
        	}else{
        		parent.$.etDialog.open({
                    url: 'hrp/hr/healthadministration/consultation/updateMeetDiagApplicationPage.do?isCheck=false&emp_id='+rowData.emp_id+'&bill_no='+rowData.bill_no+'&patient='+rowData.patient,
                title: '修改',
                width: $(window).width(),
                height: $(window).height(),
                frameName :window.name
            })}
        };
        var initGrid = function () {
            var columns = [
                { display: '会诊单号', name: 'bill_no', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                            ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                },
                { display: '申请日期', name: 'app_date', width: 120 },
                { display: '科室名称', name: 'dept_name', width: 120 },
                { display: '病案号', name: 'case_no', width: 120 },
                { display: '病人姓名', name: 'patient', width: 120 },
                { display: '床位号', name: 'bed_no', width: 120 },
                { display: '诊断', name: 'diagnose', width: 120 },
                { display: '申请原因', name: 'reason', width: 120 },
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
                  /*   url: '' */
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                        { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                      /*   { type: 'button', label: '审批', listeners: [{ click: audit }], icon: 'audit' },
                        { type: 'button', label: '销审', listeners: [{ click: unaudit }], icon: 'unaudit' },
                        { type: 'button', label: '未通过', listeners: [{ click: dispass }], icon: 'cacnel' }, */
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
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="app_date"  type="text" />
                </td>
                <td class="label">会诊单号：</td>
                <td class="ipt">
                    <input id="bill_no" type="text" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">病案号：</td>
                <td class="ipt">
                   <input id="case_no" type="text">
                </td>
                <td class="label">病人姓名：</td>
                <td class="ipt">
                    <input id="patient" type="text">
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