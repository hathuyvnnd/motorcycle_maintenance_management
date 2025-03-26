var app = angular.module("myApp", ["ngRoute"]);

app.config(function ($routeProvider) {
  $routeProvider
    .when("/", {
      templateUrl: "customer/views/trangchu.html",
      controller: "HomeController",
    })
    .when("/phutung", {
      templateUrl: "views/phutung.html",
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

app.controller("HomeController", function ($scope, $http, $rootScope) {
  $scope.title = "Trang Chủ";
  $scope.list = [];
  $rootScope.listLoaiPT = [];
  $scope.currentPage = 1;
  $scope.itemsPerPage = 6;

  // Lấy danh sách loại phụ tùng
  $http.get("/api/loaiphutung").then(
    function (response) {
      $rootScope.listLoaiPT = response.data;
    },
    function (error) {
      console.error("Lỗi tải dữ liệu", error);
    }
  );

  $http.get("/api/dichvu").then(
    function (response) {
      $scope.list = response.data;
      $scope.totalPages = Math.ceil($scope.list.length / $scope.itemsPerPage);
      $scope.pageNumbers = Array.from({ length: $scope.totalPages }, (_, i) => i + 1);
    },
    function (error) {
      console.error("Lỗi tải dữ liệu", error);
    }
  );

  // Chuyển trang
  $scope.changePage = function (page) {
    if (page >= 1 && page <= $scope.totalPages) {
      $scope.currentPage = page;
    }
  };

  // Lấy danh sách theo trang
  // $scope.getDichVuPage = function(){
  //     let start = ($scope.currentPage -1) * $scope.itemsPerPage;
  //     return $scope.list.slice(start,start+$scope.itemsPerPage);
  // }

  // Hàm sắp xếp theo tên dịch vụ
  $scope.sortBy = function (order) {
    $scope.sortOrder = order;
  };
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
