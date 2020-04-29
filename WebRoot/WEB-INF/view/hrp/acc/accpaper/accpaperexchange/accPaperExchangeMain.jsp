<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">	
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var show_sum = 0;
    
    
    //页面初始化
    $(function (){
		/* loadDict(); */
		loadButton();
    	loadHead(null);	//加载数据
    	loadHotkeys();
    	
    });
    
    
    
    //查询
    function  query(){
   		/* grid.options.parms=[];
   		grid.options.newPage=1;
   		//grid.options.parms.push({name:'subj_id',value:liger.get("subj_code").getValue().split(".")[0]}); 
   		
   		grid.options.parms.push({name:'subj_code',value:$("#subj_code").val().split(" ")[0]});
       	if(liger.get("is_check").getValue() !=""){
       		grid.options.parms.push({name:'is_check',value:liger.get("is_check").getValue()}); 
       	} */
   		//加载查询条件
   		grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '汇率编码', name: 'RATE_CODE', align: 'left'

					,render:function(rowdata,index,value){
                   		return "<a href=javascript:openUpdate('"+
                   		rowdata.RATE_CODE+"|"+
                   		rowdata.RATE_NAME+"|"+
    					rowdata.RATE+"|"+
    					rowdata.IS_STOP+"|"+
    					rowdata.NOTE+"')>"+value+"</a>";
					}

				},
				
				{ display: '汇率名称', name: 'RATE_NAME', align: 'left'},
				
				{ display: '汇率', name: 'RATE', align: 'left'},
				
				{ display: '是否停用', name: 'IS_STOP', align: 'left'
					,render:function(rowdata,index,value){
                   		if(value == 1){
							return "是";
						}else if(value == 2){
							return "否";
						}
							return "";
					}
				},
				
				{ display: '备注', name: 'NOTE', align: 'center',formatter:'###,##0.00'
					,render:function(rowdata,index,value){
                   		if(value != null && value != ""){
                   			return value;
						}else{
							return "";
						}
					}
				}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccExchange.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			selectRowButtonOnly:true ,//heightDiff: -10,
			toolbar: { 
				items: [
            		{ text: '添加', id:'save', click: addLeder,icon:'add' },
                	{ line:true },
                	                
                	{ text: '查询', id:'query', click: query,icon:'search' },
                	{ line:true },
                	
                	{ text: '删除', id:'delete', click: deletes,icon:'delete' },
                	{ line:true }
				]
			},
			onDblClickRow : function (rowdata, rowindex, value){
				openUpdate(
					rowdata.RATE_CODE+"|"
					+rowdata.RATE_NAME+"|"
					+rowdata.RATE+"|"
					+rowdata.IS_STOP+"|"
					+rowdata.NOTE
				);
			} ,
			lodop:{
      	    	title:"科目初始账",fn:{
      	    		debit:function(value){//借方
    	          		if(value == 0){
    	          			return "";
    	          		}else{
    	                	return formatNumber(value, 2, 1);
    	                }
    	          	},
					credit:function(value){//贷方
     	          		if(value == 0){
     	          			return "";
     	          		}else{
     	          			return formatNumber(value, 2, 1);
     	          		}
					},
      	         	end_os:function(value){//余额
      	      	   		if(value==0){
      	      	   			return "Q";
      	      	   		}else{
      	      	   			return formatNumber(value, 2, 1);
      	      	   		}
      	        	}
				}
			}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    
    
    //添加页跳转
    function addLeder(){
    	$.ligerDialog.open({
    		url: 'accPaperExchangeAddPage.do?isCheck=false', 
    		height: 238,width: 342, title:'添加',modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) {
    				var code = dialog.frame.yanzheng();
					if(code){
						return false;
					}
    					var exchangeAddFormJava = dialog.frame.$("#exchangeAddForm").serialize()
    					ajaxJsonObjectByUrl("addPaperExchange.do",exchangeAddFormJava,function(responseData){
    						if(responseData.state == "true"){
    							dialog.close();
        						query();
    			            }
    			        });
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick:
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
    
    function deletes(){
    	
    	var gridfrom = gridManager.getCheckedRows();
    	
    	if(gridfrom != null && gridfrom.length > 0){
    		var arrid = "";
    		$.each(gridfrom,function(){
    			arrid += ",'"+this.RATE_CODE+"'";
    		})
    		arrid = arrid.substr(1);
			ajaxJsonObjectByUrl("deletePaperExchange.do?isCheck=false",{'arrid':arrid},function(responseData){
					query();
	        });
    	}else{
    	}
    	
    }
    
  //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
  
  
  	//修改页跳转
    function openUpdate(obj){
  		
		var vo = obj.split("|");
		var mapVo =
			"RATE_CODE="+ vo[0] +
		 	"&RATE_NAME=" + vo[1]+
		 	"&RATE=" + vo[2] +
		 	"&IS_STOP="+ vo[3] +
		 	"&NOTE="+ vo[4]+
		 	"&code=1";
		
		$.ligerDialog.open({
    		url: 'accPaperExchangeAddPage.do?isCheck=false&'+mapVo, 
    		height: 238,width: 342, title:'修改',modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) {
    				var code = dialog.frame.yanzheng();
					if(code){
						return false;
					}
    				dialog.frame.$("#rate_code").prop('disabled',false);
    					var exchangeAddFormJava = dialog.frame.$("#exchangeAddForm").serialize()
    					ajaxJsonObjectByUrl("updatePaperExchange.do",exchangeAddFormJava,function(responseData){
    						if(responseData.state=="true"){
    			                query();
    			                dialog.close();
    			            }
    			        });
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick:
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
		
    }
  	
	function loadButton(){
		$("#query").ligerButton({click: query, width:90});
	}

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<!-- <div id="topmenu"></div> -->
<!--     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否辅助核算：</td>
            <td align="left" class="l-table-edit-td"><input type="text" id="is_check"/></td>
            <td align="left"></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="query" value="查询(Q)"/>
			</td>
			<td align="left"></td>
            
        </tr> 
    </table> -->

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
		<table width="100%">
			   	<thead>
				<tr>
                <th width="200">汇率编码</th>	
                <th width="200">汇率名称</th>	
                <th width="200">汇率</th>
                <th width="200">是否停用</th>
                <th width="200">备注</th>	
				 </tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
	</div>
</body>
</html>
