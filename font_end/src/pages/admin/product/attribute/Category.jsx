import {
    Button,
    Col,
    Form,
    Input,
    Modal,
    Radio,
    Row,
    Switch,
    Table,
  } from "antd";
  import React from "react";
  import { useState } from "react";
  import { useEffect } from "react";
  import { Link } from "react-router-dom";
  import BaseUI from "~/layouts/admin/BaseUI";
  import * as request from "~/utils/httpRequest";
  import { ExclamationCircleFilled } from "@ant-design/icons";
  import { WarningTwoTone } from "@ant-design/icons";
  import { toast } from "react-toastify";
  import moment from "moment";
  
  import "./Color.css";
  function Color() {
    const [colorList, setColorList] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [searchValue, setSearchValue] = useState("");
    const [statusColor, setStatusColor] = useState(null);
    const [pageSize, setPageSize] = useState(5);
    const [isModalAddOpen, setIsModalAddOpen] = useState(false);
    const [isModalUpdateOpen, setIsModalUpdateOpen] = useState(false);
    const { confirm } = Modal;
    const [formAdd] = Form.useForm();
    const [formUpdate] = Form.useForm();
    const [item, setItem] = useState("");
  
    const showModalAdd = () => {
      setIsModalAddOpen(true);
    };
    const handleCancelAdd = () => {
      setIsModalAddOpen(false);
    };
  
    const showModalUpdate = () => {
      setIsModalUpdateOpen(true);
    };
    const handleCancelUpdate = () => {
      setIsModalUpdateOpen(false);
    };
  
    useEffect(() => {
      if (isModalUpdateOpen === true) {
        formUpdate.setFieldsValue({
          name: item.name,
        });
      }
    }, [isModalUpdateOpen, formUpdate]);
  
    useEffect(() => {
      loadData(currentPage, pageSize, searchValue, statusColor);
    }, [currentPage, pageSize, searchValue, statusColor]);
  
    const loadData = (currentPage, pageSize, searchValue, statusSize) => {
      request
        .get("/color", {
          params: {
            name: searchValue,
            page: currentPage,
            sizePage: pageSize,
            status: statusSize,
          },
        })
        .then((response) => {
          setColorList(response.data);
          setTotalPages(response.totalPages);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    
    const columns = [
      {
        title: "STT",
        dataIndex: "index",
        key: "index",
        className: "text-center custom-index",
      },
      {
        title: "Màu sắc",
        dataIndex: "name",
        key: "name",
        className: "text-center custom-name",
      },
      {
        title: "Ngày tạo",
        dataIndex: "createAt",
        key: "createAt",
        className: "text-center custom-date",
        render: (text) => moment(text).format("DD-MM-YYYY"),
      },
      {
            title: "Hoạt động",
            dataIndex: "status",
            key: "status",
            className: "text-center",
            render: (x, item) => (
              <Switch
                className={x ? "bg-success" : "bg-danger"}
                checkedChildren={<i class="fa-solid fa-check"></i>}
                unCheckedChildren={<i class="fa-solid fa-xmark"></i>}
                checked={!x}
                onChange={() => showDeleteConfirm(item)}
              />
            ),
          },
      {
        title: "Thao tác",
        dataIndex: "id",
        key: "action",
        className: "text-center custom-action",
        render: (x, item) => (
          <>
            <Link to={"/admin/color"}>
              <button
                type="primary"
                onClick={() => {
                  setItem(item);
                  showModalUpdate(x);
                }}
                className="btn btn-sm text-primary"
              >
                <i className="fas fa-edit"></i>
              </button>
            </Link>
          </>
        ),
      },
    ];
    const showDeleteConfirm = (item) => {
      confirm({
        title: "Xác nhận ",
        icon: <ExclamationCircleFilled />,
        content: "Bạn có chắc muốn sửa trạng thái hoạt động? ",
        okText: "OK",
        okType: "danger",
        cancelText: "Đóng",
        onOk() {
          request
            .remove(`/color/${item.id}`)
            .then((response) => {
              if (response.status === 200) {
                loadData(currentPage, pageSize, searchValue, statusColor);
                toast.success("Thành công!");
              }
            })
            .catch((e) => {
              console.log(e);
            });
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    };
    const handleAdd = (data) => {
      confirm({
        title: "Xác nhận ",
        icon: (
          <WarningTwoTone twoToneColor="#fa541c" style={{ fontSize: "26px" }} />
        ),
        content: "Bạn có chắc muốn thêm màu mới? ",
        okText: "OK",
        okType: "danger",
        cancelText: "Đóng",
        onOk() {
          request
            .post("/color", { ...data, status: true })
            .then((response) => {
              if (response.status === 200) {
                console.log(response);
                toast.success("Thêm mới thành công!");
                setIsModalAddOpen(false);
                loadData();
                formAdd.resetFields();
              }
            })
            .catch((e) => {
              console.log(e);
              if (e.response.status === 500) {
                toast.error(e.response.data);
              }
              toast.error(e.response.data.message);
            });
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    };
    const handleUpdate = (data) => {
      confirm({
        title: "Xác nhận ",
        icon: <ExclamationCircleFilled />,
        content: "Bạn có chắc muốn cập nhật màu sắc? ",
        okText: "OK",
        okType: "danger",
        cancelText: "Đóng",
        onOk() {
          request
            .put(`/color/${item.id}`, { ...data, status: item.status }) // Cập nhật trạng thái
            .then((response) => {
              if (response.status === 200) {
                toast.success("Cập nhật thành công!");
                setIsModalUpdateOpen(false);
                loadData(currentPage, pageSize, searchValue, statusColor);
                formUpdate.resetFields();
              }
            })
            .catch((e) => {
              console.log(e);
              if (e.response.status === 500) {
                toast.error(e.response.data);
              }
              toast.error(e.response.data.message);
            });
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    };
  }
  
  export default Color;
  