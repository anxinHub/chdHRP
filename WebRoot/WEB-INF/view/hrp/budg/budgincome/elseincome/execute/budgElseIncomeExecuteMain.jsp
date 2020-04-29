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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var userUpdateStr;
    var year ;
    $(function (){
    	//加载数据
    	loadHead(null);
		loadHotkeys();
		init();
    });
    
	var year_input, month_input, subj_code_select;

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
	}
	function init(){
		getData("../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				//minDate: data[data.length - 1].text,
				//maxDate: data[0].text,
				todayButton: false,
				onSelect: function (value) {
					reloadSubjCode(value);
					setTimeout(function () {
						queryNew();
					}, 10);
				}
			});
			reloadSubjCode(data[0].text);
		});


		month_input= $("#month_input").etDatepicker({
			view: "months",
			minView: "months",
			dateFormat:"mm",
			todayButton: false,
			showNav:false,
			onChanged:query
		});

		subj_code_select = $("#subj_code_select").etSelect({
			defaultValue:"none",
			onChange:query
		});
		function reloadSubjCode(year){
			subj_code_select.reload({
				url:"../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:"04",
					is_last:"1",
					budg_year:year
				}
			})
		}
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
	}
    
    //查询
    function  query(){
    	if(!year_input.getValue()){
    		$.etDialog.error('年度不能为空');
    		return false ;
    	}
    	var parms=[
			{name:'year',value:year_input.getValue()},
			{name:'month',value:month_input.getValue()},
			{name:'subj_code',value:subj_code_select.getValue()}
		];
    	//加载查询条件
    	grid.loadData(parms,'queryBudgElseIncomeExecute.do?isCheck=false');
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '年度', name: 'year', align: 'center',width:'10%',editable:setEdit ,
	               	 editor: {
	               		valueField:'id',
						textField:'text',
		               	 type: 'select',  //编辑框为下拉框时
		               	 //source:[],   //  静态数据接口  也可以是回调函数返回值
		               	 url: '../../../queryBudgYear.do?isCheck=false',      //  动态数据接口
		               	 change:function(rowdata,celldata){
	                		 grid.updateRow(celldata.rowIndx,{subj_code:"",subj_name:""})
	                	 }
	               	 },
               	 
			   },
               { display: '月份', name: 'month', align: 'center',width:'5%',editable:setEdit ,
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
                	 }
			   },
               { display: '科目编码', name: 'subj_code', align: 'left',width:'22%',editable:false ,
			   },
               { display: '科目名称', name: 'subj_name', align: 'left',width:'30%',editable:setEdit ,
			   		editor: {
						valueField:'code',
						textField:'name',
                	    type: 'select',  //编辑框为下拉框时
                	    //source:[],   //  静态数据接口  也可以是回调函数返回值
                	    url: 'queryBudgIncomeSubj.do?isCheck=false&is_last=1&budg_year='+year,      //  动态数据接口
                	    change:function(rowdata,celldata){
                	    	grid.updateRow(celldata.rowIndx,{subj_code:rowdata.code})
                	    },
                	    //与年度联动查询
                	    create:function(rowdata,celldata,setting){
                	    	if(rowdata.year){
                	    		setting.url = 'queryBudgIncomeSubj.do?isCheck=false&is_last=1&budg_year='+rowdata.year;
                	    	}else{
                	    		 $.etDialog.error('请先填写年度');
                	    		 return false ;
                	    	 }
                	     }
                	 }
			   },
               { display: '金额', name: 'amount', align: 'right',dataType:'float',width:"10%",
				    editor:{
					    type: 'textbox',  //文本框
				    },
				    render:function(ui){
				   		return formatNumber(ui.rowData.amount,2,1)
				    }
			   },
		       { display: '说明', name: 'remark', align: 'left',dataType:'string',minWidth:"20%",
					editor:{
						  type: 'textbox', // 文本框
					}
			   }
           ],
           dataModel:{
	           method:'POST',
	           location:'remote',
	           url:'',
	           recIndx: 'year'
           },
           usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
           addRowByKey:true,
		   toolbar: {
		       items: [
			       { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
				   { type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
				   { type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
				   { type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
				   { type: "button", label: '财务取数',icon:'search',listeners: [{ click: getDatafromAcc}] },
				   { type: 'button', label: '下载导入模板（<u>B</u>）', id: 'downTemplate', listeners: [{ click: downTemplate }], icon: 'import' },
				   { type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] }
		       ]
		   },
       });
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
  //财务取数
    function getDatafromAcc(){
    	$.etDialog.open({
            title: '财务取数',
            height: 388,
            width: 600,
            btn: ["确定", "取消"],
            btn1: function (index, el) {
                var frameWindow = window[el.find('iframe')[0].name];
                frameWindow.getDatafromAcc(); //子页函数
            },
            btn2: function (index) {
                $.etDialog.close(index); // 关闭弹窗
                return false;
            },
            url: "../../compilationbasic/elseincomeexe/getDatafromAccPage.do?isCheck=false"
        });
 	   	/* var budg_year = year.getValue();
 	   	var acc_month = month.getValue();
 	   	if(!budg_year || !acc_month){
 	   		$.etDialog.error("预算年度和月份必填,不可为空");
 	   		return
 	   	}
 	   	ajaxPostData({
		    url: "getDatafromAcc.do?isCheck=false&budg_year="+budg_year+"&acc_month="+acc_month,
		    data: '',
		    success: function (responseData) {
		    	queryNew();
		    },
		}) */
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
    					this.id   +"@"+ 
    					this.monthID   +"@"+ 
    					this.subj_code +"@"+ 
    					this.amount +"@"+ 
    					(this.remark?this.remark:"") +"@"+
    					this._rowIndx +"@"+ 
    					'1' //添加数据标识
    				) 
    			});
        	}
    		if(data.updateList.length > 0){
        		
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
    					this.year   +"@"+ 
    					this.month   +"@"+ 
    					this.subj_code  +"@"+
    					this.amount +"@"+ 
    					(this.remark?this.remark:"") +"@"+
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
                url: "saveBudgElseIncomeExecute.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	if(responseData.state=="true"){
    					$.etDialog.success('保存成功');
    					query();
               		}else{
               			$.etDialog.error(responseData.message)
               		}
                }
            });
    	}else{
    		$.etDialog.warn('没有需要保存的数据!');
    	}
    }
    
    function remove(){
    	
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];//后台删除数据
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
           				this.rowData.group_id   +"@"+ 
           				this.rowData.hos_id   +"@"+ 
           				this.rowData.copy_code   +"@"+ 
           				this.rowData.year   +"@"+ 
           				this.rowData.month   +"@"+ 
           				this.rowData.subj_code 
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
			});
            if(ParamVo.length > 0){
            	$.etDialog.confirm('确定删除?', function() {
                    ajaxPostData({
                        url: "deleteBudgElseIncomeExecute.do?isCheck=false",
                        data: { ParamVo: ParamVo.toString() },
                        success: function(responseData) {
                            query();
                        }
                    });
                });
            }else if(deletePageRow.length > 0 ){
            	grid.deleteRows(deletePageRow);
            	$.etDialog.success("删除成功!");
            }
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
			content : '../../compilationbasic/elseincomeexe/budgElseIncomeExePreImportPage.do?isCheck=false'
		});
		layer.full(index);
    }	
    function downTemplate(){
    	location.href = "../../compilationbasic/elseincomeexe/downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"year="+vo[3]   +"&"+ 
			"month="+vo[4]   +"&"+ 
			"subj_code="+vo[5] 
		 $.ligerDialog.open({ url : 'budgElseIncomeExecuteUpdatePage.do?isCheck=false&'+parm,data:{}, height: 300,width: 750, title:'其他收入预算执行修改', 
			modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgElseIncomeExecute(); },cls:'l-dialog-btn-highlight' },
		        { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
		    ]
    	}); 
    }
    
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.year) {
				rowm+="[年度]、";
			}
			if (!v.month) {
				rowm+="[月份]、";
			}
			if (!v.subj_name) {
				rowm+="[科目名称]、";
			}
			if (!v.amount) {
				rowm+="[金额]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.month + v.subj_code 
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
		hotkeys('A', add_Row);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('I', imp);
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
				<td class="label">月份：</td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
				<td class="label">预算科目：</td>
				<td class="ipt">
					<select name="" id="subj_code_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>
	
</body>
</html>
