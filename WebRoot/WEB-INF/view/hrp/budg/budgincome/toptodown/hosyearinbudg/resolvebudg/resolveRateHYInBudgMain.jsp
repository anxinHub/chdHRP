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
    var gridManager = null;
    var userUpdateStr;
    var year_input,subj_name_select,subj_level_select;
    var budg_year ;
    
    $(function ()
    {
    	
    	loadHead(null);
        //loadDict()//加载下拉框
        init();
    	//加载数据
    		
		loadHotkeys();
		
		budg_year = year_input.getValue();
		
		$("#year").change(function(){
			budg_year = year_input.getValue();
			
			autocomplete("#subj_code","../../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
			query();
        });
		
		$("#subj_code").change(function(){
        	query();
        });
		
		$("#subj_level").change(function(){
        	query();
        });
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'year',value:year_input.getValue()}); 
    	  grid.options.parms.push({name:'subj_code',value:subj_code_select.getValue()}); 
    	  grid.options.parms.push({name:'subj_level',value:subj_level_select.getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                   
                     { display: '科目编码', name: 'subj_code', align: 'left'
					
					 		},
				 	 { display: '科目名称', name: 'subj_name', align: 'left'
					 		},
				 	 { display: '上年收入 (元)', name: 'last_year_income', align: 'left',
					 			render:function(rowdata,rowindex,value){
					 				if(value){
					 					return formatNumber(value,2,1);
					 				}
					 			}
					 		},
					
	                 { display: '增长比例(E)', name: 'grow_rate', align: 'left',editor : {type : 'float'},
					 			render:function(rowdata,rowindex,value){
 					 				if(value){
 					 					return formatNumber(value,2,0)+"%" ;
 					 				}
 					 			}
					 		},
                     { display: '分解比例(E)', name: 'resolve_rate', align: 'left',editor : {type : 'float'},
					 			render:function(rowdata,rowindex,value){
 					 				if(value){
 					 					return formatNumber(value,2,0)+"%" ;
 					 				}
 					 			}
					 		},
                     { display: '说明(E)', name: 'remark', align: 'left',editor : {type : 'string'}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryResolveRateHYInBudgUp.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit :true ,isAddRow:false ,
                     selectRowButtonOnly:true,delayLoad:true,//heightDiff: -10,
         			 onToFirst : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToPrev  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToNext  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToLast  : updateDateExist,//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	
    					{ text: '保存（<u>S</u>）', id:'save', click: save, icon:'save' },
    	                { line:true },
    	               /*  { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
		                { line:true }, */
		                
    	                { text: '生成（<u>G</u>）', id:'generate', click: generate, icon:'generate' },
    	                { line:true }, 
		                /* { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' },
		                { line:true }, */
		                /* { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:resolveRatedownTemplate,icon:'down' },
		                { line:true }, */
		                /* { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' } */
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
  
    function save (){
    	var data = grid.getUpdated();
    	
    	if( data.length == 0 ){
    		
    		$.ligerDialog.error('没有需要保存的数据');
    	}else{
   			var ParamVo =[]; 
   			 $(data).each(function (){
   				 ParamVo.push(
					this.year   +"@"+ 
					this.subj_code  +"@"+ 
					(this.last_year_income? this.last_year_income:"") +"@"+
					(this.resolve_rate? this.resolve_rate:"")  +"@"+ 
					(this.grow_rate? this.grow_rate:"")  +"@"+ 
					(this.remark?this.remark:"") 
					)
   			 })
   			 ajaxJsonObjectByUrl("updateResolveRateHYInBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			parent.query();
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
						this.year   +"@"+ 
						this.subj_code  +"@"+ 
						(this.last_year_income? this.last_year_income:"") +"@"+
						(this.resolve_rate="" )+"@"+ 
						(this.grow_rate="" )+"@"+ 
						(this.remark="-1") 
					) 
				});
                    
                $.ligerDialog.confirm('确定删除?', function (yes){
                	if(yes){
                    	ajaxJsonObjectByUrl("updateResolveRateHYInBudgUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                    		if(responseData.state=="true"){
                    			parent.query();
                    			query();
                    		}
                    	});
                	}
                }); 
             }
    }
    function generate(){

		var year = year_input.getValue();
    	if(year){
    		ajaxPostData({
				url: "generateResolveRate.do?isCheck=false&year="+year,
				data: {},
				success: function (res) {
					if (res.state == "true") {
						query();
					}
				}
			})
    	}else{
    		$.etDialog.error("预算年度不能为空");
    	}
    	
    }
    
   /*  function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'resolveRateHYInBudgImportPage.do?isCheck=false'
				});
				layer.full(index);
    }	 */
    /* function resolveRatedownTemplate(){
    	
    	location.href = "resolveRatedownTemplate.do?isCheck=false";
    	}	 */
      
       //打印数据
    /* function printDate(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name:'year',value:liger.get("year").getValue()}); 
		grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
		grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"医院年度医疗收入预算分解预算信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryResolveRateHYInBudgUp.do?isCheck=false&is_single_count=0", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    } */
    
    function init() {
	
			year_input = $("#year").etDatepicker({
				defaultDate:true,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
			/* 	minDate: data[data.length - 1].text,
				maxDate: data[0].text, */
				todayButton: false,
				  onInit : function(value) {
						 reloadSubjCode(value);
					 },
					 onChange: function (value) {
					reloadSubjCode(value);
					query();
				}
			  
			});
			
	/* 		
		subj_code_select = $("#subj_code").etSelect({
			defaultValue: "none",
			url: "../../../../queryBudgSubj.do?isCheck=false",
			para: {
				subj_type: "04",
				budg_year: year_input.getValue()
			},
			onChange:query
		}); */
		subj_code_select = $("#subj_code").etSelect({
		    url: '../../../../queryBudgSubj.do?isCheck=false&subj_type='+'04'+"&budg_year="+year_input.getValue(),
		    defaultValue: "none",
		   
		});
		function reloadSubjCode(year) {
			subj_code_select.reload({
				url: "../../../../queryBudgSubj.do?isCheck=false",
				para: {
					subj_type: "04",
					budg_year: year
				}
			});
		}
		
		subj_level_select = $("#subj_level").etSelect({
			url: "../../../../queryBudgSubjLevel.do?isCheck=false&insurance_natrue='01'",
			defaultValue: "none",
			onChange: query
		});
	}
    function loadDict(){
            //字典下拉框
            
    	 //预算年度下拉框
        autocomplete("#year","../../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
            
           budg_year = year_input.getValue();
            
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
    	//科目级次下拉框
        autocomplete("#subj_level","../../../../queryBudgSubjLevel.do?isCheck=false&insurance_natrue='01'","id","text",true,true);
    	
       
    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('S', save);
		hotkeys('D', remove);

		/* hotkeys('B', downTemplate); */

		/* hotkeys('P', printDate); */
		/* hotkeys('I', imp); */
		

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" style="width:180px;"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目级次：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text" validate="{required:true,maxlength:20}" style="width:180px;" /></td>
            <td align="left"></td>
        </tr> 
       
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
