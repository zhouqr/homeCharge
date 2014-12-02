
angular.module('charge.account.add',[])
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
  .controller('AccountAddCtrl', ['$scope','$http','$modal','Topics', function($scope,$http,$modal,Topics) {
	  $scope.topic = {};
	  function alertHelp(className,info){
  		  $("#alertinfo").slideDown(); 
  		  $scope.alertInfo ={
  				alertinfoClass:className,
  				info:info,
  		  };
  		  window.setTimeout(function(){$("#alertinfo").slideUp();},2000); //2秒钟自动关闭
	  };
	
	  $scope.addYes = function(){
		  $scope.topic.startTime = $("#d4311").val();
		  $scope.topic.endTime = $("#d4312").val();
		  Topics.topicAdd($scope.topic)
		  .success(function(data){
		    	  if(data.code == 401){
		    		  alertHelp("alert-danger","专题名称重复！");
		    		  $scope.topic = {};
		    	  }
		    	  else if(data.code == 200){
		    		  alertHelp("alert-success","专题添加成功！");
		    		  $("#successinfo").slideDown();
		    	  }
		      }, function(x) {
		    }).error(function(data){
		    	  alertHelp("alert-danger","专题添加失败！");
	    		  $scope.topic = {};
		    });
	  };
	  $scope.reset = function(){
		  $scope.topic = {};
	  };
	  
	 
  }])
