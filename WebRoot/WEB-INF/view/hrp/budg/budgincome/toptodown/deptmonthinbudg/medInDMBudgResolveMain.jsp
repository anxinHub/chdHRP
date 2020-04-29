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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var budg_year ;
    var flag = 0 ;
    $(function ()
    {
        loadDict()//加载下拉框
        loadInit();
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
       
		$("#year").change(function(){
			budg_year = budg_year.getValue();
			if(flag>0){
				autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
				query();
			}
			flag = flag + 1 ;
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
		
		$("#month").change(function(){
			query()
		})
    });
  function loadInit(){
    	
       	getData("../../../queryBudgYear.do?isCheck=false", function (data) {
       		budg_year = $("#year").etDatepicker({
			defaultDate: data[0].text,
			view: "years",
			minView: "years",
			dateFormat: "yyyy",
			/* minDate: data[data.length - 1].text,
			maxDate: data[0].text, */
			todayButton: false,
		
		});
	
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
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'year',value:budg_year.getValue()}); 
    	  grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	  grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()});
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue()});
    	  grid.options.parms.push({name:'month',value:liger.get("month").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '预算年度', name: 'year', align: 'left',width:80},
                     { display: '月份', name: 'month', align: 'left',width:40},
                     { display: '科室编码', name: 'dept_code', align: 'left',width:150},
					 { display: '科室名称', name: 'dept_name', align: 'left',width:150},
				 	 { display: '科目编码', name: 'subj_code', align: 'left',width:150},
				 	 { display: '科目名称', name: 'subj_name', align: 'left',width:150},
				 	 { display: '科室年度收入预算(元)', name: 'yearValue', align: 'right',width:150,
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
			 					return formatNumber(value, 2, 1)+"%";
			 				};
				 		 }
				 	 },
				 	{ display: '分解比例(E)', name: 'resolve_rate', align: 'right',width:150,editor : {type : 'float'/*, onChanged : setCountValueAfterRate */},
				 		 render:function(rowdata,rowindex,value){
				 			
				 			if(value){
			 					return formatNumber(value, 2, 1)+"%";
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInDMBudgResolveUp.do?isCheck=false&is_last=1&resolve_or_sum=01',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit :true ,isAddRow:false ,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     onToFirst : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToPrev  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToNext  : updateDateExist,//翻页之前判断当前页是否有变更数据 （若有变更数据，提示其保存）
         			 onToLast  : updateDateExist,//翻页之前判断当前页是否有变更数据（若有变更数据，提示其保存）
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '按科目计算（<u>C</u>）', id:'count', click: count,icon:'count' },
                     	{ line:true },	
                     	{ text: '计算全部（<u>A</u>）', id:'count', click: countAll,icon:'count' },
                     	{ line:true },
    	                { text: '保存（<u>S</u>）', id:'save', click: save, icon:'save' },
    	                { line:true }
    	               
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
    	var year = budg_year.getValue() ;
		var subj_code = liger.get("subj_code").getValue() ;
		if( !year){
			$.ligerDialog.error('预算年度不能为空');
			
			return false ;
		}
		if( !subj_code){
			
			$.ligerDialog.error('预算科目不能为空');
			
			return false ;
		}
		
		ajaxJsonObjectByUrl("collectMedInDMBudgUp.do?isCheck=false&year="+year+"&subj_code="+subj_code,{},function (responseData){
    		if(responseData.state=="true"){
    			parent.query();
    			query();
    		}
		});
    }   
    
    //计算
    function countAll(){
    	var year = budg_year.getValue() ;
		var subj_code = liger.get("subj_code").getValue() ;
		if( !year){
			$.ligerDialog.error('预算年度不能为空');
			
			return false ;
		}
		
		ajaxJsonObjectByUrl("collectMedInDMBudgUp.do?isCheck=false&year="+year,{},function (responseData){
    		if(responseData.state=="true"){
    			parent.query();
    			query();
    		}
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
					this.year   +"@"+ 
					this.month   +"@"+ 
					this.dept_id  +"@"+ 
					this.subj_code  +"@"+ 
					(this.budg_value? this.budg_value:"")  +"@"+ 
					(this.grow_rate? this.grow_rate:"-1") + "@" + 
					(this.resolve_rate? this.resolve_rate:"-1") + "@" + 
					(this.count_value? this.count_value:"-1") + "@" +  
					(this.remark? this.remark:"-1") + "@" + 
					(this.last_year_income? this.last_year_income:"-1"));
   					
   			 })
   			 ajaxJsonObjectByUrl("updateMedInDMBudgResolveUp.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
   		}
    }
    
    function loadDict(){
        //字典下拉框
            
    	//预算月份下拉框
       // autocomplete("#year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true,'${year}',60);
            
            
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+'${year}',"id","text",true,true,{key:'${subj_code}'},false,'${subj_code}',200);
    	//科目级次下拉框
        autocomplete("#subj_level","../../../queryBudgSubjLevel.do?isCheck=false","id","text",true,true,'',false,'',50);
    	
        //预算科室下拉框（）
        autocomplete("#dept_id","../../../queryBudgDeptDict.do?isCheck=false","id","text",true,true,'',false,'',200);
 		autoCompleteByData("#month", monthData.Rows, "id", "text", true, true,'',false,'',50);
        
   	 	//$("#year").ligerTextBox({width:60});
        $("#month").ligerTextBox({width:50});
        $("#subj_code").ligerTextBox({width:200});
        $("#subj_level").ligerTextBox({width:100});
        $("#dept_id").ligerTextBox({width:200});
     }  
    //键盘事件
	 function loadHotkeys() {

		hotkeys('Q', query);

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份：</td>
            <td align="left" class="l-table-edit-td"><input name="month" type="text" id="month" ltype="text" validate="{required:true,maxlength:20}" /></td>
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
