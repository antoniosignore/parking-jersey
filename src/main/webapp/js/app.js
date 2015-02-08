angular.module('App', ['ui.router'])
    .config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('index', {
                url: '/',
                views: {
                    '@': {
                        templateUrl: 'layout.html',
                        controller: 'IndexCtrl'
                    },
                    'top@index': {templateUrl: 'tpl.top.html'},
                    'left@index': {templateUrl: 'tpl.left.html'},
                    'main@index': {templateUrl: 'tpl.main.html'}
                }
            })
            .state('index.list', {
                url: '/list',
                templateUrl: 'list.html',
                controller: 'ListCtrl'
            })
            .state('index.list.detail', {
                url: '/:id',
                views: {
                    'detail@index': {
                        templateUrl: 'detail.html',
                        controller: 'DetailCtrl'
                    }
                }
            })

            // this definition switches the targets
            .state('index.list2', {
                url: '/list2',
                views: {
                    'detail@index': {
                        templateUrl: 'list2.html',
                        controller: 'ListCtrl'
                    }
                }
            })
            .state('index.list2.detail', {
                url: '/:id',
                views: {
                    '@index': {
                        templateUrl: 'detail.html',
                        controller: 'DetailCtrl'
                    },
                    'list2detailtip': {
                        template: '<div>current selection is {{id}}</div>',
                        controller: 'DetailCtrl'
                    }
                }
            })
    })
    .controller('IndexCtrl', function () {
    })
    .controller('ListCtrl', function () {
    })
    .controller('DetailCtrl', function ($scope, $stateParams) {
        $scope.id = $stateParams.id;
    });