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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys(); 
		
		$("#index_code").change(function(){
			query();
		})
		$("#type_code").change(function(){
			query();
		})
		
		$("#kind_code").change(function(){
			query();
		})
		$("#out_code").change(function(){
			query();
		})
		$("#natur_code").change(function(){
			query();
		})
		
		
    });
    //查询
    function  query(){
    	
    	var index_code = liger.get("index_code").getValue();
    	if(!index_code){
    		$.ligerDialog.error('指标名称不能为空！');
   		  	return false ;
    	}
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
 
     	 grid.options.parms.push({name:'index_code',value:liger.get("index_code").getValue()}); 
     	 grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
     	 grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
     	 grid.options.parms.push({name:'out_code',value:liger.get("out_code").getValue()}); 
     	 grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    { display: '科室编码', name: 'dept_code', align: 'left'},
			 		{ display: '科室名称', name: 'dept_name', align: 'left'},
			 		{ display: '部门分类', name: 'kind_name', align: 'left'},
			 		{ display: '部门性质', name: 'natur_name', align: 'left'},
			 		{ display: '支出性质', name: 'out_name', align: 'left'	}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgIndexDeptSet.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,isChecked: initCheck,
                     delayLoad :true ,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存（<u>A</u>）', id:'add', click: save, icon:'add' },
    	                { line:true }
		               
    				]},
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  function initCheck(e){
	  if(e.index_code == liger.get("index_code").getValue()){
  		return true;
  	}else{
  		return false ;
  	}
  }
  
  function save(){
	  
	  var index_code = liger.get("index_code").getValue();
	  
	  var data = grid.getSelectedRows();
	  if(!index_code){
		  $.ligerDialog.error('指标名称不能为空！');
		  return false ;
	  }
	  
	  if(data.length ==  0){
		  $.ligerDialog.error('请选择行数据后再保存！');
		  return false ;
	  }
	  
	  var ParamVo =[];
      $(data).each(function (){					
			ParamVo.push(
				index_code   +"@"+ 
				this.dept_id    
			) 
  	  }); 
      $.ligerDialog.confirm('确定保存所选的数据?', function (yes){
      	if(yes){
          	ajaxJsonObjectByUrl("addBudgIndexDeptSet.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
          		if(responseData.state=="true"){
          			query();
          		}
          	});
      	}
      }); 
  }
   function loadDict(){
            //字典下拉框
    	 //费用标准性质下拉框
        autocomplete("#type_code","../../queryBudgDeptType.do?isCheck=false","id","text",true,true);        
        autocomplete("#kind_code","../../queryBudgDeptKind.do?isCheck=false","id","text",true,true);
        autocomplete("#out_code","../../queryBudgDeptOut.do?isCheck=false","id","text",true,true);
        autocomplete("#natur_code","../../queryBudgDeptNature.do?isCheck=false","id","text",true,true);
        autocomplete("#index_code","../../queryBudgIndexDict.do?isCheck=false","id","text",true,true,'',true);
    }   
  //键盘事件
	  function loadHotkeys(){
  }
 	   
    </script>
    
	

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称<span style="color:red" >*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门性质：</td>
            <td align="left" class="l-table-edit-td"><input name="natur_code" type="text" id="natur_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出性质：</td>
            <td align="left" class="l-table-edit-td"><input name="out_code" type="text" id="out_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	
</body>
</html>
