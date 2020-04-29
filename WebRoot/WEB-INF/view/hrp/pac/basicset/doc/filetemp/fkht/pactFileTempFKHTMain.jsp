<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,tree,grid,hr,upload,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var tree, mainGrid, childGrid,empId,pids;
        
        var query = function () {
            params = [];
            
            grid.loadData(params);
        };

        
        //跳转保存页面
        var addFileType = function (data) {
        	 $.etDialog.open({
                 url: 'PactFileTempFKHTInsert.do?isCheck=false&pid='+pids,
                 modal: true,
                 width: 800,
                 height: 550,
                 btn1: function (index, el) {
                     var iframeWindow = window[el.find('iframe').get(0).name];
                     iframeWindow.save()
                 }
             });
        };
        
        
        
       	 var initGrid = function () {
                var columns = [
                	 { display: '归档项目编码', name: 'type_code',width: '15%',editable: false},
                     { display: '归档项目名称', name: 'type_name',width: '19%',editable: false},
                     { display: '备注', name: 'note', width: '15%',editable: true,hidden:false}
                ];
                var paramObj = {
                	editable: true,
                	height: '100%',
                	width:'100%',
                	checkbox: false,
                    columns: columns,
                    toolbar: {
                        items: [
                          // { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                           { type: 'button', label: '归档模板设置', listeners: [{ click: addFileType }], icon: 'add' }
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(paramObj);
            };

        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    // url: '.do?isCheck=false'
                    url: 'queryPactTypeFKHT.do?isCheck=false'
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                    	var pid = treeNode.pId;
                    	
                    	pids = treeNode.id;
                    	//alert(pids);
                    	var params=[{name:'pact_type_code',value:treeNode.id}];
                    	grid.loadData(params,'queryPactFileTempFKHTfile.do?isCheck=false');

                    	//var pid = treeNode.pId;
                    	
                    },
					 onNodeCreated:function(event, treeId, treeNode){
						 tree.expandAll(true);
                    }
                }
            })
        };
        
        
        $(function () {
            initTree();
            initGrid();
              
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
            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>