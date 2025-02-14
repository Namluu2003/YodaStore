import { Button, Col, Empty, Input, Radio, Row, Select } from "antd";
import { Option } from "antd/es/mentions";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import Pagination from "~/components/Pagination";
import BaseUI from "~/layouts/admin/BaseUI";
import * as request from "~/utils/httpRequest";

function Shoe() {
  const [productList, setProductList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [listCate, setListCate] = useState([]);
  const [listBrand, setListBrand] = useState([]);

  const [selectedCate, setSelectedCate] = useState(null);
  const [selectedBrand, setSelectedBrand] = useState(null);
  const [searchValue, setSearchValue] = useState("");
  const [statusProduct, setStatusProduct] = useState(null);
  const [pageSize, setPageSize] = useState(3);

  const [searchCate, setSearchCate] = useState('');
  const [searchBrand, setSearchBrand] = useState('');

  const indexOfLastItem = currentPage * pageSize;
  const indexOfFirstItem = indexOfLastItem - pageSize;

  useEffect(() => {
    request.get("/category", { params: { name: searchCate, status: false } }).then((response) => {
      setListCate(response.data);
    }).catch((error) => { console.log(error); });
    request.get("/brand", { params: { name: searchBrand, status: false } }).then((response) => {
      setListBrand(response.data);
    }).catch((error) => { console.log(error); });
  }, [searchCate, searchBrand]);

  useEffect(() => {
    request.get("/shoe", {
      params: { name: searchValue, page: currentPage, sizePage: pageSize, category: selectedCate, brand: selectedBrand, deleted: statusProduct },
    }).then((response) => {
      setProductList(response.data);
      setTotalPages(response.totalPages);
    }).catch((error) => {
      console.log(error);
    });
  }, [currentPage, selectedCate, selectedBrand, pageSize, searchValue,statusProduct]);
  const handlePageChange = (pageNumber) => {
    if (pageNumber < 1) pageNumber = 1;
    setCurrentPage(pageNumber);
  };

  // return (
  //   <BaseUI>
  //     <h6 className="fw-semibold">Danh sách sản phẩm</h6>

     
  //     <Row gutter={10}>
  //       <Col span={16}>
  //         <label className="mb-1">Tên sản phẩm</label>
  //         <Input onChange={(event) => setSearchValue(event.target.value)} placeholder="Tìm kiếm sản phẩm theo tên..." />
  //       </Col>
  //       <Col span={4}>
  //         <div className="mb-1">Số bản ghi</div>
  //         <Select defaultValue={3} style={{ width: "100%" }} onChange={(value) => setPageSize(value)}
  //           options={[{ value: 3 }, { value: 5 }, { value: 10 }, { value: 15 }]}
  //         />
  //       </Col>
  //       <Col span={4}>
  //         <div className="mb-1">‍</div>
  //         <Link to={"/admin/product/add"}>
  //           <Button type="primary" className="bg-warning w-100">
  //             <i className="fas fa-plus-circle me-1"></i>Thêm sản phẩm
  //           </Button>
  //         </Link>
  //       </Col>
  //       <Col span={8}>
  //         <div className="mb-1">Trạng thái</div>
  //         <Radio.Group defaultValue={""} onChange={(event) => setStatusProduct(event.target.value)}>
  //           <Radio value={""}>Tất cả</Radio>
  //           <Radio value={false}>Đang bán</Radio>
  //           <Radio value={true}>Ngừng bán</Radio>
  //         </Radio.Group>
  //       </Col>
  //       <Col span={8}>
  //         <label className="mb-1">Danh mục</label>
  //         <Select showSearch onChange={setSelectedCate} placeholder="Chọn danh mục..." optionFilterProp="children" style={{ width: "100%" }} onSearch={setSearchCate}>
  //           <Option value="">Chọn danh mục</Option>
  //           {listCate.map((item) => (
  //             <Option key={item.id} value={item.id}>
  //               {item.name}
  //             </Option>
  //           ))}
  //         </Select>
  //       </Col>
  //       <Col span={8}>
  //         <label className="mb-1">Thương hiệu</label>
  //         <Select showSearch onChange={setSelectedBrand} placeholder="Chọn thương hiệu..." optionFilterProp="children" style={{ width: "100%" }} onSearch={setSearchBrand}>
  //           <Option value="">Chọn thương hiệu</Option>
  //           {listBrand.map((item) => (
  //             <Option key={item.id} value={item.id}>
  //               {item.name}
  //             </Option>
  //           ))}
  //         </Select>
  //       </Col>
  //     </Row>
  //     <div className="">
  //       <div className="table-responsive">
  //         <table className="table table-striped mt-3">
  //           <thead className="fw-semibold table-secondary">
  //             <tr>
  //               <td>#</td>
  //               <td>Tên</td>
  //               <td>Số lượng</td>
  //               <td>Danh mục</td>
  //               <td>Thương hiệu</td>
  //               <td>Trạng thái</td>
  //               <td>Thao tác</td>
  //             </tr>
  //           </thead>
  //           <tbody>
  //             {totalPages === 0 ? (
  //               <tr className="text-center fw-semibold">
  //                 <td colSpan={8}>
  //                   <Empty />
  //                 </td>
  //               </tr>
  //             ) : (
  //               productList.map((item, index) => (
  //                 <tr key={item.id}>
  //                   <td>{indexOfFirstItem + index + 1}</td>
  //                   <td>{item.name}</td>
  //                   <td>{item.quantity === null ? 0 : item.quantity}</td>
  //                   <td>{item.category.name}</td>
  //                   <td>{item.brand.name}</td>
  //                   <td>
  //                     <span
  //                       class={`fw-semibold ${item.deleted === true ? "text-danger" : "__bee-text"
  //                         }`}
  //                     >
  //                       {item.deleted === true ? "Ngùng bán" : "Đang bán"}
  //                     </span>
  //                   </td>
  //                   <td>
  //                     <button
  //                       type="button"
  //                       className="btn btn-sm"
  //                       data-bs-toggle="modal"
  //                       data-bs-target="#imagesModal"
  //                     >
  //                       <i className="fas fa-eye"></i>
  //                     </button>
  //                     <Link
  //                       to={`/admin/product/${item.id}`}
  //                       className="btn btn-sm text-warning"
  //                     >
  //                       <i className="fas fa-edit"></i>
  //                     </Link>
  //                   </td>
  //                 </tr>
  //               ))
  //             )}
  //           </tbody>
  //         </table>
  //       </div>
  //       <Pagination
  //         totalPages={totalPages}
  //         currentPage={currentPage}
  //         handleChange={handlePageChange}
  //       />
  //     </div>

  //   </BaseUI>
  // );
  return (
    <BaseUI>
      <h6 className="fw-semibold">Danh sách sản phẩm</h6>
      
      {/* Phần 1: Bộ lọc */}
      <div className="card p-3 mb-3">
        <h6 className="fw-semibold">Bộ lọc</h6>
        <Row gutter={10}>
          <Col span={12} md={8} lg={6}>
            <label className="mb-1">Tên sản phẩm</label>
            <Input onChange={(event) => setSearchValue(event.target.value)} placeholder="Tìm kiếm sản phẩm theo tên..." />
          </Col>
          {/* <Col span={12} md={8} lg={6}>
            <label className="mb-1">Số bản ghi</label>
            <Select defaultValue={3} style={{ width: "100%" }} onChange={(value) => setPageSize(value)}
              options={[{ value: 3 }, { value: 5 }, { value: 10 }, { value: 15 }]}
            />
          </Col> */}
          {/* <Col span={12} md={8} lg={6}>
            <label className="mb-1">Danh mục</label>
              <Select showSearch onChange={setSelectedCate} placeholder="Chọn danh mục..." optionFilterProp="children" style={{ width: "100%" }} onSearch={setSearchCate}>
              <Option value="">Chọn danh mục</Option>
              {listCate.map((item) => (
                <Option key={item.id} value={item.id}>{item.name}</Option>
              ))}
            </Select>
          </Col>
          <Col span={12} md={8} lg={6}>
            <label className="mb-1">Thương hiệu</label>
            <Select showSearch onChange={setSelectedBrand} placeholder="Chọn thương hiệu..." optionFilterProp="children" style={{ width: "100%" }} onSearch={setSearchBrand}>
            <Option value="">Chọn thương hiệu</Option>
              {listBrand.map((item) => (
                <Option key={item.id} value={item.id}>{item.name}</Option>
              ))}
            </Select>
          </Col>
          <Col span={12} md={8} lg={6}>
            <label className="mb-1">Trạng thái</label>
            <Radio.Group defaultValue={""} onChange={(event) => setStatusProduct(event.target.value)}>
              <Radio value={""}>Tất cả</Radio>
              <Radio value={false}>Đang bán</Radio>
              <Radio value={true}>Ngừng bán</Radio>
            </Radio.Group>
          </Col> */}
          
        </Row>
      </div>
      
      {/* Phần 2: Danh sách sản phẩm + Nút thêm sản phẩm */}
      <div className="card p-3">
        <div className="d-flex justify-content-between align-items-center mb-2">
          <h6 className="fw-semibold">Bảng sản phẩm</h6>
          <Link to={"/admin/product/add"}>
            <Button type="primary" className="bg-primary">
              <i className="fas fa-plus-circle me-1"></i>Thêm sản phẩm
            </Button>
          </Link>
        </div>
        <div className="table-responsive">
          <table className="table">
            <thead>
              <tr>
                    <td className="bg-primary text-white py-3 ">#</td>
                    <td className="bg-primary text-white py-3 ">Tên sản phẩm</td>
                    <td className="bg-primary text-white py-3 ">Số lượng</td>
                    <td className="bg-primary text-white py-3 ">Danh mục</td>
                    <td className="bg-primary text-white py-3 ">Thương hiệu</td>
                    <td className="bg-primary text-white py-3 ">Trạng thái</td>
                    <td className="bg-primary text-white py-3 ">Thao tác</td>
              </tr>

            </thead>
            <tbody>
              {totalPages === 0 ? (
                <tr className="text-center fw-semibold">
                  <td colSpan={7}><Empty /></td>
                </tr>
              ) : (
                productList.map((item, index) => (
                  <tr key={item.id}>
                    <td>{indexOfFirstItem + index + 1}</td>
                    <td>{item.name}</td>
                    <td>{item.quantity ?? 0}</td>
                    <td>{item.danhMuc.name}</td>
                    <td>{item.thuongHieu.name}</td>
                    <td>
                      <span className={`fw-semibold ${item.deleted ? "text-danger" : "text-success"}`}>
                        {item.deleted ? "Ngừng bán" : "Đang bán"}
                      </span>
                    </td>
                    <td>
                      <button type="button" className="btn btn-sm" data-bs-toggle="modal" data-bs-target="#imagesModal">
                        {/* <i className="fas fa-eye"></i> */}
                      </button>
                      <Link to={`/admin/product/${item.id}`} className="btn btn-sm text-warning">
                        <i className="fas fa-edit"></i>
                      </Link>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
        <Pagination totalPages={totalPages} currentPage={currentPage} handleChange={handlePageChange} />
      </div>
    </BaseUI>
  );
  
}

export default Shoe;
