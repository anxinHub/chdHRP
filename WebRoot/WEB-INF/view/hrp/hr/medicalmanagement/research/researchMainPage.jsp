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
        var occ_date, plaint_date, plainter, finder;
        var grid;
        var initFrom = function () {
            occ_date = $("#occ_date").etDatepicker({
            	onChange: query,
            });
            plaint_date = $("#plaint_date").etDatepicker({
            	onChange: query,
            });
        };
        var query = function () {
            var params = [
                          { name: 'occ_date', value: occ_date.getValue('yyyy/mm/dd') },
                          { name: 'plaint_date', value: plaint_date.getValue('yyyy/mm/dd')},
                          { name: 'plainter', value: $("#plainter").val() },
                          { name: 'patient', value: $("#patient").val() },
                          { name: 'in_hos_no', value: $("#in_hos_no").val() },
                          { name: 'plaint_tel', value: $("#plaint_tel").val() },
                          { name: 'content', value: $("#content").val() },
            ];
            grid.loadData(params,'queryResearch.do');
        };
      
        var add = function () {
        	  parent.$.etDialog.open({
                  url: 'hrp/hr/privilegemanagement/medicalsafety/addResearchPage.do?isCheck=false',
                  title: '添加',
            	   width: $(parent.window).width(),
                   height: $(parent.window).height(),
                   frameName: window.name
              });
        };
        var openUpdate = function (rowData) {
        	if(rowData.is_commit!=0){
        		  $.etDialog.error('请选择新建状态修改');
        	}else{
        		parent.$.etDialog.open({
                url: 'hrp/hr/privilegemanagement/medicalsafety/updateResearchPage.do?isCheck=false&in_hos_no='+rowData.in_hos_no+'&occ_date='+rowData.occ_date+'&plaint_date='+rowData.plaint_date,
                title: '修改',
                width: $(parent.window).width(),
                height: $(parent.window).height(),
                frameName: window.name
            })}
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
                        url: 'confirmHrTechRec.do?isCheck=false',
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
                        url: 'reConfirmHrHrTechRec.do?isCheck=false',
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
                        url: 'auditHrTechRec.do?isCheck=false',
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
                       		msg+='请选择审批状态单据';
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
                           url: 'unauditHrHrTechRec.do?isCheck=false',
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
        	var msg="";
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
                if(item.rowData.is_commit!=0){
            		msg+='请选择新建状态单据';
              	  return false;
                }else{
                param.push({
                	in_hos_no: item.rowData.in_hos_no,
                	plaint_date: item.rowData.plaint_date,
                	occ_date: item.rowData.occ_date,
                });
                }
            })
            if(msg!=""){
           	  $.etDialog.error(msg);
           	  return;
             }
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteResearch.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
            });
        };
        var  print = function () {

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
                title: " 投诉纠纷调查表打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrResearchService",
                method_name: "queryResearchByPrint",
                bean_name: "hrResearchService",
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
    			},{"name" : "plaint_date",
    				"display" : "投诉日期",
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
    				"name" : "plainter",
    				"display" : "投诉人",
    				"width" : "200"
    			},{
    				"name" : "plaint_tel",
    				"display" : "投诉人电话",
    				"width" : "200"
    			},{
    				"name" : "patient_ref",
    				"display" : "与患者关系",
    				"width" : "200"
    			},{
    				"name" : "content",
    				"display" : "内容",
    				"width" : "200"
    			},{"name" : "is_commit",
    				"display" : "是否提交",
    				"width" : "200"
    			}]

    		};
    		importSpreadView("/hrp/hr/privilegemanagement/medicalsafety/importPlaint.do?isCheck=false", para, query);
        };
       
        var initGrid = function () {
            var columns = [
                           { display: '住院号或门诊号', name: 'in_hos_no', width: 120,
                               render: function (ui) {
                                   var updateHtml =
                                       '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                       ui.cellData +
                                       '</a>'

                                   return updateHtml;
                               } },
                           { display: '发生日期', name: 'occ_date', width: 100,
                         	   editor: {
                                   type: 'date',
                                   dateFormat: 'yy-mm-dd',
                               } },
                               { display: '投诉日期', name: 'plaint_date', width: 100,
                             	   editor: {
                                       type: 'date',
                                       dateFormat: 'yy-mm-dd',
                                   } },
               
                { display: '病人姓名', name: 'patient', width: 120 },
                { display: '投诉人', name: 'plainter', width: 120 },
                { display: '投诉人电话', name: 'plaint_tel', width: 120 },
                { display: '与患者关系', name: 'patient_ref', width: 120 },
                { display: '投诉内容', name: 'content', width: 120 },
                { display: '定性', name: 'prob_nature_name', width: 120 },
                { display: '问题分类', name: 'prob_type_name', width: 120 },
                 { display: '是否提交', name: 'commit_name', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                wrap: true,
                hwrap: true,
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
                       /*  { type: 'button', label: '审批', listeners: [{ click: audit }], icon: 'audit' },
                        { type: 'button', label: '销审', listeners: [{ click: unaudit }], icon: 'unaudit' }, */
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
                <td class="label">投诉日期：</td>
                <td class="ipt">
                  <input id="plaint_date" type="text" />
                </td>
                <td class="label">投诉人：</td>
                <td class="ipt">
                  <input id="plainter" type="text" />
                </td>
                <td class="label">投诉人电话：</td>
                <td class="ipt">
                      <input id="plaint_tel" type="text" />
                </td>
            </tr>
            <tr> <td class="label">投拆内容：</td>
                <td class="ipt">
                      <input id="content" type="text" />
                </td></tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>