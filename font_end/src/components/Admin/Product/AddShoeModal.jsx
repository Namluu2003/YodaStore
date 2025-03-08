


import React, { useEffect, useState } from "react";
import * as request from "~/utils/httpRequest";
import { toast } from "react-toastify";
import { Button, Form, Input, Modal, Select } from "antd";
import { FaPlusCircle } from "react-icons/fa";
import { Option } from "antd/es/mentions";
import ImageGallery from "./ImageGallery";
import "./AddShoeModal.css";
import { ExclamationCircleFilled } from "@ant-design/icons";

function AddShoeModal({ onAddSuccess }) {
  const { confirm } = Modal;
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form] = Form.useForm();
  const [searchCate, setSearchCate] = useState(null);
  const [cateList, setCateList] = useState([]);
  const [searchBrand, setSearchBrand] = useState(null);
  const [brandList, setBrandList] = useState([]);
  const [searchSole, setSearchSole] = useState(null);
  const [soleList, setSoleList] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);
  const [isGalleryModalOpen, setIsGalleryModalOpen] = useState(false); // Điều khiển modal ImageGallery

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = (data) => {
    confirm({
      title: "Xác nhận",
      icon: <ExclamationCircleFilled />,
      content: "Bạn có chắc muốn thêm sản phẩm giày mới?",
      okText: "Xác nhận",
      okType: "danger",
      cancelText: "Hủy",
      onOk() {
        if (selectedImages.length === 0) {
          toast.error("Hình ảnh không được để trống!");
          return;
        }

        const formData = new FormData();
        formData.append("name", data.name);
        formData.append("brand", data.brand);
        formData.append("sole", data.sole);
        formData.append("category", data.category);

        selectedImages.forEach((url) => {
          formData.append("listImages", url);
        });

        request
          .post("/shoe", formData, {
            headers: { "Content-Type": "multipart/form-data" },
          })
          .then((response) => {
            toast.success("Thêm thành công!");
            onAddSuccess();
            form.resetFields();
            setSelectedImages([]);
            setIsModalOpen(false);
          })
          .catch((e) => {
            console.log(e);
            toast.error(e.response?.data || "Có lỗi xảy ra!");
          });
      },
      onCancel() {
        console.log("Hủy thêm sản phẩm");
      },
    });
  };

  const handleCancel = () => {
    setIsModalOpen(false);
    form.resetFields();
  };

  const loadCate = () => {
    request
      .get("/category", { params: { name: searchCate } })
      .then((response) => {
        setCateList(response.data);
      });
  };

  const loadBrand = () => {
    request
      .get("/brand", { params: { name: searchBrand } })
      .then((response) => {
        setBrandList(response.data);
      });
  };

  const loadSole = () => {
    request.get("/sole", { params: { name: searchSole } }).then((response) => {
      setSoleList(response.data);
    });
  };

  useEffect(() => {
    loadCate();
  }, [searchCate]);

  useEffect(() => {
    loadBrand();
  }, [searchBrand]);

  useEffect(() => {
    loadSole();
  }, [searchSole]);

  const handleImageSelect = (imageUrl, isRemoving = false) => {
    if (isRemoving) {
      setSelectedImages((prev) => prev.filter((url) => url !== imageUrl));
    } else {
      if (selectedImages.length >= 3) {
        toast.error("Tổng số ảnh không được vượt quá 3!");
        return;
      }
      setSelectedImages((prev) => [...prev, imageUrl]);
    }
  };

  // Xử lý mở modal ImageGallery
  const handleShowGallery = () => {
    setIsGalleryModalOpen(true);
  };

  // Xử lý đóng modal ImageGallery
  const handleCloseGalleryModal = () => {
    setIsGalleryModalOpen(false);
  };

  return (
    <>
      <Button
        type="primary"
        onClick={showModal}
        className="bg-blue"
        size="large"
      >
        <FaPlusCircle />
      </Button>
      <Modal
        title="Thêm giày"
        open={isModalOpen}
        onCancel={handleCancel}
        footer=""
        width={800}
      >
        <Form onFinish={handleOk} layout="vertical" form={form}>
          <Form.Item
            label="Tên giày"
            name="name"
            rules={[{ required: true, message: "Tên không được để trống!" }]}
          >
            <Input placeholder="Nhập tên giày..." />
          </Form.Item>

          <Form.Item
            label="Thương hiệu"
            name="brand"
            rules={[
              { required: true, message: "Thương hiệu không được để trống!" },
            ]}
          >
            <Select
              showSearch
              optionFilterProp="children"
              style={{ width: "100%" }}
              onSearch={setSearchBrand}
              placeholder="Chọn thương hiệu..."
            >
              {brandList.map((item) => (
                <Option key={item.id} value={item.id}>
                  {item.name}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            label="Đế"
            name="sole"
            rules={[{ required: true, message: "Đế không được để trống!" }]}
          >
            <Select
              showSearch
              optionFilterProp="children"
              style={{ width: "100%" }}
              onSearch={setSearchSole}
              placeholder="Chọn đế..."
            >
              {soleList.map((item) => (
                <Option key={item.id} value={item.id}>
                  {item.name}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            label="Danh mục"
            name="category"
            rules={[
              { required: true, message: "Danh mục không được để trống!" },
            ]}
          >
            <Select
              showSearch
              optionFilterProp="children"
              style={{ width: "100%" }}
              onSearch={setSearchCate}
              placeholder="Chọn danh mục..."
            >
              {cateList.map((item) => (
                <Option key={item.id} value={item.id}>
                  {item.name}
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item label="Hình ảnh" name="listImages">
            <Button type="default" onClick={handleShowGallery}>
              <FaPlusCircle /> Chọn từ Cloudinary
            </Button>
            {selectedImages.length > 0 && (
              <div className="mt-2">
                <p>Danh sách ảnh đã chọn</p>
                <div className="d-flex flex-wrap gap-2">
                  {selectedImages.map((url, index) => (
                    <div
                      key={index}
                      style={{
                        position: "relative",
                        border: "2px dashed #fa8c16",
                        padding: "5px",
                      }}
                    >
                      <img
                        src={url}
                        alt={`selected-${index}`}
                        style={{
                          width: "100px",
                          height: "100px",
                          objectFit: "cover",
                        }}
                      />
                      
                    </div>
                  ))}
                </div>
              </div>
            )}
          </Form.Item>

          <div className="d-flex justify-content-end">
            <Button type="primary" htmlType="submit">
              <i className="fas fa-plus-circle me-1"></i> Thêm
            </Button>
          </div>
        </Form>
      </Modal>

      {/* Modal cho ImageGallery */}
      <Modal
        title="Danh sách ảnh"
        open={isGalleryModalOpen}
        onCancel={handleCloseGalleryModal}
        footer={[
          <Button
            key="close"
            type="primary"
            danger
            onClick={handleCloseGalleryModal}
          >
            OK
          </Button>,
        ]}
        width={800}
      >
        <div style={{ maxHeight: "400px", overflowY: "auto" }}>
        {selectedImages.length > 0 && (
              <div className="mt-2">
                <p>Danh sách ảnh đã chọn</p>
                <div className="d-flex flex-wrap gap-2">
                  {selectedImages.map((url, index) => (
                    <div
                      key={index}
                      style={{
                        position: "relative",
                        border: "2px dashed #fa8c16",
                        padding: "5px",
                      }}
                    >
                      <img
                        src={url}
                        alt={`selected-${index}`}
                        style={{
                          width: "100px",
                          height: "100px",
                          objectFit: "cover",
                        }}
                      />
                      <button
                        onClick={() => handleImageSelect(url, true)}
                        style={{
                          position: "absolute",
                          top: "0",
                          right: "0",
                          background: "red",
                          color: "white",
                          border: "none",
                          borderRadius: "50%",
                          width: "20px",
                          height: "20px",
                          cursor: "pointer",
                        }}
                      >
                        X
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            )}
          <ImageGallery
          
            onImageSelect={handleImageSelect}
            selectedImages={selectedImages}
          />
        </div>
      </Modal>
    </>
  );
}

export default AddShoeModal;