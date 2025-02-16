import React, { useState, useCallback } from "react";
import { Layout, Button, Badge, Space, Avatar, Tooltip } from "antd";
import { MenuFoldOutlined, MenuUnfoldOutlined, UserOutlined, BellOutlined } from "@ant-design/icons";
import Footer from "./Footer";
import Sidebar from "./components/Menu/Menu";

const { Header, Sider, Content } = Layout;

const BaseUI = ({ children }) => {
  const [collapsed, setCollapsed] = useState(false);

  // Hàm toggle sidebar
  const toggleSidebar = useCallback(() => setCollapsed((prev) => !prev), []);

  return (
    <Layout>
      {/* Sidebar */}
      <Sider trigger={null} collapsible collapsed={collapsed}>
        <div className="demo-logo-vertical" />
        <Sidebar />
      </Sider>

      <Layout>
        {/* Header */}
        <Header className="bg-body px-3 d-flex align-items-center justify-content-between">
          {/* Nút thu/phóng sidebar */}
          <Button
            icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
            onClick={toggleSidebar}
            className="btn border-0"
          />

          {/* Biểu tượng thông báo & avatar người dùng */}
          <Space size="middle">
            <Tooltip title="Thông báo">
              <Badge count={10} size="small">
                <BellOutlined className="fs-5" />
              </Badge>
            </Tooltip>
            <Tooltip title="Tài khoản">
              <Avatar icon={<UserOutlined />} size="large" />
            </Tooltip>
          </Space>
        </Header>

        {/* Nội dung chính */}
        <Content className="m-3 p-3 bg-body" style={{ minHeight: "100vh" }}>
          {children}
        </Content>

        {/* Footer */}
        <Footer />
      </Layout>
    </Layout>
  );
};

export default BaseUI;
