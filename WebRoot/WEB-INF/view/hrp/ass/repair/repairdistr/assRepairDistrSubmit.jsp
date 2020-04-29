<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.ettab-container{
		flex:1
	}
</style>
 <title>Insert title here</title>
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,tab" name="plugins" />
    </jsp:include>
    <style>
    	.ettab-container {
                height: 100%;
                display: -webkit-flex;
                display: -ms-flex;
                display: flex;
            }

            .ettab-nav {
                padding: 0;
                margin: 0;
            }

             .ettab-nav .ettab-tab {
                /* padding: 0;
                margin: 0;
                width: 30%; 
                box-sizing: border-box; */
                height: 100%;
                text-align: center
            } 

            .ettab-content .ettab-panel {
                padding: 0;
            }
            
             .ettab-content .ettab-panel.ettab-active {
                display: flex;
                flex-direction: column;
            }
            
            /*  .ztree {
                height: 1px;
            } */
    </style>
    <script type="text/javascript">
   
    var  userTree;
    $(function () {
    	$("#etTabUser").etTab();
        loadTree();
        $("#searchUserTree").keyup(function (e) { // 树快速定位
            var _this = this;
        	searchTree({
                tree: userTree,
                value: this.value,
                callback: function (node) {
                    $(_this).focus(); //回去焦点
                }
            });
        });
   });
	
	 
    function loadTree() {
    	userTree = $("#userTree").etTree({
            async: {
                enable: true,
                url: '../assRepRule/queryRepTeamUser.do?isCheck=false'
            }
            
        })
    	 
    }
        
    function save (){
    	var selectNode = userTree.getSelectedNodes()[0];
    	var user_id;
        if(selectNode){
        	user_id = selectNode.id;
      	  if(selectNode.FLAG=='team'){
      		  $.etDialog.error('只能选择维修工程师，不能选择维修班组！');
      		  return;
      	  }
        }else{
     		 $.etDialog.error('请选择维修工程师');
     		 return;
      	 
        }
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.updateRepairDistrState(user_id,curIndex)
        
     
    }
    </script>
</head>
<body>
	<div class="container">
		<div id="etTabUser">
		       <div class="etTab" title="维修班组与员工">
		           <div class="search-form">
		               <label>快速定位</label>
		               <input type="text" id="searchUserTree" class="text-input">
		           </div>
		           <div id="userTree" class="flex-item-1"></div>
	    	  </div>
        </div>
   </div>
</body>
</html>