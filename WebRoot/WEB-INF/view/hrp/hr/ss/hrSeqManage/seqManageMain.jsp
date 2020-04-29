<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,grid,ligerUI,dialog" name="plugins" />
</jsp:include>
<title>序列管理</title>
<script type="text/javascript">
//初始化渲染界面，加载数据
$(function () {
    initGrid();
})
var grid;
var query = function () {
    params = [
        { name: 'seq_code', value: $("#seq_code").val() },
        { name: 'seq_name', value: $("#seq_name").val() }
    ];
    grid.loadData(params);
};
var add = function () {
    $.etDialog.open({
        url: 'seqManageAddPage.do?isCheck=false',
        width: 450,
        height: 300,
        title: '添加',
        btn: ['确定', '取消'],
        btn1: function (index, el) {
            var iframeWindow = window[el.find('iframe').get(0).name];
            iframeWindow.save()
        }
    });
};
var create= function () {
	var data = grid.selectGet();
    if (data.length == 0) {
        $.etDialog.error('请选择行');
    } else {
    	var param = [];
        $(data).each(function () {
            var rowdata=this.rowData
            param.push(rowdata.seq_code + "@" +
            		rowdata.seq_name + "@" +
            		rowdata.min_value + "@" +
            		rowdata.max_value + "@" +
            		rowdata.buffer_value + "@" +
            		rowdata.start_value + "@" +
            		rowdata.increment_value 
			      );           
        });

        $.etDialog.confirm('确定生成?', function () {
            ajaxPostData({
                url: 'createSeqManage.do',
                data: { param: param.toString() },
                success: function () {
                    query();
                }
                })
        });
    }
};
var remove= function () {
	var data = grid.selectGet();
    if (data.length == 0) {
        $.etDialog.error('请选择行');
    } else {
    	var param = [];
        $(data).each(function () {
            var rowdata = this.rowData;
            param.push(rowdata.seq_code);
        });

        $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deleteSeqManage.do',
                data: { paramVo: param.toString() },
                success: function () {
                    query();
                }
                })
        });
    }
};
var drop= function () {
	var data = grid.selectGet();
    if (data.length == 0) {
        $.etDialog.error('请选择行');
    } else {
    	var param = [];
        $(data).each(function () {
            var rowdata = this.rowData;
            param.push(rowdata.seq_code);
        });

        $.etDialog.confirm('确定删除序列?', function () {
            ajaxPostData({
                url: 'dropSeqManage.do',
                data: { paramVo: param.toString() },
                success: function () {
                    query();
                }
                })
        });
    }
};
var initGrid = function () {
    var columns = [
			{ display: '序列编码', name: 'seq_code', align: 'center', width: 130 },
			{ display: '序列名称', name: 'seq_name', align: 'center', width: 130 },
			{ display: '最小值', name: 'min_value', align: 'center', width: 130 },
			{ display: '最大值', name: 'max_value', align: 'center', width: 110},
			{ display: '缓冲大小', name: 'buffer_value', align: 'center', width: 110}	,
			{ display: '开始值', name: 'start_value', align: 'center', width: 110}	,
			{ display: '增量值', name: 'increment_value', align: 'center', width: 110}	
    ];
    var paramObj = {
        height: '100%',
        checkbox: true,
        showBottom:false,
        
        dataModel: {
            url: 'querySeqManage.do?isCheck=false'
        },      
        columns: columns,
        toolbar: {
            items: [
                { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                { type: 'button', label: '生成序列', listeners: [{ click: create }], icon: 'update' },
                { type: 'button', label: '删除序列', listeners: [{ click: drop }], icon: 'delete' }
            ]
        }
    };
    grid = $("#mainGrid").etGrid(paramObj);
};
</script>
</head>
<body>
<table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">序列编码：</td>
            <td class="ipt">
                <input id="seq_code" type="text" />
            </td>

            <td class="label" style="width: 100px;">序列名称：</td>
            <td class="ipt">
                <input id="seq_name" type="text" />
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>
</html>