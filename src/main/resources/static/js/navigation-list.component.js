var navigationListController = function($location) {
  var ctrl = this;

  ctrl.pages = [
    {
      path: '/new',
      name: 'New'
    },
    {
      path: '/expenses',
      name: 'Expenses'
    },
    {
      path: '/chart',
      name: 'Chart'
    }
  ];

  ctrl.isActive = function(page) {
    return page.path === $location.path();
  };
};

angular
  .module('nickel')
  .component('navigationList', {
    templateUrl: 'js/navigation-list.template.html',
    controller: navigationListController
  });
