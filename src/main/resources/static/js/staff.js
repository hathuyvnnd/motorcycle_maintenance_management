var appEmployee = angular.module("myApp", ["ngRoute"]);
appEmployee.factory("AuthInterceptor", [
    "$q", "$window", function($q, $window) {
      return {
        request: function(config) {
          const token = sessionStorage.getItem("token");
          if (token) {
            config.headers = config.headers || {};
            config.headers.Authorization = "Bearer " + token;
            console.log("Staff: token added to header",token);
          }
          return config;
        },
        responseError: function(rejection) {
          if (rejection.status === 401) {
            $window.location.href = "/views/dangnhap.html";
          }
          return $q.reject(rejection);
        }
      };
    }
  ]);
  
  appEmployee.config(["$httpProvider", function($httpProvider) {
    $httpProvider.interceptors.push("AuthInterceptor");
  }]);
appEmployee.config(function ($routeProvider) {
$routeProvider
  .when("/", {
    templateUrl: "/employee/content/homestaff.html",
    controller: "homeStaffController",
  })
  // .when("/", {
  //   templateUrl: "/employee/content/hoadonstaff.html",
  // })
  .when("/hoa-don", {
    templateUrl: "/employee/content/hoadonstaff.html",
  })
  .when("/tra-cuu-lich-hen", {
    templateUrl: "/employee/content/taolichhen.html",
    controller: "lichHenController",
  })
  .when("/nhan-vien-tao-lich-hen", {

    templateUrl: "/employee/content/staffTaoLichHen.html",
    controller: "TaoLichHenController",
  })
  .when("/nhan-vien-thong-tin", {
    templateUrl: "/employee/content/thongtinstaff.html",
    controller: "thongTinStaffController",
  })
  .when("/doi-mat-khau", {
    templateUrl: "/employee/content/doiMatKhau.html",
    controller: "doiMatKhauStaffController",
  })
  .when("/tra-cuu-phieu-tinh-trang", {
    templateUrl: "/employee/content/tracuuphieughinhan.html",
    controller: "traCuuPhieuGhiNhanController",
  })
  .when("/tra-cuu-hoa-don", {
    templateUrl: "/employee/content/tracuuhoadon.html",
    controller: "traCuuHoaDonController",
  })
  .when("/lich-hen-cho-xac-nhan", {
    templateUrl: "/employee/content/lichhenchoxacnhan.html",
    controller: "lHChoXacNhanController",
  })
  .when("/lich-hen-chua-hoan-tat", {
    templateUrl: "/employee/content/lichhenchuahoantat.html",
    controller: "lHChuaHoanTatController",
  })
  .when("/logout", {
    templateUrl: "index.html",
    controller: "LogoutController",
  })
  // .otherwise({
  //   redirectTo: "/",
  // });
});
appEmployee.controller("laman", function ($http, $scope) {
    $scope.logout = function () {
        // Xóa token và các thông tin liên quan
        sessionStorage.clear();      // hoặc: sessionStorage.removeItem("token"); v.v...
        localStorage.clear();
    
        // Điều hướng về trang chủ
        window.location.href = "/";
    };
$scope.lichhen = []; // Dữ liệu lich hen
localStorage.setItem("idNhanVien", "NV001");
$scope.isSidebarHidden = false;
$scope.currentYear = new Date().getFullYear();
$scope.currentDay = new Date();
// $rootScope.pageTitle = "Dashboard"; // tiêu đề mặc định
$scope.toggleSidebar = function () {
  $scope.isSidebarHidden = !$scope.isSidebarHidden;
  console.log("Sidebar hidden:", $scope.isSidebarHidden);
};
$scope.chucNangList = [
    {
      ten: "Đổi Mật khẩu",
      icon: "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
      isNew: true,
      link: "doi-mat-khau"
    },
    {
      ten: "Xem Lịch Hẹn Trong Ngày",
      icon: "https://cdn-icons-png.flaticon.com/512/891/891462.png",
      isNew: false,
      link: "/tra-cuu"
    },
    {
      ten: "Tạo Lịch",
      icon: "https://cdn-icons-png.flaticon.com/512/3030/3030371.png",
      isNew: false,
      link: "/quan-ly-lop"
    },
    {
      ten: "Tra Cứu Lịch Hẹn",
      icon: "https://cdn-icons-png.flaticon.com/512/747/747310.png",
      isNew: false,
      link: "/thoi-khoa-bieu"
    },
    {
      ten: "Thông Tin",
      icon: "https://cdn-icons-png.flaticon.com/512/942/942748.png",
      isNew: true,
      link: "/nhap-diem"
    },
   
  ];

  $http.get("/api/lich-hen").then(function(response) {
    $scope.allLichHen = response.data.result;
    });
    
  $scope.staff = {}; // sẽ bind lên giao diện
     idNhanVien = localStorage.getItem("idNhanVien");

    if (idNhanVien) {
        $http.get(`/test/findid/nhan-vien`, { params: { id: idNhanVien } })
          .then(function (response) {
            // Do backend trả về dạng ApiReponse, nên dữ liệu nằm trong .result
            $scope.staff = response.data.result;
            console.log("ttnv",  $scope.staff);
            // Cập nhật đường dẫn ảnh
            $scope.imageUrl = "/images/" +  $scope.staff.hinhAnh;
            console.log("ttnvim",  $scope.imageUrl);
          })
          .catch(function (error) {
            console.error("Lỗi khi lấy thông tin nhân viên:", error);
          });
    } else {
        console.warn("Chưa có ID nhân viên trong localStorage");
    }
});
appEmployee.filter('vnd', function () {
    return function (input) {
      if (isNaN(input)) return input;
      return parseInt(input).toLocaleString('vi-VN') + 'đ';
    };
  });

//app.controller("ServiceController", function ($scope) {
//$scope.services = [];
//$scope.parts = [];
//$scope.showPartsSelection = false;
//
//$scope.addService = function () {
//  if ($scope.selectedService) {
//    $scope.services.push($scope.selectedService);
//    if ($scope.selectedService === "Thay thế phụ tùng") {
//      $scope.showPartsSelection = true;
//    }
//    $scope.selectedService = "";
//  }
//};
//
//$scope.removeService = function (index) {
//  if ($scope.services[index] === "Thay thế phụ tùng") {
//    $scope.showPartsSelection = false;
//    $scope.parts = [];
//  }
//  $scope.services.splice(index, 1);
//};
//
//$scope.addPart = function () {
//  if ($scope.selectedPart && $scope.selectedQuantity) {
//    $scope.parts.push({ name: $scope.selectedPart, quantity: $scope.selectedQuantity });
//    $scope.selectedPart = "";
//    $scope.selectedQuantity = "";
//  }
//};
//
//$scope.removePart = function (index) {
//  $scope.parts.splice(index, 1);
//};
//
//$scope.saveServiceTicket = function () {
//  alert("Phiếu dịch vụ đã được lưu!");
//};
//});


