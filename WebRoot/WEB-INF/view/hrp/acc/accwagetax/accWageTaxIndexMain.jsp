<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
    	loadDict(null);
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
     grid.options.parms.push({name:'wage_code',value:liger.get("wage_code").getValue()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
    		
           columns:[ 
                    { display: '描述', name: 'note', align: 'left',editor: { type: 'text' }
					 },
	                 { display: '起点数', name: 'starts', align: 'left',editor: { type: 'text' }
					 },
	                 { display: '终点数', name: 'ends', align: 'left',editor: { type: 'text' }
					 },
	                 { display: '税率', name: 'rate', align: 'left',editor: { type: 'text' }
					 },
	                 { display: '速算扣除数', name: 'deduct', align: 'left',editor: { type: 'text' }
					 },
	                 { display: '操作', name:'operation', align: 'left',render : function(rowdata, rowindex,
								value) {
							return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 60px;' ligeruiid='Button1004'" 
							+"onclick=deleteData("+rowdata.rate_id+");>"
              				+"<span>删除</span></div>";
   						}
					 }
	                 ],
           
           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccWageTax.do',
                     
           width: '100%', height: '100%', checkbox: false,rownumbers:true,
           
           selectRowButtonOnly:true,enabledEdit: true,
           
           toolbar: { items: [
						
						{ text: '查询', id:'search', click: query,icon:'search' },
						
						{ line:true },
                     	
						{ text: '添加', id:'add', click: addNewRow,icon:'add' },
                     	
						{ line:true },
						
						{ text: '保存', id:'save', click: endEdit,icon:'save' },
                     	
						{ line:true }
						
						
    				]}
    	
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
       
    }
    
    function endEdit()
    {
        gridManager.endEdit();
        
        var data = gridManager.getAdded();
        
        var updateData = gridManager.getUpdated();
        
        var wage_code=liger.get("wage_code").getValue();
        
        var msg="";
        
        if(JSON.stringify(data)=="[]"&&JSON.stringify(updateData)=="[]"){
        	
        	$.ligerDialog.error("没有保存数据");
        	
        	return;
        
        }else if(wage_code==""){
        	
        	$.ligerDialog.error("工资套不能为空！");
        	
        	return;
        
        }else{
        	var ParamVo =[];
        	
        	var Param =[];
        	
        	 $.each(data,function(i,v){
        		 
            	if(v.starts==""||v.rate==""||v.deduct==""){
            		
            		msg="请完善信息再保存";
            		
            		return;
            		
            	}else{
            		
            		ParamVo.push(
							//表的主键
							this.note +"@"+
							this.starts +"@"+
							this.ends +"@"+
							this.rate +"@"+
							this.deduct 
							);
            	}
            }); 
        	 
        	 $.each(updateData,function(i,v){
        		
             	if(v.starts==""||isnull(v.rate)==""||v.deduct==""){
             		
             		$.ligerDialog.error("请完善信息再保存");
             		
             		return;
             		
             	}else{
             		
             		Param.push(
 							//表的主键
 							wage_code+"@"+
 							this.note +"@"+
 							this.starts +"@"+
 							this.ends +"@"+
 							this.rate +"@"+
 							this.deduct +"@"+
 							this.rate_id 
 							);
             	}
             }); 
			 if(msg != ""){
				 
				 $.ligerDialog.error(msg); 
				 
				 return;
				 
			 }
        	 ajaxJsonObjectByUrl("addAccWageTax.do",{ParamVo : ParamVo.toString(),Param : Param.toString()},function (responseData){
         		if(responseData.state=="true"){
         			query();
         		}
         	}); 
        } 
        
    }
    
    function addNewRow()
    {
    	var data = gridManager.getAdded();
    	
    	var i=0;
    	
        if(JSON.stringify(data)=="[]"){
        	
        	gridManager.addEditRow();
        	
        }else{
        	
        	 $.each(data,function(i,v){
        		 
            	if(v.note==""||v.starts==""||v.ends==""||v.rate==""||v.deduct==""){
            		
            		$.ligerDialog.error("请完善信息");
            		
            		i=i+1;
            		
            		return;
            	}
            }); 
        	 
        	 if(i==0){
        		 gridManager.addEditRow();
        	 }
        }
        
    } 
    
    function deleteData(rate_id){
    	
		var ParamVo=[];    	
    	$.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
        		ParamVo.push(
						//表的主键
						rate_id 
						);
            	ajaxJsonObjectByUrl("deleteAccWageTax.do",{ParamVo : ParamVo},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
    function loadDict(){
        //字典下拉框
		autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true,'',true);
		 
     } 
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
       <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
            <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
