
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

  
 
}

export default AddShoeModal;