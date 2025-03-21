var app = angular.module("myApp", ["ngRoute"]);

app.config(function ($routeProvider) {
  $routeProvider
    .when("/", {
      templateUrl: "customer/views/trangchu.html",
      controller: "HomeController",
    })
    .when("/dichvu", {
      templateUrl: "customer/views/dichvu.html",
      controller: "DichVuController",
    })
    .when("/phutung", {
      templateUrl: "customer/views/phutung.html",
      controller: "PhuTungController",
    })
    .when("/gioithieu", {
      templateUrl: "customer/views/gioithieu.html",
      controller: "GioiThieuController",
    })
    .when("/datlich", {
      templateUrl: "customer/views/datLichHen.html",
      controller: "DatLichController",
    })
    .when("/dangki", {
      templateUrl: "customer/views/dangki.html",
      controller: "DangKiController",
    })
    .when("/dangnhap", {
      templateUrl: "customer/views/dangnhap.html",
      controller: "DangNhapController",
    })
    .when("/quenmatkhau", {
      templateUrl: "customer/views/quenmatkhau.html",
      controller: "QuenMatKhauController",
    });
});

app.controller("HomeController", function ($scope) {
  $scope.title = "Trang Chủ";
});
app.controller("DichVuController", function ($scope) {
  $scope.title = "Dịch Vụ";
});
app.controller("PhuTungController", function ($scope) {
  $scope.title = "Phụ Tùng";
});
app.controller("GioiThieuController", function ($scope) {
  $scope.title = "Giới Thiệu";
});
app.controller("DatLichController", function ($scope) {
  $scope.title = "Đặt Lịch Hẹn";
});
app.controller("DangKiController", function ($scope) {
  $scope.title = "Đăng Kí";
});
app.controller("DangNhapController", function ($scope) {
  $scope.title = "Đăng Nhập";
});
app.controller("QuenMatKhauController", function ($scope) {
  $scope.title = "Quên Mật Khẩu";
});
