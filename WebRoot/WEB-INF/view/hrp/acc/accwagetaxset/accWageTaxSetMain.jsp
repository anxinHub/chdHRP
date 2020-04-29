<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    $(function ()
    {
    	loadHead(null);	//加载数据
    	
    	$("#tax_value").ligerTextBox({width:160});
    	
    	$("#ass_value").ligerTextBox({width:160});
    	
    	$("#layout1").ligerLayout({ leftWidth: 400,allowLeftCollapse:false,allowRightCollapse:false}); 
    	
    	is_addRow();
    	
        $("#tax_value").val(formatNumber('${tax_value}',2,1));

    	$("#ass_value").val(formatNumber('${ass_value}',2,1));
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
       
    	//加载查询条件
    	grid.loadData(grid.where);
     }

	function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
    		
           columns:[ 
                    { display: '级次', name: 'note', align: 'left',editor: { type: 'text' },width:'10%'
					 },
	                 { display: '起点数', name: 'starts', align: 'right',editor: { type: 'text' },width:'13%',
						 render:function(rowdata){
							 
							if(rowdata.starts!=null){
								 return formatNumber(rowdata.starts, 2, 1)
							};
							 
						 }
					 },
	                 { display: '终点数', name: 'ends', align: 'right',editor: { type: 'text' },width:'13%',
							render:function(rowdata){
								
								if(rowdata.ends!=null){
									 return formatNumber(rowdata.ends, 2, 1);
								};
							 
						 }
					 },
	                 { display: '税率(%)', name: 'rate', align: 'left',editor: { type: 'text' },width:'10%'
					 },
	                 { display: '速算扣除数', name: 'deduct', align: 'right',editor: { type: 'text' },width:'15%',
						render:function(rowdata){
							 
							if(rowdata.deduct!=null){
								 return formatNumber(rowdata.deduct, 2, 1);
							};
							
						 }
					 }
	                 ],
           
           dataAction: 'server',dataType: 'server',usePager:true,url:'../accwagetax/queryAccWageTax.do',
                     
           width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager:false,
           
           selectRowButtonOnly:true,enabledEdit: true,lodop:{
         		title:"个人所得税设置",
      			fn:{
          			debit:function(value){//借方
          				if(value == 0){return "";}
                 			else{return formatNumber(value, 2, 1);}
          			},
          			credit:function(value){//贷方
          				if(value == 0){return "";}
                			else{return formatNumber(value, 2, 1);}
         				},
         				end_os:function(value){//余额
      	   				 if(value==0){return "Q";}
      					 else{return formatNumber(value, 2, 1);}
        				}
          		}
      		}
    	
           });

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    function save_tax_set(){
    	
       var formPara={
       		
    		   tax_value:$("#tax_value").val().replace(/\,/g, ""),
           
    		   ass_value:$("#ass_value").val().replace(/\,/g, "")
           
        }
       
		ajaxJsonObjectByUrl("addAccWageTaxSet.do?isCheck=false",formPara,function(responseData){
	           
	    });
    	
    }

    
    function endEdit()
    {
        gridManager.endEdit();
        
        var data = gridManager.getData();
        
        if(JSON.stringify(data)=="[]"&&JSON.stringify(updateData)=="[]"){
        	
        	$.ligerDialog.error("没有保存数据");
        	
        	return;
        
        }else{
        	var ParamVo =[];
        	
        	var Param =[];
        	
        	var rate=0;
        	
        	 $.each(data,function(i,v){
        		 
            	if(v.starts!=null||v.note!=null||v.rate!=null||v.deduct!=null){
            		
            		ParamVo.push( 
            				
							//表的主键
							this.note +"@"+
							this.starts +"@"+
							this.ends+"@"+
							this.rate +"@"+
							this.deduct 
							);
            		
            	}
            }); 
        	 if(ParamVo.length==0){
        		 
        		 $.ligerDialog.error("请填写信息再保存");
             	
             	return;
        		 
        	 }
			
        	 ajaxJsonObjectByUrl("../accwagetax/addAccWageTax.do",{ParamVo : ParamVo.toString()},function (responseData){
         		
        		 if(responseData.state=="true"){
         			
         			query();
         			
         			is_addRow();
         		}
         	}); 
        } 
        
    }
    
    function delete_btn(){
    	
    	var data = gridManager.getCheckedRows();
        
    	if (data.length == 0){
        	
    		$.ligerDialog.error('请选择行');
       
    	}else{
            
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		
            		var ParamVo =[];

                    $(data).each(function (){
        			
                    	if(this.rate_id ==null){ 
                    		
                    		grid.deleteSelectedRow();
                    		
                    	}else{
                    		
                    		ParamVo.push(
                    				
                    				this.rate_id 
                    				)
                    	}
        				
                    });
                    
                    if(ParamVo.length==0){return; };
            		
                	ajaxJsonObjectByUrl("../accwagetax/deleteAccWageTax.do?ParamVo="+ParamVo,{},function (responseData){
                		if(responseData.state=="true"){
                			query();
                			is_addRow();
                		}
                	});
            	}
            }); 
        }
    }
    
    function is_addRow()
    {
    	
    	setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 2000);
    	
    } 
    
    function onKeyPrice(t)
    {
       var stmp = "";
       if(t.value==stmp) return;
       var ms = t.value.replace(/[^\d\.]/g,"").replace(/(\.\d{2}).+$/,"$1").replace(/^0+([1-9])/,"$1").replace(/^0+$/,"0");
       var txt = ms.split(".");
       while(/\d{4}(,|$)/.test(txt[0]))
         txt[0] = txt[0].replace(/(\d)(\d{3}(,|$))/,"$1,$2");
       t.value = stmp = txt[0]+(txt.length>1?"."+txt[1]:"");
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="layout1">
            <div position="left" title="起征点设置" ">
            
	             <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			       <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:40px;padding-top:80px;"><b><font color="red">*</font></b>起 征 点：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:80px;"><input style="text-align: right" name="tax_value" type="text" id="tax_value" ltype="text" value="${tax_value}" validate="{required:true,maxlength:18}" onkeyup="onKeyPrice(this);"/></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:40px;padding-top:80px;"><b><font color="red">*</font></b>附加起征点：</td>
			            <td align="left" class="l-table-edit-td" style="padding-top:80px;"><input style="text-align: right" name="ass_value" type="text" id="ass_value"  ltype="text" value="${ass_value}" validate="{required:true,maxlength:18}" onkeyup="onKeyPrice(this);"/></td>
			            <td align="left"></td>
			        </tr> 
	   			  </table>
	   			  <div style="padding-top: 50px;padding-left: 150">
	   			  <input class="l-button" style="width: 80px;" type="button" id="ass_value"  value="保存(S)" onclick="save_tax_set();"/>
   			  </div>
            
            </div>
            <div position="center"  title="税率设置">
            <div style="border:1px;padding-top: 10;padding-bottom: 5px">
			<input class="l-button" style="width: 80px;" type="button" value=" 查询（Q）" onclick="query();"/>
			<input class="l-button" style="width: 80px;" type="button" value=" 删除（D）" onclick="delete_btn();"/>
			<input class="l-button" style="width: 80px" type="button" value=" 保存（S）" onclick="endEdit();"/>
			</div>
	   		<div id="maingrid"></div>
            </div>
        </div> 
	
</body>
</html>
