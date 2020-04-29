<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <link rel="stylesheet" href="<%=path%>/lib/hrp/budg/panel-slide/panel-slide.css" />
    <script src="<%=path%>/lib/hrp/budg/panel-slide/panel-slide.js"	type="text/javascript"></script>
    <script type="text/javascript">
     
    var dataFormat;
    var grid;
    var gridManager = null;
    var detailGrid;
    var detailManager = null;
    var detailColumn = "";
    var sourceID = "";
    var sourceText = "" ;
     $(function (){
         loadDict()//加载下拉框
         
         loadForm();
         
         loadHead();
         
      // 重新 渲染表格  防止 数据加载时显示问题
         function showData(){
    	 	grid.reRender();
         }
         //重新 渲染表格  防止 数据加载时显示问题
         function showDetail(){
     	 	detailGrid.reRender();
         }
         
         panelSlide($("#panelMain"), { title: "项目预算申报明细(按资金来源)", width: "98%",showOnly: true,showFn: showData});
         panelSlide($("#panelDetail"), { title: "项目预算分解查看 ", width: "98%", isShow: false ,showOnly: true,showFn: showDetail})
         
         if('${state}' == '01' ){
         	grid.toolbarManager.setDisabled("back");
         	grid.toolbarManager.setDisabled("audit");
         	grid.toolbarManager.setDisabled("reaudit");
         }
         
         if('${state}' == '02' ){
        	grid.toolbarManager.setDisabled("save");
          	grid.toolbarManager.setDisabled("add");
          	grid.toolbarManager.setDisabled("delete");
          	grid.toolbarManager.setDisabled("up");
         	grid.toolbarManager.setDisabled("reaudit");
          }
         if('${state}' == '03' ){
        	 grid.toolbarManager.setDisabled("save");
        	grid.toolbarManager.setDisabled("save");
           	grid.toolbarManager.setDisabled("add");
           	grid.toolbarManager.setDisabled("delete");
           	grid.toolbarManager.setDisabled("up");
           	grid.toolbarManager.setDisabled("back");
           	grid.toolbarManager.setDisabled("audit");
           }
         
         $("#upload_btn").click(function(){
         	$("#upFile").click();
         })
     });  
     
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '资金来源(E)', name: 'source_id', align: 'left',width:'40%',
                    	  valueField:"id",textField : 'text',
				 			editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : '../../queryBudgSource.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
							} 
                     	 
 					 		},
 				 	 { display: '申报金额(E)', name: 'apply_amount', align: 'right',editor:{type:'float',onChanged : setApplyAmount},width:'40%',
 					 			render:function(rowdata,rowindex,value){
 					 				if(value){
 					 					return formatNumber(value,2,1);
 					 				}
 					 			}
 					 		},
 					 { display: '预算分解设置', name: 'resolve', align: 'center', width:'20%',
				 			render:function(rowdata,rowindex,value){
				 				 return "<a href=javascript:budgResolve('"+rowdata.source_id+"|"+rowdata.apply_amount+"|"+rowindex+"')>预算分解设置</a>";
				 			}
				 		}
                     
                      ],
                      dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgProjApplySource.do?isCheck=false&proj_id='+'${proj_id}'+'&apply_code='+'${apply_code}'+'&budg_year='+'${budg_year}',
                      width: '100%', height: '90%', checkbox: true,rownumbers:true,
                      enabledEdit :true ,isAddRow:false ,
                      onSuccess  : setDetailColumnInit,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { 
	                    	items: [
									{ text: '保存', id:'save', click: save , icon:'save' },
									{ line:true },
             						{ text: '添加行', id:'add', click: addRow , icon:'add' },
             	                	{ line:true },
             	                	{ text: '删除', id:'delete', click: deleteRow ,icon:'delete' },
             	               		{ line:true },
	             	               	{ text: '提交（<u>T</u>）', id:'up', click: submitBudgProjApply,icon:'up' },
	        		                { line:true },
	        		                { text: '撤回（<u>R</u>）', id:'back', click: recallBudgProjApply,icon:'back' },
	        		                { line:true },
	        		                { text: '审核（<u>A</u>）', id:'audit', click: auditBudgProjApply,icon:'audit' },
	        		                { line:true },
	        		                { text: '消审（<u>U</u>）', id:'reaudit', click: reAuditBudgProjApply,icon:'bcancle' },
	        		                { line:true },
	        		                { text: '模板打印（<u>U</u>）', id:'print', click: printSet,icon:'print' },
	        		                { line:true },
	        		                { text: '关闭（<u>C</u>）', id:'close', click: this_close,icon:'close' }
             				]},
                      
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
     
     function loadDetailHead(){
	     	detailGrid = $("#detailgrid").ligerGrid({
	     		columns: eval("["+detailColumn+"]"),
	            
	            dataAction: 'server',dataType: 'server',usePager:false, url:'queryBudgProjApplyResolve.do?isCheck=false&proj_id='+'${proj_id}'+'&apply_code='+'${apply_code}'+'&budg_year='+'${budg_year}'+'&sourceID='+sourceID,
	            width: '100%', height: '90%', checkbox: false,rownumbers:true,
	            enabledEdit :true ,isAddRow:false ,
	            selectRowButtonOnly:true,//heightDiff: -10,
	            onBeforeShowData : sumData,//计算合计值
	     	});

         detailManager = $("#detailgrid").ligerGetGridManager();
     }
     
     
     //  重新设置 表头
     function setDetailColumnInit(data){
    	 
    	 // 校验 资金来源表格中是否有重复数据。有数据错误信息，明细表格表头不重新设置
    	 if(!validateGridTitle(data.Rows)){
    		 return false;
    	 }
    	//重置表头
    	 detailColumn = "{ display: '支出项目ID', name: 'payment_item_id', align: 'left',hide :true,width:100}," 
    		 			+ "{ display: '支出项目变更ID', name: 'payment_item_no', align: 'left',hide :true,width:100},"
    		 			+ "{ display: '支出项目编码', name: 'payment_item_code', align: 'left',hide :true,width:120},"
						+ "{ display: '支出项目', name: 'payment_item_name', align: 'left',width:200}," ;
		 sourceID = "" ;//置空,重新赋值
		 sourceText = "" ;//置空,重新赋值
    	 if(data.Rows.length > 0){
    		 $(data.Rows).each(function (){
   				 detailColumn  += "{ display: '"+this.text.split(" ")[1]+"', name: '"+this.text.split(" ")[0]+"', align: 'left',width:120,"+
   				 "render:function(rowdata,rowindex,value){if(value){return formatNumber(value,2,1)}}},";
    			sourceID += this.id +"@" ;//为下面计算合计值及查询数据用 
    			
    			sourceText  += this.text +"@" ; //校验数据用
    			
    		 })
    		
    	}
   			 			
		detailColumn  += "{ display: '合计', name: 'sum', align: 'left',width:120,render:function(rowdata,rowindex,value){"+
					"if(value){return formatNumber(value,2,1)}}}";
		
		loadDetailHead();
		
     }
     
     //  添加、删除资金来源 时 重新设置 表头
     function setDetailColumn(){
    	 var data = grid.getData();
    	 // 校验 资金来源表格中是否有重复数据。有数据错误信息，明细表格表头不重新设置
    	 if(!validateGridTitle(data)){
    		 return false;
    	 }
    	//重置表头
    	 detailColumn = "{ display: '支出项目ID', name: 'payment_item_id', align: 'left',hide :true,width:100}," 
    		 			+ "{ display: '支出项目变更ID', name: 'payment_item_no', align: 'left',hide :true,width:100},"
    		 			+ "{ display: '支出项目编码', name: 'payment_item_code', align: 'left',hide :true,width:120},"
						+ "{ display: '支出项目', name: 'payment_item_name', align: 'left',width:200}," ;
		 sourceID = "" ;//置空,重新赋值
		 sourceText = "" ;//置空,重新赋值
    	 if(data.length > 0){
    		 $(data).each(function (){
   				 detailColumn  += "{ display: '"+this.text.split(" ")[1]+"', name: '"+this.text.split(" ")[0]+"', align: 'left',width:120,"+
   				 "render:function(rowdata,rowindex,value){if(value){return formatNumber(value,2,1)}}},";
    			sourceID += this.id +"@" ;//为下面计算合计值及查询数据用 
    			
    			sourceText  += this.text +"@" ; //校验数据用
    			
    		 })
    		
    	}
   			 			
		detailColumn  += "{ display: '合计', name: 'sum', align: 'left',width:120,render:function(rowdata,rowindex,value){"+
					"if(value){return formatNumber(value,2,1)}}}";
		
		loadDetailHead();
		
		var dataDetail = detailGrid.getData();
		
		if(dataDetail.length > 0 ){
			
			detailGrid.deleteAllRows();
			
			detailGrid.addRows(data);
		}
		
     }
     
     function sumData(rowdata){
    	 
    	 var codeKey = sourceID.split("@") ;
    	 $.each(rowdata.Rows,function(i, item){
    		 
    		 var sum = 0 ;
    		 for(var i in codeKey ){
    			 if(codeKey[i]){
    				 var key = "budgAmount"+codeKey[i]
    				 if(item[key]){
    					 sum +=  item[key] ;
    				 }
    			 }
    			
    		 }
    		 item.sum = sum ;
    		 
     	})
     }
     
     function setApplyAmount(){
    	 
    	 var data = gridManager.getData();
    	 
    	 if(data.length > 0 ){
    		// 校验 资金来源表格数据。
        	 if(!validateGrid(data)){
        		 return false;
        	 }
        	 
        	 var applyAmount = 0 ; //记录 申报金额总额
        	 
        	 $(data).each(function(){
        		 
        		 applyAmount += this.apply_amount  ;
        		 
        	 })
        	 
        	 $("#apply_amount").val(applyAmount);
        	 
    	 }else{
    		 
    		 $("#apply_amount").val("0.00");
    		 
    	 }
    	 
     }
     
     
     //资金来源数据校验（表头设置使用）
     function validateGridTitle(data) {  
      	var msg="";
   		var rowm = "";
   		//判断grid 中的数据是否重复或者为空
   		var targetMap = new HashMap();
   		$.each(data,function(i, v){
   			rowm = "";
  			if (v.source_id == "" || v.source_id == null || v.source_id == 'undefined') {
  				rowm+="[资金]、";
  			}
  			if(rowm != ""){
  				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
  			}
  			msg += rowm;
  			var key=v.source_id 
  			var value="第"+(i+1)+"行";
  			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
  				targetMap.put(key ,value);
  			}else{
  				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
  			}
   		});
   		if(msg != ""){
   			$.ligerDialog.warn(msg);  
  			return false;  
   		}else{
   			return true;  
   		} 	
 	}
     
     //资金来源数据校验（进行其他功能操作时用）
    function validateGrid(data) {  
     	var msg="";
  		var rowm = "";
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
 			if (v.source_id == "" || v.source_id == null || v.source_id == 'undefined') {
 				rowm+="[资金]、";
 			}
 			if (v.apply_amount == "" || v.apply_amount == null || v.apply_amount == 'undefined') {
 				rowm+="[申报金额]、";
 			}
 			if(rowm != ""){
 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 			}
 			msg += rowm;
 			var key=v.source_id 
 			var value="第"+(i+1)+"行";
 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 				targetMap.put(key ,value);
 			}else{
 				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
 			}
  		});
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		}else{
  			return true;  
  		} 	
	}
     
    // 预算分解表格 数据校验
    function validateGridResolve(data) {  
  		
    	var rowm = "";
    	
    	var codeKey = sourceText.split("@") ;
    	
  		$.each(data,function(i, item){
  			
	   		 for(var j in codeKey ){
	   			 if(codeKey[j]){
	   				var key = codeKey[j].split(" ")[0] ;
   					if (item[key] == "" || item[key] == null || item[key] == 'undefined') {
   		 				rowm+="["+codeKey[j].split(" ")[1]+"]、";
   		 			}
	   			 }
	   		 }
	   		if(rowm != ""){
	 			rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
	 		}
  		});
  		if(rowm != ""){
  			$.ligerDialog.warn(rowm);  
 			return false;  
  		}else{
  			return true;  
  		} 	
	}
     
     //保存
     function  save(){
    	 
    	 if('${state}' != '01'){
         	$.ligerDialog.error('数据不是新建状态,不允许修改!');
         	
         	return false ;
    	 }
    	 var data = gridManager.getData();
    	 
    	 if(data.length == 0 ){
    		 
    		 $.ligerDialog.error('项目预算申报明细(按资金来源)表格没有数据!');
    		 
    		 return false;
    	 }
    	 
    	// 校验 资金来源表格中数据。
    	 if(!validateGrid(data)){
    		 return false;
    	 }
    	 var arr = $("#file_url").val().split("\\");
         var file_url = arr[arr.length-1];
         if(file_url!=null||file_url!=""){  
           var extname = file_url.substring(file_url.lastIndexOf(".")+1,file_url.length);  
           extname = extname.toLowerCase();//处理了大小写  
         }
         
         var file = document.getElementById("upFile").files;
    	 
         //var formPara={
        		var maker = '${maker}';
        		var make_date = '${make_date}';
        		var checker = '${checker}';
        		var check_date = '${check_date}';
        		var apply_code=$("#apply_code").val();
        		var budg_year=liger.get("budg_year").getValue();
        		var proj_id=liger.get("proj_id").getValue().split(",")[0];
        		var proj_no=liger.get("proj_id").getValue().split(",")[1];
        		var apply_type=liger.get("apply_type").getValue();
        		var remark=$("#remark").val();
                
	            //file_url=file_url;
	            var old_file_url = '${file_url}';
	            var apply_amount=$("#apply_amount").val();
	            
	            var state=liger.get("state").getValue() ;
	           
	            var sourceData = JSON.stringify(grid.getData())
             	
         //};
         //console.log(formPara)
         $.ajaxFileUpload({  
             url : 'updateBudgProjApply.do?isCheck=false&maker='+maker+'&make_date='+make_date+'&checker='+checker+'&check_date='+check_date
             +'&apply_code='+apply_code+'&budg_year='+budg_year+'&proj_id='+proj_id+'&apply_type='+apply_type+'&remark='+remark+'&file_url='+file_url
             +'&old_file_url='+old_file_url+'&apply_amount='+apply_amount+'&state='+state+'&sourceData='+sourceData, //用于文件上传的服务器端请求地址  
             //data : formPara,
             secureuri : false, //一般设置为false  
             fileElementId : 'upFile', //文件上传空间的id属性  <input type="file" id="file" name="file" />  
             type : 'post',  
             dataType : 'json', //返回值类型 一般设置为json  
             success : function(data, status) //服务器成功响应处理函数  
             {  
             	if(data.state == "true"){
             		$.ligerDialog.success(data.msg)
             		 parentFrameUse().query();
             	}else{
             		$.ligerDialog.warn(data.msg)
             	}
             },  
         });  
    }
     
   //提交
  	function submitBudgProjApply(){
			
			if('${state}' != '01' ){
				
				$.ligerDialog.error('该数据不是新建状态,不允许提交!');
				
				return false ;
			}
			var ParamVo =[];
			
 			ParamVo.push(
 					'${group_id}'   +"@"+ 
	 				'${hos_id}'   +"@"+ 
	 				'${copy_code}'   +"@"+ 
	 				'${budg_year}'   +"@"+ 
	 				'${apply_code}'   +"@"+ 
	 				'${proj_id}'   +"@"+ '02'
 				
 				) 
			$.ligerDialog.confirm('确定提交吗?', function (yes){
		    	if(yes){
		        	ajaxJsonObjectByUrl("updateBudgProjApplyState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
		        		if(responseData.state=="true"){
		        			parentFrameUse().query();
		        			location.reload() ;
		        		}
		        	});
		    	}
		   }); 
  	}
  	//撤回
  	function recallBudgProjApply(){
  		
  		if('${state}' != '02' ){
			
			$.ligerDialog.error('数据不是已提交状态,不允许撤回!');
			
			return false ;
		}
  		var ParamVo =[];
		ParamVo.push(
			'${group_id}'   +"@"+ 
			'${hos_id}'   +"@"+ 
			'${copy_code}'   +"@"+ 
			'${budg_year}'   +"@"+ 
			'${apply_code}'   +"@"+ 
			'${proj_id}'   +"@"+ '01'
		
		) 
 			
		$.ligerDialog.confirm('确定撤回吗?', function (yes){
	    	if(yes){
	        	ajaxJsonObjectByUrl("updateBudgProjApplyState.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
	        		if(responseData.state=="true"){
	        			parentFrameUse().query();
	        			location.reload() ;
	        		}
	        	});
	    	}
	   }); 
  	}
  	//审核
  	function auditBudgProjApply(){
  		
		if('${state}' != '02' ){
			
			$.ligerDialog.error('数据不是已提交状态,不允许审核!');
			
			return false ;
		}
  		var ParamVo =[];
		ParamVo.push(
				'${group_id}'   +"@"+ 
				'${hos_id}'   +"@"+ 
				'${copy_code}'   +"@"+ 
				'${budg_year}'   +"@"+ 
				'${apply_code}'   +"@"+ 
				'${proj_id}'   +"@"+ '03'
			
		) 
 			
		$.ligerDialog.confirm('确定审核吗?', function (yes){
	    	if(yes){
	        	ajaxJsonObjectByUrl("auditBudgProjApply.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
	        		if(responseData.state=="true"){
	        			parentFrameUse().query();
	        			location.reload() ;
	        		}
	        	});
	    	}
	   }); 
  	}
  	//消审
  	function reAuditBudgProjApply(){
  		
  		if('${state}' != '03' ){
			
			$.ligerDialog.error('数据不是审核状态,不允许消审!');
			
			return false ;
		}
  		var ParamVo =[];	     	   
		ParamVo.push(
				'${group_id}'   +"@"+ 
				'${hos_id}'   +"@"+ 
				'${copy_code}'   +"@"+ 
				'${budg_year}'   +"@"+ 
				'${apply_code}'   +"@"+ 
				'${proj_id}'   +"@"+ '02'
			
			) 
 			
		$.ligerDialog.confirm('确定消审吗?', function (yes){
	    	if(yes){
	        	ajaxJsonObjectByUrl("reAuditBudgProjApply.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
	        		if(responseData.state=="true"){
	        			parentFrameUse().query();
	        			location.reload() ;
	        		}
	        	});
	    	}
	   }); 
  	}
	function loadForm(){
	    
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     //$("form").ligerForm();
	}       
   
    function saveBudgProjApply(){
        if($("form").valid()){
            save();
        }
   }
    //添加行
   function addRow(){
    	//添加 可编辑行
    	grid.addRowEdited({
        	source_id: '' ,
        	apply_amount : ''
        	
  		});
    }
    
    //删除
	function deleteRow(){
		var data = grid.getCheckedRows();
 		if(data.length == 0){
 			$.ligerDialog.error('请选择要删除的行!');
              return;
        }else{
        	 for (var i = 0; i < data.length; i++){
        		 grid.remove(data[i]);
             } 
        }
 		//重新设置表头
 		setDetailColumn();
 		//重新计算 申报金额 总额
 		setApplyAmount();
    }
    
	//预算分解设置
	function budgResolve(obj){
		//加载 项目预算分解查看 表格表头
		setDetailColumn();
    	if($("form").valid()){
    		
			var vo = obj.split("|");
			
			if(vo[1]){
    			
    	    	 var proj_id = liger.get("proj_id").getValue().split(",")[0];
    			 
    			 var proj_no = liger.get("proj_id").getValue().split(",")[1];
    	    	 
    			 parent.$.ligerDialog.open({ url : 'hrp/budg/project/apply/budgProjApplyResolvePage.do?isCheck=false&source_id='+vo[0]+'&apply_amount='+vo[1]+'&proj_id='+proj_id+'&proj_no='+proj_no+'&rowindex='+vo[2]+'&apply_code='+'${apply_code}', data:{}, height: 300,width: 600,
    				 title:'预算分解设置',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
    				 parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    			}); 
    		}else{
    			
    			$.ligerDialog.error('申报金额不能为空!');
    			
    		}
    	}
	}
    
	//模板打印
	function printSet(){
		
	}
  //关闭当前页面
	function this_close(){
	  
		frameElement.dialog.close();
	}
    function loadDict(){
            //字典下拉框
    	 //预算年度下拉框
    	 
        autocomplete("#budg_year","../../queryBudgYear.do?isCheck=false","id","text",true,true,"",false,'${budg_year}');
        
        //申报类型 下拉框
        autocomplete("#apply_type","../../queryBudgApplyType.do?isCheck=false","id","text",true,true,'',false,'${apply_type}');
        
        //项目 下拉框
        autocomplete("#proj_id","../../queryProjName.do?isCheck=false","id","text",true,true,'',false,'${proj_id},${proj_no}',415);
        
        //状态
        autocomplete("#state","../../queryBudgApplyState.do?isCheck=false","id","text",true,true,'',false,'${state}');
        
        $("#budg_year").ligerTextBox({width:160,disabled:true,cancelable:true});
   	 	$("#apply_code").ligerTextBox({width:160,disabled:true});
        $("#proj_id").ligerTextBox({width:415,disabled:true,cancelable:true});
        $("#apply_type").ligerTextBox({width:160});
        $("#file_url").ligerTextBox({width:320});
        $("#remark").ligerTextBox({width:415});
        $("#apply_amount").ligerTextBox({width:160,disabled:true});
        $("#state").ligerTextBox({width:160,disabled:true,cancelable:true});
        
     } 
    </script>
  	<script>
    jQuery.extend({  
        createUploadIframe: function(id, uri){  
                //create frame  
                var frameId = 'jUploadFrame' + id;  
                var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';  
                if(window.ActiveXObject)  
                {  
                    if(typeof uri== 'boolean'){  
                        iframeHtml += ' src="' + 'javascript:false' + '"';  
      
                    }  
                    else if(typeof uri== 'string'){  
                        iframeHtml += ' src="' + uri + '"';  
                    }     
                }  
                iframeHtml += ' />';
                
                jQuery(iframeHtml).appendTo(document.body); 
                return jQuery('#' + frameId).get(0);              
        },  
        createUploadForm: function(id, fileElementId, data){  
            //create form     
            var formId = 'jUploadForm' + id;  
            var fileId = 'jUploadFile' + id;  
            var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');      
            if(data)  
            {  
                for(var i in data)  
                {  
                    jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);  
                }             
            }         
            var oldElement = jQuery('#' + fileElementId);  
            var newElement = jQuery(oldElement).clone();  
            jQuery(oldElement).attr('id', fileId);  
            jQuery(oldElement).before(newElement);  
            jQuery(oldElement).appendTo(form);  
            //set attributes  
            jQuery(form).css('position', 'absolute');  
            jQuery(form).css('top', '-1200px');  
            jQuery(form).css('left', '-1200px');  
            jQuery(form).appendTo('body');        
            return form;  
        },  
      
        ajaxFileUpload: function(s) {  
            // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout          
            s = jQuery.extend({}, jQuery.ajaxSettings, s);  
            var id = new Date().getTime()    
            var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));  
            var io = jQuery.createUploadIframe(id, s.secureuri);
            var frameId = 'jUploadFrame' + id;  
            var formId = 'jUploadForm' + id;          
            // Watch for a new set of requests  
            if ( s.global && ! jQuery.active++ )  
            {  
                jQuery.event.trigger( "ajaxStart" );  
            }              
            var requestDone = false;  
            // Create the request object  
            var xml = {}     
            if ( s.global )  
                jQuery.event.trigger("ajaxSend", [xml, s]);  
            // Wait for a response to come back  
            var uploadCallback = function(isTimeout)  
            {
                var io = document.getElementById(frameId);
                try   
                {                 
                    if(io.contentWindow)  
                    {  
                    	
                         // xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;  
                         // xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document.children[0].outerHTML;
						//console.log(io.contentWindow.document.children[0].outerHTML)
						 xml.responseText = io.contentWindow.document.children[0].outerHTML;
                         xml.responseXML = io.contentWindow.document.children[0].outerHTML;
                    }else if(io.contentDocument)  
                    {  
                         xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;  
                        xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;  
                    }                         
                }catch(e)  
                {  
                    jQuery.handleError(s, xml, null, e);  
                }  
                if ( xml || isTimeout == "timeout")   
                {                 
                    requestDone = true;  
                    var status;  
                    try {  
                        status = isTimeout != "timeout" ? "success" : "error";  
                        // Make sure that the request was successful or notmodified  
                        if ( status != "error" )  
                        {  
                            // process the data (runs the xml through httpData regardless of callback)  
                            var data = jQuery.uploadHttpData( xml, s.dataType );      
                            // If a local callback was specified, fire it and pass it the data 
                            if ( s.success )  
                                s.success( data, status );  
          
                            // Fire the global callback  
                            if( s.global )  
                                jQuery.event.trigger( "ajaxSuccess", [xml, s] );  
                        } else  
                            jQuery.handleError(s, xml, status);  
                    } catch(e)   
                    {  
                        status = "error";  
                        jQuery.handleError(s, xml, status, e);  
                    }  
      
                    // The request was completed  
                    if( s.global )  
                        jQuery.event.trigger( "ajaxComplete", [xml, s] );  
      
                    // Handle the global AJAX counter  
                    if ( s.global && ! --jQuery.active )  
                        jQuery.event.trigger( "ajaxStop" );  
      
                    // Process result  
                    if ( s.complete )  
                        s.complete(xml, status);  
      
                    jQuery(io).unbind()  
      
                    setTimeout(function(){   
                    	try{  
                           jQuery(io).remove();  
                           jQuery(form).remove();    
                             
                        } catch(e){  
                           jQuery.handleError(s, xml, null, e);  
                        }                                     
                    }, 100)  
                    xml = null  
                }  
            }  
            // Timeout checker  
            if ( s.timeout > 0 )   
            {  
                setTimeout(function(){  
                    // Check to see if the request is still happening  
                    if( !requestDone ) uploadCallback( "timeout" );  
                }, s.timeout);  
            }  
            try{  
                var form = jQuery('#' + formId);  
                jQuery(form).attr('action', s.url);  
                jQuery(form).attr('method', 'POST');  
                jQuery(form).attr('target', frameId);  
                if(form.encoding)  
                {  
                    jQuery(form).attr('encoding', 'multipart/form-data');                 
                }  
                else  
                {     
                    jQuery(form).attr('enctype', 'multipart/form-data');              
                }             
                jQuery(form).submit();  
      
            } catch(e){             
                jQuery.handleError(s, xml, null, e);  
            }  
              
            jQuery('#' + frameId).load(uploadCallback   );  
            return {abort: function () {}};   
        },  
      
        uploadHttpData: function( r, type ) {
            var data = !type;  
            data = type == "xml" || data ? r.responseXML : r.responseText;
            // If the type is "script", eval it in global context 
            if ( type == "script" ) {
            	jQuery.globalEval( data );
            
            // Get the JavaScript object, if JSON is used.
            } else if ( type == "json" ) {
            	data = data.replace(/<\/\w{0,}>/g, "");
            	data = data.replace(/<JSONObject>/g, "");
            	data = data.replace(/</g, "\",\"");
            	data = data.replace(/\",\"/, "");
            	data = data.replace(/>/g, "\":\"");
            	data = "{\"" + data + "\"}";
            	data = JSON.parse(data);
            	 
           	// evaluate scripts within html
            } else if ( type == "html" ) {
            	jQuery("<div>").html(data).evalScripts(); 
            }
            return data;  
        },  
        handleError: function( s, xhr, status, e )      {  
            // If a local callback was specified, fire it  
            if ( s.error ) {  
                s.error.call( s.context || s, xhr, status, e );  
            }  
            // Fire the global callback  
            if ( s.global ) {  
                (s.context ? jQuery(s.context) : jQuery.event).trigger( "ajaxError", [xhr, s, e] );  
            }  
        }  
       
    });
    
    function chooseFile(dom){
    	var arr = $(dom).val().split("\\");
    	var fileName = arr[arr.length-1];
    	$("#file_url").val(fileName);
    }
    </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">申报单号：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_code" type="text" id="apply_code" disabled="disabled" value="${apply_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
           	<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
        </tr>
        <tr> 
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">申报类型<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_type" type="text" id="apply_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申报金额<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_amount" type="text" id="apply_amount" disabled="disabled" value= "${apply_amount}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">相关文件：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="file_url" type="text" id="file_url" value="${file_url}"  ltype="text" validate="{maxlength:40}" /></td>
           	<td align="left">
           		<input type="button" id="upload_btn" value="选择文件"  />
           		<input name="upFile" style="display: none" id ="upFile" type="file" accessKey="A" onChange = "chooseFile(this)" /></td>
           	<td align="left"></td>
        	<%-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">相关文件：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="file_url" type="text" id="file_url" value= "${file_url}" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"><button id ="upFile" accessKey="A"><b>上传文件（<u>A</u>）</b></button></td>
            <td align="left"></td> --%>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">申报说明：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="remark" type="text" id="remark" ltype="text" value= "${remark}" validate="{maxlength:100}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态<span style="color:red">*</span>：</td>
            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
    <div>
    	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
	    	<tr>	
				<td align="center" class="l-table-edit-td" width="25%">
					制单人：${maker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="25%" >
					制单日期：${make_date}
				</td>
				<td align="center" class="l-table-edit-td" width="25%">
					审核人：${checker_name}
				</td>
				<td align="center" class="l-table-edit-td" width="25%" >
					审核日期：${check_date}
				</td>
			</tr>
		</table>
    </div>
    <div id="panelMain">
    	<div id="maingrid"></div>
    </div>
    <div id="panelDetail">
    	<div id="detailgrid"></div>
   	</div>
   	
   	
    </body>
</html>
