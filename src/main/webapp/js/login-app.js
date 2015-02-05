angular.module('exampleApp', ['ngRoute', 'ngCookies', 'exampleApp.services'])
    .config(
    ['$routeProvider', '$locationProvider', '$httpProvider', function ($routeProvider, $locationProvider, $httpProvider) {

        $routeProvider.when('/create', {
            templateUrl: 'partials/create.html',
            controller: CreateController
        });

        $routeProvider.when('/edit/:id', {
            templateUrl: 'partials/edit.html',
            controller: EditController
        });

        $routeProvider.when('/login', {
            templateUrl: 'partials/login.html',
            controller: LoginController
        });

        $routeProvider.otherwise({
            templateUrl: 'partials/index-original.html',
            controller: IndexController
        });

        $locationProvider.hashPrefix('!');

        /* Register error provider that shows message on failed requests or redirects to login page on
         * unauthenticated requests */
        $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
                return {
                    'responseError': function (rejection) {
                        var status = rejection.status;
                        var config = rejection.config;
                        var method = config.method;
                        var url = config.url;

                        if (status == 401) {
                            $location.path("/login");
                        } else {
                            $rootScope.error = method + " on " + url + " failed with status " + status;
                        }

                        return $q.reject(rejection);
                    }
                };
            }
        );

        /* Registers auth token interceptor, auth token is either passed by header or by query parameter
         * as soon as there is an authenticated account */
        $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
                return {
                    'request': function (config) {
                        var isRestCall = config.url.indexOf('rest') == 0;
                        if (isRestCall && angular.isDefined($rootScope.authToken)) {
                            var authToken = $rootScope.authToken;
                            if (exampleAppConfig.useAuthTokenHeader) {
                                config.headers['X-Auth-Token'] = authToken;
                            } else {
                                config.url = config.url + "?token=" + authToken;
                            }
                        }
                        return config || $q.when(config);
                    }
                };
            }
        );

    }]
).run(function ($rootScope, $location, $cookieStore, UserService) {

        /* Reset error when a new view is loaded */
        $rootScope.$on('$viewContentLoaded', function () {
            delete $rootScope.error;
        });

        $rootScope.hasRole = function (role) {

            if ($rootScope.user === undefined) {
                return false;
            }

            if ($rootScope.user.roles[role] === undefined) {
                return false;
            }

            return $rootScope.user.roles[role];
        };

        $rootScope.logout = function () {
            delete $rootScope.user;
            delete $rootScope.authToken;
            $cookieStore.remove('authToken');
            $location.path("/login");
        };

        /* Try getting valid account from cookie or go to login page */
        var originalPath = $location.path();
        $location.path("/login");
        var authToken = $cookieStore.get('authToken');
        if (authToken !== undefined) {
            $rootScope.authToken = authToken;
            UserService.get(function (user) {
                $rootScope.user = user;
                $location.path(originalPath);
            });
        }
        $rootScope.initialized = true;
    });


function IndexController($scope, PostService) {

    $scope.posts = PostService.query();

    $scope.deleteEntry = function (post) {
        post.$remove(function () {
            $scope.posts = PostService.query();
        });
    };
};

function EditController($scope, $routeParams, $location, PostService) {
    $scope.post = PostService.get({id: $routeParams.id});
    $scope.save = function () {
        $scope.post.$save(function () {
            $location.path('/');
        });
    };
};

function CreateController($scope, $location, PostService) {
    $scope.post = new PostService();
    $scope.save = function () {
        $scope.post.$save(function () {
            $location.path('/');
        });
    };
};


function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
    $scope.rememberMe = false;
    $scope.login = function () {
        UserService.authenticate($.param({
            username: $scope.username,
            password: $scope.password
        }), function (authenticationResult) {
            var authToken = authenticationResult.token;
            $rootScope.authToken = authToken;
            if ($scope.rememberMe) {
                $cookieStore.put('authToken', authToken);
            }
            UserService.get(function (user) {
                $rootScope.user = user;
                $location.path("/");
            });
        });
    };
};


var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('UserService', function ($resource) {
    return $resource('rest/accounts/:action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action': 'authenticate'},
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }
        }
    );
});

services.factory('PostService', function ($resource) {
    return $resource('rest/blog-entries/:id', {id: '@id'});
});

services.factory('AccountGroupService', function ($resource) {
    return $resource('rest/accountGroups/:id', {id: '@id'});
});

services.factory('ConnectionService', function ($resource) {
    return $resource('rest/connection/:id', {id: '@id'});
});

services.factory('ParkingService', function ($resource) {
    return $resource('rest/parking/:id', {id: '@id'});
});

services.factory('VehicleService', function ($resource) {
    return $resource('rest/vehicle/:id', {id: '@id'});
});
