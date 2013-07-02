'use strict';

angular.module('jsApp')
    .controller('NotebookListCtrl', function ($scope, notebooks) {
        $scope.notebooks = notebooks.data;
    })
    .controller('NewNotebookCtrl', ['$scope', '$resource', function ($scope, $resource) {
        $scope.notebook = { id: 'new', flashCards: [] };

        $scope.addFlashCard = function () {
            $scope.notebook.flashCards.push({});

        }

        $scope.removeFlashCard = function (index) {
            $scope.notebook.flashCards.splice(index, 1);
        }

        $scope.save = function (book) {

            book.created = Date.now();

            for (var card in book.flashCards) {
                delete book.flashCards[card]['$$hashKey'];
            }
            var Notebook = $resource('/api/notebook/:id', { id: '@id' }, { update: { method: 'PUT' } });

            var notebook = new Notebook(book);

            notebook.$save();

            //$http( { url: '/notebooks/new', method: 'get', params: {json: ""+JSON.stringify(notebook)} });
        }
    }])
    .controller('UpdateNotebookCtrl', ['$scope', '$resource', '$routeParams', function ($scope, $resource, $routeParams) {
        var Notebook = $resource('/api/notebook/:id', { id: '@id' }, { update: { method: 'PUT' } });

        $scope.notebook = Notebook.get({id: $routeParams['id']});

        $scope.addFlashCard = function () {
            $scope.notebook.flashCards.push({});

        }

        $scope.removeFlashCard = function (index) {
            $scope.notebook.flashCards.splice(index, 1);
        }

        $scope.save = function (book) {

            book.updated = Date.now();

            for (var card in book.flashCards) {
                delete book.flashCards[card]['$$hashKey'];
            }

            book.$save();

        }
    }])
    .controller('ViewNotebookCtrl', ['$scope', '$resource', '$routeParams', function ($scope, $resource, $routeParams) {
        var Notebook = $resource('/api/notebook/:id', { id: '@id' }, { update: { method: 'PUT' } });

        $scope.notebook = Notebook.get({id: $routeParams['id']});

        $scope.currentId = 0;

        $scope.addFlashCard = function () {
            $scope.notebook.flashCards.push({});

        }

        $scope.removeFlashCard = function (index) {
            $scope.notebook.flashCards.splice(index, 1);
        }

        $scope.save = function (book) {

            book.updated = Date.now();

            for (var card in book.flashCards) {
                delete book.flashCards[card]['$$hashKey'];
            }

            book.$save();

        }
    }])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/notebooks', {
                templateUrl: 'views/notebooks.html',

                resolve: {
                    notebooks: ['$http', function ($http) {
                        return $http.get('/notebooks')
                    }]
                },
                controller: 'NotebookListCtrl'
            }).when('/notebook/new', {
                templateUrl: 'views/notebookForm.html',
                controller: 'NewNotebookCtrl'
            }).when('/notebook/:id/edit', {
                templateUrl: 'views/notebookForm.html',
                controller: 'UpdateNotebookCtrl'
            })
            .when('/notebook/:id', {
                templateUrl: 'views/flashCards.html',
                controller: 'ViewNotebookCtrl'
            })

    }]);