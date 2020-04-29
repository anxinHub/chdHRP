<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,tree,grid,hr,upload,pageOffice" name="plugins" />
	</jsp:include>
	 <style>
            html,
            body {
                height: 100%;
                min-width: 900px
            }

            .grid_header .float_left {
                float: left;
                padding: 5px 10px;
            }

            .grid_header .float_right {
                float: right;
                padding: 5px 10px;
            }
            .leftContent{
           	    min-width: 220px;
   				max-width: 220px;
            }

            .main_title {
                /*background-color: #ededed;*/
                /* background:linear-gradient(to bottom,#EDEDED 0%, #98c4f7 50%,#ededed 100%); */
                /* height: 40px; */
                /* background: linear-gradient(#e2f0ff, #c1dbfa, #e2f0ff); */
                padding: 2px 10px;
                min-width: 220px;
                padding: 2px 10px;
               /* position: fixed; */
            }

            /* tab切换铺满 */

            .ettab-container {
                height: 100%;
                display: -webkit-flex;
                display: -ms-flex;
                display: flex;
            }

            .ettab-nav {
                padding: 0;
                margin: 0;
            }

            .ettab-nav .ettab-tab {
                padding: 0;
                margin: 0;
                width: 50%;
                box-sizing: border-box;
                height: 100%;
                text-align: center
            }
            .ettab-content {
                height: 95%;
                height: -moz-calc(100% - 28px);
                height: -webkit-calc(100% - 28px);
                height: calc(100% - 28px);
            }
            .ettab-content .ettab-panel {
                padding: 0;
            }

            /* 按钮样式 */

            button {
                box-sizing: border-box;
                height: 26px;
                padding-left: 10px;
                padding-right: 10px;
                border: 1px solid #aecaf0;
                background: #e5edf4;
                outline: none;
                border-radius: 2px;
                cursor: pointer;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
            }

            /* 主集操作区 */

            .main_operation {
                padding: 1px 2px;
                border: 1px solid #aecaf0;
                background: linear-gradient(#e2f0ff, #c1dbfa, #e2f0ff)
            }

            /* 子集表格标题样式 */

            #children_title {
                background-color: #ededed;
                background: linear-gradient(#e2f0ff, #c1dbfa, #e2f0ff);
                height: 25px;
                line-height: 25px;
                text-align: center;
                border-radius: 2px;
                font-weight: 800;
                border-top: 1px solid #aecaf0;
            }
           #tab_name{
    box-sizing: border-box;
    width: 120px;
    height: 26px;
    padding: 1px 1px 1px 5px;
    border: 1px solid #aecaf0;
    border-radius: 1px;
    outline: none;
           }
            /* tab样式覆盖 */

            .ettab-content .ettab-panel.ettab-active {
                display: flex;
                flex-direction: column;
            }

            /* ztree样式覆盖 */

         /*     .ztree {
                height: 1px;
            } */ 

            /* 主表标记查询字表 */

            tr.query-child>td.pq-grid-number-cell {
                /* border-bottom: 1px solid blue !important;
                border-top: 1px solid blue !important; */
                background-color: rgba(96, 96, 243, 0.472)
            }
        </style>
    <script>
        var tree, mainGrid, childGrid,empId,searchParam={},deptTreeParam={};
        
        // 职工表格查询
        var queryMain = function (dept_code) {

		var store_type_code = "01";
             
		var data = {main_code: "HOS_EMP", store_type_code: store_type_code,dept_code:dept_code==0 ? "" : "'"+dept_code+"'"};
				
		ajaxPostData({url:'queryEmpMainHead.do?isCheck=false', data:data, success:function (res) {// 获取数据

            	 mainGrid.option('columns', res.columns);

                 var main_param = [{ name:'tmpSQL',value:res.tmpSQL}];
                 
                 mainGrid.loadData(main_param,"queryEmpMainGrid.do?isCheck=false");
                 
                 mainGrid.refreshView();
                 
             }}); 
        };
        // 通过查询条件 重新加载主表格
        var reloadMainGrid = function () {
        	
            var store_type_code = "01";
            var tab_code='${tab_code}';
            var data = $.extend({main_code: "HOS_EMP", store_type_code: store_type_code,child_code:tab_code}, deptTreeParam,searchParam);
				
            ajaxPostData({url:'queryEmpMainHead.do?isCheck=false', data:data, success:function (res) {// 获取数据

           	 mainGrid.option('columns', res.columns);

                var main_param = [{ name:'tmpSQL',value:res.tmpSQL}];
                
                mainGrid.loadData(main_param,"queryEmpMainGrid.do?isCheck=false");
                
                mainGrid.refreshView();
                
            }}); 
        };
        // 职工 查询条件弹框
        function btn_search() {
    	   var tab_code='${tab_code}';
    	   parent.$.etDialog.open({
                title: '查询条件',
                height: 388,
                width: 800,
                frameName: window.name,
                btn: ["查询", "取消"],
                btn1: function (index, el) {
                    var frameWindow = parent.window[el.find('iframe').get(0).name];
                   
                    frameWindow.query(); //子页函数
                    parent.$.etDialog.close(index);
                },
                btn2: function (index) {
                	parent.$.etDialog.close(index);// 关闭弹窗
                    return false;
                },
                url: "hrp/hr/record/searchPage.do?isCheck=false&tab_code=HOS_EMP&store_type_code=01&child_code="+tab_code,
            });
        } 
        // 职工信息表格 查询
        var queryChild = function (emp_id) {

            var grid_rowData = mainGrid.selectGet();

            if (grid_rowData) {//封装main_grid 选中条数据
            	
            	var data = {
                		
					store_type_code:'01',
					tab_code:'${tab_code}',
					EMP_ID: grid_rowData[0].rowData.EMP_ID
                        
                };

                ajaxPostData({url:'queryEmpChildHead.do?isCheck=false', data:data, success:function (res) {// 获取数据
                    
                	childGrid.option('columns', res.columns);
                    
                    var child_param = [{ name:'tmpSQL',value:res.tmpSQL}];
                    
                    childGrid.loadData(child_param,"queryEmpChildGrid.do?isCheck=false");
                    
                    childGrid.refreshView();
                    
                    childGrid.commit();
                   
               }});
            
            }
        };
        // 保存
         function save() {
        	//alert(12)
            var main_grid_selected = mainGrid.selectGet()[0];
            if (!main_grid_selected) {
                $.etDialog.error('请选择职工')
            }
            //console.log(childGrid.getAllData())
            var child_added = childGrid.getAdded();
            var child_updated = childGrid.getUpdated();
            
            if (child_added.length === 0 && child_updated.length === 0) {
                return;
            }

            var addData = child_added.map(function (item) {
                return item.rowData;
            });
            var modData = child_updated.map(function (item) {
                return item.rowData;
            });
            //alert(JSON.stringify(addData));
            //alert(JSON.stringify(modData));
            ajaxPostData({
                url: 'addEmpSubItem.do?isCheck=false',
                data: {
                    'tab_code': '${tab_code}',
                    'store_type_code': '01',
                    'EMP_ID': main_grid_selected.rowData.EMP_ID,
                    'addData': JSON.stringify(addData),
                    'modData': JSON.stringify(modData)
                },
                success: function () {
                    
                }
            })
        };
        // 添加
          function add()  {
            childGrid.addRow();
        };
        // 删除
      function btn_c_delete() {
            var selectData = childGrid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
                param.push(item.rowData);
            })
			if(param.length > 0){
				
				
				 $.etDialog.confirm('确定删除?', function () {
			            ajaxPostData({
			                // url: '.do?isCheck=false',
			                url: 'deleteEmpGridItem.do?isCheck=false',
			                data: { paramVo: JSON.stringify(param) ,tab_code:'${tab_code}'},
			                success: function () {
			                    childGrid.deleteRows(selectData);
			                }
			            }) })
				
			}
            
        };
        // 打印设置
        var child_print_set = function () {
            
        };
        // 打印
        var child_print = function () {
        	if(!childGrid.getAllData() || childGrid.getAllData().length==0){
        		$.etDialog.error("暂无数据！");
    			return;
    		}
        	//alert(tree.getSelectedNodes()[0].id);
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
            		]  */}; 
        	var printPara={
              		title: '${tab_name}',//标题
              		columns: JSON.stringify(childGrid.getPrintColumns()),//表头
              		class_name: "com.chd.hrp.hr.service.base.HrCommonService",
           			method_name: "queryHrChildDataByPrint",
           			bean_name: "hrCommonService",
           			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
           			foots: '',//表尾需要打印的查询条件,可以为空 
           			EMP_ID: empId,
                	tab_code: '${tab_code}' ,
                	store_type_code: '01' 
               	};

            officeGridPrint(printPara);
        };
        // 导入
        var child_import = function () {
            
        };
        // 导出
        var child_export = function () {
            
        };

        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    // url: '.do?isCheck=false'
                    url: '../queryHosDeptDictTree.do?isCheck=false'
                },
                callback: {
                    onAsyncSuccess: function () {
                        var defaultNode = selectTreeDefault(tree);
                        queryMain(defaultNode.id);
                    },
                    onClick: function (event, treeId, treeNode) {
                        queryMain(treeNode.id);
                    }
                }
            })
        };
        
        var initGrid = function () {
            var mainGridParam = {
               /*  showTitle: true, */
             /*    title: ' 基本信息', */
                height: '50%',
                inWindowHeight: true,
                checkbox: true,
                selectionModel: {
                    type: 'row',
                    mode: 'single'
                },
                dataModel: {},
                columns: [],
                rowSelect: function (e, ui) {
                	empId=ui.rowData.EMP_ID;
                    queryChild(ui.rowData.EMP_ID);
                },
                rowUnSelect: function () {
                    childGrid.option('columns', []);
                    childGrid.option('dataModel.data', []);

                    childGrid.refreshView();
                },
                pageModel: false,
            };
            mainGrid = $("#mainGrid").etGrid(mainGridParam);

            var childGridParam = {
             /*    showTitle: true, */
           /*      title: '${tab_name}', */
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                dataModel: {},
                columns: [],
                pageModel: false,
            };
            childGrid = $("#childGrid").etGrid(childGridParam);
        };

        $(function () {
            initTree();
            initGrid();
            $("#mainTree").height($(window).height()-50);//获取树形菜单高度适配版本低浏览器滚动条
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
        	<div class="leftContent">
        	
	            <div class="left_pd left " >
	            <div class="search-form">
	                <label for="">快速定位：</label>
	                 <input type="text" class="text-input" id="search1_input" style="width:140px;">
	            </div>
	                <div id="tab"  >
	                     <div id="mainTree" class="flex-item-1" ></div>
	                </div>
	                <!-- <div class="container-bar"></div> -->
	            </div>
        	</div>
            <div class="center" style="padding:0">
                <div class='grid_header clearfix'>
                    <div class="main_operation">
                        <button onclick='btn_search()'>查询条件</button>
                    </div>
                    
                </div>
                <div id="mainGrid"></div>
                <div id="children_block">
                    <div class="grid_header clearfix">
	                    <div class="main_operation">
	                        <label for="">子集：</label>
                          <input  id="tab_name" type="text" value="${tab_name}"  disabled/>
                            <button onclick='save()'>保存</button>
                            <button onclick='add()'>添加</button>
                            <button onclick='btn_c_delete()'>删除</button>
	                      
	                    </div>
                    </div>
           
                    <div id="childGrid"></div>
                </div>
            </div>
        </div>
</body>

</html>