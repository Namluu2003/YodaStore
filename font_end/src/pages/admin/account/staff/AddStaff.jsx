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

  

 

  
}

export default AddStaff;
