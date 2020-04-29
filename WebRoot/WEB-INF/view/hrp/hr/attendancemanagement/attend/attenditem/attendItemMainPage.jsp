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
    var grid, tree,attend_types,attend_code;
    $(function () {
    	initTree();
        initGrid();
       query();
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
    
    //是否显示按钮
	/* function setShowBtn(treeId, treeNode) {
		return !treeNode.isParent;
	} */
    
    function initTree() {
        tree = $("#mainTree").etTree({
        	async: {
                enable: true,
                url: '../../queryAttendItemTree.do?isCheck=false'
            },
        	data : {
    			simpleData : {
    				enable : true,
    				idKey : "id",
    				pIdKey : "pId",
    				rootPId : 0
    			},
    			keep : {
    				leaf : true
    			},
    			key : {
    				children : "nodes"
    			}
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
            	onNodeCreated : function(event, treeId, node) {
    				tree.expandNode(node, true, false, false);
    				if (node.nodes && node.level === 0 && node.nodes.length === 0) {
    					treeObj.hideNode(node);
    				}
    			},
    			onClick : function(event, treeId, node) {
                	query(node.id);
                }
            },
            
        });
        
  /*       hos_name = $("#hos_name").etSelect({
            url: '../../queryHosInfoSelect.do?isCheck=false',
            defaultValue: '${sessionScope.hos_id}'
        }); */
    };
    
    var initGrid = function () {
        var columns = [
            { display: '项目编码', name: 'attend_code', width: 100,editable: false,
                render: function (ui) {
                	return '<a class="openUpdate" row-index="' + ui.rowIndx + '">' + ui.cellData + '</a>';
                }
            },
            { display: '项目名称', name: 'attend_name', width: 100 },
            { display: '项目简称', name: 'attend_shortname', width: 100 },
            { display: '考勤分类', name: 'attend_type_name', width: 100 } ,
            { display: '是否积休', name: 'is_jx', width: 100 ,
            	render : function(ui){
            		if(ui.rowData.is_jx == 0){
            			return "否";
            		}else{
            			return "是";
            		}
            	}
            },
            { display: '是否计算', name: 'is_calculate', width: 100 ,
            	render : function(ui){
            		if(ui.rowData.is_calculate == 0){
            			return "否";
            		}else{
            			return "是";
            		}
            	}
            },
            { display: '是否限额', name: 'attend_ed_is', width: 100 ,
            	render : function(ui){
            		if(ui.rowData.attend_ed_is == 0){
            			return "否";
            		}else{
            			return "是";
            		}
            	}
            },
            { display: '额度控制方式', name: 'control_type', width: 100 ,
            	render : function(ui){
            		if(ui.rowData.control_type == 0){
            			return "全院";
            		}else if(ui.rowData.control_type == 1){
            			return "个人";
            		}else {
            			return "";
            		}
            	}
            },
            { display: '额度上限', name: 'max_ed', width: 100 },
            { display: '是否停用', name: 'is_stop', width: 100,
            	render : function(ui){
            		if(ui.rowData.is_stop == 0){
            			return "否";
            		}else{
            			return "是";
            		}
            	}
            },
            
            { display: '是否默认考勤', name: 'is_default', width: 100,
            	render : function(ui){
            		if(ui.rowData.is_default == 0||ui.rowData.is_default == null){
            			return "否";
            		}else{
            			return "是";
            		}
            	}
            },
            { display: '备注', name: 'note', width: 100 }
        ];
        var paramObj = {
        		height: '100%',
                inWindowHeight: true,
                checkbox: true,
                showBottom:false,
              /*   dataModel: {
                	url: ''
                }, */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
        };

        grid = $("#mainGrid").etGrid(paramObj);

        $("#mainGrid").on('click', '.openUpdate', function () {
            var rowIndex = $(this).attr('row-index');
            var currentRowData = grid.getDataInPage()[rowIndex];
            openUpdate(currentRowData);
        })
    };
    
	    var query = function () {
	    	var attend_types;
	    	var attend_codes;
	        var selectedNode = tree.getSelectedNodes()[0];
	        if(selectedNode){
	        	if(selectedNode.id == 0){
	        		attend_types="";
	        		attend_codes="";
	        	}else{
	        		if(selectedNode.pId==0){
		        		attend_types = selectedNode.id;
		        	}else{
		        		attend_codes = selectedNode.id;
		        	}
	        	}
	        }
	        params = [
	             { name: 'attend_code', value: $('#attend_code').val() }, 
	             { name: 'attend_name', value: $('#attend_name').val() },
	             { name: 'attend_types', value: attend_types } ,
	             { name: 'attend_codes', value: attend_codes } 
	        ];
	        grid.loadData(params,'queryAttendItem.do');
	    };
	    
	    //添加
	    var add = function () {
	    	 var selectedNode = tree.getSelectedNodes()[0];
	    	 if(selectedNode){
	         	if(selectedNode.pId !=0 ){
	         		$.etDialog.error('请选择父节点数据！');
	           	 	return;
	         	}
	         }else{
	        	 $.etDialog.error('请选择父节点数据！');
	        	 return;
	         }
	    	 
	    	parent.$.etDialog.open({
	            url: 'hrp/hr/attendancemanagement/attend/hraddAttendItemPage.do?isCheck=false&attend_types='+selectedNode.id,
	            width: 700,//$(window).width()-100,
	            height: 400,//$(window).height()-100,
	            frameName :window.name,
	            title: '考勤项目添加'
	        });
	    };
	    
	    //删除
	    var remove = function () {
			var data = grid.selectGet();
	        if (data.length == 0) {
	            $.etDialog.error('请选择行');
	            return;
	        } else {
	        	var ParamVo = [];
	            $(data).each(function () {
	                var rowdata = this.rowData;
	                ParamVo.push(rowdata);
	            });
	            $.etDialog.confirm('确定删除?', function () {
	                ajaxPostData({
	                    url: "deleteAttendItem.do",
	                    data: {paramVo: JSON.stringify(ParamVo)},
	                    success: function (res) {
	                        grid.deleteRows(data);
	                        tree.reAsyncChildNodes(null, 'refresh');
	                    }
	                })
	            });
	        }
	    };
	    
	    //修改
	    var openUpdate = function (rowData) {
	    	parent.$.etDialog.open({
	            url: 'hrp/hr/attendancemanagement/attend/updateAttendItemPage.do?isCheck=false&attend_code=' + rowData.attend_code,
	            width: 700,//$(window).width()-100,
	            height: 400,//$(window).height()-100,
	            frameName :window.name,
	            title: '考勤项目修改'
	        })
	    }
	    
        var print = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
            		]  */}; 
        	var printPara={
                title: " 考勤项目打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.attendancemanagement.attend.HrAttendItemService",
                method_name: "queryAttendItemByPrint",
                bean_name: "hrAttendItemService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        };
    </script>
</head>

<body>
    <div class="container">
        <div class="left border-right">
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        <div class="center">
            <table class="table-layout">
                <tr>
		      <!--       <td class="label">单位信息：</td>
		            <td class="ipt">
		                <select id="hos_name" style="width:180px;" disabled></select>
		            </td> -->
		            <td class="label">项目编码：</td>
		            <td class="ipt">
		                <input id="attend_code" type="text" />
		            </td>
		            <td class="label">项目名称：</td>
		            <td class="ipt">
		                <input id="attend_name" type="text" />
		            </td>
	        	</tr>
            </table>

            <div id="mainGrid"></div>
        </div>
    </div>
</body>

</html>