//app.controller("lichHenController", function ($scope, $http, $rootScope) {
//    $scope.appointments = [];
//    $scope.selectedAppointment = null;
//    $scope.panelTemplate = ""; // Load giao diện modal phù hợp
//
//    // Gọi API lấy danh sách lịch hẹn
//    $http.get("http://localhost:8081/api/lich-hen/today")
//        .then(function (response) {
//            if (response.data && response.data.result) {
//                $scope.appointments = response.data.result;
//            } else {
//                console.error("API không trả về dữ liệu hợp lệ.");
//            }
//        })
//        .catch(function (error) {
//            console.error("Lỗi khi lấy dữ liệu:", error);
//        });
//    $scope.getActionText = function (status) {
//    switch (status) {
//        case "Đã xác nhận":
//            return "Lập phiếu tình trạng xe";
//        case "Đang kiểm tra":
//            return "Lập phiếu dịch vụ";
//        case "true":
//            return "Cập nhật phiếu dịch vụ";
//        case "false":
//            return "Thanh toán";
//        case "Đã thanh toán":
//            return "Xem hóa đơn";
//        default:
//            return "Không xác định";
//    }
//};
//    // Mở modal phù hợp với trạng thái
//         $scope.services = [];
//        $scope.parts = [];
//        $scope.showPartsSelection = false;
//    $scope.openModal = function (appointment) {
//        console.log("selectedAppointment: ",  $scope.selectedAppointment);
//        $scope.selectedAppointment = appointment;
//
//        // Load template phù hợp với trạng thái
//        switch (appointment.trangThai) {
//             case "Đã xác nhận":
//                $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
//                break;
//            case "Đang kiểm tra":
//                $scope.panelTemplate = "/employee/content/panel/lapPhieuDichVuModal.html";
//                break;
//            case "Đang sửa chữa":
//            case "Đã hoàn thành":
//            case "Đã thanh toán":
//                $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
//                break;
//            default:
//                $scope.panelTemplate = "";
//                break;
//        }
//
//        // Mở modal Bootstrap
//        var myModal = new bootstrap.Modal(document.getElementById("lichHenModal"));
//        myModal.show();
//  };
//    $scope.addService = function () {
//     console.log("selectedAppointment: ",  $scope.selectedService);
//      if ($scope.selectedService) {
//        $scope.services.push($scope.selectedService);
//        if ($scope.selectedService === "Thay thế phụ tùng") {
//          $scope.showPartsSelection = true;
//        }
//        $scope.selectedService = "";
//      }
//    };
//
//    $scope.removeService = function (index) {
//      if ($scope.services[index] === "Thay thế phụ tùng") {
//        $scope.showPartsSelection = false;
//        $scope.parts = [];
//      }
//      $scope.services.splice(index, 1);
//    };
//
//    $scope.addPart = function () {
//      if ($scope.selectedPart && $scope.selectedQuantity) {
//        $scope.parts.push({ name: $scope.selectedPart, quantity: $scope.selectedQuantity });
//        $scope.selectedPart = "";
//        $scope.selectedQuantity = "";
//      }
//    };
//
//    $scope.removePart = function (index) {
//      $scope.parts.splice(index, 1);
//    };
//    $scope.taoPhieuTinhTrang = function (appointment) {
//  console.log("Selected Appointment:", $scope.selectedAppointment); // Kiểm tra dữ liệu
//
//    if (!$scope.selectedAppointment || !$scope.selectedAppointment.bienSoXe) {
//        alert("Lỗi: Không có thông tin xe để tạo phiếu!");
//        return;
//    }
//
//    const data = {
//        bienSoXe: $scope.selectedAppointment.bienSoXe,
//        moTaTinhTrangXe: $scope.selectedAppointment.moTaTinhTrangXe || ""
//    };
//
//    $http.post("http://localhost:8081/api/staff/phieu-tinh-trang", data)
//        .then(function (response) {
//            if (response.data && response.data.result) {
//                alert("Tạo phiếu ghi nhận thành công!");
//                console.log(response.data.result);
//                 $scope.loadAppointments();
//            } else {
//                alert("Không thể tạo phiếu ghi nhận.");
//            }
//        })
//        .catch(function (error) {
//            console.error("Lỗi khi tạo phiếu:", error);
//            alert("Lỗi khi tạo phiếu.");
//        });
//  var myModalEl = document.getElementById("lichHenModal");
//                var modal = bootstrap.Modal.getInstance(myModalEl);
//                if (modal) {
//                    modal.hide();
//                }
//};
//    $scope.loadAppointments = function () {
//        $http.get("http://localhost:8081/api/lich-hen/today")
//            .then(function (response) {
//                if (response.data && response.data.result) {
//                    $scope.appointments = response.data.result;
//                }
//            })
//            .catch(function (error) {
//                console.error("Lỗi khi lấy dữ liệu:", error);
//            });
//    };
//
//
//
//
//
//
//
//
//
//    $scope.saveServiceTicket = function () {
//      alert("Phiếu dịch vụ đã được lưu!");
//    };
//});

