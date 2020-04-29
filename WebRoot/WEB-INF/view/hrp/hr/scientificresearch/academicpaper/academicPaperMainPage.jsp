<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,tree,grid" name="plugins" />
	</jsp:include>
    <script>
        var tree, memberGrid, academicGrid;
        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    // url: '.do?isCheck=false'
                    url: 'http://118.178.184.131:9090/tree'
                },
                callback: {
                    onClick: memberGridQuery
                }
            })
        };
        var memberGridQuery = function () {
            var params = [
                { name: '', value: '' }
            ];
            memberGrid.loadData(params);
        };
        var MemberGridSave = function () {
            
        };
        var MemberGridPrintSet = function () {};
        var MemberGridPrintPreview = function () {};
        var MemberGridPrint = function () {};
        var initMemberGrid = function () {
            var columns = [
                { display: '职工工号', name: 'number1', width: 120 },
                { display: '职工姓名', name: 'name1', width: 120 },
                { display: '科室名称', name: 'a', width: 120 },
            ];
            var paramObj = {
                height: '50%',
                inWindowHeight: true,
                checkbox: true,
                title: '人员基本情况',
                showTitle: true,
                selectionModel: {
                    type: 'row',
                    mode: 'single'
                },
                dataModel: {
                    // url: '.do?isCheck=false'
                    url: 'http://118.178.184.131:9090/static_column/grid'
                },
                rowClick: function () {
                    academicGridQuery();
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '保存', listeners: [{ click: MemberGridSave }], icon: 'save' },
                        { type: 'button', label: '打印设置', listeners: [{ click: MemberGridPrintSet }], icon: 'set' },
                        { type: 'button', label: '打印预览', listeners: [{ click: MemberGridPrintPreview }], icon: 'preview' },
                        { type: 'button', label: '打印', listeners: [{ click: MemberGridPrint }], icon: 'print' },
                    ]
                }
            };
            memberGrid = $("#memberGrid").etGrid(paramObj);
        };

        var academicGridQuery = function () {
            var params = [
                { name: '', value: '' }
            ];
            // academicGrid.loadData(params, '.do?isCheck=false');
            academicGrid.loadData(params, 'http://118.178.184.131:9090/static_column/grid');
        };
        var academicGridAdd = function () {
            var memeberData = memberGrid.selectGet();
            
            if (memeberData.length === 0) {
                $.etDialog.error('请先选择人员');
                return;
            }

            $.etDialog.open({
                url: 'addAcademicHonorsPage.do?isCheck=false',
                title: '添加',
                width: 450,
                height: 450,
                btn: [ '保存', '取消' ],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save();
                }
            })
        };
        var academicGridRemove = function () {
            var academicData = academicGrid.selectGet();

            if (academicData.length === 0) {
                $.etDialog.error('请先选择项目');
                return;
            }

            var param = [];
            academicData.forEach(function (item) {
                param.push({
                    kind_code: item.rowData.kind_code
                });
            })

            ajaxPostData({
                url: 'http://118.178.184.131:9090/delete?id=1',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    academicGrid.deleteRows(academicData);
                }
            })
        };
        var academicGridPrintSet = function () {};
        var academicGridPrint = function () {};
        var academicGridPutOut = function () {};
        var academicGridPutIn = function () {};
        var initAcademicGrid = function () {
            var columns = [
                { display: '论文类别', name: 'e', width: 120 },
                { display: 'SCI学术论文', name: 'd', width: 120 },
                { display: '影响因子', name: 'number1', width: 120 },
                { display: '发表时间', name: 'date1', width: 120 },
                { display: '附件', name: 'c', width: 120 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                title: '学术论文发表',
                showTitle: true,
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '添加', listeners: [{ click: academicGridAdd }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: academicGridRemove }], icon: 'delete' },
                        { type: 'button', label: '打印设置', listeners: [{ click: academicGridPrintSet }], icon: 'set' },
                        { type: 'button', label: '打印', listeners: [{ click: academicGridPrint }], icon: 'print' },
                        { type: 'button', label: '导出', listeners: [{ click: academicGridPutOut }], icon: 'export' },
                        { type: 'button', label: '导入', listeners: [{ click: academicGridPutIn }], icon: 'import' },
                    ]
                }
            };
            academicGrid = $("#academicGrid").etGrid(paramObj);
        };

        $(function () {
            initTree();
            initMemberGrid();
            initAcademicGrid();

            // 给输入框绑定搜索树事件
            $(".text-input").on('keyup', function () {
                var $self = $(this)
                searchTree({
                    tree: tree,
                    value: $self.val(),
                    callback: function () {
                        $self.focus();
                    }
                })
            })
        })
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text">
            </div>
            <div id="mainTree"></div>
        </div>
        <div class="center">
            <div id="memberGrid"></div>
            <br />
            <div id="academicGrid"></div>
        </div>
    </div>
</body>

</html>