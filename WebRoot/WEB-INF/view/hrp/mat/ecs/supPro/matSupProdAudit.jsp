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
    var $grid, $sup_id, $fac_id;
    var $data, parentWindow; 
    $(function () {
        var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
        parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
        $data = parentWindow.$data;
        loadDict();
        loadGrid(); 
        search();
    })
    
	function loadDict() {
    	//供应商
    	$sup_id = $("#sup_id").etSelect({
    		options: [{id: $data.sup_id, text: $data.sup_name}], 
    		defaultValue: $data.sup_id
    	}); 
    	$sup_id.disabled();
    	
    	//生产厂商
		$fac_id = $("#fac_id").etSelect({
			url: "../../../mat/queryHosFacInv.do?isCheck=false",
			defaultValue: "none"
		});
    }
	
    //查询
    function search() {
    
        //根据表字段进行添加查询条件
        var parms = [];
        parms.push({
            name: 'sup_id',
            value: $sup_id.getValue()
        });
        parms.push({
            name: 'inv_name',
            value: $('#inv_name').val()
        });
        parms.push({
            name: 'inv_model',
            value: $('#inv_model').val()
        });
        parms.push({
            name: 'fac_id',
            value: $fac_id.getValue()
        });
        parms.push({
            name: 'bid_code',
            value: $('#bid_code').val()
        });
        parms.push({
            name: 'use_state',
            value: "1"
        });

        //加载查询条件	
        $grid.loadData(parms, 'queryMatInvList.do?isCheck=false');
    }
    
    //表格
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
	            display: '操作',
	            align: 'left',
	            name: '',
	            width: 90,
	       		render: function (ui) { // 修改页打开
	            	return '<input type = "button" value = "绑定关系" data-item=' + ui.rowIndx + ' class="audit g-btn"/>'
	            }
	        }, {
                display: '材料编码',
                align: 'left',
                name: 'inv_code',
                width: 120,
           		render: function (ui) { // 修改页打开
                    return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +'</a>'
                }
            }, {
                display: "材料名称",
                align: "left",
                width: 200,
                name: "inv_name"
            }, {
                display: "规格型号",
                align: "left",
                width: 140,
                name: "inv_model"
            }, {
                display: "交易编码",
                align: "left",
                width: 120,
                name: "bid_code"
            }, {
                display: "计划价",
                align: "left",
                width: 90,
                name: "plan_price"
            }, {
                display: "生产厂商",
                align: "left",
                width: 180,
                name: "fac_name"
            }, {
                display: "证件号",
                align: "left",
                width: 140,
                name: "cert_code"
            }, {
                display: "证件日期",
                align: "left",
                width: 160,
                name: "cert_date"
            }, {
                display: "是否代销",
                align: "left",
                width: 90,
                name: "is_com"
            }
        ];
        $grid = $("#maingrid").etGrid(gridObj);
        //查看材料
        $('#maingrid').on('click', '.td-a', function () {
            var index = $(this).attr('data-item') * 1;
            var data = $grid.getRowData(index);
            openInv(data.group_id, data.hos_id, data.copy_code, data.inv_id);
        })
        //绑定关系
        $('#maingrid').on('click', '.audit', function () {
            var index = $(this).attr('data-item') * 1;
            var data = $grid.getRowData(index);
            save(data.inv_id);
        })
    }
	
    //查看材料
    function openInv(group_id, hos_id, copy_code, inv_id){
    	//alert(hos_id + ", " +group_id+ ", " +copy_code+ ", " +inv_id)
		var paras = "inv_id="+inv_id.toString();

		parent.$.etDialog.open({
			title: '物资修改',
            height: $(window).height(),
            width: $(window).width(),
			url: 'hrp/mat/info/basic/inv/matInvUpdatePage.do?isCheck=false&' + paras.toString(),
			maxmin: true, resize: true, 
			parentframename: window.name
		});
//		parent.$.ligerDialog.open({
//			title: '物资修改',
//			height: $(window).height(),
//			width: $(window).width(),
//			url: 'hrp/mat/info/basic/inv/matInvUpdatePage.do?isCheck=false&' + paras.toString(),
//			maxmin: true, resize: true, 
//			parentframename: window.name
//		});
    }
    
    //绑定关系
    function save(inv_id){
		var formData = [];
    	formData.push({name:'check_state' , value :'2'})
    	formData.push({name:'prod_id' , value : $data.prod_id})
    	formData.push({name:'spec_id' , value : $data.spec_id})
    	formData.push({name:'chos_id' , value : $data.chos_id})
    	formData.push({name:'csup_id' , value : $data.csup_id})
    	formData.push({name:'sid' , value : $data.sid})
    	formData.push({name:'inv_id' , value : inv_id})
		 ajaxPostData({
             url: "addMatSupProdSpecInv.do?isCheck=false",
             data: formData,
             success: function (res) { 
                 if (res.state == true || res.state == "true") {
                	 parentWindow.search();
                	 closeAlter();
                 }
             }
         })
    }
    
    //添加新材料
    function addNew(){
    	parentWindow.$auditWindow = window;
    	parent.$.etDialog.open({
			title: '物资添加',
            height: $(window).height(),
            width: $(window).width(),
			url: 'hrp/sys/ecs/sup/addInvPage.do?isCheck=false',
			maxmin: true, resize: true, 
			parentframename: window.name,
            btn: ["保存", "取消"],
            btnAlign: 'center',
            btn1: function (index, el) {
                var frameWindow = parent.window[el.find('iframe')[0].name];
                frameWindow.save();
            }
		});
    }
    
    //关闭页面
    function myClose(){
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭 
    }
    //关闭页面并在父页面弹出操作成功提示
    function closeAlter(){
		
    	parentWindow.search();
   	 	parentWindow.$.etDialog.alert("操作成功", {icon: 1});
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭 
    }
    </script>
</title>
</head>
<body>
	<div class="container">
        <div class="center">
            <table class="table-layout" width="100%" >
                <tr>
                    <td class="label">供应商：</td>
                    <td class="ipt">
                        <select id="sup_id" style="width:180px;"></select>
                    </td>
                    <td class="label">材料名称：</td>
                    <td class="ipt">
                        <input id="inv_name" type="text" style="width:180px;" />
                    </td>
                    <td class="label">规格型号：</td>
                    <td class="ipt">
                        <input id="inv_model" type="text" style="width:180px;" />
                    </td>
                </tr>
                <tr>
                    <td class="label">生产厂商：</td>
                    <td class="ipt">
                        <select id="fac_id" style="width:180px;"></select>
                    </td>
                    <td class="label">交易码：</td>
                    <td class="ipt">
                        <input id="bid_code" type="text" style="width:180px;" />
                    </td>
                </tr>
                <tr>
                    <td align="right" class="l-table-edit-td">
						<button id="serchBtn" accessKey="Q" onclick="addNew();"><b>新增（<u>Q</u>）</b></button>
                    </td>
                    <td align="left" class="l-table-edit-td" colspan="3">
						<span style="color: red">←以下材料如果不是您想要的，您还可以添加新的材料</span>
                    </td>
                    <td align="right" class="l-table-edit-td" colspan="2">
						<button id="serchBtn" accessKey="Q" onclick="search();"><b>查询（<u>Q</u>）</b></button>
						&nbsp;&nbsp; 
						<button id="closeBtn" accessKey="C" onclick="myClose();"><b>关闭（<u>C</u>）</b></button>
						&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
					</td>
                </tr>
            </table>
            <div id="maingrid"></div>
        </div>
   </div>
</body>
</html>