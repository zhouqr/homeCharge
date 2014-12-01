

angular.module('app', ['ngAnimate',
                       'ngCookies',
                       'ngStorage',
                       'ui.router',
                       'ui.bootstrap',
                       'ui.load',
                       'ui.jq',
                       'ui.validate',
                       'oc.lazyLoad',
                       'pascalprecht.translate','homeCharge','app.controllers','app.directives'])
      .run(
  [          '$rootScope', '$state', '$stateParams',
    function ($rootScope,   $state,   $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;        
    }
  ]
)
    
    /**
 * jQuery plugin config use ui-jq directive , config the js and css files that required
 * key: function name of the jQuery plugin
 * value: array of the css js file located
 */
.constant('JQ_CONFIG', {
    easyPieChart:   ['public/javascripts/jquery/charts/easypiechart/jquery.easy-pie-chart.js'],
    sparkline:      ['public/javascripts/jquery/charts/sparkline/jquery.sparkline.min.js'],
    plot:           ['public/libs/jquery/charts/flot/jquery.flot.min.js', 
                        'public/libs/jquery/charts/flot/jquery.flot.resize.js',
                        'public/libs/jquery/charts/flot/jquery.flot.tooltip.min.js',
                        'public/libs/jquery/charts/flot/jquery.flot.spline.js',
                        'public/libs/jquery/charts/flot/jquery.flot.orderBars.js',
                        'public/libs/jquery/charts/flot/jquery.flot.pie.min.js'],
    slimScroll:     ['public/javascripts/jquery/slimscroll/jquery.slimscroll.min.js'],
    sortable:       ['public/javascripts/jquery/sortable/jquery.sortable.js'],
    nestable:       ['public/javascripts/jquery/nestable/jquery.nestable.js',
                        'public/javascripts/jquery/nestable/nestable.css'],
    filestyle:      ['public/javascripts/jquery/file/bootstrap-filestyle.min.js'],
    slider:         ['public/javascripts/jquery/slider/bootstrap-slider.js',
                        'public/javascripts/jquery/slider/slider.css'],
    chosen:         ['public/javascripts/jquery/chosen/chosen.jquery.min.js',
                        'public/javascripts/jquery/chosen/chosen.css'],
    TouchSpin:      ['public/javascripts/jquery/spinner/jquery.bootstrap-touchspin.min.js',
                        'public/javascripts/jquery/spinner/jquery.bootstrap-touchspin.css'],
    wysiwyg:        ['public/javascripts/jquery/wysiwyg/bootstrap-wysiwyg.js',
                        'public/javascripts/jquery/wysiwyg/jquery.hotkeys.js'],
    dataTable:      ['public/javascripts/jquery/datatables/jquery.dataTables.min.js',
                        'public/javascripts/jquery/datatables/dataTables.bootstrap.js',
                        'public/javascripts/jquery/datatables/dataTables.bootstrap.css'],
    vectorMap:      ['public/javascripts/jquery/jvectormap/jquery-jvectormap.min.js', 
                        'public/javascripts/jquery/jvectormap/jquery-jvectormap-world-mill-en.js',
                        'public/javascripts/jquery/jvectormap/jquery-jvectormap-us-aea-en.js',
                        'public/javascripts/jquery/jvectormap/jquery-jvectormap.css'],
    footable:       ['public/javascripts/jquery/footable/footable.all.min.js',
                        'public/javascripts/jquery/footable/footable.core.css'],
    my97DatePicker:  ['public/public/libs/jquery/datepicker97/WdatePicker.js'],
    }
)

// modules config
.constant('MODULE_CONFIG', {
    select2:        ['public/javascripts/jquery/select2/select2.css',
                        'public/javascripts/jquery/select2/select2-bootstrap.css',
                        'public/javascripts/jquery/select2/select2.min.js',
                        'public/javascripts/modules/ui-select2.js']
    }
)

// oclazyload config
.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    // We configure ocLazyLoad to use the lib script.js as the async loader
    $ocLazyLoadProvider.config({
        debug: false,
        events: true,
        modules: [
            {
                name: 'ngGrid',
                files: [
                    'public/javascripts/modules/ng-grid/ng-grid.min.js',
                    'public/javascripts/modules/ng-grid/ng-grid.css',
                    'public/javascripts/modules/ng-grid/theme.css'
                ]
            },
            {
                name: 'toaster',
                files: [                    
                    'public/javascripts/modules/toaster/toaster.js',
                    'public/javascripts/modules/toaster/toaster.css'
                ]
            }
        ]
    });
}])
     
      //登录
  .controller('LoginController', ['$scope', '$http', '$state', function($scope, $http, $state) {
    $scope.user = {};
    $scope.signin = function() {
      // Try to create
    	$http({method:'get',url:'/loginService/login',params:$scope.user})
    	.success(function(data){
      	  if(data.code == 401){
      		  alert("用户名或密码错误！");
      		  $scope.user = {};
      	  }
      	  else if(data.code == 200){
      		  location.href = "/";
      	  }
        }, function(x) {
          $scope.authError = 'Server Error';
        });
    	
    	
    };
  }])
 ;