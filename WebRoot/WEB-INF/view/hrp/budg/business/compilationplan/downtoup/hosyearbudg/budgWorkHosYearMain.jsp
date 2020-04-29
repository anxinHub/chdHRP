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
	<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var year_input;
    var index_code_select;
    $(function (){
    	loadHead(null);
		loadHotkeys();
        init();
    });
    
    function init(){
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
			defaultValue: "none",
			url:'../../../../queryBudgIndexDict.do?isCheck=false',
			onChange: query,
		});
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
			})
		};
    } 
    //查询
    function  query(){
    	var params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'budg_level', value: "01" },
		]
		//加载查询条件
		grid.loadData(params,"");
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
	              { display: '预算年度', name: 'year', align: 'center',width:'10%',editable:setEdit ,
				     	editor : {
							type : 'select',
							keyField : 'year',
							url : '../../../../queryBudgYearTen.do?isCheck=false',
				    	}
					 },
	              { display: '指标编码', name: 'index_code', align: 'left',editable:false ,width:'15%',
					 },
				  { display: '指标名称', name: 'index_name', align: 'left',width:'15%',
						editor : {
							type : 'select',
							keyField : 'index_code',
							url : '../../../../queryBudgIndexDict.do?isCheck=false',
							change:reloadIndexCode,
					    } 
					},
	              { display: '预算值(E)', name: 'budg_value', align: 'right', width:'15%',dataType:'float',
						render:function(ui){
							return formatNumber(ui.rowData.budg_value, 2, 1);
						}
					},
	              { display: '说明(E)', name: 'remark', align: 'left', dataType: 'string',minWidth:'25%'
					}
          	   ],
               dataModel:{
  	           	 method:'POST',
  	           	 location:'remote',
  	           	 url:'queryBudgWorkHosYearDown.do?isCheck=false&budg_leve=01',
  	           	 recIndx: 'year'
 	           }, 
 	           usePager:false,width: '100%', height: '100%',checkbox: true,editable: true,
 	           addRowByKey:true,
 	           toolbar: {
	               items: [
				       { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
				       { type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] },
					   { type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
					   { type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					   { type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					   { type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
					   { type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
					   { type: "button", label: '汇总',icon:'plus',listeners: [{ click: collect}] },
	               ]
 	           }
         });
    }
    
    //选择指标后 更新指标编码 
    function reloadIndexCode(rowdata,celldata){
    	setTimeout(function () {
    		grid.updateRow(celldata.rowIndx,{'index_code':rowdata.index_name.split(" ")[0]})
    	}, 10);
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
    
    function add_open(){
		$.etDialog.open({ 
			url : 'budgWorkHosYearAddPage.do?isCheck=false',
			data:{}, 
			height: 500,
			width: 800, 
			title:'医院年度业务预算添加'
    	}); 
    }
    
  //保存
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
               			this.year   +"@"+ 
       					this.index_code  +"@"+ 
       					(this.budg_value? this.budg_value:"")  +"@"+ 
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
               			this.year   +"@"+ 
       					this.index_code  +"@"+ 
       					(this.budg_value? this.budg_value:"")  +"@"+ 
       					(this.remark?this.remark:"")   	+"@"+ 
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
 			    url: 'saveBudgWorkHosYearDown.do?isCheck=false',
 			    data: {ParamVo : ParamVo.toString()},
 			    success: function (responseData) {
 			    	query();
 			    }
 			})
		}else{
			$.etDialog.error('没有需要保存的数据');
		}
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
			if (!v.index_code) {
				rowm+="[指标名称]、";
			}
			if (!v.budg_value) {
				rowm+="[预算值]、";
			}
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.month + v.index_code;
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
  
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];
            var deletePageRow = [];
            $(data).each(function (){
            	if(!this.rowData.group_id){
            		deletePageRow.push(this);
            	}else{					
					ParamVo.push(
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.year   +"@"+ 
						this.rowData.index_code 
					) 
				}
			});
            $.etDialog.confirm('确定删除?', function (yes){
         		if(ParamVo.length > 0){
         			ajaxPostData({
         			    url: 'deleteBudgWorkHosYearDown.do?isCheck=false',
         			    data: {ParamVo : ParamVo.toString()},
         			    success: function (responseData) {
         			    	query();
         			    }
         			})
         		}else if(deletePageRow.length > 0 ){
   	            	grid.deleteRows(deletePageRow);
   	            	$.etDialog.success("删除成功!");
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
			content : 'budgWorkHosYearImportPage.do?isCheck=false'
		});
		layer.full(index);
   	}	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&year="+vo[3] +"@index_code="+vo[5] 
		$.etDialog.open({ url : 'budgWorkHosYearUpdatePage.do?isCheck=false&' + parm ,data:{}, height: 300,width: 450, title:'医院年度医疗收入独立核算科目预算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgWorkHosYear(); },cls:'l-dialog-btn-highlight' },
	            { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
	        ]
    	}); 
    }
    
    function printDate(){
    	if(grid.getData().length==0){
			$.etDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name:'year',value:liger.get("year").getValue()}); 
		grid.options.parms.push({name:'index_code',value:liger.get("index_code").getValue()});
		grid.options.parms.push({name:'budg_level',value:"01"});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"医院年度业务预算",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgWorkHosYearDown.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    }
    
    //汇总
    function collect(){
    	var year = year_input.getValue();
		   
		if(!year){
			$.etDialog.error('预算年度不能为空');
			return false ;
		}
		ajaxPostData({
		    url:"collectBudgWorkHosYearDown.do?isCheck=false&year="+year,
		    data: {},
		    success: function (responseData) {
		    	query();
		    }
		})
    }
    
    //增量生成
    function generate(){
 	   	var year = year_input.getValue();
 	   	if(year){
	 	   	ajaxPostData({
			    url:"generate.do?isCheck=false&year="+year,
			    data: {},
			    success: function (responseData) {
			    	query();
			    }
			})
 	   	}else{
 	   		$.etDialog.error("预算年度不能为空");
 	   	}
    }
  
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('G', generate);
		hotkeys('A', add_open);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('P', printDate);
		hotkeys('I', imp);
		hotkeys('C', collect);
	}
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">预算指标：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
