
import { Col, Form, Select } from "antd";
import { Option } from "antd/es/mentions";
import React, { useEffect, useState } from "react";
import * as request from "~/utils/httpRequest";

const GHNInfo = ({ dataAddress, prov, distr, war, disabledValue }) => {
  const [provinces, setProvinces] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);

  const [selectedProvince, setSelectedProvince] = useState(null);
  const [selectedDistrict, setSelectedDistrict] = useState(null);
  const [selectedWard, setSelectedWard] = useState(null);

    const configApi = {
    headers: {
      Token: "aef361b5-f26a-11ed-bc91-ba0234fcde32",
      "Content-Type": "application/json",
      ShopId: 124173,
    },
  };

   useEffect(() => {
    request.get("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province", configApi).then((response) => {
      const filteredProvinces = response.data.filter(
        (province) => province.ProvinceName !== "Test"
      );
      setProvinces(filteredProvinces);
    }).catch((e) => {
      console.log(e);
    });

    // Lấy danh sách quận/huyện và xã/phường nếu có prov và distr
    if (prov && distr) {
      request
        .get(
          `https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=${prov}`,
          configApi
        )
        .then((response) => {
          setDistricts(response.data);
        })
        .catch((e) => {
          console.log(e);
        });

      request
        .get(
          `https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=${distr}`,
          configApi
        )
        .then((response) => {
          setWards(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    }
  }, [prov, distr, war]);

  const handleProvinceChange = (provinceCode) => {
    request
      .get(
        `https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=${provinceCode}`,
        configApi
      )
      .then((response) => {
        setDistricts(response.data);
        setWards([]); // Reset wards khi chọn lại tỉnh
      })
      .catch((e) => {
        console.log(e);
      });
    setSelectedProvince(provinceCode);
    setSelectedDistrict(null); // Reset quận/huyện khi thay đổi tỉnh
    setSelectedWard(null); // Reset xã/phường khi thay đổi tỉnh
  };

  const handleDistrictChange = (districtCode) => {
    request
      .get(
        `https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=${districtCode}`,
        configApi
      )
      .then((response) => {
        setWards(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
    setSelectedDistrict(districtCode);
    setSelectedWard(null); // Reset xã/phường khi thay đổi quận/huyện
  };

  const handleWardChange = (wardCode) => {
    setSelectedWard(wardCode);
    dataAddress({
      province: selectedProvince,
      district: selectedDistrict,
      ward: wardCode,
    });
  };

  useEffect(() => {
    setSelectedProvince(prov);
    setSelectedDistrict(distr);
    setSelectedWard(war);
  }, [prov, distr, war]);

  return (
    <>
      <Col span={8}>
        <Form.Item
          label="Tỉnh/thành phố"
          name="province"
          rules={[
            { required: true, message: "Tỉnh thành phố không được để trống!" },
          ]}
          initialValue={!prov ? null : parseInt(prov)}
        >
          <Select
            showSearch
            onChange={handleProvinceChange}
            placeholder="Chọn tỉnh/thành phố..."
            optionFilterProp="children"
            filterOption={(input, option) =>
              (option?.children ?? "").toLowerCase().includes(input.toLowerCase())
            }
            defaultValue={!prov ? null : parseInt(prov)}
            disabled={disabledValue}
          >
            {provinces.map((province) => (
              <Option key={province.ProvinceID} value={province.ProvinceID}>
                {province.ProvinceName}
              </Option>
            ))}
          </Select>
        </Form.Item>
      </Col>
      <Col span={8}>
        <Form.Item
          label="Quận/huyện"
          name="district"
          rules={[
            { required: true, message: "Quận huyện không được để trống!" },
          ]}
          initialValue={!distr ? null : parseInt(distr)}
        >
          <Select
            showSearch
            onChange={handleDistrictChange}
            placeholder="Chọn quận/huyện..."
            optionFilterProp="children"
            filterOption={(input, option) =>
              (option?.children ?? "").toLowerCase().includes(input.toLowerCase())
            }
            defaultValue={!distr ? null : parseInt(distr)}
            disabled={disabledValue || !selectedProvince} // Vô hiệu hóa nếu chưa chọn tỉnh
          >
            {districts.map((district) => (
              <Option key={district.DistrictID} value={district.DistrictID}>
                {district.DistrictName}
              </Option>
            ))}
          </Select>
        </Form.Item>
      </Col>
      <Col span={8}>
        <Form.Item
          label="Xã/phường/thị trấn"
          name="ward"
          rules={[
            { required: true, message: "Xã phường không được để trống!" },
          ]}
          initialValue={war}
        >
          <Select
            showSearch
            onChange={handleWardChange}
            placeholder="Chọn xã/phường/thị trấn..."
            optionFilterProp="children"
            filterOption={(input, option) =>
              (option?.children ?? "").toLowerCase().includes(input.toLowerCase())
            }
            defaultValue={war}
            disabled={disabledValue || !selectedDistrict} // Vô hiệu hóa nếu chưa chọn quận/huyện
          >
            {wards.map((ward) => (
              <Option key={ward.WardCode} value={ward.WardCode}>
                {ward.WardName}
              </Option>
            ))}
          </Select>
        </Form.Item>
      </Col>
    </>
  );
};

export default GHNInfo;