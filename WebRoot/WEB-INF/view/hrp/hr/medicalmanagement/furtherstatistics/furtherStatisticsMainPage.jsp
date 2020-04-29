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
        var app_date, dept_code, emp_id, patient, is_commit;
        var tree, grid;
        var initFrom = function () {
            app_date = $("#app_date").etDatepicker({
                range: true
            });
            emp_id = $("#emp_id").etSelect({
    			url : "../../queryEmpSelect.do?isCheck=false",
    			defaultValue : "none",
    			onChange : query,

            });
        

        };
        var query = function () {
            var params = [
              { name: 'app_date', value: app_date.getValue()[0] || '' },
               { name: 'end_date', value: app_date.getValue()[1] || '' },
             
           	{
          			name : 'app_no',
          			 value: $("#app_no").val()
          		},{
       			name : 'emp_id',
       			value : emp_id.getValue() 
       		}, 
       		{
       			name : 'furstd_hos',
       			 value: $("#furstd_hos").val()
       		},
       		{
       			name : 'profession',
       			 value: $("#profession").val()
       		},
       		
            ];
            grid.loadData(params,'queryFurtherStatistics.do');
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
                title: " 进修统计表打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.medicalmanagement.HrFurtherStatisticsService",
                method_name: "queryFurtherStatisticsByPrint",
                bean_name: "hrFurtherStatisticsService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
        var initGrid = function () {
            var columns = [
                { display: '申请日期', name: 'app_date', width: 120 },
                { display: '姓名', name: 'emp_name', width: 120 },
                { display: '专业', name: 'profession', width: 120 },
                { display: '年龄', name: 'age', width: 120 },
                { display: '工龄', name: 'workage', width: 120 },
                { display: '进修开始时间', name: 'beg_date', width: 120 },
                { display: '进修时长', name: 'duration', width: 120 },
                { display: '进修医院', name: 'furstd_hos', width: 120 },
                { display: '费用', name: 'hostel_charge', width: 120 },
                { display: '联系电话', name: 'phone', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
               
                dataModel: {
                   // url: ''
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
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
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="app_date"  type="text" />
                </td>
                <td class="label">姓名：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                  <td class="label">专业：</td>
                <td class="ipt">
                    <input id="profession" type="text"/>
                </td>
            </tr>
            <tr>
                <td class="label">进修医院：</td>
                <td class="ipt">
                   <input id="furstd_hos" type="text">
                </td>
              
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>