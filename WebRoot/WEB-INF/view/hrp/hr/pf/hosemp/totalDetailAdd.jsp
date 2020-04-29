<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>个人一览统计表</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,grid" name="plugins" />
        </jsp:include>
        <style>
.basetitle {
  /*   background: url(../icons/resume_1.png) left center no-repeat;
    */ margin: 10px;
    padding: 10px 10px 10px 20px;
    border-bottom: 1px solid #ccc;
    font-size: 14px;
}
 html * {
              
                font-size: 16px;
            }
.resumexport {
    display: inline-block;
    width: 28px;
    height: 25px;
/*     background: url(../icons/big/export.png) center center no-repeat;
 */    padding-left: 50px;
    vertical-align: middle;
}
.header {
    border-bottom: 2px solid #509DE1;
    padding-top: 65px;
}

    .header > table {
        margin: 0 0 10px 20px;
    }

.headpic {
    float: right;
    margin: 0 15px 10px 0;
}

    .headpic > img {
        max-width: 100px;
        max-height: 100px;
        margin-top: -50px;
    }

.name {
    color: #509DE1;
    font-size: 24px;
    font-weight: 600;
    padding-left: 8px;
    width:90px;
    text-align:justify;
}
td.name:after{
    content:'';
     display:inline-block;
     width:100%;
}

.empcard, .qq, .mobile {
    padding-left: 8px;
    padding-top: 20px;
    color: #509DE1;
}

.qq {
    padding-left: 30px;
    padding-right: 30px;
}

   #divall {
    border: 1px solid #ccc;
    border-top: none;
    width: 100%;
    margin: 0 auto;
    margin-top: 10px;
}

.divleft {
    position: relative;
    float: left;
    width: 60%;
    height: 0px;
    border-top: none;
    border-bottom: 10px #509DE1 solid;
    border-right: transparent 10px solid;
    border-left: none;
}

