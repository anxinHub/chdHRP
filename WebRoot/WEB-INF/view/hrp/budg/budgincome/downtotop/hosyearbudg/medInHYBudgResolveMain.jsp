<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    var budg_year ;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
       
		$("#year").change(function(){
			budg_year = liger.get("year").getValue();
			
			autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
			query();
        });
		
		$("#subj_code").change(function(){
        	query();
        });
		
		$("#subj_level").change(function(){
        	query();
        });
		
		$("#dept_id").change(function(){
        	query();
        });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'year',value:liger.get("year").getValue()}); 
    	  grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	  grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()});
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }
  
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '预算年度', name: 'year', align: 'left',width:80},
                     { display: '科室编码', name: 'dept_code', align: 'left',width:150},
					 { display: '科室名称', name: 'dept_name', align: 'left',width:150},
				 	 { display: '科目编码', name: 'subj_code', align: 'left',width:150},
				 	 { display: '科目名称', name: 'subj_name', align: 'left',width:150},
				 	 { display: '医院年度收入预算(元)', name: 'hyValue', align: 'right',width:150,
				 		 render:function(rowdata,rowindex,value){
				 				return formatNumber(value, 2, 1);
				 		 }
				 	 },
				 	 { display: '上年收入(元)', name: 'last_year_income', align: 'right',width:150,
				 		 render:function(rowdata,rowindex,value){
				 				return formatNumber(value, 2, 1);
				 		 }
				 	 },
				 	{ display: '增减比例(E)', name: 'grow_rate', align: 'right',width:150,editor : {type : 'float'/*, onChanged : setCountValueAfterRate */},
				 		 render:function(rowdata,rowindex,value){
				 			if(value){
			 					return value+"%";
			 				};
				 		 }
				 	 },
				 	{ display: '分解比例(E)', name: 'resolve_rate', align: 'right',width:150,editor : {type : 'float'/*, onChanged : setCountValueAfterRate */},
				 		 render:function(rowdata,rowindex,value){
				 			
				 			if(value){
			 					return value+"%";
			 				};
				 				
				 		 }
				 	 },
				 	{ display: '计算值(元)', name: 'count_value', align: 'right',width:150,
				 		 render:function(rowdata,rowindex,value){
				 				return formatNumber(value, 2, 1);
				 		 }
				 	 },
				 	 { display: '预算值(元)(E)', name: 'budg_value', align: 'right',width:150,editor : {type : 'float'/*, onChanged : setCountValueAfterRate */},
				 		 render:function(rowdata,rowindex,value){
				 				return formatNumber(value, 2, 1);
				 		 }
				 	 },
                     { display: '说明(E)', name: 'remark', align: 'left',width:200,editor : {type : 'string'}}
                     /* { display: '科室意见(元/E)', name: 'dept_suggest', align: 'right',width:150,editor : {type : 'float'},
                    	 render:function(rowdata,rowindex,value){
				 				return formatNumber(vlue, 2, 1);
				 		 }
                     } */
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInDYBudgUp.do?isCheck=false&is_last=1&resolve_or_sum=01',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit :true ,isAddRow:false ,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad:true,
                     onToFirst : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToPrev  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToNext  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToLast  : updateDateExist,//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '计算（<u>C</u>）', id:'count', click: count,icon:'count' },
                     	{ line:true },
                     	
                     	/*{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },*/
    	                { text: '保存（<u>S</u>）', id:'save', click: save, icon:'save' },
    	                { line:true }
    	                /*{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' },
		                { line:true }, 
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' }*/
    				]},
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
	function updateDateExist(){
		var data = grid.changedCells;
        if (!isObjEmpty(data)){

        	$.ligerDialog.warn('数据有变更【<span style="color:red">不支持跨页保存数据,请先保存变更数据(若不想保存变更数据,请刷新页面确定后再操作)</span>】');

        	return false;
        }
        function isObjEmpty(obj){      //新注册的方法判断对象是否为空对象
        	for(var i in obj )
        		{
        			return false;
        		}
        	return true;
        }
	}
  	
  
    //计算
    function count(){
    	var year = liger.get("year").getValue() ;
		var subj_code = liger.get("subj_code").getValue() ;
		if( !year){
			$.ligerDialog.error('预算年度不能为空');
			
			return false ;
		}
		if( !subj_code){
			
			$.ligerDialog.error('预算科目不能为空');
			
			return false ;
		}
		
		ajaxJsonObjectByUrl("collectMedInDYBudgUp.do?isCheck=false&year="+year+"&subj_code="+subj_code,{},function (responseData){
    		if(responseData.state=="true"){
    			query();
    		}
		});
    }
  
    function add_open(){
    	
		$.ligerDialog.open({ url : 'medInDYBudgAddPage.do?isCheck=false',data:{}, height: 500,width: 800, 
				title:'科室年度医疗收入预算添加',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
				
    	}); 
    }
    
    
    //修改保存
    
    function save(){
    	var data = grid.getUpdated();
    	
		if( data.length == 0 ){
    		
    		$.ligerDialog.error('没有需要保存的数据');
    	}else{
   			var ParamVo =[]; 
   			 $(data).each(function (){
   				 ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id  +"@"+ 
					this.copy_code  +"@"+ 
					this.year   +"@"+ 
					this.subj_code  +"@"+ 
					this.dept_id  +"@"+ 
					(this.budg_value? this.budg_value:"")  +"@"+ 
					(this.last_year_income? this.last_year_income:"")  +"@"+ 
   					(this.remark?this.remark:"")   	+"@"+ 
   					(this.dept_suggest? this.dept_suggest:"-1")   +"@"+ 
   					(this.grow_rate? this.grow_rate:"-1")  +"@"+
   					(this.resolve_rate? this.resolve_rate:"-1")  +"@"+
   					(this.count_value? this.count_value:"-1") 
   					
				)
   			 })
   			 ajaxJsonObjectByUrl("updateMedInDYBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
   		}
    }
    
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.year   +"@"+ 
				this.subj_code   +"@"+ 
				this.dept_id 
				) });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteMedInDYBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
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
					content : 'medInDYBudgImportPage.do?isCheck=false'
				});
				layer.full(index);
    }	
    
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function printDate(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name:'year',value:liger.get("year").getValue()}); 
  	  	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
  	  	grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()});
  	 	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue()});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"科室年度医疗收入预算信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryMedInDYBudgUp.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    }
    
    function loadDict(){
            //字典下拉框
            
    	 //预算年度下拉框
        autocomplete("#year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true,'',80);
            
        budg_year = liger.get("year").getValue();
            
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
    	//科目级次下拉框
        autocomplete("#subj_level","../../../queryBudgSubjLevel.do?isCheck=false","id","text",true,true,'',false,'',80);
    	
        //预算科室下拉框（）
        autocomplete("#dept_id","../../../queryBudgDeptDict.do?isCheck=false","id","text",true,true,'',false,'',200);
    	
        $("#year").ligerTextBox({width:80});
        $("#subj_code").ligerTextBox({width:200});
        $("#subj_level").ligerTextBox({width:80});
        $("#dept_id").ligerTextBox({width:200});
     }  
    //键盘事件
	 function loadHotkeys() {

		hotkeys('Q', query);

		/* hotkeys('A', add_open);
		hotkeys('S', save);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);


		hotkeys('P', printDate);
		hotkeys('I', imp);
		 */

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目名称<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目级次：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
         </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
