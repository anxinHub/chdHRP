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
	<jsp:param value="select,datepicker,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var year;
    $(function (){
    	//加载数据
    	loadHead(null);	
    	init();
    });
    
    var year_input,month_input,cash_dire,cash_type_id,cash_item_id;
    
	function init(){
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: true
        });


		month_input = $("#month_input").etDatepicker({
			view:'months',
			minView:'months',
			dateFormat:"mm",
			todayButton:false,
			showNav:false,
			onSelect:query
		});

		cash_dire = $("#cash_dire").etSelect({
			url:'../../queryCashDire.do?isCheck=false',
			defaultValue: "none",
			onChange: query
		});

		cash_type_id = $("#cash_type_id").etSelect({
			url:'../../queryCashType.do?isCheck=false',
			defaultValue: "none",
			onChange: query
		});
		
		cash_item_id = $("#cash_item_id").etSelect({
			url:'../../queryCashItem.do?isCheck=false',
			defaultValue: "none",
			onChange: query
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
    	var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'month', value: month_input.getValue() },
			{ name: 'cash_dire', value: cash_dire.getValue() },
			{ name: 'cash_type_id', value: cash_type_id.getValue() },
			{ name: 'cash_item_id', value: cash_item_id.getValue() }
		];
    	//加载查询条件
		grid.loadData(parms, '');
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '预算年度', name: 'year', align: 'left',width:'15%',editable:setEdit ,
                	 editor: {
			 			 keyField:'year',
	              	     type: 'select',  //编辑框为下拉框时
	              	     url: '../../queryBudgYear.do?isCheck=false',      //  动态数据接口
                	 }
			   },
               { display: '月份', name: 'month', align: 'left',width:'10%',editable:setEdit ,
			 		 editor: {
			 			 keyField:'month',
	              	     type: 'select',  //编辑框为下拉框时
			 			 url : 'queryBudgMonth.do?isCheck=false',
                	 }
			   },
               { display: '现金流量项目', name: 'cash_item_name',width:'30%', align: 'left',editable:setEdit ,
			 		 editor: {
			 			 keyField:'cash_item_id',
	              	     type: 'select',  //编辑框为下拉框时
					 	 url : '../../queryCashItem.do?isCheck=false',
                	 },
			   },
               { display: '金额(E)', name: 'amount',minWidth:180, align: 'right',
					 render:function(ui) {
                	 	var value = ui.cellData;
						if(value){
								return formatNumber(value,2,1);
						}else{
								return formatNumber(0,2,1);
						}
					 }
			   }
          ],
          dataModel:{
	          method:'POST',
	          location:'remote',
	          url:'queryBudgCashFlowExe.do?isCheck=false',
	          recIndx: 'year'
          },
          usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
		  changeRow:function (evt,obj) {
		  	  onChangeRowSeve(obj);
		  },
    	  toolbar: {
	          items: [
		          { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		          { type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
				  { type: "button", label: '确认',icon:'contact',listeners: [{ click: count}] },
				  { type: "button", label: '添加行',icon:'plus',listeners: [{ click: addRow}] }
	          ]
		  },
      });
    }
    
    //添加可编辑行
    function addRow(){
    	grid.addRow();
    }
    
	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
    	
    function onChangeRowSeve(obj){

		var rowdata = grid.getRowData(obj.prevR.rowIndxPage);
    	if(!rowdata.year){
    		setTimeout(function () {
    			$.etDialog.error("年度不能为空！！！")
    		}, 10)
    		return;
    	}
    	if(!rowdata.month){
    		setTimeout(function () {
    			$.etDialog.error("月份不能为空！！！")
    		}, 10)
    		return;
    	}
    	if(!rowdata.cash_item_name){
    		setTimeout(function () {
    			$.etDialog.error("现金流量项目不能为空！！！")
    		}, 10)
    		return;
    	}
    	if(!rowdata.amount){
    		setTimeout(function () {
	    		$.etDialog.error("金额不能为空！！！")
	    	}, 10)
    		return;
    	}
		 
		var dataAll = grid.getAllData();
     	for(var j=0;j<dataAll.length;j++){
    	 	if(dataAll[j].cash_item_name!==undefined){
    		 	if(rowdata._rowIndx!=j){
    			 	if(rowdata.cash_item_name==dataAll[j].cash_item_name){
    				 	 setTimeout(function(){
    						$.etDialog.error("第"+(rowdata._rowIndx+1)+"行数据填写的现金流量项目为【"+rowdata.cash_item_name+"】，该项目已存在，请修改数据后再保存");
    					 },10) 
    					 return false;
    		    	} 
    		 	}
    	 	} 
     	}
     
    	var paramData={
   			year : rowdata.year ,
   			month : rowdata.month ,
   			cash_item_id : rowdata.cash_item_id?rowdata.cash_item_id:rowdata.cash_item_name  ,
   			cash_item_name : rowdata.text?rowdata.text.split(" ")[1]: rowdata.cash_item_name,
   			amount : rowdata.amount
    	};
   		ajaxJsonObjectByUrl("addOrUpdateBudgCashFlowExe.do?isCheck=false",paramData, function(responseData){
	   		query();
    	});
    }
    
    //删除
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];
            var delData = [] ;//接受页面端删除数据
            $(data).each(function (){
            	if(this.rowData.group_id){
					ParamVo.push(
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.year   +"@"+ 
						this.rowData.month  +"@"+
						(this.rowData.cash_item_id?this.rowData.cash_item_id:this.rowData.cash_item_name)
					) 
            	}else{
	              	delData.push(data)
              	}
			});
     		if(ParamVo.length > 0){
     			$.etDialog.confirm('确定删除?', function() {
                    ajaxPostData({
                        url: "deleteBudgCashFlowExe.do?isCheck=false",
                        data: { ParamVo: ParamVo.toString() },
                        success: function() {
                            query();
                        }
                    });
                });
     		}else{
     			grid.deleteRows(delData);
             	$.etDialog.success("删除成功!");
       		}
        }
    }
    
    //2 对现金流量执行数据表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量执行表  budg_cash_exe
    function count(){
    	var param = {
    		year : year_input.getValue()
    	}
    	ajaxPostData({
            url: "collectBudgCashFlowExe.do?isCheck=false",
            data: param,
            success: function() {
                query();
            }
        });
    }
    
  	//导入
	function impNew(){
		parent.$.ligerDialog.open({ url : 'hrp/budg/budgcash/budgcashflowexe/importBudgCashFlowExePage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'现金流量执行数据导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
  		}); 
	}	
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">月份： </td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
				<td class="label">方向：</td>
				<td class="ipt">
					<select name="cash_dire" id="cash_dire" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label" style="width:120px;" >现金流量类别：</td>
				<td class="ipt">
					<select name="cash_type_id" id="cash_type_id" style="width:180px;"></select>
				</td>
				<td class="label" style="width:120px;" >现金流量项目： </td>
				<td class="ipt">
					<select name="cash_item_id" id="cash_item_id" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="ipt"></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
