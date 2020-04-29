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
        var nint_date, case_tol, finder;
        var grid;
        var initFrom = function () {
            nint_date = $("#nint_date").etDatepicker({
            	range : true,
            	onChange: query,
            });
           
        };
        var query = function () {
            var params = [
                          { name: 'nintbeg_date', value: nint_date.getValue()[0] || '' },
                          { name: 'nint_date', value: nint_date.getValue()[1] || '' },
                          { name: 'nint', value: $("#nint").val() },
                        /*  { name: 'case_tol', value: $("#case_tol").val() },
                          { name: 'role', value: $("#role").val() },
                          { name: 'prize', value: $("#prize").val() },
                          { name: 'content', value: $("#content").val() }, */
            ];
            grid.loadData(params,'queryInnovation.do');
        };
        var save = function () {
        	var msg="";
        	var ParamVo = [];
     	   var isPass = grid.validateTest({
               required: {
            	   nint_date: true,
            	   nint:true
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
                       	if(rowdata.is_commit!=0 && rowdata.commit_name !=""){
                    		msg+='请选择提交状态单据';
                    		return false;
                    	}

                           ParamVo.push(rowdata);
                       }) 
                       }
                 //验证重复数据
             	if (!grid.checkRepeat(
             			grid.selectGet(),
             			['nint_date','nint']
             	)		
             	) {
                    return;
                }
                 if(msg!=""){
                	$.etDialog.error(msg);
                	return;
                 }
            ajaxPostData({
                url: 'addInnovation.do',
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
                        url: 'confirmInnovation.do?isCheck=false',
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
                        url: 'reConfirmInnovation.do?isCheck=false',
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
            		msg+='请选择新建状态删除';
            		
            		
            	}else{
                param.push({
                	nint: item.rowData.nint,
                	nint_date: item.rowData.nint_date,
                	is_commit: item.rowData.is_commit,
                });
            }})
            if(msg!=""){
            	$.etDialog.error('请选择新建状态删除' );
            	return;
            }
             $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteInnovation.do',
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
                title: " 创新能力打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrInnovationService",
                method_name: "queryInnovationByPrint",
                bean_name: "hrInnovationService",
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
    				"name" : "nint_date",
    				"display" : "日期",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "nint",
    				"display" : "新项目新技术",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "role",
    				"display" : "参与角色",
    				"width" : "200"
    			},{
    				"name" : "emp",
    				"display" : "参与成员",
    				"width" : "200"
    			},{
    				"name" : "case_num",
    				"display" : "完成例数",
    				"width" : "200"
    			},{
    				"name" : "case_tol",
    				"display" : "总例数",
    				"width" : "200"
    			},{
    				"name" : "prize",
    				"display" : "获奖情况",
    				"width" : "200"
    			},{"name" : "is_commit",
    				"display" : "是否提交",
    				"width" : "200"
    			}]

    		};
    		importSpreadView("/hrp/hr/healthadministration/innovation/importInnovation.do?isCheck=false", para, query);
        };
       
        var initGrid = function () {
            var columns = [
                           { display: '发生日期', name: 'nint_date', width: 100,
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
                               
                { display: '新项目新技术', name: 'nint', width: 120,editable : function(col){
                  	if(col.rowData){
                   		if(col.rowData.hos_id!=null){
                   			return false;
                   		}
                   		return true;
                   	}else{
                   		return true;
                   	}
                   }  },
                { display: '参与角色', name: 'role', width: 120 },
                { display: '参与成员', name: 'emp', width: 120},
                { display: '完成例数', name: 'case_num', width: 120 },
                { display: '总例数', name: 'case_tol', width: 120 },
                { display: '获奖情况', name: 'prize', width: 120 },
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
                <td class="label">日期：</td>
                <td class="ipt">
                    <input id="nint_date" type="text" />
                       <td class="label">新项目新技术：</td>
                <td class="ipt">
                    <input id="nint" type="text" />
              </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>