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
             { name: 'para_code', value: $("#para_name").val() },
             { name: 'is_stop', value: $("#is_stop").val() }
         ];
         grid.loadData(params);
   };   
   var add = function () {
       $.etDialog.open({
           url: 'hrFunParaMethodAddPage.do?isCheck=false',
           height : 400,
		   width : 670,
           title: '添加',
           btn: ['确定', '取消'],
           btn1: function (index, el) {
               var iframeWindow = window[el.find('iframe').get(0).name];
               iframeWindow.save()
           }
       });
   };
   var remove = function () {
		var data = grid.selectGet();
       if (data.length == 0) {
           $.etDialog.error('请选择行');
       } else {
       	var param = [];
           $(data).each(function () {
               var rowdata = this.rowData;
               param.push(rowdata);
           });

           $.etDialog.confirm('确定删除?', function () {
               ajaxPostData({
                   url: 'hrFunParaMethodSetdelete.do',
                   data: { param: JSON.stringify(param) },
                   success: function () {
                       query();
                   }
                   })
           });
       }
   };
   var openUpdate = function (openParam) {
       $.etDialog.open({
           url: 'hrFunParaMethodUpdatePage.do?isCheck=false&par_code='+openParam.para_code,
           title: '修改',
           height : 400,
			width : 670,
           btn: [ '保存', '取消' ],
           btn1: function (index, el) {
               var iframeWindow = window[el.find('iframe').get(0).name];
               iframeWindow.save();
           }
       })
   };
	
   $(function () {
       initGrid();
       is_stop = $("#is_stop").etSelect({
           options: [
               { id: 0, text: '否' },
               { id: 1, text: '是' }
           ]
       });
   })
   var initGrid = function () {
           var columns = [
               { display: '参数代码', name: 'para_code', width: 300,
                   render: function (ui) {
                       var updateHtml =
                           '<a class="openUpdate" row-index="' + ui.rowIndx + '">' +ui.cellData +'</a>'
                       return updateHtml;
                   }
               },{ 
               	display: '参数名称', name: 'para_name', width: 200 
               },{
                   display: '是否停用',name: 'is_stop',width: 120,
                   render: function (ui) {
                       var cellData = ui.cellData;
                       if (cellData === "是") {
                           return  '<span style="color:red;"> 是</span>';
                       } else {
                           return '否';
                       }
                   }    
               }
           ];
           var paramObj = {
               height: '100%',
               checkbox: true,
               showBottom:false,
               dataModel: {
                   url: 'hrFunParaMethodSetquery.do?isCheck=false'
               },
               rowDblClick: function (event, ui) {
                   var rowData = ui.rowData;
                   var openParam = {
                		   para_code: rowData.para_code
                   };
                   openUpdate(openParam);
               },
               columns: columns,
               toolbar: {
                   items: [
                       { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                       { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                       { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' }
                   ]
               }
           };
           grid = $("#mainGrid").etGrid(paramObj);

           $("#mainGrid").on('click', '.openUpdate', function () {
               var rowIndex = $(this).attr('row-index');
               var currentRowData = grid.getAllData()[rowIndex];

               var openParam = {
            		   para_code: currentRowData.para_code
               };
               openUpdate(openParam);
           })
       };
</script>
</head>

<body>
	<table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">参数名称：</td>
            <td class="ipt">
                <input id="para_name" type="text" />
            </td>

            <td class="label" style="width: 100px;">是否停用：</td>
            <td class="ipt">
                <input id="is_stop" type="text" style="width: 180px;"/>
            </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>
</html>
