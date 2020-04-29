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
    
    $(function ()
    {
    	 loadHead(null);
         loadDict();//加载下拉框
         f_setColumns();
		// loadHotkeys();
		 var dataGrid = [
				{ id: 1, name: '按单据生成'},
				{ id: 3, name: '按日期生成'}
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
    	
    	if($("#year_month").val()==""){
 			$.ligerDialog.error("请选择统计年月！");
 			return;
 		}
    	
		grid.options.parms=[];
		grid.options.newPage=1;
	
		
		grid.options.parms.push({
			name : 'busi_type_code',
			value : '0102'
		});
		
		grid.options.parms.push({
			name : 'mod_code',
			value : '01'
		});
		
		grid.options.parms.push({
			name : 'year_month',
			value : $("#year_month").val()
		});
		grid.options.parms.push({
			name : 'proj_id',
			value : liger.get("proj_id").getValue()
		});
		
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryPubAutoVouch.do',
			width: '100%', height: '100%', rownumbers:true,checkbox: true,checkBoxDisplay : isCheckDisplay,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true//,heightDiff: -10
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    var columnsData;
    function f_setColumns() {
    	
    	
		$.post("queryPubAutoVouchHead.do?isCheck=false", {
			"mod_code" : '01',
			"busi_type_code":'0102'
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
					}else if(value_name == "BUSINESS_NO"){
						columns.push({display:display_name,name:value_name,width: 160,align : 'left',frozen : true,
							render:function(rowdata){
								if(rowdata.BUSINESS_NO!=null){
									return "<a href=javascript:openUpdate('"+rowdata.BUSINESS_NO+"')>"+rowdata.BUSINESS_NO+"</a>";
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
	    	 id: '#proj_id',
	    	 urlStr: '../../../acc/queryHosDictType.do?isCheck=false&table_code=01005',
	    	 valueField: 'id',
	    	 textField: 'text',
	    	 autocomplete: true,
	    	 //initWidth: '400',
	    	 //defaultSelect: true,
	    	 autocompletelocal: true,
	    	 pageSize: 99999
	     });
		
		autocomplete("#template_code","../../autovouch/his/charge/queryAutoBusiTemplate.do?isCheck=false&busi_type_code=0102","id","text",true,true,"",true);
		$("#year_month").ligerTextBox({width:100 });
		autodate("#year_month", "yyyymm");
		
		$("#vouch_date").ligerTextBox({width:90 });
		$("#vouch_date").val('${vouch_date}');
	}  
    
    function createVouch(flag){
    	
        if($("#year_month").val()==""){
			$.ligerDialog.error("请选择统计年月！");
			return;
		}
		if(liger.get("template_code").getValue()==""){
			$.ligerDialog.error("请选择凭证模板！");
			return;
		}
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
	   
		var init_type = liger.get("initType").getValue();
		var data = gridManager.getCheckedRows();
		if (data.length>0 ){
			//如果勾选单据按单据生成
			init_type=1;
		}else{
			//按日期生成
			init_type=3;
		}
		var para={
				year_month: $("#year_month").val(),
				template_code: liger.get("template_code").getValue(),
				vouch_date: $("#vouch_date").val(),
				init_type: init_type,
				huizong_sql: '',
				mod_code: '01',
				busi_type_code: '0102',				
				busi_log_table: 'ACC_BUSI_LOG_0102'
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
		}
		
		if(flag==1){
			checkVouch(para);
		}else{
			$.ligerDialog.confirm('确定生成凭证?', function (yes){
		    	if(yes){
		    		var loadIndex = layer.load(1);
		    		ajaxJsonObjectBylayer("queryPubVouchJsonByBusi.do",para,function(responseData){	
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
		    				data:{auto_id:responseData.auto_id,busi_log_table:'ACC_BUSI_LOG_0102'}
		    				
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
	
	function openUpdate(business_no){
		if(business_no==""){
			return;
		}
		
		var para={
			url:'hrp/acc/autovouch/accpubcost/pubcostreg/pubCostRegUpdatePage.do?isCheck=false&year_month='+business_no.split("-")[0]+'&proj_code='+business_no.split("-")[1],
			title:"公用费用分摊",
			width:100,
			height:100,
			isDrag:true,
			data:{
				call:'vouch',year_month:business_no.split("-")[0],proj_code:business_no.split("-")[1]
			}
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
							{"cell":0,"value":"统计年月："+$("#year_month").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
 		var printPara={
 			rowCount:1,
 			title:'公用费用自动凭证',
 			columns: JSON.stringify(grid.getPrintColumns()),//表头
 			class_name: "com.chd.hrp.acc.service.autovouch.accpubCost.AccPubAutoVouchService",
			method_name: "queryPubAutoVouchPrint",
			bean_name: "accPubAutoVouchService",
			busi_type_code : '0102',
			mod_code : '01',
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
        	<td align="right" class="l-table-edit-td" style="padding-left:10px;">统计年月：</td>
        	<td align="left" >
        		<table>
        			<tr>
		            <td align="left" class="l-table-edit-td" >
		            	<input class="Wdate" id="year_month" name="year_month" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" />
		            </td>
		            </tr>
        		</table>
        	</td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:10px;">项目：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="proj_id" name="proj_id" />
            </td>
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
    		<button accessKey="S" name="createVouchBtn" onclick="createVouch(2);">生成凭证 S</button>
    		<button accessKey="P" onclick="printDate();">打 印 P</button>
    	</td>
    </tr>
    </table>
    
 
	<div id="maingrid"></div>
</body>
</html>
