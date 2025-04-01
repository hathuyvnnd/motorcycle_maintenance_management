var app = angular.module("myApp", ["ngRoute"]);

app.config(function ($routeProvider) {
  $routeProvider
    .when("/", {
      templateUrl: "views/trangchu.html",
      controller: "HomeController",
    })

    .when("/phutung/:idLoaiPT", {
      templateUrl: "views/phutung.html",
      controller: "PhuTungController",
    })
    .when("/gioithieu", {
      templateUrl: "views/gioithieu.html",
      controller: "GioiThieuController",
    })
    .when("/datlich", {
      templateUrl: "views/datLichHen.html",
      controller: "DatLichController",
    })
    .when("/dangki", {
      templateUrl: "views/dangki.html",
      controller: "DangKiController",
    })
    .when("/dangnhap", {
      templateUrl: "views/dangnhap.html",
      controller: "DangNhapController",
    })
    .when("/quenmatkhau", {
      templateUrl: "views/quenmatkhau.html",
      controller: "QuenMatKhauController",
    })
    .when("/hoadon/:idHoaDon", {
      templateUrl: "views/hoadon.html",
      controller: "HoaDonController",
    })
    .when("/dichvuchitiet/:idPhieuDichVu",{
      templateUrl: "views/dichvuchitiet.html",
      controller: "DichVuController",
    })
    .when("/lichsusuachua", {
      templateUrl: "views/lichsu.html",
      controller: "LichSuSuaChuaController",
    })
    .when("/tinhtrangxe/:idPhieuGNX", {
      templateUrl: "views/tinhtrangxe.html",
      controller: "TinhTrangXeController",
    })
    .when("/timkiem/:keyword", {
      templateUrl: "views/timkiem.html",
      controller: "TimKiemController",
    });
});
app.run(["$rootScope", "$location", function ($rootScope, $location) {
  console.log("✅ AngularJS đã khởi động!");

  $rootScope.keyword = "";

  $rootScope.submitSearch = function (event) {
    if (event) event.preventDefault(); // Ngăn reload trang
    console.log("🔍 Hàm submitSearch() chạy! Từ khóa:", $rootScope.keyword);

    if ($rootScope.keyword && $rootScope.keyword.trim() !== "") {//Trim() cắt khoảng trắng
      $location.path("/timkiem/" + encodeURIComponent($rootScope.keyword)); //encodeURIComponent mã hóa kí tự từ URL
    } else {
      console.warn("❌ Không có từ khóa để tìm kiếm!");
    }
  };
}]);




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



  // Hàm sắp xếp theo tên dịch vụ
  $scope.sortBy = function (order) {
    $scope.sortOrder = order;
  };

  // Hàm tìm kiếm
  $rootScope.submitSearch = function () {
    console.log("Từ khóa tìm kiếm:", $rootScope.keyword);
    if ($rootScope.keyword) {
        window.location.href = "#!/timkiem/" + $rootScope.keyword;
    }
};

  
});

