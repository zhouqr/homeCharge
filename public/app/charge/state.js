/**
 * Created with IntelliJ IDEA.
 * User: xuanzhang
 * Date: 14-3-18
 * Time: 下午10:39
 * To change this template use File | Settings | File Templates.
 */
angular.module('homeCharge.charge',['homeCharge.charge.home'])
    .config(function ($stateProvider){
        $stateProvider
            .state('charge',{
                url:'/charge',
                abstract:true,
                templateUrl: appPath + '/charge/view.html'
            })
    })



