<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- 维修进度滚动条样式 -->
        <style>
            /*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/

            #mainTree::-webkit-scrollbar {
                width: 10px;
                height: 10px;
                background-color: #f5f5f5;
            }
            /*定义滚动条的轨道，内阴影及圆角*/

            #mainTree::-webkit-scrollbar-track {
                -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
                border-radius: 10px;
                background-color: #f5f5f5;
            }
            /*定义滑块，内阴影及圆角*/

            #mainTree::-webkit-scrollbar-thumb {
                /*width: 10px;*/
                height: 20px;
                border-radius: 10px;
                -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
                background-color: rgb(207, 207, 207);
            }
            /*滑块效果*/

            #mainTree::-webkit-scrollbar-thumb:hover {
                border-radius: 5px;
                -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
                background: rgba(78, 78, 78, 0.4);
            }
        </style>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,dialog,grid,select,tree, validate" name="plugins" />
        </jsp:include>
        <script>
            var tree;

            function loadTree() {
                tree = $("#mainTree").etTree({
                    async: {
                        enable: true,
                        url: 'queryRepTeamUser.do?isCheck=false'
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode) {
                            tree.checkNode(treeNode,true,false)
                        }
                    }/* ,
                    check: {
                        enable: true
                        //chkStyle: "radio"
                        
                    } */
                })
            }

           
           

            function saveData(value) {
                var selectNode = tree.getSelectedNodes()[0];
            	var user_id;
                var formData = [];
                if(selectNode){
                	user_id = selectNode.id;
              	  if(selectNode.flag=='team'){
              		  $.etDialog.error('只能选择维修工程师，不能选择维修班组！');
              		  return;
              	  }
                }else{
             		 $.etDialog.error('请左侧选择维修工程师');
             		 return;
              	 
                }
                formData.push({name:'task_user',value:user_id})
                formData.push({name:'rep_code',value: value})
                console.log(formData)
                ajaxPostData({
                    url: 'updateRepUser.do?isCheck=false',
                    data: formData,
                    success: function () {
                        parent.query();
                    }
                })
            }
            $(function () {
                loadTree();

            })
        </script>
    </head>

    <body>
        <div class="container" style="padding:10px;border:1px solid #ddd">
            <div class="left border-right" style="border:1px solid #ddd">
                <div id="mainTree"></div>
                <div class="container-bar"></div>
            </div>
            
        </div>
    </body>

    </html>