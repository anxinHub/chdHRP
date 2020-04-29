<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,pageOffice" name="plugins" />
    </jsp:include>

    <script>
        var station_name,/* tree, */grid;
       /*  var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: '../../queryDeptTree.do?isCheck=false'
                },
                callback: {
                    onClick: function () {
                        query();
                    }
                }
            });
        }; */
        
        function query() {
        	/* var selectedNode = tree.getSelectedNodes()[0];
            tab_code = selectedNode ? selectedNode.id : '';
            if(selectedNode){
            	var param = [
            	             { name: 'station_code', value: $("#station_code").val() },
            	             { name: 'station_name', value: $("#station_name").val() },
            	             { name: 'dept_id', value: tab_code}
            	];
            	grid.loadData(param);
            }else{ */
            	params = [
             				{ name: 'station_code', value: $("#station_code").val() },
                        	{ name: 'station_name', value: $("#station_name").val() }
                    	];
             	grid.loadData(params,'queryStationInfo.do');
            //}
        	
        }
        
        $(function () {
            initGrid();
            //initTree();
            query();
            // 给输入框绑定搜索树事件
            /* $(".text-input").on('keyup', function () {
                var $self = $(this)
                searchTree({
                    tree: tree,
                    value: $self.val(),
                    callback: function () {
                        $self.focus();
                    }
                })
            }) */
        })
        
        var initGrid = function () {
            var columns = [
                        { display: '岗位编码',name: 'station_code',width: 100,algin:'left',
                        	render: function (ui) {
                                var updateHtml =
                                    '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                        ui.cellData +
                                    '</a>'
                                return updateHtml;
                            }	
                        },
            			{ display: '岗位名称',name: 'station_name',width: 100,algin:'left'},
            			{ display: '部门信息',name: 'dept_name',width: 200,algin:'left'},
            			{ display: '职务名称',name: 'duty_name',width: 100,algin:'left'},
            			{ display: '岗位等级',name: 'station_level_name',width: 100,algin:'left'},
            			{ display: '是否停用',name: 'is_stop_name',width: 100,algin:'left',
            				render: function (ui) {
                            var cellData = ui.cellData;
                            if (cellData === "是") {
                                return  '<span style="color:red;"> 是</span>';
                            } else {
                                return '否';

                            }
                        }},
            			{ display: '备注',name: 'note',width: 100}
            ];
            var paramObj = {
                height: '100%',
                checkbox: true,
                inWindowHeight: '100%',
               /*  dataModel: {
                    url: ''
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        {type: 'button', label: '查询', listeners: [{click: query}],icon: 'search'},
                    	{type: 'button', label: '添加', listeners: [{click: add}],icon: 'add'},
                    	{type: 'button',label: '删除',listeners: [{click: remove}],icon: 'delete'},
                    	{type: 'button',label: '打印',listeners: [{click: print}],icon: 'print'},
                    	{type: 'button',label: '导入',listeners: [{click: importMainGrid}],icon: 'import'}
                    ]
                }
            };

            grid = $("#mainGrid").etGrid(paramObj)
            
            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getDataInPage()[rowIndex];
                var openParam = {
                		station_code: currentRowData.station_code
                };
                openUpdate(openParam);
            })
        };

        function add() {
        	/* var selectedNode = tree.getSelectedNodes()[0];
            dept_code = selectedNode ? selectedNode.id : ''; */
            
        	$.etDialog.open({
                //url: 'stationInfoAddPage.do?isCheck=false&dept_code='+dept_code,
                url: 'stationInfoAddPage.do?isCheck=false',
                title: '添加',
                width: 450,
                height: 500,
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save()
                }
            })
        }
		
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'stationInfoUpdatePage.do?isCheck=false&station_code=' +openParam.station_code,
                title: '修改',
                width: 450,
                height: 500,
                btn: [ '保存', '取消' ],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save();
                }
            })
        }
        
        function remove() {
        	var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
            	var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    param.push(//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.station_code );
                });

                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                        url: 'deleteStationInfo.do',
                        data: { paramVo: JSON.stringify(param) },
                        success: function () {
                            query();
                        }
                     })
                });
            }
        }
        
		//打印
        function print() {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads = {};
        	
        	var printPara={
          		title: "岗位信息",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.hr.service.organize.HosStationInfoService",
       			bean_name: "hosStationInfoService",
       			method_name: "queryStationInfoPrint",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: ''//表尾需要打印的查询条件,可以为空 
           	};
        	
           	$.each(grid.getUrlParms(),function(i,obj){
         		printPara[obj.name]=obj.value;
           	}); 
           	//console.log(printPara);
           	officeGridPrint(printPara);
        }
		
		//导入
        function importMainGrid() {
    		var para = {
    			"column" : [ {
    				"name" : "station_code",
    				"display" : "岗位编码",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "station_name",
    				"display" : "岗位名称",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "dept_code",
    				"display" : "部门信息",
    				"width" : "200"
    			},{
    				"name" : "duty_code",
    				"display" : "职务信息",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "station_level_code",
    				"display" : "岗位等级信息",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "is_stop",
    				"display" : "是否停用",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			}]
    		};
    		importSpreadView("/hrp/hr/organize/station/importDate.do?isCheck=false", para); 
        }
		
    </script>
</head>

<body>
    <div class="container">
        <!-- <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div> -->
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">岗位编码：</td>
                    <td class="ipt">
                        <input id="station_code" type="text" style="width: 180px;"/>
                    </td>

                    <td class="label">岗位名称：</td>
                    <td class="ipt">
                        <input id="station_name" type="text" style="width: 180px;"/>
                    </td>
                </tr>
            </table>

            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>