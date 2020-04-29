<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script>
var is_check =  [{ text: '全部', id: '' },{ text: '否', id: '0' },{ text: '是', id: '1' }]
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
        grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
        grid.options.parms.push({name:'begin_paper_num',value:$("#begin_paper_num").val()}); 
        grid.options.parms.push({name:'end_paper_num',value:$("#end_paper_num").val()}); 
        grid.options.parms.push({name:'is_check',value:liger.get("is_check").getValue()}); 
        
        grid.options.parms.push({name:'paper_way_type',value:'1'}); 
        grid.options.parms.push({name:'state1',value:'2,3'}); 
        
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '票据号码', name: 'paper_num', align: 'left',width:120},
   	  				 { display: '核销状态', name: 'is_check', align: 'left',
                    	render : function(rowdata, rowindex,value) {
 	  					    if(rowdata.is_check == 0){
	  						    return "未核销"
	  					      }else {
	  						    return "已核销"
	  					}
	  				   }	
                     },
                     { display: '领用日期', name: 'out_date1', align: 'left'},
                     { display: '核销人', name: 'check_user_name', align: 'left'},
                     { display: '核销日期', name: 'check_date', align: 'left'},
                     { display: '核销金额', name: 'money', align: 'right',editor: { type: 'text' },
                    	 render:function(rowdata){
  							    return formatNumber(rowdata.money,2,1);
 						 }
                      },
                     { display: '凭证号', name: 'vouch_no', align: 'left',editor: { type: 'text' },
                    	  render:function(rowdata){
                    		   if(rowdata.check_vouch_id != null && rowdata.vouch_no != null){
                    			   return "<a href=javascript:openSuperVouch('"+rowdata.vouch_id+"')><div>"+rowdata.vouch_type_short+"-"+rowdata.vouch_no+"</div></a>";
                    		   }
                    		   return rowdata.vouch_no;
                    	  }
                    	},
                     { display: '备注说明', name: 'note', align: 'left',editor: { type: 'text' },
                    	render:function(rowdata){
                    		if(rowdata.note == 'null'){
                    			rowdata.note = "";
                    		}
                    		return rowdata.note;
                    	}		
                     },
                     { display: '是否作废', name: 'state1', align: 'left',
                     	render:function(rowdata){
                     		if(rowdata.state1 == '3'){
                     			return "作废";
                     		}
                     		//return rowdata.note;
                     	}		
                      }
                     /* ,
                     { display: '自动核销', name: 'is_auto', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
   	  					    if(rowdata.is_auto == 0){
  	  						    return "否"
  	  					      }else {
  	  						    return "是"
  	  					}
  	  				   }
                     },
                     { display: '手工核销', name: 'is_sd', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
  	  					    if(rowdata.is_sd == 0){
 	  						    return "否"
 	  					      }else {
 	  						    return "是"
 	  					}
 	  				   }
                     } */
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperDetailSola.do',delayLoad:true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,enabledEdit: true//heightDiff: -10,
                     ,lodop:{
    	         		title:"单张票据核销",
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
    
    function verification(){
    	var data = gridManager.getCheckedRows();
		 if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         	return;
         }else{
             var ParamVo =[];
             var paper_numMoney = "";
             var paper_numNo = "";
             $(data).each(function (){	
            	if (this.money == "" || this.money == null  || this.money == 'undefined') {  
            		paper_numMoney = paper_numMoney + this.paper_num + ","
  	 			}
            	if (this.vouch_no == "" || this.vouch_no == null  || this.vouch_no == 'undefined') {  
            		paper_numNo = paper_numNo + this.paper_num + ","
  	 			}
				ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code +"@"+
					this.pid   +"@"+
					this.paper_num +"@"+
					"1" +"@"+
					this.is_check+"@"+
					this.money   +"@"+
					this.vouch_no+"@"+
					this.note
					)
             });
			 
            if(paper_numMoney != ""){
 				$.ligerDialog.error("核销失败！ 单据"+paper_numMoney+" 核销金额不能为空！");
 				return;
 			}
            if(paper_numNo != ""){
 				$.ligerDialog.error("核销失败！ 单据 "+paper_numNo+" 凭证号不能为空！");
 				return;
 			}
             $.ligerDialog.confirm('是否确认核销?', function (yes){
            	 
             	 if(yes){
             		 
             		//$.ligerDialog.open({ url:'updateAccPaperDetailSolaVerification.do?isCheck=false&paramVo='+ParamVo,data:{},height: 300,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccPayType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
             		 ajaxJsonObjectByUrl("updateAccPaperDetailSolaVerification.do",{ParamVo : ParamVo.toString()},function (responseData){
             	  		
             			 if(responseData.state=="true"){
             	  			
             				 parent.query();
             	  		
             			 }
             	  	    
             		 }); 
             	
             	 } 
            
             }); 
         
         }
		 
    }
    //打印回调方法
    function lodopPrint(){
   
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td>";
 		grid.options.lodop.head=head;
    }
    function CancelVerification(){
    	
    	var data = gridManager.getCheckedRows();
		 if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code +"@"+
					this.pid   +"@"+
					this.paper_num +"@"+
					"0" +"@"+
					this.is_check +"@"+
				    (this.vouch_id == null?"vouch_id":this.vouch_id)
					)
            });
            $.ligerDialog.confirm('是否确认取消核销?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("updateAccPaperDetailSolaCancelVerification.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    	
    }
    
    function Nullify(){
    	   var data = gridManager.getCheckedRows();
			 if (data.length == 0){
	         	$.ligerDialog.error('请选择行');
	         }else{
	             var ParamVo =[];
	             $(data).each(function (){					
						ParamVo.push(
						//表的主键
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code +"@"+
						this.pid   +"@"+
						this.paper_num +"@"+
						(this.vouch_id == null?"vouch_id":this.vouch_id)
						)
	             });
	             $.ligerDialog.confirm('是否确认作废?', function (yes){
	             	if(yes){
	                 	ajaxJsonObjectByUrl("updateAccPaperDetailSolaNullify.do",{ParamVo : ParamVo.toString()},function (responseData){
	                 		if(responseData.state=="true"){
	                 			query();
	                 		}
	                 	});
	             	}
	             }); 
	         }
    }
    
    function CancelNullify(){
    	
    	var data = gridManager.getCheckedRows();
		 if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code +"@"+
					this.pid   +"@"+
					this.paper_num +"@"+
					(this.vouch_id == null?"vouch_id":this.vouch_id)
					)
            });
            $.ligerDialog.confirm('是否确认作废?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("updateAccPaperDetailSolaCancelNullify.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    	
    }
    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
    function loadDict(){
    	
    	autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);

            //字典下拉框
         $("#is_check").ligerComboBox({  
             data: is_check,
             width:160,
         });
            
    	 $(':button').ligerButton({width:80});
            
    	 autodate("#begin_date","yyyy-MM-dd");
    	 var date = $("#begin_date").val();
    	 $("#begin_date").val(date.substr(0,date.length-2) + "01");

         autodate("#end_date","yyyy-MM-dd"); 	
    	 
         $("#begin_date").ligerTextBox({width:120});
    	 
    	 $("#end_date").ligerTextBox({width:120});
    	 
         $("#begin_paper_num").ligerTextBox({width:120});
    	 
    	 $("#end_paper_num").ligerTextBox({width:120});
    	 
    	 
         }   
    
