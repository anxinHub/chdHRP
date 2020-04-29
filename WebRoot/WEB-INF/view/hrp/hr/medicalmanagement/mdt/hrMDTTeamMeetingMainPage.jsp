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
    var app_date, dept_code, compere, oper_name, state;
    var tree, grid;
    var initFrom = function () {
        app_date = $("#rc_date").etDatepicker({
            range: true
        });
        compere = $("#compere").etSelect({
			url : "../../queryEmpSelect.do?isCheck=false",
			defaultValue : "none",
			onChange : query

        });
    };
    //查询
    var query = function () {
        var params = [
          { name: 'apply_date', value: app_date.getValue()[0] || '' },
          { name: 'end_date', value: app_date.getValue()[1] || '' },
          {
   			name : 'rc_no',
   			value : $("#rc_no").val()
   		}, {
   			name : 'team_name',
   			value : $("#team_name").val()
   		}, 
   		{
   			name : 'recorder',
   			value: $("#recorder").val()
   		},
   		{
   			name : 'compere',
   			value: compere.getValue()
   		},
   		{
   			name : 'title',
   			value: $("#title").val()
   		},
        ];
        grid.loadData(params,'queryMDTTeamMeeting.do');
    };
    var openUpdate = function (rowData) {
    	if(rowData.is_commit!=0){
    		  $.etDialog.error('请选择新建状态修改');
    	}else{
        parent.$.etDialog.open({
            url: 'hrp/hr/healthadministration/MDT/updateHrMDTTeamMeetingPage.do?isCheck=false&rc_no='
            		+rowData.rc_no+'&rc_date='+rowData.rc_date
            		+'&team_name='+rowData.team_name+'&title='+rowData.title,
            title: '修改',
            width: $(window).width(),
            height: $(window).height(),
            frameName :window.name
        })}
    };
    var add = function () {
        parent.$.etDialog.open({
            url: 'hrp/hr/healthadministration/MDT/addHrMDTTeamMeetingpage.do?isCheck=false',
            title: '添加',
            width: $(window).width(),
            height: $(window).height(),
            frameName :window.name
        });
    };
    //删除
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
        		return;
        	}else{
            param.push({
            	rc_no: item.rowData.rc_no,
            	rc_date: item.rowData.rc_date,
            	team_name: item.rowData.team_name,
            	title: item.rowData.title,
            });
        	}
        })
        if(msg!=""){
        	$.etDialog.error('请选择新建状态删除' );
        	return;
        }
        $.etDialog.confirm('确定删除?', function () {
        ajaxPostData({
            url: 'deleteMDTTeamMeeting.do',
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
                	 msg+="请选择未提交状态!" 
                		 return false;
                  }else{
                  ParamVo.push(rowdata);
                  }
              });
              if(msg!=""){
            	  $.etDialog.error(msg );
              	return;
              }
            $.etDialog.confirm('确定提交?', function () {
                ajaxPostData({
                    url: 'confirmHrMDTTeamMeeting.do?isCheck=false',
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
                 	 msg+="请选择提交状态!" 
                 		 return false;
                   }else{
                  ParamVo.push(rowdata);}
              });
              if(msg!=""){
            	  $.etDialog.error(msg );
              	return;
              }
            $.etDialog.confirm('确定撤回?', function () {
                ajaxPostData({
                    url: 'reConfirmHrMDTTeamMeeting.do?isCheck=false',
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
                 	 msg+="请选择提交状态!" 
                 		return false;
                   }else{
                  ParamVo.push(rowdata);}
              });
              if(msg!=""){
            	  $.etDialog.error(msg );
              	return;
              }
            $.etDialog.confirm('确定审批?', function () {
                ajaxPostData({
                    url: 'auditHrMDTTeamMeeting.do?isCheck=false',
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
                 	 msg+="请选择审批状态!" 
                 		 return false;
                   }else{
                  ParamVo.push(rowdata);}
              });
              if(msg!=""){
            	  $.etDialog.error(msg );
              	return;
              }
               $.etDialog.confirm('确定销审?', function () {
                   ajaxPostData({
                       url: 'unauditHrMDTTeamMeeting.do?isCheck=false',
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
                 	 msg+="请选择审批状态!" 
                 		 return false;
                   }else{
                  ParamVo.push(rowdata);}
              });
              if(msg!=""){
            	  $.etDialog.error(msg );
              	return;
              }
               $.etDialog.confirm('确定未通过?', function () {
                   ajaxPostData({
                       url: 'dispassHrMDTTeamMeeting.do?isCheck=false',
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
                title: " 投诉纠纷处理打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrMDTTeamMeetingService",
                method_name: "queryMDTTeamMeetingByPrint",
                bean_name: "hrMDTTeamMeetingService",
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
            { display: '记录单号', name: 'rc_no', width: 120,
                render: function (ui) {
                    var updateHtml =
                        '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                        ui.cellData +
                        '</a>'
                    return updateHtml;
                }
            },
            { display: '团队名称', name: 'team_name', width: 120 },
            { display: '记录人', name: 'recorder', width: 120 },
            { display: '主持人', name: 'emp_name', width: 120 },
            { display: '会议日期', name: 'rc_date', width: 120 },
            { display: '讨论主题', name: 'title', width: 120 },
            { display: '状态', name: 'state_name', width: 120 },
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
               // url: '?isCheck=false'
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
                <td class="label">会议日期：</td>
                <td class="ipt">
                    <input id="rc_date"  type="text" />
                </td>
                <td class="label">记录单号：</td>
                <td class="ipt">
                    <input id="rc_no" type="text" style="width:180px;"/>
                </td>
                <td class="label">团队名称：</td>
                <td class="ipt">
                    <input id="team_name" type="text" style="width:180px;"/>
                </td>
            </tr>
            <tr>
                <td class="label">记&nbsp录&nbsp人：</td>
                <td class="ipt">
                    <input id="recorder" type="text"/>
                </td>
                <td class="label">主&nbsp持&nbsp&nbsp人：</td>
                <td class="ipt">
                    <select id="compere" style="width:180px;"></select>
                </td>
                <td class="label">讨论主题：</td>
                <td class="ipt">
                    <input id="title" type="text"/>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>
</html>