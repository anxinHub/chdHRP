<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var grid,year_month,dept_code,train_type,train_way,id_exe;
        var query = function () {
            params = [
                { name: 'train_time', value: year_month.getValue()  },
               // { name: 'month', value: year_month.getValue().split('-')[1]  },
                { name: 'dept_id', value: dept_code.getValue().split('@')[1] },
              
                { name: 'train_title',  value: $('#train_title').val()},
             

            ];
            grid.loadData(params);
        };
    
       
     
        var initGrid = function () {
            var columns = [
                { display: '培训时间', name: 'train_date', width: 120},
                { display: '培训部门', name: 'dept_name', width: 120 },
                { display: '培训主题', name: 'train_title', width: 120 },
                { display: '应到人数', name: 'emcount', width: 120 },
                { display: '实到人数', name: 'shidao', width: 120,
                	render:function(rowData){
		            	return formatNumber((rowData.rowData.s1)+(rowData.rowData.s2)+(rowData.rowData.s3)+(rowData.rowData.s4))
		            }

                },
                { display: '缺席人数', name: 's0', width: 120,
                	render:function(rowData){
		            	return formatNumber((rowData.rowData.emcount)-(rowData.rowData.s1)-(rowData.rowData.s2)-(rowData.rowData.s3)-(rowData.rowData.s4))
		            } },
                { display: '到会率', name: 'daohui', width: 120,
                	render:function(rowData){
		            	return formatNumber(((rowData.rowData.s1)+(rowData.rowData.s2)+(rowData.rowData.s3)+(rowData.rowData.s4))/(rowData.rowData.emcount)*100,2, 1)+"%"
		            } },
                { display: '迟到人数', name: 's2', width: 120 }/*,
                 { display: '缺席人数', name: 'id_exe_name', width: 120 } */

            ];
            var paramObj = {
                height: '100%',
                checkbox: true,

                dataModel: {
                    url: 'queryReportSign.do'
                },
            /*     rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        plan_id: rowData.plan_id
                    };

                    openUpdate(openParam);
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                      
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

        };

        $(function () {
            initGrid();
            initDict();
        })
          //加载查询条件
        function initDict() {
        	year_month = $("#year_month").etDatepicker({

        		  view: "months",
        		  minView: "months",
        		  dateFormat: "yyyy-mm",
            });// 编制日期
            
            //培训部门
            dept_code = $("#dept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
          
        }
        
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" >计划时间：</td>
            <td class="ipt">
                <input id="year_month" type="text"  style="width:200px"/>
            </td>
          <td class="label">培训部门：</td>
                <td class="ipt">
                    <select id="dept_code" type="text" style="width:180px"></select>
                </td>

        
            <td class="label" >培训主题：</td>
            <td class="ipt">
                <input id="train_title" type="text"  style="width:200px"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
