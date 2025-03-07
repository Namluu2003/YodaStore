import { Button, Col, Input,Carousel, Radio, Row, Select, Table } from "antd";
import { Option } from "antd/es/mentions";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
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
  const [pageSize, setPageSize] = useState(5);

  const [searchCate, setSearchCate] = useState('');
  const [searchBrand, setSearchBrand] = useState('');

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
      params: { name: searchValue, page: currentPage, sizePage: pageSize, category: selectedCate, brand: selectedBrand, status: statusProduct },
    }).then((response) => {
      setProductList(response.data);
      setTotalPages(response.totalPages);
    }).catch((error) => {
      console.log(error);
    });
  }, [currentPage, selectedCate, selectedBrand, pageSize, searchValue, statusProduct]);

  const columns = [
    {
      title: 'STT',
      dataIndex: 'index',
      key: 'index',
    },
    {
          title: <i>Ảnh</i>,
          dataIndex: 'images',
          key: 'images',
          render: (images) => (
            <Carousel autoplay autoplaySpeed={3000} dots={false} arrows={false} style={{ width: "100px" }}>
              {images.split(',').map((image, index) => (
                <img src={image} alt="images" style={{ width: "100px", height: "100px" }} className="object-fit-contain" />
              ))}
            </Carousel>
          ),
        },
    {
      title: 'Tên',
      dataIndex: 'name',
      key: 'name',
    },
    
    {
      title: 'Danh mục',
      dataIndex: 'category',
      key: 'category',
    },
    {
      title: 'Thương hiệu',
      dataIndex: 'brand',
      key: 'brand',
    },
    {
      title: 'Đế giày',
      dataIndex: 'sole',
      key: 'sole',
    },
    {
      title: 'Số lượng',
      dataIndex: 'quantity',
      key: 'quantity',
      render: (x) => x == null ? 0 : x,
    },
    {
      title: 'Trạng thái',
      dataIndex: 'status',
      key: 'status',
      render: (x) => <span className={x ? 'text-primary fw-semibold' : 'text-success fw-semibold'}>{x ? 'Ngừng bán' : 'Đang bán'}</span>,
    },
    {
      title: 'Thao tác',
      dataIndex: 'id',
      key: 'action',
      render: (x) => (
        <>
          
          <Link to={`/admin/product/${x}`} className="btn btn-sm text-primary">
            <i className="fas fa-edit"></i>
          </Link>
        </>
      ),
    },
  ];

  return (
    <BaseUI>
      <h6 className="fw-semibold">Danh sách sản phẩm</h6>
      <Row gutter={10}>
        <Col span={20}>
          <label className="mb-1">Tên sản phẩm</label>
          <Input onChange={(event) => setSearchValue(event.target.value)} placeholder="Tìm kiếm sản phẩm theo tên..." />
        </Col>
        <Col span={4}>
          <div className="mb-1">‍</div>
          <Link to={"/admin/product/add"}>
            <Button type="primary" className="bg-primary w-100">
              <i className="fas fa-plus-circle me-1"></i>Thêm sản phẩm
            </Button>
          </Link>
        </Col>
        
      </Row>
      <Table dataSource={productList} columns={columns} className="mt-3"
        pagination={{
          // showSizeChanger: true,
          current: currentPage,
          pageSize: pageSize,
          // pageSizeOptions: [5, 10, 20, 50, 100],
          // showQuickJumper: true,
          total: totalPages * pageSize,
          onChange: (page, pageSize) => {
            setCurrentPage(page);
            setPageSize(pageSize);
          },
        }} />
    </BaseUI>
  );
}

export default Shoe;
