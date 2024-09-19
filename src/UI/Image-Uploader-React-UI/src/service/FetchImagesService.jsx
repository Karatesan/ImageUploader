import axios from 'axios'


const BASE_URL = "http://localhost:8080/"


export const getImage = async (dataType, groupId, fileName) =>{ 
    try{
    const response = await axios.post(BASE_URL + "download/getImage",{
              fileName: fileName,
              groupId: groupId,
              dataType: dataType
      }
  )
    return response.data;
  }
  catch(exception){
    throw exception
  }
}

export const uploadFile = async (image) =>{ 
    try{
        const formData = new FormData();
        // files.forEach(image=>{
        //     formData.append("images",image)
        // })
        formData.append("image",image)
        console.log(formData)
    const response = await axios.post(BASE_URL + "upload", formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        }})
    return response.data;
  }
  catch(exception){
    throw exception
  }
}