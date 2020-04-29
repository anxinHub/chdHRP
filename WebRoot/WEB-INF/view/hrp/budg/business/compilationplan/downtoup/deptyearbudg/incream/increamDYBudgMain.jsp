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
    var year_input;
    var index_code_select;
    var dept_id_select;
    var budg_year ;
    var index_code;
    var dept_id;
    var rowidex ;
	//打印 单元格格式化 用
    var renderFunc = {
    		last_year_workload : function(value){ //上年业务量
				return formatNumber(value, 2, 1);
			},	 
			
			grow_rate : function(value){ //增长比例
				return formatNumber(value, 2, 1);
			},
			grow_value : function(value){ //增长额
				return formatNumber(value, 2, 1) + "%";
			},
			count_value : function(value){ //计算值
				return formatNumber(value, 2, 1);
			},
			budg_value : function(value){ //预算值
				return formatNumber(value, 2, 1);
			},
			hos_suggest_resolve : function(value){ //医院意见分解
				return formatNumber(value, 2, 1);
			},
	};
    $(function (){
    	//加载数据
    	loadHead();
		loadHotkeys();
		init();
    });
    
    function init(){
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function (value) {
            	index_code_select.setValue('');
            	reloadSubjName(value);
            },
            defaultDate: true
        });


		index_code_select = $("#index_code_select").etSelect({
			defaultValue: "none",
			onChange: function(value){
				index_code = value ;
				dept_id_select.setValue('');
				reloadDeptName(value);
			}
		});
		reloadSubjName(year_input.getValue());
		function reloadSubjName(value){
			index_code_select.reload({
				url:"../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=03&edit_method=02",
				para:{
					budg_year:value
				}
			})
		};
		
		dept_id_select = $("#dept_id_select").etSelect({
			defaultValue: "none",
			onChange: query
		});
		reloadDeptName(index_code_select.getValue());
		function reloadDeptName(value){
			dept_id_select.reload({
				url:"../../../../../queryBudgIndexDeptSet.do?isCheck=false",
				para:{
					index_code : value
				}
			})
		};
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
		})
	};
    
    //查询
    function  query(){
    	if(!year_input.getValue()){
    		$.etDialog.error("预算年度不能为空");
    	}
    	var params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
			{ name: 'budg_level', value: "03" },
			{ name: 'edit_method', value: "02" },
		]
		//加载查询条件
		grid.loadData(params,"queryIncreamDYBudgDown.do?isCheck=false&budg_level=03&edit_method=02");
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [
        	     { display: '预算年度', name:'year', align: 'center',width:90,editable:setEdit ,
			        	   editor : {
								type : 'select',
								keyField : 'year',
								url : '../../../../../queryBudgYearTen.do?isCheck=false',
								change:function(rowdata,celldata){
			               		 	grid.updateRow(celldata.rowIndx,{index_code:"",index_name:""})
			               	 	}
			       	 		}
				 		},
                 { display: '指标编码', name: 'index_code', align: 'left',editable:false ,width:120,
				 		},
			 	 { display: '指标名称', name: 'index_name', align: 'left',editable:setEdit ,width:150,
				 			
				 			editor : {
								type : 'select',
								keyField : 'index_code',
								url : 'queryBudgIndex.do?isCheck=false&budg_level=03&edit_method=02',
								change :function(rowdata,celldata){
				 					grid.updateRow(celldata.rowIndx,{'index_code':rowdata.index_code});
				 					queryLastYearWorkload(rowdata,celldata);
				 				},
							    //与年度联动查询
	                	        create:function(rowdata,celldata,setting){
	                	    	 	if(rowdata.year){
	                	    		 	setting.url = 'queryBudgIndex.do?isCheck=false&budg_level=03&edit_method=02&budg_year='+rowdata.year;
	                	    	 	}else{
	                	    		 	$.etDialog.error('请先填写年度');
	                	    		 return false ;
	                	    	 	}
	                	     	}
							} 
				 		},
				 { display: '科室编码', name: 'dept_code', align: 'left',width:80,editable:false,
				 		},
				 { display: '科室名称', name: 'dept_name', align: 'left',width:150,editable:setEdit,
				 			editor:{
				 				type:'select' ,
				 				keyField:'dept_id',
				 				url:'../../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code=',
				 				change : function(rowdata,celldata){
				 					setTimeout(function(){
				 						grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]});
				 						queryLastYearWorkload(rowdata,celldata);
				 					},200)
				 				},
				 				//与指标联动查询
		                	    create:function(rowdata,celldata,setting){
		                	    	 if(rowdata.index_code){
		                	    		 setting.url = '../../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code='+rowdata.index_code;
		                	    	 }else{
		                	    		 $.etDialog.error('请先填写指标名称');
		                	    		 return false ;
		                	    	 }
		                	    }
				 			}
				 		},
	 		 	  { display: '上年业务量', name: 'last_year_workload', align: 'right',width:120,
				 			render:function(ui){
				 				if(ui.rowData.last_year_workload){
				 					return formatNumber(ui.rowData.last_year_workload,2,1);
				 				}
				 			}
				 		},
				  {display: '增长比例(E)', name: 'grow_rate', align: 'center', dataType:'float',width:120,editable:setRateEdit,
							editor: {change: setCountValueAfterRate },
							render:function(ui){
								return formatNumber(ui.rowData.grow_rate, 2, 1) + "%";
							}
						},
				  {display: '增长额(E)', name: 'grow_value', align: 'right',dataType:'float',width:120, editable:setValueEdit,
							editor: { change: setCountValueAfterValue },
							render:function(ui){
								return formatNumber(ui.rowData.grow_value, 2, 1) ;
							}
						},
				  {display: '计算值', name: 'count_value', align: 'right',width:120,editable:false,
							render:function(ui){
								if (ui.rowData.count_value) {
									return formatNumber(ui.rowData.count_value, 2, 1);
								}
							}
						},
				  {display: '预算值(E)', name: 'budg_value', align: 'right', width:120,dataType:'float',
							render:function(ui){
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}
						},
				  {display: '说明(E)', name: 'remark', align: 'left', dataType: 'string',minWidth:200
				 		}
             ],
             dataModel:{
	           	 method:'POST',
	           	 location:'remote',
	           	 url:'',
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
				{ type: "button", label: '增长比例获取',icon:'info',listeners: [{ click: getGrowRate}] },
				{ type: "button",label:'下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
          	 ]}
         });
    }
  //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室年度业务预算-增量预算";
    }
 // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
	
 	// 根据 get_way 04:历史数据*增长比例   05:历史数据+增长额  返回 true 或 false  控制 增长比例 单元格可否编辑 用 
    function setRateEdit(ui){
   		 if(ui.rowData && ui.rowData.get_way == '04'){
   			 return true ;
   		 }else{
   			 return false ;
   		 }
    }
 	
    // 根据 get_way 04:历史数据*增长比例   05:历史数据+增长额  返回 true 或 false  控制 增长额 单元格可否编辑 用 
    function setValueEdit(ui){
   		 if(ui.rowData && ui.rowData.get_way == '05'){
   			 return true ;
   		 }else{
   			 return false ;
   		 }
    }
    
  //添加行
    function add_Row(){
    	grid.addRow() ;
    }
    
    //选择 指标后 查询其上年业务量 并更新上年业务量单元格
    function queryLastYearWorkload(rowdata,celldata){
  	   	if(rowdata.index_code && rowdata.year && rowdata.dept_id){
  	   		$.post("queryDeptYearLastYearWork.do?isCheck=false&budg_year="+rowdata.year+"&index_code="
  	   				+rowdata.index_code+"&dept_id="+rowdata.dept_id+"&dept_code="+rowdata.dept_code,null,function(responseData){
              	var para = eval("("+responseData+")") ;
              	if(!para.get_way){
              		$.etDialog.warn('所选指标的取值方法没有维护');
              		grid.updateRow(celldata.rowIndx,{'get_way':""});
              		return false ;
              	} ;
              	
				if(!para.last_year_workload){
              		$.etDialog.warn('所选指标的上年业务量未维护,请至【科室年度历史指标数据采集】处维护数据后操作!');
              		grid.updateRow(celldata.rowIndx,{'last_year_workload':""});
              		return false ;
              	} ;
              	grid.updateRow(celldata.rowIndx,{'last_year_workload':para.last_year_workload,'get_way':para.get_way});
  	   		});
  	   	}
    }
  
  //填写、修改 增长比例后  更新 计算值单元格
    function setCountValueAfterRate(rowdata,celldata){
   	 
   	 if(rowdata.grow_rate){
   		 if(rowdata.last_year_workload){
	   		 var count_value = Number(rowdata.last_year_workload)*(1+Number(rowdata.grow_rate)/100) ;
	   		 
	   		 grid.updateRow(celldata.rowIndx,{'count_value':count_value,'budg_value':count_value});
	   		 
   		 }else{
			setTimeout(function(){
   				$.etDialog.error('指标:【'+rowdata.index_code +' '+ rowdata.index_name +'】上年业务量未维护,请至【医院年度历史指标数据采集】处维护数据后操作!');
   			},10)
   		 } 
   	 }else{
   		 $.etDialog.error('增长比例不能为空,且必须为数字');
   	 }
    }
    
	//填写、修改 增长额后  更新 计算值单元格
    function setCountValueAfterValue(rowdata,celldata){
   	  if(rowdata.grow_value){
   		if(rowdata.last_year_workload){	 
   		    var count_value = Number(rowdata.last_year_workload) + Number(rowdata.grow_value) ;
   		 	grid.updateRow(celldata.rowIndx,{'count_value':count_value,'budg_value':count_value});
   		}else{
   			setTimeout(function(){
   				$.etDialog.error('指标:【'+rowdata.index_code +' '+ rowdata.index_name +'】上年业务量未维护,请至【医院年度历史指标数据采集】处维护数据后操作!');
   			},10)
  		 }
      }else{
    	 $.etDialog.error('增长额不能为空,且必须为数字');
      }
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
    					this.index_code +"@"+ 
    					this.dept_id  +"@"+
    					this.last_year_workload +"@"+
    					(this.grow_rate? this.grow_rate:"") +"@"+ 
    					(this.grow_value? this.grow_value:"") +"@"+ 
    					(this.count_value? this.count_value:"") +"@"+ 
    					this.budg_value +"@"+ 
    					(this.remark?this.remark:"")   	+"@"+ 
    					this._rowIndx +"@"+ 
    					'1' //添加数据标识
    				) 
    			});
        	}
        	
    		if(data.updateList.length > 0){
        		var updateData = data.updateList ;
        		if(!validateGrid(updateData)){
        			return  false ;
        		}
                $(updateData).each(function (){	
                	ParamVo.push(
            			this.year   +"@"+ 
    					this.index_code +"@"+ 
    					this.dept_id  +"@"+
    					this.last_year_workload +"@"+
    					(this.grow_rate? this.grow_rate:"") +"@"+ 
    					(this.grow_value? this.grow_value:"") +"@"+ 
    					(this.count_value? this.count_value:"0") +"@"+ 
    					this.budg_value +"@"+ 
    					(this.remark?this.remark:"")   	+"@"+ 
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
                url: "saveIncreamDYBudgDown.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
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
			if (!v.dept_id) {
				rowm+="[科室名称]、";
			}
			if (!v.last_year_workload) {
				rowm+="[上年业务量]、";
			}
			if (!v.budg_value) {
				rowm+="[预算值]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.month + v.index_code + v.dept_id
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
            var deleteDate = [];
            $(data).each(function (){
            	if(!this.rowData.group_id){
            		deleteDate.push(this);
            	}else{
            		ParamVo.push(
          				this.rowData.group_id   +"@"+ 
          				this.rowData.hos_id   +"@"+ 
          				this.rowData.copy_code   +"@"+ 
          				this.rowData.year   +"@"+ 
          				this.rowData.dept_id   +"@"+
          				this.rowData.index_code 
       				) 
            	}
			});
	        $.etDialog.confirm('确定删除?', function (yes){
	         	if(yes){
	         		if(ParamVo.length > 0){
	         			ajaxPostData({
	         			    url: 'deleteIncreamDYBudgDown.do?isCheck=false',
	         			    data: {ParamVo : ParamVo.toString()},
	         			    success: function (responseData) {
	         			    	query();
	         			    }
	         			 })
	         		}else if(deleteDate.length > 0 ){
    	            	grid.deleteRows(deleteDate);
    	            	$.etDialog.success("删除成功!");
    	            }
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
			content : 'increamDYBudgImportPage.do?isCheck=false'
		});
		layer.full(index);
    }	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = "group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&year="+vo[3] +"@index_code="+vo[5] 
		 
		 $.etDialog.open({ url : 'increamDYBudgUpdatePage.do?isCheck=false&' + parm ,data:{}, height: 300,width: 450, 
			title:'医院年度业务预算增量预算修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgWorkHosYear(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
    
	//增量生成
    function generate(){
 	   	var year = year_input.getValue();
 	   	if(year){
	 	   	ajaxPostData({
	            url: "generate.do?isCheck=false&year="+year,
	            data: {},
	            success: function(responseData) {
	            	query();
	            }
	        });
 	   	}else{
 	   		$.etDialog.error("预算年度不能为空");
 	   	}
    }
	
	//增长比例获取
    function getGrowRate(){
 	   var year = year_input.getValue();
 	   $.etDialog.open({ 
 		   	url : 'getGrowRatePage.do?isCheck=false&year='+year,
 		   height: 500,width: 900,title:'增长比例获取'
   	   }); 
    }
    //键盘事件
	  function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('G', generate);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
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
				<td class="label">预算年度：</td>
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
