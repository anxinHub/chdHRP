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
    var grid, tree,order_time_begin;
    var parmVo=[];
    $(function () {
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
    	order_time_begin = $("#order_time_begin").etDatepicker({
            dateFormat: "yyyy-mm-dd", 
            defaultDate:'yyyy-mm-fd',
            })
    	order_time_end = $("#order_time_end").etDatepicker({
            dateFormat: "yyyy-mm-dd", 
            defaultDate:'yyyy-mm-ed',
            })
             // 维修标识
                rep_bz = $("#rep_bz").etSelect({
                	 options: [{
	                         id: 1,
	                         text: '内部维修'
	                     }, {
	                         id: 2,
	                         text: '外部维修'
	                     }],
                    defaultValue: "none",
                })
        }
    
    function loadGrid() {
        var gridObj = {
            editable: false,
            checkbox: false,
            height: '100%',
            inWindowHeight: true,
            addRowByKey: true //  快捷键控制添加行
        };
        gridObj.numberCell = {
            title: '#'
        };
        gridObj.columns = [
        	{
                display: "卡片编号",
                align: "left",
                width: 120,
                name: "ass_card_no"  

            },
            {
                display: "资产名称",
                align: "left",
                width: 120,
                name: "ass_name"  

            },
        	{
                display: "完成",
                align: "center",
                width: 120,
                name: "endstate" ,
           		render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'
                } 

            } ,
        	{
                display: "误报",
                align: "center",
                width: 120,
                name: "errorstate" ,
           		render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'
                } 

            } 
        ];
        gridObj.toolbar = {
            items: [{
                    type: "button",
                    label: '查询',
                    icon: 'search',
                    id: 'search',
                    listeners: [{
                        click: search
                    }]
                } 
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
	 
	function update(data, index, value) {
		parmVo=[];
		var state ='';
		var ass_card_no= data.ass_card_no;
		if(grid.selectGet('cell')[0].dataIndx==='endstate'){ 
			state='5';
		}else if(grid.selectGet('cell')[0].dataIndx==='errorstate'){
			state = '6'
		}
		parmVo.push({ name:'state' ,value : state})
		parmVo.push({ name:'order_time_begin' ,value : $('#order_time_begin').val()})
		parmVo.push({ name:'order_time_end' ,value : $('#order_time_end').val()})
		parmVo.push({ name:'ass_card_no' ,value : ass_card_no})
        $.etDialog.open({
            url: 'assRepStaisByCardNoPaga.do?isCheck=false',
            isMax:true,
            title: '位置',
            btn: ['关闭'],
            btn2: function (index, el) {
                var frameWindow = window[el.find('iframe')[0].name];
                frameWindow.saveData();
            },
            btn2: function (index) {
                $.etDialog.close(index); // 关闭弹窗
                return false;
            }
        });

    }

    
    function search() {
        //根据表字段进行添加查询条件
        var parms = [];
        parms.push({
            name: 'order_time_begin',
            value: $('#order_time_begin').val()
        });
        parms.push({
            name: 'order_time_end',
            value: $('#order_time_end').val()
        }); 
        parms.push({
            name: 'rep_bz',
            value: $('#rep_bz').val()
        });
        
        //加载查询条件
        grid.loadData(parms, 'queryRepCountByRepCardNo.do?isCheck=false');
    }
    
    
    </script>
</head>
<body>
	<div class="container">
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">维修日期：</td>
                    <td class="ipt" >
                        <input id="order_time_begin" type="text" style="width:180px;"/>    至：
                    </td>
                    <td class="ipt" >
                       <input id="order_time_end" type="text" style="width:180px;"/>
                    </td>
                    <td class="label">维修标识：</td>
                    <td class="ipt">
                        <input id="rep_bz" type="text" style="width:180px;"/>
                    </td>
                     
                     
                </tr>
            </table>

            <div id="maingrid"></div>
        </div>
   </div>
</body>
</html>