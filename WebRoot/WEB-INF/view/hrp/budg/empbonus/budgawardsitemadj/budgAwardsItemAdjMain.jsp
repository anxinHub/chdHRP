<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function (){
        //loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
    var year_input,emp_type_select,awards_item_select

	function init(){
		getData("../../queryBudgYear.do?isCheck=false",function(data){
			year_input = $("#year_input").etDatepicker({
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate:data[data.length - 1].text,
				maxDate:data[0].text,
				todayButton: false,
				onSelect:function(){
					setTimeout(function() {
						query();
					}, 10);
				}
			});
		});

		emp_type_select = $("#emp_type_select").etSelect({
			url:"../../queryBudgEmpType.do?isCheck=false",
			defaultValue:"none",
			onChange:query
		});

		awards_item_select = $("#awards_item_select").etSelect({
			url:"../../queryBudgAwardsItem.do?isCheck=false",
			defaultValue:"none",
			onChange:query
		});
	}

	//ajax获取数据
	function getData(url,callback){
		$.ajax({
			url:url,
			dataType:"JSON",
			type:"post",
			success:function(res){
				if(typeof callback ==="function"){
					callback(res);
				}
			}
		})
	}

	function query(){
		var search = [
			{name:'budg_year',value:year_input.getValue()},
			{name:'emp_type_code',value:emp_type_select.getValue()},
			{name:'awards_item_code',value:awards_item_select.getValue()}
		]
		//加载查询条件
		grid.loadData(parms,"queryBudgAwardsItemAdj.do");
	}
	
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '预算年度', name: 'budg_year', align: 'left',width:"8%"
			   },
               { display: '奖金项目', name: 'awards_item_name', align: 'left',width:"20%"
			   },
               { display: '职工类别编码', name: 'type_name', align: 'left',width:"10%"
			   },
               { display: '调整金额(E)', name: 'adj_amount', align: 'left',dataType: 'float',width:"15%",
				    editable: function (ui) {
	           	        if (ui.rowData.adj_amount) {
	           	            return false;
	           	        }
	           	        return true
           	    	},
					render:function(ui){
            	   		var value = ui.cellData;
						if(value){
							return formatNumber(value,2,1);
						}
					}
			   },
               { display: '调整比例(E)', name: 'adj_rate', align: 'left',dataType: 'float',width:"15%",
				    editable: function (ui) {
				        if (ui.rowData.adj_amount) {
				            return false;
				        }
				        return true
				    },
					render:function(ui){
            	        var value = ui.cellData;
						if(value){
							return value+"%";
						}
					}
			   },
               { display: '计划调整月份(E)', name: 'adj_month', align: 'left',dataType: 'string',width:"8%"
			   },
               { display: '说明(E)', name: 'remark', align: 'left',dataType: 'string',minWidth:"20%"
			   }
           ],
           dataModel:{
	           method:'POST',
	           location:'remote',
	           url:'',
	           recIndx: 'year' //必填 且该列不能为空  
           },
           usePager:true,width: '100%', height: '100%',checkbox: true,
		   toolbar : {
	           items: [
	               { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
	               { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
	               { type: 'button', label: '批量设置', listeners: [{ click: add_open }], icon: 'add' },
	               { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
	           ]
           }
       });
    }
    
    //翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
    function updateDateExist() {
        var data = grid.changedCells;
        if (!isObjEmpty(data)) {
            $.etDialog.warn('数据有变更【<span style="color:red">不支持跨页保存数据,请先保存变更数据(若不想保存变更数据,请刷新页面确定后再操作)</span>】');
            return false;
        }

        function isObjEmpty(obj) { //新注册的方法判断对象是否为空对象
            for (var i in obj) {
                return false;
            }
            return true;
        }
    }
    
    function add_open() {
        $.etDialog.open({
            url: 'budgAwardsItemAdjAddPage.do?isCheck=false&',
            height: 400,
            width: 700,
            title: '奖金变动',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgAwardsItemAdj();
                query();
            }
        });
    }
    
    //保存
    function save() {
        var data = grid.getAllData();
        if (data.length == 0) {
            $.etDialog.error('请添加行数据');
        } else {
            var ParamVo = [];
            $(data).each(function() {
                ParamVo.push(
                    this.budg_year + "@" +
                    this.awards_item_code + "@" +
                    this.emp_type_code + "@" +
                    (this.adj_amount ? this.adj_amount : "") + "@" +
                    (this.adj_rate ? this.adj_rate : "") + "@" +
                    this.adj_month + "@" +
                    (this.remark ? this.remark : "")
                )
            });

            if (!ParamVo) {
                $.etDialog.error('没有需要保存的数据,请确认添加数据后操作!');
            }
            ajaxPostData({
                url: "updateBudgAwardsItemAdj.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    if (responseData.state == "true") {
                        query();
                    }
                }
            });
        }
    }
    
    //删除
    function remove() {
        var data = grid.selectGet();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = [];
            $(data).each(function() {
                ParamVo.push(
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.budg_year + "@" +
                    this.rowData.emp_type_code + "@" +
                    this.rowData.awards_item_code
                )
            });
            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgAwardsItemAdj.do",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        if (responseData.state == "true") {
                            query();
                        }
                    }
                });
            });
        }
    }
    //键盘事件
    function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
    }
 	  
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">职工类别：</td>
				<td class="ipt">
					<select name="" id="emp_type_select" style="width:180px;"></select>
				</td>
				<td class="label">工资项目：</td>
				<td class="ipt">
					<select name="" id="awards_item_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
