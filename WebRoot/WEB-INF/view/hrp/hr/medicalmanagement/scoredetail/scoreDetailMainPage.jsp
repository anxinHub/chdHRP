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
        var occ_date, plaint_date, plainter, finder;
        var grid;
        var initFrom = function () {
            occ_date = $("#occ_date").etDatepicker({
            	range : true,
            	onChange: query,
            });
            plaint_date = $("#plaint_date").etDatepicker({
            	range : true,
            	onChange: query,
            });
           dept_id = $("#dept_id").etSelect({
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
                          { name: 'occ_date', value: occ_date.getValue('yyyy-mm-dd') },
                          { name: 'plaint_date', value: plaint_date.getValue('yyyy-mm-dd')},
                          { name: 'plainter', value: $("#plainter").val() },
                          { name: 'patient', value: $("#patient").val() },
                          { name: 'dept_id', value: dept_id.getValue().split('@')[1] },
                          { name: 'emp_id', value: emp_id.getValue() },
                          { name: 'in_hos_no', value: $("#in_hos_no").val() },
                          { name: 'plaint_tel', value: $("#plaint_tel").val() },
                          { name: 'content', value: $("#content").val() },
            ];
            grid.loadData(params,'queryScoreDetail.do');
        };
        var save = function () {
        	var ParamVo = [];
     	   var isPass = grid.validateTest({
               required: {
            	   in_hos_no : true,
            	   plaint_date : true,
            	   occ_date : true
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
                           ParamVo.push(rowdata);
                       }) 
                       }
                 //验证重复数据
             	if (!grid.checkRepeat(
             			grid.selectGet(),
             			['in_hos_no','plaint_date','occ_date','emp_id', 'dept_id']
             	)		
             	) {
                    return;
                }
            ajaxPostData({
                url: 'addScoreDetail.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	query();
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
                  		msg+='请选择新建状态';
                  		return false;
                  		
                  	}else{
                      ParamVo.push(rowdata);
                  	}
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmScoreDetail.do?isCheck=false',
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
                  		msg+='请选择提交状态';
                  		return false;
                  		
                  	}else{
                      ParamVo.push(rowdata);
                  	}
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定撤回?', function () {
                    ajaxPostData({
                        url: 'reConfirmScoreDetail.do?isCheck=false',
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
            		msg+='请选择新建状态删除';
            		
            		
            	}else{
                param.push({
                	occ_date: item.rowData.occ_date,
                	plaint_date: item.rowData.plaint_date,
                	in_hos_no: item.rowData.in_hos_no,
                	dept_id: item.rowData.dept_id,
                	emp_id: item.rowData.emp_id
                });
                }
            });
  		  if(msg!=""){
          	$.etDialog.error('请选择新建状态删除' );
          	return;
          }
				$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteScoreDetail.do',
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
                title: " 投诉纠纷扣分打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrScoreDetailService",
                method_name: "queryScoreDetailByPrint",
                bean_name: "hrScoreDetailService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
        <%--var putout = function () {
        	exportGrid(grid);
        };--%>
       
       
        var initGrid = function () {
            var columns = [
                { display: '住院号或门诊号', name: 'in_hos_no', width: 120,
                                       editor: {
                                           type: 'grid',
                                           columns: [
                                           	 { display: '住院号或门诊号', name: 'in_hos_no', width: 120 },
                                                { display: '投诉日期', name: 'plaint_date', width: 120 },
                                                { display: '发生日期', name: 'occ_date', width: 120 },
                                                { display: '病人姓名', name: 'patient', width: 120 },
                                                { display: '年龄', name: 'age', width: 120 },
                                                { display: '投诉人', name: 'plainter', width: 120 },
                                                { display: '投诉内容', name: 'content', width: 120 },
                                                { display: '责任科室', name: 'dept_name', width: 120 },
                                                { display: '责任人', name: 'emp_name', width: 120 },
                                                { display: '责任人性质', name: 'emp_nature', width: 120 },
                                                { display: '承担比例', name: 'ratio', width: 120 },
                                                { display: '', name: 'dept_id', hidden: true },
                                                { display: '', name: 'emp_name', hidden: true },
                                           ],
                                           width: '700px',
                                           height: '205px',
                                           dataModel: {
                                               url: 'queryHrResearch.do?isCheck=false'
                                           }, },
                                           editable : function(col){
                                              	if(col.rowData){
                                           		if(col.rowData.is_commit!=null){
                                           			return false;
                                           		}
                                           		return true;
                                           	}else{
                                           		return true;
                                           	}
                                           }},
                { display: '投诉日期', name: 'plaint_date', width: 100, editable: false },
                { display: '发生日期', name: 'occ_date', width: 100, editable: false },
                { display: '病人姓名', name: 'patient', width: 120, editable: false },
                { display: '年龄', name: 'age', width: 120 , editable: false},
                { display: '投诉人', name: 'plainter', width: 120, editable: false },
                { display: '投诉内容', name: 'content', width: 120, editable: false },
                { display: '责任科室', name: 'dept_name', width: 120, editable: false },
                { display: '责任人', name: 'emp_name', width: 120, editable: false },
                { display: '责任人性质', name: 'emp_nature', width: 120 , editable: false},
                { display: '承担比例', name: 'ratio', width: 120, editable: false },
                { display: '建议扣分', name: 'score', width: 120 },
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
                <td class="label">投诉日期：</td>
                <td class="ipt">
                    <input id="plaint_date" type="text" />
                </td>
                <td class="label" style="width:120px;">住院号/门诊号：</td>
                <td class="ipt">
                    <input id="in_hos_no" type="text" />
                </td>
            </tr>
            <tr>
                  <td class="label">责任科室：</td>
                <td class="ipt">
                    <select id="dept_id" style="width:180px;"></select>
                </td>
                <td class="label">当事者：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
           <!--  </tr>
            <tr> --> <td class="label">投拆内容：</td>
                <td class="ipt">
                      <input id="content" type="text" />
                </td></tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>