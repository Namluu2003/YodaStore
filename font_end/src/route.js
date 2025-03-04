import BaseUI from "./layouts/admin/BaseUI";

// import Color from "./pages/admin/product/attribute/Color";
// import Size from "./pages/admin/product/attribute/Size";
import Voucher from "./pages/admin/voucher/Voucher";

const publicRouters = [
  { path: "/", element: BaseUI },

  // { path: "/admin/color", element: Color },
  // { path: "/admin/size", element: Size },
  { path: "/admin/voucher", element: Voucher },
];

const privateRouters = [];

export { publicRouters, privateRouters };