.divright {
    position: relative;
    float: right;
    width: 38%;
    height: 0px;
    border-bottom: none;
    border-top: 10px #ffbb2e solid;
    border-left: transparent 10px solid;
    border-right: none;
}        
.girdClass{
  width: 99%;
margin-left: 9px;
    margin-right: 8px;
}
            button {
                margin: 0px 5px;
                box-sizing: border-box;
                height: 26px;
                padding-left: 10px;
                padding-right: 10px;
                border: 1px solid #aecaf0;
                background: #e5edf4;
                outline: none;
                border-radius: 2px;
                cursor: pointer;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
            }
            .addStyle{
           	    width: 25%;
       	        float: left;
            }
            
            @media print {
            	.btn_group{
            		display:none
            	}
            }
            	      	/* 图片放大查看 */
	      	.preView {
	      		position: fixed;
			    width: 100%;
			    height: 100%;
			    top: 0;
			    left: 0;
			    opacity: .5;
			    background-color: #000;
			    z-index: 99;
	      	}
	      	
	      	.preView-image {
      		    position: fixed;
			    width: 400px;
			    height: 400px;
			    top: 100px;
			    left: 50%;
			    margin-left: -200px;
			    z-index: 100;
	      	}
	      	
	      	.preView-image img {
      		    width: 100%;
	      	}
        </style>
        <script>
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        
        var parentFrameName = parent.$.etDialog.parentFrameName;
        var parentWindow = parent.window[parentFrameName];

        var data = parentWindow.main_grid.selectGet().length== 0 ? parentWindow.rowData : parentWindow.main_grid.selectGet()[0].rowData;
         var store_type_code= parentWindow.archives_select.getValue();
       var emp_id = data.emp_id;//主表ID
        var emp_code = data.emp_code;//主表ID
        var emp_name= data.emp_name;//姓名
        var phone= data.phone;//电话
        var photo=data.photo;//照片
        var url;
            $(function () {
            	  if (photo && Array.isArray(JSON.parse(photo))) {
                     JSON.parse(photo).forEach(function(t) {
                        url=t.url
                     })
                 } 
            	document.getElementById('emp_code').innerHTML=emp_code;
            	document.getElementById('emp_name').innerHTML=emp_name;
            	document.getElementById('phone').innerHTML=phone;
            	document.getElementById("photo").src = url;
            	
            	
            	$.post("queryHosEmpDetail.do?isCheck=false", {
    				'tab_code' : 'HOS_EMP',
    				'emp_code':emp_code,
    				 'rjt':'jsonDetail'
    			
    			},function(responseData){
    				
    				rowData = data;
					var max_field_area = 0;

					var tableStr = $("#divTables");

    				 $.each(responseData.Rows,function(i,v){ 
    					 var field_width = 0;
 						var colspan = "";
 						var width = "";
 						var html = "";
 						var $tr = $("<tr>");
 						var $td = $("<td>");
 						var $table = $("<table class='l-table-edit' cellpadding='0' cellspacing='0'  width='100%' border='0' >");
 						$tr.append($td);
 						$td.append($table);
 						tableStr.append($tr);
 						html += "<tr>";
    	                   	$(v.data).each(function (i, data) {
    	                   		
    	                   		field_width++;
    	                           $(data).each(function () {
    	                               var value = this;
    	                     if(value.value != null){
  	   								html += "<td align='right' class='l-table-edit-td' style='padding-left:20px'  >"+ value.text+ "：</td> <td align='left'></td><td align='left' class='l-table-edit-td' style='padding-left:20px'  width = '14%'>"+ value.value+"</td>";
 
    	                          }else{
  	   								html += "<td align='right' class='l-table-edit-td' style='padding-left:20px' 	>"+ value.text+ "：</td> <td align='left'><td align='left' class='l-table-edit-td' style='padding-left:20px'  width = '14%'>"+""+"</td>";
  
    	                          }
    	                          
    	                               
    	                           	if (field_width == 4|| i + 1 == v.data.length) {
										/* if (field_width < 4&& k + 1 == data.Rows.length) {
											if (field_width == 3) {
												html += "<td align='right' width='10%' class='l-table-edit-td' style='padding-left:20px;'></td><td align='left'  colspan='"
														+ colspan
														+ "' width='14%' class='l-table-edit-td'></td>";
											}
											if (field_width == 2) {
												html += "<td><td/><td><td/><td><td/><td><td/>";
											}
											if (field_width == 1) {
												html += "<td><td/><td><td/><td><td/><td><td/><td><td/><td><td/>";
											}
										} */
										field_width = 0;
										html += "</tr>";
									}
    	                           });
    							
    	                       });
    	                   	$table.append(html);
    	           		 })
         
           	
    			
            })
            //查询档案库构建
            $.post("queryStoreTabStruc.do?isCheck=false", {
    				'store_type_code':store_type_code,
   				 'rjt':'json'
    			},function(responseData){
    				rowData = data;
    				var div1 = document.createElement("grid");
    				 $.each(responseData.Rows,function(i,v){ 
    					 if(v.TAB_CODE!='HOS_EMP'){
    						 
    					 var tab_code=v.TAB_CODE;
    					 var grid_obj=tab_code+"_obj";
    					 var grid=tab_code+"grid";
    						var childdiv=$('<div  class="girdClass"></div>');   
							  childdiv.attr('id',grid);

    					 var   param= tab_code+"_param";
    					 var url;
			            	//拼接动态子集查询
			        		var  arr=tab_code.toLowerCase().split("_");
			        		url='query';
			        	  for(var i=1;i< arr.length;i++){//遍历字符串
			        		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
			        	      }
			        	  
			        	  url+=".do";
				            var columns=getGridColumns({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:[tab_code],design:url});
                              if(columns.length>0){
                            	  grid_obj = {
          			            		
          				                editable: false,
          				                
          				                height: '145',
          				                inWindowHeight: true,
          				                
          				                checkbox: true,
          				                
          				                columns: columns,
          				                
          				                
          				                
          				                pageModel: false,

          				                showBottom: false
          				            };
          					    grid =childdiv.etGrid(grid_obj);
          					    $(document.body).append(childdiv);
          					            param = [{ name:'tab_code',value:tab_code},{ name:'emp_id',value:emp_id}];
          		                          
          		                          grid.loadData(param,url+"?isCheck=false");
          		                          
          		                          grid.refreshView();  
          		                          grid.commit(); 
                              }
    					  
    		                        
    					 }
    				 })
    			});
             

                $("#btn_print").click(function () {

                });

                $("#btn_export").click(function () {

                });

              
            });
            function this_close() {
            	   var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                   parent.$.etDialog.close(curIndex);
            }
         /*    function exe_word(){
                  location.href = "exportExcelStationBasics.do?isCheck=false&tab_code="+'HR_STATION_BASICS';

            } */
          	//查看照片
    	    function photoClick(file_path){
                   
    	    	  //var file_path =ui.rowData.PHOTO;
	        	  if(file_path == 'undefined' || file_path == '' || file_path == null){
						$.etDialog.error("暂无照片可预览");
	          			return;
	  		  	  }
	        	  imgSrc.src = file_path;
	        	  $("#preMain").show();
	        	  
	        	  $("#preMain").click(function(){
	        		  $("#preMain").hide()
	              })
          	};
        </script>
    </head>

    <body style="height:100%;overflow:auto">
        <div class="main" >
            <div class="btn_group">
                <button id="btn_print" hotkey='P' ctrl="true" onclick="window.print()">打 印(P)</button>
                <!-- <button id="btn_word" hotkey='E' onclick="exe_word()">导 出(E)</button> -->
                <button id="btn_close"  onclick="this_close();"hotkey='C'>关 闭(C)</button>
            </div>
             <div id="divall">
        <div class="divleft"></div>
        <div class="divright"></div>
                   <div class="header">
            <div class="headpic">
                <img id="photo" alt="头像" />
            </div>
            <table>
                <tr>
                     <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >姓名：</td>
            <td align="left" class="l-table-edit-td"> <div id="emp_name"></div><td align="left"></td> 
                    <td colspan="2"><a href="#" class="resumexport" onclick="exportword();"></a></td>
                </tr>
                <tr>
                  <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >工号：</td>
            <td align="left" class="l-table-edit-td"><div id="emp_code"></div> <td align="left"></td> 
               <!--   <td align="right" class="l-table-edit-td"  style="padding-left:20px;" id="emp_name">姓名：</td>
            <td align="left" class="l-table-edit-td"> <td align="left"></td>  -->
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >电话：</td>
            <td align="left" class="l-table-edit-td"> <div id="phone"></div><td align="left"></td> 
                </tr>
            </table>

        </div>
            <div class="basetitle">基本信息</div>
            	<table id="divTables" cellpadding="0" cellspacing="0"
			class="l-table-edit" width="100%"></table>
         <div id="grid"></div>
        </div>
		<!-- 图片查看 -->
       	<div id="preMain" style="display:none">
			<div class="preView"></div>
			<div class="preView-image">
			<img src="" id="imgSrc"></div>
		</div>
    </body>

    </html>