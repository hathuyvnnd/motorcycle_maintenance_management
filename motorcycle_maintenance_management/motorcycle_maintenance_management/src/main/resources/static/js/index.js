var app = angular.module("myApp", ["ngRoute"]);

app.config(function($routeProvider){
    $routeProvider
    .when("/",{
        templateUrl: "views/trangchu.html",
        controller: "HomeController",
    })
    .when("/dichvu",{
        templateUrl:"views/dichvu.html",
        controller:"DichVuController",
    })
    .when("/phutung",{
        templateUrl:"views/phutung.html",
        controller:"PhuTungController",
    })
    .when("/gioithieu",{
        templateUrl:"views/gioithieu.html",
        controller:"GioiThieuController",
    })
    .when("/datlich",{
        templateUrl:"views/datLichHen.html",
        controller:"DatLichController",
    })
    .when("/dangki",{
        templateUrl:"views/dangki.html",
        controller:"DangKiController",
    })
    .when("/dangnhap",{
        templateUrl:"views/dangnhap.html",
        controller:"DangNhapController",
    })
    .when("/quenmatkhau",{
        templateUrl:"views/quenmatkhau.html",
        controller:"QuenMatKhauController",
    })


    
});

app.controller("HomeController",function($scope){
    $scope.title = "Trang Chủ";
});
app.controller("DichVuController",function($scope){
    $scope.title = "Dịch Vụ";
});
app.controller("PhuTungController",function($scope){
    $scope.title = "Phụ Tùng";
})
app.controller("GioiThieuController",function($scope){
    $scope.title = "Giới Thiệu";
})
app.controller("DatLichController",function($scope){
    $scope.title = "Đặt Lịch Hẹn";
})
app.controller("DangKiController",function($scope){
    $scope.title = "Đăng Kí";
})
app.controller("DangNhapController",function($scope){
    $scope.title = "Đăng Nhập";
})
app.controller("QuenMatKhauController",function($scope){
    $scope.title = "Quên Mật Khẩu";
})