var app = angular.module("myApp", ["ngRoute"]);
app.config(function ($routeProvider) {
$routeProvider
  .when("/", {
    templateUrl: "/employee/content/homestaff.html",
  })
  .when("/hoa-don", {
    templateUrl: "/employee/content/hoadonstaff.html",
  })
  .when("/tra-cuu-lich-hen", {
    templateUrl: "/employee/content/taolichhen.html",
    controller: "lichHenController",
  })
  .when("/nhan-vien-tao-lich-hen", {

    templateUrl: "/employee/content/staffTaoLichHen.html",
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
  .otherwise({
    redirectTo: "/",
  });
});
app.controller("laman", function ($http, $scope) {
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
app.filter('vnd', function () {
    return function (input) {
      if (isNaN(input)) return input;
      return parseInt(input).toLocaleString('vi-VN') + 'đ';
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
        $scope.openModalThayDoi = function (appointment) {
        $scope.selectedAppointment = appointment;
        console.log("ttlichhen", $scope.selectedAppointment);
    
        switch (appointment.trangThai) {
            case "Đã xác nhận":
                $scope.panelTemplate = "/employee/content/panel/lichHenEditPanel.html";
                $scope.selectedAppointment.thoiGian = new Date($scope.selectedAppointment.thoiGian);
    
                break;
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
app.directive('fileModel', ['$parse', function ($parse) {
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
app.controller("thongTinStaffController", function ($scope, $http) {
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


  app.controller("doiMatKhauStaffController", function ($scope, $http, $rootScope) {
    $scope.matKhauMoi = "";
    $scope.xacNhanMatKhauMoi = "";
    $scope.loiDoiMatKhau = "";
    $scope.doiMatKhauThanhCong = "";

    $scope.doiMatKhau = function () {
        $scope.loiDoiMatKhau = "";
        $scope.doiMatKhauThanhCong = "";
    
        if (!$scope.matKhauCu || !$scope.matKhauMoi || !$scope.xacNhanMatKhauMoi) {
            $scope.loiDoiMatKhau = "Vui lòng nhập đầy đủ thông tin.";
            return;
        }
    
        if ($scope.matKhauMoi !== $scope.xacNhanMatKhauMoi) {
            $scope.loiDoiMatKhau = "Mật khẩu mới và xác nhận không trùng khớp.";
            return;
        }
    
        const idTaiKhoan = "0912345678"; // hoặc lấy từ $rootScope.currentUser.taiKhoanNV
    
        // Bước 1: Lấy tài khoản để xác minh mật khẩu cũ
        $http.get("http://localhost:8081/api/staff/doi-mat-khau", {
            params: { id: idTaiKhoan }
        }).then(function (res) {
            const taiKhoan = res.data.result;
            if (!taiKhoan || taiKhoan.matKhau !== $scope.matKhauCu) {
                $scope.loiDoiMatKhau = "Mật khẩu cũ không chính xác.";
                return;
            }
    
            // Bước 2: Gọi API đổi mật khẩu
            $http.put("http://localhost:8081/api/staff/doi-mat-khau", null, {
                params: {
                    id: idTaiKhoan,
                    mk: $scope.matKhauMoi
                }
            }).then(function () {
                $scope.doiMatKhauThanhCong = "Đổi mật khẩu thành công!";
                $scope.matKhauCu = "";
                $scope.matKhauMoi = "";
                $scope.xacNhanMatKhauMoi = "";
            }).catch(function () {
                $scope.loiDoiMatKhau = "Đổi mật khẩu thất bại!";
            });
    
        }).catch(function () {
            $scope.loiDoiMatKhau = "Không thể xác minh mật khẩu cũ.";
        });
    };
    
});
app.controller("traCuuPhieuGhiNhanController", function ($scope, $http) {
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
app.controller('traCuuHoaDonController', function($scope, $http) {
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


  