<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>预算管理</title>
        <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, grid, tab, tree, hr, dialog,checkbox,upload,pageOffice,datepicker" name="plugins" />
        </jsp:include>
        </head>
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
	      
	    
	      	
	      	
        </style>
          <script>
          var grid,budg_year,child_grid,searchParam,mod_code;
          $(function () {
        	  initDict();
        	  initGrid();
        	   initTree();
               initChildCrid();
        	 
              $("#search-tree").on('keyup', function(){
      			var $self = $(this);
      			searchTree({
      				tree: tree,
      				value: $self.val(),
      				callback: function(){
      					$self.focus();
      				}
      			});
      		});
              $("#children_block").hide();
              
              grid.option('height', '100%');
         
 
              $("#child_show_button").click(function () {
            	   grid.option('height', '100%');
                   grid.refresh();
              });
              
              // 查询
              $("#btn_search").click(function () {
            	  var select=tree.getSelectedNodes()[0];
              	parent.$.etDialog.open({
                      title: '查询',
                      height: 288,
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
                      url: "hrp/budg/budgsysset/budgsyssetsearchPage.do?isCheck=false&plan_code="+select.id+'&budg_year='+budg_year.getValue()+"&mod_code="+mod_code,
                  });
              });
          })
          
          function initDict(){
          budg_year = $("#budg_year").etDatepicker({

        		  view: "years",
        		  minView: "years",
        		  dateFormat: "yyyy",
        		  defaultDate: true,
        		   /* onChange : query */
        		  onChange:function (){
        			  initTree();
        			  $("#children_block").hide();
                      
                      grid.option('height', '100%');
        		  }
            });// 编制日期
            
            }
          function reloadChildGrid(grid_rowData) {// 远程加载子集表格 child_grid
        	  var select=tree.getSelectedNodes()[0];

              if (grid_rowData) {//封装main_grid 选中条数据
            	  mod_code=grid_rowData.mod_code;
              
              	 params = [
                           { name: 'plan_code', value: select.id},
                           { name: 'mod_code', value: grid_rowData.mod_code },
                           { name: 'budg_year', value: budg_year.getValue()},
                           { name: 'link_code', value: grid_rowData.link_code},

                       ];
                child_grid.loadData(params,"queryBudgCDet.do");
                
                child_grid.refreshView();
                
                child_grid.commit();
              
              }

          }
          function initTree() {
              tree = $("#mainTree").etTree({
              	async: {
                      enable: true,
                      url: '../../queryBudgCPlan.do?isCheck=false&year='+budg_year.getValue()
                  },
           
          		/* edit : {
          			enable : true,
          			removeTitle : '删除',
          			showRemoveBtn : setShowBtn,
          			showRenameBtn : false
          		}, */
          		treeNode : {
          			open : true
          		},
                  callback: {
                	  
                	  onAsyncSuccess: function (event, treeId, treeNode, msg) {
                          var selectedNode = selectTreeDefault(tree);

                          query(selectedNode.id);
                      },
                  	onNodeCreated : function(event, treeId, node) {
          				tree.expandNode(node, true, false, false);
          				if (node.nodes && node.level === 0 && node.nodes.length === 0) {
          					treeObj.hideNode(node);
          				}
          			
          			},
          			onClick : function(event, treeId, node) {
                      	query(node.id);
                        $("#children_block").hide();
                        
                        grid.option('height', '100%');
                      }
                  },
                 
              });
              
        /*       hos_name = $("#hos_name").etSelect({
                  url: '../../queryHosInfoSelect.do?isCheck=false',
                  defaultValue: '15'
              }); */
          };
        
          var create = function (){
        	  var select=tree.getSelectedNodes()[0];
        	 
            /*   var budg_year= budg_year.getValue();
              if(budg_year==null){
              	  $.etDialog.error('请选择要生成的年度');
              	  return;
              }else{ */

                $.etDialog.confirm('确定初始化数据?', function () {
                	ajaxPostData({
                        url: 'initBudgSysSetControl.do',
                        data: {
                        	'budg_year': budg_year.getValue(),
                            'plan_code' :select.id
                        },
                        success: function () {
                            query();
                        }
        			});
                });
              /* } */
            }
          var uncheck = function (){
        	  var data = grid.selectGet();
              if (data.length == 0) {$.etDialog.error('请选择行');return;} 
              
              if(data.length > 1){$.etDialog.error('只能选择一行');return;}
              var rowData = data[0].rowData;
              var param = [];
              $(data).each(function () {
                  var rowdata = this.rowData;
                  param.push(rowdata);
              });
              $.etDialog.confirm('确定停用?', function () {
                  ajaxPostData({
                      url: 'uncheckBudgSysSetControl.do',
                      data: {
                          paramVo: JSON.stringify(param)
                      },
                      success: function () {
                    	  query();
                      }
                  })
              });
          }
        var check = function (){
        	var str='';
           var data = grid.selectGet();
             
             if (data.length == 0) {$.etDialog.error('请选择行');return;} 
             
             if(data.length > 1){$.etDialog.error('只能选择一行');return;}
             var rowData = data[0].rowData;
             var param = [];
             $(data).each(function () {
                 var rowdata = this.rowData;
                 if(rowdata.use_nature==1){
                	 if(rowdata.re_link==null){
                		 str+='联合占用关联环节不能为空';
                		 return;
                	 }
                 }
                 param.push(rowdata);
             });
             if(str!=''){
            	 $.etDialog.error(str) 
            	 return;
             }
             $.etDialog.confirm('确定启用?', function () {
                 ajaxPostData({
                     url: 'checkBudgSysSetControl.do',
                     data: {
                         paramVo: JSON.stringify(param)
                     },
                     success: function () {
                         query();
                     }
                 })
             });
             
          }
    var add = function (){
    	  var select=tree.getSelectedNodes()[0];
  /* 	   var budg_year= budg_year.getValue();
         if(budg_year==null){
         	  $.etDialog.error('请选择要继承的年度');
         	  return;
         }else{ */

           $.etDialog.confirm('确定继承数据?', function () {
           	ajaxPostData({
                   url: 'extendBudgSysSetControl.do',
                   data: {
                	   'budg_year': budg_year.getValue(),
                       'plan_code' :select.id
                   },
                   success: function () {
                       query();
                   }
   			});
           });
      /*    } */
          	  
            }
    
        var openUpdate = function (openParam) {
        	  var selectedNode = tree.getSelectedNodes()[0];
        	 var data = grid.selectGet();
             
           
             
             if (data.length == 0) {$.etDialog.error('请选择行');return;} 
             
             if(data.length > 1){$.etDialog.error('只能选择一行');return;}
             var rowData = data[0].rowData;
            $.etDialog.open({
                url: 'BudgSysSetControlSetUpdatePage.do?isCheck=false&plan_code=' + selectedNode.id+ "&budg_year=" +  budg_year.getValue() + "&mod_code=" + rowData.mod_code+"&link_code="+rowData.link_code,
                title: '控制方案维护',
                width: $(window).width(),
                height: $(window).height(),
                
            })
        }
          var query = function () {
        	  var selectedNode = tree.getSelectedNodes()[0];
  	       
              params = [
                  { name: 'budg_year', value: budg_year.getValue()},
                  { name: 'plan_code', value: selectedNode.id }

              ];
              grid.loadData(params,'queryBudgSysSetControl.do');
          };
          
          
          var initGrid = function () {
              var columns = [
                  { display: '系统模块', name: 'mod_name', width: 120}, 
                  { display: '控制环节', name: 'link_name', width: 120 },
                  { display: '控制模式', name: 'm_name', width: 120 },
                  { display: '控制层次', name: 'l_name', width: 120 },
                  { display: '控制期间', name: 'p_name', width: 120 },
                  { display: '控制方式', name: 'w_name', width: 120 },
                  { display: '占用性质', name: 'nature_name', width: 120 },
                  { display: '关联环节', name: 're_name', width: 120 },
                  { display: '控制节点', name: 'cont_name', width: 120 },
                  { display: '是否启用', name: 'start_name', width: 120 },
              ];
              var paramObj = {
                  height: '70%',
                  checkbox: true,
                  dataModel: {
                      url: ''
                  },
                /*   rowDblClick: function (event, ui) {
                      var rowData = ui.rowData;
                      var openParam = {
                    		  plan_code: rowData.plan_code
                      };

                      openUpdate(openParam);
                  }, */
                  rowDblClick:function (event, ui){
                	  $("#children_block").show();
                      grid.option('height', '100%-260');
                      grid.refresh();
                      
                      reloadChildGrid(ui.rowData);
                      
                  },
                  showBottom:false,
                  columns: columns,
                  toolbar: {
                      items: [
                          { type: 'button', label: '初始化', listeners: [{ click: create }], icon: 'create' },
                        /*   { type: 'button', label: '继承', listeners: [{ click: add }], icon: 'add' },
                          { type: 'button', label: '校验', listeners: [{ click: check }], icon: 'circle-check' }, */
                          { type: "button", label: '修改', icon: 'importKj', listeners: [{ click: openUpdate }] }, 
                          { type: 'button', label: '停用', listeners: [{ click: uncheck }], icon: 'circle-check' }, 
                          { type: 'button', label: '启用', listeners: [{ click: check }], icon: 'circle-check' },
                         
                      ]
                  }
              };
              grid = $("#main_grid").etGrid(paramObj);

              $("#main_grid").on('click', '.openUpdate', function () {
                  var rowIndex = $(this).attr('row-index');
                  var currentRowData = grid.getAllData()[rowIndex];

                  var openParam = {
                      plan_id: currentRowData.plan_id
                  };
                  openUpdate(openParam);
              })
              //rowClick
          };
          var initChildCrid= function () {
              var child_columns = [
                             { display: '预算项编码', name: 'item_code', width: 120}, 
                             { display: '预算项名称', name: 'item_name', width: 120 },
                             { display: '控制层次', name: 'l_name', width: 120 },
                             { display: '控制期间', name: 'p_name', width: 120 },
                             { display: '控制方式', name: 'w_name', width: 120 },
                            
                         ];
                         var child_paramObj = {
                             height: '35%',
                             checkbox: true,
                             editable: true,
                            /*  dataModel: {
                                 url: 'queryBudgSysSetControl.do'
                             }, */
                         /*     rowDblClick: function (event, ui) {
                                 var rowData = ui.rowData;
                                 var openParam = {
                               		  plan_code: rowData.plan_code
                                 };

                                 openUpdate(openParam);
                             },
                             rowClick:function (event, ui){
                           	  
                             } */
                             columns: child_columns,
                            
                         };
                         child_grid = $("#child_grid").etGrid(child_paramObj);

                       
                         //rowClick
                     };
         
          </script>
        
        
        
        
<body>
 
        <div class="container">
        	 <div class="left border-right" style="margin-top:20px">
            <div class="" style="margin-left:10px">
                <label>预算年度:</label>
               <input id="budg_year" type="text"  style="width:90px;margin-left:10px"/>
            </div>
            <div id="mainTree"></div>
        </div>
	       
       
            <div class="center" style="padding:0">
               
                <div id="main_grid"></div>
                <div id="children_block">
                    <div class="grid_header clearfix">
	                    <div class="main_operation" >
	                        <label for="">明细控制方案：</label>
                           <button id='btn_search'>查询条件</button>
                        
                       
                          <div style="float:right;padding-right:10px;" id="child_button_group">
	                         
	                            <!--   <button onclick='add()'>预算物资分类维护</button> -->
                            	<a href="javascript:;" id="child_show_button" style="text-decoration:none">>>收起</a>
	                        </div>
                            
	                        </div>
	                    </div>
                    </div>
                    <div id="child_grid"></div>
                </div>
           
      	</div>
        
</body>
</html>