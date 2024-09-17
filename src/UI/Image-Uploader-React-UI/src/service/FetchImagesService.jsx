import axios from 'axios'


const BASE_URL = "http://localhost:8080/"


export const getImage = async (dataType, groupId, fileName) =>{ 
    try{
    const response = await axios.post(BASE_URL + "upload/getImage",{
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