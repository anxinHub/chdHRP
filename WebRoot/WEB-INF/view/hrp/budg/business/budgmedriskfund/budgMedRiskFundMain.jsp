<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var year ;
    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });

    var year_input, month_input, dept_name_select;

    function init() {
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	initColumns();
                }, 10);
            },
            defaultDate: true
        });

        month_input = $("#month_input").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "mm",
            showNav: false,
            todayButton: false,
            onChanged: query
        });

        dept_name_select = $("#dept_name_select").etSelect({
            url: "../../queryBudgDeptDict.do?isCheck=false",
            defaultValue: "none",
            onChange: query
        });

    }

    function query() {
        var search = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() }
        ];
        grid.loadData(search, "queryBudgMedRiskFund.do");
    }
    
    function loadHead() {
    	var columns = [
    		{ display: '预算年度', name: 'budg_year', width: "10%",editable:setEdit ,
	           	 editor: {
	        		 keyField:'budg_year',
	        	     type: 'select',  //编辑框为下拉框时
	        	     //source:[],   //  静态数据接口  也可以是回调函数返回值
	        	     url: '../../queryBudgYear.do?isCheck=false',      //  动态数据接口
	        	     change:queryWorkload ,
	        	 }, 
        	},
    		{ display: '月份', name: 'month', width: "10%",editable:setEdit ,
        		editor: {
	 				valueField:'monthID',
	 				textField:'label',
	           	    type: 'select',  //编辑框为下拉框时
	           	    source:[{id : "01",label : "1月"}, {id : "02",label : "2月"
	           			}, {id : "03",label : "3月"}, {id : "04",label : "4月"
	           			}, {id : "05",label : "5月"}, {id : "06",label : "6月"
	           			}, {id : "07",label : "7月"}, {id : "08",label : "8月"
	           			}, {id : "09",label : "9月"}, {id : "10",label : "10月"
	           			}, {id : "11",label : "11月"}, {id : "12",label : "12月"
	           			}
	           		],   //  静态数据接口  也可以是回调函数返回值
	           		change:queryWorkload ,
	           	}
    		},
    		{ display: '科室名称', name: 'dept_name', width: "22%",editable:setEdit,
	 			editor:{
	 				type:'select' ,
	 				keyField:'dept_id',
	 				url:'../../queryBudgDeptDict.do?isCheck=false' ,
	 				change:queryWorkload ,
	 			}
    		},
    		{ display: '科室收入预算（元）', name: 'income_budg', align: 'right', width: "22%",editable: false,
                render: function(ui) {
                    return formatNumber(ui.cellData, 2, 1);
                }
            },
            { display: '提取比例', name: 'risk_fund_rate', align: 'right', width: "10%",editable: false,
            	render: function(ui) {
                    return formatNumber(ui.cellData, 2, 1)+"‰";
                }
            },
            { display: '支出预算（元）', name: 'cost_budg', align: 'right', minWidth: "22%",
                render: function(ui) {
                    return formatNumber(ui.cellData, 2, 1);
                }
            }
       	];

       	var paramObj = {
       		height: '100%',
            checkbox: true,
            editable: true,
            columns: columns,
            toolbar: {
                items: [
	                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search'},
	                { type: 'button', label: '生成（<u>G</u>）', listeners: [{ click: generate }], icon: 'generate'},
	                { type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
	                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete'},
	            	{ type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
					{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
               	]
            }
       	}
        grid = $("#maingrid").etGrid(paramObj);
    }
	
	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
    
    function add_Row(){
    	grid.addRow() ;
    }
	//选择 年度、月份、科室后 查询其科室月份收入预算值 
    function queryWorkload(rowdata,celldata){
	   	var budg_year = rowdata.budg_year ;
	   	var month = rowdata.monthID ;
	   	grid.updateRow(celldata.rowIndx,{"dept_code":rowdata.dept_name.split(" ")[0]});
	   	if(rowdata.budg_year && rowdata.monthID && rowdata.dept_id){
	   		$.post("queryWorkload.do?isCheck=false&budg_year="+budg_year+"&month="+month+"&dept_id="+rowdata.dept_id,null,function(responseData){
	      			
	              var para = eval("("+responseData+")") ;
	              	
	              if(para){
	              	if(para.income_budg && para.risk_fund_rate){
	              		var cost_budg = para.income_budg * para.risk_fund_rate/1000 ;
	              		grid.updateRow(celldata.rowIndx,{"income_budg":para.income_budg,
	              			"risk_fund_rate":para.risk_fund_rate,"cost_budg":cost_budg});
	              	}else if(para.income_budg){
		              		 
		              	grid.updateRow(celldata.rowIndx,{"income_budg":para.income_budg});
		              		
		              	$.etDialog.error('<span style="color: red">提取比例未设置,请先设置医疗风险基金的提取比例</span>')
		              		
		             }else if(para.risk_fund_rate){
		              		 
		              	grid.updateRow(celldata.rowIndx,{"risk_fund_rate":para.risk_fund_rate});
		              		
		              	$.etDialog.error('<span style="color: red">所选年度月份科室收入预算数据不存在,请先编制科室月份医疗收入预算</span>')
		              }
	              		
	              }else{
	              	$.etDialog.error('<span style="color: red">请先编制科室月份医疗收入预算,并设置医疗风险基金的提取比例</span>')
	              }
	         });
	   	}
    }
    function save(){
    	
    	var data = grid.getChanges();
    	
    	var ParamVo =[];
    	
    	if(data.addList.length > 0 || data.updateList.length > 0){
    		
        	if(data.addList.length > 0){
        		
        		var addData = data.addList ;
        		
        		if(!validateGrid(addData)){
        			return  false ;
        		}
                $(addData).each(function (){	
                	
                	ParamVo.push(
    					this.budg_year   +"@"+ 
    					this.monthID   +"@"+ 
    					this.dept_id +"@"+ 
    					this.income_budg +"@"+ 
    					this.risk_fund_rate +"@"+
    					this.cost_budg +"@"+
    					this._rowIndx +"@"+ 
    					'1' //添加数据标识
    				) 
    			});
        	}
    		if(data.updateList.length > 0){
        		
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
               			this.budg_year   +"@"+ 
       					this.month   +"@"+ 
       					this.dept_id +"@"+ 
       					this.income_budg +"@"+ 
       					this.risk_fund_rate +"@"+
       					this.cost_budg +"@"+
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
				url:"saveBudgMedRiskFund.do?isCheck=false",
				data:{ParamVo : ParamVo.toString()},
				success:function (responseData){
	         		if(responseData.state=="true"){
						query();
	         		}
				}
			});
    	}else{
    		$.ligerDialog.warn('没有需要保存的数据!');
    	}
    }
    
    function generate() {
        $.etDialog.confirm('确定执行生成操作？', function() {
            var formPara = { budg_year: year_input.getValue() };

            ajaxPostData({
            	url: "generateBudgMedRiskFund.do?isCheck=false",
            	data: formPara,
            	success: function(responseData) {
                    query();
                }
            });
        });
    }
	
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.budg_year) {
				rowm+="[预算年度]、";
			}
			if (!v.month) {
				rowm+="[月份]、";
			}
			if (!v.dept_name) {
				rowm+="[科室名称]、";
			}
			if (!v.income_budg) {
				rowm+="[科室收入预算]、";
			}
			if (!v.risk_fund_rate) {
				rowm+="[提取比例]、";
			}
			
			if (!v.cost_budg) {
				rowm+="[支出预算]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.budg_year + v.monthID + v.dept_id 
			var value="第"+(Number(v._rowIndx)+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
			}
 		});
 		if(msg != ""){
 			$.etDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
    function imp() {
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgMedRiskFundImportPage.do?isCheck=false'
		});
		layer.full(index);
	}	
	function downTemplate(){
	
		location.href = "downTemplate.do?isCheck=false";
	}	
    function openUpdate(obj) {
        var parm =
            "budg_year=" + obj[0] + "&" +
            "month=" + obj[1] + "&" +
            "dept_id=" + obj[2];

        $.etDialog.open({
            url: 'budgMedRiskFundUpdatePage.do?isCheck=false&' + parm,
            height: 400,
            width: 500,
            title: '提取医疗风险基金预算编制',
            btn: ['保存', '关闭'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgMedRiskFund();
            }
        });
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgMedRiskFundAddPage.do?isCheck=false&budg_year=' + year_input.getValue(),
            height: 400,
            width: 500,
            title: '提取医疗风险基金预算编制',
            btn: ['保存', '关闭'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
		        iframeWindow.saveBudgMedRiskFund();
            }
        });
    }

    //删除
    function remove() {

        var data = grid.selectGetChecked();
        if (data.length == 0) {
            $.etDialog.error('请选择行');
        } else {
            var ParamVo = []; //后台删除数据
            var deletePageRow = []; // 页面删除数据
            $(data).each(function() {
                if (this.rowData.group_id) {
                    ParamVo.push(
                		this.rowData.group_id + "@" +
                    	this.rowData.hos_id + "@" +
                        this.rowData.copy_code + "@" +
                        this.rowData.budg_year + "@" +
                        this.rowData.month + "@" +
                        this.rowData.dept_id
                    )
                } else {
                    deletePageRow.push(this);
                }
            });
            if (ParamVo.length > 0) {
                $.etDialog.confirm('确定删除?', function() {
                    ajaxPostData({
                    	url: "deleteBudgMedRiskFund.do?isCheck=false",
                    	data: { ParamVo: ParamVo.toString() },
                    	success: function(responseData) {
                            query();
                        }
                    });
                });
            } else if (deletePageRow.length > 0) {
                grid.deleteRows(deletePageRow);
                $.etDialog.success("删除成功!");
            }
        }
    }

    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('G', generate);
        hotkeys('A', add);
        hotkeys('D', remove);
        hotkeys('I', imp);
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">预算年度：</td>
                <td class="ipt">
                    <input type="text" id="year_input" />
                </td>
                <td class="label">月份：</td>
                <td class="ipt">
                    <input type="text" id="month_input" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select name="" id="dept_name_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>