var app = angular.module("myApp", ["ngRoute"]);
app.config(function ($routeProvider) {
$routeProvider
  .when("/", {
    templateUrl: "/employee/content/hoadonstaff.html",
  })
  .when("/hoa-don", {
    templateUrl: "/employee/content/hoadonstaff.html",
  })
  .when("/tra-cuu-lich-hen", {
    templateUrl: "/employee/content/tracuulichhen.html",
    controller: "lichHenController",
  })
  .when("/nhan-vien-tao-lich-hen", {
    templateUrl: "/employee/content/staffTaoLichHen.html",
  })
  .otherwise({
    redirectTo: "/",
  });
});
app.controller("laman", function ($http, $scope) {
$scope.lichhen = []; // Dữ liệu lich hen

$scope.isSidebarHidden = false;
$scope.currentYear = new Date().getFullYear();
$scope.currentDay = new Date();
$scope.toggleSidebar = function () {
  $scope.isSidebarHidden = !$scope.isSidebarHidden;
  console.log("Sidebar hidden:", $scope.isSidebarHidden);
};
});

app.controller("lichHenController", function ($scope, $http, $rootScope , $q) {
    $scope.appointments = [];
    $scope.selectedAppointment = null;
    $scope.panelTemplate = ""; // Load giao diện modal phù hợp
    $scope.dichVuList = [];
    $scope.phuTungList = [];


    // Gọi API lấy danh sách lịch hẹn
    $scope.loadAppointments = function () {
        $http.get("http://localhost:8081/api/lich-hen/today")
            .then(function (response) {
                if (response.data && response.data.result) {
                    $scope.appointments = response.data.result;
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


                break;
               case "Đang sửa chữa":
                   $scope.panelTemplate = "/employee/content/panel/xacNhanHoanTatPanel.html";
                   break;
               case "Đã sửa chữa":
                   $scope.panelTemplate = "/employee/content/panel/thanhToanPanel.html"; // <- File html cho modal thanh toán
                    //  $http.get("http://localhost:8081/test/findid/phieu-phu-tung", {
                    //        params: { id: $scope.selectedAppointment.idLichHen }
                    //    }).then(function (response) {
                    //        $scope.phuTungDaChon = response.data.result;
                    //        $scope.tinhTongTien(); // Cập nhật tổng tiền sau khi load
                    //    });

                    //    $http.get("http://localhost:8081/test/findid/phieu-dich-vu-ct", {
                    //        params: { id: $scope.selectedAppointment.idLichHen }
                    //    }).then(function (response) {
                    //        $scope.dichVuDaChon = response.data.result;
                    //        $scope.tinhTongTien(); // Cập nhật tổng tiền sau khi load
                    //    });
                    $scope.layDichVuVaPhuTung();
                    
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
        if (response.data && response.data.success) {
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

    // Thêm phụ tùng
    // $scope.addPart = function () {
    //     if ($rootScope.selectedPart && $rootScope.selectedQuantity) {
    //         $scope.parts.push( $rootScope.selectedPart.push( $rootScope.selectedQuantity));
    //         console.log("select part: ", $scope.part);
    //         $rootScope.selectedPart = "";
    //         $rootScope.selectedQuantity = "";
    //     }
    // };
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
    $scope.openModalXem = function (appointment) {
    $scope.selectedAppointment = appointment;
        console.log("appointment", appointment);
    switch (appointment.trangThai) {
        case "Đã xác nhận":
            $scope.panelTemplate = "/employee/content/panel/lichHenDetailPanel.html";
            break;
        case "Đang kiểm tra":
            $scope.panelTemplate = "/employee/content/panel/xemPhieuTinhTrangPanel.html";
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
            // if (appointment.phuongThucThanhToan === "Chuyen Khoan") {
            //     const qrRequest = {
            //         accountNo: "0987300853",
            //         accountName: "TRUONG LAM AN",
            //         acqId: 970422, // MB Bank
            //         amount: appointment.tongTien,
            //         addInfo: "Thanh toan lich hen #" + appointment.id,
            //         template: "compact2"
            //     };
        
            //     fetch("https://api.vietqr.io/v2/generate", {
            //         method: "POST",
            //         headers: { "Content-Type": "application/json" },
            //         body: JSON.stringify(qrRequest)
            //     })
            //     .then(res => res.json())
            //     .then(data => {
            //         $scope.$apply(() => {
            //             $scope.qrcodeURL = data.data.qrDataURL;
            //         });
            //     })
            //     .catch(err => {
            //         console.error("Lỗi gọi API VietQR:", err);
            //     });
            // }
            break;
        default:
            alert("Không xác định trạng thái để xem.");
            return;
    }

    $('#lichHenModal').modal('show');
    // if (appointment.trangThai === "Chờ thanh toán") {
    //     // Gọi API tạo VietQR
    //     const qrRequest = {
    //         accountNo: "0987300853", // số tài khoản của bạn
    //         accountName: "TRUONG LAM AN", // tên chủ tài khoản
    //         acqId: 970422, // mã ngân hàng (MB bank)
    //         amount: 10000, // số tiền
    //         addInfo: "Thanh toan lich hen #",
    //         template: "compact2"
    //     };

    //     fetch("https://api.vietqr.io/v2/generate", {
    //         method: "POST",
    //         headers: { "Content-Type": "application/json" },
    //         body: JSON.stringify(qrRequest)
    //     })
    //     .then(res => res.json())
    //     .then(data => {
    //         // Gán vào scope để hiển thị QR
    //         $scope.$apply(() => {
    //             $scope.qrImage = data.data.qrDataURL;
    //         });
    //     })
    //     .catch(err => console.error("Lỗi gọi API VietQR:", err));
    // }

};
    $scope.openModalThayDoi = function (appointment) {
    $scope.selectedAppointment = appointment;

    switch (appointment.trangThai) {
        case "Đã xác nhận":
            $scope.panelTemplate = "/employee/content/panel/lichHenEditPanel.html";
            break;
        case "Đang kiểm tra":
            $scope.panelTemplate = "/employee/content/panel/ghiNhanTinhTrangPanel.html";
            break;
        default:
            alert("Không xác định trạng thái để chỉnh sửa.");
            return;
    }

    $('#lichHenModal').modal('show');
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
                                                location.reload();
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
            $http.get("http://localhost:8081/api/admin/dichvu")
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
        $http.get("http://localhost:8081/api/admin/phutung")
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



// app.controller("thanhToanController", function($scope, $http) {
//     var idLichHen = $scope.selectedAppointment.idLichHen;

//     $http.get("http://localhost:8081/test/findid/phieu-phu-tung", { params: { id: idLichHen } })
//         .then(function(response) {
//             $scope.phuTungDaChon = response.data.result;
//         });

//     $http.get("http://localhost:8081/test/findid/phieu-dich-vu-ct", { params: { id: idLichHen } })
//         .then(function(response) {
//             $scope.dichVuDaChon = response.data.result;
//         });

//     // Tính tổng tiền
//     $scope.tinhTongTien = function () {
//         let tong = 0;
//         if ($scope.dichVuDaChon) {
//             $scope.dichVuDaChon.forEach(d => tong += d.dichVu.giaDichVu);
//         }
//         if ($scope.phuTungDaChon) {
//             $scope.phuTungDaChon.forEach(p => tong += p.donGia * p.soLuong);
//         }
//         $scope.tongTien = tong;
//     };

//     // Tự động tính lại mỗi lần dữ liệu thay đổi
//     $scope.$watchGroup(['dichVuDaChon', 'phuTungDaChon'], function () {
//         $scope.tinhTongTien();
//     });
// });




app.controller("TaoLichHenController", function ($scope, $http) {
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

        $http.get("http://localhost:8081/api/lich-hen/checkPhone", { params: { phone: $scope.phone } })
            .then(function (response) {
                if (response.data.result) {
                    $scope.customerExists = true;
                    $scope.tenKhachHang = response.data.result.tenKhachHang;
                } else {
                    $scope.customerExists = false;
                    $scope.tenKhachHang = "";
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
        $http.get("http://localhost:8081/api/admin/dichvu")
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
