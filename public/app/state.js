angular.module('homeCharge', ['homeCharge.charge','homeCharge.directives','homeCharge.controllers','homeCharge.filters'])
.config(function($urlRouterProvider){
    $urlRouterProvider.otherwise('/homeCharge/charge')
})