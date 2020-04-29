<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,grid,validate,select" name="plugins" />
	</jsp:include>
    <script>
        var attend_year, teacher,formValidate;
        var grid;
        
        $(function () {
            initFrom();
            initGrid();
            <%--query();--%>
        })
        
        var initFrom = function () {
        	 attend_code = $("#attend_code").etSelect({
                 url: "queryAttendCode.do?isCheck=false&control_type=1",
                 defaultValue: "none",
             });
        	
        };
       
        function initGrid() {
                    // 基础表格参数
                    var main_toolbar = {
                        items: [
                            { type: "button", label: '保存', icon: 'save', listeners: [{ click: save }] },
                            { type: "button", label: '关闭', icon: 'close', listeners: [{ click: close }] }
                        ]
                    };
                    var main_columns = [
                          { display: '职工编码', name: 'emp_code', width: 120},
                          { display: '职工姓名', name: 'emp_name', width: 120} 
                    ];
                    var main_obj = {
                        height: '100%',
                        inWindowHeight: true,
                        checkbox: true,
                        toolbar: main_toolbar,
                        columns: main_columns,
                        dataModel: {
                            url: 'queryEmpByDeptId.do?isCheck=false&dept_id='+'${dept_id}'
                        }  
                    };
                    grid = $("#mainGrid").etGrid(main_obj);
        }

        function query(){
        	var param = [ 
						 { name: 'dept_id', value: '${dept_id}' }
				];
             grid.loadData(param);	
        }
        
        //保存
        function save(){
        	if(attend_code.getValue() == ""){
   	       	 	$.etDialog.warn("请选择休假类型!");
   	       	 	return;
          	}

        	var data = grid.selectGet();
            var empIds = "";
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                  $(data).each(function () {
                      	var rowdata = this.rowData;
                      	empIds=empIds+'\''+rowdata.emp_id+"\',";
                  });
            }
            
            var data = {
                // 表单 填写的
                attend_code : attend_code.getValue(),
                attend_ed : $("#attend_ed").val(),
                limit : $("#attend_ed").val(),
                bala_amt : $("#attend_ed").val(),
                empIds  : empIds.substring(0, empIds.lastIndexOf(','))
            };
            ajaxPostData({
               url: 'addXJED.do',
                data: data,
                success: function () {
                    var parentFrameName = parent.$.etDialog.parentFrameName;
                    var parentWindow = parent.window[parentFrameName];
                    parentWindow.query();
                    close();
                },
                delayCallback: true
            })
            
            
        }
        
        //关闭
        function close(){
        	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            parent.$.etDialog.close(curIndex);
        }
        
       
    </script>
</head>

	<body>
	    <div class="main">
			<table class="table-layout">
				<tr>
					<td class="label">休假类型</td>
					<td class="ipt">
						<select id="attend_code" style="width: 180px;"></select>
					</td>
					
					<td class="label">额度(天) ：</td>
					<td class="ipt">
						<input id="attend_ed" type="text" />
					</td>
				</tr>
			</table>
		</div>
	    <div id="mainGrid"></div>
	</body>
</html>