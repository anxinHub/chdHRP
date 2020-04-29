<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title></title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="dialog,grid,select,datepicker" name="plugins" />
    </jsp:include>
    <script>
        var attend_overtime_reg_operdate,attend_overtime_begdate,
        emp_id,
            dept_code,
            attend_overtime_state,
            attend_overtime_kind,
            grid;

        function initDict() {
           
        }
        function query() {
         /*    var param = [
            { name: 'attend_overtime_reg_operdate1',  value: attend_overtime_reg_operdate.getValue()[0] || ''},
            { name: 'attend_overtime_reg_operdate2',  value: attend_overtime_reg_operdate.getValue()[1] || ''},
            { name: 'attend_overtime_begdate', value: attend_overtime_begdate.getValue()[0] || ''},
            { name: 'attend_overtime_enddate', value: attend_overtime_begdate.getValue()[1] || ''},
            {name:'dept_id', value: dept_code.getValue().split('@')[1] },
            { name: 'emp_id',  value: emp_id.getValue() },
            {name: 'attend_overtime_kind', value: attend_overtime_kind.getValue()},
            {name:'attend_overtime_state',value:attend_overtime_state.getValue()  }
            ];
            grid.loadData(param); */
        }
        function initGrid() {
            // 基础表格参数
            var main_toolbar = {
                /* items: [
                    { type: "button", label: '查询', icon: 'print', listeners: [{ click: query }] },
                    { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                    { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
                    //{ type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                    { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'submit' },
                    { type: 'button', label: '撤回', listeners: [{ click: cancel }], icon: 'cancel' },
                   
                ] */
            };
            var main_columns = [
             
                {
                    display: '职工',
                    align: 'center',
                    name: 'emp_name',
                    width: 120
                },{
                    display: '部门',
                    align: 'center',
                    name: 'dept_name',
                    width: 120
                },
                { display: "考勤月度",
                    align: "center",
                    width: 120,
                    name: "attend_overtime_begdate",
                },
                {
                    display: '变更类型',
                    align: 'center',
                    name: 'file_type',
                    width: 120
                },
             
              
                {
                    display: "变更天数",
                    align: "center",
                    width: 120,
                    name: "attend_accchadays"
                },
                {
                    display: "变更时间",
                    align: "center",
                    width: 120,
                    name: "OPER_DATE"
                }
            ];
            var main_obj = {
                height: '100%',
                inWindowHeight: true,
                toolbar: main_toolbar,
                columns: main_columns,
                dataModel: {
                    url: 'overtimeQuery.do?isCheck=false&emp_id='+'${emp_id}'
                },  
            };
            grid = $("#mainGrid").etGrid(main_obj);
      
        }



        $(function () {
            initDict();
            initGrid();
        })
    </script>
</head>

<body>
    <div id="mainGrid"></div>
</body>

</html>