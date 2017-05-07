var incomeResourceFactory = function($http, $window) {
  var incomesUri = 'api/incomes';

  var failure = function(response) {
    $window.alert('Status ' + response.status);
  };

  return {
    create: function(income, callback) {
      $http.post(incomesUri, income).then(
        function success(response) { callback(response.headers('location')); },
        failure
      );
    },

    delete: function(id, callback) {
      var incomeUri = incomesUri + '/' + id;
      $http.delete(incomeUri).then(
        function success() { callback(); },
        failure
      );
    },

    getAll: function(callback) {
      $http.get(incomesUri).then(
        function success(response) { callback(response.data); },
        failure
      );
    },
  };
};

angular
  .module('nickel')
  .factory('incomeResource', incomeResourceFactory);
