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
    var flag = 0 ;
    var index_code ;
    
    var renderFunc = {
			 
       		yearValue : function(value){ //医院年度预算
    			return formatNumber(value, 2, 1);
    		},
    		last_year_workload : function(value){ //预算值
    			return formatNumber(value, 2, 1);
    		},
    		grow_rate : function(value){ //预算值
    			return formatNumber(value, 2, 1)+"%";
    		},
    		resolve_rate : function(value){ //预算值
    			return formatNumber(value, 2, 1);
    		},
    		count_value : function(value){ //预算值
    			return formatNumber(value, 2, 1);
    		},
    		budg_value : function(value){ //预算值
    			return formatNumber(value, 2, 1);
    		},
    		dept_suggest : function(value){ //科室意见
    			return formatNumber(value, 2, 1);
    		}
    	};
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
		$("#year").change(function(){
			var budg_year = liger.get("year").getValue();
			autocomplete("#index_code","../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=03&budg_year="+budg_year,"id","text",true,true,"",false);
			query()
		})
		
		$("#index_code").change(function(){
			
			index_code = liger.get("index_code").getValue();
			
			query()
		})
		
    });
    //查询
    function  query(){
    	
    	if( !liger.get("year").getValue()){
    		$.ligerDialog.error('预算年度不能为空');
    		return false ;
    	}
    	/* if( !index_code){
    		
    		$.ligerDialog.error('预算指标不能为空');
    		
    		return false ;
    	} */
   		var parms=[];
   		
        //根据表字段进行添加查询条件
    	parms.push({name:'year',value:liger.get("year").getValue()}); 
		parms.push({name:'index_code',value:liger.get("index_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(parms,'queryBudgWorkDeptYearResolve.do?isCheck=false&budg_level=03');
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                     { display: '年度', name: 'year', align: 'left',width:60},
                     { display: '指标编码', name: 'index_code', align: 'left',width:100},
				 	 { display: '指标名称', name: 'index_name', align: 'left',width:120},
					 { display: '科室编码', name: 'dept_code', align: 'left',width:100},
					 { display: '科室名称', name: 'dept_name', align: 'left',width:120},
					 { display: '医院年度预算', name: 'yearValue', align: 'right',width:100,frozen :true,
					 			render:function(ui){
					 				if(ui.rowData.yearValue){
					 					return formatNumber(ui.rowData.yearValue,2,1);
					 				}
					 			}
					 		},
					 { display: '上年业务量', name: 'last_year_workload', align: 'right',width:120,
							 render:function(ui){
								 if(ui.rowData.last_year_workload){
									 return formatNumber(ui.rowData.last_year_workload, 2, 1);
								 }
					 		 }
					 	},
					 { display: '增长比例(E)', name: 'grow_rate', align: 'center',width:80,dataType : 'float',
								 render:function(ui){
									 if(ui.rowData.grow_rate){
										 return formatNumber(ui.rowData.grow_rate, 2, 1)+"%";
									 }
						 		 }
					 		},
					/*  { display: '增加额(E)', name: 'grow_value', align: 'right',width:80,dataType : 'float',
								 render:function(ui){
									 if(ui.rowData.grow_value){
										 return formatNumber(ui.rowData.grow_value, 2, 1);
									 }
						 		 }
					 		}, */
                  	 { display: '分解比例(E)', name: 'resolve_rate', align: 'center',width:80,dataType : 'float',
								 render:function(ui){
									 if(ui.rowData.resolve_rate){
										 return formatNumber(ui.rowData.resolve_rate, 2, 1)+"%";
									 }
						 		 },
					 		},
                     { display: '计算值', name: 'count_value', align: 'right',width:120,
								 render:function(ui){
									 if(ui.rowData.count_value){
										 return formatNumber(ui.rowData.count_value, 2, 1);
									 }
						 		 },
						 		
					 		},
                     { display: '预算值(E)', name: 'budg_value', align: 'right',width:120,editor : {type : 'float'},
								 render:function(ui){
									 if(ui.rowData.budg_value){
										 return formatNumber(ui.rowData.budg_value, 2, 1);
									 }
						 		 }
					 		},
					{ display: '说明(E)', name: 'remark', align: 'left',width:150
						 		},
	                { display: '科室意见(E)', name: 'dept_suggest', align: 'left',editor : {type : 'float'},width:100,
						 			render:function(ui){
						 				if(ui.rowData.dept_suggest){
						 					return formatNumber(ui.rowData.dept_suggest,2,1);
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
	       	         addRowByKey:false,
	       	         summary: { //  前台渲染摘要行    摘要行集合    
		                 totalColumns: ['resolve_rate','count_value','budg_value'], //合计冻结行 
		                 keyWordCol: 'year', //关键字所在列的列名
		                /*  averageColumns: ['revenues', 'profits'], // 平均冻结行
		                 maxColumns: ['revenues', 'profits'], // 最大值冻结行
		                 minColumns: ['revenues', 'profits'] //  最小值冻结行 */
	                },
	                load:function(){
	                	grid.refreshSummary();
	                },
	                toolbar: {
                        items: [
         		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
         		        	{ type: "button", label: '增量生成',icon:'plus',listeners: [{ click: generate}] },
         					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
         					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
         					{ type: "button", label: '计算',icon:'calculator',listeners: [{ click: collect}] },
                    ]}
                     
            });

    }
    
	function add_open(){
    	
		$.ligerDialog.open({ url : 'budgWorkDeptMonthAddPage.do?isCheck=false&',data:{}, height: 300,width: 600, 
			title:'科室月份业务预算添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgWorkDeptMonth(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
	//增量生成
	function generate(){
	 	var year = liger.get("year").getValue();
	 	if(year){
	 		ajaxJsonObjectByUrl("generate.do?isCheck=false&year="+year,{},function (responseData){
	 			if(responseData.state=="true"){
	 				query();
	 			}
	 		});
	 	}else{
	 		$.ligerDialog.error("预算年度不能为空");
	 	}
	  
	}
	//修改
    function save (){
		var data = grid.getChanges();
		var ParamVo =[];
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
   					(this.dept_suggest? this.dept_suggest:"-1")
   				) 
 			});
             
            ajaxJsonObjectByUrl("updateBudgWorkDeptYearUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
         		if(responseData.state=="true"){
         			query();
         			parentFrameUse().query();
         		}
            })
            
        }else{
        	
        	$.ligerDialog.warn('没有需要保存的数据!');
        }
   			
    	
    }
    function remove(){
       	
		var data = grid.selectGet();
		
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){	
           		ParamVo.push(
					this.rowData.group_id   +"@"+ 
					this.rowData.hos_id   +"@"+ 
					this.rowData.copy_code   +"@"+ 
					this.rowData.year   +"@"+ 
					this.rowData.index_code   +"@"+ 
					this.rowData.dept_id 
				)
			});
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteBudgWorkDeptYearUp.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                			parentFrameUse().query();
                		}
                	});
            	}
            }); 
        }
   	}
	//计算
    function collect(){
		var year = liger.get("year").getValue() ;
			   
		if( !year){
			$.ligerDialog.error('预算年度不能为空');
			
			return false ;
		}
		if( !index_code){
			
			$.ligerDialog.error('预算指标不能为空');
			
			return false ;
		}
		
		ajaxJsonObjectByUrl("collectBudgWorkDeptYearUp.do?isCheck=false&year="+year+"&index_code="+index_code,{},function (responseData){
    		if(responseData.state=="true"){
    			query();
    			parentFrameUse().query();
    		}else if(responseData.state=="false"){
    			$.ligerDialog.error(responseData.message,'','',{width:500,isResize:true});
    		}
		});
		
	   
   }
    function loadDict(){
         //字典下拉框
         
        autocomplete("#year","../../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",false,"${year}",80);
        
        //预算指标下拉框
        //autocomplete("#index_code","../../../../queryBudgIndexDict.do?isCheck=false","id","text",true,true,"",true,"");
        
        autocomplete("#index_code","../../../../qureyBudgIndexFromPlan.do?isCheck=false&budg_level=03&budg_year="+"${year}","id","text",true,true,"",false,"");
        
   	 	$("#year").ligerTextBox({width:160});
        $("#index_code").ligerTextBox({width:160});
    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		
		hotkeys('G', generate)
		
		hotkeys('C', collect);

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
