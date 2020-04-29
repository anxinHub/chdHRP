<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%String path = request.getContextPath();%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <jsp:include page="${path}/resource.jsp">
                <jsp:param value="hr,dialog,select,tree,validate" name="plugins" />
            </jsp:include>
            <style>
                .btn {
                    text-align: center;
                }

                .center .btn button {
                    margin: 4px;
                    width: 100px;
                }
            </style>
            <script>
				var leftTree, rightTree,attend_class_typecode;
                var initFrom = function () {
                	attend_class_typecode = $("#attend_class_typecode").etSelect({
                        url: "../../queryAttendCalssType.do?isCheck=false",
                        defaultValue: "${attend_class_typecode}",
                    });

                	 //排版规则下拉加载
		              attend_pbrule = $("#attend_pbrule").etSelect({
		                options: [{
		                        id: 0,
		                        text: '周'
		                    },
		                    {
		                        id: 1,
		                        text: '月'
		                    },
		                ],
		                defaultValue: "${pb_gz}",
		            });

		            //倒班规则下拉框加载
	                    db_gz = $("#db_gz").etSelect({
	                        options: [{
	                                id: 1,
	                                text: '一班'
	                            },
	                            {
	                                id: 2,
	                                text: '二班'
	                            },
	                            {
	                                id: 3,
	                                text: '三班'
	                            }
	                        ],
	                        defaultValue: "${db_gz}",
	                        onInit: function (value) {
	            			},
	            			onChange: function (value) {
	            			}
	                    });

	                  //医护属性下拉加载
	                    field_col_code = $("#field_col_code").etSelect({
	                        url:"../../queryAttendFieldOption.do?isCheck=false",
	                        defaultValue: "${yh_code}",
	                        onInit: function (value) {
	            			},
	            			onChange: function (value) {
	            			}
	                    });
	                    
              	};
              	/* 禁止勾选数据 */
				/* function checkTree() {
					ajaxPostData({
						url: 'queryAreaDeptCheck.do?isCheck=false&attend_areacode='+"${attend_areacode}",
                      	success: function (res) {  
                        	if (res.length > 0) {
	           					$.each(res, function(index, item) {
       								var disabledNodes = leftTree.getNodesByParam("DEPT_ID", item.DEPT_ID, null);
       								leftTree.setChkDisabled(disabledNodes[0], true);
	           					})
           					};
						},
          			});
              	}; */
              	
                function queryDeptId(){
                	ajaxPostData({
                		url: 'queryAreaDept.do?isCheck=false&attend_areacode='+"${attend_areacode}",
                        success: function (res) {
                        	/* 默认勾选数据 */
           					if (res.length > 0) {
	           					$.each(res, function(index, item) {
	           						var treeNodes = leftTree.getNodesByParam("DEPT_ID", item.DEPT_ID, null);
	           						console.log(treeNodes);
	           						leftTree.checkNode(treeNodes[0], true, true);
	           					})
           					};
                        },
               		});
                };

                function initTree() {
                    leftTree = $("#leftTree").etTree({
                        async: {
                            enable: true,
                            url: '../../queryDeptTree.do?isCheck=false'
                        },
                    	check: {
                    		enable: true
                    	
                    	},
                        callback: {
                        	onNodeCreated : function(event, treeId, node) {
                        		leftTree.expandNode(node, true, false, false);
                        		
                				if (node.nodes && node.level === 0 && node.nodes.length === 0) {
                					leftTree.hideNode(node);
                				};
                				
                				
                			},
                            onClick: function (event, treeId, treeNode) {
                                var is_innr = treeNode.is_innr;
                            }
                        }
                    });
                   
                    $("#searchleftTree").keyup(function (e) { // 树快速定位
                        var _this = this;
                        searchTree({
                            tree: leftTree,
                            value: this.value,
                            callback: function (node) {
                                $(_this).focus(); //回去焦点
                            }
                        });
                    });

                    $("#save").click( function () {
                     	var msg="";
                   		//验证
                		if(!checked()){
               				return;
                		};
                		
                		var dept_id =[];
						var checkNodes = leftTree.getCheckedNodes();
						
						$.each(checkNodes,function(index,value){ 
							if(value.IS_LAST==1){
			        			/*  	msg+="勾选的科室存在非末级科室";
			        				return false;
			        			}else{  */
			        			dept_id.push({
			        				dept_id : value.DEPT_ID
		        				}); 
			        			 } 
		        		});
                  
                    	if(checkNodes.length==0){
							$.etDialog.error('请选择科室');
                               return;
                    	};
                    	  if(msg!=""){
                      		$.etDialog.error(msg);
                              return;  
                         }
                      	if(dept_id.length==0){
                    		$.etDialog.error('选择的科室为非末级科室！');
                            return;
                    	}
                   		ajaxPostData({
                            url: 'updateAttendArea.do',
                            data: {
                         		attend_class_typecode:attend_class_typecode.getValue(),
                         	    old_attend_areacode:"${attend_areacode}",
                             	attend_areacode:$("#attend_areacode").val(),
                            	attend_area_name:$("#attend_area_name").val(),
                            	field_col_code:field_col_code.getValue(),
                               	db_gz:db_gz.getValue(),
                               	attend_pbrule:attend_pbrule.getValue(),
                            	paramVo : JSON.stringify(dept_id),
                            },
                            success: function (responseData) {
                            	$.etDialog.success(
         							'修改成功',
         							 function (index, el) {
         								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
         		                            
         		                            var parentFrameName = parent.$.etDialog.parentFrameName;
         		                            var parentWindow = parent.window[parentFrameName];
         		                            parentWindow.query(); 
         		                            parent.$.etDialog.close(curIndex);
         							    }
         							)
                            },
                   		});
                    });
                    $("#close").click(function () {
                    	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    	parent.$.etDialog.close(curIndex);
                	})
                };
                function checked(){
                	var dataValidate = $.etValidate({
                		config:{},
                		items:[
                            { el : $('#attend_areacode'), required : true },
                			{ el : $('#attend_area_name'), required : true },
                			{ el : $('#attend_class_typecode'), required : true },
                			//{ el : $('#field_col_code'), required : true },
                			{ el : $('#attend_pbrule'), required : true },
                			{ el : $('#db_gz'), required : true }
                		]
                	});
                	return dataValidate.test();
                }
                $(function () {
                	initTree();
              
                	//checkTree();
                    initFrom();
                    $('#Trees').css({'width':'266px','height': $(window).height()-10});
                  	queryDeptId();
                })
                
            </script>
        </head>

	<body>
		<div style="overflow:hidden;">
    	<div class="main" style="float:left;">
	        <table class="table-layout">
	            <tr>
	                <td class="label"><font size="2" color="red">*</font>区域编码：</td>
	                <td class="ipt">
	                    <input id="attend_areacode"  type="text" value="${attend_areacode}" disabled/>
	                </td>
	                </tr>
	                <tr>
	                  <td class="label"><font size="2" color="red">*</font>区域名称：</td>
	                <td class="ipt">
	                    <input id="attend_area_name"     type="text" value="${attend_area_name}" />
	                </td>
	                </tr>
	                <tr>
	                  <td class="label"><font size="2" color="red">*</font>班次分类：</td>
	                <td class="ipt">
	                <select id="attend_class_typecode" style="width: 180px;"></select>
	                </td>
	            </tr>
	        	<tr>							
						<td class="label"><font size="2" color="red">*</font>排班规则：</td>
						<td class="ipt"><select id="attend_pbrule"style="width: 180px;"></select></td>
				</tr>
				<tr>
						<td class="label"><font size="3" color="red">*</font>班次显示：</td>
						<td class="ipt"><select id="db_gz"style="width: 180px;"></select></td>
				</tr>
				<tr  style="display:none">
						<td class="label ">医护属性：</td>
						<td class="ipt"><select id="field_col_code" style="width: 180px;"></select></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button id="save">保存</button>
        				<button id="close" style="margin-left:20px">关闭</button>
					</td>
				</tr>
	        </table>
	    </div>
		<div id="Trees" class="flex-wrap single-block" style="float:left;">
        	<div class="flex-item-1">
            	<div class="search-form">
                	<label>快速定位</label>
                	<input type="text" id="searchleftTree" class="text-input">
            	</div>
           		<div id="leftTree" style="height:91%"></div>
			</div>
		</div>
		<div class="button-group btn">
        	
		</div>
	</div>
    </body>
</html>