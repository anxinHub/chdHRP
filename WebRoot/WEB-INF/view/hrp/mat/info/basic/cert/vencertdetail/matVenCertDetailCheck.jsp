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
    	loadHead();	
		loadHotkeys();
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
            columns: [ 
					{ display: '供应商', name: 'sup_name', align: 'left', width:'10%'},
	                { display: '证件编码', name: 'cert_code', align: 'left', width:'10%'
	                 	  /* render: function(rowdata,index,value){
	                 		  return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.cert_code+"|"+rowdata.sup_id+"')>"+rowdata.cert_code+"</a>";
	                 	  } */
				 	},
                    { display: '起始日期', name: 'start_date', align: 'left',width:'8%'
				 		},
                    { display: '结束日期', name: 'end_date', align: 'left',width:'8%'
				 		},
                    { display: '证件类型', name: 'type_name', align: 'left', width:'10%'},
				  	{ display: '发证机关', name: 'issuing_authority', align: 'left',width:'20%'
				 		},
				  	{ display: '是否停用', name: 'cert_state', align: 'left',width:'8%',
				 			render:function(rowdate,rowindex,value){
				 				if(rowdate.cert_state == 1){
				 					return "在用";
				 				}else{
				 					return "停用";
				 				}
				 			}
				 	},
				 	{ display: '审核状态', name: 'state', align: 'left',width:'8%',
			 			render:function(rowdate,rowindex,value){
			 				if(rowdate.state == 1){
			 					return "已审核";
			 				}else if(rowdate.state == 2){
			 					return "驳回";
			 				}else{
			 					return "未审核";
			 				}
			 			}
			 		},
			 		{ display: '下载', name: 'file_path', align: 'left',width:'5%',
			 			render: function(rowdata,index,value){
		            		  if(!rowdata.file_path){
		            			  return '<a>下载</a>';
		            		  }else{
		            			  var array = rowdata.file_path.split('/');
			            		  var name = array[array.length-1];
			                      return '<a href='+value+' download='+name+'>下载</a>';
		            		  }
		            		 
	                 	  }
			 		},
				  	{ display: '备注', name: 'cert_memo', align: 'left',width:'17%'}
				 	],
                    dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatVenCertDetailCertCheck.do?isCheck=false',
                    width: '100%',  height: '100%', checkbox: true,rownumbers:true, allowAdjustColWidth:false,
	                    heightDiff: 0,
	                    toolbar: { items: [
	                    	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
		                   	{ line:true },
		  					{ text: '审核（<u>A</u>）', id:'audit', click: auditVenCert, icon:'audit' },
		  	                { line:true },
		  	             	{ text: '消审（<u>U</u>）', id: 'unAudit', click: unAuditVenCert, icon: 'unaudit' },
		  	             	{ line:true },
		  					{ text: '驳回（<u>T</u>）', id:'confirm', click: confirmVenCert, icon:'before' },
		  					{ line:true },
		  					{ text: '取消驳回（<u>B</u>）', id:'unConfirm', click: unConfirmVenCert, icon:'next' },
   						]},
	   				onDblClickRow : function (rowdata, rowindex, value)
	   				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.cert_code   + "|" + 
								rowdata.sup_id 
							);
	   				} 
               });

         gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //审核
	function auditVenCert(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 0){
				str += this.sup_name+'-证件:'+this.cert_code +",";
			}
			ids += this.sup_id +'@'+this.cert_code +'@1'+ ",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】不是未审核状态不能审核！");
			return false;
		}
		$.ligerDialog.confirm('确定审核所选供应商证件吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateVenCertDetailState.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							grid.updateRow(this, {state: 1});
						})
					}
				});
			}
		}); 
	}
	
	//消审
	function unAuditVenCert(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 1){
				str += this.sup_name+'-证件:'+this.cert_code +",";
			}
			ids += this.sup_id +'@'+this.cert_code + '@0'+",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】未审核不能消审！");
			return false;
		}
		$.ligerDialog.confirm('确定消审所选供应商证件吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateVenCertDetailState.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							grid.updateRow(this, {state: 0});
						})
					}
				});
			}
		}); 
	}
	
	//驳回
	function confirmVenCert(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 0){
				str += this.sup_name+'-证件:'+this.cert_code +",";
			}
			ids += this.sup_id +'@'+this.cert_code + '@2'+",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】不是未审核状态不能驳回！");
			return false;
		}
		$.ligerDialog.confirm('确定驳回所选供应商证件吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateVenCertDetailState.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							grid.updateRow(this, {state: 2});
						})
					}
				});
			}
		}); 
	}
	
	//取消驳回
	function unConfirmVenCert(){
		var data = gridManager.getCheckedRows();
		var ids = "";
		var str = '';
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}
		
		$.each(data, function (){
			if(this.state != 2){
				str += this.sup_name+'-证件:'+this.cert_code +",";
			}
			ids += this.sup_id +'@'+this.cert_code + '@0'+",";
		})
		
		if(str != ''){
			$.ligerDialog.error("【"+str.substr(0, str.length - 1)+"】不是驳回状态不能驳回！");
			return false;
		}
		$.ligerDialog.confirm('确定驳回所选供应商证件吗?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("updateVenCertDetailState.do?isCheck=false",{ParamVo: ids.substr(0, ids.length-1)},function (res){
					if(res.state=="true"){
						$.each(data, function (){
							grid.updateRow(this, {state: 0});
						})
					}
				});
			}
		}); 
	}
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"cert_code="+vo[3]   +"&"+ 
			"sup_id="+vo[4] 
		 
		$.ligerDialog.open({url: 'matVenCertDetailUpdatePage.do?isCheck=false&'+ parm, height: 440,width: 800, title:'供应商证件明细修改',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true})
			
	}
    function loadFile(obj){
    	
    }
    function loadDict(){
            //字典下拉框
            //供货单位
    		autocomplete("#sup_id", "../../../../queryHosSupDict.do?isCheck=false", "id","text", true, true);
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', auditVenCert);
		hotkeys('U', unAuditVenCert);
		
		hotkeys('T', confirmVenCert);
		hotkeys('B', unConfirmVenCert);



	 }
   
    </script> 

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商编码：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
