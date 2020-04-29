<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="select, grid, tab, tree, hr, dialog,checkbox" name="plugins" />
</jsp:include>
</head>
	<script type="text/javascript">
		var archives_select, tree, store_type_code, tab_code, archives_select_to;
		
		$(function() {
			loadTree();
			
			//获取到父页面所选的档案库code
			var parentFrameName = parent.$.etDialog.parentFrameName;
			var parentWindow = parent.window[parentFrameName];
			
			
			archives_select = $("#archives_select").etSelect({
	            url: '../../queryHrStoreType.do?isCheck=false',
	            showClear: false,
	            defaultValue: "01",
	            onChange: function (value) {
	            	loadTree(value);
	            }
	        });
			
			archives_select_to = $("#archives_select_to").etSelect({
	            url: '../../queryHrStoreType.do?isCheck=false',
	            showClear: false,
	            defaultValue: parentWindow.$("#archives_select").val(),
	            onChange: function (value) {
	            	//loadTree(value);
	            }
	        });

			$("#search_input").keyup(function(e) {
				var _this = this;
				searchTree({
					tree : tree,
					value : this.value,
					callback : function(node) {
						$(_this).focus(); //回去焦点
					}
				});
			});
			
		})
		
		function loadTree(value) {
		value= value != undefined ? value : '01';
		var tree_url = '../../queryHrStoreTabStruc.do?isCheck=false&store_type_code=' + value;
		tree = $("#mainTree").etTree({
			async : {
				enable : true,
				url : tree_url
			},
			check: {
                enable: true
            },
			callback : {
				onClick : function(e, id, node) {
					search();
				},
				  onAsyncSuccess:function(e, id, node){
	                	 var firstChildrenNode = tree.getNodes()[0];
	                	 tree.selectNode(firstChildrenNode); 
					 //search();
	                } 
			},
			addSuffix : function() {
				var treeNodes = tree.transformToArray(tree.getNodes());
				return {
					nodes : treeNodes,
					rules : [ {
						rule : {
							is_innr : 1
						},
						text : '内置',
						color : 'red'
					} ]
				}
			}

		})
	}
		
	function save(){
		
		var store_type_code = $("#archives_select").val();
		var end_store_type_code = $("#archives_select_to").val();
		
		if(!store_type_code){
			$.etDialog.error('请选择档案库!');
			return false;
		}else if(!end_store_type_code){
			$.etDialog.error('请选择目标档案库!');
			return false;
		}else if(store_type_code == end_store_type_code){
			$.etDialog.error('档案库和目标档案库不能重复!');
			return false;
		}
		
		var selectedNode = tree.getCheckedNodes();
		if(selectedNode != null && selectedNode.length > 0){
			var arrid = [];
			$.each(selectedNode,function(){
				arrid.push(this.id);
			})
				ajaxPostData({
	                url: "addHrStoreColSetBatch.do",
	                data: {
		                "arrid": JSON.stringify(arrid),
		                "store_type_code":store_type_code,
		                "end_store_type_code":end_store_type_code
	                },
	                success: function (res) {
		                if (res.state == "true") {
			                var parentFrameName = parent.$.etDialog.parentFrameName;
			                var parentWindow = parent.window[parentFrameName];
			                parentWindow.search();
			                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			                parent.$.etDialog.close(curIndex);
               			}
               		}
               })
		}else{
			$.etDialog.error('请选择要同步的数据集!');
		}
	}
	
    function selectNode(code){
      	 //$.fn.zTree.getZTreeObj("tree1").checkAllNodes(true);
    	tree.checkAllNodes(code);
   	}
	</script>
<body>
	<div class="main_title" style="margin:10px;">
		<label for="" style="padding-left: 10px;">原档案库：</label> <select name="" id="archives_select"
			style="width: 164px;"></select>
		<label for="" style="padding-left: 20px;">同步目标档案库：</label> <select name="" id="archives_select_to"
			style="width: 164px;"></select>
	</div>
	<div class="container" style="padding: 0 20px;height: 90%;">
		<div class="border" style="width: 100%;overflow: hidden;border: 1px solid rgb(204, 204, 204);">
			<div class="search-form">
					<table class="table-layout">
                        <tr>
                        	<td><label>快速定位</label> <input type="text" id="search_input"
							class="text-input"></td>
                            <td class="label"><button onclick="selectNode(true);">全选</button>
                            <button onclick="selectNode(false);">撤销</button></td>
                        </tr>
                    </table>
			</div>
			<div id="mainTree" style="width: 100%;height: 92%;box-sizing: border-box;"></div>
		</div>
	</div>
</body>
</html>