app.controller('ScheduleController', function($scope, $http) {
    $scope.vehicles = [];
    $scope.formData = {
        idLichHen: "",
        idLoaiXe: '',
        thoiGian: '',
        bienSoXe: '',
        trangThai: 'Chờ xử lý',
        ghiChu: '',
        dichVu: ''
    };

    $http.get('http://localhost:8080/schedule/vehicles').then(function(response) {
        $scope.vehicles = response.data;
    }, function(error) {
        console.error('Lỗi khi tải danh sách loại xe', error);
    });

    $scope.submitForm = function() {
        if (!$scope.formData.idLoaiXe || !$scope.formData.thoiGian || !$scope.formData.dichVu) {
            alert('Vui lòng điền đầy đủ thông tin!');
            return;
        }

        // Lấy idTaiKhoan từ localStorage
        var idTaiKhoan = localStorage.getItem("idTaiKhoan");
        if (!idTaiKhoan) {
            Swal.fire({
                title: "Thất bại",
                text: "Bạn chưa đăng nhập!",
                icon: "error"
            });
            return;
        }

        $http.post('http://localhost:8080/schedule/save?idloaixe=' + $scope.formData.idLoaiXe+"&account="+idTaiKhoan, $scope.formData)
            .then(function(response) {
                Swal.fire({
                    title: "Thành công",
                    text: response.message,
                    icon: "success"
                });

                $scope.formData = {};
            }, function(error) {
                Swal.fire({
                    title: "Thất bại",
                    text: "Đặt lịch thất bại !",
                    icon: "error"
                });
                console.error('Lỗi:', error);
            });
    };
});
