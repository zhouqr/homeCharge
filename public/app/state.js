/**
 * Created with IntelliJ IDEA.
 * User: xuanzhang
 * Date: 14-3-18
 * Time: 下午11:55
 * To change this template use File | Settings | File Templates.
 */
angular.module('homeCharge',['homeCharge.charge'])

    .config(function($urlRouterProvider){
        $urlRouterProvider.otherwise('/charge/home')
    })