/* 	function myPrint(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		
   		var printPara={
   			rowCount:1,
   			title:'票据二次领用查询',
   			type:1,//表单级数据绑定，适用于单表头
   			columns:grid.getColumns(1)
   			};
   		ajaxJsonObjectByUrl("queryAccPaperDetailSola.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
    } */
	
	function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"购置日期："+$("#begin_date").val()+"至"+$("#end_date").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'单张票据核销',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.paper.AccPaperDetailService",
			method_name: "queryAccPaperDetailSolaPrint",
			bean_name: "accPaperDetailService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
		
	officeGridPrint(printPara); 
	
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置日期：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_date" class="Wdate" id="begin_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td">至</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" class="Wdate" id="end_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据号：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_paper_num" type="text" id="begin_paper_num" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td">至</td>
            <td align="left" class="l-table-edit-td"><input name="end_paper_num" type="text" id="end_paper_num" ltype="text"  /></td>
            <td align="left"></td>
    </tr>
       <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否核销：</td>
            <td align="left" class="l-table-edit-td"><input name="is_check" type="text" id="is_check" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询" onclick="query();"/>
    <input  type="button" value=" 核销" onclick="verification();"/>
    <input  type="button" value=" 取消核销" onclick="CancelVerification();"/>
    <input  type="button" value=" 作废" onclick="Nullify();"/>
    <input  type="button" value=" 取消作废" onclick="CancelNullify();"/>
    <!-- <input  type="button" value=" 转换" onclick="myPrint();"/> -->
    <input  type="button" value=" 打 印" onclick="printDate();"/>  
	</div>
	<div id="maingrid"></div>

</body>
</html>
