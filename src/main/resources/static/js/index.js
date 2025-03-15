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