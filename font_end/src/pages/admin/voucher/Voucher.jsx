import {
    Breadcrumb,
    Button,
    Col,
    Divider,
    Input,
    Modal,
    Radio,
    Row,
    Select,
    Space,
    Table,
    message,
  } from "antd";
  import React, { useEffect, useState } from "react";
  import { Link } from "react-router-dom";
  // import Pagination from "~/components/Pagination";
  import BaseUI from "~/layouts/admin/BaseUI";
  import * as request from "~/utils/httpRequest";
  // import FormatDate from "../../../utils/FormatDate";
  // import { Empty } from "antd";
  import { FaHome, FaTrash } from "react-icons/fa";
  
  import FormatCurrency from "~/utils/FormatCurrency";
  import VoucherSatus from "./VoucherStatus";
  import "./Voucher.css";

  function Voucher() {
    const [voucherList, setVoucherList] = useState([]);
    const [currentPageState, setCurrentPageState] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
  
    const [searchValue, setSearchValue] = useState("");
    const [statusValue, setStatusValue] = useState("");
    const [pageSize, setPageSize] = useState(5);
  
    const indexOfLastItem = currentPage * pageSize;
    const indexOfFirstItem = indexOfLastItem - pageSize;
    const [reloadInterval, setReloadInterval] = useState(null);
    const [selectedOption, setSelectedOption] = useState("voucher");
  
    const handleOptionChange = (value) => {
      setSelectedOption(value);
    };
  
    useEffect(() => {
      loadVoucher();
      // Khởi tạo interval khi component được tạo
      const intervalId = setInterval(() => {
        loadVoucher();
        console.log("test");
      }, 1000);
  
      // Lưu intervalId vào state để sau này có thể xóa interval
      setReloadInterval(intervalId);
  
      // Hủy interval khi component unmount
      return () => {
        clearInterval(intervalId);
      };
    }, [searchValue, pageSize, currentPage, statusValue]);
  
  
  
    const loadVoucher = async () => {
  
      try {
        const response = await request.get("/voucher", {
          params: {
            name: searchValue,
            page: currentPage,
            sizePage: pageSize,
            status: statusValue,
          },
        });
        console.log('Dữ liệu voucher:', response.data); // Kiểm tra định dạng startDate và endDate
  
        setVoucherList(response.data);
        setTotalPages(response.totalPages);
      } catch (e) {
        console.log(e);
      }
    };
  
    const handlePageChange = (pageNumber) => {
      if (pageNumber < 1) pageNumber = 1;
      setCurrentPage(pageNumber);
    };
    const columns = [
      {
        title: 'STT',
        dataIndex: 'index',
        key: 'index',
        render: (text, record, index) => indexOfFirstItem + index + 1,
      },
      {
        title: 'Mã voucher',
        dataIndex: 'code',
        key: 'code',
      },
      {
        title: 'Tên voucher',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: 'Đơn hàng tối thiểu',
        dataIndex: 'minBillValue',
        key: 'minBillValue',
        render: (x) => <FormatCurrency value={x} />
      },
      {
        title: 'Đơn hàng tối đa',
        dataIndex: 'maxBillValue',
        key: 'maxBillValue',
        render: (x) => <FormatCurrency value={x} />
      },
      {
        title: 'Giảm',
        dataIndex: 'percentReduce',
        key: 'percentReduce',
        render: (x) => `${x}%`
      },
      {
        title: 'Số lượng',
        dataIndex: 'quantity',
        key: 'quantity',
      },
      
      {
        title: 'Ngày bắt đầu',
        dataIndex: 'startDate',
        key: 'startDate',
        render: (date) => {
            const dateObj = new Date(date);
            const formattedDate = dateObj.toLocaleDateString('vi-VN', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric'
            });
    
            let formattedTime = dateObj.toLocaleTimeString('vi-VN', {
                hour: '2-digit',
                minute: '2-digit',
                hour12: true
            });
    
            formattedTime = formattedTime.replace('SA', 'AM').replace('CH', 'PM');
    
            return (
                <div className="date-container">
                   
                    <span className="start-date">
                        {formattedDate} {formattedTime}
                    </span>
                </div>
            );
        }
    },
    {
        title: 'Ngày kết thúc',
        dataIndex: 'endDate',
        key: 'endDate',
        render: (date) => {
            const dateObj = new Date(date);
            const formattedDate = dateObj.toLocaleDateString('vi-VN', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric'
            });
    
            let formattedTime = dateObj.toLocaleTimeString('vi-VN', {
                hour: '2-digit',
                minute: '2-digit',
                hour12: true
            });
    
            formattedTime = formattedTime.replace('SA', 'AM').replace('CH', 'PM');
    
            return (
                <div className="date-container">
                    
                    <span className="end-date">
                        {formattedDate} {formattedTime}
                    </span>
                </div>
            );
        }
    },
    
    
  
      {
        title: 'Trạng thái',
        dataIndex: 'status',
        key: 'status',
        render: (x) => <VoucherSatus status={x} />
      },
  
      {
        title: 'Thao tác',
        dataIndex: 'id',
        key: 'action',
        render: (x) => (
          <Link to={`/admin/voucher/${x}`} className="btn btn-sm text-primary">
            <i className="fas fa-edit"></i>
          </Link>
        )
      },
    ];
  
    return (
      <BaseUI>
        <div className="">
          <Breadcrumb
            className="mb-2"
            items={[
              { href: "/", title: <FaHome /> },
              { title: "Danh sách Voucher" },
            ]}
          />
          <Row gutter={12}>
            <Col span={6}>
              <label className="mb-1">Nhập mã, tên Voucher </label>
              <Input
                onChange={(event) => setSearchValue(event.target.value)}
                placeholder="Tìm kiếm voucher theo tên, mã ..."
              //
              />
            </Col>
            {/* <Col span={14}>
              <div className="mb-1">Trạng thái</div>
              <label className="mb-1">ㅤ</label>
              <Radio.Group
                defaultValue={""}
                onChange={(event) => {
                  setStatusValue(event.target.value);
                  setCurrentPage(1);
                }}
              >
                <Radio value={""}>Tất cả</Radio>
                <Radio value={0}>Sắp diễn ra</Radio>
                <Radio value={1}>Đang diễn ra</Radio>
                <Radio value={2}>Đã kết thúc</Radio>
              </Radio.Group>
            </Col> */}
            {/* <Col span={2}>
              <div className="mb-1">Số bản ghi</div>
              <Select
                defaultValue={5}
                onChange={(value) => setPageSize(value)}
                options={[{ value: 5 }, { value: 10 }, { value: 15 }]}
              />
            </Col> */}
  
            <Col span={4}>
              <div className="mb-1">‍</div>
              <Link to={"/admin/voucher/add"}>
                <Button
                  type="primary"
                  className="bg-primary"
                  style={{ textAlign: "center" }}
                >
                  <i className="fas fa-plus-circle me-1"></i>Thêm Voucher
                </Button>
              </Link>
            </Col>
          </Row>
          <Table dataSource={voucherList} columns={columns} className="mt-3"
            pagination={{
              // showSizeChanger: true,
              // current: currentPage,
              pageSize: pageSize,
              // pageSizeOptions: [5, 10, 20, 50, 100],
              showQuickJumper: true,
              total: totalPages * pageSize,
              onChange: (page, pageSize) => {
                setCurrentPage(page);
                setPageSize(pageSize);
              },
            }} />
        </div>
      </BaseUI>
    );
  }
  
  export default Voucher;
  