<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var grid,year_month,dept_code,train_type,train_way,id_exe;
        var query = function () {
            params = [
                { name: 'year', value: year_month.getValue().split('-')[0]  },
                { name: 'month', value: year_month.getValue().split('-')[1]  },
                { name: 'dept_id', value: dept_code.getValue().split('@')[1] },
                { name: 'train_way_code', value: train_way.getValue() },
                { name: 'train_title',  value: $('#train_title').val()},
                { name: 'train_type_code', value: train_type.getValue() },
                { name: 'id_exe',value: id_exe.getValue()  }

            ];
            grid.loadData(params);
        };
        var add = function () {
            $.etDialog.open({
                url: 'addPlantPage.do?isCheck=false',
                width: 700,
                height: 450,
                title: '添加',
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save()
                }
            });
        };
        var remove = function () {
        	var res="";
    		var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
            	var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    param.push(rowdata);
                });
                var aaaa=JSON.stringify(param);
 
               $.etDialog.open({
            	   content: $("#itemDiv"), 
                    width: 200,
                    height: 120,
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                    	ajaxPostData({
      					   url: 'queryUserPlan.do?isCheck=false',
      					  data: { paramVo: JSON.stringify(param) },
      					 success: function (res) {
      						/* res=res;
      						  */
      						if(res.state=="true"){
     							 $.etDialog.confirm('<font color="#FF0000">有培训记录，培训考核数据不能删除，是否确认全部删除?', function (index1) {
     	         					ajaxPostData({
     	         					   url: 'deletePlantBach.do',
     	         					  data: { paramVo: aaaa },
              					    success: function () {
              					    	$.etDialog.close(index); // 关闭弹窗
              					  	query();
            						}
     	         					})
     	         					$.etDialog.close(index1); // 关闭弹窗
        							query();
     	         					}) 
     						 }else{
     							/*  $.etDialog.confirm('确定删除?', function (index1) {
      	         					}) */

   	         					ajaxPostData({
   	         					   url: 'deletePlant.do',
   	         					  data: { paramVo: aaaa },
           					    success: function () {
           					    	$.etDialog.close(index); // 关闭弹窗
           					 	query();
         						}
   	         					})
   	         				
     						 }
      					 }
                      	
      					})
      					 
      					query();
                    },
                   
                }) 
            }
        };
        var openUpdate = function (openParam) {
            $.etDialog.open({
                url: 'updatePlantPage.do?isCheck=false&plan_id=' + openParam.plan_id,
                title: '修改',
                width: 700,
                height: 450,
                btn: [ '保存', '取消' ],
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save();
                }
            })
        };
        var initGrid = function () {
            var columns = [
             /*    { display: '培训计划编码', name: 'plan_id', width: 120,
                    render: function (ui) {
                        var updateHtml =
                            '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +
                                ui.cellData +
                            '</a>'

                        return updateHtml;
                    }
                }, */
                { display: '培训主题', name: 'train_title', width: 120 },
                { display: '培训类别', name: 'train_type_name', width: 120 },
                { display: '部门', name: 'dept_name', width: 120 },
                { display: '月份', name: 'month', width: 120 },
                
                { display: '培训方式', name: 'train_way_name', width: 120 },
                { display: '培训对象', name: 'train_object', width: 120 },
                { display: '是否考核', name: 'is_exam_name', width: 120 },
                { display: '是否发证', name: 'is_cert_name', width: 120 },
                { display: '学分', name: 'credit_hour', width: 120 },
                { display: '是否执行', name: 'id_exe_name', width: 120 },
             
                {
                    display: "备注",
                    width: 120,
                    name: "note"
                }  

            ];
            var paramObj = {
                height: '100%',
                checkbox: true,

                dataModel: {
                    url: 'queryPlant.do?isCheck=false'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    var openParam = {
                        plan_id: rowData.plan_id
                    };

                    openUpdate(openParam);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'remove' },
                        { type: 'button', label: '导入', listeners: [{ click: importDate }], icon: 'import' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);

            $("#mainGrid").on('click', '.openUpdate', function () {
                var rowIndex = $(this).attr('row-index');
                var currentRowData = grid.getAllData()[rowIndex];

                var openParam = {
                    plan_id: currentRowData.plan_id
                };
                openUpdate(openParam);
            })
        };

        $(function () {
            initGrid();
            initDict();
        })
          //加载查询条件
        function initDict() {
        	year_month = $("#year_month").etDatepicker({

        		  view: "months",
        		  minView: "months",
        		  dateFormat: "yyyy-mm",
        		   onChange : query
            });// 编制日期
            
            //培训部门
            dept_code = $("#dept_code").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
            //培训类别
            train_type = $("#train_type").etSelect({
                url: "../../queryHosTrainTypeSelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
            //培训方式
            train_way = $("#train_way").etSelect({
                url: "../../queryHosTrainWaySelect.do?isCheck=false",
                defaultValue: "none",
                onChange : query
            });
          
        
        	
            //是否执行
            id_exe = $("#id_exe").etSelect({
                   options: [
                       { id: 2, text: '否' },
                       { id: 1, text: '是' }
                     
                   ],
                   defaultValue: "none",
                   onChange: query, 
			});
        }
        

        //导入数据
        function importDate(){
    		//$("form[name=fileForm]").submit();
    		var para = {
    			"column" : [ {
    				"name" : "train_type_code",
    				"display" : "培训类别",
    				"width" : "160",
    				"require" : true
    			},{
    				"name" : "dept_id",
    				"display" : "部门",
    				"width" : "160",
    				"require" : true
    			},{
    				"name" : "year",
    				"display" : "年度",
    				"width" : "100",
    				"require" : true
    			},{
    				"name" : "month",
    				"display" : "月份",
    				"width" : "100",
    				"require" : true
    			},{
    				"name" : "train_title",
    				"display" : "培训主题",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "train_way_code",
    				"display" : "培训方式",
    				"width" : "160",
    				"require" : true
    			},{
    				"name" : "train_object",
    				"display" : "培训对象",
    				"width" : "200",
    				"require" : true
    			},{
    				"name" : "is_exam",
    				"display" : "是否考核",
    				"width" : "100",
    				"require" : true
    			},{
    				"name" : "is_cert",
    				"display" : "是否发证",
    				"width" : "100",
    				"require" : true
    			},{
    				"name" : "credit_hour",
    				"display" : "学分",
    				"width" : "100"
    			},{
    				"name" : "note",
    				"display" : "备注",
    				"width" : "200"
    			} ]

    		};
    		importSpreadView("/hrp/hr/training/plant/importDateHTL.do?isCheck=false", para);
    	}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" >计划时间：</td>
            <td class="ipt">
                <input id="year_month" type="text"  style="width:200px"/>
            </td>

            <td class="label" style="width: 100px;">培训类别：</td>
            <td class="ipt">
              <select id="train_type" type="text" style="width:180px"></select>

            </td>
          <td class="label">培训部门：</td>
                <td class="ipt">
                    <select id="dept_code" type="text" style="width:180px"></select>
                </td>

        </tr>  <tr>
            <td class="label" >培训主题：</td>
            <td class="ipt">
                <input id="train_title" type="text"  style="width:200px"/>
            </td>

            <td class="label" style="width: 100px;">培训方式：</td>
            <td class="ipt">
              <select id="train_way" type="text" style="width:180px"></select>

            </td>
          <td class="label">是否执行：</td>
                <td class="ipt">
                    <select id="id_exe" type="text" style="width:180px"></select>
                </td>

        </tr>
    </table>
    <div id="mainGrid"></div>
    <div id="itemDiv" style="display: none">
		
		<p align="center">确认删除？</p>
	</div>
    
</body>

</html>
