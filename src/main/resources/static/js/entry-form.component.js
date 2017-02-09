var entryFormController = function(expenseResource) {
  var ctrl = this;

  ctrl.initInput = function() {
    ctrl.input = {
      date: new Date(),
      amounts: '',
      category: '',
      description: ''
    };
  }

  ctrl.createExpense = function() {
    var newExpense = {
      date: ctrl.input.date,
      amount: ctrl.calcAmount(),
      category: ctrl.input.category,
      description: ctrl.input.description
    };
    expenseResource.create(newExpense, function(){
      ctrl.initInput();
    })
  };

  ctrl.calcAmount = function() {
    return ctrl.input.amounts
      .split(' ')
      .map(function(x) { return parseInt(x, 10); })
      .filter(function(x) { return !isNaN(x); })
      .reduce(function(x, y) { return x + y; }, 0);
  };

  ctrl.initInput();
};

angular
  .module('nickel')
  .component('entryForm', {
    templateUrl: 'js/entry-form.template.html',
    controller: entryFormController
  });
