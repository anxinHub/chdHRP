<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>简单统计表</title>
	<jsp:include page="${path}/resource.jsp">
		<jsp:param value="hr,dialog,grid,select,tree,pageOffice,jquery_print" name="plugins" />
	</jsp:include>
	<script>
		var tree, grid, statistic_code,mainSQL,searchParam; 
		
		var store_type_code = '${store_type_code}';

		// -----------------------------------主集表格参数分割线---------------------------------------
		
		/* 		var gridObj = {
			checkbox: true,
			height: '100%',
			inWindowHeight: true,
			columns: [],
			addRowByKey: true, //  快捷键控制添加行
			virtualY: false
		}; */
		
        var main_obj = {
				
            height: '100%',
            
            inWindowHeight: true,
            
            checkbox: true,
            
            columns: [],
            
            selectionModel: {type: 'row'},
            
            pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
            
            showBottom: false,
            
            selectionModel: { type: 'none', cbHeader: true, cbAll: true }
      
        };
        
        function reloadMainGrid(param) {//远程加载主集表格 main_grid
        	
        	var data = $.extend({statistic_code: statistic_code, store_type_code: store_type_code}, param);
        	
        	searchParam=param;
        	
            ajaxPostData({url:'queryHrStatisticCustomHead.do?isCheck=false', data:data, success:function (res) {// 获取数据

                grid.option('columns', res.columns);
            
                mainSQL = res.tmpSQL;
               
                var main_param = [
                                  
                    { name:'tmpSQL',value:res.tmpSQL}
                    
                ];
                
                grid.loadData(main_param,"queryHrStatisticCustomGrid.do");
                grid.refreshView();
            }
            }); 
        }
        
     	// -----------------------------------主集表格参数分割线---------------------------------------
		
		$(function () {
			
			loadTree();
			
			initGrid();

			$('#addStatisticSetBtn').on(
				'click',
				function () {
					parent.$.etDialog
						.open({
							title: '简单统计表设置添加',
							url: "hrp/hr/record/hrStatisticCustomSetForm.do?isCheck=false&store_type_code="+store_type_code,
							isMax: true,
							frameName: window.name,
							end: function () {
								tree.reAsyncChildNodes(null, 'refresh')
							}
						});
				})

			$('#modStatisticSetBtn').on(
				'click',
				function () {
					if (tree.getSelectedNodes().length) {
						parent.$.etDialog
							.open({
								title: '简单统计表设置修改',
								url: "hrp/hr/record/hrStatisticCustomSetForm.do?isCheck=false&statistic_code=" + tree.getSelectedNodes()[0].id + "&store_type_code="+store_type_code,
								isMax: true,
								frameName: window.name,
								end: function () {
									tree.reAsyncChildNodes(null, 'refresh')
								}
							});
					} else {
						$.etDialog.error('请选择树节点');
					}
				})

			$('#delStatisticSetBtn').on('click', function () {
				deleteStatisticSet();
			})

			$("#search_input").keyup(function (e) {
				var _this = this;
				searchTree({
					tree: tree,
					value: this.value,
					callback: function (node) {
						$(_this).focus(); //回去焦点
					}
				});
			});

		})

		function deleteStatisticSet() {//获取树当前选择项
       	 var selectData = grid.getAllData();
			if (tree.getSelectedNodes().length) {
				$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: "deleteHrStatisticCustomSet.do?isCheck=false",
						data: {
							statistic_code: tree.getSelectedNodes()[0].id
						},
						success: function (res) {
							if (res.state == "true") {
								//reloadMainGrid(-1);
									initGrid();
								loadTree();
								  grid.deleteRows(selectData);
							}
						}
					})
				});
			} else {
				$.etDialog.error('请选择树节点');
			}
		}

		function initGrid() {
			main_obj.toolbar = {
				items: [{
					type: "button",
					label: '查询',
					icon: 'search',
					id: 'search',
					listeners: [{
						click: search
					}]
				}, {
					type: "button",
					label: '查询条件',
					icon: 'search',
					id: 'searchCondition',
					listeners: [{
						click: searchCondition
					}]
				}, {
					type: "button",
					label: '打印',
					icon: 'print',
					listeners: [{
						click: print
					}]
				}]
			};
			grid = $("#maingrid").etGrid(main_obj);
		}



		function loadTree() {
			tree = $("#mainTree").etTree({
				async: {
					enable: true,
					url: 'queryHrStatisticCustomSet.do?isCheck=false'
				},
				callback: {
					onClick: function (e, id, node) {
						statistic_code = node.id;
						reloadMainGrid();
					}
				}

			})
		}

		function search() {
			if (tree.getSelectedNodes().length) {
				var selectedNode = tree.getSelectedNodes()[0];
				statistic_code = selectedNode ? selectedNode.id : '';
				reloadMainGrid();
			} else {
				$.etDialog.error('请选择树节点');
			}
		}



		function searchCondition() {
			if (tree.getSelectedNodes().length) {
				var selectedNode = tree.getSelectedNodes()[0];
				statistic_code = selectedNode ? selectedNode.id : '';
				$.etDialog.open({
					title: '查询',
					height: 500,
					width: 800,
					frameName: window.name,
					btn: ["查询", "取消"],
					btn1: function (index, el) {
						var iframeWindow = window[el.find('iframe')[0].name];
						iframeWindow.query(); //子页函数
						$.etDialog.close(index);
					},
					btn2: function (index) {
						$.etDialog.close(index); // 关闭弹窗
						parent.$.etDialog.close(index);
						return false;
					},
					url: "queryHrStatisticCustomQueForm.do?isCheck=false&statistic_code=" + statistic_code + "&store_type_code="+store_type_code
				});
			} else {
				$.etDialog.error('请选择树节点');
			}
		}

        function print() {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={};
        	var printPara={
              		title: tree.getSelectedNodes()[0].name,//标题
              		columns: JSON.stringify(grid.getPrintColumns()),//表头
              		class_name: "com.chd.hrp.hr.service.base.HrCommonService",
           			method_name: "queryHrStatisticCustomPrint",
           			bean_name: "hrCommonService",
           			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
           			foots: '',//表尾需要打印的查询条件,可以为空 
           			tmpSQL : mainSQL
               	};
   				
            	officeGridPrint(printPara);
        }

		function exportData() {
			exportGrid(grid);
		}

		function importData() {

		}
	</script>
</head>

<body>
	<div class="container">
		<div class="left border-right">
			<div class="button-group">
				<button id="addStatisticSetBtn">增加</button>
				<button id="modStatisticSetBtn">修改</button>
				<button id="delStatisticSetBtn">删除</button>
			</div>
			<div class="search-form">
				<label>快速定位</label>
				<input type="text" id="search_input" class="text-input">
			</div>
			<div id="mainTree"></div>
			<div class="container-bar"></div>
		</div>
		<div class="center">
			<div id="maingrid"></div>
		</div>
	</div>
</body>

</html>