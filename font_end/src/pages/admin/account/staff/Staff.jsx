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



  
}

export default Staff;
