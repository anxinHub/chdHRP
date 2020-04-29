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
        var grid, tree;
        
        function isNumber(){         
        	var val = $("#attend_acctop").val();
        	var ival = parseInt(val);//如果变量val是字符类型的数则转换为int类型 如果不是则ival为NaN
            if(!isNaN(ival)){
            	if(ival<=0){
            		alert("请输入大于0的数字");
            		return false;
            	}
            } else{
                alert("非法的数字类型");
                return false;
            }
        	return true;
        }
        var query = function () {
            var selectedNode = tree.getSelectedNodes()[0];
            var super_id = "";
            var dept_id = "";
            if(selectedNode){
            	if(selectedNode.IS_LAST!=0){
            		dept_id = selectedNode.DEPT_ID;
            		  params = [
            	                 { name: 'dept_name', value: $('#dept_name').val() }, 
            	                 { name: 'dept_id', value: dept_id } 
            	            ];
            	            grid.loadData(params, 'queryAccRestInit.do');
            	}else if(selectedNode.IS_LAST== 0){
            			super_code = selectedNode.id;
            			  params = [
                 	                 { name: 'dept_name', value: $('#dept_name').val() }, 
                 	                 { name: 'super_code', value: super_code } 
                 	            ];
                 	            grid.loadData(params, 'queryAccRestInit.do');
					}
            		
          	
            	}
          
        };

        var initGrid = function () {
            var columns = [
                { display: '人员编码', name: 'emp_code', width: 120,editable: false },
                { display: '', name: 'emp_id', width: 120,editable: false ,hidden: true},
                { display: '职工名称', name: 'emp_name', width: 120,editable: false },
                { display: '系统初始积休天数', name: 'attend_accdays', width: 120 },
                { display: '备注', name: 'attend_accnote', width: 120 }
            ];
            var paramObj = {
            	title: '<div width="100%" align="center">积休初始化</div>', 
            	showTitle: true, 
                height: '100%',
                checkbox: true,
                editable: true,
                inWindowHeight: '100%',
                summary: {
                    keyWordCol: '',
                    totalColumns: []
                },
                pageModel: {
                    type: 'remote',
                },
                columns: columns,
                toolbar: {
                    items: [ 
                        { type: 'button', label: '保存', listeners: [{ click: save1 }], icon: 'save' } , 
                        { type: 'button', label: '导入', listeners: [{ click: importDate }], icon: 'import' }
                    ]
                }
            };

            grid = $("#mainGrid").etGrid(paramObj);

        };
        
        var initFrom = function () {
        	 // 生成静态选择数据
            var simpleNumberOptions = function (times, isAdd) {
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
                            id: value,
                            text: value
                        })
                    }
                }
                return options;
            };

          	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            });
        };

          	$(document).ready(function() {
          	    $('input[type=radio]').change(function() {
          	        if (this.value == '1') {
          	        	attend_acctop.disabled(true);
          	        	attend_acctop.setValue(0);
          	        }else{
          	        	attend_acctop.enabled();
          	        }
          	       
          	    });
          	});
          	
            //导入数据
            function importDate(){
        		//$("form[name=fileForm]").submit();
        		var para = {
        			"column" : [ {
        				"name" : "emp_code",
        				"display" : "职工编码",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "emp_name",
        				"display" : "职工名称",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "attend_accdays",
        				"display" : "积休天数",
        				"width" : "200",
        				"require" : true
        			},{
        				"name" : "attend_accnote",
        				"display" : "备注",
        				"width" : "200"
        			} ]

        		};
        		importSpreadView("/hrp/hr/attendancemanagement/attend/importRestInit.do?isCheck=false", para, query); 
        	}    	
         
        var initTree = function () {
            tree = $("#mainTree").etTree({
                async: {
                    enable: true,
                    url: '../../queryDeptTree.do?isCheck=false'
                },
                callback: {
                    onClick: function () {
                        query();
                    }
                }
            });
        };
     
        
        $(function () {
            initGrid();
            initTree();
            initFrom();

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
            
        });
    	function save1(){
      		var errorMsg = '';//错误提示信息
      		var selectedNode = tree.getSelectedNodes()[0];
            var super_id = "";
            var dept_id ="";
            var data = grid.selectGet();
      	  	var param = [];
      	 	if (data.length == 0) {
             	$.etDialog.error('请选择行');
         	}else{
          		$(data).each(function (index,obj) {
              		var rowdata = obj.rowData;
              		if(rowdata.emp_id==null){
            	  		errorMsg += '第' + (index+1) + '行人员不能为空<br/>';
              			return false;
              		}else{
              			var aa = "${attend_acctop}";
              			if(aa != "" && parseFloat(rowdata.attend_accdays) > parseFloat(aa)){
              				errorMsg += "职工系统初始积休天数不能大于积休最大值";
              				return false;
              			}
              		}
            	  	param.push(rowdata);
          		});
          		if(errorMsg != ""){
          			$.etDialog.error(errorMsg);
                 	return;
          		}
          		if(selectedNode){
                	if(selectedNode.IS_LAST!=0){
                  		dept_id = selectedNode.DEPT_ID;
                  	}else{
                  		super_id = selectedNode.id;
                  	}
                }
                 
      			ajaxPostData({
                	url: 'addAccRestInit.do',
                	data: {
                    	gridData : JSON.stringify(param),
                    	dept_id : dept_id
                	},
                	success: function () {
                    	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    	parent.$.etDialog.close(curIndex);
                    	query();
                	},
                	delayCallback: true
            	})
        	}
		}
    	
    	function setBtn(){
    		 
      		//验证数字是否合法
      		if(!isNumber())
      			return;
      		ajaxPostData({
                url: 'setAccJxMax.do',
                data: {
                    attend_acctop : $("#attend_acctop").val(),
                    attend_acc_isnot : $('input[name="isnot"]:checked ').val(),
                },
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                    parent.$.etDialog.close(curIndex);
                    parent.query();
                },
                delayCallback: true
            })
      	}
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
    		<fieldset>
    			<legend><h2>积休天数计算上限设置</h2></legend>
	    		<table class="table-layout"> 
					<tr>
	              		<td class="label" style="display:none ;">单位信息：</td>
	            		<td class="ipt" style="display:none ;">
	                		<select id="hos_name" style="width:180px ;"  ></select>
	            		</td> 
						<td align="center">   
							<div class="button-group btn"> <button id="save" onclick="setBtn();">设置</button> </div>
	    				</td>
	          			 
			            <td class="label"></td>
					   	<td class="label">积休最大值设置：</td>
			            <td class="ipt">
			                <input id="attend_acctop" name="attend_acctop" type="text"  value="${attend_acctop}" style="width:180px;">
			            </td>
					</tr>	
					<!-- <tr>
						<td>
					   		<button style="width:150px;">管理考勤项目</button>
						</td>
						<td  class="label">积休业务关联考勤项目：</td>
					    <td>
			                <select id="attend_code" style="width:180px;"></select>
			            </td>
					</tr> -->
	     		</table>
    		</fieldset>
            <div  class="center" id="mainGrid" style="margin-top: 5px;"></div>
        </div>
    </div>
</body>

</html>