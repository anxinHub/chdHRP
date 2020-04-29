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
	<jsp:param value="select,datepicker,dialog,ligerUI,grid,pageOffice" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var parms= [];
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
    var year_input,index_code_select,dept_id_select;
    
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


		index_code_select = $("#index_code_select").etSelect({
			defaultValue:"none",
			url:"../../../queryBudgIndexDict.do?isCheck=false",
			onChange:query
		});
		
		dept_id_select = $("#dept_id_select").etSelect({
			defaultValue:"none",
			url:"../../../queryDept.do?isCheck=false",
			onChange:query
		});
	}
    
	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	};
    
    //查询
    function  query(){
    	parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms, 'queryWorkDeptYearExecute.do');
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '年度', name: 'year', width:100,editable: false
					},
			   { display: '科室编码', name: 'dept_code',width:120,editable: false
					},
			   { display: '科室名称', name: 'dept_name',width:140,editable: false
					},
			   { display: '指标编码', name: 'index_code',width:120,editable: false
					},
			   { display: '指标名称', name: 'index_name',width:140,editable: false
					},
			   { display: '指标值(E)', name: 'execute_value', align: 'right',width:140,dataType: "float",
						render:function(ui){
				   			var value = ui.cellData;
				   			if(value){
				   				return formatNumber(value,2,1);
				   			}
						}
					},
               { display: '说明(E)', name: 'remark',minWidth: 555, dataType: "string",
					}
               ],
               dataModel : {
            	   method: 'POST',
            	   location: 'remote',
            	   url: '',
            	   recIndx: 'year'
               },
               usePager: false,
               width: '100%',
               height: '100%',
               checkbox: true,
               editable: true,
               toolbar: {
   				items: [
   					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
   					{ type: "button", label: '添加', icon: 'plus', listeners: [{ click: add_open }] },
   					{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
   					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
   					{ type: "button", label: '下载导入模板', icon: 'arrowthickstop-1-s', listeners: [{ click: downTemplate }] },
   					{ type: "button", label: '导入', icon: 'arrowthick-1-n', listeners: [{ click: imp }] },
   					{ type: "button", label: '打印', icon: 'script', listeners: [{ click: printData }] }
   				]
   			}
        });
    }
    
    //添加
    function add_open() {
        $.etDialog.open({
            url: 'workDeptYearExecuteAddPage.do?isCheck=false',
            height: 350,
            width: 700,
            title: '科室年度业务预算执行添加',
            btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgWorkDeptYearExecute();
            }
        });
    }
    
    //修改保存
    function save(){
    	var data = grid.getChanges();
    	
		if( data.updateList.length == 0 ){
    		$.etDialog.error('没有需要保存的数据');
    		return;
    	}
		var ParamVo =[]; 
	 	$(data.updateList).each(function (){
			ParamVo.push( 
				this.year +"@"+ 
				this.dept_id +"@"+ 
				this.index_code +"@"+ 
				this.execute_value +"@"+ 
				(this.remark?this.remark:"-1")
			);
		})
		ajaxPostData({
		   url: 'updateWorkDeptYearExecute.do?isCheck=false',
		   data: {ParamVo : ParamVo.toString()},
		   success: function (responseData) {
		   	  query();
		   }
		})
    }
    
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){	
           		ParamVo.push(
					this.rowData.group_id   +"@"+ 
					this.rowData.hos_id   +"@"+ 
					this.rowData.copy_code   +"@"+ 
					this.rowData.year   +"@"+ 
					this.rowData.dept_id   +"@"+ 
					this.rowData.index_code 
				)
			});
            $.etDialog.confirm('确定删除?', function (yes){
         		if(ParamVo.length > 0){
         			ajaxPostData({
         			    url: 'deleteWorkDeptYearExecute.do',
         			    data: {ParamVo : ParamVo.toString()},
         			    success: function (responseData) {
         			    	query();
         			    }
         			})
         		}
	        }); 
        }
   	}
    
    function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'workDeptYearExecuteImportPage.do?isCheck=false'
		});
		layer.full(index);
    }
    
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    //打印
    function printData(){
    	if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads={}; 
    	var printPara={
            title: "科室年度业务预算执行",//标题
            columns: JSON.stringify(grid.getPrintColumns()),//表头
            class_name: "com.chd.hrp.budg.service.business.compilationbasic.dpetexecute.BudgWorkDeptYearExecuteService",            
            method_name: "getPrintData",
            bean_name: "budgWorkDeptYearExecuteService",
            heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
            foots: '',//表尾需要打印的查询条件,可以为空 
        };
        $.each(parms,function(i,obj){
            printPara[obj.name]=obj.value;
        }); 

        officeGridPrint(printPara);
    	
    	
    };
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('P', printData);
		hotkeys('I', imp);
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>
	
</body>
</html>
