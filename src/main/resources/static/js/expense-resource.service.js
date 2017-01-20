var expenseResourceFactory = function($http, $window) {
  var expensesUri = 'api/expenses';
  var yearMonthsUri = expensesUri + '/yearmonths';

  var failure = function(response) {
    $window.alert('Status ' + response.status);
  };

  return {
    create: function(expense, callback) {
      $http.post(expensesUri, expense).then(
        function success(response) { callback(response.headers('location')); },
        failure
      );
    },

    delete: function(id, callback) {
      var expenseUri = expensesUri + '/' + id;
      $http.delete(expenseUri).then(
        function success() { callback(); },
        failure
      );
    },

    getAllInYearMonth: function(yearMonth, callback) {
      var params = { yearmonth: yearMonth };
      $http.get(expensesUri, { params: params }).then(
        function success(response) { callback(response.data); },
        failure
      );
    },

    getYearMonths: function(callback) {
      $http.get(yearMonthsUri).then(
        function success(response) { callback(response.data); },
        failure
      );
    },

    getSumsByCategoryAndYearMonth: function(callback) {
      $http.get(expensesUri).then(
        function success(response) { callback(response.data); },
        failure
      )
    },
  };
};

angular
  .module('nickel')
  .factory('expenseResource', expenseResourceFactory);
