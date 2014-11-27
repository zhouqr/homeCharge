
angular.module('homeCharge.charge',[])
    .config(function ($stateProvider){
        $stateProvider
            .state('homeCharge.charge',{
            	abstract:true,
                url:'/charge',
                templateUrl: appPath + '/charge/view.html',
            })
        })
    
    
    



