const sidebarData = [
    {
        "key": "thongKe",
        "title": "Thống kê",
        "icon": "",
        "path": "/admin/product-list"
    },
    {
        "key": "tongQuan",
        "title": "Bán hàng tại quầy",
        "icon": "",
        "path": "/admin/product"
    },
    {
        "key": "qlDonHang",
        "title": "Quản lý hoá đơn",
        "icon": "",
        "path": "/admin/bill"
    },

    
    {
        "key": "qlsanpham",
        "title": "Quản lý sản phẩm",
        "icon": "",
        "path": "/services",
        "children": [
            {
                "key": "sanPham",
                "title": "Sản phẩm",
                "icon": "",
                "path": "/admin/product"
            },
            {
                "key": "kichCo",
                "title": "Kích cỡ",
                "icon": "",
                "path": "/admin/size"
            },
            {
                "key": "mauSac",
                "title": "Màu sắc",
                "icon": "",
                "path": "/admin/color"
            },
            {
                "key": "deGiay",
                "title": "Đế giày",
                "icon": "",
                "path": "/admin/sole"
            },
            {
                "key": "thuongHieu",
                "title": "Thương hiệu",
                "icon": "",
                "path": "/admin/brand"
            },
            {
                "key": "danhMuc",
                "title": "Danh Mục",
                "icon": "",
                "path": "/admin/category"
            },
            
        ]
    },
    {
        "key": "qlTaiKhoan",
        "title": "Quản lý tài khoản",
        "icon": "fa-user",
        "children": [
            {
                "key": "qlNhanVien",
                "title": "Quản lý nhân viên",
                "icon": "",
                "path": "/admin/staff"
            },
            {
                "key": "qlKhachHang",
                "title": "Quản lý khách hàng",
                "icon": "",
                "path": "/admin/customer"
            }
        ]
    },
    {
        "key": "qlVoucher",
        "title": "Quản lý Voucher",
        "icon": "",
        "path": "/admin/voucher"
     },
    
]

export default sidebarData;