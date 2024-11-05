/* eslint-disable no-useless-catch */
import axios from "axios";

const BASE_URL = "http://localhost:8080/";

export const getImage = async (dataType, groupId, fileName) => {
  try {
    const response = await axios.post(BASE_URL + "download/getImage", {
      fileName: fileName,
      groupId: groupId,
      dataType: dataType,
    });
    return response.data;
  } catch (exception) {
    throw exception;
  }
};

export const getAllImagesFromGroup = async (groupId) => {
  try {
    const response = await axios.post(BASE_URL + "download/images/" + groupId);
    return response.data;
  } catch (exception) {
    throw exception;
  }
};

export const uploadFiles = async (images, groupId) => {
  try {
    const formData = new FormData();
    images.forEach((image) => {
      formData.append("images", image);
    });
    formData.append("groupId", groupId);
    //formData.append("image",image)

    const response = await axios.post(BASE_URL + "upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (exception) {
    throw exception;
  }
};
