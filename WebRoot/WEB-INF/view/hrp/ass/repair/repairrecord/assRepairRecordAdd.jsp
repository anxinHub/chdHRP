<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>assLocationAdd</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,select,dialog,validate,grid,tree,upload,datepicker,time, checkbox" name="plugins" />
    </jsp:include>
    <style type="text/css">
    
    .container {
    display: -ms-flexbox;
    display: -webkit-flex;
    display: flex;
    box-sizing: border-box;
    height: 75%;
}
    </style>
    <script>
    var dialog = frameElement.dialog;
        var  eme_status, rep_dept,rep_user, loc_code, ass_card_no,validate ,tree,file;
        $(function () {
            loadDict();
            loadTree();
            loadForm();
            initGrid() ;
        })
/* 	//保存
	function save() {
	

		var formPara = {
				
		

		};
		
		ajaxJsonObjectByUrl("addPrmGoal.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
			
				
				parentFrameUse().query();
			}
		});
	}
        */    
      
      //选中回充数据
    	function f_onSelectRow_detail(data, rowindex, rowobj) {
    		selectData = "";
    		selectData = data;
    		//alert(JSON.stringify(data)); 
    		if (column_name == "inv_code") {
    			if (selectData != "" || selectData != null) {
    				//回充数据 
    				//grid.updateCell('apply_emp', 100, e.record);
    				grid.updateRow(rowindex_id, {
    					inv_code : data.inv_code,
    					inv_name : data.inv_name,
    					brand_name : data.brand_name,
    					inv_model : data.inv_model,
    					fac_id : data.fac_id,
    					fac_name : data.fac_name,
    					ass_model : data.ass_model,
    					ass_spec : data.ass_spec,
    					ass_brand: data.ass_brand
    				});

    			}
    		}
    		return true;
    	}

        function loadDict() {
        	  checkBox_2 = $('#checkBox_2').etCheck({
                  onChange: function (status, checked, disabled) {
                      if (checked) {
                          // 勾选没有卡片，关闭资产卡片验证
                      	$('#card_no').removeClass("no-empty");
                      	$('#ass_name').removeAttr('disabled');
                          ass_card_no.setValue('none');
                          ass_card_no.disabled();
                          validate.closeValidate(ass_card_no);
                      } else {
                      	$('#card_no').addClass("no-empty")
                      	$('#ass_name').attr('disabled','disabled');
                          ass_card_no.enabled();
                          validate.openValidate(ass_card_no);
                      }
                  },
              });
        	eme_status = $("#eme_status").etSelect({
                	  options: [{
                          id: 1,
                          text: '1 非常紧急'
                      }, {
                          id: 2,
                          text: '2 紧急'
                      }, {
                          id: 3,
                          text: '3 一般'
                      }],
                     defaultValue: 3
                 });

                loc_code = $("#loc_code").etSelect({
                    url: '../../querySuperLocationSelect.do?isCheck=false',
                    
                });
             /*    ass_card_no = $("#ass_card_no").etSelect({
                	url: "../../queryAssCardNoDictSelect.do?isCheck=false&use_state=1,2,3,4,5",
                	defaultValue: "${ass_card_no}",
	          		onChange: function (value) {
	          			   $('#ass_name').val($('#ass_card_no').text().split(' ')[1]);
		          		}
                }); */
                 rep_user = $("#rep_user").etSelect({
                	 url: '../../queryUserDict.do?isCheck=false&key='+'${user_id}',
                     defaultValue: "${user_id}",
                     onInit:function(value){
                    	 ajaxPostData({
                             url: '../../queryDeptDictInitValue.do?isCheck=false&user_id='+'${user_id}',
                             delayCallback: true,
                             success: function (data) {
                            	 if(data){
                            		 var id = ''
                            	 }else{
                            		 var id=data[0].id
                            	 }
                            	 
                            	 rep_dept = $("#rep_dept").etSelect({
                                     url: '../../queryDeptDict.do?isCheck=false',
                          			 defaultValue: id,
                                     onChange:function(value){
                                    	 reloadSelect(value)
                                    	 
                                     },
                                     onInit: function (value) {
                                    	 reloadSelect(value)
                                     }
                                    
                                 }); 
                            	 
                            	 ajaxPostData({
                                     url: '../../queryAccEmpAttr.do?isCheck=false&emp_phone_user='+value,
                                     delayCallback: true,
                                     success: function (data) {
                                    	 $("#phone").val(data[0].text);
                                     }
                            	 });
                             }
                         });
                    	 
                     }
                }); 
                ass_card_no = $("#ass_card_no").etSelect({
     				defaultValue: 'none',
     				onChange : function(value){
     					backwashData(value)
     				}
     			});
                begin_date = laydate.render({
        			elem: '#begin_date',
        			type: 'time',
        			format: 'HH:mm'
        		});
        		end_date = laydate.render({
        			elem: '#end_date',
        			type: 'time',
        			format: 'HH:mm'
        		});
        		app_time = $("#app_time").etDatepicker({
        			defaultDate : true
        		});
        		comp_time = $("#comp_time").etDatepicker({
        			defaultDate : true
        		});
               file = $('#file').etUpload({
                   multiple: true,
                   onShow: function (obj) {
                       var imgsNode = $("#file .upload-preView").find('img');

                       var index = top.$.etDialog.open({
                           type: 2,
                           title: '打印页面' ,
                           frameNameObj: {imgsNode: imgsNode},
                           url: 'hrp/ass/repair/assMyInformRepair/printImgPage.do?isCheck=false',
                        });
                        top.$.etDialog.max(index);
                   }
               }); 
           	$("#searchTree").keyup(function(e) {
				var _this = this;
				searchTree({
					tree : tree,
					value : this.value,
					callback : function(node) {
						$(_this).focus(); //回去焦点
					}
				});
			});

           	/* order_time_begin = $("#order_time_begin").etDatepicker({
                   dateFormat: "yyyy-mm-dd", 
                   defaultDate:'yyyy-mm-fd',
                   });
           	order_time_end = $("#order_time_end").etDatepicker({
                   dateFormat: "yyyy-mm-dd", 
                   defaultDate:'yyyy-mm-ed',
                   }); */
                    // 维修标识
                       rep_bz = $("#rep_bz").etSelect({
                       	 options: [{
       	                         id: 1,
       	                         text: '内部维修'
       	                     }, {
       	                         id: 2,
       	                         text: '外部维修'
       	                     }],
                           defaultValue: "none",
                       })
            }
       
			function reloadSelect (value){
				ass_card_no.reload({
					url: "../../queryAssCardNoDictSelect.do?isCheck=false",
					type: "POST",
					para: {
						 dept_id: value,
						 use_state: '1,2,3,4,5'
					}, 
				});
				
				loc_code.reload({
					url: "../../querySuperLocationSelect.do?isCheck=false",
					type: "POST",
					para: {
						 dept_id: value.split("@")[0]
					}
				});
			}
			
			 function initGrid() {
	                var gridObj = {
	                    editable: false,
	                    height: '100%',
	                   	editable: true,
	                   	checkbox: true,
	                   	usePager:false,
	                    selectionModel: {
	                        type: 'row',
	                        mode: 'block'
	                    }
	                };
	                gridObj.columns = [{
	                        display: "维修材料",
	                        width: 240,
	                        name: "inv_name" ,
	                        editor: {
	                 		     type: 'select', 
	                 		     keyField: 'inv_code',
	                 		     url:'../repairrecord/queryMatInvDictSelect.do?isCheck=false',
	       	          		 change: function(ui){
	                		   		ajaxPostData({
	                		   			url: '../repairrecord/queryMatInvDictSelectInfo.do?isCheck=false&inv_code='+ui.inv_code,
	                		   			success: function (data) {
	       	         		   			ui.inv_name = data.Rows[0].inv_name;
	       	      		   				ui.brand_name = data.Rows[0].brand_name;
	       	      		   				ui.mat_type_name =data.Rows[0].mat_type_name;
	       	      		   				ui.inv_model = data.Rows[0].inv_model;
	       	      		   				
	       	      		   		grid.refreshCell(ui._rowIndx, 'inv_name', false);
	      		   				grid.refreshCell(ui._rowIndx, 'brand_name', false);
	      		   				grid.refreshCell(ui._rowIndx, 'mat_type_name', false);
	      		   				grid.refreshCell(ui._rowIndx, 'inv_model', false);

	                		   			}
	                		   		});
	                		   		//without_id += ui.subject_id + ","
	       	         		  },

	                },
	                
	                },
	                    {
	                        display: "材料分类",
	                        width: 120,
	                        name: "mat_type_name"
	                    },
	                    {
	                        display: '品牌',
	                        name: 'brand_name',
	                        width: 120
	                    },
	                    {
	                        display: "规格型号",
	                        align: "left",
	                        width: 120,
	                        name: "inv_model"
	                    },
	                    {
	                        display: "数量",
	                        width: 120,
	                        align: 'center',
	                        name: "amount",
	                       	dataType :'float'
	                    }
	                ];
	                gridObj.toolbar = {
	                		items: [/* {	
	                			type: "button",
								label: '查询',
								icon: 'search',
								id: 'search',
								listeners: [{
									click: search
								}]
	                		}, */
							{
								type: "button",
								label: '添加',
								icon: 'add',
								id: 'add',
								listeners: [{
									click: addRep
								}]
							}]
	                };
	                gridObj.dataModel = { // 数据加载的有关属性
	                    location: 'remote',
	                    url: '',
	                    recIndx: 'a'
	                };
	                grid = $("#mainGrid").etGrid(gridObj);
	            }
			  function search(){
	            	var param = [{
						name: 'inv_name',
						value: $('#inv_name').val()
					},
				];
				grid.loadData(param,'');
	            }
			 function backwashData(data){
					$('#ass_name').val($('#ass_card_no').text().split(' ')[1])
			 }
			 
				function addRep() {
					 var data = {};
		                grid.addRow(data)

				}
            function loadForm() {
                validate = $.etValidate({
                    items: [
                        {
                            el: $("#eme_status"),
                            required: true
                        },
                        {
                            el: $("#rep_user"),
                            required: true
                        },
                        {
                            el: $("#loc_code"),
                            required: true
                        },
                        {
                            el: $("#rep_dept"),
                            required: true
                        },
                        {
                            el: $("#phone"),
                            required: true
                        },
                        {
                            el: $("#fau_note"),
                            required: true
                        },
                        {
                            el: $("#rep_bz"),
                            required: true
                        }
                    ]
                })
            }
			
            function loadTree() {
            	
                tree = $("#mainTree").etTree({
                    async: {
                        enable: true,
                        url: '../assFaultType/queryAssFaultTypeTree.do?isCheck=false'
                    } 
                })
                
            }
            
            function savePrmGoal() {
                if (validate.test()) {
                    save();

				

                }
            }

            function save() {
            	  var gridData = grid.getAllData();
                  var ParamVo = [];
                  var isTrue = false ; 
  	            $(gridData).each(function () {
  	                var rowdata = this;
  	                if(!rowdata.amount){
  	                	   $.etDialog.error('请维护【'+rowdata.inv_name+'】材料数量');
  	                	   isTrue=true
  	                	   return false;
  	                }
  	                ParamVo.push(rowdata.inv_code+' '+rowdata.amount);
  	            });
            	  var selected = tree.getSelectedNodes()[0];
                  var sId;
                  if(selected){
                	  sId = selected.id;
                	  if(selected.isParent){
                		  $.etDialog.error('请左侧选择末级故障分类');
                		  return;
                	  }
                  }else{
               		 $.etDialog.error('请左侧选择末级故障分类');
               		 return;
                	 
                  }
                  var formData = new FormData();
                 
            	formData.append("eme_status" ,$("#eme_status").val())
            	formData.append("seq_no" ,$("#seq_no").val())
            	formData.append("rep_dept" ,$("#rep_dept").val())
            	formData.append("loc_code" ,$("#loc_code").val())
            	formData.append("ass_card_no",$("#ass_card_no").text().split(' ')[0])
            	formData.append("ass_name",$("#ass_name").val())
            	formData.append("rep_user" ,$("#rep_user").val())
            	formData.append("phone",$("#phone").val())
            	formData.append("fau_note" ,$("#fau_note").val())
            	formData.append("fau_code",sId) 
            	formData.append('task_user',${user_id})
                //formData.append('rep_code', value[0].rowData.rep_code)
                //formData.push('ass_card_no',$('#ass_card_no').val())
               // formData.push('ass_name', $('#ass_name').val())
                formData.append('rep_bz', $('#rep_bz').val())
                formData.append('rep_comp_user', $('#rep_comp_user').val())
                formData.append('rep_comp', $('#rep_comp').val())
                formData.append('rep_note', $('#rep_note').val())
                formData.append('invdata',ParamVo)
               formData.append('is_base',$('#checkBox_1').get(0).checked == true ? 1:0)
            formData.append('is_card', $('#checkBox_2').get(0).checked == true ? 1:0)
             formData.append('begin_date',$("#begin_date").val())
			formData.append('end_date',$("#end_date").val())
			formData.append('app_time', app_time.getValue())
			formData.append('comp_time',comp_time.getValue())
                //formData.append('file', file.getValues());
            	 for(var i=0;i<file.getValues().length;i++){
            		formData.append("file"+i, file.getValues()[i]);
            	}  
            	//formData.append("files", file.getValues());
                  ajaxPostFormData({
                      url: 'addAssRepairRecord.do?isCheck=false',
                      data: formData,
                      dataType: 'json',
                      success: function (data) { 
                    	  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
  					var parentFrameName = parent.$.etDialog.parentFrameName;
  					var parentWindow = parent.window[parentFrameName];
  					parentWindow.search(); 
  					 parent.$.etDialog.close(curIndex);
              }
                  })
            	
			 
            }
        </script>
    </head>

    <body>
    <div class="container"> 
     <div class="left border-right" style="width: 150px;"> 
            <div class="button-group">
            </div>
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" id="searchTree" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        
        <div class="center" style="width: 100%;overflow:auto;width:100%;overflow-x:auto;border: 1px solid rgb(204, 204, 204);">
            <table class="table-layout">
					<tr>
						<td class="label">紧急程度：</td>
						<td class="ipt">
							<select id="eme_status" style="width:180px"></select>
						</td>
						<td class="label">序列号：</td>
						<td class="ipt">
							<input id="seq_no" style="width:180px" type="text"/>
						</td>
						</tr>
					<tr>
						<td class="label  no-empty">报修科室：</td>
						<td class="ipt">
							<select id="rep_dept" style="width:180px"></select>
						</td>
						
						<td class="label  no-empty">位置：</td>
						<td class="ipt">
							<select id="loc_code" type="text" style="width:180px"></select>
						</td>
					
					</tr>
					<tr>
						<td class="label">资产卡片：</td>
						<td class="ipt">
							<select id="ass_card_no" style="width:180px" disabled="disabled"></select>
							<input type="checkbox" id="checkBox_2" checked/>
                        <label for="checkBox_2">没有卡片</label>
						</td>
						<td class="label">资产名称：</td>
						<td class="ipt">
							<input id="ass_name" style="width:180px" type="text" />
						</td>
					</tr>
					<tr>
						<td class="label">报修人：</td>
						<td class="ipt">
							<select id="rep_user" type="text" style="width:180px" disabled="disabled"></select>
 						</td>
						
						<td class="label  no-empty">报修人电话：</td>
						<td class="ipt">
							<input id="phone" style="width:180px" type="text"/>
						</td>
						
					</tr>
					<tr>
					<td class="label">报修日期：</td>
				<td class="ipt"><input id="app_time" type="text" /></td>
					<td class="label">报修时间：</td>
				<td class="ipt">
					<input id="begin_date"  type="text" placeholder="HH:mm" style="width: 180px;"/>
				</td>
					</tr>
	                <tr>
	                    <td class="label  no-empty">问题描述：</td>
	                    <td class="ipt" colspan="2" >
	                         <textarea rows="5" cols="50" name="fau_note" id="fau_note"></textarea>
	                    </td>
	                    	
				  <td class="label">文件：</td>
	                    <td class="ipt" >
	                          <div id="file"></div> 
	                    </td>
	                </tr>
	                 <tr>
                    <td class="label no-empty">维修标识：</td>
                    <td class="ipt" style="width:310px">
                        <select id="rep_bz" style="width:180px"></select>&nbsp;&nbsp;
                        <input type="checkbox" id="checkBox_1">
                        <label for="checkBox_1">进入故障知识库</label>
                    </td>
                  <!--   <td class="label no-empty" id="card_no" >资产卡片：</td>
                    <td class="ipt">
                        <select id="ass_card_no" style="width:180px" ></select>&nbsp;&nbsp;
                    	<input type="checkbox" id="checkBox_2" />
                        <label for="checkBox_2">没有卡片</label>
                    </td> -->
              
                    <td class="label" id="comp_user" >维修工程师：</td>
                    <td class="ipt">
                        <input type="text" id="rep_comp_user" />
                    </td>
                      </tr>	<tr>
					<td class="label">维修日期：</td>
				<td class="ipt"><input id="comp_time" type="text" /></td>
					<td class="label">维修时间：</td>
				<td class="ipt">
					<input id="end_date"  type="text" placeholder="HH:mm" style="width: 180px;"/>
				</td>
					</tr>
                <tr>
                    <td class="label" id="comp">维修公司：</td>
                    <td class="ipt">
                        <input id="rep_comp" type="text" />
                    </td>
                    
              <!--   </tr>
                <tr> -->
                   <!--  <td class="label no-empty" id="card_name">资产名称：</td>
                    <td class="ipt">
                        <input type="text" id="ass_name"  disabled="disabled"  />
                    </td> -->
                   <!--  <td class="label">维修材料：</td>
                    <td class="ipt">
                        <input id="inv_name" type="text" />
                    </td> -->
                </tr>
                <tr>
                	<td class="label">维修结果：</td>
                    <td class="ipt">
                        <textarea style="width: 350px;height:50px" name="rep_note" id="rep_note" cols="30" rows="10">完成</textarea>
                    </td>
                </tr>
            </table>
			 
        </div>
       
        
    </div>
     <div id="mainGrid"></div>
    </body>

    </html>