appEmployee.controller("lichHenController", function ($scope, $http, $rootScope, $q) {
    $scope.appointments = [];
    $scope.selectedAppointment = null;
    $scope.panelTemplate = ""; // Load giao diện modal phù hợp
    $scope.dichVuList = [];
    $scope.phuTungList = [];


    // Gọi API lấy danh sách lịch hẹn
    $scope.loadAppointments = function () {
        $http.get('/api/lich-hen/today')
            .then(function (response) {
                if (response.data && response.data.result) {
                    $scope.appointments = response.data.result;
                    console.log("t",$scope.appointments);
                } else {
                    console.error("API không trả về dữ liệu hợp lệ.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi lấy dữ liệu:", error);
            });
    };

    $scope.getActionText = function (status) {
        switch (status) {
            case "Đã xác nhận":
                return "Lập phiếu tình trạng xe";
            case "Đang kiểm tra":
                return "Lập phiếu dịch vụ";
            case "Đang sửa chữa":
                return "Hoàn tất sửa chữa";
            case "Đã sửa chữa":
                return "Thanh toán";
            case "Chờ thanh toán":
                return "Hoàn tất";
            default:
                return "";
        }
    };

    // Mở modal phù hợp với trạng thái
    $scope.openModal = function (appointment) {
        $scope.selectedAppointment = appointment;
        $scope.services = [];
        $scope.parts = [];
        $scope.showPartsSelection = false;
        $rootScope.selectedService = ""; // Reset select service
        switch (appointment.trangThai) {
            case "Đã xác nhận":
                $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
                break;
            case "Đang kiểm tra":
                $scope.panelTemplate = "/employee/content/panel/lapPhieuDichVuModal.html";
                $scope.loadLichHen();

                break;
            case "Đang sửa chữa":
                $scope.panelTemplate = "/employee/content/panel/xacNhanHoanTatPanel.html";
                break;
            case "Đã sửa chữa":
                $scope.panelTemplate = "/employee/content/panel/thanhToanPanel.html"; // <- File html cho modal thanh toán
                $scope.layDichVuVaPhuTung();               
                break;
            case "Chờ thanh toán":
                $scope.panelTemplate = "/employee/content/panel/xacNhanThanhToanPanel.html"; // Tạo giao diện thanh toán
                break;
            default:
                $scope.panelTemplate = "";
                break;
        }

       // Loại bỏ focus hiện tại để tránh lỗi aria-hidden
    document.activeElement.blur();

    // Delay một chút rồi mới mở modal
    setTimeout(function () {
        var modalElement = document.getElementById("lichHenModal");
    
        // blur toàn bộ phần tử có thể đang giữ focus
        modalElement.querySelectorAll("input, select, button, textarea").forEach(el => el.blur());
    
        // Mở modal
        var myModal = new bootstrap.Modal(modalElement);
        myModal.show();
    }, 100);
    };
    $scope.openModalXem = function (appointment) {
        $scope.selectedAppointment = appointment;
            console.log("appointment", appointment);
        switch (appointment.trangThai) {
            case "Đã xác nhận":
                $scope.panelTemplate = "/employee/content/panel/lichHenDetailPanel.html";
                break;
            case "Đang kiểm tra":
                $scope.panelTemplate = "/employee/content/panel/xemPhieuTinhTrangPanel.html";
                $scope.loadPhieuTinhTrang();
                break;
            case "Đang sửa chữa":
                $scope.panelTemplate = "/employee/content/panel/xemPhieuDichVu.html";
                $scope.layDichVuVaPhuTung();
                break;
            case "Chờ thanh toán":
                $scope.panelTemplate = "/employee/content/panel/chiTietHoaDonPanel.html";
                $scope.hienThiQR = false;
                $scope.layDichVuVaPhuTung();
                $http.get("http://localhost:8081/test/findid/hoa-don/by-lich-hen?id=" + appointment.idLichHen)
                .then(function (res) {
                    const hoaDon = res.data.result;
                    console.log("hoadon", hoaDon);
                    $scope.idHoaDon = hoaDon.idHoaDon;
                    $scope.tongTien = hoaDon.tongTien;
                    $scope.phuongThucThanhToan = hoaDon.phuongThucThanhToan === true ? 'Tien Mat' : 'Chuyen Khoan'; // hoặc bạn kiểm tra dạng chuỗi
    
                    // Nếu chuyển khoản thì gọi API VietQR
                    if ($scope.phuongThucThanhToan === "Chuyen Khoan") {
                        const qrRequest = {
                            accountNo: "0987300853",
                            accountName: "TRUONG LAM AN",
                            acqId: 970422,
                            amount: hoaDon.tongTien,
                            addInfo: "Thanh toan lich hen #",
                            template: "compact2"
                        };
    
                        fetch("https://api.vietqr.io/v2/generate", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify(qrRequest)
                        })
                        .then(res => res.json())
                        .then(data => {
                            $scope.$apply(() => {
                                $scope.qrcodeURL = data.data.qrDataURL;
                                console.log("Da goi ham vietqr");
                            });
                        })
                        .catch(err => console.error("Lỗi gọi API VietQR:", err));
                    }
                });
                break;
            default:
                alert("Không xác định trạng thái để xem.");
                return;
        }
    
        $('#lichHenModal').modal('show');
    };
    $scope.canEditAppointment = function(trangThai) {
        const trangThaiKhongChoSua = ['Đã xác nhận', 'Đã hoàn thành', 'Đã hủy'];
        return !trangThaiKhongChoSua.includes(trangThai);
    };
    $scope.openModalThayDoi = function (appointment) {
        $scope.selectedAppointment = appointment;
        console.log("ttlichhen", $scope.selectedAppointment);
    
        switch (appointment.trangThai) {
            // case "Đã xác nhận":
            //     $scope.panelTemplate = "/employee/content/panel/lichHenEditPanel.html";
            //     $scope.selectedAppointment.thoiGian = new Date($scope.selectedAppointment.thoiGian);
    
            //     break;
            case "Đang kiểm tra":
                $scope.panelTemplate = "/employee/content/panel/editPhieuTinhTrang.html";
                $scope.loadPhieuTinhTrang();
                break;
            case "Đang sửa chữa":
                $scope.panelTemplate = "/employee/content/panel/editPhieuDichVu.html";
                $scope.layDichVuVaPhuTung().then(function () {
                    // 👉 Kiểm tra dịch vụ sau khi đã load xong
                    var hasThayThePhuTung = $scope.dichVuDaChon.some(function (dv) {
                        return dv.dichVu && dv.dichVu.tenDichVu === "Thay thế phụ tùng";
                    });
                    console.log("showPartsSelection1:", hasThayThePhuTung);
                    $scope.showPartsSelection1 = hasThayThePhuTung;
            
                    // Gọi $apply nếu cần (khi dùng promise ngoài $digest)
                    if (!$scope.$$phase) $scope.$apply();
                });
                break;
            default:
                alert("Không xác định trạng thái để chỉnh sửa.");
                return;
        }
    
        $('#lichHenModal').modal('show');
    };

    $scope.loadLichHen = function (){
        $http.get("http://localhost:8081/api/staff/lich-hen-chi-tiet/byidlichhen" ,{ params: { idLichHen: $scope.selectedAppointment.idLichHen } })
        .then(function (response) {
            if (response.data.result) {
                $scope.services = response.data.result;
                $scope.tesst = "Anhi";

                console.log("Dịch vụ trong lịch hẹn:", $scope.services);
            }
        })
        .catch(function (error) {
            console.error("Lỗi khi tải danh sách dịch vụ:", error);
        });

    };
    $scope.loadPhieuTinhTrang = function (){
        $http.get("http://localhost:8081/test/findid/phieu-tinh-trang-by-lich-hen", { params: { id: $scope.selectedAppointment.idLichHen } })
        .then(function (response) {
            if (response.data && response.data.result) {
                $scope.detailPhieuTinhTrang = response.data.result;

                console.log("ID phiếu GNX:", $scope.detailPhieuTinhTrang);
                
            } else {
                console.warn("Không tìm thấy phiếu ghi nhận.");
            }
        })
        .catch(function (error) {
            console.error("Lỗi khi lấy phiếu ghi nhận:", error);
        });

    };

   
$scope.dichVuDaChon = [];
$scope.phuTungDaChon = [];
$scope.tongTien = 0;
$scope.layDichVuVaPhuTung = function (){
    var request1 = $http.get("http://localhost:8081/test/findid/phieu-phu-tung", {
        params: { id: $scope.selectedAppointment.idLichHen }
    }).then(function (response) {
        $scope.phuTungDaChon = response.data.result;
    });
    
    var request2 = $http.get("http://localhost:8081/test/findid/phieu-dich-vu-ct", {
        params: { id: $scope.selectedAppointment.idLichHen }
    }).then(function (response) {
        $scope.dichVuDaChon = response.data.result;
        if ($scope.dichVuDaChon.length > 0) {
            // Lấy idPhieuDichVu từ phần tử đầu tiên
            $scope.idPhieuDichVu = $scope.dichVuDaChon[0].phieuDichVu?.idPhieuDichVu || $scope.dichVuDaChon[0].phieuDichVu;
            console.log("Lấy được idPhieuDichVu:", $scope.idPhieuDichVu);
            }
    });
    
    $q.all([request1, request2]).then(function () {
        $scope.tinhTongTien(); // gọi sau khi cả 2 đã load xong
    });
    return Promise.all([request1, request2]);

}
$scope.tinhTongTien = function () {
    let tong = 0;
    if ($scope.dichVuDaChon) {
        $scope.dichVuDaChon.forEach(d => tong += d.dichVu.giaDichVu);
    }
    if ($scope.phuTungDaChon) {
        $scope.phuTungDaChon.forEach(p => tong += p.phuTung.giaPhuTung * p.soLuong);
    }
    $scope.tongTien = tong;
};
$scope.taoHoaDon = function () {
    if (!$scope.selectedAppointment || !$scope.selectedAppointment.idLichHen) {
        alert("Không tìm thấy thông tin lịch hẹn.");
        return;
    }
    var requestDataa11 = {
        idPhieuDichVu: $scope.idPhieuDichVu,
        idNhanVienTao: "NV001", // Lúc này đã có giá trị
        tongTien: $scope.tongTien,
        idLichHen: $scope.selectedAppointment.idLichHen,
        phuongThucThanhToan: $rootScope.phuongThucThanhToan,
        idKhachHang: $scope.selectedAppointment.idKhachHang
      
    };
    console.log("req", requestDataa11);
    // Gọi API tạo hóa đơn
    $http.post("http://localhost:8081/api/staff/hoa-don/tao-phieu",requestDataa11)
    .then(function (response) {
        if (response.data) {
            alert("Tạo hóa đơn thành công!");
            $scope.loadAppointments();
        } else {
            alert("Tạo hóa đơn thất bại!");
        }   
    }).catch(function (error) {
        console.error("Lỗi khi tạo hóa đơn:", error);
        alert("Đã xảy ra lỗi khi tạo hóa đơn.");
    });
    document.activeElement.blur();
    const modalEl = document.getElementById("lichHenModal");
    const modal = bootstrap.Modal.getInstance(modalEl);
    if (modal) {
        modal.hide();
    }
};
    // Thêm dịch vụ
    $scope.addService = function () {
        if ($rootScope.selectedService) {
            $scope.services.push($rootScope.selectedService);
           if ($rootScope.selectedService.tenDichVu === "Thay thế phụ tùng") {
               $scope.showPartsSelection = true;
           }
             console.log("dich vu hien tai", $scope.services);
            $rootScope.selectedService = "";
        }
    };

    $scope.removeService = function (index) {
        if ($scope.services[index].tenDichVu === "Thay thế phụ tùng") {
            $scope.showPartsSelection = false;
            $scope.parts = [];
        }
        $scope.services.splice(index, 1);
    };

    $scope.addPart = function () {
        if ($rootScope.selectedPart && $rootScope.selectedQuantity) {
            // Tạo bản sao của selectedPart và thêm thuộc tính số lượng
            const selected = angular.copy($rootScope.selectedPart);
            selected.soLuongCuaPhuTung = $rootScope.selectedQuantity;
    
            // Đẩy vào mảng
            $scope.parts.push(selected);
    
            console.log("Danh sách phụ tùng đã chọn:", $scope.parts);
    
            // Reset input
            $rootScope.selectedPart = "";
            $rootScope.selectedQuantity = "";
        }
    };
    
    $scope.removePart = function (index) {
        $scope.parts.splice(index, 1);
    };

    $scope.addService1 = function () {
        if ($rootScope.selectedService) {
            var exists = $scope.dichVuDaChon.some(function (dv) {
                return dv.dichVu.idDichVu === $rootScope.selectedService.idDichVu;
            });
    
            if (!exists) {
                const now = new Date().toISOString();
    
                // Gán giống như các phần tử từ API
                $scope.dichVuDaChon.push({
                    idPhieuDichVuCT:null, // mới thêm nên chưa có
                    giaDichVu: $rootScope.selectedService.giaDichVu || 0,
                    ngayThucHien: now,
                    dichVu: angular.copy($rootScope.selectedService),
                    phieuDichVu: $scope.dichVuDaChon[0]?.phieuDichVu,
                    isNew: true

                });
                console.log("dich vu hien tai", $scope.dichVuDaChon);
                if ($rootScope.selectedService.tenDichVu === "Thay thế phụ tùng") {
                    $scope.showPartsSelection1 = true;
                }
            }
    
            $rootScope.selectedService = null;
        }
    };
    

    $scope.removeService1 = function (index) {
        const removed = $scope.dichVuDaChon.splice(index, 1)[0];
    
        // Nếu xóa dịch vụ "Thay thế phụ tùng" thì ẩn phụ tùng
        if (removed.dichVu.tenDichVu === "Thay thế phụ tùng") {
            const stillHas = $scope.dichVuDaChon.some(function (dv) {
                return dv.dichVu.tenDichVu === "Thay thế phụ tùng";
            });
            $scope.showPartsSelection1 = stillHas;
        }
    
        // Optional: nếu bạn muốn lưu lại các dịch vụ bị xóa để gọi API xóa sau
        if (!removed.isNew) {
            $scope.deletedServices = $scope.deletedServices || [];
            $scope.deletedServices.push(removed);
        }
    };
    

    $scope.addPart1 = function () {
        if ($rootScope.selectedPart && $rootScope.selectedQuantity > 0) {
            const exists = $scope.phuTungDaChon.find(p => p.phuTung.idPhuTung === $rootScope.selectedPart.idPhuTung);
    
            if (!exists) {
                $scope.phuTungDaChon.push({
                    idPhieuPhuTung: null, // chưa lưu DB
                    phuTung: angular.copy($rootScope.selectedPart),
                    soLuong: $rootScope.selectedQuantity,
                    isNew: true
                });
            }
    
            $rootScope.selectedPart = null;
            $rootScope.selectedQuantity = null;
        }
    };
    
    
    $scope.removePart1 = function (index) {
        const removed = $scope.phuTungDaChon.splice(index, 1)[0];
    
        if (!removed.isNew) {
            $scope.deletedParts = $scope.deletedParts || [];
            $scope.deletedParts.push(removed);
        }
    };
    

    $scope.taoPhieuTinhTrang = function () {
        if (!$scope.selectedAppointment || !$scope.selectedAppointment.bienSoXe) {
            alert("Lỗi: Không có thông tin xe để tạo phiếu!");
            return;
        }

        const data = {
            idLichHen: $scope.selectedAppointment.idLichHen,
            moTaTinhTrangXe: $scope.selectedAppointment.moTaTinhTrangXe || ""
        };

        $http.post("http://localhost:8081/api/staff/phieu-tinh-trang", data)
            .then(function (response) {
                if (response.data && response.data.result) {
                    alert("Tạo phiếu ghi nhận thành công!");
                    $scope.loadAppointments();
                } else {
                    alert("Không thể tạo phiếu ghi nhận.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi tạo phiếu:", error);
                alert("Lỗi khi tạo phiếu.");
            });

        var myModalEl = document.getElementById("lichHenModal");
        var modal = bootstrap.Modal.getInstance(myModalEl);
        if (modal) {
            modal.hide();
        }
    };
    $scope.updatePhieuTinhTrang = function () {
        const data = {
            idLichHen: $scope.selectedAppointment.idLichHen,
            moTaTinhTrangXe: $scope.detailPhieuTinhTrang.moTaTinhTrangXe || "",
            idNhanVien: $scope.staff.idNhanVien
        };
        console.log("dataa",data);

        $http.put("http://localhost:8081/api/staff/phieu-tinh-trang", data)
            .then(function (response) {
                if (response.data && response.data.result) {
                    alert("Tạo phiếu ghi nhận thành công!");
                    $scope.loadAppointments();
                } else {
                    alert("Không thể tạo phiếu ghi nhận.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi tạo phiếu:", error);
                alert("Lỗi khi tạo phiếu.");
            });

        var myModalEl = document.getElementById("lichHenModal");
        var modal = bootstrap.Modal.getInstance(myModalEl);
        if (modal) {
            modal.hide();
        }
    };
    $scope.saveServiceTicket = function () {
        if ($scope.services.length === 0) {
            alert("Vui lòng chọn ít nhất một dịch vụ!");
            return;
        }
            console.log("tesst", $scope.tesst);

    $http.get("http://localhost:8081/test/findid/phieu-tinh-trang-by-lich-hen", { params: { id: $scope.selectedAppointment.idLichHen } })
                .then(function (response) {
                    if (response.data && response.data.result) {
                        $scope.idPhieuGNX = response.data.result.idPhieuGNX;
                        console.log("ID phiếu GNX:", $scope.idPhieuGNX);
                         var requestDataa = {
                                    tenNhanVienSuaChua: "An",
                                    idPhieuGNX: $scope.idPhieuGNX, // Lúc này đã có giá trị

                                    listIdDichVu: $scope.services.map(function (service) {
                                        return service.idDichVu;
                                    }),
                                    idLichHen: $scope.selectedAppointment.idLichHen,
                                    danhSachPhuTung: $scope.parts.map(p => ({ 
                                        id: p.idPhuTung,
                                        soLuong: p.soLuongCuaPhuTung
                                    }))
                                };
                                 console.log("listDv", requestDataa);

                                        $http.post("http://localhost:8081/api/staff/phieu-dich-vu/tao-phieu", requestDataa)
                                            .then(function (response) {
                                                alert("Phieu dich vu đã được tạo thành công!");
                                                $scope.loadAppointments();
                                            })
                                            .catch(function (error) {
                                                console.error("Lỗi khi tạo lịch hẹn:", error);
                                            });
                    } else {
                        console.warn("Không tìm thấy phiếu ghi nhận.");
                    }
                })
                .catch(function (error) {
                    console.error("Lỗi khi lấy phiếu ghi nhận:", error);
                });
                $scope.closeModal();
    };
    $scope.huyLichHen = function(appointment) {
        Swal.fire({
            title: 'Bạn có chắc muốn hủy lịch hẹn?',
            text: "Thao tác này sẽ cập nhật trạng thái lịch hẹn thành 'Đã hủy'.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Hủy lịch',
            cancelButtonText: 'Không',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                // Gọi API cập nhật trạng thái
                $http.put("http://localhost:8081/api/lich-hen/update-trang-thai", {
                    idLichHen: appointment.idLichHen,
                    trangThai: "Đã hủy"
                }).then(function (response) {
                    Swal.fire('✅ Đã hủy!', response.data.message, 'success');
                    $scope.loadAppointments(); // refresh danh sách
                }).catch(function (error) {
                    console.error("Lỗi khi cập nhật trạng thái:", error);
                    Swal.fire('❌ Lỗi!', 'Không thể cập nhật trạng thái.', 'error');
                });
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                // Không làm gì nếu người dùng hủy
                console.log("Người dùng đã hủy thao tác.");
            }
        });
    };
    $scope.hoanTatSuaChua = function () {
        if (!$scope.selectedAppointment || !$scope.selectedAppointment.idLichHen) {
            alert("Không có lịch hẹn để cập nhật.");
            return;
        }

        $http.put("http://localhost:8081/api/lich-hen/update-trang-thai", {
            idLichHen: $scope.selectedAppointment.idLichHen,
            trangThai: "Đã sửa chữa"
        })
        .then(function (response) {
                alert(response.data.message);
                $scope.loadAppointments();
        })
        .catch(function (error) {
            console.error("Lỗi khi cập nhật trạng thái:", error);
            alert("Lỗi khi cập nhật!");
        });

        const modalEl = document.getElementById("lichHenModal");
        const modal = bootstrap.Modal.getInstance(modalEl);
        if (modal) {
            modal.hide();
        }
    };
    $scope.confirmThanhToan = function () {
        $http.put("http://localhost:8081/api/lich-hen/update-trang-thai", {
            idLichHen: $scope.selectedAppointment.idLichHen,
            trangThai: "Hoàn tất" // Thay đổi trạng thái khi thanh toán hoàn tất
        }).then(function (response) {
            // Nếu thành công, có thể hiển thị thông báo và đóng modal
            alert("Thanh toán hoàn tất!");
            $scope.loadAppointments();
          $scope.closeModal();
            // Bạn có thể cập nhật lại trạng thái giao diện tại đây nếu cần
        }).catch(function (error) {
            alert("Có lỗi xảy ra, vui lòng thử lại.");
            console.error(error);
        });
    };
    $scope.closeModal = function () {
        // Blur toàn bộ phần tử đang giữ focus
        const modalElement = document.getElementById("lichHenModal");
        modalElement.querySelectorAll("input, select, textarea, button").forEach(el => el.blur());
    
        // Đóng modal thủ công
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        if (modalInstance) {
            modalInstance.hide();
        }
    };
    $scope.moModalQR = function () {
     
    
        setTimeout(function () {
            var modal = new bootstrap.Modal(document.getElementById('qrModal'));
            modal.show();
        }, 0);
    };
    console.log("tesst", $scope.tesst);
    $scope.loadAppointments();
    $scope.loadDichVu = function () {
            $http.get("http://localhost:8081/api/staff/phieu-dich-vu/get-all")
                .then(function (response) {
                    if (response.data) {
                        console.log("da goi api lay dich vu");
                        $scope.dichVuList = response.data;
                        console.log("listdichvu", $scope.dichVuList );
                    } else {
                        console.error("API không trả về dữ liệu hợp lệ.");
                    }
                })
                .catch(function (error) {
                    console.error("Lỗi khi tải danh sách dịch vụ:", error);
                });
        };
    $scope.loadPhuTung = function () {
        $http.get("http://localhost:8081/api/staff/phieu-dich-vu/get-all-phu-tung")
            .then(function (response) {
                if (response.data) {
                    console.log("da goi api lay phu tung");
                    $scope.phuTungList = response.data;
                    console.log("listphutung", $scope.phuTungList );
                } else {
                    console.error("API không trả về dữ liệu hợp lệ.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi tải danh sách phu tung:", error);
            });
    };
    $scope.loadDichVu();
    $scope.loadPhuTung();
});


appEmployee.controller("TaoLichHenController", function ($scope, $http) {
    $scope.phone = "";
    $scope.customerExists = false;
    $scope.phoneChecked = false;
    $scope.tenKhachHang = "";
    $scope.bienSoXe = "";
    $scope.moTa = "";
    $scope.thoiGian = "";
    $scope.idLoaiXe = "";
    $scope.services = [];  // Mảng lưu trữ các dịch vụ đã chọn
    $scope.selectedService = null; // Dịch vụ đang được chọn
    $scope.listIdDichVu = []; // Danh sách các dịch vụ

    // Hàm kiểm tra số điện thoại
    $scope.checkPhone = function () {
        if (!$scope.phone) {
            alert("Vui lòng nhập số điện thoại!");
            return;
        }
        var phoneRegex = /^\d{10}$/;
        if (!phoneRegex.test($scope.phone)) {
            alert("Số điện thoại phải đúng 10 chữ số!");
            return;
        }
        $http.get("http://localhost:8081/api/lich-hen/checkPhone", { params: { phone: $scope.phone } })
            .then(function (response) {
                if (response.data.result) {
                    $scope.customerExists = true;
                    $scope.tenKhachHang = response.data.result.tenKhachHang;
                } else {
                    $scope.customerExists = false;
                    $scope.tenKhachHang = "";
                    alert("Không tìm thấy khách hàng với số điện thoại này.");
                }
                $scope.phoneChecked = true;
            })
            .catch(function (error) {
                console.error("Lỗi khi kiểm tra số điện thoại:", error);
            });
    };

    // Hàm thêm dịch vụ vào mảng dịch vụ đã chọn
    $scope.addService = function () {
        if ($scope.selectedService && !$scope.services.includes($scope.selectedService)) {
            $scope.services.push($scope.selectedService);
            $scope.selectedService = null; // Reset selected service sau khi thêm
            console.log("Danh sách dịch vụ đã chọn: ", $scope.services);
        } else {
            alert("Vui lòng chọn dịch vụ để thêm.");
        }
    };

    // Hàm xóa dịch vụ khỏi mảng dịch vụ đã chọn
    $scope.removeService = function (index) {
        $scope.services.splice(index, 1);
    };

    // Hàm tạo lịch hẹn
    $scope.taoLichHen = function () {
        if (!$scope.bienSoXe) {
            alert("Vui lòng nhập biển số xe!");
            return;
        }

        if (!$scope.tenKhachHang || $scope.tenKhachHang.trim() === "") {
            alert("Vui lòng nhập tên khách hàng!");
            return;
        }

        if ($scope.services.length === 0) {
            alert("Vui lòng chọn ít nhất một dịch vụ!");
            return;
        }

        var requestData = {
            idKhachHang: $scope.phone,
            thoiGian: $scope.thoiGian ? new Date($scope.thoiGian).toISOString() : new Date().toISOString(),
            bienSoXe: $scope.bienSoXe,
            ghiChu: $scope.moTa,
            listIdDichVu: $scope.services.map(function (service) {
                       return service.idDichVu;
                   }),
            tenKhachHang: $scope.tenKhachHang
        };
        console.log("listDv", $scope.listIdDichVu);

        // Gửi yêu cầu tạo lịch hẹn
        $http.post("http://localhost:8081/api/lich-hen/tao-lich-hen", requestData)
            .then(function (response) {
                alert("Lịch hẹn đã được tạo thành công!");
                location.reload(); // Tải lại trang
            })
            .catch(function (error) {
                console.error("Lỗi khi tạo lịch hẹn:", error);
            });
    };

    // Hàm tải danh sách dịch vụ
    $scope.loadDichVu = function () {
        $http.get("http://localhost:8081/api/staff/phieu-dich-vu/get-all")
            .then(function (response) {
                if (response.data) {
                    $scope.dichVuList = response.data; // Gán danh sách dịch vụ vào scope
                } else {
                    console.error("API không trả về dữ liệu hợp lệ.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi tải danh sách dịch vụ:", error);
            });
    };

    $scope.loadDichVu(); // Tải danh sách dịch vụ khi khởi tạo controller
});
appEmployee.directive('fileModel', ['$parse', function ($parse) {
    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
        var model = $parse(attrs.fileModel);
        var modelSetter = model.assign;

        element.bind('change', function () {
          scope.$apply(function () {
            modelSetter(scope, element[0].files[0]);
          });
        });
      }
    };
  }]);
  appEmployee.controller("thongTinStaffController", function ($scope, $http) {
    $scope.previewUrl = null;
    $scope.selectedFileName = "";
    
    $scope.previewImage = function(input) {
        const file = input.files[0];
        if (file) {
          $scope.previewUrl = URL.createObjectURL(file);
          $scope.selectedFileName = file.name;
          $scope.$apply();
        }
      };
    // Mở modal chỉnh sửa thông tin nhân viên
    $scope.openEditModal = function () {
        // Gọi API lấy thông tin nhân viên nếu chưa có
        // Nếu thông tin đã có thì không cần gọi lại, chỉ cần show modal
        $('#editStaffModal').modal('show');
    };

    $scope.uploadImage = function() {
        if (!$scope.nhanVien || !$scope.nhanVien.file) return alert("Vui lòng chọn ảnh");
  
        var formData = new FormData();
        formData.append("file", $scope.nhanVien.file);
  
        $http.post("/api/nhanvien/testupload/" + idNhanVien + "/upload-avatar", formData, {
          transformRequest: angular.identity,
          headers: { 'Content-Type': undefined },
        }).then(function(response) {
          alert(response.data.message);
          $scope.imageUrl = "/images/" + response.data.fileName;
          $scope.previewUrl = null;
          $scope.selectedFileName = "";
          $scope.nhanVien.file = null;
          document.getElementById("modalFileInput").value = null;
          bootstrap.Modal.getInstance(document.getElementById('avatarModal')).hide();
        }, function(error) {
          alert("Upload thất bại!");
          console.error("Lỗi upload", error);
        });
      };

    // Cập nhật thông tin nhân viên k hình
    $scope.updateStaff = function () {
        const reqstaff = {
            idNhanVien: $scope.staff.idNhanVien,
            ten: $scope.staff.ten,
            email: $scope.staff.email,
            diaChi: $scope.staff.diaChi,
            // Đảm bảo rằng hinhAnh được cập nhật (nếu có thay đổi)
            // hinhAnh: $scope.staff.hinhAnh 
        };

        $http.put('/api/nhan-vien-thong-tin', reqstaff).then(
          function (response) {
            alert('Cập nhật thành công!');
            $scope.closeModal();
          },
          function (error) {
            alert('Cập nhật thất bại!');
          }
        );
    };

    // Đóng modal
    $scope.closeModal = function (){
        const modalElement = document.getElementById('editStaffModal');
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        
        if (modalInstance) {
          modalInstance.hide();
        } else {
          // Nếu modal chưa được khởi tạo, khởi tạo rồi ẩn
          const newModal = new bootstrap.Modal(modalElement);
          newModal.hide();
        }

        // Xử lý blur input nếu cần
        modalElement.querySelectorAll("input, select, textarea, button").forEach(el => el.blur());
    };
});


appEmployee.controller("doiMatKhauStaffController", function ($scope, $http, $rootScope) {
    $scope.matKhauMoi = "";
    $scope.xacNhanMatKhauMoi = "";
    $scope.loiDoiMatKhau = "";
    $scope.doiMatKhauThanhCong = "";

    $scope.doiMatKhau = function () {
      
    
        const idTaiKhoan = sessionStorage.getItem("idTaiKhoan");
        console.log("idtaikhoan lay tu ss", idTaiKhoan);
    
        // Bước 1: Lấy tài khoản để xác minh mật khẩu cũ
        $http.put("http://localhost:8081/api/staff/doi-mat-khau/doi", {
            id: idTaiKhoan,
            matKhauCu: $scope.matKhauCu,
            matKhauMoi: $scope.matKhauMoi
        }).then(function (res) {
            const msg = res.data.message;
            if (msg === "Mật khẩu cũ không chính xác.") {
                $scope.loiDoiMatKhau = msg;
                return;
            }
        
            $scope.doiMatKhauThanhCong = msg || "Đổi mật khẩu thành công!";
            $scope.matKhauCu = "";
            $scope.matKhauMoi = "";
            $scope.xacNhanMatKhauMoi = "";
        }).catch(function () {
            $scope.loiDoiMatKhau = "Có lỗi xảy ra khi đổi mật khẩu.";
        });
    };
    
});
appEmployee.controller("traCuuPhieuGhiNhanController", function ($scope, $http) {
    // $rootScope.pageTitle = "Tra cứu Phiếu",
    $scope.keyword = "";
    $scope.appointments = [];
    $scope.currentDay = new Date();

    $scope.search = function () {
        if (!$scope.keyword || $scope.keyword.length < 3) {
            alert("Vui lòng nhập ít nhất 3 ký tự để tìm kiếm.");
            return;
        }
        const url = `http://localhost:8081/api/staff/phieu-tinh-trang/search?keyword=${$scope.keyword}`;

        $http.get(url)
            .then(function (response) {
                if (response.data && response.data.result) {
                    $scope.appointments = response.data.result;
                } else {
                    $scope.appointments = [];
                    console.warn("Không có kết quả.");
                }
            })
            .catch(function (error) {
                console.error("Lỗi khi tìm kiếm:", error);
                $scope.appointments = [];
            });
    };

});
appEmployee.controller('traCuuHoaDonController', function($scope, $http) {
    $scope.invoices = [];
    $scope.keyword = '';
    
    $scope.search = function() {
        if (!$scope.keyword || $scope.keyword.length < 3) {
            alert("Vui lòng nhập ít nhất 3 ký tự để tìm kiếm.");
            return;
        }
        const apiUrl = '/api/staff/hoa-don/search'; // API của bạn cho việc tìm kiếm hóa đơn
        $http.get(apiUrl, { params: { keyword: $scope.keyword } })
            .then(function(response) {
                if (response.data.code === 3206) {
                    $scope.invoices = response.data.result;
                } else {
                    $scope.invoices = [];
                    alert("Không tìm thấy hóa đơn nào.");
                }
            })
            .catch(function(error) {
                console.error('Lỗi khi gọi API:', error);
                $scope.invoices = [];
            });
    };
});

appEmployee.controller('lHChoXacNhanController', function($scope, $http) {
    $http.get('/api/lich-hen/cho-xac-nhan')
    .then(function (response) {
        if (response.data && response.data.result) {
            $scope.lschoxacnhan = response.data.result;
            $scope.lschoxacnhanFiltered = $scope.lschoxacnhan; // Mặc định show hết
            console.log("t",$scope.lschoxacnhan);
        } else {
            console.error("API không trả về dữ liệu hợp lệ.");
        }
    })
    .catch(function (error) {
        console.error("Lỗi khi lấy dữ liệu:", error);
    });
    $scope.timLichTheoNgay = function () {
        if (!$scope.ngayChon) {
            // Nếu không có ngày nào được chọn (đã clear)
            $scope.lschoxacnhanFiltered = angular.copy($scope.lschoxacnhan);
        } else {
            const selectedDate = new Date($scope.ngayChon).setHours(0, 0, 0, 0);
            $scope.lschoxacnhanFiltered = $scope.lschoxacnhan.filter(function (lich) {
                const lichDate = new Date(lich.thoiGian).setHours(0, 0, 0, 0);
                return lichDate === selectedDate;
            });
        }
    };
    // $scope.xacNhanLichHen = function(lichHen) {
    //     const xacNhan = confirm("Bạn có muốn xác nhận lịch hẹn này không?");
    //     if (xacNhan) {
    //         // Gọi API hoặc hàm cập nhật trạng thái
    //         lichHen.trangThai = "Đã Xác Nhận";
    
    //         // Nếu dùng API gọi backend thì ví dụ:
    //         $http.put("http://localhost:8081/api/lich-hen/update-trang-thai", {
    //             idLichHen: lichHen.idLichHen,
    //             trangThai: lichHen.trangThai
    //         })
    //             .then(function(response) {
    //                 alert("Đã xác nhận lịch hẹn!");
    //             }, function(error) {
    //                 alert("Xác nhận thất bại.");
    //             });
    //     }
    // };
    $scope.xacNhanLichHen = function(lichHen) {
        Swal.fire({
            title: 'Xác nhận lịch hẹn?',
            text: "Bạn có chắc chắn muốn xác nhận lịch hẹn này?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                lichHen.trangThai = "Đã Xác Nhận";
    
                $http.put("http://localhost:8081/api/lich-hen/update-trang-thai", {
                    idLichHen: lichHen.idLichHen,
                    trangThai: lichHen.trangThai
                }).then(function(response) {
                    Swal.fire('Thành công!', 'Lịch hẹn đã được xác nhận.', 'success');
                }, function(error) {
                    Swal.fire('Lỗi!', 'Xác nhận thất bại.', 'error');
                });
            }
        });
    };
    
        console.log("mmm",$scope.lschoxacnhanFiltered );
});
appEmployee.controller('lHChuaHoanTatController', function($scope, $http) {
    $scope.currentPage = 1;
    $scope.pageSize = 10;
    
    // Lấy dữ liệu lịch hẹn chưa hoàn tất
    $http.get('/api/lich-hen/chua-hoan-tat')
    .then(function (response) {
        if (response.data && response.data.result) {
            $scope.lschuahoantat = response.data.result;
            $scope.lschuahoantatFiltered = $scope.lschuahoantat; // Mặc định show hết
            console.log("t", $scope.lschuahoantat);
        } else {
            console.error("API không trả về dữ liệu hợp lệ.");
        }
    })
    .catch(function (error) {
        console.error("Lỗi khi lấy dữ liệu:", error);
    });

    // Tìm lịch hẹn theo ngày
    $scope.timLichTheoNgay = function () {
        if (!$scope.ngayChon) {
            // Nếu không có ngày nào được chọn (đã clear)
            $scope.lschuahoantatFiltered = angular.copy($scope.lschuahoantat);
        } else {
            const selectedDate = new Date($scope.ngayChon).setHours(0, 0, 0, 0);
            $scope.lschuahoantatFiltered = $scope.lschuahoantat.filter(function (lich) {
                const lichDate = new Date(lich.thoiGian).setHours(0, 0, 0, 0);
                return lichDate === selectedDate;
            });
        }
    };

    // Xác nhận lịch hẹn
    $scope.confirmAppointment = function(lich) {
        // Lấy thông tin từ lịch hẹn đã chọn
        const appointmentId = lich.idLichHen;
        console.log("id lich hen trong trang trang chua hoan tat", appointmentId);
        const today = new Date();
        // const todayString = today.toISOString().split('T')[0];  // Lấy ngày hiện tại theo định dạng YYYY-MM-DD

        // Hiển thị hộp thoại xác nhận với SweetAlert2
        Swal.fire({
            title: 'Bạn có muốn tiếp tục lịch hẹn này?',
            text: "Khi bạn nhấn xác nhận, ngày sẽ được cập nhật thành hôm nay.",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                // Gọi API để cập nhật ngày lịch hẹn
                $http({
                    method: 'POST',
                    url: '/api/lich-hen/update-ngay',
                    data: {
                        idLichHen: appointmentId, // ID lịch hẹn từ đối tượng được chọn
                        thoiGian: today // Cập nhật ngày thành hôm nay
                    }
                }).then(function(response) {
                    // Thực hiện gì đó khi API thành công, ví dụ thông báo thành công
                    Swal.fire(
                        'Thành công!',
                        'Ngày lịch hẹn đã được cập nhật.',
                        'success'
                    );
                    lich.thoiGian = today;
                }, function(error) {
                    // Thông báo lỗi nếu có sự cố khi gọi API
                    Swal.fire(
                        'Lỗi!',
                        'Không thể cập nhật ngày lịch hẹn.',
                        'error'
                    );
                });
            }
        });
    };
    $scope.huyLichHen = function(lich) {
        const appointmentId = lich.idLichHen;
        Swal.fire({
            title: 'Bạn có chắc muốn hủy lịch hẹn?',
            text: "Thao tác này sẽ cập nhật trạng thái lịch hẹn thành 'Đã hủy'.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Hủy lịch',
            cancelButtonText: 'Không',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                // Gọi API cập nhật trạng thái
                $http.put("http://localhost:8081/api/lich-hen/update-trang-thai", {
                    idLichHen: appointmentId,
                    trangThai: "Đã hủy"
                }).then(function (response) {
                    Swal.fire('✅ Đã hủy!', response.data.message, 'success');
                    lich.trangThai = "Đã hủy";
                }).catch(function (error) {
                    console.error("Lỗi khi cập nhật trạng thái:", error);
                    Swal.fire('❌ Lỗi!', 'Không thể cập nhật trạng thái.', 'error');
                });
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                // Không làm gì nếu người dùng hủy
                console.log("Người dùng đã hủy thao tác.");
            }
        });
    };
    $scope.changePage = function(pageNum, $event) {
        $event.preventDefault();
        $scope.currentPage = pageNum;
    };
    
    $scope.getPageNumbers = function () {
        if (!$scope.lschuahoantatFiltered || !$scope.lschuahoantatFiltered.length) {
            return [];
        }
        $scope.totalPages = Math.ceil($scope.lschuahoantatFiltered.length / $scope.pageSize);
        var pages = [];
        for (var i = 1; i <= $scope.totalPages; i++) {
            pages.push(i);
        }
        return pages;
    };
    
    console.log("mmm", $scope.lschuahoantatFiltered);
});

