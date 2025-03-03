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
    return (
      <BaseUI>
        <h6 className="fw-semibold">Danh sách màu sắc</h6>
  
        <div className="card p-3 mb-3">
          <h6 className="fw-semibold">Bộ lọc</h6>
          <Row gutter={10}>
            <Col span={15}>
              <label className="mb-1">Màu sắc</label>
              <Input
                onChange={(event) => setSearchValue(event.target.value)}
                placeholder="Tìm kiếm màu sắc theo tên..."
              />
            </Col>
            {/* <Col span={8}>
              <div className="mb-1">Trạng thái</div>
              <Radio.Group
                defaultValue={null}
                onChange={(event) => setStatusColor(event.target.value)}
              >
                <Radio value={null}>Tất cả</Radio>
                <Radio value={false}>Hoạt động</Radio>
                <Radio value={true}>Ngừng hoạt động</Radio>
              </Radio.Group>
            </Col> */}
          </Row>
        </div>
        <div className="card p-3">
          <div className="d-flex justify-content-between align-items-center mb-2">
            <h6 className="fw-semibold">Bảng sản phẩm</h6>
            <Link to={"/admin/color"}>
              <Button
                type="primary"
                onClick={showModalAdd}
                className="bg-primary w-100"
              >
                <i className="fas fa-plus-circle me-1"></i>Thêm màu sắc
              </Button>
            </Link>
          </div>
  
          <Table
            dataSource={colorList}
            columns={columns}
            className="mt-3"
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
            }}
            bordered
          />
  
          <Modal
            title="Thêm màu sắc"
            open={isModalAddOpen}
            onCancel={handleCancelAdd}
            footer=""
          >
            <Form onFinish={handleAdd} layout="vertical" form={formAdd}>
              <Form.Item
                label={"Màu sắc"}
                name={"name"}
                rules={[
                  { required: true, message: "Màu sắc không được để trống!" },
                ]}
              >
                <Input placeholder="Nhập tên màu..." />
              </Form.Item>
  
              <div className="d-flex justify-content-end">
                <Button type="primary" htmlType="submit">
                  <i className="fas fa-plus-circle me-1"></i> Thêm
                </Button>
              </div>
            </Form>
          </Modal>
  
          <Modal
            title="Chỉnh sửa màu sắc"
            open={isModalUpdateOpen}
            onCancel={handleCancelUpdate}
            footer=""
          >
            <Form onFinish={handleUpdate} layout="vertical" form={formUpdate}>
              <Form.Item
                label={"Màu sắc"}
                name={"name"}
                rules={[
                  { required: true, message: "Màu sắc không được để trống!" },
                ]}
              >
                <Input placeholder="Nhập tên màu..." />
              </Form.Item>
              <Form.Item label={"Trạng thái"} name={"status"}>
                <Switch
                  className={item.status ? "bg-success" : "bg-danger"} // True => Xanh, False => Đỏ
                  checkedChildren={<i className="fa-solid fa-check"></i>}
                  unCheckedChildren={<i className="fa-solid fa-xmark"></i>}
                  checked={item.status} // Sử dụng trạng thái của item
                  onChange={(checked) => {
                    setItem({ ...item, status: checked }); // Cập nhật trạng thái trong item
                  }}
                />
              </Form.Item>
  
              <div className="d-flex justify-content-end">
                <Button type="primary" htmlType="submit">
                  <i className="fas fa-plus-circle me-1"></i> Cập nhật
                </Button>
              </div>
            </Form>
          </Modal>
        </div>
      </BaseUI>
    );
  }
  
  export default Color;
  