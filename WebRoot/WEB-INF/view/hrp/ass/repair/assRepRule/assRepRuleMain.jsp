<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.ettab-container{
		flex:1
	}
</style>
 <title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,tab" name="plugins" />
    </jsp:include>
    <style>
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
                /* padding: 0;
                margin: 0;
                width: 30%; 
                box-sizing: border-box; */
                height: 100%;
                text-align: center
            } 

            .ettab-content .ettab-panel {
                padding: 0;
            }
            
             .ettab-content .ettab-panel.ettab-active {
                display: flex;
                flex-direction: column;
            }
            
             .ztree {
                height: 1px;
            }
    </style>
    <script type="text/javascript">
   
    var  userTree,ass_rep_user_fault,ass_rep_user_card,assTypeTree,naturs_code,dept_id;
    $(function () {
    	$("#etTab").etTab({
    		onChange: query
    	});
    	$("#etTabUser").etTab();
        initDict();
        loadGrid();
        loadTree();
        $("#searchUserTree").keyup(function (e) { // 树快速定位
            var _this = this;
        	searchTree({
                tree: userTree,
                value: this.value,
                callback: function (node) {
                    $(_this).focus(); //回去焦点
                }
            });
        });
      /*   $("#searchCardTree").keyup(function (e) { // 树快速定位
            var _this = this;
        	searchTree({
                tree: ass_rep_user_card,
                value: this.value,
                callback: function (node) {
                    $(_this).focus(); //回去焦点
                }
            });
        }); */
        $("#searchCardTypeTree").keyup(function (e) { // 树快速定位
            var _this = this;
        	searchTree({
                tree: ass_rep_user_type,
                value: this.value,
                callback: function (node) {
                    $(_this).focus(); //回去焦点
                }
            });
        });
        $("#searchFauTypeTree").keyup(function (e) { // 树快速定位
            var _this = this;
        	searchTree({
                tree: ass_rep_user_fault,
                value: this.value,
                callback: function (node) {
                    $(_this).focus(); //回去焦点
                }
            });
        });
    })
    
    
   
	 
	
	 function loadGrid() {
        var gridObj = {
                editable: false,
                checkbox: true,
                height: '100%',
                usePager:false,
                inWindowHeight: true,
                addRowByKey: true//  快捷键控制添加行
                
            };
	        gridObj.dataModel ={
				url: 'queryAsscardTree.do?isCheck=false&super_code='+$('#naturs_code').val()+'&dept_id='+$('#dept_id').val(),
			};
            gridObj.numberCell = {
                title: '#'
            };
            /* gridObj.PageModel={
            		rPP: '1000',
            		type: 'local'
            }; */
            gridObj.columns = [{
                    display: "资产编码",
                    align: "left",
                    width: 200,
                    name: "id" 
                },
                {
                    display: '资产名称',
                    align: 'left',
                    name: 'name',
                    width: 200
                },
                {
                    display: '科室名称',
                    align: 'left',
                    name: 'dept_name',
                    width: 200
                },
                {
                    display: '父级',
                    align: 'left',
                    name: 'pId',
                    width: 200
                }
            ];
            grid = $("#ass_rep_user_card").etGrid(gridObj);
           /*  $('#ass_rep_user_card').on('click', '.td-a', function () {
                var index = $(this).attr('data-item') * 1;
                var data = grid.getRowData(index);
                var value = $(this).text();
                update(data, index, value);
            }) */
        
    	
    }

    function loadTree() {
    	userTree = $("#userTree").etTree({
            async: {
                enable: true,
                url: 'queryRepTeamUser.do?isCheck=false'
            }  ,
            callback: {
                onClick: function (event, treeId, treeNode) {
                    var id = treeNode.id;
                    query();
                }
            },  
        })
    	/* ass_rep_user_card = $("#ass_rep_user_card").etTree({
            async: {
                enable: true,
                url: 'queryAsscardTree.do?isCheck=false&super_code='+$('#naturs_code').val()
            },
            check: {
        		enable: true,
        		checked: true,
        		}
        }) */
    	ass_rep_user_type = $("#ass_rep_user_type").etTree({
            async: {
                enable: true,
                url: 'queryAssTypeTree.do?isCheck=false'
            },
            check: {
        		enable: true,
        		checked: true,
        		}
        })
    	ass_rep_user_fault = $("#ass_rep_user_fault").etTree({
            async: {
                enable: true,
                url: 'queryAssFaultTypeTree.do?isCheck=false&super_code=0'
            },
            check: {
        		enable: true,
        		checked: true,
        		}
        })
    }
    function search() { 
    	 var parms = [];
         parms.push({
             name: 'super_code',
             value: $('#naturs_code').val()
         });
         parms.push({
             name: 'dept_id',
             value: $('#dept_id').val()
         });
        
         //加载查询条件
         grid.loadData(parms, 'queryAsscardTree.do?isCheck=false');
    }
    
	function initDict() {
		// 紧急程度
		naturs_code = $('#naturs_code').etSelect({
			 options: [{
                  id: '01',
                  text: '01 房屋及建筑'
              }, {
                  id: '02',
                  text: '02 专用设备'
              }, {
                  id: '03',
                  text: '03 一般设备'
              }, {
                  id: '04',
                  text: '04 其他固定资产'
              }, {
                  id: '05',
                  text: '05 其他无形资产'
              }, {
                  id: '06',
                  text: '06 土地使用权'
              }],
             defaultValue: "01",
             onChange: queryTreeAndGrid
		});
		//科室
		dept_id = $("#dept_id").etSelect({
			url: "../../queryDeptDict.do?isCheck=false",
			defaultValue: "none",
			onChange: queryTreeAndGrid
		});
	}
	 function queryTreeAndGrid(){
		 loadTree();
		 search();
	 }    
    function deleteData() { 
    	
    }
    
      function query() { 
   	    var selectNode = userTree.getSelectedNodes()[0];
   	    var tableNam=$('.center').find('.ettab-panel.ettab-active').find('.flex-item-1').attr('id');
   	    var formData = [];
      	var user_id;
          	user_id = selectNode.id;
		formData.push({name:'user_id',value: user_id})
		formData.push({name:'table_name',value: tableNam})
		  if(tableNam==='ass_rep_user_fault'){
			
				  formData.push({name:'column',value: 'fau_code'})
				
				  formData.push({name:'flag',value: ''})
				
				  var column = 'FAU_CODE'
			
			  }else if (tableNam==='ass_rep_user_type'){
			
				formData.push({name:'column',value: 'ass_type_id'})
			
				formData.push({name:'flag',value: ''})
			
				var column = 'ASS_TYPE_ID'
	        
			}else if(tableNam==='ass_rep_user_card'){
		
	        	formData.push({name:'column',value: 'ass_card_no'})
			
	        	formData.push({name:'naturs_code',value: $('#naturs_code').val()})
			
	        	formData.push({name:'flag',value: 1})
			
	        	var column = 'ASS_CARD_NO'
        	}
		ajaxPostData({
                    url: 'queryTreeDataByUserId.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                       /*  //父级查询
                        search(); */
                        //关闭所有节点
                       	//window[tableNam].expandAll(false);
                       	//取消全部已勾选
                       	if(tableNam==='ass_rep_user_card'){
		                      grid.selectRemoveAll()
                       	}else{
                     		  window[tableNam].checkAllNodes(false);
                       	}
                        $(data).each(function(index,item){
                        	
                        	 if(tableNam==='ass_rep_user_type'){
                        	 var node = window[tableNam].getNodeByParam("ASS_TYPE_ID", item[column]);
                        	 //勾选后台存的数据
                        	 window[tableNam].checkNode(node,true,true);
                        	 window[tableNam].expandNode(node.getParentNode(),true,false,true);
                        	 
                    			 }else if(tableNam==='ass_rep_user_card'){
                    				 var dataNode=grid.getAllData();
				                        $(dataNode).each(function(index,i){
				                        	if(i.id==item[column]){
				                        		grid.setSelection(index)
				                        	}
				                        })
                    			 }else{
                        		 var node = window[tableNam].getNodeByParam("id", item[column]);
                        	 //勾选后台存的数据
                        	 window[tableNam].checkNode(node,true,true);
                        	 window[tableNam].expandNode(node.getParentNode(),true,false,true);
                    			 }
                        })
		               
    	                    	
                       
                    }
                })
    }
      
    function saveCard (){
    	var selectNode = userTree.getSelectedNodes()[0];
    	var user_id;
        var formData = {};
        if(selectNode){
        	user_id = selectNode.id;
      	  if(selectNode.FLAG=='team'){
      		  $.etDialog.error('只能选择维修工程师，不能选择维修班组！');
      		  return;
      	  }
        }else{
     		 $.etDialog.error('请左侧选择维修工程师');
     		 return;
      	 
        }
    	var tableNam=$('.center').find('.ettab-panel.ettab-active').find('.flex-item-1').attr('id');
    		formData.table_name=tableNam ;
    	var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
           
            $(data).each(function () {
                var rowdata = this.rowData;
                ParamVo.push(rowdata.id);
            });
        	formData.column='ass_card_no,naturs_code'
      		formData.flag='1';
      		formData.naturs_code=$('#naturs_code').val();
     		formData.id=JSON.stringify(ParamVo);
            formData.user_id=user_id;
            ajaxPostData({
                url: 'saveRepRule.do?isCheck=false',
                data: formData,
                delayCallback: true,
                success: function (data) {
                    //父级查询
                    //query();
                   
                }
            })
        }
    }
        
        
        
        
    function save (){
    	var selectNode = userTree.getSelectedNodes()[0];
    	var user_id;
        if(selectNode){
        	user_id = selectNode.id;
      	  if(selectNode.FLAG=='team'){
      		  $.etDialog.error('只能选择维修工程师，不能选择维修班组！');
      		  return;
      	  }
        }else{
     		 $.etDialog.error('请左侧选择维修工程师');
     		 return;
      	 
        }
    	var tableNam=$('.center').find('.ettab-panel.ettab-active').find('.flex-item-1').attr('id');
    	var selected = window[tableNam].getCheckedNodes(true);
        var sId=[];
        var flag=false;
        var formData = {};
        formData.table_name=tableNam ;
        $(selected).each(function(index,item){
       	    if(item){
           	  if(!item.isParent){
           		 if(!item.getCheckStatus().half){
           			 if(tableNam==='ass_rep_user_type'){
           				sId.push(item.ASS_TYPE_ID+'')
               			return;
           			 }else{
           				sId.push(item.id+'')
               			return;
           			 }
           			
           		 }
           	  }
            } 
        })
        if(tableNam==='ass_rep_user_fault'){
        	formData.column='fau_code';
       		formData.flag='';
        }else if (tableNam==='ass_rep_user_type'){
        	formData.column='ass_type_id'
       		formData.flag='';
        }else if(tableNam==='ass_rep_user_card'){
        	formData.column='ass_card_no,naturs_code'
       		formData.flag='1';
       		formData.naturs_code=$('#naturs_code').val();
        	
        }
        formData.id=JSON.stringify(sId);
        formData.user_id=user_id;
        console.log(formData)
        ajaxPostData({
                    url: 'saveRepRule.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                        //父级查询
                        //query();
                       
                    }
                })
       
    }
    
    </script>
