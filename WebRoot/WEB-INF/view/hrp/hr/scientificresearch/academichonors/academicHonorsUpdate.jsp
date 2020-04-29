<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,select,grid,upload,validate" name="plugins" />
	</jsp:include>
    <script>
        var apply_date, honor_code, get_date;
        var formValidate;
		var state = '${state}';
        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#apply_date"), required: true },
                    { el: $("#honor_code"), required: true },
                    { el: $("#get_date"), required: true },
                ]
            });
        };
        
        var initForm = function () {
            apply_date = $("#apply_date").etDatepicker({
                defaultDate: '${apply_date}'
            });
            get_date = $("#get_date").etDatepicker({
                defaultDate: '${get_date}'
            });
            honor_code = $("#honor_code").etSelect({
                url: "queryHonor.do?isCheck=false",
                defaultValue: "${honor_code}"
            });
        };
        
        var query = function () {
            var params = [
                { name: 'apply_no', value: '${apply_no}' },
                { name: 'apply_date', value: apply_date.getValue() },
                { name: 'emp_id', value: '${emp_id}' },
                { name: 'honor_code', value: honor_code.getValue() },
                { name: 'get_date', value: get_date.getValue() },
                { name: 'note', value: $("#note") },
            ];
            // TODO: 路径
            grid.loadData(params, 'queryHrAcadeHonorDetail.do?isCheck=false');
        };
        
        var remove = function () {
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            
            var param = [];
            selectData.forEach(function (item) {
            	if(item.rowData.seq_no){
            		param.push({
                    	emp_id: '${emp_id}',
                        apply_no : '${apply_no}',
                        honor_code : honor_code.getValue(),
                        seq_no : item.rowData.seq_no,
                        filepath : item.rowData.filepath
                    });
            	}
            })
            
            ajaxPostData({
                url: 'deleteAcademicHonorsDetail.do?isCheck=false',
                data: {
                    paramVo: JSON.stringify(param)
                },
                success: function () {
                	initGrid();
                	query();
                	parent.query();
                },
            })
        };
        
        var save = function () {
        	//申请日期
        	var beginDate = apply_date.getValue();
        	//获得日期
			var endDate = get_date.getValue();

			var d1 = new Date(beginDate.replace(/\-/g, "\/"));
			var d2=new Date(endDate.replace(/\-/g, "\/"));
			if(beginDate!=""&&endDate!=""&&d1 <d2){
			  $.etDialog.error("申请日期不能早于获得日期！");
			  return false;
			}
            var isPass = grid.validateTest({}) && formValidate.test();
            if (!isPass) {
                return;
            }
             var allData = grid.getAllData();
             /*  if(allData==null){
            	  $.etDialog.error('请添加明细');
                  return;
              } */
            ajaxPostData({
                url: 'updateAcademicHonors.do',
                data: {
                    gridData: JSON.stringify(allData),
                    apply_no: '${apply_no}',
                    apply_date: apply_date.getValue(),
                    emp_id: '${emp_id}',
                    honor_code: honor_code.getValue(),
                    get_date: get_date.getValue(),
                    note: $("#note").val(),
                },
                success: function () {
                	query();
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    //parent.$.etDialog.close(curIndex);
                    parent.query();
                },
                delayCallback: true
            })
        };
        var add = function () {
            grid.addRow();
        };
        
        var submit = function () {
        	var ParamVo = [];
        	var data={
        			 apply_no: '${apply_no}',
                     apply_date: apply_date.getValue(),
                     emp_id: '${emp_id}',
                     honor_code: honor_code.getValue(),
                     get_date: get_date.getValue(),
                     state:state,
        	};
        	ParamVo.push(data)
            ajaxPostData({
                url: 'confirmAcademicHonors.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	state = 1;
                	initGrid();
                	query();
                	parent.query();
                	
                },
            })
        };
        var unsubmit = function () {
        	var ParamVo = [];
        	var data={
        			 apply_no: '${apply_no}',
                     apply_date: apply_date.getValue(),
                     emp_id: '${emp_id}',
                     honor_code: honor_code.getValue(),
                     get_date: get_date.getValue(),
                     state : state,
        	};
        	 ParamVo.push(data)
            ajaxPostData({
                url: 'reConfirmAcademicHonors.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	state = 0;
                	initGrid();
                	query();
                	parent.query();
                },
            })
        };
        var initGrid = function () {
            var columns = [
                { display: '序号', name: 'seq_no', width: 120 },
                { display: '详细内容', name: 'content', width: 120 },
                { display: '附件', name: 'accessory', width: 200,
                    fileModel: {
                        keyField: 'file',
                        url: 'addPicture.do?isCheck=false'
                    }
                },
                //{ display: '附件', name: 'filepath', hidden:true}
                
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                columns: columns,
                editable : (state == 0 ) ? true : false,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' ,disabled: state == 0 ? false : true},
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save',disabled: state == 0 ? false : true },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add',disabled: state == 0 ? false : true },
                        { type: 'button', label: '提交', listeners: [{ click: submit }], icon: 'add',disabled: state == 0 ? false : true },
                        { type: 'button', label: '取消提交', listeners: [{ click: unsubmit }], icon: 'cancel', disabled: state == 1 ? false : true}
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        }

        $(function () {
            initValidate();
            initForm();
            initGrid();

            query();
        })
    </script>
</head>

<body>
    <div id="main">
        <table class="table-layout" style="width: 100%;">
            <tr>
                <td class="label">申请单号：</td>
                <td class="ipt">
                    <input id="apply_no" type="text" value="${apply_no}" disabled="disabled"/>
                </td>
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="apply_date" type="text" />
                </td>
                <td class="label">职工姓名：</td>
                <td class="ipt">
                    <input id="emp_id" type="text" value="${emp_name}" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <td class="label">学术荣誉：</td>
                <td class="ipt">
                    <select id="honor_code" style="width:180px;"></select>
                </td>
                <td class="label">获得日期：</td>
                <td class="ipt">
                    <input id="get_date" type="text" />
                </td>
                <td class="label">摘要：</td>
                <td class="ipt">
                    <input id="note" type="text" value="${note}" />
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>