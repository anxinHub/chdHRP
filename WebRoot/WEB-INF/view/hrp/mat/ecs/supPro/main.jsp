<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree" name="plugins" />
    </jsp:include>
    
     <style type="text/css">
	    
	.pq-grid-cell .g-btn {
	    box-sizing: border-box;
	    height: 26px;
	    margin:0 5px;
	    padding-left: 10px;
	    padding-right: 10px;
	    border: 1px solid #aecaf0;
	    background: #e5edf4;
	    outline: none;
	    border-radius: 2px;
	    cursor: pointer;
	    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	}
    </style>
    <script type="text/javascript">
    var grid ,prod_name,spec_name,prod_type_name1,bar_code,check_state ,is_impl,sup_id;
    var dialog = frameElement.dialog; 
    var $data, $auditWindow;
    $(function () {
        loadGrid();
        loadDict();
        if(dialog){
        	if(dialog.get("data").check_state){
            	check_state.setValue(dialog.get("data").check_state);
            }
            if(dialog.get("data").inv_no){
            	$("#invList").text(dialog.get("data").inv_no);
            	search();
            }
        }else{
        	$("#closeBtn").hide();
        }
        
    })
    
      function loadDict() {
                is_impl = $("#is_impl").etSelect({
                    options: [{
                        id: 1,
                        text: '是'
                    }, {
                        id: 0,
                        text: '否'
                    }],
                    defaultValue: "none"
                });
                sup_id = $("#sup_id").etSelect({
                   url:'../../../mat/queryHosSup.do?isCheck=false',
                    defaultValue: "none"
                });
                check_state = $("#check_state").etSelect({
                    options: [{
                        id: 1,
                        text: '未审核'
                    }, 
                    {
                        id: 2,
                        text: '已完成'
                    }, 
                    {
                        id: 3,
                        text: '未通过'
                    }
                    ],
                    defaultValue: "none"
                });
                prod_type_name1 = $("#prod_type_name1").etSelect({
                    options: [{
                        id: '医疗器械',
                        text: '医疗器械'
                    }, 
                    {
                        id:'试剂',
                        text: '试剂'
                    }, 
                    {
                        id: '药品',
                        text: '药品'
                    }, 
                    {
                        id:'其他',
                        text: '其他'
                    }, 
                    {
                        id: '化妆品',
                        text: '化妆品'
                    }
                    ],
                    defaultValue: "none"
                });
            }
    
    function loadGrid() {
        var gridObj = {
            editable: false,
            checkbox: true,
            height: '100%',
            inWindowHeight: true,
            addRowByKey: true //  快捷键控制添加行
        };
        gridObj.numberCell = {
            title: '#'
        };
        gridObj.columns = [  
        	{
            display: '操作',
            align: 'left',
            name: '',
            width: 120,
       		render: function (ui) { // 修改页打开
            return '<input type = "button" value = "通过" data-item=' + ui.rowIndx + ' class="audit g-btn"/><input type = "button" value = "驳回"  data-item=' + ui.rowIndx + ' class="NoPass g-btn"   />'
            }
        },
        {
            display: "状态",
            align: "left",
            width: 120,
            name: "check_state"
        },
            {
                display: '平台产品',
                align: 'left',
                name: 'prod_name',
                width: 120,
           		render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'
                }
            },
            {
                display: "规格名称",
                align: "left",
                width: 120,
                name: "spec_name"
            },
            {
                display: "HRP材料编码",
                align: "left",
                width: 120,
                name: "inv_code"
            },
            {
                display: "HRP材料名称",
                align: "left",
                width: 120,
                name: "inv_name"
            },
            {
                display: "规格别名",
                align: "left",
                width: 120,
                name: "spec_name_sec"
            },
            {
                display: '供应商名称',
                align: 'left',
                name: 'sup_name',
                width: 120
            },
            {
                display: "厂商名称",
                align: "left",
                width: 120,
                name: "fac_name"
            },
            {
                display: "类别",
                align: "left",
                width: 120,
                name: "prod_type_name1",
            },
            {
                display: "产品小类",
                align: "left",
                width: 120,
                name: "prod_type_name2"
            },
            {
                display: "68分类",
                align: "left",
                width: 120,
                name: "prod_type_name3"
            },
            {
                display: "注册证号",
                align: "left",
                width: 120,
                name: "cert_code"
            },
            {
                display: "证件开始日期",
                align: "left",
                width: 120,
                name: "start_date"
            },
            {
                display: "证件结束日期",
                align: "left",
                width: 120,
                name: "end_date"
            },
            {
                display: "全称",
                align: "left",
                width: 120,
                name: "loc_name_all"
            },
            {
                display: "是否长期",
                align: "left",
                width: 120,
                name: "is_long"
            },
            {
                display: "产品别名",
                align: "left",
                width: 120,
                name: "alias1"
            },
            {
                display: "产地类型",
                align: "left",
                width: 120,
                name: "origin_type"
            },
            {
                display: "品牌",
                align: "left",
                width: 120,
                name: "brand_name"
            },
            {
                display: "产地名称",
                align: "left",
                width: 120,
                name: "origin_name"
            },
            {
                display: "是否冷藏",
                align: "left",
                width: 120,
                name: "is_cold"
            },
            {
                display: "储存方式",
                align: "left",
                width: 120,
                name: "stora_term"
            },
            {
                display: "组件名称",
                align: "left",
                width: 120,
                name: "term_name"
            },
            {
                display: "是否植入",
                align: "left",
                width: 120,
                name: "is_impl",
            }
        ];
      /*   gridObj.dataModel = { // 数据加载的有关属性
             url: '',
            recIndx: 'bar_code' 
        }; */
 /*        gridObj.toolbar = {
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
        }; */
        grid = $("#maingrid").etGrid(gridObj);
        $('#maingrid').on('click', '.td-a', function () {
            var index = $(this).attr('data-item') * 1;
            var data = grid.getRowData(index);
            var value = $(this).text();
            update(data, index, value);
        })
        $('#maingrid').on('click', '.audit', function () {
            var index = $(this).attr('data-item') * 1;
            audit(index);
        })
        $('#maingrid').on('click', '.NoPass', function () {
            var index = $(this).attr('data-item') * 1;
            NoPass(index);
        })
    }
    
    function reAudit(inv_id){
        //绑定关系
   		var formData = [];
       	formData.push({name:'check_state' , value :'2'});
       	formData.push({name:'prod_id' , value : $data.prod_id});
       	formData.push({name:'spec_id' , value : $data.spec_id});
       	formData.push({name:'chos_id' , value : $data.chos_id});
       	formData.push({name:'csup_id' , value : $data.csup_id});
       	formData.push({name:'sid' , value : $data.sid});
       	formData.push({name:'inv_id' , value : $data.inv_id});
   		 ajaxPostData({
                url: "../../../sys/ecs/sup/addMatSupProdSpecInv.do?isCheck=false",
                data: formData,
                success: function (res) {
                    if (res.state == "true") {
                   	  parent.search();
                    }
                }
            })
    }
    
	 function audit (index){
		 $data = grid.getRowData(index);
		 if($data.inv_id){
			 $.etDialog.confirm("存在已绑定材料是否重新绑定？", function(){
				 reAudit();
				 return false;
			}, function (){
				 parent.$.etDialog.open({
		            url: 'hrp/sys/ecs/sup/matSupProdAuditPage.do?isCheck=false',
		            height: $(window).height(),
		            width: $(window).width(),
		            title: '材料审核',
					maxmin: true, resize: true, 
					// 父页面向子页面传值
					frameName: window.name,
					success: function (el, index) {
						var iframeWindow = parent.window[el.find('iframe').get(0).name];
						$(iframeWindow).attr({data: $data});
						
						//var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
						//var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
						//$(parentWindow).attr({data: data});
					}
		        });
			});
		 }else{

			 parent.$.etDialog.open({
	            url: 'hrp/sys/ecs/sup/matSupProdAuditPage.do?isCheck=false',
	            height: $(window).height(),
	            width: $(window).width(),
	            title: '材料审核',
				maxmin: true, resize: true, 
				// 父页面向子页面传值
				frameName: window.name,
				success: function (el, index) {
					var iframeWindow = parent.window[el.find('iframe').get(0).name];
					$(iframeWindow).attr({data: $data});
					
					//var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
					//var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
					//$(parentWindow).attr({data: data});
				}
			});
		}
	}
	 
	 var inv_name="";
	 var inv_code="";
	 function NoPass (index){
		 var data = grid.getRowData(index);
		 inv_name=data.inv_name;
		 inv_code=data.inv_code;
			 $.etDialog.open({
	            url: 'matSupProdNoPassPage.do?isCheck=false',
	            height: 300,
	            width: 500,
	            title: '申请驳回',
	            btn: ["确定", "取消"],
	            btn1: function (index, el) {
	                var frameWindow = window[el.find('iframe')[0].name];
	                frameWindow.saveData(data);
	            },
	            btn2: function (index) {
	                $.etDialog.close(index); // 关闭弹窗
	                return false;
	            }
        });
	 }
	function update(data, index, value) {
        var parm = 'sid='+data.sid;
        parent.$.etDialog.open({
            url: 'hrp/sys/ecs/sup/matSupProdUpdatePage.do?isCheck=false&'+parm,
            height: $(window).height(),
	        width: $(window).width(),
			maxmin: true, resize: true, 
            title: '修改位置',
            btn: ["关闭"],
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
            name: 'prod_name',
            value: $('#prod_name').val()
        });
        parms.push({
            name: 'spec_name',
            value: $('#spec_name').val()
        });
        parms.push({
            name: 'prod_type_name1',
            value: $('#prod_type_name1').val()
        });
        parms.push({
            name: 'bar_code',
            value: $('#bar_code').val()
        });
        parms.push({
            name: 'check_state',
            value: $('#check_state').val()
        });
        parms.push({
            name: 'is_impl',
            value: $('#is_impl').val()
        });
        parms.push({
            name: 'sup_id',
            value: $('#sup_id').val()
        });
        //加载查询条件	
        grid.loadData(parms, 'queryMatSupProdSpec.do?isCheck=false');
    }
    
    function myClose(){
    	frameElement.dialog.close();
    }
    </script>
