
angular.module('charge.account.add',['services.account'])
    .config(function($stateProvider){
        $stateProvider
            .state('charge.account.add',{
                url:'/add',
                templateUrl: appPath + '/charge/account/add/view.html',
                controller:function($scope,$stateParams,$state){
                	console.log("");
                }
            })
    })
   
    //添加专题
  .controller('AccountAddCtrl', ['$scope','$http','$modal','Users','Accounts', function($scope,$http,$modal,Users,Accounts) {
	  Users.allUsers().success(function(data){
		  $scope.users = data.info;
		  console.log(data.info);
	  });
	  $scope.account = {};
	  function alertHelp(className,info){
  		  $("#alertinfo").slideDown(); 
  		  $scope.alertInfo ={
  				alertinfoClass:className,
  				info:info,
  		  };
  		  window.setTimeout(function(){$("#alertinfo").slideUp();},2000); //2秒钟自动关闭
	  };
	
	  $scope.addYes = function(){
		  $scope.account.date = $("#d4311").val();
		  debugger
		  $scope.account.userIds = "";
		  var ids ="";
		  $(".checkUserIds").each(function(){
			  if(this.checked)
			      ids += $(this).val()+",";
		  });
		  if(ids!="")
			  $scope.account.userIds = ids.substring(0,ids.length-1);
		  
		  Accounts.addAccount($scope.account)
		  .success(function(data){
		    	  if(data.code == 401){
		    		  alertHelp("alert-danger",data.msg);
		    		  $scope.topic = {};
		    	  }
		    	  else if(data.code == 200){
		    		  alertHelp("alert-success","添加成功！");
		    		  $("#successinfo").slideDown();
		    	  }
		      }, function(x) {
		    }).error(function(data){
		    	  alertHelp("alert-danger","添加失败！");
	    		  $scope.account = {};
		    });
	  };
	  $scope.reset = function(){
		  $scope.account = {};
	  };
	  
	 
  }])
