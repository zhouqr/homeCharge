
angular.module('charge.account.list',['services.account'])
    .config(function($stateProvider){
        $stateProvider
            .state('charge.account.list',{
                url:'/list',
                templateUrl: appPath + '/charge/account/list/view.html',
                controller:function($scope,$stateParams,$state){
                	console.log("");
                }
            })
    })
   
    //添加专题
  .controller('AccountListCtrl', ['$scope','$http','$modal','Accounts', function($scope,$http,$modal,Accounts) {
	  
	  Accounts.getAccounts().success(function(data){
	    	 $scope.accounts = data.info; 
	      });
	  
	 $scope.chargeAll = function(){
		 Accounts.chargeAll()
		  .success(function(data){
		    	  if(data.code == 401){
		    		  alertHelp("alert-danger",data.msg);
		    		  $scope.topic = {};
		    	  }
		    	  
		      }, function(x) {
		    }).error(function(data){
		    	  alertHelp("alert-danger","添加失败！");
	    		  $scope.account = {};
		    });
	 };
	 
  }])
