import BaseUI from './layouts/admin/BaseUI';

import Color from './pages/admin/product/attribute/Color';
import Size from './pages/admin/product/attribute/Size';

import AddStaff from './pages/admin/account/staff/AddStaff';
import Staff from './pages/admin/account/staff/Staff';
import StaffDetail from './pages/admin/account/staff/StaffDetail';

const publicRouters = [
    { path: "/", element: BaseUI },
    
    
    { path: "/admin/color", element: Color },
    { path: "/admin/size", element: Size },

    { path: "/admin/staff", element: Staff },
    { path: "/admin/staff/add", element: AddStaff },
    { path: "/admin/staff/:id", element: StaffDetail },
    
   
];

const privateRouters = [];

export { publicRouters, privateRouters };