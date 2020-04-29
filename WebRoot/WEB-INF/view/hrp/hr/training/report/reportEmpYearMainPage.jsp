<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	     <jsp:param value="hr,dialog,grid,select,tree,pageOffice,tab,datepicker,time,upload,validate" name="plugins" />
	</jsp:include>
	<style type="text/css">
	 button {
	 
                margin: 0px 5px;
                box-sizing: border-box;
                height: 26px;
                padding-left: 10px;
                padding-right: 10px;
                border: 1px solid #aecaf0;
                background: #c1dbfa;
                outline: none;
                border-radius: 2px;
                cursor: pointer;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
                  text-align: center
            }
	</style>
    <script>
        var grid,year,dept_code,train_type,train_way,id_exe;
        var query = function () {
             params = [
                { name: 'train_year', value: year.getValue()  },
                { name: 'emp_id', value: "${emp_id}"},
                /*  { name: 'dept_id', value: dept_code.getValue().split('@')[1] },
              
                { name: 'train_title',  value: $('#train_title').val()},
                */

            ]; 
            grid.loadData(params);
        };
    	
    	// 证书模板设置
    	function setTemplateCert(){
    		
    		if(grid.getAllData() == null){
    			$.etDialog.warn("请先查出数据.");
    			return;
    		}
    		
    		var useId = 0;// 按培训计划区分
    		officeFormTemplate({template_code: "06001", use_id: useId});
    	}
        // 电子证书打印
    	function dzzsPrint(ele){
    		var certRowI = ele.getAttribute("row-index");
    		var certRowD = grid.getAllData()[certRowI];
    		
    		var useId = 0;
    	   	var para = {
    			class_name: "com.chd.hrp.hr.service.training.HrTrainExamineService",
    			method_name: "generateDZBTrainEmpCert",
    			bean_name: "hrTrainExamineService",
    			emp_ids: "${emp_id}",
    			plan_id: certRowD.plan_id,
    			template_code: '06001',
    			isPrintCount: false,//更新打印次数
    			isPreview: true,//预览窗口，传绝对路径
    			use_id: useId
    		};
    	   	
    	   	officeFormPrint(para); 
    		
    	}
     
        var initGrid = function () {
            var columns = [
                { display: '计划培训时间', name: 'htctrain_date', width: 120},
                { display: '培训项目', name: 'train_title', width: 120 },
                { display: '培训地点', name: 'train_site', width: 120 },
                { display: '实际培训时间', name: 'train_date', width: 120 },
                { display: '授课人', name: 'teacher', width: 120 },
                { display: '考试成绩', name: 'score', width: 120 },
                { display: '是否合格', name: 'is_pass_name', width: 120 },
                { display: '是否发证', name: 'is_cert_name', width: 120 },
                { display: '获得学分', name: 'xuefen', width: 120 },
     			{ display: '电子证书', name: 'cert_code', width: 80, align: 'center', editable: false,
     				render: function(ui){
     					if(ui.rowData.cert_code!=null){
	     					return '<a onclick="dzzsPrint(this);" row-index="' + ui.rowData._rowIndx + '">打印</a>';
     					}
     				}
     			},
            ];
            var paramObj = {
                height: '100%',
                checkbox: true,
                editable: true,
                dataModel: {
                    url: 'queryReportEmpYear.do'
                },
               /*  rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        plan_id: rowData.plan_id
                    };

                    openUpdate(openParam);
                }, */
                columns: columns,
             /*   toolbar: {
                    items: [
                        	{ type: 'button', label: '证书模板设置', listeners: [{ click: setTemplateCert }], icon: 'document' },
                      
                    ]
                }  */
            };
            grid = $("#mainGrid").etGrid(paramObj);

        };

        $(function () {
            initGrid();
            initDict();
        
        })
          //加载查询条件
        function initDict() {
        	year = $("#year").etDatepicker({

        		  view: "years",
        		  minView: "years",
        		  dateFormat: "yyyy",
        		  defaultDate: true,
            });// 编制日期
            
        	$("#query").click(function () {
        	if(year.getValue()==""){
        		   $.etDialog.error('请选择需要查询的年份');
        	}
                	var formPara=[
                			 { name: 'year', value: year.getValue().split('-')[0]  },
            		];
                	
                    grid.loadData(formPara);
                	//grid.loadData(grid1.where);
                	
            	})
  
        	
        }
   
        
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" >年度：</td>
            <td class="ipt">
                <input id="year" type="text"  style="width:200px"/>
            </td>
          <td class="btn_group"> <button id="query">查询</button></td>
        </tr>
        <tr>
         <td class="label" >部门：</td>
            <td class="ipt">
                <input id="dept_name" type="text"  style="width:180px" disabled="disabled" value="${dept_name}"/>
            </td>
      <%--    <td class="label" >工号：</td>
            <td class="ipt">
                <input id="emp_code" type="text"  style="width:180px" disabled="disabled" value="${emp_code}"/>
            </td> --%>
             <td class="label" >姓名：</td>
            <td class="ipt">
                <input id="emp_name" type="text"  style="width:180px" disabled="disabled" value="${emp_name}"/>
            </td>
             <td class="label" >人员类别：</td>
            <td class="ipt">
                <input id="kind_name" type="text"  style="width:180px" disabled="disabled" value="${kind_name}"/>
            </td>
            
        
        </tr>
    </table>
 
    <div id="mainGrid"></div>
</body>

</html>