appEmployee.filter('startFrom', function () {
    return function (input, start) {
        if (!input || !input.length) return [];
        return input.slice(start);
    };
});
appEmployee.controller("homeStaffController", function ($http, $scope) {
    $scope.thongKeLichHenTheoTuan = function() {
        const today = new Date();
        const startOfWeek = new Date(today);
        startOfWeek.setDate(today.getDate() - today.getDay() + 1); // Monday
        startOfWeek.setHours(0, 0, 0, 0);
    
        const endOfWeek = new Date(today);
        endOfWeek.setDate(today.getDate() + (7 - today.getDay())); // Sunday
        endOfWeek.setHours(23, 59, 59, 999);
    
        const countByDay = {
            'Thứ Hai': 0,
            'Thứ Ba': 0,
            'Thứ Tư': 0,
            'Thứ Năm': 0,
            'Thứ Sáu': 0,
            'Thứ Bảy': 0,
            'Chủ Nhật': 0
        };
    
        $scope.allLichHen.forEach(function(lich) {
            const date = new Date(lich.thoiGian);
            if (date >= startOfWeek && date <= endOfWeek) {
                const day = date.getDay(); // 0 = Chủ Nhật, 1 = Thứ Hai, ...
                const map = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
                const tenThu = map[day];
                countByDay[tenThu]++;
            }
        });
    
        $scope.thongKeTuan = countByDay;
        $scope.veBieuDoThongKeTuan();
    };

    $scope.veBieuDoThongKeTuan = function() {
        const ctx = document.getElementById('lichHenChartTuan').getContext('2d');
        const labels = ['Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy', 'Chủ Nhật'];
        const data = labels.map(label => $scope.thongKeTuan[label] || 0);
    
        if ($scope.bieuDoLichHenTuan) {
            $scope.bieuDoLichHenTuan.destroy(); // Xoá biểu đồ cũ nếu có
        }
    
        $scope.bieuDoLichHenTuan = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Số lịch hẹn',
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    borderRadius: 2, // Bo tròn đầu cột
                    borderSkipped: false, // Không bỏ viền nào
                    barThickness: 10, // Độ dày cột
                    hoverBackgroundColor: 'rgba(84, 215, 69, 0.8)'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        enabled: true
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        },
                        grid: {
                            color: "#eee"
                        }
                    },
                    x: {
                        grid: {
                            display: false
                        }
                    }
                }
            }
        });
    };
    
    // Thống kê lịch hẹn theo năm
    $scope.thongKeLichHenTheoNam = function() {
        const today = new Date();
        const year = today.getFullYear();

        const countByMonth = {
            'Tháng 1': 0, 'Tháng 2': 0, 'Tháng 3': 0, 'Tháng 4': 0,
            'Tháng 5': 0, 'Tháng 6': 0, 'Tháng 7': 0, 'Tháng 8': 0,
            'Tháng 9': 0, 'Tháng 10': 0, 'Tháng 11': 0, 'Tháng 12': 0
        };

        $scope.allLichHen.forEach(function(lich) {
            const date = new Date(lich.thoiGian);
            if (date.getFullYear() === year) {
                const month = date.getMonth(); // 0 - January
                const map = [
                    'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4',
                    'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8',
                    'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'
                ];
                const tenThang = map[month];
                countByMonth[tenThang]++;
            }
        });

        $scope.thongKeNam = countByMonth;
        $scope.veBieuDoThongKeNam();
    };

    $scope.veBieuDoThongKeNam = function() {
        const ctx = document.getElementById('lichHenChartNam').getContext('2d');
        const labels = [
            'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4',
            'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8',
            'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'
        ];
        const data = labels.map(label => $scope.thongKeNam[label] || 0);
    
        if ($scope.bieuDoLichHenNam) {
            $scope.bieuDoLichHenNam.destroy();
        }
    
        $scope.bieuDoLichHenNam = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Số lịch hẹn',
                    data: data,
                    backgroundColor: 'rgba(153, 102, 255, 0.6)',
                    borderColor: 'rgba(153, 102, 255, 1)',
                    borderWidth: 1,
                    borderRadius: 2,
                    borderSkipped: false,
                    barThickness: 10,
                    hoverBackgroundColor: 'rgba(84, 215, 69, 0.8)'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        enabled: true
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        },
                        grid: {
                            color: "#eee"
                        }
                    },
                    x: {
                        grid: {
                            display: false
                        }
                    }
                }
            }
        });
    };
    

    // Watch để đợi allLichHen load xong mới vẽ
    $scope.$watch('allLichHen', function(newVal) {
        if (newVal && newVal.length > 0) {
            console.log('Đã có dữ liệu lịch hẹn, bắt đầu thống kê...');
            $scope.thongKeLichHenTheoTuan();
            $scope.thongKeLichHenTheoNam();
            $scope.tinhThongKeLichHenHomNay();
        }
    });
    $scope.tinhThongKeLichHenHomNay = function() {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const endOfToday = new Date(today);
        endOfToday.setHours(23, 59, 59, 999);
    
        const yesterday = new Date(today);
        yesterday.setDate(yesterday.getDate() - 1);
        yesterday.setHours(0, 0, 0, 0);
        const endOfYesterday = new Date(yesterday);
        endOfYesterday.setHours(23, 59, 59, 999);
    
        // Biến đếm hôm nay
        let tongHomNay = 0, choXacNhanHomNay = 0, daHoanThanhHomNay = 0;
        // Biến đếm hôm qua
        let tongHomQua = 0, choXacNhanHomQua = 0, daHoanThanhHomQua = 0;
    
        $scope.allLichHen.forEach(function(lich) {
            const thoiGian = new Date(lich.thoiGian);
    
            if (thoiGian >= today && thoiGian <= endOfToday) {
                tongHomNay++;
                if (lich.trangThai === 'Chờ xác nhận') choXacNhanHomNay++;
                if (lich.trangThai === 'Hoàn thành') daHoanThanhHomNay++;
            }
    
            if (thoiGian >= yesterday && thoiGian <= endOfYesterday) {
                tongHomQua++;
                if (lich.trangThai === 'Chờ xác nhận') choXacNhanHomQua++;
                if (lich.trangThai === 'Hoàn thành') daHoanThanhHomQua++;
            }
        });
    
        // Gán dữ liệu
        $scope.soLichHenHomNay = tongHomNay;
        $scope.soLichHenChoXacNhan = choXacNhanHomNay;
        $scope.soLichHenDaHoanThanh = daHoanThanhHomNay;
    
        $scope.soLichHenHomQua = tongHomQua;
        $scope.soLichHenChoXacNhanHomQua = choXacNhanHomQua;
        $scope.soLichHenDaHoanThanhHomQua = daHoanThanhHomQua;
    
        // Hàm tính phần trăm
        function tinhPhanTram(homNay, homQua) {
            if (homQua === 0) return 0;
            return ((homNay - homQua) / homQua * 100).toFixed(1);
        }
    
        $scope.phanTramHomNay = tinhPhanTram(tongHomNay, tongHomQua);
        $scope.phanTramChoXacNhan = tinhPhanTram(choXacNhanHomNay, choXacNhanHomQua);
        $scope.phanTramDaHoanThanh = tinhPhanTram(daHoanThanhHomNay, daHoanThanhHomQua);
    };
    
});
appEmployee.controller("LogoutController", function ($scope, $location) {
    AuthService.logout();
    $location.path("/");
});