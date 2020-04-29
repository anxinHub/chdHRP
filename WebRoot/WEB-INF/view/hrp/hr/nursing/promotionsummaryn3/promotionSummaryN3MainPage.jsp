<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,select,datepicker,grid,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var year, dept;
        var grid;

        var query = function () {
            params = [
                { name: 'year', value: year.getValue() },
                { name: 'dept_code', value: dept_code.getValue() },
                { name: 'emp_code', value: $("#emp_code").val() },
                { name: 'emp_name', value: $("#emp_name").val() },
            ];
            grid.loadData(params,"queryPromotionSummaryN3.do");
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
                title: " 护理晋级汇总申请表N3打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.nursing.HrPromotionSummaryN3Service",
                method_name: "queryPromotionN3ByPrint",
                bean_name: "hrPromotionSummaryN3Service",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
        // 护士长审核
        var nurse_auditN3 = function () {
      /*       var isPass = grid.validateTest({
                required: {
                    hnurse_name: true
                }
            });
            if (!isPass) {
                return;
            } */
          	var errorMsg = '';//错误提示信息
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function (index) {
                      var rowdata = this.rowData;
                      if(rowdata.hnurse_name == '' || rowdata.hnurse_name == undefined){
                      	errorMsg += '所选的第' + (index+1) + '行护士长初审不能为空<br/>';
                      	return false;
                      }
                      ParamVo.push(rowdata);
                  });
                  
                  if(errorMsg != ''){
                 	 $.etDialog.error(errorMsg);
                      return;
                      }
                  ajaxPostData({
                        url: 'auditHnurseAuditN3.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                })
            }
            
        };
        // 护士长销审
        var nurse_unauditN3 = function () {
        	var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      ParamVo.push(rowdata);
                  });
               
                  ajaxPostData({
                        url: 'reAuditHnurseAuditN3.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                })
            }
        };
        
        // 科护长审核
        var section_chief_auditN3 = function () {
         	var errorMsg = '';//错误提示信息
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {

                $(data).each(function (index) {
                    var rowdata = this.rowData;
                    if(rowdata.hnurse_name == '"未通过"' || rowdata.hnurse_audit == 0){
                    	errorMsg += '所选的第' + (index+1) + '行护士长审核未通过<br/>';
                    	return false;
                    }
                    if(rowdata.hnurse_name == '' || rowdata.hnurse_name == undefined){
                    	errorMsg += '所选的第' + (index+1) + '行科护士长初审不能为空<br/>';
                    	return false;
                    }
                    ParamVo.push(rowdata);
                });
                
                if(errorMsg != ''){
               	 $.etDialog.error(errorMsg);
                    return;
                    }
               
                  ajaxPostData({
                        url: 'auditDhnurseAuditN3.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                })
            }
        };
        
        // 科护长销审
        var section_chief_unauditN3 = function () {
        	var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      ParamVo.push(rowdata);
                  });
               
                  ajaxPostData({
                        url: 'reAuditDhnurseAuditN3.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                })
            }
        	
        };
        // 晋级小组意见
        var advocacy_team_commentsN3 = function () {
         	var errorMsg = '';//错误提示信息
            
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {

                $(data).each(function (index) {
                    var rowdata = this.rowData;
                    if(rowdata.dhnurse_name == '"未通过"' || rowdata.dhnurse_audit == 0){
                    	errorMsg += '所选的第' + (index+1) + '行科护士长审核未通过<br/>';
                    	return false;
                    }
                    if(rowdata.promotion_name == '' || rowdata.promotion_name == undefined){
                    	errorMsg += '所选的第' + (index+1) + '行晋级意见不能为空<br/>';
                    	return false;
                    }
                    ParamVo.push(rowdata);
                });
                
                if(errorMsg != ''){
               	 $.etDialog.error(errorMsg);
                    return;
                    }
               
                  ajaxPostData({
                        url: 'auditPromotionAuditN3.do',
                        data: {
                        	paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                })
            }
            
        };


        var initFrom = function () {
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                // minDate: data[data.length - 1].text,
                // maxDate: data[0].text,
                onChange: query
            });
            dept_code = $("#dept_code").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
            });

            $("#query").on('click', query);
            $("#nurse_auditN3").on('click', nurse_auditN3);
            $("#nurse_unauditN3").on('click', nurse_unauditN3);
            $("#section_chief_auditN3").on('click', section_chief_auditN3);
            $("#section_chief_unauditN3").on('click', section_chief_unauditN3);
            $("#advocacy_team_commentsN3").on('click', advocacy_team_commentsN3);
            $("#print").on('click', print);
        };

        var initGrid = function () {
            var columns = [
                { display: '科室', name: 'dept_name', width: 80, editable: false, },
                { display: '工号', name: 'emp_code', width: 80, editable: false, },
                { display: '姓名', name: 'emp_name', width: 80, editable: false, },
                { display: '在职教育', name: 'education', width: 80, editable: false, },
                { display: '读书报告', name: 'book_report', width: 80, editable: false, },
                { display: '案例分析', name: 'case_analy', width: 80, editable: false, },
                { display: '个案分析报告', name: 'special_case_analy', width: 80, editable: false, },
                { display: 'CPR成绩', name: 'cpr_score', width: 80, editable: false, },
                { display: '同事评价', name: 'peer_score', width: 120, editable: false, },
                { display: '笔试成绩', name: 'write_score', width: 80, editable: false, },
                { display: '护士长初审', name: 'hnurse_name', width: 80,
                    editor: {
                        type: 'select',
                        keyField: 'hnurse_audit',
                        source: [
                            { label: '通过', id: '1' },
                            { label: '未通过', id: '0' },
                        ]
                    }
                },
                { display: '科护长审', name: 'dhnurse_name', width: 80,
                    editor: {
                        type: 'select',
                        keyField: 'dhnurse_audit',
                        source: [
                            { label: '通过', id: '1' },
                            { label: '未通过', id: '0' },
                        ]
                    }
                },
                { display: '晋级小组意见', name: 'promotion_name', width: 100,
                    editor: {
                        type: 'select',
                        keyField: 'promotion_audit',
                        source: [
                            { label: '通过', id: '1' },
                            { label: '未通过', id: '0' },
                        ]
                    }
                },
                { display: '不通过理由', name: 'pass_reason', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                /* dataModel: {
                    url: 'queryPromotionSummaryN3.do?isCheck=false'
                }, */
                columns: columns
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
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
                <td class="label">科室：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>
                <td class="label">工号：</td>
                <td class="ipt">
                    <input id="emp_code" type="text" />
                </td>
                <td class="label">姓名：</td>
                <td class="ipt">
                    <input id="emp_name" type="text" />
                </td>
            </tr>
        </table>
    </div>
    <div class="button-group" style="text-align: left;">
        <button id="query">查询</button>
        <button id="nurse_auditN3">护士长审核</button>
        <button id="nurse_unauditN3">护士长销审</button>
        <button id="section_chief_auditN3">科护长审核</button>
        <button id="section_chief_unauditN3">科护长销审</button>
        <button id="advocacy_team_commentsN3">晋级小组意见</button>
        <button id="print">打印</button>
    </div>
    <div id="mainGrid"></div>
</body>

</html>