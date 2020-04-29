<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>assLocationAdd</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,select,dialog,validate,grid,tree,upload" name="plugins" />
    </jsp:include>
    <script>
        var rep_code, eme_status, rep_dept,rep_user, loc_code, ass_card_no,validate ,tree,file;
        var pathName= []; 
        
        $(function () {
            loadDict();
            loadTree();
            loadForm();
            loadInit();
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
                     defaultValue: "${eme_status}"
                 });

                 loc_code = $("#loc_code").etSelect({
                     url: '../../querySuperLocationSelect.do?isCheck=false&is_last=1',
                     defaultValue: "${loc_code}"
                 });
                 rep_dept = $("#rep_dept").etSelect({
                     url: '../../queryDeptDict.do?isCheck=false',
                     defaultValue: '${rep_dept}',
                     onChange:function(value){
                    	 reloadSelect(value)
                    	 
                     },
                     onInit: function (value) {
                    	 reloadSelect(value)
                     }
                    
                 });
                 rep_user = $("#rep_user").etSelect({
                	 url: '../../queryUserDict.do?isCheck=false&key='+'${rep_user}',
                     defaultValue: "${rep_user}"/*,
                     onInit:function(value){
                    	 ajaxPostData({
                             url: '../../queryDeptDictInitValue.do?isCheck=false&user_id='+'${user_id}',
                             delayCallback: true,
                             success: function (data) {
                            	 
                            	 
                             }
                         })
                     } */
                }); 
                 ass_card_no = $("#ass_card_no").etSelect({
     				defaultValue: '${ass_card_no}',
     				onChange : function(value){
     					backwashData(value)
     				}
     			});
                 
                 function backwashData(data){
 					$('#ass_name').val($('#ass_card_no').text().split(' ')[1])
 			 }
                 
               file = $('#file').etUpload({
                   multiple: true
               });
               
              
            }
        	function loadInit(){
        		ajaxPostData({
                    url: "queryImgUrlByRepCode.do?isCheck=false",
                    data: {'rep_code':${rep_code}},
                    success: function (res) {
                        	$(res).each(function(index,item){
                        		pathName.push('<%=path%>/'+item.ATT_PATH+'/'+item.ATT_NAME_N)
                        	})
                        		file.setValues(pathName); 
                        
                    }
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
            function loadForm() {
                validate = $.etValidate({
                    items: [{
                            el: $("#rep_code"),
                            required: true
                        },
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
                        }
                    ]
                })
            }
			
            function loadTree() {
            	
                tree = $("#mainTree").etTree({
                    async: {
                        enable: true,
                        url: '../assFaultType/queryAssFaultTypeTree.do?isCheck=false'
                    },
                    callback: {
	                    onAsyncSuccess: function () {
			                var node = tree.getNodeByParam("id", '${fau_code}');
			                tree.selectNode(node);
	                    	
	                    }
                    }
                }); 
                //tree.selectNode(node[0]);
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
					var fileName = [];
					for(var i =0 ;i<=pathName.length;i++){
						   var count=0;
						   var name =pathName[i]
						for(var j = 0 ;j<=file.getValues().length ; j++){
							
							if(pathName[i]==file.getValues()[j]){
							   count++;
							   break;
							}
							
						}
						 if(count==0){//表示数组1的这个值没有重复的，放到返回列表中
						      fileName.push(name);
							}
					}
                var formData = new FormData();
            	formData.append("rep_code" ,$("#rep_code").val())
            	formData.append("eme_status" ,$("#eme_status").val())
            	formData.append("rep_dept" ,$("#rep_dept").val())
            	formData.append("loc_code" ,$("#loc_code").val())
            	formData.append("ass_card_no",$("#ass_card_no").text().split(' ')[0])
            	formData.append("ass_name",$("#ass_name").val())
            	formData.append("rep_user" ,$("#rep_user").val())
            	formData.append("phone",$("#phone").val())
            	formData.append("fau_note" ,$("#fau_note").val())
            	formData.append("fau_code",sId)
				formData.append("old_path",fileName)
				for(var i=0;i<file.getValues().length;i++){
	            		formData.append("file"+i, file.getValues()[i]);
            	}  
				ajaxPostFormData({
                    url: 'updateAssRepReportCentre.do?isCheck=false',
                    data: formData,
                    delayCallback: true,
                    success: function (data) {
                    	//关闭
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex);
                        //父级查询
                        parent.search();
                       
                    }
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
					
					<td class="label">报修单号：</td>
						<td class="ipt">
							<input id="rep_code" style="width:180px" type="text"  value="${rep_code}" disabled="disabled"/> 
						</td>
						<td class="label">紧急程度：</td>
						<td class="ipt">
							<select id="eme_status" style="width:180px"></select>
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
							<input id="ass_name" style="width:180px" value="${ass_name}" type="text"/>
						</td>
					</tr>
					<tr>
						<td class="label">报修人：</td>
						<td class="ipt">
							<select id="rep_user" type="text" style="width:180px" disabled="disabled"></select>
 						</td>
						<td class="label">报修人电话：</td>
						<td class="ipt">
							<input id="phone" style="width:180px" type="text" value="${phone}"/>
						</td>
						
					</tr>
	                <tr>
	                    <td class="label">问题描述：</td>
	                    <td class="ipt" colspan="4" >
	                         <textarea rows="5" cols="50" name="fau_note" id="fau_note">${fau_note}</textarea>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="label">文件：</td>
	                    <td class="ipt" colspan="4">
	                    	<%-- <input name="old_file_url" id="old_file_url"value="${att_path}/${att_name_n}" type="hidden" > --%>
	                    	<%--
	                        <input type="file" name = "file" id = "file" value="<%=path%>/${att_path}/${att_name_n}" > --%>
	                        <div id="file"></div>
	                    </td>
	                </tr>
            </table>
			
        </div>
    </div>
    
    </body>

    </html>