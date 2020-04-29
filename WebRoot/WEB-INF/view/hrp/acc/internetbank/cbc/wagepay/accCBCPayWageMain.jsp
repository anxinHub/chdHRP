<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/CHD-HRP/lib/hrp/acc/internetbank/icbc/common.js"
	type="text/javascript"></script>
	 <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree" name="plugins" />
    </jsp:include>
<script type="text/javascript">
	var year_Month = '${sessionScope.wage_year_month}';

	if (year_Month.toString() == "000000") {

		var date = new Date;

		var year = date.getFullYear();

		var month = date.getMonth() + 1;

		month = (month < 10 ? "0" + month : month);

		year_Month = (year.toString() + month.toString());

	}
	 var wage_code;//工资套全局变量
	 var grid, tree ,item_code;
	$(function() {
		loadDict();
		loadHead(); //加载数据
		loadTree();
	});

	function zTreeOnClick(event, treeId, treeNode) {

		if (wage_code != '0') {

			wage_code = treeNode.id;
			var acc_time = $("#acc_time").val();
			var	acc_year = year_Month.substring(0, 4);
			item_code.reload({
	          	url:"../../queryAccWageItem.do?isCheck=false&is_stop=0&wage_code="+wage_code+'&acc_year='+acc_year 
	        });
		}

	};

	//查询
	function query() {//根据表字段进行添加查询条件

		parms = [];
		if (!wage_code) {

			$.etDialog.error('请选择左侧工资套！');

			return false;
		}

		var acc_time = $("#acc_time").val();

		if (!acc_time) {

			$.etDialog.error('请选择会计区间！');

			return false;

		}

		var item_code = $("#item_code").val();

		if (!item_code) {

			$.etDialog.error('请选择工资项名称！');

			return false;

		}

		parms.push({
			name : 'wage_code',
			value : wage_code
		});

		parms.push({
			name : 'item_code',
			value : item_code
		});

		parms.push({
			name : 'emp_kind_code',
			value : $("#kind_code").val()
		});

		parms.push({
			name : 'emp_code',
			value : $("#emp_code").val()
		});

		parms.push({
			name : 'acc_year',
			value : acc_time.split(".")[0]
		});

		parms.push({
			name : 'acc_month',
			value : acc_time.split(".")[1]
		});

		parms.push({
			name : 'is_city_same',
			value : $("#is_city_same").val()
		});

		parms.push({
			name : 'is_bank_same',
			value : $("#is_bank_same").val()
		});

		//加载查询条件
		grid.loadData(parms,"queryAccWagePayMain.do");

	}

	function myPrint() {

		if (grid.getData().length == 0) {

			$.etDialog.error("请先查询数据！");

			return;
		}
		var printPara = {
			title : "工资支付查询",//标题
			columns : JSON.stringify(grid.getPrintColumns()),//表头
			class_name : "com.chd.hrp.acc.service.InternetBank.cbc.AccCBCPayWageService",
			method_name : "queryAccCBCPayWagePrint",
			bean_name : "accCBCPayWageService"

		};
		//执行方法的查询条件
		$.each(parms, function(i, obj) {
			printPara[obj.name] = obj.value;
		});
		officeGridPrint(printPara);
	}

	function pay() {

		var acc_time = $("#acc_time").val();

		if (!acc_time) {

			$.ligerDialog.warn('请选择会计区间！');

			return false;

		}

		if (!wage_code) {

			$.ligerDialog.warn('请选择左侧工资套！');

			return false;

		}

		var item_code = $("#item_code").val();

		if (!item_code) {

			$.ligerDialog.warn('请选择工资项目！');

			return false;

		}

		var formPara = {

			wage_code : wage_code,

			acc_year : acc_time.split(".")[0],

			acc_month : acc_time.split(".")[1]

		};

		ajaxPostData({
				url:"../../../accempaccount/queryAccEmpAccountCount.do?isCheck=false",
				data: formPara,
				success: function(responseData) {
					if (responseData.num != "0") {
						var para = "&wage_code=" + wage_code + "&acc_year="
								+ acc_time.split(".")[0] + "&acc_month="
								+ acc_time.split(".")[1];
						$.etDialog.open({
									url : '../../accempaccount/updateAccEmpAccountCountMain.do?isCheck=false'
											+ para,
									height : 500,
									width : 870,
									title : '维护职工账号信息',
									modal : true,
									showToggle : false,
									showMax : false,
									showMin : false,
									isResize : true
								});

					} else {

						var para = "&payFlag=0" + "&wage_code=" + wage_code
								+ "&item_code=" + item_code + "&acc_year="
								+ acc_time.split(".")[0] + "&acc_month="
								+ acc_time.split(".")[1];

						$.etDialog.open({
							url : 'payAccCBCWagePage.do?isCheck=false'
									+ para,
							height : 460,
							width : 870,
							title : '补录网上银行信息',
							modal : true,
							showToggle : false,
							showMax : false,
							showMin : false,
							isResize : true,
						});
				}
			}
		})

	}

	function loadHead() {
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
		        gridObj.columns=[{
						display : '职工编码',
						name : 'emp_code',
						width : 100,
						align : 'left'
					},

					{
						display : '职工名称',
						name : 'emp_name',
						width : 150,
						align : 'left'
					},

					{
						display : '职工分类',
						name : 'kind_name',
						width : 150,
						align : 'left'
					},

					{
						display : '银行账户',
						name : 'account_name',
						width : 180,
						align : 'left'
					},

					{
						display : '同城',
						name : 'is_city_same',
						width : 60,
						align : 'left',
						render : function(item) {
							for (var i = 0; i < is_city_same_data.length; i++) {
								if (is_city_same_data[i]['id'] == item.is_city_same)
									return is_city_same_data[i]['text'];
							}
						}
					},

					{
						display : '同行',
						name : 'is_bank_same',
						width : 60,
						align : 'left',
						render : function(item) {
							for (var i = 0; i < is_bank_same_data.length; i++) {
								if (is_bank_same_data[i]['id'] == item.is_bank_same)
									return is_bank_same_data[i]['text'];
							}
						}
					},

					{
						display : '实发合计',
						name : 'payamt',
						width : 120,
						align : 'right',
						render : function(ui) {
							return $.paramquery.formatCurrency(ui.rowData.payamt)
						}
					} ];
		        
		        gridObj.toolbar = {
		                items: [{
		                	 type: "button",
		                     label: '查询',
		                     icon: 'search',
		                     id: 'search',
		                     listeners: [{
		                         click: query
		                     }]
						},{
							
							  type: "button",
			                    label: '网上支付',
			                    icon: 'communication',
			                    id:'carry',
			                    listeners: [{
			                        click: pay
			                    }]
							 
						},{
							  type: "button",
			                    label: '转换',
			                    icon: 'down',
			                    id:'export',
			                    listeners: [{
			                        click: myPrint
			                    }]
						} ]
					}
		grid = $("#maingrid").etGrid(gridObj);
	}

	//加载方案树
	function loadTree() {
		   tree = $("#mainTree").etTree({
	            async: {
	                enable: true,
	                url: 'queryAccCBCPayWageTree.do?isCheck=false'
	            },
		        callback: {
		    		onClick: zTreeOnClick
		    	}
	        })
	}

	function loadDict() {
		kind_code = $("#kind_code").etSelect({
          	url:"../../../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0",
            defaultValue: "none"
        });
		emp_code = $("#emp_code").etSelect({
          	url:"../../../../sys/queryEmpDictForCode.do?isCheck=false&is_stop=0",
            defaultValue: "none"
        });
		item_code = $("#item_code").etSelect({
          	url:"../../queryAccWageItem.do?isCheck=false&is_stop=0",
            onChange: function (value,text){
				grid.changeHeaderText('payamt',item_code.getText());
            }
        });
		is_bank_same = $("#is_bank_same").etSelect({
          	url:"../../../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0",
            defaultValue: "none"
        });
		is_bank_same = $("#is_bank_same").etSelect({
             options: [{
                 id: 1,
                 text: '是'
             }, {
                 id: 0,
                 text: '否'
             }],
             defaultValue: "none"
         });
		is_city_same = $("#is_city_same").etSelect({
             options: [{
                 id: 1,
                 text: '是'
             }, {
                 id: 0,
                 text: '否'
             }],
             defaultValue: "none"
         });
		acc_time = $("#acc_time").etSelect({
          	url:"../../../queryYearMonth.do?isCheck=false",
            defaultValue: year_Month.substring(0, 4) + "."	+ year_Month.substring(4, 6).toString()
        });
	 

	}
</script>

</head>

<body >
 	<div class="container">
        <div class="left border-right">
            <div class="button-group">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        
		<div  class="center">
			<table class="table-layout">
				<tr>
					<td class="label">会计期间：</td>
					<td  class="ipt">
					<input style="width: 160" name="acc_time" type="text" id="acc_time" /></td>
					<td class="label">工资项名称：</td>
					<td class="ipt">
					<input style="width: 160"  name="item_code" type="text" id="item_code" /></td>
					<td class="label">是否同城：</td>
					<td class="ipt">
					<input style="width: 160"  name="is_city_same" type="text" id="is_city_same" /></td>
				</tr>
				<tr>
					<td class="label">职工分类：</td>
					<td class="ipt">
					<input style="width: 160"  name="kind_code" type="text" id="kind_code" ltype="text"/></td>
					<td class="label">职工名称：</td>
					<td class="ipt">
					<input style="width: 160" name="emp_code" type="text" id="emp_code" ltype="text"/></td>
					<td class="label">是否同行：</td>
					<td class="ipt">
					<input style="width: 160" name="is_bank_same" type="text" id="is_bank_same" /></td>
				<tr>
			</table>
			<div id="maingrid"></div>
		</div>
	</div>
</body>
</html>
