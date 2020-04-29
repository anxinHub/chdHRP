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
    var year_input, month_input, dept_name_select, med_type_select;
    var grid;
    var budg_year ;
    $(function() {
        loadHead();
        loadHotkeys();
        init();
    });

    function init () {
        ajaxPostData({
            url: "../../../queryBudgYear.do?isCheck=false",
            success: function(data) {
                year_input = $("#year_input").etDatepicker({
                    defaultDate: data[0].text,
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    minDate: data[data.length - 1].text,
                    maxDate: data[0].text,
                    todayButton: false,
                    onChanged: query
                });
            }
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
            url: 'queryHosDeptDict.do?isCheck=false',
            defaultValue: "none",
            onChange: query
        });

        med_type_select = $("#med_type_select").etSelect({
            url: '../../../queryBudgDrugType.do?isCheck=false',
            defaultValue: "none",
            onChange: query
        });
    }
    
    function query() {
        var search = [
            { name: 'budg_year', value: year_input.getValue() },
            { name: 'month', value: month_input.getValue() },
            { name: 'dept_id', value: dept_name_select.getValue() },
            { name: 'med_type_id', value: med_type_select.getValue().split(",")[0] }
        ];

        grid.loadData(search, 'queryBudgDrug.do?isCheck=false');
    }

    function loadHead() {
        var columns = [
            { display: '预算年度', name: 'budg_year', width: 80,editable:setEdit ,
	           	 editor: {
	           		 keyField:'budg_year',
	           	     type: 'select',  //编辑框为下拉框时
	           	     //source:[],   //  静态数据接口  也可以是回调函数返回值
	           	     url: '../../../queryBudgYear.do?isCheck=false',      //  动态数据接口
	           	  	 change:setLastCostAndRate
           		 },
            },
            { display: '月份', name: 'month', width: 50,editable:setEdit ,
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
           			}],   //  静态数据接口  也可以是回调函数返回值
           			change:setLastCostAndRate
           	    }	
            },
            { display: '科室名称', name: 'dept_name', width: 120,editable:setEdit ,
            	editor:{
	 				keyField:'dept_id',
            	    type: 'select',  //编辑框为下拉框时
            	    //source:[],   //  静态数据接口  也可以是回调函数返回值
            	    url: '../../../queryBudgDeptDict.do?isCheck=false',      //  动态数据接口
            	    change:setLastCostAndRate
            	}
            },
            { display: '药品分类', name: 'med_type_name', width: 120,editable:setEdit ,
            	editor:{
	 				keyField:'med_type_id',
            	    type: 'select',  //编辑框为下拉框时
            	    //source:[],   //  静态数据接口  也可以是回调函数返回值
            	    url: 'queryBudgMedTypeSubj.do?isCheck=false&budg_year='+budg_year,      //  动态数据接口
            	    change:setLastCostAndRate ,
            	    //与年度联动查询
	           	    create:function(rowdata,celldata,setting){
	           	    	 if(rowdata.budg_year){
	           	    		 setting.url = 'queryBudgMedTypeSubj.do?isCheck=false&budg_year='+rowdata.budg_year;
	           	    	 }else{
	           	    		 $.ligerDialog.error('请先填写年度');
	           	    		 return false ;
	           	    	 }
	           	    }
            	}
            },
            { display: '上年同期支出', name: 'last_cost', align: 'right', width: 110,editable:false ,
                render: function(ui) {
                    ui.rowData.last_cost = ui.rowData.last_cost || 0;

                    return formatNumber(ui.rowData.last_cost, 2, 1);
                }
            },
            { display: '收入预算增长比例', name: 'grow_rate',align: 'center', width: 110,editable:false ,
                render: function(ui) {
                    ui.rowData.grow_rate = ui.rowData.grow_rate || 0;

                    return ui.rowData.grow_rate + "%";
                }
            },
            { display: '计算值', name: 'count_value', align: 'right', width: 100,editable:false ,
                render: function(ui) {
                    ui.rowData.count_value = ui.rowData.count_value || 0;

                    return formatNumber(ui.rowData.count_value, 2, 1);
                }
            },
            { display: '调整比例(%)', name: 'adj_rate', align: 'center',width: 90,dataType:'float',
            	editor:{change:setCostValue},
                render: function(ui) {
                    ui.rowData.adj_rate = ui.rowData.adj_rate || 0;

                    return ui.rowData.adj_rate + "%";
                }
            },
            { display: '支出预算', name: 'cost_budg', align: 'right', width: 110,editable:false ,
                render: function(ui) {
                    ui.rowData.cost_budg = ui.rowData.cost_budg || 0;

                    return formatNumber(ui.rowData.cost_budg, 2, 1);
                }
            },
            { display: '说明', name: 'remark', minWidth: '17%' }
        ];
        var toolbar = {
            items: [
                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: query }], icon: 'search' },
                { type: 'button', label: '生成（<u>G</u>）', listeners: [{ click: generate }], icon: 'generate' },
                { type: 'button', label: '添加行（<u>A</u>）', listeners: [{ click: add_Row }], icon: 'add' },
                { type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                { type: 'button', label: '批量调整', listeners: [{ click: update }], icon: 'update' },
            ]
        }

        var paramObj = {
            columns: columns,
            toolbar: toolbar,
            editable: true,
            height: '100%',
            checkbox: true,
        };
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
	//添加行
    function add_Row(){
    	grid.addRow() ;
    }
	
	//选择完 年度、月份、科室 、药品分类后 查询其上年同期支出 并更新计算收入预算增长比例和计算值
    function setLastCostAndRate(rowdata,celldata){
    	setTimeout(function (){  // 为了让数据同步 增加延时定时器
            if(rowdata.dept_name){
                grid.updateRow(celldata.rowIndx,{"dept_code":rowdata.dept_name.split(" ")[0]});
            }
            
            if(rowdata.med_type_name){
                grid.updateRow(celldata.rowIndx,{"med_type_code":rowdata.med_type_name.split(" ")[0]});
            }
            
            if(rowdata.budg_year &&  rowdata.month && rowdata.dept_code && rowdata.med_type_id){
                var month = rowdata.monthID?rowdata.monthID:rowdata.month ;
                $.post("queryLastCostAndRate.do?isCheck=false&year="+rowdata.budg_year+"&month="+month
                        +"&dept_id="+rowdata.dept_id+"&med_type_id="+rowdata.med_type_id,null,function(responseData){
                       
                       var para = eval("("+responseData+")") ;
                       
                       if(para){
                           if(para.last_cost){//更新上年同期支出
                               grid.updateRow(celldata.rowIndx,{"last_cost":para.last_cost});
                               
                            }else{
                               $.etDialog.error('<span style="color: red">上年同期支出数据不存在,</span>')
                            }
                            //计算 收入预算增长比例和计算值 并更新
                            if(para.income_value && para.execute_value ){
                               //收入预算增长比例=收入预算/上年收入-1
                               var grow_rate = para.income_value/para.execute_value -1 ;
                               //计算值=上年同期支出*（1+收入预算增长比例）
                               var count_value = rowdata.last_cost *(1+grow_rate)
                               grid.updateRow(celldata.rowIndx,{"grow_rate":formatNumber(grow_rate,2,1),"count_value":formatNumber(count_value,2,1)});
                            }else{
                               grid.updateRow(celldata.rowIndx,{"grow_rate":0.00,"count_value":rowdata.last_cost});
                            }
                            
                       }else{
                           $.etDialog.error('<span style="color: red">上年同期支出数据不存在,</span>')
                       }
                });
            }
        },300)
     }
	//填写、修改 调整比例后  计算更新 支出预算
	function setCostValue(rowdata,celldata) {
		
		if(rowdata.count_value){
			if (rowdata.adj_rate) {
				//支出预算=计算值*（1+调整比例）
				var cost_budg = Number(rowdata.count_value) * (1 + Number(rowdata.adj_rate) / 100);

				grid.updateRow(celldata.rowIndx,{'cost_budg':cost_budg});
			} else {
				$.etDialog.error('增长比例不能为空,且必须位数字');
			}
		}else{
			$.etDialog.error('请先填写预算年度、月份、科室名称、药品分类');
		}
	}
	//保存
	function save() {
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
    					this.monthID +"@"+ 
    					this.dept_id +"@"+ 
    					this.med_type_id +"@"+ 
    					this.last_cost +"@"+
    					this.grow_rate+"@"+ 
    					this.count_value +"@"+ 
    					(this.adj_rate?this.adj_rate:"") +"@"+
    					this.cost_budg +"@"+ 
    					(this.remark?this.remark:"")   	+"@"+ 
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
       					this.month +"@"+ 
       					this.dept_id +"@"+ 
       					this.med_type_id +"@"+ 
       					this.last_cost +"@"+
       					this.grow_rate+"@"+ 
       					this.count_value +"@"+ 
       					(this.adj_rate?this.adj_rate:"") +"@"+
       					this.cost_budg +"@"+ 
       					(this.remark?this.remark:"")   	+"@"+ 
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
    			url:"saveBudgDrug.do?isCheck=false", 
    			data:{ ParamVo: ParamVo.toString() }, 
   				success:function (responseData) {
					if (responseData.state == "true"){
						query();
					}
   				}
			});
		}else{
			$.ligerDialog.error('没有需要保存的数据');
		}
	}
	
    //批量调整
    function update() {
        var data = grid.selectGet();

        if (data.length === 0) {
            $.etDialog.error('请选择行');
            return;
        }
        $.etDialog.open({
            url: 'budgDrugUpdateAdjRatePage.do?isCheck=false&',
            height: 350,
            width: 400,
            title: '批量设置调整比例',
            btn: ['确定', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgChargeMat()
            }
        });
    }

    //生成
    function generate() {
        var formPara = {
            budg_year: year_input.getValue(),
            month: month_input.getValue(),
            dept_id: dept_name_select.getValue(),
            med_type_id: med_type_select.getValue()
        }
        $.etDialog.confirm('生成功能产生的新数据将会将覆盖原有数据,确定生成?', function() {
            ajaxPostData({
                url: "generateBudgDrug.do?isCheck=false",
                data: formPara,
                success: function(responseData) {
                    query();
                }
            })
        });
    }

    function openUpdate(obj) {
        var parm =
            "budg_year=" + obj[0] + "&" +
            "month=" + obj[1] + "&" +
            "dept_id=" + obj[2] + "&" +
            "med_type_id=" + obj[3];

        $.etDialog.open({
            url: 'budgDrugUpdatePage.do?isCheck=false&' + parm.toString(),
            height: 400,
            width: 500,
            title: '科室药品支出预算编制',
            btn: ['保存', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];

                iframeWindow.saveBudgDrug()
            }
        });
    }

    function add_open() {
        $.etDialog.open({
            url: 'budgDrugAddPage.do?isCheck=false&budg_year=' + $("#budg_year").val(),
            height: 400,
            width: 500,
            title: '科室药品支出预算编制',
            btn: ['保存', '取消'],
            btn1: function (index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                
                iframeWindow.saveBudgDrug()
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
	                    this.rowData.dept_id + "@" +
	                    this.rowData.med_type_id
                    )
                } else {
                    deletePageRow.push(this);
                }
            });
            if (ParamVo.length > 0) {
                $.etDialog.confirm('确定删除?', function() {
                    ajaxPostData({
                    	url: "deleteBudgDrug.do?isCheck=false",
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
    
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.budg_year) {
				rowm+="[年度]、";
			}
			if (!v.month) {
				rowm+="[月份]、";
			}
			if (!v.dept_name) {
				rowm+="[科室名称]、";
			}
			if (!v.med_type_name) {
				rowm+="[药品分类]、";
			}
			if (!v.cost_budg) {
				rowm+="[支出预算]、";
			}
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.month + v.dept_id + v.med_type_id 
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
	
    //键盘事件
    function loadHotkeys() {
        hotkeys('Q', query);
        hotkeys('G', generate);
        hotkeys('A', add);
        hotkeys('D', remove);
    }
</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
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
            <tr>
                <td class="label">药品分类：</td>
                <td class="ipt">
                    <select name="" id="med_type_select" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="maingrid"></div>
</body>

</html>