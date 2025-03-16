var app = angular.module("megaviaApp", ["ngRoute"]);

app.controller("MainController", function ($scope) {
  $scope.isSidebarHidden = false;

  $scope.toggleSidebar = function () {
    $scope.isSidebarHidden = !$scope.isSidebarHidden;
  };
});

// ...existing code...

// Cấu hình các route
app.config(function ($routeProvider) {
  $routeProvider
    // .when("/home", {
    //   templateUrl: "home.html", // Trang home
    //   controller: "HomeController",
    // })
    .when("/employee", {
      templateUrl: "employee.html", // Trang schedule
      controller: "EmployeeController",
    })
    .when("/customer", {
      templateUrl: "customer.html", // Trang schedule
      controller: "CustomerController",
    })
    .when("/account", {
      templateUrl: "account.html", // Trang schedule
      controller: "AccountController",
    })
    .when("/service", {
      templateUrl: "service.html", // Trang schedule
      controller: "ServiceController",
    })
    .when("/accessory", {
      templateUrl: "accessory.html", // Trang schedule
      controller: "AccessoryController",
    })
    .when("/booking", {
      templateUrl: "booking.html", // Trang booking
      controller: "BookingController",
    })
    .when("/invoice", {
      templateUrl: "invoice.html", // Trang invoice
      controller: "InvoiceController",
    })
    .when("/statistics", {
      templateUrl: "statistics.html", // Trang statistics
      controller: "StatisticsController",
    })
    .otherwise({
      redirectTo: "/statistics", // Điều hướng mặc định nếu không tìm thấy route
    });
});

// app.controller("HomeController", function ($scope) {
//   $scope.pageTitle = "Dùng các chức năng";
// });
app.controller("EmployeeController", function ($scope) {
  $scope.pageTitle = "Quản lý nhân viên";
});
app.controller("CustomerController", function ($scope) {
  $scope.pageTitle = "Quản lý khách hàng";
});
app.controller("AccountController", function ($scope) {
  $scope.pageTitle = "Quản lý tài khoản";
});
app.controller("ServiceController", function ($scope) {
  $scope.pageTitle = "Quản lý dịch vụ";
});
app.controller("AccessoryController", function ($scope) {
  $scope.pageTitle = "Quản lý phụ tùng";
});
app.controller("BookingController", function ($scope) {
  $scope.pageTitle = "Quản lý lịch hẹn";
});
app.controller("InvoiceController", function ($scope) {
  $scope.pageTitle = "Quản lý hoá đơn";
});
app.controller("StatisticsController", function ($scope) {
  $scope.pageTitle = "Thống kê";
});
