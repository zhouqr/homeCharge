/**
 * 
 */
angular.module('homeCharge.charge.home',['ui.router'])
    .config(function ($stateProvider){
        $stateProvider
            .state('charge.home',{
                url:'/home',
                templateUrl: appPath + '/charge/home/view.html'
            })
    })



