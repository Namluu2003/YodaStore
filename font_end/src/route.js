import BaseUI from "./layouts/admin/BaseUI";
import Shoe from './pages/admin/product/shoe/Shoe';
import Color from "./pages/admin/product/attribute/Color";
import Size from "./pages/admin/product/attribute/Size";
import Brand from './pages/admin/product/attribute/Brand';
import Category from './pages/admin/product/attribute/Category';
import Sole from './pages/admin/product/attribute/Sole';
import ShoeInfo from './pages/admin/product/shoe/ShoeInfo';
import AddProduct from './pages/admin/product/shoe-detail/AddShoe';
import Voucher from "./pages/admin/voucher/Voucher";
import AddVoucher from "./pages/admin/voucher/AddVoucher";

import AddStaff from './pages/admin/account/staff/AddStaff';
import Staff from './pages/admin/account/staff/Staff';
import StaffDetail from './pages/admin/account/staff/StaffDetail';

import Customer from './pages/admin/account/customer/Customer';
import AddCustomer from './pages/admin/account/customer/AddCustomer';
import CustomerDetail from './pages/admin/account/customer/CustomerDetail';

const publicRouters = [
    { path: "/", element: BaseUI },
    { path: "/", element: BaseUI },
    
    { path: "/admin/product", element: Shoe },
    
    { path: "/admin/color", element: Color },
    { path: "/admin/size", element: Size },
    { path: "/admin/brand", element: Brand },
    { path: "/admin/category", element: Category },
    { path: "/admin/product/add", element: AddProduct },
    { path: "/admin/sole", element: Sole },
    { path: "/admin/product/:id", element: ShoeInfo },


    { path: "/admin/staff", element: Staff },
    { path: "/admin/staff/add", element: AddStaff },
    { path: "/admin/staff/:id", element: StaffDetail },

    { path: "/admin/customer", element: Customer },
    { path: "/admin/customer/add", element: AddCustomer },
    { path: "/admin/customer/:id", element: CustomerDetail },

    { path: "/admin/voucher", element: Voucher },
    { path: "/admin/voucher/add", element: AddVoucher },
    { path: "/admin/voucher/:id", element: VoucherDetail },
];

const privateRouters = [];

export { publicRouters, privateRouters };
