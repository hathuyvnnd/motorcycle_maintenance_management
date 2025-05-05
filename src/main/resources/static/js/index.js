var app = angular.module("megavia", ["ngRoute", "megaviaApp", "myApp"]);
app.factory("AuthInterceptor", ["$q", "$window", "AuthService", function ($q, $window, AuthService) {
    return {
        request: function (config) {
            const token = sessionStorage.getItem("token");
            if (token) {
                config.headers.Authorization = "Bearer " + token;
                console.log("Token added to header: Bearer " + token);
            } else {
                console.log("No token found in sessionStorage");
            }
            return config;
        },
        responseError: function (rejection) {
            if (rejection.status === 401) {
                AuthService.logout();
                $window.location.href = '/views/dangnhap.html';
            } else if (rejection.status === 403) {
                alert("Bạn không có quyền truy cập vào tài nguyên này!");
            }
            return $q.reject(rejection);
        }
    };
}]);

app.config(["$httpProvider", function ($httpProvider) {
    $httpProvider.interceptors.push("AuthInterceptor");
}]);
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
        })
        .when("/logout", {
            controller: "LogoutController",
            template: "",
        });
});
app.run(["$rootScope", "$location", function ($rootScope, $location) {
    $rootScope.keyword = "";

    $rootScope.submitSearch = function (event) {
        if (event) event.preventDefault(); // Ngăn reload trang
        console.log(" Hàm submitSearch() chạy! Từ khóa:", $rootScope.keyword);

        if ($rootScope.keyword && $rootScope.keyword.trim() !== "") {//Trim() cắt khoảng trắng
            $location.path("/timkiem/" + encodeURIComponent($rootScope.keyword)); //encodeURIComponent mã hóa kí tự từ URL
        } else {
            console.warn(" Không có từ khóa để tìm kiếm!");
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
    $http.get("/api/khachhang/loaiphutung").then(
        function (response) {
            $rootScope.listLoaiPT = response.data;
        },
        function (error) {
            console.error("Lỗi tải dữ liệu", error);
        }
    );

// Load dịch vụ
    $http.get("/api/khachhang/dichvu").then(
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
    // $rootScope.submitSearch = function () {
    //     console.log("Từ khóa tìm kiếm:", $rootScope.keyword);
    //     if ($rootScope.keyword) {
    //         window.location.href = "#!/timkiem/" + $rootScope.keyword;
    //     }
    // };

    // Thêm Lịch hẹn chi tiết vào local storage
    $scope.dsLichHenCT = JSON.parse(localStorage.getItem("lichhenct")) || [];
    $scope.chonDichVu = function (dv){
        const exists = $scope.dsLichHenCT.some(item => item.idDichVu === dv.idDichVu);
        if (!exists) {
            $scope.dsLichHenCT.push({
                idDichVu: dv.idDichVu,
                tenDichVu: dv.tenDichVu,
                giaDichVu: dv.giaDichVu,
                ghiChu: ""
            });
            localStorage.setItem("lichhenct", JSON.stringify($scope.dsLichHenCT));
            alert("Đã thêm dịch vụ vào lịch hẹn!");
        } else {
            alert("Dịch vụ đã được chọn trước đó.");
        }
    }
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
    $http.get("/api/khachhang/phutung?idLoaiPT=" + idLoaiPT).then(
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
    $http.get("/api/khachhang/loaiphutung").then(
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
///////////////////////////////////////////////////////////////////////////////////////
app.controller("DatLichController", function ($scope,$http) {
    $scope.title = "Đặt Lịch Hẹn";
    $scope.thongBao = null;
    $scope.form = {};
    $scope.dsLoaiXe = [];
    $scope.dsLichHenCT = JSON.parse(localStorage.getItem("lichhenct")) || [];

    $http.get("/api/khachhang/loaixe").then(function (res) {
        $scope.dsLoaiXe = res.data;
    });

    $scope.xacNhanDatLich = function () {
        if (!$scope.form.ngay) {
            alert("Vui lòng chọn ngày!");
            return;
        }

        const thoiGian = new Date($scope.form.ngay);
        const ngayISO = thoiGian.toISOString().split("T")[0];

        let lichHenData = {
            idKhachHang: $scope.form.idKhachHang,
            idLoaiXe: $scope.form.idLoaiXe,
            bienSoXe: $scope.form.bienSoXe,
            thoiGian: ngayISO,
            trangThai: "Chờ xác nhận",
            lichHenCTList: $scope.dsLichHenCT.map(ct => ({
                idDichVu: ct.idDichVu,
                ghiChu: ct.ghiChu
            }))
        };

        $http.post("/api/khachhang/lichhen", lichHenData).then(function (res) {
            console.log("Đặt lịch thành công", res); // Log thành công
            $scope.thongBao = "✅ Đặt lịch thành công!";

            localStorage.removeItem("lichhenct");
            $scope.dsLichHenCT = [];
            $scope.form = {};
            $scope.datLichForm.$setPristine();
            $scope.datLichForm.$setUntouched();
        }, function (err) {
            console.log("Lỗi đặt lịch:", err); // Log lỗi
            if (err.data && err.data.message) {
                $scope.thongBao = "❗ " + err.data.message;
            } else {
                $scope.thongBao = "❗ Có lỗi xảy ra!";
            }
        });
    };
    $scope.kiemTraNgay = function () {
        const today = new Date();
        today.setHours(0, 0, 0, 0); // reset giờ về 0h00 để so sánh chỉ theo ngày

        const selectedDate = new Date($scope.form.ngay);

        if (selectedDate <= today) {
            $scope.loiNgay = true;
        } else {
            $scope.loiNgay = false;
        }
    };

});
app.controller("DangKiController", ["$scope", "$http", function ($scope, $http) {
    $scope.title = "Đăng Kí";

    $scope.message = '';
    $scope.success = false;

    // Hàm đăng ký khách hàng
    $scope.dangKyKhachHang = function() {
        // Kiểm tra nếu form hợp lệ trước khi gửi yêu cầu
        if ($scope.dangKyForm.$valid) {
            const khachHangDTO = {
                soDienThoai: $scope.khachHang.soDienThoai,
                matKhau: $scope.khachHang.matKhau,
                hoTen: $scope.khachHang.hoTen,
                diaChi: $scope.khachHang.diaChi,
                email: $scope.khachHang.email,
            };

            // Gửi yêu cầu đăng ký
            $http.post('/api/dangky', khachHangDTO)
                .then(function(response) {
                    $scope.success = true;
                    $scope.message = 'Đăng ký thành công!';

                    //  Reset form và trạng thái
                    $scope.khachHang = {};                       // Xóa dữ liệu form
                    $scope.dangKyForm.$setPristine();            // Đặt form về trạng thái chưa chỉnh sửa
                    $scope.dangKyForm.$setUntouched();           // Reset trạng thái touched của các input
                }, function(error) {
                    $scope.success = false;
                    // Kiểm tra lỗi trả về từ server
                    if (error.data) {
                        // Kiểm tra lỗi trùng ID
                        if (error.data === 'Số điện thoại đã được sử dụng.') {
                            $scope.message = 'Số điện thoại đã được đăng ký! Vui lòng chọn số khác.';
                        } else {
                            $scope.message = 'Lỗi: ' + error.data;
                        }
                    } else {
                        $scope.message = 'Đã xảy ra lỗi. Vui lòng thử lại!';
                    }
                });
        } else {
            $scope.message = 'Vui lòng điền đầy đủ thông tin hợp lệ.';
            $scope.success = false;
        }
    };
}]);

app.controller("DangNhapController", function ($scope, $http,$window, $location,$rootScope, AuthService) {

    $scope.dangNhap = function () {
        const formData = {
            idTaiKhoan: $scope.idTaiKhoan,
            matKhau: $scope.matKhau
        };

        $http.post('/api/auth/login', formData).then(
            function (response) {
                AuthService.login(response.data.token, response.data.vaiTro);
                alert("Đăng nhập thành công!");
                sessionStorage.setItem("idTaiKhoan",response.data.idTaiKhoan);
                $rootScope.isLoggedIn = true;
                $rootScope.idTaiKhoan = response.data.idTaiKhoan;
                $rootScope.vaiTro = response.data.vaiTro;

                if (response.data.vaiTro === 'Admin') {
                    $window.location.href = "/admin";
                } else if (response.data.vaiTro === 'Nhân viên') {
                    $location.path("/hoa-don");
                } else if (response.data.vaiTro === 'Khách hàng') {
                    $location.path("/");
                }
            },
            function () {
                alert("Sai số điện thoại hoặc mật khẩu!");
            }
        );
    };

    $scope.logout = function () {
        AuthService.logout();
        $location.path("/dangnhap");
    };

    $scope.isLoggedIn = function () {
        return AuthService.isLoggedIn();
    };

    $scope.getRole = function () {
        return AuthService.getRole();
    };
});

app.controller("QuenMatKhauController", function ($scope,$http) {
    $scope.title = "Quên Mật Khẩu";
    $scope.form = {};
    $scope.message = "";
    $scope.success = false;

    $scope.submitForgotPassword = function () {
        $http.post("/api/khachhang/forgot-password", { email: $scope.form.email })
            .then(function (response) {
                $scope.message = response.data.message; // ✅ lấy message từ object
                $scope.success = true;
            })
            .catch(function (error) {
                $scope.message = (error.data && error.data.message) || "Đã xảy ra lỗi khi gửi email.";
                $scope.success = false;
            });

    };
});

//////////////////////////////////////////////////////////////////////
app.controller("LichSuSuaChuaController", function ($scope, $rootScope, $http) {
    $scope.title = "Lịch Sử Sữa Chữa";
    // $scope.listPDV=[];
    $scope.listHoaDon=[];
    $scope.currentPage = 1;
    $scope.itemsPerPage = 6;
    $scope.batDau = "";
    $scope.ketThuc = "";
    $http.get("/api/khachhang/lichsu").then(function(response){
            $scope.listHoaDon = response.data;
            // $scope.listPDV = response.data;
            $scope.totalPages = Math.ceil($scope.listHoaDon.length / $scope.itemsPerPage);
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

        let ngayThucHien = new Date(hd.ngayTao).getTime();
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
    $http.get("/api/khachang/hoadon?idHoaDon="+idHoaDon)
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
    $http.get("/api/khachhang/chitiet?idPhieuDichVu="+idPhieuDichVu)
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
    $http.get("/api/khachhang/tinhtrangxe?idPhieuGNX="+idPhieuGNX)
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
        $http.get("/api/khachhang/timkiem?keyword=" + encodeURIComponent($scope.keyword))
            .then(function (response) {
                console.log("Kết quả tìm kiếm:", response.data);
                $scope.kqTimKiem = response.data;
            })
            .catch(function (error) {
                console.error("Lỗi tìm kiếm:", error);
            });
    }
});
app.controller("LogoutController", function ($scope, $location, AuthService) {
    AuthService.logout();
    $location.path("/");
});
app.controller("NavbarController", function($scope, $rootScope) {
    $scope.isLoggedIn = $rootScope.isLoggedIn;
    $scope.idTaiKhoan = sessionStorage.getItem("idTaiKhoan");
    $scope.vaiTro = sessionStorage.getItem("vaiTro");

    $scope.$watch(function() {
        return $rootScope.isLoggedIn;
    }, function(newVal) {
        $scope.isLoggedIn = newVal;
        $scope.idTaiKhoan = $rootScope.idTaiKhoan;
        $scope.vaiTro = $rootScope.vaiTro;
    });

});