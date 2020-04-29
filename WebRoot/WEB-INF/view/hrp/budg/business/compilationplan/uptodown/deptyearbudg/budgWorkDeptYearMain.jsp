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
    var budg_year ;
    var index_code ;
    var year_input;
    var index_code_select;
    var dept_id_select,state;
    var flag = 0 ;//年度下拉框改变次数标识
    var checkData;
    //打印 单元格格式化 用
    var renderFunc = {
   		yearValue : function(value){ //医院年度预算
   			return formatNumber(value, 2, 1);
		},
		budg_value : function(value){ //预算值
			return formatNumber(value, 2, 1);
		},
		dept_suggest : function(value){ //科室意见
			return formatNumber(value, 2, 1);
		}
	};

    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    //查询
    function  query(){
    	budg_year = year_input.getValue();
    	//index_code = index_code_select.getValue()
    	if( !budg_year){
    		$.etDialog.error('预算年度不能为空');
    		return false ;
    	}
    	/* if( !index_code){
    		$.ligerDialog.error('预算指标不能为空');
    		return false ;
    	} */
    	var params = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_id_select.getValue() },
			{ name: 'state', value: state.getValue() }
		]
		//加载查询条件
		grid.loadData(params,"queryBudgWorkDeptYearUp.do?isCheck=false");
    }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '年度', name: 'year', width:80,frozen :true,editable:setEdit ,
	              	 	editor:{
							keyField:'year',
							type: 'select',  //编辑框为下拉框时
							//source:[],   //  静态数据接口  也可以是回调函数返回值
							url: '../../../../queryBudgYearTen.do?isCheck=false',      //  动态数据接口
							change:function(rowdata,celldata){
								//grid.updateRow(celldata.rowIndx,{index_code:"",index_name:""})
							}
						}
					},
				{ display: '指标编码', name: 'index_code', width:100,editable:false,
					},
				{ display: '指标名称', name: 'index_name', width:150,editable:setEdit,
						editor:{
							type:'select' ,
							keyField:'index_code',
							url:'../../../../queryBudgIndexDict.do?isCheck=false&budg_year='+budg_year ,
							change:function(rowdata,celldata){
			             	   // grid.updateRow(celldata.rowIndx,{dept_id:"",dept_code:"",dept_name:""})
			             	},
		             	    //与年度联动查询
		             	    create:function(rowdata,celldata,setting){
		             	    	 if(rowdata.year){
		             	    		 setting.url = '../../../../queryBudgIndexDict.do?isCheck=false&budg_year='+rowdata.year;
		             	    	 }else{
		             	    		 $.etDialog.error('请先填写年度');
		             	    		 return false ;
		             	    	 }
		             	    }
						}
					},
				{ display: '科室编码', name: 'dept_code', width:80,editable:false,
					},
				{ display: '科室名称', name: 'dept_name', width:150,editable:setEdit,
						editor:{
							type:'select' ,
							keyField:'dept_id',
							url:'../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code='+index_code ,
							change:queryLastYearWorkload ,
							//与指标联动查询
		             	    create:function(rowdata,celldata,setting){
		             	    	 if(rowdata.index_code){
		             	    		 setting.url = '../../../../queryBudgIndexDeptSet.do?isCheck=false&index_code='+rowdata.index_code;
		             	    	 }else{
		             	    		 $.etDialog.error('请先填写指标名称');
		             	    		 return false ;
		             	    	 }
		             	    }
						}
					},
                { display: '预算值', name: 'budg_value', align: 'right',width:100,editable:setEditByState,
						render:function(ui) {
							if (ui.rowData.budg_value) {
								return formatNumber(ui.rowData.budg_value, 2, 1);
							}
				        }
					},
				{ display: '说明', name: 'remark', width:200,editable:false,editable:setEditByState,
					},
				{ display: '参考值', name: 'refer_value', align: 'right',width:120,editable:false,
						render:function(ui) {
							if (ui.rowData.refer_value) {
								return formatNumber(ui.rowData.refer_value, 2, 1);
							}
						}
					},
               { display: '不通过原因', name: 'reason', width:200,dataType:'float',editable:false
					},
               { display: '状态', name: 'state', minWidth:120,dataType:'float',editable:false,
						render:function(ui) {
							if (ui.rowData.state == '01') {
								return "下发";
							}else if (ui.rowData.state == '02'){
								return "通过";
							}else if (ui.rowData.state == '03'){
								return "不通过";
							}
						}
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
  	           summary: { //  前台渲染摘要行    摘要行集合    
	               totalColumns: ['budg_value'], //合计冻结行 
	               keyWordCol: 'year', //关键字所在列的列名
           	   },
	           load:function(){
	           	   grid.refreshSummary();
	           },
               toolbar: {
                   items: [
    		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
    		        	{ type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] },
    					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_row}] },
    					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
    					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
    					{ type: "button", label: '预算分解维护',icon:'minus',listeners: [{ click: openRatePage}] },
    					//{ type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
    					//{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
    					/* { type: "button", label: '计算',icon:'calculator',listeners: [{ click: collect}] }, */
    					{ type: "button", label: '下发',icon:'arrowreturnthick-1-e',listeners: [{ click: issued}] },
    					{ type: "button", label: '撤回',icon:'arrowreturnthick-1-w',listeners: [{ click: retract}] },
    					{ type: "button", label: '确认通过',icon:'check',listeners: [{ click: pass}] }, 
    					{ type: "button", label: '确认不通过',icon:'closethick',listeners: [{ click: disPass}] },
    					{ type: "button", label: '取消确认',icon:'circle-arrow-w',listeners: [{ click: cancelConfirm}] }
              	   ]
	           }
        });
    }
    
	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 (合计行所有列不可编辑)
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id ){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
	
	// 根据 state 是否存在 返回 true 或 false  控制单元格可否编辑 用 (合计行所有列不可编辑)
    function setEditByState(ui){
   		 if(ui.rowData && (ui.rowData.state == null || ui.rowData.state == '' || ui.rowData.state =='03' )){
   			 return true ;
   		 }else{
   			 return false ;
   		 }
    }
	
	//添加行
	function add_row(){
		grid.addRow();
	}
	
    //选择 科室后 查询其上年业务量 并在行数据中添加上年业务量数据
    function queryLastYearWorkload(rowdata,celldata){
		setTimeout(function (){   // 为了改过的rowdata同步，增加延时定时器
			grid.updateRow(celldata.rowIndx,{'dept_code':rowdata.dept_name.split(" ")[0]});
			   if(rowdata.year && rowdata.index_code && rowdata.dept_id){
				   $.post("queryDeptYearLastYearWork.do?isCheck=false&budg_year="+rowdata.year+"&index_code="+rowdata.index_code+"&dept_code="+rowdata.dept_code+"&dept_code="+rowdata.dept_code,null,function(responseData){
					   var para = eval("("+responseData+")") ;
					  
					   if(para){
						  grid.updateRow(celldata.rowIndx,{'last_year_workload':para.last_year_workload});
					   }
				   });
			   }
		},300)
    }
	
    //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室年度业务预算";
    }
    
    function add_open(){
		$.ligerDialog.open({ url : 'budgWorkDeptYearAddPage.do?isCheck=false',data:{}, 
			height: 300,width: 450, title:'科室年度业务预算添加',modal:true,showToggle:false,showMax:true,showMin: false,
			isResize:true,
    	}); 
    }
	//保存
    function save (){
		var data = grid.getChanges();
		var ParamVo =[];
		if( data.addList.length > 0 || data.updateList.length > 0 ){
        	
    		if(data.addList.length > 0){
        		var addData = data.addList ;
        		if(!validateGrid(addData)){
        			return  false ;
        		}
                $(addData).each(function (){	
                	ParamVo.push(
            			this.year	+"@"+
       					this.index_code   +"@"+ 
       					this.dept_id   +"@"+ 
       					(this.last_year_workload? this.last_year_workload:"")  +"@"+ 
       					(this.grow_rate? this.grow_rate:"")  +"@"+ 
       					(this.grow_value? this.grow_value:"")  +"@"+ 
       					(this.resolve_rate? this.resolve_rate:"")  +"@"+ 
       					(this.count_value? this.count_value:"")  +"@"+ 
       					(this.budg_value? this.budg_value:"")  +"@"+ 
       					(this.remark?this.remark:"")   	+"@"+ 
       					(this.refer_value? this.refer_value:"") +"@"+
       					(this.reason? this.reason:"") +"@"+
       					(this.state? this.state:"") +"@"+
      					//行号 提示错误信息用
      					this._rowIndx +"@"+
      					"1" //添加标识
    				) 
    			});
        	}
			if( data.updateList.length > 0){
	        	var updateData = data.updateList ;
	               $(updateData).each(function (){	
	               	ParamVo.push(
	           			this.year	+"@"+
      					this.index_code   +"@"+ 
      					this.dept_id   +"@"+ 
      					(this.last_year_workload? this.last_year_workload:"")  +"@"+ 
      					(this.grow_rate? this.grow_rate:"")  +"@"+ 
      					(this.grow_value? this.grow_value:"")  +"@"+ 
      					(this.resolve_rate? this.resolve_rate:"")  +"@"+ 
      					(this.count_value? this.count_value:"")  +"@"+ 
      					(this.budg_value? this.budg_value:"")  +"@"+ 
      					(this.remark?this.remark:"")   	+"@"+ 
      					(this.refer_value? this.refer_value:"") +"@"+
       					(this.reason? this.reason:"") +"@"+
       					(this.state? this.state:"") +"@"+
      					//行号 提示错误信息用
      					this._rowIndx +"@"+
      					"2" //修改标识
	   				) 
	   			});
        	}
			ajaxPostData({
                url: "saveBudgWorkDeptYearUp.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
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
            var ParamVo =[];
            var deleteDate = [];
            $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code   +"@"+ 
						this.rowData.year   +"@"+ 
						this.rowData.index_code   +"@"+ 
						this.rowData.dept_id 
					)
            	}else{
            		deleteDate.push(this);
            	}
			});
            $.etDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		if(ParamVo.length > 0){
            			ajaxPostData({
                            url: "deleteBudgWorkDeptYearUp.do?isCheck=false",
                            data: { ParamVo: ParamVo.toString() },
                            success: function(responseData) {
                            	query();
                            }
                        });
            		}else{
            			grid.deleteRows(deleteDate);
                    	$.etDialog.success("删除成功!");
            		}
            	}
            }); 
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
			if (!v.index_name) {
				rowm+="[指标名称]、";
			}
			if (!v.dept_name) {
				rowm+="[预算值]、";
			}
			if (!v.budg_value) {
				rowm+="[预算值]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.year + v.index_code + v.dept_id 
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
  
    function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgWorkDeptYearImportPage.do?isCheck=false'
		});
		layer.full(index);
    }	
    
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
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
    //计算
    function collect(){
		var year = year_input.getValue();
		var index_code = index_code_select.getValue()	   
		if( !year){
			$.etDialog.error('预算年度不能为空');
			
			return false ;
		}
		if( !index_code){
			
			$.etDialog.error('预算指标不能为空');
			
			return false ;
		}
		ajaxPostData({
            url:"collectBudgWorkDeptYearUp.do?isCheck=false&year="+year+"&index_code="+index_code,
            data: {},
            success: function(responseData) {
            	query();
            }
        });
   }
   
    //下发的功能
	function issued(){
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}
		var ParamVo =[];
		$(data).each(function (){
			ParamVo.push(
				this.rowData.group_id   +"@"+ 
				this.rowData.hos_id   +"@"+ 
				this.rowData.copy_code   +"@"+ 
				this.rowData.year   +"@"+ 
				this.rowData.index_code   +"@"+ 
				this.rowData.index_name   +"@"+ 
				this.rowData.dept_id   +"@"+
				this.rowData.dept_name +"@"+
				"01"  +"@"+
				"1"
			) 
		});
		$.etDialog.confirm('确定下发?', function (yes){
			if(yes){
				ajaxPostData({
	                url: "issuedOrRetract.do?isCheck=false",
	                data: {ParamVo : ParamVo.toString()},
	                success: function(responseData) {
	                	query();
	                }
	            });
			}
		}); 
	}
    //撤回的功能
	function retract(){
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}
		var ParamVo =[];
		$(data).each(function (){
			ParamVo.push(
				this.rowData.group_id   +"@"+ 
				this.rowData.hos_id   +"@"+ 
				this.rowData.copy_code   +"@"+ 
				this.rowData.year   +"@"+ 
				this.rowData.index_code   +"@"+ 
				this.rowData.index_name   +"@"+ 
				this.rowData.dept_id   +"@"+
				this.rowData.dept_name +"@"+
				"-1"   +"@"+
				"2"
			) 
		});
		$.etDialog.confirm('确定撤回?', function (yes){
			if(yes){
				ajaxPostData({
	                url: "issuedOrRetract.do?isCheck=false",
	                data: {ParamVo : ParamVo.toString()},
	                success: function(responseData) {
	                	query();
	                }
	            });
			}
		}); 
	}
    
    //确认通过的功能
	function pass(){
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}
		var ParamVo =[];
		$(data).each(function (){
			ParamVo.push(
				this.rowData.group_id   +"@"+ 
 				this.rowData.hos_id   +"@"+ 
 				this.rowData.copy_code   +"@"+ 
 				this.rowData.year   +"@"+ 
 				this.rowData.index_code   +"@"+ 
 				this.rowData.index_name   +"@"+ 
 				this.rowData.dept_id   +"@"+
 				this.rowData.dept_name +"@"+
 				"" +"@"+
 	        	"" +"@"+
 				"02"  
			) 
		});
		$.etDialog.confirm('确定确认通过?', function (yes){
			if(yes){
				ajaxPostData({
	                url: "passOrDisPass.do?isCheck=false",
	                data: {ParamVo : ParamVo.toString()},
	                success: function(responseData) {
	                	query();
	                }
	            });
			}
		}); 
	}
    
	//确认不通过
    function disPass() {
    	checkData = grid.selectGetChecked();
    	if (checkData.length == 0){
			$.etDialog.error('请选择行');
			return;
		}
        $.etDialog.open({
            url: 'disPassReasonPage.do?isCheck=false',
            height: 350,
            width: 700,
            title: '预算确认不通过原因',
            btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgWorkDeptDisPassReason();
            }
        });
    }
    
    //取消确认的功能
	function cancelConfirm(){
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}
		var ParamVo =[];
		$(data).each(function (){
			ParamVo.push(
				this.rowData.group_id   +"@"+ 
				this.rowData.hos_id   +"@"+ 
				this.rowData.copy_code   +"@"+ 
				this.rowData.year   +"@"+ 
				this.rowData.index_code   +"@"+ 
				this.rowData.index_name   +"@"+ 
				this.rowData.dept_id   +"@"+
				this.rowData.dept_name +"@"+
				"01"   +"@"+
				"3"
			) 
		});
		
		$.etDialog.confirm('确定取消确认?', function (yes){
			if(yes){
				ajaxPostData({
	                url: "issuedOrRetract.do?isCheck=false",
	                data: {ParamVo : ParamVo.toString()},
	                success: function(responseData) {
	                	query();
	                }
	            });
			}
		}); 
	}
   
	function openRatePage(){
	 	var year = year_input.getValue();
		var index_code = index_code_select.getValue()	   
		if( !year){
			$.etDialog.error('预算年度不能为空');
			
			return false ;
		}
	  	var parm = "year="+year +"&index_code="+index_code 
	  	parent.$.ligerDialog.open({ url : 'hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearUpdatePage.do?isCheck=false&'+parm,data:{}, height:500,width:700,
				title:'预算分解维护',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
				parentframename: window.name,
	   	}); 
    }
   
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
			url: "../../../../queryBudgIndexDict.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		});
		
		
		dept_id_select = $("#dept_id_select").etSelect({
			defaultValue: "none",
			url:"../../../../queryBudgDeptDict.do?isCheck=false",
			onChange: query
		});
		
		state = $("#state").etSelect({
		    options:[
	            {id: '',text:'全部'},
	            {id: '04',text:'空'},
	            {id: '01',text:'下发'},
	            {id: '02',text:'通过'},
	            {id: '03',text:'不通过'},
		    ],
		    onChange: query
		})
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
	
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', save);
		//hotkeys('A', add_open);
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
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_id_select" style="width:180px;"></select>
				</td>
				<td class="label">状态：</td>
				<td class="ipt">
					<select name="" id="state" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
