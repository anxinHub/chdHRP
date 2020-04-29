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
        query();
		loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
          grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
    	  grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
    	  grid.options.parms.push({name:'pre_pay_no',value:$("#pre_pay_no").val()}); 
    	  grid.options.parms.push({name:'state',value:$("#state").val()}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]});
    	  grid.options.parms.push({name:'pay_bill_type',value:$("#pay_bill_type").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '单据号', name: 'pre_pay_no', align: 'left',width:120 ,
					 			render:function(rowdata,index,value){
					 				if(rowdata.pay_bill_type == 1){
					 					return "<a href=javascript:openUpdate('"+
				 						rowdata.group_id+"|"+
				 						rowdata.hos_id+"|"+
				 						rowdata.copy_code+"|"+
				 						rowdata.pre_pay_id+"|"+
				 						rowdata.pre_pay_no+"|"+
				 						rowdata.state+"')>"+rowdata.pre_pay_no+"</a>";
					 				}else{
					 					return "<a href=javascript:openUpdate('"+
				 						rowdata.group_id+"|"+
				 						rowdata.hos_id+"|"+
				 						rowdata.copy_code+"|"+
				 						rowdata.pre_pay_id+"|"+
				 						rowdata.pre_pay_no+"|"+
				 						rowdata.state+"')>"+rowdata.pre_pay_no+"<font color='red'>(退)</font></a>";
					 				}
					 				
					 			}
					 		},
                     { display: '付款日期', name: 'pay_date', align: 'left',width:100
					 		},
                     { display: '供应商', name: 'sup_name', align: 'left',width:'20%'
					 		},
				 	 { display: '付款金额', name: 'pre_pay_money', align: 'right',
					 			render:function(rowdata,rowindex,value){
					 				return formatNumber(rowdata.pre_pay_money ,'${p08005 }', 1);
					 			}
					 		},
					 { display: '制单人', name: 'maker_name', align: 'left'
					 		},
					 { display: '制单日期', name: 'make_date', align: 'left',width:100
					 		},
                     { display: '审核人', name: 'checker_name', align: 'left',width:100
					 		},
                     { display: '审核日期', name: 'chk_date', align: 'left',width:100
					 		},
                     { display: '状态', name: 'state', align: 'left',width:120 ,
					 			render:function(rowdata,index,value){
					 				if(rowdata.state == 1){
					 					return "未审核";
					 				}else if (rowdata.state == 2){
					 					return "已审核";
					 				}else{
					 					return "记账";
					 				}
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPrePayMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,isScroll :true ,
                     delayLoad: true,//初始化不加载，默认false
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
    	                /* { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true }, */
		                { text: '审核（<u>Z</u>）', id:'audit', click: audit, icon:'bluebook' },
						{ line:true },
						{ text: '消审（<u>U</u>）', id:'unaudit', click: unaudit,icon:'bookpen' },
						{ line:true },
						{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
						{ line:true } ,
						{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.pre_pay_id  + "|" + 
								rowdata.pre_pay_no  + "|" + 
								rowdata.state 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	$.ligerDialog.open({ url : 'medPrePayMainAddPage.do?isCheck=false',data:{}, 
			height: 500,width: 1000, title:'添加预付款单',modal:true,showToggle:false,showMax:true,
			showMin: true,isResize:true, top: 0
    		}); 
    	
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){	
            	if(this.state ==1){
            		ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code  +"@"+ 
				this.pre_pay_id    +"@"+ 
				this.pre_pay_no 
				) 
            	}else{
            		$.ligerDialog.error('只有未审核状态的预付款单允许删除');
            	}
           })
           if(ParamVo.length >0){
				$.ligerDialog.confirm('确定删除?', function (yes){
                   	if(yes){
                       	ajaxJsonObjectByUrl("deleteMedPrePayMain.do",{ParamVo : ParamVo.toString()},function (responseData){
                       		if(responseData.state=="true"){
                       			query();
                       		}
                       	});
                   	}
                }); 
			}
        }
    }
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medPrePayMainImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"pre_pay_id="+vo[3]	+"&"+ 
			"pre_pay_no="+vo[4] +"&"+ 
			"state="+vo[5] 
			$.ligerDialog.open({ url : 'medPrePayMainUpdatePage.do?isCheck=false&'+parm,data:{}, 
				height: 650,width: 1000, title:'修改',modal:true,showToggle:false,showMax:true,
				showMin: true,isResize:true,top: 0
				});
		}
 		
     /* var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medPrePayMainImportPage.do?isCheck=false&' + parm
				});
				layer.full(index);	
     */
    
    //审核
    function audit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 if(this.state != 1){
        			 str += this.pre_pay_no +',';
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.pre_pay_id +"@"+ this.pre_pay_no +"@"+this.pay_date+"@"+2
					)};
        	 })
        if(str != ''){
        	$.ligerDialog.error('<span style="color:red">审核失败,单据号:'+str+'不是未审核状态！</span>(只有未审核状态的预付款单允许审核 )');
        }else{
        	 $.ligerDialog.confirm('确定审核所选预付款单吗?', function (yes){
	             	if(yes){
	                 	ajaxJsonObjectByUrl("updateMedPrePayMainState.do",{ParamVo : ParamVo.toString()},function (responseData){
	                 		if(responseData.state=="true"){
	                 			loadHead(null);
	                 		}
	                 	});
	             	}
	             }); 
        	}
        }
    }
    //消审
    function unaudit(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
    	var str = '';
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	 $(data).each(function (){
        		 if(this.state != 2){
        			 str += this.pre_pay_no +",";
         		}else{
					ParamVo.push(
						this.group_id   +"@"+ this.hos_id   +"@"+ this.copy_code   +"@"+ this.pre_pay_id +"@"+ this.pre_pay_no +"@"+this.pay_date+"@"+ 1
					)};
        	 })
        	
	        if(str != ''){
	        	$.ligerDialog.error('<span style="color:red">消核失败,单据号:'+str+'不是已审核状态！</span>(只有已审核状态的预付款单允许消审)');
	        }else{
	        	 $.ligerDialog.confirm('确定消审所选预付款单吗?', function (yes){
	             	if(yes){
	                 	ajaxJsonObjectByUrl("updateMedPrePayMainState.do",{ParamVo : ParamVo.toString()},function (responseData){
	                 		if(responseData.state=="true"){
	                 			loadHead(null);
	                 		}
	                 	});
	             	}
		        });
	        }
        }
    }  
    function loadDict(){
            //字典下拉框
	    	//供应商下拉框
			autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',360);
			//科室：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
			//autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
			//采购员下拉框
			//autocomplete("#stocker", "../../queryMedStoctEmpDict.do?isCheck=false", "id", "text", true, true);
			autodate("#beginDate", "yyyy-mm-dd", "month_first");
	    	autodate("#endDate", "yyyy-mm-dd", "month_last");
	    	
			$("#beginDate").ligerTextBox({width:100});
	        $("#endDate").ligerTextBox({width:100});
			$("#pre_pay_no").ligerTextBox({width:160});
	        $("#state").ligerTextBox({width:160});
			$("#sup_id").ligerTextBox({width:220});
	        $("#pay_bill_type").ligerTextBox({width:160});
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		//hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		
		hotkeys('Z', audit);

		hotkeys('U', unaudit);
		
		//hotkeys('I', imp);
		

	 }
	//打印设置
		function printSet(){
			
			
			var useId=0;//统一打印
			if('${p08017 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}else if('${p08017 }'==2){
				//按仓库打印
				if(liger.get("store_code").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("store_code").getValue().split(",")[0];
			}
			
			officeFormTemplate({template_code:"08025",user_id:useId});
			
			/**
			parent.parent.$.ligerDialog.open({url : 'hrp/med/medprepay/medprepaymain/prePayPrintSetPage.do?isCheck=false&template_code=08025&use_id='+useId,
				data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			});*/
		}
		
	    //打印
	    function print(){
	    	
	    	 var useId=0;//统一打印
	 		if('${p08017 }'==1){
	 			//按用户打印
	 			useId='${sessionScope.user_id }';
	 		}else if('${p08017 }'==2){
	 			//按仓库打印
	 			if(liger.get("store_code").getValue()==""){
	 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
	 				return;
	 			}
	 			useId=liger.get("store_code").getValue().split(",")[0];
	 		}

	    	//if($("#create_date_b").val())
	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				
				var pre_pay_id ="" ;
				var in_nos = "";
				$(data).each(function (){		
					if(this.state != 2){
						in_nos = in_nos + this.in_no + "<br>";
					}
					
					pre_pay_id  += this.pre_pay_id+","
						
				});
				 
				 var para={
							paraId :pay_id.substring(0,pay_id.length-1) ,
							template_code:'08025',
							class_name:"com.chd.hrp.med.serviceImpl.medprepay.MedPrePayMainServiceImpl",
							method_name:"queryPrePayByPrintTemlateNew",
							isSetPrint:false,//是否套打，默认非套打
							isPreview:true,//是否预览，默认直接打印
							use_id:useId,
							p_num:1
					};
					
					officeFormPrint(para);
				
		    	//printTemplate("hrp/med/medprepay/medprepaymain/queryPrePayByPrintTemlate.do?isCheck=false",para);
		    	
			}
	    	
	    }
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","预付款单.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
				usePager:false,
				beginDate:$("#beginDate").val(),
				endDate:$("#endDate").val(),
				pre_pay_no:$("#pre_pay_no").val(),
				state:$("#state").val(),
				sup_id:liger.get("sup_id").getValue().split(",")[0],
				pay_bill_type:$("#pay_bill_type").val()
	         };
			ajaxJsonObjectByUrl("queryMedPrePayMain.do?isCheck=false",exportPara,function (responseData){
				 var trHtml='';
				$.each(responseData.Rows,function(idx,item){ 
					 	 trHtml+="<tr>";
						 trHtml+="<td>"+item.pre_pay_no+"</td>"; 
						 trHtml+="<td>"+item.pay_date+"</td>"; 
						 trHtml+="<td>"+item.sup_name+"</td>"; 
						 trHtml+="<td>"+item.pre_pay_money+"</td>"; 
						 trHtml+="<td>"+item.maker_name+"</td>";
						 trHtml+="<td>"+item.make_date+"</td>";
						 trHtml+="<td>"+item.checker_name+"</td>"; 
						 trHtml+="<td>"+item.chk_date+"</td>"; 
						 if(item.state ==1){
							 trHtml+="<td>未审核</td>"; 
						 }else if (item.state == 2){
							 trHtml+="<td>已审核</td>";
						 }else{
							 trHtml+="<td>记账</td>";
						 }
						 
					 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","预付款单.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
    	<tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款日期：</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            			</td>
            		</tr>
				</table>
	        </td>  
            <!-- <td align="left" class="l-table-edit-td" style="width: 160">
            	<input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            </td>
            <td align="left" style="width: 15">至：</td>
            <td align="left" style="width: 160">
            	<input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            </td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据号：</td>
            <td align="left" class="l-table-edit-td"><input name="pre_pay_no" type="text" id="pre_pay_no" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
             <td align="left" class="l-table-edit-td">
            	<select name="state" id="state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="1">未审核</option>
                		<option value="2">审核</option>
                		<option value="3">记账</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"  ><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预付款类别：</td>
            <td align="left" class="l-table-edit-td">
	            <select name="pay_bill_type" id="pay_bill_type"style="width: 135px;" >
	            			<option value="">请选择</option>
	                		<option value="1">预付款单</option>
	                		<!-- <option value="0">退款单</option> -->
	            </select>
	            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">预付款单号</th>	
                <th width="200">付款日期</th>	
                <th width="200">供应商ID</th>	
                <th width="200">付款金额</th>
                <th width="200">制单人</th>	
                <th width="200">制单日期</th>
                <th width="200">审核人</th>	
                <th width="200">审核日期</th>
                <th width="200">状态</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
