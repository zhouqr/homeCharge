
angular.module('charge.account',['charge.account.add'])
    .config(function($stateProvider){
        $stateProvider
            .state('charge.account',{
                url:'/account',
                templateUrl: appPath + '/charge/account/view.html',
                controller:function($scope,$stateParams,$state){
                }
            })
    })
