<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var query = function () {
            params = [
                { name: 'type_name', value: $("#type_name").val() },
                { name: 'is_stop', value: $("#is_stop").val() },
            ];
            grid.loadData(params);
        };
        
        var initGrid = function () {
            var columns = [
            	 { display: '归档项目编码', name: 'type_code',width: '15%'},
                 { display: '归档项目名称', name: 'type_name',width: '19%'},
                 { display: '备注', name: 'note', width: '15%'},
                 { display: '是否停用', name: 'is_stop', align: 'center',width: '15%',
                	 render: function (obj) {
                 		if(obj.rowData.is_stop == 0){
                 			return '否';
                 		}else{
                 			return '是';
                 		}
                     }	 
                 }

            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactFileType.do?isCheck=false'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    openUpdate(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' }
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        //跳转修改页面
        var openUpdate = function (rowData) {
            $.etDialog.open({
            	 url: 'PactFileTypeEdit.do?isCheck=false&type_code=' + rowData.type_code+"&sub_id="+rowData.sub_id+"&sub_no="+rowData.sub_no,
                 width: 320,
                 height: 280,
                 title: '修改',
                 btn: ['保存', '取消'],
                 modal: true,
                 btn1: function (index, el) {
                	 $.ajax({
                         type: 'POST',
                         url: 'updatePactFileType.do?isCheck=false',
                         data : {
                        	 type_code : rowData.type_code,
              				check : 'false',
              			},
                         dataType: 'json',
                         success: function (res) {
                        	 var result = res.msg;
                        	 if ("数据存在." == result) {
								$.etDialog.confirm("该数据已使用，请确认是否修改",
									function(index){
									  	 var iframeWindow = window[el.find('iframe').get(0).name];
					                  	 iframeWindow.update();
					                  	 $.etDialog.close(index);
									}		
								);
							}else{
								var iframeWindow = window[el.find('iframe').get(0).name];
			                  	 iframeWindow.update()
							}
                         }
                     })
                 }
            });
        };
        
       
        //跳转保存页面
        var save = function (data) {
        	 $.etDialog.open({
                 url: 'PactFileTypeAdd.do?isCheck=false',
                 width: 320,
                 height: 280,
                 title: '添加',
                 btn: ['保存', '取消'],
                 modal: true,
                 btn1: function (index, el) {
                     var iframeWindow = window[el.find('iframe').get(0).name];
                     iframeWindow.save()
                 }
             });
        };
        //删除
        var remove = function () {
        	 var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     rowdata.group_id = ${group_id};
                     rowdata.hos_id = ${hos_id};
                     rowdata.copy_code = '${copy_code}';
	                 param.push(rowdata);
                 });
                 $.etDialog.confirm('确定删除?', function () {
                     ajaxPostData({
                         url: 'deletePactFileType.do?isCheck=false',
                         data: {
                             paramVo: JSON.stringify(param)
                         },
                         success: function () {
                             grid.deleteRows(data);
                         }
                     })
                 });
             }
        };        
        $(function () {
            initGrid();
            
            $("#is_stop").etSelect({
  			  options: [
  			    { id: 0, text: '否' },
  			    { id: 1, text: '是' }
  			  ],
  			  defaultValue: "none"
  			})
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">归档项目：</td>
            <td class="ipt">
                <input id="type_name" type="text" />
            </td>
            <td class="label" style="width: 100px;">是否停用：</td>
            <td class="ipt">
                <select id="is_stop" style="width: 180px"></select>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>

