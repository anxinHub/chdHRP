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
<jsp:param value="select,datepicker,ligerUI,grid,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var budg_year ;
    var rowidex ;
    var year_input,index_code_select;
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
			dept_suggest_sum : function(value){ //科室意见汇总
				return formatNumber(value, 2, 1);
			},
	};
    
    $(function ()
    {
    	loadHead(null);
    	//加载数据
		loadHotkeys();
		init();
		/* budg_year = year_input.getValue();
		$("#year").change(function(){
			 budg_year = year_input.getValue();
	            
	        //预算指标下拉框budg_level （01医院年度 02医院月份 03科室年度 04科室月份 ）编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 
	        autocomplete("#index_code","../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=01&edit_method=02&budg_year="+budg_year,"id","text",true,true);
			query();
        });
         
		$("#index_code").change(function(){
			query();
        });
		
		 */
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
			url: "../../../../../queryBudgIndexDict.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		});
		function reloadSubjName(value){
			index_code_select.reload({
				url:"../../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=01&edit_method=02",
				para:{
					budg_year:value
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
		})
	};
    
    //查询
    function  query(){
    	if(!year_input.getValue()){
    		$.etDialog.error("预算年度不能为空");
    	}
    	var parms=[];
        //根据表字段进行添加查询条件
    	parms.push({name:'year',value:year_input.getValue()}); 
    	parms.push({name:'index_code',value:index_code_select.getValue()}); 
    	/* parms.push({name:'budg_level',value:"01"});
  		parms.push({name:'edit_method',value:"02"}); */

    	//加载查询条件
    	grid.loadData(parms,"queryIncreamHYBudgUp.do?isCheck=false&budg_level=01&edit_method=02");
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '预算年度', name: 'year', align: 'center',editable:setEdit ,width:80,
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
							url : 'queryBudgIndex.do?isCheck=false&budg_level=01&edit_method=02',
							change : queryLastYearWorkload ,
							//与年度联动查询
	             	     	create:function(rowdata,celldata,setting){
		             	    	 if(rowdata.year){
		             	    		 setting.url = 'queryBudgIndex.do?isCheck=false&budg_level=01&edit_method=02&budg_year='+rowdata.year;
		             	    	 }else{
		             	    		 $.etDialog.error('请先填写年度');
		             	    		 return false ;
		             	    	 }
	             	     	}
						} 
					},
				 { display: '上年业务量', name: 'last_year_workload', align: 'right',width:120,editable:true,
						editor: {change: setCountValueAfterWorkload },
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
				{display: '说明(E)', name: 'remark', align: 'left', dataType: 'string',width:200}
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
   	           		]
           	   }
        });
    }
    
  //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="医院年度业务预算-增量预算";
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
    	
    	grid.updateRow(celldata.rowIndx,{"subj_code":rowdata.subj_code});
    	
  	   	if(rowdata.index_code && rowdata.year){
  	   		$.post("queryLastYearWork.do?isCheck=false&budg_year="+rowdata.year+"&index_code="
  	   				+rowdata.index_code,null,function(responseData){
  	      			
              	var para = eval("("+responseData+")") ;
              	
              	if(!para.get_way){
              		
              		$.etDialog.warn('所选指标的取值方法没有维护');
              		
              		return false ;
              	} ;
              	grid.updateRow(celldata.rowIndx,{'get_way':para.get_way});
				if(!para.last_year_workload){
              		
              		$.etDialog.warn('所选指标的上年业务量未维护,请至【医院年度历史指标数据采集】处维护数据后操作!');
              		
              		return false ;
              	} ;
              	grid.updateRow(celldata.rowIndx,{'last_year_workload':para.last_year_workload});
              		
  	   		});
  	   	}else{
  	   		$.etDialog.error('指标编码不能为空');
  	   	}
    }
     
  //填写、修改 业务量后  更新 计算值单元格
    function setCountValueAfterWorkload(rowdata,celldata){
	   	if(rowdata.last_year_workload){
	   		if(rowdata.get_way == '04' && rowdata.grow_rate){
		   		var count_value = Number(rowdata.last_year_workload)*(1+Number(rowdata.grow_rate)/100) ;
		   		grid.updateRow(celldata.rowIndx,{'count_value':count_value,'budg_value':count_value});
		   		 
	   		}else if(rowdata.get_way == '05' && rowdata.grow_value){
		   		var count_value = Number(rowdata.last_year_workload) + Number(rowdata.grow_value) ;
		   		grid.updateRow(celldata.rowIndx,{'count_value':count_value,'budg_value':count_value});
	   		}
	   	}else{
	   		$.etDialog.error('指标:【'+rowdata.index_code +' '+ rowdata.index_name +'】上年业务量未维护,请至【医院年度历史指标数据采集】处维护数据后操作!');
	   		
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
  
    function add_open(){
		$.etDialog.open({ 
			url : 'increamHYBudgAddPage.do?isCheck=false',
			height: 500,width: 800, 
			title:'医院年度业务预算-增量预算添加',
    	}); 
    }
 
	//  保存
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
                $(updateData).each(function (){	
                	ParamVo.push(
            			this.year   +"@"+ 
    					this.index_code +"@"+ 
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
                url: "saveIncreamHYBudgUp.do?isCheck=false",
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
			var key=v.year + v.month + v.index_code 
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
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			  var ParamVo =[];//后台删除数据
	          var deletePageRow = [];// 页面删除数据
	          $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
           				this.rowData.group_id   +"@"+ 
           				this.rowData.hos_id   +"@"+ 
           				this.rowData.copy_code   +"@"+ 
           				this.rowData.year   +"@"+ 
           				this.rowData.index_code 
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
	        })
            $.etDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		if(ParamVo.length > 0){
            			ajaxPostData({
                            url: "deleteIncreamHYBudgUp.do?isCheck=false",
                            data: { ParamVo: ParamVo.toString() },
                            success: function(responseData) {
                            	query();
                            }
                        });
            		}else if(deletePageRow.length > 0 ){
    	            	grid.deleteRows(deletePageRow);
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
			content : 'increamHYBudgImportPage.do?isCheck=false'
		});
		layer.full(index);
    }
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = "group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&year="+vo[3] +"@index_code="+vo[5] 
		
		$.etDialog.open({ 
	 		url : 'increamHYBudgUpdatePage.do?isCheck=false&' + parm ,
			height: 300,width: 450, 
			title:'医院年度业务预算-增量预算修改',
			btn: ['确定', '取消'],
	        btn1: function(index, el) {
	            var iframeWindow = window[el.find('iframe').get(0).name];
	            iframeWindow.saveBudgWorkHosYear()
	        }
    	}); 
    }
    
    //增量生成
    function generate(){
    	var year = year_input.getValue();
    	if(year){
    		ajaxPostData({
                url: "generate.do?isCheck=false&year="+year,
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
 		   	height: 500,width: 900, title:'增长比例获取',
 		   	//isMax:true
   	   }); 
    }
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('G', generate);
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
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
