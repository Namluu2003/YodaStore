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
