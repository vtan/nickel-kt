var balanceController = function(balanceResource) {
  var ctrl = this;

  ctrl.balance = [];

  balanceResource.get(function(balance) {
    ctrl.balance = balance;
  });
};

angular
  .module('nickel')
  .component('balance', {
    templateUrl: 'js/balance.template.html',
    controller: balanceController
  });
