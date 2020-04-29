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
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var dataType = {
    		Rows : [{
 				"id" : "1",
 				"text" : "是" 
 			},{
 				"id" : "0",
 				"text" : "否" 
 			}],
 			Total : 2
    };
    var docType = {
    		Rows : [{
 				"id" : "",
 				"text" : "全部" 
 			},{
 				"id" : "0",
 				"text" : "门诊" 
 			},{
 				"id" : "1",
 				"text" : "住院" 
 			}],
 			Total : 3
    };
    
    $(function ()
    {
    	 loadHead(null);
         loadDict();//加载下拉框
		// loadHotkeys();
		 var dataGrid = [
				{ id: 1, name: '按单据生成'},
				{ id: 3, name: '按日期生成'},
				{ id: 4, name: '按汇总生成'}//只能按日期、库房生成
             ]; 
         $("#initType").ligerRadioList({
             data: dataGrid,
             textField: 'name'
         });
         liger.get("initType").setValue("1");
         $(':button').ligerButton({width:70});
         
        
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		var busi_type_code = liger.get("acc_busi_type").getValue();
		var mod_code = '08';
		
		if(""==busi_type_code){
        	
        	$.ligerDialog.error('请选择业务类型');
        	return;
        }
		
		
		grid.options.parms.push({
			name : 'busi_type_code',
			value : busi_type_code.split("@")[0]
		});
		
		grid.options.parms.push({
			name : 'mod_code',
			value : mod_code
		});
		
		grid.options.parms.push({
			name : 'busi_date_b',
			value : $("#busi_date_b").val()
		});
		grid.options.parms.push({
			name : 'busi_date_e',
			value : $("#busi_date_e").val()
		});
		grid.options.parms.push({
			name : 'business_no_b',
			value : $("#business_no_b").val()
		});
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue().split(".")[0]
		});
		 grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_id").getValue().split(".")[0]
		});
		 
		 
		
		if(busi_type_code.split("@")[0]=="080101" || busi_type_code.split("@")[0]=="080102"){
			grid.options.parms.push({
				name : 'bill_state',
				value : liger.get("bill_state").getValue()
			});
			if(busi_type_code.split("@")[0]=="080101"){
				grid.options.parms.push({
					name : 'med_type_code',
					value : liger.get("med_type_code").getValue()
				});
			}
		}
		
		if(busi_type_code.split("@")[0]=="080104" || busi_type_code.split("@")[0]=="080106"){
			grid.options.parms.push({
				name : 'doc_type',
				value : liger.get("doc_type").getValue()
			});
		}
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAutoVouch.do',
			width: '100%', height: '100%', rownumbers:true,checkbox: true,checkBoxDisplay : isCheckDisplay,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true//,heightDiff: -10
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    var columnsData;
    function f_setColumns() {
    	
    	var mod_code = '08';
    	var acc_busi_type = liger.get("acc_busi_type").getValue().split("@")[0];
    	
		$.post("queryMedAutoVouchHead.do?isCheck=false", {
			"mod_code" : mod_code,
			"busi_type_code":acc_busi_type
		}, function(data) {
			var columns = [];
			
			$.each(data.Rows, function(i, v) {
				var name_dis = v.head_name.split(",");
				for(var i = 0 ; i < name_dis.length; i++){
					var value_name = name_dis[i].split("|")[0];
					var display_name = name_dis[i].split("|")[1];
					
					
					if(i==0){
						columns.push({display:"凭证号",name:"VOUCH_NO",width:100,align : 'left',frozen : true,render:function(rowdata){
							if(rowdata.VOUCH_NO != null){
								if(rowdata.VOUCH_NO=='-'){
									return rowdata.VOUCH_NO;
								}else{
									return "<a href=javascript:openSuperVouch('"+rowdata.VOUCH_ID+"')>"+rowdata.VOUCH_NO+"</a>";
								}
							}else{
								return '';
							}
							
						}});
					}
					
					if(value_name == "AMOUNT_MONEY"){
						columns.push({display:display_name,name:value_name,width: 100,align : 'right',
							render:function(rowdata){
								return formatNumber(rowdata.AMOUNT_MONEY,2,1);
						 	 } });
					}else if(value_name.indexOf("DEPT")!=-1){
						columns.push({display:display_name,name:value_name,width: 160,align : 'left'});
					}else if(value_name == "BILL_TEXT" || value_name == "BUSI_DATE"){
						columns.push({display:display_name,name:value_name,width: 80,align : 'left'});
					}else if(value_name == "HIS_SUP_NAME" || value_name == "HRP_SUP_NAME"){
						columns.push({display:display_name,name:value_name,width: 220,align : 'left'});
					}else if(value_name == "BUSINESS_NO" && (liger.get("acc_busi_type").getValue().split("@")[0]=="080101" || liger.get("acc_busi_type").getValue().split("@")[0]=="080102" || liger.get("acc_busi_type").getValue().split("@")[0]=="080110" || liger.get("acc_busi_type").getValue().split("@")[0]=="080111")){
						columns.push({display:display_name,name:value_name,width: 160,align : 'left',frozen : true,
							render:function(rowdata){
								if(rowdata.BUSINESS_NO!=null){
									return "<a href=javascript:openInBackDetail('"+rowdata.VOUCH_ID+"','"+rowdata.BUSINESS_NO+"')>"+rowdata.BUSINESS_NO+"</a>";
								}
								
						 } });
						
					}else{
						columns.push({display:display_name,name:value_name,width: 120,align : 'left'});
					}
					
				}
			});
			columnsData = data;
			grid.set('columns', columns);
			grid.reRender();
		}, "json");

	}
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
    
    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}
  
   
    function loadDict(){
		
		$("#template_code").ligerComboBox({
			width : 160
		}); 
		
		autocompleteObj({
			id:"#acc_busi_type",
          	urlStr: '../../queryBusiType.do?isCheck=false&is_vouch=1&mod_code=08',
          	valueField: 'id',
           	textField: 'text', 
          	autocomplete: true,
			defaultSelect: true,
			initWidth: 220,
			selectEvent:function(value){
				liger.get("template_code").setValue("");
				liger.get("template_code").setText("");
				autocomplete("#template_code","../../autovouch/his/charge/queryAutoBusiTemplate.do?isCheck=false&busi_type_code="+value.split("@")[0],"id","text",true,true,"",true);
				var bstype=value.split("@")[0];
				$("td[name='supTd']").hide();
				$("td[name='busiNoTd']").hide();
				$("td[name='billTd']").hide();
				$("td[name='deptTd']").hide();
				$("td[name='docTd']").hide();
				$("td[name='typeId']").hide();
				$("#business_no_b").val("");
				liger.get("sup_id").setValue("");
				liger.get("sup_id").setText("");
				liger.get("dept_id").setValue("");
				liger.get("dept_id").setText("");
				
				if(bstype=="080101" || bstype=="080102"){
					$("td[name='supTd']").show();
					$("td[name='busiNoTd']").show();
					$("td[name='billTd']").show();
					$("td[name='typeId']").show();
					
				}else if(bstype=="080109" || bstype=="080110"){
					$("td[name='supTd']").show();
					$("td[name='busiNoTd']").show();
					$("td[name='typeId']").hide();
				}else if(bstype=="080104" || bstype=="080106"){
					$("td[name='deptTd']").show();
					$("td[name='docTd']").show();
					$("td[name='typeId']").hide();
				}
				
				
				if(bstype=="080101" || bstype=="080102" || bstype=="080109" || bstype=="080110"){
					$("td[name='initTypeTd']").show();
				}else{
					$("td[name='initTypeTd']").hide();
					liger.get("initType").setValue("4");
				}
				
		        f_setColumns();
		       
			}
 		 });
		
		
		autocomplete("#med_type_code", "../../queryAccMedTypeHis.do?isCheck=false","id", "text", true, true,null,null,null,"222");
		
		autocomplete("#dept_id", "../../../sys/queryDeptDict.do?isCheck=false","id", "text", true, true,null,null,null,"222");
		autocomplete("#store_id", "../../../sys/queryStoreDictDict.do?isCheck=false","id", "text", true, true,null,null,null,"220");
		autocomplete("#sup_id", "../../../sys/querySupDictDict.do?isCheck=false","id", "text", true, true,null,null,null,"220");
		autoCompleteByData("#bill_state", dataType.Rows, "id", "text", false, true,null,false,null,220,
			function(value){
				var bstype=liger.get("acc_busi_type").getValue().split("@")[0];
				if(bstype=="080101" || bstype=="080102"){
					var data=liger.get("template_code").data;
					if(data.length>1){
						if(value==1){
							liger.get("template_code").setValue(data[0].id);
							liger.get("template_code").setText(data[0].text);
						}else if(value==0){
							liger.get("template_code").setValue(data[1].id);
							liger.get("template_code").setText(data[1].text);
						}
						
					}
				}
			}		
		);
		
		autoCompleteByData("#doc_type", docType.Rows, "id", "text", false, true,null,true,null,220);
		
		$("#bill_state").ligerComboBox({ cancelable: true });
		$("#doc_type").ligerComboBox({ cancelable: false });
		$("#busi_date_b").ligerTextBox({width:100 });
		$("#busi_date_e").ligerTextBox({width:100 });
		
		autodate("#busi_date_b", "yyyy-mm-dd", "month_first");
    	autodate("#busi_date_e", "yyyy-mm-dd", "month_last");
		
		$("#vouch_date").ligerTextBox({width:90 });
		$("#vouch_date").val('${vouch_date}');
		$("#business_no_b").ligerTextBox({width:222 });
	}  
    
    function createVouch(flag){
    	/* if($("#busi_date_b").val().substring(0,7)!=$("#busi_date_e").val().substring(0,7)){
		$.ligerDialog.error("不能跨月生成凭证！");
		return;
		} */
		if(liger.get("template_code").getValue()==""){
			$.ligerDialog.error("请选择凭证模板！");
			return;
		}
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
	   
		var init_type = liger.get("initType").getValue();
		
		var para={
				busi_date_b: $("#busi_date_b").val(),
				busi_date_e: $("#busi_date_e").val(),
				template_code: liger.get("template_code").getValue(),
				vouch_date: $("#vouch_date").val(),
				init_type: init_type,
				mod_code: '08',
				bill_state: liger.get("bill_state").getValue(),
				doc_type: liger.get("doc_type").getValue(),
				busi_type_code: liger.get("acc_busi_type").getValue().split("@")[0],				
				busi_log_table: liger.get("acc_busi_type").getValue().split("@")[1]
		};
		
		//如果按单据生成则需要拼接勾选的单据
		if(init_type == 1){
			var busi_no = "";
			var data = gridManager.getCheckedRows();
		    if (data.length>0){
		        $(data).each(function (){
		        	busi_no=busi_no+this.BUSINESS_NO+",";
				});
		        
		        busi_no=busi_no.substring(0,busi_no.length-1);
		    }else{
		    	$.ligerDialog.error("请选择单据！");
				return;
		    }
		    
		    para.busi_no = busi_no;
		}else if(init_type == 4){
			para.store_id = liger.get("store_id").getValue().split(".")[0];
			para.sup_id = liger.get("sup_id").getValue().split(".")[0];
		}
		
		if(flag==1){
			checkVouch(para);
		}else{
			$.ligerDialog.confirm('确定生成凭证?', function (yes){
		    	if(yes){
		    		var loadIndex = layer.load(1);
		    		ajaxJsonObjectBylayer("queryVouchJsonByMed.do",para,function(responseData){	
		    			//console.log(responseData);
		    			layer.close(loadIndex);
		    			var para={
		    				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
		    				title:'会计凭证',
		    				width:0,
		    				height:0,
		    				isShowMin:true,
		    				isModal:true,
		    				/* data:{vouch:responseData.vouch,busi_log_table:liger.get("acc_busi_type").getValue().split("@")[1],busi_type_code:liger.get("acc_busi_type").getValue().split("@")[0],
		    					busi_no:responseData.busi_no,template_code:liger.get("template_code").getValue()} */
		    				data:{auto_id:responseData.auto_id,busi_log_table:liger.get("acc_busi_type").getValue().split("@")[1]}
		    				
		    			};
		    			//期末处理生成凭证格式：data:{vouch:responseData,busi_log_table:'ACC_VOUCH_SOURCE',busi_type_code:'0103'}
		    			parent.openDialog(para);
		      		},layer,loadIndex);
		    	}
		    }); 
		}
    }
	
    function isCheckDisplay(rowdata) {
    	
		if (rowdata.__index == 0 && grid.options.page==1)
			return false;

		return true;

	}

    
	function openSuperVouch(vouch_id){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+vouch_id,'会计凭证',0,0,true,true);
	}	
	
	function openInBackDetail(vouch_id,business_no){
		var para={
			url:"hrp/acc/autovouch/medautovouch/medInBackPage.do?isCheck=false",
			title:"明细数据",
			width:100,
			height:100,
			data:{busi_date_b:$("#busi_date_b").val(),
				busi_date_e:$("#busi_date_e").val(),
				vouch_id:vouch_id,			
				business_no: business_no,
				store_id: liger.get("store_id").getValue().split(".")[0],
				sup_id: liger.get("sup_id").getValue().split(".")[0],
				bill_state: liger.get("bill_state").getValue(),
				bus_type_code:liger.get("acc_busi_type").getValue().split("@")[0]}
		};
		parent.openDialog(para);
	}
	
	function printDate(){
		 if(grid.getData().length==0){
  		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
  	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"业务日期："+$("#busi_date_b").val()+"至"+$("#busi_date_e").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
 		var printPara={
 			rowCount:1,
 			title:'药品自动凭证',
 			columns: JSON.stringify(grid.getPrintColumns()),//表头
 			class_name: "com.chd.hrp.acc.service.autovouch.AccMedAutoVouchService",
			method_name: "queryMedAutoVouchPrint",
			bean_name: "accMedAutoVouchService",
			busi_type_code : liger.get("acc_busi_type").getValue(),
			mod_code : '08',
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
 			};
  	
 		//执行方法的查询条件
 		$.each(grid.options.parms,function(i,obj){
 			printPara[obj.name]=obj.value;
  	});
 		
  	officeGridPrint(printPara); 
	
	}
	
	//检查凭证
	var checkJson=[];
	function checkVouch(para){
		var loadIndex = layer.load(1);
		ajaxJsonObjectBylayer("../checkAutoVouch.do?isCheck=false",para,function(res){
			if(res.Total==0){
				$.ligerDialog.success("检查成功，业务数据正常。");
				return;
			}
			
			checkJson=res;
			$.etDialog.open({
   				id: "vouchNotePage",
   				url: '../vouchNotePage.do?isCheck=false',
   				frameName : window.name,
   				width: 700,
   				height: $(window).height()-200,
   				shade: 0,
   				zIndex:0,
   				maxmin: true,
   				title: '凭证说明',
   				success: function(layero){
   				   	 layer.setTop(layero);
   				 },resizing: function(layero){
   					  
   				 },full: function(layero){
   					 
   				 },min: function(layero){
   					  
   				 },restore: function(layero){
   					
   				 }
   			});
			
			
  		},layer,loadIndex);
		
	}
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" border=0>
        <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left:10px;">业务日期：</td>
        	<td align="left" >
        		<table>
        			<tr>
		            <td align="left" class="l-table-edit-td" >
		            	<input class="Wdate" id="busi_date_b" name="busi_date_b"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
		            </td>
		            <td>至</td>
		            <td align="left" class="l-table-edit-td" >
		            	<input class="Wdate" id="busi_date_e" name="busi_date_e"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
		            </td>       
		            </tr>
        		</table>
        	</td>
            <td align="right" class="l-table-edit-td" >业务类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="acc_busi_type" name="acc_busi_type" />
            </td>
            <td align="right" class="l-table-edit-td" name='storetTd'>仓库：</td>
            <td align="left" class="l-table-edit-td" name='storetTd'>
            	<input id="store_id" name="store_id" />
            </td>
           
            <td align="right" class="l-table-edit-td" name="typeId">药品类型：</td>
            <td align="left" class="l-table-edit-td" name="typeId">
            	<input id="med_type_code" name="med_type_code" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" name="busiNoTd">单据编号：</td>
        	<td align="left" class="l-table-edit-td" name="busiNoTd">
        		<input id="business_no_b" name="business_no_b" />
            </td>
        	<td align="right" class="l-table-edit-td" style="padding-left:10px;" name="billTd">是否挂账：</td>
            <td align="left" class="l-table-edit-td" name="billTd"><input name="bill_state" type="text" id="bill_state" /></td>
            <td align="right" class="l-table-edit-td"  name="supTd"> 供应商：</td>
            <td align="left" class="l-table-edit-td" name="supTd">
            	<input id="sup_id" name="sup_id" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;" id="deptTd1" name='deptTd'>科室：</td>
            <td align="left" class="l-table-edit-td"  id="deptTd2" name='deptTd'>
            	<input id="dept_id" name="dept_id" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;" name='docTd'>医嘱类别：</td>
            <td align="left" class="l-table-edit-td"  name='docTd'>
            	<input id="doc_type" name="doc_type" />
            </td>
        </tr>
        <tr>
         	
        </tr>
    </table>
    <hr/>
    <table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" >
    <tr>
    	<td align="right" class="l-table-edit-td"  style="width:65px;"><font color="red">*</font>凭证模板：</td>
		<td align="left" class="l-table-edit-td" style="width:120px;">
		    <input type="text" id="template_code"  />
		</td>
		<td align="right" class="l-table-edit-td" style="width:70px;"><font color="red">*</font>凭证日期：</td>
		<td align="left" class="l-table-edit-td" style="width:90px;">
			<input class="Wdate" name="vouch_date"  type="text"  id="vouch_date"   onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
		</td>
		<td align="right"  class="l-table-edit-td" style="width:70px;" name="initTypeTd"><font color="red">*</font>生成方式：</td>
		<td align="left" class="l-table-edit-td" style="width:250px;" name="initTypeTd">
			<div id="initType"></div>
		</td>
    	<td align="right">
    	
    		<button accessKey="Q" onclick="query();">查询 Q</button>
    		<button accessKey="C" name="createVouchBtn" onclick="createVouch(1);">检查凭证 C</button>
    		<button accessKey="S" name="createVouchBtn" onclick="createVouch(2);">生成凭证 S</button>
    		<button accessKey="P" onclick="printDate();">打 印 P</button>
    	</td>
    </tr>
    </table>
    
 
	<div id="maingrid"></div>
</body>
</html>
