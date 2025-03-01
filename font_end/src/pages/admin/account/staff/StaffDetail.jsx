import { Breadcrumb, Button, Col, Divider, Form, Input, Modal, Radio, Row } from "antd";
import React, { useEffect, useState } from "react";
import { FaHome, FaTrash } from "react-icons/fa";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";

import Loading from "~/components/Loading/Loading";
import BaseUI from "~/layouts/admin/BaseUI";
import * as request from "~/utils/httpRequest";

function StaffDetail() {
  const { id } = useParams();
  const [form] = Form.useForm();
  const [avatar, setAvatar] = useState(null);
 

  const [loading, setLoading] = useState(true);
  const [staff, setStaff] = useState({});
  const [previewUrl, setPreviewUrl] = useState(null);
  const handleImageSelect = (event) => {
    try {
      const file = event.target.files[0];
      const imageUrl = URL.createObjectURL(file);
      setPreviewUrl(imageUrl);
      setAvatar(file);
    } catch (e) {
      setPreviewUrl(null);
    }
  };

  useEffect(() => {
    const timeout = setTimeout(() => {
      loadStaff(form,id);
    }, 1000);
    return () => clearTimeout(timeout);
  }, [form, id]);

  const loadStaff = (form,id) => {
    request.get(`/staff/${id}`).then((response) => {
      setStaff(response);
      form.setFieldsValue({
        username: response.username,
        cccd: response.cccd,
        name: response.name,
        birthday: response.birthday,
        gender: response.gender,
        email: response.email,
        phoneNumber: response.phoneNumber
      });
    }).catch((e) => {
      console.log(e);
    });
    setLoading(false);
  }

  const handleUpdate = (data) => {
    const formData = new FormData();
    if (avatar !== null) {
      formData.append("avatar", avatar);
    }
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
      content: "Xác nhận cập nhật nhân viên ?",
      okText: "Ok",
      cancelText: "Cancel",
      onOk: () => {
        setLoading(true);
        request.put(`/staff/${id}`, formData, { headers: { "Content-Type": "multipart/form-data", }, }).then((response) => {
          console.log(response);
          setLoading(true);
          if (response.data.success) {
            toast.success("Cập nhật thành công!");
            setAvatar(null);
            setPreviewUrl(null);
            loadStaff(form,id);
          }
        }).catch((e) => {
          console.log(e);
          toast.error(e.response.data);
          setLoading(false);
        });
      },
    });
  }


  if (loading) {
    return (
      <>
        <BaseUI>
          <Loading />
        </BaseUI>
      </>
    );
  }
  
}

export default StaffDetail;
