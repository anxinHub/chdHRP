<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>assLocationAdd</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,select,dialog,validate,grid,tree,upload" name="plugins" />
    </jsp:include>
    <script>
        var  eme_status, rep_dept,rep_user, loc_code, ass_card_no,validate ,tree,file;
        $(function () {
            loadDict();
            loadTree();
            loadForm();
        })

            function loadDict() {
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
                     defaultValue: 2
                 });

                 loc_code = $("#loc_code").etSelect({
                     url: '../../querySuperLocationSelect.do?isCheck=false'
                 });
 				 
                 rep_user = $("#rep_user").etSelect({
                	 url: '../../queryUserDict.do?isCheck=false',
                     defaultValue: "none",
                }); 
                 ass_card_no = $("#ass_card_no").etSelect({
     				defaultValue: 'none',
     				onChange : function(value){
     					backwashData(value)
     				}
     			});
                 rep_dept = $("#rep_dept").etSelect({
                     url: '../../queryDeptDict.do?isCheck=false',
          			 defaultValue: 'none',
                     onChange:function(value){
                    	 reloadSelect(value)
                    	 
                     },
                     onInit: function (value) {
                     	if("none"!=value){
                     		reloadSelect(value)
                     	}
                     }
                    
                 }); 
              
               file = $('#file').etUpload({
            	   multiple: true
               }); 
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
				if(""!=value){
					loc_code.reload({
						url: "../../querySuperLocationSelect.do?isCheck=false",
						type: "POST",
						para: {
							 dept_id: value.split("@")[0]
						}
					});
				}else{
					loc_code.clearOptions();
				}
			}
			
			
			 function backwashData(data){
					$('#ass_name').val($('#ass_card_no').text().split(' ')[1])
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
                            required: false
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
            
            function saveData() {
                if (validate.test()) {
                    save();
                }
            }

            function save() {
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
                //formData.append('file', file.getValues());
            	 for(var i=0;i<file.getValues().length;i++){
            		formData.append("file"+i, file.getValues()[i]);
            	}  
            	//formData.append("files", file.getValues());
                  ajaxPostFormData({
                      url: 'addAssRepReportCentre.do',
                      data: formData,
                      dataType: 'json',
                      success: function (data) { 
                  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                      parent.$.etDialog.close(curIndex);
                      //父级查询
                      parent.search();}
                  })
            	
			 
            }
        </script>
    </head>

    <body>
    <div class="container"> 
     <div class="left border-right">
            <div class="button-group">
            </div>
            <div class="search-form">
                <label>快速定位</label>
                <input type="text" id="searchTree" class="text-input">
            </div>
            <div id="mainTree"></div>
            <div class="container-bar"></div>
        </div>
        
        <div class="center">
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
						<td class="label">报修科室：</td>
						<td class="ipt">
							<select id="rep_dept" style="width:180px"></select>
						</td>
						<td class="label">位置：</td>
						<td class="ipt">
							<select id="loc_code" type="text" style="width:180px"></select>
						</td>
					</tr>
					<tr>
						<td class="label">资产卡片：</td>
						<td class="ipt">
							<select id="ass_card_no" style="width:180px"></select>
						</td>
						<td class="label">资产名称：</td>
						<td class="ipt">
							<input id="ass_name" style="width:180px" type="text"/>
						</td>
					</tr>
					<tr>
						<td class="label">报修人：</td>
						<td class="ipt">
							<select id="rep_user" type="text" style="width:180px" ></select>
 						</td>
						
						<td class="label">报修人电话：</td>
						<td class="ipt">
							<input id="phone" style="width:180px" type="text"/>
						</td>
						
					</tr>
	                <tr>
	                    <td class="label">问题描述：</td>
	                    <td class="ipt" colspan="4" >
	                         <textarea rows="5" cols="50" name="fau_note" id="fau_note"></textarea>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label">文件：</td>
	                    <td class="ipt" colspan="4">
	                          <div id="file"></div> 
	                    </td>
	                </tr>
            </table>
			
        </div>
    </div>
    
    </body>

    </html>