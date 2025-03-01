import { Button, Col, Input, Radio, Row, Table, } from "antd";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import BaseUI from "~/layouts/admin/BaseUI";
import FormatDate from "~/utils/FormatDate";
import * as request from "~/utils/httpRequest";

function Staff() {
  const [staffList, setStaffList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);

  const [searchValue, setSearchValue] = useState("");
  const [staffStatus, setStaffStatus] = useState(null);
  const [pageSize, setPageSize] = useState(5);
  useEffect(() => {
    request.get("/staff", {
      params: {
        name: searchValue,
        page: currentPage,
        sizePage: pageSize,
        status: staffStatus,
      },
    }).then(response => {
      setStaffList(response.data);
      setTotalPages(response.totalPages);
    }).catch(e => {
      console.log(e);
    })
  }, [searchValue, pageSize, staffStatus, currentPage]);

// const handleUpdateStatus = (staff) => {
  //   Modal.confirm({
  //     title: "Xác nhận",
  //     maskClosable: true,
  //     content: (
  //       <div>
  //         <p>{`Cập nhật trạng thái ${staff.name} thành ${staff.deleted === false ? "Đã nghỉ" : "Đang làm"
  //           } ?`}</p>
  //         {staff.deleted === false ? (
  //           <Input
  //             placeholder="Nhập lý do nghỉ việc"
  //             onChange={(e) => console.log(e.target.value)}
  //           />
  //         ) : (
  //           ""
  //         )}
  //       </div>
  //     ),
  //     okText: "Ok",
  //     cancelText: "Cancel",
  //     onOk: () => {
  //       request
  //         .put(`/staff/${staff.id}`, {
  //           ...staff,
  //           deleted:
  //             staff.deleted === true
  //               ? (staff.deleted = false)
  //               : (staff.deleted = true),
  //         })
  //         .then((response) => {
  //           if (response.status === 200) {
  //             toast.success("Cập nhật thành công!");
  //             loadData();
  //           }
  //         })
  //         .catch((e) => {
  //           console.log(e);
  //         });
  //     },
  //   });
  // };

  const columns = [
    {
      title: '#',
      dataIndex: 'index',
      key: 'index',
      className: "text-center",
    },
    {
      title: 'Tên',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
    },
    {
      title: 'SĐT',
      dataIndex: 'phoneNumber',
      key: 'phoneNumber',
    },
    {
      title: 'Ngày tham gia',
      dataIndex: 'createAt',
      key: 'createAt',
      render: (x) => <FormatDate date={x} />
    },
    {
      title: 'Trạng thái',
      dataIndex: 'deleted',
      key: 'deleted',
      render: (x) => (
        <span className={x ? "fw-semibold text-danger" : "fw-semibold text-success"}>
          {x ? "Đã nghỉ" : "Đang làm"}
        </span>
      )
    },
    {
      title: 'Thao tác',
      dataIndex: 'id',
      key: 'action',
      render: (x) => (
        <Link to={`/admin/staff/${x}`} className="btn btn-sm text-primary">
          <i className="fas fa-edit"></i>
        </Link>
      )
    },
  ];


}

export default Staff;