</head>
<body>
	<div class="container">
		<div id="etTabUser">
		       <div class="etTab" title="维修班组与员工">
		           <div class="search-form">
		              <!--  <label>快速定位</label>
		               <input type="text" id="searchUserTree" class="text-input"> -->
		           </div>
		           <div id="userTree" class="flex-item-1"></div>
	    	  </div>
        </div>
        <div class="center">
	        <div id="etTab">
				<div title="按资产卡片派发">
					 <div class="search-form">
		               <!--  <label>快速定位</label>
		                <input type="text" id="searchCardTree" class="text-input"> -->
		                 <label>资产性质</label>
		                <input  id="naturs_code" style="width:180px">
		                <label>科室</label>
		                <select  id="dept_id" style="width:180px"></select>
		               <input type="button" id="saveUser" value="保存" onclick="saveCard()">
		            </div>
		            <div id="ass_rep_user_card" class="flex-item-1"></div>
				</div>
				<div title="按资产分类派发">
					<div class="search-form">
		             <!--    <label>快速定位</label>
		                <input type="text" id="searchCardTypeTree" class="text-input"> -->
		               <input type="button" id="saveUser" value="保存" onclick="save()">
		            </div>
		            <div id="ass_rep_user_type" class="flex-item-1"></div>
				</div>
				<div title="按故障分类派发">
					 <div class="search-form">
		               <!--  <label>快速定位</label> 
		                <input type="text" id="searchFauTypeTree" class="text-input"> -->
		               <input type="button" id="saveUser" value="保存" onclick="save()">
		            </div>
		            <div id="ass_rep_user_fault" class="flex-item-1"></div>
				</div>
			</div>
        </div>
   </div>
</body>
</html>