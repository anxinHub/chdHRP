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
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
        
    	$("#insurance_code").change(function(){
			query();
		})
		
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'insurance_code',value:liger.get("insurance_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '医保类型编码', name: 'insurance_code', align: 'left'},
			 		 { display: '医保类型名称', name: 'insurance_name', align: 'left'},
                     { display: '是否总额预付', name: 'ZE', align: 'center', 
			 			render: function(rowdata, rowindex,value) {
							if(rowdata.ZE == '√'){
								return "<input id=ZE"+rowdata.insurance_code+"  type ='checkbox' checked='checked' style='margin-top:8px;'>";
							}else{
								return "<input id=ZE"+rowdata.insurance_code+"  type ='checkbox' style='margin-top:8px;'>";
							}
			 			}
			 		},
                    { display: '是否单病种付费', name: 'DB', align: 'center',
				 			render: function(rowdata, rowindex,value) {
								if(rowdata.DB == '√'){
									return "<input id=DB"+rowdata.insurance_code+"  type ='checkbox' checked='checked' style='margin-top:8px;'>";
								}else{
									return "<input id=DB"+rowdata.insurance_code+"  type ='checkbox' style='margin-top:8px;'>";
								}
				 			}
					 },
                     { display: '是否按人头付费', name: 'RT', align: 'center',
						 render: function(rowdata, rowindex,value) {
								if(rowdata.RT == '√'){
									return "<input id=RT"+rowdata.insurance_code+"  type ='checkbox' checked='checked' style='margin-top:8px;'>";
								}else{
									return "<input id=RT"+rowdata.insurance_code+"  type ='checkbox' style='margin-top:8px;'>";
								}
				 		}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgPayMode.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 30,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存', id:'add', click: add, icon:'add' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	//添加
	function add(){
		var data = gridManager.getData();
		 var ParamVo =[];
         $(data).each(function (){
      	 		if($("#ZE"+this.insurance_code+"").prop("checked")){
      	 			ParamVo.push(
   	 					this.group_id   +"@"+ 
   	 					this.hos_id   +"@"+ 
   	 					this.copy_code   +"@"+ 
   	 					this.insurance_code  +"@"+ 
   	 					'ZE' 
   	 					) 
      	 		};
      	 		if($("#DB"+this.insurance_code+"").prop("checked")){
      	 			ParamVo.push(
   	 					this.group_id   +"@"+ 
   	 					this.hos_id   +"@"+ 
   	 					this.copy_code   +"@"+ 
   	 					this.insurance_code  +"@"+ 
   	 					'DB' 
   	 					) 
      	 		};
      	 		if($("#RT"+this.insurance_code+"").prop("checked")){
      	 			ParamVo.push(
   	 					this.group_id   +"@"+ 
   	 					this.hos_id   +"@"+ 
   	 					this.copy_code   +"@"+ 
   	 					this.insurance_code  +"@"+ 
   	 					'RT' 
   	 					) 
      	 		};
         });
         if(ParamVo.length>0){
        	 ajaxJsonObjectByUrl("addBudgPayMode.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
         		if(responseData.state=="true"){
         			query();
         		}
         	});
         }else{
        	 ajaxJsonObjectByUrl("deleteBudgPayMode.do?isCheck=false",function (responseData){
          		if(responseData.state=="true"){
          			query();
          		}
          	});
         }
         
	}

	//打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		/* grid.options.lodop.fn=renderFunc; */
 		grid.options.lodop.title="医保付费机制";
    }
    
    //字典加载
    function loadDict(){
        //字典下拉框
        //医保类型下拉框（医保性质 为 01 医保）
    	 autocomplete("#insurance_code","../../../queryBudgYBType.do?isCheck=false&insurance_natrue='01'","id","text",true,true);
    }  
    
  	
		  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医保类型：</td>
            <td align="left" class="l-table-edit-td"><input  name="insurance_code" type="text" id="insurance_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
