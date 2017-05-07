var balanceResourceFactory = function($http, $window) {
  var balanceUri = 'api/balances';

  var failure = function(response) {
    $window.alert('Status ' + response.status);
  };

  return {
    get: function(callback) {
      $http.get(balanceUri).then(
        function success(response) { callback(response.data); },
        failure
      );
    },
  };
};

angular
  .module('nickel')
  .factory('balanceResource', balanceResourceFactory);
