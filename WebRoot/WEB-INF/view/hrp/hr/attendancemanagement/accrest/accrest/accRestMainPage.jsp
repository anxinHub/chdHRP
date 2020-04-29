<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,grid,select,tree,pageOffice,characterList,datepicker" name="plugins" />
    </jsp:include>
        <style type="text/css">
html, body, div { margin: 0; }

#divLeft {width: 200px; float: left } 
#mainGrid {  margin-left: 200px }
#query {
    margin: 10px 12px;
    box-sizing: border-box;
    height: 26px;
    padding-left: 10px;
    padding-right: 10px;
    border: 1px solid #aecaf0;
    background: #e5edf4;
    outline: none;
    border-radius: 2px;
    cursor: pointer;
 }
    </style>
    <script>
        var grid, tree;
       
        
      
        var initGrid = function () {
            var columns = [
               
					{
							display : '积休编码',
							name : 'attend_xjapply_code',
							width : 120
						}, {
							display : '职工名称',
							name : 'emp_name',
							width : 120
						}, {
							display : '部门',
							align : 'center',
							name : 'dept_name',
							width : 120
						}, {
							display : "考勤月度",
							align : "center",
							width : 120,
							name : "attend_month",
						}, {
							display : '变更类型',
							align : 'center',
							name : 'file_type',
							width : 120
						},

						{
							display : "变更天数",
							align : "center",
							width : 120,
							name : "attend_xjdays"
						}, {
							display : "实际变更天数",
							align : "center",
							width : 120,
							name : "attend_xxjdays"
						},{
							display : "变更时间",
							align : "center",
							width : 120,
							name : "attend_xjcheckdate"
						} ];
						var paramObj = {
							height : '100%',
							checkbox : true,
							editable : false,
							inWindowHeight : '100%',
						
							dataModel: {
							    url: 'queryAccRest.do'
							 },
							columns : columns,

						};

						grid = $("#mainGrid").etGrid(paramObj);

						// 给a标签绑定打开更新页事件
						$("#mainGrid").on('click', '.openUpdate', function() {
							var rowIndex = $(this).attr('row-index');
							var currentRowData = grid.getAllData()[rowIndex];
							var openParam = {
								dept_code : currentRowData.dept_code
							};
							openUpdate(openParam);
						})
					};
					var initFrom = function() {

						// 生成静态选择数据
						var simpleNumberOptions = function(times, isAdd) {
							var options = [];
							var startNum = 1;

							if (isAdd) {
								startNum = 0;
								times += 1;
							}
							if (times) {
								for (var i = startNum; i < times; i++) {
									var pre = i < 10 ? '0' : '';
									var value = pre + i;

									options.push({
										id : value,
										text : value
									})
								}
							}
							return options;
						};
						characterList = $('#character_list').etCharacterList({
							url : '../../queryEmp.do?isCheck=false&is_attend=1',
							param : {},
							onClick : function(selectedItem) {
								query(selectedItem);
								/* dept_name.clearItem();
								user_name.clearItem(); */
							},
							pageModel : {
								type : true,
							}
						});
						file_type_code = $("#file_type_code").etSelect({

							options : [ {
								id : "",
								text : '全部'
							}, {
								id : 1,
								text : '减少'
							}, {
								id : 2,
								text : '增加'
							}, ],
							defaultValue : "none"
						});
						reg_date = $("#reg_date").etDatepicker({
							view : "months",
							minView : "months",
							dateFormat : "yyyy-mm",
						/* 	defaultDate : true, */
						});
						emp_select = $("#emp_id").etSelect({
							url : "../../queryEmpSelectAttend.do?isCheck=false",
							defaultValue : "none",
							onChange : query
						});
					/* 	hos_name = $("#hos_name").etSelect({
							url : '../../queryHosInfoSelect.do?isCheck=false',
							defaultValue : '${sessionScope.hos_id}'
						}); */

						var query = function(selectedItem) {
							var emp_id = "";
							if (selectedItem) {
								emp_id = selectedItem.user_code;
							}
							params = [/*  {
								name : 'hos_id',
								value : hos_name.getValue()
							},  */ {
								name : 'emp_select',
								value : emp_select.getValue()
							} , {
								name : 'reg_date',
								value : reg_date.getValue()
							} , {
								name : 'file_type_code',
								value : file_type_code.getValue()
							} , {
								name : 'emp_id',
								value : emp_id
							}
							];
							grid.loadData(params);
						};

						$("#query").on('click', query);

					};
					var initTree = function() {
						tree = $("#mainTree")
								.etTree(
										{
											async : {
												enable : true,
												url : '../../queryDeptTree.do?isCheck=false'
											},
											callback : {
												onClick : function() {

													var selectedNode = tree
															.getSelectedNodes()[0];
													var super_id = "";
													var dept_id = "";
													if (selectedNode) {
														if (selectedNode.IS_LAST== 1) {
															characterList.param.dept_id = selectedNode.DEPT_ID;
															characterList.param.super_id = "";
														} else if(selectedNode.IS_LAST== 0){
															characterList.param.super_id = selectedNode.id;
															characterList.param.dept_id ="";
														}else if(selectedNode.IS_LAST== undefined){
															characterList.param.super_id ="";
															characterList.param.dept_id ="";
														}
													}

													characterList.reload()
												}
											}
										});
					};

					$(function() {
						initGrid();
						initTree();
						initFrom();
						$('#divLeft').height($(window).height()-90);

						// 给输入框绑定搜索树事件
						$(".text-input").on('keyup', function() {
							var $self = $(this)
							searchTree({
								tree : tree,
								value : $self.val(),
								callback : function() {
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
                <input type="text" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
      <div>
            <table class="table-layout">
              <tr>  <!-- <td class="label">单位信息：</td>
            <td class="ipt">
                <select id="hos_name" style="width:180px;" ></select>
            </td> -->
                   <td class="label">职工名称：</td>
            <td class="ipt">
                <select id="emp_id" type="text" style="width:180px"></select>
            </td>
            <td class="label">考勤周期：</td>
            <td class="ipt">
                <input id="reg_date" type="text" />
            </td>
              </td>
                   <td class="label">变更类型：</td>
            <td class="ipt">
                <select id="file_type_code" type="text" style="width:180px"></select>
            </td>
		</tr>
		
            </table>
               <div>
     <button id="query">查询</button>
    
    </div>
           <div id="divLeft" class="left border-right"><ul id="character_list"></ul></div> 
              <div  id="mainGrid"></div>
             </div>
            
    </div>
    
</body>

</html>