angular.module('services.account', [])
    .factory('Accounts',function($http){
        return {
            addAccount:function(params){
                return $http({method:'post',url:'/AccountService/addAccount',params:params})
            },
            getAccounts:function() {
                return $http({method:'get',url:'/AccountService/getAccounts'})
            }
        }
    });