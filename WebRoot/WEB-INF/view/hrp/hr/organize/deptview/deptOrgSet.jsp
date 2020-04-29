<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,pageOffice" name="plugins" />
    </jsp:include>
  </head>
     <style type="text/css">
     .label{
      margin-left: 60px;
     }
     .input{
     box-sizing: border-box;
    width: 280px;
    height: 26px;
    box-shadow: rgba(0, 0, 0, 0.1) 0px 1px 1px inset;
    padding: 1px 1px 1px 5px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(174, 202, 240);
    border-image: initial;
    border-radius: 1px;
    outline: none;
        margin-right: 60px;
    }
       .input1{
     box-sizing: border-box;
    width: 150px;
    height: 26px;
    box-shadow: rgba(0, 0, 0, 0.1) 0px 1px 1px inset;
    padding: 1px 1px 1px 5px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(174, 202, 240);
    border-image: initial;
    border-radius: 1px;
    outline: none;
        margin-right: 60px;
    }
      .input2{
     box-sizing: border-box;
    width: 150px;
    height: 26px;
    box-shadow: rgba(0, 0, 0, 0.1) 0px 1px 1px inset;
    padding: 1px 1px 1px 5px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(174, 202, 240);
    border-image: initial;
    border-radius: 1px;
    outline: none;
        margin-right: 60px;
    }
     
     </style>
   <script>
    var grid, tree,attend_types,statistic;
   /*  var EmployeeData = {
		    Rows: [
		        { "attend_name": "照片",
		            "Sex": 1
		        },
		        { "attend_name": "性别",
		            "Sex": 1
		        }
		    ],
		    Total: 2
		}; */
    $(function () {
        initGrid();
      
    })
    
    //是否显示按钮
	/* function setShowBtn(treeId, treeNode) {
		return !treeNode.isParent;
	} */
/* 	
   
     */
    var initGrid = function () {
        var columns = [
           
            { display: '项目名称', name: 'dept_name', width: 100 },
            { display: '是否保存', name: 'is_last', width: 100,
				render: function(rowData, rowindex,value) {
					if(rowData.rowData.is_last == '0'){
						return "<input id=is_budg"+rowData.rowData.dept_code+"  type ='checkbox' checked='checked' style='margin-top:8px;'>";
					}else{
						return "<input id=is_budg"+rowData.rowData.dept_code+"  type ='checkbox' style='margin-top:8px;'>";
					}
					} },
            
        ];
        var paramObj = {
        		height: '100%',
                inWindowHeight: true,
                checkbox: true,
                showBottom:false,
            /*     dataModel: {
                	 Rows: [
		        { "attend_name": "照片",
		            "Sex": 1
		        },
		        { "attend_name": "性别",
		            "Sex": 1
		        }
		    ],
		    Total: 2
                },  */
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '保存', listeners: [{ click: add }], icon: 'add' }
                       
                    ]
                }
        };

        grid = $("#mainGrid").etGrid(paramObj);

    };
    
	    var query = function () {
	    	
	        params = [
	             { name: 'statistic', value: $('#statistic').val() },
	           
	        ];
	        grid.loadData(params,'queryDeptViewOrgSet.do?isCheck=false');
	    };
	    //添加
	    var add = function () {
	    
	    	/* var isPass = grid.validateTest({
        		required: {
        			emp_code :true,
        			year:true
        		}
        	})
        	if (!isPass) {
        		return;
        	} */
        	 var selectData = grid.selectGet();
             if (selectData.length === 0) {
                 $.etDialog.error('请选择行');
                 return;
             } ;

        /*     //验证重复数据
         	if (!grid.checkRepeat(grid.selectGet(), ['emp_code','year'])){
                return;
            } */
             var param = [];
             var code;
             selectData.forEach(function (item) {
            	 var is_budg=0;
            	 if($("#is_budg"+item.rowData.dept_code+"").prop("checked")){
       	 			is_budg = 1;
       	 		};
                 param.push({
                	 emp_code : item.rowData.emp_code,
                	 is_budg :is_budg ,
                 });
             });
             ajaxPostData({
                 url: 'addDeptOrgSet.do?isCheck=false',
                 data: { paramVo: JSON.stringify(param) },
                 success: function () {
                     query();
                 }
            })
        };
	    
	  
	
	    
       
    </script>
   <body>
   <div >
   
			<p class="label">请求地址<font color="red">*</font>:</b>
				<input id="statistic" type="text" class="input" />
			宽度:
			<input id="width" type="text"  class="input1" />
				高度:
			<input id="hight" type="text" class="input2" />
				<!-- 	<td class="label "><button id="search">查询</button></td> -->
	</p>

  <div>
    <div id="mainGrid"></div>
  </div>
	</div>
    </body>
</html>