import { Breadcrumb, Button, Col, Divider, Form, Input, Modal, Radio, Row } from "antd";
import React, { useState } from "react";
import { FaHome, FaTrash } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import GHNInfo from "~/components/GhnInfo";
import Loading from "~/components/Loading/Loading";

import BaseUI from "~/layouts/admin/BaseUI";
import * as request from "~/utils/httpRequest";

function AddStaff() {
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [dataAddress, setDataAddress] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const [avatar, setAvatar] = useState(null);
  const handleImageSelect = (event) => {
    try {
      const file = event.target.files[0];
      const imageUrl = URL.createObjectURL(file);
      setAvatar(file);
      setPreviewUrl(imageUrl);
    } catch (e) {
      setPreviewUrl("");
    }
  };

  
  const handleQrSuccess = (value) => {
    const withoutName = value.substring(14, value.length);
    const splits = withoutName.split("|");
    const birthday = splits[1];
    if (value.substring(0, 12).length === 12) {
      toast.success(`Đã tìm thấy ${splits[0].toString()}!`);
      form.setFieldsValue({
        gender: splits[2],
        cccd: value.substring(0, 12),
        name: splits[0],
        birthday: `${birthday.substring(4)}-${birthday.substring(2, 4)}-${birthday.substring(0, 2)}`,
        specificAddress: splits[3],
      });
    }
  };

  const handleAddStaff = (data) => {
    if (dataAddress == null) {
      toast.error("Vui lòng chọn địa chỉ!");
    } else {
      const formData = new FormData();
      formData.append("avatar", avatar);
      formData.append("address.name", data.name);
      formData.append("address.phoneNumber", data.phoneNumber);
      formData.append("address.specificAddress", data.specificAddress);
      formData.append("address.ward", dataAddress.ward);
      formData.append("address.district", dataAddress.district);
      formData.append("address.province", dataAddress.province);
      formData.append("address.defaultAddress", true);

      formData.append("cccd", data.cccd);
      formData.append("username", data.username);
      formData.append("name", data.name);
      formData.append("gender", data.gender);
      formData.append("birthday", data.birthday);
      formData.append("email", data.email);
      formData.append("phoneNumber", data.phoneNumber);
      Modal.confirm({
        title: "Xác nhận",
        maskClosable: true,
        content: "Xác nhận thêm nhân viên ?",
        okText: "Ok",
        cancelText: "Cancel",
        onOk: () => {
          request
          .post("/staff", formData, { headers: { "Content-Type": "multipart/form-data", }, })
          .then((response) => {
            console.log(response);
            setLoading(true);
            if (response.data.success) {
              setLoading(false);
              toast.success("Thêm thành công!");
              navigate("/admin/staff");
            }
          }).catch((e) => {
            console.log(e);
            toast.error(e.response.data);
          });
        },
      });
    }
  };

  if (loading) {
    return (
      <BaseUI>
        <Loading />
      </BaseUI>
    );
  }
 

  
}

export default AddStaff;