app.controller("PhuTungController", function ($scope, $rootScope, $http, $routeParams) {
  $scope.title = "Phụ Tùng";
  $rootScope.listPhuTung = [];
  $scope.currentPage = 1;
  $scope.itemsPerPage = 6;

  // Lấy idLoaiPT từ URL
  var idLoaiPT = $routeParams.idLoaiPT;
  console.log(" Lấy danh sách phụ tùng cho loại: ", idLoaiPT);
  // Gọi API để lấy danh sách phụ tùng
  $http.get("/api/phutung?idLoaiPT=" + idLoaiPT).then(
    function (response) {
      $rootScope.listPhuTung = response.data;
      $scope.totalPages = Math.ceil($rootScope.listPhuTung.length / $scope.itemsPerPage);
      $scope.pageNumbers = Array.from({ length: $scope.totalPages }, (_, i) => i + 1);
    },
    function (error) {
      console.error("Lỗi tải phụ tùng theo loại", error);
    }
  );

  $scope.changePage = function (page) {
    if (page >= 1 && page <= $scope.totalPages) {
      $scope.currentPage = page;
    }
  };

  $scope.sortBy = function (order) {
    $scope.sortOrder = order;
  };


  /////////////////////////////////////////////////////////
  $http.get("/api/loaiphutung").then(
    function (response) {
      $rootScope.listLoaiPT = response.data;
    },
    function (error) {
      console.error("Lỗi tải dữ liệu", error);
    }
  );
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

//////////////////////////////////////////////////////////////////////
app.controller("LichSuSuaChuaController", function ($scope, $rootScope, $http) {
  $scope.title = "Lịch Sử Sữa Chữa";
  $scope.listPDV=[];
  $scope.currentPage = 1;
  $scope.itemsPerPage = 6;
  $scope.batDau = "";
  $scope.ketThuc = "";
  $http.get("/api/lichsu").then(function(response){
    $scope.listPDV = response.data;
    $scope.totalPages = Math.ceil($scope.listPDV.length / $scope.itemsPerPage);
    $scope.pageNumbers = Array.from({ length: $scope.totalPages }, (_, i) => i + 1);
  },
  function (error) {
    console.error("Lỗi tải phiếu dịch vụ", error);
  });
  $scope.changePage = function (page) {
    if (page >= 1 && page <= $scope.totalPages) {
      $scope.currentPage = page;
    }
  };
  // Bộ lọc ngày
  $scope.dateFilter = function (hd) {
    if (!$scope.batDau && !$scope.ketThuc) return true;
    
    let ngayThucHien = new Date(hd.ngayThucHien).getTime();
    let start = $scope.batDau ? new Date($scope.batDau).getTime() : -Infinity;
    let end = $scope.ketThuc ? new Date($scope.ketThuc).getTime() : Infinity;

    return ngayThucHien >= start && ngayThucHien <= end;
};

});



//////////////////////////////////////////////////////////////////////
app.controller("HoaDonController", function ($scope, $http,$routeParams) {
  $scope.title = "Hóa Đơn";
  
  $scope.hoaDon = null; // Không phải danh sách nữa
// Lấy ID từ URL
var idHoaDon = $routeParams.idHoaDon;
  // Gọi API để lấy một đối tượng hóa đơn duy nhất
  $http.get("/api/hoadon?idHoaDon="+idHoaDon)
      .then(function (response) {
          $scope.hoaDon = response.data; // Lưu một đối tượng duy nhất
      })
      .catch(function (error) {
          console.error("Lỗi tải Hóa Đơn", error);
      });

});


//////////////////////////////////////////////////////////////////////
app.controller("DichVuController",function($scope, $http,$routeParams){
  $scope.title = "Chi Tiết Dịch Vụ";
  $scope.chiTietPhieuDichVu = {};
  var idPhieuDichVu = $routeParams.idPhieuDichVu;
  $http.get("/api/chitiet?idPhieuDichVu="+idPhieuDichVu)
  .then(function(response){
    $scope.chiTietPhieuDichVu = response.data;
  })
  .catch(function (error) {
    console.error("Lỗi khi tải chi tiết phiếu dịch vụ", error);
});
});

//////////////////////////////////////////////////////////////////////
app.controller("TinhTrangXeController",function($scope, $http,$routeParams){
  $scope.title = "Phiều Ghi Nhận Tình Trạng Xe";
  $scope.phieuGhiNhanXe = null;
  var idPhieuGNX = $routeParams.idPhieuGNX;
  console.log(" ID Phiếu Dịch Vụ từ route:", idPhieuGNX); // Debug kiểm tra
  $http.get("/api/tinhtrangxe?idPhieuGNX="+idPhieuGNX)
  .then(function(response){
    $scope.phieuGhiNhanXe = response.data;
  })
  .catch(function (error) {
    console.error("Lỗi khi tải tình trạng xe", error);
});
});

/////////////////////////////////////////////////////////////////
//Hàm tìm kiếm
app.controller("TimKiemController", function ($scope, $http, $routeParams) {
  $scope.keyword = $routeParams.keyword || "";
  $scope.kqTimKiem = [];

  console.log("Tìm kiếm với từ khóa:", $scope.keyword); // Debug

  if ($scope.keyword.trim() !== "") {
    $http.get("/api/timkiem?keyword=" + encodeURIComponent($scope.keyword))
      .then(function (response) {
        console.log("Kết quả tìm kiếm:", response.data);
        $scope.kqTimKiem = response.data;
      })
      .catch(function (error) {
        console.error("Lỗi tìm kiếm:", error);
      });
  }
});