</title>
</head>
<body>
	<div class="container">
        <div class="center">
            <table class="table-layout" width="100%">
                <tr>
                    <td class="label">产品名称：</td>
                    <td class="ipt">
                        <input id="prod_name" type="text" style="width:180px;"/>
                    </td>
                    <td class="label">规格名称：</td>
                    <td class="ipt">
                        <input id="spec_name" type="text" style="width:180px;" />
                    </td>
                    <td class="label">类型：</td>
                    <td class="ipt">
                        <select id="prod_type_name1" name="is_impl" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">主条码：</td>
                    <td class="ipt">
                        <input id="bar_code" type="text" style="width:180px;"/>
                    </td>
                    <td class="label">审核状态：</td>
                    <td class="ipt">
                        <input id="check_state" type="text" style="width:180px;"/>
                    </td>
                    <td class="label">是否植入：</td>
                    <td class="ipt">
                        <select id="is_impl" name="is_impl" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                	<td class="label">供应商：</td>
                    <td class="ipt">
                        <select id="sup_id" name="sup_id" style="width:280px;"></select>
                    </td>
                    <td align="right" class="l-table-edit-td" colspan="4">
						<button id="serchBtn" accessKey="Q" onclick="search();"><b>查询（<u>Q</u>）</b></button>
						&nbsp;&nbsp; 
						<button id="closeBtn" accessKey="C" onclick="myClose();"><b>关闭（<u>C</u>）</b></button>
						&nbsp;&nbsp; &nbsp;&nbsp; &nbsp; 
					</td>
                </tr>
            </table>
            <div id="invList"></div>
            <div id="maingrid"></div>
        </div>
   </div>
</body>
</html>