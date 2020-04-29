<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="hr,dialog,datepicker,select,validate,grid,dateSlider,characterList,tree,tab,from,pageOffice" name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">
        var unit_info,acc_year_month;
        var grid;
        var initFrom = function () {
        /* 	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            }); */
        	
        	acc_year_month = $("#acc_year_month").etDatepicker({
    			view: "months",
    			minView: "months",
    			dateFormat: "yyyy-mm",
    			defaultDate: true
    		});
        	
        	dept_code = $("#dept_code").etSelect({
            	url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: function (value) {
                	emp_id.reload({url: '../../queryEmpSelect.do?isCheck=false',para: {dept_code: value},})
                }
           });
        	
           emp_id = $("#emp_id").etSelect({
       			url : "../../queryEmpSelect.do?isCheck=false",
       			defaultValue : "none",
       			onChange : query
           }); 
        };
        
        var query = function () {
            params = [
            	/*  {name: 'hos_id', value: hos_name.getValue() }, */
            	 {name: 'acc_year_month', value: acc_year_month.getValue() },
            	 {name : 'emp_id',value : emp_id.getValue()}, 
            	 {name : 'dept_id',value : dept_code.getValue().split('@')[1]}, 
            ];
            grid.loadData(params,'queryAccRestStatisticsKQ.do?isCheck=false');
        };
        
        var add = function () {
        	 grid.addRow();
        };
        
        function f_isChecked(rowdata){
            if (rowdata.dept_name.indexOf('100') == 0) 
                return true;
            return false;
        };
        
        var initGrid = function () {
            var columns = [
                { display: '科室信息', name: 'dept_name', width: 120 },
                { display: '职工名称', name: 'emp_name', width: 120 },
                { display: '期初天数', name: 'attend_accdays', width: 120 },
                
             { display: '本月积休', name: 'attend_jxdays', width: 120 }, 
                { display: '期末天数', name: 'end_xjdays', width: 120 }/* ,
                { display: '显示', name: 'is_check', width: 120 } */
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '计算', listeners: [{ click: submitData }], icon: 'submit' },
                        { type: 'button', label: '打印', listeners: [{ click: printData }], icon: 'print' }
                    ]
                },
                dataModel:{
                    getData:function(res){
                     	$.each(res.Rows,function(index,value){
                    	 	//if(value.is_check == 0){
                    	 		 //var currentRowData = grid.getAllData();
                                //console.log(currentRowData); 
                                console.log($(".pq-grid-row").find("input[type='checkbox']"));
                    	 		$(".pq-grid-row").find("input :checkbox").attr("disabled","disabled");
                    	 		
                    	 	//}
                     });
                     return res;
                    }
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                if(currentRowData.add_days!=0){
                	 parent.$.etDialog.open({
                 		width:$(window).width()-40,
                         height: $(window).height()-40,
                 		url:"hrp/hr/attendancemanagement/attend/hrOvertimePage.do?isCheck=false&emp_id=" + currentRowData.emp_id,
                 		title: '增加详情',
                 	})	
                }
                	
                
            }) 
            
            $("#mainGrid").on('click', '.openUpdate1', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                if(currentRowData.attend_xjdays!=0){
                	 parent.$.etDialog.open({
                		width:$(window).width()-40,
                        height: $(window).height()-40,
                		url:"hrp/hr/attendancemanagement/attend/applyLeavePage.do?isCheck=false&emp_id=" + currentRowData.emp_id,
                		title: '减少详情',
                	})
                }
            }) 
           
        };
		
        //计算
        function submitData(){
        	var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
            	var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    param.push({
                    	emp_id : rowdata.emp_id,
                    	attend_accdays : rowdata.attend_accdays,
                    	attend_jxdays : rowdata.attend_jxdays,
                    	attend_dec : rowdata.attend_dec,
                    	attend_end : rowdata.attend_end,
                    	end_xjdays : rowdata.end_xjdays,
                    	end_days : rowdata.end_days
                    })
                    
                });
                
                $.etDialog.confirm('确定要计算吗?', function () {
                    ajaxPostData({
                        url: 'updateAccRestStatisticsKQ.do',
                        data: {
                        	'acc_year_month': acc_year_month.getValue(),
                            'allData': JSON.stringify(param)
                        },
                        success: function () {
                            query();
                        }
                     })
                });
            }
        }
		
        //打印
        function printData(){
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads = {};
        	
        	var printPara={
          		title: "积休统计",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestStatisticsKQService",
       			bean_name: "hrAccRestStatisticsKQService",
       			method_name: "queryAccRestStatisticsKQPrint",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: ''//表尾需要打印的查询条件,可以为空 
           	};
        	
           	$.each(grid.getUrlParms(),function(i,obj){
         		printPara[obj.name]=obj.value;
           	}); 
           	officeGridPrint(printPara);
        }
        
        $(function () {
            initFrom();
            initGrid();
            query();
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
       <!--      <td class="label">单位信息：</td>
            <td class="ipt">
                <select id="hos_name" style="width:160px;" disabled></select>
            </td> -->
            
            <td class="label">考勤月度：</td>
	        <td class="ipt">
	        	<input name="acc_year_month" type="text" id="acc_year_month" style="width:160px"/>
	       </td>
            
            <td class="label">部门：</td>
            <td class="ipt">
                 <select id="dept_code" style="width:160px;" ></select>
            </td>
            
            <td class="label">职工姓名：</td>
            <td class="ipt">
            	<select id="emp_id" style="width:160px;"></select>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>