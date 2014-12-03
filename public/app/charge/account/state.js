
angular.module('charge.account',['charge.account.add','charge.account.list'])
    .config(function($stateProvider){
        $stateProvider
            .state('charge.account',{
                url:'/account',
                templateUrl: appPath + '/charge/account/view.html',
                controller:function($scope,$stateParams,$state){
                }
            })
    })
