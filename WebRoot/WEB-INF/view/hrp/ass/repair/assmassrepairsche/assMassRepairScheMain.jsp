<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,datepicker" name="plugins" />
    </jsp:include>
    <script type="text/javascript">
    var grid,acc_month ;
    var grid, tree;
    $(function () { 
        loadTree();
        loadGrid();
        loadDict();
        $("#searchTree").keyup(function (e) { // 树快速定位
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
    
      function loadDict() {
                // rep_team_code = $("#rep_team_code").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });

                // loc_name = $("#loc_name").etSelect({
                //     url: '',
                //     defaultValue: "none"
                // });

              /*   super_code = $("#super_code").etSelect({
                    url: '../../querySuperLocationSelect.do?isCheck=false',
                    defaultValue: "none"
                }); */

              /*   is_last = $("#is_last").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "none"
                }); */
			    	acc_month = $("#acc_month").etDatepicker({
			            view: "months",
			            minView: "months",
			            showNav: false,
			            dateFormat: "yyyy-mm", 
			            //defaultDate:'yyyy-mm-dd',
			            onChange:function(data){
			            	var columns=[];
			            	//console.log(data);
			            	var year=data.split("-")[0];
			            	var month=data.split("-")[1];
			            	columns.push(
			            			{
					            		display: "工程师编码",
					            		align: "center",
					            		width: 80,
					            		editable: false,
					            		name: "user_code"/* ,
					            		render: function (ui) 
					            			{return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'} */
					            	},
			                        {display: '工程师名称',align: 'center',name: 'user_name',editable: false,width: 80},
			                        {display: "排序",align: "center",width: 20,name: "sort_code",dataType :'float'},
			                        )
			            	if(month=='01' || month=='03'||month=='05'||month=='07'||month=='08'||month=='10'||month=='12'){
			            		for(var i =0 ; i<31;i++){
			            			columns.push({
			            				  display: i+1,
			            	                align: "center",
			            	                width: 20,
			            	                editable: false,
			            	                name: "day"+(i+1),
			            	                render: function (ui) { // 修改页打开
			            	                	return  ui.cellData==0?'休':'班';
			            	                }
			            			})
			            		}
			            	}
			            	if(month=='04' || month=='06'||month=='09'||month=='11'){
			            		for(var i =0 ; i<30;i++){
			            			columns.push({
			          				  display: i+1,
			          	                align: "center",
			          	                width: 20,
			          	              	editable: false,
			          	                name: "day"+(i+1),
			          	                render: function (ui) { // 修改页打开
			          	                	return  ui.cellData==0?'休':'班';
			          	                }
			          				})
			            		}
			            	}
			            	if(year%4==0){
			            		for(var i =0 ; i<29;i++){
			                		if(month=='02'){
			                			columns.push({
			              				  display: i+1,
			              	                align: "center",
			              	                width: 20,
			              	              	editable: false,
			              	                name: "day"+(i+1),
			              	                render: function (ui) { // 修改页打开
			              	                	return  ui.cellData==0?'休':'班';
			              	                }
			              				})
			                		}
			            		}
			            	}else{
			            		for(var i =0 ; i<28;i++){
			                		if(month=='02'){
			                			columns.push({
			              				  display: i+1,
			              	                align: "center",
			              	                width: 20,
			              	             	editable: false,
			              	                name: "day"+(i+1),
			              	                render: function (ui) { // 修改页打开
			              	                	return  ui.cellData==0?'休':'班';
			              	                }
			              				})
			                		}
			            		}
			            	}
			            	grid.option('columns', columns);  	
			            	grid.refreshView();
			            	search();
			            }                        
			        });
			}
			
			function loadGrid() {
			var gridObj = {
			editable: true,
			checkbox: true,
			inWindowHeight:true,
			height: '100%',
			};
			gridObj.columns = [];
        gridObj.toolbar = {
            items: [{
                    type: "button",
                    label: '查询',
                    icon: 'search',
                    id: 'search',
                    listeners: [{
                        click: search
                    }]
                },
                {
                    type: "button",
                    label: '添加',
                    icon: 'add',
                    id:'add',
                    listeners: [{
                        click: addAssLocationTree
                    }]
                }, {
                    type: "button",
                    label: '删除',
                    id:'delete',
                    icon: 'delete',
                    listeners: [{
                        click: deleteData
                    }]
                } , {
                    type: "button",
                    label: '上班',
                    id:'upWorkDay',
                    icon: 'up',
                    listeners: [{
                        click: upWorkDay
                    }]
                }, {
                    type: "button",
                    label: '休假',
                    icon: 'doun',
                    id:'rest',
                    listeners: [{
                        click: rest
                    }]
                }, {
                    type: "button",
                    label: '保存排序',
                    icon: 'save',
                    listeners: [{
                        click: save
                    }]
                }/*, {
                    type: "button",
                    label: '导出',
                    icon: 'export',
                    listeners: [{
                        click: exportData
                    }]
                }, {
                    type: "button",
                    label: '导入',
                    icon: 'import',
                    id:'import',
                    listeners: [{
                        click: importData
                    }]
                } */
            ]
        };
        grid = $("#maingrid").etGrid(gridObj);
        $('#maingrid').on('click', '.td-a', function () {
            var index = $(this).attr('data-item') * 1;
            var data = grid.getRowData(index);
            var value = $(this).text();
            update(data, index, value);
        })
    }
	/* function update(data, index, value){
	    	 $.etDialog.open({
	             url: 'assLocationUpdatePage.do?isCheck=false',
	             height: 400,
	             width: 400,
	             title: '修改位置',
	             btn: ['确定', '取消'],
	             btn1: function (index, el) {
	                 var iframeWindow = window[el.find('iframe').get(0).name];
	                 iframeWindow.saveData();
	             }
	         });
	} */
	function save (){
		 var modData = grid.getUpdated();
		if (modData.length == 0) {
       		$.etDialog.error('无修改数据');
        } else {
            var ParamVo = [];
            $(modData).each(function () {
                var rowdata = this.rowData;
                ParamVo.push({
                	'rep_user':rowdata.rep_user,
                	'sort_code': rowdata.sort_code,
                	'acc_year': rowdata.acc_year,
                	'acc_month': rowdata.acc_month
                	});
            });
		 console.log(ParamVo)
		    	ajaxPostData({
		            url: 'updateUserSort.do?isCheck=false',
		            data:{
                        ParamVo: JSON.stringify(ParamVo)
                    },
		            delayCallback: true,
		            success: function (data) {
		                search();
		              
		            }
		        })
        }
	}
	function upWorkDay (){
		var data =grid.selectGet('cell');
		 if (data.length == 0) {
	            $.etDialog.error('请选人员及休假日期');
	        } else {
				var data =grid.selectGet('cell');
				var formData = [];
				var valueIndex= data[0].dataIndx;
				if(valueIndex != 'et_checkBox' && valueIndex != 'user_code' && valueIndex != 'user_name' && valueIndex != 'sort_code'){
		    	formData.push({name:'workday' , value : valueIndex})
		    	formData.push({name:'type' , value : '1'})
		    	formData.push({name:'rep_user' , value : data[0].rowData.rep_user})
		    	formData.push({name:'acc_year' , value : data[0].rowData.acc_year})
		    	formData.push({name:'acc_month' , value : data[0].rowData.acc_month})
		    	formData.push({name:'group_id' , value : data[0].rowData.group_id})
		    	formData.push({name:'hos_id' , value : data[0].rowData.hos_id})
		    	formData.push({name:'copy_code' , value : data[0].rowData.copy_code})
		    	//console.log(formData)
		    	ajaxPostData({
		            url: 'updateWorkDay.do?isCheck=false',
		            data: formData,
		            delayCallback: true,
		            success: function (data) {
		                search();
		              
		            }
		        })
	        }
	        }
	}
	function rest (){
		var data =grid.selectGet('cell');
		  if (data.length == 0) {
	            $.etDialog.error('请选人员及休假日期');
	        } else {
				var formData = [];
				var valueIndex= data[0].dataIndx;
				console.log(valueIndex);
				if(valueIndex != 'et_checkBox' && valueIndex != 'user_code' && valueIndex != 'user_name' && valueIndex != 'sort_code'){
					
				
		    	formData.push({name:'workday' , value : valueIndex})
		    	formData.push({name:'type' , value : '0'})
		    	formData.push({name:'rep_user' , value : data[0].rowData.rep_user})
		    	formData.push({name:'acc_year' , value : data[0].rowData.acc_year})
		    	formData.push({name:'acc_month' , value : data[0].rowData.acc_month})
		    	formData.push({name:'group_id' , value : data[0].rowData.group_id})
		    	formData.push({name:'hos_id' , value : data[0].rowData.hos_id})
		    	formData.push({name:'copy_code' , value : data[0].rowData.copy_code})
		    	//console.log(formData)
		    	ajaxPostData({
		            url: 'updateWorkDay.do?isCheck=false',
		            data: formData,
		            delayCallback: true,
		            success: function (data) {
		                search();
		              
		            }
		        })
	        } 
	        }
	}
 

    function loadTree() {
        tree = $("#mainTree").etTree({
            async: {
                enable: true,
                url: 'queryAssrRepTeamTree.do?isCheck=false'
            } ,
            callback: {
                onClick: function (event, treeId, treeNode) {
                    var rep_team_code = treeNode.id;
                    search();
                }
            }, 
        

        })
    }
    function search() {
        var selected = tree.getSelectedNodes()[0];
        var sId;
        if(selected) {
            sId = selected.id;
        }
        //根据表字段进行添加查询条件
        var parms = [];
        parms.push({
            name: 'rep_team_code',
            value: sId
        });
        parms.push({
            name: 'rep_team_code',
            value: $('#rep_team_code').val()
        });
        parms.push({
            name: 'acc_year',
            value: $('#acc_month').val().split("-")[0]
        });
        parms.push({
            name: 'acc_month',
            value: $('#acc_month').val().split("-")[1]
        });
        //加载查询条件
        grid.loadData(parms, 'queryAssRepUser.do?isCheck=false');
    }
    
    
    function addAssLocationTree() {
    	 var selected = tree.getSelectedNodes()[0];
         var sId;
         if(selected) {
             sId = selected.id;
         }
         if(!sId){
        	  $.etDialog.error('请选择维修班组');
        	  return;
         }
         if(!$('#acc_month').val()){
        	  $.etDialog.error('请选择日期');
        	  return;
         }
    	 $.etDialog.open({
             url: 'assMassRepairScheAddPage.do?isCheck=false&rep_team_code='+sId+'&acc_year='+$('#acc_month').val(),
             height: 400,
             width: 400,
             title: '添加位置',
             btn: ['确定', '取消'],
             btn1: function (index, el) {
                 var iframeWindow = window[el.find('iframe').get(0).name];
                 iframeWindow.saveData();
             }
         });
    }

    function create() {

    }

    
    function deleteData() {
   	    var selected = tree.getSelectedNodes()[0];
        var sId = selected.id;
        var data = grid.selectGet();
        var str = null;
        if (data.length == 0) {
       		$.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
            $(data).each(function () {
                var rowdata = this.rowData;
                rowdata.rep_team_code = sId;
                ParamVo.push({'rep_user':rowdata.rep_user,'acc_year':$('#acc_month').val()});
            });
            if(data.length==grid.getAllData().length){
            	str = '确定删除?（全部删除后,  将会按照班组重新初始化）'
            }else{
            	str = '确定删除?'
            }
            $.etDialog.confirm(str, function () {
                ajaxPostData({
                    url: "deleteAssMassRepairSche.do?isCheck=false",
                    data: {
                        ParamVo: JSON.stringify(ParamVo)
                    },
                    success: function (res) {
                        if (res.state == "true") {
                            search();
                            //tree.reAsyncChildNodes(null, 'refresh');
                        }
                    }
                })
            });
        }
    }
    
    </script>
</title>
</head>
<body>
	<div class="container">
	<div class="left border-right">
            <div class="button-group">
            </div>
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" id="searchTree" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        
        
        <div class="center">
            <table class="table-layout">
                <tr>
                    
                    <td class="label">期间：</td>
                    <td class="ipt">
                        <input id="acc_month" type="text" style="width:180px;"/>
                    </td>
                </tr>
            </table>
            <div id="maingrid"></div>
        </div>
   </div>
</body>
</html>