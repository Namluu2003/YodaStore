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
  
   
    
    
  
    
  
  }
  
  export default Color;
  