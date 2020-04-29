<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<% String plugins = request.getParameter("plugins");%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>人力资源管理</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, grid, tab, tree, hr, dialog,checkbox,upload,pageOffice" name="plugins" />
        </jsp:include>
        

 <script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
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

            /* tab样式覆盖 */
            .ettab-content .ettab-panel.ettab-active {
                display: flex;
                flex-direction: column;
            }

            /* 主表标记查询字表 */
            tr.query-child>td.pq-grid-number-cell {
                /* border-bottom: 1px solid blue !important;
                border-top: 1px solid blue !important; */
                background-color: rgba(96, 96, 243, 0.472)
            }
            
          	tr.query-child>td.pq-grid-cell{
           		background: #dcf8a8;
     
			}
	      	/* 图片放大查看 */
	      	.preView {
	      		position: fixed;
			    width: 100%;
			    height: 100%;
			    top: 0;
			    left: 0;
			    opacity: .5;
			    background-color: #000;
			    z-index: 99;
	      	}
	      	
	      	.preView-image {
      		    position: fixed;
			    width: 400px;
			    height: 400px;
			    top: 100px;
			    left: 50%;
			    margin-left: -200px;
			    z-index: 100;
	      	}
	      	
	      	.preView-image img {
      		    width: 100%;
	      	}
        </style>
        <script>
            var archives_select, main_select, child_select,
                main_grid, child_grid,
                dept_tree, child_tree, currRow,mainSQL,childSQL,searchParam={},deptTreeParam={};
            var is_write="${is_write}";
            var rowData;
            var mainAndChild;
            // 自定义查询 -- 数据暂存
            var CustomQueryStorage = function () {
                var o = {
                    data: {},
                    curKey: null, // 当前主集的id
                };
                // 切换主集
                o.changeKey = function (curKey) {
                    this.curKey = curKey || null;
                };
                // 保存数据
                o.saveData = function (value, key) {
                    var theKey = key || this.curKey;
                    this.data[theKey] = value;
                };
                // 还原数据
                o.clearData = function () {
                    this.data = {};
                    this.curKey = null;
                };
                return o;
            };
            var show_child_count = 0;
            var emp_id;
            var custom_query_storage = CustomQueryStorage();
           // ${sessionScope}
            
            // -----------------------------------主集表格参数分割线---------------------------------------
            var main_obj = {
            		
                height: '100%-220',
                
                inWindowHeight: true,
                
                checkbox: true,
                
                columns: [],
               // editable: true,
              selectionModel: {type: 'row'},
               
                isSaveColumn: false,  //是否显示保存查询列
                
                addMenus: [   //单元格右键增加菜单列
                    { id: 'photo', name: '照片', icon: 'view', click: photoClick },
                    { id: 'update', name: '修改', icon: 'view', click: updateClick },
                    { id: 'totalView', name: '一览统计', icon: 'view', click: totalView }
                ],
              rowDblClick: function (e, ui) {
                	
                	$('#dept_name').html('职工  : ' + ui.rowData.emp_name);
                	emp_id=ui.rowData.emp_id;
                    et_default.childGridQuery.main_grid = ui.rowData;
                    
                    reloadChildGrid(emp_id);
                    $("#children_block").show();
                    main_grid.option('height', '100%-180');
                    main_grid.refresh();
                    $('#show_child').html("隐藏子集");
                    
                    show_child_count = 1;
                    for (var i = 0, len = main_grid.getAllData().length; i < len; i++) {
                    	
                        main_grid.removeRowClass(i, 'query-child');
                        
                    }
                    
                    main_grid.addRowClass(ui.rowIndx, 'query-child');

                    currRow = ui.rowData;

                    child_grid.commit();
                }, 

                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                
                showBottom: false,
                
                selectionModel: { type: 'none', cbHeader: true, cbAll: true }
          
            };
            
          	//查看照片
    	    function photoClick(event,ui){
          		var o=ui.rowData.photo;
          		var url;
          		 if (o && Array.isArray(JSON.parse(o))) {
                     JSON.parse(o).forEach(function(t) {
                        url=t.url
                     })
                 }
    	    	  var file_path =url;
	        	  if(file_path == 'undefined' || file_path == '' || file_path == null){
						$.etDialog.error("暂无照片可预览");
	          			return;
	  		  	  }
	        	  imgSrc.src = file_path;
	        	  $("#preMain").show();
	        	  
	        	  $("#preMain").click(function(){
	        		  $("#preMain").hide()
	              })
          	};
          	//修改
    	    function updateClick(event,ui){
                
                var data = main_grid.selectGet();
                
                var tab_code = main_select.getValue();
                
                rowData = ui.rowData;
                
                parent.$.etDialog.open({
                    title: '修改',
                    url: "hrp/hr/pf/hosemp/hosEmpUpdatePage.do?isCheck=false&store_type_code=" + archives_select.getValue() + "&emp_id=" + rowData.emp_id + "&emp_code=" + rowData.emp_code +"&tab_code="+tab_code+ "&is_write=${is_write == null ? 0 : is_write}",
                    frameName :window.name,
                    isMax: true
                })
      		};
    	    //一览统计
    		function totalView(event,ui){
                
                var EMP_ID = ui.rowData.EMP_ID;
				
	           	var archives = archives_select.getValue();
	            rowData = ui.rowData;
            	if(archives.length == 0){$.etDialog.error('请选择档案库');return;}
            	
				
	            parent.$.etDialog.open({
	                title: '个人一览统计表',
	               url: "hrp/hr/pf/hosemp/totalDetailAddPage.do?isCheck=false&store_type_code=" + archives + "&emp_id=" + EMP_ID,
	                frameName: window.name,
	                isMax: true
	            })
      		}
			

            
            function reloadMainGrid(param) {// 远程加载主集表格 main_grid
            	   
                var value = main_select.getValue();
                var url;
            if(value!='HOS_EMP'){
            	//拼接动态子集查询
        		var  arr=value.toLowerCase().split("_");
        		url='query';
        	  for(var i=1;i< arr.length;i++){//遍历字符串
        		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
        	      }
        	  
            }else{
            	url='queryHosEmp';
            }
            url+='.do';
            var columns=getGridColumns({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:[value],design:url});
                var child = child_select.getValue();
                
                var store_type_code = archives_select.getValue();
/*
  做一些加载完成后需要执行的事情
 */ 
 
	 main_grid.option('columns',columns);
  var main_param = [];

	for(var key in searchParam){
		main_param.push({name: key,value: searchParam[key] })
	     
	    }

	for(var key in deptTreeParam){
		main_param.push({name: key,value: deptTreeParam[key] })
	     
	    }
	main_param.push({name:"tab_code",value:value});
	
	main_param.push({name:"store_type_code",value:store_type_code});
     main_grid.loadData(main_param,url+"?isCheck=false");
     
     main_grid.refreshView();
                   
                    
                //}}); 
            }
         	// -----------------------------------主集表格参数分割线---------------------------------------
         	
            // ----------------------------------子集表格参数分割线--------------------------------
            var child_obj = {
            		
                editable: true,
                
                height: '145',
                
                inWindowHeight: true,
                
                checkbox: true,
                
                columns: [],
                
                selectionModel: {type: 'row'},
                
                virtualY: false,
                
                pageModel: false,

                showBottom: false
            }
            
         
            function reloadChildGrid(ui) {// 远程加载子集表格 child_grid
                
                var query = et_default.childGridQuery
                
                var grid_rowData = query.main_grid || main_grid.getRowData(0);
            var   tab_code= query.tab_code || child_select.getValue();
 

                    	  
                    	  //拼接动态子集查询
                    		var  arr=tab_code.toLowerCase().split("_");
                    		 var url='query';
                    		 
                    	  for(var i=1;i< arr.length;i++){//遍历字符串
                    		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
                    	      }
                    	  url+='.do';
                          var columns=getGridColumns({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:[tab_code],design:url});
                          child_grid.option('columns', columns);
                       
                          var child_param = [{ name:'tab_code',value:tab_code}];
                          if(ui!=undefined){
                        	  child_param.push({ name:'emp_id',value:ui})
                     	     
                          }
                          
                          child_grid.loadData(child_param,url+"?isCheck=false");
                          
                          child_grid.refreshView();
                          
                          child_grid.commit();
 
            }
            // ----------------------------------子集表格参数分割线--------------------------------
            
            // ----------------------------------初始化树分割线--------------------------------
            function initTree() {
                // 加载部门树	               

                dept_tree = $("#dept_tree").etTree({
    	        	async: {
    	                enable: true,
    	                url: 'queryDeptTreeMod.do?isCheck=false&rjt='+"tree"
    	            },
    	        	data : {
    	    			simpleData : {
    	    				enable : true,
    	    				idKey : "id",
    	    				pIdKey : "pid",
    	    				rootPId : 0
    	    			},
    	    			keep : {
    	    				leaf : true
    	    			},
    	    			key : {
    	    				children : "nodes"
    	    			}
    	    		},
    	    		check: {
                		enable: true
                	},
    	    		/* edit : {
    	    			enable : true,
    	    			removeTitle : '删除',
    	    			showRemoveBtn : setShowBtn,
    	    			showRenameBtn : false
    	    		}, */
    	    		treeNode : {
    	    			open : false
    	    		},
    	            callback: {
    	            	
    	    			onClick : function(event, treeId, node) {
    	    				//node.id获取的为dept_id 如有必要 后期此处要重新写加载数据 并且修改后台buildQuerySQL 的查询条件
                        	var param = {};
                         	
    						var checkNodes = dept_tree.getCheckedNodes();
    						
                        	var dept_id  = '';
                        	
                        	if(checkNodes.length >0){
                        		
                            	$.each(checkNodes,function(index,value){ 
        							
        							if(value.id == 0){return;}
        							
        							dept_id+="'"+value.dept_id+"',"
        			        		
        		        		});
                        	}
							
    						if(node.id != 0){
    							
    							if(dept_id != ''){
    								
    								param = {'dept_id': dept_id + node.dept_id }
    								
    							}else{
    								
    								param = {'dept_id': node.dept_id }
    								
    							}
    						}
    						
    						deptTreeParam=param;
    						
                            reloadMainGrid();
                        },
                        onCheck: function (e, id, node) {
                        	
                         	var param = {};
                         	
    						var checkNodes = dept_tree.getCheckedNodes();
    						
                        	var dept_id  = '';
                        	
                        	$.each(checkNodes,function(index,value){ 
    							
    							if(value.id == 0){return;}
    							
    							dept_id+=value.dept_id+","
    			        		
    		        		});
                        	
    						param = {'dept_id': dept_id.substring(0,dept_id.length-1)}
  
    						deptTreeParam=param;
                        
                           reloadMainGrid();
                        },
    	            },
    	            
    	        });
    	        
                  
                // 加载子集树
                child_tree = $("#child_tree").etTree();
            }
         	// ----------------------------------初始化树分割线--------------------------------
         	
            // 页面全局默认值
            var et_default = {
                mainGridObj: main_obj,
                childGridObj: child_obj,
                mainGridQuery: {},
                childGridQuery: {main_grid: null,tab_code: null,emp_code: null}
            }

            function initMainBtn(){
         		if( "${is_perm}"==1){
         			 is_write = ${is_write == null ? 0 : is_write};
            	
            	if (is_write === 0) {
                    $(".main_operation>button").attr('disabled', true);
                } else {
                    $(".main_operation>button").attr('disabled', false);
                }
         		}
            }

            // 初始化下拉框
            function initSelect() {
                archives_select = $("#archives_select").etSelect({
                    url: '../../queryHrStoreType.do?isCheck=false',
                    showClear: false,
                    defaultValue:'01' ,
                    onInit: function (value) {
                    	
                    	
                        reloadChildTree(value);
                        reloadMainSelect(value);
                    },
                    onChange: function (value) {
                        reloadChildTree(value);
                        reloadMainSelect(value);
                        custom_query_storage.clearData();
                    }
                });
                main_select = $("#main_select").etSelect({
                    labelField: 'name',
                    showClear: false,
                    backEndSearch:false,
                    searchField:["name"],
                    maxOptions: '', 
                    onChange: function (value) {
                        custom_query_storage.changeKey(value);
                        if (!value) return;
                        reloadMainGrid();
                    }
                });
                child_select = $("#child_select").etSelect({
                    labelField: 'name',
                    showClear: false,
                    maxOptions: '', 
                    defaultValue:'0',
                    onInit: function (value) {
                        setChildrenTitle();
                    },
                    onChange: function (value) {
                        if (!value) return;
                        selectTreeNodeById(child_tree, value);
                        reloadChildGrid(emp_id);
                        setChildrenTitle();
                        setTimeout("setPerm('"+value+"')",1000);
                        
                    }
                });
            }

            // 初始化表格
            function initGrid() {
            	
                main_grid = $("#main_grid").etGrid(et_default.mainGridObj);
               
                child_grid = $("#child_grid").etGrid(et_default.childGridObj);
            }

            // 初始化事件
            function initEvent() {
                // 主集增加
                $("#btn_add").click(function () {
                	
                    	var param = [];
                    	mainAndChild=main_select.getValue();
                    parent.$.etDialog.open({
                        title: '添加',
                        url: "hrp/hr/pf/hosemp/hosEmpAddPage.do?isCheck=false&store_type_code=" + archives_select.getValue()+ "&is_write=${is_write == null ? 0 : is_write}",
                        isMax: true,
                        frameName: window.name
                    });
                });
                // 子集增加
                $("#add").click(function () {
                	
                    	var param = [];
                       mainAndChild= child_select.getValue();
                    parent.$.etDialog.open({
                        title: '添加',
                        url: "hrp/hr/pf/hosemp/hosEmpAddPage.do?isCheck=false&store_type_code=" + archives_select.getValue() + "&is_write=${is_write == null ? 0 : is_write}",
                        isMax: true,
                        frameName: window.name
                    });
                });
             	// 修改
                $("#btn_update").click(function () {
                	
                    var data = main_grid.selectGet();
                    
                    var tab_code = main_select.getValue();
                    
                    if (data.length == 0) {$.etDialog.error('请选择行');return;} 
                    
                    if(data.length > 1){$.etDialog.error('只能选择一行');return;}
                    
                    var rowData = data[0].rowData;
                    
                    parent.$.etDialog.open({
                        title: '修改',
                        url: "hrp/hr/pf/hosemp/hosEmpUpdatePage.do?isCheck=false&store_type_code=" + archives_select.getValue() + "&emp_id=" + rowData.emp_id + "&emp_code=" + rowData.emp_code +"&tab_code="+tab_code+ "&is_write=${is_write == null ? 0 : is_write}",
                        frameName :window.name,
                        isMax: true
                    })
                    
                });
                
             	// 批量修改
                $("#batch_update").click(function () {
                	
                    var batch_data = main_grid.getAllData();
                    
                    var tab_code = main_select.getValue();
                    
                    var child_code = child_select.getValue();
                    
                    var searchParam=searchParam;
                    
                    parent.$.etDialog.open({
                        title: '批量修改',
                        height: $(window).height(),
                        width:  $(window).width()-100,
                        frameName: window.name,
                        btn: ["确定", "取消"],
                        btn1: function (index, el) {
	                            var iframeWindow = parent.window[el.find('iframe').get(0).name];
	                            iframeWindow.saveUpdate();
	                            parent.$.etDialog.close(index);
                        },
                        btn2: function (index) {
                        	parent.$.etDialog.close(index); // 关闭弹窗
                            return false;
                        },
                        url: "hrp/hr/pf/hosemp/hosEmpUpatePage.do?isCheck=false&tab_code=" + main_select.getValue() + "&store_type_code=" + archives_select.getValue() + "&child_code=" + child_select.getValue(),
                    });
                });
                // 删除
                $("#btn_delete").click(function () {
                    var data = main_grid.selectGet();
                    var tab_code = main_select.getValue();
                    var url;
                    if(tab_code=="HOS_EMP"){
                    	url="deleteHosEmp.do?isCheck=false"
                    }else{
                    	var  arr=tab_code.toLowerCase().split("_");
               		  url='delete';
               	  for(var i=1;i< arr.length;i++){//遍历字符串
               		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
               	      }
               	  
               	  url+=".do?isCheck=false";
                    }
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                    } else {
                        var ParamVo = [];
                        $(data).each(function () {
                            var rowdata = this.rowData;
                            rowdata.store_type_code = archives_select.getValue();
                            rowdata.tab_code=tab_code;
                            ParamVo.push(rowdata);
                        });
                        if (ParamVo.length > 0) {  // 通过后台所删的数据
                            $.etDialog.confirm('确定删除?', function () {
                                ajaxPostData({
                                    url: url,
                                    data: {
                                        paramVo: JSON.stringify(ParamVo),
                                        tab_code: tab_code
                                    },
                                    success: function (res) {
                                        if (res.state == "true") {
                                            reloadMainGrid();
                                        }
                                    }
                                })
                            });
                        }
                    }
                });

                // 删除子集
                $("#btn_c_delete").click(function () {
                    var data = child_grid.selectGet();
                    var tab_code = child_select.getValue();
                    var url;
                    if(tab_code=="HOS_EMP"){
                    	url="deleteHosEmp.do?isCheck=false"
                    }else{
                    	var  arr=tab_code.toLowerCase().split("_");
               		  url='delete';
               	  for(var i=1;i< arr.length;i++){//遍历字符串
               		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
               	      }
               	  
               	  url+=".do?isCheck=false";
                    }
                    if (data.length == 0) {
                        $.etDialog.error('请选择行');
                    } else {
                        var ParamVo = [];
                        $(data).each(function () {
                            var rowdata = this.rowData;
                            rowdata.store_type_code = archives_select.getValue();
                            rowdata.tab_code=tab_code;
                            ParamVo.push(rowdata);
                        });
                        if (ParamVo.length > 0) {  // 通过后台所删的数据
                            $.etDialog.confirm('确定删除?', function () {
                                ajaxPostData({
                                    url: url,
                                    data: {
                                        paramVo: JSON.stringify(ParamVo),
                                        tab_code: tab_code
                                    },
                                    success: function (res) {
                                        if (res.state == "true") {
                                            // reloadChildGrid();
                                            child_grid.deleteSelectedRows();
                                        }
                                    }
                                })
                            });
                        }
                    }
                });

                // 查询
                $("#btn_search").click(function () {
                	parent.$.etDialog.open({
                        title: '查询',
                        height: 588,
                        width: 800,
                        frameName: window.name,
                        btn: ["查询", "取消"],
                        btn1: function (index, el) {
                              //子页函数
                            var iframeWindow = parent.window[el.find('iframe').get(0).name];
                            iframeWindow.query();
                            parent.$.etDialog.close(index);
                        },
                        btn2: function (index) {
                        	parent.$.etDialog.close(index);// 关闭弹窗
                            return false;
                        },
                        //url: "hrp/hr/pf/hosemp/searchAddPage.do?isCheck=false&tab_code=" + main_select.getValue() + "&store_type_code=" + archives_select.getValue()+ "&child_code=" + child_select.getValue(),
                        url: "hrp/hr/pf/hosemp/searchAddPage.do?isCheck=false",

                	});
                });
                // 自定义查询
                $("#btn_custom").click(function () {
                    var tab_code = main_select.getValue();
                    var tab_name = main_select.getText();

                    custom_dialog = $.etDialog.open({
                        title: '自定义查询',
                        height: 500,
                        width: 900,
                        maxmin: true,
                        id: 'selfSearch',
                        btn: ["查询", "取消"],
                        btn1: function (index, el) {
                            var frameWindow = window[el.find('iframe')[0].name];
                            frameWindow.query(); //子页函数
                        },
                        btn2: function (index) {
                            $.etDialog.close(index); // 关闭弹窗
                            return false;
                        },
                        url: "customSearchUpatePage.do?isCheck=false&tab_code=" + tab_code + "&tab_name=" + tab_name + "&store_type_code=" + archives_select.getValue()
                    });
                });

                // 部门树定位
                $("#search1_input").keyup(function (e) {
                    var _this = this;
                    searchTree({
                        tree: dept_tree,
                        value: this.value,
                        callback: function (node) {
                        }
                    });
                });

                // 子集树定位
                $("#search2_input").keyup(function (e) {
                    var _this = this;
                    searchTree({
                        tree: child_tree,
                        value: this.value,
                        callback: function (node) {
                            child_select.setValue(node.id);
                        }
                    });
                });

                // 展开收起主集按钮
                var main_show_button_count = 1;
                $("#main_show_button").click(function () {
                    main_show_button_count++;
                    if (main_show_button_count % 2 === 0) {
                        $(this).html("<<展开");
                        $("#main_button_group button").css('visibility', 'hidden');
                    } else {
                        $(this).html(">>收起");
                        $("#main_button_group button").css('visibility', '');
                    }
                });

                // 展开收起子集按钮
                var child_show_button_count = 1;
                $("#child_show_button").click(function () {
                    child_show_button_count++;
                    if (child_show_button_count % 2 === 0) {
                        $(this).html("<<展开");
                        $("#child_button_group button").css('visibility', 'hidden');
                    } else {
                        $(this).html(">>收起");
                        $("#child_button_group button").css('visibility', '');
                    }
                });

               
                $('#show_child').click(function () {
                    show_child_count++;
                    if (show_child_count % 2 === 0) {
                        $(this).html("显示子集");
                        $("#children_block").hide();
                        main_grid.option('height', '100%');
                        main_grid.refresh();
                    } else {
                        $(this).html("隐藏子集");
                        $("#children_block").show();
                        main_grid.option('height', '100%-180');
                        main_grid.refresh();
                    }
                })

                // 简单统计
                $("#btn_statistic_custom").click(function () {    	
                	var archives = archives_select.getValue();
                	if(archives.length == 0){$.etDialog.error('请选择档案库');}
                    parent.$.etDialog.open({
                        title: '简单统计',
                        url: "hrp/hr/statistic/simple/hrSimpleStatisticsMainPage.do?isCheck=false&store_type_code=" + archives,
                        isMax: true,
                        frameName: window.name
                    });
                });
            }

            /* 逻辑操作函数 */
            // 远程加载子集树 child_tree
            function reloadChildTree(value) {
                var child_url = '../../queryHrStoreTabStruc.do?isCheck=false&ignore=HOS_EMP&store_type_code=' + value;
                child_tree = $("#child_tree").etTree({
                    async: {
                        enable: true,
                        url: child_url
                    },
                    callback: {
                        onClick: function (e, id, node) {
                        	if(show_child_count!=0&&show_child_count % 2 != 0){
                        		  child_select.setValue(node.id);
                                  
                                  show_child_count = 1;
                        	}
                          
                           
                        }
                    },
                    addSuffix: function () {
                        var treeNodes = child_tree.transformToArray(child_tree.getNodes());
                        return {
                            nodes: treeNodes,
                            rules: [
                               /*  { if("${MyConfig.getSysPara("06005")}"==1) */{
                                	rule: { is_write: 0 }, text: '只读', color: 'red' 
                                	}
                            	/* } */
                            ]
                        }
                    }
                });
            }
            
       /*      // 重新封装列数据
            Array.prototype.renderColumns = function(){
            	$(this).each(function(index,value){
            		if(value["disabled"] === 1){
            			value.editable = function(ui){
            				const editable = ui.rowData?!ui.rowData["EMP_ID"]:true;
            				return editable;
            			}
            		}
            	})
            } */

            // 远程加载主集下拉框 main_select,子集下拉框 child_select
            function reloadMainSelect(value) {
                main_select.reload({
                    url: '../../queryHrStoreTabStruc.do?isCheck=false&store_type_code=' + value
                });
                child_select.reload({
                    url: '../../queryHrStoreTabStruc.do?isCheck=false&ignore=HOS_EMP&store_type_code=' + value
                });
            };

            //修改员工信息
            function update(index) {
                var rowData = main_grid.getRowData(index);
                parent.$.etDialog.open({
                    title: '修改',
                    url: "hrp/hr/pf/hosemp/hosEmpAddPage.do?isCheck=false&store_type_code=" + archives_select.getValue() + "&EMP_ID=" + rowData.EMP_ID + "&is_write=${is_write == null ? 0 : is_write}",
                    frameName: window.name,
                    isMax: true
                })
            }

            //一览统计
            function totalDetail() {
            	
            	var archives = archives_select.getValue();
            	
            	if(archives.length == 0){$.etDialog.error('请选择档案库');return;}
            	
                var data = main_grid.selectGet();
                
                if (data.length == 0) { $.etDialog.error('请选择行');} 
                else {
                    var rowData = data[0].rowData;
                    parent.$.etDialog.open({
                        title: '个人一览统计表',
                        //url: "hrp/hr/pf/hosemp/totalDetailAddPage.do?isCheck=false",
                        url: "hrp/hr/pf/hosemp/totalDetailAddPage.do?isCheck=false&store_type_code=" + archives + "&emp_id=" + rowData.EMP_ID,
                        frameName: window.name,
                        isMax: true
                    })
                }
            }

            function exportMainGrid() {exportGrid(main_grid);}

            function exportChildGrid() {exportGrid(child_grid);}

            function setChildrenTitle() {
                var title = child_select.getText();
                $("#children_title").html(title);
            }

            // 下拉框上一条
            function prevSelect() {
                var options = child_select.getOptions();
                var current = child_select.getValue();
                var arrOptions = Object.keys(options).sort(function (a, b) {
                    return options[a].$order - options[b].$order;
                });
                var len = arrOptions.length;
                var prevIndex = arrOptions.indexOf(current) - 1;
                child_select.setValue(arrOptions[(prevIndex + len) % len]);
            }

            // 下拉框下一条
            function nextSelect() {
                var options = child_select.getOptions();
                var current = child_select.getValue();
                var arrOptions = Object.keys(options).sort(function (a, b) {
                    return options[a].$order - options[b].$order;
                });
                var len = arrOptions.length;
                var prevIndex = arrOptions.indexOf(current) + 1;
                child_select.setValue(arrOptions[(prevIndex + len) % len]);
            }

          /*   //添加行
            function add() {
            	if(currRow){
            	child_grid.addRowByDefault();
            	}
            	} */
			
            //打印子集
            function print_child(){
            	if(!child_grid.getAllData() || child_grid.getAllData().length==0){
            		$.etDialog.error("暂无数据！");
        			return;
        		}
            	  var value = child_select.getValue();
                  var url;
              if(value!='HOS_EMP'){
              	//拼接动态子集查询
          		var  arr=value.toLowerCase().split("_");
          		url='query';
          	  for(var i=1;i< arr.length;i++){//遍历字符串
          		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
          	      }
          	  
          	  url+=".do";
              }else{
              	url='queryHosEmp.do';
              }
            	var heads={}; 
            	var printPara={
                  		title: child_select.getText(),//标题
                  		columns: JSON.stringify(child_grid.getPrintColumns()),//表头
                  		class_name: "com.chd.hrp.hr.service.QueryService",
               			bean_name: "queryService",
               			method_name: "queryBaseInfoPtint",
               			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
               			foots: '',//表尾需要打印的查询条件,可以为空 
               			EMP_ID: et_default.childGridQuery.main_grid.EMP_ID,
                    	tab_code: child_select.getValue(),
                    	store_type_code: archives_select.getValue()
                   	};
            	//执行方法的查询条件
    	   		printPara['design_code']=url;
                officeGridPrint(printPara);	
            }

            //保存子表
            function save() {
                var tab_code = child_select.getValue();
                if (currRow) {
                    if (tab_code != null && tab_code != '') {

                        var addData = child_grid.getAdded(); //添加数据
                        var modData = child_grid.getUpdated(); //修改数据'

                        var addDataVo = [];
                        var modDataVo = [];
                        $(addData).each(function () {
                            addDataVo.push(this.rowData);
                        });
                        $(modData).each(function () {
                            modDataVo.push(this.rowData);
                        });

                        ajaxPostData({
                            url: 'addEmpSubItem.do?isCheck=false',
                            data: {
                                'tab_code': tab_code,
                                'store_type_code': archives_select.getValue(),
                                'EMP_ID': currRow.EMP_ID,
                                'EMP_CODE': currRow.EMP_CODE,
                                'EMP_NAME': currRow.EMP_NAME,
                                'addData': JSON.stringify(addDataVo),
                                'modData': JSON.stringify(modDataVo),
                            },
                            delayCallback: true,
                            success: function (data) {
                                child_grid.commit();
                                reloadChildGrid();
                            }
                        })
                    } else {
                        $.etDialog.error('请选择子集菜单');
                    }
                } else {
                    $.etDialog.error("请单选主集行数据");
                }
            }
            function saveUpdate(param) {
            	var ParamVo = [];
            	  var batch_data = main_grid.getAllData();
                  
                  var tab_code = main_select.getValue();
                  
                  var child_code = child_select.getValue();
                  mainSQL =mainSQL;
                  if(batch_data==null){
                	  $.etDialog.error("没有需要修改的数据");
                	  return ;
                  }
                //  var main_param = [{ name:'tmpSQL',value:res.tmpSQL}];
                  batch_data.forEach(function (item) {
                    
                      ParamVo.push({emp_id : item.EMP_ID});
                  });
                  ajaxPostData({
                      url: 'batchEmpUpate.do?isCheck=false',
                      data: {
                          'table_code': tab_code,
                          'store_type_code': archives_select.getValue(),
                          'child_code':child_code,
                         /*  'allData': JSON.stringify(ParamVo), */
                          'modData': JSON.stringify(param),
                          'mainSQL' :mainSQL
                      },
                      delayCallback: true,
                      success: function (data) {
                          
                          reloadChildGrid();
                      }
                  })
            }
            function setPerm(treeId) {
                var node = child_tree.getNodesByParam('id', treeId)[0];
                if(node){
                	if (node.is_write === 0) {
                        $("#children_block .float_left>button").attr('disabled', true);
                    } else {
                        $("#children_block .float_left>button").attr('disabled', false);
                    }
                }else{
                	$("#children_block .float_left>button").attr('disabled', true);
                }
                
            }
            function print(){

            	if(main_grid.getAllData()==null){
            		
            		$.etDialog.error("请先查询数据！");
        			
        			return;
        		}
            	   var value = main_select.getValue();
                   var url;
               if(value!='HOS_EMP'){
               	//拼接动态子集查询
           		var  arr=value.toLowerCase().split("_");
           		url='query';
           	  for(var i=1;i< arr.length;i++){//遍历字符串
           		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
           	      }
           	  
           	  url+=".do";
               }else{
               	url='queryHosEmp.do';
               }
            	var heads={
            	};  
            
            	
            	var printPara={
                  		title: "人员信息列表",//标题
                  		columns: JSON.stringify(main_grid.getPrintColumns()),//表头
               			class_name: "com.chd.hrp.hr.service.QueryService",
               			bean_name: "queryService",
               			method_name: "queryBaseInfoPtint",
               			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
               			foots: '',//表尾需要打印的查询条件,可以为空 
               			tmpSQL : mainSQL
                   	};
            	//执行方法的查询条件
    	   		printPara['design_code']=url;
                	officeGridPrint(printPara);
        	}
            
            function importDate(){
            	 var value = main_select.getValue();
           
            	 parent.$.etDialog.open({
                    url: "hrp/hr/pf/hosemp/hosEmpImprotPage.do?isCheck=false&store_type_code=" + archives_select.getValue() + "&tab_code=" + value+"&ui="+"et",
                    frameName: window.name,
                    isMax: true
                })
            }
            
            function importDateChild(){
            	  var value = child_select.getValue();
           
          	 parent.$.etDialog.open({
                 url: "hrp/hr/pf/hosemp/hosEmpImprotPage.do?isCheck=false&store_type_code=" + archives_select.getValue() + "&tab_code=" + value+"&ui="+"et",
                 frameName: window.name,
                 isMax: true
             })
             }
         // 加载页面 
            $(function () {
            	
                tab = $("#tab").etTab();// 初始化标签
           
                initSelect(); // 初始化下拉框数据
             
                initTree();// 初始化树形结构
                
                initGrid();// 初始化表格
             
                initEvent();// 初始化事件
       
                initMainBtn();// 初始化主集按钮
                
                $("#children_block").hide();
                
                main_grid.option('height', '100%');
                $("#dept_tree").height($(window).height()-105);//获取树形菜单高度适配版本低浏览器滚动条
                $("#child_tree").height($(window).height()-105);//获取树形菜单高度适配版本低浏览器滚动条
       /*          if("${main_select}"==1){main_select.enabled();}
                
                else{main_select.disabled();} */
                
            });
        </script>
    </head>

    <body>
        
        <div class="container">
        	<div class="leftContent">
        		<div class="main_title">
		            <label for="">档案库：</label>
		            <select name="" id="archives_select" style="width:155px;"></select>
		    	</div>
	            <div class="left_pd left" style="height:95%;">
	                <div id="tab">
	                    <div title="部门" tabid="0">
	                        <div class="search-form">
	                            <label for="">快速定位：</label>
	                            <input type="text" class="text-input" id="search1_input" style="width:140px;">
	                        </div>
	                        <div id="dept_tree" class="flex-item-1"></div>
	                    </div>
	                    <div title="子集" tabid="1">
	                        <div class="search-form">
	                            <label for="">快速定位：</label>
	                            <input type="text" class="text-input" id="search2_input" style="width:140px;">
	                        </div>
	                        <div id="child_tree" class="flex-item-1"></div>
	                    </div>
	                </div>
	                <!-- <div class="container-bar"></div> -->
	            </div>
        	</div>
            <div class="center" style="padding:0">
                <div class='grid_header clearfix'>
                    <div class="main_operation">
                        <label for="">主集：</label>
                        <select name="" id="main_select" style="width:180px;"  ></select>
                        <button id="btn_add">增加</button>
                        <button id="btn_update">修改</button>
                        <!--button id="batch_update">批量修改</button-->
                        <button id="btn_delete">删除</button>
                        <div style="float:right;padding-right:10px;" id="main_button_group">
                          <!--  <button onclick="setPrint()">打印设置</button> -->
                            <button onclick="print()">打印</button>
                <!--             <button id="btn_statistic_custom">简单统计</button> -->
                            <!-- <button onclick="">自定义报表</button> -->
                            <button onclick="totalDetail()">一览统计</button>
                            <button onclick="importDate()">导入</button>
                            <!-- <button onclick="exportMainGrid()" hotkey="E" >导出(E)</button> -->
                            
                        <button id="btn_search">查询条件</button>
                        <!--button id="btn_custom">自定义查询条件</button-->
                        <button id="show_child">显示子集</button>
                        <a href="javascript:;" id="main_show_button" style="text-decoration:none">>>收起</a>
                        </div>
                    </div>
                    
                </div>
                <div id="main_grid"></div>
                <div id="children_block">
                    <div class="grid_header clearfix">
	                    <div class="main_operation">
	                        <label for="">子集：</label>
                            <a style="text-decoration:none" href="javascript:;" onclick="prevSelect()">< </a>
                            <select name="child_select" id="child_select" style="width:180px;"></select>
                            <a style="text-decoration:none" href="javascript:;" onclick="nextSelect()">></a>
                           <!--  <button onclick='save()'>保存</button> -->
                            <button id='add'>添加</button>
                            <button id='btn_c_delete'>删除</button>
                            <button onclick="importDateChild()">导入</button>
                            <span id="dept_name" style="color: red;margin-left: 5px"></span>
	                        <div style="float:right;padding-right:10px;" id="child_button_group">
	                          	<!-- <button onclick="">打印设置</button> -->
                            	<button onclick="print_child()">打印</button>
                            	<!-- <button onclick="">导入</button> -->
                            	<!-- <button onclick="exportChildGrid()">导出</button> -->
                            	<a href="javascript:;" id="child_show_button" style="text-decoration:none">>>收起</a>
	                        </div>
	                    </div>
                    </div>
                    <!--  div id="children_title"></div-->
                    <div id="child_grid"></div>
                </div>
            </div>
        </div>
        
       	<!-- 图片查看 -->
       	<div id="preMain" style="display:none">
			<div class="preView"></div>
			<div class="preView-image">
			<img src="" id="imgSrc"></div>
		</div>
    </body>

    </html>